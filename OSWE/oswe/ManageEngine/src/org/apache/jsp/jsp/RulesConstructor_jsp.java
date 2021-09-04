/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class RulesConstructor_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  31 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  35 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  36 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  46 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  49 */     JspWriter out = null;
/*  50 */     Object page = this;
/*  51 */     JspWriter _jspx_out = null;
/*  52 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  56 */       response.setContentType("text/html; charset=ISO-8859-1");
/*  57 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  59 */       _jspx_page_context = pageContext;
/*  60 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  61 */       ServletConfig config = pageContext.getServletConfig();
/*  62 */       session = pageContext.getSession();
/*  63 */       out = pageContext.getOut();
/*  64 */       _jspx_out = out;
/*     */       
/*  66 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  67 */       String attribute = request.getParameter("attribute");
/*  68 */       String ruleSeverity = request.getParameter("severity");
/*     */       
/*  70 */       out.write("\n\n\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n<title>Rule Template</title>\n\n<script type=\"text/javascript\">\n\n\n\tvar attribute='");
/*  71 */       out.print(attribute);
/*  72 */       out.write("'; //No I18N\n\n\tfunction loadImage(count_Severity)\n\t{\n\t\tdocument.getElementById(\"selectRuleType\"+count_Severity).innerHTML=\"<img src='/images/add-selectedMonitors.gif' onclick='PopUpSelectMonitors(getRowId(this.parentNode.parentNode.parentNode))' style='cursor:pointer' onMouseOver='showSelectedMonitors(getRowId(this.parentNode.parentNode.parentNode),this,event)' onmouseout='hideddrivetip()'>\"  //No I18N\n\t}\n\t\n\tfunction loadSelectedMonitors(selectedMonitors,countSeverity)\n\t{\n\t\tvar selecteMonitor=selectedMonitors.split(\",\"); //No I18N\n\t\tfor(i=0;i<selecteMonitor.length;i++)\n\t\t{\n\t\t\t\tif(selecteMonitor[i].trim()!='')\n\t\t\t\t{\n\t\t\t\t\tvar tempStr=\"<input type='hidden' name='selectedMonitors\"+countSeverity+\"' value='\"+selecteMonitor[i]+\"'>\"; //No I18N\n\t\t\t\t\tdocument.getElementById('selectedMon'+countSeverity).innerHTML+=tempStr; //No I18N\n\t\t\t\t}\n\t\t}\n\t}\t\n\t\t\n\tfunction loadRules(ruleType,monitorCount,conditionAttribute,conditionSeverity,selectedMonitors,count,severity)\n\t{\n\t\tvar countSeverity=count+'_'+severity; //No I18N\n\t\tif(ruleType==0)\n");
/*  73 */       out.write("\t\t{\n\t\t\tdocument.getElementById(\"selectMonType\"+countSeverity)[0].selected=true;\n\t\t\tdocument.getElementById(\"count\"+countSeverity).value=monitorCount;\n\t\t\tdocument.getElementById(\"count\"+countSeverity).disabled=false;\n\t\t}\n\t\tif(ruleType==2)\n\t\t{\n\t\t\tdocument.getElementById(\"selectRuleType\"+countSeverity).innerHTML+=\"%\" \n\t\t\tdocument.getElementById(\"selectMonType\"+countSeverity)[3].selected=true;\n\t\t\tdocument.getElementById(\"count\"+countSeverity).value=monitorCount;\n\t\t\tdocument.getElementById(\"count\"+countSeverity).disabled=false;\n\t\t\t\n\t\t}\t\t\n\t\telse if(ruleType==-1)\n\t\t{\n\t\t\tdocument.getElementById(\"selectMonType\"+countSeverity)[1].selected=true;\n\t\t\tdocument.getElementById(\"selectRuleType\"+countSeverity).innerHTML=\"\" //No I18N\n\t\t}\n\t\telse if(ruleType==1)\n\t\t{\n\t\t\tdocument.getElementById(\"selectMonType\"+countSeverity)[2].selected=true;\n\t\t\tloadImage(countSeverity)\n\t\t}\n\t\t\n\n\t\tif(conditionAttribute==1)\n\t\t{\n\t\t\tdocument.getElementById(\"conditionAttribute\"+countSeverity)[0].selected=true;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.getElementById(\"conditionAttribute\"+countSeverity)[1].selected=true;\n");
/*  74 */       out.write("\t\t}\n\n\t\tchangeAttribute(countSeverity,attribute,conditionSeverity);\n\t\tloadSelectedMonitors(selectedMonitors,countSeverity)\n\t\tdocument.getElementById('totalRowCount'+severity).value=++count;\n\t}\n\tfunction changeAttribute(countSeverity,attribute,conditionSeverity)\n\t{\n\t\tif(document.getElementById(\"conditionAttribute\"+countSeverity).value==1)\n\t\t{\n\t\t\tdocument.getElementById(\"conditionSeverity\"+countSeverity)[0].text='");
/*  75 */       out.print(FormatUtil.getString("Down"));
/*  76 */       out.write("'; //No I18N\n\t\t\tdocument.getElementById(\"conditionSeverity\"+countSeverity)[0].selected=true;\n\t\t\tdocument.getElementById(\"conditionSeverity\"+countSeverity)[1]=null; //No I18N\n\t\t}\n\t\telse //No I18N\n\t\t{\n\t\t\tdocument.getElementById(\"conditionSeverity\"+countSeverity)[0].text='");
/*  77 */       out.print(FormatUtil.getString("Critical"));
/*  78 */       out.write("'; //No I18N\n\t\t\tdocument.getElementById(\"conditionSeverity\"+countSeverity)[1]=new Option('");
/*  79 */       out.print(FormatUtil.getString("Warning"));
/*  80 */       out.write("','4'); //No I18N\n\t\t\tif(conditionSeverity==4)\n\t\t\t{\n\t\t\t\tdocument.getElementById(\"conditionSeverity\"+countSeverity)[1].selected=true;\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\tdocument.getElementById(\"conditionSeverity\"+countSeverity)[0].selected=true;\n\t\t\t}\n\t\t}\n\t}\n\t\nfunction showSelectedMonitors(countVsSeverity,ele,event)\n{\n  var allSelectedMonitors=document.getElementsByName(\"selectedMonitors\"+countVsSeverity);\n  var allMonitorsValue=new Array();\n  for(i=0;i<allSelectedMonitors.length;i++)\n  {\n  \tallMonitorsValue[i]=allSelectedMonitors[i].value;\n  }\n$.ajax({  //No I18N\n  async: false, //No I18N\n  cache: false, //No I18N\n  type: \"POST\", //No I18N\n  url: \"/showresource.do\", //No I18N\n  data: {method:\"getSelectedMonitors\",allSelectedMonitors:allMonitorsValue+\"\"}, //No I18N\n  success: function(data){ddrivetip(this,event,data,null,true,'#000000')} //No I18N\n  });\n}\n\t\n\t\n</script>\n<style>\n\t.noshow_obj{\n\t\tdisplay:none;\n\t}\n\t.show_obj{\n\t\tfont-family: Arial, Helvetica, sans-serif, ;\n\t\tfont-size: 11px;\n\t\tcolor: #000000;\n\t\tdisplay:block;\n\t}\n</style>\n");
/*  81 */       out.write("</head>\n\t<body >\n\t<input type='hidden' name='totalRowCount");
/*  82 */       out.print(ruleSeverity);
/*  83 */       out.write("' id='totalRowCount");
/*  84 */       out.print(ruleSeverity);
/*  85 */       out.write("' value='1'>\n\t\t<table border='0' cellspacing='5' cellpadding='5' align='center'>\n\t\t\t<tbody id='ruleTable");
/*  86 */       out.print(ruleSeverity);
/*  87 */       out.write("'>\n        ");
/*     */       
/*  89 */       String haid = request.getParameter("haid");
/*  90 */       String entityId = null;
/*  91 */       int selectedMonitorType = 0;
/*  92 */       ArrayList<HashMap> allRules = com.adventnet.appmanager.fault.RuleAnalyser.getRCARules(haid, ruleSeverity, attribute);
/*  93 */       String conditionType = null;
/*  94 */       String ruleType = null;
/*  95 */       for (int i = 0; i < allRules.size(); i++)
/*     */       {
/*  97 */         HashMap ruleInfo = (HashMap)allRules.get(i);
/*  98 */         String monitorCount = (String)ruleInfo.get("NO_OF_MONITORS");
/*  99 */         ruleType = (String)ruleInfo.get("RULE_TYPE");
/* 100 */         conditionType = (String)ruleInfo.get("MONITOR_CONDITION");
/* 101 */         String conditionAttribute = (String)ruleInfo.get("CONDITION_ATTRIBUTE");
/* 102 */         String conditionSeverity = (String)ruleInfo.get("CONDITION_SEVERITY");
/* 103 */         HashMap<String, String> selectedMonitors = (HashMap)ruleInfo.get("SELECTED_MONITORS");
/* 104 */         String[] selectedMOInfo = { (String)selectedMonitors.get("resourceIds"), (String)selectedMonitors.get("resourceNames"), (String)selectedMonitors.get("imagePaths") };
/* 105 */         entityId = i + "_" + ruleSeverity;
/* 106 */         if (monitorCount.equals("-1"))
/*     */         {
/* 108 */           selectedMonitorType = 1;
/*     */         }
/*     */         
/* 111 */         out.write("\n        \t\n        \t<input type='hidden' name='moCount");
/* 112 */         out.print(i);
/* 113 */         out.write(95);
/* 114 */         out.print(ruleSeverity);
/* 115 */         out.write("' id='moCount");
/* 116 */         out.print(i);
/* 117 */         out.write(95);
/* 118 */         out.print(ruleSeverity);
/* 119 */         out.write("' value='");
/* 120 */         out.print(monitorCount);
/* 121 */         out.write("'>\n        \n\t\t\t\t<tr id='ruleRow'>\n\t\t\t\t\t<td class='bodytext'nowrap>\n\t\t\t\t\t\t");
/* 122 */         out.print(FormatUtil.getString("am.webclient.if.text"));
/* 123 */         out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td>\n\t\t\t\t\t\t\t<select name=\"selectMonType");
/* 124 */         out.print(i);
/* 125 */         out.write(95);
/* 126 */         out.print(ruleSeverity);
/* 127 */         out.write("\" id=\"selectMonType");
/* 128 */         out.print(i);
/* 129 */         out.write(95);
/* 130 */         out.print(ruleSeverity);
/* 131 */         out.write("\" class='bodytext' onchange='loadRuleType(getRowId(this.parentNode.parentNode))'>\n\t\t\t\t\t\t\t\t<option value='0'> ");
/* 132 */         out.print(FormatUtil.getString("Any"));
/* 133 */         out.write(" </option>\n\t\t\t\t\t\t\t\t<option value='-1'> ");
/* 134 */         out.print(FormatUtil.getString("All"));
/* 135 */         out.write(" </option>\n\t\t\t\t\t\t\t\t<option value='1'> ");
/* 136 */         out.print(FormatUtil.getString("Selected"));
/* 137 */         out.write(" </option>\n\t\t\t\t\t\t\t\t<option value='2'> ");
/* 138 */         out.print(FormatUtil.getString("Percentage"));
/* 139 */         out.write(" </option>\n\t\t\t\t\t\t\t</select>\n\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t<td class='bodytext' align='center' nowrap=\"\">\n\t\t\t\t\t\t<div id='selectRuleType");
/* 140 */         out.print(i);
/* 141 */         out.write(95);
/* 142 */         out.print(ruleSeverity);
/* 143 */         out.write("'>\n\t\t\t\t\t\t\t<input type=\"text\" name=\"count");
/* 144 */         out.print(i);
/* 145 */         out.write(95);
/* 146 */         out.print(ruleSeverity);
/* 147 */         out.write("\" id=\"count");
/* 148 */         out.print(i);
/* 149 */         out.write(95);
/* 150 */         out.print(ruleSeverity);
/* 151 */         out.write("\" class='bodytext' maxLength='4' size='2'>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t<td class='bodytext' nowrap>\n\t\t\t\t\t\t");
/* 152 */         out.print(FormatUtil.getString("am.webclient.rule.monitors"));
/* 153 */         out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t<td>\n\t\t\t\t\t\t\t<select name=\"conditionAttribute");
/* 154 */         out.print(i);
/* 155 */         out.write(95);
/* 156 */         out.print(ruleSeverity);
/* 157 */         out.write("\" id=\"conditionAttribute");
/* 158 */         out.print(i);
/* 159 */         out.write(95);
/* 160 */         out.print(ruleSeverity);
/* 161 */         out.write("\" class='bodytext' onchange='changeAttribute(getRowId(this.parentNode.parentNode),");
/* 162 */         out.print(attribute);
/* 163 */         out.write(")'>\n\t\t\t\t\t\t\t\t<option value='1'> ");
/* 164 */         out.print(FormatUtil.getString("Availability"));
/* 165 */         out.write(" </option>\n\t\t\t\t\t\t\t\t<option value='2'> ");
/* 166 */         out.print(FormatUtil.getString("Health"));
/* 167 */         out.write(" </option>\n\t\t\t\t\t\t\t</select>\n\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t<td class='bodytext' nowrap>\n\t\t\t\t\t\t");
/* 168 */         out.print(FormatUtil.getString("am.webclient.is.text"));
/* 169 */         out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t\n\t\t\t\t\t<td>\n\t\t\t\t\t\t<div id='selectSeverity'>\n\t\t\t\t\t\t\t<select name=\"conditionSeverity");
/* 170 */         out.print(i);
/* 171 */         out.write(95);
/* 172 */         out.print(ruleSeverity);
/* 173 */         out.write("\" id=\"conditionSeverity");
/* 174 */         out.print(i);
/* 175 */         out.write(95);
/* 176 */         out.print(ruleSeverity);
/* 177 */         out.write("\" class='bodytext' style=\"width:75px;\">\n\t\t\t\t\t\t\t\t\t<option value='1' selected> ");
/* 178 */         out.print(FormatUtil.getString("Critical"));
/* 179 */         out.write(" </option>\n\t\t\t\t\t\t\t\t\t<option value='4'> ");
/* 180 */         out.print(FormatUtil.getString("Warning"));
/* 181 */         out.write(" </option>\n\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/* 182 */         if (i < allRules.size() - 1) {
/* 183 */           out.write(" \n\t\t\t\t\t\n\t\t\t\t\t\t<td class='noshow_obj' nowrap >\n\t\t\t\t\t");
/*     */         }
/*     */         else {
/* 186 */           out.write("\n\t\t\t\t\t\t   \n\t\t\t\t\t   \t<td class='show_obj' nowrap >\n\t\t\t\t\t");
/*     */         }
/* 188 */         out.write("\n\t\t\t\t\t\t&nbsp;\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/* 189 */         if (i < allRules.size() - 1) {
/* 190 */           out.write(" \n\t\t\t\t\t\n\t\t\t\t\t\t<td class='show_obj' nowrap >\n\t\t\t\t\t");
/*     */         }
/*     */         else {
/* 193 */           out.write("\n\t\t\t\t\t\t   \n\t\t\t\t\t   \t<td class='noshow_obj' nowrap >\n\t\t\t\t\t");
/*     */         }
/* 195 */         out.write("\n\t\t\t\t\t\t\n\t\t\t\t\t\t ");
/* 196 */         out.print(FormatUtil.getString("am.webclient.OR.text"));
/* 197 */         out.write(" \n\t\t\t\t\t\n\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t<td>\n\t\t\t\t\t\t");
/* 198 */         if (i > 0) {
/* 199 */           out.write("\n\t\t\t\t\t\t<img src=\"/images/prereq_notconfigured.gif\" onclick=\"deleteRule(this.parentNode.parentNode,");
/* 200 */           out.print(ruleSeverity);
/* 201 */           out.write(")\">\n\t\t\t\t\t\t");
/*     */         } else {
/* 203 */           out.write("&nbsp;");
/*     */         }
/* 205 */         out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/* 206 */         if (i == allRules.size() - 1) {
/* 207 */           out.write(" \n\t\t\t\t\t\t\n\t\t\t\t\t\t<td class='show_obj' nowrap >\n\t\t\t\t\t\t\t\t\t\t");
/*     */         }
/*     */         else {
/* 210 */           out.write("\n\t\t\t\t\t\t<td class='noshow_obj' nowrap  > \n\t\t\t\t\t");
/*     */         }
/* 212 */         out.write("\n\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"addTemplate(this.parentNode.parentNode.parentNode,this.parentNode.parentNode,");
/* 213 */         out.print(ruleSeverity);
/* 214 */         out.write(")\" class=\"staticlinks\" nowrap>");
/* 215 */         out.print(FormatUtil.getString("webclient.common.searchcomponent.morebutton.text"));
/* 216 */         out.write("</a>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width='400px'>\n\t\t\t\t\t\t<input type='hidden' name='rowId' id='rowId' value=\"");
/* 217 */         out.print(i);
/* 218 */         out.write(95);
/* 219 */         out.print(ruleSeverity);
/* 220 */         out.write("\" >\n\t\t\t\t\t\t<input type='hidden' name='selectedMonitorType");
/* 221 */         out.print(i);
/* 222 */         out.write(95);
/* 223 */         out.print(ruleSeverity);
/* 224 */         out.write("' id='selectedMonitorType");
/* 225 */         out.print(i);
/* 226 */         out.write(95);
/* 227 */         out.print(ruleSeverity);
/* 228 */         out.write("' value='");
/* 229 */         out.print(selectedMonitorType);
/* 230 */         out.write("'>\n\t\t\t\t\t\t<input type='hidden' name='selectedMonitorCount");
/* 231 */         out.print(i);
/* 232 */         out.write(95);
/* 233 */         out.print(ruleSeverity);
/* 234 */         out.write("' id='selectedMonitorCount");
/* 235 */         out.print(i);
/* 236 */         out.write(95);
/* 237 */         out.print(ruleSeverity);
/* 238 */         out.write("' value='1'>\n\t\t\t\t\t\t<div id='selectedMon");
/* 239 */         out.print(i);
/* 240 */         out.write(95);
/* 241 */         out.print(ruleSeverity);
/* 242 */         out.write("' style=\"display:none\" />\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<script>loadRules('");
/* 243 */         out.print(ruleType);
/* 244 */         out.write(39);
/* 245 */         out.write(44);
/* 246 */         out.write(39);
/* 247 */         out.print(monitorCount);
/* 248 */         out.write(39);
/* 249 */         out.write(44);
/* 250 */         out.write(39);
/* 251 */         out.print(conditionAttribute);
/* 252 */         out.write(39);
/* 253 */         out.write(44);
/* 254 */         out.write(39);
/* 255 */         out.print(conditionSeverity);
/* 256 */         out.write(39);
/* 257 */         out.write(44);
/* 258 */         out.write(39);
/* 259 */         out.print((String)selectedMonitors.get("resourceIds"));
/* 260 */         out.write(39);
/* 261 */         out.write(44);
/* 262 */         out.write(39);
/* 263 */         out.print(i);
/* 264 */         out.write(39);
/* 265 */         out.write(44);
/* 266 */         out.write(39);
/* 267 */         out.print(ruleSeverity);
/* 268 */         out.write("')</script>\n\t\t\t\t");
/*     */       }
/* 270 */       out.write("\n\t\t\t</tbody>\n\t\t</table>\n<div style=\"display:none\">\t\n\n\t\t<table border='0' cellspacing='5' cellpadding='5'>\n\t\t\t<tbody id='TemplateTable");
/* 271 */       out.print(ruleSeverity);
/* 272 */       out.write("'>\n\t\t\t        \t\n\t\t\t\t<tr id='TemplateRow'>\n\t\t\t\t<input type='hidden' name='moCount' id='moCount' value='-1'>\n\t\t\t\t\t<td class='bodytext' nowrap>\n\t\t\t\t\t\t\t");
/* 273 */       out.print(FormatUtil.getString("am.webclient.if.text"));
/* 274 */       out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td>\n\t\t\t\t\t\t\t<select name=\"selectMonType\" id=\"selectMonType\" class='bodytext' onchange='loadRuleType(getRowId(this.parentNode.parentNode))'>\n\t\t\t\t\t\t\t\t<option value='0'> ");
/* 275 */       out.print(FormatUtil.getString("Any"));
/* 276 */       out.write(" </option>\n\t\t\t\t\t\t\t\t<option value='-1'> ");
/* 277 */       out.print(FormatUtil.getString("All"));
/* 278 */       out.write(" </option>\n\t\t\t\t\t\t\t\t<option value='1'> ");
/* 279 */       out.print(FormatUtil.getString("Selected"));
/* 280 */       out.write(" </option>\n\t\t\t\t\t\t\t\t<option value='2'> ");
/* 281 */       out.print(FormatUtil.getString("Percentage"));
/* 282 */       out.write(" </option>\n\t\t\t\t\t\t\t</select>\n\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t<td class='bodytext' align='center' nowrap=\"\">\n\t\t\t\t\t\t<div id='selectRuleType'>\n\t\t\t\t\t\t\t<input type=\"text\" name=\"count\" id=\"count\" class='bodytext' value='1' maxLength='4' size='2'>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t<td class='bodytext' nowrap>\n\t\t\t\t\t\t");
/* 283 */       out.print(FormatUtil.getString("am.webclient.rule.monitors"));
/* 284 */       out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t<td>\n\t\t\t\t\t\t\t<select name=\"conditionAttribute\" id=\"conditionAttribute\" class='bodytext' onchange='changeAttribute(getRowId(this.parentNode.parentNode),");
/* 285 */       out.print(attribute);
/* 286 */       out.write(") '>\n\t\t\t\t\t\t\t\t<option value='2' selected> ");
/* 287 */       out.print(FormatUtil.getString("Health"));
/* 288 */       out.write(" </option>\n\t\t\t\t\t\t\t\t<option value='1'> ");
/* 289 */       out.print(FormatUtil.getString("Availability"));
/* 290 */       out.write(" </option>\n\t\t\t\t\t\t\t</select>\n\t\t\t\t\t</td>\n\t\n\t\t\t\t\t<td class='bodytext' nowrap>\n\t\t\t\t\t\t");
/* 291 */       out.print(FormatUtil.getString("am.webclient.is.text"));
/* 292 */       out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t<td>\n\t\t\t\t\t\t<div id='selectSeverity'>\n\t\t\t\t\t\t\t<select name=\"conditionSeverity\" id=\"conditionSeverity\" class='bodytext' style=\"width:75px;\">\n\t\t\t\t\t\t\t\t\t<option value='1' selected> ");
/* 293 */       out.print(FormatUtil.getString("Critical"));
/* 294 */       out.write(" </option>\n\t\t\t\t\t\t\t\t\t<option value='4'> ");
/* 295 */       out.print(FormatUtil.getString("Warning"));
/* 296 */       out.write(" </option>\n\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class='show_obj' nowrap >\n\t\t\t\t\t\t&nbsp;\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class='noshow_obj' >\n\t\t\t\t\t\t");
/* 297 */       out.print(FormatUtil.getString("am.webclient.OR.text"));
/* 298 */       out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td  >\n\t\t\t\t\t\t<img src=\"/images/prereq_notconfigured.gif\" onclick=\"deleteRule(this.parentNode.parentNode,");
/* 299 */       out.print(ruleSeverity);
/* 300 */       out.write(")\">\n\t\t\t\t\t</td>\n\t\t\t\t\t\n\t\t\t\t\t<td nowrap class='show_obj' >\n\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"addTemplate(this.parentNode.parentNode.parentNode,this.parentNode.parentNode,");
/* 301 */       out.print(ruleSeverity);
/* 302 */       out.write(")\" class=\"staticlinks\">");
/* 303 */       out.print(FormatUtil.getString("webclient.common.searchcomponent.morebutton.text"));
/* 304 */       out.write("</a>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width='400px'>\n\t\t\t\t\t\t<input type='hidden' name='rowId' id='rowId'>\n\t\t\t\t\t\t<input type='hidden' name='selectedMonitorType' id='selectedMonitorType' value='0'>\n\t\t\t\t\t\t<input type='hidden' name='selectedMonitorCount' id='selectedMonitorCount' value='0'>\n\t\t\t\t\t\t<div id='selectedMon' style=\"display:none\" />\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</tbody>\n\t\t</table>\n</div>\t\t\n\t</body>\n</html>\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<script type=\"text/javascript\">\nvar jsCount=0;\nfunction addTemplate(node,currentnode,severity)\n{\n\tvar elements=node.getElementsByTagName('tr'); //No I18N\n\tvar templateTable=document.getElementById(\"TemplateTable\"+severity).cloneNode(true); //No I18N\n\tvar templateRow=templateTable.getElementsByTagName('tr'); //No I18N\n\tvar row=templateRow[0]; //No I18N\n\tvar select=row.getElementsByTagName('select'); //No I18N\n\tvar eleLength=elements.length\n\t\n\tfor(i=0;i<select.length;i++)\n\t{\n\t\tselect[i].setAttribute('name',select[i].getAttribute('name')+eleLength+'_'+severity); //No I18N\n");
/* 305 */       out.write("\t\tselect[i].setAttribute('id',select[i].getAttribute('id')+eleLength+'_'+severity); //No I18N\n\t}\n\tvar input=row.getElementsByTagName('input'); //No I18N\n\tfor(i=0;i<input.length;i++)\n\t{\n\t\tif(input[i].getAttribute('name')=='rowId')\n\t\t{\n\t\t\tinput[i].value=eleLength+'_'+severity; //No I18N\n\t\t}\n\t\telse //No I18N\n\t\t{\n\t\t\tinput[i].setAttribute('name',input[i].getAttribute('name')+eleLength+'_'+severity); //No I18N\n\t\t\tinput[i].setAttribute('id',input[i].getAttribute('id')+eleLength+'_'+severity); //No I18N\n\t\t}\n\t}\n\tvar divElement=row.getElementsByTagName('div'); //No I18N\n\tfor(i=0;i<divElement.length;i++)\n\t{\n\t\tdivElement[i].setAttribute('id',divElement[i].getAttribute('id')+eleLength+'_'+severity);\n\t}\n\tdocument.getElementById('totalRowCount'+severity).value=++eleLength;\n\tdocument.getElementById('ruleTable'+severity).appendChild(row); //No I18N\n\ttoglemoreLink(currentnode,'add');\n}\nfunction toglemoreLink(node,actionType){ \n\tif(actionType == 'add') {\n\t\tnode.cells[7].className='noshow_obj'; \n\t\tnode.cells[8].className='show_obj';  \n");
/* 306 */       out.write("\t\tnode.cells[10].className='noshow_obj';\n\t}\n\telse { \n\t\tnode.cells[7].className='show_obj'; \n\t\tnode.cells[8].className='noshow_obj'; \n\t\tnode.cells[10].className='show_obj';  \n\t}\n}\n\nfunction deleteRule(node,ruleSeverity)\n{ \t\t\n\tvar rowElements= node.parentNode.getElementsByTagName('tr'); \n\t\n\tif(node.cells[10].className == 'show_obj') {\n\t\n\t\ttoglemoreLink(node.parentNode.rows[rowElements.length-2], 'delete');\n\t}\n\telse {\n\t\n\t\ttoglemoreLink(node.parentNode.rows[rowElements.length-1], 'delete');\n\t}\n\tdocument.getElementById(\"ruleTable\"+ruleSeverity).removeChild(node); //No I18N\n}\n\nfunction loadRuleType(count_Severity)\n{\n\tif(document.getElementById(\"selectMonType\"+count_Severity).value==1)\n\t{\n\t\tloadImage(count_Severity);\n\t}\n\telse //No I18N\n\t{\n\t\tloadTextBox(count_Severity);\n\t}\t\n}\n\nfunction loadTextBox(count_Severity)\n{\n\t\n\tif(document.getElementById(\"selectMonType\"+count_Severity).value==0)\n\t{\n\t\tdocument.getElementById(\"selectRuleType\"+count_Severity).innerHTML=\"<input type='text' name='count\"+count_Severity+\"' id='count\"+count_Severity+\"' class='bodytext' maxLength='4' size='2' value='1'>\" //No I18N\n");
/* 307 */       out.write("\t}\n\telse if(document.getElementById(\"selectMonType\"+count_Severity).value==2)\n\t{\n\t\tdocument.getElementById(\"selectRuleType\"+count_Severity).innerHTML=\"<input type='text' name='count\"+count_Severity+\"' id='count\"+count_Severity+\"' class='bodytext' maxLength='4' size='2' value='100'>% \" //No I18N\n\t}\n\telse if(document.getElementById(\"selectMonType\"+count_Severity).value==-1)\n\t\t{\n\t\t\tdocument.getElementById(\"selectRuleType\"+count_Severity).innerHTML=\"\" //No I18N\n\t}\n\t\n}\n\nfunction PopUpSelectMonitors(countSeverity)\n{\n\tfnOpenNewWindow('/showresource.do?method=selectMonitors&haid=");
/* 308 */       out.print(haid);
/* 309 */       out.write("&attributetype=");
/* 310 */       out.print(attribute);
/* 311 */       out.write("&countSeverity='+countSeverity); //No I18N\n}\n\nfunction hideMoreLink(divElement)\n{\n\tdivElement.style.display='none'; //No I18N\n}\n\nfunction getRowId(node)\n{\n\tvar row=node.getElementsByTagName('input'); //No I18N\n\tfor(var k=0;k<row.length;k++)\n\t{\n\t\tif(row[k].getAttribute('name')=='rowId')\n\t\t{\n\t\t\treturn row[k].value; //No I18N\n\t\t}\n\t}\n}\n\n\n\n</script>\n");
/*     */     } catch (Throwable t) {
/* 313 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 314 */         out = _jspx_out;
/* 315 */         if ((out != null) && (out.getBufferSize() != 0))
/* 316 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 317 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 320 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\RulesConstructor_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */