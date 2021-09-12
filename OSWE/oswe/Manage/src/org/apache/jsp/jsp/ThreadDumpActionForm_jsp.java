/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
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
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ThreadDumpActionForm_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   33 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   39 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   40 */   static { _jspx_dependants.put("/jsp/includes/NewActions.jspf", Long.valueOf(1473429417000L));
/*   41 */     _jspx_dependants.put("/jsp/includes/CreateApplicationWizard.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonchange_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   83 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   87 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   94 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   95 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   96 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   97 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   98 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   99 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  100 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  101 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  102 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  103 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  104 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  105 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  106 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  107 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  108 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  109 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  110 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  111 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  112 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  113 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  114 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  115 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  116 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  117 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  118 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  119 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  120 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  121 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  122 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  126 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  127 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  128 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  129 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  130 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  131 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  132 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  133 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  134 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/*  135 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/*  136 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*  137 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/*  138 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  139 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  140 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  141 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  142 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/*  143 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*  144 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
/*  145 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*  146 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*  147 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonchange_005fnobody.release();
/*  148 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*  149 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/*  150 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  151 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/*  152 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  153 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  154 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/*  155 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  156 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*  157 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.release();
/*  158 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.release();
/*  159 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  166 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  169 */     JspWriter out = null;
/*  170 */     Object page = this;
/*  171 */     JspWriter _jspx_out = null;
/*  172 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  176 */       response.setContentType("text/html;charset=UTF-8");
/*  177 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  179 */       _jspx_page_context = pageContext;
/*  180 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  181 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  182 */       session = pageContext.getSession();
/*  183 */       out = pageContext.getOut();
/*  184 */       _jspx_out = out;
/*      */       
/*  186 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/*  187 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  189 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<script language=\"JavaScript1.2\" src=\"/template/appmanager.js\"></script>\n\n\n");
/*      */       
/*  191 */       org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/*  192 */       token.saveToken(request);
/*      */       
/*  194 */       out.write(10);
/*      */       
/*  196 */       boolean isInvokedFromPopup = request.getParameter("popup") != null;
/*  197 */       String mode = request.getParameter("mode");
/*  198 */       String monitorType = request.getParameter("monitorType");
/*  199 */       String wiz = request.getParameter("wiz");
/*  200 */       int actionID = -1;
/*  201 */       if (request.getParameter("actionID") != null)
/*      */       {
/*  203 */         actionID = Integer.parseInt(request.getParameter("actionID"));
/*      */       }
/*      */       
/*  206 */       out.write("\n\n\n<script>\n\n  function callAction()\n  {\n\t showDiv(\"takeaction\");\n  }\n  function removeAction()\n  {\n     hideDiv(\"takeaction\");\n  }\n\n    function getAction()\n  {\n    if(document.AMActionForm.displayname.value=='')\n\t{\n     alert(\"");
/*  207 */       out.print(FormatUtil.getString("am.webclient.common.validatename.text"));
/*  208 */       out.write("\");\n     document.AMActionForm.displayname.focus();\n     return false;\n    }\n\n   if(document.AMActionForm.priority.value=='')\n   {\n     alert(\"");
/*  209 */       out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforscheduleemail.text"));
/*  210 */       out.write("\");\n     document.AMActionForm.priority.focus();\n     return false;\n   }\n   else\n   {\n    var a=document.AMActionForm.priority.value;\n    var b=encodeURIComponent(document.AMActionForm.displayname.value);\n\tvar msg=\"Java\"; //NO I18N\n\t\t  \t\tif(document.AMActionForm.jtaskMethod[0].checked==true){\n               msg='");
/*  211 */       out.print(FormatUtil.getString("am.javaruntime.javaaction.threaddump"));
/*  212 */       out.write("';\n\t\t}else if(document.AMActionForm.jtaskMethod[1].checked==true){\n\t\t\t\t msg='");
/*  213 */       out.print(FormatUtil.getString("am.javaruntime.javaaction.heapdump"));
/*  214 */       out.write("';\n\t\t}else if(document.AMActionForm.jtaskMethod[2].checked==true){\n\t\t\t     msg='");
/*  215 */       out.print(FormatUtil.getString("am.javaruntime.javaaction.performgc"));
/*  216 */       out.write("';\n\t\t}\n    url=\"/JavaRuntime.do?method=sendActionDetails&emailid=\"+a+\"&emailname=\"+b+\"&message=\"+msg; //NO I18N\n\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getActionTypes;\n    http.send(null);\n   }\n\n }\n\n\n  function getActionTypes()\n  {\n    if(http.readyState == 4)\n    {\n      var result = http.responseText;\n      hideDiv(\"takeaction\");\n      var id=result;\n      var stringtokens=id.split(\",\");\n      smessage=stringtokens[0];\n      sid=stringtokens[1];\n      hideDiv(\"actionmessage\");\n     if(smessage !='null' || smessage != '')\n     {\n             hideDiv(\"actionmessage\");\n            document.AMActionForm.sendmail.options[document.AMActionForm.sendmail.length] =new Option(smessage,sid,false,true);\n     }\n     else\n     {\n            showDiv(\"actionmessage\");\n            document.getElementById(\"actionmessage\").innerHTML=smessage;\n     }\n    }\n }\n\n//select Target JVM Control\nfunction getResourceForSelectionType()\n{\n var selecttionType=document.getElementsByName('logConfig')[0].value;  //NO I18N\n");
/*  217 */       out.write("\tif(selecttionType=='1')\n\t{\n\t //document.getElementById('mg').style.display='none';\n\t //document.getElementById('jre').style.display='none';\n\t //document.getElementById('host').style.display='none';\n\t hideRow('mg'); // NO I18N\n\t hideRow('jre');// NO I18N\n\t hideRow('host');\t // NO I18N\n\t}\n\telse if(selecttionType=='2')\n\t{\n\t //document.getElementById('jre').style.display='none';\n\t //document.getElementById('host').style.display='none';\n\t //document.getElementById('mg').style.display='table-row';\n\t showRow('mg');// NO I18N\n\t hideRow('jre');// NO I18N\n\t hideRow('host');\t\t // NO I18N\n\t}\n\telse if(selecttionType=='3')\n\t{\n\t //document.getElementById('mg').style.display='none';\n\t //document.getElementById('jre').style.display='none';\n\t //document.getElementById('host').style.display='table-row';\n\t showRow('host');// NO I18N\n\t hideRow('jre');// NO I18N\n\t hideRow('mg');\t // NO I18N\n\t}\n\telse if(selecttionType=='4')\n\t{\n\t //document.getElementById('mg').style.display='none';\n\t //document.getElementById('host').style.display='none';\n");
/*  218 */       out.write("\t //document.getElementById('jre').style.display='table-row'\n\t showRow('jre');// NO I18N\n\t hideRow('mg');// NO I18N\n\t hideRow('host');\t \t // NO I18N\t\t \n\t}\n\telse if(selecttionType=='6')\n\t{\n\t //document.getElementById('mg').style.display='table-row';\n\t //document.getElementById('ec2instt').style.display='none';\n\t showRow('mg');// NO I18N\n\t hideRow('ec2instt');// NO I18N\n\t}\n\telse if(selecttionType=='7')\n\t{\n\t //document.getElementById('mg').style.display='none';\n\t //document.getElementById('ec2instt').style.display='table-row';\n\t showRow('ec2instt');\t// NO I18N\t \n\t hideRow('mg');\t // NO I18N\n\t}\n\n }\n\n function validateAndSubmit(monitorName)\n {\n\t\t");
/*  219 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/*  221 */       out.write("\n\tif((document.AMActionForm.displayname.value).trim()=='')\n\t{\n\talert(\"");
/*  222 */       out.print(FormatUtil.getString("am.webclient.newaction.alertdisplayname"));
/*  223 */       out.write("\");\n\tdocument.AMActionForm.displayname.focus();\n\t return false;\n\t}\n\tif(isBlackListedCharactersPresent(document.AMActionForm.displayname.value,'");
/*  224 */       out.print(FormatUtil.getString("am.webclient.specialchar.alert.displayname"));
/*  225 */       out.write("')) {\n\t\treturn false;\n    \t}\n    \t\n\tif(monitorName==\"jre\"){\n\tif((document.AMActionForm.tdcount.value).trim()=='')\n\t{\n\t  \t\tif(document.AMActionForm.jtaskMethod[0].checked==true){\n                alert(\"");
/*  226 */       out.print(FormatUtil.getString("am.javaruntime.action.noofthreaddump"));
/*  227 */       out.write("\");\n\t\t}else if(document.AMActionForm.jtaskMethod[1].checked==true){\n\t\t\t\talert(\"");
/*  228 */       out.print(FormatUtil.getString("am.javaruntime.action.noofheapdump"));
/*  229 */       out.write("\");\n\t\t}else if(document.AMActionForm.jtaskMethod[2].checked==true){\n\t\t\t    alert(\"");
/*  230 */       out.print(FormatUtil.getString("am.javaruntime.action.noofgcs"));
/*  231 */       out.write("\");\n\t\t}\n\n\tdocument.AMActionForm.tdCount.focus();\n\t return false;\n\t}\n\n\tif((document.AMActionForm.tddelay.value).trim()=='')\n\t{\n\t\t  \t\tif(document.AMActionForm.jtaskMethod[0].checked==true){\n               alert(\"");
/*  232 */       out.print(FormatUtil.getString("am.javaruntime.action.threaddumpdelay"));
/*  233 */       out.write("\");\n\t\t}else if(document.AMActionForm.jtaskMethod[1].checked==true){\n\t\t\t\talert(\"");
/*  234 */       out.print(FormatUtil.getString("am.javaruntime.action.heapdumpdelay"));
/*  235 */       out.write("\");\n\t\t}else if(document.AMActionForm.jtaskMethod[2].checked==true){\n\t\t\t    alert(\"");
/*  236 */       out.print(FormatUtil.getString("am.javaruntime.action.performgcdelay"));
/*  237 */       out.write("\");\n\t\t}\n\tdocument.AMActionForm.tdDelay.focus();\n\t return false;\n\t}\n\t}\n\n\tif(document.AMActionForm.sendmail.value=='')\n\t{\n\talert(\"");
/*  238 */       out.print(FormatUtil.getString("am.javaruntime.action.createmail"));
/*  239 */       out.write("\");\n\t return false;\n\t}\n\n\tif(monitorName==\"jre\"){\n\tif(document.AMActionForm.logConfig.value=='3' && document.AMActionForm.selectedhost.value=='')\n\t{\n\t alert(\"");
/*  240 */       out.print(FormatUtil.getString("am.javaruntime.action.selecthost"));
/*  241 */       out.write("\");\n\t return false;\n\t}\n\n\tif(document.AMActionForm.logConfig.value=='4'  && document.AMActionForm.selectedjre.value=='')\n\t{\n\t alert(\"");
/*  242 */       out.print(FormatUtil.getString("am.javaruntime.action.selectjre"));
/*  243 */       out.write("\");\n\t return false;\n\t}\n\t}else{\n\t   if(document.AMActionForm.logConfig.value=='7'  && document.AMActionForm.selectedjre.value=='')\n\t\t{\n\t\t alert(\"");
/*  244 */       out.print(FormatUtil.getString("am.amazon.ec2instanceaction.alertmessage.noec2instance.text"));
/*  245 */       out.write("\");\n\t\t return false;\n\t\t}\n\t}\n\n\t document.AMActionForm.submit();\n\n }\n\n //JRE - to chahge title based of action type selection\n function changetitle()\n  {\n\n\t\tif(document.AMActionForm.jtaskMethod[0].checked==true){\n            document.getElementById('tit1').innerHTML='");
/*  246 */       out.print(FormatUtil.getString("am.javaruntime.action.nooftd"));
/*  247 */       out.write("';\n\t\t\tdocument.getElementById('tit2').innerHTML='");
/*  248 */       out.print(FormatUtil.getString("am.javaruntime.action.delaybwtd"));
/*  249 */       out.write("';\n\t\t\tdocument.getElementById('tit3').innerHTML='");
/*  250 */       out.print(FormatUtil.getString("am.javaruntime.action.createjtd"));
/*  251 */       out.write("';\n\t\t\t ");
/*  252 */       if (actionID == -1) {
/*  253 */         out.write("\n\t\t\t document.AMActionForm.tdcount.value='2';\n\t\t\t ");
/*      */       }
/*  255 */       out.write("\n\t\t\tchangeDelay();\n\t\t}else if(document.AMActionForm.jtaskMethod[1].checked==true){\n\t\t    document.getElementById('tit1').innerHTML='");
/*  256 */       out.print(FormatUtil.getString("am.javaruntime.action.noofhd"));
/*  257 */       out.write("';\n\t\t\tdocument.getElementById('tit2').innerHTML='");
/*  258 */       out.print(FormatUtil.getString("am.javaruntime.action.delaybwhd"));
/*  259 */       out.write("';\n\t\t\tdocument.getElementById('tit3').innerHTML='");
/*  260 */       out.print(FormatUtil.getString("am.javaruntime.action.createjhd"));
/*  261 */       out.write("';\n\t\t\t ");
/*  262 */       if (actionID == -1) {
/*  263 */         out.write("\n\t\t\t document.AMActionForm.tdcount.value='1';\n\t\t\t ");
/*      */       }
/*  265 */       out.write("\n\t\t\tchangeDelay();\n\t\t}else if(document.AMActionForm.jtaskMethod[2].checked==true){\n\t\t\tdocument.getElementById('tit1').innerHTML='");
/*  266 */       out.print(FormatUtil.getString("am.javaruntime.action.noofgc"));
/*  267 */       out.write("';\n\t\t\tdocument.getElementById('tit2').innerHTML='");
/*  268 */       out.print(FormatUtil.getString("am.javaruntime.action.delaybwgc"));
/*  269 */       out.write("';\n\t\t\tdocument.getElementById('tit3').innerHTML='");
/*  270 */       out.print(FormatUtil.getString("am.javaruntime.action.createjgc"));
/*  271 */       out.write("';\n\t\t\t ");
/*  272 */       if (actionID == -1) {
/*  273 */         out.write("\n\t\t\t document.AMActionForm.tdcount.value='1';\n\t\t\t ");
/*      */       }
/*  275 */       out.write("\n\t\t\tchangeDelay()\n\t\t}\n }\n\n function changeDelay()\n {\n   var tdcount=document.AMActionForm.tdcount.value;\n   if(tdcount>1){\n\t   document.AMActionForm.tddelay.disabled=false;\n   }else{\n\t   document.AMActionForm.tddelay.disabled=true;\n   }\n }\n</script>\n\n\n\n");
/*      */       
/*  277 */       org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/*  278 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  279 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/*  281 */       _jspx_th_html_005fform_005f0.setAction("/JavaRuntime");
/*  282 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  283 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/*  285 */           out.write(10);
/*  286 */           out.write("<!--$Id$-->\n              \n\n\n\n\n<script>                      \nfunction fnFormSubmit1(object)\n{\n\tlocation.href=object;\n}\n\nfunction showMailServerSettings()\n {\n         var showMail = false;\n         var query = window.location.search;\n         var pairs = query.split(\"&\");\n         \n         for (var i=0;i<pairs.length;i++)\n         {\n                 var pos = pairs[i].indexOf('=');\n                 if (pos >= 0)\n                 {\n                         var argname = pairs[i].substring(0,pos);\n                         var value = pairs[i].substring(pos+1);\n                         if(argname == \"isFromApi\")\n                         {\n                                 showMail = true;\n                         }\n                         //keys[keys.length] = argname;\n                         //values[values.length] = value;\n                 }\n         }\n         //alert(showMail);\n         return  showMail;\n }\n\nfunction doInitStuffOnBodyLoad()\n{\n        ");
/*      */           
/*  288 */           if ((pageContext.getAttribute("jsppage") != null) && (pageContext.getAttribute("jsppage").equals("programaction")))
/*      */           {
/*      */ 
/*  291 */             out.write("\n        myOnLoad1();\n        ");
/*      */           }
/*      */           
/*      */ 
/*  295 */           out.write("\n        \n\tif(document.AMActionForm!=null)\n\t{\n\tif(typeof(document.AMActionForm.actions)!='undefined')\n\t  {\n\t  if((location.search!= null && (location.search).match(\"EmailAction\")!=null) || (location.path!=null && (location.path).match(\"EMailActionForm\")!=null))\n\t  {\n\t\t\n\t\t");
/*  296 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  298 */           out.write("\t\n\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"SMSAction\")!=null) || (location.path!=null && (location.path).match(\"SMSActionForm\")!=null) || (location.search!= null && (location.search).match(\"SMSServerConfiguration\")!=null))\n\t  {\n\t\t \n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"ExecProg\")!=null) || (location.path!=null && (location.path).match(\"ExecProgramActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"reloadSendTrapActionForm\")!=null) || (location.path!=null && (location.path).match(\"SendTrapActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"Ticket\")!=null) || (location.path!=null && (location.path).match(\"showLogTicket\")!=null) ||  (location.search).match(\"showTicketAction\")!=null )\n");
/*  299 */           out.write("\t  {\n\t\t\t");
/*  300 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  302 */           out.write("\n\t\t  ");
/*      */           
/*  304 */           if ((com.adventnet.appmanager.util.Constants.sqlManager) || (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */           {
/*  306 */             out.write("\n\t\t\tdocument.AMActionForm.actions.selectedIndex=4;\n\t    \t");
/*      */           }
/*      */           else
/*      */           {
/*  310 */             out.write("\n\t       \tdocument.AMActionForm.actions.selectedIndex=5;\n\t  \t\t");
/*      */           }
/*  312 */           out.write("\n\t  }\n\t  else if(location.pathname!= null && (location.pathname).match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(location.search!= null && (location.search).match(\"jre\")!=null || (location.search!=null && (location.search).match(\"ThreadDumpActions\")!=null))\n\t  {\n\t    ");
/*  313 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer())
/*      */           {
/*  315 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=5;\n\t\t");
/*      */           }
/*      */           else
/*      */           {
/*  319 */             out.write("\n\t    document.AMActionForm.actions.selectedIndex=6;\n        ");
/*      */           }
/*  321 */           out.write("\n\t  }\t\n\t  else if(location.search!= null && (location.search).match(\"showVMAction\")!=null || (location.search!=null && (location.search).match(\"ShowVMActions\")!=null))\n\t  {\t\t \n\t    ");
/*  322 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer())
/*      */           {
/*  324 */             out.write("\n\t    if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t    \tdocument.AMActionForm.actions.selectedIndex=9;\n\t    }else{\n\t\t\tdocument.AMActionForm.actions.selectedIndex=7;\n\t    }\n\t\t");
/*      */           }
/*      */           else
/*      */           {
/*  328 */             out.write("\n\t\t  if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t\t    \tdocument.AMActionForm.actions.selectedIndex=10;\n\t\t    }else{\n\t  \t  document.AMActionForm.actions.selectedIndex=8;\n\t\t    }\n        ");
/*      */           }
/*  330 */           out.write("\n\t  }\t  \n\t  else if(location.search!= null && (location.search).match(\"winServAction\")!=null || (location.search!=null && (location.search).match(\"winServAction\")!=null))\n\t  { \n\t    ");
/*  331 */           if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  332 */             out.write("\n\t      document.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */           }
/*  334 */           else if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) {
/*  335 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=8;\n\t\t");
/*      */           } else {
/*  337 */             out.write("\n          document.AMActionForm.actions.selectedIndex=9;\n        ");
/*      */           }
/*  339 */           out.write("\t\t\n\t  }\t  \n\t   else if((location.search!= null && (location.search).match(\"ExecQueryAction\")!=null) || (location.path!=null && (location.path).match(\"ExecQueryActionForm\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=5;\n  \t   }\n\t   else if((location.search!= null && (location.search).match(\"sqlJobAction\")!=null) || (location.path!=null && (location.path).match(\"sqlJobAction\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=7;\n  \t   }\n\t   else if(location.search!= null && (location.search).match(\"amazon\")!=null)\n\t\t  {\n\t\t    ");
/*  340 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) {
/*  341 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */           } else {
/*  343 */             out.write("\n\t\t    document.AMActionForm.actions.selectedIndex=7;\n        ");
/*      */           }
/*  345 */           out.write("\n\t\t  }\n\t  \t  \n\t  }\n\n\tif(document.AMActionForm.selectedBusinessHourID != null)\n\t{\n\t\tinit();\n\t}\n\t\n\t}\n\telse\n\t{\n\t\tif(document.MBeanOperationActionForm.actions!=undefined)\n\t\t{\n\t  \t      document.MBeanOperationActionForm.actions.selectedIndex=4;\t  \n\t  \t}\n\t}\n\n}\nfunction restvalue()\n{\n//alert(document.AMActionForm.actionslist.selectedIndex);\nvar selectedIdx=document.AMActionForm.actionslist.selectedIndex;\ndocument.AMActionForm.reset();\ndocument.AMActionForm.actionslist.selectedIndex=selectedIdx;\n}\n</script>\n");
/*      */           
/*  347 */           String action_haid = request.getParameter("haid");
/*  348 */           String returnpath = "";
/*      */           
/*  350 */           if (request.getParameter("returnpath") != null)
/*      */           {
/*  352 */             returnpath = "&returnpath=" + java.net.URLEncoder.encode(request.getParameter("returnpath"));
/*      */           }
/*      */           
/*      */ 
/*  356 */           out.write(10);
/*  357 */           out.write(10);
/*      */           
/*  359 */           SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  360 */           _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  361 */           _jspx_th_c_005fset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  363 */           _jspx_th_c_005fset_005f0.setVar("isSqlManager");
/*  364 */           int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  365 */           if (_jspx_eval_c_005fset_005f0 != 0) {
/*  366 */             if (_jspx_eval_c_005fset_005f0 != 1) {
/*  367 */               out = _jspx_page_context.pushBody();
/*  368 */               _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  369 */               _jspx_th_c_005fset_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  372 */               out.print(com.adventnet.appmanager.util.Constants.sqlManager);
/*  373 */               int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  374 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  377 */             if (_jspx_eval_c_005fset_005f0 != 1) {
/*  378 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  381 */           if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  382 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */           }
/*      */           
/*  385 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  386 */           out.write(10);
/*      */           
/*  388 */           SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  389 */           _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  390 */           _jspx_th_c_005fset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  392 */           _jspx_th_c_005fset_005f1.setVar("isIt360");
/*  393 */           int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  394 */           if (_jspx_eval_c_005fset_005f1 != 0) {
/*  395 */             if (_jspx_eval_c_005fset_005f1 != 1) {
/*  396 */               out = _jspx_page_context.pushBody();
/*  397 */               _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/*  398 */               _jspx_th_c_005fset_005f1.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  401 */               out.print(com.adventnet.appmanager.util.Constants.isIt360);
/*  402 */               int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/*  403 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  406 */             if (_jspx_eval_c_005fset_005f1 != 1) {
/*  407 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  410 */           if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  411 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */           }
/*      */           
/*  414 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/*  415 */           out.write(10);
/*      */           
/*  417 */           SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  418 */           _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/*  419 */           _jspx_th_c_005fset_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  421 */           _jspx_th_c_005fset_005f2.setVar("isAdminServer");
/*  422 */           int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/*  423 */           if (_jspx_eval_c_005fset_005f2 != 0) {
/*  424 */             if (_jspx_eval_c_005fset_005f2 != 1) {
/*  425 */               out = _jspx_page_context.pushBody();
/*  426 */               _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/*  427 */               _jspx_th_c_005fset_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  430 */               out.print(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer());
/*  431 */               int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/*  432 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  435 */             if (_jspx_eval_c_005fset_005f2 != 1) {
/*  436 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  439 */           if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/*  440 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */           }
/*      */           
/*  443 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/*  444 */           out.write(10);
/*      */           
/*  446 */           SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  447 */           _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/*  448 */           _jspx_th_c_005fset_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  450 */           _jspx_th_c_005fset_005f3.setVar("isProfServer");
/*  451 */           int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/*  452 */           if (_jspx_eval_c_005fset_005f3 != 0) {
/*  453 */             if (_jspx_eval_c_005fset_005f3 != 1) {
/*  454 */               out = _jspx_page_context.pushBody();
/*  455 */               _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/*  456 */               _jspx_th_c_005fset_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  459 */               out.print(com.adventnet.appmanager.util.EnterpriseUtil.isProfEdition());
/*  460 */               int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/*  461 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  464 */             if (_jspx_eval_c_005fset_005f3 != 1) {
/*  465 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  468 */           if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/*  469 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */           }
/*      */           
/*  472 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/*  473 */           out.write(10);
/*      */           
/*  475 */           SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  476 */           _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/*  477 */           _jspx_th_c_005fset_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  479 */           _jspx_th_c_005fset_005f4.setVar("isCloudServer");
/*  480 */           int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/*  481 */           if (_jspx_eval_c_005fset_005f4 != 0) {
/*  482 */             if (_jspx_eval_c_005fset_005f4 != 1) {
/*  483 */               out = _jspx_page_context.pushBody();
/*  484 */               _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/*  485 */               _jspx_th_c_005fset_005f4.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  488 */               out.print(com.adventnet.appmanager.util.EnterpriseUtil.isCloudEdition());
/*  489 */               int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/*  490 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  493 */             if (_jspx_eval_c_005fset_005f4 != 1) {
/*  494 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  497 */           if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/*  498 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */           }
/*      */           
/*  501 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/*  502 */           out.write("\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" >\n  <tr> \n    <td align=\"left\" width=\"50%\" class=\"bodytext\">");
/*  503 */           out.print(FormatUtil.getString("am.webclient.newaction.selectactiontype"));
/*  504 */           out.write("&nbsp;\n\t<select id=\"actionslist\" name=\"actions\" onchange=\"javascript:fnFormSubmit1(this.form.actions.options[this.selectedIndex].value);\" class=\"formtext\">\n\t");
/*      */           
/*  506 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  507 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  508 */           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_html_005fform_005f0);
/*  509 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  510 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */             for (;;) {
/*  512 */               out.write(10);
/*  513 */               out.write(9);
/*      */               
/*  515 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  516 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  517 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */               
/*  519 */               _jspx_th_c_005fwhen_005f0.setTest("${empty param.global}");
/*  520 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  521 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                 for (;;) {
/*  523 */                   out.write("\t\n\t\t<option value=\"/showTile.do?TileName=.EmailActions&haid=");
/*  524 */                   out.print(action_haid);
/*  525 */                   out.print(returnpath);
/*  526 */                   out.write(34);
/*  527 */                   out.write(62);
/*  528 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  529 */                   out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.SMSActions&haid=");
/*  530 */                   out.print(action_haid);
/*  531 */                   out.print(returnpath);
/*  532 */                   out.write(34);
/*  533 */                   out.write(62);
/*  534 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  535 */                   out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.ExecProg&haid=");
/*  536 */                   out.print(action_haid);
/*  537 */                   out.print(returnpath);
/*  538 */                   out.write(34);
/*  539 */                   out.write(62);
/*  540 */                   out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  541 */                   out.write("</option>\n\t\t<option value=\"/adminAction.do?method=reloadSendTrapActionForm&haid=");
/*  542 */                   out.print(action_haid);
/*  543 */                   out.print(returnpath);
/*  544 */                   out.write(34);
/*  545 */                   out.write(62);
/*  546 */                   out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/*  547 */                   out.write("</option>\n\t\t\n\t\t");
/*      */                   
/*  549 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  550 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  551 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*  552 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  553 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/*  555 */                       out.write("\n\t\t\t");
/*      */                       
/*  557 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  558 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  559 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/*  561 */                       _jspx_th_c_005fwhen_005f1.setTest("${!isIt360}");
/*  562 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  563 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/*  565 */                           out.write("\n\t\t\t\t");
/*      */                           
/*  567 */                           ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  568 */                           _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  569 */                           _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/*  570 */                           int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  571 */                           if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                             for (;;) {
/*  573 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/*  575 */                               WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  576 */                               _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  577 */                               _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                               
/*  579 */                               _jspx_th_c_005fwhen_005f2.setTest("${!isSqlManager}");
/*  580 */                               int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  581 */                               if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                 for (;;) {
/*  583 */                                   out.write("\n\t\t\t\t\t\t<!-- MBean Operation-->\n\t\t\t\t\t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  584 */                                   out.print(action_haid);
/*  585 */                                   out.print(returnpath);
/*  586 */                                   out.write(34);
/*  587 */                                   out.write(62);
/*  588 */                                   out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  589 */                                   out.write("</option>\n\t\t\t\t\t");
/*  590 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  591 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  595 */                               if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  596 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                               }
/*      */                               
/*  599 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  600 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/*  602 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  603 */                               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  604 */                               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f2);
/*  605 */                               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  606 */                               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                 for (;;) {
/*  608 */                                   out.write("\n\t\t\t\t\t\t<!-- Execute Query Action-->\n\t   \t\t\t\t\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  609 */                                   out.print(action_haid);
/*  610 */                                   out.print(returnpath);
/*  611 */                                   out.write(34);
/*  612 */                                   out.write(62);
/*  613 */                                   out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  614 */                                   out.write("</option>\n\t   \t\t\t\t\t<!-- Sql job action -->\n\t\t\t\t\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  615 */                                   out.print(action_haid);
/*  616 */                                   out.print(returnpath);
/*  617 */                                   out.write(34);
/*  618 */                                   out.write(62);
/*  619 */                                   out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  620 */                                   out.write("</option>\n\t\t\t\t\t");
/*  621 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  622 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  626 */                               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  627 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                               }
/*      */                               
/*  630 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  631 */                               out.write("\n\t\t\t\t");
/*  632 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  633 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  637 */                           if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  638 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                           }
/*      */                           
/*  641 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  642 */                           out.write("\n\t\t\t\t\t<!-- Log Ticket Operation-->\n\t\t\t\t\t");
/*  643 */                           if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  644 */                             out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  645 */                             out.print(action_haid);
/*  646 */                             out.print(returnpath);
/*  647 */                             out.write(34);
/*  648 */                             out.write(62);
/*  649 */                             out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  650 */                             out.write("</option> ");
/*      */                           }
/*  652 */                           out.write("\n\t\t\t");
/*  653 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  654 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  658 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  659 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/*  662 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  663 */                       out.write("\n\t\t\t");
/*      */                       
/*  665 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  666 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  667 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  668 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  669 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/*  671 */                           out.write("\n\t\t   \t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  672 */                           out.print(action_haid);
/*  673 */                           out.print(returnpath);
/*  674 */                           out.write(34);
/*  675 */                           out.write(62);
/*  676 */                           out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  677 */                           out.write("</option>\n\t\t   \t\t<!-- Log Ticket Operation-->\n\t\t   \t\t");
/*      */                           
/*  679 */                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  680 */                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  681 */                           _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                           
/*  683 */                           _jspx_th_c_005fif_005f2.setTest("${isProfServer || isAdminServer}");
/*  684 */                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  685 */                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                             for (;;) {
/*  687 */                               out.write("\n\t\t\t\t\t");
/*  688 */                               if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  689 */                                 out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  690 */                                 out.print(action_haid);
/*  691 */                                 out.print(returnpath);
/*  692 */                                 out.write(34);
/*  693 */                                 out.write(62);
/*  694 */                                 out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  695 */                                 out.write("</option> ");
/*      */                               }
/*  697 */                               out.write("\n\t\t   \t\t");
/*  698 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  699 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  703 */                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  704 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                           }
/*      */                           
/*  707 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  708 */                           out.write("\n\t\t\t");
/*  709 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  710 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  714 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  715 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/*  718 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  719 */                       out.write(10);
/*  720 */                       out.write(9);
/*  721 */                       out.write(9);
/*  722 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  723 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  727 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  728 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/*  731 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  732 */                   out.write(10);
/*  733 */                   out.write(9);
/*  734 */                   out.write(9);
/*      */                   
/*  736 */                   IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  737 */                   _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  738 */                   _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  740 */                   _jspx_th_c_005fif_005f3.setTest("${!isAdminServer}");
/*  741 */                   int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  742 */                   if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                     for (;;) {
/*  744 */                       out.write("\n\t\t\t<!--JRE Action -->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  745 */                       out.print(action_haid);
/*  746 */                       out.print(returnpath);
/*  747 */                       out.write(34);
/*  748 */                       out.write(62);
/*  749 */                       out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  750 */                       out.write("</option>\n\t\t\t<!--Amazon Instance Action-->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  751 */                       out.print(action_haid);
/*  752 */                       out.print(returnpath);
/*  753 */                       out.write(34);
/*  754 */                       out.write(62);
/*  755 */                       out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  756 */                       out.write("</option>\n\t\t\t<!--VM Action -->\n\t      \t<option value=\"/adminAction.do?method=showVMAction&haid=");
/*  757 */                       out.print(action_haid);
/*  758 */                       out.print(returnpath);
/*  759 */                       out.write(34);
/*  760 */                       out.write(62);
/*  761 */                       out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  762 */                       out.write("</option>\n\t      \t\n\t\t\t<!-- Windows Service action -->\n\t\t\t");
/*      */                       
/*  764 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  765 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  766 */                       _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */                       
/*  768 */                       _jspx_th_c_005fif_005f4.setTest("${!isCloudServer}");
/*  769 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  770 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/*  772 */                           out.write("\n\t   \t\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  773 */                           out.print(action_haid);
/*  774 */                           out.print(returnpath);
/*  775 */                           out.write(34);
/*  776 */                           out.write(62);
/*  777 */                           out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  778 */                           out.write("</option>\n\t   \t\t");
/*  779 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  780 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  784 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  785 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/*  788 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  789 */                       out.write("\n\t   \t\t<option value=\"/adminAction.do?method=showVMAction&isContainerAction=true&haid=");
/*  790 */                       out.print(action_haid);
/*  791 */                       out.print(returnpath);
/*  792 */                       out.write(34);
/*  793 */                       out.write(62);
/*  794 */                       out.print(FormatUtil.getString("am.container.action.createnew"));
/*  795 */                       out.write("</option>\n   \t\t");
/*  796 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  797 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  801 */                   if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  802 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                   }
/*      */                   
/*  805 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  806 */                   out.write(10);
/*  807 */                   out.write(9);
/*  808 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  809 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  813 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  814 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */               }
/*      */               
/*  817 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  818 */               out.write(10);
/*  819 */               out.write(9);
/*      */               
/*  821 */               OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  822 */               _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  823 */               _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*  824 */               int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  825 */               if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                 for (;;) {
/*  827 */                   out.write(10);
/*      */                   
/*  829 */                   String redirectTo = null;
/*  830 */                   if (request.getParameter("redirectto") != null)
/*      */                   {
/*  832 */                     redirectTo = java.net.URLEncoder.encode(request.getParameter("redirectto"));
/*      */                   }
/*      */                   else
/*      */                   {
/*  836 */                     redirectTo = java.net.URLEncoder.encode("/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true");
/*      */                   }
/*      */                   
/*  839 */                   out.write("\t\n\t<option value=\"/showTile.do?TileName=.EmailActions&PRINTER_FRIENDLY=true&haid=");
/*  840 */                   out.print(action_haid);
/*  841 */                   out.write("&global=true");
/*  842 */                   out.print(returnpath);
/*  843 */                   out.write(34);
/*  844 */                   out.write(62);
/*  845 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  846 */                   out.write("</option>\n\t<option value=\"/showTile.do?TileName=.SMSActions&PRINTER_FRIENDLY=true&haid=");
/*  847 */                   out.print(action_haid);
/*  848 */                   out.write("&global=true");
/*  849 */                   out.print(returnpath);
/*  850 */                   out.write(34);
/*  851 */                   out.write(62);
/*  852 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  853 */                   out.write("</option>\n\t");
/*      */                   
/*  855 */                   PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  856 */                   _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  857 */                   _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                   
/*  859 */                   _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/*  860 */                   int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  861 */                   if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                     for (;;) {
/*  863 */                       out.write(32);
/*  864 */                       out.write("\n\t<option value=\"/jsp/ExecProgramActionForm.jsp?haid=");
/*  865 */                       out.print(action_haid);
/*  866 */                       out.write("&global=true");
/*  867 */                       out.print(returnpath);
/*  868 */                       out.write(34);
/*  869 */                       out.write(62);
/*  870 */                       out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  871 */                       out.write("</option>\n\t");
/*  872 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/*  873 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  877 */                   if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/*  878 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                   }
/*      */                   
/*  881 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*  882 */                   out.write("\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=11&haid=");
/*  883 */                   out.print(action_haid);
/*  884 */                   out.write("&global=true");
/*  885 */                   out.print(returnpath);
/*  886 */                   out.write(34);
/*  887 */                   out.write(62);
/*  888 */                   out.print(FormatUtil.getString("am.webclient.common.sendv1trap.text"));
/*  889 */                   out.write("</option>\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=12&haid=");
/*  890 */                   out.print(action_haid);
/*  891 */                   out.write("&global=true");
/*  892 */                   out.print(returnpath);
/*  893 */                   out.write(34);
/*  894 */                   out.write(62);
/*  895 */                   out.print(FormatUtil.getString("am.webclient.common.sendv2ctrap.text"));
/*  896 */                   out.write("</option>\n\t");
/*  897 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))) {
/*  898 */                     out.write(32);
/*  899 */                     out.write("\n\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&popup=true&global=true&haid=");
/*  900 */                     out.print(action_haid);
/*  901 */                     out.print(returnpath);
/*  902 */                     out.write(34);
/*  903 */                     out.write(62);
/*  904 */                     out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  905 */                     out.write("</option>\n\t");
/*      */                   }
/*  907 */                   out.write(10);
/*  908 */                   out.write(9);
/*  909 */                   out.write(9);
/*  910 */                   out.write(10);
/*  911 */                   out.write(9);
/*  912 */                   if ((!com.adventnet.appmanager.util.Constants.isIt360) || (com.adventnet.appmanager.util.EnterpriseUtil.isProfEdition()) || (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */                   {
/*  914 */                     out.write("<option value=\"/adminAction.do?method=showLogTicket&global=true&haid=");
/*  915 */                     out.print(action_haid);
/*  916 */                     out.write("&redirectTo=");
/*  917 */                     out.print(redirectTo);
/*  918 */                     out.write(34);
/*  919 */                     out.write(62);
/*  920 */                     out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  921 */                     out.write("</option> ");
/*      */                   }
/*      */                   
/*  924 */                   out.write("\n\t\n\t");
/*  925 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))) {
/*  926 */                     out.write(" \n\t<!--JRE Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  927 */                     out.print(action_haid);
/*  928 */                     out.write("&global=true");
/*  929 */                     out.print(returnpath);
/*  930 */                     out.write("&ext=true\">");
/*  931 */                     out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  932 */                     out.write("</option>\n\t<!--VM Action -->\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&haid=");
/*  933 */                     out.print(action_haid);
/*  934 */                     out.print(returnpath);
/*  935 */                     out.write("&ext=true&global=true\">");
/*  936 */                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  937 */                     out.write("</option>\n\t<!--Amazon Instance Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  938 */                     out.print(action_haid);
/*  939 */                     out.write("&global=true");
/*  940 */                     out.print(returnpath);
/*  941 */                     out.write("&ext=true\">");
/*  942 */                     out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  943 */                     out.write("</option>\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&isContainerAction=true&haid=");
/*  944 */                     out.print(action_haid);
/*  945 */                     out.print(returnpath);
/*  946 */                     out.write("&ext=true&global=true\">");
/*  947 */                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  948 */                     out.write("</option>\n\t ");
/*  949 */                   } else if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  950 */                     out.write(32);
/*  951 */                     out.write("\n\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  952 */                     out.print(action_haid);
/*  953 */                     out.write("&global=true");
/*  954 */                     out.print(returnpath);
/*  955 */                     out.write(34);
/*  956 */                     out.write(62);
/*  957 */                     out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  958 */                     out.write("</option>\n\t");
/*      */                   }
/*  960 */                   out.write(10);
/*  961 */                   out.write(9);
/*  962 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())) && (!"CLOUD".equalsIgnoreCase(com.adventnet.appmanager.util.Constants.getCategorytype()))) {
/*  963 */                     out.write("\n\t<!-- Windows Service action -->\n\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  964 */                     out.print(action_haid);
/*  965 */                     out.print(returnpath);
/*  966 */                     out.write(34);
/*  967 */                     out.write(62);
/*  968 */                     out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  969 */                     out.write("</option>\t\n\t");
/*      */                   }
/*  971 */                   out.write(10);
/*  972 */                   out.write(9);
/*  973 */                   out.write(32);
/*  974 */                   if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  975 */                     out.write("\n\t<!-- Sql job action -->\n\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  976 */                     out.print(action_haid);
/*  977 */                     out.print(returnpath);
/*  978 */                     out.write(34);
/*  979 */                     out.write(62);
/*  980 */                     out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  981 */                     out.write("</option>\n\t");
/*      */                   }
/*  983 */                   out.write(10);
/*  984 */                   out.write(9);
/*  985 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  986 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  990 */               if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  991 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */               }
/*      */               
/*  994 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  995 */               out.write(10);
/*  996 */               out.write(9);
/*  997 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  998 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1002 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1003 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */           }
/*      */           
/* 1006 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1007 */           out.write("\n\t</select>\n    </td>\n  </tr>\n</table>\n\n");
/*      */           
/* 1009 */           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1010 */           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 1011 */           _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1013 */           _jspx_th_c_005fif_005f5.setTest("${param.global=='true'}");
/* 1014 */           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1015 */           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */             for (;;) {
/* 1017 */               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td valign=\"top\"> \n<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/* 1018 */               out.write("<!--$Id$-->\n\n\n\n");
/* 1019 */               if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                 return;
/* 1021 */               out.write("\n      \n\n");
/*      */               
/* 1023 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1024 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1025 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f5);
/*      */               
/* 1027 */               _jspx_th_c_005fif_005f6.setTest("${(uri=='/jsp/CreateApplication.jsp')|| uri=='/admin/createapplication.do'||uri=='/admin/createapplication.do' ||(!empty param.wiz && !empty param.haid && (empty invalidhaid))||(param.method=='insert')}");
/* 1028 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1029 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/* 1031 */                   out.write("\n\t  <tr>\n\t  <td class=\"tdindent\">\n");
/* 1032 */                   if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1034 */                   out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">  \n  <tr> \n    <td width=\"2%\">");
/*      */                   
/* 1036 */                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1037 */                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 1038 */                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1040 */                   _jspx_th_c_005fif_005f7.setTest("${uri=='/jsp/CreateApplication.jsp' || uri=='/admin/createapplicationwiz.do'||uri=='/admin/createapplication.do'}");
/* 1041 */                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 1042 */                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                     for (;;) {
/* 1044 */                       out.write("\n    \t");
/*      */                       
/* 1046 */                       SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1047 */                       _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 1048 */                       _jspx_th_c_005fset_005f6.setParent(_jspx_th_c_005fif_005f7);
/*      */                       
/* 1050 */                       _jspx_th_c_005fset_005f6.setVar("wizimage");
/* 1051 */                       int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 1052 */                       if (_jspx_eval_c_005fset_005f6 != 0) {
/* 1053 */                         if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1054 */                           out = _jspx_page_context.pushBody();
/* 1055 */                           _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 1056 */                           _jspx_th_c_005fset_005f6.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1059 */                           out.write(" \n    \t<img src=\"/images/wiz_newbizapp_high.gif\" border=\"0\" align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1060 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1061 */                           out.write(" </b></font>\n    \t");
/* 1062 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 1063 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1066 */                         if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1067 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1070 */                       if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 1071 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6); return;
/*      */                       }
/*      */                       
/* 1074 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 1075 */                       out.write("\n    ");
/* 1076 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1077 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1081 */                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1082 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                   }
/*      */                   
/* 1085 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1086 */                   out.write("\n    ");
/*      */                   
/* 1088 */                   IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1089 */                   _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 1090 */                   _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1092 */                   _jspx_th_c_005fif_005f8.setTest("${uri!='/jsp/CreateApplication.jsp' && uri!='/admin/createapplicationwiz.do'&& uri!='/admin/createapplication.do'}");
/* 1093 */                   int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 1094 */                   if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                     for (;;) {
/* 1096 */                       out.write("\n    \t");
/*      */                       
/* 1098 */                       SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1099 */                       _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 1100 */                       _jspx_th_c_005fset_005f7.setParent(_jspx_th_c_005fif_005f8);
/*      */                       
/* 1102 */                       _jspx_th_c_005fset_005f7.setVar("wizimage");
/* 1103 */                       int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 1104 */                       if (_jspx_eval_c_005fset_005f7 != 0) {
/* 1105 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1106 */                           out = _jspx_page_context.pushBody();
/* 1107 */                           _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 1108 */                           _jspx_th_c_005fset_005f7.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1111 */                           out.write("\n    \t<img src=\"/images/wiz_newbizapp_nor.gif\" border=0> <font family=\"verdana\" size=\"2\" color=\"#818181\">");
/* 1112 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1113 */                           out.write("</font>  \t");
/* 1114 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 1115 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1118 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1119 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1122 */                       if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 1123 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                       }
/*      */                       
/* 1126 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 1127 */                       out.write("\n    ");
/* 1128 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 1129 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1133 */                   if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 1134 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                   }
/*      */                   
/* 1137 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 1138 */                   out.write("      \n\t</td>\n    <td width=\"20%\">");
/* 1139 */                   if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1141 */                   out.write("</td>  \n   \n");
/*      */                   
/* 1143 */                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1144 */                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1145 */                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f6);
/* 1146 */                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1147 */                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                     for (;;) {
/* 1149 */                       out.write("\n    ");
/*      */                       
/* 1151 */                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1152 */                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1153 */                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                       
/* 1155 */                       _jspx_th_c_005fwhen_005f3.setTest("${( param.method=='configureHostDiscovery' || param.method=='associateMonitors'||param.method=='getMonitorForm'||param.method=='addResource')&& (empty showwiz3) && (empty UrlForm) }");
/* 1156 */                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1157 */                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                         for (;;) {
/* 1159 */                           out.write("\n    \n\t\n\t\t\n\t\t");
/*      */                           
/* 1161 */                           SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1162 */                           _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 1163 */                           _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                           
/* 1165 */                           _jspx_th_c_005fset_005f8.setVar("wizimage");
/* 1166 */                           int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 1167 */                           if (_jspx_eval_c_005fset_005f8 != 0) {
/* 1168 */                             if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1169 */                               out = _jspx_page_context.pushBody();
/* 1170 */                               _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 1171 */                               _jspx_th_c_005fset_005f8.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1174 */                               out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1175 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1176 */                               out.write(" </b></font>\n    \t");
/* 1177 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 1178 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1181 */                             if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1182 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1185 */                           if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 1186 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                           }
/*      */                           
/* 1189 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 1190 */                           out.write("\n   ");
/* 1191 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1192 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1196 */                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1197 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                       }
/*      */                       
/* 1200 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1201 */                       out.write("\n   ");
/*      */                       
/* 1203 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1204 */                       _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1205 */                       _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 1206 */                       int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1207 */                       if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                         for (;;) {
/* 1209 */                           out.write("  \n    \t\n\t\t");
/*      */                           
/* 1211 */                           SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1212 */                           _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 1213 */                           _jspx_th_c_005fset_005f9.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                           
/* 1215 */                           _jspx_th_c_005fset_005f9.setVar("wizimage");
/* 1216 */                           int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 1217 */                           if (_jspx_eval_c_005fset_005f9 != 0) {
/* 1218 */                             if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1219 */                               out = _jspx_page_context.pushBody();
/* 1220 */                               _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 1221 */                               _jspx_th_c_005fset_005f9.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1224 */                               out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1225 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1226 */                               out.write(" </font>\n    \t");
/* 1227 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 1228 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1231 */                             if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1232 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1235 */                           if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 1236 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                           }
/*      */                           
/* 1239 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 1240 */                           out.write("\n\t\n\t\t\n   ");
/* 1241 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1242 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1246 */                       if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1247 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                       }
/*      */                       
/* 1250 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1251 */                       out.write(10);
/* 1252 */                       out.write(32);
/* 1253 */                       out.write(32);
/* 1254 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1255 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1259 */                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1260 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                   }
/*      */                   
/* 1263 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1264 */                   out.write(" \n\n    \n     <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"20%\">\n    ");
/* 1265 */                   if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1267 */                   out.write("\n    \t");
/* 1268 */                   if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1270 */                   out.write("\n    \t\n    \t");
/* 1271 */                   if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1273 */                   out.write("\n    \t</td>    \t\n    \t<!-- ############################################# check for third tab #####################################  -->\n       ");
/*      */                   
/* 1275 */                   ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1276 */                   _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1277 */                   _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f6);
/* 1278 */                   int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1279 */                   if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                     for (;;) {
/* 1281 */                       out.write("\n       ");
/*      */                       
/* 1283 */                       WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1284 */                       _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1285 */                       _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                       
/* 1287 */                       _jspx_th_c_005fwhen_005f4.setTest("${(param.method=='reloadHostDiscoveryForm'|| param.method=='getMonitorForm'||param.method=='addResource'|| (!empty showwiz3) || (!empty UrlForm) ) && (param.method!='getHAProfiles') }");
/* 1288 */                       int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1289 */                       if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                         for (;;) {
/* 1291 */                           out.write("\n   \n   \t    \t");
/*      */                           
/* 1293 */                           SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1294 */                           _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 1295 */                           _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                           
/* 1297 */                           _jspx_th_c_005fset_005f10.setVar("wizimage");
/* 1298 */                           int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 1299 */                           if (_jspx_eval_c_005fset_005f10 != 0) {
/* 1300 */                             if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1301 */                               out = _jspx_page_context.pushBody();
/* 1302 */                               _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 1303 */                               _jspx_th_c_005fset_005f10.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1306 */                               out.write("\n   \t    \t<img src=\"/images/new_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b> ");
/* 1307 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1308 */                               out.write(" </b></font>\n   \t    \t");
/* 1309 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 1310 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1313 */                             if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1314 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1317 */                           if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 1318 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                           }
/*      */                           
/* 1321 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 1322 */                           out.write("\n       ");
/* 1323 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1324 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1328 */                       if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1329 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                       }
/*      */                       
/* 1332 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1333 */                       out.write("\n        ");
/*      */                       
/* 1335 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1336 */                       _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1337 */                       _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1338 */                       int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1339 */                       if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                         for (;;) {
/* 1341 */                           out.write("  \n   \t    \t");
/*      */                           
/* 1343 */                           SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1344 */                           _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 1345 */                           _jspx_th_c_005fset_005f11.setParent(_jspx_th_c_005fotherwise_005f4);
/*      */                           
/* 1347 */                           _jspx_th_c_005fset_005f11.setVar("wizimage");
/* 1348 */                           int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 1349 */                           if (_jspx_eval_c_005fset_005f11 != 0) {
/* 1350 */                             if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1351 */                               out = _jspx_page_context.pushBody();
/* 1352 */                               _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 1353 */                               _jspx_th_c_005fset_005f11.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1356 */                               out.write("\n\t\t   \t    \t<img src=\"/images/new_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1357 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1358 */                               out.write(" </font>\n   \t    \t");
/* 1359 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 1360 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1363 */                             if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1364 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1367 */                           if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 1368 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11); return;
/*      */                           }
/*      */                           
/* 1371 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 1372 */                           out.write("\n   \t");
/* 1373 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1374 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1378 */                       if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1379 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                       }
/*      */                       
/* 1382 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1383 */                       out.write(10);
/* 1384 */                       out.write(32);
/* 1385 */                       out.write(32);
/* 1386 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1387 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1391 */                   if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1392 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                   }
/*      */                   
/* 1395 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1396 */                   out.write(" \n\n        <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n       <td width=\"18%\">\n       ");
/* 1397 */                   if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1399 */                   out.write("\n       ");
/* 1400 */                   if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1402 */                   out.write("\n       ");
/* 1403 */                   out.write("\n       \t");
/* 1404 */                   if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1406 */                   out.write("\n    \t</td>\n   \n  <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"17%\">\n\t");
/*      */                   
/* 1408 */                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1409 */                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 1410 */                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1412 */                   _jspx_th_c_005fif_005f13.setTest("${param.method=='getHAProfiles'}");
/* 1413 */                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 1414 */                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                     for (;;) {
/* 1416 */                       out.write(10);
/* 1417 */                       out.write(9);
/*      */                       
/* 1419 */                       SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1420 */                       _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 1421 */                       _jspx_th_c_005fset_005f12.setParent(_jspx_th_c_005fif_005f13);
/*      */                       
/* 1423 */                       _jspx_th_c_005fset_005f12.setVar("wizimage");
/* 1424 */                       int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 1425 */                       if (_jspx_eval_c_005fset_005f12 != 0) {
/* 1426 */                         if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1427 */                           out = _jspx_page_context.pushBody();
/* 1428 */                           _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 1429 */                           _jspx_th_c_005fset_005f12.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1432 */                           out.write("\n\t\t<img src=\"/images/wiz_configurealerts_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b>");
/* 1433 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1434 */                           out.write(" </b></font>\n    \t");
/* 1435 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 1436 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1439 */                         if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1440 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1443 */                       if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 1444 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12); return;
/*      */                       }
/*      */                       
/* 1447 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 1448 */                       out.write(10);
/* 1449 */                       out.write(9);
/* 1450 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 1451 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1455 */                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 1456 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                   }
/*      */                   
/* 1459 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1460 */                   out.write(10);
/* 1461 */                   out.write(9);
/*      */                   
/* 1463 */                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1464 */                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 1465 */                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1467 */                   _jspx_th_c_005fif_005f14.setTest("${param.method!='getHAProfiles'}");
/* 1468 */                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 1469 */                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                     for (;;) {
/* 1471 */                       out.write(10);
/* 1472 */                       out.write(9);
/*      */                       
/* 1474 */                       SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1475 */                       _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 1476 */                       _jspx_th_c_005fset_005f13.setParent(_jspx_th_c_005fif_005f14);
/*      */                       
/* 1478 */                       _jspx_th_c_005fset_005f13.setVar("wizimage");
/* 1479 */                       int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 1480 */                       if (_jspx_eval_c_005fset_005f13 != 0) {
/* 1481 */                         if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1482 */                           out = _jspx_page_context.pushBody();
/* 1483 */                           _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 1484 */                           _jspx_th_c_005fset_005f13.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1487 */                           out.write("\n\t\t<img src=\"/images/wiz_configurealerts_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1488 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1489 */                           out.write(" </font>\n    \t");
/* 1490 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 1491 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1494 */                         if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1495 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1498 */                       if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 1499 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13); return;
/*      */                       }
/*      */                       
/* 1502 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 1503 */                       out.write("\n\t\n\t");
/* 1504 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 1505 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1509 */                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 1510 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                   }
/*      */                   
/* 1513 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 1514 */                   out.write(10);
/* 1515 */                   if (_jspx_meth_c_005fif_005f15(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1517 */                   out.write("   \n ");
/* 1518 */                   if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1520 */                   out.write(10);
/* 1521 */                   out.write(32);
/* 1522 */                   out.write(10);
/* 1523 */                   if (_jspx_meth_c_005fif_005f16(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1525 */                   out.write("   \n    </td>\n    \n    <td width=\"2%\" rowspan=\"2\" valign=\"bottom\" align=\"right\"><img src=\"/images/wiz_endicon.gif\" width=\"33\" height=\"36\"></td>\n  </tr>\n  <tr background=\"/images/wiz_bg_graylind.gif\"> \n    <td><img src=\"/images/wiz_startimage.gif\" width=\"18\" height=\"16\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n   \n   <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n \n");
/* 1526 */                   out.write("  </tr>\n\n</table>\n</td></tr>\n");
/* 1527 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1528 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1532 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1533 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/* 1536 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1537 */               out.write(10);
/* 1538 */               out.write(10);
/* 1539 */               if (request.getAttribute("EmailForm") == null) {
/* 1540 */                 out.write(10);
/*      */                 
/* 1542 */                 MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1543 */                 _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 1544 */                 _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                 
/* 1546 */                 _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                 
/* 1548 */                 _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 1549 */                 int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 1550 */                 if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 1551 */                   String msg = null;
/* 1552 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1553 */                     out = _jspx_page_context.pushBody();
/* 1554 */                     _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 1555 */                     _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                   }
/* 1557 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/* 1559 */                     out.write(" \n<tr> \n  <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 1560 */                     if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                       return;
/* 1562 */                     out.write("</td>\n\t  </tr>\n\t</table>\n\t<br></td>\n</tr>\n");
/* 1563 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 1564 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/* 1565 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1568 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1569 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1572 */                 if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 1573 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                 }
/*      */                 
/* 1576 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */               }
/* 1578 */               out.write(32);
/*      */               
/* 1580 */               MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 1581 */               _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 1582 */               _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */               
/* 1584 */               _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 1585 */               int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 1586 */               if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 1588 */                   out.write(" \n<tr> \n  <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\"> ");
/*      */                   
/* 1590 */                   MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1591 */                   _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 1592 */                   _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                   
/* 1594 */                   _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                   
/* 1596 */                   _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 1597 */                   int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 1598 */                   if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 1599 */                     String msg = null;
/* 1600 */                     if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1601 */                       out = _jspx_page_context.pushBody();
/* 1602 */                       _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 1603 */                       _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                     }
/* 1605 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/* 1607 */                       out.write("\n\t  ");
/* 1608 */                       if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                         return;
/* 1610 */                       out.write("<br>\n\t  ");
/* 1611 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 1612 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/* 1613 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1616 */                     if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1617 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1620 */                   if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 1621 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                   }
/*      */                   
/* 1624 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 1625 */                   out.write(" </td>\n\t  </tr>\n\t</table></td>\n</tr>\n");
/* 1626 */                   int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 1627 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1631 */               if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 1632 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */               }
/*      */               
/* 1635 */               this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 1636 */               out.write("\n</table>\n<script>\n\tobject=location.href;\n\t\n\tif(document.AMActionForm!=null)\n\t{\t  \n\t  if(object.match(\"EMailActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if(object.match(\"SMSActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if(object.match(\"ExecProgramActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=11\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=12\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\t  \n\t  else if(object.match(\"showLogTicket\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=6;\n\t  }\n\t  else if(object.match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=5;\n\t  }\t\n\t  else if(object.match(\"jre\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=7;\n");
/* 1637 */               out.write("\t  }\n\t  else if(object.match(\"showVMAction\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=8;\n\t  }\n\t  else if(object.match(\"amazon\")!=null)\n\t  {\n\t\t    document.AMActionForm.actions.selectedIndex=9;\n\t }\t  \n\t}\n\telse if(document.MBeanOperationActionForm!=null)\n\t{\n\t  document.MBeanOperationActionForm.actions.selectedIndex=5;\t  \n\t}\n</script>\n</td>\n</tr>\n</table>\n");
/* 1638 */               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1639 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1643 */           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1644 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */           }
/*      */           
/* 1647 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1648 */           out.write(10);
/* 1649 */           out.write(10);
/*      */           
/* 1651 */           IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1652 */           _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 1653 */           _jspx_th_c_005fif_005f17.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1655 */           _jspx_th_c_005fif_005f17.setTest("${!empty param.returnpath}");
/* 1656 */           int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 1657 */           if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */             for (;;) {
/* 1659 */               out.write("\n<input name=\"returnpath\" type=\"hidden\" value=\"");
/* 1660 */               out.print(request.getParameter("returnpath"));
/* 1661 */               out.write(34);
/* 1662 */               out.write(62);
/* 1663 */               out.write(10);
/* 1664 */               int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 1665 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1669 */           if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 1670 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */           }
/*      */           
/* 1673 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 1674 */           out.write(10);
/* 1675 */           if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1677 */           out.write(10);
/* 1678 */           if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1680 */           out.write(10);
/* 1681 */           if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1683 */           out.write(10);
/* 1684 */           if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1686 */           out.write(10);
/* 1687 */           out.write(10);
/*      */           
/* 1689 */           if (isInvokedFromPopup)
/*      */           {
/*      */ 
/* 1692 */             out.write(10);
/*      */             
/* 1694 */             MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 1695 */             _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/* 1696 */             _jspx_th_logic_005fmessagesPresent_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 1698 */             _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/* 1699 */             int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/* 1700 */             if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*      */               for (;;) {
/* 1702 */                 out.write("\n          <table width=\"99%\" border=\"0\" cellspacing=\"3\" cellpadding=\"3\" class=\"messagebox\">\n              <tr>\n                <td width=\"95%\" class=\"message\"> ");
/*      */                 
/* 1704 */                 MessagesTag _jspx_th_html_005fmessages_005f2 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1705 */                 _jspx_th_html_005fmessages_005f2.setPageContext(_jspx_page_context);
/* 1706 */                 _jspx_th_html_005fmessages_005f2.setParent(_jspx_th_logic_005fmessagesPresent_005f1);
/*      */                 
/* 1708 */                 _jspx_th_html_005fmessages_005f2.setId("msg");
/*      */                 
/* 1710 */                 _jspx_th_html_005fmessages_005f2.setMessage("true");
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
/* 1721 */                     out.write("\n                  <li>");
/* 1722 */                     if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                       return;
/* 1724 */                     out.write("</li>\n                  ");
/* 1725 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f2.doAfterBody();
/* 1726 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/* 1727 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1730 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1731 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1734 */                 if (_jspx_th_html_005fmessages_005f2.doEndTag() == 5) {
/* 1735 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2); return;
/*      */                 }
/*      */                 
/* 1738 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2);
/* 1739 */                 out.write(" </td>\n              </tr>\n            </table>\n            <br>\n");
/* 1740 */                 int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/* 1741 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1745 */             if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/* 1746 */               this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1); return;
/*      */             }
/*      */             
/* 1749 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/* 1750 */             out.write(32);
/* 1751 */             out.write(10);
/*      */           }
/*      */           
/*      */ 
/* 1755 */           out.write("\n\n<div id=\"actionmessage\" style=\"display:none\"  class='error-text'>\n</div>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"70%\" valign=\"top\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n  ");
/* 1756 */           if (monitorType.equalsIgnoreCase("jre")) {
/* 1757 */             out.write("\n  <td width=\"2%\" class=\"tableheading-monitor-config \" align=\"right\"><img src=\"/images/java.png\" class=\"tableheading-add-icon\" style=\"position:relative; bottom:3px;\"></td>\n\n    <td width=\"98%\" height=\"31\" class=\"tableheading-monitor-config\"><span id=\"tit3\"> ");
/* 1758 */             out.print(FormatUtil.getString("am.javaruntime.action.createjtd"));
/* 1759 */             out.write("</span></td>\n  ");
/*      */           } else {
/* 1761 */             out.write("\n  <td width=\"2%\" class=\"tableheading-monitor-config \" align=\"right\">&nbsp;<img src=\"/images/icon_monitor_amazon.gif\" class=\"tableheading-add-icon\" style=\"position:relative; bottom:3px;\"></td>\n  <td width=\"98%\" height=\"31\" class=\"tableheading-monitor-config\">\n  ");
/* 1762 */             out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.createaction.text"));
/* 1763 */             out.write("\n  </td>\n  ");
/*      */           }
/* 1765 */           out.write("\n\n  </tr>\n</table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n    <td width=\"25%\" class=\"bodytext label-align\">");
/* 1766 */           out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/* 1767 */           out.write("</td>\n    <td  class=\"bodytext\"> ");
/* 1768 */           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1770 */           out.write("</td>\n  </tr>\n\n\n");
/* 1771 */           if (monitorType.equalsIgnoreCase("jre")) {
/* 1772 */             out.write("\n  \t<tr>\n<td width=\"25%\" class=\"bodytext label-align\">");
/* 1773 */             out.print(FormatUtil.getString("am.javaruntime.action.selecttype"));
/* 1774 */             out.write("</td>\n\t  <td class=\"bodytext\" colspan=\"3\" valign=\"middle\">\n\n\t  ");
/* 1775 */             if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1777 */             out.write("&nbsp;");
/* 1778 */             out.print(FormatUtil.getString("am.javaruntime.action.threaddump"));
/* 1779 */             out.write(" &nbsp;&nbsp;\n\t  ");
/* 1780 */             if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1782 */             out.write("&nbsp;");
/* 1783 */             out.print(FormatUtil.getString("am.javaruntime.action.heapdump"));
/* 1784 */             out.write(" &nbsp;&nbsp;\n\t  ");
/* 1785 */             if (_jspx_meth_html_005fradio_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1787 */             out.write("&nbsp;");
/* 1788 */             out.print(FormatUtil.getString("am.javaruntime.action.performgc"));
/* 1789 */             out.write(" </td>\n\t</tr>\n\n\n  <tr>\n    <td class=\"bodytext label-align\"><span id=\"tit1\">");
/* 1790 */             out.print(FormatUtil.getString("am.javaruntime.action.nooftd"));
/* 1791 */             out.write("</span></td>\n    <td class=\"bodytext\">");
/* 1792 */             if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1794 */             out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"bodytext label-align\"><span id=\"tit2\">");
/* 1795 */             out.print(FormatUtil.getString("am.javaruntime.action.delaybwtd"));
/* 1796 */             out.write("</span></td>\n    <td class=\"bodytext\">");
/* 1797 */             if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1799 */             out.write("</td>\n  </tr>\n  <tr>\n\t  <td class=\"bodytext label-align\" width=\"25%\" valign=\"top\">");
/* 1800 */             out.print(FormatUtil.getString("am.javaruntime.action.targetjre"));
/* 1801 */             out.write("</td>\n\t  <td colspan=\"2\" align=\"left\">\n\t  <table width=\"100%\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\">\n\t  <tr>\n\t  <td class=\"bodytext\" width=\"25%\" valign=\"middle\">\n\n\t\t \t");
/*      */             
/* 1803 */             SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 1804 */             _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 1805 */             _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 1807 */             _jspx_th_html_005fselect_005f0.setProperty("logConfig");
/*      */             
/* 1809 */             _jspx_th_html_005fselect_005f0.setStyleClass("formtext default");
/*      */             
/* 1811 */             _jspx_th_html_005fselect_005f0.setStyle("width:242px;vertical-align:middle;");
/*      */             
/* 1813 */             _jspx_th_html_005fselect_005f0.setOnchange("javascript:getResourceForSelectionType();");
/* 1814 */             int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 1815 */             if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 1816 */               if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1817 */                 out = _jspx_page_context.pushBody();
/* 1818 */                 _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 1819 */                 _jspx_th_html_005fselect_005f0.doInitBody();
/*      */               }
/*      */               for (;;) {
/* 1822 */                 out.write("\n\t\t\t");
/*      */                 
/* 1824 */                 OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1825 */                 _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 1826 */                 _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 1828 */                 _jspx_th_html_005foption_005f0.setValue("1");
/* 1829 */                 int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 1830 */                 if (_jspx_eval_html_005foption_005f0 != 0) {
/* 1831 */                   if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1832 */                     out = _jspx_page_context.pushBody();
/* 1833 */                     _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 1834 */                     _jspx_th_html_005foption_005f0.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1837 */                     out.print(FormatUtil.getString("am.javaruntime.action.type1"));
/* 1838 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 1839 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1842 */                   if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1843 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1846 */                 if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 1847 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                 }
/*      */                 
/* 1850 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 1851 */                 out.write("\n\t\t\t");
/*      */                 
/* 1853 */                 OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1854 */                 _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 1855 */                 _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 1857 */                 _jspx_th_html_005foption_005f1.setValue("2");
/* 1858 */                 int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 1859 */                 if (_jspx_eval_html_005foption_005f1 != 0) {
/* 1860 */                   if (_jspx_eval_html_005foption_005f1 != 1) {
/* 1861 */                     out = _jspx_page_context.pushBody();
/* 1862 */                     _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 1863 */                     _jspx_th_html_005foption_005f1.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1866 */                     out.print(FormatUtil.getString("am.javaruntime.action.type2"));
/* 1867 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 1868 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1871 */                   if (_jspx_eval_html_005foption_005f1 != 1) {
/* 1872 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1875 */                 if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 1876 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                 }
/*      */                 
/* 1879 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 1880 */                 out.write("\n\t\t\t");
/*      */                 
/* 1882 */                 OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1883 */                 _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 1884 */                 _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 1886 */                 _jspx_th_html_005foption_005f2.setValue("3");
/* 1887 */                 int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 1888 */                 if (_jspx_eval_html_005foption_005f2 != 0) {
/* 1889 */                   if (_jspx_eval_html_005foption_005f2 != 1) {
/* 1890 */                     out = _jspx_page_context.pushBody();
/* 1891 */                     _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 1892 */                     _jspx_th_html_005foption_005f2.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1895 */                     out.print(FormatUtil.getString("am.javaruntime.action.type3"));
/* 1896 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 1897 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1900 */                   if (_jspx_eval_html_005foption_005f2 != 1) {
/* 1901 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1904 */                 if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 1905 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                 }
/*      */                 
/* 1908 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 1909 */                 out.write("\n\t\t\t");
/*      */                 
/* 1911 */                 OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1912 */                 _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 1913 */                 _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 1915 */                 _jspx_th_html_005foption_005f3.setValue("4");
/* 1916 */                 int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 1917 */                 if (_jspx_eval_html_005foption_005f3 != 0) {
/* 1918 */                   if (_jspx_eval_html_005foption_005f3 != 1) {
/* 1919 */                     out = _jspx_page_context.pushBody();
/* 1920 */                     _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 1921 */                     _jspx_th_html_005foption_005f3.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1924 */                     out.print(FormatUtil.getString("am.javaruntime.action.type4"));
/* 1925 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 1926 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1929 */                   if (_jspx_eval_html_005foption_005f3 != 1) {
/* 1930 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1933 */                 if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 1934 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                 }
/*      */                 
/* 1937 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 1938 */                 out.write("\n\t\t\t");
/* 1939 */                 int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 1940 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 1943 */               if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1944 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 1947 */             if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 1948 */               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */             }
/*      */             
/* 1951 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 1952 */             out.write("\n\t\t</td>\n\t       </tr>\n\n");
/*      */           } else {
/* 1954 */             out.write("\n\t<tr>\n\t\t<td width=\"25%\" class=\"bodytext label-align\" valign=\"middle\">");
/* 1955 */             out.print(FormatUtil.getString("am.javaruntime.action.selecttype"));
/* 1956 */             out.write("</td>\n\t\t  <td class=\"bodytext\" colspan=\"3\" valign=\"middle\">\n\n\t\t  ");
/* 1957 */             if (_jspx_meth_html_005fradio_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1959 */             out.write("&nbsp;");
/* 1960 */             out.print(FormatUtil.getString("am.amazon.ec2Instanceactions.startinstances.text"));
/* 1961 */             out.write(" &nbsp;&nbsp;\n\t\t  ");
/* 1962 */             if (_jspx_meth_html_005fradio_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1964 */             out.write("&nbsp;");
/* 1965 */             out.print(FormatUtil.getString("am.amazon.ec2Instanceactions.stopinstances.text"));
/* 1966 */             out.write(" &nbsp;&nbsp;\n\t\t  ");
/* 1967 */             if (_jspx_meth_html_005fradio_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1969 */             out.write("&nbsp;");
/* 1970 */             out.print(FormatUtil.getString("am.amazon.ec2Instanceactions.restartinstances.text"));
/* 1971 */             out.write(" </td>\n\t</tr>\n\t<tr>\n\t\t  <td class=\"bodytext label-align\" width=\"25%\" valign=\"top\">");
/* 1972 */             out.print(FormatUtil.getString("am.ec2.form.selecttarget.text"));
/* 1973 */             out.write("</td>\n\t\t  <td colspan=\"2\" align=\"left\">\n\t      \t  <table width=\"100%\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t  <tr>\n\t\t  <td class=\"bodytext\" width=\"35%\" valign=\"middle\">\n\n\t\t\t \t");
/*      */             
/* 1975 */             SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 1976 */             _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 1977 */             _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 1979 */             _jspx_th_html_005fselect_005f1.setProperty("logConfig");
/*      */             
/* 1981 */             _jspx_th_html_005fselect_005f1.setStyleClass("formtext default");
/*      */             
/* 1983 */             _jspx_th_html_005fselect_005f1.setStyle("width:242px;vertical-align:middle;");
/*      */             
/* 1985 */             _jspx_th_html_005fselect_005f1.setOnchange("javascript:getResourceForSelectionType();");
/* 1986 */             int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 1987 */             if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 1988 */               if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 1989 */                 out = _jspx_page_context.pushBody();
/* 1990 */                 _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 1991 */                 _jspx_th_html_005fselect_005f1.doInitBody();
/*      */               }
/*      */               for (;;) {
/* 1994 */                 out.write("\n\n\t\t\t    ");
/*      */                 
/* 1996 */                 OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1997 */                 _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 1998 */                 _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f1);
/*      */                 
/* 2000 */                 _jspx_th_html_005foption_005f4.setValue("6");
/* 2001 */                 int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2002 */                 if (_jspx_eval_html_005foption_005f4 != 0) {
/* 2003 */                   if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2004 */                     out = _jspx_page_context.pushBody();
/* 2005 */                     _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 2006 */                     _jspx_th_html_005foption_005f4.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2009 */                     out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.ec2instancesinmonitorgroup.text"));
/* 2010 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 2011 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2014 */                   if (_jspx_eval_html_005foption_005f4 != 1) {
/* 2015 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2018 */                 if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2019 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                 }
/*      */                 
/* 2022 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 2023 */                 out.write("\n\t\t\t  \t");
/*      */                 
/* 2025 */                 OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2026 */                 _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 2027 */                 _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f1);
/*      */                 
/* 2029 */                 _jspx_th_html_005foption_005f5.setValue("7");
/* 2030 */                 int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 2031 */                 if (_jspx_eval_html_005foption_005f5 != 0) {
/* 2032 */                   if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2033 */                     out = _jspx_page_context.pushBody();
/* 2034 */                     _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 2035 */                     _jspx_th_html_005foption_005f5.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2038 */                     out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.specificec2instance.text"));
/* 2039 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 2040 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2043 */                   if (_jspx_eval_html_005foption_005f5 != 1) {
/* 2044 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2047 */                 if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 2048 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                 }
/*      */                 
/* 2051 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 2052 */                 out.write("\n\n\t\t\t\t");
/* 2053 */                 int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 2054 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 2057 */               if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 2058 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 2061 */             if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 2062 */               this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */             }
/*      */             
/* 2065 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 2066 */             out.write("\n\t\t\t</td>\n\t\t\t</tr>\n\n");
/*      */           }
/* 2068 */           out.write(10);
/* 2069 */           out.write(10);
/*      */           
/* 2071 */           com.adventnet.appmanager.struts.form.AMActionForm fm = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/*      */           
/* 2073 */           out.write("\n\n\n<tr id=\"mg\" style=\"display:none\">\n\n\n\t\t\t<td width=\"25%\" class=\"bsDescriptionText\" align=\"left\">\n\n\t\t\t ");
/* 2074 */           out.print(FormatUtil.getString("am.reporttab.selectmg.text"));
/* 2075 */           out.write(":&nbsp;\n\n\t\t\t<select name=\"selectedMG\"  class=\"formtext\" style=\"width:35%\">\n\t\t\t\t ");
/*      */           
/* 2077 */           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2078 */           _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2079 */           _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2081 */           _jspx_th_logic_005fnotEmpty_005f0.setName("applications");
/* 2082 */           int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2083 */           if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */             for (;;) {
/* 2085 */               out.write("\n\t\t\t\t     ");
/*      */               
/* 2087 */               org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
/* 2088 */               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2089 */               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */               
/* 2091 */               _jspx_th_logic_005fiterate_005f0.setName("applications");
/*      */               
/* 2093 */               _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */               
/* 2095 */               _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */               
/* 2097 */               _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2098 */               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2099 */               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2100 */                 java.util.ArrayList row = null;
/* 2101 */                 Integer j = null;
/* 2102 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2103 */                   out = _jspx_page_context.pushBody();
/* 2104 */                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2105 */                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                 }
/* 2107 */                 row = (java.util.ArrayList)_jspx_page_context.findAttribute("row");
/* 2108 */                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                 for (;;) {
/* 2110 */                   out.write("\n\t\t\t\t              ");
/*      */                   
/* 2112 */                   String selected = "";
/* 2113 */                   String currentmg = (String)row.get(1);
/* 2114 */                   String selectmg = fm.getSelectedMG();
/* 2115 */                   if ((selectmg != null) && (selectmg.equals(currentmg)))
/*      */                   {
/* 2117 */                     selected = "selected=\"selected\"";
/*      */                   }
/*      */                   
/* 2120 */                   out.write("\n\t\t\t\t \t      <option value=\"");
/* 2121 */                   out.print((String)row.get(1));
/* 2122 */                   out.write(34);
/* 2123 */                   out.write(32);
/* 2124 */                   out.print(selected);
/* 2125 */                   out.write(62);
/* 2126 */                   out.print(row.get(0));
/* 2127 */                   out.write("</option>\n\t\t\t\t     ");
/* 2128 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2129 */                   row = (java.util.ArrayList)_jspx_page_context.findAttribute("row");
/* 2130 */                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 2131 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2134 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2135 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2138 */               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2139 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */               }
/*      */               
/* 2142 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2143 */               out.write("\n\t\t\t\t ");
/* 2144 */               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2145 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2149 */           if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2150 */             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */           }
/*      */           
/* 2153 */           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2154 */           out.write("\n      \t     </select>\n\n\n\t\t\t   </td>\n\t</tr>\n\n\t");
/* 2155 */           if (monitorType.equals("jre")) {
/* 2156 */             out.write("\n\t<tr id=\"host\" style=\"display:none\">\n\t\t\t<td width=\"25%\" class=\"bsDescriptionText\" align=\"left\">");
/* 2157 */             out.print(FormatUtil.getString("am.javaruntime.action.selecthost"));
/* 2158 */             out.write(":&nbsp;\n\t\t\t   ");
/* 2159 */             if (_jspx_meth_logic_005fnotEmpty_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2161 */             out.write("\n\n\n\t\t\t\t");
/*      */             
/* 2163 */             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2164 */             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2165 */             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 2167 */             _jspx_th_logic_005fempty_005f0.setName("hostlist");
/* 2168 */             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2169 */             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */               for (;;) {
/* 2171 */                 out.write("\n\t\t\t\t\t <input type=\"hidden\" name=\"selectedhost\" value=\"\">\n\t\t\t\t\t ");
/* 2172 */                 out.print(FormatUtil.getString("am.javaruntime.action.nohost"));
/* 2173 */                 out.write("\n\t\t\t\t");
/* 2174 */                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2175 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2179 */             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2180 */               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */             }
/*      */             
/* 2183 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2184 */             out.write("\n\t\t\t   </td>\n\n\t</tr>\n\n\t<tr id=\"jre\" style=\"display:none\">\n\t\t\t<td width=\"25%\" class=\"bsDescriptionText\" align=\"left\">");
/* 2185 */             out.print(FormatUtil.getString("am.javaruntime.action.selectjre"));
/* 2186 */             out.write(":&nbsp;\n\t\t\t\t");
/* 2187 */             if (_jspx_meth_logic_005fnotEmpty_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2189 */             out.write("\n\n\t\t\t\t");
/*      */             
/* 2191 */             EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2192 */             _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 2193 */             _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 2195 */             _jspx_th_logic_005fempty_005f1.setName("jrelist");
/* 2196 */             int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 2197 */             if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */               for (;;) {
/* 2199 */                 out.write("\n\t\t\t\t<input type=\"hidden\" name=\"selectedjre\" value=\"\">\n\t\t\t\t\t");
/* 2200 */                 out.print(FormatUtil.getString("am.javaruntime.action.nojre"));
/* 2201 */                 out.write("\n\t\t\t\t");
/* 2202 */                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 2203 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2207 */             if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 2208 */               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */             }
/*      */             
/* 2211 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 2212 */             out.write("\n\n\t\t\t   </td>\n\n\t</tr>\n\t");
/*      */           } else {
/* 2214 */             out.write("\n\t<tr id=\"ec2instt\" style=\"display:none\">\n\t\t\t\t<td width=\"25%\" class=\"bsDescriptionText\" align=\"left\">");
/* 2215 */             out.print(FormatUtil.getString("am.ec2.form.selectec2inst.text"));
/* 2216 */             out.write(":&nbsp;\n\t\t\t\t\t");
/* 2217 */             if (_jspx_meth_logic_005fnotEmpty_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2219 */             out.write("\n\n\t\t\t\t\t");
/*      */             
/* 2221 */             EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2222 */             _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/* 2223 */             _jspx_th_logic_005fempty_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 2225 */             _jspx_th_logic_005fempty_005f2.setName("ec2Instance");
/* 2226 */             int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/* 2227 */             if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */               for (;;) {
/* 2229 */                 out.write("\n\t\t\t\t\t<input type=\"hidden\" name=\"selectedjre\" value=\"\">\n\t\t\t\t\t\t");
/* 2230 */                 out.print(FormatUtil.getString("am.ec2.form.noec2instance.text"));
/* 2231 */                 out.write("\n\t\t\t\t\t");
/* 2232 */                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/* 2233 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2237 */             if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/* 2238 */               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2); return;
/*      */             }
/*      */             
/* 2241 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 2242 */             out.write("\n\n\t\t\t\t   </td>\n\n\t</tr>\n\t");
/*      */           }
/* 2244 */           out.write("\n\t</table>\n\t</td>\n\t</tr>\n\t<tr>\n\t  <td class=\"bodytext label-align\" width=\"25%\">");
/* 2245 */           out.print(FormatUtil.getString("am.ec2.form.notifyaction.text"));
/* 2246 */           out.write("</td>\n\t  <td class=\"bodytext\" colspan=\"2\" width=\"75%\" valign=\"middle\" >\n\t\t\t\t\t");
/* 2247 */           if (_jspx_meth_html_005fselect_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2249 */           out.write("\n\n       &nbsp;&nbsp;&nbsp;<a href='javascript:callAction()' class='staticlinks'>");
/* 2250 */           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliverynewaction.text"));
/* 2251 */           out.write(" </a>\n\t </td>\n\n\t</tr>\n\t<tr>\n\t<td class=\"bodytext\" width=\"25%\"></td>\n\t <td class=\"bodytext\"  width=\"75%\" valign=\"middle\"  align=\"left\">\n\t   <div id='takeaction' style=\"display:none;\">\n\t      <table width='100%' cellpadding=\"0\" cellspacing=\"0\"  border=\"0\"><tr><td class='bodytext' nowrap> ");
/* 2252 */           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliveryemailid.text"));
/* 2253 */           out.write(32);
/* 2254 */           if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2256 */           out.write(" <input name=\"save\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"javascript:getAction();\" value=\"");
/* 2257 */           out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 2258 */           out.write("\">\n\t         <input name=\"cancel\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 2259 */           out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2260 */           out.write("\" onclick='javascript:removeAction()'> </td></tr>\n\t\t  </table>\n\t\t</div>\n      </td>\n</tr>\n</table>\n\n\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n\t\t\t<td width=\"25%\" class=\"tablebottom\" style=\"height:30px; color:#ff0000; font-size:11px;\">* ");
/* 2261 */           out.print(FormatUtil.getString("am.webclient.newaction.trapfieldsnote"));
/* 2262 */           out.write("</td>\n            <td width=\"75%\"  class=\"tablebottom\" align=\"left\">\n\n  \t   ");
/*      */           
/* 2264 */           org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_005fequal_005f0 = (org.apache.struts.taglib.logic.EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(org.apache.struts.taglib.logic.EqualTag.class);
/* 2265 */           _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/* 2266 */           _jspx_th_logic_005fequal_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2268 */           _jspx_th_logic_005fequal_005f0.setName("AMActionForm");
/*      */           
/* 2270 */           _jspx_th_logic_005fequal_005f0.setProperty("method");
/*      */           
/* 2272 */           _jspx_th_logic_005fequal_005f0.setValue("createThreadDumpAction");
/* 2273 */           int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/* 2274 */           if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */             for (;;) {
/* 2276 */               out.write("\n\n\t\t\t");
/*      */               
/* 2278 */               if (isInvokedFromPopup)
/*      */               {
/*      */ 
/* 2281 */                 out.write("\n\t\t\t\t<input name=\"popup\" type=\"hidden\" value=\"true\">\n\t\t\t\t<input name=\"resourceid\" type=\"hidden\" value=\"");
/* 2282 */                 out.print(request.getParameter("resourceid"));
/* 2283 */                 out.write("\">\n\t\t\t\t<input name=\"attributeid\" type=\"hidden\" value=\"");
/* 2284 */                 out.print(request.getParameter("attributeid"));
/* 2285 */                 out.write("\">\n\t\t\t\t<input name=\"severity\" type=\"hidden\" value=\"");
/* 2286 */                 out.print(request.getParameter("severity"));
/* 2287 */                 out.write("\">\n\t\t\t");
/*      */                 
/* 2289 */                 if (wiz != null)
/*      */                 {
/*      */ 
/* 2292 */                   out.write("\n\t\t\t\t<input name=\"wiz\"type=\"hidden\" value=\"");
/* 2293 */                   out.print(wiz);
/* 2294 */                   out.write("\">\n\t\t\t");
/*      */                 }
/*      */               }
/*      */               
/*      */ 
/* 2299 */               out.write("<input name=\"monitorType\" type=\"hidden\" value=\"");
/* 2300 */               out.print(request.getParameter("monitorType"));
/* 2301 */               out.write("\">\n\t               <input name=\"button\" value=\"");
/* 2302 */               out.print(FormatUtil.getString("am.webclient.emailaction.button.text"));
/* 2303 */               out.write("\" type=\"button\" class=\"buttons btn_highlt\"  onClick=\"javascript:validateAndSubmit('");
/* 2304 */               out.print(monitorType);
/* 2305 */               out.write("')\">\n\t   ");
/* 2306 */               int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/* 2307 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2311 */           if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/* 2312 */             this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0); return;
/*      */           }
/*      */           
/* 2315 */           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 2316 */           out.write("\n\n       ");
/*      */           
/* 2318 */           org.apache.struts.taglib.logic.NotEqualTag _jspx_th_logic_005fnotEqual_005f0 = (org.apache.struts.taglib.logic.NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(org.apache.struts.taglib.logic.NotEqualTag.class);
/* 2319 */           _jspx_th_logic_005fnotEqual_005f0.setPageContext(_jspx_page_context);
/* 2320 */           _jspx_th_logic_005fnotEqual_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2322 */           _jspx_th_logic_005fnotEqual_005f0.setName("AMActionForm");
/*      */           
/* 2324 */           _jspx_th_logic_005fnotEqual_005f0.setProperty("method");
/*      */           
/* 2326 */           _jspx_th_logic_005fnotEqual_005f0.setValue("createThreadDumpAction");
/* 2327 */           int _jspx_eval_logic_005fnotEqual_005f0 = _jspx_th_logic_005fnotEqual_005f0.doStartTag();
/* 2328 */           if (_jspx_eval_logic_005fnotEqual_005f0 != 0) {
/*      */             for (;;) {
/* 2330 */               out.write("\n       \t\t\t\t<input name=\"monitorType\" type=\"hidden\" value=\"");
/* 2331 */               out.print(request.getParameter("monitorType"));
/* 2332 */               out.write("\">\n\t  \t           <input name=\"button\" value=\"");
/* 2333 */               out.print(FormatUtil.getString("am.webclient.newaction.updateaction"));
/* 2334 */               out.write("\" type=\"button\" class=\"buttons btn_highlt\"  onClick=\"javascript:validateAndSubmit('");
/* 2335 */               out.print(monitorType);
/* 2336 */               out.write("')\">\n\t   ");
/* 2337 */               int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f0.doAfterBody();
/* 2338 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2342 */           if (_jspx_th_logic_005fnotEqual_005f0.doEndTag() == 5) {
/* 2343 */             this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0); return;
/*      */           }
/*      */           
/* 2346 */           this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/* 2347 */           out.write("\n\n            <input name=\"button1\" type=\"button\" class=\"buttons btn_reset\" value=\"");
/* 2348 */           out.print(FormatUtil.getString("am.webclient.newaction.restoredefaults"));
/* 2349 */           out.write("\" onClick=\"javascript:restvalue()\">\n\n      &nbsp;<input name=\"button2\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 2350 */           out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2351 */           out.write("\" onClick=\"javascript:history.back()\">\n\n\t</td>\n          </tr>\n        </table>\n  </td>\n  \n   <td width=\"30%\" valign=\"top\">\n   ");
/* 2352 */           StringBuffer helpCardContent = new StringBuffer();
/* 2353 */           if (monitorType.equals("jre")) {
/* 2354 */             helpCardContent.append(FormatUtil.getString("am.javaruntime.action.help"));
/*      */           } else {
/* 2356 */             helpCardContent.append(FormatUtil.getString("am.ec2instance.action.help"));
/*      */           }
/*      */           
/* 2359 */           out.write("\n        \t");
/* 2360 */           org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(helpCardContent.toString()), request.getCharacterEncoding()), out, false);
/* 2361 */           out.write("\n\t\t</td>\n  </tr>\n  </table>\n   \n\t\t</td>\n        </tr>\n        </table>\n        ");
/* 2362 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2363 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2367 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2368 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else {
/* 2371 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2372 */         out.write("\n      ");
/* 2373 */         if (monitorType.equalsIgnoreCase("jre")) {
/* 2374 */           out.write("\n        <script>\n        changetitle()\n \t\t</script>\n        ");
/*      */         }
/* 2376 */         out.write("\n<script>\n getResourceForSelectionType()\n </script>\n");
/*      */       }
/* 2378 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2379 */         out = _jspx_out;
/* 2380 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2381 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2382 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2385 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2391 */     PageContext pageContext = _jspx_page_context;
/* 2392 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2394 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2395 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2396 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2398 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2400 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2401 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2402 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2403 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2404 */       return true;
/*      */     }
/* 2406 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2412 */     PageContext pageContext = _jspx_page_context;
/* 2413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2415 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2416 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2417 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 2419 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2420 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2421 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2423 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t\t");
/* 2424 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2425 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2429 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2430 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2431 */       return true;
/*      */     }
/* 2433 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2434 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2439 */     PageContext pageContext = _jspx_page_context;
/* 2440 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2442 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2443 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2444 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2446 */     _jspx_th_c_005fif_005f0.setTest("${globalconfig['mailserverconfigured'] != 'true'}");
/* 2447 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2448 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 2450 */         out.write("\n\t\t\tmyOnLoad();\n\t\t");
/* 2451 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2452 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2456 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2457 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2458 */       return true;
/*      */     }
/* 2460 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2466 */     PageContext pageContext = _jspx_page_context;
/* 2467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2469 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2470 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2471 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2473 */     _jspx_th_c_005fif_005f1.setTest("${globalconfig['mailserverconfigured'] != 'true' && param.checkForMailSetting eq 'true'}");
/* 2474 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2475 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2477 */         out.write("\n\t\t\t\tmyOnLoad();\n\t\t\t");
/* 2478 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2479 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2483 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2484 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2485 */       return true;
/*      */     }
/* 2487 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2488 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2493 */     PageContext pageContext = _jspx_page_context;
/* 2494 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2496 */     org.apache.taglibs.standard.tag.common.core.CatchTag _jspx_th_c_005fcatch_005f0 = (org.apache.taglibs.standard.tag.common.core.CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(org.apache.taglibs.standard.tag.common.core.CatchTag.class);
/* 2497 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 2498 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 2500 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 2501 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 2503 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 2504 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 2506 */           out.write(" \n      ");
/* 2507 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 2508 */             return true;
/* 2509 */           out.write(32);
/* 2510 */           out.write(10);
/* 2511 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 2512 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2516 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 2517 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2520 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 2521 */         out = _jspx_page_context.popBody(); }
/* 2522 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 2524 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 2525 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 2527 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 2532 */     PageContext pageContext = _jspx_page_context;
/* 2533 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2535 */     org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag.class);
/* 2536 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 2537 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 2539 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 2541 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 2542 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 2543 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 2544 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2545 */       return true;
/*      */     }
/* 2547 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2548 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2553 */     PageContext pageContext = _jspx_page_context;
/* 2554 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2556 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 2557 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 2558 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2560 */     _jspx_th_c_005fset_005f5.setVar("wiz");
/*      */     
/* 2562 */     _jspx_th_c_005fset_005f5.setValue("${param.wiz}");
/*      */     
/* 2564 */     _jspx_th_c_005fset_005f5.setScope("request");
/* 2565 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 2566 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 2567 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 2568 */       return true;
/*      */     }
/* 2570 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 2571 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2576 */     PageContext pageContext = _jspx_page_context;
/* 2577 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2579 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2580 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2581 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2583 */     _jspx_th_c_005fout_005f1.setValue("${wizimage}");
/*      */     
/* 2585 */     _jspx_th_c_005fout_005f1.setEscapeXml("false");
/* 2586 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2587 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2588 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2589 */       return true;
/*      */     }
/* 2591 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2592 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2597 */     PageContext pageContext = _jspx_page_context;
/* 2598 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2600 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2601 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2602 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2604 */     _jspx_th_c_005fif_005f9.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 2605 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2606 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 2608 */         out.write("\n    <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 2609 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 2610 */           return true;
/* 2611 */         out.write("&wiz=true\">\n    ");
/* 2612 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2613 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2617 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2618 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2619 */       return true;
/*      */     }
/* 2621 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2622 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2627 */     PageContext pageContext = _jspx_page_context;
/* 2628 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2630 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2631 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2632 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 2634 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 2635 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2636 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2637 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2638 */       return true;
/*      */     }
/* 2640 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2646 */     PageContext pageContext = _jspx_page_context;
/* 2647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2649 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2650 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2651 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2653 */     _jspx_th_c_005fout_005f3.setValue("${wizimage}");
/*      */     
/* 2655 */     _jspx_th_c_005fout_005f3.setEscapeXml("false");
/* 2656 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2657 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2658 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2659 */       return true;
/*      */     }
/* 2661 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2662 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2667 */     PageContext pageContext = _jspx_page_context;
/* 2668 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2670 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2671 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2672 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2674 */     _jspx_th_c_005fif_005f10.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 2675 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2676 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 2678 */         out.write("\n    \t</a>\n    \t");
/* 2679 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2680 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2684 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2685 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2686 */       return true;
/*      */     }
/* 2688 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2689 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2694 */     PageContext pageContext = _jspx_page_context;
/* 2695 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2697 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2698 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2699 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2701 */     _jspx_th_c_005fif_005f11.setTest("${wizimage=='/images/new_high.gif'}");
/* 2702 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2703 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 2705 */         out.write("\n       <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 2706 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 2707 */           return true;
/* 2708 */         out.write("&wiz=true\">\n       ");
/* 2709 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2710 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2714 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2715 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2716 */       return true;
/*      */     }
/* 2718 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2719 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2724 */     PageContext pageContext = _jspx_page_context;
/* 2725 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2727 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2728 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2729 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 2731 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 2732 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2733 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2734 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2735 */       return true;
/*      */     }
/* 2737 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2738 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2743 */     PageContext pageContext = _jspx_page_context;
/* 2744 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2746 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2747 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2748 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2750 */     _jspx_th_c_005fout_005f5.setValue("${wizimage}");
/*      */     
/* 2752 */     _jspx_th_c_005fout_005f5.setEscapeXml("false");
/* 2753 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2754 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2755 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2756 */       return true;
/*      */     }
/* 2758 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2759 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2764 */     PageContext pageContext = _jspx_page_context;
/* 2765 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2767 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2768 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2769 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2771 */     _jspx_th_c_005fif_005f12.setTest("${wizimage=='/images/new_high.gif'}");
/* 2772 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2773 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 2775 */         out.write("\n       \t</a>\n       \t");
/* 2776 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 2777 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2781 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 2782 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2783 */       return true;
/*      */     }
/* 2785 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2786 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2791 */     PageContext pageContext = _jspx_page_context;
/* 2792 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2794 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2795 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 2796 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2798 */     _jspx_th_c_005fif_005f15.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 2799 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 2800 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 2802 */         out.write("\t\n    <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 2803 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 2804 */           return true;
/* 2805 */         out.write("&wiz=true\">\n ");
/* 2806 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 2807 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2811 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 2812 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 2813 */       return true;
/*      */     }
/* 2815 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 2816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2821 */     PageContext pageContext = _jspx_page_context;
/* 2822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2824 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2825 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2826 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 2828 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 2829 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2830 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2831 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2832 */       return true;
/*      */     }
/* 2834 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2835 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2840 */     PageContext pageContext = _jspx_page_context;
/* 2841 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2843 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2844 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2845 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2847 */     _jspx_th_c_005fout_005f7.setValue("${wizimage}");
/*      */     
/* 2849 */     _jspx_th_c_005fout_005f7.setEscapeXml("false");
/* 2850 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2851 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2852 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2853 */       return true;
/*      */     }
/* 2855 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2856 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2861 */     PageContext pageContext = _jspx_page_context;
/* 2862 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2864 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2865 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 2866 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2868 */     _jspx_th_c_005fif_005f16.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 2869 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 2870 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 2872 */         out.write("\t    \n    </a>\n ");
/* 2873 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 2874 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2878 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 2879 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2880 */       return true;
/*      */     }
/* 2882 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2883 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2888 */     PageContext pageContext = _jspx_page_context;
/* 2889 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2891 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2892 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2893 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 2895 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 2897 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 2898 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2899 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2900 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2901 */       return true;
/*      */     }
/* 2903 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2904 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2909 */     PageContext pageContext = _jspx_page_context;
/* 2910 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2912 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2913 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 2914 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 2916 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 2918 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 2919 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 2920 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 2921 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2922 */       return true;
/*      */     }
/* 2924 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2925 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2930 */     PageContext pageContext = _jspx_page_context;
/* 2931 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2933 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2934 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 2935 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2937 */     _jspx_th_am_005fhiddenparam_005f0.setName("haid");
/* 2938 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 2939 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 2940 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 2941 */       return true;
/*      */     }
/* 2943 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 2944 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2949 */     PageContext pageContext = _jspx_page_context;
/* 2950 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2952 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 2953 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/* 2954 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2956 */     _jspx_th_am_005fhiddenparam_005f1.setName("redirectTo");
/* 2957 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/* 2958 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/* 2959 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 2960 */       return true;
/*      */     }
/* 2962 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 2963 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2968 */     PageContext pageContext = _jspx_page_context;
/* 2969 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2971 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 2972 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 2973 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2975 */     _jspx_th_html_005fhidden_005f0.setProperty("method");
/* 2976 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 2977 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 2978 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2979 */       return true;
/*      */     }
/* 2981 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2982 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2987 */     PageContext pageContext = _jspx_page_context;
/* 2988 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2990 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 2991 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 2992 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2994 */     _jspx_th_html_005fhidden_005f1.setProperty("id");
/* 2995 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 2996 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 2997 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 2998 */       return true;
/*      */     }
/* 3000 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3001 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3006 */     PageContext pageContext = _jspx_page_context;
/* 3007 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3009 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(WriteTag.class);
/* 3010 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 3011 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 3013 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/* 3014 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 3015 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 3016 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 3017 */       return true;
/*      */     }
/* 3019 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 3020 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3025 */     PageContext pageContext = _jspx_page_context;
/* 3026 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3028 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3029 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3030 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3032 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 3034 */     _jspx_th_html_005ftext_005f0.setSize("40");
/*      */     
/* 3036 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/*      */     
/* 3038 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/* 3039 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3040 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3041 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3042 */       return true;
/*      */     }
/* 3044 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3045 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3050 */     PageContext pageContext = _jspx_page_context;
/* 3051 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3053 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3054 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 3055 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3057 */     _jspx_th_html_005fradio_005f0.setProperty("jtaskMethod");
/*      */     
/* 3059 */     _jspx_th_html_005fradio_005f0.setValue("ThreadDump");
/*      */     
/* 3061 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:changetitle();");
/* 3062 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 3063 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 3064 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3065 */       return true;
/*      */     }
/* 3067 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3068 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3073 */     PageContext pageContext = _jspx_page_context;
/* 3074 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3076 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3077 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 3078 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3080 */     _jspx_th_html_005fradio_005f1.setProperty("jtaskMethod");
/*      */     
/* 3082 */     _jspx_th_html_005fradio_005f1.setValue("HeapDump");
/*      */     
/* 3084 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:changetitle();");
/* 3085 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 3086 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 3087 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3088 */       return true;
/*      */     }
/* 3090 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3091 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3096 */     PageContext pageContext = _jspx_page_context;
/* 3097 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3099 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3100 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 3101 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3103 */     _jspx_th_html_005fradio_005f2.setProperty("jtaskMethod");
/*      */     
/* 3105 */     _jspx_th_html_005fradio_005f2.setValue("PerformGC");
/*      */     
/* 3107 */     _jspx_th_html_005fradio_005f2.setOnclick("javascript:changetitle();");
/* 3108 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 3109 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 3110 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 3111 */       return true;
/*      */     }
/* 3113 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 3114 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3119 */     PageContext pageContext = _jspx_page_context;
/* 3120 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3122 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 3123 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3124 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3126 */     _jspx_th_html_005ftext_005f1.setProperty("tdcount");
/*      */     
/* 3128 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext msmall");
/*      */     
/* 3130 */     _jspx_th_html_005ftext_005f1.setSize("5");
/*      */     
/* 3132 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:changeDelay();");
/* 3133 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3134 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3135 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3136 */       return true;
/*      */     }
/* 3138 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3139 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3144 */     PageContext pageContext = _jspx_page_context;
/* 3145 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3147 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3148 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 3149 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3151 */     _jspx_th_html_005ftext_005f2.setProperty("tddelay");
/*      */     
/* 3153 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext msmall");
/*      */     
/* 3155 */     _jspx_th_html_005ftext_005f2.setSize("5");
/* 3156 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 3157 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 3158 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3159 */       return true;
/*      */     }
/* 3161 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3167 */     PageContext pageContext = _jspx_page_context;
/* 3168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3170 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3171 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 3172 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3174 */     _jspx_th_html_005fradio_005f3.setProperty("jtaskMethod");
/*      */     
/* 3176 */     _jspx_th_html_005fradio_005f3.setValue("Start");
/* 3177 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 3178 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 3179 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 3180 */       return true;
/*      */     }
/* 3182 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 3183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3188 */     PageContext pageContext = _jspx_page_context;
/* 3189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3191 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3192 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 3193 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3195 */     _jspx_th_html_005fradio_005f4.setProperty("jtaskMethod");
/*      */     
/* 3197 */     _jspx_th_html_005fradio_005f4.setValue("Stop");
/* 3198 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 3199 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 3200 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 3201 */       return true;
/*      */     }
/* 3203 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 3204 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3209 */     PageContext pageContext = _jspx_page_context;
/* 3210 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3212 */     RadioTag _jspx_th_html_005fradio_005f5 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3213 */     _jspx_th_html_005fradio_005f5.setPageContext(_jspx_page_context);
/* 3214 */     _jspx_th_html_005fradio_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3216 */     _jspx_th_html_005fradio_005f5.setProperty("jtaskMethod");
/*      */     
/* 3218 */     _jspx_th_html_005fradio_005f5.setValue("Restart");
/* 3219 */     int _jspx_eval_html_005fradio_005f5 = _jspx_th_html_005fradio_005f5.doStartTag();
/* 3220 */     if (_jspx_th_html_005fradio_005f5.doEndTag() == 5) {
/* 3221 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 3222 */       return true;
/*      */     }
/* 3224 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 3225 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3230 */     PageContext pageContext = _jspx_page_context;
/* 3231 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3233 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3234 */     _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3235 */     _jspx_th_logic_005fnotEmpty_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3237 */     _jspx_th_logic_005fnotEmpty_005f1.setName("hostlist");
/* 3238 */     int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3239 */     if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */       for (;;) {
/* 3241 */         out.write("\n\t\t\t\t\t\t");
/* 3242 */         if (_jspx_meth_html_005fselect_005f2(_jspx_th_logic_005fnotEmpty_005f1, _jspx_page_context))
/* 3243 */           return true;
/* 3244 */         out.write("\n\t\t\t\t ");
/* 3245 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3246 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3250 */     if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3251 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3252 */       return true;
/*      */     }
/* 3254 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_logic_005fnotEmpty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3260 */     PageContext pageContext = _jspx_page_context;
/* 3261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3263 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3264 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 3265 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f1);
/*      */     
/* 3267 */     _jspx_th_html_005fselect_005f2.setProperty("selectedhost");
/*      */     
/* 3269 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext default");
/* 3270 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 3271 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 3272 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3273 */         out = _jspx_page_context.pushBody();
/* 3274 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 3275 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3278 */         out.write("\n\t\t\t\t\t\t");
/* 3279 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 3280 */           return true;
/* 3281 */         out.write("\n\t\t\t\t\t    ");
/* 3282 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 3283 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3286 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3287 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3290 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 3291 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 3292 */       return true;
/*      */     }
/* 3294 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 3295 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3300 */     PageContext pageContext = _jspx_page_context;
/* 3301 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3303 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3304 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 3305 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 3307 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("hostlist");
/* 3308 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 3309 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 3310 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3311 */       return true;
/*      */     }
/* 3313 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3314 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3319 */     PageContext pageContext = _jspx_page_context;
/* 3320 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3322 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3323 */     _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 3324 */     _jspx_th_logic_005fnotEmpty_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3326 */     _jspx_th_logic_005fnotEmpty_005f2.setName("jrelist");
/* 3327 */     int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 3328 */     if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */       for (;;) {
/* 3330 */         out.write("\n\t\t\t\t\t");
/* 3331 */         if (_jspx_meth_html_005fselect_005f3(_jspx_th_logic_005fnotEmpty_005f2, _jspx_page_context))
/* 3332 */           return true;
/* 3333 */         out.write("\n\t\t\t    ");
/* 3334 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 3335 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3339 */     if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 3340 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3341 */       return true;
/*      */     }
/* 3343 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3344 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_logic_005fnotEmpty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3349 */     PageContext pageContext = _jspx_page_context;
/* 3350 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3352 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 3353 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 3354 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f2);
/*      */     
/* 3356 */     _jspx_th_html_005fselect_005f3.setProperty("selectedjre");
/*      */     
/* 3358 */     _jspx_th_html_005fselect_005f3.setStyleClass("formtext");
/*      */     
/* 3360 */     _jspx_th_html_005fselect_005f3.setStyle("width:25%");
/* 3361 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 3362 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 3363 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3364 */         out = _jspx_page_context.pushBody();
/* 3365 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 3366 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3369 */         out.write("\n\t\t\t\t\t");
/* 3370 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 3371 */           return true;
/* 3372 */         out.write("\n\t\t\t\t\t");
/* 3373 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 3374 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3377 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3378 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3381 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 3382 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 3383 */       return true;
/*      */     }
/* 3385 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 3386 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3391 */     PageContext pageContext = _jspx_page_context;
/* 3392 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3394 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3395 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 3396 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 3398 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("jrelist");
/* 3399 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 3400 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 3401 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3402 */       return true;
/*      */     }
/* 3404 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3410 */     PageContext pageContext = _jspx_page_context;
/* 3411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3413 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3414 */     _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 3415 */     _jspx_th_logic_005fnotEmpty_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3417 */     _jspx_th_logic_005fnotEmpty_005f3.setName("ec2Instance");
/* 3418 */     int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 3419 */     if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */       for (;;) {
/* 3421 */         out.write("\n\t\t\t\t\t\t");
/* 3422 */         if (_jspx_meth_html_005fselect_005f4(_jspx_th_logic_005fnotEmpty_005f3, _jspx_page_context))
/* 3423 */           return true;
/* 3424 */         out.write("\n\t\t\t\t    \t");
/* 3425 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 3426 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3430 */     if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 3431 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 3432 */       return true;
/*      */     }
/* 3434 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 3435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_logic_005fnotEmpty_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3440 */     PageContext pageContext = _jspx_page_context;
/* 3441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3443 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3444 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 3445 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f3);
/*      */     
/* 3447 */     _jspx_th_html_005fselect_005f4.setProperty("selectedjre");
/*      */     
/* 3449 */     _jspx_th_html_005fselect_005f4.setStyleClass("formtext default");
/* 3450 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 3451 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 3452 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 3453 */         out = _jspx_page_context.pushBody();
/* 3454 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 3455 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3458 */         out.write("\n\t\t\t\t\t\t");
/* 3459 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 3460 */           return true;
/* 3461 */         out.write("\n\t\t\t\t\t\t");
/* 3462 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 3463 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3466 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 3467 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3470 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 3471 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 3472 */       return true;
/*      */     }
/* 3474 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 3475 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3480 */     PageContext pageContext = _jspx_page_context;
/* 3481 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3483 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3484 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 3485 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 3487 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("ec2Instance");
/* 3488 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 3489 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 3490 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3491 */       return true;
/*      */     }
/* 3493 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3494 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3499 */     PageContext pageContext = _jspx_page_context;
/* 3500 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3502 */     SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 3503 */     _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 3504 */     _jspx_th_html_005fselect_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3506 */     _jspx_th_html_005fselect_005f5.setProperty("sendmail");
/*      */     
/* 3508 */     _jspx_th_html_005fselect_005f5.setStyleClass("formtext default");
/*      */     
/* 3510 */     _jspx_th_html_005fselect_005f5.setStyle("width:242px;");
/* 3511 */     int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 3512 */     if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 3513 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 3514 */         out = _jspx_page_context.pushBody();
/* 3515 */         _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 3516 */         _jspx_th_html_005fselect_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3519 */         out.write("\n\t\t\t\t\t\t");
/* 3520 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/* 3521 */           return true;
/* 3522 */         out.write("\n\t\t\t\t\t");
/* 3523 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 3524 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3527 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 3528 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3531 */     if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 3532 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 3533 */       return true;
/*      */     }
/* 3535 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 3536 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3541 */     PageContext pageContext = _jspx_page_context;
/* 3542 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3544 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3545 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 3546 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 3548 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("maillist");
/* 3549 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 3550 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 3551 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 3552 */       return true;
/*      */     }
/* 3554 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 3555 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3560 */     PageContext pageContext = _jspx_page_context;
/* 3561 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3563 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3564 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 3565 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3567 */     _jspx_th_html_005ftext_005f3.setProperty("priority");
/*      */     
/* 3569 */     _jspx_th_html_005ftext_005f3.setSize("30");
/*      */     
/* 3571 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 3573 */     _jspx_th_html_005ftext_005f3.setMaxlength("50");
/* 3574 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 3575 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 3576 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3577 */       return true;
/*      */     }
/* 3579 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 3580 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ThreadDumpActionForm_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */