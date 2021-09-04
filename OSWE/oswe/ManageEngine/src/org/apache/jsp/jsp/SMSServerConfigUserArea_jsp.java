/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class SMSServerConfigUserArea_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   28 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   34 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   35 */   static { _jspx_dependants.put("/jsp/includes/NewActions.jspf", Long.valueOf(1473429417000L));
/*   36 */     _jspx_dependants.put("/jsp/includes/CreateApplicationWizard.jspf", Long.valueOf(1473429417000L));
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   67 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   71 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   77 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   78 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   79 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   80 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   81 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   82 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   85 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   86 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
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
/*  118 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*  119 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  120 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/*  121 */     this._005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  128 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  131 */     JspWriter out = null;
/*  132 */     Object page = this;
/*  133 */     JspWriter _jspx_out = null;
/*  134 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  138 */       response.setContentType("text/html;charset=UTF-8");
/*  139 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  141 */       _jspx_page_context = pageContext;
/*  142 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  143 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  144 */       session = pageContext.getSession();
/*  145 */       out = pageContext.getOut();
/*  146 */       _jspx_out = out;
/*      */       
/*  148 */       out.write("<!--$Id$-->\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n\t<script language=\"JavaScript1.2\">\n\njQuery(document).ready(function(){\n\tvar isDelegatedAdmin=");
/*  149 */       out.print(com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(request.getRemoteUser()));
/*  150 */       out.write("\n\tif(isDelegatedAdmin){\n\tjQuery('input[name=\"Submit\"]').prop(\"disabled\",true);\t\t//NO I18N\n\tjQuery('input[name=\"Submit\"]').attr(\"class\",\"annotate-button-disabled\")\t\t//NO I18N\n\t}\n\t});\nfunction gotoURL(url)\n{\n\twindow.location = url;\n}\n\n\n\tfunction showHide(show)\n\t{\n\n\n\t\tif(show==\"SMS\")\n\t\t{\n\n\t\t\t");
/*  151 */       if ((!System.getProperty("os.name").startsWith("Windows")) || (!System.getProperty("os.name").startsWith("windows"))) {
/*  152 */         out.write("\n\n\n\n\t\t\tjavascript:showDiv('SMSHeading');\n\t\t\tjavascript:showDiv('SMS');\n\n\n");
/*      */       } else {
/*  154 */         out.write("\n\n\n\t\talert(\"");
/*  155 */         out.print(FormatUtil.getString("am.webclient.smsserver.notsupported"));
/*  156 */         out.write("\");\n\n");
/*      */       }
/*  158 */       out.write("\n\t\t}else if(show==\"SMSinitial\"){\n\n\t\t\t");
/*  159 */       if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/*  160 */         out.write("\n\n\n\n\n\t\t\t\tdocument.AMActionForm.action=\"/adminAction.do?method=SMSServerConfiguration&OK=OK\";\n\t\t\t\t");
/*  161 */         request.setAttribute("OK", "OK");
/*  162 */         out.write("\n\t\t\t\t\tdocument.AMActionForm.submit();\n\n\n\n\t\t\t\t");
/*      */       } else {
/*  164 */         out.write("\n\n\n\t\t\t alert(\"");
/*  165 */         out.print(FormatUtil.getString("am.webclient.smsserver.notsupported"));
/*  166 */         out.write("\");\n\n\t\t\t\t\t");
/*      */       }
/*  168 */       out.write("\n\n\n\t\t}\n\n\n\t}\nfunction fnFormSubmit ()\n{\n\t");
/*  169 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/*  171 */       out.write(10);
/*  172 */       out.write(9);
/*  173 */       out.write(9);
/*      */       
/*  175 */       org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(org.apache.struts.taglib.logic.NotPresentTag.class);
/*  176 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  177 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */       
/*  179 */       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  180 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  181 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */         for (;;) {
/*  183 */           out.write("\nvar smsString=document.AMActionForm.SMSPort.value;\nif(smsString.length>8)\n{\n\talert(\"");
/*  184 */           out.print(FormatUtil.getString("am.webclient.smsporno.text"));
/*  185 */           out.write("\");\n\treturn false;\n}\nif(trimAll(document.AMActionForm.SMSPort.value) == '' && (document.AMActionForm.smsMailServer==null)){\n\n\n                alert(\"");
/*  186 */           out.print(FormatUtil.getString("am.webclient.portemp.text"));
/*  187 */           out.write("\");\n                document.AMActionForm.SMSPort.focus();\n                return false;\n\n\n        }else{\n                document.AMActionForm.action=\"/adminAction.do?method=SMSServerConfiguration&OK=OK\";\n                ");
/*  188 */           request.setAttribute("OK", "OK");
/*  189 */           out.write("\n                        document.AMActionForm.submit();\n\n        }\n\n\t\t\t");
/*  190 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  191 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  195 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  196 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */       }
/*      */       else {
/*  199 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  200 */         out.write("\n}\n\n\nfunction myOnLoad()\n{\n");
/*  201 */         if ((request.getAttribute("sms") != null) && (((String)request.getAttribute("sms")).equals("true"))) {
/*  202 */           out.write("\n\n\t\tjavascript:showHide(\"SMS\");\n\n");
/*      */         }
/*      */         
/*      */ 
/*  206 */         out.write("\n\n\n}\n\nfunction showUserPassword()\n{\n  if(document.AMActionForm.SMTPServerAuth.checked)\n  {\n   javascript:showRow(\"username\");\n   javascript:showRow(\"password\");\n  }\n  else\n  {\n   javascript:hideRow(\"username\");\n   javascript:hideRow(\"password\");\n  }\n}\n\nfunction showUserPassword1()\n{\n\n  if(document.AMActionForm.SMTPsecServerAuth.checked)\n  {\n   javascript:showRow(\"username1\");\n   javascript:showRow(\"password1\");\n  }\n  else\n  {\n   javascript:hideRow(\"username1\");\n   javascript:hideRow(\"password1\");\n  }\n}\n\n\n\n</script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  207 */         if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */           return;
/*  209 */         out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n\n\n\n\n");
/*      */         try
/*      */         {
/*  212 */           String message = (String)request.getAttribute("success");
/*  213 */           if (message != null)
/*      */           {
/*  215 */             out.write("\n\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n\t  <tr>\n\t    <td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\">\n\t      <img src=\"/images/icon_message_success.gif\" width=\"25\" height=\"25\" vspace=\"5\"></td>\n\t    <td width=\"95%\" class=\"bodytext\"  > ");
/*  216 */             out.print(message);
/*  217 */             out.write("\n\t    </td>\n\t</tr>\n\t</table>\n\t");
/*      */           }
/*      */         } catch (Exception ex) {
/*  220 */           ex.printStackTrace();
/*      */         }
/*      */         
/*  223 */         out.write(10);
/*  224 */         out.write(10);
/*      */         
/*  226 */         org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/*  227 */         _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  228 */         _jspx_th_html_005fform_005f0.setParent(null);
/*      */         
/*  230 */         _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*      */         
/*  232 */         _jspx_th_html_005fform_005f0.setStyle("display:inline");
/*  233 */         int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  234 */         if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */           for (;;) {
/*  236 */             out.write(10);
/*      */             
/*  238 */             PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  239 */             _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  240 */             _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  242 */             _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/*  243 */             int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  244 */             if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */               for (;;) {
/*  246 */                 out.write(10);
/*      */                 
/*  248 */                 IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  249 */                 _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  250 */                 _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                 
/*  252 */                 _jspx_th_c_005fif_005f0.setTest("${param.admin!='true'}");
/*  253 */                 int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  254 */                 if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                   for (;;) {
/*  256 */                     out.write(10);
/*  257 */                     out.write("<!--$Id$-->\n              \n\n\n\n\n<script>                      \nfunction fnFormSubmit1(object)\n{\n\tlocation.href=object;\n}\n\nfunction showMailServerSettings()\n {\n         var showMail = false;\n         var query = window.location.search;\n         var pairs = query.split(\"&\");\n         \n         for (var i=0;i<pairs.length;i++)\n         {\n                 var pos = pairs[i].indexOf('=');\n                 if (pos >= 0)\n                 {\n                         var argname = pairs[i].substring(0,pos);\n                         var value = pairs[i].substring(pos+1);\n                         if(argname == \"isFromApi\")\n                         {\n                                 showMail = true;\n                         }\n                         //keys[keys.length] = argname;\n                         //values[values.length] = value;\n                 }\n         }\n         //alert(showMail);\n         return  showMail;\n }\n\nfunction doInitStuffOnBodyLoad()\n{\n        ");
/*      */                     
/*  259 */                     if ((pageContext.getAttribute("jsppage") != null) && (pageContext.getAttribute("jsppage").equals("programaction")))
/*      */                     {
/*      */ 
/*  262 */                       out.write("\n        myOnLoad1();\n        ");
/*      */                     }
/*      */                     
/*      */ 
/*  266 */                     out.write("\n        \n\tif(document.AMActionForm!=null)\n\t{\n\tif(typeof(document.AMActionForm.actions)!='undefined')\n\t  {\n\t  if((location.search!= null && (location.search).match(\"EmailAction\")!=null) || (location.path!=null && (location.path).match(\"EMailActionForm\")!=null))\n\t  {\n\t\t\n\t\t");
/*  267 */                     if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                       return;
/*  269 */                     out.write("\t\n\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"SMSAction\")!=null) || (location.path!=null && (location.path).match(\"SMSActionForm\")!=null) || (location.search!= null && (location.search).match(\"SMSServerConfiguration\")!=null))\n\t  {\n\t\t \n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"ExecProg\")!=null) || (location.path!=null && (location.path).match(\"ExecProgramActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"reloadSendTrapActionForm\")!=null) || (location.path!=null && (location.path).match(\"SendTrapActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"Ticket\")!=null) || (location.path!=null && (location.path).match(\"showLogTicket\")!=null) ||  (location.search).match(\"showTicketAction\")!=null )\n");
/*  270 */                     out.write("\t  {\n\t\t\t");
/*  271 */                     if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                       return;
/*  273 */                     out.write("\n\t\t  ");
/*      */                     
/*  275 */                     if ((Constants.sqlManager) || (EnterpriseUtil.isAdminServer()))
/*      */                     {
/*  277 */                       out.write("\n\t\t\tdocument.AMActionForm.actions.selectedIndex=4;\n\t    \t");
/*      */                     }
/*      */                     else
/*      */                     {
/*  281 */                       out.write("\n\t       \tdocument.AMActionForm.actions.selectedIndex=5;\n\t  \t\t");
/*      */                     }
/*  283 */                     out.write("\n\t  }\n\t  else if(location.pathname!= null && (location.pathname).match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(location.search!= null && (location.search).match(\"jre\")!=null || (location.search!=null && (location.search).match(\"ThreadDumpActions\")!=null))\n\t  {\n\t    ");
/*  284 */                     if (EnterpriseUtil.isManagedServer())
/*      */                     {
/*  286 */                       out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=5;\n\t\t");
/*      */                     }
/*      */                     else
/*      */                     {
/*  290 */                       out.write("\n\t    document.AMActionForm.actions.selectedIndex=6;\n        ");
/*      */                     }
/*  292 */                     out.write("\n\t  }\t\n\t  else if(location.search!= null && (location.search).match(\"showVMAction\")!=null || (location.search!=null && (location.search).match(\"ShowVMActions\")!=null))\n\t  {\t\t \n\t    ");
/*  293 */                     if (EnterpriseUtil.isManagedServer())
/*      */                     {
/*  295 */                       out.write("\n\t    if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t    \tdocument.AMActionForm.actions.selectedIndex=9;\n\t    }else{\n\t\t\tdocument.AMActionForm.actions.selectedIndex=7;\n\t    }\n\t\t");
/*      */                     }
/*      */                     else
/*      */                     {
/*  299 */                       out.write("\n\t\t  if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t\t    \tdocument.AMActionForm.actions.selectedIndex=10;\n\t\t    }else{\n\t  \t  document.AMActionForm.actions.selectedIndex=8;\n\t\t    }\n        ");
/*      */                     }
/*  301 */                     out.write("\n\t  }\t  \n\t  else if(location.search!= null && (location.search).match(\"winServAction\")!=null || (location.search!=null && (location.search).match(\"winServAction\")!=null))\n\t  { \n\t    ");
/*  302 */                     if (Constants.sqlManager) {
/*  303 */                       out.write("\n\t      document.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */                     }
/*  305 */                     else if (EnterpriseUtil.isManagedServer()) {
/*  306 */                       out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=8;\n\t\t");
/*      */                     } else {
/*  308 */                       out.write("\n          document.AMActionForm.actions.selectedIndex=9;\n        ");
/*      */                     }
/*  310 */                     out.write("\t\t\n\t  }\t  \n\t   else if((location.search!= null && (location.search).match(\"ExecQueryAction\")!=null) || (location.path!=null && (location.path).match(\"ExecQueryActionForm\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=5;\n  \t   }\n\t   else if((location.search!= null && (location.search).match(\"sqlJobAction\")!=null) || (location.path!=null && (location.path).match(\"sqlJobAction\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=7;\n  \t   }\n\t   else if(location.search!= null && (location.search).match(\"amazon\")!=null)\n\t\t  {\n\t\t    ");
/*  311 */                     if (EnterpriseUtil.isManagedServer()) {
/*  312 */                       out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */                     } else {
/*  314 */                       out.write("\n\t\t    document.AMActionForm.actions.selectedIndex=7;\n        ");
/*      */                     }
/*  316 */                     out.write("\n\t\t  }\n\t  \t  \n\t  }\n\n\tif(document.AMActionForm.selectedBusinessHourID != null)\n\t{\n\t\tinit();\n\t}\n\t\n\t}\n\telse\n\t{\n\t\tif(document.MBeanOperationActionForm.actions!=undefined)\n\t\t{\n\t  \t      document.MBeanOperationActionForm.actions.selectedIndex=4;\t  \n\t  \t}\n\t}\n\n}\nfunction restvalue()\n{\n//alert(document.AMActionForm.actionslist.selectedIndex);\nvar selectedIdx=document.AMActionForm.actionslist.selectedIndex;\ndocument.AMActionForm.reset();\ndocument.AMActionForm.actionslist.selectedIndex=selectedIdx;\n}\n</script>\n");
/*      */                     
/*  318 */                     String action_haid = request.getParameter("haid");
/*  319 */                     String returnpath = "";
/*      */                     
/*  321 */                     if (request.getParameter("returnpath") != null)
/*      */                     {
/*  323 */                       returnpath = "&returnpath=" + java.net.URLEncoder.encode(request.getParameter("returnpath"));
/*      */                     }
/*      */                     
/*      */ 
/*  327 */                     out.write(10);
/*  328 */                     out.write(10);
/*      */                     
/*  330 */                     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  331 */                     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  332 */                     _jspx_th_c_005fset_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  334 */                     _jspx_th_c_005fset_005f0.setVar("isSqlManager");
/*  335 */                     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  336 */                     if (_jspx_eval_c_005fset_005f0 != 0) {
/*  337 */                       if (_jspx_eval_c_005fset_005f0 != 1) {
/*  338 */                         out = _jspx_page_context.pushBody();
/*  339 */                         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  340 */                         _jspx_th_c_005fset_005f0.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  343 */                         out.print(Constants.sqlManager);
/*  344 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  345 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  348 */                       if (_jspx_eval_c_005fset_005f0 != 1) {
/*  349 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  352 */                     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  353 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */                     }
/*      */                     
/*  356 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  357 */                     out.write(10);
/*      */                     
/*  359 */                     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  360 */                     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  361 */                     _jspx_th_c_005fset_005f1.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  363 */                     _jspx_th_c_005fset_005f1.setVar("isIt360");
/*  364 */                     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  365 */                     if (_jspx_eval_c_005fset_005f1 != 0) {
/*  366 */                       if (_jspx_eval_c_005fset_005f1 != 1) {
/*  367 */                         out = _jspx_page_context.pushBody();
/*  368 */                         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/*  369 */                         _jspx_th_c_005fset_005f1.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  372 */                         out.print(Constants.isIt360);
/*  373 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/*  374 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  377 */                       if (_jspx_eval_c_005fset_005f1 != 1) {
/*  378 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  381 */                     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  382 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                     }
/*      */                     
/*  385 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/*  386 */                     out.write(10);
/*      */                     
/*  388 */                     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  389 */                     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/*  390 */                     _jspx_th_c_005fset_005f2.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  392 */                     _jspx_th_c_005fset_005f2.setVar("isAdminServer");
/*  393 */                     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/*  394 */                     if (_jspx_eval_c_005fset_005f2 != 0) {
/*  395 */                       if (_jspx_eval_c_005fset_005f2 != 1) {
/*  396 */                         out = _jspx_page_context.pushBody();
/*  397 */                         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/*  398 */                         _jspx_th_c_005fset_005f2.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  401 */                         out.print(EnterpriseUtil.isAdminServer());
/*  402 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/*  403 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  406 */                       if (_jspx_eval_c_005fset_005f2 != 1) {
/*  407 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  410 */                     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/*  411 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */                     }
/*      */                     
/*  414 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/*  415 */                     out.write(10);
/*      */                     
/*  417 */                     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  418 */                     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/*  419 */                     _jspx_th_c_005fset_005f3.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  421 */                     _jspx_th_c_005fset_005f3.setVar("isProfServer");
/*  422 */                     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/*  423 */                     if (_jspx_eval_c_005fset_005f3 != 0) {
/*  424 */                       if (_jspx_eval_c_005fset_005f3 != 1) {
/*  425 */                         out = _jspx_page_context.pushBody();
/*  426 */                         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/*  427 */                         _jspx_th_c_005fset_005f3.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  430 */                         out.print(EnterpriseUtil.isProfEdition());
/*  431 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/*  432 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  435 */                       if (_jspx_eval_c_005fset_005f3 != 1) {
/*  436 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  439 */                     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/*  440 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */                     }
/*      */                     
/*  443 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/*  444 */                     out.write(10);
/*      */                     
/*  446 */                     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  447 */                     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/*  448 */                     _jspx_th_c_005fset_005f4.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  450 */                     _jspx_th_c_005fset_005f4.setVar("isCloudServer");
/*  451 */                     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/*  452 */                     if (_jspx_eval_c_005fset_005f4 != 0) {
/*  453 */                       if (_jspx_eval_c_005fset_005f4 != 1) {
/*  454 */                         out = _jspx_page_context.pushBody();
/*  455 */                         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/*  456 */                         _jspx_th_c_005fset_005f4.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  459 */                         out.print(EnterpriseUtil.isCloudEdition());
/*  460 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/*  461 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  464 */                       if (_jspx_eval_c_005fset_005f4 != 1) {
/*  465 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  468 */                     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/*  469 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */                     }
/*      */                     
/*  472 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/*  473 */                     out.write("\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" >\n  <tr> \n    <td align=\"left\" width=\"50%\" class=\"bodytext\">");
/*  474 */                     out.print(FormatUtil.getString("am.webclient.newaction.selectactiontype"));
/*  475 */                     out.write("&nbsp;\n\t<select id=\"actionslist\" name=\"actions\" onchange=\"javascript:fnFormSubmit1(this.form.actions.options[this.selectedIndex].value);\" class=\"formtext\">\n\t");
/*      */                     
/*  477 */                     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  478 */                     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  479 */                     _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f0);
/*  480 */                     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  481 */                     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                       for (;;) {
/*  483 */                         out.write(10);
/*  484 */                         out.write(9);
/*      */                         
/*  486 */                         WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  487 */                         _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  488 */                         _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                         
/*  490 */                         _jspx_th_c_005fwhen_005f0.setTest("${empty param.global}");
/*  491 */                         int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  492 */                         if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                           for (;;) {
/*  494 */                             out.write("\t\n\t\t<option value=\"/showTile.do?TileName=.EmailActions&haid=");
/*  495 */                             out.print(action_haid);
/*  496 */                             out.print(returnpath);
/*  497 */                             out.write(34);
/*  498 */                             out.write(62);
/*  499 */                             out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  500 */                             out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.SMSActions&haid=");
/*  501 */                             out.print(action_haid);
/*  502 */                             out.print(returnpath);
/*  503 */                             out.write(34);
/*  504 */                             out.write(62);
/*  505 */                             out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  506 */                             out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.ExecProg&haid=");
/*  507 */                             out.print(action_haid);
/*  508 */                             out.print(returnpath);
/*  509 */                             out.write(34);
/*  510 */                             out.write(62);
/*  511 */                             out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  512 */                             out.write("</option>\n\t\t<option value=\"/adminAction.do?method=reloadSendTrapActionForm&haid=");
/*  513 */                             out.print(action_haid);
/*  514 */                             out.print(returnpath);
/*  515 */                             out.write(34);
/*  516 */                             out.write(62);
/*  517 */                             out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/*  518 */                             out.write("</option>\n\t\t\n\t\t");
/*      */                             
/*  520 */                             ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  521 */                             _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  522 */                             _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*  523 */                             int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  524 */                             if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                               for (;;) {
/*  526 */                                 out.write("\n\t\t\t");
/*      */                                 
/*  528 */                                 WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  529 */                                 _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  530 */                                 _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                 
/*  532 */                                 _jspx_th_c_005fwhen_005f1.setTest("${!isIt360}");
/*  533 */                                 int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  534 */                                 if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                   for (;;) {
/*  536 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/*  538 */                                     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  539 */                                     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  540 */                                     _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/*  541 */                                     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  542 */                                     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                       for (;;) {
/*  544 */                                         out.write("\n\t\t\t\t\t");
/*      */                                         
/*  546 */                                         WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  547 */                                         _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  548 */                                         _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                         
/*  550 */                                         _jspx_th_c_005fwhen_005f2.setTest("${!isSqlManager}");
/*  551 */                                         int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  552 */                                         if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                           for (;;) {
/*  554 */                                             out.write("\n\t\t\t\t\t\t<!-- MBean Operation-->\n\t\t\t\t\t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  555 */                                             out.print(action_haid);
/*  556 */                                             out.print(returnpath);
/*  557 */                                             out.write(34);
/*  558 */                                             out.write(62);
/*  559 */                                             out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  560 */                                             out.write("</option>\n\t\t\t\t\t");
/*  561 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  562 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/*  566 */                                         if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  567 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                         }
/*      */                                         
/*  570 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  571 */                                         out.write("\n\t\t\t\t\t");
/*      */                                         
/*  573 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  574 */                                         _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  575 */                                         _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f2);
/*  576 */                                         int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  577 */                                         if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                           for (;;) {
/*  579 */                                             out.write("\n\t\t\t\t\t\t<!-- Execute Query Action-->\n\t   \t\t\t\t\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  580 */                                             out.print(action_haid);
/*  581 */                                             out.print(returnpath);
/*  582 */                                             out.write(34);
/*  583 */                                             out.write(62);
/*  584 */                                             out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  585 */                                             out.write("</option>\n\t   \t\t\t\t\t<!-- Sql job action -->\n\t\t\t\t\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  586 */                                             out.print(action_haid);
/*  587 */                                             out.print(returnpath);
/*  588 */                                             out.write(34);
/*  589 */                                             out.write(62);
/*  590 */                                             out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  591 */                                             out.write("</option>\n\t\t\t\t\t");
/*  592 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  593 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/*  597 */                                         if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  598 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                         }
/*      */                                         
/*  601 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  602 */                                         out.write("\n\t\t\t\t");
/*  603 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  604 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/*  608 */                                     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  609 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                     }
/*      */                                     
/*  612 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  613 */                                     out.write("\n\t\t\t\t\t<!-- Log Ticket Operation-->\n\t\t\t\t\t");
/*  614 */                                     if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  615 */                                       out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  616 */                                       out.print(action_haid);
/*  617 */                                       out.print(returnpath);
/*  618 */                                       out.write(34);
/*  619 */                                       out.write(62);
/*  620 */                                       out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  621 */                                       out.write("</option> ");
/*      */                                     }
/*  623 */                                     out.write("\n\t\t\t");
/*  624 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  625 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  629 */                                 if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  630 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                 }
/*      */                                 
/*  633 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  634 */                                 out.write("\n\t\t\t");
/*      */                                 
/*  636 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  637 */                                 _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  638 */                                 _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  639 */                                 int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  640 */                                 if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                   for (;;) {
/*  642 */                                     out.write("\n\t\t   \t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  643 */                                     out.print(action_haid);
/*  644 */                                     out.print(returnpath);
/*  645 */                                     out.write(34);
/*  646 */                                     out.write(62);
/*  647 */                                     out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  648 */                                     out.write("</option>\n\t\t   \t\t<!-- Log Ticket Operation-->\n\t\t   \t\t");
/*      */                                     
/*  650 */                                     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  651 */                                     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  652 */                                     _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                                     
/*  654 */                                     _jspx_th_c_005fif_005f3.setTest("${isProfServer || isAdminServer}");
/*  655 */                                     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  656 */                                     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                       for (;;) {
/*  658 */                                         out.write("\n\t\t\t\t\t");
/*  659 */                                         if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  660 */                                           out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  661 */                                           out.print(action_haid);
/*  662 */                                           out.print(returnpath);
/*  663 */                                           out.write(34);
/*  664 */                                           out.write(62);
/*  665 */                                           out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  666 */                                           out.write("</option> ");
/*      */                                         }
/*  668 */                                         out.write("\n\t\t   \t\t");
/*  669 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  670 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/*  674 */                                     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  675 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                     }
/*      */                                     
/*  678 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  679 */                                     out.write("\n\t\t\t");
/*  680 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  681 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  685 */                                 if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  686 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                 }
/*      */                                 
/*  689 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  690 */                                 out.write(10);
/*  691 */                                 out.write(9);
/*  692 */                                 out.write(9);
/*  693 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  694 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  698 */                             if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  699 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                             }
/*      */                             
/*  702 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  703 */                             out.write(10);
/*  704 */                             out.write(9);
/*  705 */                             out.write(9);
/*      */                             
/*  707 */                             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  708 */                             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  709 */                             _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                             
/*  711 */                             _jspx_th_c_005fif_005f4.setTest("${!isAdminServer}");
/*  712 */                             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  713 */                             if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                               for (;;) {
/*  715 */                                 out.write("\n\t\t\t<!--JRE Action -->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  716 */                                 out.print(action_haid);
/*  717 */                                 out.print(returnpath);
/*  718 */                                 out.write(34);
/*  719 */                                 out.write(62);
/*  720 */                                 out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  721 */                                 out.write("</option>\n\t\t\t<!--Amazon Instance Action-->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  722 */                                 out.print(action_haid);
/*  723 */                                 out.print(returnpath);
/*  724 */                                 out.write(34);
/*  725 */                                 out.write(62);
/*  726 */                                 out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  727 */                                 out.write("</option>\n\t\t\t<!--VM Action -->\n\t      \t<option value=\"/adminAction.do?method=showVMAction&haid=");
/*  728 */                                 out.print(action_haid);
/*  729 */                                 out.print(returnpath);
/*  730 */                                 out.write(34);
/*  731 */                                 out.write(62);
/*  732 */                                 out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  733 */                                 out.write("</option>\n\t      \t\n\t\t\t<!-- Windows Service action -->\n\t\t\t");
/*      */                                 
/*  735 */                                 IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  736 */                                 _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  737 */                                 _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fif_005f4);
/*      */                                 
/*  739 */                                 _jspx_th_c_005fif_005f5.setTest("${!isCloudServer}");
/*  740 */                                 int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  741 */                                 if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                   for (;;) {
/*  743 */                                     out.write("\n\t   \t\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  744 */                                     out.print(action_haid);
/*  745 */                                     out.print(returnpath);
/*  746 */                                     out.write(34);
/*  747 */                                     out.write(62);
/*  748 */                                     out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  749 */                                     out.write("</option>\n\t   \t\t");
/*  750 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/*  751 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  755 */                                 if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/*  756 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                 }
/*      */                                 
/*  759 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*  760 */                                 out.write("\n\t   \t\t<option value=\"/adminAction.do?method=showVMAction&isContainerAction=true&haid=");
/*  761 */                                 out.print(action_haid);
/*  762 */                                 out.print(returnpath);
/*  763 */                                 out.write(34);
/*  764 */                                 out.write(62);
/*  765 */                                 out.print(FormatUtil.getString("am.container.action.createnew"));
/*  766 */                                 out.write("</option>\n   \t\t");
/*  767 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  768 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  772 */                             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  773 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                             }
/*      */                             
/*  776 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  777 */                             out.write(10);
/*  778 */                             out.write(9);
/*  779 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  780 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  784 */                         if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  785 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                         }
/*      */                         
/*  788 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  789 */                         out.write(10);
/*  790 */                         out.write(9);
/*      */                         
/*  792 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  793 */                         _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  794 */                         _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*  795 */                         int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  796 */                         if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                           for (;;) {
/*  798 */                             out.write(10);
/*      */                             
/*  800 */                             String redirectTo = null;
/*  801 */                             if (request.getParameter("redirectto") != null)
/*      */                             {
/*  803 */                               redirectTo = java.net.URLEncoder.encode(request.getParameter("redirectto"));
/*      */                             }
/*      */                             else
/*      */                             {
/*  807 */                               redirectTo = java.net.URLEncoder.encode("/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true");
/*      */                             }
/*      */                             
/*  810 */                             out.write("\t\n\t<option value=\"/showTile.do?TileName=.EmailActions&PRINTER_FRIENDLY=true&haid=");
/*  811 */                             out.print(action_haid);
/*  812 */                             out.write("&global=true");
/*  813 */                             out.print(returnpath);
/*  814 */                             out.write(34);
/*  815 */                             out.write(62);
/*  816 */                             out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  817 */                             out.write("</option>\n\t<option value=\"/showTile.do?TileName=.SMSActions&PRINTER_FRIENDLY=true&haid=");
/*  818 */                             out.print(action_haid);
/*  819 */                             out.write("&global=true");
/*  820 */                             out.print(returnpath);
/*  821 */                             out.write(34);
/*  822 */                             out.write(62);
/*  823 */                             out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  824 */                             out.write("</option>\n\t");
/*      */                             
/*  826 */                             PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  827 */                             _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/*  828 */                             _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                             
/*  830 */                             _jspx_th_logic_005fpresent_005f2.setRole("ADMIN,ENTERPRISEADMIN");
/*  831 */                             int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/*  832 */                             if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                               for (;;) {
/*  834 */                                 out.write(32);
/*  835 */                                 out.write("\n\t<option value=\"/jsp/ExecProgramActionForm.jsp?haid=");
/*  836 */                                 out.print(action_haid);
/*  837 */                                 out.write("&global=true");
/*  838 */                                 out.print(returnpath);
/*  839 */                                 out.write(34);
/*  840 */                                 out.write(62);
/*  841 */                                 out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  842 */                                 out.write("</option>\n\t");
/*  843 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/*  844 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  848 */                             if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/*  849 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                             }
/*      */                             
/*  852 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/*  853 */                             out.write("\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=11&haid=");
/*  854 */                             out.print(action_haid);
/*  855 */                             out.write("&global=true");
/*  856 */                             out.print(returnpath);
/*  857 */                             out.write(34);
/*  858 */                             out.write(62);
/*  859 */                             out.print(FormatUtil.getString("am.webclient.common.sendv1trap.text"));
/*  860 */                             out.write("</option>\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=12&haid=");
/*  861 */                             out.print(action_haid);
/*  862 */                             out.write("&global=true");
/*  863 */                             out.print(returnpath);
/*  864 */                             out.write(34);
/*  865 */                             out.write(62);
/*  866 */                             out.print(FormatUtil.getString("am.webclient.common.sendv2ctrap.text"));
/*  867 */                             out.write("</option>\n\t");
/*  868 */                             if ((!Constants.sqlManager) && ((!Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/*  869 */                               out.write(32);
/*  870 */                               out.write("\n\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&popup=true&global=true&haid=");
/*  871 */                               out.print(action_haid);
/*  872 */                               out.print(returnpath);
/*  873 */                               out.write(34);
/*  874 */                               out.write(62);
/*  875 */                               out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  876 */                               out.write("</option>\n\t");
/*      */                             }
/*  878 */                             out.write(10);
/*  879 */                             out.write(9);
/*  880 */                             out.write(9);
/*  881 */                             out.write(10);
/*  882 */                             out.write(9);
/*  883 */                             if ((!Constants.isIt360) || (EnterpriseUtil.isProfEdition()) || (EnterpriseUtil.isAdminServer()))
/*      */                             {
/*  885 */                               out.write("<option value=\"/adminAction.do?method=showLogTicket&global=true&haid=");
/*  886 */                               out.print(action_haid);
/*  887 */                               out.write("&redirectTo=");
/*  888 */                               out.print(redirectTo);
/*  889 */                               out.write(34);
/*  890 */                               out.write(62);
/*  891 */                               out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  892 */                               out.write("</option> ");
/*      */                             }
/*      */                             
/*  895 */                             out.write("\n\t\n\t");
/*  896 */                             if ((!Constants.sqlManager) && ((!Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/*  897 */                               out.write(" \n\t<!--JRE Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  898 */                               out.print(action_haid);
/*  899 */                               out.write("&global=true");
/*  900 */                               out.print(returnpath);
/*  901 */                               out.write("&ext=true\">");
/*  902 */                               out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  903 */                               out.write("</option>\n\t<!--VM Action -->\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&haid=");
/*  904 */                               out.print(action_haid);
/*  905 */                               out.print(returnpath);
/*  906 */                               out.write("&ext=true&global=true\">");
/*  907 */                               out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  908 */                               out.write("</option>\n\t<!--Amazon Instance Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  909 */                               out.print(action_haid);
/*  910 */                               out.write("&global=true");
/*  911 */                               out.print(returnpath);
/*  912 */                               out.write("&ext=true\">");
/*  913 */                               out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  914 */                               out.write("</option>\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&isContainerAction=true&haid=");
/*  915 */                               out.print(action_haid);
/*  916 */                               out.print(returnpath);
/*  917 */                               out.write("&ext=true&global=true\">");
/*  918 */                               out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  919 */                               out.write("</option>\n\t ");
/*  920 */                             } else if (Constants.sqlManager) {
/*  921 */                               out.write(32);
/*  922 */                               out.write("\n\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  923 */                               out.print(action_haid);
/*  924 */                               out.write("&global=true");
/*  925 */                               out.print(returnpath);
/*  926 */                               out.write(34);
/*  927 */                               out.write(62);
/*  928 */                               out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  929 */                               out.write("</option>\n\t");
/*      */                             }
/*  931 */                             out.write(10);
/*  932 */                             out.write(9);
/*  933 */                             if ((!Constants.sqlManager) && ((!Constants.isIt360) || (!EnterpriseUtil.isAdminServer())) && (!"CLOUD".equalsIgnoreCase(Constants.getCategorytype()))) {
/*  934 */                               out.write("\n\t<!-- Windows Service action -->\n\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  935 */                               out.print(action_haid);
/*  936 */                               out.print(returnpath);
/*  937 */                               out.write(34);
/*  938 */                               out.write(62);
/*  939 */                               out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  940 */                               out.write("</option>\t\n\t");
/*      */                             }
/*  942 */                             out.write(10);
/*  943 */                             out.write(9);
/*  944 */                             out.write(32);
/*  945 */                             if (Constants.sqlManager) {
/*  946 */                               out.write("\n\t<!-- Sql job action -->\n\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  947 */                               out.print(action_haid);
/*  948 */                               out.print(returnpath);
/*  949 */                               out.write(34);
/*  950 */                               out.write(62);
/*  951 */                               out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  952 */                               out.write("</option>\n\t");
/*      */                             }
/*  954 */                             out.write(10);
/*  955 */                             out.write(9);
/*  956 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  957 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  961 */                         if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  962 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                         }
/*      */                         
/*  965 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  966 */                         out.write(10);
/*  967 */                         out.write(9);
/*  968 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  969 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  973 */                     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  974 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                     }
/*      */                     
/*  977 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  978 */                     out.write("\n\t</select>\n    </td>\n  </tr>\n</table>\n\n");
/*      */                     
/*  980 */                     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  981 */                     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/*  982 */                     _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  984 */                     _jspx_th_c_005fif_005f6.setTest("${param.global=='true'}");
/*  985 */                     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/*  986 */                     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                       for (;;) {
/*  988 */                         out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td valign=\"top\"> \n<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/*  989 */                         out.write("<!--$Id$-->\n\n\n\n");
/*  990 */                         if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                           return;
/*  992 */                         out.write("\n      \n\n");
/*      */                         
/*  994 */                         IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  995 */                         _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/*  996 */                         _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f6);
/*      */                         
/*  998 */                         _jspx_th_c_005fif_005f7.setTest("${(uri=='/jsp/CreateApplication.jsp')|| uri=='/admin/createapplication.do'||uri=='/admin/createapplication.do' ||(!empty param.wiz && !empty param.haid && (empty invalidhaid))||(param.method=='insert')}");
/*  999 */                         int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 1000 */                         if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                           for (;;) {
/* 1002 */                             out.write("\n\t  <tr>\n\t  <td class=\"tdindent\">\n");
/* 1003 */                             if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1005 */                             out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">  \n  <tr> \n    <td width=\"2%\">");
/*      */                             
/* 1007 */                             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1008 */                             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 1009 */                             _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f7);
/*      */                             
/* 1011 */                             _jspx_th_c_005fif_005f8.setTest("${uri=='/jsp/CreateApplication.jsp' || uri=='/admin/createapplicationwiz.do'||uri=='/admin/createapplication.do'}");
/* 1012 */                             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 1013 */                             if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                               for (;;) {
/* 1015 */                                 out.write("\n    \t");
/*      */                                 
/* 1017 */                                 SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1018 */                                 _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 1019 */                                 _jspx_th_c_005fset_005f6.setParent(_jspx_th_c_005fif_005f8);
/*      */                                 
/* 1021 */                                 _jspx_th_c_005fset_005f6.setVar("wizimage");
/* 1022 */                                 int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 1023 */                                 if (_jspx_eval_c_005fset_005f6 != 0) {
/* 1024 */                                   if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1025 */                                     out = _jspx_page_context.pushBody();
/* 1026 */                                     _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 1027 */                                     _jspx_th_c_005fset_005f6.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 1030 */                                     out.write(" \n    \t<img src=\"/images/wiz_newbizapp_high.gif\" border=\"0\" align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1031 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1032 */                                     out.write(" </b></font>\n    \t");
/* 1033 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 1034 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 1037 */                                   if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1038 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 1041 */                                 if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 1042 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6); return;
/*      */                                 }
/*      */                                 
/* 1045 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 1046 */                                 out.write("\n    ");
/* 1047 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 1048 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1052 */                             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 1053 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                             }
/*      */                             
/* 1056 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 1057 */                             out.write("\n    ");
/*      */                             
/* 1059 */                             IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1060 */                             _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 1061 */                             _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f7);
/*      */                             
/* 1063 */                             _jspx_th_c_005fif_005f9.setTest("${uri!='/jsp/CreateApplication.jsp' && uri!='/admin/createapplicationwiz.do'&& uri!='/admin/createapplication.do'}");
/* 1064 */                             int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 1065 */                             if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                               for (;;) {
/* 1067 */                                 out.write("\n    \t");
/*      */                                 
/* 1069 */                                 SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1070 */                                 _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 1071 */                                 _jspx_th_c_005fset_005f7.setParent(_jspx_th_c_005fif_005f9);
/*      */                                 
/* 1073 */                                 _jspx_th_c_005fset_005f7.setVar("wizimage");
/* 1074 */                                 int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 1075 */                                 if (_jspx_eval_c_005fset_005f7 != 0) {
/* 1076 */                                   if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1077 */                                     out = _jspx_page_context.pushBody();
/* 1078 */                                     _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 1079 */                                     _jspx_th_c_005fset_005f7.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 1082 */                                     out.write("\n    \t<img src=\"/images/wiz_newbizapp_nor.gif\" border=0> <font family=\"verdana\" size=\"2\" color=\"#818181\">");
/* 1083 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1084 */                                     out.write("</font>  \t");
/* 1085 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 1086 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 1089 */                                   if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1090 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 1093 */                                 if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 1094 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                                 }
/*      */                                 
/* 1097 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 1098 */                                 out.write("\n    ");
/* 1099 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 1100 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1104 */                             if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 1105 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                             }
/*      */                             
/* 1108 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 1109 */                             out.write("      \n\t</td>\n    <td width=\"20%\">");
/* 1110 */                             if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1112 */                             out.write("</td>  \n   \n");
/*      */                             
/* 1114 */                             ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1115 */                             _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1116 */                             _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f7);
/* 1117 */                             int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1118 */                             if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                               for (;;) {
/* 1120 */                                 out.write("\n    ");
/*      */                                 
/* 1122 */                                 WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1123 */                                 _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1124 */                                 _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                 
/* 1126 */                                 _jspx_th_c_005fwhen_005f3.setTest("${( param.method=='configureHostDiscovery' || param.method=='associateMonitors'||param.method=='getMonitorForm'||param.method=='addResource')&& (empty showwiz3) && (empty UrlForm) }");
/* 1127 */                                 int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1128 */                                 if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                   for (;;) {
/* 1130 */                                     out.write("\n    \n\t\n\t\t\n\t\t");
/*      */                                     
/* 1132 */                                     SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1133 */                                     _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 1134 */                                     _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                                     
/* 1136 */                                     _jspx_th_c_005fset_005f8.setVar("wizimage");
/* 1137 */                                     int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 1138 */                                     if (_jspx_eval_c_005fset_005f8 != 0) {
/* 1139 */                                       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1140 */                                         out = _jspx_page_context.pushBody();
/* 1141 */                                         _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 1142 */                                         _jspx_th_c_005fset_005f8.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 1145 */                                         out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1146 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1147 */                                         out.write(" </b></font>\n    \t");
/* 1148 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 1149 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 1152 */                                       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1153 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 1156 */                                     if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 1157 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                                     }
/*      */                                     
/* 1160 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 1161 */                                     out.write("\n   ");
/* 1162 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1163 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1167 */                                 if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1168 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                 }
/*      */                                 
/* 1171 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1172 */                                 out.write("\n   ");
/*      */                                 
/* 1174 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1175 */                                 _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1176 */                                 _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 1177 */                                 int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1178 */                                 if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                   for (;;) {
/* 1180 */                                     out.write("  \n    \t\n\t\t");
/*      */                                     
/* 1182 */                                     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1183 */                                     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 1184 */                                     _jspx_th_c_005fset_005f9.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                                     
/* 1186 */                                     _jspx_th_c_005fset_005f9.setVar("wizimage");
/* 1187 */                                     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 1188 */                                     if (_jspx_eval_c_005fset_005f9 != 0) {
/* 1189 */                                       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1190 */                                         out = _jspx_page_context.pushBody();
/* 1191 */                                         _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 1192 */                                         _jspx_th_c_005fset_005f9.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 1195 */                                         out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1196 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1197 */                                         out.write(" </font>\n    \t");
/* 1198 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 1199 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 1202 */                                       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1203 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 1206 */                                     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 1207 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                                     }
/*      */                                     
/* 1210 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 1211 */                                     out.write("\n\t\n\t\t\n   ");
/* 1212 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1213 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1217 */                                 if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1218 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                                 }
/*      */                                 
/* 1221 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1222 */                                 out.write(10);
/* 1223 */                                 out.write(32);
/* 1224 */                                 out.write(32);
/* 1225 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1226 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1230 */                             if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1231 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                             }
/*      */                             
/* 1234 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1235 */                             out.write(" \n\n    \n     <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"20%\">\n    ");
/* 1236 */                             if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1238 */                             out.write("\n    \t");
/* 1239 */                             if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1241 */                             out.write("\n    \t\n    \t");
/* 1242 */                             if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1244 */                             out.write("\n    \t</td>    \t\n    \t<!-- ############################################# check for third tab #####################################  -->\n       ");
/*      */                             
/* 1246 */                             ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1247 */                             _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1248 */                             _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f7);
/* 1249 */                             int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1250 */                             if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                               for (;;) {
/* 1252 */                                 out.write("\n       ");
/*      */                                 
/* 1254 */                                 WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1255 */                                 _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1256 */                                 _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                 
/* 1258 */                                 _jspx_th_c_005fwhen_005f4.setTest("${(param.method=='reloadHostDiscoveryForm'|| param.method=='getMonitorForm'||param.method=='addResource'|| (!empty showwiz3) || (!empty UrlForm) ) && (param.method!='getHAProfiles') }");
/* 1259 */                                 int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1260 */                                 if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                   for (;;) {
/* 1262 */                                     out.write("\n   \n   \t    \t");
/*      */                                     
/* 1264 */                                     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1265 */                                     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 1266 */                                     _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                                     
/* 1268 */                                     _jspx_th_c_005fset_005f10.setVar("wizimage");
/* 1269 */                                     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 1270 */                                     if (_jspx_eval_c_005fset_005f10 != 0) {
/* 1271 */                                       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1272 */                                         out = _jspx_page_context.pushBody();
/* 1273 */                                         _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 1274 */                                         _jspx_th_c_005fset_005f10.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 1277 */                                         out.write("\n   \t    \t<img src=\"/images/new_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b> ");
/* 1278 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1279 */                                         out.write(" </b></font>\n   \t    \t");
/* 1280 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 1281 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 1284 */                                       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1285 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 1288 */                                     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 1289 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                                     }
/*      */                                     
/* 1292 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 1293 */                                     out.write("\n       ");
/* 1294 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1295 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1299 */                                 if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1300 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                                 }
/*      */                                 
/* 1303 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1304 */                                 out.write("\n        ");
/*      */                                 
/* 1306 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1307 */                                 _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1308 */                                 _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1309 */                                 int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1310 */                                 if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                   for (;;) {
/* 1312 */                                     out.write("  \n   \t    \t");
/*      */                                     
/* 1314 */                                     SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1315 */                                     _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 1316 */                                     _jspx_th_c_005fset_005f11.setParent(_jspx_th_c_005fotherwise_005f4);
/*      */                                     
/* 1318 */                                     _jspx_th_c_005fset_005f11.setVar("wizimage");
/* 1319 */                                     int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 1320 */                                     if (_jspx_eval_c_005fset_005f11 != 0) {
/* 1321 */                                       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1322 */                                         out = _jspx_page_context.pushBody();
/* 1323 */                                         _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 1324 */                                         _jspx_th_c_005fset_005f11.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 1327 */                                         out.write("\n\t\t   \t    \t<img src=\"/images/new_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1328 */                                         out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1329 */                                         out.write(" </font>\n   \t    \t");
/* 1330 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 1331 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 1334 */                                       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1335 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 1338 */                                     if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 1339 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11); return;
/*      */                                     }
/*      */                                     
/* 1342 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 1343 */                                     out.write("\n   \t");
/* 1344 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1345 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1349 */                                 if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1350 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                                 }
/*      */                                 
/* 1353 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1354 */                                 out.write(10);
/* 1355 */                                 out.write(32);
/* 1356 */                                 out.write(32);
/* 1357 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1358 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1362 */                             if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1363 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                             }
/*      */                             
/* 1366 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1367 */                             out.write(" \n\n        <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n       <td width=\"18%\">\n       ");
/* 1368 */                             if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1370 */                             out.write("\n       ");
/* 1371 */                             if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1373 */                             out.write("\n       ");
/* 1374 */                             out.write("\n       \t");
/* 1375 */                             if (_jspx_meth_c_005fif_005f13(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1377 */                             out.write("\n    \t</td>\n   \n  <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"17%\">\n\t");
/*      */                             
/* 1379 */                             IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1380 */                             _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 1381 */                             _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f7);
/*      */                             
/* 1383 */                             _jspx_th_c_005fif_005f14.setTest("${param.method=='getHAProfiles'}");
/* 1384 */                             int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 1385 */                             if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                               for (;;) {
/* 1387 */                                 out.write(10);
/* 1388 */                                 out.write(9);
/*      */                                 
/* 1390 */                                 SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1391 */                                 _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 1392 */                                 _jspx_th_c_005fset_005f12.setParent(_jspx_th_c_005fif_005f14);
/*      */                                 
/* 1394 */                                 _jspx_th_c_005fset_005f12.setVar("wizimage");
/* 1395 */                                 int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 1396 */                                 if (_jspx_eval_c_005fset_005f12 != 0) {
/* 1397 */                                   if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1398 */                                     out = _jspx_page_context.pushBody();
/* 1399 */                                     _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 1400 */                                     _jspx_th_c_005fset_005f12.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 1403 */                                     out.write("\n\t\t<img src=\"/images/wiz_configurealerts_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b>");
/* 1404 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1405 */                                     out.write(" </b></font>\n    \t");
/* 1406 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 1407 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 1410 */                                   if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1411 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 1414 */                                 if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 1415 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12); return;
/*      */                                 }
/*      */                                 
/* 1418 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 1419 */                                 out.write(10);
/* 1420 */                                 out.write(9);
/* 1421 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 1422 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1426 */                             if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 1427 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                             }
/*      */                             
/* 1430 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 1431 */                             out.write(10);
/* 1432 */                             out.write(9);
/*      */                             
/* 1434 */                             IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1435 */                             _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 1436 */                             _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f7);
/*      */                             
/* 1438 */                             _jspx_th_c_005fif_005f15.setTest("${param.method!='getHAProfiles'}");
/* 1439 */                             int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 1440 */                             if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                               for (;;) {
/* 1442 */                                 out.write(10);
/* 1443 */                                 out.write(9);
/*      */                                 
/* 1445 */                                 SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1446 */                                 _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 1447 */                                 _jspx_th_c_005fset_005f13.setParent(_jspx_th_c_005fif_005f15);
/*      */                                 
/* 1449 */                                 _jspx_th_c_005fset_005f13.setVar("wizimage");
/* 1450 */                                 int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 1451 */                                 if (_jspx_eval_c_005fset_005f13 != 0) {
/* 1452 */                                   if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1453 */                                     out = _jspx_page_context.pushBody();
/* 1454 */                                     _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 1455 */                                     _jspx_th_c_005fset_005f13.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 1458 */                                     out.write("\n\t\t<img src=\"/images/wiz_configurealerts_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1459 */                                     out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1460 */                                     out.write(" </font>\n    \t");
/* 1461 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 1462 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 1465 */                                   if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1466 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 1469 */                                 if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 1470 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13); return;
/*      */                                 }
/*      */                                 
/* 1473 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 1474 */                                 out.write("\n\t\n\t");
/* 1475 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 1476 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1480 */                             if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 1481 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                             }
/*      */                             
/* 1484 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 1485 */                             out.write(10);
/* 1486 */                             if (_jspx_meth_c_005fif_005f16(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1488 */                             out.write("   \n ");
/* 1489 */                             if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1491 */                             out.write(10);
/* 1492 */                             out.write(32);
/* 1493 */                             out.write(10);
/* 1494 */                             if (_jspx_meth_c_005fif_005f17(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                               return;
/* 1496 */                             out.write("   \n    </td>\n    \n    <td width=\"2%\" rowspan=\"2\" valign=\"bottom\" align=\"right\"><img src=\"/images/wiz_endicon.gif\" width=\"33\" height=\"36\"></td>\n  </tr>\n  <tr background=\"/images/wiz_bg_graylind.gif\"> \n    <td><img src=\"/images/wiz_startimage.gif\" width=\"18\" height=\"16\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n   \n   <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n \n");
/* 1497 */                             out.write("  </tr>\n\n</table>\n</td></tr>\n");
/* 1498 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1499 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1503 */                         if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1504 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                         }
/*      */                         
/* 1507 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1508 */                         out.write(10);
/* 1509 */                         out.write(10);
/* 1510 */                         if (request.getAttribute("EmailForm") == null) {
/* 1511 */                           out.write(10);
/*      */                           
/* 1513 */                           MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1514 */                           _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 1515 */                           _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                           
/* 1517 */                           _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                           
/* 1519 */                           _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 1520 */                           int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 1521 */                           if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 1522 */                             String msg = null;
/* 1523 */                             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1524 */                               out = _jspx_page_context.pushBody();
/* 1525 */                               _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 1526 */                               _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                             }
/* 1528 */                             msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                             for (;;) {
/* 1530 */                               out.write(" \n<tr> \n  <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 1531 */                               if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                                 return;
/* 1533 */                               out.write("</td>\n\t  </tr>\n\t</table>\n\t<br></td>\n</tr>\n");
/* 1534 */                               int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 1535 */                               msg = (String)_jspx_page_context.findAttribute("msg");
/* 1536 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1539 */                             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1540 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1543 */                           if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 1544 */                             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                           }
/*      */                           
/* 1547 */                           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */                         }
/* 1549 */                         out.write(32);
/*      */                         
/* 1551 */                         org.apache.struts.taglib.logic.MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (org.apache.struts.taglib.logic.MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(org.apache.struts.taglib.logic.MessagesPresentTag.class);
/* 1552 */                         _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 1553 */                         _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fif_005f6);
/*      */                         
/* 1555 */                         _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 1556 */                         int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 1557 */                         if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                           for (;;) {
/* 1559 */                             out.write(" \n<tr> \n  <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\"> ");
/*      */                             
/* 1561 */                             MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1562 */                             _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 1563 */                             _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                             
/* 1565 */                             _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                             
/* 1567 */                             _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 1568 */                             int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 1569 */                             if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 1570 */                               String msg = null;
/* 1571 */                               if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1572 */                                 out = _jspx_page_context.pushBody();
/* 1573 */                                 _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 1574 */                                 _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                               }
/* 1576 */                               msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                               for (;;) {
/* 1578 */                                 out.write("\n\t  ");
/* 1579 */                                 if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                                   return;
/* 1581 */                                 out.write("<br>\n\t  ");
/* 1582 */                                 int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 1583 */                                 msg = (String)_jspx_page_context.findAttribute("msg");
/* 1584 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1587 */                               if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1588 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1591 */                             if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 1592 */                               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                             }
/*      */                             
/* 1595 */                             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 1596 */                             out.write(" </td>\n\t  </tr>\n\t</table></td>\n</tr>\n");
/* 1597 */                             int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 1598 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1602 */                         if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 1603 */                           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                         }
/*      */                         
/* 1606 */                         this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 1607 */                         out.write("\n</table>\n<script>\n\tobject=location.href;\n\t\n\tif(document.AMActionForm!=null)\n\t{\t  \n\t  if(object.match(\"EMailActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if(object.match(\"SMSActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if(object.match(\"ExecProgramActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=11\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=12\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\t  \n\t  else if(object.match(\"showLogTicket\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=6;\n\t  }\n\t  else if(object.match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=5;\n\t  }\t\n\t  else if(object.match(\"jre\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=7;\n");
/* 1608 */                         out.write("\t  }\n\t  else if(object.match(\"showVMAction\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=8;\n\t  }\n\t  else if(object.match(\"amazon\")!=null)\n\t  {\n\t\t    document.AMActionForm.actions.selectedIndex=9;\n\t }\t  \n\t}\n\telse if(document.MBeanOperationActionForm!=null)\n\t{\n\t  document.MBeanOperationActionForm.actions.selectedIndex=5;\t  \n\t}\n</script>\n</td>\n</tr>\n</table>\n");
/* 1609 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1610 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1614 */                     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1615 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                     }
/*      */                     
/* 1618 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1619 */                     out.write(10);
/* 1620 */                     out.write(10);
/* 1621 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1622 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1626 */                 if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1627 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                 }
/*      */                 
/* 1630 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1631 */                 out.write(10);
/* 1632 */                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1633 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1637 */             if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1638 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */             }
/*      */             
/* 1641 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1642 */             out.write(10);
/* 1643 */             if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1645 */             out.write(10);
/* 1646 */             out.write(10);
/* 1647 */             if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1649 */             out.write(10);
/* 1650 */             if (_jspx_meth_am_005fhiddenparam_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1652 */             out.write(10);
/* 1653 */             if (_jspx_meth_am_005fhiddenparam_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1655 */             out.write(10);
/* 1656 */             if (_jspx_meth_am_005fhiddenparam_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1658 */             out.write(10);
/* 1659 */             if (_jspx_meth_am_005fhiddenparam_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1661 */             out.write(10);
/* 1662 */             if (_jspx_meth_am_005fhiddenparam_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1664 */             out.write(10);
/* 1665 */             if (_jspx_meth_am_005fhiddenparam_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1667 */             out.write(10);
/* 1668 */             if (_jspx_meth_am_005fhiddenparam_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1670 */             out.write(10);
/* 1671 */             if (_jspx_meth_am_005fhiddenparam_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1673 */             out.write(10);
/*      */             
/* 1675 */             String showSdeskConfiguration3 = (String)request.getAttribute("showSdeskConfiguration2");
/* 1676 */             if (showSdeskConfiguration3 != null)
/*      */             {
/*      */ 
/*      */ 
/* 1680 */               out.write("\n\t<input type=\"hidden\" name=\"checkMailServer\" value=\"showSdeskConfiguration4\" >\n\t");
/*      */             }
/*      */             
/* 1683 */             String showSdeskLogTicket3 = (String)request.getAttribute("showSdeskLogTicket2");
/* 1684 */             if (showSdeskLogTicket3 != null)
/*      */             {
/*      */ 
/* 1687 */               out.write("\n\t\t<input type=\"hidden\" name=\"checkMailForTicket\" value=\"showSdeskLogTicket4\" >\n\t");
/*      */             }
/*      */             
/*      */ 
/*      */ 
/* 1692 */             out.write("\n        ");
/*      */             
/* 1694 */             IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1695 */             _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 1696 */             _jspx_th_c_005fif_005f18.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 1698 */             _jspx_th_c_005fif_005f18.setTest("${!empty showerror}");
/* 1699 */             int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 1700 */             if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */               for (;;) {
/* 1702 */                 out.write("\n        ");
/*      */                 
/* 1704 */                 MessagesTag _jspx_th_html_005fmessages_005f2 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1705 */                 _jspx_th_html_005fmessages_005f2.setPageContext(_jspx_page_context);
/* 1706 */                 _jspx_th_html_005fmessages_005f2.setParent(_jspx_th_c_005fif_005f18);
/*      */                 
/* 1708 */                 _jspx_th_html_005fmessages_005f2.setId("msg");
/*      */                 
/* 1710 */                 _jspx_th_html_005fmessages_005f2.setMessage("false");
/* 1711 */                 int _jspx_eval_html_005fmessages_005f2 = _jspx_th_html_005fmessages_005f2.doStartTag();
/* 1712 */                 if (_jspx_eval_html_005fmessages_005f2 != 0) {
/* 1713 */                   String msg = null;
/* 1714 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1715 */                     out = _jspx_page_context.pushBody();
/* 1716 */                     _jspx_th_html_005fmessages_005f2.setBodyContent((BodyContent)out);
/* 1717 */                     _jspx_th_html_005fmessages_005f2.doInitBody();
/*      */                   }
/* 1719 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/* 1721 */                     out.write("\n        ");
/* 1722 */                     if (_jspx_meth_c_005fif_005f19(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                       return;
/* 1724 */                     out.write(10);
/* 1725 */                     out.write(10);
/* 1726 */                     out.write(9);
/* 1727 */                     if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                       return;
/* 1729 */                     out.write("<br>\n\n\t");
/* 1730 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f2.doAfterBody();
/* 1731 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/* 1732 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1735 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1736 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1739 */                 if (_jspx_th_html_005fmessages_005f2.doEndTag() == 5) {
/* 1740 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2); return;
/*      */                 }
/*      */                 
/* 1743 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2);
/* 1744 */                 out.write(10);
/* 1745 */                 out.write(9);
/* 1746 */                 if (_jspx_meth_c_005fif_005f20(_jspx_th_c_005fif_005f18, _jspx_page_context))
/*      */                   return;
/* 1748 */                 out.write("\n\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t              <tr>\n\t\t      \t\t<td  height=\"10\" ><img src=\"../images/spacer.gif\" ></td>\n\t\t</tr>\n</table>\n        ");
/* 1749 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 1750 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1754 */             if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 1755 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */             }
/*      */             
/* 1758 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 1759 */             out.write("\n\n\n\n\n\n\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder itadmin-hide\">\n\n<tr>\n\t  <td width=\"100%\" colspan=\"2\">\n\t   <div id=\"SMSHeading\" style=\"display:none\">\n\t    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t <tr>\n\t\t   <td colspan=\"2\" class=\"tableheading\">");
/* 1760 */             out.print(FormatUtil.getString("am.webclient.smsserver.settings"));
/* 1761 */             out.write("</td>\n\t\t </tr>\n \t    </table>\n\t   </div>\n\t  </td>\n\t </tr>\n\t</table>\n\t  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbdarkborder\">\n\n\n<tr>\n  <td>\n  \t<table width=\"99%\" cellspacing=\"0\" cellpadding=\"6\" border=\"0\" class=\"lrborder itadmin-no-decor\">\n  \t\t<tbody><tr>\n  \t\t\t<td class=\"plainheading1 itadmin-no-decor\" colspan=\"2\">");
/* 1762 */             out.print(FormatUtil.getString("am.webclient.modem.settings"));
/* 1763 */             out.write("</td>\n  \t\t</tr>\n  \t</tbody></table>\n  </td>\n</tr>\n\n\n<tr>\n<td>\n<div id=\"SMS\" Style=\"display:none\">\n\n<input type=\"hidden\" name=\"method\" value=\"SMSServerConfiguration\" >\n\n  <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrborder itadmin-no-decor\">\n    <tr>\n      <td colspan=\"2\" width=\"16%\"  class=\"bodytextbold\">");
/* 1764 */             out.print(FormatUtil.getString("am.webclient.adminmodem.text"));
/* 1765 */             out.write("</td>\n\n      </tr>\n\n");
/* 1766 */             if (_jspx_meth_c_005fif_005f21(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1768 */             out.write("\n\n      <tr>\n      <td class=\"bodytext\">\n      <ul class=\"bullet_list\">\n      <li>");
/* 1769 */             out.print(FormatUtil.getString("am.webclient.smsserver.info"));
/* 1770 */             out.write(" </li>\n      <li>");
/* 1771 */             out.print(FormatUtil.getString("am.webclient.sms.configuration.text"));
/* 1772 */             out.write("</li>\n      <li> ");
/* 1773 */             out.print(FormatUtil.getString("am.webclient.smsserver.info2"));
/* 1774 */             out.write("</li>\n      </ul>\n      </td>\n      </tr>\n      <tr>\n       <td class=\"bodytext\" width=\"70%\"><a href=\"#\" style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 1775 */             out.print(FormatUtil.getString("am.webclient.port.help"));
/* 1776 */             out.write("',false,true,'#000000',350,'lightyellow')\" onMouseOut=\"hideddrivetip()\"> ");
/* 1777 */             out.print(FormatUtil.getString("am.webclient.smsserver.info5"));
/* 1778 */             out.write(" </a> :  ");
/* 1779 */             if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1781 */             out.write(" </td>\n      </tr>\n\n\n      </table>\n");
/*      */             
/* 1783 */             ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1784 */             _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 1785 */             _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_html_005fform_005f0);
/* 1786 */             int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 1787 */             if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */               for (;;) {
/* 1789 */                 out.write(10);
/* 1790 */                 if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context))
/*      */                   return;
/* 1792 */                 out.write("\n\n      ");
/*      */                 
/* 1794 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1795 */                 _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 1796 */                 _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 1797 */                 int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 1798 */                 if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                   for (;;) {
/* 1800 */                     out.write("\n\n\n      <table width=\"99%\" border=\"0\" cellspacing=\"5\" cellpadding=\"5\" class=\"lrbborder itadmin-no-decor\">\n\t<Tr>\n<td class =\"columnheading\">\n");
/* 1801 */                     out.print(FormatUtil.getString("am.webclient.modemstatus.text"));
/* 1802 */                     out.write("\n</td>\n\n<tr>\n      <tr>\n      <td>\n      <table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"198\" width=\"100%\">\n      <tbody>\n      <tr>\n      <td align=\"center\" height=\"171\" valign=\"middle\">\n      <table border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"96%\" >\n      <tbody>\n      <tr>\n      <td align=\"center\" valign=\"middle\"><div id=\"setns\" style=\"display: block;\">\n      <table  cellpadding=\"2\" cellspacing=\"0\" height=\"86\" width=\"62%\" class=\"grayfullborder\">\n      <tbody>\n      <tr >\n      <td class=\"whitegrayborder\" width=\"50%\"><strong>");
/* 1803 */                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                       return;
/* 1805 */                     out.write("</strong></td>\n      <td class=\"whitegrayborder\" width=\"3%\">:</td>\n\n     <td class=\"whitegrayborder\" height=\"20\" width=\"47%\" onmouseover=\"ddrivetip(this,event,'");
/* 1806 */                     out.print(((java.util.Properties)request.getAttribute("modemProps")).getProperty("ModemManufacturer"));
/* 1807 */                     out.write("',null,true,'#000000',100)\" onmouseout=\"hideddrivetip()\"> ");
/* 1808 */                     out.print(FormatUtil.getTrimmedText(((java.util.Properties)request.getAttribute("modemProps")).getProperty("ModemManufacturer"), 25));
/* 1809 */                     out.write("</td>\n      </tr>\n      <tr >\n      <td class=\"yellowgrayborder\"><strong>");
/* 1810 */                     if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                       return;
/* 1812 */                     out.write("</strong></td>\n      <td class=\"yellowgrayborder\">:</td>\n      <td class=\"yellowgrayborder\" height=\"20\">");
/* 1813 */                     if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                       return;
/* 1815 */                     out.write("</td>\n      </tr>\n      <tr >\n      <td class=\"whitegrayborder\"><strong>");
/* 1816 */                     if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                       return;
/* 1818 */                     out.write("</strong></td>\n      <td class=\"whitegrayborder\">:</td>\n      <td class=\"whitegrayborder\" valign=\"middle\">");
/* 1819 */                     out.write("\n\n      <div style=\"vertical-align:absmiddle; height: 10px\"><div style=\"float:left; width: 60%; background-color:white; border: 1px solid #aaa;\"><div style=\"width: ");
/* 1820 */                     if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                       return;
/* 1822 */                     out.write("; background-color:#00ff00;\"></div></div><div style=\"float:left; padding-left:5px; line-height: 1.3;\">");
/* 1823 */                     if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                       return;
/* 1825 */                     out.write("</div><div  style=\"clear:both\"></div></div>\n\n\n      </td>\n      </tr>\n      <tr >\n      <td class=\"yellowgrayborder\"><strong>");
/* 1826 */                     if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                       return;
/* 1828 */                     out.write("</strong></td>\n      <td class=\"yellowgrayborder\">:</td>\n     <td class=\"yellowgrayborder\" height=\"20\"><img align=\"absmiddle\" src='");
/* 1829 */                     if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                       return;
/* 1831 */                     out.write(39);
/* 1832 */                     out.write(62);
/* 1833 */                     out.write(32);
/* 1834 */                     if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                       return;
/* 1836 */                     out.write("</tr>\n      </tr>\n      <tr >\n      <td class=\"whitegrayborder\"><strong>");
/* 1837 */                     if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                       return;
/* 1839 */                     out.write("</strong></td>\n      <td class=\"whitegrayborder\">:</td>\n      <td class=\"whitegrayborder\" height=\"20\">");
/* 1840 */                     if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fotherwise_005f5, _jspx_page_context))
/*      */                       return;
/* 1842 */                     out.write("</td>\n      </tr>\n      </tbody>\n      </table>\n      </td>\n      </tr>\n      </tbody>\n      </table>\n      </td>\n      </tr>\n      </tbody>\n      </table>\n      </td>\n      </tr>\n      </table>\n      </td>\n\n\n");
/* 1843 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 1844 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1848 */                 if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 1849 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                 }
/*      */                 
/* 1852 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 1853 */                 out.write(10);
/* 1854 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 1855 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1859 */             if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 1860 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */             }
/*      */             
/* 1863 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 1864 */             out.write("\n</div>\n</td>\n</tr>\n<tr>\n  <td>\n    <table width=\"99%\" cellspacing=\"0\" cellpadding=\"6\" border=\"0\" class=\"lrborder itadmin-no-decor\">\n      <tbody>\n        <tr>\n  \t  <td class=\"plainheading1 itadmin-no-decor\" colspan=\"2\">");
/* 1865 */             out.print(FormatUtil.getString("am.webclient.mail.settings"));
/* 1866 */             out.write("</td>\n  \t</tr>\n      </tbody>\n    </table>\n  </td>\n</tr>\n<tr>\n  <td>\n    <table width=\"99%\" cellspacing=\"0\" cellpadding=\"6\" border=\"0\" class=\"lrborder itadmin-no-decor\">\n      <tbody>\n        <tr height=\"60\" class=\"bodytext\">\n\t\t");
/*      */             
/* 1868 */             ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1869 */             _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 1870 */             _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_html_005fform_005f0);
/* 1871 */             int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 1872 */             if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */               for (;;) {
/* 1874 */                 out.write("\n\t\t  ");
/*      */                 
/* 1876 */                 WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1877 */                 _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 1878 */                 _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                 
/* 1880 */                 _jspx_th_c_005fwhen_005f6.setTest("${empty mailServerList}");
/* 1881 */                 int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 1882 */                 if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                   for (;;) {
/* 1884 */                     out.write("\n\t\t    <td  style=\"width: 135px; padding-left: 8px;\">");
/* 1885 */                     out.print(FormatUtil.getString("am.configure.mail.server"));
/* 1886 */                     out.write("</td>\n\t\t  ");
/* 1887 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 1888 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1892 */                 if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 1893 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                 }
/*      */                 
/* 1896 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1897 */                 out.write("\n\t\t  ");
/*      */                 
/* 1899 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1900 */                 _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 1901 */                 _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 1902 */                 int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 1903 */                 if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                   for (;;) {
/* 1905 */                     out.write("\n\t\t    <td  style=\"width:135px; padding-left:20px;\">");
/* 1906 */                     out.print(FormatUtil.getString("am.choose.mail.server"));
/* 1907 */                     out.write("</td>\n\t\t     <td>\n\t\t       ");
/* 1908 */                     if (_jspx_meth_html_005fselect_005f0(_jspx_th_c_005fotherwise_005f6, _jspx_page_context))
/*      */                       return;
/* 1910 */                     out.write("\n\t\t    </td> \n\t\t  ");
/* 1911 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 1912 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1916 */                 if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 1917 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                 }
/*      */                 
/* 1920 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 1921 */                 out.write(10);
/* 1922 */                 out.write(9);
/* 1923 */                 out.write(9);
/* 1924 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 1925 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1929 */             if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 1930 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */             }
/*      */             
/* 1933 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 1934 */             out.write("\n  \t</tr>\n      </tbody>\n    </table>\n  </td>\n</tr>\n</table>\n\n\n\n\n  ");
/*      */             
/* 1936 */             String save = FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverconfig.savebutton.text");
/* 1937 */             String reset = FormatUtil.getString("am.webclient.global.Reset.text");
/* 1938 */             String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/* 1939 */             String close = FormatUtil.getString("am.webclient.common.close.text");
/*      */             
/* 1941 */             out.write("\n\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder itadmin-no-decor\">\n    <tr>\n     \n      <td width=\"99%\" height=\"31\"  align=\"left\" class=\"tablebottom itadmin-no-decor\" ><input name=\"Submit\" value=\"");
/* 1942 */             out.print(save);
/* 1943 */             out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"fnFormSubmit();\">\n\n\n        ");
/*      */             
/* 1945 */             ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1946 */             _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 1947 */             _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_html_005fform_005f0);
/* 1948 */             int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 1949 */             if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */               for (;;) {
/* 1951 */                 out.write("\n        ");
/*      */                 
/* 1953 */                 WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1954 */                 _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 1955 */                 _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                 
/* 1957 */                 _jspx_th_c_005fwhen_005f7.setTest("${empty param.popup}");
/* 1958 */                 int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 1959 */                 if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                   for (;;) {
/* 1961 */                     out.write("\n\t        <input name=\"button2\" value=\"");
/* 1962 */                     out.print(cancel);
/* 1963 */                     out.write("\" type=\"button\" class=\"buttons btn_link\" onClick=\"javascript:history.back();\"></td>\n        ");
/* 1964 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 1965 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1969 */                 if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 1970 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                 }
/*      */                 
/* 1973 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1974 */                 out.write("\n        ");
/*      */                 
/* 1976 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1977 */                 _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 1978 */                 _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 1979 */                 int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 1980 */                 if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                   for (;;) {
/* 1982 */                     out.write("\n        <input name=\"button2\" value=\"");
/* 1983 */                     out.print(close);
/* 1984 */                     out.write("\" type=\"button\" class=\"buttons btn_link\" onClick=\"javascript:window.parent.close();\"></td>\n        ");
/* 1985 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 1986 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1990 */                 if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 1991 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                 }
/*      */                 
/* 1994 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 1995 */                 out.write("\n        ");
/* 1996 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 1997 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2001 */             if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 2002 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */             }
/*      */             
/* 2005 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 2006 */             out.write("\n    </tr>\n</table>\n");
/* 2007 */             int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2008 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2012 */         if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2013 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */         }
/*      */         else
/* 2016 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/* 2018 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2019 */         out = _jspx_out;
/* 2020 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2021 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2022 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2025 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2031 */     PageContext pageContext = _jspx_page_context;
/* 2032 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2034 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2035 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2036 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 2038 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2039 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2040 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2042 */         out.write("\n\t\talertUser();\n\t");
/* 2043 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2044 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2048 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2049 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2050 */       return true;
/*      */     }
/* 2052 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2053 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2058 */     PageContext pageContext = _jspx_page_context;
/* 2059 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2061 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2062 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2063 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2065 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2067 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2068 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2069 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2070 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2071 */       return true;
/*      */     }
/* 2073 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2079 */     PageContext pageContext = _jspx_page_context;
/* 2080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2082 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2083 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2084 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 2086 */     _jspx_th_c_005fif_005f1.setTest("${globalconfig['mailserverconfigured'] != 'true'}");
/* 2087 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2088 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2090 */         out.write("\n\t\t\tmyOnLoad();\n\t\t");
/* 2091 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2092 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2096 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2097 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2098 */       return true;
/*      */     }
/* 2100 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2101 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2106 */     PageContext pageContext = _jspx_page_context;
/* 2107 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2109 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2110 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2111 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 2113 */     _jspx_th_c_005fif_005f2.setTest("${globalconfig['mailserverconfigured'] != 'true' && param.checkForMailSetting eq 'true'}");
/* 2114 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2115 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 2117 */         out.write("\n\t\t\t\tmyOnLoad();\n\t\t\t");
/* 2118 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2119 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2123 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2124 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2125 */       return true;
/*      */     }
/* 2127 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2128 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2133 */     PageContext pageContext = _jspx_page_context;
/* 2134 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2136 */     org.apache.taglibs.standard.tag.common.core.CatchTag _jspx_th_c_005fcatch_005f0 = (org.apache.taglibs.standard.tag.common.core.CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(org.apache.taglibs.standard.tag.common.core.CatchTag.class);
/* 2137 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 2138 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2140 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 2141 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 2143 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 2144 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 2146 */           out.write(" \n      ");
/* 2147 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 2148 */             return true;
/* 2149 */           out.write(32);
/* 2150 */           out.write(10);
/* 2151 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 2152 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2156 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 2157 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2160 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 2161 */         out = _jspx_page_context.popBody(); }
/* 2162 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 2164 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 2165 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 2167 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 2172 */     PageContext pageContext = _jspx_page_context;
/* 2173 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2175 */     org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag.class);
/* 2176 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 2177 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 2179 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 2181 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 2182 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 2183 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 2184 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2185 */       return true;
/*      */     }
/* 2187 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2188 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2193 */     PageContext pageContext = _jspx_page_context;
/* 2194 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2196 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 2197 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 2198 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2200 */     _jspx_th_c_005fset_005f5.setVar("wiz");
/*      */     
/* 2202 */     _jspx_th_c_005fset_005f5.setValue("${param.wiz}");
/*      */     
/* 2204 */     _jspx_th_c_005fset_005f5.setScope("request");
/* 2205 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 2206 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 2207 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 2208 */       return true;
/*      */     }
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 2211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2216 */     PageContext pageContext = _jspx_page_context;
/* 2217 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2219 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2220 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2221 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2223 */     _jspx_th_c_005fout_005f1.setValue("${wizimage}");
/*      */     
/* 2225 */     _jspx_th_c_005fout_005f1.setEscapeXml("false");
/* 2226 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2227 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2228 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2229 */       return true;
/*      */     }
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2237 */     PageContext pageContext = _jspx_page_context;
/* 2238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2240 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2241 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2242 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2244 */     _jspx_th_c_005fif_005f10.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 2245 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2246 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 2248 */         out.write("\n    <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 2249 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f10, _jspx_page_context))
/* 2250 */           return true;
/* 2251 */         out.write("&wiz=true\">\n    ");
/* 2252 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2253 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2257 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2258 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2259 */       return true;
/*      */     }
/* 2261 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2262 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2267 */     PageContext pageContext = _jspx_page_context;
/* 2268 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2270 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2271 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2272 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 2274 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 2275 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2276 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2277 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2278 */       return true;
/*      */     }
/* 2280 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2281 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2286 */     PageContext pageContext = _jspx_page_context;
/* 2287 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2289 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2290 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2291 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2293 */     _jspx_th_c_005fout_005f3.setValue("${wizimage}");
/*      */     
/* 2295 */     _jspx_th_c_005fout_005f3.setEscapeXml("false");
/* 2296 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2297 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2298 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2299 */       return true;
/*      */     }
/* 2301 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2307 */     PageContext pageContext = _jspx_page_context;
/* 2308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2310 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2311 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2312 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2314 */     _jspx_th_c_005fif_005f11.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 2315 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2316 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 2318 */         out.write("\n    \t</a>\n    \t");
/* 2319 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2320 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2324 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2325 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2326 */       return true;
/*      */     }
/* 2328 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2329 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2334 */     PageContext pageContext = _jspx_page_context;
/* 2335 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2337 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2338 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2339 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2341 */     _jspx_th_c_005fif_005f12.setTest("${wizimage=='/images/new_high.gif'}");
/* 2342 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2343 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 2345 */         out.write("\n       <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 2346 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 2347 */           return true;
/* 2348 */         out.write("&wiz=true\">\n       ");
/* 2349 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 2350 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2354 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 2355 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2356 */       return true;
/*      */     }
/* 2358 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2359 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2364 */     PageContext pageContext = _jspx_page_context;
/* 2365 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2367 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2368 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2369 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 2371 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 2372 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2373 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2374 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2375 */       return true;
/*      */     }
/* 2377 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2378 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2383 */     PageContext pageContext = _jspx_page_context;
/* 2384 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2386 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2387 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2388 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2390 */     _jspx_th_c_005fout_005f5.setValue("${wizimage}");
/*      */     
/* 2392 */     _jspx_th_c_005fout_005f5.setEscapeXml("false");
/* 2393 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2394 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2395 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2396 */       return true;
/*      */     }
/* 2398 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2399 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2404 */     PageContext pageContext = _jspx_page_context;
/* 2405 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2407 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2408 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 2409 */     _jspx_th_c_005fif_005f13.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2411 */     _jspx_th_c_005fif_005f13.setTest("${wizimage=='/images/new_high.gif'}");
/* 2412 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 2413 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 2415 */         out.write("\n       \t</a>\n       \t");
/* 2416 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 2417 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2421 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 2422 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 2423 */       return true;
/*      */     }
/* 2425 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 2426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2431 */     PageContext pageContext = _jspx_page_context;
/* 2432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2434 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2435 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 2436 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2438 */     _jspx_th_c_005fif_005f16.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 2439 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 2440 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 2442 */         out.write("\t\n    <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 2443 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 2444 */           return true;
/* 2445 */         out.write("&wiz=true\">\n ");
/* 2446 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 2447 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2451 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 2452 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2453 */       return true;
/*      */     }
/* 2455 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2456 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2461 */     PageContext pageContext = _jspx_page_context;
/* 2462 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2464 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2465 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2466 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 2468 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 2469 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2470 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2471 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2472 */       return true;
/*      */     }
/* 2474 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2475 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2480 */     PageContext pageContext = _jspx_page_context;
/* 2481 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2483 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2484 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2485 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2487 */     _jspx_th_c_005fout_005f7.setValue("${wizimage}");
/*      */     
/* 2489 */     _jspx_th_c_005fout_005f7.setEscapeXml("false");
/* 2490 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2491 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2492 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2493 */       return true;
/*      */     }
/* 2495 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2496 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2501 */     PageContext pageContext = _jspx_page_context;
/* 2502 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2504 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2505 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 2506 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2508 */     _jspx_th_c_005fif_005f17.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 2509 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 2510 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 2512 */         out.write("\t    \n    </a>\n ");
/* 2513 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 2514 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2518 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 2519 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 2520 */       return true;
/*      */     }
/* 2522 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 2523 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2528 */     PageContext pageContext = _jspx_page_context;
/* 2529 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2531 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2532 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2533 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 2535 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 2537 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 2538 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2539 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2540 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2541 */       return true;
/*      */     }
/* 2543 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2544 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2549 */     PageContext pageContext = _jspx_page_context;
/* 2550 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2552 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2553 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 2554 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 2556 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 2558 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 2559 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 2560 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 2561 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2562 */       return true;
/*      */     }
/* 2564 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2570 */     PageContext pageContext = _jspx_page_context;
/* 2571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2573 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2574 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 2575 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2577 */     _jspx_th_am_005fhiddenparam_005f0.setName("mailserverredirecturl");
/* 2578 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 2579 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 2580 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 2581 */       return true;
/*      */     }
/* 2583 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 2584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2589 */     PageContext pageContext = _jspx_page_context;
/* 2590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2592 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2593 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/* 2594 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2596 */     _jspx_th_am_005fhiddenparam_005f1.setName("haid");
/* 2597 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/* 2598 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/* 2599 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 2600 */       return true;
/*      */     }
/* 2602 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 2603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2608 */     PageContext pageContext = _jspx_page_context;
/* 2609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2611 */     HiddenParam _jspx_th_am_005fhiddenparam_005f2 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2612 */     _jspx_th_am_005fhiddenparam_005f2.setPageContext(_jspx_page_context);
/* 2613 */     _jspx_th_am_005fhiddenparam_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2615 */     _jspx_th_am_005fhiddenparam_005f2.setName("global");
/* 2616 */     int _jspx_eval_am_005fhiddenparam_005f2 = _jspx_th_am_005fhiddenparam_005f2.doStartTag();
/* 2617 */     if (_jspx_th_am_005fhiddenparam_005f2.doEndTag() == 5) {
/* 2618 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 2619 */       return true;
/*      */     }
/* 2621 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 2622 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2627 */     PageContext pageContext = _jspx_page_context;
/* 2628 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2630 */     HiddenParam _jspx_th_am_005fhiddenparam_005f3 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2631 */     _jspx_th_am_005fhiddenparam_005f3.setPageContext(_jspx_page_context);
/* 2632 */     _jspx_th_am_005fhiddenparam_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2634 */     _jspx_th_am_005fhiddenparam_005f3.setName("resourceid");
/* 2635 */     int _jspx_eval_am_005fhiddenparam_005f3 = _jspx_th_am_005fhiddenparam_005f3.doStartTag();
/* 2636 */     if (_jspx_th_am_005fhiddenparam_005f3.doEndTag() == 5) {
/* 2637 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 2638 */       return true;
/*      */     }
/* 2640 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 2641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2646 */     PageContext pageContext = _jspx_page_context;
/* 2647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2649 */     HiddenParam _jspx_th_am_005fhiddenparam_005f4 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2650 */     _jspx_th_am_005fhiddenparam_005f4.setPageContext(_jspx_page_context);
/* 2651 */     _jspx_th_am_005fhiddenparam_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2653 */     _jspx_th_am_005fhiddenparam_005f4.setName("attributeid");
/* 2654 */     int _jspx_eval_am_005fhiddenparam_005f4 = _jspx_th_am_005fhiddenparam_005f4.doStartTag();
/* 2655 */     if (_jspx_th_am_005fhiddenparam_005f4.doEndTag() == 5) {
/* 2656 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 2657 */       return true;
/*      */     }
/* 2659 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 2660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2665 */     PageContext pageContext = _jspx_page_context;
/* 2666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2668 */     HiddenParam _jspx_th_am_005fhiddenparam_005f5 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2669 */     _jspx_th_am_005fhiddenparam_005f5.setPageContext(_jspx_page_context);
/* 2670 */     _jspx_th_am_005fhiddenparam_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2672 */     _jspx_th_am_005fhiddenparam_005f5.setName("severity");
/* 2673 */     int _jspx_eval_am_005fhiddenparam_005f5 = _jspx_th_am_005fhiddenparam_005f5.doStartTag();
/* 2674 */     if (_jspx_th_am_005fhiddenparam_005f5.doEndTag() == 5) {
/* 2675 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f5);
/* 2676 */       return true;
/*      */     }
/* 2678 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f5);
/* 2679 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2684 */     PageContext pageContext = _jspx_page_context;
/* 2685 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2687 */     HiddenParam _jspx_th_am_005fhiddenparam_005f6 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2688 */     _jspx_th_am_005fhiddenparam_005f6.setPageContext(_jspx_page_context);
/* 2689 */     _jspx_th_am_005fhiddenparam_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2691 */     _jspx_th_am_005fhiddenparam_005f6.setName("redirectTo");
/* 2692 */     int _jspx_eval_am_005fhiddenparam_005f6 = _jspx_th_am_005fhiddenparam_005f6.doStartTag();
/* 2693 */     if (_jspx_th_am_005fhiddenparam_005f6.doEndTag() == 5) {
/* 2694 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f6);
/* 2695 */       return true;
/*      */     }
/* 2697 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f6);
/* 2698 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2703 */     PageContext pageContext = _jspx_page_context;
/* 2704 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2706 */     HiddenParam _jspx_th_am_005fhiddenparam_005f7 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2707 */     _jspx_th_am_005fhiddenparam_005f7.setPageContext(_jspx_page_context);
/* 2708 */     _jspx_th_am_005fhiddenparam_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2710 */     _jspx_th_am_005fhiddenparam_005f7.setName("returnpath");
/* 2711 */     int _jspx_eval_am_005fhiddenparam_005f7 = _jspx_th_am_005fhiddenparam_005f7.doStartTag();
/* 2712 */     if (_jspx_th_am_005fhiddenparam_005f7.doEndTag() == 5) {
/* 2713 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f7);
/* 2714 */       return true;
/*      */     }
/* 2716 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f7);
/* 2717 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2722 */     PageContext pageContext = _jspx_page_context;
/* 2723 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2725 */     HiddenParam _jspx_th_am_005fhiddenparam_005f8 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2726 */     _jspx_th_am_005fhiddenparam_005f8.setPageContext(_jspx_page_context);
/* 2727 */     _jspx_th_am_005fhiddenparam_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2729 */     _jspx_th_am_005fhiddenparam_005f8.setName("manager");
/* 2730 */     int _jspx_eval_am_005fhiddenparam_005f8 = _jspx_th_am_005fhiddenparam_005f8.doStartTag();
/* 2731 */     if (_jspx_th_am_005fhiddenparam_005f8.doEndTag() == 5) {
/* 2732 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f8);
/* 2733 */       return true;
/*      */     }
/* 2735 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f8);
/* 2736 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2741 */     PageContext pageContext = _jspx_page_context;
/* 2742 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2744 */     HiddenParam _jspx_th_am_005fhiddenparam_005f9 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2745 */     _jspx_th_am_005fhiddenparam_005f9.setPageContext(_jspx_page_context);
/* 2746 */     _jspx_th_am_005fhiddenparam_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2748 */     _jspx_th_am_005fhiddenparam_005f9.setName("admin");
/* 2749 */     int _jspx_eval_am_005fhiddenparam_005f9 = _jspx_th_am_005fhiddenparam_005f9.doStartTag();
/* 2750 */     if (_jspx_th_am_005fhiddenparam_005f9.doEndTag() == 5) {
/* 2751 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f9);
/* 2752 */       return true;
/*      */     }
/* 2754 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f9);
/* 2755 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f19(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2760 */     PageContext pageContext = _jspx_page_context;
/* 2761 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2763 */     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2764 */     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 2765 */     _jspx_th_c_005fif_005f19.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2767 */     _jspx_th_c_005fif_005f19.setTest("${empty firstrow}");
/* 2768 */     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 2769 */     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */       for (;;) {
/* 2771 */         out.write("\n\n\t<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t<tr>\n\t<td width=\"5%\" align=\"center\">\n\t<img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">\n\t</td>\n\t<td width=\"95%\" class=\"message\">\n\n\t");
/* 2772 */         if (_jspx_meth_c_005fset_005f14(_jspx_th_c_005fif_005f19, _jspx_page_context))
/* 2773 */           return true;
/* 2774 */         out.write(10);
/* 2775 */         out.write(9);
/* 2776 */         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 2777 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2781 */     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 2782 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 2783 */       return true;
/*      */     }
/* 2785 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 2786 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f14(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2791 */     PageContext pageContext = _jspx_page_context;
/* 2792 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2794 */     SetTag _jspx_th_c_005fset_005f14 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 2795 */     _jspx_th_c_005fset_005f14.setPageContext(_jspx_page_context);
/* 2796 */     _jspx_th_c_005fset_005f14.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 2798 */     _jspx_th_c_005fset_005f14.setVar("firstrow");
/*      */     
/* 2800 */     _jspx_th_c_005fset_005f14.setValue("true");
/* 2801 */     int _jspx_eval_c_005fset_005f14 = _jspx_th_c_005fset_005f14.doStartTag();
/* 2802 */     if (_jspx_th_c_005fset_005f14.doEndTag() == 5) {
/* 2803 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f14);
/* 2804 */       return true;
/*      */     }
/* 2806 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f14);
/* 2807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2812 */     PageContext pageContext = _jspx_page_context;
/* 2813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2815 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2816 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 2817 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2819 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/*      */     
/* 2821 */     _jspx_th_bean_005fwrite_005f2.setFilter(false);
/* 2822 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 2823 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 2824 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2825 */       return true;
/*      */     }
/* 2827 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2828 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(JspTag _jspx_th_c_005fif_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2833 */     PageContext pageContext = _jspx_page_context;
/* 2834 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2836 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2837 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 2838 */     _jspx_th_c_005fif_005f20.setParent((Tag)_jspx_th_c_005fif_005f18);
/*      */     
/* 2840 */     _jspx_th_c_005fif_005f20.setTest("${!empty firstrow}");
/* 2841 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 2842 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 2844 */         out.write("\n\t</td>\n\t</tr>\n\t</table>\n\t");
/* 2845 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 2846 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2850 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 2851 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 2852 */       return true;
/*      */     }
/* 2854 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 2855 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f21(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2860 */     PageContext pageContext = _jspx_page_context;
/* 2861 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2863 */     IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2864 */     _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 2865 */     _jspx_th_c_005fif_005f21.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2867 */     _jspx_th_c_005fif_005f21.setTest("${empty modemProps}");
/* 2868 */     int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 2869 */     if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */       for (;;) {
/* 2871 */         out.write("\n\n\n\t<!--tr><td align=\"center\" class=\"smsalert\"><!%=FormatUtil.getString(\"am.webclient.adminmodem.alert\")%></td></tr-->\n");
/* 2872 */         int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 2873 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2877 */     if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 2878 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 2879 */       return true;
/*      */     }
/* 2881 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 2882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2887 */     PageContext pageContext = _jspx_page_context;
/* 2888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2890 */     org.apache.struts.taglib.html.TextTag _jspx_th_html_005ftext_005f0 = (org.apache.struts.taglib.html.TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(org.apache.struts.taglib.html.TextTag.class);
/* 2891 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 2892 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2894 */     _jspx_th_html_005ftext_005f0.setProperty("SMSPort");
/*      */     
/* 2896 */     _jspx_th_html_005ftext_005f0.setSize("10");
/*      */     
/* 2898 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext small");
/* 2899 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 2900 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 2901 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2902 */       return true;
/*      */     }
/* 2904 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2905 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2910 */     PageContext pageContext = _jspx_page_context;
/* 2911 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2913 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2914 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2915 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 2917 */     _jspx_th_c_005fwhen_005f5.setTest("${empty modemProps}");
/* 2918 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2919 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 2921 */         out.write("\n\n      ");
/* 2922 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2923 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2927 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2928 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2929 */       return true;
/*      */     }
/* 2931 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2932 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2937 */     PageContext pageContext = _jspx_page_context;
/* 2938 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2940 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2941 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2942 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2944 */     _jspx_th_fmt_005fmessage_005f0.setKey("webclient.admin.smsserver.modem.mft");
/* 2945 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2946 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2947 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2948 */       return true;
/*      */     }
/* 2950 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2951 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2956 */     PageContext pageContext = _jspx_page_context;
/* 2957 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2959 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2960 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2961 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2963 */     _jspx_th_fmt_005fmessage_005f1.setKey("webclient.admin.smsserver.modem.model");
/* 2964 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 2965 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 2966 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2967 */       return true;
/*      */     }
/* 2969 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2970 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2975 */     PageContext pageContext = _jspx_page_context;
/* 2976 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2978 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2979 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 2980 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 2982 */     _jspx_th_c_005fout_005f8.setValue("${modemProps.ModemModel}");
/* 2983 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 2984 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 2985 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2986 */       return true;
/*      */     }
/* 2988 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2989 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2994 */     PageContext pageContext = _jspx_page_context;
/* 2995 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2997 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 2998 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 2999 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 3001 */     _jspx_th_fmt_005fmessage_005f2.setKey("webclient.admin.smsserver.modem.battery");
/* 3002 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3003 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3004 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3005 */       return true;
/*      */     }
/* 3007 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3008 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3013 */     PageContext pageContext = _jspx_page_context;
/* 3014 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3016 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3017 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3018 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 3020 */     _jspx_th_c_005fout_005f9.setValue("${modemProps.BatteryStatus}");
/* 3021 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3022 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3023 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3024 */       return true;
/*      */     }
/* 3026 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3027 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3032 */     PageContext pageContext = _jspx_page_context;
/* 3033 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3035 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3036 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3037 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 3039 */     _jspx_th_c_005fout_005f10.setValue("${modemProps.BatteryStatus}");
/* 3040 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3041 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3042 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3043 */       return true;
/*      */     }
/* 3045 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3046 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3051 */     PageContext pageContext = _jspx_page_context;
/* 3052 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3054 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3055 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3056 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 3058 */     _jspx_th_fmt_005fmessage_005f3.setKey("webclient.admin.smsserver.modem.signal");
/* 3059 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3060 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3061 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3062 */       return true;
/*      */     }
/* 3064 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3065 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3070 */     PageContext pageContext = _jspx_page_context;
/* 3071 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3073 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3074 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 3075 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 3077 */     _jspx_th_c_005fout_005f11.setValue("/images/${modemProps.SignalImage}");
/* 3078 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 3079 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 3080 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3081 */       return true;
/*      */     }
/* 3083 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3084 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3089 */     PageContext pageContext = _jspx_page_context;
/* 3090 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3092 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3093 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 3094 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 3096 */     _jspx_th_c_005fout_005f12.setValue("${modemProps.SignalStatus}");
/* 3097 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 3098 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 3099 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3100 */       return true;
/*      */     }
/* 3102 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3103 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3108 */     PageContext pageContext = _jspx_page_context;
/* 3109 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3111 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 3112 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3113 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 3115 */     _jspx_th_fmt_005fmessage_005f4.setKey("webclient.admin.smsserver.modem.status");
/* 3116 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3117 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3118 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3119 */       return true;
/*      */     }
/* 3121 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3127 */     PageContext pageContext = _jspx_page_context;
/* 3128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3130 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3131 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 3132 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 3134 */     _jspx_th_c_005fout_005f13.setValue("${modemProps.Status}");
/* 3135 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 3136 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 3137 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3138 */       return true;
/*      */     }
/* 3140 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3141 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_c_005fotherwise_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3146 */     PageContext pageContext = _jspx_page_context;
/* 3147 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3149 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3150 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3151 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_c_005fotherwise_005f6);
/*      */     
/* 3153 */     _jspx_th_html_005fselect_005f0.setProperty("smsMailServer");
/*      */     
/* 3155 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext default");
/* 3156 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3157 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3158 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3159 */         out = _jspx_page_context.pushBody();
/* 3160 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3161 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3164 */         out.write("\n\t\t         ");
/* 3165 */         if (_jspx_meth_html_005foptions_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 3166 */           return true;
/* 3167 */         out.write("\n\t\t       ");
/* 3168 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3169 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3172 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3173 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3176 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3177 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 3178 */       return true;
/*      */     }
/* 3180 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 3181 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptions_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3186 */     PageContext pageContext = _jspx_page_context;
/* 3187 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3189 */     org.apache.struts.taglib.html.OptionsTag _jspx_th_html_005foptions_005f0 = (org.apache.struts.taglib.html.OptionsTag)this._005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody.get(org.apache.struts.taglib.html.OptionsTag.class);
/* 3190 */     _jspx_th_html_005foptions_005f0.setPageContext(_jspx_page_context);
/* 3191 */     _jspx_th_html_005foptions_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 3193 */     _jspx_th_html_005foptions_005f0.setCollection("mailServerList");
/*      */     
/* 3195 */     _jspx_th_html_005foptions_005f0.setProperty("value");
/*      */     
/* 3197 */     _jspx_th_html_005foptions_005f0.setLabelProperty("label");
/* 3198 */     int _jspx_eval_html_005foptions_005f0 = _jspx_th_html_005foptions_005f0.doStartTag();
/* 3199 */     if (_jspx_th_html_005foptions_005f0.doEndTag() == 5) {
/* 3200 */       this._005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody.reuse(_jspx_th_html_005foptions_005f0);
/* 3201 */       return true;
/*      */     }
/* 3203 */     this._005fjspx_005ftagPool_005fhtml_005foptions_0026_005fproperty_005flabelProperty_005fcollection_005fnobody.reuse(_jspx_th_html_005foptions_005f0);
/* 3204 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\SMSServerConfigUserArea_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */