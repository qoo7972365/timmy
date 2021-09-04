/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.Constants;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.util.ArrayList;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class MapsLeftPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   23 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   29 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*   30 */   static { _jspx_dependants.put("/jsp/includes/DiscoveryLeftLinks.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   46 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   50 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   51 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   52 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   53 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   54 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   55 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   56 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   59 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   63 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   64 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   65 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   66 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*   67 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   68 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*   69 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   70 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   77 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   80 */     JspWriter out = null;
/*   81 */     Object page = this;
/*   82 */     JspWriter _jspx_out = null;
/*   83 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*   87 */       response.setContentType("text/html");
/*   88 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*   90 */       _jspx_page_context = pageContext;
/*   91 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*   92 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*   93 */       session = pageContext.getSession();
/*   94 */       out = pageContext.getOut();
/*   95 */       _jspx_out = out;
/*      */       
/*   97 */       out.write(" <!--$Id$-->\n\n\n\n\n\n\n\n");
/*   98 */       ManagedApplication mo = null;
/*   99 */       synchronized (application) {
/*  100 */         mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/*  101 */         if (mo == null) {
/*  102 */           mo = new ManagedApplication();
/*  103 */           _jspx_page_context.setAttribute("mo", mo, 4);
/*      */         }
/*      */       }
/*  106 */       out.write(10);
/*  107 */       out.write(10);
/*      */       
/*  109 */       String method = request.getParameter("method");
/*  110 */       String network = request.getParameter("selectedNetwork");
/*  111 */       String toAppendLink = "";
/*  112 */       if (network != null)
/*      */       {
/*  114 */         toAppendLink = "&selectedNetwork=" + network;
/*      */       }
/*  116 */       java.util.Hashtable globals = (java.util.Hashtable)application.getAttribute("globalconfig");
/*  117 */       String allowDownTimeSchedule = (String)globals.get("allowDownTimeSchedule");
/*  118 */       pageContext.setAttribute("allowDownTimeSchedule", allowDownTimeSchedule);
/*      */       
/*      */ 
/*  121 */       out.write("\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n");
/*      */       
/*      */ 
/*  124 */       String requestpath = "/images/mm_menu2.jsp";
/*      */       
/*  126 */       ArrayList menupos = new ArrayList(5);
/*  127 */       if (request.isUserInRole("OPERATOR"))
/*      */       {
/*      */ 
/*  130 */         menupos.add("427");
/*  131 */         menupos.add("448");
/*  132 */         menupos.add("469");
/*  133 */         menupos.add("490");
/*  134 */         menupos.add("406");
/*  135 */         menupos.add("511");
/*  136 */         menupos.add("553");
/*  137 */         menupos.add("532");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*  152 */       else if (com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/*  153 */         menupos.add("510");
/*  154 */         menupos.add("531");
/*  155 */         menupos.add("552");
/*  156 */         menupos.add("573");
/*  157 */         menupos.add("490");
/*  158 */         menupos.add("594");
/*  159 */         menupos.add("635");
/*  160 */         menupos.add("615");
/*      */       }
/*  162 */       else if (Constants.getCategorytype().equals("J2EE"))
/*      */       {
/*      */ 
/*  165 */         menupos.add("323");
/*  166 */         menupos.add("342");
/*  167 */         menupos.add("344");
/*  168 */         menupos.add("365");
/*  169 */         menupos.add("302");
/*  170 */         menupos.add("386");
/*  171 */         menupos.add("428");
/*  172 */         menupos.add("407");
/*      */       }
/*  174 */       else if (Constants.getCategorytype().equals("WINDOWS"))
/*      */       {
/*      */ 
/*  177 */         menupos.add("323");
/*  178 */         menupos.add("323");
/*  179 */         menupos.add("344");
/*  180 */         menupos.add("365");
/*  181 */         menupos.add("302");
/*  182 */         menupos.add("386");
/*  183 */         menupos.add("428");
/*  184 */         menupos.add("407");
/*      */       }
/*  186 */       else if (Constants.getCategorytype().equals("LAMP"))
/*      */       {
/*  188 */         menupos.add("323");
/*  189 */         menupos.add(String.valueOf(Integer.parseInt("301") - 21));
/*  190 */         menupos.add(String.valueOf(Integer.parseInt("323") - 21));
/*  191 */         menupos.add(String.valueOf(Integer.parseInt("344") - 21));
/*  192 */         menupos.add(String.valueOf(Integer.parseInt("365") - 21));
/*  193 */         menupos.add(String.valueOf(Integer.parseInt("365") - 21));
/*  194 */         menupos.add(String.valueOf(Integer.parseInt("407") - 21));
/*  195 */         menupos.add(String.valueOf(Integer.parseInt("386") - 21));
/*      */ 
/*      */ 
/*      */       }
/*  199 */       else if (Constants.getCategorytype().equals("DATABASE"))
/*      */       {
/*  201 */         menupos.add(String.valueOf(Integer.parseInt("323") - 21));
/*  202 */         menupos.add(String.valueOf(Integer.parseInt("301") - 21));
/*  203 */         menupos.add(String.valueOf(Integer.parseInt("323") - 21));
/*  204 */         menupos.add(String.valueOf(Integer.parseInt("344") - 21));
/*  205 */         menupos.add(String.valueOf(Integer.parseInt("365") - 21));
/*  206 */         menupos.add(String.valueOf(Integer.parseInt("344") - 21));
/*  207 */         menupos.add(String.valueOf(Integer.parseInt("386") - 21));
/*  208 */         menupos.add(String.valueOf(Integer.parseInt("365") - 21));
/*      */ 
/*      */       }
/*  211 */       else if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */       {
/*      */ 
/*  214 */         menupos.add("510");
/*  215 */         menupos.add("531");
/*  216 */         menupos.add("552");
/*  217 */         menupos.add("573");
/*  218 */         menupos.add("490");
/*  219 */         menupos.add("594");
/*  220 */         menupos.add("635");
/*  221 */         menupos.add("615");
/*      */       }
/*      */       else
/*      */       {
/*  225 */         menupos.add("574");
/*  226 */         menupos.add("595");
/*  227 */         menupos.add("616");
/*  228 */         menupos.add("637");
/*  229 */         menupos.add("553");
/*  230 */         menupos.add("658");
/*  231 */         menupos.add("700");
/*  232 */         menupos.add("679");
/*      */       }
/*      */       
/*  235 */       pageContext.setAttribute("menupos", menupos);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  240 */       out.write(10);
/*  241 */       out.write(10);
/*  242 */       org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, requestpath, out, false);
/*  243 */       out.write(10);
/*  244 */       out.write(10);
/*  245 */       out.write(10);
/*  246 */       out.write("\n</script>\n<SCRIPT language=JavaScript1.2>mmLoadMenus()</SCRIPT>\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td class=\"leftlinksheading\">");
/*  247 */       out.print(FormatUtil.getString("am.webclient.monitors.monitorviewheading.text"));
/*  248 */       out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\">");
/*      */       
/*  250 */       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  251 */       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  252 */       _jspx_th_c_005fchoose_005f0.setParent(null);
/*  253 */       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  254 */       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */         for (;;)
/*      */         {
/*  257 */           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  258 */           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  259 */           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  261 */           _jspx_th_c_005fwhen_005f0.setTest("${param.method =='showResourceTypes'}");
/*  262 */           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  263 */           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */             for (;;) {
/*  265 */               out.print(FormatUtil.getString("am.webclient.monitors.categoryview.text"));
/*  266 */               out.write("\n      ");
/*  267 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  268 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  272 */           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  273 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */           }
/*      */           
/*  276 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  277 */           out.write(32);
/*      */           
/*  279 */           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  280 */           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  281 */           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  282 */           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  283 */           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */             for (;;) {
/*  285 */               out.write(" <a href=\"/showresource.do?method=showResourceTypes&monitor_viewtype=categoryview\" class=\"new-left-links\">");
/*  286 */               out.print(FormatUtil.getString("am.webclient.monitors.categoryview.text"));
/*  287 */               out.write("</a>\n      ");
/*  288 */               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  289 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  293 */           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  294 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */           }
/*      */           
/*  297 */           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  298 */           out.write(32);
/*  299 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  300 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  304 */       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  305 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */       }
/*      */       else {
/*  308 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  309 */         out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\"> ");
/*      */         
/*  311 */         ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  312 */         _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  313 */         _jspx_th_c_005fchoose_005f1.setParent(null);
/*  314 */         int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  315 */         if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */           for (;;) {
/*  317 */             out.write(32);
/*      */             
/*  319 */             WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  320 */             _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  321 */             _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/*  323 */             _jspx_th_c_005fwhen_005f1.setTest("${param.method =='showResourceTypesAll'}");
/*  324 */             int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  325 */             if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */               for (;;) {
/*  327 */                 out.write("\n      ");
/*  328 */                 out.print(FormatUtil.getString("am.webclient.monitors.bulkconfigview.text"));
/*  329 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  330 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  334 */             if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  335 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */             }
/*      */             
/*  338 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  339 */             out.write("\n      ");
/*      */             
/*  341 */             OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  342 */             _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  343 */             _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  344 */             int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  345 */             if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */               for (;;) {
/*  347 */                 out.write(" <a href=\"/showresource.do?method=showResourceTypesAll&group=All\" class=\"new-left-links\">\n     ");
/*  348 */                 out.print(FormatUtil.getString("am.webclient.monitors.bulkconfigview.text"));
/*  349 */                 out.write("</a>\n      ");
/*  350 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  351 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  355 */             if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  356 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */             }
/*      */             
/*  359 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  360 */             out.write(32);
/*  361 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  362 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  366 */         if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  367 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*      */         }
/*      */         else {
/*  370 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  371 */           out.write("</td>\n  </tr>\n  <tr>\n     <td class=\"leftlinkstd\">\n     <a href=\"#\"  onClick=\"window.open('/showresource.do?method=showPlasmaView','PlasmaView','scrollbars=1,resizable=1,width=900,height=650,left=50,top=25,screenX=50,screenY=25');\" class=\"new-left-links\">");
/*  372 */           out.print(FormatUtil.getString("am.monitortab.plasmaview.text"));
/*  373 */           out.write("</a>\n     </td>\n  </tr>\n ");
/*      */           
/*  375 */           NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  376 */           _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/*  377 */           _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */           
/*  379 */           _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/*  380 */           int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/*  381 */           if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */             for (;;) {
/*  383 */               out.write("\n <tr>\n     <td class=\"leftlinkstd\"> ");
/*      */               
/*  385 */               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  386 */               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  387 */               _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*  388 */               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  389 */               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                 for (;;) {
/*  391 */                   out.write(32);
/*      */                   
/*  393 */                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  394 */                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  395 */                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                   
/*  397 */                   _jspx_th_c_005fwhen_005f2.setTest("${param.method =='showMonitorGroupView'}");
/*  398 */                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  399 */                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                     for (;;) {
/*  401 */                       out.write("\n       ");
/*  402 */                       out.print(FormatUtil.getString("am.webclient.monitorgroupview.title"));
/*  403 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  404 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  408 */                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  409 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                   }
/*      */                   
/*  412 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  413 */                   out.write("\n       ");
/*      */                   
/*  415 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  416 */                   _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  417 */                   _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  418 */                   int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  419 */                   if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                     for (;;) {
/*  421 */                       out.write(" <a href=\"/showresource.do?method=showMonitorGroupView\" class=\"new-left-links\">\n       ");
/*  422 */                       out.print(FormatUtil.getString("am.webclient.monitorgroupview.title"));
/*  423 */                       out.write("</a>\n       ");
/*  424 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  425 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  429 */                   if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  430 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                   }
/*      */                   
/*  433 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  434 */                   out.write(32);
/*  435 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  436 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  440 */               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  441 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */               }
/*      */               
/*  444 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  445 */               out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\"> ");
/*      */               
/*  447 */               ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  448 */               _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/*  449 */               _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*  450 */               int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/*  451 */               if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                 for (;;)
/*      */                 {
/*  454 */                   WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  455 */                   _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  456 */                   _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                   
/*  458 */                   _jspx_th_c_005fwhen_005f3.setTest("${param.method =='showGMapView'}");
/*  459 */                   int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  460 */                   if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                     for (;;) {
/*  462 */                       out.write("\n      ");
/*  463 */                       out.print(FormatUtil.getString("am.webclient.monitors.googlemapview.text"));
/*  464 */                       out.write("\n      ");
/*  465 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  466 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  470 */                   if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  471 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                   }
/*      */                   
/*  474 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  475 */                   out.write(32);
/*      */                   
/*  477 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  478 */                   _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  479 */                   _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*  480 */                   int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  481 */                   if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                     for (;;) {
/*  483 */                       out.write(" <a href=\"/showresource.do?method=showGMapView&group=All\" class=\"new-left-links\">\n      ");
/*  484 */                       out.print(FormatUtil.getString("am.webclient.monitors.googlemapview.text"));
/*  485 */                       out.write("</a>\n      ");
/*  486 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/*  487 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  491 */                   if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/*  492 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                   }
/*      */                   
/*  495 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/*  496 */                   out.write(32);
/*  497 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/*  498 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  502 */               if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/*  503 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */               }
/*      */               
/*  506 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*  507 */               out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\"> ");
/*      */               
/*  509 */               ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  510 */               _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  511 */               _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*  512 */               int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  513 */               if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                 for (;;)
/*      */                 {
/*  516 */                   WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  517 */                   _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  518 */                   _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                   
/*  520 */                   _jspx_th_c_005fwhen_005f4.setTest("${param.method =='showIconsView'}");
/*  521 */                   int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  522 */                   if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                     for (;;) {
/*  524 */                       out.write("\n      ");
/*  525 */                       out.print(FormatUtil.getString("am.webclient.monitors.iconview.text"));
/*  526 */                       out.write("\n      ");
/*  527 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  528 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  532 */                   if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  533 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                   }
/*      */                   
/*  536 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  537 */                   out.write(32);
/*      */                   
/*  539 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  540 */                   _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  541 */                   _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*  542 */                   int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  543 */                   if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                     for (;;) {
/*  545 */                       out.write(" <a href=\"/showresource.do?method=showIconsView\" class=\"new-left-links\">\n      ");
/*  546 */                       out.print(FormatUtil.getString("am.webclient.monitors.iconview.text"));
/*  547 */                       out.write("</a>\n      ");
/*  548 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  549 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  553 */                   if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  554 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                   }
/*      */                   
/*  557 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  558 */                   out.write(32);
/*  559 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/*  560 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  564 */               if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/*  565 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */               }
/*      */               
/*  568 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*  569 */               out.write("</td>\n  </tr>\n  <tr>\n    <td class=\"leftlinkstd\"> ");
/*      */               
/*  571 */               ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  572 */               _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  573 */               _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*  574 */               int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  575 */               if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                 for (;;) {
/*  577 */                   out.write(32);
/*      */                   
/*  579 */                   WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  580 */                   _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/*  581 */                   _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                   
/*  583 */                   _jspx_th_c_005fwhen_005f5.setTest("${param.method =='showDetailsView'}");
/*  584 */                   int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/*  585 */                   if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                     for (;;) {
/*  587 */                       out.write("\n      ");
/*  588 */                       out.print(FormatUtil.getString("am.webclient.monitors.tableview.text"));
/*  589 */                       out.write("\n      ");
/*  590 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/*  591 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  595 */                   if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/*  596 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                   }
/*      */                   
/*  599 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*  600 */                   out.write(32);
/*      */                   
/*  602 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  603 */                   _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  604 */                   _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*  605 */                   int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  606 */                   if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                     for (;;) {
/*  608 */                       out.write(" <a href=\"/showresource.do?method=showDetailsView\" class=\"new-left-links\">");
/*  609 */                       out.print(FormatUtil.getString("am.webclient.monitors.tableview.text"));
/*  610 */                       out.write("</a>");
/*  611 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  612 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  616 */                   if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  617 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                   }
/*      */                   
/*  620 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  621 */                   out.write("\n      ");
/*  622 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  623 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  627 */               if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  628 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */               }
/*      */               
/*  631 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  632 */               out.write("</td>\n  </tr>\n\n  ");
/*  633 */               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/*  634 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  638 */           if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/*  639 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */           }
/*      */           else {
/*  642 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*  643 */             out.write(10);
/*  644 */             out.write(32);
/*  645 */             out.write(32);
/*      */             
/*  647 */             if (!request.isUserInRole("OPERATOR"))
/*      */             {
/*      */ 
/*  650 */               out.write("\n  <tr>\n           <td class=\"leftlinkstd\">\n  \t     <a href=\"javascript:void(0);\"  onClick=\"window.open('/GraphicalView.do?method=popUp&haid=0&isPopUp=true','FlashView','scrollbars=1,resizable=1,width='+(screen.availWidth-50)+',height='+(screen.availHeight-50)+',left=5,top=5,screenX=250,screenY=25');\" class=\"new-left-links\">");
/*  651 */               out.print(FormatUtil.getString("am.webclient.flashview.displayname"));
/*  652 */               out.write("</a>\n          </td>\n  </tr>\n  ");
/*      */             }
/*      */             
/*      */ 
/*  656 */             out.write("\n</table>\n<br>\n");
/*  657 */             out.write("<!--$Id$-->\n\n\n\n\n\n\n\n<script>\nfunction fnalert()\n{\n  alertUser();\n  return;\n}\nfunction Call()\n{\n alert(\"");
/*  658 */             out.print(FormatUtil.getString("am.webclient.discoverylinks.alert"));
/*  659 */             out.write("\");\n}\n</script>\n");
/*  660 */             if ((!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (!Constants.isIt360)) {
/*  661 */               out.write(10);
/*      */               
/*  663 */               PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  664 */               _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/*  665 */               _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */               
/*  667 */               _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/*  668 */               int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/*  669 */               if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                 for (;;) {
/*  671 */                   out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"leftmnutables\">\n  <tr> \n    <td height=\"19\" class=\"leftlinksheading\">");
/*  672 */                   out.print(FormatUtil.getString("am.webclient.discoverylinks.heading.text"));
/*  673 */                   out.write("</td>\n  </tr>\n  <tr> \n    <!--td height=\"19\" class=\"leftlinkstd\"><a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999\" class=\"new-left-links\">New Monitor</a></td-->\n   ");
/*      */                   
/*  675 */                   PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  676 */                   _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/*  677 */                   _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                   
/*  679 */                   _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/*  680 */                   int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/*  681 */                   if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                     for (;;) {
/*  683 */                       out.write("\n    ");
/*      */                       
/*  685 */                       if (request.getParameter("wiz") != null)
/*      */                       {
/*  687 */                         out.write("\n\t  <td height=\"19\" class=\"leftlinkstd\"><a href='#' onclick=\"javascript:Call()\" title=\"Disabled in the wizard\" class='disabledlink'>");
/*  688 */                         out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/*  689 */                         out.write("</a></td>\n\t");
/*      */                       }
/*      */                       else
/*      */                       {
/*  693 */                         out.write("\n    <td height=\"19\"  class=\"leftlinkstd\" > <a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=UrlMonitor\" class=\"new-left-links\">");
/*  694 */                         out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/*  695 */                         out.write("</a></td>\n\t");
/*      */                       }
/*  697 */                       out.write("\n    ");
/*  698 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/*  699 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  703 */                   if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/*  704 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                   }
/*      */                   
/*  707 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*  708 */                   out.write("\n    ");
/*      */                   
/*  710 */                   NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  711 */                   _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/*  712 */                   _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                   
/*  714 */                   _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/*  715 */                   int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/*  716 */                   if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                     for (;;) {
/*  718 */                       out.write(32);
/*  719 */                       out.write(10);
/*  720 */                       out.write(9);
/*      */                       
/*  722 */                       if (request.getParameter("wiz") != null)
/*      */                       {
/*  724 */                         out.write("\n     <td height=\"19\" class=\"leftlinkstd\"><a href='#' onclick=\"javascript:Call()\" title=\"Disabled in the wizard\" class='disabledlink'>");
/*  725 */                         out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/*  726 */                         out.write("</a></td>\n\t");
/*      */                       }
/*      */                       else
/*      */                       {
/*  730 */                         out.write("\n    <td height=\"19\"  class=\"leftlinkstd\" > ");
/*      */                         
/*  732 */                         AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  733 */                         _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/*  734 */                         _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                         
/*  736 */                         _jspx_th_am_005fadminlink_005f0.setHref("/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999");
/*      */                         
/*  738 */                         _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/*  739 */                         int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/*  740 */                         if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/*  741 */                           if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/*  742 */                             out = _jspx_page_context.pushBody();
/*  743 */                             _jspx_th_am_005fadminlink_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  744 */                             _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/*  747 */                             out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/*  748 */                             int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/*  749 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*  752 */                           if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/*  753 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/*  756 */                         if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/*  757 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                         }
/*      */                         
/*  760 */                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/*  761 */                         out.write("</td>");
/*  762 */                         out.write(32);
/*  763 */                         out.write(10);
/*  764 */                         out.write(9);
/*      */                       }
/*  766 */                       out.write(10);
/*  767 */                       out.write(9);
/*  768 */                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/*  769 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  773 */                   if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/*  774 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                   }
/*      */                   
/*  777 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*  778 */                   out.write("\n    \n    \n  </tr>\n  ");
/*      */                   
/*  780 */                   com.adventnet.appmanager.server.framework.FreeEditionDetails license = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails();
/*  781 */                   String licensetype = license.getUserType();
/*  782 */                   pageContext.setAttribute("licensetype", licensetype);
/*  783 */                   if (!licensetype.equals("F"))
/*      */                   {
/*      */ 
/*  786 */                     out.write("\n  <tr> \n   ");
/*      */                     
/*  788 */                     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  789 */                     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/*  790 */                     _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                     
/*  792 */                     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/*  793 */                     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/*  794 */                     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                       for (;;) {
/*  796 */                         out.write("\n   <td width=\"95%\" height=\"19\" class=\"leftlinkstd\"><a href=\"javascript:fnalert()\" class=\"new-left-links\"> \n      ");
/*  797 */                         out.print(FormatUtil.getString("am.webclient.discoverylinks.network.text"));
/*  798 */                         out.write("</a></td>\n   ");
/*  799 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/*  800 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  804 */                     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/*  805 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                     }
/*      */                     
/*  808 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/*  809 */                     out.write("\n   ");
/*      */                     
/*  811 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  812 */                     _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/*  813 */                     _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                     
/*  815 */                     _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/*  816 */                     int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/*  817 */                     if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                       for (;;) {
/*  819 */                         out.write("\n    ");
/*      */                         
/*  821 */                         if (!Constants.isMinimizedversion())
/*      */                         {
/*      */ 
/*  824 */                           out.write("\n    <td width=\"95%\" height=\"19\" class=\"leftlinkstd\">");
/*      */                           
/*  826 */                           AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  827 */                           _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/*  828 */                           _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                           
/*  830 */                           _jspx_th_am_005fadminlink_005f1.setHref("/adminAction.do?method=showNetworkDiscoveryForm");
/*      */                           
/*  832 */                           _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/*  833 */                           int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/*  834 */                           if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/*  835 */                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/*  836 */                               out = _jspx_page_context.pushBody();
/*  837 */                               _jspx_th_am_005fadminlink_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  838 */                               _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  841 */                               out.write(" \n      ");
/*  842 */                               out.print(FormatUtil.getString("am.webclient.discoverylinks.network.text"));
/*  843 */                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/*  844 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  847 */                             if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/*  848 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  851 */                           if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/*  852 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                           }
/*      */                           
/*  855 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/*  856 */                           out.write(" </td>\n      ");
/*      */                         }
/*      */                         
/*      */ 
/*  860 */                         out.write("\n\t  ");
/*  861 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/*  862 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  866 */                     if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/*  867 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                     }
/*      */                     
/*  870 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/*  871 */                     out.write("\n  </tr>\n  ");
/*      */                   }
/*      */                   
/*      */ 
/*  875 */                   out.write("\n</table>\n");
/*  876 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/*  877 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  881 */               if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/*  882 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */               }
/*      */               
/*  885 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*  886 */               out.write(10);
/*      */             }
/*  888 */             out.write(10);
/*  889 */             out.write("\n<br>\n");
/*      */             
/*  891 */             NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  892 */             _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/*  893 */             _jspx_th_logic_005fnotPresent_005f3.setParent(null);
/*      */             
/*  895 */             _jspx_th_logic_005fnotPresent_005f3.setRole("OPERATOR");
/*  896 */             int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/*  897 */             if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */               for (;;) {
/*  899 */                 out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td colspan=\"3\" align=\"left\" class=\"leftlinksheading\">");
/*  900 */                 out.print(FormatUtil.getString("am.webclient.gmap.businessview.text"));
/*  901 */                 out.write("</td>\n  </tr>\n  <tr>\n  \t<td colspan=\"3\" align=\"left\" class=\"leftlinkstd\">\n  \t\t<a href=\"/showBussiness.do?method=generateApplicationAvailablity&selectTabName=SLA\" class=\"new-left-links\">");
/*  902 */                 out.print(FormatUtil.getString("am.webclient.selectmonitorview.SLAview.text"));
/*  903 */                 out.write("</a>\n  \t</td>\n  </tr>\n   <tr>\n         <td colspan=\"3\" align=\"left\" class=\"leftlinkstd\">\n    \t    \t<a href=\"javascript:void(0);\"  onClick=\"window.open('/GraphicalView.do?method=popUp&haid=0&isPopUp=true','FlashView','scrollbars=1,resizable=1,width='+(screen.availWidth-50)+',height='+(screen.availHeight-50)+',left=5,top=5,screenX=250,screenY=25');\" class=\"new-left-links\">");
/*  904 */                 out.print(FormatUtil.getString("am.webclient.flashview.displayname"));
/*  905 */                 out.write("</a>\n         </td>\n  </tr>\n</table>\n");
/*  906 */                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/*  907 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  911 */             if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/*  912 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/*      */             }
/*      */             else {
/*  915 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/*  916 */               out.write(10);
/*  917 */               out.write(10);
/*      */               
/*  919 */               PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/*  920 */               _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/*  921 */               _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */               
/*  923 */               _jspx_th_logic_005fpresent_005f3.setRole("OPERATOR");
/*  924 */               int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/*  925 */               if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                 for (;;) {
/*  927 */                   out.write(10);
/*  928 */                   out.write(32);
/*      */                   
/*  930 */                   IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  931 */                   _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  932 */                   _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                   
/*  934 */                   _jspx_th_c_005fif_005f0.setTest("${allowDownTimeSchedule=='true'}");
/*  935 */                   int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  936 */                   if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                     for (;;) {
/*  938 */                       out.write("\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n\t  <tr>\n\t    \t<td class=\"leftlinksheading\">");
/*  939 */                       out.print(FormatUtil.getString("Tasks"));
/*  940 */                       out.write("</td>\n\t  </tr>\n\t  <tr>\n\t    \t<td class=\"leftlinkstd\"><a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/*  941 */                       out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/*  942 */                       out.write(" </a></td>\n\t  </tr>\n\t</table>\n  ");
/*  943 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  944 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  948 */                   if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  949 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                   }
/*      */                   
/*  952 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  953 */                   out.write(10);
/*  954 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/*  955 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  959 */               if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/*  960 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*      */               }
/*      */               else {
/*  963 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*  964 */                 out.write(10);
/*  965 */                 out.write(10);
/*      */                 
/*  967 */                 NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/*  968 */                 _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/*  969 */                 _jspx_th_logic_005fnotPresent_005f4.setParent(null);
/*      */                 
/*  971 */                 _jspx_th_logic_005fnotPresent_005f4.setRole("OPERATOR");
/*  972 */                 int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/*  973 */                 if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                   for (;;) {
/*  975 */                     out.write(10);
/*  976 */                     out.write(10);
/*      */                     
/*  978 */                     if (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */                     {
/*      */ 
/*  981 */                       out.write("\n<br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td colspan=\"3\" align=\"left\" class=\"leftlinksheading\">");
/*  982 */                       out.print(FormatUtil.getString("am.webclient.networkviews.heading.text"));
/*  983 */                       out.write("</td>\n  </tr>\n  <tr>\n    <td colspan=\"3\" align=\"left\" class=\"leftlinkstd\"><a class=\"new-left-links\"\n    ");
/*      */                       
/*  985 */                       String gr = request.getParameter("group");
/*  986 */                       String toAppend = "";
/*  987 */                       if (gr != null) {
/*  988 */                         toAppend = "&group=" + gr;
/*      */                       }
/*      */                       
/*  991 */                       if (network != null)
/*      */                       {
/*  993 */                         if (method.equals("showResourceTypes")) {
/*  994 */                           out.write("\n\t\t\t href=\"/showresource.do?method=");
/*  995 */                           out.print(method + toAppend);
/*  996 */                           out.write("&monitor_viewtype=categoryview\" class=\"new-left-links\"  ");
/*  997 */                           out.write(10);
/*  998 */                           out.write(9);
/*      */                         } else {
/* 1000 */                           out.write("\n\t \t \t href=\"/showresource.do?method=");
/* 1001 */                           out.print(method + toAppend);
/* 1002 */                           out.write("\" class=\"new-left-links\"  ");
/* 1003 */                           out.write(10);
/*      */                         }
/*      */                       }
/*      */                       
/* 1007 */                       out.write("\n    >");
/* 1008 */                       out.print(FormatUtil.getString("am.webclient.networkdetails.title"));
/* 1009 */                       out.write("</a></td>\n  </tr>\n  ");
/*      */                       
/* 1011 */                       ArrayList list = mo.getRows("select AM_ManagedObject.RESOURCENAME,AM_ManagedObject.displayname from AM_ManagedObject,Network,IpAddress where Network.NAME=IpAddress.PARENTNET and AM_ManagedObject.RESOURCENAME=IpAddress.PARENTNET group by RESOURCENAME,displayname");
/* 1012 */                       for (int i = 0; i < list.size(); i++)
/*      */                       {
/* 1014 */                         ArrayList oneList = (ArrayList)list.get(i);
/*      */                         
/*      */ 
/* 1017 */                         out.write("\n  <tr>\n    <td colspan=\"3\" align=\"left\" class=\"leftlinkstd\"><a class=\"new-left-links\"\n    ");
/*      */                         
/* 1019 */                         if (!oneList.get(0).equals(network))
/*      */                         {
/* 1021 */                           String toAppend1 = "";
/* 1022 */                           if ((method.equals("showResourceTypesAll")) || ((method.equals("showResourceTypes")) && (request.getParameter("detailspage") != null)))
/*      */                           {
/* 1024 */                             toAppend1 = "&detailspage=true";
/*      */                           }
/* 1026 */                           if (method.equals("showResourceTypes"))
/*      */                           {
/* 1028 */                             out.write("\n       \t   href=\"/showresource.do?method=");
/* 1029 */                             out.print(method + toAppend + toAppend1);
/* 1030 */                             out.write("&selectedNetwork=");
/* 1031 */                             out.print(oneList.get(0));
/* 1032 */                             out.write("&monitor_viewtype=categoryview\"  class=\"new-left-links\" ");
/* 1033 */                             out.write("\n   ");
/*      */                           }
/*      */                           else {
/* 1036 */                             out.write("\n       \t \thref=\"/showresource.do?method=");
/* 1037 */                             out.print(method + toAppend + toAppend1);
/* 1038 */                             out.write("&selectedNetwork=");
/* 1039 */                             out.print(oneList.get(0));
/* 1040 */                             out.write("\"  class=\"new-left-links\" ");
/* 1041 */                             out.write("\n   ");
/*      */                           }
/*      */                         }
/*      */                         
/* 1045 */                         out.write(10);
/* 1046 */                         out.write(62);
/* 1047 */                         out.write(10);
/* 1048 */                         out.print(oneList.get(1));
/* 1049 */                         out.write("</a>\n    </td>\n  </tr>\n ");
/*      */                       }
/*      */                       
/*      */ 
/* 1053 */                       out.write("\n\n\n</table>\n");
/*      */                     }
/*      */                     
/*      */ 
/* 1057 */                     out.write(10);
/* 1058 */                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 1059 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1063 */                 if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 1064 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/*      */                 }
/*      */                 else {
/* 1067 */                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 1068 */                   out.write("\n<br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  class=\"leftmnutables\"><tr>\n    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 1069 */                   out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 1070 */                   out.write("</td>\n    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 1071 */                   if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                     return;
/* 1073 */                   out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n  </tr>\n  <tr>\n    <td height=\"29\" colspan=\"2\" align=\"left\" class=\"quicknote\">");
/* 1074 */                   out.print(FormatUtil.getString("am.webclient.monitortab.quicknote.text"));
/* 1075 */                   out.write(".</td>\n  </tr>\n </table>\n");
/*      */                 }
/* 1077 */               } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1078 */         out = _jspx_out;
/* 1079 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1080 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 1081 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1084 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1090 */     PageContext pageContext = _jspx_page_context;
/* 1091 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1093 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 1094 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1095 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 1097 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 1099 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 1100 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1101 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1102 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1103 */       return true;
/*      */     }
/* 1105 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1106 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MapsLeftPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */