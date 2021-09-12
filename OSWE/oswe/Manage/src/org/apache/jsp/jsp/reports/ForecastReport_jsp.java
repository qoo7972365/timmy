/*      */ package org.apache.jsp.jsp.reports;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ForecastReport_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   20 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   26 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*   27 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   46 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   50 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   51 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   52 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   53 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   54 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   55 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   56 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   62 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   66 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   67 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   68 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   69 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   70 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   71 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   72 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*   73 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
/*   74 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/*   75 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.release();
/*   76 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   83 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   86 */     JspWriter out = null;
/*   87 */     Object page = this;
/*   88 */     JspWriter _jspx_out = null;
/*   89 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*   93 */       response.setContentType("text/html");
/*   94 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*   96 */       _jspx_page_context = pageContext;
/*   97 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*   98 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*   99 */       session = pageContext.getSession();
/*  100 */       out = pageContext.getOut();
/*  101 */       _jspx_out = out;
/*      */       
/*  103 */       out.write("<!DOCTYPE html>\n");
/*  104 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/*  105 */       out.write("\n\n\n\n\n");
/*  106 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  107 */       out.write(10);
/*  108 */       response.setContentType("text/html;charset=UTF-8");
/*  109 */       out.write("\n\n<head>\n<title>");
/*  110 */       out.print(FormatUtil.getString("am.webclient.forecast.report.text"));
/*  111 */       out.write("</title>\n</head>\n<link href=\"/images/");
/*  112 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  114 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"/template/highcharts.js\"></script>\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n<style>\n\n.parentDiv {\n\twidth: 100%;\n\ttext-align: center;\n}\n.yearGraph {\n\tbackground: #fff none repeat scroll 0 0;\n\tdisplay: inline-block;\n\tlist-style: outside none none;\n\tmargin: 20px 0;\n\toverflow: hidden;\n\tpadding: 0;\n\tposition: relative;\n\ttext-align: center;\n\twidth: auto;\n}\n.yearGraph::before {\n\tbackground: #b8b8b8 none repeat scroll 0 0;\n\tcontent: \"\";\n\theight: 1px;\n\tleft: 0;\n\toverflow: hidden;\n\tposition: absolute;\n\ttop: 14px;\n\twidth: 100%;\n\tz-index: 1;\n}\n.yearGraph li:first-child {\n\tmargin-left: 0;\n}\n.yearGraph li:last-child {\n");
/*  115 */       out.write("\tmargin-right: 0;\n}\n.yearGraph li a {\n\tbackground: #fff none repeat scroll 0 0;\n\tborder: 2px solid #ffa244;\n\tborder-radius: 50%;\n\tcolor: #5c5e58;\n\tdisplay: inline-block;\n\tfont-family: \"lato_b\", sans-serif;\n\tfont-size: 11px;\n\theight: 30px;\n\tline-height: 30px;\n\ttext-align: center;\n\twidth: 30px;\n\tmargin: 0 4px;\n}\n.yearGraph li {\n\tbackground: #fff none repeat scroll 0 0;\n\tdisplay: inline-block;\n\tposition: relative;\n\ttext-align: center;\n\tvertical-align: middle;\n\tz-index: 2;\n\tmargin: 0 10px;\n}\n.yearGraph li:hover a, .yearGraph li a.active {\n    background: #ffa244 none repeat scroll 0 0;\n    color: #fff;\n}\n.settingsSec ul li:hover i, .rotateY li:hover a {\n    transform: rotateY(-360deg);\n    transition: all 0.5s ease 0s;\n}\n</style>\n\n<table class=\"darkheaderbg\" height=\"55\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n<tr>\n<td>\n<span class=\"headingboldwhite\">\n    ");
/*      */       
/*  117 */       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  118 */       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  119 */       _jspx_th_c_005fchoose_005f0.setParent(null);
/*  120 */       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  121 */       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */         for (;;) {
/*  123 */           out.write("\n    ");
/*  124 */           if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */             return;
/*  126 */           out.write("\n    ");
/*      */           
/*  128 */           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  129 */           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  130 */           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  131 */           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  132 */           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */             for (;;) {
/*  134 */               out.write("\n\t  ");
/*  135 */               out.print(FormatUtil.getString("am.webclient.reportstab.text"));
/*  136 */               out.write(" \n    ");
/*  137 */               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  138 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  142 */           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  143 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */           }
/*      */           
/*  146 */           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  147 */           out.write("\n     ");
/*  148 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  149 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  153 */       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  154 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */       }
/*      */       else {
/*  157 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  158 */         out.write("\n </span>\n</td>\n</tr>\n</table>\n\t");
/*      */         
/*  160 */         ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  161 */         _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/*  162 */         _jspx_th_c_005fchoose_005f1.setParent(null);
/*  163 */         int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/*  164 */         if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */           for (;;) {
/*  166 */             out.write(10);
/*  167 */             out.write(9);
/*  168 */             out.write(9);
/*      */             
/*  170 */             WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  171 */             _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  172 */             _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */             
/*  174 */             _jspx_th_c_005fwhen_005f1.setTest("${individualReport }");
/*  175 */             int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  176 */             if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */               for (;;) {
/*  178 */                 out.write("\n\t\t\t<table border=\"0\" width=\"100%\" cellpadding=\"10\" cellspacing=\"10\">\n\t\t\t\t\n\t\t\t\t");
/*      */                 
/*  180 */                 IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  181 */                 _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  182 */                 _jspx_th_c_005fif_005f0.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                 
/*  184 */                 _jspx_th_c_005fif_005f0.setTest("${percentageReport}");
/*  185 */                 int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  186 */                 if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                   for (;;) {
/*  188 */                     out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"100%\">\n\t\t\t\t\t\t<table width=\"100%\" class=\"lrtbdarkborder\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td colspan=\"2\" class=\"tableheadingbborder\" height=\"26\">\n\t\t\t\t\t\t\t\t\t");
/*  189 */                     out.print(FormatUtil.getString("am.webclient.forecast.utilization"));
/*  190 */                     out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"43%\">\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t<div id=\"barcontainer\" style=\"height:150px;\" ></div>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td width=\"58%\" valign=\"middle\">\n\t\t\t\t\t\t\t\t\t<table width=\"100%\" class=\"lrtbdarkborder\" cellspacing=\"0\" cellpadding=\"0\" border=\"12\">\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  191 */                     out.print(FormatUtil.getString("Monitor"));
/*  192 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  193 */                     out.print(FormatUtil.getString("am.webclient.common.average.text"));
/*  194 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  195 */                     out.print(FormatUtil.getString("am.webclient.forecast.eightypercentage.full.text"));
/*  196 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  197 */                     out.print(FormatUtil.getString("am.webclient.forecast.ninetypercentage.full.text"));
/*  198 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  199 */                     out.print(FormatUtil.getString("am.webclient.forecast.hundredpercentage.full.text"));
/*  200 */                     out.write("</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t");
/*  201 */                     if (_jspx_meth_c_005fforEach_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                       return;
/*  203 */                     out.write("\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*  204 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  205 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  209 */                 if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  210 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                 }
/*      */                 
/*  213 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  214 */                 out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"100%\">\n\t\t\t\t\t\t<table width=\"100%\" class=\"lrtbdarkborder\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td colspan=\"2\" class=\"tableheadingbborder\" height=\"26\">\n\t\t\t\t\t\t\t\t\t");
/*  215 */                 out.print(FormatUtil.getString("am.webclient.forecast.growthtrend"));
/*  216 */                 out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"40%\">&nbsp;\n\t\t\t\t\t\t\t\t\t<div id=\"predictionDataContainer\" style=\"max-width: 700px; height: 400px; margin: 0 auto\"></div>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td align=\"middle\">\n\t\t\t\t\t\t\t\t\t<table width=\"80%\" class=\"lrtbdarkborder\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  217 */                 out.print(FormatUtil.getString("Date"));
/*  218 */                 out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  219 */                 out.print(FormatUtil.getString("am.webclient.forecast.actualdata.text"));
/*  220 */                 out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  221 */                 out.print(FormatUtil.getString("am.webclient.forecast.forecastdata.text"));
/*  222 */                 out.write("</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t");
/*  223 */                 if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*      */                   return;
/*  225 */                 out.write("\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t");
/*  226 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  227 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  231 */             if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  232 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */             }
/*      */             
/*  235 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  236 */             out.write(10);
/*  237 */             out.write(9);
/*  238 */             out.write(9);
/*      */             
/*  240 */             OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  241 */             _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/*  242 */             _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f1);
/*  243 */             int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/*  244 */             if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */               for (;;) {
/*  246 */                 out.write("\n\t\t\t\t");
/*      */                 
/*  248 */                 org.apache.struts.taglib.html.FormTag _jspx_th_html_005fform_005f0 = (org.apache.struts.taglib.html.FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(org.apache.struts.taglib.html.FormTag.class);
/*  249 */                 _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  250 */                 _jspx_th_html_005fform_005f0.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                 
/*  252 */                 _jspx_th_html_005fform_005f0.setAction("/forecastReport.do");
/*      */                 
/*  254 */                 _jspx_th_html_005fform_005f0.setMethod("generateForecastReport");
/*  255 */                 int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  256 */                 if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                   for (;;) {
/*  258 */                     out.write("\n\t\t\t\t<input type=\"hidden\" name=\"attributeid\" value=\"");
/*  259 */                     if (_jspx_meth_c_005fout_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  261 */                     out.write("\">\n\t\t\t\t<input type=\"hidden\" name=\"actionMethod\" value=\"generateForecastReport\">\n\t\t\t\t<input type=\"hidden\" name=\"reporttype\" value=\"\">\n\t\t\t\t<input type=\"hidden\" name=\"growthtrend\" value=\"");
/*  262 */                     if (_jspx_meth_c_005fout_005f14(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  264 */                     out.write("\">\n\t\t\t\t<table width=\"100%\" height=\"35\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" class=\"reports-head-tile\" style=\"padding:5px 0px 8px 0px; \">\n\t\t\t  \t<tr>\t\n\t\t\t\t\t<td width=\"80%\" height=\"40\" style=\"padding-left: 10px;\">\n\t\t\t\t \t");
/*  265 */                     out.print(FormatUtil.getString("am.webclient.filterby.text"));
/*  266 */                     out.write(" \n\t\t\t\t\t");
/*      */                     
/*  268 */                     org.apache.struts.taglib.html.SelectTag _jspx_th_html_005fselect_005f0 = (org.apache.struts.taglib.html.SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(org.apache.struts.taglib.html.SelectTag.class);
/*  269 */                     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/*  270 */                     _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/*  272 */                     _jspx_th_html_005fselect_005f0.setProperty("numberOfRows");
/*      */                     
/*  274 */                     _jspx_th_html_005fselect_005f0.setOnchange("javascript:fnRows()");
/*      */                     
/*  276 */                     _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*  277 */                     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/*  278 */                     if (_jspx_eval_html_005fselect_005f0 != 0) {
/*  279 */                       if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  280 */                         out = _jspx_page_context.pushBody();
/*  281 */                         _jspx_th_html_005fselect_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  282 */                         _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  285 */                         out.write("\n\t\t\t\t\t\t");
/*      */                         
/*  287 */                         OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  288 */                         _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/*  289 */                         _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                         
/*  291 */                         _jspx_th_html_005foption_005f0.setKey(FormatUtil.getString("am.webclient.common.topten.text"));
/*      */                         
/*  293 */                         _jspx_th_html_005foption_005f0.setValue("10");
/*  294 */                         int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/*  295 */                         if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/*  296 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                         }
/*      */                         
/*  299 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0);
/*  300 */                         out.write("\n\t\t\t\t\t\t");
/*      */                         
/*  302 */                         OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  303 */                         _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/*  304 */                         _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                         
/*  306 */                         _jspx_th_html_005foption_005f1.setKey(FormatUtil.getString("am.webclient.common.toptwenty.text"));
/*      */                         
/*  308 */                         _jspx_th_html_005foption_005f1.setValue("20");
/*  309 */                         int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/*  310 */                         if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/*  311 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                         }
/*      */                         
/*  314 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1);
/*  315 */                         out.write("\n\t\t\t\t\t\t");
/*      */                         
/*  317 */                         OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  318 */                         _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/*  319 */                         _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                         
/*  321 */                         _jspx_th_html_005foption_005f2.setKey(FormatUtil.getString("am.webclient.common.topfifty.text"));
/*      */                         
/*  323 */                         _jspx_th_html_005foption_005f2.setValue("50");
/*  324 */                         int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/*  325 */                         if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/*  326 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                         }
/*      */                         
/*  329 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2);
/*  330 */                         out.write("\n\t\t\t\t\t\t");
/*      */                         
/*  332 */                         OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  333 */                         _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/*  334 */                         _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                         
/*  336 */                         _jspx_th_html_005foption_005f3.setKey(FormatUtil.getString("am.monitortab.all.text"));
/*      */                         
/*  338 */                         _jspx_th_html_005foption_005f3.setValue("-1");
/*  339 */                         int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/*  340 */                         if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/*  341 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                         }
/*      */                         
/*  344 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3);
/*  345 */                         out.write("\n\t\t\t\t    ");
/*  346 */                         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/*  347 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  350 */                       if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  351 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  354 */                     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/*  355 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                     }
/*      */                     
/*  358 */                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/*  359 */                     out.write("\n\t\t\t\t\t ");
/*      */                     
/*  361 */                     org.apache.struts.taglib.html.SelectTag _jspx_th_html_005fselect_005f1 = (org.apache.struts.taglib.html.SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(org.apache.struts.taglib.html.SelectTag.class);
/*  362 */                     _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/*  363 */                     _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                     
/*  365 */                     _jspx_th_html_005fselect_005f1.setProperty("period");
/*      */                     
/*  367 */                     _jspx_th_html_005fselect_005f1.setOnchange("javascript:fnPeriod()");
/*      */                     
/*  369 */                     _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/*  370 */                     int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/*  371 */                     if (_jspx_eval_html_005fselect_005f1 != 0) {
/*  372 */                       if (_jspx_eval_html_005fselect_005f1 != 1) {
/*  373 */                         out = _jspx_page_context.pushBody();
/*  374 */                         _jspx_th_html_005fselect_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  375 */                         _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                       }
/*      */                       for (;;) {
/*  378 */                         out.write("\n\t\t\t\t\t \t");
/*      */                         
/*  380 */                         OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  381 */                         _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/*  382 */                         _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f1);
/*      */                         
/*  384 */                         _jspx_th_html_005foption_005f4.setKey(FormatUtil.getString("am.webclient.reports.period.dropdown.customtime.text"));
/*      */                         
/*  386 */                         _jspx_th_html_005foption_005f4.setValue("4");
/*  387 */                         int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/*  388 */                         if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/*  389 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                         }
/*      */                         
/*  392 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4);
/*  393 */                         out.write("\t\n\t\t\t\t        ");
/*      */                         
/*  395 */                         OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  396 */                         _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/*  397 */                         _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f1);
/*      */                         
/*  399 */                         _jspx_th_html_005foption_005f5.setKey(FormatUtil.getString("am.webclient.historydata.period.7days.text"));
/*      */                         
/*  401 */                         _jspx_th_html_005foption_005f5.setValue("1");
/*  402 */                         int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/*  403 */                         if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/*  404 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                         }
/*      */                         
/*  407 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5);
/*  408 */                         out.write("\n\t\t\t\t        ");
/*      */                         
/*  410 */                         OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  411 */                         _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/*  412 */                         _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f1);
/*      */                         
/*  414 */                         _jspx_th_html_005foption_005f6.setKey(FormatUtil.getString("am.webclient.historydata.period.30days.text"));
/*      */                         
/*  416 */                         _jspx_th_html_005foption_005f6.setValue("2");
/*  417 */                         int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/*  418 */                         if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/*  419 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                         }
/*      */                         
/*  422 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6);
/*  423 */                         out.write("\n\t\t\t\t         ");
/*      */                         
/*  425 */                         OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  426 */                         _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/*  427 */                         _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f1);
/*      */                         
/*  429 */                         _jspx_th_html_005foption_005f7.setKey(FormatUtil.getString("am.webclient.historydata.period.last3months.text"));
/*      */                         
/*  431 */                         _jspx_th_html_005foption_005f7.setValue("11");
/*  432 */                         int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/*  433 */                         if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/*  434 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                         }
/*      */                         
/*  437 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7);
/*  438 */                         out.write("\n\t\t\t\t        ");
/*      */                         
/*  440 */                         OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  441 */                         _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/*  442 */                         _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f1);
/*      */                         
/*  444 */                         _jspx_th_html_005foption_005f8.setKey(FormatUtil.getString("am.webclient.historydata.period.last6months.text"));
/*      */                         
/*  446 */                         _jspx_th_html_005foption_005f8.setValue("9");
/*  447 */                         int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/*  448 */                         if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/*  449 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                         }
/*      */                         
/*  452 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8);
/*  453 */                         out.write("    \n\t\t\t\t         ");
/*      */                         
/*  455 */                         OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  456 */                         _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/*  457 */                         _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f1);
/*      */                         
/*  459 */                         _jspx_th_html_005foption_005f9.setKey(FormatUtil.getString("am.webclient.historydata.period.last1year.text"));
/*      */                         
/*  461 */                         _jspx_th_html_005foption_005f9.setValue("5");
/*  462 */                         int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/*  463 */                         if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/*  464 */                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                         }
/*      */                         
/*  467 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9);
/*  468 */                         out.write("\n\t\t\t\t        ");
/*  469 */                         int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/*  470 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*  473 */                       if (_jspx_eval_html_005fselect_005f1 != 1) {
/*  474 */                         out = _jspx_page_context.popBody();
/*      */                       }
/*      */                     }
/*  477 */                     if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/*  478 */                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                     }
/*      */                     
/*  481 */                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/*  482 */                     out.write("\n\t\t\t\t        &nbsp;&nbsp;&nbsp;\n\t\t\t\t        <span id=\"cust\" style=\"display:none\"> &nbsp; ");
/*  483 */                     out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/*  484 */                     out.write(32);
/*  485 */                     if (_jspx_meth_c_005fchoose_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  487 */                     out.write(" <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" align=\"absmiddle\" id=startTrigger title=\"");
/*  488 */                     out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  489 */                     out.write("\"></a> \n\t\t\t      \t\t<SCRIPT type=text/javascript>\n\t\t\t\t\t\t\t\t    Calendar.setup({\n\t\t\t\t\t\t\t        inputField     :    \"start\",     // NO I18N\n\t\t\t\t\t\t\t        ifFormat       :    \"%Y-%m-%d %H:%M\",      // NO I18N\n\t\t\t\t\t\t\t\t\tshowsTime\t   :    true,\t\n\t\t\t\t\t\t\t        button         :    \"startTrigger\",  // NO I18N\n\t\t\t\t\t\t\t\t\ttimeFormat     :    \"24\",\n\t\t\t\t\t\t\t        align          :    \"Bl\",           // NO I18N\n\t\t\t\t\t\t\t        singleClick    :    true\n\t\t\t\t\t\t\t\t    });\n\t\t\t\t\t\t\t\t </SCRIPT>\n\t\t\t   \n\t\t\t  \t\t\t&nbsp;&nbsp;&nbsp;\n\t\t\t\n\t\t\t\t\t      ");
/*  490 */                     out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/*  491 */                     out.write(32);
/*  492 */                     out.write(32);
/*  493 */                     if (_jspx_meth_c_005fchoose_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                       return;
/*  495 */                     out.write(" <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" align=\"absmiddle\" id=endTrigger title=\"");
/*  496 */                     out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  497 */                     out.write("\"></a> \n\t\t\t\t\t      <SCRIPT type=text/javascript>\n\t\t\t\t\t\t\t\t    Calendar.setup({\n\t\t\t\t\t\t\t        inputField     :    \"end\",     // NO I18N\n\t\t\t\t\t\t\t        ifFormat       :    \"%Y-%m-%d %H:%M\",      // NO I18N\n\t\t\t\t\t\t\t\t\tshowsTime\t   :    true,\t\n\t\t\t\t\t\t\t        button         :    \"endTrigger\",  // NO I18N\n\t\t\t\t\t\t\t\t\ttimeFormat     :    \"24\",\n\t\t\t\t\t\t\t        align          :    \"Bl\",           // NO I18N\n\t\t\t\t\t\t\t        singleClick    :    true\n\t\t\t\t\t\t\t\t    });\n\t\t\t\t\t\t\t\t </SCRIPT>\n\t\t\t&nbsp;&nbsp;&nbsp;\n\t\t\t <input type=\"button\" name=\"show\" value=\"");
/*  498 */                     out.print(FormatUtil.getString("Go"));
/*  499 */                     out.write("\" class=\"buttons btn_highlt\" onClick=\"javascript:reload()\"> \n\t\t\t</span>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td align=\"right\" width=\"20%\"  style=\"padding-right:10px;\">\n     \t\t\t\t\t<a class=\"staticlinks\" href=\"javascript:generateReport()\"><img  align=\"center\" src=\"../images/icon_csv.gif\" border=\"0\" alt=\"CSV Report\" title=\"");
/*  500 */                     out.print(FormatUtil.getString("am.reporttab.csvtitle.text"));
/*  501 */                     out.write("\"></a>\n    \t\t\t\t</td>\n\t\t\t  </tr>\n\t\t\t</table>\n\t\t\t");
/*  502 */                     int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  503 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  507 */                 if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  508 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                 }
/*      */                 
/*  511 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  512 */                 out.write("\n\t\t\t");
/*      */                 
/*  514 */                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  515 */                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  516 */                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fotherwise_005f2);
/*      */                 
/*  518 */                 _jspx_th_c_005fif_005f1.setTest("${!nodata}");
/*  519 */                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  520 */                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                   for (;;) {
/*  522 */                     out.write("\n\t\t\t<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"10\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"55%\">\n\t\t\t\t\t\t<table width=\"100%\" class=\"lrtbdarkborder\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\" height=\"26\">\n\t\t\t\t\t\t\t\t\t");
/*  523 */                     out.print(FormatUtil.getString("am.webclient.forecast.growthtrend"));
/*  524 */                     out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t<div class=\"parentDiv\">\n\t\t\t\t\t\t\t  \t\t\t<ul class=\"yearGraph rotateY\">\n\t\t\t\t\t\t\t\t\t\t    <li><a href=\"javascript:void(0)\" onclick=\"javascript:fnGrowthTrend('1')\"  id=\"1\">1m</a></li>\t\t");
/*  525 */                     out.write("\n\t\t\t\t\t\t\t\t\t\t    <li><a href=\"javascript:void(0)\" onclick=\"javascript:fnGrowthTrend('2')\" id=\"2\">2m</a></li>\t\t");
/*  526 */                     out.write("\n\t\t\t\t\t\t\t\t\t\t    <li><a href=\"javascript:void(0)\" onclick=\"javascript:fnGrowthTrend('3')\" id=\"3\">3m</a></li>\t\t");
/*  527 */                     out.write("\n\t\t\t\t\t\t\t\t\t\t    <li><a href=\"javascript:void(0)\" onclick=\"javascript:fnGrowthTrend('4')\" id=\"4\">4m</a></li>\t\t");
/*  528 */                     out.write("\n\t\t\t\t\t\t\t\t\t\t    <li><a href=\"javascript:void(0)\" onclick=\"javascript:fnGrowthTrend('5')\"  id=\"5\">5m</a></li>\t\t");
/*  529 */                     out.write("\n\t\t\t\t\t\t\t\t\t\t    <li><a href=\"javascript:void(0)\" onclick=\"javascript:fnGrowthTrend('6')\"  id=\"6\">6m</a></li>\t\t");
/*  530 */                     out.write("\n\t\t\t\t\t\t\t  \t\t\t</ul>\n\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t<div id=\"container\" ></div>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\n\t\t\t\t\t\t\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/*      */                     
/*  532 */                     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  533 */                     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  534 */                     _jspx_th_c_005fif_005f2.setParent(_jspx_th_c_005fif_005f1);
/*      */                     
/*  536 */                     _jspx_th_c_005fif_005f2.setTest("${percentageReport }");
/*  537 */                     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  538 */                     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                       for (;;) {
/*  540 */                         out.write("\n\t\t\t\t\t<td width=\"45%\" valign=\"top\">\n\t\t\t\t\t\t<table width=\"100%\" class=\"lrtbdarkborder\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\" height=\"26\">\n\t\t\t\t\t\t\t\t\t");
/*  541 */                         out.print(FormatUtil.getString("am.webclient.forecast.utilization"));
/*  542 */                         out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t<div id=\"barcontainer\" ></div>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/*  543 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/*  544 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  548 */                     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  549 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                     }
/*      */                     
/*  552 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  553 */                     out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"2\"  valign=\"top\">\n\t\t\t\t\t\t<table width=\"100%\" class=\"lrtbdarkborder\" cellspacing=\"0\" cellpadding=\"0\" border=\"5\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  554 */                     out.print(FormatUtil.getString("Monitor"));
/*  555 */                     out.write("</td>\n\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  556 */                     out.print(FormatUtil.getString("am.webclient.common.average.text"));
/*  557 */                     out.write("</td>\n\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  558 */                     if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                       return;
/*  560 */                     out.write("</td>\n\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  561 */                     if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                       return;
/*  563 */                     out.write("</td>\n\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  564 */                     if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                       return;
/*  566 */                     out.write("</td>\n\t\t\t\t\t\t\t\t");
/*      */                     
/*  568 */                     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  569 */                     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/*  570 */                     _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fif_005f1);
/*      */                     
/*  572 */                     _jspx_th_c_005fif_005f3.setTest("${percentageReport }");
/*  573 */                     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/*  574 */                     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                       for (;;) {
/*  576 */                         out.write("\n\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  577 */                         out.print(FormatUtil.getString("am.webclient.forecast.ninetypercentage.full.text"));
/*  578 */                         out.write("</td>\n\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder\">");
/*  579 */                         out.print(FormatUtil.getString("am.webclient.forecast.hundredpercentage.full.text"));
/*  580 */                         out.write("</td>\n\t\t\t\t\t\t\t\t");
/*  581 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/*  582 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/*  586 */                     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/*  587 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                     }
/*      */                     
/*  590 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*  591 */                     out.write("\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t");
/*  592 */                     if (_jspx_meth_c_005fforEach_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                       return;
/*  594 */                     out.write("\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t");
/*  595 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  596 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  600 */                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  601 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                 }
/*      */                 
/*  604 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  605 */                 out.write("\n\t\t\t");
/*  606 */                 if (_jspx_meth_c_005fif_005f5(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/*      */                   return;
/*  608 */                 out.write(10);
/*  609 */                 out.write(9);
/*  610 */                 out.write(9);
/*  611 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/*  612 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*      */             }
/*  616 */             if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/*  617 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */             }
/*      */             
/*  620 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/*  621 */             out.write(10);
/*  622 */             out.write(9);
/*  623 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/*  624 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  628 */         if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/*  629 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*      */         }
/*      */         else {
/*  632 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*  633 */           out.write("\n\t\n\t\n\t");
/*  634 */           org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/footer.jsp", out, false);
/*  635 */           out.write("\n\t\n\t\n\n\n<script>\n\n\nvar seriesChartData\nvar hundredPercentageChart\n\njQuery(document).ready(function(){\n\t\n\tvar attributeid = '");
/*  636 */           out.print(request.getAttribute("attributeid"));
/*  637 */           out.write("'\n\tvar finalValue = '");
/*  638 */           out.print(request.getAttribute("fullPercentageData"));
/*  639 */           out.write("'\n\tvar growthTrend = '");
/*  640 */           out.print(request.getAttribute("growthTrendValue"));
/*  641 */           out.write("'\n\tvar dataModel = JSON.parse(finalValue)\n\tvar comboValue = '");
/*  642 */           out.print(request.getAttribute("period"));
/*  643 */           out.write("'\n\t\tjQuery(\"[name=period]\").val(comboValue)\t\t// NO i18n\n\tif(comboValue == '4'){\n\t\tjQuery(\"#cust\").show();\t// NO I18N\n\t}\n\t\n\tvar chartDataValue = '");
/*  644 */           out.print(request.getAttribute("chartData"));
/*  645 */           out.write("'\n\tvar chartDataModel = JSON.parse(chartDataValue)\n\t");
/*  646 */           if (_jspx_meth_c_005fif_005f6(_jspx_page_context))
/*      */             return;
/*  648 */           out.write(10);
/*  649 */           out.write(9);
/*  650 */           if (_jspx_meth_c_005fif_005f7(_jspx_page_context))
/*      */             return;
/*  652 */           out.write(10);
/*  653 */           out.write(9);
/*  654 */           if (_jspx_meth_c_005fif_005f8(_jspx_page_context))
/*      */             return;
/*  656 */           out.write("\n\tjQuery(\"#\"+growthTrend).addClass(\"active\")\n})\n\n\tfunction forecastDetails(resourceid,attributeid){\n\t\t \n\t\t var rows = jQuery(\"[name=numberOfRows]\").val();\t\t// NO I18N\n\t\t var timePeriod = jQuery(\"[name=period]\").val();\t\t// NO I18N\n\t\t var growthTrend = jQuery(\"[name=growthtrend]\").val();\t// NO I18N\n\t\t var url = '/forecastReport.do?actionMethod=generateForecastReport&attributeid='+attributeid+'&resourceid='+resourceid+'&numberOfRows='+rows+'&period='+timePeriod+'&individualReport=true&growthtrend='+growthTrend;\t\t// NO I18N\n\t\t window.open(url,'','resizable=yes,scrollbars=yes,width=1160,height=700,top=15,left=15');\n\t}\n\n\tfunction fnPeriod(){\n\t\tdocument.forms[0].reporttype.value='';\n\t\tjQuery('[name=startDate]').val('')\t\t// NO I18N\n\t\tjQuery('[name=endDate]').val('')\t\t// NO I18N\n\t\tvar comboValue = jQuery(\"[name=period]\").val();\t\t// NO I18N\n\t\t\n\t\tif(comboValue == '4'){\n\t\t\tjQuery(\"#cust\").show();\t// NO I18N\n\t\t}else{\n\t\t\tdocument.forms[0].submit();\n\t\t}\n\t}\n\n\tfunction fnRows(){\n\t\tdocument.forms[0].reporttype.value='';\n\t\tdocument.forms[0].submit();\n");
/*  657 */           out.write("\t}\n\t\n\tfunction fnGrowthTrend(trendValue){\n\t\tdocument.forms[0].reporttype.value='';\n\t\tdocument.forms[0].growthtrend.value = trendValue\n\t\tdocument.forms[0].submit();\n\t}\n\n\tfunction fnSetEndTime(a)\n\t{\n\t\tdocument.forms[0].endDate.value=a;\n\t}\n\tfunction fnSetStartTime(a)\n\t{\n\t\tdocument.forms[0].startDate.value=a;\n\t}\n\t\n\tfunction generateReport(type){\n\t\tdocument.forms[0].reporttype.value=\"csv\";\n\t\tdocument.forms[0].submit();\n\t}\n\t\n\tfunction reload()\n\t{\n\t        if(document.forms[0].startDate.value=='')\n\t        {\n\t            alert(\"");
/*  658 */           out.print(FormatUtil.getString("am.webclient.historydata.jsalertforstarttime"));
/*  659 */           out.write("\");\n\t            return false\n\t        }\n\t        else if(document.forms[0].endDate.value=='')\n\t        {\n\t            alert(\"");
/*  660 */           out.print(FormatUtil.getString("am.webclient.historydata.jsalertforendtime"));
/*  661 */           out.write("\")\n\t            return false\n\t        }\n\t        else if(document.forms[0].startDate.value > document.forms[0].endDate.value)\n\t        {\n\t                alert(\"");
/*  662 */           out.print(FormatUtil.getString("am.webclient.historydata.jsalertforgtstartime"));
/*  663 */           out.write("\" );\n\t                document.forms[0].startDate.value='';\n\t                document.forms[0].endDate.value='';\n\t\t\t\t\t document.forms[1].startDate.value='';\n\t                document.forms[1].endDate.value='';\n\t                return false;\n\t         }\n\t         else\n\t         {\n\t            document.forms[0].period.value='4';\n\t            document.forms[0].submit();\n\t         }\n\t}\n\t\n\tfunction fullPercentage(dataModel){\n\t\t\n\t\tvar holderName ='barcontainer'; \t\t// NO I18N\n\n\t\tif( this.height != null && this.width != null ) {\n\t\t    $('#'+holderName).width(this.width).height(this.height) ;       //No I18N\n\t\t  } \n\n\t\tHighcharts.Renderer.prototype.symbols.threshold = function(x, y, width, height) {    \n\t\t    return ['M',x ,y + width / 2,'L',x+height,y + width / 2];//No I18N\n\t\t  };\n\t\t\n\t\t\n\t\t\t  hundredPercentageChart = new Highcharts.Chart({\n\t\t\t\t     chart:{\n\t\t\t\t        renderTo:holderName,//No I18N\n\t\t\t\t        type:'bar' , //No I18N\n\t\t\t\t        marginLeft: 30,    \n\t\t\t\t        backgroundColor: this.backgroundColor,        \n");
/*  664 */           out.write("\t\t\t\t    },\n\t\t\t\t    credits:{enabled:false},\n\t\t\t\t    exporting:{enabled:false},\n\t\t\t\t    title:{text:''},\n\t\t\t\t    legend:{enabled:false},\n\t\t\t\t    xAxis:\n\t\t\t\t    {\n\t\t\t\t        tickLength: 20,\n\t\t\t\t        tickColor:'#C0C0C0',//No I18N\n\t\t\t\t        gridLineWidth:0,\n\t\t\t\t        //gridLineColor:'#676767',//No I18N\n\t\t\t\t        labels: \n\t\t\t\t        {\n\t\t\t\t            x:5,\n\t\t\t\t            style: \n\t\t\t\t            {                \n\t\t\t\t\t\t\t\t        fontSize: '1em',  //NO I18N\n\t\t\t\t                color: '#fff' //NO I18N\n\t\t\t\t            } ,\n\t\t\t\t            align: 'left'//No I18N\n\t\t\t\t        },\n\t\t\t\t        lineColor:'#C0C0C0',//No I18N\n\t\t\t\t        lineWidth:1,\n\t\t\t\t        categories:dataModel.monames\n\t\t\t\t    },\n\t\t\t\t    yAxis:{\n\t\t\t\t        max:100,\n\t\t\t\t        tickInterval:10,\n\t\t\t\t        allowDecimals:false,        \n\t\t\t\t        lineColor:'#eee',//No I18N\n\t\t\t\t        gridLineWidth:1,\n\t\t\t\t        gridLineColor:'#C0C0C0',//No I18N\n\t\t\t\t        endOnTick:true,\n\t\t\t\t        title:{text: ''},       \n\t\t\t\t        endOnTick:false,\n\t\t\t\t        startOnTick:false,       \n");
/*  665 */           out.write("\t\t\t\t        labels: {\n\t\t\t\t           enabled :true, \n\t\t\t\t           y:10\n\t\t\t\t        },\n\t\t\t\t        plotBands:[,\n\t\t\t\t                   {from:80,to:90,color: '#ffcdcd'},    //NO I18N\n\t\t\t\t                   {from:90,to:100,color: '#ff8080'}]   //NO I18N\n\t\t\t\t     },\n\t\t\t\t     tooltip: {\n\t\t\t\t            backgroundColor: '#fff',//No I18N\n\t\t\t\t            borderColor: '#ccc',//No I18N\n\t\t\t\t            borderRadius: 0,\n\t\t\t\t            borderWidth: 1,  \n\t\t\t\t            shadow: {\n\t\t\t\t              color : \"#666\", \n\t\t\t\t              offsetX : 0 , \n\t\t\t\t              offsetY : 0 ,\n\t\t\t\t              width: 2\n\t\t\t\t            },\n\t\t\t\t            headerFormat: '<b>{point.key}</b><br>',//No I18N\n\t\t\t\t            pointFormat: '<span style=\"color:{series.color}\">\\u25CF</span> {series.name}: {point.y} %'//No I18N\n\t\t\t\t        },\n\t\t\t\t    plotOptions:\n\t\t\t\t    {\n\t\t\t\t        bar:\n\t\t\t\t        { \n\t\t\t\t            grouping: false,\n\t\t\t\t            shadow:false,\n\t\t\t\t            borderWidth:0, \n\t\t\t\t            pointPadding:.35,\n\t\t\t\t            groupPadding:0                     \n");
/*  666 */           out.write("\t\t\t\t        },\n\t\t\t\t        scatter:\n\t\t\t\t        {\n\t\t\t\t            color: '#C0C0C0',   //NO I18N\n\t\t\t\t            visible: true,\n\t\t\t\t            marker:{\n\t\t\t\t                symbol:'threshold',//No I18N\n\t\t\t\t                lineWidth:6,\n\t\t\t\t                radius:15,\n\t\t\t\t                lineColor:'#ff5000',  //NO I18N\n\t\t\t\t                fillColor: '#ff5000', //NO I18N\n\t\t\t\t                hover:\n\t\t\t\t                {\n\t\t\t\t                  lineColor:'#ff5000',  //NO I18N\n\t\t\t\t                  fillColor: '#ff5000'  //NO I18N\n\t\t\t\t                },\n\t\t\t\t                select:\n\t\t\t\t                {\n\t\t\t\t                  lineColor:'#ff5000',  //NO I18N\n\t\t\t\t                  fillColor: '#ff5000'  //NO I18N\n\t\t\t\t                }\n\t\t\t\t        }, \n\t\t\t\t        tooltip : \n\t\t\t\t                {\n\t\t\t\t                    headerFormat: '<b>{series.name}</b><br>',//No I18N\n\t\t\t\t                    pointFormat : 'Projected to reach {point.y}% on Date : {point.name}',//No I18N\n\t\t\t\t                    positioner: function(boxWidth, boxHeight, point) \n");
/*  667 */           out.write("\t\t\t\t                    {\n\t\t\t\t                      return { x: point.plotX + 20, y: point.plotY\n\t\t\t\t                    };\n\t\t\t\t        }\n\t\t\t\t                    \n\t\t\t\t                }\n\n\t\t\t\t        },\n\t\t\t\t        series: \n\t\t\t\t        {\n\t\t\t\t          pointWidth: 25//width of the column bars irrespective of the chart size\n\t\t\t\t        }, \n\t\t\t\t        column: \n\t\t\t\t        {\n\t\t\t\t          pointPadding: 0.2,\n\t\t\t\t          borderWidth: 1\n\t\t\t\t        }\n\t\t\t\t    },\n\t\t\t\t    series:[{        \n\t\t\t\t        name:'Remaining Capacity',        //No I18N\n\t\t\t\t        enableMouseTracking:false,        \n\t\t\t\t        stacking:'normal',        //No I18N\n\t\t\t\t        color:'rgba(156,156,156,.1)',//No I18N\n\t\t\t\t        data:dataModel.remainingValue\n\t\t\t\t     },{\n\t\t\t\t        name:'");
/*  668 */           out.print(FormatUtil.getString("Current Value"));
/*  669 */           out.write("',//No I18N\n\t\t\t\t        enableMouseMouseTracking:false,\n\t\t\t\t        borderWidth:0,\n\t\t\t\t        borderRadius:0,\n\t\t\t\t        stacking:'normal',//No I18N\n\t\t\t\t        grouping:false,        \n\t\t\t\t        color: '#ff9000', //NO I18N\n\t\t\t\t        data:dataModel.attribueValue\n\t\t\t\t     },{\n\t\t\t\t        name:'");
/*  670 */           out.print(FormatUtil.getString("am.webclient.forecast.eighty.percentage.text"));
/*  671 */           out.write("',\t\t\t//No I18N\n\t\t\t\t        type:'scatter',\t\t\t\t//No I18N\n\t\t\t\t        zIndex:200,\n\t\t\t\t        fillColor:'#ff5000',  //NO I18N\n\t\t\t\t        lineColor: '#ff5000',   //NO I18N\n\t\t\t\t        color: '#ff5000', //NO I18N\n\t\t\t\t        data:dataModel.eightyPercentage\n\t\t\t\t    },{\n\t\t\t\t        name:'");
/*  672 */           out.print(FormatUtil.getString("am.webclient.forecast.ninety.percentage.text"));
/*  673 */           out.write("',//No I18N\n\t\t\t\t        type:'scatter',//No I18N\n\t\t\t\t        zIndex:200,\n\t\t\t\t        fillColor:'#ff5000',  //NO I18N\n\t\t\t\t        lineColor: '#ff5000',   //NO I18N\n\t\t\t\t        color: '#ff5000', //NO I18N\n\t\t\t\t        data:dataModel.ninetyPercentage\n\t\t\t\t    } , {\n\t\t\t\t        name:'");
/*  674 */           out.print(FormatUtil.getString("am.webclient.forecast.hundred.percentage.text"));
/*  675 */           out.write("',//No I18N\n\t\t\t\t        type:'scatter',//No I18N\n\t\t\t\t        zIndex:200,  \n\t\t\t\t        fillColor: '#ff0000', //NO I18N\n\t\t\t\t        lineColor: '#ff0000', //NO I18N\n\t\t\t\t        color: '#ff0000', //NO I18N\n\t\t\t\t        data:dataModel.hundredPercentage\n\t\t\t\t    }]  \n\t\t\t\t  });  \n\t\t}\n\tfunction forecastChart(chartDataModel){\n\t\t $('#predictionDataContainer').highcharts({\n\t    \t credits:{enabled:false},\n\t    \t title:{\n\t    \t\t text: '");
/*  676 */           if (_jspx_meth_c_005fout_005f29(_jspx_page_context))
/*      */             return;
/*  678 */           out.write("',\n\n                 style: {\n\n                       font: '14px Verdana',\t\t// NO I18N\n\n                   }\n\t    \t\t },\n\t    \t xAxis: {\n\t             type: 'datetime',\t\t\t// NO I18N\n\t             dateTimeLabelFormats: {\n\t            \t day: '%e %b %y'\t\t// NO I18N\n\t             },\n\t             title: {\n\t                 text: '");
/*  679 */           out.print(FormatUtil.getString("Date"));
/*  680 */           out.write("'\t\t// NO I18N\n\t             }\n\t         },\n\t         yAxis: {\n\t             \n\t             title: {\n\t                 text: \"");
/*  681 */           if (_jspx_meth_c_005fout_005f30(_jspx_page_context))
/*      */             return;
/*  683 */           out.write("\",\n\t             },\n\n\t         },\n\t         series: [{\n\t        \t name: '");
/*  684 */           out.print(FormatUtil.getString("am.webclient.forecast.actualdata.text"));
/*  685 */           out.write("',\n\t        \t color: '#4572A7',\t\t// NO I18N\n\t             data: chartDataModel.dataPoints,\n\t         },{\n\t        \t name: '");
/*  686 */           out.print(FormatUtil.getString("am.webclient.forecast.forecastdata.text"));
/*  687 */           out.write("',\n\t        \t dashStyle: 'dot',\t\t// NO I18N\n\t        \t color: '#4572A7',\t\t// NO I18N\n\t        \t data: chartDataModel.predictionPoints,\n\t         }]\n\t    });\n\t}\n\t\n\tfunction seriesChart(chartDataModel){\n\t\t\n\t\t  var maxValue = '");
/*  688 */           if (_jspx_meth_c_005fout_005f31(_jspx_page_context))
/*      */             return;
/*  690 */           out.write("';\n\t\t  ");
/*  691 */           if (_jspx_meth_c_005fif_005f9(_jspx_page_context))
/*      */             return;
/*  693 */           out.write("\n\t\t  \n\t    if( this.height != null && this.width != null ) {\n\t      $('#container').width(this.width).height(this.height) ;       //No I18N\n\t    }            \n\n\t\t\tseriesChartData = \tnew Highcharts.Chart({\n\n\t\t        chart: {\n\t\t            type: 'column',\t\t// NO I18N\n\t\t            renderTo: 'container',\t\t// NO I18N\n\t\t            marginTop: 30\n\t\t        },\n\t\t        \n\t\t        \n\t\t        credits: {\n\t\t            enabled: false\n\t\t        },\n\n\t\t        title: {\n\t\t            text: '',\n\t\t            style: {\n\t\t            \tdisplay : 'none'\t\t// NO I18N\n\t\t            }\n\t\t        },\n\n\t\t        xAxis: {\n\t\t            categories: chartDataModel.categories\n\t\t        },\n\n\t\t        yAxis: {\n\t\t            allowDecimals: true,\n\t\t            min: 0,\n\t\t            max: maxValue,\n\t\t            title: {\n\t\t                 text: '");
/*  694 */           if (_jspx_meth_c_005fout_005f32(_jspx_page_context))
/*      */             return;
/*  696 */           out.write("',\n\t\t             }\n\t\t        },\n\n\t\t        tooltip: {\n\t\t            headerFormat: '<b>{point.key}</b><br>',\t\t// NO I18N\n\t\t            pointFormat: '<span style=\"color:{series.color}\">\\u25CF</span> {series.name}: {point.y} / {point.stackTotal}'\t\t// NO I18N\n\t\t        },\n\n\t\t        plotOptions: {\n\t\t            column: {\n\t\t                depth: 40\n\t\t            }\n\t\t        },\n\n\t\t        series: chartDataModel.seriesValue\n\t\t    });\n\t\t}\n\t \n\n</script>");
/*      */         }
/*  698 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  699 */         out = _jspx_out;
/*  700 */         if ((out != null) && (out.getBufferSize() != 0))
/*  701 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  702 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  705 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  711 */     PageContext pageContext = _jspx_page_context;
/*  712 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  714 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  715 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  716 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  718 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  720 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  721 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  722 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  723 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  724 */       return true;
/*      */     }
/*  726 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  727 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  732 */     PageContext pageContext = _jspx_page_context;
/*  733 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  735 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  736 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  737 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/*  739 */     _jspx_th_c_005fwhen_005f0.setTest("${heading !='0'}");
/*  740 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  741 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/*  743 */         out.write("\n\t    ");
/*  744 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  745 */           return true;
/*  746 */         out.write("\n     ");
/*  747 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  748 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  752 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  753 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  754 */       return true;
/*      */     }
/*  756 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  757 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  762 */     PageContext pageContext = _jspx_page_context;
/*  763 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  765 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  766 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/*  767 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  769 */     _jspx_th_c_005fout_005f1.setValue("${heading}");
/*  770 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/*  771 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/*  772 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  773 */       return true;
/*      */     }
/*  775 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  776 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  781 */     PageContext pageContext = _jspx_page_context;
/*  782 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  784 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  785 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/*  786 */     _jspx_th_c_005fforEach_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/*  788 */     _jspx_th_c_005fforEach_005f0.setVar("row");
/*      */     
/*  790 */     _jspx_th_c_005fforEach_005f0.setItems("${attributeDetails}");
/*      */     
/*  792 */     _jspx_th_c_005fforEach_005f0.setVarStatus("i");
/*  793 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/*  795 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/*  796 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/*  798 */           out.write("\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"40%\" class=\"whitegrayrightalign\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*  799 */           boolean bool; if (_jspx_meth_c_005fchoose_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  800 */             return true;
/*  801 */           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" class=\"whitegrayrightalign\">");
/*  802 */           if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  803 */             return true;
/*  804 */           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" class=\"whitegrayrightalign\">");
/*  805 */           if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  806 */             return true;
/*  807 */           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" class=\"whitegrayrightalign\">");
/*  808 */           if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  809 */             return true;
/*  810 */           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"15%\" class=\"whitegrayrightalign\">");
/*  811 */           if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  812 */             return true;
/*  813 */           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t");
/*  814 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/*  815 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  819 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*  820 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/*  823 */         int tmp361_360 = 0; int[] tmp361_358 = _jspx_push_body_count_c_005fforEach_005f0; int tmp363_362 = tmp361_358[tmp361_360];tmp361_358[tmp361_360] = (tmp363_362 - 1); if (tmp363_362 <= 0) break;
/*  824 */         out = _jspx_page_context.popBody(); }
/*  825 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/*  827 */       _jspx_th_c_005fforEach_005f0.doFinally();
/*  828 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/*  830 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  835 */     PageContext pageContext = _jspx_page_context;
/*  836 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  838 */     ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  839 */     _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/*  840 */     _jspx_th_c_005fchoose_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*  841 */     int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/*  842 */     if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */       for (;;) {
/*  844 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*  845 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  846 */           return true;
/*  847 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*  848 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  849 */           return true;
/*  850 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*  851 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/*  852 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  856 */     if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/*  857 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  858 */       return true;
/*      */     }
/*  860 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*  861 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  866 */     PageContext pageContext = _jspx_page_context;
/*  867 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  869 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  870 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/*  871 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/*  873 */     _jspx_th_c_005fwhen_005f2.setTest("${individualReport }");
/*  874 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/*  875 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/*  877 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*  878 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fwhen_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  879 */           return true;
/*  880 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*  881 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/*  882 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  886 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/*  887 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  888 */       return true;
/*      */     }
/*  890 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*  891 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  896 */     PageContext pageContext = _jspx_page_context;
/*  897 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  899 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  900 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/*  901 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/*  903 */     _jspx_th_c_005fout_005f2.setValue("${row.value.displayname}");
/*  904 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/*  905 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/*  906 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  907 */       return true;
/*      */     }
/*  909 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  910 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  915 */     PageContext pageContext = _jspx_page_context;
/*  916 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  918 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  919 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/*  920 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*  921 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/*  922 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/*  924 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"javascript:forecastDetails('");
/*  925 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  926 */           return true;
/*  927 */         out.write(39);
/*  928 */         out.write(44);
/*  929 */         out.write(39);
/*  930 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  931 */           return true;
/*  932 */         out.write("')\">");
/*  933 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  934 */           return true;
/*  935 */         out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*  936 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/*  937 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  941 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/*  942 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  943 */       return true;
/*      */     }
/*  945 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/*  946 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  951 */     PageContext pageContext = _jspx_page_context;
/*  952 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  954 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  955 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/*  956 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/*  958 */     _jspx_th_c_005fout_005f3.setValue("${row.key}");
/*  959 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/*  960 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/*  961 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  962 */       return true;
/*      */     }
/*  964 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  965 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  970 */     PageContext pageContext = _jspx_page_context;
/*  971 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  973 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  974 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/*  975 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/*  977 */     _jspx_th_c_005fout_005f4.setValue("${attributeid }");
/*  978 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/*  979 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/*  980 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/*  981 */       return true;
/*      */     }
/*  983 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/*  984 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  989 */     PageContext pageContext = _jspx_page_context;
/*  990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  992 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  993 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/*  994 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/*  996 */     _jspx_th_c_005fout_005f5.setValue("${row.value.displayname}");
/*  997 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/*  998 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/*  999 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1000 */       return true;
/*      */     }
/* 1002 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1003 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1008 */     PageContext pageContext = _jspx_page_context;
/* 1009 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1011 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1012 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1013 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1015 */     _jspx_th_c_005fout_005f6.setValue("${row.value.value}");
/* 1016 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1017 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1018 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1019 */       return true;
/*      */     }
/* 1021 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1027 */     PageContext pageContext = _jspx_page_context;
/* 1028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1030 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1031 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1032 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1034 */     _jspx_th_c_005fout_005f7.setValue("${row.value.eightyPercentage}");
/* 1035 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1036 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1037 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1038 */       return true;
/*      */     }
/* 1040 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1041 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1046 */     PageContext pageContext = _jspx_page_context;
/* 1047 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1049 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1050 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 1051 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1053 */     _jspx_th_c_005fout_005f8.setValue("${row.value.ninetyPercentage}");
/* 1054 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 1055 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 1056 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1057 */       return true;
/*      */     }
/* 1059 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1060 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1065 */     PageContext pageContext = _jspx_page_context;
/* 1066 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1068 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1069 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 1070 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1072 */     _jspx_th_c_005fout_005f9.setValue("${row.value.hundredPercentage}");
/* 1073 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 1074 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 1075 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1076 */       return true;
/*      */     }
/* 1078 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1079 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1084 */     PageContext pageContext = _jspx_page_context;
/* 1085 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1087 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1088 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 1089 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1091 */     _jspx_th_c_005fforEach_005f1.setVar("row");
/*      */     
/* 1093 */     _jspx_th_c_005fforEach_005f1.setItems("${dataTrends}");
/*      */     
/* 1095 */     _jspx_th_c_005fforEach_005f1.setVarStatus("i");
/* 1096 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 1098 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 1099 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 1101 */           out.write("\n\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign\">");
/* 1102 */           boolean bool; if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1103 */             return true;
/* 1104 */           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign\">");
/* 1105 */           if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1106 */             return true;
/* 1107 */           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"whitegrayrightalign\">");
/* 1108 */           if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 1109 */             return true;
/* 1110 */           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t");
/* 1111 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 1112 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1116 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 1117 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1120 */         int tmp280_279 = 0; int[] tmp280_277 = _jspx_push_body_count_c_005fforEach_005f1; int tmp282_281 = tmp280_277[tmp280_279];tmp280_277[tmp280_279] = (tmp282_281 - 1); if (tmp282_281 <= 0) break;
/* 1121 */         out = _jspx_page_context.popBody(); }
/* 1122 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 1124 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 1125 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 1127 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1132 */     PageContext pageContext = _jspx_page_context;
/* 1133 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1135 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1136 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 1137 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1139 */     _jspx_th_c_005fout_005f10.setValue("${row[\"time\"]}");
/* 1140 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 1141 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 1142 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1143 */       return true;
/*      */     }
/* 1145 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1146 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1151 */     PageContext pageContext = _jspx_page_context;
/* 1152 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1154 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1155 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 1156 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1158 */     _jspx_th_c_005fout_005f11.setValue("${row[\"actualvalue\"]}");
/* 1159 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 1160 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 1161 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1162 */       return true;
/*      */     }
/* 1164 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 1165 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 1170 */     PageContext pageContext = _jspx_page_context;
/* 1171 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1173 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1174 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 1175 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 1177 */     _jspx_th_c_005fout_005f12.setValue("${row[\"predictedvalue\"]}");
/* 1178 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 1179 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 1180 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1181 */       return true;
/*      */     }
/* 1183 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 1184 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1189 */     PageContext pageContext = _jspx_page_context;
/* 1190 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1192 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1193 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 1194 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1196 */     _jspx_th_c_005fout_005f13.setValue("${attributeid }");
/* 1197 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 1198 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 1199 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 1200 */       return true;
/*      */     }
/* 1202 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 1203 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1208 */     PageContext pageContext = _jspx_page_context;
/* 1209 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1211 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1212 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 1213 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1215 */     _jspx_th_c_005fout_005f14.setValue("${growthTrendValue}");
/* 1216 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 1217 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 1218 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 1219 */       return true;
/*      */     }
/* 1221 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 1222 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1227 */     PageContext pageContext = _jspx_page_context;
/* 1228 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1230 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1231 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 1232 */     _jspx_th_c_005fchoose_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 1233 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 1234 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */       for (;;) {
/* 1236 */         out.write(32);
/* 1237 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 1238 */           return true;
/* 1239 */         out.write(32);
/* 1240 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 1241 */           return true;
/* 1242 */         out.write(32);
/* 1243 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 1244 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1248 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 1249 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1250 */       return true;
/*      */     }
/* 1252 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 1253 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1258 */     PageContext pageContext = _jspx_page_context;
/* 1259 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1261 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1262 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 1263 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*      */     
/* 1265 */     _jspx_th_c_005fwhen_005f3.setTest("${requestScope.ReportForm.startDate != ''}");
/* 1266 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 1267 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */       for (;;) {
/* 1269 */         out.write(32);
/* 1270 */         out.write(" \n\t\t\t      \t\t");
/* 1271 */         if (_jspx_meth_html_005ftext_005f0(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 1272 */           return true;
/* 1273 */         out.write(" \n\t\t\t      \t\t");
/* 1274 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 1275 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1279 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 1280 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1281 */       return true;
/*      */     }
/* 1283 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 1284 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1289 */     PageContext pageContext = _jspx_page_context;
/* 1290 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1292 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 1293 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 1294 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 1296 */     _jspx_th_html_005ftext_005f0.setSize("12");
/*      */     
/* 1298 */     _jspx_th_html_005ftext_005f0.setProperty("startDate");
/*      */     
/* 1300 */     _jspx_th_html_005ftext_005f0.setStyleId("start");
/*      */     
/* 1302 */     _jspx_th_html_005ftext_005f0.setReadonly(true);
/*      */     
/* 1304 */     _jspx_th_html_005ftext_005f0.setOnchange("javascript:fnSetStartTime(this.value)");
/*      */     
/* 1306 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 1307 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 1308 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 1309 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 1310 */       return true;
/*      */     }
/* 1312 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 1313 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1318 */     PageContext pageContext = _jspx_page_context;
/* 1319 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1321 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1322 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 1323 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 1324 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 1325 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 1327 */         out.write(32);
/* 1328 */         if (_jspx_meth_html_005ftext_005f1(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/* 1329 */           return true;
/* 1330 */         out.write(" \n\t\t\t      \t\t");
/* 1331 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 1332 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1336 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 1337 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1338 */       return true;
/*      */     }
/* 1340 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 1341 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1346 */     PageContext pageContext = _jspx_page_context;
/* 1347 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1349 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 1350 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 1351 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 1353 */     _jspx_th_html_005ftext_005f1.setSize("12");
/*      */     
/* 1355 */     _jspx_th_html_005ftext_005f1.setProperty("startDate");
/*      */     
/* 1357 */     _jspx_th_html_005ftext_005f1.setStyleId("start");
/*      */     
/* 1359 */     _jspx_th_html_005ftext_005f1.setReadonly(true);
/*      */     
/* 1361 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:fnSetStartTime(this.value)");
/*      */     
/* 1363 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/* 1364 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 1365 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 1366 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1367 */       return true;
/*      */     }
/* 1369 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1370 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1375 */     PageContext pageContext = _jspx_page_context;
/* 1376 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1378 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1379 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 1380 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/* 1381 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 1382 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 1384 */         out.write(32);
/* 1385 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 1386 */           return true;
/* 1387 */         out.write(32);
/* 1388 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context))
/* 1389 */           return true;
/* 1390 */         out.write(32);
/* 1391 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 1392 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1396 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 1397 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1398 */       return true;
/*      */     }
/* 1400 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 1401 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1406 */     PageContext pageContext = _jspx_page_context;
/* 1407 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1409 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1410 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 1411 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 1413 */     _jspx_th_c_005fwhen_005f4.setTest("${requestScope.ReportForm.endDate != ''}");
/* 1414 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 1415 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 1417 */         out.write(" \n\t\t\t\t\t      ");
/* 1418 */         if (_jspx_meth_html_005ftext_005f2(_jspx_th_c_005fwhen_005f4, _jspx_page_context))
/* 1419 */           return true;
/* 1420 */         out.write(" \n\t\t\t\t\t      ");
/* 1421 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 1422 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1426 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 1427 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1428 */       return true;
/*      */     }
/* 1430 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 1431 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1436 */     PageContext pageContext = _jspx_page_context;
/* 1437 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1439 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 1440 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 1441 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 1443 */     _jspx_th_html_005ftext_005f2.setProperty("endDate");
/*      */     
/* 1445 */     _jspx_th_html_005ftext_005f2.setSize("12");
/*      */     
/* 1447 */     _jspx_th_html_005ftext_005f2.setStyleId("end");
/*      */     
/* 1449 */     _jspx_th_html_005ftext_005f2.setReadonly(true);
/*      */     
/* 1451 */     _jspx_th_html_005ftext_005f2.setOnchange("javascript:fnSetEndTime(this.value)");
/*      */     
/* 1453 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/* 1454 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 1455 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 1456 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 1457 */       return true;
/*      */     }
/* 1459 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 1460 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1465 */     PageContext pageContext = _jspx_page_context;
/* 1466 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1468 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1469 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 1470 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 1471 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 1472 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 1474 */         out.write(32);
/* 1475 */         if (_jspx_meth_html_005ftext_005f3(_jspx_th_c_005fotherwise_005f4, _jspx_page_context))
/* 1476 */           return true;
/* 1477 */         out.write(" \n\t\t\t\t\t      ");
/* 1478 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 1479 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1483 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 1484 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1485 */       return true;
/*      */     }
/* 1487 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 1488 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1493 */     PageContext pageContext = _jspx_page_context;
/* 1494 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1496 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 1497 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 1498 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 1500 */     _jspx_th_html_005ftext_005f3.setProperty("endDate");
/*      */     
/* 1502 */     _jspx_th_html_005ftext_005f3.setSize("12");
/*      */     
/* 1504 */     _jspx_th_html_005ftext_005f3.setStyleId("end");
/*      */     
/* 1506 */     _jspx_th_html_005ftext_005f3.setReadonly(true);
/*      */     
/* 1508 */     _jspx_th_html_005ftext_005f3.setOnchange("javascript:fnSetEndTime(this.value)");
/*      */     
/* 1510 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/* 1511 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 1512 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 1513 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 1514 */       return true;
/*      */     }
/* 1516 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 1517 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1522 */     PageContext pageContext = _jspx_page_context;
/* 1523 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1525 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1526 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 1527 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1529 */     _jspx_th_c_005fout_005f15.setValue("${fromDate}");
/* 1530 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 1531 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 1532 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 1533 */       return true;
/*      */     }
/* 1535 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 1536 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1541 */     PageContext pageContext = _jspx_page_context;
/* 1542 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1544 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1545 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 1546 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1548 */     _jspx_th_c_005fout_005f16.setValue("${toDate}");
/* 1549 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 1550 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 1551 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 1552 */       return true;
/*      */     }
/* 1554 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 1555 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1560 */     PageContext pageContext = _jspx_page_context;
/* 1561 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1563 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1564 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 1565 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1567 */     _jspx_th_c_005fout_005f17.setValue("${futureDate}");
/* 1568 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 1569 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 1570 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 1571 */       return true;
/*      */     }
/* 1573 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 1574 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1579 */     PageContext pageContext = _jspx_page_context;
/* 1580 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1582 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 1583 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 1584 */     _jspx_th_c_005fforEach_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1586 */     _jspx_th_c_005fforEach_005f2.setVar("row");
/*      */     
/* 1588 */     _jspx_th_c_005fforEach_005f2.setItems("${attributeDetails}");
/*      */     
/* 1590 */     _jspx_th_c_005fforEach_005f2.setVarStatus("i");
/* 1591 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */     try {
/* 1593 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 1594 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */         for (;;) {
/* 1596 */           out.write("\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td width=\"40%\" class=\"whitegrayrightalign\">\n\t\t\t\t\t\t\t\t\t\t");
/* 1597 */           boolean bool; if (_jspx_meth_c_005fchoose_005f5(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1598 */             return true;
/* 1599 */           out.write("\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign\">");
/* 1600 */           if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1601 */             return true;
/* 1602 */           out.write("</td>\n\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign\">");
/* 1603 */           if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1604 */             return true;
/* 1605 */           out.write("</td>\n\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign\">");
/* 1606 */           if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1607 */             return true;
/* 1608 */           out.write("</td>\n\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign\">");
/* 1609 */           if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1610 */             return true;
/* 1611 */           out.write("</td>\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t");
/* 1612 */           if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1613 */             return true;
/* 1614 */           out.write("\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t");
/* 1615 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 1616 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 1620 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 1621 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 1624 */         int tmp401_400 = 0; int[] tmp401_398 = _jspx_push_body_count_c_005fforEach_005f2; int tmp403_402 = tmp401_398[tmp401_400];tmp401_398[tmp401_400] = (tmp403_402 - 1); if (tmp403_402 <= 0) break;
/* 1625 */         out = _jspx_page_context.popBody(); }
/* 1626 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */     } finally {
/* 1628 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 1629 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */     }
/* 1631 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f5(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1636 */     PageContext pageContext = _jspx_page_context;
/* 1637 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1639 */     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1640 */     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 1641 */     _jspx_th_c_005fchoose_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/* 1642 */     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 1643 */     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */       for (;;) {
/* 1645 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 1646 */         if (_jspx_meth_c_005fwhen_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1647 */           return true;
/* 1648 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 1649 */         if (_jspx_meth_c_005fotherwise_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1650 */           return true;
/* 1651 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 1652 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 1653 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1657 */     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 1658 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 1659 */       return true;
/*      */     }
/* 1661 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 1662 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1667 */     PageContext pageContext = _jspx_page_context;
/* 1668 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1670 */     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1671 */     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 1672 */     _jspx_th_c_005fwhen_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/*      */     
/* 1674 */     _jspx_th_c_005fwhen_005f5.setTest("${individualReport }");
/* 1675 */     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 1676 */     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */       for (;;) {
/* 1678 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 1679 */         if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1680 */           return true;
/* 1681 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 1682 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 1683 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1687 */     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 1688 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1689 */       return true;
/*      */     }
/* 1691 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 1692 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1697 */     PageContext pageContext = _jspx_page_context;
/* 1698 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1700 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1701 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 1702 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 1704 */     _jspx_th_c_005fout_005f18.setValue("${row.value.displayname}");
/* 1705 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 1706 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 1707 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 1708 */       return true;
/*      */     }
/* 1710 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 1711 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1716 */     PageContext pageContext = _jspx_page_context;
/* 1717 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1719 */     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1720 */     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 1721 */     _jspx_th_c_005fotherwise_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 1722 */     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 1723 */     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */       for (;;) {
/* 1725 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"javascript:forecastDetails('");
/* 1726 */         if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fotherwise_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1727 */           return true;
/* 1728 */         out.write(39);
/* 1729 */         out.write(44);
/* 1730 */         out.write(39);
/* 1731 */         if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fotherwise_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1732 */           return true;
/* 1733 */         out.write("')\">");
/* 1734 */         if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fotherwise_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1735 */           return true;
/* 1736 */         out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t");
/* 1737 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 1738 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1742 */     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 1743 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 1744 */       return true;
/*      */     }
/* 1746 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 1747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1752 */     PageContext pageContext = _jspx_page_context;
/* 1753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1755 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1756 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 1757 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 1759 */     _jspx_th_c_005fout_005f19.setValue("${row.key}");
/* 1760 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 1761 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 1762 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 1763 */       return true;
/*      */     }
/* 1765 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 1766 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1771 */     PageContext pageContext = _jspx_page_context;
/* 1772 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1774 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1775 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 1776 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 1778 */     _jspx_th_c_005fout_005f20.setValue("${attributeid }");
/* 1779 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 1780 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 1781 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 1782 */       return true;
/*      */     }
/* 1784 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 1785 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1790 */     PageContext pageContext = _jspx_page_context;
/* 1791 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1793 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1794 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 1795 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 1797 */     _jspx_th_c_005fout_005f21.setValue("${row.value.displayname}");
/* 1798 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 1799 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 1800 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 1801 */       return true;
/*      */     }
/* 1803 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 1804 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1809 */     PageContext pageContext = _jspx_page_context;
/* 1810 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1812 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1813 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 1814 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 1816 */     _jspx_th_c_005fout_005f22.setValue("${row.value.value}");
/* 1817 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 1818 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 1819 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 1820 */       return true;
/*      */     }
/* 1822 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 1823 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1828 */     PageContext pageContext = _jspx_page_context;
/* 1829 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1831 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1832 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 1833 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 1835 */     _jspx_th_c_005fout_005f23.setValue("${row.value.pastValue}");
/* 1836 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 1837 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 1838 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 1839 */       return true;
/*      */     }
/* 1841 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 1842 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1847 */     PageContext pageContext = _jspx_page_context;
/* 1848 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1850 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1851 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 1852 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 1854 */     _jspx_th_c_005fout_005f24.setValue("${row.value.presentValue}");
/* 1855 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 1856 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 1857 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 1858 */       return true;
/*      */     }
/* 1860 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 1861 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1866 */     PageContext pageContext = _jspx_page_context;
/* 1867 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1869 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1870 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 1871 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 1873 */     _jspx_th_c_005fout_005f25.setValue("${row.value.futureValue}");
/* 1874 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 1875 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 1876 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 1877 */       return true;
/*      */     }
/* 1879 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 1880 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1885 */     PageContext pageContext = _jspx_page_context;
/* 1886 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1888 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1889 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 1890 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 1892 */     _jspx_th_c_005fif_005f4.setTest("${percentageReport }");
/* 1893 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 1894 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 1896 */         out.write("\n\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign\">");
/* 1897 */         if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fif_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1898 */           return true;
/* 1899 */         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"whitegrayrightalign\">");
/* 1900 */         if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fif_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 1901 */           return true;
/* 1902 */         out.write("</td>\n\t\t\t\t\t\t\t\t\t");
/* 1903 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 1904 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1908 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 1909 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 1910 */       return true;
/*      */     }
/* 1912 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 1913 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1918 */     PageContext pageContext = _jspx_page_context;
/* 1919 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1921 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1922 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 1923 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 1925 */     _jspx_th_c_005fout_005f26.setValue("${row.value.ninetyPercentage}");
/* 1926 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 1927 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 1928 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 1929 */       return true;
/*      */     }
/* 1931 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 1932 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 1937 */     PageContext pageContext = _jspx_page_context;
/* 1938 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1940 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1941 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 1942 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 1944 */     _jspx_th_c_005fout_005f27.setValue("${row.value.hundredPercentage}");
/* 1945 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 1946 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 1947 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 1948 */       return true;
/*      */     }
/* 1950 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 1951 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1956 */     PageContext pageContext = _jspx_page_context;
/* 1957 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1959 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1960 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 1961 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 1963 */     _jspx_th_c_005fif_005f5.setTest("${nodata}");
/* 1964 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 1965 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 1967 */         out.write("\n\t\t\t\t<br>\n\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\">\n  \t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"6%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"23\" height=\"23\"></td>\n      \t\t\t\t\t<td width=\"94%\" height=\"34\"  class=\"message\">\n      \t\t\t\t\t\t");
/* 1968 */         if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 1969 */           return true;
/* 1970 */         out.write("\n      \t\t\t\t\t</td>\n    \t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t");
/* 1971 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 1972 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1976 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 1977 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1978 */       return true;
/*      */     }
/* 1980 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 1981 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1986 */     PageContext pageContext = _jspx_page_context;
/* 1987 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1989 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1990 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 1991 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 1993 */     _jspx_th_c_005fout_005f28.setValue("${emptymessage}");
/* 1994 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 1995 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 1996 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 1997 */       return true;
/*      */     }
/* 1999 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 2000 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2005 */     PageContext pageContext = _jspx_page_context;
/* 2006 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2008 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2009 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2010 */     _jspx_th_c_005fif_005f6.setParent(null);
/*      */     
/* 2012 */     _jspx_th_c_005fif_005f6.setTest("${!individualReport}");
/* 2013 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2014 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 2016 */         out.write("\n\t\tseriesChart(chartDataModel);\n\t");
/* 2017 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2018 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2022 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2023 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2024 */       return true;
/*      */     }
/* 2026 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2027 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2032 */     PageContext pageContext = _jspx_page_context;
/* 2033 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2035 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2036 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2037 */     _jspx_th_c_005fif_005f7.setParent(null);
/*      */     
/* 2039 */     _jspx_th_c_005fif_005f7.setTest("${individualReport}");
/* 2040 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2041 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 2043 */         out.write("\n\t\tforecastChart(chartDataModel);\n\t");
/* 2044 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2045 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2049 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2050 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2051 */       return true;
/*      */     }
/* 2053 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2054 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2059 */     PageContext pageContext = _jspx_page_context;
/* 2060 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2062 */     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2063 */     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 2064 */     _jspx_th_c_005fif_005f8.setParent(null);
/*      */     
/* 2066 */     _jspx_th_c_005fif_005f8.setTest("${percentageReport }");
/* 2067 */     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 2068 */     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */       for (;;) {
/* 2070 */         out.write("\n\t\tfullPercentage(dataModel)\n  \t");
/* 2071 */         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 2072 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2076 */     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 2077 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2078 */       return true;
/*      */     }
/* 2080 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 2081 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2086 */     PageContext pageContext = _jspx_page_context;
/* 2087 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2089 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2090 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 2091 */     _jspx_th_c_005fout_005f29.setParent(null);
/*      */     
/* 2093 */     _jspx_th_c_005fout_005f29.setValue("${monitorName}");
/* 2094 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 2095 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 2096 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 2097 */       return true;
/*      */     }
/* 2099 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 2100 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2105 */     PageContext pageContext = _jspx_page_context;
/* 2106 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2108 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2109 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 2110 */     _jspx_th_c_005fout_005f30.setParent(null);
/*      */     
/* 2112 */     _jspx_th_c_005fout_005f30.setValue("${attributeUnit}");
/* 2113 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 2114 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 2115 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 2116 */       return true;
/*      */     }
/* 2118 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 2119 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2124 */     PageContext pageContext = _jspx_page_context;
/* 2125 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2127 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2128 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 2129 */     _jspx_th_c_005fout_005f31.setParent(null);
/*      */     
/* 2131 */     _jspx_th_c_005fout_005f31.setValue("${maxValue}");
/* 2132 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 2133 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 2134 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 2135 */       return true;
/*      */     }
/* 2137 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 2138 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2143 */     PageContext pageContext = _jspx_page_context;
/* 2144 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2146 */     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2147 */     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 2148 */     _jspx_th_c_005fif_005f9.setParent(null);
/*      */     
/* 2150 */     _jspx_th_c_005fif_005f9.setTest("${percentageReport }");
/* 2151 */     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 2152 */     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */       for (;;) {
/* 2154 */         out.write("\n\t\t  \tmaxValue = 100;\n\t\t  ");
/* 2155 */         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 2156 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2160 */     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 2161 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2162 */       return true;
/*      */     }
/* 2164 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 2165 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2170 */     PageContext pageContext = _jspx_page_context;
/* 2171 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2173 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2174 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 2175 */     _jspx_th_c_005fout_005f32.setParent(null);
/*      */     
/* 2177 */     _jspx_th_c_005fout_005f32.setValue("${attributeUnit}");
/* 2178 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 2179 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 2180 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 2181 */       return true;
/*      */     }
/* 2183 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 2184 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\ForecastReport_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */