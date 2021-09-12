/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.EnterpriseAdminLink;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.util.Properties;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class EnableCustomReports_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   33 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   39 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   40 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*   41 */     _jspx_dependants.put("/jsp/includes/EnterpriseAdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   69 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   73 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   77 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   78 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   79 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   80 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   81 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   82 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   83 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   84 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   85 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   86 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   94 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   98 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   99 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  100 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  101 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*  102 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*  103 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*  104 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  105 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  106 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  107 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.release();
/*  108 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*  109 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*  110 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/*  111 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  112 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange.release();
/*  113 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  114 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  115 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/*  116 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/*  117 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  124 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  127 */     JspWriter out = null;
/*  128 */     Object page = this;
/*  129 */     JspWriter _jspx_out = null;
/*  130 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  134 */       response.setContentType("text/html;charset=UTF-8");
/*  135 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  137 */       _jspx_page_context = pageContext;
/*  138 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  139 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  140 */       session = pageContext.getSession();
/*  141 */       out = pageContext.getOut();
/*  142 */       _jspx_out = out;
/*      */       
/*  144 */       out.write("<!DOCTYPE html>\n");
/*  145 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  146 */       out.write(10);
/*      */       
/*  148 */       request.setAttribute("HelpKey", "Enable Reports");
/*      */       
/*  150 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n<link href=\"/images/");
/*  151 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  153 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*      */       try {
/*  155 */         String resourceId = "";
/*  156 */         if (request.getParameter("resourceid") != null) {
/*  157 */           resourceId = request.getParameter("resourceid");
/*      */         }
/*      */         
/*  160 */         String isPrint = request.getParameter("PRINTER_FRIENDLY");
/*  161 */         isPrint = request.getSession().getAttribute("PRINTER_FRIENDLY") != null ? (String)request.getSession().getAttribute("PRINTER_FRIENDLY") : isPrint != null ? isPrint : "false";
/*      */         
/*      */ 
/*  164 */         out.write("\n<script>\nvar unload = true;\n function fnFormSubmit(){\n\t ");
/*  165 */         if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */           return;
/*  167 */         out.write("\n\t unload = false;\n");
/*  168 */         if (!Constants.sqlManager) {
/*  169 */           out.write("\n document.AMActionForm[0].selectedType.value=document.AMActionForm[0].resTypeValue.options[document.AMActionForm[0].resTypeValue.selectedIndex].value;\n");
/*      */         } else {
/*  171 */           out.write("\n\tdocument.AMActionForm[0].selectedType.value=document.AMActionForm[0].resTypeValue.value;\n");
/*      */         }
/*  173 */         out.write("\n document.AMActionForm[0].submit();\n\n }\n function fnForm2Submit(){\n\t ");
/*  174 */         if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */           return;
/*  176 */         out.write("\n\t unload = false;\n  document.AMActionForm[1].submit();\n\n }\n function getAttributes()\n {\n\n");
/*  177 */         if (!Constants.sqlManager) {
/*  178 */           out.write("\n var a=document.AMActionForm[0].resTypeValue.options[document.AMActionForm[0].resTypeValue.selectedIndex].value;\n");
/*      */         } else {
/*  180 */           out.write("\n\tvar a=document.AMActionForm[0].resTypeValue.value;\n");
/*      */         }
/*  182 */         out.write("\n\n  var url=\"/customReports.do?method=sendAttributeDetails&resourcetype=\"+a+\"&todaytime=\"+");
/*  183 */         out.print(System.currentTimeMillis());
/*  184 */         out.write(";\n\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getAttributeTypes;\n    http.send(null);\n\n  }\n\n\n   function getAttributeTypes()\n{\n\n\n if(http.readyState == 4)\n {\n\n   var result = http.responseText;\n\n    document.getElementById(\"attributedetail\").innerHTML=result;\n    document.getElementById(\"attributedetail\").style.display='block';\n    }\n\n }\n function myOnLoad()\n  {\n\t ");
/*  185 */         if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) {
/*  186 */           out.write("\n\t\t document.getElementById(\"cusrep\").style.display='none';\n\t\t document.getElementById(\"downrep\").style.display='block';\n\t\t document.getElementById(\"downsumreplink-left\").className = \"tbSelected_Left\";\n\t\t document.getElementById(\"downsumreplink\").className = \"tbSelected_Middle\";\n\t\t document.getElementById(\"downsumreplink-right\").className = \"tbSelected_Right\";\n\t\t showDiv('sumrepdiv');\n\t\t document.getElementById(\"summaryHelpcard\").style.display='block';\n  ");
/*  187 */         } else if (com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) {
/*  188 */           out.write("\n\t \tdocument.getElementById(\"cusrep\").style.display='block';\n\t \tdocument.getElementById(\"downrep\").style.display='none';\n\t \tdocument.getElementById(\"customreplink-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"customreplink\").className = \"tbSelected_Middle\";\n\t\tdocument.getElementById(\"customreplink-right\").className = \"tbSelected_Right\";\n\t\tshowDiv('customrepdiv');\n\t\tdocument.getElementById(\"reportHelpcard\").style.display='block';\n\t\tgetAttributes();\n ");
/*      */         }
/*      */         else {
/*  191 */           if (request.getParameter("summarymailer") != null) {
/*  192 */             out.write("\n  \t\t\tshowHide('downtimesummary');\n  \t  ");
/*      */           }
/*      */           else {
/*  195 */             out.write("\n\t\t\tshowHide('customreport');\n\t  ");
/*      */           }
/*  197 */           out.write("\n\t\tgetAttributes();\n  ");
/*      */         }
/*  199 */         out.write("\n }\n\nfunction showHide(tab)\n{\n\tif(tab==\"customreport\")\n\t{\n\n\tdocument.getElementById(\"customreplink-left\").className = \"tbSelected_Left\";\n\tdocument.getElementById(\"customreplink\").className = \"tbSelected_Middle\";\n\tdocument.getElementById(\"customreplink-right\").className = \"tbSelected_Right\";\n\n\tdocument.getElementById(\"downsumreplink-left\").className = \"tbUnSelected_Left\";\n\tdocument.getElementById(\"downsumreplink\").className = \"tbUnSelected_Middle\";\n\tdocument.getElementById(\"downsumreplink-right\").className = \"tbUnSelected_Right\";\n\n\tjavascript:showDiv('customrepdiv');\n\tjavascript:hideDiv('sumrepdiv');\n\tdocument.getElementById(\"reportHelpcard\").style.display='block';\n\tdocument.getElementById(\"summaryHelpcard\").style.display='none';\n\t}\n\n\telse if(tab==\"downtimesummary\")\n\t{\n\n\n\tdocument.getElementById(\"downsumreplink-left\").className = \"tbSelected_Left\";\n\tdocument.getElementById(\"downsumreplink\").className = \"tbSelected_Middle\";\n\tdocument.getElementById(\"downsumreplink-right\").className = \"tbSelected_Right\";\n\n\tdocument.getElementById(\"customreplink-left\").className = \"tbUnSelected_Left\";\n");
/*  200 */         out.write("\tdocument.getElementById(\"customreplink\").className = \"tbUnSelected_Middle\";\n\tdocument.getElementById(\"customreplink-right\").className = \"tbUnSelected_Right\";\n\n\tjavascript:showDiv('sumrepdiv');\n\tjavascript:hideDiv('customrepdiv');\n\tdocument.getElementById(\"reportHelpcard\").style.display='none';\n\tdocument.getElementById(\"summaryHelpcard\").style.display='block';\n\n\t}\n\n}\n\n\n\n\n\n\n\n\n\n\n function getAction()\n{\n\n   if(document.AMActionForm[1].priority.value=='')\n  {\n    alert(\"");
/*  201 */         out.print(FormatUtil.getString("am.webclient.alertescalation.jsalertforsendmail1.text"));
/*  202 */         out.write("\");\n     document.AMActionForm[1].priority.focus();\n     return false;\n   }\n   else\n   {\n    var a=document.AMActionForm[1].priority.value;\n    var b=encodeURIComponent(document.AMActionForm[1].rulename.value);\n    url=\"/alertEscalation.do?method=sendActionDetails&createActionFrom=DowntimeSummary&emailid=\"+a+\"&emailname=\"+encodeURIComponent(b);\n\n    http.open(\"GET\",url,true);\n    http.onreadystatechange = getActionTypes;\n    http.send(null);\n   }\n\n\n }\n\n function getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n      var result = http.responseText;\n\t//alert(result);\n      hideDiv(\"takeaction\");\n      var id=result;\n      var stringtokens=id.split(\",\");\n      smessage=stringtokens[0];\n      rid=stringtokens[1];\n      hideDiv(\"actionmessage\");\n     if(smessage=='null')\n     {\n             hideDiv(\"actionmessage\");\n            var name=document.AMActionForm[1].rulename.value+\"_Action\";\n            document.AMActionForm[1].sendmail.options[document.AMActionForm[1].sendmail.length] =new Option(name,rid,false,true);\n");
/*  203 */         out.write("     }\n     else\n     {\n            showDiv(\"actionmessage\");\n            document.getElementById(\"actionmessage\").innerHTML=smessage;\n     }\n\n\n    }\n }\n\n\n\nfunction callAction()\n {\n\t");
/*  204 */         if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
/*      */           return;
/*  206 */         out.write("\n   showDiv(\"takeaction\");\n  }\n  function removeAction()\n  {\n    hideDiv(\"takeaction\");\n  }\n\n  function closeWindow() {\n\t  ");
/*      */         
/*  208 */         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  209 */         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  210 */         _jspx_th_c_005fif_005f0.setParent(null);
/*      */         
/*  212 */         _jspx_th_c_005fif_005f0.setTest("${not empty param.popup && param.popup == true}");
/*  213 */         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  214 */         if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */           for (;;) {
/*  216 */             out.write("\t  \n\t  if (unload) {\n\t  \twindow.close();\n\t  \twindow.opener.location.href='../showHistoryData.do?method=getData&resourceid=");
/*  217 */             out.print(request.getParameter("resourceid"));
/*  218 */             out.write("&attributeid=");
/*  219 */             out.print(request.getParameter("attributeid"));
/*  220 */             out.write("&period=20&businessPeriod=oni';\n\t  }\n\t  ");
/*  221 */             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  222 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  226 */         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  227 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */         }
/*      */         
/*  230 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  231 */         out.write("\n  }\n</script>\n<body onbeforeUnload=\"closeWindow()\">\n");
/*      */         
/*  233 */         String toappend = "";
/*  234 */         if ((request.getParameter("selectedType") != null) && (!request.getParameter("selectedType").equals("")))
/*      */         {
/*  236 */           String type = request.getParameter("selectedType");
/*  237 */           toappend = type;
/*      */         }
/*      */         
/*  240 */         out.write(10);
/*      */         
/*  242 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  243 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  244 */         _jspx_th_c_005fif_005f1.setParent(null);
/*      */         
/*  246 */         _jspx_th_c_005fif_005f1.setTest("${not empty param.popup && param.popup == true}");
/*  247 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  248 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */           for (;;) {
/*  250 */             out.write("\n<title>");
/*  251 */             out.print(FormatUtil.getString("am.webclient.custom.reports.page.title"));
/*  252 */             out.write("</title>\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" height=\"55\" class=\"darkheaderbg\">\n\t<tr>\n\t\t<td>&nbsp;<span class=\"headingboldwhite\">");
/*  253 */             out.print(FormatUtil.getString("am.webclient.custom.reports.page.title"));
/*  254 */             out.write("</span></td>\n\t</tr>\n</table>\n");
/*  255 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  256 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  260 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  261 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */         }
/*      */         
/*  264 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  265 */         out.write(10);
/*  266 */         out.write(10);
/*      */         
/*  268 */         InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/*  269 */         _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/*  270 */         _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */         
/*  272 */         _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/*  273 */         int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/*  274 */         if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */           for (;;) {
/*  276 */             out.write(10);
/*  277 */             out.write(9);
/*      */             
/*  279 */             PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/*  280 */             _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/*  281 */             _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/*  283 */             _jspx_th_tiles_005fput_005f0.setName("title");
/*      */             
/*  285 */             _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.custom.reports.page.title"));
/*  286 */             int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/*  287 */             if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/*  288 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */             }
/*      */             
/*  291 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/*  292 */             out.write("\t\t\n\t");
/*  293 */             if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/*  295 */             out.write("\t\t\n\t");
/*      */             
/*  297 */             PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  298 */             _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/*  299 */             _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/*  301 */             _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */             
/*  303 */             _jspx_th_tiles_005fput_005f2.setType("string");
/*  304 */             int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/*  305 */             if (_jspx_eval_tiles_005fput_005f2 != 0) {
/*  306 */               if (_jspx_eval_tiles_005fput_005f2 != 1) {
/*  307 */                 out = _jspx_page_context.pushBody();
/*  308 */                 _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/*  309 */                 _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  312 */                 out.write("\t\t\n\t        ");
/*      */                 
/*  314 */                 if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */                 {
/*  316 */                   out.write("\t\t\n\t                ");
/*  317 */                   out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */                   
/*      */ 
/*  320 */                   String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                   
/*  322 */                   out.write("\n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n <tr>\n\t     <td height=\"21\"  class=\"leftlinksheading\">");
/*  323 */                   out.print(FormatUtil.getString("am.webclient.admin.heading"));
/*  324 */                   out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/*  326 */                   ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  327 */                   _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  328 */                   _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*  329 */                   int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  330 */                   if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                     for (;;) {
/*  332 */                       out.write(10);
/*      */                       
/*  334 */                       WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  335 */                       _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  336 */                       _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/*  338 */                       _jspx_th_c_005fwhen_005f0.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/*  339 */                       int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  340 */                       if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                         for (;;) {
/*  342 */                           out.write("\n        ");
/*  343 */                           out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  344 */                           out.write(10);
/*  345 */                           out.write(32);
/*  346 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  347 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  351 */                       if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  352 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                       }
/*      */                       
/*  355 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  356 */                       out.write(10);
/*  357 */                       out.write(32);
/*      */                       
/*  359 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  360 */                       _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  361 */                       _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  362 */                       int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  363 */                       if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                         for (;;) {
/*  365 */                           out.write("\n     ");
/*      */                           
/*  367 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f0 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  368 */                           _jspx_th_ea_005feeadminlink_005f0.setPageContext(_jspx_page_context);
/*  369 */                           _jspx_th_ea_005feeadminlink_005f0.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                           
/*  371 */                           _jspx_th_ea_005feeadminlink_005f0.setHref("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general");
/*      */                           
/*  373 */                           _jspx_th_ea_005feeadminlink_005f0.setEnableClass("new-left-links");
/*  374 */                           int _jspx_eval_ea_005feeadminlink_005f0 = _jspx_th_ea_005feeadminlink_005f0.doStartTag();
/*  375 */                           if (_jspx_eval_ea_005feeadminlink_005f0 != 0) {
/*  376 */                             if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/*  377 */                               out = _jspx_page_context.pushBody();
/*  378 */                               _jspx_th_ea_005feeadminlink_005f0.setBodyContent((BodyContent)out);
/*  379 */                               _jspx_th_ea_005feeadminlink_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  382 */                               out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  383 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f0.doAfterBody();
/*  384 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  387 */                             if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/*  388 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  391 */                           if (_jspx_th_ea_005feeadminlink_005f0.doEndTag() == 5) {
/*  392 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0); return;
/*      */                           }
/*      */                           
/*  395 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0);
/*  396 */                           out.write(10);
/*  397 */                           out.write(32);
/*  398 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  399 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  403 */                       if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  404 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                       }
/*      */                       
/*  407 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  408 */                       out.write(10);
/*  409 */                       out.write(32);
/*  410 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  411 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  415 */                   if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  416 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                   }
/*      */                   
/*  419 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  420 */                   out.write("\n    </td>\n</tr>\n\n<tr>\n\n<tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/*  422 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  423 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  424 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*  425 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  426 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/*  428 */                       out.write(10);
/*      */                       
/*  430 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  431 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  432 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/*  434 */                       _jspx_th_c_005fwhen_005f1.setTest("${param.method!='showMailServerConfiguration'}");
/*  435 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  436 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/*  438 */                           out.write("\n    ");
/*      */                           
/*  440 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f1 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  441 */                           _jspx_th_ea_005feeadminlink_005f1.setPageContext(_jspx_page_context);
/*  442 */                           _jspx_th_ea_005feeadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                           
/*  444 */                           _jspx_th_ea_005feeadminlink_005f1.setHref("/adminAction.do?method=showMailServerConfiguration");
/*      */                           
/*  446 */                           _jspx_th_ea_005feeadminlink_005f1.setEnableClass("new-left-links");
/*  447 */                           int _jspx_eval_ea_005feeadminlink_005f1 = _jspx_th_ea_005feeadminlink_005f1.doStartTag();
/*  448 */                           if (_jspx_eval_ea_005feeadminlink_005f1 != 0) {
/*  449 */                             if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/*  450 */                               out = _jspx_page_context.pushBody();
/*  451 */                               _jspx_th_ea_005feeadminlink_005f1.setBodyContent((BodyContent)out);
/*  452 */                               _jspx_th_ea_005feeadminlink_005f1.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  455 */                               out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/*  456 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f1.doAfterBody();
/*  457 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  460 */                             if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/*  461 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  464 */                           if (_jspx_th_ea_005feeadminlink_005f1.doEndTag() == 5) {
/*  465 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1); return;
/*      */                           }
/*      */                           
/*  468 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1);
/*  469 */                           out.write(10);
/*  470 */                           out.write(32);
/*  471 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  472 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  476 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  477 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/*  480 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  481 */                       out.write(10);
/*  482 */                       out.write(32);
/*      */                       
/*  484 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  485 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  486 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  487 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  488 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/*  490 */                           out.write(10);
/*  491 */                           out.write(9);
/*  492 */                           out.write(32);
/*  493 */                           out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/*  494 */                           out.write(10);
/*  495 */                           out.write(32);
/*  496 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  497 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  501 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  502 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/*  505 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  506 */                       out.write(10);
/*  507 */                       out.write(32);
/*  508 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  509 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  513 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  514 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/*  517 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  518 */                   out.write("\n    </td>\n</tr>\n\n");
/*  519 */                   if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/*  520 */                     out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/*  522 */                     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  523 */                     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  524 */                     _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*  525 */                     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  526 */                     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                       for (;;) {
/*  528 */                         out.write(10);
/*      */                         
/*  530 */                         WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  531 */                         _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  532 */                         _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                         
/*  534 */                         _jspx_th_c_005fwhen_005f2.setTest("${param.method!='SMSServerConfiguration'}");
/*  535 */                         int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  536 */                         if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                           for (;;) {
/*  538 */                             out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/*  539 */                             out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/*  540 */                             out.write("\n    </a>\n ");
/*  541 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  542 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  546 */                         if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  547 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                         }
/*      */                         
/*  550 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  551 */                         out.write(10);
/*  552 */                         out.write(32);
/*      */                         
/*  554 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  555 */                         _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  556 */                         _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  557 */                         int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  558 */                         if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                           for (;;) {
/*  560 */                             out.write("\n         ");
/*  561 */                             out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/*  562 */                             out.write(10);
/*  563 */                             out.write(32);
/*  564 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  565 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  569 */                         if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  570 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                         }
/*      */                         
/*  573 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  574 */                         out.write(10);
/*  575 */                         out.write(32);
/*  576 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  577 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  581 */                     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  582 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                     }
/*      */                     
/*  585 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  586 */                     out.write("\n    </td>\n</tr>\n");
/*      */                   }
/*  588 */                   out.write("\n\n\n<tr>\n\n    <td class=\"leftlinkstd\">\n");
/*      */                   
/*  590 */                   ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  591 */                   _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/*  592 */                   _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*  593 */                   int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/*  594 */                   if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                     for (;;) {
/*  596 */                       out.write(10);
/*      */                       
/*  598 */                       WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  599 */                       _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  600 */                       _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                       
/*  602 */                       _jspx_th_c_005fwhen_005f3.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/*  603 */                       int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  604 */                       if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                         for (;;) {
/*  606 */                           out.write("\n    ");
/*      */                           
/*  608 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f2 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  609 */                           _jspx_th_ea_005feeadminlink_005f2.setPageContext(_jspx_page_context);
/*  610 */                           _jspx_th_ea_005feeadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                           
/*  612 */                           _jspx_th_ea_005feeadminlink_005f2.setHref("/jsp/ProxyConfiguration.jsp");
/*      */                           
/*  614 */                           _jspx_th_ea_005feeadminlink_005f2.setEnableClass("new-left-links");
/*  615 */                           int _jspx_eval_ea_005feeadminlink_005f2 = _jspx_th_ea_005feeadminlink_005f2.doStartTag();
/*  616 */                           if (_jspx_eval_ea_005feeadminlink_005f2 != 0) {
/*  617 */                             if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/*  618 */                               out = _jspx_page_context.pushBody();
/*  619 */                               _jspx_th_ea_005feeadminlink_005f2.setBodyContent((BodyContent)out);
/*  620 */                               _jspx_th_ea_005feeadminlink_005f2.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  623 */                               out.write("\n    ");
/*  624 */                               out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/*  625 */                               out.write("\n    ");
/*  626 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f2.doAfterBody();
/*  627 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  630 */                             if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/*  631 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  634 */                           if (_jspx_th_ea_005feeadminlink_005f2.doEndTag() == 5) {
/*  635 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2); return;
/*      */                           }
/*      */                           
/*  638 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2);
/*  639 */                           out.write(10);
/*  640 */                           out.write(32);
/*  641 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  642 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  646 */                       if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  647 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                       }
/*      */                       
/*  650 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  651 */                       out.write(10);
/*  652 */                       out.write(32);
/*      */                       
/*  654 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  655 */                       _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  656 */                       _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*  657 */                       int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  658 */                       if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                         for (;;) {
/*  660 */                           out.write(10);
/*  661 */                           out.write(9);
/*  662 */                           out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/*  663 */                           out.write(10);
/*  664 */                           out.write(32);
/*  665 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/*  666 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  670 */                       if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/*  671 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                       }
/*      */                       
/*  674 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/*  675 */                       out.write(10);
/*  676 */                       out.write(32);
/*  677 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/*  678 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  682 */                   if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/*  683 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                   }
/*      */                   
/*  686 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*  687 */                   out.write("\n    </td>\n</tr>\n<tr>\n\n<td class=\"leftlinkstd\">\n");
/*      */                   
/*  689 */                   ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  690 */                   _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  691 */                   _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/*  692 */                   int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  693 */                   if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                     for (;;) {
/*  695 */                       out.write(10);
/*      */                       
/*  697 */                       WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  698 */                       _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  699 */                       _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                       
/*  701 */                       _jspx_th_c_005fwhen_005f4.setTest("${uri !='/admin/userconfiguration.do'}");
/*  702 */                       int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  703 */                       if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                         for (;;) {
/*  705 */                           out.write("\n\n        ");
/*      */                           
/*  707 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f3 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  708 */                           _jspx_th_ea_005feeadminlink_005f3.setPageContext(_jspx_page_context);
/*  709 */                           _jspx_th_ea_005feeadminlink_005f3.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                           
/*  711 */                           _jspx_th_ea_005feeadminlink_005f3.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                           
/*  713 */                           _jspx_th_ea_005feeadminlink_005f3.setEnableClass("new-left-links");
/*  714 */                           int _jspx_eval_ea_005feeadminlink_005f3 = _jspx_th_ea_005feeadminlink_005f3.doStartTag();
/*  715 */                           if (_jspx_eval_ea_005feeadminlink_005f3 != 0) {
/*  716 */                             if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/*  717 */                               out = _jspx_page_context.pushBody();
/*  718 */                               _jspx_th_ea_005feeadminlink_005f3.setBodyContent((BodyContent)out);
/*  719 */                               _jspx_th_ea_005feeadminlink_005f3.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  722 */                               out.write("\n       ");
/*  723 */                               out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/*  724 */                               out.write("\n        ");
/*  725 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f3.doAfterBody();
/*  726 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  729 */                             if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/*  730 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  733 */                           if (_jspx_th_ea_005feeadminlink_005f3.doEndTag() == 5) {
/*  734 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3); return;
/*      */                           }
/*      */                           
/*  737 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3);
/*  738 */                           out.write(10);
/*  739 */                           out.write(10);
/*  740 */                           out.write(32);
/*  741 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  742 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  746 */                       if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  747 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                       }
/*      */                       
/*  750 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  751 */                       out.write(10);
/*  752 */                       out.write(32);
/*      */                       
/*  754 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  755 */                       _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  756 */                       _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*  757 */                       int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  758 */                       if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                         for (;;) {
/*  760 */                           out.write(10);
/*  761 */                           out.write(9);
/*  762 */                           out.write(32);
/*  763 */                           out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/*  764 */                           out.write(10);
/*  765 */                           out.write(32);
/*  766 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  767 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  771 */                       if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  772 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                       }
/*      */                       
/*  775 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  776 */                       out.write(10);
/*  777 */                       out.write(32);
/*  778 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/*  779 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  783 */                   if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/*  784 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                   }
/*      */                   
/*  787 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*  788 */                   out.write("\n</td>\n</tr>\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                   
/*  790 */                   ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  791 */                   _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  792 */                   _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/*  793 */                   int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  794 */                   if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                     for (;;) {
/*  796 */                       out.write("\n   ");
/*      */                       
/*  798 */                       WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  799 */                       _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/*  800 */                       _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                       
/*  802 */                       _jspx_th_c_005fwhen_005f5.setTest("${param.method!='showManagedServers'}");
/*  803 */                       int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/*  804 */                       if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                         for (;;) {
/*  806 */                           out.write("\n    ");
/*      */                           
/*  808 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f4 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  809 */                           _jspx_th_ea_005feeadminlink_005f4.setPageContext(_jspx_page_context);
/*  810 */                           _jspx_th_ea_005feeadminlink_005f4.setParent(_jspx_th_c_005fwhen_005f5);
/*      */                           
/*  812 */                           _jspx_th_ea_005feeadminlink_005f4.setHref("/adminAction.do?method=showManagedServers");
/*      */                           
/*  814 */                           _jspx_th_ea_005feeadminlink_005f4.setEnableClass("new-left-links");
/*  815 */                           int _jspx_eval_ea_005feeadminlink_005f4 = _jspx_th_ea_005feeadminlink_005f4.doStartTag();
/*  816 */                           if (_jspx_eval_ea_005feeadminlink_005f4 != 0) {
/*  817 */                             if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/*  818 */                               out = _jspx_page_context.pushBody();
/*  819 */                               _jspx_th_ea_005feeadminlink_005f4.setBodyContent((BodyContent)out);
/*  820 */                               _jspx_th_ea_005feeadminlink_005f4.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  823 */                               out.write("\n     ");
/*  824 */                               out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/*  825 */                               out.write(10);
/*  826 */                               out.write(9);
/*  827 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f4.doAfterBody();
/*  828 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  831 */                             if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/*  832 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  835 */                           if (_jspx_th_ea_005feeadminlink_005f4.doEndTag() == 5) {
/*  836 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4); return;
/*      */                           }
/*      */                           
/*  839 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4);
/*  840 */                           out.write("\n   ");
/*  841 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/*  842 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  846 */                       if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/*  847 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                       }
/*      */                       
/*  850 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*  851 */                       out.write("\n   ");
/*      */                       
/*  853 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  854 */                       _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  855 */                       _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*  856 */                       int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  857 */                       if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                         for (;;) {
/*  859 */                           out.write("\n     ");
/*  860 */                           out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/*  861 */                           out.write("\n   ");
/*  862 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  863 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  867 */                       if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  868 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                       }
/*      */                       
/*  871 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  872 */                       out.write("\n   ");
/*  873 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  874 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  878 */                   if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  879 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                   }
/*      */                   
/*  882 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  883 */                   out.write("\n  </td>\n</tr>\n\n\n<td class=\"leftlinkstd\" >\n");
/*      */                   
/*  885 */                   ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  886 */                   _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/*  887 */                   _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_tiles_005fput_005f2);
/*  888 */                   int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/*  889 */                   if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                     for (;;) {
/*  891 */                       out.write(10);
/*      */                       
/*  893 */                       WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  894 */                       _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/*  895 */                       _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                       
/*  897 */                       _jspx_th_c_005fwhen_005f6.setTest("${param.server!='admin'}");
/*  898 */                       int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/*  899 */                       if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                         for (;;) {
/*  901 */                           out.write(10);
/*      */                           
/*  903 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f5 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  904 */                           _jspx_th_ea_005feeadminlink_005f5.setPageContext(_jspx_page_context);
/*  905 */                           _jspx_th_ea_005feeadminlink_005f5.setParent(_jspx_th_c_005fwhen_005f6);
/*      */                           
/*  907 */                           _jspx_th_ea_005feeadminlink_005f5.setHref("/adminAction.do?method=showActionProfiles");
/*      */                           
/*  909 */                           _jspx_th_ea_005feeadminlink_005f5.setEnableClass("new-left-links");
/*  910 */                           int _jspx_eval_ea_005feeadminlink_005f5 = _jspx_th_ea_005feeadminlink_005f5.doStartTag();
/*  911 */                           if (_jspx_eval_ea_005feeadminlink_005f5 != 0) {
/*  912 */                             if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/*  913 */                               out = _jspx_page_context.pushBody();
/*  914 */                               _jspx_th_ea_005feeadminlink_005f5.setBodyContent((BodyContent)out);
/*  915 */                               _jspx_th_ea_005feeadminlink_005f5.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  918 */                               out.write(10);
/*  919 */                               out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/*  920 */                               out.write(10);
/*  921 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f5.doAfterBody();
/*  922 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  925 */                             if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/*  926 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  929 */                           if (_jspx_th_ea_005feeadminlink_005f5.doEndTag() == 5) {
/*  930 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5); return;
/*      */                           }
/*      */                           
/*  933 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5);
/*  934 */                           out.write(10);
/*  935 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/*  936 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  940 */                       if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/*  941 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                       }
/*      */                       
/*  944 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/*  945 */                       out.write(10);
/*      */                       
/*  947 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  948 */                       _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/*  949 */                       _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*  950 */                       int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/*  951 */                       if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                         for (;;) {
/*  953 */                           out.write(10);
/*  954 */                           out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/*  955 */                           out.write(10);
/*  956 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/*  957 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  961 */                       if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/*  962 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                       }
/*      */                       
/*  965 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/*  966 */                       out.write(10);
/*  967 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/*  968 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  972 */                   if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/*  973 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                   }
/*      */                   
/*  976 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/*  977 */                   out.write("\n</td>\n</tr>\n");
/*      */                   
/*  979 */                   if ((!usertype.equals("F")) || (Constants.isIt360))
/*      */                   {
/*  981 */                     out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */                     
/*  983 */                     ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  984 */                     _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/*  985 */                     _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/*  986 */                     int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/*  987 */                     if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                       for (;;) {
/*  989 */                         out.write(10);
/*  990 */                         out.write(32);
/*  991 */                         out.write(32);
/*      */                         
/*  993 */                         WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  994 */                         _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/*  995 */                         _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                         
/*  997 */                         _jspx_th_c_005fwhen_005f7.setTest("${param.method =='showScheduleReports'}");
/*  998 */                         int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/*  999 */                         if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                           for (;;) {
/* 1001 */                             out.write(10);
/* 1002 */                             out.write(32);
/* 1003 */                             out.write(32);
/* 1004 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1005 */                             out.write(10);
/* 1006 */                             out.write(32);
/* 1007 */                             out.write(32);
/* 1008 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 1009 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1013 */                         if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 1014 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                         }
/*      */                         
/* 1017 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1018 */                         out.write(10);
/* 1019 */                         out.write(32);
/*      */                         
/* 1021 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1022 */                         _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 1023 */                         _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 1024 */                         int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 1025 */                         if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                           for (;;) {
/* 1027 */                             out.write("\n   ");
/*      */                             
/* 1029 */                             EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f6 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1030 */                             _jspx_th_ea_005feeadminlink_005f6.setPageContext(_jspx_page_context);
/* 1031 */                             _jspx_th_ea_005feeadminlink_005f6.setParent(_jspx_th_c_005fotherwise_005f7);
/*      */                             
/* 1033 */                             _jspx_th_ea_005feeadminlink_005f6.setHref("/scheduleReports.do?method=showScheduleReports");
/*      */                             
/* 1035 */                             _jspx_th_ea_005feeadminlink_005f6.setEnableClass("new-left-links");
/* 1036 */                             int _jspx_eval_ea_005feeadminlink_005f6 = _jspx_th_ea_005feeadminlink_005f6.doStartTag();
/* 1037 */                             if (_jspx_eval_ea_005feeadminlink_005f6 != 0) {
/* 1038 */                               if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/* 1039 */                                 out = _jspx_page_context.pushBody();
/* 1040 */                                 _jspx_th_ea_005feeadminlink_005f6.setBodyContent((BodyContent)out);
/* 1041 */                                 _jspx_th_ea_005feeadminlink_005f6.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 1044 */                                 out.write("\n   ");
/* 1045 */                                 out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1046 */                                 out.write(10);
/* 1047 */                                 out.write(32);
/* 1048 */                                 out.write(32);
/* 1049 */                                 int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f6.doAfterBody();
/* 1050 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1053 */                               if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/* 1054 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1057 */                             if (_jspx_th_ea_005feeadminlink_005f6.doEndTag() == 5) {
/* 1058 */                               this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6); return;
/*      */                             }
/*      */                             
/* 1061 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6);
/* 1062 */                             out.write(10);
/* 1063 */                             out.write(32);
/* 1064 */                             out.write(32);
/* 1065 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 1066 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1070 */                         if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 1071 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                         }
/*      */                         
/* 1074 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 1075 */                         out.write(10);
/* 1076 */                         out.write(32);
/* 1077 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 1078 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1082 */                     if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 1083 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                     }
/*      */                     
/* 1086 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 1087 */                     out.write("\n</td>\n</tr>\n");
/*      */                   } else {
/* 1089 */                     out.write("\n<tr>\n    <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 1090 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1091 */                     out.write("\n    </td>\n</tr>\n");
/*      */                   }
/* 1093 */                   out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */                   
/* 1095 */                   ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1096 */                   _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 1097 */                   _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f2);
/* 1098 */                   int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 1099 */                   if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                     for (;;) {
/* 1101 */                       out.write(10);
/* 1102 */                       out.write(32);
/* 1103 */                       out.write(32);
/*      */                       
/* 1105 */                       WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1106 */                       _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 1107 */                       _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                       
/* 1109 */                       _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showDataCleanUp'}");
/* 1110 */                       int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 1111 */                       if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                         for (;;) {
/* 1113 */                           out.write(10);
/* 1114 */                           out.write(32);
/* 1115 */                           out.write(32);
/* 1116 */                           out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1117 */                           out.write(10);
/* 1118 */                           out.write(32);
/* 1119 */                           out.write(32);
/* 1120 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 1121 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1125 */                       if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 1126 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                       }
/*      */                       
/* 1129 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 1130 */                       out.write(10);
/* 1131 */                       out.write(32);
/*      */                       
/* 1133 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1134 */                       _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 1135 */                       _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 1136 */                       int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 1137 */                       if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                         for (;;) {
/* 1139 */                           out.write("\n   ");
/*      */                           
/* 1141 */                           EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f7 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1142 */                           _jspx_th_ea_005feeadminlink_005f7.setPageContext(_jspx_page_context);
/* 1143 */                           _jspx_th_ea_005feeadminlink_005f7.setParent(_jspx_th_c_005fotherwise_005f8);
/*      */                           
/* 1145 */                           _jspx_th_ea_005feeadminlink_005f7.setHref("/adminAction.do?method=showDataCleanUp");
/*      */                           
/* 1147 */                           _jspx_th_ea_005feeadminlink_005f7.setEnableClass("new-left-links");
/* 1148 */                           int _jspx_eval_ea_005feeadminlink_005f7 = _jspx_th_ea_005feeadminlink_005f7.doStartTag();
/* 1149 */                           if (_jspx_eval_ea_005feeadminlink_005f7 != 0) {
/* 1150 */                             if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/* 1151 */                               out = _jspx_page_context.pushBody();
/* 1152 */                               _jspx_th_ea_005feeadminlink_005f7.setBodyContent((BodyContent)out);
/* 1153 */                               _jspx_th_ea_005feeadminlink_005f7.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 1156 */                               out.write("\n   ");
/* 1157 */                               out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1158 */                               out.write(10);
/* 1159 */                               out.write(32);
/* 1160 */                               out.write(32);
/* 1161 */                               int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f7.doAfterBody();
/* 1162 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 1165 */                             if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/* 1166 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 1169 */                           if (_jspx_th_ea_005feeadminlink_005f7.doEndTag() == 5) {
/* 1170 */                             this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7); return;
/*      */                           }
/*      */                           
/* 1173 */                           this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7);
/* 1174 */                           out.write(10);
/* 1175 */                           out.write(32);
/* 1176 */                           out.write(32);
/* 1177 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 1178 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1182 */                       if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 1183 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                       }
/*      */                       
/* 1186 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 1187 */                       out.write(10);
/* 1188 */                       out.write(32);
/* 1189 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 1190 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1194 */                   if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 1195 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                   }
/*      */                   
/* 1198 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 1199 */                   out.write("\n</td>\n</tr>\n</table>\n\n");
/* 1200 */                   out.write("\t\t\n\t        ");
/*      */                 }
/*      */                 else
/*      */                 {
/* 1204 */                   out.write("\t\t\n\t                ");
/* 1205 */                   out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */                   
/*      */ 
/* 1208 */                   String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                   
/* 1210 */                   out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/* 1211 */                   out.print(FormatUtil.getString("wizard.disabled"));
/* 1212 */                   out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/* 1213 */                   out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 1214 */                   out.write("</td>\n  </tr>\n  \n ");
/*      */                   
/* 1216 */                   if (!Constants.sqlManager)
/*      */                   {
/*      */ 
/* 1219 */                     out.write("  \n  <tr>\n\n  ");
/*      */                     
/* 1221 */                     if (request.getParameter("wiz") != null)
/*      */                     {
/* 1223 */                       out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/* 1224 */                       out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 1225 */                       out.write("\" class='disabledlink'>");
/* 1226 */                       out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1227 */                       out.write("</a></td>\n  ");
/*      */                     }
/*      */                     else
/*      */                     {
/* 1231 */                       out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                       
/* 1233 */                       ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1234 */                       _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 1235 */                       _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f2);
/* 1236 */                       int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 1237 */                       if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                         for (;;) {
/* 1239 */                           out.write(10);
/*      */                           
/* 1241 */                           WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1242 */                           _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 1243 */                           _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                           
/* 1245 */                           _jspx_th_c_005fwhen_005f9.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/* 1246 */                           int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 1247 */                           if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                             for (;;) {
/* 1249 */                               out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/* 1250 */                               out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1251 */                               out.write("\n    </a>\n ");
/* 1252 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 1253 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1257 */                           if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 1258 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                           }
/*      */                           
/* 1261 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 1262 */                           out.write(10);
/* 1263 */                           out.write(32);
/*      */                           
/* 1265 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1266 */                           _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 1267 */                           _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 1268 */                           int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 1269 */                           if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                             for (;;) {
/* 1271 */                               out.write(10);
/* 1272 */                               out.write(9);
/* 1273 */                               out.write(32);
/* 1274 */                               out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1275 */                               out.write(10);
/* 1276 */                               out.write(32);
/* 1277 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 1278 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1282 */                           if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 1283 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                           }
/*      */                           
/* 1286 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 1287 */                           out.write(10);
/* 1288 */                           out.write(32);
/* 1289 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 1290 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1294 */                       if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 1295 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                       }
/*      */                       
/* 1298 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 1299 */                       out.write("\n    </td>\n\t");
/*      */                     }
/* 1301 */                     out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                     
/* 1303 */                     if (request.getParameter("wiz") != null)
/*      */                     {
/* 1305 */                       out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/* 1306 */                       out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 1307 */                       out.write("\" class='disabledlink'>");
/* 1308 */                       out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1309 */                       out.write("</a></td>\n   ");
/*      */                     }
/*      */                     else
/*      */                     {
/* 1313 */                       out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                       
/* 1315 */                       ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1316 */                       _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 1317 */                       _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/* 1318 */                       int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 1319 */                       if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                         for (;;) {
/* 1321 */                           out.write(10);
/*      */                           
/* 1323 */                           WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1324 */                           _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 1325 */                           _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                           
/* 1327 */                           _jspx_th_c_005fwhen_005f10.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/* 1328 */                           int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 1329 */                           if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                             for (;;) {
/* 1331 */                               out.write("\n   ");
/* 1332 */                               out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1333 */                               out.write(10);
/* 1334 */                               out.write(32);
/* 1335 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 1336 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1340 */                           if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 1341 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                           }
/*      */                           
/* 1344 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 1345 */                           out.write(10);
/* 1346 */                           out.write(32);
/*      */                           
/* 1348 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1349 */                           _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 1350 */                           _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 1351 */                           int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 1352 */                           if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                             for (;;) {
/* 1354 */                               out.write(10);
/* 1355 */                               String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/* 1356 */                               out.write("\n\t \n <a href=\"");
/* 1357 */                               out.print(link);
/* 1358 */                               out.write("\" class=\"new-left-links\">\n               ");
/* 1359 */                               out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1360 */                               out.write("\n    </a>    \n ");
/* 1361 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 1362 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1366 */                           if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 1367 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                           }
/*      */                           
/* 1370 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 1371 */                           out.write(10);
/* 1372 */                           out.write(32);
/* 1373 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 1374 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1378 */                       if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 1379 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                       }
/*      */                       
/* 1382 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 1383 */                       out.write("\n</td>\n");
/*      */                     }
/* 1385 */                     out.write("\n</tr>\n\n ");
/*      */                   }
/*      */                   
/*      */ 
/* 1389 */                   out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/* 1391 */                   ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1392 */                   _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 1393 */                   _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/* 1394 */                   int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 1395 */                   if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                     for (;;) {
/* 1397 */                       out.write(10);
/*      */                       
/* 1399 */                       WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1400 */                       _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 1401 */                       _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                       
/* 1403 */                       _jspx_th_c_005fwhen_005f11.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/* 1404 */                       int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 1405 */                       if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                         for (;;) {
/* 1407 */                           out.write("\n    \n       ");
/* 1408 */                           out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 1409 */                           out.write(10);
/* 1410 */                           out.write(32);
/* 1411 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 1412 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1416 */                       if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 1417 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                       }
/*      */                       
/* 1420 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 1421 */                       out.write(10);
/* 1422 */                       out.write(32);
/*      */                       
/* 1424 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1425 */                       _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 1426 */                       _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 1427 */                       int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 1428 */                       if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                         for (;;) {
/* 1430 */                           out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/* 1431 */                           out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 1432 */                           out.write("\n    </a>\n ");
/* 1433 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 1434 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1438 */                       if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 1439 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                       }
/*      */                       
/* 1442 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 1443 */                       out.write(10);
/* 1444 */                       out.write(32);
/* 1445 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 1446 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1450 */                   if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 1451 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                   }
/*      */                   
/* 1454 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 1455 */                   out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/* 1457 */                   ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1458 */                   _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 1459 */                   _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/* 1460 */                   int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 1461 */                   if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                     for (;;) {
/* 1463 */                       out.write(10);
/*      */                       
/* 1465 */                       WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1466 */                       _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 1467 */                       _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                       
/* 1469 */                       _jspx_th_c_005fwhen_005f12.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/* 1470 */                       int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 1471 */                       if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                         for (;;) {
/* 1473 */                           out.write("\n    \n       ");
/* 1474 */                           out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 1475 */                           out.write(10);
/* 1476 */                           out.write(32);
/* 1477 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 1478 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1482 */                       if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 1483 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                       }
/*      */                       
/* 1486 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 1487 */                       out.write(10);
/* 1488 */                       out.write(32);
/*      */                       
/* 1490 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1491 */                       _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 1492 */                       _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 1493 */                       int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 1494 */                       if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                         for (;;) {
/* 1496 */                           out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/* 1497 */                           out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 1498 */                           out.write("\n\t </a>\n ");
/* 1499 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 1500 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1504 */                       if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 1505 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                       }
/*      */                       
/* 1508 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 1509 */                       out.write(10);
/* 1510 */                       out.write(32);
/* 1511 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 1512 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1516 */                   if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 1517 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                   }
/*      */                   
/* 1520 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 1521 */                   out.write("\n    </td>\n</tr>  \n\n  ");
/*      */                   
/* 1523 */                   if (!Constants.sqlManager)
/*      */                   {
/*      */ 
/* 1526 */                     out.write(32);
/* 1527 */                     out.write(32);
/* 1528 */                     out.write(10);
/*      */                     
/* 1530 */                     ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1531 */                     _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 1532 */                     _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f2);
/* 1533 */                     int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 1534 */                     if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                       for (;;) {
/* 1536 */                         out.write(10);
/*      */                         
/* 1538 */                         WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1539 */                         _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 1540 */                         _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                         
/* 1542 */                         _jspx_th_c_005fwhen_005f13.setTest("${param.method !='showNetworkDiscoveryForm'}");
/* 1543 */                         int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 1544 */                         if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                           for (;;) {
/* 1546 */                             out.write("\n<tr>\n    ");
/* 1547 */                             if (!request.isUserInRole("OPERATOR")) {
/* 1548 */                               out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/* 1549 */                               out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 1550 */                               out.write("\n    </a>\n        </td>\n     ");
/*      */                             } else {
/* 1552 */                               out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/* 1553 */                               out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 1554 */                               out.write("\n\t</a>\n\t </td>\n\t");
/*      */                             }
/* 1556 */                             out.write("\n    </tr>\n ");
/* 1557 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 1558 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1562 */                         if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 1563 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                         }
/*      */                         
/* 1566 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 1567 */                         out.write(10);
/* 1568 */                         out.write(32);
/*      */                         
/* 1570 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1571 */                         _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 1572 */                         _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 1573 */                         int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 1574 */                         if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                           for (;;) {
/* 1576 */                             out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/* 1577 */                             out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 1578 */                             out.write("\n\t </td>\n ");
/* 1579 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 1580 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1584 */                         if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 1585 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                         }
/*      */                         
/* 1588 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 1589 */                         out.write(10);
/* 1590 */                         out.write(32);
/* 1591 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 1592 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1596 */                     if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 1597 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                     }
/*      */                     
/* 1600 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 1601 */                     out.write("\n \n  ");
/*      */                   }
/*      */                   
/*      */ 
/* 1605 */                   out.write("  \n \n ");
/*      */                   
/* 1607 */                   if (!usertype.equals("F"))
/*      */                   {
/* 1609 */                     out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                     
/* 1611 */                     ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1612 */                     _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 1613 */                     _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f2);
/* 1614 */                     int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 1615 */                     if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                       for (;;) {
/* 1617 */                         out.write(10);
/* 1618 */                         out.write(9);
/*      */                         
/* 1620 */                         WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1621 */                         _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 1622 */                         _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                         
/* 1624 */                         _jspx_th_c_005fwhen_005f14.setTest("${param.method !='maintenanceTaskListView'}");
/* 1625 */                         int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 1626 */                         if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                           for (;;) {
/* 1628 */                             out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 1629 */                             out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1630 */                             out.write("</a>\n  \t");
/* 1631 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 1632 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1636 */                         if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 1637 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                         }
/*      */                         
/* 1640 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 1641 */                         out.write("\n  \t");
/*      */                         
/* 1643 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1644 */                         _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 1645 */                         _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 1646 */                         int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 1647 */                         if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                           for (;;) {
/* 1649 */                             out.write("\n \t\t");
/* 1650 */                             out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1651 */                             out.write("\n  \t");
/* 1652 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 1653 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1657 */                         if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 1658 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                         }
/*      */                         
/* 1661 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 1662 */                         out.write("\n  \t");
/* 1663 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 1664 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1668 */                     if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 1669 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                     }
/*      */                     
/* 1672 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 1673 */                     out.write("\n     </td>\n </tr>   \n \n ");
/*      */                     
/* 1675 */                     if (!Constants.sqlManager)
/*      */                     {
/*      */ 
/* 1678 */                       out.write(32);
/* 1679 */                       out.write(32);
/* 1680 */                       out.write(10);
/*      */                       
/* 1682 */                       IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1683 */                       _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 1684 */                       _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                       
/* 1686 */                       _jspx_th_c_005fif_005f2.setTest("${category!='LAMP'}");
/* 1687 */                       int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 1688 */                       if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                         for (;;) {
/* 1690 */                           out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                           
/* 1692 */                           ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1693 */                           _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 1694 */                           _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_c_005fif_005f2);
/* 1695 */                           int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 1696 */                           if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                             for (;;) {
/* 1698 */                               out.write(10);
/* 1699 */                               out.write(32);
/* 1700 */                               out.write(9);
/*      */                               
/* 1702 */                               WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1703 */                               _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 1704 */                               _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                               
/* 1706 */                               _jspx_th_c_005fwhen_005f15.setTest("${param.method !='listTrapListener'}");
/* 1707 */                               int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 1708 */                               if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                                 for (;;) {
/* 1710 */                                   out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/* 1711 */                                   out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 1712 */                                   out.write("</a>\n   \t");
/* 1713 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 1714 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1718 */                               if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 1719 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                               }
/*      */                               
/* 1722 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 1723 */                               out.write("\n   \t");
/*      */                               
/* 1725 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1726 */                               _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 1727 */                               _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 1728 */                               int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 1729 */                               if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                                 for (;;) {
/* 1731 */                                   out.write("\n  \t\t");
/* 1732 */                                   out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 1733 */                                   out.write(" \n   \t");
/* 1734 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 1735 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1739 */                               if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 1740 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                               }
/*      */                               
/* 1743 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 1744 */                               out.write("\n   \t");
/* 1745 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 1746 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1750 */                           if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 1751 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                           }
/*      */                           
/* 1754 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 1755 */                           out.write("\n      </td>\n  </tr>   \n");
/* 1756 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 1757 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1761 */                       if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 1762 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                       }
/*      */                       
/* 1765 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1766 */                       out.write(10);
/* 1767 */                       out.write(32);
/*      */                     }
/*      */                     
/*      */ 
/* 1771 */                     out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 1773 */                     ChooseTag _jspx_th_c_005fchoose_005f16 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1774 */                     _jspx_th_c_005fchoose_005f16.setPageContext(_jspx_page_context);
/* 1775 */                     _jspx_th_c_005fchoose_005f16.setParent(_jspx_th_tiles_005fput_005f2);
/* 1776 */                     int _jspx_eval_c_005fchoose_005f16 = _jspx_th_c_005fchoose_005f16.doStartTag();
/* 1777 */                     if (_jspx_eval_c_005fchoose_005f16 != 0) {
/*      */                       for (;;) {
/* 1779 */                         out.write(10);
/*      */                         
/* 1781 */                         WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1782 */                         _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 1783 */                         _jspx_th_c_005fwhen_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/*      */                         
/* 1785 */                         _jspx_th_c_005fwhen_005f16.setTest("${param.method =='showScheduleReports'}");
/* 1786 */                         int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 1787 */                         if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */                           for (;;) {
/* 1789 */                             out.write("\n       ");
/* 1790 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1791 */                             out.write(10);
/* 1792 */                             out.write(32);
/* 1793 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 1794 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1798 */                         if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 1799 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16); return;
/*      */                         }
/*      */                         
/* 1802 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 1803 */                         out.write(10);
/* 1804 */                         out.write(32);
/*      */                         
/* 1806 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f16 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1807 */                         _jspx_th_c_005fotherwise_005f16.setPageContext(_jspx_page_context);
/* 1808 */                         _jspx_th_c_005fotherwise_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/* 1809 */                         int _jspx_eval_c_005fotherwise_005f16 = _jspx_th_c_005fotherwise_005f16.doStartTag();
/* 1810 */                         if (_jspx_eval_c_005fotherwise_005f16 != 0) {
/*      */                           for (;;) {
/* 1812 */                             out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/* 1813 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1814 */                             out.write("\n\t </a>\n ");
/* 1815 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f16.doAfterBody();
/* 1816 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1820 */                         if (_jspx_th_c_005fotherwise_005f16.doEndTag() == 5) {
/* 1821 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16); return;
/*      */                         }
/*      */                         
/* 1824 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 1825 */                         out.write(10);
/* 1826 */                         out.write(32);
/* 1827 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f16.doAfterBody();
/* 1828 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1832 */                     if (_jspx_th_c_005fchoose_005f16.doEndTag() == 5) {
/* 1833 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16); return;
/*      */                     }
/*      */                     
/* 1836 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 1837 */                     out.write("\n    </td>\n</tr> \n");
/*      */                   } else {
/* 1839 */                     out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 1840 */                     out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1841 */                     out.write("</a>\n     </td>\n </tr>   \n");
/*      */                     
/* 1843 */                     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1844 */                     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 1845 */                     _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                     
/* 1847 */                     _jspx_th_c_005fif_005f3.setTest("${category!='LAMP'}");
/* 1848 */                     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 1849 */                     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                       for (;;) {
/* 1851 */                         out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 1852 */                         out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 1853 */                         out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 1854 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 1855 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1859 */                     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 1860 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                     }
/*      */                     
/* 1863 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1864 */                     out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 1865 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1866 */                     out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */                   }
/* 1868 */                   out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/* 1870 */                   ChooseTag _jspx_th_c_005fchoose_005f17 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1871 */                   _jspx_th_c_005fchoose_005f17.setPageContext(_jspx_page_context);
/* 1872 */                   _jspx_th_c_005fchoose_005f17.setParent(_jspx_th_tiles_005fput_005f2);
/* 1873 */                   int _jspx_eval_c_005fchoose_005f17 = _jspx_th_c_005fchoose_005f17.doStartTag();
/* 1874 */                   if (_jspx_eval_c_005fchoose_005f17 != 0) {
/*      */                     for (;;) {
/* 1876 */                       out.write(10);
/*      */                       
/* 1878 */                       WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1879 */                       _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 1880 */                       _jspx_th_c_005fwhen_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/*      */                       
/* 1882 */                       _jspx_th_c_005fwhen_005f17.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 1883 */                       int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 1884 */                       if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */                         for (;;) {
/* 1886 */                           out.write("\n        ");
/* 1887 */                           out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 1888 */                           out.write(10);
/* 1889 */                           out.write(32);
/* 1890 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 1891 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1895 */                       if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 1896 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17); return;
/*      */                       }
/*      */                       
/* 1899 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 1900 */                       out.write(10);
/* 1901 */                       out.write(32);
/*      */                       
/* 1903 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f17 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1904 */                       _jspx_th_c_005fotherwise_005f17.setPageContext(_jspx_page_context);
/* 1905 */                       _jspx_th_c_005fotherwise_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/* 1906 */                       int _jspx_eval_c_005fotherwise_005f17 = _jspx_th_c_005fotherwise_005f17.doStartTag();
/* 1907 */                       if (_jspx_eval_c_005fotherwise_005f17 != 0) {
/*      */                         for (;;) {
/* 1909 */                           out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 1910 */                           out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 1911 */                           out.write("\n\t </a>\n ");
/* 1912 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f17.doAfterBody();
/* 1913 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1917 */                       if (_jspx_th_c_005fotherwise_005f17.doEndTag() == 5) {
/* 1918 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17); return;
/*      */                       }
/*      */                       
/* 1921 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 1922 */                       out.write(10);
/* 1923 */                       out.write(32);
/* 1924 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f17.doAfterBody();
/* 1925 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1929 */                   if (_jspx_th_c_005fchoose_005f17.doEndTag() == 5) {
/* 1930 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17); return;
/*      */                   }
/*      */                   
/* 1933 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 1934 */                   out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                   
/* 1936 */                   ChooseTag _jspx_th_c_005fchoose_005f18 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1937 */                   _jspx_th_c_005fchoose_005f18.setPageContext(_jspx_page_context);
/* 1938 */                   _jspx_th_c_005fchoose_005f18.setParent(_jspx_th_tiles_005fput_005f2);
/* 1939 */                   int _jspx_eval_c_005fchoose_005f18 = _jspx_th_c_005fchoose_005f18.doStartTag();
/* 1940 */                   if (_jspx_eval_c_005fchoose_005f18 != 0) {
/*      */                     for (;;) {
/* 1942 */                       out.write(10);
/*      */                       
/* 1944 */                       WhenTag _jspx_th_c_005fwhen_005f18 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1945 */                       _jspx_th_c_005fwhen_005f18.setPageContext(_jspx_page_context);
/* 1946 */                       _jspx_th_c_005fwhen_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/*      */                       
/* 1948 */                       _jspx_th_c_005fwhen_005f18.setTest("${param.method!='showMailServerConfiguration'}");
/* 1949 */                       int _jspx_eval_c_005fwhen_005f18 = _jspx_th_c_005fwhen_005f18.doStartTag();
/* 1950 */                       if (_jspx_eval_c_005fwhen_005f18 != 0) {
/*      */                         for (;;) {
/* 1952 */                           out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 1953 */                           out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1954 */                           out.write("\n    </a>    \n ");
/* 1955 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f18.doAfterBody();
/* 1956 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1960 */                       if (_jspx_th_c_005fwhen_005f18.doEndTag() == 5) {
/* 1961 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18); return;
/*      */                       }
/*      */                       
/* 1964 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 1965 */                       out.write(10);
/* 1966 */                       out.write(32);
/*      */                       
/* 1968 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f18 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1969 */                       _jspx_th_c_005fotherwise_005f18.setPageContext(_jspx_page_context);
/* 1970 */                       _jspx_th_c_005fotherwise_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/* 1971 */                       int _jspx_eval_c_005fotherwise_005f18 = _jspx_th_c_005fotherwise_005f18.doStartTag();
/* 1972 */                       if (_jspx_eval_c_005fotherwise_005f18 != 0) {
/*      */                         for (;;) {
/* 1974 */                           out.write(10);
/* 1975 */                           out.write(9);
/* 1976 */                           out.write(32);
/* 1977 */                           out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1978 */                           out.write(10);
/* 1979 */                           out.write(32);
/* 1980 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f18.doAfterBody();
/* 1981 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1985 */                       if (_jspx_th_c_005fotherwise_005f18.doEndTag() == 5) {
/* 1986 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18); return;
/*      */                       }
/*      */                       
/* 1989 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18);
/* 1990 */                       out.write(10);
/* 1991 */                       out.write(32);
/* 1992 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f18.doAfterBody();
/* 1993 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1997 */                   if (_jspx_th_c_005fchoose_005f18.doEndTag() == 5) {
/* 1998 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18); return;
/*      */                   }
/*      */                   
/* 2001 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18);
/* 2002 */                   out.write("\n    </td>\n</tr>\n\n\n");
/* 2003 */                   if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 2004 */                     out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 2006 */                     ChooseTag _jspx_th_c_005fchoose_005f19 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2007 */                     _jspx_th_c_005fchoose_005f19.setPageContext(_jspx_page_context);
/* 2008 */                     _jspx_th_c_005fchoose_005f19.setParent(_jspx_th_tiles_005fput_005f2);
/* 2009 */                     int _jspx_eval_c_005fchoose_005f19 = _jspx_th_c_005fchoose_005f19.doStartTag();
/* 2010 */                     if (_jspx_eval_c_005fchoose_005f19 != 0) {
/*      */                       for (;;) {
/* 2012 */                         out.write(10);
/*      */                         
/* 2014 */                         WhenTag _jspx_th_c_005fwhen_005f19 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2015 */                         _jspx_th_c_005fwhen_005f19.setPageContext(_jspx_page_context);
/* 2016 */                         _jspx_th_c_005fwhen_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/*      */                         
/* 2018 */                         _jspx_th_c_005fwhen_005f19.setTest("${param.method!='SMSServerConfiguration'}");
/* 2019 */                         int _jspx_eval_c_005fwhen_005f19 = _jspx_th_c_005fwhen_005f19.doStartTag();
/* 2020 */                         if (_jspx_eval_c_005fwhen_005f19 != 0) {
/*      */                           for (;;) {
/* 2022 */                             out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 2023 */                             out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 2024 */                             out.write("\n    </a>\n ");
/* 2025 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f19.doAfterBody();
/* 2026 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2030 */                         if (_jspx_th_c_005fwhen_005f19.doEndTag() == 5) {
/* 2031 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19); return;
/*      */                         }
/*      */                         
/* 2034 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 2035 */                         out.write(10);
/* 2036 */                         out.write(32);
/*      */                         
/* 2038 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f19 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2039 */                         _jspx_th_c_005fotherwise_005f19.setPageContext(_jspx_page_context);
/* 2040 */                         _jspx_th_c_005fotherwise_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/* 2041 */                         int _jspx_eval_c_005fotherwise_005f19 = _jspx_th_c_005fotherwise_005f19.doStartTag();
/* 2042 */                         if (_jspx_eval_c_005fotherwise_005f19 != 0) {
/*      */                           for (;;) {
/* 2044 */                             out.write("\n         ");
/* 2045 */                             out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 2046 */                             out.write(10);
/* 2047 */                             out.write(32);
/* 2048 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f19.doAfterBody();
/* 2049 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2053 */                         if (_jspx_th_c_005fotherwise_005f19.doEndTag() == 5) {
/* 2054 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19); return;
/*      */                         }
/*      */                         
/* 2057 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19);
/* 2058 */                         out.write(10);
/* 2059 */                         out.write(32);
/* 2060 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f19.doAfterBody();
/* 2061 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2065 */                     if (_jspx_th_c_005fchoose_005f19.doEndTag() == 5) {
/* 2066 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19); return;
/*      */                     }
/*      */                     
/* 2069 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19);
/* 2070 */                     out.write("\n    </td>\n</tr>\n");
/*      */                   }
/* 2072 */                   out.write("\n\n\n ");
/*      */                   
/* 2074 */                   if (!Constants.sqlManager)
/*      */                   {
/*      */ 
/* 2077 */                     out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                     
/* 2079 */                     ChooseTag _jspx_th_c_005fchoose_005f20 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2080 */                     _jspx_th_c_005fchoose_005f20.setPageContext(_jspx_page_context);
/* 2081 */                     _jspx_th_c_005fchoose_005f20.setParent(_jspx_th_tiles_005fput_005f2);
/* 2082 */                     int _jspx_eval_c_005fchoose_005f20 = _jspx_th_c_005fchoose_005f20.doStartTag();
/* 2083 */                     if (_jspx_eval_c_005fchoose_005f20 != 0) {
/*      */                       for (;;) {
/* 2085 */                         out.write(10);
/*      */                         
/* 2087 */                         WhenTag _jspx_th_c_005fwhen_005f20 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2088 */                         _jspx_th_c_005fwhen_005f20.setPageContext(_jspx_page_context);
/* 2089 */                         _jspx_th_c_005fwhen_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/*      */                         
/* 2091 */                         _jspx_th_c_005fwhen_005f20.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 2092 */                         int _jspx_eval_c_005fwhen_005f20 = _jspx_th_c_005fwhen_005f20.doStartTag();
/* 2093 */                         if (_jspx_eval_c_005fwhen_005f20 != 0) {
/*      */                           for (;;) {
/* 2095 */                             out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 2096 */                             out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2097 */                             out.write("\n    </a>\n ");
/* 2098 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f20.doAfterBody();
/* 2099 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2103 */                         if (_jspx_th_c_005fwhen_005f20.doEndTag() == 5) {
/* 2104 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20); return;
/*      */                         }
/*      */                         
/* 2107 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20);
/* 2108 */                         out.write(10);
/* 2109 */                         out.write(32);
/*      */                         
/* 2111 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f20 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2112 */                         _jspx_th_c_005fotherwise_005f20.setPageContext(_jspx_page_context);
/* 2113 */                         _jspx_th_c_005fotherwise_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/* 2114 */                         int _jspx_eval_c_005fotherwise_005f20 = _jspx_th_c_005fotherwise_005f20.doStartTag();
/* 2115 */                         if (_jspx_eval_c_005fotherwise_005f20 != 0) {
/*      */                           for (;;) {
/* 2117 */                             out.write(10);
/* 2118 */                             out.write(9);
/* 2119 */                             out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2120 */                             out.write(10);
/* 2121 */                             out.write(32);
/* 2122 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f20.doAfterBody();
/* 2123 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2127 */                         if (_jspx_th_c_005fotherwise_005f20.doEndTag() == 5) {
/* 2128 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20); return;
/*      */                         }
/*      */                         
/* 2131 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20);
/* 2132 */                         out.write(10);
/* 2133 */                         out.write(32);
/* 2134 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f20.doAfterBody();
/* 2135 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2139 */                     if (_jspx_th_c_005fchoose_005f20.doEndTag() == 5) {
/* 2140 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20); return;
/*      */                     }
/*      */                     
/* 2143 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20);
/* 2144 */                     out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                     
/* 2146 */                     ChooseTag _jspx_th_c_005fchoose_005f21 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2147 */                     _jspx_th_c_005fchoose_005f21.setPageContext(_jspx_page_context);
/* 2148 */                     _jspx_th_c_005fchoose_005f21.setParent(_jspx_th_tiles_005fput_005f2);
/* 2149 */                     int _jspx_eval_c_005fchoose_005f21 = _jspx_th_c_005fchoose_005f21.doStartTag();
/* 2150 */                     if (_jspx_eval_c_005fchoose_005f21 != 0) {
/*      */                       for (;;) {
/* 2152 */                         out.write(10);
/*      */                         
/* 2154 */                         WhenTag _jspx_th_c_005fwhen_005f21 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2155 */                         _jspx_th_c_005fwhen_005f21.setPageContext(_jspx_page_context);
/* 2156 */                         _jspx_th_c_005fwhen_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/*      */                         
/* 2158 */                         _jspx_th_c_005fwhen_005f21.setTest("${uri !='/Upload.do'}");
/* 2159 */                         int _jspx_eval_c_005fwhen_005f21 = _jspx_th_c_005fwhen_005f21.doStartTag();
/* 2160 */                         if (_jspx_eval_c_005fwhen_005f21 != 0) {
/*      */                           for (;;) {
/* 2162 */                             out.write("   \n        ");
/*      */                             
/* 2164 */                             AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2165 */                             _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 2166 */                             _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f21);
/*      */                             
/* 2168 */                             _jspx_th_am_005fadminlink_005f0.setHref("/Upload.do");
/*      */                             
/* 2170 */                             _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 2171 */                             int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 2172 */                             if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 2173 */                               if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2174 */                                 out = _jspx_page_context.pushBody();
/* 2175 */                                 _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 2176 */                                 _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2179 */                                 out.write("\n           ");
/* 2180 */                                 out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 2181 */                                 out.write("\n            ");
/* 2182 */                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 2183 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2186 */                               if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2187 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2190 */                             if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 2191 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                             }
/*      */                             
/* 2194 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 2195 */                             out.write(10);
/* 2196 */                             out.write(10);
/* 2197 */                             out.write(32);
/* 2198 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f21.doAfterBody();
/* 2199 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2203 */                         if (_jspx_th_c_005fwhen_005f21.doEndTag() == 5) {
/* 2204 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21); return;
/*      */                         }
/*      */                         
/* 2207 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21);
/* 2208 */                         out.write(10);
/* 2209 */                         out.write(32);
/*      */                         
/* 2211 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f21 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2212 */                         _jspx_th_c_005fotherwise_005f21.setPageContext(_jspx_page_context);
/* 2213 */                         _jspx_th_c_005fotherwise_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/* 2214 */                         int _jspx_eval_c_005fotherwise_005f21 = _jspx_th_c_005fotherwise_005f21.doStartTag();
/* 2215 */                         if (_jspx_eval_c_005fotherwise_005f21 != 0) {
/*      */                           for (;;) {
/* 2217 */                             out.write(10);
/* 2218 */                             out.write(9);
/* 2219 */                             out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 2220 */                             out.write(10);
/* 2221 */                             out.write(32);
/* 2222 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f21.doAfterBody();
/* 2223 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2227 */                         if (_jspx_th_c_005fotherwise_005f21.doEndTag() == 5) {
/* 2228 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21); return;
/*      */                         }
/*      */                         
/* 2231 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21);
/* 2232 */                         out.write(10);
/* 2233 */                         out.write(32);
/* 2234 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f21.doAfterBody();
/* 2235 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2239 */                     if (_jspx_th_c_005fchoose_005f21.doEndTag() == 5) {
/* 2240 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21); return;
/*      */                     }
/*      */                     
/* 2243 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21);
/* 2244 */                     out.write("\n    </td>\n</tr>\n \n ");
/*      */                   }
/*      */                   
/*      */ 
/* 2248 */                   out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                   
/* 2250 */                   ChooseTag _jspx_th_c_005fchoose_005f22 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2251 */                   _jspx_th_c_005fchoose_005f22.setPageContext(_jspx_page_context);
/* 2252 */                   _jspx_th_c_005fchoose_005f22.setParent(_jspx_th_tiles_005fput_005f2);
/* 2253 */                   int _jspx_eval_c_005fchoose_005f22 = _jspx_th_c_005fchoose_005f22.doStartTag();
/* 2254 */                   if (_jspx_eval_c_005fchoose_005f22 != 0) {
/*      */                     for (;;) {
/* 2256 */                       out.write(10);
/*      */                       
/* 2258 */                       WhenTag _jspx_th_c_005fwhen_005f22 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2259 */                       _jspx_th_c_005fwhen_005f22.setPageContext(_jspx_page_context);
/* 2260 */                       _jspx_th_c_005fwhen_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/*      */                       
/* 2262 */                       _jspx_th_c_005fwhen_005f22.setTest("${uri !='/admin/userconfiguration.do'}");
/* 2263 */                       int _jspx_eval_c_005fwhen_005f22 = _jspx_th_c_005fwhen_005f22.doStartTag();
/* 2264 */                       if (_jspx_eval_c_005fwhen_005f22 != 0) {
/*      */                         for (;;) {
/* 2266 */                           out.write("\n    \n        ");
/*      */                           
/* 2268 */                           AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2269 */                           _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 2270 */                           _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f22);
/*      */                           
/* 2272 */                           _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                           
/* 2274 */                           _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 2275 */                           int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 2276 */                           if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 2277 */                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2278 */                               out = _jspx_page_context.pushBody();
/* 2279 */                               _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 2280 */                               _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2283 */                               out.write("\n       ");
/* 2284 */                               out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2285 */                               out.write("\n        ");
/* 2286 */                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 2287 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2290 */                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2291 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2294 */                           if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 2295 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                           }
/*      */                           
/* 2298 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 2299 */                           out.write(10);
/* 2300 */                           out.write(10);
/* 2301 */                           out.write(32);
/* 2302 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f22.doAfterBody();
/* 2303 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2307 */                       if (_jspx_th_c_005fwhen_005f22.doEndTag() == 5) {
/* 2308 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22); return;
/*      */                       }
/*      */                       
/* 2311 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22);
/* 2312 */                       out.write(10);
/* 2313 */                       out.write(32);
/*      */                       
/* 2315 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f22 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2316 */                       _jspx_th_c_005fotherwise_005f22.setPageContext(_jspx_page_context);
/* 2317 */                       _jspx_th_c_005fotherwise_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/* 2318 */                       int _jspx_eval_c_005fotherwise_005f22 = _jspx_th_c_005fotherwise_005f22.doStartTag();
/* 2319 */                       if (_jspx_eval_c_005fotherwise_005f22 != 0) {
/*      */                         for (;;) {
/* 2321 */                           out.write(10);
/* 2322 */                           out.write(9);
/* 2323 */                           out.write(32);
/* 2324 */                           out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2325 */                           out.write(10);
/* 2326 */                           out.write(32);
/* 2327 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f22.doAfterBody();
/* 2328 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2332 */                       if (_jspx_th_c_005fotherwise_005f22.doEndTag() == 5) {
/* 2333 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22); return;
/*      */                       }
/*      */                       
/* 2336 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22);
/* 2337 */                       out.write(10);
/* 2338 */                       out.write(32);
/* 2339 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f22.doAfterBody();
/* 2340 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2344 */                   if (_jspx_th_c_005fchoose_005f22.doEndTag() == 5) {
/* 2345 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22); return;
/*      */                   }
/*      */                   
/* 2348 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22);
/* 2349 */                   out.write("\n    </td>\n</tr>\n   \n\n ");
/* 2350 */                   if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 2351 */                     out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                     
/* 2353 */                     ChooseTag _jspx_th_c_005fchoose_005f23 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2354 */                     _jspx_th_c_005fchoose_005f23.setPageContext(_jspx_page_context);
/* 2355 */                     _jspx_th_c_005fchoose_005f23.setParent(_jspx_th_tiles_005fput_005f2);
/* 2356 */                     int _jspx_eval_c_005fchoose_005f23 = _jspx_th_c_005fchoose_005f23.doStartTag();
/* 2357 */                     if (_jspx_eval_c_005fchoose_005f23 != 0) {
/*      */                       for (;;) {
/* 2359 */                         out.write("\n   ");
/*      */                         
/* 2361 */                         WhenTag _jspx_th_c_005fwhen_005f23 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2362 */                         _jspx_th_c_005fwhen_005f23.setPageContext(_jspx_page_context);
/* 2363 */                         _jspx_th_c_005fwhen_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/*      */                         
/* 2365 */                         _jspx_th_c_005fwhen_005f23.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 2366 */                         int _jspx_eval_c_005fwhen_005f23 = _jspx_th_c_005fwhen_005f23.doStartTag();
/* 2367 */                         if (_jspx_eval_c_005fwhen_005f23 != 0) {
/*      */                           for (;;) {
/* 2369 */                             out.write("\n    ");
/*      */                             
/* 2371 */                             AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2372 */                             _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 2373 */                             _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f23);
/*      */                             
/* 2375 */                             _jspx_th_am_005fadminlink_005f2.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                             
/* 2377 */                             _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 2378 */                             int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 2379 */                             if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 2380 */                               if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 2381 */                                 out = _jspx_page_context.pushBody();
/* 2382 */                                 _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 2383 */                                 _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2386 */                                 out.write(10);
/* 2387 */                                 out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 2388 */                                 out.write("\n    ");
/* 2389 */                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 2390 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2393 */                               if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 2394 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2397 */                             if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 2398 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                             }
/*      */                             
/* 2401 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 2402 */                             out.write("\n   ");
/* 2403 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f23.doAfterBody();
/* 2404 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2408 */                         if (_jspx_th_c_005fwhen_005f23.doEndTag() == 5) {
/* 2409 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23); return;
/*      */                         }
/*      */                         
/* 2412 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23);
/* 2413 */                         out.write("\n   ");
/*      */                         
/* 2415 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f23 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2416 */                         _jspx_th_c_005fotherwise_005f23.setPageContext(_jspx_page_context);
/* 2417 */                         _jspx_th_c_005fotherwise_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/* 2418 */                         int _jspx_eval_c_005fotherwise_005f23 = _jspx_th_c_005fotherwise_005f23.doStartTag();
/* 2419 */                         if (_jspx_eval_c_005fotherwise_005f23 != 0) {
/*      */                           for (;;) {
/* 2421 */                             out.write(10);
/* 2422 */                             out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 2423 */                             out.write("\n   ");
/* 2424 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f23.doAfterBody();
/* 2425 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2429 */                         if (_jspx_th_c_005fotherwise_005f23.doEndTag() == 5) {
/* 2430 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23); return;
/*      */                         }
/*      */                         
/* 2433 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23);
/* 2434 */                         out.write(10);
/* 2435 */                         out.write(32);
/* 2436 */                         out.write(32);
/* 2437 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f23.doAfterBody();
/* 2438 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2442 */                     if (_jspx_th_c_005fchoose_005f23.doEndTag() == 5) {
/* 2443 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23); return;
/*      */                     }
/*      */                     
/* 2446 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23);
/* 2447 */                     out.write("\n </td>\n</tr>\n  ");
/*      */                   }
/* 2449 */                   out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                   
/* 2451 */                   ChooseTag _jspx_th_c_005fchoose_005f24 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2452 */                   _jspx_th_c_005fchoose_005f24.setPageContext(_jspx_page_context);
/* 2453 */                   _jspx_th_c_005fchoose_005f24.setParent(_jspx_th_tiles_005fput_005f2);
/* 2454 */                   int _jspx_eval_c_005fchoose_005f24 = _jspx_th_c_005fchoose_005f24.doStartTag();
/* 2455 */                   if (_jspx_eval_c_005fchoose_005f24 != 0) {
/*      */                     for (;;) {
/* 2457 */                       out.write("\n   ");
/*      */                       
/* 2459 */                       WhenTag _jspx_th_c_005fwhen_005f24 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2460 */                       _jspx_th_c_005fwhen_005f24.setPageContext(_jspx_page_context);
/* 2461 */                       _jspx_th_c_005fwhen_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/*      */                       
/* 2463 */                       _jspx_th_c_005fwhen_005f24.setTest("${param.method!='showDataCleanUp'}");
/* 2464 */                       int _jspx_eval_c_005fwhen_005f24 = _jspx_th_c_005fwhen_005f24.doStartTag();
/* 2465 */                       if (_jspx_eval_c_005fwhen_005f24 != 0) {
/*      */                         for (;;) {
/* 2467 */                           out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 2468 */                           out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 2469 */                           out.write("\n    </a>\n   ");
/* 2470 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f24.doAfterBody();
/* 2471 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2475 */                       if (_jspx_th_c_005fwhen_005f24.doEndTag() == 5) {
/* 2476 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24); return;
/*      */                       }
/*      */                       
/* 2479 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24);
/* 2480 */                       out.write("\n   ");
/*      */                       
/* 2482 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f24 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2483 */                       _jspx_th_c_005fotherwise_005f24.setPageContext(_jspx_page_context);
/* 2484 */                       _jspx_th_c_005fotherwise_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/* 2485 */                       int _jspx_eval_c_005fotherwise_005f24 = _jspx_th_c_005fotherwise_005f24.doStartTag();
/* 2486 */                       if (_jspx_eval_c_005fotherwise_005f24 != 0) {
/*      */                         for (;;) {
/* 2488 */                           out.write(10);
/* 2489 */                           out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 2490 */                           out.write("\n   ");
/* 2491 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f24.doAfterBody();
/* 2492 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2496 */                       if (_jspx_th_c_005fotherwise_005f24.doEndTag() == 5) {
/* 2497 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24); return;
/*      */                       }
/*      */                       
/* 2500 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24);
/* 2501 */                       out.write(10);
/* 2502 */                       out.write(32);
/* 2503 */                       out.write(32);
/* 2504 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f24.doAfterBody();
/* 2505 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2509 */                   if (_jspx_th_c_005fchoose_005f24.doEndTag() == 5) {
/* 2510 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24); return;
/*      */                   }
/*      */                   
/* 2513 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24);
/* 2514 */                   out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 2515 */                   out.write("\t\t\n\t        ");
/*      */                 }
/* 2517 */                 out.write("\t\t\n\n\t <br>\t\t\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\"><tr>\t\t\n\t    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 2518 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 2519 */                 out.write("</td>\t\t\n\t    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 2520 */                 if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                   return;
/* 2522 */                 out.write("/img_quicknote.gif\" hspace=\"5\"></td>\t\t\n\t  </tr>\t\t\n\t  <tr>\t\t\n\t    <td colspan=\"2\" class=\"quicknote\">");
/* 2523 */                 out.print(FormatUtil.getString("am.webclient.customattribute.quicknote.text"));
/* 2524 */                 out.write("</td>\t\t\n\t  </tr>\t\t\n\t</table>\t\t\n\t");
/* 2525 */                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 2526 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 2529 */               if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2530 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 2533 */             if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2534 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */             }
/*      */             
/* 2537 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 2538 */             out.write(10);
/* 2539 */             out.write(10);
/*      */             
/* 2541 */             PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2542 */             _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2543 */             _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */             
/* 2545 */             _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */             
/* 2547 */             _jspx_th_tiles_005fput_005f3.setType("string");
/* 2548 */             int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2549 */             if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2550 */               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2551 */                 out = _jspx_page_context.pushBody();
/* 2552 */                 _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2553 */                 _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */               }
/*      */               for (;;) {
/* 2556 */                 out.write(10);
/* 2557 */                 out.write(10);
/* 2558 */                 if (request.getAttribute("message") != null)
/*      */                 {
/* 2560 */                   out.write("\n\n <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n <tr>\n          <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n              <tr>\n               <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                <td width=\"95%\" class=\"message\">  ");
/* 2561 */                   out.print(request.getAttribute("message"));
/* 2562 */                   out.write("\n                 </td>\n              </tr>\n          </table></td></tr></table>\n\n\n  ");
/*      */                 }
/* 2564 */                 out.write(10);
/* 2565 */                 out.write(10);
/* 2566 */                 out.write(10);
/*      */                 
/* 2568 */                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2569 */                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2570 */                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2572 */                 _jspx_th_c_005fif_005f4.setTest("${empty param.popup || param.popup == false}");
/* 2573 */                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2574 */                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                   for (;;) {
/* 2576 */                     out.write("\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t");
/*      */                     
/* 2578 */                     if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */                     {
/*      */ 
/* 2581 */                       out.write("\n\t  <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2582 */                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 2583 */                       out.write(" &gt; <span class=\"bcactive\">");
/* 2584 */                       out.print(FormatUtil.getString("am.reporting.admin.summarymail.subject"));
/* 2585 */                       out.write("</span></td>\n\t");
/*      */                     }
/*      */                     else {
/* 2588 */                       out.write("\n\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2589 */                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 2590 */                       out.write(" &gt; <span class=\"bcactive\">");
/* 2591 */                       out.print(FormatUtil.getString("am.webclient.customattribute.heading.text"));
/* 2592 */                       out.write("</span></td>\n\t");
/*      */                     }
/* 2594 */                     out.write("\n\t</tr>\n</table>\n");
/* 2595 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2596 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2600 */                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2601 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                 }
/*      */                 
/* 2604 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2605 */                 out.write("\n\n\n\n\n <table width=\"100%\" class=\"itadmin-hide\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n\n   <tr class=\"tabBtmLine\">\n\n    <td nowrap=\"nowrap\">\n           \t<table id=\"InnerTab\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"innertab_btm_space\">\n               <tbody>\n                 <tr>\n                   <td width=\"17\">\n\n                   </td>\n\n                   <td><div id=\"cusrep\" style=\"display:block;\"><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                       <tbody>\n                         <tr>\n                           <td class=\"tbSelected_Left\" id=\"customreplink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                           <td class=\"tbSelected_Middle\" id=\"customreplink\" onClick=\"javascript:showHide('customreport')\">\n                      &nbsp;<span class=\"tabLink\">");
/* 2606 */                 out.print(FormatUtil.getString("am.reporting.custom.enable.tab.text"));
/* 2607 */                 out.write("</span>\n                           </td>\n                           <td class=\"tbselected_Right\" id=\"customreplink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                         </tr>\n                       </tbody>\n                     </table></div>\n                   </td>\n\n                   <td><div id=\"downrep\" style=\"display:block;\"><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                       <tbody>\n                         <tr>\n                           <td class=\"tbUnselected_Left\" id=\"downsumreplink-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                           <td class=\"tbUnselected_Middle\" id=\"downsumreplink\" onClick=\"javascript:showHide('downtimesummary')\">\n                          &nbsp;<span class=\"tabLink\">");
/* 2608 */                 out.print(FormatUtil.getString("am.reporting.admin.summarymail.subject"));
/* 2609 */                 out.write("</span>\n                           </td>\n                           <td class=\"tbUnselected_Right\" id=\"downsumreplink-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n                         </tr>\n                       </tbody>\n                     </table></div>\n                   </td>\n\n  </tr></tbody></table></td></tr>\n </table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"70%\" valign=\"top\">\n\n");
/*      */                 
/* 2611 */                 FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2612 */                 _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2613 */                 _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2615 */                 _jspx_th_html_005fform_005f0.setAction("/customReports");
/*      */                 
/* 2617 */                 _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 2618 */                 int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2619 */                 if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                   for (;;) {
/* 2621 */                     out.write("\n\n<div id=\"customrepdiv\" style=\"display:none\">\n\n<table width=\"98%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" class=\"lrtbdarkborder itadmin-no-decor\">\n        <tr>\n<td width='100%' >\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" >\n     <tr class=\"itadmin-hide\"><td  width=\"100%\" class=\"tableheadingbborder\"  colspan='2'>&nbsp;\n       ");
/* 2622 */                     out.print(FormatUtil.getString("am.webclient.customattribute.selectedresource.text"));
/* 2623 */                     out.write(" </td>\n    </tr>\n \t\n    <tr><td colspan='2' width='100%'>\n    <table width=\"100%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" >\n\n\t");
/*      */                     
/* 2625 */                     if (!Constants.sqlManager)
/*      */                     {
/* 2627 */                       boolean isCategoryOpen = false;
/*      */                       
/* 2629 */                       out.write("\n    <td class=\"bodytext label-align\" width=\"25%\">");
/* 2630 */                       out.print(FormatUtil.getString("am.webclient.customattribute.monitortype.text"));
/* 2631 */                       out.write("</td>\n\t  <td width=\"75%\" ><!--");
/*      */                       
/* 2633 */                       SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 2634 */                       _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2635 */                       _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/* 2637 */                       _jspx_th_html_005fselect_005f0.setProperty("resTypeValue");
/*      */                       
/* 2639 */                       _jspx_th_html_005fselect_005f0.setValue(toappend);
/*      */                       
/* 2641 */                       _jspx_th_html_005fselect_005f0.setStyleClass("formtext1");
/*      */                       
/* 2643 */                       _jspx_th_html_005fselect_005f0.setStyle("width:180px; margin-bottom:2px;");
/*      */                       
/* 2645 */                       _jspx_th_html_005fselect_005f0.setOnchange("javascript:getAttributes()");
/* 2646 */                       int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2647 */                       if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2648 */                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2649 */                           out = _jspx_page_context.pushBody();
/* 2650 */                           _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2651 */                           _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2654 */                           if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/*      */                             return;
/* 2656 */                           out.write(32);
/* 2657 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 2658 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2661 */                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2662 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2665 */                       if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 2666 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                       }
/*      */                       
/* 2669 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 2670 */                       out.write("-->\n\t  ");
/*      */                       
/* 2672 */                       SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 2673 */                       _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 2674 */                       _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/* 2676 */                       _jspx_th_html_005fselect_005f1.setProperty("resTypeValue");
/*      */                       
/* 2678 */                       _jspx_th_html_005fselect_005f1.setValue(toappend);
/*      */                       
/* 2680 */                       _jspx_th_html_005fselect_005f1.setStyleClass("formtext default");
/*      */                       
/* 2682 */                       _jspx_th_html_005fselect_005f1.setOnchange("javascript:getAttributes()");
/* 2683 */                       int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 2684 */                       if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 2685 */                         if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 2686 */                           out = _jspx_page_context.pushBody();
/* 2687 */                           _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 2688 */                           _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2691 */                           out.write("\n\t  \t");
/*      */                           
/* 2693 */                           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2694 */                           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2695 */                           _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_html_005fselect_005f1);
/*      */                           
/* 2697 */                           _jspx_th_logic_005fiterate_005f0.setName("resourcetypenameforreports");
/*      */                           
/* 2699 */                           _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                           
/* 2701 */                           _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                           
/* 2703 */                           _jspx_th_logic_005fiterate_005f0.setType("java.util.Properties");
/* 2704 */                           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2705 */                           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2706 */                             Properties row = null;
/* 2707 */                             Integer j = null;
/* 2708 */                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2709 */                               out = _jspx_page_context.pushBody();
/* 2710 */                               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2711 */                               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                             }
/* 2713 */                             row = (Properties)_jspx_page_context.findAttribute("row");
/* 2714 */                             j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                             for (;;) {
/* 2716 */                               out.write("\n\t  \t");
/* 2717 */                               String[] categoryLink = Constants.categoryLink;
/* 2718 */                               boolean islink = false;
/* 2719 */                               for (int c = 0; c < categoryLink.length; c++)
/*      */                               {
/* 2721 */                                 if (categoryLink[c].equals(row.getProperty("value")))
/*      */                                 {
/* 2723 */                                   islink = true;
/*      */                                 }
/*      */                               }
/*      */                               
/* 2727 */                               out.write("                                    \n\t  \t\t");
/* 2728 */                               if (islink) {
/* 2729 */                                 if (isCategoryOpen) {
/* 2730 */                                   out.write(" \n\t  \t\t\t\t</optgroup>\n\t  \t\t\t");
/*      */                                 }
/* 2732 */                                 isCategoryOpen = true;
/* 2733 */                                 out.write("\n\t  \t\t\t<optgroup  label=\"");
/* 2734 */                                 out.print(row.getProperty("label"));
/* 2735 */                                 out.write(34);
/* 2736 */                                 out.write(62);
/*      */                               } else {
/* 2738 */                                 out.write("\n\t  \t\t");
/*      */                                 
/* 2740 */                                 OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2741 */                                 _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2742 */                                 _jspx_th_html_005foption_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                 
/* 2744 */                                 _jspx_th_html_005foption_005f0.setValue(row.getProperty("value"));
/* 2745 */                                 int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2746 */                                 if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2747 */                                   if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2748 */                                     out = _jspx_page_context.pushBody();
/* 2749 */                                     _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 2750 */                                     _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 2753 */                                     out.print(row.getProperty("label"));
/* 2754 */                                     int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 2755 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 2758 */                                   if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2759 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 2762 */                                 if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2763 */                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                 }
/*      */                                 
/* 2766 */                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/*      */                               }
/* 2768 */                               out.write("\n\t  \t");
/* 2769 */                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2770 */                               row = (Properties)_jspx_page_context.findAttribute("row");
/* 2771 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/* 2772 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2775 */                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2776 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2779 */                           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2780 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                           }
/*      */                           
/* 2783 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2784 */                           out.write("\n\t  \t\t");
/* 2785 */                           if (isCategoryOpen) {
/* 2786 */                             out.write(" \n\t  \t\t\t\t</optgroup>\n\t  \t\t\t");
/*      */                           }
/* 2788 */                           out.write("\n\t  ");
/* 2789 */                           int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 2790 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2793 */                         if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 2794 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2797 */                       if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 2798 */                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                       }
/*      */                       
/* 2801 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 2802 */                       out.write("\n\t  </td>\n    </tr>\n    \n\t");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*      */ 
/* 2808 */                       out.write("\n \t<input type=\"hidden\" name=\"resTypeValue\" value=\"MSSQL-DB-Server\">\n\t");
/*      */                     }
/*      */                     
/*      */ 
/* 2812 */                     out.write("\n    <td class=\"bodytext label-align\" width=\"25%\" valign=top>");
/* 2813 */                     out.print(FormatUtil.getString("am.webclient.customattribute.attribute.text"));
/* 2814 */                     out.write("</td>\n    <td  width=\"75%\">\n    <div id=\"attributedetail\" style=\"display:block;overflow:auto;width:100%;height:375px\" >\n\n\t  </div>\n\n    </td>\n    </tr>\n    </table>\n    </td></tr>\n\n    </table></td></tr>\n<tr><td colspan='2' width='100%'>\n    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\n\n\n\n    <tr>\n      <td width=\"25%\" class=\"tablebottom itadmin-no-decor\" >&nbsp;</td>\n      <td width=\"75%\" height=\"31\" class=\"tablebottom itadmin-no-decor\" >\n\n           <input type=\"hidden\" name='method' value='enableCustomReports'>\n           <input type=\"hidden\" name='resourceid' value='");
/* 2815 */                     out.print(resourceId);
/* 2816 */                     out.write("'>\n           <input type=\"hidden\" name='attributeid' value='");
/* 2817 */                     out.print(request.getParameter("attributeid"));
/* 2818 */                     out.write("'>\n           <input type=\"hidden\" name='PRINTER_FRIENDLY' value='");
/* 2819 */                     out.print(isPrint);
/* 2820 */                     out.write("'>\n           <input type=\"hidden\" name='popup' value='");
/* 2821 */                     out.print(request.getParameter("popup"));
/* 2822 */                     out.write("'>\n           <input type=\"hidden\" name='selectedType' value='");
/* 2823 */                     out.print(request.getParameter("selectedType"));
/* 2824 */                     out.write("'>\n          <input name=\"Submit\" value=\"");
/* 2825 */                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 2826 */                     out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"fnFormSubmit();\">\n         ");
/*      */                     
/* 2828 */                     ChooseTag _jspx_th_c_005fchoose_005f25 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2829 */                     _jspx_th_c_005fchoose_005f25.setPageContext(_jspx_page_context);
/* 2830 */                     _jspx_th_c_005fchoose_005f25.setParent(_jspx_th_html_005fform_005f0);
/* 2831 */                     int _jspx_eval_c_005fchoose_005f25 = _jspx_th_c_005fchoose_005f25.doStartTag();
/* 2832 */                     if (_jspx_eval_c_005fchoose_005f25 != 0) {
/*      */                       for (;;) {
/* 2834 */                         out.write("\n        ");
/*      */                         
/* 2836 */                         WhenTag _jspx_th_c_005fwhen_005f25 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2837 */                         _jspx_th_c_005fwhen_005f25.setPageContext(_jspx_page_context);
/* 2838 */                         _jspx_th_c_005fwhen_005f25.setParent(_jspx_th_c_005fchoose_005f25);
/*      */                         
/* 2840 */                         _jspx_th_c_005fwhen_005f25.setTest("${empty param.popup || param.popup == false}");
/* 2841 */                         int _jspx_eval_c_005fwhen_005f25 = _jspx_th_c_005fwhen_005f25.doStartTag();
/* 2842 */                         if (_jspx_eval_c_005fwhen_005f25 != 0) {
/*      */                           for (;;) {
/* 2844 */                             out.write("\n        \t&nbsp;&nbsp;<input type=\"button\" name=\"Submit3\" value=\"");
/* 2845 */                             out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 2846 */                             out.write("\" onClick=\"history.back();\" class=\"buttons btn_link\">\n        ");
/* 2847 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f25.doAfterBody();
/* 2848 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2852 */                         if (_jspx_th_c_005fwhen_005f25.doEndTag() == 5) {
/* 2853 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f25); return;
/*      */                         }
/*      */                         
/* 2856 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f25);
/* 2857 */                         out.write("\n        ");
/*      */                         
/* 2859 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f25 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2860 */                         _jspx_th_c_005fotherwise_005f25.setPageContext(_jspx_page_context);
/* 2861 */                         _jspx_th_c_005fotherwise_005f25.setParent(_jspx_th_c_005fchoose_005f25);
/* 2862 */                         int _jspx_eval_c_005fotherwise_005f25 = _jspx_th_c_005fotherwise_005f25.doStartTag();
/* 2863 */                         if (_jspx_eval_c_005fotherwise_005f25 != 0) {
/*      */                           for (;;) {
/* 2865 */                             out.write("\n        \t&nbsp;&nbsp;<input name=\"close\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 2866 */                             out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 2867 */                             out.write("\" onclick='javascript:closeWindow()'>\n        ");
/* 2868 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f25.doAfterBody();
/* 2869 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2873 */                         if (_jspx_th_c_005fotherwise_005f25.doEndTag() == 5) {
/* 2874 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f25); return;
/*      */                         }
/*      */                         
/* 2877 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f25);
/* 2878 */                         out.write("\n        ");
/* 2879 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f25.doAfterBody();
/* 2880 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2884 */                     if (_jspx_th_c_005fchoose_005f25.doEndTag() == 5) {
/* 2885 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f25); return;
/*      */                     }
/*      */                     
/* 2888 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f25);
/* 2889 */                     out.write("\n         </td>\n       </td></tr>\n    </table>\n    </td></tr>\n</table>\n</div>\n");
/* 2890 */                     int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2891 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2895 */                 if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2896 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                 }
/*      */                 
/* 2899 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2900 */                 out.write(10);
/*      */                 
/* 2902 */                 FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2903 */                 _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 2904 */                 _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2906 */                 _jspx_th_html_005fform_005f1.setAction("/customReports");
/*      */                 
/* 2908 */                 _jspx_th_html_005fform_005f1.setStyle("display:inline;");
/* 2909 */                 int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 2910 */                 if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                   for (;;) {
/* 2912 */                     out.write("\n<input type=\"hidden\" name='method' value='saveSummaryMail'>\n<div id=\"sumrepdiv\" style=\"display:none\">\n\t<table width=\"98%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" class=\"lrtbdarkborder itadmin-no-decor\">\n        <tr>\n\t<td width='99%' colspan=\"3\">\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"6\" >\n     \t<tr class=\"itadmin-hide\"><td  width=\"100%\" class=\"tableheadingbborder\"  colspan='3'>&nbsp;\n       ");
/* 2913 */                     out.print(FormatUtil.getString("am.reporting.admin.summarymail.enable.table.heading"));
/* 2914 */                     out.write(" </td>\n    \t</tr>\n\t<tr>\n\t  <td class=\"bodytext label-align\" width=\"20%\">");
/* 2915 */                     out.print(FormatUtil.getString("am.webclient.maintenance.status"));
/* 2916 */                     out.write("</td>\n\t  <td class=\"bodytext\" width=\"40%\" valign=\"middle\" colspan='2'>");
/* 2917 */                     if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                       return;
/* 2919 */                     out.print(FormatUtil.getString("am.webclient.maintenance.enable"));
/* 2920 */                     out.write("<span class=\"tdindent\">");
/* 2921 */                     if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                       return;
/* 2923 */                     out.print(FormatUtil.getString("am.webclient.maintenance.disable"));
/* 2924 */                     out.write("</span></td>\n\t</tr>\n\t<tr>\n\t  <td class=\"bodytext label-align\" width=\"5%\">");
/* 2925 */                     out.print(FormatUtil.getString("webclient.performance.reports.index.transmittraffic.xaxisname"));
/* 2926 */                     out.write("</td>\n\t  <td class=\"bodytext\" width=\"50%\">\n\t  ");
/* 2927 */                     if (_jspx_meth_html_005fselect_005f2(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                       return;
/* 2929 */                     out.write(" &nbsp;:\n             ");
/* 2930 */                     if (_jspx_meth_html_005fselect_005f3(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                       return;
/* 2932 */                     out.write("\n\t  </td>\n\t\t<td width='40%'>&nbsp;</td>\n\t</tr>\n\t<tr><td colspan='3' width='100%' class=\"cellpadd-none\"><div id=\"actionmessage\" style=\"display:block\"  class='error-text'>\n\n          </div></td></tr>\n\t<tr>\n         <td class=\"bodytext label-align\" width=\"25%\" nowrap>&nbsp;");
/* 2933 */                     out.print(FormatUtil.getString("am.reporting.admin.summarymail.enable.actionto.text"));
/* 2934 */                     out.write("</td>\n");
/*      */                     
/* 2936 */                     com.adventnet.appmanager.db.AMConnectionPool cp = com.adventnet.appmanager.db.AMConnectionPool.getInstance();
/* 2937 */                     String adminid_query = "select EMAILID from AM_UserPasswordTable where USERNAME='admin'";
/* 2938 */                     java.sql.ResultSet adminset = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(adminid_query);
/* 2939 */                     adminset.next();
/* 2940 */                     String adminid = adminset.getString(1);
/*      */                     
/* 2942 */                     out.write("\n          <td class=\"bodytext\" width=\"35%\" valign=\"middle\" nowrap>\n          ");
/*      */                     
/* 2944 */                     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 2945 */                     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 2946 */                     _jspx_th_html_005fselect_005f4.setParent(_jspx_th_html_005fform_005f1);
/*      */                     
/* 2948 */                     _jspx_th_html_005fselect_005f4.setProperty("sendmail");
/*      */                     
/* 2950 */                     _jspx_th_html_005fselect_005f4.setStyleClass("default");
/* 2951 */                     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 2952 */                     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 2953 */                       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 2954 */                         out = _jspx_page_context.pushBody();
/* 2955 */                         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 2956 */                         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/* 2959 */                         out.write(10);
/*      */                         
/* 2961 */                         if ((adminid != null) && (adminid.length() > 1))
/*      */                         {
/*      */ 
/* 2964 */                           out.write(10);
/* 2965 */                           out.write(9);
/*      */                           
/* 2967 */                           OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2968 */                           _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2969 */                           _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f4);
/*      */                           
/* 2971 */                           _jspx_th_html_005foption_005f1.setValue(adminid);
/* 2972 */                           int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2973 */                           if (_jspx_eval_html_005foption_005f1 != 0) {
/* 2974 */                             if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2975 */                               out = _jspx_page_context.pushBody();
/* 2976 */                               _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 2977 */                               _jspx_th_html_005foption_005f1.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2980 */                               out.print(FormatUtil.getString(adminid));
/* 2981 */                               int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 2982 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2985 */                             if (_jspx_eval_html_005foption_005f1 != 1) {
/* 2986 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2989 */                           if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2990 */                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                           }
/*      */                           
/* 2993 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 2994 */                           out.write(10);
/*      */                         }
/*      */                         
/*      */ 
/* 2998 */                         out.write(10);
/* 2999 */                         out.write(9);
/* 3000 */                         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/*      */                           return;
/* 3002 */                         out.write("\n\n\n         ");
/* 3003 */                         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 3004 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 3007 */                       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 3008 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 3011 */                     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 3012 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f4); return;
/*      */                     }
/*      */                     
/* 3015 */                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 3016 */                     out.write("\n\t<input type=\"hidden\" name='rulename' value='");
/* 3017 */                     out.print(FormatUtil.getString("am.webclient.dwntimesmmry.text"));
/* 3018 */                     out.write("'>\n      &nbsp;&nbsp;&nbsp; <a href='javascript:callAction()' class='staticlinks'> ");
/* 3019 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliverynewaction.text"));
/* 3020 */                     out.write(" </a>\n\n         </td>\n         <td class=\"bodytext\" valign=\"middle\" align=left>\n        </td>\n\t</tr>\n    <tr>\n    <td class=\"bodytext\" valign=\"middle\" colspan=\"3\" align=left>\n      <div id='takeaction' style=\"display:none;\">\n       <table width='100%' cellpadding=\"3\" cellspacing=\"0\"  border=\"0\">\n       \t\t<tr>\n            \t<td class='bodytext label-align'  width='25%' nowrap></td>\n                <td class=\"align-left\" width='75%'>");
/* 3021 */                     out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliveryemailid.text"));
/* 3022 */                     out.write(32);
/* 3023 */                     if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                       return;
/* 3025 */                     out.write(" <input name=\"save\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"javascript:getAction();\" value=\"");
/* 3026 */                     out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 3027 */                     out.write("\"> <input name=\"cancel\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 3028 */                     out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 3029 */                     out.write("\" onclick='javascript:removeAction()'></td>\n             </tr>\n         </table>\n         </div>\n       </td>\n\t</tr>\n    <tr>\n     \t<td class=\"tablebottom itadmin-no-decor\" width=\"25%\"></td>\n      \t<td class=\"tablebottom itadmin-no-decor\" colspan='2'>\n\n           <input type=\"hidden\" name='method' value='enableCustomReports'>\n           <input type=\"hidden\" name='resourceid' value='");
/* 3030 */                     out.print(resourceId);
/* 3031 */                     out.write("'>\n           <input type=\"hidden\" name='attributeid' value='");
/* 3032 */                     out.print(request.getParameter("attributeid"));
/* 3033 */                     out.write("'>\n           <input type=\"hidden\" name='PRINTER_FRIENDLY' value='");
/* 3034 */                     out.print(isPrint);
/* 3035 */                     out.write("'>\n           <input type=\"hidden\" name='popup' value='");
/* 3036 */                     out.print(request.getParameter("popup"));
/* 3037 */                     out.write("'>\n           <input type=\"hidden\" name='selectedType' value='");
/* 3038 */                     out.print(request.getParameter("selectedType"));
/* 3039 */                     out.write("'>\n          <input name=\"Submit\" value=\"");
/* 3040 */                     out.print(FormatUtil.getString("am.webclient.admintab.opmanager.save"));
/* 3041 */                     out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"fnForm2Submit();\">\n        ");
/*      */                     
/* 3043 */                     ChooseTag _jspx_th_c_005fchoose_005f26 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3044 */                     _jspx_th_c_005fchoose_005f26.setPageContext(_jspx_page_context);
/* 3045 */                     _jspx_th_c_005fchoose_005f26.setParent(_jspx_th_html_005fform_005f1);
/* 3046 */                     int _jspx_eval_c_005fchoose_005f26 = _jspx_th_c_005fchoose_005f26.doStartTag();
/* 3047 */                     if (_jspx_eval_c_005fchoose_005f26 != 0) {
/*      */                       for (;;) {
/* 3049 */                         out.write("\n        ");
/*      */                         
/* 3051 */                         WhenTag _jspx_th_c_005fwhen_005f26 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3052 */                         _jspx_th_c_005fwhen_005f26.setPageContext(_jspx_page_context);
/* 3053 */                         _jspx_th_c_005fwhen_005f26.setParent(_jspx_th_c_005fchoose_005f26);
/*      */                         
/* 3055 */                         _jspx_th_c_005fwhen_005f26.setTest("${empty param.popup || param.popup == false}");
/* 3056 */                         int _jspx_eval_c_005fwhen_005f26 = _jspx_th_c_005fwhen_005f26.doStartTag();
/* 3057 */                         if (_jspx_eval_c_005fwhen_005f26 != 0) {
/*      */                           for (;;) {
/* 3059 */                             out.write("\n        \t&nbsp;<input type=\"button\" name=\"Submit3\" value=\"");
/* 3060 */                             out.print(FormatUtil.getString("am.webclient.admintab.opmanager.cancel"));
/* 3061 */                             out.write("\" onClick=\"history.back();\" class=\"buttons btn_link\">\n        ");
/* 3062 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f26.doAfterBody();
/* 3063 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3067 */                         if (_jspx_th_c_005fwhen_005f26.doEndTag() == 5) {
/* 3068 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f26); return;
/*      */                         }
/*      */                         
/* 3071 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f26);
/* 3072 */                         out.write("\n        ");
/*      */                         
/* 3074 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f26 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3075 */                         _jspx_th_c_005fotherwise_005f26.setPageContext(_jspx_page_context);
/* 3076 */                         _jspx_th_c_005fotherwise_005f26.setParent(_jspx_th_c_005fchoose_005f26);
/* 3077 */                         int _jspx_eval_c_005fotherwise_005f26 = _jspx_th_c_005fotherwise_005f26.doStartTag();
/* 3078 */                         if (_jspx_eval_c_005fotherwise_005f26 != 0) {
/*      */                           for (;;) {
/* 3080 */                             out.write("\n        \t&nbsp;<input name=\"close\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 3081 */                             out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 3082 */                             out.write("\" onclick='javascript:closeWindow()'>\n        ");
/* 3083 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f26.doAfterBody();
/* 3084 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3088 */                         if (_jspx_th_c_005fotherwise_005f26.doEndTag() == 5) {
/* 3089 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f26); return;
/*      */                         }
/*      */                         
/* 3092 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f26);
/* 3093 */                         out.write("\n        ");
/* 3094 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f26.doAfterBody();
/* 3095 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3099 */                     if (_jspx_th_c_005fchoose_005f26.doEndTag() == 5) {
/* 3100 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f26); return;
/*      */                     }
/*      */                     
/* 3103 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f26);
/* 3104 */                     out.write("         \n         </td>\n\t</tr>\n\t</table>\n\t</td></tr>\n\n\t \n\t</table>\n\n</div>\n\n");
/* 3105 */                     int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 3106 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3110 */                 if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 3111 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                 }
/*      */                 
/* 3114 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 3115 */                 out.write("\n</td>\t \n <!--- Help card for monitor group starts here -->\n<td width=\"30%\" valign=\"top\">\n<div id=\"reportHelpcard\" style=\"display:none\">\n\t");
/* 3116 */                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.enableReports.helpcard.text")), request.getCharacterEncoding()), out, false);
/* 3117 */                 out.write("\t\n</div>\n<div id=\"summaryHelpcard\" style=\"display:none\">\n\t");
/* 3118 */                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.downtimeSummary.helpcard.text")), request.getCharacterEncoding()), out, false);
/* 3119 */                 out.write("\n</div>\n\n</td>\t   \n   <!--- Help card for monitor group ends here -->\n</tr>\n</table>\n\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t\n\n\n \n\n\n\n");
/* 3120 */                 int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3121 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/* 3124 */               if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3125 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/* 3128 */             if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3129 */               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */             }
/*      */             
/* 3132 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3133 */             out.write("\n\n\n    ");
/* 3134 */             if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */               return;
/* 3136 */             out.write(32);
/* 3137 */             out.write(10);
/* 3138 */             response.setContentType("text/html;charset=UTF-8");
/* 3139 */             out.write(10);
/* 3140 */             int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3141 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 3145 */         if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3146 */           this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */         }
/*      */         
/* 3149 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3150 */         out.write(10);
/* 3151 */         out.write(10);
/* 3152 */       } catch (Exception ex) { ex.printStackTrace(); }
/* 3153 */       out.write("\n</body>\n");
/*      */     } catch (Throwable t) {
/* 3155 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3156 */         out = _jspx_out;
/* 3157 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3158 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3159 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3162 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3168 */     PageContext pageContext = _jspx_page_context;
/* 3169 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3171 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3172 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3173 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3175 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3177 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3178 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3179 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3180 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3181 */       return true;
/*      */     }
/* 3183 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3184 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3189 */     PageContext pageContext = _jspx_page_context;
/* 3190 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3192 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3193 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3194 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 3196 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3197 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3198 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3200 */         out.write("\n\t\talertUser();\n\t\treturn false;\n\t");
/* 3201 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3202 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3206 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3207 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3208 */       return true;
/*      */     }
/* 3210 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3216 */     PageContext pageContext = _jspx_page_context;
/* 3217 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3219 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3220 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3221 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 3223 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3224 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3225 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3227 */         out.write("\n\t\talertUser();\n\t\treturn false;\n\t");
/* 3228 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3229 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3233 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3234 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3235 */       return true;
/*      */     }
/* 3237 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3238 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3243 */     PageContext pageContext = _jspx_page_context;
/* 3244 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3246 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3247 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3248 */     _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */     
/* 3250 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3251 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3252 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 3254 */         out.write("\n\talertUser();\n\treturn;\n");
/* 3255 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3256 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3260 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3261 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3262 */       return true;
/*      */     }
/* 3264 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3265 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3270 */     PageContext pageContext = _jspx_page_context;
/* 3271 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3273 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3274 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3275 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3277 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3279 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=3");
/* 3280 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3281 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3282 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3283 */       return true;
/*      */     }
/* 3285 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3286 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3291 */     PageContext pageContext = _jspx_page_context;
/* 3292 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3294 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3295 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3296 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3298 */     _jspx_th_c_005fout_005f1.setValue("${selectedskin}");
/*      */     
/* 3300 */     _jspx_th_c_005fout_005f1.setDefault("${initParam.defaultSkin}");
/* 3301 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3302 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3303 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3304 */       return true;
/*      */     }
/* 3306 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3307 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3312 */     PageContext pageContext = _jspx_page_context;
/* 3313 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3315 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3316 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 3317 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 3319 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("resourceTypeNamesForReports");
/* 3320 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 3321 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 3322 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3323 */       return true;
/*      */     }
/* 3325 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3326 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3331 */     PageContext pageContext = _jspx_page_context;
/* 3332 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3334 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3335 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 3336 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3338 */     _jspx_th_html_005fradio_005f0.setProperty("status");
/*      */     
/* 3340 */     _jspx_th_html_005fradio_005f0.setValue("enable");
/* 3341 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 3342 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 3343 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3344 */       return true;
/*      */     }
/* 3346 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3347 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3352 */     PageContext pageContext = _jspx_page_context;
/* 3353 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3355 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 3356 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 3357 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3359 */     _jspx_th_html_005fradio_005f1.setProperty("status");
/*      */     
/* 3361 */     _jspx_th_html_005fradio_005f1.setValue("disable");
/* 3362 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 3363 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 3364 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3365 */       return true;
/*      */     }
/* 3367 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3368 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3373 */     PageContext pageContext = _jspx_page_context;
/* 3374 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3376 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3377 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 3378 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3380 */     _jspx_th_html_005fselect_005f2.setProperty("dailyhour");
/*      */     
/* 3382 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext msmall");
/* 3383 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 3384 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 3385 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3386 */         out = _jspx_page_context.pushBody();
/* 3387 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 3388 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3391 */         out.write(10);
/* 3392 */         out.write(9);
/* 3393 */         out.write(9);
/* 3394 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 3395 */           return true;
/* 3396 */         out.write("\n\t  ");
/* 3397 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 3398 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3401 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3402 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3405 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 3406 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 3407 */       return true;
/*      */     }
/* 3409 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f2);
/* 3410 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3415 */     PageContext pageContext = _jspx_page_context;
/* 3416 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3418 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3419 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 3420 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 3422 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("hour");
/* 3423 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 3424 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 3425 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3426 */       return true;
/*      */     }
/* 3428 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3429 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3434 */     PageContext pageContext = _jspx_page_context;
/* 3435 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3437 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 3438 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 3439 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3441 */     _jspx_th_html_005fselect_005f3.setProperty("dailyminute");
/*      */     
/* 3443 */     _jspx_th_html_005fselect_005f3.setStyleClass("formtext msmall");
/* 3444 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 3445 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 3446 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3447 */         out = _jspx_page_context.pushBody();
/* 3448 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 3449 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3452 */         out.write(10);
/* 3453 */         out.write(9);
/* 3454 */         out.write(9);
/* 3455 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 3456 */           return true;
/* 3457 */         out.write("\n\t  ");
/* 3458 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 3459 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3462 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3463 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3466 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 3467 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 3468 */       return true;
/*      */     }
/* 3470 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 3471 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3476 */     PageContext pageContext = _jspx_page_context;
/* 3477 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3479 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3480 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 3481 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 3483 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("minute");
/* 3484 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 3485 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 3486 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3487 */       return true;
/*      */     }
/* 3489 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3490 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3495 */     PageContext pageContext = _jspx_page_context;
/* 3496 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3498 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3499 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 3500 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 3502 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("applications");
/* 3503 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 3504 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 3505 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 3506 */       return true;
/*      */     }
/* 3508 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 3509 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3514 */     PageContext pageContext = _jspx_page_context;
/* 3515 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3517 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 3518 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3519 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3521 */     _jspx_th_html_005ftext_005f0.setProperty("priority");
/*      */     
/* 3523 */     _jspx_th_html_005ftext_005f0.setSize("20");
/*      */     
/* 3525 */     _jspx_th_html_005ftext_005f0.setStyleClass("normal");
/*      */     
/* 3527 */     _jspx_th_html_005ftext_005f0.setMaxlength("50");
/* 3528 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3529 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3530 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3531 */       return true;
/*      */     }
/* 3533 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3534 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3539 */     PageContext pageContext = _jspx_page_context;
/* 3540 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3542 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3543 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3544 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3546 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3548 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3549 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3550 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3551 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3552 */       return true;
/*      */     }
/* 3554 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3555 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\EnableCustomReports_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */