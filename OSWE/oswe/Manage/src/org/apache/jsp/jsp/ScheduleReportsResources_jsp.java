/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.customfields.MyFields;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Properties;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.MultiboxTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ScheduleReportsResources_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   39 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */   private static java.util.Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   61 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   65 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   75 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   76 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   77 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   78 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   82 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   83 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   84 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*   85 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   86 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*   87 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   88 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   89 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*   90 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.release();
/*   91 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*   92 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.release();
/*   93 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*  100 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  103 */     JspWriter out = null;
/*  104 */     Object page = this;
/*  105 */     JspWriter _jspx_out = null;
/*  106 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  110 */       response.setContentType("text/html;charset=UTF-8");
/*  111 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  113 */       _jspx_page_context = pageContext;
/*  114 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  115 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  116 */       session = pageContext.getSession();
/*  117 */       out = pageContext.getOut();
/*  118 */       _jspx_out = out;
/*      */       
/*  120 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<script>\nmyHash = {};\nmyHash1 = {};\n");
/*      */       
/*  122 */       AMActionForm frm = (AMActionForm)request.getAttribute("AMActionForm");
/*      */       try
/*      */       {
/*  125 */         ArrayList toAdd = frm.getToAdd();
/*  126 */         ArrayList toAddg = frm.getToAddg();
/*  127 */         Iterator it = toAdd.iterator();
/*  128 */         while (it.hasNext())
/*      */         {
/*  130 */           Properties p = (Properties)it.next();
/*  131 */           String val = String.valueOf(p.get("value"));
/*  132 */           String key = String.valueOf(p.get("label"));
/*      */           
/*  134 */           out.write("\n    myHash['");
/*  135 */           out.print(key);
/*  136 */           out.write("'] = '");
/*  137 */           out.print(val);
/*  138 */           out.write(39);
/*  139 */           out.write(59);
/*  140 */           out.write(10);
/*      */         }
/*      */         
/*  143 */         Iterator it1 = toAddg.iterator();
/*  144 */         while (it1.hasNext())
/*      */         {
/*  146 */           Properties p = (Properties)it1.next();
/*  147 */           String val = String.valueOf(p.get("value"));
/*  148 */           String key = String.valueOf(p.get("label"));
/*      */           
/*  150 */           out.write("\n    myHash1['");
/*  151 */           out.print(key);
/*  152 */           out.write("'] = '");
/*  153 */           out.print(val);
/*  154 */           out.write(39);
/*  155 */           out.write(59);
/*  156 */           out.write(10);
/*      */         }
/*      */       }
/*      */       catch (Exception e) {}
/*      */       
/*      */ 
/*      */ 
/*  163 */       out.write("\n\nfunction getKeywordMatchedMonitors()\n{\n\tvar keywd = document.getElementById('searchByName').value.trim().toLowerCase();\n\tvar matchedMonsArr = new Array();\n\tvar k=0;\n\tfor (var name in myHash) {\n\t\tvar tempTxt = name ;\n\t\tvar tempVal = myHash[name];\n\t\tif(tempTxt.toLowerCase().indexOf(keywd) != -1)\n\t\t{ \n\t\t\tmatchedMonsArr[k]=tempTxt+\"=\"+tempVal; \n\t\t\tk++;\n\t\t}\n\t}\n\tvar len = matchedMonsArr.length;\n\t \n\tif(len > 0)\n\t{\n\t\tdocument.AMActionForm.scheduleReportResCombo1.options.length =0;\n\t\tfor(var j=0; j<len; j++)\n\t\t{\n\t\t\tvar t = matchedMonsArr[j].split(\"=\");\n\t\t\tvar tempOpt = new Option(t[0], t[1]);\n\t\t\tdocument.AMActionForm.scheduleReportResCombo1.options[j] = tempOpt;\n\t\t}\n\t}\n\telse\n\t{\n\t\talert(\"");
/*  164 */       out.print(FormatUtil.getString("am.webclient.downtimeschedular.search.nomatchfound.text"));
/*  165 */       out.write("\");\n\t}\t \n}\n\nfunction getKeywordMatchedDashboards()\n{\n\tvar keywd = document.getElementById('searchBydashboardName').value.trim().toLowerCase();\n\tvar matchedDashboardsArr = new Array();\n\tvar k=0;\n\tfor (var name in myHash1) {\n\t\tvar tempTxt = name ;\n\t\tvar tempVal = myHash1[name];\n\t\tif(tempTxt.toLowerCase().indexOf(keywd) != -1)\n\t\t{ \n\t\t\tmatchedDashboardsArr[k]=tempTxt+\"=\"+tempVal; \n\t\t\tk++;\n\t\t}\n\t}\n\tvar len = matchedDashboardsArr.length;\n\t \n\tif(len > 0)\n\t{\n\t\tdocument.AMActionForm.scheduleReportDashboardCombo1.options.length =0;\n\t\tfor(var j=0; j<len; j++)\n\t\t{\n\t\t\tvar t = matchedDashboardsArr[j].split(\"=\");\n\t\t\tvar tempOpt = new Option(t[0], t[1]);\n\t\t\tdocument.AMActionForm.scheduleReportDashboardCombo1.options[j] = tempOpt;\n\t\t}\n\t}\n\telse\n\t{\n\t\talert(\"");
/*  166 */       out.print(FormatUtil.getString("am.webclient.downtimeschedular.search.nomatchfound.text"));
/*  167 */       out.write("\");\n\t\t\n\t}\t \n}\n</script>\n\n");
/*      */       
/*  169 */       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  170 */       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  171 */       _jspx_th_c_005fchoose_005f0.setParent(null);
/*  172 */       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  173 */       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */         for (;;) {
/*  175 */           out.write(10);
/*      */           
/*  177 */           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  178 */           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  179 */           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  181 */           _jspx_th_c_005fwhen_005f0.setTest("${param.customfieldfilter == true }");
/*  182 */           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  183 */           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */             for (;;) {
/*  185 */               out.write("\n\n\t\t<table cellspacing=\"0\" cellpadding=\"5\" border=\"0\" width=\"100%\">\n\t\t\t<tr height=\"25\">\n\t\t\t\t<td class=\"bodytext label-align\" width=\"25%\">");
/*  186 */               out.print(FormatUtil.getString("am.webclient.reports.filterby.customfield.text"));
/*  187 */               out.write("</td>\n\t\t\t\t<td class=\"bodytext\" width=\"75%\">\n\t\t\t\t\t<div style=\"float: left;\"><select id=\"filterScheduleReport\" name=\"filterScheduleReport\" class=\"formtext medium\"  onchange='loadURLType(this.options[this.selectedIndex].value,this)'\t>\n\t\t\t\t\t\t<option  value=\"no\" >");
/*  188 */               out.print(FormatUtil.getString("No"));
/*  189 */               out.write("</option>\n\t\t\t\t\t\t");
/*  190 */               if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/*  191 */                 out.write("\n\t\t\t\t\t\t");
/*      */                 
/*  193 */                 SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  194 */                 _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  195 */                 _jspx_th_c_005fset_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                 
/*  197 */                 _jspx_th_c_005fset_005f0.setVar("fieldId");
/*  198 */                 int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  199 */                 if (_jspx_eval_c_005fset_005f0 != 0) {
/*  200 */                   if (_jspx_eval_c_005fset_005f0 != 1) {
/*  201 */                     out = _jspx_page_context.pushBody();
/*  202 */                     _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  203 */                     _jspx_th_c_005fset_005f0.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  206 */                     out.print(request.getParameter("customFieldId") != null ? request.getParameter("customFieldId") : "");
/*  207 */                     int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  208 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  211 */                   if (_jspx_eval_c_005fset_005f0 != 1) {
/*  212 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  215 */                 if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  216 */                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */                 }
/*      */                 
/*  219 */                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  220 */                 out.write("\n\t\t\t\t \t\t\t");
/*      */                 
/*  222 */                 IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  223 */                 _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  224 */                 _jspx_th_c_005fif_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                 
/*  226 */                 _jspx_th_c_005fif_005f0.setTest("${not empty dollartags }");
/*  227 */                 int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  228 */                 if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                   for (;;) {
/*  230 */                     out.write("\n\t\t\t\t \t\t\t\t<optgroup label=\"");
/*  231 */                     out.print(FormatUtil.getString("am.myfield.customfield.text"));
/*  232 */                     out.write("\" style=\"background-color: #FFF8C6\"></optgroup>\n\t\t\t\t\t\t\t\t\t");
/*      */                     
/*  234 */                     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/*  235 */                     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/*  236 */                     _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f0);
/*      */                     
/*  238 */                     _jspx_th_c_005fforEach_005f0.setVar("eachtag");
/*      */                     
/*  240 */                     _jspx_th_c_005fforEach_005f0.setItems("${dollartags}");
/*  241 */                     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                     try {
/*  243 */                       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/*  244 */                       if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                         for (;;) {
/*  246 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t");
/*      */                           
/*  248 */                           SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  249 */                           _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  250 */                           _jspx_th_c_005fset_005f1.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                           
/*  252 */                           _jspx_th_c_005fset_005f1.setVar("selected");
/*  253 */                           int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  254 */                           if (_jspx_eval_c_005fset_005f1 != 0) {
/*  255 */                             if (_jspx_eval_c_005fset_005f1 != 1) {
/*  256 */                               out = _jspx_page_context.pushBody();
/*  257 */                               _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/*  258 */                               _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/*  259 */                               _jspx_th_c_005fset_005f1.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  262 */                               out.print("");
/*  263 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/*  264 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  267 */                             if (_jspx_eval_c_005fset_005f1 != 1) {
/*  268 */                               out = _jspx_page_context.popBody();
/*  269 */                               _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */                             }
/*      */                           }
/*  272 */                           if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  273 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
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
/*  356 */                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  357 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                           }
/*  276 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/*  277 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t");
/*      */                           
/*  279 */                           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  280 */                           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  281 */                           _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                           
/*  283 */                           _jspx_th_c_005fif_005f1.setTest("${fieldId==eachtag.fieldid}");
/*  284 */                           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  285 */                           if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                             for (;;) {
/*  287 */                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                               
/*  289 */                               SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  290 */                               _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/*  291 */                               _jspx_th_c_005fset_005f2.setParent(_jspx_th_c_005fif_005f1);
/*      */                               
/*  293 */                               _jspx_th_c_005fset_005f2.setVar("selected");
/*  294 */                               int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/*  295 */                               if (_jspx_eval_c_005fset_005f2 != 0) {
/*  296 */                                 if (_jspx_eval_c_005fset_005f2 != 1) {
/*  297 */                                   out = _jspx_page_context.pushBody();
/*  298 */                                   _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/*  299 */                                   _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/*  300 */                                   _jspx_th_c_005fset_005f2.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/*  303 */                                   out.print("selected=selected");
/*  304 */                                   int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/*  305 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*  308 */                                 if (_jspx_eval_c_005fset_005f2 != 1) {
/*  309 */                                   out = _jspx_page_context.popBody();
/*  310 */                                   _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */                                 }
/*      */                               }
/*  313 */                               if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/*  314 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
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
/*  356 */                                 _jspx_th_c_005fforEach_005f0.doFinally();
/*  357 */                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                               }
/*  317 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/*  318 */                               out.write("\n\t\t\t\t\t\t\t\t\t\t");
/*  319 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  320 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  324 */                           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  325 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
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
/*  356 */                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  357 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                           }
/*  328 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  329 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t<option style=\"background-color: #FFF8C6\" ");
/*  330 */                           if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                           {
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
/*  356 */                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  357 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                           }
/*  332 */                           out.write(" value=\"");
/*  333 */                           if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                           {
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
/*  356 */                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  357 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                           }
/*  335 */                           out.write(36);
/*  336 */                           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                           {
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
/*  356 */                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  357 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                           }
/*  338 */                           out.write(34);
/*  339 */                           out.write(62);
/*  340 */                           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                           {
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
/*  356 */                             _jspx_th_c_005fforEach_005f0.doFinally();
/*  357 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                           }
/*  342 */                           out.write("</option>\n\t\t\t\t\t\t\t\t\t");
/*  343 */                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/*  344 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  348 */                       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  356 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/*  357 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                       }
/*      */                     }
/*      */                     catch (Throwable _jspx_exception)
/*      */                     {
/*      */                       for (;;)
/*      */                       {
/*  352 */                         int tmp1591_1590 = 0; int[] tmp1591_1588 = _jspx_push_body_count_c_005fforEach_005f0; int tmp1593_1592 = tmp1591_1588[tmp1591_1590];tmp1591_1588[tmp1591_1590] = (tmp1593_1592 - 1); if (tmp1593_1592 <= 0) break;
/*  353 */                         out = _jspx_page_context.popBody(); }
/*  354 */                       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                     } finally {
/*  356 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/*  357 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                     }
/*  359 */                     out.write("\n\t\t\t\t\t\t\t");
/*  360 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  361 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  365 */                 if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  366 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                 }
/*      */                 
/*  369 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  370 */                 out.write("\n\t\t\t\t\t\t\t");
/*      */                 
/*  372 */                 IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  373 */                 _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  374 */                 _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                 
/*  376 */                 _jspx_th_c_005fif_005f2.setTest("${empty dollartags}");
/*  377 */                 int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  378 */                 if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                   for (;;) {
/*  380 */                     out.write("\n\t\t\t\t\t\t\t\t<optgroup  style=\"background-color: #FFF8C6\" label=\"");
/*  381 */                     out.print(FormatUtil.getString("am.webclient.reports.customfield.nofield.text"));
/*  382 */                     out.write("\"></optgroup>\n\t\t\t\t\t\t\t");
/*  383 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  384 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  388 */                 if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  389 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                 }
/*      */                 
/*  392 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  393 */                 out.write("\n\t\t\t\t\t\t");
/*      */               }
/*  395 */               out.write("\n\t\t\t\t \t</select>&nbsp;&nbsp;&nbsp;</div>\n\t\t\t\t \t<div id=\"filterByOption\" style=\"float: left;\"></div>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\n");
/*  396 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  397 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  401 */           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  402 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */           }
/*      */           
/*  405 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  406 */           out.write(10);
/*      */           
/*  408 */           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  409 */           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  410 */           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  411 */           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  412 */           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */             for (;;) {
/*  414 */               out.write("\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\" >\n<tr><td>\n<!--the form is commented bcoz the IE 6 application will not allow multiple form in a single page so form is commented dont remove the comment-->\n<!--");
/*      */               
/*  416 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  417 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  418 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */               
/*  420 */               _jspx_th_html_005fform_005f0.setAction("/scheduleReports");
/*      */               
/*  422 */               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/*  423 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  424 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/*  426 */                   out.write("-->\n");
/*      */                   
/*  428 */                   int totColumnPerRow = 4;
/*  429 */                   ManagedApplication mo = new ManagedApplication();
/*  430 */                   String reportname = null;
/*  431 */                   String attribute = null;
/*  432 */                   String resource = null;
/*  433 */                   String isCustom = null;
/*  434 */                   String ResGroupName = null;
/*  435 */                   String ResGroup = null;
/*  436 */                   String resTYPE = null;
/*  437 */                   String oldresourcetypes = null;
/*  438 */                   String customfield = null;
/*  439 */                   String customfieldvalue = null;
/*  440 */                   String aliasname = null;
/*  441 */                   String customValue = null;
/*  442 */                   String datatable = "";
/*  443 */                   String querycon = "";
/*  444 */                   String fieldsDataTable = "";
/*  445 */                   String groupmonitors = "";
/*  446 */                   String attributeFilterCondition = "";
/*  447 */                   String attributeTablename = "";
/*  448 */                   boolean isCustomFieldFilter = false;
/*  449 */                   String adminResourceIdentity = "";
/*  450 */                   if (com.adventnet.appmanager.struts.beans.ClientDBUtil.isPrivilegedUser(request)) {
/*  451 */                     if (com.adventnet.appmanager.util.Constants.isUserResourceEnabled()) {
/*  452 */                       String loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/*  453 */                       adminResourceIdentity = " AND AM_ManagedObject.RESOURCEID in (select RESOURCEID FROM AM_USERRESOURCESTABLE where USERID=" + loginUserid + ")";
/*      */                     } else {
/*  455 */                       Vector resIds_vector = com.adventnet.appmanager.struts.beans.ClientDBUtil.getResourceIdentity(request.getRemoteUser());
/*  456 */                       adminResourceIdentity = " AND " + EnterpriseUtil.getCondition("AM_ManagedObject.RESOURCEID", resIds_vector) + " ";
/*      */                     }
/*      */                   }
/*      */                   
/*  460 */                   HashMap<String, String> querycondition = new HashMap();
/*  461 */                   int chkid = 0;
/*  462 */                   if (request.getParameter("report") != null)
/*      */                   {
/*  464 */                     reportname = request.getParameter("report");
/*  465 */                     if (request.getParameter("attribute") != null)
/*      */                     {
/*  467 */                       attribute = request.getParameter("attribute");
/*      */                     }
/*  469 */                     resource = request.getParameter("resource");
/*  470 */                     isCustom = request.getParameter("isCustom");
/*  471 */                     resTYPE = request.getParameter("resourcetypes");
/*      */                     
/*  473 */                     oldresourcetypes = request.getParameter("oldresourcetypes");
/*      */                     
/*  475 */                     ResGroupName = request.getParameter("resgroupname");
/*  476 */                     ResGroup = request.getParameter("resgroup");
/*  477 */                     customfield = request.getParameter("customfield");
/*  478 */                     customfieldvalue = request.getParameter("customFieldValue");
/*  479 */                     if ((customfieldvalue != null) && (customfieldvalue.indexOf("$") != -1)) {
/*  480 */                       isCustomFieldFilter = true;
/*  481 */                       aliasname = customfieldvalue.substring(0, customfieldvalue.indexOf("$"));
/*  482 */                       customValue = customfieldvalue.substring(customfieldvalue.indexOf("$") + 1);
/*  483 */                       querycondition = MyFields.customCondition(aliasname, customValue, null, false);
/*  484 */                       datatable = (String)querycondition.get("dataTable");
/*  485 */                       querycon = (String)querycondition.get("qryCon");
/*  486 */                       fieldsDataTable = (String)querycondition.get("groupTable");
/*  487 */                       groupmonitors = (String)querycondition.get("groupmonitors");
/*  488 */                       attributeFilterCondition = " and " + fieldsDataTable + ".RESOURCEID=AM_HOLISTICAPPLICATION.HAID";
/*  489 */                       attributeTablename = "," + fieldsDataTable;
/*      */                     }
/*      */                     
/*  492 */                     if ("SYS".equals(ResGroup)) {
/*  493 */                       resTYPE = "AIX','FreeBSD','HP-UX','Linux','Mac OS','Sun','Windows','Novell";
/*      */                     }
/*      */                   }
/*      */                   
/*  497 */                   if (oldresourcetypes != null) {
/*  498 */                     if ((oldresourcetypes.equals("HP-UX")) || (oldresourcetypes.equals("HP-TRU64")) || (oldresourcetypes.equals("HP-UX / Tru64"))) {
/*  499 */                       oldresourcetypes = "HP-UX";
/*      */                     }
/*  501 */                     else if (oldresourcetypes.equals("Sun Solaris")) {
/*  502 */                       oldresourcetypes = "SUN";
/*  503 */                     } else if (oldresourcetypes.equals("FreeBSD / OpenBSD")) {
/*  504 */                       oldresourcetypes = "FreeBSD";
/*      */                     }
/*      */                   }
/*      */                   
/*  508 */                   String query = null;
/*  509 */                   String resourceTypes = null;
/*      */                   
/*  511 */                   if ((resource.equalsIgnoreCase("allserver")) || (resource.equalsIgnoreCase("slaserver"))) {
/*  512 */                     out.write("\n    \t  \n    \t  \n    \t  \n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\">\t\t\t\t\t\t\t\n\t\t\t<tr>\n\t\t\t\t<td class=\"bodytext\">\n\t\t\t\t\t<div id='srchmonsdiv'>\n\t\t\t\t\t&nbsp;");
/*  513 */                     out.print(FormatUtil.getString("am.webclient.slaview.search.resources"));
/*  514 */                     out.write(" : <span class=\"input-downtime\"><input type='text' name='searchByName' class='formtext' id='searchByName'  onkeydown=\"if (event.keyCode == 13) document.getElementById('srchBt').click()\"></span>\n\t\t\t\t\t&nbsp; &nbsp; <strong><input type=\"button\" class=\"buttons\" value='");
/*  515 */                     out.print(FormatUtil.getString("Go"));
/*  516 */                     out.write("' name=\"srchBt\" id=\"srchBt\" onclick='getKeywordMatchedMonitors();'></strong>\n\n\t\t\t\t\t</div>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td>\t\t\t\t\t\n\t\t\t\t\t<div id=\"allmonitors\">\t\t\t\t\t\n\t\t\t\t\t\t<table width=\"100%\">\n\t\t\t\t\t\t\t<tr width=\"100%\">\n\t\t\t\t\t\t\t\t<td width=\"46%\" align=\"center\" class=\"bodytext\">");
/*  517 */                     out.print(FormatUtil.getString("am.webclient.maintenance.unassociatedmonitors"));
/*  518 */                     out.write("</td>\n\t\t\t\t\t\t\t\t<td width=\"8%\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td width=\"46%\" align=\"center\" class=\"bodytext\">");
/*  519 */                     out.print(FormatUtil.getString("am.webclient.maintenance.associatedmonitors"));
/*  520 */                     out.write("</td>\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr width=\"100%\">\n\t\t\t\t\t\t\t\t<td width=\"46%\">\t\t\n\t\t\t\t\t\t\t\t\t");
/*  521 */                     if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  523 */                     out.write("\t\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td width=\"8%\" align=\"center\" class=\"bodytext\">\n\t\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\"><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.scheduleReportResCombo1,document.AMActionForm.scheduleReportResCombo2),fnRemoveFromRightCombo(document.AMActionForm.scheduleReportResCombo1);\" value=\"&nbsp;&gt;&nbsp;\"></td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t<tr><td height=\"5\"></td></tr>\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td  align=\"center\"><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:frmSelectAllIncludingEmpty(document.AMActionForm.scheduleReportResCombo1),fnAddToRightCombo(document.AMActionForm.scheduleReportResCombo1,document.AMActionForm.scheduleReportResCombo2),fnRemoveFromRightCombo(document.AMActionForm.scheduleReportResCombo1);\" value=\"&gt;&gt;\"></td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td  align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:frmSelectAllIncludingEmpty(document.AMActionForm.scheduleReportResCombo2),fnAddToRightCombo(document.AMActionForm.scheduleReportResCombo2,document.AMActionForm.scheduleReportResCombo1),fnRemoveFromRightCombo(document.AMActionForm.scheduleReportResCombo2);\" value=\"&lt;&lt;\"></td>\n");
/*  524 */                     out.write("\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t<tr><td height=\"5\"></td></tr>\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td  align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.scheduleReportResCombo2,document.AMActionForm.scheduleReportResCombo1),fnRemoveFromRightCombo(document.AMActionForm.scheduleReportResCombo2);\" value=\"&nbsp;&lt;&nbsp;\"></td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td width=\"46%\">\t\n\t\t\t\t\t\t\t\t\t");
/*  525 */                     if (_jspx_meth_html_005fselect_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  527 */                     out.write("\n\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\t\t\t\t\t\n\t\t\t\t\t</div>\t\t\t\t\t\t\t\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\t\n    ");
/*      */ 
/*      */                   }
/*  530 */                   else if (("allba".equalsIgnoreCase(resource)) || ("slaba".equalsIgnoreCase(resource)))
/*      */                   {
/*      */ 
/*  533 */                     out.write("\n    \t\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\">\t\t\t\t\t\t\t\n\t\t\t<tr>\n\t\t\t\t<td class=\"bodytext\">\n\t\t\t\t\t<div id='srchmonsdiv'>\n\t\t\t\t\t&nbsp;");
/*  534 */                     out.print(FormatUtil.getString("am.webclient.slaview.search.resources"));
/*  535 */                     out.write(" : <span class=\"input-downtime\"><input type='text' name='searchByName' class='formtext' id='searchByName'  onkeydown=\"if (event.keyCode == 13) document.getElementById('srchBt').click()\"></span>\n\t\t\t\t\t&nbsp; &nbsp; <strong><input type=\"button\" class=\"buttons\" value='");
/*  536 */                     out.print(FormatUtil.getString("Go"));
/*  537 */                     out.write("' name=\"srchBt\" id=\"srchBt\" onclick='getKeywordMatchedMonitors();'></strong>\n\n\t\t\t\t\t</div>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td>\t\t\t\t\t\n\t\t\t\t\t<div id=\"allmonitors\">\t\t\t\t\t\n\t\t\t\t\t\t<table width=\"100%\">\n\t\t\t\t\t\t\t<tr width=\"100%\">\n\t\t\t\t\t\t\t\t<td width=\"46%\" align=\"center\" class=\"bodytext\">");
/*  538 */                     out.print(FormatUtil.getString("am.webclient.manager.newsla.availableba.text"));
/*  539 */                     out.write("</td>\n\t\t\t\t\t\t\t\t<td width=\"8%\">&nbsp;</td>\n\t\t\t\t\t\t\t\t<td width=\"46%\" align=\"center\" class=\"bodytext\">");
/*  540 */                     out.print(FormatUtil.getString("am.webclient.manager.newsla.selectedba.text"));
/*  541 */                     out.write("</td>\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr width=\"100%\">\n\t\t\t\t\t\t\t\t<td width=\"46%\">\t\t\n\t\t\t\t\t\t\t\t\t");
/*  542 */                     if (_jspx_meth_html_005fselect_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  544 */                     out.write("\t\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td width=\"8%\" align=\"center\" class=\"bodytext\">\n\t\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\"><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.scheduleReportResCombo1,document.AMActionForm.scheduleReportResCombo2),fnRemoveFromRightCombo(document.AMActionForm.scheduleReportResCombo1);\" value=\"&nbsp;&gt;&nbsp;\"></td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t<tr><td height=\"5\"></td></tr>\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td  align=\"center\"><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:frmSelectAllIncludingEmpty(document.AMActionForm.scheduleReportResCombo1),fnAddToRightCombo(document.AMActionForm.scheduleReportResCombo1,document.AMActionForm.scheduleReportResCombo2),fnRemoveFromRightCombo(document.AMActionForm.scheduleReportResCombo1);\" value=\"&gt;&gt;\"></td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td  align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:frmSelectAllIncludingEmpty(document.AMActionForm.scheduleReportResCombo2),fnAddToRightCombo(document.AMActionForm.scheduleReportResCombo2,document.AMActionForm.scheduleReportResCombo1),fnRemoveFromRightCombo(document.AMActionForm.scheduleReportResCombo2);\" value=\"&lt;&lt;\"></td>\n");
/*  545 */                     out.write("\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t<tr><td height=\"5\"></td></tr>\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td  align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.scheduleReportResCombo2,document.AMActionForm.scheduleReportResCombo1),fnRemoveFromRightCombo(document.AMActionForm.scheduleReportResCombo2);\" value=\"&nbsp;&lt;&nbsp;\"></td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td width=\"46%\">\t\n\t\t\t\t\t\t\t\t\t");
/*  546 */                     if (_jspx_meth_html_005fselect_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  548 */                     out.write("\n\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\t\t\t\t\t\n\t\t\t\t\t</div>\t\t\t\t\t\t\t\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\t\n\n\n\n\n\n\n     ");
/*      */                   }
/*  550 */                   else if (reportname.equalsIgnoreCase("dashboard")) {
/*  551 */                     out.write("\n \t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\">\t\t\t\t\t\t\t\n\t\t<tr>\n\t\t\t<td class=\"bodytext\">\n\t\t\t\t<div id='srchdashboardsdiv'>\n\t\t\t\t&nbsp;");
/*  552 */                     out.print(FormatUtil.getString("am.webclient.dashboard.search.resources"));
/*  553 */                     out.write(" : <span class=\"input-downtime\"><input type='text' name='searchBydashboardName' class='formtext' id='searchBydashboardName'  onkeydown=\"if (event.keyCode == 13) document.getElementById('srchBt1').click()\"></span>\n\t\t\t\t&nbsp; &nbsp; <strong><input type=\"button\" class=\"buttons\" value='");
/*  554 */                     out.print(FormatUtil.getString("Go"));
/*  555 */                     out.write("' name=\"srchBt1\" id=\"srchBt1\" onclick='getKeywordMatchedDashboards();'></strong>\n\n\t\t\t\t</div>\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>\t\t\t\t\t\n\t\t\t\t<div id=\"alldashboards\">\t\t\t\t\t\n\t\t\t\t\t<table width=\"100%\">\n\t\t\t\t\t\t<tr width=\"100%\">\n\t\t\t\t\t\t\t<td width=\"46%\" align=\"center\" class=\"bodytext\">");
/*  556 */                     out.print(FormatUtil.getString("am.webclient.manager.dashboard.available.text"));
/*  557 */                     out.write("</td>\n\t\t\t\t\t\t\t<td width=\"8%\">&nbsp;</td>\n\t\t\t\t\t\t\t<td width=\"46%\" align=\"center\" class=\"bodytext\">");
/*  558 */                     out.print(FormatUtil.getString("am.webclient.manager.dashboard.selected.text"));
/*  559 */                     out.write("</td>\t\t\t\t\t\t\t\n\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\n\t\t\t\t\t\t<tr width=\"100%\">\n\t\t\t\t\t\t\t<td width=\"46%\">\t\t\n\t\t\t\t\t\t\t\t");
/*  560 */                     if (_jspx_meth_html_005fselect_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  562 */                     out.write("\t\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"8%\" align=\"center\" class=\"bodytext\">\n\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td align=\"center\"><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.scheduleReportDashboardCombo1,document.AMActionForm.scheduleReportDashboardCombo2),fnRemoveFromRightCombo(document.AMActionForm.scheduleReportDashboardCombo1);\" value=\"&nbsp;&gt;&nbsp;\"></td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr><td height=\"5\"></td></tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td  align=\"center\"><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:frmSelectAllIncludingEmpty(document.AMActionForm.scheduleReportDashboardCombo1),fnAddToRightCombo(document.AMActionForm.scheduleReportDashboardCombo1,document.AMActionForm.scheduleReportDashboardCombo2),fnRemoveFromRightCombo(document.AMActionForm.scheduleReportDashboardCombo1);\" value=\"&gt;&gt;\"></td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\t\t\t\t\t\t\t\t\t<tr>\n");
/*  563 */                     out.write("\t\t\t\t\t\t\t\t\t\t<td  align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:frmSelectAllIncludingEmpty(document.AMActionForm.scheduleReportDashboardCombo2),fnAddToRightCombo(document.AMActionForm.scheduleReportDashboardCombo2,document.AMActionForm.scheduleReportDashboardCombo1),fnRemoveFromRightCombo(document.AMActionForm.scheduleReportDashboardCombo2);\" value=\"&lt;&lt;\"></td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr><td height=\"5\"></td></tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td  align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddToRightCombo(document.AMActionForm.scheduleReportDashboardCombo2,document.AMActionForm.scheduleReportDashboardCombo1),fnRemoveFromRightCombo(document.AMActionForm.scheduleReportDashboardCombo2);\" value=\"&nbsp;&lt;&nbsp;\"></td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"46%\">\t\n\t\t\t\t\t\t\t\t");
/*  564 */                     if (_jspx_meth_html_005fselect_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  566 */                     out.write("\n\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\t\t\t\t\t\n\t\t\t\t</div>\t\t\t\t\t\t\t\n\t\t\t</td>\n\t\t</tr>\n\t</table>\t\n");
/*      */ 
/*      */                   }
/*  569 */                   else if ((resource.equalsIgnoreCase("monitor")) && ((reportname.equalsIgnoreCase("availability")) || (reportname.equalsIgnoreCase("health")) || (reportname.equalsIgnoreCase("glancereport")) || (((reportname.equalsIgnoreCase("attribute")) || (reportname.equalsIgnoreCase("forecast"))) && (attribute != null) && (attribute.equalsIgnoreCase("responseTime")))))
/*      */                   {
/*  571 */                     ArrayList aListSUBGROUP = new ArrayList();
/*  572 */                     aListSUBGROUP.add("Windows");
/*  573 */                     aListSUBGROUP.add("HP-UX / Tru64");
/*      */                     
/*  575 */                     out.write("\n\t             <table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\">\n\t  \t\t\t <tr>\n\t  \n\t       \n\t             ");
/*      */                     
/*  577 */                     String idNum = "";
/*  578 */                     String idName = "";
/*  579 */                     String shortName = "";
/*  580 */                     String scriptFunc = "";
/*  581 */                     int dispColumnCount = 0;
/*  582 */                     StringBuffer queryBuff = new StringBuffer();
/*  583 */                     for (int n = 0; n < com.adventnet.appmanager.util.Constants.categoryLink.length; n++)
/*      */                     {
/*  585 */                       if ((!com.adventnet.appmanager.util.Constants.categoryLink[n].equals("SCR")) && (!com.adventnet.appmanager.util.Constants.categoryLink[n].equals("SAN")) && (!com.adventnet.appmanager.util.Constants.categoryLink[n].equals("NWD")) && (!com.adventnet.appmanager.util.Constants.categoryLink[n].equals("EMO")) && (!com.adventnet.appmanager.util.Constants.categoryLink[n].equals("CAM")))
/*      */                       {
/*      */ 
/*      */ 
/*  589 */                         queryBuff.delete(0, queryBuff.length());
/*      */                         
/*  591 */                         if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("SYS"))
/*      */                         {
/*  593 */                           queryBuff.append("SELECT min(RESOURCETYPE),min(AM_ManagedResourceType.DISPLAYNAME),SUBGROUP FROM AM_ManagedResourceType,AM_ManagedObject where RESOURCEGROUP='");
/*  594 */                           queryBuff.append(com.adventnet.appmanager.util.Constants.categoryLink[n]);
/*  595 */                           queryBuff.append("' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCETYPE in ");
/*  596 */                           queryBuff.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/*  597 */                           queryBuff.append(adminResourceIdentity);
/*      */                           
/*  599 */                           queryBuff.append(" GROUP BY SUBGROUP");
/*      */                         }
/*      */                         else
/*      */                         {
/*  603 */                           queryBuff.append("SELECT DISTINCT RESOURCETYPE,AM_ManagedResourceType.DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType,AM_ManagedObject where RESOURCEGROUP='");
/*  604 */                           queryBuff.append(com.adventnet.appmanager.util.Constants.categoryLink[n]);
/*  605 */                           queryBuff.append("' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCETYPE in ");
/*  606 */                           queryBuff.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/*      */                           
/*  608 */                           if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("APP"))
/*      */                           {
/*  610 */                             queryBuff.append(" and AM_ManagedResourceType.DISPLAYNAME NOT IN ('WebLogic Clusters')");
/*      */                           }
/*  612 */                           else if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("SER"))
/*      */                           {
/*  614 */                             queryBuff.append(" and SUBGROUP not in ('RMI')");
/*      */                           }
/*  616 */                           else if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("TM"))
/*      */                           {
/*  618 */                             queryBuff.append(" and RESOURCETYPE NOT in ('WTA')");
/*      */                           }
/*  620 */                           queryBuff.append(adminResourceIdentity);
/*  621 */                           queryBuff.append(" ORDER BY AM_ManagedResourceType.DISPLAYNAME");
/*      */                         }
/*      */                         
/*  624 */                         ArrayList rows = mo.getRows(queryBuff.toString());
/*      */                         
/*  626 */                         if ((rows != null) && (rows.size() != 0))
/*      */                         {
/*      */ 
/*      */ 
/*  630 */                           dispColumnCount++;
/*      */                           
/*  632 */                           if (dispColumnCount == totColumnPerRow + 1)
/*      */                           {
/*      */ 
/*  635 */                             out.write("\n\t            \t\t<tr>\n\t            ");
/*      */                             
/*  637 */                             dispColumnCount = 1;
/*      */                           }
/*      */                           
/*  640 */                           if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("APP")) {
/*  641 */                             idNum = "1";
/*  642 */                             idName = "APP";
/*  643 */                             shortName = "Application servers";
/*  644 */                             scriptFunc = "javascript:checkAppServer()";
/*      */                           }
/*  646 */                           else if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("DBS")) {
/*  647 */                             idNum = "7";
/*  648 */                             idName = "DB";
/*  649 */                             shortName = "Database Servers";
/*  650 */                             scriptFunc = "javascript:checkDBServer()";
/*      */                           }
/*  652 */                           else if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("URL")) {
/*  653 */                             idNum = "12";
/*  654 */                             idName = "web";
/*  655 */                             shortName = "Web Services";
/*  656 */                             scriptFunc = "javascript:checkWebServer()";
/*      */                           }
/*  658 */                           else if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("SYS")) {
/*  659 */                             idNum = "19";
/*  660 */                             idName = "servers";
/*  661 */                             shortName = "Servers";
/*  662 */                             scriptFunc = "javascript:checkServer()";
/*      */                           }
/*  664 */                           else if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("MS")) {
/*  665 */                             idNum = "26";
/*  666 */                             idName = "mail";
/*  667 */                             shortName = "Mail Servers";
/*  668 */                             scriptFunc = "javascript:checkMailServer()";
/*      */                           }
/*  670 */                           else if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("SER")) {
/*  671 */                             idNum = "29";
/*  672 */                             idName = "services";
/*  673 */                             shortName = "Services";
/*  674 */                             scriptFunc = "javascript:checkServices()";
/*      */                           }
/*  676 */                           else if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("ERP")) {
/*  677 */                             idNum = "70";
/*  678 */                             idName = "erpservers";
/*  679 */                             shortName = "ERP Monitors";
/*  680 */                             scriptFunc = "javascript:checkERPServer()";
/*      */                           }
/*  682 */                           else if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("MOM")) {
/*  683 */                             idNum = "80";
/*  684 */                             idName = "middleware";
/*  685 */                             shortName = "Middleware Servers";
/*  686 */                             scriptFunc = "javascript:checkMOMServer()";
/*      */                           }
/*  688 */                           else if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("VIR")) {
/*  689 */                             idNum = "100";
/*  690 */                             idName = "vir";
/*  691 */                             shortName = "Virtual Servers";
/*  692 */                             scriptFunc = "javascript:checkVirtualServer()";
/*      */                           }
/*  694 */                           else if (com.adventnet.appmanager.util.Constants.categoryLink[n].equals("CLD")) {
/*  695 */                             idNum = "110";
/*  696 */                             idName = "cld";
/*  697 */                             shortName = "Cloud Apps";
/*  698 */                             scriptFunc = "javascript:checkCloudApps()";
/*      */                           }
/*      */                           else
/*      */                           {
/*  702 */                             idName = com.adventnet.appmanager.util.Constants.categoryLink[n];
/*  703 */                             shortName = com.adventnet.appmanager.util.Constants.categoryTitle[n];
/*  704 */                             scriptFunc = "javascript:checkAppServer()";
/*      */                           }
/*      */                           
/*  707 */                           out.write("\n\t\t          <td valign=\"top\" width=\"25%\">\n\t            \t<table border=\"0\" cellpadding=\"5\">\n\t            \t\t<tr>\n\t            \t\t\t<td>\n\t  \t\t\n\t            ");
/*      */                           
/*  709 */                           if (!com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*      */                           {
/*      */ 
/*  712 */                             out.write("\n\t\t\t\t \t\t");
/*      */                             
/*  714 */                             MultiboxTag _jspx_th_html_005fmultibox_005f0 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.get(MultiboxTag.class);
/*  715 */                             _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/*  716 */                             _jspx_th_html_005fmultibox_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                             
/*  718 */                             _jspx_th_html_005fmultibox_005f0.setProperty("resourcestypes");
/*      */                             
/*  720 */                             _jspx_th_html_005fmultibox_005f0.setStyleId(idNum);
/*      */                             
/*  722 */                             _jspx_th_html_005fmultibox_005f0.setValue(shortName);
/*      */                             
/*  724 */                             _jspx_th_html_005fmultibox_005f0.setOnclick(scriptFunc);
/*  725 */                             int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/*  726 */                             if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/*  727 */                               this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0); return;
/*      */                             }
/*      */                             
/*  730 */                             this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/*  731 */                             out.write("\n\t\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/*      */ 
/*  736 */                           out.write("\n\t            \t\t\t\t<span class='bodytextbold'>");
/*  737 */                           out.print(FormatUtil.getString(com.adventnet.appmanager.util.Constants.categoryTitle[n]));
/*  738 */                           out.write("</span>\n\t            \t\t\t</td>\n\t            \t\t</tr>            \n\t            ");
/*      */                           
/*      */ 
/*  741 */                           for (int i = 0; i < rows.size(); i++)
/*      */                           {
/*  743 */                             ArrayList row = (ArrayList)rows.get(i);
/*  744 */                             String id = idName + "-" + i;
/*  745 */                             String val = (String)row.get(2);
/*  746 */                             String dname = (String)row.get(1);
/*      */                             
/*  748 */                             if (aListSUBGROUP.contains(val)) {
/*  749 */                               dname = val;
/*      */                             }
/*      */                             
/*  752 */                             out.write("\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t \t<td>\n\t\t\t\t\t\t \t\t&nbsp;&nbsp;&nbsp;\n\t\t\t\t\t\t \t\t");
/*      */                             
/*  754 */                             MultiboxTag _jspx_th_html_005fmultibox_005f1 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/*  755 */                             _jspx_th_html_005fmultibox_005f1.setPageContext(_jspx_page_context);
/*  756 */                             _jspx_th_html_005fmultibox_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                             
/*  758 */                             _jspx_th_html_005fmultibox_005f1.setProperty("resourcestypes");
/*      */                             
/*  760 */                             _jspx_th_html_005fmultibox_005f1.setStyleId(id);
/*      */                             
/*  762 */                             _jspx_th_html_005fmultibox_005f1.setValue(val);
/*  763 */                             int _jspx_eval_html_005fmultibox_005f1 = _jspx_th_html_005fmultibox_005f1.doStartTag();
/*  764 */                             if (_jspx_th_html_005fmultibox_005f1.doEndTag() == 5) {
/*  765 */                               this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f1); return;
/*      */                             }
/*      */                             
/*  768 */                             this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f1);
/*  769 */                             out.write("\n\t\t\t\t\t\t \t\t<span class='bodytext'>");
/*  770 */                             out.print(FormatUtil.getString(dname));
/*  771 */                             out.write("</span>\n\t\t\t\t\t\t \t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/*      */ 
/*  776 */                           out.write("\n\t            \t\t</table>\n\t            \t</td>\n\t            \t\n\t            ");
/*  777 */                           if (dispColumnCount == totColumnPerRow)
/*      */                           {
/*      */ 
/*  780 */                             out.write("\n\t             \t\t</tr>\n\t            ");
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                     }
/*  785 */                     if (dispColumnCount != totColumnPerRow)
/*      */                     {
/*      */ 
/*  788 */                       out.write("\n\t           \t\t</tr>\n\t           \t");
/*      */                     }
/*      */                     
/*      */ 
/*  792 */                     if (dispColumnCount == 0)
/*      */                     {
/*      */ 
/*  795 */                       out.write("\n\t        \t\n\t            <tr>\n\t             <td>\n\t              <span class='bodytext'>");
/*  796 */                       out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.nomonitors.text"));
/*  797 */                       out.write("</span>\t             \n\t               </td>\n\t            </tr>\n\t            ");
/*      */                     }
/*      */                     
/*      */ 
/*  801 */                     out.write("\t           \t\n </table>\n  ");
/*      */ 
/*      */                   }
/*  804 */                   else if (((reportname.equalsIgnoreCase("attribute")) || (reportname.equalsIgnoreCase("forecast"))) && ((attribute.equalsIgnoreCase("jvm")) || (attribute.equalsIgnoreCase("jdbc")) || (attribute.equalsIgnoreCase("Thread")) || (attribute.equalsIgnoreCase("session")) || (attribute.equalsIgnoreCase("throughput")) || (attribute.equalsIgnoreCase("webappthroughput"))))
/*      */                   {
/*  806 */                     out.write(" \n <table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" class=\"monitorinfoeven\">\n            ");
/*      */                     
/*  808 */                     if (resource.equalsIgnoreCase("monitorgroup"))
/*      */                     {
/*  810 */                       String mgquery44 = "select HAID from AM_HOLISTICAPPLICATION,AM_PARENTCHILDMAPPER,AM_ManagedObject,AM_ManagedResourceType " + attributeTablename + " where AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION.HAID and AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID and AM_ManagedObject.TYPE=AM_ManagedResourceType.RESOURCETYPE and AM_ManagedResourceType.RESOURCEGROUP='APP' " + attributeFilterCondition + adminResourceIdentity + " " + querycon + " group BY HAID";
/*  811 */                       ArrayList mgrows44 = mo.getRows(mgquery44);
/*  812 */                       if (mgrows44.size() > 0)
/*      */                       {
/*  814 */                         ArrayList haList = new ArrayList();
/*      */                         try
/*      */                         {
/*  817 */                           for (int l = 0; l < mgrows44.size(); l++)
/*      */                           {
/*  819 */                             ArrayList mgrow44 = (ArrayList)mgrows44.get(l);
/*  820 */                             String temphaid = mo.getTOPLevelMG((String)mgrow44.get(0));
/*  821 */                             if (!haList.contains(temphaid))
/*      */                             {
/*  823 */                               haList.add(temphaid);
/*      */                             }
/*      */                           }
/*      */                         } catch (Exception ex) {
/*  827 */                           ex.printStackTrace(); }
/*  828 */                         for (int l = 0; l < haList.size(); l++)
/*      */                         {
/*  830 */                           String mgmonid44 = String.valueOf(l);
/*  831 */                           String mgmonval44 = (String)haList.get(l);
/*      */                           
/*  833 */                           out.write("\n                        <tr>\n                \t\t\t<td>\n                 &nbsp;&nbsp;&nbsp;");
/*      */                           
/*  835 */                           MultiboxTag _jspx_th_html_005fmultibox_005f2 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/*  836 */                           _jspx_th_html_005fmultibox_005f2.setPageContext(_jspx_page_context);
/*  837 */                           _jspx_th_html_005fmultibox_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/*  839 */                           _jspx_th_html_005fmultibox_005f2.setProperty("resourcestypes");
/*      */                           
/*  841 */                           _jspx_th_html_005fmultibox_005f2.setStyleId(mgmonid44);
/*      */                           
/*  843 */                           _jspx_th_html_005fmultibox_005f2.setValue(mgmonval44);
/*  844 */                           int _jspx_eval_html_005fmultibox_005f2 = _jspx_th_html_005fmultibox_005f2.doStartTag();
/*  845 */                           if (_jspx_th_html_005fmultibox_005f2.doEndTag() == 5) {
/*  846 */                             this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f2); return;
/*      */                           }
/*      */                           
/*  849 */                           this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f2);
/*  850 */                           out.write("<span class='bodytext'>");
/*  851 */                           out.print((String)((java.util.Hashtable)request.getSession().getServletContext().getAttribute("applications")).get(mgmonval44));
/*  852 */                           out.write("</span>\n                  \n          \t\t\t\t\t</td>\n          \t\t\t\t</tr>\n          ");
/*      */                         }
/*      */                       }
/*      */                       else
/*      */                       {
/*  857 */                         out.write("\n\t            <tr>\n\t             <td>\n\t              <span class='bodytext'>");
/*  858 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.nomonitorgroups.text"));
/*  859 */                         out.write("</span>\t             \n\t               </td>\n\t            </tr>\t\t\t\n\t");
/*      */                       }
/*      */                       
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*  866 */                       String query11 = "SELECT DISTINCT RESOURCETYPE,AM_ManagedResourceType.DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType,AM_ManagedObject where RESOURCEGROUP='APP' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCETYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + " and AM_ManagedResourceType.DISPLAYNAME NOT IN ('WebLogic Clusters') " + adminResourceIdentity + " ORDER BY AM_ManagedResourceType.DISPLAYNAME";
/*      */                       
/*  868 */                       ArrayList rows11 = mo.getRows(query11);
/*  869 */                       if (rows11.size() > 0)
/*      */                       {
/*  871 */                         if (!com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*      */                         {
/*      */ 
/*  874 */                           out.write("\n                     \t\t<tr>\n                     \t\t\t<td>\n                            \t\t");
/*  875 */                           if (_jspx_meth_html_005fmultibox_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/*  877 */                           out.write(" \n                     ");
/*      */                         }
/*      */                         
/*      */ 
/*      */ 
/*      */ 
/*  883 */                         out.write("\n                     \t\t\t\t<span class='bodytextbold'>");
/*  884 */                         out.print(FormatUtil.getString("Application Servers"));
/*  885 */                         out.write("</span>\n                       \n                      \t\t \t</td>\n                      \t\t</tr>\n                       ");
/*      */                         
/*  887 */                         for (int i = 0; i < rows11.size(); i++)
/*      */                         {
/*  889 */                           ArrayList row11 = (ArrayList)rows11.get(i);
/*  890 */                           String aid = "APP-" + i;
/*  891 */                           String avalue = (String)row11.get(2);
/*  892 */                           String appname = (String)row11.get(1);
/*      */                           
/*  894 */                           out.write("\n                         <tr>\n                \t\t\t<td>\n                     &nbsp;&nbsp;&nbsp;");
/*      */                           
/*  896 */                           MultiboxTag _jspx_th_html_005fmultibox_005f4 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/*  897 */                           _jspx_th_html_005fmultibox_005f4.setPageContext(_jspx_page_context);
/*  898 */                           _jspx_th_html_005fmultibox_005f4.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/*  900 */                           _jspx_th_html_005fmultibox_005f4.setProperty("resourcestypes");
/*      */                           
/*  902 */                           _jspx_th_html_005fmultibox_005f4.setStyleId(aid);
/*      */                           
/*  904 */                           _jspx_th_html_005fmultibox_005f4.setValue(avalue);
/*  905 */                           int _jspx_eval_html_005fmultibox_005f4 = _jspx_th_html_005fmultibox_005f4.doStartTag();
/*  906 */                           if (_jspx_th_html_005fmultibox_005f4.doEndTag() == 5) {
/*  907 */                             this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f4); return;
/*      */                           }
/*      */                           
/*  910 */                           this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f4);
/*  911 */                           out.write("<span class='bodytext'>");
/*  912 */                           out.print(FormatUtil.getString(appname));
/*  913 */                           out.write("</span>\n                    \n          \t\t\t\t\t</td>\n          \t\t\t\t</tr>\n          \n          ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       else
/*      */                       {
/*  919 */                         out.write("\n \t\t\t\t<tr>\n\t             \t<td>\n\t              \t\t<span class='bodytext'>");
/*  920 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.nomonitors.text"));
/*  921 */                         out.write("</span>\t             \n\t               </td>\n\t            </tr>\t                \t\n                \t");
/*      */                       }
/*      */                       
/*      */ 
/*  925 */                       out.write("\n </table>\n    ");
/*      */                     }
/*  927 */                   } else if (((reportname.equalsIgnoreCase("attribute")) || (reportname.equalsIgnoreCase("forecast"))) && (attribute.equalsIgnoreCase("operationExecutionTime")))
/*      */                   {
/*      */ 
/*  930 */                     out.write(" \n \n            ");
/*  931 */                     if (resource.equalsIgnoreCase("monitorgroup"))
/*      */                     {
/*  933 */                       out.write("\n\n\t\t");
/*  934 */                       JspRuntimeLibrary.include(request, response, "/jsp/ScheduleReportsMGTree.jsp" + ("/jsp/ScheduleReportsMGTree.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("mgResType", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("URL", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("customfieldfilter", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(isCustomFieldFilter), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("aliasname", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(aliasname), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("customValue", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(customValue), request.getCharacterEncoding()), out, true);
/*  935 */                       out.write("\n          ");
/*      */                     }
/*      */                     else {
/*  938 */                       out.write("\n\t\t<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" class=\"monitorinfoeven\">\n  \t\t\n             ");
/*      */                       
/*  940 */                       String query11 = "SELECT DISTINCT RESOURCETYPE,AM_ManagedResourceType.DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType,AM_ManagedObject where RESOURCEGROUP='URL' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCETYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + adminResourceIdentity + " ORDER BY AM_ManagedResourceType.DISPLAYNAME";
/*  941 */                       ArrayList rows11 = mo.getRows(query11);
/*  942 */                       if (rows11.size() > 0)
/*      */                       {
/*      */ 
/*      */ 
/*  946 */                         out.write("\n\t\t\t\t<tr>\n            \t\t<td>\n             ");
/*  947 */                         if (!com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*      */                         {
/*      */ 
/*  950 */                           out.write("\n                   ");
/*  951 */                           if (_jspx_meth_html_005fmultibox_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/*  953 */                           out.write("\n                ");
/*      */                         }
/*      */                         
/*      */ 
/*  957 */                         out.write("\n           \t\t\t<span class='bodytextbold'>");
/*  958 */                         out.print(FormatUtil.getString("Web Services"));
/*  959 */                         out.write("</span>\n             \n             \t\t</td>\n             \t</tr>\t\t\n\t\t");
/*      */                         
/*  961 */                         for (int i = 0; i < rows11.size(); i++)
/*      */                         {
/*  963 */                           ArrayList row11 = (ArrayList)rows11.get(i);
/*  964 */                           String wid = "web-" + i;
/*  965 */                           String wvalue = (String)row11.get(2);
/*  966 */                           String webname = (String)row11.get(1);
/*      */                           
/*  968 */                           out.write("\n                         <tr>\n                <td>\n                     &nbsp;&nbsp;&nbsp;");
/*      */                           
/*  970 */                           MultiboxTag _jspx_th_html_005fmultibox_005f6 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/*  971 */                           _jspx_th_html_005fmultibox_005f6.setPageContext(_jspx_page_context);
/*  972 */                           _jspx_th_html_005fmultibox_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/*  974 */                           _jspx_th_html_005fmultibox_005f6.setProperty("resourcestypes");
/*      */                           
/*  976 */                           _jspx_th_html_005fmultibox_005f6.setStyleId(wid);
/*      */                           
/*  978 */                           _jspx_th_html_005fmultibox_005f6.setValue(wvalue);
/*  979 */                           int _jspx_eval_html_005fmultibox_005f6 = _jspx_th_html_005fmultibox_005f6.doStartTag();
/*  980 */                           if (_jspx_th_html_005fmultibox_005f6.doEndTag() == 5) {
/*  981 */                             this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f6); return;
/*      */                           }
/*      */                           
/*  984 */                           this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f6);
/*  985 */                           out.write("<span class='bodytext'>");
/*  986 */                           out.print(FormatUtil.getString(webname));
/*  987 */                           out.write("</span>\n                    \n          </td>\n          </tr>\n          \n          ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       else
/*      */                       {
/*  993 */                         out.write("\n  \t\t\t\t<tr>\n\t             \t<td>\n\t              \t\t<span class='bodytext'>");
/*  994 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.nomonitors.text"));
/*  995 */                         out.write("</span>\t             \n\t               </td>\n\t            </tr>\t         \n          ");
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 1000 */                       out.write("\n </table>\n    ");
/*      */                     }
/*      */                   }
/* 1003 */                   else if (((reportname.equalsIgnoreCase("attribute")) || (reportname.equalsIgnoreCase("forecast"))) && ((attribute.equalsIgnoreCase("connectionTime")) || (attribute.equalsIgnoreCase("buffer")) || (attribute.equalsIgnoreCase("cache"))))
/*      */                   {
/*      */ 
/* 1006 */                     out.write(" \n \n \t");
/* 1007 */                     if (resource.equalsIgnoreCase("monitorgroup"))
/*      */                     {
/*      */ 
/* 1010 */                       out.write(10);
/* 1011 */                       out.write(9);
/* 1012 */                       out.write(9);
/* 1013 */                       JspRuntimeLibrary.include(request, response, "/jsp/ScheduleReportsMGTree.jsp" + ("/jsp/ScheduleReportsMGTree.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("mgResType", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("DBS", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("customfieldfilter", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(isCustomFieldFilter), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("aliasname", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(aliasname), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("customValue", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(customValue), request.getCharacterEncoding()), out, true);
/* 1014 */                       out.write("\n          ");
/*      */                     }
/*      */                     else {
/* 1017 */                       out.write("\n\t<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" class=\"monitorinfoeven\">\n\t\n           ");
/*      */                       
/* 1019 */                       String query22 = "SELECT DISTINCT RESOURCETYPE,AM_ManagedResourceType.DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType,AM_ManagedObject where RESOURCEGROUP='DBS' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCETYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + adminResourceIdentity + "  ORDER BY AM_ManagedResourceType.DISPLAYNAME";
/* 1020 */                       ArrayList rows22 = mo.getRows(query22);
/* 1021 */                       if (rows22.size() > 0)
/*      */                       {
/*      */ 
/* 1024 */                         out.write(" \t  \t\n             <tr> <td> \n                  \n                   ");
/* 1025 */                         if (!com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*      */                         {
/*      */ 
/* 1028 */                           out.write("\n                             ");
/* 1029 */                           if (_jspx_meth_html_005fmultibox_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 1031 */                           out.write("\n                        ");
/*      */                         }
/*      */                         
/*      */ 
/* 1035 */                         out.write("\n                      \n                       \n                               <span class='bodytextbold'>");
/* 1036 */                         out.print(FormatUtil.getString("Database Servers"));
/* 1037 */                         out.write("</span></td></tr>                \t\n                \t\t");
/*      */                         
/*      */ 
/* 1040 */                         for (int j = 0; j < rows22.size(); j++)
/*      */                         {
/* 1042 */                           ArrayList row22 = (ArrayList)rows22.get(j);
/* 1043 */                           String did = "DB-" + j;
/* 1044 */                           String dvalue = (String)row22.get(2);
/* 1045 */                           String bname = (String)row22.get(1);
/*      */                           
/* 1047 */                           out.write("\n                         <tr>\n                <td>\n                    &nbsp;&nbsp;&nbsp;");
/*      */                           
/* 1049 */                           MultiboxTag _jspx_th_html_005fmultibox_005f8 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 1050 */                           _jspx_th_html_005fmultibox_005f8.setPageContext(_jspx_page_context);
/* 1051 */                           _jspx_th_html_005fmultibox_005f8.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/* 1053 */                           _jspx_th_html_005fmultibox_005f8.setProperty("resourcestypes");
/*      */                           
/* 1055 */                           _jspx_th_html_005fmultibox_005f8.setStyleId(did);
/*      */                           
/* 1057 */                           _jspx_th_html_005fmultibox_005f8.setValue(dvalue);
/* 1058 */                           int _jspx_eval_html_005fmultibox_005f8 = _jspx_th_html_005fmultibox_005f8.doStartTag();
/* 1059 */                           if (_jspx_th_html_005fmultibox_005f8.doEndTag() == 5) {
/* 1060 */                             this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f8); return;
/*      */                           }
/*      */                           
/* 1063 */                           this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f8);
/* 1064 */                           out.write("<span class='bodytext'>");
/* 1065 */                           out.print(FormatUtil.getString(bname));
/* 1066 */                           out.write("</span>\n                    \n          </td>\n          </tr>\n          ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       else
/*      */                       {
/* 1072 */                         out.write("\n   \t\t\t\t<tr>\n\t             \t<td>\n\t              \t\t<span class='bodytext'>");
/* 1073 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.nomonitors.text"));
/* 1074 */                         out.write("</span>\t             \n\t               </td>\n\t            </tr>\t    \n     ");
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 1080 */                       out.write("\n  </table>\n    ");
/*      */                     }
/*      */                   }
/* 1083 */                   else if (((reportname.equalsIgnoreCase("attribute")) || (reportname.equalsIgnoreCase("forecast"))) && ((attribute.equalsIgnoreCase("cpuid")) || (attribute.equalsIgnoreCase("mem")) || (attribute.equalsIgnoreCase("disk"))))
/*      */                   {
/*      */ 
/*      */ 
/* 1087 */                     out.write(" \n \n  ");
/* 1088 */                     if (resource.equalsIgnoreCase("monitorgroup"))
/*      */                     {
/* 1090 */                       out.write(10);
/* 1091 */                       out.write(9);
/* 1092 */                       JspRuntimeLibrary.include(request, response, "/jsp/ScheduleReportsMGTree.jsp" + ("/jsp/ScheduleReportsMGTree.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("mgResType", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("SYS", request.getCharacterEncoding()), out, true);
/* 1093 */                       out.write("\n\n          ");
/*      */                     }
/*      */                     else {
/* 1096 */                       out.write("\n\t\t<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" class=\"monitorinfoeven\">\n           ");
/*      */                       
/*      */ 
/* 1099 */                       String query44 = "SELECT DISTINCT min(RESOURCETYPE),min(AM_ManagedResourceType.DISPLAYNAME),SUBGROUP FROM AM_ManagedResourceType,AM_ManagedObject where RESOURCEGROUP='SYS' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCETYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + adminResourceIdentity + " GROUP BY SUBGROUP";
/* 1100 */                       ArrayList rows44 = mo.getRows(query44);
/* 1101 */                       int kl = 0;
/* 1102 */                       String win = "";
/* 1103 */                       if (rows44.size() > 0)
/*      */                       {
/*      */ 
/* 1106 */                         out.write("\n               <tr> \n               \t\t<td>\n                \t    ");
/* 1107 */                         if (!com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*      */                         {
/*      */ 
/* 1110 */                           out.write("\n                \t     ");
/* 1111 */                           if (_jspx_meth_html_005fmultibox_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 1113 */                           out.write("\n                \t    ");
/*      */                         }
/*      */                         
/*      */ 
/* 1117 */                         out.write("\n                \t         <span class='bodytextbold'>");
/* 1118 */                         out.print(FormatUtil.getString("Servers"));
/* 1119 */                         out.write("</span>\n                \t </td>\n               </tr>  \n               ");
/*      */                         
/* 1121 */                         kl = rows44.size();
/* 1122 */                         win = "servers-" + kl;
/* 1123 */                         for (int l = 0; l < rows44.size(); l++)
/*      */                         {
/* 1125 */                           ArrayList row44 = (ArrayList)rows44.get(l);
/* 1126 */                           String sid1 = "servers-" + l;
/* 1127 */                           String svalue1 = (String)row44.get(2);
/* 1128 */                           String sname1 = (String)row44.get(1);
/*      */                           
/* 1130 */                           out.write("\n                         <tr>\n                <td>\n                 &nbsp;&nbsp;&nbsp;");
/*      */                           
/* 1132 */                           MultiboxTag _jspx_th_html_005fmultibox_005f10 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 1133 */                           _jspx_th_html_005fmultibox_005f10.setPageContext(_jspx_page_context);
/* 1134 */                           _jspx_th_html_005fmultibox_005f10.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/* 1136 */                           _jspx_th_html_005fmultibox_005f10.setProperty("resourcestypes");
/*      */                           
/* 1138 */                           _jspx_th_html_005fmultibox_005f10.setStyleId(sid1);
/*      */                           
/* 1140 */                           _jspx_th_html_005fmultibox_005f10.setValue(svalue1);
/* 1141 */                           int _jspx_eval_html_005fmultibox_005f10 = _jspx_th_html_005fmultibox_005f10.doStartTag();
/* 1142 */                           if (_jspx_th_html_005fmultibox_005f10.doEndTag() == 5) {
/* 1143 */                             this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f10); return;
/*      */                           }
/*      */                           
/* 1146 */                           this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f10);
/* 1147 */                           out.write("<span class='bodytext'>");
/* 1148 */                           out.print(FormatUtil.getString(svalue1));
/* 1149 */                           out.write("</span>\n                  \n          </td>\n          </tr>\n          ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       else
/*      */                       {
/* 1155 */                         out.write("\n  \t\t\t<tr>\n             \t<td>\n              \t\t<span class='bodytext'>");
/* 1156 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.nomonitors.text"));
/* 1157 */                         out.write("</span>\t             \n               </td>\n            </tr>\t    \n          ");
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/* 1162 */                       out.write("\n<tr>\n                <td>\n                <!-- &nbsp;&nbsp;&nbsp;");
/* 1163 */                       if (_jspx_meth_html_005fmultibox_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                         return;
/* 1165 */                       out.write("<span class='bodytext'>");
/*      */                       
/* 1167 */                       out.write("</span> -->\n                  \n          </td>\n          </tr>");
/*      */                     }
/* 1169 */                     out.write("\n  </table>\n    ");
/*      */                   }
/* 1171 */                   else if (((reportname.equalsIgnoreCase("attribute")) || (reportname.equalsIgnoreCase("forecast"))) && ((attribute.equalsIgnoreCase("jdkcpuid")) || (attribute.equalsIgnoreCase("memmb"))))
/*      */                   {
/*      */ 
/* 1174 */                     out.write(" \n \n  ");
/* 1175 */                     if (resource.equalsIgnoreCase("monitorgroup"))
/*      */                     {
/*      */ 
/* 1178 */                       out.write(10);
/* 1179 */                       out.write(9);
/* 1180 */                       JspRuntimeLibrary.include(request, response, "/jsp/ScheduleReportsMGTree.jsp" + ("/jsp/ScheduleReportsMGTree.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("mgResType", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("TM", request.getCharacterEncoding()), out, true);
/* 1181 */                       out.write("  \n          ");
/*      */                     } else {
/* 1183 */                       out.write("\n\t<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" class=\"monitorinfoeven\">\n\n           ");
/*      */                       
/* 1185 */                       String query44 = "SELECT DISTINCT RESOURCETYPE,AM_ManagedResourceType.DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType,AM_ManagedObject where RESOURCEGROUP='TM' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCETYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + adminResourceIdentity + " and RESOURCETYPE NOT in ('WTA') ORDER BY AM_ManagedResourceType.DISPLAYNAME";
/* 1186 */                       ArrayList rows44 = mo.getRows(query44);
/* 1187 */                       if (rows44.size() > 0)
/*      */                       {
/*      */ 
/* 1190 */                         out.write("\n               \t   <tr> <td>\n               \t    ");
/* 1191 */                         if (!com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*      */                         {
/*      */ 
/* 1194 */                           out.write("\n               \t     ");
/* 1195 */                           if (_jspx_meth_html_005fmultibox_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 1197 */                           out.write("\n               \t    ");
/*      */                         }
/*      */                         
/*      */ 
/* 1201 */                         out.write("        \n                \t    <span class='bodytextbold'>");
/* 1202 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.transaction"));
/* 1203 */                         out.write("</span></td></tr>                \t\n                     ");
/*      */                         
/* 1205 */                         for (int l = 0; l < rows44.size(); l++)
/*      */                         {
/* 1207 */                           ArrayList row44 = (ArrayList)rows44.get(l);
/* 1208 */                           String sid1 = "javaservers-" + l;
/* 1209 */                           String svalue1 = (String)row44.get(2);
/* 1210 */                           String sname1 = (String)row44.get(1);
/*      */                           
/* 1212 */                           out.write("\n                        \n                         <tr>\n                <td>\n                 &nbsp;&nbsp;&nbsp;");
/*      */                           
/* 1214 */                           MultiboxTag _jspx_th_html_005fmultibox_005f13 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 1215 */                           _jspx_th_html_005fmultibox_005f13.setPageContext(_jspx_page_context);
/* 1216 */                           _jspx_th_html_005fmultibox_005f13.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/* 1218 */                           _jspx_th_html_005fmultibox_005f13.setProperty("resourcestypes");
/*      */                           
/* 1220 */                           _jspx_th_html_005fmultibox_005f13.setStyleId(sid1);
/*      */                           
/* 1222 */                           _jspx_th_html_005fmultibox_005f13.setValue(svalue1);
/* 1223 */                           int _jspx_eval_html_005fmultibox_005f13 = _jspx_th_html_005fmultibox_005f13.doStartTag();
/* 1224 */                           if (_jspx_th_html_005fmultibox_005f13.doEndTag() == 5) {
/* 1225 */                             this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f13); return;
/*      */                           }
/*      */                           
/* 1228 */                           this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f13);
/* 1229 */                           out.write("<span class='bodytext'>");
/* 1230 */                           out.print(FormatUtil.getString(sname1));
/* 1231 */                           out.write("</span>\n                  \n          </td>\n          </tr>\n          ");
/*      */                         }
/*      */                         
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 1238 */                         out.write("\n          \t\t\t<tr>\n                     \t<td>\n                      \t\t<span class='bodytext'>");
/* 1239 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.nomonitors.text"));
/* 1240 */                         out.write("</span>\t             \n                       </td>\n                    </tr>\t    \n                  ");
/*      */                       }
/*      */                       
/*      */ 
/* 1244 */                       out.write(10);
/*      */                     }
/* 1246 */                     out.write("\n  </table>\n    ");
/*      */                   }
/* 1248 */                   else if (((reportname.equalsIgnoreCase("attribute")) || (reportname.equalsIgnoreCase("forecast"))) && ("true".equalsIgnoreCase(isCustom))) {
/* 1249 */                     int mn = 0;
/* 1250 */                     out.write(" \n \n  ");
/*      */                     
/* 1252 */                     if (resource.equalsIgnoreCase("monitorgroup"))
/*      */                     {
/*      */ 
/* 1255 */                       out.write("\n\n\t\t");
/* 1256 */                       JspRuntimeLibrary.include(request, response, "/jsp/ScheduleReportsMGTree.jsp" + ("/jsp/ScheduleReportsMGTree.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("mgResType", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("Custom", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("ResGroup", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(ResGroup), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("customfieldfilter", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(isCustomFieldFilter), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("aliasname", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(aliasname), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("customValue", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(customValue), request.getCharacterEncoding()), out, true);
/* 1257 */                       out.write("\n          ");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/* 1262 */                       out.write("\n\t<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" class=\"monitorinfoeven\">\n   <tr> <td>\n    ");
/* 1263 */                       if (!com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*      */                       {
/*      */ 
/*      */ 
/* 1267 */                         out.write("\n     ");
/*      */                         
/* 1269 */                         MultiboxTag _jspx_th_html_005fmultibox_005f14 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.get(MultiboxTag.class);
/* 1270 */                         _jspx_th_html_005fmultibox_005f14.setPageContext(_jspx_page_context);
/* 1271 */                         _jspx_th_html_005fmultibox_005f14.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1273 */                         _jspx_th_html_005fmultibox_005f14.setProperty("resourcestypes");
/*      */                         
/* 1275 */                         _jspx_th_html_005fmultibox_005f14.setStyleId("10000");
/*      */                         
/* 1277 */                         _jspx_th_html_005fmultibox_005f14.setValue(ResGroupName);
/*      */                         
/* 1279 */                         _jspx_th_html_005fmultibox_005f14.setOnclick("javascript:checkCustomAttributeServer()");
/* 1280 */                         int _jspx_eval_html_005fmultibox_005f14 = _jspx_th_html_005fmultibox_005f14.doStartTag();
/* 1281 */                         if (_jspx_th_html_005fmultibox_005f14.doEndTag() == 5) {
/* 1282 */                           this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f14); return;
/*      */                         }
/*      */                         
/* 1285 */                         this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f14);
/* 1286 */                         out.write("\n    ");
/*      */                       }
/*      */                       
/* 1289 */                       if ("Transaction Monitors".equalsIgnoreCase(ResGroupName)) {
/* 1290 */                         ResGroupName = "Java/Transaction";
/* 1291 */                       } else if ("Virtual Servers".equalsIgnoreCase(ResGroupName)) {
/* 1292 */                         ResGroupName = "am.webclient.monitorgroupsecond.category.virtualserver";
/* 1293 */                       } else if ("Cloud Apps".equalsIgnoreCase(ResGroupName)) {
/* 1294 */                         ResGroupName = "am.webclient.monitorgroupsecond.category.cloudapps";
/*      */                       }
/*      */                       
/* 1297 */                       out.write("\n  \n       \n                <span class='bodytextbold'>");
/* 1298 */                       out.print(FormatUtil.getString(ResGroupName));
/* 1299 */                       out.write("</span></td></tr>\n           ");
/* 1300 */                       String query44 = "SELECT DISTINCT RESOURCETYPE,AM_ManagedResourceType.DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType,AM_ManagedObject where RESOURCEGROUP='" + ResGroup + "' and RESOURCETYPE  like '%" + oldresourcetypes + "%' and RESOURCETYPE NOT in ('WTA','Node','WindowsNT_Server','Windows95','SUN PC','snmp-node') and AM_ManagedResourceType.DISPLAYNAME NOT IN ('WebLogic Clusters') and SUBGROUP not in ('RMI') " + adminResourceIdentity + " ORDER BY AM_ManagedResourceType.DISPLAYNAME";
/* 1301 */                       if (ConfMonitorConfiguration.getInstance().isConfMonitor(oldresourcetypes)) {
/* 1302 */                         query44 = "SELECT DISTINCT RESOURCETYPE,AM_ManagedResourceType.DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType,AM_ManagedObject where RESOURCEGROUP='" + ResGroup + "' and RESOURCETYPE='" + oldresourcetypes + "' and RESOURCETYPE NOT in ('WTA','Node','WindowsNT_Server','Windows95','SUN PC','snmp-node') and AM_ManagedResourceType.DISPLAYNAME NOT IN ('WebLogic Clusters') and SUBGROUP not in ('RMI') " + adminResourceIdentity + " ORDER BY AM_ManagedResourceType.DISPLAYNAME";
/*      */                       }
/* 1304 */                       ArrayList rows44 = mo.getRows(query44);
/*      */                       
/* 1306 */                       if (rows44.size() > 0)
/*      */                       {
/* 1308 */                         mn = 0;
/* 1309 */                         for (int l = 0; l < rows44.size(); mn++)
/*      */                         {
/* 1311 */                           ArrayList row44 = (ArrayList)rows44.get(l);
/* 1312 */                           String sid1 = "customAttribute-1";
/* 1313 */                           String svalue1 = (String)row44.get(2);
/* 1314 */                           String sname1 = (String)row44.get(1);
/*      */                           
/* 1316 */                           out.write("\n                        \n                         <tr>\n                <td>\n                 &nbsp;&nbsp;&nbsp;");
/*      */                           
/* 1318 */                           MultiboxTag _jspx_th_html_005fmultibox_005f15 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 1319 */                           _jspx_th_html_005fmultibox_005f15.setPageContext(_jspx_page_context);
/* 1320 */                           _jspx_th_html_005fmultibox_005f15.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/* 1322 */                           _jspx_th_html_005fmultibox_005f15.setProperty("resourcestypes");
/*      */                           
/* 1324 */                           _jspx_th_html_005fmultibox_005f15.setStyleId(sid1);
/*      */                           
/* 1326 */                           _jspx_th_html_005fmultibox_005f15.setValue(svalue1);
/* 1327 */                           int _jspx_eval_html_005fmultibox_005f15 = _jspx_th_html_005fmultibox_005f15.doStartTag();
/* 1328 */                           if (_jspx_th_html_005fmultibox_005f15.doEndTag() == 5) {
/* 1329 */                             this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f15); return;
/*      */                           }
/*      */                           
/* 1332 */                           this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f15);
/* 1333 */                           out.write("<span class='bodytext'>");
/* 1334 */                           out.print(FormatUtil.getString(sname1));
/* 1335 */                           out.write("</span>\n                  \n          </td>\n          </tr>\n          ");l++;
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 1339 */                       out.write(10); }
/* 1340 */                     if ((resource.equals("monitor")) && ("SYS".equals(ResGroup)) && ("windows".equals(oldresourcetypes)))
/*      */                     {
/* 1342 */                       String Styid = "customAttribute-1";
/* 1343 */                       out.write("\n\n<tr>\n                <td>\n                 &nbsp;&nbsp;&nbsp;");
/*      */                       
/* 1345 */                       MultiboxTag _jspx_th_html_005fmultibox_005f16 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 1346 */                       _jspx_th_html_005fmultibox_005f16.setPageContext(_jspx_page_context);
/* 1347 */                       _jspx_th_html_005fmultibox_005f16.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/* 1349 */                       _jspx_th_html_005fmultibox_005f16.setProperty("resourcestypes");
/*      */                       
/* 1351 */                       _jspx_th_html_005fmultibox_005f16.setStyleId(Styid);
/*      */                       
/* 1353 */                       _jspx_th_html_005fmultibox_005f16.setValue("Windows");
/* 1354 */                       int _jspx_eval_html_005fmultibox_005f16 = _jspx_th_html_005fmultibox_005f16.doStartTag();
/* 1355 */                       if (_jspx_th_html_005fmultibox_005f16.doEndTag() == 5) {
/* 1356 */                         this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f16); return;
/*      */                       }
/*      */                       
/* 1359 */                       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f16);
/* 1360 */                       out.write("<span class='bodytext'>");
/* 1361 */                       out.print(FormatUtil.getString("Windows"));
/* 1362 */                       out.write("</span>\n                   \n          </td>\n          </tr>\n          ");
/*      */                     }
/* 1364 */                     out.write("\n  </table>\n    ");
/*      */ 
/*      */                   }
/* 1367 */                   else if (((reportname.equalsIgnoreCase("attribute")) || (reportname.equalsIgnoreCase("forecast"))) && ((attribute.equalsIgnoreCase("sapcpu")) || (attribute.equalsIgnoreCase("sapmemory")) || (attribute.equalsIgnoreCase("sapdisk")) || (attribute.equalsIgnoreCase("sappagein")) || (attribute.equalsIgnoreCase("sappageout")) || (attribute.equalsIgnoreCase("sutilization")) || (attribute.equalsIgnoreCase("butilization")) || (attribute.equalsIgnoreCase("sapferestime")) || (attribute.equalsIgnoreCase("sapenqreq"))))
/*      */                   {
/*      */ 
/* 1370 */                     out.write("\n <!-- SAP Start-->\n \n     ");
/* 1371 */                     if (resource.equalsIgnoreCase("monitorgroup"))
/*      */                     {
/*      */ 
/* 1374 */                       out.write(10);
/* 1375 */                       out.write(9);
/* 1376 */                       out.write(9);
/* 1377 */                       JspRuntimeLibrary.include(request, response, "/jsp/ScheduleReportsMGTree.jsp" + ("/jsp/ScheduleReportsMGTree.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("mgResType", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("SAP", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("customfieldfilter", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(isCustomFieldFilter), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("aliasname", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(aliasname), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("customValue", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(customValue), request.getCharacterEncoding()), out, true);
/* 1378 */                       out.write("\n\t\t \n          ");
/*      */                     }
/*      */                     else {
/* 1381 */                       out.write("\n\n\t\t<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" class=\"monitorinfoeven\">\n   \t\t\n    ");
/*      */                       
/* 1383 */                       String query44 = "SELECT DISTINCT RESOURCETYPE,AM_ManagedResourceType.DISPLAYNAME,SUBGROUP FROM AM_ManagedResourceType,AM_ManagedObject where RESOURCEGROUP='ERP' and AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE and RESOURCETYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + adminResourceIdentity + " ORDER BY AM_ManagedResourceType.DISPLAYNAME";
/* 1384 */                       ArrayList rows44 = mo.getRows(query44);
/* 1385 */                       if (rows44.size() > 0)
/*      */                       {
/*      */ 
/* 1388 */                         out.write("\n            \t\t\n    \t<tr>\n   \t\t\t<td>  \n    ");
/*      */                         
/* 1390 */                         if (!com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*      */                         {
/*      */ 
/* 1393 */                           out.write("\n    ");
/* 1394 */                           if (_jspx_meth_html_005fmultibox_005f17(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 1396 */                           out.write("\n    \n    ");
/*      */                         }
/*      */                         
/*      */ 
/* 1400 */                         out.write("     \n        <span class='bodytextbold'>");
/* 1401 */                         out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 1402 */                         out.write("</span>\n        \t</td>\n        </tr>\n     ");
/*      */                         
/* 1404 */                         for (int l = 0; l < rows44.size(); l++)
/*      */                         {
/* 1406 */                           ArrayList row44 = (ArrayList)rows44.get(l);
/* 1407 */                           String sid1 = "erpservers-" + l;
/* 1408 */                           String svalue1 = (String)row44.get(2);
/* 1409 */                           String sname1 = (String)row44.get(1);
/*      */                           
/* 1411 */                           out.write("\n                        \n        \t                 <tr>\n        \t        \t<td>\n        \t        \t &nbsp;&nbsp;&nbsp;");
/*      */                           
/* 1413 */                           MultiboxTag _jspx_th_html_005fmultibox_005f18 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 1414 */                           _jspx_th_html_005fmultibox_005f18.setPageContext(_jspx_page_context);
/* 1415 */                           _jspx_th_html_005fmultibox_005f18.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/* 1417 */                           _jspx_th_html_005fmultibox_005f18.setProperty("resourcestypes");
/*      */                           
/* 1419 */                           _jspx_th_html_005fmultibox_005f18.setStyleId(sid1);
/*      */                           
/* 1421 */                           _jspx_th_html_005fmultibox_005f18.setValue(svalue1);
/* 1422 */                           int _jspx_eval_html_005fmultibox_005f18 = _jspx_th_html_005fmultibox_005f18.doStartTag();
/* 1423 */                           if (_jspx_th_html_005fmultibox_005f18.doEndTag() == 5) {
/* 1424 */                             this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f18); return;
/*      */                           }
/*      */                           
/* 1427 */                           this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f18);
/* 1428 */                           out.write("<span class='bodytext'>");
/* 1429 */                           out.print(FormatUtil.getString(sname1));
/* 1430 */                           out.write("</span>\n        \t          \n        \t  \t\t</td>\n        \t  \t\t</tr>\n          ");
/*      */                         }
/*      */                         
/*      */                       }
/*      */                       else
/*      */                       {
/* 1436 */                         out.write("\n      \t\t\t<tr>\n                 \t<td>\n                  \t\t<span class='bodytext'>");
/* 1437 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.nomonitors.text"));
/* 1438 */                         out.write("</span>\t             \n                   </td>\n                </tr>\t    \n              ");
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/* 1443 */                     out.write("\n</table>\n  <!-- SAP Ends -->\n");
/*      */                   }
/* 1445 */                   else if (reportname.equalsIgnoreCase("custom"))
/*      */                   {
/*      */ 
/* 1448 */                     String que = "(select AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME as ATTRIBUTENAME,AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME as DISPLAYNAME,AM_ManagedObject.RESOURCEID as RESOURCEID," + com.adventnet.appmanager.db.DBQueryUtil.castasVarchar("AM_CAM_DC_GROUPS.GROUPID") + " as GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME as DISPLAYNAME from AM_CAM_DC_ATTRIBUTES,AM_ARCHIVERCONFIG_EXTN,AM_CAM_DC_GROUPS,AM_ManagedObject where AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_ARCHIVERCONFIG_EXTN.ATTRIBUTEID and AM_ARCHIVERCONFIG_EXTN.RESOURCEID=AM_CAM_DC_GROUPS.GROUPID and AM_ManagedObject.RESOURCEID=AM_ARCHIVERCONFIG_EXTN.RESOURCEID " + adminResourceIdentity + ") UNION (select ATTRIBUTENAME,AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME  from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ArchiverCAMConfig,AM_CAM_DC_ATTRIBUTES,AM_ManagedObject,AM_CAM_DC_GROUPS where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ArchiverCAMConfig.ATTRIBUTEID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.RESOURCEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID " + adminResourceIdentity + " and AM_CAM_DC_ATTRIBUTES.GROUPID=AM_CAM_DC_GROUPS.GROUPID )";
/* 1449 */                     if (isCustomFieldFilter) {
/* 1450 */                       que = "(select AM_CAM_DC_ATTRIBUTES.ATTRIBUTENAME as ATTRIBUTENAME,AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID as ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME as DISPLAYNAME,AM_ManagedObject.RESOURCEID as RESOURCEID," + com.adventnet.appmanager.db.DBQueryUtil.castasVarchar("AM_CAM_DC_GROUPS.GROUPID") + " as GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME as DISPLAYNAME from AM_CAM_DC_ATTRIBUTES,AM_ARCHIVERCONFIG_EXTN,AM_CAM_DC_GROUPS,AM_ManagedObject," + fieldsDataTable + " where AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID=AM_ARCHIVERCONFIG_EXTN.ATTRIBUTEID and AM_ARCHIVERCONFIG_EXTN.RESOURCEID=AM_CAM_DC_GROUPS.GROUPID and AM_ManagedObject.RESOURCEID=AM_ARCHIVERCONFIG_EXTN.RESOURCEID and AM_CAM_DC_GROUPS.RESOURCEID=" + fieldsDataTable + ".RESOURCEID " + querycon + adminResourceIdentity + ") UNION (select ATTRIBUTENAME,AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID,AM_ManagedObject.DISPLAYNAME,AM_ManagedObject.RESOURCEID,GROUPNAME,AM_CAM_DC_ATTRIBUTES.DISPLAYNAME  from AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER,AM_ArchiverCAMConfig,AM_CAM_DC_ATTRIBUTES,AM_ManagedObject,AM_CAM_DC_GROUPS," + fieldsDataTable + " where AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_ArchiverCAMConfig.ATTRIBUTEID and AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.ATTRIBUTEID=AM_CAM_DC_ATTRIBUTES.ATTRIBUTEID and AM_ManagedObject.RESOURCEID=AM_CAM_DC_RESOURCE_ATTRIBUTES_MAPPER.RESOURCEID " + adminResourceIdentity + " and AM_CAM_DC_ATTRIBUTES.GROUPID=AM_CAM_DC_GROUPS.GROUPID and AM_CAM_DC_GROUPS.RESOURCEID=" + fieldsDataTable + ".RESOURCEID " + querycon + " )";
/*      */                     }
/* 1452 */                     ArrayList rows = mo.getRows(que);
/* 1453 */                     if (rows.size() > 0)
/*      */                     {
/* 1455 */                       out.write("\n                 <table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" class=\"monitorinfoeven\">\n\t");
/*      */                       
/* 1457 */                       String bgclass = "whitegrayborder";
/* 1458 */                       for (int i = 0; i < rows.size(); i++)
/*      */                       {
/* 1460 */                         ArrayList row = (ArrayList)rows.get(i);
/* 1461 */                         String id = String.valueOf(i);
/* 1462 */                         String attname = (String)row.get(0);
/* 1463 */                         String attid = (String)row.get(1);
/* 1464 */                         String resid = (String)row.get(3);
/*      */                         
/* 1466 */                         String value = resid + ":" + attid;
/*      */                         
/* 1468 */                         out.write("\n       <tr>\n        <td>\n         ");
/*      */                         
/* 1470 */                         MultiboxTag _jspx_th_html_005fmultibox_005f19 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 1471 */                         _jspx_th_html_005fmultibox_005f19.setPageContext(_jspx_page_context);
/* 1472 */                         _jspx_th_html_005fmultibox_005f19.setParent(_jspx_th_html_005fform_005f0);
/*      */                         
/* 1474 */                         _jspx_th_html_005fmultibox_005f19.setProperty("resourcestypes");
/*      */                         
/* 1476 */                         _jspx_th_html_005fmultibox_005f19.setStyleId(id);
/*      */                         
/* 1478 */                         _jspx_th_html_005fmultibox_005f19.setValue(value);
/* 1479 */                         int _jspx_eval_html_005fmultibox_005f19 = _jspx_th_html_005fmultibox_005f19.doStartTag();
/* 1480 */                         if (_jspx_th_html_005fmultibox_005f19.doEndTag() == 5) {
/* 1481 */                           this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f19); return;
/*      */                         }
/*      */                         
/* 1484 */                         this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f19);
/* 1485 */                         out.write("<span class='bodytext'>");
/* 1486 */                         out.print((String)row.get(5));
/* 1487 */                         out.write("</span>\n         \n          </td>\n          </tr>\n          ");
/*      */                       }
/* 1489 */                       out.write("\n    </table>\n    \n    ");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*      */ 
/* 1495 */                       out.write("\n                    \n                    <table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" class=\"monitorinfoeven\">\n\t\n       <tr>\n        <td>\n         <span class='bodytext'>");
/* 1496 */                       out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.nomonitors.text"));
/* 1497 */                       out.write("</span>\n         \n          </td>\n          </tr>\n         \n    </table>\n    \n                    \n                    \n    ");
/*      */                     }
/*      */                     
/*      */ 
/*      */                   }
/* 1502 */                   else if ((reportname.equalsIgnoreCase("downtime")) || (reportname.equalsIgnoreCase("eumGlancereport")) || (reportname.equalsIgnoreCase("summary")) || ((resource.equalsIgnoreCase("indimonitor")) && (reportname.equalsIgnoreCase("glancereport"))))
/*      */                   {
/* 1504 */                     Vector resourceIds = new Vector();
/* 1505 */                     String cond = "";
/* 1506 */                     Vector selResids = new Vector();
/* 1507 */                     selResids.addAll(java.util.Arrays.asList(frm.getResourcestypes()));
/* 1508 */                     String ignoreSelIds = "";
/* 1509 */                     if (EnterpriseUtil.isIt360MSPEdition())
/*      */                     {
/* 1511 */                       cond = " AND RESOURCEID in (";
/* 1512 */                       resourceIds = com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.filterSiteBasedResourceIds(request.getParameter("site"), resourceIds);
/* 1513 */                       if ((resourceIds != null) && (resourceIds.size() > 0))
/*      */                       {
/* 1515 */                         for (int kk = 0; kk < resourceIds.size(); kk++)
/*      */                         {
/* 1517 */                           cond = cond + resourceIds.get(kk) + ",";
/*      */                         }
/*      */                       }
/* 1520 */                       cond = cond + "-1)";
/*      */                     }
/* 1522 */                     if ((selResids != null) && (selResids.size() > 0))
/*      */                     {
/* 1524 */                       ignoreSelIds = com.adventnet.appmanager.reporting.ReportUtilities.getNotCondition("AM_ManagedObject.RESOURCEID", new Vector(selResids));
/* 1525 */                       cond = cond + " and " + ignoreSelIds;
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/* 1530 */                     resourceTypes = request.getParameter("resourcetypes");
/* 1531 */                     query = "SELECT AM_ManagedObject.RESOURCEID,DISPLAYNAME FROM AM_ManagedObject " + datatable + " where TYPE in ('" + resourceTypes + "') " + querycon + cond + adminResourceIdentity + " ORDER BY DISPLAYNAME";
/* 1532 */                     if (reportname.equalsIgnoreCase("eumGlancereport")) {
/* 1533 */                       ArrayList eumChildIdList = com.adventnet.appmanager.util.Constants.getEUMChildList();
/* 1534 */                       String ignoreChildIds = com.adventnet.appmanager.reporting.ReportUtilities.getNotCondition("AM_ManagedObject.RESOURCEID", new Vector(eumChildIdList));
/* 1535 */                       query = "SELECT AM_ManagedObject.RESOURCEID,DISPLAYNAME FROM AM_ManagedObject " + datatable + " where TYPE in ('" + resourceTypes + "') and " + ignoreChildIds + " " + querycon + adminResourceIdentity + " ORDER BY DISPLAYNAME";
/*      */                     }
/* 1537 */                     if (query != null)
/*      */                     {
/* 1539 */                       ArrayList rows = mo.getRows(query);
/* 1540 */                       if (isCustomFieldFilter) {
/*      */                         try {
/* 1542 */                           String mgQuery = "select AM_ManagedObject.RESOURCEID from AM_ManagedObject " + datatable + "  where AM_ManagedObject.type='HAI' " + querycon;
/* 1543 */                           StringBuilder childids = MyFields.getCustomFieldMonitorsfromMG(mgQuery);
/* 1544 */                           if (childids.length() != 0) {
/* 1545 */                             query = "SELECT AM_ManagedObject.RESOURCEID,DISPLAYNAME FROM AM_ManagedObject left outer join " + fieldsDataTable + " on AM_ManagedObject.RESOURCEID=" + fieldsDataTable + ".RESOURCEID where TYPE in ('" + resourceTypes + "') " + adminResourceIdentity + " and AM_ManagedObject.RESOURCEID in (" + childids.substring(0, childids.length() - 1) + ") " + groupmonitors + "  ORDER BY DISPLAYNAME";
/* 1546 */                             ArrayList customrows = mo.getRows(query);
/* 1547 */                             rows.addAll(customrows);
/*      */                           }
/*      */                         } catch (Exception ex) {
/* 1550 */                           ex.printStackTrace();
/*      */                         }
/*      */                       }
/*      */                       
/* 1554 */                       if ("true".equals(request.getParameter("edit"))) {
/*      */                         try {
/* 1556 */                           String selQuery = "SELECT AM_ManagedObject.RESOURCEID,DISPLAYNAME FROM AM_ManagedObject where " + com.adventnet.appmanager.util.DBUtil.getCondition("RESOURCEID", selResids) + " ORDER BY DISPLAYNAME";
/*      */                           
/* 1558 */                           ArrayList selrows = mo.getRows(selQuery);
/* 1559 */                           rows.addAll(0, selrows);
/*      */                         } catch (Exception ex) {
/* 1561 */                           ex.printStackTrace();
/*      */                         }
/*      */                       }
/* 1564 */                       if (rows.size() > 0)
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/* 1569 */                         out.write("\n    \t\t\t\t<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" class=\"monitorinfoeven\">\n     \n            \t\t\t");
/* 1570 */                         if (("hasnapshot".equalsIgnoreCase(reportname)) || ("hasnapshotHost".equalsIgnoreCase(reportname)) || ("availabilitysnapshot".equalsIgnoreCase(reportname)) || ("weeklyoutage".equalsIgnoreCase(reportname)) || ("availabilitytrend".equalsIgnoreCase(reportname)))
/*      */                         {
/*      */ 
/* 1573 */                           out.write("\n            \t\t\t<tr><td> \n             \t\t\t\t");
/* 1574 */                           if (_jspx_meth_html_005fmultibox_005f20(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                             return;
/* 1576 */                           out.write("\n             \t\t\t\t<span class='bodytextbold'>");
/* 1577 */                           out.print(FormatUtil.getString("All"));
/* 1578 */                           out.write("</span></td></tr>\n            \t\t\t");
/*      */                         }
/*      */                         
/*      */ 
/* 1582 */                         out.write("\n           \n\t");
/*      */                         
/* 1584 */                         String bgclass = "whitegrayborder";
/*      */                         
/*      */ 
/* 1587 */                         for (int i = 0; i < rows.size(); i++)
/*      */                         {
/* 1589 */                           ArrayList row = (ArrayList)rows.get(i);
/* 1590 */                           String monid = String.valueOf(i);
/* 1591 */                           if (chkid == -1)
/*      */                           {
/* 1593 */                             monid = "MG-" + i;
/*      */                           }
/* 1595 */                           String monval = (String)row.get(0);
/* 1596 */                           String monName = (String)row.get(1);
/* 1597 */                           int resIdTOCheck = -1;
/* 1598 */                           resIdTOCheck = Integer.parseInt(monval);
/* 1599 */                           if ((resource.equalsIgnoreCase("monitorgroup")) && (EnterpriseUtil.isAdminServer()) && (resIdTOCheck > com.adventnet.appmanager.server.framework.comm.Constants.RANGE))
/*      */                           {
/* 1601 */                             monName = monName + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(monval);
/*      */                           }
/*      */                           
/* 1604 */                           out.write("\n       \t\t\t<tr>\n        \t\t<td>\n         \t\t");
/*      */                           
/* 1606 */                           MultiboxTag _jspx_th_html_005fmultibox_005f21 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 1607 */                           _jspx_th_html_005fmultibox_005f21.setPageContext(_jspx_page_context);
/* 1608 */                           _jspx_th_html_005fmultibox_005f21.setParent(_jspx_th_html_005fform_005f0);
/*      */                           
/* 1610 */                           _jspx_th_html_005fmultibox_005f21.setProperty("resourcestypes");
/*      */                           
/* 1612 */                           _jspx_th_html_005fmultibox_005f21.setStyleId(monid);
/*      */                           
/* 1614 */                           _jspx_th_html_005fmultibox_005f21.setValue(monval);
/* 1615 */                           int _jspx_eval_html_005fmultibox_005f21 = _jspx_th_html_005fmultibox_005f21.doStartTag();
/* 1616 */                           if (_jspx_th_html_005fmultibox_005f21.doEndTag() == 5) {
/* 1617 */                             this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f21); return;
/*      */                           }
/*      */                           
/* 1620 */                           this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f21);
/* 1621 */                           out.write("<span class='bodytext'>");
/* 1622 */                           out.print(monName);
/* 1623 */                           out.write("</span>\n          \t\t</td>\n          \t\t</tr>\n          \t\t");
/*      */                         }
/* 1625 */                         out.write("\n    \t\t</table>\n    \n    \t");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 1630 */                         out.write("\n                    <table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\"  border=\"0\" class=\"monitorinfoeven\">\n\t\t\t<tr>\n        \t\t<td>\n         \t\t<span class='bodytext'>");
/* 1631 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.nomonitors.text"));
/* 1632 */                         out.write("</span>\n              \t\t</td>\n          \t\t</tr>\n         \t\t</table>\n ");
/*      */                       }
/*      */                     }
/*      */                   }
/* 1636 */                   else if ((reportname.equalsIgnoreCase("event")) || (resource.equalsIgnoreCase("monitorgroup")) || ("hasnapshot".equalsIgnoreCase(reportname)) || ("hasnapshotHost".equalsIgnoreCase(reportname)) || ("availabilitysnapshot".equalsIgnoreCase(reportname)) || ("weeklyoutage".equalsIgnoreCase(reportname)) || ("availabilitytrend".equalsIgnoreCase(reportname)))
/*      */                   {
/*      */ 
/*      */ 
/* 1640 */                     out.write("\n\t\n\t");
/* 1641 */                     JspRuntimeLibrary.include(request, response, "/jsp/ScheduleReportsMGTree.jsp" + ("/jsp/ScheduleReportsMGTree.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("customfieldfilter", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(isCustomFieldFilter), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("aliasname", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(aliasname), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("customValue", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(customValue), request.getCharacterEncoding()), out, true);
/* 1642 */                     out.write(10);
/* 1643 */                     out.write(9);
/* 1644 */                     out.write(10);
/*      */                   }
/*      */                   
/*      */ 
/* 1648 */                   out.write("\n\n\t\n\n\n <!--the form is commented bcoz the IE 6 application will not allow multiple form in a single page so form is commented dont remove the comment-->    \n  <!--  ");
/* 1649 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 1650 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1654 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 1655 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */               }
/*      */               
/* 1658 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 1659 */               out.write(" -->\n     </td></tr>\n     </table>\n");
/* 1660 */               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1661 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1665 */           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1666 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */           }
/*      */           
/* 1669 */           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1670 */           out.write(10);
/* 1671 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1672 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1676 */       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1677 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */       }
/*      */       else {
/* 1680 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1681 */         out.write(10);
/* 1682 */         out.write(10);
/*      */       }
/* 1684 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1685 */         out = _jspx_out;
/* 1686 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1687 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 1688 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1691 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1697 */     PageContext pageContext = _jspx_page_context;
/* 1698 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1700 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1701 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1702 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1704 */     _jspx_th_c_005fout_005f0.setValue("${selected}");
/* 1705 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1706 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1707 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1708 */       return true;
/*      */     }
/* 1710 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1711 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1716 */     PageContext pageContext = _jspx_page_context;
/* 1717 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1719 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1720 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1721 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1723 */     _jspx_th_c_005fout_005f1.setValue("${eachtag.labelalias}");
/* 1724 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1725 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1726 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1727 */       return true;
/*      */     }
/* 1729 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1730 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1735 */     PageContext pageContext = _jspx_page_context;
/* 1736 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1738 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1739 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1740 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1742 */     _jspx_th_c_005fout_005f2.setValue("${eachtag.fieldid}");
/* 1743 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1744 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1745 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1746 */       return true;
/*      */     }
/* 1748 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1749 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1754 */     PageContext pageContext = _jspx_page_context;
/* 1755 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1757 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1758 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1759 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1761 */     _jspx_th_c_005fout_005f3.setValue("${eachtag.label}");
/* 1762 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1763 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1764 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1765 */       return true;
/*      */     }
/* 1767 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1768 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1773 */     PageContext pageContext = _jspx_page_context;
/* 1774 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1776 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 1777 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 1778 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1780 */     _jspx_th_html_005fselect_005f0.setProperty("scheduleReportResCombo1");
/*      */     
/* 1782 */     _jspx_th_html_005fselect_005f0.setStyle("width:100%;overflow-x: scroll;");
/*      */     
/* 1784 */     _jspx_th_html_005fselect_005f0.setMultiple("true");
/*      */     
/* 1786 */     _jspx_th_html_005fselect_005f0.setSize("10");
/* 1787 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 1788 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 1789 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1790 */         out = _jspx_page_context.pushBody();
/* 1791 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 1792 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1795 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 1796 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 1797 */           return true;
/* 1798 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 1799 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 1800 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1803 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 1804 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1807 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 1808 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f0);
/* 1809 */       return true;
/*      */     }
/* 1811 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f0);
/* 1812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1817 */     PageContext pageContext = _jspx_page_context;
/* 1818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1820 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1821 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 1822 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*      */     
/* 1824 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("toAdd");
/* 1825 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 1826 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 1827 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 1828 */       return true;
/*      */     }
/* 1830 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 1831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1836 */     PageContext pageContext = _jspx_page_context;
/* 1837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1839 */     SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 1840 */     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 1841 */     _jspx_th_html_005fselect_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1843 */     _jspx_th_html_005fselect_005f1.setProperty("scheduleReportResCombo2");
/*      */     
/* 1845 */     _jspx_th_html_005fselect_005f1.setStyle("width:100%");
/*      */     
/* 1847 */     _jspx_th_html_005fselect_005f1.setMultiple("true");
/*      */     
/* 1849 */     _jspx_th_html_005fselect_005f1.setSize("10");
/* 1850 */     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 1851 */     if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 1852 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 1853 */         out = _jspx_page_context.pushBody();
/* 1854 */         _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 1855 */         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1858 */         out.write("\n\t\t               \t\t\t\t\t");
/* 1859 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/* 1860 */           return true;
/* 1861 */         out.write("\n\t\t\t \t\t\t\t    \t");
/* 1862 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 1863 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1866 */       if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 1867 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1870 */     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 1871 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f1);
/* 1872 */       return true;
/*      */     }
/* 1874 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f1);
/* 1875 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1880 */     PageContext pageContext = _jspx_page_context;
/* 1881 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1883 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1884 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 1885 */     _jspx_th_html_005foptionsCollection_005f1.setParent((Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 1887 */     _jspx_th_html_005foptionsCollection_005f1.setProperty("present");
/* 1888 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 1889 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 1890 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 1891 */       return true;
/*      */     }
/* 1893 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 1894 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1899 */     PageContext pageContext = _jspx_page_context;
/* 1900 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1902 */     SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 1903 */     _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 1904 */     _jspx_th_html_005fselect_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1906 */     _jspx_th_html_005fselect_005f2.setProperty("scheduleReportResCombo1");
/*      */     
/* 1908 */     _jspx_th_html_005fselect_005f2.setStyle("width:100%;overflow-x: scroll;");
/*      */     
/* 1910 */     _jspx_th_html_005fselect_005f2.setMultiple("true");
/*      */     
/* 1912 */     _jspx_th_html_005fselect_005f2.setSize("10");
/* 1913 */     int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 1914 */     if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 1915 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 1916 */         out = _jspx_page_context.pushBody();
/* 1917 */         _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 1918 */         _jspx_th_html_005fselect_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1921 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 1922 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/* 1923 */           return true;
/* 1924 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 1925 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 1926 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1929 */       if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 1930 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1933 */     if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 1934 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f2);
/* 1935 */       return true;
/*      */     }
/* 1937 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f2);
/* 1938 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1943 */     PageContext pageContext = _jspx_page_context;
/* 1944 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1946 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 1947 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 1948 */     _jspx_th_html_005foptionsCollection_005f2.setParent((Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 1950 */     _jspx_th_html_005foptionsCollection_005f2.setProperty("toAdd");
/* 1951 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 1952 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 1953 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 1954 */       return true;
/*      */     }
/* 1956 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 1957 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1962 */     PageContext pageContext = _jspx_page_context;
/* 1963 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1965 */     SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 1966 */     _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 1967 */     _jspx_th_html_005fselect_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1969 */     _jspx_th_html_005fselect_005f3.setProperty("scheduleReportResCombo2");
/*      */     
/* 1971 */     _jspx_th_html_005fselect_005f3.setStyle("width:100%");
/*      */     
/* 1973 */     _jspx_th_html_005fselect_005f3.setMultiple("true");
/*      */     
/* 1975 */     _jspx_th_html_005fselect_005f3.setSize("10");
/* 1976 */     int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 1977 */     if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 1978 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 1979 */         out = _jspx_page_context.pushBody();
/* 1980 */         _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 1981 */         _jspx_th_html_005fselect_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1984 */         out.write("\n\t\t               \t\t\t\t\t");
/* 1985 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/* 1986 */           return true;
/* 1987 */         out.write("\n\t\t\t \t\t\t\t    \t");
/* 1988 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 1989 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1992 */       if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 1993 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1996 */     if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 1997 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f3);
/* 1998 */       return true;
/*      */     }
/* 2000 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f3);
/* 2001 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2006 */     PageContext pageContext = _jspx_page_context;
/* 2007 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2009 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 2010 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 2011 */     _jspx_th_html_005foptionsCollection_005f3.setParent((Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 2013 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("present");
/* 2014 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 2015 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 2016 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 2017 */       return true;
/*      */     }
/* 2019 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 2020 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2025 */     PageContext pageContext = _jspx_page_context;
/* 2026 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2028 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 2029 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 2030 */     _jspx_th_html_005fselect_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2032 */     _jspx_th_html_005fselect_005f4.setProperty("scheduleReportDashboardCombo1");
/*      */     
/* 2034 */     _jspx_th_html_005fselect_005f4.setStyle("width:100%;overflow-x: scroll;");
/*      */     
/* 2036 */     _jspx_th_html_005fselect_005f4.setMultiple("true");
/*      */     
/* 2038 */     _jspx_th_html_005fselect_005f4.setSize("10");
/* 2039 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 2040 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 2041 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 2042 */         out = _jspx_page_context.pushBody();
/* 2043 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 2044 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2047 */         out.write("\n\t\t\t\t\t\t\t\t\t");
/* 2048 */         if (_jspx_meth_html_005foptionsCollection_005f4(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 2049 */           return true;
/* 2050 */         out.write("\n\t\t\t\t\t\t\t\t");
/* 2051 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 2052 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2055 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 2056 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2059 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 2060 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f4);
/* 2061 */       return true;
/*      */     }
/* 2063 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f4);
/* 2064 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f4(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2069 */     PageContext pageContext = _jspx_page_context;
/* 2070 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2072 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f4 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 2073 */     _jspx_th_html_005foptionsCollection_005f4.setPageContext(_jspx_page_context);
/* 2074 */     _jspx_th_html_005foptionsCollection_005f4.setParent((Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 2076 */     _jspx_th_html_005foptionsCollection_005f4.setProperty("toAddg");
/* 2077 */     int _jspx_eval_html_005foptionsCollection_005f4 = _jspx_th_html_005foptionsCollection_005f4.doStartTag();
/* 2078 */     if (_jspx_th_html_005foptionsCollection_005f4.doEndTag() == 5) {
/* 2079 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 2080 */       return true;
/*      */     }
/* 2082 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f4);
/* 2083 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2088 */     PageContext pageContext = _jspx_page_context;
/* 2089 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2091 */     SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.get(SelectTag.class);
/* 2092 */     _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 2093 */     _jspx_th_html_005fselect_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2095 */     _jspx_th_html_005fselect_005f5.setProperty("scheduleReportDashboardCombo2");
/*      */     
/* 2097 */     _jspx_th_html_005fselect_005f5.setStyle("width:100%");
/*      */     
/* 2099 */     _jspx_th_html_005fselect_005f5.setMultiple("true");
/*      */     
/* 2101 */     _jspx_th_html_005fselect_005f5.setSize("10");
/* 2102 */     int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 2103 */     if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 2104 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 2105 */         out = _jspx_page_context.pushBody();
/* 2106 */         _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 2107 */         _jspx_th_html_005fselect_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2110 */         out.write("\n\t               \t\t\t\t\t");
/* 2111 */         if (_jspx_meth_html_005foptionsCollection_005f5(_jspx_th_html_005fselect_005f5, _jspx_page_context))
/* 2112 */           return true;
/* 2113 */         out.write("\n\t\t \t\t\t\t    \t");
/* 2114 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 2115 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2118 */       if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 2119 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2122 */     if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 2123 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f5);
/* 2124 */       return true;
/*      */     }
/* 2126 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fsize_005fproperty_005fmultiple.reuse(_jspx_th_html_005fselect_005f5);
/* 2127 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f5(JspTag _jspx_th_html_005fselect_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2132 */     PageContext pageContext = _jspx_page_context;
/* 2133 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2135 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f5 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 2136 */     _jspx_th_html_005foptionsCollection_005f5.setPageContext(_jspx_page_context);
/* 2137 */     _jspx_th_html_005foptionsCollection_005f5.setParent((Tag)_jspx_th_html_005fselect_005f5);
/*      */     
/* 2139 */     _jspx_th_html_005foptionsCollection_005f5.setProperty("presentg");
/* 2140 */     int _jspx_eval_html_005foptionsCollection_005f5 = _jspx_th_html_005foptionsCollection_005f5.doStartTag();
/* 2141 */     if (_jspx_th_html_005foptionsCollection_005f5.doEndTag() == 5) {
/* 2142 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 2143 */       return true;
/*      */     }
/* 2145 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f5);
/* 2146 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2151 */     PageContext pageContext = _jspx_page_context;
/* 2152 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2154 */     MultiboxTag _jspx_th_html_005fmultibox_005f3 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.get(MultiboxTag.class);
/* 2155 */     _jspx_th_html_005fmultibox_005f3.setPageContext(_jspx_page_context);
/* 2156 */     _jspx_th_html_005fmultibox_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2158 */     _jspx_th_html_005fmultibox_005f3.setProperty("resourcestypes");
/*      */     
/* 2160 */     _jspx_th_html_005fmultibox_005f3.setStyleId("1");
/*      */     
/* 2162 */     _jspx_th_html_005fmultibox_005f3.setValue("Application servers");
/*      */     
/* 2164 */     _jspx_th_html_005fmultibox_005f3.setOnclick("javascript:checkAppServer()");
/* 2165 */     int _jspx_eval_html_005fmultibox_005f3 = _jspx_th_html_005fmultibox_005f3.doStartTag();
/* 2166 */     if (_jspx_th_html_005fmultibox_005f3.doEndTag() == 5) {
/* 2167 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f3);
/* 2168 */       return true;
/*      */     }
/* 2170 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f3);
/* 2171 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2176 */     PageContext pageContext = _jspx_page_context;
/* 2177 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2179 */     MultiboxTag _jspx_th_html_005fmultibox_005f5 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.get(MultiboxTag.class);
/* 2180 */     _jspx_th_html_005fmultibox_005f5.setPageContext(_jspx_page_context);
/* 2181 */     _jspx_th_html_005fmultibox_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2183 */     _jspx_th_html_005fmultibox_005f5.setProperty("resourcestypes");
/*      */     
/* 2185 */     _jspx_th_html_005fmultibox_005f5.setStyleId("12");
/*      */     
/* 2187 */     _jspx_th_html_005fmultibox_005f5.setValue("Web Services");
/*      */     
/* 2189 */     _jspx_th_html_005fmultibox_005f5.setOnclick("javascript:checkWebServer()");
/* 2190 */     int _jspx_eval_html_005fmultibox_005f5 = _jspx_th_html_005fmultibox_005f5.doStartTag();
/* 2191 */     if (_jspx_th_html_005fmultibox_005f5.doEndTag() == 5) {
/* 2192 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f5);
/* 2193 */       return true;
/*      */     }
/* 2195 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f5);
/* 2196 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2201 */     PageContext pageContext = _jspx_page_context;
/* 2202 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2204 */     MultiboxTag _jspx_th_html_005fmultibox_005f7 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.get(MultiboxTag.class);
/* 2205 */     _jspx_th_html_005fmultibox_005f7.setPageContext(_jspx_page_context);
/* 2206 */     _jspx_th_html_005fmultibox_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2208 */     _jspx_th_html_005fmultibox_005f7.setProperty("resourcestypes");
/*      */     
/* 2210 */     _jspx_th_html_005fmultibox_005f7.setStyleId("7");
/*      */     
/* 2212 */     _jspx_th_html_005fmultibox_005f7.setValue("Database Servers");
/*      */     
/* 2214 */     _jspx_th_html_005fmultibox_005f7.setOnclick("javascript:checkDBServer()");
/* 2215 */     int _jspx_eval_html_005fmultibox_005f7 = _jspx_th_html_005fmultibox_005f7.doStartTag();
/* 2216 */     if (_jspx_th_html_005fmultibox_005f7.doEndTag() == 5) {
/* 2217 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f7);
/* 2218 */       return true;
/*      */     }
/* 2220 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f7);
/* 2221 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2226 */     PageContext pageContext = _jspx_page_context;
/* 2227 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2229 */     MultiboxTag _jspx_th_html_005fmultibox_005f9 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.get(MultiboxTag.class);
/* 2230 */     _jspx_th_html_005fmultibox_005f9.setPageContext(_jspx_page_context);
/* 2231 */     _jspx_th_html_005fmultibox_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2233 */     _jspx_th_html_005fmultibox_005f9.setProperty("resourcestypes");
/*      */     
/* 2235 */     _jspx_th_html_005fmultibox_005f9.setStyleId("19");
/*      */     
/* 2237 */     _jspx_th_html_005fmultibox_005f9.setValue("Servers");
/*      */     
/* 2239 */     _jspx_th_html_005fmultibox_005f9.setOnclick("javascript:checkServer()");
/* 2240 */     int _jspx_eval_html_005fmultibox_005f9 = _jspx_th_html_005fmultibox_005f9.doStartTag();
/* 2241 */     if (_jspx_th_html_005fmultibox_005f9.doEndTag() == 5) {
/* 2242 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f9);
/* 2243 */       return true;
/*      */     }
/* 2245 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f9);
/* 2246 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2251 */     PageContext pageContext = _jspx_page_context;
/* 2252 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2254 */     MultiboxTag _jspx_th_html_005fmultibox_005f11 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.get(MultiboxTag.class);
/* 2255 */     _jspx_th_html_005fmultibox_005f11.setPageContext(_jspx_page_context);
/* 2256 */     _jspx_th_html_005fmultibox_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2258 */     _jspx_th_html_005fmultibox_005f11.setProperty("resourcestypes");
/*      */     
/* 2260 */     _jspx_th_html_005fmultibox_005f11.setStyleId("<%//=win%>");
/*      */     
/* 2262 */     _jspx_th_html_005fmultibox_005f11.setValue("Windows");
/* 2263 */     int _jspx_eval_html_005fmultibox_005f11 = _jspx_th_html_005fmultibox_005f11.doStartTag();
/* 2264 */     if (_jspx_th_html_005fmultibox_005f11.doEndTag() == 5) {
/* 2265 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f11);
/* 2266 */       return true;
/*      */     }
/* 2268 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f11);
/* 2269 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2274 */     PageContext pageContext = _jspx_page_context;
/* 2275 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2277 */     MultiboxTag _jspx_th_html_005fmultibox_005f12 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.get(MultiboxTag.class);
/* 2278 */     _jspx_th_html_005fmultibox_005f12.setPageContext(_jspx_page_context);
/* 2279 */     _jspx_th_html_005fmultibox_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2281 */     _jspx_th_html_005fmultibox_005f12.setProperty("resourcestypes");
/*      */     
/* 2283 */     _jspx_th_html_005fmultibox_005f12.setStyleId("40");
/*      */     
/* 2285 */     _jspx_th_html_005fmultibox_005f12.setValue("Transaction Monitors");
/*      */     
/* 2287 */     _jspx_th_html_005fmultibox_005f12.setOnclick("javascript:checkJavaServer()");
/* 2288 */     int _jspx_eval_html_005fmultibox_005f12 = _jspx_th_html_005fmultibox_005f12.doStartTag();
/* 2289 */     if (_jspx_th_html_005fmultibox_005f12.doEndTag() == 5) {
/* 2290 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f12);
/* 2291 */       return true;
/*      */     }
/* 2293 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f12);
/* 2294 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f17(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2299 */     PageContext pageContext = _jspx_page_context;
/* 2300 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2302 */     MultiboxTag _jspx_th_html_005fmultibox_005f17 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.get(MultiboxTag.class);
/* 2303 */     _jspx_th_html_005fmultibox_005f17.setPageContext(_jspx_page_context);
/* 2304 */     _jspx_th_html_005fmultibox_005f17.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2306 */     _jspx_th_html_005fmultibox_005f17.setProperty("resourcestypes");
/*      */     
/* 2308 */     _jspx_th_html_005fmultibox_005f17.setStyleId("70");
/*      */     
/* 2310 */     _jspx_th_html_005fmultibox_005f17.setValue("ERP Monitors");
/*      */     
/* 2312 */     _jspx_th_html_005fmultibox_005f17.setOnclick("javascript:checkERPServer()");
/* 2313 */     int _jspx_eval_html_005fmultibox_005f17 = _jspx_th_html_005fmultibox_005f17.doStartTag();
/* 2314 */     if (_jspx_th_html_005fmultibox_005f17.doEndTag() == 5) {
/* 2315 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f17);
/* 2316 */       return true;
/*      */     }
/* 2318 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f17);
/* 2319 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f20(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2324 */     PageContext pageContext = _jspx_page_context;
/* 2325 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2327 */     MultiboxTag _jspx_th_html_005fmultibox_005f20 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.get(MultiboxTag.class);
/* 2328 */     _jspx_th_html_005fmultibox_005f20.setPageContext(_jspx_page_context);
/* 2329 */     _jspx_th_html_005fmultibox_005f20.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2331 */     _jspx_th_html_005fmultibox_005f20.setProperty("resourcestypes");
/*      */     
/* 2333 */     _jspx_th_html_005fmultibox_005f20.setStyleId("MG-99");
/*      */     
/* 2335 */     _jspx_th_html_005fmultibox_005f20.setValue("all");
/*      */     
/* 2337 */     _jspx_th_html_005fmultibox_005f20.setOnclick("javascript:checkMonitorGroup()");
/* 2338 */     int _jspx_eval_html_005fmultibox_005f20 = _jspx_th_html_005fmultibox_005f20.doStartTag();
/* 2339 */     if (_jspx_th_html_005fmultibox_005f20.doEndTag() == 5) {
/* 2340 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f20);
/* 2341 */       return true;
/*      */     }
/* 2343 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fstyleId_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fmultibox_005f20);
/* 2344 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ScheduleReportsResources_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */