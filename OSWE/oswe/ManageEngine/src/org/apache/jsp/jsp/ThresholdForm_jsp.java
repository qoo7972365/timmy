/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.EqualTag;
/*      */ import org.apache.struts.taglib.logic.NotEqualTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ 
/*      */ public final class ThresholdForm_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   24 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   30 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*   31 */   static { _jspx_dependants.put("/jsp/includes/NewThresholdActions.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   60 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   64 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   77 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   78 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   79 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   80 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   81 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   82 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   85 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   86 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   90 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   91 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*   92 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.release();
/*   93 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.release();
/*   94 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*   95 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/*   96 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   97 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*   98 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.release();
/*   99 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.release();
/*  100 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/*  101 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*  102 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.release();
/*  103 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  104 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.release();
/*  105 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.release();
/*  106 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/*  107 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  108 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  109 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  110 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
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
/*  127 */       response.setContentType("text/html");
/*  128 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  130 */       _jspx_page_context = pageContext;
/*  131 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  132 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  133 */       session = pageContext.getSession();
/*  134 */       out = pageContext.getOut();
/*  135 */       _jspx_out = out;
/*      */       
/*  137 */       out.write("\n\n\n\n\n\n\n\n\n\n");
/*      */       
/*  139 */       if ((request.getParameter("anomalyType") != null) && (request.getParameter("anomalyType").equals("customExp")))
/*      */       {
/*  141 */         request.setAttribute("formulatype", "expression");
/*      */       }
/*  143 */       String anomalyvalue = request.getParameter("isanomaly");
/*  144 */       String ftype = (String)request.getAttribute("formulatype");
/*  145 */       String editftype = request.getParameter("typeofformula");
/*  146 */       String met = request.getParameter("method");
/*      */       
/*  148 */       boolean isdata = com.adventnet.appmanager.util.ReportDataUtilities.getDataTime(1, 2, 2009);
/*      */       
/*  150 */       boolean showscript = !com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().isAnomalyAllowed();
/*  151 */       boolean isdelegatedAdmin = com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(request.getRemoteUser());
/*      */       
/*  153 */       if (!com.adventnet.appmanager.util.DBUtil.hasGlobalConfigValue("NewThresholdAccessed"))
/*      */       {
/*  155 */         com.adventnet.appmanager.util.AppManagerUtil.insertTimeforMetrack("NewThresholdAccessed");
/*  156 */         com.adventnet.appmanager.util.DBUtil.insertIntoGlobalConfig("NewThresholdAccessed", "true");
/*      */       }
/*      */       
/*      */ 
/*  160 */       out.write("\n\n<script>\nvar useGlobDef ='");
/*  161 */       out.print(FormatUtil.getString("am.webclient.threshold.critcal.text"));
/*  162 */       out.write("';\nvar dontshow=");
/*  163 */       out.print(showscript);
/*  164 */       out.write(";\nuseGlobDef=encodeURIComponent(useGlobDef);\n\n$(document).ready(function() {\n\n\tvar severity = [ \"critical\", \"warning\", \"info\" ]; //no i18n\n\tfor ( var i = 0; i < severity.length; i = i + 1 ) {\n        $(\"#addmore\"+severity[i]).click(function(event) {\n\t   var severity=event.target.id.substr(7);\n\t  $(\"#secondary\"+severity+\"exist\").val(\"true\");\t \t\n\t   $(\"#addmore\"+severity).hide();\t\n           $(\"#\"+severity+\"row_secondary\").show();\t\n          return false;\n        });\n\n\n\t$(\"#\"+severity[i]+\"close\").click(function(event) {\n\t\tvar closeid=jQuery(this).attr(\"id\"); //no i18n\n\t\tvar closeidx=closeid.indexOf(\"close\");\n\t\tvar severity=closeid.substr(0,closeidx);\n\t  $(\"#secondary\"+severity+\"exist\").val(\"false\");\t \t\n\t   $(\"#addmore\"+severity).show();\t\n\t   $(\"#\"+severity+\"row_secondary\").hide();\n\t});\n\t}\n    });\n\n\n\nfunction fnFormSubmit()\n{\n\tdocument.AMActionForm.submit();\n}\n\nfunction changetext(a)\n{\n\tif(a.value==useGlobDef)\n\t{\n\t\ta.value='';\n\t}\n}\n\nfunction redirectToPattern(redirectTo)\n{\n\tif(redirectTo=='thresholdPattern')\n\t{\n\t\tdocument.AMActionForm.action=\"/showTile.do?TileName=.PatternConf\";\n");
/*  165 */       out.write("\t\tfnFormSubmit();\n\t}\n\telse if(redirectTo=='thresholdFloat'){\n\t\tjavascript:showDiv('floatNote');\n\t}\n\telse{\n\t\tjavascript:hideDiv('floatNote');\n\t}\n}\n\nfunction validateAndSubmit(value)\n{\n\t");
/*  166 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/*  168 */       out.write(10);
/*  169 */       out.write(9);
/*      */       
/*  171 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  172 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  173 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */       
/*  175 */       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  176 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  177 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */         for (;;) {
/*  179 */           out.write(10);
/*  180 */           out.write(9);
/*      */           
/*  182 */           EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/*  183 */           _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/*  184 */           _jspx_th_logic_005fequal_005f0.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */           
/*  186 */           _jspx_th_logic_005fequal_005f0.setName("AMActionForm");
/*      */           
/*  188 */           _jspx_th_logic_005fequal_005f0.setProperty("isEditAllowed");
/*      */           
/*  190 */           _jspx_th_logic_005fequal_005f0.setValue("false");
/*  191 */           int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/*  192 */           if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */             for (;;) {
/*  194 */               out.write("\n\t\twindow.alert('");
/*  195 */               out.print(FormatUtil.getString("am.webclient.userauthorization.unaunthorised"));
/*  196 */               out.write("');\n\t\treturn;\n\t");
/*  197 */               int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/*  198 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  202 */           if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/*  203 */             this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0); return;
/*      */           }
/*      */           
/*  206 */           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/*  207 */           out.write("\n\tif(value == 1)\n\t{\n\tdocument.AMActionForm.cancel.value=\"false\";\n\tfor(i=0;i<document.AMActionForm.elements.length;i++)\n\t{\n\t\tif(document.AMActionForm.elements[i].type==\"text\")\n                {\n                        var name = document.AMActionForm.elements[i].name;\n                        var value =encodeURIComponent( document.AMActionForm.elements[i].value);\n\t\t\tif(name==\"displayname\")\n                        {\n                             if(trimAll(value)==\"\")\n                             {\n                             \twindow.alert('");
/*  208 */           out.print(FormatUtil.getString("am.webclient.threshold.alertemptyname"));
/*  209 */           out.write("');\n                             \treturn false;\n                             }\n                             if(displayNameHasQuotes(trimAll(value),\"");
/*  210 */           out.print(FormatUtil.getString("am.webclient.threshold.alertsingleqoute"));
/*  211 */           out.write("\"))\n\t\t\t     {\n\t\t\t      \treturn false;\n                             }\n                             \n//                           if(!checkSpecialCharacter(trimAll(document.AMActionForm.elements[i].value),\"");
/*  212 */           out.print(FormatUtil.getString("am.webclient.specialchar.alert.thresholdname"));
/*  213 */           out.write("\"))\n//                    \t\t {\n//                    \t\t \treturn false;\n//                     \t\t }\n\n                        }\n\t\t\telse if(name==\"criticalthresholdvalue\" || name==\"secondarycriticalthresholdvalue\")\n            {\n\t\t\t\tif(document.AMActionForm.select.value=='thresholdNumeric')\n\t\t\t\t{\n\t\t\t\t\tif((trimAll(value)==\"\") || (isPositiveInteger(value)==false))\n\t\t   \t\t\t{\n\t\t\t\t\t\twindow.alert('");
/*  214 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositivecritical"));
/*  215 */           out.write("');\n\t\t\t\t\t\treturn false;\n\t\t   \t\t\t}\n\t\t\t\t}\n\t\t\t\telse if(document.AMActionForm.select.value=='thresholdFloat')\n\t\t\t\t{\n\t\t\t\t\tif((trimAll(value)==\"\") || isNaN(value) || (value*1)<0)\n\t\t   \t\t\t{\n\t\t\t\t\t\twindow.alert('");
/*  216 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositivefloatcritical"));
/*  217 */           out.write("');\n\t\t\t\t\t\treturn false;\n\t\t   \t\t\t}\n\t\t\t\t}\n\t        }\n\t        else if(name==\"warningthresholdvalue\" || name==\"secondarywarningthresholdvalue\")\n\t        {\n\t        \tif(document.AMActionForm.select.value=='thresholdNumeric')\n\t\t\t\t{\n\t           \tif((trimAll(value)==\"\")||(isPositiveInteger(value)==false))\n\t                {\n\t\t\t\t\t\twindow.alert('");
/*  218 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositivewarning"));
/*  219 */           out.write("');\n\t\t\t\t\t\treturn false;\n\t                }\n\t\t\t\t}\n\t\t\t\telse if(document.AMActionForm.select.value=='thresholdFloat')\n\t\t\t\t{\n\t\t\t\t\tif((trimAll(value)==\"\") || isNaN(value) || (value*1)<0)\n\t\t\t\t\t{\n\t\t\t\t\t\twindow.alert('");
/*  220 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositivefloatwarning"));
/*  221 */           out.write("');\n\t\t\t\t\t\treturn false;\n\t\t\t\t\t}\n\t\t\t\t\t}\n\t        }\n\t        else if(name==\"infothresholdvalue\" || name==\"secondaryinfothresholdvalue\")\n\t        {\n\t        \tif(document.AMActionForm.select.value=='thresholdNumeric')\n\t\t\t\t{\n\t             \tif((trimAll(value)==\"\")||(isPositiveInteger(value)==false))\n\t                {\n\t\t\t\t\t\twindow.alert('");
/*  222 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositiveclear"));
/*  223 */           out.write("');\n\t\t\t\t\t\treturn false;\n\t                }\n\t\t\t\t}\n\t        \telse if(document.AMActionForm.select.value=='thresholdFloat')\n\t\t\t\t{\n\t        \t\tif((trimAll(value)==\"\") || isNaN(value) || (value*1)<0)\n\t                {\n\t\t\t\t\t\twindow.alert('");
/*  224 */           out.print(FormatUtil.getString("am.webclient.threshold.alertfloatpositiveclear"));
/*  225 */           out.write("');\n\t\t\t\t\t\treturn false;\n\t                }\n\t\t\t\t}\n\t        }\n\t        else if(name==\"consecutive_mincriticalpolls\"){\n\t\t\tif(trimAll(value)!=\"\" && trimAll(value)!=useGlobDef){\n\n\t\t\t  \t \tif(isPositiveInteger(value)==false){\n\t\t\t\t \t\twindow.alert('");
/*  226 */           out.print(FormatUtil.getString("am.webclient.threshold.minimum.alertpositivecriticalconsecutive"));
/*  227 */           out.write("');\n\t\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t\t  \t \n\t\t\t\t\t  \tvar min_criticalpolls = trimAll(value);\n\t\t\t\t\t\tvar criticalPolls = encodeURIComponent(document.AMActionForm.consecutive_criticalpolls.value)\n\t\t\t\t\t\tif(criticalPolls == useGlobDef){\n\t\t\t\t\t\t\twindow.alert('");
/*  228 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositivecriticalconsecutive"));
/*  229 */           out.write("');\n\t\t\t\t\t \t\treturn false;\n\t\t\t\t\t\t}\n\t\t\t\t\t\tif(trimAll(criticalPolls)!=\"\" && trimAll(criticalPolls)!=useGlobDef){\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\tif(isPositiveInteger(criticalPolls)){\n\t\t\t\t\t\t\t\t\tif(parseInt(min_criticalpolls) > parseInt(criticalPolls)) {\n\t\t\t\t\t\t\t\t\t\twindow.alert('");
/*  230 */           out.print(FormatUtil.getString("am.webclient.threshold.pollstotry.greater.text"));
/*  231 */           out.write("');\n\t\t\t\t\t\t\t\t\t\tdocument.AMActionForm.elements[i].focus();\n\t\t\t\t\t\t\t\t \t\treturn false;\n\t\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\t}else{\n\t\t\t\t\t\t\t\t\twindow.alert('");
/*  232 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositivecriticalconsecutive"));
/*  233 */           out.write("');\n\t\t\t\t\t\t\t \t\treturn false;\n\t\t\t\t\t\t\t}\n\t\t\t\t\t\t}\n\t\t           }\n\t\t\t\n\t\t\t    if(trimAll(value)==useGlobDef)\n\t\t\t    {\n                                document.AMActionForm.consecutive_mincriticalpolls.value=\"\";\n\t\t\t    }\n\t\t\t }else if(name==\"consecutive_minwarningpolls\"){\n\t\t\t\t   if(trimAll(value)!=\"\" && trimAll(value)!=useGlobDef){\n\t\t\t\t\t  \t if(isPositiveInteger(value)==false){\n\t\t\t\t\t \t\twindow.alert('");
/*  234 */           out.print(FormatUtil.getString("am.webclient.threshold.minimum.alertpositivewarningconsecutive"));
/*  235 */           out.write("');\n\t\t\t\t\t \t\treturn false;\n\t\t\t\t\t     }\n\t\t\t\t  \t \n\t\t\t\t  \tvar min_warningpolls = trimAll(value);\n\t\t\t\t\tvar warningPolls = encodeURIComponent(document.AMActionForm.consecutive_warningpolls.value)\n\t\t\t\t\tif(warningPolls == useGlobDef){\n\t\t\t\t\t\twindow.alert('");
/*  236 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositivewarningconsecutive"));
/*  237 */           out.write("');\n\t\t\t\t \t\treturn false;\n\t\t\t\t\t}\n\t\t\t\t\tif(trimAll(warningPolls)!=\"\" && trimAll(warningPolls)!=useGlobDef){\n\t\t\t\t\t\t\tif(isPositiveInteger(warningPolls)){\n\t\t\t\t\t\t\t\tif(parseInt(min_warningpolls) > parseInt(warningPolls)) {\n\t\t\t\t\t\t\t\t\twindow.alert('");
/*  238 */           out.print(FormatUtil.getString("am.webclient.threshold.pollstotry.greater.text"));
/*  239 */           out.write("');\n\t\t\t\t\t\t\t\t\tdocument.AMActionForm.elements[i].focus();\n\t\t\t\t\t\t\t \t\treturn false;\n\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t}else{\n\t\t\t\t\t\t\t\twindow.alert('");
/*  240 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositivewarningconsecutive"));
/*  241 */           out.write("');\n\t\t\t\t\t\t \t\treturn false;\n\t\t\t\t\t\t\t}\n\t\t\t \t\t\t}\n\t\t\t\t     }\n\t\t\t\t   \n\t\t\t\t     if(trimAll(value)==useGlobDef){\n\t                                document.AMActionForm.consecutive_minwarningpolls.value=\"\";\n\t\t\t\t     }\n\t\t\t\t }else if(name==\"consecutive_minclearpolls\")\n\t\t\t\t {\n\n\t\t\t\t\t    if(trimAll(value)!=\"\" && trimAll(value)!=useGlobDef)\n\t\t\t\t\t    {\n\t\t\t\t\t   \t if(isPositiveInteger(value)==false)\n\t\t\t\t\t         {\n\t\t\t\t\t \t\twindow.alert('");
/*  242 */           out.print(FormatUtil.getString("am.webclient.threshold.minimum.alertpositiveclearconsecutive"));
/*  243 */           out.write("');\n\t\t\t\t\t \t\treturn false;\n\t\t\t\t\t         }\n\t\t\t\t\t   \t \n\t\t\t\t\t   \tvar min_clearpolls = trimAll(value);\n\t\t\t\t\t\tvar clearPolls = encodeURIComponent(document.AMActionForm.consecutive_clearpolls.value)\n\t\t\t\t\t\tif(clearPolls == useGlobDef){\n\t\t\t\t\t\t\twindow.alert('");
/*  244 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositiveclearconsecutive"));
/*  245 */           out.write("');\n\t\t\t\t\t \t\treturn false;\n\t\t\t\t\t\t}\n\t\t\t\t\t\t if(trimAll(clearPolls)!=\"\" && trimAll(clearPolls)!=useGlobDef){\n\t\t\t\t\t\t\tif(isPositiveInteger(clearPolls)){\n\t\t\t\t\t\t\t\tif(parseInt(min_clearpolls) > parseInt(clearPolls)) {\n\t\t\t\t\t\t\t\t\twindow.alert('");
/*  246 */           out.print(FormatUtil.getString("am.webclient.threshold.pollstotry.greater.text"));
/*  247 */           out.write("');\n\t\t\t\t\t\t\t\t\tdocument.AMActionForm.elements[i].focus();\n\t\t\t\t\t\t\t \t\treturn false;\n\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t}else{\n\t\t\t\t\t\t\t\twindow.alert('");
/*  248 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositiveclearconsecutive"));
/*  249 */           out.write("');\n\t\t\t\t\t\t \t\treturn false;\n\t\t\t\t\t\t\t}\n\t\t\t\t\t\t }\n\t\t\t\t\t     }\n\t\t\t\t\t    \n\t\t\t\t\t    \n\t\t\t\t\t    if(trimAll(value)==useGlobDef)\n\t\t\t\t\t    {\n\t\t                                document.AMActionForm.consecutive_minclearpolls.value=\"\";\n\n\t\t\t\t\t    }\n\t\t                        }\n                        else if(name==\"consecutive_criticalpolls\")\n\t\t\t{\n\t\t\tif(trimAll(value)!=\"\" && trimAll(value)!=useGlobDef)\n\t\t\t   {\n\n\t\t\t  \t if(isPositiveInteger(value)==false)\n\t\t\t         {\n\t\t\t \t\twindow.alert('");
/*  250 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositivecriticalconsecutive"));
/*  251 */           out.write("');\n\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t           }\n\t\t\t    if(trimAll(value)==useGlobDef)\n\t\t\t    {\n                                document.AMActionForm.consecutive_criticalpolls.value=\"\";\n\t\t\t    }\n\t\t\t }\n\t\t\t else if(name==\"consecutive_warningpolls\")\n\t\t\t {\n\t\t\t   if(trimAll(value)!=\"\" && trimAll(value)!=useGlobDef)\n\t\t\t   {\n\t\t\t  \t if(isPositiveInteger(value)==false)\n\t\t\t         {\n\t\t\t \t\twindow.alert('");
/*  252 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositivewarningconsecutive"));
/*  253 */           out.write("');\n\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t\t     }\n\t\t\t     if(trimAll(value)==useGlobDef)\n\t\t\t    {\n                                document.AMActionForm.consecutive_warningpolls.value=\"\";\n\t\t\t    }\n\t\t\t }\n\t\t\t else if(name==\"consecutive_clearpolls\")\n\t\t\t {\n\n\t\t\t    if(trimAll(value)!=\"\" && trimAll(value)!=useGlobDef)\n\t\t\t    {\n\t\t\t   \t if(isPositiveInteger(value)==false)\n\t\t\t         {\n\t\t\t \t\twindow.alert('");
/*  254 */           out.print(FormatUtil.getString("am.webclient.threshold.alertpositiveclearconsecutive"));
/*  255 */           out.write("');\n\t\t\t \t\treturn false;\n\t\t\t         }\n\t\t\t     }\n\t\t\t    if(trimAll(value)==useGlobDef)\n\t\t\t    {\n                                document.AMActionForm.consecutive_clearpolls.value=\"\";\n\n\t\t\t    }\n                        }\n        \t}\n\t}\n\tif(document.getElementById('showDetails').style.display == 'none')\n\t{\n\t\tif (document.AMActionForm.criticalthresholdcondition.value=='NE')\n\t\t{\n\t\t\tdocument.AMActionForm.warningthresholdcondition.value='NE';\n\t\t\tdocument.AMActionForm.warningthresholdvalue.value=document.AMActionForm.criticalthresholdvalue.value;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.AMActionForm.warningthresholdcondition.value='EQ';\n\t\t\tdocument.AMActionForm.warningthresholdvalue.value=document.AMActionForm.criticalthresholdvalue.value;\n\t\t}\n\t\tif (document.AMActionForm.criticalthresholdcondition.value=='LT' || document.AMActionForm.criticalthresholdcondition.value=='LE')\n\t\t{\n\t\t\tdocument.AMActionForm.infothresholdcondition.value='GT';\n\t\t\tdocument.AMActionForm.infothresholdvalue.value=document.AMActionForm.criticalthresholdvalue.value;\n");
/*  256 */           out.write("\t\t}\n\t\telse if (document.AMActionForm.criticalthresholdcondition.value=='GT' || document.AMActionForm.criticalthresholdcondition.value=='GE')\n\t\t{\n\t\t\tdocument.AMActionForm.infothresholdcondition.value='LT';\n\t\t\tdocument.AMActionForm.infothresholdvalue.value=document.AMActionForm.criticalthresholdvalue.value;\n\t\t}\n\t\telse if (document.AMActionForm.criticalthresholdcondition.value=='EQ')\n\t\t{\n\t\t\tdocument.AMActionForm.infothresholdcondition.value='NE';\n\t\t\tdocument.AMActionForm.infothresholdvalue.value=document.AMActionForm.criticalthresholdvalue.value;\n\t\t}\n\t\telse if (document.AMActionForm.criticalthresholdcondition.value=='NE')\n\t\t{\n\t\t\tdocument.AMActionForm.infothresholdcondition.value='EQ';\n\t\t\tdocument.AMActionForm.infothresholdvalue.value=document.AMActionForm.criticalthresholdvalue.value;\n\t\t}\n\t}\n\t}\n\telse\n\t{\n\t  document.AMActionForm.cancel.value=\"true\";\n\t}\n\tif(document.AMActionForm.type!=null && document.AMActionForm.type.value=='4')\n\t{\n\t\tdocument.AMActionForm.method.value = 'editPatternAction'; //No I18N\n\t}\n\telse if(document.AMActionForm.select.value=='thresholdFloat')\n");
/*  257 */           out.write("\t{\n\t\tdocument.AMActionForm.method.value = 'createPatternAction'; //No I18N \n\t}\n\tfnFormSubmit();\n\t");
/*  258 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  259 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  263 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  264 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */       }
/*      */       else {
/*  267 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  268 */         out.write("\n }\nfunction callStep5()\n{\n\t");
/*  269 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */           return;
/*  271 */         out.write(10);
/*  272 */         out.write(9);
/*  273 */         if (_jspx_meth_logic_005fnotPresent_005f1(_jspx_page_context))
/*      */           return;
/*  275 */         out.write("\n}\nfunction doInitStuffOnBodyLoad()\n{\n\tif(document.AMActionForm.checkbox1!= null)\n\t{\n\t\tdocument.AMActionForm.checkbox1.checked=\"");
/*  276 */         out.print(com.adventnet.appmanager.fault.FaultUtil.ADVANCED_USER ? "checked" : "");
/*  277 */         out.write("\";\n\t\tshowAdvancedOptions()\n\t}\n\n\n}\nfunction showAdvancedOptions()\n{\n\tif(document.AMActionForm.checkbox1.checked)\n\t{\n\t\tshowDiv('showDetails$showRetryPolls1$showRetryPolls2$showRetryPolls3$showMessage1$showMessage2');\n\t}\n\telse\n\t{\n\t\thideDiv('showDetails$showRetryPolls1$showRetryPolls2$showRetryPolls3$showMessage1$showMessage2');\n\t}\n}\n\n //the below for tabs and anomaly inclusion..\n\n    function showHide(tab)\n{\n\n\tif(tab==\"threshold\")\n\t{\n\n\n\n\tdocument.getElementById(\"customreplink-left\").className = \"tbSelected_Left\";\n\tdocument.getElementById(\"customreplink\").className = \"tbSelected_Middle\";\n\tdocument.getElementById(\"customreplink-right\").className = \"tbSelected_Right\";\n\n\tdocument.getElementById(\"downsumreplink-left\").className = \"tbUnSelected_Left\";\n\tdocument.getElementById(\"downsumreplink\").className = \"tbUnSelected_Middle\";\n\tdocument.getElementById(\"downsumreplink-right\").className = \"tbUnSelected_Right\";\n\n\tjavascript:showDiv('thresholdnewdiv');\n            ");
/*  278 */         if ("showThresholdAction".equals(met)) {
/*  279 */           out.write("\n             document.getElementById(\"ancolumn\").style.display='none';\n        ");
/*      */         }
/*  281 */         out.write("\n\n\tjavascript:hideDiv('anomalydiv');\n\n\t}\n\n\telse if(tab==\"anomaly\")\n\t{\n\t ");
/*  282 */         if ("showAnomalyAction".equals(met)) {
/*  283 */           out.write("\n\t  document.getElementById(\"thresholdcolumn\").style.display='none';\n        ");
/*      */         }
/*  285 */         out.write("\n\tdocument.getElementById(\"downsumreplink-left\").className = \"tbSelected_Left\";\n\tdocument.getElementById(\"downsumreplink\").className = \"tbSelected_Middle\";\n\tdocument.getElementById(\"downsumreplink-right\").className = \"tbSelected_Right\";\n\n\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n\n\tjavascript:hideDiv('thresholdnewdiv');\n\tjavascript:showDiv('anomalydiv');\n\n\n\t}\n\n}\n/**\n         * checkPercent()\n         */\n        if(!dontshow){//this is to check anomaly license\n        function checkPercentage() {\n\n           if(document.getElementById(\"HP\") && document.getElementById(\"HP\").checked)\n\t{\n            document.getElementById(\"showpercentage\").style.display=\"block\";\n            document.getElementById(\"showpercentage1\").style.display=\"block\";\n\n        }else{\n          if(document.getElementById(\"showpercentage\")){\n            document.getElementById(\"showpercentage\").style.display=\"none\";\n");
/*  286 */         out.write("          }\n           if(document.getElementById(\"showpercentage1\")){\n                    document.getElementById(\"showpercentage1\").style.display=\"none\";\n            }\n\n        }\n\n        }\n        }\n        /**\n         * Comment\n         */\n        function showAdvancedDiv() {\n             if(document.getElementById(\"LP\").checked)\n\t{\n            showDiv(\"anomalyadvanceddiv\");\n\n        }else{\n            hideDiv(\"anomalyadvanceddiv\");\n\n        }\n\n        }\n function myOnLoad() {\n      javascript:showDiv('thresholdnewdiv');//no i18n\n\tjavascript:hideDiv('anomalydiv');//no i18n\n        javascript:hideDiv('emailactions');//no i18n\n        changediv(\"predefined\");\n        if(document.AMActionForm.select.value=='thresholdFloat'){\n    \t\tjavascript:showDiv('floatNote');\n        }\n        else{\n        \tjavascript:hideDiv('floatNote');//no i18n\n        }\n        ");
/*  287 */         if ("true".equals(anomalyvalue)) {
/*  288 */           out.write("\n\n         showHide(\"anomaly\");//no i18n\n         javascript:hideDiv('thresholdnewdiv');//no i18n\n\tjavascript:showDiv('anomalydiv');//no i18n\n        ");
/*  289 */           if (("expression".equals(ftype)) || ("expression".equals(editftype))) {
/*  290 */             out.write("\n\n            if(document.AMActionForm.baseformulaType){\n               document.AMActionForm.baseformulaType[1].checked=true;\n            }\n               changediv(\"expression\");\n        ");
/*      */           }
/*  292 */           out.write("\n        if(!dontshow){//this is to check anomaly license\n        checkPercentage();//no i18n\n         //getEmailAction();//no i18n\n        }\n\n        ");
/*      */         } else {
/*  294 */           out.write("\n             showHide(\"threshold\");//no i18n\n            ");
/*      */         }
/*  296 */         out.write("\n\n\n }\n  function changediv(p) {\n        if(p==\"predefined\"){\n\n            javascript:showDiv(\"predefined\");\n            javascript:hideDiv(\"expression\");\n             javascript:showDiv(\"predefinedhelp\");\n            javascript:hideDiv(\"expressionhelp\");\n        }else{\n            javascript:showDiv(\"expression\");\n            javascript:hideDiv(\"predefined\");\n            javascript:showDiv(\"expressionhelp\");\n            javascript:hideDiv(\"predefinedhelp\");\n        }\n\n    }\n /**\n * Comment\n */\nfunction validateAndSubmitAnomaly() {\n    validateAndSubmitAnomaly('no');//no i18n\n}\n /**\n     * validateAndSubmitAnomaly()\n     */\n    function validateAndSubmitAnomaly(e) {\n         ");
/*  297 */         if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
/*      */           return;
/*  299 */         out.write(10);
/*  300 */         out.write(9);
/*      */         
/*  302 */         EqualTag _jspx_th_logic_005fequal_005f1 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/*  303 */         _jspx_th_logic_005fequal_005f1.setPageContext(_jspx_page_context);
/*  304 */         _jspx_th_logic_005fequal_005f1.setParent(null);
/*      */         
/*  306 */         _jspx_th_logic_005fequal_005f1.setName("AMActionForm");
/*      */         
/*  308 */         _jspx_th_logic_005fequal_005f1.setProperty("isEditAllowed");
/*      */         
/*  310 */         _jspx_th_logic_005fequal_005f1.setValue("false");
/*  311 */         int _jspx_eval_logic_005fequal_005f1 = _jspx_th_logic_005fequal_005f1.doStartTag();
/*  312 */         if (_jspx_eval_logic_005fequal_005f1 != 0) {
/*      */           for (;;) {
/*  314 */             out.write("\n\t\twindow.alert('");
/*  315 */             out.print(FormatUtil.getString("am.webclient.userauthorization.unaunthorised"));
/*  316 */             out.write("');\n\t\treturn;\n\t");
/*  317 */             int evalDoAfterBody = _jspx_th_logic_005fequal_005f1.doAfterBody();
/*  318 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  322 */         if (_jspx_th_logic_005fequal_005f1.doEndTag() == 5) {
/*  323 */           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/*      */         }
/*      */         else {
/*  326 */           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/*  327 */           out.write("\n        if(document.AMActionForm.anomalyName.value==''){\n            alert('");
/*  328 */           out.print(FormatUtil.getString("am.webclient.anomalyprofile.jsalertname.text"));
/*  329 */           out.write("');//No I18N\n            return ;\n        }\n\n        if(document.AMActionForm.baseformulaType[1].checked){\n\n            if(document.AMActionForm.leftexp1.value=='' || !isAcceptableAnomalyExpression(document.AMActionForm.leftexp1.value)){\n                 alert('");
/*  330 */           out.print(FormatUtil.getString("am.webclient.anomaly.expression.jsalert.text"));
/*  331 */           out.write("');//No I18N\n                 return ;\n            }\n\n            if(document.AMActionForm.rightexp1.value=='' || document.AMActionForm.rightexp1.value.indexOf('Sample') !=-1 || !isAcceptableAnomalyExpression(document.AMActionForm.rightexp1.value)){\n                alert('");
/*  332 */           out.print(FormatUtil.getString("am.webclient.anomaly.expression.jsalert.text"));
/*  333 */           out.write("');//No I18N\n                document.AMActionForm.rightexp1.value='';\n                document.AMActionForm.rightexp1.focus();\n                 return ;\n            }\n\n            if(document.AMActionForm.leftexp2.value=='' || !isAcceptableAnomalyExpression(document.AMActionForm.leftexp2.value)){\n                alert('");
/*  334 */           out.print(FormatUtil.getString("am.webclient.anomaly.expression.jsalert.text"));
/*  335 */           out.write("');//No I18N\n                 return ;\n            }\n\n            if(document.AMActionForm.rightexp2.value=='' || document.AMActionForm.rightexp2.value.indexOf('Sample') !=-1 || !isAcceptableAnomalyExpression(document.AMActionForm.rightexp2.value)){\n                alert('");
/*  336 */           out.print(FormatUtil.getString("am.webclient.anomaly.expression.jsalert.text"));
/*  337 */           out.write("');//No I18N\n                 document.AMActionForm.rightexp2.value='';\n                document.AMActionForm.rightexp2.focus();\n                 return ;\n            }\n        }else{\n        if(document.AMActionForm.higherValue.value==''){\n            alert('");
/*  338 */           out.print(FormatUtil.getString("am.webclient.anomalyprofile.jaalertupper.text"));
/*  339 */           out.write("');//No I18N\n            return ;\n        }\n        if (document.AMActionForm.higherValue.value.indexOf(\".\")!=-1 || isNaN(document.AMActionForm.higherValue.value))//No I18 N\n        {\n             alert('");
/*  340 */           out.print(FormatUtil.getString("am.webclient.anomalyprofile.jaalertupperint.text"));
/*  341 */           out.write("');//No I18N\n            return ;\n        }\n        if(document.AMActionForm.lowerValue.value==''){\n            alert('");
/*  342 */           out.print(FormatUtil.getString("am.webclient.anomalyprofile.jaalertlower.text"));
/*  343 */           out.write("');//No I18N\n            return ;\n        }\n        if (document.AMActionForm.lowerValue.value.indexOf(\".\")!=-1 || isNaN(document.AMActionForm.lowerValue.value))//No I18 N\n        {\n             alert('");
/*  344 */           out.print(FormatUtil.getString("am.webclient.anomalyprofile.jaalertlowerint.text"));
/*  345 */           out.write("');//No I18N\n            return ;\n        }\n        if(!document.AMActionForm.higherPercentage.checked){\n\n            document.AMActionForm.percentagevalue.value=0;\n\n        }\n        }\n       // if(document.AMActionForm.baselineType[0].checked){\n\n            //if(dataavailable==false){\n              //  alert('<!--%=FormatUtil.getString(\"am.webclient.anomaly.nodataweek.text\")%-->');//No I18N\n            //    return;\n          //  }\n\n        //}\n\n        document.AMActionForm.method.value=document.AMActionForm.anomalymethod.value;\n\n        document.AMActionForm.submit();\n\n\n\n    }\n    /**\n     *\n     */\n\n     function isAcceptableAnomalyExpression(expression) {\n    \tvar token = expression.split(/[-+*/]/);\n    \tfor (var i = 0; i < token.length; i++)\n    \t{\n\t\t\tvar e = token[i];\n\t\t\tif(!(e=='$10D_MVA' || e=='$30D_MVA' || e=='$6H_MVA' || e=='$7D_MVA' || e=='$LastHourValue' || e=='$10H_MVA' || !isNaN(e))) {\n\t\t\t\treturn false;\n\t\t\t}\n    \t}\n    \treturn true;\n    }\n    /*function getEmailAction() {\n\n        if(document.AMActionForm.alarmType.value!='0' || document.AMActionForm.loweralarmType.value!='0'){\n");
/*  346 */           out.write("\n            javascript:hideDiv('emailactions');\n        }else{\n\n            javascript:showDiv('emailactions');\n        }\n    }*/\n    /**\n              * Comment\n              */\n             function getEmptyText(f,a) {\n\n                 if(a== 'rightexp1' && f == '");
/*  347 */           out.print(FormatUtil.getString("am.webclient.anomaly.expression.sampleexp.text"));
/*  348 */           out.write("'){\n                        document.AMActionForm.rightexp1.value='';\n                    }\n                    if(a== 'rightexp2' && f == '");
/*  349 */           out.print(FormatUtil.getString("am.webclient.anomaly.expression.sampleexp1.text"));
/*  350 */           out.write("'){\n                        document.AMActionForm.rightexp2.value='';\n                    }\n\n             }\n\n</script>\n\n\n\n\n");
/*  351 */           if (_jspx_meth_html_005ferrors_005f0(_jspx_page_context))
/*      */             return;
/*  353 */           out.write(10);
/*  354 */           out.write(10);
/*      */           
/*  356 */           org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/*  357 */           _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  358 */           _jspx_th_html_005fform_005f0.setParent(null);
/*      */           
/*  360 */           _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*  361 */           int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  362 */           if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */             for (;;) {
/*  364 */               out.write(10);
/*  365 */               out.write(10);
/*  366 */               if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  368 */               out.write(10);
/*  369 */               if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  371 */               out.write(10);
/*  372 */               if (_jspx_meth_am_005fhiddenparam_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  374 */               out.write(10);
/*  375 */               if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  377 */               out.write(10);
/*  378 */               if (_jspx_meth_logic_005fequal_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  380 */               out.write(10);
/*  381 */               out.write(10);
/*  382 */               if (_jspx_meth_logic_005fnotEqual_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  384 */               out.write(10);
/*  385 */               if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  387 */               out.write(10);
/*  388 */               if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  390 */               out.write(10);
/*  391 */               if (_jspx_meth_html_005fhidden_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  393 */               out.write(10);
/*  394 */               if (_jspx_meth_html_005fhidden_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  396 */               out.write(10);
/*  397 */               out.write(10);
/*  398 */               out.write(32);
/*  399 */               if (_jspx_meth_c_005fcatch_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  401 */               out.write("\n      ");
/*  402 */               if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  404 */               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"itadmin-hide\">\n  <tr class=\"tabBtmLine\">\n  <td><table id=\"InnerTab\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"innertab_btm_space\">\n               <tbody>\n                 <tr >\n                   <td width=\"17\">\n\n                   </td>\n\n                   <td id=\"thresholdcolumn\"><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                       <tbody>\n\n                         <tr>\n                           <td class=\"tbSelected_Left\" id=\"customreplink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                           <td class=\"tbSelected_Middle\" id=\"customreplink\">\n                        <a href=\"javascript:showHide('threshold')\">&nbsp;<span class=\"tabLink\">");
/*  405 */               out.print(FormatUtil.getString("am.webclient.configurealert.thresholdprofile"));
/*  406 */               out.write("</span></a></a>\n                           </td>\n                           <td class=\"tbselected_Right\" id=\"customreplink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                         </tr>\n                       </tbody>\n\n                     </table>\n                   </td>\n\n                   <td id=\"ancolumn\"><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                       <tbody>\n                         <tr>\n                           <td class=\"tbUnselected_Left\" id=\"downsumreplink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                           <td class=\"tbUnselected_Middle\" id=\"downsumreplink\">\n                           <a href=\"javascript:showHide('anomaly')\">&nbsp;<span class=\"tabLink\">");
/*  407 */               out.print(FormatUtil.getString("am.webclient.anomalyprofile.heading.text"));
/*  408 */               out.write("</span></a>\n                           </td>\n                           <td class=\"tbUnselected_Right\" id=\"downsumreplink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n                         </tr>\n                       </tbody>\n                     </table>\n                   </td>\n\n  </tr>\n  </table></td>\n\n  </tr>\n</table>\n\n<div id=\"thresholdnewdiv\" style=\"display:none\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t\t<td width=\"70%\" valign=\"top\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n    <td width=\"100%\" height=\"31\" class=\"tableheading-monitor-config\" >\n    ");
/*      */               
/*  410 */               EqualTag _jspx_th_logic_005fequal_005f3 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/*  411 */               _jspx_th_logic_005fequal_005f3.setPageContext(_jspx_page_context);
/*  412 */               _jspx_th_logic_005fequal_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  414 */               _jspx_th_logic_005fequal_005f3.setName("AMActionForm");
/*      */               
/*  416 */               _jspx_th_logic_005fequal_005f3.setProperty("method");
/*      */               
/*  418 */               _jspx_th_logic_005fequal_005f3.setValue("editThresholdAction");
/*  419 */               int _jspx_eval_logic_005fequal_005f3 = _jspx_th_logic_005fequal_005f3.doStartTag();
/*  420 */               if (_jspx_eval_logic_005fequal_005f3 != 0) {
/*      */                 for (;;) {
/*  422 */                   out.write("\n    ");
/*  423 */                   out.print(FormatUtil.getString("am.webclient.threshold.edit"));
/*  424 */                   out.write(" \n    ");
/*  425 */                   String thresholdType = "thresholdNumeric";
/*  426 */                   if ((request.getParameter("type") != null) && (request.getParameter("type").equals("4")))
/*      */                   {
/*  428 */                     thresholdType = "thresholdFloat";
/*      */                   }
/*  430 */                   out.write("\n    \t<input type=\"hidden\" name=\"select\" value=\"");
/*  431 */                   out.print(thresholdType);
/*  432 */                   out.write("\"/>\n    \t<input type=\"hidden\" name=\"type\" value=\"");
/*  433 */                   out.print(request.getParameter("type"));
/*  434 */                   out.write("\"/>\n    ");
/*  435 */                   int evalDoAfterBody = _jspx_th_logic_005fequal_005f3.doAfterBody();
/*  436 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  440 */               if (_jspx_th_logic_005fequal_005f3.doEndTag() == 5) {
/*  441 */                 this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f3); return;
/*      */               }
/*      */               
/*  444 */               this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f3);
/*  445 */               out.write(" \n     ");
/*      */               
/*  447 */               NotEqualTag _jspx_th_logic_005fnotEqual_005f1 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(NotEqualTag.class);
/*  448 */               _jspx_th_logic_005fnotEqual_005f1.setPageContext(_jspx_page_context);
/*  449 */               _jspx_th_logic_005fnotEqual_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  451 */               _jspx_th_logic_005fnotEqual_005f1.setName("AMActionForm");
/*      */               
/*  453 */               _jspx_th_logic_005fnotEqual_005f1.setProperty("method");
/*      */               
/*  455 */               _jspx_th_logic_005fnotEqual_005f1.setValue("editThresholdAction");
/*  456 */               int _jspx_eval_logic_005fnotEqual_005f1 = _jspx_th_logic_005fnotEqual_005f1.doStartTag();
/*  457 */               if (_jspx_eval_logic_005fnotEqual_005f1 != 0) {
/*      */                 for (;;) {
/*  459 */                   out.write("\n      ");
/*  460 */                   out.print(FormatUtil.getString("am.webclient.threshold.new"));
/*  461 */                   out.write("\n\n      <select name=\"select\" class=\"formtext medium\" onChange=\"redirectToPattern(this.value)\" style=\"width:150px\">\n\t\t<option value=\"thresholdNumeric\" ");
/*  462 */                   if ((request.getParameter("select") == null) || (request.getParameter("select").equals("thresholdNumeric"))) {
/*  463 */                     out.write("selected");
/*      */                   }
/*  465 */                   out.write(62);
/*  466 */                   out.print(FormatUtil.getString("am.webclient.threshold.numeric"));
/*  467 */                   out.write("</option>\n\t\t<option value=\"thresholdPattern\">");
/*  468 */                   out.print(FormatUtil.getString("am.webclient.threshold.string"));
/*  469 */                   out.write("</option>\n\t\t<option value=\"thresholdFloat\" ");
/*  470 */                   if ((request.getParameter("select") != null) && (request.getParameter("select").equals("thresholdFloat"))) {
/*  471 */                     out.write("selected");
/*      */                   }
/*  473 */                   out.write(62);
/*  474 */                   out.print(FormatUtil.getString("am.webclient.threshold.float"));
/*  475 */                   out.write("</option>\n     </select>\n");
/*  476 */                   int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f1.doAfterBody();
/*  477 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  481 */               if (_jspx_th_logic_005fnotEqual_005f1.doEndTag() == 5) {
/*  482 */                 this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f1); return;
/*      */               }
/*      */               
/*  485 */               this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f1);
/*  486 */               out.write(" </td>\n        </tr>\n      </table>\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n<td width=\"25%\" class=\"label-align\">&nbsp; ");
/*  487 */               out.print(FormatUtil.getString("am.webclient.threshold.thresholdname"));
/*  488 */               out.write("<span class=\"mandatory\">*</span></td>\n<td width=\"75%\" class=\"bodytext\" colspan=\"2\">\n");
/*  489 */               if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  491 */               out.write("\n</td>\n</tr>\n      </table>\n\n<table width=\"99%\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\" border=\"0\">\n  <tr>\n\n  <td width=\"100%\">\n  <table id=\"criticaltable\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n  <tr>\n    <td width=\"25%\" class=\"bodytext label-align\" align=\"left\"><img src=\"../images/icon_critical.gif\" width=\"13\" height=\"13\" style=\"position:relative; top:3px;\">&nbsp;<b>");
/*  492 */               out.print(FormatUtil.getString("am.webclient.threshold.criticalseverity"));
/*  493 */               out.write("</b></td>\n   \t<td class=\"bodytext\" width=\"45%\">");
/*  494 */               out.print(FormatUtil.getString("am.webclient.threshold.monitoredvalue"));
/*  495 */               out.write(32);
/*  496 */               if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  498 */               out.write(32);
/*  499 */               out.write(10);
/*  500 */               out.write(9);
/*  501 */               out.print(FormatUtil.getString("am.webclient.threshold.limit"));
/*  502 */               out.write(10);
/*  503 */               out.write(9);
/*  504 */               if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  506 */               out.write("</td>\n\t<td width=\"25%\"><div id=floatNote>");
/*  507 */               out.print(FormatUtil.getString("am.fault.floatthreshold.help"));
/*  508 */               out.write("</div></td>\n");
/*      */               
/*  510 */               EqualTag _jspx_th_logic_005fequal_005f4 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/*  511 */               _jspx_th_logic_005fequal_005f4.setPageContext(_jspx_page_context);
/*  512 */               _jspx_th_logic_005fequal_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  514 */               _jspx_th_logic_005fequal_005f4.setName("AMActionForm");
/*      */               
/*  516 */               _jspx_th_logic_005fequal_005f4.setProperty("secondarycriticalexist");
/*      */               
/*  518 */               _jspx_th_logic_005fequal_005f4.setValue("false");
/*  519 */               int _jspx_eval_logic_005fequal_005f4 = _jspx_th_logic_005fequal_005f4.doStartTag();
/*  520 */               if (_jspx_eval_logic_005fequal_005f4 != 0) {
/*      */                 for (;;) {
/*  522 */                   out.write("\n\t<td  align=\"left\" width=\"5%\"><a style=\"display:block;\" id=\"addmorecritical\" href=\"javascript:void(0)\" class=\"staticlinks\" nowrap>");
/*  523 */                   out.print(FormatUtil.getString("webclient.common.searchcomponent.morebutton.text"));
/*  524 */                   out.write("</a></td>\n   </tr>\n    <tr id=\"criticalrow_secondary\" style=\"display: none;\">\n");
/*  525 */                   int evalDoAfterBody = _jspx_th_logic_005fequal_005f4.doAfterBody();
/*  526 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  530 */               if (_jspx_th_logic_005fequal_005f4.doEndTag() == 5) {
/*  531 */                 this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f4); return;
/*      */               }
/*      */               
/*  534 */               this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f4);
/*  535 */               out.write(10);
/*      */               
/*  537 */               EqualTag _jspx_th_logic_005fequal_005f5 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/*  538 */               _jspx_th_logic_005fequal_005f5.setPageContext(_jspx_page_context);
/*  539 */               _jspx_th_logic_005fequal_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  541 */               _jspx_th_logic_005fequal_005f5.setName("AMActionForm");
/*      */               
/*  543 */               _jspx_th_logic_005fequal_005f5.setProperty("secondarycriticalexist");
/*      */               
/*  545 */               _jspx_th_logic_005fequal_005f5.setValue("true");
/*  546 */               int _jspx_eval_logic_005fequal_005f5 = _jspx_th_logic_005fequal_005f5.doStartTag();
/*  547 */               if (_jspx_eval_logic_005fequal_005f5 != 0) {
/*      */                 for (;;) {
/*  549 */                   out.write("\n\t<td  align=\"left\" width=\"25%\" colspan=\"4\" ><a style=\"display:none;\" id=\"addmorecritical\" href=\"javascript:void(0)\" class=\"staticlinks\" nowrap>");
/*  550 */                   out.print(FormatUtil.getString("webclient.common.searchcomponent.morebutton.text"));
/*  551 */                   out.write("</a></td>\n   </tr>\n    <tr id=\"criticalrow_secondary\" style=\"display: table-row;\">\n");
/*  552 */                   int evalDoAfterBody = _jspx_th_logic_005fequal_005f5.doAfterBody();
/*  553 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  557 */               if (_jspx_th_logic_005fequal_005f5.doEndTag() == 5) {
/*  558 */                 this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f5); return;
/*      */               }
/*      */               
/*  561 */               this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f5);
/*  562 */               out.write("\n    <td align=\"right\">    \n\t");
/*  563 */               if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  565 */               out.write("\n    </td>\n\n        <td class=\"bodytext\" colspan=\"2\">");
/*  566 */               out.print(FormatUtil.getString("am.webclient.threshold.monitoredvalue"));
/*  567 */               out.write(32);
/*  568 */               if (_jspx_meth_html_005fselect_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  570 */               out.write("\n        ");
/*  571 */               out.print(FormatUtil.getString("am.webclient.threshold.limit"));
/*  572 */               out.write("\n        ");
/*  573 */               if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  575 */               out.write("</td>\n        <td id=\"criticalclose\" align=\"left\"><img src=\"/images/prereq_notconfigured.gif\" /></td>\n   </tr>\n\n\n   <tr>\n   <td class=\"bodytext label-align\">&nbsp;</td>\n    <td class=\"bodytext\" colspan=\"3\" align=\"left\" style=\"height:30px;\"><input style=\"position:relative;\" name=\"checkbox1\" ");
/*  576 */               out.print(com.adventnet.appmanager.fault.FaultUtil.ADVANCED_USER ? "checked" : "");
/*  577 */               out.write(" type=\"checkbox\" ");
/*  578 */               out.print(com.adventnet.appmanager.fault.FaultUtil.ADVANCED_USER ? "checked" : "");
/*  579 */               out.write(" onclick=\"javascript:showAdvancedOptions();\"> ");
/*  580 */               out.print(FormatUtil.getString("am.webclient.threshold.advancedoption"));
/*  581 */               out.write("</td>\n   </tr>\n   <tr>\n    <td valign=\"top\" class=\"bodytext label-align\"><div id=\"showMessage1\" style=\"display:");
/*  582 */               out.print(com.adventnet.appmanager.fault.FaultUtil.ADVANCED_USER ? "block" : "none");
/*  583 */               out.write(59);
/*  584 */               out.write(34);
/*  585 */               out.write(62);
/*  586 */               out.print(FormatUtil.getString("am.webclient.threshold.message"));
/*  587 */               out.write("</div></td>\n    <td  valign=\"top\" class=\"bodytext\" colspan=\"3\"><div id=\"showMessage2\" style=\"display:");
/*  588 */               out.print(com.adventnet.appmanager.fault.FaultUtil.ADVANCED_USER ? "block" : "none");
/*  589 */               out.write(59);
/*  590 */               out.write(34);
/*  591 */               out.write(62);
/*  592 */               if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  594 */               out.write("</div></td>\n   </tr>\n    ");
/*  595 */               if (!isdelegatedAdmin) {
/*  596 */                 out.write("\n   <tr>\n    <td class=\"bodytext label-align\">\n    \t<div id=\"showRetryPolls1\" style=\"display:");
/*  597 */                 out.print(com.adventnet.appmanager.fault.FaultUtil.ADVANCED_USER ? "block" : "none");
/*  598 */                 out.write(59);
/*  599 */                 out.write(34);
/*  600 */                 out.write(62);
/*  601 */                 out.print(FormatUtil.getString("am.webclient.threshold.pollstotry"));
/*  602 */                 out.write("</div>\n    </td>\n    <td class=\"bodytext\" colspan=\"3\">\n    \t<div id=\"showRetryPolls2\" style=\"display:");
/*  603 */                 out.print(com.adventnet.appmanager.fault.FaultUtil.ADVANCED_USER ? "block" : "none");
/*  604 */                 out.write(";\">\n    \t");
/*  605 */                 if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                   return;
/*  607 */                 out.write("&nbsp;");
/*  608 */                 out.print(FormatUtil.getString("am.webclient.threshold.out.text"));
/*  609 */                 out.write("&nbsp;");
/*  610 */                 out.print(FormatUtil.getString("of"));
/*  611 */                 out.write("&nbsp;&nbsp;");
/*  612 */                 if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                   return;
/*  614 */                 out.write("&nbsp;");
/*  615 */                 out.print(FormatUtil.getString("am.webclient.common.consecutive.polls.text"));
/*  616 */                 out.write("\n    \t</div>\n    </td>\n   </tr>\n   ");
/*      */               }
/*  618 */               out.write("\n   </table>\n   </td></tr>\n\n\n   <tr><td height=\"2\">\n   <div id=\"showDetails\" style=\"display:");
/*  619 */               out.print(com.adventnet.appmanager.fault.FaultUtil.ADVANCED_USER ? "block" : "none");
/*  620 */               out.write(";\">\n\n\n\n        <table cellpadding=\"5\" cellspacing=\"0\" border=\"0\" width=\"100%\" class=\"dottedline-top\">\n          <tr>\n            <td class=\"bodytext label-align\" width=\"25%\"><img src=\"../images/icon_warning.gif\" width=\"13\" height=\"13\" style=\"position:relative; top:3px;\">&nbsp;<b>");
/*  621 */               out.print(FormatUtil.getString("am.webclient.threshold.warningseverity"));
/*  622 */               out.write("</b></td>\n            <td class=\"bodytext\" width=\"60%\">");
/*  623 */               out.print(FormatUtil.getString("am.webclient.threshold.monitoredvalue"));
/*  624 */               out.write(32);
/*  625 */               if (_jspx_meth_html_005fselect_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  627 */               out.write(" \n\t\t\t  ");
/*  628 */               out.print(FormatUtil.getString("am.webclient.threshold.limit"));
/*  629 */               out.write("\n           ");
/*  630 */               if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  632 */               out.write("</td>\n\t   <td align=\"left\" width=\"15%\"><a id=\"addmorewarning\" href=\"javascript:void(0)\" class=\"staticlinks\" nowrap>");
/*  633 */               out.print(FormatUtil.getString("webclient.common.searchcomponent.morebutton.text"));
/*  634 */               out.write("</a></td>\n          </tr>\n\t  <tr id=\"warningrow_secondary\" style=\"display: none;\">\n    <td width=\"25%\" align=\"right\">\n        ");
/*  635 */               if (_jspx_meth_html_005fselect_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  637 */               out.write("\n    </td>\n\n        <td width=\"50%\" class=\"bodytext\" >");
/*  638 */               out.print(FormatUtil.getString("am.webclient.threshold.monitoredvalue"));
/*  639 */               out.write(32);
/*  640 */               if (_jspx_meth_html_005fselect_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  642 */               out.write("\n        ");
/*  643 */               out.print(FormatUtil.getString("am.webclient.threshold.limit"));
/*  644 */               out.write("\n        ");
/*  645 */               if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  647 */               out.write("</td>\n        <td id=\"warningclose\" align=\"left\" width=\"25%\"><img src=\"/images/prereq_notconfigured.gif\" /></td>\n   </tr>\n\n          <tr>\n            <td valign=\"top\" width=\"25%\" class=\"bodytext label-align\">");
/*  648 */               out.print(FormatUtil.getString("am.webclient.threshold.message"));
/*  649 */               out.write("</td>\n            <td class=\"bodytext\" colspan=\"4\">");
/*  650 */               if (_jspx_meth_html_005ftextarea_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  652 */               out.write("\n            </td>\n          </tr>\n           ");
/*  653 */               if (!isdelegatedAdmin) {
/*  654 */                 out.write("\n          <tr>\n            <td width=\"25%\" class=\"bodytext label-align padd-bottom\">");
/*  655 */                 out.print(FormatUtil.getString("am.webclient.threshold.pollstotry"));
/*  656 */                 out.write("</td>\n            <td class=\"bodytext padd-bottom\" colspan=\"4\">\n            \t");
/*  657 */                 if (_jspx_meth_html_005ftext_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                   return;
/*  659 */                 out.write("&nbsp;");
/*  660 */                 out.print(FormatUtil.getString("am.webclient.threshold.out.text"));
/*  661 */                 out.write("&nbsp;");
/*  662 */                 out.print(FormatUtil.getString("of"));
/*  663 */                 out.write("&nbsp;&nbsp;");
/*  664 */                 if (_jspx_meth_html_005ftext_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                   return;
/*  666 */                 out.write("&nbsp;");
/*  667 */                 out.print(FormatUtil.getString("am.webclient.common.consecutive.polls.text"));
/*  668 */                 out.write("\n            </td>\n          </tr>\n          ");
/*      */               }
/*  670 */               out.write("\n          <tr>\n            <td width=\"25%\" class=\"bodytext label-align dottedline-top\"><img src=\"../images/icon_clear.gif\" width=\"13\" height=\"13\" style=\"position:relative; top:3px;\">&nbsp;<b>");
/*  671 */               out.print(FormatUtil.getString("am.webclient.threshold.clearseverity"));
/*  672 */               out.write("</b></td>\n          \n          <td width=\"60%\" class=\"bodytext dottedline-top\" >");
/*  673 */               out.print(FormatUtil.getString("am.webclient.threshold.monitoredvalue"));
/*  674 */               out.write(" \n          ");
/*  675 */               if (_jspx_meth_html_005fselect_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  677 */               out.write("&nbsp;\n           ");
/*  678 */               out.print(FormatUtil.getString("am.webclient.threshold.limit"));
/*  679 */               out.write("\n            ");
/*  680 */               if (_jspx_meth_html_005ftext_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  682 */               out.write("</td>\n\t  <td align=\"left\" class=\"bodytext dottedline-top\" width=\"15%\"><a id=\"addmoreinfo\" href=\"javascript:void(0)\" class=\"staticlinks\" nowrap>");
/*  683 */               out.print(FormatUtil.getString("webclient.common.searchcomponent.morebutton.text"));
/*  684 */               out.write("</a></td>\n          </tr>\n\t     <tr id=\"inforow_secondary\" style=\"display: none;\">\n    <td width=\"25%\" align=\"right\">&nbsp;\n        ");
/*  685 */               if (_jspx_meth_html_005fselect_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  687 */               out.write("\n    </td><td width=\"50%\" class=\"bodytext\" >");
/*  688 */               out.print(FormatUtil.getString("am.webclient.threshold.monitoredvalue"));
/*  689 */               out.write(32);
/*  690 */               if (_jspx_meth_html_005fselect_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  692 */               out.write("\n        ");
/*  693 */               out.print(FormatUtil.getString("am.webclient.threshold.limit"));
/*  694 */               out.write("\n        ");
/*  695 */               if (_jspx_meth_html_005ftext_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  697 */               out.write("\n        </td>\n        <td id=\"infoclose\" align=\"left\" width=\"25%\"><img src=\"/images/prereq_notconfigured.gif\" /></td>\n          </tr>\n          <tr>\n            <td valign=\"top\" width=\"25%\" class=\"bodytext label-align\">");
/*  698 */               out.print(FormatUtil.getString("am.webclient.threshold.message"));
/*  699 */               out.write("</td>\n            <td width=\"75%\" colspan=\"3\" class=\"bodytext\" >");
/*  700 */               if (_jspx_meth_html_005ftextarea_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  702 */               out.write("\n            </td>\n          </tr>\n           ");
/*  703 */               if (!isdelegatedAdmin) {
/*  704 */                 out.write("\n          <tr>\n            <td class=\"bodytext label-align padd-bottom\" width=\"25%\">");
/*  705 */                 out.print(FormatUtil.getString("am.webclient.threshold.pollstotry"));
/*  706 */                 out.write("</td>\n            <td class=\"bodytext padd-bottom\" colspan=\"3\" width=\"75%\">\n            \t");
/*  707 */                 if (_jspx_meth_html_005ftext_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                   return;
/*  709 */                 out.write("&nbsp;");
/*  710 */                 out.print(FormatUtil.getString("am.webclient.threshold.out.text"));
/*  711 */                 out.write("&nbsp;");
/*  712 */                 out.print(FormatUtil.getString("of"));
/*  713 */                 out.write("&nbsp;&nbsp;");
/*  714 */                 if (_jspx_meth_html_005ftext_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                   return;
/*  716 */                 out.write("&nbsp;");
/*  717 */                 out.print(FormatUtil.getString("am.webclient.common.consecutive.polls.text"));
/*  718 */                 out.write("\n            </td>\n          </tr>\n          ");
/*      */               }
/*  720 */               out.write("\n          <tr>\n            <td height=\"58\" width=\"25%\" class=\"bodytext label-align dottedline-top\">");
/*  721 */               out.print(FormatUtil.getString("am.webclient.threshold.description"));
/*  722 */               out.write("</td>\n            <td colspan=\"3\" width=\"75%\" class=\"bodytext dottedline-top\" >");
/*  723 */               if (_jspx_meth_html_005ftextarea_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                 return;
/*  725 */               out.write("</td>\n          </tr>\n        </table>\n\n   </div>\n   </td></tr>\n   </table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t<tr>\n    <td width=\"25%\" class=\"tablebottom\"></td>\n\t<td height=\"31\" colspan=\"3\" class=\"tablebottom\">\n\t");
/*      */               
/*  727 */               EqualTag _jspx_th_logic_005fequal_005f6 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/*  728 */               _jspx_th_logic_005fequal_005f6.setPageContext(_jspx_page_context);
/*  729 */               _jspx_th_logic_005fequal_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  731 */               _jspx_th_logic_005fequal_005f6.setName("AMActionForm");
/*      */               
/*  733 */               _jspx_th_logic_005fequal_005f6.setProperty("method");
/*      */               
/*  735 */               _jspx_th_logic_005fequal_005f6.setValue("editThresholdAction");
/*  736 */               int _jspx_eval_logic_005fequal_005f6 = _jspx_th_logic_005fequal_005f6.doStartTag();
/*  737 */               if (_jspx_eval_logic_005fequal_005f6 != 0) {
/*      */                 for (;;) {
/*  739 */                   out.write("\n\t    ");
/*      */                   
/*  741 */                   org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f0 = (org.apache.taglibs.standard.tag.common.core.ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
/*  742 */                   _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  743 */                   _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_logic_005fequal_005f6);
/*  744 */                   int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  745 */                   if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                     for (;;) {
/*  747 */                       out.write(10);
/*  748 */                       out.write(9);
/*  749 */                       out.write(9);
/*      */                       
/*  751 */                       org.apache.taglibs.standard.tag.el.core.WhenTag _jspx_th_c_005fwhen_005f0 = (org.apache.taglibs.standard.tag.el.core.WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(org.apache.taglibs.standard.tag.el.core.WhenTag.class);
/*  752 */                       _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  753 */                       _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/*  755 */                       _jspx_th_c_005fwhen_005f0.setTest("${!adminThresholdConfig}");
/*  756 */                       int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  757 */                       if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                         for (;;) {
/*  759 */                           out.write("\n\t\t    ");
/*      */                           
/*  761 */                           EqualTag _jspx_th_logic_005fequal_005f7 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/*  762 */                           _jspx_th_logic_005fequal_005f7.setPageContext(_jspx_page_context);
/*  763 */                           _jspx_th_logic_005fequal_005f7.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/*  765 */                           _jspx_th_logic_005fequal_005f7.setName("AMActionForm");
/*      */                           
/*  767 */                           _jspx_th_logic_005fequal_005f7.setProperty("isEditAllowed");
/*      */                           
/*  769 */                           _jspx_th_logic_005fequal_005f7.setValue("true");
/*  770 */                           int _jspx_eval_logic_005fequal_005f7 = _jspx_th_logic_005fequal_005f7.doStartTag();
/*  771 */                           if (_jspx_eval_logic_005fequal_005f7 != 0) {
/*      */                             for (;;) {
/*  773 */                               out.write("\n\t\t\t    <input name=\"button\" value=\"");
/*  774 */                               out.print(FormatUtil.getString("am.webclient.threshold.updateprofile"));
/*  775 */                               out.write("\" type=\"button\" class=\"buttons  btn_highlt\"  onClick=\"javascript:validateAndSubmit(1)\">\n\t\t    ");
/*  776 */                               int evalDoAfterBody = _jspx_th_logic_005fequal_005f7.doAfterBody();
/*  777 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  781 */                           if (_jspx_th_logic_005fequal_005f7.doEndTag() == 5) {
/*  782 */                             this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f7); return;
/*      */                           }
/*      */                           
/*  785 */                           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f7);
/*  786 */                           out.write("\n\t\t    ");
/*      */                           
/*  788 */                           EqualTag _jspx_th_logic_005fequal_005f8 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/*  789 */                           _jspx_th_logic_005fequal_005f8.setPageContext(_jspx_page_context);
/*  790 */                           _jspx_th_logic_005fequal_005f8.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                           
/*  792 */                           _jspx_th_logic_005fequal_005f8.setName("AMActionForm");
/*      */                           
/*  794 */                           _jspx_th_logic_005fequal_005f8.setProperty("isEditAllowed");
/*      */                           
/*  796 */                           _jspx_th_logic_005fequal_005f8.setValue("false");
/*  797 */                           int _jspx_eval_logic_005fequal_005f8 = _jspx_th_logic_005fequal_005f8.doStartTag();
/*  798 */                           if (_jspx_eval_logic_005fequal_005f8 != 0) {
/*      */                             for (;;) {
/*  800 */                               out.write("\n\t\t\t  <p class=\"bg-info\">");
/*  801 */                               out.print(FormatUtil.getString("am.webclient.delegatedadmin.notauthorised.update.message"));
/*  802 */                               out.write("</p>&nbsp;\n\t\t    ");
/*  803 */                               int evalDoAfterBody = _jspx_th_logic_005fequal_005f8.doAfterBody();
/*  804 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  808 */                           if (_jspx_th_logic_005fequal_005f8.doEndTag() == 5) {
/*  809 */                             this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f8); return;
/*      */                           }
/*      */                           
/*  812 */                           this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f8);
/*  813 */                           out.write(10);
/*  814 */                           out.write(9);
/*  815 */                           out.write(9);
/*  816 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  817 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  821 */                       if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  822 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                       }
/*      */                       
/*  825 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  826 */                       out.write(10);
/*  827 */                       out.write(9);
/*  828 */                       out.write(9);
/*      */                       
/*  830 */                       org.apache.taglibs.standard.tag.common.core.OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (org.apache.taglibs.standard.tag.common.core.OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(org.apache.taglibs.standard.tag.common.core.OtherwiseTag.class);
/*  831 */                       _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  832 */                       _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  833 */                       int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  834 */                       if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                         for (;;) {
/*  836 */                           out.write("\n\t\t\t&nbsp;<p class=\"bg-info\">");
/*  837 */                           out.print(FormatUtil.getString("am.webclient.mas.threshold.fromaam.message"));
/*  838 */                           out.write("</p>&nbsp;\n\t\t");
/*  839 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  840 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  844 */                       if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  845 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                       }
/*      */                       
/*  848 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  849 */                       out.write("\n\t    ");
/*  850 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  851 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  855 */                   if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  856 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                   }
/*      */                   
/*  859 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  860 */                   out.write(10);
/*  861 */                   out.write(9);
/*  862 */                   int evalDoAfterBody = _jspx_th_logic_005fequal_005f6.doAfterBody();
/*  863 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  867 */               if (_jspx_th_logic_005fequal_005f6.doEndTag() == 5) {
/*  868 */                 this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f6); return;
/*      */               }
/*      */               
/*  871 */               this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f6);
/*  872 */               out.write(10);
/*  873 */               out.write(10);
/*  874 */               out.write(9);
/*      */               
/*  876 */               NotEqualTag _jspx_th_logic_005fnotEqual_005f2 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(NotEqualTag.class);
/*  877 */               _jspx_th_logic_005fnotEqual_005f2.setPageContext(_jspx_page_context);
/*  878 */               _jspx_th_logic_005fnotEqual_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  880 */               _jspx_th_logic_005fnotEqual_005f2.setName("AMActionForm");
/*      */               
/*  882 */               _jspx_th_logic_005fnotEqual_005f2.setProperty("method");
/*      */               
/*  884 */               _jspx_th_logic_005fnotEqual_005f2.setValue("editThresholdAction");
/*  885 */               int _jspx_eval_logic_005fnotEqual_005f2 = _jspx_th_logic_005fnotEqual_005f2.doStartTag();
/*  886 */               if (_jspx_eval_logic_005fnotEqual_005f2 != 0) {
/*      */                 for (;;) {
/*  888 */                   out.write(10);
/*  889 */                   out.write(9);
/*      */                   
/*  891 */                   IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  892 */                   _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  893 */                   _jspx_th_c_005fif_005f2.setParent(_jspx_th_logic_005fnotEqual_005f2);
/*      */                   
/*  895 */                   _jspx_th_c_005fif_005f2.setTest("${!empty param.returnpath}");
/*  896 */                   int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  897 */                   if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                     for (;;) {
/*  899 */                       out.write(10);
/*  900 */                       out.write(9);
/*      */                       
/*      */ 
/*  903 */                       if ((!request.getParameter("returnpath").equals("NULL")) && (!request.getParameter("returnpath").equals("/showTile.do?TileName=.ThresholdConf")))
/*      */                       {
/*      */ 
/*  906 */                         out.write("\n\n\t<input name=\"button\" value=\"");
/*  907 */                         out.print(FormatUtil.getString("am.webclient.threshold.createandreturn"));
/*  908 */                         out.write("\" type=\"button\" class=\"buttons btn_highlt\"  onClick=\"javascript:validateAndSubmit(1)\">\n\t");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/*      */ 
/*  914 */                         out.write("\n\t<input name=\"button\" value=\"");
/*  915 */                         out.print(FormatUtil.getString("am.webclient.threshold.create"));
/*  916 */                         out.write("\" type=\"button\" class=\"buttons btn_highlt\"  onClick=\"javascript:validateAndSubmit(1)\">\t&nbsp;\n\t");
/*      */                       }
/*      */                       
/*      */ 
/*  920 */                       out.write(10);
/*  921 */                       out.write(9);
/*  922 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  923 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  927 */                   if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  928 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                   }
/*      */                   
/*  931 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  932 */                   out.write(10);
/*  933 */                   out.write(9);
/*      */                   
/*  935 */                   IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  936 */                   _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  937 */                   _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fnotEqual_005f2);
/*      */                   
/*  939 */                   _jspx_th_c_005fif_005f3.setTest("${empty param.returnpath}");
/*  940 */                   int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  941 */                   if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                     for (;;) {
/*  943 */                       out.write("\n\t<input name=\"button\" value=\"");
/*  944 */                       out.print(FormatUtil.getString("am.webclient.threshold.create"));
/*  945 */                       out.write("\" type=\"button\" class=\"buttons btn_highlt\"  onClick=\"javascript:validateAndSubmit(1)\">\n\t");
/*  946 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  947 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  951 */                   if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  952 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                   }
/*      */                   
/*  955 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  956 */                   out.write(10);
/*  957 */                   out.write(9);
/*  958 */                   int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f2.doAfterBody();
/*  959 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  963 */               if (_jspx_th_logic_005fnotEqual_005f2.doEndTag() == 5) {
/*  964 */                 this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f2); return;
/*      */               }
/*      */               
/*  967 */               this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f2);
/*  968 */               out.write(10);
/*  969 */               out.write(9);
/*      */               
/*  971 */               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  972 */               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  973 */               _jspx_th_c_005fif_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  975 */               _jspx_th_c_005fif_005f4.setTest("${!empty param.wiz}");
/*  976 */               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  977 */               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                 for (;;) {
/*  979 */                   out.write("\n\t<input name=\"button\" value=\"");
/*  980 */                   out.print(FormatUtil.getString("am.webclient.threshold.createandgoto"));
/*  981 */                   out.write("\" type=\"button\" class=\"buttons btn_highlt\"  onClick=\"javascript:callStep5()\">\n\t");
/*  982 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  983 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  987 */               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  988 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */               }
/*      */               
/*  991 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  992 */               out.write("\n\t<input name=\"cancel\" type=\"hidden\" value=\"true\">\n\t");
/*      */               
/*  994 */               IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  995 */               _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  996 */               _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */               
/*  998 */               _jspx_th_c_005fif_005f5.setTest("${empty param.wiz}");
/*  999 */               int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1000 */               if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                 for (;;) {
/* 1002 */                   out.write(10);
/* 1003 */                   out.write(9);
/*      */                   
/* 1005 */                   IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1006 */                   _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1007 */                   _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f5);
/*      */                   
/* 1009 */                   _jspx_th_c_005fif_005f6.setTest("${!adminThresholdConfig}");
/* 1010 */                   int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1011 */                   if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                     for (;;) {
/* 1013 */                       out.write(10);
/* 1014 */                       out.write(9);
/* 1015 */                       out.write(32);
/*      */                       
/* 1017 */                       EqualTag _jspx_th_logic_005fequal_005f9 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 1018 */                       _jspx_th_logic_005fequal_005f9.setPageContext(_jspx_page_context);
/* 1019 */                       _jspx_th_logic_005fequal_005f9.setParent(_jspx_th_c_005fif_005f6);
/*      */                       
/* 1021 */                       _jspx_th_logic_005fequal_005f9.setName("AMActionForm");
/*      */                       
/* 1023 */                       _jspx_th_logic_005fequal_005f9.setProperty("isEditAllowed");
/*      */                       
/* 1025 */                       _jspx_th_logic_005fequal_005f9.setValue("true");
/* 1026 */                       int _jspx_eval_logic_005fequal_005f9 = _jspx_th_logic_005fequal_005f9.doStartTag();
/* 1027 */                       if (_jspx_eval_logic_005fequal_005f9 != 0) {
/*      */                         for (;;) {
/* 1029 */                           out.write("\n\t    <input name=\"button1\" type=\"reset\" class=\"buttons btn_reset\" value=\"");
/* 1030 */                           out.print(FormatUtil.getString("am.webclient.common.reset.text"));
/* 1031 */                           out.write("\">&nbsp;\n\t ");
/* 1032 */                           int evalDoAfterBody = _jspx_th_logic_005fequal_005f9.doAfterBody();
/* 1033 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1037 */                       if (_jspx_th_logic_005fequal_005f9.doEndTag() == 5) {
/* 1038 */                         this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f9); return;
/*      */                       }
/*      */                       
/* 1041 */                       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f9);
/* 1042 */                       out.write(10);
/* 1043 */                       out.write(9);
/* 1044 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1045 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1049 */                   if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1050 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                   }
/*      */                   
/* 1053 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1054 */                   out.write("\n\t<input name=\"button2\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 1055 */                   out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 1056 */                   out.write("\" onClick=\"javascript:history.back();\">\n\t");
/* 1057 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1058 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1062 */               if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1063 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */               }
/*      */               
/* 1066 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1067 */               out.write(" </td>\n\n\t</tr>\n\n\n      </table>\n      \n      </td>\n      <td width=\"30%\" valign=\"top\">\n\n\t ");
/* 1068 */               org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.configurealert.thresholdprofile.helpcard")), request.getCharacterEncoding()), out, false);
/* 1069 */               out.write(" \n\t</td>\n\t</tr>\n\t</table>\n      </div>\n\n<div id=\"anomalydiv\" style=\"display:none\">\n         ");
/* 1070 */               org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/AnomalyDetails.jsp", out, true);
/* 1071 */               out.write("\n</div>\n");
/* 1072 */               int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 1073 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1077 */           if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 1078 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */           }
/*      */           else {
/* 1081 */             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 1082 */             out.write(10);
/* 1083 */             out.write(32);
/*      */             
/* 1085 */             IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1086 */             _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 1087 */             _jspx_th_c_005fif_005f7.setParent(null);
/*      */             
/* 1089 */             _jspx_th_c_005fif_005f7.setTest("${!empty param.wiz}");
/* 1090 */             int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 1091 */             if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */               for (;;) {
/* 1093 */                 out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td colspan=\"3\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  </tr>\n  <tr>\n    <td width=\"2%\" background=\"/images/wiz_bg_graylind.gif\"><img src=\"../images/wiz_startimage_bottom.gif\" width=\"20\" height=\"19\"></td>\n    <td width=\"94%\" background=\"../images/wiz_bg_grayline_bottom.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"19\"></td>\n    <td width=\"4%\" align=\"right\" background=\"../images/wiz_bg_grayline_bottom.gif\"><img src=\"../images/wiz_endicon_bottom.gif\" width=\"32\" height=\"19\"></td>\n  </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td width=\"86%\" height=\"26\" align=\"center\" > <input type=\"button\" name=\"xx\" value=\"");
/* 1094 */                 out.print(FormatUtil.getString("am.webclient.common.skip.text"));
/* 1095 */                 out.write("\" class=\"buttons btn_reset\"  onClick=\"javascript:location.href='/showActionProfiles.do?method=getHAProfiles&haid=");
/* 1096 */                 if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                   return;
/* 1098 */                 out.write("&wiz=true'\">\n<input type=\"button\" name=\"xx1\" value=\"");
/* 1099 */                 out.print(FormatUtil.getString("am.webclient.threshold.finish"));
/* 1100 */                 out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:location.href='/showapplication.do?method=showApplication&haid=");
/* 1101 */                 if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                   return;
/* 1103 */                 out.write("'\">  </td>\n  </tr>\n</table>\n");
/* 1104 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1105 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1109 */             if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1110 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/*      */             }
/*      */             else {
/* 1113 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1114 */               out.write("\n<br><br>\n\n");
/*      */             }
/* 1116 */           } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1117 */         out = _jspx_out;
/* 1118 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1119 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 1120 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1123 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1129 */     PageContext pageContext = _jspx_page_context;
/* 1130 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1132 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1133 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1134 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 1136 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 1137 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1138 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 1140 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 1141 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1142 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1146 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1147 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1148 */       return true;
/*      */     }
/* 1150 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1151 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1156 */     PageContext pageContext = _jspx_page_context;
/* 1157 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1159 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1160 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 1161 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 1163 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 1164 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 1165 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 1167 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 1168 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1169 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1173 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1174 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1175 */       return true;
/*      */     }
/* 1177 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1178 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1183 */     PageContext pageContext = _jspx_page_context;
/* 1184 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1186 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1187 */     _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 1188 */     _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */     
/* 1190 */     _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 1191 */     int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 1192 */     if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */       for (;;) {
/* 1194 */         out.write("\n\tdocument.AMActionForm.returnpath.value='/showActionProfiles.do?method=getHAProfiles';\n\tvalidateAndSubmit(1);\n\t");
/* 1195 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 1196 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1200 */     if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 1201 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 1202 */       return true;
/*      */     }
/* 1204 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 1205 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1210 */     PageContext pageContext = _jspx_page_context;
/* 1211 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1213 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1214 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 1215 */     _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */     
/* 1217 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 1218 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 1219 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 1221 */         out.write("\n        alertUser();\n\t return;\n        ");
/* 1222 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 1223 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1227 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 1228 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1229 */       return true;
/*      */     }
/* 1231 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ferrors_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1237 */     PageContext pageContext = _jspx_page_context;
/* 1238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1240 */     org.apache.struts.taglib.html.ErrorsTag _jspx_th_html_005ferrors_005f0 = (org.apache.struts.taglib.html.ErrorsTag)this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.get(org.apache.struts.taglib.html.ErrorsTag.class);
/* 1241 */     _jspx_th_html_005ferrors_005f0.setPageContext(_jspx_page_context);
/* 1242 */     _jspx_th_html_005ferrors_005f0.setParent(null);
/* 1243 */     int _jspx_eval_html_005ferrors_005f0 = _jspx_th_html_005ferrors_005f0.doStartTag();
/* 1244 */     if (_jspx_th_html_005ferrors_005f0.doEndTag() == 5) {
/* 1245 */       this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/* 1246 */       return true;
/*      */     }
/* 1248 */     this._005fjspx_005ftagPool_005fhtml_005ferrors_005fnobody.reuse(_jspx_th_html_005ferrors_005f0);
/* 1249 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1254 */     PageContext pageContext = _jspx_page_context;
/* 1255 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1257 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 1258 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 1259 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1261 */     _jspx_th_am_005fhiddenparam_005f0.setName("returnpath");
/* 1262 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 1263 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 1264 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 1265 */       return true;
/*      */     }
/* 1267 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 1268 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1273 */     PageContext pageContext = _jspx_page_context;
/* 1274 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1276 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 1277 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/* 1278 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1280 */     _jspx_th_am_005fhiddenparam_005f1.setName("wiz");
/* 1281 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/* 1282 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/* 1283 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 1284 */       return true;
/*      */     }
/* 1286 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 1287 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1292 */     PageContext pageContext = _jspx_page_context;
/* 1293 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1295 */     HiddenParam _jspx_th_am_005fhiddenparam_005f2 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 1296 */     _jspx_th_am_005fhiddenparam_005f2.setPageContext(_jspx_page_context);
/* 1297 */     _jspx_th_am_005fhiddenparam_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1299 */     _jspx_th_am_005fhiddenparam_005f2.setName("haid");
/* 1300 */     int _jspx_eval_am_005fhiddenparam_005f2 = _jspx_th_am_005fhiddenparam_005f2.doStartTag();
/* 1301 */     if (_jspx_th_am_005fhiddenparam_005f2.doEndTag() == 5) {
/* 1302 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 1303 */       return true;
/*      */     }
/* 1305 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 1306 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1311 */     PageContext pageContext = _jspx_page_context;
/* 1312 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1314 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1315 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1316 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1318 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.wiz && empty param.returnpath}");
/* 1319 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1320 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 1322 */         out.write("\n<input type=\"hidden\" name=\"returnpath\" value=\"/showTile.do?TileName=.ThresholdConf\">\n");
/* 1323 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1324 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1328 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1329 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1330 */       return true;
/*      */     }
/* 1332 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1333 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fequal_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1338 */     PageContext pageContext = _jspx_page_context;
/* 1339 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1341 */     EqualTag _jspx_th_logic_005fequal_005f2 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 1342 */     _jspx_th_logic_005fequal_005f2.setPageContext(_jspx_page_context);
/* 1343 */     _jspx_th_logic_005fequal_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1345 */     _jspx_th_logic_005fequal_005f2.setName("AMActionForm");
/*      */     
/* 1347 */     _jspx_th_logic_005fequal_005f2.setProperty("method");
/*      */     
/* 1349 */     _jspx_th_logic_005fequal_005f2.setValue("editThresholdAction");
/* 1350 */     int _jspx_eval_logic_005fequal_005f2 = _jspx_th_logic_005fequal_005f2.doStartTag();
/* 1351 */     if (_jspx_eval_logic_005fequal_005f2 != 0) {
/*      */       for (;;) {
/* 1353 */         out.write(10);
/* 1354 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_logic_005fequal_005f2, _jspx_page_context))
/* 1355 */           return true;
/* 1356 */         out.write(10);
/* 1357 */         int evalDoAfterBody = _jspx_th_logic_005fequal_005f2.doAfterBody();
/* 1358 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1362 */     if (_jspx_th_logic_005fequal_005f2.doEndTag() == 5) {
/* 1363 */       this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2);
/* 1364 */       return true;
/*      */     }
/* 1366 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f2);
/* 1367 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_logic_005fequal_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1372 */     PageContext pageContext = _jspx_page_context;
/* 1373 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1375 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 1376 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 1377 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_logic_005fequal_005f2);
/*      */     
/* 1379 */     _jspx_th_html_005fhidden_005f0.setProperty("method");
/* 1380 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 1381 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 1382 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 1383 */       return true;
/*      */     }
/* 1385 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 1386 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEqual_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1391 */     PageContext pageContext = _jspx_page_context;
/* 1392 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1394 */     NotEqualTag _jspx_th_logic_005fnotEqual_005f0 = (NotEqualTag)this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.get(NotEqualTag.class);
/* 1395 */     _jspx_th_logic_005fnotEqual_005f0.setPageContext(_jspx_page_context);
/* 1396 */     _jspx_th_logic_005fnotEqual_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1398 */     _jspx_th_logic_005fnotEqual_005f0.setName("AMActionForm");
/*      */     
/* 1400 */     _jspx_th_logic_005fnotEqual_005f0.setProperty("method");
/*      */     
/* 1402 */     _jspx_th_logic_005fnotEqual_005f0.setValue("editThresholdAction");
/* 1403 */     int _jspx_eval_logic_005fnotEqual_005f0 = _jspx_th_logic_005fnotEqual_005f0.doStartTag();
/* 1404 */     if (_jspx_eval_logic_005fnotEqual_005f0 != 0) {
/*      */       for (;;) {
/* 1406 */         out.write("\n<input name=\"method\" type=\"hidden\" value=\"createThresholdAction\">\n");
/* 1407 */         int evalDoAfterBody = _jspx_th_logic_005fnotEqual_005f0.doAfterBody();
/* 1408 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1412 */     if (_jspx_th_logic_005fnotEqual_005f0.doEndTag() == 5) {
/* 1413 */       this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/* 1414 */       return true;
/*      */     }
/* 1416 */     this._005fjspx_005ftagPool_005flogic_005fnotEqual_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEqual_005f0);
/* 1417 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1422 */     PageContext pageContext = _jspx_page_context;
/* 1423 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1425 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 1426 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 1427 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1429 */     _jspx_th_html_005fhidden_005f1.setProperty("id");
/* 1430 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 1431 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 1432 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 1433 */       return true;
/*      */     }
/* 1435 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 1436 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1441 */     PageContext pageContext = _jspx_page_context;
/* 1442 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1444 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.get(HiddenTag.class);
/* 1445 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 1446 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1448 */     _jspx_th_html_005fhidden_005f2.setStyleId("secondarycriticalexist");
/*      */     
/* 1450 */     _jspx_th_html_005fhidden_005f2.setProperty("secondarycriticalexist");
/* 1451 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 1452 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 1453 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 1454 */       return true;
/*      */     }
/* 1456 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 1457 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1462 */     PageContext pageContext = _jspx_page_context;
/* 1463 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1465 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.get(HiddenTag.class);
/* 1466 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/* 1467 */     _jspx_th_html_005fhidden_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1469 */     _jspx_th_html_005fhidden_005f3.setStyleId("secondarywarningexist");
/*      */     
/* 1471 */     _jspx_th_html_005fhidden_005f3.setProperty("secondarywarningexist");
/* 1472 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/* 1473 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/* 1474 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 1475 */       return true;
/*      */     }
/* 1477 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 1478 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1483 */     PageContext pageContext = _jspx_page_context;
/* 1484 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1486 */     HiddenTag _jspx_th_html_005fhidden_005f4 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.get(HiddenTag.class);
/* 1487 */     _jspx_th_html_005fhidden_005f4.setPageContext(_jspx_page_context);
/* 1488 */     _jspx_th_html_005fhidden_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1490 */     _jspx_th_html_005fhidden_005f4.setStyleId("secondaryinfoexist");
/*      */     
/* 1492 */     _jspx_th_html_005fhidden_005f4.setProperty("secondaryinfoexist");
/* 1493 */     int _jspx_eval_html_005fhidden_005f4 = _jspx_th_html_005fhidden_005f4.doStartTag();
/* 1494 */     if (_jspx_th_html_005fhidden_005f4.doEndTag() == 5) {
/* 1495 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 1496 */       return true;
/*      */     }
/* 1498 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 1499 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1504 */     PageContext pageContext = _jspx_page_context;
/* 1505 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1507 */     org.apache.taglibs.standard.tag.common.core.CatchTag _jspx_th_c_005fcatch_005f0 = (org.apache.taglibs.standard.tag.common.core.CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(org.apache.taglibs.standard.tag.common.core.CatchTag.class);
/* 1508 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 1509 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1511 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 1512 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 1514 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 1515 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 1517 */           out.write(" \n      <fmt:parseNumber var=\"wnhaid\" value=\"${param.haid}\"/> \n");
/* 1518 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 1519 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1523 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 1524 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1527 */         int tmp145_144 = 0; int[] tmp145_142 = _jspx_push_body_count_c_005fcatch_005f0; int tmp147_146 = tmp145_142[tmp145_144];tmp145_142[tmp145_144] = (tmp147_146 - 1); if (tmp147_146 <= 0) break;
/* 1528 */         out = _jspx_page_context.popBody(); }
/* 1529 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1531 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 1532 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 1534 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1539 */     PageContext pageContext = _jspx_page_context;
/* 1540 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1542 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1543 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1544 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1546 */     _jspx_th_c_005fif_005f1.setTest("${(uri=='/jsp/CreateApplication.jsp')||(!empty param.wiz && !empty param.haid && (empty invalidhaid))}");
/* 1547 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1548 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 1550 */         out.write("   \n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"28%\">&nbsp;</td>\n    <td width=\"9%\" class=\"selectedmenu\">Threshold</td>\n    <td width=\"2%\"><img src=\"/images/wiz_separator.gif\" width=\"3\" height=\"17\"></td>\n    <td width=\"12%\"><a href=\"/showTile.do?TileName=.EmailActions&haid=null\" class=\"Staticlinks\">Email \n      Action</a></td>\n    <td width=\"2%\"><img src=\"/images/wiz_separator.gif\" width=\"3\" height=\"17\"></td>\n    <td width=\"11%\"><a href=\"/showTile.do?TileName=.SMSActions&haid=null\" class=\"Staticlinks\">SMS \n      Action</a> </td>\n    <td width=\"2%\"><img src=\"/images/wiz_separator.gif\" width=\"3\" height=\"17\"></td>\n    <td width=\"16%\"><a href=\"/showTile.do?TileName=.ExecProg&haid=null\"  class=\"Staticlinks\">Program \n      Exec. Action</a></td>\n    <td width=\"2%\"><img src=\"/images/wiz_separator.gif\" width=\"3\" height=\"17\"></td>\n    <td width=\"16%\"><a href=\"adminAction.do?method=reloadSendTrapActionForm\" class=\"Staticlinks\" >Trap \n      Action</a></td>\n  </tr>\n</table>\n\n");
/* 1551 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1552 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1556 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1557 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1558 */       return true;
/*      */     }
/* 1560 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1561 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1566 */     PageContext pageContext = _jspx_page_context;
/* 1567 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1569 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 1570 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 1571 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1573 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 1575 */     _jspx_th_html_005ftext_005f0.setSize("50");
/*      */     
/* 1577 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/*      */     
/* 1579 */     _jspx_th_html_005ftext_005f0.setMaxlength("100");
/* 1580 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 1581 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 1582 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 1583 */       return true;
/*      */     }
/* 1585 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 1586 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1591 */     PageContext pageContext = _jspx_page_context;
/* 1592 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1594 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1595 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 1596 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1598 */     _jspx_th_html_005fselect_005f0.setProperty("criticalthresholdcondition");
/*      */     
/* 1600 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext small");
/*      */     
/* 1602 */     _jspx_th_html_005fselect_005f0.setStyle("width: 50px;");
/* 1603 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 1604 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 1605 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1606 */         out = _jspx_page_context.pushBody();
/* 1607 */         _jspx_th_html_005fselect_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1608 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1611 */         out.write(10);
/* 1612 */         out.write(9);
/* 1613 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 1614 */           return true;
/* 1615 */         out.write(10);
/* 1616 */         out.write(9);
/* 1617 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 1618 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1621 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1622 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1625 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 1626 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 1627 */       return true;
/*      */     }
/* 1629 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f0);
/* 1630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1635 */     PageContext pageContext = _jspx_page_context;
/* 1636 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1638 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1639 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 1640 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 1642 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("conditions");
/* 1643 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 1644 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 1645 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 1646 */       return true;
/*      */     }
/* 1648 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 1649 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1654 */     PageContext pageContext = _jspx_page_context;
/* 1655 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1657 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 1658 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 1659 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1661 */     _jspx_th_html_005ftext_005f1.setProperty("criticalthresholdvalue");
/*      */     
/* 1663 */     _jspx_th_html_005ftext_005f1.setSize("5");
/*      */     
/* 1665 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext msmall");
/*      */     
/* 1667 */     _jspx_th_html_005ftext_005f1.setMaxlength("100");
/* 1668 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 1669 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 1670 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1671 */       return true;
/*      */     }
/* 1673 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1674 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1679 */     PageContext pageContext = _jspx_page_context;
/* 1680 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1682 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1683 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 1684 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1686 */     _jspx_th_html_005fselect_005f1.setProperty("criticalconditionjoiner");
/*      */     
/* 1688 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext small");
/*      */     
/* 1690 */     _jspx_th_html_005fselect_005f1.setStyle("width: 60px;");
/* 1691 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 1692 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 1693 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 1694 */         out = _jspx_page_context.pushBody();
/* 1695 */         _jspx_th_html_005fselect_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1696 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1699 */         out.write("\n        ");
/* 1700 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 1701 */           return true;
/* 1702 */         out.write("\n        ");
/* 1703 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 1704 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1707 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 1708 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1711 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 1712 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 1713 */       return true;
/*      */     }
/* 1715 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 1716 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1721 */     PageContext pageContext = _jspx_page_context;
/* 1722 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1724 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1725 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 1726 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 1728 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("conditionjoinerlist");
/* 1729 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 1730 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 1731 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 1732 */       return true;
/*      */     }
/* 1734 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 1735 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1740 */     PageContext pageContext = _jspx_page_context;
/* 1741 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1743 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1744 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 1745 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1747 */     _jspx_th_html_005fselect_005f2.setProperty("secondarycriticalthresholdcondition");
/*      */     
/* 1749 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext small");
/*      */     
/* 1751 */     _jspx_th_html_005fselect_005f2.setStyle("width: 50px;");
/* 1752 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 1753 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 1754 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 1755 */         out = _jspx_page_context.pushBody();
/* 1756 */         _jspx_th_html_005fselect_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1757 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1760 */         out.write("\n        ");
/* 1761 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 1762 */           return true;
/* 1763 */         out.write("\n        ");
/* 1764 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 1765 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1768 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 1769 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1772 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 1773 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 1774 */       return true;
/*      */     }
/* 1776 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 1777 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1782 */     PageContext pageContext = _jspx_page_context;
/* 1783 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1785 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1786 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 1787 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 1789 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("conditions");
/* 1790 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 1791 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 1792 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 1793 */       return true;
/*      */     }
/* 1795 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 1796 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1801 */     PageContext pageContext = _jspx_page_context;
/* 1802 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1804 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 1805 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 1806 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1808 */     _jspx_th_html_005ftext_005f2.setProperty("secondarycriticalthresholdvalue");
/*      */     
/* 1810 */     _jspx_th_html_005ftext_005f2.setSize("5");
/*      */     
/* 1812 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext msmall");
/*      */     
/* 1814 */     _jspx_th_html_005ftext_005f2.setMaxlength("100");
/* 1815 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 1816 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 1817 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 1818 */       return true;
/*      */     }
/* 1820 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 1821 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1826 */     PageContext pageContext = _jspx_page_context;
/* 1827 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1829 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.get(TextareaTag.class);
/* 1830 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 1831 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1833 */     _jspx_th_html_005ftextarea_005f0.setProperty("criticalthresholdmessage");
/*      */     
/* 1835 */     _jspx_th_html_005ftextarea_005f0.setCols("50");
/*      */     
/* 1837 */     _jspx_th_html_005ftextarea_005f0.setRows("3");
/*      */     
/* 1839 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea xlarge");
/*      */     
/* 1841 */     _jspx_th_html_005ftextarea_005f0.setOnfocus("document.AMActionForm.criticalthresholdmessage.select()");
/* 1842 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 1843 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 1844 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 1845 */       return true;
/*      */     }
/* 1847 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 1848 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1853 */     PageContext pageContext = _jspx_page_context;
/* 1854 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1856 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.get(TextTag.class);
/* 1857 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 1858 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1860 */     _jspx_th_html_005ftext_005f3.setProperty("consecutive_mincriticalpolls");
/*      */     
/* 1862 */     _jspx_th_html_005ftext_005f3.setOnfocus("changetext(this);");
/*      */     
/* 1864 */     _jspx_th_html_005ftext_005f3.setSize("20");
/*      */     
/* 1866 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext medium");
/*      */     
/* 1868 */     _jspx_th_html_005ftext_005f3.setMaxlength("60");
/* 1869 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 1870 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 1871 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 1872 */       return true;
/*      */     }
/* 1874 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 1875 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1880 */     PageContext pageContext = _jspx_page_context;
/* 1881 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1883 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.get(TextTag.class);
/* 1884 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 1885 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1887 */     _jspx_th_html_005ftext_005f4.setProperty("consecutive_criticalpolls");
/*      */     
/* 1889 */     _jspx_th_html_005ftext_005f4.setOnfocus("changetext(this);");
/*      */     
/* 1891 */     _jspx_th_html_005ftext_005f4.setSize("20");
/*      */     
/* 1893 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext medium");
/*      */     
/* 1895 */     _jspx_th_html_005ftext_005f4.setMaxlength("60");
/* 1896 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 1897 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 1898 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 1899 */       return true;
/*      */     }
/* 1901 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 1902 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1907 */     PageContext pageContext = _jspx_page_context;
/* 1908 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1910 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 1911 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 1912 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1914 */     _jspx_th_html_005fselect_005f3.setProperty("warningthresholdcondition");
/*      */     
/* 1916 */     _jspx_th_html_005fselect_005f3.setStyleClass("formtext msmall");
/* 1917 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 1918 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 1919 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 1920 */         out = _jspx_page_context.pushBody();
/* 1921 */         _jspx_th_html_005fselect_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1922 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1925 */         out.write("\n              ");
/* 1926 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 1927 */           return true;
/* 1928 */         out.write(32);
/* 1929 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 1930 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1933 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 1934 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1937 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 1938 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 1939 */       return true;
/*      */     }
/* 1941 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 1942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1947 */     PageContext pageContext = _jspx_page_context;
/* 1948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1950 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1951 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 1952 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 1954 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("conditions");
/* 1955 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 1956 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 1957 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 1958 */       return true;
/*      */     }
/* 1960 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 1961 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1966 */     PageContext pageContext = _jspx_page_context;
/* 1967 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1969 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 1970 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 1971 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1973 */     _jspx_th_html_005ftext_005f5.setProperty("warningthresholdvalue");
/*      */     
/* 1975 */     _jspx_th_html_005ftext_005f5.setSize("5");
/*      */     
/* 1977 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext msmall");
/*      */     
/* 1979 */     _jspx_th_html_005ftext_005f5.setMaxlength("100");
/* 1980 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 1981 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 1982 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 1983 */       return true;
/*      */     }
/* 1985 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 1986 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1991 */     PageContext pageContext = _jspx_page_context;
/* 1992 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1994 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 1995 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 1996 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1998 */     _jspx_th_html_005fselect_005f4.setProperty("warningconditionjoiner");
/*      */     
/* 2000 */     _jspx_th_html_005fselect_005f4.setStyleClass("formtext small");
/*      */     
/* 2002 */     _jspx_th_html_005fselect_005f4.setStyle("width: 60px;");
/* 2003 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 2004 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 2005 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 2006 */         out = _jspx_page_context.pushBody();
/* 2007 */         _jspx_th_html_005fselect_005f4.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2008 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2011 */         out.write("\n        ");
/* 2012 */         if (_jspx_meth_html_005foptionsCollection_005f4(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 2013 */           return true;
/* 2014 */         out.write("\n        ");
/* 2015 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 2016 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2019 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 2020 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2023 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 2024 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 2025 */       return true;
/*      */     }
/* 2027 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 2028 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f4(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2033 */     PageContext pageContext = _jspx_page_context;
/* 2034 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2036 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f4 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 2037 */     _jspx_th_html_005foptionsCollection_005f4.setPageContext(_jspx_page_context);
/* 2038 */     _jspx_th_html_005foptionsCollection_005f4.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 2040 */     _jspx_th_html_005foptionsCollection_005f4.setProperty("conditionjoinerlist");
/* 2041 */     int _jspx_eval_html_005foptionsCollection_005f4 = _jspx_th_html_005foptionsCollection_005f4.doStartTag();
/* 2042 */     if (_jspx_th_html_005foptionsCollection_005f4.doEndTag() == 5) {
/* 2043 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 2044 */       return true;
/*      */     }
/* 2046 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 2047 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2052 */     PageContext pageContext = _jspx_page_context;
/* 2053 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2055 */     SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 2056 */     _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 2057 */     _jspx_th_html_005fselect_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2059 */     _jspx_th_html_005fselect_005f5.setProperty("secondarywarningthresholdcondition");
/*      */     
/* 2061 */     _jspx_th_html_005fselect_005f5.setStyleClass("formtext small");
/*      */     
/* 2063 */     _jspx_th_html_005fselect_005f5.setStyle("width: 50px;");
/* 2064 */     int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 2065 */     if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 2066 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 2067 */         out = _jspx_page_context.pushBody();
/* 2068 */         _jspx_th_html_005fselect_005f5.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2069 */         _jspx_th_html_005fselect_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2072 */         out.write("\n        ");
/* 2073 */         if (_jspx_meth_html_005foptionsCollection_005f5(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/* 2074 */           return true;
/* 2075 */         out.write("\n        ");
/* 2076 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 2077 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2080 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 2081 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2084 */     if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 2085 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 2086 */       return true;
/*      */     }
/* 2088 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 2089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f5(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2094 */     PageContext pageContext = _jspx_page_context;
/* 2095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2097 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f5 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 2098 */     _jspx_th_html_005foptionsCollection_005f5.setPageContext(_jspx_page_context);
/* 2099 */     _jspx_th_html_005foptionsCollection_005f5.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 2101 */     _jspx_th_html_005foptionsCollection_005f5.setProperty("conditions");
/* 2102 */     int _jspx_eval_html_005foptionsCollection_005f5 = _jspx_th_html_005foptionsCollection_005f5.doStartTag();
/* 2103 */     if (_jspx_th_html_005foptionsCollection_005f5.doEndTag() == 5) {
/* 2104 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 2105 */       return true;
/*      */     }
/* 2107 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 2108 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2113 */     PageContext pageContext = _jspx_page_context;
/* 2114 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2116 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2117 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 2118 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2120 */     _jspx_th_html_005ftext_005f6.setProperty("secondarywarningthresholdvalue");
/*      */     
/* 2122 */     _jspx_th_html_005ftext_005f6.setSize("5");
/*      */     
/* 2124 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext msmall");
/*      */     
/* 2126 */     _jspx_th_html_005ftext_005f6.setMaxlength("100");
/* 2127 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 2128 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 2129 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 2130 */       return true;
/*      */     }
/* 2132 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 2133 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2138 */     PageContext pageContext = _jspx_page_context;
/* 2139 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2141 */     TextareaTag _jspx_th_html_005ftextarea_005f1 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.get(TextareaTag.class);
/* 2142 */     _jspx_th_html_005ftextarea_005f1.setPageContext(_jspx_page_context);
/* 2143 */     _jspx_th_html_005ftextarea_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2145 */     _jspx_th_html_005ftextarea_005f1.setProperty("warningthresholdmessage");
/*      */     
/* 2147 */     _jspx_th_html_005ftextarea_005f1.setCols("50");
/*      */     
/* 2149 */     _jspx_th_html_005ftextarea_005f1.setRows("3");
/*      */     
/* 2151 */     _jspx_th_html_005ftextarea_005f1.setStyleClass("formtextarea xlarge");
/*      */     
/* 2153 */     _jspx_th_html_005ftextarea_005f1.setOnfocus("document.AMActionForm.warningthresholdmessage.select()");
/* 2154 */     int _jspx_eval_html_005ftextarea_005f1 = _jspx_th_html_005ftextarea_005f1.doStartTag();
/* 2155 */     if (_jspx_th_html_005ftextarea_005f1.doEndTag() == 5) {
/* 2156 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 2157 */       return true;
/*      */     }
/* 2159 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 2160 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2165 */     PageContext pageContext = _jspx_page_context;
/* 2166 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2168 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.get(TextTag.class);
/* 2169 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 2170 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2172 */     _jspx_th_html_005ftext_005f7.setProperty("consecutive_minwarningpolls");
/*      */     
/* 2174 */     _jspx_th_html_005ftext_005f7.setOnfocus("changetext(this);");
/*      */     
/* 2176 */     _jspx_th_html_005ftext_005f7.setSize("20");
/*      */     
/* 2178 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext medium");
/*      */     
/* 2180 */     _jspx_th_html_005ftext_005f7.setMaxlength("60");
/* 2181 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 2182 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 2183 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 2184 */       return true;
/*      */     }
/* 2186 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 2187 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2192 */     PageContext pageContext = _jspx_page_context;
/* 2193 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2195 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.get(TextTag.class);
/* 2196 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/* 2197 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2199 */     _jspx_th_html_005ftext_005f8.setProperty("consecutive_warningpolls");
/*      */     
/* 2201 */     _jspx_th_html_005ftext_005f8.setOnfocus("changetext(this);");
/*      */     
/* 2203 */     _jspx_th_html_005ftext_005f8.setSize("20");
/*      */     
/* 2205 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext medium");
/*      */     
/* 2207 */     _jspx_th_html_005ftext_005f8.setMaxlength("60");
/* 2208 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/* 2209 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/* 2210 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 2211 */       return true;
/*      */     }
/* 2213 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 2214 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2219 */     PageContext pageContext = _jspx_page_context;
/* 2220 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2222 */     SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 2223 */     _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/* 2224 */     _jspx_th_html_005fselect_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2226 */     _jspx_th_html_005fselect_005f6.setProperty("infothresholdcondition");
/*      */     
/* 2228 */     _jspx_th_html_005fselect_005f6.setStyleClass("formtext msmall");
/*      */     
/* 2230 */     _jspx_th_html_005fselect_005f6.setStyle("width: 50px;");
/* 2231 */     int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/* 2232 */     if (_jspx_eval_html_005fselect_005f6 != 0) {
/* 2233 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 2234 */         out = _jspx_page_context.pushBody();
/* 2235 */         _jspx_th_html_005fselect_005f6.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2236 */         _jspx_th_html_005fselect_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2239 */         out.write("\n              ");
/* 2240 */         if (_jspx_meth_html_005foptionsCollection_005f6(_jspx_th_html_005fselect_005f6, _jspx_page_context))
/* 2241 */           return true;
/* 2242 */         out.write(32);
/* 2243 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/* 2244 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2247 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 2248 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2251 */     if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/* 2252 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f6);
/* 2253 */       return true;
/*      */     }
/* 2255 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f6);
/* 2256 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f6(JspTag _jspx_th_html_005fselect_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2261 */     PageContext pageContext = _jspx_page_context;
/* 2262 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2264 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f6 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 2265 */     _jspx_th_html_005foptionsCollection_005f6.setPageContext(_jspx_page_context);
/* 2266 */     _jspx_th_html_005foptionsCollection_005f6.setParent((Tag)_jspx_th_html_005fselect_005f6);
/*      */     
/* 2268 */     _jspx_th_html_005foptionsCollection_005f6.setProperty("conditions");
/* 2269 */     int _jspx_eval_html_005foptionsCollection_005f6 = _jspx_th_html_005foptionsCollection_005f6.doStartTag();
/* 2270 */     if (_jspx_th_html_005foptionsCollection_005f6.doEndTag() == 5) {
/* 2271 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 2272 */       return true;
/*      */     }
/* 2274 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 2275 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2280 */     PageContext pageContext = _jspx_page_context;
/* 2281 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2283 */     TextTag _jspx_th_html_005ftext_005f9 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2284 */     _jspx_th_html_005ftext_005f9.setPageContext(_jspx_page_context);
/* 2285 */     _jspx_th_html_005ftext_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2287 */     _jspx_th_html_005ftext_005f9.setProperty("infothresholdvalue");
/*      */     
/* 2289 */     _jspx_th_html_005ftext_005f9.setSize("5");
/*      */     
/* 2291 */     _jspx_th_html_005ftext_005f9.setStyleClass("formtext msmall");
/*      */     
/* 2293 */     _jspx_th_html_005ftext_005f9.setMaxlength("100");
/* 2294 */     int _jspx_eval_html_005ftext_005f9 = _jspx_th_html_005ftext_005f9.doStartTag();
/* 2295 */     if (_jspx_th_html_005ftext_005f9.doEndTag() == 5) {
/* 2296 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 2297 */       return true;
/*      */     }
/* 2299 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 2300 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2305 */     PageContext pageContext = _jspx_page_context;
/* 2306 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2308 */     SelectTag _jspx_th_html_005fselect_005f7 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 2309 */     _jspx_th_html_005fselect_005f7.setPageContext(_jspx_page_context);
/* 2310 */     _jspx_th_html_005fselect_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2312 */     _jspx_th_html_005fselect_005f7.setProperty("infoconditionjoiner");
/*      */     
/* 2314 */     _jspx_th_html_005fselect_005f7.setStyleClass("formtext small");
/*      */     
/* 2316 */     _jspx_th_html_005fselect_005f7.setStyle("width: 60px;");
/* 2317 */     int _jspx_eval_html_005fselect_005f7 = _jspx_th_html_005fselect_005f7.doStartTag();
/* 2318 */     if (_jspx_eval_html_005fselect_005f7 != 0) {
/* 2319 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 2320 */         out = _jspx_page_context.pushBody();
/* 2321 */         _jspx_th_html_005fselect_005f7.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2322 */         _jspx_th_html_005fselect_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2325 */         out.write("\n        ");
/* 2326 */         if (_jspx_meth_html_005foptionsCollection_005f7(_jspx_th_html_005fselect_005f7, _jspx_page_context))
/* 2327 */           return true;
/* 2328 */         out.write("\n        ");
/* 2329 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f7.doAfterBody();
/* 2330 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2333 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 2334 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2337 */     if (_jspx_th_html_005fselect_005f7.doEndTag() == 5) {
/* 2338 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f7);
/* 2339 */       return true;
/*      */     }
/* 2341 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f7);
/* 2342 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f7(JspTag _jspx_th_html_005fselect_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2347 */     PageContext pageContext = _jspx_page_context;
/* 2348 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2350 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f7 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 2351 */     _jspx_th_html_005foptionsCollection_005f7.setPageContext(_jspx_page_context);
/* 2352 */     _jspx_th_html_005foptionsCollection_005f7.setParent((Tag)_jspx_th_html_005fselect_005f7);
/*      */     
/* 2354 */     _jspx_th_html_005foptionsCollection_005f7.setProperty("conditionjoinerlist");
/* 2355 */     int _jspx_eval_html_005foptionsCollection_005f7 = _jspx_th_html_005foptionsCollection_005f7.doStartTag();
/* 2356 */     if (_jspx_th_html_005foptionsCollection_005f7.doEndTag() == 5) {
/* 2357 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f7);
/* 2358 */       return true;
/*      */     }
/* 2360 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f7);
/* 2361 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2366 */     PageContext pageContext = _jspx_page_context;
/* 2367 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2369 */     SelectTag _jspx_th_html_005fselect_005f8 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.get(SelectTag.class);
/* 2370 */     _jspx_th_html_005fselect_005f8.setPageContext(_jspx_page_context);
/* 2371 */     _jspx_th_html_005fselect_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2373 */     _jspx_th_html_005fselect_005f8.setProperty("secondaryinfothresholdcondition");
/*      */     
/* 2375 */     _jspx_th_html_005fselect_005f8.setStyleClass("formtext small");
/*      */     
/* 2377 */     _jspx_th_html_005fselect_005f8.setStyle("width: 50px;");
/* 2378 */     int _jspx_eval_html_005fselect_005f8 = _jspx_th_html_005fselect_005f8.doStartTag();
/* 2379 */     if (_jspx_eval_html_005fselect_005f8 != 0) {
/* 2380 */       if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 2381 */         out = _jspx_page_context.pushBody();
/* 2382 */         _jspx_th_html_005fselect_005f8.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2383 */         _jspx_th_html_005fselect_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2386 */         out.write("\n        ");
/* 2387 */         if (_jspx_meth_html_005foptionsCollection_005f8(_jspx_th_html_005fselect_005f8, _jspx_page_context))
/* 2388 */           return true;
/* 2389 */         out.write("\n        ");
/* 2390 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f8.doAfterBody();
/* 2391 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2394 */       if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 2395 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2398 */     if (_jspx_th_html_005fselect_005f8.doEndTag() == 5) {
/* 2399 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f8);
/* 2400 */       return true;
/*      */     }
/* 2402 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f8);
/* 2403 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f8(JspTag _jspx_th_html_005fselect_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2408 */     PageContext pageContext = _jspx_page_context;
/* 2409 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2411 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f8 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 2412 */     _jspx_th_html_005foptionsCollection_005f8.setPageContext(_jspx_page_context);
/* 2413 */     _jspx_th_html_005foptionsCollection_005f8.setParent((Tag)_jspx_th_html_005fselect_005f8);
/*      */     
/* 2415 */     _jspx_th_html_005foptionsCollection_005f8.setProperty("conditions");
/* 2416 */     int _jspx_eval_html_005foptionsCollection_005f8 = _jspx_th_html_005foptionsCollection_005f8.doStartTag();
/* 2417 */     if (_jspx_th_html_005foptionsCollection_005f8.doEndTag() == 5) {
/* 2418 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f8);
/* 2419 */       return true;
/*      */     }
/* 2421 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f8);
/* 2422 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2427 */     PageContext pageContext = _jspx_page_context;
/* 2428 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2430 */     TextTag _jspx_th_html_005ftext_005f10 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 2431 */     _jspx_th_html_005ftext_005f10.setPageContext(_jspx_page_context);
/* 2432 */     _jspx_th_html_005ftext_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2434 */     _jspx_th_html_005ftext_005f10.setProperty("secondaryinfothresholdvalue");
/*      */     
/* 2436 */     _jspx_th_html_005ftext_005f10.setSize("5");
/*      */     
/* 2438 */     _jspx_th_html_005ftext_005f10.setStyleClass("formtext msmall");
/*      */     
/* 2440 */     _jspx_th_html_005ftext_005f10.setMaxlength("100");
/* 2441 */     int _jspx_eval_html_005ftext_005f10 = _jspx_th_html_005ftext_005f10.doStartTag();
/* 2442 */     if (_jspx_th_html_005ftext_005f10.doEndTag() == 5) {
/* 2443 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 2444 */       return true;
/*      */     }
/* 2446 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 2447 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2452 */     PageContext pageContext = _jspx_page_context;
/* 2453 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2455 */     TextareaTag _jspx_th_html_005ftextarea_005f2 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.get(TextareaTag.class);
/* 2456 */     _jspx_th_html_005ftextarea_005f2.setPageContext(_jspx_page_context);
/* 2457 */     _jspx_th_html_005ftextarea_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2459 */     _jspx_th_html_005ftextarea_005f2.setProperty("infothresholdmessage");
/*      */     
/* 2461 */     _jspx_th_html_005ftextarea_005f2.setCols("50");
/*      */     
/* 2463 */     _jspx_th_html_005ftextarea_005f2.setRows("3");
/*      */     
/* 2465 */     _jspx_th_html_005ftextarea_005f2.setStyleClass("formtextarea xlarge");
/*      */     
/* 2467 */     _jspx_th_html_005ftextarea_005f2.setOnfocus("document.AMActionForm.infothresholdmessage.select()");
/* 2468 */     int _jspx_eval_html_005ftextarea_005f2 = _jspx_th_html_005ftextarea_005f2.doStartTag();
/* 2469 */     if (_jspx_th_html_005ftextarea_005f2.doEndTag() == 5) {
/* 2470 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f2);
/* 2471 */       return true;
/*      */     }
/* 2473 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f2);
/* 2474 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2479 */     PageContext pageContext = _jspx_page_context;
/* 2480 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2482 */     TextTag _jspx_th_html_005ftext_005f11 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.get(TextTag.class);
/* 2483 */     _jspx_th_html_005ftext_005f11.setPageContext(_jspx_page_context);
/* 2484 */     _jspx_th_html_005ftext_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2486 */     _jspx_th_html_005ftext_005f11.setProperty("consecutive_minclearpolls");
/*      */     
/* 2488 */     _jspx_th_html_005ftext_005f11.setOnfocus("changetext(this);");
/*      */     
/* 2490 */     _jspx_th_html_005ftext_005f11.setSize("20");
/*      */     
/* 2492 */     _jspx_th_html_005ftext_005f11.setStyleClass("formtext medium");
/*      */     
/* 2494 */     _jspx_th_html_005ftext_005f11.setMaxlength("60");
/* 2495 */     int _jspx_eval_html_005ftext_005f11 = _jspx_th_html_005ftext_005f11.doStartTag();
/* 2496 */     if (_jspx_th_html_005ftext_005f11.doEndTag() == 5) {
/* 2497 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 2498 */       return true;
/*      */     }
/* 2500 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 2501 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2506 */     PageContext pageContext = _jspx_page_context;
/* 2507 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2509 */     TextTag _jspx_th_html_005ftext_005f12 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.get(TextTag.class);
/* 2510 */     _jspx_th_html_005ftext_005f12.setPageContext(_jspx_page_context);
/* 2511 */     _jspx_th_html_005ftext_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2513 */     _jspx_th_html_005ftext_005f12.setProperty("consecutive_clearpolls");
/*      */     
/* 2515 */     _jspx_th_html_005ftext_005f12.setOnfocus("changetext(this);");
/*      */     
/* 2517 */     _jspx_th_html_005ftext_005f12.setSize("20");
/*      */     
/* 2519 */     _jspx_th_html_005ftext_005f12.setStyleClass("formtext medium");
/*      */     
/* 2521 */     _jspx_th_html_005ftext_005f12.setMaxlength("60");
/* 2522 */     int _jspx_eval_html_005ftext_005f12 = _jspx_th_html_005ftext_005f12.doStartTag();
/* 2523 */     if (_jspx_th_html_005ftext_005f12.doEndTag() == 5) {
/* 2524 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 2525 */       return true;
/*      */     }
/* 2527 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 2528 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2533 */     PageContext pageContext = _jspx_page_context;
/* 2534 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2536 */     TextareaTag _jspx_th_html_005ftextarea_005f3 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.get(TextareaTag.class);
/* 2537 */     _jspx_th_html_005ftextarea_005f3.setPageContext(_jspx_page_context);
/* 2538 */     _jspx_th_html_005ftextarea_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2540 */     _jspx_th_html_005ftextarea_005f3.setProperty("description");
/*      */     
/* 2542 */     _jspx_th_html_005ftextarea_005f3.setCols("50");
/*      */     
/* 2544 */     _jspx_th_html_005ftextarea_005f3.setRows("3");
/*      */     
/* 2546 */     _jspx_th_html_005ftextarea_005f3.setStyleClass("formtextarea xlarge");
/*      */     
/* 2548 */     _jspx_th_html_005ftextarea_005f3.setOnfocus("document.AMActionForm.description.select()");
/* 2549 */     int _jspx_eval_html_005ftextarea_005f3 = _jspx_th_html_005ftextarea_005f3.doStartTag();
/* 2550 */     if (_jspx_th_html_005ftextarea_005f3.doEndTag() == 5) {
/* 2551 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f3);
/* 2552 */       return true;
/*      */     }
/* 2554 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fonfocus_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f3);
/* 2555 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2560 */     PageContext pageContext = _jspx_page_context;
/* 2561 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2563 */     org.apache.taglibs.standard.tag.el.core.OutTag _jspx_th_c_005fout_005f0 = (org.apache.taglibs.standard.tag.el.core.OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.el.core.OutTag.class);
/* 2564 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2565 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2567 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 2568 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2569 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2570 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2571 */       return true;
/*      */     }
/* 2573 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2574 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2579 */     PageContext pageContext = _jspx_page_context;
/* 2580 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2582 */     org.apache.taglibs.standard.tag.el.core.OutTag _jspx_th_c_005fout_005f1 = (org.apache.taglibs.standard.tag.el.core.OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.el.core.OutTag.class);
/* 2583 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2584 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2586 */     _jspx_th_c_005fout_005f1.setValue("${param.haid}");
/* 2587 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2588 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2589 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2590 */       return true;
/*      */     }
/* 2592 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2593 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ThresholdForm_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */