/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.tags.AdminLink;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.EmptyTag;
/*     */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*     */ import org.apache.struts.taglib.logic.NotPresentTag;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class UrlMonitorsLeftPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  21 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  27 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  28 */   static { _jspx_dependants.put("/jsp/includes/DiscoveryLeftLinks.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  44 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  48 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  57 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  61 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  62 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  63 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*  64 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  65 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  66 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.release();
/*  67 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*  68 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  75 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  78 */     JspWriter out = null;
/*  79 */     Object page = this;
/*  80 */     JspWriter _jspx_out = null;
/*  81 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  85 */       response.setContentType("text/html");
/*  86 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  88 */       _jspx_page_context = pageContext;
/*  89 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  90 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  91 */       session = pageContext.getSession();
/*  92 */       out = pageContext.getOut();
/*  93 */       _jspx_out = out;
/*     */       
/*  95 */       out.write("\n<!--$Id$-->\n\n\n\n\n\n\n\n");
/*     */       
/*     */ 
/*  98 */       String resourceName = request.getParameter("type");
/*  99 */       String appname = request.getParameter("name");
/* 100 */       String haid = request.getParameter("haid");
/*     */       
/* 102 */       out.write("\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n\n  \n\n");
/* 103 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n<script>\nfunction fnalert()\n{\n  alertUser();\n  return;\n}\nfunction Call()\n{\n alert(\"");
/* 104 */       out.print(FormatUtil.getString("am.webclient.discoverylinks.alert"));
/* 105 */       out.write("\");\n}\n</script>\n");
/* 106 */       if ((!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (!com.adventnet.appmanager.util.Constants.isIt360)) {
/* 107 */         out.write(10);
/*     */         
/* 109 */         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 110 */         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 111 */         _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */         
/* 113 */         _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 114 */         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 115 */         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */           for (;;) {
/* 117 */             out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"leftmnutables\">\n  <tr> \n    <td height=\"19\" class=\"leftlinksheading\">");
/* 118 */             out.print(FormatUtil.getString("am.webclient.discoverylinks.heading.text"));
/* 119 */             out.write("</td>\n  </tr>\n  <tr> \n    <!--td height=\"19\" class=\"leftlinkstd\"><a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999\" class=\"new-left-links\">New Monitor</a></td-->\n   ");
/*     */             
/* 121 */             PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 122 */             _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 123 */             _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 125 */             _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 126 */             int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 127 */             if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*     */               for (;;) {
/* 129 */                 out.write("\n    ");
/*     */                 
/* 131 */                 if (request.getParameter("wiz") != null)
/*     */                 {
/* 133 */                   out.write("\n\t  <td height=\"19\" class=\"leftlinkstd\"><a href='#' onclick=\"javascript:Call()\" title=\"Disabled in the wizard\" class='disabledlink'>");
/* 134 */                   out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/* 135 */                   out.write("</a></td>\n\t");
/*     */                 }
/*     */                 else
/*     */                 {
/* 139 */                   out.write("\n    <td height=\"19\"  class=\"leftlinkstd\" > <a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=UrlMonitor\" class=\"new-left-links\">");
/* 140 */                   out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/* 141 */                   out.write("</a></td>\n\t");
/*     */                 }
/* 143 */                 out.write("\n    ");
/* 144 */                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 145 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 149 */             if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 150 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*     */             }
/*     */             
/* 153 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 154 */             out.write("\n    ");
/*     */             
/* 156 */             NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 157 */             _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 158 */             _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 160 */             _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 161 */             int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 162 */             if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */               for (;;) {
/* 164 */                 out.write(32);
/* 165 */                 out.write(10);
/* 166 */                 out.write(9);
/*     */                 
/* 168 */                 if (request.getParameter("wiz") != null)
/*     */                 {
/* 170 */                   out.write("\n     <td height=\"19\" class=\"leftlinkstd\"><a href='#' onclick=\"javascript:Call()\" title=\"Disabled in the wizard\" class='disabledlink'>");
/* 171 */                   out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/* 172 */                   out.write("</a></td>\n\t");
/*     */                 }
/*     */                 else
/*     */                 {
/* 176 */                   out.write("\n    <td height=\"19\"  class=\"leftlinkstd\" > ");
/*     */                   
/* 178 */                   AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 179 */                   _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 180 */                   _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */                   
/* 182 */                   _jspx_th_am_005fadminlink_005f0.setHref("/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999");
/*     */                   
/* 184 */                   _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 185 */                   int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 186 */                   if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 187 */                     if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 188 */                       out = _jspx_page_context.pushBody();
/* 189 */                       _jspx_th_am_005fadminlink_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 190 */                       _jspx_th_am_005fadminlink_005f0.doInitBody();
/*     */                     }
/*     */                     for (;;) {
/* 193 */                       out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/* 194 */                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 195 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/* 198 */                     if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 199 */                       out = _jspx_page_context.popBody();
/*     */                     }
/*     */                   }
/* 202 */                   if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 203 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*     */                   }
/*     */                   
/* 206 */                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 207 */                   out.write("</td>");
/* 208 */                   out.write(32);
/* 209 */                   out.write(10);
/* 210 */                   out.write(9);
/*     */                 }
/* 212 */                 out.write(10);
/* 213 */                 out.write(9);
/* 214 */                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 215 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 219 */             if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 220 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*     */             }
/*     */             
/* 223 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 224 */             out.write("\n    \n    \n  </tr>\n  ");
/*     */             
/* 226 */             com.adventnet.appmanager.server.framework.FreeEditionDetails license = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails();
/* 227 */             String licensetype = license.getUserType();
/* 228 */             pageContext.setAttribute("licensetype", licensetype);
/* 229 */             if (!licensetype.equals("F"))
/*     */             {
/*     */ 
/* 232 */               out.write("\n  <tr> \n   ");
/*     */               
/* 234 */               PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 235 */               _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 236 */               _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */               
/* 238 */               _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 239 */               int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 240 */               if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*     */                 for (;;) {
/* 242 */                   out.write("\n   <td width=\"95%\" height=\"19\" class=\"leftlinkstd\"><a href=\"javascript:fnalert()\" class=\"new-left-links\"> \n      ");
/* 243 */                   out.print(FormatUtil.getString("am.webclient.discoverylinks.network.text"));
/* 244 */                   out.write("</a></td>\n   ");
/* 245 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 246 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 250 */               if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 251 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*     */               }
/*     */               
/* 254 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 255 */               out.write("\n   ");
/*     */               
/* 257 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 258 */               _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 259 */               _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */               
/* 261 */               _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 262 */               int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 263 */               if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*     */                 for (;;) {
/* 265 */                   out.write("\n    ");
/*     */                   
/* 267 */                   if (!com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*     */                   {
/*     */ 
/* 270 */                     out.write("\n    <td width=\"95%\" height=\"19\" class=\"leftlinkstd\">");
/*     */                     
/* 272 */                     AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 273 */                     _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 274 */                     _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*     */                     
/* 276 */                     _jspx_th_am_005fadminlink_005f1.setHref("/adminAction.do?method=showNetworkDiscoveryForm");
/*     */                     
/* 278 */                     _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 279 */                     int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 280 */                     if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 281 */                       if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 282 */                         out = _jspx_page_context.pushBody();
/* 283 */                         _jspx_th_am_005fadminlink_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 284 */                         _jspx_th_am_005fadminlink_005f1.doInitBody();
/*     */                       }
/*     */                       for (;;) {
/* 287 */                         out.write(" \n      ");
/* 288 */                         out.print(FormatUtil.getString("am.webclient.discoverylinks.network.text"));
/* 289 */                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 290 */                         if (evalDoAfterBody != 2)
/*     */                           break;
/*     */                       }
/* 293 */                       if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 294 */                         out = _jspx_page_context.popBody();
/*     */                       }
/*     */                     }
/* 297 */                     if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 298 */                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*     */                     }
/*     */                     
/* 301 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 302 */                     out.write(" </td>\n      ");
/*     */                   }
/*     */                   
/*     */ 
/* 306 */                   out.write("\n\t  ");
/* 307 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 308 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 312 */               if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 313 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*     */               }
/*     */               
/* 316 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 317 */               out.write("\n  </tr>\n  ");
/*     */             }
/*     */             
/*     */ 
/* 321 */             out.write("\n</table>\n");
/* 322 */             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 323 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 327 */         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 328 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*     */         }
/*     */         
/* 331 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 332 */         out.write(10);
/*     */       }
/* 334 */       out.write(10);
/* 335 */       out.write(10);
/*     */       
/* 337 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 338 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 339 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/* 341 */       _jspx_th_c_005fif_005f0.setTest("${param.type=='UrlSeq'}");
/* 342 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 343 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */         for (;;) {
/* 345 */           out.write("\n<br>\n<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 346 */           out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 347 */           out.write("</td>\n    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 348 */           if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */             return;
/* 350 */           out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n\n  </tr>\n  <tr> \n    <td class=\"quicknote\" colspan='2'>\n    ");
/* 351 */           if (_jspx_meth_logic_005fempty_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */             return;
/* 353 */           out.write("\n    ");
/* 354 */           if (_jspx_meth_logic_005fnotEmpty_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */             return;
/* 356 */           out.write("    \n    <!--Fill in the details for monitoring a url sequence.--></td>\n  </tr>\n</table>\n");
/* 357 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 358 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 362 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 363 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 366 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 367 */         out.write(10);
/*     */       }
/* 369 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 370 */         out = _jspx_out;
/* 371 */         if ((out != null) && (out.getBufferSize() != 0))
/* 372 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 373 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 376 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 382 */     PageContext pageContext = _jspx_page_context;
/* 383 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 385 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 386 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 387 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 389 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 391 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 392 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 393 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 394 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 395 */       return true;
/*     */     }
/* 397 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 398 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fempty_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 403 */     PageContext pageContext = _jspx_page_context;
/* 404 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 406 */     EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 407 */     _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 408 */     _jspx_th_logic_005fempty_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 410 */     _jspx_th_logic_005fempty_005f0.setName("UrlForm");
/*     */     
/* 412 */     _jspx_th_logic_005fempty_005f0.setProperty("userseqid");
/* 413 */     int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 414 */     if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*     */       for (;;) {
/* 416 */         out.write("\n    ");
/* 417 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fempty_005f0, _jspx_page_context))
/* 418 */           return true;
/* 419 */         out.write("\n    ");
/* 420 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 421 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 425 */     if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 426 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 427 */       return true;
/*     */     }
/* 429 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 430 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fempty_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 435 */     PageContext pageContext = _jspx_page_context;
/* 436 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 438 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 439 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 440 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fempty_005f0);
/* 441 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 442 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 443 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 444 */         out = _jspx_page_context.pushBody();
/* 445 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 446 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 449 */         out.write("quicknote.urlconf.firstpage");
/* 450 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 451 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 454 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 455 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 458 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 459 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 460 */       return true;
/*     */     }
/* 462 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 463 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fnotEmpty_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 468 */     PageContext pageContext = _jspx_page_context;
/* 469 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 471 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 472 */     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 473 */     _jspx_th_logic_005fnotEmpty_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 475 */     _jspx_th_logic_005fnotEmpty_005f0.setName("UrlForm");
/*     */     
/* 477 */     _jspx_th_logic_005fnotEmpty_005f0.setProperty("userseqid");
/* 478 */     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 479 */     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*     */       for (;;) {
/* 481 */         out.write("\n    ");
/* 482 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/* 483 */           return true;
/* 484 */         out.write("\n    ");
/* 485 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 486 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 490 */     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 491 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 492 */       return true;
/*     */     }
/* 494 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 495 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 500 */     PageContext pageContext = _jspx_page_context;
/* 501 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 503 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 504 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 505 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fnotEmpty_005f0);
/* 506 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 507 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 508 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 509 */         out = _jspx_page_context.pushBody();
/* 510 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 511 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 514 */         out.write("quicknote.urlconf.nextpage");
/* 515 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 516 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 519 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 520 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 523 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 524 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 525 */       return true;
/*     */     }
/* 527 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 528 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\UrlMonitorsLeftPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */