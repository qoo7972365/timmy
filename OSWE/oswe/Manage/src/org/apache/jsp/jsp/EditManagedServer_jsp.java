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
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class EditManagedServer_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   23 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */   private static java.util.Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005ftarget_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   52 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   56 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005ftarget_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   76 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   80 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   81 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   82 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.release();
/*   83 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*   84 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.release();
/*   85 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.release();
/*   86 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*   87 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   88 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   89 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/*   90 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*   91 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*   92 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   93 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*   94 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*   95 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005ftarget_005fproperty_005fnobody.release();
/*   96 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*   97 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/*   98 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  105 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  108 */     JspWriter out = null;
/*  109 */     Object page = this;
/*  110 */     JspWriter _jspx_out = null;
/*  111 */     PageContext _jspx_page_context = null;
/*      */     
/*  113 */     String _jspx_row1_1 = null;
/*  114 */     Integer _jspx_k_1 = null;
/*      */     try
/*      */     {
/*  117 */       response.setContentType("text/html");
/*  118 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  120 */       _jspx_page_context = pageContext;
/*  121 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  122 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  123 */       session = pageContext.getSession();
/*  124 */       out = pageContext.getOut();
/*  125 */       _jspx_out = out;
/*      */       
/*  127 */       out.write("\n<html>\n<head>\n\n\n\n\n\n\n\n<title>Managed Server Details</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n<link href=\"/images/");
/*  128 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  130 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/chosen.jquery.min.js\"></SCRIPT>\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n<script>\njQuery(document).ready(function(){\n\tjQuery(\".chosenselectmasgroup\").chosen({ // NO I18N\n\t\tno_results_text: '");
/*  131 */       out.print(FormatUtil.getString("am.common.search.no.result.match.text"));
/*  132 */       out.write("',\n\t\tdisable_search_threshold: 5\n\t});\n});\nfunction myOnLoad()\n{\n ");
/*      */       
/*  134 */       if (request.getAttribute("expandSyDetails") != null)
/*      */       {
/*      */ 
/*  137 */         out.write("\n      document.AMActionForm.failOvercheckbox.checked=true;\n      toggleDiv('failoverServer');\n ");
/*      */       }
/*  139 */       out.write("\n}\nfunction validateAndSubmit()\n{\n\t\t\tif(document.AMActionForm.displayname.value=='')\n\t\t\t{\n\t\t\t  alert(\"");
/*  140 */       out.print(FormatUtil.getString("am.webclient.common.jsalertfordisplayname.text"));
/*  141 */       out.write("\");\n\t\t\t  document.AMActionForm.displayname.select();\n\t\t\t  return false;\n\t\t\t}\n\t\t\tif(document.AMActionForm.fromaddress.value=='')\n\t\t\t{\n\t\t\t  alert(\"");
/*  142 */       out.print(FormatUtil.getString("am.webclient.managedserver.hostname.alert"));
/*  143 */       out.write("\");\n\t\t\t  document.AMActionForm.fromaddress.select();\n\t\t\t  return false;\n\t\t\t}\n\t\t\tvar temp = trimAll(document.AMActionForm.category.value);\n\t\t\tif((temp == '') || !(isPositiveInteger(temp)))\n\t\t\t{\n\t\t\t\talert(\"");
/*  144 */       out.print(FormatUtil.getString("am.webclient.managedserver.webserverport.alert"));
/*  145 */       out.write("\");\n\t\t\t\tdocument.AMActionForm.category.select();\n\t\t\t\treturn false;\n\t\t\t}\n\t\t\tvar temp = trimAll(document.AMActionForm.priority.value);\n\t\t\tif((temp == '') || !(isPositiveInteger(temp)))\n\t\t\t{\n\t\t\t\talert(\"");
/*  146 */       out.print(FormatUtil.getString("am.webclient.managedserver.sslport.alert"));
/*  147 */       out.write("\");\n\t\t\t\tdocument.AMActionForm.priority.select();\n\t\t\t\treturn false;\n\t\t\t}\n\t\t\tvar temp = trimAll(document.AMActionForm.password.value);\n\t\t\tif((temp == ''))\n\t\t\t{\n\t\t\t\talert(\"");
/*  148 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.password"));
/*  149 */       out.write("\");\n\t\t\t\tdocument.AMActionForm.password.select();\n\t\t\t\treturn;\n\t\t\t}\n\t\t\t/*var temp = trimAll(document.AMActionForm.timeout.value);\n\t\t     if((temp == '') || !(isPositiveInteger(temp)) || (temp == '0'))\n\t\t\t {\n\t\t\t\talert(\"");
/*  150 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.polling"));
/*  151 */       out.write("\");\n\t\t\t\tdocument.AMActionForm.timeout.select();\n\t\t\t\treturn;\n\t\t\t }*/\n\t\t\tif(document.AMActionForm.failOvercheckbox.checked)\n\t\t\t{\n\t\t\t\tif(document.AMActionForm.toaddress.value=='')\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  152 */       out.print(FormatUtil.getString("am.webclient.managedserver.hostname.alert"));
/*  153 */       out.write("\");\n\t\t\t\t\tdocument.AMActionForm.toaddress.select();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\tvar temp = trimAll(document.AMActionForm.technician.value);\n\t\t\t\tif((temp == '') || !(isPositiveInteger(temp)))\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  154 */       out.print(FormatUtil.getString("am.webclient.managedserver.webserverport.alert"));
/*  155 */       out.write("\");\n\t\t\t\t\tdocument.AMActionForm.technician.select();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t\tvar temp = trimAll(document.AMActionForm.instance.value);\n\t\t\t\tif((temp == '') || !(isPositiveInteger(temp)))\n\t\t\t\t{\n\t\t\t\t\talert(\"");
/*  156 */       out.print(FormatUtil.getString("am.webclient.managedserver.sslport.alert"));
/*  157 */       out.write("\");\n\t\t\t\t\tdocument.AMActionForm.instance.select();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t }\n                         if(document.AMActionForm.anomalybox){\n                         if(document.AMActionForm.anomalybox.checked){\n                         \n                                document.AMActionForm.anomalybox.value=1;\n   \n                        }else{\n                         \n                                document.AMActionForm.anomalybox.value=-1;\n                        }\n                         }\n                        if(document.AMActionForm.rbmbox){\n                        if(document.AMActionForm.rbmbox.options[document.AMActionForm.rbmbox.selectedIndex].value==0){\n     \t\t\t\t document.AMActionForm.rbmbox.value=-1;\n    \n  \t\t\t}\n  \t\t\telse\n  \t\t\t{\n\t  \t\t\t//rbm Agents\n  \t\t\t}\n                        }\n\tdocument.AMActionForm.submit();\n}\n\nfunction chooseDomain() {\n\t\n\tvar selIdx = document.AMActionForm.masGroupName.options.selectedIndex;\n\tif (selIdx != 0) {\n\t\tdocument.AMActionForm.editDomainName.value =  document.AMActionForm.masGroupName.options[selIdx].value;\n");
/*  158 */       out.write("\t} else {\n\t\tdocument.AMActionForm.editDomainName.value =  '';\n\t\thideDiv('updatemessage'); // NO I18N\n\t\thideDiv('editdomain');// NO I18N\n\t\thideDiv('adddomain');\t// NO I18N\t\n\t\tshowDiv('domainAddEdit'); // NO I18N\n\t}\n}\nfunction addDomainNameToList() {\n\t\n\tvar value = document.AMActionForm.newMasGroupName.value;\n\tif(value=='')\n\t{\n\t\talert(\"");
/*  159 */       out.print(FormatUtil.getString("am.webclient.admin.add.masgroup.valid.name.alert.text"));
/*  160 */       out.write("\");\n\t\tdocument.AMActionForm.newMasGroupName.focus();\n\t\treturn false;\n\t}\t\n\tvar len = document.AMActionForm.masGroupName.options.length;\n\tfor (var i=0;i<len;i++) {\n\t\tvar eachValue = document.AMActionForm.masGroupName.options[i].value;\n\t\tif (eachValue.toLowerCase() == value.toLowerCase()) {\n\t\t\talert(\"");
/*  161 */       out.print(FormatUtil.getString("am.webclient.admin.add.masgroup.name.exist.alert.text"));
/*  162 */       out.write("\");\n\t\t\tdocument.AMActionForm.masGroupName.options[i].selected=true;\n\t\t\tdocument.AMActionForm.newMasGroupName.focus();\n\t\t\treturn false;\t\t\t\n\t\t}\n\t}\n\tdocument.AMActionForm.masGroupName.options[document.AMActionForm.masGroupName.length] =new Option(value,value,false,true);\n\t$('.chosenselectmasgroup').trigger(\"liszt:updated\"); // NO I18N\n\thideDiv('adddomain'); // NO I18N\n\thideDiv('updatemessage'); // NO I18N\n\tshowDiv('domainAddEdit');// NO I18N\n}\n\nfunction checkBeforeAdd() {\n\t\n\tdocument.AMActionForm.newMasGroupName.value='';\n\thideDiv('domainAddEdit'); // NO I18N\n\thideDiv('updatemessage'); // NO I18N\n\thideDiv('editdomain');// NO I18N\n\tshowDiv('adddomain');// NO I18N\n}\n\nfunction checkBeforeEdit() {\n\n\tvar selIdx = document.AMActionForm.masGroupName.options.selectedIndex;\n\tif (selIdx == 0) {\n\t\talert(\"");
/*  163 */       out.print(FormatUtil.getString("am.webclient.admin.add.masgroup.select.valid.name.alert.text"));
/*  164 */       out.write("\");\n\t\tdocument.AMActionForm.masGroupName.focus();\n\t\treturn false;\t\t\n\t}\n\thideDiv('domainAddEdit'); // NO I18N\n\thideDiv('updatemessage');// NO I18N\n\thideDiv('adddomain');// NO I18N\n\tshowDiv('editdomain');// NO I18N\n\tdocument.AMActionForm.editDomainName.value=document.AMActionForm.masGroupName.options[selIdx].value;\n}\n\nfunction editDomainNameToList() {\n\t\n\tvar value = document.AMActionForm.editDomainName.value;\n\tif(value=='')\n\t{\n\t\talert(\"");
/*  165 */       out.print(FormatUtil.getString("am.webclient.admin.add.masgroup.valid.name.alert.text"));
/*  166 */       out.write("\");\n\t\tdocument.AMActionForm.editDomainName.focus();\n\t\treturn false;\n\t}\t\n\tvar len = document.AMActionForm.masGroupName.options.length;\n\tfor (var i=0;i<len;i++) {\n\t\tvar eachValue = document.AMActionForm.masGroupName.options[i].value;\n\t\tif (eachValue.toLowerCase() == value.toLowerCase()) {\n\t\t\talert(\"");
/*  167 */       out.print(FormatUtil.getString("am.webclient.admin.add.masgroup.name.exist.alert.text"));
/*  168 */       out.write("\");\n\t\t\tdocument.AMActionForm.masGroupName.options[i].selected=true;\n\t\t\tdocument.AMActionForm.editDomainName.focus();\n\t\t\treturn false;\t\t\t\n\t\t}\n\t}\n\tvar selIdx = document.AMActionForm.masGroupName.options.selectedIndex;\n\tvar oldName = document.AMActionForm.masGroupName.options[selIdx].value;\n\tvar newName = value;\n\tvar url=\"/adminAction.do?method=updateMasDomainName&oldMasGroupName=\"+encodeURIComponent(oldName)+\"&newMasGroupName=\"+encodeURIComponent(newName);\t//NO I18N\n\thttp.open(\"GET\",url,true);\n\thttp.onreadystatechange = showUpdateResult;\n\thttp.send(null);\n}\n\nfunction showUpdateResult() {\n\n    if(http.readyState == 4)\n    {\n\t\tvar result = http.responseText;\n\t\tvar statusMessage = \"\";\n\t\tif (result == \"FAILED\") { // NO I18N\n\t\t\tstatusMessage = '");
/*  169 */       out.print(FormatUtil.getString("am.webclient.admin.update.masgroup.name.fail.text"));
/*  170 */       out.write("';\n\t\t} else if (result == \"SUCCESS\") { // NO I18N\n\t\t\tstatusMessage = '");
/*  171 */       out.print(FormatUtil.getString("am.webclient.admin.update.masgroup.name.success.text"));
/*  172 */       out.write("';\n\t\t}\t\t\n\t\tdocument.getElementById(\"updatemessage\").innerHTML=statusMessage;\t\t\n\t\tvar value = document.AMActionForm.editDomainName.value;\n\t\tvar selIdx = document.AMActionForm.masGroupName.options.selectedIndex;\n\t\tdocument.AMActionForm.masGroupName.options[selIdx].text=value;\n\t\tdocument.AMActionForm.masGroupName.options[selIdx].value=value;\t\t\n\t\t$('.chosenselectmasgroup').trigger(\"liszt:updated\"); // NO I18N\n\t\t//document.AMActionForm.masGroupName.options[selIdx].text=null;\n\t\t//document.AMActionForm.masGroupName.options[document.AMActionForm.masGroupName.length] =new Option(value,value,false,true);\n\t\thideDiv('editdomain');  // NO I18N\n\t\tshowDiv('domainAddEdit');\t // NO I18N\t\n\t\tshowDiv('updatemessage');  // NO I18N\n\t}\n}\n</script>\n</head>\n\n<body>\n   <!-- ");
/*      */       
/*  174 */       com.adventnet.appmanager.server.framework.FreeEditionDetails fd = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails();
/*  175 */       int ac = com.adventnet.appmanager.util.EnterpriseUtil.getAnomalyCount();
/*  176 */       int rc = com.adventnet.appmanager.util.EnterpriseUtil.getRBMCount();
/*  177 */       int totalanomaly = fd.getNumberOfAnomalyPermitted();
/*  178 */       int totalrbm = fd.getNumberOfRBMPermitted();
/*  179 */       boolean isAnomaly = fd.isAnomalyAllowed();
/*  180 */       boolean isRBM = fd.isRBMAllowed();
/*  181 */       int remainanomaly = totalanomaly - ac;
/*  182 */       if (rc < 0)
/*      */       {
/*  184 */         rc = 0;
/*      */       }
/*  186 */       int remainrbm = totalrbm - rc;
/*  187 */       String licensetype = fd.getUserType();
/*  188 */       String anomalymessage = FormatUtil.getString("am.webclient.anomaly.remainanomalyaddon.text", new String[] { remainanomaly + "" });
/*  189 */       String rbmmessage = FormatUtil.getString("am.webclient.rbmurlmonitor.remainaddon.text", new String[] { remainrbm + "" });
/*      */       
/*  191 */       out.write("-->\n");
/*      */       
/*  193 */       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  194 */       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  195 */       _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */       
/*  197 */       _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/*  198 */       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  199 */       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */         for (;;) {
/*  201 */           out.write("\n    \n    <!-- ");
/*      */           
/*  203 */           if ("R".equals(licensetype)) {
/*  204 */             if ((isAnomaly) && (remainanomaly != 0)) {
/*  205 */               out.write("\n    ");
/*  206 */               out.print(anomalymessage);
/*  207 */               out.write("\n    ");
/*      */             }
/*  209 */             out.write("<br>\n         ");
/*  210 */             if ((isRBM) && (remainrbm != 0) && (totalrbm != -1)) {
/*  211 */               out.write("\n         ");
/*  212 */               out.print(rbmmessage);
/*  213 */               out.write("\n          ");
/*      */             }
/*      */           }
/*  216 */           out.write("-->\n<table width=\"90%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/*  217 */           out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/*  218 */           out.write(" &gt;&nbsp;");
/*  219 */           out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getManagedServerPage(request));
/*  220 */           out.write(" &gt; <span class=\"bcactive\">");
/*  221 */           out.print(FormatUtil.getString("am.webclient.managedserver.title.edit"));
/*  222 */           out.write("</span></td>\n\t</tr>\n</table>\n\n\t");
/*  223 */           if (_jspx_meth_html_005ferrors_005f0(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */             return;
/*  225 */           out.write(10);
/*  226 */           out.write(9);
/*      */           
/*  228 */           FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/*  229 */           _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  230 */           _jspx_th_html_005fform_005f0.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */           
/*  232 */           _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*  233 */           int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  234 */           if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */             for (;;) {
/*  236 */               out.write("\n\t<input name=\"update\" type=\"hidden\" value=\"update\">\n        \n\t<input name=\"method\" type=\"hidden\" value=\"showManagedServers\">\n\t<input name=\"serverID\" type=\"hidden\" value=\"");
/*  237 */               out.print(request.getParameter("serverID"));
/*  238 */               out.write("\">\n\t<input name=\"serverType\" type=\"hidden\" value=\"");
/*  239 */               out.print(request.getParameter("serverType"));
/*  240 */               out.write("\">\n\t");
/*  241 */               out.write("\n\t<input name=\"_isfromProbeSettings\" type=\"hidden\" value=\"true\">\n<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr> \n\t    <td height=\"28\" width=\"25%\" colspan=\"2\"  class=\"tableheadingbborder\">");
/*  242 */               out.print(FormatUtil.getString("am.webclient.managedserver.title.edit"));
/*  243 */               out.write("</td>\n\t  </tr>\n</table>\n  <table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n   <tr>\n    <td height=\"28\">\n\t<table align=\"center\" width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" >\n\t");
/*      */               
/*  245 */               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.get(IterateTag.class);
/*  246 */               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/*  247 */               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  249 */               _jspx_th_logic_005fiterate_005f0.setName("EditManagedServer");
/*      */               
/*  251 */               _jspx_th_logic_005fiterate_005f0.setScope("request");
/*      */               
/*  253 */               _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */               
/*  255 */               _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*  256 */               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/*  257 */               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/*  258 */                 Object row = null;
/*  259 */                 Integer j = null;
/*  260 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  261 */                   out = _jspx_page_context.pushBody();
/*  262 */                   _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  263 */                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                 }
/*  265 */                 row = _jspx_page_context.findAttribute("row");
/*  266 */                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                 for (;;) {
/*  268 */                   out.write("\n\t  <tr > \n\t    <td height=\"28\" width=\"25%\" class=\"label-align\">");
/*  269 */                   out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/*  270 */                   out.write("<span class=\"mandatory\">*</span></td>\n\t    <td height=\"28\" width=\"75%\">\n\t    ");
/*  271 */                   if (_jspx_meth_c_005fset_005f0(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  273 */                   out.write("\n\t    ");
/*  274 */                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  276 */                   out.write(" </td>\n\t  </tr>\t\n\t<tr>\n\t  <td height=\"28\" width=\"25%\" class=\"label-align\">");
/*  277 */                   out.print(FormatUtil.getString("am.webclient.admin.add.masgroup.heading.text"));
/*  278 */                   out.write("</td>\t  \n \t  <td height=\"28\" width=\"75%\" class=\"\" colspan=\"2\">\n\t\t<table width=\"100%\" BORDER=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t<tr>\n\t\t\t\t");
/*  279 */                   if (_jspx_meth_c_005fset_005f1(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  281 */                   out.write("\n\t\t\t\t");
/*      */                   
/*  283 */                   org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f0 = (org.apache.taglibs.standard.tag.common.core.ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
/*  284 */                   _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  285 */                   _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*  286 */                   int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  287 */                   if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                     for (;;) {
/*  289 */                       out.write("\n\t\t\t\t\t");
/*      */                       
/*  291 */                       WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  292 */                       _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  293 */                       _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/*  295 */                       _jspx_th_c_005fwhen_005f0.setTest("${not empty masCreatedMasGroupNames}");
/*  296 */                       int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  297 */                       if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                         for (;;) {
/*  299 */                           out.write(" \n\t\t\t\t\t\t<td width=\"30%\" style=\"padding-left:0px;\">\t\t\t\t\t\t\n\t\t\t\t\t\t\t");
/*      */                           
/*  301 */                           SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/*  302 */                           _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/*  303 */                           _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/*  305 */                           _jspx_th_html_005fselect_005f0.setProperty("masGroupName");
/*      */                           
/*  307 */                           _jspx_th_html_005fselect_005f0.setStyleClass("formtext chosenselectmasgroup");
/*      */                           
/*  309 */                           _jspx_th_html_005fselect_005f0.setOnchange("javascript:chooseDomain();");
/*      */                           
/*  311 */                           _jspx_th_html_005fselect_005f0.setStyle("width:258px");
/*  312 */                           int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/*  313 */                           if (_jspx_eval_html_005fselect_005f0 != 0) {
/*  314 */                             if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  315 */                               out = _jspx_page_context.pushBody();
/*  316 */                               _jspx_th_html_005fselect_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  317 */                               _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  320 */                               out.write("\n\t \t\t\t\t\t\t\t");
/*      */                               
/*  322 */                               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  323 */                               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/*  324 */                               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/*  326 */                               _jspx_th_html_005foption_005f0.setValue("-");
/*  327 */                               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/*  328 */                               if (_jspx_eval_html_005foption_005f0 != 0) {
/*  329 */                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/*  330 */                                   out = _jspx_page_context.pushBody();
/*  331 */                                   _jspx_th_html_005foption_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  332 */                                   _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/*  335 */                                   out.print(FormatUtil.getString("am.webclient.admin.add.mas.select.masgroup.text"));
/*  336 */                                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/*  337 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*  340 */                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/*  341 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/*  344 */                               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/*  345 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                               }
/*      */                               
/*  348 */                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/*  349 */                               out.write("\t\t\n\t\t\t\t\t\t      \t \t");
/*      */                               
/*  351 */                               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  352 */                               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/*  353 */                               _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                               
/*  355 */                               _jspx_th_logic_005fiterate_005f1.setName("masCreatedMasGroupNames");
/*      */                               
/*  357 */                               _jspx_th_logic_005fiterate_005f1.setId("row1");
/*      */                               
/*  359 */                               _jspx_th_logic_005fiterate_005f1.setIndexId("k");
/*      */                               
/*  361 */                               _jspx_th_logic_005fiterate_005f1.setType("java.lang.String");
/*  362 */                               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/*  363 */                               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/*  364 */                                 String row1 = null;
/*  365 */                                 Integer k = null;
/*  366 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/*  367 */                                   out = _jspx_page_context.pushBody();
/*  368 */                                   _jspx_th_logic_005fiterate_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  369 */                                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                 }
/*  371 */                                 row1 = (String)_jspx_page_context.findAttribute("row1");
/*  372 */                                 k = (Integer)_jspx_page_context.findAttribute("k");
/*      */                                 for (;;) {
/*  374 */                                   out.write("\n\t\t\t\t\t\t      \t \t\t");
/*      */                                   
/*  376 */                                   OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  377 */                                   _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/*  378 */                                   _jspx_th_html_005foption_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                   
/*  380 */                                   _jspx_th_html_005foption_005f1.setValue(row1);
/*  381 */                                   int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/*  382 */                                   if (_jspx_eval_html_005foption_005f1 != 0) {
/*  383 */                                     if (_jspx_eval_html_005foption_005f1 != 1) {
/*  384 */                                       out = _jspx_page_context.pushBody();
/*  385 */                                       _jspx_th_html_005foption_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  386 */                                       _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/*  389 */                                       out.print(row1);
/*  390 */                                       int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/*  391 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*  394 */                                     if (_jspx_eval_html_005foption_005f1 != 1) {
/*  395 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/*  398 */                                   if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/*  399 */                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                   }
/*      */                                   
/*  402 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/*  403 */                                   out.write("\n\t\t\t\t\t\t      \t \t");
/*  404 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/*  405 */                                   row1 = (String)_jspx_page_context.findAttribute("row1");
/*  406 */                                   k = (Integer)_jspx_page_context.findAttribute("k");
/*  407 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*  410 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/*  411 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/*  414 */                               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/*  415 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                               }
/*      */                               
/*  418 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/*  419 */                               out.write("\n\t   \t\t\t\t\t\t");
/*  420 */                               int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/*  421 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  424 */                             if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  425 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  428 */                           if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/*  429 */                             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                           }
/*      */                           
/*  432 */                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/*  433 */                           out.write("\n\t   \t\t\t\t\t</td>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t<table BORDER=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td colspan=\"3\" align=\"left\">\n\t\t\t\t\t\t\t\t\t\t<div id=\"domainAddEdit\" width=\"25%\" style=\"padding-top: 5px;padding-left:15px;\">\n\t\t\t\t\t\t\t\t\t\t<a href=\"#\" title='");
/*  434 */                           out.print(FormatUtil.getString("am.webclient.admin.add.masgroup.text"));
/*  435 */                           out.write("' onClick=\"javascript:checkBeforeAdd();\" class=\"no-bg-change\"><img border=\"0\" class=\"no-bg-change\" src=\"/images/icon_custom_add_label.gif\"></a>\n\t\t\t\t\t\t\t\t\t\t<a href=\"#\" title='");
/*  436 */                           out.print(FormatUtil.getString("am.webclient.admin.edit.masgroup.text"));
/*  437 */                           out.write("' onClick=\"javascript:checkBeforeEdit();\" class=\"no-bg-change\"><img border=\"0\" style=\"position:relative; left:5px;\" class=\"no-bg-change\" src=\"/images/editWidget.gif\"></a>&nbsp;\n\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t<td align=\"left\">\n\t\t\t\t\t\t\t\t\t\t<div id=\"adddomain\" style=\"display:none;padding-left:0px;\">\n\t\t\t\t\t\t\t\t\t\t\t&nbsp;&nbsp;<span class=\"bodytext\">");
/*  438 */                           out.print(FormatUtil.getString("am.webclient.admin.add.masgroup.text"));
/*  439 */                           out.write(":</span>\n\t\t\t\t\t\t\t\t\t\t\t<input name=\"newMasGroupName\" type=\"text\" class=\"formtext\">\n\t\t\t\t\t\t\t\t\t\t\t<input name=\"Create\" type=\"button\" class=\"buttons\" value=\"");
/*  440 */                           out.print(FormatUtil.getString("Add"));
/*  441 */                           out.write("\" onClick=\"javascript:addDomainNameToList();\"> \n\t\t\t\t\t\t\t\t\t\t\t<input name=\"cancel\" type=\"button\" class=\"buttons\" value=\"");
/*  442 */                           out.print(FormatUtil.getString("Cancel"));
/*  443 */                           out.write("\" onClick=\"javascript:hideDiv('adddomain'); showDiv('domainAddEdit'); return false;\">\n\t\t\t\t\t\t\t\t\t\t</div>\t  \n\t\t\t\t\t\t\t\t\t\t<div id=\"editdomain\" style=\"display:none;padding-left:0px;\">\n\t\t\t\t\t\t\t\t\t\t\t&nbsp;&nbsp;<span class=\"bodytext\">");
/*  444 */                           out.print(FormatUtil.getString("am.webclient.admin.edit.masgroup.name"));
/*  445 */                           out.write(":</span>\n\t\t\t\t\t\t\t\t\t\t\t<input name=\"editDomainName\" type=\"text\" class=\"formtext\">\n\t\t\t\t\t\t\t\t\t\t\t<input name=\"Update\" type=\"button\" class=\"buttons\" value=\"");
/*  446 */                           out.print(FormatUtil.getString("Update"));
/*  447 */                           out.write("\" onClick=\"javascript:editDomainNameToList();\"> \n\t\t\t\t\t\t\t\t\t\t\t<input name=\"cancel\" type=\"button\" class=\"buttons\" value=\"");
/*  448 */                           out.print(FormatUtil.getString("Cancel"));
/*  449 */                           out.write("\" onClick=\"javascript:hideDiv('editdomain'); showDiv('domainAddEdit'); return false;\">\n\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t</td>\t\n\t\t\t\t\t\t\t\t\t<td align=\"left\">\t\n\t\t\t\t\t\t\t\t\t\t<div id=\"updatemessage\" style=\"display:block; padding-left:0px;\" class='error-text'></div>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\t\t\t\t\t\t\n\t\t\t\t\t\t</td>  \n\t\t\t\t\t");
/*  450 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  451 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  455 */                       if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  456 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                       }
/*      */                       
/*  459 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  460 */                       out.write(" \t\t\t\t\t\n   \t\t\t\t\t");
/*  461 */                       if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */                         return;
/*  463 */                       out.write("\n   \t\t\t\t");
/*  464 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  465 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  469 */                   if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  470 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                   }
/*      */                   
/*  473 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  474 */                   out.write("\n\t\t\t</tr>\n\t\t</table>\n\t  </td>\n\t</tr>\t    \n\t  <tr > \n\t    <td height=\"28\" width=\"25%\" class=\"label-align\">");
/*  475 */                   out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/*  476 */                   out.write("<span class=\"mandatory\">*</span></td>\n\t    <td height=\"28\" width=\"75%\"> \n\t    ");
/*  477 */                   if (_jspx_meth_c_005fset_005f2(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  479 */                   out.write("\n\t    ");
/*  480 */                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  482 */                   if (com.adventnet.appmanager.util.Constants.isIt360) {
/*  483 */                     out.write("<span class=\"bodytext\">&nbsp;&nbsp;*");
/*  484 */                     out.print(FormatUtil.getString("am.webclient.managedserver.reachability.probe"));
/*  485 */                     out.write("</span> ");
/*      */                   }
/*  487 */                   out.write(" </td>\n\t  </tr>\n\t  \n\t  <tr > \n\t    <td height=\"28\" width=\"25%\" class=\"label-align\">");
/*  488 */                   out.print(FormatUtil.getString("am.webclient.common.externalhostname.text"));
/*  489 */                   out.write("</td>\n\t    <td height=\"28\" width=\"75%\"> \n\t    ");
/*  490 */                   if (_jspx_meth_c_005fset_005f3(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  492 */                   out.write("\n\t    ");
/*  493 */                   if (_jspx_meth_html_005ftext_005f3(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  495 */                   if (com.adventnet.appmanager.util.Constants.isIt360) {
/*  496 */                     out.write("<span class=\"bodytext\">&nbsp;&nbsp;*");
/*  497 */                     out.print(FormatUtil.getString("am.webclient.managedserver.reachability.central"));
/*  498 */                     out.write("</span>");
/*      */                   }
/*  500 */                   out.write("</td>\n\t  </tr>\n\t  \n\t  <tr > \n\t    <td height=\"28\" width=\"25%\" class=\"label-align\">");
/*  501 */                   out.print(FormatUtil.getString("am.webclient.jboss.webserverport.text"));
/*  502 */                   out.write("<span class=\"mandatory\">*</span></td>\n\t    <td height=\"28\" width=\"75%\"> \n\t    ");
/*  503 */                   if (_jspx_meth_c_005fset_005f4(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  505 */                   out.write("\n\t    ");
/*  506 */                   if (_jspx_meth_html_005ftext_005f4(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  508 */                   out.write("<span class=\"bodytext\">&nbsp;&nbsp;*");
/*  509 */                   out.print(FormatUtil.getString("am.webclient.managedserver.webserverport.message"));
/*  510 */                   out.write("</span> </td>\n\t  </tr>\n\t  <tr>\n\t    <td height=\"28\" width=\"25%\" class=\"label-align\">");
/*  511 */                   out.print(FormatUtil.getString("am.webclient.managedserver.sslport"));
/*  512 */                   out.write("<span class=\"mandatory\">*</span></td>\n\t\t<td height=\"28\" width=\"75%\">\n\t\t");
/*  513 */                   if (_jspx_meth_c_005fset_005f5(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  515 */                   out.write(10);
/*  516 */                   out.write(9);
/*  517 */                   out.write(9);
/*  518 */                   if (_jspx_meth_html_005ftext_005f5(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  520 */                   out.write("<span class=\"bodytext\">&nbsp;&nbsp;*");
/*  521 */                   out.print(FormatUtil.getString("am.webclient.managedserver.sslport.message.centralprobe"));
/*  522 */                   out.write("</span> </td>\n\t  </tr>\n\t  <tr>\n\t   <td height=\"28\" width=\"25%\" class=\"label-align\">");
/*  523 */                   out.print(FormatUtil.getString("am.webclient.managedserver.password.text"));
/*  524 */                   out.write("<span class=\"mandatory\">*</span></td>\n\t   <td height=\"28\" width=\"75%\">\n\t   ");
/*  525 */                   if (_jspx_meth_c_005fset_005f6(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  527 */                   out.write("\n\t   ");
/*  528 */                   if (_jspx_meth_html_005fpassword_005f0(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  530 */                   out.write("\n\t   </td>\n\t  </tr>\n\t  <tr>\n\t   <td height=\"28\" width=\"25%\" class=\"label-align\">");
/*  531 */                   out.print(FormatUtil.getString("am.webclient.managedserver.mailtext"));
/*  532 */                   out.write("</td> \n\t   ");
/*      */                   
/*      */ 
/*  535 */                   String checked = (String)((java.util.ArrayList)row).get(5);
/*  536 */                   String acount = (String)((java.util.ArrayList)row).get(6);
/*  537 */                   String rcount = (String)((java.util.ArrayList)row).get(7);
/*  538 */                   String unchecked = "";
/*  539 */                   if (checked.equals("TRUE"))
/*      */                   {
/*  541 */                     checked = "CHECKED";
/*      */                   }
/*      */                   else
/*      */                   {
/*  545 */                     checked = "";
/*  546 */                     unchecked = "CHECKED";
/*      */                   }
/*  548 */                   if (!"-1".equals(acount)) {
/*  549 */                     acount = "CHECKED";
/*      */                   } else {
/*  551 */                     acount = "";
/*      */                   }
/*      */                   
/*      */ 
/*  555 */                   out.write("\n\t   <td height=\"28\" width=\"75%\">\n\t   \t\t<table>\n\t   \t\t\t<tr>\n\t   \t\t\t\t<td height=\"28\" width=\"28%\">\n\t  \t\t\t\t\t<input type=\"radio\" name=\"sslenabled\"  value=\"adminMail\" ");
/*  556 */                   out.print(checked);
/*  557 */                   out.write(" >\n\t   \t\t\t\t\t<span class=\"bodytext\">");
/*  558 */                   out.print(FormatUtil.getString("am.webclient.editmanagedserver.mailmemessage"));
/*  559 */                   out.write("</span>\n\t   \t\t\t\t</td>\n\t  \t\t\t\t<td height=\"28\" width=\"95%\">\n\t \t \t\t\t\t<table>\n\t \t \t\t\t\t\t<tr>\n\t \t \t\t\t\t\t\t<td height=\"28\" width=\"80%\">\n\t \t\t\t\t\t\t\t\t<input type=\"radio\" name=\"sslenabled\" value=\"nonAdminMail\" ");
/*  560 */                   out.print(unchecked);
/*  561 */                   out.write(" >\n\t   \t\t\t\t\t\t\t\t<span class=\"bodytext\">");
/*  562 */                   out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliverysentemail.text"));
/*  563 */                   out.write("</span> : \n\t   \t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t\t<td>");
/*  564 */                   if (_jspx_meth_c_005fset_005f7(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  566 */                   out.write("\n\t \t \t\t\t\t\t\t\t");
/*  567 */                   if (_jspx_meth_html_005fselect_005f1(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                     return;
/*  569 */                   out.write("\n\t\t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t</tr>\n\t \t\t\t\t\t</table>\n\t \t\t\t\t</td>\n\t \t\t\t</tr>\n\t \t\t</table>\n\t \t</td>\n\t  </tr> \n       <!-- \n\t   \n\t   ");
/*  570 */                   if (isAnomaly) {
/*  571 */                     out.write("\n            <tr>\n\t    <td height=\"19\"  class=\"whitegrayborder\">");
/*  572 */                     out.print(FormatUtil.getString("am.webclient.anomaly.anomalyprofiles.anomalyaddon.text"));
/*  573 */                     out.write("</td>\n\t\t<td height=\"19\"  class=\"whitegrayborder\">\n\t\t<input type=\"checkbox\" ");
/*  574 */                     out.print(acount);
/*  575 */                     out.write(" name=\"anomalybox\">\n\t</td>\n\t  </tr>\n         ");
/*      */                   }
/*  577 */                   out.write("\n\t\t ");
/*  578 */                   if (isRBM) {
/*  579 */                     out.write("\n\t\t\t <tr>\n\t\t\t\t <td height=\"19\"  class=\"whitegrayborder\">");
/*  580 */                     out.print(FormatUtil.getString("am.webclient.rbmurlmonitor.addon.text"));
/*  581 */                     out.write("</td>\n\t\t\t\t <td height=\"19\"  class=\"whitegrayborder\">\n\t\t\t\t <select name=\"rbmbox\" >\n\t\t\t\t ");
/*      */                     
/*  583 */                     if (licensetype.equals("T"))
/*      */                     {
/*      */ 
/*  586 */                       out.write("\n\t\t\t\t\t\t <option value=\"-1\">");
/*  587 */                       out.print(FormatUtil.getString("Unlimited"));
/*  588 */                       out.write("</option>\n\t\t\t\t\t\t ");
/*      */ 
/*      */                     }
/*  591 */                     else if (totalrbm == -1)
/*      */                     {
/*      */ 
/*  594 */                       out.write("\n\t\t  <option value=\"-1\">");
/*  595 */                       out.print(FormatUtil.getString("Unlimited"));
/*  596 */                       out.write("</option>\n\t\t  ");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*      */ 
/*  602 */                       int rcountintval = Integer.parseInt(rcount);
/*  603 */                       if (rcountintval < 0)
/*      */                       {
/*  605 */                         rcountintval = 0;
/*      */                       }
/*  607 */                       int total = remainrbm + rcountintval;
/*  608 */                       String selected = "false";
/*  609 */                       for (int y = 0; y <= total; y++)
/*      */                       {
/*  611 */                         if (rcountintval == y)
/*      */                         {
/*  613 */                           selected = "SELECTED";
/*      */                         }
/*      */                         else
/*      */                         {
/*  617 */                           selected = "";
/*      */                         }
/*      */                         
/*      */ 
/*      */ 
/*  622 */                         out.write("\n\t\t\t\t\t\t\t\t <option ");
/*  623 */                         out.print(selected);
/*  624 */                         out.write(" value=\"");
/*  625 */                         out.print(y);
/*  626 */                         out.write(34);
/*  627 */                         out.write(62);
/*  628 */                         out.print(y);
/*  629 */                         out.write("</option>\n\t\t\t\t\t\t\t\t ");
/*      */                       }
/*      */                       
/*      */ 
/*  633 */                       out.write("\n\t\t\t\t\t\t </select>\n\t\t\t\t\t\t </td>\n\t\t\t\t\t\t </tr>\n\t\t\t\t\t\t ");
/*      */                     }
/*      */                   }
/*      */                   
/*  637 */                   out.write("\n         \n\t  ");
/*  638 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/*  639 */                   row = _jspx_page_context.findAttribute("row");
/*  640 */                   j = (Integer)_jspx_page_context.findAttribute("j");
/*  641 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  644 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  645 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  648 */               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/*  649 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */               }
/*      */               
/*  652 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*  653 */               out.write(" -->\n\t\t<tr>\n\t\t <td colspan=\"3\" class=\"advancedoption\" style=\"padding-left: 7px;\">\n\t\t <input name=\"failOvercheckbox\" onClick=\"javascript:toggleDiv('failoverServer');\" type=\"checkbox\"><span class=\"bodytext\"> ");
/*  654 */               out.print(FormatUtil.getString("Failover Server Details"));
/*  655 */               out.write("</span></td>\n\t\t</tr>\n\t\t<tr>\n\t\t <td colspan=\"2\">\n\t\t  <div id=\"failoverServer\"   style=\"DISPLAY: none\">\n\t\t  <table  border=\"0\" cellspacing=\"0\" cellpadding=\"4\" width=\"100%\" align=\"center\">\n\t\t   <tr >\n\t\t    <td height=\"19\" width=\"25%\" class=\"label-align\">");
/*  656 */               out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/*  657 */               out.write("<span class=\"mandatory\">*</span></td>\n            <td width=\"75%\">\n            ");
/*  658 */               if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  660 */               out.write(" </td>\n           </tr>\n           <tr >\n            <td height=\"19\" class=\"label-align\">");
/*  661 */               out.print(FormatUtil.getString("am.webclient.jboss.webserverport.text"));
/*  662 */               out.write("<span class=\"mandatory\">*</span></td>\n            <td height=\"19\">\n            ");
/*  663 */               if (_jspx_meth_html_005ftext_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  665 */               out.write("</td>\n           </tr>\n           <tr>\n            <td height=\"19\" class=\"label-align\">");
/*  666 */               out.print(FormatUtil.getString("am.webclient.managedserver.sslport"));
/*  667 */               out.write("<span class=\"mandatory\">*</span></td>\n            <td height=\"19\">\n            ");
/*  668 */               if (_jspx_meth_html_005ftext_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  670 */               out.write("</td>\n           </tr>\n\t\t   </table>\n\t\t  </div>\n\t\t </td>\n\t\t</tr> \n\t</table>\n\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\">\n\t  <tr> \n\t    <td width=\"48%\" class=\"tablebottom\" align=\"right\"><input name=\"Button\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/*  671 */               out.print(FormatUtil.getString("am.webclient.common.update.text"));
/*  672 */               out.write("\" onClick=\"javascript:validateAndSubmit();\"></td>\n\t    <td width=\"52%\" class=\"tablebottom\"><input name=\"Button\" type=\"button\" class=\"buttons btn_link\" value=\"");
/*  673 */               out.print(FormatUtil.getString("Cancel"));
/*  674 */               out.write("\" onClick=\"javascript:history.back();\"></td>\n\t  </tr>\n\t</table>\n\t</td>\n\t</tr>\n\t</table>\n\t");
/*  675 */               int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  676 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  680 */           if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  681 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */           }
/*      */           
/*  684 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  685 */           out.write(10);
/*  686 */           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  687 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  691 */       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  692 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */       }
/*      */       else {
/*  695 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  696 */         out.write("\n</body>\n</html>\n");
/*      */       }
/*  698 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  699 */         out = _jspx_out;
/*  700 */         if ((out != null) && (out.getBufferSize() != 0))
/*  701 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  702 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  705 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  711 */     PageContext pageContext = _jspx_page_context;
/*  712 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  714 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  715 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  716 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  718 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  720 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  721 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  722 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  723 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  724 */       return true;
/*      */     }
/*  726 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  727 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ferrors_005f0(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  732 */     PageContext pageContext = _jspx_page_context;
/*  733 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  735 */     org.apache.struts.taglib.html.ErrorsTag _jspx_th_html_005ferrors_005f0 = (org.apache.struts.taglib.html.ErrorsTag)this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.get(org.apache.struts.taglib.html.ErrorsTag.class);
/*  736 */     _jspx_th_html_005ferrors_005f0.setPageContext(_jspx_page_context);
/*  737 */     _jspx_th_html_005ferrors_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*  738 */     int _jspx_eval_html_005ferrors_005f0 = _jspx_th_html_005ferrors_005f0.doStartTag();
/*  739 */     if (_jspx_th_html_005ferrors_005f0.doEndTag() == 5) {
/*  740 */       this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/*  741 */       return true;
/*      */     }
/*  743 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/*  744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  749 */     PageContext pageContext = _jspx_page_context;
/*  750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  752 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/*  753 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  754 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/*  756 */     _jspx_th_c_005fset_005f0.setTarget("${AMActionForm}");
/*      */     
/*  758 */     _jspx_th_c_005fset_005f0.setProperty("displayname");
/*      */     
/*  760 */     _jspx_th_c_005fset_005f0.setValue("${row[0]}");
/*  761 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  762 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  763 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/*  764 */       return true;
/*      */     }
/*  766 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/*  767 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  772 */     PageContext pageContext = _jspx_page_context;
/*  773 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  775 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  776 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/*  777 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/*  779 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/*  781 */     _jspx_th_html_005ftext_005f0.setSize("25");
/*      */     
/*  783 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/*  784 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/*  785 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/*  786 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  787 */       return true;
/*      */     }
/*  789 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  790 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  795 */     PageContext pageContext = _jspx_page_context;
/*  796 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  798 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/*  799 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  800 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/*  802 */     _jspx_th_c_005fset_005f1.setTarget("${AMActionForm}");
/*      */     
/*  804 */     _jspx_th_c_005fset_005f1.setProperty("masGroupName");
/*      */     
/*  806 */     _jspx_th_c_005fset_005f1.setValue("${row[9]}");
/*  807 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  808 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  809 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/*  810 */       return true;
/*      */     }
/*  812 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/*  813 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  818 */     PageContext pageContext = _jspx_page_context;
/*  819 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  821 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  822 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  823 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*  824 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  825 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/*  827 */         out.write("\n\t   \t\t\t\t<td height=\"28\" width=\"75%\" class=\"\" style=\"padding-left:0px;\">\n\t   \t\t\t\t\t");
/*  828 */         if (_jspx_meth_html_005ftext_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*  829 */           return true;
/*  830 */         out.write(" \n\t   \t\t\t\t</td>\n   \t\t\t\t\t");
/*  831 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  832 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  836 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  837 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  838 */       return true;
/*      */     }
/*  840 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  841 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  846 */     PageContext pageContext = _jspx_page_context;
/*  847 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  849 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  850 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/*  851 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/*  853 */     _jspx_th_html_005ftext_005f1.setProperty("masGroupName");
/*      */     
/*  855 */     _jspx_th_html_005ftext_005f1.setSize("25");
/*      */     
/*  857 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext default");
/*      */     
/*  859 */     _jspx_th_html_005ftext_005f1.setValue("");
/*  860 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/*  861 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/*  862 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/*  863 */       return true;
/*      */     }
/*  865 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/*  866 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  871 */     PageContext pageContext = _jspx_page_context;
/*  872 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  874 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/*  875 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/*  876 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/*  878 */     _jspx_th_c_005fset_005f2.setTarget("${AMActionForm}");
/*      */     
/*  880 */     _jspx_th_c_005fset_005f2.setProperty("fromaddress");
/*      */     
/*  882 */     _jspx_th_c_005fset_005f2.setValue("${row[1]}");
/*  883 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/*  884 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/*  885 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/*  886 */       return true;
/*      */     }
/*  888 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/*  889 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  894 */     PageContext pageContext = _jspx_page_context;
/*  895 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  897 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  898 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/*  899 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/*  901 */     _jspx_th_html_005ftext_005f2.setProperty("fromaddress");
/*      */     
/*  903 */     _jspx_th_html_005ftext_005f2.setSize("25");
/*      */     
/*  905 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext default");
/*  906 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/*  907 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/*  908 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/*  909 */       return true;
/*      */     }
/*  911 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/*  912 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  917 */     PageContext pageContext = _jspx_page_context;
/*  918 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  920 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/*  921 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/*  922 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/*  924 */     _jspx_th_c_005fset_005f3.setTarget("${AMActionForm}");
/*      */     
/*  926 */     _jspx_th_c_005fset_005f3.setProperty("externalhost");
/*      */     
/*  928 */     _jspx_th_c_005fset_005f3.setValue("${row[8]}");
/*  929 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/*  930 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/*  931 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/*  932 */       return true;
/*      */     }
/*  934 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/*  935 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  940 */     PageContext pageContext = _jspx_page_context;
/*  941 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  943 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  944 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/*  945 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/*  947 */     _jspx_th_html_005ftext_005f3.setProperty("externalhost");
/*      */     
/*  949 */     _jspx_th_html_005ftext_005f3.setSize("25");
/*      */     
/*  951 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext default");
/*  952 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/*  953 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/*  954 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/*  955 */       return true;
/*      */     }
/*  957 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/*  958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  963 */     PageContext pageContext = _jspx_page_context;
/*  964 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  966 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/*  967 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/*  968 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/*  970 */     _jspx_th_c_005fset_005f4.setTarget("${AMActionForm}");
/*      */     
/*  972 */     _jspx_th_c_005fset_005f4.setProperty("category");
/*      */     
/*  974 */     _jspx_th_c_005fset_005f4.setValue("${row[2]}");
/*  975 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/*  976 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/*  977 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/*  978 */       return true;
/*      */     }
/*  980 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f4);
/*  981 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  986 */     PageContext pageContext = _jspx_page_context;
/*  987 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  989 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/*  990 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/*  991 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/*  993 */     _jspx_th_html_005ftext_005f4.setProperty("category");
/*      */     
/*  995 */     _jspx_th_html_005ftext_005f4.setSize("25");
/*      */     
/*  997 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext default");
/*      */     
/*  999 */     _jspx_th_html_005ftext_005f4.setMaxlength("40");
/* 1000 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 1001 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 1002 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 1003 */       return true;
/*      */     }
/* 1005 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 1006 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1011 */     PageContext pageContext = _jspx_page_context;
/* 1012 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1014 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 1015 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 1016 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 1018 */     _jspx_th_c_005fset_005f5.setTarget("${AMActionForm}");
/*      */     
/* 1020 */     _jspx_th_c_005fset_005f5.setProperty("priority");
/*      */     
/* 1022 */     _jspx_th_c_005fset_005f5.setValue("${row[3]}");
/* 1023 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 1024 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 1025 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 1026 */       return true;
/*      */     }
/* 1028 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 1029 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1034 */     PageContext pageContext = _jspx_page_context;
/* 1035 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1037 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 1038 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 1039 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 1041 */     _jspx_th_html_005ftext_005f5.setProperty("priority");
/*      */     
/* 1043 */     _jspx_th_html_005ftext_005f5.setSize("25");
/*      */     
/* 1045 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext default");
/*      */     
/* 1047 */     _jspx_th_html_005ftext_005f5.setMaxlength("40");
/* 1048 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 1049 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 1050 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 1051 */       return true;
/*      */     }
/* 1053 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 1054 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1059 */     PageContext pageContext = _jspx_page_context;
/* 1060 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1062 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 1063 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 1064 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 1066 */     _jspx_th_c_005fset_005f6.setTarget("${AMActionForm}");
/*      */     
/* 1068 */     _jspx_th_c_005fset_005f6.setProperty("password");
/* 1069 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 1070 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 1071 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f6);
/* 1072 */       return true;
/*      */     }
/* 1074 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f6);
/* 1075 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1080 */     PageContext pageContext = _jspx_page_context;
/* 1081 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1083 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(PasswordTag.class);
/* 1084 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 1085 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 1087 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 1089 */     _jspx_th_html_005fpassword_005f0.setSize("25");
/*      */     
/* 1091 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext default");
/*      */     
/* 1093 */     _jspx_th_html_005fpassword_005f0.setMaxlength("40");
/* 1094 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 1095 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 1096 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 1097 */       return true;
/*      */     }
/* 1099 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 1100 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1105 */     PageContext pageContext = _jspx_page_context;
/* 1106 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1108 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 1109 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 1110 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 1112 */     _jspx_th_c_005fset_005f7.setTarget("${AMActionForm}");
/*      */     
/* 1114 */     _jspx_th_c_005fset_005f7.setProperty("sendmail");
/*      */     
/* 1116 */     _jspx_th_c_005fset_005f7.setValue("${row[10]}");
/* 1117 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 1118 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 1119 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f7);
/* 1120 */       return true;
/*      */     }
/* 1122 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f7);
/* 1123 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1128 */     PageContext pageContext = _jspx_page_context;
/* 1129 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1131 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 1132 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 1133 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 1135 */     _jspx_th_html_005fselect_005f1.setProperty("sendmail");
/*      */     
/* 1137 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext default actionSelectCSS");
/* 1138 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 1139 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 1140 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 1141 */         out = _jspx_page_context.pushBody();
/* 1142 */         _jspx_th_html_005fselect_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1143 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1146 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 1147 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 1148 */           return true;
/* 1149 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 1150 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 1151 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1154 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 1155 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1158 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 1159 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 1160 */       return true;
/*      */     }
/* 1162 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 1163 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1168 */     PageContext pageContext = _jspx_page_context;
/* 1169 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1171 */     org.apache.struts.taglib.html.OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (org.apache.struts.taglib.html.OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.OptionsCollectionTag.class);
/* 1172 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 1173 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 1175 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("applications");
/* 1176 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 1177 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 1178 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 1179 */       return true;
/*      */     }
/* 1181 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 1182 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1187 */     PageContext pageContext = _jspx_page_context;
/* 1188 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1190 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 1191 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 1192 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1194 */     _jspx_th_html_005ftext_005f6.setProperty("toaddress");
/*      */     
/* 1196 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext default");
/*      */     
/* 1198 */     _jspx_th_html_005ftext_005f6.setSize("25");
/* 1199 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 1200 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 1201 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 1202 */       return true;
/*      */     }
/* 1204 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 1205 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1210 */     PageContext pageContext = _jspx_page_context;
/* 1211 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1213 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 1214 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 1215 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1217 */     _jspx_th_html_005ftext_005f7.setProperty("technician");
/*      */     
/* 1219 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext default");
/*      */     
/* 1221 */     _jspx_th_html_005ftext_005f7.setSize("25");
/*      */     
/* 1223 */     _jspx_th_html_005ftext_005f7.setMaxlength("40");
/* 1224 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 1225 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 1226 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 1227 */       return true;
/*      */     }
/* 1229 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 1230 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1235 */     PageContext pageContext = _jspx_page_context;
/* 1236 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1238 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 1239 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/* 1240 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1242 */     _jspx_th_html_005ftext_005f8.setProperty("instance");
/*      */     
/* 1244 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext default");
/*      */     
/* 1246 */     _jspx_th_html_005ftext_005f8.setSize("25");
/*      */     
/* 1248 */     _jspx_th_html_005ftext_005f8.setMaxlength("40");
/* 1249 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/* 1250 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/* 1251 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 1252 */       return true;
/*      */     }
/* 1254 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 1255 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\EditManagedServer_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */