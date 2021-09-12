/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.util.Hashtable;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.MultiboxTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SubmitTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ 
/*      */ public final class Prerequisites_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   private String getMyStatusRow(Hashtable resultHt)
/*      */   {
/*   24 */     if (resultHt.get("link") != null)
/*      */     {
/*   26 */       if (resultHt.get("jarName").equals("cryptix-jce-provider.jar"))
/*      */       {
/*   28 */         return "<tr> <td class=\"bodytext\">&nbsp;" + resultHt.get("jarName") + "</td> <td >&nbsp;<img src=\"" + resultHt.get("classStatusImage") + "\"></td> <td >&nbsp;<a href=\"/help/getting-started/prerequisites.html#ntlm\" target=\"_blank\" class=\"staticlinks\"><img src=\"/images/icon_quicknote_help.gif\" hspace=\"5\" vspace=\"5\" border=\"0\" align=\"absmiddle\">" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a></td></tr>";
/*      */       }
/*      */       
/*      */ 
/*   32 */       return "<tr> <td  class=\"bodytext\">&nbsp;" + resultHt.get("jarName") + "</td> <td>&nbsp;<img src=\"" + resultHt.get("classStatusImage") + "\"></td> <td>&nbsp;<a href=\"/help/getting-started/prerequisites.html#linsol\" target=\"_blank\" class=\"staticlinks\"><img src=\"/images/icon_quicknote_help.gif\" hspace=\"5\" vspace=\"5\" border=\"0\" align=\"absmiddle\">" + FormatUtil.getString("am.webclient.contexthelplink.text") + "</a></td></tr>";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*   37 */     return "<tr> <td class=\"bodytext\">&nbsp;" + resultHt.get("jarName") + "</td> <td>&nbsp;<img src=\"" + resultHt.get("classStatusImage") + "\"></td> <td class=\"bodytext\">&nbsp;" + resultHt.get("message") + "</td></tr>";
/*      */   }
/*      */   
/*      */ 
/*   41 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */   private static java.util.Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fonsubmit_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   67 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   71 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fonsubmit_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   77 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   78 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   79 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   80 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   81 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   82 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   85 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   86 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   88 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   92 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   93 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*   94 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*   95 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*   96 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*   97 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fonsubmit_005faction.release();
/*   98 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/*   99 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/*  100 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fmaxlength_005fnobody.release();
/*  101 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  102 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.release();
/*  103 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.release();
/*  104 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005frows_005fproperty_005fcols_005fnobody.release();
/*  105 */     this._005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/*  106 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.release();
/*  107 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  114 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  117 */     JspWriter out = null;
/*  118 */     Object page = this;
/*  119 */     JspWriter _jspx_out = null;
/*  120 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  124 */       response.setContentType("text/html");
/*  125 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  127 */       _jspx_page_context = pageContext;
/*  128 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  129 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  130 */       session = pageContext.getSession();
/*  131 */       out = pageContext.getOut();
/*  132 */       _jspx_out = out;
/*      */       
/*  134 */       out.write("\n<html>\n<head>\n");
/*      */       
/*  136 */       String ret = "/adminAction.do?method=showPrerequisitesConfiguration&showPreRequisites=true";
/*      */       
/*  138 */       out.write("\n<title>Prerequisites for Applications Manager</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n\n\n\n\n\n\n\n<script language=\"JavaScript1.2\">\nfunction fnSaveMailSettings()\n{\n\tdocument.forms[1].mailserverredirecturl.value=\"");
/*  139 */       out.print(ret);
/*  140 */       out.write("\";\n\tif(document.forms[2].proxycheckbox && document.forms[2].proxycheckbox.checked)\n\t{\n\t     document.forms[1].mailserverredirecturl.value=document.forms[1].mailserverredirecturl.value+\"&proxystyle=block\";\n\t}\n\tif(document.forms[1].smtpcheckbox && !document.forms[1].smtpcheckbox.checked)\n\t{\n\t     document.forms[1].mailserverredirecturl.value=document.forms[1].mailserverredirecturl.value+\"&smtpstyle=none\";\n\t}\n\tif(document.forms[3].adcheckbox && !document.forms[3].adcheckbox.checked)\n\t{\n\t     document.forms[1].mailserverredirecturl.value=document.forms[1].mailserverredirecturl.value+\"&driverstyle=none\";\n   \t}\n\tif(document.forms[1].emailidfortechnicalsupport != null && document.forms[1].emailidfortechnicalsupport.checked)\n   \t{\n   \t\tdocument.forms[1].emailidfortechnicalsupport.value=document.forms[1].emailAddress.value;\n\t}\n\n\tif(trimAll(document.forms[1].smtpserver.value)=='')\n\t{\n                alert('");
/*  141 */       out.print(FormatUtil.getString("am.webclient.mailsettings.alertname.text"));
/*  142 */       out.write("');\n\t \tdocument.forms[1].smtpserver.focus();\n\t \treturn false;\n\t}\n\telse if(trimAll(document.forms[1].smtpport.value)=='')\n\t{\n\t \talert('");
/*  143 */       out.print(FormatUtil.getString("am.webclient.mailsettings.alertport.text"));
/*  144 */       out.write("');\n\t \tdocument.forms[1].smtpport.focus();\n\t \treturn false;\n\t}\n\telse\n\t{\n\t     document.forms[1].submit();\n\t}\n}\n\nfunction fnGoToHomeTab()\n{\n \t");
/*      */       
/*  146 */       request.getSession().setAttribute("prerequisitesalreadydisplayed", "true");
/*      */       
/*  148 */       out.write("\n\tif(document.form3.dontshowornot.checked)\n\t{\n\t    return;\n\t    location.href=\"/applications.do?shouldSaveRequisitesSettings=true\";\n\t}\n\telse\n\t{\n\t    location.href=\"/applications.do\";\n\t}\n}\n\nfunction invertCheckBoxSelection(checkbox)\n{\n   checkbox.checked=!checkbox.checked;\n}\n\nfunction fnUploadFiles()\n{\n   var val = document.forms[3].theFile.value;\n   document.forms[3].returnpath.value=\"");
/*  149 */       out.print(ret);
/*  150 */       out.write("\";\n   if(document.forms[2].proxycheckbox && document.forms[2].proxycheckbox.checked)\n   {\n        document.forms[3].returnpath.value=document.forms[3].returnpath.value+\"&proxystyle=block\";\n   }\n   if(document.forms[1].smtpcheckbox && !document.forms[1].smtpcheckbox.checked)\n   {\n        document.forms[3].returnpath.value=document.forms[3].returnpath.value+\"&smtpstyle=none\";\n   }\n   if(document.forms[3].adcheckbox && !document.forms[3].adcheckbox.checked)\n   {\n        document.forms[3].returnpath.value=document.forms[3].returnpath.value+\"&driverstyle=none\";\n   }\n   if(trimAll(val)==\"\")\n   {\n   \twindow.alert('");
/*  151 */       out.print(FormatUtil.getString("am.webclient.fileupload.alert1.text"));
/*  152 */       out.write("');\n   \treturn false;\n   }\n   document.forms[3].submit();\n}\nfunction fnMQUploadFiles()\n{\nvar val = document.MQForm.theFile.value;\n document.MQForm.returnpath.value=\"");
/*  153 */       out.print(ret);
/*  154 */       out.write("\";\nif(trimAll(val)==\"\")\n   {\n        window.alert('");
/*  155 */       out.print(FormatUtil.getString("am.webclient.fileupload.alert1.text"));
/*  156 */       out.write("');\n        return false;\n   }\n   document.MQForm.submit();\n\n}\nfunction setProxyBlockStatus()\n{\n     if(document.forms[2].proxycheckbox && document.forms[2].proxycheckbox.checked)\n     {\n     \tdocument.forms[2].redirecturl.value=document.forms[2].redirecturl.value+\"&proxystyle=block\";\n     }\n     if(document.forms[1].smtpcheckbox && !document.forms[1].smtpcheckbox.checked)\n     {\n       document.forms[2].redirecturl.value=document.forms[2].redirecturl.value+\"&smtpstyle=none\";\n     }\n     if(document.forms[3].adcheckbox && !document.forms[3].adcheckbox.checked)\n     {\n         document.forms[2].redirecturl.value=document.forms[2].redirecturl.value+\"&driverstyle=none\";\n     }\n}\n\n\nfunction alertUser1()\n{\n\t");
/*  157 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/*  159 */       out.write(10);
/*  160 */       out.write(9);
/*  161 */       if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
/*      */         return;
/*  163 */       out.write("\n}\n\nfunction showPassword(chkbox) {\n\tif(chkbox.checked) {\n\t\tshowRow(\"user_tr\");// NO I18N\n\t\tshowRow(\"pwd_tr\");// NO I18N\n\t} else {\n\t\thideRow(\"user_tr\");// NO I18N\n\t\thideRow(\"pwd_tr\");// NO I18N\n\t}\n\t\n}\n</script>\n\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"grayfullborder\">\n");
/*      */       
/*  165 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  166 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  167 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/*  169 */       _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*      */       
/*  171 */       _jspx_th_html_005fform_005f0.setStyle("display:inline");
/*  172 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  173 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/*  175 */           out.write("\n<input type=\"hidden\" name=\"method\" value=\"preRequisitesMailServerConfiguration\" >\n<input type=\"hidden\" name=\"mailserverredirecturl\"/>\n<input type=\"hidden\" name=\"popup\"/>\n<input type=\"hidden\" name=\"resourceid\"/>\n<input type=\"hidden\" name=\"attributeid\"/>\n<input type=\"hidden\" name=\"severity\"/>\n<input type=\"hidden\" name=\"redirectTo\"/>\n<input type=\"hidden\" name=\"returnpath\"/>\n<input type=\"hidden\" name=\"manager\"/>\n<input type=\"hidden\" name=\"uploadDir\" value=\"../lib/ext/\"/>\n    <tr class=\"lightbg\">\n      <td width=\"5%\">&nbsp;<input type=\"checkbox\" ");
/*  176 */           out.print(request.getParameter("smtpstyle") == null ? "checked" : "");
/*  177 */           out.write(" name=\"smtpcheckbox\" value=\"true\" onclick=\"javascript:toggleDiv('smtpconfig');\"></td>\n    <td width=\"95%\" onclick=\"javascript:toggleDiv('smtpconfig');javascript:invertCheckBoxSelection(document.forms[1].smtpcheckbox);\">\n      <img src=\"/images/icon_pre_mailserver.gif\" width=\"22\" height=\"22\" hspace=\"10\" vspace=\"10\" align=\"absmiddle\"><span class=\"bodytext\">\n      <a href=\"javascript:void(0);\" class=\"bodytext\">");
/*  178 */           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverconfig.heading"));
/*  179 */           out.write("</a></span>\n");
/*      */           
/*  181 */           String conf = (String)request.getAttribute("smtp_statusImage");
/*  182 */           String ttip = null;
/*  183 */           if (conf.equals("/images/prereq_configured.gif")) {
/*  184 */             ttip = FormatUtil.getString("am.webclient.gettingstarted.configured.text");
/*      */           }
/*      */           else {
/*  187 */             ttip = FormatUtil.getString("am.webclient.gettingstarted.notconfigured.text");
/*      */           }
/*  189 */           out.write("\n\n <img src=\"");
/*  190 */           out.print(request.getAttribute("smtp_statusImage"));
/*  191 */           out.write("\"  align=\"absmiddle\" title=\"");
/*  192 */           out.print(ttip);
/*  193 */           out.write("\" >\n  </tr>\n\n  <tr align=\"center\" >\n    <td colspan=\"4\" class=\"lightbg\">\n      <div id=\"smtpconfig\" style=\"display:");
/*  194 */           out.print(request.getParameter("smtpstyle") == null ? "block" : "none");
/*  195 */           out.write(";\">\n\t    <table  width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"5\" bgcolor=\"#FFFFFF\" class=\"grayfullborder\">\n          <tr >\n            <td width=\"109\" height=\"32\" class=\"bodytext\">");
/*  196 */           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpservername"));
/*  197 */           out.write("</td>\n            <td width=\"158\"> ");
/*  198 */           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  200 */           out.write("</td>\n            <td width=\"95\" class=\"bodytext\">");
/*  201 */           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/*  202 */           out.write("</td>\n            <td width=\"555\"> ");
/*  203 */           if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  205 */           out.write("</td>\n          </tr>\n          <tr>\n            <td height=\"32\" class=\"bodytext\">");
/*  206 */           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverusername"));
/*  207 */           out.write("</td>\n            <td><!--");
/*  208 */           if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  210 */           out.write("--><input type=\"text\" name=\"SMTPServerUserName\" size=\"15\" class=\"formtext\" maxlength=\"25\" autocomplete=\"off\"/></td>\n            <td class=\"bodytext\">");
/*  211 */           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverpassword"));
/*  212 */           out.write("</td>\n            <td><!--");
/*  213 */           if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  215 */           out.write("--> <input type=\"password\" name=\"SMTPServerPassword\" size=\"10\" class=\"formtext\" maxlength=\"25\" autocomplete=\"off\"/>&nbsp;<span class=\"footer\">");
/*  216 */           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverpassword.help.text"));
/*  217 */           out.write("</span></td>\n          </tr>\n          <tr>\n            <td width=\"109\" height=\"32\" class=\"bodytext\">&nbsp;");
/*  218 */           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.email.text"));
/*  219 */           out.write("\n            </td>\n            <td width=\"158\"> ");
/*  220 */           if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  222 */           out.write("</td>\n            <td colspan=\"2\"><span class=\"footer\">");
/*  223 */           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.email.help.text"));
/*  224 */           out.write("</span></td>\n          </tr>\n\t");
/*  225 */           if ((request.getRemoteUser() != null) && (request.getRemoteUser().equals("admin"))) {
/*  226 */             out.write("\n\t  <tr>\n            <td width=\"129\" height=\"32\" class=\"bodytext\">&nbsp;");
/*  227 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.adminid.text"));
/*  228 */             out.write("\n            </td>\n            <td width=\"158\"> ");
/*  229 */             if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  231 */             out.write("</td>\n            <td colspan=\"2\"><span class=\"footer\">");
/*  232 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.adminid.help.text"));
/*  233 */             out.write("</span></td>\n          </tr>\n\t");
/*      */           }
/*  235 */           if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {}
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
/*  246 */           out.write("\n          <tr>\n            <td>&nbsp;</td>\n            <td><input name=\"saveButton\" value=\"");
/*  247 */           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverconfig.savebutton.text"));
/*  248 */           out.write("\" type=\"button\" class=\"buttons\" onClick=\"fnSaveMailSettings();\"></td>\n            <td></td>\n            <td></td>\n          </tr>\n          <tr>\n            <td> </td>\n            <td colspan=\"5\"></td>\n          </tr>\n        </table>\n\t   <br>\n      </div>\n      </td>\n  </tr>\n   <tr>\n      <td colspan=\"2\"><img src=\"/images/spacer.gif\" width=\"9\" height=\"4\"></td>\n  </tr>\n   <tr>\n      <td colspan=\"2\"><img src=\"/images/spacer.gif\" width=\"9\" height=\"4\"></td>\n  </tr>\n  ");
/*  249 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  250 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  254 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  255 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else {
/*  258 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  259 */         out.write(10);
/*  260 */         out.write(32);
/*  261 */         out.write(32);
/*      */         
/*  263 */         FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fonsubmit_005faction.get(FormTag.class);
/*  264 */         _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/*  265 */         _jspx_th_html_005fform_005f1.setParent(null);
/*      */         
/*  267 */         _jspx_th_html_005fform_005f1.setAction("/ConfigureProxy");
/*      */         
/*  269 */         _jspx_th_html_005fform_005f1.setStyle("display:inline;");
/*      */         
/*  271 */         _jspx_th_html_005fform_005f1.setOnsubmit("return alertUser1();");
/*  272 */         int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/*  273 */         if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */           for (;;) {
/*  275 */             out.write("\n    <input type=\"hidden\" name=\"redirecturl\" value=\"");
/*  276 */             out.print(ret);
/*  277 */             out.write("\" >\n    <tr class=\"lightbg\">\n      <td width=\"5%\" >&nbsp;<input type=\"checkbox\" ");
/*  278 */             out.print(request.getParameter("proxystyle") == null ? "" : "checked");
/*  279 */             out.write(" name=\"proxycheckbox\" value=\"true\" onclick=\"javascript:toggleDiv('proxy');\"></td>\n      <td width=\"95%\" onclick=\"javascript:toggleDiv('proxy');javascript:invertCheckBoxSelection(document.ProxyConfiguration.proxycheckbox)\"  class=\"bodytext\"> <img src=\"/images/icon_pre_proxyserver.gif\" width=\"27\" height=\"23\" hspace=\"10\" vspace=\"10\" align=\"absmiddle\">\n         <a href=\"javascript:void(0)\" class=\"bodytext\">");
/*  280 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.httpproxy.heading"));
/*  281 */             out.write("</a>\n       </td>\n        </tr>\n        <tr class=\"lightbg\">\n        <td colspan=\"2\">\n        <div id=\"proxy\" style=\"display:");
/*  282 */             out.print(request.getParameter("proxystyle") == null ? "none" : "block");
/*  283 */             out.write(";\">\n  \t       <table width=\"100%\" border=\"0\" cellspacing=\"10\" cellpadding=\"0\">\n  \t\t      <tr>\n  \t\t\t  <td>\n  \t\t\t  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" class=\"grayfullborder\">\n  \t\t\t    <tr>\n  \t\t\t      <td bgcolor=\"#FFFFFF\" class=\"bodytext\"> ");
/*  284 */             if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */               return;
/*  286 */             out.write("\n                    ");
/*  287 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.settings"));
/*  288 */             out.write("</td>\n  \t\t\t      </tr>\n  \t\t\t    </table>\n  \t\t\t  </td>\n  \t\t\t</tr>\n  \t\t     <tr>\n  \t\t      <td>\n  \t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\" class=\"grayfullborder\">\n                <tr >\n  \t\t\t  <td colspan=\"2\" class=\"bodytext\">");
/*  289 */             if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */               return;
/*  291 */             out.write("\n  \t\t\t    ");
/*  292 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.proxyserver"));
/*  293 */             out.write("</td>\n  \t\t\t</tr>\n  \t\t\t<tr >\n  \t\t\t  <td width=\"13%\" class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;");
/*  294 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.host"));
/*  295 */             out.write("</td>\n  \t\t\t  <td width=\"80%\" class=\"bodytext\">");
/*  296 */             if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */               return;
/*  298 */             out.write("</td>\n  \t\t\t</tr>\n  \t\t\t<tr >\n  \t\t\t  <td class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;");
/*  299 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.port"));
/*  300 */             out.write("</td>\n  \t\t\t  <td class=\"bodytext\">");
/*  301 */             if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */               return;
/*  303 */             out.write("</td>\n  \t\t\t</tr>\n  \t\t\t\n\t        ");
/*      */             
/*  305 */             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  306 */             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  307 */             _jspx_th_c_005fif_005f0.setParent(_jspx_th_html_005fform_005f1);
/*      */             
/*  309 */             _jspx_th_c_005fif_005f0.setTest("${not empty host}");
/*  310 */             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  311 */             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */               for (;;) {
/*  313 */                 out.write("\n\t        <tr>\n\t          <td class=\"bodytext\" colspan=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"checkbox\" name=\"updatepwd\" onclick=\"javascript:showPassword(this)\" value=\"true\"/>");
/*  314 */                 out.print(FormatUtil.getString("am.webclient.update.proxy.authentication.text"));
/*  315 */                 out.write("</td>\n\t        </tr>  \n\t        <tr id=\"user_tr\" style=\"display:none\">\n\t          <td class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;");
/*  316 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.name"));
/*  317 */                 out.write("</td>\n\t          <td class=\"bodytext\"><!--<input type=\"text\" name=\"userId\" class=\"formtext\" autocomplete=\"off\" />-->");
/*  318 */                 if (_jspx_meth_html_005ftext_005f7(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                   return;
/*  320 */                 out.write("</td>\n\t        </tr>        \n\t        <tr id=\"pwd_tr\" style=\"display:none\">\n\t          <td class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;");
/*  321 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.password"));
/*  322 */                 out.write("</td>\n\t          <td class=\"bodytext\"><input type=\"password\" name=\"password\" class=\"formtext\" autocomplete=\"off\" /><!--");
/*  323 */                 if (_jspx_meth_html_005fpassword_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                   return;
/*  325 */                 out.write("--></td>\n\t        </tr>              \n\t        ");
/*  326 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  327 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  331 */             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  332 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */             }
/*      */             
/*  335 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  336 */             out.write("        \n\t        ");
/*      */             
/*  338 */             IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  339 */             _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  340 */             _jspx_th_c_005fif_005f1.setParent(_jspx_th_html_005fform_005f1);
/*      */             
/*  342 */             _jspx_th_c_005fif_005f1.setTest("${empty host}");
/*  343 */             int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  344 */             if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */               for (;;) {
/*  346 */                 out.write("\n\t        <tr>\n\t          <td class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;");
/*  347 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.name"));
/*  348 */                 out.write("</td>\n\t          <td class=\"bodytext\"><!--<input type=\"text\" name=\"userId\" class=\"formtext\" autocomplete=\"off\" />-->");
/*  349 */                 if (_jspx_meth_html_005ftext_005f8(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                   return;
/*  351 */                 out.write("</td>\n\t        </tr>        \n\t        <tr>\n\t          <td class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;");
/*  352 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.password"));
/*  353 */                 out.write("</td>\n\t          <td class=\"bodytext\"><input type=\"password\" name=\"password\" class=\"formtext\" autocomplete=\"off\" /><!--");
/*  354 */                 if (_jspx_meth_html_005fpassword_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                   return;
/*  356 */                 out.write("--></td>\n\t         </tr>\n\t        ");
/*  357 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  358 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  362 */             if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  363 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */             }
/*      */             
/*  366 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  367 */             out.write("\n\t        \n\t        \n  \t\t\t<tr >\n  \t\t\t  <td class=\"bodytext\" colspan=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;");
/*  368 */             if (_jspx_meth_html_005fmultibox_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */               return;
/*  370 */             out.write("\n  \t\t\t    ");
/*  371 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.bypassserver"));
/*  372 */             out.write("</td>\n  \t\t\t</tr>\n  \t\t\t<tr >\n  \t\t\t  <td class=\"bodytext\" colspan=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;");
/*  373 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.noproxy"));
/*  374 */             out.write("</td>\n  \t\t\t</tr>\n  \t\t\t<tr >\n  \t\t\t  <td class=\"bodytext\" colspan=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;");
/*  375 */             if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */               return;
/*  377 */             out.write("\n  \t\t\t    <br> &nbsp;&nbsp;&nbsp;&nbsp;");
/*  378 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.text"));
/*  379 */             out.write("\n  \t\t\t     </td>\n  \t\t\t</tr>\n  \t\t\t<tr >\n  \t\t\t  <td class=\"bodytext\" colspan=\"2\">&nbsp;&nbsp;&nbsp;&nbsp;");
/*      */             
/*  381 */             SubmitTag _jspx_th_html_005fsubmit_005f0 = (SubmitTag)this._005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(SubmitTag.class);
/*  382 */             _jspx_th_html_005fsubmit_005f0.setPageContext(_jspx_page_context);
/*  383 */             _jspx_th_html_005fsubmit_005f0.setParent(_jspx_th_html_005fform_005f1);
/*      */             
/*  385 */             _jspx_th_html_005fsubmit_005f0.setProperty("submit");
/*      */             
/*  387 */             _jspx_th_html_005fsubmit_005f0.setStyleClass("buttons");
/*      */             
/*  389 */             _jspx_th_html_005fsubmit_005f0.setValue(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.savebutton.text"));
/*      */             
/*  391 */             _jspx_th_html_005fsubmit_005f0.setOnclick("javascript:setProxyBlockStatus();");
/*  392 */             int _jspx_eval_html_005fsubmit_005f0 = _jspx_th_html_005fsubmit_005f0.doStartTag();
/*  393 */             if (_jspx_th_html_005fsubmit_005f0.doEndTag() == 5) {
/*  394 */               this._005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fsubmit_005f0); return;
/*      */             }
/*      */             
/*  397 */             this._005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fsubmit_005f0);
/*  398 */             out.write("</td>\n  \t\t\t</tr>\n  \t\t      </table>\n                       </td>\n  \t            </tr>\n  \t      </table>\n  \t  </div>\n        </td>\n    </tr>\n  ");
/*  399 */             int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/*  400 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  404 */         if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/*  405 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fonsubmit_005faction.reuse(_jspx_th_html_005fform_005f1);
/*      */         }
/*      */         else {
/*  408 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fonsubmit_005faction.reuse(_jspx_th_html_005fform_005f1);
/*  409 */           out.write("\n    <tr>\n      <td colspan=\"2\"><img src=\"/images/spacer.gif\" width=\"9\" height=\"4\"></td>\n    </tr>\n    <tr>\n        <td colspan=\"2\"><img src=\"/images/spacer.gif\" width=\"9\" height=\"4\"></td>\n  </tr>\n  ");
/*      */           
/*  411 */           FormTag _jspx_th_html_005fform_005f2 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.get(FormTag.class);
/*  412 */           _jspx_th_html_005fform_005f2.setPageContext(_jspx_page_context);
/*  413 */           _jspx_th_html_005fform_005f2.setParent(null);
/*      */           
/*  415 */           _jspx_th_html_005fform_005f2.setAction("/Upload.do");
/*      */           
/*  417 */           _jspx_th_html_005fform_005f2.setStyle("display:inline");
/*      */           
/*  419 */           _jspx_th_html_005fform_005f2.setEnctype("multipart/form-data");
/*  420 */           int _jspx_eval_html_005fform_005f2 = _jspx_th_html_005fform_005f2.doStartTag();
/*  421 */           if (_jspx_eval_html_005fform_005f2 != 0) {
/*      */             for (;;) {
/*  423 */               out.write("\n  <input type=\"hidden\" name=\"method\" value=\"preRequisitesMailServerConfiguration\" >\n  <input type=\"hidden\" name=\"mailserverredirecturl\"/>\n  <input type=\"hidden\" name=\"popup\"/>\n  <input type=\"hidden\" name=\"resourceid\"/>\n  <input type=\"hidden\" name=\"attributeid\"/>\n  <input type=\"hidden\" name=\"severity\"/>\n  <input type=\"hidden\" name=\"redirectTo\"/>\n  <input type=\"hidden\" name=\"returnpath\"/>\n  <input type=\"hidden\" name=\"manager\"/>\n  <input type=\"hidden\" name=\"uploadDir\" value=\"../lib/ext/\"/>\n\n   ");
/*      */               
/*  425 */               String configured = FormatUtil.getString("am.webclient.gettingstarted.configured.text");
/*  426 */               String notConfigured = FormatUtil.getString("am.webclient.gettingstarted.notconfigured.text");
/*      */               
/*  428 */               String allJarsStatusImg = "<img src=/images/prereq_notconfigured.gif align=absmiddle title=" + notConfigured + ">";
/*  429 */               Hashtable cryptixJarStatus = com.adventnet.appmanager.util.DriverChecker.checkAndProvideCryptixJarStatus();
/*  430 */               if (cryptixJarStatus.get("result").equals("" + com.adventnet.appmanager.util.DriverChecker.ALL_REQUISITES_MET))
/*      */               {
/*  432 */                 allJarsStatusImg = "<img src=\"/images/prereq_configured.gif\" align=absmiddle title=" + configured + ">";
/*      */               }
/*      */               
/*  435 */               if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.admintab.upload.jar.remove"))
/*      */               {
/*      */ 
/*  438 */                 out.write("\n\n  <tr class=\"lightbg\">\n     <td width=\"5%\">&nbsp;<input type=\"checkbox\" ");
/*  439 */                 out.print(request.getParameter("driverstyle") == null ? "checked" : "");
/*  440 */                 out.write("  name=\"adcheckbox\" value=\"true\" onclick=\"javascript:toggleDiv('alldrivers');\"></td>\n      <td width=\"95%\" onclick=\"javascript:toggleDiv('alldrivers');javascript:invertCheckBoxSelection(document.forms[3].adcheckbox)\"  class=\"bodytext\"><img src=\"/images/icon_pre_jarrequirements.gif\" width=\"19\" hspace=\"10\" vspace=\"10\" align=\"absmiddle\">\n      <a href=\"javascript:void(0);\" class=\"bodytext\">");
/*  441 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.ntlmcheck.heading"));
/*  442 */                 out.write(" </a>&nbsp;&nbsp;&nbsp;&nbsp;");
/*  443 */                 out.print(allJarsStatusImg);
/*  444 */                 out.write(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/help/getting-started/prerequisites.html#ntlm\" target=\"_blank\" class=\"staticlinks\"><img src=\"/images/icon_quicknote_help.gif\" hspace=\"5\" vspace=\"5\" border=\"0\" align=\"absmiddle\">");
/*  445 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.help.text"));
/*  446 */                 out.write("</a>\n        </tr>\n        <tr>\n         <td colspan=\"2\">\n        <div id=\"alldrivers\" style=\"display:");
/*  447 */                 out.print(request.getParameter("driverstyle") == null ? "block" : "none");
/*  448 */                 out.write(";\">\n            <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\" class=\"lightbg\">\n\n\t\t  \t\t  <tr>\n\t\t  \t\t    <td colspan=\"2\">\n\t\t  \t\t     <div id=\"ntlm\" style=\"display:block;\">\n\t\t\t\t\t <br>\n\n                <table width=\"98%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\" class=\"grayfullborder\">\n                  <tr  class=\"bodytext\">\n\t\t  \t\t  \t<td class=\"yellowgrayborder\" width=\"25%\"><span class=\"bodytextbold\">");
/*  449 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.ntlmcheck.table.jarname"));
/*  450 */                 out.write("</span></td>\n\t\t  \t\t  \t<td  class=\"yellowgrayborder\"  width=\"15%\"><span class=\"bodytextbold\">");
/*  451 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.ntlmcheck.table.prerequsitesmet"));
/*  452 */                 out.write("</span></td>\n\t\t  \t\t  \t<td   class=\"yellowgrayborder\"  width=\"50%\"><span class=\"bodytextbold\">");
/*  453 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.ntlmcheck.table.remarks"));
/*  454 */                 out.write("</span></td>\n\t\t  \t\t  \t</tr>\n\t\t  \t\t  \t");
/*  455 */                 out.print(getMyStatusRow(cryptixJarStatus));
/*  456 */                 out.write("\n\t\t  \t\t  \t</table>\n\t\t  \t\t     </div>\n\t\t  \t\t  </td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t    <td colspan=\"2\" align=\"center\"> ");
/*      */                 
/*  458 */                 IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  459 */                 _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  460 */                 _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fform_005f2);
/*      */                 
/*  462 */                 _jspx_th_c_005fif_005f2.setTest("${!empty param.returnpath}");
/*  463 */                 int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  464 */                 if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                   for (;;) {
/*  466 */                     out.write("\n              <input name=\"returnpath\" type=\"hidden\" value=\"");
/*  467 */                     out.print(request.getParameter("returnpath"));
/*  468 */                     out.write("\">\n\t\t");
/*  469 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  470 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  474 */                 if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  475 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                 }
/*      */                 
/*  478 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  479 */                 out.write("\n\t\t<br>&nbsp;&nbsp;&nbsp;\n\t\t<table width=\"98%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" bgcolor=\"#FFFFFF\" class=\"grayfullborder\">\n                <tr>\n\t\t<td class=\"yellowgrayborder\" colspan=\"3\"><span class=\"bodytextbold\">");
/*  480 */                 out.print(FormatUtil.getString("Upload Files"));
/*  481 */                 out.write("</span>\n\n\t\t</td>\n\t\t</tr>\n\t\t  <tr >\n\t\t          <td width=\"25%\"  height=\"22\" class=\"bodytext\" >");
/*  482 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.uploadfile.filename.text"));
/*  483 */                 out.write("</td>\n\t\t          <td width=\"60%\" height=\"22\" class=\"bodytext\"> ");
/*  484 */                 if (_jspx_meth_html_005ffile_005f0(_jspx_th_html_005fform_005f2, _jspx_page_context))
/*      */                   return;
/*  486 */                 out.write("\n                  <td width=\"15%\" height=\"30\" align=\"left\">\n                    <input name=\"button1\" type=\"button\" class=\"buttons\" value=\"");
/*  487 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.uploadfile.uploadbutton.text"));
/*  488 */                 out.write("\" onClick=\"javascript:fnUploadFiles()\">\n\t\t    </td>\n\t\t  </tr>\n\t\t  </table>\n\t\t      <br>\n\t\t\t<table width=\"98%\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">\n                <tr>\n\n                  <td width=\"100%\"  height=\"22\" colspan=\"3\" class=\"footer\"> *\n                    ");
/*  489 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.upload.message"));
/*  490 */                 out.write("&nbsp;&nbsp;&nbsp;<a href=\"/help/getting-started/prerequisites.html\" target=\"_blank\" class=\"footer\"><img src=\"/images/icon_quicknote_help.gif\" hspace=\"5\" vspace=\"5\" border=\"0\" align=\"absmiddle\">");
/*  491 */                 out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.morehelp.text"));
/*  492 */                 out.write("</a></td>\n\t\t\t\t  </tr>\n\t\t  </table>\n\t\t  </td>\n\t\t  </tr>\n\t\t</table>\n\t</div>\n        </td>\n  </tr>\n  ");
/*      */               }
/*  494 */               out.write(10);
/*  495 */               out.write(32);
/*  496 */               out.write(32);
/*  497 */               int evalDoAfterBody = _jspx_th_html_005fform_005f2.doAfterBody();
/*  498 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  502 */           if (_jspx_th_html_005fform_005f2.doEndTag() == 5) {
/*  503 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f2);
/*      */           }
/*      */           else {
/*  506 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f2);
/*  507 */             out.write("\n<form style=\"display: inline;\" enctype=\"multipart/form-data\" action=\"/Upload.do\" method=\"post\" name=\"MQForm\"/>\n<input type=\"hidden\" value=\"preRequisitesMailServerConfiguration\" name=\"method\"/>\n<input name=\"returnpath\" type=\"hidden\" value=\"");
/*  508 */             out.print(request.getParameter("returnpath"));
/*  509 */             out.write("\"\n<input type=\"hidden\" value=\"./jre/lib/ext/\" name=\"uploadDir\"/>\n<tr>\n<td colspan=\"2\">\n<img width=\"9\" height=\"4\" src=\"/images/spacer.gif\"/>\n</td>\n</tr>\n<tr>\n<tr>\n<td colspan=\"2\">\n<img width=\"9\" height=\"4\" src=\"/images/spacer.gif\"/>\n</td>\n</tr>\n<tr class=\"lightbg\">\n\n<td colspan=\"2\">\n");
/*      */             
/*  511 */             Hashtable mqjarStatus = null;
/*  512 */             String[][] mqjars = new String[6][2];
/*  513 */             mqjars[0][0] = "com.ibm.mq.jar";
/*  514 */             mqjars[1][0] = "com.ibm.mq.pcf-6.1.jar";
/*  515 */             mqjars[2][0] = "connector.jar";
/*  516 */             mqjars[3][0] = "com.ibm.mq.commonservices.jar";
/*  517 */             mqjars[4][0] = "com.ibm.mq.headers.jar";
/*  518 */             mqjars[5][0] = "com.ibm.mq.jmqi.jar";
/*      */             
/*  520 */             mqjars[0][1] = "com.ibm.mq.MQException";
/*  521 */             mqjars[1][1] = "com.ibm.mq.pcf.PCFMessageAgent";
/*  522 */             mqjars[2][1] = "javax.resource.ResourceException";
/*  523 */             mqjars[3][1] = "com.ibm.mq.commonservices.CommonServicesException";
/*  524 */             mqjars[4][1] = "com.ibm.mq.headers.CCSID";
/*  525 */             mqjars[5][1] = "com.ibm.mq.jmqi.JmqiException";
/*  526 */             String configured1 = FormatUtil.getString("am.webclient.gettingstarted.configured.text");
/*  527 */             String notConfigured1 = FormatUtil.getString("am.webclient.gettingstarted.notconfigured.text");
/*      */             
/*  529 */             String areAllMQJarsPresent = "";
/*  530 */             String allMQJarsStatusImg = "<img src=\"/images/prereq_configured.gif\" align=absmiddle title=" + configured1 + ">";
/*  531 */             String mqdivstyle = "display:none";
/*  532 */             for (int jarcount = 0; jarcount < mqjars.length; jarcount++)
/*      */             {
/*  534 */               mqjarStatus = com.adventnet.appmanager.util.DriverChecker.checkAndProvideDriverStatus(mqjars[jarcount][0], mqjars[jarcount][1]);
/*      */               
/*      */ 
/*  537 */               if (!mqjarStatus.get("result").equals("" + com.adventnet.appmanager.util.DriverChecker.ALL_REQUISITES_MET))
/*      */               {
/*  539 */                 areAllMQJarsPresent = "value='true'";
/*  540 */                 allMQJarsStatusImg = "<img src=/images/prereq_notconfigured.gif align=absmiddle title=" + notConfigured1 + ">";
/*  541 */                 mqdivstyle = "display:inline";
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  547 */             out.write("\n<tr class=\"lightbg\">\n        <td width=\"5%\">\n&nbsp;<input type=\"checkbox\" onclick=\"javascript:toggleDiv('allmqdrivers');\" ");
/*  548 */             out.print(areAllMQJarsPresent);
/*  549 */             out.write("  name=\"adcheckbox\" checked=\"\"/>\n</td>\n<td class=\"bodytext\" width=\"95%\" onclick=\"javascript:toggleDiv('allmqdrivers');javascript:invertCheckBoxSelection(document.MQForm.adcheckbox)\">\n<img width=\"19\" vspace=\"10\" hspace=\"10\" align=\"absmiddle\" src=\"/images/icon_pre_jarrequirements.gif\"/>\n<a class=\"bodytext\" href=\"javascript:void(0);\">");
/*  550 */             out.print(FormatUtil.getString("am.webclient.prerequisistes.mq.text"));
/*  551 */             out.write("</a>\n ");
/*  552 */             out.print(allMQJarsStatusImg);
/*  553 */             out.write("\n<a class=\"staticlinks\" target=\"_blank\" href=\"/help/getting-started/prerequisites.html#ntlm\">\n<img vspace=\"5\" hspace=\"5\" border=\"0\" align=\"absmiddle\" src=\"/images/icon_quicknote_help.gif\"/>\n");
/*  554 */             out.print(FormatUtil.getString("am.webclient.contexthelplink.text"));
/*  555 */             out.write("\n</a>\n</td>\n</tr>\n<tr>\n<td colspan=\"2\">\n<div id=\"allmqdrivers\" style=\"");
/*  556 */             out.print(mqdivstyle);
/*  557 */             out.write("\">\n<table class=\"lightbg\" width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\">\n  <tr>\n                                    <td colspan=\"2\">\n                                     <div id=\"mqdrivers\">\n                                         <br>\n\n                <table width=\"98%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\" class=\"grayfullborder\">\n                  <tr  class=\"bodytext\">\n                                        <td class=\"yellowgrayborder\" width=\"25%\"><span class=\"bodytextbold\">");
/*  558 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.ntlmcheck.table.jarname"));
/*  559 */             out.write("</span></td>\n                                        <td  class=\"yellowgrayborder\"  width=\"15%\"><span class=\"bodytextbold\">");
/*  560 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.ntlmcheck.table.prerequsitesmet"));
/*  561 */             out.write("</span></td>\n                                        <td   class=\"yellowgrayborder\"  width=\"50%\"><span class=\"bodytextbold\">");
/*  562 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.ntlmcheck.table.remarks"));
/*  563 */             out.write("</span></td>\n                                        </tr>\n");
/*  564 */             for (int jarcount = 0; jarcount < mqjars.length; jarcount++)
/*      */             {
/*  566 */               mqjarStatus = com.adventnet.appmanager.util.DriverChecker.checkAndProvideDriverStatus(mqjars[jarcount][0], mqjars[jarcount][1]);
/*  567 */               String configured = FormatUtil.getString("am.webclient.gettingstarted.configured.text");
/*  568 */               String notConfigured = FormatUtil.getString("am.webclient.gettingstarted.notconfigured.text");
/*      */               
/*  570 */               String allJarsStatusImg = "<img src=/images/prereq_notconfigured.gif align=absmiddle title=" + notConfigured + ">";
/*  571 */               if (mqjarStatus.get("result").equals("" + com.adventnet.appmanager.util.DriverChecker.ALL_REQUISITES_MET))
/*      */               {
/*  573 */                 allJarsStatusImg = "<img src=\"/images/prereq_configured.gif\" align=absmiddle title=" + configured + ">";
/*      */               }
/*      */               
/*  576 */               if (jarcount == 3)
/*      */               {
/*      */ 
/*  579 */                 out.write("\n<tr><td colspan=\"3\" class=\"bodytext\">&nbsp;<i>");
/*  580 */                 out.print(FormatUtil.getString("am.webclient.prerequisistes.mq.version7.text"));
/*  581 */                 out.write("</i></td> </tr>\n");
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*  586 */               out.write("\n                                        ");
/*  587 */               out.print(getMyStatusRow(mqjarStatus));
/*  588 */               out.write(10);
/*      */             }
/*  590 */             out.write("\n\n                                        </table>\n\t\t\t\t</div>\n                                  </td>\n                  </tr>\n                                       <tr>\n                    <td colspan=\"2\" align=\"center\"> ");
/*  591 */             if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*      */               return;
/*  593 */             out.write("\n                <br>&nbsp;&nbsp;&nbsp;\n                <table width=\"98%\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" bgcolor=\"#FFFFFF\" class=\"grayfullborder\">\n                <tr>\n                <td class=\"yellowgrayborder\" colspan=\"3\"><span class=\"bodytextbold\">");
/*  594 */             out.print(FormatUtil.getString("Upload Files"));
/*  595 */             out.write("</span>\n\n                </td>\n                </tr>\n                  <tr >\n                          <td width=\"25%\"  height=\"22\" class=\"bodytext\" >");
/*  596 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.uploadfile.filename.text"));
/*  597 */             out.write("</td>\n                          <td width=\"60%\" height=\"22\" class=\"bodytext\"> <input type=\"file\" size=\"70\" name=\"theFile\"/>\n                  <td width=\"15%\" height=\"30\" align=\"left\">\n                    <input name=\"button1\" type=\"button\" class=\"buttons\" value=\"");
/*  598 */             out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.uploadfile.uploadbutton.text"));
/*  599 */             out.write("\" onClick=\"javascript:fnMQUploadFiles()\">\n                    </td>\n                  </tr>\n              </table><br>\n</td>\n</div>\n</tr>\n</table>\n<tr>\n</form>\n");
/*  600 */             out.write("\n\n\n</table>\n</body>\n</html>\n");
/*      */           }
/*  602 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  603 */         out = _jspx_out;
/*  604 */         if ((out != null) && (out.getBufferSize() != 0))
/*  605 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  606 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  609 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  615 */     PageContext pageContext = _jspx_page_context;
/*  616 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  618 */     org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f0 = (org.apache.struts.taglib.logic.PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(org.apache.struts.taglib.logic.PresentTag.class);
/*  619 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  620 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/*  622 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/*  623 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  624 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/*  626 */         out.write("\n\talertUser();\n\treturn false;\n\t");
/*  627 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  628 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  632 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  633 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  634 */       return true;
/*      */     }
/*  636 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  642 */     PageContext pageContext = _jspx_page_context;
/*  643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  645 */     org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(org.apache.struts.taglib.logic.NotPresentTag.class);
/*  646 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  647 */     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */     
/*  649 */     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  650 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  651 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/*  653 */         out.write("\n\treturn true;\n\t");
/*  654 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  655 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  659 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  660 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  661 */       return true;
/*      */     }
/*  663 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  664 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  669 */     PageContext pageContext = _jspx_page_context;
/*  670 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  672 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/*  673 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/*  674 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  676 */     _jspx_th_html_005ftext_005f0.setProperty("smtpserver");
/*      */     
/*  678 */     _jspx_th_html_005ftext_005f0.setSize("15");
/*      */     
/*  680 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */     
/*  682 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/*  683 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/*  684 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/*  685 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  686 */       return true;
/*      */     }
/*  688 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  689 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  694 */     PageContext pageContext = _jspx_page_context;
/*  695 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  697 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/*  698 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/*  699 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  701 */     _jspx_th_html_005ftext_005f1.setProperty("smtpport");
/*      */     
/*  703 */     _jspx_th_html_005ftext_005f1.setSize("10");
/*      */     
/*  705 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/*  707 */     _jspx_th_html_005ftext_005f1.setMaxlength("100");
/*  708 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/*  709 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/*  710 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/*  711 */       return true;
/*      */     }
/*  713 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/*  714 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  719 */     PageContext pageContext = _jspx_page_context;
/*  720 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  722 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/*  723 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/*  724 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  726 */     _jspx_th_html_005ftext_005f2.setProperty("SMTPServerUserName");
/*      */     
/*  728 */     _jspx_th_html_005ftext_005f2.setSize("15");
/*      */     
/*  730 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/*  732 */     _jspx_th_html_005ftext_005f2.setMaxlength("25");
/*  733 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/*  734 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/*  735 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/*  736 */       return true;
/*      */     }
/*  738 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/*  739 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  744 */     PageContext pageContext = _jspx_page_context;
/*  745 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  747 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(PasswordTag.class);
/*  748 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/*  749 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  751 */     _jspx_th_html_005fpassword_005f0.setProperty("SMTPServerPassword");
/*      */     
/*  753 */     _jspx_th_html_005fpassword_005f0.setSize("10");
/*      */     
/*  755 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*      */     
/*  757 */     _jspx_th_html_005fpassword_005f0.setMaxlength("25");
/*  758 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/*  759 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/*  760 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/*  761 */       return true;
/*      */     }
/*  763 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/*  764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  769 */     PageContext pageContext = _jspx_page_context;
/*  770 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  772 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/*  773 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/*  774 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  776 */     _jspx_th_html_005ftext_005f3.setProperty("emailAddress");
/*      */     
/*  778 */     _jspx_th_html_005ftext_005f3.setSize("15");
/*      */     
/*  780 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/*  782 */     _jspx_th_html_005ftext_005f3.setMaxlength("100");
/*  783 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/*  784 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/*  785 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/*  786 */       return true;
/*      */     }
/*  788 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/*  789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  794 */     PageContext pageContext = _jspx_page_context;
/*  795 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  797 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/*  798 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/*  799 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  801 */     _jspx_th_html_005ftext_005f4.setProperty("adminemailAddress");
/*      */     
/*  803 */     _jspx_th_html_005ftext_005f4.setSize("15");
/*      */     
/*  805 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/*      */     
/*  807 */     _jspx_th_html_005ftext_005f4.setMaxlength("100");
/*  808 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/*  809 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/*  810 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/*  811 */       return true;
/*      */     }
/*  813 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/*  814 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  819 */     PageContext pageContext = _jspx_page_context;
/*  820 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  822 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/*  823 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/*  824 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/*  826 */     _jspx_th_html_005fradio_005f0.setProperty("useproxy");
/*      */     
/*  828 */     _jspx_th_html_005fradio_005f0.setValue("false");
/*  829 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/*  830 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/*  831 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/*  832 */       return true;
/*      */     }
/*  834 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/*  835 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  840 */     PageContext pageContext = _jspx_page_context;
/*  841 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  843 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/*  844 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/*  845 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/*  847 */     _jspx_th_html_005fradio_005f1.setProperty("useproxy");
/*      */     
/*  849 */     _jspx_th_html_005fradio_005f1.setValue("true");
/*  850 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/*  851 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/*  852 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/*  853 */       return true;
/*      */     }
/*  855 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/*  856 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  861 */     PageContext pageContext = _jspx_page_context;
/*  862 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  864 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  865 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/*  866 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/*  868 */     _jspx_th_html_005ftext_005f5.setProperty("host");
/*      */     
/*  870 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext");
/*  871 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/*  872 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/*  873 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/*  874 */       return true;
/*      */     }
/*  876 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/*  877 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  882 */     PageContext pageContext = _jspx_page_context;
/*  883 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  885 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/*  886 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/*  887 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/*  889 */     _jspx_th_html_005ftext_005f6.setProperty("port");
/*      */     
/*  891 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext");
/*      */     
/*  893 */     _jspx_th_html_005ftext_005f6.setMaxlength("5");
/*  894 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/*  895 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/*  896 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/*  897 */       return true;
/*      */     }
/*  899 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/*  900 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  905 */     PageContext pageContext = _jspx_page_context;
/*  906 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  908 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  909 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/*  910 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/*  912 */     _jspx_th_html_005ftext_005f7.setProperty("userId");
/*      */     
/*  914 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext");
/*  915 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/*  916 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/*  917 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/*  918 */       return true;
/*      */     }
/*  920 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/*  921 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  926 */     PageContext pageContext = _jspx_page_context;
/*  927 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  929 */     PasswordTag _jspx_th_html_005fpassword_005f1 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/*  930 */     _jspx_th_html_005fpassword_005f1.setPageContext(_jspx_page_context);
/*  931 */     _jspx_th_html_005fpassword_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/*  933 */     _jspx_th_html_005fpassword_005f1.setProperty("password");
/*      */     
/*  935 */     _jspx_th_html_005fpassword_005f1.setStyleClass("formtext");
/*  936 */     int _jspx_eval_html_005fpassword_005f1 = _jspx_th_html_005fpassword_005f1.doStartTag();
/*  937 */     if (_jspx_th_html_005fpassword_005f1.doEndTag() == 5) {
/*  938 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/*  939 */       return true;
/*      */     }
/*  941 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f1);
/*  942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  947 */     PageContext pageContext = _jspx_page_context;
/*  948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  950 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/*  951 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/*  952 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/*  954 */     _jspx_th_html_005ftext_005f8.setProperty("userId");
/*      */     
/*  956 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext");
/*  957 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/*  958 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/*  959 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/*  960 */       return true;
/*      */     }
/*  962 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/*  963 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  968 */     PageContext pageContext = _jspx_page_context;
/*  969 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  971 */     PasswordTag _jspx_th_html_005fpassword_005f2 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/*  972 */     _jspx_th_html_005fpassword_005f2.setPageContext(_jspx_page_context);
/*  973 */     _jspx_th_html_005fpassword_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/*  975 */     _jspx_th_html_005fpassword_005f2.setProperty("password");
/*      */     
/*  977 */     _jspx_th_html_005fpassword_005f2.setStyleClass("formtext");
/*  978 */     int _jspx_eval_html_005fpassword_005f2 = _jspx_th_html_005fpassword_005f2.doStartTag();
/*  979 */     if (_jspx_th_html_005fpassword_005f2.doEndTag() == 5) {
/*  980 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f2);
/*  981 */       return true;
/*      */     }
/*  983 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f2);
/*  984 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  989 */     PageContext pageContext = _jspx_page_context;
/*  990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  992 */     MultiboxTag _jspx_th_html_005fmultibox_005f0 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/*  993 */     _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/*  994 */     _jspx_th_html_005fmultibox_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/*  996 */     _jspx_th_html_005fmultibox_005f0.setProperty("bypassproxy");
/*      */     
/*  998 */     _jspx_th_html_005fmultibox_005f0.setValue("true");
/*  999 */     int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/* 1000 */     if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/* 1001 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 1002 */       return true;
/*      */     }
/* 1004 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 1005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1010 */     PageContext pageContext = _jspx_page_context;
/* 1011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1013 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 1014 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 1015 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 1017 */     _jspx_th_html_005ftextarea_005f0.setProperty("dontProxyfor");
/*      */     
/* 1019 */     _jspx_th_html_005ftextarea_005f0.setCols("55");
/*      */     
/* 1021 */     _jspx_th_html_005ftextarea_005f0.setRows("3");
/* 1022 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 1023 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 1024 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 1025 */       return true;
/*      */     }
/* 1027 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 1028 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ffile_005f0(JspTag _jspx_th_html_005fform_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1033 */     PageContext pageContext = _jspx_page_context;
/* 1034 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1036 */     org.apache.struts.taglib.html.FileTag _jspx_th_html_005ffile_005f0 = (org.apache.struts.taglib.html.FileTag)this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.get(org.apache.struts.taglib.html.FileTag.class);
/* 1037 */     _jspx_th_html_005ffile_005f0.setPageContext(_jspx_page_context);
/* 1038 */     _jspx_th_html_005ffile_005f0.setParent((Tag)_jspx_th_html_005fform_005f2);
/*      */     
/* 1040 */     _jspx_th_html_005ffile_005f0.setSize("70");
/*      */     
/* 1042 */     _jspx_th_html_005ffile_005f0.setProperty("theFile");
/* 1043 */     int _jspx_eval_html_005ffile_005f0 = _jspx_th_html_005ffile_005f0.doStartTag();
/* 1044 */     if (_jspx_th_html_005ffile_005f0.doEndTag() == 5) {
/* 1045 */       this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 1046 */       return true;
/*      */     }
/* 1048 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 1049 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1054 */     PageContext pageContext = _jspx_page_context;
/* 1055 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1057 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1058 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 1059 */     _jspx_th_c_005fif_005f3.setParent(null);
/*      */     
/* 1061 */     _jspx_th_c_005fif_005f3.setTest("${!empty param.returnpath}");
/* 1062 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 1063 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 1065 */         out.write("\n                ");
/* 1066 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 1067 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1071 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 1072 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1073 */       return true;
/*      */     }
/* 1075 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1076 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Prerequisites_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */