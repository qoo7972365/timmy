/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.EqualTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ 
/*      */ public final class Popup_005fVMActions_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   26 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */   private static java.util.Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   58 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   62 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   77 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   78 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   79 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   80 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   81 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   82 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   85 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   89 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   90 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*   91 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/*   92 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/*   93 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*   94 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*   95 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*   96 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
/*   97 */     this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fmessage.release();
/*   98 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*   99 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*  100 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.release();
/*  101 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/*  102 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/*  103 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  104 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.release();
/*  105 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  106 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  107 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.release();
/*  108 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  109 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*  110 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  117 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  120 */     JspWriter out = null;
/*  121 */     Object page = this;
/*  122 */     JspWriter _jspx_out = null;
/*  123 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  127 */       response.setContentType("text/html;charset=UTF-8");
/*  128 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  130 */       _jspx_page_context = pageContext;
/*  131 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  132 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  133 */       session = pageContext.getSession();
/*  134 */       out = pageContext.getOut();
/*  135 */       _jspx_out = out;
/*      */       
/*  137 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<link href=\"/images/");
/*  138 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  140 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*      */       
/*  142 */       com.adventnet.appmanager.servlets.AMInitializationServlet osName = new com.adventnet.appmanager.servlets.AMInitializationServlet();
/*      */       
/*  144 */       out.write("\n<script>\n\n  function callAction()\n  {\n\t showDiv(\"takeaction\");\n  }\n  function removeAction()\n  {\n     hideDiv(\"takeaction\"); \n  }\n  \n \n//select Target JVM Control\nfunction getResourceForSelectionType()\n{\n var selecttionType=document.getElementsByName('logConfig')[0].value;  //NO I18N\n\tif(selecttionType=='1')\n\t{\n\t document.getElementById('mg').style.display='none';\n\t document.getElementById('jre').style.display='none';\n\t   document.getElementById('hypervhost').style.display='none'\n\t document.getElementById('host').style.display='none';\n\t document.getElementById('hyperVVMs').style.display='none';\n\t}\n\telse if(selecttionType=='2' || selecttionType=='7')\n\t{\n\t document.getElementById('jre').style.display='none';\n\t document.getElementById('host').style.display='none';\n\t   document.getElementById('hypervhost').style.display='none';\n\t document.getElementById('mg').style.display='table-row';\n\t document.getElementById('hyperVVMs').style.display='none';\n\t}\n\telse if(selecttionType=='3' || selecttionType=='8')\n\t{\n\t document.getElementById('mg').style.display='none';\n");
/*  145 */       out.write("\t document.getElementById('jre').style.display='none';\n\t   document.getElementById('hypervhost').style.display='none';\n\t document.getElementById('host').style.display='table-row';\n\t document.getElementById('hyperVVMs').style.display='none'; \n\t\n\t}\n\telse if((selecttionType=='4' && document.AMActionForm.hostType[0].checked==true) || selecttionType=='9')\n\t{\n\t\tdocument.getElementById('mg').style.display='none';\n                 document.getElementById('host').style.display='none';\n                 document.getElementById('jre').style.display='table-row'\n                  document.getElementById('hypervhost').style.display='none';\n                 document.getElementById('hyperVVMs').style.display='none';\n\n\t}\n\telse if(selecttionType=='4' && document.AMActionForm.hostType[1].checked==true )\n                {\n\n                document.getElementById('mg').style.display='none';\n                 document.getElementById('host').style.display='none';\n                 document.getElementById('hyperVVMs').style.display='table-row'\n");
/*  146 */       out.write("                  document.getElementById('hypervhost').style.display='none';\n                 document.getElementById('jre').style.display='none';\n        }\n\n       else if(selecttionType=='5')\n       \t{\n       \t document.getElementById('mg').style.display='none';\n       \t document.getElementById('host').style.display='none';\n       \t document.getElementById('jre').style.display='none';\n       \t  document.getElementById('hypervhost').style.display='table-row';\n\t document.getElementById('hyperVVMs').style.display='none'; \n\t}\n     \n }\n \n function validateAndSubmit()\n {\n\t if((document.AMActionForm.displayname.value).trim()=='')\n\t\t{\n\t\talert(\"");
/*  147 */       out.print(FormatUtil.getString("am.webclient.newaction.alertdisplayname"));
/*  148 */       out.write("\");\n\t\tdocument.AMActionForm.displayname.focus();\n\t\t return false;\n\t\t}\n\t\t\n\t\tif(!checkSpecialCharacter(document.AMActionForm.displayname.value,'");
/*  149 */       out.print(FormatUtil.getString("am.webclient.specialchar.alert.displayname"));
/*  150 */       out.write("')) {\n\t\t\treturn false;\n    \t\t}\n\n\t\tif((document.AMActionForm.tddelay.value).trim()=='')\n\t\t{\n\t\t\t  \t\t\n\t               alert(\"");
/*  151 */       out.print(FormatUtil.getString("am.vm.action.vmtimeout"));
/*  152 */       out.write("\");\n\t\t\tdocument.AMActionForm.tddelay.focus();\n\t\t\t return false;\n\t\t}\n\t\t\n\t\tif( isNaN(document.AMActionForm.tddelay.value) || ((document.AMActionForm.tddelay.value)*1) <= 0 )\n\t\t{\n\t\t\t  \t\t\n\t               alert(\"");
/*  153 */       out.print(FormatUtil.getString("am.vm.action.vmtimeout.invalid"));
/*  154 */       out.write("\");\n\t\t\tdocument.AMActionForm.tddelay.focus();\n\t\t\t return false;\n\t\t}\n\n\t\tif(document.AMActionForm.sendmail.value=='')\n\t\t{\n\t\talert(\"");
/*  155 */       out.print(FormatUtil.getString("am.javaruntime.action.createmail"));
/*  156 */       out.write("\");\n\t\t return false;\n\t\t}\n\n\t\tif(document.AMActionForm.logConfig.value=='3' && document.AMActionForm.selectedhost.value=='')\n\t\t{\n\t\t alert(\"");
/*  157 */       out.print(FormatUtil.getString("am.vm.action.select.esxhost"));
/*  158 */       out.write("\");\n\t\t return false;\n\t\t} \n\t\tif(document.AMActionForm.logConfig.value=='8' && document.AMActionForm.selectedhost.value=='')\n\t\t{\n\t\t alert(\"");
/*  159 */       out.print(FormatUtil.getString("am.docker.container.action.select.alert.text"));
/*  160 */       out.write("\");\n\t\t return false;\n\t\t} \n\t\tif(document.AMActionForm.logConfig.value=='9' && document.AMActionForm.selectedjre.value=='')\n\t\t{\n\t\t alert(\"");
/*  161 */       out.print(FormatUtil.getString("am.docker.container.action.select.docker.alert.text"));
/*  162 */       out.write("\");\n\t\t return false;\n\t\t} \n\t\tif(document.AMActionForm.logConfig.value=='5' && document.AMActionForm.selectedhypervhost.value=='')\n\t\t{\n\t\t alert(\"");
/*  163 */       out.print(FormatUtil.getString("am.vm.action.select.hypervhost"));
/*  164 */       out.write("\");\n\t\t return false;\n\t\t} \n\t\n\t\tif(document.AMActionForm.logConfig.value=='4')\n        \t{\n\t          //if Esx is selected in HostType without any EsX monitors configured (OR)\n        \t  // HyperV is selected without any HyperVMonitors configured   \n\t         if( (document.AMActionForm.hostType[0].checked &&  document.AMActionForm.selectedjre.value=='') || \n        \t                (document.AMActionForm.hostType[1].checked && document.AMActionForm.selectedHyperVVM.value=='')\n                \t){\n\t         alert(\"");
/*  165 */       out.print(FormatUtil.getString("am.vm.action.selectvm"));
/*  166 */       out.write("\");\n        \t return false;\n         \t}\n        \t}\n\t\n\t\t document.AMActionForm.submit();\n\n\t}\n\nfunction fillOptionsOnChange()\n{\n\n    var hosttype=document.AMActionForm.hostType.value;\n    var comboBx = document.AMActionForm.logConfig;\n     removeElementsInComboBox( comboBx );\n     if( document.AMActionForm.hostType[0].checked==true)\n                {\n                moveOptions(document.AMActionForm.esxHostOptions,comboBx);\n                }\n        else if(document.AMActionForm.hostType[1].checked ==true)\n                {\n                moveOptions(document.AMActionForm.hypervHostoptions,comboBx);\n                }\n                if(document.getElementById('jre')!=null)\n                {\n        document.getElementById('jre').style.display='none';\n        }\n        if(document.getElementById('host')!=null)\n                {\n        document.getElementById('host').style.display='none';\n        }\n        if(document.getElementById('mg')!=null)\n                {\n        document.getElementById('mg').style.display='table-row';\n");
/*  167 */       out.write("                 }\n                if(document.getElementById('hypervhost')!=null)\n                {\n                document.getElementById('hypervhost').style.display='none';\n                }\n                if(document.getElementById('hyperVVMs')!=null)\n                {\n        document.getElementById('hyperVVMs').style.display='none';\n        }\n\n}\n \nfunction removeElementsInComboBox( elementObj )\n        {\n         for(var i=0;i<elementObj.length;)\n                {\n                elementObj.remove(i);\n                }\n        }\n        \nfunction moveOptions( sourceCombo, destinationCombo )\n        {\n        for(var i=0;i<sourceCombo.length;i++)\n                {\n                var opt = document.createElement(\"option\");\n                destinationCombo.options.add( opt );\n        opt.text = sourceCombo[i].text;\n        opt.value = sourceCombo[i].value;\n                }\n        }\n\nfunction fillOptions( hostType )\n        {\n        var comboBx = document.AMActionForm.logConfig;\n        removeElementsInComboBox( comboBx );\n");
/*  168 */       out.write("        if( hostType == 0)\n                {\n                moveOptions(document.AMActionForm.esxHostOptions,comboBx);\n                }\n        else if( hostType == 100 )\n                {\n                moveOptions(document.AMActionForm.hypervHostoptions,comboBx);\n                }\n        else if( hostType == 850 )\n        {\n        moveOptions(document.AMActionForm.containeroptions,comboBx);\n        }\n                if(document.getElementById('jre')!=null)\n                {\n        document.getElementById('jre').style.display='none';\n        }\n\n        ");
/*  169 */       if (request.getParameter("actionid") == null)
/*      */       {
/*  171 */         out.write("\n        if(document.getElementById('host')){\n                document.getElementById('host').style.display='none';\n        }\n\n        if(document.getElementById('mg')){\n                document.getElementById('mg').style.display='table-row';\n        }\n\n        if(document.getElementById('hypervhost')){\n                document.getElementById('hypervhost').style.display='none';\n        }\n        ");
/*      */       } else {
/*  173 */         com.adventnet.appmanager.struts.form.AMActionForm fm1 = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/*      */         
/*  175 */         out.write("\n                logConfig= ");
/*  176 */         out.print(fm1.getLogConfig());
/*  177 */         out.write(" ;\n                document.getElementsByName('logConfig')[0].value=logConfig;\n        ");
/*      */       }
/*  179 */       out.write("\n        }\n\n \n</script>\n\n");
/*      */       
/*  181 */       org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/*  182 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  183 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/*  185 */       _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*  186 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  187 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/*  189 */           out.write(10);
/*  190 */           if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  192 */           out.write(10);
/*  193 */           if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  195 */           out.write(10);
/*  196 */           if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  198 */           out.write(10);
/*  199 */           if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  201 */           out.write(10);
/*  202 */           if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  204 */           out.write("   \n");
/*      */           
/*  206 */           com.adventnet.appmanager.struts.form.AMActionForm fm = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/*  207 */           boolean isContainerAction = request.getAttribute("isContainerAction") != null ? ((Boolean)request.getAttribute("isContainerAction")).booleanValue() : false;
/*      */           
/*  209 */           out.write("\n<title>");
/*  210 */           out.print(FormatUtil.getString("am.javaruntime.action.vm.update.text"));
/*  211 */           out.write(32);
/*  212 */           out.write(58);
/*  213 */           out.write(32);
/*  214 */           out.print(fm.getDisplayname());
/*  215 */           out.write("</title>\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"55\" class=\"darkheaderbg\">\n\t<tr>\n\t\t<td><span class=\"headingboldwhite\">");
/*  216 */           out.print(isContainerAction ? FormatUtil.getString("am.docker.action.update.text") : FormatUtil.getString("am.javaruntime.action.vm.update.text"));
/*  217 */           out.write(32);
/*  218 */           out.write(58);
/*  219 */           out.write(32);
/*  220 */           out.print(fm.getDisplayname());
/*  221 */           out.write("</span></td>\n\t</tr>\n</table>\n\n ");
/*      */           
/*  223 */           MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  224 */           _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/*  225 */           _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  227 */           _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/*  228 */           int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/*  229 */           if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */             for (;;) {
/*  231 */               out.write(" \n\t   ");
/*      */               
/*  233 */               MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  234 */               _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/*  235 */               _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */               
/*  237 */               _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */               
/*  239 */               _jspx_th_html_005fmessages_005f0.setMessage("true");
/*  240 */               int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/*  241 */               if (_jspx_eval_html_005fmessages_005f0 != 0) {
/*  242 */                 String msg = null;
/*  243 */                 if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  244 */                   out = _jspx_page_context.pushBody();
/*  245 */                   _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/*  246 */                   _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                 }
/*  248 */                 msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                 for (;;) {
/*  250 */                   out.write("\n\t\t<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"messagebox\" style=\"margin-top:10px;margin-left:7px\"> \n\t\t\t\t  <tr> \n\t\t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\"></td>\n\t\t\t\t\t<td width=\"95%\" class=\"message\"> ");
/*  251 */                   if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                     return;
/*  253 */                   out.write("</td>\n\t\t\t\t  </tr>\n\t\t\t\t</table>\n\t\t\t\t<br>\n\t\t");
/*  254 */                   int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/*  255 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*  256 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  259 */                 if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  260 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  263 */               if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/*  264 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */               }
/*      */               
/*  267 */               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*  268 */               out.write(10);
/*  269 */               out.write(9);
/*  270 */               int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/*  271 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  275 */           if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/*  276 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */           }
/*      */           
/*  279 */           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*  280 */           out.write(32);
/*  281 */           out.write("\n\t\n<div id=\"actionmessage\" style=\"display:none\"  class='error-text'>\n</div>\n\n");
/*  282 */           if (_jspx_meth_logic_005fmessagesPresent_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  284 */           out.write(32);
/*  285 */           out.write(10);
/*  286 */           if (_jspx_meth_logic_005fmessagesNotPresent_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  288 */           out.write("\n\n  <tr>\n    <td width=\"40%\" class=\"bodytext\">");
/*  289 */           out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/*  290 */           out.write("</td>\n    <td width=\"60%\" class=\"bodytext\"> ");
/*  291 */           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  293 */           out.write("</td>\n  </tr>\n\n<tr>\n");
/*  294 */           if (!isContainerAction) {
/*  295 */             out.write("\n <td width=\"30%\" class=\"bodytext\">");
/*  296 */             out.print(FormatUtil.getString("am.vm.select.hosttype"));
/*  297 */             out.write("</td>\n          <td class=\"bodytext\" colspan=\"2\" valign=\"middle\">\n\t\t  ");
/*  298 */             if (osName.isWindows()) {
/*  299 */               out.write("\n          ");
/*  300 */               if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  302 */               out.write("&nbsp;");
/*  303 */               out.print(FormatUtil.getString("am.vm.host.esx"));
/*  304 */               out.write(" &nbsp;&nbsp;\n          ");
/*  305 */               if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  307 */               out.write("&nbsp;");
/*  308 */               out.print(FormatUtil.getString("am.vm.host.hyperv"));
/*  309 */               out.write(" &nbsp;&nbsp;\n          ");
/*      */             } else {
/*  311 */               out.write("\n\t   \t  ");
/*  312 */               if (_jspx_meth_html_005fradio_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  314 */               out.write("&nbsp;");
/*  315 */               out.print(FormatUtil.getString("am.vm.host.esx"));
/*  316 */               out.write(" &nbsp;&nbsp;\n\t   \t  ");
/*  317 */               if (_jspx_meth_html_005fradio_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  319 */               out.write("&nbsp;");
/*  320 */               out.print(FormatUtil.getString("am.vm.host.hyperv"));
/*  321 */               out.write("&nbsp;(");
/*  322 */               out.print(FormatUtil.getString("am.vm.linuxdisabled.buttonoption"));
/*  323 */               out.write(")  &nbsp;&nbsp;\n \t  ");
/*      */             }
/*  325 */             out.write(10);
/*  326 */             out.write(32);
/*  327 */             out.write(9);
/*      */           } else {
/*  329 */             out.write(32);
/*  330 */             out.write(32);
/*  331 */             out.write(32);
/*  332 */             if (_jspx_meth_html_005fhidden_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  334 */             out.write("\n          ");
/*      */           }
/*  336 */           out.write("      </tr>\n\n  \n  <tr>\n<td width=\"30%\" class=\"bodytext\">");
/*  337 */           out.print(FormatUtil.getString("am.javaruntime.action.selecttype"));
/*  338 */           out.write("</td>\t\n\t  <td class=\"bodytext\" colspan=\"3\" valign=\"middle\">\n\t  \n\t  ");
/*  339 */           if (_jspx_meth_html_005fradio_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  341 */           out.write("&nbsp;");
/*  342 */           out.print(isContainerAction ? FormatUtil.getString("am.docker.container.action.start") : FormatUtil.getString("am.vm.action.startvm"));
/*  343 */           out.write(" &nbsp;&nbsp;\n\t  ");
/*  344 */           if (_jspx_meth_html_005fradio_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  346 */           out.write("&nbsp;");
/*  347 */           out.print(isContainerAction ? FormatUtil.getString("am.docker.container.action.stop") : FormatUtil.getString("am.vm.action.stopvm"));
/*  348 */           out.write(" &nbsp;&nbsp;\n\t  ");
/*  349 */           if (_jspx_meth_html_005fradio_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  351 */           out.write("&nbsp;");
/*  352 */           out.print(isContainerAction ? FormatUtil.getString("am.docker.container.action.restart") : FormatUtil.getString("am.vm.action.restartvm"));
/*  353 */           out.write(" </td>\n\t  \n\t</tr>\n \n<tr>\n\t  <td class=\"bodytext\" width=\"23%\" valign=\"middle\">");
/*  354 */           out.print(FormatUtil.getString("am.vm.action.targetvm"));
/*  355 */           out.write("</td>\n\t  <td colspan=\"2\" align=\"left\">\n\t\t<div id=\"dummy\" style=\"display:none\">\n                        <!-- dummy combo box supporting esx host type. -->\n                        <select name=\"esxHostOptions\">\n                                <option value=\"2\">");
/*  356 */           out.print(FormatUtil.getString("am.vm.action.type2"));
/*  357 */           out.write("</option>\n                                <option value=\"3\">");
/*  358 */           out.print(FormatUtil.getString("am.vm.action.type3"));
/*  359 */           out.write("</option>\n                                <option value=\"4\">");
/*  360 */           out.print(FormatUtil.getString("am.vm.action.type4"));
/*  361 */           out.write("</option>\n                        </select>\n\n                        <!-- dummy combo box supporting hyperv host type. -->\n                                <select name=\"hypervHostoptions\">\n                                        <option value=\"2\">");
/*  362 */           out.print(FormatUtil.getString("am.vm.action.type2"));
/*  363 */           out.write("</option>\n                                        <option value=\"5\">");
/*  364 */           out.print(FormatUtil.getString("am.vm.action.type5"));
/*  365 */           out.write("</option>\n                                        <option value=\"4\">");
/*  366 */           out.print(FormatUtil.getString("am.vm.action.type4"));
/*  367 */           out.write("</option>\n                                </select>\n                                <select name=\"containeroptions\">\n                                        <option value=\"7\">");
/*  368 */           out.print(FormatUtil.getString("am.docker.container.action.select.mg"));
/*  369 */           out.write("</option>\n\t\t\t\t\t\t\t\t\t\t<option value=\"8\">");
/*  370 */           out.print(FormatUtil.getString("am.docker.container.action.select.docker"));
/*  371 */           out.write("</option>\n\t\t\t\t\t\t\t\t\t\t<option value=\"9\">");
/*  372 */           out.print(FormatUtil.getString("am.docker.container.action.select.container"));
/*  373 */           out.write("</option>\n                                </select>\n                </div>\n\n\t  \t\n\t\t\t\n\t\t \t");
/*      */           
/*  375 */           SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/*  376 */           _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/*  377 */           _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  379 */           _jspx_th_html_005fselect_005f0.setProperty("logConfig");
/*      */           
/*  381 */           _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */           
/*  383 */           _jspx_th_html_005fselect_005f0.setStyle("width:242px;vertical-align:middle;");
/*      */           
/*  385 */           _jspx_th_html_005fselect_005f0.setOnchange("javascript:getResourceForSelectionType();");
/*  386 */           int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/*  387 */           if (_jspx_eval_html_005fselect_005f0 != 0) {
/*  388 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  389 */               out = _jspx_page_context.pushBody();
/*  390 */               _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/*  391 */               _jspx_th_html_005fselect_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  394 */               out.write("\n\t\t\t<!--");
/*      */               
/*  396 */               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  397 */               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/*  398 */               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  400 */               _jspx_th_html_005foption_005f0.setValue("1");
/*  401 */               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/*  402 */               if (_jspx_eval_html_005foption_005f0 != 0) {
/*  403 */                 if (_jspx_eval_html_005foption_005f0 != 1) {
/*  404 */                   out = _jspx_page_context.pushBody();
/*  405 */                   _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/*  406 */                   _jspx_th_html_005foption_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  409 */                   out.print(FormatUtil.getString("am.vm.action.type1"));
/*  410 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/*  411 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  414 */                 if (_jspx_eval_html_005foption_005f0 != 1) {
/*  415 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  418 */               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/*  419 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */               }
/*      */               
/*  422 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/*  423 */               out.write("-->\n\t\t\t");
/*      */               
/*  425 */               OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  426 */               _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/*  427 */               _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/*  429 */               _jspx_th_html_005foption_005f1.setValue("2");
/*  430 */               int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/*  431 */               if (_jspx_eval_html_005foption_005f1 != 0) {
/*  432 */                 if (_jspx_eval_html_005foption_005f1 != 1) {
/*  433 */                   out = _jspx_page_context.pushBody();
/*  434 */                   _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/*  435 */                   _jspx_th_html_005foption_005f1.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/*  438 */                   out.print(FormatUtil.getString("am.vm.action.type2"));
/*  439 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/*  440 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  443 */                 if (_jspx_eval_html_005foption_005f1 != 1) {
/*  444 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  447 */               if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/*  448 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */               }
/*      */               
/*  451 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/*  452 */               out.write("\n\t\t\t\n\t\t\t");
/*  453 */               int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/*  454 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  457 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  458 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  461 */           if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/*  462 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */           }
/*      */           
/*  465 */           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/*  466 */           out.write("\n\t\t\t<!-- Add corresponding options. -->\n             ");
/*  467 */           if (_jspx_meth_logic_005fequal_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  469 */           out.write("\n              ");
/*  470 */           if (_jspx_meth_logic_005fequal_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  472 */           out.write("\n \t\t\t");
/*  473 */           if (_jspx_meth_logic_005fequal_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  475 */           out.write("\n\t </td>\n</tr> \n<tr id=\"mg\" style=\"display:none\">\n\t<td colspan=\"2\"> \n\t\t<table border=\"0\" width=\"100%\">\n\t\t<tr class=\"bodytext\">\n\t\t\t<td width=\"40%\" style=\"padding-top:1px;padding-bottom:2px;\" align=\"left\">");
/*  476 */           out.print(FormatUtil.getString("am.reporttab.selectmg.text"));
/*  477 */           out.write(":</td>\n\t\t\t<td width=\"60%\" style=\"padding-top:1px;padding-bottom:2px;padding-left:2px\" align=\"left\">\n\t\t\t<select name=\"selectedMG\"  class=\"formtext\" style=\"width:150px\">\n\t\t\t\t ");
/*      */           
/*  479 */           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/*  480 */           _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/*  481 */           _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  483 */           _jspx_th_logic_005fnotEmpty_005f0.setName("applications");
/*  484 */           int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/*  485 */           if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */             for (;;) {
/*  487 */               out.write("\n\t\t\t\t     ");
/*      */               
/*  489 */               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  490 */               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/*  491 */               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */               
/*  493 */               _jspx_th_logic_005fiterate_005f0.setName("applications");
/*      */               
/*  495 */               _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */               
/*  497 */               _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */               
/*  499 */               _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*  500 */               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/*  501 */               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/*  502 */                 java.util.ArrayList row = null;
/*  503 */                 Integer j = null;
/*  504 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  505 */                   out = _jspx_page_context.pushBody();
/*  506 */                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/*  507 */                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                 }
/*  509 */                 row = (java.util.ArrayList)_jspx_page_context.findAttribute("row");
/*  510 */                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                 for (;;) {
/*  512 */                   out.write("\n\t\t\t\t              ");
/*      */                   
/*  514 */                   String selected = "";
/*  515 */                   String currentmg = (String)row.get(1);
/*  516 */                   String selectmg = fm.getSelectedMG();
/*  517 */                   if ((selectmg != null) && (selectmg.equals(currentmg)))
/*      */                   {
/*  519 */                     selected = "selected=\"selected\"";
/*      */                   }
/*      */                   
/*  522 */                   out.write("\n\t\t\t\t \t      <option value=\"");
/*  523 */                   out.print((String)row.get(1));
/*  524 */                   out.write(34);
/*  525 */                   out.write(32);
/*  526 */                   out.print(selected);
/*  527 */                   out.write(62);
/*  528 */                   out.print(row.get(0));
/*  529 */                   out.write("</option>\n\t\t\t\t     ");
/*  530 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/*  531 */                   row = (java.util.ArrayList)_jspx_page_context.findAttribute("row");
/*  532 */                   j = (Integer)_jspx_page_context.findAttribute("j");
/*  533 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  536 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  537 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  540 */               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/*  541 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */               }
/*      */               
/*  544 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*  545 */               out.write("\n\t\t\t\t ");
/*  546 */               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/*  547 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  551 */           if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/*  552 */             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */           }
/*      */           
/*  555 */           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*  556 */           out.write("\n      \t     </select>\n\t\t\t   </td>\n\t\t\t   </tr>\n\t\t   </table>\n\t</td>\n</tr>\n \n<tr id=\"host\" style=\"display:none\">\n\t<td colspan=\"2\"> \n\t\t<table border=\"0\" width=\"100%\">\n\t\t<tr class=\"bodytext\">\n\t\t\t<td width=\"40%\" style=\"padding-top:1px;padding-bottom:2px;\" align=\"left\">");
/*  557 */           out.print(FormatUtil.getString("am.vm.action.select.esxhost"));
/*  558 */           out.write(":</td>\n\t\t\t<td width=\"60%\" style=\"padding-top:1px;padding-bottom:2px;2px;padding-left:2px\" align=\"left\">\n\t\t\t   ");
/*  559 */           if (_jspx_meth_logic_005fnotEmpty_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  561 */           out.write("\t\t\n\n\t\t\t\t \n\t\t\t\t");
/*      */           
/*  563 */           EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/*  564 */           _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/*  565 */           _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  567 */           _jspx_th_logic_005fempty_005f0.setName("hostlist");
/*  568 */           int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/*  569 */           if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */             for (;;) {
/*  571 */               out.write("\n\t\t\t\t\t <input type=\"hidden\" name=\"selectedhost\" value=\"\">\n\t\t\t\t\t");
/*  572 */               out.print(FormatUtil.getString("am.vm.action.noesx"));
/*  573 */               out.write("\n\t\t\t\t");
/*  574 */               int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/*  575 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  579 */           if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/*  580 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */           }
/*      */           
/*  583 */           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/*  584 */           out.write("\n\t\t\t   </td>\n\t\t\t   </tr>\n\t\t\t   \n\t\t   </table>\n\t</td>\n</tr>\t\n\n<tr id=\"hyperVVMs\" style=\"display:none\">\n\n        <td width=\"45%\" style=\"padding-top:1px;padding-bottom:2px;\" align=\"left\">");
/*  585 */           out.print(FormatUtil.getString("am.vm.action.selectvm"));
/*  586 */           out.write(":\n                        &nbsp;\n                                ");
/*  587 */           if (_jspx_meth_logic_005fnotEmpty_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  589 */           out.write("\n\n                                ");
/*      */           
/*  591 */           EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/*  592 */           _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/*  593 */           _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  595 */           _jspx_th_logic_005fempty_005f1.setName("hypervVMList");
/*  596 */           int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/*  597 */           if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */             for (;;) {
/*  599 */               out.write("\n                                <input type=\"hidden\" name=\"selectedHyperVVM\" value=\"\">\n                                        ");
/*  600 */               out.print(FormatUtil.getString("am.vm.action.novm"));
/*  601 */               out.write("\n                                ");
/*  602 */               int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/*  603 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  607 */           if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/*  608 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */           }
/*      */           
/*  611 */           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/*  612 */           out.write("\n                           </td>\n       </tr>\n\t\n \t<tr id=\"hypervhost\" style=\"display:none\">\n\t\t\t\t\t<td colspan=\"2\">\n\t\t\t\t\t\t<table border=\"0\" width=\"100%\">\n\t\t\t\t\t\t<tr class=\"bodytext\">\n\t\t\t\t\t\t\t<td width=\"45%\" style=\"padding-top:1px;padding-bottom:2px;\" align=\"left\">");
/*  613 */           out.print(FormatUtil.getString("am.vm.action.select.hypervhost"));
/*  614 */           out.write(":</td>\n\t\t\t\t\t\t\t<td width=\"55%\" style=\"padding-top:1px;padding-bottom:2px;\" align=\"left\">\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t   ");
/*  615 */           if (_jspx_meth_logic_005fnotEmpty_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  617 */           out.write("\n\t\t\t\t\n\t\t\t\t\n\t\t\t\t\t\t\t\t");
/*      */           
/*  619 */           EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/*  620 */           _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/*  621 */           _jspx_th_logic_005fempty_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  623 */           _jspx_th_logic_005fempty_005f2.setName("hypervhostlist");
/*  624 */           int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/*  625 */           if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */             for (;;) {
/*  627 */               out.write("\n\t\t\t\t\t\t\t\t\t <input type=\"hidden\" name=\"selectedhypervhost\" value=\"\">\n\t\t\t\t\t\t\t\t\t ");
/*  628 */               out.print(FormatUtil.getString("am.vm.action.nohyperv"));
/*  629 */               out.write("\n\t\t\t\t\t\t\t\t");
/*  630 */               int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/*  631 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  635 */           if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/*  636 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2); return;
/*      */           }
/*      */           
/*  639 */           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/*  640 */           out.write("\n\t\t\t\t\t\t\t   </td>\n\t\t\t\t\t\t\t   </tr>\n\t\t\t\t\t\t   </table>\n\t\t\t\t\t</td>\n\t\t\t</tr>\n\t\n\t<tr id=\"jre\" style=\"display:none\">\n\t<td colspan=\"2\"> \n\t\t<table border=\"0\" width=\"100%\">\n\t\t<tr class=\"bodytext\">\n\t\t\t<td width=\"40%\" style=\"padding-top:1px;padding-bottom:2px;\" align=\"left\">");
/*  641 */           out.print(FormatUtil.getString("am.vm.action.selectvm"));
/*  642 */           out.write(":</td>\n\t\t\t<td width=\"60%\" style=\"padding-top:1px;padding-bottom:2px;2px;padding-left:2px\" align=\"left\">\n\t\t\t\t");
/*  643 */           if (_jspx_meth_logic_005fnotEmpty_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  645 */           out.write("\n\t\t\t\t\n\t\t\t\t");
/*      */           
/*  647 */           EmptyTag _jspx_th_logic_005fempty_005f3 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/*  648 */           _jspx_th_logic_005fempty_005f3.setPageContext(_jspx_page_context);
/*  649 */           _jspx_th_logic_005fempty_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  651 */           _jspx_th_logic_005fempty_005f3.setName("jrelist");
/*  652 */           int _jspx_eval_logic_005fempty_005f3 = _jspx_th_logic_005fempty_005f3.doStartTag();
/*  653 */           if (_jspx_eval_logic_005fempty_005f3 != 0) {
/*      */             for (;;) {
/*  655 */               out.write("\n\t\t\t\t<input type=\"hidden\" name=\"selectedjre\" value=\"\">\n\t\t\t\t\t");
/*  656 */               out.print(FormatUtil.getString("am.vm.action.novm"));
/*  657 */               out.write("\n\t\t\t\t");
/*  658 */               int evalDoAfterBody = _jspx_th_logic_005fempty_005f3.doAfterBody();
/*  659 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  663 */           if (_jspx_th_logic_005fempty_005f3.doEndTag() == 5) {
/*  664 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f3); return;
/*      */           }
/*      */           
/*  667 */           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f3);
/*  668 */           out.write("\n\t\t\t\t\n\t\t\t   </td>\n\t\t\t   </tr>\n\t\t\t   \n\t\t   </table>\n\n </td>\n </tr>\n\t<tr>\n    <td class=\"bodytext\"><span id=\"tit2\">");
/*  669 */           out.print(FormatUtil.getString("am.vm.action.timeout"));
/*  670 */           out.write("</span></td>\n    <td class=\"bodytext\">");
/*  671 */           if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  673 */           out.write("</td>\n  </tr>\n\n \t\n <tr>\t\n\t  <td class=\"bodytext\" width=\"23%\">");
/*  674 */           out.print(FormatUtil.getString("am.vm.eaction.associate"));
/*  675 */           out.write("</td>\n\t  <td class=\"bodytext\" width=\"37%\" valign=\"middle\" >\n\t\t\t\t\t");
/*  676 */           if (_jspx_meth_html_005fselect_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  678 */           out.write(" \n\t\n        \n\t </td>\n\t \n \n</tr>\n</table>\n \n\t <table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\" style=\"margin-top:0px;margin-left:7px\">\n\t  <tr> \n\t    <td width=\"48%\" class=\"tablebottom\" align=\"right\"><input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/*  679 */           out.print(FormatUtil.getString("am.webclient.common.update.text"));
/*  680 */           out.write("\" onClick=\"javascript:validateAndSubmit();\"></td>\n\t    <td width=\"50%\" class=\"tablebottom\"><input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/*  681 */           out.print(FormatUtil.getString("am.webclient.common.close.text"));
/*  682 */           out.write("\" onClick=\"javascript:window.parent.close();\"></td>\n\t  </tr>\n\t</table>\n   <br>\n* ");
/*  683 */           out.print(FormatUtil.getString("am.webclient.newaction.trapfieldsnote"));
/*  684 */           out.write("  \n        ");
/*  685 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  686 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  690 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  691 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else {
/*  694 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  695 */         out.write("\n   \n<script>\ngetResourceForSelectionType()\n\n</script>\n");
/*      */       }
/*  697 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  698 */         out = _jspx_out;
/*  699 */         if ((out != null) && (out.getBufferSize() != 0))
/*  700 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  701 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  704 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  710 */     PageContext pageContext = _jspx_page_context;
/*  711 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  713 */     org.apache.taglibs.standard.tag.el.core.OutTag _jspx_th_c_005fout_005f0 = (org.apache.taglibs.standard.tag.el.core.OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(org.apache.taglibs.standard.tag.el.core.OutTag.class);
/*  714 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  715 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  717 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  719 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  720 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  721 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  722 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  723 */       return true;
/*      */     }
/*  725 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  726 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  731 */     PageContext pageContext = _jspx_page_context;
/*  732 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  734 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/*  735 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/*  736 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  738 */     _jspx_th_am_005fhiddenparam_005f0.setName("haid");
/*  739 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/*  740 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/*  741 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/*  742 */       return true;
/*      */     }
/*  744 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/*  745 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  750 */     PageContext pageContext = _jspx_page_context;
/*  751 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  753 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/*  754 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/*  755 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  757 */     _jspx_th_am_005fhiddenparam_005f1.setName("redirectTo");
/*  758 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/*  759 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/*  760 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/*  761 */       return true;
/*      */     }
/*  763 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/*  764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  769 */     PageContext pageContext = _jspx_page_context;
/*  770 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  772 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/*  773 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/*  774 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  776 */     _jspx_th_html_005fhidden_005f0.setProperty("method");
/*      */     
/*  778 */     _jspx_th_html_005fhidden_005f0.setValue("editVMAction");
/*  779 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/*  780 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/*  781 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/*  782 */       return true;
/*      */     }
/*  784 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/*  785 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  790 */     PageContext pageContext = _jspx_page_context;
/*  791 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  793 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/*  794 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/*  795 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  797 */     _jspx_th_html_005fhidden_005f1.setProperty("id");
/*  798 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/*  799 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/*  800 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/*  801 */       return true;
/*      */     }
/*  803 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/*  804 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  809 */     PageContext pageContext = _jspx_page_context;
/*  810 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  812 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/*  813 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/*  814 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  816 */     _jspx_th_html_005fhidden_005f2.setProperty("update");
/*      */     
/*  818 */     _jspx_th_html_005fhidden_005f2.setValue("true");
/*  819 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/*  820 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/*  821 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/*  822 */       return true;
/*      */     }
/*  824 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/*  825 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  830 */     PageContext pageContext = _jspx_page_context;
/*  831 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  833 */     org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
/*  834 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/*  835 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/*  837 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*  838 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/*  839 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/*  840 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/*  841 */       return true;
/*      */     }
/*  843 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/*  844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fmessagesPresent_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  849 */     PageContext pageContext = _jspx_page_context;
/*  850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  852 */     MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  853 */     _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/*  854 */     _jspx_th_logic_005fmessagesPresent_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  856 */     _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/*  857 */     int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/*  858 */     if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*      */       for (;;) {
/*  860 */         out.write(" \t\n<table width=\"98%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtborder\" style=\"margin-top:0px;margin-left:7px\">\n");
/*  861 */         int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/*  862 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  866 */     if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/*  867 */       this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/*  868 */       return true;
/*      */     }
/*  870 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/*  871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fmessagesNotPresent_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  876 */     PageContext pageContext = _jspx_page_context;
/*  877 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  879 */     org.apache.struts.taglib.logic.MessagesNotPresentTag _jspx_th_logic_005fmessagesNotPresent_005f0 = (org.apache.struts.taglib.logic.MessagesNotPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fmessage.get(org.apache.struts.taglib.logic.MessagesNotPresentTag.class);
/*  880 */     _jspx_th_logic_005fmessagesNotPresent_005f0.setPageContext(_jspx_page_context);
/*  881 */     _jspx_th_logic_005fmessagesNotPresent_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  883 */     _jspx_th_logic_005fmessagesNotPresent_005f0.setMessage("true");
/*  884 */     int _jspx_eval_logic_005fmessagesNotPresent_005f0 = _jspx_th_logic_005fmessagesNotPresent_005f0.doStartTag();
/*  885 */     if (_jspx_eval_logic_005fmessagesNotPresent_005f0 != 0) {
/*      */       for (;;) {
/*  887 */         out.write(" \t\n<table width=\"98%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtborder\" style=\"margin-top:10px;margin-left:7px\">\n");
/*  888 */         int evalDoAfterBody = _jspx_th_logic_005fmessagesNotPresent_005f0.doAfterBody();
/*  889 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  893 */     if (_jspx_th_logic_005fmessagesNotPresent_005f0.doEndTag() == 5) {
/*  894 */       this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesNotPresent_005f0);
/*  895 */       return true;
/*      */     }
/*  897 */     this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesNotPresent_005f0);
/*  898 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  903 */     PageContext pageContext = _jspx_page_context;
/*  904 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  906 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/*  907 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/*  908 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  910 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/*  912 */     _jspx_th_html_005ftext_005f0.setSize("40");
/*      */     
/*  914 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */     
/*  916 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/*  917 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/*  918 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/*  919 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  920 */       return true;
/*      */     }
/*  922 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  923 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  928 */     PageContext pageContext = _jspx_page_context;
/*  929 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  931 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/*  932 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/*  933 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  935 */     _jspx_th_html_005fradio_005f0.setProperty("hostType");
/*      */     
/*  937 */     _jspx_th_html_005fradio_005f0.setValue("0");
/*      */     
/*  939 */     _jspx_th_html_005fradio_005f0.setOnclick("fillOptionsOnChange()");
/*  940 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/*  941 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/*  942 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/*  943 */       return true;
/*      */     }
/*  945 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/*  946 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  951 */     PageContext pageContext = _jspx_page_context;
/*  952 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  954 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/*  955 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/*  956 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  958 */     _jspx_th_html_005fradio_005f1.setProperty("hostType");
/*      */     
/*  960 */     _jspx_th_html_005fradio_005f1.setValue("100");
/*      */     
/*  962 */     _jspx_th_html_005fradio_005f1.setOnclick("fillOptionsOnChange()");
/*  963 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/*  964 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/*  965 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/*  966 */       return true;
/*      */     }
/*  968 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/*  969 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  974 */     PageContext pageContext = _jspx_page_context;
/*  975 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  977 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/*  978 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/*  979 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  981 */     _jspx_th_html_005fradio_005f2.setProperty("hostType");
/*      */     
/*  983 */     _jspx_th_html_005fradio_005f2.setValue("0");
/*      */     
/*  985 */     _jspx_th_html_005fradio_005f2.setOnclick("fillOptionsOnChange()");
/*  986 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/*  987 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/*  988 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/*  989 */       return true;
/*      */     }
/*  991 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/*  992 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  997 */     PageContext pageContext = _jspx_page_context;
/*  998 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1000 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.get(RadioTag.class);
/* 1001 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 1002 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1004 */     _jspx_th_html_005fradio_005f3.setProperty("hostType");
/*      */     
/* 1006 */     _jspx_th_html_005fradio_005f3.setValue("100");
/*      */     
/* 1008 */     _jspx_th_html_005fradio_005f3.setOnclick("fillOptionsOnChange()");
/*      */     
/* 1010 */     _jspx_th_html_005fradio_005f3.setDisabled(true);
/* 1011 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 1012 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 1013 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 1014 */       return true;
/*      */     }
/* 1016 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 1017 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1022 */     PageContext pageContext = _jspx_page_context;
/* 1023 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1025 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 1026 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/* 1027 */     _jspx_th_html_005fhidden_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1029 */     _jspx_th_html_005fhidden_005f3.setProperty("hostType");
/* 1030 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/* 1031 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/* 1032 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 1033 */       return true;
/*      */     }
/* 1035 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 1036 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1041 */     PageContext pageContext = _jspx_page_context;
/* 1042 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1044 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 1045 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 1046 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1048 */     _jspx_th_html_005fradio_005f4.setProperty("jtaskMethod");
/*      */     
/* 1050 */     _jspx_th_html_005fradio_005f4.setValue("StartVM");
/* 1051 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 1052 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 1053 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 1054 */       return true;
/*      */     }
/* 1056 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 1057 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1062 */     PageContext pageContext = _jspx_page_context;
/* 1063 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1065 */     RadioTag _jspx_th_html_005fradio_005f5 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 1066 */     _jspx_th_html_005fradio_005f5.setPageContext(_jspx_page_context);
/* 1067 */     _jspx_th_html_005fradio_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1069 */     _jspx_th_html_005fradio_005f5.setProperty("jtaskMethod");
/*      */     
/* 1071 */     _jspx_th_html_005fradio_005f5.setValue("StopVM");
/* 1072 */     int _jspx_eval_html_005fradio_005f5 = _jspx_th_html_005fradio_005f5.doStartTag();
/* 1073 */     if (_jspx_th_html_005fradio_005f5.doEndTag() == 5) {
/* 1074 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 1075 */       return true;
/*      */     }
/* 1077 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 1078 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1083 */     PageContext pageContext = _jspx_page_context;
/* 1084 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1086 */     RadioTag _jspx_th_html_005fradio_005f6 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 1087 */     _jspx_th_html_005fradio_005f6.setPageContext(_jspx_page_context);
/* 1088 */     _jspx_th_html_005fradio_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1090 */     _jspx_th_html_005fradio_005f6.setProperty("jtaskMethod");
/*      */     
/* 1092 */     _jspx_th_html_005fradio_005f6.setValue("RestartVM");
/* 1093 */     int _jspx_eval_html_005fradio_005f6 = _jspx_th_html_005fradio_005f6.doStartTag();
/* 1094 */     if (_jspx_th_html_005fradio_005f6.doEndTag() == 5) {
/* 1095 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 1096 */       return true;
/*      */     }
/* 1098 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 1099 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1104 */     PageContext pageContext = _jspx_page_context;
/* 1105 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1107 */     EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 1108 */     _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/* 1109 */     _jspx_th_logic_005fequal_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1111 */     _jspx_th_logic_005fequal_005f0.setName("AMActionForm");
/*      */     
/* 1113 */     _jspx_th_logic_005fequal_005f0.setProperty("hostType");
/*      */     
/* 1115 */     _jspx_th_logic_005fequal_005f0.setValue("0");
/* 1116 */     int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/* 1117 */     if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */       for (;;) {
/* 1119 */         out.write("\n                     <script>\n                             fillOptions(0);\n                     </script>\n             ");
/* 1120 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/* 1121 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1125 */     if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/* 1126 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 1127 */       return true;
/*      */     }
/* 1129 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 1130 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1135 */     PageContext pageContext = _jspx_page_context;
/* 1136 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1138 */     EqualTag _jspx_th_logic_005fequal_005f1 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 1139 */     _jspx_th_logic_005fequal_005f1.setPageContext(_jspx_page_context);
/* 1140 */     _jspx_th_logic_005fequal_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1142 */     _jspx_th_logic_005fequal_005f1.setName("AMActionForm");
/*      */     
/* 1144 */     _jspx_th_logic_005fequal_005f1.setProperty("hostType");
/*      */     
/* 1146 */     _jspx_th_logic_005fequal_005f1.setValue("100");
/* 1147 */     int _jspx_eval_logic_005fequal_005f1 = _jspx_th_logic_005fequal_005f1.doStartTag();
/* 1148 */     if (_jspx_eval_logic_005fequal_005f1 != 0) {
/*      */       for (;;) {
/* 1150 */         out.write("\n                     <script>\n                             fillOptions(100);\n                     </script>\n             ");
/* 1151 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f1.doAfterBody();
/* 1152 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1156 */     if (_jspx_th_logic_005fequal_005f1.doEndTag() == 5) {
/* 1157 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 1158 */       return true;
/*      */     }
/* 1160 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 1161 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1166 */     PageContext pageContext = _jspx_page_context;
/* 1167 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1169 */     EqualTag _jspx_th_logic_005fequal_005f2 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 1170 */     _jspx_th_logic_005fequal_005f2.setPageContext(_jspx_page_context);
/* 1171 */     _jspx_th_logic_005fequal_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1173 */     _jspx_th_logic_005fequal_005f2.setName("AMActionForm");
/*      */     
/* 1175 */     _jspx_th_logic_005fequal_005f2.setProperty("hostType");
/*      */     
/* 1177 */     _jspx_th_logic_005fequal_005f2.setValue("850");
/* 1178 */     int _jspx_eval_logic_005fequal_005f2 = _jspx_th_logic_005fequal_005f2.doStartTag();
/* 1179 */     if (_jspx_eval_logic_005fequal_005f2 != 0) {
/*      */       for (;;) {
/* 1181 */         out.write("\n                     <script>\n                             fillOptions(850);\n                     </script>\n             ");
/* 1182 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f2.doAfterBody();
/* 1183 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1187 */     if (_jspx_th_logic_005fequal_005f2.doEndTag() == 5) {
/* 1188 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2);
/* 1189 */       return true;
/*      */     }
/* 1191 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2);
/* 1192 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1197 */     PageContext pageContext = _jspx_page_context;
/* 1198 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1200 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 1201 */     _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 1202 */     _jspx_th_logic_005fnotEmpty_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1204 */     _jspx_th_logic_005fnotEmpty_005f1.setName("hostlist");
/* 1205 */     int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 1206 */     if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */       for (;;) {
/* 1208 */         out.write("\n\t\t\t\t\t\t");
/* 1209 */         if (_jspx_meth_html_005fselect_005f1(_jspx_th_logic_005fnotEmpty_005f1, _jspx_page_context))
/* 1210 */           return true;
/* 1211 */         out.write("\n\t\t\t\t ");
/* 1212 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 1213 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1217 */     if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 1218 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 1219 */       return true;
/*      */     }
/* 1221 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 1222 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_logic_005fnotEmpty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1227 */     PageContext pageContext = _jspx_page_context;
/* 1228 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1230 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1231 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 1232 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f1);
/*      */     
/* 1234 */     _jspx_th_html_005fselect_005f1.setProperty("selectedhost");
/*      */     
/* 1236 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/*      */     
/* 1238 */     _jspx_th_html_005fselect_005f1.setStyle("width:65%");
/* 1239 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 1240 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 1241 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 1242 */         out = _jspx_page_context.pushBody();
/* 1243 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 1244 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1247 */         out.write("\n\t\t\t\t\t\t");
/* 1248 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 1249 */           return true;
/* 1250 */         out.write("        \n\t\t\t\t\t    ");
/* 1251 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 1252 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1255 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 1256 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1259 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 1260 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 1261 */       return true;
/*      */     }
/* 1263 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 1264 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1269 */     PageContext pageContext = _jspx_page_context;
/* 1270 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1272 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1273 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 1274 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 1276 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("hostlist");
/* 1277 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 1278 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 1279 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 1280 */       return true;
/*      */     }
/* 1282 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 1283 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1288 */     PageContext pageContext = _jspx_page_context;
/* 1289 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1291 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 1292 */     _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 1293 */     _jspx_th_logic_005fnotEmpty_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1295 */     _jspx_th_logic_005fnotEmpty_005f2.setName("hypervVMList");
/* 1296 */     int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 1297 */     if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */       for (;;) {
/* 1299 */         out.write("\n                                        ");
/* 1300 */         if (_jspx_meth_html_005fselect_005f2(_jspx_th_logic_005fnotEmpty_005f2, _jspx_page_context))
/* 1301 */           return true;
/* 1302 */         out.write("\n                            ");
/* 1303 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 1304 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1308 */     if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 1309 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 1310 */       return true;
/*      */     }
/* 1312 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 1313 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_logic_005fnotEmpty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1318 */     PageContext pageContext = _jspx_page_context;
/* 1319 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1321 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1322 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 1323 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f2);
/*      */     
/* 1325 */     _jspx_th_html_005fselect_005f2.setProperty("selectedHyperVVM");
/*      */     
/* 1327 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext");
/*      */     
/* 1329 */     _jspx_th_html_005fselect_005f2.setStyle("width:25%");
/* 1330 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 1331 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 1332 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 1333 */         out = _jspx_page_context.pushBody();
/* 1334 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 1335 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1338 */         out.write("\n                                        ");
/* 1339 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 1340 */           return true;
/* 1341 */         out.write("\n                                        ");
/* 1342 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 1343 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1346 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 1347 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1350 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 1351 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 1352 */       return true;
/*      */     }
/* 1354 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 1355 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1360 */     PageContext pageContext = _jspx_page_context;
/* 1361 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1363 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1364 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 1365 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 1367 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("hypervVMList");
/* 1368 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 1369 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 1370 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 1371 */       return true;
/*      */     }
/* 1373 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 1374 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1379 */     PageContext pageContext = _jspx_page_context;
/* 1380 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1382 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 1383 */     _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 1384 */     _jspx_th_logic_005fnotEmpty_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1386 */     _jspx_th_logic_005fnotEmpty_005f3.setName("hypervhostlist");
/* 1387 */     int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 1388 */     if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */       for (;;) {
/* 1390 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 1391 */         if (_jspx_meth_html_005fselect_005f3(_jspx_th_logic_005fnotEmpty_005f3, _jspx_page_context))
/* 1392 */           return true;
/* 1393 */         out.write("\n\t\t\t\t\t\t\t\t ");
/* 1394 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 1395 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1399 */     if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 1400 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 1401 */       return true;
/*      */     }
/* 1403 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 1404 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_logic_005fnotEmpty_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1409 */     PageContext pageContext = _jspx_page_context;
/* 1410 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1412 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1413 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 1414 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f3);
/*      */     
/* 1416 */     _jspx_th_html_005fselect_005f3.setProperty("selectedhypervhost");
/*      */     
/* 1418 */     _jspx_th_html_005fselect_005f3.setStyleClass("formtext");
/*      */     
/* 1420 */     _jspx_th_html_005fselect_005f3.setStyle("width:65%");
/* 1421 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 1422 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 1423 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 1424 */         out = _jspx_page_context.pushBody();
/* 1425 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 1426 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1429 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 1430 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 1431 */           return true;
/* 1432 */         out.write("\n\t\t\t\t\t\t\t\t\t        ");
/* 1433 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 1434 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1437 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 1438 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1441 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 1442 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 1443 */       return true;
/*      */     }
/* 1445 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 1446 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1451 */     PageContext pageContext = _jspx_page_context;
/* 1452 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1454 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1455 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 1456 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 1458 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("hypervhostlist");
/* 1459 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 1460 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 1461 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 1462 */       return true;
/*      */     }
/* 1464 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 1465 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1470 */     PageContext pageContext = _jspx_page_context;
/* 1471 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1473 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f4 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 1474 */     _jspx_th_logic_005fnotEmpty_005f4.setPageContext(_jspx_page_context);
/* 1475 */     _jspx_th_logic_005fnotEmpty_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1477 */     _jspx_th_logic_005fnotEmpty_005f4.setName("jrelist");
/* 1478 */     int _jspx_eval_logic_005fnotEmpty_005f4 = _jspx_th_logic_005fnotEmpty_005f4.doStartTag();
/* 1479 */     if (_jspx_eval_logic_005fnotEmpty_005f4 != 0) {
/*      */       for (;;) {
/* 1481 */         out.write("\n\t\t\t\t\t");
/* 1482 */         if (_jspx_meth_html_005fselect_005f4(_jspx_th_logic_005fnotEmpty_005f4, _jspx_page_context))
/* 1483 */           return true;
/* 1484 */         out.write(" \n\t\t\t    ");
/* 1485 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f4.doAfterBody();
/* 1486 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1490 */     if (_jspx_th_logic_005fnotEmpty_005f4.doEndTag() == 5) {
/* 1491 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 1492 */       return true;
/*      */     }
/* 1494 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 1495 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_logic_005fnotEmpty_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1500 */     PageContext pageContext = _jspx_page_context;
/* 1501 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1503 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1504 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 1505 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f4);
/*      */     
/* 1507 */     _jspx_th_html_005fselect_005f4.setProperty("selectedjre");
/*      */     
/* 1509 */     _jspx_th_html_005fselect_005f4.setStyleClass("formtext");
/*      */     
/* 1511 */     _jspx_th_html_005fselect_005f4.setStyle("width:65%");
/* 1512 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 1513 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 1514 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 1515 */         out = _jspx_page_context.pushBody();
/* 1516 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 1517 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1520 */         out.write("\n\t\t\t\t\t");
/* 1521 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 1522 */           return true;
/* 1523 */         out.write("        \n\t\t\t\t\t");
/* 1524 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 1525 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1528 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 1529 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1532 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 1533 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 1534 */       return true;
/*      */     }
/* 1536 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 1537 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1542 */     PageContext pageContext = _jspx_page_context;
/* 1543 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1545 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1546 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 1547 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 1549 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("jrelist");
/* 1550 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 1551 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 1552 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 1553 */       return true;
/*      */     }
/* 1555 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 1556 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1561 */     PageContext pageContext = _jspx_page_context;
/* 1562 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1564 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 1565 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 1566 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1568 */     _jspx_th_html_005ftext_005f1.setProperty("tddelay");
/*      */     
/* 1570 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 1572 */     _jspx_th_html_005ftext_005f1.setSize("5");
/* 1573 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 1574 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 1575 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1576 */       return true;
/*      */     }
/* 1578 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1579 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1584 */     PageContext pageContext = _jspx_page_context;
/* 1585 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1587 */     SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1588 */     _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 1589 */     _jspx_th_html_005fselect_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1591 */     _jspx_th_html_005fselect_005f5.setProperty("sendmail");
/*      */     
/* 1593 */     _jspx_th_html_005fselect_005f5.setStyleClass("formtext");
/*      */     
/* 1595 */     _jspx_th_html_005fselect_005f5.setStyle("width:65%");
/* 1596 */     int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 1597 */     if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 1598 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 1599 */         out = _jspx_page_context.pushBody();
/* 1600 */         _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 1601 */         _jspx_th_html_005fselect_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1604 */         out.write("\n\t\t\t\t\t\t");
/* 1605 */         if (_jspx_meth_html_005foptionsCollection_005f4(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/* 1606 */           return true;
/* 1607 */         out.write("        \n\t\t\t\t\t");
/* 1608 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 1609 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1612 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 1613 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1616 */     if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 1617 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 1618 */       return true;
/*      */     }
/* 1620 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 1621 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f4(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1626 */     PageContext pageContext = _jspx_page_context;
/* 1627 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1629 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f4 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1630 */     _jspx_th_html_005foptionsCollection_005f4.setPageContext(_jspx_page_context);
/* 1631 */     _jspx_th_html_005foptionsCollection_005f4.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 1633 */     _jspx_th_html_005foptionsCollection_005f4.setProperty("maillist");
/* 1634 */     int _jspx_eval_html_005foptionsCollection_005f4 = _jspx_th_html_005foptionsCollection_005f4.doStartTag();
/* 1635 */     if (_jspx_th_html_005foptionsCollection_005f4.doEndTag() == 5) {
/* 1636 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 1637 */       return true;
/*      */     }
/* 1639 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 1640 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fVMActions_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */