/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.io.IOException;
/*      */ import java.util.Map;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class DiscoveryLeftLinks_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   26 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   32 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   33 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*   34 */     _jspx_dependants.put("/jsp/includes/DiscoveryLeftLinks.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*   50 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   54 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   55 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   56 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   63 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   67 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   68 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*   69 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*   70 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   71 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   72 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   73 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   74 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*   81 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   84 */     JspWriter out = null;
/*   85 */     Object page = this;
/*   86 */     JspWriter _jspx_out = null;
/*   87 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*   91 */       response.setContentType("text/html");
/*   92 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*   94 */       _jspx_page_context = pageContext;
/*   95 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*   96 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*   97 */       session = pageContext.getSession();
/*   98 */       out = pageContext.getOut();
/*   99 */       _jspx_out = out;
/*      */       
/*  101 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */       
/*  103 */       if (!Constants.isIt360)
/*      */       {
/*  105 */         out.write(10);
/*  106 */         out.write(10);
/*  107 */         out.write("<!--$Id$-->\n\n\n\n\n\n\n\n<script>\nfunction fnalert()\n{\n  alertUser();\n  return;\n}\nfunction Call()\n{\n alert(\"");
/*  108 */         out.print(FormatUtil.getString("am.webclient.discoverylinks.alert"));
/*  109 */         out.write("\");\n}\n</script>\n");
/*  110 */         if ((!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (!Constants.isIt360)) {
/*  111 */           out.write(10);
/*      */           
/*  113 */           PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  114 */           _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  115 */           _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */           
/*  117 */           _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/*  118 */           int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  119 */           if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */             for (;;) {
/*  121 */               out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"leftmnutables\">\n  <tr> \n    <td height=\"19\" class=\"leftlinksheading\">");
/*  122 */               out.print(FormatUtil.getString("am.webclient.discoverylinks.heading.text"));
/*  123 */               out.write("</td>\n  </tr>\n  <tr> \n    <!--td height=\"19\" class=\"leftlinkstd\"><a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999\" class=\"new-left-links\">New Monitor</a></td-->\n   ");
/*      */               
/*  125 */               PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  126 */               _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  127 */               _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */               
/*  129 */               _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/*  130 */               int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  131 */               if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                 for (;;) {
/*  133 */                   out.write("\n    ");
/*      */                   
/*  135 */                   if (request.getParameter("wiz") != null)
/*      */                   {
/*  137 */                     out.write("\n\t  <td height=\"19\" class=\"leftlinkstd\"><a href='#' onclick=\"javascript:Call()\" title=\"Disabled in the wizard\" class='disabledlink'>");
/*  138 */                     out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/*  139 */                     out.write("</a></td>\n\t");
/*      */                   }
/*      */                   else
/*      */                   {
/*  143 */                     out.write("\n    <td height=\"19\"  class=\"leftlinkstd\" > <a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=UrlMonitor\" class=\"new-left-links\">");
/*  144 */                     out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/*  145 */                     out.write("</a></td>\n\t");
/*      */                   }
/*  147 */                   out.write("\n    ");
/*  148 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/*  149 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  153 */               if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/*  154 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */               }
/*      */               
/*  157 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*  158 */               out.write("\n    ");
/*      */               
/*  160 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  161 */               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  162 */               _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */               
/*  164 */               _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  165 */               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  166 */               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                 for (;;) {
/*  168 */                   out.write(32);
/*  169 */                   out.write(10);
/*  170 */                   out.write(9);
/*      */                   
/*  172 */                   if (request.getParameter("wiz") != null)
/*      */                   {
/*  174 */                     out.write("\n     <td height=\"19\" class=\"leftlinkstd\"><a href='#' onclick=\"javascript:Call()\" title=\"Disabled in the wizard\" class='disabledlink'>");
/*  175 */                     out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/*  176 */                     out.write("</a></td>\n\t");
/*      */                   }
/*      */                   else
/*      */                   {
/*  180 */                     out.write("\n    <td height=\"19\"  class=\"leftlinkstd\" > ");
/*      */                     
/*  182 */                     AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  183 */                     _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/*  184 */                     _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                     
/*  186 */                     _jspx_th_am_005fadminlink_005f0.setHref("/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999");
/*      */                     
/*  188 */                     _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/*  189 */                     int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/*  190 */                     if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/*  191 */                       if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/*  192 */                         out = _jspx_page_context.pushBody();
/*  193 */                         _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/*  194 */                         _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  197 */                         out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/*  198 */                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/*  199 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  202 */                       if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/*  203 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  206 */                     if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/*  207 */                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                     }
/*      */                     
/*  210 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/*  211 */                     out.write("</td>");
/*  212 */                     out.write(32);
/*  213 */                     out.write(10);
/*  214 */                     out.write(9);
/*      */                   }
/*  216 */                   out.write(10);
/*  217 */                   out.write(9);
/*  218 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  219 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  223 */               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  224 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */               }
/*      */               
/*  227 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  228 */               out.write("\n    \n    \n  </tr>\n  ");
/*      */               
/*  230 */               FreeEditionDetails license = FreeEditionDetails.getFreeEditionDetails();
/*  231 */               String licensetype = license.getUserType();
/*  232 */               pageContext.setAttribute("licensetype", licensetype);
/*  233 */               if (!licensetype.equals("F"))
/*      */               {
/*      */ 
/*  236 */                 out.write("\n  <tr> \n   ");
/*      */                 
/*  238 */                 PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  239 */                 _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/*  240 */                 _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                 
/*  242 */                 _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/*  243 */                 int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/*  244 */                 if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                   for (;;) {
/*  246 */                     out.write("\n   <td width=\"95%\" height=\"19\" class=\"leftlinkstd\"><a href=\"javascript:fnalert()\" class=\"new-left-links\"> \n      ");
/*  247 */                     out.print(FormatUtil.getString("am.webclient.discoverylinks.network.text"));
/*  248 */                     out.write("</a></td>\n   ");
/*  249 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/*  250 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  254 */                 if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/*  255 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                 }
/*      */                 
/*  258 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/*  259 */                 out.write("\n   ");
/*      */                 
/*  261 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  262 */                 _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  263 */                 _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                 
/*  265 */                 _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/*  266 */                 int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  267 */                 if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                   for (;;) {
/*  269 */                     out.write("\n    ");
/*      */                     
/*  271 */                     if (!Constants.isMinimizedversion())
/*      */                     {
/*      */ 
/*  274 */                       out.write("\n    <td width=\"95%\" height=\"19\" class=\"leftlinkstd\">");
/*      */                       
/*  276 */                       AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  277 */                       _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/*  278 */                       _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                       
/*  280 */                       _jspx_th_am_005fadminlink_005f1.setHref("/adminAction.do?method=showNetworkDiscoveryForm");
/*      */                       
/*  282 */                       _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/*  283 */                       int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/*  284 */                       if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/*  285 */                         if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/*  286 */                           out = _jspx_page_context.pushBody();
/*  287 */                           _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/*  288 */                           _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/*  291 */                           out.write(" \n      ");
/*  292 */                           out.print(FormatUtil.getString("am.webclient.discoverylinks.network.text"));
/*  293 */                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/*  294 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*  297 */                         if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/*  298 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/*  301 */                       if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/*  302 */                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                       }
/*      */                       
/*  305 */                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/*  306 */                       out.write(" </td>\n      ");
/*      */                     }
/*      */                     
/*      */ 
/*  310 */                     out.write("\n\t  ");
/*  311 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  312 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  316 */                 if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  317 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                 }
/*      */                 
/*  320 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  321 */                 out.write("\n  </tr>\n  ");
/*      */               }
/*      */               
/*      */ 
/*  325 */               out.write("\n</table>\n");
/*  326 */               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  327 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  331 */           if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  332 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */           }
/*      */           
/*  335 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  336 */           out.write(10);
/*      */         }
/*  338 */         out.write(10);
/*  339 */         out.write("\n<br>\n");
/*  340 */         out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */         
/*      */ 
/*  343 */         String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */         
/*  345 */         out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/*  346 */         out.print(FormatUtil.getString("wizard.disabled"));
/*  347 */         out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/*  348 */         out.print(FormatUtil.getString("am.webclient.admin.heading"));
/*  349 */         out.write("</td>\n  </tr>\n  \n ");
/*      */         
/*  351 */         if (!Constants.sqlManager)
/*      */         {
/*      */ 
/*  354 */           out.write("  \n  <tr>\n\n  ");
/*      */           
/*  356 */           if (request.getParameter("wiz") != null)
/*      */           {
/*  358 */             out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/*  359 */             out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/*  360 */             out.write("\" class='disabledlink'>");
/*  361 */             out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/*  362 */             out.write("</a></td>\n  ");
/*      */           }
/*      */           else
/*      */           {
/*  366 */             out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */             
/*  368 */             ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  369 */             _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  370 */             _jspx_th_c_005fchoose_005f0.setParent(null);
/*  371 */             int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  372 */             if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */               for (;;) {
/*  374 */                 out.write(10);
/*      */                 
/*  376 */                 WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  377 */                 _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  378 */                 _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                 
/*  380 */                 _jspx_th_c_005fwhen_005f0.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/*  381 */                 int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  382 */                 if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                   for (;;) {
/*  384 */                     out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/*  385 */                     out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/*  386 */                     out.write("\n    </a>\n ");
/*  387 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  388 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  392 */                 if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  393 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                 }
/*      */                 
/*  396 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  397 */                 out.write(10);
/*  398 */                 out.write(32);
/*      */                 
/*  400 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  401 */                 _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  402 */                 _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  403 */                 int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  404 */                 if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                   for (;;) {
/*  406 */                     out.write(10);
/*  407 */                     out.write(9);
/*  408 */                     out.write(32);
/*  409 */                     out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/*  410 */                     out.write(10);
/*  411 */                     out.write(32);
/*  412 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  413 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  417 */                 if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  418 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                 }
/*      */                 
/*  421 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  422 */                 out.write(10);
/*  423 */                 out.write(32);
/*  424 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  425 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  429 */             if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  430 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */             }
/*      */             
/*  433 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  434 */             out.write("\n    </td>\n\t");
/*      */           }
/*  436 */           out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */           
/*  438 */           if (request.getParameter("wiz") != null)
/*      */           {
/*  440 */             out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/*  441 */             out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/*  442 */             out.write("\" class='disabledlink'>");
/*  443 */             out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  444 */             out.write("</a></td>\n   ");
/*      */           }
/*      */           else
/*      */           {
/*  448 */             out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */             
/*  450 */             ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  451 */             _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  452 */             _jspx_th_c_005fchoose_005f1.setParent(null);
/*  453 */             int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  454 */             if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */               for (;;) {
/*  456 */                 out.write(10);
/*      */                 
/*  458 */                 WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  459 */                 _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  460 */                 _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                 
/*  462 */                 _jspx_th_c_005fwhen_005f1.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/*  463 */                 int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  464 */                 if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                   for (;;) {
/*  466 */                     out.write("\n   ");
/*  467 */                     out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  468 */                     out.write(10);
/*  469 */                     out.write(32);
/*  470 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  471 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  475 */                 if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  476 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                 }
/*      */                 
/*  479 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  480 */                 out.write(10);
/*  481 */                 out.write(32);
/*      */                 
/*  483 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  484 */                 _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  485 */                 _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  486 */                 int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  487 */                 if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                   for (;;) {
/*  489 */                     out.write(10);
/*  490 */                     String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/*  491 */                     out.write("\n\t \n <a href=\"");
/*  492 */                     out.print(link);
/*  493 */                     out.write("\" class=\"new-left-links\">\n               ");
/*  494 */                     out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  495 */                     out.write("\n    </a>    \n ");
/*  496 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  497 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  501 */                 if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  502 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                 }
/*      */                 
/*  505 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  506 */                 out.write(10);
/*  507 */                 out.write(32);
/*  508 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  509 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  513 */             if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  514 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */             }
/*      */             
/*  517 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  518 */             out.write("\n</td>\n");
/*      */           }
/*  520 */           out.write("\n</tr>\n\n ");
/*      */         }
/*      */         
/*      */ 
/*  524 */         out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */         
/*  526 */         ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  527 */         _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  528 */         _jspx_th_c_005fchoose_005f2.setParent(null);
/*  529 */         int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  530 */         if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */           for (;;) {
/*  532 */             out.write(10);
/*      */             
/*  534 */             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  535 */             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  536 */             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */             
/*  538 */             _jspx_th_c_005fwhen_005f2.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/*  539 */             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  540 */             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */               for (;;) {
/*  542 */                 out.write("\n    \n       ");
/*  543 */                 out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/*  544 */                 out.write(10);
/*  545 */                 out.write(32);
/*  546 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  547 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  551 */             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  552 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */             }
/*      */             
/*  555 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  556 */             out.write(10);
/*  557 */             out.write(32);
/*      */             
/*  559 */             OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  560 */             _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  561 */             _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  562 */             int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  563 */             if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */               for (;;) {
/*  565 */                 out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/*  566 */                 out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/*  567 */                 out.write("\n    </a>\n ");
/*  568 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  569 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  573 */             if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  574 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */             }
/*      */             
/*  577 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  578 */             out.write(10);
/*  579 */             out.write(32);
/*  580 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  581 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  585 */         if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  586 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */         }
/*      */         
/*  589 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  590 */         out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */         
/*  592 */         ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  593 */         _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/*  594 */         _jspx_th_c_005fchoose_005f3.setParent(null);
/*  595 */         int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/*  596 */         if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */           for (;;) {
/*  598 */             out.write(10);
/*      */             
/*  600 */             WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  601 */             _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  602 */             _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */             
/*  604 */             _jspx_th_c_005fwhen_005f3.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/*  605 */             int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  606 */             if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */               for (;;) {
/*  608 */                 out.write("\n    \n       ");
/*  609 */                 out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/*  610 */                 out.write(10);
/*  611 */                 out.write(32);
/*  612 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  613 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  617 */             if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  618 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */             }
/*      */             
/*  621 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  622 */             out.write(10);
/*  623 */             out.write(32);
/*      */             
/*  625 */             OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  626 */             _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  627 */             _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*  628 */             int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  629 */             if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */               for (;;) {
/*  631 */                 out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/*  632 */                 out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/*  633 */                 out.write("\n\t </a>\n ");
/*  634 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/*  635 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  639 */             if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/*  640 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */             }
/*      */             
/*  643 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/*  644 */             out.write(10);
/*  645 */             out.write(32);
/*  646 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/*  647 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  651 */         if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/*  652 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */         }
/*      */         
/*  655 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*  656 */         out.write("\n    </td>\n</tr>  \n\n  ");
/*      */         
/*  658 */         if (!Constants.sqlManager)
/*      */         {
/*      */ 
/*  661 */           out.write(32);
/*  662 */           out.write(32);
/*  663 */           out.write(10);
/*      */           
/*  665 */           ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  666 */           _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  667 */           _jspx_th_c_005fchoose_005f4.setParent(null);
/*  668 */           int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  669 */           if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */             for (;;) {
/*  671 */               out.write(10);
/*      */               
/*  673 */               WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  674 */               _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  675 */               _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */               
/*  677 */               _jspx_th_c_005fwhen_005f4.setTest("${param.method !='showNetworkDiscoveryForm'}");
/*  678 */               int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  679 */               if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                 for (;;) {
/*  681 */                   out.write("\n<tr>\n    ");
/*  682 */                   if (!request.isUserInRole("OPERATOR")) {
/*  683 */                     out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/*  684 */                     out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/*  685 */                     out.write("\n    </a>\n        </td>\n     ");
/*      */                   } else {
/*  687 */                     out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/*  688 */                     out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/*  689 */                     out.write("\n\t</a>\n\t </td>\n\t");
/*      */                   }
/*  691 */                   out.write("\n    </tr>\n ");
/*  692 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  693 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  697 */               if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  698 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */               }
/*      */               
/*  701 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  702 */               out.write(10);
/*  703 */               out.write(32);
/*      */               
/*  705 */               OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  706 */               _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  707 */               _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*  708 */               int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  709 */               if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                 for (;;) {
/*  711 */                   out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/*  712 */                   out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/*  713 */                   out.write("\n\t </td>\n ");
/*  714 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  715 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  719 */               if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  720 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */               }
/*      */               
/*  723 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  724 */               out.write(10);
/*  725 */               out.write(32);
/*  726 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/*  727 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  731 */           if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/*  732 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */           }
/*      */           
/*  735 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*  736 */           out.write("\n \n  ");
/*      */         }
/*      */         
/*      */ 
/*  740 */         out.write("  \n \n ");
/*      */         
/*  742 */         if (!usertype.equals("F"))
/*      */         {
/*  744 */           out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */           
/*  746 */           ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  747 */           _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  748 */           _jspx_th_c_005fchoose_005f5.setParent(null);
/*  749 */           int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  750 */           if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */             for (;;) {
/*  752 */               out.write(10);
/*  753 */               out.write(9);
/*      */               
/*  755 */               WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  756 */               _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/*  757 */               _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */               
/*  759 */               _jspx_th_c_005fwhen_005f5.setTest("${param.method !='maintenanceTaskListView'}");
/*  760 */               int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/*  761 */               if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                 for (;;) {
/*  763 */                   out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/*  764 */                   out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/*  765 */                   out.write("</a>\n  \t");
/*  766 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/*  767 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  771 */               if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/*  772 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */               }
/*      */               
/*  775 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*  776 */               out.write("\n  \t");
/*      */               
/*  778 */               OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  779 */               _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  780 */               _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*  781 */               int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  782 */               if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                 for (;;) {
/*  784 */                   out.write("\n \t\t");
/*  785 */                   out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/*  786 */                   out.write("\n  \t");
/*  787 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  788 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  792 */               if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  793 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */               }
/*      */               
/*  796 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  797 */               out.write("\n  \t");
/*  798 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  799 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  803 */           if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  804 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */           }
/*      */           
/*  807 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  808 */           out.write("\n     </td>\n </tr>   \n \n ");
/*      */           
/*  810 */           if (!Constants.sqlManager)
/*      */           {
/*      */ 
/*  813 */             out.write(32);
/*  814 */             out.write(32);
/*  815 */             out.write(10);
/*      */             
/*  817 */             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  818 */             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  819 */             _jspx_th_c_005fif_005f0.setParent(null);
/*      */             
/*  821 */             _jspx_th_c_005fif_005f0.setTest("${category!='LAMP'}");
/*  822 */             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  823 */             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */               for (;;) {
/*  825 */                 out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                 
/*  827 */                 ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  828 */                 _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/*  829 */                 _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_c_005fif_005f0);
/*  830 */                 int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/*  831 */                 if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                   for (;;) {
/*  833 */                     out.write(10);
/*  834 */                     out.write(32);
/*  835 */                     out.write(9);
/*      */                     
/*  837 */                     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  838 */                     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/*  839 */                     _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                     
/*  841 */                     _jspx_th_c_005fwhen_005f6.setTest("${param.method !='listTrapListener'}");
/*  842 */                     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/*  843 */                     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                       for (;;) {
/*  845 */                         out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/*  846 */                         out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/*  847 */                         out.write("</a>\n   \t");
/*  848 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/*  849 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  853 */                     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/*  854 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                     }
/*      */                     
/*  857 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/*  858 */                     out.write("\n   \t");
/*      */                     
/*  860 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  861 */                     _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/*  862 */                     _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*  863 */                     int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/*  864 */                     if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                       for (;;) {
/*  866 */                         out.write("\n  \t\t");
/*  867 */                         out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/*  868 */                         out.write(" \n   \t");
/*  869 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/*  870 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  874 */                     if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/*  875 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                     }
/*      */                     
/*  878 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/*  879 */                     out.write("\n   \t");
/*  880 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/*  881 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  885 */                 if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/*  886 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                 }
/*      */                 
/*  889 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/*  890 */                 out.write("\n      </td>\n  </tr>   \n");
/*  891 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  892 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  896 */             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  897 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */             }
/*      */             
/*  900 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  901 */             out.write(10);
/*  902 */             out.write(32);
/*      */           }
/*      */           
/*      */ 
/*  906 */           out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */           
/*  908 */           ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  909 */           _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/*  910 */           _jspx_th_c_005fchoose_005f7.setParent(null);
/*  911 */           int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/*  912 */           if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */             for (;;) {
/*  914 */               out.write(10);
/*      */               
/*  916 */               WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  917 */               _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/*  918 */               _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */               
/*  920 */               _jspx_th_c_005fwhen_005f7.setTest("${param.method =='showScheduleReports'}");
/*  921 */               int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/*  922 */               if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                 for (;;) {
/*  924 */                   out.write("\n       ");
/*  925 */                   out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  926 */                   out.write(10);
/*  927 */                   out.write(32);
/*  928 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/*  929 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  933 */               if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/*  934 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */               }
/*      */               
/*  937 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/*  938 */               out.write(10);
/*  939 */               out.write(32);
/*      */               
/*  941 */               OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  942 */               _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/*  943 */               _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*  944 */               int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/*  945 */               if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                 for (;;) {
/*  947 */                   out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/*  948 */                   out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  949 */                   out.write("\n\t </a>\n ");
/*  950 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/*  951 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  955 */               if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/*  956 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */               }
/*      */               
/*  959 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/*  960 */               out.write(10);
/*  961 */               out.write(32);
/*  962 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/*  963 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  967 */           if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/*  968 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */           }
/*      */           
/*  971 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/*  972 */           out.write("\n    </td>\n</tr> \n");
/*      */         } else {
/*  974 */           out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/*  975 */           out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/*  976 */           out.write("</a>\n     </td>\n </tr>   \n");
/*      */           
/*  978 */           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  979 */           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  980 */           _jspx_th_c_005fif_005f1.setParent(null);
/*      */           
/*  982 */           _jspx_th_c_005fif_005f1.setTest("${category!='LAMP'}");
/*  983 */           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  984 */           if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */             for (;;) {
/*  986 */               out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/*  987 */               out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/*  988 */               out.write("</a>\n\t  </td>\n  </tr>   \n");
/*  989 */               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  990 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  994 */           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  995 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */           }
/*      */           
/*  998 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  999 */           out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 1000 */           out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1001 */           out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */         }
/* 1003 */         out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */         
/* 1005 */         ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1006 */         _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 1007 */         _jspx_th_c_005fchoose_005f8.setParent(null);
/* 1008 */         int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 1009 */         if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */           for (;;) {
/* 1011 */             out.write(10);
/*      */             
/* 1013 */             WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1014 */             _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 1015 */             _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */             
/* 1017 */             _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 1018 */             int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 1019 */             if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */               for (;;) {
/* 1021 */                 out.write("\n        ");
/* 1022 */                 out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 1023 */                 out.write(10);
/* 1024 */                 out.write(32);
/* 1025 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 1026 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1030 */             if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 1031 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */             }
/*      */             
/* 1034 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 1035 */             out.write(10);
/* 1036 */             out.write(32);
/*      */             
/* 1038 */             OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1039 */             _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 1040 */             _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 1041 */             int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 1042 */             if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */               for (;;) {
/* 1044 */                 out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 1045 */                 out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 1046 */                 out.write("\n\t </a>\n ");
/* 1047 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 1048 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1052 */             if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 1053 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */             }
/*      */             
/* 1056 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 1057 */             out.write(10);
/* 1058 */             out.write(32);
/* 1059 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 1060 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1064 */         if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 1065 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */         }
/*      */         
/* 1068 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 1069 */         out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */         
/* 1071 */         ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1072 */         _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 1073 */         _jspx_th_c_005fchoose_005f9.setParent(null);
/* 1074 */         int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 1075 */         if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */           for (;;) {
/* 1077 */             out.write(10);
/*      */             
/* 1079 */             WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1080 */             _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 1081 */             _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */             
/* 1083 */             _jspx_th_c_005fwhen_005f9.setTest("${param.method!='showMailServerConfiguration'}");
/* 1084 */             int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 1085 */             if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */               for (;;) {
/* 1087 */                 out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 1088 */                 out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1089 */                 out.write("\n    </a>    \n ");
/* 1090 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 1091 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1095 */             if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 1096 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */             }
/*      */             
/* 1099 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 1100 */             out.write(10);
/* 1101 */             out.write(32);
/*      */             
/* 1103 */             OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1104 */             _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 1105 */             _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 1106 */             int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 1107 */             if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */               for (;;) {
/* 1109 */                 out.write(10);
/* 1110 */                 out.write(9);
/* 1111 */                 out.write(32);
/* 1112 */                 out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1113 */                 out.write(10);
/* 1114 */                 out.write(32);
/* 1115 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 1116 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1120 */             if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 1121 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */             }
/*      */             
/* 1124 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 1125 */             out.write(10);
/* 1126 */             out.write(32);
/* 1127 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 1128 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1132 */         if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 1133 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */         }
/*      */         
/* 1136 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 1137 */         out.write("\n    </td>\n</tr>\n\n\n");
/* 1138 */         if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 1139 */           out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */           
/* 1141 */           ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1142 */           _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 1143 */           _jspx_th_c_005fchoose_005f10.setParent(null);
/* 1144 */           int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 1145 */           if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */             for (;;) {
/* 1147 */               out.write(10);
/*      */               
/* 1149 */               WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1150 */               _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 1151 */               _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */               
/* 1153 */               _jspx_th_c_005fwhen_005f10.setTest("${param.method!='SMSServerConfiguration'}");
/* 1154 */               int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 1155 */               if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                 for (;;) {
/* 1157 */                   out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 1158 */                   out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 1159 */                   out.write("\n    </a>\n ");
/* 1160 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 1161 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1165 */               if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 1166 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */               }
/*      */               
/* 1169 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 1170 */               out.write(10);
/* 1171 */               out.write(32);
/*      */               
/* 1173 */               OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1174 */               _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 1175 */               _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 1176 */               int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 1177 */               if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                 for (;;) {
/* 1179 */                   out.write("\n         ");
/* 1180 */                   out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 1181 */                   out.write(10);
/* 1182 */                   out.write(32);
/* 1183 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 1184 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1188 */               if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 1189 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */               }
/*      */               
/* 1192 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 1193 */               out.write(10);
/* 1194 */               out.write(32);
/* 1195 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 1196 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1200 */           if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 1201 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */           }
/*      */           
/* 1204 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 1205 */           out.write("\n    </td>\n</tr>\n");
/*      */         }
/* 1207 */         out.write("\n\n\n ");
/*      */         
/* 1209 */         if (!Constants.sqlManager)
/*      */         {
/*      */ 
/* 1212 */           out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */           
/* 1214 */           ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1215 */           _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 1216 */           _jspx_th_c_005fchoose_005f11.setParent(null);
/* 1217 */           int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 1218 */           if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */             for (;;) {
/* 1220 */               out.write(10);
/*      */               
/* 1222 */               WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1223 */               _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 1224 */               _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */               
/* 1226 */               _jspx_th_c_005fwhen_005f11.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 1227 */               int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 1228 */               if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                 for (;;) {
/* 1230 */                   out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 1231 */                   out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 1232 */                   out.write("\n    </a>\n ");
/* 1233 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 1234 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1238 */               if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 1239 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */               }
/*      */               
/* 1242 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 1243 */               out.write(10);
/* 1244 */               out.write(32);
/*      */               
/* 1246 */               OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1247 */               _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 1248 */               _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 1249 */               int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 1250 */               if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                 for (;;) {
/* 1252 */                   out.write(10);
/* 1253 */                   out.write(9);
/* 1254 */                   out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 1255 */                   out.write(10);
/* 1256 */                   out.write(32);
/* 1257 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 1258 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1262 */               if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 1263 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */               }
/*      */               
/* 1266 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 1267 */               out.write(10);
/* 1268 */               out.write(32);
/* 1269 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 1270 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1274 */           if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 1275 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */           }
/*      */           
/* 1278 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 1279 */           out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */           
/* 1281 */           ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1282 */           _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 1283 */           _jspx_th_c_005fchoose_005f12.setParent(null);
/* 1284 */           int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 1285 */           if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */             for (;;) {
/* 1287 */               out.write(10);
/*      */               
/* 1289 */               WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1290 */               _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 1291 */               _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */               
/* 1293 */               _jspx_th_c_005fwhen_005f12.setTest("${uri !='/Upload.do'}");
/* 1294 */               int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 1295 */               if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                 for (;;) {
/* 1297 */                   out.write("   \n        ");
/*      */                   
/* 1299 */                   AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1300 */                   _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 1301 */                   _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f12);
/*      */                   
/* 1303 */                   _jspx_th_am_005fadminlink_005f2.setHref("/Upload.do");
/*      */                   
/* 1305 */                   _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 1306 */                   int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 1307 */                   if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 1308 */                     if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 1309 */                       out = _jspx_page_context.pushBody();
/* 1310 */                       _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 1311 */                       _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 1314 */                       out.write("\n           ");
/* 1315 */                       out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 1316 */                       out.write("\n            ");
/* 1317 */                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 1318 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1321 */                     if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 1322 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1325 */                   if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 1326 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                   }
/*      */                   
/* 1329 */                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 1330 */                   out.write(10);
/* 1331 */                   out.write(10);
/* 1332 */                   out.write(32);
/* 1333 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 1334 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1338 */               if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 1339 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */               }
/*      */               
/* 1342 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 1343 */               out.write(10);
/* 1344 */               out.write(32);
/*      */               
/* 1346 */               OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1347 */               _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 1348 */               _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 1349 */               int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 1350 */               if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                 for (;;) {
/* 1352 */                   out.write(10);
/* 1353 */                   out.write(9);
/* 1354 */                   out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 1355 */                   out.write(10);
/* 1356 */                   out.write(32);
/* 1357 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 1358 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1362 */               if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 1363 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */               }
/*      */               
/* 1366 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 1367 */               out.write(10);
/* 1368 */               out.write(32);
/* 1369 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 1370 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1374 */           if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 1375 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */           }
/*      */           
/* 1378 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 1379 */           out.write("\n    </td>\n</tr>\n \n ");
/*      */         }
/*      */         
/*      */ 
/* 1383 */         out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */         
/* 1385 */         ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1386 */         _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 1387 */         _jspx_th_c_005fchoose_005f13.setParent(null);
/* 1388 */         int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 1389 */         if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */           for (;;) {
/* 1391 */             out.write(10);
/*      */             
/* 1393 */             WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1394 */             _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 1395 */             _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */             
/* 1397 */             _jspx_th_c_005fwhen_005f13.setTest("${uri !='/admin/userconfiguration.do'}");
/* 1398 */             int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 1399 */             if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */               for (;;) {
/* 1401 */                 out.write("\n    \n        ");
/*      */                 
/* 1403 */                 AdminLink _jspx_th_am_005fadminlink_005f3 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1404 */                 _jspx_th_am_005fadminlink_005f3.setPageContext(_jspx_page_context);
/* 1405 */                 _jspx_th_am_005fadminlink_005f3.setParent(_jspx_th_c_005fwhen_005f13);
/*      */                 
/* 1407 */                 _jspx_th_am_005fadminlink_005f3.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                 
/* 1409 */                 _jspx_th_am_005fadminlink_005f3.setEnableClass("new-left-links");
/* 1410 */                 int _jspx_eval_am_005fadminlink_005f3 = _jspx_th_am_005fadminlink_005f3.doStartTag();
/* 1411 */                 if (_jspx_eval_am_005fadminlink_005f3 != 0) {
/* 1412 */                   if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/* 1413 */                     out = _jspx_page_context.pushBody();
/* 1414 */                     _jspx_th_am_005fadminlink_005f3.setBodyContent((BodyContent)out);
/* 1415 */                     _jspx_th_am_005fadminlink_005f3.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1418 */                     out.write("\n       ");
/* 1419 */                     out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 1420 */                     out.write("\n        ");
/* 1421 */                     int evalDoAfterBody = _jspx_th_am_005fadminlink_005f3.doAfterBody();
/* 1422 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1425 */                   if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/* 1426 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1429 */                 if (_jspx_th_am_005fadminlink_005f3.doEndTag() == 5) {
/* 1430 */                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f3); return;
/*      */                 }
/*      */                 
/* 1433 */                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f3);
/* 1434 */                 out.write(10);
/* 1435 */                 out.write(10);
/* 1436 */                 out.write(32);
/* 1437 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 1438 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1442 */             if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 1443 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */             }
/*      */             
/* 1446 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 1447 */             out.write(10);
/* 1448 */             out.write(32);
/*      */             
/* 1450 */             OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1451 */             _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 1452 */             _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 1453 */             int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 1454 */             if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */               for (;;) {
/* 1456 */                 out.write(10);
/* 1457 */                 out.write(9);
/* 1458 */                 out.write(32);
/* 1459 */                 out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 1460 */                 out.write(10);
/* 1461 */                 out.write(32);
/* 1462 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 1463 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1467 */             if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 1468 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */             }
/*      */             
/* 1471 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 1472 */             out.write(10);
/* 1473 */             out.write(32);
/* 1474 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 1475 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1479 */         if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 1480 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */         }
/*      */         
/* 1483 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 1484 */         out.write("\n    </td>\n</tr>\n   \n\n ");
/* 1485 */         if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 1486 */           out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */           
/* 1488 */           ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1489 */           _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 1490 */           _jspx_th_c_005fchoose_005f14.setParent(null);
/* 1491 */           int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 1492 */           if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */             for (;;) {
/* 1494 */               out.write("\n   ");
/*      */               
/* 1496 */               WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1497 */               _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 1498 */               _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */               
/* 1500 */               _jspx_th_c_005fwhen_005f14.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 1501 */               int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 1502 */               if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                 for (;;) {
/* 1504 */                   out.write("\n    ");
/*      */                   
/* 1506 */                   AdminLink _jspx_th_am_005fadminlink_005f4 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1507 */                   _jspx_th_am_005fadminlink_005f4.setPageContext(_jspx_page_context);
/* 1508 */                   _jspx_th_am_005fadminlink_005f4.setParent(_jspx_th_c_005fwhen_005f14);
/*      */                   
/* 1510 */                   _jspx_th_am_005fadminlink_005f4.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                   
/* 1512 */                   _jspx_th_am_005fadminlink_005f4.setEnableClass("new-left-links");
/* 1513 */                   int _jspx_eval_am_005fadminlink_005f4 = _jspx_th_am_005fadminlink_005f4.doStartTag();
/* 1514 */                   if (_jspx_eval_am_005fadminlink_005f4 != 0) {
/* 1515 */                     if (_jspx_eval_am_005fadminlink_005f4 != 1) {
/* 1516 */                       out = _jspx_page_context.pushBody();
/* 1517 */                       _jspx_th_am_005fadminlink_005f4.setBodyContent((BodyContent)out);
/* 1518 */                       _jspx_th_am_005fadminlink_005f4.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 1521 */                       out.write(10);
/* 1522 */                       out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 1523 */                       out.write("\n    ");
/* 1524 */                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f4.doAfterBody();
/* 1525 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1528 */                     if (_jspx_eval_am_005fadminlink_005f4 != 1) {
/* 1529 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1532 */                   if (_jspx_th_am_005fadminlink_005f4.doEndTag() == 5) {
/* 1533 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f4); return;
/*      */                   }
/*      */                   
/* 1536 */                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f4);
/* 1537 */                   out.write("\n   ");
/* 1538 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 1539 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1543 */               if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 1544 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */               }
/*      */               
/* 1547 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 1548 */               out.write("\n   ");
/*      */               
/* 1550 */               OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1551 */               _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 1552 */               _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 1553 */               int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 1554 */               if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                 for (;;) {
/* 1556 */                   out.write(10);
/* 1557 */                   out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 1558 */                   out.write("\n   ");
/* 1559 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 1560 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1564 */               if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 1565 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */               }
/*      */               
/* 1568 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 1569 */               out.write(10);
/* 1570 */               out.write(32);
/* 1571 */               out.write(32);
/* 1572 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 1573 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1577 */           if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 1578 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */           }
/*      */           
/* 1581 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 1582 */           out.write("\n </td>\n</tr>\n  ");
/*      */         }
/* 1584 */         out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */         
/* 1586 */         ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1587 */         _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 1588 */         _jspx_th_c_005fchoose_005f15.setParent(null);
/* 1589 */         int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 1590 */         if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */           for (;;) {
/* 1592 */             out.write("\n   ");
/*      */             
/* 1594 */             WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1595 */             _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 1596 */             _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */             
/* 1598 */             _jspx_th_c_005fwhen_005f15.setTest("${param.method!='showDataCleanUp'}");
/* 1599 */             int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 1600 */             if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */               for (;;) {
/* 1602 */                 out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 1603 */                 out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1604 */                 out.write("\n    </a>\n   ");
/* 1605 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 1606 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1610 */             if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 1611 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */             }
/*      */             
/* 1614 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 1615 */             out.write("\n   ");
/*      */             
/* 1617 */             OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1618 */             _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 1619 */             _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 1620 */             int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 1621 */             if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */               for (;;) {
/* 1623 */                 out.write(10);
/* 1624 */                 out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1625 */                 out.write("\n   ");
/* 1626 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 1627 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1631 */             if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 1632 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */             }
/*      */             
/* 1635 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 1636 */             out.write(10);
/* 1637 */             out.write(32);
/* 1638 */             out.write(32);
/* 1639 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 1640 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1644 */         if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 1645 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */         }
/*      */         
/* 1648 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 1649 */         out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 1650 */         out.write("\n\n\n<br>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 1651 */         out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 1652 */         out.write("</td>\n    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 1653 */         if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */           return;
/* 1655 */         out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n\n  </tr>\n  ");
/*      */         
/* 1657 */         IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1658 */         _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 1659 */         _jspx_th_c_005fif_005f2.setParent(null);
/*      */         
/* 1661 */         _jspx_th_c_005fif_005f2.setTest("${!empty param.addmonitors}");
/* 1662 */         int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 1663 */         if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */           for (;;) {
/* 1665 */             out.write(" \n  \n  <tr>\n    <td colspan=\"2\" class=\"quicknote\">");
/* 1666 */             out.print(FormatUtil.getString("am.webclient.hostdiscovery.leftlink.quicknote1", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }));
/* 1667 */             out.write("\n      </td>\n  </tr>\n  <tr> \n    <td colspan=\"2\" align=\"right\" class=\"quicknote\"> <a href=\"/help/managing-business-applications/monitor-server-application.html\" target=\"_blank\" class=\"selectedmenu\"><img src=\"/images/icon_quicknote_help.gif\" hspace=\"5\" vspace=\"5\" border=\"0\" align=\"absmiddle\">");
/* 1668 */             out.print(FormatUtil.getString("am.webclient.hometab.availabilitycheck.helpmessage"));
/* 1669 */             out.write("</a> </td>\n  </tr>\n  ");
/* 1670 */             int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 1671 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1675 */         if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 1676 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */         }
/*      */         
/* 1679 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1680 */         out.write(32);
/*      */         
/* 1682 */         IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1683 */         _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 1684 */         _jspx_th_c_005fif_005f3.setParent(null);
/*      */         
/* 1686 */         _jspx_th_c_005fif_005f3.setTest("${empty param.addmonitors}");
/* 1687 */         int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 1688 */         if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */           for (;;) {
/* 1690 */             out.write(" \n  <tr> \n    <td colspan=\"2\" class=\"quicknote\"> ");
/* 1691 */             out.print(FormatUtil.getString("am.webclient.hostdiscovery.leftlink.quicknote2"));
/* 1692 */             out.write("</td>\n  </tr>\n  ");
/* 1693 */             int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 1694 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1698 */         if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 1699 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */         }
/*      */         
/* 1702 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1703 */         out.write(" \n</table>\n");
/*      */       }
/*      */     } catch (Throwable t) {
/* 1706 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1707 */         out = _jspx_out;
/* 1708 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1709 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 1710 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1713 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1719 */     PageContext pageContext = _jspx_page_context;
/* 1720 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1722 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1723 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1724 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 1726 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 1728 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 1729 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1730 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1731 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1732 */       return true;
/*      */     }
/* 1734 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1735 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\DiscoveryLeftLinks_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */