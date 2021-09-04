/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.struts.taglib.tiles.InsertTag;
/*     */ import org.apache.struts.taglib.tiles.PutTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ 
/*     */ public final class EventLogRules_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  29 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  30 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  46 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  50 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  57 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  58 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  59 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  63 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*  64 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*  65 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  66 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  67 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  68 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  69 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  70 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  77 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  80 */     JspWriter out = null;
/*  81 */     Object page = this;
/*  82 */     JspWriter _jspx_out = null;
/*  83 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  87 */       response.setContentType("text/html;charset=UTF-8");
/*  88 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  90 */       _jspx_page_context = pageContext;
/*  91 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  92 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  93 */       session = pageContext.getSession();
/*  94 */       out = pageContext.getOut();
/*  95 */       _jspx_out = out;
/*     */       
/*  97 */       out.write("<!DOCTYPE html>\n");
/*  98 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  99 */       out.write(10);
/* 100 */       request.setAttribute("HelpKey", "Event Log Rules");
/* 101 */       out.write("\n\n\n\n\n\n\n\n");
/* 102 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 103 */       out.write("\n<html>\n<head>\n<style>\n.disabledtext {\n    font-family: Arial, Helvetica, sans-serif;\n    font-size: 11px;\n    color: #a2a2a2;\n} \n</style>\n\n</head>\n<body onLoad=\"javascript:myonload()\">\n\n");
/*     */       
/* 105 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 106 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 107 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*     */       
/* 109 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/NewAdminLayout.jsp");
/* 110 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 111 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*     */         for (;;) {
/* 113 */           out.write("\n\t\n  \n");
/*     */           
/* 115 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 116 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 117 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*     */           
/* 119 */           _jspx_th_tiles_005fput_005f0.setName("UserArea");
/*     */           
/* 121 */           _jspx_th_tiles_005fput_005f0.setType("string");
/* 122 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 123 */           if (_jspx_eval_tiles_005fput_005f0 != 0) {
/* 124 */             if (_jspx_eval_tiles_005fput_005f0 != 1) {
/* 125 */               out = _jspx_page_context.pushBody();
/* 126 */               _jspx_th_tiles_005fput_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 127 */               _jspx_th_tiles_005fput_005f0.doInitBody();
/*     */             }
/*     */             for (;;) {
/* 130 */               out.write("\n\n <script>\nfunction submitformforenable(ruletype,type1,ruleName, logCategoryName)\n{\n");
/* 131 */               if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f0, _jspx_page_context))
/*     */                 return;
/* 133 */               out.write("\n    // Modified to check whether the boxes related to the particular category alone is selected.\n    if(!isRuleChecked(ruleName))\n    {\n        if(type1=='enable')\n            alert('");
/* 134 */               out.print(FormatUtil.getString("am.webclient.eventlogrules.alert.enable"));
/* 135 */               out.write("');\n        else\n            alert('");
/* 136 */               out.print(FormatUtil.getString("am.webclient.eventlogrules.alert.disable"));
/* 137 */               out.write("');\n    }\n    else\n    {\n        document.form1.method.value=\"globalEnableDisable\";\n        document.form1.ruletype.value=ruletype;\n        document.form1.savetype.value=type1;\n\tdocument.form1.logCategoryName.value = logCategoryName;\n        document.form1.submit();\n    }\n}\n\nfunction submitformfordelete(ruletype,ruleName, logCategoryName)\n{\n    ");
/* 138 */               if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_tiles_005fput_005f0, _jspx_page_context))
/*     */                 return;
/* 140 */               out.write("\n    // Modified to check whether the boxes related to the particular category alone is selected. \n    if(!isRuleChecked(ruleName))\n    {\n            alert('");
/* 141 */               out.print(FormatUtil.getString("am.webclient.eventlogrules.alert.delete"));
/* 142 */               out.write("');   \n    }\n    else\n    {\t\n        if(confirm('");
/* 143 */               out.print(FormatUtil.getString("am.webclient.eventlogrules.confirm.delete"));
/* 144 */               out.write("'))\n        {\n            document.form1.method.value=\"globalDelete\";\n            document.form1.ruletype.value=ruletype;  \n\t    document.form1.logCategoryName.value = logCategoryName;\n            document.form1.submit();\n        }\n    } \n}\n\nfunction isRuleChecked(chckid)\n    {\n    return $(\"input:checkbox[id^='\"+chckid+\"']:checked\").length>0;\n    }\n\nfunction deleteEventLogCreated(Eventruleid)\n{\n\t");
/* 145 */               if (_jspx_meth_logic_005fpresent_005f2(_jspx_th_tiles_005fput_005f0, _jspx_page_context))
/*     */                 return;
/* 147 */               out.write("\n    if(confirm('");
/* 148 */               out.print(FormatUtil.getString("am.webclient.eventlog.confirm.delete"));
/* 149 */               out.write("'))\n    {\n    \t document.form1.method.value=\"deleteEventLog\";");
/* 150 */               out.write("\n    \t document.form1.ruletype.value=Eventruleid;\n         document.form1.submit();\n    }\n   \n}\nfunction showicon(name){\n\n\t document.getElementById(name).style.display = \"block\";\n\t var delElement =  document.getElementById(name+'_del');\n\t if(delElement) {\n\t\t delElement.style.display = \"block\";\n\t }\n//\tjQuery(name).show();\n}\n\nfunction hideicon(name)\n{\n\tdocument.getElementById(name).style.display = \"none\";\n\t var delElement =  document.getElementById(name+'_del');\n\t if(delElement) {\n\t\t delElement.style.display = \"none\";\n\t }\n\t//jQuery(name).hide();\n}\n\nfunction showHide(divIDToBeShown)\n{\n");
/* 151 */               if (_jspx_meth_c_005fchoose_005f0(_jspx_th_tiles_005fput_005f0, _jspx_page_context))
/*     */                 return;
/* 153 */               out.write("\n}\nfunction myonload()\n{\n\tvar logCategoryName = document.form1.logCategoryName.value;\n\tif(logCategoryName != '' && logCategoryName != 'EventLogs')\n\t{\n\t\tshowHide('windowsAzureLogRule');//No I18N\n\t}\n\telse\n\t{\n\t\tshowHide('eventLogRule');//No I18N\n\t}\n}\n</script>\n\n\n");
/*     */               
/* 155 */               Hashtable table = (Hashtable)request.getAttribute("table");
/* 156 */               ArrayList ruletypes = (ArrayList)table.get("ruletypes");
/* 157 */               String ruleid = null;
/* 158 */               boolean delegAdmin = com.adventnet.appmanager.util.DBUtil.isDelegatedAdmin(request.getRemoteUser());
/*     */               
/* 160 */               out.write("\n\n<form name=\"form1\" action=\"/eventlogrules.do\">\n<input type=\"hidden\" name=\"method\">\n<input type=\"hidden\" name=\"ruletype\">\n<input type=\"hidden\" name=\"savetype\">\n<input type=\"hidden\" name=\"logCategoryName\" value=\"");
/* 161 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f0, _jspx_page_context))
/*     */                 return;
/* 163 */               out.write("\">\n<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\" class=\"itadmin-hide\">\n    <tr>\n   \t <td class=\"bcsign\"  height=\"22\" valign=\"top\"> \n     \t");
/* 164 */               if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) {
/* 165 */                 out.write("\n\t\t\t");
/* 166 */                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 167 */                 out.write(" \n\t\t");
/*     */               } else {
/* 169 */                 out.write("\n\t \t\t ");
/* 170 */                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 171 */                 out.write("\n\t \t");
/*     */               }
/* 173 */               out.write("\n\t \t &gt; <span class=\"bcactive\"> ");
/* 174 */               out.print(FormatUtil.getString("am.webclient.admin.eventlogrules.link"));
/* 175 */               out.write("</span>\n\t </td>\n   </tr>\n</table>\n<table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">\n    <tr class=\"tabBtmLine\">\n        <td nowrap=\"nowrap\">\n            <table id=\"innerTab\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"innertab_btm_space\">\n                <tr>\n                    <td width=\"17\"></td>\n                    <td>\n\t\t    <div id=\"eventLogTabDiv\">\n                        <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\">\n                            <tr>\n                                <td id=\"tab1-left\" class=\"tbSelected_Left\">\n                                    <img height=\"1\" width=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\">\n                                </td>\n                                <td id=\"tab1\" class=\"tbSelected_Middle\">\n                                    <a href=\"javascript:showHide('eventLogRule')\">&nbsp;<span class=\"tabLink\">");
/* 176 */               out.print(FormatUtil.getString("am.webclient.admin.config.eventLogRules"));
/* 177 */               out.write("</span></a>\n                                </td>\n                                <td id=\"tab1-right\" class=\"tbSelected_Right\">\n                                    <img height=\"1\" width=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\">\n                                </td>\n                            </tr>\n                        </table>\n\t\t      </div>\n                    </td>\n                    <td>\n                        <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\">\n                            <tr>\n                                <td id=\"tab2-left\" class=\"tbUnSelected_Left\">\n                                    <img height=\"1\" width=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\">\n                                </td>\n                                <td id=\"tab2\" class=\"tbUnSelected_Middle\">\n                                    <a href=\"javascript:showHide('windowsAzureLogRule')\">&nbsp;<span class=\"tabLink\">");
/* 178 */               out.print(FormatUtil.getString("am.webclient.admin.configure.windowsAzureLogRules"));
/* 179 */               out.write("</span></a>\n                                </td>\n                                <td id=\"tab2-right\" class=\"tbUnSelected_Right\">\n                                    <img height=\"1\" width=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\">\n                                </td>\n                            </tr>\n                        </table>\n                    </td>\n                </tr>               \n            </table> \n        </td>\n    </tr>\n</table>\n<div id=\"eventLogRuleDiv\">\n<table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\">\n    ");
/*     */               
/* 181 */               for (int i = 0; i < ruletypes.size(); i++)
/*     */               {
/*     */ 
/* 184 */                 out.write("\n    <tr>\n    ");
/*     */                 
/* 186 */                 for (int temp = 0; (temp < 2) && (i < ruletypes.size()); temp++)
/*     */                 {
/* 188 */                   String eventruleid = (String)((ArrayList)ruletypes.get(i)).get(0);
/* 189 */                   String ruleName = (String)((ArrayList)ruletypes.get(i)).get(1);
/* 190 */                   String categoryName = (String)((ArrayList)ruletypes.get(i)).get(2);
/* 191 */                   if ((categoryName == null) || (categoryName.indexOf("EventLogs") != -1))
/*     */                   {
/*     */ 
/* 194 */                     out.write("\n    <td valign=\"top\" width=\"49%\"  style=\"padding:10px 0px 10px 0px;\">\n        <table  width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" onMouseOver=\"showicon('");
/* 195 */                     out.print(ruleName);
/* 196 */                     out.write("')\" onMouseOut=\"hideicon('");
/* 197 */                     out.print(ruleName);
/* 198 */                     out.write("')\">\n            <tr> \n                <td colspan=\"3\" class=\"tableheadingbborder\">\n                    ");
/* 199 */                     out.print(FormatUtil.getString(ruleName));
/* 200 */                     out.write("\n                </td>\n                <td class=\"tableheadingbborder\"  align=right>\n                \n              ");
/* 201 */                     if ((Integer.parseInt(eventruleid) > 8) && (!delegAdmin)) {
/* 202 */                       out.write("\n              <table style=\"align:right ; width:40px; margin: 0px;border-collapse: collapse;padding: 0px;height:16px;\"  > <tr>\n              <td  align=right><a title='");
/* 203 */                       out.print(FormatUtil.getString("Edit"));
/* 204 */                       out.write("' onClick=\"MM_openBrWindow('/jsp/AddNewEventLog.jsp?method=editEventLog&ruleName=");
/* 205 */                       out.print(ruleName);
/* 206 */                       out.write("&ruleid=");
/* 207 */                       out.print(eventruleid);
/* 208 */                       out.write("','Register','width=750,height=500,top=120,left=180, scrollbars=yes, resizable=yes')\" href=\"#\" ><img align=\"right\" style=\"display:none\" id=\"");
/* 209 */                       out.print(ruleName);
/* 210 */                       out.write("\" src=\"/images/icon_edit.gif\"></a></td>\n              <td  align=right><a title='");
/* 211 */                       out.print(FormatUtil.getString("am.webclient.eventlog.delete.tooltip.text"));
/* 212 */                       out.write("' onClick=\"JavaScript:deleteEventLogCreated('");
/* 213 */                       out.print(eventruleid);
/* 214 */                       out.write("')\" href=\"#\" > <img align=\"right\" style=\"display:none\" id=\"");
/* 215 */                       out.print(ruleName);
/* 216 */                       out.write("_del\" src=\"/images/icon_del_jobs.gif\"></a></td>\n              <tr>\n              </table>      \n                    \n               ");
/*     */                     } else {
/* 218 */                       out.write("\n                    <a title='");
/* 219 */                       out.print(FormatUtil.getString("Edit"));
/* 220 */                       out.write("' onClick=\"MM_openBrWindow('/jsp/AddNewEventLog.jsp?method=editEventLog&ruleName=");
/* 221 */                       out.print(ruleName);
/* 222 */                       out.write("&ruleid=");
/* 223 */                       out.print(eventruleid);
/* 224 */                       out.write("','Register','width=750,height=500,top=120,left=180, scrollbars=yes, resizable=yes')\" href=\"#\" style=\"height:16px;\"><img align=\"right\" style=\"display:none\" id=\"");
/* 225 */                       out.print(ruleName);
/* 226 */                       out.write("\" src=\"/images/icon_edit.gif\"></a>\n              ");
/*     */                     }
/* 228 */                     out.write("      \n                </td>\n               \n            </tr>\n            ");
/*     */                     
/* 230 */                     ArrayList list = (ArrayList)table.get((String)((ArrayList)ruletypes.get(i)).get(0));
/* 231 */                     for (int j = 0; j < list.size(); j++)
/*     */                     {
/* 233 */                       String styleclass = null;
/* 234 */                       if (j % 2 == 0)
/*     */                       {
/* 236 */                         styleclass = "whitegrayborder";
/*     */                       }
/*     */                       else
/*     */                       {
/* 240 */                         styleclass = "yellowgrayborder";
/*     */                       }
/*     */                       
/* 243 */                       out.write(" \n            <tr  onmouseout=\"this.className='alarmheader'\" onMouseOver=\"this.className='alarmHeaderHover'\" class=\"alarmheader\">\n                ");
/*     */                       
/* 245 */                       String check = (String)((ArrayList)list.get(j)).get(3);
/* 246 */                       String status = null;
/* 247 */                       if (check.equals("1"))
/*     */                       {
/* 249 */                         status = "bodytext";
/*     */                       }
/*     */                       else
/*     */                       {
/* 253 */                         status = "disabledtext";
/*     */                       }
/* 255 */                       ruleid = (String)((ArrayList)list.get(j)).get(0);
/*     */                       
/* 257 */                       out.write("\n                <td  class=\"");
/* 258 */                       out.print(styleclass);
/* 259 */                       out.write("\" align=\"center\">\n\n       \t   \t");
/*     */                       
/* 261 */                       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) || (Integer.parseInt(ruleid) < 10000)) {
/* 262 */                         out.write("\n                    <input name=\"rules\" id=\"");
/* 263 */                         out.print(ruleName);
/* 264 */                         out.print(j);
/* 265 */                         out.write("\" type=\"checkbox\" value=");
/* 266 */                         out.print(ruleid);
/* 267 */                         out.write(">\n                    ");
/*     */                       } else {
/* 269 */                         out.write(" &nbsp; ");
/*     */                       }
/* 271 */                       out.write("\n                </td>\n                <td width=79%  class=");
/* 272 */                       out.print(styleclass);
/* 273 */                       out.write(" >\n                    <span class=");
/* 274 */                       out.print(status);
/* 275 */                       out.write(62);
/* 276 */                       out.print(FormatUtil.getString((String)((ArrayList)list.get(j)).get(1)));
/* 277 */                       out.write("</span>\n          \t</td>\n       \t   \t<td colspan=\"3\" align=\"center\" title=\"");
/* 278 */                       out.print(FormatUtil.getString("am.webclient.eventlogrules.EditRule"));
/* 279 */                       out.write("\" class=");
/* 280 */                       out.print(styleclass);
/* 281 */                       out.write(" >\n                    <a href=\"/eventlogrules.do?method=showForm&savetype=edit&ruleid=");
/* 282 */                       out.print(ruleid);
/* 283 */                       out.write("\" class=\"staticlinks\"> <img align=\"right\"  src=\"/images/icon_edit.gif\"/> </a>\n          \t</td>\n            </tr>  \n            ");
/*     */                     }
/*     */                     
/*     */ 
/* 287 */                     out.write("\n            ");
/*     */                     
/* 289 */                     if (list.size() == 0)
/*     */                     {
/*     */ 
/* 292 */                       out.write("\n            <tr rowspan=\"10\">\n                <td colspan=\"4\" align=\"center\" height=\"25\" class=\"whitegrayborder\">\n                    ");
/* 293 */                       out.print(FormatUtil.getString("am.webclient.eventlogrules.NoRules"));
/* 294 */                       out.write("\n                </td>\n            </tr>\n            <tr>\n                <td colspan=\"4\" align=\"right\" class=\"bodytext tablebottom1\">\n                    <a href=\"/eventlogrules.do?method=showForm&savetype=new&ruletype=");
/* 295 */                       out.print((String)((ArrayList)ruletypes.get(i)).get(0));
/* 296 */                       out.write("\" class=\"new-monitordiv-link\" style=\"line-height:9px;\">");
/* 297 */                       out.print(FormatUtil.getString("am.webclient.admin.eventlogrules.NewRule"));
/* 298 */                       out.write("</a>\n                </td>\n            </tr>\n            ");
/*     */ 
/*     */                     }
/*     */                     else
/*     */                     {
/*     */ 
/* 304 */                       out.write("\n            <tr>\n                <td colspan=\"3\" width=70% class=\"bodytext tablebottom1\" style=\"line-height:9px;\">\n                    <a  href=\"JavaScript:submitformforenable('");
/* 305 */                       out.print((String)((ArrayList)ruletypes.get(i)).get(0));
/* 306 */                       out.write("','enable','");
/* 307 */                       out.print(ruleName);
/* 308 */                       out.write("','EventLogs')\" class=\"new-monitordiv-link\" style=\"line-height:9px;\">");
/* 309 */                       out.print(FormatUtil.getString("am.webclient.maintenance.enable"));
/* 310 */                       out.write("</a>&nbsp;|&nbsp;\n                    <a  href=\"JavaScript:submitformforenable('");
/* 311 */                       out.print((String)((ArrayList)ruletypes.get(i)).get(0));
/* 312 */                       out.write("','disable','");
/* 313 */                       out.print(ruleName);
/* 314 */                       out.write("','EventLogs')\"  class=\"new-monitordiv-link\" style=\"line-height:9px;\">");
/* 315 */                       out.print(FormatUtil.getString("am.webclient.maintenance.disable"));
/* 316 */                       out.write("</a>&nbsp;|&nbsp;<a  href=\"JavaScript:submitformfordelete('");
/* 317 */                       out.print((String)((ArrayList)ruletypes.get(i)).get(0));
/* 318 */                       out.write(39);
/* 319 */                       out.write(44);
/* 320 */                       out.write(39);
/* 321 */                       out.print(ruleName);
/* 322 */                       out.write("','EventLogs')\"  class=\"new-monitordiv-link\" style=\"line-height:9px;\">");
/* 323 */                       out.print(FormatUtil.getString("am.webclient.cam.delete.link"));
/* 324 */                       out.write("</a>\n                </td>\n\t  \t<td width=\"30%\" class=\"bodytext tablebottom1\" align=\"right\">\n                    <a href=\"/eventlogrules.do?method=showForm&savetype=new&ruletype=");
/* 325 */                       out.print((String)((ArrayList)ruletypes.get(i)).get(0));
/* 326 */                       out.write("&logCategoryName=EventLogs\"  class=\"new-monitordiv-link\" style=\"line-height:9px;\">");
/* 327 */                       out.print(FormatUtil.getString("am.webclient.admin.eventlogrules.NewRule"));
/* 328 */                       out.write("</a>\n                </td>\n            </tr> \n            ");
/*     */                     }
/*     */                     
/*     */ 
/* 332 */                     out.write("\n        </table>\n    </td>\n    ");
/*     */                   }
/*     */                   
/*     */ 
/* 336 */                   out.write("\n    <td style=\"width:1%\">&nbsp;</td>\n        ");
/*     */                   
/* 338 */                   if (temp != 1)
/*     */                   {
/* 340 */                     i++;
/*     */                   }
/*     */                 }
/*     */                 
/* 344 */                 out.write("\n  </tr>\n  ");
/*     */               }
/*     */               
/*     */ 
/* 348 */               out.write("\n</table>\n");
/* 349 */               if (!delegAdmin) {
/* 350 */                 out.write("\n<table align=\"right\">\n    <tr>\n        <td width=\"90%\">\n            <a class=\"staticlinks-red\" onClick=\"MM_openBrWindow('/jsp/AddNewEventLog.jsp?method=addNewEvent','Register','width=750,height=500,top=120,left=180, scrollbars=yes, resizable=yes')\" href=\"#\">\n                ");
/* 351 */                 out.print(FormatUtil.getString("Add New Event Log"));
/* 352 */                 out.write("\n            </a>\n        </td>\n    </tr>\n</table>\n");
/*     */               }
/* 354 */               out.write("\n</div>\n<div id=\"windowsAzureLogRuleDiv\" style=\"display:none\">\n    <table width=\"100%\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\">\n    ");
/*     */               
/* 356 */               for (int i = 0; i < ruletypes.size(); i++)
/*     */               {
/* 358 */                 String eventruleid = (String)((ArrayList)ruletypes.get(i)).get(0);
/* 359 */                 String ruleName = (String)((ArrayList)ruletypes.get(i)).get(1);
/* 360 */                 String categoryName = (String)((ArrayList)ruletypes.get(i)).get(2);
/* 361 */                 if ((categoryName == null) || (categoryName.indexOf("EventLogs") == -1))
/*     */                 {
/*     */ 
/*     */ 
/* 365 */                   if (i % 2 == 0)
/*     */                   {
/*     */ 
/* 368 */                     out.write("\n                <tr>\n    ");
/*     */                   }
/*     */                   
/*     */ 
/* 372 */                   out.write("\n        <td valign=\"top\" width=\"49%\"  style=\"padding:10px 0px 10px 0px;\">\n        <table  width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n            <tr> \n                <td style=\"height:16px;\" colspan=\"3\" class=\"tableheadingbborder\">\n                    ");
/* 373 */                   out.print(FormatUtil.getString(ruleName));
/* 374 */                   out.write("\n                </td>\n                <td style=\"height:16px;\"  class=\"tableheadingbborder\">&nbsp;\n                    <!--a onclick=\"MM_openBrWindow('/jsp/AddNewEventLog.jsp?method=editEventLog&ruleName=");
/* 375 */                   out.print(ruleName);
/* 376 */                   out.write("&ruleid=");
/* 377 */                   out.print(eventruleid);
/* 378 */                   out.write("','Register','width=750,height=500,top=120,left=180, scrollbars=yes, resizable=yes')\" href=\"#\" style=\"height:16px;\"><img align=\"right\" style=\"display:none\" id=\"");
/* 379 */                   out.print(ruleName);
/* 380 */                   out.write("\" src=\"/images/icon_edit.gif\"></a--> ");
/* 381 */                   out.write("\n                </td>\n            </tr>\n            ");
/*     */                   
/* 383 */                   ArrayList list = (ArrayList)table.get((String)((ArrayList)ruletypes.get(i)).get(0));
/* 384 */                   for (int j = 0; j < list.size(); j++)
/*     */                   {
/* 386 */                     String styleclass = null;
/* 387 */                     if (j % 2 == 0)
/*     */                     {
/* 389 */                       styleclass = "whitegrayborder";
/*     */                     }
/*     */                     else
/*     */                     {
/* 393 */                       styleclass = "yellowgrayborder";
/*     */                     }
/*     */                     
/* 396 */                     out.write(" \n            <tr  onmouseout=\"this.className='alarmheader'\" onMouseOver=\"this.className='alarmHeaderHover'\" class=\"alarmheader\">\n                ");
/*     */                     
/* 398 */                     String check = (String)((ArrayList)list.get(j)).get(3);
/* 399 */                     String status = null;
/* 400 */                     if (check.equals("1"))
/*     */                     {
/* 402 */                       status = "bodytext";
/*     */                     }
/*     */                     else
/*     */                     {
/* 406 */                       status = "disabledtext";
/*     */                     }
/* 408 */                     ruleid = (String)((ArrayList)list.get(j)).get(0);
/*     */                     
/* 410 */                     out.write("\n                <td  class=\"");
/* 411 */                     out.print(styleclass);
/* 412 */                     out.write("\" align=\"center\">\n                    <input name=\"rules\" id=\"");
/* 413 */                     out.print(ruleName);
/* 414 */                     out.print(j);
/* 415 */                     out.write("\" type=\"checkbox\" value=");
/* 416 */                     out.print(ruleid);
/* 417 */                     out.write(">\n                </td>\n                <td width=79%  class=");
/* 418 */                     out.print(styleclass);
/* 419 */                     out.write(" >\n                    <span class=");
/* 420 */                     out.print(status);
/* 421 */                     out.write(62);
/* 422 */                     out.print(FormatUtil.getString((String)((ArrayList)list.get(j)).get(1)));
/* 423 */                     out.write("</span>\n          \t</td>\n       \t   \t<td colspan=\"3\" align=\"center\" title=\"");
/* 424 */                     out.print(FormatUtil.getString("am.webclient.eventlogrules.EditRule"));
/* 425 */                     out.write("\" class=");
/* 426 */                     out.print(styleclass);
/* 427 */                     out.write(">\n                    <a href=\"/eventlogrules.do?method=showForm&savetype=edit&ruleid=");
/* 428 */                     out.print(ruleid);
/* 429 */                     out.write("&logCategory=");
/* 430 */                     out.print((String)((ArrayList)ruletypes.get(i)).get(2));
/* 431 */                     out.write("\" class=\"staticlinks\">\n                        <img align=\"right\"  src=\"/images/icon_edit.gif\"/>\n                    </a>\n          \t</td>\n            </tr>  \n            ");
/*     */                   }
/*     */                   
/*     */ 
/* 435 */                   out.write("\n            ");
/*     */                   
/* 437 */                   if (list.size() == 0)
/*     */                   {
/*     */ 
/* 440 */                     out.write("\n            <tr rowspan=\"10\">\n                <td colspan=\"4\" align=\"center\" height=\"25\" class=\"whitegrayborder\">\n\t\t");
/*     */                     
/* 442 */                     if ((categoryName != null) && (categoryName.equals("AzureTraceLogs")))
/*     */                     {
/*     */ 
/* 445 */                       out.write(10);
/* 446 */                       out.write(9);
/* 447 */                       out.write(9);
/* 448 */                       out.print(FormatUtil.getString("am.webclient.windowsazure.tracelogrules.NoRules"));
/* 449 */                       out.write(10);
/* 450 */                       out.write(9);
/* 451 */                       out.write(9);
/*     */ 
/*     */                     }
/* 454 */                     else if ((categoryName != null) && (categoryName.equals("AzureDiagnosticLogs")))
/*     */                     {
/*     */ 
/* 457 */                       out.write(10);
/* 458 */                       out.write(9);
/* 459 */                       out.write(9);
/* 460 */                       out.print(FormatUtil.getString("am.webclient.windowsazure.diagnosticlogrules.NoRules"));
/* 461 */                       out.write(10);
/* 462 */                       out.write(9);
/* 463 */                       out.write(9);
/*     */                     }
/*     */                     
/*     */ 
/* 467 */                     out.write("\n                </td>\n            </tr>\n            <tr>\n                <td colspan=\"4\" align=\"right\" class=\"bodytext tablebottom1\">\n                    <a href=\"/eventlogrules.do?method=showForm&savetype=new&ruletype=");
/* 468 */                     out.print((String)((ArrayList)ruletypes.get(i)).get(0));
/* 469 */                     out.write("&logCategory=");
/* 470 */                     out.print((String)((ArrayList)ruletypes.get(i)).get(2));
/* 471 */                     out.write("\" class=\"new-monitordiv-link\" style=\"line-height:9px;\">");
/* 472 */                     out.print(FormatUtil.getString("am.webclient.admin.eventlogrules.NewRule"));
/* 473 */                     out.write("</a>\n                </td>\n            </tr>\n            ");
/*     */ 
/*     */                   }
/*     */                   else
/*     */                   {
/*     */ 
/* 479 */                     out.write("\n            <tr>\n                <td colspan=\"3\" width=79% class=\"bodytext tablebottom1\" style=\"line-height:9px;\">\n                    <a  href=\"JavaScript:submitformforenable('");
/* 480 */                     out.print((String)((ArrayList)ruletypes.get(i)).get(0));
/* 481 */                     out.write("','enable','");
/* 482 */                     out.print(ruleName);
/* 483 */                     out.write(39);
/* 484 */                     out.write(44);
/* 485 */                     out.write(39);
/* 486 */                     if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f0, _jspx_page_context))
/*     */                       return;
/* 488 */                     out.write("')\" class=\"new-monitordiv-link\" style=\"line-height:9px;\">");
/* 489 */                     out.print(FormatUtil.getString("am.webclient.maintenance.enable"));
/* 490 */                     out.write("</a>&nbsp;|&nbsp;\n                    <a  href=\"JavaScript:submitformforenable('");
/* 491 */                     out.print((String)((ArrayList)ruletypes.get(i)).get(0));
/* 492 */                     out.write("','disable','");
/* 493 */                     out.print(ruleName);
/* 494 */                     out.write(39);
/* 495 */                     out.write(44);
/* 496 */                     out.write(39);
/* 497 */                     if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f0, _jspx_page_context))
/*     */                       return;
/* 499 */                     out.write("')\"  class=\"new-monitordiv-link\" style=\"line-height:9px;\">");
/* 500 */                     out.print(FormatUtil.getString("am.webclient.maintenance.disable"));
/* 501 */                     out.write("</a>&nbsp;|&nbsp;<a  href=\"JavaScript:submitformfordelete('");
/* 502 */                     out.print((String)((ArrayList)ruletypes.get(i)).get(0));
/* 503 */                     out.write(39);
/* 504 */                     out.write(44);
/* 505 */                     out.write(39);
/* 506 */                     out.print(ruleName);
/* 507 */                     out.write(39);
/* 508 */                     out.write(44);
/* 509 */                     out.write(39);
/* 510 */                     if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f0, _jspx_page_context))
/*     */                       return;
/* 512 */                     out.write("')\"  class=\"new-monitordiv-link\" style=\"line-height:9px;\">");
/* 513 */                     out.print(FormatUtil.getString("am.webclient.cam.delete.link"));
/* 514 */                     out.write("</a>\n                </td>\n\t  \t<td class=\"bodytext tablebottom1\" align=\"right\" c>\n                    <a href=\"/eventlogrules.do?method=showForm&savetype=new&ruletype=");
/* 515 */                     out.print((String)((ArrayList)ruletypes.get(i)).get(0));
/* 516 */                     out.write("&logCategory=");
/* 517 */                     out.print((String)((ArrayList)ruletypes.get(i)).get(2));
/* 518 */                     out.write("\"  class=\"new-monitordiv-link\" style=\"line-height:9px;\">");
/* 519 */                     out.print(FormatUtil.getString("am.webclient.admin.eventlogrules.NewRule"));
/* 520 */                     out.write("</a>\n                </td>\n            </tr> \n            ");
/*     */                   }
/*     */                   
/*     */ 
/* 524 */                   out.write("\n        </table>\n    </td>\n    ");
/*     */                   
/* 526 */                   if (i % 2 != 0)
/*     */                   {
/*     */ 
/* 529 */                     out.write("\n  </tr>\n    ");
/*     */ 
/*     */                   }
/*     */                   else
/*     */                   {
/*     */ 
/* 535 */                     out.write("\n    <td style=\"width:1%\">&nbsp;</td>\n    ");
/*     */                   }
/*     */                 }
/*     */               }
/*     */               
/* 540 */               out.write("\n</table>\n</div>\n<br>\n<br>\n<!--//  Helpcard template table starts here..-->\n<table class=\"\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\">\n    <tbody>\n        <tr>\n            <td class=\"helpCardHdrTopLeft\"></td>\n            <td class=\"helpCardHdrTopBg\">\n                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                    <tbody>\n                        <tr>\n                            <td class=\"helpCardContentBg\" align=\"left\" valign=\"middle\"><span class=\"helpHdrTxt\">");
/* 541 */               out.print(FormatUtil.getString("Help Card"));
/* 542 */               out.write("</span><img src=\"/images/helpCue.gif\" align=\"texttop\" height=\"16\" width=\"19\"></td>\n                            <td class=\"helpCardHdrRightEar\" align=\"left\" valign=\"middle\">&nbsp;</td>\n                            <td align=\"left\" valign=\"middle\">&nbsp;</td>\n                        </tr>\n                    </tbody>\n                </table>\n            </td>\n            <td class=\"helpCardHdrRightTop\">&nbsp;</td>\n        </tr>\n        <tr>\n            <td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n            <td valign=\"top\">\n                <table align=\"center\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\" width=\"100%\">\n                    <tbody>\n                        <tr>\n                            <td style=\"padding-top: 10px;\" class=\"boxedContent\">\n                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n                                    <tbody>\n                                        <tr>\n                                            <td>\n                                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n");
/* 543 */               out.write("                                                    <tbody>\n                                                        <tr>\n                                                            <td class=\"hCardInnerTopLeft\"></td>\n                                                            <td class=\"hCardInnerTopBg\"></td>\n                                                            <td class=\"hCardInnerTopRight\"></td>\n                                                        </tr>\n                                                        <tr>\n                                                            <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n                                                            <td class=\"hCardInnerBoxBg product-help\">\n                                                                ");
/* 544 */               out.print(FormatUtil.getString("am.webclient.eventloghelpcard.text3"));
/* 545 */               out.write("\n                                                            </td>\n                                                            <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n                                                        </tr>\n                                                    </tbody>\n                                                </table>\n                                            </td>\n                                        </tr>\n                                    </tbody>\n                                </table>\n                            </td>\n                        </tr>\n                    </tbody>\n                </table>\n            </td>\n            <td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n        </tr>\n        <tr>\n            <td class=\"helpCardMainBtmLeft\"></td>\n            <td class=\"helpCardMainBtmBg\"></td>\n            <td class=\"helpCardMainBtmRight\"></td>\n        </tr>\n    </tbody>\n</table>\n<!-- Help card template table ends here.-->\n</form>\t\n");
/* 546 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f0.doAfterBody();
/* 547 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 550 */             if (_jspx_eval_tiles_005fput_005f0 != 1) {
/* 551 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 554 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 555 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f0); return;
/*     */           }
/*     */           
/* 558 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f0);
/* 559 */           out.write(10);
/* 560 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 562 */           out.write("\n    ");
/* 563 */           if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 565 */           out.write(10);
/* 566 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 567 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 571 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 572 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*     */       }
/*     */       else {
/* 575 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 576 */         out.write(10);
/*     */       }
/* 578 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 579 */         out = _jspx_out;
/* 580 */         if ((out != null) && (out.getBufferSize() != 0))
/* 581 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 582 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 585 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 591 */     PageContext pageContext = _jspx_page_context;
/* 592 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 594 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 595 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 596 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f0);
/*     */     
/* 598 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 599 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 600 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 602 */         out.write("\n    alertUser();\n    return;\n");
/* 603 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 604 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 608 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 609 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 610 */       return true;
/*     */     }
/* 612 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 613 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_tiles_005fput_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 618 */     PageContext pageContext = _jspx_page_context;
/* 619 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 621 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 622 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 623 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f0);
/*     */     
/* 625 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 626 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 627 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*     */       for (;;) {
/* 629 */         out.write("\n\talertUser();\n\treturn;\n    ");
/* 630 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 631 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 635 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 636 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 637 */       return true;
/*     */     }
/* 639 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 640 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f2(JspTag _jspx_th_tiles_005fput_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 645 */     PageContext pageContext = _jspx_page_context;
/* 646 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 648 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 649 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 650 */     _jspx_th_logic_005fpresent_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f0);
/*     */     
/* 652 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 653 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 654 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*     */       for (;;) {
/* 656 */         out.write("\n\talertUser();\n\treturn;\n    ");
/* 657 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 658 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 662 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 663 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 664 */       return true;
/*     */     }
/* 666 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 667 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_tiles_005fput_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 672 */     PageContext pageContext = _jspx_page_context;
/* 673 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 675 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 676 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 677 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f0);
/* 678 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 679 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */       for (;;) {
/* 681 */         out.write(10);
/* 682 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 683 */           return true;
/* 684 */         out.write(10);
/* 685 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 686 */           return true;
/* 687 */         out.write(10);
/* 688 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 689 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 693 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 694 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 695 */       return true;
/*     */     }
/* 697 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 698 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 703 */     PageContext pageContext = _jspx_page_context;
/* 704 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 706 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 707 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 708 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*     */     
/* 710 */     _jspx_th_c_005fwhen_005f0.setTest("${productEdition!='CLOUD'}");
/* 711 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 712 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */       for (;;) {
/* 714 */         out.write("\n    if(divIDToBeShown == \"eventLogRule\")\n    {\n        document.getElementById(\"tab1-left\").className = \"tbSelected_Left\";\n        document.getElementById(\"tab1\").className = \"tbSelected_Middle\";\n        document.getElementById(\"tab1-right\").className = \"tbSelected_Right\";\n        document.getElementById(\"tab2-left\").className = \"tbUnSelected_Left\";\n        document.getElementById(\"tab2\").className = \"tbUnSelected_Middle\";\n        document.getElementById(\"tab2-right\").className = \"tbUnSelected_Right\";\n        showDiv(\"eventLogRuleDiv\");\n        hideDiv(\"windowsAzureLogRuleDiv\");\n    }\n    else\n    {\n        document.getElementById(\"tab2-left\").className = \"tbSelected_Left\";\n        document.getElementById(\"tab2\").className = \"tbSelected_Middle\";\n        document.getElementById(\"tab2-right\").className = \"tbSelected_Right\";\n        document.getElementById(\"tab1-left\").className = \"tbUnSelected_Left\";\n        document.getElementById(\"tab1\").className = \"tbUnSelected_Middle\";\n        document.getElementById(\"tab1-right\").className = \"tbUnSelected_Right\";\n");
/* 715 */         out.write("        showDiv(\"windowsAzureLogRuleDiv\");\n        hideDiv(\"eventLogRuleDiv\");\n    }\n");
/* 716 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 717 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 721 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 722 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 723 */       return true;
/*     */     }
/* 725 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 726 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 731 */     PageContext pageContext = _jspx_page_context;
/* 732 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 734 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 735 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 736 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 737 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 738 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */       for (;;) {
/* 740 */         out.write("\n\tdocument.getElementById(\"tab2-left\").className = \"tbSelected_Left\";\n        document.getElementById(\"tab2\").className = \"tbSelected_Middle\";\n        document.getElementById(\"tab2-right\").className = \"tbSelected_Right\";\n        document.getElementById(\"tab1-left\").className = \"tbUnSelected_Left\";\n        document.getElementById(\"tab1\").className = \"tbUnSelected_Middle\";\n        document.getElementById(\"tab1-right\").className = \"tbUnSelected_Right\";\n        showDiv(\"windowsAzureLogRuleDiv\");\n        hideDiv(\"eventLogRuleDiv\");\n\thideDiv(\"eventLogTabDiv\");\n    ");
/* 741 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 742 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 746 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 747 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 748 */       return true;
/*     */     }
/* 750 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 751 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 756 */     PageContext pageContext = _jspx_page_context;
/* 757 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 759 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 760 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 761 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f0);
/*     */     
/* 763 */     _jspx_th_c_005fout_005f0.setValue("${logCategoryName}");
/* 764 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 765 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 766 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 767 */       return true;
/*     */     }
/* 769 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 770 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 775 */     PageContext pageContext = _jspx_page_context;
/* 776 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 778 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 779 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 780 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f0);
/*     */     
/* 782 */     _jspx_th_c_005fout_005f1.setValue("${logCategoryName}");
/* 783 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 784 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 785 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 786 */       return true;
/*     */     }
/* 788 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 789 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 794 */     PageContext pageContext = _jspx_page_context;
/* 795 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 797 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 798 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 799 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f0);
/*     */     
/* 801 */     _jspx_th_c_005fout_005f2.setValue("${logCategoryName}");
/* 802 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 803 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 804 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 805 */       return true;
/*     */     }
/* 807 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 808 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 813 */     PageContext pageContext = _jspx_page_context;
/* 814 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 816 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 817 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 818 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f0);
/*     */     
/* 820 */     _jspx_th_c_005fout_005f3.setValue("${logCategoryName}");
/* 821 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 822 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 823 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 824 */       return true;
/*     */     }
/* 826 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 827 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 832 */     PageContext pageContext = _jspx_page_context;
/* 833 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 835 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 836 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 837 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 839 */     _jspx_th_tiles_005fput_005f1.setName("HelpContent");
/*     */     
/* 841 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/test.jsp");
/* 842 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 843 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 844 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 845 */       return true;
/*     */     }
/* 847 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 848 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 853 */     PageContext pageContext = _jspx_page_context;
/* 854 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 856 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 857 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 858 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 860 */     _jspx_th_tiles_005fput_005f2.setName("Footer");
/*     */     
/* 862 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/footer.jsp");
/* 863 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 864 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 865 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 866 */       return true;
/*     */     }
/* 868 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 869 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\EventLogRules_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */