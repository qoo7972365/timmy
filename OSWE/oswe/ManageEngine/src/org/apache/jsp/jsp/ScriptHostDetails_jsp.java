/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class ScriptHostDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   21 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   27 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*   28 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   52 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   56 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   73 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   77 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   78 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*   79 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   80 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   81 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/*   82 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*   83 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/*   84 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*   85 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*   86 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*   87 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.release();
/*   88 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*   89 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.release();
/*   90 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/*   91 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*   92 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   99 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  102 */     JspWriter out = null;
/*  103 */     Object page = this;
/*  104 */     JspWriter _jspx_out = null;
/*  105 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  109 */       response.setContentType("text/html;charset=UTF-8");
/*  110 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  112 */       _jspx_page_context = pageContext;
/*  113 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  114 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  115 */       session = pageContext.getSession();
/*  116 */       out = pageContext.getOut();
/*  117 */       _jspx_out = out;
/*      */       
/*  119 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*  120 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  121 */       out.write("\n\n\n\n\n<link href=\"/images/");
/*  122 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  124 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n");
/*      */       
/*  126 */       String avoidFilter = "true".equals(request.getParameter("avoidFilter")) ? "true" : "false";
/*  127 */       String hostname = "";
/*  128 */       String username = "";
/*  129 */       String mode = "TELNET";
/*  130 */       String prompt = "$";
/*  131 */       String port = "23";
/*      */       try
/*      */       {
/*  134 */         com.adventnet.appmanager.db.AMConnectionPool cp = com.adventnet.appmanager.db.AMConnectionPool.getInstance();
/*  135 */         java.sql.ResultSet rs = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt("select * from AM_SCRIPTHOSTDETAILS where ID=" + request.getParameter("mapperid"));
/*  136 */         if (rs.next())
/*      */         {
/*  138 */           hostname = rs.getString("HOSTNAME");
/*  139 */           username = rs.getString("USERNAME");
/*  140 */           mode = rs.getString("MODE");
/*  141 */           prompt = rs.getString("PROMPT");
/*  142 */           port = rs.getString("PORT");
/*      */         }
/*  144 */         rs.close();
/*      */       }
/*      */       catch (Exception exc) {}
/*      */       
/*      */ 
/*      */ 
/*  150 */       if (mode.equals("SSH_KEY"))
/*      */       {
/*  152 */         mode = "SSH";
/*      */       }
/*      */       
/*  155 */       out.write("\n\n<script language=\"JavaScript1.2\">\n\nfunction changeport()\n{\n\tvar mode=document.AMActionForm.monitoringmode.value;\n\tif(mode==\"TELNET\")\n\t{\n\t\tjavascript:hideDiv('sshKeyAuth');\n\t\tjavascript:showDiv('passwordid');\n\t\tdocument.AMActionForm.port.value=\"23\";\n\t\tdocument.AMActionForm.sshkey.checked=false;\n\t\tjavascript:hideDiv(\"keyid\");\n\t\tjavascript:hideDiv(\"passphraseid\");\n\t}\n\telse\n\t{\n\t\tjavascript:showDiv('sshKeyAuth');\n\t\tjavascript:showDiv('passwordid');\n\t\tdocument.AMActionForm.port.value=\"22\";\n\t}\n\tif($('input[name=isCredManager]:checked').val() =='true')\n\t{\n\t\tswitchCredentialProfiles();\n\t}\n}\n\nfunction showPrivateKey()\n{\n  if(document.AMActionForm.sshkey.checked)\n  {\n    javascript:hideDiv(\"passwordid\");\n    javascript:showDiv(\"keyid\");\n    javascript:showDiv(\"passphraseid\");\n  }\n  else\n  {\n   javascript:showDiv(\"passwordid\");\n   javascript:hideDiv(\"keyid\");\n   javascript:hideDiv(\"passphraseid\");\n  }\n}\n\nfunction submitForm()\n{\n\tif($('input[name=isCredManager]:checked').val()=='true' && document.AMActionForm.credentialID.value=='No Credentials'){\n");
/*  156 */       out.write("\t\talert('");
/*  157 */       out.print(FormatUtil.getString("am.webclient.common.chooseCredential.text"));
/*  158 */       out.write("');\n\t\tdocument.AMActionForm.credentialID.focus();\n\t\treturn;\n\t}\n            if(document.AMActionForm.host.value=='')\n            {\n              alert('");
/*  159 */       out.print(FormatUtil.getString("am.webclient.hostnameemp.text"));
/*  160 */       out.write("');\n              document.AMActionForm.host.focus();\n              return;\n            }\n            if(document.AMActionForm.username.value=='' && $('input[name=isCredManager]:checked').val() =='false')\n            {\n              alert('");
/*  161 */       out.print(FormatUtil.getString("am.webclient.usernameemp.text"));
/*  162 */       out.write("');\n              document.AMActionForm.username.focus();\n              return;\n            }\n\t    if(document.AMActionForm.sshkey.checked==true)\n\t\t{\n\t\tif(document.AMActionForm.description.value=='')\n\t\t{\n\t\talert('");
/*  163 */       out.print(FormatUtil.getString("am.webclient.sshkeyemp.text"));
/*  164 */       out.write("');\n\t\tdocument.AMActionForm.sshkey.focus();\n\t\treturn;\n\t\t}\n\t\t}\n            else if(document.AMActionForm.password.value=='' && $('input[name=isCredManager]:checked').val() =='false')\n            {\n              if(confirm('");
/*  165 */       out.print(FormatUtil.getString("password.empty.message"));
/*  166 */       out.write("'))\n              {\n              }\n              else\n              {\n                document.AMActionForm.password.focus();\n                return;\n              }\n            }\n            if(document.AMActionForm.port.value=='')\n            {\n              alert('");
/*  167 */       out.print(FormatUtil.getString("am.webclient.portemp.text"));
/*  168 */       out.write(" ');\n              document.AMActionForm.port.focus();\n              return;\n            }\n            if(document.AMActionForm.prompt.value=='' && $('input[name=isCredManager]:checked').val() =='false')\n            {\n              alert('");
/*  169 */       out.print(FormatUtil.getString("am.webclient.promptemp.text"));
/*  170 */       out.write("');\n              document.AMActionForm.prompt.focus();\n              return;\n            }\n            if(confirm('");
/*  171 */       out.print(FormatUtil.getString("am.webclient.alertupdate.text"));
/*  172 */       out.write("'))\n            {\t\n              document.AMActionForm.action=\"/updateScript.do?method=updateHostDetails&mapperid=\"+");
/*  173 */       out.print(request.getParameter("mapperid"));
/*  174 */       out.write("+\"&resourceid=\"+");
/*  175 */       out.print(request.getParameter("resourceid"));
/*  176 */       out.write("+\"&avoidFilter=");
/*  177 */       out.print(avoidFilter);
/*  178 */       out.write("\";  ");
/*  179 */       out.write("\n              document.AMActionForm.submit();\n            }\n\treturn;\n}\n\nfunction myOnLoad()\n{\n    \n    ");
/*      */       
/*  181 */       String toclose = request.getParameter("toclose");
/*  182 */       String updated = request.getParameter("updated");
/*  183 */       if ((toclose != null) && (toclose.equals("true")))
/*      */       {
/*      */ 
/*  186 */         out.write("\n\t\t  window.close();\n                  ");
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*  191 */       else if (mode.equals("TELNET"))
/*      */       {
/*      */ 
/*  194 */         out.write("\n\t\tjavascript:hideDiv('sshKeyAuth');\n\t\tjavascript:showDiv('passwordid');\n\t\tdocument.AMActionForm.port.value=\"23\";\n");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*  200 */         out.write("\t\tjavascript:showDiv('sshKeyAuth');\n\t\tjavascript:showDiv('passwordid');\n\t\tdocument.AMActionForm.port.value=\"22\";\n");
/*      */       }
/*      */       
/*      */ 
/*  204 */       out.write("\n    if($('input[name=isCredManager]:checked').val() =='true')\n\t  {\n\t\tdocument.getElementById(\"credentialFormDiv\").style.display=\"none\";\n\t\tswitchCredentialProfiles();\n\t  }\n  else{\n  \tdocument.getElementById(\"credentialDropDiv\").style.display=\"none\";\n  }\n}\n\n\nfunction showCredentialProfiles()\n{\n  if($('input[name=isCredManager]:checked').val() =='true')\n  {\n\tdocument.getElementById(\"credentialDropDiv\").style.display=\"block\";\n\tdocument.getElementById(\"credentialFormDiv\").style.display=\"none\";\n\tswitchCredentialProfiles();\n  }\n  else\n  {\n       document.getElementById(\"credentialDropDiv\").style.display=\"none\";\n       document.getElementById(\"credentialDropRow\").height=\"1px\";\n       document.getElementById(\"credentialFormDiv\").style.display=\"block\";\n  }\n}\n\nfunction switchCredentialProfiles()\n{ \n var mode=document.AMActionForm.monitoringmode.value;\n    for (var i = 0; i < document.AMActionForm.credentialID.length; i++) \n { //Clear the 2nd menu\n  document.AMActionForm.credentialID.options[i] = null;\n }\n    var i = 0;\n");
/*  205 */       out.write("    var selectedIndex = 0;\n    var isdata = 0; \n    if(mode =='SSH')\n    {\n     ");
/*  206 */       if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */         return;
/*  208 */       out.write("\n       document.AMActionForm.credentialID.selectedIndex=selectedIndex;\n    if(isdata==0){\n     document.AMActionForm.credentialID.options[i] = new Option('No Credentials','No Credentials',0,0);  ");
/*  209 */       out.write("\n    }\n   \n  \n    }\n    else if(mode =='TELNET')\n    {\n        \n       ");
/*  210 */       if (_jspx_meth_c_005fforEach_005f1(_jspx_page_context))
/*      */         return;
/*  212 */       out.write("\n       document.AMActionForm.credentialID.selectedIndex=selectedIndex;\n       \n       if(isdata==0){\n      document.AMActionForm.credentialID.options[i] = new Option('No Credentials','No Credentials',0,0);  ");
/*  213 */       out.write("\n     }\n    \n     \n  \n    }\n    else\n    {\n        ");
/*  214 */       if (_jspx_meth_c_005fforEach_005f2(_jspx_page_context))
/*      */         return;
/*  216 */       out.write("\n       document.AMActionForm.credentialID.selectedIndex=selectedIndex;\n       \n       if(isdata==0){\n      document.AMActionForm.credentialID.options[i] = new Option('No Credentials','No Credentials',0,0);  ");
/*  217 */       out.write("\n     }\n    \n  \n    }\n    \n}\n\n</script>\n\n<title>");
/*  218 */       out.print(FormatUtil.getString("Update Host Details"));
/*  219 */       out.write("</title>\n<body onLoad=\"javascript:myOnLoad()\"></body>\n");
/*      */       
/*  221 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/*  222 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  223 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/*  225 */       _jspx_th_html_005fform_005f0.setAction("/updateScript");
/*      */       
/*  227 */       _jspx_th_html_005fform_005f0.setMethod("post");
/*      */       
/*  229 */       _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/*  230 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  231 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/*  233 */           out.write("\n\n<table class=\"darkheaderbg\" height=\"55\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n<tr>\n<td>\n<span class=\"headingboldwhite\">");
/*  234 */           out.print(FormatUtil.getString("am.webclient.newscript.edithostdetails.text"));
/*  235 */           out.write("\n </span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\" style=\"position:relative; top:8px;\">\n    <tr>\n              <td class=\"bodytext whitegrayrightalign\" width=\"25%\">");
/*  236 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.hostname"));
/*  237 */           out.write("<span class=\"mandatory\">*</span></td>\n             <td width=\"75%\" class=\"whitegrayrightalign credential-popup\" > ");
/*      */           
/*  239 */           TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  240 */           _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/*  241 */           _jspx_th_html_005ftext_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  243 */           _jspx_th_html_005ftext_005f0.setProperty("host");
/*      */           
/*  245 */           _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */           
/*  247 */           _jspx_th_html_005ftext_005f0.setValue(hostname);
/*      */           
/*  249 */           _jspx_th_html_005ftext_005f0.setSize("15");
/*  250 */           int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/*  251 */           if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/*  252 */             this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0); return;
/*      */           }
/*      */           
/*  255 */           this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  256 */           out.write("</td>\n            </tr>\n            <tr>\n\t       <td class=\"bodytext whitegrayrightalign\" width=\"25%\">");
/*  257 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.mode"));
/*  258 */           out.write("<span class=\"mandatory\">*</span></td>\n             <td width=\"75%\" class=\"whitegrayrightalign credential-popup\" ><span> ");
/*      */           
/*  260 */           SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/*  261 */           _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/*  262 */           _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  264 */           _jspx_th_html_005fselect_005f0.setProperty("monitoringmode");
/*      */           
/*  266 */           _jspx_th_html_005fselect_005f0.setValue(mode);
/*      */           
/*  268 */           _jspx_th_html_005fselect_005f0.setStyle("width:125px");
/*      */           
/*  270 */           _jspx_th_html_005fselect_005f0.setOnchange("changeport()");
/*      */           
/*  272 */           _jspx_th_html_005fselect_005f0.setStyleClass("formtextarea");
/*  273 */           int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/*  274 */           if (_jspx_eval_html_005fselect_005f0 != 0) {
/*  275 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  276 */               out = _jspx_page_context.pushBody();
/*  277 */               _jspx_th_html_005fselect_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  278 */               _jspx_th_html_005fselect_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  281 */               out.write("\n        \t ");
/*      */               
/*  283 */               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  284 */               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/*  285 */               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  287 */               _jspx_th_html_005foption_005f0.setValue("TELNET");
/*  288 */               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/*  289 */               if (_jspx_eval_html_005foption_005f0 != 0) {
/*  290 */                 if (_jspx_eval_html_005foption_005f0 != 1) {
/*  291 */                   out = _jspx_page_context.pushBody();
/*  292 */                   _jspx_th_html_005foption_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  293 */                   _jspx_th_html_005foption_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  296 */                   out.write(32);
/*  297 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.telnet"));
/*  298 */                   out.write(32);
/*  299 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/*  300 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  303 */                 if (_jspx_eval_html_005foption_005f0 != 1) {
/*  304 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  307 */               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/*  308 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */               }
/*      */               
/*  311 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/*  312 */               out.write("\n\t         ");
/*      */               
/*  314 */               OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  315 */               _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/*  316 */               _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  318 */               _jspx_th_html_005foption_005f1.setValue("SSH");
/*  319 */               int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/*  320 */               if (_jspx_eval_html_005foption_005f1 != 0) {
/*  321 */                 if (_jspx_eval_html_005foption_005f1 != 1) {
/*  322 */                   out = _jspx_page_context.pushBody();
/*  323 */                   _jspx_th_html_005foption_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  324 */                   _jspx_th_html_005foption_005f1.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  327 */                   out.write(32);
/*  328 */                   out.write(32);
/*  329 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh"));
/*  330 */                   out.write("    ");
/*  331 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/*  332 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  335 */                 if (_jspx_eval_html_005foption_005f1 != 1) {
/*  336 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  339 */               if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/*  340 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */               }
/*      */               
/*  343 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/*  344 */               out.write("\n\t         ");
/*  345 */               if (request.getParameter("mtype") != null) {
/*  346 */                 String mtype = request.getParameter("mtype");
/*  347 */                 if ((System.getProperty("os.name").startsWith("Windows")) && ((mtype.equals("file")) || (mtype.equals("directory")) || (mtype.equals("File System Monitor")))) {
/*  348 */                   out.write("\n\t\t \t\t\t\n\t\t \t\t\t");
/*      */                   
/*  350 */                   OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  351 */                   _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/*  352 */                   _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                   
/*  354 */                   _jspx_th_html_005foption_005f2.setValue("WMI");
/*  355 */                   int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/*  356 */                   if (_jspx_eval_html_005foption_005f2 != 0) {
/*  357 */                     if (_jspx_eval_html_005foption_005f2 != 1) {
/*  358 */                       out = _jspx_page_context.pushBody();
/*  359 */                       _jspx_th_html_005foption_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  360 */                       _jspx_th_html_005foption_005f2.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/*  363 */                       out.write(32);
/*  364 */                       out.print(FormatUtil.getString("WMI"));
/*  365 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/*  366 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  369 */                     if (_jspx_eval_html_005foption_005f2 != 1) {
/*  370 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  373 */                   if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/*  374 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                   }
/*      */                   
/*  377 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/*  378 */                   out.write(10);
/*  379 */                   out.write(9);
/*  380 */                   out.write(9);
/*      */                 } }
/*  382 */               out.write("\n\t        ");
/*  383 */               int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/*  384 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  387 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  388 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  391 */           if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/*  392 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */           }
/*      */           
/*  395 */           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/*  396 */           out.write("</span></td>\n            </tr>\n\t\t\n          <tr  height=\"35\">\n\t\t  <td  class=\"bodytext whitegrayrightalign\" width=\"25%\">");
/*  397 */           out.print(FormatUtil.getString("Credential Details"));
/*  398 */           out.write("</a></div><span class=\"mandatory\">*</span></td>\n\t\t  <td class=\"bodytext\" valign=\"center\">\n\t\t\t\t");
/*  399 */           if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  401 */           out.write("<span class=\"bodytext\">");
/*  402 */           out.print(FormatUtil.getString("Use below credential"));
/*  403 */           out.write("</span>&nbsp;\n\t\t\t\t");
/*  404 */           if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  406 */           out.write("<span class=\"bodytext\">");
/*  407 */           out.print(FormatUtil.getString("Select from credential list"));
/*  408 */           out.write("</span>\n\t\t  </td>\n\t      </tr>\n\t       <tr height=\"1px\" id='credentialDropRow' >\n\t\t  <td colspan='2' width='100%'>\n\t\t  <div id='credentialDropDiv'>\n\t\t    <table width='100%' cellpadding=\"5\" cellspacing=\"0\" height=\"30px\">\n\t\t      <tr>\n\t\t       <td  class=\"bodytext whitegrayrightalign\" width=\"25%\">");
/*  409 */           out.print(FormatUtil.getString("Credential Manager"));
/*  410 */           out.write("</a></div><span class=\"mandatory\">*</span></td>\n\t\t       <td class=\"bodytext\" valign=\"center\">\n\t\t\t");
/*  411 */           if (_jspx_meth_logic_005fnotEmpty_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  413 */           out.write("\n\t\t\t");
/*  414 */           if (_jspx_meth_logic_005fempty_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  416 */           out.write("\n\t\t      </td>\n\t\t     </tr>\n\t\t    </table>\n\t\t  </div>\n\t\t  </td>\n\t      </tr>\n\t<tr >\n\t<td colspan=\"2\" >\n\t   <div id ='credentialFormDiv'>\n\t   <table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t<tr>\n\t\t  <td colspan=\"2\">\n\t<div id=\"sshKeyAuth\" style=\"display:none\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t<td class=\"bodytext whitegrayrightalign\" width=\"25%\">");
/*  417 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh.publickey.text"));
/*  418 */           out.write("</TD>\n\t<td width=\"75%\" class=\"whitegrayrightalign credential-popup\" >");
/*  419 */           if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  421 */           out.write("</TD>\n\t</tr>\n\t</table>\n\t</div>\n\t</td></tr>\n          <tr class=\"whitegrayborder\"> \n            <td class=\"bodytext whitegrayrightalign\" width=\"25%\">");
/*  422 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/*  423 */           out.write("<span class=\"mandatory\">*</span></td>\n           <td width=\"75%\" class=\"whitegrayrightalign credential-popup\" >");
/*      */           
/*  425 */           TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  426 */           _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/*  427 */           _jspx_th_html_005ftext_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  429 */           _jspx_th_html_005ftext_005f1.setProperty("username");
/*      */           
/*  431 */           _jspx_th_html_005ftext_005f1.setValue(username);
/*      */           
/*  433 */           _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */           
/*  435 */           _jspx_th_html_005ftext_005f1.setSize("15");
/*  436 */           int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/*  437 */           if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/*  438 */             this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1); return;
/*      */           }
/*      */           
/*  441 */           this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/*  442 */           out.write("&nbsp;&nbsp; \n            </td>\n          </tr >\n\t<tr >\n\t<td colspan=\"2\">\n\t<div id=\"passwordid\" style=\"display:none\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n          <tr class=\"whitegrayborder\"> \n           <td class=\"bodytext whitegrayrightalign\" width=\"25%\">");
/*  443 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/*  444 */           out.write("<span class=\"mandatory\">*</span></td>\n         <td width=\"75%\" class=\"whitegrayrightalign credential-popup\" >");
/*  445 */           if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  447 */           out.write(" \n              &nbsp;</td>\n\t\t\t  <!--td><b>*</b><span class=\"footer\">");
/*  448 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.passwordleave"));
/*  449 */           out.write("</span></td-->\n          </tr>\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n\t<tr >\n\t<td colspan=\"2\">\n\t<div id=\"keyid\" style=\"display:none\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t <td class=\"bodytext whitegrayrightalign\" width=\"25%\">");
/*  450 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh.privateKey"));
/*  451 */           out.write("<span class=\"mandatory\">*</span>\n\t</TD>\n\t<td width=\"75%\" class=\"whitegrayrightalign credential-popup\" >");
/*  452 */           if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  454 */           out.write(" </TD>\n\t</TR>\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n\t<tr>\n\t<td colspan=\"2\">\n\t<div id=\"passphraseid\" style=\"display:none\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t  <tr>\n\t    <td class=\"bodytext whitegrayrightalign\" width=\"25%\">");
/*  455 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.passphrase"));
/*  456 */           out.write("</td>\n\t    <td width=\"75%\" class=\"whitegrayrightalign credential-popup\" ><input type=\"password\" name=\"passphrase\" class=\"formtext\" size=\"15\" autocomplete=\"off\" />\n\t     </td>\n\t  </tr>\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n            <tr>\n             <td class=\"bodytext whitegrayrightalign\" width=\"25%\">");
/*  457 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.commandprompt"));
/*  458 */           out.write("</td>\n              <td width=\"75%\" class=\"whitegrayrightalign credential-popup\" >");
/*      */           
/*  460 */           TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  461 */           _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/*  462 */           _jspx_th_html_005ftext_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  464 */           _jspx_th_html_005ftext_005f2.setProperty("prompt");
/*      */           
/*  466 */           _jspx_th_html_005ftext_005f2.setValue(prompt);
/*      */           
/*  468 */           _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */           
/*  470 */           _jspx_th_html_005ftext_005f2.setSize("15");
/*  471 */           int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/*  472 */           if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/*  473 */             this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2); return;
/*      */           }
/*      */           
/*  476 */           this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/*  477 */           out.write("</td>\n            </tr>\n            \n     </table>\n     </div></td></tr>\n     <tr>\n              <td class=\"bodytext whitegrayrightalign\" width=\"25%\">");
/*  478 */           out.print(FormatUtil.getString("Port"));
/*  479 */           out.write("</td>\n              <td width=\"75%\" class=\"whitegrayrightalign credential-popup\" >");
/*      */           
/*  481 */           TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  482 */           _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/*  483 */           _jspx_th_html_005ftext_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  485 */           _jspx_th_html_005ftext_005f3.setProperty("port");
/*      */           
/*  487 */           _jspx_th_html_005ftext_005f3.setValue(port);
/*      */           
/*  489 */           _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */           
/*  491 */           _jspx_th_html_005ftext_005f3.setSize("15");
/*  492 */           int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/*  493 */           if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/*  494 */             this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3); return;
/*      */           }
/*      */           
/*  497 */           this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/*  498 */           out.write("</td>\n            </tr>\n    \n             <tr> \n            \t <td height=\"27\" align=\"center\" class=\"tablebottom credential-popup\"><img src=\"/images/spacer.gif\" height='8' width='1'></td>\n       \t\t\t<td height=\"27\"  align=\"left\" class=\"tablebottom credential-popup\">\n\t\t           <input name=\"Submit\" type=\"button\" class=\"buttons\" value='");
/*  499 */           out.print(FormatUtil.getString("am.webclient.common.update.text"));
/*  500 */           out.write("' onClick=\"javascript:submitForm();\"> \n\t\t           <input name=\"GoBack\" type=\"button\" class=\"buttons\" value='");
/*  501 */           out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/*  502 */           out.write("'  onClick=\"window.close();\">&nbsp;&nbsp; \n       \t\t\t</td>\n        </tr>\n</table>\n    <table width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" style=\"padding-left:8px\">\n        <tr>\n        &nbsp;&nbsp;\n        </tr>\n\t<tr>\n            <td class=\"bodytext\">");
/*  503 */           out.print(FormatUtil.getString("am.webclient.scripthostpassword.text"));
/*  504 */           out.write(" </td>\n\t</tr>\n\n        <tr>\n            <td class=\"bodytext\">");
/*  505 */           out.print(FormatUtil.getString("am.webclient.scripthostprompt.text"));
/*  506 */           out.write("</td>\n        </tr>\n    </table>\n        \n");
/*  507 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  508 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  512 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  513 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else {
/*  516 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  517 */         out.write(10);
/*      */       }
/*  519 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  520 */         out = _jspx_out;
/*  521 */         if ((out != null) && (out.getBufferSize() != 0))
/*  522 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  523 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  526 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  532 */     PageContext pageContext = _jspx_page_context;
/*  533 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  535 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  536 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  537 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  539 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  541 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  542 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  543 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  544 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  545 */       return true;
/*      */     }
/*  547 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  548 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  553 */     PageContext pageContext = _jspx_page_context;
/*  554 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  556 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/*  557 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/*  558 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/*  560 */     _jspx_th_c_005fforEach_005f0.setItems("${credentialHash.SSH}");
/*      */     
/*  562 */     _jspx_th_c_005fforEach_005f0.setVar("credential");
/*  563 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/*  565 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/*  566 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/*  568 */           out.write("\n   document.AMActionForm.credentialID.options[i] = new Option('");
/*  569 */           boolean bool; if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  570 */             return true;
/*  571 */           out.write(39);
/*  572 */           out.write(44);
/*  573 */           out.write(39);
/*  574 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  575 */             return true;
/*  576 */           out.write("',0,0);\n    ");
/*  577 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  578 */             return true;
/*  579 */           out.write("\n   i++;\n   isdata = 1; \n     ");
/*  580 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/*  581 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  585 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*  586 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/*  589 */         int tmp276_275 = 0; int[] tmp276_273 = _jspx_push_body_count_c_005fforEach_005f0; int tmp278_277 = tmp276_273[tmp276_275];tmp276_273[tmp276_275] = (tmp278_277 - 1); if (tmp278_277 <= 0) break;
/*  590 */         out = _jspx_page_context.popBody(); }
/*  591 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/*  593 */       _jspx_th_c_005fforEach_005f0.doFinally();
/*  594 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/*  596 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  601 */     PageContext pageContext = _jspx_page_context;
/*  602 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  604 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  605 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/*  606 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/*  608 */     _jspx_th_c_005fout_005f1.setValue("${credential.value}");
/*  609 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/*  610 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/*  611 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  612 */       return true;
/*      */     }
/*  614 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  615 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  620 */     PageContext pageContext = _jspx_page_context;
/*  621 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  623 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  624 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/*  625 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/*  627 */     _jspx_th_c_005fout_005f2.setValue("${credential.key}");
/*  628 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/*  629 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/*  630 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  631 */       return true;
/*      */     }
/*  633 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  634 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  639 */     PageContext pageContext = _jspx_page_context;
/*  640 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  642 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  643 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  644 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/*  646 */     _jspx_th_c_005fif_005f0.setTest("${credentialIDSel == credential.key}");
/*  647 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  648 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/*  650 */         out.write("\n     selectedIndex = i;\n   ");
/*  651 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  652 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  656 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  657 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  658 */       return true;
/*      */     }
/*  660 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  661 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  666 */     PageContext pageContext = _jspx_page_context;
/*  667 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  669 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/*  670 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/*  671 */     _jspx_th_c_005fforEach_005f1.setParent(null);
/*      */     
/*  673 */     _jspx_th_c_005fforEach_005f1.setItems("${credentialHash.TELNET}");
/*      */     
/*  675 */     _jspx_th_c_005fforEach_005f1.setVar("credential");
/*  676 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/*  678 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/*  679 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/*  681 */           out.write("\n   document.AMActionForm.credentialID.options[i] = new Option('");
/*  682 */           boolean bool; if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  683 */             return true;
/*  684 */           out.write(39);
/*  685 */           out.write(44);
/*  686 */           out.write(39);
/*  687 */           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  688 */             return true;
/*  689 */           out.write("',0,0);\n   ");
/*  690 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*  691 */             return true;
/*  692 */           out.write("\n   i++;\n   isdata = 1; \n       ");
/*  693 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/*  694 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  698 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*  699 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/*  702 */         int tmp276_275 = 0; int[] tmp276_273 = _jspx_push_body_count_c_005fforEach_005f1; int tmp278_277 = tmp276_273[tmp276_275];tmp276_273[tmp276_275] = (tmp278_277 - 1); if (tmp278_277 <= 0) break;
/*  703 */         out = _jspx_page_context.popBody(); }
/*  704 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/*  706 */       _jspx_th_c_005fforEach_005f1.doFinally();
/*  707 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/*  709 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/*  714 */     PageContext pageContext = _jspx_page_context;
/*  715 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  717 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  718 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/*  719 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/*  721 */     _jspx_th_c_005fout_005f3.setValue("${credential.value}");
/*  722 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/*  723 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/*  724 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  725 */       return true;
/*      */     }
/*  727 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  728 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/*  733 */     PageContext pageContext = _jspx_page_context;
/*  734 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  736 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  737 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/*  738 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/*  740 */     _jspx_th_c_005fout_005f4.setValue("${credential.key}");
/*  741 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/*  742 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/*  743 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/*  744 */       return true;
/*      */     }
/*  746 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/*  747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/*  752 */     PageContext pageContext = _jspx_page_context;
/*  753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  755 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  756 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  757 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/*  759 */     _jspx_th_c_005fif_005f1.setTest("${credentialIDSel == credential.key}");
/*  760 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  761 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/*  763 */         out.write("\n     selectedIndex = i;\n   ");
/*  764 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  765 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  769 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  770 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  771 */       return true;
/*      */     }
/*  773 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  779 */     PageContext pageContext = _jspx_page_context;
/*  780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  782 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/*  783 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/*  784 */     _jspx_th_c_005fforEach_005f2.setParent(null);
/*      */     
/*  786 */     _jspx_th_c_005fforEach_005f2.setItems("${credentialHash.WMI}");
/*      */     
/*  788 */     _jspx_th_c_005fforEach_005f2.setVar("credential");
/*  789 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/*  791 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/*  792 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/*  794 */           out.write("\n   document.AMActionForm.credentialID.options[i] = new Option('");
/*  795 */           boolean bool; if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*  796 */             return true;
/*  797 */           out.write(39);
/*  798 */           out.write(44);
/*  799 */           out.write(39);
/*  800 */           if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*  801 */             return true;
/*  802 */           out.write("',0,0);\n   ");
/*  803 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*  804 */             return true;
/*  805 */           out.write("\n   i++;\n   isdata = 1; \n       ");
/*  806 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/*  807 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  811 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/*  812 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/*  815 */         int tmp276_275 = 0; int[] tmp276_273 = _jspx_push_body_count_c_005fforEach_005f2; int tmp278_277 = tmp276_273[tmp276_275];tmp276_273[tmp276_275] = (tmp278_277 - 1); if (tmp278_277 <= 0) break;
/*  816 */         out = _jspx_page_context.popBody(); }
/*  817 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/*  819 */       _jspx_th_c_005fforEach_005f2.doFinally();
/*  820 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/*  822 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/*  827 */     PageContext pageContext = _jspx_page_context;
/*  828 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  830 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  831 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/*  832 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/*  834 */     _jspx_th_c_005fout_005f5.setValue("${credential.value}");
/*  835 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/*  836 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/*  837 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/*  838 */       return true;
/*      */     }
/*  840 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/*  841 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/*  846 */     PageContext pageContext = _jspx_page_context;
/*  847 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  849 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  850 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/*  851 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/*  853 */     _jspx_th_c_005fout_005f6.setValue("${credential.key}");
/*  854 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/*  855 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/*  856 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/*  857 */       return true;
/*      */     }
/*  859 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/*  860 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/*  865 */     PageContext pageContext = _jspx_page_context;
/*  866 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  868 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  869 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  870 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/*  872 */     _jspx_th_c_005fif_005f2.setTest("${credentialIDSel == credential.key}");
/*  873 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  874 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/*  876 */         out.write("\n     selectedIndex = i;\n   ");
/*  877 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  878 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  882 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  883 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  884 */       return true;
/*      */     }
/*  886 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  887 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  892 */     PageContext pageContext = _jspx_page_context;
/*  893 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  895 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/*  896 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/*  897 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  899 */     _jspx_th_html_005fradio_005f0.setProperty("isCredManager");
/*      */     
/*  901 */     _jspx_th_html_005fradio_005f0.setValue("false");
/*      */     
/*  903 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:showCredentialProfiles();");
/*  904 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/*  905 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/*  906 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/*  907 */       return true;
/*      */     }
/*  909 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/*  910 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  915 */     PageContext pageContext = _jspx_page_context;
/*  916 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  918 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/*  919 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/*  920 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  922 */     _jspx_th_html_005fradio_005f1.setProperty("isCredManager");
/*      */     
/*  924 */     _jspx_th_html_005fradio_005f1.setValue("true");
/*      */     
/*  926 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:showCredentialProfiles();");
/*  927 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/*  928 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/*  929 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/*  930 */       return true;
/*      */     }
/*  932 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/*  933 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  938 */     PageContext pageContext = _jspx_page_context;
/*  939 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  941 */     org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (org.apache.struts.taglib.logic.NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
/*  942 */     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/*  943 */     _jspx_th_logic_005fnotEmpty_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  945 */     _jspx_th_logic_005fnotEmpty_005f0.setName("credentialHash");
/*  946 */     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/*  947 */     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */       for (;;) {
/*  949 */         out.write("\n\t\t\t   ");
/*  950 */         if (_jspx_meth_html_005fselect_005f1(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*  951 */           return true;
/*  952 */         out.write("\n\t\t\t");
/*  953 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/*  954 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  958 */     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/*  959 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*  960 */       return true;
/*      */     }
/*  962 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*  963 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  968 */     PageContext pageContext = _jspx_page_context;
/*  969 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  971 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/*  972 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/*  973 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/*  975 */     _jspx_th_html_005fselect_005f1.setStyle("width: 135px;");
/*      */     
/*  977 */     _jspx_th_html_005fselect_005f1.setProperty("credentialID");
/*      */     
/*  979 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtextarea");
/*  980 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/*  981 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/*  982 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/*  983 */         out = _jspx_page_context.pushBody();
/*  984 */         _jspx_th_html_005fselect_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  985 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  988 */         out.write("\t   ");
/*  989 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/*  990 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  993 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/*  994 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  997 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/*  998 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/*  999 */       return true;
/*      */     }
/* 1001 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 1002 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1007 */     PageContext pageContext = _jspx_page_context;
/* 1008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1010 */     org.apache.struts.taglib.logic.EmptyTag _jspx_th_logic_005fempty_005f0 = (org.apache.struts.taglib.logic.EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(org.apache.struts.taglib.logic.EmptyTag.class);
/* 1011 */     _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 1012 */     _jspx_th_logic_005fempty_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1014 */     _jspx_th_logic_005fempty_005f0.setName("credentialHash");
/* 1015 */     int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 1016 */     if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */       for (;;) {
/* 1018 */         out.write("\n\t\t\t\t      <b><i>");
/* 1019 */         if (_jspx_meth_bean_005fmessage_005f0(_jspx_th_logic_005fempty_005f0, _jspx_page_context))
/* 1020 */           return true;
/* 1021 */         out.write("</i></b>\n\t\t\t");
/* 1022 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 1023 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1027 */     if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 1028 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 1029 */       return true;
/*      */     }
/* 1031 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 1032 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fmessage_005f0(JspTag _jspx_th_logic_005fempty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1037 */     PageContext pageContext = _jspx_page_context;
/* 1038 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1040 */     org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
/* 1041 */     _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 1042 */     _jspx_th_bean_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fempty_005f0);
/*      */     
/* 1044 */     _jspx_th_bean_005fmessage_005f0.setKey("No Credentials");
/* 1045 */     int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
/* 1046 */     if (_jspx_th_bean_005fmessage_005f0.doEndTag() == 5) {
/* 1047 */       this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 1048 */       return true;
/*      */     }
/* 1050 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 1051 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1056 */     PageContext pageContext = _jspx_page_context;
/* 1057 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1059 */     org.apache.struts.taglib.html.CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (org.apache.struts.taglib.html.CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(org.apache.struts.taglib.html.CheckboxTag.class);
/* 1060 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 1061 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1063 */     _jspx_th_html_005fcheckbox_005f0.setProperty("sshkey");
/*      */     
/* 1065 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("showPrivateKey()");
/* 1066 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 1067 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 1068 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 1069 */       return true;
/*      */     }
/* 1071 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 1072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1077 */     PageContext pageContext = _jspx_page_context;
/* 1078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1080 */     org.apache.struts.taglib.html.PasswordTag _jspx_th_html_005fpassword_005f0 = (org.apache.struts.taglib.html.PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(org.apache.struts.taglib.html.PasswordTag.class);
/* 1081 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 1082 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1084 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 1086 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*      */     
/* 1088 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/* 1089 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 1090 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 1091 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 1092 */       return true;
/*      */     }
/* 1094 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 1095 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1100 */     PageContext pageContext = _jspx_page_context;
/* 1101 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1103 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 1104 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 1105 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1107 */     _jspx_th_html_005ftextarea_005f0.setProperty("description");
/*      */     
/* 1109 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea");
/*      */     
/* 1111 */     _jspx_th_html_005ftextarea_005f0.setRows("4");
/*      */     
/* 1113 */     _jspx_th_html_005ftextarea_005f0.setCols("30");
/* 1114 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 1115 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 1116 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 1117 */       return true;
/*      */     }
/* 1119 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 1120 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ScriptHostDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */