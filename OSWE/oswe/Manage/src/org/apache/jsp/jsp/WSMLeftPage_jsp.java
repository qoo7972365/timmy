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
/*     */ public final class WSMLeftPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  94 */       out.write("<!--$Id$-->\n\n");
/*  95 */       org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/showresource.do?method=showResourceTypes&includeleftmenu=true", out, false);
/*  96 */       out.write(10);
/*  97 */       out.write("\n<!--$Id$-->\n\n\n\n");
/*  98 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/* 100 */       out.write(10);
/* 101 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*     */         return;
/* 103 */       out.write(10);
/* 104 */       out.write(10);
/* 105 */       out.write(10);
/* 106 */       out.write("\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n");
/*     */       
/*     */ 
/* 109 */       String requestpath = "/images/mm_menu2.jsp";
/*     */       
/* 111 */       ArrayList menupos = new ArrayList(5);
/* 112 */       if (request.isUserInRole("OPERATOR"))
/*     */       {
/* 114 */         menupos.add("160");
/* 115 */         menupos.add("202");
/* 116 */         menupos.add("224");
/* 117 */         menupos.add("245");
/* 118 */         menupos.add("139");
/* 119 */         menupos.add("181");
/* 120 */         menupos.add("287");
/* 121 */         menupos.add("266");
/* 122 */         menupos.add("308");
/* 123 */         menupos.add("328");
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 128 */       else if (com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 129 */         menupos.add("160");
/* 130 */         menupos.add("205");
/* 131 */         menupos.add("220");
/* 132 */         menupos.add("240");
/* 133 */         menupos.add("140");
/* 134 */         menupos.add("180");
/* 135 */         menupos.add("265");
/* 136 */         menupos.add("285");
/* 137 */         menupos.add("305");
/* 138 */         menupos.add("325");
/*     */       } else {
/* 140 */         menupos.add("218");
/* 141 */         menupos.add("261");
/* 142 */         menupos.add("282");
/* 143 */         menupos.add("303");
/* 144 */         menupos.add("197");
/* 145 */         menupos.add("239");
/* 146 */         menupos.add("324");
/* 147 */         menupos.add("345");
/* 148 */         menupos.add("366");
/* 149 */         menupos.add("400");
/*     */       }
/*     */       
/* 152 */       pageContext.setAttribute("menupos", menupos);
/*     */       
/* 154 */       out.write(10);
/* 155 */       org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, requestpath, out, false);
/* 156 */       out.write("\n</script>\n<SCRIPT language=JavaScript1.2>mmLoadMenus()</SCRIPT>\n\n");
/* 157 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/*     */       
/* 159 */       String categorytype = com.adventnet.appmanager.util.Constants.getCategorytype();
/* 160 */       pageContext.setAttribute("categorytype", categorytype);
/*     */       
/* 162 */       out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n      <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 163 */       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 164 */       out.write("</td>\n  </tr>\n\n\n");
/*     */       
/* 166 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 167 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 168 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */       
/* 170 */       _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 171 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 172 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */         for (;;) {
/* 174 */           out.write(10);
/* 175 */           out.write(10);
/*     */           
/* 177 */           IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 178 */           _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 179 */           _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */           
/* 181 */           _jspx_th_c_005fif_005f3.setTest("${categorytype!='DATABASE' && categorytype!='LAMP'}");
/* 182 */           int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 183 */           if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */             for (;;) {
/* 185 */               out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" >");
/* 186 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 187 */               out.write("</a>\n\n</td>\n</tr>\n");
/* 188 */               int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 189 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 193 */           if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 194 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*     */           }
/*     */           
/* 197 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 198 */           out.write(10);
/*     */           
/* 200 */           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 201 */           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 202 */           _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */           
/* 204 */           _jspx_th_c_005fif_005f4.setTest("${categorytype!='DATABASE' && categorytype!='LAMP' && categorytype!='CLOUD'}");
/* 205 */           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 206 */           if (_jspx_eval_c_005fif_005f4 != 0) {
/*     */             for (;;) {
/* 208 */               out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" >");
/* 209 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 210 */               out.write("</a>\n\n</td>\n</tr>\n");
/* 211 */               int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 212 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 216 */           if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 217 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*     */           }
/*     */           
/* 220 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 221 */           out.write(10);
/*     */           
/* 223 */           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 224 */           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 225 */           _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */           
/* 227 */           _jspx_th_c_005fif_005f5.setTest("${categorytype!='J2EE'}");
/* 228 */           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 229 */           if (_jspx_eval_c_005fif_005f5 != 0) {
/*     */             for (;;) {
/* 231 */               out.write("\n<tr  >\n<td  align=\"left\" class=\"leftlinkstd\"><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\">");
/* 232 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 233 */               out.write("</a>\n\n</td>\n</tr>\n");
/* 234 */               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 235 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 239 */           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 240 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*     */           }
/*     */           
/* 243 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 244 */           out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\">");
/* 245 */           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 246 */           out.write("</a>\n\n</td>\n</tr>\n");
/*     */           
/* 248 */           IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 249 */           _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 250 */           _jspx_th_c_005fif_005f6.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */           
/* 252 */           _jspx_th_c_005fif_005f6.setTest("${categorytype!='DATABASE'}");
/* 253 */           int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 254 */           if (_jspx_eval_c_005fif_005f6 != 0) {
/*     */             for (;;) {
/* 256 */               out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" >");
/* 257 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 258 */               out.write("</a>\n\n</td>\n</tr>\n");
/* 259 */               int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 260 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 264 */           if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 265 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*     */           }
/*     */           
/* 268 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 269 */           out.write("\n<tr >\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\">");
/* 270 */           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 271 */           out.write("    </a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" >");
/* 272 */           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 273 */           out.write("</a>\n\n</td>\n</tr>\n\n");
/* 274 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 275 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 279 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 280 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*     */       }
/*     */       else {
/* 283 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 284 */         out.write(10);
/* 285 */         out.write(10);
/*     */         
/* 287 */         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 288 */         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 289 */         _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */         
/* 291 */         _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 292 */         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 293 */         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */           for (;;) {
/* 295 */             out.write(10);
/* 296 */             out.write(10);
/*     */             
/* 298 */             IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 299 */             _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 300 */             _jspx_th_c_005fif_005f7.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 302 */             _jspx_th_c_005fif_005f7.setTest("${categorytype!='DATABASE' && categorytype!='LAMP'}");
/* 303 */             int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 304 */             if (_jspx_eval_c_005fif_005f7 != 0) {
/*     */               for (;;) {
/* 306 */                 out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" >");
/* 307 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 308 */                 out.write("</a>\n\n</td>\n</tr>\n");
/* 309 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 310 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 314 */             if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 315 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*     */             }
/*     */             
/* 318 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 319 */             out.write(10);
/*     */             
/* 321 */             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 322 */             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 323 */             _jspx_th_c_005fif_005f8.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 325 */             _jspx_th_c_005fif_005f8.setTest("${categorytype!='DATABASE' && categorytype!='LAMP' && categorytype!='CLOUD'}");
/* 326 */             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 327 */             if (_jspx_eval_c_005fif_005f8 != 0) {
/*     */               for (;;) {
/* 329 */                 out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" >");
/* 330 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 331 */                 out.write("</a>\n\n</td>\n</tr>\n");
/* 332 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 333 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 337 */             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 338 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*     */             }
/*     */             
/* 341 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 342 */             out.write("\n\n\n<tr  >\n<td  align=\"left\" class=\"leftlinkstd\"><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\">");
/* 343 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 344 */             out.write("</a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\">");
/* 345 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 346 */             out.write("</a>\n\n</td>\n</tr>\n\n");
/*     */             
/* 348 */             IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 349 */             _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 350 */             _jspx_th_c_005fif_005f9.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 352 */             _jspx_th_c_005fif_005f9.setTest("${categorytype!='DATABASE'}");
/* 353 */             int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 354 */             if (_jspx_eval_c_005fif_005f9 != 0) {
/*     */               for (;;) {
/* 356 */                 out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" >");
/* 357 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 358 */                 out.write("</a>\n\n</td>\n</tr>\n");
/* 359 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 360 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 364 */             if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 365 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*     */             }
/*     */             
/* 368 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 369 */             out.write("\n<tr >\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\">");
/* 370 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 371 */             out.write(" </a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" >");
/* 372 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 373 */             out.write("</a>\n\n</td>\n</tr>\n");
/* 374 */             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 375 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 379 */         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 380 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*     */         }
/*     */         else {
/* 383 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 384 */           out.write("\n\n  <tr >\n  <td class=\"leftlinkstd\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=CAM&network=Custom-Application\"   class=\"new-left-links\">");
/* 385 */           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 386 */           out.write("</a></td>\n\n</tr>\n");
/*     */           
/* 388 */           IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 389 */           _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 390 */           _jspx_th_c_005fif_005f10.setParent(null);
/*     */           
/* 392 */           _jspx_th_c_005fif_005f10.setTest("${categorytype!='CLOUD'}");
/* 393 */           int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 394 */           if (_jspx_eval_c_005fif_005f10 != 0) {
/*     */             for (;;) {
/* 396 */               out.write("\n<tr >\n  <td class=\"leftlinkstd\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MOM\"   class=\"new-left-links\">");
/* 397 */               out.print(FormatUtil.getString("Middleware/Portal"));
/* 398 */               out.write("</a></td>\n \n</tr>\n");
/* 399 */               int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 400 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 404 */           if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 405 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/*     */           }
/*     */           else {
/* 408 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 409 */             out.write(10);
/*     */             
/* 411 */             String[] categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 412 */             for (int i = 0; i < categoryLink.length; i++)
/*     */             {
/* 414 */               if ((!categoryLink[i].equals("APP")) && (!categoryLink[i].equals("TM")) && (!categoryLink[i].equals("ERP")) && (!categoryLink[i].equals("DBS")) && (!categoryLink[i].equals("SYS")) && (!categoryLink[i].equals("MS")) && (!categoryLink[i].equals("SCR")) && (!categoryLink[i].equals("NWD")) && (!categoryLink[i].equals("SER")) && (!categoryLink[i].equals("URL")) && (!categoryLink[i].equals("CAM")) && (!categoryLink[i].equals("MOM")) && (!categoryLink[i].equals("SAN")) && (!categoryLink[i].equals("EMO")))
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 423 */                 out.write("\n<tr>\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href='");
/* 424 */                 out.print("/showresource.do?method=showResourceTypes&detailspage=true&group=" + categoryLink[i]);
/* 425 */                 out.write(39);
/* 426 */                 out.write(62);
/* 427 */                 out.print(FormatUtil.getString(com.adventnet.appmanager.util.Constants.categoryTitle[i]));
/* 428 */                 out.write("</a>\n</td>\n</tr>\n");
/*     */               }
/*     */             }
/*     */             
/* 432 */             out.write("\n\n</table>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 433 */             out.write(10);
/* 434 */             response.setContentType("text/html;charset=UTF-8");
/* 435 */             out.write(10);
/* 436 */             out.write(10);
/* 437 */             out.write("\n<br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  class=\"leftmnutables\">\n<tr>\n<td width=\"80%\" class=\"leftlinksquicknote\">");
/* 438 */             out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 439 */             out.write("</td>\n<td width=\"20%\" class=\"leftlinksheading\"><img src=\"/images/");
/* 440 */             if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */               return;
/* 442 */             out.write("/img_quicknote.gif\"      hspace=\"5\"></td>\n  </tr>\n  <tr> \n\t  \n    <td colspan=\"2\" class=\"quicknote\"> \n    ");
/* 443 */             out.print(FormatUtil.getString("am.webclient.wsm.quicknote.text"));
/* 444 */             out.write("\n    </td>\n  </tr>\n</table>\n\n");
/*     */           }
/* 446 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 447 */         out = _jspx_out;
/* 448 */         if ((out != null) && (out.getBufferSize() != 0))
/* 449 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 450 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 453 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 459 */     PageContext pageContext = _jspx_page_context;
/* 460 */     JspWriter out = _jspx_page_context.getOut();
/* 461 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 462 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*     */     
/* 464 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 465 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 466 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 468 */     _jspx_th_c_005fif_005f0.setTest("${empty appservers}");
/* 469 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 470 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 472 */         out.write(10);
/* 473 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/showresource.do?method=showResourceTypes&includeleftmenu=true", out, false);
/* 474 */         out.write(10);
/* 475 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 476 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 480 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 481 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 482 */       return true;
/*     */     }
/* 484 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 485 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 490 */     PageContext pageContext = _jspx_page_context;
/* 491 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 493 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 494 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 495 */     _jspx_th_c_005fif_005f1.setParent(null);
/*     */     
/* 497 */     _jspx_th_c_005fif_005f1.setTest("${!empty param.haid}");
/* 498 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 499 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */       for (;;) {
/* 501 */         out.write(10);
/* 502 */         if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 503 */           return true;
/* 504 */         out.write("\n      ");
/* 505 */         if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 506 */           return true;
/* 507 */         out.write(10);
/* 508 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 509 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 513 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 514 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 515 */       return true;
/*     */     }
/* 517 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 518 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 523 */     PageContext pageContext = _jspx_page_context;
/* 524 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 526 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 527 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 528 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*     */     
/* 530 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 531 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*     */     try {
/* 533 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 534 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*     */         for (;;) {
/* 536 */           out.write("\n      <fmt:parseNumber var=\"wnhaid\" value=\"${param.haid}\"/>\n      ");
/* 537 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 538 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 542 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 543 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 546 */         int tmp143_142 = 0; int[] tmp143_140 = _jspx_push_body_count_c_005fcatch_005f0; int tmp145_144 = tmp143_140[tmp143_142];tmp143_140[tmp143_142] = (tmp145_144 - 1); if (tmp145_144 <= 0) break;
/* 547 */         out = _jspx_page_context.popBody(); }
/* 548 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*     */     } finally {
/* 550 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 551 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*     */     }
/* 553 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 558 */     PageContext pageContext = _jspx_page_context;
/* 559 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 561 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 562 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 563 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*     */     
/* 565 */     _jspx_th_c_005fif_005f2.setTest("${(empty invalidhaid)}");
/* 566 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 567 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 569 */         out.write(10);
/* 570 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 571 */           return true;
/* 572 */         out.write(10);
/* 573 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 574 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 578 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 579 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 580 */       return true;
/*     */     }
/* 582 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 583 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 588 */     PageContext pageContext = _jspx_page_context;
/* 589 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 591 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 592 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 593 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 595 */     _jspx_th_c_005fset_005f0.setVar("haid");
/* 596 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 597 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 598 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 599 */         out = _jspx_page_context.pushBody();
/* 600 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 601 */         _jspx_th_c_005fset_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 604 */         out.write(10);
/* 605 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 606 */           return true;
/* 607 */         out.write(10);
/* 608 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 609 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 612 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 613 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 616 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 617 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 618 */       return true;
/*     */     }
/* 620 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 621 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 626 */     PageContext pageContext = _jspx_page_context;
/* 627 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 629 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 630 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 631 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*     */     
/* 633 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 634 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 635 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 636 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 637 */       return true;
/*     */     }
/* 639 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 640 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 645 */     PageContext pageContext = _jspx_page_context;
/* 646 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 648 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 649 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 650 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 652 */     _jspx_th_c_005fout_005f1.setValue("${selectedskin}");
/*     */     
/* 654 */     _jspx_th_c_005fout_005f1.setDefault("${initParam.defaultSkin}");
/* 655 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 656 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 657 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 658 */       return true;
/*     */     }
/* 660 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 661 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\WSMLeftPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */