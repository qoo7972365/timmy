/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.util.ArrayList;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class MyPage_005fRelatedAttribs_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   21 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */   private static java.util.Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   44 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   48 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   49 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   50 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   51 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   52 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   53 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   54 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   55 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   56 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   62 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   66 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   67 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   68 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   69 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*   70 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*   71 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.release();
/*   72 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   73 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*   74 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   75 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.release();
/*   76 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*   77 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*   78 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   85 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   88 */     JspWriter out = null;
/*   89 */     Object page = this;
/*   90 */     JspWriter _jspx_out = null;
/*   91 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*   95 */       response.setContentType("text/html");
/*   96 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*   98 */       _jspx_page_context = pageContext;
/*   99 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  100 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  101 */       session = pageContext.getSession();
/*  102 */       out = pageContext.getOut();
/*  103 */       _jspx_out = out;
/*      */       
/*  105 */       out.write("\n\n\n\n\n\n");
/*  106 */       response.setContentType("text/html;charset=UTF-8");
/*  107 */       out.write(10);
/*      */       
/*  109 */       com.adventnet.appmanager.struts.form.MyPageForm fm = (com.adventnet.appmanager.struts.form.MyPageForm)request.getAttribute("MyPageForm");
/*      */       
/*  111 */       out.write(10);
/*      */       
/*  113 */       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  114 */       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  115 */       _jspx_th_c_005fchoose_005f0.setParent(null);
/*  116 */       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  117 */       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */         for (;;) {
/*  119 */           out.write(10);
/*      */           
/*  121 */           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  122 */           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  123 */           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  125 */           _jspx_th_c_005fwhen_005f0.setTest("${related_action==\"monitorgroups\"}");
/*  126 */           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  127 */           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */             for (;;) {
/*  129 */               out.write("\n<span class=\"bodytext\"><i>");
/*  130 */               out.print(FormatUtil.getString("am.mypage.editwidget.selectmg.text"));
/*  131 */               out.write(" </i></span><br>\n\n");
/*  132 */               if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  134 */               out.write(10);
/*  135 */               if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                 return;
/*  137 */               out.write("\n\n\t ");
/*      */               
/*  139 */               org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (org.apache.struts.taglib.logic.NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
/*  140 */               _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/*  141 */               _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */               
/*  143 */               _jspx_th_logic_005fnotEmpty_005f0.setName("applications1");
/*  144 */               int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/*  145 */               if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                 for (;;) {
/*  147 */                   out.write("\n\t     ");
/*      */                   
/*  149 */                   IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  150 */                   _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/*  151 */                   _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                   
/*  153 */                   _jspx_th_logic_005fiterate_005f0.setName("applications1");
/*      */                   
/*  155 */                   _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                   
/*  157 */                   _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                   
/*  159 */                   _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/*  160 */                   int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/*  161 */                   if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/*  162 */                     ArrayList row = null;
/*  163 */                     Integer j = null;
/*  164 */                     if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  165 */                       out = _jspx_page_context.pushBody();
/*  166 */                       _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  167 */                       _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                     }
/*  169 */                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  170 */                     j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                     for (;;) {
/*  172 */                       out.write("\n\t              ");
/*      */                       
/*  174 */                       String selected = "";
/*  175 */                       String currentmg = (String)row.get(1);
/*  176 */                       String selectmg = fm.getSelectedMG();
/*  177 */                       if ((selectmg != null) && (selectmg.equals(currentmg)))
/*      */                       {
/*  179 */                         selected = "selected=\"selected\"";
/*      */                       }
/*      */                       
/*  182 */                       out.write("\n\t \t      <option value=\"");
/*  183 */                       out.print((String)row.get(1));
/*  184 */                       out.write(34);
/*  185 */                       out.write(32);
/*  186 */                       out.print(selected);
/*  187 */                       out.write(62);
/*  188 */                       out.print(row.get(0));
/*  189 */                       out.write("</option>\n\t     ");
/*  190 */                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/*  191 */                       row = (ArrayList)_jspx_page_context.findAttribute("row");
/*  192 */                       j = (Integer)_jspx_page_context.findAttribute("j");
/*  193 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  196 */                     if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/*  197 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  200 */                   if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/*  201 */                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                   }
/*      */                   
/*  204 */                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*  205 */                   out.write(10);
/*  206 */                   out.write(9);
/*  207 */                   out.write(32);
/*  208 */                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/*  209 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  213 */               if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/*  214 */                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */               }
/*      */               
/*  217 */               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*  218 */               out.write("\n      \t</select>\n");
/*  219 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  220 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  224 */           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  225 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */           }
/*      */           
/*  228 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  229 */           out.write(10);
/*  230 */           out.write(10);
/*      */           
/*  232 */           WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  233 */           _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  234 */           _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  236 */           _jspx_th_c_005fwhen_005f1.setTest("${related_action==\"availMonitorGroupforCustomFields\"}");
/*  237 */           int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  238 */           if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */             for (;;) {
/*  240 */               out.write("\n\n     ");
/*      */               
/*  242 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  243 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  244 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/*      */               
/*  246 */               _jspx_th_c_005fif_005f2.setTest("${not empty MyPageForm.availMultiMonitors }");
/*  247 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  248 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/*  250 */                   out.write("\n      <table width=\"100%\"   border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t<tr class=\"bodytext\">\n\t\t\t<td width=\"5%\">\n\t\t\t<td><em>");
/*  251 */                   out.print(FormatUtil.getString("am.myfields.selectedmg.text"));
/*  252 */                   out.write("</em></td> ");
/*  253 */                   out.write("\n\t\t\t");
/*      */                   
/*  255 */                   ArrayList size_list = fm.getAvailMultiMonitors();
/*  256 */                   if (size_list.size() > 5)
/*      */                   {
/*  258 */                     out.write("\n\t\t\t<td width=\"20%\"><a href=\"javascript:void(0)\" onclick=\"showAllCustomFieldMonitors()\" class=\"staticlinks\"><span id=\"showallfunction\" style=\"display: block;\">");
/*  259 */                     out.print(FormatUtil.getString("am.webclient.common.bulkconfigview.showall.text"));
/*  260 */                     out.write("</span><span id=\"hideallfunction\" style=\"display: none;\">");
/*  261 */                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/*  262 */                     out.write("</span></a></td>\n\t\t\t");
/*      */                   }
/*  264 */                   out.write("\n\t\t\t</tr>\n\t\t\t<tr><td colspan=\"3\">\n\t\t\t\t<div id=\"customFieldlessdisplay\" style=\"display: block;\">\n\t\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t");
/*  265 */                   if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                     return;
/*  267 */                   out.write("\n\t        \t\t</table>\n\t\t\t\t</div>\n\t\t\t\t\n\t\t\t\t<div id=\"customFieldAlldisplay\" style=\"display:none;\">\n\t\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t");
/*  268 */                   if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                     return;
/*  270 */                   out.write("\n\t        \t\t</table>\n\t\t\t\t</div>\n\t\t\t</td></tr>\n\t\t</table>\n \t ");
/*  271 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  272 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  276 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  277 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/*  280 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  281 */               out.write("\n \t ");
/*      */               
/*  283 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  284 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  285 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fwhen_005f1);
/*      */               
/*  287 */               _jspx_th_c_005fif_005f3.setTest("${empty MyPageForm.availMultiMonitors }");
/*  288 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  289 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/*  291 */                   out.write("\n \t \t\t <table>\n\t         <tr class=\"bodytext\"><td><em>");
/*  292 */                   out.print(FormatUtil.getString("am.myfields.nomonitorgroup.text"));
/*  293 */                   out.write("</em></td></tr>\n\t         </table>\n\t ");
/*  294 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  295 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  299 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  300 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/*  303 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  304 */               out.write("\n\t \n");
/*  305 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  306 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  310 */           if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  311 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */           }
/*      */           
/*  314 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  315 */           out.write(10);
/*  316 */           out.write(10);
/*  317 */           out.write(10);
/*      */           
/*  319 */           WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  320 */           _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  321 */           _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  323 */           _jspx_th_c_005fwhen_005f2.setTest("${related_action==\"availMutliMonitors\"}");
/*  324 */           int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  325 */           if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */             for (;;) {
/*  327 */               out.write("\n     ");
/*      */               
/*  329 */               int divsize = 50;
/*  330 */               ArrayList size_list = fm.getAvailMultiMonitors();
/*  331 */               if (size_list != null)
/*      */               {
/*  333 */                 if (size_list.size() < 3)
/*      */                 {
/*  335 */                   divsize = 60;
/*      */                 }
/*  337 */                 else if (size_list.size() < 4)
/*      */                 {
/*  339 */                   divsize = 80;
/*      */                 }
/*  341 */                 else if (size_list.size() < 6)
/*      */                 {
/*  343 */                   divsize = 120;
/*      */                 }
/*      */                 else
/*      */                 {
/*  347 */                   divsize = 120;
/*      */                 }
/*      */               }
/*      */               
/*  351 */               out.write("\n\n     <div style=\"overflow: auto; display: block; width: 100%; height: ");
/*  352 */               out.print(divsize);
/*  353 */               out.write("px;\">\n     <table width=\"100%\"   border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\n\t       ");
/*      */               
/*  355 */               ArrayList selectedlist = new ArrayList();
/*  356 */               if (request.getAttribute("selectedMultiMonitors_list") != null)
/*      */               {
/*  358 */                 selectedlist = (ArrayList)request.getAttribute("selectedMultiMonitors_list");
/*      */               }
/*      */               
/*      */ 
/*  362 */               out.write("\n               ");
/*      */               
/*  364 */               ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  365 */               _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/*  366 */               _jspx_th_c_005fforEach_005f2.setParent(_jspx_th_c_005fwhen_005f2);
/*      */               
/*  368 */               _jspx_th_c_005fforEach_005f2.setVar("singleRow");
/*      */               
/*  370 */               _jspx_th_c_005fforEach_005f2.setItems("${MyPageForm.availMultiMonitors}");
/*      */               
/*  372 */               _jspx_th_c_005fforEach_005f2.setVarStatus("linecount");
/*  373 */               int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */               try {
/*  375 */                 int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/*  376 */                 if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */                   for (;;) {
/*  378 */                     out.write("\n               ");
/*      */                     
/*  380 */                     ArrayList singlerow = (ArrayList)pageContext.getAttribute("singleRow");
/*  381 */                     String resid = (String)singlerow.get(0);
/*  382 */                     String checked = "";
/*  383 */                     String bgcolor = "";
/*  384 */                     if (selectedlist.contains(resid))
/*      */                     {
/*  386 */                       checked = "checked=\"true\"";
/*      */                     }
/*      */                     
/*      */ 
/*  390 */                     out.write("\n\t        <tr class=\"bodytext\">\n\t\t<td  ");
/*  391 */                     out.print(bgcolor);
/*  392 */                     out.write("  width=\"2%\" align=\"left\" >\n\t\t     <input type=\"checkbox\" name=\"selectedMultiMonitors\" ");
/*  393 */                     out.print(checked);
/*  394 */                     out.write(" value=\"");
/*  395 */                     if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                     {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  416 */                       _jspx_th_c_005fforEach_005f2.doFinally();
/*  417 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                     }
/*  397 */                     out.write("\" />\n\t\t</td>\n\t\t<td ");
/*  398 */                     out.print(bgcolor);
/*  399 */                     out.write(" >\n\t\t ");
/*  400 */                     if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                     {
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
/*      */ 
/*  416 */                       _jspx_th_c_005fforEach_005f2.doFinally();
/*  417 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                     }
/*  402 */                     out.write("\n\t\t</td>\n\t        </tr>\n\t        ");
/*  403 */                     int evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/*  404 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  408 */                 if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  416 */                   _jspx_th_c_005fforEach_005f2.doFinally();
/*  417 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                 }
/*      */               }
/*      */               catch (Throwable _jspx_exception)
/*      */               {
/*      */                 for (;;)
/*      */                 {
/*  412 */                   int tmp1865_1864 = 0; int[] tmp1865_1862 = _jspx_push_body_count_c_005fforEach_005f2; int tmp1867_1866 = tmp1865_1862[tmp1865_1864];tmp1865_1862[tmp1865_1864] = (tmp1867_1866 - 1); if (tmp1867_1866 <= 0) break;
/*  413 */                   out = _jspx_page_context.popBody(); }
/*  414 */                 _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */               } finally {
/*  416 */                 _jspx_th_c_005fforEach_005f2.doFinally();
/*  417 */                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */               }
/*  419 */               out.write("\n\t        ");
/*      */               
/*  421 */               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  422 */               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  423 */               _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fwhen_005f2);
/*      */               
/*  425 */               _jspx_th_c_005fif_005f4.setTest("${empty MyPageForm.availMultiMonitors }");
/*  426 */               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  427 */               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                 for (;;) {
/*  429 */                   out.write("\n\t        ");
/*  430 */                   out.print(FormatUtil.getString("no monitors found"));
/*  431 */                   out.write("\n\t        ");
/*  432 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  433 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  437 */               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  438 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */               }
/*      */               
/*  441 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  442 */               out.write("\n\t</table>\n\t</div>\n\n");
/*  443 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  444 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  448 */           if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  449 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */           }
/*      */           
/*  452 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  453 */           out.write(10);
/*  454 */           out.write(10);
/*  455 */           out.write(10);
/*      */           
/*  457 */           WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  458 */           _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/*  459 */           _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  461 */           _jspx_th_c_005fwhen_005f3.setTest("${related_action==\"availCustomFieldMonitors\"}");
/*  462 */           int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/*  463 */           if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */             for (;;) {
/*  465 */               out.write("\n     \n\n     <div style=\"display: block; width: 100%; height: 100%;\">\n      ");
/*      */               
/*  467 */               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  468 */               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  469 */               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fwhen_005f3);
/*  470 */               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  471 */               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                 for (;;) {
/*  473 */                   out.write("\n     ");
/*      */                   
/*  475 */                   WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  476 */                   _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/*  477 */                   _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                   
/*  479 */                   _jspx_th_c_005fwhen_005f4.setTest("${not empty MyPageForm.availMultiMonitors }");
/*  480 */                   int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/*  481 */                   if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                     for (;;) {
/*  483 */                       out.write("\n     \t<table width=\"100%\"   border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t<tr class=\"bodytext\">\n\t\t\t<td width=\"5%\">\n\t\t\t<td><em>");
/*  484 */                       out.print(FormatUtil.getString("am.myfields.selectedmonitor.text"));
/*  485 */                       out.write("</em></td> ");
/*  486 */                       out.write("\n\t\t\t");
/*      */                       
/*  488 */                       ArrayList size_list = fm.getAvailMultiMonitors();
/*  489 */                       if (size_list.size() > 5)
/*      */                       {
/*  491 */                         out.write("\n\t\t\t<td width=\"20%\"><a href=\"javascript:void(0)\" onclick=\"showAllCustomFieldMonitors()\" class=\"staticlinks\"><span id=\"showallfunction\" style=\"display: block;\">");
/*  492 */                         out.print(FormatUtil.getString("am.webclient.common.bulkconfigview.showall.text"));
/*  493 */                         out.write("</span><span id=\"hideallfunction\" style=\"display: none;\">");
/*  494 */                         out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/*  495 */                         out.write("</span></a></td>\n\t\t\t");
/*      */                       }
/*  497 */                       out.write("\n\t\t\t</tr>\n\t\t\t<tr><td colspan=\"3\">\n\t\t\t\t<div id=\"customFieldlessdisplay\" style=\"display: block;\">\n\t\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t");
/*  498 */                       if (_jspx_meth_c_005fforEach_005f3(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                         return;
/*  500 */                       out.write("\n\t        \t\t</table>\n\t\t\t\t</div>\n\t\t\t\t\n\t\t\t\t<div id=\"customFieldAlldisplay\" style=\"display:none;\">\n\t\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t");
/*  501 */                       if (_jspx_meth_c_005fforEach_005f4(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/*      */                         return;
/*  503 */                       out.write("\n\t        \t\t</table>\n\t\t\t\t</div>\n\t\t\t</td></tr>\n\t        \t\n\t\t</table>\n\t");
/*  504 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/*  505 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  509 */                   if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/*  510 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                   }
/*      */                   
/*  513 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/*  514 */                   out.write(10);
/*  515 */                   out.write(9);
/*      */                   
/*  517 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  518 */                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  519 */                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f1);
/*  520 */                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  521 */                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                     for (;;) {
/*  523 */                       out.write("\n\t\t<table>\n\t\t\t<tr class=\"bodytext\">\n\t\t\t\t<td><em>");
/*  524 */                       out.print(FormatUtil.getString("am.myfields.nomonitor.text"));
/*  525 */                       out.write("</em></td>\n\t\t\t</tr>\n\t\t</table>\n\t");
/*  526 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  527 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/*  531 */                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  532 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                   }
/*      */                   
/*  535 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  536 */                   out.write(10);
/*  537 */                   out.write(9);
/*  538 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  539 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  543 */               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  544 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */               }
/*      */               
/*  547 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  548 */               out.write("\n\t</div>\n\n");
/*  549 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/*  550 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  554 */           if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/*  555 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */           }
/*      */           
/*  558 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*  559 */           out.write(10);
/*  560 */           out.write(10);
/*      */           
/*  562 */           WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  563 */           _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/*  564 */           _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  566 */           _jspx_th_c_005fwhen_005f5.setTest("${related_action==\"availMonitors\"}");
/*  567 */           int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/*  568 */           if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */             for (;;) {
/*  570 */               out.write("\n\n         ");
/*      */               
/*  572 */               org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (org.apache.struts.taglib.logic.NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
/*  573 */               _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/*  574 */               _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_c_005fwhen_005f5);
/*      */               
/*  576 */               _jspx_th_logic_005fnotEmpty_005f1.setName("availMonitors");
/*  577 */               int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/*  578 */               if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                 for (;;) {
/*  580 */                   out.write("\n         <table class=\"lrtbdarkborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"50%\">\n         <tr>\n\t \t<td class=\"tableHeader\" width=\"20%\">\n\t \t ");
/*  581 */                   out.print(FormatUtil.getString("am.mypage.editwidget.selectmonitors.text"));
/*  582 */                   out.write("\n\t \t</td>\n\t  </tr>\n\n\t     ");
/*      */                   
/*  584 */                   IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/*  585 */                   _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/*  586 */                   _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                   
/*  588 */                   _jspx_th_logic_005fiterate_005f1.setName("availMonitors");
/*      */                   
/*  590 */                   _jspx_th_logic_005fiterate_005f1.setId("row1");
/*      */                   
/*  592 */                   _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                   
/*  594 */                   _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/*  595 */                   int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/*  596 */                   if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/*  597 */                     ArrayList row1 = null;
/*  598 */                     Integer j = null;
/*  599 */                     if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/*  600 */                       out = _jspx_page_context.pushBody();
/*  601 */                       _jspx_th_logic_005fiterate_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  602 */                       _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                     }
/*  604 */                     row1 = (ArrayList)_jspx_page_context.findAttribute("row1");
/*  605 */                     j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                     for (;;) {
/*  607 */                       out.write("\n\t \t      <tr>\n\t \t      <td class=\"whitegrayrightalign\"><input type=\"checkbox\" value=\"");
/*  608 */                       out.print((String)row1.get(1));
/*  609 */                       out.write("\" name=\"selectedMultiMonitors\" ></td>\n\t \t      <td class=\"whitegrayrightalign\"><span class=\"bodytext\">");
/*  610 */                       out.print(row1.get(0));
/*  611 */                       out.write("</span></td>\n\t     ");
/*  612 */                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/*  613 */                       row1 = (ArrayList)_jspx_page_context.findAttribute("row1");
/*  614 */                       j = (Integer)_jspx_page_context.findAttribute("j");
/*  615 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  618 */                     if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/*  619 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  622 */                   if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/*  623 */                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                   }
/*      */                   
/*  626 */                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/*  627 */                   out.write("\n\t </table>\n\t ");
/*  628 */                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/*  629 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  633 */               if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/*  634 */                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */               }
/*      */               
/*  637 */               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/*  638 */               out.write(10);
/*  639 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/*  640 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  644 */           if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/*  645 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */           }
/*      */           
/*  648 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*  649 */           out.write(10);
/*      */           
/*  651 */           WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  652 */           _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/*  653 */           _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  655 */           _jspx_th_c_005fwhen_005f6.setTest("${related_action==\"relatedattributes\"}");
/*  656 */           int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/*  657 */           if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */             for (;;) {
/*  659 */               out.write("\n        <!-- related attributes are shown only to  types 1,2,3-->\n        ");
/*      */               
/*  661 */               IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  662 */               _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/*  663 */               _jspx_th_c_005fif_005f5.setParent(_jspx_th_c_005fwhen_005f6);
/*      */               
/*  665 */               _jspx_th_c_005fif_005f5.setTest("${MyPageForm.widgetType==1 || MyPageForm.widgetType==2 || MyPageForm.widgetType==3}");
/*  666 */               int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/*  667 */               if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                 for (;;) {
/*  669 */                   out.write("\n\t<table  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"100%\" >\n\n\t<tr class=\"bodytext\" valign=\"top\">\n\t<td width=\"30%\" class=\"bodytext label-align\">\n\t ");
/*  670 */                   out.print(FormatUtil.getString("am.mypage.editwidget.relatedattribs.text"));
/*  671 */                   out.write(" :\n\t</td>\n\t<td width=\"70%\">\n\n\t ");
/*      */                   
/*  673 */                   ArrayList related_sizelist = (ArrayList)request.getAttribute("relatedattributes");
/*  674 */                   int divsizeforattribs = 115;
/*  675 */                   if (related_sizelist != null)
/*      */                   {
/*  677 */                     if (related_sizelist.size() <= 3)
/*      */                     {
/*  679 */                       divsizeforattribs = 60;
/*      */                     }
/*  681 */                     else if (related_sizelist.size() <= 4)
/*      */                     {
/*  683 */                       divsizeforattribs = 80;
/*      */                     }
/*  685 */                     else if (related_sizelist.size() <= 6)
/*      */                     {
/*  687 */                       divsizeforattribs = 120;
/*      */                     }
/*      */                   }
/*      */                   
/*  691 */                   out.write("\n\t<div style=\"overflow: auto; display: block; border:1px solid #c9c9c9; padding:4px 0px 4px 5px; height: ");
/*  692 */                   out.print(divsizeforattribs);
/*  693 */                   out.write("px;\">\n\t<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"  >\n\t\t    ");
/*      */                   
/*  695 */                   boolean areRelatedAttributesPresent = false;
/*  696 */                   ArrayList selectedlist = new ArrayList();
/*  697 */                   if (request.getAttribute("selectedlist") != null)
/*      */                   {
/*  699 */                     selectedlist = (ArrayList)request.getAttribute("selectedlist");
/*      */                   }
/*      */                   
/*  702 */                   out.write(10);
/*  703 */                   out.write(9);
/*  704 */                   out.write(9);
/*      */                   
/*  706 */                   ForEachTag _jspx_th_c_005fforEach_005f5 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  707 */                   _jspx_th_c_005fforEach_005f5.setPageContext(_jspx_page_context);
/*  708 */                   _jspx_th_c_005fforEach_005f5.setParent(_jspx_th_c_005fif_005f5);
/*      */                   
/*  710 */                   _jspx_th_c_005fforEach_005f5.setVar("attr");
/*      */                   
/*  712 */                   _jspx_th_c_005fforEach_005f5.setItems("${relatedattributes}");
/*      */                   
/*  714 */                   _jspx_th_c_005fforEach_005f5.setVarStatus("counter");
/*  715 */                   int[] _jspx_push_body_count_c_005fforEach_005f5 = { 0 };
/*      */                   try {
/*  717 */                     int _jspx_eval_c_005fforEach_005f5 = _jspx_th_c_005fforEach_005f5.doStartTag();
/*  718 */                     if (_jspx_eval_c_005fforEach_005f5 != 0) {
/*      */                       for (;;) {
/*  720 */                         out.write(10);
/*  721 */                         out.write(9);
/*  722 */                         out.write(9);
/*      */                         
/*  724 */                         areRelatedAttributesPresent = true;
/*  725 */                         ArrayList singlerow = (ArrayList)pageContext.getAttribute("attr");
/*  726 */                         String attr = (String)singlerow.get(0);
/*  727 */                         String checked = "";
/*  728 */                         if (selectedlist.contains(attr))
/*      */                         {
/*  730 */                           checked = "checked=\"true\"";
/*      */                         }
/*      */                         
/*  733 */                         out.write("\n\t\t<tr>\n\t\t<td width=\"5%\" align=\"center\" class=\"editwidget-checkbox-padding\"><input type=\"checkbox\" value=\"");
/*  734 */                         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f5))
/*      */                         {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  757 */                           _jspx_th_c_005fforEach_005f5.doFinally();
/*  758 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5); return;
/*      */                         }
/*  736 */                         out.write("\" name=\"relatedAttributes\" ");
/*  737 */                         out.print(checked);
/*  738 */                         out.write("/></td>\n\t\t<td  width=\"90%\"><span class=\"bodytext\">");
/*  739 */                         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f5))
/*      */                         {
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
/*      */ 
/*      */ 
/*      */ 
/*  757 */                           _jspx_th_c_005fforEach_005f5.doFinally();
/*  758 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5); return;
/*      */                         }
/*  741 */                         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f5))
/*      */                         {
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
/*      */ 
/*  757 */                           _jspx_th_c_005fforEach_005f5.doFinally();
/*  758 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5); return;
/*      */                         }
/*  743 */                         out.write("</span></td>\n\t\t</tr>\n\t\t");
/*  744 */                         int evalDoAfterBody = _jspx_th_c_005fforEach_005f5.doAfterBody();
/*  745 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  749 */                     if (_jspx_th_c_005fforEach_005f5.doEndTag() == 5)
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  757 */                       _jspx_th_c_005fforEach_005f5.doFinally();
/*  758 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5); return;
/*      */                     }
/*      */                   }
/*      */                   catch (Throwable _jspx_exception)
/*      */                   {
/*      */                     for (;;)
/*      */                     {
/*  753 */                       int tmp3925_3924 = 0; int[] tmp3925_3922 = _jspx_push_body_count_c_005fforEach_005f5; int tmp3927_3926 = tmp3925_3922[tmp3925_3924];tmp3925_3922[tmp3925_3924] = (tmp3927_3926 - 1); if (tmp3927_3926 <= 0) break;
/*  754 */                       out = _jspx_page_context.popBody(); }
/*  755 */                     _jspx_th_c_005fforEach_005f5.doCatch(_jspx_exception);
/*      */                   } finally {
/*  757 */                     _jspx_th_c_005fforEach_005f5.doFinally();
/*  758 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f5);
/*      */                   }
/*  760 */                   out.write(10);
/*  761 */                   out.write(9);
/*  762 */                   out.write(9);
/*  763 */                   if (!areRelatedAttributesPresent) {
/*  764 */                     out.write("\n\t\t<tr height=\"40px\">\n\t\t<td class=\"bodytext\">\n\t\t");
/*  765 */                     out.print(FormatUtil.getString("am.mypage.editwidget.norelatedattribs.text"));
/*  766 */                     out.write("\n\t\t</td>\n\t\t</tr>\n\t\t");
/*      */                   }
/*  768 */                   out.write("\n\t</table>\n\t</div>\n\t</td>\n\t<td width=\"20%\">&nbsp;\n\t\n\t</td>\n\t</tr>\n\t</table>\n\t<br>\n\t");
/*  769 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/*  770 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  774 */               if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/*  775 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */               }
/*      */               
/*  778 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/*  779 */               out.write(10);
/*  780 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/*  781 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  785 */           if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/*  786 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */           }
/*      */           
/*  789 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/*  790 */           out.write(10);
/*  791 */           out.write(10);
/*  792 */           out.write(10);
/*      */           
/*  794 */           WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  795 */           _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/*  796 */           _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  798 */           _jspx_th_c_005fwhen_005f7.setTest("${related_action==\"selectSingleMonitor\" }");
/*  799 */           int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/*  800 */           if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */             for (;;) {
/*  802 */               out.write(10);
/*  803 */               out.write(10);
/*      */               
/*  805 */               IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  806 */               _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/*  807 */               _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fwhen_005f7);
/*      */               
/*  809 */               _jspx_th_c_005fif_005f6.setTest("${MyPageForm.widgetType==2 || MyPageForm.widgetType==21}");
/*  810 */               int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/*  811 */               if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                 for (;;) {
/*  813 */                   out.write("\n\t<table  border=\"0\" cellpadding=\"0\" cellspacing=\"2\" width=\"100%\"  >\n\t");
/*      */                   
/*  815 */                   int noofmonitors = 0;
/*      */                   
/*  817 */                   out.write(10);
/*  818 */                   out.write(9);
/*      */                   
/*  820 */                   ForEachTag _jspx_th_c_005fforEach_005f6 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  821 */                   _jspx_th_c_005fforEach_005f6.setPageContext(_jspx_page_context);
/*  822 */                   _jspx_th_c_005fforEach_005f6.setParent(_jspx_th_c_005fif_005f6);
/*      */                   
/*  824 */                   _jspx_th_c_005fforEach_005f6.setVar("monitor");
/*      */                   
/*  826 */                   _jspx_th_c_005fforEach_005f6.setItems("${MyPageForm.availMonitors}");
/*      */                   
/*  828 */                   _jspx_th_c_005fforEach_005f6.setVarStatus("counter");
/*  829 */                   int[] _jspx_push_body_count_c_005fforEach_005f6 = { 0 };
/*      */                   try {
/*  831 */                     int _jspx_eval_c_005fforEach_005f6 = _jspx_th_c_005fforEach_005f6.doStartTag();
/*  832 */                     if (_jspx_eval_c_005fforEach_005f6 != 0) {
/*      */                       for (;;) {
/*  834 */                         out.write(10);
/*  835 */                         out.write(9);
/*  836 */                         noofmonitors++;
/*  837 */                         out.write("\n\t\t\t          ");
/*  838 */                         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fforEach_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/*      */                         {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  861 */                           _jspx_th_c_005fforEach_005f6.doFinally();
/*  862 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f6); return;
/*      */                         }
/*  840 */                         out.write("\n\t\t\t          ");
/*  841 */                         if (_jspx_meth_c_005fif_005f7(_jspx_th_c_005fforEach_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/*      */                         {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  861 */                           _jspx_th_c_005fforEach_005f6.doFinally();
/*  862 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f6); return;
/*      */                         }
/*  843 */                         out.write("\n\n\t\t\t\t  ");
/*  844 */                         if (_jspx_meth_c_005fif_005f8(_jspx_th_c_005fforEach_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/*      */                         {
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
/*      */ 
/*      */ 
/*  861 */                           _jspx_th_c_005fforEach_005f6.doFinally();
/*  862 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f6); return;
/*      */                         }
/*  846 */                         out.write(10);
/*  847 */                         out.write(9);
/*  848 */                         int evalDoAfterBody = _jspx_th_c_005fforEach_005f6.doAfterBody();
/*  849 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  853 */                     if (_jspx_th_c_005fforEach_005f6.doEndTag() == 5)
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  861 */                       _jspx_th_c_005fforEach_005f6.doFinally();
/*  862 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f6); return;
/*      */                     }
/*      */                   }
/*      */                   catch (Throwable _jspx_exception)
/*      */                   {
/*      */                     for (;;)
/*      */                     {
/*  857 */                       int tmp4620_4619 = 0; int[] tmp4620_4617 = _jspx_push_body_count_c_005fforEach_005f6; int tmp4622_4621 = tmp4620_4617[tmp4620_4619];tmp4620_4617[tmp4620_4619] = (tmp4622_4621 - 1); if (tmp4622_4621 <= 0) break;
/*  858 */                       out = _jspx_page_context.popBody(); }
/*  859 */                     _jspx_th_c_005fforEach_005f6.doCatch(_jspx_exception);
/*      */                   } finally {
/*  861 */                     _jspx_th_c_005fforEach_005f6.doFinally();
/*  862 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f6);
/*      */                   }
/*  864 */                   out.write(10);
/*  865 */                   out.write(9);
/*      */                   
/*  867 */                   String selectmon = (String)pageContext.getAttribute("selectmonitorname");
/*  868 */                   int divlength1 = 80;
/*  869 */                   if ((noofmonitors > 10) && (noofmonitors < 20))
/*      */                   {
/*  871 */                     divlength1 = 130;
/*      */                   }
/*  873 */                   else if (noofmonitors > 25)
/*      */                   {
/*  875 */                     divlength1 = 200;
/*      */                   }
/*      */                   
/*  878 */                   out.write("\n\t <tr  class=\"bodytext\">\n\t<td width=\"30%\" class=\"bodytext label-align\">");
/*  879 */                   out.print(FormatUtil.getString("am.mypage.editwidget.availmonitors.text"));
/*  880 */                   out.write(" :</td>\n\t<td width=\"70%\">\n\t<input type=\"hidden\" name=\"childParentid\" id=\"childParentid\" value=\"");
/*  881 */                   if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/*  883 */                   out.write("\"/>\n\t<input type=\"hidden\"  name=\"selectedMonitor\" value=\"");
/*  884 */                   if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/*  886 */                   out.write("\"/><input  type=\"text\" readonly=\"readonly\" class=\"formtext input-down-arrow\" size=\"35\" autocomplete=\"off\" name=\"monitorname\" onMousedown=\"DisplayServiceList('service_list_left1','leftimage1')\"  value=\"");
/*  887 */                   out.print(selectmon);
/*  888 */                   out.write("\"><img src=\"../../images/icon_downarrow.gif\" name=\"leftimage1\" style=\"display:none\" class=\"drop-downimg-custom\"  align=\"absmiddle\" onmousedown=\"DisplayServiceList('service_list_left1','leftimage1')\">\n\t<input type=\"hidden\" id=\"childMonitorAttribute\" name=\"childMonitorAttribute\" value=\"");
/*  889 */                   if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/*  891 */                   out.write("\"/>\n\t   <div id=\"dummyid\" style=\"width:0px; height:0px; overflow:none;\">\n\t   <div id=\"leftmonitordisplayframe1\" style=\"display:none\"><iframe src=\"/html/blank.html\" style=\"position:absolute; width:400; height:");
/*  892 */                   out.print(divlength1);
/*  893 */                   out.write("; z-index:0;\" id=\"leftmonitordisplay1\" frameborder=\"0\"></iframe></div></div>\n\t  <div id=\"dummyid\" style=\"width:0px; height:0px; overflow:none; z-index:2;\">\n\t  <div id=\"service_list_left1\" class=\"formtext\" style=\"overflow:auto; width:270; height:");
/*  894 */                   out.print(divlength1);
/*  895 */                   out.write("; display:none; position:absolute; background:#FFFFFF; z-index:2000;\">\n\t  <table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"1\">\n\t    ");
/*  896 */                   if (_jspx_meth_c_005fforEach_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                     return;
/*  898 */                   out.write("\n\t    </table>\n\t      </div>\n       </div>\n\t</td>\n\t</tr>\n\n\t</table>\n\n\n\t");
/*  899 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/*  900 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  904 */               if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/*  905 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */               }
/*      */               
/*  908 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/*  909 */               out.write(10);
/*  910 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/*  911 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  915 */           if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/*  916 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */           }
/*      */           
/*  919 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/*  920 */           out.write(10);
/*  921 */           out.write(10);
/*  922 */           out.write(10);
/*      */           
/*  924 */           WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  925 */           _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/*  926 */           _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  928 */           _jspx_th_c_005fwhen_005f8.setTest("${related_action==\"businessviews\"}");
/*  929 */           int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/*  930 */           if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */             for (;;) {
/*  932 */               out.write("\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\n<tr class=\"bodytext\">\n\t<td width=\"30%\">");
/*  933 */               out.print(FormatUtil.getString("am.mypage.editwidget.availbusinessviews.text"));
/*  934 */               out.write("</td>\n\t<td>\n\t<select name=\"selectedBusinessView\" class=\"formtext\" style=\"width:240px\">\n\n\t\t  ");
/*      */               
/*  936 */               ForEachTag _jspx_th_c_005fforEach_005f8 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  937 */               _jspx_th_c_005fforEach_005f8.setPageContext(_jspx_page_context);
/*  938 */               _jspx_th_c_005fforEach_005f8.setParent(_jspx_th_c_005fwhen_005f8);
/*      */               
/*  940 */               _jspx_th_c_005fforEach_005f8.setVar("view");
/*      */               
/*  942 */               _jspx_th_c_005fforEach_005f8.setItems("${MyPageForm.availBusinessViews}");
/*      */               
/*  944 */               _jspx_th_c_005fforEach_005f8.setVarStatus("counter");
/*  945 */               int[] _jspx_push_body_count_c_005fforEach_005f8 = { 0 };
/*      */               try {
/*  947 */                 int _jspx_eval_c_005fforEach_005f8 = _jspx_th_c_005fforEach_005f8.doStartTag();
/*  948 */                 if (_jspx_eval_c_005fforEach_005f8 != 0) {
/*      */                   for (;;) {
/*  950 */                     out.write("\n\t\t\t\t\t  ");
/*      */                     
/*  952 */                     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  953 */                     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/*  954 */                     _jspx_th_c_005fset_005f6.setParent(_jspx_th_c_005fforEach_005f8);
/*      */                     
/*  956 */                     _jspx_th_c_005fset_005f6.setVar("areBusinessViewsConfigured");
/*  957 */                     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/*  958 */                     if (_jspx_eval_c_005fset_005f6 != 0) {
/*  959 */                       if (_jspx_eval_c_005fset_005f6 != 1) {
/*  960 */                         out = _jspx_page_context.pushBody();
/*  961 */                         _jspx_push_body_count_c_005fforEach_005f8[0] += 1;
/*  962 */                         _jspx_th_c_005fset_005f6.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  963 */                         _jspx_th_c_005fset_005f6.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  966 */                         out.print("true");
/*  967 */                         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/*  968 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  971 */                       if (_jspx_eval_c_005fset_005f6 != 1) {
/*  972 */                         out = _jspx_page_context.popBody();
/*  973 */                         _jspx_push_body_count_c_005fforEach_005f8[0] -= 1;
/*      */                       }
/*      */                     }
/*  976 */                     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/*  977 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1060 */                       _jspx_th_c_005fforEach_005f8.doFinally();
/* 1061 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f8); return;
/*      */                     }
/*  980 */                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/*  981 */                     out.write("\n\t\t\t\t\t  ");
/*  982 */                     if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fforEach_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f8))
/*      */                     {
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
/* 1060 */                       _jspx_th_c_005fforEach_005f8.doFinally();
/* 1061 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f8); return;
/*      */                     }
/*  984 */                     out.write("\n\t\t\t\t\t  ");
/*      */                     
/*  986 */                     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  987 */                     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/*  988 */                     _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fforEach_005f8);
/*      */                     
/*  990 */                     _jspx_th_c_005fif_005f9.setTest("${MyPageForm.selectedBusinessView==view[0]}");
/*  991 */                     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/*  992 */                     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                       for (;;) {
/*  994 */                         out.write("\n\t\t\t\t\t  ");
/*      */                         
/*  996 */                         SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  997 */                         _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/*  998 */                         _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fif_005f9);
/*      */                         
/* 1000 */                         _jspx_th_c_005fset_005f8.setVar("key");
/* 1001 */                         int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 1002 */                         if (_jspx_eval_c_005fset_005f8 != 0) {
/* 1003 */                           if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1004 */                             out = _jspx_page_context.pushBody();
/* 1005 */                             _jspx_push_body_count_c_005fforEach_005f8[0] += 1;
/* 1006 */                             _jspx_th_c_005fset_005f8.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1007 */                             _jspx_th_c_005fset_005f8.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 1010 */                             out.print("selected=selected");
/* 1011 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 1012 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 1015 */                           if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1016 */                             out = _jspx_page_context.popBody();
/* 1017 */                             _jspx_push_body_count_c_005fforEach_005f8[0] -= 1;
/*      */                           }
/*      */                         }
/* 1020 */                         if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 1021 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
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
/* 1060 */                           _jspx_th_c_005fforEach_005f8.doFinally();
/* 1061 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f8); return;
/*      */                         }
/* 1024 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 1025 */                         out.write("\n\t\t\t\t\t  ");
/* 1026 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 1027 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 1031 */                     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 1032 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
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
/*      */ 
/* 1060 */                       _jspx_th_c_005fforEach_005f8.doFinally();
/* 1061 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f8); return;
/*      */                     }
/* 1035 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 1036 */                     out.write("\n\n\t\t\t\t\t  <option value=\"");
/* 1037 */                     if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fforEach_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f8))
/*      */                     {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1060 */                       _jspx_th_c_005fforEach_005f8.doFinally();
/* 1061 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f8); return;
/*      */                     }
/* 1039 */                     out.write(34);
/* 1040 */                     out.write(32);
/* 1041 */                     if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fforEach_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f8))
/*      */                     {
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1060 */                       _jspx_th_c_005fforEach_005f8.doFinally();
/* 1061 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f8); return;
/*      */                     }
/* 1043 */                     out.write(62);
/* 1044 */                     if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fforEach_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f8))
/*      */                     {
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
/*      */ 
/* 1060 */                       _jspx_th_c_005fforEach_005f8.doFinally();
/* 1061 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f8); return;
/*      */                     }
/* 1046 */                     out.write("</option>\n\t\t   ");
/* 1047 */                     int evalDoAfterBody = _jspx_th_c_005fforEach_005f8.doAfterBody();
/* 1048 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1052 */                 if (_jspx_th_c_005fforEach_005f8.doEndTag() == 5)
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1060 */                   _jspx_th_c_005fforEach_005f8.doFinally();
/* 1061 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f8); return;
/*      */                 }
/*      */               }
/*      */               catch (Throwable _jspx_exception)
/*      */               {
/*      */                 for (;;)
/*      */                 {
/* 1056 */                   int tmp5980_5979 = 0; int[] tmp5980_5977 = _jspx_push_body_count_c_005fforEach_005f8; int tmp5982_5981 = tmp5980_5977[tmp5980_5979];tmp5980_5977[tmp5980_5979] = (tmp5982_5981 - 1); if (tmp5982_5981 <= 0) break;
/* 1057 */                   out = _jspx_page_context.popBody(); }
/* 1058 */                 _jspx_th_c_005fforEach_005f8.doCatch(_jspx_exception);
/*      */               } finally {
/* 1060 */                 _jspx_th_c_005fforEach_005f8.doFinally();
/* 1061 */                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f8);
/*      */               }
/* 1063 */               out.write("\n\t\t   ");
/*      */               
/* 1065 */               IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1066 */               _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 1067 */               _jspx_th_c_005fif_005f10.setParent(_jspx_th_c_005fwhen_005f8);
/*      */               
/* 1069 */               _jspx_th_c_005fif_005f10.setTest("${areBusinessViewsConfigured!=\"true\"}");
/* 1070 */               int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 1071 */               if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                 for (;;) {
/* 1073 */                   out.write("\n\t\t    <option value=\"-10\" ");
/* 1074 */                   if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                     return;
/* 1076 */                   out.write(">----");
/* 1077 */                   out.print(FormatUtil.getString("am.mypage.editwidget.noflashview.text"));
/* 1078 */                   out.write("----</option>\n\t\t   ");
/* 1079 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 1080 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1084 */               if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 1085 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */               }
/*      */               
/* 1088 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 1089 */               out.write("\n\t</select>\n\t");
/*      */               
/* 1091 */               IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1092 */               _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 1093 */               _jspx_th_c_005fif_005f11.setParent(_jspx_th_c_005fwhen_005f8);
/*      */               
/* 1095 */               _jspx_th_c_005fif_005f11.setTest("${areBusinessViewsConfigured!=\"true\"}");
/* 1096 */               int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 1097 */               if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                 for (;;) {
/* 1099 */                   out.write("\n\t<br><a target=\"_blank\" href=\"GraphicalView.do?method=createNewView&viewid=-1&haid=0&isPopUp=true\" class=\"staticlinks\">");
/* 1100 */                   out.print(FormatUtil.getString("am.mypage.editwidget.createnewflashview.text"));
/* 1101 */                   out.write("</a>\n\t");
/* 1102 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 1103 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1107 */               if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 1108 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */               }
/*      */               
/* 1111 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 1112 */               out.write("\n\t</td>\n\t</tr>\n\t</table\t>\n");
/* 1113 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 1114 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1118 */           if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 1119 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */           }
/*      */           
/* 1122 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 1123 */           out.write(10);
/* 1124 */           out.write(10);
/* 1125 */           out.write(10);
/*      */           
/* 1127 */           WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1128 */           _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 1129 */           _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/* 1131 */           _jspx_th_c_005fwhen_005f9.setTest("${related_action==\"availMonitorGroupsforPage\"}");
/* 1132 */           int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 1133 */           if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */             for (;;) {
/* 1135 */               out.write("\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\n\t      ");
/*      */               
/* 1137 */               ArrayList selectedlist = new ArrayList();
/* 1138 */               if (request.getAttribute("selectedMultiMgs_list") != null)
/*      */               {
/* 1140 */                 selectedlist = (ArrayList)request.getAttribute("selectedMultiMgs_list");
/*      */               }
/*      */               
/* 1143 */               out.write(10);
/* 1144 */               out.write(9);
/* 1145 */               out.write(32);
/*      */               
/* 1147 */               org.apache.struts.taglib.logic.NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (org.apache.struts.taglib.logic.NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(org.apache.struts.taglib.logic.NotEmptyTag.class);
/* 1148 */               _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 1149 */               _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_c_005fwhen_005f9);
/*      */               
/* 1151 */               _jspx_th_logic_005fnotEmpty_005f2.setName("applications1");
/* 1152 */               int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 1153 */               if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                 for (;;) {
/* 1155 */                   out.write("\n\t     ");
/*      */                   
/* 1157 */                   IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 1158 */                   _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 1159 */                   _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                   
/* 1161 */                   _jspx_th_logic_005fiterate_005f2.setName("applications1");
/*      */                   
/* 1163 */                   _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */                   
/* 1165 */                   _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/*      */                   
/* 1167 */                   _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/* 1168 */                   int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 1169 */                   if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 1170 */                     ArrayList row = null;
/* 1171 */                     Integer j = null;
/* 1172 */                     if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 1173 */                       out = _jspx_page_context.pushBody();
/* 1174 */                       _jspx_th_logic_005fiterate_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1175 */                       _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                     }
/* 1177 */                     row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 1178 */                     j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                     for (;;) {
/* 1180 */                       out.write("\n\t              ");
/*      */                       
/* 1182 */                       String checked = "";
/* 1183 */                       String currentmg = (String)row.get(1);
/* 1184 */                       if (selectedlist.contains(currentmg))
/*      */                       {
/* 1186 */                         checked = "checked=\"true\"";
/*      */                       }
/*      */                       
/* 1189 */                       out.write("\n\t \t      <tr>\n\t\t\t<td width=\"2%\" align=\"left\">\n\t\t\t     <input type=\"checkbox\" name=\"selectedMultiMonitors\" ");
/* 1190 */                       out.print(checked);
/* 1191 */                       out.write(" value=\"");
/* 1192 */                       out.print((String)row.get(1));
/* 1193 */                       out.write("\" />\n\t\t\t</td>\n\t\t\t<td  >\n\t\t\t<span class=\"bodytext\">");
/* 1194 */                       out.print((String)row.get(0));
/* 1195 */                       out.write("</span>\n\t\t\t</td>\n\t             </tr>\n\n\t     ");
/* 1196 */                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 1197 */                       row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 1198 */                       j = (Integer)_jspx_page_context.findAttribute("j");
/* 1199 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 1202 */                     if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 1203 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 1206 */                   if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 1207 */                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                   }
/*      */                   
/* 1210 */                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 1211 */                   out.write(10);
/* 1212 */                   out.write(9);
/* 1213 */                   out.write(32);
/* 1214 */                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 1215 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1219 */               if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 1220 */                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */               }
/*      */               
/* 1223 */               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 1224 */               out.write("\n      \t</select>\n </table>\n");
/* 1225 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 1226 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1230 */           if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 1231 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */           }
/*      */           
/* 1234 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 1235 */           out.write(10);
/* 1236 */           out.write(10);
/* 1237 */           if (_jspx_meth_c_005fwhen_005f10(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */             return;
/* 1239 */           out.write(10);
/* 1240 */           out.write(10);
/* 1241 */           out.write(10);
/*      */           
/* 1243 */           WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1244 */           _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 1245 */           _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/* 1247 */           _jspx_th_c_005fwhen_005f12.setTest("${related_action==\"includePage\"}");
/* 1248 */           int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 1249 */           if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */             for (;;) {
/* 1251 */               out.write(10);
/*      */               
/* 1253 */               ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1254 */               _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1255 */               _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fwhen_005f12);
/* 1256 */               int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1257 */               if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                 for (;;) {
/* 1259 */                   out.write(10);
/*      */                   
/* 1261 */                   WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1262 */                   _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 1263 */                   _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                   
/* 1265 */                   _jspx_th_c_005fwhen_005f13.setTest("${not empty url && url!=\"\"}");
/* 1266 */                   int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 1267 */                   if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                     for (;;) {
/* 1269 */                       out.write(10);
/*      */                       
/* 1271 */                       String widgetHeight = (String)request.getAttribute("widgetHeight");
/*      */                       
/* 1273 */                       out.write("\n<iframe onload=\"frameonload(this,'");
/* 1274 */                       out.print(request.getParameter("widgetid"));
/* 1275 */                       out.write("')\" src=\"");
/* 1276 */                       if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fwhen_005f13, _jspx_page_context))
/*      */                         return;
/* 1278 */                       out.write("\" scrolling=\"yes\" align=\"center\" width=\"100%\" height=\"");
/* 1279 */                       out.print(widgetHeight);
/* 1280 */                       out.write("px\" border=\"0px\" frameborder=\"0\"> </iframe>\n");
/* 1281 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 1282 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1286 */                   if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 1287 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                   }
/*      */                   
/* 1290 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 1291 */                   out.write(10);
/*      */                   
/* 1293 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1294 */                   _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 1295 */                   _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f3);
/* 1296 */                   int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 1297 */                   if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                     for (;;) {
/* 1299 */                       out.write("\n<script>frameonload('noiframe','");
/* 1300 */                       out.print(request.getParameter("widgetid"));
/* 1301 */                       out.write("')</script>\n<table width=\"100%\" cellspacing=\"10\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t\t<tr><td valign=\"middle\" align=\"center\" class=\"txtGlobal errorMsg\"><span class=\"bodytext\">  ");
/* 1302 */                       out.print(FormatUtil.getString("am.mypage.widget.nodata.text"));
/* 1303 */                       out.write("<br/>\n\t\t\t ");
/*      */                       
/* 1305 */                       org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f0 = (org.apache.struts.taglib.logic.PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(org.apache.struts.taglib.logic.PresentTag.class);
/* 1306 */                       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1307 */                       _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                       
/* 1309 */                       _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN");
/* 1310 */                       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1311 */                       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                         for (;;) {
/* 1313 */                           out.write("\n\t\t\t ");
/* 1314 */                           out.print(FormatUtil.getString("am.mypage.widget.nodata.editparams.text", new String[] { "javascript:editWidget('" + request.getParameter("widgetid") + "')" }));
/* 1315 */                           out.write("\n\t\t\t ");
/* 1316 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1317 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1321 */                       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1322 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                       }
/*      */                       
/* 1325 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1326 */                       out.write("\n\t\t\t </span>\n\t\t\t </td></tr>\n\t     </table>\n");
/* 1327 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 1328 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1332 */                   if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 1333 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                   }
/*      */                   
/* 1336 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 1337 */                   out.write(10);
/* 1338 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1339 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1343 */               if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1344 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */               }
/*      */               
/* 1347 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1348 */               out.write(10);
/* 1349 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 1350 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1354 */           if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 1355 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */           }
/*      */           
/* 1358 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 1359 */           out.write(10);
/* 1360 */           out.write(10);
/*      */           
/* 1362 */           WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1363 */           _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 1364 */           _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/* 1366 */           _jspx_th_c_005fwhen_005f14.setTest("${related_action==\"includeTopologyMap\"}");
/* 1367 */           int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 1368 */           if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */             for (;;) {
/* 1370 */               out.write(10);
/*      */               
/* 1372 */               ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1373 */               _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1374 */               _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fwhen_005f14);
/* 1375 */               int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1376 */               if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                 for (;;) {
/* 1378 */                   out.write(10);
/*      */                   
/* 1380 */                   WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1381 */                   _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 1382 */                   _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                   
/* 1384 */                   _jspx_th_c_005fwhen_005f15.setTest("${not empty url && url!=\"\"}");
/* 1385 */                   int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 1386 */                   if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                     for (;;) {
/* 1388 */                       out.write(10);
/*      */                       
/* 1390 */                       String widgetHeight = (String)request.getAttribute("widgetHeight");
/*      */                       
/* 1392 */                       out.write("\n<iframe onload=\"frameonload(this,'");
/* 1393 */                       out.print(request.getParameter("widgetid"));
/* 1394 */                       out.write("')\" id=\"");
/* 1395 */                       out.print(request.getParameter("widgetid"));
/* 1396 */                       out.write("\" src=\"");
/* 1397 */                       if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fwhen_005f15, _jspx_page_context))
/*      */                         return;
/* 1399 */                       out.write("\" scrolling=\"yes\" align=\"center\" width=\"100%\" height=\"");
/* 1400 */                       out.print(widgetHeight);
/* 1401 */                       out.write("px\" border=\"0px\" frameborder=\"0\"> </iframe>\n");
/* 1402 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 1403 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1407 */                   if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 1408 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                   }
/*      */                   
/* 1411 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 1412 */                   out.write(10);
/*      */                   
/* 1414 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1415 */                   _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1416 */                   _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f4);
/* 1417 */                   int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1418 */                   if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                     for (;;) {
/* 1420 */                       out.write(10);
/*      */                       
/* 1422 */                       String widgetid = (String)request.getAttribute("widgetid");
/*      */                       
/* 1424 */                       out.write("\n<script>frameonload('noiframe','");
/* 1425 */                       out.print(request.getParameter("widgetid"));
/* 1426 */                       out.write("')</script>\n<table width=\"100%\" cellspacing=\"10\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t\t<tr><td valign=\"middle\" align=\"center\" class=\"txtGlobal errorMsg\"><span class=\"bodytext\">  ");
/* 1427 */                       out.print(FormatUtil.getString("am.mypage.widget.nomapdata.text"));
/* 1428 */                       out.write("<br/>\t\t\t \n\t\t\t ");
/* 1429 */                       out.write("\n\t\t\t");
/*      */                       
/* 1431 */                       org.apache.struts.taglib.logic.PresentTag _jspx_th_logic_005fpresent_005f1 = (org.apache.struts.taglib.logic.PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(org.apache.struts.taglib.logic.PresentTag.class);
/* 1432 */                       _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 1433 */                       _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fotherwise_005f3);
/*      */                       
/* 1435 */                       _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/* 1436 */                       int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 1437 */                       if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                         for (;;) {
/* 1439 */                           out.write("\n\t\t\t ");
/* 1440 */                           out.print(FormatUtil.getString("am.mypage.topologymap.editwidgettext.value", new String[] { "javascript:editWidget('" + request.getParameter("widgetid") + "')" }));
/* 1441 */                           out.write("\n\t\t\t ");
/* 1442 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1443 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 1447 */                       if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1448 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                       }
/*      */                       
/* 1451 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1452 */                       out.write("\t \n\t\t\t ");
/* 1453 */                       out.write("\n\t\t\t </span>\n\t\t\t </td></tr>\n\t     </table>\n");
/* 1454 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1455 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 1459 */                   if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1460 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                   }
/*      */                   
/* 1463 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1464 */                   out.write(10);
/* 1465 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1466 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1470 */               if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1471 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */               }
/*      */               
/* 1474 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1475 */               out.write(10);
/* 1476 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 1477 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1481 */           if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 1482 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */           }
/*      */           
/* 1485 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 1486 */           out.write(10);
/* 1487 */           out.write(10);
/* 1488 */           out.write(10);
/* 1489 */           out.write(10);
/*      */           
/* 1491 */           WhenTag _jspx_th_c_005fwhen_005f16 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1492 */           _jspx_th_c_005fwhen_005f16.setPageContext(_jspx_page_context);
/* 1493 */           _jspx_th_c_005fwhen_005f16.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/* 1495 */           _jspx_th_c_005fwhen_005f16.setTest("${related_action==\"publishviewkey\"}");
/* 1496 */           int _jspx_eval_c_005fwhen_005f16 = _jspx_th_c_005fwhen_005f16.doStartTag();
/* 1497 */           if (_jspx_eval_c_005fwhen_005f16 != 0) {
/*      */             for (;;) {
/* 1499 */               out.write(10);
/* 1500 */               out.write(10);
/* 1501 */               if (_jspx_meth_c_005fif_005f12(_jspx_th_c_005fwhen_005f16, _jspx_page_context))
/*      */                 return;
/* 1503 */               out.write(10);
/*      */               
/* 1505 */               String hostname = "localhost:9090";
/*      */               try
/*      */               {
/* 1508 */                 hostname = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
/*      */               }
/*      */               catch (Exception e) {
/* 1511 */                 e.printStackTrace();
/*      */               }
/*      */               
/* 1514 */               out.write("\n<table width=\"100%\" cellspacing=\"1\" cellpadding=\"2\" border=\"0\" bgcolor=\"#ffffff\" align=\"right\">\n<tr>\n<td class=\"bodytext\">");
/* 1515 */               out.print(FormatUtil.getString("am.mypage.dashboard.public.view.text"));
/* 1516 */               out.write("\n</td></tr>\n<tr>\n<td>\n\n<div id=\"txtbx\"><textarea cols=\"50\" rows=\"4\" name=\"textlink\"><iframe src=\"");
/* 1517 */               out.print(hostname);
/* 1518 */               out.write("publishPage.do?method=getPublishedDashboard&pagekey=");
/* 1519 */               if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fwhen_005f16, _jspx_page_context))
/*      */                 return;
/* 1521 */               if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fwhen_005f16, _jspx_page_context))
/*      */                 return;
/* 1523 */               out.write("\" align=\"center\" height=\"100%\" width=\"100%\" border=\"0\" frameborder=\"0\"> </iframe></textarea></div>\n\n</td>\n</tr>\n<tr>\n<td class=\"bodytext\">\n");
/* 1524 */               out.print(FormatUtil.getString("am.mypage.copylink.text"));
/* 1525 */               out.write("\n</td>\n</tr>\n</table>\n");
/* 1526 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f16.doAfterBody();
/* 1527 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1531 */           if (_jspx_th_c_005fwhen_005f16.doEndTag() == 5) {
/* 1532 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16); return;
/*      */           }
/*      */           
/* 1535 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f16);
/* 1536 */           out.write(10);
/* 1537 */           out.write(10);
/*      */           
/* 1539 */           WhenTag _jspx_th_c_005fwhen_005f17 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1540 */           _jspx_th_c_005fwhen_005f17.setPageContext(_jspx_page_context);
/* 1541 */           _jspx_th_c_005fwhen_005f17.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/* 1543 */           _jspx_th_c_005fwhen_005f17.setTest("${related_action==\"bookmarkform\"}");
/* 1544 */           int _jspx_eval_c_005fwhen_005f17 = _jspx_th_c_005fwhen_005f17.doStartTag();
/* 1545 */           if (_jspx_eval_c_005fwhen_005f17 != 0) {
/*      */             for (;;) {
/* 1547 */               out.write(10);
/* 1548 */               out.write("\n <div style=\"overflow: auto; height:280px;width: 500px;\" >\n  <form name=\"urlform\" method=\"post\"  action=\"/MyPage.do?method=saveBookMark\">\n  <input type=\"hidden\" name=\"widgetid\" value=\"");
/* 1549 */               if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fwhen_005f17, _jspx_page_context))
/*      */                 return;
/* 1551 */               out.write("\" />\n  <input type=\"hidden\" name=\"bookmarkid\" value=\"");
/* 1552 */               if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fwhen_005f17, _jspx_page_context))
/*      */                 return;
/* 1554 */               out.write("\" />\n <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"custom-tb-bg\">\n \t<tr>\n \t\t<td class=\"custom-innertb-bg\">\n\n \t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n \t\t\t\t<tr>\n \t\t\t\t\t<td class=\"custom-td-bg\" height=\"30\" colspan=\"3\" align=\"left\">\n \t\t\t\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n \t\t\t\t\t<tr>\n \t\t\t\t\t<td width=\"70%\" class=\"custom-td-bg\" align=\"left\">\n \t\t\t\t\t");
/*      */               
/* 1556 */               IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1557 */               _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 1558 */               _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fwhen_005f17);
/*      */               
/* 1560 */               _jspx_th_c_005fif_005f13.setTest("${editBookmark!=\"true\"}");
/* 1561 */               int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 1562 */               if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                 for (;;) {
/* 1564 */                   out.write("\n \t\t\t\t\t");
/* 1565 */                   out.print(FormatUtil.getString("am.mypage.addbookmark.text"));
/* 1566 */                   out.write("\n \t\t\t\t\t");
/* 1567 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 1568 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1572 */               if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 1573 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */               }
/*      */               
/* 1576 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1577 */               out.write("\n \t\t\t\t\t");
/*      */               
/* 1579 */               IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1580 */               _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 1581 */               _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fwhen_005f17);
/*      */               
/* 1583 */               _jspx_th_c_005fif_005f14.setTest("${editBookmark==\"true\"}");
/* 1584 */               int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 1585 */               if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                 for (;;) {
/* 1587 */                   out.write("\n\t\t\t\t\t");
/* 1588 */                   out.print(FormatUtil.getString("am.mypage.editbookmark.text"));
/* 1589 */                   out.write("\n \t\t\t\t\t");
/* 1590 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 1591 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1595 */               if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 1596 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */               }
/*      */               
/* 1599 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 1600 */               out.write("\n \t\t\t\t\t</td>\n \t\t\t\t\t<td width=\"30%\" align=\"right\">\n \t\t\t\t\t<a onclick=\"document.getElementById('widgetBokmarks_DivContainer').style.display='none';\" title=\"Close\" href=\"javascript:void(0)\"><img border=\"0\" src=\"/images/cross.gif\"/></a> &nbsp;\n \t\t\t\t\t</td>\n \t\t\t\t\t</tr>\n \t\t\t\t\t</table>\n \t\t\t\t\t</td>\n \t\t\t\t</tr>\n \t\t\t\t<tr>\n \t\t\t\t\t<td width=\"1%\"></td>\n \t\t\t\t\t<td width=\"98%\">\n \t\t\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"custom-td-whitebdr\">\n \t\t\t\t\t\t <tr>\n \t\t\t\t\t\t  <td>\n \t\t\t\t\t\t  <div style=\"overflow: auto; height:190px;\">\n \t\t\t\t\t\t   <table cellpadding=\"2\" cellspacing=\"3\">\n \t\t\t\t\t\t\t  <tr class=\"bodytext\">\n \t\t\t\t\t\t\t   \t<td align=\"left\"><b>");
/* 1601 */               out.print(FormatUtil.getString("Display Name"));
/* 1602 */               out.write("</b> :\n \t\t\t\t\t\t\t   \t</td>\n \t\t\t\t\t\t\t   \t<td>\n \t\t\t\t\t\t\t   \t<input type=\"textbox\" name=\"displayName\" class=\"formtext\" size=\"35\" value=\"");
/* 1603 */               if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fwhen_005f17, _jspx_page_context))
/*      */                 return;
/* 1605 */               out.write("\"/>\n \t\t\t\t\t\t\t   \t</td>\n \t\t\t\t\t\t\t   </tr>\n \t\t\t\t\t\t\t   <tr class=\"bodytext\">\n \t\t\t\t\t\t\t   \t<td  align=\"left\"><b>");
/* 1606 */               out.print(FormatUtil.getString("URL"));
/* 1607 */               out.write("</b> :\n \t\t\t\t\t\t\t   \t</td>\n \t\t\t\t\t\t\t   \t<td>\n \t\t\t\t\t\t\t   \t<input type=\"textbox\" name=\"url\" class=\"formtext\" size=\"35\" value=\"");
/* 1608 */               if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fwhen_005f17, _jspx_page_context))
/*      */                 return;
/* 1610 */               out.write("\"/>\n \t\t\t\t\t\t\t   \t</td>\n \t\t\t\t\t\t\t   </tr>\n \t\t\t\t\t\t\t   <tr class=\"bodytext\">\n\t\t\t\t\t\t\t   \t<td  align=\"left\"><b>");
/* 1611 */               out.print(FormatUtil.getString("Description"));
/* 1612 */               out.write("</b> :\n\t\t\t\t\t\t\t   \t</td>\n\t\t\t\t\t\t\t   \t<td>\n\t\t\t\t\t\t\t   \t<textarea name=\"description\"   style=\"width: 350px;\" rows=\"4\"  >");
/* 1613 */               if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fwhen_005f17, _jspx_page_context))
/*      */                 return;
/* 1615 */               out.write("</textarea>\n\t\t\t\t\t\t\t   \t</td>\n\n \t\t\t\t\t\t\t   </tr>\n \t\t\t\t\t\t\t   <tr>\n \t\t\t\t\t\t\t   <td colspan=\"2\" align=\"center\"><input class=\"buttons\" type=\"button\" onclick=\"javascript:saveBookmark()\" name=\"Save\" value=\"");
/* 1616 */               out.print(FormatUtil.getString("am.webclient.common.save.text"));
/* 1617 */               out.write("\"/>&nbsp;\n \t\t\t\t\t\t\t   <input class=\"buttons\" type=\"button\" onclick=\"document.getElementById('widgetBokmarks_DivContainer').style.display='none';\" name=\"Cancel\" value=\"");
/* 1618 */               out.print(FormatUtil.getString("am.mypage.dashboard.cancel.button.text"));
/* 1619 */               out.write("\"/>\n \t\t\t\t\t\t\t   </td>\n \t\t\t\t\t\t\t   </tr>\n \t\t\t\t\t\t    </table>\n \t\t\t\t\t\t    </div>\n \t\t\t\t\t\t   </td>\n \t\t\t\t\t\t  </tr>\n \t\t\t\t\t\t</table>\n \t\t\t\t\t</td>\n \t\t\t\t\t<td width=\"1%\"></td>\n \t\t\t\t</tr>\n\n \t\t\t</table>\n\n \t\t</td>\n \t</tr>\n </table>\n  </form>\n</div>\n");
/* 1620 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f17.doAfterBody();
/* 1621 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1625 */           if (_jspx_th_c_005fwhen_005f17.doEndTag() == 5) {
/* 1626 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17); return;
/*      */           }
/*      */           
/* 1629 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f17);
/* 1630 */           out.write(10);
/* 1631 */           out.write(10);
/*      */           
/* 1633 */           WhenTag _jspx_th_c_005fwhen_005f18 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1634 */           _jspx_th_c_005fwhen_005f18.setPageContext(_jspx_page_context);
/* 1635 */           _jspx_th_c_005fwhen_005f18.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/* 1637 */           _jspx_th_c_005fwhen_005f18.setTest("${related_action==\"widgetdescription\"}");
/* 1638 */           int _jspx_eval_c_005fwhen_005f18 = _jspx_th_c_005fwhen_005f18.doStartTag();
/* 1639 */           if (_jspx_eval_c_005fwhen_005f18 != 0) {
/*      */             for (;;) {
/* 1641 */               out.write("\n<div style=\"overflow: auto; height:220px;width: 500px;\"   >\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"custom-tb-bg\">\n\t<tr  >\n\t\t<td class=\"custom-innertb-bg\">\n\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t<tr>\n\n\t\t\t\t<td class=\"custom-td-bg\" height=\"30\" colspan=\"3\" align=\"left\">\n\t\t\t\t \t\t\t\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t\t \t\t\t\t\t<tr>\n\t\t\t\t \t\t\t\t\t<td width=\"70%\" class=\"custom-td-bg\" align=\"left\">\n\t\t\t\t \t\t\t\t\t");
/* 1642 */               out.print(FormatUtil.getString("am.webclient.mypage.widget.properties"));
/* 1643 */               out.write("\n\t\t\t\t \t\t\t\t\t</td>\n\t\t\t\t \t\t\t\t\t<td width=\"30%\" align=\"right\">\n\t\t\t\t \t\t\t\t\t<a onclick=\"javascript:closeDialog()\" class=\"closeButton\" href=\"javascript:void(0)\"></a> &nbsp;\n\t\t\t\t \t\t\t\t\t</td>\n\t\t\t\t \t\t\t\t\t</tr>\n\t\t\t\t \t\t\t\t\t</table>\n \t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"1%\"></td>\n\t\t\t\t\t<td width=\"98%\">\n\t\t\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"custom-td-whitebdr\">\n\t\t\t\t\t\t <tr>\n\t\t\t\t\t\t  <td>\n\t\t\t\t\t\t  <div style=\"overflow: auto; height:150px;\">\n\t\t\t\t\t\t   <table cellpadding=\"2\" cellspacing=\"3\">\n\t\t\t\t\t\t\t");
/*      */               
/* 1645 */               IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1646 */               _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 1647 */               _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fwhen_005f18);
/*      */               
/* 1649 */               _jspx_th_c_005fif_005f15.setTest("${not empty resourceid}");
/* 1650 */               int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 1651 */               if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                 for (;;) {
/* 1653 */                   out.write("\n\t\t\t\t\t\t\t  <tr class=\"bodytext\">\n\t\t\t\t\t\t\t   <td  colspan=\"2\" align=\"left\"><b>");
/* 1654 */                   out.print(FormatUtil.getString("am.mypage.monitorname.text"));
/* 1655 */                   out.write("</b> : <a class=\"staticlinks\"  href=\"/showresource.do?resourceid=");
/* 1656 */                   if (_jspx_meth_c_005fout_005f37(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                     return;
/* 1658 */                   out.write("&method=showResourceForResourceID\">");
/* 1659 */                   if (_jspx_meth_c_005fout_005f38(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                     return;
/* 1661 */                   out.write("</a></td>\n\t\t\t\t\t\t\t   </tr>\n\t\t\t\t\t\t\t   <tr class=\"bodytext\">\n\t\t\t\t\t\t\t   <td  colspan=\"2\" align=\"left\"><b>");
/* 1662 */                   out.print(FormatUtil.getString("am.webclient.mypage.widget.prerformancemetric"));
/* 1663 */                   out.write("</b> : ");
/* 1664 */                   if (_jspx_meth_c_005fout_005f39(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                     return;
/* 1666 */                   out.write("</td>\n\t\t\t\t\t\t\t   </tr>\n\t\t\t\t\t\t\t  ");
/* 1667 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 1668 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1672 */               if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 1673 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */               }
/*      */               
/* 1676 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 1677 */               out.write("\n\t\t\t\t\t\t\t  <tr class=\"bodytext\">\n\t\t\t\t\t\t\t\t<td  colspan=\"2\" align=\"left\"><b>");
/* 1678 */               out.print(FormatUtil.getString("am.mypage.editwidget.widgettype.text"));
/* 1679 */               out.write(" </b> : ");
/* 1680 */               if (_jspx_meth_c_005fout_005f40(_jspx_th_c_005fwhen_005f18, _jspx_page_context))
/*      */                 return;
/* 1682 */               out.write("</td>\n\t\t\t\t\t\t\t   </tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td class=\"bodytext\">\n\t\t\t\t\t\t\t");
/*      */               
/* 1684 */               SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1685 */               _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 1686 */               _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fwhen_005f18);
/*      */               
/* 1688 */               _jspx_th_c_005fset_005f10.setVar("defaultvalue");
/* 1689 */               int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 1690 */               if (_jspx_eval_c_005fset_005f10 != 0) {
/* 1691 */                 if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1692 */                   out = _jspx_page_context.pushBody();
/* 1693 */                   _jspx_th_c_005fset_005f10.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 1694 */                   _jspx_th_c_005fset_005f10.doInitBody();
/*      */                 }
/*      */                 for (;;) {
/* 1697 */                   out.print(FormatUtil.getString("am.mypage.editwidget.default.description"));
/* 1698 */                   int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 1699 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/* 1702 */                 if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1703 */                   out = _jspx_page_context.popBody();
/*      */                 }
/*      */               }
/* 1706 */               if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 1707 */                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10); return;
/*      */               }
/*      */               
/* 1710 */               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 1711 */               out.write("\n\t\t\t\t\t\t\t <b>");
/* 1712 */               out.print(FormatUtil.getString("Description"));
/* 1713 */               out.write("</b> : ");
/* 1714 */               if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fwhen_005f18, _jspx_page_context))
/*      */                 return;
/* 1716 */               out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t    </table>\n\t\t\t\t\t\t    </div>\n\t\t\t\t\t\t   </td>\n\t\t\t\t\t\t  </tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"1%\"></td>\n\t\t\t\t</tr>\n\n\t\t\t</table>\n\n\t\t</td>\n\t</tr>\n</table>\n</div>\n");
/* 1717 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f18.doAfterBody();
/* 1718 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 1722 */           if (_jspx_th_c_005fwhen_005f18.doEndTag() == 5) {
/* 1723 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18); return;
/*      */           }
/*      */           
/* 1726 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f18);
/* 1727 */           out.write(10);
/* 1728 */           out.write(10);
/* 1729 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 1730 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1734 */       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 1735 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */       }
/*      */       else {
/* 1738 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 1739 */         out.write(10);
/* 1740 */         out.write(10);
/*      */       }
/* 1742 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 1743 */         out = _jspx_out;
/* 1744 */         if ((out != null) && (out.getBufferSize() != 0))
/* 1745 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 1746 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 1749 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1755 */     PageContext pageContext = _jspx_page_context;
/* 1756 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1758 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1759 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1760 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1762 */     _jspx_th_c_005fif_005f0.setTest("${MyPageForm.pageType!=\"mgtemplate\"}");
/* 1763 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1764 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 1766 */         out.write("\n<select name=\"selectedMG\"  class=\"formtext\" style=\"width:150px\">\n");
/* 1767 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1768 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1772 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1773 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1774 */       return true;
/*      */     }
/* 1776 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1777 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1782 */     PageContext pageContext = _jspx_page_context;
/* 1783 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1785 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1786 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1787 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 1789 */     _jspx_th_c_005fif_005f1.setTest("${MyPageForm.pageType==\"mgtemplate\"}");
/* 1790 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1791 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 1793 */         out.write("\n<select name=\"selectedMG\" disabled=\"true\" class=\"formtext\" style=\"width:150px\">\n");
/* 1794 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1795 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1799 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1800 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1801 */       return true;
/*      */     }
/* 1803 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1804 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1809 */     PageContext pageContext = _jspx_page_context;
/* 1810 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1812 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.get(ForEachTag.class);
/* 1813 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 1814 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1816 */     _jspx_th_c_005fforEach_005f0.setBegin("0");
/*      */     
/* 1818 */     _jspx_th_c_005fforEach_005f0.setEnd("4");
/*      */     
/* 1820 */     _jspx_th_c_005fforEach_005f0.setVar("singleRow");
/*      */     
/* 1822 */     _jspx_th_c_005fforEach_005f0.setItems("${MyPageForm.availMultiMonitors}");
/*      */     
/* 1824 */     _jspx_th_c_005fforEach_005f0.setVarStatus("linecount");
/* 1825 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 1827 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 1828 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 1830 */           out.write("\n   \t\t        \t\t\t<tr class=\"bodytext\">\n\t\t\t\t\t\t\t\t<td width=\"5%\"></td>\n\t\t\t\t\t\t\t\t<td width=\"95%\" colspan=\"2\">*&nbsp;<em>");
/* 1831 */           if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1832 */             return true;
/* 1833 */           out.write("<em></td>\n\t\t        \t\t\t</tr>\n\t        \t\t");
/* 1834 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 1835 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1839 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 1840 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1843 */         int tmp214_213 = 0; int[] tmp214_211 = _jspx_push_body_count_c_005fforEach_005f0; int tmp216_215 = tmp214_211[tmp214_213];tmp214_211[tmp214_213] = (tmp216_215 - 1); if (tmp216_215 <= 0) break;
/* 1844 */         out = _jspx_page_context.popBody(); }
/* 1845 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 1847 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 1848 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 1850 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1855 */     PageContext pageContext = _jspx_page_context;
/* 1856 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1858 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1859 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 1860 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1862 */     _jspx_th_c_005fout_005f0.setValue("${singleRow[1]}");
/* 1863 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 1864 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 1865 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1866 */       return true;
/*      */     }
/* 1868 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 1869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1874 */     PageContext pageContext = _jspx_page_context;
/* 1875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1877 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1878 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1879 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 1881 */     _jspx_th_c_005fforEach_005f1.setVar("singleRow");
/*      */     
/* 1883 */     _jspx_th_c_005fforEach_005f1.setItems("${MyPageForm.availMultiMonitors}");
/*      */     
/* 1885 */     _jspx_th_c_005fforEach_005f1.setVarStatus("linecount");
/* 1886 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1888 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1889 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1891 */           out.write("\n   \t\t        \t\t\t<tr class=\"bodytext\">\n\t\t\t\t\t\t\t\t<td width=\"5%\"></td>\n\t\t\t\t\t\t\t\t<td width=\"95%\" colspan=\"2\">*&nbsp;<em>");
/* 1892 */           if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1893 */             return true;
/* 1894 */           out.write("<em></td>\n\t\t        \t\t\t</tr>\n\t        \t\t");
/* 1895 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1896 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1900 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 1901 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1904 */         int tmp198_197 = 0; int[] tmp198_195 = _jspx_push_body_count_c_005fforEach_005f1; int tmp200_199 = tmp198_195[tmp198_197];tmp198_195[tmp198_197] = (tmp200_199 - 1); if (tmp200_199 <= 0) break;
/* 1905 */         out = _jspx_page_context.popBody(); }
/* 1906 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 1908 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 1909 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1911 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1916 */     PageContext pageContext = _jspx_page_context;
/* 1917 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1919 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1920 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 1921 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1923 */     _jspx_th_c_005fout_005f1.setValue("${singleRow[1]}");
/* 1924 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 1925 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 1926 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1927 */       return true;
/*      */     }
/* 1929 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 1930 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1935 */     PageContext pageContext = _jspx_page_context;
/* 1936 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1938 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1939 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 1940 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 1942 */     _jspx_th_c_005fout_005f2.setValue("${singleRow[0]}");
/* 1943 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 1944 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 1945 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1946 */       return true;
/*      */     }
/* 1948 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 1949 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1954 */     PageContext pageContext = _jspx_page_context;
/* 1955 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1957 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1958 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 1959 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 1961 */     _jspx_th_c_005fout_005f3.setValue("${singleRow[1]}");
/* 1962 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 1963 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 1964 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1965 */       return true;
/*      */     }
/* 1967 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 1968 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f3(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1973 */     PageContext pageContext = _jspx_page_context;
/* 1974 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1976 */     ForEachTag _jspx_th_c_005fforEach_005f3 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.get(ForEachTag.class);
/* 1977 */     _jspx_th_c_005fforEach_005f3.setPageContext(_jspx_page_context);
/* 1978 */     _jspx_th_c_005fforEach_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 1980 */     _jspx_th_c_005fforEach_005f3.setBegin("0");
/*      */     
/* 1982 */     _jspx_th_c_005fforEach_005f3.setEnd("4");
/*      */     
/* 1984 */     _jspx_th_c_005fforEach_005f3.setVar("singleRow");
/*      */     
/* 1986 */     _jspx_th_c_005fforEach_005f3.setItems("${MyPageForm.availMultiMonitors}");
/*      */     
/* 1988 */     _jspx_th_c_005fforEach_005f3.setVarStatus("linecount");
/* 1989 */     int[] _jspx_push_body_count_c_005fforEach_005f3 = { 0 };
/*      */     try {
/* 1991 */       int _jspx_eval_c_005fforEach_005f3 = _jspx_th_c_005fforEach_005f3.doStartTag();
/* 1992 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f3 != 0) {
/*      */         for (;;) {
/* 1994 */           out.write("\n   \t\t        \t\t\t<tr class=\"bodytext\">\n\t\t\t\t\t\t\t\t<td width=\"5%\"></td>\n\t\t\t\t\t\t\t\t<td width=\"95%\" colspan=\"2\">*&nbsp;<em>");
/* 1995 */           if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f3))
/* 1996 */             return true;
/* 1997 */           out.write("<em></td>\n\t\t        \t\t\t</tr>\n\t        \t\t");
/* 1998 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f3.doAfterBody();
/* 1999 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2003 */       if (_jspx_th_c_005fforEach_005f3.doEndTag() == 5)
/* 2004 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2007 */         int tmp214_213 = 0; int[] tmp214_211 = _jspx_push_body_count_c_005fforEach_005f3; int tmp216_215 = tmp214_211[tmp214_213];tmp214_211[tmp214_213] = (tmp216_215 - 1); if (tmp216_215 <= 0) break;
/* 2008 */         out = _jspx_page_context.popBody(); }
/* 2009 */       _jspx_th_c_005fforEach_005f3.doCatch(_jspx_exception);
/*      */     } finally {
/* 2011 */       _jspx_th_c_005fforEach_005f3.doFinally();
/* 2012 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f3);
/*      */     }
/* 2014 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f3) throws Throwable
/*      */   {
/* 2019 */     PageContext pageContext = _jspx_page_context;
/* 2020 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2022 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2023 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 2024 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f3);
/*      */     
/* 2026 */     _jspx_th_c_005fout_005f4.setValue("${singleRow[1]}");
/* 2027 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 2028 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 2029 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2030 */       return true;
/*      */     }
/* 2032 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 2033 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f4(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2038 */     PageContext pageContext = _jspx_page_context;
/* 2039 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2041 */     ForEachTag _jspx_th_c_005fforEach_005f4 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2042 */     _jspx_th_c_005fforEach_005f4.setPageContext(_jspx_page_context);
/* 2043 */     _jspx_th_c_005fforEach_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 2045 */     _jspx_th_c_005fforEach_005f4.setVar("singleRow");
/*      */     
/* 2047 */     _jspx_th_c_005fforEach_005f4.setItems("${MyPageForm.availMultiMonitors}");
/*      */     
/* 2049 */     _jspx_th_c_005fforEach_005f4.setVarStatus("linecount");
/* 2050 */     int[] _jspx_push_body_count_c_005fforEach_005f4 = { 0 };
/*      */     try {
/* 2052 */       int _jspx_eval_c_005fforEach_005f4 = _jspx_th_c_005fforEach_005f4.doStartTag();
/* 2053 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f4 != 0) {
/*      */         for (;;) {
/* 2055 */           out.write("\n   \t\t        \t\t\t<tr class=\"bodytext\">\n\t\t\t\t\t\t\t\t<td width=\"5%\"></td>\n\t\t\t\t\t\t\t\t<td width=\"95%\" colspan=\"2\">*&nbsp;<em>");
/* 2056 */           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f4))
/* 2057 */             return true;
/* 2058 */           out.write("<em></td>\n\t\t        \t\t\t</tr>\n\t        \t\t");
/* 2059 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f4.doAfterBody();
/* 2060 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2064 */       if (_jspx_th_c_005fforEach_005f4.doEndTag() == 5)
/* 2065 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2068 */         int tmp198_197 = 0; int[] tmp198_195 = _jspx_push_body_count_c_005fforEach_005f4; int tmp200_199 = tmp198_195[tmp198_197];tmp198_195[tmp198_197] = (tmp200_199 - 1); if (tmp200_199 <= 0) break;
/* 2069 */         out = _jspx_page_context.popBody(); }
/* 2070 */       _jspx_th_c_005fforEach_005f4.doCatch(_jspx_exception);
/*      */     } finally {
/* 2072 */       _jspx_th_c_005fforEach_005f4.doFinally();
/* 2073 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f4);
/*      */     }
/* 2075 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f4) throws Throwable
/*      */   {
/* 2080 */     PageContext pageContext = _jspx_page_context;
/* 2081 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2083 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2084 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 2085 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f4);
/*      */     
/* 2087 */     _jspx_th_c_005fout_005f5.setValue("${singleRow[1]}");
/* 2088 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 2089 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 2090 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2091 */       return true;
/*      */     }
/* 2093 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 2094 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f5) throws Throwable
/*      */   {
/* 2099 */     PageContext pageContext = _jspx_page_context;
/* 2100 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2102 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2103 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 2104 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f5);
/*      */     
/* 2106 */     _jspx_th_c_005fout_005f6.setValue("${attr[0]}");
/* 2107 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 2108 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 2109 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2110 */       return true;
/*      */     }
/* 2112 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 2113 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f5) throws Throwable
/*      */   {
/* 2118 */     PageContext pageContext = _jspx_page_context;
/* 2119 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2121 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2122 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 2123 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f5);
/*      */     
/* 2125 */     _jspx_th_c_005fout_005f7.setValue("${attr[1]}");
/* 2126 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 2127 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 2128 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2129 */       return true;
/*      */     }
/* 2131 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 2132 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f5) throws Throwable
/*      */   {
/* 2137 */     PageContext pageContext = _jspx_page_context;
/* 2138 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2140 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2141 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 2142 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f5);
/*      */     
/* 2144 */     _jspx_th_c_005fout_005f8.setValue("${attr[2]}");
/* 2145 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 2146 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 2147 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2148 */       return true;
/*      */     }
/* 2150 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2151 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fforEach_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 2156 */     PageContext pageContext = _jspx_page_context;
/* 2157 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2159 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.get(SetTag.class);
/* 2160 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2161 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f6);
/*      */     
/* 2163 */     _jspx_th_c_005fset_005f0.setVar("key");
/* 2164 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2165 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2166 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2167 */       return true;
/*      */     }
/* 2169 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 2170 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(JspTag _jspx_th_c_005fforEach_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 2175 */     PageContext pageContext = _jspx_page_context;
/* 2176 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2178 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2179 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2180 */     _jspx_th_c_005fif_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f6);
/*      */     
/* 2182 */     _jspx_th_c_005fif_005f7.setTest("${empty monitorselected}");
/* 2183 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2184 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 2186 */         out.write("\n\t\t\t          ");
/* 2187 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/* 2188 */           return true;
/* 2189 */         out.write("\n\t\t\t          ");
/* 2190 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/* 2191 */           return true;
/* 2192 */         out.write("\n\t\t\t\t  ");
/* 2193 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2194 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2198 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2199 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2200 */       return true;
/*      */     }
/* 2202 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2203 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 2208 */     PageContext pageContext = _jspx_page_context;
/* 2209 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2211 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2212 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2213 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2215 */     _jspx_th_c_005fset_005f1.setVar("selectmonitorname");
/* 2216 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2217 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 2218 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2219 */         out = _jspx_page_context.pushBody();
/* 2220 */         _jspx_push_body_count_c_005fforEach_005f6[0] += 1;
/* 2221 */         _jspx_th_c_005fset_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2222 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2225 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/* 2226 */           return true;
/* 2227 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 2228 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2231 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2232 */         out = _jspx_page_context.popBody();
/* 2233 */         _jspx_push_body_count_c_005fforEach_005f6[0] -= 1;
/*      */       }
/*      */     }
/* 2236 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2237 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 2238 */       return true;
/*      */     }
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 2241 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 2246 */     PageContext pageContext = _jspx_page_context;
/* 2247 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2249 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2250 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 2251 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 2253 */     _jspx_th_c_005fout_005f9.setValue("${monitor[1]}");
/* 2254 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 2255 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 2256 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2257 */       return true;
/*      */     }
/* 2259 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2260 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 2265 */     PageContext pageContext = _jspx_page_context;
/* 2266 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2268 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2269 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 2270 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 2272 */     _jspx_th_c_005fset_005f2.setVar("monitorselected");
/* 2273 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 2274 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 2275 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2276 */         out = _jspx_page_context.pushBody();
/* 2277 */         _jspx_push_body_count_c_005fforEach_005f6[0] += 1;
/* 2278 */         _jspx_th_c_005fset_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2279 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2282 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fset_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/* 2283 */           return true;
/* 2284 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 2285 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2288 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2289 */         out = _jspx_page_context.popBody();
/* 2290 */         _jspx_push_body_count_c_005fforEach_005f6[0] -= 1;
/*      */       }
/*      */     }
/* 2293 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 2294 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 2295 */       return true;
/*      */     }
/* 2297 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 2298 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 2303 */     PageContext pageContext = _jspx_page_context;
/* 2304 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2306 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2307 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 2308 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 2310 */     _jspx_th_c_005fout_005f10.setValue("${monitor[0]}");
/* 2311 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 2312 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 2313 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2314 */       return true;
/*      */     }
/* 2316 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 2317 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(JspTag _jspx_th_c_005fforEach_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 2322 */     PageContext pageContext = _jspx_page_context;
/* 2323 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2325 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2326 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2327 */     _jspx_th_c_005fif_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f6);
/*      */     
/* 2329 */     _jspx_th_c_005fif_005f8.setTest("${MyPageForm.selectedMonitor==monitor[0]}");
/* 2330 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2331 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 2333 */         out.write("\n\t\t\t\t  ");
/* 2334 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/* 2335 */           return true;
/* 2336 */         out.write("\n\t\t\t\t  ");
/* 2337 */         if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fif_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/* 2338 */           return true;
/* 2339 */         out.write("\n\t\t\t\t  ");
/* 2340 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2341 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2345 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2346 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2347 */       return true;
/*      */     }
/* 2349 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2350 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 2355 */     PageContext pageContext = _jspx_page_context;
/* 2356 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2358 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2359 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 2360 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2362 */     _jspx_th_c_005fset_005f3.setVar("selectmonitorname");
/* 2363 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 2364 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 2365 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2366 */         out = _jspx_page_context.pushBody();
/* 2367 */         _jspx_push_body_count_c_005fforEach_005f6[0] += 1;
/* 2368 */         _jspx_th_c_005fset_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2369 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2372 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fset_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/* 2373 */           return true;
/* 2374 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 2375 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2378 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2379 */         out = _jspx_page_context.popBody();
/* 2380 */         _jspx_push_body_count_c_005fforEach_005f6[0] -= 1;
/*      */       }
/*      */     }
/* 2383 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 2384 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 2385 */       return true;
/*      */     }
/* 2387 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 2388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 2393 */     PageContext pageContext = _jspx_page_context;
/* 2394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2396 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2397 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 2398 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 2400 */     _jspx_th_c_005fout_005f11.setValue("${monitor[1]}");
/* 2401 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 2402 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 2403 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2404 */       return true;
/*      */     }
/* 2406 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 2407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 2412 */     PageContext pageContext = _jspx_page_context;
/* 2413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2415 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2416 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 2417 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 2419 */     _jspx_th_c_005fset_005f4.setVar("monitorselected");
/* 2420 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 2421 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 2422 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 2423 */         out = _jspx_page_context.pushBody();
/* 2424 */         _jspx_push_body_count_c_005fforEach_005f6[0] += 1;
/* 2425 */         _jspx_th_c_005fset_005f4.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2426 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2429 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fset_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f6))
/* 2430 */           return true;
/* 2431 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 2432 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2435 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 2436 */         out = _jspx_page_context.popBody();
/* 2437 */         _jspx_push_body_count_c_005fforEach_005f6[0] -= 1;
/*      */       }
/*      */     }
/* 2440 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 2441 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 2442 */       return true;
/*      */     }
/* 2444 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 2445 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f6) throws Throwable
/*      */   {
/* 2450 */     PageContext pageContext = _jspx_page_context;
/* 2451 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2453 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2454 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 2455 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 2457 */     _jspx_th_c_005fout_005f12.setValue("${monitor[0]}");
/* 2458 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 2459 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 2460 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2461 */       return true;
/*      */     }
/* 2463 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 2464 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2469 */     PageContext pageContext = _jspx_page_context;
/* 2470 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2472 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2473 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 2474 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2476 */     _jspx_th_c_005fout_005f13.setValue("${childParentid}");
/* 2477 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 2478 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 2479 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2480 */       return true;
/*      */     }
/* 2482 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 2483 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2488 */     PageContext pageContext = _jspx_page_context;
/* 2489 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2491 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2492 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 2493 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2495 */     _jspx_th_c_005fout_005f14.setValue("${monitorselected}");
/* 2496 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 2497 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 2498 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2499 */       return true;
/*      */     }
/* 2501 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 2502 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2507 */     PageContext pageContext = _jspx_page_context;
/* 2508 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2510 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2511 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 2512 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2514 */     _jspx_th_c_005fout_005f15.setValue("${secondlevelattribute}");
/* 2515 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 2516 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 2517 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 2518 */       return true;
/*      */     }
/* 2520 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 2521 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2526 */     PageContext pageContext = _jspx_page_context;
/* 2527 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2529 */     ForEachTag _jspx_th_c_005fforEach_005f7 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2530 */     _jspx_th_c_005fforEach_005f7.setPageContext(_jspx_page_context);
/* 2531 */     _jspx_th_c_005fforEach_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 2533 */     _jspx_th_c_005fforEach_005f7.setVar("monitor");
/*      */     
/* 2535 */     _jspx_th_c_005fforEach_005f7.setItems("${MyPageForm.availMonitors}");
/*      */     
/* 2537 */     _jspx_th_c_005fforEach_005f7.setVarStatus("counter");
/* 2538 */     int[] _jspx_push_body_count_c_005fforEach_005f7 = { 0 };
/*      */     try {
/* 2540 */       int _jspx_eval_c_005fforEach_005f7 = _jspx_th_c_005fforEach_005f7.doStartTag();
/* 2541 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f7 != 0) {
/*      */         for (;;) {
/* 2543 */           out.write("\n\t     ");
/* 2544 */           boolean bool; if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fforEach_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f7))
/* 2545 */             return true;
/* 2546 */           out.write("\n\t\t\t\t\t\t <tr>\n\t\t\t\t\t\t\t\t<td class=\"bodytext\" style=\"cursor: pointer\" onmouseover='SetSelected(this)' onmouseout=\"changeStyle(this);\" onclick=\"SelectMonitor('service_list_left1','");
/* 2547 */           if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fforEach_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f7))
/* 2548 */             return true;
/* 2549 */           out.write(39);
/* 2550 */           out.write(44);
/* 2551 */           out.write(39);
/* 2552 */           if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fforEach_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f7))
/* 2553 */             return true;
/* 2554 */           out.write("','leftimage1','");
/* 2555 */           if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fforEach_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f7))
/* 2556 */             return true;
/* 2557 */           out.write("')\">");
/* 2558 */           if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fforEach_005f7, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f7))
/* 2559 */             return true;
/* 2560 */           out.write("</td>\n\t\t\t\t\t\t</tr>\n\t    ");
/* 2561 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f7.doAfterBody();
/* 2562 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2566 */       if (_jspx_th_c_005fforEach_005f7.doEndTag() == 5)
/* 2567 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 2570 */         int tmp370_369 = 0; int[] tmp370_367 = _jspx_push_body_count_c_005fforEach_005f7; int tmp372_371 = tmp370_367[tmp370_369];tmp370_367[tmp370_369] = (tmp372_371 - 1); if (tmp372_371 <= 0) break;
/* 2571 */         out = _jspx_page_context.popBody(); }
/* 2572 */       _jspx_th_c_005fforEach_005f7.doCatch(_jspx_exception);
/*      */     } finally {
/* 2574 */       _jspx_th_c_005fforEach_005f7.doFinally();
/* 2575 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f7);
/*      */     }
/* 2577 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fforEach_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f7) throws Throwable
/*      */   {
/* 2582 */     PageContext pageContext = _jspx_page_context;
/* 2583 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2585 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2586 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 2587 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f7);
/*      */     
/* 2589 */     _jspx_th_c_005fset_005f5.setVar("selectedmonitorname");
/* 2590 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 2591 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 2592 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 2593 */         out = _jspx_page_context.pushBody();
/* 2594 */         _jspx_push_body_count_c_005fforEach_005f7[0] += 1;
/* 2595 */         _jspx_th_c_005fset_005f5.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2596 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 2599 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fset_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f7))
/* 2600 */           return true;
/* 2601 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 2602 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 2605 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 2606 */         out = _jspx_page_context.popBody();
/* 2607 */         _jspx_push_body_count_c_005fforEach_005f7[0] -= 1;
/*      */       }
/*      */     }
/* 2610 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 2611 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 2612 */       return true;
/*      */     }
/* 2614 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 2615 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f7) throws Throwable
/*      */   {
/* 2620 */     PageContext pageContext = _jspx_page_context;
/* 2621 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2623 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2624 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 2625 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 2627 */     _jspx_th_c_005fout_005f16.setValue("${monitor[1]}");
/* 2628 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 2629 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 2630 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 2631 */       return true;
/*      */     }
/* 2633 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 2634 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fforEach_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f7) throws Throwable
/*      */   {
/* 2639 */     PageContext pageContext = _jspx_page_context;
/* 2640 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2642 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2643 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 2644 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f7);
/*      */     
/* 2646 */     _jspx_th_c_005fout_005f17.setValue("${selectedmonitorname}");
/* 2647 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 2648 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 2649 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 2650 */       return true;
/*      */     }
/* 2652 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 2653 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fforEach_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f7) throws Throwable
/*      */   {
/* 2658 */     PageContext pageContext = _jspx_page_context;
/* 2659 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2661 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2662 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 2663 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fforEach_005f7);
/*      */     
/* 2665 */     _jspx_th_c_005fout_005f18.setValue("${monitor[0]}");
/* 2666 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 2667 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 2668 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 2669 */       return true;
/*      */     }
/* 2671 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 2672 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fforEach_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f7) throws Throwable
/*      */   {
/* 2677 */     PageContext pageContext = _jspx_page_context;
/* 2678 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2680 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2681 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 2682 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fforEach_005f7);
/*      */     
/* 2684 */     _jspx_th_c_005fout_005f19.setValue("${secondlevelattribute}");
/* 2685 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 2686 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 2687 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 2688 */       return true;
/*      */     }
/* 2690 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 2691 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fforEach_005f7, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f7) throws Throwable
/*      */   {
/* 2696 */     PageContext pageContext = _jspx_page_context;
/* 2697 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2699 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2700 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 2701 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fforEach_005f7);
/*      */     
/* 2703 */     _jspx_th_c_005fout_005f20.setValue("${monitor[1]}");
/* 2704 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 2705 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 2706 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 2707 */       return true;
/*      */     }
/* 2709 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 2710 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fforEach_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f8) throws Throwable
/*      */   {
/* 2715 */     PageContext pageContext = _jspx_page_context;
/* 2716 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2718 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.get(SetTag.class);
/* 2719 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 2720 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f8);
/*      */     
/* 2722 */     _jspx_th_c_005fset_005f7.setVar("key");
/* 2723 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 2724 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 2725 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f7);
/* 2726 */       return true;
/*      */     }
/* 2728 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fnobody.reuse(_jspx_th_c_005fset_005f7);
/* 2729 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fforEach_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f8) throws Throwable
/*      */   {
/* 2734 */     PageContext pageContext = _jspx_page_context;
/* 2735 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2737 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2738 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 2739 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fforEach_005f8);
/*      */     
/* 2741 */     _jspx_th_c_005fout_005f21.setValue("${view[0]}");
/* 2742 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 2743 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 2744 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 2745 */       return true;
/*      */     }
/* 2747 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 2748 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fforEach_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f8) throws Throwable
/*      */   {
/* 2753 */     PageContext pageContext = _jspx_page_context;
/* 2754 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2756 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2757 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 2758 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fforEach_005f8);
/*      */     
/* 2760 */     _jspx_th_c_005fout_005f22.setValue("${key}");
/* 2761 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 2762 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 2763 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 2764 */       return true;
/*      */     }
/* 2766 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 2767 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fforEach_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f8) throws Throwable
/*      */   {
/* 2772 */     PageContext pageContext = _jspx_page_context;
/* 2773 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2775 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2776 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 2777 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fforEach_005f8);
/*      */     
/* 2779 */     _jspx_th_c_005fout_005f23.setValue("${view[1]}");
/* 2780 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 2781 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 2782 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 2783 */       return true;
/*      */     }
/* 2785 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 2786 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2791 */     PageContext pageContext = _jspx_page_context;
/* 2792 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2794 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2795 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 2796 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 2798 */     _jspx_th_c_005fout_005f24.setValue("${key}");
/* 2799 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 2800 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 2801 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 2802 */       return true;
/*      */     }
/* 2804 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 2805 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f10(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2810 */     PageContext pageContext = _jspx_page_context;
/* 2811 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2813 */     WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2814 */     _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 2815 */     _jspx_th_c_005fwhen_005f10.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 2817 */     _jspx_th_c_005fwhen_005f10.setTest("${related_action==\"showbusinessview\"}");
/* 2818 */     int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 2819 */     if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */       for (;;) {
/* 2821 */         out.write(10);
/* 2822 */         if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fwhen_005f10, _jspx_page_context))
/* 2823 */           return true;
/* 2824 */         out.write(10);
/* 2825 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 2826 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2830 */     if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 2831 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 2832 */       return true;
/*      */     }
/* 2834 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 2835 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_c_005fwhen_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2840 */     PageContext pageContext = _jspx_page_context;
/* 2841 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2843 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2844 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2845 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f10);
/* 2846 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2847 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/* 2849 */         out.write(10);
/* 2850 */         if (_jspx_meth_c_005fwhen_005f11(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 2851 */           return true;
/* 2852 */         out.write(10);
/* 2853 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/* 2854 */           return true;
/* 2855 */         out.write(10);
/* 2856 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2857 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2861 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2862 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2863 */       return true;
/*      */     }
/* 2865 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2866 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f11(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2871 */     PageContext pageContext = _jspx_page_context;
/* 2872 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2874 */     WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2875 */     _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 2876 */     _jspx_th_c_005fwhen_005f11.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 2878 */     _jspx_th_c_005fwhen_005f11.setTest("${not empty viewid}");
/* 2879 */     int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 2880 */     if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */       for (;;) {
/* 2882 */         out.write("\n <iframe id=\"_iframe_view\" src=\"/GraphicalView.do?method=popUp&haid=");
/* 2883 */         if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fwhen_005f11, _jspx_page_context))
/* 2884 */           return true;
/* 2885 */         out.write("&viewid=");
/* 2886 */         if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fwhen_005f11, _jspx_page_context))
/* 2887 */           return true;
/* 2888 */         out.write("&isPopUp=true\" scrolling=\"no\" align=\"center\" width=\"100%\" height=\"700px\" border=\"0\" frameborder=\"0\"> </iframe>\n");
/* 2889 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 2890 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2894 */     if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 2895 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 2896 */       return true;
/*      */     }
/* 2898 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 2899 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fwhen_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2904 */     PageContext pageContext = _jspx_page_context;
/* 2905 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2907 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2908 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 2909 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fwhen_005f11);
/*      */     
/* 2911 */     _jspx_th_c_005fout_005f25.setValue("${haid}");
/* 2912 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 2913 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 2914 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 2915 */       return true;
/*      */     }
/* 2917 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 2918 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fwhen_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2923 */     PageContext pageContext = _jspx_page_context;
/* 2924 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2926 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2927 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 2928 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fwhen_005f11);
/*      */     
/* 2930 */     _jspx_th_c_005fout_005f26.setValue("${viewid}");
/* 2931 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 2932 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 2933 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 2934 */       return true;
/*      */     }
/* 2936 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 2937 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2942 */     PageContext pageContext = _jspx_page_context;
/* 2943 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2945 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2946 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2947 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 2948 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2949 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 2951 */         out.write("\n<iframe id=\"_iframe_view\" src=\"/GraphicalView.do?method=popUp&haid=0&isPopUp=true\" scrolling=\"no\" align=\"center\" width=\"100%\" height=\"700px\" border=\"0\" frameborder=\"0\"> </iframe>\n");
/* 2952 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2953 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2957 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2958 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2959 */       return true;
/*      */     }
/* 2961 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2962 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fwhen_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2967 */     PageContext pageContext = _jspx_page_context;
/* 2968 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2970 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2971 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 2972 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fwhen_005f13);
/*      */     
/* 2974 */     _jspx_th_c_005fout_005f27.setValue("${url}");
/* 2975 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 2976 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 2977 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 2978 */       return true;
/*      */     }
/* 2980 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 2981 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fwhen_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2986 */     PageContext pageContext = _jspx_page_context;
/* 2987 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2989 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2990 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 2991 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fwhen_005f15);
/*      */     
/* 2993 */     _jspx_th_c_005fout_005f28.setValue("${url}");
/* 2994 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 2995 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 2996 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 2997 */       return true;
/*      */     }
/* 2999 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 3000 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_c_005fwhen_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3005 */     PageContext pageContext = _jspx_page_context;
/* 3006 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3008 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3009 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3010 */     _jspx_th_c_005fif_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f16);
/*      */     
/* 3012 */     _jspx_th_c_005fif_005f12.setTest("${not empty template_resid}");
/* 3013 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3014 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 3016 */         out.write(10);
/* 3017 */         if (_jspx_meth_c_005fset_005f9(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 3018 */           return true;
/* 3019 */         out.write(10);
/* 3020 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3021 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3025 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3026 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3027 */       return true;
/*      */     }
/* 3029 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3030 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f9(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3035 */     PageContext pageContext = _jspx_page_context;
/* 3036 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3038 */     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3039 */     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 3040 */     _jspx_th_c_005fset_005f9.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 3042 */     _jspx_th_c_005fset_005f9.setVar("templateid");
/* 3043 */     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 3044 */     if (_jspx_eval_c_005fset_005f9 != 0) {
/* 3045 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 3046 */         out = _jspx_page_context.pushBody();
/* 3047 */         _jspx_th_c_005fset_005f9.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 3048 */         _jspx_th_c_005fset_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3051 */         out.write("&template_resid=");
/* 3052 */         if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fset_005f9, _jspx_page_context))
/* 3053 */           return true;
/* 3054 */         int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 3055 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3058 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 3059 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3062 */     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 3063 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 3064 */       return true;
/*      */     }
/* 3066 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 3067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fset_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3072 */     PageContext pageContext = _jspx_page_context;
/* 3073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3075 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3076 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 3077 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fset_005f9);
/*      */     
/* 3079 */     _jspx_th_c_005fout_005f29.setValue("${template_resid}");
/* 3080 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 3081 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 3082 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 3083 */       return true;
/*      */     }
/* 3085 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 3086 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fwhen_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3091 */     PageContext pageContext = _jspx_page_context;
/* 3092 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3094 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3095 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 3096 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fwhen_005f16);
/*      */     
/* 3098 */     _jspx_th_c_005fout_005f30.setValue("${pagekey}");
/* 3099 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 3100 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 3101 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 3102 */       return true;
/*      */     }
/* 3104 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 3105 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fwhen_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3110 */     PageContext pageContext = _jspx_page_context;
/* 3111 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3113 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3114 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 3115 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fwhen_005f16);
/*      */     
/* 3117 */     _jspx_th_c_005fout_005f31.setValue("${templateid}");
/* 3118 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 3119 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 3120 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 3121 */       return true;
/*      */     }
/* 3123 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 3124 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fwhen_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3129 */     PageContext pageContext = _jspx_page_context;
/* 3130 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3132 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3133 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 3134 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fwhen_005f17);
/*      */     
/* 3136 */     _jspx_th_c_005fout_005f32.setValue("${param.widgetid}");
/* 3137 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 3138 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 3139 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 3140 */       return true;
/*      */     }
/* 3142 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 3143 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fwhen_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3148 */     PageContext pageContext = _jspx_page_context;
/* 3149 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3151 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3152 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 3153 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fwhen_005f17);
/*      */     
/* 3155 */     _jspx_th_c_005fout_005f33.setValue("${bookmarkid}");
/* 3156 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 3157 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 3158 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 3159 */       return true;
/*      */     }
/* 3161 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 3162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fwhen_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3167 */     PageContext pageContext = _jspx_page_context;
/* 3168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3170 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3171 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 3172 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fwhen_005f17);
/*      */     
/* 3174 */     _jspx_th_c_005fout_005f34.setValue("${displayName}");
/* 3175 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 3176 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 3177 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 3178 */       return true;
/*      */     }
/* 3180 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 3181 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fwhen_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3186 */     PageContext pageContext = _jspx_page_context;
/* 3187 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3189 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3190 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 3191 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fwhen_005f17);
/*      */     
/* 3193 */     _jspx_th_c_005fout_005f35.setValue("${url}");
/* 3194 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 3195 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 3196 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 3197 */       return true;
/*      */     }
/* 3199 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 3200 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fwhen_005f17, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3205 */     PageContext pageContext = _jspx_page_context;
/* 3206 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3208 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3209 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 3210 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fwhen_005f17);
/*      */     
/* 3212 */     _jspx_th_c_005fout_005f36.setValue("${description}");
/* 3213 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 3214 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 3215 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 3216 */       return true;
/*      */     }
/* 3218 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 3219 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3224 */     PageContext pageContext = _jspx_page_context;
/* 3225 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3227 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3228 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 3229 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 3231 */     _jspx_th_c_005fout_005f37.setValue("${resourceid}");
/* 3232 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 3233 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 3234 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 3235 */       return true;
/*      */     }
/* 3237 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 3238 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3243 */     PageContext pageContext = _jspx_page_context;
/* 3244 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3246 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3247 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 3248 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 3250 */     _jspx_th_c_005fout_005f38.setValue("${monitorname}");
/* 3251 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 3252 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 3253 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 3254 */       return true;
/*      */     }
/* 3256 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 3257 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3262 */     PageContext pageContext = _jspx_page_context;
/* 3263 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3265 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3266 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 3267 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 3269 */     _jspx_th_c_005fout_005f39.setValue("${attributename}");
/* 3270 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 3271 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 3272 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 3273 */       return true;
/*      */     }
/* 3275 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 3276 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_c_005fwhen_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3281 */     PageContext pageContext = _jspx_page_context;
/* 3282 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3284 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3285 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 3286 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_c_005fwhen_005f18);
/*      */     
/* 3288 */     _jspx_th_c_005fout_005f40.setValue("${widgettypename}");
/* 3289 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 3290 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 3291 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 3292 */       return true;
/*      */     }
/* 3294 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 3295 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fwhen_005f18, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3300 */     PageContext pageContext = _jspx_page_context;
/* 3301 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3303 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3304 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 3305 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fwhen_005f18);
/*      */     
/* 3307 */     _jspx_th_c_005fout_005f41.setValue("${widgetdescription}");
/*      */     
/* 3309 */     _jspx_th_c_005fout_005f41.setDefault("${defaultvalue}");
/* 3310 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 3311 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 3312 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 3313 */       return true;
/*      */     }
/* 3315 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 3316 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MyPage_005fRelatedAttribs_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */