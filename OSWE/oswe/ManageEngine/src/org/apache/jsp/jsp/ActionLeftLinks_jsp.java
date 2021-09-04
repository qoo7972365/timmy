/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.EqualTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ActionLeftLinks_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   21 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   27 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*   28 */   static { _jspx_dependants.put("/jsp/includes/AlertLeftLinks.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   49 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   53 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   54 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   55 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   56 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   67 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   71 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*   72 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   73 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*   74 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   75 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   76 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*   77 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   78 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.release();
/*   79 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   80 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.release();
/*   81 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/*   82 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*   83 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   90 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   93 */     JspWriter out = null;
/*   94 */     Object page = this;
/*   95 */     JspWriter _jspx_out = null;
/*   96 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  100 */       response.setContentType("text/html");
/*  101 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  103 */       _jspx_page_context = pageContext;
/*  104 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  105 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  106 */       session = pageContext.getSession();
/*  107 */       out = pageContext.getOut();
/*  108 */       _jspx_out = out;
/*      */       
/*  110 */       out.write("\n\n\n\n\n\n\n\t\n");
/*      */       
/*  112 */       Object o = request.getParameter("Head");
/*  113 */       if (o != null)
/*  114 */         pageContext.setAttribute("Head", o);
/*  115 */       o = request.getParameter("Threshold");
/*  116 */       if (o != null) {
/*  117 */         pageContext.setAttribute("Threshold", o);
/*      */       }
/*      */       
/*  120 */       out.write("\n\n\n\n\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n  ");
/*      */       
/*  122 */       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/*  123 */       _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/*  124 */       _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */       
/*  126 */       _jspx_th_logic_005fnotEmpty_005f0.setName("Head");
/*  127 */       int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/*  128 */       if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */         for (;;) {
/*  130 */           out.write("\n  <tr>\n       <td height=\"21\"  class=\"leftlinksheading\">");
/*  131 */           out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/*  132 */           out.write("</td>\n  </tr>\n\n");
/*  133 */           if (_jspx_meth_c_005fif_005f0(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */             return;
/*  135 */           out.write(10);
/*  136 */           out.write(10);
/*  137 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */             return;
/*  139 */           out.write(10);
/*  140 */           out.write(32);
/*  141 */           out.write(32);
/*      */           
/*  143 */           String link = "/showTile.do?TileName=.EmailActions&haid=" + request.getParameter("haid");
/*  144 */           String smslink = "/showTile.do?TileName=.SMSActions&haid=" + request.getParameter("haid");
/*  145 */           String emaillink = "/showTile.do?TileName=.ExecProg&haid=" + request.getParameter("haid");
/*  146 */           String trap = "/adminAction.do?method=reloadSendTrapActionForm&haid=" + request.getParameter("haid");
/*  147 */           String moplink = "/MBeanOperationAction.do?method=showInitialScreen&haid=" + request.getParameter("haid");
/*      */           
/*  149 */           String ticketlink = "/adminAction.do?method=showLogTicket&haid=" + request.getParameter("haid");
/*      */           
/*      */ 
/*  152 */           String javathreaddumplink = "/JavaRuntime.do?method=showThreadDumpAction&type=7&monitorType=jre&haid=" + request.getParameter("haid");
/*  153 */           String ec2instanceActionlink = "/JavaRuntime.do?method=showThreadDumpAction&monitorType=amazon&haid=" + request.getParameter("haid");
/*  154 */           String vmActionlink = "/adminAction.do?method=showVMAction&type=101&haid=" + request.getParameter("haid");
/*  155 */           String winServiceActionlink = "/HostResourceDispatch.do?method=winServAction&type=winservact&haid=" + request.getParameter("haid");
/*      */           
/*  157 */           out.write("\n\n   <tr>\n    <td class=\"leftlinkstd\" height=\"19\">\n\n    ");
/*      */           
/*  159 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  160 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  161 */           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*  162 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  163 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */             for (;;) {
/*  165 */               out.write("\n\n    ");
/*      */               
/*  167 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  168 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  169 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */               
/*  171 */               _jspx_th_c_005fwhen_005f0.setTest("${(param.Head == '0' || param.Head == '2' || param.Head == '3' || param.Head == '4' || param.Head == '5') ||param.Head == '6' || param.Head == '8' || param.Head == '9'}");
/*  172 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  173 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                 for (;;) {
/*  175 */                   out.write("\n\n     ");
/*      */                   
/*  177 */                   AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  178 */                   _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/*  179 */                   _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                   
/*  181 */                   _jspx_th_am_005fadminlink_005f0.setHref(link);
/*      */                   
/*  183 */                   _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/*  184 */                   int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/*  185 */                   if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/*  186 */                     if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/*  187 */                       out = _jspx_page_context.pushBody();
/*  188 */                       _jspx_th_am_005fadminlink_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  189 */                       _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/*  192 */                       out.write("\n\n\n      \t");
/*  193 */                       out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  194 */                       out.write("\n\n       ");
/*  195 */                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/*  196 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  199 */                     if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/*  200 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  203 */                   if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/*  204 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                   }
/*      */                   
/*  207 */                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/*  208 */                   out.write("\n\n      ");
/*  209 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  210 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  214 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  215 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */               }
/*      */               
/*  218 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  219 */               out.write("\n      ");
/*      */               
/*  221 */               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  222 */               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  223 */               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  224 */               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  225 */               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                 for (;;) {
/*  227 */                   out.write("\n      \t");
/*  228 */                   out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/*  229 */                   out.write("\n      ");
/*  230 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  231 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  235 */               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  236 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */               }
/*      */               
/*  239 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  240 */               out.write("\n      ");
/*  241 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  242 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  246 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  247 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */           }
/*      */           
/*  250 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  251 */           out.write("\n\n\n\n      </td>\n    </tr>\n    <tr >\n      <td class=\"leftlinkstd\">\n\n      ");
/*      */           
/*  253 */           ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  254 */           _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  255 */           _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*  256 */           int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  257 */           if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */             for (;;) {
/*  259 */               out.write("\n      \t");
/*      */               
/*  261 */               WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  262 */               _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  263 */               _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */               
/*  265 */               _jspx_th_c_005fwhen_005f1.setTest("${param.Head == '0' || param.Head == '1' || param.Head == '3' || param.Head == '4'|| param.Head == '5'||param.Head == '6' || param.Head == '8' || param.Head == '9'}");
/*  266 */               int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  267 */               if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                 for (;;) {
/*  269 */                   out.write("\n\n      \t");
/*      */                   
/*  271 */                   AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  272 */                   _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/*  273 */                   _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                   
/*  275 */                   _jspx_th_am_005fadminlink_005f1.setHref(smslink);
/*      */                   
/*  277 */                   _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/*  278 */                   int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/*  279 */                   if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/*  280 */                     if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/*  281 */                       out = _jspx_page_context.pushBody();
/*  282 */                       _jspx_th_am_005fadminlink_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  283 */                       _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/*  286 */                       out.write("\n\n\n      \t");
/*  287 */                       out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  288 */                       out.write("\n      \t  ");
/*  289 */                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/*  290 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  293 */                     if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/*  294 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  297 */                   if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/*  298 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                   }
/*      */                   
/*  301 */                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/*  302 */                   out.write("\n\n      \t");
/*  303 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  304 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  308 */               if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  309 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */               }
/*      */               
/*  312 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  313 */               out.write("\n      \t");
/*      */               
/*  315 */               OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  316 */               _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  317 */               _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  318 */               int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  319 */               if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                 for (;;) {
/*  321 */                   out.write("\n      \t");
/*  322 */                   out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/*  323 */                   out.write("\n      \t");
/*  324 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  325 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  329 */               if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  330 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */               }
/*      */               
/*  333 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  334 */               out.write("\n      \t");
/*  335 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  336 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  340 */           if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  341 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */           }
/*      */           
/*  344 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  345 */           out.write("\n      </td>\n    </tr>\n");
/*      */           
/*  347 */           if (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */           {
/*      */ 
/*  350 */             out.write("\n    <tr >\n      <td class=\"leftlinkstd\">\n      ");
/*      */             
/*  352 */             ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  353 */             _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  354 */             _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*  355 */             int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  356 */             if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */               for (;;) {
/*  358 */                 out.write("\n      \t");
/*      */                 
/*  360 */                 WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  361 */                 _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  362 */                 _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                 
/*  364 */                 _jspx_th_c_005fwhen_005f2.setTest("${param.Head == '0' || param.Head == '1' || param.Head == '2'  || param.Head == '4' || param.Head == '5'||param.Head == '6'  || param.Head == '8' || param.Head == '9'}");
/*  365 */                 int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  366 */                 if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                   for (;;) {
/*  368 */                     out.write("\n\t  ");
/*      */                     
/*  370 */                     AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/*  371 */                     _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/*  372 */                     _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                     
/*  374 */                     _jspx_th_am_005fadminlink_005f2.setHref(emaillink);
/*      */                     
/*  376 */                     _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/*  377 */                     int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/*  378 */                     if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/*  379 */                       if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/*  380 */                         out = _jspx_page_context.pushBody();
/*  381 */                         _jspx_th_am_005fadminlink_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  382 */                         _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  385 */                         out.write("\n\t     ");
/*  386 */                         out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  387 */                         out.write("\n\t   ");
/*  388 */                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/*  389 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  392 */                       if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/*  393 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  396 */                     if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/*  397 */                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                     }
/*      */                     
/*  400 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/*  401 */                     out.write("\n      ");
/*  402 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  403 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  407 */                 if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  408 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                 }
/*      */                 
/*  411 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  412 */                 out.write("\n      ");
/*      */                 
/*  414 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  415 */                 _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  416 */                 _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  417 */                 int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  418 */                 if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                   for (;;) {
/*  420 */                     out.write("\n      ");
/*  421 */                     out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/*  422 */                     out.write("\n      ");
/*  423 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  424 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  428 */                 if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  429 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                 }
/*      */                 
/*  432 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  433 */                 out.write("\n      ");
/*  434 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  435 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  439 */             if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  440 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */             }
/*      */             
/*  443 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  444 */             out.write("\n\n      </td>\n     </tr>\n\n     <tr >\n\t<td class=\"leftlinkstd\">\n\t");
/*      */             
/*  446 */             ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  447 */             _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/*  448 */             _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*  449 */             int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/*  450 */             if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */               for (;;) {
/*  452 */                 out.write(10);
/*  453 */                 out.write(9);
/*      */                 
/*  455 */                 WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  456 */                 _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  457 */                 _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                 
/*  459 */                 _jspx_th_c_005fwhen_005f3.setTest("${param.Head == '0' || param.Head == '1' || param.Head == '2' || param.Head == '3' ||param.Head == '5'||param.Head == '6'  || param.Head == '8' || param.Head == '9'}");
/*  460 */                 int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  461 */                 if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                   for (;;) {
/*  463 */                     out.write(10);
/*  464 */                     out.write(9);
/*  465 */                     out.write(32);
/*      */                     
/*  467 */                     AdminLink _jspx_th_am_005fadminlink_005f3 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.get(AdminLink.class);
/*  468 */                     _jspx_th_am_005fadminlink_005f3.setPageContext(_jspx_page_context);
/*  469 */                     _jspx_th_am_005fadminlink_005f3.setParent(_jspx_th_c_005fwhen_005f3);
/*      */                     
/*  471 */                     _jspx_th_am_005fadminlink_005f3.setHref(trap);
/*      */                     
/*  473 */                     _jspx_th_am_005fadminlink_005f3.setEnableClass("new-left-links");
/*      */                     
/*  475 */                     _jspx_th_am_005fadminlink_005f3.setAccess(110);
/*  476 */                     int _jspx_eval_am_005fadminlink_005f3 = _jspx_th_am_005fadminlink_005f3.doStartTag();
/*  477 */                     if (_jspx_eval_am_005fadminlink_005f3 != 0) {
/*  478 */                       if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/*  479 */                         out = _jspx_page_context.pushBody();
/*  480 */                         _jspx_th_am_005fadminlink_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  481 */                         _jspx_th_am_005fadminlink_005f3.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  484 */                         out.write("\n\t  ");
/*  485 */                         out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/*  486 */                         out.write("\n\t  ");
/*  487 */                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f3.doAfterBody();
/*  488 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  491 */                       if (_jspx_eval_am_005fadminlink_005f3 != 1) {
/*  492 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  495 */                     if (_jspx_th_am_005fadminlink_005f3.doEndTag() == 5) {
/*  496 */                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f3); return;
/*      */                     }
/*      */                     
/*  499 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f3);
/*  500 */                     out.write("\n\t  ");
/*  501 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  502 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  506 */                 if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  507 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                 }
/*      */                 
/*  510 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  511 */                 out.write("\n\t  ");
/*      */                 
/*  513 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  514 */                 _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  515 */                 _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*  516 */                 int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  517 */                 if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                   for (;;) {
/*  519 */                     out.write("\n\t  ");
/*  520 */                     out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/*  521 */                     out.write("\n\t  ");
/*  522 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/*  523 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  527 */                 if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/*  528 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                 }
/*      */                 
/*  531 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/*  532 */                 out.write(10);
/*  533 */                 out.write(9);
/*  534 */                 out.write(32);
/*  535 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/*  536 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  540 */             if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/*  541 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */             }
/*      */             
/*  544 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*  545 */             out.write("\n\n\t  </td>\n      </tr>\n\n      <tr >\n      \t<td class=\"leftlinkstd\">\n      \t");
/*      */             
/*  547 */             ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  548 */             _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  549 */             _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*  550 */             int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  551 */             if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */               for (;;) {
/*  553 */                 out.write("\n      \t");
/*      */                 
/*  555 */                 WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  556 */                 _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  557 */                 _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                 
/*  559 */                 _jspx_th_c_005fwhen_005f4.setTest("${param.Head == '0' || param.Head == '1' || param.Head == '2' || param.Head == '3' ||param.Head == '4'||param.Head == '6'  || param.Head == '8' || param.Head == '9'}");
/*  560 */                 int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  561 */                 if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                   for (;;) {
/*  563 */                     out.write("\n      \t ");
/*      */                     
/*  565 */                     AdminLink _jspx_th_am_005fadminlink_005f4 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.get(AdminLink.class);
/*  566 */                     _jspx_th_am_005fadminlink_005f4.setPageContext(_jspx_page_context);
/*  567 */                     _jspx_th_am_005fadminlink_005f4.setParent(_jspx_th_c_005fwhen_005f4);
/*      */                     
/*  569 */                     _jspx_th_am_005fadminlink_005f4.setHref(moplink);
/*      */                     
/*  571 */                     _jspx_th_am_005fadminlink_005f4.setEnableClass("new-left-links");
/*      */                     
/*  573 */                     _jspx_th_am_005fadminlink_005f4.setAccess(110);
/*  574 */                     int _jspx_eval_am_005fadminlink_005f4 = _jspx_th_am_005fadminlink_005f4.doStartTag();
/*  575 */                     if (_jspx_eval_am_005fadminlink_005f4 != 0) {
/*  576 */                       if (_jspx_eval_am_005fadminlink_005f4 != 1) {
/*  577 */                         out = _jspx_page_context.pushBody();
/*  578 */                         _jspx_th_am_005fadminlink_005f4.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  579 */                         _jspx_th_am_005fadminlink_005f4.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  582 */                         out.write("\n      \t  ");
/*  583 */                         out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  584 */                         out.write("\n      \t  ");
/*  585 */                         int evalDoAfterBody = _jspx_th_am_005fadminlink_005f4.doAfterBody();
/*  586 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  589 */                       if (_jspx_eval_am_005fadminlink_005f4 != 1) {
/*  590 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  593 */                     if (_jspx_th_am_005fadminlink_005f4.doEndTag() == 5) {
/*  594 */                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f4); return;
/*      */                     }
/*      */                     
/*  597 */                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f4);
/*  598 */                     out.write("\n      \t  ");
/*  599 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  600 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  604 */                 if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  605 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                 }
/*      */                 
/*  608 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  609 */                 out.write("\n      \t  ");
/*      */                 
/*  611 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  612 */                 _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  613 */                 _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*  614 */                 int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  615 */                 if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                   for (;;) {
/*  617 */                     out.write("\n      \t  ");
/*  618 */                     out.print(FormatUtil.getString("am.webclient.common.mbeanoperation.text"));
/*  619 */                     out.write("\n      \t  ");
/*  620 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  621 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  625 */                 if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  626 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                 }
/*      */                 
/*  629 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  630 */                 out.write("\n      \t ");
/*  631 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/*  632 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  636 */             if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/*  637 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */             }
/*      */             
/*  640 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*  641 */             out.write("\n\n      \t  </td>\n      </tr>\n       ");
/*  642 */             if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/*  643 */               out.write("\n      ");
/*      */               
/*  645 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  646 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  647 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */               
/*  649 */               _jspx_th_c_005fif_005f2.setTest("${category!='LAMP'}");
/*  650 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  651 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/*  653 */                   out.write("\n\t  <tr >\n\t   <td class=\"leftlinkstd\">\n\t   ");
/*      */                   
/*  655 */                   ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  656 */                   _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  657 */                   _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_c_005fif_005f2);
/*  658 */                   int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  659 */                   if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                     for (;;) {
/*  661 */                       out.write("\n\t   ");
/*      */                       
/*  663 */                       WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  664 */                       _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/*  665 */                       _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                       
/*  667 */                       _jspx_th_c_005fwhen_005f5.setTest("${param.Head == '0' || param.Head == '1' || param.Head == '2' || param.Head == '3' ||param.Head == '4' ||param.Head == '5'  || param.Head == '8' || param.Head == '9'}");
/*  668 */                       int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/*  669 */                       if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                         for (;;) {
/*  671 */                           out.write("\n\t   ");
/*      */                           
/*  673 */                           AdminLink _jspx_th_am_005fadminlink_005f5 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.get(AdminLink.class);
/*  674 */                           _jspx_th_am_005fadminlink_005f5.setPageContext(_jspx_page_context);
/*  675 */                           _jspx_th_am_005fadminlink_005f5.setParent(_jspx_th_c_005fwhen_005f5);
/*      */                           
/*  677 */                           _jspx_th_am_005fadminlink_005f5.setHref(ticketlink);
/*      */                           
/*  679 */                           _jspx_th_am_005fadminlink_005f5.setEnableClass("new-left-links");
/*      */                           
/*  681 */                           _jspx_th_am_005fadminlink_005f5.setAccess(110);
/*  682 */                           int _jspx_eval_am_005fadminlink_005f5 = _jspx_th_am_005fadminlink_005f5.doStartTag();
/*  683 */                           if (_jspx_eval_am_005fadminlink_005f5 != 0) {
/*  684 */                             if (_jspx_eval_am_005fadminlink_005f5 != 1) {
/*  685 */                               out = _jspx_page_context.pushBody();
/*  686 */                               _jspx_th_am_005fadminlink_005f5.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  687 */                               _jspx_th_am_005fadminlink_005f5.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/*  690 */                               out.write("\n\t   ");
/*  691 */                               out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  692 */                               out.write("\n\t   ");
/*  693 */                               int evalDoAfterBody = _jspx_th_am_005fadminlink_005f5.doAfterBody();
/*  694 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*  697 */                             if (_jspx_eval_am_005fadminlink_005f5 != 1) {
/*  698 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/*  701 */                           if (_jspx_th_am_005fadminlink_005f5.doEndTag() == 5) {
/*  702 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f5); return;
/*      */                           }
/*      */                           
/*  705 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f5);
/*  706 */                           out.write("\n\t   ");
/*  707 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/*  708 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  712 */                       if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/*  713 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                       }
/*      */                       
/*  716 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*  717 */                       out.write("\n\t   ");
/*      */                       
/*  719 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  720 */                       _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  721 */                       _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*  722 */                       int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  723 */                       if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                         for (;;) {
/*  725 */                           out.write("\n\t   ");
/*  726 */                           out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/*  727 */                           out.write("\n\t   ");
/*  728 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  729 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/*  733 */                       if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  734 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                       }
/*      */                       
/*  737 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  738 */                       out.write("\n\t   ");
/*  739 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  740 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  744 */                   if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  745 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                   }
/*      */                   
/*  748 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  749 */                   out.write("\n       </td>\n\t   </tr>\n\n");
/*  750 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  751 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  755 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  756 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/*  759 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */             } }
/*  761 */           out.write("\n\n        <!-- JRE Actions-->\n        <tr >\n\t      \t <td class=\"leftlinkstd\" >\n\n\t\t\t  ");
/*  762 */           if ((request.getParameter("type") != null) && (request.getParameter("type").equals("7"))) {
/*  763 */             out.write("\n\t      \t <div style=\"float:left; margin-left:8px;  display:block; line-height:12px; width:100%; cursor:hand; text-indent:0px;\">");
/*  764 */             out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  765 */             out.write("</div>\n\t\t\t  ");
/*      */           } else {
/*  767 */             out.write("\n\t\t\t  ");
/*      */             
/*  769 */             AdminLink _jspx_th_am_005fadminlink_005f6 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.get(AdminLink.class);
/*  770 */             _jspx_th_am_005fadminlink_005f6.setPageContext(_jspx_page_context);
/*  771 */             _jspx_th_am_005fadminlink_005f6.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */             
/*  773 */             _jspx_th_am_005fadminlink_005f6.setHref(javathreaddumplink);
/*      */             
/*  775 */             _jspx_th_am_005fadminlink_005f6.setEnableClass("new-left-links");
/*      */             
/*  777 */             _jspx_th_am_005fadminlink_005f6.setAccess(110);
/*  778 */             int _jspx_eval_am_005fadminlink_005f6 = _jspx_th_am_005fadminlink_005f6.doStartTag();
/*  779 */             if (_jspx_eval_am_005fadminlink_005f6 != 0) {
/*  780 */               if (_jspx_eval_am_005fadminlink_005f6 != 1) {
/*  781 */                 out = _jspx_page_context.pushBody();
/*  782 */                 _jspx_th_am_005fadminlink_005f6.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  783 */                 _jspx_th_am_005fadminlink_005f6.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  786 */                 out.write("\n\t      \t  <div style=\"float:left; margin-left:8px;  display:block; line-height:12px; width:100%; cursor:hand; text-indent:0px;\">");
/*  787 */                 out.print(FormatUtil.getString("am.javaruntime.action.createnew"));
/*  788 */                 out.write("</div>\n\t      \t  ");
/*  789 */                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f6.doAfterBody();
/*  790 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  793 */               if (_jspx_eval_am_005fadminlink_005f6 != 1) {
/*  794 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  797 */             if (_jspx_th_am_005fadminlink_005f6.doEndTag() == 5) {
/*  798 */               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f6); return;
/*      */             }
/*      */             
/*  801 */             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f6);
/*  802 */             out.write(32);
/*  803 */             out.write("\n\t\t\t  ");
/*      */           }
/*  805 */           out.write("\n\n\t      \t  </td>\n          </tr>\n\t\t <!-- JRE Actions-->\n\n\t\t <!--  amazon actions -->\n\t<tr>\n      \t \t<td class=\"leftlinkstd\">\n\t\t  ");
/*  806 */           if ((request.getParameter("monitorType") != null) && (request.getParameter("monitorType").equals("amazon"))) {
/*  807 */             out.write("\n\t\t  ");
/*  808 */             out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  809 */             out.write("\n\t\t  ");
/*      */           } else {
/*  811 */             out.write("\n\t\t  ");
/*      */             
/*  813 */             AdminLink _jspx_th_am_005fadminlink_005f7 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.get(AdminLink.class);
/*  814 */             _jspx_th_am_005fadminlink_005f7.setPageContext(_jspx_page_context);
/*  815 */             _jspx_th_am_005fadminlink_005f7.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */             
/*  817 */             _jspx_th_am_005fadminlink_005f7.setHref(ec2instanceActionlink);
/*      */             
/*  819 */             _jspx_th_am_005fadminlink_005f7.setEnableClass("new-left-links");
/*      */             
/*  821 */             _jspx_th_am_005fadminlink_005f7.setAccess(110);
/*  822 */             int _jspx_eval_am_005fadminlink_005f7 = _jspx_th_am_005fadminlink_005f7.doStartTag();
/*  823 */             if (_jspx_eval_am_005fadminlink_005f7 != 0) {
/*  824 */               if (_jspx_eval_am_005fadminlink_005f7 != 1) {
/*  825 */                 out = _jspx_page_context.pushBody();
/*  826 */                 _jspx_th_am_005fadminlink_005f7.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  827 */                 _jspx_th_am_005fadminlink_005f7.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  830 */                 out.write("\n\t\t  ");
/*  831 */                 out.print(FormatUtil.getString("am.amazon.ec2Instanceaction.action.text"));
/*  832 */                 out.write("\n\t\t  ");
/*  833 */                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f7.doAfterBody();
/*  834 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  837 */               if (_jspx_eval_am_005fadminlink_005f7 != 1) {
/*  838 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  841 */             if (_jspx_th_am_005fadminlink_005f7.doEndTag() == 5) {
/*  842 */               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f7); return;
/*      */             }
/*      */             
/*  845 */             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f7);
/*  846 */             out.write(32);
/*  847 */             out.write("\n\t\t  ");
/*      */           }
/*  849 */           out.write("\n\n\t       </td>\n\t </tr>\n\n\t  <!-- vm Actions-->\n        <tr>\n\t      \t <td class=\"leftlinkstd\">\n\t\t\t  ");
/*  850 */           if ((request.getParameter("type") != null) && (request.getParameter("type").equals("101"))) {
/*  851 */             out.write("\n\t      \t  ");
/*  852 */             out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  853 */             out.write("\n\t\t\t  ");
/*      */           } else {
/*  855 */             out.write("\n\t\t\t  ");
/*      */             
/*  857 */             AdminLink _jspx_th_am_005fadminlink_005f8 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.get(AdminLink.class);
/*  858 */             _jspx_th_am_005fadminlink_005f8.setPageContext(_jspx_page_context);
/*  859 */             _jspx_th_am_005fadminlink_005f8.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */             
/*  861 */             _jspx_th_am_005fadminlink_005f8.setHref(vmActionlink);
/*      */             
/*  863 */             _jspx_th_am_005fadminlink_005f8.setEnableClass("new-left-links");
/*      */             
/*  865 */             _jspx_th_am_005fadminlink_005f8.setAccess(110);
/*  866 */             int _jspx_eval_am_005fadminlink_005f8 = _jspx_th_am_005fadminlink_005f8.doStartTag();
/*  867 */             if (_jspx_eval_am_005fadminlink_005f8 != 0) {
/*  868 */               if (_jspx_eval_am_005fadminlink_005f8 != 1) {
/*  869 */                 out = _jspx_page_context.pushBody();
/*  870 */                 _jspx_th_am_005fadminlink_005f8.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  871 */                 _jspx_th_am_005fadminlink_005f8.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  874 */                 out.write("\n\t      \t  ");
/*  875 */                 out.print(FormatUtil.getString("am.vm.action.createnew"));
/*  876 */                 out.write("\n\t      \t  ");
/*  877 */                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f8.doAfterBody();
/*  878 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  881 */               if (_jspx_eval_am_005fadminlink_005f8 != 1) {
/*  882 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  885 */             if (_jspx_th_am_005fadminlink_005f8.doEndTag() == 5) {
/*  886 */               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f8); return;
/*      */             }
/*      */             
/*  889 */             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f8);
/*  890 */             out.write(32);
/*  891 */             out.write("\n\t\t\t  ");
/*      */           }
/*  893 */           out.write("\n\t      \t  </td>\n          </tr>\n        <!-- vm Actions-->\n        <!-- Windows Service Actions -->\n        <tr>\n\t      \t <td class=\"leftlinkstd\">\n\t\t\t  ");
/*  894 */           if ((request.getParameter("type") != null) && (request.getParameter("type").equals("winservact"))) {
/*  895 */             out.write("\n\t      \t  ");
/*  896 */             out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  897 */             out.write("\n\t\t\t  ");
/*      */           } else {
/*  899 */             out.write("\n\t\t\t  ");
/*      */             
/*  901 */             AdminLink _jspx_th_am_005fadminlink_005f9 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.get(AdminLink.class);
/*  902 */             _jspx_th_am_005fadminlink_005f9.setPageContext(_jspx_page_context);
/*  903 */             _jspx_th_am_005fadminlink_005f9.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */             
/*  905 */             _jspx_th_am_005fadminlink_005f9.setHref(winServiceActionlink);
/*      */             
/*  907 */             _jspx_th_am_005fadminlink_005f9.setEnableClass("new-left-links");
/*      */             
/*  909 */             _jspx_th_am_005fadminlink_005f9.setAccess(110);
/*  910 */             int _jspx_eval_am_005fadminlink_005f9 = _jspx_th_am_005fadminlink_005f9.doStartTag();
/*  911 */             if (_jspx_eval_am_005fadminlink_005f9 != 0) {
/*  912 */               if (_jspx_eval_am_005fadminlink_005f9 != 1) {
/*  913 */                 out = _jspx_page_context.pushBody();
/*  914 */                 _jspx_th_am_005fadminlink_005f9.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  915 */                 _jspx_th_am_005fadminlink_005f9.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  918 */                 out.write("\n\t      \t  ");
/*  919 */                 out.print(FormatUtil.getString("am.windowsservices.action.createnew"));
/*  920 */                 out.write("\n\t      \t  ");
/*  921 */                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f9.doAfterBody();
/*  922 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  925 */               if (_jspx_eval_am_005fadminlink_005f9 != 1) {
/*  926 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  929 */             if (_jspx_th_am_005fadminlink_005f9.doEndTag() == 5) {
/*  930 */               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f9); return;
/*      */             }
/*      */             
/*  933 */             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass_005faccess.reuse(_jspx_th_am_005fadminlink_005f9);
/*  934 */             out.write(32);
/*  935 */             out.write("\n\t\t\t  ");
/*      */           }
/*  937 */           out.write("\n\t      \t  </td>\n          </tr>\n        <!-- Windows Service Actions -->\n          </table>\n\n<br>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n  <tr><td width=\"80%\" class=\"leftlinksquicknote\">");
/*  938 */           out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/*  939 */           out.write("</td>\n    <td width=\"20%\" class=\"leftlinksheading\"><img src=\"/images/");
/*  940 */           if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */             return;
/*  942 */           out.write("/img_quicknote.gif\"      hspace=\"5\"></td>\n  </tr>\n\t  <tr>\n\n    <td height=\"98\" colspan=\"2\" class=\"quicknote\">");
/*  943 */           out.print(FormatUtil.getString("am.webclient.actions.quicknote.text"));
/*  944 */           out.write("<br>\n    <br>\n      ");
/*      */           
/*  946 */           IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  947 */           _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  948 */           _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */           
/*  950 */           _jspx_th_c_005fif_005f3.setTest("${param.Head == '0'}");
/*  951 */           int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  952 */           if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */             for (;;) {
/*  954 */               out.print(FormatUtil.getString("am.webclient.actions.quicknote.allactions.text"));
/*  955 */               out.write(32);
/*  956 */               int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  957 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  961 */           if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  962 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */           }
/*      */           
/*  965 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  966 */           out.write("\n      ");
/*      */           
/*  968 */           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  969 */           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  970 */           _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */           
/*  972 */           _jspx_th_c_005fif_005f4.setTest("${param.Head == '1'}");
/*  973 */           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  974 */           if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */             for (;;) {
/*  976 */               out.print(FormatUtil.getString("am.webclient.actions.quicknote.emailaction.text"));
/*  977 */               int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  978 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  982 */           if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  983 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */           }
/*      */           
/*  986 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  987 */           out.write("\n      ");
/*      */           
/*  989 */           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  990 */           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  991 */           _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */           
/*  993 */           _jspx_th_c_005fif_005f5.setTest("${param.Head == '2'}");
/*  994 */           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  995 */           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */             for (;;) {
/*  997 */               out.print(FormatUtil.getString("am.webclient.actions.quicknote.smsaction.text"));
/*  998 */               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/*  999 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1003 */           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1004 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */           }
/*      */           
/* 1007 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1008 */           out.write("\n      ");
/*      */           
/* 1010 */           IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1011 */           _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1012 */           _jspx_th_c_005fif_005f6.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */           
/* 1014 */           _jspx_th_c_005fif_005f6.setTest("${param.Head == '3'}");
/* 1015 */           int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1016 */           if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */             for (;;) {
/* 1018 */               out.print(FormatUtil.getString("am.webclient.actions.quicknote.executeprogram.text"));
/* 1019 */               int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1020 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1024 */           if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1025 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */           }
/*      */           
/* 1028 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1029 */           out.write("\n      ");
/*      */           
/* 1031 */           IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1032 */           _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 1033 */           _jspx_th_c_005fif_005f7.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */           
/* 1035 */           _jspx_th_c_005fif_005f7.setTest("${param.Head == '4'}");
/* 1036 */           int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 1037 */           if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */             for (;;) {
/* 1039 */               out.print(FormatUtil.getString("am.webclient.actions.quicknote.sendtrap.text"));
/* 1040 */               int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1041 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1045 */           if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1046 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */           }
/*      */           
/* 1049 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1050 */           out.write("\n      ");
/*      */           
/* 1052 */           IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1053 */           _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 1054 */           _jspx_th_c_005fif_005f8.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */           
/* 1056 */           _jspx_th_c_005fif_005f8.setTest("${param.Head == '5'}");
/* 1057 */           int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 1058 */           if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */             for (;;) {
/* 1060 */               out.write(32);
/* 1061 */               out.print(FormatUtil.getString("am.webclient.actions.quicknote.mbeanoperation.text"));
/* 1062 */               int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 1063 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1067 */           if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 1068 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */           }
/*      */           
/* 1071 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 1072 */           out.write("\n\t  ");
/*      */           
/* 1074 */           IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1075 */           _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 1076 */           _jspx_th_c_005fif_005f9.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */           
/* 1078 */           _jspx_th_c_005fif_005f9.setTest("${param.Head == '6'}");
/* 1079 */           int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 1080 */           if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */             for (;;) {
/* 1082 */               out.print(FormatUtil.getString("am.webclient.actions.quicknote.logaticket.text"));
/* 1083 */               int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 1084 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1088 */           if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 1089 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */           }
/*      */           
/* 1092 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 1093 */           out.write("\n      </td>\n\t  </tr>\n\t  </table>\n\n");
/* 1094 */           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 1095 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1099 */       if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 1100 */         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*      */       }
/*      */       else {
/* 1103 */         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 1104 */         out.write("\n<!-- Links for Threshold configuration -->\n");
/*      */         
/* 1106 */         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 1107 */         _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 1108 */         _jspx_th_logic_005fnotEmpty_005f1.setParent(null);
/*      */         
/* 1110 */         _jspx_th_logic_005fnotEmpty_005f1.setName("Threshold");
/* 1111 */         int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 1112 */         if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */           for (;;) {
/* 1114 */             out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n  <tr>\n            <td height=\"19\" class=\"leftlinksheading\">");
/* 1115 */             out.print(FormatUtil.getString("am.webclient.configurealert.thresholdprofile"));
/* 1116 */             out.write("</td>\n          </tr>\n          <tr>\n\n    <td class=\"leftlinkstd\" height=\"19\"> ");
/*      */             
/* 1118 */             EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.get(EqualTag.class);
/* 1119 */             _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/* 1120 */             _jspx_th_logic_005fequal_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */             
/* 1122 */             _jspx_th_logic_005fequal_005f0.setName("Threshold");
/*      */             
/* 1124 */             _jspx_th_logic_005fequal_005f0.setValue("0");
/* 1125 */             int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/* 1126 */             if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*      */               for (;;) {
/* 1128 */                 out.print(FormatUtil.getString("am.webclient.actions.viewall.text"));
/* 1129 */                 out.write("\n      ");
/* 1130 */                 int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/* 1131 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1135 */             if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/* 1136 */               this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f0); return;
/*      */             }
/*      */             
/* 1139 */             this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 1140 */             out.write(32);
/*      */             
/* 1142 */             EqualTag _jspx_th_logic_005fequal_005f1 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.get(EqualTag.class);
/* 1143 */             _jspx_th_logic_005fequal_005f1.setPageContext(_jspx_page_context);
/* 1144 */             _jspx_th_logic_005fequal_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */             
/* 1146 */             _jspx_th_logic_005fequal_005f1.setName("Threshold");
/*      */             
/* 1148 */             _jspx_th_logic_005fequal_005f1.setValue("1");
/* 1149 */             int _jspx_eval_logic_005fequal_005f1 = _jspx_th_logic_005fequal_005f1.doStartTag();
/* 1150 */             if (_jspx_eval_logic_005fequal_005f1 != 0) {
/*      */               for (;;) {
/* 1152 */                 out.write(" <a href=\"/common/viewThreshold.do?haid=");
/* 1153 */                 out.print(request.getParameter("haid"));
/* 1154 */                 out.write("\" class=\"new-left-links\">");
/* 1155 */                 out.print(FormatUtil.getString("am.webclient.actions.viewall.text"));
/* 1156 */                 out.write("</a>\n      ");
/* 1157 */                 int evalDoAfterBody = _jspx_th_logic_005fequal_005f1.doAfterBody();
/* 1158 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1162 */             if (_jspx_th_logic_005fequal_005f1.doEndTag() == 5) {
/* 1163 */               this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f1); return;
/*      */             }
/*      */             
/* 1166 */             this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f1);
/* 1167 */             out.write(" </td>\n          </tr>\n\n          <tr>\n\n\n\n    <td height=\"19\" class=\"leftlinkstd\"> ");
/*      */             
/* 1169 */             IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1170 */             _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 1171 */             _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */             
/* 1173 */             _jspx_th_c_005fif_005f10.setTest("${!empty ADMIN}");
/* 1174 */             int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 1175 */             if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */               for (;;) {
/* 1177 */                 out.write(32);
/*      */                 
/* 1179 */                 EqualTag _jspx_th_logic_005fequal_005f2 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.get(EqualTag.class);
/* 1180 */                 _jspx_th_logic_005fequal_005f2.setPageContext(_jspx_page_context);
/* 1181 */                 _jspx_th_logic_005fequal_005f2.setParent(_jspx_th_c_005fif_005f10);
/*      */                 
/* 1183 */                 _jspx_th_logic_005fequal_005f2.setName("Threshold");
/*      */                 
/* 1185 */                 _jspx_th_logic_005fequal_005f2.setValue("0");
/* 1186 */                 int _jspx_eval_logic_005fequal_005f2 = _jspx_th_logic_005fequal_005f2.doStartTag();
/* 1187 */                 if (_jspx_eval_logic_005fequal_005f2 != 0) {
/*      */                   for (;;) {
/* 1189 */                     out.write("\n      <a href=\"/showTile.do?TileName=.ThresholdConf&haid=");
/* 1190 */                     out.print(request.getParameter("haid"));
/* 1191 */                     out.write("\" class=\"");
/* 1192 */                     if (_jspx_meth_c_005fout_005f1(_jspx_th_logic_005fequal_005f2, _jspx_page_context))
/*      */                       return;
/* 1194 */                     out.write(34);
/* 1195 */                     out.write(62);
/* 1196 */                     out.print(FormatUtil.getString("am.webclient.user.addnew.text"));
/* 1197 */                     out.write("\n      </a> ");
/* 1198 */                     int evalDoAfterBody = _jspx_th_logic_005fequal_005f2.doAfterBody();
/* 1199 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1203 */                 if (_jspx_th_logic_005fequal_005f2.doEndTag() == 5) {
/* 1204 */                   this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f2); return;
/*      */                 }
/*      */                 
/* 1207 */                 this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f2);
/* 1208 */                 out.write(32);
/*      */                 
/* 1210 */                 EqualTag _jspx_th_logic_005fequal_005f3 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.get(EqualTag.class);
/* 1211 */                 _jspx_th_logic_005fequal_005f3.setPageContext(_jspx_page_context);
/* 1212 */                 _jspx_th_logic_005fequal_005f3.setParent(_jspx_th_c_005fif_005f10);
/*      */                 
/* 1214 */                 _jspx_th_logic_005fequal_005f3.setName("Threshold");
/*      */                 
/* 1216 */                 _jspx_th_logic_005fequal_005f3.setValue("1");
/* 1217 */                 int _jspx_eval_logic_005fequal_005f3 = _jspx_th_logic_005fequal_005f3.doStartTag();
/* 1218 */                 if (_jspx_eval_logic_005fequal_005f3 != 0) {
/*      */                   for (;;) {
/* 1220 */                     out.print(FormatUtil.getString("am.webclient.user.addnew.text"));
/* 1221 */                     out.write(32);
/* 1222 */                     int evalDoAfterBody = _jspx_th_logic_005fequal_005f3.doAfterBody();
/* 1223 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1227 */                 if (_jspx_th_logic_005fequal_005f3.doEndTag() == 5) {
/* 1228 */                   this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f3); return;
/*      */                 }
/*      */                 
/* 1231 */                 this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fname.reuse(_jspx_th_logic_005fequal_005f3);
/* 1232 */                 out.write("\n      ");
/* 1233 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 1234 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1238 */             if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 1239 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */             }
/*      */             
/* 1242 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 1243 */             out.write(32);
/*      */             
/* 1245 */             IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1246 */             _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 1247 */             _jspx_th_c_005fif_005f11.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */             
/* 1249 */             _jspx_th_c_005fif_005f11.setTest("${empty ADMIN}");
/* 1250 */             int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 1251 */             if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */               for (;;) {
/* 1253 */                 out.write(32);
/*      */                 
/* 1255 */                 AdminLink _jspx_th_am_005fadminlink_005f10 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 1256 */                 _jspx_th_am_005fadminlink_005f10.setPageContext(_jspx_page_context);
/* 1257 */                 _jspx_th_am_005fadminlink_005f10.setParent(_jspx_th_c_005fif_005f11);
/*      */                 
/* 1259 */                 _jspx_th_am_005fadminlink_005f10.setHref("/jsp/CreateApplication.jsp");
/*      */                 
/* 1261 */                 _jspx_th_am_005fadminlink_005f10.setEnableClass("new-left-links");
/* 1262 */                 int _jspx_eval_am_005fadminlink_005f10 = _jspx_th_am_005fadminlink_005f10.doStartTag();
/* 1263 */                 if (_jspx_eval_am_005fadminlink_005f10 != 0) {
/* 1264 */                   if (_jspx_eval_am_005fadminlink_005f10 != 1) {
/* 1265 */                     out = _jspx_page_context.pushBody();
/* 1266 */                     _jspx_th_am_005fadminlink_005f10.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1267 */                     _jspx_th_am_005fadminlink_005f10.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 1270 */                     out.write("\n      ");
/* 1271 */                     out.print(FormatUtil.getString("am.webclient.user.addnew.text"));
/* 1272 */                     out.write(32);
/* 1273 */                     out.write(32);
/* 1274 */                     int evalDoAfterBody = _jspx_th_am_005fadminlink_005f10.doAfterBody();
/* 1275 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 1278 */                   if (_jspx_eval_am_005fadminlink_005f10 != 1) {
/* 1279 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 1282 */                 if (_jspx_th_am_005fadminlink_005f10.doEndTag() == 5) {
/* 1283 */                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f10); return;
/*      */                 }
/*      */                 
/* 1286 */                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f10);
/* 1287 */                 out.write(32);
/* 1288 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 1289 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1293 */             if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 1294 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */             }
/*      */             
/* 1297 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 1298 */             out.write(" </td>\n          </tr>\n </table>\n <br>\n\n\n");
/* 1299 */             out.write("\n\n\n\n");
/*      */             
/* 1301 */             String critical = "0";
/* 1302 */             String warning = "0";
/* 1303 */             String clear = "0";
/* 1304 */             int criticalper = 0;
/* 1305 */             int clearper = 0;
/* 1306 */             int warningper = 0;
/* 1307 */             int total = 0;
/*      */             try
/*      */             {
/* 1310 */               if (request.getAttribute("alertdetails") != null) {
/* 1311 */                 java.util.Hashtable ht = (java.util.Hashtable)request.getAttribute("alertdetails");
/*      */                 
/*      */ 
/* 1314 */                 critical = (String)ht.get("Critical");
/* 1315 */                 warning = (String)ht.get("Warning");
/* 1316 */                 clear = (String)ht.get("Clear");
/* 1317 */                 total = Integer.parseInt(critical) + Integer.parseInt(warning) + Integer.parseInt(clear);
/* 1318 */                 if (total > 0)
/*      */                 {
/* 1320 */                   criticalper = Integer.parseInt(critical) * 100 / total;
/* 1321 */                   clearper = Integer.parseInt(clear) * 100 / total;
/* 1322 */                   warningper = Integer.parseInt(warning) * 100 / total;
/*      */                 }
/*      */               }
/*      */             }
/*      */             catch (Exception alertcount)
/*      */             {
/* 1328 */               alertcount.printStackTrace();
/*      */             }
/*      */             
/* 1331 */             out.write(10);
/*      */             
/* 1333 */             IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1334 */             _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 1335 */             _jspx_th_c_005fif_005f12.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */             
/* 1337 */             _jspx_th_c_005fif_005f12.setTest("${!empty isAlertsPage}");
/* 1338 */             int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 1339 */             if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */               for (;;) {
/* 1341 */                 out.write(10);
/* 1342 */                 out.write(10);
/* 1343 */                 out.write(32);
/*      */                 
/* 1345 */                 if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                 {
/*      */ 
/* 1348 */                   out.write("\n\n\n    <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\" >\n    \t<tr>\n\n    \t\t<td  class=\"leftlinksheading \" colspan=\"3\" height=\"20\">");
/* 1349 */                   out.print(FormatUtil.getString("Total Alarms"));
/* 1350 */                   out.write(32);
/* 1351 */                   out.write(40);
/* 1352 */                   out.print(total);
/* 1353 */                   out.write(")</td>\n\n    \t</tr>\n    <tr>\n\n    \t<td>\n    \t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\"  width=\"100%\" style=\"background-color:#ffffff; font-size:11px; font-family:Arial, Helvetica, sans-serif;\" >\n\n    \t\t\t<tr><td clospan=\"5\" height=\"10\"></td></tr>\n    \t\t\t<tr><td width=\"1%\"></td>\n    \t\t\t\t<td width=\"28%\">&nbsp;");
/* 1354 */                   out.print(FormatUtil.getString("Critical"));
/* 1355 */                   out.write("</td>\n    \t\t\t\t<td  align=\"center\"><a style=\"text-decoration:underline; color:#000;\" href=\"javascript:fnCallLink('Critical',");
/* 1356 */                   out.print(critical);
/* 1357 */                   out.write(")\" title=\"Critical : ");
/* 1358 */                   out.print(critical);
/* 1359 */                   out.write(32);
/* 1360 */                   out.write(34);
/* 1361 */                   out.write(62);
/* 1362 */                   out.print(critical);
/* 1363 */                   out.write("</a></td>\n    \t\t\t\t<td align=\"center\"><table style=\"border: 1px solid rgb(0, 0, 0);\" width=\"80\" cellpadding=\"0\" cellspacing=\"0\" height=\"10\">\n\t\t\t\t         <tbody><tr>\n\n\t\t\t\t          <td  align=\"center\" title=\"Critical : ");
/* 1364 */                   out.print(critical);
/* 1365 */                   out.write(" \" onclick=\"javascript:fnCallLink('Critical',");
/* 1366 */                   out.print(critical);
/* 1367 */                   out.write(")\" style=\"background-color: #ff1616; cursor:pointer; background-position: 18px 50%;\" width=");
/* 1368 */                   out.print(criticalper);
/* 1369 */                   out.write(" height=\"10\"> </td>\n\t\t\t\t\t<td  align=\"center\" onclick=\"javascript:fnCallLink('Critical',");
/* 1370 */                   out.print(critical);
/* 1371 */                   out.write(")\"  title=\"Critical : ");
/* 1372 */                   out.print(critical);
/* 1373 */                   out.write(" \" style=\"background-color: #ffffff; cursor:pointer; background-position: 18px 50%;\" width=");
/* 1374 */                   out.print(100 - criticalper);
/* 1375 */                   out.write(" height=\"10\"> </td>\n\t\t\t\t\t<td width=\"1%\"></td>\n\t\t\t\t        </tr>\n       </tbody></table></td>\n    \t\t\t</tr>\n\n    \t\t\t<tr><td clospan=\"5\" height=\"10\"></td></tr>\n\t\t\t    \t\t\t<tr>\n\t\t\t    \t\t\t<td width=\"1%\"></td>\n\t\t\t    \t\t\t<td width=\"10%\">&nbsp;");
/* 1376 */                   out.print(FormatUtil.getString("Warning"));
/* 1377 */                   out.write("</td>\n\t\t\t    \t\t\t<td width=\"10%\"  align=\"center\"><a style=\"text-decoration:underline; color:#000;\" title=\"Warning : ");
/* 1378 */                   out.print(warning);
/* 1379 */                   out.write(" \" href=\"javascript:fnCallLink('Warning',");
/* 1380 */                   out.print(warning);
/* 1381 */                   out.write(41);
/* 1382 */                   out.write(34);
/* 1383 */                   out.write(62);
/* 1384 */                   out.print(warning);
/* 1385 */                   out.write("</a></td>\n\t\t\t    \t\t\t<td align=\"center\"><table style=\"border: 1px solid rgb(0, 0, 0);\" width=\"80\" cellpadding=\"0\" cellspacing=\"0\" height=\"10\">\n\t\t\t\t\t\t<tbody><tr>\n\n\t\t\t\t\t\t<td align=\"center\" onclick=\"javascript:fnCallLink('Warning',");
/* 1386 */                   out.print(warning);
/* 1387 */                   out.write(")\" title=\"Warning : ");
/* 1388 */                   out.print(warning);
/* 1389 */                   out.write(" \" class=\"barGraphBg\" style=\"background-color: #ea940e; cursor:pointer; background-position: 42px 50%;\" width=");
/* 1390 */                   out.print(warningper);
/* 1391 */                   out.write(" height=\"10\"> </td>\n\t\t\t\t\t\t<td align=\"center\" title=\"Warning : ");
/* 1392 */                   out.print(warning);
/* 1393 */                   out.write(" \" onclick=\"javascript:fnCallLink('Warning',");
/* 1394 */                   out.print(warning);
/* 1395 */                   out.write(")\"  style=\"background-color: #ffffff; cursor:pointer; background-position: 18px 50%;\" width=");
/* 1396 */                   out.print(100 - warningper);
/* 1397 */                   out.write(" height=\"10\"> </td>\n\t\t\t\t\t\t<td width=\"1%\"></td>\n\t\t\t\t\t\t\t        </tr>\n       </tbody></table></td>\n    \t\t\t</tr>\n\n    \t\t\t<tr><td clospan=\"5\" height=\"10\"></td></tr>\n\t\t\t    \t\t\t<tr><td width=\"1%\"></td>\n\t\t\t    \t\t\t\t<td>&nbsp;");
/* 1398 */                   out.print(FormatUtil.getString("Clear"));
/* 1399 */                   out.write("</td>\n\t\t\t    \t\t\t\t<td  align=\"center\"><a style=\"text-decoration:underline; color:#000;\" title=\"Clear : ");
/* 1400 */                   out.print(clear);
/* 1401 */                   out.write(" \" href=\"javascript:fnCallLink('Clear',");
/* 1402 */                   out.print(clear);
/* 1403 */                   out.write(41);
/* 1404 */                   out.write(34);
/* 1405 */                   out.write(62);
/* 1406 */                   out.print(clear);
/* 1407 */                   out.write("</a> </td>\n\t\t\t    \t\t\t\t<td align=\"center\"><table style=\"border: 1px solid rgb(0, 0, 0);\" width=\"80\" cellpadding=\"0\" cellspacing=\"0\" height=\"10\">\n\t\t\t\t\t\t\t         <tbody><tr>\n\n\t\t\t\t\t\t\t          <td align=\"center\" class=\"barGraphBg\" title=\"Clear : ");
/* 1408 */                   out.print(clear);
/* 1409 */                   out.write(" \" onclick=\"javascript:fnCallLink('Clear',");
/* 1410 */                   out.print(clear);
/* 1411 */                   out.write(")\" style=\"background-color: #38da43; cursor:pointer; background-position: 42px 50%;\" width=");
/* 1412 */                   out.print(clearper);
/* 1413 */                   out.write(" height=\"10\"> </td>\n\t\t\t\t\t\t\t          <td align=\"center\" title=\"Clear : ");
/* 1414 */                   out.print(clear);
/* 1415 */                   out.write(" \" onclick=\"javascript:fnCallLink('Clear',");
/* 1416 */                   out.print(clear);
/* 1417 */                   out.write(")\"   style=\"background-color: #ffffff; cursor:pointer; background-position: 18px 50%;\" width=");
/* 1418 */                   out.print(100 - clearper);
/* 1419 */                   out.write(" height=\"10\"> </td>\n\t\t\t\t\t\t\t\t\t<td width=\"1%\"></td>\n\t\t\t\t\t\t\t        </tr>\n\n\n\n\n\n       </tbody></table></td>\n    \t\t\t</tr>\n\n\n\n    \t\t<tr><td colspan=\"5\" height=\"10\"></td></tr>\n    \t\t</table>\n    \t</td>\n    \t<td class=\"status-right-bg\"></td>\n    </tr>\n\n</table>\n\n\n\n ");
/*      */                 }
/*      */                 
/*      */ 
/* 1423 */                 out.write(10);
/* 1424 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 1425 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1429 */             if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 1430 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */             }
/*      */             
/* 1433 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 1434 */             out.write("\n\n\n\t    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n  <tr>\n    <td height=\"21\" class=\"leftlinksheading\">");
/* 1435 */             out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/* 1436 */             out.write("</td>\n  </tr>\n\n\t    <tr>\n    <td class=\"leftlinkstd\">\n");
/*      */             
/* 1438 */             ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1439 */             _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 1440 */             _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/* 1441 */             int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 1442 */             if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */               for (;;) {
/* 1444 */                 out.write(10);
/*      */                 
/* 1446 */                 WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1447 */                 _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 1448 */                 _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                 
/* 1450 */                 _jspx_th_c_005fwhen_005f6.setTest("${(param.viewId !='Alerts')}");
/* 1451 */                 int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 1452 */                 if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                   for (;;) {
/* 1454 */                     out.write(10);
/*      */                     
/* 1456 */                     if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                     {
/* 1458 */                       out.write("\n <a href=\"/fault/AlarmView.do?displayName=All Alerts&viewId=Alerts\" class=\"new-left-links\">");
/* 1459 */                       out.print(FormatUtil.getString("am.monitortab.all.text"));
/* 1460 */                       out.write("</a>\n");
/*      */                     } else {
/* 1462 */                       out.write("\n <a href=\"/fault/AMAlarmView.do?displayName=All Alerts&monitor=MSSQL-DB-server&viewId=Alerts\" class=\"new-left-links\">");
/* 1463 */                       out.print(FormatUtil.getString("am.monitortab.all.text"));
/* 1464 */                       out.write("</a>\n");
/*      */                     }
/*      */                     
/*      */ 
/* 1468 */                     out.write(10);
/* 1469 */                     out.write(10);
/* 1470 */                     out.write(32);
/* 1471 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 1472 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1476 */                 if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 1477 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                 }
/*      */                 
/* 1480 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 1481 */                 out.write(10);
/* 1482 */                 out.write(32);
/*      */                 
/* 1484 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1485 */                 _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 1486 */                 _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 1487 */                 int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 1488 */                 if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                   for (;;) {
/* 1490 */                     out.write(10);
/* 1491 */                     out.write(32);
/* 1492 */                     out.print(FormatUtil.getString("am.monitortab.all.text"));
/* 1493 */                     out.write(10);
/* 1494 */                     out.write(32);
/* 1495 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 1496 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1500 */                 if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 1501 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                 }
/*      */                 
/* 1504 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 1505 */                 out.write(10);
/* 1506 */                 out.write(32);
/* 1507 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 1508 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1512 */             if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 1513 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */             }
/*      */             
/* 1516 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 1517 */             out.write("\n\n</td>\n        </tr>\n\n        <tr>\n    <td class=\"leftlinkstd\">\n\n    ");
/*      */             
/* 1519 */             ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1520 */             _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 1521 */             _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/* 1522 */             int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 1523 */             if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */               for (;;) {
/* 1525 */                 out.write("\n    ");
/*      */                 
/* 1527 */                 WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1528 */                 _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 1529 */                 _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                 
/* 1531 */                 _jspx_th_c_005fwhen_005f7.setTest("${param.viewId !='Alerts.2'}");
/* 1532 */                 int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 1533 */                 if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                   for (;;) {
/* 1535 */                     out.write(10);
/*      */                     
/* 1537 */                     if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                     {
/* 1539 */                       out.write("\n\t   <a href=\"/fault/AlarmView.do?displayName=Last one hour Alerts&viewId=Alerts.2\" class=\"new-left-links\">");
/* 1540 */                       out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 1541 */                       out.write("</a>\n");
/*      */                     } else {
/* 1543 */                       out.write("\n   \t   <a href=\"/fault/AMAlarmView.do?displayName=Last one hour Alerts&monitor=MSSQL-DB-server&viewId=Alerts.2\" class=\"new-left-links\">");
/* 1544 */                       out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 1545 */                       out.write("</a>\n");
/*      */                     }
/* 1547 */                     out.write(10);
/* 1548 */                     out.write(9);
/* 1549 */                     out.write(32);
/* 1550 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 1551 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1555 */                 if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 1556 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                 }
/*      */                 
/* 1559 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 1560 */                 out.write("\n     ");
/*      */                 
/* 1562 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1563 */                 _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 1564 */                 _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 1565 */                 int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 1566 */                 if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                   for (;;) {
/* 1568 */                     out.write("\n    \t");
/* 1569 */                     out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 1570 */                     out.write("\n     ");
/* 1571 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 1572 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1576 */                 if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 1577 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                 }
/*      */                 
/* 1580 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 1581 */                 out.write(10);
/* 1582 */                 out.write(32);
/* 1583 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 1584 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1588 */             if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 1589 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */             }
/*      */             
/* 1592 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 1593 */             out.write("\n\n\n    </td>\n        </tr>\n<tr>\n\n\n    <td class=\"leftlinkstd\">\n    ");
/*      */             
/* 1595 */             ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1596 */             _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 1597 */             _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/* 1598 */             int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 1599 */             if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */               for (;;) {
/* 1601 */                 out.write("\n    ");
/*      */                 
/* 1603 */                 WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1604 */                 _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 1605 */                 _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                 
/* 1607 */                 _jspx_th_c_005fwhen_005f8.setTest("${param.viewId !='Alerts.4'}");
/* 1608 */                 int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 1609 */                 if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                   for (;;) {
/* 1611 */                     out.write(10);
/* 1612 */                     out.write(10);
/*      */                     
/* 1614 */                     if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                     {
/* 1616 */                       out.write("\n             <a href=\"/fault/AlarmView.do?displayName=Last one day Alerts&viewId=Alerts.4\" class=\"new-left-links\">\n    ");
/* 1617 */                       out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.lastoneday.text"));
/* 1618 */                       out.write("</a>\n");
/*      */                     } else {
/* 1620 */                       out.write("\n             <a href=\"/fault/AMAlarmView.do?displayName=Last one day Alerts&monitor=MSSQL-DB-server&viewId=Alerts.4\" class=\"new-left-links\">\n    ");
/* 1621 */                       out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.lastoneday.text"));
/* 1622 */                       out.write("</a>\n");
/*      */                     }
/* 1624 */                     out.write("\n\n     ");
/* 1625 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 1626 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1630 */                 if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 1631 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                 }
/*      */                 
/* 1634 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 1635 */                 out.write("\n     ");
/*      */                 
/* 1637 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1638 */                 _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 1639 */                 _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 1640 */                 int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 1641 */                 if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                   for (;;) {
/* 1643 */                     out.write("\n    \t ");
/* 1644 */                     out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.lastoneday.text"));
/* 1645 */                     out.write("\n     ");
/* 1646 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 1647 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1651 */                 if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 1652 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                 }
/*      */                 
/* 1655 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 1656 */                 out.write(10);
/* 1657 */                 out.write(32);
/* 1658 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 1659 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1663 */             if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 1664 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */             }
/*      */             
/* 1667 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 1668 */             out.write("\n\n</td>\n        </tr>\n</table>\n\n\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n\t<td height=\"21\" class=\"leftlinksheading\">");
/* 1669 */             out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.trapheading.text"));
/* 1670 */             out.write("</td>\n</tr>\n<tr>\n\t");
/*      */             
/* 1672 */             NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1673 */             _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 1674 */             _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */             
/* 1676 */             _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 1677 */             int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 1678 */             if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */               for (;;) {
/* 1680 */                 out.write("\n\t<td class=\"leftlinkstd\">\n\t");
/*      */                 
/* 1682 */                 ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1683 */                 _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 1684 */                 _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_logic_005fnotPresent_005f0);
/* 1685 */                 int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 1686 */                 if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                   for (;;) {
/* 1688 */                     out.write(10);
/* 1689 */                     out.write(9);
/*      */                     
/* 1691 */                     WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1692 */                     _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 1693 */                     _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                     
/* 1695 */                     _jspx_th_c_005fwhen_005f9.setTest("${param.monitor !='Trap'}");
/* 1696 */                     int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 1697 */                     if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                       for (;;) {
/* 1699 */                         out.write("\n\t<a href=\"/fault/AMAlarmView.do?displayName=Traps%20Received&monitor=Trap\" class=\"new-left-links\">");
/* 1700 */                         out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.configuretrap.text"));
/* 1701 */                         out.write("</a>\n\t");
/* 1702 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 1703 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1707 */                     if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 1708 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                     }
/*      */                     
/* 1711 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 1712 */                     out.write(10);
/* 1713 */                     out.write(9);
/*      */                     
/* 1715 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1716 */                     _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 1717 */                     _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 1718 */                     int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 1719 */                     if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                       for (;;) {
/* 1721 */                         out.write(10);
/* 1722 */                         out.write(9);
/* 1723 */                         out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.configuretrap.text"));
/* 1724 */                         out.write(10);
/* 1725 */                         out.write(9);
/* 1726 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 1727 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1731 */                     if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 1732 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                     }
/*      */                     
/* 1735 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 1736 */                     out.write(10);
/* 1737 */                     out.write(9);
/* 1738 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 1739 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1743 */                 if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 1744 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                 }
/*      */                 
/* 1747 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 1748 */                 out.write("\n\t</td>\n\t");
/* 1749 */                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 1750 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1754 */             if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 1755 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */             }
/*      */             
/* 1758 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 1759 */             out.write(10);
/* 1760 */             out.write(9);
/*      */             
/* 1762 */             PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1763 */             _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1764 */             _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */             
/* 1766 */             _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 1767 */             int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1768 */             if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */               for (;;) {
/* 1770 */                 out.write("\n\n\t<td class=\"leftlinkstd\">\n\n<a href=\"#\" class=\"disabledlink\">\t");
/* 1771 */                 out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.configuretrap.text"));
/* 1772 */                 out.write("</a>\n</td>\n");
/* 1773 */                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1774 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1778 */             if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1779 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */             }
/*      */             
/* 1782 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1783 */             out.write("\n</tr>\n<tr>\n\t");
/*      */             
/* 1785 */             NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 1786 */             _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 1787 */             _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */             
/* 1789 */             _jspx_th_logic_005fnotPresent_005f1.setRole("OPERATOR");
/* 1790 */             int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 1791 */             if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */               for (;;) {
/* 1793 */                 out.write("\n\n\t<td class=\"leftlinkstd\">\n\t");
/*      */                 
/* 1795 */                 ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1796 */                 _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 1797 */                 _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_logic_005fnotPresent_005f1);
/* 1798 */                 int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 1799 */                 if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                   for (;;) {
/* 1801 */                     out.write(10);
/* 1802 */                     out.write(9);
/*      */                     
/* 1804 */                     WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1805 */                     _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 1806 */                     _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                     
/* 1808 */                     _jspx_th_c_005fwhen_005f10.setTest("${param.viewId !='Trap'}");
/* 1809 */                     int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 1810 */                     if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                       for (;;) {
/* 1812 */                         out.write("\n\t<a href=\"/fault/AMAlarmView.do?viewId=Trap\" class=\"new-left-links\">");
/* 1813 */                         out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.unsolicitatedtrap.text"));
/* 1814 */                         out.write("</a>\n\t");
/* 1815 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 1816 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1820 */                     if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 1821 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                     }
/*      */                     
/* 1824 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 1825 */                     out.write(10);
/* 1826 */                     out.write(9);
/*      */                     
/* 1828 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1829 */                     _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 1830 */                     _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 1831 */                     int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 1832 */                     if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                       for (;;) {
/* 1834 */                         out.write(10);
/* 1835 */                         out.write(9);
/* 1836 */                         out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.unsolicitatedtrap.text"));
/* 1837 */                         out.write(10);
/* 1838 */                         out.write(9);
/* 1839 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 1840 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1844 */                     if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 1845 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                     }
/*      */                     
/* 1848 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 1849 */                     out.write(10);
/* 1850 */                     out.write(9);
/* 1851 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 1852 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1856 */                 if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 1857 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                 }
/*      */                 
/* 1860 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 1861 */                 out.write("\n\t</td>\n\t");
/* 1862 */                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 1863 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1867 */             if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 1868 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */             }
/*      */             
/* 1871 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 1872 */             out.write("\n        ");
/*      */             
/* 1874 */             PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1875 */             _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 1876 */             _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */             
/* 1878 */             _jspx_th_logic_005fpresent_005f1.setRole("OPERATOR");
/* 1879 */             int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 1880 */             if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */               for (;;) {
/* 1882 */                 out.write("\n\n        <td class=\"leftlinkstd\">\n\n\t<a href=\"#\" class=\"disabledlink\">        ");
/* 1883 */                 out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.unsolicitatedtrap.text"));
/* 1884 */                 out.write("</a>\n</td>\n");
/* 1885 */                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1886 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/* 1890 */             if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1891 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */             }
/*      */             
/* 1894 */             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1895 */             out.write("\n\n</tr>\n</table>\n\n ");
/*      */             
/* 1897 */             if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */             {
/*      */ 
/* 1900 */               out.write("\n\n       <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n<tr>\n\t<td height=\"21\" class=\"leftlinksheading\">");
/* 1901 */               out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.jmxnotification.text"));
/* 1902 */               out.write("</td>\n</tr>\n<tr>\n\t<td class=\"leftlinkstd\">\n\t");
/*      */               
/* 1904 */               ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1905 */               _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 1906 */               _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/* 1907 */               int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 1908 */               if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                 for (;;) {
/* 1910 */                   out.write(10);
/* 1911 */                   out.write(9);
/*      */                   
/* 1913 */                   WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1914 */                   _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 1915 */                   _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                   
/* 1917 */                   _jspx_th_c_005fwhen_005f11.setTest("${param.monitor !='JMXNotification'}");
/* 1918 */                   int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 1919 */                   if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                     for (;;) {
/* 1921 */                       out.write("\n\t<a href=\"/fault/AMAlarmView.do?displayName=Alerts due to JMX Notifications&monitor=JMXNotification\" class=\"new-left-links\">");
/* 1922 */                       out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.jmxnotification.text"));
/* 1923 */                       out.write("</a>\n\t");
/* 1924 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 1925 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1929 */                   if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 1930 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                   }
/*      */                   
/* 1933 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 1934 */                   out.write(10);
/* 1935 */                   out.write(9);
/*      */                   
/* 1937 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1938 */                   _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 1939 */                   _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 1940 */                   int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 1941 */                   if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                     for (;;) {
/* 1943 */                       out.write(10);
/* 1944 */                       out.write(9);
/* 1945 */                       out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.jmxnotification.text"));
/* 1946 */                       out.write(10);
/* 1947 */                       out.write(9);
/* 1948 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 1949 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1953 */                   if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 1954 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                   }
/*      */                   
/* 1957 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 1958 */                   out.write(10);
/* 1959 */                   out.write(9);
/* 1960 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 1961 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1965 */               if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 1966 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */               }
/*      */               
/* 1969 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 1970 */               out.write("\n\t</td>\n</tr>\n</table>\n ");
/*      */             }
/*      */             
/*      */ 
/* 1974 */             out.write(10);
/* 1975 */             out.write(10);
/* 1976 */             out.write(32);
/*      */             
/* 1978 */             if (!com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer())
/*      */             {
/* 1980 */               out.write(10);
/* 1981 */               out.write(32);
/* 1982 */               out.write(32);
/*      */               
/* 1984 */               PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1985 */               _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 1986 */               _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */               
/* 1988 */               _jspx_th_logic_005fpresent_005f2.setRole("ADMIN");
/* 1989 */               int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 1990 */               if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                 for (;;) {
/* 1992 */                   out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n<tr>\n\t<td height=\"21\" class=\"leftlinksheading\">");
/* 1993 */                   out.print(FormatUtil.getString("am.webclient.alerttab.leftlink.configure.text"));
/* 1994 */                   out.write("</td>\n</tr>\n<tr>\n\t<td class=\"leftlinkstd\">\n\t\t<a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true\" class=\"new-left-links\">");
/* 1995 */                   out.print(FormatUtil.getString("am.webclient.header.title.configurealert.text"));
/* 1996 */                   out.write("</a>\n\t</td>\n</tr>\n</table>\n ");
/* 1997 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 1998 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2002 */               if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2003 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */               }
/*      */               
/* 2006 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2007 */               out.write(10);
/*      */             }
/* 2009 */             out.write(10);
/* 2010 */             out.write("\n\n\n <br>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n  <tr><td width=\"80%\" class=\"leftlinksquicknote\">");
/* 2011 */             out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 2012 */             out.write("</td>\n    <td width=\"20%\" class=\"leftlinksheading\"><img src=\"/images/");
/* 2013 */             if (_jspx_meth_c_005fout_005f2(_jspx_th_logic_005fnotEmpty_005f1, _jspx_page_context))
/*      */               return;
/* 2015 */             out.write("/img_quicknote.gif\"      hspace=\"5\"></td>\n  </tr>\n\t  <tr>\n\n    <td height=\"98\" colspan=\"2\"  class=\"quicknote\">");
/* 2016 */             out.print(FormatUtil.getString("am.webclient.actions.thresholdprofiles.quicknote1.text"));
/* 2017 */             out.write(" <br>\n      <br> ");
/* 2018 */             out.print(FormatUtil.getString("am.webclient.actions.thresholdprofiles.quicknote2.text"));
/* 2019 */             out.write(" <br> </td>\n        </tr>\n</table>\n");
/* 2020 */             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2021 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2025 */         if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2026 */           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/*      */         }
/*      */         else {
/* 2029 */           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2030 */           out.write(10);
/* 2031 */           out.write(10);
/*      */         }
/* 2033 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2034 */         out = _jspx_out;
/* 2035 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2036 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2037 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2040 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2046 */     PageContext pageContext = _jspx_page_context;
/* 2047 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2049 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2050 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2051 */     _jspx_th_c_005fif_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 2053 */     _jspx_th_c_005fif_005f0.setTest("${!empty ADMIN}");
/* 2054 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2055 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 2057 */         out.write(10);
/* 2058 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 2059 */           return true;
/* 2060 */         out.write(10);
/* 2061 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2062 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2066 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2067 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2068 */       return true;
/*      */     }
/* 2070 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2071 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2076 */     PageContext pageContext = _jspx_page_context;
/* 2077 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2079 */     org.apache.taglibs.standard.tag.el.core.SetTag _jspx_th_c_005fset_005f0 = (org.apache.taglibs.standard.tag.el.core.SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.el.core.SetTag.class);
/* 2080 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2081 */     _jspx_th_c_005fset_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 2083 */     _jspx_th_c_005fset_005f0.setVar("linkclass");
/*      */     
/* 2085 */     _jspx_th_c_005fset_005f0.setValue("new-left-links");
/* 2086 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2087 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2088 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2089 */       return true;
/*      */     }
/* 2091 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2092 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2097 */     PageContext pageContext = _jspx_page_context;
/* 2098 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2100 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2101 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2102 */     _jspx_th_c_005fif_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 2104 */     _jspx_th_c_005fif_005f1.setTest("${empty ADMIN}");
/* 2105 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2106 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2108 */         out.write(10);
/* 2109 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 2110 */           return true;
/* 2111 */         out.write(10);
/* 2112 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2113 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2117 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2118 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2119 */       return true;
/*      */     }
/* 2121 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2127 */     PageContext pageContext = _jspx_page_context;
/* 2128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2130 */     org.apache.taglibs.standard.tag.el.core.SetTag _jspx_th_c_005fset_005f1 = (org.apache.taglibs.standard.tag.el.core.SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.el.core.SetTag.class);
/* 2131 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2132 */     _jspx_th_c_005fset_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 2134 */     _jspx_th_c_005fset_005f1.setVar("linkclass");
/*      */     
/* 2136 */     _jspx_th_c_005fset_005f1.setValue("disablelinks");
/* 2137 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2138 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2139 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 2140 */       return true;
/*      */     }
/* 2142 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 2143 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2148 */     PageContext pageContext = _jspx_page_context;
/* 2149 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2151 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2152 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2153 */     _jspx_th_c_005fout_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 2155 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 2157 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 2158 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2159 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2160 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2161 */       return true;
/*      */     }
/* 2163 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fequal_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2169 */     PageContext pageContext = _jspx_page_context;
/* 2170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2172 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 2173 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2174 */     _jspx_th_c_005fout_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fequal_005f2);
/*      */     
/* 2176 */     _jspx_th_c_005fout_005f1.setValue("${linkclass}");
/* 2177 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2178 */     if (_jspx_eval_c_005fout_005f1 != 0) {
/* 2179 */       if (_jspx_eval_c_005fout_005f1 != 1) {
/* 2180 */         out = _jspx_page_context.pushBody();
/* 2181 */         _jspx_th_c_005fout_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2182 */         _jspx_th_c_005fout_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2185 */         out.write("new-left-links");
/* 2186 */         int evalDoAfterBody = _jspx_th_c_005fout_005f1.doAfterBody();
/* 2187 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2190 */       if (_jspx_eval_c_005fout_005f1 != 1) {
/* 2191 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 2194 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2195 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f1);
/* 2196 */       return true;
/*      */     }
/* 2198 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f1);
/* 2199 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(javax.servlet.jsp.tagext.JspTag _jspx_th_logic_005fnotEmpty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2204 */     PageContext pageContext = _jspx_page_context;
/* 2205 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2207 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 2208 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 2209 */     _jspx_th_c_005fout_005f2.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f1);
/*      */     
/* 2211 */     _jspx_th_c_005fout_005f2.setValue("${selectedskin}");
/*      */     
/* 2213 */     _jspx_th_c_005fout_005f2.setDefault("${initParam.defaultSkin}");
/* 2214 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 2215 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 2216 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2217 */       return true;
/*      */     }
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 2220 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ActionLeftLinks_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */