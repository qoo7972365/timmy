/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
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
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ 
/*     */ public final class DependentMGroupsListView_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
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
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  40 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  44 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  52 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  56 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  57 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  58 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  59 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  60 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  61 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  62 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  69 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  72 */     JspWriter out = null;
/*  73 */     Object page = this;
/*  74 */     JspWriter _jspx_out = null;
/*  75 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  79 */       response.setContentType("text/html");
/*  80 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  82 */       _jspx_page_context = pageContext;
/*  83 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  84 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  85 */       session = pageContext.getSession();
/*  86 */       out = pageContext.getOut();
/*  87 */       _jspx_out = out;
/*     */       
/*  89 */       out.write("\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/json2.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n");
/*     */       
/*  91 */       String selectedDependentMGroupsStr = "";
/*  92 */       String haid = request.getParameter("resourceid");
/*  93 */       java.util.ArrayList<HashMap<String, String>> selectedDepMGrps = new com.adventnet.appmanager.struts.actions.ShowResourceDetails().listSelectedDependentMGrps(haid);
/*  94 */       request.setAttribute("selectedDepMGrps", selectedDepMGrps);
/*     */       
/*  96 */       out.write("\n<SCRIPT>\nfunction createRowsForSelDepMGroup(tableName, depDeviceList)\n{\n\tvar tableObj = document.getElementById(tableName);\n\tvar tbody = tableObj.getElementsByTagName(\"tbody\")[0];\n\tfor(tempObj in depDeviceList)\n\t{\n\t\tvar i = tempObj;\n\t\tvar row = document.createElement('TR');\n\t\trow.setAttribute(\"class\", \"whitegrayborder\");\n\t\trow.className = \"whitegrayborder\";\n\t\t\n\t\t// Append the selected group with selected monitors.\n\t\tvar alreadySeleDepMGroups = document.getElementById(\"selectedDepMonGroups\").value;\n\t\tif (alreadySeleDepMGroups.indexOf(depDeviceList[i].resourceid)!=-1) {\n\t\t\tcontinue;\n\t\t}\n\t\tif (alreadySeleDepMGroups.trim() == \"\") {\n\t\t\talreadySeleDepMGroups = depDeviceList[i].resourceid;\n\t\t} else {\n\t\t\talreadySeleDepMGroups = alreadySeleDepMGroups+\",\"+depDeviceList[i].resourceid;\n\t\t}\n\t\tdocument.getElementById(\"selectedDepMonGroups\").value=alreadySeleDepMGroups;\n\t\t\n\t\t// TD for monitor group displayname\n\t\tvar monitorNameCell = document.createElement('TD');\n\t\tmonitorNameCell.setAttribute(\"class\", \"yellowgrayborder\");\n");
/*  97 */       out.write("\t\tmonitorNameCell.setAttribute(\"width\", \"80%\");\n\t\tmonitorNameCell.setAttribute(\"height\", \"28\");\n\t\tmonitorNameCell.className = \"yellowgrayborder\";\n\t\tmonitorNameCell.innerHTML = depDeviceList[i].displayname;\n\t\trow.appendChild(monitorNameCell);\n\t\t\n\t\t// TD for remove image\n\t\tvar imageCell = document.createElement('TD');\n\t\timageCell.setAttribute(\"class\", \"yellowgrayborder\");\n\t\timageCell.className = \"yellowgrayborder\";\n\t\timageCell.setAttribute(\"width\", \"20%\");\n\t\timageCell.setAttribute(\"height\", \"28\");\t\t\n\t\tvar deleteImageCell = document.createElement('IMG');\n\t\tdeleteImageCell.setAttribute(\"src\", \"../images/thrash.gif\");\n\t\tdeleteImageCell.setAttribute(\"border\", \"0\");\n\t\tdeleteImageCell.setAttribute(\"style\", \"cursor:pointer;padding-left:20px\");\n\t\tdeleteImageCell.style.cursor = \"pointer\";\n\t\tvar url = \"removeMonitorGroupFromCache(this,'\"+i+\"','\"+depDeviceList[i].resourceid+\"')\";//No I18N\n\t\tdeleteImageCell.setAttribute(\"onclick\", url);\n\t\tdeleteImageCell.onclick = new Function(url);\n\t\timageCell.appendChild(deleteImageCell);\n");
/*  98 */       out.write("\t\trow.appendChild(imageCell);\n\t\ttbody.appendChild(row);\n\t}\n\tdocument.getElementById(\"allSelectedMGroupTable\").style.display = \"block\";\n\tdocument.getElementById(\"UpdateButtonTable\").style.display = \"block\";\n\tdocument.getElementById(\"emptyMGroupTableID\").style.display = \"none\";\n}\n\nfunction removeMonitorGroupFromCache(imageNodeObj, objectId, dependentGroupId)\n{\n\tvar s = confirm('");
/*  99 */       out.print(FormatUtil.getString("am.webclient.monitorgroup.remove.dependencygroup.jsalert.text"));
/* 100 */       out.write("');\n\tif(!s)\n\t{\n\t\treturn;\n\t}\n\tvar rowIndex = imageNodeObj.parentNode.parentNode.rowIndex;\n\tvar tableObj = document.getElementById(\"DependentMGroupTableID\");\n\ttableObj.deleteRow(rowIndex);\n\tvar tbody = tableObj.getElementsByTagName(\"tbody\")[0];\n\tvar rowLen = tbody.rows.length;\n\tif(rowLen == 1)\n\t{\n\t\tdocument.getElementById(\"emptyMGroupTableID\").style.display = \"block\";\n\t\tdocument.getElementById(\"allSelectedMGroupTable\").style.display = \"none\";\n\t\tdocument.getElementById(\"UpdateButtonTable\").style.display = \"none\";\n\t}\n\t\n\t// Remove the selected dependent group from selected list\n\tvar alreadySeleDepMGroups = document.getElementById(\"selectedDepMonGroups\").value;\n\tif (alreadySeleDepMGroups.indexOf(dependentGroupId)!=-1) {\n\t\talreadySeleDepMGroups = alreadySeleDepMGroups.replace(new RegExp(dependentGroupId,'g'),\"\");\n\t\tvar obj = alreadySeleDepMGroups.split(\",\");\n\t\talreadySeleDepMGroups=\"\";\n\t\tfor (var i=0;i < obj.length;i++) {\n\t\t\tif (obj[i] == null || obj[i].trim().length == 0) {\n\t\t\t\tcontinue;\n\t\t\t}\n\t\t\tif (i == (obj.length-1)) {\n");
/* 101 */       out.write("\t\t\t\talreadySeleDepMGroups=alreadySeleDepMGroups+obj[i].trim();\n\t\t\t} else {\n\t\t\t\talreadySeleDepMGroups=alreadySeleDepMGroups+obj[i].trim()+\",\";\n\t\t\t}\n\t\t}\n\t\tdocument.getElementById(\"selectedDepMonGroups\").value=alreadySeleDepMGroups;\n\t}\n}\n\nfunction removeMonitorGroupFromDependency(dependentGroupId,parentId, imageNodeObj)\n{\n\tvar s = confirm('");
/* 102 */       out.print(FormatUtil.getString("am.webclient.monitorgroup.remove.dependencygroup.jsalert.text"));
/* 103 */       out.write("');\n\tif(!s)\n\t{\n\t\treturn;\n\t}\n\tvar responseObj = $.ajax({  //No I18N\n\t\tasync: false, //No I18N\n\t\tcache: false, //No I18N\n\t\ttype: \"GET\", //No I18N\n\t\turl: \"/manageApplications.do\", //No I18N\n\t\tdata: {method:\"removeMonitorGrpFromDependency\",parentGroupId:parentId,selectedGroupId:dependentGroupId,fromAjax:\"true\"} //No I18N\n\t});\n\tvar response = eval(\"(\"+ responseObj.responseText +\")\");\n\tif(response.result)\n\t{\n\t\t// Remove the selected dependent group from selected list\n\t\tvar alreadySeleDepMGroups = document.getElementById(\"selectedDepMonGroups\").value;\n\t\tif (alreadySeleDepMGroups.indexOf(dependentGroupId)!=-1) {\n\t\t\talreadySeleDepMGroups = alreadySeleDepMGroups.replace(new RegExp(dependentGroupId,'g'),\"\");\n\t\t\tvar obj = alreadySeleDepMGroups.split(\",\");\n\t\t\talreadySeleDepMGroups=\"\";\n\t\t\tfor (var i=0;i < obj.length;i++) {\n\t\t\t\tif (obj[i] == null || obj[i].trim().length == 0) {\n\t\t\t\t\tcontinue;\n\t\t\t\t}\n\t\t\t\tif (i == (obj.length-1)) {\n\t\t\t\t\talreadySeleDepMGroups=alreadySeleDepMGroups+obj[i].trim();\n\t\t\t\t} else {\n\t\t\t\t\talreadySeleDepMGroups=alreadySeleDepMGroups+obj[i].trim()+\",\";\n");
/* 104 */       out.write("\t\t\t\t}\n\t\t\t}\n\t\t\tdocument.getElementById(\"selectedDepMonGroups\").value=alreadySeleDepMGroups;\n\t\t}\n\t\t\n\t\tvar rowIndex = imageNodeObj.parentNode.parentNode.rowIndex;\n\t\tvar tableObj = document.getElementById(\"DependentMGroupTableID\");\n\t\ttableObj.deleteRow(rowIndex);\n\t\tvar tbody = tableObj.getElementsByTagName(\"tbody\")[0];\n\t\tvar rowLen = tbody.rows.length;\n\t\tif(rowLen == 1)\n\t\t{\n\t\t\tdocument.getElementById(\"emptyMGroupTableID\").style.display = \"block\";\n\t\t\tdocument.getElementById(\"allSelectedMGroupTable\").style.display = \"none\";\n\t\t\tdocument.getElementById(\"UpdateButtonTable\").style.display = \"none\";\n\t\t}\n\t}\n\telse\n\t{\n\t\talert(response.message); //I18N\n\t}\n}\n</SCRIPT>\n<form name='DependentMGroupListForm' action=\"/showresource.do\">\n<div id=\"DependentMGroupsTable\" style=\"display:none;\">\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\"  align=\"center\" style=\"margin-top:10px;\">\n\t<tr>\n\t\t<td width=\"100%\">\n\t\t\t");
/* 105 */       if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*     */         return;
/* 107 */       out.write("\n\t\t\t<table class=\"lrtbdarkborder\"  style=\"background-color:#fff\" width=\"100%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td align=\"left\" height=\"28\"class=\"columnheadingnotop\" width=\"50%\" colapn=\"3\">");
/* 108 */       out.print(FormatUtil.getString("am.webclient.rule.depeMGroups"));
/* 109 */       out.write("</td>\n\t\t\t\t</tr>\t\t\t\n\t\t\t\t<tr>\n\t\t\t\t\t<td style=\"width:100%;\" class=\"bodytext\" align=\"center\">\n\t\t\t\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"6\" border=\"0\" class=\"\" align=\"left\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"bodytext\" align=\"center\" style=\"padding-top:20px;padding-bottom:20px\"> ");
/* 110 */       out.print(FormatUtil.getString("am.webclient.no.dependent.monitor.groups.text"));
/* 111 */       out.write(".&nbsp;&nbsp;\n\t\t\t\t\t");
/*     */       
/* 113 */       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 114 */       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 115 */       _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */       
/* 117 */       _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 118 */       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 119 */       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */         for (;;) {
/* 121 */           out.write("\n\t\t\t\t\t<a class=\"staticlinks\" href=\"javascript:void(0)\" onclick=\"alertUser()\">");
/* 122 */           out.print(FormatUtil.getString("am.webclient.rule.add.dependent.monitor.groups.link.text"));
/* 123 */           out.write("\n\t\t\t\t\t");
/* 124 */           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 125 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 129 */       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 130 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*     */       }
/*     */       else {
/* 133 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 134 */         out.write("\n\t\t\t\t\t");
/*     */         
/* 136 */         NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 137 */         _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 138 */         _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */         
/* 140 */         _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 141 */         int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 142 */         if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */           for (;;) {
/* 144 */             out.write("\n\t\t\t\t\t<a class=\"staticlinks\" href=\"javascript:void(0)\" onclick=\"showAllGroups()\">");
/* 145 */             out.print(FormatUtil.getString("am.webclient.rule.add.dependent.monitor.groups.link.text"));
/* 146 */             out.write("\n\t\t\t\t\t");
/* 147 */             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 148 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 152 */         if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 153 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*     */         }
/*     */         else {
/* 156 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 157 */           out.write("\n\t\t\t\t\t</a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</div>\n\t\t\t\n\t\t\t");
/* 158 */           if (_jspx_meth_c_005fchoose_005f1(_jspx_page_context))
/*     */             return;
/* 160 */           out.write("\t\t\t\n\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" width=\"100%\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td style=\"width:100%;\" class=\"bodytext\">\n\t\t\t\t\t\t<table class=\"lrtbdarkborder\"  style=\"background-color:#fff\" width=\"100%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" id=\"DependentMGroupTableID\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td align=\"left\" height=\"28\"class=\"columnheadingnotop\" width=\"80%\">");
/* 161 */           out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/* 162 */           out.write("</td>\n\t\t\t\t\t\t\t<td align=\"left\" height=\"28\" class=\"columnheadingnotop\" width=\"20%\">&nbsp;</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*     */           
/* 164 */           IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 165 */           _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 166 */           _jspx_th_c_005fif_005f0.setParent(null);
/*     */           
/* 168 */           _jspx_th_c_005fif_005f0.setTest("${!empty selectedDepMGrps}");
/* 169 */           int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 170 */           if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */             for (;;) {
/* 172 */               out.write("\n\t\t\t\t\t\t");
/*     */               
/* 174 */               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.get(IterateTag.class);
/* 175 */               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 176 */               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_c_005fif_005f0);
/*     */               
/* 178 */               _jspx_th_logic_005fiterate_005f0.setName("selectedDepMGrps");
/*     */               
/* 180 */               _jspx_th_logic_005fiterate_005f0.setId("row");
/*     */               
/* 182 */               _jspx_th_logic_005fiterate_005f0.setType("java.util.HashMap");
/* 183 */               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 184 */               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 185 */                 HashMap row = null;
/* 186 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 187 */                   out = _jspx_page_context.pushBody();
/* 188 */                   _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 189 */                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */                 }
/* 191 */                 row = (HashMap)_jspx_page_context.findAttribute("row");
/*     */                 for (;;) {
/* 193 */                   out.write("\n\t\t\t\t\t\t<tr class=\"whitegrayborder\">\n\t\t\t\t\t\t    ");
/*     */                   
/* 195 */                   String name = (String)row.get("displayname");
/* 196 */                   String resourceid = (String)row.get("resourceid");
/* 197 */                   String type = (String)row.get("type");
/* 198 */                   if (type.equalsIgnoreCase("HAI"))
/*     */                   {
/* 200 */                     type = FormatUtil.getString("am.webclient.monitorgroupdetails.subgroup.text");
/*     */                   }
/* 202 */                   String imgpath = (String)row.get("imagepath");
/* 203 */                   selectedDependentMGroupsStr = selectedDependentMGroupsStr + resourceid + ",";
/*     */                   
/* 205 */                   out.write("\n\t\t\t\t\t\t    <td class=\"yellowgrayborder\" height=\"28\" width=\"80%\">");
/* 206 */                   out.print(FormatUtil.getTrimmedText(name, 50));
/* 207 */                   out.write("</td>\n\t\t\t\t\t\t\t<td class=\"yellowgrayborder\" height=\"28\" width=\"20%\"><img border=\"0\" title=\"");
/* 208 */                   out.print(FormatUtil.getString("am.webclient.monitorgroup.remove.dependencygroup.text"));
/* 209 */                   out.write("\" name=\"Image14\" src=\"/images/thrash.gif\" style=\"cursor:pointer;padding-left:20px\" onclick=\"removeMonitorGroupFromDependency('");
/* 210 */                   out.print(resourceid);
/* 211 */                   out.write(39);
/* 212 */                   out.write(44);
/* 213 */                   out.write(39);
/* 214 */                   out.print(haid);
/* 215 */                   out.write("', this)\"></img></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/* 216 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 217 */                   row = (HashMap)_jspx_page_context.findAttribute("row");
/* 218 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 221 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 222 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 225 */               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 226 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*     */               }
/*     */               
/* 229 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 230 */               out.write("\n\t\t\t\t\t\t");
/* 231 */               int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 232 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 236 */           if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 237 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */           }
/*     */           else {
/* 240 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 241 */             out.write("\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td align=\"left\" class=\"columnheadingnotop bodytext\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onclick=\"showAllGroups()\">");
/* 242 */             out.print(FormatUtil.getString("am.webclient.rule.add.dependent.monitor.groups.link.text"));
/* 243 */             out.write("</a></td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t");
/*     */             
/* 245 */             if ((selectedDependentMGroupsStr.length() != 0) && (selectedDependentMGroupsStr.endsWith(","))) {
/* 246 */               selectedDependentMGroupsStr = selectedDependentMGroupsStr.substring(0, selectedDependentMGroupsStr.length() - 1);
/*     */             }
/*     */             
/* 249 */             out.write("\n\t\t\t<input type=\"hidden\" name=\"selectedDepMonGroups\" id=\"selectedDepMonGroups\" value=\"");
/* 250 */             out.print(selectedDependentMGroupsStr);
/* 251 */             out.write("\"/>\n\t\t\t</div>\n\t\t\t");
/* 252 */             if (_jspx_meth_c_005fchoose_005f2(_jspx_page_context))
/*     */               return;
/* 254 */             out.write("\t\t\t\t\n\t\t\t<table class=\"lrbtborder\" style=\"margin-bottom: 10px;\" width=\"100%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t   <td class=\"tablebottom\" align='center' colspan=\"3\">\n\t\t\t\t\t\t<input type=\"button\" onclick=\"javascript:fnFormSubmit()\" value='");
/* 255 */             out.print(FormatUtil.getString("am.webclient.rule.conf.saveall"));
/* 256 */             out.write("' class=\"buttons btn_highlt\" name=\"button\" title=\"submits values in all tabs\"/>&nbsp;\n\t\t\t\t\t\t<input type=\"button\" onclick=\"javascript:backToScreen();\" value='");
/* 257 */             out.print(FormatUtil.getString("webclient.common.printview.button.close"));
/* 258 */             out.write("' class=\"buttons\" name=\"button3\"/>&nbsp;\n\t\t\t\t\t\t<input type=\"button\" onclick=\"javascript:removeConfiguration();\" value='");
/* 259 */             out.print(FormatUtil.getString("am.webclient.rule.conf.removeall"));
/* 260 */             out.write("' class=\"buttons\" name=\"button4\"/>\n\t\t\t\t   </td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</div>\n\t\t</td>\n\t</tr>\n</table>\n</div>\n</form>\n\n\n\n\n");
/*     */           }
/* 262 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 263 */         out = _jspx_out;
/* 264 */         if ((out != null) && (out.getBufferSize() != 0))
/* 265 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 266 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 269 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 275 */     PageContext pageContext = _jspx_page_context;
/* 276 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 278 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 279 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 280 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 281 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 282 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */       for (;;) {
/* 284 */         out.write("\n\t\t\t\t");
/* 285 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 286 */           return true;
/* 287 */         out.write("\n\t\t\t\t");
/* 288 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 289 */           return true;
/* 290 */         out.write("\n\t\t\t");
/* 291 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 292 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 296 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 297 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 298 */       return true;
/*     */     }
/* 300 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 301 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 306 */     PageContext pageContext = _jspx_page_context;
/* 307 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 309 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 310 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 311 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*     */     
/* 313 */     _jspx_th_c_005fwhen_005f0.setTest("${empty selectedDepMGrps}");
/* 314 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 315 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */       for (;;) {
/* 317 */         out.write("\n\t\t\t\t<div id=\"emptyMGroupTableID\" style=\"margin-bottom: 10px; display:block\">\n\t\t\t\t");
/* 318 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 319 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 323 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 324 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 325 */       return true;
/*     */     }
/* 327 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 328 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 333 */     PageContext pageContext = _jspx_page_context;
/* 334 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 336 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 337 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 338 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 339 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 340 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */       for (;;) {
/* 342 */         out.write("\n\t\t\t\t<div id=\"emptyMGroupTableID\" style=\"margin-bottom: 10px; display:none\">\n\t\t\t\t");
/* 343 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 344 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 348 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 349 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 350 */       return true;
/*     */     }
/* 352 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 353 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fchoose_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 358 */     PageContext pageContext = _jspx_page_context;
/* 359 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 361 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 362 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 363 */     _jspx_th_c_005fchoose_005f1.setParent(null);
/* 364 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 365 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*     */       for (;;) {
/* 367 */         out.write("\n\t\t\t\t");
/* 368 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 369 */           return true;
/* 370 */         out.write("\n\t\t\t\t");
/* 371 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 372 */           return true;
/* 373 */         out.write("\n\t\t\t");
/* 374 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 375 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 379 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 380 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 381 */       return true;
/*     */     }
/* 383 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 384 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 389 */     PageContext pageContext = _jspx_page_context;
/* 390 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 392 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 393 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 394 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*     */     
/* 396 */     _jspx_th_c_005fwhen_005f1.setTest("${!empty selectedDepMGrps}");
/* 397 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 398 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*     */       for (;;) {
/* 400 */         out.write("\n\t\t\t\t<div id=\"allSelectedMGroupTable\" style=\"margin-bottom: 10px; display:block\">\n\t\t\t\t");
/* 401 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 402 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 406 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 407 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 408 */       return true;
/*     */     }
/* 410 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 411 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 416 */     PageContext pageContext = _jspx_page_context;
/* 417 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 419 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 420 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 421 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 422 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 423 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*     */       for (;;) {
/* 425 */         out.write("\n\t\t\t\t<div id=\"allSelectedMGroupTable\" style=\"margin-bottom: 10px; display:none\">\n\t\t\t\t");
/* 426 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 427 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 431 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 432 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 433 */       return true;
/*     */     }
/* 435 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 436 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fchoose_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 441 */     PageContext pageContext = _jspx_page_context;
/* 442 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 444 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 445 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 446 */     _jspx_th_c_005fchoose_005f2.setParent(null);
/* 447 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 448 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*     */       for (;;) {
/* 450 */         out.write("\n\t\t\t\t");
/* 451 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 452 */           return true;
/* 453 */         out.write("\n\t\t\t\t");
/* 454 */         if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 455 */           return true;
/* 456 */         out.write("\n\t\t\t");
/* 457 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 458 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 462 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 463 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 464 */       return true;
/*     */     }
/* 466 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 467 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 472 */     PageContext pageContext = _jspx_page_context;
/* 473 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 475 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 476 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 477 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*     */     
/* 479 */     _jspx_th_c_005fwhen_005f2.setTest("${!empty selectedDepMGrps}");
/* 480 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 481 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*     */       for (;;) {
/* 483 */         out.write("\n\t\t\t\t<div id=\"UpdateButtonTable\" style=\"margin-bottom: 10px; display:block\">\n\t\t\t\t");
/* 484 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 485 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 489 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 490 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 491 */       return true;
/*     */     }
/* 493 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 494 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 499 */     PageContext pageContext = _jspx_page_context;
/* 500 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 502 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 503 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 504 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 505 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 506 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*     */       for (;;) {
/* 508 */         out.write("\n\t\t\t\t<div id=\"UpdateButtonTable\" style=\"margin-bottom: 10px; display:none\">\n\t\t\t\t");
/* 509 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 510 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 514 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 515 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 516 */       return true;
/*     */     }
/* 518 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 519 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\DependentMGroupsListView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */