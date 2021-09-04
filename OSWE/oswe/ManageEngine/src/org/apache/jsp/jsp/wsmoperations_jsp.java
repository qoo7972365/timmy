/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class wsmoperations_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  29 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*  30 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*  31 */     _jspx_dependants.put("/jsp/wsmbreadcrumb.jsp", Long.valueOf(1473429417000L));
/*     */   }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  42 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  46 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  50 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  54 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  55 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  56 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  63 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  66 */     JspWriter out = null;
/*  67 */     Object page = this;
/*  68 */     JspWriter _jspx_out = null;
/*  69 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  73 */       response.setContentType("text/html");
/*  74 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  76 */       _jspx_page_context = pageContext;
/*  77 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  78 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  79 */       session = pageContext.getSession();
/*  80 */       out = pageContext.getOut();
/*  81 */       _jspx_out = out;
/*     */       
/*  83 */       out.write("<!--$Id$-->\n");
/*     */       
/*  85 */       request.setAttribute("HelpKey", "Adding Web Service Operation");
/*     */       
/*  87 */       out.write("\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n");
/*  88 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  89 */       out.write("\n<style>\n.ui-widget-header { border: 0px solid #bbbbbb; background: #dadada url(/apminsight/style/images/ui-bg_gloss-wave_55_5c9ccc_500x100.png) 50% 50% repeat-x; font-weight: bold; color: #FFF; }\n</style>\n<script>\nfunction showArgs(oid,insid,hasargs)\n{\n\tMM_openBrWindow('/wsm.do?method=showargs&oid='+oid+'&insid='+insid,'Argument Info','width=400,height=300,top=200,left=300, scrollbars=yes');\n}\n\nfunction isOneSelected(tagname)\n{\n\tvar checks = document.getElementsByName(tagname);\n\tvar isonechecked = false;\n\tfor(var i=0;i<checks.length;i++)\n\t{\n\t\tif(checks[i].checked)\n\t\t{\n\t\t\tisonechecked = true;\n\t\t\tbreak;\n\t\t}\n\t}\n\treturn isonechecked;\n}\nfunction submitvalue(formtosubmit)\n{\n\t");
/*  90 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/*  92 */       out.write("\n\tvar operationid = document.manageoperation.operation.value\n\tvar operationName = document.getElementById('operationName').value\n\tvar soaprequest = document.getElementById(\"soapRequest\").value.trim()\n\tvar soapAction = document.getElementById('soapAction').value\n\tvar xsltInput = document.getElementById('XSLTInput').value\n\t\n\tvar custom = true\n\t\n\tif(operationName == null || operationName == '' )\n\t{\n\t\t\talert('");
/*  93 */       out.print(FormatUtil.getString("am.webclient.wsm.alert.operationname.text"));
/*  94 */       out.write("')\n\t\t\treturn\n\t}\n\tif(operationid == 0)\n\t{\n\t\tif(operationName == null || operationName == '' )\n\t\t{\n\t\t\talert('");
/*  95 */       out.print(FormatUtil.getString("am.webclient.wsm.alert.operationname.text"));
/*  96 */       out.write("')\n\t\t\tcustom = false\n\t\t\treturn\n\t\t}\n\n\t\tif(soaprequest == '' )\n\t\t{\n\t\t\talert('");
/*  97 */       out.print(FormatUtil.getString("am.webclient.wsm.alert.soaprequest.text"));
/*  98 */       out.write("')\n\t\t\tcustom = false\n\t\t}\n\t}\n\t\n\t\n\tif(operationid != '-1' && operationid != null && custom == true)\n\t{\n\t\tif(soaprequest == '' )\n\t\t{\n\t\t\talert('");
/*  99 */       out.print(FormatUtil.getString("am.webclient.wsm.alert.soaprequest.text"));
/* 100 */       out.write("')\n\t\t\treturn\n\t\t}\n\t\t\n\t\t\n\t\tif(formtosubmit == 1)\n\t\t{\n\t\t\t\n\t\t\tdocument.manageoperation.action=\"/wsm.do?method=saveoperation&value=one&resourceid=\"+");
/* 101 */       out.print(request.getParameter("resourceid"));
/* 102 */       out.write("\n\t\t\tdocument.manageoperation.submit()\n\t\t}\n\t\telse if(formtosubmit == 2){\n\t\t\t\n\t\t\tdocument.manageoperation.action=\"/wsm.do?method=saveoperation&value=two&resourceid=\"+");
/* 103 */       out.print(request.getParameter("resourceid"));
/* 104 */       out.write("\n\t\t\tdocument.manageoperation.submit()\n\t\t}\n\t\telse if(formtosubmit == 3) {\n\t\t\t\n\t\t\tvar dialogTitle = '");
/* 105 */       out.print(FormatUtil.getString("am.webclient.wsm.soapdetails.soapresponse"));
/* 106 */       out.write("'\n\t\t\tvar tag = $('<div></div>');\n\t\t\ttag.html('<table border=\"0\"  width=\"100%\" ><tr><td align=\"center\" height=\"300\"><img src=\"/images/loader.gif\" ></td></tr></table>').dialog({\n\t\t\t\tautoOpen: true,\tmodal: true, draggable: false, resizable: false,\n\t\t\t\tcloseText: 'Close',\tshow: 'fade', position:'center',  //No I18N\n\t\t\t\twidth: '650',\n\t\t\t\tminHeight : '350', \n\t\t\t\ttitle: dialogTitle,\n\t\t\t\topen: function()\n\t\t\t\t{\n\t\t\t\t\t$('.ui-dialog').css(\"padding\",\"0px\"); //No I18N\n\t\t\t\t\t$('.ui-dialog-titlebar').removeClass('ui-corner-all').addClass('ui-corner-top'); //No I18N\n\t\t\t\t},\n\t\t\t\tclose: function(event, ui) \n\t\t\t\t{\n\t\t\t\t\ttag.dialog('destroy').empty().hide(); //No I18N\n\t\t\t\t}\n        \n});\n\t\t\t\n\t\t\t$.post(\"/wsm.do?method=testOperation&resourceid=\"+");
/* 107 */       out.print(request.getParameter("resourceid"));
/* 108 */       out.write(", $(\"#manageoperation\").serialize(),function(data){\t// No I18N\n\t\t\t\t\n\t\t\tJSONObject = eval( \"(\" + data + \")\" );\n\t\t\tif(JSONObject.xsltOutput!=\"\")\n\t\t\t{\n\t\t\t\ttag.dialog({width:'1000'});\n\t\t\t\ttag.html('<table width=\"100%\" border=\"0\" class=\"bodytext\"><tr><td width=\"50%\"><table><tr><td class=\"tableheadingbborder\" width=\"20%\" valign=\"top\"><span class=\"bcactive\">SOAP Response : </span></td></tr><tr><td align=\"center\"><textarea rows=\"20\" cols=\"70\" accept=\"xml\"  id=\"soapRequest\" name=\"soapRequest\" style=\"resize: none; overflow-y: scroll;\">'+JSONObject.soapresponse+'</textarea></td></tr></table></td><td><table><tr><td class=\"tableheadingbborder\" width=\"20%\" valign=\"top\"><span class=\"bcactive\">XSLT Output : </span></td></tr><tr><td align=\"center\"><textarea style=\"resize: none; overflow-y: scroll;\" rows=\"20\" cols=\"70\" accept=\"xml\"  id=\"xsltOutput\" name=\"xsltOutput\">'+JSONObject.xsltOutput+'</textarea></td></tr></table></td></tr></table>') //No I18N\n\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\ttag.html('<table width=\"100%\" border=\"0\" class=\"bodytext\"><tr><td align=\"center\"><textarea style=\"border: none; resize: none\" rows=\"20\" cols=\"85\" accept=\"xml\"  id=\"soapRequest\" name=\"soapRequest\">'+JSONObject.soapresponse+'</textarea></td></tr></table>')\n");
/* 109 */       out.write("\t\t\t}\n\t\t\ttag.dialog({title:dialogTitle+' - '+JSONObject.operationName});\n\t\t\n\t\t\n\t\t});\n\t\n\t\t}\n\t}\n\telse if(operationid == -1)\n\t{\n\t\talert(\"");
/* 110 */       out.print(FormatUtil.getString("am.webclient.wsm.jsalertselectoperation.text"));
/* 111 */       out.write("\");\n\t\treturn;\n\t}\n\t\n\t\n}\n\nfunction showSOAPInfo(element)\n{\n\t\n\tvar opid = element.value\n\tif(opid == 0)\n\t{\n\t\tjQuery(\"#soapRequest\").val('');\t// NO I18N\n\t\tjQuery(\"#operationName\").val('');\t// NO I18N\n\t\tjQuery(\"#soapAction\").val('')\t// NO I18N\n\n\t}else if(opid == -1){\n\t\tdocument.getElementById('soapRequest').value='';\n\t}\n\telse\n\t{\n\t\t  URL='/wsm.do?method=getSOAPRequestInfo&operationID='+element.value;  //No I18N\n\t\t  http1=getHTTPObject();\n\t\t  http1.onreadystatechange = setSOAPValues;\n\t\t  http1.open(\"GET\", URL, true);\n\t\t  http1.send(null);\n\t\t  \n\t}\n\n}\n\nfunction setSOAPValues()\n{\n\tif(http1.readyState == 4 && http1.status == 200)\n\t{\n\t\tvar array=http1.responseText.split(\"#SOAP_SEPERATOR#\");\n\t\tif(array[0] != null && array[0] != 'null')\n\t\t{\n\t\t\tdocument.getElementById(\"soapAction\").value=array[0];\n\t\t}\n\t\telse\n\t\t{\n\t\tdocument.getElementById(\"soapAction\").value=''\n\t\t}\n\t\tdocument.getElementById(\"soapRequest\").value=array[1];\n\t}\n}\n\nfunction showXSLT()\n{\n\tjQuery('#XSLT').show(); //No I18N\n\tjQuery('#AddContentCheck').hide(); //No I18N\n");
/* 112 */       out.write("}\n\n</script>\n");
/*     */       
/* 114 */       ArrayList<Properties> operations = (ArrayList)request.getAttribute("operations");
/* 115 */       int opcount = ((Integer)request.getAttribute("count")).intValue();
/* 116 */       String disabled = "";
/* 117 */       String buttonclass = "buttons";
/* 118 */       if (opcount > 9) {
/* 119 */         disabled = "disabled";
/* 120 */         buttonclass = "";
/*     */       }
/*     */       
/* 123 */       out.write(10);
/* 124 */       out.write(10);
/* 125 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n  \n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n\t");
/*     */       
/* 127 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 128 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 129 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/* 131 */       _jspx_th_c_005fif_005f0.setTest("${param.method=='showdetails'}");
/* 132 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 133 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */         for (;;) {
/* 135 */           out.write("\t\n       <td class=\"bcsign\" height=\"22\">");
/* 136 */           out.print(BreadcrumbUtil.getMonitorsPage());
/* 137 */           out.write(" &gt; ");
/* 138 */           out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 139 */           out.write(" &gt; <span class=\"bcactive\">");
/* 140 */           out.print(request.getAttribute("displayname"));
/* 141 */           out.write(" </span></td>\n\t");
/* 142 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 143 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 147 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 148 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 151 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 152 */         out.write(10);
/* 153 */         out.write(9);
/*     */         
/* 155 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 156 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 157 */         _jspx_th_c_005fif_005f1.setParent(null);
/*     */         
/* 159 */         _jspx_th_c_005fif_005f1.setTest("${param.method=='manageoperations'}");
/* 160 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 161 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */           for (;;) {
/* 163 */             out.write("\n\t<td class=\"bcsign\" height=\"22\">\n      ");
/* 164 */             out.print(BreadcrumbUtil.getMonitorsPage());
/* 165 */             out.write(" &gt; ");
/* 166 */             out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 167 */             out.write(" &gt; <a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 168 */             if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*     */               return;
/* 170 */             out.write(34);
/* 171 */             out.write(62);
/* 172 */             out.print(request.getAttribute("displayname"));
/* 173 */             out.write("</a> &gt; <span class=\"bcactive\">");
/* 174 */             out.print(FormatUtil.getString("am.webclient.wsm.manageoperationsbc.text"));
/* 175 */             out.write("</span>\n      </td>\n\t");
/* 176 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 177 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 181 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 182 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*     */         }
/*     */         else {
/* 185 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 186 */           out.write(10);
/* 187 */           out.write(10);
/* 188 */           out.write(9);
/*     */           
/* 190 */           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 191 */           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 192 */           _jspx_th_c_005fif_005f2.setParent(null);
/*     */           
/* 194 */           _jspx_th_c_005fif_005f2.setTest("${param.method=='showoperations'}");
/* 195 */           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 196 */           if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */             for (;;) {
/* 198 */               out.write("\n\t<td class=\"bcsign\" height=\"22\">\n      ");
/* 199 */               out.print(BreadcrumbUtil.getMonitorsPage());
/* 200 */               out.write(" &gt; ");
/* 201 */               out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 202 */               out.write(" &gt; <a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 203 */               if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*     */                 return;
/* 205 */               out.write(34);
/* 206 */               out.write(62);
/* 207 */               out.print(request.getAttribute("displayname"));
/* 208 */               out.write("</a> &gt; <span class=\"bcactive\">");
/* 209 */               out.print(FormatUtil.getString("am.webclient.wsm.showoperationsbc.text"));
/* 210 */               out.write("</span>\n      </td>\n\t");
/* 211 */               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 212 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 216 */           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 217 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*     */           }
/*     */           else {
/* 220 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 221 */             out.write(10);
/* 222 */             out.write(9);
/*     */             
/* 224 */             IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 225 */             _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 226 */             _jspx_th_c_005fif_005f3.setParent(null);
/*     */             
/* 228 */             _jspx_th_c_005fif_005f3.setTest("${param.method=='getSOAPInfo'}");
/* 229 */             int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 230 */             if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */               for (;;) {
/* 232 */                 out.write("\n\t  <td class=\"bcsign\" height=\"22\">\n\t   \t");
/* 233 */                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 234 */                 out.write(" &gt; ");
/* 235 */                 out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 236 */                 out.write(" &gt; <a class=\"bcinactive\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 237 */                 if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*     */                   return;
/* 239 */                 out.write(34);
/* 240 */                 out.write(62);
/* 241 */                 out.print(request.getAttribute("displayname"));
/* 242 */                 out.write(" </a> &gt; <span class=\"bcactive\">");
/* 243 */                 out.print(request.getAttribute("operationName"));
/* 244 */                 out.write("</span>\n\t");
/* 245 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 246 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 250 */             if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 251 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*     */             }
/*     */             else {
/* 254 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 255 */               out.write("\n</tr>\n\t<tr>\n\t\t<td  class=\"bcstrip\" height=\"2\"><img src=\"../images/spacer.gif\" width=\"20\" height=\"2px\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\t\n");
/* 256 */               out.write("\n\n\n<form action=\"/wsm.do\" method=\"post\" id=\"manageoperation\" name=\"manageoperation\">\n<table border=\"0\" class=\" monitorinfoeven-actions\" cellspacing=\"0\"  cellpadding=\"0\"  width=\"99%\">\n\t<tr >\n\t\t<td style=\"padding:10px 0px 10px 15px\" width=\"25%\">\n\t\t\t<span class=\"bcactive\">");
/* 257 */               out.print(FormatUtil.getString("am.webclient.wsm.soapoperation.text"));
/* 258 */               out.write("</span>&nbsp;<span class=\"mandatory\">*</span>\n\t\t</td>\n\n\t\t<td>\n\t\t\t<select style=\"width:250px\" class=\"formtext\"  name=\"operation\" onchange=\"showSOAPInfo(this)\">\n\t\t\t\t<option value=-1>");
/* 259 */               out.print(FormatUtil.getString("am.webclient.wsm.selectoperation.text"));
/* 260 */               out.write("</option>\n\t\t\t");
/*     */               
/* 262 */               for (Properties prop : operations)
/*     */               {
/* 264 */                 String name = prop.getProperty("name");
/* 265 */                 String oid = prop.getProperty("oid");
/*     */                 
/* 267 */                 out.write("\n\t\t\t\t<option value=");
/* 268 */                 out.print(oid);
/* 269 */                 out.write(62);
/* 270 */                 out.print(name);
/* 271 */                 out.write("</option>\n\t\t\t");
/*     */               }
/*     */               
/*     */ 
/* 275 */               out.write("\n\t\t\t<option value=0>");
/* 276 */               out.print(FormatUtil.getString("am.webclient.wsm.customoperation.text"));
/* 277 */               out.write("</option>\n\t\t\t</select>\n\t\t</td>\n\t\t\n\t\t<td width=\"50%\" style=\"padding-right: 15px;\" align=\"right\">\n\t\t<a class=\"staticlinks\" href=\"/wsm.do?method=refreshoperations&resourceid=");
/* 278 */               out.print(request.getParameter("resourceid"));
/* 279 */               out.write(34);
/* 280 */               out.write(62);
/* 281 */               out.print(FormatUtil.getString("am.webclient.wsm.refreshoperations.text"));
/* 282 */               out.write("</a>\n\t\t&nbsp;|&nbsp;\n\t\t<a class=\"staticlinks\" onclick=\"MM_openBrWindow('/wsm.do?method=variableMethods','','resizable=yes,width=800,height=450,top=100,left=75,scrollbar=yes')\" href=\"javascript:void(0)\">");
/* 283 */               out.print(FormatUtil.getString("am.webclient.wsm.availablefunctions.text"));
/* 284 */               out.write("</a>\n\t\t</td>\n\t</tr>\n\t<tr id=\"custom\">\n\t\t \n\t\t\t\t\n\t\t<td style=\"padding: 10px 0px 10px 15px\"><span class=\"bcactive\">");
/* 285 */               out.print(FormatUtil.getString("am.webclient.wsm.soapname.text"));
/* 286 */               out.write("</span>&nbsp;<span class=\"mandatory\">*</span></td>\n\t\t<td colspan=\"2\"><input type=\"text\" class=\"formtext\" style=\"width:250px\" name=\"operationName\" id=\"operationName\"></td>\n\t\t\n\t\t \n\t\t\n\t</tr>\n\t<tr >\n\t\t<td style=\"padding:10px 0px 10px 15px\" >\n\t\t\t\t<span class=\"bcactive\">");
/* 287 */               out.print(FormatUtil.getString("am.webclient.wsm.soapaction.text"));
/* 288 */               out.write("</span>\n\t\t</td>\n\t\t<td colspan=\"2\">\n\t\t\t\t<input type=\"text\" class=\"formtext\" name=\"soapAction\" id=\"soapAction\" value=\"\" style=\"width:250px\">\n\t\t</td>\n\t</tr>\n\t<tr>\n\n\t<td style=\"padding:10px 0px 10px 15px\" valign=\"top\">\n\n\t\t\t\t\t<span class=\"bcactive\">");
/* 289 */               out.print(FormatUtil.getString("am.webclient.wsm.soaprequest.text"));
/* 290 */               out.write("</span>&nbsp;<span class=\"mandatory\">*</span>\n\t\t</td>\n\t\t<td style=\"padding-top: 10px\" colspan=\"2\">\n\t\t\t\t\t\t<textarea rows=\"20\" cols=\"80\" accept=\"xml\"  id=\"soapRequest\" name=\"soapRequest\">\n\t\t\t\t\t\t</textarea>\n\t\t</td>\n\t\t\n\n\n\t</tr>\n\t<tr id=\"XSLT\" style=\"display:none;\">\n\t\t<td style=\"padding:10px 0px 10px 15px\" valign=\"top\">\n\t\t\t<span class=\"bcactive\">");
/* 291 */               out.print(FormatUtil.getString("am.webclient.wsm.xsltInput.text"));
/* 292 */               out.write("</span>&nbsp;<span class=\"mandatory\">*</span>\n\t\t</td>\n\t\t<td style=\"padding-top: 10px\" colspan=\"2\">\n\t\t\t<textarea rows=\"20\" cols=\"80\" accept=\"xml\"  id=\"XSLTInput\" name=\"XSLTInput\">\n\t\t\t</textarea>\n\t\t</td>\n\t</tr>\n\n\t<tr>\n\t<td style=\"padding:10px 0px 10px 0px\" colspan=\"3\" align=\"center\" >\n\t\t\t<input type=\"button\" class=\"");
/* 293 */               out.print(buttonclass);
/* 294 */               out.write(34);
/* 295 */               out.write(32);
/* 296 */               out.print(disabled);
/* 297 */               out.write(" value=\"");
/* 298 */               out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 299 */               out.write("\" onclick=\"submitvalue(1)\"/>\n\t\t\t<input type=\"button\" class=\"");
/* 300 */               out.print(buttonclass);
/* 301 */               out.write(34);
/* 302 */               out.write(32);
/* 303 */               out.print(disabled);
/* 304 */               out.write(" value=\"");
/* 305 */               out.print(FormatUtil.getString("am.webclient.wsm.saveconfigure.button.text"));
/* 306 */               out.write("\" onclick=\"submitvalue(2)\" />\n\t\t\t<input type=\"button\" class=\"buttons\" value=\"");
/* 307 */               out.print(FormatUtil.getString("am.webclient.wsm.testoperation.text"));
/* 308 */               out.write("\" onClick=\"submitvalue(3)\"/>\n\t\t\t<input type=\"button\" id=\"AddContentCheck\" class=\"buttons\" value=\"");
/* 309 */               out.print(FormatUtil.getString("am.webclient.wsm.addxslt.text"));
/* 310 */               out.write("\" onClick=\"showXSLT()\"/>\n\t\t\t<input type=\"button\" class=\"buttons\" value=\"");
/* 311 */               out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 312 */               out.write("\" onclick=\"location.href='/wsm.do?resourceid=");
/* 313 */               out.print(request.getParameter("resourceid"));
/* 314 */               out.write("&method=showdetails'\"/>\n\t</td>\n\t</tr>\n\n</table>\n</form>\n\n\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  >\n\t\t\t<tr>\n\t\t\t<td class=\"helpCardHdrTopLeft\"/>\n\n\t\t\t<td class=\"helpCardHdrTopBg\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t\t\t<tr>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/* 315 */               out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/* 316 */               out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t</table></td>\n\t\t\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t<td valign=\"top\">\n\t\t\t\n\t\t\t<!--//include your Helpcard template table here..-->\n\n\n\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\n    <tr>\n    <td style=\"padding-top: 10px;\" class=\"boxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n      <tr>\n          <td class=\"txtSpace\">\n           <p>");
/* 317 */               out.print(FormatUtil.getString("am.webclient.wsm.helpcard.heading.text"));
/* 318 */               out.write("</p>\n          </td>\n      </tr>\n      <tr>\n\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n        <tr>\n          <td class=\"hCardInnerTopLeft\"/>\n          <td class=\"hCardInnerTopBg\"/>\n          <td class=\"hCardInnerTopRight\"/>\n        </tr>\n        <tr>\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n                <td class=\"hCardInnerBoxBg\">\n\t\t\t\t");
/* 319 */               out.print(FormatUtil.getString("am.webclient.wsm.helpcard.contents.text"));
/* 320 */               out.write("\n                       </td>\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n        </tr>\n\n      </table></td>\n\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td class=\"helpCardMainBtmLeft\"/>\n\n\t\t\t<td class=\"helpCardMainBtmBg\"/>\n\t\t\t<td class=\"helpCardMainBtmRight\"/>\n\n\t\t\t</tr>\n\t\t\t</table>\n\n");
/*     */             }
/* 322 */           } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 323 */         out = _jspx_out;
/* 324 */         if ((out != null) && (out.getBufferSize() != 0))
/* 325 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 326 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 329 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 335 */     PageContext pageContext = _jspx_page_context;
/* 336 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 338 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 339 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 340 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 342 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 343 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 344 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 346 */         out.write("\n\t alertUser();\n\treturn;\n\t");
/* 347 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 348 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 352 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 353 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 354 */       return true;
/*     */     }
/* 356 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 357 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 362 */     PageContext pageContext = _jspx_page_context;
/* 363 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 365 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 366 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 367 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*     */     
/* 369 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 370 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 371 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 372 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 373 */       return true;
/*     */     }
/* 375 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 376 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 381 */     PageContext pageContext = _jspx_page_context;
/* 382 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 384 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 385 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 386 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 388 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 389 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 390 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 391 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 392 */       return true;
/*     */     }
/* 394 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 395 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 400 */     PageContext pageContext = _jspx_page_context;
/* 401 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 403 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 404 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 405 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*     */     
/* 407 */     _jspx_th_c_005fout_005f2.setValue("${param.resId}");
/* 408 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 409 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 410 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 411 */       return true;
/*     */     }
/* 413 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 414 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\wsmoperations_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */