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
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.EqualTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class VMActionForm_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   80 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   84 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   85 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   86 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   94 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   95 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   96 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   97 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   98 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   99 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  100 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  101 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  102 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  103 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  104 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  105 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  106 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  107 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  108 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  109 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  110 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  111 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  112 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  113 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  114 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  115 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  116 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  117 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  118 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  122 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  123 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  124 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  125 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  126 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  127 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  128 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  129 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  130 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/*  131 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/*  132 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*  133 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/*  134 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  135 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  136 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  137 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  138 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/*  139 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*  140 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
/*  141 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*  142 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*  143 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.release();
/*  144 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/*  145 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/*  146 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  147 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.release();
/*  148 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  149 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  150 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/*  151 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  152 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*  153 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.release();
/*  154 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  161 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  164 */     JspWriter out = null;
/*  165 */     Object page = this;
/*  166 */     JspWriter _jspx_out = null;
/*  167 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  171 */       response.setContentType("text/html;charset=UTF-8");
/*  172 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  174 */       _jspx_page_context = pageContext;
/*  175 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  176 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  177 */       session = pageContext.getSession();
/*  178 */       out = pageContext.getOut();
/*  179 */       _jspx_out = out;
/*      */       
/*  181 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/*  182 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  184 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<script language=\"JavaScript1.2\" src=\"/template/appmanager.js\"></script>\n\n\n");
/*      */       
/*  186 */       org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/*  187 */       token.saveToken(request);
/*      */       
/*  189 */       out.write(10);
/*      */       
/*  191 */       boolean isInvokedFromPopup = request.getParameter("popup") != null;
/*  192 */       boolean isContainerAction = request.getAttribute("isContainerAction") != null ? ((Boolean)request.getAttribute("isContainerAction")).booleanValue() : false;
/*  193 */       String mode = request.getParameter("mode");
/*  194 */       String wiz = request.getParameter("wiz");
/*  195 */       int actionID = -1;
/*  196 */       if (request.getParameter("actionID") != null)
/*      */       {
/*  198 */         actionID = Integer.parseInt(request.getParameter("actionID"));
/*      */       }
/*  200 */       com.adventnet.appmanager.servlets.AMInitializationServlet osName = new com.adventnet.appmanager.servlets.AMInitializationServlet();
/*      */       
/*  202 */       out.write("\n\n\n<script>\n  function callAction()\n  {\n\t showDiv(\"takeaction\");\n  }\n  function removeAction()\n  {\n     hideDiv(\"takeaction\");\n  }\n\n    function getAction()\n  {\n    if(document.AMActionForm.displayname.value=='')\n\t{\n     alert(\"");
/*  203 */       out.print(FormatUtil.getString("am.webclient.common.validatename.text"));
/*  204 */       out.write("\");\n     document.AMActionForm.displayname.focus();\n     return false;\n    }\n\n   if(document.AMActionForm.priority.value=='')\n   {\n     alert(\"");
/*  205 */       out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.jsalertforscheduleemail.text"));
/*  206 */       out.write("\");\n     document.AMActionForm.priority.focus();\n     return false;\n   }\n   else\n   {\n    var a=document.AMActionForm.priority.value;\n    var b=encodeURIComponent(document.AMActionForm.displayname.value);\n\tvar msg=\"Java\"; //NO I18N\n\t\t  \t\tif(document.AMActionForm.jtaskMethod[0].checked==true){\n               msg='");
/*  207 */       out.print(FormatUtil.getString("am.vm.action.startvm"));
/*  208 */       out.write("';\n\t\t}else if(document.AMActionForm.jtaskMethod[1].checked==true){\n\t\t\t\t msg='");
/*  209 */       out.print(FormatUtil.getString("am.vm.action.stopvm"));
/*  210 */       out.write("';\n\t\t}else if(document.AMActionForm.jtaskMethod[2].checked==true){\n\t\t\t     msg='");
/*  211 */       out.print(FormatUtil.getString("am.vm.action.restartvm"));
/*  212 */       out.write("';\n\t\t}\n    url=\"/JavaRuntime.do?method=sendActionDetails&emailid=\"+a+\"&emailname=\"+b+\"&message=\"+msg; //NO I18N\n\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getActionTypes;\n    http.send(null);\n   }\n\n }\n\n\n  function getActionTypes()\n  {\n    if(http.readyState == 4)\n    {\n      var result = http.responseText;\n      hideDiv(\"takeaction\");\n      var id=result;\n      var stringtokens=id.split(\",\");\n      smessage=stringtokens[0];\n      sid=stringtokens[1];\n      hideDiv(\"actionmessage\");\n      if(smessage !='null' || smessage != '')\n     {\n             hideDiv(\"actionmessage\");\n            var name=document.AMActionForm.displayname.value+\"_Action\"; //NO I18N\n            document.AMActionForm.sendmail.options[document.AMActionForm.sendmail.length] =new Option(name,sid,false,true);\n     }\n     else\n     {\n            showDiv(\"actionmessage\");\n            document.getElementById(\"actionmessage\").innerHTML=smessage;\n     }\n    }\n }\n\n//select Target JVM Control\nfunction getResourceForSelectionType()\n{\n");
/*  213 */       out.write(" var selecttionType=document.getElementsByName('logConfig')[0].value;  //NO I18N\n\tif(selecttionType=='1')\n\t\t{\n\t\t\thideRow('mg'); // NO I18N\n\t\t\thideRow('jre');// NO I18N\n\t\t\thideRow('host');// NO I18N\n\t\t\thideRow('hypervhost');// NO I18N\n\t\t\thideRow('hyperVVMs');\t\t  // NO I18N\n\t\t\t\thideRow('xenhost');//No I18N\n\t\t\t\thideRow('xenVMs');//No I18N\n\t\t}\t\t\n\t\telse if(selecttionType=='2' || selecttionType=='7' )\n\t\t{\n \t\t \tshowRow('mg');// NO I18N\n\t\t\thideRow('jre');// NO I18N\n\t\t\thideRow('host');// NO I18N\t\t\t\n\t\t\thideRow('hypervhost');// NO I18N\n\t\t\thideRow('hyperVVMs');// NO I18N\t\t\t\t  \n\t\t\thideRow('xenhost');//No I18N\n\t\t\thideRow('xenVMs');//No I18N\n\t\t\n\t\t}\n\t\telse if(selecttionType=='3' || selecttionType=='8' )\n\t\t{\n\t\t    showRow('host');// NO I18N\n\t\t\thideRow('mg');// NO I18N\n\t\t\thideRow('jre');// NO I18N\n\t\t\thideRow('hypervhost');// NO I18N\t\t\t\n\t\t\thideRow('hyperVVMs');// NO I18N\t\t  \n\t\t\t\thideRow('xenhost');//No I18N\n\t\t\t\thideRow('xenVMs');//No I18N\n\t\t}\n\t\telse if(selecttionType=='5')\n\t\t{\n\t\t\t\tshowRow('hypervhost');// NO I18N\n\t\t\t\thideRow('mg');// NO I18N\n");
/*  214 */       out.write("\t\t\t\thideRow('jre');// NO I18N\n\t\t\t\thideRow('host');// NO I18N\n\t\t\t\thideRow('hyperVVMs');// NO I18N\t\t\t \n\t\t\t\thideRow('xenhost');//No I18N\n\t\t\t\thideRow('xenVMs');//No I18N\n\t\t}\n\t\telse if(selecttionType == '6')\n\t\t{\n\t\t\t\tshowRow('xenhost');// NO I18N\n\t\t\t\thideRow('mg');// NO I18N\n\t\t\t\thideRow('jre');// NO I18N\n\t\t\t\thideRow('host');// NO I18N\n\t\t\t\thideRow('hypervhost');// NO I18N\t\t\t\n\t\t\t\thideRow('hyperVVMs');// NO I18N\t\t  \n\t\t\t\thideRow('xenVMs');//No I18N\n\t\t}\n\telse if((selecttionType=='4' && document.AMActionForm.hostType[0].checked==true) || selecttionType=='9')\n\t\t\t{\n\t\t    showRow('jre');// NO I18N\n\t\t\thideRow('mg');\t// NO I18N\t\t\n\t\t\thideRow('host');// NO I18N\n\t\t\thideRow('hypervhost');// NO I18N\n\t\t\thideRow('hyperVVMs');\t// NO I18N\t \n\t\t\t\thideRow('xenhost');//No I18N\n\t\t\t\thideRow('xenVMs');//No I18N\n\n\t}\n\telse if(selecttionType=='4' && document.AMActionForm.hostType[1].checked==true )\n\t\t{\n\t\t\thideRow('mg');// NO I18N\n\t\t\thideRow('jre');// NO I18N\n\t\t\thideRow('host');// NO I18N\n\t\t\thideRow('hypervhost');// NO I18N\n\t\t\tshowRow('hyperVVMs');\t// NO I18N\t \n");
/*  215 */       out.write("\t\t\thideRow('xenhost');//No I18N\n\t\t\thideRow('xenVMs');//No I18N\n\t}\n\telse if(selecttionType == '4' && document.AMActionForm.hostType[2].checked == true)\n\t{\n\t\t\thideRow('mg');// NO I18N\n\t\t\thideRow('jre');// NO I18N\n\t\t\thideRow('host');// NO I18N\n\t\t\thideRow('hypervhost');// NO I18N\n\t\t\thideRow('hyperVVMs');\t// NO I18N\t \n\t\t\thideRow('xenhost');//No I18N\n\t\t\tshowRow('xenVMs');//No I18N\n\t}\n\t\n }\n\n function validateAndSubmit()\n {\n\t ");
/*  216 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/*  218 */       out.write("\n\tif((document.AMActionForm.displayname.value).trim()=='')\n\t{\n\talert(\"");
/*  219 */       out.print(FormatUtil.getString("am.webclient.newaction.alertdisplayname"));
/*  220 */       out.write("\");\n\tdocument.AMActionForm.displayname.focus();\n\t return false;\n\t}\n        if(isBlackListedCharactersPresent(document.AMActionForm.displayname.value,'");
/*  221 */       out.print(FormatUtil.getString("am.webclient.specialchar.alert.displayname"));
/*  222 */       out.write("')) {\n\t\treturn false;\n    \t}\n\tif((document.AMActionForm.tddelay.value).trim()=='')\n\t{\n\n               alert(\"");
/*  223 */       out.print(FormatUtil.getString("am.vm.action.vmtimeout"));
/*  224 */       out.write("\");\n\t\tdocument.AMActionForm.tddelay.focus();\n\t\t return false;\n\t}\n\n\tif( isNaN(document.AMActionForm.tddelay.value) || ((document.AMActionForm.tddelay.value)*1) <= 0 )\n\t{\n\n               alert(\"");
/*  225 */       out.print(FormatUtil.getString("am.vm.action.vmtimeout.invalid"));
/*  226 */       out.write("\");\n\t\tdocument.AMActionForm.tddelay.focus();\n\t\t return false;\n\t}\n\n\tif(document.AMActionForm.sendmail.value=='')\n\t{\n\talert(\"");
/*  227 */       out.print(FormatUtil.getString("am.javaruntime.action.createmail"));
/*  228 */       out.write("\");\n\t return false;\n\t}\n\n\tif((document.AMActionForm.logConfig.value=='3' || document.AMActionForm.logConfig.value=='8') && document.AMActionForm.selectedhost.value=='' )\n\t{\n\t\tif(document.AMActionForm.logConfig.value=='8'){\n\t\t\talert(\"");
/*  229 */       out.print(FormatUtil.getString("am.docker.container.action.select.alert.text"));
/*  230 */       out.write("\");\n\t\t}\n\t\telse{\n\t\t\talert(\"");
/*  231 */       out.print(FormatUtil.getString("am.vm.action.select.esxhost"));
/*  232 */       out.write("\");\n\t\t}\n\t \n\t return false;\n\t}\n\tif(document.AMActionForm.logConfig.value=='5' && document.AMActionForm.selectedhypervhost.value=='')\n\t{\n\t alert(\"");
/*  233 */       out.print(FormatUtil.getString("am.vm.action.select.hypervhost"));
/*  234 */       out.write("\");\n\t return false;\n\t}\n\tif(document.AMActionForm.logConfig.value=='6' && document.AMActionForm.selectedXenHost.value=='')\n\t{\n\t alert(\"");
/*  235 */       out.print(FormatUtil.getString("am.vm.action.select.xenhost"));
/*  236 */       out.write("\");\n\t return false;\n\t}\n\t\n\tif(document.AMActionForm.logConfig.value=='9' && document.AMActionForm.selectedjre.value=='')\n\t{\n\t \n\t \talert(\"");
/*  237 */       out.print(FormatUtil.getString("am.docker.container.action.select.docker.alert.text"));
/*  238 */       out.write("\");\n\t \treturn false;\n\t \n\t}\n\tif(document.AMActionForm.logConfig.value=='4')\n\t{\n\t  //if Esx is selected in HostType without any EsX monitors configured (OR)\n\t  // HyperV is selected without any HyperVMonitors configured\n\t if( (document.AMActionForm.hostType[0].checked &&  document.AMActionForm.selectedjre.value=='') ||\n\t\t\t(document.AMActionForm.hostType[1].checked && document.AMActionForm.selectedHyperVVM.value=='') ||\n\t\t\t(document.AMActionForm.hostType[2].checked && document.AMActionForm.selectedXenVM.value=='')\n\t\t){\n\t alert(\"");
/*  239 */       out.print(FormatUtil.getString("am.vm.action.selectvm"));
/*  240 */       out.write("\");\n\t return false;\n\t }\n\t}\n\t document.AMActionForm.submit();\n\n }\n\n function removeElementsInComboBox( elementObj )\n \t{\n\t for(var i=0;i<elementObj.length;)\n\t \t{\n\t\telementObj.remove(i);\n\t \t}\n \t}\n\nfunction moveOptions( sourceCombo, destinationCombo )\n\t{\n\tfor(var i=0;i<sourceCombo.length;i++)\n \t\t{\n\t\tvar opt = document.createElement(\"option\");\n\t\tdestinationCombo.options.add( opt );\n        opt.text = sourceCombo[i].text;\n        opt.value = sourceCombo[i].value;\n\t\t}\n\t}\n\n function fillOptions( hostType )\n \t{\n\tvar comboBx = document.AMActionForm.logConfig;\n\tremoveElementsInComboBox( comboBx );\n\tif( hostType == 0)\n\t\t{\n\t\tmoveOptions(document.AMActionForm.esxHostOptions,comboBx);\n\t\t}\n\telse if( hostType == 100 )\n\t\t{\n\t\tmoveOptions(document.AMActionForm.hypervHostoptions,comboBx);\n\t\t}\n\telse if( hostType == 700)\n\t\t{\n\t\t\tmoveOptions(document.AMActionForm.xenHostOptions, comboBx);\n\t\t}\n\telse if( hostType == 850)\n\t{\n\t\tmoveOptions(document.AMActionForm.containerOptions, comboBx);\n\t}\n\t\tif(document.getElementById('jre')!=null)\n\t\t{\n");
/*  241 */       out.write("\tdocument.getElementById('jre').style.display='none';\n\t}\n\n\t");
/*  242 */       if (request.getParameter("actionID") == null)
/*      */       {
/*  244 */         out.write("\n\n\tif(document.getElementById('host')){\n \t\tdocument.getElementById('host').style.display='none';\n\t}\n\n\tif(document.getElementById('mg')){\n\t\tdocument.getElementById('mg').style.display='table-row';\n\t}\n\n\tif(document.getElementById('hypervhost')){\n\t\tdocument.getElementById('hypervhost').style.display='none';\n\t}\n\t\n\tif(document.getElementById('xenhost'))\n\t{\n\t\tdocument.getElementById('xenhost').style.display='none';\n\t}\n\t");
/*      */       } else {
/*  246 */         com.adventnet.appmanager.struts.form.AMActionForm fm1 = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/*      */         
/*  248 */         out.write("\n\t\tlogConfig= ");
/*  249 */         out.print(fm1.getLogConfig());
/*  250 */         out.write(" ;\n\t\tdocument.getElementsByName('logConfig')[0].value=logConfig;\n\t");
/*      */       }
/*  252 */       out.write("\n \t}\n\nfunction fillOptionsOnChange()\n{\n\n    var hosttype=document.AMActionForm.hostType.value;\n    var comboBx = document.AMActionForm.logConfig;\n     removeElementsInComboBox( comboBx );\n     if( document.AMActionForm.hostType[0].checked==true)\n     \t\t{\n     \t\tmoveOptions(document.AMActionForm.esxHostOptions,comboBx);\n     \t\t}\n     \telse if(document.AMActionForm.hostType[1].checked ==true)\n     \t\t{\n     \t\tmoveOptions(document.AMActionForm.hypervHostoptions,comboBx);\n     \t\t}\n\telse if(document.AMActionForm.hostType[2].checked == true)\n\t\t{\n     \t\t\tmoveOptions(document.AMActionForm.xenHostOptions,comboBx);\n\t\t}\n     \t\tif(document.getElementById('jre')!=null)\n\t\t{\n     \tdocument.getElementById('jre').style.display='none';\n     \t}\n     \tif(document.getElementById('host')!=null)\n\t\t{\n      \tdocument.getElementById('host').style.display='none';\n      \t}\n      \tif(document.getElementById('mg')!=null)\n\t\t{\n     \tdocument.getElementById('mg').style.display='table-row';\n     \t         }\n     \t        if(document.getElementById('hypervhost')!=null)\n");
/*  253 */       out.write("\t\t{\n\t        document.getElementById('hypervhost').style.display='none';\n\t        }\n\t        if(document.getElementById('hyperVVMs')!=null)\n\t\t{\n\tdocument.getElementById('hyperVVMs').style.display='none';\n\t}\n\t\tif(document.getElementById('xenhost') != null)\n\t\t{\n\t\t\tdocument.getElementById('xenhost').style.display='none';\n\t\t}\n\t\tif(document.getElementById('xenVMs') != null)\n\t\t{\n\t\t\tdocument.getElementById('xenVMs').style.display='none';\n\t\t}\n\n}\n</script>\n");
/*      */       
/*  255 */       org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/*  256 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  257 */       _jspx_th_html_005fform_005f0.setParent(null);
/*      */       
/*  259 */       _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*  260 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  261 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */         for (;;) {
/*  263 */           out.write(10);
/*  264 */           out.write("<!--$Id$-->\n              \n\n\n\n\n<script>                      \nfunction fnFormSubmit1(object)\n{\n\tlocation.href=object;\n}\n\nfunction showMailServerSettings()\n {\n         var showMail = false;\n         var query = window.location.search;\n         var pairs = query.split(\"&\");\n         \n         for (var i=0;i<pairs.length;i++)\n         {\n                 var pos = pairs[i].indexOf('=');\n                 if (pos >= 0)\n                 {\n                         var argname = pairs[i].substring(0,pos);\n                         var value = pairs[i].substring(pos+1);\n                         if(argname == \"isFromApi\")\n                         {\n                                 showMail = true;\n                         }\n                         //keys[keys.length] = argname;\n                         //values[values.length] = value;\n                 }\n         }\n         //alert(showMail);\n         return  showMail;\n }\n\nfunction doInitStuffOnBodyLoad()\n{\n        ");
/*      */           
/*  266 */           if ((pageContext.getAttribute("jsppage") != null) && (pageContext.getAttribute("jsppage").equals("programaction")))
/*      */           {
/*      */ 
/*  269 */             out.write("\n        myOnLoad1();\n        ");
/*      */           }
/*      */           
/*      */ 
/*  273 */           out.write("\n        \n\tif(document.AMActionForm!=null)\n\t{\n\tif(typeof(document.AMActionForm.actions)!='undefined')\n\t  {\n\t  if((location.search!= null && (location.search).match(\"EmailAction\")!=null) || (location.path!=null && (location.path).match(\"EMailActionForm\")!=null))\n\t  {\n\t\t\n\t\t");
/*  274 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  276 */           out.write("\t\n\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"SMSAction\")!=null) || (location.path!=null && (location.path).match(\"SMSActionForm\")!=null) || (location.search!= null && (location.search).match(\"SMSServerConfiguration\")!=null))\n\t  {\n\t\t \n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"ExecProg\")!=null) || (location.path!=null && (location.path).match(\"ExecProgramActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"reloadSendTrapActionForm\")!=null) || (location.path!=null && (location.path).match(\"SendTrapActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"Ticket\")!=null) || (location.path!=null && (location.path).match(\"showLogTicket\")!=null) ||  (location.search).match(\"showTicketAction\")!=null )\n");
/*  277 */           out.write("\t  {\n\t\t\t");
/*  278 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/*  280 */           out.write("\n\t\t  ");
/*      */           
/*  282 */           if ((com.adventnet.appmanager.util.Constants.sqlManager) || (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */           {
/*  284 */             out.write("\n\t\t\tdocument.AMActionForm.actions.selectedIndex=4;\n\t    \t");
/*      */           }
/*      */           else
/*      */           {
/*  288 */             out.write("\n\t       \tdocument.AMActionForm.actions.selectedIndex=5;\n\t  \t\t");
/*      */           }
/*  290 */           out.write("\n\t  }\n\t  else if(location.pathname!= null && (location.pathname).match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(location.search!= null && (location.search).match(\"jre\")!=null || (location.search!=null && (location.search).match(\"ThreadDumpActions\")!=null))\n\t  {\n\t    ");
/*  291 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer())
/*      */           {
/*  293 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=5;\n\t\t");
/*      */           }
/*      */           else
/*      */           {
/*  297 */             out.write("\n\t    document.AMActionForm.actions.selectedIndex=6;\n        ");
/*      */           }
/*  299 */           out.write("\n\t  }\t\n\t  else if(location.search!= null && (location.search).match(\"showVMAction\")!=null || (location.search!=null && (location.search).match(\"ShowVMActions\")!=null))\n\t  {\t\t \n\t    ");
/*  300 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer())
/*      */           {
/*  302 */             out.write("\n\t    if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t    \tdocument.AMActionForm.actions.selectedIndex=9;\n\t    }else{\n\t\t\tdocument.AMActionForm.actions.selectedIndex=7;\n\t    }\n\t\t");
/*      */           }
/*      */           else
/*      */           {
/*  306 */             out.write("\n\t\t  if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t\t    \tdocument.AMActionForm.actions.selectedIndex=10;\n\t\t    }else{\n\t  \t  document.AMActionForm.actions.selectedIndex=8;\n\t\t    }\n        ");
/*      */           }
/*  308 */           out.write("\n\t  }\t  \n\t  else if(location.search!= null && (location.search).match(\"winServAction\")!=null || (location.search!=null && (location.search).match(\"winServAction\")!=null))\n\t  { \n\t    ");
/*  309 */           if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  310 */             out.write("\n\t      document.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */           }
/*  312 */           else if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) {
/*  313 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=8;\n\t\t");
/*      */           } else {
/*  315 */             out.write("\n          document.AMActionForm.actions.selectedIndex=9;\n        ");
/*      */           }
/*  317 */           out.write("\t\t\n\t  }\t  \n\t   else if((location.search!= null && (location.search).match(\"ExecQueryAction\")!=null) || (location.path!=null && (location.path).match(\"ExecQueryActionForm\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=5;\n  \t   }\n\t   else if((location.search!= null && (location.search).match(\"sqlJobAction\")!=null) || (location.path!=null && (location.path).match(\"sqlJobAction\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=7;\n  \t   }\n\t   else if(location.search!= null && (location.search).match(\"amazon\")!=null)\n\t\t  {\n\t\t    ");
/*  318 */           if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) {
/*  319 */             out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */           } else {
/*  321 */             out.write("\n\t\t    document.AMActionForm.actions.selectedIndex=7;\n        ");
/*      */           }
/*  323 */           out.write("\n\t\t  }\n\t  \t  \n\t  }\n\n\tif(document.AMActionForm.selectedBusinessHourID != null)\n\t{\n\t\tinit();\n\t}\n\t\n\t}\n\telse\n\t{\n\t\tif(document.MBeanOperationActionForm.actions!=undefined)\n\t\t{\n\t  \t      document.MBeanOperationActionForm.actions.selectedIndex=4;\t  \n\t  \t}\n\t}\n\n}\nfunction restvalue()\n{\n//alert(document.AMActionForm.actionslist.selectedIndex);\nvar selectedIdx=document.AMActionForm.actionslist.selectedIndex;\ndocument.AMActionForm.reset();\ndocument.AMActionForm.actionslist.selectedIndex=selectedIdx;\n}\n</script>\n");
/*      */           
/*  325 */           String action_haid = request.getParameter("haid");
/*  326 */           String returnpath = "";
/*      */           
/*  328 */           if (request.getParameter("returnpath") != null)
/*      */           {
/*  330 */             returnpath = "&returnpath=" + java.net.URLEncoder.encode(request.getParameter("returnpath"));
/*      */           }
/*      */           
/*      */ 
/*  334 */           out.write(10);
/*  335 */           out.write(10);
/*      */           
/*  337 */           SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  338 */           _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  339 */           _jspx_th_c_005fset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  341 */           _jspx_th_c_005fset_005f0.setVar("isSqlManager");
/*  342 */           int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  343 */           if (_jspx_eval_c_005fset_005f0 != 0) {
/*  344 */             if (_jspx_eval_c_005fset_005f0 != 1) {
/*  345 */               out = _jspx_page_context.pushBody();
/*  346 */               _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  347 */               _jspx_th_c_005fset_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  350 */               out.print(com.adventnet.appmanager.util.Constants.sqlManager);
/*  351 */               int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  352 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  355 */             if (_jspx_eval_c_005fset_005f0 != 1) {
/*  356 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  359 */           if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  360 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */           }
/*      */           
/*  363 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  364 */           out.write(10);
/*      */           
/*  366 */           SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  367 */           _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  368 */           _jspx_th_c_005fset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  370 */           _jspx_th_c_005fset_005f1.setVar("isIt360");
/*  371 */           int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  372 */           if (_jspx_eval_c_005fset_005f1 != 0) {
/*  373 */             if (_jspx_eval_c_005fset_005f1 != 1) {
/*  374 */               out = _jspx_page_context.pushBody();
/*  375 */               _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/*  376 */               _jspx_th_c_005fset_005f1.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  379 */               out.print(com.adventnet.appmanager.util.Constants.isIt360);
/*  380 */               int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/*  381 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  384 */             if (_jspx_eval_c_005fset_005f1 != 1) {
/*  385 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  388 */           if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  389 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */           }
/*      */           
/*  392 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/*  393 */           out.write(10);
/*      */           
/*  395 */           SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  396 */           _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/*  397 */           _jspx_th_c_005fset_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  399 */           _jspx_th_c_005fset_005f2.setVar("isAdminServer");
/*  400 */           int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/*  401 */           if (_jspx_eval_c_005fset_005f2 != 0) {
/*  402 */             if (_jspx_eval_c_005fset_005f2 != 1) {
/*  403 */               out = _jspx_page_context.pushBody();
/*  404 */               _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/*  405 */               _jspx_th_c_005fset_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  408 */               out.print(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer());
/*  409 */               int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/*  410 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  413 */             if (_jspx_eval_c_005fset_005f2 != 1) {
/*  414 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  417 */           if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/*  418 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */           }
/*      */           
/*  421 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/*  422 */           out.write(10);
/*      */           
/*  424 */           SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  425 */           _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/*  426 */           _jspx_th_c_005fset_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  428 */           _jspx_th_c_005fset_005f3.setVar("isProfServer");
/*  429 */           int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/*  430 */           if (_jspx_eval_c_005fset_005f3 != 0) {
/*  431 */             if (_jspx_eval_c_005fset_005f3 != 1) {
/*  432 */               out = _jspx_page_context.pushBody();
/*  433 */               _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/*  434 */               _jspx_th_c_005fset_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  437 */               out.print(com.adventnet.appmanager.util.EnterpriseUtil.isProfEdition());
/*  438 */               int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/*  439 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  442 */             if (_jspx_eval_c_005fset_005f3 != 1) {
/*  443 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  446 */           if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/*  447 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */           }
/*      */           
/*  450 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/*  451 */           out.write(10);
/*      */           
/*  453 */           SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  454 */           _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/*  455 */           _jspx_th_c_005fset_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  457 */           _jspx_th_c_005fset_005f4.setVar("isCloudServer");
/*  458 */           int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/*  459 */           if (_jspx_eval_c_005fset_005f4 != 0) {
/*  460 */             if (_jspx_eval_c_005fset_005f4 != 1) {
/*  461 */               out = _jspx_page_context.pushBody();
/*  462 */               _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/*  463 */               _jspx_th_c_005fset_005f4.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  466 */               out.print(com.adventnet.appmanager.util.EnterpriseUtil.isCloudEdition());
/*  467 */               int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/*  468 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*  471 */             if (_jspx_eval_c_005fset_005f4 != 1) {
/*  472 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/*  475 */           if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/*  476 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */           }
/*      */           
/*  479 */           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/*  480 */           out.write("\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" >\n  <tr> \n    <td align=\"left\" width=\"50%\" class=\"bodytext\">");
/*  481 */           out.print(FormatUtil.getString("am.webclient.newaction.selectactiontype"));
/*  482 */           out.write("&nbsp;\n\t<select id=\"actionslist\" name=\"actions\" onchange=\"javascript:fnFormSubmit1(this.form.actions.options[this.selectedIndex].value);\" class=\"formtext\">\n\t");
/*      */           
/*  484 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  485 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  486 */           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_html_005fform_005f0);
/*  487 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  488 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */             for (;;) {
/*  490 */               out.write(10);
/*  491 */               out.write(9);
/*      */               
/*  493 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  494 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  495 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */               
/*  497 */               _jspx_th_c_005fwhen_005f0.setTest("${empty param.global}");
/*  498 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  499 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                 for (;;) {
/*  501 */                   out.write("\t\n\t\t<option value=\"/showTile.do?TileName=.EmailActions&haid=");
/*  502 */                   out.print(action_haid);
/*  503 */                   out.print(returnpath);
/*  504 */                   out.write(34);
/*  505 */                   out.write(62);
/*  506 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  507 */                   out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.SMSActions&haid=");
/*  508 */                   out.print(action_haid);
/*  509 */                   out.print(returnpath);
/*  510 */                   out.write(34);
/*  511 */                   out.write(62);
/*  512 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  513 */                   out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.ExecProg&haid=");
/*  514 */                   out.print(action_haid);
/*  515 */                   out.print(returnpath);
/*  516 */                   out.write(34);
/*  517 */                   out.write(62);
/*  518 */                   out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  519 */                   out.write("</option>\n\t\t<option value=\"/adminAction.do?method=reloadSendTrapActionForm&haid=");
/*  520 */                   out.print(action_haid);
/*  521 */                   out.print(returnpath);
/*  522 */                   out.write(34);
/*  523 */                   out.write(62);
/*  524 */                   out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/*  525 */                   out.write("</option>\n\t\t\n\t\t");
/*      */                   
/*  527 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  528 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  529 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*  530 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  531 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/*  533 */                       out.write("\n\t\t\t");
/*      */                       
/*  535 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  536 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  537 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/*  539 */                       _jspx_th_c_005fwhen_005f1.setTest("${!isIt360}");
/*  540 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  541 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/*  543 */                           out.write("\n\t\t\t\t");
/*      */                           
/*  545 */                           ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  546 */                           _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  547 */                           _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/*  548 */                           int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  549 */                           if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                             for (;;) {
/*  551 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/*  553 */                               WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  554 */                               _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  555 */                               _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                               
/*  557 */                               _jspx_th_c_005fwhen_005f2.setTest("${!isSqlManager}");
/*  558 */                               int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  559 */                               if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                 for (;;) {
/*  561 */                                   out.write("\n\t\t\t\t\t\t<!-- MBean Operation-->\n\t\t\t\t\t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  562 */                                   out.print(action_haid);
/*  563 */                                   out.print(returnpath);
/*  564 */                                   out.write(34);
/*  565 */                                   out.write(62);
/*  566 */                                   out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  567 */                                   out.write("</option>\n\t\t\t\t\t");
/*  568 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  569 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  573 */                               if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  574 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                               }
/*      */                               
/*  577 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  578 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/*  580 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  581 */                               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  582 */                               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f2);
/*  583 */                               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  584 */                               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                 for (;;) {
/*  586 */                                   out.write("\n\t\t\t\t\t\t<!-- Execute Query Action-->\n\t   \t\t\t\t\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  587 */                                   out.print(action_haid);
/*  588 */                                   out.print(returnpath);
/*  589 */                                   out.write(34);
/*  590 */                                   out.write(62);
/*  591 */                                   out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  592 */                                   out.write("</option>\n\t   \t\t\t\t\t<!-- Sql job action -->\n\t\t\t\t\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  593 */                                   out.print(action_haid);
/*  594 */                                   out.print(returnpath);
/*  595 */                                   out.write(34);
/*  596 */                                   out.write(62);
/*  597 */                                   out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  598 */                                   out.write("</option>\n\t\t\t\t\t");
/*  599 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  600 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  604 */                               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  605 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                               }
/*      */                               
/*  608 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  609 */                               out.write("\n\t\t\t\t");
/*  610 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  611 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  615 */                           if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  616 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                           }
/*      */                           
/*  619 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  620 */                           out.write("\n\t\t\t\t\t<!-- Log Ticket Operation-->\n\t\t\t\t\t");
/*  621 */                           if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  622 */                             out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  623 */                             out.print(action_haid);
/*  624 */                             out.print(returnpath);
/*  625 */                             out.write(34);
/*  626 */                             out.write(62);
/*  627 */                             out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  628 */                             out.write("</option> ");
/*      */                           }
/*  630 */                           out.write("\n\t\t\t");
/*  631 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  632 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  636 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  637 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/*  640 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  641 */                       out.write("\n\t\t\t");
/*      */                       
/*  643 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  644 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  645 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  646 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  647 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/*  649 */                           out.write("\n\t\t   \t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  650 */                           out.print(action_haid);
/*  651 */                           out.print(returnpath);
/*  652 */                           out.write(34);
/*  653 */                           out.write(62);
/*  654 */                           out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  655 */                           out.write("</option>\n\t\t   \t\t<!-- Log Ticket Operation-->\n\t\t   \t\t");
/*      */                           
/*  657 */                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  658 */                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  659 */                           _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                           
/*  661 */                           _jspx_th_c_005fif_005f2.setTest("${isProfServer || isAdminServer}");
/*  662 */                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  663 */                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                             for (;;) {
/*  665 */                               out.write("\n\t\t\t\t\t");
/*  666 */                               if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  667 */                                 out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  668 */                                 out.print(action_haid);
/*  669 */                                 out.print(returnpath);
/*  670 */                                 out.write(34);
/*  671 */                                 out.write(62);
/*  672 */                                 out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  673 */                                 out.write("</option> ");
/*      */                               }
/*  675 */                               out.write("\n\t\t   \t\t");
/*  676 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  677 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  681 */                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  682 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                           }
/*      */                           
/*  685 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  686 */                           out.write("\n\t\t\t");
/*  687 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  688 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  692 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  693 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/*  696 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  697 */                       out.write(10);
/*  698 */                       out.write(9);
/*  699 */                       out.write(9);
/*  700 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  701 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  705 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  706 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/*  709 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  710 */                   out.write(10);
/*  711 */                   out.write(9);
/*  712 */                   out.write(9);
/*      */                   
/*  714 */                   IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  715 */                   _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  716 */                   _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  718 */                   _jspx_th_c_005fif_005f3.setTest("${!isAdminServer}");
/*  719 */                   int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  720 */                   if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                     for (;;) {
/*  722 */                       out.write("\n\t\t\t<!--JRE Action -->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  723 */                       out.print(action_haid);
/*  724 */                       out.print(returnpath);
/*  725 */                       out.write(34);
/*  726 */                       out.write(62);
/*  727 */                       out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  728 */                       out.write("</option>\n\t\t\t<!--Amazon Instance Action-->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  729 */                       out.print(action_haid);
/*  730 */                       out.print(returnpath);
/*  731 */                       out.write(34);
/*  732 */                       out.write(62);
/*  733 */                       out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  734 */                       out.write("</option>\n\t\t\t<!--VM Action -->\n\t      \t<option value=\"/adminAction.do?method=showVMAction&haid=");
/*  735 */                       out.print(action_haid);
/*  736 */                       out.print(returnpath);
/*  737 */                       out.write(34);
/*  738 */                       out.write(62);
/*  739 */                       out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  740 */                       out.write("</option>\n\t      \t\n\t\t\t<!-- Windows Service action -->\n\t\t\t");
/*      */                       
/*  742 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  743 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  744 */                       _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */                       
/*  746 */                       _jspx_th_c_005fif_005f4.setTest("${!isCloudServer}");
/*  747 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  748 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/*  750 */                           out.write("\n\t   \t\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  751 */                           out.print(action_haid);
/*  752 */                           out.print(returnpath);
/*  753 */                           out.write(34);
/*  754 */                           out.write(62);
/*  755 */                           out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  756 */                           out.write("</option>\n\t   \t\t");
/*  757 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  758 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  762 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  763 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/*  766 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  767 */                       out.write("\n\t   \t\t<option value=\"/adminAction.do?method=showVMAction&isContainerAction=true&haid=");
/*  768 */                       out.print(action_haid);
/*  769 */                       out.print(returnpath);
/*  770 */                       out.write(34);
/*  771 */                       out.write(62);
/*  772 */                       out.print(FormatUtil.getString("am.container.action.createnew"));
/*  773 */                       out.write("</option>\n   \t\t");
/*  774 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  775 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  779 */                   if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  780 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                   }
/*      */                   
/*  783 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  784 */                   out.write(10);
/*  785 */                   out.write(9);
/*  786 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  787 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  791 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  792 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */               }
/*      */               
/*  795 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  796 */               out.write(10);
/*  797 */               out.write(9);
/*      */               
/*  799 */               OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  800 */               _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  801 */               _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*  802 */               int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  803 */               if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                 for (;;) {
/*  805 */                   out.write(10);
/*      */                   
/*  807 */                   String redirectTo = null;
/*  808 */                   if (request.getParameter("redirectto") != null)
/*      */                   {
/*  810 */                     redirectTo = java.net.URLEncoder.encode(request.getParameter("redirectto"));
/*      */                   }
/*      */                   else
/*      */                   {
/*  814 */                     redirectTo = java.net.URLEncoder.encode("/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true");
/*      */                   }
/*      */                   
/*  817 */                   out.write("\t\n\t<option value=\"/showTile.do?TileName=.EmailActions&PRINTER_FRIENDLY=true&haid=");
/*  818 */                   out.print(action_haid);
/*  819 */                   out.write("&global=true");
/*  820 */                   out.print(returnpath);
/*  821 */                   out.write(34);
/*  822 */                   out.write(62);
/*  823 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  824 */                   out.write("</option>\n\t<option value=\"/showTile.do?TileName=.SMSActions&PRINTER_FRIENDLY=true&haid=");
/*  825 */                   out.print(action_haid);
/*  826 */                   out.write("&global=true");
/*  827 */                   out.print(returnpath);
/*  828 */                   out.write(34);
/*  829 */                   out.write(62);
/*  830 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  831 */                   out.write("</option>\n\t");
/*      */                   
/*  833 */                   org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f1 = (org.apache.struts.taglib.logic.PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(org.apache.struts.taglib.logic.PresentTag.class);
/*  834 */                   _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  835 */                   _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                   
/*  837 */                   _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/*  838 */                   int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  839 */                   if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                     for (;;) {
/*  841 */                       out.write(32);
/*  842 */                       out.write("\n\t<option value=\"/jsp/ExecProgramActionForm.jsp?haid=");
/*  843 */                       out.print(action_haid);
/*  844 */                       out.write("&global=true");
/*  845 */                       out.print(returnpath);
/*  846 */                       out.write(34);
/*  847 */                       out.write(62);
/*  848 */                       out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  849 */                       out.write("</option>\n\t");
/*  850 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/*  851 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  855 */                   if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/*  856 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                   }
/*      */                   
/*  859 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*  860 */                   out.write("\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=11&haid=");
/*  861 */                   out.print(action_haid);
/*  862 */                   out.write("&global=true");
/*  863 */                   out.print(returnpath);
/*  864 */                   out.write(34);
/*  865 */                   out.write(62);
/*  866 */                   out.print(FormatUtil.getString("am.webclient.common.sendv1trap.text"));
/*  867 */                   out.write("</option>\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=12&haid=");
/*  868 */                   out.print(action_haid);
/*  869 */                   out.write("&global=true");
/*  870 */                   out.print(returnpath);
/*  871 */                   out.write(34);
/*  872 */                   out.write(62);
/*  873 */                   out.print(FormatUtil.getString("am.webclient.common.sendv2ctrap.text"));
/*  874 */                   out.write("</option>\n\t");
/*  875 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))) {
/*  876 */                     out.write(32);
/*  877 */                     out.write("\n\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&popup=true&global=true&haid=");
/*  878 */                     out.print(action_haid);
/*  879 */                     out.print(returnpath);
/*  880 */                     out.write(34);
/*  881 */                     out.write(62);
/*  882 */                     out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  883 */                     out.write("</option>\n\t");
/*      */                   }
/*  885 */                   out.write(10);
/*  886 */                   out.write(9);
/*  887 */                   out.write(9);
/*  888 */                   out.write(10);
/*  889 */                   out.write(9);
/*  890 */                   if ((!com.adventnet.appmanager.util.Constants.isIt360) || (com.adventnet.appmanager.util.EnterpriseUtil.isProfEdition()) || (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))
/*      */                   {
/*  892 */                     out.write("<option value=\"/adminAction.do?method=showLogTicket&global=true&haid=");
/*  893 */                     out.print(action_haid);
/*  894 */                     out.write("&redirectTo=");
/*  895 */                     out.print(redirectTo);
/*  896 */                     out.write(34);
/*  897 */                     out.write(62);
/*  898 */                     out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  899 */                     out.write("</option> ");
/*      */                   }
/*      */                   
/*  902 */                   out.write("\n\t\n\t");
/*  903 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()))) {
/*  904 */                     out.write(" \n\t<!--JRE Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  905 */                     out.print(action_haid);
/*  906 */                     out.write("&global=true");
/*  907 */                     out.print(returnpath);
/*  908 */                     out.write("&ext=true\">");
/*  909 */                     out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  910 */                     out.write("</option>\n\t<!--VM Action -->\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&haid=");
/*  911 */                     out.print(action_haid);
/*  912 */                     out.print(returnpath);
/*  913 */                     out.write("&ext=true&global=true\">");
/*  914 */                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  915 */                     out.write("</option>\n\t<!--Amazon Instance Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  916 */                     out.print(action_haid);
/*  917 */                     out.write("&global=true");
/*  918 */                     out.print(returnpath);
/*  919 */                     out.write("&ext=true\">");
/*  920 */                     out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  921 */                     out.write("</option>\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&isContainerAction=true&haid=");
/*  922 */                     out.print(action_haid);
/*  923 */                     out.print(returnpath);
/*  924 */                     out.write("&ext=true&global=true\">");
/*  925 */                     out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  926 */                     out.write("</option>\n\t ");
/*  927 */                   } else if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  928 */                     out.write(32);
/*  929 */                     out.write("\n\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  930 */                     out.print(action_haid);
/*  931 */                     out.write("&global=true");
/*  932 */                     out.print(returnpath);
/*  933 */                     out.write(34);
/*  934 */                     out.write(62);
/*  935 */                     out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  936 */                     out.write("</option>\n\t");
/*      */                   }
/*  938 */                   out.write(10);
/*  939 */                   out.write(9);
/*  940 */                   if ((!com.adventnet.appmanager.util.Constants.sqlManager) && ((!com.adventnet.appmanager.util.Constants.isIt360) || (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())) && (!"CLOUD".equalsIgnoreCase(com.adventnet.appmanager.util.Constants.getCategorytype()))) {
/*  941 */                     out.write("\n\t<!-- Windows Service action -->\n\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  942 */                     out.print(action_haid);
/*  943 */                     out.print(returnpath);
/*  944 */                     out.write(34);
/*  945 */                     out.write(62);
/*  946 */                     out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  947 */                     out.write("</option>\t\n\t");
/*      */                   }
/*  949 */                   out.write(10);
/*  950 */                   out.write(9);
/*  951 */                   out.write(32);
/*  952 */                   if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  953 */                     out.write("\n\t<!-- Sql job action -->\n\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  954 */                     out.print(action_haid);
/*  955 */                     out.print(returnpath);
/*  956 */                     out.write(34);
/*  957 */                     out.write(62);
/*  958 */                     out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  959 */                     out.write("</option>\n\t");
/*      */                   }
/*  961 */                   out.write(10);
/*  962 */                   out.write(9);
/*  963 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  964 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  968 */               if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  969 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */               }
/*      */               
/*  972 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  973 */               out.write(10);
/*  974 */               out.write(9);
/*  975 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  976 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  980 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  981 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */           }
/*      */           
/*  984 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  985 */           out.write("\n\t</select>\n    </td>\n  </tr>\n</table>\n\n");
/*      */           
/*  987 */           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  988 */           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  989 */           _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/*  991 */           _jspx_th_c_005fif_005f5.setTest("${param.global=='true'}");
/*  992 */           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  993 */           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */             for (;;) {
/*  995 */               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td valign=\"top\"> \n<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/*  996 */               out.write("<!--$Id$-->\n\n\n\n");
/*  997 */               if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                 return;
/*  999 */               out.write("\n      \n\n");
/*      */               
/* 1001 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1002 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1003 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f5);
/*      */               
/* 1005 */               _jspx_th_c_005fif_005f6.setTest("${(uri=='/jsp/CreateApplication.jsp')|| uri=='/admin/createapplication.do'||uri=='/admin/createapplication.do' ||(!empty param.wiz && !empty param.haid && (empty invalidhaid))||(param.method=='insert')}");
/* 1006 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1007 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/* 1009 */                   out.write("\n\t  <tr>\n\t  <td class=\"tdindent\">\n");
/* 1010 */                   if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1012 */                   out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">  \n  <tr> \n    <td width=\"2%\">");
/*      */                   
/* 1014 */                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1015 */                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 1016 */                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1018 */                   _jspx_th_c_005fif_005f7.setTest("${uri=='/jsp/CreateApplication.jsp' || uri=='/admin/createapplicationwiz.do'||uri=='/admin/createapplication.do'}");
/* 1019 */                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 1020 */                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                     for (;;) {
/* 1022 */                       out.write("\n    \t");
/*      */                       
/* 1024 */                       SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1025 */                       _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 1026 */                       _jspx_th_c_005fset_005f6.setParent(_jspx_th_c_005fif_005f7);
/*      */                       
/* 1028 */                       _jspx_th_c_005fset_005f6.setVar("wizimage");
/* 1029 */                       int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 1030 */                       if (_jspx_eval_c_005fset_005f6 != 0) {
/* 1031 */                         if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1032 */                           out = _jspx_page_context.pushBody();
/* 1033 */                           _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 1034 */                           _jspx_th_c_005fset_005f6.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1037 */                           out.write(" \n    \t<img src=\"/images/wiz_newbizapp_high.gif\" border=\"0\" align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1038 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1039 */                           out.write(" </b></font>\n    \t");
/* 1040 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 1041 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1044 */                         if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1045 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1048 */                       if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 1049 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6); return;
/*      */                       }
/*      */                       
/* 1052 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 1053 */                       out.write("\n    ");
/* 1054 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1055 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1059 */                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1060 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                   }
/*      */                   
/* 1063 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1064 */                   out.write("\n    ");
/*      */                   
/* 1066 */                   IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1067 */                   _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 1068 */                   _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1070 */                   _jspx_th_c_005fif_005f8.setTest("${uri!='/jsp/CreateApplication.jsp' && uri!='/admin/createapplicationwiz.do'&& uri!='/admin/createapplication.do'}");
/* 1071 */                   int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 1072 */                   if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                     for (;;) {
/* 1074 */                       out.write("\n    \t");
/*      */                       
/* 1076 */                       SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1077 */                       _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 1078 */                       _jspx_th_c_005fset_005f7.setParent(_jspx_th_c_005fif_005f8);
/*      */                       
/* 1080 */                       _jspx_th_c_005fset_005f7.setVar("wizimage");
/* 1081 */                       int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 1082 */                       if (_jspx_eval_c_005fset_005f7 != 0) {
/* 1083 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1084 */                           out = _jspx_page_context.pushBody();
/* 1085 */                           _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 1086 */                           _jspx_th_c_005fset_005f7.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1089 */                           out.write("\n    \t<img src=\"/images/wiz_newbizapp_nor.gif\" border=0> <font family=\"verdana\" size=\"2\" color=\"#818181\">");
/* 1090 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1091 */                           out.write("</font>  \t");
/* 1092 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 1093 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1096 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1097 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1100 */                       if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 1101 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                       }
/*      */                       
/* 1104 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 1105 */                       out.write("\n    ");
/* 1106 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 1107 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1111 */                   if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 1112 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                   }
/*      */                   
/* 1115 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 1116 */                   out.write("      \n\t</td>\n    <td width=\"20%\">");
/* 1117 */                   if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1119 */                   out.write("</td>  \n   \n");
/*      */                   
/* 1121 */                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1122 */                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1123 */                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f6);
/* 1124 */                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1125 */                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                     for (;;) {
/* 1127 */                       out.write("\n    ");
/*      */                       
/* 1129 */                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1130 */                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1131 */                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                       
/* 1133 */                       _jspx_th_c_005fwhen_005f3.setTest("${( param.method=='configureHostDiscovery' || param.method=='associateMonitors'||param.method=='getMonitorForm'||param.method=='addResource')&& (empty showwiz3) && (empty UrlForm) }");
/* 1134 */                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1135 */                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                         for (;;) {
/* 1137 */                           out.write("\n    \n\t\n\t\t\n\t\t");
/*      */                           
/* 1139 */                           SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1140 */                           _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 1141 */                           _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                           
/* 1143 */                           _jspx_th_c_005fset_005f8.setVar("wizimage");
/* 1144 */                           int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 1145 */                           if (_jspx_eval_c_005fset_005f8 != 0) {
/* 1146 */                             if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1147 */                               out = _jspx_page_context.pushBody();
/* 1148 */                               _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 1149 */                               _jspx_th_c_005fset_005f8.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1152 */                               out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1153 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1154 */                               out.write(" </b></font>\n    \t");
/* 1155 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 1156 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1159 */                             if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1160 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1163 */                           if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 1164 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                           }
/*      */                           
/* 1167 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 1168 */                           out.write("\n   ");
/* 1169 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1170 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1174 */                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1175 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                       }
/*      */                       
/* 1178 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1179 */                       out.write("\n   ");
/*      */                       
/* 1181 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1182 */                       _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1183 */                       _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 1184 */                       int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1185 */                       if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                         for (;;) {
/* 1187 */                           out.write("  \n    \t\n\t\t");
/*      */                           
/* 1189 */                           SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1190 */                           _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 1191 */                           _jspx_th_c_005fset_005f9.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                           
/* 1193 */                           _jspx_th_c_005fset_005f9.setVar("wizimage");
/* 1194 */                           int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 1195 */                           if (_jspx_eval_c_005fset_005f9 != 0) {
/* 1196 */                             if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1197 */                               out = _jspx_page_context.pushBody();
/* 1198 */                               _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 1199 */                               _jspx_th_c_005fset_005f9.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1202 */                               out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1203 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1204 */                               out.write(" </font>\n    \t");
/* 1205 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 1206 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1209 */                             if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1210 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1213 */                           if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 1214 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                           }
/*      */                           
/* 1217 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 1218 */                           out.write("\n\t\n\t\t\n   ");
/* 1219 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1220 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1224 */                       if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1225 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                       }
/*      */                       
/* 1228 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1229 */                       out.write(10);
/* 1230 */                       out.write(32);
/* 1231 */                       out.write(32);
/* 1232 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1233 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1237 */                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1238 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                   }
/*      */                   
/* 1241 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1242 */                   out.write(" \n\n    \n     <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"20%\">\n    ");
/* 1243 */                   if (_jspx_meth_c_005fif_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1245 */                   out.write("\n    \t");
/* 1246 */                   if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1248 */                   out.write("\n    \t\n    \t");
/* 1249 */                   if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1251 */                   out.write("\n    \t</td>    \t\n    \t<!-- ############################################# check for third tab #####################################  -->\n       ");
/*      */                   
/* 1253 */                   ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1254 */                   _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1255 */                   _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f6);
/* 1256 */                   int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1257 */                   if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                     for (;;) {
/* 1259 */                       out.write("\n       ");
/*      */                       
/* 1261 */                       WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1262 */                       _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1263 */                       _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                       
/* 1265 */                       _jspx_th_c_005fwhen_005f4.setTest("${(param.method=='reloadHostDiscoveryForm'|| param.method=='getMonitorForm'||param.method=='addResource'|| (!empty showwiz3) || (!empty UrlForm) ) && (param.method!='getHAProfiles') }");
/* 1266 */                       int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1267 */                       if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                         for (;;) {
/* 1269 */                           out.write("\n   \n   \t    \t");
/*      */                           
/* 1271 */                           SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1272 */                           _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 1273 */                           _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                           
/* 1275 */                           _jspx_th_c_005fset_005f10.setVar("wizimage");
/* 1276 */                           int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 1277 */                           if (_jspx_eval_c_005fset_005f10 != 0) {
/* 1278 */                             if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1279 */                               out = _jspx_page_context.pushBody();
/* 1280 */                               _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 1281 */                               _jspx_th_c_005fset_005f10.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1284 */                               out.write("\n   \t    \t<img src=\"/images/new_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b> ");
/* 1285 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1286 */                               out.write(" </b></font>\n   \t    \t");
/* 1287 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 1288 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1291 */                             if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1292 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1295 */                           if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 1296 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                           }
/*      */                           
/* 1299 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 1300 */                           out.write("\n       ");
/* 1301 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1302 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1306 */                       if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1307 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                       }
/*      */                       
/* 1310 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1311 */                       out.write("\n        ");
/*      */                       
/* 1313 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1314 */                       _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1315 */                       _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1316 */                       int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1317 */                       if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                         for (;;) {
/* 1319 */                           out.write("  \n   \t    \t");
/*      */                           
/* 1321 */                           SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1322 */                           _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 1323 */                           _jspx_th_c_005fset_005f11.setParent(_jspx_th_c_005fotherwise_005f4);
/*      */                           
/* 1325 */                           _jspx_th_c_005fset_005f11.setVar("wizimage");
/* 1326 */                           int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 1327 */                           if (_jspx_eval_c_005fset_005f11 != 0) {
/* 1328 */                             if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1329 */                               out = _jspx_page_context.pushBody();
/* 1330 */                               _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 1331 */                               _jspx_th_c_005fset_005f11.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1334 */                               out.write("\n\t\t   \t    \t<img src=\"/images/new_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1335 */                               out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1336 */                               out.write(" </font>\n   \t    \t");
/* 1337 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 1338 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1341 */                             if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1342 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1345 */                           if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 1346 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11); return;
/*      */                           }
/*      */                           
/* 1349 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 1350 */                           out.write("\n   \t");
/* 1351 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1352 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1356 */                       if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1357 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                       }
/*      */                       
/* 1360 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1361 */                       out.write(10);
/* 1362 */                       out.write(32);
/* 1363 */                       out.write(32);
/* 1364 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1365 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1369 */                   if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1370 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                   }
/*      */                   
/* 1373 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1374 */                   out.write(" \n\n        <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n       <td width=\"18%\">\n       ");
/* 1375 */                   if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1377 */                   out.write("\n       ");
/* 1378 */                   if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1380 */                   out.write("\n       ");
/* 1381 */                   out.write("\n       \t");
/* 1382 */                   if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1384 */                   out.write("\n    \t</td>\n   \n  <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"17%\">\n\t");
/*      */                   
/* 1386 */                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1387 */                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 1388 */                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1390 */                   _jspx_th_c_005fif_005f13.setTest("${param.method=='getHAProfiles'}");
/* 1391 */                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 1392 */                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                     for (;;) {
/* 1394 */                       out.write(10);
/* 1395 */                       out.write(9);
/*      */                       
/* 1397 */                       SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1398 */                       _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 1399 */                       _jspx_th_c_005fset_005f12.setParent(_jspx_th_c_005fif_005f13);
/*      */                       
/* 1401 */                       _jspx_th_c_005fset_005f12.setVar("wizimage");
/* 1402 */                       int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 1403 */                       if (_jspx_eval_c_005fset_005f12 != 0) {
/* 1404 */                         if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1405 */                           out = _jspx_page_context.pushBody();
/* 1406 */                           _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 1407 */                           _jspx_th_c_005fset_005f12.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1410 */                           out.write("\n\t\t<img src=\"/images/wiz_configurealerts_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b>");
/* 1411 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1412 */                           out.write(" </b></font>\n    \t");
/* 1413 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 1414 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1417 */                         if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1418 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1421 */                       if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 1422 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12); return;
/*      */                       }
/*      */                       
/* 1425 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 1426 */                       out.write(10);
/* 1427 */                       out.write(9);
/* 1428 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 1429 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1433 */                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 1434 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                   }
/*      */                   
/* 1437 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1438 */                   out.write(10);
/* 1439 */                   out.write(9);
/*      */                   
/* 1441 */                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1442 */                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 1443 */                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/* 1445 */                   _jspx_th_c_005fif_005f14.setTest("${param.method!='getHAProfiles'}");
/* 1446 */                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 1447 */                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                     for (;;) {
/* 1449 */                       out.write(10);
/* 1450 */                       out.write(9);
/*      */                       
/* 1452 */                       SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1453 */                       _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 1454 */                       _jspx_th_c_005fset_005f13.setParent(_jspx_th_c_005fif_005f14);
/*      */                       
/* 1456 */                       _jspx_th_c_005fset_005f13.setVar("wizimage");
/* 1457 */                       int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 1458 */                       if (_jspx_eval_c_005fset_005f13 != 0) {
/* 1459 */                         if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1460 */                           out = _jspx_page_context.pushBody();
/* 1461 */                           _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 1462 */                           _jspx_th_c_005fset_005f13.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1465 */                           out.write("\n\t\t<img src=\"/images/wiz_configurealerts_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1466 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1467 */                           out.write(" </font>\n    \t");
/* 1468 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 1469 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1472 */                         if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1473 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1476 */                       if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 1477 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13); return;
/*      */                       }
/*      */                       
/* 1480 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 1481 */                       out.write("\n\t\n\t");
/* 1482 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 1483 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1487 */                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 1488 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                   }
/*      */                   
/* 1491 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 1492 */                   out.write(10);
/* 1493 */                   if (_jspx_meth_c_005fif_005f15(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1495 */                   out.write("   \n ");
/* 1496 */                   if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1498 */                   out.write(10);
/* 1499 */                   out.write(32);
/* 1500 */                   out.write(10);
/* 1501 */                   if (_jspx_meth_c_005fif_005f16(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/* 1503 */                   out.write("   \n    </td>\n    \n    <td width=\"2%\" rowspan=\"2\" valign=\"bottom\" align=\"right\"><img src=\"/images/wiz_endicon.gif\" width=\"33\" height=\"36\"></td>\n  </tr>\n  <tr background=\"/images/wiz_bg_graylind.gif\"> \n    <td><img src=\"/images/wiz_startimage.gif\" width=\"18\" height=\"16\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n   \n   <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n \n");
/* 1504 */                   out.write("  </tr>\n\n</table>\n</td></tr>\n");
/* 1505 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1506 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1510 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1511 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/* 1514 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1515 */               out.write(10);
/* 1516 */               out.write(10);
/* 1517 */               if (request.getAttribute("EmailForm") == null) {
/* 1518 */                 out.write(10);
/*      */                 
/* 1520 */                 MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1521 */                 _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 1522 */                 _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                 
/* 1524 */                 _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                 
/* 1526 */                 _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 1527 */                 int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 1528 */                 if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 1529 */                   String msg = null;
/* 1530 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1531 */                     out = _jspx_page_context.pushBody();
/* 1532 */                     _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 1533 */                     _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                   }
/* 1535 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/* 1537 */                     out.write(" \n<tr> \n  <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 1538 */                     if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                       return;
/* 1540 */                     out.write("</td>\n\t  </tr>\n\t</table>\n\t<br></td>\n</tr>\n");
/* 1541 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 1542 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/* 1543 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1546 */                   if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1547 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1550 */                 if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 1551 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                 }
/*      */                 
/* 1554 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */               }
/* 1556 */               out.write(32);
/*      */               
/* 1558 */               org.apache.struts.taglib.logic.MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (org.apache.struts.taglib.logic.MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(org.apache.struts.taglib.logic.MessagesPresentTag.class);
/* 1559 */               _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 1560 */               _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */               
/* 1562 */               _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 1563 */               int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 1564 */               if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 1566 */                   out.write(" \n<tr> \n  <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\"> ");
/*      */                   
/* 1568 */                   MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1569 */                   _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 1570 */                   _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                   
/* 1572 */                   _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                   
/* 1574 */                   _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 1575 */                   int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 1576 */                   if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 1577 */                     String msg = null;
/* 1578 */                     if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1579 */                       out = _jspx_page_context.pushBody();
/* 1580 */                       _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 1581 */                       _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                     }
/* 1583 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/* 1585 */                       out.write("\n\t  ");
/* 1586 */                       if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                         return;
/* 1588 */                       out.write("<br>\n\t  ");
/* 1589 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 1590 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/* 1591 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1594 */                     if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1595 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1598 */                   if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 1599 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                   }
/*      */                   
/* 1602 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 1603 */                   out.write(" </td>\n\t  </tr>\n\t</table></td>\n</tr>\n");
/* 1604 */                   int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 1605 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1609 */               if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 1610 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */               }
/*      */               
/* 1613 */               this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 1614 */               out.write("\n</table>\n<script>\n\tobject=location.href;\n\t\n\tif(document.AMActionForm!=null)\n\t{\t  \n\t  if(object.match(\"EMailActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if(object.match(\"SMSActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if(object.match(\"ExecProgramActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=11\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=12\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\t  \n\t  else if(object.match(\"showLogTicket\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=6;\n\t  }\n\t  else if(object.match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=5;\n\t  }\t\n\t  else if(object.match(\"jre\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=7;\n");
/* 1615 */               out.write("\t  }\n\t  else if(object.match(\"showVMAction\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=8;\n\t  }\n\t  else if(object.match(\"amazon\")!=null)\n\t  {\n\t\t    document.AMActionForm.actions.selectedIndex=9;\n\t }\t  \n\t}\n\telse if(document.MBeanOperationActionForm!=null)\n\t{\n\t  document.MBeanOperationActionForm.actions.selectedIndex=5;\t  \n\t}\n</script>\n</td>\n</tr>\n</table>\n");
/* 1616 */               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1617 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1621 */           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1622 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */           }
/*      */           
/* 1625 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1626 */           out.write(10);
/* 1627 */           out.write(10);
/*      */           
/* 1629 */           IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1630 */           _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 1631 */           _jspx_th_c_005fif_005f17.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1633 */           _jspx_th_c_005fif_005f17.setTest("${!empty param.returnpath}");
/* 1634 */           int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 1635 */           if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */             for (;;) {
/* 1637 */               out.write("\n<input name=\"returnpath\" type=\"hidden\" value=\"");
/* 1638 */               out.print(request.getParameter("returnpath"));
/* 1639 */               out.write(34);
/* 1640 */               out.write(62);
/* 1641 */               out.write(10);
/* 1642 */               int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 1643 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1647 */           if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 1648 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */           }
/*      */           
/* 1651 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 1652 */           out.write(10);
/* 1653 */           if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1655 */           out.write(10);
/* 1656 */           if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1658 */           out.write(10);
/* 1659 */           if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1661 */           out.write(10);
/* 1662 */           if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1664 */           out.write(10);
/*      */           
/* 1666 */           if (isInvokedFromPopup)
/*      */           {
/*      */ 
/* 1669 */             out.write(10);
/*      */             
/* 1671 */             org.apache.struts.taglib.logic.MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (org.apache.struts.taglib.logic.MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(org.apache.struts.taglib.logic.MessagesPresentTag.class);
/* 1672 */             _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/* 1673 */             _jspx_th_logic_005fmessagesPresent_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 1675 */             _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/* 1676 */             int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/* 1677 */             if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*      */               for (;;) {
/* 1679 */                 out.write("\n          <table width=\"99%\" border=\"0\" cellspacing=\"3\" cellpadding=\"3\" class=\"messagebox\">\n              <tr>\n                <td width=\"95%\" class=\"message\"> ");
/*      */                 
/* 1681 */                 MessagesTag _jspx_th_html_005fmessages_005f2 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1682 */                 _jspx_th_html_005fmessages_005f2.setPageContext(_jspx_page_context);
/* 1683 */                 _jspx_th_html_005fmessages_005f2.setParent(_jspx_th_logic_005fmessagesPresent_005f1);
/*      */                 
/* 1685 */                 _jspx_th_html_005fmessages_005f2.setId("msg");
/*      */                 
/* 1687 */                 _jspx_th_html_005fmessages_005f2.setMessage("true");
/* 1688 */                 int _jspx_eval_html_005fmessages_005f2 = _jspx_th_html_005fmessages_005f2.doStartTag();
/* 1689 */                 if (_jspx_eval_html_005fmessages_005f2 != 0) {
/* 1690 */                   String msg = null;
/* 1691 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1692 */                     out = _jspx_page_context.pushBody();
/* 1693 */                     _jspx_th_html_005fmessages_005f2.setBodyContent((BodyContent)out);
/* 1694 */                     _jspx_th_html_005fmessages_005f2.doInitBody();
/*      */                   }
/* 1696 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                   for (;;) {
/* 1698 */                     out.write("\n                  <li>");
/* 1699 */                     if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_html_005fmessages_005f2, _jspx_page_context))
/*      */                       return;
/* 1701 */                     out.write("</li>\n                  ");
/* 1702 */                     int evalDoAfterBody = _jspx_th_html_005fmessages_005f2.doAfterBody();
/* 1703 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/* 1704 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1707 */                   if (_jspx_eval_html_005fmessages_005f2 != 1) {
/* 1708 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1711 */                 if (_jspx_th_html_005fmessages_005f2.doEndTag() == 5) {
/* 1712 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2); return;
/*      */                 }
/*      */                 
/* 1715 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f2);
/* 1716 */                 out.write(" </td>\n              </tr>\n            </table>\n            <br>\n");
/* 1717 */                 int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/* 1718 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1722 */             if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/* 1723 */               this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1); return;
/*      */             }
/*      */             
/* 1726 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/* 1727 */             out.write(32);
/* 1728 */             out.write(10);
/*      */           }
/*      */           
/*      */ 
/* 1732 */           out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"70%\" valign=\"top\">\n<div id=\"actionmessage\" style=\"display:none\"  class='error-text'>\n</div>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n  <td width=\"2%\" class=\"tableheading-monitor-config \" align=\"right\"><img src=\"/images/icon_vm_actions.gif\" class=\"tableheading-add-icon\" style=\"position:relative;\"></td>\n\t");
/* 1733 */           if (request.getParameter("actionID") == null) {
/* 1734 */             out.write("\n    <td width=\"98%\" height=\"31\" class=\"tableheading-monitor-config\"><span id=\"tit3\"> ");
/* 1735 */             out.print(isContainerAction ? FormatUtil.getString("am.docker.action.create.text") : FormatUtil.getString("am.vm.action.createvm"));
/* 1736 */             out.write("</span></td>\n\n \t");
/*      */           } else {
/* 1738 */             out.write("\n    <td width=\"98%\" height=\"31\" class=\"tableheading-monitor-config\"><span id=\"tit3\"> ");
/* 1739 */             out.print(isContainerAction ? FormatUtil.getString("am.docker.action.edit.text") : FormatUtil.getString("am.vm.action.editvm"));
/* 1740 */             out.write("</span></td>\n \t");
/*      */           }
/* 1742 */           out.write("\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"lrborder\">\n  <tr>\n    <td width=\"25%\" class=\"bodytext label-align\">");
/* 1743 */           out.print(FormatUtil.getString("am.webclient.newaction.displayname"));
/* 1744 */           out.write("</td>\n    <td  class=\"bodytext\" colspan=\"2\"> ");
/* 1745 */           if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1747 */           out.write("</td>\n  </tr>\n\n <tr>\n ");
/* 1748 */           if (!isContainerAction) {
/* 1749 */             out.write("\n <td width=\"25%\" class=\"bodytext label-align\">");
/* 1750 */             out.print(FormatUtil.getString("am.vm.select.hosttype"));
/* 1751 */             out.write("</td>\n \t  <td class=\"bodytext\" colspan=\"2\" valign=\"middle\">\n \t  ");
/* 1752 */             if (osName.isWindows()) {
/* 1753 */               out.write("\n \t  ");
/* 1754 */               if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/* 1756 */               out.write("&nbsp;");
/* 1757 */               out.print(FormatUtil.getString("am.vm.host.esx"));
/* 1758 */               out.write(" &nbsp;&nbsp;\n \t  ");
/* 1759 */               if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/* 1761 */               out.write("&nbsp;");
/* 1762 */               out.print(FormatUtil.getString("am.vm.host.hyperv"));
/* 1763 */               out.write(" &nbsp;&nbsp;\n \t  ");
/* 1764 */               if (_jspx_meth_html_005fradio_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/* 1766 */               out.write("&nbsp;");
/* 1767 */               out.print(FormatUtil.getString("am.vm.host.xenserver"));
/* 1768 */               out.write(" &nbsp;&nbsp;\n \t  ");
/*      */             } else {
/* 1770 */               out.write("\n \t  ");
/* 1771 */               if (_jspx_meth_html_005fradio_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/* 1773 */               out.write("&nbsp;");
/* 1774 */               out.print(FormatUtil.getString("am.vm.host.esx"));
/* 1775 */               out.write(" &nbsp;&nbsp;\n \t  ");
/* 1776 */               if (_jspx_meth_html_005fradio_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/* 1778 */               out.write("&nbsp;");
/* 1779 */               out.print(FormatUtil.getString("am.vm.host.hyperv"));
/* 1780 */               out.write("&nbsp;(");
/* 1781 */               out.print(FormatUtil.getString("am.vm.linuxdisabled.buttonoption"));
/* 1782 */               out.write(") &nbsp;&nbsp;\n \t  ");
/* 1783 */               if (_jspx_meth_html_005fradio_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/* 1785 */               out.write("&nbsp;");
/* 1786 */               out.print(FormatUtil.getString("am.vm.host.xenserver"));
/* 1787 */               out.write(" &nbsp;&nbsp;\n \t  ");
/*      */             }
/* 1789 */             out.write("\n \t  \t</tr>\n \t");
/*      */           } else {
/* 1791 */             out.write("\n \t\t\t");
/* 1792 */             if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1794 */             out.write(10);
/* 1795 */             out.write(32);
/* 1796 */             out.write(9);
/*      */           }
/* 1798 */           out.write("  \t\n  \t<tr>\n<td width=\"25%\" class=\"bodytext label-align\">");
/* 1799 */           out.print(FormatUtil.getString("am.javaruntime.action.selecttype"));
/* 1800 */           out.write("</td>\n\t  <td class=\"bodytext\" colspan=\"2\" valign=\"middle\">\n\n\t  ");
/* 1801 */           if (_jspx_meth_html_005fradio_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1803 */           out.write("&nbsp;");
/* 1804 */           out.print(isContainerAction ? FormatUtil.getString("am.docker.container.action.start") : FormatUtil.getString("am.vm.action.startvm"));
/* 1805 */           out.write(" &nbsp;&nbsp;\n\t  ");
/* 1806 */           if (_jspx_meth_html_005fradio_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1808 */           out.write("&nbsp;");
/* 1809 */           out.print(isContainerAction ? FormatUtil.getString("am.docker.container.action.stop") : FormatUtil.getString("am.vm.action.stopvm"));
/* 1810 */           out.write(" &nbsp;&nbsp;\n\t  ");
/* 1811 */           if (_jspx_meth_html_005fradio_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1813 */           out.write("&nbsp;");
/* 1814 */           out.print(isContainerAction ? FormatUtil.getString("am.docker.container.action.restart") : FormatUtil.getString("am.vm.action.restartvm"));
/* 1815 */           out.write(" </td>\n\t</tr>\n\n<tr>\n\t  <td class=\"bodytext label-align\" width=\"25%\" valign=\"top\" style=\"padding-top:10px;\" >");
/* 1816 */           out.print(isContainerAction ? FormatUtil.getString("am.docker.action.select.target.text") : FormatUtil.getString("am.vm.action.targetvm"));
/* 1817 */           out.write("</td>\n\t  <td colspan=\"2\" align=\"left\">\n\t  \t<div id=\"dummy\" style=\"display:none\">\n\t  \t\t<!-- dummy combo box supporting esx host type. -->\n  \t\t\t<select name=\"esxHostOptions\">\n  \t\t\t\t<option value=\"2\">");
/* 1818 */           out.print(FormatUtil.getString("am.vm.action.type2"));
/* 1819 */           out.write("</option>\n\t\t\t\t<option value=\"3\">");
/* 1820 */           out.print(FormatUtil.getString("am.vm.action.type3"));
/* 1821 */           out.write("</option>\n\t\t\t\t<option value=\"4\">");
/* 1822 */           out.print(FormatUtil.getString("am.vm.action.type4"));
/* 1823 */           out.write("</option>\n\t\t\t</select>\n\n\t\t\t<!-- dummy combo box supporting hyperv host type. -->\n\t\t\t<select name=\"hypervHostoptions\">\n\t\t\t\t<option value=\"2\">");
/* 1824 */           out.print(FormatUtil.getString("am.vm.action.type2"));
/* 1825 */           out.write("</option>\n\t\t\t\t<option value=\"5\">");
/* 1826 */           out.print(FormatUtil.getString("am.vm.action.type5"));
/* 1827 */           out.write("</option>\n\t\t\t\t<option value=\"4\">");
/* 1828 */           out.print(FormatUtil.getString("am.vm.action.type4"));
/* 1829 */           out.write("</option>\n\t\t\t</select>\n\t\t\t<select name=\"xenHostOptions\">\n\t\t\t\t<option value=\"2\">");
/* 1830 */           out.print(FormatUtil.getString("am.vm.action.type2"));
/* 1831 */           out.write("</option>\n\t\t\t\t<option value=\"6\">");
/* 1832 */           out.print(FormatUtil.getString("am.vm.action.type6"));
/* 1833 */           out.write("</option>\n\t\t\t\t<option value=\"4\">");
/* 1834 */           out.print(FormatUtil.getString("am.vm.action.type4"));
/* 1835 */           out.write("</option>\n\t\t\t</select>\n\t\t\t<select name=\"containerOptions\">\n\t\t\t\t<option value=\"7\">");
/* 1836 */           out.print(FormatUtil.getString("am.docker.container.action.select.mg"));
/* 1837 */           out.write("</option>\n\t\t\t\t<option value=\"8\">");
/* 1838 */           out.print(FormatUtil.getString("am.docker.container.action.select.docker"));
/* 1839 */           out.write("</option>\n\t\t\t\t<option value=\"9\">");
/* 1840 */           out.print(FormatUtil.getString("am.docker.container.action.select.container"));
/* 1841 */           out.write("</option>\n\t\t\t</select>\n\t  \t</div>\n\n\t\t\t\t \t");
/*      */           
/* 1843 */           SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 1844 */           _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 1845 */           _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1847 */           _jspx_th_html_005fselect_005f0.setProperty("logConfig");
/*      */           
/* 1849 */           _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */           
/* 1851 */           _jspx_th_html_005fselect_005f0.setStyle("width:242px;vertical-align:middle;");
/*      */           
/* 1853 */           _jspx_th_html_005fselect_005f0.setOnchange("javascript:getResourceForSelectionType();");
/* 1854 */           int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 1855 */           if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 1856 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1857 */               out = _jspx_page_context.pushBody();
/* 1858 */               _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 1859 */               _jspx_th_html_005fselect_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 1862 */               out.write("\n\t\t\t\t \t\t<!--");
/*      */               
/* 1864 */               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1865 */               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 1866 */               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */               
/* 1868 */               _jspx_th_html_005foption_005f0.setValue("1");
/* 1869 */               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 1870 */               if (_jspx_eval_html_005foption_005f0 != 0) {
/* 1871 */                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1872 */                   out = _jspx_page_context.pushBody();
/* 1873 */                   _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 1874 */                   _jspx_th_html_005foption_005f0.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 1877 */                   out.print(FormatUtil.getString("am.vm.action.type1"));
/* 1878 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 1879 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 1882 */                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 1883 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 1886 */               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 1887 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */               }
/*      */               
/* 1890 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 1891 */               out.write("-->\n\t\t\t\t \t\t");
/* 1892 */               if (!isContainerAction) {
/* 1893 */                 out.write("\n\t\t\t\t\t\t\t");
/*      */                 
/* 1895 */                 OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1896 */                 _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 1897 */                 _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 1899 */                 _jspx_th_html_005foption_005f1.setValue("2");
/* 1900 */                 int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 1901 */                 if (_jspx_eval_html_005foption_005f1 != 0) {
/* 1902 */                   if (_jspx_eval_html_005foption_005f1 != 1) {
/* 1903 */                     out = _jspx_page_context.pushBody();
/* 1904 */                     _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 1905 */                     _jspx_th_html_005foption_005f1.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1908 */                     out.print(FormatUtil.getString("am.vm.action.type2"));
/* 1909 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 1910 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1913 */                   if (_jspx_eval_html_005foption_005f1 != 1) {
/* 1914 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1917 */                 if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 1918 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                 }
/*      */                 
/* 1921 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 1922 */                 out.write("\n\t\t\t\t\t\t");
/*      */               } else {
/* 1924 */                 out.write("\n\t\t\t\t\t\t\t");
/*      */                 
/* 1926 */                 OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 1927 */                 _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 1928 */                 _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                 
/* 1930 */                 _jspx_th_html_005foption_005f2.setValue("7");
/* 1931 */                 int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 1932 */                 if (_jspx_eval_html_005foption_005f2 != 0) {
/* 1933 */                   if (_jspx_eval_html_005foption_005f2 != 1) {
/* 1934 */                     out = _jspx_page_context.pushBody();
/* 1935 */                     _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 1936 */                     _jspx_th_html_005foption_005f2.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1939 */                     out.print(FormatUtil.getString("am.vm.action.type2"));
/* 1940 */                     int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 1941 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1944 */                   if (_jspx_eval_html_005foption_005f2 != 1) {
/* 1945 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1948 */                 if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 1949 */                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                 }
/*      */                 
/* 1952 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 1953 */                 out.write("\n\t\t\t\t\t\t");
/*      */               }
/* 1955 */               out.write("\n\t\t\t\t \t");
/* 1956 */               int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 1957 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 1960 */             if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1961 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 1964 */           if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 1965 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */           }
/*      */           
/* 1968 */           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 1969 */           out.write("\n\n\t\t\t\t\t<!-- Add corresponding options. -->\n\t\t\t\t\t");
/* 1970 */           if (_jspx_meth_logic_005fequal_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1972 */           out.write("\n\t\t\t\t\t\t");
/* 1973 */           if (_jspx_meth_logic_005fequal_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1975 */           out.write("\n\t\t\t\t\t");
/* 1976 */           if (_jspx_meth_logic_005fequal_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1978 */           out.write("\n\t\t\t\t\t");
/* 1979 */           if (_jspx_meth_logic_005fequal_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 1981 */           out.write("\n\t\t\t\t </td>\n  \t\t\t</tr>\n  \t\t\t");
/*      */           
/* 1983 */           com.adventnet.appmanager.struts.form.AMActionForm fm = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/*      */           
/* 1985 */           out.write("\n\n\n<tr id=\"mg\" style=\"display:none\">\n\n\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 1986 */           out.print(FormatUtil.getString("am.reporttab.selectmg.text"));
/* 1987 */           out.write("\n\t\t\t</td>\n\t\t\t <td colspan=\"2\" align=\"left\">\n\t\t\t<select name=\"selectedMG\"  class=\"formtext default\">\n\t\t\t\t ");
/*      */           
/* 1989 */           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 1990 */           _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 1991 */           _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 1993 */           _jspx_th_logic_005fnotEmpty_005f0.setName("applications");
/* 1994 */           int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 1995 */           if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */             for (;;) {
/* 1997 */               out.write("\n\t\t\t\t     ");
/*      */               
/* 1999 */               org.apache.struts.taglib.logic.IterateTag _jspx_th_logic_005fiterate_005f0 = (org.apache.struts.taglib.logic.IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(org.apache.struts.taglib.logic.IterateTag.class);
/* 2000 */               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2001 */               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */               
/* 2003 */               _jspx_th_logic_005fiterate_005f0.setName("applications");
/*      */               
/* 2005 */               _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */               
/* 2007 */               _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */               
/* 2009 */               _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2010 */               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2011 */               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2012 */                 java.util.ArrayList row = null;
/* 2013 */                 Integer j = null;
/* 2014 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2015 */                   out = _jspx_page_context.pushBody();
/* 2016 */                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2017 */                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                 }
/* 2019 */                 row = (java.util.ArrayList)_jspx_page_context.findAttribute("row");
/* 2020 */                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                 for (;;) {
/* 2022 */                   out.write("\n\t\t\t\t              ");
/*      */                   
/* 2024 */                   String selected = "";
/* 2025 */                   String currentmg = (String)row.get(1);
/* 2026 */                   String selectmg = fm.getSelectedMG();
/* 2027 */                   if ((selectmg != null) && (selectmg.equals(currentmg)))
/*      */                   {
/* 2029 */                     selected = "selected=\"selected\"";
/*      */                   }
/*      */                   
/* 2032 */                   out.write("\n\t\t\t\t \t      <option value=\"");
/* 2033 */                   out.print((String)row.get(1));
/* 2034 */                   out.write(34);
/* 2035 */                   out.write(32);
/* 2036 */                   out.print(selected);
/* 2037 */                   out.write(62);
/* 2038 */                   out.print(row.get(0));
/* 2039 */                   out.write("</option>\n\t\t\t\t     ");
/* 2040 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2041 */                   row = (java.util.ArrayList)_jspx_page_context.findAttribute("row");
/* 2042 */                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 2043 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 2046 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2047 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 2050 */               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2051 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */               }
/*      */               
/* 2054 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2055 */               out.write("\n\t\t\t\t ");
/* 2056 */               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2057 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2061 */           if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2062 */             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */           }
/*      */           
/* 2065 */           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2066 */           out.write("\n      \t     </select>\n\n\n\t\t\t   </td>\n\n\n\n\t</tr>\n\n\t<tr id=\"host\" style=\"display:none\">\n\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 2067 */           out.print(isContainerAction ? FormatUtil.getString("am.docker.container.action.select.alert.text") : FormatUtil.getString("am.vm.action.select.esxhost"));
/* 2068 */           out.write("\n\t\t\t&nbsp;\n\t\t\t</td>\n\t\t\t <td colspan=\"2\" align=\"left\">\n\t\t\t   ");
/* 2069 */           if (_jspx_meth_logic_005fnotEmpty_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2071 */           out.write("\n\n\n\t\t\t\t");
/*      */           
/* 2073 */           EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2074 */           _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2075 */           _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2077 */           _jspx_th_logic_005fempty_005f0.setName("hostlist");
/* 2078 */           int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2079 */           if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */             for (;;) {
/* 2081 */               out.write("\n\t\t\t\t\t <input type=\"hidden\" name=\"selectedhost\" value=\"\">\n\t\t\t\t\t <span class=\"bodytext\">");
/* 2082 */               out.print(isContainerAction ? FormatUtil.getString("am.docker.container.action.not.available.text") : FormatUtil.getString("am.vm.action.noesx"));
/* 2083 */               out.write("</span>\n\t\t\t\t");
/* 2084 */               int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2085 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2089 */           if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2090 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */           }
/*      */           
/* 2093 */           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2094 */           out.write("\n\t\t\t   </td>\n\n\t</tr>\n\t<tr id=\"hyperVVMs\" style=\"display:none\">\n\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 2095 */           out.print(FormatUtil.getString("am.vm.action.selectvm"));
/* 2096 */           out.write("\n\t\t\t</td>\n\t\t\t <td colspan=\"2\" align=\"left\">\n\t\t\t&nbsp;\n\t\t\t\t");
/* 2097 */           if (_jspx_meth_logic_005fnotEmpty_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2099 */           out.write("\n\n\t\t\t\t");
/*      */           
/* 2101 */           EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2102 */           _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 2103 */           _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2105 */           _jspx_th_logic_005fempty_005f1.setName("hypervVMList");
/* 2106 */           int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 2107 */           if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */             for (;;) {
/* 2109 */               out.write("\n\t\t\t\t<input type=\"hidden\" name=\"selectedHyperVVM\" value=\"\">\n\t\t\t\t\t<span class=\"bodytext\">");
/* 2110 */               out.print(FormatUtil.getString("am.vm.action.novm"));
/* 2111 */               out.write("</span>\n\t\t\t\t");
/* 2112 */               int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 2113 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2117 */           if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 2118 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */           }
/*      */           
/* 2121 */           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 2122 */           out.write("\n\t\t\t   </td>\n\t</tr>\n\n\t<tr id=\"xenVMs\" style=\"display:none\">\n\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\" align=\"left\">");
/* 2123 */           out.print(FormatUtil.getString("am.vm.action.selectvm"));
/* 2124 */           out.write("\n\t\t\t</td>\n\t\t\t <td colspan=\"2\" align=\"left\">\n\t\t\t&nbsp;\n\t\t\t\t");
/* 2125 */           if (_jspx_meth_logic_005fnotEmpty_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2127 */           out.write("\n\n\t\t\t\t");
/*      */           
/* 2129 */           EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2130 */           _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/* 2131 */           _jspx_th_logic_005fempty_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2133 */           _jspx_th_logic_005fempty_005f2.setName("xenVMList");
/* 2134 */           int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/* 2135 */           if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */             for (;;) {
/* 2137 */               out.write("\n\t\t\t\t<input type=\"hidden\" name=\"selectedXenVM\" value=\"\">\n\t\t\t\t\t<span class=\"bodytext\">");
/* 2138 */               out.print(FormatUtil.getString("am.vm.action.novm"));
/* 2139 */               out.write("</span>\n\t\t\t\t");
/* 2140 */               int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/* 2141 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2145 */           if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/* 2146 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2); return;
/*      */           }
/*      */           
/* 2149 */           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 2150 */           out.write("\n\t\t\t   </td>\n\t</tr>\n\n\t<tr id=\"jre\" style=\"display:none\">\n\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 2151 */           out.print(isContainerAction ? FormatUtil.getString("am.docker.container.action.selectContainer") : FormatUtil.getString("am.vm.action.selectvm"));
/* 2152 */           out.write("\n\t\t\t</td>\n\t\t\t <td colspan=\"2\" align=\"left\">\n\t\t\t\t");
/* 2153 */           if (_jspx_meth_logic_005fnotEmpty_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2155 */           out.write("\n\n\t\t\t\t");
/*      */           
/* 2157 */           EmptyTag _jspx_th_logic_005fempty_005f3 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2158 */           _jspx_th_logic_005fempty_005f3.setPageContext(_jspx_page_context);
/* 2159 */           _jspx_th_logic_005fempty_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2161 */           _jspx_th_logic_005fempty_005f3.setName("jrelist");
/* 2162 */           int _jspx_eval_logic_005fempty_005f3 = _jspx_th_logic_005fempty_005f3.doStartTag();
/* 2163 */           if (_jspx_eval_logic_005fempty_005f3 != 0) {
/*      */             for (;;) {
/* 2165 */               out.write("\n\t\t\t\t<input type=\"hidden\" name=\"selectedjre\" value=\"\">\n\t\t\t\t\t<span class=\"bodytext\">");
/* 2166 */               out.print(isContainerAction ? FormatUtil.getString("am.docker.container.action.not.available.containers.text") : FormatUtil.getString("am.vm.action.novm"));
/* 2167 */               out.write("</span>\n\t\t\t\t");
/* 2168 */               int evalDoAfterBody = _jspx_th_logic_005fempty_005f3.doAfterBody();
/* 2169 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2173 */           if (_jspx_th_logic_005fempty_005f3.doEndTag() == 5) {
/* 2174 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f3); return;
/*      */           }
/*      */           
/* 2177 */           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f3);
/* 2178 */           out.write("\n\t\t\t   </td>\n\t</tr>\n\n\t\t\t<tr id=\"hypervhost\" style=\"display:none\">\n\n\t\t\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 2179 */           out.print(FormatUtil.getString("am.vm.action.select.hypervhost"));
/* 2180 */           out.write("\n\t\t\t\t\t\n\t\t\t\t\t\t</td>\n\t\t\t <td colspan=\"2\" align=\"left\">\n\t\t\t\t\t\t   ");
/* 2181 */           if (_jspx_meth_logic_005fnotEmpty_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2183 */           out.write("\n\n\n\t\t\t\t\t\t\t");
/*      */           
/* 2185 */           EmptyTag _jspx_th_logic_005fempty_005f4 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2186 */           _jspx_th_logic_005fempty_005f4.setPageContext(_jspx_page_context);
/* 2187 */           _jspx_th_logic_005fempty_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2189 */           _jspx_th_logic_005fempty_005f4.setName("hypervhostlist");
/* 2190 */           int _jspx_eval_logic_005fempty_005f4 = _jspx_th_logic_005fempty_005f4.doStartTag();
/* 2191 */           if (_jspx_eval_logic_005fempty_005f4 != 0) {
/*      */             for (;;) {
/* 2193 */               out.write("\n\t\t\t\t\t\t\t\t <input type=\"hidden\" name=\"selectedhypervhost\" value=\"\">\n\t\t\t\t\t\t\t\t<span class=\"bodytext\">");
/* 2194 */               out.print(FormatUtil.getString("am.vm.action.nohyperv"));
/* 2195 */               out.write("</span>\n\t\t\t\t\t\t\t");
/* 2196 */               int evalDoAfterBody = _jspx_th_logic_005fempty_005f4.doAfterBody();
/* 2197 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2201 */           if (_jspx_th_logic_005fempty_005f4.doEndTag() == 5) {
/* 2202 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f4); return;
/*      */           }
/*      */           
/* 2205 */           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f4);
/* 2206 */           out.write("\n\t\t\t\t\t\t   </td>\n\n\t\t\t</tr>\n\t\t\t<tr id=\"xenhost\" style=\"display:none\">\n\n\t\t\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\" align=\"left\">");
/* 2207 */           out.print(FormatUtil.getString("am.vm.action.select.xenhost"));
/* 2208 */           out.write("\n\t\t\t\t\t\n\t\t\t\t\t\t</td>\n\t\t\t <td colspan=\"2\" align=\"left\">\n\t\t\t\t\t\t   ");
/* 2209 */           if (_jspx_meth_logic_005fnotEmpty_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2211 */           out.write("\n\n\n\t\t\t\t\t\t\t");
/*      */           
/* 2213 */           EmptyTag _jspx_th_logic_005fempty_005f5 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2214 */           _jspx_th_logic_005fempty_005f5.setPageContext(_jspx_page_context);
/* 2215 */           _jspx_th_logic_005fempty_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */           
/* 2217 */           _jspx_th_logic_005fempty_005f5.setName("xenHostList");
/* 2218 */           int _jspx_eval_logic_005fempty_005f5 = _jspx_th_logic_005fempty_005f5.doStartTag();
/* 2219 */           if (_jspx_eval_logic_005fempty_005f5 != 0) {
/*      */             for (;;) {
/* 2221 */               out.write("\n\t\t\t\t\t\t\t\t <input type=\"hidden\" name=\"selectedXenHost\" value=\"\">\n\t\t\t\t\t\t\t\t<span class=\"bodytext\">");
/* 2222 */               out.print(FormatUtil.getString("am.vm.action.noxenhost"));
/* 2223 */               out.write("</span>\n\t\t\t\t\t\t\t");
/* 2224 */               int evalDoAfterBody = _jspx_th_logic_005fempty_005f5.doAfterBody();
/* 2225 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2229 */           if (_jspx_th_logic_005fempty_005f5.doEndTag() == 5) {
/* 2230 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f5); return;
/*      */           }
/*      */           
/* 2233 */           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f5);
/* 2234 */           out.write("\n\t\t\t\t\t\t   </td>\n\n\t\t\t</tr>\n\n <tr>\n    <td class=\"bodytext label-align\"><span id=\"tit2\">");
/* 2235 */           out.print(FormatUtil.getString("am.vm.action.timeout"));
/* 2236 */           out.write("</span></td>\n    <td class=\"bodytext\" colspan=\"2\">");
/* 2237 */           if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2239 */           out.write("</td>\n  </tr>\n\n\n <tr>\n\t  <td class=\"bodytext label-align\" width=\"25%\">");
/* 2240 */           out.print(FormatUtil.getString("am.vm.eaction.associate"));
/* 2241 */           out.write("</td>\n\t  <td class=\"bodytext\" width=\"75%\"  colspan=\"2\">\n\t\t\t\t\t");
/* 2242 */           if (_jspx_meth_html_005fselect_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2244 */           out.write("&nbsp;&nbsp;&nbsp;<a href='javascript:callAction()' class='staticlinks'>");
/* 2245 */           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliverynewaction.text"));
/* 2246 */           out.write(" </a>\n\t  </td>\n\t  </tr>\n\t  <tr>\n\t\t  <td class=\"bodytext\" width=\"25%\"></td>\n\t\t  <td class=\"bodytext\"  width=\"75%\" valign=\"middle\"  align=\"left\">\n\t\t   <div id='takeaction' style=\"display:none;\">\n\t\t \n\t\t      <table width='100%' cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">\n\t\t      \t<tr>\n\t\t      \t\t<td class='bodytext' nowrap> ");
/* 2247 */           out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliveryemailid.text"));
/* 2248 */           out.write(32);
/* 2249 */           if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */             return;
/* 2251 */           out.write(" <input name=\"save\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"javascript:getAction();\" value=\"");
/* 2252 */           out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 2253 */           out.write("\"> <input name=\"cancel\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 2254 */           out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2255 */           out.write("\" onclick='javascript:removeAction()'> </td>\n\t\t\t\t</tr>\n\t\t\t </table>\n\t\t\t</div>\n\t\t\t</td>\n\t</tr>\n</table>\n\n\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n\t\t\t<td width=\"25%\" class=\"tablebottom\" style=\"height:30px; color:#ff0000; font-size:11px;\">* ");
/* 2256 */           out.print(FormatUtil.getString("am.webclient.newaction.trapfieldsnote"));
/* 2257 */           out.write("</td>\n            <td width=\"75%\"  class=\"tablebottom\" align=\"left\">\n\n\t          ");
/* 2258 */           if (request.getParameter("actionID") == null) {
/* 2259 */             out.write("\n\t           <input name=\"button\" value=\"");
/* 2260 */             out.print(FormatUtil.getString("am.vm.action.create.text"));
/* 2261 */             out.write("\" type=\"button\" class=\"buttons btn_highlt\"  onClick=\"javascript:validateAndSubmit()\">\n\t           ");
/*      */           } else {
/* 2263 */             out.write("\n\t           <input name=\"button\" value=\"");
/* 2264 */             out.print(FormatUtil.getString("am.vm.action.update.text"));
/* 2265 */             out.write("\" type=\"button\" class=\"buttons btn_highlt\"  onClick=\"javascript:validateAndSubmit()\">\n\t           ");
/*      */           }
/* 2267 */           out.write("\n\n\n            <input name=\"button1\" type=\"button\" class=\"buttons btn_reset\" value=\"");
/* 2268 */           out.print(FormatUtil.getString("am.webclient.newaction.restoredefaults"));
/* 2269 */           out.write("\" onClick=\"javascript:restvalue()\">\n\n      &nbsp;<input name=\"button2\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 2270 */           out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2271 */           out.write("\" onClick=\"javascript:history.back()\">\n\n\t</td>\n          </tr>\n        </table>\n        </td>\n        <td width=\"30%\" valign=\"top\">\n        \t");
/* 2272 */           org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(isContainerAction ? FormatUtil.getString("am.container.action.help") : FormatUtil.getString("am.vm.action.help")), request.getCharacterEncoding()), out, false);
/* 2273 */           out.write("\n\t\t</td>\n        </tr>\n        </table>\n    \n   ");
/* 2274 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2275 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2279 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2280 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */       }
/*      */       else {
/* 2283 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2284 */         out.write("\n\n<script>\ngetResourceForSelectionType()\n\n</script>\n");
/*      */       }
/* 2286 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2287 */         out = _jspx_out;
/* 2288 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2289 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2290 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2293 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2299 */     PageContext pageContext = _jspx_page_context;
/* 2300 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2302 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2303 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2304 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2306 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2308 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2309 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2310 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2311 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2312 */       return true;
/*      */     }
/* 2314 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2315 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2320 */     PageContext pageContext = _jspx_page_context;
/* 2321 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2323 */     org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f0 = (org.apache.struts.taglib.logic.PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(org.apache.struts.taglib.logic.PresentTag.class);
/* 2324 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2325 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 2327 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2328 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2329 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2331 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t\t");
/* 2332 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2333 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2337 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2338 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2339 */       return true;
/*      */     }
/* 2341 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2342 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2347 */     PageContext pageContext = _jspx_page_context;
/* 2348 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2350 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2351 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2352 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2354 */     _jspx_th_c_005fif_005f0.setTest("${globalconfig['mailserverconfigured'] != 'true'}");
/* 2355 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2356 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 2358 */         out.write("\n\t\t\tmyOnLoad();\n\t\t");
/* 2359 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2360 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2364 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2365 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2366 */       return true;
/*      */     }
/* 2368 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2369 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2374 */     PageContext pageContext = _jspx_page_context;
/* 2375 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2377 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2378 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2379 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2381 */     _jspx_th_c_005fif_005f1.setTest("${globalconfig['mailserverconfigured'] != 'true' && param.checkForMailSetting eq 'true'}");
/* 2382 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2383 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2385 */         out.write("\n\t\t\t\tmyOnLoad();\n\t\t\t");
/* 2386 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2387 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2391 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2392 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2393 */       return true;
/*      */     }
/* 2395 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2396 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2401 */     PageContext pageContext = _jspx_page_context;
/* 2402 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2404 */     org.apache.taglibs.standard.tag.common.core.CatchTag _jspx_th_c_005fcatch_005f0 = (org.apache.taglibs.standard.tag.common.core.CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(org.apache.taglibs.standard.tag.common.core.CatchTag.class);
/* 2405 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 2406 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 2408 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 2409 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 2411 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 2412 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 2414 */           out.write(" \n      ");
/* 2415 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 2416 */             return true;
/* 2417 */           out.write(32);
/* 2418 */           out.write(10);
/* 2419 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 2420 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2424 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 2425 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2428 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 2429 */         out = _jspx_page_context.popBody(); }
/* 2430 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 2432 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 2433 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 2435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 2440 */     PageContext pageContext = _jspx_page_context;
/* 2441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2443 */     org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag.class);
/* 2444 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 2445 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 2447 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 2449 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 2450 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 2451 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 2452 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2453 */       return true;
/*      */     }
/* 2455 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 2456 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2461 */     PageContext pageContext = _jspx_page_context;
/* 2462 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2464 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 2465 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 2466 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2468 */     _jspx_th_c_005fset_005f5.setVar("wiz");
/*      */     
/* 2470 */     _jspx_th_c_005fset_005f5.setValue("${param.wiz}");
/*      */     
/* 2472 */     _jspx_th_c_005fset_005f5.setScope("request");
/* 2473 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 2474 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 2475 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 2476 */       return true;
/*      */     }
/* 2478 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 2479 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2484 */     PageContext pageContext = _jspx_page_context;
/* 2485 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2487 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2488 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2489 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2491 */     _jspx_th_c_005fout_005f1.setValue("${wizimage}");
/*      */     
/* 2493 */     _jspx_th_c_005fout_005f1.setEscapeXml("false");
/* 2494 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2495 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2496 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2497 */       return true;
/*      */     }
/* 2499 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2500 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2505 */     PageContext pageContext = _jspx_page_context;
/* 2506 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2508 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2509 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2510 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2512 */     _jspx_th_c_005fif_005f9.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 2513 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2514 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 2516 */         out.write("\n    <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 2517 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f9, _jspx_page_context))
/* 2518 */           return true;
/* 2519 */         out.write("&wiz=true\">\n    ");
/* 2520 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2521 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2525 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2526 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2527 */       return true;
/*      */     }
/* 2529 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2530 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2535 */     PageContext pageContext = _jspx_page_context;
/* 2536 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2538 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2539 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2540 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 2542 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 2543 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2544 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2545 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2546 */       return true;
/*      */     }
/* 2548 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2554 */     PageContext pageContext = _jspx_page_context;
/* 2555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2557 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2558 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2559 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2561 */     _jspx_th_c_005fout_005f3.setValue("${wizimage}");
/*      */     
/* 2563 */     _jspx_th_c_005fout_005f3.setEscapeXml("false");
/* 2564 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2565 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2566 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2567 */       return true;
/*      */     }
/* 2569 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2570 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2575 */     PageContext pageContext = _jspx_page_context;
/* 2576 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2578 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2579 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2580 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2582 */     _jspx_th_c_005fif_005f10.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 2583 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2584 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 2586 */         out.write("\n    \t</a>\n    \t");
/* 2587 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2588 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2592 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2593 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2594 */       return true;
/*      */     }
/* 2596 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2597 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2602 */     PageContext pageContext = _jspx_page_context;
/* 2603 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2605 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2606 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2607 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2609 */     _jspx_th_c_005fif_005f11.setTest("${wizimage=='/images/new_high.gif'}");
/* 2610 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2611 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 2613 */         out.write("\n       <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 2614 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f11, _jspx_page_context))
/* 2615 */           return true;
/* 2616 */         out.write("&wiz=true\">\n       ");
/* 2617 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2618 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2622 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2623 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2624 */       return true;
/*      */     }
/* 2626 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2627 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2632 */     PageContext pageContext = _jspx_page_context;
/* 2633 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2635 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2636 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2637 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 2639 */     _jspx_th_c_005fout_005f4.setValue("${param.haid}");
/* 2640 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2641 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2642 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2643 */       return true;
/*      */     }
/* 2645 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2646 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2651 */     PageContext pageContext = _jspx_page_context;
/* 2652 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2654 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2655 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2656 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2658 */     _jspx_th_c_005fout_005f5.setValue("${wizimage}");
/*      */     
/* 2660 */     _jspx_th_c_005fout_005f5.setEscapeXml("false");
/* 2661 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2662 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2663 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2664 */       return true;
/*      */     }
/* 2666 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2667 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2672 */     PageContext pageContext = _jspx_page_context;
/* 2673 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2675 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2676 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2677 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2679 */     _jspx_th_c_005fif_005f12.setTest("${wizimage=='/images/new_high.gif'}");
/* 2680 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 2681 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 2683 */         out.write("\n       \t</a>\n       \t");
/* 2684 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 2685 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2689 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 2690 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2691 */       return true;
/*      */     }
/* 2693 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 2694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2699 */     PageContext pageContext = _jspx_page_context;
/* 2700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2702 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2703 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 2704 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2706 */     _jspx_th_c_005fif_005f15.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 2707 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 2708 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */       for (;;) {
/* 2710 */         out.write("\t\n    <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 2711 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f15, _jspx_page_context))
/* 2712 */           return true;
/* 2713 */         out.write("&wiz=true\">\n ");
/* 2714 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 2715 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2719 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 2720 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 2721 */       return true;
/*      */     }
/* 2723 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 2724 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2729 */     PageContext pageContext = _jspx_page_context;
/* 2730 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2732 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2733 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2734 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 2736 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 2737 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2738 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2739 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2740 */       return true;
/*      */     }
/* 2742 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2743 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2748 */     PageContext pageContext = _jspx_page_context;
/* 2749 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2751 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 2752 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2753 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2755 */     _jspx_th_c_005fout_005f7.setValue("${wizimage}");
/*      */     
/* 2757 */     _jspx_th_c_005fout_005f7.setEscapeXml("false");
/* 2758 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2759 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2760 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2761 */       return true;
/*      */     }
/* 2763 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2769 */     PageContext pageContext = _jspx_page_context;
/* 2770 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2772 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2773 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 2774 */     _jspx_th_c_005fif_005f16.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2776 */     _jspx_th_c_005fif_005f16.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 2777 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 2778 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 2780 */         out.write("\t    \n    </a>\n ");
/* 2781 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 2782 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2786 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 2787 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2788 */       return true;
/*      */     }
/* 2790 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 2791 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2796 */     PageContext pageContext = _jspx_page_context;
/* 2797 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2799 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2800 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 2801 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 2803 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 2805 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 2806 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 2807 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 2808 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2809 */       return true;
/*      */     }
/* 2811 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 2812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2817 */     PageContext pageContext = _jspx_page_context;
/* 2818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2820 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 2821 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 2822 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 2824 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 2826 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 2827 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 2828 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 2829 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2830 */       return true;
/*      */     }
/* 2832 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 2833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2838 */     PageContext pageContext = _jspx_page_context;
/* 2839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2841 */     com.adventnet.appmanager.tags.HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (com.adventnet.appmanager.tags.HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(com.adventnet.appmanager.tags.HiddenParam.class);
/* 2842 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 2843 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2845 */     _jspx_th_am_005fhiddenparam_005f0.setName("haid");
/* 2846 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 2847 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 2848 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 2849 */       return true;
/*      */     }
/* 2851 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 2852 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2857 */     PageContext pageContext = _jspx_page_context;
/* 2858 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2860 */     com.adventnet.appmanager.tags.HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (com.adventnet.appmanager.tags.HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(com.adventnet.appmanager.tags.HiddenParam.class);
/* 2861 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/* 2862 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2864 */     _jspx_th_am_005fhiddenparam_005f1.setName("redirectTo");
/* 2865 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/* 2866 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/* 2867 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 2868 */       return true;
/*      */     }
/* 2870 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 2871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2876 */     PageContext pageContext = _jspx_page_context;
/* 2877 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2879 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 2880 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 2881 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2883 */     _jspx_th_html_005fhidden_005f0.setProperty("method");
/* 2884 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 2885 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 2886 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2887 */       return true;
/*      */     }
/* 2889 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 2890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2895 */     PageContext pageContext = _jspx_page_context;
/* 2896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2898 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 2899 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 2900 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2902 */     _jspx_th_html_005fhidden_005f1.setProperty("id");
/* 2903 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 2904 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 2905 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 2906 */       return true;
/*      */     }
/* 2908 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 2909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_html_005fmessages_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2914 */     PageContext pageContext = _jspx_page_context;
/* 2915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2917 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(WriteTag.class);
/* 2918 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 2919 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_html_005fmessages_005f2);
/*      */     
/* 2921 */     _jspx_th_bean_005fwrite_005f2.setName("msg");
/* 2922 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 2923 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 2924 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2925 */       return true;
/*      */     }
/* 2927 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 2928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2933 */     PageContext pageContext = _jspx_page_context;
/* 2934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2936 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2937 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 2938 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2940 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 2942 */     _jspx_th_html_005ftext_005f0.setSize("40");
/*      */     
/* 2944 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/*      */     
/* 2946 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/* 2947 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 2948 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 2949 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2950 */       return true;
/*      */     }
/* 2952 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 2953 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2958 */     PageContext pageContext = _jspx_page_context;
/* 2959 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2961 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 2962 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 2963 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2965 */     _jspx_th_html_005fradio_005f0.setProperty("hostType");
/*      */     
/* 2967 */     _jspx_th_html_005fradio_005f0.setValue("0");
/*      */     
/* 2969 */     _jspx_th_html_005fradio_005f0.setOnclick("fillOptionsOnChange()");
/* 2970 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 2971 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 2972 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 2973 */       return true;
/*      */     }
/* 2975 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 2976 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2981 */     PageContext pageContext = _jspx_page_context;
/* 2982 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2984 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 2985 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 2986 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2988 */     _jspx_th_html_005fradio_005f1.setProperty("hostType");
/*      */     
/* 2990 */     _jspx_th_html_005fradio_005f1.setValue("100");
/*      */     
/* 2992 */     _jspx_th_html_005fradio_005f1.setOnclick("fillOptionsOnChange()");
/* 2993 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 2994 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 2995 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 2996 */       return true;
/*      */     }
/* 2998 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 2999 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3004 */     PageContext pageContext = _jspx_page_context;
/* 3005 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3007 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3008 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 3009 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3011 */     _jspx_th_html_005fradio_005f2.setProperty("hostType");
/*      */     
/* 3013 */     _jspx_th_html_005fradio_005f2.setValue("700");
/*      */     
/* 3015 */     _jspx_th_html_005fradio_005f2.setOnclick("fillOptionsOnChange()");
/* 3016 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 3017 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 3018 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 3019 */       return true;
/*      */     }
/* 3021 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 3022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3027 */     PageContext pageContext = _jspx_page_context;
/* 3028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3030 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3031 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 3032 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3034 */     _jspx_th_html_005fradio_005f3.setProperty("hostType");
/*      */     
/* 3036 */     _jspx_th_html_005fradio_005f3.setValue("0");
/*      */     
/* 3038 */     _jspx_th_html_005fradio_005f3.setOnclick("fillOptionsOnChange()");
/* 3039 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 3040 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 3041 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 3042 */       return true;
/*      */     }
/* 3044 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 3045 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3050 */     PageContext pageContext = _jspx_page_context;
/* 3051 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3053 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.get(RadioTag.class);
/* 3054 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 3055 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3057 */     _jspx_th_html_005fradio_005f4.setProperty("hostType");
/*      */     
/* 3059 */     _jspx_th_html_005fradio_005f4.setValue("100");
/*      */     
/* 3061 */     _jspx_th_html_005fradio_005f4.setOnclick("fillOptionsOnChange()");
/*      */     
/* 3063 */     _jspx_th_html_005fradio_005f4.setDisabled(true);
/* 3064 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 3065 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 3066 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 3067 */       return true;
/*      */     }
/* 3069 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 3070 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3075 */     PageContext pageContext = _jspx_page_context;
/* 3076 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3078 */     RadioTag _jspx_th_html_005fradio_005f5 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3079 */     _jspx_th_html_005fradio_005f5.setPageContext(_jspx_page_context);
/* 3080 */     _jspx_th_html_005fradio_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3082 */     _jspx_th_html_005fradio_005f5.setProperty("hostType");
/*      */     
/* 3084 */     _jspx_th_html_005fradio_005f5.setValue("700");
/*      */     
/* 3086 */     _jspx_th_html_005fradio_005f5.setOnclick("fillOptionsOnChange()");
/* 3087 */     int _jspx_eval_html_005fradio_005f5 = _jspx_th_html_005fradio_005f5.doStartTag();
/* 3088 */     if (_jspx_th_html_005fradio_005f5.doEndTag() == 5) {
/* 3089 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 3090 */       return true;
/*      */     }
/* 3092 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 3093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3098 */     PageContext pageContext = _jspx_page_context;
/* 3099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3101 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 3102 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 3103 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3105 */     _jspx_th_html_005fhidden_005f2.setProperty("hostType");
/* 3106 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 3107 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 3108 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 3109 */       return true;
/*      */     }
/* 3111 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 3112 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3117 */     PageContext pageContext = _jspx_page_context;
/* 3118 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3120 */     RadioTag _jspx_th_html_005fradio_005f6 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3121 */     _jspx_th_html_005fradio_005f6.setPageContext(_jspx_page_context);
/* 3122 */     _jspx_th_html_005fradio_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3124 */     _jspx_th_html_005fradio_005f6.setProperty("jtaskMethod");
/*      */     
/* 3126 */     _jspx_th_html_005fradio_005f6.setValue("StartVM");
/* 3127 */     int _jspx_eval_html_005fradio_005f6 = _jspx_th_html_005fradio_005f6.doStartTag();
/* 3128 */     if (_jspx_th_html_005fradio_005f6.doEndTag() == 5) {
/* 3129 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 3130 */       return true;
/*      */     }
/* 3132 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 3133 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3138 */     PageContext pageContext = _jspx_page_context;
/* 3139 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3141 */     RadioTag _jspx_th_html_005fradio_005f7 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3142 */     _jspx_th_html_005fradio_005f7.setPageContext(_jspx_page_context);
/* 3143 */     _jspx_th_html_005fradio_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3145 */     _jspx_th_html_005fradio_005f7.setProperty("jtaskMethod");
/*      */     
/* 3147 */     _jspx_th_html_005fradio_005f7.setValue("StopVM");
/* 3148 */     int _jspx_eval_html_005fradio_005f7 = _jspx_th_html_005fradio_005f7.doStartTag();
/* 3149 */     if (_jspx_th_html_005fradio_005f7.doEndTag() == 5) {
/* 3150 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 3151 */       return true;
/*      */     }
/* 3153 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 3154 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3159 */     PageContext pageContext = _jspx_page_context;
/* 3160 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3162 */     RadioTag _jspx_th_html_005fradio_005f8 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3163 */     _jspx_th_html_005fradio_005f8.setPageContext(_jspx_page_context);
/* 3164 */     _jspx_th_html_005fradio_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3166 */     _jspx_th_html_005fradio_005f8.setProperty("jtaskMethod");
/*      */     
/* 3168 */     _jspx_th_html_005fradio_005f8.setValue("RestartVM");
/* 3169 */     int _jspx_eval_html_005fradio_005f8 = _jspx_th_html_005fradio_005f8.doStartTag();
/* 3170 */     if (_jspx_th_html_005fradio_005f8.doEndTag() == 5) {
/* 3171 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 3172 */       return true;
/*      */     }
/* 3174 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 3175 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3180 */     PageContext pageContext = _jspx_page_context;
/* 3181 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3183 */     EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 3184 */     _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/* 3185 */     _jspx_th_logic_005fequal_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3187 */     _jspx_th_logic_005fequal_005f0.setName("AMActionForm");
/*      */     
/* 3189 */     _jspx_th_logic_005fequal_005f0.setProperty("hostType");
/*      */     
/* 3191 */     _jspx_th_logic_005fequal_005f0.setValue("0");
/* 3192 */     int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/* 3193 */     if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */       for (;;) {
/* 3195 */         out.write("\n\t\t\t\t\t\t<script>\n\t\t\t\t\t\t\tfillOptions(0);\n\t\t\t\t\t\t</script>\n\t\t\t\t\t");
/* 3196 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/* 3197 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3201 */     if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/* 3202 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 3203 */       return true;
/*      */     }
/* 3205 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 3206 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3211 */     PageContext pageContext = _jspx_page_context;
/* 3212 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3214 */     EqualTag _jspx_th_logic_005fequal_005f1 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 3215 */     _jspx_th_logic_005fequal_005f1.setPageContext(_jspx_page_context);
/* 3216 */     _jspx_th_logic_005fequal_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3218 */     _jspx_th_logic_005fequal_005f1.setName("AMActionForm");
/*      */     
/* 3220 */     _jspx_th_logic_005fequal_005f1.setProperty("hostType");
/*      */     
/* 3222 */     _jspx_th_logic_005fequal_005f1.setValue("100");
/* 3223 */     int _jspx_eval_logic_005fequal_005f1 = _jspx_th_logic_005fequal_005f1.doStartTag();
/* 3224 */     if (_jspx_eval_logic_005fequal_005f1 != 0) {
/*      */       for (;;) {
/* 3226 */         out.write("\n\t\t\t\t\t\t\t<script>\n\t\t\t\t\t\t\tfillOptions(100);\n\t\t\t\t\t\t</script>\n\t\t\t\t\t");
/* 3227 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f1.doAfterBody();
/* 3228 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3232 */     if (_jspx_th_logic_005fequal_005f1.doEndTag() == 5) {
/* 3233 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 3234 */       return true;
/*      */     }
/* 3236 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 3237 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3242 */     PageContext pageContext = _jspx_page_context;
/* 3243 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3245 */     EqualTag _jspx_th_logic_005fequal_005f2 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 3246 */     _jspx_th_logic_005fequal_005f2.setPageContext(_jspx_page_context);
/* 3247 */     _jspx_th_logic_005fequal_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3249 */     _jspx_th_logic_005fequal_005f2.setName("AMActionForm");
/*      */     
/* 3251 */     _jspx_th_logic_005fequal_005f2.setProperty("hostType");
/*      */     
/* 3253 */     _jspx_th_logic_005fequal_005f2.setValue("700");
/* 3254 */     int _jspx_eval_logic_005fequal_005f2 = _jspx_th_logic_005fequal_005f2.doStartTag();
/* 3255 */     if (_jspx_eval_logic_005fequal_005f2 != 0) {
/*      */       for (;;) {
/* 3257 */         out.write("\n\t\t\t\t\t\t\t<script>\n\t\t\t\t\t\t\tfillOptions(700);\n\t\t\t\t\t\t</script>\n\t\t\t\t\t");
/* 3258 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f2.doAfterBody();
/* 3259 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3263 */     if (_jspx_th_logic_005fequal_005f2.doEndTag() == 5) {
/* 3264 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2);
/* 3265 */       return true;
/*      */     }
/* 3267 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2);
/* 3268 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3273 */     PageContext pageContext = _jspx_page_context;
/* 3274 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3276 */     EqualTag _jspx_th_logic_005fequal_005f3 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 3277 */     _jspx_th_logic_005fequal_005f3.setPageContext(_jspx_page_context);
/* 3278 */     _jspx_th_logic_005fequal_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3280 */     _jspx_th_logic_005fequal_005f3.setName("AMActionForm");
/*      */     
/* 3282 */     _jspx_th_logic_005fequal_005f3.setProperty("hostType");
/*      */     
/* 3284 */     _jspx_th_logic_005fequal_005f3.setValue("850");
/* 3285 */     int _jspx_eval_logic_005fequal_005f3 = _jspx_th_logic_005fequal_005f3.doStartTag();
/* 3286 */     if (_jspx_eval_logic_005fequal_005f3 != 0) {
/*      */       for (;;) {
/* 3288 */         out.write("\n\t\t\t\t\t\t<script>\n\t\t\t\t\t\t\tfillOptions(850);\n\t\t\t\t\t\t</script>\n\t\t\t\t\t");
/* 3289 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f3.doAfterBody();
/* 3290 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3294 */     if (_jspx_th_logic_005fequal_005f3.doEndTag() == 5) {
/* 3295 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f3);
/* 3296 */       return true;
/*      */     }
/* 3298 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f3);
/* 3299 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3304 */     PageContext pageContext = _jspx_page_context;
/* 3305 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3307 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3308 */     _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3309 */     _jspx_th_logic_005fnotEmpty_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3311 */     _jspx_th_logic_005fnotEmpty_005f1.setName("hostlist");
/* 3312 */     int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3313 */     if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */       for (;;) {
/* 3315 */         out.write("\n\t\t\t\t\t\t");
/* 3316 */         if (_jspx_meth_html_005fselect_005f1(_jspx_th_logic_005fnotEmpty_005f1, _jspx_page_context))
/* 3317 */           return true;
/* 3318 */         out.write("\n\t\t\t\t ");
/* 3319 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3320 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3324 */     if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3325 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3326 */       return true;
/*      */     }
/* 3328 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3329 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_logic_005fnotEmpty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3334 */     PageContext pageContext = _jspx_page_context;
/* 3335 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3337 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3338 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 3339 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f1);
/*      */     
/* 3341 */     _jspx_th_html_005fselect_005f1.setProperty("selectedhost");
/*      */     
/* 3343 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext default");
/* 3344 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 3345 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 3346 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3347 */         out = _jspx_page_context.pushBody();
/* 3348 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 3349 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3352 */         out.write("\n\t\t\t\t\t\t");
/* 3353 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 3354 */           return true;
/* 3355 */         out.write("\n\t\t\t\t\t    ");
/* 3356 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 3357 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3360 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3361 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3364 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 3365 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 3366 */       return true;
/*      */     }
/* 3368 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 3369 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3374 */     PageContext pageContext = _jspx_page_context;
/* 3375 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3377 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3378 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 3379 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 3381 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("hostlist");
/* 3382 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 3383 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 3384 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3385 */       return true;
/*      */     }
/* 3387 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3393 */     PageContext pageContext = _jspx_page_context;
/* 3394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3396 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3397 */     _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 3398 */     _jspx_th_logic_005fnotEmpty_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3400 */     _jspx_th_logic_005fnotEmpty_005f2.setName("hypervVMList");
/* 3401 */     int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 3402 */     if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */       for (;;) {
/* 3404 */         out.write("\n\t\t\t\t\t");
/* 3405 */         if (_jspx_meth_html_005fselect_005f2(_jspx_th_logic_005fnotEmpty_005f2, _jspx_page_context))
/* 3406 */           return true;
/* 3407 */         out.write("\n\t\t\t    ");
/* 3408 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 3409 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3413 */     if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 3414 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3415 */       return true;
/*      */     }
/* 3417 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 3418 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_logic_005fnotEmpty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3423 */     PageContext pageContext = _jspx_page_context;
/* 3424 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3426 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3427 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 3428 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f2);
/*      */     
/* 3430 */     _jspx_th_html_005fselect_005f2.setProperty("selectedHyperVVM");
/*      */     
/* 3432 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext default");
/* 3433 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 3434 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 3435 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3436 */         out = _jspx_page_context.pushBody();
/* 3437 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 3438 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3441 */         out.write("\n\t\t\t\t\t");
/* 3442 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 3443 */           return true;
/* 3444 */         out.write("\n\t\t\t\t\t");
/* 3445 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 3446 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3449 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3450 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3453 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 3454 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 3455 */       return true;
/*      */     }
/* 3457 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 3458 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3463 */     PageContext pageContext = _jspx_page_context;
/* 3464 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3466 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3467 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 3468 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 3470 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("hypervVMList");
/* 3471 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 3472 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 3473 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3474 */       return true;
/*      */     }
/* 3476 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3477 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3482 */     PageContext pageContext = _jspx_page_context;
/* 3483 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3485 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3486 */     _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 3487 */     _jspx_th_logic_005fnotEmpty_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3489 */     _jspx_th_logic_005fnotEmpty_005f3.setName("xenVMList");
/* 3490 */     int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 3491 */     if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */       for (;;) {
/* 3493 */         out.write("\n\t\t\t\t\t");
/* 3494 */         if (_jspx_meth_html_005fselect_005f3(_jspx_th_logic_005fnotEmpty_005f3, _jspx_page_context))
/* 3495 */           return true;
/* 3496 */         out.write("\n\t\t\t    ");
/* 3497 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 3498 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3502 */     if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 3503 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 3504 */       return true;
/*      */     }
/* 3506 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 3507 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_logic_005fnotEmpty_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3512 */     PageContext pageContext = _jspx_page_context;
/* 3513 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3515 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 3516 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 3517 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f3);
/*      */     
/* 3519 */     _jspx_th_html_005fselect_005f3.setProperty("selectedXenVM");
/*      */     
/* 3521 */     _jspx_th_html_005fselect_005f3.setStyleClass("formtext");
/*      */     
/* 3523 */     _jspx_th_html_005fselect_005f3.setStyle("width:25%");
/* 3524 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 3525 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 3526 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3527 */         out = _jspx_page_context.pushBody();
/* 3528 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 3529 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3532 */         out.write("\n\t\t\t\t\t");
/* 3533 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 3534 */           return true;
/* 3535 */         out.write("\n\t\t\t\t\t");
/* 3536 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 3537 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3540 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3541 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3544 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 3545 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 3546 */       return true;
/*      */     }
/* 3548 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 3549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3554 */     PageContext pageContext = _jspx_page_context;
/* 3555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3557 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3558 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 3559 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 3561 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("xenVMList");
/* 3562 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 3563 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 3564 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3565 */       return true;
/*      */     }
/* 3567 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3568 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3573 */     PageContext pageContext = _jspx_page_context;
/* 3574 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3576 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f4 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3577 */     _jspx_th_logic_005fnotEmpty_005f4.setPageContext(_jspx_page_context);
/* 3578 */     _jspx_th_logic_005fnotEmpty_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3580 */     _jspx_th_logic_005fnotEmpty_005f4.setName("jrelist");
/* 3581 */     int _jspx_eval_logic_005fnotEmpty_005f4 = _jspx_th_logic_005fnotEmpty_005f4.doStartTag();
/* 3582 */     if (_jspx_eval_logic_005fnotEmpty_005f4 != 0) {
/*      */       for (;;) {
/* 3584 */         out.write("\n\t\t\t\t\t");
/* 3585 */         if (_jspx_meth_html_005fselect_005f4(_jspx_th_logic_005fnotEmpty_005f4, _jspx_page_context))
/* 3586 */           return true;
/* 3587 */         out.write("\n\t\t\t    ");
/* 3588 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f4.doAfterBody();
/* 3589 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3593 */     if (_jspx_th_logic_005fnotEmpty_005f4.doEndTag() == 5) {
/* 3594 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 3595 */       return true;
/*      */     }
/* 3597 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 3598 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_logic_005fnotEmpty_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3603 */     PageContext pageContext = _jspx_page_context;
/* 3604 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3606 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3607 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 3608 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f4);
/*      */     
/* 3610 */     _jspx_th_html_005fselect_005f4.setProperty("selectedjre");
/*      */     
/* 3612 */     _jspx_th_html_005fselect_005f4.setStyleClass("formtext default");
/* 3613 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 3614 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 3615 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 3616 */         out = _jspx_page_context.pushBody();
/* 3617 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 3618 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3621 */         out.write("\n\t\t\t\t\t");
/* 3622 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 3623 */           return true;
/* 3624 */         out.write("\n\t\t\t\t\t");
/* 3625 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 3626 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3629 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 3630 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3633 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 3634 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 3635 */       return true;
/*      */     }
/* 3637 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 3638 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3643 */     PageContext pageContext = _jspx_page_context;
/* 3644 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3646 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3647 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 3648 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 3650 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("jrelist");
/* 3651 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 3652 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 3653 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 3654 */       return true;
/*      */     }
/* 3656 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 3657 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3662 */     PageContext pageContext = _jspx_page_context;
/* 3663 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3665 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f5 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3666 */     _jspx_th_logic_005fnotEmpty_005f5.setPageContext(_jspx_page_context);
/* 3667 */     _jspx_th_logic_005fnotEmpty_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3669 */     _jspx_th_logic_005fnotEmpty_005f5.setName("hypervhostlist");
/* 3670 */     int _jspx_eval_logic_005fnotEmpty_005f5 = _jspx_th_logic_005fnotEmpty_005f5.doStartTag();
/* 3671 */     if (_jspx_eval_logic_005fnotEmpty_005f5 != 0) {
/*      */       for (;;) {
/* 3673 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 3674 */         if (_jspx_meth_html_005fselect_005f5(_jspx_th_logic_005fnotEmpty_005f5, _jspx_page_context))
/* 3675 */           return true;
/* 3676 */         out.write("\n\t\t\t\t\t\t\t ");
/* 3677 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f5.doAfterBody();
/* 3678 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3682 */     if (_jspx_th_logic_005fnotEmpty_005f5.doEndTag() == 5) {
/* 3683 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5);
/* 3684 */       return true;
/*      */     }
/* 3686 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5);
/* 3687 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f5(JspTag _jspx_th_logic_005fnotEmpty_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3692 */     PageContext pageContext = _jspx_page_context;
/* 3693 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3695 */     SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3696 */     _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 3697 */     _jspx_th_html_005fselect_005f5.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f5);
/*      */     
/* 3699 */     _jspx_th_html_005fselect_005f5.setProperty("selectedhypervhost");
/*      */     
/* 3701 */     _jspx_th_html_005fselect_005f5.setStyleClass("formtext default");
/* 3702 */     int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 3703 */     if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 3704 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 3705 */         out = _jspx_page_context.pushBody();
/* 3706 */         _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 3707 */         _jspx_th_html_005fselect_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3710 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 3711 */         if (_jspx_meth_html_005foptionsCollection_005f4(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/* 3712 */           return true;
/* 3713 */         out.write("\n\t\t\t\t\t\t\t\t        ");
/* 3714 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 3715 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3718 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 3719 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3722 */     if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 3723 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 3724 */       return true;
/*      */     }
/* 3726 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 3727 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f4(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3732 */     PageContext pageContext = _jspx_page_context;
/* 3733 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3735 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f4 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3736 */     _jspx_th_html_005foptionsCollection_005f4.setPageContext(_jspx_page_context);
/* 3737 */     _jspx_th_html_005foptionsCollection_005f4.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 3739 */     _jspx_th_html_005foptionsCollection_005f4.setProperty("hypervhostlist");
/* 3740 */     int _jspx_eval_html_005foptionsCollection_005f4 = _jspx_th_html_005foptionsCollection_005f4.doStartTag();
/* 3741 */     if (_jspx_th_html_005foptionsCollection_005f4.doEndTag() == 5) {
/* 3742 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 3743 */       return true;
/*      */     }
/* 3745 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 3746 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3751 */     PageContext pageContext = _jspx_page_context;
/* 3752 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3754 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f6 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3755 */     _jspx_th_logic_005fnotEmpty_005f6.setPageContext(_jspx_page_context);
/* 3756 */     _jspx_th_logic_005fnotEmpty_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3758 */     _jspx_th_logic_005fnotEmpty_005f6.setName("xenHostList");
/* 3759 */     int _jspx_eval_logic_005fnotEmpty_005f6 = _jspx_th_logic_005fnotEmpty_005f6.doStartTag();
/* 3760 */     if (_jspx_eval_logic_005fnotEmpty_005f6 != 0) {
/*      */       for (;;) {
/* 3762 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 3763 */         if (_jspx_meth_html_005fselect_005f6(_jspx_th_logic_005fnotEmpty_005f6, _jspx_page_context))
/* 3764 */           return true;
/* 3765 */         out.write("\n\t\t\t\t\t\t\t ");
/* 3766 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f6.doAfterBody();
/* 3767 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3771 */     if (_jspx_th_logic_005fnotEmpty_005f6.doEndTag() == 5) {
/* 3772 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f6);
/* 3773 */       return true;
/*      */     }
/* 3775 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f6);
/* 3776 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f6(JspTag _jspx_th_logic_005fnotEmpty_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3781 */     PageContext pageContext = _jspx_page_context;
/* 3782 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3784 */     SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 3785 */     _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/* 3786 */     _jspx_th_html_005fselect_005f6.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f6);
/*      */     
/* 3788 */     _jspx_th_html_005fselect_005f6.setProperty("selectedXenHost");
/*      */     
/* 3790 */     _jspx_th_html_005fselect_005f6.setStyleClass("formtext");
/*      */     
/* 3792 */     _jspx_th_html_005fselect_005f6.setStyle("width:25%");
/* 3793 */     int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/* 3794 */     if (_jspx_eval_html_005fselect_005f6 != 0) {
/* 3795 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 3796 */         out = _jspx_page_context.pushBody();
/* 3797 */         _jspx_th_html_005fselect_005f6.setBodyContent((BodyContent)out);
/* 3798 */         _jspx_th_html_005fselect_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3801 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 3802 */         if (_jspx_meth_html_005foptionsCollection_005f5(_jspx_th_html_005fselect_005f6, _jspx_page_context))
/* 3803 */           return true;
/* 3804 */         out.write("\n\t\t\t\t\t\t\t\t        ");
/* 3805 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/* 3806 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3809 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 3810 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3813 */     if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/* 3814 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f6);
/* 3815 */       return true;
/*      */     }
/* 3817 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f6);
/* 3818 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f5(JspTag _jspx_th_html_005fselect_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3823 */     PageContext pageContext = _jspx_page_context;
/* 3824 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3826 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f5 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3827 */     _jspx_th_html_005foptionsCollection_005f5.setPageContext(_jspx_page_context);
/* 3828 */     _jspx_th_html_005foptionsCollection_005f5.setParent((Tag)_jspx_th_html_005fselect_005f6);
/*      */     
/* 3830 */     _jspx_th_html_005foptionsCollection_005f5.setProperty("xenHostList");
/* 3831 */     int _jspx_eval_html_005foptionsCollection_005f5 = _jspx_th_html_005foptionsCollection_005f5.doStartTag();
/* 3832 */     if (_jspx_th_html_005foptionsCollection_005f5.doEndTag() == 5) {
/* 3833 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 3834 */       return true;
/*      */     }
/* 3836 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 3837 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3842 */     PageContext pageContext = _jspx_page_context;
/* 3843 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3845 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 3846 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3847 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3849 */     _jspx_th_html_005ftext_005f1.setProperty("tddelay");
/*      */     
/* 3851 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext msmall");
/*      */     
/* 3853 */     _jspx_th_html_005ftext_005f1.setSize("5");
/* 3854 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3855 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3856 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3857 */       return true;
/*      */     }
/* 3859 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3860 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3865 */     PageContext pageContext = _jspx_page_context;
/* 3866 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3868 */     SelectTag _jspx_th_html_005fselect_005f7 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3869 */     _jspx_th_html_005fselect_005f7.setPageContext(_jspx_page_context);
/* 3870 */     _jspx_th_html_005fselect_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3872 */     _jspx_th_html_005fselect_005f7.setProperty("sendmail");
/*      */     
/* 3874 */     _jspx_th_html_005fselect_005f7.setStyleClass("formtext default");
/* 3875 */     int _jspx_eval_html_005fselect_005f7 = _jspx_th_html_005fselect_005f7.doStartTag();
/* 3876 */     if (_jspx_eval_html_005fselect_005f7 != 0) {
/* 3877 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 3878 */         out = _jspx_page_context.pushBody();
/* 3879 */         _jspx_th_html_005fselect_005f7.setBodyContent((BodyContent)out);
/* 3880 */         _jspx_th_html_005fselect_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3883 */         out.write("\n\t\t\t\t\t\t");
/* 3884 */         if (_jspx_meth_html_005foptionsCollection_005f6(_jspx_th_html_005fselect_005f7, _jspx_page_context))
/* 3885 */           return true;
/* 3886 */         out.write(10);
/* 3887 */         out.write(9);
/* 3888 */         out.write(9);
/* 3889 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f7.doAfterBody();
/* 3890 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3893 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 3894 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3897 */     if (_jspx_th_html_005fselect_005f7.doEndTag() == 5) {
/* 3898 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f7);
/* 3899 */       return true;
/*      */     }
/* 3901 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f7);
/* 3902 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f6(JspTag _jspx_th_html_005fselect_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3907 */     PageContext pageContext = _jspx_page_context;
/* 3908 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3910 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f6 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3911 */     _jspx_th_html_005foptionsCollection_005f6.setPageContext(_jspx_page_context);
/* 3912 */     _jspx_th_html_005foptionsCollection_005f6.setParent((Tag)_jspx_th_html_005fselect_005f7);
/*      */     
/* 3914 */     _jspx_th_html_005foptionsCollection_005f6.setProperty("maillist");
/* 3915 */     int _jspx_eval_html_005foptionsCollection_005f6 = _jspx_th_html_005foptionsCollection_005f6.doStartTag();
/* 3916 */     if (_jspx_th_html_005foptionsCollection_005f6.doEndTag() == 5) {
/* 3917 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 3918 */       return true;
/*      */     }
/* 3920 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 3921 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3926 */     PageContext pageContext = _jspx_page_context;
/* 3927 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3929 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3930 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 3931 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3933 */     _jspx_th_html_005ftext_005f2.setProperty("priority");
/*      */     
/* 3935 */     _jspx_th_html_005ftext_005f2.setSize("30");
/*      */     
/* 3937 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 3939 */     _jspx_th_html_005ftext_005f2.setMaxlength("50");
/* 3940 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 3941 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 3942 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3943 */       return true;
/*      */     }
/* 3945 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 3946 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\VMActionForm_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */