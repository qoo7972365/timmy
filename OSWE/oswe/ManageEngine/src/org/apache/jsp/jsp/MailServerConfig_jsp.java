/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.EnterpriseAdminLink;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.util.Map;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class MailServerConfig_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   26 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   32 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(3);
/*   33 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*   34 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/*   35 */     _jspx_dependants.put("/jsp/includes/EnterpriseAdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*   53 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   57 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   68 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   72 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   73 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   74 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   75 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   76 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   77 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*   78 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*   79 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*   80 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.release();
/*   81 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   88 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   91 */     JspWriter out = null;
/*   92 */     Object page = this;
/*   93 */     JspWriter _jspx_out = null;
/*   94 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*   98 */       response.setContentType("text/html;charset=UTF-8");
/*   99 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  101 */       _jspx_page_context = pageContext;
/*  102 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  103 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  104 */       session = pageContext.getSession();
/*  105 */       out = pageContext.getOut();
/*  106 */       _jspx_out = out;
/*      */       
/*  108 */       out.write("<!DOCTYPE html>\n");
/*  109 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  110 */       out.write("\n\n\n\n\n\n\n\n\n");
/*  111 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  112 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  114 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*  115 */       out.write(10);
/*  116 */       out.write("\n<script>\n\t\t");
/*      */       
/*  118 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  119 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  120 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/*  122 */       _jspx_th_c_005fif_005f0.setTest("${sms=='true'}");
/*  123 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  124 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/*  126 */           out.write(10);
/*      */           
/*  128 */           String ostype = System.getProperty("os.name");
/*  129 */           if (ostype.indexOf("Windows") == -1)
/*      */           {
/*  131 */             request.setAttribute("hideForLinux", Boolean.valueOf(true));
/*      */           }
/*      */           
/*  134 */           out.write(10);
/*  135 */           out.write(32);
/*  136 */           out.write(32);
/*  137 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  138 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  142 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  143 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/*  146 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  147 */         out.write("\n  </script>\n");
/*      */         
/*      */ 
/*      */ 
/*  151 */         String heading = FormatUtil.getString("am.webclient.mailsettings.configuremailserver.text");
/*  152 */         String title = FormatUtil.getString("am.webclient.mail.title");
/*  153 */         String qNote = FormatUtil.getString("am.webclient.mailsettings.quicknote.text");
/*      */         try
/*      */         {
/*  156 */           if ((request.getAttribute("sms") != null) && (((String)request.getAttribute("sms")).equals("true")))
/*      */           {
/*  158 */             request.setAttribute("HelpKey", "Configure SMS Server");
/*  159 */             heading = FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text");
/*  160 */             title = FormatUtil.getString("am.webclient.sms.title");
/*      */             
/*  162 */             qNote = FormatUtil.getString("am.webclient.smssettings.quicknote.text");
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  167 */             request.setAttribute("HelpKey", "Configure Mail Server");
/*      */             
/*  169 */             heading = FormatUtil.getString("am.webclient.mailsettings.configuremailserver.text");
/*  170 */             title = FormatUtil.getString("am.webclient.mail.title");
/*  171 */             qNote = FormatUtil.getString("am.webclient.mailsettings.quicknote.text");
/*      */           }
/*      */         }
/*      */         catch (Exception ex) {
/*  175 */           ex.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  180 */         out.write(10);
/*      */         
/*  182 */         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  183 */         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  184 */         _jspx_th_c_005fchoose_005f0.setParent(null);
/*  185 */         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  186 */         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */           for (;;)
/*      */           {
/*  189 */             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  190 */             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  191 */             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */             
/*  193 */             _jspx_th_c_005fwhen_005f0.setTest("${hideForLinux==true}");
/*  194 */             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  195 */             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */               for (;;) {
/*  197 */                 out.write("\n\t<div class=\"msg-table-width\" style=\"text-align:center\">\n\t\t\t\t<span class=\"padd-rt-15\"><img\n\t\t\t\t\tsrc=\"../images/icon_message_success.gif\" class=\"align-middle\"\n\t\t\t\t\talt=\"Icon\" width=\"25\" height=\"25\">\n\t\t\t\t</span>\n\t\t\t\t<b>\t");
/*  198 */                 out.print(FormatUtil.getString("am.webclinet.smsmodemrules.windowscheck"));
/*  199 */                 out.write(" </b>\n\t\t\t</div>\n\n\n");
/*  200 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  201 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  205 */             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  206 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */             }
/*      */             
/*  209 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  210 */             out.write(10);
/*      */             
/*  212 */             OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  213 */             _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  214 */             _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  215 */             int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  216 */             if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */               for (;;) {
/*  218 */                 out.write(10);
/*      */                 
/*  220 */                 InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/*  221 */                 _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/*  222 */                 _jspx_th_tiles_005finsert_005f0.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                 
/*  224 */                 _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/*  225 */                 int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/*  226 */                 if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                   for (;;) {
/*  228 */                     out.write(10);
/*  229 */                     out.write(9);
/*      */                     
/*  231 */                     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/*  232 */                     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/*  233 */                     _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                     
/*  235 */                     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                     
/*  237 */                     _jspx_th_tiles_005fput_005f0.setValue(title);
/*  238 */                     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/*  239 */                     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/*  240 */                       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                     }
/*      */                     
/*  243 */                     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/*  244 */                     out.write("\t\t\n\t");
/*  245 */                     if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                       return;
/*  247 */                     out.write("\t\t\n\t");
/*      */                     
/*  249 */                     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/*  250 */                     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/*  251 */                     _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                     
/*  253 */                     _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                     
/*  255 */                     _jspx_th_tiles_005fput_005f2.setType("string");
/*  256 */                     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/*  257 */                     if (_jspx_eval_tiles_005fput_005f2 != 0) {
/*  258 */                       if (_jspx_eval_tiles_005fput_005f2 != 1) {
/*  259 */                         out = _jspx_page_context.pushBody();
/*  260 */                         _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/*  261 */                         _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  264 */                         out.write("\t\t\n\t");
/*      */                         
/*  266 */                         if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */                         {
/*  268 */                           out.write("\t\t\n\t        ");
/*  269 */                           out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */                           
/*      */ 
/*  272 */                           String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                           
/*  274 */                           out.write("\n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n <tr>\n\t     <td height=\"21\"  class=\"leftlinksheading\">");
/*  275 */                           out.print(FormatUtil.getString("am.webclient.admin.heading"));
/*  276 */                           out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/*  278 */                           ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  279 */                           _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  280 */                           _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*  281 */                           int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  282 */                           if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                             for (;;) {
/*  284 */                               out.write(10);
/*      */                               
/*  286 */                               WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  287 */                               _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  288 */                               _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                               
/*  290 */                               _jspx_th_c_005fwhen_005f1.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/*  291 */                               int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  292 */                               if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                 for (;;) {
/*  294 */                                   out.write("\n        ");
/*  295 */                                   out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  296 */                                   out.write(10);
/*  297 */                                   out.write(32);
/*  298 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  299 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  303 */                               if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  304 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                               }
/*      */                               
/*  307 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  308 */                               out.write(10);
/*  309 */                               out.write(32);
/*      */                               
/*  311 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  312 */                               _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  313 */                               _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  314 */                               int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  315 */                               if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                 for (;;) {
/*  317 */                                   out.write("\n     ");
/*      */                                   
/*  319 */                                   EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f0 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  320 */                                   _jspx_th_ea_005feeadminlink_005f0.setPageContext(_jspx_page_context);
/*  321 */                                   _jspx_th_ea_005feeadminlink_005f0.setParent(_jspx_th_c_005fotherwise_005f1);
/*      */                                   
/*  323 */                                   _jspx_th_ea_005feeadminlink_005f0.setHref("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general");
/*      */                                   
/*  325 */                                   _jspx_th_ea_005feeadminlink_005f0.setEnableClass("new-left-links");
/*  326 */                                   int _jspx_eval_ea_005feeadminlink_005f0 = _jspx_th_ea_005feeadminlink_005f0.doStartTag();
/*  327 */                                   if (_jspx_eval_ea_005feeadminlink_005f0 != 0) {
/*  328 */                                     if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/*  329 */                                       out = _jspx_page_context.pushBody();
/*  330 */                                       _jspx_th_ea_005feeadminlink_005f0.setBodyContent((BodyContent)out);
/*  331 */                                       _jspx_th_ea_005feeadminlink_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/*  334 */                                       out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  335 */                                       int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f0.doAfterBody();
/*  336 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*  339 */                                     if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/*  340 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/*  343 */                                   if (_jspx_th_ea_005feeadminlink_005f0.doEndTag() == 5) {
/*  344 */                                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0); return;
/*      */                                   }
/*      */                                   
/*  347 */                                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0);
/*  348 */                                   out.write(10);
/*  349 */                                   out.write(32);
/*  350 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  351 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  355 */                               if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  356 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                               }
/*      */                               
/*  359 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  360 */                               out.write(10);
/*  361 */                               out.write(32);
/*  362 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  363 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  367 */                           if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  368 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                           }
/*      */                           
/*  371 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  372 */                           out.write("\n    </td>\n</tr>\n\n<tr>\n\n<tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/*  374 */                           ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  375 */                           _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  376 */                           _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*  377 */                           int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  378 */                           if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                             for (;;) {
/*  380 */                               out.write(10);
/*      */                               
/*  382 */                               WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  383 */                               _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  384 */                               _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                               
/*  386 */                               _jspx_th_c_005fwhen_005f2.setTest("${param.method!='showMailServerConfiguration'}");
/*  387 */                               int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  388 */                               if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                 for (;;) {
/*  390 */                                   out.write("\n    ");
/*      */                                   
/*  392 */                                   EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f1 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  393 */                                   _jspx_th_ea_005feeadminlink_005f1.setPageContext(_jspx_page_context);
/*  394 */                                   _jspx_th_ea_005feeadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                                   
/*  396 */                                   _jspx_th_ea_005feeadminlink_005f1.setHref("/adminAction.do?method=showMailServerConfiguration");
/*      */                                   
/*  398 */                                   _jspx_th_ea_005feeadminlink_005f1.setEnableClass("new-left-links");
/*  399 */                                   int _jspx_eval_ea_005feeadminlink_005f1 = _jspx_th_ea_005feeadminlink_005f1.doStartTag();
/*  400 */                                   if (_jspx_eval_ea_005feeadminlink_005f1 != 0) {
/*  401 */                                     if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/*  402 */                                       out = _jspx_page_context.pushBody();
/*  403 */                                       _jspx_th_ea_005feeadminlink_005f1.setBodyContent((BodyContent)out);
/*  404 */                                       _jspx_th_ea_005feeadminlink_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/*  407 */                                       out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/*  408 */                                       int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f1.doAfterBody();
/*  409 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*  412 */                                     if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/*  413 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/*  416 */                                   if (_jspx_th_ea_005feeadminlink_005f1.doEndTag() == 5) {
/*  417 */                                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1); return;
/*      */                                   }
/*      */                                   
/*  420 */                                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1);
/*  421 */                                   out.write(10);
/*  422 */                                   out.write(32);
/*  423 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  424 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  428 */                               if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  429 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                               }
/*      */                               
/*  432 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  433 */                               out.write(10);
/*  434 */                               out.write(32);
/*      */                               
/*  436 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  437 */                               _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  438 */                               _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  439 */                               int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  440 */                               if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                 for (;;) {
/*  442 */                                   out.write(10);
/*  443 */                                   out.write(9);
/*  444 */                                   out.write(32);
/*  445 */                                   out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/*  446 */                                   out.write(10);
/*  447 */                                   out.write(32);
/*  448 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  449 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  453 */                               if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  454 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                               }
/*      */                               
/*  457 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  458 */                               out.write(10);
/*  459 */                               out.write(32);
/*  460 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  461 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  465 */                           if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  466 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                           }
/*      */                           
/*  469 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  470 */                           out.write("\n    </td>\n</tr>\n\n");
/*  471 */                           if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/*  472 */                             out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/*  474 */                             ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  475 */                             _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/*  476 */                             _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*  477 */                             int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/*  478 */                             if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                               for (;;) {
/*  480 */                                 out.write(10);
/*      */                                 
/*  482 */                                 WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  483 */                                 _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  484 */                                 _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                 
/*  486 */                                 _jspx_th_c_005fwhen_005f3.setTest("${param.method!='SMSServerConfiguration'}");
/*  487 */                                 int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  488 */                                 if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                   for (;;) {
/*  490 */                                     out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/*  491 */                                     out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/*  492 */                                     out.write("\n    </a>\n ");
/*  493 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  494 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  498 */                                 if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  499 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                 }
/*      */                                 
/*  502 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  503 */                                 out.write(10);
/*  504 */                                 out.write(32);
/*      */                                 
/*  506 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  507 */                                 _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  508 */                                 _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*  509 */                                 int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  510 */                                 if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                   for (;;) {
/*  512 */                                     out.write("\n         ");
/*  513 */                                     out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/*  514 */                                     out.write(10);
/*  515 */                                     out.write(32);
/*  516 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/*  517 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  521 */                                 if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/*  522 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                                 }
/*      */                                 
/*  525 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/*  526 */                                 out.write(10);
/*  527 */                                 out.write(32);
/*  528 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/*  529 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  533 */                             if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/*  534 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                             }
/*      */                             
/*  537 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*  538 */                             out.write("\n    </td>\n</tr>\n");
/*      */                           }
/*  540 */                           out.write("\n\n\n<tr>\n\n    <td class=\"leftlinkstd\">\n");
/*      */                           
/*  542 */                           ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  543 */                           _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  544 */                           _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/*  545 */                           int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  546 */                           if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                             for (;;) {
/*  548 */                               out.write(10);
/*      */                               
/*  550 */                               WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  551 */                               _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  552 */                               _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                               
/*  554 */                               _jspx_th_c_005fwhen_005f4.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/*  555 */                               int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  556 */                               if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                 for (;;) {
/*  558 */                                   out.write("\n    ");
/*      */                                   
/*  560 */                                   EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f2 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  561 */                                   _jspx_th_ea_005feeadminlink_005f2.setPageContext(_jspx_page_context);
/*  562 */                                   _jspx_th_ea_005feeadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                                   
/*  564 */                                   _jspx_th_ea_005feeadminlink_005f2.setHref("/jsp/ProxyConfiguration.jsp");
/*      */                                   
/*  566 */                                   _jspx_th_ea_005feeadminlink_005f2.setEnableClass("new-left-links");
/*  567 */                                   int _jspx_eval_ea_005feeadminlink_005f2 = _jspx_th_ea_005feeadminlink_005f2.doStartTag();
/*  568 */                                   if (_jspx_eval_ea_005feeadminlink_005f2 != 0) {
/*  569 */                                     if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/*  570 */                                       out = _jspx_page_context.pushBody();
/*  571 */                                       _jspx_th_ea_005feeadminlink_005f2.setBodyContent((BodyContent)out);
/*  572 */                                       _jspx_th_ea_005feeadminlink_005f2.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/*  575 */                                       out.write("\n    ");
/*  576 */                                       out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/*  577 */                                       out.write("\n    ");
/*  578 */                                       int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f2.doAfterBody();
/*  579 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*  582 */                                     if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/*  583 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/*  586 */                                   if (_jspx_th_ea_005feeadminlink_005f2.doEndTag() == 5) {
/*  587 */                                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2); return;
/*      */                                   }
/*      */                                   
/*  590 */                                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2);
/*  591 */                                   out.write(10);
/*  592 */                                   out.write(32);
/*  593 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  594 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  598 */                               if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  599 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                               }
/*      */                               
/*  602 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  603 */                               out.write(10);
/*  604 */                               out.write(32);
/*      */                               
/*  606 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  607 */                               _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  608 */                               _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*  609 */                               int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  610 */                               if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                                 for (;;) {
/*  612 */                                   out.write(10);
/*  613 */                                   out.write(9);
/*  614 */                                   out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/*  615 */                                   out.write(10);
/*  616 */                                   out.write(32);
/*  617 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  618 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  622 */                               if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  623 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                               }
/*      */                               
/*  626 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  627 */                               out.write(10);
/*  628 */                               out.write(32);
/*  629 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/*  630 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  634 */                           if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/*  635 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                           }
/*      */                           
/*  638 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*  639 */                           out.write("\n    </td>\n</tr>\n<tr>\n\n<td class=\"leftlinkstd\">\n");
/*      */                           
/*  641 */                           ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  642 */                           _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  643 */                           _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/*  644 */                           int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  645 */                           if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                             for (;;) {
/*  647 */                               out.write(10);
/*      */                               
/*  649 */                               WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  650 */                               _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/*  651 */                               _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                               
/*  653 */                               _jspx_th_c_005fwhen_005f5.setTest("${uri !='/admin/userconfiguration.do'}");
/*  654 */                               int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/*  655 */                               if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                 for (;;) {
/*  657 */                                   out.write("\n\n        ");
/*      */                                   
/*  659 */                                   EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f3 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  660 */                                   _jspx_th_ea_005feeadminlink_005f3.setPageContext(_jspx_page_context);
/*  661 */                                   _jspx_th_ea_005feeadminlink_005f3.setParent(_jspx_th_c_005fwhen_005f5);
/*      */                                   
/*  663 */                                   _jspx_th_ea_005feeadminlink_005f3.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                                   
/*  665 */                                   _jspx_th_ea_005feeadminlink_005f3.setEnableClass("new-left-links");
/*  666 */                                   int _jspx_eval_ea_005feeadminlink_005f3 = _jspx_th_ea_005feeadminlink_005f3.doStartTag();
/*  667 */                                   if (_jspx_eval_ea_005feeadminlink_005f3 != 0) {
/*  668 */                                     if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/*  669 */                                       out = _jspx_page_context.pushBody();
/*  670 */                                       _jspx_th_ea_005feeadminlink_005f3.setBodyContent((BodyContent)out);
/*  671 */                                       _jspx_th_ea_005feeadminlink_005f3.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/*  674 */                                       out.write("\n       ");
/*  675 */                                       out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/*  676 */                                       out.write("\n        ");
/*  677 */                                       int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f3.doAfterBody();
/*  678 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*  681 */                                     if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/*  682 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/*  685 */                                   if (_jspx_th_ea_005feeadminlink_005f3.doEndTag() == 5) {
/*  686 */                                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3); return;
/*      */                                   }
/*      */                                   
/*  689 */                                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3);
/*  690 */                                   out.write(10);
/*  691 */                                   out.write(10);
/*  692 */                                   out.write(32);
/*  693 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/*  694 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  698 */                               if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/*  699 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                               }
/*      */                               
/*  702 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*  703 */                               out.write(10);
/*  704 */                               out.write(32);
/*      */                               
/*  706 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  707 */                               _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  708 */                               _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*  709 */                               int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  710 */                               if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                                 for (;;) {
/*  712 */                                   out.write(10);
/*  713 */                                   out.write(9);
/*  714 */                                   out.write(32);
/*  715 */                                   out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/*  716 */                                   out.write(10);
/*  717 */                                   out.write(32);
/*  718 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  719 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  723 */                               if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  724 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                               }
/*      */                               
/*  727 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  728 */                               out.write(10);
/*  729 */                               out.write(32);
/*  730 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  731 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  735 */                           if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  736 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                           }
/*      */                           
/*  739 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  740 */                           out.write("\n</td>\n</tr>\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                           
/*  742 */                           ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  743 */                           _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/*  744 */                           _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_tiles_005fput_005f2);
/*  745 */                           int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/*  746 */                           if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                             for (;;) {
/*  748 */                               out.write("\n   ");
/*      */                               
/*  750 */                               WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  751 */                               _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/*  752 */                               _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                               
/*  754 */                               _jspx_th_c_005fwhen_005f6.setTest("${param.method!='showManagedServers'}");
/*  755 */                               int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/*  756 */                               if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                                 for (;;) {
/*  758 */                                   out.write("\n    ");
/*      */                                   
/*  760 */                                   EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f4 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  761 */                                   _jspx_th_ea_005feeadminlink_005f4.setPageContext(_jspx_page_context);
/*  762 */                                   _jspx_th_ea_005feeadminlink_005f4.setParent(_jspx_th_c_005fwhen_005f6);
/*      */                                   
/*  764 */                                   _jspx_th_ea_005feeadminlink_005f4.setHref("/adminAction.do?method=showManagedServers");
/*      */                                   
/*  766 */                                   _jspx_th_ea_005feeadminlink_005f4.setEnableClass("new-left-links");
/*  767 */                                   int _jspx_eval_ea_005feeadminlink_005f4 = _jspx_th_ea_005feeadminlink_005f4.doStartTag();
/*  768 */                                   if (_jspx_eval_ea_005feeadminlink_005f4 != 0) {
/*  769 */                                     if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/*  770 */                                       out = _jspx_page_context.pushBody();
/*  771 */                                       _jspx_th_ea_005feeadminlink_005f4.setBodyContent((BodyContent)out);
/*  772 */                                       _jspx_th_ea_005feeadminlink_005f4.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/*  775 */                                       out.write("\n     ");
/*  776 */                                       out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/*  777 */                                       out.write(10);
/*  778 */                                       out.write(9);
/*  779 */                                       int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f4.doAfterBody();
/*  780 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*  783 */                                     if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/*  784 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/*  787 */                                   if (_jspx_th_ea_005feeadminlink_005f4.doEndTag() == 5) {
/*  788 */                                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4); return;
/*      */                                   }
/*      */                                   
/*  791 */                                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4);
/*  792 */                                   out.write("\n   ");
/*  793 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/*  794 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  798 */                               if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/*  799 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                               }
/*      */                               
/*  802 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/*  803 */                               out.write("\n   ");
/*      */                               
/*  805 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  806 */                               _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/*  807 */                               _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*  808 */                               int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/*  809 */                               if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                                 for (;;) {
/*  811 */                                   out.write("\n     ");
/*  812 */                                   out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/*  813 */                                   out.write("\n   ");
/*  814 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/*  815 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  819 */                               if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/*  820 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                               }
/*      */                               
/*  823 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/*  824 */                               out.write("\n   ");
/*  825 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/*  826 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  830 */                           if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/*  831 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                           }
/*      */                           
/*  834 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/*  835 */                           out.write("\n  </td>\n</tr>\n\n\n<td class=\"leftlinkstd\" >\n");
/*      */                           
/*  837 */                           ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  838 */                           _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/*  839 */                           _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/*  840 */                           int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/*  841 */                           if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                             for (;;) {
/*  843 */                               out.write(10);
/*      */                               
/*  845 */                               WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  846 */                               _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/*  847 */                               _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                               
/*  849 */                               _jspx_th_c_005fwhen_005f7.setTest("${param.server!='admin'}");
/*  850 */                               int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/*  851 */                               if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                                 for (;;) {
/*  853 */                                   out.write(10);
/*      */                                   
/*  855 */                                   EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f5 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  856 */                                   _jspx_th_ea_005feeadminlink_005f5.setPageContext(_jspx_page_context);
/*  857 */                                   _jspx_th_ea_005feeadminlink_005f5.setParent(_jspx_th_c_005fwhen_005f7);
/*      */                                   
/*  859 */                                   _jspx_th_ea_005feeadminlink_005f5.setHref("/adminAction.do?method=showActionProfiles");
/*      */                                   
/*  861 */                                   _jspx_th_ea_005feeadminlink_005f5.setEnableClass("new-left-links");
/*  862 */                                   int _jspx_eval_ea_005feeadminlink_005f5 = _jspx_th_ea_005feeadminlink_005f5.doStartTag();
/*  863 */                                   if (_jspx_eval_ea_005feeadminlink_005f5 != 0) {
/*  864 */                                     if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/*  865 */                                       out = _jspx_page_context.pushBody();
/*  866 */                                       _jspx_th_ea_005feeadminlink_005f5.setBodyContent((BodyContent)out);
/*  867 */                                       _jspx_th_ea_005feeadminlink_005f5.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/*  870 */                                       out.write(10);
/*  871 */                                       out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/*  872 */                                       out.write(10);
/*  873 */                                       int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f5.doAfterBody();
/*  874 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*  877 */                                     if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/*  878 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/*  881 */                                   if (_jspx_th_ea_005feeadminlink_005f5.doEndTag() == 5) {
/*  882 */                                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5); return;
/*      */                                   }
/*      */                                   
/*  885 */                                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5);
/*  886 */                                   out.write(10);
/*  887 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/*  888 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  892 */                               if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/*  893 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                               }
/*      */                               
/*  896 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/*  897 */                               out.write(10);
/*      */                               
/*  899 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  900 */                               _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/*  901 */                               _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*  902 */                               int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/*  903 */                               if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                                 for (;;) {
/*  905 */                                   out.write(10);
/*  906 */                                   out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/*  907 */                                   out.write(10);
/*  908 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/*  909 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/*  913 */                               if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/*  914 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                               }
/*      */                               
/*  917 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/*  918 */                               out.write(10);
/*  919 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/*  920 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/*  924 */                           if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/*  925 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                           }
/*      */                           
/*  928 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/*  929 */                           out.write("\n</td>\n</tr>\n");
/*      */                           
/*  931 */                           if ((!usertype.equals("F")) || (Constants.isIt360))
/*      */                           {
/*  933 */                             out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */                             
/*  935 */                             ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  936 */                             _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/*  937 */                             _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f2);
/*  938 */                             int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/*  939 */                             if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                               for (;;) {
/*  941 */                                 out.write(10);
/*  942 */                                 out.write(32);
/*  943 */                                 out.write(32);
/*      */                                 
/*  945 */                                 WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  946 */                                 _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/*  947 */                                 _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                                 
/*  949 */                                 _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showScheduleReports'}");
/*  950 */                                 int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/*  951 */                                 if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                   for (;;) {
/*  953 */                                     out.write(10);
/*  954 */                                     out.write(32);
/*  955 */                                     out.write(32);
/*  956 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  957 */                                     out.write(10);
/*  958 */                                     out.write(32);
/*  959 */                                     out.write(32);
/*  960 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/*  961 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  965 */                                 if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/*  966 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                                 }
/*      */                                 
/*  969 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/*  970 */                                 out.write(10);
/*  971 */                                 out.write(32);
/*      */                                 
/*  973 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  974 */                                 _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/*  975 */                                 _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*  976 */                                 int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/*  977 */                                 if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                                   for (;;) {
/*  979 */                                     out.write("\n   ");
/*      */                                     
/*  981 */                                     EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f6 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  982 */                                     _jspx_th_ea_005feeadminlink_005f6.setPageContext(_jspx_page_context);
/*  983 */                                     _jspx_th_ea_005feeadminlink_005f6.setParent(_jspx_th_c_005fotherwise_005f8);
/*      */                                     
/*  985 */                                     _jspx_th_ea_005feeadminlink_005f6.setHref("/scheduleReports.do?method=showScheduleReports");
/*      */                                     
/*  987 */                                     _jspx_th_ea_005feeadminlink_005f6.setEnableClass("new-left-links");
/*  988 */                                     int _jspx_eval_ea_005feeadminlink_005f6 = _jspx_th_ea_005feeadminlink_005f6.doStartTag();
/*  989 */                                     if (_jspx_eval_ea_005feeadminlink_005f6 != 0) {
/*  990 */                                       if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/*  991 */                                         out = _jspx_page_context.pushBody();
/*  992 */                                         _jspx_th_ea_005feeadminlink_005f6.setBodyContent((BodyContent)out);
/*  993 */                                         _jspx_th_ea_005feeadminlink_005f6.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/*  996 */                                         out.write("\n   ");
/*  997 */                                         out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  998 */                                         out.write(10);
/*  999 */                                         out.write(32);
/* 1000 */                                         out.write(32);
/* 1001 */                                         int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f6.doAfterBody();
/* 1002 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 1005 */                                       if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/* 1006 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 1009 */                                     if (_jspx_th_ea_005feeadminlink_005f6.doEndTag() == 5) {
/* 1010 */                                       this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6); return;
/*      */                                     }
/*      */                                     
/* 1013 */                                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6);
/* 1014 */                                     out.write(10);
/* 1015 */                                     out.write(32);
/* 1016 */                                     out.write(32);
/* 1017 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 1018 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1022 */                                 if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 1023 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                                 }
/*      */                                 
/* 1026 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 1027 */                                 out.write(10);
/* 1028 */                                 out.write(32);
/* 1029 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 1030 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1034 */                             if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 1035 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                             }
/*      */                             
/* 1038 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 1039 */                             out.write("\n</td>\n</tr>\n");
/*      */                           } else {
/* 1041 */                             out.write("\n<tr>\n    <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 1042 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1043 */                             out.write("\n    </td>\n</tr>\n");
/*      */                           }
/* 1045 */                           out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */                           
/* 1047 */                           ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1048 */                           _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 1049 */                           _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f2);
/* 1050 */                           int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 1051 */                           if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                             for (;;) {
/* 1053 */                               out.write(10);
/* 1054 */                               out.write(32);
/* 1055 */                               out.write(32);
/*      */                               
/* 1057 */                               WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1058 */                               _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 1059 */                               _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                               
/* 1061 */                               _jspx_th_c_005fwhen_005f9.setTest("${param.method =='showDataCleanUp'}");
/* 1062 */                               int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 1063 */                               if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                                 for (;;) {
/* 1065 */                                   out.write(10);
/* 1066 */                                   out.write(32);
/* 1067 */                                   out.write(32);
/* 1068 */                                   out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1069 */                                   out.write(10);
/* 1070 */                                   out.write(32);
/* 1071 */                                   out.write(32);
/* 1072 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 1073 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1077 */                               if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 1078 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                               }
/*      */                               
/* 1081 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 1082 */                               out.write(10);
/* 1083 */                               out.write(32);
/*      */                               
/* 1085 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1086 */                               _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 1087 */                               _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 1088 */                               int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 1089 */                               if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                                 for (;;) {
/* 1091 */                                   out.write("\n   ");
/*      */                                   
/* 1093 */                                   EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f7 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/* 1094 */                                   _jspx_th_ea_005feeadminlink_005f7.setPageContext(_jspx_page_context);
/* 1095 */                                   _jspx_th_ea_005feeadminlink_005f7.setParent(_jspx_th_c_005fotherwise_005f9);
/*      */                                   
/* 1097 */                                   _jspx_th_ea_005feeadminlink_005f7.setHref("/adminAction.do?method=showDataCleanUp");
/*      */                                   
/* 1099 */                                   _jspx_th_ea_005feeadminlink_005f7.setEnableClass("new-left-links");
/* 1100 */                                   int _jspx_eval_ea_005feeadminlink_005f7 = _jspx_th_ea_005feeadminlink_005f7.doStartTag();
/* 1101 */                                   if (_jspx_eval_ea_005feeadminlink_005f7 != 0) {
/* 1102 */                                     if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/* 1103 */                                       out = _jspx_page_context.pushBody();
/* 1104 */                                       _jspx_th_ea_005feeadminlink_005f7.setBodyContent((BodyContent)out);
/* 1105 */                                       _jspx_th_ea_005feeadminlink_005f7.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 1108 */                                       out.write("\n   ");
/* 1109 */                                       out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 1110 */                                       out.write(10);
/* 1111 */                                       out.write(32);
/* 1112 */                                       out.write(32);
/* 1113 */                                       int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f7.doAfterBody();
/* 1114 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 1117 */                                     if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/* 1118 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 1121 */                                   if (_jspx_th_ea_005feeadminlink_005f7.doEndTag() == 5) {
/* 1122 */                                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7); return;
/*      */                                   }
/*      */                                   
/* 1125 */                                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7);
/* 1126 */                                   out.write(10);
/* 1127 */                                   out.write(32);
/* 1128 */                                   out.write(32);
/* 1129 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 1130 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1134 */                               if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 1135 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                               }
/*      */                               
/* 1138 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 1139 */                               out.write(10);
/* 1140 */                               out.write(32);
/* 1141 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 1142 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1146 */                           if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 1147 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                           }
/*      */                           
/* 1150 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 1151 */                           out.write("\n</td>\n</tr>\n</table>\n\n");
/* 1152 */                           out.write("\t\t\n\t  ");
/*      */                         }
/*      */                         else
/*      */                         {
/* 1156 */                           out.write("\t\t\n\t        ");
/* 1157 */                           out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */                           
/*      */ 
/* 1160 */                           String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */                           
/* 1162 */                           out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/* 1163 */                           out.print(FormatUtil.getString("wizard.disabled"));
/* 1164 */                           out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/* 1165 */                           out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 1166 */                           out.write("</td>\n  </tr>\n  \n ");
/*      */                           
/* 1168 */                           if (!Constants.sqlManager)
/*      */                           {
/*      */ 
/* 1171 */                             out.write("  \n  <tr>\n\n  ");
/*      */                             
/* 1173 */                             if (request.getParameter("wiz") != null)
/*      */                             {
/* 1175 */                               out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/* 1176 */                               out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 1177 */                               out.write("\" class='disabledlink'>");
/* 1178 */                               out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1179 */                               out.write("</a></td>\n  ");
/*      */                             }
/*      */                             else
/*      */                             {
/* 1183 */                               out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                               
/* 1185 */                               ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1186 */                               _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 1187 */                               _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/* 1188 */                               int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 1189 */                               if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                                 for (;;) {
/* 1191 */                                   out.write(10);
/*      */                                   
/* 1193 */                                   WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1194 */                                   _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 1195 */                                   _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                                   
/* 1197 */                                   _jspx_th_c_005fwhen_005f10.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/* 1198 */                                   int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 1199 */                                   if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                     for (;;) {
/* 1201 */                                       out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/* 1202 */                                       out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1203 */                                       out.write("\n    </a>\n ");
/* 1204 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 1205 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 1209 */                                   if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 1210 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                                   }
/*      */                                   
/* 1213 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 1214 */                                   out.write(10);
/* 1215 */                                   out.write(32);
/*      */                                   
/* 1217 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1218 */                                   _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 1219 */                                   _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 1220 */                                   int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 1221 */                                   if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                                     for (;;) {
/* 1223 */                                       out.write(10);
/* 1224 */                                       out.write(9);
/* 1225 */                                       out.write(32);
/* 1226 */                                       out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1227 */                                       out.write(10);
/* 1228 */                                       out.write(32);
/* 1229 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 1230 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 1234 */                                   if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 1235 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                                   }
/*      */                                   
/* 1238 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 1239 */                                   out.write(10);
/* 1240 */                                   out.write(32);
/* 1241 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 1242 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1246 */                               if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 1247 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                               }
/*      */                               
/* 1250 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 1251 */                               out.write("\n    </td>\n\t");
/*      */                             }
/* 1253 */                             out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                             
/* 1255 */                             if (request.getParameter("wiz") != null)
/*      */                             {
/* 1257 */                               out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/* 1258 */                               out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 1259 */                               out.write("\" class='disabledlink'>");
/* 1260 */                               out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1261 */                               out.write("</a></td>\n   ");
/*      */                             }
/*      */                             else
/*      */                             {
/* 1265 */                               out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                               
/* 1267 */                               ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1268 */                               _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 1269 */                               _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/* 1270 */                               int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 1271 */                               if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                                 for (;;) {
/* 1273 */                                   out.write(10);
/*      */                                   
/* 1275 */                                   WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1276 */                                   _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 1277 */                                   _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                                   
/* 1279 */                                   _jspx_th_c_005fwhen_005f11.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/* 1280 */                                   int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 1281 */                                   if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                                     for (;;) {
/* 1283 */                                       out.write("\n   ");
/* 1284 */                                       out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1285 */                                       out.write(10);
/* 1286 */                                       out.write(32);
/* 1287 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 1288 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 1292 */                                   if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 1293 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                                   }
/*      */                                   
/* 1296 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 1297 */                                   out.write(10);
/* 1298 */                                   out.write(32);
/*      */                                   
/* 1300 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1301 */                                   _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 1302 */                                   _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 1303 */                                   int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 1304 */                                   if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                                     for (;;) {
/* 1306 */                                       out.write(10);
/* 1307 */                                       String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/* 1308 */                                       out.write("\n\t \n <a href=\"");
/* 1309 */                                       out.print(link);
/* 1310 */                                       out.write("\" class=\"new-left-links\">\n               ");
/* 1311 */                                       out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1312 */                                       out.write("\n    </a>    \n ");
/* 1313 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 1314 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 1318 */                                   if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 1319 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                                   }
/*      */                                   
/* 1322 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 1323 */                                   out.write(10);
/* 1324 */                                   out.write(32);
/* 1325 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 1326 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1330 */                               if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 1331 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                               }
/*      */                               
/* 1334 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 1335 */                               out.write("\n</td>\n");
/*      */                             }
/* 1337 */                             out.write("\n</tr>\n\n ");
/*      */                           }
/*      */                           
/*      */ 
/* 1341 */                           out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/* 1343 */                           ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1344 */                           _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 1345 */                           _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/* 1346 */                           int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 1347 */                           if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                             for (;;) {
/* 1349 */                               out.write(10);
/*      */                               
/* 1351 */                               WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1352 */                               _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 1353 */                               _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                               
/* 1355 */                               _jspx_th_c_005fwhen_005f12.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/* 1356 */                               int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 1357 */                               if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                                 for (;;) {
/* 1359 */                                   out.write("\n    \n       ");
/* 1360 */                                   out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 1361 */                                   out.write(10);
/* 1362 */                                   out.write(32);
/* 1363 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 1364 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1368 */                               if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 1369 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                               }
/*      */                               
/* 1372 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 1373 */                               out.write(10);
/* 1374 */                               out.write(32);
/*      */                               
/* 1376 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1377 */                               _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 1378 */                               _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 1379 */                               int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 1380 */                               if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                                 for (;;) {
/* 1382 */                                   out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/* 1383 */                                   out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 1384 */                                   out.write("\n    </a>\n ");
/* 1385 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 1386 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1390 */                               if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 1391 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                               }
/*      */                               
/* 1394 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 1395 */                               out.write(10);
/* 1396 */                               out.write(32);
/* 1397 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 1398 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1402 */                           if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 1403 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                           }
/*      */                           
/* 1406 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 1407 */                           out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/* 1409 */                           ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1410 */                           _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 1411 */                           _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f2);
/* 1412 */                           int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 1413 */                           if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                             for (;;) {
/* 1415 */                               out.write(10);
/*      */                               
/* 1417 */                               WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1418 */                               _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 1419 */                               _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                               
/* 1421 */                               _jspx_th_c_005fwhen_005f13.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/* 1422 */                               int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 1423 */                               if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                                 for (;;) {
/* 1425 */                                   out.write("\n    \n       ");
/* 1426 */                                   out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 1427 */                                   out.write(10);
/* 1428 */                                   out.write(32);
/* 1429 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 1430 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1434 */                               if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 1435 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                               }
/*      */                               
/* 1438 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 1439 */                               out.write(10);
/* 1440 */                               out.write(32);
/*      */                               
/* 1442 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1443 */                               _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 1444 */                               _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 1445 */                               int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 1446 */                               if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                                 for (;;) {
/* 1448 */                                   out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/* 1449 */                                   out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 1450 */                                   out.write("\n\t </a>\n ");
/* 1451 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 1452 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1456 */                               if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 1457 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                               }
/*      */                               
/* 1460 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 1461 */                               out.write(10);
/* 1462 */                               out.write(32);
/* 1463 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 1464 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1468 */                           if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 1469 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                           }
/*      */                           
/* 1472 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 1473 */                           out.write("\n    </td>\n</tr>  \n\n  ");
/*      */                           
/* 1475 */                           if (!Constants.sqlManager)
/*      */                           {
/*      */ 
/* 1478 */                             out.write(32);
/* 1479 */                             out.write(32);
/* 1480 */                             out.write(10);
/*      */                             
/* 1482 */                             ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1483 */                             _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 1484 */                             _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f2);
/* 1485 */                             int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 1486 */                             if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                               for (;;) {
/* 1488 */                                 out.write(10);
/*      */                                 
/* 1490 */                                 WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1491 */                                 _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 1492 */                                 _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                                 
/* 1494 */                                 _jspx_th_c_005fwhen_005f14.setTest("${param.method !='showNetworkDiscoveryForm'}");
/* 1495 */                                 int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 1496 */                                 if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                                   for (;;) {
/* 1498 */                                     out.write("\n<tr>\n    ");
/* 1499 */                                     if (!request.isUserInRole("OPERATOR")) {
/* 1500 */                                       out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/* 1501 */                                       out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 1502 */                                       out.write("\n    </a>\n        </td>\n     ");
/*      */                                     } else {
/* 1504 */                                       out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/* 1505 */                                       out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 1506 */                                       out.write("\n\t</a>\n\t </td>\n\t");
/*      */                                     }
/* 1508 */                                     out.write("\n    </tr>\n ");
/* 1509 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 1510 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1514 */                                 if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 1515 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                                 }
/*      */                                 
/* 1518 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 1519 */                                 out.write(10);
/* 1520 */                                 out.write(32);
/*      */                                 
/* 1522 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1523 */                                 _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 1524 */                                 _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 1525 */                                 int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 1526 */                                 if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                                   for (;;) {
/* 1528 */                                     out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/* 1529 */                                     out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 1530 */                                     out.write("\n\t </td>\n ");
/* 1531 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 1532 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1536 */                                 if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 1537 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                                 }
/*      */                                 
/* 1540 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 1541 */                                 out.write(10);
/* 1542 */                                 out.write(32);
/* 1543 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 1544 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1548 */                             if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 1549 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                             }
/*      */                             
/* 1552 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 1553 */                             out.write("\n \n  ");
/*      */                           }
/*      */                           
/*      */ 
/* 1557 */                           out.write("  \n \n ");
/*      */                           
/* 1559 */                           if (!usertype.equals("F"))
/*      */                           {
/* 1561 */                             out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                             
/* 1563 */                             ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1564 */                             _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 1565 */                             _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_tiles_005fput_005f2);
/* 1566 */                             int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 1567 */                             if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                               for (;;) {
/* 1569 */                                 out.write(10);
/* 1570 */                                 out.write(9);
/*      */                                 
/* 1572 */                                 WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1573 */                                 _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 1574 */                                 _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                                 
/* 1576 */                                 _jspx_th_c_005fwhen_005f15.setTest("${param.method !='maintenanceTaskListView'}");
/* 1577 */                                 int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 1578 */                                 if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                                   for (;;) {
/* 1580 */                                     out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 1581 */                                     out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1582 */                                     out.write("</a>\n  \t");
/* 1583 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 1584 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1588 */                                 if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 1589 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                                 }
/*      */                                 
/* 1592 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 1593 */                                 out.write("\n  \t");
/*      */                                 
/* 1595 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1596 */                                 _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 1597 */                                 _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 1598 */                                 int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 1599 */                                 if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                                   for (;;) {
/* 1601 */                                     out.write("\n \t\t");
/* 1602 */                                     out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1603 */                                     out.write("\n  \t");
/* 1604 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 1605 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1609 */                                 if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 1610 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                                 }
/*      */                                 
/* 1613 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 1614 */                                 out.write("\n  \t");
/* 1615 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 1616 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1620 */                             if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 1621 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                             }
/*      */                             
/* 1624 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 1625 */                             out.write("\n     </td>\n </tr>   \n \n ");
/*      */                             
/* 1627 */                             if (!Constants.sqlManager)
/*      */                             {
/*      */ 
/* 1630 */                               out.write(32);
/* 1631 */                               out.write(32);
/* 1632 */                               out.write(10);
/*      */                               
/* 1634 */                               IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1635 */                               _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1636 */                               _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 1638 */                               _jspx_th_c_005fif_005f1.setTest("${category!='LAMP'}");
/* 1639 */                               int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1640 */                               if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                 for (;;) {
/* 1642 */                                   out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                                   
/* 1644 */                                   ChooseTag _jspx_th_c_005fchoose_005f16 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1645 */                                   _jspx_th_c_005fchoose_005f16.setPageContext(_jspx_page_context);
/* 1646 */                                   _jspx_th_c_005fchoose_005f16.setParent(_jspx_th_c_005fif_005f1);
/* 1647 */                                   int _jspx_eval_c_005fchoose_005f16 = _jspx_th_c_005fchoose_005f16.doStartTag();
/* 1648 */                                   if (_jspx_eval_c_005fchoose_005f16 != 0) {
/*      */                                     for (;;) {
/* 1650 */                                       out.write(10);
/* 1651 */                                       out.write(32);
/* 1652 */                                       out.write(9);
/*      */                                       
/* 1654 */                                       WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1655 */                                       _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 1656 */                                       _jspx_th_c_005fwhen_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/*      */                                       
/* 1658 */                                       _jspx_th_c_005fwhen_005f16.setTest("${param.method !='listTrapListener'}");
/* 1659 */                                       int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 1660 */                                       if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */                                         for (;;) {
/* 1662 */                                           out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/* 1663 */                                           out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 1664 */                                           out.write("</a>\n   \t");
/* 1665 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 1666 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 1670 */                                       if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 1671 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16); return;
/*      */                                       }
/*      */                                       
/* 1674 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 1675 */                                       out.write("\n   \t");
/*      */                                       
/* 1677 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f16 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1678 */                                       _jspx_th_c_005fotherwise_005f16.setPageContext(_jspx_page_context);
/* 1679 */                                       _jspx_th_c_005fotherwise_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/* 1680 */                                       int _jspx_eval_c_005fotherwise_005f16 = _jspx_th_c_005fotherwise_005f16.doStartTag();
/* 1681 */                                       if (_jspx_eval_c_005fotherwise_005f16 != 0) {
/*      */                                         for (;;) {
/* 1683 */                                           out.write("\n  \t\t");
/* 1684 */                                           out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 1685 */                                           out.write(" \n   \t");
/* 1686 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f16.doAfterBody();
/* 1687 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 1691 */                                       if (_jspx_th_c_005fotherwise_005f16.doEndTag() == 5) {
/* 1692 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16); return;
/*      */                                       }
/*      */                                       
/* 1695 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 1696 */                                       out.write("\n   \t");
/* 1697 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f16.doAfterBody();
/* 1698 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 1702 */                                   if (_jspx_th_c_005fchoose_005f16.doEndTag() == 5) {
/* 1703 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16); return;
/*      */                                   }
/*      */                                   
/* 1706 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 1707 */                                   out.write("\n      </td>\n  </tr>   \n");
/* 1708 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1709 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1713 */                               if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1714 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                               }
/*      */                               
/* 1717 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1718 */                               out.write(10);
/* 1719 */                               out.write(32);
/*      */                             }
/*      */                             
/*      */ 
/* 1723 */                             out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 1725 */                             ChooseTag _jspx_th_c_005fchoose_005f17 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1726 */                             _jspx_th_c_005fchoose_005f17.setPageContext(_jspx_page_context);
/* 1727 */                             _jspx_th_c_005fchoose_005f17.setParent(_jspx_th_tiles_005fput_005f2);
/* 1728 */                             int _jspx_eval_c_005fchoose_005f17 = _jspx_th_c_005fchoose_005f17.doStartTag();
/* 1729 */                             if (_jspx_eval_c_005fchoose_005f17 != 0) {
/*      */                               for (;;) {
/* 1731 */                                 out.write(10);
/*      */                                 
/* 1733 */                                 WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1734 */                                 _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 1735 */                                 _jspx_th_c_005fwhen_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/*      */                                 
/* 1737 */                                 _jspx_th_c_005fwhen_005f17.setTest("${param.method =='showScheduleReports'}");
/* 1738 */                                 int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 1739 */                                 if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */                                   for (;;) {
/* 1741 */                                     out.write("\n       ");
/* 1742 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1743 */                                     out.write(10);
/* 1744 */                                     out.write(32);
/* 1745 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 1746 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1750 */                                 if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 1751 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17); return;
/*      */                                 }
/*      */                                 
/* 1754 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 1755 */                                 out.write(10);
/* 1756 */                                 out.write(32);
/*      */                                 
/* 1758 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f17 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1759 */                                 _jspx_th_c_005fotherwise_005f17.setPageContext(_jspx_page_context);
/* 1760 */                                 _jspx_th_c_005fotherwise_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/* 1761 */                                 int _jspx_eval_c_005fotherwise_005f17 = _jspx_th_c_005fotherwise_005f17.doStartTag();
/* 1762 */                                 if (_jspx_eval_c_005fotherwise_005f17 != 0) {
/*      */                                   for (;;) {
/* 1764 */                                     out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/* 1765 */                                     out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1766 */                                     out.write("\n\t </a>\n ");
/* 1767 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f17.doAfterBody();
/* 1768 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1772 */                                 if (_jspx_th_c_005fotherwise_005f17.doEndTag() == 5) {
/* 1773 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17); return;
/*      */                                 }
/*      */                                 
/* 1776 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 1777 */                                 out.write(10);
/* 1778 */                                 out.write(32);
/* 1779 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f17.doAfterBody();
/* 1780 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1784 */                             if (_jspx_th_c_005fchoose_005f17.doEndTag() == 5) {
/* 1785 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17); return;
/*      */                             }
/*      */                             
/* 1788 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 1789 */                             out.write("\n    </td>\n</tr> \n");
/*      */                           } else {
/* 1791 */                             out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 1792 */                             out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1793 */                             out.write("</a>\n     </td>\n </tr>   \n");
/*      */                             
/* 1795 */                             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1796 */                             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 1797 */                             _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 1799 */                             _jspx_th_c_005fif_005f2.setTest("${category!='LAMP'}");
/* 1800 */                             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 1801 */                             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                               for (;;) {
/* 1803 */                                 out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 1804 */                                 out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 1805 */                                 out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 1806 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 1807 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 1811 */                             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 1812 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                             }
/*      */                             
/* 1815 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 1816 */                             out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 1817 */                             out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1818 */                             out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */                           }
/* 1820 */                           out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/* 1822 */                           ChooseTag _jspx_th_c_005fchoose_005f18 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1823 */                           _jspx_th_c_005fchoose_005f18.setPageContext(_jspx_page_context);
/* 1824 */                           _jspx_th_c_005fchoose_005f18.setParent(_jspx_th_tiles_005fput_005f2);
/* 1825 */                           int _jspx_eval_c_005fchoose_005f18 = _jspx_th_c_005fchoose_005f18.doStartTag();
/* 1826 */                           if (_jspx_eval_c_005fchoose_005f18 != 0) {
/*      */                             for (;;) {
/* 1828 */                               out.write(10);
/*      */                               
/* 1830 */                               WhenTag _jspx_th_c_005fwhen_005f18 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1831 */                               _jspx_th_c_005fwhen_005f18.setPageContext(_jspx_page_context);
/* 1832 */                               _jspx_th_c_005fwhen_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/*      */                               
/* 1834 */                               _jspx_th_c_005fwhen_005f18.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 1835 */                               int _jspx_eval_c_005fwhen_005f18 = _jspx_th_c_005fwhen_005f18.doStartTag();
/* 1836 */                               if (_jspx_eval_c_005fwhen_005f18 != 0) {
/*      */                                 for (;;) {
/* 1838 */                                   out.write("\n        ");
/* 1839 */                                   out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 1840 */                                   out.write(10);
/* 1841 */                                   out.write(32);
/* 1842 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f18.doAfterBody();
/* 1843 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1847 */                               if (_jspx_th_c_005fwhen_005f18.doEndTag() == 5) {
/* 1848 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18); return;
/*      */                               }
/*      */                               
/* 1851 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 1852 */                               out.write(10);
/* 1853 */                               out.write(32);
/*      */                               
/* 1855 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f18 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1856 */                               _jspx_th_c_005fotherwise_005f18.setPageContext(_jspx_page_context);
/* 1857 */                               _jspx_th_c_005fotherwise_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/* 1858 */                               int _jspx_eval_c_005fotherwise_005f18 = _jspx_th_c_005fotherwise_005f18.doStartTag();
/* 1859 */                               if (_jspx_eval_c_005fotherwise_005f18 != 0) {
/*      */                                 for (;;) {
/* 1861 */                                   out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 1862 */                                   out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 1863 */                                   out.write("\n\t </a>\n ");
/* 1864 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f18.doAfterBody();
/* 1865 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1869 */                               if (_jspx_th_c_005fotherwise_005f18.doEndTag() == 5) {
/* 1870 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18); return;
/*      */                               }
/*      */                               
/* 1873 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18);
/* 1874 */                               out.write(10);
/* 1875 */                               out.write(32);
/* 1876 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f18.doAfterBody();
/* 1877 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1881 */                           if (_jspx_th_c_005fchoose_005f18.doEndTag() == 5) {
/* 1882 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18); return;
/*      */                           }
/*      */                           
/* 1885 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18);
/* 1886 */                           out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                           
/* 1888 */                           ChooseTag _jspx_th_c_005fchoose_005f19 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1889 */                           _jspx_th_c_005fchoose_005f19.setPageContext(_jspx_page_context);
/* 1890 */                           _jspx_th_c_005fchoose_005f19.setParent(_jspx_th_tiles_005fput_005f2);
/* 1891 */                           int _jspx_eval_c_005fchoose_005f19 = _jspx_th_c_005fchoose_005f19.doStartTag();
/* 1892 */                           if (_jspx_eval_c_005fchoose_005f19 != 0) {
/*      */                             for (;;) {
/* 1894 */                               out.write(10);
/*      */                               
/* 1896 */                               WhenTag _jspx_th_c_005fwhen_005f19 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1897 */                               _jspx_th_c_005fwhen_005f19.setPageContext(_jspx_page_context);
/* 1898 */                               _jspx_th_c_005fwhen_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/*      */                               
/* 1900 */                               _jspx_th_c_005fwhen_005f19.setTest("${param.method!='showMailServerConfiguration'}");
/* 1901 */                               int _jspx_eval_c_005fwhen_005f19 = _jspx_th_c_005fwhen_005f19.doStartTag();
/* 1902 */                               if (_jspx_eval_c_005fwhen_005f19 != 0) {
/*      */                                 for (;;) {
/* 1904 */                                   out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 1905 */                                   out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1906 */                                   out.write("\n    </a>    \n ");
/* 1907 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f19.doAfterBody();
/* 1908 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1912 */                               if (_jspx_th_c_005fwhen_005f19.doEndTag() == 5) {
/* 1913 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19); return;
/*      */                               }
/*      */                               
/* 1916 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 1917 */                               out.write(10);
/* 1918 */                               out.write(32);
/*      */                               
/* 1920 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f19 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1921 */                               _jspx_th_c_005fotherwise_005f19.setPageContext(_jspx_page_context);
/* 1922 */                               _jspx_th_c_005fotherwise_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/* 1923 */                               int _jspx_eval_c_005fotherwise_005f19 = _jspx_th_c_005fotherwise_005f19.doStartTag();
/* 1924 */                               if (_jspx_eval_c_005fotherwise_005f19 != 0) {
/*      */                                 for (;;) {
/* 1926 */                                   out.write(10);
/* 1927 */                                   out.write(9);
/* 1928 */                                   out.write(32);
/* 1929 */                                   out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1930 */                                   out.write(10);
/* 1931 */                                   out.write(32);
/* 1932 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f19.doAfterBody();
/* 1933 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 1937 */                               if (_jspx_th_c_005fotherwise_005f19.doEndTag() == 5) {
/* 1938 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19); return;
/*      */                               }
/*      */                               
/* 1941 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19);
/* 1942 */                               out.write(10);
/* 1943 */                               out.write(32);
/* 1944 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f19.doAfterBody();
/* 1945 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 1949 */                           if (_jspx_th_c_005fchoose_005f19.doEndTag() == 5) {
/* 1950 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19); return;
/*      */                           }
/*      */                           
/* 1953 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19);
/* 1954 */                           out.write("\n    </td>\n</tr>\n\n\n");
/* 1955 */                           if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 1956 */                             out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 1958 */                             ChooseTag _jspx_th_c_005fchoose_005f20 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1959 */                             _jspx_th_c_005fchoose_005f20.setPageContext(_jspx_page_context);
/* 1960 */                             _jspx_th_c_005fchoose_005f20.setParent(_jspx_th_tiles_005fput_005f2);
/* 1961 */                             int _jspx_eval_c_005fchoose_005f20 = _jspx_th_c_005fchoose_005f20.doStartTag();
/* 1962 */                             if (_jspx_eval_c_005fchoose_005f20 != 0) {
/*      */                               for (;;) {
/* 1964 */                                 out.write(10);
/*      */                                 
/* 1966 */                                 WhenTag _jspx_th_c_005fwhen_005f20 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1967 */                                 _jspx_th_c_005fwhen_005f20.setPageContext(_jspx_page_context);
/* 1968 */                                 _jspx_th_c_005fwhen_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/*      */                                 
/* 1970 */                                 _jspx_th_c_005fwhen_005f20.setTest("${param.method!='SMSServerConfiguration'}");
/* 1971 */                                 int _jspx_eval_c_005fwhen_005f20 = _jspx_th_c_005fwhen_005f20.doStartTag();
/* 1972 */                                 if (_jspx_eval_c_005fwhen_005f20 != 0) {
/*      */                                   for (;;) {
/* 1974 */                                     out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 1975 */                                     out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 1976 */                                     out.write("\n    </a>\n ");
/* 1977 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f20.doAfterBody();
/* 1978 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 1982 */                                 if (_jspx_th_c_005fwhen_005f20.doEndTag() == 5) {
/* 1983 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20); return;
/*      */                                 }
/*      */                                 
/* 1986 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20);
/* 1987 */                                 out.write(10);
/* 1988 */                                 out.write(32);
/*      */                                 
/* 1990 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f20 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1991 */                                 _jspx_th_c_005fotherwise_005f20.setPageContext(_jspx_page_context);
/* 1992 */                                 _jspx_th_c_005fotherwise_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/* 1993 */                                 int _jspx_eval_c_005fotherwise_005f20 = _jspx_th_c_005fotherwise_005f20.doStartTag();
/* 1994 */                                 if (_jspx_eval_c_005fotherwise_005f20 != 0) {
/*      */                                   for (;;) {
/* 1996 */                                     out.write("\n         ");
/* 1997 */                                     out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 1998 */                                     out.write(10);
/* 1999 */                                     out.write(32);
/* 2000 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f20.doAfterBody();
/* 2001 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2005 */                                 if (_jspx_th_c_005fotherwise_005f20.doEndTag() == 5) {
/* 2006 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20); return;
/*      */                                 }
/*      */                                 
/* 2009 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20);
/* 2010 */                                 out.write(10);
/* 2011 */                                 out.write(32);
/* 2012 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f20.doAfterBody();
/* 2013 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2017 */                             if (_jspx_th_c_005fchoose_005f20.doEndTag() == 5) {
/* 2018 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20); return;
/*      */                             }
/*      */                             
/* 2021 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20);
/* 2022 */                             out.write("\n    </td>\n</tr>\n");
/*      */                           }
/* 2024 */                           out.write("\n\n\n ");
/*      */                           
/* 2026 */                           if (!Constants.sqlManager)
/*      */                           {
/*      */ 
/* 2029 */                             out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                             
/* 2031 */                             ChooseTag _jspx_th_c_005fchoose_005f21 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2032 */                             _jspx_th_c_005fchoose_005f21.setPageContext(_jspx_page_context);
/* 2033 */                             _jspx_th_c_005fchoose_005f21.setParent(_jspx_th_tiles_005fput_005f2);
/* 2034 */                             int _jspx_eval_c_005fchoose_005f21 = _jspx_th_c_005fchoose_005f21.doStartTag();
/* 2035 */                             if (_jspx_eval_c_005fchoose_005f21 != 0) {
/*      */                               for (;;) {
/* 2037 */                                 out.write(10);
/*      */                                 
/* 2039 */                                 WhenTag _jspx_th_c_005fwhen_005f21 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2040 */                                 _jspx_th_c_005fwhen_005f21.setPageContext(_jspx_page_context);
/* 2041 */                                 _jspx_th_c_005fwhen_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/*      */                                 
/* 2043 */                                 _jspx_th_c_005fwhen_005f21.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 2044 */                                 int _jspx_eval_c_005fwhen_005f21 = _jspx_th_c_005fwhen_005f21.doStartTag();
/* 2045 */                                 if (_jspx_eval_c_005fwhen_005f21 != 0) {
/*      */                                   for (;;) {
/* 2047 */                                     out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 2048 */                                     out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2049 */                                     out.write("\n    </a>\n ");
/* 2050 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f21.doAfterBody();
/* 2051 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2055 */                                 if (_jspx_th_c_005fwhen_005f21.doEndTag() == 5) {
/* 2056 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21); return;
/*      */                                 }
/*      */                                 
/* 2059 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21);
/* 2060 */                                 out.write(10);
/* 2061 */                                 out.write(32);
/*      */                                 
/* 2063 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f21 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2064 */                                 _jspx_th_c_005fotherwise_005f21.setPageContext(_jspx_page_context);
/* 2065 */                                 _jspx_th_c_005fotherwise_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/* 2066 */                                 int _jspx_eval_c_005fotherwise_005f21 = _jspx_th_c_005fotherwise_005f21.doStartTag();
/* 2067 */                                 if (_jspx_eval_c_005fotherwise_005f21 != 0) {
/*      */                                   for (;;) {
/* 2069 */                                     out.write(10);
/* 2070 */                                     out.write(9);
/* 2071 */                                     out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 2072 */                                     out.write(10);
/* 2073 */                                     out.write(32);
/* 2074 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f21.doAfterBody();
/* 2075 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2079 */                                 if (_jspx_th_c_005fotherwise_005f21.doEndTag() == 5) {
/* 2080 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21); return;
/*      */                                 }
/*      */                                 
/* 2083 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21);
/* 2084 */                                 out.write(10);
/* 2085 */                                 out.write(32);
/* 2086 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f21.doAfterBody();
/* 2087 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2091 */                             if (_jspx_th_c_005fchoose_005f21.doEndTag() == 5) {
/* 2092 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21); return;
/*      */                             }
/*      */                             
/* 2095 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21);
/* 2096 */                             out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                             
/* 2098 */                             ChooseTag _jspx_th_c_005fchoose_005f22 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2099 */                             _jspx_th_c_005fchoose_005f22.setPageContext(_jspx_page_context);
/* 2100 */                             _jspx_th_c_005fchoose_005f22.setParent(_jspx_th_tiles_005fput_005f2);
/* 2101 */                             int _jspx_eval_c_005fchoose_005f22 = _jspx_th_c_005fchoose_005f22.doStartTag();
/* 2102 */                             if (_jspx_eval_c_005fchoose_005f22 != 0) {
/*      */                               for (;;) {
/* 2104 */                                 out.write(10);
/*      */                                 
/* 2106 */                                 WhenTag _jspx_th_c_005fwhen_005f22 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2107 */                                 _jspx_th_c_005fwhen_005f22.setPageContext(_jspx_page_context);
/* 2108 */                                 _jspx_th_c_005fwhen_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/*      */                                 
/* 2110 */                                 _jspx_th_c_005fwhen_005f22.setTest("${uri !='/Upload.do'}");
/* 2111 */                                 int _jspx_eval_c_005fwhen_005f22 = _jspx_th_c_005fwhen_005f22.doStartTag();
/* 2112 */                                 if (_jspx_eval_c_005fwhen_005f22 != 0) {
/*      */                                   for (;;) {
/* 2114 */                                     out.write("   \n        ");
/*      */                                     
/* 2116 */                                     AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2117 */                                     _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 2118 */                                     _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f22);
/*      */                                     
/* 2120 */                                     _jspx_th_am_005fadminlink_005f0.setHref("/Upload.do");
/*      */                                     
/* 2122 */                                     _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 2123 */                                     int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 2124 */                                     if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 2125 */                                       if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2126 */                                         out = _jspx_page_context.pushBody();
/* 2127 */                                         _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 2128 */                                         _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2131 */                                         out.write("\n           ");
/* 2132 */                                         out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 2133 */                                         out.write("\n            ");
/* 2134 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 2135 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2138 */                                       if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 2139 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2142 */                                     if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 2143 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                     }
/*      */                                     
/* 2146 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 2147 */                                     out.write(10);
/* 2148 */                                     out.write(10);
/* 2149 */                                     out.write(32);
/* 2150 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f22.doAfterBody();
/* 2151 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2155 */                                 if (_jspx_th_c_005fwhen_005f22.doEndTag() == 5) {
/* 2156 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22); return;
/*      */                                 }
/*      */                                 
/* 2159 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22);
/* 2160 */                                 out.write(10);
/* 2161 */                                 out.write(32);
/*      */                                 
/* 2163 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f22 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2164 */                                 _jspx_th_c_005fotherwise_005f22.setPageContext(_jspx_page_context);
/* 2165 */                                 _jspx_th_c_005fotherwise_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/* 2166 */                                 int _jspx_eval_c_005fotherwise_005f22 = _jspx_th_c_005fotherwise_005f22.doStartTag();
/* 2167 */                                 if (_jspx_eval_c_005fotherwise_005f22 != 0) {
/*      */                                   for (;;) {
/* 2169 */                                     out.write(10);
/* 2170 */                                     out.write(9);
/* 2171 */                                     out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 2172 */                                     out.write(10);
/* 2173 */                                     out.write(32);
/* 2174 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f22.doAfterBody();
/* 2175 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2179 */                                 if (_jspx_th_c_005fotherwise_005f22.doEndTag() == 5) {
/* 2180 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22); return;
/*      */                                 }
/*      */                                 
/* 2183 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22);
/* 2184 */                                 out.write(10);
/* 2185 */                                 out.write(32);
/* 2186 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f22.doAfterBody();
/* 2187 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2191 */                             if (_jspx_th_c_005fchoose_005f22.doEndTag() == 5) {
/* 2192 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22); return;
/*      */                             }
/*      */                             
/* 2195 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22);
/* 2196 */                             out.write("\n    </td>\n</tr>\n \n ");
/*      */                           }
/*      */                           
/*      */ 
/* 2200 */                           out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                           
/* 2202 */                           ChooseTag _jspx_th_c_005fchoose_005f23 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2203 */                           _jspx_th_c_005fchoose_005f23.setPageContext(_jspx_page_context);
/* 2204 */                           _jspx_th_c_005fchoose_005f23.setParent(_jspx_th_tiles_005fput_005f2);
/* 2205 */                           int _jspx_eval_c_005fchoose_005f23 = _jspx_th_c_005fchoose_005f23.doStartTag();
/* 2206 */                           if (_jspx_eval_c_005fchoose_005f23 != 0) {
/*      */                             for (;;) {
/* 2208 */                               out.write(10);
/*      */                               
/* 2210 */                               WhenTag _jspx_th_c_005fwhen_005f23 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2211 */                               _jspx_th_c_005fwhen_005f23.setPageContext(_jspx_page_context);
/* 2212 */                               _jspx_th_c_005fwhen_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/*      */                               
/* 2214 */                               _jspx_th_c_005fwhen_005f23.setTest("${uri !='/admin/userconfiguration.do'}");
/* 2215 */                               int _jspx_eval_c_005fwhen_005f23 = _jspx_th_c_005fwhen_005f23.doStartTag();
/* 2216 */                               if (_jspx_eval_c_005fwhen_005f23 != 0) {
/*      */                                 for (;;) {
/* 2218 */                                   out.write("\n    \n        ");
/*      */                                   
/* 2220 */                                   AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2221 */                                   _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 2222 */                                   _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f23);
/*      */                                   
/* 2224 */                                   _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                                   
/* 2226 */                                   _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 2227 */                                   int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 2228 */                                   if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 2229 */                                     if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2230 */                                       out = _jspx_page_context.pushBody();
/* 2231 */                                       _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 2232 */                                       _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 2235 */                                       out.write("\n       ");
/* 2236 */                                       out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2237 */                                       out.write("\n        ");
/* 2238 */                                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 2239 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 2242 */                                     if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2243 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 2246 */                                   if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 2247 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                                   }
/*      */                                   
/* 2250 */                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 2251 */                                   out.write(10);
/* 2252 */                                   out.write(10);
/* 2253 */                                   out.write(32);
/* 2254 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f23.doAfterBody();
/* 2255 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2259 */                               if (_jspx_th_c_005fwhen_005f23.doEndTag() == 5) {
/* 2260 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23); return;
/*      */                               }
/*      */                               
/* 2263 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23);
/* 2264 */                               out.write(10);
/* 2265 */                               out.write(32);
/*      */                               
/* 2267 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f23 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2268 */                               _jspx_th_c_005fotherwise_005f23.setPageContext(_jspx_page_context);
/* 2269 */                               _jspx_th_c_005fotherwise_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/* 2270 */                               int _jspx_eval_c_005fotherwise_005f23 = _jspx_th_c_005fotherwise_005f23.doStartTag();
/* 2271 */                               if (_jspx_eval_c_005fotherwise_005f23 != 0) {
/*      */                                 for (;;) {
/* 2273 */                                   out.write(10);
/* 2274 */                                   out.write(9);
/* 2275 */                                   out.write(32);
/* 2276 */                                   out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2277 */                                   out.write(10);
/* 2278 */                                   out.write(32);
/* 2279 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f23.doAfterBody();
/* 2280 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2284 */                               if (_jspx_th_c_005fotherwise_005f23.doEndTag() == 5) {
/* 2285 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23); return;
/*      */                               }
/*      */                               
/* 2288 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23);
/* 2289 */                               out.write(10);
/* 2290 */                               out.write(32);
/* 2291 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f23.doAfterBody();
/* 2292 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2296 */                           if (_jspx_th_c_005fchoose_005f23.doEndTag() == 5) {
/* 2297 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23); return;
/*      */                           }
/*      */                           
/* 2300 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23);
/* 2301 */                           out.write("\n    </td>\n</tr>\n   \n\n ");
/* 2302 */                           if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 2303 */                             out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                             
/* 2305 */                             ChooseTag _jspx_th_c_005fchoose_005f24 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2306 */                             _jspx_th_c_005fchoose_005f24.setPageContext(_jspx_page_context);
/* 2307 */                             _jspx_th_c_005fchoose_005f24.setParent(_jspx_th_tiles_005fput_005f2);
/* 2308 */                             int _jspx_eval_c_005fchoose_005f24 = _jspx_th_c_005fchoose_005f24.doStartTag();
/* 2309 */                             if (_jspx_eval_c_005fchoose_005f24 != 0) {
/*      */                               for (;;) {
/* 2311 */                                 out.write("\n   ");
/*      */                                 
/* 2313 */                                 WhenTag _jspx_th_c_005fwhen_005f24 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2314 */                                 _jspx_th_c_005fwhen_005f24.setPageContext(_jspx_page_context);
/* 2315 */                                 _jspx_th_c_005fwhen_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/*      */                                 
/* 2317 */                                 _jspx_th_c_005fwhen_005f24.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 2318 */                                 int _jspx_eval_c_005fwhen_005f24 = _jspx_th_c_005fwhen_005f24.doStartTag();
/* 2319 */                                 if (_jspx_eval_c_005fwhen_005f24 != 0) {
/*      */                                   for (;;) {
/* 2321 */                                     out.write("\n    ");
/*      */                                     
/* 2323 */                                     AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2324 */                                     _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 2325 */                                     _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f24);
/*      */                                     
/* 2327 */                                     _jspx_th_am_005fadminlink_005f2.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                                     
/* 2329 */                                     _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 2330 */                                     int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 2331 */                                     if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 2332 */                                       if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 2333 */                                         out = _jspx_page_context.pushBody();
/* 2334 */                                         _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 2335 */                                         _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2338 */                                         out.write(10);
/* 2339 */                                         out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 2340 */                                         out.write("\n    ");
/* 2341 */                                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 2342 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2345 */                                       if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 2346 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2349 */                                     if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 2350 */                                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                                     }
/*      */                                     
/* 2353 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 2354 */                                     out.write("\n   ");
/* 2355 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f24.doAfterBody();
/* 2356 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2360 */                                 if (_jspx_th_c_005fwhen_005f24.doEndTag() == 5) {
/* 2361 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24); return;
/*      */                                 }
/*      */                                 
/* 2364 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24);
/* 2365 */                                 out.write("\n   ");
/*      */                                 
/* 2367 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f24 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2368 */                                 _jspx_th_c_005fotherwise_005f24.setPageContext(_jspx_page_context);
/* 2369 */                                 _jspx_th_c_005fotherwise_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/* 2370 */                                 int _jspx_eval_c_005fotherwise_005f24 = _jspx_th_c_005fotherwise_005f24.doStartTag();
/* 2371 */                                 if (_jspx_eval_c_005fotherwise_005f24 != 0) {
/*      */                                   for (;;) {
/* 2373 */                                     out.write(10);
/* 2374 */                                     out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 2375 */                                     out.write("\n   ");
/* 2376 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f24.doAfterBody();
/* 2377 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2381 */                                 if (_jspx_th_c_005fotherwise_005f24.doEndTag() == 5) {
/* 2382 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24); return;
/*      */                                 }
/*      */                                 
/* 2385 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24);
/* 2386 */                                 out.write(10);
/* 2387 */                                 out.write(32);
/* 2388 */                                 out.write(32);
/* 2389 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f24.doAfterBody();
/* 2390 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2394 */                             if (_jspx_th_c_005fchoose_005f24.doEndTag() == 5) {
/* 2395 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24); return;
/*      */                             }
/*      */                             
/* 2398 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24);
/* 2399 */                             out.write("\n </td>\n</tr>\n  ");
/*      */                           }
/* 2401 */                           out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                           
/* 2403 */                           ChooseTag _jspx_th_c_005fchoose_005f25 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2404 */                           _jspx_th_c_005fchoose_005f25.setPageContext(_jspx_page_context);
/* 2405 */                           _jspx_th_c_005fchoose_005f25.setParent(_jspx_th_tiles_005fput_005f2);
/* 2406 */                           int _jspx_eval_c_005fchoose_005f25 = _jspx_th_c_005fchoose_005f25.doStartTag();
/* 2407 */                           if (_jspx_eval_c_005fchoose_005f25 != 0) {
/*      */                             for (;;) {
/* 2409 */                               out.write("\n   ");
/*      */                               
/* 2411 */                               WhenTag _jspx_th_c_005fwhen_005f25 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2412 */                               _jspx_th_c_005fwhen_005f25.setPageContext(_jspx_page_context);
/* 2413 */                               _jspx_th_c_005fwhen_005f25.setParent(_jspx_th_c_005fchoose_005f25);
/*      */                               
/* 2415 */                               _jspx_th_c_005fwhen_005f25.setTest("${param.method!='showDataCleanUp'}");
/* 2416 */                               int _jspx_eval_c_005fwhen_005f25 = _jspx_th_c_005fwhen_005f25.doStartTag();
/* 2417 */                               if (_jspx_eval_c_005fwhen_005f25 != 0) {
/*      */                                 for (;;) {
/* 2419 */                                   out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 2420 */                                   out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 2421 */                                   out.write("\n    </a>\n   ");
/* 2422 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f25.doAfterBody();
/* 2423 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2427 */                               if (_jspx_th_c_005fwhen_005f25.doEndTag() == 5) {
/* 2428 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f25); return;
/*      */                               }
/*      */                               
/* 2431 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f25);
/* 2432 */                               out.write("\n   ");
/*      */                               
/* 2434 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f25 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2435 */                               _jspx_th_c_005fotherwise_005f25.setPageContext(_jspx_page_context);
/* 2436 */                               _jspx_th_c_005fotherwise_005f25.setParent(_jspx_th_c_005fchoose_005f25);
/* 2437 */                               int _jspx_eval_c_005fotherwise_005f25 = _jspx_th_c_005fotherwise_005f25.doStartTag();
/* 2438 */                               if (_jspx_eval_c_005fotherwise_005f25 != 0) {
/*      */                                 for (;;) {
/* 2440 */                                   out.write(10);
/* 2441 */                                   out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 2442 */                                   out.write("\n   ");
/* 2443 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f25.doAfterBody();
/* 2444 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2448 */                               if (_jspx_th_c_005fotherwise_005f25.doEndTag() == 5) {
/* 2449 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f25); return;
/*      */                               }
/*      */                               
/* 2452 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f25);
/* 2453 */                               out.write(10);
/* 2454 */                               out.write(32);
/* 2455 */                               out.write(32);
/* 2456 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f25.doAfterBody();
/* 2457 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2461 */                           if (_jspx_th_c_005fchoose_005f25.doEndTag() == 5) {
/* 2462 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f25); return;
/*      */                           }
/*      */                           
/* 2465 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f25);
/* 2466 */                           out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 2467 */                           out.write("\t\t\n\t  ");
/*      */                         }
/* 2469 */                         out.write("\t\t\n\n\t  <br>\t\t\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables hideQuikNotes\"><tr> \t\t\n\t    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 2470 */                         out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 2471 */                         out.write("</td>\t\t\n\t    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 2472 */                         if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                           return;
/* 2474 */                         out.write("/img_quicknote.gif\" hspace=\"5\"></td>\t\t\n\t  </tr>\t\t\n\t  <tr> \t\t\n\t    <td colspan=\"2\" class=\"quicknote\">");
/* 2475 */                         out.print(qNote);
/* 2476 */                         out.write(" </td>\t\t\n\t  </tr>\t\t\n\t</table>\t\t\n\t");
/* 2477 */                         int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 2478 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 2481 */                       if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2482 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 2485 */                     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2486 */                       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                     }
/*      */                     
/* 2489 */                     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 2490 */                     out.write("      \n\n\n");
/*      */                     
/* 2492 */                     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2493 */                     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2494 */                     _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                     
/* 2496 */                     _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                     
/* 2498 */                     _jspx_th_tiles_005fput_005f3.setType("string");
/* 2499 */                     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2500 */                     if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2501 */                       if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2502 */                         out = _jspx_page_context.pushBody();
/* 2503 */                         _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2504 */                         _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/* 2507 */                         out.write("\n\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t");
/*      */                         
/* 2509 */                         if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */                         {
/* 2511 */                           out.write("\n\t  <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2512 */                           out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 2513 */                           out.write(" &gt; <span class=\"bcactive\">");
/* 2514 */                           out.print(FormatUtil.getString("am.webclient.mailsettings.configuremailserver.text"));
/* 2515 */                           out.write("</span></td>\n\t  ");
/*      */                         }
/*      */                         else
/*      */                         {
/* 2519 */                           out.write("\n\t   <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2520 */                           out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 2521 */                           out.write(" &gt; <span class=\"bcactive\">");
/* 2522 */                           out.print(heading);
/* 2523 */                           out.write("</span></td>\n\t   ");
/*      */                         }
/* 2525 */                         out.write("\n\t</tr>\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"70%\" valign=\"top\">\n");
/*      */                         try
/*      */                         {
/* 2528 */                           if ((request.getAttribute("sms") != null) && (((String)request.getAttribute("sms")).equals("true"))) {
/* 2529 */                             out.write(10);
/* 2530 */                             request.setAttribute("sms", "true");
/* 2531 */                             out.write(10);
/* 2532 */                             out.write(10);
/* 2533 */                             out.write(10);
/* 2534 */                             JspRuntimeLibrary.include(request, response, "/jsp/SMSServerConfigUserArea.jsp", out, false);
/* 2535 */                             out.write("\n</td>\n<td width=\"30%\" valign=\"top\" class=\"itadmin-hide\">\n");
/* 2536 */                             JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.smsSettings.helpcard")), request.getCharacterEncoding()), out, false);
/* 2537 */                             out.write(10);
/* 2538 */                             out.write(10);
/*      */                           }
/*      */                           else {
/* 2541 */                             request.setAttribute("sms", "false");
/* 2542 */                             out.write(10);
/* 2543 */                             out.write(10);
/* 2544 */                             out.write(10);
/* 2545 */                             JspRuntimeLibrary.include(request, response, "/jsp/MailServerConfigUserArea.jsp", out, false);
/* 2546 */                             out.write("\n</td>\n<td width=\"30%\" valign=\"top\">\n");
/* 2547 */                             JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.mailSettings.helpcard")), request.getCharacterEncoding()), out, false);
/* 2548 */                             out.write(10);
/* 2549 */                             out.write(10);
/*      */                           }
/*      */                           
/*      */                         }
/*      */                         catch (Exception ex)
/*      */                         {
/* 2555 */                           ex.printStackTrace();
/*      */                         }
/*      */                         
/* 2558 */                         out.write("\n\n</td>\n</tr>\n</table>\n");
/* 2559 */                         int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 2560 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/* 2563 */                       if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2564 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/* 2567 */                     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 2568 */                       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                     }
/*      */                     
/* 2571 */                     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 2572 */                     out.write(" \n    ");
/* 2573 */                     if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                       return;
/* 2575 */                     out.write(10);
/* 2576 */                     int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2577 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2581 */                 if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2582 */                   this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0); return;
/*      */                 }
/*      */                 
/* 2585 */                 this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2586 */                 out.write("\n\n    ");
/* 2587 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2588 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2592 */             if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2593 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */             }
/*      */             
/* 2596 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2597 */             out.write("\n    ");
/* 2598 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2599 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2603 */         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2604 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */         }
/*      */         else {
/* 2607 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2608 */           out.write(10);
/*      */         }
/* 2610 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2611 */         out = _jspx_out;
/* 2612 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2613 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2614 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2617 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2623 */     PageContext pageContext = _jspx_page_context;
/* 2624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2626 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2627 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2628 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2630 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2632 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2633 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2634 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2635 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2636 */       return true;
/*      */     }
/* 2638 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2639 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2644 */     PageContext pageContext = _jspx_page_context;
/* 2645 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2647 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2648 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2649 */     _jspx_th_tiles_005fput_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2651 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 2653 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=3");
/* 2654 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2655 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2656 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2657 */       return true;
/*      */     }
/* 2659 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2665 */     PageContext pageContext = _jspx_page_context;
/* 2666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2668 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2669 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2670 */     _jspx_th_c_005fout_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 2672 */     _jspx_th_c_005fout_005f1.setValue("${selectedskin}");
/*      */     
/* 2674 */     _jspx_th_c_005fout_005f1.setDefault("${initParam.defaultSkin}");
/* 2675 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2676 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2677 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2678 */       return true;
/*      */     }
/* 2680 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2681 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2686 */     PageContext pageContext = _jspx_page_context;
/* 2687 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2689 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2690 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 2691 */     _jspx_th_tiles_005fput_005f4.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2693 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 2695 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 2696 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 2697 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 2698 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 2699 */       return true;
/*      */     }
/* 2701 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 2702 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MailServerConfig_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */