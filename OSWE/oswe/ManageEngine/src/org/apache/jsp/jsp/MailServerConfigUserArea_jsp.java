/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class MailServerConfigUserArea_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   26 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   32 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   33 */   static { _jspx_dependants.put("/jsp/includes/NewActions.jspf", Long.valueOf(1473429417000L));
/*   34 */     _jspx_dependants.put("/jsp/includes/CreateApplicationWizard.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   66 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   70 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   77 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   78 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   79 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   80 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   81 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   82 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   85 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   86 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   94 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   95 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   99 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  100 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  101 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  102 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*  103 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  104 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  105 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  106 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  107 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  108 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/*  109 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/*  110 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*  111 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/*  112 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  113 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  114 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  115 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  116 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/*  117 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*  118 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*  119 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
/*  120 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.release();
/*  121 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/*  122 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  129 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  132 */     JspWriter out = null;
/*  133 */     Object page = this;
/*  134 */     JspWriter _jspx_out = null;
/*  135 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  139 */       response.setContentType("text/html;charset=UTF-8");
/*  140 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  142 */       _jspx_page_context = pageContext;
/*  143 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  144 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  145 */       session = pageContext.getSession();
/*  146 */       out = pageContext.getOut();
/*  147 */       _jspx_out = out;
/*      */       
/*  149 */       out.write("\n<!--$Id$-->\n\n\n\n\t<script language=\"JavaScript1.2\">\n\t\n\tjQuery(document).ready(function(){\n\tvar isDelegatedAdmin=");
/*  150 */       out.print(com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(request.getRemoteUser()));
/*  151 */       out.write("\n\tif(isDelegatedAdmin){\n\tjQuery('input[name=\"Submit\"],input[name=\"reset\"]').prop(\"disabled\",true);\t\t//NO I18N\n\tjQuery('input[name=\"Submit\"],input[name=\"reset\"]').attr(\"class\",\"annotate-button-disabled\")\t//NO I18N\n\t}\n\t});\n\t\nfunction fnFormSubmit()\n{\n\t");
/*  152 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/*  154 */       out.write(10);
/*  155 */       out.write(9);
/*  156 */       out.write(9);
/*      */       
/*  158 */       org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(org.apache.struts.taglib.logic.NotPresentTag.class);
/*  159 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  160 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */       
/*  162 */       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  163 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  164 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */         for (;;) {
/*  166 */           out.write("\n\t\tif(trimAll(document.AMActionForm.smtpserver.value)=='')\n\t\t{\n\t\t\talert('");
/*  167 */           out.print(FormatUtil.getString("am.webclient.mailsettings.alertname.text"));
/*  168 */           out.write("');\n\t\t\tdocument.AMActionForm.smtpserver.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse if(trimAll(document.AMActionForm.smtpport.value)=='')\n\t\t{\n\t\t\talert('");
/*  169 */           out.print(FormatUtil.getString("am.webclient.mailsettings.alertport.text"));
/*  170 */           out.write("');\n\t\t\tdocument.AMActionForm.smtpport.focus();\n\t\t\treturn false;\n\t\t}\n\n\t\tif(document.AMActionForm.SMTPServerAuth.checked == true)\n\t\t{\n\t\t\tif(document.AMActionForm.SMTPServerUserName.value =='')\n\t\t\t{\n\t\t\t\talert('");
/*  171 */           out.print(FormatUtil.getString("am.webclient.mailsettings.alertusrname.text"));
/*  172 */           out.write("');\n\t\t\t\tdocument.AMActionForm.SMTPServerUserName.focus();\n\t\t\t\treturn false;\n\t\t\t}\n\t\t\telse if(document.AMActionForm.SMTPServerPassword.value == '')\n\t\t\t{\n\t\t\t\tvar pwd = confirm('");
/*  173 */           out.print(FormatUtil.getString("am.webclient.mailsettings.pwd.text"));
/*  174 */           out.write("');\n\t\t\t\tif(!pwd)\n\t\t\t\t{\n\t\t\t\t\tdocument.AMActionForm.SMTPServerUserName.focus();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t}\n\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.AMActionForm.SMTPServerUserName.value='';\n\t\t\tdocument.AMActionForm.SMTPServerPassword.value = '';\n// \t\t\tdocument.AMActionForm.prmTlsAuth.value = false;\n\t\t}\n\t\tif(document.AMActionForm.emailidfortechnicalsupport != null && document.AMActionForm.emailidfortechnicalsupport.checked)\n\t\t{\n\t\t\tif(document.AMActionForm.emailidfortechnicalsupport.checked == true)\n\t\t\t{\n\t\t\t\tdocument.AMActionForm.emailidfortechnicalsupport.value='on';");
/*  175 */           out.write("\n\t\t\t}\n\t\t\t//document.AMActionForm.emailidfortechnicalsupport.value=document.AMActionForm.emailAddress.value;\n\t\t}\n\n\t\tif(document.AMActionForm.seccheck.checked == true)\n\t\t{\n\t\t\tvalsecserver();\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.AMActionForm.submit();\n\t\t}\n\t\t");
/*  176 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  177 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  181 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  182 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */       }
/*      */       else {
/*  185 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  186 */         out.write("\n}\n\nfunction valsecserver()\n{\n\tif(trimAll(document.AMActionForm.smtpsecserver.value)=='')\n\t{\n\t\talert('");
/*  187 */         out.print(FormatUtil.getString("am.webclient.mailsettings.alertname1.text"));
/*  188 */         out.write("');\n\t\tdocument.AMActionForm.smtpsecserver.focus();\n\t\treturn false;\n\t}\n\telse if(trimAll(document.AMActionForm.smtpsecport.value)=='')\n\t{\n\t\talert('");
/*  189 */         out.print(FormatUtil.getString("am.webclient.mailsettings.alertport1.text"));
/*  190 */         out.write("');\n\t\tdocument.AMActionForm.smtpsecport.focus();\n\t\treturn false;\n\t}\n\tif(document.AMActionForm.SMTPsecServerAuth.checked == true)\n\t{\n\t\tif(document.AMActionForm.SMTPsecServerUserName.value =='')\n\t\t{\n\t\t\talert('");
/*  191 */         out.print(FormatUtil.getString("am.webclient.mailsettings.alertserver.text"));
/*  192 */         out.write("');\n\t\t\tdocument.AMActionForm.SMTPsecServerUserName.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse if(document.AMActionForm.SMTPsecServerPassword.value == '')\n\t\t{\n\t\t\tvar pwd = confirm('");
/*  193 */         out.print(FormatUtil.getString("am.webclient.mailsettings.alertpwd.text"));
/*  194 */         out.write("');\n\t\t\tif(!pwd)\n\t\t\t{\n\t\t\t\tdocument.AMActionForm.SMTPsecServerPassword.focus();\n\t\t\t\treturn false;\n\t\t\t}\n\t\t}\n\t}\n\telse\n\t{\n\t\tdocument.AMActionForm.SMTPsecServerUserName.value='';\n\t\tdocument.AMActionForm.SMTPsecServerPassword.value = '';\n// \t\tdocument.AMActionForm.secTlsAuth.value = false;\n\t}\n\n\tdocument.AMActionForm.submit();\n}\n\nfunction myOnLoad()\n{\n\t\tdocument.getElementById(\"topheading\").innerHTMl='");
/*  195 */         out.print(FormatUtil.getString("am.webclient.mailsettings.configuremailserver.text"));
/*  196 */         out.write("';\n\t\tif(document.AMActionForm.SMTPServerUserName.value !=\"\")\n\t\t{\n\t\t\tdocument.AMActionForm.SMTPServerAuth.checked=true;\n\t\t\tshowPrimaryUserPassword();\n\t\t}\n\t\telse{\n\t\t\tdocument.AMActionForm.SMTPServerAuth.checked=false;\n\t\t}\n// \t\t------Secondary Mail server------\n\t\tif(document.AMActionForm.seccheck.checked)\n\t\t{\n\t\t\tjavascript:showDiv('secserver');\n\t\t\tif(document.AMActionForm.SMTPsecServerUserName.value !=\"\")\n\t\t\t{\n\t\t\t\tdocument.AMActionForm.SMTPsecServerAuth.checked=true;\n\t\t\t\tshowSecondaryUserPassword();\n\t\t\t}\n\t\t\telse{\n\t\t\t\tdocument.AMActionForm.SMTPsecServerAuth.checked=false;\n\t\t\t}\n\t\t}\n\t\telse\n\t\t{\n\t\t\tjavascript:hideDiv('secserver');\n\t\t}\n}\n\nfunction showPrimaryUserPassword()\n{\n  if(document.AMActionForm.SMTPServerAuth.checked)\n  {\n   javascript:showRow(\"mailConfig\");\t//NO I18N\n  }\n  else\n  {\n   javascript:hideRow(\"mailConfig\");\t//NO I18N\n  }\n}\n\nfunction showSecondaryUserPassword()\n{\n\n  if(document.AMActionForm.SMTPsecServerAuth.checked)\n  {\n   javascript:showRow(\"mailConfig1\");\t//NO I18N\n  }\n  else\n  {\n   javascript:hideRow(\"mailConfig1\");\t//NO I18N\n");
/*  197 */         out.write("  }\n}\n\n\nfunction showHide(show)\n{\n\tif(document.AMActionForm.seccheck.checked == true)\n\t{\n\t\tjavascript:showDiv(show);\n\t}\n\telse\n\t{\n\t\tjavascript:hideDiv(show);\n\t}\n}\n\n</script>\n\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  198 */         if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */           return;
/*  200 */         out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n\n\n\n\n");
/*      */         
/*  202 */         String message = (String)request.getAttribute("success");
/*  203 */         pageContext.setAttribute("isAdminServer", Boolean.valueOf(EnterpriseUtil.isAdminServer()));
/*  204 */         if (message != null)
/*      */         {
/*  206 */           out.write("\n\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n\t  <tr>\n\t    <td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\">\n\t      <img src=\"/images/icon_message_success.gif\" width=\"25\" height=\"25\" vspace=\"5\"></td>\n\t    <td width=\"95%\" class=\"bodytext\"  > ");
/*  207 */           out.print(message);
/*  208 */           out.write("\n\t    </td>\n\t</tr>\n\t</table>\n\t");
/*      */         }
/*  210 */         out.write(10);
/*  211 */         out.write(10);
/*      */         
/*  213 */         org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/*  214 */         _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  215 */         _jspx_th_html_005fform_005f0.setParent(null);
/*      */         
/*  217 */         _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*      */         
/*  219 */         _jspx_th_html_005fform_005f0.setStyle("display:inline");
/*  220 */         int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  221 */         if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */           for (;;) {
/*  223 */             out.write(10);
/*      */             
/*  225 */             PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  226 */             _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  227 */             _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  229 */             _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/*  230 */             int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  231 */             if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */               for (;;) {
/*  233 */                 out.write(10);
/*      */                 
/*  235 */                 IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  236 */                 _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  237 */                 _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                 
/*  239 */                 _jspx_th_c_005fif_005f0.setTest("${param.admin!='true'}");
/*  240 */                 int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  241 */                 if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                   for (;;) {
/*  243 */                     out.write(10);
/*  244 */                     out.write("<!--$Id$-->\n              \n\n\n\n\n<script>                      \nfunction fnFormSubmit1(object)\n{\n\tlocation.href=object;\n}\n\nfunction showMailServerSettings()\n {\n         var showMail = false;\n         var query = window.location.search;\n         var pairs = query.split(\"&\");\n         \n         for (var i=0;i<pairs.length;i++)\n         {\n                 var pos = pairs[i].indexOf('=');\n                 if (pos >= 0)\n                 {\n                         var argname = pairs[i].substring(0,pos);\n                         var value = pairs[i].substring(pos+1);\n                         if(argname == \"isFromApi\")\n                         {\n                                 showMail = true;\n                         }\n                         //keys[keys.length] = argname;\n                         //values[values.length] = value;\n                 }\n         }\n         //alert(showMail);\n         return  showMail;\n }\n\nfunction doInitStuffOnBodyLoad()\n{\n        ");
/*      */                     
/*  246 */                     if ((pageContext.getAttribute("jsppage") != null) && (pageContext.getAttribute("jsppage").equals("programaction")))
/*      */                     {
/*      */ 
/*  249 */                       out.write("\n        myOnLoad1();\n        ");
/*      */                     }
/*      */                     
/*      */ 
/*  253 */                     out.write("\n        \n\tif(document.AMActionForm!=null)\n\t{\n\tif(typeof(document.AMActionForm.actions)!='undefined')\n\t  {\n\t  if((location.search!= null && (location.search).match(\"EmailAction\")!=null) || (location.path!=null && (location.path).match(\"EMailActionForm\")!=null))\n\t  {\n\t\t\n\t\t");
/*  254 */                     if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                       return;
/*  256 */                     out.write("\t\n\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"SMSAction\")!=null) || (location.path!=null && (location.path).match(\"SMSActionForm\")!=null) || (location.search!= null && (location.search).match(\"SMSServerConfiguration\")!=null))\n\t  {\n\t\t \n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"ExecProg\")!=null) || (location.path!=null && (location.path).match(\"ExecProgramActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"reloadSendTrapActionForm\")!=null) || (location.path!=null && (location.path).match(\"SendTrapActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"Ticket\")!=null) || (location.path!=null && (location.path).match(\"showLogTicket\")!=null) ||  (location.search).match(\"showTicketAction\")!=null )\n");
/*  257 */                     out.write("\t  {\n\t\t\t");
/*  258 */                     if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                       return;
/*  260 */                     out.write("\n\t\t  ");
/*      */                     
/*  262 */                     if ((com.adventnet.appmanager.util.Constants.sqlManager) || (EnterpriseUtil.isAdminServer()))
/*      */                     {
/*  264 */                       out.write("\n\t\t\tdocument.AMActionForm.actions.selectedIndex=4;\n\t    \t");
/*      */                     }
/*      */                     else
/*      */                     {
/*  268 */                       out.write("\n\t       \tdocument.AMActionForm.actions.selectedIndex=5;\n\t  \t\t");
/*      */                     }
/*  270 */                     out.write("\n\t  }\n\t  else if(location.pathname!= null && (location.pathname).match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(location.search!= null && (location.search).match(\"jre\")!=null || (location.search!=null && (location.search).match(\"ThreadDumpActions\")!=null))\n\t  {\n\t    ");
/*  271 */                     if (EnterpriseUtil.isManagedServer())
/*      */                     {
/*  273 */                       out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=5;\n\t\t");
/*      */                     }
/*      */                     else
/*      */                     {
/*  277 */                       out.write("\n\t    document.AMActionForm.actions.selectedIndex=6;\n        ");
/*      */                     }
/*  279 */                     out.write("\n\t  }\t\n\t  else if(location.search!= null && (location.search).match(\"showVMAction\")!=null || (location.search!=null && (location.search).match(\"ShowVMActions\")!=null))\n\t  {\t\t \n\t    ");
/*  280 */                     if (EnterpriseUtil.isManagedServer())
/*      */                     {
/*  282 */                       out.write("\n\t    if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t    \tdocument.AMActionForm.actions.selectedIndex=9;\n\t    }else{\n\t\t\tdocument.AMActionForm.actions.selectedIndex=7;\n\t    }\n\t\t");
/*      */                     }
/*      */                     else
/*      */                     {
/*  286 */                       out.write("\n\t\t  if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t\t    \tdocument.AMActionForm.actions.selectedIndex=10;\n\t\t    }else{\n\t  \t  document.AMActionForm.actions.selectedIndex=8;\n\t\t    }\n        ");
/*      */                     }
/*  288 */                     out.write("\n\t  }\t  \n\t  else if(location.search!= null && (location.search).match(\"winServAction\")!=null || (location.search!=null && (location.search).match(\"winServAction\")!=null))\n\t  { \n\t    ");
/*  289 */                     if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  290 */                       out.write("\n\t      document.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */                     }
/*  292 */                     else if (EnterpriseUtil.isManagedServer()) {
/*  293 */                       out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=8;\n\t\t");
/*      */                     } else {
/*  295 */                       out.write("\n          document.AMActionForm.actions.selectedIndex=9;\n        ");
/*      */                     }
/*  297 */                     out.write("\t\t\n\t  }\t  \n\t   else if((location.search!= null && (location.search).match(\"ExecQueryAction\")!=null) || (location.path!=null && (location.path).match(\"ExecQueryActionForm\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=5;\n  \t   }\n\t   else if((location.search!= null && (location.search).match(\"sqlJobAction\")!=null) || (location.path!=null && (location.path).match(\"sqlJobAction\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=7;\n  \t   }\n\t   else if(location.search!= null && (location.search).match(\"amazon\")!=null)\n\t\t  {\n\t\t    ");
/*  298 */                     if (EnterpriseUtil.isManagedServer()) {
/*  299 */                       out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */                     } else {
/*  301 */                       out.write("\n\t\t    document.AMActionForm.actions.selectedIndex=7;\n        ");
/*      */                     }
/*  303 */                     out.write("\n\t\t  }\n\t  \t  \n\t  }\n\n\tif(document.AMActionForm.selectedBusinessHourID != null)\n\t{\n\t\tinit();\n\t}\n\t\n\t}\n\telse\n\t{\n\t\tif(document.MBeanOperationActionForm.actions!=undefined)\n\t\t{\n\t  \t      document.MBeanOperationActionForm.actions.selectedIndex=4;\t  \n\t  \t}\n\t}\n\n}\nfunction restvalue()\n{\n//alert(document.AMActionForm.actionslist.selectedIndex);\nvar selectedIdx=document.AMActionForm.actionslist.selectedIndex;\ndocument.AMActionForm.reset();\ndocument.AMActionForm.actionslist.selectedIndex=selectedIdx;\n}\n</script>\n");
/*      */                     
/*  305 */                     String action_haid = request.getParameter("haid");
/*  306 */                     String returnpath = "";
/*      */                     
/*  308 */                     if (request.getParameter("returnpath") != null)
/*      */                     {
/*  310 */                       returnpath = "&returnpath=" + java.net.URLEncoder.encode(request.getParameter("returnpath"));
/*      */                     }
/*      */                     
/*      */ 
/*  314 */                     out.write(10);
/*  315 */                     out.write(10);
/*      */                     
/*  317 */                     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  318 */                     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  319 */                     _jspx_th_c_005fset_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  321 */                     _jspx_th_c_005fset_005f0.setVar("isSqlManager");
/*  322 */                     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  323 */                     if (_jspx_eval_c_005fset_005f0 != 0) {
/*  324 */                       if (_jspx_eval_c_005fset_005f0 != 1) {
/*  325 */                         out = _jspx_page_context.pushBody();
/*  326 */                         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  327 */                         _jspx_th_c_005fset_005f0.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  330 */                         out.print(com.adventnet.appmanager.util.Constants.sqlManager);
/*  331 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  332 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  335 */                       if (_jspx_eval_c_005fset_005f0 != 1) {
/*  336 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  339 */                     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  340 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */                     }
/*      */                     
/*  343 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  344 */                     out.write(10);
/*      */                     
/*  346 */                     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  347 */                     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  348 */                     _jspx_th_c_005fset_005f1.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  350 */                     _jspx_th_c_005fset_005f1.setVar("isIt360");
/*  351 */                     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  352 */                     if (_jspx_eval_c_005fset_005f1 != 0) {
/*  353 */                       if (_jspx_eval_c_005fset_005f1 != 1) {
/*  354 */                         out = _jspx_page_context.pushBody();
/*  355 */                         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/*  356 */                         _jspx_th_c_005fset_005f1.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  359 */                         out.print(com.adventnet.appmanager.util.Constants.isIt360);
/*  360 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/*  361 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  364 */                       if (_jspx_eval_c_005fset_005f1 != 1) {
/*  365 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  368 */                     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  369 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                     }
/*      */                     
/*  372 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/*  373 */                     out.write(10);
/*      */                     
/*  375 */                     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  376 */                     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/*  377 */                     _jspx_th_c_005fset_005f2.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  379 */                     _jspx_th_c_005fset_005f2.setVar("isAdminServer");
/*  380 */                     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/*  381 */                     if (_jspx_eval_c_005fset_005f2 != 0) {
/*  382 */                       if (_jspx_eval_c_005fset_005f2 != 1) {
/*  383 */                         out = _jspx_page_context.pushBody();
/*  384 */                         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/*  385 */                         _jspx_th_c_005fset_005f2.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  388 */                         out.print(EnterpriseUtil.isAdminServer());
/*  389 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/*  390 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  393 */                       if (_jspx_eval_c_005fset_005f2 != 1) {
/*  394 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  397 */                     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/*  398 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */                     }
/*      */                     
/*  401 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/*  402 */                     out.write(10);
/*      */                     
/*  404 */                     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  405 */                     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/*  406 */                     _jspx_th_c_005fset_005f3.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  408 */                     _jspx_th_c_005fset_005f3.setVar("isProfServer");
/*  409 */                     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/*  410 */                     if (_jspx_eval_c_005fset_005f3 != 0) {
/*  411 */                       if (_jspx_eval_c_005fset_005f3 != 1) {
/*  412 */                         out = _jspx_page_context.pushBody();
/*  413 */                         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/*  414 */                         _jspx_th_c_005fset_005f3.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  417 */                         out.print(EnterpriseUtil.isProfEdition());
/*  418 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/*  419 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  422 */                       if (_jspx_eval_c_005fset_005f3 != 1) {
/*  423 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  426 */                     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/*  427 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */                     }
/*      */                     
/*  430 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/*  431 */                     out.write(10);
/*      */                     
/*  433 */                     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  434 */                     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/*  435 */                     _jspx_th_c_005fset_005f4.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  437 */                     _jspx_th_c_005fset_005f4.setVar("isCloudServer");
/*  438 */                     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/*  439 */                     if (_jspx_eval_c_005fset_005f4 != 0) {
/*  440 */                       if (_jspx_eval_c_005fset_005f4 != 1) {
/*  441 */                         out = _jspx_page_context.pushBody();
/*  442 */                         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/*  443 */                         _jspx_th_c_005fset_005f4.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  446 */                         out.print(EnterpriseUtil.isCloudEdition());
/*  447 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/*  448 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  451 */                       if (_jspx_eval_c_005fset_005f4 != 1) {
/*  452 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  455 */                     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/*  456 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */                     }
/*      */                     
/*  459 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/*  460 */                     out.write("\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" >\n  <tr> \n    <td align=\"left\" width=\"50%\" class=\"bodytext\">");
/*  461 */                     out.print(FormatUtil.getString("am.webclient.newaction.selectactiontype"));
/*  462 */                     out.write("&nbsp;\n\t<select id=\"actionslist\" name=\"actions\" onchange=\"javascript:fnFormSubmit1(this.form.actions.options[this.selectedIndex].value);\" class=\"formtext\">\n\t");
/*      */                     
/*  464 */                     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  465 */                     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  466 */                     _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f0);
/*  467 */                     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  468 */                     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                       for (;;) {
/*  470 */                         out.write(10);
/*  471 */                         out.write(9);
/*      */                         
/*  473 */                         WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  474 */                         _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  475 */                         _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                         
/*  477 */                         _jspx_th_c_005fwhen_005f0.setTest("${empty param.global}");
/*  478 */                         int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  479 */                         if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                           for (;;) {
/*  481 */                             out.write("\t\n\t\t<option value=\"/showTile.do?TileName=.EmailActions&haid=");
/*  482 */                             out.print(action_haid);
/*  483 */                             out.print(returnpath);
/*  484 */                             out.write(34);
/*  485 */                             out.write(62);
/*  486 */                             out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  487 */                             out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.SMSActions&haid=");
/*  488 */                             out.print(action_haid);
/*  489 */                             out.print(returnpath);
/*  490 */                             out.write(34);
/*  491 */                             out.write(62);
/*  492 */                             out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  493 */                             out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.ExecProg&haid=");
/*  494 */                             out.print(action_haid);
/*  495 */                             out.print(returnpath);
/*  496 */                             out.write(34);
/*  497 */                             out.write(62);
/*  498 */                             out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  499 */                             out.write("</option>\n\t\t<option value=\"/adminAction.do?method=reloadSendTrapActionForm&haid=");
/*  500 */                             out.print(action_haid);
/*  501 */                             out.print(returnpath);
/*  502 */                             out.write(34);
/*  503 */                             out.write(62);
/*  504 */                             out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/*  505 */                             out.write("</option>\n\t\t\n\t\t");
/*      */                             
/*  507 */                             ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  508 */                             _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  509 */                             _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*  510 */                             int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  511 */                             if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                               for (;;) {
/*  513 */                                 out.write("\n\t\t\t");
/*      */                                 
/*  515 */                                 WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  516 */                                 _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  517 */                                 _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                 
/*  519 */                                 _jspx_th_c_005fwhen_005f1.setTest("${!isIt360}");
/*  520 */                                 int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  521 */                                 if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                   for (;;) {
/*  523 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/*  525 */                                     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  526 */                                     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  527 */                                     _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/*  528 */                                     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  529 */                                     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                       for (;;) {
/*  531 */                                         out.write("\n\t\t\t\t\t");
/*      */                                         
/*  533 */                                         WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  534 */                                         _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  535 */                                         _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                         
/*  537 */                                         _jspx_th_c_005fwhen_005f2.setTest("${!isSqlManager}");
/*  538 */                                         int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  539 */                                         if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                           for (;;) {
/*  541 */                                             out.write("\n\t\t\t\t\t\t<!-- MBean Operation-->\n\t\t\t\t\t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  542 */                                             out.print(action_haid);
/*  543 */                                             out.print(returnpath);
/*  544 */                                             out.write(34);
/*  545 */                                             out.write(62);
/*  546 */                                             out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  547 */                                             out.write("</option>\n\t\t\t\t\t");
/*  548 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  549 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/*  553 */                                         if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  554 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                         }
/*      */                                         
/*  557 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  558 */                                         out.write("\n\t\t\t\t\t");
/*      */                                         
/*  560 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  561 */                                         _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  562 */                                         _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f2);
/*  563 */                                         int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  564 */                                         if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                           for (;;) {
/*  566 */                                             out.write("\n\t\t\t\t\t\t<!-- Execute Query Action-->\n\t   \t\t\t\t\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  567 */                                             out.print(action_haid);
/*  568 */                                             out.print(returnpath);
/*  569 */                                             out.write(34);
/*  570 */                                             out.write(62);
/*  571 */                                             out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  572 */                                             out.write("</option>\n\t   \t\t\t\t\t<!-- Sql job action -->\n\t\t\t\t\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  573 */                                             out.print(action_haid);
/*  574 */                                             out.print(returnpath);
/*  575 */                                             out.write(34);
/*  576 */                                             out.write(62);
/*  577 */                                             out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  578 */                                             out.write("</option>\n\t\t\t\t\t");
/*  579 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  580 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/*  584 */                                         if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  585 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                         }
/*      */                                         
/*  588 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  589 */                                         out.write("\n\t\t\t\t");
/*  590 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  591 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/*  595 */                                     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  596 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                     }
/*      */                                     
/*  599 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  600 */                                     out.write("\n\t\t\t\t\t<!-- Log Ticket Operation-->\n\t\t\t\t\t");
/*  601 */                                     if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  602 */                                       out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  603 */                                       out.print(action_haid);
/*  604 */                                       out.print(returnpath);
/*  605 */                                       out.write(34);
/*  606 */                                       out.write(62);
/*  607 */                                       out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  608 */                                       out.write("</option> ");
/*      */                                     }
/*  610 */                                     out.write("\n\t\t\t");
/*  611 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  612 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  616 */                                 if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  617 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                 }
/*      */                                 
/*  620 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  621 */                                 out.write("\n\t\t\t");
/*      */                                 
/*  623 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  624 */                                 _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  625 */                                 _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  626 */                                 int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  627 */                                 if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                   for (;;) {
/*  629 */                                     out.write("\n\t\t   \t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  630 */                                     out.print(action_haid);
/*  631 */                                     out.print(returnpath);
/*  632 */                                     out.write(34);
/*  633 */                                     out.write(62);
/*  634 */                                     out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  635 */                                     out.write("</option>\n\t\t   \t\t<!-- Log Ticket Operation-->\n\t\t   \t\t");
/*      */                                     
/*  637 */                                     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  638 */                                     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  639 */                                     _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                                     
/*  641 */                                     _jspx_th_c_005fif_005f3.setTest("${isProfServer || isAdminServer}");
/*  642 */                                     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  643 */                                     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                       for (;;) {
/*  645 */                                         out.write("\n\t\t\t\t\t");
/*  646 */                                         if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  647 */                                           out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  648 */                                           out.print(action_haid);
/*  649 */                                           out.print(returnpath);
/*  650 */                                           out.write(34);
/*  651 */                                           out.write(62);
/*  652 */                                           out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  653 */                                           out.write("</option> ");
/*      */                                         }
/*  655 */                                         out.write("\n\t\t   \t\t");
/*  656 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  657 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/*  661 */                                     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  662 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                     }
/*      */                                     
/*  665 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  666 */                                     out.write("\n\t\t\t");
/*  667 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  668 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  672 */                                 if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  673 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                 }
/*      */                                 
/*  676 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  677 */                                 out.write(10);
/*  678 */                                 out.write(9);
/*  679 */                                 out.write(9);
/*  680 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  681 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  685 */                             if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  686 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                             }
/*      */                             
/*  689 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  690 */                             out.write(10);
/*  691 */                             out.write(9);
/*  692 */                             out.write(9);
/*      */                             
/*  694 */                             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  695 */                             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  696 */                             _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/*  698 */                             _jspx_th_c_005fif_005f4.setTest("${!isAdminServer}");
/*  699 */                             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  700 */                             if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                               for (;;) {
/*  702 */                                 out.write("\n\t\t\t<!--JRE Action -->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  703 */                                 out.print(action_haid);
/*  704 */                                 out.print(returnpath);
/*  705 */                                 out.write(34);
/*  706 */                                 out.write(62);
/*  707 */                                 out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  708 */                                 out.write("</option>\n\t\t\t<!--Amazon Instance Action-->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  709 */                                 out.print(action_haid);
/*  710 */                                 out.print(returnpath);
/*  711 */                                 out.write(34);
/*  712 */                                 out.write(62);
/*  713 */                                 out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  714 */                                 out.write("</option>\n\t\t\t<!--VM Action -->\n\t      \t<option value=\"/adminAction.do?method=showVMAction&haid=");
/*  715 */                                 out.print(action_haid);
/*  716 */                                 out.print(returnpath);
/*  717 */                                 out.write(34);
/*  718 */                                 out.write(62);
/*  719 */                                 out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  720 */                                 out.write("</option>\n\t      \t\n\t\t\t<!-- Windows Service action -->\n\t\t\t");
/*      */                                 
/*  722 */                                 IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  723 */                                 _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  724 */                                 _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                                 
/*  726 */                                 _jspx_th_c_005fif_005f5.setTest("${!isCloudServer}");
/*  727 */                                 int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  728 */                                 if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                   for (;;) {
/*  730 */                                     out.write("\n\t   \t\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  731 */                                     out.print(action_haid);
/*  732 */                                     out.print(returnpath);
/*  733 */                                     out.write(34);
/*  734 */                                     out.write(62);
/*  735 */                                     out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  736 */                                     out.write("</option>\n\t   \t\t");
/*  737 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/*  738 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  742 */                                 if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/*  743 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                 }
/*      */                                 
/*  746 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*  747 */                                 out.write("\n\t   \t\t<option value=\"/adminAction.do?method=showVMAction&isContainerAction=true&haid=");
/*  748 */                                 out.print(action_haid);
/*  749 */                                 out.print(returnpath);
/*  750 */                                 out.write(34);
/*  751 */                                 out.write(62);
/*  752 */                                 out.print(FormatUtil.getString("am.container.action.createnew"));
/*  753 */                                 out.write("</option>\n   \t\t");
/*  754 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  755 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  759 */                             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  760 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                             }
/*      */                             
/*  763 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  764 */                             out.write(10);
/*  765 */                             out.write(9);
/*  766 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  767 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  771 */                         if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  772 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                         }
/*      */                         
/*  775 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  776 */                         out.write(10);
/*  777 */                         out.write(9);
/*      */                         
/*  779 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  780 */                         _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  781 */                         _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*  782 */                         int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  783 */                         if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                           for (;;) {
/*  785 */                             out.write(10);
/*      */                             
/*  787 */                             String redirectTo = null;
/*  788 */                             if (request.getParameter("redirectto") != null)
/*      */                             {
/*  790 */                               redirectTo = java.net.URLEncoder.encode(request.getParameter("redirectto"));
/*      */                             }
/*      */                             else
/*      */                             {
/*  794 */                               redirectTo = java.net.URLEncoder.encode("/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true");
/*      */                             }
/*      */                             
/*  797 */                             out.write("\t\n\t<option value=\"/showTile.do?TileName=.EmailActions&PRINTER_FRIENDLY=true&haid=");
/*  798 */                             out.print(action_haid);
/*  799 */                             out.write("&global=true");
/*  800 */                             out.print(returnpath);
/*  801 */                             out.write(34);
/*  802 */                             out.write(62);
/*  803 */                             out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  804 */                             out.write("</option>\n\t<option value=\"/showTile.do?TileName=.SMSActions&PRINTER_FRIENDLY=true&haid=");
/*  805 */                             out.print(action_haid);
/*  806 */                             out.write("&global=true");
/*  807 */                             out.print(returnpath);
/*  808 */                             out.write(34);
/*  809 */                             out.write(62);
/*  810 */                             out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  811 */                             out.write("</option>\n\t");
/*      */                             
/*  813 */                             PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  814 */                             _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/*  815 */                             _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                             
/*  817 */                             _jspx_th_logic_005fpresent_005f2.setRole("ADMIN,ENTERPRISEADMIN");
/*  818 */                             int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/*  819 */                             if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                               for (;;) {
/*  821 */                                 out.write(32);
/*  822 */                                 out.write("\n\t<option value=\"/jsp/ExecProgramActionForm.jsp?haid=");
/*  823 */                                 out.print(action_haid);
/*  824 */                                 out.write("&global=true");
/*  825 */                                 out.print(returnpath);
/*  826 */                                 out.write(34);
/*  827 */                                 out.write(62);
/*  828 */                                 out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  829 */                                 out.write("</option>\n\t");
/*  830 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/*  831 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  835 */                             if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/*  836 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                             }
/*      */                             
/*  839 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/*  840 */                             out.write("\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=11&haid=");
/*  841 */                             out.print(action_haid);
/*  842 */                             out.write("&global=true");
/*  843 */                             out.print(returnpath);
/*  844 */                             out.write(34);
/*  845 */                             out.write(62);
/*  846 */                             out.print(FormatUtil.getString("am.webclient.common.sendv1trap.text"));
/*  847 */                             out.write("</option>\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=12&haid=");
/*  848 */                             out.print(action_haid);
/*  849 */                             out.write("&global=true");
/*  850 */                             out.print(returnpath);
/*  851 */                             out.write(34);
/*  852 */                             out.write(62);
/*  853 */                             out.print(FormatUtil.getString("am.webclient.common.sendv2ctrap.text"));
/*  854 */                             out.write("</option>\n\t");
/*  855 */                             if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/*  856 */                               out.write(32);
/*  857 */                               out.write("\n\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&popup=true&global=true&haid=");
/*  858 */                               out.print(action_haid);
/*  859 */                               out.print(returnpath);
/*  860 */                               out.write(34);
/*  861 */                               out.write(62);
/*  862 */                               out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  863 */                               out.write("</option>\n\t");
/*      */                             }
/*  865 */                             out.write(10);
/*  866 */                             out.write(9);
/*  867 */                             out.write(9);
/*  868 */                             out.write(10);
/*  869 */                             out.write(9);
/*  870 */                             if ((!com.adventnet.appmanager.util.Constants.isIt360) || (EnterpriseUtil.isProfEdition()) || (EnterpriseUtil.isAdminServer()))
/*      */                             {
/*  872 */                               out.write("<option value=\"/adminAction.do?method=showLogTicket&global=true&haid=");
/*  873 */                               out.print(action_haid);
/*  874 */                               out.write("&redirectTo=");
/*  875 */                               out.print(redirectTo);
/*  876 */                               out.write(34);
/*  877 */                               out.write(62);
/*  878 */                               out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  879 */                               out.write("</option> ");
/*      */                             }
/*      */                             
/*  882 */                             out.write("\n\t\n\t");
/*  883 */                             if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/*  884 */                               out.write(" \n\t<!--JRE Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  885 */                               out.print(action_haid);
/*  886 */                               out.write("&global=true");
/*  887 */                               out.print(returnpath);
/*  888 */                               out.write("&ext=true\">");
/*  889 */                               out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  890 */                               out.write("</option>\n\t<!--VM Action -->\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&haid=");
/*  891 */                               out.print(action_haid);
/*  892 */                               out.print(returnpath);
/*  893 */                               out.write("&ext=true&global=true\">");
/*  894 */                               out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  895 */                               out.write("</option>\n\t<!--Amazon Instance Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  896 */                               out.print(action_haid);
/*  897 */                               out.write("&global=true");
/*  898 */                               out.print(returnpath);
/*  899 */                               out.write("&ext=true\">");
/*  900 */                               out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  901 */                               out.write("</option>\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&isContainerAction=true&haid=");
/*  902 */                               out.print(action_haid);
/*  903 */                               out.print(returnpath);
/*  904 */                               out.write("&ext=true&global=true\">");
/*  905 */                               out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  906 */                               out.write("</option>\n\t ");
/*  907 */                             } else if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  908 */                               out.write(32);
/*  909 */                               out.write("\n\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  910 */                               out.print(action_haid);
/*  911 */                               out.write("&global=true");
/*  912 */                               out.print(returnpath);
/*  913 */                               out.write(34);
/*  914 */                               out.write(62);
/*  915 */                               out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  916 */                               out.write("</option>\n\t");
/*      */                             }
/*  918 */                             out.write(10);
/*  919 */                             out.write(9);
/*  920 */                             if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!EnterpriseUtil.isAdminServer())) && (!"CLOUD".equalsIgnoreCase(com.adventnet.appmanager.util.Constants.getCategorytype()))) {
/*  921 */                               out.write("\n\t<!-- Windows Service action -->\n\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  922 */                               out.print(action_haid);
/*  923 */                               out.print(returnpath);
/*  924 */                               out.write(34);
/*  925 */                               out.write(62);
/*  926 */                               out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  927 */                               out.write("</option>\t\n\t");
/*      */                             }
/*  929 */                             out.write(10);
/*  930 */                             out.write(9);
/*  931 */                             out.write(32);
/*  932 */                             if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  933 */                               out.write("\n\t<!-- Sql job action -->\n\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  934 */                               out.print(action_haid);
/*  935 */                               out.print(returnpath);
/*  936 */                               out.write(34);
/*  937 */                               out.write(62);
/*  938 */                               out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  939 */                               out.write("</option>\n\t");
/*      */                             }
/*  941 */                             out.write(10);
/*  942 */                             out.write(9);
/*  943 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  944 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  948 */                         if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  949 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                         }
/*      */                         
/*  952 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  953 */                         out.write(10);
/*  954 */                         out.write(9);
/*  955 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  956 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  960 */                     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  961 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                     }
/*      */                     
/*  964 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  965 */                     out.write("\n\t</select>\n    </td>\n  </tr>\n</table>\n\n");
/*      */                     
/*  967 */                     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  968 */                     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/*  969 */                     _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  971 */                     _jspx_th_c_005fif_005f6.setTest("${param.global=='true'}");
/*  972 */                     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/*  973 */                     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                       for (;;) {
/*  975 */                         out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td valign=\"top\"> \n<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/*  976 */                         out.write("<!--$Id$-->\n\n\n\n");
/*  977 */                         if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                           return;
/*  979 */                         out.write("\n      \n\n");
/*      */                         
/*  981 */                         IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  982 */                         _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/*  983 */                         _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f6);
/*      */                         
/*  985 */                         _jspx_th_c_005fif_005f7.setTest("${(uri=='/jsp/CreateApplication.jsp')|| uri=='/admin/createapplication.do'||uri=='/admin/createapplication.do' ||(!empty param.wiz && !empty param.haid && (empty invalidhaid))||(param.method=='insert')}");
/*  986 */                         int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/*  987 */                         if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                           for (;;) {
/*  989 */                             out.write("\n\t  <tr>\n\t  <td class=\"tdindent\">\n");
/*  990 */                             if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/*  992 */                             out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">  \n  <tr> \n    <td width=\"2%\">");
/*      */                             
/*  994 */                             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  995 */                             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/*  996 */                             _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f7);
/*      */                             
/*  998 */                             _jspx_th_c_005fif_005f8.setTest("${uri=='/jsp/CreateApplication.jsp' || uri=='/admin/createapplicationwiz.do'||uri=='/admin/createapplication.do'}");
/*  999 */                             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 1000 */                             if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                               for (;;) {
/* 1002 */                                 out.write("\n    \t");
/*      */                                 
/* 1004 */                                 SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1005 */                                 _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 1006 */                                 _jspx_th_c_005fset_005f6.setParent(_jspx_th_c_005fif_005f8);
/*      */                                 
/* 1008 */                                 _jspx_th_c_005fset_005f6.setVar("wizimage");
/* 1009 */                                 int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 1010 */                                 if (_jspx_eval_c_005fset_005f6 != 0) {
/* 1011 */                                   if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1012 */                                     out = _jspx_page_context.pushBody();
/* 1013 */                                     _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 1014 */                                     _jspx_th_c_005fset_005f6.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 1017 */                                     out.write(" \n    \t<img src=\"/images/wiz_newbizapp_high.gif\" border=\"0\" align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1018 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1019 */                                     out.write(" </b></font>\n    \t");
/* 1020 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 1021 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 1024 */                                   if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1025 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 1028 */                                 if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 1029 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6); return;
/*      */                                 }
/*      */                                 
/* 1032 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 1033 */                                 out.write("\n    ");
/* 1034 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 1035 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1039 */                             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 1040 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                             }
/*      */                             
/* 1043 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 1044 */                             out.write("\n    ");
/*      */                             
/* 1046 */                             IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1047 */                             _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 1048 */                             _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f7);
/*      */                             
/* 1050 */                             _jspx_th_c_005fif_005f9.setTest("${uri!='/jsp/CreateApplication.jsp' && uri!='/admin/createapplicationwiz.do'&& uri!='/admin/createapplication.do'}");
/* 1051 */                             int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 1052 */                             if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                               for (;;) {
/* 1054 */                                 out.write("\n    \t");
/*      */                                 
/* 1056 */                                 SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1057 */                                 _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 1058 */                                 _jspx_th_c_005fset_005f7.setParent(_jspx_th_c_005fif_005f9);
/*      */                                 
/* 1060 */                                 _jspx_th_c_005fset_005f7.setVar("wizimage");
/* 1061 */                                 int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 1062 */                                 if (_jspx_eval_c_005fset_005f7 != 0) {
/* 1063 */                                   if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1064 */                                     out = _jspx_page_context.pushBody();
/* 1065 */                                     _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 1066 */                                     _jspx_th_c_005fset_005f7.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 1069 */                                     out.write("\n    \t<img src=\"/images/wiz_newbizapp_nor.gif\" border=0> <font family=\"verdana\" size=\"2\" color=\"#818181\">");
/* 1070 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1071 */                                     out.write("</font>  \t");
/* 1072 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 1073 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 1076 */                                   if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1077 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 1080 */                                 if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 1081 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                                 }
/*      */                                 
/* 1084 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 1085 */                                 out.write("\n    ");
/* 1086 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 1087 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1091 */                             if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 1092 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                             }
/*      */                             
/* 1095 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 1096 */                             out.write("      \n\t</td>\n    <td width=\"20%\">");
/* 1097 */                             if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1099 */                             out.write("</td>  \n   \n");
/*      */                             
/* 1101 */                             ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1102 */                             _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1103 */                             _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f7);
/* 1104 */                             int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1105 */                             if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                               for (;;) {
/* 1107 */                                 out.write("\n    ");
/*      */                                 
/* 1109 */                                 WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1110 */                                 _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1111 */                                 _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                 
/* 1113 */                                 _jspx_th_c_005fwhen_005f3.setTest("${( param.method=='configureHostDiscovery' || param.method=='associateMonitors'||param.method=='getMonitorForm'||param.method=='addResource')&& (empty showwiz3) && (empty UrlForm) }");
/* 1114 */                                 int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1115 */                                 if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                   for (;;) {
/* 1117 */                                     out.write("\n    \n\t\n\t\t\n\t\t");
/*      */                                     
/* 1119 */                                     SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1120 */                                     _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 1121 */                                     _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                                     
/* 1123 */                                     _jspx_th_c_005fset_005f8.setVar("wizimage");
/* 1124 */                                     int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 1125 */                                     if (_jspx_eval_c_005fset_005f8 != 0) {
/* 1126 */                                       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1127 */                                         out = _jspx_page_context.pushBody();
/* 1128 */                                         _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 1129 */                                         _jspx_th_c_005fset_005f8.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 1132 */                                         out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1133 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1134 */                                         out.write(" </b></font>\n    \t");
/* 1135 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 1136 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 1139 */                                       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1140 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 1143 */                                     if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 1144 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                                     }
/*      */                                     
/* 1147 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 1148 */                                     out.write("\n   ");
/* 1149 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1150 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1154 */                                 if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1155 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                 }
/*      */                                 
/* 1158 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1159 */                                 out.write("\n   ");
/*      */                                 
/* 1161 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1162 */                                 _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1163 */                                 _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 1164 */                                 int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1165 */                                 if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                   for (;;) {
/* 1167 */                                     out.write("  \n    \t\n\t\t");
/*      */                                     
/* 1169 */                                     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1170 */                                     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 1171 */                                     _jspx_th_c_005fset_005f9.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                                     
/* 1173 */                                     _jspx_th_c_005fset_005f9.setVar("wizimage");
/* 1174 */                                     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 1175 */                                     if (_jspx_eval_c_005fset_005f9 != 0) {
/* 1176 */                                       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1177 */                                         out = _jspx_page_context.pushBody();
/* 1178 */                                         _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 1179 */                                         _jspx_th_c_005fset_005f9.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 1182 */                                         out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1183 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1184 */                                         out.write(" </font>\n    \t");
/* 1185 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 1186 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 1189 */                                       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1190 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 1193 */                                     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 1194 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                                     }
/*      */                                     
/* 1197 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 1198 */                                     out.write("\n\t\n\t\t\n   ");
/* 1199 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1200 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1204 */                                 if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1205 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                                 }
/*      */                                 
/* 1208 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1209 */                                 out.write(10);
/* 1210 */                                 out.write(32);
/* 1211 */                                 out.write(32);
/* 1212 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1213 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1217 */                             if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1218 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                             }
/*      */                             
/* 1221 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1222 */                             out.write(" \n\n    \n     <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"20%\">\n    ");
/* 1223 */                             if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1225 */                             out.write("\n    \t");
/* 1226 */                             if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1228 */                             out.write("\n    \t\n    \t");
/* 1229 */                             if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1231 */                             out.write("\n    \t</td>    \t\n    \t<!-- ############################################# check for third tab #####################################  -->\n       ");
/*      */                             
/* 1233 */                             ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1234 */                             _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1235 */                             _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f7);
/* 1236 */                             int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1237 */                             if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                               for (;;) {
/* 1239 */                                 out.write("\n       ");
/*      */                                 
/* 1241 */                                 WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1242 */                                 _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1243 */                                 _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                 
/* 1245 */                                 _jspx_th_c_005fwhen_005f4.setTest("${(param.method=='reloadHostDiscoveryForm'|| param.method=='getMonitorForm'||param.method=='addResource'|| (!empty showwiz3) || (!empty UrlForm) ) && (param.method!='getHAProfiles') }");
/* 1246 */                                 int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1247 */                                 if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                   for (;;) {
/* 1249 */                                     out.write("\n   \n   \t    \t");
/*      */                                     
/* 1251 */                                     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1252 */                                     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 1253 */                                     _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                                     
/* 1255 */                                     _jspx_th_c_005fset_005f10.setVar("wizimage");
/* 1256 */                                     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 1257 */                                     if (_jspx_eval_c_005fset_005f10 != 0) {
/* 1258 */                                       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1259 */                                         out = _jspx_page_context.pushBody();
/* 1260 */                                         _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 1261 */                                         _jspx_th_c_005fset_005f10.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 1264 */                                         out.write("\n   \t    \t<img src=\"/images/new_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b> ");
/* 1265 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1266 */                                         out.write(" </b></font>\n   \t    \t");
/* 1267 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 1268 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 1271 */                                       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1272 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 1275 */                                     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 1276 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                                     }
/*      */                                     
/* 1279 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 1280 */                                     out.write("\n       ");
/* 1281 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1282 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1286 */                                 if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1287 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                                 }
/*      */                                 
/* 1290 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1291 */                                 out.write("\n        ");
/*      */                                 
/* 1293 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1294 */                                 _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1295 */                                 _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1296 */                                 int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1297 */                                 if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                   for (;;) {
/* 1299 */                                     out.write("  \n   \t    \t");
/*      */                                     
/* 1301 */                                     SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1302 */                                     _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 1303 */                                     _jspx_th_c_005fset_005f11.setParent(_jspx_th_c_005fotherwise_005f4);
/*      */                                     
/* 1305 */                                     _jspx_th_c_005fset_005f11.setVar("wizimage");
/* 1306 */                                     int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 1307 */                                     if (_jspx_eval_c_005fset_005f11 != 0) {
/* 1308 */                                       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1309 */                                         out = _jspx_page_context.pushBody();
/* 1310 */                                         _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 1311 */                                         _jspx_th_c_005fset_005f11.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 1314 */                                         out.write("\n\t\t   \t    \t<img src=\"/images/new_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1315 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1316 */                                         out.write(" </font>\n   \t    \t");
/* 1317 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 1318 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 1321 */                                       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1322 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 1325 */                                     if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 1326 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11); return;
/*      */                                     }
/*      */                                     
/* 1329 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 1330 */                                     out.write("\n   \t");
/* 1331 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1332 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1336 */                                 if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1337 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                                 }
/*      */                                 
/* 1340 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1341 */                                 out.write(10);
/* 1342 */                                 out.write(32);
/* 1343 */                                 out.write(32);
/* 1344 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1345 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1349 */                             if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1350 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                             }
/*      */                             
/* 1353 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1354 */                             out.write(" \n\n        <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n       <td width=\"18%\">\n       ");
/* 1355 */                             if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1357 */                             out.write("\n       ");
/* 1358 */                             if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1360 */                             out.write("\n       ");
/* 1361 */                             out.write("\n       \t");
/* 1362 */                             if (_jspx_meth_c_005fif_005f13(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1364 */                             out.write("\n    \t</td>\n   \n  <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"17%\">\n\t");
/*      */                             
/* 1366 */                             IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1367 */                             _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 1368 */                             _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f7);
/*      */                             
/* 1370 */                             _jspx_th_c_005fif_005f14.setTest("${param.method=='getHAProfiles'}");
/* 1371 */                             int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 1372 */                             if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                               for (;;) {
/* 1374 */                                 out.write(10);
/* 1375 */                                 out.write(9);
/*      */                                 
/* 1377 */                                 SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1378 */                                 _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 1379 */                                 _jspx_th_c_005fset_005f12.setParent(_jspx_th_c_005fif_005f14);
/*      */                                 
/* 1381 */                                 _jspx_th_c_005fset_005f12.setVar("wizimage");
/* 1382 */                                 int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 1383 */                                 if (_jspx_eval_c_005fset_005f12 != 0) {
/* 1384 */                                   if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1385 */                                     out = _jspx_page_context.pushBody();
/* 1386 */                                     _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 1387 */                                     _jspx_th_c_005fset_005f12.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 1390 */                                     out.write("\n\t\t<img src=\"/images/wiz_configurealerts_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b>");
/* 1391 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1392 */                                     out.write(" </b></font>\n    \t");
/* 1393 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 1394 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 1397 */                                   if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1398 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 1401 */                                 if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 1402 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12); return;
/*      */                                 }
/*      */                                 
/* 1405 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 1406 */                                 out.write(10);
/* 1407 */                                 out.write(9);
/* 1408 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 1409 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1413 */                             if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 1414 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                             }
/*      */                             
/* 1417 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 1418 */                             out.write(10);
/* 1419 */                             out.write(9);
/*      */                             
/* 1421 */                             IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1422 */                             _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 1423 */                             _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f7);
/*      */                             
/* 1425 */                             _jspx_th_c_005fif_005f15.setTest("${param.method!='getHAProfiles'}");
/* 1426 */                             int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 1427 */                             if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                               for (;;) {
/* 1429 */                                 out.write(10);
/* 1430 */                                 out.write(9);
/*      */                                 
/* 1432 */                                 SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1433 */                                 _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 1434 */                                 _jspx_th_c_005fset_005f13.setParent(_jspx_th_c_005fif_005f15);
/*      */                                 
/* 1436 */                                 _jspx_th_c_005fset_005f13.setVar("wizimage");
/* 1437 */                                 int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 1438 */                                 if (_jspx_eval_c_005fset_005f13 != 0) {
/* 1439 */                                   if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1440 */                                     out = _jspx_page_context.pushBody();
/* 1441 */                                     _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 1442 */                                     _jspx_th_c_005fset_005f13.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 1445 */                                     out.write("\n\t\t<img src=\"/images/wiz_configurealerts_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1446 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1447 */                                     out.write(" </font>\n    \t");
/* 1448 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 1449 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 1452 */                                   if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1453 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 1456 */                                 if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 1457 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13); return;
/*      */                                 }
/*      */                                 
/* 1460 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 1461 */                                 out.write("\n\t\n\t");
/* 1462 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 1463 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1467 */                             if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 1468 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                             }
/*      */                             
/* 1471 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 1472 */                             out.write(10);
/* 1473 */                             if (_jspx_meth_c_005fif_005f16(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1475 */                             out.write("   \n ");
/* 1476 */                             if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1478 */                             out.write(10);
/* 1479 */                             out.write(32);
/* 1480 */                             out.write(10);
/* 1481 */                             if (_jspx_meth_c_005fif_005f17(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1483 */                             out.write("   \n    </td>\n    \n    <td width=\"2%\" rowspan=\"2\" valign=\"bottom\" align=\"right\"><img src=\"/images/wiz_endicon.gif\" width=\"33\" height=\"36\"></td>\n  </tr>\n  <tr background=\"/images/wiz_bg_graylind.gif\"> \n    <td><img src=\"/images/wiz_startimage.gif\" width=\"18\" height=\"16\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n   \n   <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n \n");
/* 1484 */                             out.write("  </tr>\n\n</table>\n</td></tr>\n");
/* 1485 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1486 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1490 */                         if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1491 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                         }
/*      */                         
/* 1494 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1495 */                         out.write(10);
/* 1496 */                         out.write(10);
/* 1497 */                         if (request.getAttribute("EmailForm") == null) {
/* 1498 */                           out.write(10);
/*      */                           
/* 1500 */                           MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1501 */                           _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 1502 */                           _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                           
/* 1504 */                           _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                           
/* 1506 */                           _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 1507 */                           int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 1508 */                           if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 1509 */                             String msg = null;
/* 1510 */                             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1511 */                               out = _jspx_page_context.pushBody();
/* 1512 */                               _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 1513 */                               _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                             }
/* 1515 */                             msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                             for (;;) {
/* 1517 */                               out.write(" \n<tr> \n  <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 1518 */                               if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                                 return;
/* 1520 */                               out.write("</td>\n\t  </tr>\n\t</table>\n\t<br></td>\n</tr>\n");
/* 1521 */                               int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 1522 */                               msg = (String)_jspx_page_context.findAttribute("msg");
/* 1523 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1526 */                             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1527 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1530 */                           if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 1531 */                             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                           }
/*      */                           
/* 1534 */                           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */                         }
/* 1536 */                         out.write(32);
/*      */                         
/* 1538 */                         org.apache.struts.taglib.logic.MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (org.apache.struts.taglib.logic.MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(org.apache.struts.taglib.logic.MessagesPresentTag.class);
/* 1539 */                         _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 1540 */                         _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                         
/* 1542 */                         _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 1543 */                         int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 1544 */                         if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                           for (;;) {
/* 1546 */                             out.write(" \n<tr> \n  <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\"> ");
/*      */                             
/* 1548 */                             MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1549 */                             _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 1550 */                             _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                             
/* 1552 */                             _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                             
/* 1554 */                             _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 1555 */                             int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 1556 */                             if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 1557 */                               String msg = null;
/* 1558 */                               if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1559 */                                 out = _jspx_page_context.pushBody();
/* 1560 */                                 _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 1561 */                                 _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                               }
/* 1563 */                               msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                               for (;;) {
/* 1565 */                                 out.write("\n\t  ");
/* 1566 */                                 if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                                   return;
/* 1568 */                                 out.write("<br>\n\t  ");
/* 1569 */                                 int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 1570 */                                 msg = (String)_jspx_page_context.findAttribute("msg");
/* 1571 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1574 */                               if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1575 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1578 */                             if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 1579 */                               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                             }
/*      */                             
/* 1582 */                             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 1583 */                             out.write(" </td>\n\t  </tr>\n\t</table></td>\n</tr>\n");
/* 1584 */                             int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 1585 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1589 */                         if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 1590 */                           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                         }
/*      */                         
/* 1593 */                         this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 1594 */                         out.write("\n</table>\n<script>\n\tobject=location.href;\n\t\n\tif(document.AMActionForm!=null)\n\t{\t  \n\t  if(object.match(\"EMailActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if(object.match(\"SMSActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if(object.match(\"ExecProgramActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=11\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=12\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\t  \n\t  else if(object.match(\"showLogTicket\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=6;\n\t  }\n\t  else if(object.match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=5;\n\t  }\t\n\t  else if(object.match(\"jre\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=7;\n");
/* 1595 */                         out.write("\t  }\n\t  else if(object.match(\"showVMAction\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=8;\n\t  }\n\t  else if(object.match(\"amazon\")!=null)\n\t  {\n\t\t    document.AMActionForm.actions.selectedIndex=9;\n\t }\t  \n\t}\n\telse if(document.MBeanOperationActionForm!=null)\n\t{\n\t  document.MBeanOperationActionForm.actions.selectedIndex=5;\t  \n\t}\n</script>\n</td>\n</tr>\n</table>\n");
/* 1596 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1597 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1601 */                     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1602 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                     }
/*      */                     
/* 1605 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1606 */                     out.write(10);
/* 1607 */                     out.write(10);
/* 1608 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1609 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1613 */                 if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1614 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                 }
/*      */                 
/* 1617 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1618 */                 out.write(10);
/* 1619 */                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1620 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1624 */             if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1625 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */             }
/*      */             
/* 1628 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1629 */             out.write("\n<input type=\"hidden\" name=\"method\" value=\"mailServerConfiguration\" >\n");
/* 1630 */             if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1632 */             out.write(10);
/* 1633 */             out.write(10);
/* 1634 */             if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1636 */             out.write(10);
/* 1637 */             if (_jspx_meth_am_005fhiddenparam_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1639 */             out.write(10);
/* 1640 */             if (_jspx_meth_am_005fhiddenparam_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1642 */             out.write(10);
/* 1643 */             if (_jspx_meth_am_005fhiddenparam_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1645 */             out.write(10);
/* 1646 */             if (_jspx_meth_am_005fhiddenparam_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1648 */             out.write(10);
/* 1649 */             if (_jspx_meth_am_005fhiddenparam_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1651 */             out.write(10);
/* 1652 */             if (_jspx_meth_am_005fhiddenparam_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1654 */             out.write(10);
/* 1655 */             if (_jspx_meth_am_005fhiddenparam_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1657 */             out.write(10);
/* 1658 */             if (_jspx_meth_am_005fhiddenparam_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1660 */             out.write(10);
/*      */             
/* 1662 */             String showSdeskConfiguration3 = (String)request.getAttribute("showSdeskConfiguration2");
/* 1663 */             if (showSdeskConfiguration3 != null)
/*      */             {
/*      */ 
/*      */ 
/* 1667 */               out.write("\n\t<input type=\"hidden\" name=\"checkMailServer\" value=\"showSdeskConfiguration4\" >\n\t");
/*      */             }
/*      */             
/* 1670 */             String showSdeskLogTicket3 = (String)request.getAttribute("showSdeskLogTicket2");
/* 1671 */             if (showSdeskLogTicket3 != null)
/*      */             {
/*      */ 
/* 1674 */               out.write("\n\t\t<input type=\"hidden\" name=\"checkMailForTicket\" value=\"showSdeskLogTicket4\" >\n\t");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 1679 */             out.write("\n        ");
/*      */             
/* 1681 */             IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1682 */             _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 1683 */             _jspx_th_c_005fif_005f18.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 1685 */             _jspx_th_c_005fif_005f18.setTest("${!empty showerror}");
/* 1686 */             int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 1687 */             if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */               for (;;) {
/* 1689 */                 out.write("\n        ");
/*      */                 
/* 1691 */                 MessagesTag _jspx_th_html_005fmessages_005f2 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1692 */                 _jspx_th_html_005fmessages_005f2.setPageContext(_jspx_page_context);
/* 1693 */                 _jspx_th_html_005fmessages_005f2.setParent(_jspx_th_c_005fif_005f18);
/*      */                 
/* 1695 */                 _jspx_th_html_005fmessages_005f2.setId("msg");
/*      */                 
/* 1697 */                 _jspx_th_html_005fmessages_005f2.setMessage("false");
/* 1698 */                 int _jspx_eval_html_005fmessages_005f2 = _jspx_th_html_005fmessages_005f2.doStartTag();
/* 1699 */                 if (_jspx_eval_html_005fmessages_005f2 != 0) {
/* 1700 */                   String msg = null;
/* 1701 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1702 */                     out = _jspx_page_context.pushBody();
/* 1703 */                     _jspx_th_html_005fmessages_005f2.setBodyContent((BodyContent)out);
/* 1704 */                     _jspx_th_html_005fmessages_005f2.doInitBody();
/*      */                   }
/* 1706 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/* 1708 */                     out.write("\n        ");
/* 1709 */                     if (_jspx_meth_c_005fif_005f19(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                       return;
/* 1711 */                     out.write("\n\n              ");
/* 1712 */                     if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                       return;
/* 1714 */                     out.write("<br>\n\n        ");
/* 1715 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f2.doAfterBody();
/* 1716 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/* 1717 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1720 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1721 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1724 */                 if (_jspx_th_html_005fmessages_005f2.doEndTag() == 5) {
/* 1725 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2); return;
/*      */                 }
/*      */                 
/* 1728 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2);
/* 1729 */                 out.write("\n        ");
/* 1730 */                 if (_jspx_meth_c_005fif_005f20(_jspx_th_c_005fif_005f18, _jspx_page_context))
/*      */                   return;
/* 1732 */                 out.write("\n        <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t              <tr>\n\t\t      \t\t<td  height=\"10\" ><img src=\"../images/spacer.gif\" ></td>\n\t\t</tr>\n</table>\n        ");
/* 1733 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 1734 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1738 */             if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 1739 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */             }
/*      */             
/* 1742 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 1743 */             out.write("\n\n\n\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n  \t <td width=\"2%\" class=\"tableheading\" ><img src=\"/images/email.png\"></td>\n \t       <td width=\"72%\" height=\"31\" class=\"tableheading1\" ><div id=\"topheading\">");
/* 1744 */             out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1745 */             out.write("</div></td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbdarkborder\">\n<tr>\n  <td>\n  \t<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"lrborder\">\n  \t\t<tr>\n  \t\t\t<td colspan=\"2\"  class=\"plainheading1\">");
/* 1746 */             out.print(FormatUtil.getString("am.webclient.mailsettings.primary.text"));
/* 1747 */             out.write("</td>\n  \t\t</tr>\n  \t</table>\n  </td>\n</tr>\n\n<!-- primary mail server configuration starts -->\n<tr>\n\t<td width=\"100%\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"lrborder\">\n\t\t<tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\" >");
/* 1748 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpservername"));
/* 1749 */             out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td width=\"75%\" >");
/* 1750 */             if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1752 */             out.write("\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\"  >");
/* 1753 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpservername"));
/* 1754 */             out.write(32);
/* 1755 */             out.print(FormatUtil.getString("am.webclient.common.port.text"));
/* 1756 */             out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td width=\"75%\" >");
/* 1757 */             if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1759 */             out.write("\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\" >");
/* 1760 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.email.text"));
/* 1761 */             out.write("</td>\n\t\t\t<td width=\"75%\">");
/* 1762 */             if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1764 */             out.write("&nbsp;&nbsp;&nbsp;<span class=\"footer\">");
/* 1765 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.email.help.text"));
/* 1766 */             out.write("</span></td>\n\t\t</tr>\n\n\t\t<tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">&nbsp;</td>\n            <td width=\"75%\"><input name=\"SMTPServerAuth\" onclick=\"showPrimaryUserPassword()\" type=\"checkbox\">\n            ");
/* 1767 */             out.print(FormatUtil.getString("am.webclient.smtpserver.authMessage"));
/* 1768 */             out.write("</td>\n  \t\t</tr>\n\t\t<tr id=\"mailConfig\" style=\"display:none\">\n\t\t\t<td colspan=\"2\">\n                <table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t\t\t<tr>\n                        <td class=\"bodytext label-align\" width=\"25%\">");
/* 1769 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverusername"));
/* 1770 */             out.write("</td>\n                        <td width=\"75%\"><input type=\"text\" name=\"SMTPServerUserName\" size=\"30\" class=\"formtext default\" maxlength=\"50\" autocomplete=\"off\" value=\"");
/* 1771 */             if (_jspx_meth_bean_005fwrite_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1773 */             out.write("\"/></td>\n\t\t\t\t\t</tr>\n\t\t\t\t<tr>\n                        <td class=\"bodytext label-align\" width=\"25%\">");
/* 1774 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverpassword"));
/* 1775 */             out.write("</td>\n                        <td width=\"75%\"><input type=\"password\" name=\"SMTPServerPassword\" size=\"30\" class=\"formtext default\" maxlength=\"50\" autocomplete=\"off\" value=\"\"/></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t\n\t\t<tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">&nbsp;</td>\n            <td width=\"75%\">");
/* 1776 */             if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1778 */             out.write("\n            ");
/* 1779 */             out.print(FormatUtil.getString("TLS Authentication Enabled"));
/* 1780 */             out.write("</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">&nbsp;</td>\n\t\t\t<td width=\"75%\">");
/* 1781 */             if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1783 */             out.write("\n            ");
/* 1784 */             out.print(FormatUtil.getString("am.webclient.admin.mailSettings.sslEnabled"));
/* 1785 */             out.write("</td>\n\t\t</tr>\n\t\t\t</td>\n\t\t</tr>\n\t\t<!-- primary mail server configuration ends -->\n\n\n\t\t<!-- seconday mail server configuration starts -->\n\t\t<tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">&nbsp;</td>\n\t\t\t<td width=\"75%\">");
/* 1786 */             if (_jspx_meth_html_005fcheckbox_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1788 */             out.write("\n            ");
/* 1789 */             out.print(FormatUtil.getString("am.webclient.mailsettings.configuremailsecserver.text"));
/* 1790 */             out.write("</td>\n\t\t    </tr>\n\n\t\t<tr>\n\t\t\t<td colspan=\"2\" class=\"cellpadd-none\">\n\t\t\t<div id=\"secserver\" style=\"display:none\">\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" >\n\t\t\t  \t\t<tr>\n\t\t\t  \t\t\t<td class=\"plainheading1\">");
/* 1791 */             out.print(FormatUtil.getString("am.webclient.mailsettings.secondary.text"));
/* 1792 */             out.write("</td>\n\t\t\t  \t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t<table align=\"left\" width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t\t\t\t<tr >\n\t\t\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\" >");
/* 1793 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpservername"));
/* 1794 */             out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t\t\t\t<td width=\"75%\" colspan=\"2\">");
/* 1795 */             if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1797 */             out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 1798 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpservername"));
/* 1799 */             out.write(32);
/* 1800 */             out.print(FormatUtil.getString("am.webclient.common.port.text"));
/* 1801 */             out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t\t\t\t<td width=\"75%\" colspan=\"2\">");
/* 1802 */             if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1804 */             out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\" >");
/* 1805 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.email.text"));
/* 1806 */             out.write("</td>\n\t\t\t\t\t\t<td width=\"33%\">");
/* 1807 */             if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1809 */             out.write("</td>\n                        <td width=\"42%\"><span class=\"footer\">");
/* 1810 */             out.print(FormatUtil.getString("am.webclient.smtpsecserver.email.message"));
/* 1811 */             out.write("</span></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr >\n\t\t\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\">&nbsp;</td>\n\t\t\t\t\t\t<td  width=\"75%\" colspan=\"2\"><input name=\"SMTPsecServerAuth\" onclick=\"showSecondaryUserPassword()\" type=\"checkbox\">\n                        ");
/* 1812 */             out.print(FormatUtil.getString("am.webclient.smtpserver.authMessage"));
/* 1813 */             out.write("</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t<tr id=\"mailConfig1\" style=\"display:none\" >\n\t\t\t\t\t\t<td colspan=\"3\">\n                            <table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t\t\t\t\t\t<tr>\n                                    <td class=\"bodytext label-align\" width=\"25%\">");
/* 1814 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverusername"));
/* 1815 */             out.write("</td>\n                                    <td width=\"75%\">");
/* 1816 */             if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1818 */             out.write("</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n                                    <td class=\"bodytext label-align\" width=\"25%\">");
/* 1819 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverpassword"));
/* 1820 */             out.write("</td>\n                                    <td width=\"75%\">");
/* 1821 */             if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1823 */             out.write("</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n                    \n\t\t\t\t\t<tr>\n                    \t<td class=\"bodytext label-align\" width=\"25%\">&nbsp;</td>\n                        <td width=\"75%\" colspan=\"2\">");
/* 1824 */             if (_jspx_meth_html_005fcheckbox_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1826 */             out.write("\n                        ");
/* 1827 */             out.print(FormatUtil.getString("TLS Authentication Enabled"));
/* 1828 */             out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n                    \t<td class=\"bodytext label-align\" width=\"25%\">&nbsp;</td>\n\t\t\t\t\t\t<td width=\"75%\" colspan=\"2\" class=\"bodytext\">");
/* 1829 */             if (_jspx_meth_html_005fcheckbox_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1831 */             out.write("\n                        ");
/* 1832 */             out.print(FormatUtil.getString("am.webclient.admin.mailSettings.sslEnabled"));
/* 1833 */             out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t</div>\n\t\t\t</td>\n\t\t</tr>\n\t\t");
/*      */             
/* 1835 */             IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1836 */             _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 1837 */             _jspx_th_c_005fif_005f21.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 1839 */             _jspx_th_c_005fif_005f21.setTest("${isAdminServer }");
/* 1840 */             int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 1841 */             if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */               for (;;) {
/* 1843 */                 out.write("\n\t\t\t<tr><td class=\"bodytext label-align\" width=\"25%\">&nbsp;</td>\n\t\t\t<td width=\"75%\" >\n\t\t\t\t");
/* 1844 */                 if (_jspx_meth_html_005fcheckbox_005f5(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                   return;
/* 1846 */                 out.print(FormatUtil.getString("am.webclient.mailsettings.managedserver.text"));
/* 1847 */                 out.write("\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t\n\t\t");
/* 1848 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 1849 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1853 */             if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 1854 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */             }
/*      */             
/* 1857 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 1858 */             out.write("\n\t\t<!-- secondary mail server configuration ends -->\n\t\t");
/* 1859 */             if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {}
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1871 */             out.write("\n\t</table>\n\t</td>\n</tr>\n\n\n\n\n</table>\n\n  ");
/*      */             
/* 1873 */             String save = FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverconfig.savebutton.text");
/* 1874 */             String reset = FormatUtil.getString("am.webclient.global.Reset.text");
/* 1875 */             String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/* 1876 */             String close = FormatUtil.getString("am.webclient.common.close.text");
/*      */             
/* 1878 */             out.write("\n\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n    <tr>\n     <td class=\"tablebottom\" width=\"25%\"></td>\n      <td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 1879 */             out.print(save);
/* 1880 */             out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"fnFormSubmit();\">\n        &nbsp;<input type=\"reset\" name=\"reset\" value=\"");
/* 1881 */             out.print(reset);
/* 1882 */             out.write("\"  class=\"buttons btn_reset\">  &nbsp;\n\n        ");
/*      */             
/* 1884 */             ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1885 */             _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 1886 */             _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_html_005fform_005f0);
/* 1887 */             int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 1888 */             if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */               for (;;) {
/* 1890 */                 out.write("\n        ");
/*      */                 
/* 1892 */                 WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1893 */                 _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 1894 */                 _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                 
/* 1896 */                 _jspx_th_c_005fwhen_005f5.setTest("${empty param.popup}");
/* 1897 */                 int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 1898 */                 if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                   for (;;) {
/* 1900 */                     out.write("\n        <input name=\"button2\" value=\"");
/* 1901 */                     out.print(cancel);
/* 1902 */                     out.write("\" type=\"button\" class=\"buttons btn_link\" onClick=\"javascript:history.back();\"></td>\n        ");
/* 1903 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 1904 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1908 */                 if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 1909 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                 }
/*      */                 
/* 1912 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1913 */                 out.write("\n        ");
/*      */                 
/* 1915 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1916 */                 _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 1917 */                 _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 1918 */                 int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 1919 */                 if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                   for (;;) {
/* 1921 */                     out.write("\n        <input name=\"button2\" value=\"");
/* 1922 */                     out.print(close);
/* 1923 */                     out.write("\" type=\"button\" class=\"buttons btn_link\" onClick=\"javascript:window.parent.close();\"></td>\n        ");
/* 1924 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 1925 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1929 */                 if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 1930 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                 }
/*      */                 
/* 1933 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 1934 */                 out.write("\n        ");
/* 1935 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 1936 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1940 */             if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 1941 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */             }
/*      */             
/* 1944 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 1945 */             out.write("\n    </tr>\n</table>\n");
/* 1946 */             int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 1947 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1951 */         if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 1952 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */         }
/*      */         else
/* 1955 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/* 1957 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1958 */         out = _jspx_out;
/* 1959 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1960 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 1961 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1964 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1970 */     PageContext pageContext = _jspx_page_context;
/* 1971 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1973 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1974 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1975 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 1977 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 1978 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1979 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 1981 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 1982 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1983 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1987 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1988 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1989 */       return true;
/*      */     }
/* 1991 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1992 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1997 */     PageContext pageContext = _jspx_page_context;
/* 1998 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2000 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2001 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2002 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2004 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2006 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2007 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2008 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2009 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2010 */       return true;
/*      */     }
/* 2012 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2013 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2018 */     PageContext pageContext = _jspx_page_context;
/* 2019 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2021 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2022 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2023 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 2025 */     _jspx_th_c_005fif_005f1.setTest("${globalconfig['mailserverconfigured'] != 'true'}");
/* 2026 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2027 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2029 */         out.write("\n\t\t\tmyOnLoad();\n\t\t");
/* 2030 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2031 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2035 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2036 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2037 */       return true;
/*      */     }
/* 2039 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2045 */     PageContext pageContext = _jspx_page_context;
/* 2046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2048 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2049 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2050 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 2052 */     _jspx_th_c_005fif_005f2.setTest("${globalconfig['mailserverconfigured'] != 'true' && param.checkForMailSetting eq 'true'}");
/* 2053 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2054 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 2056 */         out.write("\n\t\t\t\tmyOnLoad();\n\t\t\t");
/* 2057 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2058 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2062 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2063 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2064 */       return true;
/*      */     }
/* 2066 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2072 */     PageContext pageContext = _jspx_page_context;
/* 2073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2075 */     org.apache.taglibs.standard.tag.common.core.CatchTag _jspx_th_c_005fcatch_005f0 = (org.apache.taglibs.standard.tag.common.core.CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(org.apache.taglibs.standard.tag.common.core.CatchTag.class);
/* 2076 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 2077 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2079 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 2080 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 2082 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 2083 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 2085 */           out.write(" \n      ");
/* 2086 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 2087 */             return true;
/* 2088 */           out.write(32);
/* 2089 */           out.write(10);
/* 2090 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 2091 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2095 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 2096 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2099 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 2100 */         out = _jspx_page_context.popBody(); }
/* 2101 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 2103 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 2104 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 2106 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 2111 */     PageContext pageContext = _jspx_page_context;
/* 2112 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2114 */     org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag.class);
/* 2115 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 2116 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 2118 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 2120 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 2121 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 2122 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 2123 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2124 */       return true;
/*      */     }
/* 2126 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2127 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2132 */     PageContext pageContext = _jspx_page_context;
/* 2133 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2135 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 2136 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 2137 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2139 */     _jspx_th_c_005fset_005f5.setVar("wiz");
/*      */     
/* 2141 */     _jspx_th_c_005fset_005f5.setValue("${param.wiz}");
/*      */     
/* 2143 */     _jspx_th_c_005fset_005f5.setScope("request");
/* 2144 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 2145 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 2146 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 2147 */       return true;
/*      */     }
/* 2149 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 2150 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2155 */     PageContext pageContext = _jspx_page_context;
/* 2156 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2158 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2159 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2160 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2162 */     _jspx_th_c_005fout_005f1.setValue("${wizimage}");
/*      */     
/* 2164 */     _jspx_th_c_005fout_005f1.setEscapeXml("false");
/* 2165 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2166 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2167 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2168 */       return true;
/*      */     }
/* 2170 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2171 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2176 */     PageContext pageContext = _jspx_page_context;
/* 2177 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2179 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2180 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2181 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2183 */     _jspx_th_c_005fif_005f10.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 2184 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2185 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 2187 */         out.write("\n    <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 2188 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 2189 */           return true;
/* 2190 */         out.write("&wiz=true\">\n    ");
/* 2191 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2192 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2196 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2197 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2198 */       return true;
/*      */     }
/* 2200 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2201 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2206 */     PageContext pageContext = _jspx_page_context;
/* 2207 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2209 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2210 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2211 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 2213 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 2214 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2215 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2216 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2217 */       return true;
/*      */     }
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2220 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2225 */     PageContext pageContext = _jspx_page_context;
/* 2226 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2228 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2229 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2230 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2232 */     _jspx_th_c_005fout_005f3.setValue("${wizimage}");
/*      */     
/* 2234 */     _jspx_th_c_005fout_005f3.setEscapeXml("false");
/* 2235 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2236 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2237 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2238 */       return true;
/*      */     }
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2241 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2246 */     PageContext pageContext = _jspx_page_context;
/* 2247 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2249 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2250 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2251 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2253 */     _jspx_th_c_005fif_005f11.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 2254 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2255 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 2257 */         out.write("\n    \t</a>\n    \t");
/* 2258 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2259 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2263 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2264 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2265 */       return true;
/*      */     }
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2268 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2273 */     PageContext pageContext = _jspx_page_context;
/* 2274 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2276 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2277 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2278 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2280 */     _jspx_th_c_005fif_005f12.setTest("${wizimage=='/images/new_high.gif'}");
/* 2281 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2282 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 2284 */         out.write("\n       <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 2285 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 2286 */           return true;
/* 2287 */         out.write("&wiz=true\">\n       ");
/* 2288 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 2289 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2293 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 2294 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2295 */       return true;
/*      */     }
/* 2297 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2298 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2303 */     PageContext pageContext = _jspx_page_context;
/* 2304 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2306 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2307 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2308 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 2310 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 2311 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2312 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2313 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2314 */       return true;
/*      */     }
/* 2316 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2317 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2322 */     PageContext pageContext = _jspx_page_context;
/* 2323 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2325 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2326 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2327 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2329 */     _jspx_th_c_005fout_005f5.setValue("${wizimage}");
/*      */     
/* 2331 */     _jspx_th_c_005fout_005f5.setEscapeXml("false");
/* 2332 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2333 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2334 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2335 */       return true;
/*      */     }
/* 2337 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2338 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2343 */     PageContext pageContext = _jspx_page_context;
/* 2344 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2346 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2347 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 2348 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2350 */     _jspx_th_c_005fif_005f13.setTest("${wizimage=='/images/new_high.gif'}");
/* 2351 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 2352 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 2354 */         out.write("\n       \t</a>\n       \t");
/* 2355 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 2356 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2360 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 2361 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 2362 */       return true;
/*      */     }
/* 2364 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 2365 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2370 */     PageContext pageContext = _jspx_page_context;
/* 2371 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2373 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2374 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 2375 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2377 */     _jspx_th_c_005fif_005f16.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 2378 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 2379 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 2381 */         out.write("\t\n    <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 2382 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 2383 */           return true;
/* 2384 */         out.write("&wiz=true\">\n ");
/* 2385 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 2386 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2390 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 2391 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2392 */       return true;
/*      */     }
/* 2394 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2395 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2400 */     PageContext pageContext = _jspx_page_context;
/* 2401 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2403 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2404 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2405 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 2407 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 2408 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2409 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2410 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2411 */       return true;
/*      */     }
/* 2413 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2414 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2419 */     PageContext pageContext = _jspx_page_context;
/* 2420 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2422 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2423 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2424 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2426 */     _jspx_th_c_005fout_005f7.setValue("${wizimage}");
/*      */     
/* 2428 */     _jspx_th_c_005fout_005f7.setEscapeXml("false");
/* 2429 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2430 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2431 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2432 */       return true;
/*      */     }
/* 2434 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2440 */     PageContext pageContext = _jspx_page_context;
/* 2441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2443 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2444 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 2445 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2447 */     _jspx_th_c_005fif_005f17.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 2448 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 2449 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 2451 */         out.write("\t    \n    </a>\n ");
/* 2452 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 2453 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2457 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 2458 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 2459 */       return true;
/*      */     }
/* 2461 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 2462 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2467 */     PageContext pageContext = _jspx_page_context;
/* 2468 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2470 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2471 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2472 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 2474 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 2476 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 2477 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2478 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2479 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2480 */       return true;
/*      */     }
/* 2482 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2483 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2488 */     PageContext pageContext = _jspx_page_context;
/* 2489 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2491 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2492 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 2493 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 2495 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 2497 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 2498 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 2499 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 2500 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2501 */       return true;
/*      */     }
/* 2503 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2504 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2509 */     PageContext pageContext = _jspx_page_context;
/* 2510 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2512 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2513 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 2514 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2516 */     _jspx_th_am_005fhiddenparam_005f0.setName("mailserverredirecturl");
/* 2517 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 2518 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 2519 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 2520 */       return true;
/*      */     }
/* 2522 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 2523 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2528 */     PageContext pageContext = _jspx_page_context;
/* 2529 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2531 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2532 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/* 2533 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2535 */     _jspx_th_am_005fhiddenparam_005f1.setName("haid");
/* 2536 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/* 2537 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/* 2538 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 2539 */       return true;
/*      */     }
/* 2541 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 2542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2547 */     PageContext pageContext = _jspx_page_context;
/* 2548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2550 */     HiddenParam _jspx_th_am_005fhiddenparam_005f2 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2551 */     _jspx_th_am_005fhiddenparam_005f2.setPageContext(_jspx_page_context);
/* 2552 */     _jspx_th_am_005fhiddenparam_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2554 */     _jspx_th_am_005fhiddenparam_005f2.setName("global");
/* 2555 */     int _jspx_eval_am_005fhiddenparam_005f2 = _jspx_th_am_005fhiddenparam_005f2.doStartTag();
/* 2556 */     if (_jspx_th_am_005fhiddenparam_005f2.doEndTag() == 5) {
/* 2557 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 2558 */       return true;
/*      */     }
/* 2560 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 2561 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2566 */     PageContext pageContext = _jspx_page_context;
/* 2567 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2569 */     HiddenParam _jspx_th_am_005fhiddenparam_005f3 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2570 */     _jspx_th_am_005fhiddenparam_005f3.setPageContext(_jspx_page_context);
/* 2571 */     _jspx_th_am_005fhiddenparam_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2573 */     _jspx_th_am_005fhiddenparam_005f3.setName("resourceid");
/* 2574 */     int _jspx_eval_am_005fhiddenparam_005f3 = _jspx_th_am_005fhiddenparam_005f3.doStartTag();
/* 2575 */     if (_jspx_th_am_005fhiddenparam_005f3.doEndTag() == 5) {
/* 2576 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 2577 */       return true;
/*      */     }
/* 2579 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 2580 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2585 */     PageContext pageContext = _jspx_page_context;
/* 2586 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2588 */     HiddenParam _jspx_th_am_005fhiddenparam_005f4 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2589 */     _jspx_th_am_005fhiddenparam_005f4.setPageContext(_jspx_page_context);
/* 2590 */     _jspx_th_am_005fhiddenparam_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2592 */     _jspx_th_am_005fhiddenparam_005f4.setName("attributeid");
/* 2593 */     int _jspx_eval_am_005fhiddenparam_005f4 = _jspx_th_am_005fhiddenparam_005f4.doStartTag();
/* 2594 */     if (_jspx_th_am_005fhiddenparam_005f4.doEndTag() == 5) {
/* 2595 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 2596 */       return true;
/*      */     }
/* 2598 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 2599 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2604 */     PageContext pageContext = _jspx_page_context;
/* 2605 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2607 */     HiddenParam _jspx_th_am_005fhiddenparam_005f5 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2608 */     _jspx_th_am_005fhiddenparam_005f5.setPageContext(_jspx_page_context);
/* 2609 */     _jspx_th_am_005fhiddenparam_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2611 */     _jspx_th_am_005fhiddenparam_005f5.setName("severity");
/* 2612 */     int _jspx_eval_am_005fhiddenparam_005f5 = _jspx_th_am_005fhiddenparam_005f5.doStartTag();
/* 2613 */     if (_jspx_th_am_005fhiddenparam_005f5.doEndTag() == 5) {
/* 2614 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f5);
/* 2615 */       return true;
/*      */     }
/* 2617 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f5);
/* 2618 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2623 */     PageContext pageContext = _jspx_page_context;
/* 2624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2626 */     HiddenParam _jspx_th_am_005fhiddenparam_005f6 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2627 */     _jspx_th_am_005fhiddenparam_005f6.setPageContext(_jspx_page_context);
/* 2628 */     _jspx_th_am_005fhiddenparam_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2630 */     _jspx_th_am_005fhiddenparam_005f6.setName("redirectTo");
/* 2631 */     int _jspx_eval_am_005fhiddenparam_005f6 = _jspx_th_am_005fhiddenparam_005f6.doStartTag();
/* 2632 */     if (_jspx_th_am_005fhiddenparam_005f6.doEndTag() == 5) {
/* 2633 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f6);
/* 2634 */       return true;
/*      */     }
/* 2636 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f6);
/* 2637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2642 */     PageContext pageContext = _jspx_page_context;
/* 2643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2645 */     HiddenParam _jspx_th_am_005fhiddenparam_005f7 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2646 */     _jspx_th_am_005fhiddenparam_005f7.setPageContext(_jspx_page_context);
/* 2647 */     _jspx_th_am_005fhiddenparam_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2649 */     _jspx_th_am_005fhiddenparam_005f7.setName("returnpath");
/* 2650 */     int _jspx_eval_am_005fhiddenparam_005f7 = _jspx_th_am_005fhiddenparam_005f7.doStartTag();
/* 2651 */     if (_jspx_th_am_005fhiddenparam_005f7.doEndTag() == 5) {
/* 2652 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f7);
/* 2653 */       return true;
/*      */     }
/* 2655 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f7);
/* 2656 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2661 */     PageContext pageContext = _jspx_page_context;
/* 2662 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2664 */     HiddenParam _jspx_th_am_005fhiddenparam_005f8 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2665 */     _jspx_th_am_005fhiddenparam_005f8.setPageContext(_jspx_page_context);
/* 2666 */     _jspx_th_am_005fhiddenparam_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2668 */     _jspx_th_am_005fhiddenparam_005f8.setName("manager");
/* 2669 */     int _jspx_eval_am_005fhiddenparam_005f8 = _jspx_th_am_005fhiddenparam_005f8.doStartTag();
/* 2670 */     if (_jspx_th_am_005fhiddenparam_005f8.doEndTag() == 5) {
/* 2671 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f8);
/* 2672 */       return true;
/*      */     }
/* 2674 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f8);
/* 2675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2680 */     PageContext pageContext = _jspx_page_context;
/* 2681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2683 */     HiddenParam _jspx_th_am_005fhiddenparam_005f9 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2684 */     _jspx_th_am_005fhiddenparam_005f9.setPageContext(_jspx_page_context);
/* 2685 */     _jspx_th_am_005fhiddenparam_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2687 */     _jspx_th_am_005fhiddenparam_005f9.setName("admin");
/* 2688 */     int _jspx_eval_am_005fhiddenparam_005f9 = _jspx_th_am_005fhiddenparam_005f9.doStartTag();
/* 2689 */     if (_jspx_th_am_005fhiddenparam_005f9.doEndTag() == 5) {
/* 2690 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f9);
/* 2691 */       return true;
/*      */     }
/* 2693 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f9);
/* 2694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f19(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2699 */     PageContext pageContext = _jspx_page_context;
/* 2700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2702 */     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2703 */     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 2704 */     _jspx_th_c_005fif_005f19.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2706 */     _jspx_th_c_005fif_005f19.setTest("${empty firstrow}");
/* 2707 */     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 2708 */     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */       for (;;) {
/* 2710 */         out.write("\n\n<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t              <tr>\n                <td width=\"5%\" align=\"center\">\n                <img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">\n                </td>\n                <td width=\"95%\" class=\"message\">\n\n          ");
/* 2711 */         if (_jspx_meth_c_005fset_005f14(_jspx_th_c_005fif_005f19, _jspx_page_context))
/* 2712 */           return true;
/* 2713 */         out.write("\n        ");
/* 2714 */         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 2715 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2719 */     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 2720 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 2721 */       return true;
/*      */     }
/* 2723 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 2724 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f14(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2729 */     PageContext pageContext = _jspx_page_context;
/* 2730 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2732 */     SetTag _jspx_th_c_005fset_005f14 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2733 */     _jspx_th_c_005fset_005f14.setPageContext(_jspx_page_context);
/* 2734 */     _jspx_th_c_005fset_005f14.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 2736 */     _jspx_th_c_005fset_005f14.setVar("firstrow");
/*      */     
/* 2738 */     _jspx_th_c_005fset_005f14.setValue("true");
/* 2739 */     int _jspx_eval_c_005fset_005f14 = _jspx_th_c_005fset_005f14.doStartTag();
/* 2740 */     if (_jspx_th_c_005fset_005f14.doEndTag() == 5) {
/* 2741 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f14);
/* 2742 */       return true;
/*      */     }
/* 2744 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f14);
/* 2745 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2750 */     PageContext pageContext = _jspx_page_context;
/* 2751 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2753 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2754 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 2755 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2757 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/*      */     
/* 2759 */     _jspx_th_bean_005fwrite_005f2.setFilter(false);
/* 2760 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 2761 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 2762 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2763 */       return true;
/*      */     }
/* 2765 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2766 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2771 */     PageContext pageContext = _jspx_page_context;
/* 2772 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2774 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2775 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 2776 */     _jspx_th_c_005fif_005f20.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 2778 */     _jspx_th_c_005fif_005f20.setTest("${!empty firstrow}");
/* 2779 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 2780 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 2782 */         out.write("\n        </td>\n\t              </tr>\n\t            </table>\n        ");
/* 2783 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 2784 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2788 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 2789 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 2790 */       return true;
/*      */     }
/* 2792 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 2793 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2798 */     PageContext pageContext = _jspx_page_context;
/* 2799 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2801 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2802 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 2803 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2805 */     _jspx_th_html_005ftext_005f0.setProperty("smtpserver");
/*      */     
/* 2807 */     _jspx_th_html_005ftext_005f0.setSize("30");
/*      */     
/* 2809 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/*      */     
/* 2811 */     _jspx_th_html_005ftext_005f0.setMaxlength("60");
/* 2812 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 2813 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 2814 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2815 */       return true;
/*      */     }
/* 2817 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2818 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2823 */     PageContext pageContext = _jspx_page_context;
/* 2824 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2826 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2827 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 2828 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2830 */     _jspx_th_html_005ftext_005f1.setProperty("smtpport");
/*      */     
/* 2832 */     _jspx_th_html_005ftext_005f1.setSize("10");
/*      */     
/* 2834 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext small");
/*      */     
/* 2836 */     _jspx_th_html_005ftext_005f1.setMaxlength("100");
/* 2837 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 2838 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 2839 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 2840 */       return true;
/*      */     }
/* 2842 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 2843 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2848 */     PageContext pageContext = _jspx_page_context;
/* 2849 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2851 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2852 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 2853 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2855 */     _jspx_th_html_005ftext_005f2.setProperty("emailAddress");
/*      */     
/* 2857 */     _jspx_th_html_005ftext_005f2.setSize("30");
/*      */     
/* 2859 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext default");
/*      */     
/* 2861 */     _jspx_th_html_005ftext_005f2.setMaxlength("100");
/* 2862 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 2863 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 2864 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 2865 */       return true;
/*      */     }
/* 2867 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 2868 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2873 */     PageContext pageContext = _jspx_page_context;
/* 2874 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2876 */     WriteTag _jspx_th_bean_005fwrite_005f3 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 2877 */     _jspx_th_bean_005fwrite_005f3.setPageContext(_jspx_page_context);
/* 2878 */     _jspx_th_bean_005fwrite_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2880 */     _jspx_th_bean_005fwrite_005f3.setName("AMActionForm");
/*      */     
/* 2882 */     _jspx_th_bean_005fwrite_005f3.setProperty("SMTPServerUserName");
/* 2883 */     int _jspx_eval_bean_005fwrite_005f3 = _jspx_th_bean_005fwrite_005f3.doStartTag();
/* 2884 */     if (_jspx_th_bean_005fwrite_005f3.doEndTag() == 5) {
/* 2885 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2886 */       return true;
/*      */     }
/* 2888 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f3);
/* 2889 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2894 */     PageContext pageContext = _jspx_page_context;
/* 2895 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2897 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 2898 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 2899 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2901 */     _jspx_th_html_005fcheckbox_005f0.setProperty("prmTlsAuth");
/* 2902 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 2903 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 2904 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 2905 */       return true;
/*      */     }
/* 2907 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 2908 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2913 */     PageContext pageContext = _jspx_page_context;
/* 2914 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2916 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 2917 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 2918 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2920 */     _jspx_th_html_005fcheckbox_005f1.setProperty("prmSSLAuth");
/* 2921 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 2922 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 2923 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 2924 */       return true;
/*      */     }
/* 2926 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 2927 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2932 */     PageContext pageContext = _jspx_page_context;
/* 2933 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2935 */     CheckboxTag _jspx_th_html_005fcheckbox_005f2 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 2936 */     _jspx_th_html_005fcheckbox_005f2.setPageContext(_jspx_page_context);
/* 2937 */     _jspx_th_html_005fcheckbox_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2939 */     _jspx_th_html_005fcheckbox_005f2.setProperty("seccheck");
/*      */     
/* 2941 */     _jspx_th_html_005fcheckbox_005f2.setOnclick("javascript:showHide('secserver')");
/* 2942 */     int _jspx_eval_html_005fcheckbox_005f2 = _jspx_th_html_005fcheckbox_005f2.doStartTag();
/* 2943 */     if (_jspx_th_html_005fcheckbox_005f2.doEndTag() == 5) {
/* 2944 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 2945 */       return true;
/*      */     }
/* 2947 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 2948 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2953 */     PageContext pageContext = _jspx_page_context;
/* 2954 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2956 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2957 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 2958 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2960 */     _jspx_th_html_005ftext_005f3.setProperty("smtpsecserver");
/*      */     
/* 2962 */     _jspx_th_html_005ftext_005f3.setSize("30");
/*      */     
/* 2964 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext default");
/*      */     
/* 2966 */     _jspx_th_html_005ftext_005f3.setMaxlength("60");
/* 2967 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 2968 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 2969 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2970 */       return true;
/*      */     }
/* 2972 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 2973 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2978 */     PageContext pageContext = _jspx_page_context;
/* 2979 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2981 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2982 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 2983 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2985 */     _jspx_th_html_005ftext_005f4.setProperty("smtpsecport");
/*      */     
/* 2987 */     _jspx_th_html_005ftext_005f4.setSize("10");
/*      */     
/* 2989 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/*      */     
/* 2991 */     _jspx_th_html_005ftext_005f4.setMaxlength("100");
/* 2992 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 2993 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 2994 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 2995 */       return true;
/*      */     }
/* 2997 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 2998 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3003 */     PageContext pageContext = _jspx_page_context;
/* 3004 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3006 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3007 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 3008 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3010 */     _jspx_th_html_005ftext_005f5.setProperty("secemailAddress");
/*      */     
/* 3012 */     _jspx_th_html_005ftext_005f5.setSize("30");
/*      */     
/* 3014 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext default");
/*      */     
/* 3016 */     _jspx_th_html_005ftext_005f5.setMaxlength("100");
/* 3017 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 3018 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 3019 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3020 */       return true;
/*      */     }
/* 3022 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3023 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3028 */     PageContext pageContext = _jspx_page_context;
/* 3029 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3031 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3032 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 3033 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3035 */     _jspx_th_html_005ftext_005f6.setProperty("SMTPsecServerUserName");
/*      */     
/* 3037 */     _jspx_th_html_005ftext_005f6.setSize("30");
/*      */     
/* 3039 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext default");
/*      */     
/* 3041 */     _jspx_th_html_005ftext_005f6.setMaxlength("50");
/* 3042 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 3043 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 3044 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 3045 */       return true;
/*      */     }
/* 3047 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 3048 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3053 */     PageContext pageContext = _jspx_page_context;
/* 3054 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3056 */     org.apache.struts.taglib.html.PasswordTag _jspx_th_html_005fpassword_005f0 = (org.apache.struts.taglib.html.PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(org.apache.struts.taglib.html.PasswordTag.class);
/* 3057 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 3058 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3060 */     _jspx_th_html_005fpassword_005f0.setProperty("SMTPsecServerPassword");
/*      */     
/* 3062 */     _jspx_th_html_005fpassword_005f0.setSize("30");
/*      */     
/* 3064 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext default");
/*      */     
/* 3066 */     _jspx_th_html_005fpassword_005f0.setValue("");
/*      */     
/* 3068 */     _jspx_th_html_005fpassword_005f0.setMaxlength("50");
/* 3069 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 3070 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 3071 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 3072 */       return true;
/*      */     }
/* 3074 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 3075 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3080 */     PageContext pageContext = _jspx_page_context;
/* 3081 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3083 */     CheckboxTag _jspx_th_html_005fcheckbox_005f3 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 3084 */     _jspx_th_html_005fcheckbox_005f3.setPageContext(_jspx_page_context);
/* 3085 */     _jspx_th_html_005fcheckbox_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3087 */     _jspx_th_html_005fcheckbox_005f3.setProperty("secTlsAuth");
/* 3088 */     int _jspx_eval_html_005fcheckbox_005f3 = _jspx_th_html_005fcheckbox_005f3.doStartTag();
/* 3089 */     if (_jspx_th_html_005fcheckbox_005f3.doEndTag() == 5) {
/* 3090 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 3091 */       return true;
/*      */     }
/* 3093 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 3094 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3099 */     PageContext pageContext = _jspx_page_context;
/* 3100 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3102 */     CheckboxTag _jspx_th_html_005fcheckbox_005f4 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 3103 */     _jspx_th_html_005fcheckbox_005f4.setPageContext(_jspx_page_context);
/* 3104 */     _jspx_th_html_005fcheckbox_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3106 */     _jspx_th_html_005fcheckbox_005f4.setProperty("secSSLAuth");
/* 3107 */     int _jspx_eval_html_005fcheckbox_005f4 = _jspx_th_html_005fcheckbox_005f4.doStartTag();
/* 3108 */     if (_jspx_th_html_005fcheckbox_005f4.doEndTag() == 5) {
/* 3109 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f4);
/* 3110 */       return true;
/*      */     }
/* 3112 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f4);
/* 3113 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f5(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3118 */     PageContext pageContext = _jspx_page_context;
/* 3119 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3121 */     CheckboxTag _jspx_th_html_005fcheckbox_005f5 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 3122 */     _jspx_th_html_005fcheckbox_005f5.setPageContext(_jspx_page_context);
/* 3123 */     _jspx_th_html_005fcheckbox_005f5.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 3125 */     _jspx_th_html_005fcheckbox_005f5.setProperty("masMailServer");
/* 3126 */     int _jspx_eval_html_005fcheckbox_005f5 = _jspx_th_html_005fcheckbox_005f5.doStartTag();
/* 3127 */     if (_jspx_th_html_005fcheckbox_005f5.doEndTag() == 5) {
/* 3128 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f5);
/* 3129 */       return true;
/*      */     }
/* 3131 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f5);
/* 3132 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MailServerConfigUserArea_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */