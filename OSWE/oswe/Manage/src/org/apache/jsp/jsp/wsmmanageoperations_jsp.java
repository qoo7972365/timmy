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
/*     */ public final class wsmmanageoperations_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  29 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  30 */   static { _jspx_dependants.put("/jsp/wsmbreadcrumb.jsp", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  41 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  45 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  49 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  53 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  54 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  55 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  62 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  65 */     JspWriter out = null;
/*  66 */     Object page = this;
/*  67 */     JspWriter _jspx_out = null;
/*  68 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  72 */       response.setContentType("text/html");
/*  73 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  75 */       _jspx_page_context = pageContext;
/*  76 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  77 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  78 */       session = pageContext.getSession();
/*  79 */       out = pageContext.getOut();
/*  80 */       _jspx_out = out;
/*     */       
/*  82 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n <script>\nfunction showArgs(oid,insid)\n{\n\tMM_openBrWindow('/wsm.do?method=showargs&oid='+oid+'&insid='+insid,'ArgumentInfo','width=400,height=300,top=200,left=300, scrollbars=yes,resizable=yes');\n}\n\nfunction isOneSelected(tagname)\n{\n\tvar checks = document.getElementsByName(tagname);\n\tvar isonechecked = false;\n\tfor(var i=0;i<checks.length;i++)\n\t{\n\t\tif(checks[i].checked)\n\t\t{\n\t\t\tisonechecked = true;\n\t\t\tbreak;\n\t\t}\n\t}\n\treturn isonechecked;\n}\n\nfunction delete1()\n{\n\n\t");
/*  83 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/*  85 */       out.write("\n\tif(!isOneSelected(\"instance\"))\n\t{\n\t\talert(\"");
/*  86 */       out.print(FormatUtil.getString("am.webclient.wsm.jsalertselectoperation.text"));
/*  87 */       out.write("\");\n\t\treturn;\n\t}\n\tvar s = confirm(\"");
/*  88 */       out.print(FormatUtil.getString("am.webclient.wsm.jsalertfordelete.text"));
/*  89 */       out.write("\");\n\tif(s)\n\t{\n\t\tdocument.forms.operations.method.value=\"deleteoperations\";\n\t\tdocument.forms.operations.submit();\n\t}\n}\n\nfunction showoperation(resid)\n{\n\tlocation.href=\"/wsm.do?method=showoperations&resourceid=\"+resid;\n}\n</script>\n\n");
/*  90 */       String resid = request.getParameter("resourceid");
/*  91 */       String header = "";
/*  92 */       String content = "";
/*  93 */       out.write(10);
/*  94 */       ArrayList data = (ArrayList)request.getAttribute("WSOpData");
/*  95 */       out.write(10);
/*  96 */       out.write(10);
/*  97 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n  \n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n\t");
/*     */       
/*  99 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 100 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 101 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/* 103 */       _jspx_th_c_005fif_005f0.setTest("${param.method=='showdetails'}");
/* 104 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 105 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */         for (;;) {
/* 107 */           out.write("\t\n       <td class=\"bcsign\" height=\"22\">");
/* 108 */           out.print(BreadcrumbUtil.getMonitorsPage());
/* 109 */           out.write(" &gt; ");
/* 110 */           out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 111 */           out.write(" &gt; <span class=\"bcactive\">");
/* 112 */           out.print(request.getAttribute("displayname"));
/* 113 */           out.write(" </span></td>\n\t");
/* 114 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 115 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 119 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 120 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 123 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 124 */         out.write(10);
/* 125 */         out.write(9);
/*     */         
/* 127 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 128 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 129 */         _jspx_th_c_005fif_005f1.setParent(null);
/*     */         
/* 131 */         _jspx_th_c_005fif_005f1.setTest("${param.method=='manageoperations'}");
/* 132 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 133 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */           for (;;) {
/* 135 */             out.write("\n\t<td class=\"bcsign\" height=\"22\">\n      ");
/* 136 */             out.print(BreadcrumbUtil.getMonitorsPage());
/* 137 */             out.write(" &gt; ");
/* 138 */             out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 139 */             out.write(" &gt; <a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 140 */             if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*     */               return;
/* 142 */             out.write(34);
/* 143 */             out.write(62);
/* 144 */             out.print(request.getAttribute("displayname"));
/* 145 */             out.write("</a> &gt; <span class=\"bcactive\">");
/* 146 */             out.print(FormatUtil.getString("am.webclient.wsm.manageoperationsbc.text"));
/* 147 */             out.write("</span>\n      </td>\n\t");
/* 148 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 149 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 153 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 154 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*     */         }
/*     */         else {
/* 157 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 158 */           out.write(10);
/* 159 */           out.write(10);
/* 160 */           out.write(9);
/*     */           
/* 162 */           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 163 */           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 164 */           _jspx_th_c_005fif_005f2.setParent(null);
/*     */           
/* 166 */           _jspx_th_c_005fif_005f2.setTest("${param.method=='showoperations'}");
/* 167 */           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 168 */           if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */             for (;;) {
/* 170 */               out.write("\n\t<td class=\"bcsign\" height=\"22\">\n      ");
/* 171 */               out.print(BreadcrumbUtil.getMonitorsPage());
/* 172 */               out.write(" &gt; ");
/* 173 */               out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 174 */               out.write(" &gt; <a class=\"staticlinks\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 175 */               if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*     */                 return;
/* 177 */               out.write(34);
/* 178 */               out.write(62);
/* 179 */               out.print(request.getAttribute("displayname"));
/* 180 */               out.write("</a> &gt; <span class=\"bcactive\">");
/* 181 */               out.print(FormatUtil.getString("am.webclient.wsm.showoperationsbc.text"));
/* 182 */               out.write("</span>\n      </td>\n\t");
/* 183 */               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 184 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 188 */           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 189 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*     */           }
/*     */           else {
/* 192 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 193 */             out.write(10);
/* 194 */             out.write(9);
/*     */             
/* 196 */             IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 197 */             _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 198 */             _jspx_th_c_005fif_005f3.setParent(null);
/*     */             
/* 200 */             _jspx_th_c_005fif_005f3.setTest("${param.method=='getSOAPInfo'}");
/* 201 */             int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 202 */             if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */               for (;;) {
/* 204 */                 out.write("\n\t  <td class=\"bcsign\" height=\"22\">\n\t   \t");
/* 205 */                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 206 */                 out.write(" &gt; ");
/* 207 */                 out.print(BreadcrumbUtil.getMonitorResourceTypes("Web Service"));
/* 208 */                 out.write(" &gt; <a class=\"bcinactive\" href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 209 */                 if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*     */                   return;
/* 211 */                 out.write(34);
/* 212 */                 out.write(62);
/* 213 */                 out.print(request.getAttribute("displayname"));
/* 214 */                 out.write(" </a> &gt; <span class=\"bcactive\">");
/* 215 */                 out.print(request.getAttribute("operationName"));
/* 216 */                 out.write("</span>\n\t");
/* 217 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 218 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 222 */             if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 223 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*     */             }
/*     */             else {
/* 226 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 227 */               out.write("\n</tr>\n\t<tr>\n\t\t<td  class=\"bcstrip\" height=\"2\"><img src=\"../images/spacer.gif\" width=\"20\" height=\"2px\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\t\n");
/* 228 */               out.write("\n\n<form name=\"operations\" method=\"post\" action=\"/wsm.do\">\n<input type=\"hidden\" name=\"method\" value=\"deleteoperations\">\n<input type=\"hidden\" name=\"enable\" value=\"false\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 229 */               out.print(resid);
/* 230 */               out.write("\">\n\n");
/* 231 */               if ((data != null) && (data.size() > 0))
/*     */               {
/* 233 */                 header = FormatUtil.getString("am.webclient.wsm.helpcard.manageoperation.heading.text");
/* 234 */                 content = FormatUtil.getString("am.webclient.wsm.helpcard.manageoperation.contents.text");
/* 235 */                 if (data.size() > 10)
/*     */                 {
/* 237 */                   out.write("\n<table width=\"99%\" border=\"0\">\n<tr><td align=\"right\">\n<a class=\"staticlinks\" href=\"/wsm.do?resourceid=");
/* 238 */                   out.print(resid);
/* 239 */                   out.write("&method=showdetails\">");
/* 240 */                   out.print(FormatUtil.getString("am.webclient.wsm.monitordetailspage.text"));
/* 241 */                   out.write("</a>\n</td></tr></table>");
/*     */                 }
/* 243 */                 out.write("\n<table class=\"lrtbdarkborder\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"99%\">\n<tr>\n<td colspan=\"4\" class=\"tableheadingtrans\" >\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"99%\">\n<tr>\n<td class=\"tableheadingtrans\">");
/* 244 */                 out.print(FormatUtil.getString("am.webclient.wsm.manageoperationsbc.text"));
/* 245 */                 out.write("</td>\n</tr>\n</table>\n</td>\n</tr>\n<tr height=\"26\">\n<td class=\"columnheading\" width=\"5%\" align=\"center\"><input type=\"checkbox\" onclick=\"ToggleAll(this,document.operations,'instance')\"></td>\n<td class=\"columnheading\">");
/* 246 */                 out.print(FormatUtil.getString("am.webclient.wsm.operationname.text"));
/* 247 */                 out.write("</td>\n<td class=\"columnheading\" align=\"center\" width=\"5%\">");
/* 248 */                 out.print(FormatUtil.getString("jmxnotification.edit"));
/* 249 */                 out.write("</td>\n</tr>\n");
/*     */                 
/* 251 */                 int i = 0; for (int size = data.size(); i < size; i++)
/*     */                 {
/*     */ 
/* 254 */                   Properties p = (Properties)data.get(i);
/* 255 */                   String displayname = p.getProperty("displayname");
/* 256 */                   String insid = p.getProperty("insid");
/* 257 */                   String styleclass = "whitegrayrightalign-reports-normal";
/* 258 */                   if (i % 2 == 0)
/*     */                   {
/* 260 */                     styleclass = "whitegrayrightalign-reports-normal";
/*     */                   }
/*     */                   
/* 263 */                   out.write("\n\n<tr class=\"");
/* 264 */                   out.print(styleclass);
/* 265 */                   out.write("\" >\n<td align=\"center\"> &nbsp;<input type=\"checkbox\" name=\"instance\" value=\"");
/* 266 */                   out.print(insid);
/* 267 */                   out.write("\"></td>\n<td nowrap><div style=\"width:300px;overflow:hidden\">");
/* 268 */                   out.print(displayname);
/* 269 */                   out.write("</div></td>\n<td align='center'><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/wsm.do?method=getSOAPInfo&operationId=");
/* 270 */                   out.print(insid);
/* 271 */                   out.write("&resId=");
/* 272 */                   out.print(resid);
/* 273 */                   out.write("&type=request')\"><img src='/images/icon_edit.gif' border=\"0\"></img></a></td>\n</tr>\n");
/*     */                 }
/*     */                 
/* 276 */                 out.write("\n<tr>\n<td colspan=\"4\" class=\"tablebottom\">\n<table cellpadding=\"0\" cellspacing=\"0\"  border=\"0\" width=\"99%\">\n<tr>\n<td align=\"left\"><a class=\"staticlinks\" href=\"javascript:delete1();\">");
/* 277 */                 out.print(FormatUtil.getString("am.webclient.wsm.deleteoperation.text"));
/* 278 */                 out.write("</a></td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\">\n<tr><td align=\"right\">\n<a class=\"staticlinks\" href=\"/wsm.do?resourceid=");
/* 279 */                 out.print(resid);
/* 280 */                 out.write("&method=showdetails\">");
/* 281 */                 out.print(FormatUtil.getString("am.webclient.wsm.monitordetailspage.text"));
/* 282 */                 out.write("</a>\n</td></tr></table>\n\n");
/*     */ 
/*     */               }
/*     */               else
/*     */               {
/* 287 */                 header = FormatUtil.getString("am.webclient.wsm.helpcard.emptymanageoperation.heading.text");
/* 288 */                 content = FormatUtil.getString("am.webclient.wsm.helpcard.emptymanageoperation.contents.text");
/*     */                 
/* 290 */                 out.write("\n<table class=\"lrtbdarkborder\" width=\"99%\" cellspacing=\"0\" border=\"0\">\n<tr>\n<td class=\"tableheadingtrans\">");
/* 291 */                 out.print(FormatUtil.getString("am.webclient.wsm.manageoperationsbc.text"));
/* 292 */                 out.write("</td>\n</tr>\n<tr>\n<td height=\"25px\">\n<span class=\"bodytext\">");
/* 293 */                 out.print(FormatUtil.getString("am.webclient.wsm.nooperations.manageoperation.text"));
/* 294 */                 out.write("<a class=\"staticlinks\" href=\"/wsm.do?method=showoperations&resourceid=");
/* 295 */                 out.print(resid);
/* 296 */                 out.write(34);
/* 297 */                 out.write(62);
/* 298 */                 out.print(FormatUtil.getString("am.webclient.wsm.showoperationsbc.text"));
/* 299 */                 out.write("</a></span>\n</td>\n</tr>\n</table>\n\n");
/*     */               }
/*     */               
/*     */ 
/* 303 */               out.write("\n\n<br>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"margin:0px 0px 0px 0px;\">\n\t\t\t<tr>\n\t\t\t<td class=\"helpCardHdrTopLeft\"/>\n\n\t\t\t<td class=\"helpCardHdrTopBg\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t\t\t<tr>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/* 304 */               out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/* 305 */               out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t</table></td>\n\t\t\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t<td valign=\"top\">\n\t\t\t<!--//include your Helpcard template table here..-->\n\n\n\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\n    <tr>\n    <td style=\"padding-top: 10px;\" class=\"boxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n      <tr>\n          <td class=\"txtSpace\">\n           <p>");
/* 306 */               out.print(header);
/* 307 */               out.write("</p>\n          </td>\n      </tr>\n      <tr>\n\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n        <tr>\n          <td class=\"hCardInnerTopLeft\"/>\n          <td class=\"hCardInnerTopBg\"/>\n          <td class=\"hCardInnerTopRight\"/>\n        </tr>\n        <tr>\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n                <td class=\"hCardInnerBoxBg\">\n                  ");
/* 308 */               out.print(content);
/* 309 */               out.write("\n            </td>\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n        </tr>\n\n      </table></td>\n\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td class=\"helpCardMainBtmLeft\"/>\n\n\t\t\t<td class=\"helpCardMainBtmBg\"/>\n\t\t\t<td class=\"helpCardMainBtmRight\"/>\n\n\t\t\t</tr>\n\t\t\t</table>\n\n\n</form>\n");
/*     */             }
/* 311 */           } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 312 */         out = _jspx_out;
/* 313 */         if ((out != null) && (out.getBufferSize() != 0))
/* 314 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 315 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 318 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 324 */     PageContext pageContext = _jspx_page_context;
/* 325 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 327 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 328 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 329 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 331 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 332 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 333 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 335 */         out.write("\n\t alertUser();\n\treturn;\n\t");
/* 336 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 337 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 341 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 342 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 343 */       return true;
/*     */     }
/* 345 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 346 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 351 */     PageContext pageContext = _jspx_page_context;
/* 352 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 354 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 355 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 356 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*     */     
/* 358 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 359 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 360 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 361 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 362 */       return true;
/*     */     }
/* 364 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 365 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 370 */     PageContext pageContext = _jspx_page_context;
/* 371 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 373 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 374 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 375 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 377 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 378 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 379 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 380 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 381 */       return true;
/*     */     }
/* 383 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 384 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 389 */     PageContext pageContext = _jspx_page_context;
/* 390 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 392 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 393 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 394 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*     */     
/* 396 */     _jspx_th_c_005fout_005f2.setValue("${param.resId}");
/* 397 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 398 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 399 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 400 */       return true;
/*     */     }
/* 402 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 403 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\wsmmanageoperations_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */