/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.tags.Truncate;
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
/*     */ 
/*     */ public final class AvailabilityReport_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  33 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  37 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  40 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  41 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  42 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  46 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  47 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  48 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.release();
/*  49 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(javax.servlet.http.HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  56 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  59 */     JspWriter out = null;
/*  60 */     Object page = this;
/*  61 */     JspWriter _jspx_out = null;
/*  62 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  66 */       response.setContentType("text/html");
/*  67 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  69 */       _jspx_page_context = pageContext;
/*  70 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  71 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  72 */       session = pageContext.getSession();
/*  73 */       out = pageContext.getOut();
/*  74 */       _jspx_out = out;
/*     */       
/*  76 */       out.write("\n\n\n\n\n\n<html>\n<head>\n<title>");
/*  77 */       out.print(FormatUtil.getString("am.reporttab.availablityreport.text"));
/*  78 */       out.write("</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n</head>\n<body>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"25%\" class=\"tableheadingbborder\">");
/*  79 */       out.print(FormatUtil.getString("webclient.fault.details.properties.source"));
/*  80 */       out.write("</td>\n    <td width=\"18%\" class=\"tableheadingbborder\"> ");
/*  81 */       out.print(FormatUtil.getString("am.webclient.historydata.totaldown.text"));
/*  82 */       out.write("</td>\n    <td width=\"18%\" class=\"tableheadingbborder\"> ");
/*  83 */       out.print(FormatUtil.getString("am.webclient.historydata.capmttr.text"));
/*  84 */       out.write("</td>\n    <td width=\"18%\" class=\"tableheadingbborder\"> ");
/*  85 */       out.print(FormatUtil.getString("am.webclient.historydata.capmtbf.text"));
/*  86 */       out.write("</td>\n    <td height=\"18\" width=\"21%\" colspan='2' class=\"tableheadingbborder\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/*  87 */       out.print(FormatUtil.getString("am.webclient.historydata.uptime.text"));
/*  88 */       out.write("  %</td>\n  </tr>\n  ");
/*     */       
/*  90 */       ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  91 */       _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/*  92 */       _jspx_th_c_005fforEach_005f0.setParent(null);
/*     */       
/*  94 */       _jspx_th_c_005fforEach_005f0.setVar("row");
/*     */       
/*  96 */       _jspx_th_c_005fforEach_005f0.setItems("${data}");
/*     */       
/*  98 */       _jspx_th_c_005fforEach_005f0.setVarStatus("i");
/*  99 */       int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*     */       try {
/* 101 */         int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 102 */         if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*     */           for (;;) {
/* 104 */             out.write("\n  <tr>\n    <td class=\"whitegrayrightalign\" title='");
/* 105 */             if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 157 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 107 */             out.write(39);
/* 108 */             out.write(62);
/* 109 */             if (_jspx_meth_am_005fTruncate_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 157 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 111 */             out.write("</td>\n    <td class=\"whitegrayrightalign\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 112 */             if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 157 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 114 */             out.write("&period=1&resourcename=");
/* 115 */             if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 157 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 117 */             out.write("')\">\n     <span class='staticlinks' title=\"Click to view downtime history data\">   ");
/* 118 */             if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 157 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 120 */             out.write("</span></a></td>\n    <td class=\"whitegrayrightalign\">");
/* 121 */             if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 157 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 123 */             out.write("</td>\n    <td class=\"whitegrayrightalign\">");
/* 124 */             if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 157 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 126 */             out.write("</td>\n    <td width=\"15%\" height=\"20\" valign=\"middle\" class=\"whitegrayrightalign\" align='center'><table width=\"75\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n          <td width=\"65\"> <table width=\"60\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\">\n              <tr>\n                <td> <table width=\"");
/* 127 */             if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 157 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 129 */             out.write("%\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n                    <tr>\n                      <td  height=\"8\" class=\"availabilitybar\">");
/* 130 */             if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 157 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 132 */             out.write("</td>\n                    </tr>\n                  </table></td>\n              </tr>\n            </table></td>\n          <td class=\"bodytext\">&nbsp;");
/* 133 */             if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 157 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 135 */             out.write("%</td>\n        </tr>\n      </table>\n              <td width=\"96%\" align=\"right\" class=\"whitegrayrightalign\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 136 */             if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 157 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 138 */             out.write("&period=1&resourcename=");
/* 139 */             if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 157 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/* 141 */             out.write("')\">\n                  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 142 */             out.print(FormatUtil.getString("am.reporttab.availablityreport.title.text"));
/* 143 */             out.write("\"></a></td>\n\n\n\n</tr>\n  ");
/* 144 */             int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 145 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 149 */         if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 157 */           _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */         }
/*     */       }
/*     */       catch (Throwable _jspx_exception)
/*     */       {
/*     */         for (;;)
/*     */         {
/* 153 */           int tmp869_868 = 0; int[] tmp869_866 = _jspx_push_body_count_c_005fforEach_005f0; int tmp871_870 = tmp869_866[tmp869_868];tmp869_866[tmp869_868] = (tmp871_870 - 1); if (tmp871_870 <= 0) break;
/* 154 */           out = _jspx_page_context.popBody(); }
/* 155 */         _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*     */       } finally {
/* 157 */         _jspx_th_c_005fforEach_005f0.doFinally();
/* 158 */         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*     */       }
/* 160 */       out.write("\n</table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td align=\"center\" class=\"bodytext\"> <br> <table width=\"100%\" border=\"0\" cellspacing=\"5\" cellpadding=\"5\" class=\"grayfullborder\">\n        <tr>\n          <td class=\"tdindent\"><span class=\"bodytext\"><b style=\"color:#686868; font-size:10px;\"> ");
/* 161 */       out.print(FormatUtil.getString("am.webclient.historydata.mttr.text"));
/* 162 */       out.write("</b><br>\n           <p style=\"color:#686868;\"> ");
/* 163 */       out.print(FormatUtil.getString("am.webclient.availablityreport.mttrnote.text"));
/* 164 */       out.write(".</p>\n            <p style=\"color:#686868;\">");
/* 165 */       out.print(FormatUtil.getString("am.webclient.availablityreport.mttrsmallnote.text"));
/* 166 */       out.write(". </p>\n            <p style=\"color:#686868;\"> <b>");
/* 167 */       out.print(FormatUtil.getString("am.webclient.historydata.mtbf.text"));
/* 168 */       out.write(" </b><br>\n              ");
/* 169 */       out.print(FormatUtil.getString("am.webclient.availablityreport.mtbfnote.text"));
/* 170 */       out.write(". </p>\n            <p style=\"color:#686868;\">");
/* 171 */       out.print(FormatUtil.getString("am.webclient.availablityreport.mtbfsmallnote.text"));
/* 172 */       out.write(". <br>\n              <br>\n              * ");
/* 173 */       out.print(FormatUtil.getString("am.webclient.availablityreport.downtimenote.text"));
/* 174 */       out.write(". </p></span></td>\n        </tr>\n      </table></td>\n  </tr>\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 176 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 177 */         out = _jspx_out;
/* 178 */         if ((out != null) && (out.getBufferSize() != 0))
/* 179 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 180 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 183 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 189 */     PageContext pageContext = _jspx_page_context;
/* 190 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 192 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 193 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 194 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 196 */     _jspx_th_c_005fout_005f0.setValue("${row.Name}");
/* 197 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 198 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 199 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 200 */       return true;
/*     */     }
/* 202 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_am_005fTruncate_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 208 */     PageContext pageContext = _jspx_page_context;
/* 209 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 211 */     Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 212 */     _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 213 */     _jspx_th_am_005fTruncate_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 215 */     _jspx_th_am_005fTruncate_005f0.setLength(25);
/* 216 */     int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 217 */     if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 218 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 219 */         out = _jspx_page_context.pushBody();
/* 220 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 221 */         _jspx_th_am_005fTruncate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 222 */         _jspx_th_am_005fTruncate_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 225 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_am_005fTruncate_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 226 */           return true;
/* 227 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 228 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 231 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 232 */         out = _jspx_page_context.popBody();
/* 233 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*     */       }
/*     */     }
/* 236 */     if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 237 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 238 */       return true;
/*     */     }
/* 240 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 241 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_am_005fTruncate_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 246 */     PageContext pageContext = _jspx_page_context;
/* 247 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 249 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 250 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 251 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_am_005fTruncate_005f0);
/*     */     
/* 253 */     _jspx_th_c_005fout_005f1.setValue("${row.Name}");
/* 254 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 255 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 256 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 257 */       return true;
/*     */     }
/* 259 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 260 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 265 */     PageContext pageContext = _jspx_page_context;
/* 266 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 268 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 269 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 270 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 272 */     _jspx_th_c_005fout_005f2.setValue("${row.resourceid}");
/* 273 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 274 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 275 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 276 */       return true;
/*     */     }
/* 278 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 279 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 284 */     PageContext pageContext = _jspx_page_context;
/* 285 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 287 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 288 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 289 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 291 */     _jspx_th_c_005fout_005f3.setValue("${row.Name}");
/* 292 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 293 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 294 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 295 */       return true;
/*     */     }
/* 297 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 298 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 303 */     PageContext pageContext = _jspx_page_context;
/* 304 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 306 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 307 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 308 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 310 */     _jspx_th_c_005fout_005f4.setValue("${row.totaldowntime}");
/* 311 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 312 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 313 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 314 */       return true;
/*     */     }
/* 316 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 317 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 322 */     PageContext pageContext = _jspx_page_context;
/* 323 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 325 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 326 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 327 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 329 */     _jspx_th_c_005fout_005f5.setValue("${row.mttr}");
/* 330 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 331 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 332 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 333 */       return true;
/*     */     }
/* 335 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 336 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 341 */     PageContext pageContext = _jspx_page_context;
/* 342 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 344 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 345 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 346 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 348 */     _jspx_th_c_005fout_005f6.setValue("${row.mtbf}");
/* 349 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 350 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 351 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 352 */       return true;
/*     */     }
/* 354 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 355 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 360 */     PageContext pageContext = _jspx_page_context;
/* 361 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 363 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 364 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 365 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 367 */     _jspx_th_c_005fout_005f7.setValue("${row.available}");
/* 368 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 369 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 370 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 371 */       return true;
/*     */     }
/* 373 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 374 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 379 */     PageContext pageContext = _jspx_page_context;
/* 380 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 382 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 383 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 384 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 386 */     _jspx_th_c_005fif_005f0.setTest("${row.available !='0'}");
/* 387 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 388 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 390 */         out.write("<img src=\"../images/spacer.gif\" width=\"1\" height=\"8\">");
/* 391 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 392 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 396 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 397 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 398 */       return true;
/*     */     }
/* 400 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 401 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 406 */     PageContext pageContext = _jspx_page_context;
/* 407 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 409 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 410 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 411 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 413 */     _jspx_th_c_005fout_005f8.setValue("${row.available}");
/* 414 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 415 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 416 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 417 */       return true;
/*     */     }
/* 419 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 420 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 425 */     PageContext pageContext = _jspx_page_context;
/* 426 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 428 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 429 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 430 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 432 */     _jspx_th_c_005fout_005f9.setValue("${row.resourceid}");
/* 433 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 434 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 435 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 436 */       return true;
/*     */     }
/* 438 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 439 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 444 */     PageContext pageContext = _jspx_page_context;
/* 445 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 447 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 448 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 449 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 451 */     _jspx_th_c_005fout_005f10.setValue("${row.Name}");
/* 452 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 453 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 454 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 455 */       return true;
/*     */     }
/* 457 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 458 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\AvailabilityReport_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */