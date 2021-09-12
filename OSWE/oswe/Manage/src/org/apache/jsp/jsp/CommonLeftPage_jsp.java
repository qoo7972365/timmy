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
/*     */ public final class CommonLeftPage_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  44 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  55 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  59 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  60 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/*  61 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  62 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  63 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  64 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  71 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  74 */     JspWriter out = null;
/*  75 */     Object page = this;
/*  76 */     JspWriter _jspx_out = null;
/*  77 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  81 */       response.setContentType("text/html");
/*  82 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  84 */       _jspx_page_context = pageContext;
/*  85 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  86 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  87 */       session = pageContext.getSession();
/*  88 */       out = pageContext.getOut();
/*  89 */       _jspx_out = out;
/*     */       
/*  91 */       out.write(10);
/*  92 */       org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/showresource.do?method=showResourceTypes&includeleftmenu=true", out, false);
/*  93 */       out.write(10);
/*  94 */       out.write("\n<!--$Id$-->\n\n\n\n");
/*  95 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/*  97 */       out.write(10);
/*  98 */       if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*     */         return;
/* 100 */       out.write(10);
/* 101 */       out.write(10);
/* 102 */       out.write(10);
/* 103 */       out.write("\n<SCRIPT language=JavaScript1.2 src=\"/template/mm_menu.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\n");
/*     */       
/*     */ 
/* 106 */       String requestpath = "/images/mm_menu2.jsp";
/*     */       
/* 108 */       ArrayList menupos = new ArrayList(5);
/* 109 */       if (request.isUserInRole("OPERATOR"))
/*     */       {
/* 111 */         menupos.add("160");
/* 112 */         menupos.add("202");
/* 113 */         menupos.add("224");
/* 114 */         menupos.add("245");
/* 115 */         menupos.add("139");
/* 116 */         menupos.add("181");
/* 117 */         menupos.add("287");
/* 118 */         menupos.add("266");
/* 119 */         menupos.add("308");
/* 120 */         menupos.add("328");
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 125 */       else if (com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 126 */         menupos.add("160");
/* 127 */         menupos.add("205");
/* 128 */         menupos.add("220");
/* 129 */         menupos.add("240");
/* 130 */         menupos.add("140");
/* 131 */         menupos.add("180");
/* 132 */         menupos.add("265");
/* 133 */         menupos.add("285");
/* 134 */         menupos.add("305");
/* 135 */         menupos.add("325");
/*     */       } else {
/* 137 */         menupos.add("218");
/* 138 */         menupos.add("261");
/* 139 */         menupos.add("282");
/* 140 */         menupos.add("303");
/* 141 */         menupos.add("197");
/* 142 */         menupos.add("239");
/* 143 */         menupos.add("324");
/* 144 */         menupos.add("345");
/* 145 */         menupos.add("366");
/* 146 */         menupos.add("400");
/*     */       }
/*     */       
/* 149 */       pageContext.setAttribute("menupos", menupos);
/*     */       
/* 151 */       out.write(10);
/* 152 */       org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, requestpath, out, false);
/* 153 */       out.write("\n</script>\n<SCRIPT language=JavaScript1.2>mmLoadMenus()</SCRIPT>\n\n");
/* 154 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/*     */       
/* 156 */       String categorytype = com.adventnet.appmanager.util.Constants.getCategorytype();
/* 157 */       pageContext.setAttribute("categorytype", categorytype);
/*     */       
/* 159 */       out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n      <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 160 */       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.type"));
/* 161 */       out.write("</td>\n  </tr>\n\n\n");
/*     */       
/* 163 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 164 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 165 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */       
/* 167 */       _jspx_th_logic_005fnotPresent_005f0.setRole("OPERATOR");
/* 168 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 169 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */         for (;;) {
/* 171 */           out.write(10);
/* 172 */           out.write(10);
/*     */           
/* 174 */           IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 175 */           _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 176 */           _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */           
/* 178 */           _jspx_th_c_005fif_005f3.setTest("${categorytype!='DATABASE' && categorytype!='LAMP'}");
/* 179 */           int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 180 */           if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */             for (;;) {
/* 182 */               out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" >");
/* 183 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 184 */               out.write("</a>\n\n</td>\n</tr>\n");
/* 185 */               int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 186 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 190 */           if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 191 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*     */           }
/*     */           
/* 194 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 195 */           out.write(10);
/*     */           
/* 197 */           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 198 */           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 199 */           _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */           
/* 201 */           _jspx_th_c_005fif_005f4.setTest("${categorytype!='DATABASE' && categorytype!='LAMP' && categorytype!='CLOUD'}");
/* 202 */           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 203 */           if (_jspx_eval_c_005fif_005f4 != 0) {
/*     */             for (;;) {
/* 205 */               out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" >");
/* 206 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 207 */               out.write("</a>\n\n</td>\n</tr>\n");
/* 208 */               int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 209 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 213 */           if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 214 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*     */           }
/*     */           
/* 217 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 218 */           out.write(10);
/*     */           
/* 220 */           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 221 */           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 222 */           _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */           
/* 224 */           _jspx_th_c_005fif_005f5.setTest("${categorytype!='J2EE'}");
/* 225 */           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 226 */           if (_jspx_eval_c_005fif_005f5 != 0) {
/*     */             for (;;) {
/* 228 */               out.write("\n<tr  >\n<td  align=\"left\" class=\"leftlinkstd\"><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\">");
/* 229 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 230 */               out.write("</a>\n\n</td>\n</tr>\n");
/* 231 */               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 232 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 236 */           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 237 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*     */           }
/*     */           
/* 240 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 241 */           out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\">");
/* 242 */           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 243 */           out.write("</a>\n\n</td>\n</tr>\n");
/*     */           
/* 245 */           IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 246 */           _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 247 */           _jspx_th_c_005fif_005f6.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*     */           
/* 249 */           _jspx_th_c_005fif_005f6.setTest("${categorytype!='DATABASE'}");
/* 250 */           int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 251 */           if (_jspx_eval_c_005fif_005f6 != 0) {
/*     */             for (;;) {
/* 253 */               out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" >");
/* 254 */               out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 255 */               out.write("</a>\n\n</td>\n</tr>\n");
/* 256 */               int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 257 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 261 */           if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 262 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*     */           }
/*     */           
/* 265 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 266 */           out.write("\n<tr >\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\">");
/* 267 */           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 268 */           out.write("    </a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" >");
/* 269 */           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 270 */           out.write("</a>\n\n</td>\n</tr>\n\n");
/* 271 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 272 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 276 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 277 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*     */       }
/*     */       else {
/* 280 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 281 */         out.write(10);
/* 282 */         out.write(10);
/*     */         
/* 284 */         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 285 */         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 286 */         _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */         
/* 288 */         _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 289 */         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 290 */         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */           for (;;) {
/* 292 */             out.write(10);
/* 293 */             out.write(10);
/*     */             
/* 295 */             IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 296 */             _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 297 */             _jspx_th_c_005fif_005f7.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 299 */             _jspx_th_c_005fif_005f7.setTest("${categorytype!='DATABASE' && categorytype!='LAMP'}");
/* 300 */             int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 301 */             if (_jspx_eval_c_005fif_005f7 != 0) {
/*     */               for (;;) {
/* 303 */                 out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=APP\" >");
/* 304 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.appserver"));
/* 305 */                 out.write("</a>\n\n</td>\n</tr>\n");
/* 306 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 307 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 311 */             if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 312 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*     */             }
/*     */             
/* 315 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 316 */             out.write(10);
/*     */             
/* 318 */             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 319 */             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 320 */             _jspx_th_c_005fif_005f8.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 322 */             _jspx_th_c_005fif_005f8.setTest("${categorytype!='DATABASE' && categorytype!='LAMP' && categorytype!='CLOUD'}");
/* 323 */             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 324 */             if (_jspx_eval_c_005fif_005f8 != 0) {
/*     */               for (;;) {
/* 326 */                 out.write("\n<tr >\n<td  width=\"85%\" align=\"left\" class=\"leftlinkstd\" > <A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=ERP\" >");
/* 327 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.erp"));
/* 328 */                 out.write("</a>\n\n</td>\n</tr>\n");
/* 329 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 330 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 334 */             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 335 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*     */             }
/*     */             
/* 338 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 339 */             out.write("\n\n\n<tr  >\n<td  align=\"left\" class=\"leftlinkstd\"><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=DBS\">");
/* 340 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 341 */             out.write("</a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SYS\">");
/* 342 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 343 */             out.write("</a>\n\n</td>\n</tr>\n\n");
/*     */             
/* 345 */             IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 346 */             _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 347 */             _jspx_th_c_005fif_005f9.setParent(_jspx_th_logic_005fpresent_005f0);
/*     */             
/* 349 */             _jspx_th_c_005fif_005f9.setTest("${categorytype!='DATABASE'}");
/* 350 */             int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 351 */             if (_jspx_eval_c_005fif_005f9 != 0) {
/*     */               for (;;) {
/* 353 */                 out.write("\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MS\" >");
/* 354 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 355 */                 out.write("</a>\n\n</td>\n</tr>\n");
/* 356 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 357 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 361 */             if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 362 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*     */             }
/*     */             
/* 365 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 366 */             out.write("\n<tr >\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=URL\">");
/* 367 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 368 */             out.write(" </a>\n\n</td>\n</tr>\n<tr >\n<td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=SER\" >");
/* 369 */             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 370 */             out.write("</a>\n\n</td>\n</tr>\n");
/* 371 */             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 372 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 376 */         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 377 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*     */         }
/*     */         else {
/* 380 */           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 381 */           out.write("\n\n  <tr >\n  <td class=\"leftlinkstd\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=CAM&network=Custom-Application\"   class=\"new-left-links\">");
/* 382 */           out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 383 */           out.write("</a></td>\n\n</tr>\n");
/*     */           
/* 385 */           IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 386 */           _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 387 */           _jspx_th_c_005fif_005f10.setParent(null);
/*     */           
/* 389 */           _jspx_th_c_005fif_005f10.setTest("${categorytype!='CLOUD'}");
/* 390 */           int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 391 */           if (_jspx_eval_c_005fif_005f10 != 0) {
/*     */             for (;;) {
/* 393 */               out.write("\n<tr >\n  <td class=\"leftlinkstd\"><a href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=MOM\"   class=\"new-left-links\">");
/* 394 */               out.print(FormatUtil.getString("Middleware/Portal"));
/* 395 */               out.write("</a></td>\n \n</tr>\n");
/* 396 */               int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 397 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 401 */           if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 402 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/*     */           }
/*     */           else {
/* 405 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 406 */             out.write(10);
/*     */             
/* 408 */             String[] categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 409 */             for (int i = 0; i < categoryLink.length; i++)
/*     */             {
/* 411 */               if ((!categoryLink[i].equals("APP")) && (!categoryLink[i].equals("TM")) && (!categoryLink[i].equals("ERP")) && (!categoryLink[i].equals("DBS")) && (!categoryLink[i].equals("SYS")) && (!categoryLink[i].equals("MS")) && (!categoryLink[i].equals("SCR")) && (!categoryLink[i].equals("NWD")) && (!categoryLink[i].equals("SER")) && (!categoryLink[i].equals("URL")) && (!categoryLink[i].equals("CAM")) && (!categoryLink[i].equals("MOM")) && (!categoryLink[i].equals("SAN")) && (!categoryLink[i].equals("EMO")))
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 420 */                 out.write("\n<tr>\n    <td  align=\"left\" class=\"leftlinkstd\" ><A class=\"new-left-links\" href='");
/* 421 */                 out.print("/showresource.do?method=showResourceTypes&detailspage=true&group=" + categoryLink[i]);
/* 422 */                 out.write(39);
/* 423 */                 out.write(62);
/* 424 */                 out.print(FormatUtil.getString(com.adventnet.appmanager.util.Constants.categoryTitle[i]));
/* 425 */                 out.write("</a>\n</td>\n</tr>\n");
/*     */               }
/*     */             }
/*     */             
/* 429 */             out.write("\n\n</table>\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 430 */             out.write(10);
/* 431 */             response.setContentType("text/html;charset=UTF-8");
/* 432 */             out.write(10);
/* 433 */             out.write(10);
/*     */           }
/* 435 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 436 */         out = _jspx_out;
/* 437 */         if ((out != null) && (out.getBufferSize() != 0))
/* 438 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 439 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 442 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 448 */     PageContext pageContext = _jspx_page_context;
/* 449 */     JspWriter out = _jspx_page_context.getOut();
/* 450 */     HttpServletRequest request = (HttpServletRequest)_jspx_page_context.getRequest();
/* 451 */     HttpServletResponse response = (HttpServletResponse)_jspx_page_context.getResponse();
/*     */     
/* 453 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 454 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 455 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 457 */     _jspx_th_c_005fif_005f0.setTest("${empty appservers}");
/* 458 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 459 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 461 */         out.write(10);
/* 462 */         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/showresource.do?method=showResourceTypes&includeleftmenu=true", out, false);
/* 463 */         out.write(10);
/* 464 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 465 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 469 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 470 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 471 */       return true;
/*     */     }
/* 473 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 474 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 479 */     PageContext pageContext = _jspx_page_context;
/* 480 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 482 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 483 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 484 */     _jspx_th_c_005fif_005f1.setParent(null);
/*     */     
/* 486 */     _jspx_th_c_005fif_005f1.setTest("${!empty param.haid}");
/* 487 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 488 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */       for (;;) {
/* 490 */         out.write(10);
/* 491 */         if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 492 */           return true;
/* 493 */         out.write("\n      ");
/* 494 */         if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 495 */           return true;
/* 496 */         out.write(10);
/* 497 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 498 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 502 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 503 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 504 */       return true;
/*     */     }
/* 506 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 507 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 512 */     PageContext pageContext = _jspx_page_context;
/* 513 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 515 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 516 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 517 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*     */     
/* 519 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 520 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*     */     try {
/* 522 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 523 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*     */         for (;;) {
/* 525 */           out.write("\n      <fmt:parseNumber var=\"wnhaid\" value=\"${param.haid}\"/>\n      ");
/* 526 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 527 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 531 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 532 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 535 */         int tmp143_142 = 0; int[] tmp143_140 = _jspx_push_body_count_c_005fcatch_005f0; int tmp145_144 = tmp143_140[tmp143_142];tmp143_140[tmp143_142] = (tmp145_144 - 1); if (tmp145_144 <= 0) break;
/* 536 */         out = _jspx_page_context.popBody(); }
/* 537 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*     */     } finally {
/* 539 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 540 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*     */     }
/* 542 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 547 */     PageContext pageContext = _jspx_page_context;
/* 548 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 550 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 551 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 552 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*     */     
/* 554 */     _jspx_th_c_005fif_005f2.setTest("${(empty invalidhaid)}");
/* 555 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 556 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 558 */         out.write(10);
/* 559 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 560 */           return true;
/* 561 */         out.write(10);
/* 562 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 563 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 567 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 568 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 569 */       return true;
/*     */     }
/* 571 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 572 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 577 */     PageContext pageContext = _jspx_page_context;
/* 578 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 580 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 581 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 582 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 584 */     _jspx_th_c_005fset_005f0.setVar("haid");
/* 585 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 586 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 587 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 588 */         out = _jspx_page_context.pushBody();
/* 589 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 590 */         _jspx_th_c_005fset_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 593 */         out.write(10);
/* 594 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 595 */           return true;
/* 596 */         out.write(10);
/* 597 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 598 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 601 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 602 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 605 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 606 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 607 */       return true;
/*     */     }
/* 609 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 610 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 615 */     PageContext pageContext = _jspx_page_context;
/* 616 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 618 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 619 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 620 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*     */     
/* 622 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 623 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 624 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 625 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 626 */       return true;
/*     */     }
/* 628 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 629 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\CommonLeftPage_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */