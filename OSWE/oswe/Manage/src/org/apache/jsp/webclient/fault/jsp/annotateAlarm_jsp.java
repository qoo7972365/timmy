/*     */ package org.apache.jsp.webclient.fault.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.tags.HiddenParam;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class annotateAlarm_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  20 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  26 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  27 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  44 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  57 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  58 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  62 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  63 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  64 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  65 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  66 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  67 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/*  68 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  69 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*  70 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  77 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  80 */     JspWriter out = null;
/*  81 */     Object page = this;
/*  82 */     JspWriter _jspx_out = null;
/*  83 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  87 */       response.setContentType("text/html;charset=UTF-8");
/*  88 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  90 */       _jspx_page_context = pageContext;
/*  91 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  92 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  93 */       session = pageContext.getSession();
/*  94 */       out = pageContext.getOut();
/*  95 */       _jspx_out = out;
/*     */       
/*  97 */       out.write("\n\n\n\n\n\n\n\n");
/*     */       
/*  99 */       String editRedirect = java.net.URLEncoder.encode("/fault/AlarmDetails.do?method=editAnnotation&entity=" + request.getParameter("entity") + "&displayname=Edit&annotationFwd=true");
/* 100 */       String setRedirect = java.net.URLEncoder.encode("/fault/AlarmDetails.do?method=setAnnotation&entity=" + request.getParameter("entity") + "&displayname=Health&annotationFwd=true");
/*     */       
/* 102 */       out.write("\n<html>\n<head>\n<title>");
/* 103 */       out.print(com.adventnet.appmanager.util.FormatUtil.getString("webclient.fault.alarmdetails.annotatenotes.button.annotate"));
/* 104 */       out.write("</title>\n");
/* 105 */       out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 106 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 108 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 109 */       out.write(10);
/* 110 */       out.write("\n<LINK REL=\"SHORTCUT ICON\" HREF='");
/* 111 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */         return;
/* 113 */       out.write(39);
/* 114 */       out.write(62);
/* 115 */       out.write("\n<script language=\"javascript\" src=\"/webclient/fault/js/fault.js\" type=\"text/javascript\"></script>\n</head>\n\n<body class=\"popupbg\" onLoad=\"focusAnnotate()\">\n");
/*     */       
/* 117 */       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 118 */       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 119 */       _jspx_th_c_005fchoose_005f0.setParent(null);
/* 120 */       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 121 */       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */         for (;;) {
/* 123 */           out.write(10);
/*     */           
/* 125 */           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 126 */           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 127 */           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */           
/* 129 */           _jspx_th_c_005fwhen_005f0.setTest("${empty param.edit}");
/* 130 */           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 131 */           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */             for (;;) {
/* 133 */               out.write(10);
/* 134 */               if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer) {
/* 135 */                 out.write("\n  \t <form name=\"annotation\" action=\"/fault/AlarmDetails.do?method=setAnnotation&entity=");
/* 136 */                 if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*     */                   return;
/* 138 */                 out.write("&displayname=");
/* 139 */                 if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*     */                   return;
/* 141 */                 out.write("&redirectto=");
/* 142 */                 out.print(setRedirect);
/* 143 */                 out.write("\" method=\"post\">\n  \t ");
/*     */               } else {
/* 145 */                 out.write("\n<form name=\"annotation\" action=\"/fault/AlarmDetails.do?method=setAnnotation&entity=");
/* 146 */                 if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*     */                   return;
/* 148 */                 out.write("&displayname=");
/* 149 */                 if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*     */                   return;
/* 151 */                 out.write("\" method=\"post\">\n");
/*     */               }
/* 153 */               out.write(10);
/* 154 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 155 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 159 */           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 160 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */           }
/*     */           
/* 163 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 164 */           out.write(10);
/*     */           
/* 166 */           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 167 */           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 168 */           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 169 */           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 170 */           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */             for (;;) {
/* 172 */               out.write(10);
/* 173 */               if (com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer) {
/* 174 */                 out.write("\n  \t <form name=\"annotation\" action=\"/fault/AlarmDetails.do?method=editAnnotation&entity=");
/* 175 */                 if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                   return;
/* 177 */                 out.write("&displayname=");
/* 178 */                 if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                   return;
/* 180 */                 out.write("&redirectto=");
/* 181 */                 out.print(editRedirect);
/* 182 */                 out.write("\" method=\"post\">\n  \t ");
/*     */               } else {
/* 184 */                 out.write("\n<form name=\"annotation\" action=\"/fault/AlarmDetails.do?method=editAnnotation&entity=");
/* 185 */                 if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                   return;
/* 187 */                 out.write("&displayname=");
/* 188 */                 if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*     */                   return;
/* 190 */                 out.write("\" method=\"post\">\n");
/*     */               }
/* 192 */               out.write(10);
/* 193 */               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 194 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 198 */           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 199 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */           }
/*     */           
/* 202 */           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 203 */           out.write(10);
/* 204 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 205 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 209 */       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 210 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*     */       }
/*     */       else {
/* 213 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 214 */         out.write(10);
/* 215 */         if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_page_context))
/*     */           return;
/* 217 */         out.write(10);
/* 218 */         if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_page_context))
/*     */           return;
/* 220 */         out.write(10);
/* 221 */         if (_jspx_meth_am_005fhiddenparam_005f2(_jspx_page_context))
/*     */           return;
/* 223 */         out.write(10);
/* 224 */         if (_jspx_meth_am_005fhiddenparam_005f3(_jspx_page_context))
/*     */           return;
/* 226 */         out.write("\n<input name=\"bulkannotate\" type=\"hidden\" value=\"");
/* 227 */         if (_jspx_meth_c_005fout_005f10(_jspx_page_context))
/*     */           return;
/* 229 */         out.write("\"/>\n");
/*     */         
/* 231 */         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 232 */         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 233 */         _jspx_th_c_005fif_005f0.setParent(null);
/*     */         
/* 235 */         _jspx_th_c_005fif_005f0.setTest("${!empty param.monitor }");
/* 236 */         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 237 */         if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */           for (;;) {
/* 239 */             out.write("\n<input type=\"hidden\" name=\"monitor\" value=\"");
/* 240 */             out.print(request.getParameter("monitor"));
/* 241 */             out.write(34);
/* 242 */             out.write(62);
/* 243 */             out.write(10);
/* 244 */             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 245 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 249 */         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 250 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */         }
/*     */         else {
/* 253 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 254 */           out.write(10);
/*     */           
/* 256 */           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 257 */           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 258 */           _jspx_th_c_005fif_005f1.setParent(null);
/*     */           
/* 260 */           _jspx_th_c_005fif_005f1.setTest("${empty param.redirectto}");
/* 261 */           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 262 */           if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */             for (;;) {
/* 264 */               out.write(10);
/*     */               
/* 266 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 267 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 268 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fif_005f1);
/*     */               
/* 270 */               _jspx_th_c_005fif_005f2.setTest("${!empty param.haid }");
/* 271 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 272 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */                 for (;;) {
/* 274 */                   out.write("\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 275 */                   out.print(request.getParameter("haid"));
/* 276 */                   out.write(34);
/* 277 */                   out.write(62);
/* 278 */                   out.write(10);
/* 279 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 280 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 284 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 285 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*     */               }
/*     */               
/* 288 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 289 */               out.write(10);
/* 290 */               if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*     */                 return;
/* 292 */               out.write(10);
/* 293 */               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 294 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 298 */           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 299 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*     */           }
/*     */           else {
/* 302 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 303 */             out.write(10);
/*     */             
/* 305 */             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 306 */             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 307 */             _jspx_th_c_005fif_005f4.setParent(null);
/*     */             
/* 309 */             _jspx_th_c_005fif_005f4.setTest("${!empty param.redirectto}");
/* 310 */             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 311 */             if (_jspx_eval_c_005fif_005f4 != 0) {
/*     */               for (;;) {
/* 313 */                 out.write("\n<input type=\"hidden\" name=\"redirectto\" value=\"");
/* 314 */                 out.print(request.getParameter("redirectto"));
/* 315 */                 out.write(34);
/* 316 */                 out.write(62);
/* 317 */                 out.write(10);
/* 318 */                 if (_jspx_meth_am_005fhiddenparam_005f4(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*     */                   return;
/* 320 */                 out.write(10);
/* 321 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 322 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 326 */             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 327 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*     */             }
/*     */             else {
/* 330 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 331 */               out.write("\n<table width=\"100%\" height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n<tr>\n<td>&nbsp;<span class=\"headingboldwhite\">");
/* 332 */               if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */                 return;
/* 334 */               out.write("</span><span class=\"headingwhite\"> </span>\n</td>\n</tr>\n\n</table>\n<br>\n<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" class=\"lrtbdarkborder\">\n  <tr>\n      ");
/* 335 */               if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*     */                 return;
/* 337 */               out.write("\n      ");
/* 338 */               String dispnamevalue = (String)pageContext.getAttribute("dispname");
/*     */               
/* 340 */               out.write("\n      <td class=\"tableheadingbborder\" colspan=\"3\" height=\"30\">");
/* 341 */               out.print(com.adventnet.appmanager.util.FormatUtil.getString("webclient.fault.alarmdetails.annotatenotes.button.annotate"));
/* 342 */               out.write("\n        </td>\n    </tr>\n    <tr>\n      <td colspan=\"3\" align=\"right\" valign=\"top\">\n        <table width=\"100%\" border=\"0\" align=\"left\" cellpadding=\"5\" cellspacing=\"0\">\n          <td width=\"25%\" class=\"bodytext label-align\">");
/* 343 */               if (_jspx_meth_fmt_005fmessage_005f1(_jspx_page_context))
/*     */                 return;
/* 345 */               out.write(" </td>\n\n          <td width=\"75%\" height=\"95\">\n            <textarea name=\"text\" rows=\"8\" class=\"textarea\" style=width:250>");
/* 346 */               if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*     */                 return;
/* 348 */               out.write("</textarea>\n          </td>\n          </tr>\n\n        <tr>\n          <td height=\"25\" colspan=\"3\" align=\"center\" class=\"tablebottom\"> <input type=\"button\" name=\"Annotate\" class=\"buttons btn_highlt\" value=\"");
/* 349 */               if (_jspx_meth_fmt_005fmessage_005f2(_jspx_page_context))
/*     */                 return;
/* 351 */               out.write("\"  class=\"button\" onClick=\"javascript:validateAnnotationMessage('");
/* 352 */               if (_jspx_meth_fmt_005fmessage_005f3(_jspx_page_context))
/*     */                 return;
/* 354 */               out.write("')\">\n            <input type=\"button\" name=\"Cancel\" value=\"");
/* 355 */               if (_jspx_meth_fmt_005fmessage_005f4(_jspx_page_context))
/*     */                 return;
/* 357 */               out.write("\"  class=\"buttons btn_link\" onClick=\"javascript:MM_callJS('window.close()')\">\n            </td>\n          </tr>\n        </table></td>\n    </tr>\n</table>\n</form>\n</body>\n</html>\n");
/*     */             }
/* 359 */           } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 360 */         out = _jspx_out;
/* 361 */         if ((out != null) && (out.getBufferSize() != 0))
/* 362 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 363 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 366 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 372 */     PageContext pageContext = _jspx_page_context;
/* 373 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 375 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 376 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 377 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 379 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 381 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 382 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 383 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 384 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 385 */       return true;
/*     */     }
/* 387 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 388 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 393 */     PageContext pageContext = _jspx_page_context;
/* 394 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 396 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 397 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 398 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 400 */     _jspx_th_c_005fout_005f1.setValue("${faviconHref}");
/*     */     
/* 402 */     _jspx_th_c_005fout_005f1.setDefault("/favicon.ico");
/* 403 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 404 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 405 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 406 */       return true;
/*     */     }
/* 408 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 409 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 414 */     PageContext pageContext = _jspx_page_context;
/* 415 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 417 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 418 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 419 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*     */     
/* 421 */     _jspx_th_c_005fout_005f2.setValue("${entity}");
/* 422 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 423 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 424 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 425 */       return true;
/*     */     }
/* 427 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 428 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 433 */     PageContext pageContext = _jspx_page_context;
/* 434 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 436 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 437 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 438 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*     */     
/* 440 */     _jspx_th_c_005fout_005f3.setValue("${param.displayname}");
/* 441 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 442 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 443 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 444 */       return true;
/*     */     }
/* 446 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 447 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 452 */     PageContext pageContext = _jspx_page_context;
/* 453 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 455 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 456 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 457 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*     */     
/* 459 */     _jspx_th_c_005fout_005f4.setValue("${entity}");
/* 460 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 461 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 462 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 463 */       return true;
/*     */     }
/* 465 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 466 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 471 */     PageContext pageContext = _jspx_page_context;
/* 472 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 474 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 475 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 476 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*     */     
/* 478 */     _jspx_th_c_005fout_005f5.setValue("${param.displayname}");
/* 479 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 480 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 481 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 482 */       return true;
/*     */     }
/* 484 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 485 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 490 */     PageContext pageContext = _jspx_page_context;
/* 491 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 493 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 494 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 495 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 497 */     _jspx_th_c_005fout_005f6.setValue("${entity}");
/* 498 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 499 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 500 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 501 */       return true;
/*     */     }
/* 503 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 504 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 509 */     PageContext pageContext = _jspx_page_context;
/* 510 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 512 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 513 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 514 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 516 */     _jspx_th_c_005fout_005f7.setValue("${param.displayname}");
/* 517 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 518 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 519 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 520 */       return true;
/*     */     }
/* 522 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 523 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 528 */     PageContext pageContext = _jspx_page_context;
/* 529 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 531 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 532 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 533 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 535 */     _jspx_th_c_005fout_005f8.setValue("${entity}");
/* 536 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 537 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 538 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 539 */       return true;
/*     */     }
/* 541 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 542 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 547 */     PageContext pageContext = _jspx_page_context;
/* 548 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 550 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 551 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 552 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*     */     
/* 554 */     _jspx_th_c_005fout_005f9.setValue("${param.displayname}");
/* 555 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 556 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 557 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 558 */       return true;
/*     */     }
/* 560 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 561 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_am_005fhiddenparam_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 566 */     PageContext pageContext = _jspx_page_context;
/* 567 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 569 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 570 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 571 */     _jspx_th_am_005fhiddenparam_005f0.setParent(null);
/*     */     
/* 573 */     _jspx_th_am_005fhiddenparam_005f0.setName("source");
/* 574 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 575 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 576 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 577 */       return true;
/*     */     }
/* 579 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 580 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_am_005fhiddenparam_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 585 */     PageContext pageContext = _jspx_page_context;
/* 586 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 588 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 589 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/* 590 */     _jspx_th_am_005fhiddenparam_005f1.setParent(null);
/*     */     
/* 592 */     _jspx_th_am_005fhiddenparam_005f1.setName("category");
/* 593 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/* 594 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/* 595 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 596 */       return true;
/*     */     }
/* 598 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 599 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_am_005fhiddenparam_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 604 */     PageContext pageContext = _jspx_page_context;
/* 605 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 607 */     HiddenParam _jspx_th_am_005fhiddenparam_005f2 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 608 */     _jspx_th_am_005fhiddenparam_005f2.setPageContext(_jspx_page_context);
/* 609 */     _jspx_th_am_005fhiddenparam_005f2.setParent(null);
/*     */     
/* 611 */     _jspx_th_am_005fhiddenparam_005f2.setName("userName");
/* 612 */     int _jspx_eval_am_005fhiddenparam_005f2 = _jspx_th_am_005fhiddenparam_005f2.doStartTag();
/* 613 */     if (_jspx_th_am_005fhiddenparam_005f2.doEndTag() == 5) {
/* 614 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 615 */       return true;
/*     */     }
/* 617 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 618 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_am_005fhiddenparam_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 623 */     PageContext pageContext = _jspx_page_context;
/* 624 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 626 */     HiddenParam _jspx_th_am_005fhiddenparam_005f3 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 627 */     _jspx_th_am_005fhiddenparam_005f3.setPageContext(_jspx_page_context);
/* 628 */     _jspx_th_am_005fhiddenparam_005f3.setParent(null);
/*     */     
/* 630 */     _jspx_th_am_005fhiddenparam_005f3.setName("time");
/* 631 */     int _jspx_eval_am_005fhiddenparam_005f3 = _jspx_th_am_005fhiddenparam_005f3.doStartTag();
/* 632 */     if (_jspx_th_am_005fhiddenparam_005f3.doEndTag() == 5) {
/* 633 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 634 */       return true;
/*     */     }
/* 636 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f3);
/* 637 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f10(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 642 */     PageContext pageContext = _jspx_page_context;
/* 643 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 645 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 646 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 647 */     _jspx_th_c_005fout_005f10.setParent(null);
/*     */     
/* 649 */     _jspx_th_c_005fout_005f10.setValue("${bulkannotate}");
/* 650 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 651 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 652 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 653 */       return true;
/*     */     }
/* 655 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 656 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 661 */     PageContext pageContext = _jspx_page_context;
/* 662 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 664 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 665 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 666 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fif_005f1);
/*     */     
/* 668 */     _jspx_th_c_005fif_005f3.setTest("${empty param.haid }");
/* 669 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 670 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */       for (;;) {
/* 672 */         out.write(10);
/* 673 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 674 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 678 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 679 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 680 */       return true;
/*     */     }
/* 682 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 683 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_am_005fhiddenparam_005f4(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 688 */     PageContext pageContext = _jspx_page_context;
/* 689 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 691 */     HiddenParam _jspx_th_am_005fhiddenparam_005f4 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 692 */     _jspx_th_am_005fhiddenparam_005f4.setPageContext(_jspx_page_context);
/* 693 */     _jspx_th_am_005fhiddenparam_005f4.setParent((Tag)_jspx_th_c_005fif_005f4);
/*     */     
/* 695 */     _jspx_th_am_005fhiddenparam_005f4.setName("fromIcon");
/* 696 */     int _jspx_eval_am_005fhiddenparam_005f4 = _jspx_th_am_005fhiddenparam_005f4.doStartTag();
/* 697 */     if (_jspx_th_am_005fhiddenparam_005f4.doEndTag() == 5) {
/* 698 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 699 */       return true;
/*     */     }
/* 701 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f4);
/* 702 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 707 */     PageContext pageContext = _jspx_page_context;
/* 708 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 710 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 711 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 712 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 714 */     _jspx_th_fmt_005fmessage_005f0.setKey("webclient.fault.alarm.annotate.header.text");
/* 715 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 716 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 717 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 718 */       return true;
/*     */     }
/* 720 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 721 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 726 */     PageContext pageContext = _jspx_page_context;
/* 727 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 729 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 730 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 731 */     _jspx_th_c_005fset_005f0.setParent(null);
/*     */     
/* 733 */     _jspx_th_c_005fset_005f0.setVar("dispname");
/* 734 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 735 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 736 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 737 */         out = _jspx_page_context.pushBody();
/* 738 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 739 */         _jspx_th_c_005fset_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 742 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 743 */           return true;
/* 744 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 745 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 748 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 749 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 752 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 753 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 754 */       return true;
/*     */     }
/* 756 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 757 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 762 */     PageContext pageContext = _jspx_page_context;
/* 763 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 765 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 766 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 767 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fset_005f0);
/*     */     
/* 769 */     _jspx_th_c_005fout_005f11.setValue("${param.displayname}");
/* 770 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 771 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 772 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 773 */       return true;
/*     */     }
/* 775 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 776 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 781 */     PageContext pageContext = _jspx_page_context;
/* 782 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 784 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 785 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 786 */     _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*     */     
/* 788 */     _jspx_th_fmt_005fmessage_005f1.setKey("webclient.topo.addnoderesult.message");
/* 789 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 790 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 791 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 792 */       return true;
/*     */     }
/* 794 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 795 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 800 */     PageContext pageContext = _jspx_page_context;
/* 801 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 803 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 804 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 805 */     _jspx_th_c_005fif_005f5.setParent(null);
/*     */     
/* 807 */     _jspx_th_c_005fif_005f5.setTest("${!empty param.edit}");
/* 808 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 809 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*     */       for (;;) {
/* 811 */         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 812 */           return true;
/* 813 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 814 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 818 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 819 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 820 */       return true;
/*     */     }
/* 822 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 823 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 828 */     PageContext pageContext = _jspx_page_context;
/* 829 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 831 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 832 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 833 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f5);
/*     */     
/* 835 */     _jspx_th_c_005fout_005f12.setValue("${notes}");
/* 836 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 837 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 838 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 839 */       return true;
/*     */     }
/* 841 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 842 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 847 */     PageContext pageContext = _jspx_page_context;
/* 848 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 850 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 851 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 852 */     _jspx_th_fmt_005fmessage_005f2.setParent(null);
/*     */     
/* 854 */     _jspx_th_fmt_005fmessage_005f2.setKey("webclient.fault.alarmdetails.annotatenotes.button.annotate");
/* 855 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 856 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 857 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 858 */       return true;
/*     */     }
/* 860 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 861 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 866 */     PageContext pageContext = _jspx_page_context;
/* 867 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 869 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 870 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 871 */     _jspx_th_fmt_005fmessage_005f3.setParent(null);
/*     */     
/* 873 */     _jspx_th_fmt_005fmessage_005f3.setKey("webclient.fault.alarmdetails.annotatenotes.error");
/* 874 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 875 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 876 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 877 */       return true;
/*     */     }
/* 879 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 880 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f4(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 885 */     PageContext pageContext = _jspx_page_context;
/* 886 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 888 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 889 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 890 */     _jspx_th_fmt_005fmessage_005f4.setParent(null);
/*     */     
/* 892 */     _jspx_th_fmt_005fmessage_005f4.setKey("webclient.fault.alarmdetails.button.cancel");
/* 893 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 894 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 895 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 896 */       return true;
/*     */     }
/* 898 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 899 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\webclient\fault\jsp\annotateAlarm_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */