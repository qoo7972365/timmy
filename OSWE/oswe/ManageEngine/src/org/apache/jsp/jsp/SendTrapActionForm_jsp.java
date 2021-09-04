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
/*      */ import org.apache.struts.taglib.logic.NotEqualTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class SendTrapActionForm_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   29 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   35 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(5);
/*   36 */   static { _jspx_dependants.put("/jsp/v1TrapActionFields.jsp", Long.valueOf(1473429417000L));
/*   37 */     _jspx_dependants.put("/jsp/includes/NewActions.jspf", Long.valueOf(1473429417000L));
/*   38 */     _jspx_dependants.put("/jsp/v2TrapActionFields.jsp", Long.valueOf(1473429417000L));
/*   39 */     _jspx_dependants.put("/jsp/v3TrapActionFields.jsp", Long.valueOf(1473429417000L));
/*   40 */     _jspx_dependants.put("/jsp/includes/CreateApplicationWizard.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   73 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   77 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   78 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   79 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   80 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   81 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   82 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   85 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   86 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   94 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   95 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   96 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   97 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   98 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   99 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  100 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  101 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  102 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  103 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  107 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  108 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  109 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  110 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  111 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  112 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  113 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  114 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  115 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/*  116 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/*  117 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*  118 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/*  119 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  120 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  121 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  122 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  123 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.release();
/*  124 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*  125 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.release();
/*  126 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
/*  127 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*  128 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  129 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/*  130 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*  131 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  138 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  141 */     JspWriter out = null;
/*  142 */     Object page = this;
/*  143 */     JspWriter _jspx_out = null;
/*  144 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  148 */       response.setContentType("text/html;charset=UTF-8");
/*  149 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  151 */       _jspx_page_context = pageContext;
/*  152 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  153 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  154 */       session = pageContext.getSession();
/*  155 */       out = pageContext.getOut();
/*  156 */       _jspx_out = out;
/*      */       
/*  158 */       out.write("<!-- $Id$ -->\n\n\n\n\n\n\n\n");
/*      */       
/*  160 */       String mode = request.getParameter("mode");
/*  161 */       String wiz = request.getParameter("wiz");
/*  162 */       int actionID = -1;
/*  163 */       if (request.getParameter("actionID") != null)
/*      */       {
/*  165 */         actionID = Integer.parseInt(request.getParameter("actionID"));
/*      */       }
/*  167 */       boolean isInvokedFromPopup = request.getParameter("global") != null;
/*  168 */       String path = (String)session.getAttribute("mibNodesToOpen");
/*  169 */       String oid = (String)session.getAttribute("mibNodeToSelect");
/*  170 */       String snmpversion = request.getParameter("snmpversion");
/*  171 */       if (snmpversion == null)
/*      */       {
/*  173 */         snmpversion = ((com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm")).getSnmpVersionList();
/*      */       }
/*  175 */       String[] versions = { "v1", "v2C", "v3" };
/*  176 */       String[] versionValues = { "11", "12", "13" };
/*      */       
/*  178 */       out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/validation.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<script>\n\nfunction setVal(val)\n{\n\tif(document.AMActionForm.snmpVersionList.value==\"12\")\n\t{\n\t   document.AMActionForm.v2SNMPTrapOID.value=val;\n\t}\n\telse if(document.AMActionForm.snmpVersionList.value==\"11\")\n\t{\n\t   document.AMActionForm.v1TrapEnterprise.value=val;\n\t}\n\telse\n\t{\n\t   document.AMActionForm.objectID.value=val;\n\t}\n\treloadForm();\n}\n\nfunction fnFormSubmit()\n{\n\tdocument.AMActionForm.submit();\n}\n\nfunction validateAndSubmit(value)\n{\n\t");
/*  179 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/*  181 */       out.write("\n\tif(value == 1)\n\t{\n\tdocument.AMActionForm.cancel.value=\"false\";\n\tfor(i=0;i<document.AMActionForm.elements.length;i++)\n\t{\n\t\tif(document.AMActionForm.elements[i].type==\"text\")\n                {\n                        var name = document.AMActionForm.elements[i].name;\n                        var value = document.AMActionForm.elements[i].value;\n\n\t\t\tif(name==\"displayname\")\n                        {\n                             if(trimAll(value)==\"\")\n                             {\n                             \twindow.alert('");
/*  182 */       out.print(FormatUtil.getString("am.webclient.newaction.alertdisplayname"));
/*  183 */       out.write("');\n                             \treturn false;\n                             }\n                             if(displayNameHasQuotes(trimAll(value),'");
/*  184 */       out.print(FormatUtil.getString("am.webclient.newaction.alertsinglequote"));
/*  185 */       out.write("'))\n\t\t\t     {\n\t\t\t      \treturn false;\n                             }\n                             if(isBlackListedCharactersPresent(value,'");
/*  186 */       out.print(FormatUtil.getString("am.webclient.specialchar.alert.displayname"));
/*  187 */       out.write("')) {\n                              return false;\n                             }\n\t\t\t    \n                        }\n                        else if(name==\"trapDestinationAddress\")\n                        {\n                                if(trimAll(value)==\"\")\n                          \t{\n                          \t\twindow.alert('");
/*  188 */       out.print(FormatUtil.getString("am.webclient.newaction.alertemptydestinationaddress"));
/*  189 */       out.write("');\n                                \treturn false\n                                }\n\n                        }\n                        else if(name==\"trapCommunity\")\n\t\t\t{\n\t\t\t      if(trimAll(value)==\"\")\n\t\t\t      {\n\t\t\t      \t  window.alert('");
/*  190 */       out.print(FormatUtil.getString("am.webclient.newaction.alertemptycommunity"));
/*  191 */       out.write("');\n\t\t\t          return false\n\t\t\t      }\n\t\t\t}\n                        else if((name==\"trapDestinationPort\"))\n\t\t\t{\n\t\t\t\tif((trimAll(value)==\"\") || (isInteger(value)==false))\n\t\t\t\t{\n\t\t\t\t\twindow.alert('");
/*  192 */       out.print(FormatUtil.getString("am.webclient.newaction.alertemptydestinationport"));
/*  193 */       out.write("');\n\t\t\t\t\treturn false;\n\t\t\t\t}\n                        }\n                        else if((name==\"mibName\"))\n\t\t\t{\n\t\t\t\tif(trimAll(value)==\"\")\n\t\t\t\t{\n\t\t\t\t\twindow.alert('");
/*  194 */       out.print(FormatUtil.getString("am.webclient.newaction.alertemptymibname"));
/*  195 */       out.write("');\n\t\t\t\t\treturn false;\n\t\t\t\t}\n                        }\n                        else if((name==\"objectID\"))\n\t\t\t{\n\t\t\t\tif(trimAll(value)==\"\")\n\t\t\t\t{\n\t\t\t\t\twindow.alert('");
/*  196 */       out.print(FormatUtil.getString("am.webclient.newaction.alertemptyoid"));
/*  197 */       out.write("');\n\t\t\t\t\treturn false;\n\t\t\t\t}\n                        }\n                        else if((name==\"trapVarbinds\"))\n\t\t\t{\n\n\t\t\t\tif(trimAll(value)==\"\")\n\t\t\t\t{\n\t\t\t\t\twindow.alert('");
/*  198 */       out.print(FormatUtil.getString("am.webclient.newaction.alertmessageempty"));
/*  199 */       out.write("');\n\t\t\t\t\treturn false;\n\t\t\t\t}\n                        }\n                        else if((name==\"v1TrapSpecificType\"))\n\t\t\t{\n\t\t\t\tif((trimAll(value)==\"\") || (isInteger(value)==false))\n\t\t\t\t{\n\t\t\t\t\t\twindow.alert('");
/*  200 */       out.print(FormatUtil.getString("am.webclient.newaction.alertvalidintegerfield"));
/*  201 */       out.write("');\n\t\t\t\t\t\treturn false;\n\t\t\t\t}\n                        }\n                        else if((name==\"v1TrapEnterprise\"))\n\t\t\t{\n\t\t\t\tif(trimAll(value)==\"\")\n\t\t\t\t{\n\t\t\t\t\twindow.alert('");
/*  202 */       out.print(FormatUtil.getString("am.webclient.newaction.alertemptyenterpriseoid"));
/*  203 */       out.write("');\n\t\t\t\t\treturn false;\n\t\t\t\t}\n                        }\n                        else if((name==\"v2SNMPTrapOID\"))\n\t\t\t{\n\t\t\t\tif(trimAll(value)==\"\")\n\t\t\t\t{\n\t\t\t\t\twindow.alert('");
/*  204 */       out.print(FormatUtil.getString("am.webclient.newaction.alertemptytrapoid"));
/*  205 */       out.write("');\n\t\t\t\t\treturn false;\n\t\t\t\t}\n                        }\n\n        \t }\n\t  }\n\t}\n\telse\n\t{\n\t\tdocument.AMActionForm.cancel.value=\"true\";\n\t}\n\tfnFormSubmit();\n }\n\nfunction reloadForm()\n{\n    var attribForPassing=document.AMActionForm.snmpVersionList[document.AMActionForm.snmpVersionList.selectedIndex].value;\n    document.AMActionForm.snmpVersionList.value=attribForPassing;\n    document.AMActionForm.action=\"/adminAction.do?method=reloadSendTrapActionForm\";\n    document.AMActionForm.submit();\n}\n\n</script>\n <link href=\"/images/");
/*  206 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  208 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\"><link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*      */       
/*  210 */       org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/*  211 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  212 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/*  214 */       _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*  215 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  216 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/*  218 */           out.write(10);
/*  219 */           out.write("<!--$Id$-->\n              \n\n\n\n\n<script>                      \nfunction fnFormSubmit1(object)\n{\n\tlocation.href=object;\n}\n\nfunction showMailServerSettings()\n {\n         var showMail = false;\n         var query = window.location.search;\n         var pairs = query.split(\"&\");\n         \n         for (var i=0;i<pairs.length;i++)\n         {\n                 var pos = pairs[i].indexOf('=');\n                 if (pos >= 0)\n                 {\n                         var argname = pairs[i].substring(0,pos);\n                         var value = pairs[i].substring(pos+1);\n                         if(argname == \"isFromApi\")\n                         {\n                                 showMail = true;\n                         }\n                         //keys[keys.length] = argname;\n                         //values[values.length] = value;\n                 }\n         }\n         //alert(showMail);\n         return  showMail;\n }\n\nfunction doInitStuffOnBodyLoad()\n{\n        ");
/*      */           
/*  221 */           if ((pageContext.getAttribute("jsppage") != null) && (pageContext.getAttribute("jsppage").equals("programaction")))
/*      */           {
/*      */ 
/*  224 */             out.write("\n        myOnLoad1();\n        ");
/*      */           }
/*      */           
/*      */ 
/*  228 */           out.write("\n        \n\tif(document.AMActionForm!=null)\n\t{\n\tif(typeof(document.AMActionForm.actions)!='undefined')\n\t  {\n\t  if((location.search!= null && (location.search).match(\"EmailAction\")!=null) || (location.path!=null && (location.path).match(\"EMailActionForm\")!=null))\n\t  {\n\t\t\n\t\t");
/*  229 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  231 */           out.write("\t\n\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"SMSAction\")!=null) || (location.path!=null && (location.path).match(\"SMSActionForm\")!=null) || (location.search!= null && (location.search).match(\"SMSServerConfiguration\")!=null))\n\t  {\n\t\t \n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"ExecProg\")!=null) || (location.path!=null && (location.path).match(\"ExecProgramActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"reloadSendTrapActionForm\")!=null) || (location.path!=null && (location.path).match(\"SendTrapActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"Ticket\")!=null) || (location.path!=null && (location.path).match(\"showLogTicket\")!=null) ||  (location.search).match(\"showTicketAction\")!=null )\n");
/*  232 */           out.write("\t  {\n\t\t\t");
/*  233 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  235 */           out.write("\n\t\t  ");
/*      */           
/*  237 */           if ((com.adventnet.appmanager.util.Constants.sqlManager) || (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */           {
/*  239 */             out.write("\n\t\t\tdocument.AMActionForm.actions.selectedIndex=4;\n\t    \t");
/*      */           }
/*      */           else
/*      */           {
/*  243 */             out.write("\n\t       \tdocument.AMActionForm.actions.selectedIndex=5;\n\t  \t\t");
/*      */           }
/*  245 */           out.write("\n\t  }\n\t  else if(location.pathname!= null && (location.pathname).match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(location.search!= null && (location.search).match(\"jre\")!=null || (location.search!=null && (location.search).match(\"ThreadDumpActions\")!=null))\n\t  {\n\t    ");
/*  246 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer())
/*      */           {
/*  248 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=5;\n\t\t");
/*      */           }
/*      */           else
/*      */           {
/*  252 */             out.write("\n\t    document.AMActionForm.actions.selectedIndex=6;\n        ");
/*      */           }
/*  254 */           out.write("\n\t  }\t\n\t  else if(location.search!= null && (location.search).match(\"showVMAction\")!=null || (location.search!=null && (location.search).match(\"ShowVMActions\")!=null))\n\t  {\t\t \n\t    ");
/*  255 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer())
/*      */           {
/*  257 */             out.write("\n\t    if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t    \tdocument.AMActionForm.actions.selectedIndex=9;\n\t    }else{\n\t\t\tdocument.AMActionForm.actions.selectedIndex=7;\n\t    }\n\t\t");
/*      */           }
/*      */           else
/*      */           {
/*  261 */             out.write("\n\t\t  if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t\t    \tdocument.AMActionForm.actions.selectedIndex=10;\n\t\t    }else{\n\t  \t  document.AMActionForm.actions.selectedIndex=8;\n\t\t    }\n        ");
/*      */           }
/*  263 */           out.write("\n\t  }\t  \n\t  else if(location.search!= null && (location.search).match(\"winServAction\")!=null || (location.search!=null && (location.search).match(\"winServAction\")!=null))\n\t  { \n\t    ");
/*  264 */           if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  265 */             out.write("\n\t      document.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */           }
/*  267 */           else if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) {
/*  268 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=8;\n\t\t");
/*      */           } else {
/*  270 */             out.write("\n          document.AMActionForm.actions.selectedIndex=9;\n        ");
/*      */           }
/*  272 */           out.write("\t\t\n\t  }\t  \n\t   else if((location.search!= null && (location.search).match(\"ExecQueryAction\")!=null) || (location.path!=null && (location.path).match(\"ExecQueryActionForm\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=5;\n  \t   }\n\t   else if((location.search!= null && (location.search).match(\"sqlJobAction\")!=null) || (location.path!=null && (location.path).match(\"sqlJobAction\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=7;\n  \t   }\n\t   else if(location.search!= null && (location.search).match(\"amazon\")!=null)\n\t\t  {\n\t\t    ");
/*  273 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) {
/*  274 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */           } else {
/*  276 */             out.write("\n\t\t    document.AMActionForm.actions.selectedIndex=7;\n        ");
/*      */           }
/*  278 */           out.write("\n\t\t  }\n\t  \t  \n\t  }\n\n\tif(document.AMActionForm.selectedBusinessHourID != null)\n\t{\n\t\tinit();\n\t}\n\t\n\t}\n\telse\n\t{\n\t\tif(document.MBeanOperationActionForm.actions!=undefined)\n\t\t{\n\t  \t      document.MBeanOperationActionForm.actions.selectedIndex=4;\t  \n\t  \t}\n\t}\n\n}\nfunction restvalue()\n{\n//alert(document.AMActionForm.actionslist.selectedIndex);\nvar selectedIdx=document.AMActionForm.actionslist.selectedIndex;\ndocument.AMActionForm.reset();\ndocument.AMActionForm.actionslist.selectedIndex=selectedIdx;\n}\n</script>\n");
/*      */           
/*  280 */           String action_haid = request.getParameter("haid");
/*  281 */           String returnpath = "";
/*      */           
/*  283 */           if (request.getParameter("returnpath") != null)
/*      */           {
/*  285 */             returnpath = "&returnpath=" + java.net.URLEncoder.encode(request.getParameter("returnpath"));
/*      */           }
/*      */           
/*      */ 
/*  289 */           out.write(10);
/*  290 */           out.write(10);
/*      */           
/*  292 */           SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  293 */           _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  294 */           _jspx_th_c_005fset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  296 */           _jspx_th_c_005fset_005f0.setVar("isSqlManager");
/*  297 */           int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  298 */           if (_jspx_eval_c_005fset_005f0 != 0) {
/*  299 */             if (_jspx_eval_c_005fset_005f0 != 1) {
/*  300 */               out = _jspx_page_context.pushBody();
/*  301 */               _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  302 */               _jspx_th_c_005fset_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  305 */               out.print(com.adventnet.appmanager.util.Constants.sqlManager);
/*  306 */               int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  307 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  310 */             if (_jspx_eval_c_005fset_005f0 != 1) {
/*  311 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  314 */           if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  315 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */           }
/*      */           
/*  318 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  319 */           out.write(10);
/*      */           
/*  321 */           SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  322 */           _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  323 */           _jspx_th_c_005fset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  325 */           _jspx_th_c_005fset_005f1.setVar("isIt360");
/*  326 */           int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  327 */           if (_jspx_eval_c_005fset_005f1 != 0) {
/*  328 */             if (_jspx_eval_c_005fset_005f1 != 1) {
/*  329 */               out = _jspx_page_context.pushBody();
/*  330 */               _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/*  331 */               _jspx_th_c_005fset_005f1.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  334 */               out.print(com.adventnet.appmanager.util.Constants.isIt360);
/*  335 */               int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/*  336 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  339 */             if (_jspx_eval_c_005fset_005f1 != 1) {
/*  340 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  343 */           if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  344 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */           }
/*      */           
/*  347 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/*  348 */           out.write(10);
/*      */           
/*  350 */           SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  351 */           _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/*  352 */           _jspx_th_c_005fset_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  354 */           _jspx_th_c_005fset_005f2.setVar("isAdminServer");
/*  355 */           int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/*  356 */           if (_jspx_eval_c_005fset_005f2 != 0) {
/*  357 */             if (_jspx_eval_c_005fset_005f2 != 1) {
/*  358 */               out = _jspx_page_context.pushBody();
/*  359 */               _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/*  360 */               _jspx_th_c_005fset_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  363 */               out.print(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer());
/*  364 */               int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/*  365 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  368 */             if (_jspx_eval_c_005fset_005f2 != 1) {
/*  369 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  372 */           if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/*  373 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */           }
/*      */           
/*  376 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/*  377 */           out.write(10);
/*      */           
/*  379 */           SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  380 */           _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/*  381 */           _jspx_th_c_005fset_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  383 */           _jspx_th_c_005fset_005f3.setVar("isProfServer");
/*  384 */           int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/*  385 */           if (_jspx_eval_c_005fset_005f3 != 0) {
/*  386 */             if (_jspx_eval_c_005fset_005f3 != 1) {
/*  387 */               out = _jspx_page_context.pushBody();
/*  388 */               _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/*  389 */               _jspx_th_c_005fset_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  392 */               out.print(com.adventnet.appmanager.util.EnterpriseUtil.isProfEdition());
/*  393 */               int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/*  394 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  397 */             if (_jspx_eval_c_005fset_005f3 != 1) {
/*  398 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  401 */           if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/*  402 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */           }
/*      */           
/*  405 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/*  406 */           out.write(10);
/*      */           
/*  408 */           SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  409 */           _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/*  410 */           _jspx_th_c_005fset_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  412 */           _jspx_th_c_005fset_005f4.setVar("isCloudServer");
/*  413 */           int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/*  414 */           if (_jspx_eval_c_005fset_005f4 != 0) {
/*  415 */             if (_jspx_eval_c_005fset_005f4 != 1) {
/*  416 */               out = _jspx_page_context.pushBody();
/*  417 */               _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/*  418 */               _jspx_th_c_005fset_005f4.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  421 */               out.print(com.adventnet.appmanager.util.EnterpriseUtil.isCloudEdition());
/*  422 */               int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/*  423 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  426 */             if (_jspx_eval_c_005fset_005f4 != 1) {
/*  427 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  430 */           if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/*  431 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */           }
/*      */           
/*  434 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/*  435 */           out.write("\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" >\n  <tr> \n    <td align=\"left\" width=\"50%\" class=\"bodytext\">");
/*  436 */           out.print(FormatUtil.getString("am.webclient.newaction.selectactiontype"));
/*  437 */           out.write("&nbsp;\n\t<select id=\"actionslist\" name=\"actions\" onchange=\"javascript:fnFormSubmit1(this.form.actions.options[this.selectedIndex].value);\" class=\"formtext\">\n\t");
/*      */           
/*  439 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  440 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  441 */           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_html_005fform_005f0);
/*  442 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  443 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */             for (;;) {
/*  445 */               out.write(10);
/*  446 */               out.write(9);
/*      */               
/*  448 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  449 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  450 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */               
/*  452 */               _jspx_th_c_005fwhen_005f0.setTest("${empty param.global}");
/*  453 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  454 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                 for (;;) {
/*  456 */                   out.write("\t\n\t\t<option value=\"/showTile.do?TileName=.EmailActions&haid=");
/*  457 */                   out.print(action_haid);
/*  458 */                   out.print(returnpath);
/*  459 */                   out.write(34);
/*  460 */                   out.write(62);
/*  461 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  462 */                   out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.SMSActions&haid=");
/*  463 */                   out.print(action_haid);
/*  464 */                   out.print(returnpath);
/*  465 */                   out.write(34);
/*  466 */                   out.write(62);
/*  467 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  468 */                   out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.ExecProg&haid=");
/*  469 */                   out.print(action_haid);
/*  470 */                   out.print(returnpath);
/*  471 */                   out.write(34);
/*  472 */                   out.write(62);
/*  473 */                   out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  474 */                   out.write("</option>\n\t\t<option value=\"/adminAction.do?method=reloadSendTrapActionForm&haid=");
/*  475 */                   out.print(action_haid);
/*  476 */                   out.print(returnpath);
/*  477 */                   out.write(34);
/*  478 */                   out.write(62);
/*  479 */                   out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/*  480 */                   out.write("</option>\n\t\t\n\t\t");
/*      */                   
/*  482 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  483 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  484 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*  485 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  486 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/*  488 */                       out.write("\n\t\t\t");
/*      */                       
/*  490 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  491 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  492 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/*  494 */                       _jspx_th_c_005fwhen_005f1.setTest("${!isIt360}");
/*  495 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  496 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/*  498 */                           out.write("\n\t\t\t\t");
/*      */                           
/*  500 */                           ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  501 */                           _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  502 */                           _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/*  503 */                           int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  504 */                           if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                             for (;;) {
/*  506 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/*  508 */                               WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  509 */                               _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  510 */                               _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                               
/*  512 */                               _jspx_th_c_005fwhen_005f2.setTest("${!isSqlManager}");
/*  513 */                               int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  514 */                               if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                 for (;;) {
/*  516 */                                   out.write("\n\t\t\t\t\t\t<!-- MBean Operation-->\n\t\t\t\t\t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  517 */                                   out.print(action_haid);
/*  518 */                                   out.print(returnpath);
/*  519 */                                   out.write(34);
/*  520 */                                   out.write(62);
/*  521 */                                   out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  522 */                                   out.write("</option>\n\t\t\t\t\t");
/*  523 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  524 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  528 */                               if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  529 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                               }
/*      */                               
/*  532 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  533 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/*  535 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  536 */                               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  537 */                               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f2);
/*  538 */                               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  539 */                               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                 for (;;) {
/*  541 */                                   out.write("\n\t\t\t\t\t\t<!-- Execute Query Action-->\n\t   \t\t\t\t\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  542 */                                   out.print(action_haid);
/*  543 */                                   out.print(returnpath);
/*  544 */                                   out.write(34);
/*  545 */                                   out.write(62);
/*  546 */                                   out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  547 */                                   out.write("</option>\n\t   \t\t\t\t\t<!-- Sql job action -->\n\t\t\t\t\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  548 */                                   out.print(action_haid);
/*  549 */                                   out.print(returnpath);
/*  550 */                                   out.write(34);
/*  551 */                                   out.write(62);
/*  552 */                                   out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  553 */                                   out.write("</option>\n\t\t\t\t\t");
/*  554 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  555 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  559 */                               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  560 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                               }
/*      */                               
/*  563 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  564 */                               out.write("\n\t\t\t\t");
/*  565 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  566 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  570 */                           if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  571 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                           }
/*      */                           
/*  574 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  575 */                           out.write("\n\t\t\t\t\t<!-- Log Ticket Operation-->\n\t\t\t\t\t");
/*  576 */                           if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  577 */                             out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  578 */                             out.print(action_haid);
/*  579 */                             out.print(returnpath);
/*  580 */                             out.write(34);
/*  581 */                             out.write(62);
/*  582 */                             out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  583 */                             out.write("</option> ");
/*      */                           }
/*  585 */                           out.write("\n\t\t\t");
/*  586 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  587 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  591 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  592 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/*  595 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  596 */                       out.write("\n\t\t\t");
/*      */                       
/*  598 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  599 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  600 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  601 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  602 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/*  604 */                           out.write("\n\t\t   \t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  605 */                           out.print(action_haid);
/*  606 */                           out.print(returnpath);
/*  607 */                           out.write(34);
/*  608 */                           out.write(62);
/*  609 */                           out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  610 */                           out.write("</option>\n\t\t   \t\t<!-- Log Ticket Operation-->\n\t\t   \t\t");
/*      */                           
/*  612 */                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  613 */                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  614 */                           _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                           
/*  616 */                           _jspx_th_c_005fif_005f2.setTest("${isProfServer || isAdminServer}");
/*  617 */                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  618 */                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                             for (;;) {
/*  620 */                               out.write("\n\t\t\t\t\t");
/*  621 */                               if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  622 */                                 out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  623 */                                 out.print(action_haid);
/*  624 */                                 out.print(returnpath);
/*  625 */                                 out.write(34);
/*  626 */                                 out.write(62);
/*  627 */                                 out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  628 */                                 out.write("</option> ");
/*      */                               }
/*  630 */                               out.write("\n\t\t   \t\t");
/*  631 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  632 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  636 */                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  637 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                           }
/*      */                           
/*  640 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  641 */                           out.write("\n\t\t\t");
/*  642 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  643 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  647 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  648 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/*  651 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  652 */                       out.write(10);
/*  653 */                       out.write(9);
/*  654 */                       out.write(9);
/*  655 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  656 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  660 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  661 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/*  664 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  665 */                   out.write(10);
/*  666 */                   out.write(9);
/*  667 */                   out.write(9);
/*      */                   
/*  669 */                   IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  670 */                   _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  671 */                   _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  673 */                   _jspx_th_c_005fif_005f3.setTest("${!isAdminServer}");
/*  674 */                   int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  675 */                   if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                     for (;;) {
/*  677 */                       out.write("\n\t\t\t<!--JRE Action -->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  678 */                       out.print(action_haid);
/*  679 */                       out.print(returnpath);
/*  680 */                       out.write(34);
/*  681 */                       out.write(62);
/*  682 */                       out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  683 */                       out.write("</option>\n\t\t\t<!--Amazon Instance Action-->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  684 */                       out.print(action_haid);
/*  685 */                       out.print(returnpath);
/*  686 */                       out.write(34);
/*  687 */                       out.write(62);
/*  688 */                       out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  689 */                       out.write("</option>\n\t\t\t<!--VM Action -->\n\t      \t<option value=\"/adminAction.do?method=showVMAction&haid=");
/*  690 */                       out.print(action_haid);
/*  691 */                       out.print(returnpath);
/*  692 */                       out.write(34);
/*  693 */                       out.write(62);
/*  694 */                       out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  695 */                       out.write("</option>\n\t      \t\n\t\t\t<!-- Windows Service action -->\n\t\t\t");
/*      */                       
/*  697 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  698 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  699 */                       _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */                       
/*  701 */                       _jspx_th_c_005fif_005f4.setTest("${!isCloudServer}");
/*  702 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  703 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/*  705 */                           out.write("\n\t   \t\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  706 */                           out.print(action_haid);
/*  707 */                           out.print(returnpath);
/*  708 */                           out.write(34);
/*  709 */                           out.write(62);
/*  710 */                           out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  711 */                           out.write("</option>\n\t   \t\t");
/*  712 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  713 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  717 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  718 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/*  721 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  722 */                       out.write("\n\t   \t\t<option value=\"/adminAction.do?method=showVMAction&isContainerAction=true&haid=");
/*  723 */                       out.print(action_haid);
/*  724 */                       out.print(returnpath);
/*  725 */                       out.write(34);
/*  726 */                       out.write(62);
/*  727 */                       out.print(FormatUtil.getString("am.container.action.createnew"));
/*  728 */                       out.write("</option>\n   \t\t");
/*  729 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  730 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  734 */                   if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  735 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                   }
/*      */                   
/*  738 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  739 */                   out.write(10);
/*  740 */                   out.write(9);
/*  741 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  742 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  746 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  747 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */               }
/*      */               
/*  750 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  751 */               out.write(10);
/*  752 */               out.write(9);
/*      */               
/*  754 */               OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  755 */               _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  756 */               _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*  757 */               int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  758 */               if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                 for (;;) {
/*  760 */                   out.write(10);
/*      */                   
/*  762 */                   String redirectTo = null;
/*  763 */                   if (request.getParameter("redirectto") != null)
/*      */                   {
/*  765 */                     redirectTo = java.net.URLEncoder.encode(request.getParameter("redirectto"));
/*      */                   }
/*      */                   else
/*      */                   {
/*  769 */                     redirectTo = java.net.URLEncoder.encode("/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true");
/*      */                   }
/*      */                   
/*  772 */                   out.write("\t\n\t<option value=\"/showTile.do?TileName=.EmailActions&PRINTER_FRIENDLY=true&haid=");
/*  773 */                   out.print(action_haid);
/*  774 */                   out.write("&global=true");
/*  775 */                   out.print(returnpath);
/*  776 */                   out.write(34);
/*  777 */                   out.write(62);
/*  778 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  779 */                   out.write("</option>\n\t<option value=\"/showTile.do?TileName=.SMSActions&PRINTER_FRIENDLY=true&haid=");
/*  780 */                   out.print(action_haid);
/*  781 */                   out.write("&global=true");
/*  782 */                   out.print(returnpath);
/*  783 */                   out.write(34);
/*  784 */                   out.write(62);
/*  785 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  786 */                   out.write("</option>\n\t");
/*      */                   
/*  788 */                   org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f1 = (org.apache.struts.taglib.logic.PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(org.apache.struts.taglib.logic.PresentTag.class);
/*  789 */                   _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  790 */                   _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                   
/*  792 */                   _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/*  793 */                   int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  794 */                   if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                     for (;;) {
/*  796 */                       out.write(32);
/*  797 */                       out.write("\n\t<option value=\"/jsp/ExecProgramActionForm.jsp?haid=");
/*  798 */                       out.print(action_haid);
/*  799 */                       out.write("&global=true");
/*  800 */                       out.print(returnpath);
/*  801 */                       out.write(34);
/*  802 */                       out.write(62);
/*  803 */                       out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  804 */                       out.write("</option>\n\t");
/*  805 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/*  806 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  810 */                   if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/*  811 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                   }
/*      */                   
/*  814 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*  815 */                   out.write("\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=11&haid=");
/*  816 */                   out.print(action_haid);
/*  817 */                   out.write("&global=true");
/*  818 */                   out.print(returnpath);
/*  819 */                   out.write(34);
/*  820 */                   out.write(62);
/*  821 */                   out.print(FormatUtil.getString("am.webclient.common.sendv1trap.text"));
/*  822 */                   out.write("</option>\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=12&haid=");
/*  823 */                   out.print(action_haid);
/*  824 */                   out.write("&global=true");
/*  825 */                   out.print(returnpath);
/*  826 */                   out.write(34);
/*  827 */                   out.write(62);
/*  828 */                   out.print(FormatUtil.getString("am.webclient.common.sendv2ctrap.text"));
/*  829 */                   out.write("</option>\n\t");
/*  830 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))) {
/*  831 */                     out.write(32);
/*  832 */                     out.write("\n\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&popup=true&global=true&haid=");
/*  833 */                     out.print(action_haid);
/*  834 */                     out.print(returnpath);
/*  835 */                     out.write(34);
/*  836 */                     out.write(62);
/*  837 */                     out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  838 */                     out.write("</option>\n\t");
/*      */                   }
/*  840 */                   out.write(10);
/*  841 */                   out.write(9);
/*  842 */                   out.write(9);
/*  843 */                   out.write(10);
/*  844 */                   out.write(9);
/*  845 */                   if ((!com.adventnet.appmanager.util.Constants.isIt360) || (com.adventnet.appmanager.util.EnterpriseUtil.isProfEdition()) || (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */                   {
/*  847 */                     out.write("<option value=\"/adminAction.do?method=showLogTicket&global=true&haid=");
/*  848 */                     out.print(action_haid);
/*  849 */                     out.write("&redirectTo=");
/*  850 */                     out.print(redirectTo);
/*  851 */                     out.write(34);
/*  852 */                     out.write(62);
/*  853 */                     out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  854 */                     out.write("</option> ");
/*      */                   }
/*      */                   
/*  857 */                   out.write("\n\t\n\t");
/*  858 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))) {
/*  859 */                     out.write(" \n\t<!--JRE Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  860 */                     out.print(action_haid);
/*  861 */                     out.write("&global=true");
/*  862 */                     out.print(returnpath);
/*  863 */                     out.write("&ext=true\">");
/*  864 */                     out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  865 */                     out.write("</option>\n\t<!--VM Action -->\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&haid=");
/*  866 */                     out.print(action_haid);
/*  867 */                     out.print(returnpath);
/*  868 */                     out.write("&ext=true&global=true\">");
/*  869 */                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  870 */                     out.write("</option>\n\t<!--Amazon Instance Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  871 */                     out.print(action_haid);
/*  872 */                     out.write("&global=true");
/*  873 */                     out.print(returnpath);
/*  874 */                     out.write("&ext=true\">");
/*  875 */                     out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  876 */                     out.write("</option>\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&isContainerAction=true&haid=");
/*  877 */                     out.print(action_haid);
/*  878 */                     out.print(returnpath);
/*  879 */                     out.write("&ext=true&global=true\">");
/*  880 */                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  881 */                     out.write("</option>\n\t ");
/*  882 */                   } else if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  883 */                     out.write(32);
/*  884 */                     out.write("\n\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  885 */                     out.print(action_haid);
/*  886 */                     out.write("&global=true");
/*  887 */                     out.print(returnpath);
/*  888 */                     out.write(34);
/*  889 */                     out.write(62);
/*  890 */                     out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  891 */                     out.write("</option>\n\t");
/*      */                   }
/*  893 */                   out.write(10);
/*  894 */                   out.write(9);
/*  895 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())) && (!"CLOUD".equalsIgnoreCase(com.adventnet.appmanager.util.Constants.getCategorytype()))) {
/*  896 */                     out.write("\n\t<!-- Windows Service action -->\n\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  897 */                     out.print(action_haid);
/*  898 */                     out.print(returnpath);
/*  899 */                     out.write(34);
/*  900 */                     out.write(62);
/*  901 */                     out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  902 */                     out.write("</option>\t\n\t");
/*      */                   }
/*  904 */                   out.write(10);
/*  905 */                   out.write(9);
/*  906 */                   out.write(32);
/*  907 */                   if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  908 */                     out.write("\n\t<!-- Sql job action -->\n\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  909 */                     out.print(action_haid);
/*  910 */                     out.print(returnpath);
/*  911 */                     out.write(34);
/*  912 */                     out.write(62);
/*  913 */                     out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  914 */                     out.write("</option>\n\t");
/*      */                   }
/*  916 */                   out.write(10);
/*  917 */                   out.write(9);
/*  918 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  919 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  923 */               if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  924 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */               }
/*      */               
/*  927 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  928 */               out.write(10);
/*  929 */               out.write(9);
/*  930 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  931 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  935 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  936 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */           }
/*      */           
/*  939 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  940 */           out.write("\n\t</select>\n    </td>\n  </tr>\n</table>\n\n");
/*      */           
/*  942 */           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  943 */           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  944 */           _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  946 */           _jspx_th_c_005fif_005f5.setTest("${param.global=='true'}");
/*  947 */           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  948 */           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */             for (;;) {
/*  950 */               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td valign=\"top\"> \n<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/*  951 */               out.write("<!--$Id$-->\n\n\n\n");
/*  952 */               if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                 return;
/*  954 */               out.write("\n      \n\n");
/*      */               
/*  956 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  957 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/*  958 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f5);
/*      */               
/*  960 */               _jspx_th_c_005fif_005f6.setTest("${(uri=='/jsp/CreateApplication.jsp')|| uri=='/admin/createapplication.do'||uri=='/admin/createapplication.do' ||(!empty param.wiz && !empty param.haid && (empty invalidhaid))||(param.method=='insert')}");
/*  961 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/*  962 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/*  964 */                   out.write("\n\t  <tr>\n\t  <td class=\"tdindent\">\n");
/*  965 */                   if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/*  967 */                   out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">  \n  <tr> \n    <td width=\"2%\">");
/*      */                   
/*  969 */                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  970 */                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/*  971 */                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/*  973 */                   _jspx_th_c_005fif_005f7.setTest("${uri=='/jsp/CreateApplication.jsp' || uri=='/admin/createapplicationwiz.do'||uri=='/admin/createapplication.do'}");
/*  974 */                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/*  975 */                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                     for (;;) {
/*  977 */                       out.write("\n    \t");
/*      */                       
/*  979 */                       SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  980 */                       _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/*  981 */                       _jspx_th_c_005fset_005f6.setParent(_jspx_th_c_005fif_005f7);
/*      */                       
/*  983 */                       _jspx_th_c_005fset_005f6.setVar("wizimage");
/*  984 */                       int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/*  985 */                       if (_jspx_eval_c_005fset_005f6 != 0) {
/*  986 */                         if (_jspx_eval_c_005fset_005f6 != 1) {
/*  987 */                           out = _jspx_page_context.pushBody();
/*  988 */                           _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/*  989 */                           _jspx_th_c_005fset_005f6.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/*  992 */                           out.write(" \n    \t<img src=\"/images/wiz_newbizapp_high.gif\" border=\"0\" align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/*  993 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/*  994 */                           out.write(" </b></font>\n    \t");
/*  995 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/*  996 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*  999 */                         if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1000 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1003 */                       if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 1004 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6); return;
/*      */                       }
/*      */                       
/* 1007 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 1008 */                       out.write("\n    ");
/* 1009 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1010 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1014 */                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1015 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                   }
/*      */                   
/* 1018 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1019 */                   out.write("\n    ");
/*      */                   
/* 1021 */                   IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1022 */                   _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 1023 */                   _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1025 */                   _jspx_th_c_005fif_005f8.setTest("${uri!='/jsp/CreateApplication.jsp' && uri!='/admin/createapplicationwiz.do'&& uri!='/admin/createapplication.do'}");
/* 1026 */                   int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 1027 */                   if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                     for (;;) {
/* 1029 */                       out.write("\n    \t");
/*      */                       
/* 1031 */                       SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1032 */                       _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 1033 */                       _jspx_th_c_005fset_005f7.setParent(_jspx_th_c_005fif_005f8);
/*      */                       
/* 1035 */                       _jspx_th_c_005fset_005f7.setVar("wizimage");
/* 1036 */                       int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 1037 */                       if (_jspx_eval_c_005fset_005f7 != 0) {
/* 1038 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1039 */                           out = _jspx_page_context.pushBody();
/* 1040 */                           _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 1041 */                           _jspx_th_c_005fset_005f7.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1044 */                           out.write("\n    \t<img src=\"/images/wiz_newbizapp_nor.gif\" border=0> <font family=\"verdana\" size=\"2\" color=\"#818181\">");
/* 1045 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1046 */                           out.write("</font>  \t");
/* 1047 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 1048 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1051 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1052 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1055 */                       if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 1056 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                       }
/*      */                       
/* 1059 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 1060 */                       out.write("\n    ");
/* 1061 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 1062 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1066 */                   if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 1067 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                   }
/*      */                   
/* 1070 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 1071 */                   out.write("      \n\t</td>\n    <td width=\"20%\">");
/* 1072 */                   if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1074 */                   out.write("</td>  \n   \n");
/*      */                   
/* 1076 */                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1077 */                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1078 */                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f6);
/* 1079 */                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1080 */                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                     for (;;) {
/* 1082 */                       out.write("\n    ");
/*      */                       
/* 1084 */                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1085 */                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1086 */                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                       
/* 1088 */                       _jspx_th_c_005fwhen_005f3.setTest("${( param.method=='configureHostDiscovery' || param.method=='associateMonitors'||param.method=='getMonitorForm'||param.method=='addResource')&& (empty showwiz3) && (empty UrlForm) }");
/* 1089 */                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1090 */                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                         for (;;) {
/* 1092 */                           out.write("\n    \n\t\n\t\t\n\t\t");
/*      */                           
/* 1094 */                           SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1095 */                           _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 1096 */                           _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                           
/* 1098 */                           _jspx_th_c_005fset_005f8.setVar("wizimage");
/* 1099 */                           int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 1100 */                           if (_jspx_eval_c_005fset_005f8 != 0) {
/* 1101 */                             if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1102 */                               out = _jspx_page_context.pushBody();
/* 1103 */                               _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 1104 */                               _jspx_th_c_005fset_005f8.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1107 */                               out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1108 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1109 */                               out.write(" </b></font>\n    \t");
/* 1110 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 1111 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1114 */                             if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1115 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1118 */                           if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 1119 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                           }
/*      */                           
/* 1122 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 1123 */                           out.write("\n   ");
/* 1124 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1125 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1129 */                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1130 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                       }
/*      */                       
/* 1133 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1134 */                       out.write("\n   ");
/*      */                       
/* 1136 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1137 */                       _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1138 */                       _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 1139 */                       int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1140 */                       if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                         for (;;) {
/* 1142 */                           out.write("  \n    \t\n\t\t");
/*      */                           
/* 1144 */                           SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1145 */                           _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 1146 */                           _jspx_th_c_005fset_005f9.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                           
/* 1148 */                           _jspx_th_c_005fset_005f9.setVar("wizimage");
/* 1149 */                           int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 1150 */                           if (_jspx_eval_c_005fset_005f9 != 0) {
/* 1151 */                             if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1152 */                               out = _jspx_page_context.pushBody();
/* 1153 */                               _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 1154 */                               _jspx_th_c_005fset_005f9.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1157 */                               out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1158 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1159 */                               out.write(" </font>\n    \t");
/* 1160 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 1161 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1164 */                             if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1165 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1168 */                           if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 1169 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                           }
/*      */                           
/* 1172 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 1173 */                           out.write("\n\t\n\t\t\n   ");
/* 1174 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1175 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1179 */                       if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1180 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                       }
/*      */                       
/* 1183 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1184 */                       out.write(10);
/* 1185 */                       out.write(32);
/* 1186 */                       out.write(32);
/* 1187 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1188 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1192 */                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1193 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                   }
/*      */                   
/* 1196 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1197 */                   out.write(" \n\n    \n     <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"20%\">\n    ");
/* 1198 */                   if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1200 */                   out.write("\n    \t");
/* 1201 */                   if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1203 */                   out.write("\n    \t\n    \t");
/* 1204 */                   if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1206 */                   out.write("\n    \t</td>    \t\n    \t<!-- ############################################# check for third tab #####################################  -->\n       ");
/*      */                   
/* 1208 */                   ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1209 */                   _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1210 */                   _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f6);
/* 1211 */                   int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1212 */                   if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                     for (;;) {
/* 1214 */                       out.write("\n       ");
/*      */                       
/* 1216 */                       WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1217 */                       _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1218 */                       _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                       
/* 1220 */                       _jspx_th_c_005fwhen_005f4.setTest("${(param.method=='reloadHostDiscoveryForm'|| param.method=='getMonitorForm'||param.method=='addResource'|| (!empty showwiz3) || (!empty UrlForm) ) && (param.method!='getHAProfiles') }");
/* 1221 */                       int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1222 */                       if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                         for (;;) {
/* 1224 */                           out.write("\n   \n   \t    \t");
/*      */                           
/* 1226 */                           SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1227 */                           _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 1228 */                           _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                           
/* 1230 */                           _jspx_th_c_005fset_005f10.setVar("wizimage");
/* 1231 */                           int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 1232 */                           if (_jspx_eval_c_005fset_005f10 != 0) {
/* 1233 */                             if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1234 */                               out = _jspx_page_context.pushBody();
/* 1235 */                               _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 1236 */                               _jspx_th_c_005fset_005f10.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1239 */                               out.write("\n   \t    \t<img src=\"/images/new_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b> ");
/* 1240 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1241 */                               out.write(" </b></font>\n   \t    \t");
/* 1242 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 1243 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1246 */                             if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1247 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1250 */                           if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 1251 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                           }
/*      */                           
/* 1254 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 1255 */                           out.write("\n       ");
/* 1256 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1257 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1261 */                       if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1262 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                       }
/*      */                       
/* 1265 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1266 */                       out.write("\n        ");
/*      */                       
/* 1268 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1269 */                       _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1270 */                       _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1271 */                       int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1272 */                       if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                         for (;;) {
/* 1274 */                           out.write("  \n   \t    \t");
/*      */                           
/* 1276 */                           SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1277 */                           _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 1278 */                           _jspx_th_c_005fset_005f11.setParent(_jspx_th_c_005fotherwise_005f4);
/*      */                           
/* 1280 */                           _jspx_th_c_005fset_005f11.setVar("wizimage");
/* 1281 */                           int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 1282 */                           if (_jspx_eval_c_005fset_005f11 != 0) {
/* 1283 */                             if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1284 */                               out = _jspx_page_context.pushBody();
/* 1285 */                               _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 1286 */                               _jspx_th_c_005fset_005f11.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1289 */                               out.write("\n\t\t   \t    \t<img src=\"/images/new_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1290 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1291 */                               out.write(" </font>\n   \t    \t");
/* 1292 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 1293 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1296 */                             if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1297 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1300 */                           if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 1301 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11); return;
/*      */                           }
/*      */                           
/* 1304 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 1305 */                           out.write("\n   \t");
/* 1306 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1307 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1311 */                       if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1312 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                       }
/*      */                       
/* 1315 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1316 */                       out.write(10);
/* 1317 */                       out.write(32);
/* 1318 */                       out.write(32);
/* 1319 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1320 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1324 */                   if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1325 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                   }
/*      */                   
/* 1328 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1329 */                   out.write(" \n\n        <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n       <td width=\"18%\">\n       ");
/* 1330 */                   if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1332 */                   out.write("\n       ");
/* 1333 */                   if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1335 */                   out.write("\n       ");
/* 1336 */                   out.write("\n       \t");
/* 1337 */                   if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1339 */                   out.write("\n    \t</td>\n   \n  <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"17%\">\n\t");
/*      */                   
/* 1341 */                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1342 */                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 1343 */                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1345 */                   _jspx_th_c_005fif_005f13.setTest("${param.method=='getHAProfiles'}");
/* 1346 */                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 1347 */                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                     for (;;) {
/* 1349 */                       out.write(10);
/* 1350 */                       out.write(9);
/*      */                       
/* 1352 */                       SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1353 */                       _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 1354 */                       _jspx_th_c_005fset_005f12.setParent(_jspx_th_c_005fif_005f13);
/*      */                       
/* 1356 */                       _jspx_th_c_005fset_005f12.setVar("wizimage");
/* 1357 */                       int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 1358 */                       if (_jspx_eval_c_005fset_005f12 != 0) {
/* 1359 */                         if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1360 */                           out = _jspx_page_context.pushBody();
/* 1361 */                           _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 1362 */                           _jspx_th_c_005fset_005f12.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1365 */                           out.write("\n\t\t<img src=\"/images/wiz_configurealerts_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b>");
/* 1366 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1367 */                           out.write(" </b></font>\n    \t");
/* 1368 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 1369 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1372 */                         if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1373 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1376 */                       if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 1377 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12); return;
/*      */                       }
/*      */                       
/* 1380 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 1381 */                       out.write(10);
/* 1382 */                       out.write(9);
/* 1383 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 1384 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1388 */                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 1389 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                   }
/*      */                   
/* 1392 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1393 */                   out.write(10);
/* 1394 */                   out.write(9);
/*      */                   
/* 1396 */                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1397 */                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 1398 */                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1400 */                   _jspx_th_c_005fif_005f14.setTest("${param.method!='getHAProfiles'}");
/* 1401 */                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 1402 */                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                     for (;;) {
/* 1404 */                       out.write(10);
/* 1405 */                       out.write(9);
/*      */                       
/* 1407 */                       SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1408 */                       _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 1409 */                       _jspx_th_c_005fset_005f13.setParent(_jspx_th_c_005fif_005f14);
/*      */                       
/* 1411 */                       _jspx_th_c_005fset_005f13.setVar("wizimage");
/* 1412 */                       int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 1413 */                       if (_jspx_eval_c_005fset_005f13 != 0) {
/* 1414 */                         if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1415 */                           out = _jspx_page_context.pushBody();
/* 1416 */                           _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 1417 */                           _jspx_th_c_005fset_005f13.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1420 */                           out.write("\n\t\t<img src=\"/images/wiz_configurealerts_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1421 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1422 */                           out.write(" </font>\n    \t");
/* 1423 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 1424 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1427 */                         if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1428 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1431 */                       if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 1432 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13); return;
/*      */                       }
/*      */                       
/* 1435 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 1436 */                       out.write("\n\t\n\t");
/* 1437 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 1438 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1442 */                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 1443 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                   }
/*      */                   
/* 1446 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 1447 */                   out.write(10);
/* 1448 */                   if (_jspx_meth_c_005fif_005f15(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1450 */                   out.write("   \n ");
/* 1451 */                   if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1453 */                   out.write(10);
/* 1454 */                   out.write(32);
/* 1455 */                   out.write(10);
/* 1456 */                   if (_jspx_meth_c_005fif_005f16(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1458 */                   out.write("   \n    </td>\n    \n    <td width=\"2%\" rowspan=\"2\" valign=\"bottom\" align=\"right\"><img src=\"/images/wiz_endicon.gif\" width=\"33\" height=\"36\"></td>\n  </tr>\n  <tr background=\"/images/wiz_bg_graylind.gif\"> \n    <td><img src=\"/images/wiz_startimage.gif\" width=\"18\" height=\"16\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n   \n   <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n \n");
/* 1459 */                   out.write("  </tr>\n\n</table>\n</td></tr>\n");
/* 1460 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1461 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1465 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1466 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/* 1469 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1470 */               out.write(10);
/* 1471 */               out.write(10);
/* 1472 */               if (request.getAttribute("EmailForm") == null) {
/* 1473 */                 out.write(10);
/*      */                 
/* 1475 */                 MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1476 */                 _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 1477 */                 _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                 
/* 1479 */                 _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                 
/* 1481 */                 _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 1482 */                 int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 1483 */                 if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 1484 */                   String msg = null;
/* 1485 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1486 */                     out = _jspx_page_context.pushBody();
/* 1487 */                     _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 1488 */                     _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                   }
/* 1490 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/* 1492 */                     out.write(" \n<tr> \n  <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 1493 */                     if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                       return;
/* 1495 */                     out.write("</td>\n\t  </tr>\n\t</table>\n\t<br></td>\n</tr>\n");
/* 1496 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 1497 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/* 1498 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1501 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1502 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1505 */                 if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 1506 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                 }
/*      */                 
/* 1509 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */               }
/* 1511 */               out.write(32);
/*      */               
/* 1513 */               org.apache.struts.taglib.logic.MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (org.apache.struts.taglib.logic.MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(org.apache.struts.taglib.logic.MessagesPresentTag.class);
/* 1514 */               _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 1515 */               _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */               
/* 1517 */               _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 1518 */               int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 1519 */               if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 1521 */                   out.write(" \n<tr> \n  <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\"> ");
/*      */                   
/* 1523 */                   MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1524 */                   _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 1525 */                   _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                   
/* 1527 */                   _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                   
/* 1529 */                   _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 1530 */                   int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 1531 */                   if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 1532 */                     String msg = null;
/* 1533 */                     if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1534 */                       out = _jspx_page_context.pushBody();
/* 1535 */                       _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 1536 */                       _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                     }
/* 1538 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/* 1540 */                       out.write("\n\t  ");
/* 1541 */                       if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                         return;
/* 1543 */                       out.write("<br>\n\t  ");
/* 1544 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 1545 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/* 1546 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1549 */                     if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1550 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1553 */                   if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 1554 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                   }
/*      */                   
/* 1557 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 1558 */                   out.write(" </td>\n\t  </tr>\n\t</table></td>\n</tr>\n");
/* 1559 */                   int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 1560 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1564 */               if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 1565 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */               }
/*      */               
/* 1568 */               this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 1569 */               out.write("\n</table>\n<script>\n\tobject=location.href;\n\t\n\tif(document.AMActionForm!=null)\n\t{\t  \n\t  if(object.match(\"EMailActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if(object.match(\"SMSActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if(object.match(\"ExecProgramActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=11\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=12\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\t  \n\t  else if(object.match(\"showLogTicket\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=6;\n\t  }\n\t  else if(object.match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=5;\n\t  }\t\n\t  else if(object.match(\"jre\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=7;\n");
/* 1570 */               out.write("\t  }\n\t  else if(object.match(\"showVMAction\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=8;\n\t  }\n\t  else if(object.match(\"amazon\")!=null)\n\t  {\n\t\t    document.AMActionForm.actions.selectedIndex=9;\n\t }\t  \n\t}\n\telse if(document.MBeanOperationActionForm!=null)\n\t{\n\t  document.MBeanOperationActionForm.actions.selectedIndex=5;\t  \n\t}\n</script>\n</td>\n</tr>\n</table>\n");
/* 1571 */               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1572 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1576 */           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1577 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */           }
/*      */           
/* 1580 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1581 */           out.write(10);
/* 1582 */           out.write(10);
/*      */           
/* 1584 */           IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1585 */           _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 1586 */           _jspx_th_c_005fif_005f17.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1588 */           _jspx_th_c_005fif_005f17.setTest("${!empty param.returnpath}");
/* 1589 */           int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 1590 */           if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */             for (;;) {
/* 1592 */               out.write("\n<input name=\"returnpath\" type=\"hidden\" value=\"");
/* 1593 */               out.print(request.getParameter("returnpath"));
/* 1594 */               out.write(34);
/* 1595 */               out.write(62);
/* 1596 */               out.write(10);
/* 1597 */               int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 1598 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1602 */           if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 1603 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */           }
/*      */           
/* 1606 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 1607 */           out.write(32);
/*      */           
/* 1609 */           IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1610 */           _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 1611 */           _jspx_th_c_005fif_005f18.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1613 */           _jspx_th_c_005fif_005f18.setTest("${!empty param.haid}");
/* 1614 */           int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 1615 */           if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */             for (;;) {
/* 1617 */               out.write("\n<input name=\"haid\" type=\"hidden\" value=\"");
/* 1618 */               out.print(request.getParameter("haid"));
/* 1619 */               out.write(34);
/* 1620 */               out.write(62);
/* 1621 */               out.write(10);
/* 1622 */               int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 1623 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1627 */           if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 1628 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */           }
/*      */           
/* 1631 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 1632 */           out.write(32);
/* 1633 */           if (_jspx_meth_logic_005fequal_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1635 */           out.write(32);
/*      */           
/* 1637 */           NotEqualTag _jspx_th_logic_005fnotEqual_005f0 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(NotEqualTag.class);
/* 1638 */           _jspx_th_logic_005fnotEqual_005f0.setPageContext(_jspx_page_context);
/* 1639 */           _jspx_th_logic_005fnotEqual_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1641 */           _jspx_th_logic_005fnotEqual_005f0.setName("AMActionForm");
/*      */           
/* 1643 */           _jspx_th_logic_005fnotEqual_005f0.setProperty("method");
/*      */           
/* 1645 */           _jspx_th_logic_005fnotEqual_005f0.setValue("editSendTrapAction");
/* 1646 */           int _jspx_eval_logic_005fnotEqual_005f0 = _jspx_th_logic_005fnotEqual_005f0.doStartTag();
/* 1647 */           if (_jspx_eval_logic_005fnotEqual_005f0 != 0) {
/*      */             for (;;) {
/* 1649 */               out.write("\n<input name=\"method\" type=\"hidden\" value=\"createSendTrapAction\">\n<input name=\"redirectTo\" type=\"hidden\" value=\"");
/* 1650 */               out.print(request.getParameter("redirectTo"));
/* 1651 */               out.write(34);
/* 1652 */               out.write(62);
/* 1653 */               out.write(10);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1737 */               out.write(10);
/* 1738 */               int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f0.doAfterBody();
/* 1739 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1743 */           if (_jspx_th_logic_005fnotEqual_005f0.doEndTag() == 5) {
/* 1744 */             this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0); return;
/*      */           }
/*      */           
/* 1747 */           this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/* 1748 */           out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"70%\" valign=\"top\">\t\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n  <td width=\"4%\" class=\"tableheading-monitor-config\"><img src=\"/images/trap.png\" class=\"tableheading-add-icon\"></td>\n\n    <td width=\"96%\" height=\"31\" class=\"tableheading-monitor-config\"> ");
/*      */           
/* 1750 */           EqualTag _jspx_th_logic_005fequal_005f1 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 1751 */           _jspx_th_logic_005fequal_005f1.setPageContext(_jspx_page_context);
/* 1752 */           _jspx_th_logic_005fequal_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1754 */           _jspx_th_logic_005fequal_005f1.setName("AMActionForm");
/*      */           
/* 1756 */           _jspx_th_logic_005fequal_005f1.setProperty("method");
/*      */           
/* 1758 */           _jspx_th_logic_005fequal_005f1.setValue("editSendTrapAction");
/* 1759 */           int _jspx_eval_logic_005fequal_005f1 = _jspx_th_logic_005fequal_005f1.doStartTag();
/* 1760 */           if (_jspx_eval_logic_005fequal_005f1 != 0) {
/*      */             for (;;) {
/* 1762 */               out.write("\n      ");
/* 1763 */               out.print(FormatUtil.getString("am.webclient.newaction.editactionttrap"));
/* 1764 */               out.write(32);
/* 1765 */               if (_jspx_meth_html_005fhidden_005f1(_jspx_th_logic_005fequal_005f1, _jspx_page_context))
/*      */                 return;
/* 1767 */               out.write(32);
/* 1768 */               int evalDoAfterBody = _jspx_th_logic_005fequal_005f1.doAfterBody();
/* 1769 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1773 */           if (_jspx_th_logic_005fequal_005f1.doEndTag() == 5) {
/* 1774 */             this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1); return;
/*      */           }
/*      */           
/* 1777 */           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 1778 */           out.write("\n      ");
/*      */           
/* 1780 */           NotEqualTag _jspx_th_logic_005fnotEqual_005f1 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(NotEqualTag.class);
/* 1781 */           _jspx_th_logic_005fnotEqual_005f1.setPageContext(_jspx_page_context);
/* 1782 */           _jspx_th_logic_005fnotEqual_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1784 */           _jspx_th_logic_005fnotEqual_005f1.setName("AMActionForm");
/*      */           
/* 1786 */           _jspx_th_logic_005fnotEqual_005f1.setProperty("method");
/*      */           
/* 1788 */           _jspx_th_logic_005fnotEqual_005f1.setValue("editSendTrapAction");
/* 1789 */           int _jspx_eval_logic_005fnotEqual_005f1 = _jspx_th_logic_005fnotEqual_005f1.doStartTag();
/* 1790 */           if (_jspx_eval_logic_005fnotEqual_005f1 != 0) {
/*      */             for (;;) {
/* 1792 */               out.write("\n      ");
/* 1793 */               out.print(FormatUtil.getString("am.webclient.newaction.createactionttrap"));
/* 1794 */               out.write("\n      ");
/*      */               
/* 1796 */               if (!isInvokedFromPopup)
/*      */               {
/*      */ 
/* 1799 */                 out.write("\n      ");
/*      */                 
/* 1801 */                 SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 1802 */                 _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 1803 */                 _jspx_th_html_005fselect_005f0.setParent(_jspx_th_logic_005fnotEqual_005f1);
/*      */                 
/* 1805 */                 _jspx_th_html_005fselect_005f0.setProperty("snmpVersionList");
/*      */                 
/* 1807 */                 _jspx_th_html_005fselect_005f0.setStyleClass("formtext small");
/*      */                 
/* 1809 */                 _jspx_th_html_005fselect_005f0.setOnchange("javascript:reloadForm()");
/* 1810 */                 int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 1811 */                 if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 1812 */                   if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1813 */                     out = _jspx_page_context.pushBody();
/* 1814 */                     _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 1815 */                     _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1818 */                     out.write("\n      ");
/*      */                     
/* 1820 */                     for (int i = 0; i < versions.length; i++)
/*      */                     {
/*      */ 
/* 1823 */                       out.write("\n      ");
/*      */                       
/* 1825 */                       OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1826 */                       _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 1827 */                       _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/* 1829 */                       _jspx_th_html_005foption_005f0.setValue(versionValues[i]);
/* 1830 */                       int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 1831 */                       if (_jspx_eval_html_005foption_005f0 != 0) {
/* 1832 */                         if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1833 */                           out = _jspx_page_context.pushBody();
/* 1834 */                           _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 1835 */                           _jspx_th_html_005foption_005f0.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1838 */                           out.write(32);
/* 1839 */                           out.print(versions[i]);
/* 1840 */                           out.write(32);
/* 1841 */                           int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 1842 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1845 */                         if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1846 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1849 */                       if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 1850 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                       }
/*      */                       
/* 1853 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 1854 */                       out.write("\n      ");
/*      */                     }
/*      */                     
/*      */ 
/* 1858 */                     out.write("\n      ");
/* 1859 */                     int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 1860 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1863 */                   if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1864 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1867 */                 if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 1868 */                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                 }
/*      */                 
/* 1871 */                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 1872 */                 out.write("\n      ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/* 1877 */                 String version = "v1";
/* 1878 */                 if (snmpversion.equals("12"))
/*      */                 {
/* 1880 */                   version = "v2C";
/*      */                 }
/*      */                 
/* 1883 */                 out.write("\n      ");
/*      */                 
/* 1885 */                 HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 1886 */                 _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 1887 */                 _jspx_th_html_005fhidden_005f2.setParent(_jspx_th_logic_005fnotEqual_005f1);
/*      */                 
/* 1889 */                 _jspx_th_html_005fhidden_005f2.setProperty("snmpVersionList");
/*      */                 
/* 1891 */                 _jspx_th_html_005fhidden_005f2.setValue(snmpversion);
/* 1892 */                 int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 1893 */                 if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 1894 */                   this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2); return;
/*      */                 }
/*      */                 
/* 1897 */                 this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 1898 */                 out.write(32);
/* 1899 */                 out.print(version);
/* 1900 */                 out.write("\n      ");
/*      */               }
/*      */               
/*      */ 
/* 1904 */               out.write("\n      ");
/* 1905 */               int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f1.doAfterBody();
/* 1906 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1910 */           if (_jspx_th_logic_005fnotEqual_005f1.doEndTag() == 5) {
/* 1911 */             this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f1); return;
/*      */           }
/*      */           
/* 1914 */           this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f1);
/* 1915 */           out.write(" </td>\n  </tr>\n</table>\n");
/* 1916 */           if (_jspx_meth_html_005fhidden_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1918 */           out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n    <td width=\"25%\" class=\"bodytext label-align\">");
/* 1919 */           out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/* 1920 */           out.write("</td>\n    <td colspan=\"2\" class=\"bodytext\"> ");
/* 1921 */           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1923 */           out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"bodytext label-align\">");
/* 1924 */           out.print(FormatUtil.getString("am.webclient.newaction.destinationaddress"));
/* 1925 */           out.write("</td>\n    <td colspan=\"2\" class=\"bodytext\">");
/* 1926 */           if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1928 */           out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"bodytext label-align\">");
/* 1929 */           out.print(FormatUtil.getString("am.webclient.newaction.destinationport"));
/* 1930 */           out.write("</td>\n    <td colspan=\"2\" class=\"bodytext\">");
/* 1931 */           if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1933 */           out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"bodytext label-align\" width=\"25%\">");
/* 1934 */           out.print(FormatUtil.getString("am.webclient.newaction.trapoid"));
/* 1935 */           out.write("</td>\n    <td width=\"30%\" class=\"bodytext\"> ");
/* 1936 */           if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1938 */           out.write("\n    </td>\n    ");
/*      */           
/* 1940 */           if (!isInvokedFromPopup)
/*      */           {
/*      */ 
/* 1943 */             String querString = "";
/* 1944 */             if ((path != null) && (oid != null))
/*      */             {
/* 1946 */               querString = "?pressName=" + oid + "&pressType=Treelink&type=" + oid + "&nodesToOpen=" + java.net.URLEncoder.encode(path);
/*      */             }
/* 1948 */             String field = FormatUtil.getString("am.webclient.sendtrap.field.text");
/* 1949 */             if (snmpversion.equals("12"))
/*      */             {
/* 1951 */               field = FormatUtil.getString("am.webclient.sendtrap.field2.text");
/*      */             }
/*      */             
/* 1954 */             out.write("\n\n    <td width=\"45%\" class=\"bodytext\">&nbsp;");
/* 1955 */             out.print(FormatUtil.getString("am.webclient.newaction.trapmibnote1", new String[] { field }));
/* 1956 */             out.write(" <a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"javascript:fnOpenWindow('/jsp/MibBrowser.jsp");
/* 1957 */             out.print(querString);
/* 1958 */             out.write("')\" >");
/* 1959 */             out.print(FormatUtil.getString("am.webclient.newaction.trapmibnote2"));
/* 1960 */             out.write("</a><br>&nbsp;");
/* 1961 */             out.print(FormatUtil.getString("am.webclient.newaction.trapmibnote3"));
/* 1962 */             out.write(" <a class=\"staticlinks\" href=\"/Upload.do?uploadDir=./mibs/&returnpath=");
/* 1963 */             out.print(java.net.URLEncoder.encode("/adminAction.do?method=reloadSendTrapActionForm"));
/* 1964 */             out.write(34);
/* 1965 */             out.write(32);
/* 1966 */             out.write(62);
/* 1967 */             out.print(FormatUtil.getString("am.webclient.newaction.trapmibnote4"));
/* 1968 */             out.write("</td>\n\t");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 1974 */             out.write("\n    <td width=\"28%\" class=\"bodytext\">&nbsp;</td>\n    ");
/*      */           }
/*      */           
/*      */ 
/* 1978 */           out.write("\n  </tr>\n \n  ");
/* 1979 */           if (_jspx_meth_html_005fhidden_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1981 */           out.write("\n  <!--tr>\n\t   <td class=\"bodytext\">Mib Name</td>\n\t   <td class=\"bodytext\">");
/* 1982 */           if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1984 */           out.write("</td>\n\t   <td class=\"bodytext\">&nbsp;<i>Multiple mibs can be specified, separated by a blank space</i></td>\n\t   </tr-->\n  <tr>\n    <td class=\"bodytext label-align\">");
/* 1985 */           out.print(FormatUtil.getString("am.webclient.newaction.trapmessage"));
/* 1986 */           out.write("</td>\n    <td class=\"bodytext\">");
/* 1987 */           if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1989 */           out.write("</td>\n    ");
/*      */           
/* 1991 */           if (!isInvokedFromPopup)
/*      */           {
/*      */ 
/* 1994 */             out.write("\n      <td class=\"bodytext\">&nbsp;");
/* 1995 */             out.print(FormatUtil.getString("am.webclient.newaction.traptag"));
/* 1996 */             out.write("&nbsp;<a href=\"help/alerts/replaceable_tags.html\" target=\"_blank\" class=\"selectedmenu\">");
/* 1997 */             out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.morehelp"));
/* 1998 */             out.write("&gt;&gt;</a></td>\n      ");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 2004 */             out.write("\n      <td class=\"bodytext\">&nbsp;</td>\n      ");
/*      */           }
/*      */           
/*      */ 
/* 2008 */           out.write("\n\n  </tr>\n\n<tr>\n\t<td class=\"bodytext label-align\">");
/* 2009 */           out.print(FormatUtil.getString("am.webclient.newaction.trap.messageformat"));
/* 2010 */           out.write("</td>\n\t<td colspan=\"2\" class=\"bodytext\">");
/* 2011 */           if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2013 */           out.print(FormatUtil.getString("am.webclient.newaction.plaintext"));
/* 2014 */           out.write("&nbsp;&nbsp;&nbsp;&nbsp;");
/* 2015 */           if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2017 */           out.print(FormatUtil.getString("am.webclient.newaction.html"));
/* 2018 */           out.write("</td>\n\n</tr>\n  ");
/*      */           
/* 2020 */           if (snmpversion.equals("11"))
/*      */           {
/*      */ 
/* 2023 */             out.write(10);
/* 2024 */             out.write(32);
/* 2025 */             out.write(32);
/* 2026 */             out.write("<!-- $Id$ -->\n\n");
/*      */             
/* 2028 */             String[] type = { "0", "1", "2", "3", "4", "5", "6" };
/* 2029 */             String[] displayString = { "coldStart", "warmStart", "linkDown", "linkUp", "authenticationFailure", "egpNeighbourLoss", "enterpriseSpecific" };
/*      */             
/* 2031 */             out.write("\n<script>\nfunction checkType()\n{\n\tif(document.AMActionForm.v1TrapGenericType.value==6)\n\t{\n\t\tshowDiv('specifictype');\n\t}\n\telse\n\t{\n\t\thideDiv('specifictype');\n\t}\n}\n/*onload = function()\n{\n\tif(document.AMActionForm.v1TrapGenericType.value==6)\n\t{\n\t\tshowDiv('specifictype');\n\t}\n\telse\n\t{\n\t\thideDiv('specifictype');\n\t}\n}*/\n\n</script>\n <tr>\n    <td class=\"bodytext label-align\">");
/* 2032 */             out.print(FormatUtil.getString("am.webclient.newaction.trapcommunity"));
/* 2033 */             out.write("</td>\n    <td class=\"bodytext\">");
/* 2034 */             if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2036 */             out.write("</td>\n    <td class=\"bodytext\">&nbsp;</td>\n  </tr>\n<tr>\n     <td class=\"bodytext label-align\">");
/* 2037 */             out.print(FormatUtil.getString("am.webclient.newaction.generictype"));
/* 2038 */             out.write("</td>\n      <td colspan=\"2\" class=\"bodytext\">\n      ");
/*      */             
/* 2040 */             SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 2041 */             _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 2042 */             _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 2044 */             _jspx_th_html_005fselect_005f1.setProperty("v1TrapGenericType");
/*      */             
/* 2046 */             _jspx_th_html_005fselect_005f1.setStyleClass("formtext normal");
/*      */             
/* 2048 */             _jspx_th_html_005fselect_005f1.setOnchange("javascript:checkType()");
/* 2049 */             int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 2050 */             if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 2051 */               if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 2052 */                 out = _jspx_page_context.pushBody();
/* 2053 */                 _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 2054 */                 _jspx_th_html_005fselect_005f1.doInitBody();
/*      */               }
/*      */               for (;;) {
/* 2057 */                 out.write("\n      ");
/*      */                 
/* 2059 */                 for (int i = 0; i < type.length; i++)
/*      */                 {
/*      */ 
/* 2062 */                   out.write("\n         ");
/*      */                   
/* 2064 */                   OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2065 */                   _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2066 */                   _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f1);
/*      */                   
/* 2068 */                   _jspx_th_html_005foption_005f1.setValue(type[i]);
/* 2069 */                   int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2070 */                   if (_jspx_eval_html_005foption_005f1 != 0) {
/* 2071 */                     if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2072 */                       out = _jspx_page_context.pushBody();
/* 2073 */                       _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 2074 */                       _jspx_th_html_005foption_005f1.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 2077 */                       out.write(32);
/* 2078 */                       out.print(displayString[i]);
/* 2079 */                       out.write("\n         ");
/* 2080 */                       int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 2081 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 2084 */                     if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2085 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 2088 */                   if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2089 */                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                   }
/*      */                   
/* 2092 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 2093 */                   out.write("\n      ");
/*      */                 }
/*      */                 
/*      */ 
/* 2097 */                 out.write("\n      ");
/* 2098 */                 int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 2099 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 2102 */               if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 2103 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 2106 */             if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 2107 */               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */             }
/*      */             
/* 2110 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 2111 */             out.write("\n      </td>\n</tr>\n<tr>\n\t  <td width=\"100%\" colspan=\"3\">\n\t  <div id=\"specifictype\" style=\"display:none\">\n\t  <table width=\"99%\">\n\t  <tr>\n     <td class=\"bodytext label-align\" width=\"25%\">");
/* 2112 */             out.print(FormatUtil.getString("am.webclient.newaction.specifictype"));
/* 2113 */             out.write("</td>\n      <td class=\"bodytext\">");
/* 2114 */             if (_jspx_meth_html_005ftext_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2116 */             out.write("</td>\n\t  </tr>\n\t  </table>\n\t  </div>\n\t  </td>\n</tr>\n<tr>\n     <td class=\"bodytext label-align\">");
/* 2117 */             out.print(FormatUtil.getString("am.webclient.newaction.enterprise"));
/* 2118 */             out.write("</td>\n      <td colspan=\"2\" class=\"bodytext\">");
/* 2119 */             if (_jspx_meth_html_005ftext_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2121 */             out.write("</td>\n</tr>\n");
/* 2122 */             out.write(10);
/* 2123 */             out.write(32);
/* 2124 */             out.write(32);
/*      */ 
/*      */           }
/* 2127 */           else if (snmpversion.equals("12"))
/*      */           {
/*      */ 
/* 2130 */             out.write(10);
/* 2131 */             out.write(32);
/* 2132 */             out.write(32);
/* 2133 */             out.write("\n\n <tr>\n    <td class=\"bodytext label-align\">");
/* 2134 */             out.print(FormatUtil.getString("am.webclient.newaction.trapcommunity"));
/* 2135 */             out.write("</td>\n    <td class=\"bodytext\">");
/* 2136 */             if (_jspx_meth_html_005ftext_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2138 */             out.write("</td>\n    <td class=\"bodytext\">&nbsp;</td>\n  </tr>\n<tr> \n     <td class=\"bodytext label-align\">");
/* 2139 */             out.print(FormatUtil.getString("am.webclient.newaction.snmptrapoid"));
/* 2140 */             out.write("</td>\n      <td colspan=\"2\" class=\"bodytext\">");
/* 2141 */             if (_jspx_meth_html_005ftext_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2143 */             out.write("</td>\n</tr>\n");
/* 2144 */             out.write(10);
/* 2145 */             out.write(32);
/* 2146 */             out.write(32);
/*      */ 
/*      */           }
/* 2149 */           else if (snmpversion.equals("13"))
/*      */           {
/*      */ 
/* 2152 */             out.write(10);
/* 2153 */             out.write(32);
/* 2154 */             out.write(32);
/* 2155 */             out.write("\n\n<tr> \n     <td class=\"bodytext label-align\">");
/* 2156 */             out.print(FormatUtil.getString("am.webclient.newaction.snmptrapoid"));
/* 2157 */             out.write("</td>\n     <td colspan=\"2\" class=\"bodytext\">");
/* 2158 */             if (_jspx_meth_html_005ftext_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2160 */             out.write("</td>\n</tr>\n<tr>\n     <td class=\"bodytext label-align\">");
/* 2161 */             out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.userName"));
/* 2162 */             out.write("</td>\n      <td colspan=\"2\" class=\"bodytext\">");
/* 2163 */             if (_jspx_meth_html_005ftext_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2165 */             out.write("</td>\n</tr>\n<tr>\n     <td class=\"bodytext label-align\">");
/* 2166 */             out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.contextName"));
/* 2167 */             out.write("</td>\n      <td colspan=\"2\" class=\"bodytext\">");
/* 2168 */             if (_jspx_meth_html_005ftext_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2170 */             out.write("</td>\n</tr>\n<tr>\n     <td class=\"bodytext label-align\">");
/* 2171 */             out.print(FormatUtil.getString("am.webclient.sendTrap.EngineID"));
/* 2172 */             out.write("</td>\n      <td colspan=\"2\" class=\"bodytext\">");
/* 2173 */             if (_jspx_meth_html_005ftext_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2175 */             out.write("</td>\n</tr>\n");
/* 2176 */             out.write(10);
/* 2177 */             out.write(32);
/* 2178 */             out.write(32);
/*      */           }
/*      */           
/*      */ 
/* 2182 */           out.write("\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"25%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"75%\" height=\"31\" class=\"tablebottom\"> ");
/*      */           
/* 2184 */           EqualTag _jspx_th_logic_005fequal_005f2 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 2185 */           _jspx_th_logic_005fequal_005f2.setPageContext(_jspx_page_context);
/* 2186 */           _jspx_th_logic_005fequal_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2188 */           _jspx_th_logic_005fequal_005f2.setName("AMActionForm");
/*      */           
/* 2190 */           _jspx_th_logic_005fequal_005f2.setProperty("method");
/*      */           
/* 2192 */           _jspx_th_logic_005fequal_005f2.setValue("editSendTrapAction");
/* 2193 */           int _jspx_eval_logic_005fequal_005f2 = _jspx_th_logic_005fequal_005f2.doStartTag();
/* 2194 */           if (_jspx_eval_logic_005fequal_005f2 != 0) {
/*      */             for (;;) {
/* 2196 */               out.write("\n      <input name=\"button\" value=\"");
/* 2197 */               out.print(FormatUtil.getString("am.webclient.newaction.updateaction"));
/* 2198 */               out.write("\" type=\"button\" class=\"buttons btn_highlt\"  onClick=\"javascript:validateAndSubmit(1)\">\n      ");
/* 2199 */               int evalDoAfterBody = _jspx_th_logic_005fequal_005f2.doAfterBody();
/* 2200 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2204 */           if (_jspx_th_logic_005fequal_005f2.doEndTag() == 5) {
/* 2205 */             this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2); return;
/*      */           }
/*      */           
/* 2208 */           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2);
/* 2209 */           out.write(32);
/*      */           
/* 2211 */           NotEqualTag _jspx_th_logic_005fnotEqual_005f2 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(NotEqualTag.class);
/* 2212 */           _jspx_th_logic_005fnotEqual_005f2.setPageContext(_jspx_page_context);
/* 2213 */           _jspx_th_logic_005fnotEqual_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2215 */           _jspx_th_logic_005fnotEqual_005f2.setName("AMActionForm");
/*      */           
/* 2217 */           _jspx_th_logic_005fnotEqual_005f2.setProperty("method");
/*      */           
/* 2219 */           _jspx_th_logic_005fnotEqual_005f2.setValue("editSendTrapAction");
/* 2220 */           int _jspx_eval_logic_005fnotEqual_005f2 = _jspx_th_logic_005fnotEqual_005f2.doStartTag();
/* 2221 */           if (_jspx_eval_logic_005fnotEqual_005f2 != 0) {
/*      */             for (;;) {
/* 2223 */               out.write("\n      ");
/*      */               
/* 2225 */               IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2226 */               _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 2227 */               _jspx_th_c_005fif_005f19.setParent(_jspx_th_logic_005fnotEqual_005f2);
/*      */               
/* 2229 */               _jspx_th_c_005fif_005f19.setTest("${!empty param.returnpath}");
/* 2230 */               int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 2231 */               if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                 for (;;) {
/* 2233 */                   out.write("\n      <input name=\"button\" value=\"");
/* 2234 */                   out.print(FormatUtil.getString("am.webclient.newaction.createbutton"));
/* 2235 */                   out.write("\" type=\"button\" class=\"buttons btn_highlt\"  onClick=\"javascript:validateAndSubmit(1)\">\n      ");
/* 2236 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 2237 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2241 */               if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 2242 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */               }
/*      */               
/* 2245 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 2246 */               out.write(32);
/*      */               
/* 2248 */               IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2249 */               _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 2250 */               _jspx_th_c_005fif_005f20.setParent(_jspx_th_logic_005fnotEqual_005f2);
/*      */               
/* 2252 */               _jspx_th_c_005fif_005f20.setTest("${empty param.returnpath}");
/* 2253 */               int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 2254 */               if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                 for (;;) {
/* 2256 */                   out.write("\n      ");
/*      */                   
/* 2258 */                   String butText = FormatUtil.getString("am.webclient.sendtrap.button.text");
/* 2259 */                   if (isInvokedFromPopup)
/*      */                   {
/* 2261 */                     butText = FormatUtil.getString("am.webclient.sendtrap.button1.text");
/*      */                   }
/*      */                   
/* 2264 */                   out.write("\n      <input name=\"button\" value=\"");
/* 2265 */                   out.print(butText);
/* 2266 */                   out.write("\" type=\"button\" class=\"buttons btn_highlt\"  onClick=\"javascript:validateAndSubmit(1)\">\n      ");
/* 2267 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 2268 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2272 */               if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 2273 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */               }
/*      */               
/* 2276 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 2277 */               out.write(32);
/* 2278 */               int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f2.doAfterBody();
/* 2279 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2283 */           if (_jspx_th_logic_005fnotEqual_005f2.doEndTag() == 5) {
/* 2284 */             this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f2); return;
/*      */           }
/*      */           
/* 2287 */           this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f2);
/* 2288 */           out.write(" <input name=\"cancel\" type=\"hidden\" value=\"true\">\n      <input name=\"button1\" type=\"reset\" class=\"buttons btn_reset\" value=\"");
/* 2289 */           out.print(FormatUtil.getString("am.webclient.newaction.restoredefaults"));
/* 2290 */           out.write("\">\n      ");
/*      */           
/* 2292 */           if (!isInvokedFromPopup)
/*      */           {
/*      */ 
/* 2295 */             out.write("\n      <input name=\"button2\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 2296 */             out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2297 */             out.write("\" onClick=\"javascript:history.back()\">\n      ");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*      */ 
/* 2303 */             out.write("\n      <input name=\"button2\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 2304 */             out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 2305 */             out.write("\" onClick=\"javascript:window.parent.close()\">\n      ");
/*      */           }
/*      */           
/*      */ 
/* 2309 */           out.write("\n    </td>\n    \n  </tr>\n</table>\n\n\n\n</td>\n <td width=\"30%\" valign=\"top\">\n ");
/*      */           
/* 2311 */           StringBuffer helpcardKey = new StringBuffer();
/* 2312 */           helpcardKey.append(FormatUtil.getString("am.webclient.actions.quicknote.sendtrap.help") + "<br>");
/* 2313 */           if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2314 */             helpcardKey.append(FormatUtil.getString("am.webclient.snmptrap.globaltrapactionlink"));
/*      */           }
/* 2316 */           out.write("\n        \t");
/* 2317 */           org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()), out, false);
/* 2318 */           out.write("\n\t\t</td>\n</tr>\n</table>\n\n");
/* 2319 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2320 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2324 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2325 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else {
/* 2328 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2329 */         out.write(10);
/* 2330 */         response.setContentType("text/html;charset=UTF-8");
/* 2331 */         out.write("\n\n<script>\n    document.AMActionForm.actions.selectedIndex=3;\n</script>\n");
/*      */       }
/* 2333 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2334 */         out = _jspx_out;
/* 2335 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2336 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2337 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2340 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2346 */     PageContext pageContext = _jspx_page_context;
/* 2347 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2349 */     org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f0 = (org.apache.struts.taglib.logic.PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(org.apache.struts.taglib.logic.PresentTag.class);
/* 2350 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2351 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 2353 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2354 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2355 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2357 */         out.write("\n\talertUser();\n\treturn;\n\t");
/* 2358 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2359 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2363 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2364 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2365 */       return true;
/*      */     }
/* 2367 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2368 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2373 */     PageContext pageContext = _jspx_page_context;
/* 2374 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2376 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2377 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2378 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2380 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2382 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2383 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2384 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2385 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2386 */       return true;
/*      */     }
/* 2388 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2389 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2394 */     PageContext pageContext = _jspx_page_context;
/* 2395 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2397 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2398 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2399 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2401 */     _jspx_th_c_005fif_005f0.setTest("${globalconfig['mailserverconfigured'] != 'true'}");
/* 2402 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2403 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 2405 */         out.write("\n\t\t\tmyOnLoad();\n\t\t");
/* 2406 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2407 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2411 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2412 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2413 */       return true;
/*      */     }
/* 2415 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2416 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2421 */     PageContext pageContext = _jspx_page_context;
/* 2422 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2424 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2425 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2426 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2428 */     _jspx_th_c_005fif_005f1.setTest("${globalconfig['mailserverconfigured'] != 'true' && param.checkForMailSetting eq 'true'}");
/* 2429 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2430 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2432 */         out.write("\n\t\t\t\tmyOnLoad();\n\t\t\t");
/* 2433 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2434 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2438 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2439 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2440 */       return true;
/*      */     }
/* 2442 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2443 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2448 */     PageContext pageContext = _jspx_page_context;
/* 2449 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2451 */     org.apache.taglibs.standard.tag.common.core.CatchTag _jspx_th_c_005fcatch_005f0 = (org.apache.taglibs.standard.tag.common.core.CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(org.apache.taglibs.standard.tag.common.core.CatchTag.class);
/* 2452 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 2453 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 2455 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 2456 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 2458 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 2459 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 2461 */           out.write(" \n      ");
/* 2462 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 2463 */             return true;
/* 2464 */           out.write(32);
/* 2465 */           out.write(10);
/* 2466 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 2467 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2471 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 2472 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2475 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 2476 */         out = _jspx_page_context.popBody(); }
/* 2477 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 2479 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 2480 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 2482 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 2487 */     PageContext pageContext = _jspx_page_context;
/* 2488 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2490 */     org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag.class);
/* 2491 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 2492 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 2494 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 2496 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 2497 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 2498 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 2499 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2500 */       return true;
/*      */     }
/* 2502 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2503 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2508 */     PageContext pageContext = _jspx_page_context;
/* 2509 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2511 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 2512 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 2513 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2515 */     _jspx_th_c_005fset_005f5.setVar("wiz");
/*      */     
/* 2517 */     _jspx_th_c_005fset_005f5.setValue("${param.wiz}");
/*      */     
/* 2519 */     _jspx_th_c_005fset_005f5.setScope("request");
/* 2520 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 2521 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 2522 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 2523 */       return true;
/*      */     }
/* 2525 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 2526 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2531 */     PageContext pageContext = _jspx_page_context;
/* 2532 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2534 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2535 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2536 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2538 */     _jspx_th_c_005fout_005f1.setValue("${wizimage}");
/*      */     
/* 2540 */     _jspx_th_c_005fout_005f1.setEscapeXml("false");
/* 2541 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2542 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2543 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2544 */       return true;
/*      */     }
/* 2546 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2547 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2552 */     PageContext pageContext = _jspx_page_context;
/* 2553 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2555 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2556 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2557 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2559 */     _jspx_th_c_005fif_005f9.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 2560 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2561 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 2563 */         out.write("\n    <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 2564 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 2565 */           return true;
/* 2566 */         out.write("&wiz=true\">\n    ");
/* 2567 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2568 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2572 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2573 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2574 */       return true;
/*      */     }
/* 2576 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2577 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2582 */     PageContext pageContext = _jspx_page_context;
/* 2583 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2585 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2586 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2587 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 2589 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 2590 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2591 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2592 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2593 */       return true;
/*      */     }
/* 2595 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2596 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2601 */     PageContext pageContext = _jspx_page_context;
/* 2602 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2604 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2605 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2606 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2608 */     _jspx_th_c_005fout_005f3.setValue("${wizimage}");
/*      */     
/* 2610 */     _jspx_th_c_005fout_005f3.setEscapeXml("false");
/* 2611 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2612 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2613 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2614 */       return true;
/*      */     }
/* 2616 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2617 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2622 */     PageContext pageContext = _jspx_page_context;
/* 2623 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2625 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2626 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2627 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2629 */     _jspx_th_c_005fif_005f10.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 2630 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2631 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 2633 */         out.write("\n    \t</a>\n    \t");
/* 2634 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2635 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2639 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2640 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2641 */       return true;
/*      */     }
/* 2643 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2644 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2649 */     PageContext pageContext = _jspx_page_context;
/* 2650 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2652 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2653 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2654 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2656 */     _jspx_th_c_005fif_005f11.setTest("${wizimage=='/images/new_high.gif'}");
/* 2657 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2658 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 2660 */         out.write("\n       <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 2661 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 2662 */           return true;
/* 2663 */         out.write("&wiz=true\">\n       ");
/* 2664 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2665 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2669 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2670 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2671 */       return true;
/*      */     }
/* 2673 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2674 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2679 */     PageContext pageContext = _jspx_page_context;
/* 2680 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2682 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2683 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2684 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 2686 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 2687 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2688 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2689 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2690 */       return true;
/*      */     }
/* 2692 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2693 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2698 */     PageContext pageContext = _jspx_page_context;
/* 2699 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2701 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2702 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2703 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2705 */     _jspx_th_c_005fout_005f5.setValue("${wizimage}");
/*      */     
/* 2707 */     _jspx_th_c_005fout_005f5.setEscapeXml("false");
/* 2708 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2709 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2710 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2711 */       return true;
/*      */     }
/* 2713 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2714 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2719 */     PageContext pageContext = _jspx_page_context;
/* 2720 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2722 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2723 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2724 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2726 */     _jspx_th_c_005fif_005f12.setTest("${wizimage=='/images/new_high.gif'}");
/* 2727 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2728 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 2730 */         out.write("\n       \t</a>\n       \t");
/* 2731 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 2732 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2736 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 2737 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2738 */       return true;
/*      */     }
/* 2740 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2741 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2746 */     PageContext pageContext = _jspx_page_context;
/* 2747 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2749 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2750 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 2751 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2753 */     _jspx_th_c_005fif_005f15.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 2754 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 2755 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 2757 */         out.write("\t\n    <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 2758 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 2759 */           return true;
/* 2760 */         out.write("&wiz=true\">\n ");
/* 2761 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 2762 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2766 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 2767 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 2768 */       return true;
/*      */     }
/* 2770 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 2771 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2776 */     PageContext pageContext = _jspx_page_context;
/* 2777 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2779 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2780 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2781 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 2783 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 2784 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2785 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2786 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2787 */       return true;
/*      */     }
/* 2789 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2790 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2795 */     PageContext pageContext = _jspx_page_context;
/* 2796 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2798 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2799 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2800 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2802 */     _jspx_th_c_005fout_005f7.setValue("${wizimage}");
/*      */     
/* 2804 */     _jspx_th_c_005fout_005f7.setEscapeXml("false");
/* 2805 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2806 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2807 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2808 */       return true;
/*      */     }
/* 2810 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2811 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2816 */     PageContext pageContext = _jspx_page_context;
/* 2817 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2819 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2820 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 2821 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2823 */     _jspx_th_c_005fif_005f16.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 2824 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 2825 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 2827 */         out.write("\t    \n    </a>\n ");
/* 2828 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 2829 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2833 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 2834 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2835 */       return true;
/*      */     }
/* 2837 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2838 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2843 */     PageContext pageContext = _jspx_page_context;
/* 2844 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2846 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2847 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2848 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 2850 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 2852 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 2853 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2854 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2855 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2856 */       return true;
/*      */     }
/* 2858 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2859 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2864 */     PageContext pageContext = _jspx_page_context;
/* 2865 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2867 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2868 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 2869 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 2871 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 2873 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 2874 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 2875 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 2876 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2877 */       return true;
/*      */     }
/* 2879 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2880 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2885 */     PageContext pageContext = _jspx_page_context;
/* 2886 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2888 */     EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 2889 */     _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/* 2890 */     _jspx_th_logic_005fequal_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2892 */     _jspx_th_logic_005fequal_005f0.setName("AMActionForm");
/*      */     
/* 2894 */     _jspx_th_logic_005fequal_005f0.setProperty("method");
/*      */     
/* 2896 */     _jspx_th_logic_005fequal_005f0.setValue("editSendTrapAction");
/* 2897 */     int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/* 2898 */     if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */       for (;;) {
/* 2900 */         out.write(10);
/* 2901 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_logic_005fequal_005f0, _jspx_page_context))
/* 2902 */           return true;
/* 2903 */         out.write(32);
/* 2904 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/* 2905 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2909 */     if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/* 2910 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 2911 */       return true;
/*      */     }
/* 2913 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 2914 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_logic_005fequal_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2919 */     PageContext pageContext = _jspx_page_context;
/* 2920 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2922 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 2923 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 2924 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_logic_005fequal_005f0);
/*      */     
/* 2926 */     _jspx_th_html_005fhidden_005f0.setProperty("method");
/* 2927 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 2928 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 2929 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2930 */       return true;
/*      */     }
/* 2932 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2933 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2938 */     PageContext pageContext = _jspx_page_context;
/* 2939 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2941 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(WriteTag.class);
/* 2942 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 2943 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2945 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/* 2946 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 2947 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 2948 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2949 */       return true;
/*      */     }
/* 2951 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2952 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_logic_005fequal_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2957 */     PageContext pageContext = _jspx_page_context;
/* 2958 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2960 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 2961 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 2962 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_logic_005fequal_005f1);
/*      */     
/* 2964 */     _jspx_th_html_005fhidden_005f1.setProperty("snmpVersionList");
/* 2965 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 2966 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 2967 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 2968 */       return true;
/*      */     }
/* 2970 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 2971 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2976 */     PageContext pageContext = _jspx_page_context;
/* 2977 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2979 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 2980 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/* 2981 */     _jspx_th_html_005fhidden_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2983 */     _jspx_th_html_005fhidden_005f3.setProperty("id");
/* 2984 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/* 2985 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/* 2986 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 2987 */       return true;
/*      */     }
/* 2989 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 2990 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2995 */     PageContext pageContext = _jspx_page_context;
/* 2996 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2998 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2999 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3000 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3002 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 3004 */     _jspx_th_html_005ftext_005f0.setSize("40");
/*      */     
/* 3006 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/*      */     
/* 3008 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/* 3009 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3010 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3011 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3012 */       return true;
/*      */     }
/* 3014 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3015 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3020 */     PageContext pageContext = _jspx_page_context;
/* 3021 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3023 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3024 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3025 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3027 */     _jspx_th_html_005ftext_005f1.setProperty("trapDestinationAddress");
/*      */     
/* 3029 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext default");
/*      */     
/* 3031 */     _jspx_th_html_005ftext_005f1.setSize("40");
/*      */     
/* 3033 */     _jspx_th_html_005ftext_005f1.setMaxlength("255");
/* 3034 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3035 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3036 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3037 */       return true;
/*      */     }
/* 3039 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3045 */     PageContext pageContext = _jspx_page_context;
/* 3046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3048 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3049 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 3050 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3052 */     _jspx_th_html_005ftext_005f2.setProperty("trapDestinationPort");
/*      */     
/* 3054 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext medium");
/*      */     
/* 3056 */     _jspx_th_html_005ftext_005f2.setSize("40");
/*      */     
/* 3058 */     _jspx_th_html_005ftext_005f2.setMaxlength("7");
/* 3059 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 3060 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 3061 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3062 */       return true;
/*      */     }
/* 3064 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3065 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3070 */     PageContext pageContext = _jspx_page_context;
/* 3071 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3073 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3074 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 3075 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3077 */     _jspx_th_html_005ftext_005f3.setProperty("objectID");
/*      */     
/* 3079 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext default");
/*      */     
/* 3081 */     _jspx_th_html_005ftext_005f3.setSize("40");
/*      */     
/* 3083 */     _jspx_th_html_005ftext_005f3.setMaxlength("255");
/* 3084 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 3085 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 3086 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3087 */       return true;
/*      */     }
/* 3089 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3090 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3095 */     PageContext pageContext = _jspx_page_context;
/* 3096 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3098 */     HiddenTag _jspx_th_html_005fhidden_005f4 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 3099 */     _jspx_th_html_005fhidden_005f4.setPageContext(_jspx_page_context);
/* 3100 */     _jspx_th_html_005fhidden_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3102 */     _jspx_th_html_005fhidden_005f4.setProperty("mibName");
/* 3103 */     int _jspx_eval_html_005fhidden_005f4 = _jspx_th_html_005fhidden_005f4.doStartTag();
/* 3104 */     if (_jspx_th_html_005fhidden_005f4.doEndTag() == 5) {
/* 3105 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 3106 */       return true;
/*      */     }
/* 3108 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 3109 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3114 */     PageContext pageContext = _jspx_page_context;
/* 3115 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3117 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3118 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 3119 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3121 */     _jspx_th_html_005ftext_005f4.setProperty("mibName");
/*      */     
/* 3123 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext itadmin-formtext");
/*      */     
/* 3125 */     _jspx_th_html_005ftext_005f4.setSize("50");
/*      */     
/* 3127 */     _jspx_th_html_005ftext_005f4.setMaxlength("255");
/* 3128 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 3129 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 3130 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 3131 */       return true;
/*      */     }
/* 3133 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 3134 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3139 */     PageContext pageContext = _jspx_page_context;
/* 3140 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3142 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3143 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 3144 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3146 */     _jspx_th_html_005ftext_005f5.setProperty("trapVarbinds");
/*      */     
/* 3148 */     _jspx_th_html_005ftext_005f5.setSize("40");
/*      */     
/* 3150 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext default");
/*      */     
/* 3152 */     _jspx_th_html_005ftext_005f5.setMaxlength("255");
/* 3153 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 3154 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 3155 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3156 */       return true;
/*      */     }
/* 3158 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 3159 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3164 */     PageContext pageContext = _jspx_page_context;
/* 3165 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3167 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3168 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 3169 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3171 */     _jspx_th_html_005fradio_005f0.setProperty("messageFormat");
/*      */     
/* 3173 */     _jspx_th_html_005fradio_005f0.setValue("1");
/* 3174 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 3175 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 3176 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3177 */       return true;
/*      */     }
/* 3179 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3180 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3185 */     PageContext pageContext = _jspx_page_context;
/* 3186 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3188 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3189 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 3190 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3192 */     _jspx_th_html_005fradio_005f1.setProperty("messageFormat");
/*      */     
/* 3194 */     _jspx_th_html_005fradio_005f1.setValue("2");
/* 3195 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 3196 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 3197 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3198 */       return true;
/*      */     }
/* 3200 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3201 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3206 */     PageContext pageContext = _jspx_page_context;
/* 3207 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3209 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3210 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 3211 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3213 */     _jspx_th_html_005ftext_005f6.setProperty("trapCommunity");
/*      */     
/* 3215 */     _jspx_th_html_005ftext_005f6.setSize("40");
/*      */     
/* 3217 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext default");
/*      */     
/* 3219 */     _jspx_th_html_005ftext_005f6.setMaxlength("50");
/* 3220 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 3221 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 3222 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 3223 */       return true;
/*      */     }
/* 3225 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 3226 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3231 */     PageContext pageContext = _jspx_page_context;
/* 3232 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3234 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3235 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 3236 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3238 */     _jspx_th_html_005ftext_005f7.setProperty("v1TrapSpecificType");
/*      */     
/* 3240 */     _jspx_th_html_005ftext_005f7.setSize("40");
/*      */     
/* 3242 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext default");
/*      */     
/* 3244 */     _jspx_th_html_005ftext_005f7.setMaxlength("50");
/* 3245 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 3246 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 3247 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 3248 */       return true;
/*      */     }
/* 3250 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 3251 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3256 */     PageContext pageContext = _jspx_page_context;
/* 3257 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3259 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3260 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/* 3261 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3263 */     _jspx_th_html_005ftext_005f8.setProperty("v1TrapEnterprise");
/*      */     
/* 3265 */     _jspx_th_html_005ftext_005f8.setSize("40");
/*      */     
/* 3267 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext default");
/*      */     
/* 3269 */     _jspx_th_html_005ftext_005f8.setMaxlength("50");
/* 3270 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/* 3271 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/* 3272 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 3273 */       return true;
/*      */     }
/* 3275 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 3276 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3281 */     PageContext pageContext = _jspx_page_context;
/* 3282 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3284 */     TextTag _jspx_th_html_005ftext_005f9 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3285 */     _jspx_th_html_005ftext_005f9.setPageContext(_jspx_page_context);
/* 3286 */     _jspx_th_html_005ftext_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3288 */     _jspx_th_html_005ftext_005f9.setProperty("trapCommunity");
/*      */     
/* 3290 */     _jspx_th_html_005ftext_005f9.setSize("40");
/*      */     
/* 3292 */     _jspx_th_html_005ftext_005f9.setStyleClass("formtext default");
/*      */     
/* 3294 */     _jspx_th_html_005ftext_005f9.setMaxlength("50");
/* 3295 */     int _jspx_eval_html_005ftext_005f9 = _jspx_th_html_005ftext_005f9.doStartTag();
/* 3296 */     if (_jspx_th_html_005ftext_005f9.doEndTag() == 5) {
/* 3297 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 3298 */       return true;
/*      */     }
/* 3300 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 3301 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3306 */     PageContext pageContext = _jspx_page_context;
/* 3307 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3309 */     TextTag _jspx_th_html_005ftext_005f10 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3310 */     _jspx_th_html_005ftext_005f10.setPageContext(_jspx_page_context);
/* 3311 */     _jspx_th_html_005ftext_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3313 */     _jspx_th_html_005ftext_005f10.setProperty("v2SNMPTrapOID");
/*      */     
/* 3315 */     _jspx_th_html_005ftext_005f10.setSize("40");
/*      */     
/* 3317 */     _jspx_th_html_005ftext_005f10.setStyleClass("formtext default");
/*      */     
/* 3319 */     _jspx_th_html_005ftext_005f10.setMaxlength("255");
/* 3320 */     int _jspx_eval_html_005ftext_005f10 = _jspx_th_html_005ftext_005f10.doStartTag();
/* 3321 */     if (_jspx_th_html_005ftext_005f10.doEndTag() == 5) {
/* 3322 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 3323 */       return true;
/*      */     }
/* 3325 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 3326 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3331 */     PageContext pageContext = _jspx_page_context;
/* 3332 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3334 */     TextTag _jspx_th_html_005ftext_005f11 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3335 */     _jspx_th_html_005ftext_005f11.setPageContext(_jspx_page_context);
/* 3336 */     _jspx_th_html_005ftext_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3338 */     _jspx_th_html_005ftext_005f11.setProperty("v3SNMPTrapOID");
/*      */     
/* 3340 */     _jspx_th_html_005ftext_005f11.setSize("40");
/*      */     
/* 3342 */     _jspx_th_html_005ftext_005f11.setStyleClass("formtext default");
/*      */     
/* 3344 */     _jspx_th_html_005ftext_005f11.setMaxlength("255");
/* 3345 */     int _jspx_eval_html_005ftext_005f11 = _jspx_th_html_005ftext_005f11.doStartTag();
/* 3346 */     if (_jspx_th_html_005ftext_005f11.doEndTag() == 5) {
/* 3347 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 3348 */       return true;
/*      */     }
/* 3350 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 3351 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3356 */     PageContext pageContext = _jspx_page_context;
/* 3357 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3359 */     TextTag _jspx_th_html_005ftext_005f12 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3360 */     _jspx_th_html_005ftext_005f12.setPageContext(_jspx_page_context);
/* 3361 */     _jspx_th_html_005ftext_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3363 */     _jspx_th_html_005ftext_005f12.setProperty("v3TrapUser");
/*      */     
/* 3365 */     _jspx_th_html_005ftext_005f12.setSize("40");
/*      */     
/* 3367 */     _jspx_th_html_005ftext_005f12.setStyleClass("formtext default");
/*      */     
/* 3369 */     _jspx_th_html_005ftext_005f12.setMaxlength("255");
/* 3370 */     int _jspx_eval_html_005ftext_005f12 = _jspx_th_html_005ftext_005f12.doStartTag();
/* 3371 */     if (_jspx_th_html_005ftext_005f12.doEndTag() == 5) {
/* 3372 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 3373 */       return true;
/*      */     }
/* 3375 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 3376 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3381 */     PageContext pageContext = _jspx_page_context;
/* 3382 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3384 */     TextTag _jspx_th_html_005ftext_005f13 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3385 */     _jspx_th_html_005ftext_005f13.setPageContext(_jspx_page_context);
/* 3386 */     _jspx_th_html_005ftext_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3388 */     _jspx_th_html_005ftext_005f13.setProperty("v3TrapContextName");
/*      */     
/* 3390 */     _jspx_th_html_005ftext_005f13.setSize("40");
/*      */     
/* 3392 */     _jspx_th_html_005ftext_005f13.setStyleClass("formtext default");
/*      */     
/* 3394 */     _jspx_th_html_005ftext_005f13.setMaxlength("255");
/* 3395 */     int _jspx_eval_html_005ftext_005f13 = _jspx_th_html_005ftext_005f13.doStartTag();
/* 3396 */     if (_jspx_th_html_005ftext_005f13.doEndTag() == 5) {
/* 3397 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/* 3398 */       return true;
/*      */     }
/* 3400 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/* 3401 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3406 */     PageContext pageContext = _jspx_page_context;
/* 3407 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3409 */     TextTag _jspx_th_html_005ftext_005f14 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3410 */     _jspx_th_html_005ftext_005f14.setPageContext(_jspx_page_context);
/* 3411 */     _jspx_th_html_005ftext_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3413 */     _jspx_th_html_005ftext_005f14.setProperty("v3TrapEngineID");
/*      */     
/* 3415 */     _jspx_th_html_005ftext_005f14.setSize("40");
/*      */     
/* 3417 */     _jspx_th_html_005ftext_005f14.setStyleClass("formtext default");
/*      */     
/* 3419 */     _jspx_th_html_005ftext_005f14.setMaxlength("50");
/* 3420 */     int _jspx_eval_html_005ftext_005f14 = _jspx_th_html_005ftext_005f14.doStartTag();
/* 3421 */     if (_jspx_th_html_005ftext_005f14.doEndTag() == 5) {
/* 3422 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f14);
/* 3423 */       return true;
/*      */     }
/* 3425 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f14);
/* 3426 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\SendTrapActionForm_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */