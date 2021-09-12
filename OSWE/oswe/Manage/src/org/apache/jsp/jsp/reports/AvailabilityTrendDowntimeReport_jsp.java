/*     */ package org.apache.jsp.jsp.reports;
/*     */ 
/*     */ import com.adventnet.appmanager.reporting.dataproducer.ReportGraphs;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.tags.BarChart;
/*     */ import com.adventnet.awolf.tags.TimeChart;
/*     */ import java.awt.Color;
/*     */ import java.awt.Paint;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ 
/*     */ public final class AvailabilityTrendDowntimeReport_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  30 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fskipLabelRotation_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  42 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  46 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fskipLabelRotation_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  49 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  53 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.release();
/*  54 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fskipLabelRotation_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
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
/*  76 */       ServletConfig config = pageContext.getServletConfig();
/*  77 */       session = pageContext.getSession();
/*  78 */       out = pageContext.getOut();
/*  79 */       _jspx_out = out;
/*     */       
/*  81 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/*  82 */       ReportGraphs reportGraph = null;
/*  83 */       reportGraph = (ReportGraphs)_jspx_page_context.getAttribute("reportGraph", 1);
/*  84 */       if (reportGraph == null) {
/*  85 */         reportGraph = new ReportGraphs();
/*  86 */         _jspx_page_context.setAttribute("reportGraph", reportGraph, 1);
/*     */       }
/*  88 */       out.write("\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n</head>\n\n");
/*     */       
/*  90 */       ArrayList Values = (ArrayList)request.getAttribute("allvalues");
/*     */       
/*  92 */       String period = (String)request.getAttribute("timeperiod");
/*  93 */       String interval = request.getParameter("interval");
/*  94 */       if ((Values != null) && (Values.size() > 0)) {
/*     */         try {
/*  96 */           Map params = new HashMap();
/*  97 */           params.put("type", "TRENDAVAILABILITYLINE");
/*  98 */           params.put("period", period);
/*  99 */           params.put("data", Values);
/* 100 */           params.put("id", request.getParameter("haid"));
/* 101 */           params.put("interval", request.getParameter("interval"));
/* 102 */           reportGraph.setParams(params);
/*     */           
/* 104 */           out.write("\n\n<table width=\"49%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n<tr> \n<td width=\"49%\" align=\"center\" class=\"bodytextbold\"> ");
/* 105 */           out.print(FormatUtil.getString("am.reporting.admin.summarymail.coloumn.availibility"));
/* 106 */           out.write(" </td> \n</tr>\n</table>\n<table width=\"49%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n \n<tr> <td width=\"99%\" align=\"center\" >\n\t");
/*     */           
/* 108 */           TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/* 109 */           _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 110 */           _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*     */           
/* 112 */           _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("reportGraph");
/*     */           
/* 114 */           _jspx_th_awolf_005ftimechart_005f0.setWidth("800");
/*     */           
/* 116 */           _jspx_th_awolf_005ftimechart_005f0.setHeight("250");
/*     */           
/* 118 */           _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*     */           
/* 120 */           _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.reporting.timeperiods.name"));
/*     */           
/* 122 */           _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.reports.excel.trendreport.availability.text"));
/*     */           
/* 124 */           _jspx_th_awolf_005ftimechart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*     */           
/* 126 */           _jspx_th_awolf_005ftimechart_005f0.setCustomDateAxis(true);
/*     */           
/* 128 */           _jspx_th_awolf_005ftimechart_005f0.setCustomAngle(270.0D);
/* 129 */           int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 130 */           if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 131 */             if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 132 */               out = _jspx_page_context.pushBody();
/* 133 */               _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 134 */               _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*     */             }
/*     */             for (;;) {
/* 137 */               out.write(" \n    ");
/* 138 */               int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 139 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 142 */             if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 143 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 146 */           if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 147 */             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*     */           }
/*     */           
/* 150 */           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 151 */           out.write(32);
/* 152 */           out.write(32);
/* 153 */           out.write(" \n\t\n</td> </tr>\n</table>\n<br>\n\n");
/*     */           
/* 155 */           params.put("type", "TRENDDOWNCOUNTREPORT");
/* 156 */           params.put("period", period);
/* 157 */           params.put("data", Values);
/* 158 */           params.put("id", request.getParameter("haid"));
/* 159 */           params.put("interval", request.getParameter("interval"));
/* 160 */           reportGraph.setParams(params);
/*     */           
/* 162 */           out.write("\n<table width=\"49%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n<tr> \n<td width=\"99%\" align=\"center\" >\n\t");
/*     */           
/* 164 */           BarChart _jspx_th_awolf_005fbarchart_005f0 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fskipLabelRotation_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor.get(BarChart.class);
/* 165 */           _jspx_th_awolf_005fbarchart_005f0.setPageContext(_jspx_page_context);
/* 166 */           _jspx_th_awolf_005fbarchart_005f0.setParent(null);
/*     */           
/* 168 */           _jspx_th_awolf_005fbarchart_005f0.setDataSetProducer("reportGraph");
/*     */           
/* 170 */           _jspx_th_awolf_005fbarchart_005f0.setWidth("800");
/*     */           
/* 172 */           _jspx_th_awolf_005fbarchart_005f0.setHeight("250");
/*     */           
/* 174 */           _jspx_th_awolf_005fbarchart_005f0.setLegend("false");
/*     */           
/* 176 */           _jspx_th_awolf_005fbarchart_005f0.setUrl(false);
/*     */           
/* 178 */           _jspx_th_awolf_005fbarchart_005f0.setXaxisLabel(FormatUtil.getString("am.reporting.timeperiods.name"));
/*     */           
/* 180 */           _jspx_th_awolf_005fbarchart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.reports.excel.trendreport.downtimecount.text"));
/*     */           
/* 182 */           _jspx_th_awolf_005fbarchart_005f0.setChartTitle(FormatUtil.getString("am.webclient.reports.excel.trendreport.downtimecount.text"));
/*     */           
/* 184 */           _jspx_th_awolf_005fbarchart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*     */           
/* 186 */           _jspx_th_awolf_005fbarchart_005f0.setMaxBarWidth(0.07D);
/*     */           
/* 188 */           _jspx_th_awolf_005fbarchart_005f0.setBarcolor(new Paint[] { new Color(135, 206, 250) });
/*     */           
/* 190 */           _jspx_th_awolf_005fbarchart_005f0.setLabelRotation(false);
/*     */           
/* 192 */           _jspx_th_awolf_005fbarchart_005f0.setBaseItemLabel(false);
/*     */           
/* 194 */           _jspx_th_awolf_005fbarchart_005f0.setTwoDimensionBar(true);
/*     */           
/* 196 */           _jspx_th_awolf_005fbarchart_005f0.setSkipLabelRotation(true);
/* 197 */           int _jspx_eval_awolf_005fbarchart_005f0 = _jspx_th_awolf_005fbarchart_005f0.doStartTag();
/* 198 */           if (_jspx_eval_awolf_005fbarchart_005f0 != 0) {
/* 199 */             if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 200 */               out = _jspx_page_context.pushBody();
/* 201 */               _jspx_th_awolf_005fbarchart_005f0.setBodyContent((BodyContent)out);
/* 202 */               _jspx_th_awolf_005fbarchart_005f0.doInitBody();
/*     */             }
/*     */             for (;;) {
/* 205 */               out.write(10);
/* 206 */               out.write(9);
/* 207 */               int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f0.doAfterBody();
/* 208 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 211 */             if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 212 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 215 */           if (_jspx_th_awolf_005fbarchart_005f0.doEndTag() == 5) {
/* 216 */             this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fskipLabelRotation_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor.reuse(_jspx_th_awolf_005fbarchart_005f0); return;
/*     */           }
/*     */           
/* 219 */           this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fskipLabelRotation_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor.reuse(_jspx_th_awolf_005fbarchart_005f0);
/* 220 */           out.write(9);
/* 221 */           out.write(32);
/* 222 */           out.write(" \n</td> </tr>\n</table>\n<br>\n\n");
/*     */           
/* 224 */           params.put("type", "TRENDDOWNTIMEREPORT");
/* 225 */           params.put("period", period);
/* 226 */           params.put("data", Values);
/* 227 */           params.put("id", request.getParameter("haid"));
/* 228 */           params.put("interval", request.getParameter("interval"));
/* 229 */           reportGraph.setParams(params);
/*     */           
/* 231 */           out.write("\n<table width=\"49%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n<tr> \n<td width=\"99%\" align=\"center\" >\n\t");
/*     */           
/* 233 */           BarChart _jspx_th_awolf_005fbarchart_005f1 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fskipLabelRotation_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor.get(BarChart.class);
/* 234 */           _jspx_th_awolf_005fbarchart_005f1.setPageContext(_jspx_page_context);
/* 235 */           _jspx_th_awolf_005fbarchart_005f1.setParent(null);
/*     */           
/* 237 */           _jspx_th_awolf_005fbarchart_005f1.setDataSetProducer("reportGraph");
/*     */           
/* 239 */           _jspx_th_awolf_005fbarchart_005f1.setWidth("800");
/*     */           
/* 241 */           _jspx_th_awolf_005fbarchart_005f1.setHeight("250");
/*     */           
/* 243 */           _jspx_th_awolf_005fbarchart_005f1.setLegend("false");
/*     */           
/* 245 */           _jspx_th_awolf_005fbarchart_005f1.setUrl(false);
/*     */           
/* 247 */           _jspx_th_awolf_005fbarchart_005f1.setXaxisLabel(FormatUtil.getString("am.reporting.timeperiods.name"));
/*     */           
/* 249 */           _jspx_th_awolf_005fbarchart_005f1.setYaxisLabel(FormatUtil.getString("am.webclient.reports.availabiityTrendDowntimeReport.downtimetext"));
/*     */           
/* 251 */           _jspx_th_awolf_005fbarchart_005f1.setChartTitle(FormatUtil.getString("am.webclient.historydata.totaldown.text"));
/*     */           
/* 253 */           _jspx_th_awolf_005fbarchart_005f1.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*     */           
/* 255 */           _jspx_th_awolf_005fbarchart_005f1.setMaxBarWidth(0.07D);
/*     */           
/* 257 */           _jspx_th_awolf_005fbarchart_005f1.setBarcolor(new Paint[] { new Color(135, 206, 250) });
/*     */           
/* 259 */           _jspx_th_awolf_005fbarchart_005f1.setLabelRotation(false);
/*     */           
/* 261 */           _jspx_th_awolf_005fbarchart_005f1.setBaseItemLabel(false);
/*     */           
/* 263 */           _jspx_th_awolf_005fbarchart_005f1.setTwoDimensionBar(true);
/*     */           
/* 265 */           _jspx_th_awolf_005fbarchart_005f1.setSkipLabelRotation(true);
/* 266 */           int _jspx_eval_awolf_005fbarchart_005f1 = _jspx_th_awolf_005fbarchart_005f1.doStartTag();
/* 267 */           if (_jspx_eval_awolf_005fbarchart_005f1 != 0) {
/* 268 */             if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 269 */               out = _jspx_page_context.pushBody();
/* 270 */               _jspx_th_awolf_005fbarchart_005f1.setBodyContent((BodyContent)out);
/* 271 */               _jspx_th_awolf_005fbarchart_005f1.doInitBody();
/*     */             }
/*     */             for (;;) {
/* 274 */               out.write(10);
/* 275 */               out.write(9);
/* 276 */               int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f1.doAfterBody();
/* 277 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 280 */             if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 281 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 284 */           if (_jspx_th_awolf_005fbarchart_005f1.doEndTag() == 5) {
/* 285 */             this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fskipLabelRotation_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor.reuse(_jspx_th_awolf_005fbarchart_005f1); return;
/*     */           }
/*     */           
/* 288 */           this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fskipLabelRotation_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor.reuse(_jspx_th_awolf_005fbarchart_005f1);
/* 289 */           out.write(9);
/* 290 */           out.write("\n</td> </tr>\n</table>\n<br>\n\n<br>\n   <table width=\"75%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder1\"  style=\"font-family:Arial, Helvetica, sans-serif;\" align=\"center\">\n\n    <tr>\n     <td width=\"15%\" align=\"center\" class=\"tableheadingbborder-reports\" align=\"left\">");
/* 291 */           out.print(FormatUtil.getString("am.reporting.timeperiods.name"));
/* 292 */           out.write(" </td>\n   <td width=\"15%\" align=\"center\" class=\"tableheadingbborder-reports\" align=\"left\">");
/* 293 */           out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.availability.text"));
/* 294 */           out.write(" </td>\n   <td width=\"15%\" align=\"center\" class=\"tableheadingbborder-reports\" align=\"left\">");
/* 295 */           out.print(FormatUtil.getString("am.webclient.reports.excel.trendreport.downtimecount.text"));
/* 296 */           out.write(" </td>\n   <td width=\"15%\" align=\"center\" class=\"tableheadingbborder-reports\" align=\"left\">");
/* 297 */           out.print(FormatUtil.getString("am.webclient.historydata.totaldown.text"));
/* 298 */           out.write(" </td>\n    </tr>\n\n   \t  ");
/*     */           
/* 300 */           Calendar cal = Calendar.getInstance();
/* 301 */           SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
/* 302 */           SimpleDateFormat sdf1 = new SimpleDateFormat("MMM-yyyy");
/* 303 */           Date d = new Date();
/*     */           
/* 305 */           for (int i = 0; i < Values.size(); i++) {
/* 306 */             String timetodisplay = null;
/* 307 */             ArrayList a1 = (ArrayList)Values.get(i);
/*     */             
/* 309 */             String type = a1.get(3).toString();
/* 310 */             HashMap data = (HashMap)a1.get(4);
/* 311 */             String bgclass = "whitegrayrightalign";
/* 312 */             if ("HAI".equals(type)) {
/* 313 */               bgclass = "columnheading";
/*     */               
/*     */ 
/* 316 */               for (int j = 12; j > 0; j--) {
/* 317 */                 cal.setTime(new Date(System.currentTimeMillis()));
/* 318 */                 if ("day".equalsIgnoreCase(interval)) {
/* 319 */                   d = new Date(System.currentTimeMillis() - (j - 1) * 86400000);
/*     */                 }
/* 321 */                 else if ("week".equalsIgnoreCase(interval)) {
/* 322 */                   d = new Date(System.currentTimeMillis() - (j - 1) * 604800000L);
/*     */                 }
/*     */                 else {
/* 325 */                   cal.add(2, -j + 1);
/*     */                 }
/* 327 */                 String k = j + "";
/* 328 */                 HashMap temp = (HashMap)data.get(k);
/*     */                 
/* 330 */                 String uptime = temp.get("uptime").toString();
/* 331 */                 String totaldowntime = temp.get("totaldowntime").toString();
/* 332 */                 int downcount = Integer.parseInt(temp.get("downcount").toString());
/* 333 */                 if (!"month".equalsIgnoreCase(interval)) {
/* 334 */                   timetodisplay = sdf.format(d);
/*     */                 }
/*     */                 else {
/* 337 */                   timetodisplay = sdf1.format(cal.getTime());
/*     */                 }
/*     */                 
/* 340 */                 out.write("\n\t\t<tr> \t\t\n\t\t<td width=\"15%\" class=\"whitegrayrightalign-reports\" align=\"center\"> ");
/* 341 */                 out.print(timetodisplay);
/* 342 */                 out.write(" </td>\n      \n\t\t<td width=\"15%\" class=\"whitegrayrightalign-reports-normal\" align=\"center\"> ");
/* 343 */                 out.print(uptime);
/* 344 */                 out.write("</td>\n\t\t<td width=\"15%\" class=\"whitegrayrightalign-reports-normal\" align=\"center\"> ");
/* 345 */                 out.print(downcount);
/* 346 */                 out.write(" </td> \n\t\t<td width=\"15%\" class=\"whitegrayrightalign-reports-normal\" align=\"center\"> ");
/* 347 */                 out.print(totaldowntime);
/* 348 */                 out.write(" </td> \t\t\t\n     \n        ");
/*     */               }
/*     */             }
/*     */           }
/* 352 */           out.write("\n\t\n   </tr>\n\n   </table>\n\n\n");
/*     */         }
/*     */         catch (Exception ex) {}
/*     */       }
/* 356 */       out.write("\n</html>\n\n");
/*     */     } catch (Throwable t) {
/* 358 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 359 */         out = _jspx_out;
/* 360 */         if ((out != null) && (out.getBufferSize() != 0))
/* 361 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 362 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 365 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\AvailabilityTrendDowntimeReport_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */