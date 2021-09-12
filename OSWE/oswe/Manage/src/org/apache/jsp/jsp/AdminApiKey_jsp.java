/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.tiles.InsertTag;
/*     */ import org.apache.struts.taglib.tiles.PutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class AdminApiKey_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  28 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  34 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  35 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  47 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  51 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  56 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  60 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  61 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*  62 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*  63 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  70 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  73 */     JspWriter out = null;
/*  74 */     Object page = this;
/*  75 */     JspWriter _jspx_out = null;
/*  76 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  80 */       response.setContentType("text/html;charset=UTF-8");
/*  81 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  83 */       _jspx_page_context = pageContext;
/*  84 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  85 */       ServletConfig config = pageContext.getServletConfig();
/*  86 */       session = pageContext.getSession();
/*  87 */       out = pageContext.getOut();
/*  88 */       _jspx_out = out;
/*     */       
/*  90 */       out.write("<!DOCTYPE html>\n");
/*  91 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  92 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*  93 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  94 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  96 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  97 */       out.write(10);
/*  98 */       out.write("\n<SCRIPT>\nfunction js()\n{\ndocument.AMapikey.submit();\n}\n</SCRIPT>\n");
/*     */       
/* 100 */       request.setAttribute("HelpKey", "REST API");
/*     */       
/* 102 */       out.write(10);
/*     */       
/* 104 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 105 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 106 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*     */       
/* 108 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/NewAdminLayout.jsp");
/* 109 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 110 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*     */         for (;;) {
/* 112 */           out.write("\n\n<style>\nfont.notchecked{\ncolor:gray;\n}\nfont.checked{\ncolor:black;\n}\n</style>\n");
/*     */           
/* 114 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 115 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 116 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*     */           
/* 118 */           _jspx_th_tiles_005fput_005f0.setName("UserArea");
/*     */           
/* 120 */           _jspx_th_tiles_005fput_005f0.setType("string");
/* 121 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 122 */           if (_jspx_eval_tiles_005fput_005f0 != 0) {
/* 123 */             if (_jspx_eval_tiles_005fput_005f0 != 1) {
/* 124 */               out = _jspx_page_context.pushBody();
/* 125 */               _jspx_th_tiles_005fput_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 126 */               _jspx_th_tiles_005fput_005f0.doInitBody();
/*     */             }
/*     */             for (;;) {
/* 129 */               out.write("\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t");
/*     */               
/* 131 */               if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*     */               {
/*     */ 
/* 134 */                 out.write("\n\t  <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 135 */                 out.print(BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 136 */                 out.write(" &gt; <span class=\"bcactive\">");
/* 137 */                 out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/* 138 */                 out.write("</span></td>\n\t ");
/*     */               }
/*     */               else
/*     */               {
/* 142 */                 out.write("\n\t\t <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 143 */                 out.print(BreadcrumbUtil.getAdminPage(request));
/* 144 */                 out.write(" &gt; <span class=\"bcactive\">");
/* 145 */                 out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/* 146 */                 out.write("</span></td>\n\t ");
/*     */               }
/* 148 */               out.write("\n\t</tr>\n</table>\n<form name=\"AMapikey\" action=\"\" style=\"display:inline;\">\n<table align=\"center\" width=\"100%\" >\n<tr >\n<td >\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder itadmin-no-decor\">\n<tr>\n    <td class=\"tableheadingbborder itadmin-hide\">");
/* 149 */               out.print(FormatUtil.getString("am.webclient.apikey.restapi.text"));
/* 150 */               out.write("</td>\n</tr>\n  <tr>\n    <td colspan=\"2\" class=\"message\">");
/* 151 */               out.print(FormatUtil.getString("am.webclient.apikey.table.msg1", new String[] { OEMUtil.getOEMString("product.name") }));
/* 152 */               out.write("  <br><div class=\"tdindent\">");
/* 153 */               out.print(FormatUtil.getString("am.webclient.apikey.text"));
/* 154 */               out.write("&nbsp;:&nbsp;<b>");
/* 155 */               out.print(request.getAttribute("APIKey"));
/* 156 */               out.write("</b></div><br>\n");
/* 157 */               out.print(FormatUtil.getString("am.webclient.apikey.table.msg2"));
/* 158 */               out.write("\n<br>\n");
/* 159 */               if ((request.getRemoteUser() != null) && (request.getRemoteUser().equals("admin")))
/*     */               {
/*     */ 
/* 162 */                 out.write(10);
/* 163 */                 out.print(FormatUtil.getString("am.webclient.apikey.table.msg3"));
/* 164 */                 out.write(10);
/*     */               }
/* 166 */               out.write("\n</td>\n  </tr>\n</table>\n<br>\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"left\">\n\t<tr>\n\t<td class=\"helpCardHdrTopLeft\"/>\n\t<td class=\"helpCardHdrTopBg\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t<tr>\n\t<td valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/* 167 */               out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/* 168 */               out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td>\n\t<td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t</tr>\n\t</table></td>\n\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\t</tr>\n\t<tr>\n\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t<td valign=\"top\">\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n    <tr>\n    <td  class=\"boxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\n      <tr>\n        <td valign=\"top\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n        <tr>\n          <td class=\"hCardInnerTopLeft\"/>\n          <td class=\"hCardInnerTopBg\"/>\n          <td class=\"hCardInnerTopRight\"/>\n        </tr>\n        <tr>\n\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n                <td class=\"hCardInnerBoxBg\" valign=\"top\">\n\n<table align=\"right\" cellpadding=\"0\" cellspacing=\"0\">\n\n<tr class=\"ithelplink-hide\">\n");
/* 169 */               out.write("<td></td>\n<td width=\"20\"></td>\n<td align=\"right\" >\n<a target=\"_blank\" href=\"/help/API/ExampleCreateOnce.html\" class=\"staticlinks-red\" align=\"right\">");
/* 170 */               out.print(FormatUtil.getString("am.webclient.apikey.exampleform.text"));
/* 171 */               out.write("</a><span class=\"ancillary1\"> | </span><a target=\"_blank\" href=\"/help/administrative-operation/applications-manager-api.html\" class=\"staticlinks-red\">");
/* 172 */               out.print(FormatUtil.getString("am.webclient.apikey.ref.key"));
/* 173 */               out.write("</a>\n\n</td>\n</tr>\n\n\n<tr><td colspan=\"3\" height=\"10\"></td></tr>\n\n\n<tr>\n<td valign=\"top\" colspan=\"3\">\n<table cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n<td >\n<table cellpadding=\"0\" cellspacing=\"0\" >\n<tr>\n<td>\n<span class=\"bodytext\" style=\"line-height:20px;\">\n\n<span style=\"font-family:Arial, Helvetica, sans-serif; color:#000; font-size:18px; line-height:30px; font-weight:bold;\">");
/* 174 */               out.print(FormatUtil.getString("am.webclient.apikey.restapikey.text"));
/* 175 */               out.write("</span><br><br>\n");
/* 176 */               out.print(FormatUtil.getString("am.webclient.apikey.disp.message1", new String[] { OEMUtil.getOEMString("company.name"), OEMUtil.getOEMString("product.shortname"), OEMUtil.getOEMString("product.name") }));
/* 177 */               out.write("<br>\n");
/* 178 */               out.print(FormatUtil.getString("am.webclient.apikey.disp.message2"));
/* 179 */               out.write("<br><br>\n");
/* 180 */               out.print(FormatUtil.getString("am.webclient.apikey.disp.message3", new String[] { OEMUtil.getOEMString("product.name"), OEMUtil.getOEMString("product.shortname") }));
/* 181 */               out.write("<br><br>\n</span>\n</td>\n</tr>\n</table>\n\n</td>\n<td width=\"20\"></td>\n<td width=\"559\" valign=\"top\">\n<table cellpadding=\"0\" cellspacing=\"0\" class=\"rest-api-flow-diagram\" width=\"559\">\n<tr>\n\n<td width=\"37\"></td>\n<td width=\"233\" valign=\"top\">\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"233\">\n\n<tr><td height=\"35\" colspan=\"3\"></td></tr>\n\n<tr>\n<td class=\"bodytext\" width=\"91\" align=\"center\" valign=\"middle\">&nbsp;");
/* 182 */               out.print(FormatUtil.getString("am.webclient.apikey.webportal.text"));
/* 183 */               out.write("</td>\n<td width=\"12\"></td>\n<td class=\"bodytext\" align=\"left\"><span style=\"margin-left:5px; float:left;\">");
/* 184 */               out.print(FormatUtil.getString("am.webclient.apikey.thirdpartyapplication.text"));
/* 185 */               out.write("</span></td>\n\n</tr>\n\n</table>\n\n<div class=\"bodytextbold\" style=\"position:absolute;margin-top:152px; margin-left:80px; \">&nbsp; ");
/* 186 */               out.print(FormatUtil.getString("am.webclient.apikey.api.text"));
/* 187 */               out.write("</div>\n\n\n<div class=\"bodytextbold\" style=\"position:absolute;margin-top:202px; margin-left:80px; \">&nbsp;");
/* 188 */               out.print(FormatUtil.getString("am.webclient.apikey.apm.text"));
/* 189 */               out.write("</div>\n\n\n\n</td>\n\n\n\n\n\n<td width=\"257\" valign=\"top\">\n<table cellpadding=\"0\" cellspacing=\"0\" style=\"margin:40px 0px 0px 0px;\">\n\n<tr>\n\n<td>\n\n\n\n\n\n<form id=\"two\" action=\"...\" method=\"post\">\n<fieldset id=\"personal\">\n<legend class=\"rest-api-txt\" style=\"height:13px; font-size:11px; color:#fff;\">");
/* 190 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text1"));
/* 191 */               out.write("</legend>\n\n<table width=\"100%\" cellpadding=\"3\" cellspacing=\"0\">\n<tr>\n<td class=\"rest-api-txt\"><label for=\"lastname\">");
/* 192 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text2"));
/* 193 */               out.write("</label></td>\n<td class=\"rest-api-txt\"><input class=\"bg-grey\" style=\"background-color: rgb(255, 255, 160); color:#000; height:18px; width:150px; font-size:9px; font-family:Arial, Helvetica, sans-serif;\" name=\"lastname\" id=\"lastname\" disabled=\"disabled\" value=\"49f35c39eeae2d74305\" tabindex=\"1\" type=\"text\"></td>\n</tr>\n\n<tr>\n<td class=\"rest-api-txt\"><label for=\"firstname\">");
/* 194 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text3"));
/* 195 */               out.write("</label></td>\n<td class=\"rest-api-txt\"><input class=\"bg-grey\" style=\"background-color: rgb(255, 255, 160); color:#000; height:18px; width:150px; font-size:9px; font-family:Arial, Helvetica, sans-serif;\"  name=\"lastname\" id=\"lastname\" disabled=\"disabled\" value=\"");
/* 196 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text4"));
/* 197 */               out.write("\" tabindex=\"1\" type=\"text\"></td>\n</tr>\n\n<tr>\n<td class=\"rest-api-txt\"> <label for=\"address\">");
/* 198 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text5"));
/* 199 */               out.write("</label></td>\n<td class=\"rest-api-txt\"><input class=\"bg-grey\" style=\"background-color: rgb(255, 255, 160); color:#000; height:18px; width:150px; font-size:9px; font-family:Arial, Helvetica, sans-serif;\"  name=\"lastname\" id=\"lastname\" disabled=\"disabled\" value=\"100000185\" tabindex=\"1\" type=\"text\"></td>\n</tr>\n\n\n<tr>\n<td class=\"rest-api-txt\"><label for=\"lastname\">");
/* 200 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text6"));
/* 201 */               out.write("</label></td>\n<td class=\"rest-api-txt\"><input class=\"bg-grey\" style=\"background-color: rgb(255, 255, 160); color:#000; height:18px; width:150px; font-size:9px; font-family:Arial, Helvetica, sans-serif;\"  name=\"lastname\" id=\"lastname\" disabled=\"disabled\" value=\"");
/* 202 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text7"));
/* 203 */               out.write("\" tabindex=\"1\" type=\"text\"></td>\n</tr>\n\n<tr>\n<td class=\"rest-api-txt\"><label for=\"firstname\">");
/* 204 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text8"));
/* 205 */               out.write("</label></td>\n<td class=\"rest-api-txt\"><input class=\"bg-grey\" style=\"background-color: rgb(255, 255, 160); color:#000; height:18px; width:150px; font-size:9px; font-family:Arial, Helvetica, sans-serif;\"   name=\"lastname\" id=\"lastname\" disabled=\"disabled\" value=\"");
/* 206 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text9"));
/* 207 */               out.write("\" tabindex=\"1\" type=\"text\"></td>\n</tr>\n\n<tr>\n<td class=\"rest-api-txt\"> <label for=\"address\">");
/* 208 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text10"));
/* 209 */               out.write("</label></td>\n<td class=\"rest-api-txt\"><input class=\"bg-grey\" style=\"background-color: rgb(255, 255, 160); color:#000; height:18px; width:150px; font-size:9px; font-family:Arial, Helvetica, sans-serif;\"  name=\"lastname\" id=\"lastname\" disabled=\"disabled\" value=\"");
/* 210 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text11"));
/* 211 */               out.write("\" tabindex=\"1\" type=\"text\"></td>\n</tr>\n\n\n<tr>\n<td class=\"rest-api-txt\"> <label for=\"address\"> ");
/* 212 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text12"));
/* 213 */               out.write("</label></td>\n<td class=\"rest-api-txt\"><input class=\"bg-grey\" style=\"background-color: rgb(255, 255, 160); color:#000; height:18px; width:150px; font-size:9px; font-family:Arial, Helvetica, sans-serif;\"  name=\"lastname\" id=\"lastname\" disabled=\"disabled\" value=\"");
/* 214 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text13"));
/* 215 */               out.write("\" tabindex=\"1\" type=\"text\"></td>\n</tr>\n\n<tr>\n<td class=\"rest-api-txt\"> <label for=\"address\">");
/* 216 */               out.print(FormatUtil.getString("am.webclient.apikey.img.text14"));
/* 217 */               out.write("</label></td>\n<td class=\"rest-api-txt\"><input class=\"bg-grey\" style=\"background-color: rgb(255, 255, 160); color:#000; height:18px; width:150px; font-size:9px; font-family:Arial, Helvetica, sans-serif;\"  name=\"lastname\" id=\"lastname\" disabled=\"disabled\" value=\"*****\" tabindex=\"1\" type=\"text\"></td>\n</tr>\n\n\n<tr><td colspan=\"2\" align=\"center\"><p> <input style=\"cursor:default; width:50px;  outline: none;\"  class=\"buttons btn_highlt\" value=\"Submit\"></p></td></tr>\n</table>\n\n\n</fieldset>\n\n\n</form>\n\n\n\n</td>\n\n</tr>\n\n\n</table>\n</td>\n<td width=\"11\"></td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n</td>\n\n\n\n</tr>\n\n<tr>\n<td colspan=\"3\">\n<table border=\"0\" width=\"100%\" align=\"center\" class=\"bodytext\" cellspacing=\"0\" cellpadding=\"0\" style=\"border:1px solid #e3e3e3; background-color:#fff; margin:20px 0px 20px 0px;\">\n<tr><td  width=\"100%\">\n<pre style=\"padding:10px;\">\n&lt;AppManager-response uri=\"AppManager/xml/ListAlarms\"&gt;\t");
/* 218 */               out.write("\n  &lt;result&gt;\t");
/* 219 */               out.write("\n    &lt;response response-code=\"4000\"&gt;\t");
/* 220 */               out.write("\n      &lt;Alarms&gt;\t");
/* 221 */               out.write("\n        &lt;Alarm RESOURCEID=\"10000057\" DISPLAYNAME=\"prod_SQL\" TYPE=\"MSSQL-DB-server\" TYPEDISPLAYNAME=\"MS SQL\" ");
/* 222 */               out.write("\n        SEVERITY=\"1\" STATUS=\"critical\"");
/* 223 */               out.write("\n        MESSAGE=\"Health of app.manager.com_MSSQL-DB-server_1433 is critical.&lt;br&gt;Root Cause : &lt;br&gt;1. ");
/* 224 */               out.write("\n        AMDB_QM is OFFLINE &lt;br&gt;2. AMDB_Ms is OFFLINE");
/* 225 */               out.write("\n        &lt;br&gt;3. AMDB_AD is OFFLINE &lt;br&gt;\" ATTRIBUTEID=\"3101\" MODTIME=\"1261214478384\"/&gt;\t");
/* 226 */               out.write("\n      &lt;/Alarms&gt;\t");
/* 227 */               out.write("\n    &lt;/response&gt\t");
/* 228 */               out.write("\n  &lt;/result&gt;<br>&lt;/AppManager-response&gt;\t");
/* 229 */               out.write("\n</pre>\n</td></tr>\n</table>\n\n\n\n</td>\n</tr>\n\n</table>\n\n\n\n\n<br><br>\n\n\n\n\n            </td>\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n        </tr>\n      </table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n</tr>\n<tr>\n<td class=\"helpCardMainBtmLeft\"/>\n<td class=\"helpCardMainBtmBg\"/>\n<td class=\"helpCardMainBtmRight\"/>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n</form>\n");
/* 230 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f0.doAfterBody();
/* 231 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 234 */             if (_jspx_eval_tiles_005fput_005f0 != 1) {
/* 235 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 238 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 239 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f0); return;
/*     */           }
/*     */           
/* 242 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f0);
/* 243 */           out.write(10);
/* 244 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 246 */           out.write("\n    ");
/* 247 */           if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 249 */           out.write(10);
/* 250 */           response.setContentType("text/html;charset=UTF-8");
/* 251 */           out.write(10);
/* 252 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 253 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 257 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 258 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*     */       }
/*     */       else {
/* 261 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 262 */         out.write(10);
/* 263 */         out.write(10);
/*     */       }
/* 265 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 266 */         out = _jspx_out;
/* 267 */         if ((out != null) && (out.getBufferSize() != 0))
/* 268 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 269 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 272 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 278 */     PageContext pageContext = _jspx_page_context;
/* 279 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 281 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 282 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 283 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 285 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 287 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 288 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 289 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 290 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 291 */       return true;
/*     */     }
/* 293 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 294 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 299 */     PageContext pageContext = _jspx_page_context;
/* 300 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 302 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 303 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 304 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 306 */     _jspx_th_tiles_005fput_005f1.setName("HelpContent");
/*     */     
/* 308 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/test.jsp");
/* 309 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 310 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 311 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 312 */       return true;
/*     */     }
/* 314 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 315 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 320 */     PageContext pageContext = _jspx_page_context;
/* 321 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 323 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 324 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 325 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 327 */     _jspx_th_tiles_005fput_005f2.setName("Footer");
/*     */     
/* 329 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/footer.jsp");
/* 330 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 331 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 332 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 333 */       return true;
/*     */     }
/* 335 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 336 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AdminApiKey_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */