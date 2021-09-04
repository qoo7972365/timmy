/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.util.ArrayList;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.NotPresentTag;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ 
/*     */ public final class HostResourceCommonLeft_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  28 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*  29 */   static { _jspx_dependants.put("/jsp/includes/MonitorTemplate.jspf", Long.valueOf(1473429417000L));
/*  30 */     _jspx_dependants.put("/jsp/includes/CommonLeftPage.jspf", Long.valueOf(1473429417000L));
/*     */   }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  45 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  49 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  57 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  61 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  62 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/*  63 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  64 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  65 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  66 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  67 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  74 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  77 */     JspWriter out = null;
/*  78 */     Object page = this;
/*  79 */     JspWriter _jspx_out = null;
/*  80 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  84 */       response.setContentType("text/html");
/*  85 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  87 */       _jspx_page_context = pageContext;
/*  88 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  89 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  90 */       session = pageContext.getSession();
/*  91 */       out = pageContext.getOut();
/*  92 */       _jspx_out = out;
/*     */       
/*  94 */       out.write(10);
/*  95 */       out.write(10);
/*  96 */       out.write("\n<!--$Id$-->\n\n\n\n");
/*  97 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/*  99 */       out.write(10);
/* 100 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*     */         return;
/* 102 */       out.write(10);
/* 103 */       out.write(10);
/* 104 */       out.write(10);
/* 105 */       out.write("\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n");
/*     */       
/*     */ 
/* 108 */       String requestpath = "/images/mm_menu2.jsp";
/*     */       
/* 110 */       ArrayList menupos = new ArrayList(5);
/* 111 */       if (request.isUserInRole("OPERATOR"))
/*     */       {
/* 113 */         menupos.add("160");
/* 114 */         menupos.add("202");
/* 115 */         menupos.add("224");
/* 116 */         menupos.add("245");
/* 117 */         menupos.add("139");
/* 118 */         menupos.add("181");
/* 119 */         menupos.add("287");
/* 120 */         menupos.add("266");
/* 121 */         menupos.add("308");
/* 122 */         menupos.add("328");
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 127 */       else if (com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 128 */         menupos.add("160");
/* 129 */         menupos.add("205");
/* 130 */         menupos.add("220");
/* 131 */         menupos.add("240");
/* 132 */         menupos.add("140");
/* 133 */         menupos.add("180");
/* 134 */         menupos.add("265");
/* 135 */         menupos.add("285");
/* 136 */         menupos.add("305");
/* 137 */         menupos.add("325");
/*     */       } else {
/* 139 */         menupos.add("218");
/* 140 */         menupos.add("261");
/* 141 */         menupos.add("282");
/* 142 */         menupos.add("303");
/* 143 */         menupos.add("197");
/* 144 */         menupos.add("239");
/* 145 */         menupos.add("324");
/* 146 */         menupos.add("345");
/* 147 */         menupos.add("366");
/* 148 */         menupos.add("400");
/*     */       }
/*     */       
/* 151 */       pageContext.setAttribute("menupos", menupos);
/*     */       
/* 153 */       out.write(10);
/* 154 */       org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, requestpath, out, false);
/* 155 */       out.write("\n</script>\n<SCRIPT language=JavaScript1.2>mmLoadMenus()</SCRIPT>\n\n");
/* 156 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/*     */       
/* 158 */       String categorytype = com.adventnet.appmanager.util.Constants.getCategorytype();
/* 159 */       pageContext.setAttribute("categorytype", categorytype);
/*     */       
/* 161 */       out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n      <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 162 */       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 163 */       out.write("</td>\n  </tr>\n\n\n");
/*     */       
/* 165 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 166 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 167 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */       
/* 169 */       _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 170 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 171 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */         for (;;) {
/* 173 */           out.write(10);
/* 174 */           out.write(10);
/*     */           
/* 176 */           IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 177 */           _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 178 */           _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */           
/* 180 */           _jspx_th_c_005fif_005f3.setTest("${categorytype!='DATABASE' && categorytype!='LAMP'}");
/* 181 */           int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 182 */           if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */             for (;;) {
/* 184 */               out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" >");
/* 185 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 186 */               out.write("</a>\n\n</td>\n</tr>\n");
/* 187 */               int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 188 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 192 */           if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 193 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*     */           }
/*     */           
/* 196 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 197 */           out.write(10);
/*     */           
/* 199 */           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 200 */           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 201 */           _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */           
/* 203 */           _jspx_th_c_005fif_005f4.setTest("${categorytype!='DATABASE' && categorytype!='LAMP' && categorytype!='CLOUD'}");
/* 204 */           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 205 */           if (_jspx_eval_c_005fif_005f4 != 0) {
/*     */             for (;;) {
/* 207 */               out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" >");
/* 208 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 209 */               out.write("</a>\n\n</td>\n</tr>\n");
/* 210 */               int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 211 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 215 */           if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 216 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*     */           }
/*     */           
/* 219 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 220 */           out.write(10);
/*     */           
/* 222 */           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 223 */           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 224 */           _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */           
/* 226 */           _jspx_th_c_005fif_005f5.setTest("${categorytype!='J2EE'}");
/* 227 */           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 228 */           if (_jspx_eval_c_005fif_005f5 != 0) {
/*     */             for (;;) {
/* 230 */               out.write("\n<tr  >\n<td  align=\"left\" class=\"leftlinkstd\"><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\">");
/* 231 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 232 */               out.write("</a>\n\n</td>\n</tr>\n");
/* 233 */               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 234 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 238 */           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 239 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*     */           }
/*     */           
/* 242 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 243 */           out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\">");
/* 244 */           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 245 */           out.write("</a>\n\n</td>\n</tr>\n");
/*     */           
/* 247 */           IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 248 */           _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 249 */           _jspx_th_c_005fif_005f6.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */           
/* 251 */           _jspx_th_c_005fif_005f6.setTest("${categorytype!='DATABASE'}");
/* 252 */           int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 253 */           if (_jspx_eval_c_005fif_005f6 != 0) {
/*     */             for (;;) {
/* 255 */               out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" >");
/* 256 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 257 */               out.write("</a>\n\n</td>\n</tr>\n");
/* 258 */               int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 259 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 263 */           if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 264 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*     */           }
/*     */           
/* 267 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 268 */           out.write("\n<tr >\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\">");
/* 269 */           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 270 */           out.write("    </a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" >");
/* 271 */           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 272 */           out.write("</a>\n\n</td>\n</tr>\n\n");
/* 273 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 274 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 278 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 279 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*     */       }
/*     */       else {
/* 282 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 283 */         out.write(10);
/* 284 */         out.write(10);
/*     */         
/* 286 */         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 287 */         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 288 */         _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */         
/* 290 */         _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 291 */         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 292 */         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */           for (;;) {
/* 294 */             out.write(10);
/* 295 */             out.write(10);
/*     */             
/* 297 */             IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 298 */             _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 299 */             _jspx_th_c_005fif_005f7.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 301 */             _jspx_th_c_005fif_005f7.setTest("${categorytype!='DATABASE' && categorytype!='LAMP'}");
/* 302 */             int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 303 */             if (_jspx_eval_c_005fif_005f7 != 0) {
/*     */               for (;;) {
/* 305 */                 out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" >");
/* 306 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 307 */                 out.write("</a>\n\n</td>\n</tr>\n");
/* 308 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 309 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 313 */             if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 314 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*     */             }
/*     */             
/* 317 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 318 */             out.write(10);
/*     */             
/* 320 */             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 321 */             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 322 */             _jspx_th_c_005fif_005f8.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 324 */             _jspx_th_c_005fif_005f8.setTest("${categorytype!='DATABASE' && categorytype!='LAMP' && categorytype!='CLOUD'}");
/* 325 */             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 326 */             if (_jspx_eval_c_005fif_005f8 != 0) {
/*     */               for (;;) {
/* 328 */                 out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" >");
/* 329 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 330 */                 out.write("</a>\n\n</td>\n</tr>\n");
/* 331 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 332 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 336 */             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 337 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*     */             }
/*     */             
/* 340 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 341 */             out.write("\n\n\n<tr  >\n<td  align=\"left\" class=\"leftlinkstd\"><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\">");
/* 342 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 343 */             out.write("</a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\">");
/* 344 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 345 */             out.write("</a>\n\n</td>\n</tr>\n\n");
/*     */             
/* 347 */             IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 348 */             _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 349 */             _jspx_th_c_005fif_005f9.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 351 */             _jspx_th_c_005fif_005f9.setTest("${categorytype!='DATABASE'}");
/* 352 */             int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 353 */             if (_jspx_eval_c_005fif_005f9 != 0) {
/*     */               for (;;) {
/* 355 */                 out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" >");
/* 356 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 357 */                 out.write("</a>\n\n</td>\n</tr>\n");
/* 358 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 359 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 363 */             if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 364 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*     */             }
/*     */             
/* 367 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 368 */             out.write("\n<tr >\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\">");
/* 369 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 370 */             out.write(" </a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" >");
/* 371 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 372 */             out.write("</a>\n\n</td>\n</tr>\n");
/* 373 */             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 374 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 378 */         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 379 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*     */         }
/*     */         else {
/* 382 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 383 */           out.write("\n\n  <tr >\n  <td class=\"leftlinkstd\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=CAM&network=Custom-Application\"   class=\"new-left-links\">");
/* 384 */           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 385 */           out.write("</a></td>\n\n</tr>\n");
/*     */           
/* 387 */           IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 388 */           _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 389 */           _jspx_th_c_005fif_005f10.setParent(null);
/*     */           
/* 391 */           _jspx_th_c_005fif_005f10.setTest("${categorytype!='CLOUD'}");
/* 392 */           int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 393 */           if (_jspx_eval_c_005fif_005f10 != 0) {
/*     */             for (;;) {
/* 395 */               out.write("\n<tr >\n  <td class=\"leftlinkstd\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MOM\"   class=\"new-left-links\">");
/* 396 */               out.print(FormatUtil.getString("Middleware/Portal"));
/* 397 */               out.write("</a></td>\n \n</tr>\n");
/* 398 */               int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 399 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 403 */           if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 404 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/*     */           }
/*     */           else {
/* 407 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 408 */             out.write(10);
/*     */             
/* 410 */             String[] categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 411 */             for (int i = 0; i < categoryLink.length; i++)
/*     */             {
/* 413 */               if ((!categoryLink[i].equals("APP")) && (!categoryLink[i].equals("TM")) && (!categoryLink[i].equals("ERP")) && (!categoryLink[i].equals("DBS")) && (!categoryLink[i].equals("SYS")) && (!categoryLink[i].equals("MS")) && (!categoryLink[i].equals("SCR")) && (!categoryLink[i].equals("NWD")) && (!categoryLink[i].equals("SER")) && (!categoryLink[i].equals("URL")) && (!categoryLink[i].equals("CAM")) && (!categoryLink[i].equals("MOM")) && (!categoryLink[i].equals("SAN")) && (!categoryLink[i].equals("EMO")))
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 422 */                 out.write("\n<tr>\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href='");
/* 423 */                 out.print("/showresource.do?method=showResourceTypes&detailspage=true&group=" + categoryLink[i]);
/* 424 */                 out.write(39);
/* 425 */                 out.write(62);
/* 426 */                 out.print(FormatUtil.getString(com.adventnet.appmanager.util.Constants.categoryTitle[i]));
/* 427 */                 out.write("</a>\n</td>\n</tr>\n");
/*     */               }
/*     */             }
/*     */             
/* 431 */             out.write("\n\n</table>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 432 */             out.write(10);
/* 433 */             response.setContentType("text/html;charset=UTF-8");
/* 434 */             out.write(10);
/* 435 */             out.write(10);
/* 436 */             out.write("\n<br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n<tr><td width=\"80%\" class=\"leftlinksquicknote\">");
/* 437 */             out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 438 */             out.write("</td>\n    <td width=\"20%\" class=\"leftlinksheading\"><img src=\"/images/");
/* 439 */             if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */               return;
/* 441 */             out.write("/img_quicknote.gif\"      hspace=\"5\"></td>\n  </tr>\n<tr>\n");
/*     */             
/* 443 */             IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 444 */             _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 445 */             _jspx_th_c_005fif_005f11.setParent(null);
/*     */             
/* 447 */             _jspx_th_c_005fif_005f11.setTest("${!empty param.editProcess && param.editProcess=='true'}");
/* 448 */             int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 449 */             if (_jspx_eval_c_005fif_005f11 != 0) {
/*     */               for (;;) {
/* 451 */                 out.write("\n\n<td class=\"quicknote\" colspan=\"2\">");
/* 452 */                 out.print(FormatUtil.getString("am.webclient.hostresourcecommonleft.quicknote"));
/* 453 */                 out.write(" </td>\n");
/* 454 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 455 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 459 */             if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 460 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/*     */             }
/*     */             else {
/* 463 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 464 */               out.write(10);
/*     */               
/* 466 */               IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 467 */               _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 468 */               _jspx_th_c_005fif_005f12.setParent(null);
/*     */               
/* 470 */               _jspx_th_c_005fif_005f12.setTest("${empty param.editProcess && empty param.actiononServices}");
/* 471 */               int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 472 */               if (_jspx_eval_c_005fif_005f12 != 0) {
/*     */                 for (;;) {
/* 474 */                   out.write("\n<td class=\"quicknote\">");
/* 475 */                   out.print(FormatUtil.getString("am.webclient.hostresourcecommonleft.quicknote1"));
/* 476 */                   out.write("</td>\n");
/* 477 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 478 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 482 */               if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 483 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*     */               }
/*     */               else {
/* 486 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 487 */                 out.write(10);
/*     */                 
/* 489 */                 IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 490 */                 _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 491 */                 _jspx_th_c_005fif_005f13.setParent(null);
/*     */                 
/* 493 */                 _jspx_th_c_005fif_005f13.setTest("${!empty param.actiononServices && param.actiononServices=='Edit'}");
/* 494 */                 int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 495 */                 if (_jspx_eval_c_005fif_005f13 != 0) {
/*     */                   for (;;) {
/* 497 */                     out.write("\n<td class=\"quicknote\" colspan=\"2\">");
/* 498 */                     out.print(FormatUtil.getString("am.webclient.hostresourcecommonleft.quicknote2"));
/* 499 */                     out.write("</td>\n");
/* 500 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 501 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 505 */                 if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 506 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/*     */                 }
/*     */                 else {
/* 509 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 510 */                   out.write("\n</tr>\n</table>\n");
/*     */                 }
/* 512 */               } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 513 */         out = _jspx_out;
/* 514 */         if ((out != null) && (out.getBufferSize() != 0))
/* 515 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 516 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 519 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 525 */     PageContext pageContext = _jspx_page_context;
/* 526 */     JspWriter out = _jspx_page_context.getOut();
/* 527 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 528 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*     */     
/* 530 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 531 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 532 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 534 */     _jspx_th_c_005fif_005f0.setTest("${empty appservers}");
/* 535 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 536 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 538 */         out.write(10);
/* 539 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/showresource.do?method=showResourceTypes&includeleftmenu=true", out, false);
/* 540 */         out.write(10);
/* 541 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 542 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 546 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 547 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 548 */       return true;
/*     */     }
/* 550 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 551 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 556 */     PageContext pageContext = _jspx_page_context;
/* 557 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 559 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 560 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 561 */     _jspx_th_c_005fif_005f1.setParent(null);
/*     */     
/* 563 */     _jspx_th_c_005fif_005f1.setTest("${!empty param.haid}");
/* 564 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 565 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */       for (;;) {
/* 567 */         out.write(10);
/* 568 */         if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 569 */           return true;
/* 570 */         out.write("\n      ");
/* 571 */         if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 572 */           return true;
/* 573 */         out.write(10);
/* 574 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 575 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 579 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 580 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 581 */       return true;
/*     */     }
/* 583 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 584 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 589 */     PageContext pageContext = _jspx_page_context;
/* 590 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 592 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 593 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 594 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*     */     
/* 596 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 597 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*     */     try {
/* 599 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 600 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*     */         for (;;) {
/* 602 */           out.write("\n      <fmt:parseNumber var=\"wnhaid\" value=\"${param.haid}\"/>\n      ");
/* 603 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 604 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 608 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 609 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 612 */         int tmp143_142 = 0; int[] tmp143_140 = _jspx_push_body_count_c_005fcatch_005f0; int tmp145_144 = tmp143_140[tmp143_142];tmp143_140[tmp143_142] = (tmp145_144 - 1); if (tmp145_144 <= 0) break;
/* 613 */         out = _jspx_page_context.popBody(); }
/* 614 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*     */     } finally {
/* 616 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 617 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*     */     }
/* 619 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 624 */     PageContext pageContext = _jspx_page_context;
/* 625 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 627 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 628 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 629 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*     */     
/* 631 */     _jspx_th_c_005fif_005f2.setTest("${(empty invalidhaid)}");
/* 632 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 633 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 635 */         out.write(10);
/* 636 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 637 */           return true;
/* 638 */         out.write(10);
/* 639 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 640 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 644 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 645 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 646 */       return true;
/*     */     }
/* 648 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 649 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 654 */     PageContext pageContext = _jspx_page_context;
/* 655 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 657 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 658 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 659 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 661 */     _jspx_th_c_005fset_005f0.setVar("haid");
/* 662 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 663 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 664 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 665 */         out = _jspx_page_context.pushBody();
/* 666 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 667 */         _jspx_th_c_005fset_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 670 */         out.write(10);
/* 671 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 672 */           return true;
/* 673 */         out.write(10);
/* 674 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 675 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 678 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 679 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 682 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 683 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 684 */       return true;
/*     */     }
/* 686 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 687 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 692 */     PageContext pageContext = _jspx_page_context;
/* 693 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 695 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 696 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 697 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*     */     
/* 699 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 700 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 701 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 702 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 703 */       return true;
/*     */     }
/* 705 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 706 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 711 */     PageContext pageContext = _jspx_page_context;
/* 712 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 714 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 715 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 716 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 718 */     _jspx_th_c_005fout_005f1.setValue("${selectedskin}");
/*     */     
/* 720 */     _jspx_th_c_005fout_005f1.setDefault("${initParam.defaultSkin}");
/* 721 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 722 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 723 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 724 */       return true;
/*     */     }
/* 726 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 727 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HostResourceCommonLeft_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */