/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.server.framework.FreeEditionDetails;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.EnterpriseAdminLink;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.io.IOException;
/*      */ import java.util.Map;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class AdminLeftLinks_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   24 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   30 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*   31 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*   32 */     _jspx_dependants.put("/jsp/includes/EnterpriseAdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*   47 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   51 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   52 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   53 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   54 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   55 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   56 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   59 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   63 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   64 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   65 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   66 */     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.release();
/*   67 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   68 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*   69 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*   76 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   79 */     JspWriter out = null;
/*   80 */     Object page = this;
/*   81 */     JspWriter _jspx_out = null;
/*   82 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*   86 */       response.setContentType("text/html");
/*   87 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*   89 */       _jspx_page_context = pageContext;
/*   90 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*   91 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*   92 */       session = pageContext.getSession();
/*   93 */       out = pageContext.getOut();
/*   94 */       _jspx_out = out;
/*      */       
/*   96 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */       
/*   98 */       String resourceName = request.getParameter("type");
/*   99 */       String appname = request.getParameter("name");
/*  100 */       String haid = request.getParameter("haid");
/*      */       
/*  102 */       out.write(10);
/*      */       
/*  104 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*      */ 
/*  107 */         out.write(10);
/*  108 */         out.write(32);
/*  109 */         out.write(32);
/*  110 */         out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/*      */         
/*      */ 
/*  113 */         String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */         
/*  115 */         out.write("\n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n <tr>\n\t     <td height=\"21\"  class=\"leftlinksheading\">");
/*  116 */         out.print(FormatUtil.getString("am.webclient.admin.heading"));
/*  117 */         out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */         
/*  119 */         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  120 */         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  121 */         _jspx_th_c_005fchoose_005f0.setParent(null);
/*  122 */         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  123 */         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */           for (;;) {
/*  125 */             out.write(10);
/*      */             
/*  127 */             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  128 */             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  129 */             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */             
/*  131 */             _jspx_th_c_005fwhen_005f0.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/*  132 */             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  133 */             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */               for (;;) {
/*  135 */                 out.write("\n        ");
/*  136 */                 out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  137 */                 out.write(10);
/*  138 */                 out.write(32);
/*  139 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  140 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  144 */             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  145 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */             }
/*      */             
/*  148 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  149 */             out.write(10);
/*  150 */             out.write(32);
/*      */             
/*  152 */             OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  153 */             _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  154 */             _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  155 */             int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  156 */             if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */               for (;;) {
/*  158 */                 out.write("\n     ");
/*      */                 
/*  160 */                 EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f0 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  161 */                 _jspx_th_ea_005feeadminlink_005f0.setPageContext(_jspx_page_context);
/*  162 */                 _jspx_th_ea_005feeadminlink_005f0.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                 
/*  164 */                 _jspx_th_ea_005feeadminlink_005f0.setHref("/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general");
/*      */                 
/*  166 */                 _jspx_th_ea_005feeadminlink_005f0.setEnableClass("new-left-links");
/*  167 */                 int _jspx_eval_ea_005feeadminlink_005f0 = _jspx_th_ea_005feeadminlink_005f0.doStartTag();
/*  168 */                 if (_jspx_eval_ea_005feeadminlink_005f0 != 0) {
/*  169 */                   if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/*  170 */                     out = _jspx_page_context.pushBody();
/*  171 */                     _jspx_th_ea_005feeadminlink_005f0.setBodyContent((BodyContent)out);
/*  172 */                     _jspx_th_ea_005feeadminlink_005f0.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  175 */                     out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/*  176 */                     int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f0.doAfterBody();
/*  177 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  180 */                   if (_jspx_eval_ea_005feeadminlink_005f0 != 1) {
/*  181 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  184 */                 if (_jspx_th_ea_005feeadminlink_005f0.doEndTag() == 5) {
/*  185 */                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0); return;
/*      */                 }
/*      */                 
/*  188 */                 this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f0);
/*  189 */                 out.write(10);
/*  190 */                 out.write(32);
/*  191 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  192 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  196 */             if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  197 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */             }
/*      */             
/*  200 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  201 */             out.write(10);
/*  202 */             out.write(32);
/*  203 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  204 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  208 */         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  209 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */         }
/*      */         
/*  212 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  213 */         out.write("\n    </td>\n</tr>\n\n<tr>\n\n<tr>\n    <td class=\"leftlinkstd\" >\n");
/*      */         
/*  215 */         ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  216 */         _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  217 */         _jspx_th_c_005fchoose_005f1.setParent(null);
/*  218 */         int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  219 */         if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */           for (;;) {
/*  221 */             out.write(10);
/*      */             
/*  223 */             WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  224 */             _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  225 */             _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/*  227 */             _jspx_th_c_005fwhen_005f1.setTest("${param.method!='showMailServerConfiguration'}");
/*  228 */             int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  229 */             if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */               for (;;) {
/*  231 */                 out.write("\n    ");
/*      */                 
/*  233 */                 EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f1 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  234 */                 _jspx_th_ea_005feeadminlink_005f1.setPageContext(_jspx_page_context);
/*  235 */                 _jspx_th_ea_005feeadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                 
/*  237 */                 _jspx_th_ea_005feeadminlink_005f1.setHref("/adminAction.do?method=showMailServerConfiguration");
/*      */                 
/*  239 */                 _jspx_th_ea_005feeadminlink_005f1.setEnableClass("new-left-links");
/*  240 */                 int _jspx_eval_ea_005feeadminlink_005f1 = _jspx_th_ea_005feeadminlink_005f1.doStartTag();
/*  241 */                 if (_jspx_eval_ea_005feeadminlink_005f1 != 0) {
/*  242 */                   if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/*  243 */                     out = _jspx_page_context.pushBody();
/*  244 */                     _jspx_th_ea_005feeadminlink_005f1.setBodyContent((BodyContent)out);
/*  245 */                     _jspx_th_ea_005feeadminlink_005f1.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  248 */                     out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/*  249 */                     int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f1.doAfterBody();
/*  250 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  253 */                   if (_jspx_eval_ea_005feeadminlink_005f1 != 1) {
/*  254 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  257 */                 if (_jspx_th_ea_005feeadminlink_005f1.doEndTag() == 5) {
/*  258 */                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1); return;
/*      */                 }
/*      */                 
/*  261 */                 this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f1);
/*  262 */                 out.write(10);
/*  263 */                 out.write(32);
/*  264 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  265 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  269 */             if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  270 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */             }
/*      */             
/*  273 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  274 */             out.write(10);
/*  275 */             out.write(32);
/*      */             
/*  277 */             OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  278 */             _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  279 */             _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  280 */             int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  281 */             if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */               for (;;) {
/*  283 */                 out.write(10);
/*  284 */                 out.write(9);
/*  285 */                 out.write(32);
/*  286 */                 out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/*  287 */                 out.write(10);
/*  288 */                 out.write(32);
/*  289 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  290 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  294 */             if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  295 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */             }
/*      */             
/*  298 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  299 */             out.write(10);
/*  300 */             out.write(32);
/*  301 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  302 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  306 */         if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  307 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */         }
/*      */         
/*  310 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  311 */         out.write("\n    </td>\n</tr>\n\n");
/*  312 */         if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/*  313 */           out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */           
/*  315 */           ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  316 */           _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  317 */           _jspx_th_c_005fchoose_005f2.setParent(null);
/*  318 */           int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  319 */           if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */             for (;;) {
/*  321 */               out.write(10);
/*      */               
/*  323 */               WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  324 */               _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  325 */               _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */               
/*  327 */               _jspx_th_c_005fwhen_005f2.setTest("${param.method!='SMSServerConfiguration'}");
/*  328 */               int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  329 */               if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                 for (;;) {
/*  331 */                   out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/*  332 */                   out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/*  333 */                   out.write("\n    </a>\n ");
/*  334 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  335 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  339 */               if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  340 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */               }
/*      */               
/*  343 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  344 */               out.write(10);
/*  345 */               out.write(32);
/*      */               
/*  347 */               OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  348 */               _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  349 */               _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  350 */               int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  351 */               if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                 for (;;) {
/*  353 */                   out.write("\n         ");
/*  354 */                   out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/*  355 */                   out.write(10);
/*  356 */                   out.write(32);
/*  357 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  358 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  362 */               if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  363 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */               }
/*      */               
/*  366 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  367 */               out.write(10);
/*  368 */               out.write(32);
/*  369 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  370 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  374 */           if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  375 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */           }
/*      */           
/*  378 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  379 */           out.write("\n    </td>\n</tr>\n");
/*      */         }
/*  381 */         out.write("\n\n\n<tr>\n\n    <td class=\"leftlinkstd\">\n");
/*      */         
/*  383 */         ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  384 */         _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/*  385 */         _jspx_th_c_005fchoose_005f3.setParent(null);
/*  386 */         int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/*  387 */         if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */           for (;;) {
/*  389 */             out.write(10);
/*      */             
/*  391 */             WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  392 */             _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  393 */             _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */             
/*  395 */             _jspx_th_c_005fwhen_005f3.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/*  396 */             int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  397 */             if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */               for (;;) {
/*  399 */                 out.write("\n    ");
/*      */                 
/*  401 */                 EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f2 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  402 */                 _jspx_th_ea_005feeadminlink_005f2.setPageContext(_jspx_page_context);
/*  403 */                 _jspx_th_ea_005feeadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                 
/*  405 */                 _jspx_th_ea_005feeadminlink_005f2.setHref("/jsp/ProxyConfiguration.jsp");
/*      */                 
/*  407 */                 _jspx_th_ea_005feeadminlink_005f2.setEnableClass("new-left-links");
/*  408 */                 int _jspx_eval_ea_005feeadminlink_005f2 = _jspx_th_ea_005feeadminlink_005f2.doStartTag();
/*  409 */                 if (_jspx_eval_ea_005feeadminlink_005f2 != 0) {
/*  410 */                   if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/*  411 */                     out = _jspx_page_context.pushBody();
/*  412 */                     _jspx_th_ea_005feeadminlink_005f2.setBodyContent((BodyContent)out);
/*  413 */                     _jspx_th_ea_005feeadminlink_005f2.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  416 */                     out.write("\n    ");
/*  417 */                     out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/*  418 */                     out.write("\n    ");
/*  419 */                     int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f2.doAfterBody();
/*  420 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  423 */                   if (_jspx_eval_ea_005feeadminlink_005f2 != 1) {
/*  424 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  427 */                 if (_jspx_th_ea_005feeadminlink_005f2.doEndTag() == 5) {
/*  428 */                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2); return;
/*      */                 }
/*      */                 
/*  431 */                 this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f2);
/*  432 */                 out.write(10);
/*  433 */                 out.write(32);
/*  434 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  435 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  439 */             if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  440 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */             }
/*      */             
/*  443 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  444 */             out.write(10);
/*  445 */             out.write(32);
/*      */             
/*  447 */             OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  448 */             _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  449 */             _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*  450 */             int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  451 */             if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */               for (;;) {
/*  453 */                 out.write(10);
/*  454 */                 out.write(9);
/*  455 */                 out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/*  456 */                 out.write(10);
/*  457 */                 out.write(32);
/*  458 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/*  459 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  463 */             if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/*  464 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */             }
/*      */             
/*  467 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/*  468 */             out.write(10);
/*  469 */             out.write(32);
/*  470 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/*  471 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  475 */         if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/*  476 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */         }
/*      */         
/*  479 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*  480 */         out.write("\n    </td>\n</tr>\n<tr>\n\n<td class=\"leftlinkstd\">\n");
/*      */         
/*  482 */         ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  483 */         _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  484 */         _jspx_th_c_005fchoose_005f4.setParent(null);
/*  485 */         int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  486 */         if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */           for (;;) {
/*  488 */             out.write(10);
/*      */             
/*  490 */             WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  491 */             _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  492 */             _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */             
/*  494 */             _jspx_th_c_005fwhen_005f4.setTest("${uri !='/admin/userconfiguration.do'}");
/*  495 */             int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  496 */             if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */               for (;;) {
/*  498 */                 out.write("\n\n        ");
/*      */                 
/*  500 */                 EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f3 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  501 */                 _jspx_th_ea_005feeadminlink_005f3.setPageContext(_jspx_page_context);
/*  502 */                 _jspx_th_ea_005feeadminlink_005f3.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                 
/*  504 */                 _jspx_th_ea_005feeadminlink_005f3.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                 
/*  506 */                 _jspx_th_ea_005feeadminlink_005f3.setEnableClass("new-left-links");
/*  507 */                 int _jspx_eval_ea_005feeadminlink_005f3 = _jspx_th_ea_005feeadminlink_005f3.doStartTag();
/*  508 */                 if (_jspx_eval_ea_005feeadminlink_005f3 != 0) {
/*  509 */                   if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/*  510 */                     out = _jspx_page_context.pushBody();
/*  511 */                     _jspx_th_ea_005feeadminlink_005f3.setBodyContent((BodyContent)out);
/*  512 */                     _jspx_th_ea_005feeadminlink_005f3.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  515 */                     out.write("\n       ");
/*  516 */                     out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/*  517 */                     out.write("\n        ");
/*  518 */                     int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f3.doAfterBody();
/*  519 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  522 */                   if (_jspx_eval_ea_005feeadminlink_005f3 != 1) {
/*  523 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  526 */                 if (_jspx_th_ea_005feeadminlink_005f3.doEndTag() == 5) {
/*  527 */                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3); return;
/*      */                 }
/*      */                 
/*  530 */                 this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f3);
/*  531 */                 out.write(10);
/*  532 */                 out.write(10);
/*  533 */                 out.write(32);
/*  534 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  535 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  539 */             if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  540 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */             }
/*      */             
/*  543 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  544 */             out.write(10);
/*  545 */             out.write(32);
/*      */             
/*  547 */             OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  548 */             _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  549 */             _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*  550 */             int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  551 */             if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */               for (;;) {
/*  553 */                 out.write(10);
/*  554 */                 out.write(9);
/*  555 */                 out.write(32);
/*  556 */                 out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/*  557 */                 out.write(10);
/*  558 */                 out.write(32);
/*  559 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  560 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  564 */             if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  565 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */             }
/*      */             
/*  568 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  569 */             out.write(10);
/*  570 */             out.write(32);
/*  571 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/*  572 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  576 */         if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/*  577 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */         }
/*      */         
/*  580 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*  581 */         out.write("\n</td>\n</tr>\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */         
/*  583 */         ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  584 */         _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  585 */         _jspx_th_c_005fchoose_005f5.setParent(null);
/*  586 */         int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  587 */         if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */           for (;;) {
/*  589 */             out.write("\n   ");
/*      */             
/*  591 */             WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  592 */             _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/*  593 */             _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */             
/*  595 */             _jspx_th_c_005fwhen_005f5.setTest("${param.method!='showManagedServers'}");
/*  596 */             int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/*  597 */             if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */               for (;;) {
/*  599 */                 out.write("\n    ");
/*      */                 
/*  601 */                 EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f4 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  602 */                 _jspx_th_ea_005feeadminlink_005f4.setPageContext(_jspx_page_context);
/*  603 */                 _jspx_th_ea_005feeadminlink_005f4.setParent(_jspx_th_c_005fwhen_005f5);
/*      */                 
/*  605 */                 _jspx_th_ea_005feeadminlink_005f4.setHref("/adminAction.do?method=showManagedServers");
/*      */                 
/*  607 */                 _jspx_th_ea_005feeadminlink_005f4.setEnableClass("new-left-links");
/*  608 */                 int _jspx_eval_ea_005feeadminlink_005f4 = _jspx_th_ea_005feeadminlink_005f4.doStartTag();
/*  609 */                 if (_jspx_eval_ea_005feeadminlink_005f4 != 0) {
/*  610 */                   if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/*  611 */                     out = _jspx_page_context.pushBody();
/*  612 */                     _jspx_th_ea_005feeadminlink_005f4.setBodyContent((BodyContent)out);
/*  613 */                     _jspx_th_ea_005feeadminlink_005f4.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  616 */                     out.write("\n     ");
/*  617 */                     out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/*  618 */                     out.write(10);
/*  619 */                     out.write(9);
/*  620 */                     int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f4.doAfterBody();
/*  621 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  624 */                   if (_jspx_eval_ea_005feeadminlink_005f4 != 1) {
/*  625 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  628 */                 if (_jspx_th_ea_005feeadminlink_005f4.doEndTag() == 5) {
/*  629 */                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4); return;
/*      */                 }
/*      */                 
/*  632 */                 this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f4);
/*  633 */                 out.write("\n   ");
/*  634 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/*  635 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  639 */             if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/*  640 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */             }
/*      */             
/*  643 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*  644 */             out.write("\n   ");
/*      */             
/*  646 */             OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  647 */             _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  648 */             _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*  649 */             int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  650 */             if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */               for (;;) {
/*  652 */                 out.write("\n     ");
/*  653 */                 out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/*  654 */                 out.write("\n   ");
/*  655 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  656 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  660 */             if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  661 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */             }
/*      */             
/*  664 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  665 */             out.write("\n   ");
/*  666 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  667 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  671 */         if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  672 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */         }
/*      */         
/*  675 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  676 */         out.write("\n  </td>\n</tr>\n\n\n<td class=\"leftlinkstd\" >\n");
/*      */         
/*  678 */         ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  679 */         _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/*  680 */         _jspx_th_c_005fchoose_005f6.setParent(null);
/*  681 */         int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/*  682 */         if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */           for (;;) {
/*  684 */             out.write(10);
/*      */             
/*  686 */             WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  687 */             _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/*  688 */             _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */             
/*  690 */             _jspx_th_c_005fwhen_005f6.setTest("${param.server!='admin'}");
/*  691 */             int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/*  692 */             if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */               for (;;) {
/*  694 */                 out.write(10);
/*      */                 
/*  696 */                 EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f5 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  697 */                 _jspx_th_ea_005feeadminlink_005f5.setPageContext(_jspx_page_context);
/*  698 */                 _jspx_th_ea_005feeadminlink_005f5.setParent(_jspx_th_c_005fwhen_005f6);
/*      */                 
/*  700 */                 _jspx_th_ea_005feeadminlink_005f5.setHref("/adminAction.do?method=showActionProfiles");
/*      */                 
/*  702 */                 _jspx_th_ea_005feeadminlink_005f5.setEnableClass("new-left-links");
/*  703 */                 int _jspx_eval_ea_005feeadminlink_005f5 = _jspx_th_ea_005feeadminlink_005f5.doStartTag();
/*  704 */                 if (_jspx_eval_ea_005feeadminlink_005f5 != 0) {
/*  705 */                   if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/*  706 */                     out = _jspx_page_context.pushBody();
/*  707 */                     _jspx_th_ea_005feeadminlink_005f5.setBodyContent((BodyContent)out);
/*  708 */                     _jspx_th_ea_005feeadminlink_005f5.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  711 */                     out.write(10);
/*  712 */                     out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/*  713 */                     out.write(10);
/*  714 */                     int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f5.doAfterBody();
/*  715 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  718 */                   if (_jspx_eval_ea_005feeadminlink_005f5 != 1) {
/*  719 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  722 */                 if (_jspx_th_ea_005feeadminlink_005f5.doEndTag() == 5) {
/*  723 */                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5); return;
/*      */                 }
/*      */                 
/*  726 */                 this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f5);
/*  727 */                 out.write(10);
/*  728 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/*  729 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  733 */             if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/*  734 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */             }
/*      */             
/*  737 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/*  738 */             out.write(10);
/*      */             
/*  740 */             OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  741 */             _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/*  742 */             _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*  743 */             int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/*  744 */             if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */               for (;;) {
/*  746 */                 out.write(10);
/*  747 */                 out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/*  748 */                 out.write(10);
/*  749 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/*  750 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  754 */             if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/*  755 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */             }
/*      */             
/*  758 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/*  759 */             out.write(10);
/*  760 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/*  761 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  765 */         if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/*  766 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */         }
/*      */         
/*  769 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/*  770 */         out.write("\n</td>\n</tr>\n");
/*      */         
/*  772 */         if ((!usertype.equals("F")) || (Constants.isIt360))
/*      */         {
/*  774 */           out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */           
/*  776 */           ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  777 */           _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/*  778 */           _jspx_th_c_005fchoose_005f7.setParent(null);
/*  779 */           int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/*  780 */           if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */             for (;;) {
/*  782 */               out.write(10);
/*  783 */               out.write(32);
/*  784 */               out.write(32);
/*      */               
/*  786 */               WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  787 */               _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/*  788 */               _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */               
/*  790 */               _jspx_th_c_005fwhen_005f7.setTest("${param.method =='showScheduleReports'}");
/*  791 */               int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/*  792 */               if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                 for (;;) {
/*  794 */                   out.write(10);
/*  795 */                   out.write(32);
/*  796 */                   out.write(32);
/*  797 */                   out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  798 */                   out.write(10);
/*  799 */                   out.write(32);
/*  800 */                   out.write(32);
/*  801 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/*  802 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  806 */               if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/*  807 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */               }
/*      */               
/*  810 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/*  811 */               out.write(10);
/*  812 */               out.write(32);
/*      */               
/*  814 */               OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  815 */               _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/*  816 */               _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*  817 */               int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/*  818 */               if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                 for (;;) {
/*  820 */                   out.write("\n   ");
/*      */                   
/*  822 */                   EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f6 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  823 */                   _jspx_th_ea_005feeadminlink_005f6.setPageContext(_jspx_page_context);
/*  824 */                   _jspx_th_ea_005feeadminlink_005f6.setParent(_jspx_th_c_005fotherwise_005f7);
/*      */                   
/*  826 */                   _jspx_th_ea_005feeadminlink_005f6.setHref("/scheduleReports.do?method=showScheduleReports");
/*      */                   
/*  828 */                   _jspx_th_ea_005feeadminlink_005f6.setEnableClass("new-left-links");
/*  829 */                   int _jspx_eval_ea_005feeadminlink_005f6 = _jspx_th_ea_005feeadminlink_005f6.doStartTag();
/*  830 */                   if (_jspx_eval_ea_005feeadminlink_005f6 != 0) {
/*  831 */                     if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/*  832 */                       out = _jspx_page_context.pushBody();
/*  833 */                       _jspx_th_ea_005feeadminlink_005f6.setBodyContent((BodyContent)out);
/*  834 */                       _jspx_th_ea_005feeadminlink_005f6.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/*  837 */                       out.write("\n   ");
/*  838 */                       out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  839 */                       out.write(10);
/*  840 */                       out.write(32);
/*  841 */                       out.write(32);
/*  842 */                       int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f6.doAfterBody();
/*  843 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  846 */                     if (_jspx_eval_ea_005feeadminlink_005f6 != 1) {
/*  847 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  850 */                   if (_jspx_th_ea_005feeadminlink_005f6.doEndTag() == 5) {
/*  851 */                     this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6); return;
/*      */                   }
/*      */                   
/*  854 */                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f6);
/*  855 */                   out.write(10);
/*  856 */                   out.write(32);
/*  857 */                   out.write(32);
/*  858 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/*  859 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  863 */               if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/*  864 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */               }
/*      */               
/*  867 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/*  868 */               out.write(10);
/*  869 */               out.write(32);
/*  870 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/*  871 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  875 */           if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/*  876 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */           }
/*      */           
/*  879 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/*  880 */           out.write("\n</td>\n</tr>\n");
/*      */         } else {
/*  882 */           out.write("\n<tr>\n    <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/*  883 */           out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/*  884 */           out.write("\n    </td>\n</tr>\n");
/*      */         }
/*  886 */         out.write("\n<tr>\n<td class=\"leftlinkstd\" >\n ");
/*      */         
/*  888 */         ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  889 */         _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/*  890 */         _jspx_th_c_005fchoose_005f8.setParent(null);
/*  891 */         int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/*  892 */         if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */           for (;;) {
/*  894 */             out.write(10);
/*  895 */             out.write(32);
/*  896 */             out.write(32);
/*      */             
/*  898 */             WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  899 */             _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/*  900 */             _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */             
/*  902 */             _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showDataCleanUp'}");
/*  903 */             int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/*  904 */             if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */               for (;;) {
/*  906 */                 out.write(10);
/*  907 */                 out.write(32);
/*  908 */                 out.write(32);
/*  909 */                 out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/*  910 */                 out.write(10);
/*  911 */                 out.write(32);
/*  912 */                 out.write(32);
/*  913 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/*  914 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  918 */             if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/*  919 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */             }
/*      */             
/*  922 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/*  923 */             out.write(10);
/*  924 */             out.write(32);
/*      */             
/*  926 */             OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  927 */             _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/*  928 */             _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*  929 */             int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/*  930 */             if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */               for (;;) {
/*  932 */                 out.write("\n   ");
/*      */                 
/*  934 */                 EnterpriseAdminLink _jspx_th_ea_005feeadminlink_005f7 = (EnterpriseAdminLink)this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.get(EnterpriseAdminLink.class);
/*  935 */                 _jspx_th_ea_005feeadminlink_005f7.setPageContext(_jspx_page_context);
/*  936 */                 _jspx_th_ea_005feeadminlink_005f7.setParent(_jspx_th_c_005fotherwise_005f8);
/*      */                 
/*  938 */                 _jspx_th_ea_005feeadminlink_005f7.setHref("/adminAction.do?method=showDataCleanUp");
/*      */                 
/*  940 */                 _jspx_th_ea_005feeadminlink_005f7.setEnableClass("new-left-links");
/*  941 */                 int _jspx_eval_ea_005feeadminlink_005f7 = _jspx_th_ea_005feeadminlink_005f7.doStartTag();
/*  942 */                 if (_jspx_eval_ea_005feeadminlink_005f7 != 0) {
/*  943 */                   if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/*  944 */                     out = _jspx_page_context.pushBody();
/*  945 */                     _jspx_th_ea_005feeadminlink_005f7.setBodyContent((BodyContent)out);
/*  946 */                     _jspx_th_ea_005feeadminlink_005f7.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/*  949 */                     out.write("\n   ");
/*  950 */                     out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/*  951 */                     out.write(10);
/*  952 */                     out.write(32);
/*  953 */                     out.write(32);
/*  954 */                     int evalDoAfterBody = _jspx_th_ea_005feeadminlink_005f7.doAfterBody();
/*  955 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*  958 */                   if (_jspx_eval_ea_005feeadminlink_005f7 != 1) {
/*  959 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/*  962 */                 if (_jspx_th_ea_005feeadminlink_005f7.doEndTag() == 5) {
/*  963 */                   this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7); return;
/*      */                 }
/*      */                 
/*  966 */                 this._005fjspx_005ftagPool_005fea_005feeadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_ea_005feeadminlink_005f7);
/*  967 */                 out.write(10);
/*  968 */                 out.write(32);
/*  969 */                 out.write(32);
/*  970 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/*  971 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  975 */             if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/*  976 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */             }
/*      */             
/*  979 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/*  980 */             out.write(10);
/*  981 */             out.write(32);
/*  982 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/*  983 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  987 */         if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/*  988 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */         }
/*      */         
/*  991 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/*  992 */         out.write("\n</td>\n</tr>\n</table>\n\n");
/*  993 */         out.write(10);
/*  994 */         out.write(32);
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  999 */         out.write("\n   ");
/* 1000 */         out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */         
/*      */ 
/* 1003 */         String usertype = FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */         
/* 1005 */         out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/* 1006 */         out.print(FormatUtil.getString("wizard.disabled"));
/* 1007 */         out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/* 1008 */         out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 1009 */         out.write("</td>\n  </tr>\n  \n ");
/*      */         
/* 1011 */         if (!Constants.sqlManager)
/*      */         {
/*      */ 
/* 1014 */           out.write("  \n  <tr>\n\n  ");
/*      */           
/* 1016 */           if (request.getParameter("wiz") != null)
/*      */           {
/* 1018 */             out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/* 1019 */             out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 1020 */             out.write("\" class='disabledlink'>");
/* 1021 */             out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1022 */             out.write("</a></td>\n  ");
/*      */           }
/*      */           else
/*      */           {
/* 1026 */             out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */             
/* 1028 */             ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1029 */             _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 1030 */             _jspx_th_c_005fchoose_005f9.setParent(null);
/* 1031 */             int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 1032 */             if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */               for (;;) {
/* 1034 */                 out.write(10);
/*      */                 
/* 1036 */                 WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1037 */                 _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 1038 */                 _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                 
/* 1040 */                 _jspx_th_c_005fwhen_005f9.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/* 1041 */                 int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 1042 */                 if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                   for (;;) {
/* 1044 */                     out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/* 1045 */                     out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1046 */                     out.write("\n    </a>\n ");
/* 1047 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 1048 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1052 */                 if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 1053 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                 }
/*      */                 
/* 1056 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 1057 */                 out.write(10);
/* 1058 */                 out.write(32);
/*      */                 
/* 1060 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1061 */                 _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 1062 */                 _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 1063 */                 int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 1064 */                 if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                   for (;;) {
/* 1066 */                     out.write(10);
/* 1067 */                     out.write(9);
/* 1068 */                     out.write(32);
/* 1069 */                     out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 1070 */                     out.write(10);
/* 1071 */                     out.write(32);
/* 1072 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 1073 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1077 */                 if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 1078 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                 }
/*      */                 
/* 1081 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 1082 */                 out.write(10);
/* 1083 */                 out.write(32);
/* 1084 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 1085 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1089 */             if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 1090 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */             }
/*      */             
/* 1093 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 1094 */             out.write("\n    </td>\n\t");
/*      */           }
/* 1096 */           out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */           
/* 1098 */           if (request.getParameter("wiz") != null)
/*      */           {
/* 1100 */             out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/* 1101 */             out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 1102 */             out.write("\" class='disabledlink'>");
/* 1103 */             out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1104 */             out.write("</a></td>\n   ");
/*      */           }
/*      */           else
/*      */           {
/* 1108 */             out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */             
/* 1110 */             ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1111 */             _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 1112 */             _jspx_th_c_005fchoose_005f10.setParent(null);
/* 1113 */             int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 1114 */             if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */               for (;;) {
/* 1116 */                 out.write(10);
/*      */                 
/* 1118 */                 WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1119 */                 _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 1120 */                 _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                 
/* 1122 */                 _jspx_th_c_005fwhen_005f10.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/* 1123 */                 int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 1124 */                 if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                   for (;;) {
/* 1126 */                     out.write("\n   ");
/* 1127 */                     out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1128 */                     out.write(10);
/* 1129 */                     out.write(32);
/* 1130 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 1131 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1135 */                 if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 1136 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                 }
/*      */                 
/* 1139 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 1140 */                 out.write(10);
/* 1141 */                 out.write(32);
/*      */                 
/* 1143 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1144 */                 _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 1145 */                 _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 1146 */                 int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 1147 */                 if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                   for (;;) {
/* 1149 */                     out.write(10);
/* 1150 */                     String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/* 1151 */                     out.write("\n\t \n <a href=\"");
/* 1152 */                     out.print(link);
/* 1153 */                     out.write("\" class=\"new-left-links\">\n               ");
/* 1154 */                     out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 1155 */                     out.write("\n    </a>    \n ");
/* 1156 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 1157 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1161 */                 if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 1162 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                 }
/*      */                 
/* 1165 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 1166 */                 out.write(10);
/* 1167 */                 out.write(32);
/* 1168 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 1169 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1173 */             if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 1174 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */             }
/*      */             
/* 1177 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 1178 */             out.write("\n</td>\n");
/*      */           }
/* 1180 */           out.write("\n</tr>\n\n ");
/*      */         }
/*      */         
/*      */ 
/* 1184 */         out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */         
/* 1186 */         ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1187 */         _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 1188 */         _jspx_th_c_005fchoose_005f11.setParent(null);
/* 1189 */         int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 1190 */         if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */           for (;;) {
/* 1192 */             out.write(10);
/*      */             
/* 1194 */             WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1195 */             _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 1196 */             _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */             
/* 1198 */             _jspx_th_c_005fwhen_005f11.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/* 1199 */             int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 1200 */             if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */               for (;;) {
/* 1202 */                 out.write("\n    \n       ");
/* 1203 */                 out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 1204 */                 out.write(10);
/* 1205 */                 out.write(32);
/* 1206 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 1207 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1211 */             if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 1212 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */             }
/*      */             
/* 1215 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 1216 */             out.write(10);
/* 1217 */             out.write(32);
/*      */             
/* 1219 */             OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1220 */             _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 1221 */             _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 1222 */             int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 1223 */             if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */               for (;;) {
/* 1225 */                 out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/* 1226 */                 out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 1227 */                 out.write("\n    </a>\n ");
/* 1228 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 1229 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1233 */             if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 1234 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */             }
/*      */             
/* 1237 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 1238 */             out.write(10);
/* 1239 */             out.write(32);
/* 1240 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 1241 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1245 */         if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 1246 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */         }
/*      */         
/* 1249 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 1250 */         out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */         
/* 1252 */         ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1253 */         _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 1254 */         _jspx_th_c_005fchoose_005f12.setParent(null);
/* 1255 */         int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 1256 */         if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */           for (;;) {
/* 1258 */             out.write(10);
/*      */             
/* 1260 */             WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1261 */             _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 1262 */             _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */             
/* 1264 */             _jspx_th_c_005fwhen_005f12.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/* 1265 */             int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 1266 */             if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */               for (;;) {
/* 1268 */                 out.write("\n    \n       ");
/* 1269 */                 out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 1270 */                 out.write(10);
/* 1271 */                 out.write(32);
/* 1272 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 1273 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1277 */             if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 1278 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */             }
/*      */             
/* 1281 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 1282 */             out.write(10);
/* 1283 */             out.write(32);
/*      */             
/* 1285 */             OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1286 */             _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 1287 */             _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 1288 */             int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 1289 */             if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */               for (;;) {
/* 1291 */                 out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/* 1292 */                 out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 1293 */                 out.write("\n\t </a>\n ");
/* 1294 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 1295 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1299 */             if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 1300 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */             }
/*      */             
/* 1303 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 1304 */             out.write(10);
/* 1305 */             out.write(32);
/* 1306 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 1307 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1311 */         if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 1312 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */         }
/*      */         
/* 1315 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 1316 */         out.write("\n    </td>\n</tr>  \n\n  ");
/*      */         
/* 1318 */         if (!Constants.sqlManager)
/*      */         {
/*      */ 
/* 1321 */           out.write(32);
/* 1322 */           out.write(32);
/* 1323 */           out.write(10);
/*      */           
/* 1325 */           ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1326 */           _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 1327 */           _jspx_th_c_005fchoose_005f13.setParent(null);
/* 1328 */           int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 1329 */           if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */             for (;;) {
/* 1331 */               out.write(10);
/*      */               
/* 1333 */               WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1334 */               _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 1335 */               _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */               
/* 1337 */               _jspx_th_c_005fwhen_005f13.setTest("${param.method !='showNetworkDiscoveryForm'}");
/* 1338 */               int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 1339 */               if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                 for (;;) {
/* 1341 */                   out.write("\n<tr>\n    ");
/* 1342 */                   if (!request.isUserInRole("OPERATOR")) {
/* 1343 */                     out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/* 1344 */                     out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 1345 */                     out.write("\n    </a>\n        </td>\n     ");
/*      */                   } else {
/* 1347 */                     out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/* 1348 */                     out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 1349 */                     out.write("\n\t</a>\n\t </td>\n\t");
/*      */                   }
/* 1351 */                   out.write("\n    </tr>\n ");
/* 1352 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 1353 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1357 */               if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 1358 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */               }
/*      */               
/* 1361 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 1362 */               out.write(10);
/* 1363 */               out.write(32);
/*      */               
/* 1365 */               OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1366 */               _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 1367 */               _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 1368 */               int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 1369 */               if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                 for (;;) {
/* 1371 */                   out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/* 1372 */                   out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 1373 */                   out.write("\n\t </td>\n ");
/* 1374 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 1375 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1379 */               if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 1380 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */               }
/*      */               
/* 1383 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 1384 */               out.write(10);
/* 1385 */               out.write(32);
/* 1386 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 1387 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1391 */           if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 1392 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */           }
/*      */           
/* 1395 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 1396 */           out.write("\n \n  ");
/*      */         }
/*      */         
/*      */ 
/* 1400 */         out.write("  \n \n ");
/*      */         
/* 1402 */         if (!usertype.equals("F"))
/*      */         {
/* 1404 */           out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */           
/* 1406 */           ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1407 */           _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 1408 */           _jspx_th_c_005fchoose_005f14.setParent(null);
/* 1409 */           int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 1410 */           if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */             for (;;) {
/* 1412 */               out.write(10);
/* 1413 */               out.write(9);
/*      */               
/* 1415 */               WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1416 */               _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 1417 */               _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */               
/* 1419 */               _jspx_th_c_005fwhen_005f14.setTest("${param.method !='maintenanceTaskListView'}");
/* 1420 */               int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 1421 */               if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                 for (;;) {
/* 1423 */                   out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 1424 */                   out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1425 */                   out.write("</a>\n  \t");
/* 1426 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 1427 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1431 */               if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 1432 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */               }
/*      */               
/* 1435 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 1436 */               out.write("\n  \t");
/*      */               
/* 1438 */               OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1439 */               _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 1440 */               _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 1441 */               int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 1442 */               if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                 for (;;) {
/* 1444 */                   out.write("\n \t\t");
/* 1445 */                   out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1446 */                   out.write("\n  \t");
/* 1447 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 1448 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1452 */               if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 1453 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */               }
/*      */               
/* 1456 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 1457 */               out.write("\n  \t");
/* 1458 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 1459 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1463 */           if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 1464 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */           }
/*      */           
/* 1467 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 1468 */           out.write("\n     </td>\n </tr>   \n \n ");
/*      */           
/* 1470 */           if (!Constants.sqlManager)
/*      */           {
/*      */ 
/* 1473 */             out.write(32);
/* 1474 */             out.write(32);
/* 1475 */             out.write(10);
/*      */             
/* 1477 */             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1478 */             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1479 */             _jspx_th_c_005fif_005f0.setParent(null);
/*      */             
/* 1481 */             _jspx_th_c_005fif_005f0.setTest("${category!='LAMP'}");
/* 1482 */             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1483 */             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */               for (;;) {
/* 1485 */                 out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                 
/* 1487 */                 ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1488 */                 _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 1489 */                 _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_c_005fif_005f0);
/* 1490 */                 int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 1491 */                 if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                   for (;;) {
/* 1493 */                     out.write(10);
/* 1494 */                     out.write(32);
/* 1495 */                     out.write(9);
/*      */                     
/* 1497 */                     WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1498 */                     _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 1499 */                     _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                     
/* 1501 */                     _jspx_th_c_005fwhen_005f15.setTest("${param.method !='listTrapListener'}");
/* 1502 */                     int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 1503 */                     if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                       for (;;) {
/* 1505 */                         out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/* 1506 */                         out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 1507 */                         out.write("</a>\n   \t");
/* 1508 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 1509 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1513 */                     if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 1514 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                     }
/*      */                     
/* 1517 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 1518 */                     out.write("\n   \t");
/*      */                     
/* 1520 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1521 */                     _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 1522 */                     _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 1523 */                     int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 1524 */                     if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                       for (;;) {
/* 1526 */                         out.write("\n  \t\t");
/* 1527 */                         out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 1528 */                         out.write(" \n   \t");
/* 1529 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 1530 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1534 */                     if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 1535 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                     }
/*      */                     
/* 1538 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 1539 */                     out.write("\n   \t");
/* 1540 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 1541 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1545 */                 if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 1546 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                 }
/*      */                 
/* 1549 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 1550 */                 out.write("\n      </td>\n  </tr>   \n");
/* 1551 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1552 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1556 */             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1557 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */             }
/*      */             
/* 1560 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1561 */             out.write(10);
/* 1562 */             out.write(32);
/*      */           }
/*      */           
/*      */ 
/* 1566 */           out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */           
/* 1568 */           ChooseTag _jspx_th_c_005fchoose_005f16 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1569 */           _jspx_th_c_005fchoose_005f16.setPageContext(_jspx_page_context);
/* 1570 */           _jspx_th_c_005fchoose_005f16.setParent(null);
/* 1571 */           int _jspx_eval_c_005fchoose_005f16 = _jspx_th_c_005fchoose_005f16.doStartTag();
/* 1572 */           if (_jspx_eval_c_005fchoose_005f16 != 0) {
/*      */             for (;;) {
/* 1574 */               out.write(10);
/*      */               
/* 1576 */               WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1577 */               _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 1578 */               _jspx_th_c_005fwhen_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/*      */               
/* 1580 */               _jspx_th_c_005fwhen_005f16.setTest("${param.method =='showScheduleReports'}");
/* 1581 */               int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 1582 */               if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */                 for (;;) {
/* 1584 */                   out.write("\n       ");
/* 1585 */                   out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1586 */                   out.write(10);
/* 1587 */                   out.write(32);
/* 1588 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 1589 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1593 */               if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 1594 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16); return;
/*      */               }
/*      */               
/* 1597 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 1598 */               out.write(10);
/* 1599 */               out.write(32);
/*      */               
/* 1601 */               OtherwiseTag _jspx_th_c_005fotherwise_005f16 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1602 */               _jspx_th_c_005fotherwise_005f16.setPageContext(_jspx_page_context);
/* 1603 */               _jspx_th_c_005fotherwise_005f16.setParent(_jspx_th_c_005fchoose_005f16);
/* 1604 */               int _jspx_eval_c_005fotherwise_005f16 = _jspx_th_c_005fotherwise_005f16.doStartTag();
/* 1605 */               if (_jspx_eval_c_005fotherwise_005f16 != 0) {
/*      */                 for (;;) {
/* 1607 */                   out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/* 1608 */                   out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1609 */                   out.write("\n\t </a>\n ");
/* 1610 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f16.doAfterBody();
/* 1611 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1615 */               if (_jspx_th_c_005fotherwise_005f16.doEndTag() == 5) {
/* 1616 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16); return;
/*      */               }
/*      */               
/* 1619 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f16);
/* 1620 */               out.write(10);
/* 1621 */               out.write(32);
/* 1622 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f16.doAfterBody();
/* 1623 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1627 */           if (_jspx_th_c_005fchoose_005f16.doEndTag() == 5) {
/* 1628 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16); return;
/*      */           }
/*      */           
/* 1631 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f16);
/* 1632 */           out.write("\n    </td>\n</tr> \n");
/*      */         } else {
/* 1634 */           out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 1635 */           out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 1636 */           out.write("</a>\n     </td>\n </tr>   \n");
/*      */           
/* 1638 */           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1639 */           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1640 */           _jspx_th_c_005fif_005f1.setParent(null);
/*      */           
/* 1642 */           _jspx_th_c_005fif_005f1.setTest("${category!='LAMP'}");
/* 1643 */           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1644 */           if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */             for (;;) {
/* 1646 */               out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 1647 */               out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 1648 */               out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 1649 */               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1650 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1654 */           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1655 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */           }
/*      */           
/* 1658 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1659 */           out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 1660 */           out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 1661 */           out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */         }
/* 1663 */         out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */         
/* 1665 */         ChooseTag _jspx_th_c_005fchoose_005f17 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1666 */         _jspx_th_c_005fchoose_005f17.setPageContext(_jspx_page_context);
/* 1667 */         _jspx_th_c_005fchoose_005f17.setParent(null);
/* 1668 */         int _jspx_eval_c_005fchoose_005f17 = _jspx_th_c_005fchoose_005f17.doStartTag();
/* 1669 */         if (_jspx_eval_c_005fchoose_005f17 != 0) {
/*      */           for (;;) {
/* 1671 */             out.write(10);
/*      */             
/* 1673 */             WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1674 */             _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 1675 */             _jspx_th_c_005fwhen_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/*      */             
/* 1677 */             _jspx_th_c_005fwhen_005f17.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 1678 */             int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 1679 */             if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */               for (;;) {
/* 1681 */                 out.write("\n        ");
/* 1682 */                 out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 1683 */                 out.write(10);
/* 1684 */                 out.write(32);
/* 1685 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 1686 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1690 */             if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 1691 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17); return;
/*      */             }
/*      */             
/* 1694 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 1695 */             out.write(10);
/* 1696 */             out.write(32);
/*      */             
/* 1698 */             OtherwiseTag _jspx_th_c_005fotherwise_005f17 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1699 */             _jspx_th_c_005fotherwise_005f17.setPageContext(_jspx_page_context);
/* 1700 */             _jspx_th_c_005fotherwise_005f17.setParent(_jspx_th_c_005fchoose_005f17);
/* 1701 */             int _jspx_eval_c_005fotherwise_005f17 = _jspx_th_c_005fotherwise_005f17.doStartTag();
/* 1702 */             if (_jspx_eval_c_005fotherwise_005f17 != 0) {
/*      */               for (;;) {
/* 1704 */                 out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 1705 */                 out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 1706 */                 out.write("\n\t </a>\n ");
/* 1707 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f17.doAfterBody();
/* 1708 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1712 */             if (_jspx_th_c_005fotherwise_005f17.doEndTag() == 5) {
/* 1713 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17); return;
/*      */             }
/*      */             
/* 1716 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f17);
/* 1717 */             out.write(10);
/* 1718 */             out.write(32);
/* 1719 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f17.doAfterBody();
/* 1720 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1724 */         if (_jspx_th_c_005fchoose_005f17.doEndTag() == 5) {
/* 1725 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17); return;
/*      */         }
/*      */         
/* 1728 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f17);
/* 1729 */         out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */         
/* 1731 */         ChooseTag _jspx_th_c_005fchoose_005f18 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1732 */         _jspx_th_c_005fchoose_005f18.setPageContext(_jspx_page_context);
/* 1733 */         _jspx_th_c_005fchoose_005f18.setParent(null);
/* 1734 */         int _jspx_eval_c_005fchoose_005f18 = _jspx_th_c_005fchoose_005f18.doStartTag();
/* 1735 */         if (_jspx_eval_c_005fchoose_005f18 != 0) {
/*      */           for (;;) {
/* 1737 */             out.write(10);
/*      */             
/* 1739 */             WhenTag _jspx_th_c_005fwhen_005f18 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1740 */             _jspx_th_c_005fwhen_005f18.setPageContext(_jspx_page_context);
/* 1741 */             _jspx_th_c_005fwhen_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/*      */             
/* 1743 */             _jspx_th_c_005fwhen_005f18.setTest("${param.method!='showMailServerConfiguration'}");
/* 1744 */             int _jspx_eval_c_005fwhen_005f18 = _jspx_th_c_005fwhen_005f18.doStartTag();
/* 1745 */             if (_jspx_eval_c_005fwhen_005f18 != 0) {
/*      */               for (;;) {
/* 1747 */                 out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 1748 */                 out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1749 */                 out.write("\n    </a>    \n ");
/* 1750 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f18.doAfterBody();
/* 1751 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1755 */             if (_jspx_th_c_005fwhen_005f18.doEndTag() == 5) {
/* 1756 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18); return;
/*      */             }
/*      */             
/* 1759 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 1760 */             out.write(10);
/* 1761 */             out.write(32);
/*      */             
/* 1763 */             OtherwiseTag _jspx_th_c_005fotherwise_005f18 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1764 */             _jspx_th_c_005fotherwise_005f18.setPageContext(_jspx_page_context);
/* 1765 */             _jspx_th_c_005fotherwise_005f18.setParent(_jspx_th_c_005fchoose_005f18);
/* 1766 */             int _jspx_eval_c_005fotherwise_005f18 = _jspx_th_c_005fotherwise_005f18.doStartTag();
/* 1767 */             if (_jspx_eval_c_005fotherwise_005f18 != 0) {
/*      */               for (;;) {
/* 1769 */                 out.write(10);
/* 1770 */                 out.write(9);
/* 1771 */                 out.write(32);
/* 1772 */                 out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 1773 */                 out.write(10);
/* 1774 */                 out.write(32);
/* 1775 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f18.doAfterBody();
/* 1776 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1780 */             if (_jspx_th_c_005fotherwise_005f18.doEndTag() == 5) {
/* 1781 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18); return;
/*      */             }
/*      */             
/* 1784 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f18);
/* 1785 */             out.write(10);
/* 1786 */             out.write(32);
/* 1787 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f18.doAfterBody();
/* 1788 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 1792 */         if (_jspx_th_c_005fchoose_005f18.doEndTag() == 5) {
/* 1793 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18); return;
/*      */         }
/*      */         
/* 1796 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f18);
/* 1797 */         out.write("\n    </td>\n</tr>\n\n\n");
/* 1798 */         if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 1799 */           out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */           
/* 1801 */           ChooseTag _jspx_th_c_005fchoose_005f19 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1802 */           _jspx_th_c_005fchoose_005f19.setPageContext(_jspx_page_context);
/* 1803 */           _jspx_th_c_005fchoose_005f19.setParent(null);
/* 1804 */           int _jspx_eval_c_005fchoose_005f19 = _jspx_th_c_005fchoose_005f19.doStartTag();
/* 1805 */           if (_jspx_eval_c_005fchoose_005f19 != 0) {
/*      */             for (;;) {
/* 1807 */               out.write(10);
/*      */               
/* 1809 */               WhenTag _jspx_th_c_005fwhen_005f19 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1810 */               _jspx_th_c_005fwhen_005f19.setPageContext(_jspx_page_context);
/* 1811 */               _jspx_th_c_005fwhen_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/*      */               
/* 1813 */               _jspx_th_c_005fwhen_005f19.setTest("${param.method!='SMSServerConfiguration'}");
/* 1814 */               int _jspx_eval_c_005fwhen_005f19 = _jspx_th_c_005fwhen_005f19.doStartTag();
/* 1815 */               if (_jspx_eval_c_005fwhen_005f19 != 0) {
/*      */                 for (;;) {
/* 1817 */                   out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 1818 */                   out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 1819 */                   out.write("\n    </a>\n ");
/* 1820 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f19.doAfterBody();
/* 1821 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1825 */               if (_jspx_th_c_005fwhen_005f19.doEndTag() == 5) {
/* 1826 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19); return;
/*      */               }
/*      */               
/* 1829 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f19);
/* 1830 */               out.write(10);
/* 1831 */               out.write(32);
/*      */               
/* 1833 */               OtherwiseTag _jspx_th_c_005fotherwise_005f19 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1834 */               _jspx_th_c_005fotherwise_005f19.setPageContext(_jspx_page_context);
/* 1835 */               _jspx_th_c_005fotherwise_005f19.setParent(_jspx_th_c_005fchoose_005f19);
/* 1836 */               int _jspx_eval_c_005fotherwise_005f19 = _jspx_th_c_005fotherwise_005f19.doStartTag();
/* 1837 */               if (_jspx_eval_c_005fotherwise_005f19 != 0) {
/*      */                 for (;;) {
/* 1839 */                   out.write("\n         ");
/* 1840 */                   out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 1841 */                   out.write(10);
/* 1842 */                   out.write(32);
/* 1843 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f19.doAfterBody();
/* 1844 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1848 */               if (_jspx_th_c_005fotherwise_005f19.doEndTag() == 5) {
/* 1849 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19); return;
/*      */               }
/*      */               
/* 1852 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f19);
/* 1853 */               out.write(10);
/* 1854 */               out.write(32);
/* 1855 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f19.doAfterBody();
/* 1856 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1860 */           if (_jspx_th_c_005fchoose_005f19.doEndTag() == 5) {
/* 1861 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19); return;
/*      */           }
/*      */           
/* 1864 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f19);
/* 1865 */           out.write("\n    </td>\n</tr>\n");
/*      */         }
/* 1867 */         out.write("\n\n\n ");
/*      */         
/* 1869 */         if (!Constants.sqlManager)
/*      */         {
/*      */ 
/* 1872 */           out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */           
/* 1874 */           ChooseTag _jspx_th_c_005fchoose_005f20 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1875 */           _jspx_th_c_005fchoose_005f20.setPageContext(_jspx_page_context);
/* 1876 */           _jspx_th_c_005fchoose_005f20.setParent(null);
/* 1877 */           int _jspx_eval_c_005fchoose_005f20 = _jspx_th_c_005fchoose_005f20.doStartTag();
/* 1878 */           if (_jspx_eval_c_005fchoose_005f20 != 0) {
/*      */             for (;;) {
/* 1880 */               out.write(10);
/*      */               
/* 1882 */               WhenTag _jspx_th_c_005fwhen_005f20 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1883 */               _jspx_th_c_005fwhen_005f20.setPageContext(_jspx_page_context);
/* 1884 */               _jspx_th_c_005fwhen_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/*      */               
/* 1886 */               _jspx_th_c_005fwhen_005f20.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 1887 */               int _jspx_eval_c_005fwhen_005f20 = _jspx_th_c_005fwhen_005f20.doStartTag();
/* 1888 */               if (_jspx_eval_c_005fwhen_005f20 != 0) {
/*      */                 for (;;) {
/* 1890 */                   out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 1891 */                   out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 1892 */                   out.write("\n    </a>\n ");
/* 1893 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f20.doAfterBody();
/* 1894 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1898 */               if (_jspx_th_c_005fwhen_005f20.doEndTag() == 5) {
/* 1899 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20); return;
/*      */               }
/*      */               
/* 1902 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f20);
/* 1903 */               out.write(10);
/* 1904 */               out.write(32);
/*      */               
/* 1906 */               OtherwiseTag _jspx_th_c_005fotherwise_005f20 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1907 */               _jspx_th_c_005fotherwise_005f20.setPageContext(_jspx_page_context);
/* 1908 */               _jspx_th_c_005fotherwise_005f20.setParent(_jspx_th_c_005fchoose_005f20);
/* 1909 */               int _jspx_eval_c_005fotherwise_005f20 = _jspx_th_c_005fotherwise_005f20.doStartTag();
/* 1910 */               if (_jspx_eval_c_005fotherwise_005f20 != 0) {
/*      */                 for (;;) {
/* 1912 */                   out.write(10);
/* 1913 */                   out.write(9);
/* 1914 */                   out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 1915 */                   out.write(10);
/* 1916 */                   out.write(32);
/* 1917 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f20.doAfterBody();
/* 1918 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1922 */               if (_jspx_th_c_005fotherwise_005f20.doEndTag() == 5) {
/* 1923 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20); return;
/*      */               }
/*      */               
/* 1926 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f20);
/* 1927 */               out.write(10);
/* 1928 */               out.write(32);
/* 1929 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f20.doAfterBody();
/* 1930 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1934 */           if (_jspx_th_c_005fchoose_005f20.doEndTag() == 5) {
/* 1935 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20); return;
/*      */           }
/*      */           
/* 1938 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f20);
/* 1939 */           out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */           
/* 1941 */           ChooseTag _jspx_th_c_005fchoose_005f21 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1942 */           _jspx_th_c_005fchoose_005f21.setPageContext(_jspx_page_context);
/* 1943 */           _jspx_th_c_005fchoose_005f21.setParent(null);
/* 1944 */           int _jspx_eval_c_005fchoose_005f21 = _jspx_th_c_005fchoose_005f21.doStartTag();
/* 1945 */           if (_jspx_eval_c_005fchoose_005f21 != 0) {
/*      */             for (;;) {
/* 1947 */               out.write(10);
/*      */               
/* 1949 */               WhenTag _jspx_th_c_005fwhen_005f21 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1950 */               _jspx_th_c_005fwhen_005f21.setPageContext(_jspx_page_context);
/* 1951 */               _jspx_th_c_005fwhen_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/*      */               
/* 1953 */               _jspx_th_c_005fwhen_005f21.setTest("${uri !='/Upload.do'}");
/* 1954 */               int _jspx_eval_c_005fwhen_005f21 = _jspx_th_c_005fwhen_005f21.doStartTag();
/* 1955 */               if (_jspx_eval_c_005fwhen_005f21 != 0) {
/*      */                 for (;;) {
/* 1957 */                   out.write("   \n        ");
/*      */                   
/* 1959 */                   AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1960 */                   _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 1961 */                   _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f21);
/*      */                   
/* 1963 */                   _jspx_th_am_005fadminlink_005f0.setHref("/Upload.do");
/*      */                   
/* 1965 */                   _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 1966 */                   int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 1967 */                   if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 1968 */                     if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 1969 */                       out = _jspx_page_context.pushBody();
/* 1970 */                       _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 1971 */                       _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 1974 */                       out.write("\n           ");
/* 1975 */                       out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 1976 */                       out.write("\n            ");
/* 1977 */                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 1978 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1981 */                     if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 1982 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1985 */                   if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 1986 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                   }
/*      */                   
/* 1989 */                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 1990 */                   out.write(10);
/* 1991 */                   out.write(10);
/* 1992 */                   out.write(32);
/* 1993 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f21.doAfterBody();
/* 1994 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1998 */               if (_jspx_th_c_005fwhen_005f21.doEndTag() == 5) {
/* 1999 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21); return;
/*      */               }
/*      */               
/* 2002 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f21);
/* 2003 */               out.write(10);
/* 2004 */               out.write(32);
/*      */               
/* 2006 */               OtherwiseTag _jspx_th_c_005fotherwise_005f21 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2007 */               _jspx_th_c_005fotherwise_005f21.setPageContext(_jspx_page_context);
/* 2008 */               _jspx_th_c_005fotherwise_005f21.setParent(_jspx_th_c_005fchoose_005f21);
/* 2009 */               int _jspx_eval_c_005fotherwise_005f21 = _jspx_th_c_005fotherwise_005f21.doStartTag();
/* 2010 */               if (_jspx_eval_c_005fotherwise_005f21 != 0) {
/*      */                 for (;;) {
/* 2012 */                   out.write(10);
/* 2013 */                   out.write(9);
/* 2014 */                   out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 2015 */                   out.write(10);
/* 2016 */                   out.write(32);
/* 2017 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f21.doAfterBody();
/* 2018 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2022 */               if (_jspx_th_c_005fotherwise_005f21.doEndTag() == 5) {
/* 2023 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21); return;
/*      */               }
/*      */               
/* 2026 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f21);
/* 2027 */               out.write(10);
/* 2028 */               out.write(32);
/* 2029 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f21.doAfterBody();
/* 2030 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2034 */           if (_jspx_th_c_005fchoose_005f21.doEndTag() == 5) {
/* 2035 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21); return;
/*      */           }
/*      */           
/* 2038 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f21);
/* 2039 */           out.write("\n    </td>\n</tr>\n \n ");
/*      */         }
/*      */         
/*      */ 
/* 2043 */         out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */         
/* 2045 */         ChooseTag _jspx_th_c_005fchoose_005f22 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2046 */         _jspx_th_c_005fchoose_005f22.setPageContext(_jspx_page_context);
/* 2047 */         _jspx_th_c_005fchoose_005f22.setParent(null);
/* 2048 */         int _jspx_eval_c_005fchoose_005f22 = _jspx_th_c_005fchoose_005f22.doStartTag();
/* 2049 */         if (_jspx_eval_c_005fchoose_005f22 != 0) {
/*      */           for (;;) {
/* 2051 */             out.write(10);
/*      */             
/* 2053 */             WhenTag _jspx_th_c_005fwhen_005f22 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2054 */             _jspx_th_c_005fwhen_005f22.setPageContext(_jspx_page_context);
/* 2055 */             _jspx_th_c_005fwhen_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/*      */             
/* 2057 */             _jspx_th_c_005fwhen_005f22.setTest("${uri !='/admin/userconfiguration.do'}");
/* 2058 */             int _jspx_eval_c_005fwhen_005f22 = _jspx_th_c_005fwhen_005f22.doStartTag();
/* 2059 */             if (_jspx_eval_c_005fwhen_005f22 != 0) {
/*      */               for (;;) {
/* 2061 */                 out.write("\n    \n        ");
/*      */                 
/* 2063 */                 AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2064 */                 _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 2065 */                 _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f22);
/*      */                 
/* 2067 */                 _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                 
/* 2069 */                 _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 2070 */                 int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 2071 */                 if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 2072 */                   if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2073 */                     out = _jspx_page_context.pushBody();
/* 2074 */                     _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 2075 */                     _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 2078 */                     out.write("\n       ");
/* 2079 */                     out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2080 */                     out.write("\n        ");
/* 2081 */                     int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 2082 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 2085 */                   if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 2086 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 2089 */                 if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 2090 */                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                 }
/*      */                 
/* 2093 */                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 2094 */                 out.write(10);
/* 2095 */                 out.write(10);
/* 2096 */                 out.write(32);
/* 2097 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f22.doAfterBody();
/* 2098 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2102 */             if (_jspx_th_c_005fwhen_005f22.doEndTag() == 5) {
/* 2103 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22); return;
/*      */             }
/*      */             
/* 2106 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f22);
/* 2107 */             out.write(10);
/* 2108 */             out.write(32);
/*      */             
/* 2110 */             OtherwiseTag _jspx_th_c_005fotherwise_005f22 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2111 */             _jspx_th_c_005fotherwise_005f22.setPageContext(_jspx_page_context);
/* 2112 */             _jspx_th_c_005fotherwise_005f22.setParent(_jspx_th_c_005fchoose_005f22);
/* 2113 */             int _jspx_eval_c_005fotherwise_005f22 = _jspx_th_c_005fotherwise_005f22.doStartTag();
/* 2114 */             if (_jspx_eval_c_005fotherwise_005f22 != 0) {
/*      */               for (;;) {
/* 2116 */                 out.write(10);
/* 2117 */                 out.write(9);
/* 2118 */                 out.write(32);
/* 2119 */                 out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 2120 */                 out.write(10);
/* 2121 */                 out.write(32);
/* 2122 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f22.doAfterBody();
/* 2123 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2127 */             if (_jspx_th_c_005fotherwise_005f22.doEndTag() == 5) {
/* 2128 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22); return;
/*      */             }
/*      */             
/* 2131 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f22);
/* 2132 */             out.write(10);
/* 2133 */             out.write(32);
/* 2134 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f22.doAfterBody();
/* 2135 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2139 */         if (_jspx_th_c_005fchoose_005f22.doEndTag() == 5) {
/* 2140 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22); return;
/*      */         }
/*      */         
/* 2143 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f22);
/* 2144 */         out.write("\n    </td>\n</tr>\n   \n\n ");
/* 2145 */         if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 2146 */           out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */           
/* 2148 */           ChooseTag _jspx_th_c_005fchoose_005f23 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2149 */           _jspx_th_c_005fchoose_005f23.setPageContext(_jspx_page_context);
/* 2150 */           _jspx_th_c_005fchoose_005f23.setParent(null);
/* 2151 */           int _jspx_eval_c_005fchoose_005f23 = _jspx_th_c_005fchoose_005f23.doStartTag();
/* 2152 */           if (_jspx_eval_c_005fchoose_005f23 != 0) {
/*      */             for (;;) {
/* 2154 */               out.write("\n   ");
/*      */               
/* 2156 */               WhenTag _jspx_th_c_005fwhen_005f23 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2157 */               _jspx_th_c_005fwhen_005f23.setPageContext(_jspx_page_context);
/* 2158 */               _jspx_th_c_005fwhen_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/*      */               
/* 2160 */               _jspx_th_c_005fwhen_005f23.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 2161 */               int _jspx_eval_c_005fwhen_005f23 = _jspx_th_c_005fwhen_005f23.doStartTag();
/* 2162 */               if (_jspx_eval_c_005fwhen_005f23 != 0) {
/*      */                 for (;;) {
/* 2164 */                   out.write("\n    ");
/*      */                   
/* 2166 */                   AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 2167 */                   _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 2168 */                   _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f23);
/*      */                   
/* 2170 */                   _jspx_th_am_005fadminlink_005f2.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                   
/* 2172 */                   _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 2173 */                   int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 2174 */                   if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 2175 */                     if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 2176 */                       out = _jspx_page_context.pushBody();
/* 2177 */                       _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 2178 */                       _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 2181 */                       out.write(10);
/* 2182 */                       out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 2183 */                       out.write("\n    ");
/* 2184 */                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 2185 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 2188 */                     if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 2189 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 2192 */                   if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 2193 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                   }
/*      */                   
/* 2196 */                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 2197 */                   out.write("\n   ");
/* 2198 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f23.doAfterBody();
/* 2199 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2203 */               if (_jspx_th_c_005fwhen_005f23.doEndTag() == 5) {
/* 2204 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23); return;
/*      */               }
/*      */               
/* 2207 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f23);
/* 2208 */               out.write("\n   ");
/*      */               
/* 2210 */               OtherwiseTag _jspx_th_c_005fotherwise_005f23 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2211 */               _jspx_th_c_005fotherwise_005f23.setPageContext(_jspx_page_context);
/* 2212 */               _jspx_th_c_005fotherwise_005f23.setParent(_jspx_th_c_005fchoose_005f23);
/* 2213 */               int _jspx_eval_c_005fotherwise_005f23 = _jspx_th_c_005fotherwise_005f23.doStartTag();
/* 2214 */               if (_jspx_eval_c_005fotherwise_005f23 != 0) {
/*      */                 for (;;) {
/* 2216 */                   out.write(10);
/* 2217 */                   out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 2218 */                   out.write("\n   ");
/* 2219 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f23.doAfterBody();
/* 2220 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2224 */               if (_jspx_th_c_005fotherwise_005f23.doEndTag() == 5) {
/* 2225 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23); return;
/*      */               }
/*      */               
/* 2228 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f23);
/* 2229 */               out.write(10);
/* 2230 */               out.write(32);
/* 2231 */               out.write(32);
/* 2232 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f23.doAfterBody();
/* 2233 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2237 */           if (_jspx_th_c_005fchoose_005f23.doEndTag() == 5) {
/* 2238 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23); return;
/*      */           }
/*      */           
/* 2241 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f23);
/* 2242 */           out.write("\n </td>\n</tr>\n  ");
/*      */         }
/* 2244 */         out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */         
/* 2246 */         ChooseTag _jspx_th_c_005fchoose_005f24 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2247 */         _jspx_th_c_005fchoose_005f24.setPageContext(_jspx_page_context);
/* 2248 */         _jspx_th_c_005fchoose_005f24.setParent(null);
/* 2249 */         int _jspx_eval_c_005fchoose_005f24 = _jspx_th_c_005fchoose_005f24.doStartTag();
/* 2250 */         if (_jspx_eval_c_005fchoose_005f24 != 0) {
/*      */           for (;;) {
/* 2252 */             out.write("\n   ");
/*      */             
/* 2254 */             WhenTag _jspx_th_c_005fwhen_005f24 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2255 */             _jspx_th_c_005fwhen_005f24.setPageContext(_jspx_page_context);
/* 2256 */             _jspx_th_c_005fwhen_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/*      */             
/* 2258 */             _jspx_th_c_005fwhen_005f24.setTest("${param.method!='showDataCleanUp'}");
/* 2259 */             int _jspx_eval_c_005fwhen_005f24 = _jspx_th_c_005fwhen_005f24.doStartTag();
/* 2260 */             if (_jspx_eval_c_005fwhen_005f24 != 0) {
/*      */               for (;;) {
/* 2262 */                 out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 2263 */                 out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 2264 */                 out.write("\n    </a>\n   ");
/* 2265 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f24.doAfterBody();
/* 2266 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2270 */             if (_jspx_th_c_005fwhen_005f24.doEndTag() == 5) {
/* 2271 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24); return;
/*      */             }
/*      */             
/* 2274 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f24);
/* 2275 */             out.write("\n   ");
/*      */             
/* 2277 */             OtherwiseTag _jspx_th_c_005fotherwise_005f24 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2278 */             _jspx_th_c_005fotherwise_005f24.setPageContext(_jspx_page_context);
/* 2279 */             _jspx_th_c_005fotherwise_005f24.setParent(_jspx_th_c_005fchoose_005f24);
/* 2280 */             int _jspx_eval_c_005fotherwise_005f24 = _jspx_th_c_005fotherwise_005f24.doStartTag();
/* 2281 */             if (_jspx_eval_c_005fotherwise_005f24 != 0) {
/*      */               for (;;) {
/* 2283 */                 out.write(10);
/* 2284 */                 out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 2285 */                 out.write("\n   ");
/* 2286 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f24.doAfterBody();
/* 2287 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 2291 */             if (_jspx_th_c_005fotherwise_005f24.doEndTag() == 5) {
/* 2292 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24); return;
/*      */             }
/*      */             
/* 2295 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f24);
/* 2296 */             out.write(10);
/* 2297 */             out.write(32);
/* 2298 */             out.write(32);
/* 2299 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f24.doAfterBody();
/* 2300 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2304 */         if (_jspx_th_c_005fchoose_005f24.doEndTag() == 5) {
/* 2305 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24); return;
/*      */         }
/*      */         
/* 2308 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f24);
/* 2309 */         out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 2310 */         out.write(10);
/* 2311 */         out.write(32);
/*      */       }
/*      */       
/*      */ 
/* 2315 */       out.write("\n <br>\n    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n     <tr> \n        <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 2316 */       out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 2317 */       out.write("</td>\n        <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 2318 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/* 2320 */       out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n      </tr>\n      <tr> \n        <td colspan=\"2\" class=\"quicknote\">\n        ");
/*      */       
/* 2322 */       String quickNoteForAdmin = null;
/*      */       
/* 2324 */       out.write("\n            ");
/*      */       
/* 2326 */       IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2327 */       _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2328 */       _jspx_th_c_005fif_005f2.setParent(null);
/*      */       
/* 2330 */       _jspx_th_c_005fif_005f2.setTest("${param.server =='admin'}");
/* 2331 */       int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2332 */       if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */         for (;;) {
/* 2334 */           out.write("\n            ");
/* 2335 */           quickNoteForAdmin = FormatUtil.getString("am.webclient.adminserver.admintab.adminmailsetting.quicknote");
/* 2336 */           out.write("\n            ");
/* 2337 */           int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2338 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2342 */       if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2343 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */       }
/*      */       else {
/* 2346 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2347 */         out.write("\n                   \n            ");
/*      */         
/* 2349 */         IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2350 */         _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2351 */         _jspx_th_c_005fif_005f3.setParent(null);
/*      */         
/* 2353 */         _jspx_th_c_005fif_005f3.setTest("${param.method=='showManagedServers'}");
/* 2354 */         int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2355 */         if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */           for (;;) {
/* 2357 */             out.write("\n            ");
/* 2358 */             quickNoteForAdmin = FormatUtil.getString("am.webclient.adminserver.admintab.managedservers.quicknote");
/* 2359 */             out.write("   \n            ");
/* 2360 */             int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2361 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2365 */         if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2366 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*      */         }
/*      */         else {
/* 2369 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2370 */           out.write("\n          \n          ");
/*      */           
/* 2372 */           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2373 */           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2374 */           _jspx_th_c_005fif_005f4.setParent(null);
/*      */           
/* 2376 */           _jspx_th_c_005fif_005f4.setTest("${uri =='/admin/userconfiguration.do'}");
/* 2377 */           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2378 */           if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */             for (;;) {
/* 2380 */               out.write("\n          ");
/* 2381 */               quickNoteForAdmin = FormatUtil.getString("am.webclient.admin.quicknote1");
/* 2382 */               out.write("   \n          ");
/* 2383 */               int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2384 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2388 */           if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2389 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*      */           }
/*      */           else {
/* 2392 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2393 */             out.write("\n         \n            ");
/*      */             
/* 2395 */             if (quickNoteForAdmin == null)
/*      */             {
/*      */ 
/* 2398 */               out.write("\n\t       ");
/* 2399 */               quickNoteForAdmin = FormatUtil.getString("am.webclient.admin.quicknote");
/* 2400 */               out.write("\n\t    ");
/*      */             }
/*      */             
/*      */ 
/* 2404 */             out.write("\n        ");
/* 2405 */             out.print(quickNoteForAdmin);
/* 2406 */             out.write("\n\t</td>\n      </tr>\n   </table>\n");
/*      */           }
/* 2408 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2409 */         out = _jspx_out;
/* 2410 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2411 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2412 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2415 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2421 */     PageContext pageContext = _jspx_page_context;
/* 2422 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2424 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2425 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2426 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 2428 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2430 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2431 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2432 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2433 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2434 */       return true;
/*      */     }
/* 2436 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2437 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AdminLeftLinks_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */