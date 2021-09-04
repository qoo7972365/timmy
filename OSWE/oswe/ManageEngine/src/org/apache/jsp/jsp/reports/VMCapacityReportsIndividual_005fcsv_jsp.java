/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.bean.DefineTag;
/*     */ import org.apache.struts.taglib.logic.EqualTag;
/*     */ import org.apache.struts.taglib.logic.IterateTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*     */ 
/*     */ public final class VMCapacityReportsIndividual_005fcsv_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  27 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fproperty_005fname_005fid_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  48 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  52 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  57 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  58 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  59 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  60 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  61 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  62 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  63 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  64 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  68 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.release();
/*  69 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
/*  70 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  71 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  72 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  73 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  74 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.release();
/*  75 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005fproperty_005fname_005fid_005fnobody.release();
/*  76 */     this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.release();
/*  77 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.release();
/*  78 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  85 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  88 */     JspWriter out = null;
/*  89 */     Object page = this;
/*  90 */     JspWriter _jspx_out = null;
/*  91 */     PageContext _jspx_page_context = null;
/*     */     
/*  93 */     Object _jspx_att_1 = null;
/*  94 */     Integer _jspx_m_1 = null;
/*     */     try
/*     */     {
/*  97 */       response.setContentType("text/html");
/*  98 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/* 100 */       _jspx_page_context = pageContext;
/* 101 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 102 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 103 */       session = pageContext.getSession();
/* 104 */       out = pageContext.getOut();
/* 105 */       _jspx_out = out;
/*     */       
/* 107 */       out.write("<!--$Id$--> \n\n\n\n\n\n\n\n\n\n\n\n\n");
/*     */       
/* 109 */       response.setContentType("text/html;charset=" + com.adventnet.appmanager.util.Constants.getCharSet());
/* 110 */       response.setHeader("Content-Disposition", "attachment;filename=VMCapacityReports_" + new java.sql.Date(System.currentTimeMillis()) + ".csv");
/* 111 */       String title = FormatUtil.getString("am.webclient.managermail.schedulemail.reportgenerated.text");
/*     */       
/*     */ 
/* 114 */       out.write(10);
/* 115 */       out.write(10);
/*     */       
/* 117 */       OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.get(OutTag.class);
/* 118 */       _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 119 */       _jspx_th_c_005fout_005f0.setParent(null);
/*     */       
/* 121 */       _jspx_th_c_005fout_005f0.setValue("${heading}");
/*     */       
/* 123 */       _jspx_th_c_005fout_005f0.setEscapeXml("false");
/* 124 */       int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 125 */       if (_jspx_eval_c_005fout_005f0 != 0) {
/* 126 */         if (_jspx_eval_c_005fout_005f0 != 1) {
/* 127 */           out = _jspx_page_context.pushBody();
/* 128 */           _jspx_th_c_005fout_005f0.setBodyContent((BodyContent)out);
/* 129 */           _jspx_th_c_005fout_005f0.doInitBody();
/*     */         }
/*     */         for (;;) {
/* 132 */           out.print(FormatUtil.getString("webclient.performance.reports.commonheader"));
/* 133 */           int evalDoAfterBody = _jspx_th_c_005fout_005f0.doAfterBody();
/* 134 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 137 */         if (_jspx_eval_c_005fout_005f0 != 1) {
/* 138 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 141 */       if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 142 */         this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.reuse(_jspx_th_c_005fout_005f0);
/*     */       }
/*     */       else {
/* 145 */         this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml.reuse(_jspx_th_c_005fout_005f0);
/* 146 */         out.write(10);
/* 147 */         request.setAttribute("currTime", new java.util.Date(System.currentTimeMillis()));
/* 148 */         out.write(10);
/*     */         
/* 150 */         out.write(10);
/* 151 */         out.write(35);
/* 152 */         out.write(34);
/* 153 */         out.print(title);
/* 154 */         out.write("  : ");
/* 155 */         if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_page_context))
/*     */           return;
/* 157 */         out.write(34);
/* 158 */         out.write(10);
/*     */         
/* 160 */         String category = (String)request.getAttribute("categoryTitle");
/* 161 */         ArrayList calculatedTime = (ArrayList)request.getAttribute("calculatedTime");
/* 162 */         String resultTime = FormatUtil.getString("am.vmreports.capacityplanning.summary", new String[] { (String)calculatedTime.get(4), category, (String)calculatedTime.get(2), (String)calculatedTime.get(3) });
/*     */         
/*     */ 
/* 165 */         out.write(10);
/* 166 */         out.write(35);
/* 167 */         out.print(FormatUtil.getString("am.reporttab.vmreports.monitorname"));
/* 168 */         out.write(32);
/* 169 */         out.write(44);
/* 170 */         out.write(32);
/* 171 */         if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */           return;
/* 173 */         out.write(" ,\n#");
/* 174 */         if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*     */           return;
/* 176 */         out.write(32);
/* 177 */         out.write(44);
/* 178 */         out.write(32);
/*     */         
/* 180 */         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 181 */         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 182 */         _jspx_th_c_005fchoose_005f0.setParent(null);
/* 183 */         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 184 */         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */           for (;;) {
/* 186 */             out.write(32);
/*     */             
/* 188 */             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 189 */             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 190 */             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */             
/* 192 */             _jspx_th_c_005fwhen_005f0.setTest("${calculatedTime[0]=='red'}");
/* 193 */             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 194 */             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */               for (;;) {
/* 196 */                 out.print(FormatUtil.getString("am.vmreports.capacityplanning.undersized.yes"));
/* 197 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 198 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 202 */             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 203 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */             }
/*     */             
/* 206 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 207 */             out.write(32);
/*     */             
/* 209 */             OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 210 */             _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 211 */             _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 212 */             int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 213 */             if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */               for (;;) {
/* 215 */                 out.write(32);
/* 216 */                 out.print(FormatUtil.getString("am.vmreports.capacityplanning.undersized.no"));
/* 217 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 218 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 222 */             if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 223 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */             }
/*     */             
/* 226 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 227 */             out.write(32);
/* 228 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 229 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 233 */         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 234 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*     */         }
/*     */         else {
/* 237 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 238 */           out.write(44);
/* 239 */           out.write(10);
/* 240 */           out.write(35);
/* 241 */           out.print(FormatUtil.getString("am.vmreports.capacityplanning.individual.timetype", new String[] { category }));
/* 242 */           out.write(32);
/* 243 */           out.write(44);
/* 244 */           out.print(resultTime);
/* 245 */           out.write(10);
/* 246 */           out.write(10);
/* 247 */           out.write(10);
/* 248 */           out.print(FormatUtil.getString("am.vmreports.capacityplanning.hourlydetails.date"));
/* 249 */           out.write(44);
/* 250 */           out.print(FormatUtil.getString("am.vmreports.capacityplanning.hourlydetails.time"));
/* 251 */           out.write(44);
/* 252 */           out.print(category);
/* 253 */           out.write(44);
/*     */           
/* 255 */           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.get(IterateTag.class);
/* 256 */           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 257 */           _jspx_th_logic_005fiterate_005f0.setParent(null);
/*     */           
/* 259 */           _jspx_th_logic_005fiterate_005f0.setName("AttributeIDList");
/*     */           
/* 261 */           _jspx_th_logic_005fiterate_005f0.setId("row");
/*     */           
/* 263 */           _jspx_th_logic_005fiterate_005f0.setScope("request");
/*     */           
/* 265 */           _jspx_th_logic_005fiterate_005f0.setIndexId("m");
/* 266 */           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 267 */           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 268 */             Object row = null;
/* 269 */             Integer m = null;
/* 270 */             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 271 */               out = _jspx_page_context.pushBody();
/* 272 */               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 273 */               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */             }
/* 275 */             row = _jspx_page_context.findAttribute("row");
/* 276 */             m = (Integer)_jspx_page_context.findAttribute("m");
/*     */             for (;;) {
/* 278 */               if (_jspx_meth_c_005fout_005f3(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*     */                 return;
/* 280 */               out.write(44);
/* 281 */               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 282 */               row = _jspx_page_context.findAttribute("row");
/* 283 */               m = (Integer)_jspx_page_context.findAttribute("m");
/* 284 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 287 */             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 288 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 291 */           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 292 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/*     */           }
/*     */           else {
/* 295 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 296 */             out.write(10);
/* 297 */             out.write(10);
/*     */             
/* 299 */             IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.get(IterateTag.class);
/* 300 */             _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 301 */             _jspx_th_logic_005fiterate_005f1.setParent(null);
/*     */             
/* 303 */             _jspx_th_logic_005fiterate_005f1.setName("outermap");
/*     */             
/* 305 */             _jspx_th_logic_005fiterate_005f1.setId("row");
/*     */             
/* 307 */             _jspx_th_logic_005fiterate_005f1.setScope("request");
/*     */             
/* 309 */             _jspx_th_logic_005fiterate_005f1.setIndexId("m");
/* 310 */             int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 311 */             if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 312 */               Object row = null;
/* 313 */               Integer m = null;
/* 314 */               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 315 */                 out = _jspx_page_context.pushBody();
/* 316 */                 _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 317 */                 _jspx_th_logic_005fiterate_005f1.doInitBody();
/*     */               }
/* 319 */               row = _jspx_page_context.findAttribute("row");
/* 320 */               m = (Integer)_jspx_page_context.findAttribute("m");
/*     */               for (;;)
/*     */               {
/* 323 */                 DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 324 */                 _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 325 */                 _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_logic_005fiterate_005f1);
/*     */                 
/* 327 */                 _jspx_th_bean_005fdefine_005f0.setId("row1");
/*     */                 
/* 329 */                 _jspx_th_bean_005fdefine_005f0.setName("row");
/*     */                 
/* 331 */                 _jspx_th_bean_005fdefine_005f0.setProperty("key");
/* 332 */                 int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 333 */                 if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 334 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*     */                 }
/*     */                 
/* 337 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 338 */                 Object row1 = null;
/* 339 */                 row1 = _jspx_page_context.findAttribute("row1");
/*     */                 
/* 341 */                 DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 342 */                 _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 343 */                 _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*     */                 
/* 345 */                 _jspx_th_bean_005fdefine_005f1.setId("valueprop");
/*     */                 
/* 347 */                 _jspx_th_bean_005fdefine_005f1.setName("row");
/*     */                 
/* 349 */                 _jspx_th_bean_005fdefine_005f1.setProperty("value");
/* 350 */                 int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 351 */                 if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 352 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*     */                 }
/*     */                 
/* 355 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 356 */                 Object valueprop = null;
/* 357 */                 valueprop = _jspx_page_context.findAttribute("valueprop");
/* 358 */                 String colorDisplay = FormatUtil.getString("am.vmreports.capacityplanning.undersized.no");
/*     */                 
/* 360 */                 EqualTag _jspx_th_logic_005fequal_005f0 = (EqualTag)this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.get(EqualTag.class);
/* 361 */                 _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
/* 362 */                 _jspx_th_logic_005fequal_005f0.setParent(_jspx_th_logic_005fiterate_005f1);
/*     */                 
/* 364 */                 _jspx_th_logic_005fequal_005f0.setName("valueprop");
/*     */                 
/* 366 */                 _jspx_th_logic_005fequal_005f0.setProperty("unicolor");
/*     */                 
/* 368 */                 _jspx_th_logic_005fequal_005f0.setValue("red");
/* 369 */                 int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
/* 370 */                 if (_jspx_eval_logic_005fequal_005f0 != 0) {
/*     */                   for (;;) {
/* 372 */                     colorDisplay = FormatUtil.getString("am.vmreports.capacityplanning.undersized.no");
/* 373 */                     int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
/* 374 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 378 */                 if (_jspx_th_logic_005fequal_005f0.doEndTag() == 5) {
/* 379 */                   this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0); return;
/*     */                 }
/*     */                 
/* 382 */                 this._005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fproperty_005fname.reuse(_jspx_th_logic_005fequal_005f0);
/* 383 */                 if (_jspx_meth_fmt_005fformatDate_005f1(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*     */                   return;
/* 385 */                 out.write(44);
/* 386 */                 if (_jspx_meth_fmt_005fformatDate_005f2(_jspx_th_logic_005fiterate_005f1, _jspx_page_context))
/*     */                   return;
/* 388 */                 out.write(44);
/* 389 */                 out.print(colorDisplay);
/* 390 */                 out.write(44);
/*     */                 
/* 392 */                 IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.get(IterateTag.class);
/* 393 */                 _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 394 */                 _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fiterate_005f1);
/*     */                 
/* 396 */                 _jspx_th_logic_005fiterate_005f2.setName("AttributeIDList");
/*     */                 
/* 398 */                 _jspx_th_logic_005fiterate_005f2.setId("att");
/*     */                 
/* 400 */                 _jspx_th_logic_005fiterate_005f2.setScope("request");
/*     */                 
/* 402 */                 _jspx_th_logic_005fiterate_005f2.setIndexId("m");
/* 403 */                 int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 404 */                 if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 405 */                   Object att = null;
/* 406 */                   _jspx_m_1 = m;
/* 407 */                   if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 408 */                     out = _jspx_page_context.pushBody();
/* 409 */                     _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 410 */                     _jspx_th_logic_005fiterate_005f2.doInitBody();
/*     */                   }
/* 412 */                   att = _jspx_page_context.findAttribute("att");
/* 413 */                   m = (Integer)_jspx_page_context.findAttribute("m");
/*     */                   for (;;) {
/* 415 */                     if (_jspx_meth_c_005fset_005f0(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*     */                       return;
/* 417 */                     if (_jspx_meth_c_005fout_005f4(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*     */                       return;
/* 419 */                     out.write(44);
/* 420 */                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 421 */                     att = _jspx_page_context.findAttribute("att");
/* 422 */                     m = (Integer)_jspx_page_context.findAttribute("m");
/* 423 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/* 426 */                   m = _jspx_m_1;
/* 427 */                   if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 428 */                     out = _jspx_page_context.popBody();
/*     */                   }
/*     */                 }
/* 431 */                 if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 432 */                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*     */                 }
/*     */                 
/* 435 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 436 */                 out.write(10);
/* 437 */                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 438 */                 row = _jspx_page_context.findAttribute("row");
/* 439 */                 m = (Integer)_jspx_page_context.findAttribute("m");
/* 440 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/* 443 */               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 444 */                 out = _jspx_page_context.popBody();
/*     */               }
/*     */             }
/* 447 */             if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 448 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/*     */             }
/*     */             else {
/* 451 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 452 */               out.write("\n     \n       \n\n\n\n\n");
/*     */             }
/* 454 */           } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 455 */         out = _jspx_out;
/* 456 */         if ((out != null) && (out.getBufferSize() != 0))
/* 457 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 458 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 461 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 467 */     PageContext pageContext = _jspx_page_context;
/* 468 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 470 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/* 471 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 472 */     _jspx_th_fmt_005fformatDate_005f0.setParent(null);
/*     */     
/* 474 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${currTime}");
/*     */     
/* 476 */     _jspx_th_fmt_005fformatDate_005f0.setType("both");
/* 477 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 478 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 479 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 480 */       return true;
/*     */     }
/* 482 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 483 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 488 */     PageContext pageContext = _jspx_page_context;
/* 489 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 491 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 492 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 493 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 495 */     _jspx_th_c_005fout_005f1.setValue("${resourcetypename}");
/* 496 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 497 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 498 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 499 */       return true;
/*     */     }
/* 501 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 502 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 507 */     PageContext pageContext = _jspx_page_context;
/* 508 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 510 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 511 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 512 */     _jspx_th_c_005fout_005f2.setParent(null);
/*     */     
/* 514 */     _jspx_th_c_005fout_005f2.setValue("${categoryTitle}");
/* 515 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 516 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 517 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 518 */       return true;
/*     */     }
/* 520 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 521 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 526 */     PageContext pageContext = _jspx_page_context;
/* 527 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 529 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 530 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 531 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*     */     
/* 533 */     _jspx_th_c_005fout_005f3.setValue("${attributeNames[row]}");
/* 534 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 535 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 536 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 537 */       return true;
/*     */     }
/* 539 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 540 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f1(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 545 */     PageContext pageContext = _jspx_page_context;
/* 546 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 548 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f1 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 549 */     _jspx_th_fmt_005fformatDate_005f1.setPageContext(_jspx_page_context);
/* 550 */     _jspx_th_fmt_005fformatDate_005f1.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*     */     
/* 552 */     _jspx_th_fmt_005fformatDate_005f1.setValue("${valueprop.date}");
/*     */     
/* 554 */     _jspx_th_fmt_005fformatDate_005f1.setPattern("MMM d yyyy");
/* 555 */     int _jspx_eval_fmt_005fformatDate_005f1 = _jspx_th_fmt_005fformatDate_005f1.doStartTag();
/* 556 */     if (_jspx_th_fmt_005fformatDate_005f1.doEndTag() == 5) {
/* 557 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 558 */       return true;
/*     */     }
/* 560 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 561 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f2(JspTag _jspx_th_logic_005fiterate_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 566 */     PageContext pageContext = _jspx_page_context;
/* 567 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 569 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f2 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 570 */     _jspx_th_fmt_005fformatDate_005f2.setPageContext(_jspx_page_context);
/* 571 */     _jspx_th_fmt_005fformatDate_005f2.setParent((Tag)_jspx_th_logic_005fiterate_005f1);
/*     */     
/* 573 */     _jspx_th_fmt_005fformatDate_005f2.setValue("${valueprop.date}");
/*     */     
/* 575 */     _jspx_th_fmt_005fformatDate_005f2.setPattern("H:mm");
/* 576 */     int _jspx_eval_fmt_005fformatDate_005f2 = _jspx_th_fmt_005fformatDate_005f2.doStartTag();
/* 577 */     if (_jspx_th_fmt_005fformatDate_005f2.doEndTag() == 5) {
/* 578 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 579 */       return true;
/*     */     }
/* 581 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f2);
/* 582 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 587 */     PageContext pageContext = _jspx_page_context;
/* 588 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 590 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 591 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 592 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/*     */     
/* 594 */     _jspx_th_c_005fset_005f0.setVar("csvkey");
/*     */     
/* 596 */     _jspx_th_c_005fset_005f0.setValue("${att}_csv");
/* 597 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 598 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 599 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 600 */       return true;
/*     */     }
/* 602 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 603 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 608 */     PageContext pageContext = _jspx_page_context;
/* 609 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 611 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 612 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 613 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/*     */     
/* 615 */     _jspx_th_c_005fout_005f4.setValue("${valueprop[csvkey]}");
/* 616 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 617 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 618 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 619 */       return true;
/*     */     }
/* 621 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 622 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\VMCapacityReportsIndividual_005fcsv_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */