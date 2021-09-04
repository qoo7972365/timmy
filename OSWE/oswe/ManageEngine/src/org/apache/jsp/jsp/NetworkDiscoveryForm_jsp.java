/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class NetworkDiscoveryForm_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   25 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   31 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(3);
/*   32 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*   33 */     _jspx_dependants.put("/jsp/includes/DiscoveryLeftLinks.jspf", Long.valueOf(1473429417000L));
/*   34 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fonsubmit_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   56 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   60 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fonsubmit_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   73 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   74 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   75 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   79 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*   80 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*   81 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*   82 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   83 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*   84 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*   85 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   86 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   87 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   88 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   89 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   90 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*   91 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fonsubmit_005faction.release();
/*   92 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   99 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  102 */     JspWriter out = null;
/*  103 */     Object page = this;
/*  104 */     JspWriter _jspx_out = null;
/*  105 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  109 */       response.setContentType("text/html; charset=UTF-8");
/*  110 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  112 */       _jspx_page_context = pageContext;
/*  113 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  114 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  115 */       session = pageContext.getSession();
/*  116 */       out = pageContext.getOut();
/*  117 */       _jspx_out = out;
/*      */       
/*  119 */       out.write("\n<!DOCTYPE html>\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n\n\n\n\n\n\n\n\n");
/*      */       
/*  121 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/*  122 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/*  123 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/*  125 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/*  126 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/*  127 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/*  129 */           out.write(10);
/*  130 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/*  132 */           out.write(10);
/*  133 */           out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  134 */           out.write(10);
/*  135 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/*  137 */           out.write(10);
/*      */           
/*  139 */           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  140 */           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/*  141 */           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/*  143 */           _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */           
/*  145 */           _jspx_th_tiles_005fput_005f2.setType("string");
/*  146 */           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/*  147 */           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/*  148 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/*  149 */               out = _jspx_page_context.pushBody();
/*  150 */               _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/*  151 */               _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/*  154 */               out.write(10);
/*  155 */               out.write("<!--$Id$-->\n\n\n\n\n\n\n\n<script>\nfunction fnalert()\n{\n  alertUser();\n  return;\n}\nfunction Call()\n{\n alert(\"");
/*  156 */               out.print(FormatUtil.getString("am.webclient.discoverylinks.alert"));
/*  157 */               out.write("\");\n}\n</script>\n");
/*  158 */               if ((!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (!com.adventnet.appmanager.util.Constants.isIt360)) {
/*  159 */                 out.write(10);
/*      */                 
/*  161 */                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  162 */                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  163 */                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                 
/*  165 */                 _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/*  166 */                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  167 */                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                   for (;;) {
/*  169 */                     out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"leftmnutables\">\n  <tr> \n    <td height=\"19\" class=\"leftlinksheading\">");
/*  170 */                     out.print(FormatUtil.getString("am.webclient.discoverylinks.heading.text"));
/*  171 */                     out.write("</td>\n  </tr>\n  <tr> \n    <!--td height=\"19\" class=\"leftlinkstd\"><a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999\" class=\"new-left-links\">New Monitor</a></td-->\n   ");
/*      */                     
/*  173 */                     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  174 */                     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  175 */                     _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                     
/*  177 */                     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/*  178 */                     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  179 */                     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                       for (;;) {
/*  181 */                         out.write("\n    ");
/*      */                         
/*  183 */                         if (request.getParameter("wiz") != null)
/*      */                         {
/*  185 */                           out.write("\n\t  <td height=\"19\" class=\"leftlinkstd\"><a href='#' onclick=\"javascript:Call()\" title=\"Disabled in the wizard\" class='disabledlink'>");
/*  186 */                           out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/*  187 */                           out.write("</a></td>\n\t");
/*      */                         }
/*      */                         else
/*      */                         {
/*  191 */                           out.write("\n    <td height=\"19\"  class=\"leftlinkstd\" > <a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=UrlMonitor\" class=\"new-left-links\">");
/*  192 */                           out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/*  193 */                           out.write("</a></td>\n\t");
/*      */                         }
/*  195 */                         out.write("\n    ");
/*  196 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/*  197 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  201 */                     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/*  202 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                     }
/*      */                     
/*  205 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*  206 */                     out.write("\n    ");
/*      */                     
/*  208 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  209 */                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  210 */                     _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                     
/*  212 */                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/*  213 */                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  214 */                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                       for (;;) {
/*  216 */                         out.write(32);
/*  217 */                         out.write(10);
/*  218 */                         out.write(9);
/*      */                         
/*  220 */                         if (request.getParameter("wiz") != null)
/*      */                         {
/*  222 */                           out.write("\n     <td height=\"19\" class=\"leftlinkstd\"><a href='#' onclick=\"javascript:Call()\" title=\"Disabled in the wizard\" class='disabledlink'>");
/*  223 */                           out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/*  224 */                           out.write("</a></td>\n\t");
/*      */                         }
/*      */                         else
/*      */                         {
/*  228 */                           out.write("\n    <td height=\"19\"  class=\"leftlinkstd\" > ");
/*      */                           
/*  230 */                           AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  231 */                           _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/*  232 */                           _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                           
/*  234 */                           _jspx_th_am_005fadminlink_005f0.setHref("/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999");
/*      */                           
/*  236 */                           _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/*  237 */                           int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/*  238 */                           if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/*  239 */                             if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/*  240 */                               out = _jspx_page_context.pushBody();
/*  241 */                               _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/*  242 */                               _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  245 */                               out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/*  246 */                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/*  247 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  250 */                             if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/*  251 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  254 */                           if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/*  255 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                           }
/*      */                           
/*  258 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/*  259 */                           out.write("</td>");
/*  260 */                           out.write(32);
/*  261 */                           out.write(10);
/*  262 */                           out.write(9);
/*      */                         }
/*  264 */                         out.write(10);
/*  265 */                         out.write(9);
/*  266 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  267 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  271 */                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  272 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                     }
/*      */                     
/*  275 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  276 */                     out.write("\n    \n    \n  </tr>\n  ");
/*      */                     
/*  278 */                     com.adventnet.appmanager.server.framework.FreeEditionDetails license = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails();
/*  279 */                     String licensetype = license.getUserType();
/*  280 */                     pageContext.setAttribute("licensetype", licensetype);
/*  281 */                     if (!licensetype.equals("F"))
/*      */                     {
/*      */ 
/*  284 */                       out.write("\n  <tr> \n   ");
/*      */                       
/*  286 */                       PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  287 */                       _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/*  288 */                       _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                       
/*  290 */                       _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/*  291 */                       int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/*  292 */                       if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                         for (;;) {
/*  294 */                           out.write("\n   <td width=\"95%\" height=\"19\" class=\"leftlinkstd\"><a href=\"javascript:fnalert()\" class=\"new-left-links\"> \n      ");
/*  295 */                           out.print(FormatUtil.getString("am.webclient.discoverylinks.network.text"));
/*  296 */                           out.write("</a></td>\n   ");
/*  297 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/*  298 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  302 */                       if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/*  303 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                       }
/*      */                       
/*  306 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/*  307 */                       out.write("\n   ");
/*      */                       
/*  309 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  310 */                       _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  311 */                       _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                       
/*  313 */                       _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/*  314 */                       int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  315 */                       if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                         for (;;) {
/*  317 */                           out.write("\n    ");
/*      */                           
/*  319 */                           if (!com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*      */                           {
/*      */ 
/*  322 */                             out.write("\n    <td width=\"95%\" height=\"19\" class=\"leftlinkstd\">");
/*      */                             
/*  324 */                             AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  325 */                             _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/*  326 */                             _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                             
/*  328 */                             _jspx_th_am_005fadminlink_005f1.setHref("/adminAction.do?method=showNetworkDiscoveryForm");
/*      */                             
/*  330 */                             _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/*  331 */                             int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/*  332 */                             if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/*  333 */                               if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/*  334 */                                 out = _jspx_page_context.pushBody();
/*  335 */                                 _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/*  336 */                                 _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/*  339 */                                 out.write(" \n      ");
/*  340 */                                 out.print(FormatUtil.getString("am.webclient.discoverylinks.network.text"));
/*  341 */                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/*  342 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*  345 */                               if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/*  346 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/*  349 */                             if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/*  350 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                             }
/*      */                             
/*  353 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/*  354 */                             out.write(" </td>\n      ");
/*      */                           }
/*      */                           
/*      */ 
/*  358 */                           out.write("\n\t  ");
/*  359 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  360 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  364 */                       if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  365 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                       }
/*      */                       
/*  368 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  369 */                       out.write("\n  </tr>\n  ");
/*      */                     }
/*      */                     
/*      */ 
/*  373 */                     out.write("\n</table>\n");
/*  374 */                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  375 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  379 */                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  380 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                 }
/*      */                 
/*  383 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  384 */                 out.write(10);
/*      */               }
/*  386 */               out.write(10);
/*  387 */               out.write("\n    <br>\n    ");
/*  388 */               out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */               
/*      */ 
/*  391 */               String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */               
/*  393 */               out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/*  394 */               out.print(FormatUtil.getString("wizard.disabled"));
/*  395 */               out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/*  396 */               out.print(FormatUtil.getString("am.webclient.admin.heading"));
/*  397 */               out.write("</td>\n  </tr>\n  \n ");
/*      */               
/*  399 */               if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */               {
/*      */ 
/*  402 */                 out.write("  \n  <tr>\n\n  ");
/*      */                 
/*  404 */                 if (request.getParameter("wiz") != null)
/*      */                 {
/*  406 */                   out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/*  407 */                   out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/*  408 */                   out.write("\" class='disabledlink'>");
/*  409 */                   out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/*  410 */                   out.write("</a></td>\n  ");
/*      */                 }
/*      */                 else
/*      */                 {
/*  414 */                   out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                   
/*  416 */                   ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  417 */                   _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  418 */                   _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*  419 */                   int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  420 */                   if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                     for (;;) {
/*  422 */                       out.write(10);
/*      */                       
/*  424 */                       WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  425 */                       _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  426 */                       _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/*  428 */                       _jspx_th_c_005fwhen_005f0.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/*  429 */                       int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  430 */                       if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                         for (;;) {
/*  432 */                           out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/*  433 */                           out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/*  434 */                           out.write("\n    </a>\n ");
/*  435 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  436 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  440 */                       if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  441 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                       }
/*      */                       
/*  444 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  445 */                       out.write(10);
/*  446 */                       out.write(32);
/*      */                       
/*  448 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  449 */                       _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  450 */                       _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  451 */                       int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  452 */                       if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                         for (;;) {
/*  454 */                           out.write(10);
/*  455 */                           out.write(9);
/*  456 */                           out.write(32);
/*  457 */                           out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/*  458 */                           out.write(10);
/*  459 */                           out.write(32);
/*  460 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  461 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  465 */                       if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  466 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                       }
/*      */                       
/*  469 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  470 */                       out.write(10);
/*  471 */                       out.write(32);
/*  472 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  473 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  477 */                   if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  478 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                   }
/*      */                   
/*  481 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  482 */                   out.write("\n    </td>\n\t");
/*      */                 }
/*  484 */                 out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                 
/*  486 */                 if (request.getParameter("wiz") != null)
/*      */                 {
/*  488 */                   out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/*  489 */                   out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/*  490 */                   out.write("\" class='disabledlink'>");
/*  491 */                   out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  492 */                   out.write("</a></td>\n   ");
/*      */                 }
/*      */                 else
/*      */                 {
/*  496 */                   out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                   
/*  498 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  499 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  500 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*  501 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  502 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/*  504 */                       out.write(10);
/*      */                       
/*  506 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  507 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  508 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/*  510 */                       _jspx_th_c_005fwhen_005f1.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/*  511 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  512 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/*  514 */                           out.write("\n   ");
/*  515 */                           out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  516 */                           out.write(10);
/*  517 */                           out.write(32);
/*  518 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  519 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  523 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  524 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/*  527 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  528 */                       out.write(10);
/*  529 */                       out.write(32);
/*      */                       
/*  531 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  532 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  533 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  534 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  535 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/*  537 */                           out.write(10);
/*  538 */                           String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/*  539 */                           out.write("\n\t \n <a href=\"");
/*  540 */                           out.print(link);
/*  541 */                           out.write("\" class=\"new-left-links\">\n               ");
/*  542 */                           out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/*  543 */                           out.write("\n    </a>    \n ");
/*  544 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  545 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  549 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  550 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/*  553 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  554 */                       out.write(10);
/*  555 */                       out.write(32);
/*  556 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  557 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  561 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  562 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/*  565 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  566 */                   out.write("\n</td>\n");
/*      */                 }
/*  568 */                 out.write("\n</tr>\n\n ");
/*      */               }
/*      */               
/*      */ 
/*  572 */               out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */               
/*  574 */               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  575 */               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  576 */               _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*  577 */               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  578 */               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                 for (;;) {
/*  580 */                   out.write(10);
/*      */                   
/*  582 */                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  583 */                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  584 */                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                   
/*  586 */                   _jspx_th_c_005fwhen_005f2.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/*  587 */                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  588 */                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                     for (;;) {
/*  590 */                       out.write("\n    \n       ");
/*  591 */                       out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/*  592 */                       out.write(10);
/*  593 */                       out.write(32);
/*  594 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  595 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  599 */                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  600 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                   }
/*      */                   
/*  603 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  604 */                   out.write(10);
/*  605 */                   out.write(32);
/*      */                   
/*  607 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  608 */                   _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  609 */                   _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  610 */                   int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  611 */                   if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                     for (;;) {
/*  613 */                       out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/*  614 */                       out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/*  615 */                       out.write("\n    </a>\n ");
/*  616 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  617 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  621 */                   if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  622 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                   }
/*      */                   
/*  625 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  626 */                   out.write(10);
/*  627 */                   out.write(32);
/*  628 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  629 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  633 */               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  634 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */               }
/*      */               
/*  637 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  638 */               out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */               
/*  640 */               ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  641 */               _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/*  642 */               _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*  643 */               int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/*  644 */               if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                 for (;;) {
/*  646 */                   out.write(10);
/*      */                   
/*  648 */                   WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  649 */                   _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  650 */                   _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                   
/*  652 */                   _jspx_th_c_005fwhen_005f3.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/*  653 */                   int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  654 */                   if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                     for (;;) {
/*  656 */                       out.write("\n    \n       ");
/*  657 */                       out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/*  658 */                       out.write(10);
/*  659 */                       out.write(32);
/*  660 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  661 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  665 */                   if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  666 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                   }
/*      */                   
/*  669 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  670 */                   out.write(10);
/*  671 */                   out.write(32);
/*      */                   
/*  673 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  674 */                   _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  675 */                   _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*  676 */                   int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  677 */                   if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                     for (;;) {
/*  679 */                       out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/*  680 */                       out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/*  681 */                       out.write("\n\t </a>\n ");
/*  682 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/*  683 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  687 */                   if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/*  688 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                   }
/*      */                   
/*  691 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/*  692 */                   out.write(10);
/*  693 */                   out.write(32);
/*  694 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/*  695 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  699 */               if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/*  700 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */               }
/*      */               
/*  703 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*  704 */               out.write("\n    </td>\n</tr>  \n\n  ");
/*      */               
/*  706 */               if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */               {
/*      */ 
/*  709 */                 out.write(32);
/*  710 */                 out.write(32);
/*  711 */                 out.write(10);
/*      */                 
/*  713 */                 ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  714 */                 _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  715 */                 _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/*  716 */                 int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  717 */                 if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                   for (;;) {
/*  719 */                     out.write(10);
/*      */                     
/*  721 */                     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  722 */                     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  723 */                     _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                     
/*  725 */                     _jspx_th_c_005fwhen_005f4.setTest("${param.method !='showNetworkDiscoveryForm'}");
/*  726 */                     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  727 */                     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                       for (;;) {
/*  729 */                         out.write("\n<tr>\n    ");
/*  730 */                         if (!request.isUserInRole("OPERATOR")) {
/*  731 */                           out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/*  732 */                           out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/*  733 */                           out.write("\n    </a>\n        </td>\n     ");
/*      */                         } else {
/*  735 */                           out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/*  736 */                           out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/*  737 */                           out.write("\n\t</a>\n\t </td>\n\t");
/*      */                         }
/*  739 */                         out.write("\n    </tr>\n ");
/*  740 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  741 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  745 */                     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  746 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                     }
/*      */                     
/*  749 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  750 */                     out.write(10);
/*  751 */                     out.write(32);
/*      */                     
/*  753 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  754 */                     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  755 */                     _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*  756 */                     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  757 */                     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                       for (;;) {
/*  759 */                         out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/*  760 */                         out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/*  761 */                         out.write("\n\t </td>\n ");
/*  762 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  763 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  767 */                     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  768 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                     }
/*      */                     
/*  771 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  772 */                     out.write(10);
/*  773 */                     out.write(32);
/*  774 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/*  775 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  779 */                 if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/*  780 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                 }
/*      */                 
/*  783 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*  784 */                 out.write("\n \n  ");
/*      */               }
/*      */               
/*      */ 
/*  788 */               out.write("  \n \n ");
/*      */               
/*  790 */               if (!usertype.equals("F"))
/*      */               {
/*  792 */                 out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                 
/*  794 */                 ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  795 */                 _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  796 */                 _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/*  797 */                 int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  798 */                 if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                   for (;;) {
/*  800 */                     out.write(10);
/*  801 */                     out.write(9);
/*      */                     
/*  803 */                     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  804 */                     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/*  805 */                     _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                     
/*  807 */                     _jspx_th_c_005fwhen_005f5.setTest("${param.method !='maintenanceTaskListView'}");
/*  808 */                     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/*  809 */                     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                       for (;;) {
/*  811 */                         out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/*  812 */                         out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/*  813 */                         out.write("</a>\n  \t");
/*  814 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/*  815 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  819 */                     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/*  820 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                     }
/*      */                     
/*  823 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*  824 */                     out.write("\n  \t");
/*      */                     
/*  826 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  827 */                     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  828 */                     _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*  829 */                     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  830 */                     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                       for (;;) {
/*  832 */                         out.write("\n \t\t");
/*  833 */                         out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/*  834 */                         out.write("\n  \t");
/*  835 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  836 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  840 */                     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  841 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                     }
/*      */                     
/*  844 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  845 */                     out.write("\n  \t");
/*  846 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  847 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  851 */                 if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  852 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                 }
/*      */                 
/*  855 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  856 */                 out.write("\n     </td>\n </tr>   \n \n ");
/*      */                 
/*  858 */                 if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                 {
/*      */ 
/*  861 */                   out.write(32);
/*  862 */                   out.write(32);
/*  863 */                   out.write(10);
/*      */                   
/*  865 */                   IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  866 */                   _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  867 */                   _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                   
/*  869 */                   _jspx_th_c_005fif_005f0.setTest("${category!='LAMP'}");
/*  870 */                   int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  871 */                   if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                     for (;;) {
/*  873 */                       out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                       
/*  875 */                       ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  876 */                       _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/*  877 */                       _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_c_005fif_005f0);
/*  878 */                       int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/*  879 */                       if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                         for (;;) {
/*  881 */                           out.write(10);
/*  882 */                           out.write(32);
/*  883 */                           out.write(9);
/*      */                           
/*  885 */                           WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  886 */                           _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/*  887 */                           _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                           
/*  889 */                           _jspx_th_c_005fwhen_005f6.setTest("${param.method !='listTrapListener'}");
/*  890 */                           int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/*  891 */                           if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                             for (;;) {
/*  893 */                               out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/*  894 */                               out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/*  895 */                               out.write("</a>\n   \t");
/*  896 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/*  897 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  901 */                           if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/*  902 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                           }
/*      */                           
/*  905 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/*  906 */                           out.write("\n   \t");
/*      */                           
/*  908 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  909 */                           _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/*  910 */                           _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*  911 */                           int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/*  912 */                           if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                             for (;;) {
/*  914 */                               out.write("\n  \t\t");
/*  915 */                               out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/*  916 */                               out.write(" \n   \t");
/*  917 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/*  918 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  922 */                           if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/*  923 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                           }
/*      */                           
/*  926 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/*  927 */                           out.write("\n   \t");
/*  928 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/*  929 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  933 */                       if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/*  934 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                       }
/*      */                       
/*  937 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/*  938 */                       out.write("\n      </td>\n  </tr>   \n");
/*  939 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  940 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  944 */                   if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  945 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                   }
/*      */                   
/*  948 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  949 */                   out.write(10);
/*  950 */                   out.write(32);
/*      */                 }
/*      */                 
/*      */ 
/*  954 */                 out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                 
/*  956 */                 ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  957 */                 _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/*  958 */                 _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/*  959 */                 int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/*  960 */                 if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                   for (;;) {
/*  962 */                     out.write(10);
/*      */                     
/*  964 */                     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  965 */                     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/*  966 */                     _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                     
/*  968 */                     _jspx_th_c_005fwhen_005f7.setTest("${param.method =='showScheduleReports'}");
/*  969 */                     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/*  970 */                     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                       for (;;) {
/*  972 */                         out.write("\n       ");
/*  973 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  974 */                         out.write(10);
/*  975 */                         out.write(32);
/*  976 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/*  977 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  981 */                     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/*  982 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                     }
/*      */                     
/*  985 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/*  986 */                     out.write(10);
/*  987 */                     out.write(32);
/*      */                     
/*  989 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  990 */                     _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/*  991 */                     _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*  992 */                     int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/*  993 */                     if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                       for (;;) {
/*  995 */                         out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/*  996 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  997 */                         out.write("\n\t </a>\n ");
/*  998 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/*  999 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1003 */                     if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 1004 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                     }
/*      */                     
/* 1007 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 1008 */                     out.write(10);
/* 1009 */                     out.write(32);
/* 1010 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 1011 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1015 */                 if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 1016 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                 }
/*      */                 
/* 1019 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 1020 */                 out.write("\n    </td>\n</tr> \n");
/*      */               } else {
/* 1022 */                 out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 1023 */                 out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1024 */                 out.write("</a>\n     </td>\n </tr>   \n");
/*      */                 
/* 1026 */                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1027 */                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1028 */                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                 
/* 1030 */                 _jspx_th_c_005fif_005f1.setTest("${category!='LAMP'}");
/* 1031 */                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1032 */                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                   for (;;) {
/* 1034 */                     out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 1035 */                     out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 1036 */                     out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 1037 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1038 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1042 */                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1043 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                 }
/*      */                 
/* 1046 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1047 */                 out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 1048 */                 out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1049 */                 out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */               }
/* 1051 */               out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */               
/* 1053 */               ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1054 */               _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 1055 */               _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f2);
/* 1056 */               int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 1057 */               if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                 for (;;) {
/* 1059 */                   out.write(10);
/*      */                   
/* 1061 */                   WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1062 */                   _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 1063 */                   _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                   
/* 1065 */                   _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 1066 */                   int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 1067 */                   if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                     for (;;) {
/* 1069 */                       out.write("\n        ");
/* 1070 */                       out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 1071 */                       out.write(10);
/* 1072 */                       out.write(32);
/* 1073 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 1074 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1078 */                   if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 1079 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                   }
/*      */                   
/* 1082 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 1083 */                   out.write(10);
/* 1084 */                   out.write(32);
/*      */                   
/* 1086 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1087 */                   _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 1088 */                   _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 1089 */                   int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 1090 */                   if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                     for (;;) {
/* 1092 */                       out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 1093 */                       out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 1094 */                       out.write("\n\t </a>\n ");
/* 1095 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 1096 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1100 */                   if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 1101 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                   }
/*      */                   
/* 1104 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 1105 */                   out.write(10);
/* 1106 */                   out.write(32);
/* 1107 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 1108 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1112 */               if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 1113 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */               }
/*      */               
/* 1116 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 1117 */               out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */               
/* 1119 */               ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1120 */               _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 1121 */               _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f2);
/* 1122 */               int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 1123 */               if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                 for (;;) {
/* 1125 */                   out.write(10);
/*      */                   
/* 1127 */                   WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1128 */                   _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 1129 */                   _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                   
/* 1131 */                   _jspx_th_c_005fwhen_005f9.setTest("${param.method!='showMailServerConfiguration'}");
/* 1132 */                   int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 1133 */                   if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                     for (;;) {
/* 1135 */                       out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 1136 */                       out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1137 */                       out.write("\n    </a>    \n ");
/* 1138 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 1139 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1143 */                   if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 1144 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                   }
/*      */                   
/* 1147 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 1148 */                   out.write(10);
/* 1149 */                   out.write(32);
/*      */                   
/* 1151 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1152 */                   _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 1153 */                   _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 1154 */                   int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 1155 */                   if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                     for (;;) {
/* 1157 */                       out.write(10);
/* 1158 */                       out.write(9);
/* 1159 */                       out.write(32);
/* 1160 */                       out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1161 */                       out.write(10);
/* 1162 */                       out.write(32);
/* 1163 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 1164 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1168 */                   if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 1169 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                   }
/*      */                   
/* 1172 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 1173 */                   out.write(10);
/* 1174 */                   out.write(32);
/* 1175 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 1176 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1180 */               if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 1181 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */               }
/*      */               
/* 1184 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 1185 */               out.write("\n    </td>\n</tr>\n\n\n");
/* 1186 */               if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 1187 */                 out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                 
/* 1189 */                 ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1190 */                 _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 1191 */                 _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/* 1192 */                 int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 1193 */                 if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                   for (;;) {
/* 1195 */                     out.write(10);
/*      */                     
/* 1197 */                     WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1198 */                     _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 1199 */                     _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                     
/* 1201 */                     _jspx_th_c_005fwhen_005f10.setTest("${param.method!='SMSServerConfiguration'}");
/* 1202 */                     int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 1203 */                     if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                       for (;;) {
/* 1205 */                         out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 1206 */                         out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 1207 */                         out.write("\n    </a>\n ");
/* 1208 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 1209 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1213 */                     if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 1214 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                     }
/*      */                     
/* 1217 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 1218 */                     out.write(10);
/* 1219 */                     out.write(32);
/*      */                     
/* 1221 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1222 */                     _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 1223 */                     _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 1224 */                     int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 1225 */                     if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                       for (;;) {
/* 1227 */                         out.write("\n         ");
/* 1228 */                         out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 1229 */                         out.write(10);
/* 1230 */                         out.write(32);
/* 1231 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 1232 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1236 */                     if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 1237 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                     }
/*      */                     
/* 1240 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 1241 */                     out.write(10);
/* 1242 */                     out.write(32);
/* 1243 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 1244 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1248 */                 if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 1249 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                 }
/*      */                 
/* 1252 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 1253 */                 out.write("\n    </td>\n</tr>\n");
/*      */               }
/* 1255 */               out.write("\n\n\n ");
/*      */               
/* 1257 */               if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */               {
/*      */ 
/* 1260 */                 out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                 
/* 1262 */                 ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1263 */                 _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 1264 */                 _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/* 1265 */                 int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 1266 */                 if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                   for (;;) {
/* 1268 */                     out.write(10);
/*      */                     
/* 1270 */                     WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1271 */                     _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 1272 */                     _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                     
/* 1274 */                     _jspx_th_c_005fwhen_005f11.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 1275 */                     int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 1276 */                     if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                       for (;;) {
/* 1278 */                         out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 1279 */                         out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 1280 */                         out.write("\n    </a>\n ");
/* 1281 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 1282 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1286 */                     if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 1287 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                     }
/*      */                     
/* 1290 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 1291 */                     out.write(10);
/* 1292 */                     out.write(32);
/*      */                     
/* 1294 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1295 */                     _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 1296 */                     _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 1297 */                     int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 1298 */                     if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                       for (;;) {
/* 1300 */                         out.write(10);
/* 1301 */                         out.write(9);
/* 1302 */                         out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 1303 */                         out.write(10);
/* 1304 */                         out.write(32);
/* 1305 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 1306 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1310 */                     if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 1311 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                     }
/*      */                     
/* 1314 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 1315 */                     out.write(10);
/* 1316 */                     out.write(32);
/* 1317 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 1318 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1322 */                 if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 1323 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                 }
/*      */                 
/* 1326 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 1327 */                 out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                 
/* 1329 */                 ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1330 */                 _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 1331 */                 _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/* 1332 */                 int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 1333 */                 if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                   for (;;) {
/* 1335 */                     out.write(10);
/*      */                     
/* 1337 */                     WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1338 */                     _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 1339 */                     _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                     
/* 1341 */                     _jspx_th_c_005fwhen_005f12.setTest("${uri !='/Upload.do'}");
/* 1342 */                     int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 1343 */                     if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                       for (;;) {
/* 1345 */                         out.write("   \n        ");
/*      */                         
/* 1347 */                         AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1348 */                         _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 1349 */                         _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f12);
/*      */                         
/* 1351 */                         _jspx_th_am_005fadminlink_005f2.setHref("/Upload.do");
/*      */                         
/* 1353 */                         _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 1354 */                         int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 1355 */                         if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 1356 */                           if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 1357 */                             out = _jspx_page_context.pushBody();
/* 1358 */                             _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 1359 */                             _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 1362 */                             out.write("\n           ");
/* 1363 */                             out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 1364 */                             out.write("\n            ");
/* 1365 */                             int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 1366 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 1369 */                           if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 1370 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 1373 */                         if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 1374 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                         }
/*      */                         
/* 1377 */                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 1378 */                         out.write(10);
/* 1379 */                         out.write(10);
/* 1380 */                         out.write(32);
/* 1381 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 1382 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1386 */                     if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 1387 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                     }
/*      */                     
/* 1390 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 1391 */                     out.write(10);
/* 1392 */                     out.write(32);
/*      */                     
/* 1394 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1395 */                     _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 1396 */                     _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 1397 */                     int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 1398 */                     if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                       for (;;) {
/* 1400 */                         out.write(10);
/* 1401 */                         out.write(9);
/* 1402 */                         out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 1403 */                         out.write(10);
/* 1404 */                         out.write(32);
/* 1405 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 1406 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1410 */                     if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 1411 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                     }
/*      */                     
/* 1414 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 1415 */                     out.write(10);
/* 1416 */                     out.write(32);
/* 1417 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 1418 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1422 */                 if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 1423 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                 }
/*      */                 
/* 1426 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 1427 */                 out.write("\n    </td>\n</tr>\n \n ");
/*      */               }
/*      */               
/*      */ 
/* 1431 */               out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */               
/* 1433 */               ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1434 */               _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 1435 */               _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f2);
/* 1436 */               int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 1437 */               if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                 for (;;) {
/* 1439 */                   out.write(10);
/*      */                   
/* 1441 */                   WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1442 */                   _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 1443 */                   _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                   
/* 1445 */                   _jspx_th_c_005fwhen_005f13.setTest("${uri !='/admin/userconfiguration.do'}");
/* 1446 */                   int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 1447 */                   if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                     for (;;) {
/* 1449 */                       out.write("\n    \n        ");
/*      */                       
/* 1451 */                       AdminLink _jspx_th_am_005fadminlink_005f3 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1452 */                       _jspx_th_am_005fadminlink_005f3.setPageContext(_jspx_page_context);
/* 1453 */                       _jspx_th_am_005fadminlink_005f3.setParent(_jspx_th_c_005fwhen_005f13);
/*      */                       
/* 1455 */                       _jspx_th_am_005fadminlink_005f3.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                       
/* 1457 */                       _jspx_th_am_005fadminlink_005f3.setEnableClass("new-left-links");
/* 1458 */                       int _jspx_eval_am_005fadminlink_005f3 = _jspx_th_am_005fadminlink_005f3.doStartTag();
/* 1459 */                       if (_jspx_eval_am_005fadminlink_005f3 != 0) {
/* 1460 */                         if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/* 1461 */                           out = _jspx_page_context.pushBody();
/* 1462 */                           _jspx_th_am_005fadminlink_005f3.setBodyContent((BodyContent)out);
/* 1463 */                           _jspx_th_am_005fadminlink_005f3.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 1466 */                           out.write("\n       ");
/* 1467 */                           out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 1468 */                           out.write("\n        ");
/* 1469 */                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f3.doAfterBody();
/* 1470 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 1473 */                         if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/* 1474 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 1477 */                       if (_jspx_th_am_005fadminlink_005f3.doEndTag() == 5) {
/* 1478 */                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f3); return;
/*      */                       }
/*      */                       
/* 1481 */                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f3);
/* 1482 */                       out.write(10);
/* 1483 */                       out.write(10);
/* 1484 */                       out.write(32);
/* 1485 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 1486 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1490 */                   if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 1491 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                   }
/*      */                   
/* 1494 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 1495 */                   out.write(10);
/* 1496 */                   out.write(32);
/*      */                   
/* 1498 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1499 */                   _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 1500 */                   _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 1501 */                   int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 1502 */                   if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                     for (;;) {
/* 1504 */                       out.write(10);
/* 1505 */                       out.write(9);
/* 1506 */                       out.write(32);
/* 1507 */                       out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 1508 */                       out.write(10);
/* 1509 */                       out.write(32);
/* 1510 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 1511 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1515 */                   if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 1516 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                   }
/*      */                   
/* 1519 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 1520 */                   out.write(10);
/* 1521 */                   out.write(32);
/* 1522 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 1523 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1527 */               if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 1528 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */               }
/*      */               
/* 1531 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 1532 */               out.write("\n    </td>\n</tr>\n   \n\n ");
/* 1533 */               if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 1534 */                 out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                 
/* 1536 */                 ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1537 */                 _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 1538 */                 _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f2);
/* 1539 */                 int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 1540 */                 if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                   for (;;) {
/* 1542 */                     out.write("\n   ");
/*      */                     
/* 1544 */                     WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1545 */                     _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 1546 */                     _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                     
/* 1548 */                     _jspx_th_c_005fwhen_005f14.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 1549 */                     int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 1550 */                     if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                       for (;;) {
/* 1552 */                         out.write("\n    ");
/*      */                         
/* 1554 */                         AdminLink _jspx_th_am_005fadminlink_005f4 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1555 */                         _jspx_th_am_005fadminlink_005f4.setPageContext(_jspx_page_context);
/* 1556 */                         _jspx_th_am_005fadminlink_005f4.setParent(_jspx_th_c_005fwhen_005f14);
/*      */                         
/* 1558 */                         _jspx_th_am_005fadminlink_005f4.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                         
/* 1560 */                         _jspx_th_am_005fadminlink_005f4.setEnableClass("new-left-links");
/* 1561 */                         int _jspx_eval_am_005fadminlink_005f4 = _jspx_th_am_005fadminlink_005f4.doStartTag();
/* 1562 */                         if (_jspx_eval_am_005fadminlink_005f4 != 0) {
/* 1563 */                           if (_jspx_eval_am_005fadminlink_005f4 != 1) {
/* 1564 */                             out = _jspx_page_context.pushBody();
/* 1565 */                             _jspx_th_am_005fadminlink_005f4.setBodyContent((BodyContent)out);
/* 1566 */                             _jspx_th_am_005fadminlink_005f4.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 1569 */                             out.write(10);
/* 1570 */                             out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 1571 */                             out.write("\n    ");
/* 1572 */                             int evalDoAfterBody = _jspx_th_am_005fadminlink_005f4.doAfterBody();
/* 1573 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 1576 */                           if (_jspx_eval_am_005fadminlink_005f4 != 1) {
/* 1577 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 1580 */                         if (_jspx_th_am_005fadminlink_005f4.doEndTag() == 5) {
/* 1581 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f4); return;
/*      */                         }
/*      */                         
/* 1584 */                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f4);
/* 1585 */                         out.write("\n   ");
/* 1586 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 1587 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1591 */                     if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 1592 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                     }
/*      */                     
/* 1595 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 1596 */                     out.write("\n   ");
/*      */                     
/* 1598 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1599 */                     _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 1600 */                     _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 1601 */                     int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 1602 */                     if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                       for (;;) {
/* 1604 */                         out.write(10);
/* 1605 */                         out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 1606 */                         out.write("\n   ");
/* 1607 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 1608 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1612 */                     if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 1613 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                     }
/*      */                     
/* 1616 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 1617 */                     out.write(10);
/* 1618 */                     out.write(32);
/* 1619 */                     out.write(32);
/* 1620 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 1621 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1625 */                 if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 1626 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                 }
/*      */                 
/* 1629 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 1630 */                 out.write("\n </td>\n</tr>\n  ");
/*      */               }
/* 1632 */               out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */               
/* 1634 */               ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1635 */               _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 1636 */               _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_tiles_005fput_005f2);
/* 1637 */               int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 1638 */               if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                 for (;;) {
/* 1640 */                   out.write("\n   ");
/*      */                   
/* 1642 */                   WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1643 */                   _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 1644 */                   _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                   
/* 1646 */                   _jspx_th_c_005fwhen_005f15.setTest("${param.method!='showDataCleanUp'}");
/* 1647 */                   int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 1648 */                   if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                     for (;;) {
/* 1650 */                       out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 1651 */                       out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1652 */                       out.write("\n    </a>\n   ");
/* 1653 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 1654 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1658 */                   if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 1659 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                   }
/*      */                   
/* 1662 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 1663 */                   out.write("\n   ");
/*      */                   
/* 1665 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1666 */                   _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 1667 */                   _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 1668 */                   int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 1669 */                   if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                     for (;;) {
/* 1671 */                       out.write(10);
/* 1672 */                       out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1673 */                       out.write("\n   ");
/* 1674 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 1675 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1679 */                   if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 1680 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                   }
/*      */                   
/* 1683 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 1684 */                   out.write(10);
/* 1685 */                   out.write(32);
/* 1686 */                   out.write(32);
/* 1687 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 1688 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1692 */               if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 1693 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */               }
/*      */               
/* 1696 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 1697 */               out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 1698 */               out.write("\n    <br>\n    ");
/* 1699 */               java.util.Hashtable applications = null;
/* 1700 */               synchronized (application) {
/* 1701 */                 applications = (java.util.Hashtable)_jspx_page_context.getAttribute("applications", 4);
/* 1702 */                 if (applications == null) {
/* 1703 */                   applications = new java.util.Hashtable();
/* 1704 */                   _jspx_page_context.setAttribute("applications", applications, 4);
/*      */                 }
/*      */               }
/* 1707 */               out.write(10);
/* 1708 */               out.write(10);
/* 1709 */               out.write(10);
/* 1710 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 1711 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 1714 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 1715 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 1718 */           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 1719 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */           }
/*      */           
/* 1722 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 1723 */           out.write(10);
/*      */           
/* 1725 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 1726 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 1727 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 1729 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 1731 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 1732 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 1733 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 1734 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 1735 */               out = _jspx_page_context.pushBody();
/* 1736 */               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 1737 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 1740 */               out.write(" \n<!-- <link href=\"images/discovery_styles.css\" type=\"text/css\" rel=\"stylesheet\" />-->\n<SCRIPT LANGAUGE =\"javascript\" SRC=\"../template/appmanager.js\" ></SCRIPT>\n<script type=\"text/javascript\" src=\"/template/chosen.jquery.min.js\"></script>\n<!-- <script type=\"text/javascript\" src=\"/template/jquery.jscrollpane.min.js\"></script>\n<script type=\"text/javascript\" src=\"/template/jquery.mousewheel.js\"></script> -->\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/template/jquery.jscrollpane.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n\n<script>\n\n var discID=\"\";\n \n\n$(document).ready(function()\n{ \n\t ");
/* 1741 */               if (_jspx_meth_c_005fchoose_005f16(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 1743 */               out.write("\n\n     function rotate(e)\n     {\n       $(e).animate( { left: $('.loaderInner').width() }, 1500, function(){\n         $(e).css(\"left\", -($(e).width()) + \"px\");//No I18N\n         rotate(e);\n       });\n     }\n     \n     rotate('.barStripe');//No I18N\n\n\t}); \n\n  function setCidrRange(value) \n  {\n    for(var i=1; i<33;i++)\n    {\n    $('select[name=cidr]').append('<option value='+i+'>'+i+'</option>');\n    }\n    $('select[name=cidr]').val(value);\n  \n  }\n\tfunction performEditActions() {\n\t\t//These Elements should be made UnEditable during discovery.\n\t\t\t$('input[name=discoveryName]').attr(\"disabled\", true); //No I18N\n\t\t\t$(\"input[name=discoverytype]\").attr('disabled', true); //No I18N\n\t\t\t$(\"select[name=selectedMAS]\").attr('disabled', true).trigger(\"liszt:updated\"); //No I18N\n\t\t\tvar credentialSelected= \"");
/* 1744 */               if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 1746 */               out.write("\";\n\t\t\tif(credentialSelected == 'selectCredentials' )\n\t\t\t{\n\t\t\tshowCredentialList('true');\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\tshowCredentialList('false');\n\t\t\t}\n\t\t\tvar discoveryType=\"");
/* 1747 */               if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 1749 */               out.write("\";\n\t\t\tchangeAddressType(discoveryType);\n\t\t\tif(discoveryType == 'cidr')\n\t\t\t{\n\t\t\t\t  var cidr=\"");
/* 1750 */               if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 1752 */               out.write("\";\n\t\t\t\t  $('select[name=cidr]').val(cidr);     \n\t\t\t}\n\t\t\tvar createMGViewEnabled=\"");
/* 1753 */               if (_jspx_meth_c_005fout_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 1755 */               out.write("\";\n\t\t\tif(createMGViewEnabled=='true')\n\t\t\t{\n\t\t\t\t$('input:checkbox[name=createMGview]').attr('checked',true);// No I18N\n\t\t\t}\n\t}\n\nfunction checkDiscName()\n{\n   $(\"#discoverydisplay\").css(\"display\",'none');//No I18N\n   $('input[name=discoveryName]').focus(function(e) //No I18N\n    {        \n        //No I18N\n        $(\"input[name=discover]\").attr(\"disabled\", false); //No I18N\n    });\n\n    $('input[name=discoveryName]').blur(function(e) //No I18N\n    {\n     \n        cacheid = (new Date()).getTime();\n        var discoveryName = $('input[name=discoveryName]').val().trim(); //No I18N      \n       if(discoveryName != \"\")\n        {    dataString = \"&method=discoveryNameExistsCheck&discoveryName=\"+discoveryName+\"&cacheid=\"+cacheid; //No I18N\n            $.ajax(\n            {\n                type:\"POST\", //No I18N\n                url:\"/newDiscoveryAction.do\", //No I18N\n                data:dataString,\n                success: function(response)\n                {\n                  \n                    if(response.trim() == 'false')\n");
/* 1756 */               out.write("                    {\n                        $(\"#discoverydisplay\").text(\"\"); //No I18N\n                        $(\"#discoverydisplay\").css(\"background-color\",\"white\"); //No I18N\n                       $(\"input[name=discover]\").attr(\"disabled\", false);//No I18N\n                       $(\"#discoverydisplay\").css(\"display\",'none');//No I18N\n                    }\n                    else\n                    {\n                      var stringToShow = \"");
/* 1757 */               out.print(FormatUtil.getString("am.discover.duplicate.discoveryname.error"));
/* 1758 */               out.write("\"; \n                      $(\"#discoverydisplay\").html(stringToShow);      // Set response into particular div ID .. //No I18N\n                      $(\"#discoverydisplay\").css(\"color\",\"red\"); //No I18N\n                      $(\"input[name=discover]\").attr(\"disabled\", true); //No I18N\n                       $(\"#discoverydisplay\").css(\"display\",'block');//No I18N\n                     //  $('input[name=discoveryName]').focus();\n                    }\n                }\n            });\n        }\n    });\n}\n\nfunction checkForNumber(event)\n{\n    var keyCodeArray = [9,46,48,49,50,51,52,53,54,55,56,57,8,35,36,173]; //tab,del,0-9,bkspace,home,end,hyphen\n    var keycode = (event.keyCode ? event.keyCode : event.which);\n    //alert(keycode); \n    return ((jQuery.inArray(keycode, keyCodeArray) != -1) ? true : false);\n}\n\nfunction checkOnChange()\n{\n      getPortsForThisDiscovery();\n}\n\nfunction getPortsForThisDiscovery()\n{\n      var rows = $('#defaultPortDetails tr').length;\n      var fullPorts = [];\n\t  var serverPort=getEnabledPorts(\"defaultPortDetails\"); //No I18N  \n");
/* 1759 */               out.write("\t  var appPort=getEnabledPorts(\"defaultappPortDetails\"); //No I18N  \n\t  fullPorts = serverPort.concat(appPort);\n      return fullPorts.toString();\n\n}\nfunction getEnabledPorts(tableName) {\n\nvar portArr = [];\n$('#'+tableName+' tr').each(function() {\n      var idName = $(this).prop('id'); //No I18N\n      var port = $(this).find(\"input\").val();\n      if(!$(\"tr[id='\"+idName+\"']\").hasClass(\"iconEnable\") && (idName != '' || port != undefined))\n      {                \n            portArr[portArr.length] = idName+\"#1:\"+port+\"#2\";//No I18N  \n      }\n      });\n\t  return portArr;\n}\nfunction removePort(id)\n{\n    //alert(id);\n    $(\"#\"+id).remove();        \n}\n\nfunction removeCredentialAndAddToNotSelectedList(credentialID, credentialName)\n{\n    $(\"#\"+credentialID).remove();\n    var toAppend = \"<li id=\\'\"+credentialID+\"\\'><span>\"+credentialName+\"<a class=\\\"iconClose iconTick\\\" href=\\\"javascript:addToCredentialsSelectedList(\\'\"+credentialID+\"\\',\\'\"+credentialName+\"\\')\\\"><img src=\\\"../images/icon_tick.png\\\"></a></span></li>\";\n    $(\"#leftOutCredentialsULId\").append(toAppend);\n");
/* 1760 */               out.write("\tif($(\"#leftOutCredentialsULId\").is(':visible'))\n\t{\n\t$('input[name=showAllCred]').val('");
/* 1761 */               out.print(FormatUtil.getString("am.discover.hidebelow.credential"));
/* 1762 */               out.write("');\n\t}\n}\n\nfunction addPort(idName)\n{    \n    var toAppend = \"<li id=\\\"dummy\\\"><input type=\\\"text\\\" onkeydown=\\\"return checkForNumber(event)\\\" name=\\\"portText\\\" id=\\\"enterPort\\\" style=\\\"width:85px;\\\"/><a class=\\\"iconCheck\\\" href=\\\"javascript:toggleAndCloseTemp(\\'\"+idName+\"\\')\\\"> <i>x</i> </a></li>\"; //No I18N\n    $(\"#\"+idName).append(toAppend);    \n    $(\".iconAdd\").hide();\n    $(\"#enterPort\").focus();\n}\nfunction inputLimit(presentCursorPosition) {\n  \n  if($(\"#\"+presentCursorPosition).val() > 255)\n  {  \n    $(\"#\"+presentCursorPosition).val(255);\n  }\n  if(presentCursorPosition == 'fromAddress4') \n  {\n  $(\"#toAddress1\").val($(\"#fromAddress1\").val()); //No I18N\n  $(\"#toAddress2\").val($(\"#fromAddress2\").val()); //No I18N\n  $(\"#toAddress3\").val($(\"#fromAddress3\").val()); //No I18N\n  $(\"#toAddress4\").focus(); \n  }\n}\nfunction checkAndShow(presentCursorPosition,nextCursorPosition,event)\n{    \n    if(checkForNumber(event))\n    {\n            var keycode = (event.keyCode ? event.keyCode : event.which);\n            var keycodeToSkipArray = [8,9,46,35,36];\n");
/* 1763 */               out.write("            if((jQuery.inArray(keycode,keycodeToSkipArray) != -1) ? false : true)\n            {\n                if($(\"#\"+presentCursorPosition).val().length == 3)\n                {\n                    $(\"#\"+nextCursorPosition).focus();\n                    return true;\n                }\n            }\n    }\n    else\n    {\n        return false;\n    }\n    return true;    \n}\n\n\n//It is going to be dummy id always - so can remove it blindly\nfunction toggleAndCloseTemp(idName)\n{    \n    var valueToShow = $(\"#enterPort\").val();    \n    if(\"\" != valueToShow)\n    {        \n        var idForli = idName+\"_\"+valueToShow;\n        var toAppend1 = \"<li id=\\\"\"+idForli+\"\\\"><span>\"+valueToShow+\"<a class=\\\"iconClose\\\" href=\\\"javascript:removePort(\\'\"+idForli+\"\\')\\\">x</a></span></li>\"; //No I18N\n        $(\"#\"+idName).append(toAppend1);\n    }\n    $(\"#dummy\").remove();\n    $(\".iconAdd\").show();    \n}\n\nfunction checkAndAddPortDetails(credentialTypeDisplayName, credentialType)\n{    \n    var checkArray = new Array();\n    $('#portTypeDiv ul').each(function() { \n");
/* 1764 */               out.write("            checkArray.push($(this).attr('id'));    //No I18N\n    });\n            if(($.inArray(credentialType,checkArray)) == -1) //No port details for this type\n            {\n                addPortDetailsForThisType(credentialTypeDisplayName, credentialType);\n            }\n            else\n            {\n                //Need not do anything for now - later need to check the ports for default ports and then add it accordingly ...so leave this place to be in rest for sometime ..\n            }\n}\n\nfunction showCredentialList(toShow)\n{\n    if(toShow == 'true') {\n      $('#credentialDetailsDiv').css(\"display\",'block'); //No I18N\n      $(\"input[name=showAllCred]\").show();\n      $(\"input[name=showAllCred]\").attr(\"disabled\",false);//No I18N\n      if(($(\"#credentialsSelected li\").size()) == 0 && ($(\"#leftOutCredentialsULId li\").size()) == 0) {\n  \t\t$(\"#addCred\").trigger(\"click\");//No I18N\n  \t  }\n    }\n    else\n    {\n      $('#credentialDetailsDiv').css(\"display\",'none');//No I18N\n      $('#leftOutCredentials').css(\"display\",'none');//No I18N\n");
/* 1765 */               out.write("      $(\"input[name=showAllCred]\").attr(\"disabled\", true);//No I18N\n\t  $(\"input[name=showAllCred]\").val(\"");
/* 1766 */               out.print(FormatUtil.getString("am.discovery.showall.credential"));
/* 1767 */               out.write("\");\n      $(\"input[name=showAllCred]\").hide();\n    }\n\n}\n\nfunction showDeviceCountDiv(toShow)\n{\n\tvar selectedVal=$('select[name=selectedMAS]').val();\n\t//alert(selectedVal);\n  \tvar masIDfromMap=0;\n\tvar moCountFromMap=0;\n\tif(selectedVal==0)\n\t{\n\t\ttoShow=false;\n\t}\n\ttoShow=selectedVal==0?'false':'true';\n\t\n\t");
/* 1768 */               if (_jspx_meth_c_005fforEach_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 1770 */               out.write(" \n\t\n\t $('#devCountDiv').css(\"display\",toShow == 'true' ? 'block' : 'none'); //No I18N\n// $('#devCountDiv').html(\"<span>Device count in Selected Managed Server :\"+moCountFromMap+\"</span>\"); //No I18N\n $('#devCountSpan').html(\"");
/* 1771 */               out.print(FormatUtil.getString("am.discovery.selected.managedserver.devicecount"));
/* 1772 */               out.write("\"+moCountFromMap); \n\t //document.getElementById(\"devCountDiv\").innerHTML=\"");
/* 1773 */               if (_jspx_meth_c_005fout_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 1775 */               out.write("\";\n}\n\nfunction addPortDetailsForThisType(credentialTypeDisplayName, credentialType)\n{\n    var cacheID = (new Date()).getTime();\n    var toAppendToPortTypeDiv = \"<div id=\\\"\"+credentialType+\"\\\"><b>\"+credentialTypeDisplayName+\" <a class=\\\"iconClose iconTick\\\" href=\\\"javascript:removePort(\\'\"+credentialType+\"\\')\\\"><img src=\\\"../images/icon_tick.png\\\"></a></b><div class=\\\"portContent\\\"><ul id=\\\"credentialUL\"+cacheID+\"\\\"><li id=\\\"defaultID\"+cacheID+\"\\\"><span>7001<a class=\\\"iconClose\\\" href=\\\"javascript:removePort(\\'defaultID\"+cacheID+\"\\')\\\">x</a></span></li></ul><a class=\\\"iconAdd\\\" href=\\\"javascript:addPort(\\'credentialUL\"+cacheID+\"\\')\\\">+</a></div></div>\"; //No I18N\n    $(\"#portTypeDiv\").append(toAppendToPortTypeDiv);\n     \n}\nfunction showDiscoveryStatus1()\n{\n    var credentialDetails = $('#credentialsSelected li').map(function(i,n) {\n        return $(n).attr('id'); //No I18N\n        }).get().join(',');        \n}\n\nfunction validateDiscover() {\n\t  if($('input[name=discoveryName]').val().trim() == \"\")\n\t  { \n\t    alert(\"");
/* 1776 */               out.print(FormatUtil.getString("am.discover.discoveryname.empty.error"));
/* 1777 */               out.write("\");//No I18N\n\t    return -1;\n\t  }\n\t  var discoverytype = $('input[name=discoverytype]:radio:checked').val();\n\t  if(discoverytype == \"cidr\") {\n\t    var netWorkAddress = getRequiredField('netWorkAddress'); //No I18N\n\t    \n          var cidr = $('select[name=cidr]').val().trim();\n\t    if((netWorkAddress == -1) || (cidr == \"\"))\n\t    {\n\t      alert(\"");
/* 1778 */               out.print(FormatUtil.getString("am.discover.networkaddress.empty.error"));
/* 1779 */               out.write("\");//No I18N\n\t      return -1;\n\t    }\n\t    \n\t    if( cidr > 32)\n\t    {\n\t    alert(\"");
/* 1780 */               out.print(FormatUtil.getString("am.discover.invalid.cidr"));
/* 1781 */               out.write("\");//No I18N\n\t    return -1;\n\t  }\n\t  \t  }\n\t  if(discoverytype == \"range\") {\n\t    var netMask = $('select[name=netMask]').val().trim();\n\t    var getFromAddress = getRequiredField('fromAddress'); //No I18N\n\t    //alert(getFromAddress);\n\t    var getToAddress = getRequiredField('toAddress');//No I18N\n\t    if(getFromAddress == -1 ) {\n\t      alert(\"");
/* 1782 */               out.print(FormatUtil.getString("am.discover.fromaddress.empty.error"));
/* 1783 */               out.write("\");//No I18N\n\t      return -1;\n\t    }\n\t    if(getToAddress == -1)\n\t    {\n\t      alert(\"");
/* 1784 */               out.print(FormatUtil.getString("am.discover.toaddress.empty.error"));
/* 1785 */               out.write("\");//No I18N\n\t      return -1;\n\t    }\n\n\t  }\n\t  if(discoverytype == 'virtual') {\t\n\t\tif($('input[name=virtualHost]').val().trim() == \"\" ) {\n\t      alert(\"");
/* 1786 */               out.print(FormatUtil.getString("am.discover.invalid.virtualaddress"));
/* 1787 */               out.write("\");//No I18N\n\t      return -1;\n\t    }\n\t\tif($('input[name=virtualPort]').val().trim() == \"\" ) {\n\t      alert(\"");
/* 1788 */               out.print(FormatUtil.getString("am.discover.invalid.virtualport"));
/* 1789 */               out.write("\");//No I18N\n\t      return -1;\n\t    }\n\t\tif($('input[name=virtualUserName]').val().trim() == \"\" ) {\n\t      alert(\"");
/* 1790 */               out.print(FormatUtil.getString("am.discover.invalid.virtual.hostname"));
/* 1791 */               out.write("\");//No I18N\n\t      return -1;\n\t    }\n\t\tif($('input[name=virtualPassword]').val().trim() == \"\" ) {\n\t      alert(\"");
/* 1792 */               out.print(FormatUtil.getString("am.discover.invalid.virtual.password"));
/* 1793 */               out.write("\");//No I18N\n\t      return -1;\n\t    }\n\t\t\n\t  }\n\t  \n\t  var masID = $('select[name=selectedMAS]').val();\n\t  if(masID == '0')\n\t  {\n\t  alert(\"");
/* 1794 */               out.print(FormatUtil.getString("am.discover.select.mas.alert"));
/* 1795 */               out.write("\");//No I18N\n\t  return -1;\n\t  }\n\n\t}\n\t\nfunction stopDiscoveryProcess()\n{\n\t    var confirmStopDiscovery = confirm('");
/* 1796 */               out.print(FormatUtil.getString("am.webclient.discovery.stop"));
/* 1797 */               out.write("');\n\t    if(confirmStopDiscovery)\n\t    {\n\t      \t var dataString = \"&method=stopDiscoveryProcess&discoveryID=\"+discID; //No I18N\n\t    \t $.ajax({\n\t \t         type: \"POST\", //No I18N\n\t \t         url: \"/newDiscoveryAction.do\",//No I18N\n\t \t         data: dataString,            // Query String parameters\n\t \t         success: function(response)\n\t \t         {\n\t \t        \tvar isStopped=response.trim();\n\t \t            if(isStopped=='true')\n\t                \t{\n\t                            var redirect = '/newDiscoveryAction.do?method=getDiscoveryDetails';//No I18N\n\t                            $.redirectPost(redirect, {\"isDiscoveryStopped\":isStopped , \"discId\": discID});//No I18N\n\t                        //\twindow.location.href=\"/newDiscoveryAction.do?method=getDiscoveryDetails&isDiscoveryStopped=\"+isStopped+\"&discId=\"+discID;//No I18N\n\t                \t}\n\t             }\n\t         });\n\t       \t \n\t    \t $.extend(\n\t    \t\t\t {\n\t    \t\t\t     redirectPost: function(location, args)\n\t    \t\t\t     {\n\t    \t\t\t         var form = '';\n");
/* 1798 */               out.write("\t    \t\t\t         $.each( args, function( key, value ) {\n\t    \t\t\t             value = value.split('\"').join('\\\"')\n\t    \t\t\t             form += '<input type=\"hidden\" name=\"'+key+'\" value=\"'+value+'\">';\n\t    \t\t\t         });\n\t    \t\t\t         $('<form action=\"' + location + '\" method=\"POST\">' + form + '</form>').appendTo($(document.body)).submit();\n\t    \t\t\t     }\n\t    \t\t\t });\n\t    }\n\n}\n\t\nfunction startReDiscovery()\n{\n   // alert(\"startRediscovery\");\n    var discoveryID=\"");
/* 1799 */               if (_jspx_meth_c_005fout_005f8(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 1801 */               out.write("\";\n    var dataString=\"&method=performRediscovery&discoveryID=\"+discoveryID;//No I18N\n    sendAjaxReq(dataString);\n}\n\t\nfunction showDiscoveryStatus()\n{\n\t if(validateDiscover() == -1)\n     {\n       return;\n     }\n\t $(\"button[name=discover]\").attr(\"disabled\", true); //No I18N\n    var discoveryName = $('input[name=discoveryName]').val().trim(); //No I18N\n    var discoverytype = $('input[name=discoverytype]:radio:checked').val();\n    var credentialDetails = \"\"; //No I18N\n    var isAllCred = \"false\"; //No I18N\n\t ");
/* 1802 */               if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 1804 */               out.write("\n    if(\"selectCredentials\" == ($('input[name=credentialDetailsSelected]:radio:checked').val()))\n    {\n        credentialDetails = $('#credentialsSelected li').map(function(i,n) {\n        return $(n).attr('id'); //No I18N\n        }).get().join(',');\n    }\n    else\n    {\n        credentialDetails = \"All\"; //No I18N\n        isAllCred = \"true\" ; //No I18N\n    }\n    var ports = getPortsForThisDiscovery();    \n\tvar dataString;\n    if(discoverytype == 'range') { \n\t\tvar netMask = $('select[name=netMask]').val().trim(); //No I18N\n        var getFromAddress = getRequiredField('fromAddress'); //No I18N\n        var getToAddress = getRequiredField('toAddress'); //No I18N\n        dataString = \"&method=getDiscoveryParamsAndShowDiscoveryProcess&discoveryName=\"+discoveryName+\"&discoveryType=\"+discoverytype+\"&netMask=\"+netMask+\"&fromAddress=\"+getFromAddress+\"&toAddress=\"+getToAddress+\"&isAllCred=\"+isAllCred+\"&selectedCredentials=\"+credentialDetails+\"&portDetails=\"+ports; //No I18N\n\n      }\n    else if(discoverytype == 'cidr'){\n");
/* 1805 */               out.write("        var netWorkAddress = getRequiredField('netWorkAddress'); //No I18N\n        var cidr = $('select[name=cidr]').val().trim();\n         dataString = \"&method=getDiscoveryParamsAndShowDiscoveryProcess&discoveryName=\"+discoveryName+\"&discoveryType=\"+discoverytype+\"&netWorkAddress=\"+netWorkAddress+\"&cidr=\"+cidr+\"&isAllCred=\"+isAllCred+\"&selectedCredentials=\"+credentialDetails+\"&portDetails=\"+ports; //No I18N\n      \n      }\n\telse if(discoverytype == 'virtual'){\n\t\tvar netMask = $('select[name=netMask]').val().trim(); //No I18N\n\t\tvar virtualHost = $('input[name=virtualHost]').val().trim();\n\t\tvar virtualPort = $('input[name=virtualPort]').val().trim();\n\t\tvar virtualUserName = $('input[name=virtualUserName]').val().trim();\n\t\tvar virtualPassword = $('input[name=virtualPassword]').val().trim();\n\t\tvar createMGview = 'false'; //No I18N\n\t\tif($('input[name=createMGview]').prop('checked')) {\n\t\t\tcreateMGview='true'; //No I18N\n\t\t}\n\t\tdataString = \"&method=getDiscoveryParamsAndShowDiscoveryProcess&discoveryName=\"+discoveryName+\"&discoveryType=\"+discoverytype+\"&virtualHost=\"+virtualHost+\"&virtualPort=\"+virtualPort+\"&virtualUserName=\"+virtualUserName+\"&virtualPassword=\"+virtualPassword+\"&isAllCred=\"+isAllCred+\"&selectedCredentials=\"+credentialDetails+\"&portDetails=\"+ports+\"&createMGview=\"+createMGview+\"&netMask=\"+netMask; //No I18N \n");
/* 1806 */               out.write("\t  }\n\t  \n    ");
/* 1807 */               if (_jspx_meth_c_005fif_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 1809 */               out.write("\n\t//var edit=\"false\";\n\tvar discCateg=\"discovery\";//No I18N\n");
/* 1810 */               if (_jspx_meth_c_005fif_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 1812 */               out.write("\n\n//dataString =dataString+\"&edit=\"+edit;//No I18N\ndataString =dataString+\"&discoveryCateg=\"+discCateg;//No I18N\n\n//alert(dataString);\n  \nsendAjaxReq(dataString);                \n}\n\nfunction sendAjaxReq(dataString)\n{\n\n    $.ajax(\n    {\n                type:\"POST\", //No I18N\n                url:\"/newDiscoveryAction.do\", //No I18N\n                data:dataString,\n                success: function(response)\n                {\n                   // var discoveryID = response.trim();\n                    discID = response.trim();\n                    if(discID == '-1')\n                    {\n                    \t  var masDownMSg=\"");
/* 1813 */               out.print(FormatUtil.getString("am.discovery.masdown.alert.msg"));
/* 1814 */               out.write(" \";\n                          alert(masDownMSg);\n                        window.location.href=\"/newDiscoveryAction.do?method=getDiscoveryDetails\";//No I18N\n                    }\n                    else if(discID == '0')\n                    \t{\n                    \t var alreadyRunningMsg=\"");
/* 1815 */               out.print(FormatUtil.getString("am.discovery.already.running"));
/* 1816 */               out.write(" \";\n                         alert(alreadyRunningMsg);\n                       window.location.href=\"/newDiscoveryAction.do?method=getDiscoveryDetails\";//No I18N\n                    \t}\n                    \n                    else\n                    {\n                        $('#discoveryFirstPage').css(\"display\",'none'); //No I18N\n                        $('#discoverySecondPage').css(\"display\",'block'); //No I18N\n                        getDiscoveryStatusToShow(discID,\"Discovery Started..\"); //No I18N\n                    }\n                    //alert('success');\n                }\n    });                   \n}\nvar discoveryType=\"\";\nfunction getDiscoveryStatusToShow(discoveryID, previousMessage)\n{\n        \n        var cacheID = (new Date()).getTime();\n        var toSendToDiscovery = \"&method=getDiscoveryStatusMessage&discoveryID=\"+discoveryID+\"&cacheid=\"+cacheID; //No I18N\n        $.ajax(\n        {\n                type:\"POST\", //No I18N\n                url:\"/newDiscoveryAction.do\", //No I18N\n                data:toSendToDiscovery,\n");
/* 1817 */               out.write("                success: function(response)\n                {                                                                \n                \t        var messageFromDiscoveryEngine;\n                \t       // var isInstanceAvailable=response.isInstanceAvailable;\n                           //   alert(isExceptionOccurred);\n                          //  if(isInstanceAvailable===true)\n                       \n                            messageFromDiscoveryEngine = response.logMessage;\n                            var totalRequests = response.totalDevices;\n                            var reqsReceived = response.noOfCompletedRequests;\n                            var devsAdded=response.noOfAddedDevices;\n                            var noOfAddedApplications=response.noOfAddedApplications;\n                             var esxCount=response.esxCount;\n                           var noOfAddedEsxHosts=response.noOfAddedEsxHosts;\n                           var reqReceivedForEsx=response.reqReceivedForEsx;\n                            discoveryType=response.discoveryType;\n");
/* 1818 */               out.write("                            if(previousMessage == messageFromDiscoveryEngine || messageFromDiscoveryEngine == undefined )\n                            {\n                               \taddToDeviceAdditionStatus(totalRequests,reqsReceived,devsAdded,noOfAddedApplications,messageFromDiscoveryEngine,esxCount,noOfAddedEsxHosts,reqReceivedForEsx);\n                                setTimeout(\"getDiscoveryStatusToShow(\"+discoveryID+\",'\"+messageFromDiscoveryEngine+\"')\",500); //No I18N\n                            }\n                           // else if(messageFromDiscoveryEngine == 'completed')\n                           else if( messageFromDiscoveryEngine==\"");
/* 1819 */               out.print(FormatUtil.getString("am.webclient.discovery.completed"));
/* 1820 */               out.write("\")\n                            {\n                            //\talert(messageFromDiscoveryEngine);\n                                addToLogStatusTable(messageFromDiscoveryEngine,devsAdded,discoveryID); //No I18N\n                                addToDeviceAdditionStatus(totalRequests,reqsReceived,devsAdded,noOfAddedApplications,messageFromDiscoveryEngine,esxCount,noOfAddedEsxHosts,reqReceivedForEsx);\n                                //show the discovery configured page.\n                            }\n                            else\n                            {\n                                addToLogStatusTable(messageFromDiscoveryEngine,devsAdded,discoveryID);\n                                addToDeviceAdditionStatus(totalRequests,reqsReceived,devsAdded,noOfAddedApplications,messageFromDiscoveryEngine,esxCount,noOfAddedEsxHosts,reqReceivedForEsx);\n                                setTimeout(\"getDiscoveryStatusToShow(\"+discoveryID+\",'\"+messageFromDiscoveryEngine+\"')\",500); //No I18N\n                            }                                                                                    \n");
/* 1821 */               out.write("                     \n                       /*    else\n                         {\n                        \t//   alert(\"canceled/deleted\");\n                        \t   messageFromDiscoveryEngine='Discovery Canceled or Deleted.';\n                        \t   addToLogStatusTable(messageFromDiscoveryEngine,0,discoveryID);                       \t   \n                         }*/\n                }\n        });\n}\n\nfunction addToDeviceAdditionStatus(totalRequests,reqsReceived,devsAdded,noOfAddedApplications,messageFromDiscoveryEngine,esxCount,noOfAddedEsxHosts,reqReceivedForEsx)\n{\n\t var copyTotalReq=\"-1\";\n   var masDetMsg=\"");
/* 1822 */               out.print(FormatUtil.getString("am.discovery.discoveryprocess.text"));
/* 1823 */               out.write("\";\n   if(totalRequests==undefined)\n\t   {\n\t   totalRequests=0;\n\t   }\n   else{\n\t   copyTotalReq=totalRequests;\n   }\n   if(devsAdded==undefined)\n   {\n\t   devsAdded=0;\n   }\n  var hostDiscoveryCompleted=\"false\";\n  var messageToDisplay=\"\";\n  \n  if(discoveryType=='virtual')\n\t   {\n\t  if(esxCount=='-1')\n\t\t  {\n\t\t  esxCount=\"0\";\n\t\t  }\n\t   messageToDisplay=\" ");
/* 1824 */               out.print(FormatUtil.getString("am.discovery.added.esxhosts.text"));
/* 1825 */               out.write("\"+noOfAddedEsxHosts+\"/\"+esxCount+\"<br/>\";//No I18N\n\t   messageToDisplay =messageToDisplay+ \"");
/* 1826 */               out.print(FormatUtil.getString("am.discovery.discoveryprocess.total.vm.text"));
/* 1827 */               out.write(" \"+totalRequests+\" &nbsp;&nbsp;&nbsp;&nbsp;");
/* 1828 */               out.print(FormatUtil.getString("am.discovery.discoveryprocess.added.vm.text"));
/* 1829 */               out.write("\"+devsAdded;\n\t   }\n  else{\n\t   messageToDisplay =messageToDisplay+ \"");
/* 1830 */               out.print(FormatUtil.getString("am.discovery.discoveryprocess.totalIPs.text"));
/* 1831 */               out.write(" \"+totalRequests+\" &nbsp;&nbsp;&nbsp;&nbsp;");
/* 1832 */               out.print(FormatUtil.getString("am.discovery.discoveryprocess.added.devices"));
/* 1833 */               out.write("\"+devsAdded+\"<br/>\"; \n       messageToDisplay =messageToDisplay+ \"");
/* 1834 */               out.print(FormatUtil.getString("am.discovery.discoveryprocess.added.applications"));
/* 1835 */               out.write(" \"+noOfAddedApplications;\n      \n\t  if(copyTotalReq=='0')\n\t  {\n\t     hostDiscoveryCompleted=\"true\";\n\t  }\n    }\n    if(totalRequests=='-1')\n\t   {\n\t   totalRequests=\"0\";\n\t   }\n    var dispname=$(\"#selectedMAS option:selected\").text();\n    var msgToDisplay=\"<p> \"+messageToDisplay+\" </p>\"; //No I18N\n     ");
/* 1836 */               if (_jspx_meth_c_005fif_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                 return;
/* 1838 */               out.write("\n     $('#deviceAdditionStatus').html(msgToDisplay);\n     if(hostDiscoveryCompleted==\"false\")\n\t {\n  var percent=0;\n  if(reqsReceived==-1)\n\t  {\n\t  percent=100;\n\t  }\n  else{\n    percent=Math.floor((reqsReceived/totalRequests)*100);\n  }\n       if(isNaN(percent))//If percent is NaN isNaN() returns true..\n\t {\n\t percent=0;\n\t }\n\t if((percent==100 && messageFromDiscoveryEngine!=\"");
/* 1839 */               out.print(FormatUtil.getString("am.webclient.discovery.completed"));
/* 1840 */               out.write("\") && (reqReceivedForEsx==esxCount))\n     {\n    \t $('#progressOuter').addClass('progressComplete');//No I18N\n       \t $('#waitMsgId').css(\"display\",'block'); //No I18N\n         $('#loadInnerId').css(\"display\",'block'); //No I18N\n     }\n     $('#progressSpan').html(percent+\"%\"); //No I18N\n     $('.progressStripe').css(\"width\",percent+'%'); //No I18N\n      }\n else{\n\t $('#progressOuter').addClass('progressComplete');//No I18N\n\t $('#waitMsgId').css(\"display\",'block'); //No I18N\n     $('#loadInnerId').css(\"display\",'block'); //No I18N\n\t $('#progressSpan').html(\"100%\"); //No I18N\n     $('.progressStripe').css(\"width\",'100%'); //No I18N\n }\n      }\n\nfunction addToLogStatusTable(statusMessage,devsAdded,discoveryID)\n{\n    var statusMessageToTable = \"<li>\"+statusMessage+\"</li>\"; //No I18N\n    $('#statusTable').prepend(statusMessageToTable);    \n    if(statusMessage==\"");
/* 1841 */               out.print(FormatUtil.getString("am.webclient.discovery.completed"));
/* 1842 */               out.write("\")\n    \t{\n    \t window.location.href=\"/newDiscoveryAction.do?method=discoveredDevicesTableView&devsAdded=\"+devsAdded+\"&discoveryID=\"+discoveryID;//No I18N\n    \t}\n   // else if(statusMessage=='Discovery Canceled or Deleted.')\n    //\t{\n      //  \t window.location.href=\"/newDiscoveryAction.do?method=getDiscoveryDetails\";//No I18N\n    //\t}\n}\n\nfunction getRequiredField(fieldName)\n{\n    if(fieldName == 'fromAddress')\n    {\n          if(($('#fromAddress1').val() == \"\") || ($('#fromAddress2').val() == \"\") ||($('#fromAddress3').val() == \"\") ||($('#fromAddress4').val() == \"\")  ) {\n            return -1;\n          }\n           return $('#fromAddress1').val()+\".\"+$('#fromAddress2').val()+\".\"+$('#fromAddress3').val()+\".\"+$('#fromAddress4').val();\n    }\n    if(fieldName == 'toAddress')\n    {\n      if(($('#toAddress1').val() == \"\") || ($('#toAddress2').val() == \"\") ||($('#toAddress3').val() == \"\") ||($('#toAddress4').val() == \"\") ) {\n             return -1;\n           }\n           return $('#toAddress1').val()+\".\"+$('#toAddress2').val()+\".\"+$('#toAddress3').val()+\".\"+$('#toAddress4').val();\n");
/* 1843 */               out.write("    }\n    if(fieldName == 'netWorkAddress')\n    {\n      if(($('#netWorkAddress1').val() == \"\") || ($('#netWorkAddress2').val() == \"\") ||($('#netWorkAddress3').val() == \"\") ||($('#netWorkAddress4').val() == \"\") ) {\n            return -1;\n          }\n            return $('#netWorkAddress1').val()+\".\"+$('#netWorkAddress2').val()+\".\"+$('#netWorkAddress3').val()+\".\"+$('#netWorkAddress4').val();\n    }\n}\n\nfunction showAllDefaultPorts(discoveryID)\n{\n\t//\talert(\"hiii\");\n    var arr=['TELNET','SSH','SNMP'];//No I18N\n        var cacheID = (new Date()).getTime();\n        var toGetDefaultPorts = \"&method=getDefaultPorts&cacheid=\"+cacheID+\"&discoveryID=\"+discoveryID;  //No I18N\n        $.ajax(\n        {\n                type:\"POST\", //No I18N\n                url:\"/newDiscoveryAction.do\", //No I18N\n                data:toGetDefaultPorts,\n                success: function(response)\n                {\n                    $('#defaultPortDetails').find(\"tr:gt(0)\").remove();\n                    $('#defaultappPortDetails').find(\"tr:gt(0)\").remove();\n");
/* 1844 */               out.write("                    for(var key in response)\n                    {\n                        if(response.hasOwnProperty(key))\n                        {\n                        \tvar portName = response[key].portName;\n                            var portValue = response[key].portValue;\n\t\t\t\t\t\t\tvar isEnabled = response[key].isEnabled;\n\t\t\t\t\t\t\tvar className=('true' == isEnabled )?\"Disable\":\"Enable\"; //No I18N  \n\t\t\t\t\t\t\tvar titleName=('Enable' == className )?'");
/* 1845 */               out.print(FormatUtil.getString("am.discovery.title.Enable"));
/* 1846 */               out.write(39);
/* 1847 */               out.write(58);
/* 1848 */               out.write(39);
/* 1849 */               out.print(FormatUtil.getString("am.discovery.title.Disable"));
/* 1850 */               out.write("';  //No I18N\n\t\t\t\t\t\t\tvar toAddRow=\"<tr class=\\\"icon\"+className+\" selectPorts\\\" id=\\\"\"+portName+\"\\\"> <td width=\\\"195\\\"><span>\"+portName+\"</span></td><td id=\\\"check\\\"><input type=\\\"text\\\" value=\\\"\"+portValue+\"\\\"><a title=\\\"\"+titleName+\"\\\" href=\\\"javascript:togglePort(\\'\"+portName+\"\\')\\\"></a></td></tr><tr> <td colspan=\\\"2\\\" height=\\\"12\\\"></td></tr>\";  //No I18N    \n\t\t\t\t\t\t\tvar tableToAdd=($.inArray(portName,arr) !== -1)?\"defaultPortDetails\":\"defaultappPortDetails\"; //No I18N\n\t\t\t\t\t\t\t$('#'+tableToAdd+' tr:last').after(toAddRow);\t\n                        }\n                    }\n                    \n                }\n        });       \n}\n\nfunction togglePort(idName)\n{\n\t$(\"tr[id='\"+idName+\"']\").toggleClass(\"iconDisable iconEnable\"); //No I18N  \n\t//$(\"tr[id='\"+idName+\"']\").toggleClass(\"iconDisable\");\n\t\n\tvar titleName=($(\"tr[id='\"+idName+\"']\").hasClass(\"iconEnable\"))?'");
/* 1851 */               out.print(FormatUtil.getString("am.discovery.title.Enable"));
/* 1852 */               out.write(39);
/* 1853 */               out.write(58);
/* 1854 */               out.write(39);
/* 1855 */               out.print(FormatUtil.getString("am.discovery.title.Disable"));
/* 1856 */               out.write("';  //No I18N\n\t\n\t$(\"tr[id='\"+idName+\"'] a\").prop('title',titleName); //No I18N  \n}\n\nfunction showLeftOutCredentials()\n{       \n\tif(($(\"#leftOutCredentialsULId li\").size()) != 0)\n\t  {\n    if($('input[name=showAllCred]').val() == '");
/* 1857 */               out.print(FormatUtil.getString("am.discovery.showall.credential"));
/* 1858 */               out.write("')\n\t{\n\t$('#leftOutCredentials').show();\n\t$('input[name=showAllCred]').val('");
/* 1859 */               out.print(FormatUtil.getString("am.discover.hidebelow.credential"));
/* 1860 */               out.write("');\n\t}\n\telse\n\t{\n\t$('#leftOutCredentials').hide();\n\t$('input[name=showAllCred]').val('");
/* 1861 */               out.print(FormatUtil.getString("am.discovery.showall.credential"));
/* 1862 */               out.write("');\n\t}\n}\n}\n\nfunction addToCredentialsSelectedList(credentialID,credentialName)\n{\n  var toAppendToCredentialsSelected = \"<li id=\\'\"+credentialID+\"\\'><span>\"+credentialName+\"<a class=\\\"iconClose\\\" href=\\\"javascript:removeCredentialAndAddToNotSelectedList(\\'\"+credentialID+\"\\',\\'\"+credentialName+\"\\')\\\">x</a></span></li>\";\n  $(\"#credentialsSelected\").append(toAppendToCredentialsSelected);\n  $(\"#leftOutCredentialsULId #\"+credentialID).remove();\n  if(($(\"#leftOutCredentialsULId li\").size()) == 0)\n  {\n   $('input[name=showAllCred]').val('");
/* 1863 */               out.print(FormatUtil.getString("am.discovery.showall.credential"));
/* 1864 */               out.write("'); \n  }\n  }\n       \n    \n\nfunction onSaveDialog(credentialID,credentialName)\n{\n  $(\"#addCredentialDialog\").dialog().dialog(\"close\");  //No I18N\n  addToCredentialsSelectedList(credentialID,credentialName);\n  loadCredential();\n}     \n\nfunction closeDialog()\n{\n   $(\"#addCredentialDialog\").dialog().dialog(\"close\");  //No I18N\n // document.getElementById(\"addCredentialDialog\").style.display= 'none';\n}     \n\nfunction showclass() \n{\n  $(\"#helpbox\").addClass(\"helpBoxAnim\");\n} \nfunction showclassPort() \n{\n  $(\"#helpBoxPort\").addClass(\"helpBoxAnim\");\n}   \nfunction addOrRemoveClass() {\n  $(\"#helpbox\").toggleClass(\"helpBoxAnim\"); //No I18N\n  $(\"#helpBoxPort\").toggleClass(\"helpBoxAnim\"); //No I18N\n}\n\n\nfunction changeAddressType(value){\n  if(value == 'range') {\n    $(\"#cidrAddress\").hide();\n    $(\"#fromAddress\").show();\n    $(\"#toAddress\").show();\n    $(\"#subnetmask\").show();\n\t$(\"#virtualAddress\").hide();\n\t$(\"#vmcheck\").hide();\n  }\n  else if(value == 'cidr')\n  {\n    $(\"#fromAddress\").hide();\n    $(\"#toAddress\").hide();\n    $(\"#subnetmask\").hide();\n");
/* 1865 */               out.write("    $(\"#cidrAddress\").show();\n\t$(\"#virtualAddress\").hide();\n\t$(\"#vmcheck\").hide();\n  }\nelse if(value == 'virtual') \n{\n\t$(\"#fromAddress\").hide();\n    $(\"#toAddress\").hide();\n    $(\"#subnetmask\").hide();\n    $(\"#cidrAddress\").hide();\n    $(\"#virtualAddress\").show();\n\t$(\"#subnetmask\").show();\t\n\t$(\"#vmcheck\").show();\n}\n\t\n\n}\n\n$(\"#defaultPortDetails\").change(function() {\n   // alert(\"change\");\n    getPortsForThisDiscovery();\n  });\n  \n  $(\"#defaultappPortDetails\").change(function() {\n   // alert(\"change\");\n    getPortsForThisDiscovery();\n  });\n  function loadCredential()\n  {\n   var dataString = \"&method=showCredentialByTypeName&operation=add&fromDiscovery=true\"; //No I18N\n  $.ajax({\n            type: \"POST\",//No I18N\n            url: \"/credentialManager.do\",//No I18N\n            data: dataString,\n            context: this,\n            success: function (response) {\n           $(\"#addCredInnerDialog\").html(response);\n           $(\"#heading\").hide();\n            }   \n        }); \n   }\n\n</script>\n\n");
/*      */               
/* 1867 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fonsubmit_005faction.get(FormTag.class);
/* 1868 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 1869 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 1871 */               _jspx_th_html_005fform_005f0.setAction("/newDiscoveryAction.do");
/*      */               
/* 1873 */               _jspx_th_html_005fform_005f0.setOnsubmit("return validate()");
/* 1874 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 1875 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/* 1877 */                   out.write("\n\n<body >\n<div class=\"bcsign breadcrumb_btm_space\">\n   <a href=\"/showTile.do?TileName=Tile.AdminConf\" class=\"bcinactive\">");
/* 1878 */                   out.print(FormatUtil.getString("am.webclient.tab.admin"));
/* 1879 */                   out.write(" </a> &gt;  ");
/* 1880 */                   out.write("\n   <a href=\"/newDiscoveryAction.do?method=getDiscoveryDetails\" class=\"bcinactive\">");
/* 1881 */                   out.print(FormatUtil.getString("am.webclient.networkdiscovery.networksdiscovery.text"));
/* 1882 */                   out.write("\n</a> &gt; ");
/* 1883 */                   out.write("\n   <span class=\"bcactive\">");
/* 1884 */                   out.print(FormatUtil.getString("am.webclient.networkdiscovery.addnewdiscovery.text"));
/* 1885 */                   out.write("\n</span> ");
/* 1886 */                   out.write("\n</div> \n<div id= \"discoveryFirstPage\" class=\"boxOuterCon\" style=\"display:block\">\n<!-- help box -->\n  <div id=\"helpbox\" class=\"helpBox\"> \n  <a href=\"#\" class=\"btnClose\">X</a> ");
/* 1887 */                   out.write("\n    <h4>");
/* 1888 */                   out.print(FormatUtil.getString("Network Details"));
/* 1889 */                   out.write("\n</h4> ");
/* 1890 */                   out.write("\n    <p>");
/* 1891 */                   out.print(FormatUtil.getString("am.webclient.discovery.discoveryname.tooltip"));
/* 1892 */                   out.write("\n</p> ");
/* 1893 */                   out.write("\n    <h4>");
/* 1894 */                   out.print(FormatUtil.getString("am.webclient.cluster.windows.credentialdetails"));
/* 1895 */                   out.write("\n</h4> ");
/* 1896 */                   out.write("\n    <p>");
/* 1897 */                   out.print(FormatUtil.getString("am.webclient.discovery.selectcredential.tooltip"));
/* 1898 */                   out.write("\n</p> ");
/* 1899 */                   out.write("\n  </div>\n  <!-- Discovery Details -->\n\n\n\n\n  <div class=\"boxCon\">\n \n    <div id=\"boxHead\" class=\"boxHead\">");
/* 1900 */                   out.print(FormatUtil.getString("webclient.topo.details.tabs.discovery.tooltip"));
/* 1901 */                   out.write(" <i class=\"iconHelpNote\" id=\"addOrRemove\"></i></div>");
/* 1902 */                   out.write("\n  \n    <div class=\"conInner\">\n      <div class=\"form-group\">\n        <label for=\"inputName\" class=\"control-label\">");
/* 1903 */                   out.print(FormatUtil.getString("am.webclient.hometab.monitorgroups.columnheader.name"));
/* 1904 */                   out.write("&nbsp;<span class=\"mandatory\">*</span></label>");
/* 1905 */                   out.write("\n        <div class=\"inputBlock\">\n          <input type=\"text\" class=\"form-control\" name=\"discoveryName\" placeholder=\"");
/* 1906 */                   out.print(FormatUtil.getString("am.webclient.hometab.monitorgroups.columnheader.name"));
/* 1907 */                   out.write("\" value=\"");
/* 1908 */                   if (_jspx_meth_c_005fout_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1910 */                   out.write("\">\n          <div id=\"discoverydisplay\"></div>\n        </div>\n      </div>\n      <div class=\"form-group\">\n        <label class=\"control-label\">");
/* 1911 */                   out.print(FormatUtil.getString("am.webclient.discovery.credentiallist"));
/* 1912 */                   out.write("\n</label> ");
/* 1913 */                   out.write("\n        <div class=\"radio\">\n          <label  class=\"blockContent\" onclick=\"javascript:showCredentialList('false')\">\n            <input type=\"radio\" name=\"credentialDetailsSelected\" id=\"inlineRadio3\" value=\"allCredentials\" ");
/* 1914 */                   if (_jspx_meth_c_005fif_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1916 */                   out.write("> \n            <span>\n            ");
/* 1917 */                   out.print(FormatUtil.getString("am.webclient.discovery.credentiallist.selectall"));
/* 1918 */                   out.write("\n</span> </label> ");
/* 1919 */                   out.write("\n          <label  class=\"blockContent\" onclick=\"javascript:showCredentialList('true')\">\n            <input type=\"radio\" name=\"credentialDetailsSelected\" id=\"inlineRadio4\" value=\"selectCredentials\" ");
/* 1920 */                   if (_jspx_meth_c_005fif_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1922 */                   out.write(">\n           <span> ");
/* 1923 */                   out.print(FormatUtil.getString("am.webclient.discovery.credentiallist.useselected"));
/* 1924 */                   out.write("\n</span></label> ");
/* 1925 */                   out.write("\n        </div>\n    \n      </div>\n    \n      <div class=\"form-group\">\n        <label class=\"control-label\"></label>\n        <div class=\"inputBlock portBlock\">\n          <div class=\"credentialBox\">\n          <div id=\"credentialBox\">\n      <div id=\"credentialDetailsDiv\" class=\"checkList credentialDetails\" style=\"display:block;\">\n\n                            <ul id=\"credentialsSelected\">\n                            ");
/* 1926 */                   if (_jspx_meth_c_005fforEach_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1928 */                   out.write("\n                            </ul>\n    </div>\n            <div> <!-- port will be coming here--> </div>\n            <input name=\"showAllCred\" type=\"button\" class=\"btn\" onclick=\"javascript:showLeftOutCredentials()\" value=\"");
/* 1929 */                   out.print(FormatUtil.getString("am.discovery.showall.credential"));
/* 1930 */                   out.write("\">\n        <input type=\"button\" id=\"addCred\" class=\"btn\" value=\"");
/* 1931 */                   out.print(FormatUtil.getString("am.webclient.credentialManager.addCredential"));
/* 1932 */                   out.write("\">\n      <div id=\"leftOutCredentials\" class=\"monitorContent\" style=\"display:none;\">\n    <ul id=\"leftOutCredentialsULId\">\n                            ");
/* 1933 */                   if (_jspx_meth_c_005fforEach_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1935 */                   out.write("\n   </ul>\n   \n  </div>\n          </div>\n      </div>\n           \n        </div>\n         </div>          \n                   \n    <div id=\"discoveryInputDiv\">\n      <div class=\"form-group\">\n        <label for=\"inputName\" class=\"control-label\">");
/* 1936 */                   out.print(FormatUtil.getString("am.discover.discovery.type"));
/* 1937 */                   out.write("</label>  ");
/* 1938 */                   out.write("\n        <div class=\"radio\">\n          <label>\n            <input type=\"radio\" onclick=\"changeAddressType('range')\" name=\"discoverytype\" id=\"range\" value=\"range\" ");
/* 1939 */                   if (_jspx_meth_c_005fif_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1941 */                   out.write(">\n            ");
/* 1942 */                   out.print(FormatUtil.getString("am.webclient.discovery.discoverytype.range"));
/* 1943 */                   out.write(" </label> ");
/* 1944 */                   out.write("\n          <label>\n            <input type=\"radio\" onclick=\"changeAddressType('cidr')\" name=\"discoverytype\" id=\"cidr\" value=\"cidr\" ");
/* 1945 */                   if (_jspx_meth_c_005fif_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1947 */                   out.write(">\n            ");
/* 1948 */                   out.print(FormatUtil.getString("am.webclient.discovery.discoverytype.cidr"));
/* 1949 */                   out.write(" </label> ");
/* 1950 */                   out.write("\n\t\t  <label>\n            <input type=\"radio\" onclick=\"changeAddressType('virtual')\" name=\"discoverytype\" id=\"virtual\" value=\"virtual\" ");
/* 1951 */                   if (_jspx_meth_c_005fif_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1953 */                   out.write(62);
/* 1954 */                   out.print(FormatUtil.getString("am.webclient.discovery.discoverytype.virtualization"));
/* 1955 */                   out.write(" </label> ");
/* 1956 */                   out.write("\n        </div>\n      </div>\n      <div id=\"fromAddress\" class=\"form-group\">\n        <label class=\"control-label\">");
/* 1957 */                   out.print(FormatUtil.getString("am.webclient.newaction.fromaddress"));
/* 1958 */                   out.write("</label> ");
/* 1959 */                   out.write("\n        <div class=\"inputBlock inputMulti\">\n       \n          <input id=\"fromAddress1\" maxlength=\"3\" type=\"text\" onblur=\"return inputLimit('fromAddress1')\" onkeydown=\"return checkAndShow('fromAddress1','fromAddress2',event)\" class=\"form-control\" value=");
/* 1960 */                   if (_jspx_meth_c_005fout_005f19(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1962 */                   out.write(" >\n                            <input id=\"fromAddress2\" maxlength=\"3\" type=\"text\" onblur=\"return inputLimit('fromAddress2')\" onkeydown=\"return checkAndShow('fromAddress2','fromAddress3',event)\" class=\"form-control\" value=");
/* 1963 */                   if (_jspx_meth_c_005fout_005f20(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1965 */                   out.write(" >\n                            <input id=\"fromAddress3\" maxlength=\"3\" type=\"text\" onblur=\"return inputLimit('fromAddress3')\" onkeydown=\"return checkAndShow('fromAddress3','fromAddress4',event)\" class=\"form-control\" value=");
/* 1966 */                   if (_jspx_meth_c_005fout_005f21(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1968 */                   out.write(" >\n                            <input id=\"fromAddress4\" maxlength=\"3\" type=\"text\" onblur=\"return inputLimit('fromAddress4')\" onkeydown=\"return checkAndShow('fromAddress4','toAddress1',event)\" class=\"form-control\" value=");
/* 1969 */                   if (_jspx_meth_c_005fout_005f22(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1971 */                   out.write(" >\n\n\n        </div>\n      </div>\n      <div id=\"toAddress\" class=\"form-group\">\n        <label class=\"control-label\">");
/* 1972 */                   out.print(FormatUtil.getString("am.webclient.newaction.toaddress"));
/* 1973 */                   out.write("</label> ");
/* 1974 */                   out.write("\n        <div class=\"inputBlock inputMulti\">\n          <input id=\"toAddress1\" maxlength=\"3\" type=\"text\" onblur=\"return inputLimit('toAddress1')\" onkeydown=\"return checkAndShow('toAddress1','toAddress2',event)\" class=\"form-control\" value=");
/* 1975 */                   if (_jspx_meth_c_005fout_005f23(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1977 */                   out.write(" >\n                            <input id=\"toAddress2\" maxlength=\"3\" type=\"text\" onblur=\"return inputLimit('toAddress2')\" onkeydown=\"return checkAndShow('toAddress2','toAddress3',event)\" class=\"form-control\" value=");
/* 1978 */                   if (_jspx_meth_c_005fout_005f24(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1980 */                   out.write(" >\n                            <input id=\"toAddress3\" maxlength=\"3\" type=\"text\" onblur=\"return inputLimit('toAddress3')\" onkeydown=\"return checkAndShow('toAddress3','toAddress4',event)\" class=\"form-control\" value=");
/* 1981 */                   if (_jspx_meth_c_005fout_005f25(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1983 */                   out.write(" >\n                            <input id=\"toAddress4\" maxlength=\"3\" type=\"text\" onblur=\"return inputLimit('toAddress4')\" onkeydown=\"return checkAndShow('toAddress4','toAddress4',event)\" class=\"form-control\" value=");
/* 1984 */                   if (_jspx_meth_c_005fout_005f26(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1986 */                   out.write(" >\n        </div>\n      </div>\n\n      \n      \n      <div id=\"cidrAddress\" class=\"form-group\">\n      <label for=\"NetWorkAddress\" class=\"control-label\">");
/* 1987 */                   out.print(FormatUtil.getString("am.webclient.discovery.networkaddress"));
/* 1988 */                   out.write("</label> ");
/* 1989 */                   out.write("\n      <div id=\"netWorkAddress\" class=\"inputBlock inputMulti\">\n                            <input id=\"netWorkAddress1\" maxlength=\"3\" type=\"text\" onblur=\"return inputLimit('netWorkAddress1')\" onkeydown=\"return checkAndShow('netWorkAddress1','netWorkAddress2',event)\" class=\"form-control\" value=");
/* 1990 */                   if (_jspx_meth_c_005fout_005f27(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1992 */                   out.write(" >\n                            <input id=\"netWorkAddress2\" maxlength=\"3\" type=\"text\" onblur=\"return inputLimit('netWorkAddress2')\"  onkeydown=\"return checkAndShow('netWorkAddress2','netWorkAddress3',event)\"  class=\"form-control\" value=");
/* 1993 */                   if (_jspx_meth_c_005fout_005f28(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1995 */                   out.write(" >\n                            <input id=\"netWorkAddress3\" maxlength=\"3\" type=\"text\" onblur=\"return inputLimit('netWorkAddress3')\" onkeydown=\"return checkAndShow('netWorkAddress3','netWorkAddress4',event)\"  class=\"form-control\" value=");
/* 1996 */                   if (_jspx_meth_c_005fout_005f29(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 1998 */                   out.write(" >\n                            <input id=\"netWorkAddress4\" maxlength=\"3\" type=\"text\" onblur=\"return inputLimit('netWorkAddress4')\" onkeydown=\"return checkAndShow('netWorkAddress4','netWorkAddress4',event)\"  class=\"form-control\" value=");
/* 1999 */                   if (_jspx_meth_c_005fout_005f30(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2001 */                   out.write(" >\n                            <span style=\"float: left; margin: 8px 10px 0px 5px; font-size: 20px;\" class=\"slash\">/</span>\n\n            \n      \n      <select class=\"\"  name=\"cidr\" ></select>\n      \n      \n      </div>\n      </div>\n      <div id=\"virtualAddress\" style=\"display: block;\">\n  <div class=\"form-group\">\n    <label class=\"control-label\" for=\"VirtualHostAddress\">");
/* 2002 */                   out.print(FormatUtil.getString("am.webclient.discovery.virtualhostname"));
/* 2003 */                   out.write("</label> ");
/* 2004 */                   out.write("\n    <div class=\"inputBlock\">\n      <input type=\"text\" name=\"virtualHost\" class=\"form-control\" value=");
/* 2005 */                   if (_jspx_meth_c_005fout_005f31(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2007 */                   out.write(">\n    </div>\n  </div>\n  <div class=\"form-group\">\n    <label class=\"control-label\" for=\"Port\">");
/* 2008 */                   out.print(FormatUtil.getString("am.webclient.discovery.port"));
/* 2009 */                   out.write("</label> ");
/* 2010 */                   out.write("\n    <div class=\"inputBlock\">\n      <input type=\"text\" name=\"virtualPort\" class=\"form-control\" onkeydown=\"return checkForNumber(event)\" value=");
/* 2011 */                   if (_jspx_meth_c_005fout_005f32(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2013 */                   out.write(">\n    </div>\n  </div>\n  <div class=\"form-group\">\n    <label class=\"control-label\" for=\"UserName\" class=\"form-control\">");
/* 2014 */                   out.print(FormatUtil.getString("am.webclient.discovery.username"));
/* 2015 */                   out.write("</label> ");
/* 2016 */                   out.write("\n    <div class=\"inputBlock\">\n      <input type=\"text\" name=\"virtualUserName\" class=\"form-control\" value=");
/* 2017 */                   if (_jspx_meth_c_005fout_005f33(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2019 */                   out.write(">\n    </div>\n  </div>\n  <div class=\"form-group\">\n    <label class=\"control-label\" for=\"Password\" class=\"form-control\">");
/* 2020 */                   out.print(FormatUtil.getString("am.webclient.discovery.password"));
/* 2021 */                   out.write("</label> ");
/* 2022 */                   out.write("\n    <div class=\"inputBlock\">\n      <input type=\"password\" name=\"virtualPassword\" class=\"form-control\" >\n    </div>\n  </div>\n  \n</div>\n<div id=\"subnetmask\" class=\"form-group\">\n        <label for=\"SubNet\" class=\"control-label\">");
/* 2023 */                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.subnetmask"));
/* 2024 */                   out.write("</label> ");
/* 2025 */                   out.write("\n        <div>\n          <span>\n                            <select name=\"netMask\" class=\" chzn-select\">\n                                ");
/* 2026 */                   if (_jspx_meth_c_005fforEach_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/* 2028 */                   out.write("\n\n                            </select>\n          </span>\n        </div>\n\t\t</div>\n      <div id=\"vmcheck\" class=\"form-group\">\n\t\t<label for=\"vmCheck\" class=\"control-label\" class=\"form-control\"> ");
/* 2029 */                   out.print(FormatUtil.getString("am.webclient.discovery.create.virtual.infrastructure.view"));
/* 2030 */                   out.write("</label>\n\t\t<div class=\"checkbox offset\"><input type=\"checkbox\" name=\"createMGview\" id=\"createMGview\" value=\"Yes\"></div>\n\t   </div>\n       ");
/*      */                   
/* 2032 */                   IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2033 */                   _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 2034 */                   _jspx_th_c_005fif_005f11.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/* 2036 */                   _jspx_th_c_005fif_005f11.setTest("${isAdminServer== 'true'}");
/* 2037 */                   int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 2038 */                   if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                     for (;;) {
/* 2040 */                       out.write("\n                    <div  class=\"form-group\">\n                     <label class=\"control-label\">");
/* 2041 */                       out.print(FormatUtil.getString("am.webclient.admin.addmonitor.mas.heading.text"));
/* 2042 */                       out.write(" </label> ");
/* 2043 */                       out.write("\n                     <div class=\"inputBlock\">\n          <span>\n    <select name=\"selectedMAS\" id=\"selectedMAS\" class=\"chzn-select\" onChange=\"javascript:showDeviceCountDiv('true')\" >\n                    <option value=0>");
/* 2044 */                       out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.value.mas.text"));
/* 2045 */                       out.write(" </option> ");
/* 2046 */                       out.write("\n                               ");
/* 2047 */                       if (_jspx_meth_c_005fforEach_005f4(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                         return;
/* 2049 */                       out.write("\n                   \n                      </select>\n                      </span></div>\n                   \n                     </div>\n                     <label class=\"control-label\"></label>\n                  <div id=\"devCountDiv\" style=\"display:block\" class=\"inputFields\">\n                 <span id=\"devCountSpan\" class=\"infoText\"></span>\n                 </div>\n                 \n                  ");
/* 2050 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 2051 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2055 */                   if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 2056 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                   }
/*      */                   
/* 2059 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 2060 */                   out.write("\n       \n      \n      </div>\n      </div>\n      </div>\n<!-- dialog credentials -->\n<div id=\"addCredentialDialog\" class=\"dialogCustom hidden\" >\n    <h2 class=\"dialogHeading\">");
/* 2061 */                   out.print(FormatUtil.getString("am.webclient.credentialManager.addCredential"));
/* 2062 */                   out.write("</h2> ");
/* 2063 */                   out.write("\n    <a href=\"#\" id=\"closeDialog\" class=\"btnClose\">X</a> ");
/* 2064 */                   out.write("\n  <div id=\"addCredInnerDialog\"></div>\n  \n \n</div>\n\n\n<!-- help box -->\n<div id=\"helpBoxPort\" class=\"helpBox\"> <a href=\"#\" class=\"btnClose\">X</a> ");
/* 2065 */                   out.write("\n    <h4>");
/* 2066 */                   out.print(FormatUtil.getString("webclient.topo.details.tabs.port.tooltip"));
/* 2067 */                   out.write("</h4> ");
/* 2068 */                   out.write("\n    <p>");
/* 2069 */                   out.print(FormatUtil.getString("am.webclient.discovery.porthelp"));
/* 2070 */                   out.write("</p> ");
/* 2071 */                   out.write("\n   </div>\n   <!-- Port Selection -->\n<div class=\"boxCon\">\n  <div class=\"boxHead\">");
/* 2072 */                   out.print(FormatUtil.getString("am.webclient.discovery.heading.portselection"));
/* 2073 */                   out.write("<i class=\"iconHelpNote\" id=\"addOrRemovePort\"></i></div> ");
/* 2074 */                   out.write("\n  <div class=\"conInner\">\n    <div class=\"form-group\">\n      <label class=\"control-label\">");
/* 2075 */                   out.print(FormatUtil.getString("webclient.topo.details.tabs.port.tooltip"));
/* 2076 */                   out.write("</label> ");
/* 2077 */                   out.write("\n      <div class=\"inputBlock\">\n        <div> <a href=\"#\" id=\"configurePort\" class=\"linkPort\">");
/* 2078 */                   out.print(FormatUtil.getString("am.webclient.discovery.configureport"));
/* 2079 */                   out.write("</a> </div> ");
/* 2080 */                   out.write("\n      </div>\n    </div>\n  </div>\n</div>\n\n<!-- Dialog configure port -->\n<div id=\"dialogPort\" class=\"dialogPort dialogCustom\">\n    <h2 class=\"dialogHeading\">");
/* 2081 */                   out.print(FormatUtil.getString("am.webclient.discovery.configureport"));
/* 2082 */                   out.write("</h2> ");
/* 2083 */                   out.write("\n    <a href=\"#\" id=\"closePortDialog\" class=\"btnClose\">X</a> ");
/* 2084 */                   out.write("\n    \n <div class=\"popupCon\">\n\n<div class=\"form-group headPopCon\">\n        <span class=\"headingPop\">");
/* 2085 */                   out.print(FormatUtil.getString("am.webclient.configurealert.type"));
/* 2086 */                   out.write("</span> ");
/* 2087 */                   out.write("\n        <div class=\"inputBlock\">\n  <span class=\"headingPop\">");
/* 2088 */                   out.print(FormatUtil.getString("am.webclient.discovery.defaultport"));
/* 2089 */                   out.write("</span> ");
/* 2090 */                   out.write("\n        </div>\n        </div>\n      <div class=\"form-group configCon\">\n      <div><span class=\"subHeading\">");
/* 2091 */                   out.print(FormatUtil.getString("am.mypage.complextype.servers.text"));
/* 2092 */                   out.write("</span></div>\n        <table id=\"defaultPortDetails\" width=\"100%\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n<tbody class=\"portTable\">\n          <tr>             \n          </tr>\n</tbody>\n</table>\n        \n<div><span class=\"subHeading\">");
/* 2093 */                   out.print(FormatUtil.getString("it360.search.facet.apm.applications"));
/* 2094 */                   out.write("</span></div>\n<table id=\"defaultappPortDetails\" width=\"100%\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n<tbody class=\"portTable\">\n          <tr>             \n          </tr>\n</tbody>\n</table>\n        \n      </div>\n</div>\n</div>\n\n\n<div class=\"form-group\">\n  <div class=\"inputBlock inputBlockOffset\">\n    <input name=\"discover\" type=\"button\" class=\"btn\" value=\"");
/* 2095 */                   out.print(FormatUtil.getString("am.discovery.discover"));
/* 2096 */                   out.write("\" onclick=\"javascript:showDiscoveryStatus()\">\n    <input name=\"cancel\" class=\"btn\" value=\"");
/* 2097 */                   out.print(FormatUtil.getString("am.discovery.cancel"));
/* 2098 */                   out.write("\" type=\"button\" onclick=\"javascript:window.location.href='newDiscoveryAction.do?method=getDiscoveryDetails'\">\n  </div>\n</div>\n\n</div>\n<div id=\"discoverySecondPage\" style=\"display:none\">\n\n <div class=\"progressOuter\" id=\"progressOuter\"><img src=\"images/progress.gif\"/><span id=\"progressSpan\"></span>\n            \n            <div class=\"progressStripeOuter\">\n            <div class=\"progressStripe\" style=\"\">\n            <i>");
/* 2099 */                   out.print(FormatUtil.getString("am.webclient.discovery.completed.msg"));
/* 2100 */                   out.write("</i>");
/* 2101 */                   out.write("\n            </div>\n            </div>\n            <p id=\"waitMsgId\" style=\"display:none\">");
/* 2102 */                   out.print(FormatUtil.getString("am.webclient.discovery.completed.waitmsg"));
/* 2103 */                   out.write("</p>");
/* 2104 */                   out.write("\n           </div> \n       \n    <div class=\"nodeAddedMsg\"> \n    <!-- loader -->\n     <div id=\"loadInnerId\" class=\"loaderInner\" style=\"display:none\">\n    <div class=\"barStripe\"></div>\n  </div>\n   \n    <div id=\"deviceAdditionStatus\" class=\"deviceStatus\">   </div>\n    \n    <ul id=\"statusTable\">  </ul>\n   \n    </div>\n  <!-- discovery cancel button removed.. -->\n   </div>\n\n\n<!-- <script type=\"text/javascript\" src=\"/template/jquery-1.11.0.min.js\"></script>  -->\n<script type=\"text/javascript\" src=\"/template/jquery.jscrollpane.min.js\"></script>\n<script type=\"text/javascript\" src=\"/template/jquery.mousewheel.js\"></script>\n\n<script>\n// dialog credentials\n\n\n$(function() {\n\n\n$(\"#addCred\").click(function () {\n\n  \n                              $(\"#addCredentialDialog\").dialog({\n                                  modal   : true, \n                                  height  : 530, \n                                  width   : 500,\n                                  show    : {effect: 'drop', direction: \"left\", duration: 500},       //No I18N\n");
/* 2105 */                   out.write("                                  hide    : {effect: 'drop', direction: \"right\", duration: 500},       //No I18N\n                                  dialogClass : 'dialogCustom'                //No I18N\n                              });\n                          \n                        $(\"#closeDialog\").click(function(e) {\n                            $(\"#addCredentialDialog\").dialog('close');                        //No I18N\n                            e.preventDefault(); \n                        });\n            });\n\t \n  });\n\n\n\n\n// dialog configure port\n  $(function() {\n  \n    $(\"#configurePort\").click(function (e) {\n      \n      $(\"#dialogPort\").dialog({\n                                  modal   : true, \n                                  height  : 400, \n                                  width   : 500,\n                show    : {effect: 'drop', direction: \"left\", duration: 500},       //No I18N\n                hide    : {effect: 'drop', direction: \"right\", duration: 500},       //No I18N\n                                  dialogClass : 'dialogCustom dialogPort'               //No I18N\n");
/* 2106 */                   out.write("                              });\n            e.preventDefault();\n      });\n\n      $(\"#closePortDialog\").click(function(e) {\n                            $(\"#dialogPort\").dialog('close');                       //No I18N\n          e.preventDefault();\n      });\n                        });\njQuery(document).ready(function(e) {\n                        /* jscroll pane*/ \n  //  $('.scroll-pane').jScrollPane();\n            \n  });\n  \n  \n\n \n</script>\n</body>\n  ");
/* 2107 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2108 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2112 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2113 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fonsubmit_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */               }
/*      */               
/* 2116 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fonsubmit_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2117 */               out.write(10);
/* 2118 */               out.write(10);
/* 2119 */               out.write(10);
/* 2120 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 2121 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2124 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2125 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2128 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 2129 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 2132 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 2133 */           out.write(32);
/* 2134 */           out.write(10);
/* 2135 */           out.write(32);
/* 2136 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2138 */           out.write(10);
/* 2139 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2140 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2144 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2145 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 2148 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2149 */         out.write(10);
/*      */       }
/* 2151 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2152 */         out = _jspx_out;
/* 2153 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2154 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2155 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2158 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2164 */     PageContext pageContext = _jspx_page_context;
/* 2165 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2167 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2168 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2169 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2171 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 2173 */     _jspx_th_tiles_005fput_005f0.setValue("Add Network");
/* 2174 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2175 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2176 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2177 */       return true;
/*      */     }
/* 2179 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2180 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2185 */     PageContext pageContext = _jspx_page_context;
/* 2186 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2188 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2189 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2190 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2192 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 2194 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=3");
/* 2195 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2196 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2197 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2198 */       return true;
/*      */     }
/* 2200 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2201 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f16(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2206 */     PageContext pageContext = _jspx_page_context;
/* 2207 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2209 */     ChooseTag _jspx_th_c_005fchoose_005f16 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2210 */     _jspx_th_c_005fchoose_005f16.setPageContext(_jspx_page_context);
/* 2211 */     _jspx_th_c_005fchoose_005f16.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 2212 */     int _jspx_eval_c_005fchoose_005f16 = _jspx_th_c_005fchoose_005f16.doStartTag();
/* 2213 */     if (_jspx_eval_c_005fchoose_005f16 != 0) {
/*      */       for (;;) {
/* 2215 */         out.write("\n     ");
/* 2216 */         if (_jspx_meth_c_005fwhen_005f16(_jspx_th_c_005fchoose_005f16, _jspx_page_context))
/* 2217 */           return true;
/* 2218 */         out.write("\n     ");
/* 2219 */         if (_jspx_meth_c_005fotherwise_005f16(_jspx_th_c_005fchoose_005f16, _jspx_page_context))
/* 2220 */           return true;
/* 2221 */         out.write("\n\n     ");
/* 2222 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f16.doAfterBody();
/* 2223 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2227 */     if (_jspx_th_c_005fchoose_005f16.doEndTag() == 5) {
/* 2228 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 2229 */       return true;
/*      */     }
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 2232 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f16(JspTag _jspx_th_c_005fchoose_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2237 */     PageContext pageContext = _jspx_page_context;
/* 2238 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2240 */     WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2241 */     _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 2242 */     _jspx_th_c_005fwhen_005f16.setParent((Tag)_jspx_th_c_005fchoose_005f16);
/*      */     
/* 2244 */     _jspx_th_c_005fwhen_005f16.setTest("${rediscovery == 'true'}");
/* 2245 */     int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 2246 */     if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */       for (;;) {
/* 2248 */         out.write("\n          $('#discoveryFirstPage').css(\"display\",'none'); //No I18N\n          $('#discoverySecondPage').css(\"display\",'block'); //No I18N\n          startReDiscovery();\n     ");
/* 2249 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 2250 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2254 */     if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 2255 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 2256 */       return true;
/*      */     }
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 2259 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f16(JspTag _jspx_th_c_005fchoose_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2264 */     PageContext pageContext = _jspx_page_context;
/* 2265 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2267 */     OtherwiseTag _jspx_th_c_005fotherwise_005f16 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2268 */     _jspx_th_c_005fotherwise_005f16.setPageContext(_jspx_page_context);
/* 2269 */     _jspx_th_c_005fotherwise_005f16.setParent((Tag)_jspx_th_c_005fchoose_005f16);
/* 2270 */     int _jspx_eval_c_005fotherwise_005f16 = _jspx_th_c_005fotherwise_005f16.doStartTag();
/* 2271 */     if (_jspx_eval_c_005fotherwise_005f16 != 0) {
/*      */       for (;;) {
/* 2273 */         out.write("  \n\t  $('.chzn-select').chosen();\n      showDeviceCountDiv('false');\n      setCidrRange('24');\n\t   ");
/* 2274 */         if (_jspx_meth_c_005fchoose_005f17(_jspx_th_c_005fotherwise_005f16, _jspx_page_context))
/* 2275 */           return true;
/* 2276 */         out.write("\n\t      // help box \n     $('#helpbox .btnClose').click(function(e){\n       $('#helpbox').addClass(\"helpBoxAnim\");\n       $('#addOrRemove').addClass('arrowChange'); \n       e.preventDefault();\n\t\t});\n\n\t  $('#helpBoxPort .btnClose').click(function(e){\n\t\t   $('#helpBoxPort').addClass(\"helpBoxAnim\");\n\t\t   $('#addOrRemovePort').addClass('arrowChange'); \n\t\t   e.preventDefault();\n\t  });\n\n\t  $('#addOrRemove').click(function(e){\n\t\t  $(\"#helpbox\").toggleClass(\"helpBoxAnim\"); //No I18N\n\t\t  $(this).toggleClass(\"arrowChange\"); //No I18N\n\t  });\n\n\t  $('#addOrRemovePort').click(function(e){\n\t\t  $(\"#helpBoxPort\").toggleClass(\"helpBoxAnim\"); //No I18N\n\t\t  $(this).toggleClass(\"arrowChange\"); //No I18N\n\t  }); \n\n\tloadCredential();\n    ");
/* 2277 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f16.doAfterBody();
/* 2278 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2282 */     if (_jspx_th_c_005fotherwise_005f16.doEndTag() == 5) {
/* 2283 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 2284 */       return true;
/*      */     }
/* 2286 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 2287 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f17(JspTag _jspx_th_c_005fotherwise_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2292 */     PageContext pageContext = _jspx_page_context;
/* 2293 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2295 */     ChooseTag _jspx_th_c_005fchoose_005f17 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2296 */     _jspx_th_c_005fchoose_005f17.setPageContext(_jspx_page_context);
/* 2297 */     _jspx_th_c_005fchoose_005f17.setParent((Tag)_jspx_th_c_005fotherwise_005f16);
/* 2298 */     int _jspx_eval_c_005fchoose_005f17 = _jspx_th_c_005fchoose_005f17.doStartTag();
/* 2299 */     if (_jspx_eval_c_005fchoose_005f17 != 0) {
/*      */       for (;;) {
/* 2301 */         out.write("\n\t\t  ");
/* 2302 */         if (_jspx_meth_c_005fwhen_005f17(_jspx_th_c_005fchoose_005f17, _jspx_page_context))
/* 2303 */           return true;
/* 2304 */         out.write("\n\t\t  ");
/* 2305 */         if (_jspx_meth_c_005fotherwise_005f17(_jspx_th_c_005fchoose_005f17, _jspx_page_context))
/* 2306 */           return true;
/* 2307 */         out.write("\n\n     ");
/* 2308 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f17.doAfterBody();
/* 2309 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2313 */     if (_jspx_th_c_005fchoose_005f17.doEndTag() == 5) {
/* 2314 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 2315 */       return true;
/*      */     }
/* 2317 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 2318 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f17(JspTag _jspx_th_c_005fchoose_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2323 */     PageContext pageContext = _jspx_page_context;
/* 2324 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2326 */     WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2327 */     _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 2328 */     _jspx_th_c_005fwhen_005f17.setParent((Tag)_jspx_th_c_005fchoose_005f17);
/*      */     
/* 2330 */     _jspx_th_c_005fwhen_005f17.setTest("${actionTodo == 'edit'}");
/* 2331 */     int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 2332 */     if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */       for (;;) {
/* 2334 */         out.write("\n\t\t\t  var discoveryID=\"");
/* 2335 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f17, _jspx_page_context))
/* 2336 */           return true;
/* 2337 */         out.write("\";\n\t\t\t  showAllDefaultPorts(discoveryID);\n\t\t\t  performEditActions();\n\t\t  ");
/* 2338 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 2339 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2343 */     if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 2344 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 2345 */       return true;
/*      */     }
/* 2347 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 2348 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fwhen_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2353 */     PageContext pageContext = _jspx_page_context;
/* 2354 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2356 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2357 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2358 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f17);
/*      */     
/* 2360 */     _jspx_th_c_005fout_005f0.setValue("${discoveryID}");
/* 2361 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2362 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2363 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2364 */       return true;
/*      */     }
/* 2366 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2367 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f17(JspTag _jspx_th_c_005fchoose_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2372 */     PageContext pageContext = _jspx_page_context;
/* 2373 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2375 */     OtherwiseTag _jspx_th_c_005fotherwise_005f17 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2376 */     _jspx_th_c_005fotherwise_005f17.setPageContext(_jspx_page_context);
/* 2377 */     _jspx_th_c_005fotherwise_005f17.setParent((Tag)_jspx_th_c_005fchoose_005f17);
/* 2378 */     int _jspx_eval_c_005fotherwise_005f17 = _jspx_th_c_005fotherwise_005f17.doStartTag();
/* 2379 */     if (_jspx_eval_c_005fotherwise_005f17 != 0) {
/*      */       for (;;) {
/* 2381 */         out.write("\n\t\t\t   $('input[name=discoveryName]').attr(\"disabled\", false); //No I18N\n\t\t\t   $('input:radio[name=discoverytype]')[0].checked = true;\n\t\t\t   showCredentialList('true');\n\t\t\t   showAllDefaultPorts('-1');\n\t\t\t   changeAddressType('range'); //No I18N\n\t\t\t   checkDiscName();\n\t\t  ");
/* 2382 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f17.doAfterBody();
/* 2383 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2387 */     if (_jspx_th_c_005fotherwise_005f17.doEndTag() == 5) {
/* 2388 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 2389 */       return true;
/*      */     }
/* 2391 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 2392 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2397 */     PageContext pageContext = _jspx_page_context;
/* 2398 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2400 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2401 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2402 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2404 */     _jspx_th_c_005fout_005f1.setValue("${credentialDetailsSelected}");
/* 2405 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2406 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2407 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2408 */       return true;
/*      */     }
/* 2410 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2411 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2416 */     PageContext pageContext = _jspx_page_context;
/* 2417 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2419 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2420 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2421 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2423 */     _jspx_th_c_005fout_005f2.setValue("${discoveryType}");
/* 2424 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2425 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2426 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2427 */       return true;
/*      */     }
/* 2429 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2430 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2435 */     PageContext pageContext = _jspx_page_context;
/* 2436 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2438 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2439 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 2440 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2442 */     _jspx_th_c_005fout_005f3.setValue("${cidr}");
/* 2443 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 2444 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 2445 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2446 */       return true;
/*      */     }
/* 2448 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 2449 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2454 */     PageContext pageContext = _jspx_page_context;
/* 2455 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2457 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2458 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2459 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2461 */     _jspx_th_c_005fout_005f4.setValue("${createMGView}");
/* 2462 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2463 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2464 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2465 */       return true;
/*      */     }
/* 2467 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2468 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2473 */     PageContext pageContext = _jspx_page_context;
/* 2474 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2476 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 2477 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 2478 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2480 */     _jspx_th_c_005fforEach_005f0.setItems("${moCountMap}");
/*      */     
/* 2482 */     _jspx_th_c_005fforEach_005f0.setVar("entry");
/* 2483 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 2485 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 2486 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 2488 */           out.write(" \n       masIDfromMap=\"");
/* 2489 */           boolean bool; if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2490 */             return true;
/* 2491 */           out.write("\";\n       if(masIDfromMap==selectedVal)\n    \t   {\n    \t   moCountFromMap=\"");
/* 2492 */           if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 2493 */             return true;
/* 2494 */           out.write("\";\n    \t   }\n    ");
/* 2495 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 2496 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2500 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 2501 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2504 */         int tmp233_232 = 0; int[] tmp233_230 = _jspx_push_body_count_c_005fforEach_005f0; int tmp235_234 = tmp233_230[tmp233_232];tmp233_230[tmp233_232] = (tmp235_234 - 1); if (tmp235_234 <= 0) break;
/* 2505 */         out = _jspx_page_context.popBody(); }
/* 2506 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 2508 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2509 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 2511 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2516 */     PageContext pageContext = _jspx_page_context;
/* 2517 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2519 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2520 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2521 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2523 */     _jspx_th_c_005fout_005f5.setValue("${entry.key}");
/* 2524 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2525 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2526 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2527 */       return true;
/*      */     }
/* 2529 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2530 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 2535 */     PageContext pageContext = _jspx_page_context;
/* 2536 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2538 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2539 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2540 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 2542 */     _jspx_th_c_005fout_005f6.setValue("${entry.value}");
/* 2543 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2544 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2545 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2546 */       return true;
/*      */     }
/* 2548 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2554 */     PageContext pageContext = _jspx_page_context;
/* 2555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2557 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2558 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2559 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2561 */     _jspx_th_c_005fout_005f7.setValue("${devCount}");
/* 2562 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2563 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2564 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2565 */       return true;
/*      */     }
/* 2567 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2568 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2573 */     PageContext pageContext = _jspx_page_context;
/* 2574 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2576 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2577 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 2578 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2580 */     _jspx_th_c_005fout_005f8.setValue("${discoveryID}");
/* 2581 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 2582 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 2583 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2584 */       return true;
/*      */     }
/* 2586 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2587 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2592 */     PageContext pageContext = _jspx_page_context;
/* 2593 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2595 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2596 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2597 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2599 */     _jspx_th_c_005fif_005f2.setTest("${isAdminServer== 'true'}");
/* 2600 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2601 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 2603 */         out.write("\n    \t   var masID=$('select[name=selectedMAS]').val();//No I18N\n\t");
/* 2604 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2605 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2609 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2610 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2611 */       return true;
/*      */     }
/* 2613 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2614 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2619 */     PageContext pageContext = _jspx_page_context;
/* 2620 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2622 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2623 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2624 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2626 */     _jspx_th_c_005fif_005f3.setTest("${isAdminServer== 'true'}");
/* 2627 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2628 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 2630 */         out.write("\n        dataString =dataString+\"&masID=\"+masID; //No I18N   \n    ");
/* 2631 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2632 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2636 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2637 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2638 */       return true;
/*      */     }
/* 2640 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2646 */     PageContext pageContext = _jspx_page_context;
/* 2647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2649 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2650 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2651 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2653 */     _jspx_th_c_005fif_005f4.setTest("${actionTodo == 'edit'}");
/* 2654 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2655 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 2657 */         out.write("\n\t//edit=\"true\";\n\tdiscCateg=\"edit\";//No I18N\n\tvar discoveryID=\"");
/* 2658 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 2659 */           return true;
/* 2660 */         out.write("\";\n\tdataString = dataString+\"&discoveryID=\"+discoveryID;//No I18N\n");
/* 2661 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2662 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2666 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2667 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2668 */       return true;
/*      */     }
/* 2670 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2671 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2676 */     PageContext pageContext = _jspx_page_context;
/* 2677 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2679 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2680 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 2681 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 2683 */     _jspx_th_c_005fout_005f9.setValue("${discoveryID}");
/* 2684 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 2685 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 2686 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2687 */       return true;
/*      */     }
/* 2689 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2690 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2695 */     PageContext pageContext = _jspx_page_context;
/* 2696 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2698 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2699 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2700 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 2702 */     _jspx_th_c_005fif_005f5.setTest("${isAdminServer== 'true'}");
/* 2703 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2704 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 2706 */         out.write("\n     msgToDisplay=\"<p> \"+masDetMsg+\" \"+dispname+\"<br/>\"+messageToDisplay+\" </p>\" //No I18N\n     ");
/* 2707 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2708 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2712 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2713 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2714 */       return true;
/*      */     }
/* 2716 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2717 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2722 */     PageContext pageContext = _jspx_page_context;
/* 2723 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2725 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2726 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 2727 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2729 */     _jspx_th_c_005fout_005f10.setValue("${discoveryName}");
/* 2730 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 2731 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 2732 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2733 */       return true;
/*      */     }
/* 2735 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2736 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2741 */     PageContext pageContext = _jspx_page_context;
/* 2742 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2744 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2745 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2746 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2748 */     _jspx_th_c_005fif_005f6.setTest("${credentialDetailsSelected == 'allCredentials'}");
/* 2749 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2750 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 2752 */         out.write("CHECKED");
/* 2753 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2754 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2758 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2759 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2760 */       return true;
/*      */     }
/* 2762 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2763 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2768 */     PageContext pageContext = _jspx_page_context;
/* 2769 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2771 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2772 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2773 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2775 */     _jspx_th_c_005fif_005f7.setTest("${credentialDetailsSelected == 'selectCredentials'}");
/* 2776 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2777 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 2779 */         out.write("CHECKED");
/* 2780 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2781 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2785 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2786 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2787 */       return true;
/*      */     }
/* 2789 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2790 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2795 */     PageContext pageContext = _jspx_page_context;
/* 2796 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2798 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2799 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 2800 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2802 */     _jspx_th_c_005fforEach_005f1.setVar("prop");
/*      */     
/* 2804 */     _jspx_th_c_005fforEach_005f1.setItems("${list}");
/*      */     
/* 2806 */     _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 2807 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 2809 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 2810 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 2812 */           out.write("                            \n                             <li id='");
/* 2813 */           boolean bool; if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2814 */             return true;
/* 2815 */           out.write("'>\n                                            <span>");
/* 2816 */           if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2817 */             return true;
/* 2818 */           out.write("<a class=\"iconClose\" href=\"javascript:removeCredentialAndAddToNotSelectedList('");
/* 2819 */           if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2820 */             return true;
/* 2821 */           out.write(39);
/* 2822 */           out.write(44);
/* 2823 */           out.write(39);
/* 2824 */           if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 2825 */             return true;
/* 2826 */           out.write("')\">x</a></span> ");
/* 2827 */           out.write(" \n                             </li>\n                             ");
/* 2828 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 2829 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2833 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 2834 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2837 */         int tmp342_341 = 0; int[] tmp342_339 = _jspx_push_body_count_c_005fforEach_005f1; int tmp344_343 = tmp342_339[tmp342_341];tmp342_339[tmp342_341] = (tmp344_343 - 1); if (tmp344_343 <= 0) break;
/* 2838 */         out = _jspx_page_context.popBody(); }
/* 2839 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 2841 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 2842 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 2844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2849 */     PageContext pageContext = _jspx_page_context;
/* 2850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2852 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2853 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 2854 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 2856 */     _jspx_th_c_005fout_005f11.setValue("${prop.credentialID}");
/* 2857 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 2858 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 2859 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2860 */       return true;
/*      */     }
/* 2862 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2863 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2868 */     PageContext pageContext = _jspx_page_context;
/* 2869 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2871 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2872 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 2873 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 2875 */     _jspx_th_c_005fout_005f12.setValue("${prop.credentialName}");
/* 2876 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 2877 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 2878 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2879 */       return true;
/*      */     }
/* 2881 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2887 */     PageContext pageContext = _jspx_page_context;
/* 2888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2890 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2891 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 2892 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 2894 */     _jspx_th_c_005fout_005f13.setValue("${prop.credentialID}");
/* 2895 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 2896 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 2897 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2898 */       return true;
/*      */     }
/* 2900 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2901 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 2906 */     PageContext pageContext = _jspx_page_context;
/* 2907 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2909 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2910 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 2911 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 2913 */     _jspx_th_c_005fout_005f14.setValue("${prop.credentialName}");
/* 2914 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 2915 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 2916 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2917 */       return true;
/*      */     }
/* 2919 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2920 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2925 */     PageContext pageContext = _jspx_page_context;
/* 2926 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2928 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2929 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 2930 */     _jspx_th_c_005fforEach_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 2932 */     _jspx_th_c_005fforEach_005f2.setVar("prop");
/*      */     
/* 2934 */     _jspx_th_c_005fforEach_005f2.setItems("${notPresentList}");
/*      */     
/* 2936 */     _jspx_th_c_005fforEach_005f2.setVarStatus("status");
/* 2937 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 2939 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 2940 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 2942 */           out.write("\n                             <li id='");
/* 2943 */           boolean bool; if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 2944 */             return true;
/* 2945 */           out.write("'>\n                                            <span>");
/* 2946 */           if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 2947 */             return true;
/* 2948 */           out.write("<a class=\"iconClose iconTick\" href=\"javascript:addToCredentialsSelectedList('");
/* 2949 */           if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 2950 */             return true;
/* 2951 */           out.write(39);
/* 2952 */           out.write(44);
/* 2953 */           out.write(39);
/* 2954 */           if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 2955 */             return true;
/* 2956 */           out.write("')\"><img src=\"../images/icon_tick.png\"></a></span>  ");
/* 2957 */           out.write(" \n                             </li>\n                             ");
/* 2958 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 2959 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2963 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 2964 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2967 */         int tmp342_341 = 0; int[] tmp342_339 = _jspx_push_body_count_c_005fforEach_005f2; int tmp344_343 = tmp342_339[tmp342_341];tmp342_339[tmp342_341] = (tmp344_343 - 1); if (tmp344_343 <= 0) break;
/* 2968 */         out = _jspx_page_context.popBody(); }
/* 2969 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 2971 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 2972 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 2974 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 2979 */     PageContext pageContext = _jspx_page_context;
/* 2980 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2982 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2983 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 2984 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 2986 */     _jspx_th_c_005fout_005f15.setValue("${prop.credentialID}");
/* 2987 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 2988 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 2989 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 2990 */       return true;
/*      */     }
/* 2992 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 2993 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 2998 */     PageContext pageContext = _jspx_page_context;
/* 2999 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3001 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3002 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 3003 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 3005 */     _jspx_th_c_005fout_005f16.setValue("${prop.credentialName}");
/* 3006 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 3007 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 3008 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 3009 */       return true;
/*      */     }
/* 3011 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 3012 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 3017 */     PageContext pageContext = _jspx_page_context;
/* 3018 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3020 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3021 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 3022 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 3024 */     _jspx_th_c_005fout_005f17.setValue("${prop.credentialID}");
/* 3025 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 3026 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 3027 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 3028 */       return true;
/*      */     }
/* 3030 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 3031 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 3036 */     PageContext pageContext = _jspx_page_context;
/* 3037 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3039 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3040 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 3041 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 3043 */     _jspx_th_c_005fout_005f18.setValue("${prop.credentialName}");
/* 3044 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 3045 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 3046 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 3047 */       return true;
/*      */     }
/* 3049 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 3050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3055 */     PageContext pageContext = _jspx_page_context;
/* 3056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3058 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3059 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3060 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3062 */     _jspx_th_c_005fif_005f8.setTest("${discoveryType == 'range'}");
/* 3063 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3064 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 3066 */         out.write("CHECKED");
/* 3067 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3068 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3072 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3073 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3074 */       return true;
/*      */     }
/* 3076 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3077 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3082 */     PageContext pageContext = _jspx_page_context;
/* 3083 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3085 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3086 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3087 */     _jspx_th_c_005fif_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3089 */     _jspx_th_c_005fif_005f9.setTest("${discoveryType == 'cidr'}");
/* 3090 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3091 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 3093 */         out.write("CHECKED");
/* 3094 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3095 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3099 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3100 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3101 */       return true;
/*      */     }
/* 3103 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3104 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3109 */     PageContext pageContext = _jspx_page_context;
/* 3110 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3112 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3113 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3114 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3116 */     _jspx_th_c_005fif_005f10.setTest("${discoveryType == 'virtual'}");
/* 3117 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3118 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 3120 */         out.write("CHECKED");
/* 3121 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3122 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3126 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3127 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3128 */       return true;
/*      */     }
/* 3130 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3131 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3136 */     PageContext pageContext = _jspx_page_context;
/* 3137 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3139 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3140 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 3141 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3143 */     _jspx_th_c_005fout_005f19.setValue("${fromAddress1}");
/* 3144 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 3145 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 3146 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 3147 */       return true;
/*      */     }
/* 3149 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 3150 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3155 */     PageContext pageContext = _jspx_page_context;
/* 3156 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3158 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3159 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 3160 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3162 */     _jspx_th_c_005fout_005f20.setValue("${fromAddress2}");
/* 3163 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 3164 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 3165 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 3166 */       return true;
/*      */     }
/* 3168 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 3169 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3174 */     PageContext pageContext = _jspx_page_context;
/* 3175 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3177 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3178 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 3179 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3181 */     _jspx_th_c_005fout_005f21.setValue("${fromAddress3}");
/* 3182 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 3183 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 3184 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 3185 */       return true;
/*      */     }
/* 3187 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 3188 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3193 */     PageContext pageContext = _jspx_page_context;
/* 3194 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3196 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3197 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 3198 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3200 */     _jspx_th_c_005fout_005f22.setValue("${fromAddress4}");
/* 3201 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 3202 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 3203 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 3204 */       return true;
/*      */     }
/* 3206 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 3207 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3212 */     PageContext pageContext = _jspx_page_context;
/* 3213 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3215 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3216 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 3217 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3219 */     _jspx_th_c_005fout_005f23.setValue("${toAddress1}");
/* 3220 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 3221 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 3222 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 3223 */       return true;
/*      */     }
/* 3225 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 3226 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3231 */     PageContext pageContext = _jspx_page_context;
/* 3232 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3234 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3235 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 3236 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3238 */     _jspx_th_c_005fout_005f24.setValue("${toAddress2}");
/* 3239 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 3240 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 3241 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 3242 */       return true;
/*      */     }
/* 3244 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 3245 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3250 */     PageContext pageContext = _jspx_page_context;
/* 3251 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3253 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3254 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 3255 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3257 */     _jspx_th_c_005fout_005f25.setValue("${toAddress3}");
/* 3258 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 3259 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 3260 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 3261 */       return true;
/*      */     }
/* 3263 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 3264 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3269 */     PageContext pageContext = _jspx_page_context;
/* 3270 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3272 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3273 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 3274 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3276 */     _jspx_th_c_005fout_005f26.setValue("${toAddress4}");
/* 3277 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 3278 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 3279 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 3280 */       return true;
/*      */     }
/* 3282 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 3283 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3288 */     PageContext pageContext = _jspx_page_context;
/* 3289 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3291 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3292 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 3293 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3295 */     _jspx_th_c_005fout_005f27.setValue("${netWorkAddress1}");
/* 3296 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 3297 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 3298 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 3299 */       return true;
/*      */     }
/* 3301 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 3302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3307 */     PageContext pageContext = _jspx_page_context;
/* 3308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3310 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3311 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 3312 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3314 */     _jspx_th_c_005fout_005f28.setValue("${netWorkAddress2}");
/* 3315 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 3316 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 3317 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 3318 */       return true;
/*      */     }
/* 3320 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 3321 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3326 */     PageContext pageContext = _jspx_page_context;
/* 3327 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3329 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3330 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 3331 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3333 */     _jspx_th_c_005fout_005f29.setValue("${netWorkAddress3}");
/* 3334 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 3335 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 3336 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 3337 */       return true;
/*      */     }
/* 3339 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 3340 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3345 */     PageContext pageContext = _jspx_page_context;
/* 3346 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3348 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3349 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 3350 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3352 */     _jspx_th_c_005fout_005f30.setValue("${netWorkAddress4}");
/* 3353 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 3354 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 3355 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 3356 */       return true;
/*      */     }
/* 3358 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 3359 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3364 */     PageContext pageContext = _jspx_page_context;
/* 3365 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3367 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3368 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 3369 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3371 */     _jspx_th_c_005fout_005f31.setValue("${virtualHost}");
/* 3372 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 3373 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 3374 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 3375 */       return true;
/*      */     }
/* 3377 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 3378 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3383 */     PageContext pageContext = _jspx_page_context;
/* 3384 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3386 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3387 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 3388 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3390 */     _jspx_th_c_005fout_005f32.setValue("${virtualPort}");
/* 3391 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 3392 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 3393 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 3394 */       return true;
/*      */     }
/* 3396 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 3397 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3402 */     PageContext pageContext = _jspx_page_context;
/* 3403 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3405 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3406 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 3407 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3409 */     _jspx_th_c_005fout_005f33.setValue("${virtualUserName}");
/* 3410 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 3411 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 3412 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 3413 */       return true;
/*      */     }
/* 3415 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 3416 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3421 */     PageContext pageContext = _jspx_page_context;
/* 3422 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3424 */     ForEachTag _jspx_th_c_005fforEach_005f3 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3425 */     _jspx_th_c_005fforEach_005f3.setPageContext(_jspx_page_context);
/* 3426 */     _jspx_th_c_005fforEach_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3428 */     _jspx_th_c_005fforEach_005f3.setVar("netmask");
/*      */     
/* 3430 */     _jspx_th_c_005fforEach_005f3.setItems("${netMaskList}");
/*      */     
/* 3432 */     _jspx_th_c_005fforEach_005f3.setVarStatus("status");
/* 3433 */     int[] _jspx_push_body_count_c_005fforEach_005f3 = { 0 };
/*      */     try {
/* 3435 */       int _jspx_eval_c_005fforEach_005f3 = _jspx_th_c_005fforEach_005f3.doStartTag();
/* 3436 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f3 != 0) {
/*      */         for (;;) {
/* 3438 */           out.write("\n                                    ");
/* 3439 */           if (_jspx_meth_c_005fchoose_005f18(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 3440 */             return true;
/* 3441 */           out.write("\n                                ");
/* 3442 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f3.doAfterBody();
/* 3443 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3447 */       if (_jspx_th_c_005fforEach_005f3.doEndTag() == 5)
/* 3448 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3451 */         int tmp201_200 = 0; int[] tmp201_198 = _jspx_push_body_count_c_005fforEach_005f3; int tmp203_202 = tmp201_198[tmp201_200];tmp201_198[tmp201_200] = (tmp203_202 - 1); if (tmp203_202 <= 0) break;
/* 3452 */         out = _jspx_page_context.popBody(); }
/* 3453 */       _jspx_th_c_005fforEach_005f3.doCatch(_jspx_exception);
/*      */     } finally {
/* 3455 */       _jspx_th_c_005fforEach_005f3.doFinally();
/* 3456 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f3);
/*      */     }
/* 3458 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f18(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 3463 */     PageContext pageContext = _jspx_page_context;
/* 3464 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3466 */     ChooseTag _jspx_th_c_005fchoose_005f18 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3467 */     _jspx_th_c_005fchoose_005f18.setPageContext(_jspx_page_context);
/* 3468 */     _jspx_th_c_005fchoose_005f18.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/* 3469 */     int _jspx_eval_c_005fchoose_005f18 = _jspx_th_c_005fchoose_005f18.doStartTag();
/* 3470 */     if (_jspx_eval_c_005fchoose_005f18 != 0) {
/*      */       for (;;) {
/* 3472 */         out.write("\n                                        ");
/* 3473 */         if (_jspx_meth_c_005fwhen_005f18(_jspx_th_c_005fchoose_005f18, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 3474 */           return true;
/* 3475 */         out.write("\n                                    ");
/* 3476 */         if (_jspx_meth_c_005fotherwise_005f18(_jspx_th_c_005fchoose_005f18, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 3477 */           return true;
/* 3478 */         out.write("\n                                    ");
/* 3479 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f18.doAfterBody();
/* 3480 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3484 */     if (_jspx_th_c_005fchoose_005f18.doEndTag() == 5) {
/* 3485 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18);
/* 3486 */       return true;
/*      */     }
/* 3488 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18);
/* 3489 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f18(JspTag _jspx_th_c_005fchoose_005f18, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 3494 */     PageContext pageContext = _jspx_page_context;
/* 3495 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3497 */     WhenTag _jspx_th_c_005fwhen_005f18 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3498 */     _jspx_th_c_005fwhen_005f18.setPageContext(_jspx_page_context);
/* 3499 */     _jspx_th_c_005fwhen_005f18.setParent((Tag)_jspx_th_c_005fchoose_005f18);
/*      */     
/* 3501 */     _jspx_th_c_005fwhen_005f18.setTest("${netmask.selected == 'selected'}");
/* 3502 */     int _jspx_eval_c_005fwhen_005f18 = _jspx_th_c_005fwhen_005f18.doStartTag();
/* 3503 */     if (_jspx_eval_c_005fwhen_005f18 != 0) {
/*      */       for (;;) {
/* 3505 */         out.write("\n                                                <option value=\"");
/* 3506 */         if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fwhen_005f18, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 3507 */           return true;
/* 3508 */         out.write("\" selected=\"selected\" >");
/* 3509 */         if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fwhen_005f18, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 3510 */           return true;
/* 3511 */         out.write("</option>\n                                        ");
/* 3512 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f18.doAfterBody();
/* 3513 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3517 */     if (_jspx_th_c_005fwhen_005f18.doEndTag() == 5) {
/* 3518 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 3519 */       return true;
/*      */     }
/* 3521 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 3522 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fwhen_005f18, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 3527 */     PageContext pageContext = _jspx_page_context;
/* 3528 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3530 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3531 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 3532 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fwhen_005f18);
/*      */     
/* 3534 */     _jspx_th_c_005fout_005f34.setValue("${netmask.netMaskValue}");
/* 3535 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 3536 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 3537 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 3538 */       return true;
/*      */     }
/* 3540 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 3541 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fwhen_005f18, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 3546 */     PageContext pageContext = _jspx_page_context;
/* 3547 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3549 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3550 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 3551 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fwhen_005f18);
/*      */     
/* 3553 */     _jspx_th_c_005fout_005f35.setValue("${netmask.netMaskValue}");
/* 3554 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 3555 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 3556 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 3557 */       return true;
/*      */     }
/* 3559 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 3560 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f18(JspTag _jspx_th_c_005fchoose_005f18, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 3565 */     PageContext pageContext = _jspx_page_context;
/* 3566 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3568 */     OtherwiseTag _jspx_th_c_005fotherwise_005f18 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3569 */     _jspx_th_c_005fotherwise_005f18.setPageContext(_jspx_page_context);
/* 3570 */     _jspx_th_c_005fotherwise_005f18.setParent((Tag)_jspx_th_c_005fchoose_005f18);
/* 3571 */     int _jspx_eval_c_005fotherwise_005f18 = _jspx_th_c_005fotherwise_005f18.doStartTag();
/* 3572 */     if (_jspx_eval_c_005fotherwise_005f18 != 0) {
/*      */       for (;;) {
/* 3574 */         out.write("\n                                         <option value=\"");
/* 3575 */         if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fotherwise_005f18, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 3576 */           return true;
/* 3577 */         out.write(34);
/* 3578 */         out.write(62);
/* 3579 */         if (_jspx_meth_c_005fout_005f37(_jspx_th_c_005fotherwise_005f18, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 3580 */           return true;
/* 3581 */         out.write("</option>\n                                    ");
/* 3582 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f18.doAfterBody();
/* 3583 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3587 */     if (_jspx_th_c_005fotherwise_005f18.doEndTag() == 5) {
/* 3588 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18);
/* 3589 */       return true;
/*      */     }
/* 3591 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18);
/* 3592 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fotherwise_005f18, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 3597 */     PageContext pageContext = _jspx_page_context;
/* 3598 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3600 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3601 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 3602 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fotherwise_005f18);
/*      */     
/* 3604 */     _jspx_th_c_005fout_005f36.setValue("${netmask.netMaskValue}");
/* 3605 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 3606 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 3607 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 3608 */       return true;
/*      */     }
/* 3610 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 3611 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_c_005fotherwise_005f18, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 3616 */     PageContext pageContext = _jspx_page_context;
/* 3617 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3619 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3620 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 3621 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_c_005fotherwise_005f18);
/*      */     
/* 3623 */     _jspx_th_c_005fout_005f37.setValue("${netmask.netMaskValue}");
/* 3624 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 3625 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 3626 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 3627 */       return true;
/*      */     }
/* 3629 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 3630 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f4(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3635 */     PageContext pageContext = _jspx_page_context;
/* 3636 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3638 */     ForEachTag _jspx_th_c_005fforEach_005f4 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3639 */     _jspx_th_c_005fforEach_005f4.setPageContext(_jspx_page_context);
/* 3640 */     _jspx_th_c_005fforEach_005f4.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 3642 */     _jspx_th_c_005fforEach_005f4.setVar("entry");
/*      */     
/* 3644 */     _jspx_th_c_005fforEach_005f4.setItems("${masDetailsList}");
/*      */     
/* 3646 */     _jspx_th_c_005fforEach_005f4.setVarStatus("status");
/* 3647 */     int[] _jspx_push_body_count_c_005fforEach_005f4 = { 0 };
/*      */     try {
/* 3649 */       int _jspx_eval_c_005fforEach_005f4 = _jspx_th_c_005fforEach_005f4.doStartTag();
/* 3650 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f4 != 0) {
/*      */         for (;;) {
/* 3652 */           out.write("\n                             <!--       <option value=\"");
/* 3653 */           boolean bool; if (_jspx_meth_c_005fout_005f38(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3654 */             return true;
/* 3655 */           out.write("\" selected=\"");
/* 3656 */           if (_jspx_meth_c_005fout_005f39(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3657 */             return true;
/* 3658 */           out.write(34);
/* 3659 */           out.write(32);
/* 3660 */           out.write(62);
/* 3661 */           if (_jspx_meth_c_005fout_005f40(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3662 */             return true;
/* 3663 */           out.write(32);
/* 3664 */           if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3665 */             return true;
/* 3666 */           out.write(40);
/* 3667 */           if (_jspx_meth_c_005fout_005f42(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3668 */             return true;
/* 3669 */           out.write(")</option> ");
/* 3670 */           out.write(" -->\n                                 \n                                  ");
/* 3671 */           if (_jspx_meth_c_005fchoose_005f19(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3672 */             return true;
/* 3673 */           out.write("      \n                                ");
/* 3674 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f4.doAfterBody();
/* 3675 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3679 */       if (_jspx_th_c_005fforEach_005f4.doEndTag() == 5)
/* 3680 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3683 */         int tmp420_419 = 0; int[] tmp420_417 = _jspx_push_body_count_c_005fforEach_005f4; int tmp422_421 = tmp420_417[tmp420_419];tmp420_417[tmp420_419] = (tmp422_421 - 1); if (tmp422_421 <= 0) break;
/* 3684 */         out = _jspx_page_context.popBody(); }
/* 3685 */       _jspx_th_c_005fforEach_005f4.doCatch(_jspx_exception);
/*      */     } finally {
/* 3687 */       _jspx_th_c_005fforEach_005f4.doFinally();
/* 3688 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4);
/*      */     }
/* 3690 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3695 */     PageContext pageContext = _jspx_page_context;
/* 3696 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3698 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3699 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 3700 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 3702 */     _jspx_th_c_005fout_005f38.setValue("${entry.SERVERID}");
/* 3703 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 3704 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 3705 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 3706 */       return true;
/*      */     }
/* 3708 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 3709 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3714 */     PageContext pageContext = _jspx_page_context;
/* 3715 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3717 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3718 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 3719 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 3721 */     _jspx_th_c_005fout_005f39.setValue("${entry.SELECTED}");
/* 3722 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 3723 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 3724 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 3725 */       return true;
/*      */     }
/* 3727 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 3728 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3733 */     PageContext pageContext = _jspx_page_context;
/* 3734 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3736 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3737 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 3738 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 3740 */     _jspx_th_c_005fout_005f40.setValue("${entry.SERVERID}");
/* 3741 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 3742 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 3743 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 3744 */       return true;
/*      */     }
/* 3746 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 3747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3752 */     PageContext pageContext = _jspx_page_context;
/* 3753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3755 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3756 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 3757 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 3759 */     _jspx_th_c_005fout_005f41.setValue("${entry.HOST}");
/* 3760 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 3761 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 3762 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 3763 */       return true;
/*      */     }
/* 3765 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 3766 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3771 */     PageContext pageContext = _jspx_page_context;
/* 3772 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3774 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3775 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 3776 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 3778 */     _jspx_th_c_005fout_005f42.setValue("${entry.LOADFACTOR}");
/* 3779 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 3780 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 3781 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 3782 */       return true;
/*      */     }
/* 3784 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 3785 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f19(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3790 */     PageContext pageContext = _jspx_page_context;
/* 3791 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3793 */     ChooseTag _jspx_th_c_005fchoose_005f19 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3794 */     _jspx_th_c_005fchoose_005f19.setPageContext(_jspx_page_context);
/* 3795 */     _jspx_th_c_005fchoose_005f19.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/* 3796 */     int _jspx_eval_c_005fchoose_005f19 = _jspx_th_c_005fchoose_005f19.doStartTag();
/* 3797 */     if (_jspx_eval_c_005fchoose_005f19 != 0) {
/*      */       for (;;) {
/* 3799 */         out.write("\n                                        ");
/* 3800 */         if (_jspx_meth_c_005fwhen_005f19(_jspx_th_c_005fchoose_005f19, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3801 */           return true;
/* 3802 */         out.write("\n                                    ");
/* 3803 */         if (_jspx_meth_c_005fotherwise_005f19(_jspx_th_c_005fchoose_005f19, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3804 */           return true;
/* 3805 */         out.write("\n                                    ");
/* 3806 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f19.doAfterBody();
/* 3807 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3811 */     if (_jspx_th_c_005fchoose_005f19.doEndTag() == 5) {
/* 3812 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19);
/* 3813 */       return true;
/*      */     }
/* 3815 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19);
/* 3816 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f19(JspTag _jspx_th_c_005fchoose_005f19, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3821 */     PageContext pageContext = _jspx_page_context;
/* 3822 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3824 */     WhenTag _jspx_th_c_005fwhen_005f19 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3825 */     _jspx_th_c_005fwhen_005f19.setPageContext(_jspx_page_context);
/* 3826 */     _jspx_th_c_005fwhen_005f19.setParent((Tag)_jspx_th_c_005fchoose_005f19);
/*      */     
/* 3828 */     _jspx_th_c_005fwhen_005f19.setTest("${entry.SELECTED == 'selected'}");
/* 3829 */     int _jspx_eval_c_005fwhen_005f19 = _jspx_th_c_005fwhen_005f19.doStartTag();
/* 3830 */     if (_jspx_eval_c_005fwhen_005f19 != 0) {
/*      */       for (;;) {
/* 3832 */         out.write("\n                                                <option value=\"");
/* 3833 */         if (_jspx_meth_c_005fout_005f43(_jspx_th_c_005fwhen_005f19, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3834 */           return true;
/* 3835 */         out.write("\" selected=\"selected\" > ");
/* 3836 */         if (_jspx_meth_c_005fout_005f44(_jspx_th_c_005fwhen_005f19, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3837 */           return true;
/* 3838 */         out.write("</option> ");
/* 3839 */         out.write("\n                                        ");
/* 3840 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f19.doAfterBody();
/* 3841 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3845 */     if (_jspx_th_c_005fwhen_005f19.doEndTag() == 5) {
/* 3846 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 3847 */       return true;
/*      */     }
/* 3849 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 3850 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f43(JspTag _jspx_th_c_005fwhen_005f19, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3855 */     PageContext pageContext = _jspx_page_context;
/* 3856 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3858 */     OutTag _jspx_th_c_005fout_005f43 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3859 */     _jspx_th_c_005fout_005f43.setPageContext(_jspx_page_context);
/* 3860 */     _jspx_th_c_005fout_005f43.setParent((Tag)_jspx_th_c_005fwhen_005f19);
/*      */     
/* 3862 */     _jspx_th_c_005fout_005f43.setValue("${entry.SERVERID}");
/* 3863 */     int _jspx_eval_c_005fout_005f43 = _jspx_th_c_005fout_005f43.doStartTag();
/* 3864 */     if (_jspx_th_c_005fout_005f43.doEndTag() == 5) {
/* 3865 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 3866 */       return true;
/*      */     }
/* 3868 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f43);
/* 3869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f44(JspTag _jspx_th_c_005fwhen_005f19, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3874 */     PageContext pageContext = _jspx_page_context;
/* 3875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3877 */     OutTag _jspx_th_c_005fout_005f44 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3878 */     _jspx_th_c_005fout_005f44.setPageContext(_jspx_page_context);
/* 3879 */     _jspx_th_c_005fout_005f44.setParent((Tag)_jspx_th_c_005fwhen_005f19);
/*      */     
/* 3881 */     _jspx_th_c_005fout_005f44.setValue("${entry.HOST}");
/* 3882 */     int _jspx_eval_c_005fout_005f44 = _jspx_th_c_005fout_005f44.doStartTag();
/* 3883 */     if (_jspx_th_c_005fout_005f44.doEndTag() == 5) {
/* 3884 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 3885 */       return true;
/*      */     }
/* 3887 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f44);
/* 3888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f19(JspTag _jspx_th_c_005fchoose_005f19, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3893 */     PageContext pageContext = _jspx_page_context;
/* 3894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3896 */     OtherwiseTag _jspx_th_c_005fotherwise_005f19 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3897 */     _jspx_th_c_005fotherwise_005f19.setPageContext(_jspx_page_context);
/* 3898 */     _jspx_th_c_005fotherwise_005f19.setParent((Tag)_jspx_th_c_005fchoose_005f19);
/* 3899 */     int _jspx_eval_c_005fotherwise_005f19 = _jspx_th_c_005fotherwise_005f19.doStartTag();
/* 3900 */     if (_jspx_eval_c_005fotherwise_005f19 != 0) {
/*      */       for (;;) {
/* 3902 */         out.write("\n                                         <option value=\"");
/* 3903 */         if (_jspx_meth_c_005fout_005f45(_jspx_th_c_005fotherwise_005f19, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3904 */           return true;
/* 3905 */         out.write(34);
/* 3906 */         out.write(62);
/* 3907 */         if (_jspx_meth_c_005fout_005f46(_jspx_th_c_005fotherwise_005f19, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 3908 */           return true;
/* 3909 */         out.write("</option> ");
/* 3910 */         out.write("\n                                    ");
/* 3911 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f19.doAfterBody();
/* 3912 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3916 */     if (_jspx_th_c_005fotherwise_005f19.doEndTag() == 5) {
/* 3917 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19);
/* 3918 */       return true;
/*      */     }
/* 3920 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19);
/* 3921 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f45(JspTag _jspx_th_c_005fotherwise_005f19, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3926 */     PageContext pageContext = _jspx_page_context;
/* 3927 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3929 */     OutTag _jspx_th_c_005fout_005f45 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3930 */     _jspx_th_c_005fout_005f45.setPageContext(_jspx_page_context);
/* 3931 */     _jspx_th_c_005fout_005f45.setParent((Tag)_jspx_th_c_005fotherwise_005f19);
/*      */     
/* 3933 */     _jspx_th_c_005fout_005f45.setValue("${entry.SERVERID}");
/* 3934 */     int _jspx_eval_c_005fout_005f45 = _jspx_th_c_005fout_005f45.doStartTag();
/* 3935 */     if (_jspx_th_c_005fout_005f45.doEndTag() == 5) {
/* 3936 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 3937 */       return true;
/*      */     }
/* 3939 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f45);
/* 3940 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f46(JspTag _jspx_th_c_005fotherwise_005f19, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 3945 */     PageContext pageContext = _jspx_page_context;
/* 3946 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3948 */     OutTag _jspx_th_c_005fout_005f46 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3949 */     _jspx_th_c_005fout_005f46.setPageContext(_jspx_page_context);
/* 3950 */     _jspx_th_c_005fout_005f46.setParent((Tag)_jspx_th_c_005fotherwise_005f19);
/*      */     
/* 3952 */     _jspx_th_c_005fout_005f46.setValue("${entry.HOST}");
/* 3953 */     int _jspx_eval_c_005fout_005f46 = _jspx_th_c_005fout_005f46.doStartTag();
/* 3954 */     if (_jspx_th_c_005fout_005f46.doEndTag() == 5) {
/* 3955 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 3956 */       return true;
/*      */     }
/* 3958 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f46);
/* 3959 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3964 */     PageContext pageContext = _jspx_page_context;
/* 3965 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3967 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3968 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3969 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3971 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3973 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3974 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3975 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3976 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3977 */       return true;
/*      */     }
/* 3979 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3980 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\NetworkDiscoveryForm_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */