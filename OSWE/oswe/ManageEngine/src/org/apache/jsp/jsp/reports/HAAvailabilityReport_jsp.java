/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.tags.Truncate;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ 
/*     */ public final class HAAvailabilityReport_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  21 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  36 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  40 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  41 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  46 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  50 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*  51 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  52 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  53 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.release();
/*  54 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(javax.servlet.http.HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  61 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  64 */     JspWriter out = null;
/*  65 */     Object page = this;
/*  66 */     JspWriter _jspx_out = null;
/*  67 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  71 */       response.setContentType("text/html");
/*  72 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  74 */       _jspx_page_context = pageContext;
/*  75 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  76 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  77 */       session = pageContext.getSession();
/*  78 */       out = pageContext.getOut();
/*  79 */       _jspx_out = out;
/*     */       
/*  81 */       out.write("<!--$Id$-->\n\n\n\n\n\n \n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<script type=\"text/javascript\" src=\"/template/appmanager.js\"></script>\n</head>\n<body>\n");
/*     */       
/*  83 */       DBUtil db = new DBUtil();
/*  84 */       String MGVAL = db.getGlobalConfigValueForMGAvailability();
/*     */       
/*     */ 
/*  87 */       out.write(10);
/*  88 */       if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*     */         return;
/*  90 */       out.write("\n        ");
/*  91 */       String reporthaavailablity = (String)pageContext.getAttribute("availablityvalue");
/*  92 */       out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"footer\" align=\"center\" style=\"padding-top:5px;\">\n  <tr>\n    <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n        <tr> \n       \t  ");
/*  93 */       if (("true".equalsIgnoreCase(MGVAL)) && ("false".equals(com.adventnet.appmanager.util.Constants.addMaintenanceToAvailablity))) {
/*  94 */         out.write(" \n\t\t\t<td colspan=\"8\" class=\"tableheadingbborder\">");
/*  95 */         out.print(FormatUtil.getString("am.reporttab.availablityreport.heading.text", new String[] { reporthaavailablity }));
/*  96 */         out.write("</td>\n\t\t  ");
/*     */       } else {
/*  98 */         out.write(" \n\t\t\t<td colspan=\"4\" class=\"tableheadingbborder\">");
/*  99 */         out.print(FormatUtil.getString("am.reporttab.availablityreport.heading.text", new String[] { reporthaavailablity }));
/* 100 */         out.write("</td>\n\t\t   ");
/*     */       }
/* 102 */       out.write(" \n       \n        </tr>\n          <tr height=\"28\"> \n          ");
/* 103 */       out.write("\n\t    <td width=\"22%\" class=\"whitegrayrightalign\">");
/* 104 */       out.print(FormatUtil.getString("am.webclient.historydata.uptime.text"));
/* 105 */       out.write(" : <span class=\"bodytextbold\"> ");
/* 106 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */         return;
/* 108 */       out.write(" %</span></td>\n          <td width=\"22%\" class=\"whitegrayrightalign\">");
/* 109 */       out.print(FormatUtil.getString("am.reporttab.availablityreport.downtime.text"));
/* 110 */       out.write(" : <span class=\"bodytextbold\"> ");
/* 111 */       if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*     */         return;
/* 113 */       out.write(" %</span></td>\n\t\n\t");
/* 114 */       if (("true".equalsIgnoreCase(MGVAL)) && ("false".equals(com.adventnet.appmanager.util.Constants.addMaintenanceToAvailablity))) {
/* 115 */         out.write(" \n\t\t  <td width=\"22%\" class=\"whitegrayrightalign\">");
/* 116 */         out.print(FormatUtil.getString("am.reporttab.availablityreport.unmanaged.text"));
/* 117 */         out.write(" : <span class=\"bodytextbold\">");
/* 118 */         if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*     */           return;
/* 120 */         out.write("%</span></td>\n\t\t  <td width=\"30%\" class=\"whitegrayrightalign\">");
/* 121 */         out.print(FormatUtil.getString("am.reporttab.availablityreport.scheduled.text"));
/* 122 */         out.write(" : <span class=\"bodytextbold\">");
/* 123 */         if (_jspx_meth_c_005fout_005f4(_jspx_page_context))
/*     */           return;
/* 125 */         out.write("%</span></td>\n\t\t  ");
/*     */       }
/* 127 */       out.write("\n        </tr>\n      </table>\n      <br>\n      * ");
/* 128 */       out.print(FormatUtil.getString("am.reporttab.availablityreport.message.text"));
/* 129 */       out.write("</td>\n    <td></td>\n  </tr>\n</table>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr> \n    <td>&nbsp;&nbsp;</td>\n  </tr>\n</table>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\" align=\"center\">\n  <tr> \n  ");
/*     */       
/* 131 */       String AvailHeading = FormatUtil.getString("am.reporttab.availablityreport.headingformonitors.text", new String[] { reporthaavailablity });
/* 132 */       if (request.getAttribute("MonType").equals("1"))
/*     */       {
/* 134 */         AvailHeading = FormatUtil.getString("am.reporttab.availablityreport.headingforsubgroup.text", new String[] { reporthaavailablity });
/*     */       }
/*     */       
/* 137 */       out.write("\n     <td height=\"28\" colspan=\"7\" class=\"tableheadingbborder\">");
/* 138 */       out.print(AvailHeading);
/* 139 */       out.write("&quot;</td>\n\n  </tr>\n  <tr> \n    <td width=\"19%\" class=\"columnheading\">");
/* 140 */       out.print(FormatUtil.getString("webclient.fault.details.properties.source"));
/* 141 */       out.write("</td>\n    <td width=\"25%\" class=\"columnheading\">");
/* 142 */       out.print(FormatUtil.getString("am.webclient.historydata.totaldown.text"));
/* 143 */       out.write(" </td>\n    <td width=\"25%\" class=\"columnheading\"> ");
/* 144 */       out.print(FormatUtil.getString("am.webclient.historydata.capmttr.text"));
/* 145 */       out.write("</td>\n    <td width=\"25%\" class=\"columnheading\"> ");
/* 146 */       out.print(FormatUtil.getString("am.webclient.historydata.capmtbf.text"));
/* 147 */       out.write(" </td>\n    <td height=\"20\" class=\"columnheading\" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 148 */       out.print(FormatUtil.getString("am.webclient.historydata.uptime.text"));
/* 149 */       out.write(" %</td>\n      <td width=\"25%\" class=\"columnheading\"> &nbsp;&nbsp; </td>\n  </tr>\n  ");
/*     */       
/* 151 */       ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 152 */       _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 153 */       _jspx_th_c_005fforEach_005f0.setParent(null);
/*     */       
/* 155 */       _jspx_th_c_005fforEach_005f0.setVar("row");
/*     */       
/* 157 */       _jspx_th_c_005fforEach_005f0.setItems("${data}");
/*     */       
/* 159 */       _jspx_th_c_005fforEach_005f0.setVarStatus("i");
/* 160 */       int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*     */       try {
/* 162 */         int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 163 */         if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*     */           for (;;) {
/* 165 */             out.write(" \n  <tr> \n    <td class=\"whitegrayrightalign\" title=\"");
/* 166 */             if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */             {
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
/* 219 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 168 */             out.write(34);
/* 169 */             out.write(62);
/* 170 */             if (_jspx_meth_am_005fTruncate_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */             {
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
/* 219 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 172 */             out.write("</a></td>\n    <td class=\"whitegrayrightalign\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 173 */             if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */             {
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
/* 219 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 175 */             out.write("&period=1&resourcename=");
/* 176 */             if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */             {
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
/* 219 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 178 */             out.write("')\"> <span class='staticlinks' title=\"");
/* 179 */             out.print(FormatUtil.getString("am.reporttab.availablityreport.title.text"));
/* 180 */             out.write(34);
/* 181 */             out.write(62);
/* 182 */             if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */             {
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 219 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 184 */             out.write("</span></a></td>\n    <td class=\"whitegrayrightalign\">");
/* 185 */             if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */             {
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
/*     */ 
/*     */ 
/* 219 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 187 */             out.write("</td>\n    <td class=\"whitegrayrightalign\">");
/* 188 */             if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */             {
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
/* 219 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 190 */             out.write("</td>\n    <td width=\"15%\" height=\"33\" valign=\"middle\" class=\"whitegrayrightalign\"> \n      <table width=\"75\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td width=\"65\"><table width=\"60\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\">\n              <tr> \n                <td> \n                  <table  width=\"");
/* 191 */             if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */             {
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
/* 219 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 193 */             out.write("%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"9\" >\n              <tr>\n                <td height=\"9\" class=\"availabilitybar\">");
/* 194 */             if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */             {
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
/* 219 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 196 */             out.write("</td>\n              </tr>\n            </table></td>\n\t\t\t\n        </tr>\n      </table></td>\n\t  <td class=\"bodytext\">&nbsp;");
/* 197 */             if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */             {
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
/* 219 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 199 */             out.write("%</td>\n        </tr>\n         </table>\n    </td>\n    <td width=\"96%\" align=\"right\" class=\"whitegrayrightalign\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 200 */             if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */             {
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
/* 219 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 202 */             out.write("&period=1&resourcename=");
/* 203 */             if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */             {
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
/* 219 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 205 */             out.write("')\"> \n                  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"Click to view downtime history data\"></a></td>\n    \n    \n  </tr>\n  ");
/* 206 */             int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 207 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 211 */         if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 219 */           _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */         }
/*     */       }
/*     */       catch (Throwable _jspx_exception)
/*     */       {
/*     */         for (;;)
/*     */         {
/* 215 */           int tmp1318_1317 = 0; int[] tmp1318_1315 = _jspx_push_body_count_c_005fforEach_005f0; int tmp1320_1319 = tmp1318_1315[tmp1318_1317];tmp1318_1315[tmp1318_1317] = (tmp1320_1319 - 1); if (tmp1320_1319 <= 0) break;
/* 216 */           out = _jspx_page_context.popBody(); }
/* 217 */         _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*     */       } finally {
/* 219 */         _jspx_th_c_005fforEach_005f0.doFinally();
/* 220 */         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*     */       }
/* 222 */       out.write(" \n</table>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n  <tr>\n    <td align=\"center\" class=\"bodytext\"> <br>\n      <table width=\"100%\" border=\"0\" cellspacing=\"5\" cellpadding=\"5\" class=\"grayfullborder\">\n        <tr>\n          <td class=\"bodytext\"><b>");
/* 223 */       out.print(FormatUtil.getString("am.webclient.historydata.mttr.text"));
/* 224 */       out.write(" </b><br>\n            ");
/* 225 */       out.print(FormatUtil.getString("am.webclient.availablityreport.mttrnote.text"));
/* 226 */       out.write(". \n            <p>");
/* 227 */       out.print(FormatUtil.getString("am.webclient.availablityreport.mttrsmallnote.text"));
/* 228 */       out.write(". </p>\n            <p> <b>");
/* 229 */       out.print(FormatUtil.getString("am.webclient.historydata.mtbf.text"));
/* 230 */       out.write(" </b><br>\n              ");
/* 231 */       out.print(FormatUtil.getString("am.webclient.availablityreport.mtbfnote.text"));
/* 232 */       out.write(". </p>\n            <p>");
/* 233 */       out.print(FormatUtil.getString("am.webclient.availablityreport.mtbfsmallnote.text"));
/* 234 */       out.write(". <br>\n              <br>\n              * ");
/* 235 */       out.print(FormatUtil.getString("am.webclient.availablityreport.downtimenote.text"));
/* 236 */       out.write(". </p></td>\n        </tr>\n      </table>\n      </td>\n  </tr>\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 238 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 239 */         out = _jspx_out;
/* 240 */         if ((out != null) && (out.getBufferSize() != 0))
/* 241 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 242 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 245 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 251 */     PageContext pageContext = _jspx_page_context;
/* 252 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 254 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 255 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 256 */     _jspx_th_c_005fset_005f0.setParent(null);
/*     */     
/* 258 */     _jspx_th_c_005fset_005f0.setVar("availablityvalue");
/* 259 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 260 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 261 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 262 */         out = _jspx_page_context.pushBody();
/* 263 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 264 */         _jspx_th_c_005fset_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 267 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 268 */           return true;
/* 269 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 270 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 273 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 274 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 277 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 278 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 279 */       return true;
/*     */     }
/* 281 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 282 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 287 */     PageContext pageContext = _jspx_page_context;
/* 288 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 290 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 291 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 292 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*     */     
/* 294 */     _jspx_th_c_005fout_005f0.setValue("${overAllAvailability.Name}");
/* 295 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 296 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 297 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 298 */       return true;
/*     */     }
/* 300 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 301 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 306 */     PageContext pageContext = _jspx_page_context;
/* 307 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 309 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 310 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 311 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 313 */     _jspx_th_c_005fout_005f1.setValue("${overAllAvailability.ServicesUpPercent}");
/* 314 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 315 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 316 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 317 */       return true;
/*     */     }
/* 319 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 320 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 325 */     PageContext pageContext = _jspx_page_context;
/* 326 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 328 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 329 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 330 */     _jspx_th_c_005fout_005f2.setParent(null);
/*     */     
/* 332 */     _jspx_th_c_005fout_005f2.setValue("${overAllAvailability.ServicesDownPercent}");
/* 333 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 334 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 335 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 336 */       return true;
/*     */     }
/* 338 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 339 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 344 */     PageContext pageContext = _jspx_page_context;
/* 345 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 347 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 348 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 349 */     _jspx_th_c_005fout_005f3.setParent(null);
/*     */     
/* 351 */     _jspx_th_c_005fout_005f3.setValue("${overAllAvailability.ServicesUnMgPercent}");
/* 352 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 353 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 354 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 355 */       return true;
/*     */     }
/* 357 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 358 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 363 */     PageContext pageContext = _jspx_page_context;
/* 364 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 366 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 367 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 368 */     _jspx_th_c_005fout_005f4.setParent(null);
/*     */     
/* 370 */     _jspx_th_c_005fout_005f4.setValue("${overAllAvailability.ServicesSchPercent}");
/* 371 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 372 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 373 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 374 */       return true;
/*     */     }
/* 376 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 377 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 382 */     PageContext pageContext = _jspx_page_context;
/* 383 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 385 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 386 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 387 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 389 */     _jspx_th_c_005fout_005f5.setValue("${row.Name}");
/* 390 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 391 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 392 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 393 */       return true;
/*     */     }
/* 395 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 396 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_am_005fTruncate_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 401 */     PageContext pageContext = _jspx_page_context;
/* 402 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 404 */     Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 405 */     _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 406 */     _jspx_th_am_005fTruncate_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 408 */     _jspx_th_am_005fTruncate_005f0.setLength(25);
/* 409 */     int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 410 */     if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 411 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 412 */         out = _jspx_page_context.pushBody();
/* 413 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 414 */         _jspx_th_am_005fTruncate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 415 */         _jspx_th_am_005fTruncate_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 418 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_am_005fTruncate_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 419 */           return true;
/* 420 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 421 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 424 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 425 */         out = _jspx_page_context.popBody();
/* 426 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*     */       }
/*     */     }
/* 429 */     if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 430 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 431 */       return true;
/*     */     }
/* 433 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 434 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_am_005fTruncate_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 439 */     PageContext pageContext = _jspx_page_context;
/* 440 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 442 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 443 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 444 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_am_005fTruncate_005f0);
/*     */     
/* 446 */     _jspx_th_c_005fout_005f6.setValue("${row.Name}");
/* 447 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 448 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 449 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 450 */       return true;
/*     */     }
/* 452 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 453 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 458 */     PageContext pageContext = _jspx_page_context;
/* 459 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 461 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 462 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 463 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 465 */     _jspx_th_c_005fout_005f7.setValue("${row.resourceid}");
/* 466 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 467 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 468 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 469 */       return true;
/*     */     }
/* 471 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 472 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 477 */     PageContext pageContext = _jspx_page_context;
/* 478 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 480 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 481 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 482 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 484 */     _jspx_th_c_005fout_005f8.setValue("${row.resourcename}");
/* 485 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 486 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 487 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 488 */       return true;
/*     */     }
/* 490 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 491 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 496 */     PageContext pageContext = _jspx_page_context;
/* 497 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 499 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 500 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 501 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 503 */     _jspx_th_c_005fout_005f9.setValue("${row.totaldowntime}");
/* 504 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 505 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 506 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 507 */       return true;
/*     */     }
/* 509 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 510 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 515 */     PageContext pageContext = _jspx_page_context;
/* 516 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 518 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 519 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 520 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 522 */     _jspx_th_c_005fout_005f10.setValue("${row.mttr}");
/* 523 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 524 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 525 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 526 */       return true;
/*     */     }
/* 528 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 529 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 534 */     PageContext pageContext = _jspx_page_context;
/* 535 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 537 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 538 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 539 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 541 */     _jspx_th_c_005fout_005f11.setValue("${row.mtbf}");
/* 542 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 543 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 544 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 545 */       return true;
/*     */     }
/* 547 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 548 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 553 */     PageContext pageContext = _jspx_page_context;
/* 554 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 556 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 557 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 558 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 560 */     _jspx_th_c_005fout_005f12.setValue("${row.available}");
/* 561 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 562 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 563 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 564 */       return true;
/*     */     }
/* 566 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 567 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 572 */     PageContext pageContext = _jspx_page_context;
/* 573 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 575 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 576 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 577 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 579 */     _jspx_th_c_005fif_005f0.setTest("${row.available !='0'}");
/* 580 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 581 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 583 */         out.write("<img src=\"../images/spacer.gif\" width=\"8\" height=\"9\">");
/* 584 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 585 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 589 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 590 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 591 */       return true;
/*     */     }
/* 593 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 594 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 599 */     PageContext pageContext = _jspx_page_context;
/* 600 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 602 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 603 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 604 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 606 */     _jspx_th_c_005fout_005f13.setValue("${row.available}");
/* 607 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 608 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 609 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 610 */       return true;
/*     */     }
/* 612 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 613 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 618 */     PageContext pageContext = _jspx_page_context;
/* 619 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 621 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 622 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 623 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 625 */     _jspx_th_c_005fout_005f14.setValue("${row.resourceid}");
/* 626 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 627 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 628 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 629 */       return true;
/*     */     }
/* 631 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 632 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 637 */     PageContext pageContext = _jspx_page_context;
/* 638 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 640 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 641 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 642 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 644 */     _jspx_th_c_005fout_005f15.setValue("${row.resourcename}");
/* 645 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 646 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 647 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 648 */       return true;
/*     */     }
/* 650 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 651 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\HAAvailabilityReport_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */