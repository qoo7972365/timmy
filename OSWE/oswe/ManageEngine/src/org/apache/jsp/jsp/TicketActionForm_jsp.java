/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MessagesTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class TicketActionForm_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   34 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   40 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(4);
/*   41 */   static { _jspx_dependants.put("/jsp/includes/NewActions.jspf", Long.valueOf(1473429417000L));
/*   42 */     _jspx_dependants.put("/jsp/includes/ServiceNowTicketForm.jspf", Long.valueOf(1473429417000L));
/*   43 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*   44 */     _jspx_dependants.put("/jsp/includes/CreateApplicationWizard.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fsize_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyle_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   81 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   85 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   86 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   87 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   88 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   89 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   90 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   91 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   92 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   93 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   94 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   95 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   96 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   97 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   98 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   99 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  100 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  101 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  102 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  103 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fsize_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  104 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  105 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  106 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  107 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  108 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  109 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  110 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyle_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  111 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  112 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  113 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  114 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  115 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*  119 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  120 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  121 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  122 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  123 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  124 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  125 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*  126 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  127 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  128 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/*  129 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/*  130 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*  131 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/*  132 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  133 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  134 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  135 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/*  136 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/*  137 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fsize_005fproperty_005fonchange.release();
/*  138 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  139 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.release();
/*  140 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.release();
/*  141 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.release();
/*  142 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.release();
/*  143 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.release();
/*  144 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyle_005frows_005fproperty_005fcols_005fnobody.release();
/*  145 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/*  146 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.release();
/*  147 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*  154 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  157 */     JspWriter out = null;
/*  158 */     Object page = this;
/*  159 */     JspWriter _jspx_out = null;
/*  160 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  164 */       response.setContentType("text/html;charset=UTF-8");
/*  165 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  167 */       _jspx_page_context = pageContext;
/*  168 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  169 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  170 */       session = pageContext.getSession();
/*  171 */       out = pageContext.getOut();
/*  172 */       _jspx_out = out;
/*      */       
/*  174 */       out.write(10);
/*      */       
/*  176 */       request.setAttribute("HelpKey", "ServiceDesk Settings");
/*      */       
/*  178 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       try
/*      */       {
/*  181 */         String error = (String)request.getAttribute("errorMsg");
/*  182 */         com.adventnet.appmanager.struts.form.AMActionForm fmm = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/*  183 */         String product = fmm.getHelpDeskProduct();
/*  184 */         if ((product != null) && (product.equalsIgnoreCase("ServiceNow")))
/*      */         {
/*  186 */           request.setAttribute("product", "ServiceNow");
/*      */         }
/*      */         
/*  189 */         String ticketType = fmm.getTicketingType();
/*      */         
/*  191 */         String customHeaders = (String)request.getAttribute("customSDPfields");
/*      */         
/*  193 */         String chooseAValStr = fmm.getTicketingType().equalsIgnoreCase("restapi") ? "" : FormatUtil.getString("am.webclient.admintab.servicedesk.choosevalue");
/*  194 */         String sdcategory = chooseAValStr;
/*  195 */         String sdscategory = chooseAValStr;
/*  196 */         String siteInfo = chooseAValStr;
/*      */         
/*      */ 
/*  199 */         if ((request.getAttribute("subCat") != null) && (!request.getAttribute("subCat").equals("-1")))
/*      */         {
/*  201 */           sdcategory = (String)request.getAttribute("subCat");
/*      */         }
/*  203 */         if ((request.getAttribute("catItem") != null) && (!request.getAttribute("catItem").equals("-1")))
/*      */         {
/*  205 */           sdscategory = (String)request.getAttribute("catItem");
/*      */         }
/*  207 */         if ((request.getAttribute("techni") == null) || (request.getAttribute("techni").equals("-1")))
/*      */         {
/*  209 */           request.setAttribute("techni", "Choose a Value");
/*      */         }
/*  211 */         if ((request.getAttribute("site") != null) && (!request.getAttribute("site").equals("")))
/*      */         {
/*  213 */           siteInfo = (String)request.getAttribute("site");
/*      */         }
/*  215 */         request.setAttribute("isIT360MSP", Boolean.valueOf(EnterpriseUtil.isIt360MSPEdition()));
/*  216 */         request.setAttribute("isIt360", Boolean.valueOf(Constants.isIt360));
/*  217 */         request.setAttribute("isRestApiEnabled", Boolean.valueOf(com.adventnet.appmanager.server.framework.extprod.SDPIntegConfig.getInstance().isRestApiEnabled()));
/*      */         
/*  219 */         out.write("\n\n<link href=\"/images/");
/*  220 */         if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */           return;
/*  222 */         out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  223 */         out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  224 */         out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\nvar product = '");
/*  225 */         out.print(product);
/*  226 */         out.write("';\ndocument.body.onload = function() {\n\tif(product!='SERVICENOW')\n\t{\n\t\tshoweditForm_CustomHideForReqTemple();\n\t}\n};\nvar chooseAValue= '");
/*  227 */         out.print(chooseAValStr);
/*  228 */         out.write("';\nfunction getTemplateInformation()\n{\n\tURL=\"/adminAction.do?method=getRequestTemplateDetails&reqTemplateId=\"+document.AMActionForm.reqTemplate.value;//No I18N\n\t");
/*  229 */         if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */           return;
/*  231 */         out.write("\n\thttp1=getHTTPObject();\n\thttp1.onreadystatechange = jsonReqTemplateInfo;\n\thttp1.open(\"GET\", URL, true);\n\thttp1.send(null);\n}\n\nfunction jsonReqTemplateInfo()\n{\n\ttry\n\t{\n\t\tif(http1.readyState == 4 && http1.status == 200)\n\t\t{\n\t\t\tvar s= eval('(' + http1.responseText+ ')');\n\t\t\tdocument.AMActionForm.subject.value=s.Subject;\n\t\t\tdocument.AMActionForm.message.value=s.Description;\n\t\t\tdocument.AMActionForm.ignoreAdditionalFields.value=s.hideFields\n\t\t\tif(s.hideFields=='true')\n\t\t\t{\n\t\t\t\tdocument.getElementById(\"noTemplateDiv\").style.display='none';\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tdocument.getElementById(\"noTemplateDiv\").style.display='block\t';\n\t\t\t}\n \t\t}\n\t}\n\tcatch(e)\n\t{}\n}\nfunction showeditForm_CustomHideForReqTemple(){\n\t\t");
/*  232 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*      */           return;
/*  234 */         out.write("\n}\nfunction getSCategories()\n{\n\tURL=\"/adminAction.do?method=getsubCategories&category=\"+encodeURIComponent(document.AMActionForm.category.value);//No I18N\n\t");
/*  235 */         if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */           return;
/*  237 */         out.write("\n\thttp1=getHTTPObject();\n\thttp1.onreadystatechange = jsonSCategories;\n\thttp1.open(\"GET\", URL, true);\n\thttp1.send(null);\n}\n\nfunction jsonSCategories()\n{\n\ttry\n\t{\n\t\tif(http1.readyState == 4 && http1.status == 200)\n\t\t{\n\t\t\tdocument.getElementById(\"subcategory\").innerHTML=getFormattedData(http1.responseText,'scategory','getMyItems()');//No I18N\n\t\t\tdocument.getElementById(\"subcategory\").style.display='block';\n \t\t}\n\t}\n\tcatch(e)\n\t{}\n\n}\nfunction getMyItems()\n{\n\t\n\tURL=\"/adminAction.do?method=getItems&category=\"+encodeURIComponent(document.AMActionForm.category.value)+\"&subcategory=\"+encodeURIComponent(document.AMActionForm.subCategory.value);//No I18N\n\t");
/*  238 */         if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*      */           return;
/*  240 */         out.write("\n\thttp1=getHTTPObject();\n\thttp1.onreadystatechange = jsonItems\n\thttp1.open(\"GET\", URL, true);\n\thttp1.send(null);\n}\n\nfunction jsonItems()\n{\n\ttry\n\t{\n\t\tif(http1.readyState == 4 && http1.status == 200)\n\t\t{\n\t\t\tdocument.getElementById(\"item\").innerHTML=getFormattedData(http1.responseText,'item','')\n\t\t\tdocument.getElementById(\"item\").style.display='block';\n \t\t}\n\t}\n\tcatch(e)\n\t{}\n\n}\n\nfunction getTechnicians()\n{\n\t\tURL=\"/adminAction.do?method=getTechnicians&group=\"+document.AMActionForm.group.value;\n\t\t");
/*  241 */         if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*      */           return;
/*  243 */         out.write("\n\t\thttp1=getHTTPObject();\n\t\thttp1.onreadystatechange = jsonTechnicians;\n\t\thttp1.open(\"GET\", URL, true);\n\t\thttp1.send(null);\n}\n\nfunction jsonTechnicians()\n{\n\ttry\n\t{\n\t\tif(http1.readyState == 4 && http1.status == 200)\n\t\t{\n\t\t\tdocument.getElementById(\"technician\").innerHTML=getFormattedData(http1.responseText,'techns','getTechniciansValue()'); //NO I18N\n\t\t\tdocument.getElementById(\"technician\").style.display='block';\n \t\t}\n\t}\n\tcatch(e)\n\t{}\n\n}\n\nfunction getTechniciansValue()\n{\n\tdocument.AMActionForm.technician.value=document.AMActionForm.techns.value\n}\n\nfunction getCategNames()\n{\n\t\tURL=\"/adminAction.do?method=getCategNames&accName=\"+encodeURIComponent(document.AMActionForm.accountName.value); //NO I18N\n\t\t");
/*  244 */         if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
/*      */           return;
/*  246 */         out.write("\n\t\thttp2=getHTTPObject();\n\t\thttp2.onreadystatechange = jsonCateg;\n\t\thttp2.open(\"GET\", URL, true);\n\t\thttp2.send(null);\n}\nfunction jsonCateg()\n{\n\ttry\n\t{\n\t\tif(http2.readyState == 4 && http2.status == 200)\n\t\t{\n\t\t\tdocument.getElementById(\"category\").innerHTML=getFormattedData(http2.responseText,'category','getScategory()');// NO I18N\n\t\t\tdocument.getElementById(\"category\").style.display='block';\n \t\t}\n\t}\n\tcatch(e)\n\t{}\n\n}\n\nfunction getGroupNames()\n{\n\t\tURL=\"/adminAction.do?method=getTechgroupNames&accName=\"+encodeURIComponent(document.AMActionForm.accountName.value)+\"&siteName=\"+encodeURIComponent(document.AMActionForm.site.value); //NO I18N\n\t\t");
/*  247 */         if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*      */           return;
/*  249 */         out.write("\n\t\thttp1=getHTTPObject();\n\t\thttp1.onreadystatechange = jsonGroups;\n\t\thttp1.open(\"GET\", URL, true);\n\t\thttp1.send(null);\n}\nfunction jsonGroups()\n{\n\ttry\n\t{\n\t\tif(http1.readyState == 4 && http1.status == 200)\n\t\t{\n\t\t\tdocument.getElementById(\"group\").innerHTML=getFormattedData(http1.responseText,'group','getTechnicians()');// NO I18N\n\t\t\tdocument.getElementById(\"group\").style.display='block';\n\t\t}\n\t}\n\tcatch(e)\n\t{}\n\n}\n\n\n\nfunction getSiteNames()\n{\n\t\tURL=\"/adminAction.do?method=getSiteNames&accName=\"+encodeURIComponent(document.AMActionForm.accountName.value); //NO I18N\n\t\t");
/*  250 */         if (_jspx_meth_c_005fif_005f6(_jspx_page_context))
/*      */           return;
/*  252 */         out.write("\n\t\thttp1=getHTTPObject();\n\t\thttp1.onreadystatechange = jsonSites;\n\t\thttp1.open(\"GET\", URL, true);\n\t\thttp1.send(null);\n}\nfunction jsonSites()\n{\n\ttry\n\t{\n\t\tif(http1.readyState == 4 && http1.status == 200)\n\t\t{\n\t\t\tdocument.getElementById(\"site\").innerHTML=getFormattedData(http1.responseText,'sitename','getGroupNames()');// NO I18N\n\t\t\tdocument.getElementById(\"site\").style.display='block';\n \t\t}\n\t}\n\tcatch(e)\n\t{}\n\n}\n\n\n\n\nfunction getRequesterNames()\n{\n\n\tif(document.AMActionForm.accountName.value != 'Choose a Value' && document.AMActionForm.site.value != 'Choose a Value')\n\t{\n\t\tURL=\"/adminAction.do?method=getRequesterNames&accName=\"+document.AMActionForm.accountName.value+\"&stName=\"+document.AMActionForm.sitename.value;\n\t\t");
/*  253 */         if (_jspx_meth_c_005fif_005f7(_jspx_page_context))
/*      */           return;
/*  255 */         out.write("\n\t\thttp1=getHTTPObject();\n\t\thttp1.onreadystatechange = jsonRequesterNames;\n\t\thttp1.open(\"GET\", URL, true);\n\t\thttp1.send(null);\n\t}\n\telse\n\t{\n\t\tdocument.getElementById(\"requesters\").style.display='none';\n\t\tdocument.getElementById(\"dummyReqs\").style.display='block';\n\t}\n\t\n}\n\nfunction jsonRequesterNames()\n{\n\ttry\n\t{\n\t\tif(http1.readyState == 4 && http1.status == 200)\n\t\t{\n\t\t\tdocument.getElementById(\"requesters\").innerHTML=getFormattedData(http1.responseText,'reqname','getRequestersValue()')\n\t\t\tdocument.getElementById(\"dummyReqs\").style.display='none';\n\t\t\tdocument.getElementById(\"requesters\").style.display='block';\n \t\t}\n\t}\n\tcatch(e)\n\t{}\n\n}\n\nfunction getRequestersValue()\n{\n\tdocument.AMActionForm.reqName.value = document.AMActionForm.reqname.value;\n}\n\n\nfunction fnFormSubmit()\n{\n    if(trimAll(document.AMActionForm.displayname.value)=='')\n\t{\n\t\talert('");
/*  256 */         out.print(FormatUtil.getString("am.webclient.newaction.alertemptylogaticketname"));
/*  257 */         out.write("');\n\t\tdocument.AMActionForm.displayname.focus();\n\t\treturn false;\n\t}\n    if(isBlackListedCharactersPresent(document.AMActionForm.displayname.value,'");
/*  258 */         out.print(FormatUtil.getString("am.webclient.specialchar.alert.displayname"));
/*  259 */         out.write("')) {\n\treturn false;\n    }\t\n\t");
/*      */         
/*  261 */         IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  262 */         _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/*  263 */         _jspx_th_c_005fif_005f8.setParent(null);
/*      */         
/*  265 */         _jspx_th_c_005fif_005f8.setTest("${AMActionForm.mspDesk eq true || isIT360MSP eq true}");
/*  266 */         int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/*  267 */         if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */           for (;;) {
/*  269 */             out.write("\n\t   \tif(trimAll(document.AMActionForm.accountName.value)=='' || trimAll(document.AMActionForm.accountName.value) == 'Choose a Value')\n\t\t{\n\t\t\talert('");
/*  270 */             out.print(FormatUtil.getString("am.webclient.newaction.alertEmptylogaticketAccountName"));
/*  271 */             out.write("');\n\t\t         \treturn false;\n\t\t}\n\t\tif(trimAll(document.AMActionForm.site.value)=='' || trimAll(document.AMActionForm.site.value) == 'Choose a Value')\n\t\t{\n\t\t\talert('");
/*  272 */             out.print(FormatUtil.getString("am.webclient.newaction.alertEmptylogaticketSiteName"));
/*  273 */             out.write("');\n\t\t\treturn false;\n\t\t}\n\t\tif(trimAll(document.AMActionForm.reqName.value)=='' || trimAll(document.AMActionForm.reqName.value) == 'Choose a Value')\n\t\t{\n\t\t\talert('");
/*  274 */             out.print(FormatUtil.getString("am.webclient.newaction.alertEmptylogaticketRequesterName"));
/*  275 */             out.write("');\n\t\t\tdocument.AMActionForm.reqName.focus();\n\t\t\treturn false;\n\t\t}\n\t");
/*  276 */             int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/*  277 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  281 */         if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/*  282 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */         }
/*      */         
/*  285 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/*  286 */         out.write("\n\t\n\tif($('.customHeadersCSS').is(':checked'))\n\t{\n\t\tvar empties = $(\"input:text[name='headerInput']\").filter(function () {\n\t\t    return $.trim($(this).val()) == '';\n\t\t});\n\t\tif (empties.length) \n\t\t{\n\t\t\talert(\"Custom Fields name/value cannot be empty\"); // NO I18N \n\t\t\treturn false;\n\t\t}\n\t\tvar temp = \"\";\n\n\t\ttemp = $.map($(\"input:text[name='headerInput']\"), function(customHeaders, i) // NO I18N\n\t\t{\n\t\t\tif(i==0)\n\t\t\t{\n\t\t\t\treturn customHeaders.value;\n\t\t\t}\n\t\t\telse if(i%2 ==0)\n\t\t\t{\n\t\t\t\treturn \"#\"+customHeaders.value;\t\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\treturn \"_sep_\"+customHeaders.value; // NO I18N\n\t\t\t}\n\t\t\t\n\t\t}).join('');\n\t\t$(\"#customHeadersId\").val(temp);\n\t}\n\telse\n\t{\n\t\t$(\"#customHeadersId\").val(\"\");\t\n\t}\n\tdocument.AMActionForm.submit();\n}\n\nfunction addTitle(x)\n{\n if(x=='0')\n {\n\tvar titleAlarm=document.AMActionForm.titleList.selectedIndex;\n\tif(titleAlarm!=0)\n\t {\n       \t\tdocument.AMActionForm.subject.value=document.AMActionForm.subject.value+\"  \"+document.AMActionForm.titleList.value;\n\t } \n }\n else\n {\n   var descAlarm=document.AMActionForm.descList.selectedIndex;\n");
/*  287 */         out.write("   if(descAlarm!=0)\n   {\n     document.AMActionForm.message.value=document.AMActionForm.message.value+\"\\n\"+document.AMActionForm.descList.value; \n   }\n }\n}\n\nfunction getFormattedData(data,id,method)\n{\n\tvar dataArray = data.split(\",\");\n\t// Comma in fields like technician, category names is causing it to split to separate field. \n\t// So we are using _APM_COMMA_DELIMITER_ text as delimiter for comman and replacing it after splitting. #3126493\n\tif(data.indexOf(\"_APM_COMMA_DELIMITER_\") != -1){\n\t\t$.each(dataArray, function (key, val) {\n\t\t\tdataArray[key] = val.replace('_APM_COMMA_DELIMITER_', ',');\n\t\t})\n\t}\n\tvar formattedData = \" \";\n\tformattedData = \"<select name='\"+id+\"' onChange='\"+method+\"'>");
/*      */         
/*  289 */         IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  290 */         _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/*  291 */         _jspx_th_c_005fif_005f9.setParent(null);
/*      */         
/*  293 */         _jspx_th_c_005fif_005f9.setTest("${AMActionForm.ticketingType ne 'restapi'}");
/*  294 */         int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/*  295 */         if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */           for (;;) {
/*  297 */             out.write("<option value='Choose a Value'> ");
/*  298 */             out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.choosevalue"));
/*  299 */             out.write(" </option>");
/*  300 */             int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/*  301 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  305 */         if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/*  306 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */         }
/*      */         
/*  309 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/*  310 */         out.write("\"//No I18N\n\tif(dataArray.length > 0)\n\t{\n\t\tfor(var i=0;i<dataArray.length;i++)\n\t\t{\n\t\t\tvar label=dataArray[i];\n\t\t\t");
/*  311 */         if (_jspx_meth_c_005fif_005f10(_jspx_page_context))
/*      */           return;
/*  313 */         out.write("\n\t\t\tif(trimAll(dataArray[i])!='')\n\t\t\t{\n\t\t\t\tformattedData+=\"<option value='\"+dataArray[i]+\"'>\"+label+\"</option>\"\n\t\t\t}\n\t\t}\n\t}\n\tformattedData += \"</select>\"\n\treturn formattedData;\n}\n\n\tfunction changediv(fromWhere)\n    {\n    \tif(fromWhere != '");
/*  314 */         if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */           return;
/*  316 */         out.write("')\n    \t{\n    \t\tif(fromWhere==\"credential\")\n\t    \t{\n    \t\t\tlocation.href= \"adminAction.do?method=showSdeskLogTicket&ticketingType=credential\";\n\t    \t}\n\t    \telse\n\t    \t{\n\t    \t\tlocation.href= \"adminAction.do?method=showSdeskLogTicket\";\t    \t\t\n\t    \t}\n    \t}\n    }\n\tfunction changeProduct(productName)\n    {\n    \tif(productName==\"ServiceDesk\")\n\t    {\n    \t\tlocation.href= \"/adminAction.do?method=showSdeskLogTicket\";\n\t    }\n\t    else\n\t    {\n\t    \tlocation.href= \"/adminAction.do?method=showServiceNowLogTicket\";\t    \t\t\n\t    }\n    }\n\t\n</script>\n\n<body onLoad=\"javascript:myOnLoad()\">\n");
/*      */         
/*  318 */         FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  319 */         _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  320 */         _jspx_th_html_005fform_005f0.setParent(null);
/*      */         
/*  322 */         _jspx_th_html_005fform_005f0.setAction("/adminAction.do");
/*      */         
/*  324 */         _jspx_th_html_005fform_005f0.setStyle("display:inline");
/*  325 */         int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  326 */         if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */           for (;;) {
/*  328 */             out.write(10);
/*  329 */             out.write("<!--$Id$-->\n              \n\n\n\n\n<script>                      \nfunction fnFormSubmit1(object)\n{\n\tlocation.href=object;\n}\n\nfunction showMailServerSettings()\n {\n         var showMail = false;\n         var query = window.location.search;\n         var pairs = query.split(\"&\");\n         \n         for (var i=0;i<pairs.length;i++)\n         {\n                 var pos = pairs[i].indexOf('=');\n                 if (pos >= 0)\n                 {\n                         var argname = pairs[i].substring(0,pos);\n                         var value = pairs[i].substring(pos+1);\n                         if(argname == \"isFromApi\")\n                         {\n                                 showMail = true;\n                         }\n                         //keys[keys.length] = argname;\n                         //values[values.length] = value;\n                 }\n         }\n         //alert(showMail);\n         return  showMail;\n }\n\nfunction doInitStuffOnBodyLoad()\n{\n        ");
/*      */             
/*  331 */             if ((pageContext.getAttribute("jsppage") != null) && (pageContext.getAttribute("jsppage").equals("programaction")))
/*      */             {
/*      */ 
/*  334 */               out.write("\n        myOnLoad1();\n        ");
/*      */             }
/*      */             
/*      */ 
/*  338 */             out.write("\n        \n\tif(document.AMActionForm!=null)\n\t{\n\tif(typeof(document.AMActionForm.actions)!='undefined')\n\t  {\n\t  if((location.search!= null && (location.search).match(\"EmailAction\")!=null) || (location.path!=null && (location.path).match(\"EMailActionForm\")!=null))\n\t  {\n\t\t\n\t\t");
/*  339 */             if (_jspx_meth_c_005fif_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  341 */             out.write("\t\n\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"SMSAction\")!=null) || (location.path!=null && (location.path).match(\"SMSActionForm\")!=null) || (location.search!= null && (location.search).match(\"SMSServerConfiguration\")!=null))\n\t  {\n\t\t \n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"ExecProg\")!=null) || (location.path!=null && (location.path).match(\"ExecProgramActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"reloadSendTrapActionForm\")!=null) || (location.path!=null && (location.path).match(\"SendTrapActionForm\")!=null))\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\n\t  else if((location.search!= null && (location.search).match(\"Ticket\")!=null) || (location.path!=null && (location.path).match(\"showLogTicket\")!=null) ||  (location.search).match(\"showTicketAction\")!=null )\n");
/*  342 */             out.write("\t  {\n\t\t\t");
/*  343 */             if (_jspx_meth_c_005fif_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  345 */             out.write("\n\t\t  ");
/*      */             
/*  347 */             if ((Constants.sqlManager) || (EnterpriseUtil.isAdminServer()))
/*      */             {
/*  349 */               out.write("\n\t\t\tdocument.AMActionForm.actions.selectedIndex=4;\n\t    \t");
/*      */             }
/*      */             else
/*      */             {
/*  353 */               out.write("\n\t       \tdocument.AMActionForm.actions.selectedIndex=5;\n\t  \t\t");
/*      */             }
/*  355 */             out.write("\n\t  }\n\t  else if(location.pathname!= null && (location.pathname).match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(location.search!= null && (location.search).match(\"jre\")!=null || (location.search!=null && (location.search).match(\"ThreadDumpActions\")!=null))\n\t  {\n\t    ");
/*  356 */             if (EnterpriseUtil.isManagedServer())
/*      */             {
/*  358 */               out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=5;\n\t\t");
/*      */             }
/*      */             else
/*      */             {
/*  362 */               out.write("\n\t    document.AMActionForm.actions.selectedIndex=6;\n        ");
/*      */             }
/*  364 */             out.write("\n\t  }\t\n\t  else if(location.search!= null && (location.search).match(\"showVMAction\")!=null || (location.search!=null && (location.search).match(\"ShowVMActions\")!=null))\n\t  {\t\t \n\t    ");
/*  365 */             if (EnterpriseUtil.isManagedServer())
/*      */             {
/*  367 */               out.write("\n\t    if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t    \tdocument.AMActionForm.actions.selectedIndex=9;\n\t    }else{\n\t\t\tdocument.AMActionForm.actions.selectedIndex=7;\n\t    }\n\t\t");
/*      */             }
/*      */             else
/*      */             {
/*  371 */               out.write("\n\t\t  if(location.search!= null && (location.search).match(\"isContainerAction=true\")!=null){\n\t\t    \tdocument.AMActionForm.actions.selectedIndex=10;\n\t\t    }else{\n\t  \t  document.AMActionForm.actions.selectedIndex=8;\n\t\t    }\n        ");
/*      */             }
/*  373 */             out.write("\n\t  }\t  \n\t  else if(location.search!= null && (location.search).match(\"winServAction\")!=null || (location.search!=null && (location.search).match(\"winServAction\")!=null))\n\t  { \n\t    ");
/*  374 */             if (Constants.sqlManager) {
/*  375 */               out.write("\n\t      document.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */             }
/*  377 */             else if (EnterpriseUtil.isManagedServer()) {
/*  378 */               out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=8;\n\t\t");
/*      */             } else {
/*  380 */               out.write("\n          document.AMActionForm.actions.selectedIndex=9;\n        ");
/*      */             }
/*  382 */             out.write("\t\t\n\t  }\t  \n\t   else if((location.search!= null && (location.search).match(\"ExecQueryAction\")!=null) || (location.path!=null && (location.path).match(\"ExecQueryActionForm\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=5;\n  \t   }\n\t   else if((location.search!= null && (location.search).match(\"sqlJobAction\")!=null) || (location.path!=null && (location.path).match(\"sqlJobAction\")!=null))\n  \t   {\n  \t             document.AMActionForm.actions.selectedIndex=7;\n  \t   }\n\t   else if(location.search!= null && (location.search).match(\"amazon\")!=null)\n\t\t  {\n\t\t    ");
/*  383 */             if (EnterpriseUtil.isManagedServer()) {
/*  384 */               out.write("\n\t\tdocument.AMActionForm.actions.selectedIndex=6;\n\t\t");
/*      */             } else {
/*  386 */               out.write("\n\t\t    document.AMActionForm.actions.selectedIndex=7;\n        ");
/*      */             }
/*  388 */             out.write("\n\t\t  }\n\t  \t  \n\t  }\n\n\tif(document.AMActionForm.selectedBusinessHourID != null)\n\t{\n\t\tinit();\n\t}\n\t\n\t}\n\telse\n\t{\n\t\tif(document.MBeanOperationActionForm.actions!=undefined)\n\t\t{\n\t  \t      document.MBeanOperationActionForm.actions.selectedIndex=4;\t  \n\t  \t}\n\t}\n\n}\nfunction restvalue()\n{\n//alert(document.AMActionForm.actionslist.selectedIndex);\nvar selectedIdx=document.AMActionForm.actionslist.selectedIndex;\ndocument.AMActionForm.reset();\ndocument.AMActionForm.actionslist.selectedIndex=selectedIdx;\n}\n</script>\n");
/*      */             
/*  390 */             String action_haid = request.getParameter("haid");
/*  391 */             String returnpath = "";
/*      */             
/*  393 */             if (request.getParameter("returnpath") != null)
/*      */             {
/*  395 */               returnpath = "&returnpath=" + java.net.URLEncoder.encode(request.getParameter("returnpath"));
/*      */             }
/*      */             
/*      */ 
/*  399 */             out.write(10);
/*  400 */             out.write(10);
/*      */             
/*  402 */             SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  403 */             _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  404 */             _jspx_th_c_005fset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  406 */             _jspx_th_c_005fset_005f0.setVar("isSqlManager");
/*  407 */             int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  408 */             if (_jspx_eval_c_005fset_005f0 != 0) {
/*  409 */               if (_jspx_eval_c_005fset_005f0 != 1) {
/*  410 */                 out = _jspx_page_context.pushBody();
/*  411 */                 _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  412 */                 _jspx_th_c_005fset_005f0.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  415 */                 out.print(Constants.sqlManager);
/*  416 */                 int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  417 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  420 */               if (_jspx_eval_c_005fset_005f0 != 1) {
/*  421 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  424 */             if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  425 */               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */             }
/*      */             
/*  428 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  429 */             out.write(10);
/*      */             
/*  431 */             SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  432 */             _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  433 */             _jspx_th_c_005fset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  435 */             _jspx_th_c_005fset_005f1.setVar("isIt360");
/*  436 */             int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  437 */             if (_jspx_eval_c_005fset_005f1 != 0) {
/*  438 */               if (_jspx_eval_c_005fset_005f1 != 1) {
/*  439 */                 out = _jspx_page_context.pushBody();
/*  440 */                 _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/*  441 */                 _jspx_th_c_005fset_005f1.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  444 */                 out.print(Constants.isIt360);
/*  445 */                 int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/*  446 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  449 */               if (_jspx_eval_c_005fset_005f1 != 1) {
/*  450 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  453 */             if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  454 */               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */             }
/*      */             
/*  457 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/*  458 */             out.write(10);
/*      */             
/*  460 */             SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  461 */             _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/*  462 */             _jspx_th_c_005fset_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  464 */             _jspx_th_c_005fset_005f2.setVar("isAdminServer");
/*  465 */             int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/*  466 */             if (_jspx_eval_c_005fset_005f2 != 0) {
/*  467 */               if (_jspx_eval_c_005fset_005f2 != 1) {
/*  468 */                 out = _jspx_page_context.pushBody();
/*  469 */                 _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/*  470 */                 _jspx_th_c_005fset_005f2.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  473 */                 out.print(EnterpriseUtil.isAdminServer());
/*  474 */                 int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/*  475 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  478 */               if (_jspx_eval_c_005fset_005f2 != 1) {
/*  479 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  482 */             if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/*  483 */               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */             }
/*      */             
/*  486 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/*  487 */             out.write(10);
/*      */             
/*  489 */             SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  490 */             _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/*  491 */             _jspx_th_c_005fset_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  493 */             _jspx_th_c_005fset_005f3.setVar("isProfServer");
/*  494 */             int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/*  495 */             if (_jspx_eval_c_005fset_005f3 != 0) {
/*  496 */               if (_jspx_eval_c_005fset_005f3 != 1) {
/*  497 */                 out = _jspx_page_context.pushBody();
/*  498 */                 _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/*  499 */                 _jspx_th_c_005fset_005f3.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  502 */                 out.print(EnterpriseUtil.isProfEdition());
/*  503 */                 int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/*  504 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  507 */               if (_jspx_eval_c_005fset_005f3 != 1) {
/*  508 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  511 */             if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/*  512 */               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */             }
/*      */             
/*  515 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/*  516 */             out.write(10);
/*      */             
/*  518 */             SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  519 */             _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/*  520 */             _jspx_th_c_005fset_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/*  522 */             _jspx_th_c_005fset_005f4.setVar("isCloudServer");
/*  523 */             int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/*  524 */             if (_jspx_eval_c_005fset_005f4 != 0) {
/*  525 */               if (_jspx_eval_c_005fset_005f4 != 1) {
/*  526 */                 out = _jspx_page_context.pushBody();
/*  527 */                 _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/*  528 */                 _jspx_th_c_005fset_005f4.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  531 */                 out.print(EnterpriseUtil.isCloudEdition());
/*  532 */                 int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/*  533 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  536 */               if (_jspx_eval_c_005fset_005f4 != 1) {
/*  537 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  540 */             if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/*  541 */               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */             }
/*      */             
/*  544 */             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/*  545 */             out.write("\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"3\" cellspacing=\"3\" >\n  <tr> \n    <td align=\"left\" width=\"50%\" class=\"bodytext\">");
/*  546 */             out.print(FormatUtil.getString("am.webclient.newaction.selectactiontype"));
/*  547 */             out.write("&nbsp;\n\t<select id=\"actionslist\" name=\"actions\" onchange=\"javascript:fnFormSubmit1(this.form.actions.options[this.selectedIndex].value);\" class=\"formtext\">\n\t");
/*      */             
/*  549 */             ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  550 */             _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  551 */             _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_html_005fform_005f0);
/*  552 */             int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  553 */             if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */               for (;;) {
/*  555 */                 out.write(10);
/*  556 */                 out.write(9);
/*      */                 
/*  558 */                 WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  559 */                 _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  560 */                 _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                 
/*  562 */                 _jspx_th_c_005fwhen_005f1.setTest("${empty param.global}");
/*  563 */                 int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  564 */                 if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                   for (;;) {
/*  566 */                     out.write("\t\n\t\t<option value=\"/showTile.do?TileName=.EmailActions&haid=");
/*  567 */                     out.print(action_haid);
/*  568 */                     out.print(returnpath);
/*  569 */                     out.write(34);
/*  570 */                     out.write(62);
/*  571 */                     out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  572 */                     out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.SMSActions&haid=");
/*  573 */                     out.print(action_haid);
/*  574 */                     out.print(returnpath);
/*  575 */                     out.write(34);
/*  576 */                     out.write(62);
/*  577 */                     out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  578 */                     out.write("</option>\n\t\t<option value=\"/showTile.do?TileName=.ExecProg&haid=");
/*  579 */                     out.print(action_haid);
/*  580 */                     out.print(returnpath);
/*  581 */                     out.write(34);
/*  582 */                     out.write(62);
/*  583 */                     out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  584 */                     out.write("</option>\n\t\t<option value=\"/adminAction.do?method=reloadSendTrapActionForm&haid=");
/*  585 */                     out.print(action_haid);
/*  586 */                     out.print(returnpath);
/*  587 */                     out.write(34);
/*  588 */                     out.write(62);
/*  589 */                     out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/*  590 */                     out.write("</option>\n\t\t\n\t\t");
/*      */                     
/*  592 */                     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  593 */                     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  594 */                     _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/*  595 */                     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  596 */                     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                       for (;;) {
/*  598 */                         out.write("\n\t\t\t");
/*      */                         
/*  600 */                         WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  601 */                         _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  602 */                         _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                         
/*  604 */                         _jspx_th_c_005fwhen_005f2.setTest("${!isIt360}");
/*  605 */                         int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  606 */                         if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                           for (;;) {
/*  608 */                             out.write("\n\t\t\t\t");
/*      */                             
/*  610 */                             ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  611 */                             _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/*  612 */                             _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fwhen_005f2);
/*  613 */                             int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/*  614 */                             if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                               for (;;) {
/*  616 */                                 out.write("\n\t\t\t\t\t");
/*      */                                 
/*  618 */                                 WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  619 */                                 _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  620 */                                 _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                 
/*  622 */                                 _jspx_th_c_005fwhen_005f3.setTest("${!isSqlManager}");
/*  623 */                                 int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  624 */                                 if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                   for (;;) {
/*  626 */                                     out.write("\n\t\t\t\t\t\t<!-- MBean Operation-->\n\t\t\t\t\t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  627 */                                     out.print(action_haid);
/*  628 */                                     out.print(returnpath);
/*  629 */                                     out.write(34);
/*  630 */                                     out.write(62);
/*  631 */                                     out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  632 */                                     out.write("</option>\n\t\t\t\t\t");
/*  633 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  634 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  638 */                                 if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  639 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                 }
/*      */                                 
/*  642 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  643 */                                 out.write("\n\t\t\t\t\t");
/*      */                                 
/*  645 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  646 */                                 _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  647 */                                 _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f3);
/*  648 */                                 int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  649 */                                 if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                   for (;;) {
/*  651 */                                     out.write("\n\t\t\t\t\t\t<!-- Execute Query Action-->\n\t   \t\t\t\t\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  652 */                                     out.print(action_haid);
/*  653 */                                     out.print(returnpath);
/*  654 */                                     out.write(34);
/*  655 */                                     out.write(62);
/*  656 */                                     out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/*  657 */                                     out.write("</option>\n\t   \t\t\t\t\t<!-- Sql job action -->\n\t\t\t\t\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/*  658 */                                     out.print(action_haid);
/*  659 */                                     out.print(returnpath);
/*  660 */                                     out.write(34);
/*  661 */                                     out.write(62);
/*  662 */                                     out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/*  663 */                                     out.write("</option>\n\t\t\t\t\t");
/*  664 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  665 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  669 */                                 if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  670 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                 }
/*      */                                 
/*  673 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  674 */                                 out.write("\n\t\t\t\t");
/*  675 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/*  676 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  680 */                             if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/*  681 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                             }
/*      */                             
/*  684 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*  685 */                             out.write("\n\t\t\t\t\t<!-- Log Ticket Operation-->\n\t\t\t\t\t");
/*  686 */                             if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  687 */                               out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  688 */                               out.print(action_haid);
/*  689 */                               out.print(returnpath);
/*  690 */                               out.write(34);
/*  691 */                               out.write(62);
/*  692 */                               out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  693 */                               out.write("</option> ");
/*      */                             }
/*  695 */                             out.write("\n\t\t\t");
/*  696 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  697 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  701 */                         if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  702 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                         }
/*      */                         
/*  705 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  706 */                         out.write("\n\t\t\t");
/*      */                         
/*  708 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  709 */                         _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  710 */                         _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  711 */                         int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  712 */                         if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                           for (;;) {
/*  714 */                             out.write("\n\t\t   \t\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/*  715 */                             out.print(action_haid);
/*  716 */                             out.print(returnpath);
/*  717 */                             out.write(34);
/*  718 */                             out.write(62);
/*  719 */                             out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  720 */                             out.write("</option>\n\t\t   \t\t<!-- Log Ticket Operation-->\n\t\t   \t\t");
/*      */                             
/*  722 */                             IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  723 */                             _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/*  724 */                             _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                             
/*  726 */                             _jspx_th_c_005fif_005f13.setTest("${isProfServer || isAdminServer}");
/*  727 */                             int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/*  728 */                             if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                               for (;;) {
/*  730 */                                 out.write("\n\t\t\t\t\t");
/*  731 */                                 if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  732 */                                   out.write("<option value=\"/adminAction.do?method=showLogTicket&haid=");
/*  733 */                                   out.print(action_haid);
/*  734 */                                   out.print(returnpath);
/*  735 */                                   out.write(34);
/*  736 */                                   out.write(62);
/*  737 */                                   out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  738 */                                   out.write("</option> ");
/*      */                                 }
/*  740 */                                 out.write("\n\t\t   \t\t");
/*  741 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/*  742 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  746 */                             if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/*  747 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                             }
/*      */                             
/*  750 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/*  751 */                             out.write("\n\t\t\t");
/*  752 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  753 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  757 */                         if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  758 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                         }
/*      */                         
/*  761 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  762 */                         out.write(10);
/*  763 */                         out.write(9);
/*  764 */                         out.write(9);
/*  765 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  766 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  770 */                     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  771 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                     }
/*      */                     
/*  774 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  775 */                     out.write(10);
/*  776 */                     out.write(9);
/*  777 */                     out.write(9);
/*      */                     
/*  779 */                     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  780 */                     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/*  781 */                     _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                     
/*  783 */                     _jspx_th_c_005fif_005f14.setTest("${!isAdminServer}");
/*  784 */                     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/*  785 */                     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                       for (;;) {
/*  787 */                         out.write("\n\t\t\t<!--JRE Action -->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  788 */                         out.print(action_haid);
/*  789 */                         out.print(returnpath);
/*  790 */                         out.write(34);
/*  791 */                         out.write(62);
/*  792 */                         out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  793 */                         out.write("</option>\n\t\t\t<!--Amazon Instance Action-->\n\t\t\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  794 */                         out.print(action_haid);
/*  795 */                         out.print(returnpath);
/*  796 */                         out.write(34);
/*  797 */                         out.write(62);
/*  798 */                         out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  799 */                         out.write("</option>\n\t\t\t<!--VM Action -->\n\t      \t<option value=\"/adminAction.do?method=showVMAction&haid=");
/*  800 */                         out.print(action_haid);
/*  801 */                         out.print(returnpath);
/*  802 */                         out.write(34);
/*  803 */                         out.write(62);
/*  804 */                         out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  805 */                         out.write("</option>\n\t      \t\n\t\t\t<!-- Windows Service action -->\n\t\t\t");
/*      */                         
/*  807 */                         IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  808 */                         _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/*  809 */                         _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f14);
/*      */                         
/*  811 */                         _jspx_th_c_005fif_005f15.setTest("${!isCloudServer}");
/*  812 */                         int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/*  813 */                         if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                           for (;;) {
/*  815 */                             out.write("\n\t   \t\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/*  816 */                             out.print(action_haid);
/*  817 */                             out.print(returnpath);
/*  818 */                             out.write(34);
/*  819 */                             out.write(62);
/*  820 */                             out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  821 */                             out.write("</option>\n\t   \t\t");
/*  822 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/*  823 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  827 */                         if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/*  828 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                         }
/*      */                         
/*  831 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/*  832 */                         out.write("\n\t   \t\t<option value=\"/adminAction.do?method=showVMAction&isContainerAction=true&haid=");
/*  833 */                         out.print(action_haid);
/*  834 */                         out.print(returnpath);
/*  835 */                         out.write(34);
/*  836 */                         out.write(62);
/*  837 */                         out.print(FormatUtil.getString("am.container.action.createnew"));
/*  838 */                         out.write("</option>\n   \t\t");
/*  839 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/*  840 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  844 */                     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/*  845 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                     }
/*      */                     
/*  848 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/*  849 */                     out.write(10);
/*  850 */                     out.write(9);
/*  851 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  852 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  856 */                 if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  857 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                 }
/*      */                 
/*  860 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  861 */                 out.write(10);
/*  862 */                 out.write(9);
/*      */                 
/*  864 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  865 */                 _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  866 */                 _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f1);
/*  867 */                 int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  868 */                 if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                   for (;;) {
/*  870 */                     out.write(10);
/*      */                     
/*  872 */                     String redirectTo = null;
/*  873 */                     if (request.getParameter("redirectto") != null)
/*      */                     {
/*  875 */                       redirectTo = java.net.URLEncoder.encode(request.getParameter("redirectto"));
/*      */                     }
/*      */                     else
/*      */                     {
/*  879 */                       redirectTo = java.net.URLEncoder.encode("/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true");
/*      */                     }
/*      */                     
/*  882 */                     out.write("\t\n\t<option value=\"/showTile.do?TileName=.EmailActions&PRINTER_FRIENDLY=true&haid=");
/*  883 */                     out.print(action_haid);
/*  884 */                     out.write("&global=true");
/*  885 */                     out.print(returnpath);
/*  886 */                     out.write(34);
/*  887 */                     out.write(62);
/*  888 */                     out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  889 */                     out.write("</option>\n\t<option value=\"/showTile.do?TileName=.SMSActions&PRINTER_FRIENDLY=true&haid=");
/*  890 */                     out.print(action_haid);
/*  891 */                     out.write("&global=true");
/*  892 */                     out.print(returnpath);
/*  893 */                     out.write(34);
/*  894 */                     out.write(62);
/*  895 */                     out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  896 */                     out.write("</option>\n\t");
/*      */                     
/*  898 */                     org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f0 = (org.apache.struts.taglib.logic.PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(org.apache.struts.taglib.logic.PresentTag.class);
/*  899 */                     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  900 */                     _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                     
/*  902 */                     _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN");
/*  903 */                     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  904 */                     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                       for (;;) {
/*  906 */                         out.write(32);
/*  907 */                         out.write("\n\t<option value=\"/jsp/ExecProgramActionForm.jsp?haid=");
/*  908 */                         out.print(action_haid);
/*  909 */                         out.write("&global=true");
/*  910 */                         out.print(returnpath);
/*  911 */                         out.write(34);
/*  912 */                         out.write(62);
/*  913 */                         out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  914 */                         out.write("</option>\n\t");
/*  915 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  916 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  920 */                     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  921 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                     }
/*      */                     
/*  924 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  925 */                     out.write("\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=11&haid=");
/*  926 */                     out.print(action_haid);
/*  927 */                     out.write("&global=true");
/*  928 */                     out.print(returnpath);
/*  929 */                     out.write(34);
/*  930 */                     out.write(62);
/*  931 */                     out.print(FormatUtil.getString("am.webclient.common.sendv1trap.text"));
/*  932 */                     out.write("</option>\n\t<option value=\"/jsp/SendTrapActionForm.jsp?snmpversion=12&haid=");
/*  933 */                     out.print(action_haid);
/*  934 */                     out.write("&global=true");
/*  935 */                     out.print(returnpath);
/*  936 */                     out.write(34);
/*  937 */                     out.write(62);
/*  938 */                     out.print(FormatUtil.getString("am.webclient.common.sendv2ctrap.text"));
/*  939 */                     out.write("</option>\n\t");
/*  940 */                     if ((!Constants.sqlManager) && ((!Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/*  941 */                       out.write(32);
/*  942 */                       out.write("\n\t<option value=\"/MBeanOperationAction.do?method=showInitialScreen&popup=true&global=true&haid=");
/*  943 */                       out.print(action_haid);
/*  944 */                       out.print(returnpath);
/*  945 */                       out.write(34);
/*  946 */                       out.write(62);
/*  947 */                       out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  948 */                       out.write("</option>\n\t");
/*      */                     }
/*  950 */                     out.write(10);
/*  951 */                     out.write(9);
/*  952 */                     out.write(9);
/*  953 */                     out.write(10);
/*  954 */                     out.write(9);
/*  955 */                     if ((!Constants.isIt360) || (EnterpriseUtil.isProfEdition()) || (EnterpriseUtil.isAdminServer()))
/*      */                     {
/*  957 */                       out.write("<option value=\"/adminAction.do?method=showLogTicket&global=true&haid=");
/*  958 */                       out.print(action_haid);
/*  959 */                       out.write("&redirectTo=");
/*  960 */                       out.print(redirectTo);
/*  961 */                       out.write(34);
/*  962 */                       out.write(62);
/*  963 */                       out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  964 */                       out.write("</option> ");
/*      */                     }
/*      */                     
/*  967 */                     out.write("\n\t\n\t");
/*  968 */                     if ((!Constants.sqlManager) && ((!Constants.isIt360) || (!EnterpriseUtil.isAdminServer()))) {
/*  969 */                       out.write(" \n\t<!--JRE Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=jre&haid=");
/*  970 */                       out.print(action_haid);
/*  971 */                       out.write("&global=true");
/*  972 */                       out.print(returnpath);
/*  973 */                       out.write("&ext=true\">");
/*  974 */                       out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  975 */                       out.write("</option>\n\t<!--VM Action -->\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&haid=");
/*  976 */                       out.print(action_haid);
/*  977 */                       out.print(returnpath);
/*  978 */                       out.write("&ext=true&global=true\">");
/*  979 */                       out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  980 */                       out.write("</option>\n\t<!--Amazon Instance Action-->\n\t<option value=\"/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=");
/*  981 */                       out.print(action_haid);
/*  982 */                       out.write("&global=true");
/*  983 */                       out.print(returnpath);
/*  984 */                       out.write("&ext=true\">");
/*  985 */                       out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  986 */                       out.write("</option>\n\t<option value=\"/adminAction.do?method=showVMAction&global=true&isContainerAction=true&haid=");
/*  987 */                       out.print(action_haid);
/*  988 */                       out.print(returnpath);
/*  989 */                       out.write("&ext=true&global=true\">");
/*  990 */                       out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  991 */                       out.write("</option>\n\t ");
/*  992 */                     } else if (Constants.sqlManager) {
/*  993 */                       out.write(32);
/*  994 */                       out.write("\n\t<option value=\"/queryAction.do?method=showExecQueryAction&haid=");
/*  995 */                       out.print(action_haid);
/*  996 */                       out.write("&global=true");
/*  997 */                       out.print(returnpath);
/*  998 */                       out.write(34);
/*  999 */                       out.write(62);
/* 1000 */                       out.print(FormatUtil.getString("am.webclient.common.executequery.text"));
/* 1001 */                       out.write("</option>\n\t");
/*      */                     }
/* 1003 */                     out.write(10);
/* 1004 */                     out.write(9);
/* 1005 */                     if ((!Constants.sqlManager) && ((!Constants.isIt360) || (!EnterpriseUtil.isAdminServer())) && (!"CLOUD".equalsIgnoreCase(Constants.getCategorytype()))) {
/* 1006 */                       out.write("\n\t<!-- Windows Service action -->\n\t\t<option value=\"/HostResourceDispatch.do?method=winServAction&haid=");
/* 1007 */                       out.print(action_haid);
/* 1008 */                       out.print(returnpath);
/* 1009 */                       out.write(34);
/* 1010 */                       out.write(62);
/* 1011 */                       out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/* 1012 */                       out.write("</option>\t\n\t");
/*      */                     }
/* 1014 */                     out.write(10);
/* 1015 */                     out.write(9);
/* 1016 */                     out.write(32);
/* 1017 */                     if (Constants.sqlManager) {
/* 1018 */                       out.write("\n\t<!-- Sql job action -->\n\t\t<option value=\"/sqljob.do?method=sqlJobAction&haid=");
/* 1019 */                       out.print(action_haid);
/* 1020 */                       out.print(returnpath);
/* 1021 */                       out.write(34);
/* 1022 */                       out.write(62);
/* 1023 */                       out.print(FormatUtil.getString("am.sqljob.action.createnew"));
/* 1024 */                       out.write("</option>\n\t");
/*      */                     }
/* 1026 */                     out.write(10);
/* 1027 */                     out.write(9);
/* 1028 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1029 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1033 */                 if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1034 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                 }
/*      */                 
/* 1037 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1038 */                 out.write(10);
/* 1039 */                 out.write(9);
/* 1040 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1041 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1045 */             if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1046 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */             }
/*      */             
/* 1049 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1050 */             out.write("\n\t</select>\n    </td>\n  </tr>\n</table>\n\n");
/*      */             
/* 1052 */             IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1053 */             _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 1054 */             _jspx_th_c_005fif_005f16.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 1056 */             _jspx_th_c_005fif_005f16.setTest("${param.global=='true'}");
/* 1057 */             int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 1058 */             if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */               for (;;) {
/* 1060 */                 out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n<td valign=\"top\"> \n<table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
/* 1061 */                 out.write("<!--$Id$-->\n\n\n\n");
/* 1062 */                 if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                   return;
/* 1064 */                 out.write("\n      \n\n");
/*      */                 
/* 1066 */                 IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1067 */                 _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 1068 */                 _jspx_th_c_005fif_005f17.setParent(_jspx_th_c_005fif_005f16);
/*      */                 
/* 1070 */                 _jspx_th_c_005fif_005f17.setTest("${(uri=='/jsp/CreateApplication.jsp')|| uri=='/admin/createapplication.do'||uri=='/admin/createapplication.do' ||(!empty param.wiz && !empty param.haid && (empty invalidhaid))||(param.method=='insert')}");
/* 1071 */                 int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 1072 */                 if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                   for (;;) {
/* 1074 */                     out.write("\n\t  <tr>\n\t  <td class=\"tdindent\">\n");
/* 1075 */                     if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/* 1077 */                     out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">  \n  <tr> \n    <td width=\"2%\">");
/*      */                     
/* 1079 */                     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1080 */                     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 1081 */                     _jspx_th_c_005fif_005f18.setParent(_jspx_th_c_005fif_005f17);
/*      */                     
/* 1083 */                     _jspx_th_c_005fif_005f18.setTest("${uri=='/jsp/CreateApplication.jsp' || uri=='/admin/createapplicationwiz.do'||uri=='/admin/createapplication.do'}");
/* 1084 */                     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 1085 */                     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                       for (;;) {
/* 1087 */                         out.write("\n    \t");
/*      */                         
/* 1089 */                         SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1090 */                         _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 1091 */                         _jspx_th_c_005fset_005f6.setParent(_jspx_th_c_005fif_005f18);
/*      */                         
/* 1093 */                         _jspx_th_c_005fset_005f6.setVar("wizimage");
/* 1094 */                         int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 1095 */                         if (_jspx_eval_c_005fset_005f6 != 0) {
/* 1096 */                           if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1097 */                             out = _jspx_page_context.pushBody();
/* 1098 */                             _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 1099 */                             _jspx_th_c_005fset_005f6.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 1102 */                             out.write(" \n    \t<img src=\"/images/wiz_newbizapp_high.gif\" border=\"0\" align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1103 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1104 */                             out.write(" </b></font>\n    \t");
/* 1105 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 1106 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 1109 */                           if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1110 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 1113 */                         if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 1114 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6); return;
/*      */                         }
/*      */                         
/* 1117 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 1118 */                         out.write("\n    ");
/* 1119 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 1120 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1124 */                     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 1125 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                     }
/*      */                     
/* 1128 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 1129 */                     out.write("\n    ");
/*      */                     
/* 1131 */                     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1132 */                     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 1133 */                     _jspx_th_c_005fif_005f19.setParent(_jspx_th_c_005fif_005f17);
/*      */                     
/* 1135 */                     _jspx_th_c_005fif_005f19.setTest("${uri!='/jsp/CreateApplication.jsp' && uri!='/admin/createapplicationwiz.do'&& uri!='/admin/createapplication.do'}");
/* 1136 */                     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 1137 */                     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                       for (;;) {
/* 1139 */                         out.write("\n    \t");
/*      */                         
/* 1141 */                         SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1142 */                         _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 1143 */                         _jspx_th_c_005fset_005f7.setParent(_jspx_th_c_005fif_005f19);
/*      */                         
/* 1145 */                         _jspx_th_c_005fset_005f7.setVar("wizimage");
/* 1146 */                         int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 1147 */                         if (_jspx_eval_c_005fset_005f7 != 0) {
/* 1148 */                           if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1149 */                             out = _jspx_page_context.pushBody();
/* 1150 */                             _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 1151 */                             _jspx_th_c_005fset_005f7.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 1154 */                             out.write("\n    \t<img src=\"/images/wiz_newbizapp_nor.gif\" border=0> <font family=\"verdana\" size=\"2\" color=\"#818181\">");
/* 1155 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step1"));
/* 1156 */                             out.write("</font>  \t");
/* 1157 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 1158 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 1161 */                           if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1162 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 1165 */                         if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 1166 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                         }
/*      */                         
/* 1169 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 1170 */                         out.write("\n    ");
/* 1171 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 1172 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1176 */                     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 1177 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                     }
/*      */                     
/* 1180 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 1181 */                     out.write("      \n\t</td>\n    <td width=\"20%\">");
/* 1182 */                     if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/* 1184 */                     out.write("</td>  \n   \n");
/*      */                     
/* 1186 */                     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1187 */                     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1188 */                     _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fif_005f17);
/* 1189 */                     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1190 */                     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                       for (;;) {
/* 1192 */                         out.write("\n    ");
/*      */                         
/* 1194 */                         WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1195 */                         _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1196 */                         _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                         
/* 1198 */                         _jspx_th_c_005fwhen_005f4.setTest("${( param.method=='configureHostDiscovery' || param.method=='associateMonitors'||param.method=='getMonitorForm'||param.method=='addResource')&& (empty showwiz3) && (empty UrlForm) }");
/* 1199 */                         int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1200 */                         if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                           for (;;) {
/* 1202 */                             out.write("\n    \n\t\n\t\t\n\t\t");
/*      */                             
/* 1204 */                             SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1205 */                             _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 1206 */                             _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                             
/* 1208 */                             _jspx_th_c_005fset_005f8.setVar("wizimage");
/* 1209 */                             int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 1210 */                             if (_jspx_eval_c_005fset_005f8 != 0) {
/* 1211 */                               if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1212 */                                 out = _jspx_page_context.pushBody();
/* 1213 */                                 _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 1214 */                                 _jspx_th_c_005fset_005f8.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 1217 */                                 out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"FF0000\"><b> ");
/* 1218 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1219 */                                 out.write(" </b></font>\n    \t");
/* 1220 */                                 int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 1221 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1224 */                               if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1225 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1228 */                             if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 1229 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                             }
/*      */                             
/* 1232 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 1233 */                             out.write("\n   ");
/* 1234 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1235 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1239 */                         if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1240 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                         }
/*      */                         
/* 1243 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1244 */                         out.write("\n   ");
/*      */                         
/* 1246 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1247 */                         _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1248 */                         _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 1249 */                         int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1250 */                         if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                           for (;;) {
/* 1252 */                             out.write("  \n    \t\n\t\t");
/*      */                             
/* 1254 */                             SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1255 */                             _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 1256 */                             _jspx_th_c_005fset_005f9.setParent(_jspx_th_c_005fotherwise_005f4);
/*      */                             
/* 1258 */                             _jspx_th_c_005fset_005f9.setVar("wizimage");
/* 1259 */                             int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 1260 */                             if (_jspx_eval_c_005fset_005f9 != 0) {
/* 1261 */                               if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1262 */                                 out = _jspx_page_context.pushBody();
/* 1263 */                                 _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 1264 */                                 _jspx_th_c_005fset_005f9.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 1267 */                                 out.write(" \n    \t<img src=\"/images/wiz_associatemonitor_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1268 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step2"));
/* 1269 */                                 out.write(" </font>\n    \t");
/* 1270 */                                 int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 1271 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1274 */                               if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1275 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1278 */                             if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 1279 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                             }
/*      */                             
/* 1282 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 1283 */                             out.write("\n\t\n\t\t\n   ");
/* 1284 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1285 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1289 */                         if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1290 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                         }
/*      */                         
/* 1293 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1294 */                         out.write(10);
/* 1295 */                         out.write(32);
/* 1296 */                         out.write(32);
/* 1297 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1298 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1302 */                     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1303 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                     }
/*      */                     
/* 1306 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1307 */                     out.write(" \n\n    \n     <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"20%\">\n    ");
/* 1308 */                     if (_jspx_meth_c_005fif_005f20(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/* 1310 */                     out.write("\n    \t");
/* 1311 */                     if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/* 1313 */                     out.write("\n    \t\n    \t");
/* 1314 */                     if (_jspx_meth_c_005fif_005f21(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/* 1316 */                     out.write("\n    \t</td>    \t\n    \t<!-- ############################################# check for third tab #####################################  -->\n       ");
/*      */                     
/* 1318 */                     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1319 */                     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 1320 */                     _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_c_005fif_005f17);
/* 1321 */                     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 1322 */                     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                       for (;;) {
/* 1324 */                         out.write("\n       ");
/*      */                         
/* 1326 */                         WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1327 */                         _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 1328 */                         _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                         
/* 1330 */                         _jspx_th_c_005fwhen_005f5.setTest("${(param.method=='reloadHostDiscoveryForm'|| param.method=='getMonitorForm'||param.method=='addResource'|| (!empty showwiz3) || (!empty UrlForm) ) && (param.method!='getHAProfiles') }");
/* 1331 */                         int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 1332 */                         if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                           for (;;) {
/* 1334 */                             out.write("\n   \n   \t    \t");
/*      */                             
/* 1336 */                             SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1337 */                             _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 1338 */                             _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fwhen_005f5);
/*      */                             
/* 1340 */                             _jspx_th_c_005fset_005f10.setVar("wizimage");
/* 1341 */                             int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 1342 */                             if (_jspx_eval_c_005fset_005f10 != 0) {
/* 1343 */                               if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1344 */                                 out = _jspx_page_context.pushBody();
/* 1345 */                                 _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 1346 */                                 _jspx_th_c_005fset_005f10.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 1349 */                                 out.write("\n   \t    \t<img src=\"/images/new_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b> ");
/* 1350 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1351 */                                 out.write(" </b></font>\n   \t    \t");
/* 1352 */                                 int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 1353 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1356 */                               if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1357 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1360 */                             if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 1361 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                             }
/*      */                             
/* 1364 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 1365 */                             out.write("\n       ");
/* 1366 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 1367 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1371 */                         if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 1372 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                         }
/*      */                         
/* 1375 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1376 */                         out.write("\n        ");
/*      */                         
/* 1378 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1379 */                         _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 1380 */                         _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 1381 */                         int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 1382 */                         if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                           for (;;) {
/* 1384 */                             out.write("  \n   \t    \t");
/*      */                             
/* 1386 */                             SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1387 */                             _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 1388 */                             _jspx_th_c_005fset_005f11.setParent(_jspx_th_c_005fotherwise_005f5);
/*      */                             
/* 1390 */                             _jspx_th_c_005fset_005f11.setVar("wizimage");
/* 1391 */                             int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 1392 */                             if (_jspx_eval_c_005fset_005f11 != 0) {
/* 1393 */                               if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1394 */                                 out = _jspx_page_context.pushBody();
/* 1395 */                                 _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 1396 */                                 _jspx_th_c_005fset_005f11.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 1399 */                                 out.write("\n\t\t   \t    \t<img src=\"/images/new_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1400 */                                 out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step3"));
/* 1401 */                                 out.write(" </font>\n   \t    \t");
/* 1402 */                                 int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 1403 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 1406 */                               if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1407 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 1410 */                             if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 1411 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11); return;
/*      */                             }
/*      */                             
/* 1414 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 1415 */                             out.write("\n   \t");
/* 1416 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 1417 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 1421 */                         if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 1422 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                         }
/*      */                         
/* 1425 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 1426 */                         out.write(10);
/* 1427 */                         out.write(32);
/* 1428 */                         out.write(32);
/* 1429 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 1430 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1434 */                     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 1435 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                     }
/*      */                     
/* 1438 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 1439 */                     out.write(" \n\n        <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n       <td width=\"18%\">\n       ");
/* 1440 */                     if (_jspx_meth_c_005fif_005f22(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/* 1442 */                     out.write("\n       ");
/* 1443 */                     if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/* 1445 */                     out.write("\n       ");
/* 1446 */                     out.write("\n       \t");
/* 1447 */                     if (_jspx_meth_c_005fif_005f23(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/* 1449 */                     out.write("\n    \t</td>\n   \n  <td width=\"2%\"><img src=\"/images/wiz_grayseparator.gif\" width=\"5\" height=\"27\"></td>\n    <td width=\"17%\">\n\t");
/*      */                     
/* 1451 */                     IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1452 */                     _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 1453 */                     _jspx_th_c_005fif_005f24.setParent(_jspx_th_c_005fif_005f17);
/*      */                     
/* 1455 */                     _jspx_th_c_005fif_005f24.setTest("${param.method=='getHAProfiles'}");
/* 1456 */                     int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 1457 */                     if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */                       for (;;) {
/* 1459 */                         out.write(10);
/* 1460 */                         out.write(9);
/*      */                         
/* 1462 */                         SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1463 */                         _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 1464 */                         _jspx_th_c_005fset_005f12.setParent(_jspx_th_c_005fif_005f24);
/*      */                         
/* 1466 */                         _jspx_th_c_005fset_005f12.setVar("wizimage");
/* 1467 */                         int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 1468 */                         if (_jspx_eval_c_005fset_005f12 != 0) {
/* 1469 */                           if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1470 */                             out = _jspx_page_context.pushBody();
/* 1471 */                             _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 1472 */                             _jspx_th_c_005fset_005f12.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 1475 */                             out.write("\n\t\t<img src=\"/images/wiz_configurealerts_high.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#FF0000\"><b>");
/* 1476 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1477 */                             out.write(" </b></font>\n    \t");
/* 1478 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 1479 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 1482 */                           if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1483 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 1486 */                         if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 1487 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12); return;
/*      */                         }
/*      */                         
/* 1490 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 1491 */                         out.write(10);
/* 1492 */                         out.write(9);
/* 1493 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 1494 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1498 */                     if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 1499 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24); return;
/*      */                     }
/*      */                     
/* 1502 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 1503 */                     out.write(10);
/* 1504 */                     out.write(9);
/*      */                     
/* 1506 */                     IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1507 */                     _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 1508 */                     _jspx_th_c_005fif_005f25.setParent(_jspx_th_c_005fif_005f17);
/*      */                     
/* 1510 */                     _jspx_th_c_005fif_005f25.setTest("${param.method!='getHAProfiles'}");
/* 1511 */                     int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 1512 */                     if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                       for (;;) {
/* 1514 */                         out.write(10);
/* 1515 */                         out.write(9);
/*      */                         
/* 1517 */                         SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1518 */                         _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 1519 */                         _jspx_th_c_005fset_005f13.setParent(_jspx_th_c_005fif_005f25);
/*      */                         
/* 1521 */                         _jspx_th_c_005fset_005f13.setVar("wizimage");
/* 1522 */                         int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 1523 */                         if (_jspx_eval_c_005fset_005f13 != 0) {
/* 1524 */                           if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1525 */                             out = _jspx_page_context.pushBody();
/* 1526 */                             _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 1527 */                             _jspx_th_c_005fset_005f13.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 1530 */                             out.write("\n\t\t<img src=\"/images/wiz_configurealerts_nor.gif\" border=0 align=\"absmiddle\"><font family=\"verdana\" size=\"2\" color=\"#818181\"> ");
/* 1531 */                             out.print(FormatUtil.getString("am.webclient.monitorgroupmessage.step4"));
/* 1532 */                             out.write(" </font>\n    \t");
/* 1533 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 1534 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 1537 */                           if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1538 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 1541 */                         if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 1542 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13); return;
/*      */                         }
/*      */                         
/* 1545 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 1546 */                         out.write("\n\t\n\t");
/* 1547 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 1548 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1552 */                     if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 1553 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                     }
/*      */                     
/* 1556 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 1557 */                     out.write(10);
/* 1558 */                     if (_jspx_meth_c_005fif_005f26(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/* 1560 */                     out.write("   \n ");
/* 1561 */                     if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/* 1563 */                     out.write(10);
/* 1564 */                     out.write(32);
/* 1565 */                     out.write(10);
/* 1566 */                     if (_jspx_meth_c_005fif_005f27(_jspx_th_c_005fif_005f17, _jspx_page_context))
/*      */                       return;
/* 1568 */                     out.write("   \n    </td>\n    \n    <td width=\"2%\" rowspan=\"2\" valign=\"bottom\" align=\"right\"><img src=\"/images/wiz_endicon.gif\" width=\"33\" height=\"36\"></td>\n  </tr>\n  <tr background=\"/images/wiz_bg_graylind.gif\"> \n    <td><img src=\"/images/wiz_startimage.gif\" width=\"18\" height=\"16\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n    <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n  <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n   \n   <td background=\"/images/wiz_bg_graylind.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"12\"></td>\n \n");
/* 1569 */                     out.write("  </tr>\n\n</table>\n</td></tr>\n");
/* 1570 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 1571 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1575 */                 if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 1576 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                 }
/*      */                 
/* 1579 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 1580 */                 out.write(10);
/* 1581 */                 out.write(10);
/* 1582 */                 if (request.getAttribute("EmailForm") == null) {
/* 1583 */                   out.write(10);
/*      */                   
/* 1585 */                   MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1586 */                   _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 1587 */                   _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_c_005fif_005f16);
/*      */                   
/* 1589 */                   _jspx_th_html_005fmessages_005f0.setId("msg");
/*      */                   
/* 1591 */                   _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 1592 */                   int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 1593 */                   if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 1594 */                     String msg = null;
/* 1595 */                     if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1596 */                       out = _jspx_page_context.pushBody();
/* 1597 */                       _jspx_th_html_005fmessages_005f0.setBodyContent((BodyContent)out);
/* 1598 */                       _jspx_th_html_005fmessages_005f0.doInitBody();
/*      */                     }
/* 1600 */                     msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                     for (;;) {
/* 1602 */                       out.write(" \n<tr> \n  <td width=\"70%\" height=\"24\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 1603 */                       if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*      */                         return;
/* 1605 */                       out.write("</td>\n\t  </tr>\n\t</table>\n\t<br></td>\n</tr>\n");
/* 1606 */                       int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 1607 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/* 1608 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1611 */                     if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 1612 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1615 */                   if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 1616 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*      */                   }
/*      */                   
/* 1619 */                   this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*      */                 }
/* 1621 */                 out.write(32);
/*      */                 
/* 1623 */                 MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 1624 */                 _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 1625 */                 _jspx_th_logic_005fmessagesPresent_005f0.setParent(_jspx_th_c_005fif_005f16);
/*      */                 
/* 1627 */                 _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 1628 */                 int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 1629 */                 if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*      */                   for (;;) {
/* 1631 */                     out.write(" \n<tr> \n  <td height=\"46\" valign=\"top\" class=\"tdindent\"> <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t  <tr> \n\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t<td width=\"95%\" class=\"message\"> ");
/*      */                     
/* 1633 */                     MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 1634 */                     _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 1635 */                     _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*      */                     
/* 1637 */                     _jspx_th_html_005fmessages_005f1.setId("msg");
/*      */                     
/* 1639 */                     _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 1640 */                     int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 1641 */                     if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 1642 */                       String msg = null;
/* 1643 */                       if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1644 */                         out = _jspx_page_context.pushBody();
/* 1645 */                         _jspx_th_html_005fmessages_005f1.setBodyContent((BodyContent)out);
/* 1646 */                         _jspx_th_html_005fmessages_005f1.doInitBody();
/*      */                       }
/* 1648 */                       msg = (String)_jspx_page_context.findAttribute("msg");
/*      */                       for (;;) {
/* 1650 */                         out.write("\n\t  ");
/* 1651 */                         if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*      */                           return;
/* 1653 */                         out.write("<br>\n\t  ");
/* 1654 */                         int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 1655 */                         msg = (String)_jspx_page_context.findAttribute("msg");
/* 1656 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 1659 */                       if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 1660 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 1663 */                     if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 1664 */                       this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*      */                     }
/*      */                     
/* 1667 */                     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 1668 */                     out.write(" </td>\n\t  </tr>\n\t</table></td>\n</tr>\n");
/* 1669 */                     int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 1670 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1674 */                 if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 1675 */                   this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0); return;
/*      */                 }
/*      */                 
/* 1678 */                 this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 1679 */                 out.write("\n</table>\n<script>\n\tobject=location.href;\n\t\n\tif(document.AMActionForm!=null)\n\t{\t  \n\t  if(object.match(\"EMailActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=0;\n\t  }\n\t  else if(object.match(\"SMSActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=1;\n\t  }\n\t  else if(object.match(\"ExecProgramActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=2;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=11\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=4;\n\t  }\n\t  else if(object.match(\"SendTrapActionForm\")!=null && object.match(\"snmpversion=12\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=3;\n\t  }\t  \n\t  else if(object.match(\"showLogTicket\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=6;\n\t  }\n\t  else if(object.match(\"MBeanOperationActionForm\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=5;\n\t  }\t\n\t  else if(object.match(\"jre\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=7;\n");
/* 1680 */                 out.write("\t  }\n\t  else if(object.match(\"showVMAction\")!=null)\n\t  {\n\t    document.AMActionForm.actions.selectedIndex=8;\n\t  }\n\t  else if(object.match(\"amazon\")!=null)\n\t  {\n\t\t    document.AMActionForm.actions.selectedIndex=9;\n\t }\t  \n\t}\n\telse if(document.MBeanOperationActionForm!=null)\n\t{\n\t  document.MBeanOperationActionForm.actions.selectedIndex=5;\t  \n\t}\n</script>\n</td>\n</tr>\n</table>\n");
/* 1681 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 1682 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1686 */             if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 1687 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */             }
/*      */             
/* 1690 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 1691 */             out.write(10);
/* 1692 */             out.write("\n<am:hiddenparam name=\"global\"/>\n<am:hiddenparam name=\"returnpath\"/>\n<input type=\"hidden\" name=\"method\" value=\"");
/* 1693 */             out.print((String)request.getAttribute("SdeskLogTicketConfig"));
/* 1694 */             out.write("\" >\n<input type=\"hidden\" name=\"customHeaders\" id=\"customHeadersId\" />\n<input type=\"hidden\" name=\"ignoreAdditionalFields\" value=\"false\" >\n");
/*      */             
/* 1696 */             IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1697 */             _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 1698 */             _jspx_th_c_005fif_005f28.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 1700 */             _jspx_th_c_005fif_005f28.setTest("${errorMsg ne null}");
/* 1701 */             int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 1702 */             if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */               for (;;) {
/* 1704 */                 out.write(" \n <table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n <tr>\n  <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n  <td width=\"95%\" class=\"message\"> ");
/* 1705 */                 out.print(request.getAttribute("errorMsg"));
/* 1706 */                 out.write("</td>\n </tr>\n </table>\n <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n   <td  height=\"10\" ><img src=\"../images/spacer.gif\" ></td>\n  </tr>\n </table>\n");
/* 1707 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 1708 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1712 */             if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 1713 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28); return;
/*      */             }
/*      */             
/* 1716 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 1717 */             out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n<td width=\"70%\" valign=\"top\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n <tr >\n   <td width=\"72%\" height=\"31\" class=\"tableheading\" >");
/* 1718 */             out.print(FormatUtil.getString((String)request.getAttribute("Configure")));
/* 1719 */             out.write(32);
/* 1720 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketdetail"));
/* 1721 */             out.write("</td>\n </tr>\n</table> \n<table width=\"99%\" border=\"0\" cellspacing=\"8\" cellpadding=\"0\" class=\"lrborder\" >\n <tr>\n  <td>\n  <table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\">\n   <tr>\n   <td class=\"bodytext label-align\" width=\"25%\">");
/* 1722 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketname"));
/* 1723 */             out.write("<span class=\"mandatory\">*</span></td>\n   <td class=\"bodytext\" width=\"75%\">");
/* 1724 */             if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 1726 */             out.write("</td>\n   </tr>\n   ");
/*      */             
/* 1728 */             IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1729 */             _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 1730 */             _jspx_th_c_005fif_005f29.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 1732 */             _jspx_th_c_005fif_005f29.setTest("${product ne 'ServiceNow'}");
/* 1733 */             int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 1734 */             if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */               for (;;) {
/* 1736 */                 out.write("\n   <tr>\n\t\t<td class=\"bodytext label-align\" width=\"25%\" nowrap=\"\" valign=\"middle\">\n\t\t  <div id=\"ticketType1\" style=\"display:block;\"> \n\t\t\t  <a style=\"cursor: pointer;\" class=\"dotteduline\">");
/* 1737 */                 out.print(FormatUtil.getString("am.webclient.sdp.ticketingtype.txt"));
/* 1738 */                 out.write("</a><span class=\"mandatory\">*</span></div></td> ");
/* 1739 */                 out.write("\n\t\t<td colspan=\"2\" class=\"bodytext\" width=\"75%\">\n\t\t\t<div id=\"ticketType2\" style=\"display:block;\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr valign=\"top\">\n\t\t \t\t\t<td class=\"bodytext\">\n\t\t \t\t\t\t");
/* 1740 */                 if (_jspx_meth_html_005fradio_005f0(_jspx_th_c_005fif_005f29, _jspx_page_context))
/*      */                   return;
/* 1742 */                 out.write("\n\t\t \t\t\t\t");
/* 1743 */                 out.print(FormatUtil.getString("am.webclient.sdp.restapikey.text"));
/* 1744 */                 out.write("\n\t\t \t\t\t\t");
/* 1745 */                 if (_jspx_meth_html_005fradio_005f1(_jspx_th_c_005fif_005f29, _jspx_page_context))
/*      */                   return;
/* 1747 */                 out.write("\n\t\t \t\t\t\t");
/* 1748 */                 out.print(FormatUtil.getString("am.webclient.sdp.credential.text"));
/* 1749 */                 out.write("&nbsp;&nbsp;&nbsp;\n\t\t \t\t\t</td>\n\t\t\t\t</tr>\n\t        </table>\n\t\t</div>\n\t\t</td>\n\t</tr>\n\t");
/* 1750 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 1751 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1755 */             if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 1756 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29); return;
/*      */             }
/*      */             
/* 1759 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 1760 */             out.write("\n   <tr width=\"99%\" bgcolor=\"#efefef\">\n  \t\t<td colspan=\"2\" class=\"bodytext\"><b>");
/* 1761 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketdetail"));
/* 1762 */             out.write("</b></td>\n\t</tr>\n\t<tr>\n\t <td class=\"bodytext\" colspan=\"2\" >");
/* 1763 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketdesc"));
/* 1764 */             out.write("</td>\n\t</tr>\n\t</table>\n<div style=\"display: block;\">\n");
/*      */             
/* 1766 */             IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1767 */             _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/* 1768 */             _jspx_th_c_005fif_005f30.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 1770 */             _jspx_th_c_005fif_005f30.setTest("${product ne 'ServiceNow'}");
/* 1771 */             int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/* 1772 */             if (_jspx_eval_c_005fif_005f30 != 0) {
/*      */               for (;;) {
/* 1774 */                 out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" >\n\t");
/*      */                 
/* 1776 */                 IfTag _jspx_th_c_005fif_005f31 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1777 */                 _jspx_th_c_005fif_005f31.setPageContext(_jspx_page_context);
/* 1778 */                 _jspx_th_c_005fif_005f31.setParent(_jspx_th_c_005fif_005f30);
/*      */                 
/* 1780 */                 _jspx_th_c_005fif_005f31.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 1781 */                 int _jspx_eval_c_005fif_005f31 = _jspx_th_c_005fif_005f31.doStartTag();
/* 1782 */                 if (_jspx_eval_c_005fif_005f31 != 0) {
/*      */                   for (;;) {
/* 1784 */                     out.write(10);
/* 1785 */                     out.write(9);
/*      */                     
/* 1787 */                     IfTag _jspx_th_c_005fif_005f32 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1788 */                     _jspx_th_c_005fif_005f32.setPageContext(_jspx_page_context);
/* 1789 */                     _jspx_th_c_005fif_005f32.setParent(_jspx_th_c_005fif_005f31);
/*      */                     
/* 1791 */                     _jspx_th_c_005fif_005f32.setTest("${AMActionForm.mspDesk ne true}");
/* 1792 */                     int _jspx_eval_c_005fif_005f32 = _jspx_th_c_005fif_005f32.doStartTag();
/* 1793 */                     if (_jspx_eval_c_005fif_005f32 != 0) {
/*      */                       for (;;) {
/* 1795 */                         out.write("\n\t<tr>\n\t     <td class=\"bodytext label-align\" width=\"25%\">");
/* 1796 */                         out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.reqTemplate"));
/* 1797 */                         out.write("</td>\n\t\t  <td width=\"75%\">\n\t\t   ");
/* 1798 */                         if (_jspx_meth_html_005fselect_005f0(_jspx_th_c_005fif_005f32, _jspx_page_context))
/*      */                           return;
/* 1800 */                         out.write("\n\t\t</td>\n\t  </tr>\n\t");
/* 1801 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f32.doAfterBody();
/* 1802 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1806 */                     if (_jspx_th_c_005fif_005f32.doEndTag() == 5) {
/* 1807 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32); return;
/*      */                     }
/*      */                     
/* 1810 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f32);
/* 1811 */                     out.write(10);
/* 1812 */                     out.write(9);
/* 1813 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f31.doAfterBody();
/* 1814 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1818 */                 if (_jspx_th_c_005fif_005f31.doEndTag() == 5) {
/* 1819 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31); return;
/*      */                 }
/*      */                 
/* 1822 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 1823 */                 out.write("\n\t\n\t<tr><td colspan=\"2\" style=\"padding:0px\"><div id=\"noTemplateDiv\" style=\"display:block\"><table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\">\n\t");
/* 1824 */                 out.write(10);
/* 1825 */                 out.write(9);
/* 1826 */                 out.write(9);
/*      */                 
/* 1828 */                 IfTag _jspx_th_c_005fif_005f33 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1829 */                 _jspx_th_c_005fif_005f33.setPageContext(_jspx_page_context);
/* 1830 */                 _jspx_th_c_005fif_005f33.setParent(_jspx_th_c_005fif_005f30);
/*      */                 
/* 1832 */                 _jspx_th_c_005fif_005f33.setTest("${AMActionForm.mspDesk eq true}");
/* 1833 */                 int _jspx_eval_c_005fif_005f33 = _jspx_th_c_005fif_005f33.doStartTag();
/* 1834 */                 if (_jspx_eval_c_005fif_005f33 != 0) {
/*      */                   for (;;) {
/* 1836 */                     out.write("\n\t \t<tr>\n\t\t     <td class=\"bodytext label-align\" width=\"25%\">");
/* 1837 */                     out.print(FormatUtil.getString("am.webclient.newaction.logaticket.accountname"));
/* 1838 */                     out.write("<span class=\"mandatory\">*</span></td>\n\t\t     <td width=\"75%\">\n\t\t\t\t");
/* 1839 */                     if (_jspx_meth_html_005fselect_005f1(_jspx_th_c_005fif_005f33, _jspx_page_context))
/*      */                       return;
/* 1841 */                     out.write("\n\t\t\t  </td>\n\t  \t</tr>\t\n\t  \t\t\t<tr>\n\t  \t <td class=\"bodytext label-align\" width=\"25%\">");
/* 1842 */                     out.print(FormatUtil.getString("am.webclient.newaction.logaticket.sitename"));
/* 1843 */                     out.write("<span class=\"mandatory\">*</span></td>\n\t  \t <td width=\"75%\">\t\t\t\n\t\t\t\t");
/* 1844 */                     if (_jspx_meth_html_005fselect_005f2(_jspx_th_c_005fif_005f33, _jspx_page_context))
/*      */                       return;
/* 1846 */                     out.write("\n\t\t</td>\n\t\t</tr>\t\n\t");
/* 1847 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f33.doAfterBody();
/* 1848 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1852 */                 if (_jspx_th_c_005fif_005f33.doEndTag() == 5) {
/* 1853 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33); return;
/*      */                 }
/*      */                 
/* 1856 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f33);
/* 1857 */                 out.write(" \n\t  \n\t\t<tr>\n\t     <td class=\"bodytext label-align\" width=\"25%\">");
/* 1858 */                 out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketcategory"));
/* 1859 */                 out.write("</td>\n\t\t  <td width=\"75%\">\n\t\t   ");
/* 1860 */                 if (_jspx_meth_html_005fselect_005f3(_jspx_th_c_005fif_005f30, _jspx_page_context))
/*      */                   return;
/* 1862 */                 out.write("\n\t\t</td>\n\t  </tr>\n\t<tr>\n\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 1863 */                 out.print(FormatUtil.getString("am.webclient.sdp.subcategory.text"));
/* 1864 */                 out.write("</td>\n\t\t<td width=\"75%\">\n\t\t");
/* 1865 */                 if (_jspx_meth_html_005fselect_005f4(_jspx_th_c_005fif_005f30, _jspx_page_context))
/*      */                   return;
/* 1867 */                 out.write("\n\t\t</td>\n\t</tr>\n\t<tr>\n\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 1868 */                 out.print(FormatUtil.getString("am.webclient.sdp.item.text"));
/* 1869 */                 out.write("</td>\n\t\t<td width=\"75%\">\n\t\t");
/* 1870 */                 if (_jspx_meth_html_005fselect_005f5(_jspx_th_c_005fif_005f30, _jspx_page_context))
/*      */                   return;
/* 1872 */                 out.write("\n\t\t</td>\n\t</tr>\n\n\t <tr>\n\t     <td class=\"bodytext label-align\" width=\"25%\">");
/* 1873 */                 out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketpriority"));
/* 1874 */                 out.write("</td>\n\t\t  <td width=\"75%\">\n\t\t    ");
/* 1875 */                 if (_jspx_meth_html_005fselect_005f6(_jspx_th_c_005fif_005f30, _jspx_page_context))
/*      */                   return;
/* 1877 */                 out.write("\n\t\t  </td>\n\t  </tr>\n\n\n\t\n\t\n\t\t<tr>\n\t\t     <td class=\"bodytext label-align\" width=\"25%\">");
/* 1878 */                 out.print(FormatUtil.getString("am.webclient.sdp.group.text"));
/* 1879 */                 out.write("</td>\n\t\t\t  <td width=\"75%\">\n\t\t\t  ");
/* 1880 */                 if (_jspx_meth_html_005fselect_005f7(_jspx_th_c_005fif_005f30, _jspx_page_context))
/*      */                   return;
/* 1882 */                 out.write("\n\t\t\t  </td>\n\t </tr>\n\t<tr>\n\t\t <td class=\"bodytext label-align\" width=\"25%\">");
/* 1883 */                 out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.tickettechnician"));
/* 1884 */                 out.write("</td>\n\t\t  <td width=\"75%\">\n\t\t\t\t\t");
/* 1885 */                 if (_jspx_meth_html_005fselect_005f8(_jspx_th_c_005fif_005f30, _jspx_page_context))
/*      */                   return;
/* 1887 */                 out.write("\n\t\t  </td>\n\t</tr>\n\t<tr>\n\t");
/*      */                 
/* 1889 */                 IfTag _jspx_th_c_005fif_005f34 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1890 */                 _jspx_th_c_005fif_005f34.setPageContext(_jspx_page_context);
/* 1891 */                 _jspx_th_c_005fif_005f34.setParent(_jspx_th_c_005fif_005f30);
/*      */                 
/* 1893 */                 _jspx_th_c_005fif_005f34.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 1894 */                 int _jspx_eval_c_005fif_005f34 = _jspx_th_c_005fif_005f34.doStartTag();
/* 1895 */                 if (_jspx_eval_c_005fif_005f34 != 0) {
/*      */                   for (;;) {
/* 1897 */                     out.write(10);
/* 1898 */                     out.write(9);
/*      */                     
/* 1900 */                     IfTag _jspx_th_c_005fif_005f35 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1901 */                     _jspx_th_c_005fif_005f35.setPageContext(_jspx_page_context);
/* 1902 */                     _jspx_th_c_005fif_005f35.setParent(_jspx_th_c_005fif_005f34);
/*      */                     
/* 1904 */                     _jspx_th_c_005fif_005f35.setTest("${AMActionForm.mspDesk ne true}");
/* 1905 */                     int _jspx_eval_c_005fif_005f35 = _jspx_th_c_005fif_005f35.doStartTag();
/* 1906 */                     if (_jspx_eval_c_005fif_005f35 != 0) {
/*      */                       for (;;) {
/* 1908 */                         out.write("\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 1909 */                         out.print(FormatUtil.getString("Site"));
/* 1910 */                         out.write("</td>\n\t\t\t<td width=\"75%\">\n\t\t\t\t<input type=\"text\" name=\"site\"  class=\"formtext normal\" style=\"width:100%\"/>\n\t\t\t</td>\n\t\t\t");
/* 1911 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f35.doAfterBody();
/* 1912 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1916 */                     if (_jspx_th_c_005fif_005f35.doEndTag() == 5) {
/* 1917 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f35); return;
/*      */                     }
/*      */                     
/* 1920 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f35);
/* 1921 */                     out.write("\n\t\t</tr>\n\t\t<tr>\n    \t<td height=\"25\"  class=\"bodytext\" colspan=\"2\"> <input type=\"checkbox\" name=\"customHeadersSupported\" class=\"customHeadersCSS\" />");
/* 1922 */                     out.print(FormatUtil.getString("am.myfield.incidenttemplate.text"));
/* 1923 */                     out.write("</td>\n  \t</tr>\n  \t");
/* 1924 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f34.doAfterBody();
/* 1925 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1929 */                 if (_jspx_th_c_005fif_005f34.doEndTag() == 5) {
/* 1930 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34); return;
/*      */                 }
/*      */                 
/* 1933 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f34);
/* 1934 */                 out.write("\n\t<tr id=\"advanceOptionDiv\" style=\"display: none;\">\n\t\t<td class=\"bodytext\" width=\"100%\" colspan=\"2\">\t\n\t\t  \t<table border=\"0\"  cellpadding=\"5\" cellspacing=\"0\" id=\"tableToInsert\" width=\"100%\">\n\t\t  \t<tr>\n    \t\t\t<td height=\"25\" width=\"50%\" class=\"bodytext\" style=\"padding-left: 12%;font-weight: bold;\">");
/* 1935 */                 out.print(FormatUtil.getString("am.myfield.name.text"));
/* 1936 */                 out.write("</td>\n    \t\t\t<td height=\"25\" width=\"50%\" class=\"bodytext\" align=\"left\" style=\"padding-left: 4%;font-weight: bold;\">");
/* 1937 */                 out.print(FormatUtil.getString("am.myfield.value.text"));
/* 1938 */                 out.write("</td>\n  \t\t\t</tr>\n\t\t\t ");
/*      */                 
/* 1940 */                 if ((customHeaders != null) && (!customHeaders.equals("")))
/*      */                 {
/* 1942 */                   java.util.StringTokenizer headerToken = new java.util.StringTokenizer(customHeaders, "#");
/* 1943 */                   int i = 0;
/* 1944 */                   while (headerToken.hasMoreElements())
/*      */                   {
/*      */                     try
/*      */                     {
/* 1948 */                       i++;
/* 1949 */                       String eachToken = headerToken.nextToken();
/* 1950 */                       String[] header = eachToken.split("_sep_");
/*      */                       
/* 1952 */                       out.write("\n\t\t\t\t\t\t\t<tr id=\"customHeadersTr");
/* 1953 */                       out.print(i);
/* 1954 */                       out.write("\" class=\"trToRemove\">\n\t\t\t\t\t\t  \t\t<td colspan=\"2\">\n\t\t\t\t\t\t  \t\t<table cellspacing=\"0\" cellpadding=\"5\" border=\"0\" class=\"customHeadersTable\" width=\"100%\">\n\t\t\t\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" valign=\"top\"><input type=\"text\" size=\"35\" name=\"headerInput\" class=\"formtext\" value=\"");
/* 1955 */                       out.print(header[0]);
/* 1956 */                       out.write("\" />&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" valign=\"top\"><input type=\"text\" size=\"35\" name=\"headerInput\" class=\"formtext\" value=\"");
/* 1957 */                       out.print(header[1]);
/* 1958 */                       out.write("\" />&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" valign=\"middle\"><a href=\"javascript:void(0);\" class=\"removeFieldSelector\"><image src=\"images/icon_minus.gif\"></image></a>&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" valign=\"middle\"><a href=\"javascript:void(0);\" class=\"addFieldSelector\"><image src=\"images/icon_plus.gif\"></image></a>&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 1962 */                       e.printStackTrace();
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 else
/*      */                 {
/* 1968 */                   out.write("\n\t\t\t\t\t<tr id=\"customHeadersTr1\" class=\"trToRemove\">\n\t\t\t\t  \t\t<td colspan=\"2\" width=\"100%\">\n\t\t\t\t  \t\t<table cellspacing=\"0\" cellpadding=\"5\" border=\"0\" class=\"customHeadersTable\" width=\"100%\">\n\t\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" valign=\"top\"><input type=\"text\" name=\"headerInput\" class=\"formtext\" size=\"35\" />&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" valign=\"top\"><input type=\"text\" name=\"headerInput\" class=\"formtext\" size=\"35\" />&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" valign=\"middle\"><a href=\"javascript:void(0);\" class=\"removeFieldSelector\"><image src=\"images/icon_minus.gif\"></image></a>&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" valign=\"middle\"><a href=\"javascript:void(0);\" class=\"addFieldSelector\"><image src=\"images/icon_plus.gif\"></image></a>&nbsp;</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t \n\t\t\t\t ");
/*      */                 }
/*      */                 
/* 1971 */                 out.write(" \n</table></div></td></tr>\n");
/* 1972 */                 out.write("\n\t\t \n\t<!--");
/*      */                 
/* 1974 */                 IfTag _jspx_th_c_005fif_005f36 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1975 */                 _jspx_th_c_005fif_005f36.setPageContext(_jspx_page_context);
/* 1976 */                 _jspx_th_c_005fif_005f36.setParent(_jspx_th_c_005fif_005f30);
/*      */                 
/* 1978 */                 _jspx_th_c_005fif_005f36.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 1979 */                 int _jspx_eval_c_005fif_005f36 = _jspx_th_c_005fif_005f36.doStartTag();
/* 1980 */                 if (_jspx_eval_c_005fif_005f36 != 0) {
/*      */                   for (;;) {
/* 1982 */                     out.write("\n\t\t<tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 1983 */                     out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketcategory"));
/* 1984 */                     out.write("</td>\n\t\t  \t<td width=\"75%\">\n\t\t   \t\t");
/* 1985 */                     if (_jspx_meth_html_005ftext_005f1(_jspx_th_c_005fif_005f36, _jspx_page_context))
/*      */                       return;
/* 1987 */                     out.write("\n\t\t\t</td>\n\t\t</tr>\n\t  \n\t  <tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 1988 */                     out.print(FormatUtil.getString("Sub Category"));
/* 1989 */                     out.write("</td>\n\t\t  \t<td width=\"75%\">\n\t\t   \t\t<input type=\"text\" name=\"scategory\" class=\"formtext normal\" style=\"width:100%\"/>\n\t\t\t</td>\n\t\t</tr>\n\t\t\n\t\t<tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 1990 */                     out.print(FormatUtil.getString("Item"));
/* 1991 */                     out.write("</td>\n\t\t  \t<td width=\"75%\">\n\t\t   \t\t<input type=\"text\" name=\"item\" class=\"formtext normal\" style=\"width:100%\"/>\n\t\t\t</td>\n\t\t</tr>\n\t\t\n\t\t<tr>\n\t     \t<td class=\"bodytext label-align\" width=\"25%\">");
/* 1992 */                     out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketpriority"));
/* 1993 */                     out.write("</td>");
/* 1994 */                     out.write("\n\t\t\t<td width=\"75%\">\n\t\t    \t<select name=\"priority\" class=\"formtext normal\" size=\"1\" id=\"priorityId\">\n\t\t    \t\t<option value=\"Choose a Value\">");
/* 1995 */                     out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.choosevalue"));
/* 1996 */                     out.write("</option>");
/* 1997 */                     out.write("\n\t\t\t\t\t<option value=\"High\">");
/* 1998 */                     out.print(FormatUtil.getString("High"));
/* 1999 */                     out.write("</option>");
/* 2000 */                     out.write("\n\t\t\t\t\t<option value=\"Medium\">");
/* 2001 */                     out.print(FormatUtil.getString("Medium"));
/* 2002 */                     out.write("</option>");
/* 2003 */                     out.write("\n\t\t\t\t\t<option value=\"Normal\">");
/* 2004 */                     out.print(FormatUtil.getString("Normal"));
/* 2005 */                     out.write("</option>");
/* 2006 */                     out.write("\n\t\t\t\t\t<option value=\"Low\">");
/* 2007 */                     out.print(FormatUtil.getString("Low"));
/* 2008 */                     out.write("</option>");
/* 2009 */                     out.write("\n\t\t\t\t</select>\n\t\t  \t</td>\n\t  \t</tr>\n\t  \n\t\t<tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 2010 */                     out.print(FormatUtil.getString("am.webclient.sdp.group.text"));
/* 2011 */                     out.write("</td>\n\t\t\t<td width=\"75%\">\n\t\t\t\t");
/* 2012 */                     if (_jspx_meth_html_005ftext_005f2(_jspx_th_c_005fif_005f36, _jspx_page_context))
/*      */                       return;
/* 2014 */                     out.write("\n\t\t\t</td>\n\t\t</tr>\n\t  \n\t\t<tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 2015 */                     out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.tickettechnician"));
/* 2016 */                     out.write("</td>\n\t\t\t<td width=\"75%\">\n\t\t\t\t");
/* 2017 */                     if (_jspx_meth_html_005ftext_005f3(_jspx_th_c_005fif_005f36, _jspx_page_context))
/*      */                       return;
/* 2019 */                     out.write("\n\t\t\t</td>\n\t\t</tr>\n\t\t\n\t\t<tr>\n\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 2020 */                     out.print(FormatUtil.getString("Site"));
/* 2021 */                     out.write("</td>\n\t\t\t<td width=\"75%\">\n\t\t\t\t<input type=\"text\" name=\"site\"  class=\"formtext normal\" style=\"width:100%\"/>\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr>\n    \t<td height=\"25\"  class=\"bodytext\" colspan=\"2\"> <input type=\"checkbox\" name=\"customHeadersSupported\" class=\"customHeadersCSS\" />");
/* 2022 */                     out.print(FormatUtil.getString("Custom Fields - Incident Template"));
/* 2023 */                     out.write("</td>\n  \t</tr>\n\t<tr id=\"advanceOptionDiv\" style=\"display: none;\">\n\t\t<td class=\"bodytext\" width=\"100%\" colspan=\"2\">\t\n\t\t  \t<table border=\"0\"  cellpadding=\"5\" cellspacing=\"0\" id=\"tableToInsert\" width=\"100%\">\n\t\t  \t<tr>\n    \t\t\t<td height=\"25\" width=\"50%\" class=\"bodytext\" style=\"padding-left: 12%;font-weight: bold;\">");
/* 2024 */                     out.print(FormatUtil.getString("Field Name"));
/* 2025 */                     out.write("</td>\n    \t\t\t<td height=\"25\" width=\"50%\" class=\"bodytext\" align=\"left\" style=\"padding-left: 4%;font-weight: bold;\">");
/* 2026 */                     out.print(FormatUtil.getString("Field Value"));
/* 2027 */                     out.write("</td>\n  \t\t\t</tr>\n\t\t\t ");
/*      */                     
/* 2029 */                     if ((customHeaders != null) && (!customHeaders.equals("")))
/*      */                     {
/* 2031 */                       java.util.StringTokenizer headerToken = new java.util.StringTokenizer(customHeaders, "#");
/* 2032 */                       int i = 0;
/* 2033 */                       while (headerToken.hasMoreElements())
/*      */                       {
/*      */                         try
/*      */                         {
/* 2037 */                           i++;
/* 2038 */                           String eachToken = headerToken.nextToken();
/* 2039 */                           String[] header = eachToken.split("_sep_");
/*      */                           
/* 2041 */                           out.write("\n\t\t\t\t\t\t\t<tr id=\"customHeadersTr");
/* 2042 */                           out.print(i);
/* 2043 */                           out.write("\" class=\"trToRemove\">\n\t\t\t\t\t\t  \t\t<td colspan=\"2\">\n\t\t\t\t\t\t  \t\t<table cellspacing=\"0\" cellpadding=\"5\" border=\"0\" class=\"customHeadersTable\" width=\"100%\">\n\t\t\t\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" valign=\"top\"><input type=\"text\" size=\"35\" name=\"headerInput\" class=\"formtext\" value=\"");
/* 2044 */                           out.print(header[0]);
/* 2045 */                           out.write("\" />&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" valign=\"top\"><input type=\"text\" size=\"35\" name=\"headerInput\" class=\"formtext\" value=\"");
/* 2046 */                           out.print(header[1]);
/* 2047 */                           out.write("\" />&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" valign=\"middle\"><a href=\"javascript:void(0);\" class=\"removeFieldSelector\"><image src=\"images/icon_minus.gif\"></image></a>&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" valign=\"middle\"><a href=\"javascript:void(0);\" class=\"addFieldSelector\"><image src=\"images/icon_plus.gif\"></image></a>&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*      */                         }
/*      */                         catch (Exception e)
/*      */                         {
/* 2051 */                           e.printStackTrace();
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     else
/*      */                     {
/* 2057 */                       out.write("\n\t\t\t\t\t<tr id=\"customHeadersTr1\" class=\"trToRemove\">\n\t\t\t\t  \t\t<td colspan=\"2\" width=\"100%\">\n\t\t\t\t  \t\t<table cellspacing=\"0\" cellpadding=\"5\" border=\"0\" class=\"customHeadersTable\" width=\"100%\">\n\t\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" valign=\"top\"><input type=\"text\" name=\"headerInput\" class=\"formtext\" size=\"35\" />&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" valign=\"top\"><input type=\"text\" name=\"headerInput\" class=\"formtext\" size=\"35\" />&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" valign=\"middle\"><a href=\"javascript:void(0);\" class=\"removeFieldSelector\"><image src=\"images/icon_minus.gif\"></image></a>&nbsp;</td>\n\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" valign=\"middle\"><a href=\"javascript:void(0);\" class=\"addFieldSelector\"><image src=\"images/icon_plus.gif\"></image></a>&nbsp;</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t \n\t\t\t\t ");
/*      */                     }
/*      */                     
/* 2060 */                     out.write("  \t\n\t\t\t</table>\n\t\t</td>\n  \t</tr> \t\n\t");
/* 2061 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f36.doAfterBody();
/* 2062 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2066 */                 if (_jspx_th_c_005fif_005f36.doEndTag() == 5) {
/* 2067 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f36); return;
/*      */                 }
/*      */                 
/* 2070 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f36);
/* 2071 */                 out.write("\t-->\n\t</table>\n\t");
/* 2072 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/* 2073 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2077 */             if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/* 2078 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30); return;
/*      */             }
/*      */             
/* 2081 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 2082 */             out.write(10);
/* 2083 */             out.write(9);
/*      */             
/* 2085 */             IfTag _jspx_th_c_005fif_005f37 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2086 */             _jspx_th_c_005fif_005f37.setPageContext(_jspx_page_context);
/* 2087 */             _jspx_th_c_005fif_005f37.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 2089 */             _jspx_th_c_005fif_005f37.setTest("${product eq 'ServiceNow'}");
/* 2090 */             int _jspx_eval_c_005fif_005f37 = _jspx_th_c_005fif_005f37.doStartTag();
/* 2091 */             if (_jspx_eval_c_005fif_005f37 != 0) {
/*      */               for (;;) {
/* 2093 */                 out.write(10);
/* 2094 */                 out.write(9);
/* 2095 */                 out.write(9);
/* 2096 */                 out.write("\n<am:hiddenparam name=\"global\"/>\n\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"8\" border=\"0\">\n\t<tr>\n\t     <td class=\"bodytext label-align\" width=\"25%\">");
/* 2097 */                 out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketcategory"));
/* 2098 */                 out.write("</td>\n\t\t  <td width=\"75%\">\n\t\t    ");
/* 2099 */                 if (_jspx_meth_html_005fselect_005f9(_jspx_th_c_005fif_005f37, _jspx_page_context))
/*      */                   return;
/* 2101 */                 out.write("\n\t\t</td>\n\t\t\n\t</tr>\n\t<tr>\n\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 2102 */                 out.print(FormatUtil.getString("am.webclient.sdp.subcategory.text"));
/* 2103 */                 out.write("</td>\n\t\t<td width=\"75%\">\n\t\t");
/* 2104 */                 if (_jspx_meth_html_005fselect_005f10(_jspx_th_c_005fif_005f37, _jspx_page_context))
/*      */                   return;
/* 2106 */                 out.write("\n\t\t   <span id=\"loadingSubCateg\" ></span>\n\t\t</td>\n\t</tr>\n\t<tr>\n\t     <td class=\"bodytext label-align\" width=\"25%\">");
/* 2107 */                 out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketurgency"));
/* 2108 */                 out.write("</td>\n\t\t  <td width=\"75%\">\n\t\t    ");
/* 2109 */                 if (_jspx_meth_html_005fselect_005f11(_jspx_th_c_005fif_005f37, _jspx_page_context))
/*      */                   return;
/* 2111 */                 out.write("\n\t\t  </td>\n\t  </tr>\n\t  <tr>\n\t\t     <td class=\"bodytext label-align\" width=\"25%\">");
/* 2112 */                 out.print(FormatUtil.getString("am.webclient.sdp.group.text"));
/* 2113 */                 out.write("</td>\n\t\t\t  <td width=\"75%\">\n\t\t\t  ");
/* 2114 */                 if (_jspx_meth_html_005fselect_005f12(_jspx_th_c_005fif_005f37, _jspx_page_context))
/*      */                   return;
/* 2116 */                 out.write("\n\t\t\t  </td>\n\t </tr>\n\t<tr>\n\t\t <td class=\"bodytext label-align\" width=\"25%\">");
/* 2117 */                 out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.tickettechnician"));
/* 2118 */                 out.write("</td>\n\t\t  <td width=\"75%\">\n\t\t\t\t\t");
/* 2119 */                 if (_jspx_meth_html_005fselect_005f13(_jspx_th_c_005fif_005f37, _jspx_page_context))
/*      */                   return;
/* 2121 */                 out.write("\n\t\t\t\t\t<span id=\"loadingTechnician\"></span>\n\t\t  </td>\n\t</tr>\n</table>\n<script>\nfunction getSubCategoriesForServiceNowAjax()\n{\n\tvar formattedData = \" \";\n\t$(\"#loadingSubCateg\").append(\"<img src=\\\"/images/LoadingTC.gif\\\"/>\");//NO I18N\n\t$.ajax(\n\t\t{\n\t\t\turl: \"/adminAction.do?method=getServiceNowSubCategories\",//NO I18N\n\t\t\tasync: \"false\",//NO I18N\n\t\t\ttype: \"GET\",//NO I18N\n\t\t\tdata: \"category=\"+encodeURIComponent(document.AMActionForm.category.value),//NO I18N\n\t\t\tsuccess: function(respJSON)\n\t\t\t{\n\t\t\t\tvar dataJSON = respJSON;\n\t\t\t\tvar subCategories = $.parseJSON(JSON.stringify(dataJSON)).subCategories;\n\t\t\t\tformattedData = \"<select name='scategory'>\";//No I18N\n\t\n\t\t\t$.each(subCategories,function(i,val)\n\t\t\t{\n\t\t\t\tvar innerJSON = $.parseJSON(JSON.stringify(val));\n\t\t\t\tvar label = innerJSON.label;\n\t\t\t\tvar value = innerJSON.value;\n\t\t\n\t\t\t\tif(trimAll(label)!='' && trimAll(value)!='')\n\t\t\t\t{\n\t\t\t\t\tformattedData+=\"<option value='\"+value+\"'>\"+label+\"</option>\"\n\t\t\t\t}\n\t\t\t});\n\t\t\tformattedData += \"</select>\"\n\t\t\tdocument.getElementById(\"subcategory\").innerHTML=formattedData;\n");
/* 2122 */                 out.write("\t\t\t\n\t\t\t}\n\t\t}).done( function(){\n\t\t$(\"#loadingSubCateg img:last-child\").remove();\n\t\t\tdocument.getElementById(\"subcategory\").style.display='block';\n\t\t});\n}\nfunction getTechnicians()\n{\n\tvar formattedData = \" \";\n\t$(\"#loadingTechnician\").append(\"<img src=\\\"/images/LoadingTC.gif\\\"/>\");//NO I18N\n\t$.ajax(\n\t\t{\n\t\t\turl: \"/adminAction.do?method=getAssignedToTechnicians\",//NO I18N\n\t\t\tasync: \"false\",//NO I18N\n\t\t\ttype: \"GET\",//NO I18N\n\t\t\tdata: \"groupId=\"+encodeURIComponent(document.AMActionForm.group.value),//NO I18N\n\t\t\tsuccess: function(respJSON)\n\t\t\t{\n\t\t\t\tvar dataJSON = respJSON;\n\t\t\t\tvar technicians = $.parseJSON(JSON.stringify(dataJSON)).technicians;\n\t\t\t\tformattedData = \"<select name='technician'>\";//No I18N\n\t\n\t\t\t$.each(technicians,function(i,val)\n\t\t\t{\n\t\t\t\tvar innerJSON = $.parseJSON(JSON.stringify(val));\n\t\t\t\tvar label = innerJSON.label;\n\t\t\t\tvar value = innerJSON.value;\n\t\t\n\t\t\t\tif(trimAll(label)!='' && trimAll(value)!='')\n\t\t\t\t{\n\t\t\t\t\tformattedData+=\"<option value='\"+value+\"'>\"+label+\"</option>\";//NO I18N\n\t\t\t\t}\n\t\t\t});\n\t\t\tformattedData += \"</select>\"\n");
/* 2123 */                 out.write("\t\t\tdocument.getElementById(\"technician\").innerHTML=formattedData;\n\t\t\t\n\t\t\t}\n\t\t}).done( function(){\n\t\t$(\"#loadingTechnician img:last-child\").remove();\n\t\t\tdocument.getElementById(\"technician\").style.display='block';\n\t\t});\n}\n</script>");
/* 2124 */                 out.write(10);
/* 2125 */                 out.write(9);
/* 2126 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f37.doAfterBody();
/* 2127 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2131 */             if (_jspx_th_c_005fif_005f37.doEndTag() == 5) {
/* 2132 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f37); return;
/*      */             }
/*      */             
/* 2135 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f37);
/* 2136 */             out.write("\n</div>\n");
/*      */             
/* 2138 */             IfTag _jspx_th_c_005fif_005f38 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2139 */             _jspx_th_c_005fif_005f38.setPageContext(_jspx_page_context);
/* 2140 */             _jspx_th_c_005fif_005f38.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 2142 */             _jspx_th_c_005fif_005f38.setTest("${AMActionForm.mspDesk eq true}");
/* 2143 */             int _jspx_eval_c_005fif_005f38 = _jspx_th_c_005fif_005f38.doStartTag();
/* 2144 */             if (_jspx_eval_c_005fif_005f38 != 0) {
/*      */               for (;;) {
/* 2146 */                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" >\n\t <tr>\n\t  \t\t<td class=\"bodytext label-align\" width=\"25%\">");
/* 2147 */                 out.print(FormatUtil.getString("am.webclient.newaction.logaticket.requestername"));
/* 2148 */                 out.write("<span class=\"mandatory\">*</span></td>\n\t  \t\t<td width=\"75%\">\n\t\t\t\t<div id=\"dummyReqs\" style=\"DISPLAY: block;\">\n\t\t\t\t\t<!-- dropdown changed to textfield to avoid the issues when huge no of requesters imported via AD -->\n\t\t\t\t\t<!--\n\t\t\t\t\t");
/* 2149 */                 if (_jspx_meth_html_005fselect_005f14(_jspx_th_c_005fif_005f38, _jspx_page_context))
/*      */                   return;
/* 2151 */                 out.write("\n\t\t\t\t\t -->\n\t\t\t\t\t ");
/* 2152 */                 if (_jspx_meth_html_005ftext_005f4(_jspx_th_c_005fif_005f38, _jspx_page_context))
/*      */                   return;
/* 2154 */                 out.write("\n\t\t\t\t</div>\n\t\t\t<div id=\"requesters\" style=\"DISPLAY: none;\"></div>\n\t\t\t</td>\n\t </tr>\t\n</table>\n");
/* 2155 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f38.doAfterBody();
/* 2156 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2160 */             if (_jspx_th_c_005fif_005f38.doEndTag() == 5) {
/* 2161 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f38); return;
/*      */             }
/*      */             
/* 2164 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f38);
/* 2165 */             out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" >\t \n\t <tr bgcolor=\"#efefef\">\n\t     <td colspan=\"2\" class=\"bodytext\"><b>");
/* 2166 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketcontent"));
/* 2167 */             out.write("</b>\n\t     </td>\n\t </tr>\n\t <tr class=\"itadmin-hide\">\n\t    <td class=\"bodytext\">");
/* 2168 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.tickettitle"));
/* 2169 */             out.write("</td>\n\t </tr>\t\n\t <tr bgcolor=\"#efefef\">\n\t <td width=\"65%\">");
/* 2170 */             if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2172 */             out.write("</td>\n\t <td align=\"left\" width=\"35%\">\n\t   <select name=\"titleList\" Class=\"formtext medium\" onChange=\"javascript:addTitle('0')\">\n\t    <option value=\"alarm\">");
/* 2173 */             out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.selectalarm"));
/* 2174 */             out.write("</option>\n\t    <option value=\"$HOSTNAME\">");
/* 2175 */             out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2176 */             out.write("</option>\n\t    <option value=\"$PORT\">");
/* 2177 */             out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.serverport"));
/* 2178 */             out.write("</option>\n\t    <option value=\"$MONITORNAME\">");
/* 2179 */             out.print(FormatUtil.getString("am.webclient.configurealert.monitorname"));
/* 2180 */             out.write("</option>\n\t    <option value=\"$ATTRIBUTE\">");
/* 2181 */             out.print(FormatUtil.getString("am.webclient.wmi.attributes.text"));
/* 2182 */             out.write("</option>\n\t     <option value=\"$ATTRIBUTEVALUE\">");
/* 2183 */             out.print(FormatUtil.getString("am.webclient.ticketaction.customtag.attributevalue.text"));
/* 2184 */             out.write("</option>\n\t <option value=\"$THRESHOLDMESSAGE\">");
/* 2185 */             out.print(FormatUtil.getString("am.webclient.ticketaction.customtag.thresholdmsg.text"));
/* 2186 */             out.write("</option>\n\t ");
/* 2187 */             if (EnterpriseUtil.isIt360MSPEdition) {
/* 2188 */               out.write("\n\t     <option value=\"$CUSTOMERNAME\">");
/* 2189 */               out.print(FormatUtil.getString("am.webclient.ticketaction.customtag.customername.text"));
/* 2190 */               out.write("</option>\n\t     <option value=\"$SITENAME\">");
/* 2191 */               out.print(FormatUtil.getString("am.webclient.ticketaction.customtag.sitename.text"));
/* 2192 */               out.write("</option>\n\t ");
/*      */             }
/* 2194 */             out.write("  \n\t\t<option value=\"$SEVERITY\">");
/* 2195 */             out.print(FormatUtil.getString("webclient.fault.details.properties.severity"));
/* 2196 */             out.write("</option>\t\t\t   \n\t\t<option value=\"$ENTITY\">");
/* 2197 */             out.print(FormatUtil.getString("webclient.fault.details.properties.entity.text"));
/* 2198 */             out.write("</option>\n\t    <option value=\"$SEVERITYASNUMBER\">");
/* 2199 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.severitynumber"));
/* 2200 */             out.write(" </option>\n\t\t<option value=\"$MONITORTYPE\">");
/* 2201 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 2202 */             out.write("</option>\n\t\t<option value=\"$HOSTIP\">");
/* 2203 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.hostipadd"));
/* 2204 */             out.write("</option>\n\t    <option value=\"$OBJECTNAME\">");
/* 2205 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.objectname"));
/* 2206 */             out.write(" </option>\n\t\t<option value=\"$RCAMESSAGE\">");
/* 2207 */             out.print(FormatUtil.getString("webclient.fault.details.properties.message"));
/* 2208 */             out.write(" </option>\n\t   </select>\n      </td>\n \t </tr>\t\n\t <tr>\n\t     <td class=\"bodytext\">");
/* 2209 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketbody"));
/* 2210 */             out.write("</td>\n\t </tr>\t \n\t <tr bgcolor=\"#efefef\">\n\t <td width=\"65%\">");
/* 2211 */             if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2213 */             out.write("</td>\n\t <td align=\"left\" width=\"35%\">\n\t <select name=\"descList\" Class=\"formtext medium\" onChange=\"javascript:addTitle('1')\">\n\t <option value=\"alarm\">");
/* 2214 */             out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.selectalarm"));
/* 2215 */             out.write("</option>\n\t <option value=\"$HOSTNAME\">");
/* 2216 */             out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2217 */             out.write("</option>\n\t <option value=\"$PORT\">");
/* 2218 */             out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.serverport"));
/* 2219 */             out.write("</option>\n\t <option value=\"$MONITORNAME\">");
/* 2220 */             out.print(FormatUtil.getString("am.webclient.configurealert.monitorname"));
/* 2221 */             out.write("</option>\n\t <option value=\"$ATTRIBUTE\">");
/* 2222 */             out.print(FormatUtil.getString("am.webclient.wmi.attributes.text"));
/* 2223 */             out.write("</option>\n\t  <option value=\"$ATTRIBUTEVALUE\">");
/* 2224 */             out.print(FormatUtil.getString("am.webclient.ticketaction.customtag.attributevalue.text"));
/* 2225 */             out.write("</option>\n\t <option value=\"$THRESHOLDMESSAGE\">");
/* 2226 */             out.print(FormatUtil.getString("am.webclient.ticketaction.customtag.thresholdmsg.text"));
/* 2227 */             out.write("</option>\n\t ");
/* 2228 */             if (EnterpriseUtil.isIt360MSPEdition) {
/* 2229 */               out.write("\n\t     <option value=\"$CUSTOMERNAME\">");
/* 2230 */               out.print(FormatUtil.getString("am.webclient.ticketaction.customtag.customername.text"));
/* 2231 */               out.write("</option>\n\t     <option value=\"$SITENAME\">");
/* 2232 */               out.print(FormatUtil.getString("am.webclient.ticketaction.customtag.sitename.text"));
/* 2233 */               out.write("</option>\n\t ");
/*      */             }
/* 2235 */             out.write("  \n\t <option value=\"$SEVERITY\">");
/* 2236 */             out.print(FormatUtil.getString("webclient.fault.details.properties.severity"));
/* 2237 */             out.write("</option>        \n\t <option value=\"$ENTITY\">");
/* 2238 */             out.print(FormatUtil.getString("webclient.fault.details.properties.entity.text"));
/* 2239 */             out.write("</option>\n\t <option value=\"$SEVERITYASNUMBER\">");
/* 2240 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.severitynumber"));
/* 2241 */             out.write(" </option>\n\t <option value=\"$MONITORTYPE\">");
/* 2242 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 2243 */             out.write("</option>\n\t <option value=\"$HOSTIP\">");
/* 2244 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.hostipadd"));
/* 2245 */             out.write("</option>\n\t <option value=\"$OBJECTNAME\">");
/* 2246 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.objectname"));
/* 2247 */             out.write(" </option>\n\t <option value=\"$RCAMESSAGE\">");
/* 2248 */             out.print(FormatUtil.getString("webclient.fault.details.properties.message"));
/* 2249 */             out.write(" </option>\n\t  </select>\n\t </td>\n\t </tr>\n\t ");
/*      */             
/* 2251 */             IfTag _jspx_th_c_005fif_005f39 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2252 */             _jspx_th_c_005fif_005f39.setPageContext(_jspx_page_context);
/* 2253 */             _jspx_th_c_005fif_005f39.setParent(_jspx_th_html_005fform_005f0);
/*      */             
/* 2255 */             _jspx_th_c_005fif_005f39.setTest("${AMActionForm.ticketingType ne 'restapi'}");
/* 2256 */             int _jspx_eval_c_005fif_005f39 = _jspx_th_c_005fif_005f39.doStartTag();
/* 2257 */             if (_jspx_eval_c_005fif_005f39 != 0) {
/*      */               for (;;) {
/* 2259 */                 out.write("\n\t  <tr>\n          <td colspan=\"3\">\n          \t<table width=\"100%\" cellpadding=\"8\">\n            \t<tr>\n                \t<td class=\"bodytext label-align\" width=\"25%\">");
/* 2260 */                 out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketformat"));
/* 2261 */                 out.write("</td>\n\t   <td class=\"bodytext\" width=\"75%\">");
/* 2262 */                 if (_jspx_meth_html_005fradio_005f2(_jspx_th_c_005fif_005f39, _jspx_page_context))
/*      */                   return;
/* 2264 */                 out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketformatplain"));
/* 2265 */                 out.write("&nbsp;&nbsp;&nbsp;&nbsp;");
/* 2266 */                 if (_jspx_meth_html_005fradio_005f3(_jspx_th_c_005fif_005f39, _jspx_page_context))
/*      */                   return;
/* 2268 */                 out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketformathtml"));
/* 2269 */                 out.write("&nbsp;&nbsp;&nbsp;&nbsp;");
/* 2270 */                 if (_jspx_meth_html_005fradio_005f4(_jspx_th_c_005fif_005f39, _jspx_page_context))
/*      */                   return;
/* 2272 */                 out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketformatboth"));
/* 2273 */                 out.write("</td>\n                </tr>\n            </table>\n          </td>\n\t  </tr>\n\t  ");
/* 2274 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f39.doAfterBody();
/* 2275 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2279 */             if (_jspx_th_c_005fif_005f39.doEndTag() == 5) {
/* 2280 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f39); return;
/*      */             }
/*      */             
/* 2283 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f39);
/* 2284 */             out.write("\n\t  <tr>\n      \t<td colspan=\"3\">\n          \t<table width=\"100%\" cellpadding=\"8\">\n            \t<tr>\n                \t<td class=\"bodytext label-align\" width=\"25%\">");
/* 2285 */             out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketappendmessage"));
/* 2286 */             out.write("</td>\n\t   <td  class=\"bodytext\" width=\"75%\">");
/* 2287 */             if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2289 */             out.write("</td>                \n                </tr>\n            </table>\n        </td>\n\t  </tr>\n\t\n \t  <tr>\n\t\t<td class=\"bodytext\" colspan=\"3\">  ");
/* 2290 */             if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/* 2292 */             out.write(" </td>\n\t  </tr>\n\t\t\n</table></td>\n</tr>\n</table>\n\t\t\t\t\t\t  \n\t \t\t  \n\n<table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"lrbborder\">\n <tr>\n\t<td class=\"tablebottom\" width=\"25%\">&nbsp;</td>\n ");
/*      */             
/* 2294 */             String methodToCall = (String)request.getAttribute("SdeskLogTicketConfig");
/* 2295 */             if ((methodToCall == "SdeskLogTicketConfig") || (methodToCall == "ServiceNowTicketConfig"))
/*      */             {
/*      */ 
/* 2298 */               out.write("\n     <td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 2299 */               out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketcreate"));
/* 2300 */               out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"fnFormSubmit();\">\n\t ");
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/* 2305 */               request.setAttribute("toUpdate", "update");
/*      */               
/* 2307 */               out.write("\n    <input type=\"hidden\" name=\"toUpdate\" value=\"update\">   \n\t<input type=\"hidden\" name=\"ID\" value=\"");
/* 2308 */               out.print(request.getAttribute("ID"));
/* 2309 */               out.write("\">\n   \t<td width=\"75%\" height=\"31\" class=\"tablebottom\" ><input name=\"Submit\" value=\"");
/* 2310 */               out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketupdate"));
/* 2311 */               out.write("\" type=\"button\" class=\"buttons btn_highlt\" onClick=\"fnFormSubmit();\">\n\t");
/*      */             }
/* 2313 */             out.write("\n\t<input type=\"reset\" align=\"center\" class=\"buttons btn_reset\" value=\"");
/* 2314 */             out.print(FormatUtil.getString("am.webclient.common.reset.text"));
/* 2315 */             out.write("\">\n\t");
/*      */             
/* 2317 */             ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2318 */             _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 2319 */             _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_html_005fform_005f0);
/* 2320 */             int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 2321 */             if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */               for (;;) {
/* 2323 */                 out.write("\n    ");
/*      */                 
/* 2325 */                 WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2326 */                 _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 2327 */                 _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                 
/* 2329 */                 _jspx_th_c_005fwhen_005f6.setTest("${param.global!='true'}");
/* 2330 */                 int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 2331 */                 if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                   for (;;) {
/* 2333 */                     out.write("\t\n\t<input type=\"button\" align=\"center\" class=\"buttons btn_link\" value=\"");
/* 2334 */                     out.print(FormatUtil.getString("am.webclient.admintab.ticketaction.ticketcancel"));
/* 2335 */                     out.write("\" onClick=\"javascript:history.back();\"></td>\n    ");
/* 2336 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 2337 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2341 */                 if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 2342 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                 }
/*      */                 
/* 2345 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2346 */                 out.write("\n    ");
/*      */                 
/* 2348 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2349 */                 _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 2350 */                 _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 2351 */                 int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 2352 */                 if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                   for (;;) {
/* 2354 */                     out.write("\n    <input type=\"button\" align=\"center\" class=\"buttons btn_link\" value=\"");
/* 2355 */                     out.print(FormatUtil.getString("webclient.fault.alarmdetails.button.close"));
/* 2356 */                     out.write("\" onClick=\"javascript:window.parent.close()\"></td>\n    ");
/* 2357 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 2358 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2362 */                 if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 2363 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                 }
/*      */                 
/* 2366 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 2367 */                 out.write("\n    ");
/* 2368 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 2369 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2373 */             if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 2374 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */             }
/*      */             
/* 2377 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 2378 */             out.write("\n </tr>\n</table>\n</td>\n<td width=\"30%\" valign=\"top\">\n");
/*      */             
/* 2380 */             ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2381 */             _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 2382 */             _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_html_005fform_005f0);
/* 2383 */             int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 2384 */             if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */               for (;;) {
/* 2386 */                 out.write(10);
/*      */                 
/* 2388 */                 WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2389 */                 _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 2390 */                 _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                 
/* 2392 */                 _jspx_th_c_005fwhen_005f7.setTest("${product ne 'ServiceNow'}");
/* 2393 */                 int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 2394 */                 if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                   for (;;) {
/* 2396 */                     out.write(10);
/* 2397 */                     out.write(9);
/*      */                     
/* 2399 */                     ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2400 */                     _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 2401 */                     _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_c_005fwhen_005f7);
/* 2402 */                     int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 2403 */                     if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                       for (;;) {
/* 2405 */                         out.write(10);
/* 2406 */                         out.write(9);
/* 2407 */                         out.write(9);
/*      */                         
/* 2409 */                         WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2410 */                         _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 2411 */                         _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                         
/* 2413 */                         _jspx_th_c_005fwhen_005f8.setTest("${AMActionForm.ticketingType ne 'restapi'}");
/* 2414 */                         int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 2415 */                         if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                           for (;;) {
/* 2417 */                             out.write("\n\t\t\t");
/* 2418 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.logaticket.credential.helpcard")), request.getCharacterEncoding()), out, false);
/* 2419 */                             out.write(10);
/* 2420 */                             out.write(9);
/* 2421 */                             out.write(9);
/* 2422 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 2423 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2427 */                         if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 2428 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                         }
/*      */                         
/* 2431 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 2432 */                         out.write(10);
/* 2433 */                         out.write(9);
/* 2434 */                         out.write(9);
/*      */                         
/* 2436 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2437 */                         _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 2438 */                         _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f8);
/* 2439 */                         int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 2440 */                         if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                           for (;;) {
/* 2442 */                             out.write("\n\t\t\t");
/* 2443 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.logaticket.api.helpcard")), request.getCharacterEncoding()), out, false);
/* 2444 */                             out.write(10);
/* 2445 */                             out.write(9);
/* 2446 */                             out.write(9);
/* 2447 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 2448 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2452 */                         if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 2453 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                         }
/*      */                         
/* 2456 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 2457 */                         out.write(10);
/* 2458 */                         out.write(9);
/* 2459 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 2460 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2464 */                     if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 2465 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                     }
/*      */                     
/* 2468 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 2469 */                     out.write(10);
/* 2470 */                     out.write(9);
/* 2471 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 2472 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2476 */                 if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 2477 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                 }
/*      */                 
/* 2480 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 2481 */                 out.write(10);
/* 2482 */                 out.write(9);
/*      */                 
/* 2484 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2485 */                 _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 2486 */                 _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f7);
/* 2487 */                 int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 2488 */                 if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                   for (;;) {
/* 2490 */                     out.write(10);
/* 2491 */                     out.write(9);
/* 2492 */                     org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.logaticket.servicenow.helpcard")), request.getCharacterEncoding()), out, false);
/* 2493 */                     out.write(10);
/* 2494 */                     out.write(9);
/* 2495 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 2496 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2500 */                 if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 2501 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                 }
/*      */                 
/* 2504 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 2505 */                 out.write(10);
/* 2506 */                 out.write(9);
/* 2507 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 2508 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2512 */             if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 2513 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */             }
/*      */             
/* 2516 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 2517 */             out.write("\n</td>\n</tr>\n");
/* 2518 */             out.write("\n\n </table>\n");
/* 2519 */             int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2520 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2524 */         if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2525 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */         }
/*      */         
/* 2528 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2529 */         out.write(" </tiles:put> <tiles:put name=\"footer\" value=\"/jsp/footer.jsp\"/>\n</tiles:insert>\n\n</body>\n\n<script>\n\njQuery(document).ready(function()\n{\t\n\t\t");
/* 2530 */         if (_jspx_meth_c_005fif_005f40(_jspx_page_context))
/*      */           return;
/* 2532 */         out.write("\n\t\n\t\t");
/* 2533 */         if (_jspx_meth_c_005fif_005f41(_jspx_page_context))
/*      */           return;
/* 2535 */         out.write("\n\t\n\t\t");
/* 2536 */         if (_jspx_meth_c_005fif_005f42(_jspx_page_context))
/*      */           return;
/* 2538 */         out.write("\n\t\n\t\t");
/* 2539 */         if (_jspx_meth_c_005fif_005f43(_jspx_page_context))
/*      */           return;
/* 2541 */         out.write("\n\t\t\n\t\t");
/*      */         
/* 2543 */         IfTag _jspx_th_c_005fif_005f44 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2544 */         _jspx_th_c_005fif_005f44.setPageContext(_jspx_page_context);
/* 2545 */         _jspx_th_c_005fif_005f44.setParent(null);
/*      */         
/* 2547 */         _jspx_th_c_005fif_005f44.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 2548 */         int _jspx_eval_c_005fif_005f44 = _jspx_th_c_005fif_005f44.doStartTag();
/* 2549 */         if (_jspx_eval_c_005fif_005f44 != 0) {
/*      */           for (;;) {
/* 2551 */             out.write("\n\t\tjQuery('td').find(\"input:text[name='scategory']\").val('");
/* 2552 */             out.print(sdcategory);
/* 2553 */             out.write("');\n\t\tjQuery('td').find(\"input:text[name='item']\").val('");
/* 2554 */             out.print(sdscategory);
/* 2555 */             out.write("');\n\t\tjQuery('td').find(\"input:text[name='site']\").val('");
/* 2556 */             out.print(siteInfo);
/* 2557 */             out.write("');\n\t\n\t\t");
/* 2558 */             int evalDoAfterBody = _jspx_th_c_005fif_005f44.doAfterBody();
/* 2559 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2563 */         if (_jspx_th_c_005fif_005f44.doEndTag() == 5) {
/* 2564 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f44); return;
/*      */         }
/*      */         
/* 2567 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f44);
/* 2568 */         out.write("\n\n\t\tvar idCount = $(\".customHeadersTable\").length;\n\t\tif(idCount > 0 && ");
/* 2569 */         out.print((customHeaders != null) && (!customHeaders.equals("")));
/* 2570 */         out.write(" )\n\t\t{\n\t\t\t$('.customHeadersCSS').attr('checked','true'); // NO I18N\n\t\t\t$('#advanceOptionDiv').slideDown();\n\t\t}\n\t\n\t\t$('.addFieldSelector').click(function() //NO I18N \n\t\t{\n\t\t\tidCount++;\n\t\t\tvar actualObj = $(this).closest('.trToRemove'); //NO I18N\n\t\t\tvar clonedObj = $(actualObj).clone(true);\n\t\t\t$(clonedObj).find(\"input:text[name='headerInput']\").val(\"\"); //NO I18N\n\t\t\tclonedObj.attr('id', 'customHeadersTr'+idCount);//NO I18N\n\t\t\tclonedObj.insertAfter(actualObj);\n\t\t}),\n\t\t$('.removeFieldSelector').click(function() //NO I18N \n\t\t{\n\t\t\tif($(\".customHeadersTable\").length > 1)\n\t\t\t{\n\t\t\t\t$(this).closest('.trToRemove').remove(); // NO I18N\n\t\t\t}\n\t\t}),\n\t\t$('.customHeadersCSS').click(function() //NO I18N \n\t\t\t{\n\t\t\tif($(this).is(':checked'))\n\t\t\t{\n\t\t\t\t$('#advanceOptionDiv').show(\"slow\"); //NO I18N\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\t$('#advanceOptionDiv').hide(\"slow\"); //NO I18N\n\t\t\t}\n\t\t})\n});\n\n\t");
/* 2571 */         out.write("\n\t\n\t\t");
/* 2572 */         out.write("\n\t\n\t\t");
/* 2573 */         if (_jspx_meth_c_005fif_005f45(_jspx_page_context))
/*      */           return;
/* 2575 */         out.write("\n\n</script>\n\n\n");
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/* 2580 */         e.printStackTrace();
/*      */       }
/*      */       
/* 2583 */       out.write(10);
/*      */     } catch (Throwable t) {
/* 2585 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2586 */         out = _jspx_out;
/* 2587 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2588 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2589 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2592 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2598 */     PageContext pageContext = _jspx_page_context;
/* 2599 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2601 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2602 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2603 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2605 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2607 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2608 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2609 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2610 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2611 */       return true;
/*      */     }
/* 2613 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2614 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2619 */     PageContext pageContext = _jspx_page_context;
/* 2620 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2622 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2623 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2624 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 2626 */     _jspx_th_c_005fif_005f0.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 2627 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2628 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 2630 */         out.write("\n\tURL+=\"&API=true\";//No I18N\n\t");
/* 2631 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2632 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2636 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2637 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2638 */       return true;
/*      */     }
/* 2640 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2646 */     PageContext pageContext = _jspx_page_context;
/* 2647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2649 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2650 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2651 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2652 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2653 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 2655 */         out.write("\n\t\t\t");
/* 2656 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2657 */           return true;
/* 2658 */         out.write("\n\t\t\t");
/* 2659 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 2660 */           return true;
/* 2661 */         out.write(10);
/* 2662 */         out.write(9);
/* 2663 */         out.write(9);
/* 2664 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2665 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2669 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2670 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2671 */       return true;
/*      */     }
/* 2673 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2674 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2679 */     PageContext pageContext = _jspx_page_context;
/* 2680 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2682 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2683 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2684 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 2686 */     _jspx_th_c_005fwhen_005f0.setTest("${hideFields eq 'true'}");
/* 2687 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2688 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 2690 */         out.write("\n\t\t\t\tdocument.getElementById(\"noTemplateDiv\").style.display='none';\n\t\t\t");
/* 2691 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2692 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2696 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2697 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2698 */       return true;
/*      */     }
/* 2700 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2701 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2706 */     PageContext pageContext = _jspx_page_context;
/* 2707 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2709 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2710 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2711 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 2712 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2713 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 2715 */         out.write("\n\t\t\t\tdocument.getElementById(\"noTemplateDiv\").style.display='block';\n\t\t\t");
/* 2716 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2717 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2721 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2722 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2723 */       return true;
/*      */     }
/* 2725 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2726 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2731 */     PageContext pageContext = _jspx_page_context;
/* 2732 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2734 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2735 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2736 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/* 2738 */     _jspx_th_c_005fif_005f1.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 2739 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2740 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2742 */         out.write("\n\tURL+=\"&API=true\";//No I18N\n\t");
/* 2743 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2744 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2748 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2749 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2750 */       return true;
/*      */     }
/* 2752 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2753 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2758 */     PageContext pageContext = _jspx_page_context;
/* 2759 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2761 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2762 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2763 */     _jspx_th_c_005fif_005f2.setParent(null);
/*      */     
/* 2765 */     _jspx_th_c_005fif_005f2.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 2766 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2767 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 2769 */         out.write("\n\tURL+=\"&API=true\";//No I18N\n\t");
/* 2770 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2771 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2775 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2776 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2777 */       return true;
/*      */     }
/* 2779 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2780 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2785 */     PageContext pageContext = _jspx_page_context;
/* 2786 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2788 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2789 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2790 */     _jspx_th_c_005fif_005f3.setParent(null);
/*      */     
/* 2792 */     _jspx_th_c_005fif_005f3.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 2793 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2794 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 2796 */         out.write("\n\t\tURL+=\"&API=true\";//No I18N\n\t\t");
/* 2797 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2798 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2802 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2803 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2804 */       return true;
/*      */     }
/* 2806 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2812 */     PageContext pageContext = _jspx_page_context;
/* 2813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2815 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2816 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2817 */     _jspx_th_c_005fif_005f4.setParent(null);
/*      */     
/* 2819 */     _jspx_th_c_005fif_005f4.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 2820 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2821 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 2823 */         out.write("\n\t\tURL+=\"&API=true\";//No I18N\n\t\t");
/* 2824 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2825 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2829 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2830 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2831 */       return true;
/*      */     }
/* 2833 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2834 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2839 */     PageContext pageContext = _jspx_page_context;
/* 2840 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2842 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2843 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2844 */     _jspx_th_c_005fif_005f5.setParent(null);
/*      */     
/* 2846 */     _jspx_th_c_005fif_005f5.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 2847 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2848 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 2850 */         out.write("\n\t\tURL+=\"&API=true\";//No I18N\n\t\t");
/* 2851 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2852 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2856 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2857 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2858 */       return true;
/*      */     }
/* 2860 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2861 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2866 */     PageContext pageContext = _jspx_page_context;
/* 2867 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2869 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2870 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2871 */     _jspx_th_c_005fif_005f6.setParent(null);
/*      */     
/* 2873 */     _jspx_th_c_005fif_005f6.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 2874 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2875 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 2877 */         out.write("\n\t\tURL+=\"&API=true\";//No I18N\n\t\t");
/* 2878 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2879 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2883 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2884 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2885 */       return true;
/*      */     }
/* 2887 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2893 */     PageContext pageContext = _jspx_page_context;
/* 2894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2896 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2897 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2898 */     _jspx_th_c_005fif_005f7.setParent(null);
/*      */     
/* 2900 */     _jspx_th_c_005fif_005f7.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 2901 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2902 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 2904 */         out.write("\n\t\tURL+=\"&API=true\";//No I18N\n\t\t");
/* 2905 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2906 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2910 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2911 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2912 */       return true;
/*      */     }
/* 2914 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2920 */     PageContext pageContext = _jspx_page_context;
/* 2921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2923 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2924 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 2925 */     _jspx_th_c_005fif_005f10.setParent(null);
/*      */     
/* 2927 */     _jspx_th_c_005fif_005f10.setTest("${AMActionForm.ticketingType eq 'restapi'}");
/* 2928 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 2929 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 2931 */         out.write("\n\t\t\t\tlabel=label.substring(label.indexOf(\"_\")+1);\n\t\t\t");
/* 2932 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 2933 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2937 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 2938 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2939 */       return true;
/*      */     }
/* 2941 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 2942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2947 */     PageContext pageContext = _jspx_page_context;
/* 2948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2950 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2951 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2952 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 2954 */     _jspx_th_c_005fout_005f1.setValue("${AMActionForm.ticketingType}");
/* 2955 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2956 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2957 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2958 */       return true;
/*      */     }
/* 2960 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2961 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2966 */     PageContext pageContext = _jspx_page_context;
/* 2967 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2969 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2970 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2971 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2973 */     _jspx_th_c_005fif_005f11.setTest("${globalconfig['mailserverconfigured'] != 'true'}");
/* 2974 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2975 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 2977 */         out.write("\n\t\t\tmyOnLoad();\n\t\t");
/* 2978 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2979 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2983 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2984 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2985 */       return true;
/*      */     }
/* 2987 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2988 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2993 */     PageContext pageContext = _jspx_page_context;
/* 2994 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2996 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2997 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 2998 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3000 */     _jspx_th_c_005fif_005f12.setTest("${globalconfig['mailserverconfigured'] != 'true' && param.checkForMailSetting eq 'true'}");
/* 3001 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3002 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 3004 */         out.write("\n\t\t\t\tmyOnLoad();\n\t\t\t");
/* 3005 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3006 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3010 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3011 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3012 */       return true;
/*      */     }
/* 3014 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3015 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3020 */     PageContext pageContext = _jspx_page_context;
/* 3021 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3023 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3024 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3025 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 3027 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3028 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3030 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3031 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3033 */           out.write(" \n      ");
/* 3034 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3035 */             return true;
/* 3036 */           out.write(32);
/* 3037 */           out.write(10);
/* 3038 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3039 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3043 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3044 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3047 */         int tmp191_190 = 0; int[] tmp191_188 = _jspx_push_body_count_c_005fcatch_005f0; int tmp193_192 = tmp191_188[tmp191_190];tmp191_188[tmp191_190] = (tmp193_192 - 1); if (tmp193_192 <= 0) break;
/* 3048 */         out = _jspx_page_context.popBody(); }
/* 3049 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3051 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3052 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3054 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 3059 */     PageContext pageContext = _jspx_page_context;
/* 3060 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3062 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 3063 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 3064 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 3066 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 3068 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 3069 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 3070 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 3071 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3072 */       return true;
/*      */     }
/* 3074 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3075 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3080 */     PageContext pageContext = _jspx_page_context;
/* 3081 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3083 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 3084 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 3085 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3087 */     _jspx_th_c_005fset_005f5.setVar("wiz");
/*      */     
/* 3089 */     _jspx_th_c_005fset_005f5.setValue("${param.wiz}");
/*      */     
/* 3091 */     _jspx_th_c_005fset_005f5.setScope("request");
/* 3092 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 3093 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 3094 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 3095 */       return true;
/*      */     }
/* 3097 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f5);
/* 3098 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3103 */     PageContext pageContext = _jspx_page_context;
/* 3104 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3106 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 3107 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3108 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3110 */     _jspx_th_c_005fout_005f2.setValue("${wizimage}");
/*      */     
/* 3112 */     _jspx_th_c_005fout_005f2.setEscapeXml("false");
/* 3113 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3114 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3115 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3116 */       return true;
/*      */     }
/* 3118 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3119 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3124 */     PageContext pageContext = _jspx_page_context;
/* 3125 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3127 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3128 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 3129 */     _jspx_th_c_005fif_005f20.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3131 */     _jspx_th_c_005fif_005f20.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 3132 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 3133 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 3135 */         out.write("\n    <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 3136 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f20, _jspx_page_context))
/* 3137 */           return true;
/* 3138 */         out.write("&wiz=true\">\n    ");
/* 3139 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 3140 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3144 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 3145 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3146 */       return true;
/*      */     }
/* 3148 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 3149 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3154 */     PageContext pageContext = _jspx_page_context;
/* 3155 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3157 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3158 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3159 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f20);
/*      */     
/* 3161 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 3162 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3163 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3164 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3165 */       return true;
/*      */     }
/* 3167 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3168 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3173 */     PageContext pageContext = _jspx_page_context;
/* 3174 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3176 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 3177 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3178 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3180 */     _jspx_th_c_005fout_005f4.setValue("${wizimage}");
/*      */     
/* 3182 */     _jspx_th_c_005fout_005f4.setEscapeXml("false");
/* 3183 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3184 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3185 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3186 */       return true;
/*      */     }
/* 3188 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3189 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f21(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3194 */     PageContext pageContext = _jspx_page_context;
/* 3195 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3197 */     IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3198 */     _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 3199 */     _jspx_th_c_005fif_005f21.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3201 */     _jspx_th_c_005fif_005f21.setTest("${wizimage=='/images/wiz_associatemonitor_high.gif'}");
/* 3202 */     int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 3203 */     if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */       for (;;) {
/* 3205 */         out.write("\n    \t</a>\n    \t");
/* 3206 */         int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 3207 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3211 */     if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 3212 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 3213 */       return true;
/*      */     }
/* 3215 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 3216 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f22(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3221 */     PageContext pageContext = _jspx_page_context;
/* 3222 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3224 */     IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3225 */     _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 3226 */     _jspx_th_c_005fif_005f22.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3228 */     _jspx_th_c_005fif_005f22.setTest("${wizimage=='/images/new_high.gif'}");
/* 3229 */     int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 3230 */     if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */       for (;;) {
/* 3232 */         out.write("\n       <a href=\"/showresource.do?method=associateMonitors&haid=");
/* 3233 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f22, _jspx_page_context))
/* 3234 */           return true;
/* 3235 */         out.write("&wiz=true\">\n       ");
/* 3236 */         int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 3237 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3241 */     if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 3242 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 3243 */       return true;
/*      */     }
/* 3245 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 3246 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3251 */     PageContext pageContext = _jspx_page_context;
/* 3252 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3254 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3255 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3256 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f22);
/*      */     
/* 3258 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 3259 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3260 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3261 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3262 */       return true;
/*      */     }
/* 3264 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3265 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3270 */     PageContext pageContext = _jspx_page_context;
/* 3271 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3273 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 3274 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3275 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3277 */     _jspx_th_c_005fout_005f6.setValue("${wizimage}");
/*      */     
/* 3279 */     _jspx_th_c_005fout_005f6.setEscapeXml("false");
/* 3280 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3281 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3282 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3283 */       return true;
/*      */     }
/* 3285 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3286 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f23(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3291 */     PageContext pageContext = _jspx_page_context;
/* 3292 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3294 */     IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3295 */     _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 3296 */     _jspx_th_c_005fif_005f23.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3298 */     _jspx_th_c_005fif_005f23.setTest("${wizimage=='/images/new_high.gif'}");
/* 3299 */     int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 3300 */     if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */       for (;;) {
/* 3302 */         out.write("\n       \t</a>\n       \t");
/* 3303 */         int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 3304 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3308 */     if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 3309 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 3310 */       return true;
/*      */     }
/* 3312 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 3313 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f26(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3318 */     PageContext pageContext = _jspx_page_context;
/* 3319 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3321 */     IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3322 */     _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 3323 */     _jspx_th_c_005fif_005f26.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3325 */     _jspx_th_c_005fif_005f26.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 3326 */     int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 3327 */     if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */       for (;;) {
/* 3329 */         out.write("\t\n    <a href=\"/showActionProfiles.do?method=getHAProfiles&haid=");
/* 3330 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f26, _jspx_page_context))
/* 3331 */           return true;
/* 3332 */         out.write("&wiz=true\">\n ");
/* 3333 */         int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 3334 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3338 */     if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 3339 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 3340 */       return true;
/*      */     }
/* 3342 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 3343 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3348 */     PageContext pageContext = _jspx_page_context;
/* 3349 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3351 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3352 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3353 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 3355 */     _jspx_th_c_005fout_005f7.setValue("${param.haid}");
/* 3356 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3357 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3358 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3359 */       return true;
/*      */     }
/* 3361 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3362 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3367 */     PageContext pageContext = _jspx_page_context;
/* 3368 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3370 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 3371 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3372 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3374 */     _jspx_th_c_005fout_005f8.setValue("${wizimage}");
/*      */     
/* 3376 */     _jspx_th_c_005fout_005f8.setEscapeXml("false");
/* 3377 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3378 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3379 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3380 */       return true;
/*      */     }
/* 3382 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3383 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f27(JspTag _jspx_th_c_005fif_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3388 */     PageContext pageContext = _jspx_page_context;
/* 3389 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3391 */     IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3392 */     _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 3393 */     _jspx_th_c_005fif_005f27.setParent((Tag)_jspx_th_c_005fif_005f17);
/*      */     
/* 3395 */     _jspx_th_c_005fif_005f27.setTest("${wizimage=='/images/wiz_configurealerts_high.gif'}");
/* 3396 */     int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 3397 */     if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */       for (;;) {
/* 3399 */         out.write("\t    \n    </a>\n ");
/* 3400 */         int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 3401 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3405 */     if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 3406 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 3407 */       return true;
/*      */     }
/* 3409 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 3410 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3415 */     PageContext pageContext = _jspx_page_context;
/* 3416 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3418 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 3419 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 3420 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*      */     
/* 3422 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*      */     
/* 3424 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 3425 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 3426 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 3427 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 3428 */       return true;
/*      */     }
/* 3430 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 3431 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3436 */     PageContext pageContext = _jspx_page_context;
/* 3437 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3439 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 3440 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 3441 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*      */     
/* 3443 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*      */     
/* 3445 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 3446 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 3447 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 3448 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 3449 */       return true;
/*      */     }
/* 3451 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 3452 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3457 */     PageContext pageContext = _jspx_page_context;
/* 3458 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3460 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 3461 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3462 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3464 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 3466 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/* 3467 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3468 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3469 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3470 */       return true;
/*      */     }
/* 3472 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3473 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_c_005fif_005f29, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3478 */     PageContext pageContext = _jspx_page_context;
/* 3479 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3481 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3482 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 3483 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_c_005fif_005f29);
/*      */     
/* 3485 */     _jspx_th_html_005fradio_005f0.setProperty("ticketingType");
/*      */     
/* 3487 */     _jspx_th_html_005fradio_005f0.setValue("restapi");
/*      */     
/* 3489 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:changediv('restapi')");
/* 3490 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 3491 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 3492 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3493 */       return true;
/*      */     }
/* 3495 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 3496 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_c_005fif_005f29, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3501 */     PageContext pageContext = _jspx_page_context;
/* 3502 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3504 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 3505 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 3506 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_c_005fif_005f29);
/*      */     
/* 3508 */     _jspx_th_html_005fradio_005f1.setProperty("ticketingType");
/*      */     
/* 3510 */     _jspx_th_html_005fradio_005f1.setValue("credential");
/*      */     
/* 3512 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:changediv('credential')");
/* 3513 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 3514 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 3515 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3516 */       return true;
/*      */     }
/* 3518 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 3519 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_c_005fif_005f32, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3524 */     PageContext pageContext = _jspx_page_context;
/* 3525 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3527 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fsize_005fproperty_005fonchange.get(SelectTag.class);
/* 3528 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3529 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_c_005fif_005f32);
/*      */     
/* 3531 */     _jspx_th_html_005fselect_005f0.setProperty("reqTemplate");
/*      */     
/* 3533 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */     
/* 3535 */     _jspx_th_html_005fselect_005f0.setSize("1");
/*      */     
/* 3537 */     _jspx_th_html_005fselect_005f0.setOnchange("getTemplateInformation()");
/* 3538 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3539 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3540 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3541 */         out = _jspx_page_context.pushBody();
/* 3542 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3543 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3546 */         out.write("\n\t\t   ");
/* 3547 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 3548 */           return true;
/* 3549 */         out.write("\n\t\t   ");
/* 3550 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3551 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3554 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3555 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3558 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3559 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3560 */       return true;
/*      */     }
/* 3562 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3563 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3568 */     PageContext pageContext = _jspx_page_context;
/* 3569 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3571 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3572 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 3573 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 3575 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("availableResources");
/* 3576 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 3577 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 3578 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3579 */       return true;
/*      */     }
/* 3581 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 3582 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_c_005fif_005f33, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3587 */     PageContext pageContext = _jspx_page_context;
/* 3588 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3590 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fsize_005fproperty_005fonchange.get(SelectTag.class);
/* 3591 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 3592 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_c_005fif_005f33);
/*      */     
/* 3594 */     _jspx_th_html_005fselect_005f1.setProperty("accountName");
/*      */     
/* 3596 */     _jspx_th_html_005fselect_005f1.setStyleClass("formtext normal");
/*      */     
/* 3598 */     _jspx_th_html_005fselect_005f1.setSize("1");
/*      */     
/* 3600 */     _jspx_th_html_005fselect_005f1.setOnchange("getCategNames();getSiteNames();");
/* 3601 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 3602 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 3603 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3604 */         out = _jspx_page_context.pushBody();
/* 3605 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 3606 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3609 */         out.write("\n\t\t\t\t\t    ");
/* 3610 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 3611 */           return true;
/* 3612 */         out.write("\n\t\t\t\t ");
/* 3613 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 3614 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3617 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 3618 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3621 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 3622 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 3623 */       return true;
/*      */     }
/* 3625 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 3626 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3631 */     PageContext pageContext = _jspx_page_context;
/* 3632 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3634 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3635 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 3636 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 3638 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("accNamesArr");
/* 3639 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 3640 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 3641 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3642 */       return true;
/*      */     }
/* 3644 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 3645 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_c_005fif_005f33, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3650 */     PageContext pageContext = _jspx_page_context;
/* 3651 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3653 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.get(SelectTag.class);
/* 3654 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 3655 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_c_005fif_005f33);
/*      */     
/* 3657 */     _jspx_th_html_005fselect_005f2.setProperty("siteName");
/*      */     
/* 3659 */     _jspx_th_html_005fselect_005f2.setStyleId("site");
/*      */     
/* 3661 */     _jspx_th_html_005fselect_005f2.setStyleClass("formtext normal");
/*      */     
/* 3663 */     _jspx_th_html_005fselect_005f2.setSize("1");
/*      */     
/* 3665 */     _jspx_th_html_005fselect_005f2.setOnchange("getGroupNames()");
/* 3666 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 3667 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 3668 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3669 */         out = _jspx_page_context.pushBody();
/* 3670 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 3671 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3674 */         out.write("\n\t\t\t\t\t    ");
/* 3675 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 3676 */           return true;
/* 3677 */         out.write("\n\t\t\t\t ");
/* 3678 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 3679 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3682 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 3683 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3686 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 3687 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 3688 */       return true;
/*      */     }
/* 3690 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 3691 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3696 */     PageContext pageContext = _jspx_page_context;
/* 3697 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3699 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3700 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 3701 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 3703 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("siteNameArr");
/* 3704 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 3705 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 3706 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3707 */       return true;
/*      */     }
/* 3709 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 3710 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3715 */     PageContext pageContext = _jspx_page_context;
/* 3716 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3718 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.get(SelectTag.class);
/* 3719 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 3720 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 3722 */     _jspx_th_html_005fselect_005f3.setProperty("category");
/*      */     
/* 3724 */     _jspx_th_html_005fselect_005f3.setStyleId("category");
/*      */     
/* 3726 */     _jspx_th_html_005fselect_005f3.setStyleClass("formtext normal");
/*      */     
/* 3728 */     _jspx_th_html_005fselect_005f3.setSize("1");
/*      */     
/* 3730 */     _jspx_th_html_005fselect_005f3.setOnchange("getSCategories()");
/* 3731 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 3732 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 3733 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3734 */         out = _jspx_page_context.pushBody();
/* 3735 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 3736 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3739 */         out.write("\n\t\t\t   ");
/* 3740 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 3741 */           return true;
/* 3742 */         out.write("\n\t\t   ");
/* 3743 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 3744 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3747 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 3748 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3751 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 3752 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/* 3753 */       return true;
/*      */     }
/* 3755 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f3);
/* 3756 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3761 */     PageContext pageContext = _jspx_page_context;
/* 3762 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3764 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3765 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 3766 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 3768 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("toAdd");
/* 3769 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 3770 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 3771 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 3772 */       return true;
/*      */     }
/* 3774 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 3775 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3780 */     PageContext pageContext = _jspx_page_context;
/* 3781 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3783 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.get(SelectTag.class);
/* 3784 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 3785 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 3787 */     _jspx_th_html_005fselect_005f4.setProperty("subCategory");
/*      */     
/* 3789 */     _jspx_th_html_005fselect_005f4.setStyleId("subcategory");
/*      */     
/* 3791 */     _jspx_th_html_005fselect_005f4.setStyleClass("formtext normal");
/*      */     
/* 3793 */     _jspx_th_html_005fselect_005f4.setSize("1");
/*      */     
/* 3795 */     _jspx_th_html_005fselect_005f4.setOnchange("getMyItems()");
/* 3796 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 3797 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 3798 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 3799 */         out = _jspx_page_context.pushBody();
/* 3800 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 3801 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3804 */         out.write("\n\t\t\t   ");
/* 3805 */         if (_jspx_meth_html_005foptionsCollection_005f4(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 3806 */           return true;
/* 3807 */         out.write("\n\t\t   ");
/* 3808 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 3809 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3812 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 3813 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3816 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 3817 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4);
/* 3818 */       return true;
/*      */     }
/* 3820 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4);
/* 3821 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f4(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3826 */     PageContext pageContext = _jspx_page_context;
/* 3827 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3829 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f4 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3830 */     _jspx_th_html_005foptionsCollection_005f4.setPageContext(_jspx_page_context);
/* 3831 */     _jspx_th_html_005foptionsCollection_005f4.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 3833 */     _jspx_th_html_005foptionsCollection_005f4.setProperty("toAddSC");
/* 3834 */     int _jspx_eval_html_005foptionsCollection_005f4 = _jspx_th_html_005foptionsCollection_005f4.doStartTag();
/* 3835 */     if (_jspx_th_html_005foptionsCollection_005f4.doEndTag() == 5) {
/* 3836 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 3837 */       return true;
/*      */     }
/* 3839 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 3840 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f5(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3845 */     PageContext pageContext = _jspx_page_context;
/* 3846 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3848 */     SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.get(SelectTag.class);
/* 3849 */     _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 3850 */     _jspx_th_html_005fselect_005f5.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 3852 */     _jspx_th_html_005fselect_005f5.setProperty("item");
/*      */     
/* 3854 */     _jspx_th_html_005fselect_005f5.setStyleId("item");
/*      */     
/* 3856 */     _jspx_th_html_005fselect_005f5.setStyleClass("formtext normal");
/*      */     
/* 3858 */     _jspx_th_html_005fselect_005f5.setSize("1");
/* 3859 */     int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 3860 */     if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 3861 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 3862 */         out = _jspx_page_context.pushBody();
/* 3863 */         _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 3864 */         _jspx_th_html_005fselect_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3867 */         out.write("\n\t\t\t   ");
/* 3868 */         if (_jspx_meth_html_005foptionsCollection_005f5(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/* 3869 */           return true;
/* 3870 */         out.write(10);
/* 3871 */         out.write(9);
/* 3872 */         out.write(9);
/* 3873 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 3874 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3877 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 3878 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3881 */     if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 3882 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 3883 */       return true;
/*      */     }
/* 3885 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 3886 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f5(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3891 */     PageContext pageContext = _jspx_page_context;
/* 3892 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3894 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f5 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3895 */     _jspx_th_html_005foptionsCollection_005f5.setPageContext(_jspx_page_context);
/* 3896 */     _jspx_th_html_005foptionsCollection_005f5.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 3898 */     _jspx_th_html_005foptionsCollection_005f5.setProperty("toAddItem");
/* 3899 */     int _jspx_eval_html_005foptionsCollection_005f5 = _jspx_th_html_005foptionsCollection_005f5.doStartTag();
/* 3900 */     if (_jspx_th_html_005foptionsCollection_005f5.doEndTag() == 5) {
/* 3901 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 3902 */       return true;
/*      */     }
/* 3904 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 3905 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f6(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3910 */     PageContext pageContext = _jspx_page_context;
/* 3911 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3913 */     SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.get(SelectTag.class);
/* 3914 */     _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/* 3915 */     _jspx_th_html_005fselect_005f6.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 3917 */     _jspx_th_html_005fselect_005f6.setProperty("priority");
/*      */     
/* 3919 */     _jspx_th_html_005fselect_005f6.setStyleId("priority");
/*      */     
/* 3921 */     _jspx_th_html_005fselect_005f6.setStyleClass("formtext normal");
/*      */     
/* 3923 */     _jspx_th_html_005fselect_005f6.setSize("1");
/* 3924 */     int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/* 3925 */     if (_jspx_eval_html_005fselect_005f6 != 0) {
/* 3926 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 3927 */         out = _jspx_page_context.pushBody();
/* 3928 */         _jspx_th_html_005fselect_005f6.setBodyContent((BodyContent)out);
/* 3929 */         _jspx_th_html_005fselect_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3932 */         out.write("\n\t\t    ");
/* 3933 */         if (_jspx_meth_html_005foptionsCollection_005f6(_jspx_th_html_005fselect_005f6, _jspx_page_context))
/* 3934 */           return true;
/* 3935 */         out.write("\n\t\t    ");
/* 3936 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/* 3937 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3940 */       if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 3941 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3944 */     if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/* 3945 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f6);
/* 3946 */       return true;
/*      */     }
/* 3948 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f6);
/* 3949 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f6(JspTag _jspx_th_html_005fselect_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3954 */     PageContext pageContext = _jspx_page_context;
/* 3955 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3957 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f6 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 3958 */     _jspx_th_html_005foptionsCollection_005f6.setPageContext(_jspx_page_context);
/* 3959 */     _jspx_th_html_005foptionsCollection_005f6.setParent((Tag)_jspx_th_html_005fselect_005f6);
/*      */     
/* 3961 */     _jspx_th_html_005foptionsCollection_005f6.setProperty("present");
/* 3962 */     int _jspx_eval_html_005foptionsCollection_005f6 = _jspx_th_html_005foptionsCollection_005f6.doStartTag();
/* 3963 */     if (_jspx_th_html_005foptionsCollection_005f6.doEndTag() == 5) {
/* 3964 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 3965 */       return true;
/*      */     }
/* 3967 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f6);
/* 3968 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f7(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3973 */     PageContext pageContext = _jspx_page_context;
/* 3974 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3976 */     SelectTag _jspx_th_html_005fselect_005f7 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.get(SelectTag.class);
/* 3977 */     _jspx_th_html_005fselect_005f7.setPageContext(_jspx_page_context);
/* 3978 */     _jspx_th_html_005fselect_005f7.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 3980 */     _jspx_th_html_005fselect_005f7.setProperty("group");
/*      */     
/* 3982 */     _jspx_th_html_005fselect_005f7.setStyleId("group");
/*      */     
/* 3984 */     _jspx_th_html_005fselect_005f7.setStyleClass("formtext normal");
/*      */     
/* 3986 */     _jspx_th_html_005fselect_005f7.setSize("1");
/*      */     
/* 3988 */     _jspx_th_html_005fselect_005f7.setOnchange("getTechnicians()");
/* 3989 */     int _jspx_eval_html_005fselect_005f7 = _jspx_th_html_005fselect_005f7.doStartTag();
/* 3990 */     if (_jspx_eval_html_005fselect_005f7 != 0) {
/* 3991 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 3992 */         out = _jspx_page_context.pushBody();
/* 3993 */         _jspx_th_html_005fselect_005f7.setBodyContent((BodyContent)out);
/* 3994 */         _jspx_th_html_005fselect_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3997 */         out.write("\n\t\t\t\t\t    ");
/* 3998 */         if (_jspx_meth_html_005foptionsCollection_005f7(_jspx_th_html_005fselect_005f7, _jspx_page_context))
/* 3999 */           return true;
/* 4000 */         out.write("\n\t\t\t\t ");
/* 4001 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f7.doAfterBody();
/* 4002 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4005 */       if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 4006 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4009 */     if (_jspx_th_html_005fselect_005f7.doEndTag() == 5) {
/* 4010 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f7);
/* 4011 */       return true;
/*      */     }
/* 4013 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f7);
/* 4014 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f7(JspTag _jspx_th_html_005fselect_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4019 */     PageContext pageContext = _jspx_page_context;
/* 4020 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4022 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f7 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4023 */     _jspx_th_html_005foptionsCollection_005f7.setPageContext(_jspx_page_context);
/* 4024 */     _jspx_th_html_005foptionsCollection_005f7.setParent((Tag)_jspx_th_html_005fselect_005f7);
/*      */     
/* 4026 */     _jspx_th_html_005foptionsCollection_005f7.setProperty("toAddSG");
/* 4027 */     int _jspx_eval_html_005foptionsCollection_005f7 = _jspx_th_html_005foptionsCollection_005f7.doStartTag();
/* 4028 */     if (_jspx_th_html_005foptionsCollection_005f7.doEndTag() == 5) {
/* 4029 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f7);
/* 4030 */       return true;
/*      */     }
/* 4032 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f7);
/* 4033 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f8(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4038 */     PageContext pageContext = _jspx_page_context;
/* 4039 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4041 */     SelectTag _jspx_th_html_005fselect_005f8 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.get(SelectTag.class);
/* 4042 */     _jspx_th_html_005fselect_005f8.setPageContext(_jspx_page_context);
/* 4043 */     _jspx_th_html_005fselect_005f8.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 4045 */     _jspx_th_html_005fselect_005f8.setProperty("technician");
/*      */     
/* 4047 */     _jspx_th_html_005fselect_005f8.setStyleId("technician");
/*      */     
/* 4049 */     _jspx_th_html_005fselect_005f8.setStyleClass("myClass formtext normal");
/*      */     
/* 4051 */     _jspx_th_html_005fselect_005f8.setSize("1");
/* 4052 */     int _jspx_eval_html_005fselect_005f8 = _jspx_th_html_005fselect_005f8.doStartTag();
/* 4053 */     if (_jspx_eval_html_005fselect_005f8 != 0) {
/* 4054 */       if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 4055 */         out = _jspx_page_context.pushBody();
/* 4056 */         _jspx_th_html_005fselect_005f8.setBodyContent((BodyContent)out);
/* 4057 */         _jspx_th_html_005fselect_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4060 */         out.write("\n\t\t\t\t\t\t");
/* 4061 */         if (_jspx_meth_html_005foptionsCollection_005f8(_jspx_th_html_005fselect_005f8, _jspx_page_context))
/* 4062 */           return true;
/* 4063 */         out.write("\n\t\t\t\t\t");
/* 4064 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f8.doAfterBody();
/* 4065 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4068 */       if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 4069 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4072 */     if (_jspx_th_html_005fselect_005f8.doEndTag() == 5) {
/* 4073 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f8);
/* 4074 */       return true;
/*      */     }
/* 4076 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f8);
/* 4077 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f8(JspTag _jspx_th_html_005fselect_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4082 */     PageContext pageContext = _jspx_page_context;
/* 4083 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4085 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f8 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4086 */     _jspx_th_html_005foptionsCollection_005f8.setPageContext(_jspx_page_context);
/* 4087 */     _jspx_th_html_005foptionsCollection_005f8.setParent((Tag)_jspx_th_html_005fselect_005f8);
/*      */     
/* 4089 */     _jspx_th_html_005foptionsCollection_005f8.setProperty("applications");
/* 4090 */     int _jspx_eval_html_005foptionsCollection_005f8 = _jspx_th_html_005foptionsCollection_005f8.doStartTag();
/* 4091 */     if (_jspx_th_html_005foptionsCollection_005f8.doEndTag() == 5) {
/* 4092 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f8);
/* 4093 */       return true;
/*      */     }
/* 4095 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f8);
/* 4096 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_c_005fif_005f36, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4101 */     PageContext pageContext = _jspx_page_context;
/* 4102 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4104 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.get(TextTag.class);
/* 4105 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 4106 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_c_005fif_005f36);
/*      */     
/* 4108 */     _jspx_th_html_005ftext_005f1.setProperty("category");
/*      */     
/* 4110 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext normal");
/*      */     
/* 4112 */     _jspx_th_html_005ftext_005f1.setStyle("width:100%");
/* 4113 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 4114 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 4115 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4116 */       return true;
/*      */     }
/* 4118 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 4119 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_c_005fif_005f36, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4124 */     PageContext pageContext = _jspx_page_context;
/* 4125 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4127 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.get(TextTag.class);
/* 4128 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 4129 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_c_005fif_005f36);
/*      */     
/* 4131 */     _jspx_th_html_005ftext_005f2.setProperty("group");
/*      */     
/* 4133 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext normal");
/*      */     
/* 4135 */     _jspx_th_html_005ftext_005f2.setStyle("width:100%");
/* 4136 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 4137 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 4138 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4139 */       return true;
/*      */     }
/* 4141 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 4142 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_c_005fif_005f36, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4147 */     PageContext pageContext = _jspx_page_context;
/* 4148 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4150 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.get(TextTag.class);
/* 4151 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 4152 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_c_005fif_005f36);
/*      */     
/* 4154 */     _jspx_th_html_005ftext_005f3.setProperty("technician");
/*      */     
/* 4156 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext normal");
/*      */     
/* 4158 */     _jspx_th_html_005ftext_005f3.setStyle("width:100%");
/* 4159 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 4160 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 4161 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4162 */       return true;
/*      */     }
/* 4164 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 4165 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f9(JspTag _jspx_th_c_005fif_005f37, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4170 */     PageContext pageContext = _jspx_page_context;
/* 4171 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4173 */     SelectTag _jspx_th_html_005fselect_005f9 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.get(SelectTag.class);
/* 4174 */     _jspx_th_html_005fselect_005f9.setPageContext(_jspx_page_context);
/* 4175 */     _jspx_th_html_005fselect_005f9.setParent((Tag)_jspx_th_c_005fif_005f37);
/*      */     
/* 4177 */     _jspx_th_html_005fselect_005f9.setProperty("category");
/*      */     
/* 4179 */     _jspx_th_html_005fselect_005f9.setStyleId("category");
/*      */     
/* 4181 */     _jspx_th_html_005fselect_005f9.setStyleClass("formtext normal");
/*      */     
/* 4183 */     _jspx_th_html_005fselect_005f9.setSize("1");
/*      */     
/* 4185 */     _jspx_th_html_005fselect_005f9.setOnchange("getSubCategoriesForServiceNowAjax()");
/* 4186 */     int _jspx_eval_html_005fselect_005f9 = _jspx_th_html_005fselect_005f9.doStartTag();
/* 4187 */     if (_jspx_eval_html_005fselect_005f9 != 0) {
/* 4188 */       if (_jspx_eval_html_005fselect_005f9 != 1) {
/* 4189 */         out = _jspx_page_context.pushBody();
/* 4190 */         _jspx_th_html_005fselect_005f9.setBodyContent((BodyContent)out);
/* 4191 */         _jspx_th_html_005fselect_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4194 */         out.write("\n\t\t\t   ");
/* 4195 */         if (_jspx_meth_html_005foptionsCollection_005f9(_jspx_th_html_005fselect_005f9, _jspx_page_context))
/* 4196 */           return true;
/* 4197 */         out.write("\n\t\t   ");
/* 4198 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f9.doAfterBody();
/* 4199 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4202 */       if (_jspx_eval_html_005fselect_005f9 != 1) {
/* 4203 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4206 */     if (_jspx_th_html_005fselect_005f9.doEndTag() == 5) {
/* 4207 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f9);
/* 4208 */       return true;
/*      */     }
/* 4210 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f9);
/* 4211 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f9(JspTag _jspx_th_html_005fselect_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4216 */     PageContext pageContext = _jspx_page_context;
/* 4217 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4219 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f9 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4220 */     _jspx_th_html_005foptionsCollection_005f9.setPageContext(_jspx_page_context);
/* 4221 */     _jspx_th_html_005foptionsCollection_005f9.setParent((Tag)_jspx_th_html_005fselect_005f9);
/*      */     
/* 4223 */     _jspx_th_html_005foptionsCollection_005f9.setProperty("toAdd");
/* 4224 */     int _jspx_eval_html_005foptionsCollection_005f9 = _jspx_th_html_005foptionsCollection_005f9.doStartTag();
/* 4225 */     if (_jspx_th_html_005foptionsCollection_005f9.doEndTag() == 5) {
/* 4226 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f9);
/* 4227 */       return true;
/*      */     }
/* 4229 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f9);
/* 4230 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f10(JspTag _jspx_th_c_005fif_005f37, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4235 */     PageContext pageContext = _jspx_page_context;
/* 4236 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4238 */     SelectTag _jspx_th_html_005fselect_005f10 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.get(SelectTag.class);
/* 4239 */     _jspx_th_html_005fselect_005f10.setPageContext(_jspx_page_context);
/* 4240 */     _jspx_th_html_005fselect_005f10.setParent((Tag)_jspx_th_c_005fif_005f37);
/*      */     
/* 4242 */     _jspx_th_html_005fselect_005f10.setProperty("subCategory");
/*      */     
/* 4244 */     _jspx_th_html_005fselect_005f10.setStyleId("subcategory");
/*      */     
/* 4246 */     _jspx_th_html_005fselect_005f10.setStyleClass("formtext normal");
/*      */     
/* 4248 */     _jspx_th_html_005fselect_005f10.setSize("1");
/* 4249 */     int _jspx_eval_html_005fselect_005f10 = _jspx_th_html_005fselect_005f10.doStartTag();
/* 4250 */     if (_jspx_eval_html_005fselect_005f10 != 0) {
/* 4251 */       if (_jspx_eval_html_005fselect_005f10 != 1) {
/* 4252 */         out = _jspx_page_context.pushBody();
/* 4253 */         _jspx_th_html_005fselect_005f10.setBodyContent((BodyContent)out);
/* 4254 */         _jspx_th_html_005fselect_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4257 */         out.write("\n\t\t\t   ");
/* 4258 */         if (_jspx_meth_html_005foptionsCollection_005f10(_jspx_th_html_005fselect_005f10, _jspx_page_context))
/* 4259 */           return true;
/* 4260 */         out.write("\n\t\t   ");
/* 4261 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f10.doAfterBody();
/* 4262 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4265 */       if (_jspx_eval_html_005fselect_005f10 != 1) {
/* 4266 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4269 */     if (_jspx_th_html_005fselect_005f10.doEndTag() == 5) {
/* 4270 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f10);
/* 4271 */       return true;
/*      */     }
/* 4273 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f10);
/* 4274 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f10(JspTag _jspx_th_html_005fselect_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4279 */     PageContext pageContext = _jspx_page_context;
/* 4280 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4282 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f10 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4283 */     _jspx_th_html_005foptionsCollection_005f10.setPageContext(_jspx_page_context);
/* 4284 */     _jspx_th_html_005foptionsCollection_005f10.setParent((Tag)_jspx_th_html_005fselect_005f10);
/*      */     
/* 4286 */     _jspx_th_html_005foptionsCollection_005f10.setProperty("toAddSC");
/* 4287 */     int _jspx_eval_html_005foptionsCollection_005f10 = _jspx_th_html_005foptionsCollection_005f10.doStartTag();
/* 4288 */     if (_jspx_th_html_005foptionsCollection_005f10.doEndTag() == 5) {
/* 4289 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f10);
/* 4290 */       return true;
/*      */     }
/* 4292 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f10);
/* 4293 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f11(JspTag _jspx_th_c_005fif_005f37, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4298 */     PageContext pageContext = _jspx_page_context;
/* 4299 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4301 */     SelectTag _jspx_th_html_005fselect_005f11 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.get(SelectTag.class);
/* 4302 */     _jspx_th_html_005fselect_005f11.setPageContext(_jspx_page_context);
/* 4303 */     _jspx_th_html_005fselect_005f11.setParent((Tag)_jspx_th_c_005fif_005f37);
/*      */     
/* 4305 */     _jspx_th_html_005fselect_005f11.setProperty("priority");
/*      */     
/* 4307 */     _jspx_th_html_005fselect_005f11.setStyleId("priority");
/*      */     
/* 4309 */     _jspx_th_html_005fselect_005f11.setStyleClass("formtext normal");
/*      */     
/* 4311 */     _jspx_th_html_005fselect_005f11.setSize("1");
/* 4312 */     int _jspx_eval_html_005fselect_005f11 = _jspx_th_html_005fselect_005f11.doStartTag();
/* 4313 */     if (_jspx_eval_html_005fselect_005f11 != 0) {
/* 4314 */       if (_jspx_eval_html_005fselect_005f11 != 1) {
/* 4315 */         out = _jspx_page_context.pushBody();
/* 4316 */         _jspx_th_html_005fselect_005f11.setBodyContent((BodyContent)out);
/* 4317 */         _jspx_th_html_005fselect_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4320 */         out.write("\n\t\t    ");
/* 4321 */         if (_jspx_meth_html_005foptionsCollection_005f11(_jspx_th_html_005fselect_005f11, _jspx_page_context))
/* 4322 */           return true;
/* 4323 */         out.write("\n\t\t    ");
/* 4324 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f11.doAfterBody();
/* 4325 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4328 */       if (_jspx_eval_html_005fselect_005f11 != 1) {
/* 4329 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4332 */     if (_jspx_th_html_005fselect_005f11.doEndTag() == 5) {
/* 4333 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f11);
/* 4334 */       return true;
/*      */     }
/* 4336 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f11);
/* 4337 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f11(JspTag _jspx_th_html_005fselect_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4342 */     PageContext pageContext = _jspx_page_context;
/* 4343 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4345 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f11 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4346 */     _jspx_th_html_005foptionsCollection_005f11.setPageContext(_jspx_page_context);
/* 4347 */     _jspx_th_html_005foptionsCollection_005f11.setParent((Tag)_jspx_th_html_005fselect_005f11);
/*      */     
/* 4349 */     _jspx_th_html_005foptionsCollection_005f11.setProperty("present");
/* 4350 */     int _jspx_eval_html_005foptionsCollection_005f11 = _jspx_th_html_005foptionsCollection_005f11.doStartTag();
/* 4351 */     if (_jspx_th_html_005foptionsCollection_005f11.doEndTag() == 5) {
/* 4352 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f11);
/* 4353 */       return true;
/*      */     }
/* 4355 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f11);
/* 4356 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f12(JspTag _jspx_th_c_005fif_005f37, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4361 */     PageContext pageContext = _jspx_page_context;
/* 4362 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4364 */     SelectTag _jspx_th_html_005fselect_005f12 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.get(SelectTag.class);
/* 4365 */     _jspx_th_html_005fselect_005f12.setPageContext(_jspx_page_context);
/* 4366 */     _jspx_th_html_005fselect_005f12.setParent((Tag)_jspx_th_c_005fif_005f37);
/*      */     
/* 4368 */     _jspx_th_html_005fselect_005f12.setProperty("group");
/*      */     
/* 4370 */     _jspx_th_html_005fselect_005f12.setStyleId("group");
/*      */     
/* 4372 */     _jspx_th_html_005fselect_005f12.setStyleClass("formtext normal");
/*      */     
/* 4374 */     _jspx_th_html_005fselect_005f12.setSize("1");
/*      */     
/* 4376 */     _jspx_th_html_005fselect_005f12.setOnchange("getTechnicians()");
/* 4377 */     int _jspx_eval_html_005fselect_005f12 = _jspx_th_html_005fselect_005f12.doStartTag();
/* 4378 */     if (_jspx_eval_html_005fselect_005f12 != 0) {
/* 4379 */       if (_jspx_eval_html_005fselect_005f12 != 1) {
/* 4380 */         out = _jspx_page_context.pushBody();
/* 4381 */         _jspx_th_html_005fselect_005f12.setBodyContent((BodyContent)out);
/* 4382 */         _jspx_th_html_005fselect_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4385 */         out.write("\n\t\t\t\t\t    ");
/* 4386 */         if (_jspx_meth_html_005foptionsCollection_005f12(_jspx_th_html_005fselect_005f12, _jspx_page_context))
/* 4387 */           return true;
/* 4388 */         out.write("\n\t\t\t\t ");
/* 4389 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f12.doAfterBody();
/* 4390 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4393 */       if (_jspx_eval_html_005fselect_005f12 != 1) {
/* 4394 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4397 */     if (_jspx_th_html_005fselect_005f12.doEndTag() == 5) {
/* 4398 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f12);
/* 4399 */       return true;
/*      */     }
/* 4401 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f12);
/* 4402 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f12(JspTag _jspx_th_html_005fselect_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4407 */     PageContext pageContext = _jspx_page_context;
/* 4408 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4410 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f12 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4411 */     _jspx_th_html_005foptionsCollection_005f12.setPageContext(_jspx_page_context);
/* 4412 */     _jspx_th_html_005foptionsCollection_005f12.setParent((Tag)_jspx_th_html_005fselect_005f12);
/*      */     
/* 4414 */     _jspx_th_html_005foptionsCollection_005f12.setProperty("toAddSG");
/* 4415 */     int _jspx_eval_html_005foptionsCollection_005f12 = _jspx_th_html_005foptionsCollection_005f12.doStartTag();
/* 4416 */     if (_jspx_th_html_005foptionsCollection_005f12.doEndTag() == 5) {
/* 4417 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f12);
/* 4418 */       return true;
/*      */     }
/* 4420 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f12);
/* 4421 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f13(JspTag _jspx_th_c_005fif_005f37, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4426 */     PageContext pageContext = _jspx_page_context;
/* 4427 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4429 */     SelectTag _jspx_th_html_005fselect_005f13 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.get(SelectTag.class);
/* 4430 */     _jspx_th_html_005fselect_005f13.setPageContext(_jspx_page_context);
/* 4431 */     _jspx_th_html_005fselect_005f13.setParent((Tag)_jspx_th_c_005fif_005f37);
/*      */     
/* 4433 */     _jspx_th_html_005fselect_005f13.setProperty("technician");
/*      */     
/* 4435 */     _jspx_th_html_005fselect_005f13.setStyleId("technician");
/*      */     
/* 4437 */     _jspx_th_html_005fselect_005f13.setStyleClass("myClass formtext normal");
/*      */     
/* 4439 */     _jspx_th_html_005fselect_005f13.setSize("1");
/* 4440 */     int _jspx_eval_html_005fselect_005f13 = _jspx_th_html_005fselect_005f13.doStartTag();
/* 4441 */     if (_jspx_eval_html_005fselect_005f13 != 0) {
/* 4442 */       if (_jspx_eval_html_005fselect_005f13 != 1) {
/* 4443 */         out = _jspx_page_context.pushBody();
/* 4444 */         _jspx_th_html_005fselect_005f13.setBodyContent((BodyContent)out);
/* 4445 */         _jspx_th_html_005fselect_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4448 */         out.write("\n\t\t\t\t\t\t");
/* 4449 */         if (_jspx_meth_html_005foptionsCollection_005f13(_jspx_th_html_005fselect_005f13, _jspx_page_context))
/* 4450 */           return true;
/* 4451 */         out.write("\n\t\t\t\t\t");
/* 4452 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f13.doAfterBody();
/* 4453 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4456 */       if (_jspx_eval_html_005fselect_005f13 != 1) {
/* 4457 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4460 */     if (_jspx_th_html_005fselect_005f13.doEndTag() == 5) {
/* 4461 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f13);
/* 4462 */       return true;
/*      */     }
/* 4464 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleId_005fstyleClass_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f13);
/* 4465 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f13(JspTag _jspx_th_html_005fselect_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4470 */     PageContext pageContext = _jspx_page_context;
/* 4471 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4473 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f13 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4474 */     _jspx_th_html_005foptionsCollection_005f13.setPageContext(_jspx_page_context);
/* 4475 */     _jspx_th_html_005foptionsCollection_005f13.setParent((Tag)_jspx_th_html_005fselect_005f13);
/*      */     
/* 4477 */     _jspx_th_html_005foptionsCollection_005f13.setProperty("applications");
/* 4478 */     int _jspx_eval_html_005foptionsCollection_005f13 = _jspx_th_html_005foptionsCollection_005f13.doStartTag();
/* 4479 */     if (_jspx_th_html_005foptionsCollection_005f13.doEndTag() == 5) {
/* 4480 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f13);
/* 4481 */       return true;
/*      */     }
/* 4483 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f13);
/* 4484 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f14(JspTag _jspx_th_c_005fif_005f38, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4489 */     PageContext pageContext = _jspx_page_context;
/* 4490 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4492 */     SelectTag _jspx_th_html_005fselect_005f14 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.get(SelectTag.class);
/* 4493 */     _jspx_th_html_005fselect_005f14.setPageContext(_jspx_page_context);
/* 4494 */     _jspx_th_html_005fselect_005f14.setParent((Tag)_jspx_th_c_005fif_005f38);
/*      */     
/* 4496 */     _jspx_th_html_005fselect_005f14.setProperty("reqName");
/*      */     
/* 4498 */     _jspx_th_html_005fselect_005f14.setStyle("width:100%");
/*      */     
/* 4500 */     _jspx_th_html_005fselect_005f14.setSize("1");
/* 4501 */     int _jspx_eval_html_005fselect_005f14 = _jspx_th_html_005fselect_005f14.doStartTag();
/* 4502 */     if (_jspx_eval_html_005fselect_005f14 != 0) {
/* 4503 */       if (_jspx_eval_html_005fselect_005f14 != 1) {
/* 4504 */         out = _jspx_page_context.pushBody();
/* 4505 */         _jspx_th_html_005fselect_005f14.setBodyContent((BodyContent)out);
/* 4506 */         _jspx_th_html_005fselect_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4509 */         out.write("\n\t\t\t\t\t    ");
/* 4510 */         if (_jspx_meth_html_005foptionsCollection_005f14(_jspx_th_html_005fselect_005f14, _jspx_page_context))
/* 4511 */           return true;
/* 4512 */         out.write("\n\t\t\t\t\t ");
/* 4513 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f14.doAfterBody();
/* 4514 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4517 */       if (_jspx_eval_html_005fselect_005f14 != 1) {
/* 4518 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4521 */     if (_jspx_th_html_005fselect_005f14.doEndTag() == 5) {
/* 4522 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f14);
/* 4523 */       return true;
/*      */     }
/* 4525 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty.reuse(_jspx_th_html_005fselect_005f14);
/* 4526 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f14(JspTag _jspx_th_html_005fselect_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4531 */     PageContext pageContext = _jspx_page_context;
/* 4532 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4534 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f14 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 4535 */     _jspx_th_html_005foptionsCollection_005f14.setPageContext(_jspx_page_context);
/* 4536 */     _jspx_th_html_005foptionsCollection_005f14.setParent((Tag)_jspx_th_html_005fselect_005f14);
/*      */     
/* 4538 */     _jspx_th_html_005foptionsCollection_005f14.setProperty("reqNameArr");
/* 4539 */     int _jspx_eval_html_005foptionsCollection_005f14 = _jspx_th_html_005foptionsCollection_005f14.doStartTag();
/* 4540 */     if (_jspx_th_html_005foptionsCollection_005f14.doEndTag() == 5) {
/* 4541 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f14);
/* 4542 */       return true;
/*      */     }
/* 4544 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f14);
/* 4545 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_c_005fif_005f38, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4550 */     PageContext pageContext = _jspx_page_context;
/* 4551 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4553 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 4554 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 4555 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_c_005fif_005f38);
/*      */     
/* 4557 */     _jspx_th_html_005ftext_005f4.setProperty("reqName");
/*      */     
/* 4559 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext normal");
/* 4560 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 4561 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 4562 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4563 */       return true;
/*      */     }
/* 4565 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 4566 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4571 */     PageContext pageContext = _jspx_page_context;
/* 4572 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4574 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.get(TextTag.class);
/* 4575 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 4576 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4578 */     _jspx_th_html_005ftext_005f5.setProperty("subject");
/*      */     
/* 4580 */     _jspx_th_html_005ftext_005f5.setStyle("width:90%");
/*      */     
/* 4582 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext large");
/*      */     
/* 4584 */     _jspx_th_html_005ftext_005f5.setMaxlength("100");
/* 4585 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 4586 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 4587 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 4588 */       return true;
/*      */     }
/* 4590 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fproperty_005fmaxlength_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 4591 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4596 */     PageContext pageContext = _jspx_page_context;
/* 4597 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4599 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyle_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 4600 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 4601 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4603 */     _jspx_th_html_005ftextarea_005f0.setProperty("message");
/*      */     
/* 4605 */     _jspx_th_html_005ftextarea_005f0.setRows("8");
/*      */     
/* 4607 */     _jspx_th_html_005ftextarea_005f0.setCols("30");
/*      */     
/* 4609 */     _jspx_th_html_005ftextarea_005f0.setStyle("width:90%");
/* 4610 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 4611 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 4612 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 4613 */       return true;
/*      */     }
/* 4615 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyle_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 4616 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_c_005fif_005f39, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4621 */     PageContext pageContext = _jspx_page_context;
/* 4622 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4624 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4625 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 4626 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_c_005fif_005f39);
/*      */     
/* 4628 */     _jspx_th_html_005fradio_005f2.setProperty("mailFormat");
/*      */     
/* 4630 */     _jspx_th_html_005fradio_005f2.setValue("1");
/* 4631 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 4632 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 4633 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 4634 */       return true;
/*      */     }
/* 4636 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 4637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_c_005fif_005f39, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4642 */     PageContext pageContext = _jspx_page_context;
/* 4643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4645 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4646 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 4647 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_c_005fif_005f39);
/*      */     
/* 4649 */     _jspx_th_html_005fradio_005f3.setProperty("mailFormat");
/*      */     
/* 4651 */     _jspx_th_html_005fradio_005f3.setValue("2");
/* 4652 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 4653 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 4654 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 4655 */       return true;
/*      */     }
/* 4657 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 4658 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_c_005fif_005f39, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4663 */     PageContext pageContext = _jspx_page_context;
/* 4664 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4666 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 4667 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 4668 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_c_005fif_005f39);
/*      */     
/* 4670 */     _jspx_th_html_005fradio_005f4.setProperty("mailFormat");
/*      */     
/* 4672 */     _jspx_th_html_005fradio_005f4.setValue("0");
/* 4673 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 4674 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 4675 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 4676 */       return true;
/*      */     }
/* 4678 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 4679 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4684 */     PageContext pageContext = _jspx_page_context;
/* 4685 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4687 */     org.apache.struts.taglib.html.CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (org.apache.struts.taglib.html.CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.get(org.apache.struts.taglib.html.CheckboxTag.class);
/* 4688 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 4689 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4691 */     _jspx_th_html_005fcheckbox_005f0.setProperty("appendMessage");
/*      */     
/* 4693 */     _jspx_th_html_005fcheckbox_005f0.setValue("1");
/* 4694 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 4695 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 4696 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4697 */       return true;
/*      */     }
/* 4699 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 4700 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4705 */     PageContext pageContext = _jspx_page_context;
/* 4706 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4708 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.get(HiddenTag.class);
/* 4709 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 4710 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 4712 */     _jspx_th_html_005fhidden_005f0.setProperty("siteName");
/*      */     
/* 4714 */     _jspx_th_html_005fhidden_005f0.setStyleClass("formtext");
/* 4715 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 4716 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 4717 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 4718 */       return true;
/*      */     }
/* 4720 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 4721 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f40(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4726 */     PageContext pageContext = _jspx_page_context;
/* 4727 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4729 */     IfTag _jspx_th_c_005fif_005f40 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4730 */     _jspx_th_c_005fif_005f40.setPageContext(_jspx_page_context);
/* 4731 */     _jspx_th_c_005fif_005f40.setParent(null);
/*      */     
/* 4733 */     _jspx_th_c_005fif_005f40.setTest("${isRestApiEnabled ne true}");
/* 4734 */     int _jspx_eval_c_005fif_005f40 = _jspx_th_c_005fif_005f40.doStartTag();
/* 4735 */     if (_jspx_eval_c_005fif_005f40 != 0) {
/*      */       for (;;) {
/* 4737 */         out.write("\n\t\t\tjQuery('input:radio[name=ticketingType]')[0].disabled = true;\n\t\t");
/* 4738 */         int evalDoAfterBody = _jspx_th_c_005fif_005f40.doAfterBody();
/* 4739 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4743 */     if (_jspx_th_c_005fif_005f40.doEndTag() == 5) {
/* 4744 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f40);
/* 4745 */       return true;
/*      */     }
/* 4747 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f40);
/* 4748 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f41(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4753 */     PageContext pageContext = _jspx_page_context;
/* 4754 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4756 */     IfTag _jspx_th_c_005fif_005f41 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4757 */     _jspx_th_c_005fif_005f41.setPageContext(_jspx_page_context);
/* 4758 */     _jspx_th_c_005fif_005f41.setParent(null);
/*      */     
/* 4760 */     _jspx_th_c_005fif_005f41.setTest("${globalconfig['mailserverconfigured'] != 'true'}");
/* 4761 */     int _jspx_eval_c_005fif_005f41 = _jspx_th_c_005fif_005f41.doStartTag();
/* 4762 */     if (_jspx_eval_c_005fif_005f41 != 0) {
/*      */       for (;;) {
/* 4764 */         out.write("\n\t\t\tjQuery('input:radio[name=ticketingType]')[1].disabled = false;\n\t\t");
/* 4765 */         int evalDoAfterBody = _jspx_th_c_005fif_005f41.doAfterBody();
/* 4766 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4770 */     if (_jspx_th_c_005fif_005f41.doEndTag() == 5) {
/* 4771 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f41);
/* 4772 */       return true;
/*      */     }
/* 4774 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f41);
/* 4775 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f42(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4780 */     PageContext pageContext = _jspx_page_context;
/* 4781 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4783 */     IfTag _jspx_th_c_005fif_005f42 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4784 */     _jspx_th_c_005fif_005f42.setPageContext(_jspx_page_context);
/* 4785 */     _jspx_th_c_005fif_005f42.setParent(null);
/*      */     
/* 4787 */     _jspx_th_c_005fif_005f42.setTest("${AMActionForm.priority ne ''}");
/* 4788 */     int _jspx_eval_c_005fif_005f42 = _jspx_th_c_005fif_005f42.doStartTag();
/* 4789 */     if (_jspx_eval_c_005fif_005f42 != 0) {
/*      */       for (;;) {
/* 4791 */         out.write("\n\t\t\t$(\"select[name=priority] option[value='");
/* 4792 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f42, _jspx_page_context))
/* 4793 */           return true;
/* 4794 */         out.write("']:first\").attr(\"selected\", \"selected\"); //NO I18N\n\t\t");
/* 4795 */         int evalDoAfterBody = _jspx_th_c_005fif_005f42.doAfterBody();
/* 4796 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4800 */     if (_jspx_th_c_005fif_005f42.doEndTag() == 5) {
/* 4801 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f42);
/* 4802 */       return true;
/*      */     }
/* 4804 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f42);
/* 4805 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f42, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4810 */     PageContext pageContext = _jspx_page_context;
/* 4811 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4813 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4814 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4815 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f42);
/*      */     
/* 4817 */     _jspx_th_c_005fout_005f9.setValue("${AMActionForm.priority}");
/* 4818 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4819 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4820 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4821 */       return true;
/*      */     }
/* 4823 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4824 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f43(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4829 */     PageContext pageContext = _jspx_page_context;
/* 4830 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4832 */     IfTag _jspx_th_c_005fif_005f43 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4833 */     _jspx_th_c_005fif_005f43.setPageContext(_jspx_page_context);
/* 4834 */     _jspx_th_c_005fif_005f43.setParent(null);
/*      */     
/* 4836 */     _jspx_th_c_005fif_005f43.setTest("${isIt360 eq true}");
/* 4837 */     int _jspx_eval_c_005fif_005f43 = _jspx_th_c_005fif_005f43.doStartTag();
/* 4838 */     if (_jspx_eval_c_005fif_005f43 != 0) {
/*      */       for (;;) {
/* 4840 */         out.write("\n\t\t\t$('#ticketType1').css('display','none'); //NO I18N\n\t\t\t$('#ticketType2').css('display','none'); //NO I18N\n\t\t");
/* 4841 */         int evalDoAfterBody = _jspx_th_c_005fif_005f43.doAfterBody();
/* 4842 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4846 */     if (_jspx_th_c_005fif_005f43.doEndTag() == 5) {
/* 4847 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f43);
/* 4848 */       return true;
/*      */     }
/* 4850 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f43);
/* 4851 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f45(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4856 */     PageContext pageContext = _jspx_page_context;
/* 4857 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4859 */     IfTag _jspx_th_c_005fif_005f45 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4860 */     _jspx_th_c_005fif_005f45.setPageContext(_jspx_page_context);
/* 4861 */     _jspx_th_c_005fif_005f45.setParent(null);
/*      */     
/* 4863 */     _jspx_th_c_005fif_005f45.setTest("${isIT360MSP eq true}");
/* 4864 */     int _jspx_eval_c_005fif_005f45 = _jspx_th_c_005fif_005f45.doStartTag();
/* 4865 */     if (_jspx_eval_c_005fif_005f45 != 0) {
/*      */       for (;;) {
/* 4867 */         out.write("\n\t\t\t/* if(document.AMActionForm.siteName.value != chooseAValue)\n\t\t\t{\n\t\t\t\tdocument.getElementById(\"dummySites\").style.display='none'; \n\t\t\t\tdocument.getElementById(\"sites\").style.display='block'; \n\t\t\t\tdocument.getElementById(\"sites\").innerHTML=\"<select name='sname' style='width: 100%;'><option value='\"+document.AMActionForm.siteName.value+\"'> \"+document.AMActionForm.siteName.value+\"</option></select>\"\n\t\t\t}\n\t\t\tif(document.AMActionForm.siteName.value == '')\n\t\t\t{\n\t\t\t\tdocument.getElementById(\"dummySites\").style.display='block'; \n\t\t\t\tdocument.getElementById(\"sites\").style.display='none'; \n\t\t\t}\n\t\t\t */\n\t\t\t if(document.AMActionForm.reqName.value != chooseAValue)\n\t\t\t{\n\t\t\t\t/*document.getElementById(\"dummyReqs\").style.display='none'; \n\t\t\t\tdocument.getElementById(\"requesters\").style.display='block'; \n\t\t\t\tdocument.getElementById(\"requesters\").innerHTML=\"<select name='rname' style='width: 100%;'><option value='\"+document.AMActionForm.reqName.value+\"'> \"+document.AMActionForm.reqName.value+\"</option></select>\"*/\n\t\t\t\tdocument.getElementById(\"dummyReqs\").style.display='block';\n");
/* 4868 */         out.write("\t\t\t}\n\t\t\tif(document.AMActionForm.reqName.value == '')\n\t\t\t{\n\t\t\t\tdocument.getElementById(\"dummyReqs\").style.display='block'; \n\t\t\t\tdocument.getElementById(\"requesters\").style.display='none'; \n\t\t\t}\n\t");
/* 4869 */         int evalDoAfterBody = _jspx_th_c_005fif_005f45.doAfterBody();
/* 4870 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4874 */     if (_jspx_th_c_005fif_005f45.doEndTag() == 5) {
/* 4875 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f45);
/* 4876 */       return true;
/*      */     }
/* 4878 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f45);
/* 4879 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\TicketActionForm_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */