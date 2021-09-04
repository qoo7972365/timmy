/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.util.ArrayList;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.logic.NotPresentTag;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.struts.taglib.tiles.InsertTag;
/*     */ import org.apache.struts.taglib.tiles.PutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ 
/*     */ public final class GettingStarted_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  40 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  44 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  52 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  56 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  57 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*  58 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*  59 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*  60 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  61 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  62 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
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
/*  79 */       response.setContentType("text/html;charset=UTF-8");
/*  80 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  82 */       _jspx_page_context = pageContext;
/*  83 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  84 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  85 */       session = pageContext.getSession();
/*  86 */       out = pageContext.getOut();
/*  87 */       _jspx_out = out;
/*     */       
/*  89 */       out.write("<!DOCTYPE html>\n");
/*  90 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  91 */       out.write("\n\n\n\n\n\n\n    \n\n\n\n\n\n\n\n\n    <style type=\"text/css\">\n        .width {\n\t\t\t width:50%;\n\t\t\t min-width:900px;\n\t\t\t background:#fff;\n\t\t}\n    </style>  \n    \n    \n<script>\n");
/*  92 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/*  94 */       out.write("\n</script>\n\n");
/*     */       
/*  96 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/*  97 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/*  98 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*     */       
/* 100 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 101 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 102 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*     */         for (;;) {
/* 104 */           out.write(10);
/*     */           
/* 106 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 107 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 108 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*     */           
/* 110 */           _jspx_th_tiles_005fput_005f0.setName("title");
/*     */           
/* 112 */           _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.gettingstarted.title"));
/* 113 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 114 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 115 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*     */           }
/*     */           
/* 118 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 119 */           out.write(10);
/* 120 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 122 */           out.write("  \n\n");
/*     */           
/* 124 */           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 125 */           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 126 */           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*     */           
/* 128 */           _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*     */           
/* 130 */           _jspx_th_tiles_005fput_005f2.setType("string");
/* 131 */           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 132 */           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 133 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 134 */               out = _jspx_page_context.pushBody();
/* 135 */               _jspx_th_tiles_005fput_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 136 */               _jspx_th_tiles_005fput_005f2.doInitBody();
/*     */             }
/*     */             for (;;) {
/* 139 */               out.write("  \n<link href=\"/images/getstarted.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n<script type=\"text/javascript\" src=\"/template/jquery.tools.min.js\"></script>\n<script type=\"text/javascript\" src=\"/template/fileuploader.js\"></script>\n");
/* 140 */               String locale = System.getProperty("locale");
/* 141 */               out.write("\n<script type=\"text/javascript\" src=\"https://www.manageengine.com/products/applications_manager/js/product-marketing-feed.js?locale=");
/* 142 */               out.print(locale);
/* 143 */               out.print(System.getProperty("did") != null ? "&" + System.getProperty("did") : "");
/* 144 */               out.write("\"></script>\n");
/*     */               
/* 146 */               String selectedStep = request.getParameter("step") != null ? request.getParameter("step") : "step0";
/* 147 */               ArrayList taborderList = (ArrayList)com.adventnet.appmanager.util.DBUtil.taborderList.get(request.getRemoteUser().toLowerCase());
/* 148 */               String setTabinCookie = "1_1";
/* 149 */               if (taborderList != null) {
/* 150 */                 ArrayList list = (ArrayList)taborderList.get(0);
/* 151 */                 setTabinCookie = (String)list.get(1) + "_" + (String)list.get(3);
/*     */               }
/* 153 */               FreeEditionDetails fd = FreeEditionDetails.getFreeEditionDetails();
/* 154 */               String user = fd.getUserType();
/* 155 */               String evaluationdays = fd.getEvaluationDays() + "";
/* 156 */               boolean showOnlineContent = (user.equals("T")) && ("29".equals(evaluationdays));
/* 157 */               boolean showPreRequisites = false;
/*     */               
/* 159 */               String getstartedstyle = "DISPLAY:block";
/* 160 */               String prereqstyle = "DISPLAY:none";
/* 161 */               String selectedTab = "am.webclient.introtab.gettingStarted";
/* 162 */               String smtpSelectedTab = "am.webclient.mailsettings.primary.text";
/* 163 */               if (showPreRequisites)
/*     */               {
/* 165 */                 getstartedstyle = "DISPLAY:none";
/* 166 */                 prereqstyle = "DISPLAY:block";
/*     */               }
/* 168 */               double randnumber = Math.random();
/* 169 */               pageContext.setAttribute("isOEM", Boolean.valueOf(com.adventnet.appmanager.util.OEMUtil.isOEM()));
/*     */               
/* 171 */               out.write("\n\n<div id=\"getstarted\" style=\"");
/* 172 */               out.print(getstartedstyle);
/* 173 */               out.write("\">\n  \n  <div id=\"offlineContent1\" style=\"display:block;\">\n  ");
/*     */               
/* 175 */               String gettingStartedImgName = "img_gettingstarted_steps.gif";
/* 176 */               boolean enterprise = (EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (request.isUserInRole("ENTERPRISEADMIN"));
/* 177 */               if (enterprise)
/*     */               {
/* 179 */                 gettingStartedImgName = "gettingstarted_enterprise.gif";
/*     */               }
/*     */               
/* 182 */               out.write("\n  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n      <td width=\"50%\" valign=\"top\">\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n          <tr> \n            <td>\n              ");
/* 183 */               if ((EnterpriseUtil.isAdminServer()) || (EnterpriseUtil.isManagedServer()) || (request.isUserInRole("ENTERPRISEADMIN"))) {
/* 184 */                 out.write("\n              <table width=\"802\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"introimageadming\"  align=\"center\">\n                <tr> \n                  <td colspan=\"3\" class=\"bodytextbold\">");
/* 185 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.enterprisearchitecture.text", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }));
/* 186 */                 out.write("</td>\n                  <td width=\"222\">&nbsp;</td>\n                </tr>\n                <tr> \n                  <td width=\"313\">&nbsp;</td>\n                  <td width=\"154\">&nbsp;</td>\n                  <td width=\"113\">&nbsp;</td>\n                  <td>&nbsp;</td>\n                </tr>\n                <tr> \n                  <td>&nbsp;</td>\n                  <td rowspan=\"2\" align=\"center\" valign=\"bottom\" ><span class=\"bodytextbold\">");
/* 187 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.adminserver.text"));
/* 188 */                 out.write("</span><br> \n                    <span class=\"footer\">");
/* 189 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.500monitors.text"));
/* 190 */                 out.write("</span> \n                  </td>\n                  <td colspan=\"2\" align=\"left\" class=\"bodytext\">");
/* 191 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.reports.text"));
/* 192 */                 out.write("</td>\n                </tr>\n                <tr> \n                  <td height=\"28\">&nbsp;</td>\n                  <td>&nbsp;</td>\n                  <td>&nbsp;</td>\n                </tr>\n                <tr> \n                  <td height=\"125\">&nbsp;</td>\n                  <td align=\"center\" valign=\"bottom\" >&nbsp;</td>\n                  <td>&nbsp;</td>\n                  <td>&nbsp;</td>\n                </tr>\n                <tr> \n                  <td height=\"19\" align=\"center\" class=\"footer\">");
/* 193 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.200monitors.text"));
/* 194 */                 out.write("</td>\n                  <td align=\"center\" class=\"footer\">");
/* 195 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.150monitors.text"));
/* 196 */                 out.write("</td>\n                  <td align=\"right\" class=\"footer\">&nbsp;</td>\n                  <td class=\"footer\">");
/* 197 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.200monitors.text"));
/* 198 */                 out.write("</td>\n                </tr>\n                <tr> \n                  <td height=\"60\" valign=\"bottom\" class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 199 */                 out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/* 200 */                 out.write("</td>\n                  <td colspan=\"2\" align=\"left\" valign=\"bottom\" class=\"bodytext\">&nbsp; \n                    ");
/* 201 */                 out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/* 202 */                 out.write("</td>\n                  <td valign=\"middle\" class=\"bodytext\">");
/* 203 */                 out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/* 204 */                 out.write("</td>\n                </tr>\n                <tr> \n                  <td height=\"86\" valign=\"bottom\" class=\"bodytextbold\">");
/* 205 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.mgdserver1.text"));
/* 206 */                 out.write("</td>\n                  <td colspan=\"2\" align=\"center\" valign=\"bottom\" class=\"bodytextbold\">");
/* 207 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.mgdserver2.text"));
/* 208 */                 out.write("</td>\n                  <td align=\"right\" valign=\"bottom\" class=\"bodytextbold\">");
/* 209 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.mgdserver3.text"));
/* 210 */                 out.write("</td>\n                </tr>\n                <tr> \n                  <td>&nbsp;</td>\n                  <td align=\"center\" valign=\"bottom\" >&nbsp;</td>\n                  <td>&nbsp;</td>\n                  <td>&nbsp;</td>\n                </tr>\n                <tr> \n                  <td height=\"66\" align=\"right\" valign=\"top\" class=\"bodytext\"><br> ");
/* 211 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.configuremonitors.text"));
/* 212 */                 out.write("</td>\n                  <td align=\"center\" valign=\"bottom\" >&nbsp;</td>\n                  <td>&nbsp;</td>\n                  <td class=\"bodytext\"><br> ");
/* 213 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.configthreshold.text"));
/* 214 */                 out.write("</td>\n                </tr>\n              </table>\n              <table width=\"98%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" >\n                <tr class=\"yellowgrayborder\">\n                  <td width=\"1%\"  >&nbsp;</td>\n                  <td class=\"bodytextbold\" >");
/* 215 */                 out.print(FormatUtil.getString("am.webclient.historydata.note.text"));
/* 216 */                 out.write("</td>\n                  <td width=\"22%\" height=\"19\" ></td>\n                </tr>\n                <tr>\n                  <td width=\"1%\" >&nbsp;</td>\n                  <td class=\"bodytext\" ><br>");
/* 217 */                 out.print(FormatUtil.getString("am.webclient.managedserver.gettingstartedmessage"));
/* 218 */                 out.write("<br></td>\n                </tr>   \n              </table>  \n              ");
/*     */               } else {
/* 220 */                 out.write("\n              <table width=\"930\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"introimage\" align=\"left\">\n                <tr> \n                  <td width=\"326\" height=\"19\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"10\"></td>\n                  <td width=\"1\" rowspan=\"11\">&nbsp;</td>\n                  <td width=\"180\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"10\"></td>\n                  <td width=\"1\" rowspan=\"7\">&nbsp;</td>\n                  <td width=\"119\" rowspan=\"7\" align=\"left\" valign=\"bottom\" class=\"bodytext11white\"> \n                    ");
/* 221 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.action.text"));
/* 222 */                 out.write("</td>\n                  <td width=\"109\" rowspan=\"7\">&nbsp;</td>\n                  <td width=\"49\" rowspan=\"10\">&nbsp;</td>\n                </tr>\n                <tr> \n                  <td height=\"31\" align=\"center\" class=\"bodytextbold\" class=\"staticlinks\" title=\"");
/* 223 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.ntk.mg"));
/* 224 */                 out.write(34);
/* 225 */                 out.write(62);
/* 226 */                 out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/* 227 */                 out.write("</td>\n                  <td valign=\"bottom\" class=\"bodytextbold\" >");
/* 228 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.attribute.text"));
/* 229 */                 out.write("</td>\n                </tr>\n                <tr> \n                  <td height=\"23\">&nbsp;</td>\n                  <td class=\"bodytext\">");
/* 230 */                 out.print(FormatUtil.getString("am.webclient.traplistener.status"));
/* 231 */                 out.write(":</td>\n                </tr>\n                <tr> \n                  <td height=\"19\" align=\"center\" class=\"bodytext\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/showresource.do?group=All&method=showResourceTypes\" class=\"staticlinks\" title=\"");
/* 232 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.ntk.monitor"));
/* 233 */                 out.write("\"><span class=\"bodytextbold\">");
/* 234 */                 out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/* 235 */                 out.write("</span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\n                  <td align=\"center\" valign=\"bottom\" class=\"bodytext\">");
/* 236 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.threshold.text"));
/* 237 */                 out.write("&nbsp;&nbsp;&nbsp; \n                    &nbsp;&nbsp; &nbsp; </td>\n                </tr>\n                <tr> \n                  <td height=\"19\">&nbsp;</td>\n                  <td>&nbsp;</td>\n                </tr>\n                <tr> \n                  <td height=\"28\" align=\"right\" valign=\"middle\" class=\"bodytext\">&nbsp;&nbsp;&nbsp;</td>\n                  <td>&nbsp;</td>\n                </tr>\n                <tr> \n                  <td height=\"42\" valign=\"top\" class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \n                    &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;</td>\n                  <td align=\"left\" valign=\"bottom\" class=\"bodytext11white\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"10\">");
/* 238 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.thresholdlimit.text"));
/* 239 */                 out.write("</td>\n                </tr>\n                <tr> \n                  <td><img src=\"../images/spacer.gif\" width=\"1\" height=\"10\"></td>\n                  <td colspan=\"4\" align=\"center\" valign=\"bottom\" class=\"bodytext\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"10\"></td>\n                </tr>\n                <tr>\n                  <td rowspan=\"3\">&nbsp;</td>                 \n                  ");
/*     */                 
/* 241 */                 PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 242 */                 _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 243 */                 _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*     */                 
/* 245 */                 _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 246 */                 int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 247 */                 if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*     */                   for (;;) {
/* 249 */                     out.write("\n                   <td align=\"left\" valign=\"bottom\" class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true\" class=\"staticlinks\"><b>");
/* 250 */                     out.print(FormatUtil.getString("am.webclient.configurealert.configurealerts"));
/* 251 */                     out.write("</b></a></td>\n                   <td height=\"25\">&nbsp;</td>\n                   <td colspan=\"2\" align=\"center\" valign=\"bottom\" class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>");
/* 252 */                     out.print(FormatUtil.getString("am.webclient.gettingstarted.receivenotice.text"));
/* 253 */                     out.write("</b></td>\n                  ");
/* 254 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 255 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 259 */                 if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 260 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*     */                 }
/*     */                 
/* 263 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 264 */                 out.write("  \n                  ");
/*     */                 
/* 266 */                 PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 267 */                 _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 268 */                 _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*     */                 
/* 270 */                 _jspx_th_logic_005fpresent_005f2.setRole("OPERATOR");
/* 271 */                 int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 272 */                 if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*     */                   for (;;) {
/* 274 */                     out.write("\n                    <td align=\"left\" valign=\"bottom\" class=\"bodytext\" enableClass=\"staticlinks\">");
/* 275 */                     out.print(FormatUtil.getString("am.webclient.toolbar.configurealert.text"));
/* 276 */                     out.write("</td>                  \n                    <td height=\"25\">&nbsp;</td>\n                    <td colspan=\"2\" align=\"center\" valign=\"bottom\" class=\"bodytext\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>");
/* 277 */                     out.print(FormatUtil.getString("am.webclient.gettingstarted.receivenotice.text"));
/* 278 */                     out.write("</b></td>\n                  ");
/* 279 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 280 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 284 */                 if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 285 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*     */                 }
/*     */                 
/* 288 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 289 */                 out.write("\n                </tr>\n                <tr>\n                  <td height=\"19\" colspan=\"4\" align=\"center\" class=\"bodytext\">&nbsp;</td>\n                </tr>\n                <tr> \n                  <td height=\"26\" colspan=\"5\" align=\"center\" valign=\"top\" class=\"bodytextbold\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>\n                </tr>\n                <tr>\n                  <td colspan=\"7\" width=\"100%\" rowspan=\"3\">\n                    ");
/*     */                 
/* 291 */                 if ((!EnterpriseUtil.isAdminServer()) && (!request.isUserInRole("ENTERPRISEADMIN")))
/*     */                 {
/* 293 */                   out.write("\n                    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n                      <tr>\n                        <td>\n                          <div class=\"apm-info \">\n                            <span class=\"terms\"> &nbsp; &nbsp;");
/* 294 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.heading"));
/* 295 */                   out.write("</span>\n                            <ul id=\"content-list\">\n                              <li>\n                                <h2 id=\"home\">");
/* 296 */                   out.print(FormatUtil.getString("am.webclient.common.util.MGSTR"));
/* 297 */                   out.write("</h2>");
/* 298 */                   out.write("\n                                 <p>");
/* 299 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.ntk.mg"));
/* 300 */                   out.write("</p>\n                              </li>\n                              <li>\n                                <h2 id=\"calendar\">");
/* 301 */                   out.print(FormatUtil.getString("am.webclient.camscreen.monitor.text"));
/* 302 */                   out.write("</h2>");
/* 303 */                   out.write("\n                                 <p>");
/* 304 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.ntk.monitor"));
/* 305 */                   out.write("</p>\n                              </li>\n                              <li>\n                                <h2 id=\"info\">");
/* 306 */                   out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/* 307 */                   out.write("</h2>");
/* 308 */                   out.write("\n                                 <p>");
/* 309 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.ntk.alerts"));
/* 310 */                   out.write("</p>\n                              </li>\n                              <li style=\"clear:left;\">\n                                <h2 id=\"user\">");
/* 311 */                   out.print(FormatUtil.getString("table.heading.status"));
/* 312 */                   out.write("</h2>");
/* 313 */                   out.write("\n                                 <p>");
/* 314 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.ntk.threshold"));
/* 315 */                   out.write("</p>\n                              </li>\n                              <li>\n                                <h2 id=\"monitor\">");
/* 316 */                   out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 317 */                   out.write("</h2>");
/* 318 */                   out.write("\n                                 <p>");
/* 319 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.ntk.actions"));
/* 320 */                   out.write("</p>\n                              </li>\n                              <li>\n                                <h2 id=\"rss\">");
/* 321 */                   out.print(FormatUtil.getString("am.webclient.camscreen.reports.text"));
/* 322 */                   out.write("</h2>");
/* 323 */                   out.write("\n                                <p>");
/* 324 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.ntk.reports"));
/* 325 */                   out.write("</p>\n                              </li>\n                            </ul>\n                          </div>\n                        </td>\n                      </tr>\n                    </table>\n                    ");
/*     */                 }
/* 327 */                 out.write("\n                  </td>\n                </tr>\n              </table>\n                ");
/*     */               }
/* 329 */               out.write("\n                </td>\n              </tr>\n              </table>             \n            </td>\n          </tr>\n        </table>\n\n      </td>\n      <td width=\"2%\" align=\"center\"></td>\n      <td id=\"rightLinks\" align=\"right\" valign=\"top\" style=\"padding-top:5px\">\n        <table width=\"99%\" cellpadding=\"0\" cellspacing=\"0\" class=\"getting-start-box\" aling=\"center\">\n          ");
/* 330 */               if (request.getRemoteUser().equals("admin")) {
/* 331 */                 out.write("\n            <tr>\n              <td align=\"left\">       \n                <div id=\"mgTabMGActions\" style=\"display: inline; \">\n                  <table border=\"0\" style=\"position: relative; bottom: 2px;\" cellspacing=\"0\" cellpadding=\"0\">      \n                    <tr>\n                      <td align=\"left\"><button id=\"setup\" class=\"button-getting-start btn_highlt\">");
/* 332 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.ntk.wizard"));
/* 333 */                 out.write("</button></td>");
/* 334 */                 out.write("\n                    </tr>          \n                    <tr><td height=\"10\"></td></tr>\n                    <tr>\n                      <td align=\"left\" class=\"getting-start-inner-txt bodytext\" valign=\"top\">");
/* 335 */                 out.print(FormatUtil.getString("am.webclient.gettingstarted.description"));
/* 336 */                 out.write("</td>  \n                    </tr>\n                  </table>\n                </div>       \n              </td>        \n            </tr> \n            <tr><td height=\"20\"></td></tr>\n          ");
/*     */               }
/* 338 */               out.write("\n         ");
/*     */               
/* 340 */               IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 341 */               _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 342 */               _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*     */               
/* 344 */               _jspx_th_c_005fif_005f0.setTest("${isOEM=='false'}");
/* 345 */               int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 346 */               if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */                 for (;;) {
/* 348 */                   out.write("\n          <tr>\n            <td>\n              <div id=\"onlineContent\" style=\"display:none;\">\n                \n              </div>\n              <div id=\"offlineContent\" style=\"display:none;\">\n                <!--<ul id=\"slide\">\n                  <li class=\"current bg\"><b>");
/* 349 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.productinfo"));
/* 350 */                   out.write("</b>\n                    <div>\n                      <a class=\"big\" title=\"Watch Video - Take a Tour\"  href=\"http://www.manageengine.com/products/applications_manager/application-performance-management-solution/");
/* 351 */                   out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 352 */                   out.write("\" target=\"_blank\"><img src=\"images/watch-video.png\" style=\"margin:0px; position:relative; top:5px;\"> ");
/* 353 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.productinfo.link1.txt"));
/* 354 */                   out.write(" </a>\n                      <a class=\"big\" href=\"http://www.manageengine.com/products/applications_manager/me-appmanager-overview.pdf");
/* 355 */                   out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 356 */                   out.write("\" target=\"_blank\"><img src=\"images/icon_pdf.gif\"> ");
/* 357 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.productinfo.link2.txt"));
/* 358 */                   out.write("</a>\n                      <a class=\"big\" href=\"http://apm.manageengine.com/\" target=\"_blank\"><img src=\"images/custom-monitor-icon.gif\"> ");
/* 359 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.productinfo.link3.txt"));
/* 360 */                   out.write("</a>\n                      <a class=\"big\" href=\"http://www.manageengine.com/products/applications_manager/screenshots.html");
/* 361 */                   out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 362 */                   out.write("\" target=\"_blank\"><img src=\"images/icon_table.gif\"> ");
/* 363 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.productinfo.link4.txt"));
/* 364 */                   out.write("</a>\n                      <a style=\"border:none;\" class=\"big\" href=\"http://www.manageengine.com/products/applications_manager/applications-management-productfaq.html");
/* 365 */                   out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 366 */                   out.write("\" target=\"_blank\"><img src=\"images/icon_set_default.gif\"> ");
/* 367 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.productinfo.link5.txt"));
/* 368 */                   out.write("</a>\n                    </div>\n                  </li>\n                </ul>-->\n                <div id=\"left_col_demo\"><div id=\"left_col_inner_demo\"><h3>");
/* 369 */                   out.print(FormatUtil.getString("am.product.resources.txt"));
/* 370 */                   out.write("</h3></div></div>\n                <div id=\"left_col_demo\">\n                  <div id=\"left_col_inner_demo\">\n                    <p style=\"padding:10px;font-size:11px; font-weight:normal;color:#4c596e; font-family:Arial, Helvetica, sans-serif;\"> ");
/* 371 */                   out.print(FormatUtil.getString("am.product.nointernet.message.txt"));
/* 372 */                   out.write("</p>                    \n                  </div>\n                </div>\n              </div>\n            </td>\n          </tr>\n      ");
/* 373 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 374 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 378 */               if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 379 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*     */               }
/*     */               
/* 382 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 383 */               out.write("\n        </table>\n      </td>\n    </tr>\n  </table>\n  ");
/*     */               
/* 385 */               PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 386 */               _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 387 */               _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*     */               
/* 389 */               _jspx_th_logic_005fpresent_005f3.setRole("ADMIN,ENTERPRISEADMIN");
/* 390 */               int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 391 */               if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*     */                 for (;;) {
/* 393 */                   out.write("\n    <table width=\"100%\" align=\"right\">\n      <tr>\n        <td align=\"left\" style=\"left:15px;margin-top:20px;position:relative;\">\n          <a href=\"javascript:fnCloseGettingStarted()\" class=\"staticlinks\" style=\"bottom:15px;\">");
/* 394 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.donotshowagain"));
/* 395 */                   out.write("</a></td>\n      </td>\n      </tr>\n    </table>\n  ");
/* 396 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 397 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 401 */               if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 402 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*     */               }
/*     */               
/* 405 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 406 */               out.write("\n\n  </div>\n</div>\n<div id=\"facebox\" style=\"position:absolute !important;top:0px;left:10%;display:none\">\n  <h2>\n    <span style=\"display:inline;width:90%;margin-bottom: 9px;\"><img src=\"/images/setup_apm.png\" style=\"position: relative; top: 8px; padding: 2px;\" />");
/* 407 */               out.print(FormatUtil.getString("am.webclient.setup.txt", new String[] { FormatUtil.getString("webclient.mainviewpage.title") }));
/* 408 */               out.write("</span>\n    <span style=\"display:inline;width:9%;\"><button id=\"close-btn\" class=\"buttons\" style=\"float:right; margin-top: 10px; padding: 5px 20px;display:inline-block;font-size:13px;border-bottom:0px\">");
/* 409 */               out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 410 */               out.write("</button></span>\n  </h2>\n  <div style=\"background-color: #fff\">\n    <span id=\"main\">\n      ");
/*     */               
/* 412 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 413 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 414 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*     */               
/* 416 */               _jspx_th_html_005fform_005f0.setAction("/adminAction.do?method=preRequisitesConfiguration");
/* 417 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 418 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */                 for (;;) {
/* 420 */                   out.write("\n        <fieldset id=\"smtp\">\n          <span class=\"wizard-img\"><img src=\"/images/send-email.png\"></span>\n          <legend style=\"float: left;\">");
/* 421 */                   out.print(FormatUtil.getString("am.webclient.mailsettings.configuremailserver.text"));
/* 422 */                   out.write("</legend>\n          <span class=\"wizard-desc\">");
/* 423 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisites.smtp.description"));
/* 424 */                   out.write("</span>\n          <div class=\"width\">\n            <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" id=\"InnerTab");
/* 425 */                   out.print(randnumber);
/* 426 */                   out.write("\" style=\"padding-top: 15px;\" widht=\"95%\">\n              <tbody>\n                <tr class=\"tabBtmLine\" style=\"background-position: bottom;\">\n                  <td width=\"1%\"/>&nbsp;&nbsp;&nbsp;&nbsp;\n                  <td width=\"12%\">\n                    <table title=\"");
/* 427 */                   out.print(FormatUtil.getString("am.webclient.mailsettings.primary.text"));
/* 428 */                   out.write("\" id=\"am.webclient.mailsettings.primary.textTab\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\" >\n                    <tbody>\n                      <tr>\n                        <td ");
/* 429 */                   if (!smtpSelectedTab.equals("am.webclient.mailsettings.primary.text")) {
/* 430 */                     out.write("class=\"tbUnselected_Left\"");
/*     */                   }
/* 432 */                   if (smtpSelectedTab.equals("am.webclient.mailsettings.primary.text")) {
/* 433 */                     out.write("class=\"tbSelected_Left\"");
/*     */                   }
/* 435 */                   out.write(" ><img width=\"1\" height=\"1\" alt=\"spacer\" src=\"/images/spacer.gif\"/></td>\n                        <td ");
/* 436 */                   if (!smtpSelectedTab.equals("am.webclient.mailsettings.primary.text")) {
/* 437 */                     out.write("class=\"tbUnselected_Middle\"");
/*     */                   }
/* 439 */                   out.write(32);
/* 440 */                   if (smtpSelectedTab.equals("am.webclient.mailsettings.primary.text")) {
/* 441 */                     out.write("class=\"tbSelected_Middle\"");
/*     */                   }
/* 443 */                   out.write(" onClick=\"javascript:SetTabStyle('am.webclient.mailsettings.primary.text','InnerTab");
/* 444 */                   out.print(randnumber);
/* 445 */                   out.write("');javascript:showPrimarySmtp();\" style=\"padding-left:5px;padding-right:5px\">\n                          <span  ");
/* 446 */                   if (!smtpSelectedTab.equals("am.webclient.mailsettings.primary.text")) {
/* 447 */                     out.write(" class=\"tabLink\"");
/*     */                   }
/* 449 */                   out.write(32);
/* 450 */                   if (smtpSelectedTab.equals("am.webclient.mailsettings.primary.text")) {
/* 451 */                     out.write("class=\"tabLinkActive\"");
/*     */                   }
/* 453 */                   out.write(" id=\"A_am.webclient.mailsettings.primary.textTab\">");
/* 454 */                   out.print(FormatUtil.getString("am.webclient.mailsettings.primary.text"));
/* 455 */                   out.write("</span>\n                        </td>\n                        <td ");
/* 456 */                   if (!smtpSelectedTab.equals("am.webclient.mailsettings.primary.text")) {
/* 457 */                     out.write("class=\"tbUnselected_Right\"");
/*     */                   }
/* 459 */                   out.write(32);
/* 460 */                   if (smtpSelectedTab.equals("am.webclient.mailsettings.primary.text")) {
/* 461 */                     out.write("class=\"tbSelected_Right\"");
/*     */                   }
/* 463 */                   out.write("><img width=\"1\" height=\"1\" alt=\"spacer\" src=\"/images/spacer.gif\"/></td>\n                      </tr>\n                    </tbody>\n                    </table>\n                  </td>\n                  <td width=\"20%\">\n                    <table title=\"");
/* 464 */                   out.print(FormatUtil.getString("am.webclient.mailsettings.secondary.text"));
/* 465 */                   out.write("\" id=\"am.webclient.mailsettings.secondary.textTab\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\" >\n                    <tbody>\n                      <tr>\n                        <td ");
/* 466 */                   if (!smtpSelectedTab.equals("am.webclient.mailsettings.secondary.text")) {
/* 467 */                     out.write("class=\"tbUnselected_Left\"");
/*     */                   }
/* 469 */                   if (smtpSelectedTab.equals("am.webclient.mailsettings.secondary.text")) {
/* 470 */                     out.write("class=\"tbSelected_Left\"");
/*     */                   }
/* 472 */                   out.write(" ><img width=\"1\" height=\"1\" alt=\"spacer\" src=\"/images/spacer.gif\"/></td>\n                        <td ");
/* 473 */                   if (!smtpSelectedTab.equals("am.webclient.mailsettings.secondary.text")) {
/* 474 */                     out.write("class=\"tbUnselected_Middle\"");
/*     */                   }
/* 476 */                   out.write(32);
/* 477 */                   if (smtpSelectedTab.equals("am.webclient.mailsettings.secondary.text")) {
/* 478 */                     out.write("class=\"tbSelected_Middle\"");
/*     */                   }
/* 480 */                   out.write(" onClick=\"javascript:SetTabStyle('am.webclient.mailsettings.secondary.text','InnerTab");
/* 481 */                   out.print(randnumber);
/* 482 */                   out.write("');javascript:showSecondarySmtp();\" style=\"padding-left:5px;padding-right:5px\">\n                          <span  ");
/* 483 */                   if (!smtpSelectedTab.equals("am.webclient.mailsettings.secondary.text")) {
/* 484 */                     out.write(" class=\"tabLink\"");
/*     */                   }
/* 486 */                   out.write(32);
/* 487 */                   if (smtpSelectedTab.equals("am.webclient.mailsettings.secondary.text")) {
/* 488 */                     out.write("class=\"tabLinkActive\"");
/*     */                   }
/* 490 */                   out.write(" id=\"A_am.webclient.mailsettings.secondary.textTab\">");
/* 491 */                   out.print(FormatUtil.getString("am.webclient.mailsettings.secondary.text"));
/* 492 */                   out.write("</span>\n                        </td>\n                        <td ");
/* 493 */                   if (!smtpSelectedTab.equals("am.webclient.mailsettings.secondary.text")) {
/* 494 */                     out.write("class=\"tbUnselected_Right\"");
/*     */                   }
/* 496 */                   out.write(32);
/* 497 */                   if (smtpSelectedTab.equals("am.webclient.mailsettings.secondary.text")) {
/* 498 */                     out.write("class=\"tbSelected_Right\"");
/*     */                   }
/* 500 */                   out.write("><img width=\"1\" height=\"1\" alt=\"spacer\" src=\"/images/spacer.gif\"/></td>\n                      </tr>\n                    </tbody>\n                    </table>\n                  </td> \n                  <td>&nbsp;</td>                 \n                </tr>\n              </tbody>\n            </table>\n          </div>\n          <table id=\"primarySmtp\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"content\" width=\"100%\">\n            <tr>\n              <td >\n                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" >\n                  <tr>\n                    <td valign=\"top\" >\n                      <label for=\"SMTP Server\">");
/* 501 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpservername"));
/* 502 */                   out.write("<span class=\"mandatory\">*</span></label>\n                      <input id=\"smtpServer\" name=\"smtpServer\" type=\"text\"placeholder=\"");
/* 503 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpservername"));
/* 504 */                   out.write("\" class=\"inputClass\"/>\n                    </td>\n                    <td width=\"2%\"></td>\n                    <td valign=\"top\" >\n                      <label for=\"SMTP Port\">");
/* 505 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 506 */                   out.write("<span class=\"mandatory\">*</span> </label> \n                      <input id=\"smtpPort\" name=\"smtpPort\" type=\"text\" placeholder=\"");
/* 507 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 508 */                   out.write("\" class=\"inputClass\"/>\n                    </td>\n                  </tr>\n                  <tr>\n                    <td colspan=\"3\" style=\"height: 20px;\"></td>\n                  </tr>\n                  <tr>\n                    <td colspan=\"3\" >\n                      <table cellpadding=\"5\" cellspacing=\"0\" width=\"83%\" style=\"background-color: #fcfcfc; border:1px solid #f5f5f5;  padding:10px 10px 10px 10px; border-radius: 10px 10px 10px 10px;\">\n                        <tr>\n                          <td colspan=\"3\" class=\"bodytextbold\"><span style=\"font-size: 13px; margin: 10px 0px 0px 6px; position: relative; top: 5px;\">");
/* 509 */                   out.print(FormatUtil.getString("am.webclient.mailserver.smtpreqAuthentication"));
/* 510 */                   out.write("</span></td>\n                        </tr>\n                        <tr>\n                          <td valign=\"top\" width=\"49%\"><input id=\"smtpUserName\" name=\"smtpUserName\" placeholder=\"");
/* 511 */                   out.print(FormatUtil.getString("am.product.smtpserver.username.txt"));
/* 512 */                   out.write("\" autoComplete=\"off\" type=\"text\" class=\"inputClass\" />\n                          </td>\n                          <td width=\"2%\"></td>\n                          <td valign=\"top\"><input id=\"smtpPassword\" name=\"smtpPassword\" placeholder=\"");
/* 513 */                   out.print(FormatUtil.getString("am.product.smtpserver.password.txt"));
/* 514 */                   out.write("\" autoComplete=\"off\" type=\"password\" class=\"inputClass\" />\n                          </td>\n                        </tr>\n                        <tr>\n                          <td>\n                            <div style=\"display:block;padding-left:0px;\"><input type=\"checkbox\" name=\"prmTlsAuth\" id=\"prmTlsAuth\" class=\"wizard-chkbox\" value=\"true\" style=\"position:relative; top:3px;\"><span class=\"wizard-chkboxtd\">");
/* 515 */                   out.print(FormatUtil.getString("TLS Authentication Enabled"));
/* 516 */                   out.write("</span></div>\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"prmTlsAuth\" value=\"false\"/>\t                         \n                          </td>\n                         <td width=\"2%\"/>\n                          <td>\n                            <div style=\"display:block;padding-left:0px;\"><input type=\"checkbox\" name=\"prmSslAuth\" id=\"prmSslAuth\" class=\"wizard-chkbox\" value=\"true\" style=\"position:relative; top:3px;\"><span class=\"wizard-chkboxtd\">");
/* 517 */                   out.print(FormatUtil.getString("SSL Authentication Enabled"));
/* 518 */                   out.write("</span></div>\n                          \t<input type=\"hidden\" name=\"prmSslAuth\" value=\"false\"/>\n                          </td>\n                        </tr>\n                      </table>\n                    </td>\n                  </tr>\n                </table>\n              </td>\n              \n              <td width=\"30%\" valign=\"top\">\n                <div id=\"testPrmSmtpServer\" align=\"center\">\n                  <button class=\"TestPrmSmtpServer buttons\" style=\"padding-top: 5px; padding-right: 10px; padding-bottom: 5px; padding-left: 10px; margin-top: 10px; display: inline-block;\" type=\"button\"> ");
/* 519 */                   out.print(FormatUtil.getString("am.product.smtpserver.test.txt"));
/* 520 */                   out.write(" </button>\n                  <div id=\"testPrmSmtpServer_rsp\" style=\"display:block;\"></div>\n                </div>\n              </td>\n            </tr>\n            \n            <tr>\n              <td colspan=\"3\" style=\"height: 10px;\"></td>\n            </tr>\n            \n            <tr>\n              <td valign=\"top\"><label for=\"smtp_Email\">");
/* 521 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.email.help.text"));
/* 522 */                   out.write(" <span class=\"mandatory\">*</span> </label> \n                <input id=\"smtpEmail\" name=\"smtpEmail\" type=\"text\" placeholder=\"appmanager-noprely@yourdomain.com\" class=\"inputClass\"/>\n              </td>\n            </tr>\n          </table>\n          <table id=\"secondarySmtp\" style=\"display:none;\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"content\" width=\"100%\">\n            <tr>\n              <td >\n                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" >\n                  <tr>\n                    <td valign=\"top\" >\n                      <label for=\"SMTP Server\">");
/* 523 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpservername"));
/* 524 */                   out.write("<span class=\"mandatory\">*</span></label>\n                      <input id=\"secSmtpServer\" name=\"secSmtpServer\" type=\"text\" placeholder=\"");
/* 525 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpservername"));
/* 526 */                   out.write("\" class=\"inputClass\"/>\n                    </td>\n                    <td width=\"2%\"/>\n                    <td valign=\"top\" >\n                      <label for=\"SMTP Port\">");
/* 527 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 528 */                   out.write("<span class=\"mandatory\">*</span> </label> \n                      <input id=\"secSmtpPort\" name=\"secSmtpPort\" type=\"text\" placeholder=\"");
/* 529 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 530 */                   out.write("\" class=\"inputClass\"/>\n                    </td>\n                  </tr>\n                  <tr>\n                    <td colspan=\"3\" style=\"height: 20px;\"></td>\n                  </tr>\n                  <tr>\n                    <td colspan=\"3\" >\n                      <table cellpadding=\"5\" cellspacing=\"0\" width=\"83%\" style=\"background-color: #fcfcfc; border:1px solid #f5f5f5;  padding:10px 10px 10px 10px; border-radius: 10px 10px 10px 10px;\">\n                        <tr>\n                          <td colspan=\"3\" class=\"bodytextbold\"><span style=\"font-size: 13px; margin: 10px 0px 0px 6px; position: relative; top: 5px;\">");
/* 531 */                   out.print(FormatUtil.getString("am.webclient.mailserver.smtpreqAuthentication"));
/* 532 */                   out.write("</span></td>\n                        </tr>\n                        <tr>\n                          <td valign=\"top\" width=\"49%\"><input id=\"secSmtpUserName\" name=\"secSmtpUserName\" placeholder=\"");
/* 533 */                   out.print(FormatUtil.getString("am.product.smtpserver.username.txt"));
/* 534 */                   out.write("\" autoComplete=\"off\" type=\"text\"\n                            class=\"inputClass\"/>\n                          </td>\n                          <td width=\"2%\"></td>\n                          <td valign=\"top\"><input id=\"secSmtpPassword\" placeholder=\"");
/* 535 */                   out.print(FormatUtil.getString("am.product.smtpserver.password.txt"));
/* 536 */                   out.write("\" name=\"secSmtpPassword\" autoComplete=\"off\" type=\"password\"\n                            class=\"inputClass\"/>\n                          </td>\n                        </tr>\n                        <tr>\n                          <td> \n                            <div style=\"display:block;padding-left:0px;\"><input type=\"checkbox\" style=\"position:relative; top:3px;\" name=\"secTlsAuth\" id=\"secTlsAuth\" class=\"wizard-chkbox\" value=\"true\"><span class=\"wizard-chkboxtd\">");
/* 537 */                   out.print(FormatUtil.getString("TLS Authentication Enabled"));
/* 538 */                   out.write("</span></div>\n\t\t\t\t\t\t\t<input type=\"hidden\" name=\"secTlsAuth\" value=\"false\"/>                          \n                          </td>\n                          <td width=\"2%\"/>\n                          <td> \n                            <div style=\"display:block;padding-left:0px;\"><input type=\"checkbox\" style=\"position:relative; top:3px;\" name=\"secSslAuth\" id=\"secSslAuth\" class=\"wizard-chkbox\" value=\"true\"><span class=\"wizard-chkboxtd\">");
/* 539 */                   out.print(FormatUtil.getString("SSL Authentication Enabled"));
/* 540 */                   out.write("</span></div>\n                          \t<input type=\"hidden\" name=\"secSslAuth\" value=\"false\"/>\n                          </td>\n                        </tr>\n                      </table>\n                    </td>\n                  </tr>\n                </table>\n              </td>\n              <td width=\"30%\" valign=\"top\">\n                <div id=\"testSecSmtpServer\" align=\"center\">\n                  <button class=\"TestSecSmtpServer buttons\" style=\"padding-top: 5px; padding-right: 10px; padding-bottom: 5px; padding-left: 10px; margin-top: 10px; display: inline-block;\" type=\"button\"> ");
/* 541 */                   out.print(FormatUtil.getString("am.product.smtpserver.test.txt"));
/* 542 */                   out.write(" </button>\n                  <div id=\"testSecSmtpServer_rsp\" style=\"display:block;\"></div>\n                </div>\n              </td>\n            </tr>\n            <tr>\n              <td colspan=\"3\" style=\"height: 10px;\"></td>\n            </tr>\n            <tr>\n              <td valign=\"top\"><label for=\"smtp_Email\">");
/* 543 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.email.help.text"));
/* 544 */                   out.write(" <span class=\"mandatory\">*</span> </label> \n              <input id=\"secSmtpEmail\" name=\"secSmtpEmail\" type=\"text\" placeholder=\"appmanager-noprely@yourdomain.com\" class=\"inputClass\"/>\n              </td>\n            </tr>\n          </table>\n        </fieldset>\n        <fieldset id=\"adminEmail\">\n          <SCRIPT type=\"text/javascript\">\n            var actionMethod = '/adminAction.do?method=uploadUserImage';  //NO I18N \n            var uploader;\n            window.onload = function(){\n                uploader = new qq.FileUploader({\n                element: document.getElementById('file-uploader'),\n                //element: $('#file-uploader'),\n                // path to server-side upload script\n                action: actionMethod,\n                // For debugging\n                debug: true,\n                multiple: false,\n                params:{},\n                allowedExtensions:['jpg', 'jpeg', 'png', 'gif'],  //NO I18N\n                showMessage: function(message){ alert(message); },\n                onSubmit: function(id, fileName){document.getElementById('userImg').setAttribute('src', '/images/image_uploader.gif');},\n");
/* 545 */                   out.write("                onProgress: function(id, fileName, loaded, total){},\n                onComplete: function(id, fileName, responseJSON){\n                    setTimeout(function(){\n                      document.getElementById('userImg').setAttribute('src', responseJSON.imgpath+'?'+new Date().getTime());\n                      if(responseJSON.imgpath.indexOf('vcard')>0){\n                        $('#qq-upload-button').html($('#qq-upload-button>input')).prepend('");
/* 546 */                   out.print(FormatUtil.getString("am.webclient.uploadphoto.txt"));
/* 547 */                   out.write("'); //NO I18N\n                      }else{\n                        $('#qq-upload-button').html($('#qq-upload-button>input')).prepend('");
/* 548 */                   out.print(FormatUtil.getString("am.webclient.changephoto.txt"));
/* 549 */                   out.write("'); //NO I18N\n                      }\n                      },1000);\n                    },\n                onCancel: function(id, fileName){alert('cancelled')}  //NO I18N\n              }); \n            } \n            \n          </SCRIPT>\n          <span class=\"wizard-img\"><img id=\"userImg\" src=\"/images/vcard.png\" width=\"100\" height=\"100\" style=\"box-shadow: 1px 1px 15px #999;\"></span><div id=\"file-uploader\"></div>\n          <legend class=\"wizard-admin\" style=\"float: left;\">");
/* 550 */                   out.print(FormatUtil.getString("am.webclient.adminprofile.txt"));
/* 551 */                   out.write("</legend>\n          <span class=\"wizard-desc\" style=\"left:115px;width:90%;\">");
/* 552 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisites.admin.description"));
/* 553 */                   out.write("</span>\n          <table cellpadding=\"0\" cellspacing=\"0\" style=\"padding-top:60px;float: left;width: 100%;\" >\n            <tr>\n              <td valign=\"top\">\n                <table cellpadding=\"0\" cellspacing=\"0\" width=\"35%\">\n                  <tr>\n                    <td valign=\"top\"><label for=\"userName\">Login ID<span class=\"mandatory\">*</span> </label> <input id=\"adminName\" type=\"text\" value=\"admin\" disabled=\"disabled\" class=\"inputClass\" />");
/* 554 */                   out.write("\n                    </td>\n                  </tr>\n                  <tr>\n                    <td valign=\"top\"><label for=\"adminEmail\">");
/* 555 */                   out.print(FormatUtil.getString("am.webclient.schedulereport.newschedule.reportdeliveryemailid.text"));
/* 556 */                   out.write("<span class=\"mandatory\">*</span> </label> \n                    <input id=\"admin_emailId\" name=\"adminEmailId\" placeholder=\"appmanager-admin@yourdomain.com\" type=\"text\" class=\"inputClass\" />\n                    </td>\n                  </tr>\n                   ");
/*     */                   
/* 558 */                   IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 559 */                   _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 560 */                   _jspx_th_c_005fif_005f1.setParent(_jspx_th_html_005fform_005f0);
/*     */                   
/* 562 */                   _jspx_th_c_005fif_005f1.setTest("${isOEM=='false'}");
/* 563 */                   int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 564 */                   if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */                     for (;;) {
/* 566 */                       out.write("\n                  <tr >\n                     <td valign=\"top\" style=\"white-space: nowrap;padding-top: 15px;\">\n                      <div id=\"registerForTechSupoprt\" style=\"display:none;padding:0px\">\n                        <input type=\"checkbox\" name=\"emailidfortechnicalsupport\" id=\"emailidfortechnicalsupport\" class=\"wizard-chkbox\" checked=\"checked\"><span class=\"wizard-chkboxtd\">");
/* 567 */                       out.print(FormatUtil.getString("am.register.freetechsupport.txt"));
/* 568 */                       out.write("</span>\n                       </div>\n                     </td>\n                  </tr>\n                  ");
/* 569 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 570 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 574 */                   if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 575 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*     */                   }
/*     */                   
/* 578 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 579 */                   out.write("\n                  <tr>\n                    <td style=\"height:137px\"></td>\n                  </tr>\n                </table>\n              </td>\n            </tr>\n          </table>\n        </fieldset>\n        <fieldset id=\"proxy\">\n          <span class=\"wizard-img\"><img src=\"/images/proxy.png\" width=\"48\" height=\"48\"> </span>\n          <legend style=\"float: left;\">");
/* 580 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.httpproxy.heading.txt"));
/* 581 */                   out.write("</legend>\n          <span class=\"wizard-desc\" style=\"padding-bottom:15px;\">");
/* 582 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisites.proxy.description"));
/* 583 */                   out.write("</span>\n          <div style=\"display: block\" class=\"content\">\n            <table cellpadding=0 cellspacing=0 border=0 width=\"90%\" style=\"dislay:block;font-size: 13px;color: #686868\">\n              <tr>\n                <td class=\"label\" width=\"150px\" style=\"font-weight:bold\">");
/* 584 */                   out.print(FormatUtil.getString("am.webclient.admin.proxy.configureproxyserver.text"));
/* 585 */                   out.write(":</td>\n                <td>\n                  <input type=\"radio\" name=\"useproxy\" id=\"useproxy-off\" value=\"off\" checked/>\n                    <span style=\"margin: 0 5px 0 -5px;\" class=\"label\">");
/* 586 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.settings"));
/* 587 */                   out.write("</span>&nbsp;\n                  <input type=\"radio\" name=\"useproxy\" id=\"useproxy-on\" value=\"on\"/>\n                    <span style=\"margin: 0 5px 0 -5px;\" class=\"label\">");
/* 588 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.proxyserver"));
/* 589 */                   out.write("</span>\n                </td>\n              </tr>\n            </table>\n            <table id=\"needProxy\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"position:relative;padding-top:5px\">\n              <tr>\n                <td valign=\"top\" width=\"70%\">\n                  <table id=\"proxyDetails\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"display:block\">\n                    <tr>\n                      <td valign=\"top\"><label for=\"proxy_Server\">");
/* 590 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.host"));
/* 591 */                   out.write("<span class=\"mandatory\">*</span></label> <input id=\"proxyHost\" name=\"proxyHost\" placeholder=\"");
/* 592 */                   out.print(FormatUtil.getString("am.product.proxy.hostname.txt"));
/* 593 */                   out.write("\" type=\"text\" class=\"inputClass\" /></td>\n                      <td width=\"2%\"></td>\n                      <td  valign=\"top\"><label for=\"proxy_Port\">");
/* 594 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.port"));
/* 595 */                   out.write("<span class=\"mandatory\">*</span></label> <input id=\"proxyPort\" name=\"proxyPort\" type=\"text\" placeholder=\"");
/* 596 */                   out.print(FormatUtil.getString("am.product.proxy.port.txt"));
/* 597 */                   out.write("\" class=\"inputClass\" /></td>\n                    </tr>\n                    <tr>\n                      <td valign=\"top\"><label for=\"proxy_User\">");
/* 598 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverusername"));
/* 599 */                   out.write("</label> <input id=\"proxyUserName\" name=\"proxyUserName\" placeholder=\"");
/* 600 */                   out.print(FormatUtil.getString("am.product.proxy.username.txt"));
/* 601 */                   out.write("\" type=\"text\" autocomplete=\"off\" class=\"inputClass\" /></td>\n                      <td width=\"2%\"></td>\n                      <td valign=\"top\"><label for=\"proxy_Password\">");
/* 602 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverpassword"));
/* 603 */                   out.write("</label> <input id=\"proxyPassword\" name=\"proxyPassword\" placeholder=\"");
/* 604 */                   out.print(FormatUtil.getString("am.product.proxy.password.txt"));
/* 605 */                   out.write("\" type=\"password\" autocomplete=\"off\" class=\"inputClass\" /></td>\n                    </tr>\n                    <tr>\n                      <td colspan=\"3\">\n                        <div style=\"display:block;padding-left:0px;\"><input type=\"checkbox\" name=\"bypassproxy\" id=\"bypassproxy\" class=\"wizard-chkbox\"><span class=\"wizard-chkboxtd\">");
/* 606 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.bypassserver"));
/* 607 */                   out.write("</span></div>\n                      </td>\n                    </tr>\n                  </table>\n                </td>\n                <td valign=\"top\" width=\"30%\" style=\"margin-top: 5px;\">\n                  <div id=\"testManualProxy\" style=\"display:block;float: right;top: 0px;padding: 5px;\" align=\"right\">\n                    <select id=\"operation_manual\" style=\"display:block;height: 28px;width:200px\">\n                      <option value=\"me\" selected>");
/* 608 */                   out.print(FormatUtil.getString("am.product.access.me.txt"));
/* 609 */                   out.write("</option>");
/* 610 */                   out.write("\n                      <option value=\"url\">");
/* 611 */                   out.print(FormatUtil.getString("am.product.access.testurl.txt"));
/* 612 */                   out.write("</option>");
/* 613 */                   out.write("\n                    </select>\n                    <input type=\"text\" id=\"testManualProxyString\" placeholder='");
/* 614 */                   out.print(FormatUtil.getString("am.product.testurl.txt"));
/* 615 */                   out.write("' style=\"width: 200px;margin: 10px 0px 0px 0px;display:none\">\n                    <button class=\"testManualProxy_btn buttons\" style=\"padding: 5px 15px; margin-top: 10px; display:block;\" type=\"button\" align=\"center\">");
/* 616 */                   out.print(FormatUtil.getString("am.product.proxy.settings.test.txt"));
/* 617 */                   out.write("</button>");
/* 618 */                   out.write("\n                    <div id=\"testManualProxy_rsp\" style=\"display:block;\"></div>\n                  </div>\n                </td>\n              </tr>\n              <tr>\n                <td colspan=\"2\">\n                  <label for=\"proxy_Password\">");
/* 619 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.noproxy"));
/* 620 */                   out.write("</label>\n                  <div id=\"dontproxy\">\n                    <table>\n                      <tr style=\"align:top\">\n                        <td><textarea name=\"dontProxyList\"  id=\"dontProxyList\" cols=\"45\" rows=\"6\">127.0.0.1</textarea></td>\n                        <td>\n                        <span class=\"bodytext\" style=\"padding-left: 25px;\">");
/* 621 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.note1"));
/* 622 */                   out.write("</span>\n                        <ul class=\"bodytext\" style=\"margin-top: 0px;\">\n                          <li>");
/* 623 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.note2"));
/* 624 */                   out.write("</li>\n                          <li>");
/* 625 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.note3"));
/* 626 */                   out.write("</li>\n                          <li>");
/* 627 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.note4"));
/* 628 */                   out.write("</li>\n                          <li>");
/* 629 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.note5"));
/* 630 */                   out.write("</li>\n                        </ul>\n                        </td>\n                      </tr>\n                    </table>\n                  </div>\n                </td>\n              </tr>\n            </table>\n            <table id=\"dontNeedProxy\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"98%\">\n              <tr>\n                <td width=\"60%\" valign=\"top\" class=\"bodytextbold\">\n                  <table width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                    <tr>\n                      <td style=\"padding: 20px;display:inline-block;background-color:#ebebeb;border:1px solid #999999;width:80%;\">\n                        ");
/* 631 */                   out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.urlproxyconfig.settings"));
/* 632 */                   out.write("\n                      </td>\n                    </tr>\n                     <tr>\n                      <td style=\"height:208px;\">&nbsp;</td>\n                    </tr>\n                  </table>\n                </td>\n                <td valign=\"top\" style=\"width:20%;padding-top:-5px;padding-right:-5px\" align=\"right\">\n                    <select id=\"operation_auto\" style=\"display:block;height: 28px;width:210px\">\n                      <option value=\"me\" selected=\"\">");
/* 633 */                   out.print(FormatUtil.getString("am.product.access.me.txt"));
/* 634 */                   out.write("</option>");
/* 635 */                   out.write("\n                      <option value=\"url\">");
/* 636 */                   out.print(FormatUtil.getString("am.product.access.testurl.txt"));
/* 637 */                   out.write("</option>");
/* 638 */                   out.write("\n                    </select>\n                    <input type=\"text\" id=\"testAutoProxyString\" placeholder='");
/* 639 */                   out.print(FormatUtil.getString("am.product.testurl.txt"));
/* 640 */                   out.write("' style=\"width: 210px;margin: 10px 0px 0px 0px;display:none\">\n                    <button class=\"testAutoProxy_btn buttons\" style=\"padding: 5px 15px; margin-top: 10px;\" type=\"button\">");
/* 641 */                   out.print(FormatUtil.getString("am.product.proxy.settings.test.txt"));
/* 642 */                   out.write("</button>");
/* 643 */                   out.write("\n                    <div id=\"testAutoProxy_rsp\" style=\"display:block;\"></div>\n                </td>\n              </tr>\n            </table>\n          </div>\n        </fieldset>\n      ");
/* 644 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 645 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 649 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 650 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*     */               }
/*     */               
/* 653 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 654 */               out.write("\n    </span>\n  </div>\n</div>\n<div id=\"dummyDiv\" style=\"display:none;\">\n</div>\n<script type=\"text/javascript\">\nvar pageData;\nvar online = false;\n\ndoOnlineCheck();\n// var redirectURL = getURLParameter(\"redirectTo\");  //NO I18N\n");
/* 655 */               if (request.getRemoteUser().equals("admin"))
/*     */               {
/* 657 */                 out.write("\n$(document).ready(function(){\n  $(\"form[name=AMActionForm]\").formToWizard();    //NO I18N\n  var facebox = $(\"#facebox\");    //NO I18N\n  facebox.overlay({      //NO I18N\n\ttop: ($(window).height()-$('#facebox').height()-30)/2,   //NO I18N\n    mask: { \n      color: '#000',\n      loadSpeed: 200,\n      opacity: 0.25,\n      height:'800px'    //NO I18N\n    },\n    closeOnClick: false,  \n    load: false,\n    onLoad:function(){MakeWizard();loadPageData();},\n    onClose:function(){selectStep(0); showPrimarySmtp();}  \n  });\n\n  ");
/* 658 */                 if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*     */                   return;
/* 660 */                 out.write("\n});\n");
/*     */               }
/* 662 */               out.write("\n\n$('#operation_manual').change(function(){  //NO I18N\n  $('#operation_manual').val()!==\"me\"?$('#testManualProxyString').show():$('#testManualProxyString').hide();   //NO I18N\n});\n\n$('#operation_auto').change(function(){   //NO I18N\n  $('#operation_auto').val()!==\"me\"?$('#testAutoProxyString').show():$('#testAutoProxyString').hide();   //NO I18N\n});\n\n$('.testManualProxy_btn').click(function(){   //NO I18N\n  if(validateStep('step2') && validateUrl('manual'))  //NO I18N\n  {\n    var ele = 'testManualProxy';   //NO I18N\n    $('#'+ele+'_rsp').html('<img src=\"/images/LoadingTC.gif\" style=\"margin: -4px;\"/>&nbsp;'+'");
/* 663 */               out.print(FormatUtil.getString("am.product.manualproxy.testing.txt"));
/* 664 */               out.write("');  //NO I18N\n    testProxyServer(ele); \n  }\n});\n\n$('.testAutoProxy_btn').click(function(){//NO I18N\n  if(validateUrl('auto')){  //NO I18N\n    var ele = 'testAutoProxy';   //NO I18N\n    $('#'+ele+'_rsp').html('<img src=\"/images/LoadingTC.gif\" style=\"margin: -4px;\"/>&nbsp;'+'");
/* 665 */               out.print(FormatUtil.getString("am.product.autoproxy.testing.txt"));
/* 666 */               out.write("');  //NO I18N\n    testProxyServer(ele);\n  }\n});\n\nfunction testProxyServer(ele){\n  $.fn.removeplaceholder();\n  var testurl =  $('#'+ele+'String').val().trim();//NO I18N\n  testurl = testurl.length==0?'auto':testurl;//NO I18N\n\n  var data = {'useproxy':$('input:radio[name=useproxy]:checked').val(),'proxyHost':$('#proxyHost').val(),'proxyPort':$('#proxyPort').val(),'proxyUserName':$('#proxyUserName').val(),'proxyPassword':$('#proxyPassword').val(),'bypassproxy':$('input:checkbox[name=bypassproxy]:checked').val(),'dontProxyList':$('#dontProxyList').val().trim(),'testurl':testurl};   //NO I18N\n  $.fn.restoreplaceholder();\n  \n  $.post('/adminAction.do?method=TestProxyServer',data,function(response) {  //NO I18N\n    $('#'+ele+'_rsp').html(response);  //NO I18N\n  });\n}\n\nfunction validateUrl(ele){\n  $.fn.removeplaceholder();\n  if(ele==\"auto\" && $('#operation_auto').val()!==\"me\"){  //NO I18N\n    if($('#testAutoProxyString').val()==''){  //NO I18N\n      alert('");
/* 667 */               out.print(FormatUtil.getString("am.proxy.testurl.valid.txt"));
/* 668 */               out.write("');  //NO I18N\n      $.fn.restoreplaceholder();\n      return false;\n    }\n  }else if(ele==\"manual\" && $('#operation_manual').val()!==\"me\"){  //NO I18N\n    if($('#testManualProxyString').val()==''){  //NO I18N\n      alert('");
/* 669 */               out.print(FormatUtil.getString("am.proxy.testurl.valid.txt"));
/* 670 */               out.write("');  //NO I18N\n      $.fn.restoreplaceholder();\n      return false;\n    }\n  }\n  $.fn.restoreplaceholder();\n  return true;\n}\n\nfunction selectStep(a){\n  for(i=0;i<3;i++){\n    if(i==a){\n      $('#step'+a).show();  //NO I18N\n    }\n    else{\n      $('#step'+i).hide();  //NO I18N\n    }\n  }\n}\n\n//Note: before submitting we need to remove all the placeholders \n(function($) {\n  $.fn.placeholder = function() {\n    if(typeof document.createElement(\"input\").placeholder == 'undefined') {  \n      $('[placeholder]').focus(function() {  //NO I18N\n        var input = $(this);\n        if (input.val().trim() == input.attr('placeholder').trim()) {//NO I18N\n          input.val('');\n        }\n        input.removeClass('placeholder');  //NO I18N\n      }).blur(function() {\n        var input = $(this);\n        if (input.val().trim() == '' || input.val().trim() == input.attr('placeholder').trim()) {//NO I18N\n          input.addClass('placeholder');   //NO I18N\n          input.val(\" \"+input.attr('placeholder'));//NO I18N\n        }\n      }).blur().parents('form').submit(function() {  //NO I18N\n");
/* 671 */               out.write("        $(this).find('[placeholder]').each(function() {\n          var input = $(this);\n          if (input.val().trim() == input.attr('placeholder').trim()) {//NO I18N\n            input.val('');\n          }\n        });\n      });\n    }\n  }\n})(jQuery);\n\n//Note: below method has to be called before the ajax submit to remove placeholders.\n(function($){\n  $.fn.removeplaceholder = function() {\n    if(typeof document.createElement(\"input\").placeholder=='undefined'){\n      $('[placeholder]').each(function(){//NO I18N\n            var input = $(this);\n            if (input.val().trim() == input.attr('placeholder').trim()) {//NO I18N\n              input.val('');\n            }\n       });\n    }\n  }\n})(jQuery);\n\n//Note: below method has to be called to restore the placeholders once remove placeholder is called.\n(function($){\n  $.fn.restoreplaceholder = function() {\n    if(typeof document.createElement(\"input\").placeholder=='undefined'){\n      $('[placeholder]').each(function(){//NO I18N\n        var input = $(this);\n        if (input.val().trim() == '' || input.val().trim() == input.attr('placeholder').trim()) {//NO I18N\n");
/* 672 */               out.write("          input.addClass('placeholder');   //NO I18N\n          input.val(\" \"+input.attr('placeholder'));//NO I18N\n        }\n      });\n    }\n  }\n})(jQuery);\n\n\nfunction showPrimarySmtp()\n{\n  document.getElementById(\"secondarySmtp\").style.display=\"none\";\n  document.getElementById(\"primarySmtp\").style.display=\"block\";\n  $.fn.removeplaceholder(); \n  if($('#smtpEmail').val()=='' || !isEmailId($('#smtpEmail').val())){//NO I18N\n    $('#step0Skip').show();//NO I18N\n  }else{\n    $('#step0Skip').hide();//NO I18N\n  }\n  $.fn.restoreplaceholder(); \n}\n\nfunction showSecondarySmtp()\n{\n  document.getElementById(\"primarySmtp\").style.display=\"none\";\n  document.getElementById(\"secondarySmtp\").style.display=\"block\";\n  $.fn.removeplaceholder(); \n  if($('#secSmtpEmail').val()=='' || !isEmailId($('#secSmtpEmail').val())){\n    $('#step0Skip').show();//NO I18N\n  }else{\n    $('#step0Skip').hide();//NO I18N\n  }\n  $.fn.restoreplaceholder(); \n}\n\n\nfunction loadPageData(){\n  $.getJSON('/adminAction.do?method=showPrerequisitesConfiguration',{'isAjax':'true'},function(response) {   //NO I18N\n");
/* 673 */               out.write("    pageData = response;\n    loadSmtpData(pageData.smtpDetails);\n    loadProxyData(pageData.proxyDetails);\n    loadAdminData(pageData.adminDetails);\n    setTimeout(function(){$.fn.placeholder();},10);\n    if(!pageData.adminDetails.adminStatus){\n      selectStep(1);\n    }\n    else{\n      selectStep(0);\n    }\n  });\n}\n\nfunction MakeWizard(){\n    \n    $(\"#makeWizard\").hide();   //NO I18N\n    $(\"#info\").fadeIn(400);  //NO I18N\n    $('#qq-upload-button').css({\"padding\":\"5px\"});  //NO I18N\n}\n\n  $('input:radio[name=useproxy]').change(function() {  //NO I18N\n    $('#dontNeedProxy').toggle();  //NO I18N\n    $('#needProxy').toggle();  //NO I18N\n  });\n\nfunction loadSmtpData(data){\n  if(data && data.globalEmail=='' || !isEmailId(data.globalEmail)){//NO I18N\n    $('#step0Skip').show();//NO I18N\n  }\n  $('#smtpServer').val(data.smtpServer);   //NO I18N\n  $('#smtpPort').val(data.smtpPort);   //NO I18N\n  $('#smtpUserName').val(data.smtpUserName);   //NO I18N\n  $('#smtpPassword').val(data.smtpPassword);   //NO I18N\n  $('#smtpEmail').val(data.smtpEmail);   //NO I18N\n");
/* 674 */               out.write("  $('#prmTlsAuth').attr('checked',data.prmTlsAuth == 'true'?true:false);  //NO I18N\n  $('#prmSslAuth').attr('checked',data.prmSslAuth == 'true'?true:false);  //NO I18N\n  \n  $('#secSmtpServer').val(data.secSmtpServer);   //NO I18N\n  $('#secSmtpPort').val(data.secSmtpPort);     //NO I18N\n  $('#secSmtpUserName').val(data.secSmtpUserName);   //NO I18N\n  $('#secSmtpPassword').val(data.secSmtpPassword);   //NO I18N\n  $('#secSmtpEmail').val(data.secSmtpEmail);   //NO I18N\n  $('#secTlsAuth').attr('checked',data.secTlsAuth == 'true'?true:false);  //NO I18N\n  $('#secSslAuth').attr('checked',data.secSslAuth == 'true'?true:false);  //NO I18N\n}\n\nfunction loadProxyData(data){\n  if(data.proxyEnabled==\"true\"){//NO I18N\n    $('#proxyHost').val(data.proxyHost);   //NO I18N\n    $('#proxyPort').val(data.proxyPort);   //NO I18N\n    $('#proxyUserName').val(data.proxyUsername);   //NO I18N\n    $('#proxyPassword').val(data.proxyPassword);   //NO I18N\n    $('#useproxy-off').attr('checked',false);  //NO I18N\n    $('#useproxy-on').attr('checked',true);  //NO I18N\n");
/* 675 */               out.write("    $('#bypassproxy').attr('checked',(data.bypassProxy=='true')?true:false);//NO I18N\n    $('#dontProxyList').val(data.proxyBypass);   //NO I18N\n    $('#dontNeedProxy').hide();  //NO I18N\n    $('#needProxy').show();  //NO I18N\n  }else{\n    $('#useproxy-on').attr('checked',false);//NO I18N\n    $('#useproxy-off').attr('checked',true); //NO I18N\n    $('#dontNeedProxy').show();  //NO I18N\n    $('#needProxy').hide();  //NO I18N\n  }\n}\n\nfunction loadAdminData(data){\n  $('#admin_emailId').val(data.adminEmail);  //NO I18N\n  $('#userImg').attr('src',data.userImagePath);  //NO I18N\n  if(data.userImagePath && data.userImagePath.indexOf('vcard')>0){   //NO I18N\n    $('#qq-upload-button').html($('#qq-upload-button>input')).prepend('");
/* 676 */               out.print(FormatUtil.getString("am.webclient.uploadphoto.txt"));
/* 677 */               out.write("');  //NO I18N\n  }else{\n    $('#qq-upload-button').html($('#qq-upload-button>input')).prepend('");
/* 678 */               out.print(FormatUtil.getString("am.webclient.changephoto.txt"));
/* 679 */               out.write("')   //NO I18N\n  }\n  if(data.showRegisterTechSupport==\"true\"){//NO I18N\n    $('#registerForTechSupoprt').show(); //NO I18N\n  }\n}\n\n$('.TestPrmSmtpServer').click(function(){   //NO I18N\n  if(validateStep('step0-pri'))  //NO I18N\n  {\n    var ele = 'testPrmSmtpServer';   //NO I18N\n    $('#'+ele+'_rsp').html('<img src=\"/images/LoadingTC.gif\" style=\"margin: -4px;\"/>&nbsp;'+'");
/* 680 */               out.print(FormatUtil.getString("am.smtp.sendingemail.txt"));
/* 681 */               out.write("');  //NO I18N\n    $.fn.removeplaceholder();\n    var data = {'smtpHost':$('#smtpServer').val(),'smtpPort':$('#smtpPort').val(),'smtpUserName':$('#smtpUserName').val(),'smtpPassword':$('#smtpPassword').val(),'emailAddress':$('#smtpEmail').val(),'smtpTlsAuth':$('#prmTlsAuth').attr('checked')==='checked'?'true':'false','smtpSslAuth':$('#prmSslAuth').attr('checked')==='checked'?'true':'false'};  //NO I18N\n    $.fn.restoreplaceholder();\n    testSmtpServer(ele,data); \n  }\n});\n\n$('.TestSecSmtpServer').click(function(){   //NO I18N\n  if(validateStep('step0-sec'))  //NO I18N\n  {\n    var ele = 'testSecSmtpServer';   //NO I18N\n    $('#'+ele+'_rsp').html('<img src=\"/images/LoadingTC.gif\" style=\"margin: -4px;\"/>&nbsp;'+'");
/* 682 */               out.print(FormatUtil.getString("am.smtp.sendingemail.txt"));
/* 683 */               out.write("');  //NO I18N\n    $.fn.removeplaceholder();\n    var data = {'smtpHost':$('#secSmtpServer').val(),'smtpPort':$('#secSmtpPort').val(),'smtpUserName':$('#secSmtpUserName').val(),'smtpPassword':$('#secSmtpPassword').val(),'emailAddress':$('#secSmtpEmail').val(),'smtpTlsAuth':$('#secTlsAuth').attr('checked')==='checked'?'true':'false', 'smtpSslAuth':$('#secSslAuth').attr('checked')==='checked'?'true':'false'};  //NO I18N\n    $.fn.restoreplaceholder();\n    testSmtpServer(ele,data); \n  }\n});\n\nfunction testSmtpServer(ele, data){\n  $.post('/adminAction.do?method=TestSmtpServer',data,function(response) {   //NO I18N\n    $('#'+ele+'_rsp').html(response);  //NO I18N\n  });\n}\nvar nextDisplayValue = '");
/* 684 */               out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.next"));
/* 685 */               out.write("';   //NO I18N\n(function($) {\n    $.fn.formToWizard = function(options) {\n        options = $.extend({  \n            submitButton: ''  \n        }, options); \n        var element = this;\n        var steps = $(element).find(\"fieldset\");\n        var count = steps.size();\n\n        if($('div#step0').length==0){\n          steps.each(function(i) {\n            $(this).wrap(\"<div id='step\" + i + \"'></div>\");\n            $(this).append(\"<p id='step\" + i + \"commands' class='commands'></p>\");\n\n            // 2\n            var name = $(this).find(\"legend\").html();\n            $(\"#steps\").append(\"<li id='stepDesc\" + i + \"'>\"+'");
/* 686 */               out.print(FormatUtil.getString("am.webclient.gettingstarted.step"));
/* 687 */               out.write("'+\" \"+ (i + 1) + \"<span>\" + name + \"</span></li>\");   //NO I18N\n            if (i == 0) {\n                createNextButton(i);\n                createSkipButton(i);\n            }\n            else if (i == count - 1) {\n              $(\"#step\" + i).hide();   //NO I18N\n                createPrevButton(i);  \n                $(\"#step\" + i + \"commands\").append(\"<a href='#' id='Finish' class='finish'>\"+'");
/* 688 */               out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.finish"));
/* 689 */               out.write("'+\"</a>\");   //NO I18N\n                \n            }\n            else {\n              $(\"#step\" + i).hide();   //NO I18N\n                createPrevButton(i);\n                createNextButton(i);\n            }\n        });\n      }\n        \n        //Hack for IE \n        //if(navigator.userAgent.indexOf(\"MSIE\")>=0){$(\"#steps li span\").css({\"line-height\":\"30px\"})}\n\n        function createPrevButton(i) {\n            var stepName = \"step\" + i;   //NO I18N\n            $(\"#\" + stepName + \"commands\").append(\"<a href='#' id='\" + stepName + \"Prev' class='prev'>\"+'");
/* 690 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.back"));
/* 691 */               out.write("'+\"</a>\");   //NO I18N\n            \n            $(\"#\" + stepName + \"Prev\").bind(\"click\", function(e) {   //NO I18N\n                $(\"#\" + stepName).hide();\n                $(\"#step\" + (i - 1)).show();   //NO I18N\n            });\n        }\n        \n        function createNextButton(i) {\n            var stepName = \"step\" + i;   //NO I18N\n            $(\"#\" + stepName + \"commands\").append(\"<a href='#' id='\" + stepName + \"Next' class='next'>\"+nextDisplayValue+\"</a>\");  //NO I18N\n\n            $(\"#\" + stepName + \"Next\").bind(\"click\", function(e) {   //NO I18N\n              if(validateStep(stepName))\n              {\n                $(\"#\" + stepName).hide();\n                $(\"#step\" + (i + 1)).show();   //NO I18N\n              }\n            });\n        }\n        \n        function createSkipButton(i) {\n            var stepName = \"step\" + i;   //NO I18N\n            $(\"#\" + stepName + \"commands\").append(\"<a href='#' id='\" + stepName + \"Skip' class='skip'>\"+'");
/* 692 */               out.print(FormatUtil.getString("am.webclient.gettingstarted.remindmelater.txt"));
/* 693 */               out.write("'+\"</a>\");  //NO I18N\n            $(\"#\" + stepName + \"Skip\").hide();//NO I18N\n\n            $(\"#\" + stepName + \"Skip\").bind(\"click\", function(e) {   //NO I18N\n                $(\"#\" + stepName).hide();\n                $(\"#step\" + (i + 1)).show();   //NO I18N\n                ");
/*     */               
/* 695 */               request.getSession().setAttribute("remindmeLater", "true");
/*     */               
/* 697 */               out.write("\n            });\n        }\n\n    $(\"#steps li\").click( function(){   //NO I18N\n      $(\"#steps li\").removeClass(\"current\");   //NO I18N\n      $(this).addClass(\"current\");   //NO I18N\n      var tabClicked = $(this).attr('id');   //NO I18N\n\n      $(\"form[name=AMActionForm] > div\").each(function(){  //NO I18N\n        ($(this).attr(\"id\")==tabClicked.replace(\"Desc\", \"\")) ? $(this).show() : $(this).hide();  //NO I18N\n      });\n      });\n  }\n})(jQuery);\n\n$(function() {\n\n  if($('#secondarySmtp:first')[0].style.display==\"none\"){\n    $('#smtpEmail').keyup(function() { //NO I18N\n        $.fn.removeplaceholder(); \n        if($('#smtpEmail').val()=='' || !isEmailId($('#smtpEmail').val())){//NO I18N\n          $('#step0Skip').show();//NO I18N\n        }else{\n          $('#step0Skip').hide();//NO I18N\n        }\n        $.fn.restoreplaceholder(); \n        $('#smtpEmail').focus();//NO I18N\n    });\n  }\n  else{\n    $('#secSmtpEmail').keyup(function() { //NO I18N\n        $.fn.removeplaceholder(); \n        if($('#secSmtpEmail').val()=='' || !isEmailId($('#secSmtpEmail').val())){//NO I18N\n");
/* 698 */               out.write("          $('#step0Skip').show();//NO I18N\n        }else{\n          $('#step0Skip').hide();//NO I18N\n        }\n        $.fn.restoreplaceholder(); \n        $('#secSmtpEmail').focus();//NO I18N\n    });\n  }\n});\n\nfunction validateStep(stepName){\n  var result = true;\n  $.fn.removeplaceholder(); \n  var formData = document.forms[1]; \n  if(stepName=='step0-sec')  //NO I18N\n  {\n    if(formData.secSmtpServer.value=='')\n    {\n      alert('");
/* 699 */               out.print(FormatUtil.getString("am.webclient.mailsettings.alertname.text"));
/* 700 */               out.write("');\n      formData.secSmtpServer.focus();\n      result = false;\n    }\n    else if(formData.secSmtpPort.value=='')\n    {\n      alert('");
/* 701 */               out.print(FormatUtil.getString("am.webclient.mailsettings.alertport.text"));
/* 702 */               out.write("');\n      formData.secSmtpPort.focus();\n      result = false;\n    }\n    else if(formData.secSmtpEmail.value=='' || !isEmailId(formData.secSmtpEmail.value))//NO I18N\n    {\n      alert('");
/* 703 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.email"));
/* 704 */               out.write("');\n      formData.secSmtpEmail.focus();\n      result = false;\n    }\n  }\n  if(stepName=='step0' || stepName=='step0-pri')  //validating both primary and secondary smtpserver\n  {\n    if(formData.smtpServer.value=='')\n    {\n      alert('");
/* 705 */               out.print(FormatUtil.getString("am.webclient.mailsettings.alertname.text"));
/* 706 */               out.write("');\n      formData.smtpServer.focus();\n      result = false;\n    }\n    else if(formData.smtpPort.value=='')\n    {\n      alert('");
/* 707 */               out.print(FormatUtil.getString("am.webclient.mailsettings.alertport.text"));
/* 708 */               out.write("');\n      formData.smtpPort.focus();\n      result = false;\n    }\n    else if(formData.smtpEmail.value=='' || !isEmailId(formData.smtpEmail.value))//NO I18N\n    {\n      alert('");
/* 709 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.email"));
/* 710 */               out.write("');\n      formData.smtpEmail.focus();\n      result = false;\n    }\n  }\n  else if(stepName=='step1')\n  {\n    if(formData.adminEmailId.value=='' || !isEmailId(formData.adminEmailId.value))\n    {\n      alert('");
/* 711 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.email"));
/* 712 */               out.write("');\n      formData.adminEmailId.focus();\n      result = false;\n    }\n  }\n  else if(stepName=='step2')\n  {\n    if($('input:radio[name=useproxy]:checked').val()=='on')\n    {\n      if(formData.proxyHost.value=='')\n      {\n        alert('");
/* 713 */               out.print(FormatUtil.getString("am.webclient.hostnameemp.text"));
/* 714 */               out.write("');\n        formData.proxyHost.focus();\n        result = false;\n      }\n      else if(formData.proxyPort.value=='')\n      {\n        alert('");
/* 715 */               out.print(FormatUtil.getString("am.webclient.portemp.text"));
/* 716 */               out.write("');\n        formData.proxyPort.focus();\n        result = false;\n      }\n    }\n  }\n  $.fn.restoreplaceholder();\n  return result;\n}\n\n$('#setup').click(function(){   //NO I18N\n  $(\"#facebox\").overlay().load();//NO I18N\n});\n$('#close-btn').click(function(){//NO I18N\n  $('.close').trigger(\"click\");//NO I18N\n});\n$('#Finish').live('click', function(){   //NO I18N\n   if(validateStep('step2'))   //NO I18N\n   {\n    saveFormData(true);\n   }\n   return false;\n});\n\nif($.browser.msie){\n  $('#proxyDetails').css({'width':'80%'});     //NO I18N\n}\n\nfunction saveFormData(saveData, redirectToUrl){\n  if(saveData)\n  {\n    $.fn.removeplaceholder();\n    var data = $('form[name=AMActionForm]').serialize();   //NO I18N\n    //console.log(data);\n    $.fn.restoreplaceholder();\n    $.post('/adminAction.do?method=preRequisitesConfiguration',data,function(response) {   //NO I18N\n      $('.close').trigger(\"click\");  //NO I18N\n    });\n  }\n  if(redirectToUrl && (redirectToUrl!==\"null\")){\n    location.href=redirectToUrl;\n  }\n  return false;\n}\n\n\nfunction fnCloseGettingStarted()\n");
/* 717 */               out.write("{\n  ");
/* 718 */               if (_jspx_meth_logic_005fpresent_005f4(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*     */                 return;
/* 720 */               out.write("  \n  ");
/*     */               
/* 722 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 723 */               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 724 */               _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*     */               
/* 726 */               _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 727 */               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 728 */               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */                 for (;;) {
/* 730 */                   out.write(" \n    alert(\"");
/* 731 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.donotshowagain.message.text"));
/* 732 */                   out.write("\");\n    var c_value=escape(\"");
/* 733 */                   out.print(setTabinCookie);
/* 734 */                   out.write("\") ;\n    document.cookie=\"selectedtab=\" + c_value+\";path=/\";\n    location.href=\"/jsp/UpdateGlobalSettings.jsp?key=showgettingstarted&value=false\"; \n  ");
/* 735 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 736 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 740 */               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 741 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*     */               }
/*     */               
/* 744 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 745 */               out.write("\n}\n\n\nvar chkInterval=\"\"; //NO I18N\nfunction checkOnline()\n{\n  console.log('checking For online content with interval:20000'); //NO I18N\n  chkInterval = setInterval(\"doOnlineCheck()\", 20000); //NO I18N\n}\n\nfunction doOnlineCheck()\n{\n  ");
/* 746 */               if (showOnlineContent) {
/* 747 */                 out.write("\n  if($('#onlineImg').length){ //NO I18N\n    $(\"body\").remove('#onlineImg'); //NO I18N\n  }\n\n  if(navigator.onLine){\n    var img = new Image();\n    img.id = \"onlineImg\"; //NO I18N\n    img.src = \"https://www.manageengine.com/images/copyright.gif");
/* 748 */                 out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 749 */                 out.write("\";  //NO I18N\n    img.onload = function(){online = true; clearInterval(chkInterval); $('#offlineContent').hide(); populateData(); $('#onlineContent').show()}; //NO I18N\n    img.onerror = function(){online = false; $('#onlineContent').hide(); $('#offlineContent').show(); checkOnline()}; //NO I18N\n    $(\"#dummyDiv\").html(img);    //NO I18N \n  }\n  else{\n    $('#onlineImg').hide();//NO I18N\n  }\n  ");
/*     */               }
/* 751 */               out.write("\n}\n\nfunction populateData()\n{\n  try\n  {\n    if(apm_collaterals){\n      var a = '<div id=\"left_col_demo\"><div id=\"left_col_inner_demo\"><h3>'+apm_collaterals.name+'</h3></div></div>';//NO I18N\n      if(apm_collaterals.list){\n        $.each(apm_collaterals.list,function(key){\n        var item = apm_collaterals.list[key];\n          a += '<div id=\"left_col_demo\" onclick=\"javascript:window.open(\\''+item.link+'\\')\"><div id=\"left_col_inner_demo\"> <div id=\"left_col_img\"><img src=\"'+item.image+'\" border=\"0\"/></div><p><a>'+item.heading+'</a></p><p>'+item.description+'</p></div></div>';//NO I18N\n        });\n      }\n      $('#onlineContent').html(a);//NO I18N\n    }\n    $('script[src$=\"test.js\"]').remove();//NO I18N\n  }catch(Err){\n    $('#onlineContent').html('');$('#onlineContent').hide(); $('#offlineContent').show();//NO I18N\n  }\n}\n</script>\n");
/* 752 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 753 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 756 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 757 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 760 */           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 761 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*     */           }
/*     */           
/* 764 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 765 */           out.write(10);
/* 766 */           if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 768 */           out.write(32);
/* 769 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 770 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 774 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 775 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*     */       }
/*     */       else {
/* 778 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 779 */         out.write(32);
/* 780 */         out.write(10);
/*     */       }
/* 782 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 783 */         out = _jspx_out;
/* 784 */         if ((out != null) && (out.getBufferSize() != 0))
/* 785 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 786 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 789 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 795 */     PageContext pageContext = _jspx_page_context;
/* 796 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 798 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 799 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 800 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 802 */     _jspx_th_logic_005fpresent_005f0.setRole("MANAGER");
/* 803 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 804 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 806 */         out.write(" \n  location.href=\"/applications.do\"; \n");
/* 807 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 808 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 812 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 813 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 814 */       return true;
/*     */     }
/* 816 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 817 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 822 */     PageContext pageContext = _jspx_page_context;
/* 823 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 825 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 826 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 827 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 829 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*     */     
/* 831 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 832 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 833 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 834 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 835 */       return true;
/*     */     }
/* 837 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 838 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 843 */     PageContext pageContext = _jspx_page_context;
/* 844 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 846 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 847 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 848 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*     */     
/* 850 */     _jspx_th_c_005fif_005f2.setTest("${user_type != \"R\" && showSettingsWizard == \"true\"}");
/* 851 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 852 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 854 */         out.write("\n    facebox.overlay().load();\n  ");
/* 855 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 856 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 860 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 861 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 862 */       return true;
/*     */     }
/* 864 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 865 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f4(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 870 */     PageContext pageContext = _jspx_page_context;
/* 871 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 873 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 874 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 875 */     _jspx_th_logic_005fpresent_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*     */     
/* 877 */     _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 878 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 879 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*     */       for (;;) {
/* 881 */         out.write(" \n    alertUser();\n    return;\n  ");
/* 882 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 883 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 887 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 888 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 889 */       return true;
/*     */     }
/* 891 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 892 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 897 */     PageContext pageContext = _jspx_page_context;
/* 898 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 900 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 901 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 902 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 904 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*     */     
/* 906 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 907 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 908 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 909 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 910 */       return true;
/*     */     }
/* 912 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 913 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\GettingStarted_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */