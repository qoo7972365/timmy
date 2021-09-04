/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.util.HashMap;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.MatchTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class SelectDependentMonitor_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   22 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   28 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(1);
/*   29 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmatch_0026_005fvalue_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   48 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   52 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   53 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   54 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   55 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   56 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005flogic_005fmatch_0026_005fvalue_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   64 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   68 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   69 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   70 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*   71 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   72 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   73 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   74 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.release();
/*   75 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*   76 */     this._005fjspx_005ftagPool_005flogic_005fmatch_0026_005fvalue_005fname.release();
/*   77 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   78 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   85 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   88 */     JspWriter out = null;
/*   89 */     Object page = this;
/*   90 */     JspWriter _jspx_out = null;
/*   91 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*   95 */       response.setContentType("text/html;charset=UTF-8");
/*   96 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*   98 */       _jspx_page_context = pageContext;
/*   99 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  100 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  101 */       session = pageContext.getSession();
/*  102 */       out = pageContext.getOut();
/*  103 */       _jspx_out = out;
/*      */       
/*  105 */       out.write("<!DOCTYPE html>\n");
/*  106 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!-- $Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  107 */       com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = null;
/*  108 */       mo = (com.adventnet.appmanager.client.resourcemanagement.ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/*  109 */       if (mo == null) {
/*  110 */         mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
/*  111 */         _jspx_page_context.setAttribute("mo", mo, 1);
/*      */       }
/*  113 */       out.write("\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  114 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  116 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*      */       
/*      */ 
/*  119 */       request.setAttribute("isAdminServer", Boolean.valueOf(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()));
/*  120 */       request.setAttribute("isManagedServer", Boolean.valueOf(com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()));
/*      */       
/*  122 */       out.write(10);
/*  123 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  124 */       out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/json2.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<script>\nfunction checkAndSubmit()\n{\n\t\tvar monitorAlreadySelected = [];\n\t\tif(document.getElementById('monitorSelected').value != \"\")\n\t\t{\n\t\t\tmonitorAlreadySelected = JSON.parse(document.getElementById('monitorSelected').value);\n\t\t}\n\t\tvar dependentArr = {};\n\t\tvar selectedMonitors = [];\n\t\tvar len = document.dependent.resourceid.length;\n\t\tif(typeof len != 'undefined')\n\t\t{\n\t\t\tvar j = 0;\n\t\t\tvar isMAS = (typeof document.dependent.mas != 'undefined')?true:false; ");
/*  125 */       out.write("\n\t\t\tfor(i=0;i<len;i++)\n\t\t\t{\n\t\t\t\tif(document.dependent.resourceid[i].checked)\n\t\t\t\t{\n\t\t\t\t\tvar resourceIDStr = document.dependent.resourceid[i].value;\n\t\t\t\t\tvar resourceNameStr = document.dependent.resourceName[i].value;\n\t\t\t\t\tvar resourceTypeNameStr = document.dependent.resourceTypeName[i].value;\n\t\t\t\t\tvar imagePath = document.dependent.imagePath[i].value;\n\t\t\t\t\tvar resourceType = document.dependent.resourceType[i].value;\n\t\t\t\t\tvar masStr = \"\";\n\t\t\t\t\tvar index = $.inArray(resourceIDStr, monitorAlreadySelected);\n\t\t\t\t\tif(index != -1)\n\t\t\t\t\t{\n\t\t\t\t\t\talert(getI8NKey(' ");
/*  126 */       out.print(FormatUtil.getString("am.webclient.dependentDevice.alreadyExists.text"));
/*  127 */       out.write("', resourceNameStr));\n\t\t\t\t\t\treturn false;\n\t\t\t\t\t}\n\t\t\t\t\tselectedMonitors[j] = resourceIDStr;\n\t\t\t\t\tif(isMAS)\n\t\t\t\t\t{\n\t\t\t\t\t\tmasStr = document.dependent.mas[i].value;\n\t\t\t\t\t}\n\t\t\t\t\tdependentArr[resourceIDStr] = {'resourceid':resourceIDStr,'displayname':resourceNameStr,'managedServer':masStr, 'typeName':resourceTypeNameStr,'resourceType':resourceType,'imagepath':imagePath};\n\t\t\t\t\tj++;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t\telse\n\t\t{\n\t\t\tvar resourceIDStr = document.dependent.resourceid.value;\n\t\t\tvar resourceNameStr = document.dependent.resourceName.value;\n\t\t\tvar resourceTypeNameStr = document.dependent.resourceTypeName.value;\n\t\t\tvar imagePath = document.dependent.imagePath.value;\n\t\t\tvar resourceType = document.dependent.resourceType.value;\n\t\t\tvar isMAS = (typeof document.dependent.mas != 'undefined')?true:false;\n\t\t\tvar masStr = \"\";\n\t\t\tvar index = $.inArray(resourceIDStr, monitorAlreadySelected);\n\t\t\tif(index != -1)\n\t\t\t{\n\t\t\t\talert(getI8NKey(' ");
/*  128 */       out.print(FormatUtil.getString("am.webclient.dependentDevice.alreadyExists.text"));
/*  129 */       out.write("', resourceNameStr));\n\t\t\t\treturn false;\n\t\t\t}\n\t\t\tselectedMonitors[0] = resourceIDStr;\n\t\t\tif(isMAS)\n\t\t\t{\n\t\t\t\tmasStr = document.dependent.mas.value;\n\t\t\t}\n\t\t\tdependentArr[resourceIDStr] = {'resourceid':resourceIDStr,'displayname':resourceNameStr,'managedServer':masStr, 'typeName':resourceTypeNameStr,'resourceType':resourceType,'imagepath':imagePath};\n\t\t}\n\t\tvar cnt = getJSONArrayObjectCount(dependentArr);\n\t\tif(cnt == 0)\n\t\t{\n\t\t\talert('");
/*  130 */       out.print(FormatUtil.getString("am.webclient.dependentdevice.conf.alert"));
/*  131 */       out.write("');\n\t\t\treturn;\n\t\t}\n\t\tvar monitorAlreadySelectedLength = monitorAlreadySelected.length;\n\t\tvar selectedMonitorsLength = selectedMonitors.length;\n\t\tfor(var i=0;i<monitorAlreadySelectedLength;i++)\n\t\t{\n\t\t\tselectedMonitors[selectedMonitorsLength+i] = monitorAlreadySelected[i];\n\t\t}\n\t\tdocument.getElementById('monitorSelected').value = JSON.stringify(selectedMonitors);\n\t\twindow.opener.setDependentDetails(dependentArr, document.getElementById('monitorSelected').value);\n    \t\twindow.close();\n}\n\nfunction invokeOperations()\n{\n\tvar mas = \"AllMASServer\";\n\tvar masObj = document.getElementById('managedServer');\n\tif(masObj != null)\n\t{\n\t\tmas = masObj.value;\n\t}\n\tvar selMonitors = document.getElementById('monitorSelected').value;\n\tURL=\"/showresource.do?method=selectDependentMonitorFilter&type=\"+document.getElementById(\"montypes\").value+\"&haid=");
/*  132 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */         return;
/*  134 */       out.write("\"+\"&managedServer=\"+mas+\"&selectedMonitors=\"+encodeURIComponent(selMonitors); // No I18N\n\thttp1=getHTTPObject();\n\thttp1.onreadystatechange = getOperationsOutput;\n\thttp1.open(\"POST\", URL, true);\n\thttp1.send(null);\n\treturn false;\n}\n\nfunction getOperationsOutput()\n{\n\tif(http1.readyState == 4 && http1.status == 200)\n\t{\n\t\tdocument.getElementById(\"filteredMonitors\").innerHTML = http1.responseText;\n\t\tdocument.getElementById(\"defaultMonitors\").style.display='none'; // No I18N\n\t\tdocument.getElementById(\"defaultMonitors\").innerHTML = '&nbsp;'; // No I18N\n\t\tdocument.getElementById(\"filteredMonitors\").style.display='block'; // No I18N\n\t}\n}\n\n\n\n</script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/json2.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"55\" class=\"darkheaderbg\">\n<tr>\n\t  <td>&nbsp;<span class=\"headingboldwhite\">");
/*  135 */       out.print(FormatUtil.getString("am.webclient.dependentDevice.chooseMO"));
/*  136 */       out.write("</span></td>\n</tr>\n</table>\n<table width=\"98%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"margin-top:10px;margin-left:9px;margin-right:10px\">\n<tr>\n \t\t\t<td class=\"AlarmHdrLeftTop\">\n \t\t\t</td><td class=\"AlarmHdrBgTop\">\n \t\t\t</td><td class=\"AlarmHdrTopRight\">\n \t\t\t</td></tr>\n<tr>\n<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n<td valign=\"top\">\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n<tr>\n<td style=\"padding-top:3px;\" class=\"AlarmboxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n<tr>\n<td >\n<!--text -->\n</td>\n</tr>\n\n<tr>\n<td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n<tr>\n<td class=\"AlarmInnerTopLeft\"/>\n<td class=\"AlarmInnerTopBg\"/>\n<td class=\"AlarmInnerTopRight\"/>\n</tr>\n<tr>\n\t<td colspan=3 >\n\t \t<table width='100%' class=\"AlarmInnerBoxBg\">\n\t \t\t");
/*      */       
/*  138 */       if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */       {
/*      */ 
/*  141 */         out.write("\n\t\t\t<tr>\n\t \t\t\t<td>  <span class='bodytextbold'>&nbsp; ");
/*  142 */         out.print(FormatUtil.getString("am.webclient.filterby.text"));
/*  143 */         out.write(" : &nbsp; ");
/*  144 */         out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.resourcetypes.text"));
/*  145 */         out.write("</span>\n\t\t\t\t\t<select name=\"montypes\" id=\"montypes\" class=\"formtext\" onchange=\"javascript:invokeOperations()\">\n\t\t\t\t\t     <option selected=\"true\" value=\"selectactions\">");
/*  146 */         out.print(FormatUtil.getString("All Monitors"));
/*  147 */         out.write("</option>\n\t\t\t\t\t     ");
/*      */         
/*  149 */         IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  150 */         _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/*  151 */         _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */         
/*  153 */         _jspx_th_logic_005fiterate_005f0.setName("types");
/*      */         
/*  155 */         _jspx_th_logic_005fiterate_005f0.setId("types");
/*      */         
/*  157 */         _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */         
/*  159 */         _jspx_th_logic_005fiterate_005f0.setType("String");
/*  160 */         int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/*  161 */         if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/*  162 */           String types = null;
/*  163 */           Integer j = null;
/*  164 */           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  165 */             out = _jspx_page_context.pushBody();
/*  166 */             _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  167 */             _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */           }
/*  169 */           types = (String)_jspx_page_context.findAttribute("types");
/*  170 */           j = (Integer)_jspx_page_context.findAttribute("j");
/*      */           for (;;) {
/*  172 */             out.write("\n\t\t\t\t\t     <option value=\"");
/*  173 */             out.print(types);
/*  174 */             out.write(34);
/*  175 */             out.write(62);
/*  176 */             out.print(FormatUtil.getString(types));
/*  177 */             out.write("</option>\n\t\t\t\t\t     ");
/*  178 */             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/*  179 */             types = (String)_jspx_page_context.findAttribute("types");
/*  180 */             j = (Integer)_jspx_page_context.findAttribute("j");
/*  181 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*  184 */           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  185 */             out = _jspx_page_context.popBody();
/*      */           }
/*      */         }
/*  188 */         if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/*  189 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */         }
/*      */         
/*  192 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*  193 */         out.write("\n\t\t\t\t\t</select>\n\t \t\t\t</td>\n\t \t\t\t<td>\n\t\t\t\t");
/*      */         
/*  195 */         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  196 */         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  197 */         _jspx_th_c_005fif_005f0.setParent(null);
/*      */         
/*  199 */         _jspx_th_c_005fif_005f0.setTest("${MAS != null}");
/*  200 */         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  201 */         if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */           for (;;) {
/*  203 */             out.write("\n                                <span class='bodytextbold'>");
/*  204 */             out.print(FormatUtil.getString(" Managed Server"));
/*  205 */             out.write("</span>\n                                        <select name=\"managedServer\" id=\"managedServer\" class=\"formtext\" onchange=\"javascript:invokeOperations()\">\n                                             <option selected=\"true\" value=\"AllMASServer\">");
/*  206 */             out.print(FormatUtil.getString("All Managed Servers"));
/*  207 */             out.write("</option>\n                                             ");
/*      */             
/*  209 */             IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  210 */             _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/*  211 */             _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_c_005fif_005f0);
/*      */             
/*  213 */             _jspx_th_logic_005fiterate_005f1.setName("MAS");
/*      */             
/*  215 */             _jspx_th_logic_005fiterate_005f1.setId("masInfo");
/*      */             
/*  217 */             _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */             
/*  219 */             _jspx_th_logic_005fiterate_005f1.setType("String");
/*  220 */             int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/*  221 */             if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/*  222 */               String masInfo = null;
/*  223 */               Integer j = null;
/*  224 */               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/*  225 */                 out = _jspx_page_context.pushBody();
/*  226 */                 _jspx_th_logic_005fiterate_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  227 */                 _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */               }
/*  229 */               masInfo = (String)_jspx_page_context.findAttribute("masInfo");
/*  230 */               j = (Integer)_jspx_page_context.findAttribute("j");
/*      */               for (;;) {
/*  232 */                 out.write("\n                                             ");
/*  233 */                 String[] masServer = masInfo.split("_");
/*  234 */                 out.write("\n                                             <option value=\"");
/*  235 */                 out.print(masServer[1]);
/*  236 */                 out.write(34);
/*  237 */                 out.write(62);
/*  238 */                 out.write(32);
/*  239 */                 out.print(masServer[0]);
/*  240 */                 out.write("</option>\n                                             ");
/*  241 */                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/*  242 */                 masInfo = (String)_jspx_page_context.findAttribute("masInfo");
/*  243 */                 j = (Integer)_jspx_page_context.findAttribute("j");
/*  244 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  247 */               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/*  248 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  251 */             if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/*  252 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */             }
/*      */             
/*  255 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/*  256 */             out.write("\n                                        </select>\n                                ");
/*  257 */             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  258 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  262 */         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  263 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */         }
/*      */         
/*  266 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  267 */         out.write("\n\t\t\t\t</td>\n\t\t\t\t");
/*      */         
/*  269 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  270 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  271 */         _jspx_th_c_005fif_005f1.setParent(null);
/*      */         
/*  273 */         _jspx_th_c_005fif_005f1.setTest("${isAdminServer || isManagedServer}");
/*  274 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  275 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */           for (;;) {
/*  277 */             out.write("\n\t \t\t\t<td align='right'>\n\t \t\t\t<form action=\"/showresource.do\">\n\t \t\t\t\t<input type='hidden' name='method' value='searchDependentMonitor'>\n\t\t\t\t\t<input type='hidden' name='haid' value='");
/*  278 */             if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */               return;
/*  280 */             out.write("'>\n\t \t\t\t\t<input type=\"text\"  size=\"14\" onblur=\"if(this.value == '')this.value='");
/*  281 */             out.print(FormatUtil.getString("am.webclient.dependentDevice.search.text"));
/*  282 */             out.write("';\" onclick=\"if(this.value == '");
/*  283 */             out.print(FormatUtil.getString("am.webclient.dependentDevice.search.text"));
/*  284 */             out.write("')this.value='';\" value=\"");
/*  285 */             out.print(FormatUtil.getString("am.webclient.dependentDevice.search.text"));
/*  286 */             out.write("\" class=\"searchBox\" name=\"query\" onEnter=\"alert('Hi')\">\n\t\t\t\n\t \t\t\t</form>\n\t \t\t\t</td>\n\t\t\t\t");
/*  287 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  288 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  292 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  293 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */         }
/*      */         
/*  296 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  297 */         out.write("\n\t \t\t</tr>\n\t\t\t<tr><td colspan='3' height='5'></td></tr>\n\t\t\t");
/*      */       }
/*      */       
/*      */ 
/*  301 */       out.write("\n\n\t \t</table>\n\t</td>\n</tr>\n\n<tr>\n<td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\n<td class=\"AlarmInnerBoxBg\" valign=\"top\" width=\"100%\">\n\n<table cellpadding=\"6\" cellspacing=\"0\" border='0' width=\"100%\" class=\"lrtborder\">\n\t\t<tr>\n\t\t\t   <td align=\"left\" class=\"columnheadingnotop\" colspan=\"2\" width=\"60%\"> ");
/*  302 */       out.print(FormatUtil.getString("webclient.fault.details.properties.source"));
/*  303 */       out.write(" </td>\n\t\t\t   <td align=\"left\" class=\"columnheadingnotop\" width=\"20%\"> ");
/*  304 */       out.print(FormatUtil.getString("am.webclient.common.type.text"));
/*  305 */       out.write("</td>\n\t\t\t   ");
/*  306 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()) || (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())) {
/*  307 */         out.write("\n\t\t\t   <td align=\"left\" class=\"columnheadingnotop\" width=\"20%\"> ");
/*  308 */         out.print(FormatUtil.getString("Managed Servers"));
/*  309 */         out.write("</td>\n\t\t\t   ");
/*      */       } else {
/*  311 */         out.write("\n\t\t\t   <td align=\"left\" class=\"columnheadingnotop\" width=\"20%\">&nbsp;</td>\n\t\t\t   ");
/*      */       }
/*  313 */       out.write("\n\t\t</tr>\n</table>\n<form action=\"/manageApplications.do\" name=\"dependent\">\n\t<input type=\"hidden\" name=\"haid\" value=\"");
/*  314 */       if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*      */         return;
/*  316 */       out.write("\"/>\n\t<input type=\"hidden\" name=\"method\" value=\"addDependentMonitor\"/>\n\t<input type='hidden' name=\"dependentMonitor\" value=\"-1\"/>\n\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" border='0' width=\"100%\">\n\n<tr>\n\n<td colspan=\"4\" valign=\"top\" >\n\t<div id='defaultMonitors' style=\"DISPLAY: block;\">\n\t\t<table class=\"lrbborder\" style=\"background-color:#FFF;\" width=\"100%\" border=\"0\"  cellpadding=\"5\" cellspacing=\"0\">\n\t\t");
/*      */       
/*  318 */       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  319 */       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  320 */       _jspx_th_c_005fchoose_005f0.setParent(null);
/*  321 */       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  322 */       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */         for (;;) {
/*  324 */           out.write(10);
/*  325 */           out.write(9);
/*  326 */           out.write(9);
/*      */           
/*  328 */           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  329 */           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  330 */           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  332 */           _jspx_th_c_005fwhen_005f0.setTest("${!empty allMonitors}");
/*  333 */           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  334 */           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */             for (;;) {
/*  336 */               out.write("\n\t\t\t");
/*      */               
/*  338 */               IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.get(IterateTag.class);
/*  339 */               _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/*  340 */               _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */               
/*  342 */               _jspx_th_logic_005fiterate_005f2.setName("allMonitors");
/*      */               
/*  344 */               _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */               
/*  346 */               _jspx_th_logic_005fiterate_005f2.setType("java.util.HashMap");
/*  347 */               int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/*  348 */               if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/*  349 */                 HashMap row = null;
/*  350 */                 if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/*  351 */                   out = _jspx_page_context.pushBody();
/*  352 */                   _jspx_th_logic_005fiterate_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  353 */                   _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                 }
/*  355 */                 row = (HashMap)_jspx_page_context.findAttribute("row");
/*      */                 for (;;) {
/*  357 */                   out.write("\n\t\t\t");
/*      */                   
/*  359 */                   String resourceId = (String)row.get("resourceid");
/*  360 */                   String name = (String)row.get("displayname");
/*      */                   
/*  362 */                   out.write("\n\t\t\t<tr>\n\t\t\t       <td width=\"60%\" align=\"left\" colspan=\"2\" class=\"whitegrayborder\" valign=\"center\">\n\t\t\t\t       ");
/*  363 */                   if (_jspx_meth_c_005fset_005f0(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*      */                     return;
/*  365 */                   out.write("\n\t\t\t\t       ");
/*      */                   
/*  367 */                   MatchTag _jspx_th_logic_005fmatch_005f0 = (MatchTag)this._005fjspx_005ftagPool_005flogic_005fmatch_0026_005fvalue_005fname.get(MatchTag.class);
/*  368 */                   _jspx_th_logic_005fmatch_005f0.setPageContext(_jspx_page_context);
/*  369 */                   _jspx_th_logic_005fmatch_005f0.setParent(_jspx_th_logic_005fiterate_005f2);
/*      */                   
/*  371 */                   _jspx_th_logic_005fmatch_005f0.setName("selectedDependentMonitorList");
/*      */                   
/*  373 */                   _jspx_th_logic_005fmatch_005f0.setValue(resourceId);
/*  374 */                   int _jspx_eval_logic_005fmatch_005f0 = _jspx_th_logic_005fmatch_005f0.doStartTag();
/*  375 */                   if (_jspx_eval_logic_005fmatch_005f0 != 0) {
/*      */                     for (;;) {
/*  377 */                       out.write("\n\t\t\t\t       ");
/*  378 */                       if (_jspx_meth_c_005fset_005f1(_jspx_th_logic_005fmatch_005f0, _jspx_page_context))
/*      */                         return;
/*  380 */                       out.write("\n\t\t\t\t       ");
/*  381 */                       int evalDoAfterBody = _jspx_th_logic_005fmatch_005f0.doAfterBody();
/*  382 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  386 */                   if (_jspx_th_logic_005fmatch_005f0.doEndTag() == 5) {
/*  387 */                     this._005fjspx_005ftagPool_005flogic_005fmatch_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fmatch_005f0); return;
/*      */                   }
/*      */                   
/*  390 */                   this._005fjspx_005ftagPool_005flogic_005fmatch_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fmatch_005f0);
/*  391 */                   out.write("\n\t\t\t\t       ");
/*      */                   
/*  393 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  394 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  395 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_logic_005fiterate_005f2);
/*  396 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  397 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/*  399 */                       out.write("\n\t\t\t\t       ");
/*      */                       
/*  401 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  402 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  403 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/*  405 */                       _jspx_th_c_005fwhen_005f1.setTest("${isMonitorSelected == true}");
/*  406 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  407 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/*  409 */                           out.write("\n\t\t\t\t       <input type=\"checkbox\" id=\"resourceid\" name=\"resourceid\"  value=\"");
/*  410 */                           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                             return;
/*  412 */                           out.write("\" >&nbsp&nbsp&nbsp;");
/*  413 */                           out.print(FormatUtil.getTrimmedText(name, 50));
/*  414 */                           out.write(32);
/*  415 */                           out.write("\n\t\t\t\t       ");
/*  416 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  417 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  421 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  422 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/*  425 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  426 */                       out.write("\n\t\t\t\t       ");
/*      */                       
/*  428 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  429 */                       _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  430 */                       _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f1);
/*  431 */                       int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  432 */                       if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                         for (;;) {
/*  434 */                           out.write("\n\t\t\t\t       <input type=\"checkbox\" id=\"resourceid\" name=\"resourceid\"  value=\"");
/*  435 */                           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                             return;
/*  437 */                           out.write("\">&nbsp&nbsp&nbsp;");
/*  438 */                           out.print(FormatUtil.getTrimmedText(name, 50));
/*  439 */                           out.write(32);
/*  440 */                           out.write("\n\t\t\t\t       ");
/*  441 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  442 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  446 */                       if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  447 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                       }
/*      */                       
/*  450 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  451 */                       out.write("\n\t\t\t\t       ");
/*  452 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  453 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  457 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  458 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/*  461 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  462 */                   out.write("\n\t\t\t       </td>\n\t\t\t       <td width=\"20%\" class=\"whitegrayborder\"><img style=\"position:relative; top:5px; right:4px;\" src=\"");
/*  463 */                   if (_jspx_meth_c_005fout_005f6(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*      */                     return;
/*  465 */                   out.write(34);
/*  466 */                   out.write(62);
/*  467 */                   if (_jspx_meth_c_005fout_005f7(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*      */                     return;
/*  469 */                   out.write("</td >\n\t\t\t\t<td align=\"left\" class=\"whitegrayborder\" width=\"20%\">\n\t\t\t\t");
/*  470 */                   if (_jspx_meth_c_005fchoose_005f2(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*      */                     return;
/*  472 */                   out.write("\n\t\t\t\t<input type=\"hidden\" id=\"resourceTypeName\" name=\"resourceTypeName\" value=\"");
/*  473 */                   if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*      */                     return;
/*  475 */                   out.write("\"/>\n\t\t\t\t<input type=\"hidden\" id=\"resourceType\" name=\"resourceType\" value=\"");
/*  476 */                   if (_jspx_meth_c_005fout_005f11(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*      */                     return;
/*  478 */                   out.write("\"/>\n\t\t\t\t<input type=\"hidden\" id=\"imagePath\" name=\"imagePath\" value=\"");
/*  479 */                   if (_jspx_meth_c_005fout_005f12(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*      */                     return;
/*  481 */                   out.write("\"/>\n\t\t\t\t<input type='hidden' id=\"resourceName\" name=\"resourceName\" value=\"");
/*  482 */                   if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*      */                     return;
/*  484 */                   out.write("\">\n\t\t\t\t</td>\n\n\t\t\t</tr>\n\t\t\t");
/*  485 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/*  486 */                   row = (HashMap)_jspx_page_context.findAttribute("row");
/*  487 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*  490 */                 if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/*  491 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/*  494 */               if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/*  495 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */               }
/*      */               
/*  498 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/*  499 */               out.write("\n\t\t\t");
/*  500 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  501 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  505 */           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  506 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */           }
/*      */           
/*  509 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  510 */           out.write("\n\t\t\t");
/*      */           
/*  512 */           OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  513 */           _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  514 */           _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*  515 */           int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  516 */           if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */             for (;;) {
/*  518 */               out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"whitegrayborder\" colspan=3 align=\"center\"> ");
/*  519 */               out.print(FormatUtil.getString("am.webclient.nodata.text"));
/*  520 */               out.write(" </td>\n\t\t\t\t</tr>\n\t\t\t");
/*  521 */               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  522 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  526 */           if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  527 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */           }
/*      */           
/*  530 */           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  531 */           out.write("\n\t\t\t");
/*  532 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  533 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  537 */       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  538 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */       }
/*      */       else {
/*  541 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  542 */         out.write("\n\t\t</table>\n\t\t</div>\n\t\t<div id='filteredMonitors' style=\"display:none;\">&nbsp;</div>\n</td>\n</tr>\n<tr><td colspan=\"4\" style=\"height:10px;\"></td></tr>\n\n\t\t<tr><td colspan=\"4\"  align=\"center\">\n\t\t<input type=\"hidden\" name=\"monitorSelected\" id=\"monitorSelected\" value=\"");
/*  543 */         if (_jspx_meth_c_005fout_005f14(_jspx_page_context))
/*      */           return;
/*  545 */         out.write("\"/>\n\t\t\t\t\t\t<input type=\"button\" align=\"center\" class=\"buttons btn_highlt\" value=\"");
/*  546 */         out.print(FormatUtil.getString("Add"));
/*  547 */         out.write("\" onclick=\"checkAndSubmit()\">&nbsp;\n\t\t\t\t<input type=\"button\" align=\"center\" class=\"buttons btn_link\" value=\"");
/*  548 */         out.print(FormatUtil.getString("am.webclient.admintab.servicedesk.cancel"));
/*  549 */         out.write("\" onclick=\"javascript:window.close()\">\n\t\t</td></tr>\n</table>\n</form>\n</td>\n<td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n</tr>\n</table></td>\n\n</tr>\n</table>\n</td>\n</tr>\n</table>\n</td>\n<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n</tr>\n\n<tr>\n<td class=\"AlarmCardMainBtmLeft\"/>\n\n<td class=\"AlarmCardMainBtmBg\"/>\n<td class=\"AlarmCardMainBtmRight\"/>\n</tr>\n</table>\n\n\n");
/*      */         
/*  551 */         IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  552 */         _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  553 */         _jspx_th_c_005fif_005f2.setParent(null);
/*      */         
/*  555 */         _jspx_th_c_005fif_005f2.setTest("${isAdminServer || isManagedServer}");
/*  556 */         int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  557 */         if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */           for (;;) {
/*  559 */             out.write("\n<div style=\"height:10px;\"></div>\n \t");
/*      */             
/*  561 */             PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  562 */             _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  563 */             _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f2);
/*      */             
/*  565 */             _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN,OPERATOR");
/*  566 */             int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  567 */             if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */               for (;;) {
/*  569 */                 out.write("\n\n     <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t<tr>\n\t\t <td class=\"helpCardHdrTopLeft\"/>\n\t\t <td class=\"helpCardHdrTopBg\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t<tr>\n\t\t <td width=\"100\" valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/*  570 */                 out.print(FormatUtil.getString("Help Card"));
/*  571 */                 out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td> ");
/*  572 */                 out.write("\n\t\t <td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t <td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t</tr>\n\t\t</table></td>\n\t\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\t\t </tr>\n\t\t <tr>\n\t\t <td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t <td valign=\"top\">\n\t\t<!--//include your Helpcard template table here..-->\n\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\t    <tr>\n\t    <td style=\"padding-top: 10px;\" class=\"boxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t      <tr>\n\t        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t        <tr>\n\t          <td class=\"hCardInnerTopLeft\"/>\n\t          <td class=\"hCardInnerTopBg\"/>\n\t          <td class=\"hCardInnerTopRight\"/>\n\t        </tr>\n\t        <tr>\n\t          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n\t            <td class=\"hCardInnerBoxBg product-help\">\n\t            <span class=\"bodytext\">\n\t\t\t\t<table>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"bodytext\">");
/*  573 */                 out.print(FormatUtil.getString("am.webclient.dependentDevice.helpcard.text"));
/*  574 */                 out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t  </span>\n\t            </td>\n\t\t\t\t  <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t </table></td>\n\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t  </tr>\n\t\t\t </table>\n\t\t   </td> <td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t <tr>\n\t\t\t\t<td class=\"helpCardMainBtmLeft\"/>\n\t\t\t\t<td class=\"helpCardMainBtmBg\"/>\n\t\t\t\t<td class=\"helpCardMainBtmRight\"/>\n\t\t\t </tr>\n\t\t\t</table>\n \t\t");
/*  575 */                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  576 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  580 */             if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  581 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */             }
/*      */             
/*  584 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  585 */             out.write(10);
/*  586 */             int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  587 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  591 */         if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  592 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */         }
/*      */         else {
/*  595 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  596 */           out.write(10);
/*      */         }
/*  598 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  599 */         out = _jspx_out;
/*  600 */         if ((out != null) && (out.getBufferSize() != 0))
/*  601 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  602 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  605 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  611 */     PageContext pageContext = _jspx_page_context;
/*  612 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  614 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  615 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  616 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  618 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  620 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  621 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  622 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  623 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  624 */       return true;
/*      */     }
/*  626 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  627 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  632 */     PageContext pageContext = _jspx_page_context;
/*  633 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  635 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  636 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/*  637 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/*  639 */     _jspx_th_c_005fout_005f1.setValue("${haid}");
/*  640 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/*  641 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/*  642 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  643 */       return true;
/*      */     }
/*  645 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  646 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  651 */     PageContext pageContext = _jspx_page_context;
/*  652 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  654 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  655 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/*  656 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/*  658 */     _jspx_th_c_005fout_005f2.setValue("${haid}");
/*  659 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/*  660 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/*  661 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  662 */       return true;
/*      */     }
/*  664 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  665 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  670 */     PageContext pageContext = _jspx_page_context;
/*  671 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  673 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  674 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/*  675 */     _jspx_th_c_005fout_005f3.setParent(null);
/*      */     
/*  677 */     _jspx_th_c_005fout_005f3.setValue("${haid}");
/*  678 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/*  679 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/*  680 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  681 */       return true;
/*      */     }
/*  683 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  684 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  689 */     PageContext pageContext = _jspx_page_context;
/*  690 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  692 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/*  693 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  694 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/*      */     
/*  696 */     _jspx_th_c_005fset_005f0.setVar("isMonitorSelected");
/*      */     
/*  698 */     _jspx_th_c_005fset_005f0.setValue("false");
/*  699 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  700 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  701 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/*  702 */       return true;
/*      */     }
/*  704 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/*  705 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_logic_005fmatch_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  710 */     PageContext pageContext = _jspx_page_context;
/*  711 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  713 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/*  714 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  715 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_logic_005fmatch_005f0);
/*      */     
/*  717 */     _jspx_th_c_005fset_005f1.setVar("isMonitorSelected");
/*      */     
/*  719 */     _jspx_th_c_005fset_005f1.setValue("true");
/*  720 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  721 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  722 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/*  723 */       return true;
/*      */     }
/*  725 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/*  726 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  731 */     PageContext pageContext = _jspx_page_context;
/*  732 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  734 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  735 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/*  736 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/*  738 */     _jspx_th_c_005fout_005f4.setValue("${row.resourceid}");
/*  739 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/*  740 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/*  741 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/*  742 */       return true;
/*      */     }
/*  744 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/*  745 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  750 */     PageContext pageContext = _jspx_page_context;
/*  751 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  753 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  754 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/*  755 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/*  757 */     _jspx_th_c_005fout_005f5.setValue("${row.resourceid}");
/*  758 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/*  759 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/*  760 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/*  761 */       return true;
/*      */     }
/*  763 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/*  764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  769 */     PageContext pageContext = _jspx_page_context;
/*  770 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  772 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  773 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/*  774 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/*      */     
/*  776 */     _jspx_th_c_005fout_005f6.setValue("${row.imagepath}");
/*  777 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/*  778 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/*  779 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/*  780 */       return true;
/*      */     }
/*  782 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/*  783 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  788 */     PageContext pageContext = _jspx_page_context;
/*  789 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  791 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  792 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/*  793 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/*      */     
/*  795 */     _jspx_th_c_005fout_005f7.setValue("${row.typeName}");
/*  796 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/*  797 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/*  798 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/*  799 */       return true;
/*      */     }
/*  801 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/*  802 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  807 */     PageContext pageContext = _jspx_page_context;
/*  808 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  810 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  811 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  812 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/*  813 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  814 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/*  816 */         out.write("\n\t\t\t\t");
/*  817 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/*  818 */           return true;
/*  819 */         out.write("\n\t\t\t\t");
/*  820 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/*  821 */           return true;
/*  822 */         out.write("\n\t\t\t\t");
/*  823 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  824 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  828 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  829 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  830 */       return true;
/*      */     }
/*  832 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  833 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  838 */     PageContext pageContext = _jspx_page_context;
/*  839 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  841 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  842 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  843 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/*  845 */     _jspx_th_c_005fwhen_005f2.setTest("${isAdminServer || isManagedServer}");
/*  846 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  847 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/*  849 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*  850 */           return true;
/*  851 */         out.write("\n\t\t\t   \t<input type='hidden' id=\"mas\" name=\"mas\" value=\"");
/*  852 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*  853 */           return true;
/*  854 */         out.write("\">\n\t\t\t\t");
/*  855 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  856 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  860 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  861 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  862 */       return true;
/*      */     }
/*  864 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  865 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  870 */     PageContext pageContext = _jspx_page_context;
/*  871 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  873 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  874 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/*  875 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/*  877 */     _jspx_th_c_005fout_005f8.setValue("${row.managedServer}");
/*  878 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/*  879 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/*  880 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/*  881 */       return true;
/*      */     }
/*  883 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/*  884 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  889 */     PageContext pageContext = _jspx_page_context;
/*  890 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  892 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  893 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/*  894 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/*  896 */     _jspx_th_c_005fout_005f9.setValue("${row.managedServer}");
/*  897 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/*  898 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/*  899 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/*  900 */       return true;
/*      */     }
/*  902 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/*  903 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  908 */     PageContext pageContext = _jspx_page_context;
/*  909 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  911 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  912 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  913 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*  914 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  915 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/*  917 */         out.write("\n\t\t\t\t&nbsp;\n\t\t\t\t");
/*  918 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  919 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  923 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  924 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  925 */       return true;
/*      */     }
/*  927 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  933 */     PageContext pageContext = _jspx_page_context;
/*  934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  936 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  937 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/*  938 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/*      */     
/*  940 */     _jspx_th_c_005fout_005f10.setValue("${row.typeName}");
/*  941 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/*  942 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/*  943 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/*  944 */       return true;
/*      */     }
/*  946 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/*  947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  952 */     PageContext pageContext = _jspx_page_context;
/*  953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  955 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  956 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/*  957 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/*      */     
/*  959 */     _jspx_th_c_005fout_005f11.setValue("${row.type}");
/*  960 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/*  961 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/*  962 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/*  963 */       return true;
/*      */     }
/*  965 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/*  966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  971 */     PageContext pageContext = _jspx_page_context;
/*  972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  974 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  975 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/*  976 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/*      */     
/*  978 */     _jspx_th_c_005fout_005f12.setValue("${row.imagepath}");
/*  979 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/*  980 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/*  981 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/*  982 */       return true;
/*      */     }
/*  984 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/*  985 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  990 */     PageContext pageContext = _jspx_page_context;
/*  991 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  993 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  994 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/*  995 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/*      */     
/*  997 */     _jspx_th_c_005fout_005f13.setValue("${row.displayname}");
/*  998 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/*  999 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 1000 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 1001 */       return true;
/*      */     }
/* 1003 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 1004 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1009 */     PageContext pageContext = _jspx_page_context;
/* 1010 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1012 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1013 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 1014 */     _jspx_th_c_005fout_005f14.setParent(null);
/*      */     
/* 1016 */     _jspx_th_c_005fout_005f14.setValue("${selectedMonitors}");
/* 1017 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 1018 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 1019 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 1020 */       return true;
/*      */     }
/* 1022 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 1023 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\SelectDependentMonitor_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */