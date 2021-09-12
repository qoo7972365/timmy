/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.EqualTag;
/*      */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*      */ import org.apache.struts.taglib.logic.NotEqualTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ExecProgramActionForm_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   31 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   37 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   38 */   static { _jspx_dependants.put("/jsp/includes/NewActions.jspf", Long.valueOf(1473429417000L));
/*   39 */     _jspx_dependants.put("/jsp/includes/CreateApplicationWizard.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   78 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   82 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   85 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   86 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   94 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   95 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   96 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   97 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   98 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   99 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  100 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  101 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  102 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  103 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  104 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  105 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  106 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  107 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  108 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  109 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  110 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  111 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  112 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  113 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  114 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  118 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  119 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  120 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  121 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  122 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  123 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  124 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  125 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  126 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  127 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/*  128 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/*  129 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*  130 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/*  131 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  132 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  133 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  134 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  135 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.release();
/*  136 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*  137 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.release();
/*  138 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
/*  139 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*  140 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*  141 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*  142 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  143 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.release();
/*  144 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*  145 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/*  146 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*  147 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/*  148 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  155 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  158 */     JspWriter out = null;
/*  159 */     Object page = this;
/*  160 */     JspWriter _jspx_out = null;
/*  161 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  165 */       response.setContentType("text/html;charset=UTF-8");
/*  166 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  168 */       _jspx_page_context = pageContext;
/*  169 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  170 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  171 */       session = pageContext.getSession();
/*  172 */       out = pageContext.getOut();
/*  173 */       _jspx_out = out;
/*      */       
/*  175 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/*  177 */       boolean isInvokedFromPopup = request.getParameter("popup") != null;
/*      */       
/*  179 */       out.write("\n<script>\n    //myOnLoad1();\n\nfunction fnFormSubmit()\n{\n\tdocument.AMActionForm.submit();\n}\n\nfunction changeport()\n{\n\t\tvar mode=\"TELNET\";\n\t");
/*      */       
/*  181 */       if ((request.getAttribute("remoteactionedit") == null) || (!request.getAttribute("remoteactionedit").equals("true")))
/*      */       {
/*      */ 
/*  184 */         out.write("\n\tmode=document.AMActionForm.monitoringmode.value;\n\tif(mode==\"TELNET\")\n\t{\n\t\tjavascript:hideDiv('sshKeyAuth');\n\t\tjavascript:showDiv('passwordid');\n\t\tdocument.AMActionForm.port.value=\"23\";\n\t\tdocument.AMActionForm.sshkey.checked=false;\n\t\tjavascript:hideDiv(\"keyid\");\n\t\tjavascript:hideDiv(\"passphraseid\");\n\t}\n\telse\n\t{\n\t\tjavascript:showDiv('sshKeyAuth');\n\t\tjavascript:showDiv('passwordid');\n\t\tdocument.AMActionForm.port.value=\"22\";\n\t}\n\t");
/*      */       }
/*      */       
/*      */ 
/*  188 */       out.write("\n}\n\nfunction showPrivateKey()\n{\n  if(document.AMActionForm.sshkey.checked)\n  {\n    javascript:hideDiv(\"passwordid\");\n    javascript:showDiv(\"keyid\");\n    javascript:showDiv(\"passphraseid\");\n  }\n  else\n  {\n   javascript:showDiv(\"passwordid\");\n   javascript:hideDiv(\"keyid\");\n   javascript:hideDiv(\"passphraseid\");\n  }\n}\n\nfunction Edithostdetails()\n{\n\t");
/*  189 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/*  191 */       out.write(10);
/*  192 */       out.write(9);
/*  193 */       out.write(9);
/*  194 */       if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
/*      */         return;
/*  196 */       out.write("\n}\n\nfunction validateAndSubmit(value1)\n{\n\n\t");
/*  197 */       if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */         return;
/*  199 */       out.write("\n\t\n\t ");
/*  200 */       if (com.adventnet.appmanager.util.OEMUtil.isRemove("am.localscript.remove")) {
/*  201 */         out.write("\n\n \tif(document.AMActionForm.serversite.checked == true)\n \t");
/*      */       } else {
/*  203 */         out.write("\n\n  if(document.AMActionForm.serversite[1].checked == true)\n\t");
/*      */       }
/*  205 */       out.write("\n    {\n        if(document.AMActionForm.choosehost.value=='-2')\n        {\n          alert('");
/*  206 */       out.print(FormatUtil.getString("am.webclient.script.selecthost.alert"));
/*  207 */       out.write("');\n          return;\n        }\n        if(document.AMActionForm.choosehost.value=='-1')\n        {\n            if(document.AMActionForm.host.value=='')\n            {\n              alert('");
/*  208 */       out.print(FormatUtil.getString("am.webclient.script.hostname.alert"));
/*  209 */       out.write("');\n              document.AMActionForm.host.focus();\n              return;\n            }\n            if(document.AMActionForm.username.value=='')\n            {\n              alert('");
/*  210 */       out.print(FormatUtil.getString("am.webclient.login.jsalertforusername.text"));
/*  211 */       out.write("');\n              document.AMActionForm.username.focus();\n              return;\n            }\n            if(document.AMActionForm.sshkey.checked==true)\n\t    {\n\t\tif(document.AMActionForm.description.value=='')\n\t\t{\n\t\t\talert('");
/*  212 */       out.print(FormatUtil.getString("am.webclient.sshkeyemp.text"));
/*  213 */       out.write("');\n\t\t\t//alert(\"Private Key cannot be empty if Key Based Authentication is choosed\");\n\t\t\tdocument.AMActionForm.sshkey.focus();\n\t\t\treturn;\n\t\t}\n\t    }\n            else if(document.AMActionForm.password.value=='')\n            {\n              if(confirm('");
/*  214 */       out.print(FormatUtil.getString("password.empty.message"));
/*  215 */       out.write("'))\n              {\n              }\n              else\n              {\n                document.AMActionForm.password.focus();\n                return;\n              }\n            }\n            var temp=trimAll(document.AMActionForm.port.value);\n\n            if((temp == '') || !(isPositiveInteger(temp)))\n            {\n              alert('");
/*  216 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.port"));
/*  217 */       out.write("');\n              document.AMActionForm.port.focus();\n              return;\n            }\n            if(document.AMActionForm.prompt.value=='')\n            {\n              alert('");
/*  218 */       out.print(FormatUtil.getString("am.webclient.script.commandprompt.empty"));
/*  219 */       out.write("');\n              document.AMActionForm.prompt.focus();\n              return;\n            }\n        }\n    }\n\tfor(i=0;i<document.AMActionForm.elements.length;i++)\n\t\t{\n\t\t\tif(document.AMActionForm.elements[i].type==\"text\")\n\t                {\n\t                        var name = document.AMActionForm.elements[i].name;\n\t                        var value = document.AMActionForm.elements[i].value;\n\t\t\t\tif(name==\"displayname\")\n\t                        {\n\t                             if(trimAll(value)==\"\")\n\t                             {\n\t                             \twindow.alert('");
/*  220 */       out.print(FormatUtil.getString("am.webclient.newaction.alertdisplayname"));
/*  221 */       out.write("');\n\t                             \treturn false;\n\t                             }\n\t                             if(displayNameHasQuotes(trimAll(value),'");
/*  222 */       out.print(FormatUtil.getString("am.webclient.newaction.alertsinglequote"));
/*  223 */       out.write("'))\n\t\t\t\t     {\n\t\t\t\t      \treturn false;\n\t                             }\n\t\t\t\t     if(isBlackListedCharactersPresent(value,'");
/*  224 */       out.print(FormatUtil.getString("am.webclient.specialchar.alert.displayname"));
/*  225 */       out.write("')) {\n                              \t\treturn false;\n                                     }\t                             \n\n\t                        }\n\t                        else if((name==\"command\") && (trimAll(value)==\"\"))\n\t                        {\n\t                             window.alert('");
/*  226 */       out.print(FormatUtil.getString("am.webclient.newaction.alertemptybatch"));
/*  227 */       out.write("');\n\t                             return false\n\t                        }\n\t                        else if((name==\"execProgExecDir\") && (trimAll(value)==\"\"))\n\t\t\t\t{\n\t\t\t\t\twindow.alert('");
/*  228 */       out.print(FormatUtil.getString("am.webclient.newaction.alertemptydirectory"));
/*  229 */       out.write("');\n\t\t\t\t\treturn false\n\t                        }\n\t                        else if(name==\"abortafter\")\n\t                        {\n\t                         \tif((trimAll(value)==\"\")|| (isPositiveInteger(value)==false))\n\t                                {\n\t        \t\t\t\twindow.alert('");
/*  230 */       out.print(FormatUtil.getString("am.webclient.newaction.alertemptyabortafter"));
/*  231 */       out.write("');\n\t        \t\t\t\treturn false;\n\t                                }\n\t                        }\n\t\t\t}\n\t}\n\n\tif(value1 == 1)\n\t{\n\t\tdocument.AMActionForm.cancel.value=\"false\";\n\t}\n\telse\n\t{\n\t\tdocument.AMActionForm.cancel.value=\"true\";\n\t}\n\tfnFormSubmit();\n }\nfunction myOnLoad1()\n{\n\t");
/*  232 */       if (com.adventnet.appmanager.util.OEMUtil.isRemove("am.localscript.remove")) {
/*  233 */         out.write("\n         javascript:showDiv('remotescript');\n         if(document.AMActionForm.choosehost.value=='-1')\n         {\n            javascript:showDiv('newhost');\n         }\n         else\n         {\n           javascript:hideDiv('newhost');\n         }\n\n\n \t");
/*      */       } else {
/*  235 */         out.write("\n     if(document.AMActionForm.serversite[0].checked == false)\n     {\n\n       if(document.AMActionForm.serversite[1].checked==false)\n       {\n         document.AMActionForm.serversite[0].checked=true;\n       }\n     }\n     manageremotescript();\n\t ");
/*      */       }
/*  237 */       out.write("\n     changeport();\n}\n\nfunction managenewHost()\n{\n  if(document.AMActionForm.choosehost.value=='-1')\n  {\n        javascript:showDiv('newhost');\n  }\n  else\n  {\n        javascript:hideDiv('newhost');\n  }\n}\n\nfunction manageremotescript()\n{\n    if(document.AMActionForm.serversite[0].checked == true)\n    {\n        javascript:hideDiv('remotescript');\n        javascript:hideDiv('newhost');\n    }\n    else\n    {\n        javascript:showDiv('remotescript');\n        if(document.AMActionForm.choosehost.value=='-1')\n        {\n           javascript:showDiv('newhost');\n        }\n        else\n        {\n          javascript:hideDiv('newhost');\n        }\n    }\n}\n\n</script>\n<link href=\"/images/");
/*  238 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  240 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\"><link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*      */       
/*  242 */       org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/*  243 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  244 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/*  246 */       _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*  247 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  248 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/*  250 */           out.write(10);
/*      */           
/*      */ 
/*  253 */           pageContext.setAttribute("jsppage", "programaction");
/*  254 */           com.adventnet.appmanager.struts.form.AMActionForm frm; if (com.adventnet.appmanager.util.OEMUtil.isOEM())
/*      */           {
/*  256 */             frm = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/*      */           }
/*      */           
/*      */ 
/*  260 */           out.write(10);
/*  261 */           out.write(10);
/*  262 */           out.write("<!--$Id$-->\n              \n\n\n\n\n<script>                      \nfunction fnFormSubmit1(object)\n{\n\tlocation.href=object;\n}\n\nfunction showMailServerSettings()\n {\n         var showMail = false;\n         var query = window.location.search;\n         var pairs = query.split(\"&\");\n         \n         for (var i=0;i<pairs.length;i++)\n         {\n                 var pos = pairs[i].indexOf('=');\n                 if (pos >= 0)\n                 {\n                         var argname = pairs[i].substring(0,pos);\n                         var value = pairs[i].substring(pos+1);\n                         if(argname == \"isFromApi\")\n                         {\n                                 showMail = true;\n                         }\n                         //keys[keys.length] = argname;\n                         //values[values.length] = value;\n                 }\n         }\n         //alert(showMail);\n         return  showMail;\n }\n\nfunction doInitStuffOnBodyLoad()\n{\n        ");
/*      */           
/*  264 */           if ((pageContext.getAttribute("jsppage") != null) && (pageContext.getAttribute("jsppage").equals("programaction")))
/*      */           {
/*      */ 
/*  267 */             out.write("\n        myOnLoad1();\n        ");
/*      */           }
/*      */           
/*      */ 
/*  271 */           out.write("\n        \n\tif(document.AMActionForm!=null)\n\t{\n\tif(typeof(document.AMActionForm.actions)!='undefined')\n\t  {\n\t  if((location.search!= null && (location.search).match(\"EmailAction\")!=null) || (location.path!=null && (location.path).match(\"EMailActionForm\")!=null))\n\t  {\n\t\t\n\t\t");
/*  272 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  274 */           out.write("\t\n\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"SMSAction\")!=null) || (location.path!=null && (location.path).match(\"SMSActionForm\")!=null) || (location.search!= null && (location.search).match(\"SMSServerConfiguration\")!=null))\n\t  {\n\t\t \n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"ExecProg\")!=null) || (location.path!=null && (location.path).match(\"ExecProgramActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"reloadSendTrapActionForm\")!=null) || (location.path!=null && (location.path).match(\"SendTrapActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"Ticket\")!=null) || (location.path!=null && (location.path).match(\"showLogTicket\")!=null) ||  (location.search).match(\"showTicketAction\")!=null )\n");
/*  275 */           out.write("\t  {\n\t\t\t");
/*  276 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  278 */           out.write("\n\t\t  ");
/*      */           
/*  280 */           if ((com.adventnet.appmanager.util.Constants.sqlManager) || (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */           {
/*  282 */             out.write("\n\t\t\tdocument.AMActionForm.actions.selectedIndex=4;\n\t    \t");
/*      */           }
/*      */           else
/*      */           {
/*  286 */             out.write("\n\t       \tdocument.AMActionForm.actions.selectedIndex=5;\n\t  \t\t");
/*      */           }
/*  288 */           out.write("\n\t  }\n\t  else if(location.pathname!= null && (location.pathname).match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(location.search!= null && (location.search).match(\"jre\")!=null || (location.search!=null && (location.search).match(\"ThreadDumpActions\")!=null))\n\t  {\n\t    ");
/*  289 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer())
/*      */           {
/*  291 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=5;\n\t\t");
/*      */           }
/*      */           else
/*      */           {
/*  295 */             out.write("\n\t    document.AMActionForm.actions.selectedIndex=6;\n        ");
/*      */           }
/*  297 */           out.write("\n\t  }\t\n\t  else if(location.search!= null && (location.search).match(\"showVMAction\")!=null || (location.search!=null && (location.search).match(\"ShowVMActions\")!=null))\n\t  {\t\t \n\t    ");
/*  298 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer())
/*      */           {
/*  300 */             out.write("\n\t    if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t    \tdocument.AMActionForm.actions.selectedIndex=9;\n\t    }else{\n\t\t\tdocument.AMActionForm.actions.selectedIndex=7;\n\t    }\n\t\t");
/*      */           }
/*      */           else
/*      */           {
/*  304 */             out.write("\n\t\t  if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t\t    \tdocument.AMActionForm.actions.selectedIndex=10;\n\t\t    }else{\n\t  \t  document.AMActionForm.actions.selectedIndex=8;\n\t\t    }\n        ");
/*      */           }
/*  306 */           out.write("\n\t  }\t  \n\t  else if(location.search!= null && (location.search).match(\"winServAction\")!=null || (location.search!=null && (location.search).match(\"winServAction\")!=null))\n\t  { \n\t    ");
/*  307 */           if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  308 */             out.write("\n\t      document.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */           }
/*  310 */           else if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) {
/*  311 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=8;\n\t\t");
/*      */           } else {
/*  313 */             out.write("\n          document.AMActionForm.actions.selectedIndex=9;\n        ");
/*      */           }
/*  315 */           out.write("\t\t\n\t  }\t  \n\t   else if((location.search!= null && (location.search).match(\"ExecQueryAction\")!=null) || (location.path!=null && (location.path).match(\"ExecQueryActionForm\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=5;\n  \t   }\n\t   else if((location.search!= null && (location.search).match(\"sqlJobAction\")!=null) || (location.path!=null && (location.path).match(\"sqlJobAction\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=7;\n  \t   }\n\t   else if(location.search!= null && (location.search).match(\"amazon\")!=null)\n\t\t  {\n\t\t    ");
/*  316 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) {
/*  317 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */           } else {
/*  319 */             out.write("\n\t\t    document.AMActionForm.actions.selectedIndex=7;\n        ");
/*      */           }
/*  321 */           out.write("\n\t\t  }\n\t  \t  \n\t  }\n\n\tif(document.AMActionForm.selectedBusinessHourID != null)\n\t{\n\t\tinit();\n\t}\n\t\n\t}\n\telse\n\t{\n\t\tif(document.MBeanOperationActionForm.actions!=undefined)\n\t\t{\n\t  \t      document.MBeanOperationActionForm.actions.selectedIndex=4;\t  \n\t  \t}\n\t}\n\n}\nfunction restvalue()\n{\n//alert(document.AMActionForm.actionslist.selectedIndex);\nvar selectedIdx=document.AMActionForm.actionslist.selectedIndex;\ndocument.AMActionForm.reset();\ndocument.AMActionForm.actionslist.selectedIndex=selectedIdx;\n}\n</script>\n");
/*      */           
/*  323 */           String action_haid = request.getParameter("haid");
/*  324 */           String returnpath = "";
/*      */           
/*  326 */           if (request.getParameter("returnpath") != null)
/*      */           {
/*  328 */             returnpath = "&returnpath=" + java.net.URLEncoder.encode(request.getParameter("returnpath"));
/*      */           }
/*      */           
/*      */ 
/*  332 */           out.write(10);
/*  333 */           out.write(10);
/*      */           
/*  335 */           SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  336 */           _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  337 */           _jspx_th_c_005fset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  339 */           _jspx_th_c_005fset_005f0.setVar("isSqlManager");
/*  340 */           int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  341 */           if (_jspx_eval_c_005fset_005f0 != 0) {
/*  342 */             if (_jspx_eval_c_005fset_005f0 != 1) {
/*  343 */               out = _jspx_page_context.pushBody();
/*  344 */               _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  345 */               _jspx_th_c_005fset_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  348 */               out.print(com.adventnet.appmanager.util.Constants.sqlManager);
/*  349 */               int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  350 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  353 */             if (_jspx_eval_c_005fset_005f0 != 1) {
/*  354 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  357 */           if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  358 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */           }
/*      */           
/*  361 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  362 */           out.write(10);
/*      */           
/*  364 */           SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  365 */           _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  366 */           _jspx_th_c_005fset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  368 */           _jspx_th_c_005fset_005f1.setVar("isIt360");
/*  369 */           int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  370 */           if (_jspx_eval_c_005fset_005f1 != 0) {
/*  371 */             if (_jspx_eval_c_005fset_005f1 != 1) {
/*  372 */               out = _jspx_page_context.pushBody();
/*  373 */               _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/*  374 */               _jspx_th_c_005fset_005f1.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  377 */               out.print(com.adventnet.appmanager.util.Constants.isIt360);
/*  378 */               int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/*  379 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  382 */             if (_jspx_eval_c_005fset_005f1 != 1) {
/*  383 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  386 */           if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  387 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */           }
/*      */           
/*  390 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/*  391 */           out.write(10);
/*      */           
/*  393 */           SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  394 */           _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/*  395 */           _jspx_th_c_005fset_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  397 */           _jspx_th_c_005fset_005f2.setVar("isAdminServer");
/*  398 */           int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/*  399 */           if (_jspx_eval_c_005fset_005f2 != 0) {
/*  400 */             if (_jspx_eval_c_005fset_005f2 != 1) {
/*  401 */               out = _jspx_page_context.pushBody();
/*  402 */               _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/*  403 */               _jspx_th_c_005fset_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  406 */               out.print(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer());
/*  407 */               int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/*  408 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  411 */             if (_jspx_eval_c_005fset_005f2 != 1) {
/*  412 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  415 */           if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/*  416 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */           }
/*      */           
/*  419 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/*  420 */           out.write(10);
/*      */           
/*  422 */           SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  423 */           _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/*  424 */           _jspx_th_c_005fset_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  426 */           _jspx_th_c_005fset_005f3.setVar("isProfServer");
/*  427 */           int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/*  428 */           if (_jspx_eval_c_005fset_005f3 != 0) {
/*  429 */             if (_jspx_eval_c_005fset_005f3 != 1) {
/*  430 */               out = _jspx_page_context.pushBody();
/*  431 */               _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/*  432 */               _jspx_th_c_005fset_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  435 */               out.print(com.adventnet.appmanager.util.EnterpriseUtil.isProfEdition());
/*  436 */               int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/*  437 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  440 */             if (_jspx_eval_c_005fset_005f3 != 1) {
/*  441 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  444 */           if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/*  445 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */           }
/*      */           
/*  448 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/*  449 */           out.write(10);
/*      */           
/*  451 */           SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  452 */           _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/*  453 */           _jspx_th_c_005fset_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  455 */           _jspx_th_c_005fset_005f4.setVar("isCloudServer");
/*  456 */           int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/*  457 */           if (_jspx_eval_c_005fset_005f4 != 0) {
/*  458 */             if (_jspx_eval_c_005fset_005f4 != 1) {
/*  459 */               out = _jspx_page_context.pushBody();
/*  460 */               _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/*  461 */               _jspx_th_c_005fset_005f4.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  464 */               out.print(com.adventnet.appmanager.util.EnterpriseUtil.isCloudEdition());
/*  465 */               int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/*  466 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  469 */             if (_jspx_eval_c_005fset_005f4 != 1) {
/*  470 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  473 */           if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/*  474 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */           }
/*      */           
/*  477 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/*  478 */           out.write("\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" >\n  <tr> \n    <td align=\"left\" width=\"50%\" class=\"bodytext\">");
/*  479 */           out.print(FormatUtil.getString("am.webclient.newaction.selectactiontype"));
/*  480 */           out.write("&nbsp;\n\t<select id=\"actionslist\" name=\"actions\" onchange=\"javascript:fnFormSubmit1(this.form.actions.options[this.selectedIndex].value);\" class=\"formtext\">\n\t");
/*      */           
/*  482 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  483 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  484 */           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_html_005fform_005f0);
/*  485 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  486 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */             for (;;) {
/*  488 */               out.write(10);
/*  489 */               out.write(9);
/*      */               
/*  491 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  492 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  493 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */               
/*  495 */               _jspx_th_c_005fwhen_005f0.setTest("${empty param.global}");
/*  496 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  497 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                 for (;;) {
/*  499 */                   out.write("\t\n\t\t<option value=\"/showTile.do?TileName=.EmailActions&haid=");
/*  500 */                   out.print(action_haid);
/*  501 */                   out.print(returnpath);
/*  502 */                   out.write(34);
/*  503 */                   out.write(62);
/*  504 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  505 */                   out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.SMSActions&haid=");
/*  506 */                   out.print(action_haid);
/*  507 */                   out.print(returnpath);
/*  508 */                   out.write(34);
/*  509 */                   out.write(62);
/*  510 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  511 */                   out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.ExecProg&haid=");
/*  512 */                   out.print(action_haid);
/*  513 */                   out.print(returnpath);
/*  514 */                   out.write(34);
/*  515 */                   out.write(62);
/*  516 */                   out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  517 */                   out.write("</option>\n\t\t<option value=\"/adminAction.do?method=reloadSendTrapActionForm&haid=");
/*  518 */                   out.print(action_haid);
/*  519 */                   out.print(returnpath);
/*  520 */                   out.write(34);
/*  521 */                   out.write(62);
/*  522 */                   out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/*  523 */                   out.write("</option>\n\t\t\n\t\t");
/*      */                   
/*  525 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  526 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  527 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*  528 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  529 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/*  531 */                       out.write("\n\t\t\t");
/*      */                       
/*  533 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  534 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  535 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/*  537 */                       _jspx_th_c_005fwhen_005f1.setTest("${!isIt360}");
/*  538 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  539 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/*  541 */                           out.write("\n\t\t\t\t");
/*      */                           
/*  543 */                           ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  544 */                           _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  545 */                           _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/*  546 */                           int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  547 */                           if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                             for (;;) {
/*  549 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/*  551 */                               WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  552 */                               _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  553 */                               _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                               
/*  555 */                               _jspx_th_c_005fwhen_005f2.setTest("${!isSqlManager}");
/*  556 */                               int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  557 */                               if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                 for (;;) {
/*  559 */                                   out.write("\n\t\t\t\t\t\t<!-- MBean Operation-->\n\t\t\t\t\t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  560 */                                   out.print(action_haid);
/*  561 */                                   out.print(returnpath);
/*  562 */                                   out.write(34);
/*  563 */                                   out.write(62);
/*  564 */                                   out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  565 */                                   out.write("</option>\n\t\t\t\t\t");
/*  566 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  567 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  571 */                               if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  572 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                               }
/*      */                               
/*  575 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  576 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/*  578 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  579 */                               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  580 */                               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f2);
/*  581 */                               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  582 */                               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                 for (;;) {
/*  584 */                                   out.write("\n\t\t\t\t\t\t<!-- Execute Query Action-->\n\t   \t\t\t\t\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  585 */                                   out.print(action_haid);
/*  586 */                                   out.print(returnpath);
/*  587 */                                   out.write(34);
/*  588 */                                   out.write(62);
/*  589 */                                   out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  590 */                                   out.write("</option>\n\t   \t\t\t\t\t<!-- Sql job action -->\n\t\t\t\t\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  591 */                                   out.print(action_haid);
/*  592 */                                   out.print(returnpath);
/*  593 */                                   out.write(34);
/*  594 */                                   out.write(62);
/*  595 */                                   out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  596 */                                   out.write("</option>\n\t\t\t\t\t");
/*  597 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  598 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  602 */                               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  603 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                               }
/*      */                               
/*  606 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  607 */                               out.write("\n\t\t\t\t");
/*  608 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  609 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  613 */                           if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  614 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                           }
/*      */                           
/*  617 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  618 */                           out.write("\n\t\t\t\t\t<!-- Log Ticket Operation-->\n\t\t\t\t\t");
/*  619 */                           if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  620 */                             out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  621 */                             out.print(action_haid);
/*  622 */                             out.print(returnpath);
/*  623 */                             out.write(34);
/*  624 */                             out.write(62);
/*  625 */                             out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  626 */                             out.write("</option> ");
/*      */                           }
/*  628 */                           out.write("\n\t\t\t");
/*  629 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  630 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  634 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  635 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/*  638 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  639 */                       out.write("\n\t\t\t");
/*      */                       
/*  641 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  642 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  643 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  644 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  645 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/*  647 */                           out.write("\n\t\t   \t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  648 */                           out.print(action_haid);
/*  649 */                           out.print(returnpath);
/*  650 */                           out.write(34);
/*  651 */                           out.write(62);
/*  652 */                           out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  653 */                           out.write("</option>\n\t\t   \t\t<!-- Log Ticket Operation-->\n\t\t   \t\t");
/*      */                           
/*  655 */                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  656 */                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  657 */                           _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                           
/*  659 */                           _jspx_th_c_005fif_005f2.setTest("${isProfServer || isAdminServer}");
/*  660 */                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  661 */                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                             for (;;) {
/*  663 */                               out.write("\n\t\t\t\t\t");
/*  664 */                               if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  665 */                                 out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  666 */                                 out.print(action_haid);
/*  667 */                                 out.print(returnpath);
/*  668 */                                 out.write(34);
/*  669 */                                 out.write(62);
/*  670 */                                 out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  671 */                                 out.write("</option> ");
/*      */                               }
/*  673 */                               out.write("\n\t\t   \t\t");
/*  674 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  675 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  679 */                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  680 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                           }
/*      */                           
/*  683 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  684 */                           out.write("\n\t\t\t");
/*  685 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  686 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  690 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  691 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/*  694 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  695 */                       out.write(10);
/*  696 */                       out.write(9);
/*  697 */                       out.write(9);
/*  698 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  699 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  703 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  704 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/*  707 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  708 */                   out.write(10);
/*  709 */                   out.write(9);
/*  710 */                   out.write(9);
/*      */                   
/*  712 */                   IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  713 */                   _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  714 */                   _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  716 */                   _jspx_th_c_005fif_005f3.setTest("${!isAdminServer}");
/*  717 */                   int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  718 */                   if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                     for (;;) {
/*  720 */                       out.write("\n\t\t\t<!--JRE Action -->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  721 */                       out.print(action_haid);
/*  722 */                       out.print(returnpath);
/*  723 */                       out.write(34);
/*  724 */                       out.write(62);
/*  725 */                       out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  726 */                       out.write("</option>\n\t\t\t<!--Amazon Instance Action-->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  727 */                       out.print(action_haid);
/*  728 */                       out.print(returnpath);
/*  729 */                       out.write(34);
/*  730 */                       out.write(62);
/*  731 */                       out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  732 */                       out.write("</option>\n\t\t\t<!--VM Action -->\n\t      \t<option value=\"/adminAction.do?method=showVMAction&haid=");
/*  733 */                       out.print(action_haid);
/*  734 */                       out.print(returnpath);
/*  735 */                       out.write(34);
/*  736 */                       out.write(62);
/*  737 */                       out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  738 */                       out.write("</option>\n\t      \t\n\t\t\t<!-- Windows Service action -->\n\t\t\t");
/*      */                       
/*  740 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  741 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  742 */                       _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */                       
/*  744 */                       _jspx_th_c_005fif_005f4.setTest("${!isCloudServer}");
/*  745 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  746 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/*  748 */                           out.write("\n\t   \t\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  749 */                           out.print(action_haid);
/*  750 */                           out.print(returnpath);
/*  751 */                           out.write(34);
/*  752 */                           out.write(62);
/*  753 */                           out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  754 */                           out.write("</option>\n\t   \t\t");
/*  755 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  756 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  760 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  761 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/*  764 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  765 */                       out.write("\n\t   \t\t<option value=\"/adminAction.do?method=showVMAction&isContainerAction=true&haid=");
/*  766 */                       out.print(action_haid);
/*  767 */                       out.print(returnpath);
/*  768 */                       out.write(34);
/*  769 */                       out.write(62);
/*  770 */                       out.print(FormatUtil.getString("am.container.action.createnew"));
/*  771 */                       out.write("</option>\n   \t\t");
/*  772 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  773 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  777 */                   if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  778 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                   }
/*      */                   
/*  781 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  782 */                   out.write(10);
/*  783 */                   out.write(9);
/*  784 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  785 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  789 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  790 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */               }
/*      */               
/*  793 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  794 */               out.write(10);
/*  795 */               out.write(9);
/*      */               
/*  797 */               OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  798 */               _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  799 */               _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*  800 */               int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  801 */               if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                 for (;;) {
/*  803 */                   out.write(10);
/*      */                   
/*  805 */                   String redirectTo = null;
/*  806 */                   if (request.getParameter("redirectto") != null)
/*      */                   {
/*  808 */                     redirectTo = java.net.URLEncoder.encode(request.getParameter("redirectto"));
/*      */                   }
/*      */                   else
/*      */                   {
/*  812 */                     redirectTo = java.net.URLEncoder.encode("/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true");
/*      */                   }
/*      */                   
/*  815 */                   out.write("\t\n\t<option value=\"/showTile.do?TileName=.EmailActions&PRINTER_FRIENDLY=true&haid=");
/*  816 */                   out.print(action_haid);
/*  817 */                   out.write("&global=true");
/*  818 */                   out.print(returnpath);
/*  819 */                   out.write(34);
/*  820 */                   out.write(62);
/*  821 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  822 */                   out.write("</option>\n\t<option value=\"/showTile.do?TileName=.SMSActions&PRINTER_FRIENDLY=true&haid=");
/*  823 */                   out.print(action_haid);
/*  824 */                   out.write("&global=true");
/*  825 */                   out.print(returnpath);
/*  826 */                   out.write(34);
/*  827 */                   out.write(62);
/*  828 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  829 */                   out.write("</option>\n\t");
/*      */                   
/*  831 */                   PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  832 */                   _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/*  833 */                   _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                   
/*  835 */                   _jspx_th_logic_005fpresent_005f2.setRole("ADMIN,ENTERPRISEADMIN");
/*  836 */                   int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/*  837 */                   if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                     for (;;) {
/*  839 */                       out.write(32);
/*  840 */                       out.write("\n\t<option value=\"/jsp/ExecProgramActionForm.jsp?haid=");
/*  841 */                       out.print(action_haid);
/*  842 */                       out.write("&global=true");
/*  843 */                       out.print(returnpath);
/*  844 */                       out.write(34);
/*  845 */                       out.write(62);
/*  846 */                       out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  847 */                       out.write("</option>\n\t");
/*  848 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/*  849 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  853 */                   if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/*  854 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                   }
/*      */                   
/*  857 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/*  858 */                   out.write("\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=11&haid=");
/*  859 */                   out.print(action_haid);
/*  860 */                   out.write("&global=true");
/*  861 */                   out.print(returnpath);
/*  862 */                   out.write(34);
/*  863 */                   out.write(62);
/*  864 */                   out.print(FormatUtil.getString("am.webclient.common.sendv1trap.text"));
/*  865 */                   out.write("</option>\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=12&haid=");
/*  866 */                   out.print(action_haid);
/*  867 */                   out.write("&global=true");
/*  868 */                   out.print(returnpath);
/*  869 */                   out.write(34);
/*  870 */                   out.write(62);
/*  871 */                   out.print(FormatUtil.getString("am.webclient.common.sendv2ctrap.text"));
/*  872 */                   out.write("</option>\n\t");
/*  873 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))) {
/*  874 */                     out.write(32);
/*  875 */                     out.write("\n\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&popup=true&global=true&haid=");
/*  876 */                     out.print(action_haid);
/*  877 */                     out.print(returnpath);
/*  878 */                     out.write(34);
/*  879 */                     out.write(62);
/*  880 */                     out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  881 */                     out.write("</option>\n\t");
/*      */                   }
/*  883 */                   out.write(10);
/*  884 */                   out.write(9);
/*  885 */                   out.write(9);
/*  886 */                   out.write(10);
/*  887 */                   out.write(9);
/*  888 */                   if ((!com.adventnet.appmanager.util.Constants.isIt360) || (com.adventnet.appmanager.util.EnterpriseUtil.isProfEdition()) || (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */                   {
/*  890 */                     out.write("<option value=\"/adminAction.do?method=showLogTicket&global=true&haid=");
/*  891 */                     out.print(action_haid);
/*  892 */                     out.write("&redirectTo=");
/*  893 */                     out.print(redirectTo);
/*  894 */                     out.write(34);
/*  895 */                     out.write(62);
/*  896 */                     out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  897 */                     out.write("</option> ");
/*      */                   }
/*      */                   
/*  900 */                   out.write("\n\t\n\t");
/*  901 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))) {
/*  902 */                     out.write(" \n\t<!--JRE Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  903 */                     out.print(action_haid);
/*  904 */                     out.write("&global=true");
/*  905 */                     out.print(returnpath);
/*  906 */                     out.write("&ext=true\">");
/*  907 */                     out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  908 */                     out.write("</option>\n\t<!--VM Action -->\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&haid=");
/*  909 */                     out.print(action_haid);
/*  910 */                     out.print(returnpath);
/*  911 */                     out.write("&ext=true&global=true\">");
/*  912 */                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  913 */                     out.write("</option>\n\t<!--Amazon Instance Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  914 */                     out.print(action_haid);
/*  915 */                     out.write("&global=true");
/*  916 */                     out.print(returnpath);
/*  917 */                     out.write("&ext=true\">");
/*  918 */                     out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  919 */                     out.write("</option>\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&isContainerAction=true&haid=");
/*  920 */                     out.print(action_haid);
/*  921 */                     out.print(returnpath);
/*  922 */                     out.write("&ext=true&global=true\">");
/*  923 */                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  924 */                     out.write("</option>\n\t ");
/*  925 */                   } else if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  926 */                     out.write(32);
/*  927 */                     out.write("\n\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  928 */                     out.print(action_haid);
/*  929 */                     out.write("&global=true");
/*  930 */                     out.print(returnpath);
/*  931 */                     out.write(34);
/*  932 */                     out.write(62);
/*  933 */                     out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  934 */                     out.write("</option>\n\t");
/*      */                   }
/*  936 */                   out.write(10);
/*  937 */                   out.write(9);
/*  938 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())) && (!"CLOUD".equalsIgnoreCase(com.adventnet.appmanager.util.Constants.getCategorytype()))) {
/*  939 */                     out.write("\n\t<!-- Windows Service action -->\n\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  940 */                     out.print(action_haid);
/*  941 */                     out.print(returnpath);
/*  942 */                     out.write(34);
/*  943 */                     out.write(62);
/*  944 */                     out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  945 */                     out.write("</option>\t\n\t");
/*      */                   }
/*  947 */                   out.write(10);
/*  948 */                   out.write(9);
/*  949 */                   out.write(32);
/*  950 */                   if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  951 */                     out.write("\n\t<!-- Sql job action -->\n\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  952 */                     out.print(action_haid);
/*  953 */                     out.print(returnpath);
/*  954 */                     out.write(34);
/*  955 */                     out.write(62);
/*  956 */                     out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  957 */                     out.write("</option>\n\t");
/*      */                   }
/*  959 */                   out.write(10);
/*  960 */                   out.write(9);
/*  961 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  962 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  966 */               if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  967 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */               }
/*      */               
/*  970 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  971 */               out.write(10);
/*  972 */               out.write(9);
/*  973 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  974 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  978 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  979 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */           }
/*      */           
/*  982 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  983 */           out.write("\n\t</select>\n    </td>\n  </tr>\n</table>\n\n");
/*      */           
/*  985 */           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  986 */           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  987 */           _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  989 */           _jspx_th_c_005fif_005f5.setTest("${param.global=='true'}");
/*  990 */           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  991 */           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */             for (;;) {
/*  993 */               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td valign=\"top\"> \n<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/*  994 */               out.write("<!--$Id$-->\n\n\n\n");
/*  995 */               if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                 return;
/*  997 */               out.write("\n      \n\n");
/*      */               
/*  999 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1000 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1001 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f5);
/*      */               
/* 1003 */               _jspx_th_c_005fif_005f6.setTest("${(uri=='/jsp/CreateApplication.jsp')|| uri=='/admin/createapplication.do'||uri=='/admin/createapplication.do' ||(!empty param.wiz && !empty param.haid && (empty invalidhaid))||(param.method=='insert')}");
/* 1004 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1005 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/* 1007 */                   out.write("\n\t  <tr>\n\t  <td class=\"tdindent\">\n");
/* 1008 */                   if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1010 */                   out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">  \n  <tr> \n    <td width=\"2%\">");
/*      */                   
/* 1012 */                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1013 */                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 1014 */                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1016 */                   _jspx_th_c_005fif_005f7.setTest("${uri=='/jsp/CreateApplication.jsp' || uri=='/admin/createapplicationwiz.do'||uri=='/admin/createapplication.do'}");
/* 1017 */                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 1018 */                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                     for (;;) {
/* 1020 */                       out.write("\n    \t");
/*      */                       
/* 1022 */                       SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1023 */                       _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 1024 */                       _jspx_th_c_005fset_005f6.setParent(_jspx_th_c_005fif_005f7);
/*      */                       
/* 1026 */                       _jspx_th_c_005fset_005f6.setVar("wizimage");
/* 1027 */                       int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 1028 */                       if (_jspx_eval_c_005fset_005f6 != 0) {
/* 1029 */                         if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1030 */                           out = _jspx_page_context.pushBody();
/* 1031 */                           _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 1032 */                           _jspx_th_c_005fset_005f6.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1035 */                           out.write(" \n    \t<img src=\"/images/wiz_newbizapp_high.gif\" border=\"0\" align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1036 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1037 */                           out.write(" </b></font>\n    \t");
/* 1038 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 1039 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1042 */                         if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1043 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1046 */                       if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 1047 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6); return;
/*      */                       }
/*      */                       
/* 1050 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 1051 */                       out.write("\n    ");
/* 1052 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1053 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1057 */                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1058 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                   }
/*      */                   
/* 1061 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1062 */                   out.write("\n    ");
/*      */                   
/* 1064 */                   IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1065 */                   _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 1066 */                   _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1068 */                   _jspx_th_c_005fif_005f8.setTest("${uri!='/jsp/CreateApplication.jsp' && uri!='/admin/createapplicationwiz.do'&& uri!='/admin/createapplication.do'}");
/* 1069 */                   int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 1070 */                   if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                     for (;;) {
/* 1072 */                       out.write("\n    \t");
/*      */                       
/* 1074 */                       SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1075 */                       _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 1076 */                       _jspx_th_c_005fset_005f7.setParent(_jspx_th_c_005fif_005f8);
/*      */                       
/* 1078 */                       _jspx_th_c_005fset_005f7.setVar("wizimage");
/* 1079 */                       int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 1080 */                       if (_jspx_eval_c_005fset_005f7 != 0) {
/* 1081 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1082 */                           out = _jspx_page_context.pushBody();
/* 1083 */                           _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 1084 */                           _jspx_th_c_005fset_005f7.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1087 */                           out.write("\n    \t<img src=\"/images/wiz_newbizapp_nor.gif\" border=0> <font family=\"verdana\" size=\"2\" color=\"#818181\">");
/* 1088 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1089 */                           out.write("</font>  \t");
/* 1090 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 1091 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1094 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1095 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1098 */                       if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 1099 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                       }
/*      */                       
/* 1102 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 1103 */                       out.write("\n    ");
/* 1104 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 1105 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1109 */                   if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 1110 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                   }
/*      */                   
/* 1113 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 1114 */                   out.write("      \n\t</td>\n    <td width=\"20%\">");
/* 1115 */                   if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1117 */                   out.write("</td>  \n   \n");
/*      */                   
/* 1119 */                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1120 */                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1121 */                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f6);
/* 1122 */                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1123 */                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                     for (;;) {
/* 1125 */                       out.write("\n    ");
/*      */                       
/* 1127 */                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1128 */                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1129 */                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                       
/* 1131 */                       _jspx_th_c_005fwhen_005f3.setTest("${( param.method=='configureHostDiscovery' || param.method=='associateMonitors'||param.method=='getMonitorForm'||param.method=='addResource')&& (empty showwiz3) && (empty UrlForm) }");
/* 1132 */                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1133 */                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                         for (;;) {
/* 1135 */                           out.write("\n    \n\t\n\t\t\n\t\t");
/*      */                           
/* 1137 */                           SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1138 */                           _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 1139 */                           _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                           
/* 1141 */                           _jspx_th_c_005fset_005f8.setVar("wizimage");
/* 1142 */                           int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 1143 */                           if (_jspx_eval_c_005fset_005f8 != 0) {
/* 1144 */                             if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1145 */                               out = _jspx_page_context.pushBody();
/* 1146 */                               _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 1147 */                               _jspx_th_c_005fset_005f8.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1150 */                               out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1151 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1152 */                               out.write(" </b></font>\n    \t");
/* 1153 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 1154 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1157 */                             if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1158 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1161 */                           if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 1162 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                           }
/*      */                           
/* 1165 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 1166 */                           out.write("\n   ");
/* 1167 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1168 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1172 */                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1173 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                       }
/*      */                       
/* 1176 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1177 */                       out.write("\n   ");
/*      */                       
/* 1179 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1180 */                       _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1181 */                       _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 1182 */                       int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1183 */                       if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                         for (;;) {
/* 1185 */                           out.write("  \n    \t\n\t\t");
/*      */                           
/* 1187 */                           SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1188 */                           _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 1189 */                           _jspx_th_c_005fset_005f9.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                           
/* 1191 */                           _jspx_th_c_005fset_005f9.setVar("wizimage");
/* 1192 */                           int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 1193 */                           if (_jspx_eval_c_005fset_005f9 != 0) {
/* 1194 */                             if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1195 */                               out = _jspx_page_context.pushBody();
/* 1196 */                               _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 1197 */                               _jspx_th_c_005fset_005f9.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1200 */                               out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1201 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1202 */                               out.write(" </font>\n    \t");
/* 1203 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 1204 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1207 */                             if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1208 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1211 */                           if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 1212 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                           }
/*      */                           
/* 1215 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 1216 */                           out.write("\n\t\n\t\t\n   ");
/* 1217 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1218 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1222 */                       if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1223 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                       }
/*      */                       
/* 1226 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1227 */                       out.write(10);
/* 1228 */                       out.write(32);
/* 1229 */                       out.write(32);
/* 1230 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1231 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1235 */                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1236 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                   }
/*      */                   
/* 1239 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1240 */                   out.write(" \n\n    \n     <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"20%\">\n    ");
/* 1241 */                   if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1243 */                   out.write("\n    \t");
/* 1244 */                   if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1246 */                   out.write("\n    \t\n    \t");
/* 1247 */                   if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1249 */                   out.write("\n    \t</td>    \t\n    \t<!-- ############################################# check for third tab #####################################  -->\n       ");
/*      */                   
/* 1251 */                   ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1252 */                   _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1253 */                   _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f6);
/* 1254 */                   int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1255 */                   if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                     for (;;) {
/* 1257 */                       out.write("\n       ");
/*      */                       
/* 1259 */                       WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1260 */                       _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1261 */                       _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                       
/* 1263 */                       _jspx_th_c_005fwhen_005f4.setTest("${(param.method=='reloadHostDiscoveryForm'|| param.method=='getMonitorForm'||param.method=='addResource'|| (!empty showwiz3) || (!empty UrlForm) ) && (param.method!='getHAProfiles') }");
/* 1264 */                       int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1265 */                       if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                         for (;;) {
/* 1267 */                           out.write("\n   \n   \t    \t");
/*      */                           
/* 1269 */                           SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1270 */                           _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 1271 */                           _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                           
/* 1273 */                           _jspx_th_c_005fset_005f10.setVar("wizimage");
/* 1274 */                           int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 1275 */                           if (_jspx_eval_c_005fset_005f10 != 0) {
/* 1276 */                             if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1277 */                               out = _jspx_page_context.pushBody();
/* 1278 */                               _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 1279 */                               _jspx_th_c_005fset_005f10.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1282 */                               out.write("\n   \t    \t<img src=\"/images/new_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b> ");
/* 1283 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1284 */                               out.write(" </b></font>\n   \t    \t");
/* 1285 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 1286 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1289 */                             if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1290 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1293 */                           if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 1294 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                           }
/*      */                           
/* 1297 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 1298 */                           out.write("\n       ");
/* 1299 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1300 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1304 */                       if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1305 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                       }
/*      */                       
/* 1308 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1309 */                       out.write("\n        ");
/*      */                       
/* 1311 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1312 */                       _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1313 */                       _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1314 */                       int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1315 */                       if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                         for (;;) {
/* 1317 */                           out.write("  \n   \t    \t");
/*      */                           
/* 1319 */                           SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1320 */                           _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 1321 */                           _jspx_th_c_005fset_005f11.setParent(_jspx_th_c_005fotherwise_005f4);
/*      */                           
/* 1323 */                           _jspx_th_c_005fset_005f11.setVar("wizimage");
/* 1324 */                           int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 1325 */                           if (_jspx_eval_c_005fset_005f11 != 0) {
/* 1326 */                             if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1327 */                               out = _jspx_page_context.pushBody();
/* 1328 */                               _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 1329 */                               _jspx_th_c_005fset_005f11.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1332 */                               out.write("\n\t\t   \t    \t<img src=\"/images/new_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1333 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1334 */                               out.write(" </font>\n   \t    \t");
/* 1335 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 1336 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1339 */                             if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1340 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1343 */                           if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 1344 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11); return;
/*      */                           }
/*      */                           
/* 1347 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 1348 */                           out.write("\n   \t");
/* 1349 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1350 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1354 */                       if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1355 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                       }
/*      */                       
/* 1358 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1359 */                       out.write(10);
/* 1360 */                       out.write(32);
/* 1361 */                       out.write(32);
/* 1362 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1363 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1367 */                   if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1368 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                   }
/*      */                   
/* 1371 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1372 */                   out.write(" \n\n        <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n       <td width=\"18%\">\n       ");
/* 1373 */                   if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1375 */                   out.write("\n       ");
/* 1376 */                   if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1378 */                   out.write("\n       ");
/* 1379 */                   out.write("\n       \t");
/* 1380 */                   if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1382 */                   out.write("\n    \t</td>\n   \n  <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"17%\">\n\t");
/*      */                   
/* 1384 */                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1385 */                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 1386 */                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1388 */                   _jspx_th_c_005fif_005f13.setTest("${param.method=='getHAProfiles'}");
/* 1389 */                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 1390 */                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                     for (;;) {
/* 1392 */                       out.write(10);
/* 1393 */                       out.write(9);
/*      */                       
/* 1395 */                       SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1396 */                       _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 1397 */                       _jspx_th_c_005fset_005f12.setParent(_jspx_th_c_005fif_005f13);
/*      */                       
/* 1399 */                       _jspx_th_c_005fset_005f12.setVar("wizimage");
/* 1400 */                       int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 1401 */                       if (_jspx_eval_c_005fset_005f12 != 0) {
/* 1402 */                         if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1403 */                           out = _jspx_page_context.pushBody();
/* 1404 */                           _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 1405 */                           _jspx_th_c_005fset_005f12.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1408 */                           out.write("\n\t\t<img src=\"/images/wiz_configurealerts_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b>");
/* 1409 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1410 */                           out.write(" </b></font>\n    \t");
/* 1411 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 1412 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1415 */                         if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1416 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1419 */                       if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 1420 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12); return;
/*      */                       }
/*      */                       
/* 1423 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 1424 */                       out.write(10);
/* 1425 */                       out.write(9);
/* 1426 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 1427 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1431 */                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 1432 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                   }
/*      */                   
/* 1435 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1436 */                   out.write(10);
/* 1437 */                   out.write(9);
/*      */                   
/* 1439 */                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1440 */                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 1441 */                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1443 */                   _jspx_th_c_005fif_005f14.setTest("${param.method!='getHAProfiles'}");
/* 1444 */                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 1445 */                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                     for (;;) {
/* 1447 */                       out.write(10);
/* 1448 */                       out.write(9);
/*      */                       
/* 1450 */                       SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1451 */                       _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 1452 */                       _jspx_th_c_005fset_005f13.setParent(_jspx_th_c_005fif_005f14);
/*      */                       
/* 1454 */                       _jspx_th_c_005fset_005f13.setVar("wizimage");
/* 1455 */                       int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 1456 */                       if (_jspx_eval_c_005fset_005f13 != 0) {
/* 1457 */                         if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1458 */                           out = _jspx_page_context.pushBody();
/* 1459 */                           _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 1460 */                           _jspx_th_c_005fset_005f13.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1463 */                           out.write("\n\t\t<img src=\"/images/wiz_configurealerts_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1464 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1465 */                           out.write(" </font>\n    \t");
/* 1466 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 1467 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1470 */                         if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1471 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1474 */                       if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 1475 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13); return;
/*      */                       }
/*      */                       
/* 1478 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 1479 */                       out.write("\n\t\n\t");
/* 1480 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 1481 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1485 */                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 1486 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                   }
/*      */                   
/* 1489 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 1490 */                   out.write(10);
/* 1491 */                   if (_jspx_meth_c_005fif_005f15(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1493 */                   out.write("   \n ");
/* 1494 */                   if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1496 */                   out.write(10);
/* 1497 */                   out.write(32);
/* 1498 */                   out.write(10);
/* 1499 */                   if (_jspx_meth_c_005fif_005f16(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1501 */                   out.write("   \n    </td>\n    \n    <td width=\"2%\" rowspan=\"2\" valign=\"bottom\" align=\"right\"><img src=\"/images/wiz_endicon.gif\" width=\"33\" height=\"36\"></td>\n  </tr>\n  <tr background=\"/images/wiz_bg_graylind.gif\"> \n    <td><img src=\"/images/wiz_startimage.gif\" width=\"18\" height=\"16\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n   \n   <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n \n");
/* 1502 */                   out.write("  </tr>\n\n</table>\n</td></tr>\n");
/* 1503 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1504 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1508 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1509 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/* 1512 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1513 */               out.write(10);
/* 1514 */               out.write(10);
/* 1515 */               if (request.getAttribute("EmailForm") == null) {
/* 1516 */                 out.write(10);
/*      */                 
/* 1518 */                 MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1519 */                 _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 1520 */                 _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                 
/* 1522 */                 _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                 
/* 1524 */                 _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 1525 */                 int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 1526 */                 if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 1527 */                   String msg = null;
/* 1528 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1529 */                     out = _jspx_page_context.pushBody();
/* 1530 */                     _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 1531 */                     _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                   }
/* 1533 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/* 1535 */                     out.write(" \n<tr> \n  <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 1536 */                     if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                       return;
/* 1538 */                     out.write("</td>\n\t  </tr>\n\t</table>\n\t<br></td>\n</tr>\n");
/* 1539 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 1540 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/* 1541 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1544 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1545 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1548 */                 if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 1549 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                 }
/*      */                 
/* 1552 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */               }
/* 1554 */               out.write(32);
/*      */               
/* 1556 */               MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 1557 */               _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 1558 */               _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */               
/* 1560 */               _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 1561 */               int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 1562 */               if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 1564 */                   out.write(" \n<tr> \n  <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\"> ");
/*      */                   
/* 1566 */                   MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1567 */                   _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 1568 */                   _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                   
/* 1570 */                   _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                   
/* 1572 */                   _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 1573 */                   int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 1574 */                   if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 1575 */                     String msg = null;
/* 1576 */                     if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1577 */                       out = _jspx_page_context.pushBody();
/* 1578 */                       _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 1579 */                       _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                     }
/* 1581 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/* 1583 */                       out.write("\n\t  ");
/* 1584 */                       if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                         return;
/* 1586 */                       out.write("<br>\n\t  ");
/* 1587 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 1588 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/* 1589 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1592 */                     if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1593 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1596 */                   if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 1597 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                   }
/*      */                   
/* 1600 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 1601 */                   out.write(" </td>\n\t  </tr>\n\t</table></td>\n</tr>\n");
/* 1602 */                   int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 1603 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1607 */               if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 1608 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */               }
/*      */               
/* 1611 */               this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 1612 */               out.write("\n</table>\n<script>\n\tobject=location.href;\n\t\n\tif(document.AMActionForm!=null)\n\t{\t  \n\t  if(object.match(\"EMailActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if(object.match(\"SMSActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if(object.match(\"ExecProgramActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=11\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=12\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\t  \n\t  else if(object.match(\"showLogTicket\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=6;\n\t  }\n\t  else if(object.match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=5;\n\t  }\t\n\t  else if(object.match(\"jre\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=7;\n");
/* 1613 */               out.write("\t  }\n\t  else if(object.match(\"showVMAction\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=8;\n\t  }\n\t  else if(object.match(\"amazon\")!=null)\n\t  {\n\t\t    document.AMActionForm.actions.selectedIndex=9;\n\t }\t  \n\t}\n\telse if(document.MBeanOperationActionForm!=null)\n\t{\n\t  document.MBeanOperationActionForm.actions.selectedIndex=5;\t  \n\t}\n</script>\n</td>\n</tr>\n</table>\n");
/* 1614 */               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1615 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1619 */           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1620 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */           }
/*      */           
/* 1623 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1624 */           out.write(10);
/* 1625 */           out.write(10);
/*      */           
/* 1627 */           IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1628 */           _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 1629 */           _jspx_th_c_005fif_005f17.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1631 */           _jspx_th_c_005fif_005f17.setTest("${!empty param.returnpath}");
/* 1632 */           int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 1633 */           if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */             for (;;) {
/* 1635 */               out.write("\n<input name=\"returnpath\" type=\"hidden\" value=\"");
/* 1636 */               out.print(request.getParameter("returnpath"));
/* 1637 */               out.write(34);
/* 1638 */               out.write(62);
/* 1639 */               out.write(10);
/* 1640 */               int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 1641 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1645 */           if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 1646 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */           }
/*      */           
/* 1649 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 1650 */           out.write(10);
/*      */           
/* 1652 */           IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1653 */           _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 1654 */           _jspx_th_c_005fif_005f18.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1656 */           _jspx_th_c_005fif_005f18.setTest("${!empty param.haid}");
/* 1657 */           int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 1658 */           if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */             for (;;) {
/* 1660 */               out.write("\n<input name=\"haid\" type=\"hidden\" value=\"");
/* 1661 */               out.print(request.getParameter("haid"));
/* 1662 */               out.write(34);
/* 1663 */               out.write(62);
/* 1664 */               out.write(10);
/* 1665 */               int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 1666 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1670 */           if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 1671 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */           }
/*      */           
/* 1674 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 1675 */           out.write(10);
/* 1676 */           if (_jspx_meth_logic_005fequal_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1678 */           out.write(10);
/*      */           
/* 1680 */           NotEqualTag _jspx_th_logic_005fnotEqual_005f0 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(NotEqualTag.class);
/* 1681 */           _jspx_th_logic_005fnotEqual_005f0.setPageContext(_jspx_page_context);
/* 1682 */           _jspx_th_logic_005fnotEqual_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1684 */           _jspx_th_logic_005fnotEqual_005f0.setName("AMActionForm");
/*      */           
/* 1686 */           _jspx_th_logic_005fnotEqual_005f0.setProperty("method");
/*      */           
/* 1688 */           _jspx_th_logic_005fnotEqual_005f0.setValue("editExecProgAction");
/* 1689 */           int _jspx_eval_logic_005fnotEqual_005f0 = _jspx_th_logic_005fnotEqual_005f0.doStartTag();
/* 1690 */           if (_jspx_eval_logic_005fnotEqual_005f0 != 0) {
/*      */             for (;;) {
/* 1692 */               out.write("\n<input name=\"method\" type=\"hidden\" value=\"createExecProgAction\">\n<input name=\"redirectTo\" type=\"hidden\" value=\"");
/* 1693 */               out.print(request.getParameter("redirectTo"));
/* 1694 */               out.write(34);
/* 1695 */               out.write(62);
/* 1696 */               out.write(10);
/*      */               
/* 1698 */               if (isInvokedFromPopup)
/*      */               {
/*      */ 
/* 1701 */                 out.write("\n\t<input name=\"popup\" type=\"hidden\" value=\"true\">\n\t<input name=\"resourceid\" type=\"hidden\" value=\"");
/* 1702 */                 out.print(request.getParameter("resourceid"));
/* 1703 */                 out.write("\">\n\t<input name=\"attributeid\" type=\"hidden\" value=\"");
/* 1704 */                 out.print(request.getParameter("attributeid"));
/* 1705 */                 out.write("\">\n\t<input name=\"severity\" type=\"hidden\" value=\"");
/* 1706 */                 out.print(request.getParameter("severity"));
/* 1707 */                 out.write("\">\n\t");
/*      */                 
/* 1709 */                 String wiz = request.getParameter("wiz");
/* 1710 */                 if (wiz != null)
/*      */                 {
/*      */ 
/* 1713 */                   out.write("\n\t\t<input name=\"wiz\"type=\"hidden\" value=\"");
/* 1714 */                   out.print(wiz);
/* 1715 */                   out.write("\">\n\t");
/*      */                 }
/*      */                 
/*      */ 
/* 1719 */                 out.write(10);
/*      */                 
/* 1721 */                 MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 1722 */                 _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/* 1723 */                 _jspx_th_logic_005fmessagesPresent_005f1.setParent(_jspx_th_logic_005fnotEqual_005f0);
/*      */                 
/* 1725 */                 _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/* 1726 */                 int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/* 1727 */                 if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*      */                   for (;;) {
/* 1729 */                     out.write("\n          <table width=\"99%\" border=\"0\" cellspacing=\"3\" cellpadding=\"3\" class=\"messagebox\">\n              <tr>\n                <td width=\"95%\" class=\"message\"> ");
/*      */                     
/* 1731 */                     MessagesTag _jspx_th_html_005fmessages_005f2 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1732 */                     _jspx_th_html_005fmessages_005f2.setPageContext(_jspx_page_context);
/* 1733 */                     _jspx_th_html_005fmessages_005f2.setParent(_jspx_th_logic_005fmessagesPresent_005f1);
/*      */                     
/* 1735 */                     _jspx_th_html_005fmessages_005f2.setId("msg");
/*      */                     
/* 1737 */                     _jspx_th_html_005fmessages_005f2.setMessage("true");
/* 1738 */                     int _jspx_eval_html_005fmessages_005f2 = _jspx_th_html_005fmessages_005f2.doStartTag();
/* 1739 */                     if (_jspx_eval_html_005fmessages_005f2 != 0) {
/* 1740 */                       String msg = null;
/* 1741 */                       if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1742 */                         out = _jspx_page_context.pushBody();
/* 1743 */                         _jspx_th_html_005fmessages_005f2.setBodyContent((BodyContent)out);
/* 1744 */                         _jspx_th_html_005fmessages_005f2.doInitBody();
/*      */                       }
/* 1746 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                       for (;;) {
/* 1748 */                         out.write("\n                  <li>");
/* 1749 */                         if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                           return;
/* 1751 */                         out.write("</li>\n                  ");
/* 1752 */                         int evalDoAfterBody = _jspx_th_html_005fmessages_005f2.doAfterBody();
/* 1753 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/* 1754 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 1757 */                       if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1758 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 1761 */                     if (_jspx_th_html_005fmessages_005f2.doEndTag() == 5) {
/* 1762 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2); return;
/*      */                     }
/*      */                     
/* 1765 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2);
/* 1766 */                     out.write(" </td>\n              </tr>\n            </table>\n            <br>\n");
/* 1767 */                     int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/* 1768 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1772 */                 if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/* 1773 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1); return;
/*      */                 }
/*      */                 
/* 1776 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/* 1777 */                 out.write(10);
/* 1778 */                 out.write(9);
/*      */               }
/*      */               
/*      */ 
/* 1782 */               out.write(10);
/* 1783 */               int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f0.doAfterBody();
/* 1784 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1788 */           if (_jspx_th_logic_005fnotEqual_005f0.doEndTag() == 5) {
/* 1789 */             this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0); return;
/*      */           }
/*      */           
/* 1792 */           this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/* 1793 */           out.write(10);
/* 1794 */           out.write(10);
/* 1795 */           if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1797 */           out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"70%\" valign=\"top\">\t\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtborder\">\n  <tr>\n<td width=\"2%\" class=\"tableheading-monitor-config \"><img src=\"/images/exe.gif\" class=\"tableheading-add-icon\"></td>\n\n    <td width=\"72%\" height=\"31\" class=\"tableheading-monitor-config\" >");
/*      */           
/* 1799 */           EqualTag _jspx_th_logic_005fequal_005f1 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 1800 */           _jspx_th_logic_005fequal_005f1.setPageContext(_jspx_page_context);
/* 1801 */           _jspx_th_logic_005fequal_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1803 */           _jspx_th_logic_005fequal_005f1.setName("AMActionForm");
/*      */           
/* 1805 */           _jspx_th_logic_005fequal_005f1.setProperty("method");
/*      */           
/* 1807 */           _jspx_th_logic_005fequal_005f1.setValue("editExecProgAction");
/* 1808 */           int _jspx_eval_logic_005fequal_005f1 = _jspx_th_logic_005fequal_005f1.doStartTag();
/* 1809 */           if (_jspx_eval_logic_005fequal_005f1 != 0) {
/*      */             for (;;) {
/* 1811 */               out.write("\n      ");
/* 1812 */               out.print(FormatUtil.getString("am.webclient.newaction.editactiontextprogram"));
/* 1813 */               int evalDoAfterBody = _jspx_th_logic_005fequal_005f1.doAfterBody();
/* 1814 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1818 */           if (_jspx_th_logic_005fequal_005f1.doEndTag() == 5) {
/* 1819 */             this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1); return;
/*      */           }
/*      */           
/* 1822 */           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 1823 */           out.write("\n      ");
/*      */           
/* 1825 */           NotEqualTag _jspx_th_logic_005fnotEqual_005f1 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(NotEqualTag.class);
/* 1826 */           _jspx_th_logic_005fnotEqual_005f1.setPageContext(_jspx_page_context);
/* 1827 */           _jspx_th_logic_005fnotEqual_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1829 */           _jspx_th_logic_005fnotEqual_005f1.setName("AMActionForm");
/*      */           
/* 1831 */           _jspx_th_logic_005fnotEqual_005f1.setProperty("method");
/*      */           
/* 1833 */           _jspx_th_logic_005fnotEqual_005f1.setValue("editExecProgAction");
/* 1834 */           int _jspx_eval_logic_005fnotEqual_005f1 = _jspx_th_logic_005fnotEqual_005f1.doStartTag();
/* 1835 */           if (_jspx_eval_logic_005fnotEqual_005f1 != 0) {
/*      */             for (;;) {
/* 1837 */               out.print(FormatUtil.getString("am.webclient.newaction.createactiontextprogram"));
/* 1838 */               int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f1.doAfterBody();
/* 1839 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1843 */           if (_jspx_th_logic_005fnotEqual_005f1.doEndTag() == 5) {
/* 1844 */             this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f1); return;
/*      */           }
/*      */           
/* 1847 */           this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f1);
/* 1848 */           out.write("\n    </td>\n        </tr>\n      </table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n    <td width=\"25%\" class=\"bodytext label-align\">");
/* 1849 */           out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/* 1850 */           out.write("<span class=\"mandatory\">*</span></td>\n    <td class=\"bodytext\" colspan=\"3\">");
/* 1851 */           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1853 */           out.write("</td>\n     \n  </tr>\n  <tr>\n  ");
/* 1854 */           if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1855 */             out.write("\n    <td class=\"bodytext label-align\" width=\"25%\">");
/* 1856 */             out.print(FormatUtil.getString("Script Location"));
/* 1857 */             out.write("</td>\n    <td class=\"bodytext\" valign=\"middle\" colspan=\"3\">\n\n    ");
/* 1858 */             if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.localscript.remove"))
/*      */             {
/* 1860 */               out.write("\n        ");
/* 1861 */               if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/* 1863 */               out.write("&nbsp;");
/* 1864 */               out.print(FormatUtil.getString("am.webclient.script.localserver"));
/* 1865 */               out.write("\n      &nbsp;&nbsp;\n      ");
/*      */             }
/* 1867 */             out.write("\n        ");
/* 1868 */             if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1870 */             out.write("&nbsp;");
/* 1871 */             out.print(FormatUtil.getString("am.webclient.script.remoteserver"));
/* 1872 */             out.write("\n    ");
/*      */           } else {
/* 1874 */             out.write("\n\t        <input type=\"hidden\" name=\"serversite\" value=\"local\" onclick=\"javascript:manageremotescript()\" />\n            <input type=\"hidden\" name=\"serversite\" value=\"remote\" onclick=\"javascript:manageremotescript()\"/>\n\n\t ");
/*      */           }
/* 1876 */           out.write("\n\t</td>\n  </tr>\n  ");
/*      */           
/* 1878 */           int colspan1 = 3;
/* 1879 */           String width1 = "15%";
/* 1880 */           String width2 = "25%";
/* 1881 */           if ((request.getAttribute("remoteactionedit") != null) && (request.getAttribute("remoteactionedit").equals("true")))
/*      */           {
/* 1883 */             colspan1 = 2;
/* 1884 */             width1 = "23%";
/* 1885 */             width2 = "25%";
/*      */           }
/*      */           else
/*      */           {
/* 1889 */             colspan1 = 3;
/* 1890 */             width1 = "6%";
/* 1891 */             width2 = "15%";
/*      */           }
/*      */           
/* 1894 */           out.write("\n  <tr height=\"1\">\n    <td colspan=\"4\" class=\"cellpadd-none\"> <div id=\"remotescript\" style=\"display:block;\">\n        <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" >\n          <tr>\n            <td  class=\"bodytext label-align\" width=\"25%\"> ");
/* 1895 */           out.print(FormatUtil.getString("am.webclient.script.choosehost"));
/* 1896 */           out.write(" </td>\n            <td  colspan=");
/* 1897 */           out.print(colspan1);
/* 1898 */           out.write(" class=\"select-host\"> ");
/*      */           
/* 1900 */           SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 1901 */           _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 1902 */           _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1904 */           _jspx_th_html_005fselect_005f0.setProperty("choosehost");
/*      */           
/* 1906 */           _jspx_th_html_005fselect_005f0.setOnchange("javascript:managenewHost()");
/*      */           
/* 1908 */           _jspx_th_html_005fselect_005f0.setStyleClass("formtext normal");
/* 1909 */           int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 1910 */           if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 1911 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1912 */               out = _jspx_page_context.pushBody();
/* 1913 */               _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 1914 */               _jspx_th_html_005fselect_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 1917 */               out.write("\n              ");
/*      */               
/* 1919 */               IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1920 */               _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 1921 */               _jspx_th_c_005fif_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 1923 */               _jspx_th_c_005fif_005f19.setTest("${remoteactionedit !='true'}");
/* 1924 */               int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 1925 */               if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                 for (;;) {
/* 1927 */                   out.write(32);
/*      */                   
/* 1929 */                   OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1930 */                   _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 1931 */                   _jspx_th_html_005foption_005f0.setParent(_jspx_th_c_005fif_005f19);
/*      */                   
/* 1933 */                   _jspx_th_html_005foption_005f0.setValue("-2");
/* 1934 */                   int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 1935 */                   if (_jspx_eval_html_005foption_005f0 != 0) {
/* 1936 */                     if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1937 */                       out = _jspx_page_context.pushBody();
/* 1938 */                       _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 1939 */                       _jspx_th_html_005foption_005f0.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 1942 */                       out.print(FormatUtil.getString("Select Host"));
/* 1943 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 1944 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1947 */                     if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1948 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1951 */                   if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 1952 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                   }
/*      */                   
/* 1955 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 1956 */                   out.write(32);
/*      */                   
/* 1958 */                   OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 1959 */                   _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 1960 */                   _jspx_th_html_005foption_005f1.setParent(_jspx_th_c_005fif_005f19);
/*      */                   
/* 1962 */                   _jspx_th_html_005foption_005f1.setValue("-1");
/*      */                   
/* 1964 */                   _jspx_th_html_005foption_005f1.setStyle("font-weight: bold;");
/* 1965 */                   int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 1966 */                   if (_jspx_eval_html_005foption_005f1 != 0) {
/* 1967 */                     if (_jspx_eval_html_005foption_005f1 != 1) {
/* 1968 */                       out = _jspx_page_context.pushBody();
/* 1969 */                       _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 1970 */                       _jspx_th_html_005foption_005f1.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 1973 */                       out.print(FormatUtil.getString("am.webclient.script.newhost"));
/* 1974 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 1975 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1978 */                     if (_jspx_eval_html_005foption_005f1 != 1) {
/* 1979 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1982 */                   if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 1983 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                   }
/*      */                   
/* 1986 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f1);
/* 1987 */                   out.write(32);
/* 1988 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 1989 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1993 */               if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 1994 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */               }
/*      */               
/* 1997 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 1998 */               out.write("\n              ");
/*      */               
/* 2000 */               String hostquery = "select ID,HOSTNAME,USERNAME from AM_SCRIPTHOSTDETAILS  where MODE in ('TELNET','SSH','SSH_KEY')";
/* 2001 */               com.adventnet.appmanager.db.AMConnectionPool cp = com.adventnet.appmanager.db.AMConnectionPool.getInstance();
/*      */               try
/*      */               {
/* 2004 */                 java.sql.ResultSet rs = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(hostquery);
/* 2005 */                 while (rs.next())
/*      */                 {
/*      */ 
/* 2008 */                   out.write("\n              ");
/*      */                   
/* 2010 */                   OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2011 */                   _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2012 */                   _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                   
/* 2014 */                   _jspx_th_html_005foption_005f2.setValue(rs.getString(1));
/* 2015 */                   int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2016 */                   if (_jspx_eval_html_005foption_005f2 != 0) {
/* 2017 */                     if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2018 */                       out = _jspx_page_context.pushBody();
/* 2019 */                       _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 2020 */                       _jspx_th_html_005foption_005f2.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 2023 */                       out.print(rs.getString(2));
/* 2024 */                       out.write(32);
/* 2025 */                       out.write(40);
/* 2026 */                       out.print(rs.getString(3));
/* 2027 */                       out.write(")\n              ");
/* 2028 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 2029 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 2032 */                     if (_jspx_eval_html_005foption_005f2 != 1) {
/* 2033 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 2036 */                   if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2037 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                   }
/*      */                   
/* 2040 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 2041 */                   out.write("\n              ");
/*      */                 }
/*      */                 
/* 2044 */                 rs.close();
/*      */               }
/*      */               catch (Exception exc) {}
/*      */               
/*      */ 
/*      */ 
/*      */ 
/* 2051 */               out.write("\n              ");
/* 2052 */               int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 2053 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2056 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2057 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2060 */           if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 2061 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */           }
/*      */           
/* 2064 */           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/*      */           
/* 2066 */           IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2067 */           _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 2068 */           _jspx_th_c_005fif_005f20.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2070 */           _jspx_th_c_005fif_005f20.setTest("${remoteactionedit =='true'}");
/* 2071 */           int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 2072 */           if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */             for (;;) {
/* 2074 */               out.write(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n             <a href=\"javascript:Edithostdetails()\" class=\"staticlinks\">");
/* 2075 */               out.print(FormatUtil.getString("am.webclient.newscript.edithostdetails.text"));
/* 2076 */               out.write("</a>\n            ");
/* 2077 */               int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 2078 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2082 */           if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 2083 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */           }
/*      */           
/* 2086 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 2087 */           out.write(" </td>\n            </tr>\n        </table>\n      </div></td>\n  </tr>\n\n  ");
/*      */           
/* 2089 */           IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2090 */           _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 2091 */           _jspx_th_c_005fif_005f21.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2093 */           _jspx_th_c_005fif_005f21.setTest("${remoteactionedit !='true'}");
/* 2094 */           int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 2095 */           if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */             for (;;) {
/* 2097 */               out.write("\n  <tr>\n    <td colspan=\"4\" class=\"cellpadd-none\"><div id=\"newhost\" style=\"display:block;\">\n        <table border=\"0\" width=\"99%\" class=\"dottedline bg-lite align-top\" cellpadding=\"5\" cellspacing=\"0\">\n          <tr>\n            <td height=28 width=\"25%\" class=\"label-align\">");
/* 2098 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.hostname"));
/* 2099 */               out.write("<span class=\"mandatory\">*</span></td>\n            <td height=28 width=\"75%\"> ");
/* 2100 */               if (_jspx_meth_html_005ftext_005f1(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                 return;
/* 2102 */               out.write("&nbsp;&nbsp;<span class=\"footer\">");
/* 2103 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.textmessage.hostexample"));
/* 2104 */               out.write("\n              </span></td>\n          </tr>\n          <tr>\n            <td height=\"28\" width=\"25%\" class=\"bodytext label-align\">");
/* 2105 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.mode"));
/* 2106 */               out.write("<span class=\"mandatory\">*</span></td>\n            <td class=\"bodytext\"> ");
/*      */               
/* 2108 */               SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 2109 */               _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 2110 */               _jspx_th_html_005fselect_005f1.setParent(_jspx_th_c_005fif_005f21);
/*      */               
/* 2112 */               _jspx_th_html_005fselect_005f1.setProperty("monitoringmode");
/*      */               
/* 2114 */               _jspx_th_html_005fselect_005f1.setOnchange("changeport()");
/*      */               
/* 2116 */               _jspx_th_html_005fselect_005f1.setStyleClass("formtext medium");
/* 2117 */               int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 2118 */               if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 2119 */                 if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 2120 */                   out = _jspx_page_context.pushBody();
/* 2121 */                   _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 2122 */                   _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 2125 */                   out.write("\n              ");
/*      */                   
/* 2127 */                   OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2128 */                   _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2129 */                   _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f1);
/*      */                   
/* 2131 */                   _jspx_th_html_005foption_005f3.setValue("TELNET");
/* 2132 */                   int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2133 */                   if (_jspx_eval_html_005foption_005f3 != 0) {
/* 2134 */                     if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2135 */                       out = _jspx_page_context.pushBody();
/* 2136 */                       _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 2137 */                       _jspx_th_html_005foption_005f3.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 2140 */                       out.write(32);
/* 2141 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.telnet"));
/* 2142 */                       out.write("\n              ");
/* 2143 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 2144 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 2147 */                     if (_jspx_eval_html_005foption_005f3 != 1) {
/* 2148 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 2151 */                   if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2152 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                   }
/*      */                   
/* 2155 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 2156 */                   out.write(32);
/*      */                   
/* 2158 */                   OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2159 */                   _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2160 */                   _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f1);
/*      */                   
/* 2162 */                   _jspx_th_html_005foption_005f4.setValue("SSH");
/* 2163 */                   int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2164 */                   if (_jspx_eval_html_005foption_005f4 != 0) {
/* 2165 */                     if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2166 */                       out = _jspx_page_context.pushBody();
/* 2167 */                       _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 2168 */                       _jspx_th_html_005foption_005f4.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 2171 */                       out.write(32);
/* 2172 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh"));
/* 2173 */                       out.write("\n              ");
/* 2174 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 2175 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 2178 */                     if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2179 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 2182 */                   if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2183 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                   }
/*      */                   
/* 2186 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 2187 */                   out.write(32);
/* 2188 */                   int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 2189 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2192 */                 if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 2193 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2196 */               if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 2197 */                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */               }
/*      */               
/* 2200 */               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 2201 */               out.write("</td>\n          </tr>\n          <tr>\n    \t<td colspan=\"2\">\n\t<div id=\"sshKeyAuth\" style=\"display:none\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t<tr>\n\t<TD  width=\"25%\" class=\"bodytext label-align\">");
/* 2202 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh.privateKeyMessage"));
/* 2203 */               out.write("</TD>\n\t<TD  width=\"75%\" class=\"footer\">");
/* 2204 */               if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                 return;
/* 2206 */               out.write("</TD>\n\t</tr>\n\t</table>\n\t</div>\n\t</td></tr>\n          <tr>\n            <td height=\"28\" width=\"25%\" class=\"bodytext label-align\">");
/* 2207 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.username"));
/* 2208 */               out.write("<span class=\"mandatory\">*</span></td>\n            <td height=\"28\" width=\"75%\">");
/* 2209 */               if (_jspx_meth_html_005ftext_005f2(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                 return;
/* 2211 */               out.write("&nbsp;&nbsp;\n            </td>\n          </tr >\n\t<tr>\n\t<td colspan=\"2\">\n\t<div id=\"passwordid\" style=\"display:none\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n          <tr>\n            <td width=\"25%\" height=\"28\" class=\"bodytext label-align\">");
/* 2212 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.password"));
/* 2213 */               out.write("<span class=\"mandatory\">*</span></td>\n            <td width=\"75%\" height=\"28\"> ");
/* 2214 */               if (_jspx_meth_html_005fpassword_005f0(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                 return;
/* 2216 */               out.write("\n              &nbsp;<b>*</b><span class=\"footer\">");
/* 2217 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.passwordleave"));
/* 2218 */               out.write("</span></td>\n          </tr>\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n\t<tr>\n\t<td colspan=\"2\">\n\t<div id=\"keyid\" style=\"display:none\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t<tr>\n\t<TD  width=\"25%\" class=\"bodytext label-align\">");
/* 2219 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.ssh.privateKey"));
/* 2220 */               out.write("<span class=\"mandatory\">*</span>\n\t</TD>\n\t<TD width=\"75%\">");
/* 2221 */               if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                 return;
/* 2223 */               out.write(" </TD>\n\t</TR>\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n          <tr>\n            <td height=\"28\" width=\"25%\" class=\"bodytext label-align\">");
/* 2224 */               out.print(FormatUtil.getString("Port"));
/* 2225 */               out.write("</td>\n            <td height=\"28\" width=\"75%\">");
/* 2226 */               if (_jspx_meth_html_005ftext_005f3(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                 return;
/* 2228 */               out.write("</td>\n          </tr>\n          <tr>\n            <td height=\"28\" width=\"25%\" class=\"bodytext label-align\">");
/* 2229 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.commandprompt"));
/* 2230 */               out.write("</td>\n            <td height=\"28\" width=\"75%\">");
/* 2231 */               if (_jspx_meth_html_005ftext_005f4(_jspx_th_c_005fif_005f21, _jspx_page_context))
/*      */                 return;
/* 2233 */               out.write("<span class=\"footer\"> ");
/* 2234 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.message.commandprompt"));
/* 2235 */               out.write("</span></td>\n          </tr>\n        </table>\n      </div></td>\n  </tr>\n  ");
/* 2236 */               int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 2237 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2241 */           if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 2242 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */           }
/*      */           
/* 2245 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 2246 */           out.write("\n  <tr>\n    <td class=\"bodytext label-align\" >");
/* 2247 */           out.print(FormatUtil.getString("am.webclient.newaction.programtoexecute"));
/* 2248 */           out.write("<span class=\"mandatory\">*</span></td>\n    <td class=\"bodytext\" colspan=\"3\">");
/* 2249 */           if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2251 */           out.write("</td>\n   \n  </tr>\n  <tr>\n    <td class=\"bodytext label-align\" width=\"25%\">");
/* 2252 */           out.print(FormatUtil.getString("am.webclient.newaction.directorytoexecuteprogram"));
/* 2253 */           out.write("<span class=\"mandatory\">*</span></td>\n    <td width=\"75%\" class=\"bodytext\" colspan=\"3\">");
/* 2254 */           if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2256 */           out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"bodytext label-align\">");
/* 2257 */           out.print(FormatUtil.getString("am.webclient.newaction.abortafter"));
/* 2258 */           out.write("\n    </td>\n    <td  class=\"bodytext\" colspan=\"3\"> ");
/* 2259 */           if (_jspx_meth_html_005ftext_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2261 */           out.write("\n      &nbsp;&nbsp;");
/* 2262 */           out.print(FormatUtil.getString("am.webclient.newaction.seconds"));
/* 2263 */           out.write("</td>\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n          <td width=\"25%\" class=\"tablebottom\">&nbsp;</td>\n          <td width=\"75%\" class=\"tablebottom\" height=\"31\">\n          ");
/*      */           
/* 2265 */           EqualTag _jspx_th_logic_005fequal_005f2 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 2266 */           _jspx_th_logic_005fequal_005f2.setPageContext(_jspx_page_context);
/* 2267 */           _jspx_th_logic_005fequal_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2269 */           _jspx_th_logic_005fequal_005f2.setName("AMActionForm");
/*      */           
/* 2271 */           _jspx_th_logic_005fequal_005f2.setProperty("method");
/*      */           
/* 2273 */           _jspx_th_logic_005fequal_005f2.setValue("editExecProgAction");
/* 2274 */           int _jspx_eval_logic_005fequal_005f2 = _jspx_th_logic_005fequal_005f2.doStartTag();
/* 2275 */           if (_jspx_eval_logic_005fequal_005f2 != 0) {
/*      */             for (;;) {
/* 2277 */               out.write("\n          <input name=\"button\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 2278 */               out.print(FormatUtil.getString("am.webclient.newaction.updateaction"));
/* 2279 */               out.write("\" onClick=\"javascript:validateAndSubmit()\">\n\t   ");
/* 2280 */               int evalDoAfterBody = _jspx_th_logic_005fequal_005f2.doAfterBody();
/* 2281 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2285 */           if (_jspx_th_logic_005fequal_005f2.doEndTag() == 5) {
/* 2286 */             this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2); return;
/*      */           }
/*      */           
/* 2289 */           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2);
/* 2290 */           out.write("\n\t   ");
/*      */           
/* 2292 */           NotEqualTag _jspx_th_logic_005fnotEqual_005f2 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(NotEqualTag.class);
/* 2293 */           _jspx_th_logic_005fnotEqual_005f2.setPageContext(_jspx_page_context);
/* 2294 */           _jspx_th_logic_005fnotEqual_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2296 */           _jspx_th_logic_005fnotEqual_005f2.setName("AMActionForm");
/*      */           
/* 2298 */           _jspx_th_logic_005fnotEqual_005f2.setProperty("method");
/*      */           
/* 2300 */           _jspx_th_logic_005fnotEqual_005f2.setValue("editExecProgAction");
/* 2301 */           int _jspx_eval_logic_005fnotEqual_005f2 = _jspx_th_logic_005fnotEqual_005f2.doStartTag();
/* 2302 */           if (_jspx_eval_logic_005fnotEqual_005f2 != 0) {
/*      */             for (;;) {
/* 2304 */               out.write("\n\t   ");
/*      */               
/* 2306 */               IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2307 */               _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 2308 */               _jspx_th_c_005fif_005f22.setParent(_jspx_th_logic_005fnotEqual_005f2);
/*      */               
/* 2310 */               _jspx_th_c_005fif_005f22.setTest("${!empty param.returnpath}");
/* 2311 */               int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 2312 */               if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                 for (;;) {
/* 2314 */                   out.write("\n\t   <input name=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 2315 */                   out.print(FormatUtil.getString("am.webclient.newaction.createbutton"));
/* 2316 */                   out.write("\" type=\"button\" onClick=\"javascript:validateAndSubmit(1)\">\n\t   ");
/* 2317 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 2318 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2322 */               if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 2323 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */               }
/*      */               
/* 2326 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 2327 */               out.write("\n\t   ");
/*      */               
/* 2329 */               IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2330 */               _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 2331 */               _jspx_th_c_005fif_005f23.setParent(_jspx_th_logic_005fnotEqual_005f2);
/*      */               
/* 2333 */               _jspx_th_c_005fif_005f23.setTest("${empty param.returnpath}");
/* 2334 */               int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 2335 */               if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                 for (;;) {
/* 2337 */                   out.write("\n\t   ");
/*      */                   
/* 2339 */                   String butText = FormatUtil.getString("am.webclient.exeprogram.button.text");
/* 2340 */                   if (isInvokedFromPopup)
/*      */                   {
/* 2342 */                     butText = FormatUtil.getString("am.webclient.exeprogram.button1.text");
/*      */                   }
/*      */                   
/* 2345 */                   out.write("\n\t   <input name=\"button\" value=\"");
/* 2346 */                   out.print(butText);
/* 2347 */                   out.write("\" type=\"button\" class=\"buttons btn_highlt\"  onClick=\"javascript:validateAndSubmit(1)\">\n\t   ");
/* 2348 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 2349 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2353 */               if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 2354 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */               }
/*      */               
/* 2357 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 2358 */               out.write("\n  \t   ");
/* 2359 */               int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f2.doAfterBody();
/* 2360 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2364 */           if (_jspx_th_logic_005fnotEqual_005f2.doEndTag() == 5) {
/* 2365 */             this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f2); return;
/*      */           }
/*      */           
/* 2368 */           this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f2);
/* 2369 */           out.write("\n   \t\t<input name=\"cancel\" type=\"hidden\" value=\"true\">\n          <input name=\"button12\" type=\"reset\" class=\"buttons btn_reset\" value=\"");
/* 2370 */           out.print(FormatUtil.getString("am.webclient.newaction.restoredefaults"));
/* 2371 */           out.write(34);
/* 2372 */           out.write(62);
/* 2373 */           out.write(10);
/*      */           
/* 2375 */           if (request.getParameter("global") == null)
/*      */           {
/*      */ 
/* 2378 */             out.write("\n            <input name=\"button2\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 2379 */             out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2380 */             out.write("\" onClick=\"javascript:history.back()\">\n            ");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 2386 */             out.write("\n            <input name=\"button2\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 2387 */             out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 2388 */             out.write("\" onClick=\"javascript:window.parent.close()\">\n            ");
/*      */           }
/*      */           
/*      */ 
/* 2392 */           out.write("\n  </td>\n\n        </tr>\n      </table>\n</td>\n </td>\n        <td width=\"30%\" valign=\"top\">\n        ");
/* 2393 */           StringBuffer helpcardKey = new StringBuffer();
/* 2394 */           helpcardKey.append(FormatUtil.getString("am.webclient.actions.quicknote.executeprogram.help"));
/* 2395 */           if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.localscript.remove")) {
/* 2396 */             helpcardKey.append(FormatUtil.getString("am.webclient.newaction.programnote", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }) + "<br><br>");
/*      */           }
/* 2398 */           if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.admintab.upload.jar.remove")) {
/* 2399 */             helpcardKey.append(FormatUtil.getString("am.webclient.newaction.programnote1", new String[] { java.net.URLEncoder.encode("/showTile.do?TileName=.ExecProg&haid=" + request.getParameter("haid")), com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }) + "<br><br>");
/*      */           }
/* 2401 */           helpcardKey.append("<b>" + FormatUtil.getString("am.webclient.newaction.programtagnote") + "</b>" + "<br>");
/* 2402 */           helpcardKey.append(FormatUtil.getString("am.webclient.newaction.programtags") + "<br>");
/* 2403 */           helpcardKey.append(FormatUtil.getString("am.webclient.newaction.programtagnote1"));
/*      */           
/* 2405 */           out.write("\n        \t");
/* 2406 */           org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()), out, false);
/* 2407 */           out.write("\n\t\t</td>\n        </tr>\n        </table>\n\n");
/* 2408 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2409 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2413 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2414 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else {
/* 2417 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2418 */         out.write(10);
/* 2419 */         out.write(10);
/*      */       }
/* 2421 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2422 */         out = _jspx_out;
/* 2423 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2424 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2425 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2428 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2434 */     PageContext pageContext = _jspx_page_context;
/* 2435 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2437 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2438 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2439 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 2441 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2442 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2443 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2445 */         out.write("\n\t\talertUser();\n\treturn;\n\t");
/* 2446 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2447 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2451 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2452 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2453 */       return true;
/*      */     }
/* 2455 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2456 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2461 */     PageContext pageContext = _jspx_page_context;
/* 2462 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2464 */     org.apache.struts.taglib.logic.NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (org.apache.struts.taglib.logic.NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(org.apache.struts.taglib.logic.NotPresentTag.class);
/* 2465 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2466 */     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */     
/* 2468 */     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2469 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2470 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 2472 */         out.write("\n\n\t\tvar mapperid=document.AMActionForm.choosehost.value;\n\tif(trimAll(mapperid)==\"\")\n\t{\n\t\talert(\"No remote hosts are added yet\");\n\t\treturn;\n\t}\n\tMM_openBrWindow('/jsp/ScriptHostDetails.jsp?mapperid='+mapperid+'&avoidFilter=true','EditHostDetails','width=500,height=375,top=200,left=200,scrollbars=yes,resizable=yes'); ");
/* 2473 */         out.write(10);
/* 2474 */         out.write(9);
/* 2475 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2476 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2480 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2481 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2482 */       return true;
/*      */     }
/* 2484 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2485 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2490 */     PageContext pageContext = _jspx_page_context;
/* 2491 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2493 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2494 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2495 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 2497 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 2498 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2499 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 2501 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 2502 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2503 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2507 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2508 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2509 */       return true;
/*      */     }
/* 2511 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2512 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2517 */     PageContext pageContext = _jspx_page_context;
/* 2518 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2520 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2521 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2522 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2524 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2526 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2527 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2528 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2529 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2530 */       return true;
/*      */     }
/* 2532 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2533 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2538 */     PageContext pageContext = _jspx_page_context;
/* 2539 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2541 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2542 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2543 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2545 */     _jspx_th_c_005fif_005f0.setTest("${globalconfig['mailserverconfigured'] != 'true'}");
/* 2546 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2547 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 2549 */         out.write("\n\t\t\tmyOnLoad();\n\t\t");
/* 2550 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2551 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2555 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2556 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2557 */       return true;
/*      */     }
/* 2559 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2560 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2565 */     PageContext pageContext = _jspx_page_context;
/* 2566 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2568 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2569 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2570 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2572 */     _jspx_th_c_005fif_005f1.setTest("${globalconfig['mailserverconfigured'] != 'true' && param.checkForMailSetting eq 'true'}");
/* 2573 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2574 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2576 */         out.write("\n\t\t\t\tmyOnLoad();\n\t\t\t");
/* 2577 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2578 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2582 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2583 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2584 */       return true;
/*      */     }
/* 2586 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2587 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2592 */     PageContext pageContext = _jspx_page_context;
/* 2593 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2595 */     org.apache.taglibs.standard.tag.common.core.CatchTag _jspx_th_c_005fcatch_005f0 = (org.apache.taglibs.standard.tag.common.core.CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(org.apache.taglibs.standard.tag.common.core.CatchTag.class);
/* 2596 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 2597 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 2599 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 2600 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 2602 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 2603 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 2605 */           out.write(" \n      ");
/* 2606 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 2607 */             return true;
/* 2608 */           out.write(32);
/* 2609 */           out.write(10);
/* 2610 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 2611 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2615 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 2616 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2619 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 2620 */         out = _jspx_page_context.popBody(); }
/* 2621 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 2623 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 2624 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 2626 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 2631 */     PageContext pageContext = _jspx_page_context;
/* 2632 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2634 */     org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag.class);
/* 2635 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 2636 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 2638 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 2640 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 2641 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 2642 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 2643 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2644 */       return true;
/*      */     }
/* 2646 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2647 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2652 */     PageContext pageContext = _jspx_page_context;
/* 2653 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2655 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 2656 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 2657 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2659 */     _jspx_th_c_005fset_005f5.setVar("wiz");
/*      */     
/* 2661 */     _jspx_th_c_005fset_005f5.setValue("${param.wiz}");
/*      */     
/* 2663 */     _jspx_th_c_005fset_005f5.setScope("request");
/* 2664 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 2665 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 2666 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 2667 */       return true;
/*      */     }
/* 2669 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 2670 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2675 */     PageContext pageContext = _jspx_page_context;
/* 2676 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2678 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2679 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2680 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2682 */     _jspx_th_c_005fout_005f1.setValue("${wizimage}");
/*      */     
/* 2684 */     _jspx_th_c_005fout_005f1.setEscapeXml("false");
/* 2685 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2686 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2687 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2688 */       return true;
/*      */     }
/* 2690 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2691 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2696 */     PageContext pageContext = _jspx_page_context;
/* 2697 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2699 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2700 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2701 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2703 */     _jspx_th_c_005fif_005f9.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 2704 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2705 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 2707 */         out.write("\n    <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 2708 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 2709 */           return true;
/* 2710 */         out.write("&wiz=true\">\n    ");
/* 2711 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2712 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2716 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2717 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2718 */       return true;
/*      */     }
/* 2720 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2721 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2726 */     PageContext pageContext = _jspx_page_context;
/* 2727 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2729 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2730 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2731 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 2733 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 2734 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2735 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2736 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2737 */       return true;
/*      */     }
/* 2739 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2740 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2745 */     PageContext pageContext = _jspx_page_context;
/* 2746 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2748 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2749 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2750 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2752 */     _jspx_th_c_005fout_005f3.setValue("${wizimage}");
/*      */     
/* 2754 */     _jspx_th_c_005fout_005f3.setEscapeXml("false");
/* 2755 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2756 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2757 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2758 */       return true;
/*      */     }
/* 2760 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2761 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2766 */     PageContext pageContext = _jspx_page_context;
/* 2767 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2769 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2770 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2771 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2773 */     _jspx_th_c_005fif_005f10.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 2774 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2775 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 2777 */         out.write("\n    \t</a>\n    \t");
/* 2778 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2779 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2783 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2784 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2785 */       return true;
/*      */     }
/* 2787 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2788 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2793 */     PageContext pageContext = _jspx_page_context;
/* 2794 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2796 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2797 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2798 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2800 */     _jspx_th_c_005fif_005f11.setTest("${wizimage=='/images/new_high.gif'}");
/* 2801 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2802 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 2804 */         out.write("\n       <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 2805 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 2806 */           return true;
/* 2807 */         out.write("&wiz=true\">\n       ");
/* 2808 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2809 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2813 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2814 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2815 */       return true;
/*      */     }
/* 2817 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2818 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2823 */     PageContext pageContext = _jspx_page_context;
/* 2824 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2826 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2827 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2828 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 2830 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 2831 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2832 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2833 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2834 */       return true;
/*      */     }
/* 2836 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2837 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2842 */     PageContext pageContext = _jspx_page_context;
/* 2843 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2845 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2846 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2847 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2849 */     _jspx_th_c_005fout_005f5.setValue("${wizimage}");
/*      */     
/* 2851 */     _jspx_th_c_005fout_005f5.setEscapeXml("false");
/* 2852 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2853 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2854 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2855 */       return true;
/*      */     }
/* 2857 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2858 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2863 */     PageContext pageContext = _jspx_page_context;
/* 2864 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2866 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2867 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2868 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2870 */     _jspx_th_c_005fif_005f12.setTest("${wizimage=='/images/new_high.gif'}");
/* 2871 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2872 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 2874 */         out.write("\n       \t</a>\n       \t");
/* 2875 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 2876 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2880 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 2881 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2882 */       return true;
/*      */     }
/* 2884 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2885 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2890 */     PageContext pageContext = _jspx_page_context;
/* 2891 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2893 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2894 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 2895 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2897 */     _jspx_th_c_005fif_005f15.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 2898 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 2899 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 2901 */         out.write("\t\n    <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 2902 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 2903 */           return true;
/* 2904 */         out.write("&wiz=true\">\n ");
/* 2905 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 2906 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2910 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 2911 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 2912 */       return true;
/*      */     }
/* 2914 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 2915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2920 */     PageContext pageContext = _jspx_page_context;
/* 2921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2923 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2924 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2925 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 2927 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 2928 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2929 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2930 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2931 */       return true;
/*      */     }
/* 2933 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2934 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2939 */     PageContext pageContext = _jspx_page_context;
/* 2940 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2942 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2943 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2944 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2946 */     _jspx_th_c_005fout_005f7.setValue("${wizimage}");
/*      */     
/* 2948 */     _jspx_th_c_005fout_005f7.setEscapeXml("false");
/* 2949 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2950 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2951 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2952 */       return true;
/*      */     }
/* 2954 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2955 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2960 */     PageContext pageContext = _jspx_page_context;
/* 2961 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2963 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2964 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 2965 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2967 */     _jspx_th_c_005fif_005f16.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 2968 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 2969 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 2971 */         out.write("\t    \n    </a>\n ");
/* 2972 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 2973 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2977 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 2978 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2979 */       return true;
/*      */     }
/* 2981 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2982 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2987 */     PageContext pageContext = _jspx_page_context;
/* 2988 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2990 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2991 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2992 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 2994 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 2996 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 2997 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2998 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2999 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 3000 */       return true;
/*      */     }
/* 3002 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 3003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3008 */     PageContext pageContext = _jspx_page_context;
/* 3009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3011 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 3012 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 3013 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 3015 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 3017 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 3018 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 3019 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 3020 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 3021 */       return true;
/*      */     }
/* 3023 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 3024 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3029 */     PageContext pageContext = _jspx_page_context;
/* 3030 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3032 */     EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 3033 */     _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/* 3034 */     _jspx_th_logic_005fequal_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3036 */     _jspx_th_logic_005fequal_005f0.setName("AMActionForm");
/*      */     
/* 3038 */     _jspx_th_logic_005fequal_005f0.setProperty("method");
/*      */     
/* 3040 */     _jspx_th_logic_005fequal_005f0.setValue("editExecProgAction");
/* 3041 */     int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/* 3042 */     if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */       for (;;) {
/* 3044 */         out.write(10);
/* 3045 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_logic_005fequal_005f0, _jspx_page_context))
/* 3046 */           return true;
/* 3047 */         out.write(10);
/* 3048 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/* 3049 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3053 */     if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/* 3054 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 3055 */       return true;
/*      */     }
/* 3057 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 3058 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_logic_005fequal_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3063 */     PageContext pageContext = _jspx_page_context;
/* 3064 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3066 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 3067 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 3068 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_logic_005fequal_005f0);
/*      */     
/* 3070 */     _jspx_th_html_005fhidden_005f0.setProperty("method");
/* 3071 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 3072 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 3073 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 3074 */       return true;
/*      */     }
/* 3076 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 3077 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3082 */     PageContext pageContext = _jspx_page_context;
/* 3083 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3085 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(WriteTag.class);
/* 3086 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 3087 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 3089 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/* 3090 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 3091 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 3092 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 3093 */       return true;
/*      */     }
/* 3095 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 3096 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3101 */     PageContext pageContext = _jspx_page_context;
/* 3102 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3104 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 3105 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 3106 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3108 */     _jspx_th_html_005fhidden_005f1.setProperty("id");
/* 3109 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 3110 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 3111 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3112 */       return true;
/*      */     }
/* 3114 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3115 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3120 */     PageContext pageContext = _jspx_page_context;
/* 3121 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3123 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3124 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3125 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3127 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 3129 */     _jspx_th_html_005ftext_005f0.setSize("40");
/*      */     
/* 3131 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/*      */     
/* 3133 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/* 3134 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3135 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3136 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3137 */       return true;
/*      */     }
/* 3139 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3140 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3145 */     PageContext pageContext = _jspx_page_context;
/* 3146 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3148 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3149 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 3150 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3152 */     _jspx_th_html_005fradio_005f0.setProperty("serversite");
/*      */     
/* 3154 */     _jspx_th_html_005fradio_005f0.setValue("local");
/*      */     
/* 3156 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:manageremotescript()");
/* 3157 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 3158 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 3159 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3160 */       return true;
/*      */     }
/* 3162 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3163 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3168 */     PageContext pageContext = _jspx_page_context;
/* 3169 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3171 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3172 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 3173 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3175 */     _jspx_th_html_005fradio_005f1.setProperty("serversite");
/*      */     
/* 3177 */     _jspx_th_html_005fradio_005f1.setValue("remote");
/*      */     
/* 3179 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:manageremotescript()");
/* 3180 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 3181 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 3182 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3183 */       return true;
/*      */     }
/* 3185 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3186 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3191 */     PageContext pageContext = _jspx_page_context;
/* 3192 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3194 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3195 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3196 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 3198 */     _jspx_th_html_005ftext_005f1.setProperty("host");
/*      */     
/* 3200 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext normal");
/*      */     
/* 3202 */     _jspx_th_html_005ftext_005f1.setSize("15");
/* 3203 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3204 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3205 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3206 */       return true;
/*      */     }
/* 3208 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3214 */     PageContext pageContext = _jspx_page_context;
/* 3215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3217 */     org.apache.struts.taglib.html.CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (org.apache.struts.taglib.html.CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(org.apache.struts.taglib.html.CheckboxTag.class);
/* 3218 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 3219 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 3221 */     _jspx_th_html_005fcheckbox_005f0.setProperty("sshkey");
/*      */     
/* 3223 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("showPrivateKey()");
/* 3224 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 3225 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 3226 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 3227 */       return true;
/*      */     }
/* 3229 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 3230 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3235 */     PageContext pageContext = _jspx_page_context;
/* 3236 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3238 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3239 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 3240 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 3242 */     _jspx_th_html_005ftext_005f2.setProperty("username");
/*      */     
/* 3244 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext normal");
/*      */     
/* 3246 */     _jspx_th_html_005ftext_005f2.setSize("15");
/* 3247 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 3248 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 3249 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3250 */       return true;
/*      */     }
/* 3252 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3253 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3258 */     PageContext pageContext = _jspx_page_context;
/* 3259 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3261 */     org.apache.struts.taglib.html.PasswordTag _jspx_th_html_005fpassword_005f0 = (org.apache.struts.taglib.html.PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(org.apache.struts.taglib.html.PasswordTag.class);
/* 3262 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 3263 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 3265 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 3267 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext normal");
/*      */     
/* 3269 */     _jspx_th_html_005fpassword_005f0.setSize("15");
/* 3270 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 3271 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 3272 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 3273 */       return true;
/*      */     }
/* 3275 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 3276 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3281 */     PageContext pageContext = _jspx_page_context;
/* 3282 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3284 */     org.apache.struts.taglib.html.TextareaTag _jspx_th_html_005ftextarea_005f0 = (org.apache.struts.taglib.html.TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(org.apache.struts.taglib.html.TextareaTag.class);
/* 3285 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 3286 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 3288 */     _jspx_th_html_005ftextarea_005f0.setProperty("description");
/*      */     
/* 3290 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea large");
/*      */     
/* 3292 */     _jspx_th_html_005ftextarea_005f0.setRows("4");
/*      */     
/* 3294 */     _jspx_th_html_005ftextarea_005f0.setCols("50");
/* 3295 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 3296 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 3297 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 3298 */       return true;
/*      */     }
/* 3300 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 3301 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3306 */     PageContext pageContext = _jspx_page_context;
/* 3307 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3309 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3310 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 3311 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 3313 */     _jspx_th_html_005ftext_005f3.setProperty("port");
/*      */     
/* 3315 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext small");
/*      */     
/* 3317 */     _jspx_th_html_005ftext_005f3.setValue("23");
/*      */     
/* 3319 */     _jspx_th_html_005ftext_005f3.setSize("5");
/* 3320 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 3321 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 3322 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3323 */       return true;
/*      */     }
/* 3325 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3326 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_c_005fif_005f21, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3331 */     PageContext pageContext = _jspx_page_context;
/* 3332 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3334 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3335 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 3336 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_c_005fif_005f21);
/*      */     
/* 3338 */     _jspx_th_html_005ftext_005f4.setProperty("prompt");
/*      */     
/* 3340 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext msmall");
/*      */     
/* 3342 */     _jspx_th_html_005ftext_005f4.setValue("$");
/*      */     
/* 3344 */     _jspx_th_html_005ftext_005f4.setSize("3");
/* 3345 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 3346 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 3347 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 3348 */       return true;
/*      */     }
/* 3350 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 3351 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3356 */     PageContext pageContext = _jspx_page_context;
/* 3357 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3359 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3360 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 3361 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3363 */     _jspx_th_html_005ftext_005f5.setProperty("command");
/*      */     
/* 3365 */     _jspx_th_html_005ftext_005f5.setSize("40");
/*      */     
/* 3367 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext default");
/* 3368 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 3369 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 3370 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3371 */       return true;
/*      */     }
/* 3373 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3374 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3379 */     PageContext pageContext = _jspx_page_context;
/* 3380 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3382 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3383 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 3384 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3386 */     _jspx_th_html_005ftext_005f6.setProperty("execProgExecDir");
/*      */     
/* 3388 */     _jspx_th_html_005ftext_005f6.setSize("40");
/*      */     
/* 3390 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext xxlarge");
/*      */     
/* 3392 */     _jspx_th_html_005ftext_005f6.setMaxlength("200");
/* 3393 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 3394 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 3395 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 3396 */       return true;
/*      */     }
/* 3398 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 3399 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3404 */     PageContext pageContext = _jspx_page_context;
/* 3405 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3407 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3408 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 3409 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3411 */     _jspx_th_html_005ftext_005f7.setProperty("abortafter");
/*      */     
/* 3413 */     _jspx_th_html_005ftext_005f7.setSize("5");
/*      */     
/* 3415 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext small");
/*      */     
/* 3417 */     _jspx_th_html_005ftext_005f7.setMaxlength("100");
/* 3418 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 3419 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 3420 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 3421 */       return true;
/*      */     }
/* 3423 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 3424 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ExecProgramActionForm_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */