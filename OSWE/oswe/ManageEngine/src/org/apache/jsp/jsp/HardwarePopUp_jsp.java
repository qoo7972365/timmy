/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.CheckboxTag;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class HardwarePopUp_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  19 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  37 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  41 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  50 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  54 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  55 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  56 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/*  57 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  58 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  59 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  60 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.release();
/*  61 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  68 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  71 */     JspWriter out = null;
/*  72 */     Object page = this;
/*  73 */     JspWriter _jspx_out = null;
/*  74 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  78 */       response.setContentType("text/html;charset=UTF-8");
/*  79 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  81 */       _jspx_page_context = pageContext;
/*  82 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  83 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  84 */       session = pageContext.getSession();
/*  85 */       out = pageContext.getOut();
/*  86 */       _jspx_out = out;
/*     */       
/*  88 */       out.write("<!--$Id$  HardwarePopUp.jsp-->\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/*  89 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  91 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n\n<title>");
/*  92 */       out.print(FormatUtil.getString("am.hardware.configuration.text"));
/*  93 */       out.write("</title>\n<table width=\"100%\"  height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n<tr>\n<td>&nbsp;<span class=\"headingboldwhite\" align=\"center\">");
/*  94 */       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */         return;
/*  96 */       out.write("</span><span class=\"headingwhite\"> </span>\n</td>\n</tr>\n</table>\n\n<br>\n\n");
/*     */       
/*  98 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/*  99 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 100 */       _jspx_th_html_005fform_005f0.setParent(null);
/*     */       
/* 102 */       _jspx_th_html_005fform_005f0.setAction("/HostResourceDispatch");
/*     */       
/* 104 */       _jspx_th_html_005fform_005f0.setMethod("post");
/*     */       
/* 106 */       _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 107 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 108 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */         for (;;) {
/* 110 */           out.write(10);
/* 111 */           String resourceid = (String)request.getAttribute("resourceid");
/* 112 */           out.write("\n\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 113 */           out.print(resourceid);
/* 114 */           out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"saveHardwareConfiguration\">\n   <table width=\"97%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\"  >\n       <tr align=\"left\">\n         <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 115 */           out.print(FormatUtil.getString("am.hardware.attributes.text"));
/* 116 */           out.write("</td>\n       </tr>\n\t\t<tr>\n\t\t\t <td height=\"30\" class=\"bodytext\">\n\t\t\t\t ");
/*     */           
/* 118 */           org.apache.taglibs.standard.tag.common.core.ChooseTag _jspx_th_c_005fchoose_005f0 = (org.apache.taglibs.standard.tag.common.core.ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(org.apache.taglibs.standard.tag.common.core.ChooseTag.class);
/* 119 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 120 */           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_html_005fform_005f0);
/* 121 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 122 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */             for (;;) {
/* 124 */               out.write("\n\t\t\t\t\t");
/*     */               
/* 126 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 127 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 128 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */               
/* 130 */               _jspx_th_c_005fwhen_005f0.setTest("${not empty disable}");
/* 131 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 132 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */                 for (;;) {
/* 134 */                   out.write("\n\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 135 */                   out.print(FormatUtil.getString("am.hardware.global.enable.steps"));
/* 136 */                   out.write("</td></tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t");
/* 137 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 138 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 142 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 143 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */               }
/*     */               
/* 146 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 147 */               out.write("\n\t\t\t\t\t");
/*     */               
/* 149 */               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 150 */               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 151 */               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 152 */               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 153 */               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */                 for (;;) {
/* 155 */                   out.write("\n\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n\t\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 156 */                   if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                     return;
/* 158 */                   out.write("&nbsp;");
/* 159 */                   out.print(FormatUtil.getString("am.hardware.component.temperature"));
/* 160 */                   out.write("</td></tr>\n\t\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 161 */                   if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                     return;
/* 163 */                   out.write("&nbsp;");
/* 164 */                   out.print(FormatUtil.getString("am.hardware.component.power"));
/* 165 */                   out.write("</td></tr>\n\t\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 166 */                   if (_jspx_meth_html_005fcheckbox_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                     return;
/* 168 */                   out.write("&nbsp;");
/* 169 */                   out.print(FormatUtil.getString("am.hardware.component.fan"));
/* 170 */                   out.write("</td></tr>\n\t\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 171 */                   if (_jspx_meth_html_005fcheckbox_005f3(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                     return;
/* 173 */                   out.write("&nbsp;");
/* 174 */                   out.print(FormatUtil.getString("am.hardware.component.processor"));
/* 175 */                   out.write("</td></tr>\n\t\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 176 */                   if (_jspx_meth_html_005fcheckbox_005f4(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                     return;
/* 178 */                   out.write("&nbsp;");
/* 179 */                   out.print(FormatUtil.getString("am.hardware.component.disk"));
/* 180 */                   out.write("</td></tr>\n\t\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 181 */                   if (_jspx_meth_html_005fcheckbox_005f5(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                     return;
/* 183 */                   out.write("&nbsp;");
/* 184 */                   out.print(FormatUtil.getString("am.hardware.component.array"));
/* 185 */                   out.write("</td></tr>\n\t\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 186 */                   if (_jspx_meth_html_005fcheckbox_005f6(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                     return;
/* 188 */                   out.write("&nbsp;");
/* 189 */                   out.print(FormatUtil.getString("am.hardware.component.chassis"));
/* 190 */                   out.write("</td></tr>\n\t\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 191 */                   if (_jspx_meth_html_005fcheckbox_005f7(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                     return;
/* 193 */                   out.write("&nbsp;");
/* 194 */                   out.print(FormatUtil.getString("am.hardware.component.memorydevice"));
/* 195 */                   out.write("</td></tr>\n\t\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 196 */                   if (_jspx_meth_html_005fcheckbox_005f8(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                     return;
/* 198 */                   out.write("&nbsp;");
/* 199 */                   out.print(FormatUtil.getString("am.hardware.component.voltages"));
/* 200 */                   out.write("</td></tr>\n\t\t\t\t\t\t\t<tr><td class=\"bodytext\">");
/* 201 */                   if (_jspx_meth_html_005fcheckbox_005f9(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                     return;
/* 203 */                   out.write("&nbsp;");
/* 204 */                   out.print(FormatUtil.getString("am.hardware.component.battery"));
/* 205 */                   out.write("</td></tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t");
/* 206 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 207 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 211 */               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 212 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */               }
/*     */               
/* 215 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 216 */               out.write("\n\t\t\t\t");
/* 217 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 218 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 222 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 223 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*     */           }
/*     */           
/* 226 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 227 */           out.write("\n\t\t\t </td>\t\t\t\t   \t \t\t\n\t\t</tr>\t\t   \n\t</table>\n\t\n\t<table width=\"97%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrbborder\" align=\"center\">\n       <tr class=\"tablebottom\">\n\t\t   <td height=\"27\"  align=\"center\">\n\t\t   ");
/*     */           
/* 229 */           IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 230 */           _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 231 */           _jspx_th_c_005fif_005f0.setParent(_jspx_th_html_005fform_005f0);
/*     */           
/* 233 */           _jspx_th_c_005fif_005f0.setTest("${empty disable}");
/* 234 */           int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 235 */           if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */             for (;;) {
/* 237 */               out.write("\n\t\t\t\t<input name=\"Submit\" type=\"submit\" class=\"buttons btn_highlt\" value=\"");
/* 238 */               out.print(FormatUtil.getString("webclient.common.date.applybutton.text"));
/* 239 */               out.write("\">\n\t\t   ");
/* 240 */               int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 241 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 245 */           if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 246 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*     */           }
/*     */           
/* 249 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 250 */           out.write("\n\t\t\t\t<input name=\"GoBack\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 251 */           out.print(FormatUtil.getString("am.webclient.common.close.text"));
/* 252 */           out.write("\"  onClick=\"window.close();\">&nbsp;&nbsp;\n\t\t   </td>\n       </tr>\n    </table>\n");
/* 253 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 254 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 258 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 259 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */       }
/*     */       else {
/* 262 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 263 */         out.write(10);
/* 264 */         out.write(10);
/*     */         
/* 266 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 267 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 268 */         _jspx_th_c_005fif_005f1.setParent(null);
/*     */         
/* 270 */         _jspx_th_c_005fif_005f1.setTest("${message!=null}");
/* 271 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 272 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */           for (;;) {
/* 274 */             out.write("\n<script>\nalert('");
/* 275 */             out.print(request.getAttribute("message"));
/* 276 */             out.write("')\n</script>\n");
/* 277 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 278 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 282 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 283 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*     */         }
/*     */         else {
/* 286 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 287 */           out.write(10);
/* 288 */           out.write(10);
/*     */         }
/* 290 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 291 */         out = _jspx_out;
/* 292 */         if ((out != null) && (out.getBufferSize() != 0))
/* 293 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 294 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 297 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 303 */     PageContext pageContext = _jspx_page_context;
/* 304 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 306 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 307 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 308 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 310 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 312 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 313 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 314 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 315 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 316 */       return true;
/*     */     }
/* 318 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 319 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 324 */     PageContext pageContext = _jspx_page_context;
/* 325 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 327 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 328 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 329 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 331 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.hardware.configuration.text");
/* 332 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 333 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 334 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 335 */       return true;
/*     */     }
/* 337 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 338 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 343 */     PageContext pageContext = _jspx_page_context;
/* 344 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 346 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 347 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 348 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 350 */     _jspx_th_html_005fcheckbox_005f0.setProperty("temperature");
/*     */     
/* 352 */     _jspx_th_html_005fcheckbox_005f0.setStyle("position:relative;top:2px");
/*     */     
/* 354 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("");
/* 355 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 356 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 357 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 358 */       return true;
/*     */     }
/* 360 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 361 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 366 */     PageContext pageContext = _jspx_page_context;
/* 367 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 369 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 370 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 371 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 373 */     _jspx_th_html_005fcheckbox_005f1.setProperty("power");
/*     */     
/* 375 */     _jspx_th_html_005fcheckbox_005f1.setStyle("position:relative;top:2px");
/*     */     
/* 377 */     _jspx_th_html_005fcheckbox_005f1.setOnclick("");
/* 378 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 379 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 380 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 381 */       return true;
/*     */     }
/* 383 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 384 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fcheckbox_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 389 */     PageContext pageContext = _jspx_page_context;
/* 390 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 392 */     CheckboxTag _jspx_th_html_005fcheckbox_005f2 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 393 */     _jspx_th_html_005fcheckbox_005f2.setPageContext(_jspx_page_context);
/* 394 */     _jspx_th_html_005fcheckbox_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 396 */     _jspx_th_html_005fcheckbox_005f2.setProperty("fan");
/*     */     
/* 398 */     _jspx_th_html_005fcheckbox_005f2.setStyle("position:relative;top:2px");
/*     */     
/* 400 */     _jspx_th_html_005fcheckbox_005f2.setOnclick("");
/* 401 */     int _jspx_eval_html_005fcheckbox_005f2 = _jspx_th_html_005fcheckbox_005f2.doStartTag();
/* 402 */     if (_jspx_th_html_005fcheckbox_005f2.doEndTag() == 5) {
/* 403 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 404 */       return true;
/*     */     }
/* 406 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 407 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fcheckbox_005f3(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 412 */     PageContext pageContext = _jspx_page_context;
/* 413 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 415 */     CheckboxTag _jspx_th_html_005fcheckbox_005f3 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 416 */     _jspx_th_html_005fcheckbox_005f3.setPageContext(_jspx_page_context);
/* 417 */     _jspx_th_html_005fcheckbox_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 419 */     _jspx_th_html_005fcheckbox_005f3.setProperty("processor");
/*     */     
/* 421 */     _jspx_th_html_005fcheckbox_005f3.setStyle("position:relative;top:2px");
/*     */     
/* 423 */     _jspx_th_html_005fcheckbox_005f3.setOnclick("");
/* 424 */     int _jspx_eval_html_005fcheckbox_005f3 = _jspx_th_html_005fcheckbox_005f3.doStartTag();
/* 425 */     if (_jspx_th_html_005fcheckbox_005f3.doEndTag() == 5) {
/* 426 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 427 */       return true;
/*     */     }
/* 429 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 430 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fcheckbox_005f4(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 435 */     PageContext pageContext = _jspx_page_context;
/* 436 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 438 */     CheckboxTag _jspx_th_html_005fcheckbox_005f4 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 439 */     _jspx_th_html_005fcheckbox_005f4.setPageContext(_jspx_page_context);
/* 440 */     _jspx_th_html_005fcheckbox_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 442 */     _jspx_th_html_005fcheckbox_005f4.setProperty("disk");
/*     */     
/* 444 */     _jspx_th_html_005fcheckbox_005f4.setStyle("position:relative;top:2px");
/*     */     
/* 446 */     _jspx_th_html_005fcheckbox_005f4.setOnclick("");
/* 447 */     int _jspx_eval_html_005fcheckbox_005f4 = _jspx_th_html_005fcheckbox_005f4.doStartTag();
/* 448 */     if (_jspx_th_html_005fcheckbox_005f4.doEndTag() == 5) {
/* 449 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f4);
/* 450 */       return true;
/*     */     }
/* 452 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f4);
/* 453 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fcheckbox_005f5(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 458 */     PageContext pageContext = _jspx_page_context;
/* 459 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 461 */     CheckboxTag _jspx_th_html_005fcheckbox_005f5 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 462 */     _jspx_th_html_005fcheckbox_005f5.setPageContext(_jspx_page_context);
/* 463 */     _jspx_th_html_005fcheckbox_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 465 */     _jspx_th_html_005fcheckbox_005f5.setProperty("array");
/*     */     
/* 467 */     _jspx_th_html_005fcheckbox_005f5.setStyle("position:relative;top:2px");
/*     */     
/* 469 */     _jspx_th_html_005fcheckbox_005f5.setOnclick("");
/* 470 */     int _jspx_eval_html_005fcheckbox_005f5 = _jspx_th_html_005fcheckbox_005f5.doStartTag();
/* 471 */     if (_jspx_th_html_005fcheckbox_005f5.doEndTag() == 5) {
/* 472 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f5);
/* 473 */       return true;
/*     */     }
/* 475 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f5);
/* 476 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fcheckbox_005f6(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 481 */     PageContext pageContext = _jspx_page_context;
/* 482 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 484 */     CheckboxTag _jspx_th_html_005fcheckbox_005f6 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 485 */     _jspx_th_html_005fcheckbox_005f6.setPageContext(_jspx_page_context);
/* 486 */     _jspx_th_html_005fcheckbox_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 488 */     _jspx_th_html_005fcheckbox_005f6.setProperty("chassis");
/*     */     
/* 490 */     _jspx_th_html_005fcheckbox_005f6.setStyle("position:relative;top:2px");
/*     */     
/* 492 */     _jspx_th_html_005fcheckbox_005f6.setOnclick("");
/* 493 */     int _jspx_eval_html_005fcheckbox_005f6 = _jspx_th_html_005fcheckbox_005f6.doStartTag();
/* 494 */     if (_jspx_th_html_005fcheckbox_005f6.doEndTag() == 5) {
/* 495 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f6);
/* 496 */       return true;
/*     */     }
/* 498 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f6);
/* 499 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fcheckbox_005f7(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 504 */     PageContext pageContext = _jspx_page_context;
/* 505 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 507 */     CheckboxTag _jspx_th_html_005fcheckbox_005f7 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 508 */     _jspx_th_html_005fcheckbox_005f7.setPageContext(_jspx_page_context);
/* 509 */     _jspx_th_html_005fcheckbox_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 511 */     _jspx_th_html_005fcheckbox_005f7.setProperty("memorydevice");
/*     */     
/* 513 */     _jspx_th_html_005fcheckbox_005f7.setStyle("position:relative;top:2px");
/*     */     
/* 515 */     _jspx_th_html_005fcheckbox_005f7.setOnclick("");
/* 516 */     int _jspx_eval_html_005fcheckbox_005f7 = _jspx_th_html_005fcheckbox_005f7.doStartTag();
/* 517 */     if (_jspx_th_html_005fcheckbox_005f7.doEndTag() == 5) {
/* 518 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f7);
/* 519 */       return true;
/*     */     }
/* 521 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f7);
/* 522 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fcheckbox_005f8(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 527 */     PageContext pageContext = _jspx_page_context;
/* 528 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 530 */     CheckboxTag _jspx_th_html_005fcheckbox_005f8 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 531 */     _jspx_th_html_005fcheckbox_005f8.setPageContext(_jspx_page_context);
/* 532 */     _jspx_th_html_005fcheckbox_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 534 */     _jspx_th_html_005fcheckbox_005f8.setProperty("voltage");
/*     */     
/* 536 */     _jspx_th_html_005fcheckbox_005f8.setStyle("position:relative;top:2px");
/*     */     
/* 538 */     _jspx_th_html_005fcheckbox_005f8.setOnclick("");
/* 539 */     int _jspx_eval_html_005fcheckbox_005f8 = _jspx_th_html_005fcheckbox_005f8.doStartTag();
/* 540 */     if (_jspx_th_html_005fcheckbox_005f8.doEndTag() == 5) {
/* 541 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f8);
/* 542 */       return true;
/*     */     }
/* 544 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f8);
/* 545 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fcheckbox_005f9(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 550 */     PageContext pageContext = _jspx_page_context;
/* 551 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 553 */     CheckboxTag _jspx_th_html_005fcheckbox_005f9 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 554 */     _jspx_th_html_005fcheckbox_005f9.setPageContext(_jspx_page_context);
/* 555 */     _jspx_th_html_005fcheckbox_005f9.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 557 */     _jspx_th_html_005fcheckbox_005f9.setProperty("battery");
/*     */     
/* 559 */     _jspx_th_html_005fcheckbox_005f9.setStyle("position:relative;top:2px");
/*     */     
/* 561 */     _jspx_th_html_005fcheckbox_005f9.setOnclick("");
/* 562 */     int _jspx_eval_html_005fcheckbox_005f9 = _jspx_th_html_005fcheckbox_005f9.doStartTag();
/* 563 */     if (_jspx_th_html_005fcheckbox_005f9.doEndTag() == 5) {
/* 564 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f9);
/* 565 */       return true;
/*     */     }
/* 567 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fstyle_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f9);
/* 568 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HardwarePopUp_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */