/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class MonitorGroupTabs_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  24 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  37 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  41 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  45 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  49 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  50 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  51 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  58 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  61 */     JspWriter out = null;
/*  62 */     Object page = this;
/*  63 */     JspWriter _jspx_out = null;
/*  64 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  68 */       response.setContentType("text/html");
/*  69 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  71 */       _jspx_page_context = pageContext;
/*  72 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  73 */       ServletConfig config = pageContext.getServletConfig();
/*  74 */       session = pageContext.getSession();
/*  75 */       out = pageContext.getOut();
/*  76 */       _jspx_out = out;
/*     */       
/*  78 */       out.write("<!-- $Id$-->\n\n\n\n\n\n\n\n\n\n\n\n<script type=\"text/javascript\">\n\nfunction DashboardActions(holder, source) {\n\tvar holderObj = document.getElementById(holder);//No I18N\n\tvar posX = findPosX(holderObj)-148;//No I18N\n\tvar posY = findPosY(holderObj)+1;//No I18N\n\tvar finalY = posY + holderObj.offsetHeight - document.body.scrollTop;//No I18N\n\tshowDialog(document.getElementById(source).innerHTML, 'position=absolute,closeButton=no,closeOnBodyClick=yes,left=' + posX + ',top=' + finalY);//No I18N\n}\nfunction showMGActions(holder, source) {\n\tvar holderObj = document.getElementById(holder);//No I18N\n\tvar posX = findPosX(holderObj)-225;//No I18N\n\tvar posY = findPosY(holderObj)+1;//No I18N\n\tvar finalY = posY + holderObj.offsetHeight - document.body.scrollTop;//No I18N\n\tshowDialog(document.getElementById(source).innerHTML, 'position=absolute,closeButton=no,closeOnBodyClick=yes,left=' + posX + ',top=' + finalY);//No I18N\n}\n\nfunction addWidgets()\n{\nfnOpenNewWindowWithHeightWidthPlacement('MyPage.do?method=newWidgets&pageid=");
/*  79 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  81 */       out.write("','900','610',15,15);\t//No I18N\n}\n\nfunction setDisplayName(widgetid,name)\n{\ndocument.getElementById(\"widgetname#\"+widgetid).innerHTML=name;\n//alert(window.opener.document.getElementById(\"widgetname#");
/*  82 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */         return;
/*  84 */       out.write("\").innerHTML);\n\n}\n\nfunction popOut(template_resid)\n{\n\tvar popoutwindow;\n\tif(template_resid!=null)\n\t{\n\tpopoutwindow=window.open('/MyPage.do?method=popOut&template_resid='+template_resid+'&pageid=");
/*  85 */       if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*     */         return;
/*  87 */       out.write("','FlashView','scrollbars=1,resizable=1');\t//No I18N\n\t}\n\telse\n\t{\n\tpopoutwindow=window.open('/MyPage.do?method=popOut&pageid=");
/*  88 */       if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*     */         return;
/*  90 */       out.write("','FlashView','scrollbars=1,resizable=1');\t//No I18N\n\t}\n}\n\nfunction deleteDashboard(template_resid)\n{\nif(confirm('");
/*  91 */       out.print(FormatUtil.getString("am.mypage.delete.confirm.text"));
/*  92 */       out.write("'))\n{\n\tif(template_resid!=null)\n\t{\n\t\tdocument.location.href=\"/MyPage.do?template_resid=\"+template_resid+\"&method=deleteMyPage&pageid=");
/*  93 */       if (_jspx_meth_c_005fout_005f4(_jspx_page_context))
/*     */         return;
/*  95 */       out.write("\";//No I18N\n\t}\n\telse//No I18N\n\t{\n\t\tdocument.location.href=\"/MyPage.do?method=deleteMyPage&pageid=");
/*  96 */       if (_jspx_meth_c_005fout_005f5(_jspx_page_context))
/*     */         return;
/*  98 */       out.write("\";//No I18N\n\t}\n}\nelse\n{\nreturn;\n}\n}\n\n</script>\n");
/*  99 */       boolean isDashboardTabSelected = false;
/* 100 */       String resourceid = request.getParameter("resourceid");
/* 101 */       String gpType = com.adventnet.appmanager.struts.beans.GroupComponent.getGroupType(resourceid);
/* 102 */       String resourcename = request.getParameter("resourcename");
/* 103 */       if (com.adventnet.appmanager.util.Constants.isIt360)
/*     */       {
/* 105 */         out.write("\n<table width=\"98%\" height=\"2%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t<tr><td class=\"bcinactive it360-summary-heading\" valign=\"top\" height=\"30\">  ");
/* 106 */         out.println(com.adventnet.appmanager.util.DBUtil.getDisplaynameforResourceID(resourceid));
/* 107 */         out.write("  </td> </tr>\n</table>\n");
/*     */       }
/* 109 */       out.write("\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n  <tbody>\n    <tr class=\"tabBtmLine\">\n      <td nowrap=\"nowrap\">\n      \t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" id=\"InnerTab\">\n          <tbody>\n            <tr>\n              <td width=\"17\"/>&nbsp;&nbsp;&nbsp;&nbsp;\n");
/*     */       
/*     */ 
/* 112 */       String titles = request.getParameter("titles");
/* 113 */       String tooltips = request.getParameter("tooltip");
/* 114 */       String functions = request.getParameter("functions");
/* 115 */       String selectedtab = request.getParameter("selected");
/* 116 */       String errorcode = request.getParameter("errorcode");
/* 117 */       StringTokenizer tok = new StringTokenizer(titles, ",");
/* 118 */       StringTokenizer tok1 = new StringTokenizer(functions, ",");
/* 119 */       StringTokenizer tok2 = new StringTokenizer(tooltips, ",");
/* 120 */       while (tok.hasMoreTokens())
/*     */       {
/* 122 */         String title = tok.nextToken();
/* 123 */         String functionName = tok1.nextToken();
/* 124 */         String tooltip = tok2.nextToken();
/*     */         
/* 126 */         if (functionName.equals("getMyPages"))
/*     */         {
/*     */ 
/* 129 */           out.write("\n\n              <td><table title=\"");
/* 130 */           out.print(FormatUtil.getString(tooltip));
/* 131 */           out.write("\" id=\"");
/* 132 */           out.print(title);
/* 133 */           out.write("Tab\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\" >\n                  <tbody>\n                    <tr>\n                      <td ");
/* 134 */           if (!selectedtab.equals(title)) {
/* 135 */             out.write("class=\"tbUnselected_Left\"");
/*     */           }
/* 137 */           if (selectedtab.equals(title)) {
/* 138 */             out.write("class=\"tbSelected_Left\"");
/*     */           }
/* 140 */           out.write(" ><img width=\"1\" height=\"1\" alt=\"spacer\" src=\"/images/spacer.gif\"/></td>\n                      <td ");
/* 141 */           if (!selectedtab.equals(title)) {
/* 142 */             out.write("class=\"tbUnselected_Middle\"");
/*     */           }
/* 144 */           out.write(32);
/* 145 */           if (selectedtab.equals(title)) {
/* 146 */             out.write("class=\"tbSelected_Middle\"");
/*     */           }
/* 148 */           out.write(" style=\"padding-left:5px;padding-right:5px\">\n                      <a id=\"mgDashboardDetailsHook\" href=\"javascript:void(0);\" onClick=\"SetTabStyle('");
/* 149 */           out.print(title);
/* 150 */           out.write("','InnerTab');");
/* 151 */           out.print(functionName);
/* 152 */           out.write(40);
/* 153 */           out.write(39);
/* 154 */           out.print(resourceid);
/* 155 */           out.write(39);
/* 156 */           out.write(44);
/* 157 */           out.write(39);
/* 158 */           out.print(resourcename);
/* 159 */           out.write(39);
/* 160 */           out.write(44);
/* 161 */           out.write(39);
/* 162 */           out.print(errorcode);
/* 163 */           out.write("');\" ><span ");
/* 164 */           if (!selectedtab.equals(title)) {
/* 165 */             out.write(" class=\"tabLink\"");
/*     */           }
/* 167 */           out.write(32);
/* 168 */           if (selectedtab.equals(title)) {
/* 169 */             out.write("class=\"tabLinkActive\"");
/*     */           }
/* 171 */           out.write("  id=\"A_");
/* 172 */           out.print(title);
/* 173 */           out.write("Tab\">");
/* 174 */           out.print(FormatUtil.getString(title));
/* 175 */           out.write("</span> </a> <a href=\"javascript:void(0);\"  onclick=\"showMGDashboards('mgDashboardDetailsHook','dashboardsdiv','");
/* 176 */           if (_jspx_meth_c_005fout_005f6(_jspx_page_context))
/*     */             return;
/* 178 */           out.write("');\">");
/* 179 */           if (!gpType.equals("3")) {
/* 180 */             out.write(" <img height=\"4\" border=\"0\" width=\"7\" vspace=\"2\"  src=\"/images/icon_black_arrow.gif\" valign=\"absmiddle\"/>");
/*     */           }
/* 182 */           out.write("</a>\n                      </td>\n                      <td ");
/* 183 */           if (!selectedtab.equals(title)) {
/* 184 */             out.write("class=\"tbUnselected_Right\"");
/*     */           }
/* 186 */           out.write(32);
/* 187 */           if (selectedtab.equals(title)) {
/* 188 */             out.write("class=\"tbSelected_Right\"");
/*     */           }
/* 190 */           out.write("><img width=\"1\" height=\"1\" alt=\"spacer\" src=\"/images/spacer.gif\"/></td>\n                    </tr>\n                  </tbody>\n                </table>\n              </td>\n             <td align=\"right\">\n      <div id=\"loadingg\" style=\"display:none;\">\n      &nbsp;&nbsp;<img alt=\"Loading...\" src=\"/images/icon_cogwheel.gif\" border=\"0\"/>\n      </div>\n      </td>\n");
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 196 */           out.write("\n\n              <td><table title=\"");
/* 197 */           out.print(FormatUtil.getString(tooltip));
/* 198 */           out.write("\" id=\"");
/* 199 */           out.print(title);
/* 200 */           out.write("Tab\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\" >\n                  <tbody>\n                    <tr>\n                      <td ");
/* 201 */           if (!selectedtab.equals(title)) {
/* 202 */             out.write("class=\"tbUnselected_Left\"");
/*     */           }
/* 204 */           if (selectedtab.equals(title)) {
/* 205 */             out.write("class=\"tbSelected_Left\"");
/*     */           }
/* 207 */           out.write(" ><img width=\"1\" height=\"1\" alt=\"spacer\" src=\"/images/spacer.gif\"/></td>\n                      <td ");
/* 208 */           if (!selectedtab.equals(title)) {
/* 209 */             out.write("class=\"tbUnselected_Middle\"");
/*     */           }
/* 211 */           out.write(32);
/* 212 */           if (selectedtab.equals(title)) {
/* 213 */             out.write("class=\"tbSelected_Middle\"");
/*     */           }
/* 215 */           out.write(" onClick=\"SetTabStyle('");
/* 216 */           out.print(title);
/* 217 */           out.write("','InnerTab');");
/* 218 */           out.print(functionName);
/* 219 */           out.write(40);
/* 220 */           out.write(39);
/* 221 */           out.print(resourceid);
/* 222 */           out.write(39);
/* 223 */           out.write(44);
/* 224 */           out.write(39);
/* 225 */           out.print(resourcename);
/* 226 */           out.write(39);
/* 227 */           out.write(44);
/* 228 */           out.write(39);
/* 229 */           out.print(errorcode);
/* 230 */           out.write("');\" style=\"padding-left:5px;padding-right:5px\">\n\t      <span  ");
/* 231 */           if (!selectedtab.equals(title)) {
/* 232 */             out.write(" class=\"tablink\"");
/*     */           }
/* 234 */           out.write(32);
/* 235 */           if (selectedtab.equals(title)) {
/* 236 */             out.write("class=\"tabLinkActive\"");
/*     */           }
/* 238 */           out.write("  id=\"A_");
/* 239 */           out.print(title);
/* 240 */           out.write("Tab\">");
/* 241 */           out.print(FormatUtil.getString(title));
/* 242 */           out.write("</span>\n                      </td>\n                      <td ");
/* 243 */           if (!selectedtab.equals(title)) {
/* 244 */             out.write("class=\"tbUnselected_Right\"");
/*     */           }
/* 246 */           out.write(32);
/* 247 */           if (selectedtab.equals(title)) {
/* 248 */             out.write("class=\"tbSelected_Right\"");
/*     */           }
/* 250 */           out.write("><img width=\"1\" height=\"1\" alt=\"spacer\" src=\"/images/spacer.gif\"/></td>\n                    </tr>\n                  </tbody>\n                </table>\n              </td>\n");
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 255 */       out.write("\n            </tr>\n          </tbody>\n      \t</table>\n      </td>\n      <td align=\"right\">\n      <div id=\"loadingg\" style=\"display:none;\">\n      <img alt=\"Loading...\" src=\"/images/icon_cogwheel.gif\" border=\"0\"/>\n      </div>\n      </td>\n      ");
/*     */       
/* 257 */       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 258 */       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 259 */       _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */       
/* 261 */       _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN");
/* 262 */       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 263 */       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */         for (;;) {
/* 265 */           out.write("\n<td align=\"right\"  width=\"43%\" style=\"white-space: nowrap;\">\n<div id=\"mgTabMGActions\" style='display:inline;float: right;'>\n                              <table  id=\"MGactionListTable\" border=\"0\" style=\"position:relative; bottom:2px;\" cellspacing=\"0\" cellpadding=\"0\" onclick=\"showOrangeBGActions('MGactionListTable', 'mgSettingsDiv');\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"buttons btn_action\" align=\"center\" ><img  width=\"12\" border=\"0\" height=\"13\" alt=\"spacer\" src=\"/images/admin-actions.gif\"/> <b>");
/* 266 */           out.print(FormatUtil.getString("am.webclient.monitorgroupactions.text"));
/* 267 */           out.write("</b>&nbsp;<img  vspace=\"2\" width=\"7\" border=\"0\" height=\"4\" alt=\"spacer\" src=\"/images/red-down-arrow.gif\"/></td>\n\n\t\t\t\t</tr>\n            \t\t\t     </table>\n</div>\n\t\t\n\t\t");
/*     */           
/* 269 */           int mgId = Integer.parseInt(request.getParameter("haid"));
/*     */           
/* 271 */           out.write("\n\n\t\t");
/* 272 */           if (((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgId < com.adventnet.appmanager.server.framework.comm.Constants.RANGE) && (request.isUserInRole("ENTERPRISEADMIN"))) || (request.isUserInRole("ADMIN"))) {
/* 273 */             out.write("\n\t\t<div id=\"editMonitorGroup\" style='display:inline;float: right;position: relative;right: 5px;top:3px;'>\n\t\t");
/*     */             
/* 275 */             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 276 */             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 277 */             _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 279 */             _jspx_th_c_005fif_005f0.setTest("${(!(grouptype==\"1009\") && !(grouptype==\"1010\") && !(grouptype==\"1012\") && !(grouptype==\"1013\"))}");
/* 280 */             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 281 */             if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */               for (;;) {
/* 283 */                 out.write("\n\t\t\t");
/* 284 */                 if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */                   return;
/* 286 */                 out.write("\n\t\t\t\t<a  href=\"/showapplication.do?method=editApplication&haid=");
/* 287 */                 if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */                   return;
/* 289 */                 out.write("\" class=\"bulkmon-links onHoverCSS\" >");
/* 290 */                 out.print(FormatUtil.getString("am.webclient.maintenance.edit"));
/* 291 */                 out.write("</a>&nbsp;\n\t\t");
/* 292 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 293 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 297 */             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 298 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*     */             }
/*     */             
/* 301 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 302 */             out.write("\t\n\t\t</div>\n\t\t<div id=\"associateMonitorsforGroup\" style='display:inline;float:right;position: relative;right: 10px;top:3px;'>\n\t\t&nbsp;");
/*     */             
/* 304 */             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 305 */             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 306 */             _jspx_th_c_005fif_005f2.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 308 */             _jspx_th_c_005fif_005f2.setTest("${!(grouptype==\"3\") && !(grouptype==\"1009\") && !(grouptype==\"1010\") && !(grouptype==\"1012\") && !(grouptype==\"1013\")}");
/* 309 */             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 310 */             if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */               for (;;) {
/* 312 */                 out.write("\n\n\t\t\t\t\n\t\t\t\t\t\t<a href=\"/showresource.do?method=getMonitorForm&type=All&haid=");
/* 313 */                 if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*     */                   return;
/* 315 */                 out.write("\" class=\"bulkmon-links onHoverCSS\" >");
/* 316 */                 out.print(FormatUtil.getString("am.webclient.applicationlinks.associatemonitor.text"));
/* 317 */                 out.write("</a>\n\t\t\t\t\n\n\n\t\t\t");
/* 318 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 319 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 323 */             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 324 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*     */             }
/*     */             
/* 327 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 328 */             out.write("\n\t\t</div>\n\t\t");
/*     */           }
/* 330 */           out.write("\n\n <div id=\"mgTabdashboardActions\" style='display:none'>\n                        <table id=\"actionListTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"position:relative; bottom:2px;\" onclick=\"DashboardActions('actionListTable', 'actionListDiv')\">\n                                                        <tr >\n                                                                <td class=\"btnon\" onmouseup=\"className='btnover'\"  onmousedown=\"className='btnclick'\"  onmouseover=\"className='btnover';\" onmouseout=\"className='btnout';\"  class=\"button-mid-tile\" align=\"center\" ><img  width=\"12\" border=\"0\" height=\"13\" alt=\"spacer\" src=\"/images/admin-actions.gif\"/> <b>");
/* 331 */           out.print(FormatUtil.getString("am.mypage.dashbobard.actions.text"));
/* 332 */           out.write(" </b><img  vspace=\"2\" width=\"7\" border=\"0\" height=\"4\" alt=\"spacer\" src=\"/images/red-down-arrow.gif\"/></td>\n\n                                                        </tr>\n                                                     </table>\n\n                        </div>\n\n            </td>\n             ");
/* 333 */           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 334 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 338 */       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 339 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*     */       }
/*     */       else {
/* 342 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 343 */         out.write("\n </tr>\n  </tbody>\n</table>\n\n            ");
/*     */         
/* 345 */         PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 346 */         _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 347 */         _jspx_th_logic_005fpresent_005f1.setParent(null);
/*     */         
/* 349 */         _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/* 350 */         int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 351 */         if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*     */           for (;;) {
/* 353 */             out.write("\n\t\t\t<div style=\"display:none;\" id=\"actionListDiv\">\n\t\t\t<table id=\"dropMenuMgActions\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\" align=\"right\" class=\"action-but-padding1\">\n\t\t\t<TR>\n\n\t\t\t<td>\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"action-but-border\">\n\t\t\t<tr>\n\t\t\t<td>\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t<tr><td height=\"6\"></td></tr>\n\t\t\t<tr>\n\t\t\t<td >\n\t\t\t<span class=\"bodytext\"><b  style=\"text-decoration:none; cursor:text;\">&nbsp; ");
/* 354 */             out.print(FormatUtil.getString("am.mypage.dashbobard.actions.text"));
/* 355 */             out.write("</b></span>\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr><td height=\"6\"></td></tr>\n\t\t\t<tr>\n\t\t\t<td >\n\t\t\t<a href= \"javascript:void(0)\" onclick=\"addWidgets()\" style=\"text-decoration:none;\"><span class=\"bodytext\"><img src=\"images/add-selectedMonitors.gif\" border=\"0\"/> ");
/* 356 */             out.print(FormatUtil.getString("am.mypage.add.widgets.text"));
/* 357 */             out.write("</span></a>\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td  >\n\t\t\t<a href=\"javascript:editMyPage('");
/* 358 */             if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*     */               return;
/* 360 */             out.write("');\"><span class=\"bodytext\"><img src=\"images/icon_edit.gif\" border=\"0\"/> ");
/* 361 */             out.print(FormatUtil.getString("am.mypage.edit.dashboard.text"));
/* 362 */             out.write("</span></a>\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td >\n\t\t\t<a href=\"javascript:deleteDashboard('");
/* 363 */             if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*     */               return;
/* 365 */             out.write("');\" ><span class=\"bodytext\"><img src=\"images/deleteWidget.gif\" border=\"0\"/> ");
/* 366 */             out.print(FormatUtil.getString("am.mypage.delete.dashboard.text"));
/* 367 */             out.write("</span></a>\n\t\t\t</td>\n\t\t\t</tr>\n\n\n\t\t\t<tr>\n\t\t\t<td >\n\t\t\t<a href= \"/MyPage.do?pagetype=mgtemplate&template_resid=");
/* 368 */             if (_jspx_meth_c_005fout_005f11(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*     */               return;
/* 370 */             out.write("&method=newMyPage\" ><span class=\"bodytext\"><img src=\"images/icon_new_dashboard.png\" border=\"0\"/> ");
/* 371 */             out.print(FormatUtil.getString("am.mypage.new.dashboard.text"));
/* 372 */             out.write("</span></a>\n\t\t\t</td>\n\t\t\t</tr>\n\n\t\t\t<tr><td height=\"6\"></td></tr>\n\n\t\t\t</table>\n\t\t\t</td>\n\n\n\n\n\n\n\n\n\t\t\t<td valign=\"top\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t<tr><td height=\"6\"></td></tr>\n\t\t\t<tr>\n\t\t\t<td>\n\t\t\t<span class=\"bodytext\"><b  style=\"text-decoration:none; cursor:text;\">&nbsp; ");
/* 373 */             out.print(FormatUtil.getString("Views"));
/* 374 */             out.write("</b></span>\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr><td height=\"6\"></td></tr>\n\t\t\t<tr>\n\n\t\t\t<tr>\n\t\t\t<td>\n\t\t\t<a href=\"javascript:void(0);\" id=\"publishMghook\" onclick=\"javascript:publishDashboard('");
/* 375 */             if (_jspx_meth_c_005fout_005f12(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*     */               return;
/* 377 */             out.write("')\"><span class=\"bodytext\"><img src=\"images/icon_publish_dsboard.gif\" border=\"0\"/> ");
/* 378 */             out.print(FormatUtil.getString("am.mypage.dashboard.public.dashboard.text"));
/* 379 */             out.write("</span></a>\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td >\n\t\t\t<a href= \"javascript:void(0);\" onclick=\"javascript:popOut('");
/* 380 */             if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*     */               return;
/* 382 */             out.write("');\" ><span class=\"bodytext\"><img src=\"images/dashboard-popout.gif\" border=\"0\"/> ");
/* 383 */             out.print(FormatUtil.getString("am.webclient.mypage.popout.text"));
/* 384 */             out.write("</span></a>\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td>\n\t\t\t<a href=\"javascript:setAsDefault('");
/* 385 */             if (_jspx_meth_c_005fout_005f14(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/*     */               return;
/* 387 */             out.write("','2');\"><span class=\"bodytext\"><img src=\"images/icon_set_default.gif\" border=\"0\"/> ");
/* 388 */             out.print(FormatUtil.getString("am.mypage.setasdefault.text"));
/* 389 */             out.write("</span></a>\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\n\n\n\t\t\t</TR>\n<tr><td height=\"6\"></td></tr>\n\t\t\t</table>\n\t\t</div>\n\t\t\t");
/* 390 */             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 391 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 395 */         if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 396 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*     */         }
/*     */         else {
/* 399 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 400 */           out.write("\n<script type=\"text/javascript\">\n\t\t\tjQuery(document).ready(function()\n{\n\t$('.onHoverCSS').hover( //NO I18N\n\t    function() {\n\t        $(this).css({'text-decoration':'underline','cursor':'pointer'});//No I18N\n\t    },\n\t    function() {\n\t        $(this).css({'text-decoration':'none','cursor':'normal'}); //No I18N\n\t    }\n    )\n });\n</script>\n");
/*     */         }
/* 402 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 403 */         out = _jspx_out;
/* 404 */         if ((out != null) && (out.getBufferSize() != 0))
/* 405 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 406 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 409 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 415 */     PageContext pageContext = _jspx_page_context;
/* 416 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 418 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 419 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 420 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 422 */     _jspx_th_c_005fout_005f0.setValue("${pageid}");
/* 423 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 424 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 425 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 426 */       return true;
/*     */     }
/* 428 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 429 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 434 */     PageContext pageContext = _jspx_page_context;
/* 435 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 437 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 438 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 439 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 441 */     _jspx_th_c_005fout_005f1.setValue("${param.widgetid}");
/* 442 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 443 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 444 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 445 */       return true;
/*     */     }
/* 447 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 448 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 453 */     PageContext pageContext = _jspx_page_context;
/* 454 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 456 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 457 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 458 */     _jspx_th_c_005fout_005f2.setParent(null);
/*     */     
/* 460 */     _jspx_th_c_005fout_005f2.setValue("${pageid}");
/* 461 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 462 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 463 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 464 */       return true;
/*     */     }
/* 466 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 467 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 472 */     PageContext pageContext = _jspx_page_context;
/* 473 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 475 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 476 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 477 */     _jspx_th_c_005fout_005f3.setParent(null);
/*     */     
/* 479 */     _jspx_th_c_005fout_005f3.setValue("${pageid}");
/* 480 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 481 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 482 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 483 */       return true;
/*     */     }
/* 485 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 486 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 491 */     PageContext pageContext = _jspx_page_context;
/* 492 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 494 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 495 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 496 */     _jspx_th_c_005fout_005f4.setParent(null);
/*     */     
/* 498 */     _jspx_th_c_005fout_005f4.setValue("${pageid}");
/* 499 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 500 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 501 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 502 */       return true;
/*     */     }
/* 504 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 505 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f5(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 510 */     PageContext pageContext = _jspx_page_context;
/* 511 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 513 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 514 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 515 */     _jspx_th_c_005fout_005f5.setParent(null);
/*     */     
/* 517 */     _jspx_th_c_005fout_005f5.setValue("${pageid}");
/* 518 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 519 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 520 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 521 */       return true;
/*     */     }
/* 523 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 524 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f6(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 529 */     PageContext pageContext = _jspx_page_context;
/* 530 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 532 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 533 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 534 */     _jspx_th_c_005fout_005f6.setParent(null);
/*     */     
/* 536 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 537 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 538 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 539 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 540 */       return true;
/*     */     }
/* 542 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 543 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 548 */     PageContext pageContext = _jspx_page_context;
/* 549 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 551 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 552 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 553 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 555 */     _jspx_th_c_005fif_005f1.setTest("${!(grouptype==\"3\")}");
/* 556 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 557 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */       for (;;) {
/* 559 */         out.write("\n\t\t\t<span class=\"ancillary1\">|</span>&nbsp;\n\t\t\t");
/* 560 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 561 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 565 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 566 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 567 */       return true;
/*     */     }
/* 569 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 570 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 575 */     PageContext pageContext = _jspx_page_context;
/* 576 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 578 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 579 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 580 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 582 */     _jspx_th_c_005fout_005f7.setValue("${param.haid}");
/* 583 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 584 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 585 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 586 */       return true;
/*     */     }
/* 588 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 589 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 594 */     PageContext pageContext = _jspx_page_context;
/* 595 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 597 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 598 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 599 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 601 */     _jspx_th_c_005fout_005f8.setValue("${param.haid}");
/* 602 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 603 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 604 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 605 */       return true;
/*     */     }
/* 607 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 608 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 613 */     PageContext pageContext = _jspx_page_context;
/* 614 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 616 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 617 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 618 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*     */     
/* 620 */     _jspx_th_c_005fout_005f9.setValue("${param.haid}");
/* 621 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 622 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 623 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 624 */       return true;
/*     */     }
/* 626 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 627 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 632 */     PageContext pageContext = _jspx_page_context;
/* 633 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 635 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 636 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 637 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*     */     
/* 639 */     _jspx_th_c_005fout_005f10.setValue("${param.haid}");
/* 640 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 641 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 642 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 643 */       return true;
/*     */     }
/* 645 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 646 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 651 */     PageContext pageContext = _jspx_page_context;
/* 652 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 654 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 655 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 656 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*     */     
/* 658 */     _jspx_th_c_005fout_005f11.setValue("${param.haid}");
/* 659 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 660 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 661 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 662 */       return true;
/*     */     }
/* 664 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 665 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 670 */     PageContext pageContext = _jspx_page_context;
/* 671 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 673 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 674 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 675 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*     */     
/* 677 */     _jspx_th_c_005fout_005f12.setValue("${param.haid}");
/* 678 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 679 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 680 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 681 */       return true;
/*     */     }
/* 683 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 684 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 689 */     PageContext pageContext = _jspx_page_context;
/* 690 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 692 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 693 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 694 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*     */     
/* 696 */     _jspx_th_c_005fout_005f13.setValue("${param.haid}");
/* 697 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 698 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 699 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 700 */       return true;
/*     */     }
/* 702 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 703 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 708 */     PageContext pageContext = _jspx_page_context;
/* 709 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 711 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 712 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 713 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*     */     
/* 715 */     _jspx_th_c_005fout_005f14.setValue("${param.haid}");
/* 716 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 717 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 718 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 719 */       return true;
/*     */     }
/* 721 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 722 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MonitorGroupTabs_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */