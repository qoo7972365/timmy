/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class FlashViewEdit_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   19 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */   private static java.util.Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   42 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   46 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   47 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   48 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   49 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   50 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   51 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   52 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   53 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   54 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   55 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   56 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   60 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   64 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   65 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   66 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   67 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   68 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   69 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*   70 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*   71 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*   72 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/*   73 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*   74 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*   75 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   76 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   83 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   86 */     JspWriter out = null;
/*   87 */     Object page = this;
/*   88 */     JspWriter _jspx_out = null;
/*   89 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*   93 */       response.setContentType("text/html");
/*   94 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*   96 */       _jspx_page_context = pageContext;
/*   97 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*   98 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*   99 */       session = pageContext.getSession();
/*  100 */       out = pageContext.getOut();
/*  101 */       _jspx_out = out;
/*      */       
/*  103 */       out.write("<!--$Id$-->\n\n\n\n");
/*  104 */       response.setContentType("text/html;charset=UTF-8");
/*  105 */       out.write(10);
/*      */       
/*      */       try
/*      */       {
/*  109 */         String haid = "-1";
/*  110 */         String viewid = "-1";
/*  111 */         String customizetab = "false";
/*  112 */         if (request.getParameter("haid") != null) {
/*  113 */           haid = request.getParameter("haid");
/*      */         }
/*  115 */         if (request.getParameter("viewid") != null) {
/*  116 */           viewid = request.getParameter("viewid");
/*      */         }
/*  118 */         if (request.getParameter("customizetab") != null)
/*      */         {
/*  120 */           customizetab = request.getParameter("customizetab");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  125 */         out.write("\n\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  126 */         if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */           return;
/*  128 */         out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n\n");
/*  129 */         com.adventnet.appmanager.struts.form.FlashForm FlashForm = null;
/*  130 */         FlashForm = (com.adventnet.appmanager.struts.form.FlashForm)_jspx_page_context.getAttribute("FlashForm", 2);
/*  131 */         if (FlashForm == null) {
/*  132 */           FlashForm = new com.adventnet.appmanager.struts.form.FlashForm();
/*  133 */           _jspx_page_context.setAttribute("FlashForm", FlashForm, 2);
/*      */         }
/*  135 */         out.write(10);
/*  136 */         out.write(10);
/*      */         
/*  138 */         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  139 */         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  140 */         _jspx_th_c_005fif_005f0.setParent(null);
/*      */         
/*  142 */         _jspx_th_c_005fif_005f0.setTest("${not empty param.configurationsaved}");
/*  143 */         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  144 */         if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */           for (;;) {
/*  146 */             out.write("\n<script>\n\n ");
/*      */             
/*  148 */             ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  149 */             _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  150 */             _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f0);
/*  151 */             int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  152 */             if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */               for (;;) {
/*  154 */                 out.write(10);
/*  155 */                 out.write(32);
/*      */                 
/*  157 */                 WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  158 */                 _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  159 */                 _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                 
/*  161 */                 _jspx_th_c_005fwhen_005f0.setTest("${ FlashForm.popUp == true}");
/*  162 */                 int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  163 */                 if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                   for (;;) {
/*  165 */                     out.write("\n window.opener.location.href=\"/GraphicalView.do?method=popUp&haid=");
/*  166 */                     out.print(haid);
/*  167 */                     out.write("&viewid=");
/*  168 */                     out.print(viewid);
/*  169 */                     out.write("&isPopUp=true\";\n ");
/*  170 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  171 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  175 */                 if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  176 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                 }
/*      */                 
/*  179 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  180 */                 out.write(10);
/*  181 */                 out.write(32);
/*      */                 
/*  183 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  184 */                 _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  185 */                 _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  186 */                 int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  187 */                 if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                   for (;;) {
/*  189 */                     out.write(10);
/*  190 */                     out.write(9);
/*  191 */                     out.write(32);
/*      */                     
/*  193 */                     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  194 */                     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  195 */                     _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fotherwise_005f0);
/*  196 */                     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  197 */                     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                       for (;;) {
/*  199 */                         out.write(10);
/*  200 */                         out.write(9);
/*  201 */                         out.write(32);
/*      */                         
/*  203 */                         WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  204 */                         _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  205 */                         _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                         
/*  207 */                         _jspx_th_c_005fwhen_005f1.setTest("${FlashForm.fromWhere==\"hometab\"}");
/*  208 */                         int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  209 */                         if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                           for (;;) {
/*  211 */                             out.write("\n\t  window.opener.location.href=\"/GraphicalView.do?method=popUp&fromhomepage=true&haid=");
/*  212 */                             out.print(haid);
/*  213 */                             out.write("&viewid=");
/*  214 */                             out.print(viewid);
/*  215 */                             out.write("\";\n\t ");
/*  216 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  217 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  221 */                         if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  222 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                         }
/*      */                         
/*  225 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  226 */                         out.write(10);
/*  227 */                         out.write(9);
/*  228 */                         out.write(32);
/*      */                         
/*  230 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  231 */                         _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  232 */                         _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  233 */                         int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  234 */                         if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                           for (;;) {
/*  236 */                             out.write("\n\t     ");
/*      */                             
/*  238 */                             ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  239 */                             _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  240 */                             _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fotherwise_005f1);
/*  241 */                             int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  242 */                             if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                               for (;;) {
/*  244 */                                 out.write("\n\t     \t");
/*      */                                 
/*  246 */                                 WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  247 */                                 _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  248 */                                 _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                 
/*  250 */                                 _jspx_th_c_005fwhen_005f2.setTest("${not empty param.customizetab && param.customizetab==true}");
/*  251 */                                 int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  252 */                                 if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                   for (;;) {
/*  254 */                                     out.write("\n\t     \t\twindow.opener.location.href=\"/GraphicalView.do?method=popUp&customizetab=true&haid=");
/*  255 */                                     out.print(haid);
/*  256 */                                     out.write("&viewid=");
/*  257 */                                     out.print(viewid);
/*  258 */                                     out.write("\";\n\t     \t");
/*  259 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  260 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  264 */                                 if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  265 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                 }
/*      */                                 
/*  268 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  269 */                                 out.write(" \n\t     \t");
/*      */                                 
/*  271 */                                 OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  272 */                                 _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  273 */                                 _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*  274 */                                 int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  275 */                                 if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                   for (;;) {
/*  277 */                                     out.write("\n\t     \t\t");
/*      */                                     
/*  279 */                                     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  280 */                                     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/*  281 */                                     _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fotherwise_005f2);
/*  282 */                                     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/*  283 */                                     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                                       for (;;) {
/*  285 */                                         out.write("\n\t\t     \t\t");
/*      */                                         
/*  287 */                                         WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  288 */                                         _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  289 */                                         _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                         
/*  291 */                                         _jspx_th_c_005fwhen_005f3.setTest("${not empty param.createnewview && param.createnewview==true}");
/*  292 */                                         int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  293 */                                         if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                           for (;;) {
/*  295 */                                             out.write("\n\t\t        \t\tvar popoutwindow=window.open('/GraphicalView.do?method=popUp&haid=0&isPopUp=true&viewid=");
/*  296 */                                             out.print(viewid);
/*  297 */                                             out.write("','FlashView','scrollbars=1,resizable=1,width='+(screen.availWidth-50)+',height='+(screen.availHeight-50)+',left=5,top=5,screenX=250,screenY=25');\n\t\t        \t\tpopoutwindow.focus();\n\t\t      \t\t");
/*  298 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  299 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/*  303 */                                         if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  304 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                         }
/*      */                                         
/*  307 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  308 */                                         out.write("\n\t\t      \t\t");
/*      */                                         
/*  310 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  311 */                                         _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/*  312 */                                         _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*  313 */                                         int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/*  314 */                                         if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                           for (;;) {
/*  316 */                                             out.write("\n\t\t       \t\t\twindow.opener.location.href=\"/showapplication.do?haid=");
/*  317 */                                             out.print(haid);
/*  318 */                                             out.write("&method=showApplication&selectM=flashview&viewid=");
/*  319 */                                             out.print(viewid);
/*  320 */                                             out.write("\";\n\t\t      \t\t");
/*  321 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/*  322 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/*  326 */                                         if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/*  327 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                                         }
/*      */                                         
/*  330 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/*  331 */                                         out.write("\n\t\t      \t");
/*  332 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/*  333 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/*  337 */                                     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/*  338 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                                     }
/*      */                                     
/*  341 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*  342 */                                     out.write("\t\n\t     \t");
/*  343 */                                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  344 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/*  348 */                                 if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  349 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                                 }
/*      */                                 
/*  352 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  353 */                                 out.write("\n\t     ");
/*  354 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  355 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/*  359 */                             if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  360 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                             }
/*      */                             
/*  363 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  364 */                             out.write(10);
/*  365 */                             out.write(9);
/*  366 */                             out.write(32);
/*  367 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  368 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/*  372 */                         if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  373 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                         }
/*      */                         
/*  376 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  377 */                         out.write(10);
/*  378 */                         out.write(9);
/*  379 */                         out.write(32);
/*  380 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  381 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  385 */                     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  386 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                     }
/*      */                     
/*  389 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  390 */                     out.write(10);
/*  391 */                     out.write(32);
/*  392 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  393 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  397 */                 if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  398 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                 }
/*      */                 
/*  401 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  402 */                 out.write(10);
/*  403 */                 out.write(32);
/*  404 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  405 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  409 */             if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  410 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */             }
/*      */             
/*  413 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  414 */             out.write("\n  window.close();\n</script>\n\n");
/*  415 */             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  416 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  420 */         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  421 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */         }
/*      */         
/*  424 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  425 */         out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/tooltip.js\"></SCRIPT>\n<script>\n\nfunction showDisplay()\n{\n\ndocument.getElementById(\"loadingg\").style.display='none';\ndocument.getElementById(\"color\").style.display='inline';\ndocument.getElementById(\"status\").style.display='none';\ndocument.getElementById(\"description\").style.display='none';\ndocument.getElementById(\"asscoiatemgs\").style.display='none';\n\n}\nfunction statusUpdate()\n{\n\ndocument.getElementById(\"loadingg\").style.display='none';\ndocument.getElementById(\"status\").style.display='inline';\ndocument.getElementById(\"color\").style.display='none';\ndocument.getElementById(\"description\").style.display='none';\ndocument.getElementById(\"asscoiatemgs\").style.display='none';\n\n}\n\nfunction showDescription()\n{\ndocument.getElementById(\"loadingg\").style.display='none';\ndocument.getElementById(\"description\").style.display='inline';\ndocument.getElementById(\"color\").style.display='none';\ndocument.getElementById(\"status\").style.display='none';\n");
/*  426 */         out.write("document.getElementById(\"asscoiatemgs\").style.display='none';\n}\n\nfunction associateMgs()\n{\ndocument.getElementById(\"loadingg\").style.display='none';\ndocument.getElementById(\"description\").style.display='none';\ndocument.getElementById(\"color\").style.display='none';\ndocument.getElementById(\"status\").style.display='none';\ndocument.getElementById(\"asscoiatemgs\").style.display='inline';\n}\n\nfunction disableOthers()\n{\nif(document.FlashForm.showTopLevelMgs.checked==true)\n{\n document.FlashForm.bgColor.disabled=true;\n document.FlashForm.lineColor.disabled=true;\n document.FlashForm.labelColor.disabled=true;\n document.FlashForm.linethickness.disabled=true;\n document.FlashForm.showLabel.disabled=true;\n document.FlashForm.lineTransparency.disabled=true;\n document.FlashForm.showCriticalMonitors.disabled=true;\n document.FlashForm.showOnlySubgroups.disabled=true;\n showRow(\"noOfColumns\");\n}\nelse\n{\n document.FlashForm.bgColor.disabled=false;\n document.FlashForm.lineColor.disabled=false;\n document.FlashForm.labelColor.disabled=false;\n");
/*  427 */         out.write(" document.FlashForm.linethickness.disabled=false;\n document.FlashForm.showLabel.disabled=false;\n document.FlashForm.lineTransparency.disabled=false;\n document.FlashForm.showCriticalMonitors.disabled=false;\n document.FlashForm.showOnlySubgroups.disabled=false;\n hideRow(\"noOfColumns\");\n\n}\n}\nfunction submitForm()\n{\n\n      ");
/*      */         
/*  429 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  430 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  431 */         _jspx_th_c_005fif_005f1.setParent(null);
/*      */         
/*  433 */         _jspx_th_c_005fif_005f1.setTest("${ FlashForm.fromMonitorTab == true}");
/*  434 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  435 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */           for (;;) {
/*  437 */             out.write("\n            if(!checkforOneSelected(document.FlashForm,\"monitorGroups\"))\n            {\n               SetTabStyle('am.webclient.flashview.monitorgroups.text',tabsTableID);//No I18N\n               associateMgs();\n               alert('");
/*  438 */             out.print(FormatUtil.getString("am.webclient.flashview.nomgselected.empty.text"));
/*  439 */             out.write("');\n               return;\n            }\n      ");
/*  440 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  441 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  445 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  446 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */         }
/*      */         
/*  449 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  450 */         out.write("\n      if(trimAll(document.FlashForm.displayName.value)=='')\n      {\n      SetTabStyle('am.webclient.flashview.viewdescription.text',tabsTableID);//No I18N\n      showDescription();\n      alert(\"");
/*  451 */         out.print(FormatUtil.getString("am.webclient.flashview.displayname.empty.text"));
/*  452 */         out.write("\");//No I18N\n      return;\n      }\n      \n/*Removed script code:Start*/\n\n /*Removed script code:End*/\n      \n\n\n\ndocument.FlashForm.submit();\n}\n\nfunction validateColor(color){\nvalidCharecters='0123456789ABCDEF';\nif(color=='')\n{\nreturn false;\n}\nif(!(color.charAt(0)=='#'))\n{\nreturn false;\n}\ncololength=color.length;\nif(cololength!=7)\n{\nreturn false;\n}\ncolor=color.toUpperCase();\nfor (i=1;i<cololength;i++)\n   {\n       if(validCharecters.indexOf(color.charAt(i))<0)\n       {\n       return false;\n       }\n    }\nreturn true;\n}\n\n\nfunction selectall(e){\nToggleAll(e,document.FlashForm,'monitorGroups');\n}\n\n\n\nfunction isPositiveDecimal(str)\n{\n\tvar temp  = parseFloat(str);\n\tif(temp<=0)\n\t{\n\treturn false;\n\t}\n\tif ( isNaN(temp) ) {\n\t\treturn false;\n\t}\n\telse\n\t{\n\treturn true;\n\t}\n}\n\n\n</script>\n<div id=\"dhtmltooltip\"></div>\n<div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"55\" class=\"darkheaderbg marg-btm\">\n    <tr>\n      <td class=\"headingboldwhite\"  height=\"22\">\n");
/*  453 */         out.write("\t      ");
/*      */         
/*  455 */         ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  456 */         _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/*  457 */         _jspx_th_c_005fchoose_005f4.setParent(null);
/*  458 */         int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/*  459 */         if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */           for (;;) {
/*  461 */             out.write("\n\t\t ");
/*      */             
/*  463 */             WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  464 */             _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  465 */             _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */             
/*  467 */             _jspx_th_c_005fwhen_005f4.setTest("${not empty param.createnewview}");
/*  468 */             int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  469 */             if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */               for (;;) {
/*  471 */                 out.write("\n\t\t\t");
/*  472 */                 out.print(FormatUtil.getString("am.webclient.flashview.createview.text"));
/*  473 */                 out.write("\n\t\t ");
/*  474 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  475 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  479 */             if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  480 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */             }
/*      */             
/*  483 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  484 */             out.write("\n\t\t ");
/*      */             
/*  486 */             OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  487 */             _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/*  488 */             _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*  489 */             int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/*  490 */             if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */               for (;;) {
/*  492 */                 out.write("\n\t\t\t");
/*  493 */                 out.print(FormatUtil.getString("am.webclient.flashview.editview.text"));
/*  494 */                 out.write("\n\t\t ");
/*  495 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/*  496 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  500 */             if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/*  501 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */             }
/*      */             
/*  504 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/*  505 */             out.write("\n\t      ");
/*  506 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/*  507 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  511 */         if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/*  512 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */         }
/*      */         
/*  515 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*  516 */         out.write("\n\t      ");
/*      */         
/*  518 */         ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  519 */         _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/*  520 */         _jspx_th_c_005fchoose_005f5.setParent(null);
/*  521 */         int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/*  522 */         if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */           for (;;) {
/*  524 */             out.write("\n\t      \t ");
/*      */             
/*  526 */             WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  527 */             _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/*  528 */             _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */             
/*  530 */             _jspx_th_c_005fwhen_005f5.setTest("${ FlashForm.fromMonitorTab == true}");
/*  531 */             int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/*  532 */             if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */               for (;;) {
/*  534 */                 out.write("\n\t         \t- [");
/*  535 */                 out.print(FormatUtil.getString("am.webclient.flashview.viewtype.text"));
/*  536 */                 out.write(32);
/*  537 */                 out.write(58);
/*  538 */                 out.write(32);
/*  539 */                 out.print(FormatUtil.getString("am.webclient.flashview.customview.text"));
/*  540 */                 out.write("]\n\t         ");
/*  541 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/*  542 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  546 */             if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/*  547 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */             }
/*      */             
/*  550 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*  551 */             out.write("\n\t         ");
/*      */             
/*  553 */             OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  554 */             _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/*  555 */             _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*  556 */             int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/*  557 */             if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */               for (;;) {
/*  559 */                 out.write("\n\t                - [");
/*  560 */                 out.print(FormatUtil.getString("am.webclient.flashview.viewtype.text"));
/*  561 */                 out.write(32);
/*  562 */                 out.write(58);
/*  563 */                 out.write(32);
/*  564 */                 out.print(FormatUtil.getString("am.webclient.flashview.mgview.text"));
/*  565 */                 out.write("]\n\t         ");
/*  566 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/*  567 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  571 */             if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/*  572 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */             }
/*      */             
/*  575 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/*  576 */             out.write("\n\t      ");
/*  577 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/*  578 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  582 */         if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/*  583 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */         }
/*      */         
/*  586 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*  587 */         out.write("\n\n       </td>\n    </tr>\n</table>\n\n");
/*      */         
/*  589 */         org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/*  590 */         _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  591 */         _jspx_th_html_005fform_005f0.setParent(null);
/*      */         
/*  593 */         _jspx_th_html_005fform_005f0.setAction("/GraphicalView.do");
/*  594 */         int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  595 */         if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */           for (;;) {
/*  597 */             out.write("\n<input type=\"hidden\" name=\"method\" value=\"saveEditView\"/>\n<input type=\"hidden\" name=\"haid\" value=\"");
/*  598 */             out.print(haid);
/*  599 */             out.write("\"/>\n<input type=\"hidden\" name=\"viewid\" value=\"");
/*  600 */             out.print(viewid);
/*  601 */             out.write("\"/>\n<input type=\"hidden\" name=\"customizetab\" value=\"");
/*  602 */             out.print(customizetab);
/*  603 */             out.write("\"/>\n");
/*  604 */             if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  606 */             out.write(10);
/*  607 */             if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  609 */             out.write(10);
/*  610 */             if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  612 */             out.write(10);
/*  613 */             out.write(32);
/*  614 */             if (_jspx_meth_c_005fif_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  616 */             out.write(10);
/*  617 */             if (_jspx_meth_c_005fchoose_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  619 */             out.write("\n<div id=\"description\" style=\"display:inline\">\n<table class=\"lrborder\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"99%\">\n <tr class=\"bodytext\">\n      <td width=\"20%\" class=\"bodytext label-align\"  align=\"left\">");
/*  620 */             out.print(FormatUtil.getString("Display Name"));
/*  621 */             out.write(" : </td>\n      <td width=\"80%\" align=\"left\">");
/*  622 */             if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  624 */             out.write(" </td>\n </tr>\n\n <!--  removed Elements -->\n \n  <!--  removed Elements -->\n \n\n\n </table>\n <table class=\"lrbborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\">\n <tr>\n \t<td class=\"tablebottom\" width=\"20%\"></td>\n     <td class=\"tablebottom\" height=\"28\">\n     <input name=\"button1\" class=\"buttons\" value='");
/*  625 */             out.print(FormatUtil.getString("am.webclient.flashview.saveconfig.text"));
/*  626 */             out.write("' type=\"button\" onclick=\"return submitForm()\">\n     </td>\n </tr>\n</table>\n\n</div>\n\n\n<div id=\"color\" style=\"display:none\">\n\n<table class=\"lrborder\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\" width=\"99%\">\n\n\n\n<tr class=\"bodytext\" >\n      <td width=\"20%\" class=\"bodytext label-align\" align=\"left\">");
/*  627 */             out.print(FormatUtil.getString("am.webclient.flashview.bgcolor.text"));
/*  628 */             out.write(" :</td>\n      <td width=\"80%\" align=\"left\">");
/*  629 */             if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  631 */             out.write(" </td>\n</tr>\n<tr class=\"bodytext\">\n      <td width=\"20%\" class=\"bodytext label-align\" align=\"left\">");
/*  632 */             out.print(FormatUtil.getString("am.webclient.flashview.linecolor.text"));
/*  633 */             out.write(" :</td>\n      <td width=\"80%\" align=\"left\">");
/*  634 */             if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  636 */             out.write(" </td>\n</tr>\n<tr class=\"bodytext\" >\n      <td width=\"20%\" class=\"bodytext label-align\" align=\"left\">");
/*  637 */             out.print(FormatUtil.getString("am.webclient.flashview.labelcolor.text"));
/*  638 */             out.write(" :</td>\n      <td width=\"80%\" align=\"left\">");
/*  639 */             if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  641 */             out.write("  </td>\n</tr>\n<tr class=\"bodytext\">\n      <td width=\"20%\" class=\"bodytext label-align\" align=\"left\">");
/*  642 */             out.print(FormatUtil.getString("am.webclient.flashview.linethickness.text"));
/*  643 */             out.write(" :</td>\n      <td width=\"80%\" align=\"left\">");
/*  644 */             if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  646 */             out.write(32);
/*  647 */             out.print(FormatUtil.getString("am.flashview.edit.range1"));
/*  648 */             out.write(" </td>\n</tr>\n<tr class=\"bodytext\">\n       <td width=\"20%\" class=\"bodytext label-align\" align=\"left\">");
/*  649 */             out.print(FormatUtil.getString("am.webclient.flashview.linetransparency.text"));
/*  650 */             out.write(" :  </td>\n       <td width=\"80%\" align=\"left\">");
/*  651 */             if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  653 */             out.print(FormatUtil.getString("am.flashview.edit.range2"));
/*  654 */             out.write(" </td>\n</tr>\n<tr class=\"bodytext\">\n   <td width=\"100%\" align=\"left\" colspan=\"2\" height=\"32\">");
/*  655 */             out.print(FormatUtil.getString("am.webclient.flashview.color.help.text"));
/*  656 */             out.write("</td>\n</tr>\n</table>\n<table class=\"lrbborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\">\n<tr>\n\t<td class=\"tablebottom\" width=\"20%\" class=\"bodytext label-align\"></td>\n    <td class=\"tablebottom\" height=\"28\">\n    <input name=\"button2\" class=\"buttons\" value='");
/*  657 */             out.print(FormatUtil.getString("am.webclient.flashview.saveconfig.text"));
/*  658 */             out.write("' type=\"button\" onclick=\"return submitForm()\">\n    </td>\n</tr>\n</table>\n\n<br>\n\n\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t\t<tr>\n\t\t\t<td class=\"helpCardHdrTopLeft\"/>\n\t\t\t<td class=\"helpCardHdrTopBg\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t\t\t<tr>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/*  659 */             out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/*  660 */             out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td>\n\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t</table></td>\n\t\t\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\n\t\t\t<td valign=\"top\">\n\t\t\t<!--//include your Helpcard template table here..-->\n\n\n\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n    <tr>\n    <td style=\"padding-top: 10px;\" class=\"boxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\n      <tr>\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n        <tr>\n          <td class=\"hCardInnerTopLeft\"/>\n          <td class=\"hCardInnerTopBg\"/>\n          <td class=\"hCardInnerTopRight\"/>\n        </tr>\n        <tr>\n\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n                <td class=\"hCardInnerBoxBg\">\n");
/*  661 */             out.write("\n\n\t\t");
/*  662 */             out.print(FormatUtil.getString("am.webclient.flashview.displayprops.helpcard.text"));
/*  663 */             out.write("\n\n\n            </td>\n\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n        </tr>\n\n      </table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td class=\"helpCardMainBtmLeft\"/>\n\t\t\t<td class=\"helpCardMainBtmBg\"/>\n\t\t\t<td class=\"helpCardMainBtmRight\"/>\n\n\t\t\t</tr>\n\t\t\t</table>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n</div>\n\n<div id=\"status\" style=\"display:none\">\n<table class=\"lrborder\" border=\"0\" cellpadding=\"4\" cellspacing=\"2\" width=\"99%\">\n  <tr class=\"bodytext\">\n        <td width=\"20%\" class=\"bodytext label-align\" align=\"left\">");
/*  664 */             out.print(FormatUtil.getString("Description"));
/*  665 */             out.write(" : </td>\n        <td width=\"80%\" align=\"left\">");
/*  666 */             if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  668 */             out.write(" </td>\n </tr>\n\n</table>\n<table class=\"lrbborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\">\n<tr>\n\t<td class=\"tablebottom\" width=\"20%\"></td>\n    <td class=\"tablebottom\" height=\"28\">\n    <input name=\"button3\" class=\"buttons\" value='");
/*  669 */             out.print(FormatUtil.getString("am.webclient.flashview.saveconfig.text"));
/*  670 */             out.write("' type=\"button\" onclick=\"return submitForm()\">\n    </td>\n</tr>\n</table>\n\n</div>\n\n<div id=\"asscoiatemgs\" style=\"display:none\">\n\t<br>\n\t        <table class=\"lrtbdarkborder\"  cellspacing=\"0\" cellpadding=\"0\"  width=\"97%\">\n\t        <tr>\n\t            <td width=\"2%\" class=\"tableheadingbborder\"  height=\"25\">\n\t            <input type=\"checkbox\" name=\"headercheckbox\" onClick=\"javascript:selectall(this)\">\n\t            </td>\n\t            <td width=\"98%\" class=\"tableheadingbborder\" height=\"25\">\n\t            ");
/*  671 */             out.print(FormatUtil.getString("am.webclient.flashview.mgs.text"));
/*  672 */             out.write("\n\t            </td>\n\t        </tr>\n\t\t");
/*  673 */             if (_jspx_meth_c_005fforEach_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */               return;
/*  675 */             out.write("\n\t\t<tr>\n\n\t\t    <td colspan=\"2\" class=\"tablebottom\" align=\"center\" height=\"28\">\n\t\t    <input name=\"button4\" class=\"buttons\" value='");
/*  676 */             out.print(FormatUtil.getString("am.webclient.flashview.saveconfig.text"));
/*  677 */             out.write("' type=\"button\" onclick=\"return submitForm()\">\n\t\t    </td>\n\t\t</tr>\n\t\t</table>\n\n</div>\n\n");
/*  678 */             int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  679 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  683 */         if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  684 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */         }
/*      */         
/*  687 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  688 */         out.write(10);
/*  689 */         out.write(10);
/*      */ 
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  694 */         ex.printStackTrace();
/*      */       }
/*      */       
/*  697 */       out.write(10);
/*      */     } catch (Throwable t) {
/*  699 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  700 */         out = _jspx_out;
/*  701 */         if ((out != null) && (out.getBufferSize() != 0))
/*  702 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  703 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  706 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  712 */     PageContext pageContext = _jspx_page_context;
/*  713 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  715 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  716 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  717 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  719 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  721 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  722 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  723 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  724 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  725 */       return true;
/*      */     }
/*  727 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  728 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  733 */     PageContext pageContext = _jspx_page_context;
/*  734 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  736 */     org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f0 = (org.apache.struts.taglib.html.HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
/*  737 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/*  738 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  740 */     _jspx_th_html_005fhidden_005f0.setProperty("fromMonitorTab");
/*  741 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/*  742 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/*  743 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/*  744 */       return true;
/*      */     }
/*  746 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/*  747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  752 */     PageContext pageContext = _jspx_page_context;
/*  753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  755 */     org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f1 = (org.apache.struts.taglib.html.HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
/*  756 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/*  757 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  759 */     _jspx_th_html_005fhidden_005f1.setProperty("fromWhere");
/*  760 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/*  761 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/*  762 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/*  763 */       return true;
/*      */     }
/*  765 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/*  766 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  771 */     PageContext pageContext = _jspx_page_context;
/*  772 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  774 */     org.apache.struts.taglib.html.HiddenTag _jspx_th_html_005fhidden_005f2 = (org.apache.struts.taglib.html.HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(org.apache.struts.taglib.html.HiddenTag.class);
/*  775 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/*  776 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  778 */     _jspx_th_html_005fhidden_005f2.setProperty("popUp");
/*  779 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/*  780 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/*  781 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/*  782 */       return true;
/*      */     }
/*  784 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/*  785 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  790 */     PageContext pageContext = _jspx_page_context;
/*  791 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  793 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  794 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  795 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  797 */     _jspx_th_c_005fif_005f2.setTest("${not empty param.createnewview}");
/*  798 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  799 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/*  801 */         out.write("\n\t<input type=\"hidden\" name=\"createnewview\" value=\"true\"/>\n");
/*  802 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  803 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  807 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  808 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  809 */       return true;
/*      */     }
/*  811 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  817 */     PageContext pageContext = _jspx_page_context;
/*  818 */     JspWriter out = _jspx_page_context.getOut();
/*  819 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/*  820 */     javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/*  822 */     ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  823 */     _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/*  824 */     _jspx_th_c_005fchoose_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*  825 */     int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/*  826 */     if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */       for (;;) {
/*  828 */         out.write(10);
/*  829 */         if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/*  830 */           return true;
/*  831 */         out.write(10);
/*  832 */         if (_jspx_meth_c_005fotherwise_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/*  833 */           return true;
/*  834 */         out.write(10);
/*  835 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/*  836 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  840 */     if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/*  841 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/*  842 */       return true;
/*      */     }
/*  844 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/*  845 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  850 */     PageContext pageContext = _jspx_page_context;
/*  851 */     JspWriter out = _jspx_page_context.getOut();
/*  852 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/*  853 */     javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/*  855 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  856 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/*  857 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/*      */     
/*  859 */     _jspx_th_c_005fwhen_005f6.setTest("${ FlashForm.fromMonitorTab == true}");
/*  860 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/*  861 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/*  863 */         out.write(10);
/*  864 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("am.webclient.flashview.viewdescription.text,am.webclient.flashview.monitorgroups.text", request.getCharacterEncoding()) + "&" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("am.webclient.flashview.viewdescription.text,am.webclient.flashview.monitorgroups.text", request.getCharacterEncoding()) + "&" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("showDescription,associateMgs", request.getCharacterEncoding()) + "&" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("am.webclient.flashview.viewdescription.text", request.getCharacterEncoding()), out, true);
/*  865 */         out.write(10);
/*  866 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/*  867 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  871 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/*  872 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/*  873 */       return true;
/*      */     }
/*  875 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/*  876 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  881 */     PageContext pageContext = _jspx_page_context;
/*  882 */     JspWriter out = _jspx_page_context.getOut();
/*  883 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/*  884 */     javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse)_jspx_page_context.getResponse();
/*      */     
/*  886 */     OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  887 */     _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/*  888 */     _jspx_th_c_005fotherwise_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/*  889 */     int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/*  890 */     if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */       for (;;) {
/*  892 */         out.write(10);
/*  893 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("am.webclient.flashview.viewdescription.text", request.getCharacterEncoding()) + "&" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("am.webclient.flashview.viewdescription.text", request.getCharacterEncoding()) + "&" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("showDescription", request.getCharacterEncoding()) + "&" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("am.webclient.flashview.viewdescription.text", request.getCharacterEncoding()), out, true);
/*  894 */         out.write(10);
/*  895 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/*  896 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  900 */     if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/*  901 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/*  902 */       return true;
/*      */     }
/*  904 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/*  905 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  910 */     PageContext pageContext = _jspx_page_context;
/*  911 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  913 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  914 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/*  915 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  917 */     _jspx_th_html_005ftext_005f0.setProperty("displayName");
/*      */     
/*  919 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */     
/*  921 */     _jspx_th_html_005ftext_005f0.setSize("30");
/*  922 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/*  923 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/*  924 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  925 */       return true;
/*      */     }
/*  927 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  933 */     PageContext pageContext = _jspx_page_context;
/*  934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  936 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  937 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/*  938 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  940 */     _jspx_th_html_005ftext_005f1.setProperty("bgColor");
/*      */     
/*  942 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/*  944 */     _jspx_th_html_005ftext_005f1.setSize("30");
/*  945 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/*  946 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/*  947 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/*  948 */       return true;
/*      */     }
/*  950 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/*  951 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  956 */     PageContext pageContext = _jspx_page_context;
/*  957 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  959 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  960 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/*  961 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  963 */     _jspx_th_html_005ftext_005f2.setProperty("lineColor");
/*      */     
/*  965 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/*  967 */     _jspx_th_html_005ftext_005f2.setSize("30");
/*  968 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/*  969 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/*  970 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/*  971 */       return true;
/*      */     }
/*  973 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/*  974 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  979 */     PageContext pageContext = _jspx_page_context;
/*  980 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  982 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/*  983 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/*  984 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  986 */     _jspx_th_html_005ftext_005f3.setProperty("labelColor");
/*      */     
/*  988 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/*  990 */     _jspx_th_html_005ftext_005f3.setSize("30");
/*  991 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/*  992 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/*  993 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/*  994 */       return true;
/*      */     }
/*  996 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/*  997 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1002 */     PageContext pageContext = _jspx_page_context;
/* 1003 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1005 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 1006 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 1007 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1009 */     _jspx_th_html_005ftext_005f4.setProperty("linethickness");
/*      */     
/* 1011 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/*      */     
/* 1013 */     _jspx_th_html_005ftext_005f4.setSize("15");
/* 1014 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 1015 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 1016 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 1017 */       return true;
/*      */     }
/* 1019 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 1020 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1025 */     PageContext pageContext = _jspx_page_context;
/* 1026 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1028 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 1029 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 1030 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1032 */     _jspx_th_html_005ftext_005f5.setProperty("lineTransparency");
/*      */     
/* 1034 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext");
/*      */     
/* 1036 */     _jspx_th_html_005ftext_005f5.setSize("15");
/* 1037 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 1038 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 1039 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 1040 */       return true;
/*      */     }
/* 1042 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 1043 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1048 */     PageContext pageContext = _jspx_page_context;
/* 1049 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1051 */     org.apache.struts.taglib.html.TextareaTag _jspx_th_html_005ftextarea_005f0 = (org.apache.struts.taglib.html.TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(org.apache.struts.taglib.html.TextareaTag.class);
/* 1052 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 1053 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1055 */     _jspx_th_html_005ftextarea_005f0.setProperty("description");
/*      */     
/* 1057 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea");
/*      */     
/* 1059 */     _jspx_th_html_005ftextarea_005f0.setRows("4");
/*      */     
/* 1061 */     _jspx_th_html_005ftextarea_005f0.setCols("50");
/* 1062 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 1063 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 1064 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 1065 */       return true;
/*      */     }
/* 1067 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 1068 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1073 */     PageContext pageContext = _jspx_page_context;
/* 1074 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1076 */     org.apache.taglibs.standard.tag.el.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.el.core.ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(org.apache.taglibs.standard.tag.el.core.ForEachTag.class);
/* 1077 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1078 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1080 */     _jspx_th_c_005fforEach_005f0.setVar("singleRow");
/*      */     
/* 1082 */     _jspx_th_c_005fforEach_005f0.setItems("${FlashForm.availableMgs}");
/*      */     
/* 1084 */     _jspx_th_c_005fforEach_005f0.setVarStatus("linecount");
/* 1085 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1087 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1088 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1090 */           out.write("\n\t\t\t");
/* 1091 */           boolean bool; if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1092 */             return true;
/* 1093 */           out.write("\n                        ");
/* 1094 */           if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1095 */             return true;
/* 1096 */           out.write("\n\n\t\t<tr>\n\t\t    <td  width=\"2%\" class=\"");
/* 1097 */           if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1098 */             return true;
/* 1099 */           out.write("\">\n\t\t      ");
/* 1100 */           if (_jspx_meth_html_005fmultibox_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1101 */             return true;
/* 1102 */           out.write("\n\t\t    </td>\n\t\t    <td width=\"98%\" align=\"left\" class=\"");
/* 1103 */           if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1104 */             return true;
/* 1105 */           out.write("\">\n\t\t\t");
/* 1106 */           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1107 */             return true;
/* 1108 */           out.write("\n\t\t    </td>\n\t\t</tr>\n\t\t");
/* 1109 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1110 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1114 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1115 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1118 */         int tmp400_399 = 0; int[] tmp400_397 = _jspx_push_body_count_c_005fforEach_005f0; int tmp402_401 = tmp400_397[tmp400_399];tmp400_397[tmp400_399] = (tmp402_401 - 1); if (tmp402_401 <= 0) break;
/* 1119 */         out = _jspx_page_context.popBody(); }
/* 1120 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1122 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1123 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1125 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1130 */     PageContext pageContext = _jspx_page_context;
/* 1131 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1133 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1134 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 1135 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1137 */     _jspx_th_c_005fif_005f3.setTest("${linecount.count % 2==0}");
/* 1138 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 1139 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 1141 */         out.write("\n\t\t\t\t");
/* 1142 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1143 */           return true;
/* 1144 */         out.write("\n\t\t\t");
/* 1145 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 1146 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1150 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 1151 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1152 */       return true;
/*      */     }
/* 1154 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 1155 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1160 */     PageContext pageContext = _jspx_page_context;
/* 1161 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1163 */     org.apache.taglibs.standard.tag.el.core.SetTag _jspx_th_c_005fset_005f0 = (org.apache.taglibs.standard.tag.el.core.SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(org.apache.taglibs.standard.tag.el.core.SetTag.class);
/* 1164 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 1165 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 1167 */     _jspx_th_c_005fset_005f0.setVar("linecolor");
/*      */     
/* 1169 */     _jspx_th_c_005fset_005f0.setScope("page");
/*      */     
/* 1171 */     _jspx_th_c_005fset_005f0.setValue("whitegrayborder");
/* 1172 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 1173 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 1174 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 1175 */       return true;
/*      */     }
/* 1177 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 1178 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1183 */     PageContext pageContext = _jspx_page_context;
/* 1184 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1186 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1187 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 1188 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1190 */     _jspx_th_c_005fif_005f4.setTest("${linecount.count % 2!=0}");
/* 1191 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 1192 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 1194 */         out.write("\n\t\t\t\t");
/* 1195 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1196 */           return true;
/* 1197 */         out.write("\n\t\t\t");
/* 1198 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 1199 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1203 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 1204 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 1205 */       return true;
/*      */     }
/* 1207 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 1208 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1213 */     PageContext pageContext = _jspx_page_context;
/* 1214 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1216 */     org.apache.taglibs.standard.tag.el.core.SetTag _jspx_th_c_005fset_005f1 = (org.apache.taglibs.standard.tag.el.core.SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(org.apache.taglibs.standard.tag.el.core.SetTag.class);
/* 1217 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 1218 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 1220 */     _jspx_th_c_005fset_005f1.setVar("linecolor");
/*      */     
/* 1222 */     _jspx_th_c_005fset_005f1.setScope("page");
/*      */     
/* 1224 */     _jspx_th_c_005fset_005f1.setValue("yellowgrayborder");
/* 1225 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 1226 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 1227 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 1228 */       return true;
/*      */     }
/* 1230 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 1231 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1236 */     PageContext pageContext = _jspx_page_context;
/* 1237 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1239 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1240 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1241 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1243 */     _jspx_th_c_005fout_005f1.setValue("${linecolor}");
/* 1244 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1245 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1246 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1247 */       return true;
/*      */     }
/* 1249 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1250 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1255 */     PageContext pageContext = _jspx_page_context;
/* 1256 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1258 */     org.apache.struts.taglib.html.MultiboxTag _jspx_th_html_005fmultibox_005f0 = (org.apache.struts.taglib.html.MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty.get(org.apache.struts.taglib.html.MultiboxTag.class);
/* 1259 */     _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/* 1260 */     _jspx_th_html_005fmultibox_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1262 */     _jspx_th_html_005fmultibox_005f0.setProperty("monitorGroups");
/* 1263 */     int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/* 1264 */     if (_jspx_eval_html_005fmultibox_005f0 != 0) {
/* 1265 */       if (_jspx_eval_html_005fmultibox_005f0 != 1) {
/* 1266 */         out = _jspx_page_context.pushBody();
/* 1267 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 1268 */         _jspx_th_html_005fmultibox_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1269 */         _jspx_th_html_005fmultibox_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1272 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_html_005fmultibox_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1273 */           return true;
/* 1274 */         int evalDoAfterBody = _jspx_th_html_005fmultibox_005f0.doAfterBody();
/* 1275 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1278 */       if (_jspx_eval_html_005fmultibox_005f0 != 1) {
/* 1279 */         out = _jspx_page_context.popBody();
/* 1280 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 1283 */     if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/* 1284 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty.reuse(_jspx_th_html_005fmultibox_005f0);
/* 1285 */       return true;
/*      */     }
/* 1287 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty.reuse(_jspx_th_html_005fmultibox_005f0);
/* 1288 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_html_005fmultibox_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1293 */     PageContext pageContext = _jspx_page_context;
/* 1294 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1296 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1297 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1298 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_html_005fmultibox_005f0);
/*      */     
/* 1300 */     _jspx_th_c_005fout_005f2.setValue("${singleRow[0]}");
/* 1301 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1302 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1303 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1304 */       return true;
/*      */     }
/* 1306 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1307 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1312 */     PageContext pageContext = _jspx_page_context;
/* 1313 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1315 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1316 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1317 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1319 */     _jspx_th_c_005fout_005f3.setValue("${linecolor}");
/* 1320 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1321 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1322 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1323 */       return true;
/*      */     }
/* 1325 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1326 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1331 */     PageContext pageContext = _jspx_page_context;
/* 1332 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1334 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1335 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1336 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1338 */     _jspx_th_c_005fout_005f4.setValue("${singleRow[1]}");
/* 1339 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1340 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1341 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1342 */       return true;
/*      */     }
/* 1344 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1345 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\FlashViewEdit_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */