/*      */ package org.apache.jsp.webclient.common.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import java.io.IOException;
/*      */ import java.util.Map;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class skinSelection_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   26 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   32 */   private static Map<String, Long> _jspx_dependants = new java.util.HashMap(3);
/*   33 */   static { _jspx_dependants.put("/webclient/common/jspf/commonIncludes.jspf", Long.valueOf(1473429417000L));
/*   34 */     _jspx_dependants.put("/WEB-INF/struts-logic.tld", Long.valueOf(1473429283000L));
/*   35 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*   52 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   56 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   66 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   70 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   71 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   72 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*   73 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   74 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   75 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   76 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   77 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*   78 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
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
/*   95 */       response.setContentType("text/html;charset=UTF-8");
/*   96 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*   98 */       _jspx_page_context = pageContext;
/*   99 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  100 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  101 */       session = pageContext.getSession();
/*  102 */       out = pageContext.getOut();
/*  103 */       _jspx_out = out;
/*      */       
/*  105 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n <link href=\"/images/bootstrap-colorpicker.min.css\" rel=\"stylesheet\"> \n");
/*  106 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  107 */       out.write("\n <script language=\"javascript1.2\" src=\"/template/bootstrap-colorpicker.min.js\"></script> \n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n\n<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n<html>\n<head>\n<style type=\"text/css\">\n\t.dropdown-menu{padding: 0 !important}\n\t.colorpicker{position: absolute;padding:0;}\n\t.white-bg {\n\t  border: 1px solid #e9e9e9;\n\t  color:#333;\n\t  background: #ffffff;\n\t  /* Old browsers */\n\t  background: -moz-linear-gradient(top, #E0E0E0 1%, #f7f7f7 44%, #d8d8d8 100%);\n\t  /* FF3.6+ */\n\t  background: -webkit-gradient(linear, left top, left bottom, color-stop(1%, #E0E0E0), color-stop(44%, #f7f7f7), color-stop(100%, #d8d8d8));\n\t  /* Chrome,Safari4+ */\n\t  background: -webkit-linear-gradient(top, #E0E0E0 1%, #f7f7f7 44%, #d8d8d8 100%);\n\t  /* Chrome10+,Safari5.1+ */\n\t  background: -o-linear-gradient(top, #E0E0E0 1%, #f7f7f7 44%, #d8d8d8 100%);\n\t  /* Opera 11.10+ */\n\t  background: -ms-linear-gradient(top, #E0E0E0 1%, #f7f7f7 44%, #d8d8d8 100%);\n");
/*  108 */       out.write("\t  /* IE10+ */\n\t  background: linear-gradient(to bottom, #E0E0E0 1%, #f7f7f7 44%, #d8d8d8 100%);\n\t  /* W3C */\n\t  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#E0E0E0', endColorstr='#d8d8d8', GradientType=0);\n\t  /* IE6-9 */\n\t}\n\t.non-white-bg{\n\t\tbackground-image: url(/images/tab-bg.png); \n\t\tcolor: rgb(255, 255, 255); \n\t\tbackground-color: rgb(255, 255, 255);\n\t}\n</style>\n");
/*      */       
/*  110 */       String simpleThemeVal = String.valueOf(com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.getSimpleThemeEnabled()).trim();
/*  111 */       String simpleThemeStyle = (simpleThemeVal != null) && (simpleThemeVal.equals("true")) ? "" : "display:none";
/*  112 */       if (("true".equalsIgnoreCase(System.getProperty("DEMOUSER"))) && (request != null) && (request.isUserInRole("OPERATOR"))) {
/*  113 */         pageContext.setAttribute("operatordemo", Boolean.valueOf(true));
/*      */       }
/*      */       
/*      */ 
/*  117 */       out.write("\n\n<LINK REL=\"SHORTCUT ICON\" HREF=\"/favicon.ico\">\n");
/*  118 */       out.write("\n\n\n\n\n<title>");
/*  119 */       out.print(FormatUtil.getString("am.webclient.MGAM.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*  120 */       out.write("</title>\n");
/*  121 */       out.write("\n<link href=\"/images/");
/*  122 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  124 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<script language=\"javascript\" src=\"/webclient/common/js/treeSelection.js\" type=\"text/javascript\"></script>\n<META HTTP-EQUIV=\"Pragma\" CONTENT=\"no-cache\">\n<META HTTP-EQUIV=\"cache-control\" CONTENT=\"no-store\"></head>\n<body>\n");
/*  125 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/*  127 */       out.write(10);
/*  128 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */         return;
/*  130 */       out.write(10);
/*  131 */       if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*      */         return;
/*  133 */       out.write(10);
/*  134 */       out.write(10);
/*  135 */       if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*      */         return;
/*  137 */       out.write("\n\n<form action=\"/preferencesSavedResponse.do\" name=\"changeSkin\" onsubmit = \"return validateAutoRefreshValues();\">\n\n");
/*  138 */       if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/*  139 */         out.write("\n\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"55\" class=\"darkheaderbg itadmin-hide\">\n <tr><td style=\"position:relative;top:10px;\"><span class=\"headingboldwhite\">\n");
/*      */         
/*  141 */         ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  142 */         _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  143 */         _jspx_th_c_005fchoose_005f1.setParent(null);
/*  144 */         int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  145 */         if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */           for (;;) {
/*  147 */             out.write(10);
/*  148 */             out.write(32);
/*  149 */             out.write(9);
/*      */             
/*  151 */             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  152 */             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  153 */             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/*  155 */             _jspx_th_c_005fwhen_005f2.setTest("${selectedscheme != 'slim'}");
/*  156 */             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  157 */             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */               for (;;) {
/*  159 */                 out.write("\n\t\t ");
/*  160 */                 out.print(FormatUtil.getString("am.webclient.common.classicView.personalize"));
/*  161 */                 out.write(10);
/*  162 */                 out.write(32);
/*  163 */                 out.write(9);
/*  164 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  165 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  169 */             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  170 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */             }
/*      */             
/*  173 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  174 */             out.write(10);
/*  175 */             out.write(32);
/*  176 */             out.write(9);
/*      */             
/*  178 */             OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  179 */             _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  180 */             _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*  181 */             int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  182 */             if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */               for (;;) {
/*  184 */                 out.write("\n \t\t");
/*  185 */                 out.print(FormatUtil.getString("am.webclient.common.simpleView.personalize"));
/*  186 */                 out.write("\n \t ");
/*  187 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  188 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  192 */             if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  193 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */             }
/*      */             
/*  196 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  197 */             out.write(10);
/*  198 */             out.write(32);
/*  199 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  200 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  204 */         if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  205 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */         }
/*      */         
/*  208 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  209 */         out.write("\n</span></td></tr>\n</table>\n\n\t<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:10px;margin-left:7px;\">\n\t  <tr class=\"tabBtmLine \">\n\t   <td nowrap=\"nowrap\">\n\t   \t");
/*      */         
/*  211 */         IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  212 */         _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  213 */         _jspx_th_c_005fif_005f3.setParent(null);
/*      */         
/*  215 */         _jspx_th_c_005fif_005f3.setTest("${isConsole != 'true'}");
/*  216 */         int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  217 */         if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */           for (;;) {
/*  219 */             out.write("\n\n\t\t<table id=\"InnerTab\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t      <tbody>\n\t\t<tr>\n\t\t  <td width=\"17\" >\n\n\t\t  </td>\n\t");
/*  220 */             if (!OEMUtil.isOEM()) {
/*  221 */               out.write("\n\t\t  <td><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t      <tbody>\n\n\t\t\t<tr>\n\t\t\t  <td class=\"tbSelected_Left\" id=\"skincol-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t\t  <td class=\"tbSelected_Middle\"id=\"skincol\">\n\t\t\t<a name=\"selectedskinlink\" id=\"skin\" class=\"bodytextbold\" href=\"javascript:void(0)\" onClick=\"ShowOption(this.name);\">&nbsp; <span>");
/*  222 */               out.print(FormatUtil.getString("webclient.common.skinselection.tab.skin"));
/*  223 */               out.write("</span>  &nbsp; </a>\n\t\t\t  </td>\n\t\t\t  <td class=\"tbselected_Right\" id=\"skincol-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t\t</tr>\n\t\t      </tbody>\n\n\t\t    </table>\n\t\t  </td>\n");
/*      */             }
/*  225 */             out.write("\n\t\t  <td><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t      <tbody>\n\t\t\t<tr>\n\t\t\t  <td class=\"tbUnselected_Left\" id=\"webclientcol-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n\t\t\t  <td class=\"tbUnselected_Middle\" id=\"webclientcol\">\n\t\t\t  <a name=\"webclientlink\" id=\"webclient\" class=\"tabLink\" href=\"javascript:void(0)\" onClick=\"ShowOption(this.name);\">&nbsp;<span>");
/*  226 */             out.print(FormatUtil.getString("webclient.common.skinselection.tab.webclient"));
/*  227 */             out.write("</span> </a>\n\n\t\t\t  </td>\n\t\t\t  <td class=\"tbUnselected_Right\" id=\"webclientcol-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n\t\t\t</tr>\n\t\t      </tbody>\n\t\t    </table>\n\t\t  </td>\n\t\t");
/*      */             
/*  229 */             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  230 */             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/*  231 */             _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */             
/*  233 */             _jspx_th_c_005fif_005f4.setTest("${selectedscheme != 'slim'}");
/*  234 */             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/*  235 */             if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */               for (;;) {
/*  237 */                 out.write("\n\t\t <td><table  style=\"cursor: pointer;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n                      <tbody>\n                        <tr>\n                          <td class=\"tbUnselected_Left\" id=\"customtabcol-left\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\" ></td>\n                          <td class=\"tbUnselected_Middle\" id=\"customtabcol\">\n                          <a name=\"customizetablink\" id=\"customizetablinkid\" class=\"tabLink\" href=\"javascript:void(0)\" onClick=\"ShowOption(this.name);\">&nbsp;<span>");
/*  238 */                 out.print(FormatUtil.getString("webclient.common.skinselection.tab.CustomizeTabs"));
/*  239 */                 out.write("</span> </a>\n\n                          </td>\n                          <td class=\"tbUnselected_Right\" id=\"customtabcol-right\"><img alt=\"spacer\" src=\"/images/spacer.gif\" width=\"1\" height=\"1\"></td>\n                        </tr>\n                      </tbody>\n                    </table>\n                  </td>\n\t\t");
/*  240 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/*  241 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  245 */             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/*  246 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */             }
/*      */             
/*  249 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*  250 */             out.write("\n\n\t </tr>\n </table><br>\n ");
/*  251 */             int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  252 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  256 */         if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  257 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */         }
/*      */         
/*  260 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  261 */         out.write("\n\n\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n      <td>\n\t<div id=\"skinselectiondiv\" style=\"");
/*  262 */         if (_jspx_meth_c_005fout_005f4(_jspx_page_context))
/*      */           return;
/*  264 */         out.write("\">\n\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\">\n          <tr>\n            <td height=\"30\" colspan=\"3\" valign=\"top\" class=\"columnheadingnotop\"> ");
/*  265 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */           return;
/*  267 */         out.write("</td>\n          </tr>\n          <tr>\n            <td width=\"300\" height=\"230px\" valign=\"top\" style=\"padding-left:20px\">\n            \t<table cellpadding=\"5\" class=\"bodytext\" style=\"position:relative;top:10px;margin-bottom: 20px;line-height:18px\">\n\t            \t<tr>\n\t            \t\t<td>\n\t            \t\t<label>\n\t            \t\t<input onClick=\"javascript:changeTheme('Grey')\"style=\"position:relative;top:-1px\" name=\"skins\" type=\"radio\" onClick=\"javascript:changeTheme('Grey')\" value=\"Grey\" ");
/*  268 */         if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*      */           return;
/*  270 */         out.write(">&nbsp;");
/*  271 */         out.print(FormatUtil.getString("am.webclient.personalize.grey"));
/*  272 */         out.write("<br>");
/*  273 */         out.write("\n\t            \t\t</label>\n\t            \t\t</td>\n\t            \t</tr>\n\t            \t<tr>\n\t            \t\t<td>\n\t            \t\t<label onClick=\"javascript:changeTheme('White')\">\n\t            \t\t<input style=\"position:relative;top:-1px\" name=\"skins\" type=\"radio\" onClick=\"javascript:changeTheme('White')\" value=\"White\" ");
/*  274 */         if (_jspx_meth_c_005fif_005f6(_jspx_page_context))
/*      */           return;
/*  276 */         out.write(" >&nbsp;");
/*  277 */         out.print(FormatUtil.getString("am.webclient.personalize.white"));
/*  278 */         out.write("<br>");
/*  279 */         out.write("\n\t            \t\t</label>\n\t            \t\t</td>\n\t            \t</tr>\n\t            \t<tr>\n\t            \t\t<td>\n\t            \t\t<label onClick=\"javascript:changeTheme('Blue')\">\n\t            \t\t<input style=\"position:relative;top:-1px\" name=\"skins\" type=\"radio\" onClick=\"javascript:changeTheme('Blue')\" value=\"Blue\" ");
/*  280 */         if (_jspx_meth_c_005fif_005f7(_jspx_page_context))
/*      */           return;
/*  282 */         out.write(" >&nbsp;");
/*  283 */         out.print(FormatUtil.getString("am.webclient.personalize.blue"));
/*  284 */         out.write("<br>");
/*  285 */         out.write("\n\t            \t\t</label>\n\t            \t\t</td>\n\t            \t</tr>\n\t            \t<tr>\n\t            \t\t<td>\n\t            \t\t<label onClick=\"javascript:changeTheme('Green')\">\n\t                \t<input style=\"position:relative;top:-1px\" name=\"skins\" type=\"radio\" onClick=\"javascript:changeTheme('Green')\" value=\"Green\"");
/*  286 */         if (_jspx_meth_c_005fif_005f8(_jspx_page_context))
/*      */           return;
/*  288 */         out.write(">&nbsp;");
/*  289 */         out.print(FormatUtil.getString("am.webclient.personalize.green"));
/*  290 */         out.write("<br>");
/*  291 */         out.write("\n\t                \t</label>\n\t                \t</td>\n\t            \t</tr>\n\t            \t<tr>\n\t            \t\t<td>\n\t            \t\t<label onClick=\"javascript:changeTheme('Brown')\">\n\t                \t<input style=\"position:relative;top:-1px\" name=\"skins\" type=\"radio\" onClick=\"javascript:changeTheme('Brown')\" value=\"Brown\"");
/*  292 */         if (_jspx_meth_c_005fif_005f9(_jspx_page_context))
/*      */           return;
/*  294 */         out.write(">&nbsp;");
/*  295 */         out.print(FormatUtil.getString("am.webclient.personalize.brown"));
/*  296 */         out.write("<br>");
/*  297 */         out.write("\n\t                \t</label>\n\t                \t</td>\n\t            \t</tr>\n\t            \t<tr>\n\t            \t\t<td>\n\t            \t\t<label onClick=\"javascript:changeTheme('Orange')\">\n\t                \t<input style=\"position:relative;top:-1px\" name=\"skins\" type=\"radio\"  value=\"Orange\"");
/*  298 */         if (_jspx_meth_c_005fif_005f10(_jspx_page_context))
/*      */           return;
/*  300 */         out.write(">&nbsp;");
/*  301 */         out.print(FormatUtil.getString("am.webclient.personalize.orange"));
/*  302 */         out.write("<br>    ");
/*  303 */         out.write("\n\t                \t</label>\n\t                \t</td>\n\t            \t</tr>\n\t            \t<!-- custom color picker code -->\n                        <!-- <tr>\n\t            \t\t<td>\n\t            \t\t<label onClick=\"javascript:changeTheme('Custom')\">\n\t                \t<input style=\"position:relative;top:-1px\" name=\"skins\" type=\"radio\" onClick=\"javascript:changeTheme('Custom')\" value=\"Custom\"");
/*  304 */         if (_jspx_meth_c_005fif_005f11(_jspx_page_context))
/*      */           return;
/*  306 */         out.write(">&nbsp;");
/*  307 */         out.print(FormatUtil.getString("am.webclient.personalize.custom"));
/*  308 */         out.write("<br>    ");
/*  309 */         out.write("\n\t                \t</label>\n\t                \t</td>\n\t            \t</tr> -->\n              \t</table>\n            </td>\n            <td width=\"300\" align=\"left\" valign=\"top\" class=\"themePreview\">\n            \t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"90%\" style=\"margin-top:30px\">\n            \t\t<tr>\n            \t\t\t<td>\n            \t\t\t\t<table id=\"displayTheme\" width=\"100%\" cellspacing=\"0\" cellpadding=\"10\" border=\"0\" style=\"padding-left:10px;border-radius:5px;\">\n\t\t\t            \t\t<tr>\n\t\t\t\t            \t\t<td width=\"40px\">");
/*  310 */         out.print(FormatUtil.getString("am.webclient.introtab.text"));
/*  311 */         out.write("</td>\n\t\t\t\t            \t\t<td width=\"40px\" class=\"menuHover\">");
/*  312 */         out.print(FormatUtil.getString("am.webclient.hometab.text"));
/*  313 */         out.write("</td>\n\t\t\t\t            \t\t<!--background-image: url(/images/Grey/nav-actbut.gif);-->\n\t\t\t\t            \t\t<td width=\"40px\">");
/*  314 */         out.print(FormatUtil.getString("am.webclient.monitorstab.text"));
/*  315 */         out.write("</td>\n\t\t\t\t            \t\t<td width=\"40px\">");
/*  316 */         out.print(FormatUtil.getString("am.webclient.alertstab.text"));
/*  317 */         out.write("</td>\n\t\t\t\t            \t\t<td width=\"30px\">");
/*  318 */         out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/*  319 */         out.write("</td>\n\t\t\t\t            \t\t<td>&nbsp;</td>\n\t\t\t            \t\t</tr>\n\t\t\t            \t</table>\n            \t\t\t</td>\n            \t\t</tr>\n                        \n                        <!-- custom color picker code -->\n            \t\t<tr>\n            \t\t\t<td>\n            \t\t\t\t<table height=\"10\" id=\"customOptions\" cellspacing=\"0\" cellpadding=\"5\" border=\"0\" width=\"100%\" style=\"padding-top:10px\">\n            \t\t\t\t\t<tr>\n            \t\t\t\t\t\t<td width=\"35%\">\n            \t\t\t\t\t\t<p><i>Background Color:</i> <span class=\"customColor\"></span></p> //NO I18N\n            \t\t\t\t\t\t<p><i>Font Color:</i> <span class=\"customFontColor\"></span></p> //NO I18N\n            \t\t\t\t\t\t</td>\n            \t\t\t\t\t</tr>\n            \t\t\t\t</table>\n            \t\t\t</td>\n            \t\t</tr>\n            \t</table>\n            </td>\n          </tr>\n\t  <tr style=\"");
/*  320 */         out.print(simpleThemeStyle);
/*  321 */         out.write("\">\n            <td height=\"30\" colspan=\"3\" class=\"columnheadingnotop\">");
/*  322 */         out.print(FormatUtil.getString("webclient.common.schemeselection.selectiondialog"));
/*  323 */         out.write("</td>\n          </tr>\n          <tr valign=\"top\" style=\"");
/*  324 */         out.print(simpleThemeStyle);
/*  325 */         out.write("\">\n          \t<td height=\"30\" valign=\"top\">&nbsp;</td>\n            <td height=\"30\" class=\"bodytext\"><p class=\"bodytext\" style=\"position:relative;top:0px\">\n            <input style=\"position:relative;top:1px\" name=\"scheme\" type=\"radio\" value=\"default\"   ");
/*  326 */         if (_jspx_meth_c_005fif_005f12(_jspx_page_context))
/*      */           return;
/*  328 */         out.write(" onClick=\"javascript:MM_swapImage('scheme','','/images/");
/*  329 */         out.print(OEMUtil.getOEMString("am.personalize.skincolour.blue.image"));
/*  330 */         out.write("',1)\" value=\"default\">");
/*  331 */         out.print(FormatUtil.getString("am.webclient.schemes.classic"));
/*  332 */         out.write("<br>");
/*  333 */         out.write("\n\t\t\t<input style=\"position:relative;top:1px\" name=\"scheme\" type=\"radio\" value=\"slim\"  ");
/*  334 */         if (_jspx_meth_c_005fif_005f13(_jspx_page_context))
/*      */           return;
/*  336 */         out.write(" onClick=\"javascript:MM_swapImage('scheme','','/images/");
/*  337 */         out.print(OEMUtil.getOEMString("am.personalize.skintype.simple.image"));
/*  338 */         out.write("',1)\" value=\"slim\">");
/*  339 */         out.print(FormatUtil.getString("am.webclient.schemes.simple"));
/*  340 */         out.write("\n\t\t\t</p></td>\n            <td height=\"30\" class=\"bodytext\">");
/*      */         
/*  342 */         IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  343 */         _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/*  344 */         _jspx_th_c_005fif_005f14.setParent(null);
/*      */         
/*  346 */         _jspx_th_c_005fif_005f14.setTest("${selectedscheme == 'default'}");
/*  347 */         int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/*  348 */         if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */           for (;;) {
/*  350 */             out.write("<img src=\"/images/");
/*  351 */             out.print(OEMUtil.getOEMString("am.personalize.skincolour.blue.image"));
/*  352 */             out.write("\" name=\"scheme\"  border=\"1\" id=\"scheme\">");
/*  353 */             int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/*  354 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  358 */         if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/*  359 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */         }
/*      */         
/*  362 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/*  363 */         out.write("\n              ");
/*      */         
/*  365 */         IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  366 */         _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/*  367 */         _jspx_th_c_005fif_005f15.setParent(null);
/*      */         
/*  369 */         _jspx_th_c_005fif_005f15.setTest("${selectedscheme == 'slim'}");
/*  370 */         int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/*  371 */         if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */           for (;;) {
/*  373 */             out.write("<img src=\"/images/");
/*  374 */             out.print(OEMUtil.getOEMString("am.personalize.skintype.simple.image"));
/*  375 */             out.write("\" name=\"scheme\"  border=\"1\" id=\"scheme\">");
/*  376 */             int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/*  377 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  381 */         if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/*  382 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */         }
/*      */         
/*  385 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/*  386 */         out.write("</td>\n          </tr>\n\t</table>\n\n\n\n\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"  class=\"lrbborder\" align=\"left\">\n          <tr>\n            <td  colspan=\"2\" align=\"center\" class=\"tablebottom1\">\n              <input name=\"Submit\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/*  387 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*      */           return;
/*  389 */         out.write("\" onclick=\"saveSkin();\">\n              <input type=\"button\" name=\"Cancel\" class=\"buttons btn_link\" value=\"");
/*  390 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*      */           return;
/*  392 */         out.write("\" onclick=\"closeWindow();\">\n            </td>\n          </tr>\n        </table>\n\t</div>\n\n\t<div id=\"reloadperioddiv\" style=\"");
/*  393 */         if (_jspx_meth_c_005fout_005f5(_jspx_page_context))
/*      */           return;
/*  395 */         out.write("\">\n\n\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\">\n\t<tr>\n                <td width=\"34%\"  class=\"columnheadingnotop\" colspan='2'>");
/*  396 */         out.print(FormatUtil.getString("webclient.common.webclient.autorefresh"));
/*  397 */         out.write("</td>\n                </tr>\n\t<tr>\n\t\t");
/*  398 */         if (session.getAttribute("customreloadperiod") != null)
/*      */         {
/*  400 */           String reloadPeriod = (String)session.getAttribute("customreloadperiod");
/*  401 */           int reloadPeriodInt = Integer.parseInt(reloadPeriod);
/*  402 */           String reloadPeriodMins = String.valueOf(reloadPeriodInt / 60);
/*      */           
/*      */ 
/*  405 */           out.write("\n           <td width=\"100%\" class=\"bodytext\" style=\"height:40px\">");
/*  406 */           out.print(FormatUtil.getString("am.webclient.reloadperiod.text"));
/*  407 */           out.write("&nbsp <input type=\"text\" id=\"reloadtimeid\" name=\"reloadtime\" value=");
/*  408 */           out.print(reloadPeriodMins);
/*  409 */           out.write(" size=\"5\" class=\"formtext\" /> &nbsp ");
/*  410 */           out.print(FormatUtil.getString("am.webclient.report.mins"));
/*  411 */           out.write(" </td>");
/*  412 */           out.write(10);
/*  413 */           out.write(9);
/*  414 */           out.write(9);
/*      */         }
/*      */         else
/*      */         {
/*  418 */           out.write("\n\t\t<td width=\"100%\" class=\"bodytext\" style=\"height:40px\">");
/*  419 */           out.print(FormatUtil.getString("am.webclient.reloadperiod.text"));
/*  420 */           out.write(" <input type=\"text\" id=\"reloadtimeid\" name=\"reloadtime\" size=\"5\"  class=\"formtext\"/> ");
/*  421 */           out.print(FormatUtil.getString("am.webclient.report.mins"));
/*  422 */           out.write(" </td>\n\t\t");
/*      */         }
/*      */         
/*      */ 
/*  426 */         out.write("\n        </tr>\n          <tr>\n            <td  colspan=\"2\" align=\"center\" class=\"tablebottom1\">\n              <input name=\"Submit\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/*  427 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*      */           return;
/*  429 */         out.write("\" onclick=\"validateAutoRefreshValues();\" >\n              <input type=\"button\" name=\"Cancel\" class=\"buttons btn_link\" value=\"");
/*  430 */         if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*      */           return;
/*  432 */         out.write("\" onclick=\"closeWindow();\">\n            </td>\n          </tr>\n\t</table>\n\t</div>\n\t<div id=\"customizetabdiv\" style=\"");
/*  433 */         if (_jspx_meth_c_005fout_005f6(_jspx_page_context))
/*      */           return;
/*  435 */         out.write("\">\n\t\t");
/*  436 */         if (_jspx_meth_c_005fif_005f16(_jspx_page_context))
/*      */           return;
/*  438 */         out.write("\n        </div>\n\t</td>\n\t</tr>\n\n  </table>\n   ");
/*      */       }
/*      */       else
/*      */       {
/*  442 */         out.write("\n  \n  \t <table width=\"100%\" height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n\t\t<tbody>\n\t\t\t<tr>\n\t\t\t\t<td>&nbsp;\n\t\t\t\t\t<span class=\"headingboldwhite\"> \n\t\t\t\t\t\t");
/*  443 */         out.print(FormatUtil.getString("webclient.common.skinselection.tab.CustomizeTabs"));
/*  444 */         out.write(" \n\t\t\t\t\t</span>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</tbody>\n\t</table>\t\n\t<br>\n \n  <div id=\"customizetabdiv\" style=\"");
/*  445 */         if (_jspx_meth_c_005fout_005f7(_jspx_page_context))
/*      */           return;
/*  447 */         out.write("\">\n\t\t");
/*  448 */         if (_jspx_meth_c_005fif_005f17(_jspx_page_context))
/*      */           return;
/*  450 */         out.write("\n        </div>\n  ");
/*      */       }
/*      */       
/*  453 */       out.write("\n</form>\n</body>\n\n\n<script>\nfunction submitPage()\n{\n\t");
/*  454 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/*  456 */       out.write("\n\t\n\t");
/*  457 */       if (_jspx_meth_c_005fif_005f18(_jspx_page_context))
/*      */         return;
/*  459 */       out.write("\n\t\nvar pageid=document.getElementById(\"selectedPage1\").value;\nif(pageid<0)\n{\nalert('");
/*  460 */       out.print(FormatUtil.getString("am.mypage.tabedit.tabnotselected.text"));
/*  461 */       out.write("');\nreturn;\n}\ndocument.forms[0].submit();\n}\n\n\nfunction validateAutoRefreshValues()\n{\n\t");
/*  462 */       if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */         return;
/*  464 */       out.write("\n\t\n\t");
/*  465 */       if (_jspx_meth_c_005fif_005f19(_jspx_page_context))
/*      */         return;
/*  467 */       out.write("\n        var reloadtimeval = trimAll(document.getElementById(\"reloadtimeid\").value);\n        if(reloadtimeval == \"\" || !isPositiveInteger(reloadtimeval) || reloadtimeval =='0' )\n        {\n        \t//IT360-4911\n     \t\tif(reloadtimeval.indexOf(\".\")!=-1){\n     \t\talert (\"");
/*  468 */       out.print(FormatUtil.getString("am.webclient.autorefresh.values.text"));
/*  469 */       out.write("\");\n     \t\t}\n     \t\telse{\n                alert (\"");
/*  470 */       out.print(FormatUtil.getString("am.webclient.autorefresh.warning.text"));
/*  471 */       out.write("\");\n     \t\t}\n                return false;\n        }\n        document.changeSkin.method.value=\"\";\n        document.changeSkin.submit();\n}\nfunction saveSkin(){\n\t");
/*  472 */       if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
/*      */         return;
/*  474 */       out.write("\n\t\n\t");
/*  475 */       if (_jspx_meth_c_005fif_005f20(_jspx_page_context))
/*      */         return;
/*  477 */       out.write("\n\n\tdocument.changeSkin.method.value=\"\";\n\tdocument.changeSkin.submit();\n}\nfunction ShowOption(linkObj)\n{\n\tvar isOEM=");
/*  478 */       out.print(OEMUtil.isOEM());
/*  479 */       out.write(";\n\tif(linkObj == \"selectedskinlink\")\n\t{\n\t\tif(isOEM==false){\t\t\t\n\t\t\tdocument.getElementById(\"skincol-left\").className = \"tbSelected_Left\";\n\t\t\tdocument.getElementById(\"skincol\").className = \"tbSelected_Middle\";\n\t\t\tdocument.getElementById(\"skincol-right\").className = \"tbSelected_Right\";\n\t\t\tdocument.getElementById(\"skin\").className=\"bodytextbold\";\n\t\t}\n\t\tdocument.getElementById(\"webclientcol-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"webclientcol\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"webclientcol-right\").className = \"tbUnSelected_Right\";\n\n\t\t");
/*  480 */       if (_jspx_meth_c_005fif_005f21(_jspx_page_context))
/*      */         return;
/*  482 */       out.write("\n\n\t\n\t\tdocument.getElementById(\"webclient\").className=\"tabLink\";\n\t\tshowDiv('skinselectiondiv');\n\t\thideDiv('reloadperioddiv');\n\t\thideDiv('customizetabdiv');\n\t}\n\telse if(linkObj == 'customizetablink')\n\t{\n\t\t");
/*  483 */       if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  484 */         out.write("       \n\t\t\tgetTabInfo();\n\t\t");
/*      */       }
/*      */       else
/*      */       {
/*  488 */         out.write("\n\t\tif(isOEM==false){\n\t\tdocument.getElementById(\"skincol-left\").className = \"tbUnSelected_Left\";\n        document.getElementById(\"skincol\").className = \"tbUnSelected_Middle\";\n        document.getElementById(\"skincol-right\").className = \"tbUnSelected_Right\";\n        document.getElementById(\"skin\").className=\"tabLink\";\n        hideDiv('skinselectiondiv');\n\t\t}\n        document.getElementById(\"webclientcol-left\").className = \"tbUnSelected_Left\";\n        document.getElementById(\"webclientcol\").className = \"tbUnSelected_Middle\";\n        document.getElementById(\"webclientcol-right\").className = \"tbUnSelected_Right\";\n\n        document.getElementById(\"customtabcol-left\").className = \"tbSelected_Left\";\n        document.getElementById(\"customtabcol\").className = \"tbSelected_Middle\";\n        document.getElementById(\"customtabcol-right\").className = \"tbSelected_Right\";\n\n     \n        document.getElementById(\"webclient\").className=\"tabLink\";\n\n        ");
/*  489 */         if (_jspx_meth_c_005fif_005f22(_jspx_page_context))
/*      */           return;
/*  491 */         out.write("\n\n       \n        hideDiv('reloadperioddiv');\n\t\tgetTabInfo();\n\t\t");
/*      */       }
/*  493 */       out.write("\n\t}\n\telse\n\t{\n\t\tif(isOEM==false){\n\t\tdocument.getElementById(\"skincol-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"skincol\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"skincol-right\").className = \"tbUnSelected_Right\";\n\t\tdocument.getElementById(\"skin\").className=\"tabLink\";\n\t\thideDiv('skinselectiondiv');\n\t\t}\n\t\tdocument.getElementById(\"webclientcol-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"webclientcol\").className = \"tbSelected_Middle\";\n\t\tdocument.getElementById(\"webclientcol-right\").className = \"tbSelected_Right\";\n\n\t\t");
/*  494 */       if (_jspx_meth_c_005fif_005f23(_jspx_page_context))
/*      */         return;
/*  496 */       out.write("\n\n\t\t\n\t\tdocument.getElementById(\"webclient\").className=\"bodytextbold\";\n\n\t\tshowDiv('reloadperioddiv');\n\t\t\n\t\thideDiv('customizetabdiv');\n\t}\n}\n\nfunction changeTheme(color){\n\tif(color=='Custom'){\n\t\t$('#customOptions').show();\n\t\tcolor='");
/*  497 */       if (_jspx_meth_c_005fout_005f8(_jspx_page_context))
/*      */         return;
/*  499 */       out.write("';\n\t}\n\telse{\n\t\t$('#customOptions').hide();\n\t}\n\t\n\tvar addClass=\"non-white-bg\";\t//NO I18N \n\tvar removeClass=\"white-bg\";\t//NO I18N \n\n\tif(color=='Grey'){\n\t\tcolorCode = \"#5F5F5F\";\t//NO I18N \n\t}\n\telse if(color=='Green'){\n\t\tcolorCode = \"#4AAA5A\";\t//NO I18N \n\t}\n\telse if(color=='Blue'){\n\t\tcolorCode = \"#356FB1\";\t//NO I18N \n\t}\n\telse if(color=='Brown'){\n\t\tcolorCode = \"#9C5131\";\t//NO I18N \n\t}\n\telse if(color=='Orange'){\n\t\tcolorCode = \"#FF9D34\";\t//NO I18N \n\t}\n\telse if(color=='White'){\n\t\tcolorCode=\"#fff\";\t//NO I18N \n\t\taddClass=\"white-bg\";\t//NO I18N \n\t\tremoveClass=\"non-white-bg\";\t//NO I18N \n\t}\n\t$('#displayTheme').css({'background-color': colorCode}).removeClass(removeClass).addClass(addClass);\t//NO I18N \n\t$('.customColor').css({'background': colorCode});\t//NO I18N \n\t$('.customFontColor').css({'background': '#fff'});\t//NO I18N \n}\n\nfunction getTabInfo()\n{\n\t  http = getHTTPObject();\n\t  var consoletype=\"");
/*  500 */       out.print(request.getParameter("consoletype"));
/*  501 */       out.write("\";\n\t  if(consoletype==null){\n\t\t  consoletype=\"admin\";//No I18N\n\t  }\n      http.open(\"GET\",\"/preferencesSavedResponse.do?method=editTab&consoletype=\"+consoletype,true);\n      // http.open(\"GET\",\"/preferencesSavedResponse.do?method=editTab&consoletype=admin\",true);\n        http.onreadystatechange = customizeTabResponse;\n        http.send(null);\n}\n\nfunction customizeTabResponse()\n{\n        if(http.readyState == 4)\n        {\n                if (http.status == 200)\n                {\n                        var ele = document.getElementById(\"customizetabdiv\")\n                        ele.innerHTML = http.responseText;\n                \tshowDiv('customizetabdiv');\n                }\n        }\n\n} \nfunction closeWindow()\n{\nif(window!=top){\nwindow.history.go(-2);\n}\nelse\n{\nwindow.close();\n}\n}\n$(document).ready(function(){\nif (document.referrer.toLowerCase().indexOf( \"adminlayout\")!=-1) \n{\n\n\t\tjQuery(\".itadmin-hide\").hide();// NO I18N\n\t}\t\n\tchangeTheme('");
/*  502 */       if (_jspx_meth_c_005fout_005f9(_jspx_page_context))
/*      */         return;
/*  504 */       out.write("');\n\t$('.customColor').colorpicker().on('changeColor', function(ev){   //NO I18N\n\t  $('#displayTheme').css({'background-color': ev.color.toHex()}); //NO I18N\n\t  $('.customColor').css({'background': ev.color.toHex()});        //NO I18N\n\t});\n\n\t$('.customFontColor').colorpicker().on('changeColor', function(ev){ //NO I18N\n      $('#displayTheme').css({'color': ev.color.toHex()});              //NO I18N\n\t  $('.customFontColor').css({'background': ev.color.toHex()});      //NO I18N\n\t  });\n\t\n\n\t  \n});\n\n</script>\n</html>\n");
/*      */     } catch (Throwable t) {
/*  506 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  507 */         out = _jspx_out;
/*  508 */         if ((out != null) && (out.getBufferSize() != 0))
/*  509 */           try { out.clearBuffer(); } catch (IOException e) {}
/*  510 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  513 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  519 */     PageContext pageContext = _jspx_page_context;
/*  520 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  522 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  523 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  524 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  526 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  528 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  529 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  530 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  531 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  532 */       return true;
/*      */     }
/*  534 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  535 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  540 */     PageContext pageContext = _jspx_page_context;
/*  541 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  543 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  544 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  545 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/*  547 */     _jspx_th_c_005fif_005f0.setTest("${empty selectedskin}");
/*  548 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  549 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/*  551 */         out.write(10);
/*  552 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*  553 */           return true;
/*  554 */         out.write(10);
/*  555 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  556 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  560 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  561 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  562 */       return true;
/*      */     }
/*  564 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  570 */     PageContext pageContext = _jspx_page_context;
/*  571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  573 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  574 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  575 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/*  577 */     _jspx_th_c_005fset_005f0.setVar("selectedskin");
/*  578 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  579 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/*  580 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/*  581 */         out = _jspx_page_context.pushBody();
/*  582 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  583 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  586 */         out.write(32);
/*  587 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f0, _jspx_page_context))
/*  588 */           return true;
/*  589 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  590 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  593 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/*  594 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  597 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  598 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  599 */       return true;
/*      */     }
/*  601 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  602 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  607 */     PageContext pageContext = _jspx_page_context;
/*  608 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  610 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  611 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/*  612 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/*  614 */     _jspx_th_c_005fout_005f1.setValue("${initParam.defaultSkin}");
/*  615 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/*  616 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/*  617 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  618 */       return true;
/*      */     }
/*  620 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  621 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  626 */     PageContext pageContext = _jspx_page_context;
/*  627 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  629 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  630 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  631 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/*  633 */     _jspx_th_c_005fif_005f1.setTest("${empty selectedscheme}");
/*  634 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  635 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/*  637 */         out.write(32);
/*  638 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*  639 */           return true;
/*  640 */         out.write(10);
/*  641 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  642 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  646 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  647 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  648 */       return true;
/*      */     }
/*  650 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  651 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  656 */     PageContext pageContext = _jspx_page_context;
/*  657 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  659 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  660 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  661 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/*  663 */     _jspx_th_c_005fset_005f1.setVar("selectedscheme");
/*  664 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  665 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/*  666 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/*  667 */         out = _jspx_page_context.pushBody();
/*  668 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/*  669 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  672 */         out.write(32);
/*  673 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f1, _jspx_page_context))
/*  674 */           return true;
/*  675 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/*  676 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  679 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/*  680 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  683 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  684 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/*  685 */       return true;
/*      */     }
/*  687 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/*  688 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  693 */     PageContext pageContext = _jspx_page_context;
/*  694 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  696 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  697 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/*  698 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/*  700 */     _jspx_th_c_005fout_005f2.setValue("${initParam.webclientScheme}");
/*  701 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/*  702 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/*  703 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  704 */       return true;
/*      */     }
/*  706 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  707 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  712 */     PageContext pageContext = _jspx_page_context;
/*  713 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  715 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  716 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  717 */     _jspx_th_c_005fif_005f2.setParent(null);
/*      */     
/*  719 */     _jspx_th_c_005fif_005f2.setTest("${empty customreloadtime}");
/*  720 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  721 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/*  723 */         out.write(32);
/*  724 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*  725 */           return true;
/*  726 */         out.write(10);
/*  727 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  728 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  732 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  733 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  734 */       return true;
/*      */     }
/*  736 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  737 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  742 */     PageContext pageContext = _jspx_page_context;
/*  743 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  745 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  746 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/*  747 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/*  749 */     _jspx_th_c_005fset_005f2.setVar("reloadtime");
/*  750 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/*  751 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/*  752 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/*  753 */         out = _jspx_page_context.pushBody();
/*  754 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/*  755 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  758 */         out.write(32);
/*  759 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f2, _jspx_page_context))
/*  760 */           return true;
/*  761 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/*  762 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  765 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/*  766 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  769 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/*  770 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/*  771 */       return true;
/*      */     }
/*  773 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/*  774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  779 */     PageContext pageContext = _jspx_page_context;
/*  780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  782 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  783 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/*  784 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/*  786 */     _jspx_th_c_005fout_005f3.setValue("5");
/*  787 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/*  788 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/*  789 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  790 */       return true;
/*      */     }
/*  792 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  793 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  798 */     PageContext pageContext = _jspx_page_context;
/*  799 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  801 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  802 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  803 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/*  804 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  805 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/*  807 */         out.write(10);
/*  808 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*  809 */           return true;
/*  810 */         out.write(10);
/*  811 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*  812 */           return true;
/*  813 */         out.write(10);
/*  814 */         out.write(10);
/*  815 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*  816 */           return true;
/*  817 */         out.write(10);
/*  818 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  819 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  823 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  824 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  825 */       return true;
/*      */     }
/*  827 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  828 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  833 */     PageContext pageContext = _jspx_page_context;
/*  834 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  836 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  837 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  838 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/*  840 */     _jspx_th_c_005fwhen_005f0.setTest("${param.tab == 'customizetab'}");
/*  841 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  842 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/*  844 */         out.write(10);
/*  845 */         out.write(32);
/*  846 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  847 */           return true;
/*  848 */         out.write(10);
/*  849 */         out.write(32);
/*  850 */         if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  851 */           return true;
/*  852 */         out.write(10);
/*  853 */         out.write(32);
/*  854 */         if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  855 */           return true;
/*  856 */         out.write(10);
/*  857 */         out.write(32);
/*  858 */         if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  859 */           return true;
/*  860 */         out.write(10);
/*  861 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  862 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  866 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  867 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  868 */       return true;
/*      */     }
/*  870 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  876 */     PageContext pageContext = _jspx_page_context;
/*  877 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  879 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  880 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/*  881 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  883 */     _jspx_th_c_005fset_005f3.setVar("headingTableStyle");
/*  884 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/*  885 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/*  886 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/*  887 */         out = _jspx_page_context.pushBody();
/*  888 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/*  889 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  892 */         out.write("display:block");
/*  893 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/*  894 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  897 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/*  898 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  901 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/*  902 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/*  903 */       return true;
/*      */     }
/*  905 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/*  906 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  911 */     PageContext pageContext = _jspx_page_context;
/*  912 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  914 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  915 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/*  916 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  918 */     _jspx_th_c_005fset_005f4.setVar("skinSelectDivStyle");
/*  919 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/*  920 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/*  921 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/*  922 */         out = _jspx_page_context.pushBody();
/*  923 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/*  924 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  927 */         out.write("display:none");
/*  928 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/*  929 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  932 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/*  933 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  936 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/*  937 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/*  938 */       return true;
/*      */     }
/*  940 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/*  941 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  946 */     PageContext pageContext = _jspx_page_context;
/*  947 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  949 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  950 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/*  951 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  953 */     _jspx_th_c_005fset_005f5.setVar("reloadperioddivStyle");
/*  954 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/*  955 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/*  956 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/*  957 */         out = _jspx_page_context.pushBody();
/*  958 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/*  959 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  962 */         out.write("display:none");
/*  963 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/*  964 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  967 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/*  968 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  971 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/*  972 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/*  973 */       return true;
/*      */     }
/*  975 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/*  976 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  981 */     PageContext pageContext = _jspx_page_context;
/*  982 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  984 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  985 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/*  986 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  988 */     _jspx_th_c_005fset_005f6.setVar("customizetabdiv");
/*  989 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/*  990 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/*  991 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/*  992 */         out = _jspx_page_context.pushBody();
/*  993 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/*  994 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  997 */         out.write("display:block");
/*  998 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/*  999 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1002 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 1003 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1006 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 1007 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 1008 */       return true;
/*      */     }
/* 1010 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 1011 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1016 */     PageContext pageContext = _jspx_page_context;
/* 1017 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1019 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1020 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1021 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 1023 */     _jspx_th_c_005fwhen_005f1.setTest("${param.tab != 'customizetab'}");
/* 1024 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1025 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 1027 */         out.write(10);
/* 1028 */         out.write(32);
/* 1029 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 1030 */           return true;
/* 1031 */         out.write(10);
/* 1032 */         out.write(32);
/* 1033 */         if (_jspx_meth_c_005fset_005f8(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 1034 */           return true;
/* 1035 */         out.write(10);
/* 1036 */         out.write(32);
/* 1037 */         if (_jspx_meth_c_005fset_005f9(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 1038 */           return true;
/* 1039 */         out.write(10);
/* 1040 */         out.write(32);
/* 1041 */         if (_jspx_meth_c_005fset_005f10(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 1042 */           return true;
/* 1043 */         out.write(10);
/* 1044 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1045 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1049 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1050 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1051 */       return true;
/*      */     }
/* 1053 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1054 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1059 */     PageContext pageContext = _jspx_page_context;
/* 1060 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1062 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1063 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 1064 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1066 */     _jspx_th_c_005fset_005f7.setVar("headingTableStyle");
/* 1067 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 1068 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 1069 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1070 */         out = _jspx_page_context.pushBody();
/* 1071 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 1072 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1075 */         out.write("display:block");
/* 1076 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 1077 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1080 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 1081 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1084 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 1085 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 1086 */       return true;
/*      */     }
/* 1088 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 1089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f8(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1094 */     PageContext pageContext = _jspx_page_context;
/* 1095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1097 */     SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1098 */     _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 1099 */     _jspx_th_c_005fset_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1101 */     _jspx_th_c_005fset_005f8.setVar("skinSelectDivStyle");
/* 1102 */     int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 1103 */     if (_jspx_eval_c_005fset_005f8 != 0) {
/* 1104 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1105 */         out = _jspx_page_context.pushBody();
/* 1106 */         _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 1107 */         _jspx_th_c_005fset_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1110 */         out.write("display:block");
/* 1111 */         int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 1112 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1115 */       if (_jspx_eval_c_005fset_005f8 != 1) {
/* 1116 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1119 */     if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 1120 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 1121 */       return true;
/*      */     }
/* 1123 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f8);
/* 1124 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f9(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1129 */     PageContext pageContext = _jspx_page_context;
/* 1130 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1132 */     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1133 */     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 1134 */     _jspx_th_c_005fset_005f9.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1136 */     _jspx_th_c_005fset_005f9.setVar("reloadperioddivStyle");
/* 1137 */     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 1138 */     if (_jspx_eval_c_005fset_005f9 != 0) {
/* 1139 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1140 */         out = _jspx_page_context.pushBody();
/* 1141 */         _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 1142 */         _jspx_th_c_005fset_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1145 */         out.write("display:none");
/* 1146 */         int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 1147 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1150 */       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 1151 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1154 */     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 1155 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 1156 */       return true;
/*      */     }
/* 1158 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f9);
/* 1159 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f10(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1164 */     PageContext pageContext = _jspx_page_context;
/* 1165 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1167 */     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1168 */     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 1169 */     _jspx_th_c_005fset_005f10.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1171 */     _jspx_th_c_005fset_005f10.setVar("customizetabdiv");
/* 1172 */     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 1173 */     if (_jspx_eval_c_005fset_005f10 != 0) {
/* 1174 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1175 */         out = _jspx_page_context.pushBody();
/* 1176 */         _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 1177 */         _jspx_th_c_005fset_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1180 */         out.write("display:none");
/* 1181 */         int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 1182 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1185 */       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 1186 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1189 */     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 1190 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 1191 */       return true;
/*      */     }
/* 1193 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f10);
/* 1194 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1199 */     PageContext pageContext = _jspx_page_context;
/* 1200 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1202 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1203 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1204 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 1205 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1206 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1208 */         out.write(10);
/* 1209 */         out.write(32);
/* 1210 */         if (_jspx_meth_c_005fset_005f11(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 1211 */           return true;
/* 1212 */         out.write(10);
/* 1213 */         out.write(32);
/* 1214 */         if (_jspx_meth_c_005fset_005f12(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 1215 */           return true;
/* 1216 */         out.write(10);
/* 1217 */         out.write(32);
/* 1218 */         if (_jspx_meth_c_005fset_005f13(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 1219 */           return true;
/* 1220 */         out.write(10);
/* 1221 */         out.write(32);
/* 1222 */         if (_jspx_meth_c_005fset_005f14(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 1223 */           return true;
/* 1224 */         out.write(10);
/* 1225 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1226 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1230 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1231 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1232 */       return true;
/*      */     }
/* 1234 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1235 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f11(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1240 */     PageContext pageContext = _jspx_page_context;
/* 1241 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1243 */     SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1244 */     _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 1245 */     _jspx_th_c_005fset_005f11.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 1247 */     _jspx_th_c_005fset_005f11.setVar("skinSelectDivStyle");
/* 1248 */     int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 1249 */     if (_jspx_eval_c_005fset_005f11 != 0) {
/* 1250 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1251 */         out = _jspx_page_context.pushBody();
/* 1252 */         _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 1253 */         _jspx_th_c_005fset_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1256 */         out.write("display:none");
/* 1257 */         int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 1258 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1261 */       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 1262 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1265 */     if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 1266 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 1267 */       return true;
/*      */     }
/* 1269 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f11);
/* 1270 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f12(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1275 */     PageContext pageContext = _jspx_page_context;
/* 1276 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1278 */     SetTag _jspx_th_c_005fset_005f12 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1279 */     _jspx_th_c_005fset_005f12.setPageContext(_jspx_page_context);
/* 1280 */     _jspx_th_c_005fset_005f12.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 1282 */     _jspx_th_c_005fset_005f12.setVar("headingTableStyle");
/* 1283 */     int _jspx_eval_c_005fset_005f12 = _jspx_th_c_005fset_005f12.doStartTag();
/* 1284 */     if (_jspx_eval_c_005fset_005f12 != 0) {
/* 1285 */       if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1286 */         out = _jspx_page_context.pushBody();
/* 1287 */         _jspx_th_c_005fset_005f12.setBodyContent((BodyContent)out);
/* 1288 */         _jspx_th_c_005fset_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1291 */         out.write("display:none");
/* 1292 */         int evalDoAfterBody = _jspx_th_c_005fset_005f12.doAfterBody();
/* 1293 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1296 */       if (_jspx_eval_c_005fset_005f12 != 1) {
/* 1297 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1300 */     if (_jspx_th_c_005fset_005f12.doEndTag() == 5) {
/* 1301 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 1302 */       return true;
/*      */     }
/* 1304 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f12);
/* 1305 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f13(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1310 */     PageContext pageContext = _jspx_page_context;
/* 1311 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1313 */     SetTag _jspx_th_c_005fset_005f13 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1314 */     _jspx_th_c_005fset_005f13.setPageContext(_jspx_page_context);
/* 1315 */     _jspx_th_c_005fset_005f13.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 1317 */     _jspx_th_c_005fset_005f13.setVar("reloadperioddivStyle");
/* 1318 */     int _jspx_eval_c_005fset_005f13 = _jspx_th_c_005fset_005f13.doStartTag();
/* 1319 */     if (_jspx_eval_c_005fset_005f13 != 0) {
/* 1320 */       if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1321 */         out = _jspx_page_context.pushBody();
/* 1322 */         _jspx_th_c_005fset_005f13.setBodyContent((BodyContent)out);
/* 1323 */         _jspx_th_c_005fset_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1326 */         out.write("display:block");
/* 1327 */         int evalDoAfterBody = _jspx_th_c_005fset_005f13.doAfterBody();
/* 1328 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1331 */       if (_jspx_eval_c_005fset_005f13 != 1) {
/* 1332 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1335 */     if (_jspx_th_c_005fset_005f13.doEndTag() == 5) {
/* 1336 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 1337 */       return true;
/*      */     }
/* 1339 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f13);
/* 1340 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f14(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1345 */     PageContext pageContext = _jspx_page_context;
/* 1346 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1348 */     SetTag _jspx_th_c_005fset_005f14 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 1349 */     _jspx_th_c_005fset_005f14.setPageContext(_jspx_page_context);
/* 1350 */     _jspx_th_c_005fset_005f14.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 1352 */     _jspx_th_c_005fset_005f14.setVar("customizetabdiv");
/* 1353 */     int _jspx_eval_c_005fset_005f14 = _jspx_th_c_005fset_005f14.doStartTag();
/* 1354 */     if (_jspx_eval_c_005fset_005f14 != 0) {
/* 1355 */       if (_jspx_eval_c_005fset_005f14 != 1) {
/* 1356 */         out = _jspx_page_context.pushBody();
/* 1357 */         _jspx_th_c_005fset_005f14.setBodyContent((BodyContent)out);
/* 1358 */         _jspx_th_c_005fset_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1361 */         out.write("display:none");
/* 1362 */         int evalDoAfterBody = _jspx_th_c_005fset_005f14.doAfterBody();
/* 1363 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1366 */       if (_jspx_eval_c_005fset_005f14 != 1) {
/* 1367 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 1370 */     if (_jspx_th_c_005fset_005f14.doEndTag() == 5) {
/* 1371 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f14);
/* 1372 */       return true;
/*      */     }
/* 1374 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f14);
/* 1375 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1380 */     PageContext pageContext = _jspx_page_context;
/* 1381 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1383 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1384 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 1385 */     _jspx_th_c_005fout_005f4.setParent(null);
/*      */     
/* 1387 */     _jspx_th_c_005fout_005f4.setValue("${skinSelectDivStyle}");
/* 1388 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 1389 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 1390 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1391 */       return true;
/*      */     }
/* 1393 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 1394 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1399 */     PageContext pageContext = _jspx_page_context;
/* 1400 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1402 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1403 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 1404 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*      */     
/* 1406 */     _jspx_th_fmt_005fmessage_005f0.setKey("webclient.common.skinselection.selectiondialog");
/* 1407 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 1408 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 1409 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 1410 */       return true;
/*      */     }
/* 1412 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 1413 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1418 */     PageContext pageContext = _jspx_page_context;
/* 1419 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1421 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1422 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 1423 */     _jspx_th_c_005fif_005f5.setParent(null);
/*      */     
/* 1425 */     _jspx_th_c_005fif_005f5.setTest("${selectedskin == 'Grey'}");
/* 1426 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1427 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 1429 */         out.write("checked");
/* 1430 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1431 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1435 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1436 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1437 */       return true;
/*      */     }
/* 1439 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1440 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1445 */     PageContext pageContext = _jspx_page_context;
/* 1446 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1448 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1449 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 1450 */     _jspx_th_c_005fif_005f6.setParent(null);
/*      */     
/* 1452 */     _jspx_th_c_005fif_005f6.setTest("${selectedskin == 'White'}");
/* 1453 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 1454 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 1456 */         out.write("checked");
/* 1457 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 1458 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1462 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 1463 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1464 */       return true;
/*      */     }
/* 1466 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 1467 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1472 */     PageContext pageContext = _jspx_page_context;
/* 1473 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1475 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1476 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 1477 */     _jspx_th_c_005fif_005f7.setParent(null);
/*      */     
/* 1479 */     _jspx_th_c_005fif_005f7.setTest("${selectedskin == 'Blue'}");
/* 1480 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 1481 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 1483 */         out.write("checked");
/* 1484 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 1485 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1489 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 1490 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1491 */       return true;
/*      */     }
/* 1493 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 1494 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1499 */     PageContext pageContext = _jspx_page_context;
/* 1500 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1502 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1503 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 1504 */     _jspx_th_c_005fif_005f8.setParent(null);
/*      */     
/* 1506 */     _jspx_th_c_005fif_005f8.setTest("${selectedskin == 'Green'}");
/* 1507 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 1508 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 1510 */         out.write(" checked");
/* 1511 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 1512 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1516 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 1517 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 1518 */       return true;
/*      */     }
/* 1520 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 1521 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1526 */     PageContext pageContext = _jspx_page_context;
/* 1527 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1529 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1530 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 1531 */     _jspx_th_c_005fif_005f9.setParent(null);
/*      */     
/* 1533 */     _jspx_th_c_005fif_005f9.setTest("${selectedskin == 'Brown'}");
/* 1534 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 1535 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 1537 */         out.write(" checked");
/* 1538 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 1539 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1543 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 1544 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 1545 */       return true;
/*      */     }
/* 1547 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 1548 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1553 */     PageContext pageContext = _jspx_page_context;
/* 1554 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1556 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1557 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 1558 */     _jspx_th_c_005fif_005f10.setParent(null);
/*      */     
/* 1560 */     _jspx_th_c_005fif_005f10.setTest("${selectedskin == 'Orange'}");
/* 1561 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 1562 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 1564 */         out.write(" checked");
/* 1565 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 1566 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1570 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 1571 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 1572 */       return true;
/*      */     }
/* 1574 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 1575 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f11(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1580 */     PageContext pageContext = _jspx_page_context;
/* 1581 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1583 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1584 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 1585 */     _jspx_th_c_005fif_005f11.setParent(null);
/*      */     
/* 1587 */     _jspx_th_c_005fif_005f11.setTest("${selectedskin == 'Custom'}");
/* 1588 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 1589 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */       for (;;) {
/* 1591 */         out.write(" checked");
/* 1592 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 1593 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1597 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 1598 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 1599 */       return true;
/*      */     }
/* 1601 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 1602 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1607 */     PageContext pageContext = _jspx_page_context;
/* 1608 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1610 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1611 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 1612 */     _jspx_th_c_005fif_005f12.setParent(null);
/*      */     
/* 1614 */     _jspx_th_c_005fif_005f12.setTest("${selectedscheme == 'default'}");
/* 1615 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 1616 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 1618 */         out.write("checked");
/* 1619 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 1620 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1624 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 1625 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 1626 */       return true;
/*      */     }
/* 1628 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 1629 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1634 */     PageContext pageContext = _jspx_page_context;
/* 1635 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1637 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1638 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 1639 */     _jspx_th_c_005fif_005f13.setParent(null);
/*      */     
/* 1641 */     _jspx_th_c_005fif_005f13.setTest("${selectedscheme == 'slim'}");
/* 1642 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 1643 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 1645 */         out.write(" checked");
/* 1646 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 1647 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1651 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 1652 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1653 */       return true;
/*      */     }
/* 1655 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 1656 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1661 */     PageContext pageContext = _jspx_page_context;
/* 1662 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1664 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1665 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 1666 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*      */     
/* 1668 */     _jspx_th_fmt_005fmessage_005f1.setKey("webclient.common.skinselection.apply");
/* 1669 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 1670 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 1671 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 1672 */       return true;
/*      */     }
/* 1674 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 1675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1680 */     PageContext pageContext = _jspx_page_context;
/* 1681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1683 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1684 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 1685 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/*      */     
/* 1687 */     _jspx_th_fmt_005fmessage_005f2.setKey("webclient.common.skinselection.cancel");
/* 1688 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 1689 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 1690 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 1691 */       return true;
/*      */     }
/* 1693 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 1694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1699 */     PageContext pageContext = _jspx_page_context;
/* 1700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1702 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1703 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1704 */     _jspx_th_c_005fout_005f5.setParent(null);
/*      */     
/* 1706 */     _jspx_th_c_005fout_005f5.setValue("${reloadperioddivStyle}");
/* 1707 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1708 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1709 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1710 */       return true;
/*      */     }
/* 1712 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1718 */     PageContext pageContext = _jspx_page_context;
/* 1719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1721 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1722 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 1723 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/*      */     
/* 1725 */     _jspx_th_fmt_005fmessage_005f3.setKey("webclient.common.skinselection.apply");
/* 1726 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 1727 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 1728 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 1729 */       return true;
/*      */     }
/* 1731 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 1732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1737 */     PageContext pageContext = _jspx_page_context;
/* 1738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1740 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 1741 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 1742 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/*      */     
/* 1744 */     _jspx_th_fmt_005fmessage_005f4.setKey("webclient.common.skinselection.cancel");
/* 1745 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 1746 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 1747 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 1748 */       return true;
/*      */     }
/* 1750 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 1751 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1756 */     PageContext pageContext = _jspx_page_context;
/* 1757 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1759 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1760 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1761 */     _jspx_th_c_005fout_005f6.setParent(null);
/*      */     
/* 1763 */     _jspx_th_c_005fout_005f6.setValue("${customizetabdiv}");
/* 1764 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1765 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1766 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1767 */       return true;
/*      */     }
/* 1769 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1770 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1775 */     PageContext pageContext = _jspx_page_context;
/* 1776 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1778 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1779 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 1780 */     _jspx_th_c_005fif_005f16.setParent(null);
/*      */     
/* 1782 */     _jspx_th_c_005fif_005f16.setTest("${param.tab == 'customizetab'}");
/* 1783 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 1784 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 1786 */         out.write("\n\t\t\t<script> setTimeout(function(){ShowOption('customizetablink')},1); </script> ");
/* 1787 */         out.write(10);
/* 1788 */         out.write(9);
/* 1789 */         out.write(9);
/* 1790 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 1791 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1795 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 1796 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 1797 */       return true;
/*      */     }
/* 1799 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 1800 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1805 */     PageContext pageContext = _jspx_page_context;
/* 1806 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1808 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1809 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1810 */     _jspx_th_c_005fout_005f7.setParent(null);
/*      */     
/* 1812 */     _jspx_th_c_005fout_005f7.setValue("${customizetabdiv}");
/* 1813 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1814 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1815 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1816 */       return true;
/*      */     }
/* 1818 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1819 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1824 */     PageContext pageContext = _jspx_page_context;
/* 1825 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1827 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1828 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 1829 */     _jspx_th_c_005fif_005f17.setParent(null);
/*      */     
/* 1831 */     _jspx_th_c_005fif_005f17.setTest("${param.tab == 'customizetab'}");
/* 1832 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 1833 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 1835 */         out.write("\n\t\t<script> ShowOption('customizetablink') </script>\n\t\t");
/* 1836 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 1837 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1841 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 1842 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 1843 */       return true;
/*      */     }
/* 1845 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 1846 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1851 */     PageContext pageContext = _jspx_page_context;
/* 1852 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1854 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1855 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 1856 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 1858 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 1859 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 1860 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 1862 */         out.write("\n\t alertUser();\n\t return ;\n\t");
/* 1863 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 1864 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1868 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 1869 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1870 */       return true;
/*      */     }
/* 1872 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 1873 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f18(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1878 */     PageContext pageContext = _jspx_page_context;
/* 1879 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1881 */     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1882 */     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 1883 */     _jspx_th_c_005fif_005f18.setParent(null);
/*      */     
/* 1885 */     _jspx_th_c_005fif_005f18.setTest("${operatordemo}");
/* 1886 */     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 1887 */     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */       for (;;) {
/* 1889 */         out.write("\n\t\talertUser();\n\t \treturn ;\n\t");
/* 1890 */         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 1891 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1895 */     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 1896 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 1897 */       return true;
/*      */     }
/* 1899 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 1900 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1905 */     PageContext pageContext = _jspx_page_context;
/* 1906 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1908 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1909 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 1910 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 1912 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 1913 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 1914 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 1916 */         out.write("\n\t alertUser();\n\treturn ;\n\t");
/* 1917 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 1918 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1922 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 1923 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1924 */       return true;
/*      */     }
/* 1926 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 1927 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f19(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1932 */     PageContext pageContext = _jspx_page_context;
/* 1933 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1935 */     IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1936 */     _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 1937 */     _jspx_th_c_005fif_005f19.setParent(null);
/*      */     
/* 1939 */     _jspx_th_c_005fif_005f19.setTest("${operatordemo}");
/* 1940 */     int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 1941 */     if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */       for (;;) {
/* 1943 */         out.write("\n\t\talertUser();\n\t \treturn ;\n\t");
/* 1944 */         int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 1945 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1949 */     if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 1950 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 1951 */       return true;
/*      */     }
/* 1953 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 1954 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1959 */     PageContext pageContext = _jspx_page_context;
/* 1960 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1962 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 1963 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 1964 */     _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */     
/* 1966 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 1967 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 1968 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 1970 */         out.write("\n\t alertUser();\n\treturn ;\n\t");
/* 1971 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 1972 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1976 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 1977 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1978 */       return true;
/*      */     }
/* 1980 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 1981 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1986 */     PageContext pageContext = _jspx_page_context;
/* 1987 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1989 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1990 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 1991 */     _jspx_th_c_005fif_005f20.setParent(null);
/*      */     
/* 1993 */     _jspx_th_c_005fif_005f20.setTest("${operatordemo}");
/* 1994 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 1995 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 1997 */         out.write("\n\t\talertUser();\n\t \treturn ;\n\t");
/* 1998 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 1999 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2003 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 2004 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 2005 */       return true;
/*      */     }
/* 2007 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 2008 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f21(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2013 */     PageContext pageContext = _jspx_page_context;
/* 2014 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2016 */     IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2017 */     _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 2018 */     _jspx_th_c_005fif_005f21.setParent(null);
/*      */     
/* 2020 */     _jspx_th_c_005fif_005f21.setTest("${selectedscheme ne 'slim'}");
/* 2021 */     int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 2022 */     if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */       for (;;) {
/* 2024 */         out.write("\n\t\t\tdocument.getElementById(\"customtabcol-left\").className = \"tbUnSelected_Left\";\n            document.getElementById(\"customtabcol\").className = \"tbUnSelected_Middle\";\n            document.getElementById(\"customtabcol-right\").className = \"tbUnSelected_Right\";\n\t\t");
/* 2025 */         int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 2026 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2030 */     if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 2031 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 2032 */       return true;
/*      */     }
/* 2034 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 2035 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f22(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2040 */     PageContext pageContext = _jspx_page_context;
/* 2041 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2043 */     IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2044 */     _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 2045 */     _jspx_th_c_005fif_005f22.setParent(null);
/*      */     
/* 2047 */     _jspx_th_c_005fif_005f22.setTest("${selectedscheme ne 'slim'}");
/* 2048 */     int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 2049 */     if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */       for (;;) {
/* 2051 */         out.write("\n\t         document.getElementById(\"customtabcol-left\").className = \"tbSelected_Left\";\n\t         document.getElementById(\"customtabcol\").className = \"tbSelected_Middle\";\n\t         document.getElementById(\"customtabcol-right\").className = \"tbSelected_Right\";\n\t         document.getElementById(\"customizetablinkid\").className=\"bodytextbold\";\n\t\t");
/* 2052 */         int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 2053 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2057 */     if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 2058 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 2059 */       return true;
/*      */     }
/* 2061 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 2062 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f23(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2067 */     PageContext pageContext = _jspx_page_context;
/* 2068 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2070 */     IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2071 */     _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 2072 */     _jspx_th_c_005fif_005f23.setParent(null);
/*      */     
/* 2074 */     _jspx_th_c_005fif_005f23.setTest("${selectedscheme ne 'slim'}");
/* 2075 */     int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 2076 */     if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */       for (;;) {
/* 2078 */         out.write("\n\t\t\tdocument.getElementById(\"customtabcol-left\").className = \"tbUnSelected_Left\";\n\t        document.getElementById(\"customtabcol\").className = \"tbUnSelected_Middle\";\n\t        document.getElementById(\"customtabcol-right\").className = \"tbUnSelected_Right\";\n\t        document.getElementById(\"customizetablinkid\").className=\"tabLink\";\n        ");
/* 2079 */         int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 2080 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2084 */     if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 2085 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 2086 */       return true;
/*      */     }
/* 2088 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 2089 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2094 */     PageContext pageContext = _jspx_page_context;
/* 2095 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2097 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2098 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 2099 */     _jspx_th_c_005fout_005f8.setParent(null);
/*      */     
/* 2101 */     _jspx_th_c_005fout_005f8.setValue("${selectedskin}");
/* 2102 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 2103 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 2104 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2105 */       return true;
/*      */     }
/* 2107 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 2108 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2113 */     PageContext pageContext = _jspx_page_context;
/* 2114 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2116 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2117 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 2118 */     _jspx_th_c_005fout_005f9.setParent(null);
/*      */     
/* 2120 */     _jspx_th_c_005fout_005f9.setValue("${selectedskin}");
/* 2121 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 2122 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 2123 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2124 */       return true;
/*      */     }
/* 2126 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 2127 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\common\jsp\skinSelection_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */