/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.fault.DependentDeviceUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.IterateTag;
/*     */ import org.apache.struts.taglib.logic.NotPresentTag;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ import org.json.JSONArray;
/*     */ 
/*     */ public final class DependentDeviceListView_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  26 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  44 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  57 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  61 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  62 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  63 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  64 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  65 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  66 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  67 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.release();
/*  68 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  75 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  78 */     JspWriter out = null;
/*  79 */     Object page = this;
/*  80 */     JspWriter _jspx_out = null;
/*  81 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  85 */       response.setContentType("text/html");
/*  86 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  88 */       _jspx_page_context = pageContext;
/*  89 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  90 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  91 */       session = pageContext.getSession();
/*  92 */       out = pageContext.getOut();
/*  93 */       _jspx_out = out;
/*     */       
/*  95 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/*     */       
/*  97 */       String resourceId = request.getParameter("resourceid");
/*  98 */       ArrayList<HashMap<String, String>> dependentMonitorList = DependentDeviceUtil.getInstance().getDependentMonitorInfo(resourceId);
/*  99 */       boolean supressAlerts = false;
/* 100 */       String tabToSelect = "am.webclient.rule.action";
/* 101 */       String depDeviceValue = "";
/* 102 */       int size = dependentMonitorList.size();
/* 103 */       if ((dependentMonitorList != null) && (size > 0))
/*     */       {
/* 105 */         HashMap<String, String> dependentMonitorInfo = (HashMap)dependentMonitorList.get(0);
/* 106 */         supressAlerts = Boolean.parseBoolean((String)dependentMonitorInfo.get("suppressAlerts"));
/*     */         
/* 108 */         depDeviceValue = DependentDeviceUtil.getInstance().getJSONStringForDependentMonitorList(dependentMonitorList);
/* 109 */         JSONArray arr = new JSONArray();
/* 110 */         for (int i = 0; i < size; i++)
/*     */         {
/* 112 */           HashMap map = (HashMap)dependentMonitorList.get(i);
/* 113 */           arr.put((String)map.get("resourceid"));
/*     */         }
/* 115 */         if (arr.length() > 0)
/*     */         {
/* 117 */           request.setAttribute("selectedMonitors", arr.toString());
/*     */         }
/*     */       }
/* 120 */       request.setAttribute("depDeviceValue", depDeviceValue);
/* 121 */       request.setAttribute("DependentDeviceList", dependentMonitorList);
/* 122 */       request.setAttribute("suppressAlertSelected", Boolean.valueOf(supressAlerts));
/* 123 */       request.setAttribute("isAdminServer", Boolean.valueOf(com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()));
/* 124 */       request.setAttribute("isManagedServer", Boolean.valueOf(com.adventnet.appmanager.util.EnterpriseUtil.isManagedServer()));
/* 125 */       boolean isMonitorGroup = com.adventnet.appmanager.util.ReportDataUtilities.isHAI(resourceId);
/* 126 */       request.setAttribute("isMonitorGroup", Boolean.valueOf(isMonitorGroup));
/*     */       
/* 128 */       out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/json2.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n\n<SCRIPT>\nfunction deleteDependentMonitor(parentId,childId, imageNodeObj)\n{\n\tvar s = confirm('");
/* 129 */       out.print(FormatUtil.getString("am.webclient.common.confirm.delete.text"));
/* 130 */       out.write("');\n\tif(!s)\n\t{\n\t\treturn;\n\t}\n\tvar responseObj = $.ajax({  //No I18N\n\t\tasync: false, //No I18N\n\t\tcache: false, //No I18N\n\t\ttype: \"GET\", //No I18N\n\t\turl: \"/showresource.do\", //No I18N\n\t\tdata: {method:\"deleteDependentMonitor\",parentId:parentId,childId:childId} //No I18N\n\t});\n\tvar response = eval(\"(\"+ responseObj.responseText +\")\");\n\tif(response.result)\n\t{\n\t\tvar rowIndex = imageNodeObj.parentNode.parentNode.rowIndex;\n\t\tvar tableObj = document.getElementById(\"DependentMonitorTableID\");\n\t\ttableObj.deleteRow(rowIndex);\n\t\tvar tbody = tableObj.getElementsByTagName(\"tbody\")[0];\n\t\tvar rowLen = tbody.rows.length;\n\t\tvar depDeviceList = JSON.parse(document.getElementById(\"depDeviceId\").value);\n\t\tfor(tempObj in depDeviceList)\n\t\t{\n\t\t\tvar i = tempObj;\n\t\t\tif(depDeviceList[i].resourceid == parentId)\n\t\t\t{\n\t\t\t\tdelete depDeviceList[i];\n\t\t\t}\n\t\t}\n\t\tvar selectedMonitors = JSON.parse(document.getElementById('selectedMonitors').value);\n\t\tvar selectedMonitorLen = selectedMonitors.length;\n\t\tfor(var i=0;selectedMonitorLen;i++)\n\t\t{\n\t\t\tif(selectedMonitors[i] == parentId)\n");
/* 131 */       out.write("\t\t\t{\n\t\t\t\tselectedMonitors.splice(i, 1);\n\t\t\t\tbreak;\n\t\t\t}\n\t\t}\n\t\tdocument.getElementById('depDeviceId').value = JSON.stringify(depDeviceList);\n\t\tdocument.getElementById('selectedMonitors').value = JSON.stringify(selectedMonitors);\n\t\tif(rowLen == 1)\n\t\t{\n\t\t\tdocument.getElementById(\"resourceEmptyTableID\").style.display = \"block\";\n\t\t\tdocument.getElementById(\"allResourceTableID\").style.display = \"none\";\n\t\t}\n\t}\n\telse\n\t{\n\t\talert(response.message); //I18N\n\t}\n}\nfunction createDynamicRows(tableName, depDeviceList)\n{\n\tvar tableObj = document.getElementById(tableName);\n\tvar tbody = tableObj.getElementsByTagName(\"tbody\")[0];\n\tvar rowLen = tbody.rows.length;\n\tif(rowLen == 2 && (tbody.getElementsByTagName('TR')[1].id == 'ZeroMonitorTR'))\n\t{\n\t\t\ttbody.deleteRow(1);\n\t}\n\telse\n\t{\n\t\t//for(j=rowLen-1;j>0;j--)\n\t\t//{\n\t\t//\ttbody.deleteRow(j);\n\t\t//}\n\t}\n\tfor(tempObj in depDeviceList)\n\t{\n\t\tvar i = tempObj;\n\t\tvar row = document.createElement('TR');\n\t\trow.setAttribute(\"class\", \"whitegrayborder\");\n\t\trow.className = \"whitegrayborder\";\n\t\tvar monitorNameCell = document.createElement('TD');\n");
/* 132 */       out.write("\t\tmonitorNameCell.setAttribute(\"class\", \"yellowgrayborder\");\n\t\tmonitorNameCell.className = \"yellowgrayborder\";\n\t\tmonitorNameCell.innerHTML = depDeviceList[i].displayname;\n\t\trow.appendChild(monitorNameCell);\n\n\t\tvar imageTypeCell = document.createElement('IMG');\n\t\timageTypeCell.setAttribute(\"src\", \"..\"+depDeviceList[i].imagepath);\n\t\timageTypeCell.src = \"..\"+depDeviceList[i].imagepath;\n\t\tvar spanTag = document.createElement('SPAN');\n\t\tspanTag.align = \"top\";//No I18N\n\t\tspanTag.innerHTML = \"&nbsp;\"+depDeviceList[i].typeName;\n\n\t\tvar typeNameCell = document.createElement('TD');\n\t\ttypeNameCell.setAttribute(\"class\", \"yellowgrayborder\");\n\t\ttypeNameCell.className = \"yellowgrayborder\";\n\t\ttypeNameCell.appendChild(imageTypeCell);\n\t\ttypeNameCell.appendChild(spanTag);\n\t\trow.appendChild(typeNameCell);\n\n\t\tvar managedServerNameCell = document.createElement('TD');\n\t\tmanagedServerNameCell.setAttribute(\"class\", \"yellowgrayborder\");\n\t\tmanagedServerNameCell.className = \"yellowgrayborder\";\n\t\tif(depDeviceList[i].managedServer != '')\n\t\t{\n");
/* 133 */       out.write("\t\t\tmanagedServerNameCell.innerHTML = depDeviceList[i].managedServer;\n\t\t\trow.appendChild(managedServerNameCell);\n\t\t}\n\n\t\tvar imageCell = document.createElement('TD');\n\t\timageCell.setAttribute(\"class\", \"yellowgrayborder\");\n\t\timageCell.className = \"yellowgrayborder\";\n\t\timageCell.setAttribute(\"align\", \"center\");\n\t\tvar deleteImageCell = document.createElement('IMG');\n\t\tdeleteImageCell.setAttribute(\"src\", \"../images/thrash.gif\");\n\t\tdeleteImageCell.setAttribute(\"border\", \"0\");\n\t\tdeleteImageCell.setAttribute(\"style\", \"cursor:pointer\");\n\t\tdeleteImageCell.style.cursor = \"pointer\";\n\t\tvar url = \"deleteDependentMonitorInCache(this,'\"+i+\"')\";//No I18N\n\t\tdeleteImageCell.setAttribute(\"onclick\", url);\n\t\tdeleteImageCell.onclick = new Function(url);\n\t\timageCell.appendChild(deleteImageCell);\n\t\trow.appendChild(imageCell);\n\t\ttbody.appendChild(row);\n\n\t}\n\tdocument.getElementById(\"allResourceTableID\").style.display = \"block\";\n\tdocument.getElementById(\"resourceEmptyTableID\").style.display = \"none\";\n}\nfunction deleteDependentMonitorInCache(imageNodeObj, objectId)\n");
/* 134 */       out.write("{\n\tvar s = confirm('");
/* 135 */       out.print(FormatUtil.getString("am.webclient.common.confirm.delete.text"));
/* 136 */       out.write("');\n\tif(!s)\n\t{\n\t\treturn;\n\t}\n\tvar depDeviceList = JSON.parse(document.getElementById('depDeviceId').value);\n\tvar selectedMonitors = JSON.parse(document.getElementById('selectedMonitors').value);\n\tvar resourceid = depDeviceList[objectId].resourceid;\n\tvar selectedMonitorLen = selectedMonitors.length;\n\tfor(var i=0;selectedMonitorLen;i++)\n\t{\n\t\tif(selectedMonitors[i] == resourceid)\n\t\t{\n\t\t\tselectedMonitors.splice(i, 1);\n\t\t\tdelete depDeviceList[objectId];\n\t\t\tbreak;\n\t\t}\n\t}\n\tvar rowIndex = imageNodeObj.parentNode.parentNode.rowIndex;\n\tvar tableObj = document.getElementById(\"DependentMonitorTableID\");\n\ttableObj.deleteRow(rowIndex);\n\tvar tbody = tableObj.getElementsByTagName(\"tbody\")[0];\n\tvar rowLen = tbody.rows.length;\n\tif(rowLen == 1)\n\t{\n\t\tdocument.getElementById(\"resourceEmptyTableID\").style.display = \"block\";\n\t\tdocument.getElementById(\"allResourceTableID\").style.display = \"none\";\n\t}\n\tdocument.getElementById('depDeviceId').value = JSON.stringify(depDeviceList);\n\tdocument.getElementById('selectedMonitors').value = JSON.stringify(selectedMonitors);\n");
/* 137 */       out.write("}\n</SCRIPT>\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" id=\"DependentDeviceTable\" align=\"center\" style=\"display:none;margin-top:10px;\">\n\n\t<tr>\n\t\t<td width=\"100%\">\n\t\t\t<table cellspacing=\"0\" cellpadding=\"2\" border=\"0\" width=\"100%\"  id=\"resourceEmptyTableID\"\n\t\t\t");
/* 138 */       if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*     */         return;
/* 140 */       out.write("\n\t\t\t>\n\n\t\t\t<tbody>\n\t\t\t\t<tr>\n\t\t\t\t\t<td style=\"width:100%;\" class=\"bodytext\" align=\"center\">\n\n\n\n\t\t\t\t\t<table width=\"820\" cellspacing=\"0\" cellpadding=\"6\" border=\"0\" class=\"lrtbdarkborder\" align=\"left\">\n\n\t\t\t\t\t<tr>\n\n\t\t\t\t\t<td width=\"100%\" align=\"left\" class=\"tableheadingbborder\">");
/* 141 */       out.print(FormatUtil.getString("am.webclient.rule.depDevice"));
/* 142 */       out.write("</td>\n\n\t\t\t\t\t</tr>\n<tr><td style=\"height:5px;\"></td></tr>\n\t\t\t\t\t<tr>\n\n\t\t\t\t\t<td class=\"bodytext\" align=\"center\"> ");
/* 143 */       out.print(FormatUtil.getString("am.webclient.noDependentDevice.text"));
/* 144 */       out.write(".&nbsp;&nbsp;\n\t\t\t\t\t");
/*     */       
/* 146 */       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 147 */       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 148 */       _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */       
/* 150 */       _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 151 */       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 152 */       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */         for (;;) {
/* 154 */           out.write("\n\t\t\t\t\t<a class=\"staticlinks\" href=\"javascript:void(0)\" onclick=\"alertUser()\">");
/* 155 */           out.print(FormatUtil.getString("am.webclient.rule.selectDptMO"));
/* 156 */           out.write("\n\t\t\t\t\t");
/* 157 */           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 158 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 162 */       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 163 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*     */       }
/*     */       else {
/* 166 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 167 */         out.write("\n\t\t\t\t\t");
/*     */         
/* 169 */         NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 170 */         _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 171 */         _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */         
/* 173 */         _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 174 */         int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 175 */         if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */           for (;;) {
/* 177 */             out.write("\n\t\t\t\t\t<a class=\"staticlinks\" href=\"javascript:void(0)\" onclick=\"getPopUp()\">");
/* 178 */             out.print(FormatUtil.getString("am.webclient.rule.selectDptMO"));
/* 179 */             out.write("\n\t\t\t\t\t");
/* 180 */             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 181 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 185 */         if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 186 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*     */         }
/*     */         else {
/* 189 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 190 */           out.write("\n\t\t\t\t\t</a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr><td style=\"height:5px;\"></td></tr>\n</table>\n\n\n\n\t\t\t\t</tr>\n\n\t\t\t</tbody>\n\t\t\t</table>\n\n\t\t\t<table cellspacing=\"0\" cellpadding=\"4\" border=\"0\" align=\"center\" width=\"100%\" id=\"allResourceTableID\"\n\t\t\t");
/* 191 */           if (_jspx_meth_c_005fchoose_005f1(_jspx_page_context))
/*     */             return;
/* 193 */           out.write("\n\t\t\t>\n\t\t\t<tbody>\n\t\t\t\t<tr>\n\t\t\t\t\t<td  valign=\"top\" style=\"width:820px; \">\n\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n\t\t\t\t\t<tbody>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"100%\" valign=\"top\" >\n\t\t\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"7\" border=\"0\" width=\"100%\" style=\"background-color: rgb(255, 255, 255);\" class=\"lrtbdarkborder\" id=\"DependentMonitorTableID\">\n\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t<tr class=\"yellowgrayborder\">\n\t\t\t\t\t\t\t\t\t<td height=\"28\" valign=\"center\" class=\"columnheadingnotop\"><span style=\"font-weight: bold; color: rgb(0, 0, 0);\" class=\"bodytext\">");
/* 194 */           out.print(FormatUtil.getString("am.webclient.dependentmonitorname.text"));
/* 195 */           out.write("</span></td>\n\t\t\t\t\t\t\t\t\t<td height=\"28\" class=\"columnheadingnotop\"><span style=\"font-weight: bold; color: rgb(0, 0, 0);\" class=\"bodytext\">");
/* 196 */           out.print(FormatUtil.getString("Type"));
/* 197 */           out.write("</span></td>\n\t\t\t\t\t\t\t\t\t");
/*     */           
/* 199 */           IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 200 */           _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 201 */           _jspx_th_c_005fif_005f0.setParent(null);
/*     */           
/* 203 */           _jspx_th_c_005fif_005f0.setTest("${isAdminServer || isManagedServer}");
/* 204 */           int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 205 */           if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */             for (;;) {
/* 207 */               out.write("\n\t\t\t\t\t\t\t\t\t<td align=\"left\" height=\"28\" class=\"columnheadingnotop\"><span style=\"font-weight: bold; color: rgb(0, 0, 0);\" class=\"bodytext\">");
/* 208 */               out.print(FormatUtil.getString("am.webclient.managedserver.text"));
/* 209 */               out.write("</span></td>\n\t\t\t\t\t\t\t\t\t");
/* 210 */               int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 211 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 215 */           if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 216 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */           }
/*     */           else {
/* 219 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 220 */             out.write("\n\t\t\t\t\t\t\t\t\t<td align=\"center\" height=\"28\" class=\"columnheadingnotop\"><span style=\"font-weight: bold; color: rgb(0, 0, 0);\" class=\"bodytext\">");
/* 221 */             out.print(FormatUtil.getString("am.webclient.alertviews.columnheading.delete.title"));
/* 222 */             out.write("</span></td>\n\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t\t");
/*     */             
/* 224 */             IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 225 */             _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 226 */             _jspx_th_c_005fif_005f1.setParent(null);
/*     */             
/* 228 */             _jspx_th_c_005fif_005f1.setTest("${!empty DependentDeviceList}");
/* 229 */             int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 230 */             if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */               for (;;) {
/* 232 */                 out.write("\n\t\t\t\t\t\t\t\t");
/*     */                 
/* 234 */                 IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.get(IterateTag.class);
/* 235 */                 _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 236 */                 _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_c_005fif_005f1);
/*     */                 
/* 238 */                 _jspx_th_logic_005fiterate_005f0.setName("DependentDeviceList");
/*     */                 
/* 240 */                 _jspx_th_logic_005fiterate_005f0.setId("row");
/* 241 */                 int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 242 */                 if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 243 */                   Object row = null;
/* 244 */                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 245 */                     out = _jspx_page_context.pushBody();
/* 246 */                     _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 247 */                     _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */                   }
/* 249 */                   row = _jspx_page_context.findAttribute("row");
/*     */                   for (;;) {
/* 251 */                     out.write("\n\t\t\t\t\t\t\t\t<tr class=\"whitegrayborder\">\n\t\t\t\t\t\t\t\t\t<td class=\"yellowgrayborder\" title=\"");
/* 252 */                     if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*     */                       return;
/* 254 */                     out.write(34);
/* 255 */                     out.write(62);
/* 256 */                     if (_jspx_meth_c_005fout_005f1(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*     */                       return;
/* 258 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t<td class=\"yellowgrayborder\"><img border=\"0\" align=\"middle\" hspace=\"5\" title=\"");
/* 259 */                     if (_jspx_meth_c_005fout_005f2(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*     */                       return;
/* 261 */                     out.write("\" src=\"");
/* 262 */                     if (_jspx_meth_c_005fout_005f3(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*     */                       return;
/* 264 */                     out.write("\"><span class=\"bodytext\">");
/* 265 */                     if (_jspx_meth_c_005fout_005f4(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*     */                       return;
/* 267 */                     out.write("</span></td>\n\t\t\t\t\t\t\t\t\t");
/* 268 */                     if (_jspx_meth_c_005fif_005f2(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*     */                       return;
/* 270 */                     out.write("\n\t\t\t\t\t\t\t\t\t<td align=\"center\" class=\"yellowgrayborder\"><img border=\"0\" name=\"Image14\" src=\"/images/thrash.gif\" style=\"cursor:pointer;\" onclick=\"deleteDependentMonitor('");
/* 271 */                     if (_jspx_meth_c_005fout_005f6(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*     */                       return;
/* 273 */                     out.write(39);
/* 274 */                     out.write(44);
/* 275 */                     out.write(39);
/* 276 */                     out.print(resourceId);
/* 277 */                     out.write("', this)\"></img></td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t");
/* 278 */                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 279 */                     row = _jspx_page_context.findAttribute("row");
/* 280 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/* 283 */                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 284 */                     out = _jspx_page_context.popBody();
/*     */                   }
/*     */                 }
/* 287 */                 if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 288 */                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*     */                 }
/*     */                 
/* 291 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 292 */                 out.write("\n\t\t\t\t\t\t\t\t");
/* 293 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 294 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 298 */             if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 299 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*     */             }
/*     */             else {
/* 302 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 303 */               out.write("\n\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\n\t\t\t\t\t</tr>\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"columnheadingnotop lrbborder\">\n\t\t\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td align=\"left\" height=\"26\" class=\"bodytext\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onclick=\"getPopUp()\">");
/* 304 */               out.print(FormatUtil.getString("am.webclient.rule.selectDptMO"));
/* 305 */               out.write("</a></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t\t<tr><td style=\"height:10px;\"></td></tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td align=\"left\" valign=\"top\" class=\"bodytext\" height=\"30px\" style=\"padding-left:5px;padding-top:5px;\">\n\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"suppressAlert\" value=\"1\" ");
/* 306 */               if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*     */                 return;
/* 308 */               out.write(62);
/* 309 */               out.print(FormatUtil.getString("am.webclient.rule.suppressAlerts"));
/* 310 */               out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"depDeviceId\" id=\"depDeviceId\" value=\"");
/* 311 */               if (_jspx_meth_c_005fout_005f7(_jspx_page_context))
/*     */                 return;
/* 313 */               out.write("\"/>\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"selectedMonitors\" id=\"selectedMonitors\" value=\"");
/* 314 */               if (_jspx_meth_c_005fout_005f8(_jspx_page_context))
/*     */                 return;
/* 316 */               out.write("\"/>\n\t\t\t\t\t\t\t");
/*     */               
/* 318 */               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 319 */               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 320 */               _jspx_th_c_005fchoose_005f2.setParent(null);
/* 321 */               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 322 */               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*     */                 for (;;) {
/* 324 */                   out.write("\n\t\t\t\t\t\t\t");
/*     */                   
/* 326 */                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 327 */                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 328 */                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*     */                   
/* 330 */                   _jspx_th_c_005fwhen_005f2.setTest("${isMonitorGroup}");
/* 331 */                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 332 */                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*     */                     for (;;) {
/* 334 */                       out.write("\n        \t\t\t\t\t\t\t<input type=\"button\" onclick=\"javascript:fnFormSubmit()\" value='");
/* 335 */                       out.print(FormatUtil.getString("am.webclient.rule.conf.saveall"));
/* 336 */                       out.write("' class=\"buttons btn_highlt\" name=\"button\" title=\"submits values in all 3 tabs\"/>&nbsp;\n        \t\t\t\t\t\t\t<input type=\"button\" onclick=\"javascript:backToScreen();\" value='");
/* 337 */                       out.print(FormatUtil.getString("webclient.common.printview.button.close"));
/* 338 */                       out.write("' class=\"buttons btn_link\" name=\"button3\"/>&nbsp;\n       \t \t\t\t\t\t\t\t<input type=\"button\" onclick=\"javascript:removeConfiguration();\" value='");
/* 339 */                       out.print(FormatUtil.getString("am.webclient.rule.conf.removeall"));
/* 340 */                       out.write("' class=\"buttons\" name=\"button4\"/>\n\t\t\t\t\t\t\t");
/* 341 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 342 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 346 */                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 347 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*     */                   }
/*     */                   
/* 350 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 351 */                   out.write("\n\t\t\t\t\t\t\t");
/*     */                   
/* 353 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 354 */                   _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 355 */                   _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 356 */                   int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 357 */                   if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*     */                     for (;;) {
/* 359 */                       out.write("\n        \t\t\t\t\t\t\t");
/*     */                       
/* 361 */                       ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 362 */                       _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 363 */                       _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fotherwise_005f2);
/* 364 */                       int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 365 */                       if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*     */                         for (;;) {
/* 367 */                           out.write("\n      \t\t\t\t\t\t\t\t\t");
/*     */                           
/* 369 */                           WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 370 */                           _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 371 */                           _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*     */                           
/* 373 */                           _jspx_th_c_005fwhen_005f3.setTest("${param.global!='true'}");
/* 374 */                           int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 375 */                           if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*     */                             for (;;) {
/* 377 */                               out.write("\n\n\t\t\t\t\t\t\t\t<input type=\"button\" onclick=\"javascript:fnFormSubmit(1)\" value='");
/* 378 */                               out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 379 */                               out.write("' class=\"buttons btn_highlt\" name=\"button\">\n        \t\t\t\t\t\t\t<input type=\"button\" onclick=\"javascript:fnFormSubmit(2)\" value='");
/* 380 */                               out.print(FormatUtil.getString("am.webclient.configurealert.saveandconfigureanother"));
/* 381 */                               out.write("' class=\"buttons\" name=\"button1\">\n        \t\t\t\t\t\t\t&nbsp; <input type=\"button\" onclick=\"javascript:backToScreen();\" value='");
/* 382 */                               out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 383 */                               out.write("' class=\"buttons btn_link\" name=\"button3\">\n\t\t   \t\t\t\t\t\t<input type=\"button\" onclick=\"javascript:fnFormSubmit(3)\" value='");
/* 384 */                               out.print(FormatUtil.getString("am.webclient.configurealert.removeconfiguration"));
/* 385 */                               out.write("' class=\"buttons\" name=\"button22\">\n\t\t\t\t\t\t\t\t\t");
/* 386 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 387 */                               if (evalDoAfterBody != 2)
/*     */                                 break;
/*     */                             }
/*     */                           }
/* 391 */                           if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 392 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*     */                           }
/*     */                           
/* 395 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 396 */                           out.write("\n\t\t\t\t\t\t\t\t\t");
/*     */                           
/* 398 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 399 */                           _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 400 */                           _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 401 */                           int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 402 */                           if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*     */                             for (;;) {
/* 404 */                               out.write("\n        \t\t\t\t\t\t\t\t\t<input type=\"button\" onclick=\"javascript:fnFormSubmit(2)\" value='");
/* 405 */                               out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 406 */                               out.write("' class=\"buttons btn_highlt\" name=\"button1\">\n        \t\t\t\t\t\t\t\t\t<input name=\"button3\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 407 */                               out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 408 */                               out.write("\" onClick=\"javascript:window.close();\">\n\t\t   \t\t\t\t\t\t\t\t<input type=\"button\" onclick=\"javascript:fnFormSubmit(3)\" value='");
/* 409 */                               out.print(FormatUtil.getString("am.webclient.configurealert.removeconfiguration"));
/* 410 */                               out.write("' class=\"buttons\" name=\"button22\">\n\t\t\t\t\t\t\t\t\t");
/* 411 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 412 */                               if (evalDoAfterBody != 2)
/*     */                                 break;
/*     */                             }
/*     */                           }
/* 416 */                           if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 417 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*     */                           }
/*     */                           
/* 420 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 421 */                           out.write("\n\t\t\t\t\t\t\t\t\t");
/* 422 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 423 */                           if (evalDoAfterBody != 2)
/*     */                             break;
/*     */                         }
/*     */                       }
/* 427 */                       if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 428 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*     */                       }
/*     */                       
/* 431 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 432 */                       out.write("\n\t\t\t\t\t\t\t");
/* 433 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 434 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 438 */                   if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 439 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*     */                   }
/*     */                   
/* 442 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 443 */                   out.write("\n\t\t\t\t\t\t\t");
/* 444 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 445 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 449 */               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 450 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*     */               }
/*     */               else {
/* 453 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 454 */                 out.write("\n      \t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t</tbody>\n\t\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t</tbody>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n\n<tr><td><img src=\"/images/spacer.gif\" style=\"width:100%; height:1px;\"/></td></tr>\n</table>\n\n\n\n\n");
/*     */               }
/* 456 */             } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 457 */         out = _jspx_out;
/* 458 */         if ((out != null) && (out.getBufferSize() != 0))
/* 459 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 460 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 463 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 469 */     PageContext pageContext = _jspx_page_context;
/* 470 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 472 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 473 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 474 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 475 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 476 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */       for (;;) {
/* 478 */         out.write("\n\t\t\t\t");
/* 479 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 480 */           return true;
/* 481 */         out.write("\n\t\t\t\t");
/* 482 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 483 */           return true;
/* 484 */         out.write("\n\t\t\t");
/* 485 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 486 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 490 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 491 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 492 */       return true;
/*     */     }
/* 494 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 495 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 500 */     PageContext pageContext = _jspx_page_context;
/* 501 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 503 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 504 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 505 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*     */     
/* 507 */     _jspx_th_c_005fwhen_005f0.setTest("${empty DependentDeviceList}");
/* 508 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 509 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */       for (;;) {
/* 511 */         out.write("\n\t\t\tstyle=\"display:block;\"\n\t\t\t\t");
/* 512 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 513 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 517 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 518 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 519 */       return true;
/*     */     }
/* 521 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 522 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 527 */     PageContext pageContext = _jspx_page_context;
/* 528 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 530 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 531 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 532 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 533 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 534 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */       for (;;) {
/* 536 */         out.write("\n\t\t\tstyle=\"display:none;\"\n\t\t\t\t");
/* 537 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 538 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 542 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 543 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 544 */       return true;
/*     */     }
/* 546 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 547 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fchoose_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 552 */     PageContext pageContext = _jspx_page_context;
/* 553 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 555 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 556 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 557 */     _jspx_th_c_005fchoose_005f1.setParent(null);
/* 558 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 559 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*     */       for (;;) {
/* 561 */         out.write("\n\t\t\t\t");
/* 562 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 563 */           return true;
/* 564 */         out.write("\n\t\t\t\t");
/* 565 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 566 */           return true;
/* 567 */         out.write("\n\t\t\t");
/* 568 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 569 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 573 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 574 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 575 */       return true;
/*     */     }
/* 577 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 578 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 583 */     PageContext pageContext = _jspx_page_context;
/* 584 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 586 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 587 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 588 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*     */     
/* 590 */     _jspx_th_c_005fwhen_005f1.setTest("${!empty DependentDeviceList}");
/* 591 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 592 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*     */       for (;;) {
/* 594 */         out.write("\n\t\t\tstyle=\"display:block;\"\n\t\t\t\t");
/* 595 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 596 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 600 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 601 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 602 */       return true;
/*     */     }
/* 604 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 605 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 610 */     PageContext pageContext = _jspx_page_context;
/* 611 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 613 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 614 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 615 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 616 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 617 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*     */       for (;;) {
/* 619 */         out.write("\n\t\t\tstyle=\"display:none;\"\n\t\t\t\t");
/* 620 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 621 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 625 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 626 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 627 */       return true;
/*     */     }
/* 629 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 630 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 635 */     PageContext pageContext = _jspx_page_context;
/* 636 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 638 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 639 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 640 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*     */     
/* 642 */     _jspx_th_c_005fout_005f0.setValue("${row.displayname}");
/* 643 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 644 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 645 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 646 */       return true;
/*     */     }
/* 648 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 649 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 654 */     PageContext pageContext = _jspx_page_context;
/* 655 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 657 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 658 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 659 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*     */     
/* 661 */     _jspx_th_c_005fout_005f1.setValue("${row.displayname}");
/* 662 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 663 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 664 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 665 */       return true;
/*     */     }
/* 667 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 668 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 673 */     PageContext pageContext = _jspx_page_context;
/* 674 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 676 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 677 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 678 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*     */     
/* 680 */     _jspx_th_c_005fout_005f2.setValue("${row.typeName}");
/* 681 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 682 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 683 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 684 */       return true;
/*     */     }
/* 686 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 687 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 692 */     PageContext pageContext = _jspx_page_context;
/* 693 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 695 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 696 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 697 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*     */     
/* 699 */     _jspx_th_c_005fout_005f3.setValue("${row.imagepath}");
/* 700 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 701 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 702 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 703 */       return true;
/*     */     }
/* 705 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 706 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 711 */     PageContext pageContext = _jspx_page_context;
/* 712 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 714 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 715 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 716 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*     */     
/* 718 */     _jspx_th_c_005fout_005f4.setValue("${row.typeName}");
/* 719 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 720 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 721 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 722 */       return true;
/*     */     }
/* 724 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 725 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 730 */     PageContext pageContext = _jspx_page_context;
/* 731 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 733 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 734 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 735 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*     */     
/* 737 */     _jspx_th_c_005fif_005f2.setTest("${isAdminServer || isManagedServer}");
/* 738 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 739 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 741 */         out.write("\n\t\t\t\t\t\t\t\t\t<td class=\"yellowgrayborder\">");
/* 742 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 743 */           return true;
/* 744 */         out.write("</td>\n\t\t\t\t\t\t\t\t\t");
/* 745 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 746 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 750 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 751 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 752 */       return true;
/*     */     }
/* 754 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 755 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 760 */     PageContext pageContext = _jspx_page_context;
/* 761 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 763 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 764 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 765 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 767 */     _jspx_th_c_005fout_005f5.setValue("${row.managedServer}");
/* 768 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 769 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 770 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 771 */       return true;
/*     */     }
/* 773 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 774 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 779 */     PageContext pageContext = _jspx_page_context;
/* 780 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 782 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 783 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 784 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*     */     
/* 786 */     _jspx_th_c_005fout_005f6.setValue("${row.resourceid}");
/* 787 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 788 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 789 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 790 */       return true;
/*     */     }
/* 792 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 793 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 798 */     PageContext pageContext = _jspx_page_context;
/* 799 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 801 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 802 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 803 */     _jspx_th_c_005fif_005f3.setParent(null);
/*     */     
/* 805 */     _jspx_th_c_005fif_005f3.setTest("${suppressAlertSelected}");
/* 806 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 807 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */       for (;;) {
/* 809 */         out.write("checked=\"true\"");
/* 810 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 811 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 815 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 816 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 817 */       return true;
/*     */     }
/* 819 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 820 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f7(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 825 */     PageContext pageContext = _jspx_page_context;
/* 826 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 828 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 829 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 830 */     _jspx_th_c_005fout_005f7.setParent(null);
/*     */     
/* 832 */     _jspx_th_c_005fout_005f7.setValue("${depDeviceValue}");
/* 833 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 834 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 835 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 836 */       return true;
/*     */     }
/* 838 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 839 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f8(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 844 */     PageContext pageContext = _jspx_page_context;
/* 845 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 847 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 848 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 849 */     _jspx_th_c_005fout_005f8.setParent(null);
/*     */     
/* 851 */     _jspx_th_c_005fout_005f8.setValue("${selectedMonitors}");
/* 852 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 853 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 854 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 855 */       return true;
/*     */     }
/* 857 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 858 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\DependentDeviceListView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */