/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.NotPresentTag;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class EditEumDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  32 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  36 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  37 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  40 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  44 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  45 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  46 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  53 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  56 */     JspWriter out = null;
/*  57 */     Object page = this;
/*  58 */     JspWriter _jspx_out = null;
/*  59 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  63 */       response.setContentType("text/html;charset=UTF-8");
/*  64 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  66 */       _jspx_page_context = pageContext;
/*  67 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  68 */       ServletConfig config = pageContext.getServletConfig();
/*  69 */       session = pageContext.getSession();
/*  70 */       out = pageContext.getOut();
/*  71 */       _jspx_out = out;
/*     */       
/*  73 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  74 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  76 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*     */       
/*  78 */       String agentName = request.getParameter("agentName");
/*  79 */       String agentId = request.getParameter("agentId");
/*  80 */       String agentIp = request.getParameter("agentIp");
/*     */       
/*  82 */       out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\nvar http;\nvar isUpdateClicked = false;\nfunction submitForm(){\n\tvar name=document.getElementById(\"agentName\").value;\n\tvar ip=document.getElementById(\"agentIp\").value;\n\tvar oldName=document.getElementById(\"oldName\").value;\n\tdocument.getElementById('showMessage').style.display='none';\n\t");
/*  83 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/*  85 */       out.write(10);
/*  86 */       out.write(9);
/*     */       
/*  88 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  89 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  90 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */       
/*  92 */       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  93 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  94 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */         for (;;) {
/*  96 */           out.write("\n\tif(name==\"\"){\n\t\talert('");
/*  97 */           out.print(FormatUtil.getString("am.webclient.eumdashboard.edit.displayname.cantempty"));
/*  98 */           out.write("');\n\t  \treturn;\n\t}\n\tif(ip==\"\"){\n\t\talert('");
/*  99 */           out.print(FormatUtil.getString("am.webclient.eumdashboard.edit.valid.ip"));
/* 100 */           out.write("');\n\t  \treturn;\n\t}\n\tif(validateIPAddress(ip)){\n\t\thttp=getHTTPObject();\n\t\tURL=\"/showAgent.do?method=editAgentDetails&agentId=");
/* 101 */           out.print(agentId);
/* 102 */           out.write("&agentIp=\"+ip+\"&agentName=\"+name+\"&oldName=\"+oldName;// No I18N\n\t\thttp.open(\"GET\",URL,true);\n\t\thttp.onreadystatechange = function(){\n\t\t\tif(http.readyState == 4 && http.status == 200){\n\t\t\t\tvar result = http.responseText;\n\t\t\t\tif(result=='Success'){\n\t\t\t\t\tisUpdateClicked = true;\n\t\t\t\t\tdocument.getElementById('msg-content').innerHTML='");
/* 103 */           out.print(FormatUtil.getString("am.webclient.eumdashboard.edit.msgsuccess"));
/* 104 */           out.write("';\n\t\t\t\t\tdocument.getElementById('msgImg').innerHTML='<img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\">';\n\t\t\t\t\tdocument.getElementById('msg-table').className = 'messagebox';\n\t\t\t\t\tdocument.getElementById('showMessage').style.display='block';\n\t\t\t\t}\n\t\t\t\telse if(result=='Failure'){\n\t\t\t\t\tdocument.getElementById('msg-content').innerHTML='");
/* 105 */           out.print(FormatUtil.getString("am.webclient.eumdashboard.edit.msgfailure"));
/* 106 */           out.write("';\n\t\t\t\t\tdocument.getElementById('msgImg').innerHTML='<img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\">';\n\t\t\t\t\tdocument.getElementById('msg-table').className = 'messageboxfailure';\n\t\t\t\t\tdocument.getElementById('showMessage').style.display='block';\n\t\t\t\t}\n\n\t\t\t}\n\t\t};\n\t\thttp.send(null);\n\n\n\t\t//document.AMActionForm.action=\"/editAgent.do?method=editAgentDetails&agentId=");
/* 107 */           out.print(agentId);
/* 108 */           out.write("\";\n\t\t//document.AMActionForm.submit();\n\t}\n\telse{\n\t\talert('");
/* 109 */           out.print(FormatUtil.getString("am.webclient.eumdashboard.edit.valid.ip"));
/* 110 */           out.write("');\n\t  \treturn;\n\t}\n\t");
/* 111 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 112 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 116 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 117 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*     */       }
/*     */       else {
/* 120 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 121 */         out.write("\n\n}\n\nfunction closePopUp()\n{\n\tif(isUpdateClicked)\n\t{\n\t\tcloseDialogandReloadParent();\n\t}\n\telse\n\t{\n\t\tcloseDialog();\n\t}\n}\n\nfunction AjaxCall(){\n\talert(http.readyState);\n\tif(http.readyState==4){\n\n\n\t}\n}\nfunction validateIPAddress (IPvalue)\n{\n\tvar re = /^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$/;\n\tif (re.test(IPvalue))\n\t{\n\t\tvar parts = IPvalue.split(\".\");\n\t\tif (parseInt(parseFloat(parts[0])) == 0)\n\t\t{\n\t\t\treturn false;\n\t\t}\n\t\tfor (var i=0; i<parts.length; i++)\n\t\t{\n\t\t\tif (parseInt(parseFloat(parts[i])) > 255)\n\t\t\t{\n\t\t\t\treturn false;\n\t\t\t}\n\t\t}\n\t\treturn true;\n\t}\n\telse\n\t{\n\t\treturn false;\n\t}\n}\n\n</script>\n<title>");
/* 122 */         out.print(FormatUtil.getString("am.webclient.eumdashboard.edit.locatiodetails"));
/* 123 */         out.write("</title>\n<body onLoad=\"javascript:doInitStuffOnBodyLoad()\" style=\"background-color:white\"></body>\n<form  action=\"/editAgent\" method=\"post\" name=\"AMActionForm\" style=\"display:inline\" >\n<input type=\"hidden\" Name=\"oldName\" id=\"oldName\" value=\"");
/* 124 */         out.print(agentName);
/* 125 */         out.write("\"/>\n\n\n<div id=\"showMessage\" style=\"display:none\">\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t\t<td height=\"24\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table id=\"msg-table\" width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"5%\" align=\"center\"><span id=\"msgImg\"></span></td>\n\t\t\t\t\t<td width=\"95%\" height=\"28\" class=\"message\"><span id=\"msg-content\"></span></td>\n\t\t\t\t</tr>\n\t\t\t</table><br>\n\t\t</td>\n\t</tr>\n\t</table>\n</div>\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:white; padding-top:10px;\">\n\t<tbody>\n\t\t<tr>\n\t\t\t<td width=\"14\" class=\"topleftcurveImg\"  nowrap=\"nowrap\"></td>\n\t\t\t<td class=\"topbgImg\"></td>\n\t\t\t<td width=\"14\" class=\"toprightcurveImg\"  nowrap=\"nowrap\"></td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"leftbgImg\"><img src=\"/images/spacer.gif\"></img></td>\n\t\t\t<td>\n\t\t\t\t<table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tbody>\n\t\t\t\t\t<tr class=\"DowntimeTableColumn\">\n\t\t\t\t\t\t<td width=\"35%\"><span class=\"conf-mon-txt\" style=\"padding-left:15px;\">");
/* 126 */         out.print(FormatUtil.getString("am.webclient.eumdashboard.edit.details"));
/* 127 */         out.write("</span></td>\n\t\t\t\t\t\t<td width=\"55%\"><img src=\"/images/spacer.gif\"></img></td>\n\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr class=\"datatdborder1\">\n\t\t\t\t\t\t<td><span class=\"bodytext\" style=\"padding-left:15px;\">");
/* 128 */         out.print(FormatUtil.getString("am.webclient.eumdashboard.edit.displayname"));
/* 129 */         out.write("</span></td>\n\t\t\t\t\t\t<td><input type=\"text\" Name=\"agentName\" id=\"agentName\" value=\"");
/* 130 */         out.print(agentName);
/* 131 */         out.write("\" class=\"formtext\"/></td>\n\n\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr class=\"datatdborder1\">\n\t\t\t\t\t\t<td><span class=\"bodytext\" style=\"padding-left:15px;\">");
/* 132 */         out.print(FormatUtil.getString("am.webclient.eumdashboard.edit.ip"));
/* 133 */         out.write("</span></td>\n\t\t\t\t\t\t<td><input type=\"text\" Name=\"agentIp\" id=\"agentIp\" value=\"");
/* 134 */         out.print(agentIp);
/* 135 */         out.write("\" class=\"formtext\"/></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr height=\"5px\">\n\t\t\t\t\t\t<td><img src=\"/images/spacer.gif\"></img></td>\n\t\t\t\t\t\t<td><img src=\"/images/spacer.gif\"></img></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr class=\"DowntimeTableColumn\">\n\t\t\t\t\t\t<td align=\"center\" colspan=\"2\" style=\"padding-left:50px;\">\n\t\t\t\t\t\t\t<input name=\"Submit\" type=\"button\" class=\"buttons\" value='");
/* 136 */         out.print(FormatUtil.getString("am.webclient.eumdashboard.edit.apply"));
/* 137 */         out.write("' onClick=\"javascript:submitForm();\">\n\t\t\t\t\t\t\t<input name=\"GoBack\" type=\"button\" class=\"buttons\" value='");
/* 138 */         out.print(FormatUtil.getString("am.webclient.eumdashboard.edit.close"));
/* 139 */         out.write("' onclick=\"closePopUp();\">&nbsp;&nbsp;\n\t\t\t\t\t\t</td>\n\n\t\t\t\t\t</tr>\n\t\t\t\t</tbody>\n\t\t\t\t</table>\n\n\t\t\t</td>\n\t\t\t<td valign=\"top\" class=\"rightbgImg\"></td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td width=\"14\" class=\"bottomleftcurveImg\"></td>\n\t\t\t<td class=\"bottombgImg\"></td>\n\t\t\t<td class=\"bottomrightcurveImg\"></td>\n\t\t</tr>\n\t</tbody>\n</table>\n</form>\n</body>\n");
/*     */       }
/* 141 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 142 */         out = _jspx_out;
/* 143 */         if ((out != null) && (out.getBufferSize() != 0))
/* 144 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 145 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 148 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 154 */     PageContext pageContext = _jspx_page_context;
/* 155 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 157 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 158 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 159 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 161 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 163 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 164 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 165 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 166 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 167 */       return true;
/*     */     }
/* 169 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 170 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 175 */     PageContext pageContext = _jspx_page_context;
/* 176 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 178 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 179 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 180 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 182 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 183 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 184 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 186 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 187 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 188 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 192 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 193 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 194 */       return true;
/*     */     }
/* 196 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 197 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\EditEumDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */