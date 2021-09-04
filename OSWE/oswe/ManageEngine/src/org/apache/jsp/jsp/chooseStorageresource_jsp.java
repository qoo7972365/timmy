/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.tags.Truncate;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.logic.EmptyTag;
/*     */ import org.apache.struts.taglib.logic.IterateTag;
/*     */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*     */ 
/*     */ public final class chooseStorageresource_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  26 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  41 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  45 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  51 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  55 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/*  56 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  57 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  58 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.release();
/*  59 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  66 */     HttpSession session = null;
/*     */     
/*     */ 
/*  69 */     JspWriter out = null;
/*  70 */     Object page = this;
/*  71 */     JspWriter _jspx_out = null;
/*  72 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  76 */       response.setContentType("text/html");
/*  77 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  79 */       _jspx_page_context = pageContext;
/*  80 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  81 */       ServletConfig config = pageContext.getServletConfig();
/*  82 */       session = pageContext.getSession();
/*  83 */       out = pageContext.getOut();
/*  84 */       _jspx_out = out;
/*     */       
/*  86 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*     */       
/*  88 */       String resourceName2 = request.getParameter("type");
/*  89 */       String appname2 = request.getParameter("name");
/*  90 */       String haid2 = request.getParameter("haid");
/*  91 */       String bgcolour2 = "class=\"whitegrayborder\"";
/*  92 */       String prevpagelinktext2 = "am.webclient.monitorgroupdetails.backtomonitorgroup.text";
/*  93 */       String prevpagelink2 = "/showapplication.do?haid=" + haid2 + "&method=showApplication";
/*  94 */       String selectedscheme3 = null;
/*  95 */       boolean slimLayout3 = false;
/*  96 */       if (session.getAttribute("selectedscheme") != null)
/*     */       {
/*  98 */         selectedscheme3 = (String)session.getAttribute("selectedscheme");
/*     */       }
/* 100 */       if ((selectedscheme3 != null) && (selectedscheme3.equals("slim")))
/*     */       {
/* 102 */         slimLayout3 = true;
/*     */       }
/*     */       
/*     */ 
/* 106 */       out.write("\n<script>\n\nfunction fnSelectAllOpMon2(e,formindex, checkboxstr)\n{\n\tvar temp=formindex;\n\tif(");
/* 107 */       out.print(slimLayout3);
/* 108 */       out.write(")\n\t{\n\t\ttemp=formindex-1;\n\t}\n\tToggleAll(e,document.forms[temp],checkboxstr)\n}\nfunction fnDisplayName(combo)\n{\n\tvar v=combo.options[combo.selectedIndex].text;\n\tdocument.forms[formindex].displayname.value=v;\n}\nfunction fnOpMonFormSubmit2(a,temp,goback)\n{\n        var name='selectedStorageresource';//NO I18N\n        var msg = ' ");
/* 109 */       out.print(FormatUtil.getString("add"));
/* 110 */       out.write("';\n        // if(temp == 4) {\n            // name ='ExtIntegDevice';\n            // msg = '");
/* 111 */       out.print(FormatUtil.getString("remove"));
/* 112 */       out.write("';\n\t\t// }\n\t\tvar formindex=temp;\n\t\tif(");
/* 113 */       out.print(slimLayout3);
/* 114 */       out.write(")\n\t\t{\n\t\t\tformindex=temp-1;\n\t\t}\n\tif(!checkforOneSelected(document.forms[formindex],name))\n\t{\n\t\talert('");
/* 115 */       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.jsalertforchooseresource.text"));
/* 116 */       out.write(" ' + msg);\n\t\treturn;\n\t}\t\n\tif(goback)\n\t{\n\t\tdocument.getElementsByName(\"goback\")[2].value='true';\t\n\t}\n\telse\n\t{\n\t\tdocument.getElementsByName(\"goback\")[2].value='false';\t\n\t}\n\tdocument.forms[formindex].savetype.value=a;\n\tdocument.forms[formindex].submit();\n}\n\nfunction fnFormSubmitMg(a,temp)\n{\n\tvar name='selectedresourceMg';//NO I18N\n\tvar msg = ' ");
/* 117 */       out.print(FormatUtil.getString("add"));
/* 118 */       out.write("';\n\tif(temp == 2) {\n\t\tname ='monitorsMg';//NO I18N\n\t\tmsg = '");
/* 119 */       out.print(FormatUtil.getString("remove"));
/* 120 */       out.write("';\n\t}\n    var formindex=temp;\n\tif(");
/* 121 */       out.print(slimLayout3);
/* 122 */       out.write(")\n\t{\n\t  formindex=temp-1;\n\t}\n\tif(!checkforOneSelected(document.forms[formindex],name))\n\t{\n\t\talert('");
/* 123 */       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.jsalertforchooseresource.text"));
/* 124 */       out.write(" ' + msg);\n\t\treturn;\n\t}\n\tdocument.forms[formindex].savetype.value=a;\n\tdocument.forms[formindex].MonitorGroup.value=\"MonitorGroup\";\n\tdocument.forms[formindex].submit();\n}\n\n\n</script>\n");
/*     */       
/* 126 */       if ((!OEMUtil.isOEM()) || (Constants.isIt360) || ((OEMUtil.isOEM()) && ((List)request.getAttribute("opstordevtoconfigure") != null) && (((List)request.getAttribute("opstordevtoconfigure")).size() > 0)))
/*     */       {
/*     */ 
/* 129 */         out.write("\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
/*     */         
/* 131 */         FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 132 */         _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 133 */         _jspx_th_html_005fform_005f0.setParent(null);
/*     */         
/* 135 */         _jspx_th_html_005fform_005f0.setAction("/extDeviceAction.do");
/*     */         
/* 137 */         _jspx_th_html_005fform_005f0.setMethod("post");
/*     */         
/* 139 */         _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 140 */         int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 141 */         if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */           for (;;) {
/* 143 */             out.write("\n<input type=\"hidden\" name=\"org.apache.struts.taglib.html.TOKEN\" value=\"");
/* 144 */             out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 145 */             out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"addExtIntegResource\">\n<input type=\"hidden\" name=\"goback\" value=\"false\">\n<input type=\"hidden\" name=\"savetype\" value=\"1\">\n<input type=\"hidden\" name=\"monitortype\" value=\"network\">\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 146 */             out.print(appname2);
/* 147 */             out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 148 */             out.print(haid2);
/* 149 */             out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 150 */             out.print(resourceName2);
/* 151 */             out.write(34);
/* 152 */             out.write(62);
/* 153 */             out.write(10);
/*     */             
/* 155 */             String message2 = request.getParameter("message");
/* 156 */             String redirect = "/showresource.do?haid=" + haid2 + "&type=" + resourceName2 + "&method=getMonitorForm";
/* 157 */             String encodedurl = java.net.URLEncoder.encode(redirect);
/* 158 */             String initMonLink = "";
/* 159 */             String internaltype = null;
/*     */             
/*     */ 
/* 162 */             boolean allTypes2 = resourceName2.equals("All");
/*     */             
/* 164 */             out.write("\n<br><br>\n\n<table width=\"49%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n\n\t\t\t<tr>\n\t\t\t<td class=\"AlarmHdrTopLeft\"/>\n\t\t\t<td class=\"AlarmHdrTopBg\">\n\n\n\t\t\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n\n\t\t\t<tr>\n\t\t\t<td  valign=\"middle\" align=\"left\" class=\"AlarmCardContentBg\">\n\n\n\t\t<span class=\"bcactive\"> ");
/* 165 */             if (Constants.isIt360)
/*     */             {
/* 167 */               out.write("\n\n\n\t\t\t\t\t\t    <td height=\"25\"  class=\"AlarmCardContentBg\">");
/* 168 */               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.avlnwdevices.text"));
/* 169 */               out.write("</td>\n\t\t\t\t\t\t  ");
/*     */             } else {
/* 171 */               out.write("\n\t\t\t\t\t\t\t   <td height=\"25\" class=\"AlarmCardContentBg\"><b>&nbsp;");
/* 172 */               out.print(FormatUtil.getString("Storage Devices"));
/* 173 */               out.write("</b></td>\n\t\t\t\t\t\t  ");
/*     */             }
/* 175 */             out.write("\n</span>\n\n\n\n\t\t\t</td>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardHdrRightEar\">\n\t\t\t</td>\n\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t</table></td>\n\t\t\t<td class=\"AlarmHdrRightTop\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t<td valign=\"top\">\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n    <tr>\n\n    <td style=\"padding-top:3px;\" class=\"AlarmboxedContent\">\n    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" >\n      <tr>\n          <td >\n           <!--text -->\n          </td>\n      </tr>\n      <tr>\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" >\n        <tr>\n\n          <td class=\"AlarmInnerTopLeft\"/>\n          <td class=\"AlarmInnerTopBg\"/>\n          <td class=\"AlarmInnerTopRight\"/>\n        </tr>\n        <tr>\n          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n                <td class=\"AlarmInnerBoxBg\" valign=\"top\" width=\"100%\">\n\n");
/* 176 */             out.write("\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t\t\t\t\t<tr>\n\n\t\t\t\t\t\t<td colspan=\"4\" valign=\"top\" class=\"AlarmtxtSpace\">\n\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t    <tr>\n\t\t\t\t      <td  width=\"50%\" valign=\"top\"> ");
/*     */             
/* 178 */             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 179 */             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 180 */             _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*     */             
/* 182 */             _jspx_th_logic_005fnotEmpty_005f0.setName("opstordevtoconfigure");
/* 183 */             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 184 */             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*     */               for (;;) {
/* 186 */                 out.write("\n\t\t\t\t        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\"  style=\"background-color:#fff;\">\n\t\t\t\t          <tr>\n\n\t\t\t\t");
/* 187 */                 if (allTypes2) {
/* 188 */                   out.write("\n\t\t\t\t<td colspan=\"3\">\n\t\t\t\t");
/*     */                 } else {
/* 190 */                   out.write("\n\t\t\t\t<td colspan=\"2\" >\n\t\t\t\t");
/*     */                 }
/* 192 */                 out.write("\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  height=\"26\">\n\t\t\t\t\t<tr>\n\t\t\t\t");
/*     */                 
/* 194 */                 int s = ((List)request.getAttribute("opstordevtoconfigure")).size();
/* 195 */                 if (s > 0)
/*     */                 {
/* 197 */                   out.write("\n\n\n\t\t\t\t            ");
/* 198 */                   if (allTypes2) {
/* 199 */                     out.write("\n\t\t\t\t            <td height=\"31\" colspan=\"3\" class=\"columnheadingnotop\" align=\"left\">\n\t\t\t\t            ");
/*     */                   } else {
/* 201 */                     out.write("\n\t\t\t\t            <td height=\"31\" colspan=\"2\" class=\"columnheadingnotop\" align=\"left\">\n\t\t\t\t            ");
/*     */                   }
/* 203 */                   out.write("\n\t\t\t\t    <input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 204 */                   out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 205 */                   out.write("\" onClick=\"javascript:fnOpMonFormSubmit2('2', '4')\">&nbsp;&nbsp;&nbsp;&nbsp;");
/* 206 */                   out.print(FormatUtil.getString("am.common.or.text"));
/* 207 */                   out.write("&nbsp;&nbsp;&nbsp;<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 208 */                   out.print(FormatUtil.getString("am.monitorgroup.associateandgoback.txt"));
/* 209 */                   out.write("\" onClick=\"javascript:fnOpMonFormSubmit2('2', '4', 'true')\">\n\t\t\t\t\t</td>\n\n\n\t\t\t\t");
/*     */                 }
/*     */                 
/*     */ 
/* 213 */                 out.write("\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t\t          </tr>\n\t\t\t\t          <tr>\n\t\t\t\t            <td class=\" whitegrayborder\" width=\"5%\"><input type=\"checkbox\" onClick=\"javascript:fnSelectAllOpMon2(this, '4', 'selectedStorageresource')\"></td>\n\t\t\t\t            <td class=\"bodytextbold whitegrayborder\" align=\"left\" width=\"75%\"><b>");
/* 214 */                 out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 215 */                 out.write("</b></td>\n\t\t\t\t            ");
/* 216 */                 if (allTypes2) {
/* 217 */                   out.write("\n\t\t\t\t            <td class=\"whitegrayborder\" width=\"20%\"><b>");
/* 218 */                   out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 219 */                   out.write("</b></td>\n\t\t\t\t            ");
/*     */                 }
/* 221 */                 out.write("\n\t\t\t\t          </tr>\n\t\t\t\t          ");
/*     */                 
/* 223 */                 IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 224 */                 _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 225 */                 _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*     */                 
/* 227 */                 _jspx_th_logic_005fiterate_005f0.setName("opstordevtoconfigure");
/*     */                 
/* 229 */                 _jspx_th_logic_005fiterate_005f0.setId("row");
/*     */                 
/* 231 */                 _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*     */                 
/* 233 */                 _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 234 */                 int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 235 */                 if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 236 */                   ArrayList row = null;
/* 237 */                   Integer j = null;
/* 238 */                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 239 */                     out = _jspx_page_context.pushBody();
/* 240 */                     _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 241 */                     _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */                   }
/* 243 */                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 244 */                   j = (Integer)_jspx_page_context.findAttribute("j");
/*     */                   for (;;) {
/* 246 */                     out.write("\n\t\t\t\t          ");
/*     */                     
/* 248 */                     String deviceName = null;
/* 249 */                     deviceName = (String)row.get(0) + "$" + (String)row.get(3);
/* 250 */                     String dispName = (String)row.get(1);
/* 251 */                     dispName = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(dispName);
/*     */                     
/* 253 */                     String category = "";
/* 254 */                     if (allTypes2)
/*     */                     {
/* 256 */                       category = (String)row.get(2);
/* 257 */                       int startIndex = category.indexOf("-");
/* 258 */                       category = category.substring(startIndex + 1, category.length());
/* 259 */                       deviceName = deviceName + "$" + category;
/*     */                     }
/* 261 */                     if (j.intValue() % 2 == 0)
/*     */                     {
/* 263 */                       bgcolour2 = "class=\"whitegrayborder\"";
/*     */                     }
/*     */                     else
/*     */                     {
/* 267 */                       bgcolour2 = "class=\"yellowgrayborder\"";
/*     */                     }
/*     */                     
/* 270 */                     out.write("\n\t\t\t\t          <tr>\n\t\t\t\t            <td width=\"2%\" ");
/* 271 */                     out.print(bgcolour2);
/* 272 */                     out.write("><input type=\"checkbox\" name=\"selectedStorageresource\" value=\"");
/* 273 */                     out.print(deviceName);
/* 274 */                     out.write("\"></td>\n\t\t\t\t            <td width=\"78%\" ");
/* 275 */                     out.print(bgcolour2);
/* 276 */                     out.write(" align=\"left\">");
/*     */                     
/* 278 */                     Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 279 */                     _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 280 */                     _jspx_th_am_005fTruncate_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*     */                     
/* 282 */                     _jspx_th_am_005fTruncate_005f0.setTooltip("true");
/*     */                     
/* 284 */                     _jspx_th_am_005fTruncate_005f0.setLength(50);
/* 285 */                     int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 286 */                     if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 287 */                       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 288 */                         out = _jspx_page_context.pushBody();
/* 289 */                         _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 290 */                         _jspx_th_am_005fTruncate_005f0.doInitBody();
/*     */                       }
/*     */                       for (;;) {
/* 293 */                         out.print(dispName);
/* 294 */                         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 295 */                         if (evalDoAfterBody != 2)
/*     */                           break;
/*     */                       }
/* 298 */                       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 299 */                         out = _jspx_page_context.popBody();
/*     */                       }
/*     */                     }
/* 302 */                     if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 303 */                       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0); return;
/*     */                     }
/*     */                     
/* 306 */                     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 307 */                     out.write("</td>");
/* 308 */                     out.write("\n\n\t\t\t\t            ");
/* 309 */                     if (allTypes2) {
/* 310 */                       out.write("\n\t\t\t\t\t\t\t\t<td width=\"20%\" ");
/* 311 */                       out.print(bgcolour2);
/* 312 */                       out.write(" align=\"left\">");
/* 313 */                       out.print(category);
/* 314 */                       out.write("</td>\n\t\t\t\t            ");
/*     */                     }
/* 316 */                     out.write("\n\n\n\n\t\t\t\t          </tr>\n\t\t\t\t          ");
/* 317 */                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 318 */                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 319 */                     j = (Integer)_jspx_page_context.findAttribute("j");
/* 320 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/* 323 */                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 324 */                     out = _jspx_page_context.popBody();
/*     */                   }
/*     */                 }
/* 327 */                 if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 328 */                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*     */                 }
/*     */                 
/* 331 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 332 */                 out.write("\n\n\t\t\t\t          <tr>\n\t\t\t\t            ");
/* 333 */                 if (allTypes2) {
/* 334 */                   out.write("\n\t\t\t\t            <td colspan=\"3\" class=\"columnheadingnotop\" align=\"left\" style=\"height:26px;\">\n\t\t\t\t            ");
/*     */                 } else {
/* 336 */                   out.write("\n\t\t\t\t            <td  colspan=\"2\" class=\"columnheadingnotop\" align=\"left\" style=\"height:26px;\">\n\t\t\t\t            ");
/*     */                 }
/* 338 */                 out.write("\n\t\t\t\t            <input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 339 */                 out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 340 */                 out.write("\" onClick=\"javascript:fnOpMonFormSubmit2('2', '4')\">&nbsp;&nbsp;&nbsp;&nbsp;");
/* 341 */                 out.print(FormatUtil.getString("am.common.or.text"));
/* 342 */                 out.write("&nbsp;&nbsp;&nbsp;<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 343 */                 out.print(FormatUtil.getString("am.monitorgroup.associateandgoback.txt"));
/* 344 */                 out.write("\" onClick=\"javascript:fnOpMonFormSubmit2('2', '4', 'true')\">\n\t\t\t\t\t</td>\n\t\t\t\t          </tr>\n\n\t\t\t\t        </table>\n\t\t\t\t        ");
/* 345 */                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 346 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 350 */             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 351 */               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*     */             }
/*     */             
/* 354 */             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 355 */             out.write(32);
/*     */             
/* 357 */             EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 358 */             _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 359 */             _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*     */             
/* 361 */             _jspx_th_logic_005fempty_005f0.setName("opstordevtoconfigure");
/* 362 */             int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 363 */             if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*     */               for (;;) {
/* 365 */                 out.write("\n\t\t\t\t        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" style=\"background-color:#fff;\">\n\t\t\t\t          <tr>\n\t\t\t\t<td >\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  >\n\t\t\t\t\t<tr>\n\t\t\t\t\t ");
/* 366 */                 if (Constants.isIt360)
/*     */                 {
/* 368 */                   out.write("   <td height=\"25\"  colspan=\"2\" class=\"columnheading\">");
/* 369 */                   out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.avlnwdevices.text"));
/* 370 */                   out.write("</td>\n\t\t\t\t\t\t  ");
/*     */                 } else {
/* 372 */                   out.write("\n\t\t\t\t\t\t\t   <td height=\"25\"  colspan=\"2\" class=\"columnheading\"></td>\n\t\t\t\t\t\t  ");
/*     */                 }
/* 374 */                 out.write("\n\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\n\t\t\t\t          </tr>\n\t\t\t\t          <tr>\n\n\t\t\t\t          <td  height=\"25\"><span class=\"bodytext\"> &nbsp;");
/* 375 */                 out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 376 */                 out.write(". </span></td>\n\t\t\t\t          </tr>\n\t\t\t\t        </table>\n\t\t\t\t        ");
/* 377 */                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 378 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 382 */             if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 383 */               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*     */             }
/*     */             
/* 386 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 387 */             out.write("\n\t\t\t\t\t\t</td>\n\n\t\t\t\n\n\t\t\t\t  </table>\n\t\t\t\t\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"25%\">\n\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"30%\">\t</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t</table>\n            </td>\n          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\n        </tr>\n\n      </table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td class=\"AlarmCardMainBtmLeft\"/>\n\t\t\t<td class=\"AlarmCardMainBtmBg\"/>\n\t\t\t<td class=\"AlarmCardMainBtmRight\"/>\n\n\t\t\t</tr>\n\t\t\t</table>\n\n\t");
/* 388 */             int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 389 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 393 */         if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 394 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*     */         }
/*     */         
/* 397 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 398 */         out.write("\n\t</body>\n\t\n");
/*     */       }
/*     */     } catch (Throwable t) {
/* 401 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 402 */         out = _jspx_out;
/* 403 */         if ((out != null) && (out.getBufferSize() != 0))
/* 404 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 405 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 408 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\chooseStorageresource_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */