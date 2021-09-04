/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.FormatNumberTag;
/*     */ 
/*     */ public final class OracleBufferGets_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  42 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  46 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  56 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  60 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  61 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  62 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  63 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  64 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  65 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  66 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*  67 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.release();
/*  68 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  75 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  78 */     JspWriter out = null;
/*  79 */     Object page = this;
/*  80 */     JspWriter _jspx_out = null;
/*  81 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  85 */       response.setContentType("text/html;charset=UTF-8");
/*  86 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  88 */       _jspx_page_context = pageContext;
/*  89 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  90 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  91 */       session = pageContext.getSession();
/*  92 */       out = pageContext.getOut();
/*  93 */       _jspx_out = out;
/*     */       
/*  95 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/*  96 */       com.adventnet.appmanager.oracle.bean.OracleBean databean = null;
/*  97 */       databean = (com.adventnet.appmanager.oracle.bean.OracleBean)_jspx_page_context.getAttribute("databean", 2);
/*  98 */       if (databean == null) {
/*  99 */         databean = new com.adventnet.appmanager.oracle.bean.OracleBean();
/* 100 */         _jspx_page_context.setAttribute("databean", databean, 2);
/*     */       }
/* 102 */       out.write(10);
/*     */       
/* 104 */       String bgcolor = "";
/* 105 */       String resourcename = request.getParameter("resourcename");
/* 106 */       String resourceid = request.getParameter("resourceid");
/* 107 */       String encodeurl = java.net.URLEncoder.encode("/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID&Datatype=6");
/* 108 */       databean.setmaxcollectiontime(resourcename);
/* 109 */       java.util.ArrayList list = new java.util.ArrayList();
/* 110 */       boolean dcEnabled = com.adventnet.appmanager.util.DifferentialPollingUtil.isDCComponentEnabledForId(resourceid, "BUFFERGETS");
/* 111 */       if ((resourcename != null) && (dcEnabled))
/*     */       {
/* 113 */         list = databean.getBufferGets(resourceid);
/*     */       }
/* 115 */       request.setAttribute("list", list);
/*     */       
/* 117 */       out.write("\n<br>\n");
/*     */       
/* 119 */       SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 120 */       _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 121 */       _jspx_th_c_005fset_005f0.setParent(null);
/*     */       
/* 123 */       _jspx_th_c_005fset_005f0.setVar("dcEnabled");
/* 124 */       int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 125 */       if (_jspx_eval_c_005fset_005f0 != 0) {
/* 126 */         if (_jspx_eval_c_005fset_005f0 != 1) {
/* 127 */           out = _jspx_page_context.pushBody();
/* 128 */           _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 129 */           _jspx_th_c_005fset_005f0.doInitBody();
/*     */         }
/*     */         for (;;) {
/* 132 */           out.print(dcEnabled);
/* 133 */           int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 134 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/* 137 */         if (_jspx_eval_c_005fset_005f0 != 1) {
/* 138 */           out = _jspx_page_context.popBody();
/*     */         }
/*     */       }
/* 141 */       if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 142 */         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*     */       }
/*     */       else {
/* 145 */         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 146 */         out.write(" \n<div class=\"apmconf-table-frame\">\n  <div id=\"apmconf-tld-nav conf-mon-txt\"  style=\"height:30px; margin-left:10px;\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\"  width=\"100%\">\n\t\t\t<tr>\n\t\t\t\t<td>\n\t\t\t\t\t\t<div style=\"display:inline;float:left\">\n\t\t\t\t\t\t\t<span class=\"conf-mon-txt\">");
/* 147 */         out.print(FormatUtil.getString("am.webclient.oracle.buffergets.text"));
/* 148 */         out.write("</span>\n\t\t\t\t\t\t</div>\n\t\t\t\t</td>\t\t\t\n\t\t\t\t\t");
/*     */         
/* 150 */         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 151 */         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 152 */         _jspx_th_c_005fif_005f0.setParent(null);
/*     */         
/* 154 */         _jspx_th_c_005fif_005f0.setTest("${dcEnabled}");
/* 155 */         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 156 */         if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */           for (;;) {
/* 158 */             out.write("\t\n\t\t\t\t<td align=\"right\" style=\"padding-right:8px\" >\t\t\n\t\t\t\t\t<img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 159 */             out.print(resourceid);
/* 160 */             out.write("&attributeIDs=2422&attributeToSelect=2422&redirectto=");
/* 161 */             out.print(encodeurl);
/* 162 */             out.write("\" class=\"bodytextbold\" onMouseOver=\"this.className='bodytextboldwhiteun'\" onMouseOut=\"this.className='bodytextbold'\">");
/* 163 */             out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 164 */             out.write("</a>\n\t\t\t\t</td>\t\n\t\t\t\t");
/* 165 */             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 166 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 170 */         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 171 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */         }
/*     */         else {
/* 174 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 175 */           out.write("\n\t\t\t</tr>\n\t\t</table>\t\n\t</div>\n\t<div style=\"overflow:auto;\">\t\t\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table\" width=\"100%\">\n\t\t\t");
/*     */           
/* 177 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 178 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 179 */           _jspx_th_c_005fchoose_005f0.setParent(null);
/* 180 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 181 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */             for (;;) {
/* 183 */               out.write("\n\t\t\t\t");
/*     */               
/* 185 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 186 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 187 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */               
/* 189 */               _jspx_th_c_005fwhen_005f0.setTest("${not  dcEnabled}");
/* 190 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 191 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */                 for (;;) {
/* 193 */                   out.write("\t\n\t\t\t\t\t<tr><td height=\"30px\" colspan=\"4\" class=\"bodytextbold\" align=\"center\">");
/* 194 */                   out.print(FormatUtil.getString("am.webclient.conf.datacollection.disabled.msg"));
/* 195 */                   out.write("\n\t\t\t\t\t");
/*     */                   
/* 197 */                   PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 198 */                   _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 199 */                   _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*     */                   
/* 201 */                   _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 202 */                   int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 203 */                   if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */                     for (;;) {
/* 205 */                       out.write(9);
/* 206 */                       out.print(FormatUtil.getString("am.webclient.conf.datacollection.enable.link", new String[] { "ORACLE-DB-server" }));
/* 207 */                       out.write(9);
/* 208 */                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 209 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 213 */                   if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 214 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*     */                   }
/*     */                   
/* 217 */                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 218 */                   out.write("\n\t\t\t\t\t</td></tr>\n\t\t\t\t");
/* 219 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 220 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 224 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 225 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */               }
/*     */               
/* 228 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 229 */               out.write("\n  \t\t\t\t");
/*     */               
/* 231 */               WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 232 */               _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 233 */               _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f0);
/*     */               
/* 235 */               _jspx_th_c_005fwhen_005f1.setTest("${!empty list}");
/* 236 */               int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 237 */               if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*     */                 for (;;) {
/* 239 */                   out.write("\t\n  \t\t\t\t\t\t  <tr>\n\t\t\t\t\t\t\t    <td height=\"26\" align=\"center\" width=\"10%\" class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 240 */                   out.print(FormatUtil.getString("am.webclient.oracle.buffergets"));
/* 241 */                   out.write("</td>\n\t\t\t\t\t\t\t    <td height=\"26\" align=\"center\" width=\"10%\" class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 242 */                   out.print(FormatUtil.getString("am.webclient.oracle.executions"));
/* 243 */                   out.write("</td>\n\t\t\t\t\t\t\t    <td height=\"26\" align=\"center\" width=\"10%\" class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 244 */                   out.print(FormatUtil.getString("am.webclient.oracle.buffergetsperexec"));
/* 245 */                   out.write("</td>\n\t\t\t\t\t\t\t\t<td height=\"26\" align=\"center\" width=\"10%\" class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 246 */                   out.print(FormatUtil.getString("am.webclient.oracle.sqlid"));
/* 247 */                   out.write("</td>\n\t\t\t\t\t\t\t    <td height=\"26\" align=\"center\" width=\"70%\" class=\"monitorinfoodd-conf apmconf-dullhead\">");
/* 248 */                   out.print(FormatUtil.getString("am.webclient.oracle.query"));
/* 249 */                   out.write("</td>\n\t\t\t\t\t\t </tr>  \n\t\t\t\t\t");
/*     */                   
/* 251 */                   ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 252 */                   _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 253 */                   _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fwhen_005f1);
/*     */                   
/* 255 */                   _jspx_th_c_005fforEach_005f0.setVar("props");
/*     */                   
/* 257 */                   _jspx_th_c_005fforEach_005f0.setItems("${list}");
/*     */                   
/* 259 */                   _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 260 */                   int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*     */                   try {
/* 262 */                     int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 263 */                     if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*     */                       for (;;) {
/* 265 */                         out.write("\n\t\t\t\t \t");
/* 266 */                         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */                         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 298 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 299 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                         }
/* 268 */                         out.write("\n\t\t\t\t\t");
/* 269 */                         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */                         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 298 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 299 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                         }
/* 271 */                         out.write("\n\t\t\t\t\t  <tr>\n\t\t\t\t\t    <td class=\"whitegrayborder-conf-mon\" align=\"center\" >");
/* 272 */                         if (_jspx_meth_fmt_005fformatNumber_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */                         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 298 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 299 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                         }
/* 274 */                         out.write("</td>\n\t\t\t\t\t   <td class=\"whitegrayborder-conf-mon\" align=\"center\">");
/* 275 */                         if (_jspx_meth_fmt_005fformatNumber_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */                         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 298 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 299 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                         }
/* 277 */                         out.write("</td>\n\t\t\t\t\t    <td class=\"whitegrayborder-conf-mon\" align=\"center\">");
/* 278 */                         if (_jspx_meth_fmt_005fformatNumber_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */                         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 298 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 299 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                         }
/* 280 */                         out.write("</td>\n\t\t\t\t\t\t<td class=\"whitegrayborder-conf-mon\">");
/* 281 */                         out.print(com.adventnet.utilities.stringutils.StrUtil.wrapWord(((String)request.getAttribute("sqlid")).trim(), 60, " "));
/* 282 */                         out.write("</td>\n\t\t\t\t\t    <td class=\"whitegrayborder-conf-mon\">");
/* 283 */                         out.print(com.adventnet.utilities.stringutils.StrUtil.wrapWord(((String)request.getAttribute("query")).trim(), 60, " "));
/* 284 */                         out.write("</td>\n\t\t\t\t\t  </tr>\n\t\t\t\t  ");
/* 285 */                         int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 286 */                         if (evalDoAfterBody != 2)
/*     */                           break;
/*     */                       }
/*     */                     }
/* 290 */                     if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*     */                     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 298 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 299 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                     }
/*     */                   }
/*     */                   catch (Throwable _jspx_exception)
/*     */                   {
/*     */                     for (;;)
/*     */                     {
/* 294 */                       int tmp1470_1469 = 0; int[] tmp1470_1467 = _jspx_push_body_count_c_005fforEach_005f0; int tmp1472_1471 = tmp1470_1467[tmp1470_1469];tmp1470_1467[tmp1470_1469] = (tmp1472_1471 - 1); if (tmp1472_1471 <= 0) break;
/* 295 */                       out = _jspx_page_context.popBody(); }
/* 296 */                     _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*     */                   } finally {
/* 298 */                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 299 */                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*     */                   }
/* 301 */                   out.write("\n  \t\t\t\t");
/* 302 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 303 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 307 */               if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 308 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*     */               }
/*     */               
/* 311 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 312 */               out.write("\n\t\t\t  ");
/*     */               
/* 314 */               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 315 */               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 316 */               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 317 */               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 318 */               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */                 for (;;) {
/* 320 */                   out.write("\n  \t\t\t\t\t<tr><td height=\"30px\" colspan=\"4\" class=\"bodytextbold\" align=\"center\">");
/* 321 */                   out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 322 */                   out.write("</td></tr>\n\t\t\t  ");
/* 323 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 324 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 328 */               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 329 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */               }
/*     */               
/* 332 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 333 */               out.write("\n  \t\t\t");
/* 334 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 335 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 339 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 340 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*     */           }
/*     */           else {
/* 343 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 344 */             out.write("\n\t\t</table>\n\t</div>\n</div>\n<div id=data>\n");
/* 345 */             JspRuntimeLibrary.include(request, response, "/jsp/OracleDiskReads.jsp" + ("/jsp/OracleDiskReads.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourcename", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourcename), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceid), request.getCharacterEncoding()), out, true);
/*     */           }
/* 347 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 348 */         out = _jspx_out;
/* 349 */         if ((out != null) && (out.getBufferSize() != 0))
/* 350 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 351 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 354 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 360 */     PageContext pageContext = _jspx_page_context;
/* 361 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 363 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 364 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 365 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 367 */     _jspx_th_c_005fset_005f1.setVar("query");
/*     */     
/* 369 */     _jspx_th_c_005fset_005f1.setScope("request");
/*     */     
/* 371 */     _jspx_th_c_005fset_005f1.setValue("${props.QUERY}");
/* 372 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 373 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 374 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 375 */       return true;
/*     */     }
/* 377 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 378 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 383 */     PageContext pageContext = _jspx_page_context;
/* 384 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 386 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 387 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 388 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 390 */     _jspx_th_c_005fset_005f2.setVar("sqlid");
/*     */     
/* 392 */     _jspx_th_c_005fset_005f2.setScope("request");
/*     */     
/* 394 */     _jspx_th_c_005fset_005f2.setValue("${props.SQLID}");
/* 395 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 396 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 397 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 398 */       return true;
/*     */     }
/* 400 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 401 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatNumber_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 406 */     PageContext pageContext = _jspx_page_context;
/* 407 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 409 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 410 */     _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
/* 411 */     _jspx_th_fmt_005fformatNumber_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 413 */     _jspx_th_fmt_005fformatNumber_005f0.setValue("${props.BUFFERGETS}");
/* 414 */     int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
/* 415 */     if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == 5) {
/* 416 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 417 */       return true;
/*     */     }
/* 419 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 420 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatNumber_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 425 */     PageContext pageContext = _jspx_page_context;
/* 426 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 428 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f1 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 429 */     _jspx_th_fmt_005fformatNumber_005f1.setPageContext(_jspx_page_context);
/* 430 */     _jspx_th_fmt_005fformatNumber_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 432 */     _jspx_th_fmt_005fformatNumber_005f1.setValue("${props.EXECUTIONS}");
/* 433 */     int _jspx_eval_fmt_005fformatNumber_005f1 = _jspx_th_fmt_005fformatNumber_005f1.doStartTag();
/* 434 */     if (_jspx_th_fmt_005fformatNumber_005f1.doEndTag() == 5) {
/* 435 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 436 */       return true;
/*     */     }
/* 438 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 439 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatNumber_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 444 */     PageContext pageContext = _jspx_page_context;
/* 445 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 447 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f2 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 448 */     _jspx_th_fmt_005fformatNumber_005f2.setPageContext(_jspx_page_context);
/* 449 */     _jspx_th_fmt_005fformatNumber_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 451 */     _jspx_th_fmt_005fformatNumber_005f2.setValue("${props.BUFFERGETSPEREXEC}");
/* 452 */     int _jspx_eval_fmt_005fformatNumber_005f2 = _jspx_th_fmt_005fformatNumber_005f2.doStartTag();
/* 453 */     if (_jspx_th_fmt_005fformatNumber_005f2.doEndTag() == 5) {
/* 454 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 455 */       return true;
/*     */     }
/* 457 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 458 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\OracleBufferGets_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */