/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class customizePlasmaView_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  21 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  30 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  34 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  35 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspDestroy() {}
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  45 */     HttpSession session = null;
/*     */     
/*     */ 
/*  48 */     JspWriter out = null;
/*  49 */     Object page = this;
/*  50 */     JspWriter _jspx_out = null;
/*  51 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  55 */       response.setContentType("text/html;charset=UTF-8");
/*  56 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  58 */       _jspx_page_context = pageContext;
/*  59 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  60 */       ServletConfig config = pageContext.getServletConfig();
/*  61 */       session = pageContext.getSession();
/*  62 */       out = pageContext.getOut();
/*  63 */       _jspx_out = out;
/*     */       
/*  65 */       out.write(10);
/*  66 */       out.write("\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n\n\n\n<html>\n<head>\n<SCRIPT LANGUAGE=\"Javascript1.2\">\n\n\n//Display tabs function\nfunction DisplayTabs()\n{\n\tvar colid= document.getElementById('col_id');\n\tvar noOfColumnCount = Get_Cookie('View_cols')\t// NO I18N\n\tcolid.selectedIndex=parseInt(noOfColumnCount-1);\n\tif(noOfColumnCount == 1){\n\t\tvar allmonitorcolumn = document.getElementById('allMonitors_colid_all');\n\t\tallmonitorcolumn.selectedIndex=parseInt(Get_Cookie('View_AllMonitor_cols')-1);\t// NO I18N\n\t\tdocument.getElementById(\"monitorColumnAllOption\").style.display=\"block\"\n\t\t\tdocument.getElementById(\"monitorColumnlimitedOption\").style.display=\"none\"\n\t}else{\n\t\tvar allmonitorcolumn = document.getElementById('allMonitors_colid_limited');\n\t\tallmonitorcolumn.selectedIndex=parseInt(Get_Cookie('View_AllMonitor_cols')-1);\t// NO I18N\n\t\tdocument.getElementById(\"monitorColumnAllOption\").style.display=\"none\"\n\t\t\tdocument.getElementById(\"monitorColumnlimitedOption\").style.display=\"block\"\n\t}\n\t\n    for(var i=1;i<9;i++) {\n");
/*  67 */       out.write("       /// Get the cookie here        \n       checkCookie=Get_Cookie('check'+i) ;\t\t// NO I18N\n       if(checkCookie!=null){        \n          // if CookieValue  = true  display tab else dont display tab\n          // Also  if CookieValue  = true  set checkbox as checked else unchecked.\n          // please note that here cookie value will be string so single quotes should be used for true or false\n          // in remaining cases it will be boolean value only\n          if(i == 1 && checkCookie != 'true'){\n        \t  checkAllMonitorOption(document.getElementById('check1'))\n          }\n          \tif(checkCookie == 'true')\n          \t{\n\t\t\t\tdocument.getElementById('check'+i).checked=true;\n       \t  \t\tif(i != 4 && i<5)\n       \t  \t\t{\n\t           \t\twindow.parent.document.getElementById('DRAG_'+i).style.display='block';\n       \t  \t\t}\n\t\t\t}\n           \telse \n           \t{\n\t\t\t\tif(i != 4 && i<5)\n\t\t\t\t{\n                \twindow.parent.document.getElementById('DRAG_'+i).style.display='none';\n        \t   \t}\n\t            document.getElementById('check'+i).checked=false;\n");
/*  68 */       out.write("           }          \n       }\n    }\n\n}\n\nfunction checkAllMonitorOption(obj){\n\tvar value = obj.checked\n\t\tif(!value){\n\t\t\tjQuery('#grayout').attr('class','disabledtext');\t// NO I18N\n\t\t\tjQuery('#allMonitors_colid_all').prop('disabled','disabled');\t// NO I18N\n\t\t\tjQuery('#allMonitors_colid_limited').prop('disabled','disabled');\t// NO I18N\n\t\t}else{\n\t\t\tjQuery('#grayout').attr('class','bodytext');\t// NO I18N\n\t\t\tjQuery('#allMonitors_colid_all').prop('disabled','');\t// NO I18N\n\t\t\tjQuery('#allMonitors_colid_limited').prop('disabled','');\t// NO I18N\n\t\t}\n\t\n}\nfunction updatedivs()\n{\n\tvar today = new Date();\n\tvar expires = new Date(today.getTime() + (365 * 86400000));\n\tvar columnCount = document.getElementById('col_id').value\n\tSet_Cookie('View_cols',columnCount,expires);\t// NO I18N\n\tif(columnCount == 1){\n\t\tSet_Cookie('View_AllMonitor_cols',document.getElementById('allMonitors_colid_all').value,expires);\t// NO I18N\n\t}else{\n\t\tSet_Cookie('View_AllMonitor_cols',document.getElementById('allMonitors_colid_limited').value,expires);\t// NO I18N\n");
/*  69 */       out.write("\t}\n\t\t\n\t\tvar checkid='';\n\t\tfor(var i=1;i<9;i++){\n\t\t\tcheckid= document.getElementById('check'+i); \n\t\t//set checkbox value into cookie\n\t\t\tSet_Cookie('check'+i, checkid.checked,expires);\t\t// NO I18N\n\t\t}\n\t//\tDisplayTabs();\n\t\treturn true;\n}\n\nfunction checkMonitorsColumn(value){\n\tif(value == 1 ){\n\t\tdocument.getElementById(\"monitorColumnAllOption\").style.display=\"block\";\n\t\tdocument.getElementById(\"monitorColumnlimitedOption\").style.display=\"none\";\n\t}else{\n\t\tdocument.getElementById(\"monitorColumnAllOption\").style.display=\"none\";\n\t\tdocument.getElementById(\"monitorColumnlimitedOption\").style.display=\"block\";\n\t}\n}\n\n</SCRIPT>\n\n</head>\t\t\n<body >\t\t\n\t\t\t\n<!-- ...Display all the tables with check boxes... -->\n\t\t\t<table  width=\"400\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  align=\"center\" style=\"padding-top:5px;padding-bottom:5px\">\t\n\t\t\t<tbody>\n\t\t\t\t<tr>\n\t\t\t\t\t<td nowrap heigth=\"28\" width=\"50%\" class=\"bodytext\" style=\"padding-top:5px;padding-bottom:5px\"><input name=\"s1\" id=\"check1\" onclick=\"checkAllMonitorOption(this)\" type=\"checkbox\" >\n");
/*  70 */       out.write("\t\t\t\t\t");
/*  71 */       out.print(FormatUtil.getString("am.monitortab.plasmaview.allmonitor.critical.text"));
/*  72 */       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr><td><table width=\"100%\">\n\t\t\t\t\t<tr id=\"grayout\">\n\t\t\t\t\t\t<td nowrap heigth=\"28\" width=\"60%\" style=\"padding-left: 20px\" class=\"bodytext\">");
/*  73 */       out.print(FormatUtil.getString("am.monitortab.plasmaview.noofcolumns.monitors.text"));
/*  74 */       out.write("</td>\n\t\t\t\t\t\t<td valign='top' align=left width=\"20\">\n\t\t\t\t\t\t <div id=\"monitorColumnAllOption\" style=\"display: none;\">\t<select id=\"allMonitors_colid_all\">\n\t\t\t\t\t\t\t\t<option value=\"1\">1</html:option>\n\t\t\t\t\t\t\t\t<option value=\"2\">2</html:option>\n\t\t\t\t\t\t\t\t<option value=\"3\">3</html:option>\n\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div id=\"monitorColumnlimitedOption\" style=\"display: none;\">\n\t\t\t\t\t\t\t<select id=\"allMonitors_colid_limited\">\n\t\t\t\t\t\t\t\t<option value=\"1\">1</html:option>\n\t\t\t\t\t\t\t\t<option value=\"2\">2</html:option>\n\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t</table></td></tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td nowrap heigth=\"28\" width=\"50%\" class=\"bodytext\" style=\"padding-top:5px;padding-bottom:5px\"><input name=\"s2\" id=\"check2\" type=\"checkbox\" >\n\t\t\t\t\t");
/*  75 */       out.print(FormatUtil.getString("am.monitortab.plasmaview.alert.text"));
/*  76 */       out.write("</td>\t\t\t\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td nowrap heigth=\"28\" width=\"50%\" class=\"bodytext\" style=\"padding-top:5px;padding-bottom:2px\"><input name=\"s3\" id=\"check3\" type=\"checkbox\" >\n\t\t\t\t\t");
/*  77 */       out.print(FormatUtil.getString("am.webclient.common.util.MGSTRs"));
/*  78 */       out.write("</td>\t\t\t\n\t\t\t\t</tr>\n\t\t\t\t");
/*  79 */       if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) {
/*  80 */         out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td nowrap heigth=\"28\" width=\"50%\" class=\"bodytext\" style=\"padding-top:5px;padding-bottom:2px\"><input name=\"s8\" id=\"check8\" type=\"checkbox\" >\n\t\t\t\t\t\t");
/*  81 */         out.print(FormatUtil.getString("am.monitortab.plasmaview.jump.icon.text"));
/*  82 */         out.write("\n\t\t\t\t\t</td>\t\t\t\n\t\t\t\t</tr>\n\t\t\t\t");
/*     */       }
/*  84 */       out.write(" \n\t\t\t\t<tr>\n\t\t\t\t\t<td nowrap heigth=\"28\" width=\"50%\" class=\"bodytext\" style=\"padding-top:5px;padding-bottom:2px\">\n\t\t\t\t\t\t<input name=\"s4\" id=\"check4\" type=\"checkbox\" class=\"checkBoxCSS\">\n\t\t\t\t\t\t<label for=\"check4\" class=\"checkBoxCSS\">");
/*  85 */       out.print(FormatUtil.getString("am.monitortab.plasmaview.audioalert.enable.text"));
/*  86 */       out.write("</label>\n\t\t\t\t\t</td>\t\t\t\n\t\t\t\t</tr>\n\t\t\t\t<tr><td style=\"padding-top:5px;padding-bottom:5px\"></td ></tr>\n\t\t\t\t<tr>\n\t\t\t\t<td>\n\t\t\t\t<div id=\"alarmSoundDiv\" style=\"display: none;\">\n\t\t\t\t<table border=\"0\" cellpadding=\"3\" cellspacing=\"0\" >\n\t\t\t\t<tr>\n\t\t\t\t<td nowrap heigth=\"28\" width=\"50%\" class=\"bodytext\" style=\"padding-top:5px;padding-bottom:2px\"><input name=\"alarmLevel\" id=\"check5\" type=\"checkbox\" >\n\t\t\t\t\t<label for=\"check5\">");
/*  87 */       out.print(FormatUtil.getString("am.webclient.configurealert.monitorisdown"));
/*  88 */       out.write("</label></td>\n\t\t\t\t</tr>\t\n\t\t\t\t<tr>\n\t\t\t\t<td nowrap heigth=\"28\" width=\"50%\" class=\"bodytext\" style=\"padding-top:5px;padding-bottom:2px\"><input name=\"alarmLevel\" id=\"check6\" type=\"checkbox\" >\n\t\t\t\t\t<label for=\"check6\">");
/*  89 */       out.print(FormatUtil.getString("am.webclient.configurealert.criticalseverity"));
/*  90 */       out.write("</label></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td nowrap heigth=\"28\" width=\"50%\" class=\"bodytext\" style=\"padding-top:5px;padding-bottom:2px\"><input name=\"alarmLevel\" id=\"check7\" type=\"checkbox\" >\n\t\t\t\t\t<label for=\"check7\">");
/*  91 */       out.print(FormatUtil.getString("am.webclient.configurealert.warningseverity"));
/*  92 */       out.write("</label></td>\n\t\t\t\t</tr>\n\t\t\t\t</table>\t\t\n\t\t\t\t</div>\t\n\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr >\n\t\t\t\t<td>\n\t\t\t\t\t<table class=\"monitorinfoeven\" width=\"100%\" cellspacing=\"0\" border=\"0\" cellpadding=\"3\" align=\"left\" style=\"margin:0px 5px 5px 0px;\">\n\t\t\t\t\t<tbody>\n\t\t\t\t\t<tr >\n\t\t\t\t\t\t<td nowrap colspan=2 heigth=\"28\" width=\"50%\" class=\"bodytext\" >\n\t\t\t\t\t\t\t<strong>");
/*  93 */       out.print(FormatUtil.getString("am.monitortab.plasmaview.layout.info.text"));
/*  94 */       out.write("</strong>\n\t\t\t\t\t\t</td>\n\t\t\t\n\t\t\t\t\t</tr >  \t\t\n\t\t\t\t\t<tr >\n\t\t\t\t\t\t<td nowrap heigth=\"28\" width=\"60%\" class=\"bodytext\">");
/*  95 */       out.print(FormatUtil.getString("am.monitortab.plasmaview.noofcolumns.text"));
/*  96 */       out.write(" </td>\n\t\t\t\t\t\t<td valign='top' align=left width=\"20\">\n\t\t\t\t\t\t\t<select id=\"col_id\" onchange=\"checkMonitorsColumn(this.value)\">\n\t\t\t\t\t\t\t\t<option value=\"1\">1</html:option>\n\t\t\t\t\t\t\t\t<option value=\"2\">2</html:option>\n\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t</tbody>\n\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr valign=\"middle\">\n\t\t\t\t\t<td nowrap width=\"50%\" align=\"center\" style=\"padding-top:5px;padding-bottom:0px\" >\n\t\t\t\t\t<input type=\"button\" class=\"buttons\"  name=\"Save\" value=\"");
/*  97 */       out.print(FormatUtil.getString("am.webclient.common.save.text"));
/*  98 */       out.write("\"  onclick=\"javascript:updatedivs();closeDialog();window.location.reload();\" >\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t\t\n\t\t\t</tbody>\t\n\t\t\t</table>\n<!-- End...Display all the tables with check boxes... -->\n\t\n</body>\n<script>\n\n DisplayTabs();\n\n$(document).ready(function(){\n\tif(document.getElementById('check4').checked== true)\n\t{\n\t\t$('#alarmSoundDiv').slideDown(\"slow\"); //NO I18N\t\n\t}\n\t$('.checkBoxCSS').click(function() //NO I18N \n   \t{\n\t\tif($(this).attr(\"checked\"))\n\t\t{\n\t\t\t$('#alarmSoundDiv').slideDown(\"slow\"); //NO I18N\n\t\t}\n\t\telse\n\t\t{\n\t\t\t//toggleChecked(false);\n\t\t\t$('#alarmSoundDiv').slideUp(\"slow\"); //NO I18N\n\t\t}\n\t})\n\t}\n);\n\n</script>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 100 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 101 */         out = _jspx_out;
/* 102 */         if ((out != null) && (out.getBufferSize() != 0))
/* 103 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 104 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 107 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\customizePlasmaView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */