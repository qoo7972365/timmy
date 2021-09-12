/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.tags.Truncate;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ 
/*     */ public final class chooseSite24x7resource_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  27 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  43 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  47 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  54 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  58 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/*  59 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*  60 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  61 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  62 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  63 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  70 */     HttpSession session = null;
/*     */     
/*     */ 
/*  73 */     JspWriter out = null;
/*  74 */     Object page = this;
/*  75 */     JspWriter _jspx_out = null;
/*  76 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  80 */       response.setContentType("text/html");
/*  81 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  83 */       _jspx_page_context = pageContext;
/*  84 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  85 */       ServletConfig config = pageContext.getServletConfig();
/*  86 */       session = pageContext.getSession();
/*  87 */       out = pageContext.getOut();
/*  88 */       _jspx_out = out;
/*     */       
/*  90 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*     */       
/*  92 */       String mgid = request.getParameter("haid");
/*  93 */       String selectedscheme2 = null;
/*  94 */       boolean slimLayout2 = false;
/*  95 */       if (session.getAttribute("selectedscheme") != null)
/*     */       {
/*  97 */         selectedscheme2 = (String)session.getAttribute("selectedscheme");
/*     */       }
/*  99 */       if ((selectedscheme2 != null) && (selectedscheme2.equals("slim")))
/*     */       {
/* 101 */         slimLayout2 = true;
/*     */       }
/*     */       
/* 104 */       out.write("\n \n \n<script>\n \nfunction fnSelectAllOpMon1(e,formindex, checkboxstr)\n{\n\tvar temp=formindex;\n\tif(");
/* 105 */       out.print(slimLayout2);
/* 106 */       out.write(")\n\t{\n\t\ttemp=formindex-1;\n\t}\n\tToggleAll(e,document.forms[temp],checkboxstr)\n}\n\nfunction fnDisplayName1(combo)\n{\n\tvar v=combo.options[combo.selectedIndex].text;\n\tdocument.forms[formindex].displayname.value=v;\n}\n\nfunction fnOpMonFormSubmit1(a,temp,goback)\n{\n        var name='selectedSite24x7resource'; //No I18N\n        var msg = ' ");
/* 107 */       out.print(FormatUtil.getString("add"));
/* 108 */       out.write("';\n\t\tvar formindex=temp;\n\t\tif(");
/* 109 */       out.print(slimLayout2);
/* 110 */       out.write(")\n\t\t{\n\t\t\tformindex=temp-1;\n\t\t}\n\tif(!checkforOneSelected(document.forms[formindex],name))\n\t{\n\t\talert('");
/* 111 */       out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.jsalertforchooseresource.text"));
/* 112 */       out.write(" ' + msg);\n\t\treturn;\n\t}\t\n\tif(goback)\n\t{\n\t\tdocument.getElementsByName(\"goback\")[3].value='true';\t\n\t}\n\telse\n\t{\n\t\tdocument.getElementsByName(\"goback\")[3].value='false';\t\n\t}\n\tdocument.forms[formindex].savetype.value=a;\n\tdocument.forms[formindex].submit();\n}\n\n</script> \n \n \n <br>\n <body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
/*     */       
/* 114 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 115 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 116 */       _jspx_th_html_005fform_005f0.setParent(null);
/*     */       
/* 118 */       _jspx_th_html_005fform_005f0.setAction("/extDeviceAction.do");
/*     */       
/* 120 */       _jspx_th_html_005fform_005f0.setMethod("post");
/*     */       
/* 122 */       _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 123 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 124 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */         for (;;) {
/* 126 */           out.write("\n<input type=\"hidden\" name=\"org.apache.struts.taglib.html.TOKEN\" value=\"");
/* 127 */           out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 128 */           out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"addSite24x7Resource\">\n<input type=\"hidden\" name=\"goback\" value=\"false\">\n<input type=\"hidden\" name=\"savetype\" value=\"1\">\n<input type=\"hidden\" name=\"monitortype\" value=\"network\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 129 */           out.print(mgid);
/* 130 */           out.write("\">\n\n<table width=\"49%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n  <tr>\n\t\t<td class=\"AlarmHdrTopLeft\"/><td class=\"AlarmHdrTopBg\">\n \t\t\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n\t\t\t<tr>\n\t\t\t<td  valign=\"middle\" align=\"left\" class=\"AlarmCardContentBg\">\n\t\t\t\t  <span class=\"bcactive\"> <td height=\"25\" class=\"AlarmCardContentBg\"><b>&nbsp;");
/* 131 */           out.print(FormatUtil.getString("am.webclient.site24x7.heading"));
/* 132 */           out.write("</b></td></span>\n\t\t\t</td>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"AlarmCardHdrRightEar\"></td><td valign=\"middle\" align=\"left\">&nbsp;</td></tr>\n\t\t\t</table>\n\t\t    </td>\n\t\t\t<td class=\"AlarmHdrRightTop\">&nbsp;</td>\n</tr>\n<tr>\n\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t<td valign=\"top\">\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n    <tr>\n\n    <td style=\"padding-top:3px;\" class=\"AlarmboxedContent\">\n    <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" >\n      \n      </tr>\n      <tr>\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" >\n        <tr>\n\n          <td class=\"AlarmInnerTopLeft\"/>\n          <td class=\"AlarmInnerTopBg\"/>\n          <td class=\"AlarmInnerTopRight\"/>\n        </tr>\n        <tr>\n          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n                <td class=\"AlarmInnerBoxBg\" valign=\"top\" width=\"100%\">\n\n\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t\t\t\t\t<tr>\n");
/* 133 */           out.write("\n\t\t\t\t\t\t<td colspan=\"4\" valign=\"top\" class=\"AlarmtxtSpace\">\n\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t    <tr>\n\t\t\t\t      <td  width=\"50%\" valign=\"top\">  \n\t\t\t\t        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\"  style=\"background-color:#fff;\">\n\t\t\t\t\t\t\t\t\t\t\t\t \n\n");
/* 134 */           if (_jspx_meth_c_005fset_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 136 */           out.write("\n\t\t\t  \n  \n  \n ");
/*     */           
/* 138 */           ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 139 */           _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 140 */           _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_html_005fform_005f0);
/*     */           
/* 142 */           _jspx_th_c_005fforEach_005f0.setItems("${configuredSite24x7Mon}");
/*     */           
/* 144 */           _jspx_th_c_005fforEach_005f0.setVar("row1");
/*     */           
/* 146 */           _jspx_th_c_005fforEach_005f0.setVarStatus("rowcount1");
/* 147 */           int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*     */           try {
/* 149 */             int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 150 */             if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*     */               for (;;) {
/* 152 */                 out.write("\t\t\t\t\t\t  \n\t\t\t\t\t\n\t\t\t\t\t\n ");
/*     */                 
/* 154 */                 ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 155 */                 _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 156 */                 _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fforEach_005f0);
/*     */                 
/* 158 */                 _jspx_th_c_005fforEach_005f1.setItems("${row1.value}");
/*     */                 
/* 160 */                 _jspx_th_c_005fforEach_005f1.setVar("row");
/*     */                 
/* 162 */                 _jspx_th_c_005fforEach_005f1.setVarStatus("rowcount");
/* 163 */                 int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*     */                 try {
/* 165 */                   int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 166 */                   if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*     */                     for (;;) {
/* 168 */                       out.write("\n \n\t\t");
/*     */                       
/* 170 */                       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 171 */                       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 172 */                       _jspx_th_c_005fif_005f0.setParent(_jspx_th_c_005fforEach_005f1);
/*     */                       
/* 174 */                       _jspx_th_c_005fif_005f0.setTest("${temp == '1'}");
/* 175 */                       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 176 */                       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */                         for (;;) {
/* 178 */                           out.write("\n\t\t\t \t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td colspan=\"3\">\n\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  height=\"26\">\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t  <td height=\"31\" colspan=\"3\" class=\"columnheadingnotop\" align=\"left\">\n\t\t\t\t\t\t\t\t\t\t<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 179 */                           out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 180 */                           out.write("\" onClick=\"javascript:fnOpMonFormSubmit1('2', '5')\">&nbsp;&nbsp;&nbsp;&nbsp;");
/* 181 */                           out.print(FormatUtil.getString("am.common.or.text"));
/* 182 */                           out.write("&nbsp;&nbsp;&nbsp;<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 183 */                           out.print(FormatUtil.getString("am.monitorgroup.associateandgoback.txt"));
/* 184 */                           out.write("\" onClick=\"javascript:fnOpMonFormSubmit1('2', '5', 'true')\">\n\t\t\t\t\t\t\t\t\t  </td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t    </table>\n\t\t\t\t\t\t   </td>\n\t\t\t\t\t\t</tr>\n \n\t\t\t\t\t\t <tr>\n\t\t\t\t            <td class=\" whitegrayborder\" width=\"5%\"><input type=\"checkbox\" onClick=\"javascript:fnSelectAllOpMon1(this, '5', 'selectedSite24x7resource')\"></td>\n\t\t\t\t            <td class=\"bodytextbold whitegrayborder\" align=\"left\" width=\"75%\"><b>");
/* 185 */                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 186 */                           out.write("</b></td>\n\t\t\t\t            <td class=\"whitegrayborder\" width=\"20%\"><b>");
/* 187 */                           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 188 */                           out.write("</b></td>\n\t\t\t\t          </tr>\t\t\t\t\t\t \n\t");
/* 189 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 190 */                           if (evalDoAfterBody != 2)
/*     */                             break;
/*     */                         }
/*     */                       }
/* 194 */                       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 195 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */                         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 227 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 228 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*     */                         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 246 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 247 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                       }
/* 198 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 199 */                       out.write(10);
/* 200 */                       out.write(9);
/* 201 */                       if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*     */                       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 227 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 228 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*     */                         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 246 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 247 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                       }
/* 203 */                       out.write("\n\t\t\t\t          <tr>\n\t\t\t\t            <td width=\"2%\" class=\"whitegrayborder\"><input type=\"checkbox\" name=\"selectedSite24x7resource\" value=\"");
/* 204 */                       if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*     */                       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 227 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 228 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*     */                         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 246 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 247 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                       }
/* 206 */                       out.write("\"/></td>\n\t\t\t\t            <td width=\"78%\" class=\"whitegrayborder\" align=\"left\">");
/* 207 */                       if (_jspx_meth_am_005fTruncate_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*     */                       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 227 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 228 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*     */                         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 246 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 247 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                       }
/* 209 */                       out.write("</td>");
/* 210 */                       out.write("\n                            <td width=\"20%\" class=\"whitegrayborder\" align=\"left\">");
/* 211 */                       if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*     */                       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 227 */                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 228 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*     */                         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 246 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 247 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                       }
/* 213 */                       out.write("</td>\t \n\t\t\t\t          </tr>\n ");
/* 214 */                       int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 215 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 219 */                   if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*     */                   {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 227 */                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 228 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*     */                     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 246 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 247 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                   }
/*     */                 }
/*     */                 catch (Throwable _jspx_exception)
/*     */                 {
/*     */                   for (;;)
/*     */                   {
/* 223 */                     int tmp1075_1074 = 0; int[] tmp1075_1072 = _jspx_push_body_count_c_005fforEach_005f1; int tmp1077_1076 = tmp1075_1072[tmp1075_1074];tmp1075_1072[tmp1075_1074] = (tmp1077_1076 - 1); if (tmp1077_1076 <= 0) break;
/* 224 */                     out = _jspx_page_context.popBody(); }
/* 225 */                   _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*     */                 }
/*     */                 finally {}
/*     */                 
/*     */ 
/* 230 */                 out.write(10);
/* 231 */                 out.write(10);
/* 232 */                 out.write(32);
/* 233 */                 int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 234 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 238 */             if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 246 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 247 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/*     */           }
/*     */           catch (Throwable _jspx_exception)
/*     */           {
/*     */             for (;;)
/*     */             {
/* 242 */               int tmp1231_1230 = 0; int[] tmp1231_1228 = _jspx_push_body_count_c_005fforEach_005f0; int tmp1233_1232 = tmp1231_1228[tmp1231_1230];tmp1231_1228[tmp1231_1230] = (tmp1233_1232 - 1); if (tmp1233_1232 <= 0) break;
/* 243 */               out = _jspx_page_context.popBody(); }
/* 244 */             _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*     */           } finally {
/* 246 */             _jspx_th_c_005fforEach_005f0.doFinally();
/* 247 */             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*     */           }
/* 249 */           out.write(" \n \t\n\t\n\n");
/*     */           
/* 251 */           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 252 */           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 253 */           _jspx_th_c_005fif_005f1.setParent(_jspx_th_html_005fform_005f0);
/*     */           
/* 255 */           _jspx_th_c_005fif_005f1.setTest("${temp == '2'}");
/* 256 */           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 257 */           if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */             for (;;) {
/* 259 */               out.write("\n\t\n\t\t                <tr>\n\t\t\t\t            <td colspan=\"3\" class=\"columnheadingnotop\" align=\"left\" style=\"height:26px;\"> \n\t\t\t\t            <input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 260 */               out.print(FormatUtil.getString("am.webclient.configurealert.associate"));
/* 261 */               out.write("\" onClick=\"javascript:fnOpMonFormSubmit1('2', '5')\">&nbsp;&nbsp;&nbsp;&nbsp;");
/* 262 */               out.print(FormatUtil.getString("am.common.or.text"));
/* 263 */               out.write("&nbsp;&nbsp;&nbsp;<input name=\"button\" type=\"button\" class=\"buttons\" value=\"");
/* 264 */               out.print(FormatUtil.getString("am.monitorgroup.associateandgoback.txt"));
/* 265 */               out.write("\" onClick=\"javascript:fnOpMonFormSubmit1('2', '5', 'true')\">\n\t\t\t\t      \t   </td>\n\t\t\t\t          </tr>\n\t\n");
/* 266 */               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 267 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 271 */           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 272 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*     */           }
/*     */           
/* 275 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 276 */           out.write("\n\t\n ");
/*     */           
/* 278 */           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 279 */           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 280 */           _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fform_005f0);
/*     */           
/* 282 */           _jspx_th_c_005fif_005f2.setTest("${temp == '1'}");
/* 283 */           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 284 */           if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */             for (;;) {
/* 286 */               out.write("\n <tr>\n\t\t\t\t            <td class=\" whitegrayborder\" width=\"5%\" colspan=\"3\">");
/* 287 */               out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 288 */               out.write("</td>\n\t\t\t\t          </tr>\t\t\t\t       \n");
/* 289 */               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 290 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 294 */           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 295 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*     */           }
/*     */           
/* 298 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 299 */           out.write("\n\n\n\t\t\t\t        </table>\n\t\t\t\t        \n\n \n\n\t       \n\t\t\t\t\t\t</td>\n \n\n\t\t\t\t  </table>\n\t\t\t \n\n\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\n\n\n\n\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"25%\">\n\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"30%\">\t</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t</table>\n            </td>\n          <td class=\"AlarmInnerBoxBg\">&nbsp;</td>\n\n        </tr>\n\n      </table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td class=\"AlarmCardMainBtmLeft\"/>\n\t\t\t<td class=\"AlarmCardMainBtmBg\"/>\n\t\t\t<td class=\"AlarmCardMainBtmRight\"/>\n\n\t\t\t</tr>\n\t\t\t</table>\n \n");
/* 300 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 301 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 305 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 306 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */       }
/*     */       else {
/* 309 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 310 */         out.write(10);
/* 311 */         out.write(10);
/* 312 */         out.write(10);
/*     */       }
/* 314 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 315 */         out = _jspx_out;
/* 316 */         if ((out != null) && (out.getBufferSize() != 0))
/* 317 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 318 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 321 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 327 */     PageContext pageContext = _jspx_page_context;
/* 328 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 330 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 331 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 332 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 334 */     _jspx_th_c_005fset_005f0.setVar("temp");
/*     */     
/* 336 */     _jspx_th_c_005fset_005f0.setValue("1");
/* 337 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 338 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 339 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 340 */       return true;
/*     */     }
/* 342 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 343 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 348 */     PageContext pageContext = _jspx_page_context;
/* 349 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 351 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 352 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 353 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 355 */     _jspx_th_c_005fset_005f1.setVar("temp");
/*     */     
/* 357 */     _jspx_th_c_005fset_005f1.setValue("2");
/* 358 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 359 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 360 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 361 */       return true;
/*     */     }
/* 363 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 364 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 369 */     PageContext pageContext = _jspx_page_context;
/* 370 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 372 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 373 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 374 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 376 */     _jspx_th_c_005fout_005f0.setValue("${row.key}");
/* 377 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 378 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 379 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 380 */       return true;
/*     */     }
/* 382 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 383 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_am_005fTruncate_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 388 */     PageContext pageContext = _jspx_page_context;
/* 389 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 391 */     Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 392 */     _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 393 */     _jspx_th_am_005fTruncate_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 395 */     _jspx_th_am_005fTruncate_005f0.setTooltip("true");
/*     */     
/* 397 */     _jspx_th_am_005fTruncate_005f0.setLength(50);
/* 398 */     int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 399 */     if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 400 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 401 */         out = _jspx_page_context.pushBody();
/* 402 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 403 */         _jspx_th_am_005fTruncate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 404 */         _jspx_th_am_005fTruncate_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 407 */         out.write(32);
/* 408 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_am_005fTruncate_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 409 */           return true;
/* 410 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 411 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 414 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 415 */         out = _jspx_page_context.popBody();
/* 416 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*     */       }
/*     */     }
/* 419 */     if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 420 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 421 */       return true;
/*     */     }
/* 423 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 424 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_am_005fTruncate_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 429 */     PageContext pageContext = _jspx_page_context;
/* 430 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 432 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 433 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 434 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_am_005fTruncate_005f0);
/*     */     
/* 436 */     _jspx_th_c_005fout_005f1.setValue("${row.value['displayname']}");
/* 437 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 438 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 439 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 440 */       return true;
/*     */     }
/* 442 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 443 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 448 */     PageContext pageContext = _jspx_page_context;
/* 449 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 451 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 452 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 453 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 455 */     _jspx_th_c_005fout_005f2.setValue("${row.value['monitortype']}");
/* 456 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 457 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 458 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 459 */       return true;
/*     */     }
/* 461 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 462 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\chooseSite24x7resource_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */