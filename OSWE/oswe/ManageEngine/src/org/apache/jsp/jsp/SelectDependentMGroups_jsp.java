/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.ImportTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ 
/*     */ public final class SelectDependentMGroups_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  39 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  43 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  50 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  54 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  55 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  56 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  57 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  58 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  59 */     this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  66 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  69 */     JspWriter out = null;
/*  70 */     Object page = this;
/*  71 */     JspWriter _jspx_out = null;
/*  72 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  76 */       response.setContentType("text/html;charset=UTF-8");
/*  77 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  79 */       _jspx_page_context = pageContext;
/*  80 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  81 */       ServletConfig config = pageContext.getServletConfig();
/*  82 */       session = pageContext.getSession();
/*  83 */       out = pageContext.getOut();
/*  84 */       _jspx_out = out;
/*     */       
/*  86 */       out.write("<!-- $Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  87 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  89 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n");
/*     */       
/*  91 */       String haid = request.getParameter("haid");
/*     */       
/*  93 */       out.write("\n<script>\n\nfunction checkAndSubmit()\n{\n\tvar dependentArr = {};\n\tvar len = $('input[name=\"select\"]').length\n\tfor(i=0;i<len;i++)\n\t{\n\t\tif($('input[name=\"select\"]')[i].checked)\n\t\t{\n\t\t\tvar resourceIDStr = $('input[name=\"select\"]')[i].value;\n\t\t\tvar mgNameStr = $('input[name=\"mgName\"]')[i].value;\n\t\t\tdependentArr[resourceIDStr] = {'resourceid':resourceIDStr,'displayname':mgNameStr};//No I18N\n\t\t}\n\t}\n\tvar cnt = getJSONArrayObjectCount(dependentArr);\n\tif(cnt == 0)\n\t{\n\t\talert('");
/*  94 */       out.print(FormatUtil.getString("am.webclient.dependent.monitorgroup.js.select.alert.text"));
/*  95 */       out.write("');\n\t\treturn;\n\t}\t\n\twindow.opener.createRowsForSelDepMGroup(\"DependentMGroupTableID\",dependentArr);//No I18N\n\twindow.close();\t\n}\n\nvar isADDM=");
/*  96 */       out.print(request.getAttribute("isADDM"));
/*  97 */       out.write(";\nif(isADDM){\n\t$(\"#dependentMGHeader\").hide();\n\t$(\"#addFromDependentPage\").hide();\n}\n</script>\n");
/*     */       
/*  99 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 100 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 101 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/* 103 */       _jspx_th_c_005fif_005f0.setTest("${isADDM == false}");
/* 104 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 105 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */         for (;;) {
/* 107 */           out.write("\n\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"99%\" height=\"55\" class=\"darkheaderbg\">\n\t<tr>\n \t\t<td>&nbsp;<span class=\"headingboldwhite\">");
/* 108 */           out.print(FormatUtil.getString("am.webclient.mgroup.rule.depen.group.config.title", new String[] { com.adventnet.appmanager.util.DBUtil.getDisplaynameforResourceID(haid) }));
/* 109 */           out.write("</span></td>\n \t</tr>\n</table>\n");
/* 110 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 111 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 115 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 116 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 119 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 120 */         out.write("\n<table width=\"95%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n<tr>\n<td align=\"left\" style=\"padding-left:20px\" colspan=\"2\">\n");
/*     */         
/* 122 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 123 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 124 */         _jspx_th_c_005fif_005f1.setParent(null);
/*     */         
/* 126 */         _jspx_th_c_005fif_005f1.setTest("${isADDM == false}");
/* 127 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 128 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */           for (;;) {
/* 130 */             out.write("\n <form action=\"/showresource.do\" method=\"get\">\n    <input type=\"hidden\" name=\"method\" value=\"selectDependentMGroups\">\n    <input type=\"hidden\" name=\"haid\" value=\"");
/* 131 */             out.print(haid);
/* 132 */             out.write("\">\n\t<input type=\"text\" name=\"search\" size=\"14\" class=\"searchBox\" placeholder=\"");
/* 133 */             out.print(FormatUtil.getString("am.webclient.dependentDevice.search.text"));
/* 134 */             out.write("\" autocomplete=\"off\">\n </form>\n");
/* 135 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 136 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 140 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 141 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*     */         }
/*     */         else {
/* 144 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 145 */           out.write("\n</td>\n</tr>\n</table>\n");
/*     */           
/* 147 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 148 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 149 */           _jspx_th_c_005fchoose_005f0.setParent(null);
/* 150 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 151 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */             for (;;) {
/* 153 */               out.write(10);
/*     */               
/* 155 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 156 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 157 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */               
/* 159 */               _jspx_th_c_005fwhen_005f0.setTest("${empty groupdetaillist['childlist']}");
/* 160 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 161 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */                 for (;;) {
/* 163 */                   out.write("\n<table class=\"lrbtborder\"  style=\"background-color:#fff;margin-top:10px\" width=\"95%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n\t<tr>\n\t   <td width=\"60%\" align=\"left\" class=\"columnheadingnotop\" colspan=\"2\">&nbsp;</td>\n\t</tr>\n\t<tr>\n\t\t <td width=\"60%\" align=\"center\" colspan=\"2\" class=\"whitegrayborder\">");
/* 164 */                   out.print(FormatUtil.getString("am.webclient.hometab.monitorgroups.nodatamessage"));
/* 165 */                   out.write(".</td>\n\t</tr>\n</table>\n");
/* 166 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 167 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 171 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 172 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */               }
/*     */               
/* 175 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 176 */               out.write(10);
/*     */               
/* 178 */               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 179 */               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 180 */               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 181 */               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 182 */               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */                 for (;;) {
/* 184 */                   out.write("\n<form name='MonitorGroupSelection'>\n<table class=\"\"  style=\"background-color:#fff;margin-top:10px\" width=\"95%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" align=\"center\"> \n    <tr>\n\t\t<td colspan=\"2\">");
/* 185 */                   if (_jspx_meth_c_005fimport_005f0(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                     return;
/* 187 */                   out.write("</td>\n\t</tr>\n    <tr>\n\t\t<td colspan=\"2\">&nbsp;</td>\n\t</tr>\t\n</table>\n");
/*     */                   
/* 189 */                   IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 190 */                   _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 191 */                   _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fotherwise_005f0);
/*     */                   
/* 193 */                   _jspx_th_c_005fif_005f2.setTest("${isADDM == false}");
/* 194 */                   int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 195 */                   if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */                     for (;;) {
/* 197 */                       out.write("\n  <table class=\"lrbtborder\" width=\"95%\" border=\"0\"  cellpadding=\"0\" cellspacing=\"0\" align=\"center\" id=\"addFromDependentPage\">\n   <tr>\n    <td class=\"tablebottom\" colspan=\"2\" align='center'>\n\t<input type=\"button\" align=\"center\" class=\"buttons\"  value=\"");
/* 198 */                       out.print(FormatUtil.getString("Add"));
/* 199 */                       out.write("\" onclick=\"checkAndSubmit('submit')\"> &nbsp;&nbsp;\n\t<input type=\"button\" align=\"center\" class=\"buttons\"  value=\"");
/* 200 */                       out.print(FormatUtil.getString("Cancel"));
/* 201 */                       out.write("\" onclick=\"javascript:window.close();return;\">\n    </td>\n  </tr>\n </table>\n");
/* 202 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 203 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 207 */                   if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 208 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*     */                   }
/*     */                   
/* 211 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 212 */                   out.write("\n</form>\n");
/* 213 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 214 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 218 */               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 219 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */               }
/*     */               
/* 222 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 223 */               out.write(10);
/* 224 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 225 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 229 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 230 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*     */           }
/*     */           else
/* 233 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*     */         }
/* 235 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 236 */         out = _jspx_out;
/* 237 */         if ((out != null) && (out.getBufferSize() != 0))
/* 238 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 239 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 242 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 248 */     PageContext pageContext = _jspx_page_context;
/* 249 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 251 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 252 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 253 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 255 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 257 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 258 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 259 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 260 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 261 */       return true;
/*     */     }
/* 263 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 264 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fimport_005f0(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 269 */     PageContext pageContext = _jspx_page_context;
/* 270 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 272 */     ImportTag _jspx_th_c_005fimport_005f0 = (ImportTag)this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.get(ImportTag.class);
/* 273 */     _jspx_th_c_005fimport_005f0.setPageContext(_jspx_page_context);
/* 274 */     _jspx_th_c_005fimport_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 276 */     _jspx_th_c_005fimport_005f0.setUrl("/jsp/MGTree.jsp?width=100&expand=true&disableSelAllChilds=true");
/* 277 */     int[] _jspx_push_body_count_c_005fimport_005f0 = { 0 };
/*     */     try {
/* 279 */       int _jspx_eval_c_005fimport_005f0 = _jspx_th_c_005fimport_005f0.doStartTag();
/* 280 */       if (_jspx_th_c_005fimport_005f0.doEndTag() == 5)
/* 281 */         return true;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 284 */         int tmp112_111 = 0; int[] tmp112_109 = _jspx_push_body_count_c_005fimport_005f0; int tmp114_113 = tmp112_109[tmp112_111];tmp112_109[tmp112_111] = (tmp114_113 - 1); if (tmp114_113 <= 0) break;
/* 285 */         out = _jspx_page_context.popBody(); }
/* 286 */       _jspx_th_c_005fimport_005f0.doCatch(_jspx_exception);
/*     */     } finally {
/* 288 */       _jspx_th_c_005fimport_005f0.doFinally();
/* 289 */       this._005fjspx_005ftagPool_005fc_005fimport_0026_005furl_005fnobody.reuse(_jspx_th_c_005fimport_005f0);
/*     */     }
/* 291 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\SelectDependentMGroups_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */