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
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ 
/*      */ public final class Popup_005fTnreadDumpActions_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   25 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonchange_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   56 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   60 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   77 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   78 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   79 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   80 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   81 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   82 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   86 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   87 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*   88 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/*   89 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/*   90 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*   91 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*   92 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*   93 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
/*   94 */     this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fmessage.release();
/*   95 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*   96 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*   97 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonchange_005fnobody.release();
/*   98 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*   99 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/*  100 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  101 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/*  102 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  103 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  104 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.release();
/*  105 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  106 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  113 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  116 */     JspWriter out = null;
/*  117 */     Object page = this;
/*  118 */     JspWriter _jspx_out = null;
/*  119 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  123 */       response.setContentType("text/html;charset=UTF-8");
/*  124 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  126 */       _jspx_page_context = pageContext;
/*  127 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  128 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  129 */       session = pageContext.getSession();
/*  130 */       out = pageContext.getOut();
/*  131 */       _jspx_out = out;
/*      */       
/*  133 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<link href=\"/images/");
/*  134 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  136 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n ");
/*  137 */       String monitorType = request.getParameter("monitorType");
/*  138 */       out.write("\n<script>\n\n  function callAction()\n  {\n\t showDiv(\"takeaction\");\n  }\n  function removeAction()\n  {\n     hideDiv(\"takeaction\"); \n  }\n  \n \n//select Target JVM Control\nfunction getResourceForSelectionType()\n{\n var selecttionType=document.getElementsByName('logConfig')[0].value;  //NO I18N\n\tif(selecttionType=='1')\n\t{\n\t document.getElementById('mg').style.display='none';\n\t document.getElementById('jre').style.display='none';\n\t document.getElementById('host').style.display='none';\n\t}\n\telse if(selecttionType=='2')\n\t{\n\t document.getElementById('jre').style.display='none';\n\t document.getElementById('host').style.display='none';\n\t document.getElementById('mg').style.display='table-row';\n\t}\n\telse if(selecttionType=='3')\n\t{\n\t document.getElementById('mg').style.display='none';\n\t document.getElementById('jre').style.display='none';\n\t document.getElementById('host').style.display='table-row'; \n\t}\n\telse if(selecttionType=='4')\n\t{\n\t document.getElementById('mg').style.display='none';\n\t document.getElementById('host').style.display='none';\n");
/*  139 */       out.write("\t document.getElementById('jre').style.display='table-row' \n\t}\n\telse if(selecttionType=='6')\n\t{\n\t document.getElementById('mg').style.display='table-row';\n\t document.getElementById('ec2instt').style.display='none';\n\t}\n\telse if(selecttionType=='7')\n\t{\n\t document.getElementById('mg').style.display='none';\n\t document.getElementById('ec2instt').style.display='table-row';\n\t}\n \n }\n \n function validateAndSubmit(monitorName)\n {\n\tif((document.AMActionForm.displayname.value).trim()=='')\n\t{\n\talert(\"");
/*  140 */       out.print(FormatUtil.getString("am.webclient.newaction.alertdisplayname"));
/*  141 */       out.write("\");\n\tdocument.AMActionForm.displayname.focus();\n\t return false;\n\t} \n\t\n\tif(!checkSpecialCharacter(document.AMActionForm.displayname.value,'");
/*  142 */       out.print(FormatUtil.getString("am.webclient.specialchar.alert.displayname"));
/*  143 */       out.write("')) {\n\t\t\treturn false;\n    \t}\n\tif(monitorName==\"jre\"){\n\tif((document.AMActionForm.tdcount.value).trim()=='')\n\t{\n\t\t  \t\tif(document.AMActionForm.jtaskMethod[0].checked==true){\n                alert(\"");
/*  144 */       out.print(FormatUtil.getString("am.javaruntime.action.noofthreaddump"));
/*  145 */       out.write("\");\n\t\t}else if(document.AMActionForm.jtaskMethod[1].checked==true){\n\t\t\t\talert(\"");
/*  146 */       out.print(FormatUtil.getString("am.javaruntime.action.noofheapdump"));
/*  147 */       out.write("\");\n\t\t}else if(document.AMActionForm.jtaskMethod[2].checked==true){\n\t\t\t    alert(\"");
/*  148 */       out.print(FormatUtil.getString("am.javaruntime.action.noofgcs"));
/*  149 */       out.write("\");\n\t\t}\n\tdocument.AMActionForm.tdCount.focus();\n\t return false;\n\t} \n\t\n\tif((document.AMActionForm.tddelay.value).trim()=='')\n\t{\n\t\t\t  \t\tif(document.AMActionForm.jtaskMethod[0].checked==true){\n               alert(\"");
/*  150 */       out.print(FormatUtil.getString("am.javaruntime.action.threaddumpdelay"));
/*  151 */       out.write("\");\n\t\t}else if(document.AMActionForm.jtaskMethod[1].checked==true){\n\t\t\t\talert(\"");
/*  152 */       out.print(FormatUtil.getString("am.javaruntime.action.heapdumpdelay"));
/*  153 */       out.write("\");\n\t\t}else if(document.AMActionForm.jtaskMethod[2].checked==true){\n\t\t\t    alert(\"");
/*  154 */       out.print(FormatUtil.getString("am.javaruntime.action.performgcdelay"));
/*  155 */       out.write("\");\n\t\t}\n\tdocument.AMActionForm.tdDelay.focus();\n\t return false;\n\t} \n\t}\n\t\n\tif(document.AMActionForm.sendmail.value=='')\n\t{\n\talert(\"");
/*  156 */       out.print(FormatUtil.getString("Please create an E-Mail action for this Action by clicking New Action link"));
/*  157 */       out.write("\");\n\t return false;\n\t}  \n\t\n  \t \n\tif(document.AMActionForm.logConfig.value=='3' && document.AMActionForm.selectedhost.value=='')\n\t{\n\t alert(\"");
/*  158 */       out.print(FormatUtil.getString("am.javaruntime.action.selecthost"));
/*  159 */       out.write("\");\n\t return false;\n\t} \n\t\n\tif(document.AMActionForm.logConfig.value=='4'  && document.AMActionForm.selectedjre.value=='')\n\t{\n\t alert(\"");
/*  160 */       out.print(FormatUtil.getString("am.javaruntime.action.selectjre"));
/*  161 */       out.write("\");\n\t return false;\n\t} \n\tif(document.AMActionForm.logConfig.value=='7'  && document.AMActionForm.selectedjre.value=='')\n\t{\n\t alert(\"");
/*  162 */       out.print(FormatUtil.getString("no instance is present"));
/*  163 */       out.write("\");\n\t return false;\n\t}\n\t document.AMActionForm.submit();\n \n }\n\n \n  //JRE - to chahge title based of action type selection\n function changetitle()\n  {\n\t\tif(document.AMActionForm.jtaskMethod[0].checked==true){\n            document.getElementById('tit1').innerHTML='");
/*  164 */       out.print(FormatUtil.getString("am.javaruntime.action.nooftd"));
/*  165 */       out.write("';\n\t\t\tdocument.getElementById('tit2').innerHTML='");
/*  166 */       out.print(FormatUtil.getString("am.javaruntime.action.delaybwtd"));
/*  167 */       out.write("';\n\t\t\tdocument.getElementById('tit3').innerHTML='");
/*  168 */       out.print(FormatUtil.getString("am.javaruntime.action.createjtd"));
/*  169 */       out.write("';\n\t\t}else if(document.AMActionForm.jtaskMethod[1].checked==true){\n\t\t    document.getElementById('tit1').innerHTML='");
/*  170 */       out.print(FormatUtil.getString("am.javaruntime.action.noofhd"));
/*  171 */       out.write("';\n\t\t\tdocument.getElementById('tit2').innerHTML='");
/*  172 */       out.print(FormatUtil.getString("am.javaruntime.action.delaybwhd"));
/*  173 */       out.write("';\n\t\t\tdocument.getElementById('tit3').innerHTML='");
/*  174 */       out.print(FormatUtil.getString("am.javaruntime.action.createjhd"));
/*  175 */       out.write("';\n\t\t}else if(document.AMActionForm.jtaskMethod[2].checked==true){\n\t\t\tdocument.getElementById('tit1').innerHTML='");
/*  176 */       out.print(FormatUtil.getString("am.javaruntime.action.noofgc"));
/*  177 */       out.write("';\n\t\t\tdocument.getElementById('tit2').innerHTML='");
/*  178 */       out.print(FormatUtil.getString("am.javaruntime.action.delaybwgc"));
/*  179 */       out.write("';\n\t\t\tdocument.getElementById('tit3').innerHTML='");
/*  180 */       out.print(FormatUtil.getString("am.javaruntime.action.createjgc"));
/*  181 */       out.write("';\n\t\t}\n \n }\n  \n function changeDelay()\n {\n   var tdcount=document.AMActionForm.tdcount.value;\n   if(tdcount>1){\n\t   document.AMActionForm.tddelay.disabled=false;\n   }else{\n\t   document.AMActionForm.tddelay.disabled=true;\n   }\n }\n \n</script>\n");
/*      */       
/*  183 */       org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/*  184 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  185 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/*  187 */       _jspx_th_html_005fform_005f0.setAction("/JavaRuntime");
/*  188 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  189 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/*  191 */           out.write(10);
/*  192 */           if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  194 */           out.write(10);
/*  195 */           if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  197 */           out.write(10);
/*  198 */           if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  200 */           out.write(10);
/*  201 */           if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  203 */           out.write(10);
/*  204 */           if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  206 */           out.write(10);
/*      */           
/*  208 */           com.adventnet.appmanager.struts.form.AMActionForm fm = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/*      */           
/*  210 */           out.write(10);
/*  211 */           out.write(10);
/*  212 */           if (monitorType.equalsIgnoreCase("jre")) {
/*  213 */             out.write("\n<title>");
/*  214 */             out.print(FormatUtil.getString("am.javaruntime.action.jdk.update.text"));
/*  215 */             out.write(32);
/*  216 */             out.write(58);
/*  217 */             out.write(32);
/*  218 */             out.print(fm.getDisplayname());
/*  219 */             out.write("</title>\n");
/*      */           } else {
/*  221 */             out.write("\n<title>");
/*  222 */             out.print(FormatUtil.getString("am.amazon.action.ec2Instance.update.text"));
/*  223 */             out.write(32);
/*  224 */             out.write(58);
/*  225 */             out.write(32);
/*  226 */             out.print(fm.getDisplayname());
/*  227 */             out.write("</title>\n");
/*      */           }
/*  229 */           out.write("\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"55\" class=\"darkheaderbg\">\n\t<tr>\n\t\t");
/*  230 */           if (monitorType.equalsIgnoreCase("jre")) {
/*  231 */             out.write("\n\t\t<td><span class=\"headingboldwhite\">");
/*  232 */             out.print(FormatUtil.getString("am.javaruntime.action.jdk.update.text"));
/*  233 */             out.write(32);
/*  234 */             out.write(58);
/*  235 */             out.write(32);
/*  236 */             out.print(fm.getDisplayname());
/*  237 */             out.write("</span></td>\n\t\t");
/*      */           } else {
/*  239 */             out.write("\n\t\t<td><span class=\"headingboldwhite\">");
/*  240 */             out.print(FormatUtil.getString("am.amazon.action.ec2Instance.update.text"));
/*  241 */             out.write(32);
/*  242 */             out.write(58);
/*  243 */             out.write(32);
/*  244 */             out.print(fm.getDisplayname());
/*  245 */             out.write("</span></td>\n\t\t");
/*      */           }
/*  247 */           out.write("\n\t</tr>\n</table>\n   \n ");
/*      */           
/*  249 */           MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  250 */           _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/*  251 */           _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  253 */           _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/*  254 */           int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/*  255 */           if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */             for (;;) {
/*  257 */               out.write(" \n\t   ");
/*      */               
/*  259 */               MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/*  260 */               _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/*  261 */               _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */               
/*  263 */               _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */               
/*  265 */               _jspx_th_html_005fmessages_005f0.setMessage("true");
/*  266 */               int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/*  267 */               if (_jspx_eval_html_005fmessages_005f0 != 0) {
/*  268 */                 String msg = null;
/*  269 */                 if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  270 */                   out = _jspx_page_context.pushBody();
/*  271 */                   _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/*  272 */                   _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                 }
/*  274 */                 msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                 for (;;) {
/*  276 */                   out.write("\n\t\t<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"messagebox\" style=\"margin-top:10px;margin-left:6px\">\n\t\t\t\t  <tr> \n\t\t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\"></td>\n\t\t\t\t\t<td width=\"95%\" class=\"message\"> ");
/*  277 */                   if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                     return;
/*  279 */                   out.write("</td>\n\t\t\t\t  </tr>\n\t\t\t\t</table>\n\t\t\t\t<br>\n\t\t");
/*  280 */                   int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/*  281 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*  282 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  285 */                 if (_jspx_eval_html_005fmessages_005f0 != 1) {
/*  286 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  289 */               if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/*  290 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */               }
/*      */               
/*  293 */               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*  294 */               out.write(10);
/*  295 */               out.write(9);
/*  296 */               int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/*  297 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  301 */           if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/*  302 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */           }
/*      */           
/*  305 */           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*  306 */           out.write(32);
/*  307 */           out.write("\n\t\n<div id=\"actionmessage\" style=\"display:none\"  class='error-text'>\n</div>\n\n\t");
/*  308 */           if (_jspx_meth_logic_005fmessagesPresent_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  310 */           out.write(32);
/*  311 */           out.write(10);
/*  312 */           out.write(9);
/*  313 */           if (_jspx_meth_logic_005fmessagesNotPresent_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  315 */           out.write(" \n  <tr>\n    <td width=\"40%\" class=\"bodytext\">");
/*  316 */           out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/*  317 */           out.write("</td>\n    <td width=\"60%\" class=\"bodytext\"> ");
/*  318 */           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  320 */           out.write("</td>\n  </tr>\n  \n  ");
/*  321 */           if (monitorType.equalsIgnoreCase("jre")) {
/*  322 */             out.write("\n  <tr>\n<td width=\"30%\" class=\"bodytext\">");
/*  323 */             out.print(FormatUtil.getString("am.javaruntime.action.selecttype"));
/*  324 */             out.write("</td>\t\n\t  <td class=\"bodytext\" colspan=\"3\" valign=\"middle\">\n\t  \n\t  ");
/*  325 */             if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  327 */             out.write("&nbsp;");
/*  328 */             out.print(FormatUtil.getString("am.javaruntime.action.threaddump"));
/*  329 */             out.write(" &nbsp;&nbsp;\n\t  ");
/*  330 */             if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  332 */             out.write("&nbsp;");
/*  333 */             out.print(FormatUtil.getString("am.javaruntime.action.heapdump"));
/*  334 */             out.write(" &nbsp;&nbsp;\n\t  ");
/*  335 */             if (_jspx_meth_html_005fradio_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  337 */             out.write("&nbsp;");
/*  338 */             out.print(FormatUtil.getString("am.javaruntime.action.performgc"));
/*  339 */             out.write(" </td>\n\t</tr>\n \n    <tr>\n    <td class=\"bodytext\"><span id=\"tit1\">");
/*  340 */             out.print(FormatUtil.getString("am.javaruntime.action.nooftd"));
/*  341 */             out.write("</span></td>\n    <td class=\"bodytext\">");
/*  342 */             if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  344 */             out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"bodytext\"><span id=\"tit2\">");
/*  345 */             out.print(FormatUtil.getString("am.javaruntime.action.delaybwtd"));
/*  346 */             out.write("</span></td>\n    <td class=\"bodytext\">");
/*  347 */             if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  349 */             out.write("</td>\n  </tr>\n  <tr>\t\n\t  <td class=\"bodytext\" width=\"23%\" valign=\"top\">");
/*  350 */             out.print(FormatUtil.getString("am.javaruntime.action.targetjre"));
/*  351 */             out.write("</td>\n\t  <td class=\"bodytext\" width=\"77%\" valign=\"middle\">\n\t  <table class=\"monitorinfoeven\" style=\"margin: 0px 10px 10px 0px;\" width=\"100%\" align=\"left\" cellpadding=\"5\" cellspacing=\"5\">\n\t  <tr>\n\t  <td class=\"bodytext\" width=\"35%\" valign=\"middle\">\n\t  \n\t\t \t");
/*      */             
/*  353 */             SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/*  354 */             _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/*  355 */             _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  357 */             _jspx_th_html_005fselect_005f0.setProperty("logConfig");
/*      */             
/*  359 */             _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */             
/*  361 */             _jspx_th_html_005fselect_005f0.setStyle("width:242px;vertical-align:middle;");
/*      */             
/*  363 */             _jspx_th_html_005fselect_005f0.setOnchange("javascript:getResourceForSelectionType();");
/*  364 */             int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/*  365 */             if (_jspx_eval_html_005fselect_005f0 != 0) {
/*  366 */               if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  367 */                 out = _jspx_page_context.pushBody();
/*  368 */                 _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/*  369 */                 _jspx_th_html_005fselect_005f0.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  372 */                 out.write(" \n\t\t\t");
/*      */                 
/*  374 */                 OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  375 */                 _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/*  376 */                 _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  378 */                 _jspx_th_html_005foption_005f0.setValue("1");
/*  379 */                 int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/*  380 */                 if (_jspx_eval_html_005foption_005f0 != 0) {
/*  381 */                   if (_jspx_eval_html_005foption_005f0 != 1) {
/*  382 */                     out = _jspx_page_context.pushBody();
/*  383 */                     _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/*  384 */                     _jspx_th_html_005foption_005f0.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  387 */                     out.print(FormatUtil.getString("am.javaruntime.action.type1"));
/*  388 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/*  389 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  392 */                   if (_jspx_eval_html_005foption_005f0 != 1) {
/*  393 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  396 */                 if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/*  397 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                 }
/*      */                 
/*  400 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/*  401 */                 out.write(" \n\t\t\t");
/*      */                 
/*  403 */                 OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  404 */                 _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/*  405 */                 _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  407 */                 _jspx_th_html_005foption_005f1.setValue("2");
/*  408 */                 int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/*  409 */                 if (_jspx_eval_html_005foption_005f1 != 0) {
/*  410 */                   if (_jspx_eval_html_005foption_005f1 != 1) {
/*  411 */                     out = _jspx_page_context.pushBody();
/*  412 */                     _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/*  413 */                     _jspx_th_html_005foption_005f1.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  416 */                     out.print(FormatUtil.getString("am.javaruntime.action.type2"));
/*  417 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/*  418 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  421 */                   if (_jspx_eval_html_005foption_005f1 != 1) {
/*  422 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  425 */                 if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/*  426 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                 }
/*      */                 
/*  429 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/*  430 */                 out.write(" \n\t\t\t");
/*      */                 
/*  432 */                 OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  433 */                 _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/*  434 */                 _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  436 */                 _jspx_th_html_005foption_005f2.setValue("3");
/*  437 */                 int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/*  438 */                 if (_jspx_eval_html_005foption_005f2 != 0) {
/*  439 */                   if (_jspx_eval_html_005foption_005f2 != 1) {
/*  440 */                     out = _jspx_page_context.pushBody();
/*  441 */                     _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/*  442 */                     _jspx_th_html_005foption_005f2.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  445 */                     out.print(FormatUtil.getString("am.javaruntime.action.type3"));
/*  446 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/*  447 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  450 */                   if (_jspx_eval_html_005foption_005f2 != 1) {
/*  451 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  454 */                 if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/*  455 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                 }
/*      */                 
/*  458 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/*  459 */                 out.write(" \n\t\t\t");
/*      */                 
/*  461 */                 OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  462 */                 _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/*  463 */                 _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/*  465 */                 _jspx_th_html_005foption_005f3.setValue("4");
/*  466 */                 int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/*  467 */                 if (_jspx_eval_html_005foption_005f3 != 0) {
/*  468 */                   if (_jspx_eval_html_005foption_005f3 != 1) {
/*  469 */                     out = _jspx_page_context.pushBody();
/*  470 */                     _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/*  471 */                     _jspx_th_html_005foption_005f3.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  474 */                     out.print(FormatUtil.getString("am.javaruntime.action.type4"));
/*  475 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/*  476 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  479 */                   if (_jspx_eval_html_005foption_005f3 != 1) {
/*  480 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  483 */                 if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/*  484 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                 }
/*      */                 
/*  487 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/*  488 */                 out.write(" \n\t\t\t");
/*  489 */                 int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/*  490 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  493 */               if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  494 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  497 */             if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/*  498 */               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */             }
/*      */             
/*  501 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/*  502 */             out.write("\t\t\t \n\t </td>\n\t </tr>\n\n \t");
/*      */           } else {
/*  504 */             out.write("\n\t<tr>\n\t\t<td width=\"30%\" class=\"bodytext\">");
/*  505 */             out.print(FormatUtil.getString("am.javaruntime.action.selecttype"));
/*  506 */             out.write("</td>\n\t\t  <td class=\"bodytext\" colspan=\"3\" valign=\"middle\">\n\t\n\t\t  ");
/*  507 */             if (_jspx_meth_html_005fradio_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  509 */             out.write("&nbsp;");
/*  510 */             out.print(FormatUtil.getString("am.amazon.ec2actions.startinstances"));
/*  511 */             out.write(" &nbsp;&nbsp;\n\t\t  ");
/*  512 */             if (_jspx_meth_html_005fradio_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  514 */             out.write("&nbsp;");
/*  515 */             out.print(FormatUtil.getString("am.amazon.ec2actions.stopinstances"));
/*  516 */             out.write(" &nbsp;&nbsp;\n\t\t  ");
/*  517 */             if (_jspx_meth_html_005fradio_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  519 */             out.write("&nbsp;");
/*  520 */             out.print(FormatUtil.getString("am.amazon.ec2actions.rebootinstances"));
/*  521 */             out.write(" </td>\n\t</tr>\n\t<tr>\t\n\t\t  <td class=\"bodytext\" width=\"23%\"  valign=\"top\">");
/*  522 */             out.print(FormatUtil.getString("am.ec2.form.selecttarget.text"));
/*  523 */             out.write("</td>\n\t\t  <td class=\"bodytext\" width=\"77%\" valign=\"middle\">\n\t      <table class=\"monitorinfoeven\" style=\"margin: 0px 10px 10px 0px;\" width=\"100%\" align=\"left\" cellpadding=\"5\" cellspacing=\"5\">\n\t\t  <tr>\n\t\t  <td class=\"bodytext\" width=\"35%\" valign=\"middle\">\n\t\t  \n\t\t  ");
/*      */             
/*  525 */             SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/*  526 */             _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/*  527 */             _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  529 */             _jspx_th_html_005fselect_005f1.setProperty("logConfig");
/*      */             
/*  531 */             _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/*      */             
/*  533 */             _jspx_th_html_005fselect_005f1.setStyle("width:242px;vertical-align:middle;");
/*      */             
/*  535 */             _jspx_th_html_005fselect_005f1.setOnchange("javascript:getResourceForSelectionType();");
/*  536 */             int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/*  537 */             if (_jspx_eval_html_005fselect_005f1 != 0) {
/*  538 */               if (_jspx_eval_html_005fselect_005f1 != 1) {
/*  539 */                 out = _jspx_page_context.pushBody();
/*  540 */                 _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/*  541 */                 _jspx_th_html_005fselect_005f1.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  544 */                 out.write(" \n\t\t\t\t\n\t\t  ");
/*      */                 
/*  546 */                 OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  547 */                 _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/*  548 */                 _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f1);
/*      */                 
/*  550 */                 _jspx_th_html_005foption_005f4.setValue("6");
/*  551 */                 int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/*  552 */                 if (_jspx_eval_html_005foption_005f4 != 0) {
/*  553 */                   if (_jspx_eval_html_005foption_005f4 != 1) {
/*  554 */                     out = _jspx_page_context.pushBody();
/*  555 */                     _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/*  556 */                     _jspx_th_html_005foption_005f4.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  559 */                     out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.ec2instancesinmonitorgroup.text"));
/*  560 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/*  561 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  564 */                   if (_jspx_eval_html_005foption_005f4 != 1) {
/*  565 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  568 */                 if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/*  569 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                 }
/*      */                 
/*  572 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/*  573 */                 out.write("\n\t\t  ");
/*      */                 
/*  575 */                 OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/*  576 */                 _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/*  577 */                 _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f1);
/*      */                 
/*  579 */                 _jspx_th_html_005foption_005f5.setValue("7");
/*  580 */                 int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/*  581 */                 if (_jspx_eval_html_005foption_005f5 != 0) {
/*  582 */                   if (_jspx_eval_html_005foption_005f5 != 1) {
/*  583 */                     out = _jspx_page_context.pushBody();
/*  584 */                     _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/*  585 */                     _jspx_th_html_005foption_005f5.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  588 */                     out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.specificec2instance.text"));
/*  589 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/*  590 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  593 */                   if (_jspx_eval_html_005foption_005f5 != 1) {
/*  594 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  597 */                 if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/*  598 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                 }
/*      */                 
/*  601 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/*  602 */                 out.write("\t\t\t  \t\t\t\t  \t\t\t    \n\t\t\t\t\n\t\t  ");
/*  603 */                 int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/*  604 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  607 */               if (_jspx_eval_html_005fselect_005f1 != 1) {
/*  608 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  611 */             if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/*  612 */               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */             }
/*      */             
/*  615 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/*  616 */             out.write("\t\t\t \n\t\t </td>\n\t\t </tr>\n\t\n");
/*      */           }
/*  618 */           out.write("\n \n \n<tr id=\"mg\" style=\"display:none\">\n\t\n\t\t\t<td width=\"40%\" style=\"padding-top:1px;padding-bottom:2px;\" align=\"left\">");
/*  619 */           out.print(FormatUtil.getString("am.reporttab.selectmg.text"));
/*  620 */           out.write(":&nbsp;\n\t\t\t<select name=\"selectedMG\"  class=\"formtext\" style=\"width:25%\">\n\t\t\t\t ");
/*      */           
/*  622 */           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/*  623 */           _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/*  624 */           _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  626 */           _jspx_th_logic_005fnotEmpty_005f0.setName("applications");
/*  627 */           int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/*  628 */           if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */             for (;;) {
/*  630 */               out.write("\n\t\t\t\t     ");
/*      */               
/*  632 */               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  633 */               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/*  634 */               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */               
/*  636 */               _jspx_th_logic_005fiterate_005f0.setName("applications");
/*      */               
/*  638 */               _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */               
/*  640 */               _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */               
/*  642 */               _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*  643 */               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/*  644 */               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/*  645 */                 java.util.ArrayList row = null;
/*  646 */                 Integer j = null;
/*  647 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  648 */                   out = _jspx_page_context.pushBody();
/*  649 */                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/*  650 */                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                 }
/*  652 */                 row = (java.util.ArrayList)_jspx_page_context.findAttribute("row");
/*  653 */                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                 for (;;) {
/*  655 */                   out.write("\n\t\t\t\t              ");
/*      */                   
/*  657 */                   String selected = "";
/*  658 */                   String currentmg = (String)row.get(1);
/*  659 */                   String selectmg = fm.getSelectedMG();
/*  660 */                   if ((selectmg != null) && (selectmg.equals(currentmg)))
/*      */                   {
/*  662 */                     selected = "selected=\"selected\"";
/*      */                   }
/*      */                   
/*  665 */                   out.write("\n\t\t\t\t \t      <option value=\"");
/*  666 */                   out.print((String)row.get(1));
/*  667 */                   out.write(34);
/*  668 */                   out.write(32);
/*  669 */                   out.print(selected);
/*  670 */                   out.write(62);
/*  671 */                   out.print(row.get(0));
/*  672 */                   out.write("</option>\n\t\t\t\t     ");
/*  673 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/*  674 */                   row = (java.util.ArrayList)_jspx_page_context.findAttribute("row");
/*  675 */                   j = (Integer)_jspx_page_context.findAttribute("j");
/*  676 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  679 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  680 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  683 */               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/*  684 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */               }
/*      */               
/*  687 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*  688 */               out.write("\n\t\t\t\t ");
/*  689 */               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/*  690 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  694 */           if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/*  695 */             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */           }
/*      */           
/*  698 */           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*  699 */           out.write("\n      \t     </select>\n\t\t\t   </td>\n\t\t\t \n\t</tr>\n \t\n \t");
/*  700 */           if (monitorType.equals("jre")) {
/*  701 */             out.write("\n\t<tr id=\"host\" style=\"display:none\">\n\t\n\t\t\t<td width=\"40%\" style=\"padding-top:1px;padding-bottom:2px;\" align=\"left\">");
/*  702 */             out.print(FormatUtil.getString("am.javaruntime.action.selecthost"));
/*  703 */             out.write(":&nbsp;\n\t\t\t   ");
/*  704 */             if (_jspx_meth_logic_005fnotEmpty_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  706 */             out.write("\t\t\n\n\t\t\t\t \n\t\t\t\t");
/*      */             
/*  708 */             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/*  709 */             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/*  710 */             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  712 */             _jspx_th_logic_005fempty_005f0.setName("hostlist");
/*  713 */             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/*  714 */             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */               for (;;) {
/*  716 */                 out.write("\n\t\t\t\t\t <input type=\"hidden\" name=\"selectedhost\" value=\"\">\n\t\t\t\t\t");
/*  717 */                 out.print(FormatUtil.getString("am.javaruntime.action.nohost"));
/*  718 */                 out.write("\n\t\t\t\t");
/*  719 */                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/*  720 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  724 */             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/*  725 */               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */             }
/*      */             
/*  728 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/*  729 */             out.write("\n\t\t\t   </td>\n\t\t\t   \n\t</tr>\t\n \t\n\t<tr id=\"jre\" style=\"display:none\">\n\t\n\t\t\t<td width=\"40%\" style=\"padding-top:1px;padding-bottom:2px;\" align=\"left\">");
/*  730 */             out.print(FormatUtil.getString("am.javaruntime.action.selectjre"));
/*  731 */             out.write(":&nbsp;\n\t\t\t\t");
/*  732 */             if (_jspx_meth_logic_005fnotEmpty_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  734 */             out.write("\n\t\t\t\t\n\t\t\t\t");
/*      */             
/*  736 */             EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/*  737 */             _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/*  738 */             _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  740 */             _jspx_th_logic_005fempty_005f1.setName("jrelist");
/*  741 */             int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/*  742 */             if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */               for (;;) {
/*  744 */                 out.write("\n\t\t\t\t<input type=\"hidden\" name=\"selectedjre\" value=\"\">\n\t\t\t\t\t");
/*  745 */                 out.print(FormatUtil.getString("am.javaruntime.action.nojre"));
/*  746 */                 out.write("\n\t\t\t\t");
/*  747 */                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/*  748 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  752 */             if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/*  753 */               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */             }
/*      */             
/*  756 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/*  757 */             out.write("\n\t\t\t\t\n\t\t\t   </td>\n\t\t\t   \n\t</tr>\n\t");
/*      */           } else {
/*  759 */             out.write("\n\t<tr id=\"ec2instt\" style=\"display:none\">\n\t\n\t        <td width=\"45%\" style=\"padding-top:1px;padding-bottom:2px;\" align=\"left\">");
/*  760 */             out.print(FormatUtil.getString("am.ec2.form.selectec2inst.text"));
/*  761 */             out.write(":&nbsp;\n\t\t");
/*  762 */             if (_jspx_meth_logic_005fnotEmpty_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  764 */             out.write(10);
/*  765 */             out.write(9);
/*  766 */             out.write(9);
/*      */             
/*  768 */             EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/*  769 */             _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/*  770 */             _jspx_th_logic_005fempty_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  772 */             _jspx_th_logic_005fempty_005f2.setName("ec2Instance");
/*  773 */             int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/*  774 */             if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */               for (;;) {
/*  776 */                 out.write("\n\t\t<input type=\"hidden\" name=\"selectedjre\" value=\"\">\n\t\t");
/*  777 */                 out.print(FormatUtil.getString("am.ec2.form.noec2instance.text"));
/*  778 */                 out.write(10);
/*  779 */                 out.write(9);
/*  780 */                 out.write(9);
/*  781 */                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/*  782 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  786 */             if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/*  787 */               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2); return;
/*      */             }
/*      */             
/*  790 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/*  791 */             out.write("\n\t\t</td>\n\t</tr>\n\t");
/*      */           }
/*  793 */           out.write("\n \t</table>\n\t</td>\n\t</tr>\n \t<tr>\t\n\t  <td class=\"bodytext\" width=\"23%\">");
/*  794 */           out.print(FormatUtil.getString("am.ec2.form.notifyaction.text"));
/*  795 */           out.write("</td>\n\t  <td class=\"bodytext\" width=\"37%\" valign=\"middle\" >\n\t\t\t\t\t");
/*  796 */           if (_jspx_meth_html_005fselect_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  798 */           out.write(" \n\t\n        \n\t \t</td>\n\t \n \n\t</tr>\n\t\n</table>\n \n\t <table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\" style=\"margin-top:0px;margin-left:6px\">\n\t  <tr> \n\t    <td width=\"48%\" class=\"tablebottom\" align=\"right\"><input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/*  799 */           out.print(FormatUtil.getString("am.webclient.common.update.text"));
/*  800 */           out.write("\" onClick=\"javascript:validateAndSubmit('");
/*  801 */           out.print(monitorType);
/*  802 */           out.write("');\"></td>\n\t    <td width=\"50%\" class=\"tablebottom\"><input name=\"Button\" type=\"button\" class=\"buttons\" value=\"");
/*  803 */           out.print(FormatUtil.getString("am.webclient.common.close.text"));
/*  804 */           out.write("\" onClick=\"javascript:window.parent.close();\"></td>\n\t  </tr>\n\t</table>\n   <br>\n        ");
/*  805 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  806 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  810 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  811 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else {
/*  814 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  815 */         out.write("\n <script>\t \n getResourceForSelectionType()\t \n </script>   \n");
/*      */       }
/*  817 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  818 */         out = _jspx_out;
/*  819 */         if ((out != null) && (out.getBufferSize() != 0))
/*  820 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  821 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  824 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  830 */     PageContext pageContext = _jspx_page_context;
/*  831 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  833 */     org.apache.taglibs.standard.tag.el.core.OutTag _jspx_th_c_005fout_005f0 = (org.apache.taglibs.standard.tag.el.core.OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(org.apache.taglibs.standard.tag.el.core.OutTag.class);
/*  834 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  835 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  837 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  839 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  840 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  841 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  842 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  843 */       return true;
/*      */     }
/*  845 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  846 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  851 */     PageContext pageContext = _jspx_page_context;
/*  852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  854 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/*  855 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/*  856 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  858 */     _jspx_th_am_005fhiddenparam_005f0.setName("haid");
/*  859 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/*  860 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/*  861 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/*  862 */       return true;
/*      */     }
/*  864 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/*  865 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  870 */     PageContext pageContext = _jspx_page_context;
/*  871 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  873 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/*  874 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/*  875 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  877 */     _jspx_th_am_005fhiddenparam_005f1.setName("redirectTo");
/*  878 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/*  879 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/*  880 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/*  881 */       return true;
/*      */     }
/*  883 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/*  884 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  889 */     PageContext pageContext = _jspx_page_context;
/*  890 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  892 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/*  893 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/*  894 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  896 */     _jspx_th_html_005fhidden_005f0.setProperty("method");
/*      */     
/*  898 */     _jspx_th_html_005fhidden_005f0.setValue("editThreadDumpAction");
/*  899 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/*  900 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/*  901 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/*  902 */       return true;
/*      */     }
/*  904 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/*  905 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  910 */     PageContext pageContext = _jspx_page_context;
/*  911 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  913 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/*  914 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/*  915 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  917 */     _jspx_th_html_005fhidden_005f1.setProperty("id");
/*  918 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/*  919 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/*  920 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/*  921 */       return true;
/*      */     }
/*  923 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/*  924 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  929 */     PageContext pageContext = _jspx_page_context;
/*  930 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  932 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/*  933 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/*  934 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  936 */     _jspx_th_html_005fhidden_005f2.setProperty("update");
/*      */     
/*  938 */     _jspx_th_html_005fhidden_005f2.setValue("true");
/*  939 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/*  940 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/*  941 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/*  942 */       return true;
/*      */     }
/*  944 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/*  945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  950 */     PageContext pageContext = _jspx_page_context;
/*  951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  953 */     org.apache.struts.taglib.bean.WriteTag _jspx_th_bean_005fwrite_005f0 = (org.apache.struts.taglib.bean.WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(org.apache.struts.taglib.bean.WriteTag.class);
/*  954 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/*  955 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/*  957 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*  958 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/*  959 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/*  960 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/*  961 */       return true;
/*      */     }
/*  963 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/*  964 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fmessagesPresent_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  969 */     PageContext pageContext = _jspx_page_context;
/*  970 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  972 */     MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/*  973 */     _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/*  974 */     _jspx_th_logic_005fmessagesPresent_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  976 */     _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/*  977 */     int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/*  978 */     if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*      */       for (;;) {
/*  980 */         out.write(" \n\t\t<table width=\"98%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtborder\" style=\"margin-top:0px;margin-left:6px\">\t\n\t");
/*  981 */         int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/*  982 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  986 */     if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/*  987 */       this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/*  988 */       return true;
/*      */     }
/*  990 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/*  991 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fmessagesNotPresent_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  996 */     PageContext pageContext = _jspx_page_context;
/*  997 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  999 */     org.apache.struts.taglib.logic.MessagesNotPresentTag _jspx_th_logic_005fmessagesNotPresent_005f0 = (org.apache.struts.taglib.logic.MessagesNotPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fmessage.get(org.apache.struts.taglib.logic.MessagesNotPresentTag.class);
/* 1000 */     _jspx_th_logic_005fmessagesNotPresent_005f0.setPageContext(_jspx_page_context);
/* 1001 */     _jspx_th_logic_005fmessagesNotPresent_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1003 */     _jspx_th_logic_005fmessagesNotPresent_005f0.setMessage("true");
/* 1004 */     int _jspx_eval_logic_005fmessagesNotPresent_005f0 = _jspx_th_logic_005fmessagesNotPresent_005f0.doStartTag();
/* 1005 */     if (_jspx_eval_logic_005fmessagesNotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 1007 */         out.write(" \t\n\t\t<table width=\"98%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtborder\" style=\"margin-top:10px;margin-left:6px\">\n\t");
/* 1008 */         int evalDoAfterBody = _jspx_th_logic_005fmessagesNotPresent_005f0.doAfterBody();
/* 1009 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1013 */     if (_jspx_th_logic_005fmessagesNotPresent_005f0.doEndTag() == 5) {
/* 1014 */       this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesNotPresent_005f0);
/* 1015 */       return true;
/*      */     }
/* 1017 */     this._005fjspx_005ftagPool_005flogic_005fmessagesNotPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesNotPresent_005f0);
/* 1018 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1023 */     PageContext pageContext = _jspx_page_context;
/* 1024 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1026 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 1027 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 1028 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1030 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 1032 */     _jspx_th_html_005ftext_005f0.setSize("40");
/*      */     
/* 1034 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */     
/* 1036 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/* 1037 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 1038 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 1039 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 1040 */       return true;
/*      */     }
/* 1042 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 1043 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1048 */     PageContext pageContext = _jspx_page_context;
/* 1049 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1051 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 1052 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 1053 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1055 */     _jspx_th_html_005fradio_005f0.setProperty("jtaskMethod");
/*      */     
/* 1057 */     _jspx_th_html_005fradio_005f0.setValue("ThreadDump");
/*      */     
/* 1059 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:changetitle();");
/* 1060 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 1061 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 1062 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 1063 */       return true;
/*      */     }
/* 1065 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 1066 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1071 */     PageContext pageContext = _jspx_page_context;
/* 1072 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1074 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 1075 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 1076 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1078 */     _jspx_th_html_005fradio_005f1.setProperty("jtaskMethod");
/*      */     
/* 1080 */     _jspx_th_html_005fradio_005f1.setValue("HeapDump");
/*      */     
/* 1082 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:changetitle();");
/* 1083 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 1084 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 1085 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 1086 */       return true;
/*      */     }
/* 1088 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 1089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1094 */     PageContext pageContext = _jspx_page_context;
/* 1095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1097 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 1098 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 1099 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1101 */     _jspx_th_html_005fradio_005f2.setProperty("jtaskMethod");
/*      */     
/* 1103 */     _jspx_th_html_005fradio_005f2.setValue("PerformGC");
/*      */     
/* 1105 */     _jspx_th_html_005fradio_005f2.setOnclick("javascript:changetitle();");
/* 1106 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 1107 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 1108 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 1109 */       return true;
/*      */     }
/* 1111 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 1112 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1117 */     PageContext pageContext = _jspx_page_context;
/* 1118 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1120 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 1121 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 1122 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1124 */     _jspx_th_html_005ftext_005f1.setProperty("tdcount");
/*      */     
/* 1126 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 1128 */     _jspx_th_html_005ftext_005f1.setSize("5");
/*      */     
/* 1130 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:changeDelay();");
/* 1131 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 1132 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 1133 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1134 */       return true;
/*      */     }
/* 1136 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1137 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1142 */     PageContext pageContext = _jspx_page_context;
/* 1143 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1145 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 1146 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 1147 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1149 */     _jspx_th_html_005ftext_005f2.setProperty("tddelay");
/*      */     
/* 1151 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 1153 */     _jspx_th_html_005ftext_005f2.setSize("5");
/* 1154 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 1155 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 1156 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 1157 */       return true;
/*      */     }
/* 1159 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 1160 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1165 */     PageContext pageContext = _jspx_page_context;
/* 1166 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1168 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 1169 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 1170 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1172 */     _jspx_th_html_005fradio_005f3.setProperty("jtaskMethod");
/*      */     
/* 1174 */     _jspx_th_html_005fradio_005f3.setValue("Start");
/* 1175 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 1176 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 1177 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 1178 */       return true;
/*      */     }
/* 1180 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 1181 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1186 */     PageContext pageContext = _jspx_page_context;
/* 1187 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1189 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 1190 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 1191 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1193 */     _jspx_th_html_005fradio_005f4.setProperty("jtaskMethod");
/*      */     
/* 1195 */     _jspx_th_html_005fradio_005f4.setValue("Stop");
/* 1196 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 1197 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 1198 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 1199 */       return true;
/*      */     }
/* 1201 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 1202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1207 */     PageContext pageContext = _jspx_page_context;
/* 1208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1210 */     RadioTag _jspx_th_html_005fradio_005f5 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 1211 */     _jspx_th_html_005fradio_005f5.setPageContext(_jspx_page_context);
/* 1212 */     _jspx_th_html_005fradio_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1214 */     _jspx_th_html_005fradio_005f5.setProperty("jtaskMethod");
/*      */     
/* 1216 */     _jspx_th_html_005fradio_005f5.setValue("Restart");
/* 1217 */     int _jspx_eval_html_005fradio_005f5 = _jspx_th_html_005fradio_005f5.doStartTag();
/* 1218 */     if (_jspx_th_html_005fradio_005f5.doEndTag() == 5) {
/* 1219 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 1220 */       return true;
/*      */     }
/* 1222 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 1223 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1228 */     PageContext pageContext = _jspx_page_context;
/* 1229 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1231 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 1232 */     _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 1233 */     _jspx_th_logic_005fnotEmpty_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1235 */     _jspx_th_logic_005fnotEmpty_005f1.setName("hostlist");
/* 1236 */     int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 1237 */     if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */       for (;;) {
/* 1239 */         out.write("\n\t\t\t\t\t\t");
/* 1240 */         if (_jspx_meth_html_005fselect_005f2(_jspx_th_logic_005fnotEmpty_005f1, _jspx_page_context))
/* 1241 */           return true;
/* 1242 */         out.write("\n\t\t\t\t ");
/* 1243 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 1244 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1248 */     if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 1249 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 1250 */       return true;
/*      */     }
/* 1252 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 1253 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_logic_005fnotEmpty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1258 */     PageContext pageContext = _jspx_page_context;
/* 1259 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1261 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1262 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 1263 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f1);
/*      */     
/* 1265 */     _jspx_th_html_005fselect_005f2.setProperty("selectedhost");
/*      */     
/* 1267 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext");
/*      */     
/* 1269 */     _jspx_th_html_005fselect_005f2.setStyle("width:25%");
/* 1270 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 1271 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 1272 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 1273 */         out = _jspx_page_context.pushBody();
/* 1274 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 1275 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1278 */         out.write("\n\t\t\t\t\t\t");
/* 1279 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 1280 */           return true;
/* 1281 */         out.write("        \n\t\t\t\t\t    ");
/* 1282 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 1283 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1286 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 1287 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1290 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 1291 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 1292 */       return true;
/*      */     }
/* 1294 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 1295 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1300 */     PageContext pageContext = _jspx_page_context;
/* 1301 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1303 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1304 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 1305 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 1307 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("hostlist");
/* 1308 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 1309 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 1310 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 1311 */       return true;
/*      */     }
/* 1313 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 1314 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1319 */     PageContext pageContext = _jspx_page_context;
/* 1320 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1322 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 1323 */     _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 1324 */     _jspx_th_logic_005fnotEmpty_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1326 */     _jspx_th_logic_005fnotEmpty_005f2.setName("jrelist");
/* 1327 */     int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 1328 */     if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */       for (;;) {
/* 1330 */         out.write("\n\t\t\t\t\t");
/* 1331 */         if (_jspx_meth_html_005fselect_005f3(_jspx_th_logic_005fnotEmpty_005f2, _jspx_page_context))
/* 1332 */           return true;
/* 1333 */         out.write(" \n\t\t\t    ");
/* 1334 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 1335 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1339 */     if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 1340 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 1341 */       return true;
/*      */     }
/* 1343 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 1344 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_logic_005fnotEmpty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1349 */     PageContext pageContext = _jspx_page_context;
/* 1350 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1352 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1353 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 1354 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f2);
/*      */     
/* 1356 */     _jspx_th_html_005fselect_005f3.setProperty("selectedjre");
/*      */     
/* 1358 */     _jspx_th_html_005fselect_005f3.setStyleClass("formtext");
/*      */     
/* 1360 */     _jspx_th_html_005fselect_005f3.setStyle("width:25%");
/* 1361 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 1362 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 1363 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 1364 */         out = _jspx_page_context.pushBody();
/* 1365 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 1366 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1369 */         out.write("\n\t\t\t\t\t");
/* 1370 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 1371 */           return true;
/* 1372 */         out.write("        \n\t\t\t\t\t");
/* 1373 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 1374 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1377 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 1378 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1381 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 1382 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 1383 */       return true;
/*      */     }
/* 1385 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 1386 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1391 */     PageContext pageContext = _jspx_page_context;
/* 1392 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1394 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1395 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 1396 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 1398 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("jrelist");
/* 1399 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 1400 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 1401 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 1402 */       return true;
/*      */     }
/* 1404 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 1405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1410 */     PageContext pageContext = _jspx_page_context;
/* 1411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1413 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 1414 */     _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 1415 */     _jspx_th_logic_005fnotEmpty_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1417 */     _jspx_th_logic_005fnotEmpty_005f3.setName("ec2Instance");
/* 1418 */     int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 1419 */     if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */       for (;;) {
/* 1421 */         out.write(10);
/* 1422 */         out.write(9);
/* 1423 */         out.write(9);
/* 1424 */         if (_jspx_meth_html_005fselect_005f4(_jspx_th_logic_005fnotEmpty_005f3, _jspx_page_context))
/* 1425 */           return true;
/* 1426 */         out.write("\n\t    \t");
/* 1427 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 1428 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1432 */     if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 1433 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 1434 */       return true;
/*      */     }
/* 1436 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 1437 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_logic_005fnotEmpty_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1442 */     PageContext pageContext = _jspx_page_context;
/* 1443 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1445 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1446 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 1447 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f3);
/*      */     
/* 1449 */     _jspx_th_html_005fselect_005f4.setProperty("selectedjre");
/*      */     
/* 1451 */     _jspx_th_html_005fselect_005f4.setStyleClass("formtext");
/*      */     
/* 1453 */     _jspx_th_html_005fselect_005f4.setStyle("width:25%");
/* 1454 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 1455 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 1456 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 1457 */         out = _jspx_page_context.pushBody();
/* 1458 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 1459 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1462 */         out.write(10);
/* 1463 */         out.write(9);
/* 1464 */         out.write(9);
/* 1465 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 1466 */           return true;
/* 1467 */         out.write(10);
/* 1468 */         out.write(9);
/* 1469 */         out.write(9);
/* 1470 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 1471 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1474 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 1475 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1478 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 1479 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 1480 */       return true;
/*      */     }
/* 1482 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 1483 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1488 */     PageContext pageContext = _jspx_page_context;
/* 1489 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1491 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1492 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 1493 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 1495 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("ec2Instance");
/* 1496 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 1497 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 1498 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 1499 */       return true;
/*      */     }
/* 1501 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 1502 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1507 */     PageContext pageContext = _jspx_page_context;
/* 1508 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1510 */     SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1511 */     _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 1512 */     _jspx_th_html_005fselect_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1514 */     _jspx_th_html_005fselect_005f5.setProperty("sendmail");
/*      */     
/* 1516 */     _jspx_th_html_005fselect_005f5.setStyleClass("formtext");
/*      */     
/* 1518 */     _jspx_th_html_005fselect_005f5.setStyle("width:242px;");
/* 1519 */     int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 1520 */     if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 1521 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 1522 */         out = _jspx_page_context.pushBody();
/* 1523 */         _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 1524 */         _jspx_th_html_005fselect_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1527 */         out.write("\n\t\t\t\t\t\t");
/* 1528 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/* 1529 */           return true;
/* 1530 */         out.write("        \n\t\t\t\t\t");
/* 1531 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 1532 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1535 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 1536 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1539 */     if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 1540 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 1541 */       return true;
/*      */     }
/* 1543 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 1544 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1549 */     PageContext pageContext = _jspx_page_context;
/* 1550 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1552 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1553 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 1554 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 1556 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("maillist");
/* 1557 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 1558 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 1559 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 1560 */       return true;
/*      */     }
/* 1562 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 1563 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fTnreadDumpActions_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */