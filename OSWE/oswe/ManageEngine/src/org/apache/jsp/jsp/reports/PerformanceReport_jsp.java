/*      */ package org.apache.jsp.jsp.reports;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.SummaryBean;
/*      */ import com.adventnet.appmanager.tags.Truncate;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.BarChart;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.util.ArrayList;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatNumberTag;
/*      */ 
/*      */ public final class PerformanceReport_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   28 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */   private static java.util.Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   51 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   55 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   56 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   69 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   73 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.release();
/*   74 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer.release();
/*   75 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*   76 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   77 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.release();
/*   78 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*   79 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/*   80 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   81 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   82 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.release();
/*   83 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   84 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   85 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   92 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   95 */     JspWriter out = null;
/*   96 */     Object page = this;
/*   97 */     JspWriter _jspx_out = null;
/*   98 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  102 */       response.setContentType("text/html");
/*  103 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  105 */       _jspx_page_context = pageContext;
/*  106 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  107 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  108 */       session = pageContext.getSession();
/*  109 */       out = pageContext.getOut();
/*  110 */       _jspx_out = out;
/*      */       
/*  112 */       out.write("\n\n\n\n\n\n \n  \n \n \n \n\n\n");
/*  113 */       SummaryBean sumgraph = null;
/*  114 */       sumgraph = (SummaryBean)_jspx_page_context.getAttribute("sumgraph", 2);
/*  115 */       if (sumgraph == null) {
/*  116 */         sumgraph = new SummaryBean();
/*  117 */         _jspx_page_context.setAttribute("sumgraph", sumgraph, 2);
/*      */       }
/*  119 */       out.write("\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n<script>\nfunction fnSelectAll(e,name)\n{\n    ToggleAll(e,document.form1,name);\n}\nfunction callComparision()\n{\n      \n\tvar resid=\"\";\n\tvar attid=\"\";\n\t\n\tvar stdate=document.forms[1].startDate.value;\n\tvar enddate=document.forms[1].endDate.value\n\tvar period=document.forms[1].period.value;\n\tif(period == '1'){\n\t\tperiod = '-7'\n\t}else if(period == '2'){\n\t\tperiod = '-30'\n\t}else if(period == '5'){\n\t\tperiod = '-5'\n\t}\n\tif(!checkforOneSelected(document.form1,\"compare\"))\n\t{\n\t  alert(\"");
/*  120 */       out.print(FormatUtil.getString("am.comparereport.jsalert.text"));
/*  121 */       out.write("\");\n\t  return;\n\t}\n\telse\n\t{\n\tfor(var i=0;i<document.form1.compare.length;i++)\n\t\t{\n\t\t\tif(document.form1.compare[i].checked==true)\n\t\t\t{\n\t\t\t\tvar temp=document.form1.compare[i].value;\n\t\t\t\tvar test=temp.split(\",\");\n\t\t\t\tvar res=test[0];\n\t\t\t\tvar att=test[1];\n\t\t\t\tif(resid!=\"\")\n\t\t\t\t{\n\t\t\t\tresid=resid+\",\"+res;\n\t\t\t\tattid=attid+\",\"+att;\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\tresid=res;\n\t\t\t\tattid=att;\n\t\t\t\t}\n\t\t\t\t\n\t\t\t}\n\t\t\t\n\t}\n\t\n\t\n\tfnOpenNewWindowWithHeightWidth(\"../showHistoryData.do?method=getIndividualURLandCompareReportsData&resourceid=-1&childid=\"+resid+\"&attributeid=\"+attid+\"&period=\"+period+\"&startdate=\"+stdate+\"&enddate=\"+enddate,\"900\",\"500\");\n        }\n}\n</script>\n<script type=text/Javascript>\nfunction chk_height(){\n\ndocument.getElementById(\"div1\").scrollTop = document.getElementById(\"div2\").scrollTop\n\n}\n\n\n\n</script>\n\n</head>\n\n<div id=\"ReportsLoadingDiv\"  class=\"hideContent\"  style=\"position: absolute; top: 0px; left: 0px; z-index: 9998; background-color: rgb(0, 0, 0); opacity: 0.25; width: 100%; height: 100%;text-align:center;\">\n  <div style=\"margin-top:30%;\" ><img  alt=\"Loading...\" src=\"/images/loading.gif\" /><br/>");
/*  122 */       out.print(FormatUtil.getString("am.webclient.reports.loading"));
/*  123 */       out.write("</div>\n</div>\n\n\n");
/*  124 */       String RESOURCEIDS = (String)request.getAttribute("RESOURCEIDS");
/*  125 */       String ATTRIBUTEIDS = (String)request.getAttribute("ATTRIBUTEIDS");
/*  126 */       String ATTRINAME = (String)request.getAttribute("ATTNAME");
/*  127 */       String period = (String)request.getAttribute("period");
/*  128 */       String startTime = (String)request.getAttribute("STIME");
/*  129 */       String endTime = (String)request.getAttribute("ETIME");
/*  130 */       String dailyRptStarttime = (String)request.getAttribute("dailyStime");
/*  131 */       String dailyRptEndtime = (String)request.getAttribute("dailyEtime");
/*  132 */       String ANAME = (String)request.getAttribute("AttDispalyName");
/*  133 */       String MNAME = (String)request.getAttribute("methodName");
/*      */       
/*  135 */       sumgraph.setResid(RESOURCEIDS);
/*  136 */       sumgraph.setAttributeid(ATTRIBUTEIDS);
/*  137 */       sumgraph.setArchivedforUrl(true);
/*  138 */       sumgraph.setCompareUrls(true);
/*  139 */       sumgraph.setStarttime(startTime);
/*  140 */       sumgraph.setEndtime(endTime);
/*  141 */       sumgraph.setPeriod(period);
/*  142 */       sumgraph.setCategory(ATTRINAME);
/*  143 */       sumgraph.setResourceName(ANAME);
/*  144 */       sumgraph.setMethodName(MNAME);
/*      */       
/*  146 */       if ((dailyRptStarttime != null) && (!dailyRptStarttime.equals("0")))
/*      */       {
/*  148 */         sumgraph.setDailyRptStarttime(dailyRptStarttime);
/*  149 */         sumgraph.setDailyRptEndtime(dailyRptEndtime);
/*      */       }
/*      */       
/*  152 */       ArrayList al = (ArrayList)request.getAttribute("data");
/*      */       
/*  154 */       String heading = (String)request.getAttribute("heading");
/*  155 */       String graphheading = (String)request.getAttribute("graphheading");
/*  156 */       if (graphheading == null) {
/*  157 */         graphheading = heading;
/*      */       }
/*  159 */       boolean one = false;
/*  160 */       String yaxis = "";
/*  161 */       yaxis = (String)request.getAttribute("attributeDispalyName");
/*  162 */       String Units = (String)request.getAttribute("unitstoshow");
/*  163 */       if ("true".equals(com.adventnet.appmanager.util.Constants.attributesReportGraphType)) {
/*  164 */         sumgraph.setBarData(al);
/*      */       }
/*  166 */       if (request.getAttribute("attributeDispalyName") != null)
/*      */       {
/*  168 */         yaxis = (String)request.getAttribute("attributeDispalyName");
/*      */       }
/*      */       else
/*      */       {
/*  172 */         java.util.StringTokenizer str = new java.util.StringTokenizer(ATTRIBUTEIDS, ",");
/*  173 */         String att_id = "";
/*  174 */         if (str.hasMoreTokens())
/*      */         {
/*  176 */           att_id = str.nextToken();
/*      */         }
/*  178 */         String qry = "select attribute from AM_ATTRIBUTES WHERE ATTRIBUTEID=" + att_id;
/*  179 */         com.adventnet.appmanager.db.AMConnectionPool cp1 = com.adventnet.appmanager.db.AMConnectionPool.getInstance();
/*      */         try
/*      */         {
/*  182 */           java.sql.ResultSet rs = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(qry);
/*  183 */           if (rs.next())
/*      */           {
/*  185 */             yaxis = rs.getString(1);
/*      */           }
/*  187 */           rs.close();
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  191 */           exc.printStackTrace();
/*      */         }
/*      */       }
/*      */       
/*  195 */       if (Units == null)
/*      */       {
/*  197 */         Units = "(" + (String)request.getAttribute("Units") + ")";
/*      */       }
/*  199 */       if ("true".equals(com.adventnet.appmanager.util.Constants.attributesReportGraphType))
/*      */       {
/*  201 */         if ((String)request.getAttribute("Units") != null)
/*      */         {
/*  203 */           sumgraph.setUnit((String)request.getAttribute("Units"));
/*      */         }
/*      */         else {
/*  206 */           sumgraph.setUnit("");
/*      */         }
/*      */       }
/*  209 */       String height = "500";
/*  210 */       String width = "780";
/*  211 */       int sizeofarray = 0;
/*  212 */       if (al != null)
/*  213 */         sizeofarray = al.size();
/*  214 */       if ((period != null) && (period.equals("14"))) {
/*  215 */         one = true;
/*      */       }
/*  217 */       if (sizeofarray > 0)
/*      */       {
/*  219 */         if (sizeofarray < 5)
/*      */         {
/*  221 */           height = "300";
/*  222 */           width = "700";
/*      */         }
/*      */         else {
/*  225 */           height = "500";
/*  226 */           width = "700";
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  256 */       if (((al != null) && (al.size() == 1)) || ((request.getAttribute("PRINTER_FRIENDLY") != null) && (request.getAttribute("PRINTER_FRIENDLY").equals("true"))))
/*      */       {
/*  258 */         one = true;
/*      */       }
/*      */       
/*      */ 
/*  262 */       out.write("\n<form name=\"form1\">\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n\n    <td colspan=\"2\" width=\"72%\" height=\"26\" class=\"tableheadingbborder\">");
/*  263 */       out.print(FormatUtil.getString(graphheading));
/*  264 */       out.write(" </td>\n        </tr>\n  \n\n  <tr>\n\n    <td height=\"190\" align=\"center\" class=\"bodytext\" ><br>\n\t");
/*  265 */       if ("false".equals(com.adventnet.appmanager.util.Constants.attributesReportGraphType))
/*      */       {
/*  267 */         out.write("\n        ");
/*      */         
/*  269 */         TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/*  270 */         _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/*  271 */         _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*      */         
/*  273 */         _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("sumgraph");
/*      */         
/*  275 */         _jspx_th_awolf_005ftimechart_005f0.setWidth(width);
/*      */         
/*  277 */         _jspx_th_awolf_005ftimechart_005f0.setHeight(height);
/*      */         
/*  279 */         _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */         
/*  281 */         _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*      */         
/*  283 */         _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString(yaxis) + " " + Units);
/*      */         
/*  285 */         _jspx_th_awolf_005ftimechart_005f0.setShape(true);
/*      */         
/*  287 */         _jspx_th_awolf_005ftimechart_005f0.setCustomDateAxis(true);
/*      */         
/*  289 */         _jspx_th_awolf_005ftimechart_005f0.setCustomAngle(270.0D);
/*  290 */         int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/*  291 */         if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/*  292 */           if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/*  293 */             out = _jspx_page_context.pushBody();
/*  294 */             _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/*  295 */             _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */           }
/*      */           for (;;) {
/*  298 */             out.write("\n          ");
/*  299 */             int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/*  300 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*  303 */           if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/*  304 */             out = _jspx_page_context.popBody();
/*      */           }
/*      */         }
/*  307 */         if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/*  308 */           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */         }
/*      */         
/*  311 */         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fshape_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0);
/*  312 */         out.write("<br><br>\n\t");
/*      */       } else {
/*  314 */         out.write(10);
/*  315 */         out.write(9);
/*      */         
/*  317 */         BarChart _jspx_th_awolf_005fbarchart_005f0 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/*  318 */         _jspx_th_awolf_005fbarchart_005f0.setPageContext(_jspx_page_context);
/*  319 */         _jspx_th_awolf_005fbarchart_005f0.setParent(null);
/*      */         
/*  321 */         _jspx_th_awolf_005fbarchart_005f0.setDataSetProducer("sumgraph");
/*      */         
/*  323 */         _jspx_th_awolf_005fbarchart_005f0.setWidth("700");
/*      */         
/*  325 */         _jspx_th_awolf_005fbarchart_005f0.setHeight("300");
/*      */         
/*  327 */         _jspx_th_awolf_005fbarchart_005f0.setLegend("false");
/*      */         
/*  329 */         _jspx_th_awolf_005fbarchart_005f0.setUrl(true);
/*      */         
/*  331 */         _jspx_th_awolf_005fbarchart_005f0.setXaxisLabel(FormatUtil.getString("Monitors"));
/*      */         
/*  333 */         _jspx_th_awolf_005fbarchart_005f0.setYaxisLabel(FormatUtil.getString(yaxis) + " " + Units);
/*      */         
/*  335 */         _jspx_th_awolf_005fbarchart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*      */         
/*  337 */         _jspx_th_awolf_005fbarchart_005f0.setMaxBarWidth(0.03D);
/*  338 */         int _jspx_eval_awolf_005fbarchart_005f0 = _jspx_th_awolf_005fbarchart_005f0.doStartTag();
/*  339 */         if (_jspx_eval_awolf_005fbarchart_005f0 != 0) {
/*  340 */           if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/*  341 */             out = _jspx_page_context.pushBody();
/*  342 */             _jspx_th_awolf_005fbarchart_005f0.setBodyContent((BodyContent)out);
/*  343 */             _jspx_th_awolf_005fbarchart_005f0.doInitBody();
/*      */           }
/*      */           for (;;) {
/*  346 */             out.write(10);
/*  347 */             out.write(9);
/*  348 */             int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f0.doAfterBody();
/*  349 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*  352 */           if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/*  353 */             out = _jspx_page_context.popBody();
/*      */           }
/*      */         }
/*  356 */         if (_jspx_th_awolf_005fbarchart_005f0.doEndTag() == 5) {
/*  357 */           this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0); return;
/*      */         }
/*      */         
/*  360 */         this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0);
/*  361 */         out.write(10);
/*  362 */         out.write(9);
/*      */       }
/*  364 */       out.write("\n\t   </td>\n        </tr>\n      </table>\n      <br>\n");
/*  365 */       if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*      */         return;
/*  367 */       out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n \n  ");
/*  368 */       if ((period != null) && (period.equals("14"))) {
/*  369 */         ArrayList pdfdata = (ArrayList)request.getAttribute("rawdata");
/*  370 */         ArrayList list = (ArrayList)request.getAttribute("list");
/*  371 */         int htsize = 0;
/*  372 */         int htsize1 = 0;
/*  373 */         int columnsize = list.size();
/*  374 */         int pdfdatasize = pdfdata.size();
/*      */         
/*  376 */         if (columnsize <= 7) {
/*  377 */           htsize = 420;
/*  378 */           htsize1 = 420;
/*      */         } else {
/*  380 */           htsize = 420;
/*  381 */           htsize1 = 440;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  386 */         out.write("\n  \n  <tr><td valign=\"top\">\n  ");
/*  387 */         if (pdfdatasize < 20) {
/*  388 */           out.write("\n<div id=\"div1\" style=\"position:relative; overflow:hidden; width:150px; \"> \n");
/*      */         } else {
/*  390 */           out.write("\n<div id=\"div1\" style=\"position:relative; overflow:hidden; width:150px;height:");
/*  391 */           out.print(htsize);
/*  392 */           out.write("px;\"> \n");
/*      */         }
/*  394 */         out.write("\n          <table  border=\"0\"cellpadding=\"2\" cellspacing=\"1\" >\n            <tr> \n              <td  class=\"tableheading\" width=\"25%\" align=\"center\">");
/*  395 */         out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/*  396 */         out.write("</td>\n              <td  class=\"tableheading\" width=\"25%\" align=\"center\">");
/*  397 */         out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/*  398 */         out.write("</td>\n            </tr>\n            ");
/*      */         try
/*      */         {
/*  401 */           for (int i = 0; i < pdfdatasize; i++)
/*      */           {
/*  403 */             String bgcolor1 = "";
/*  404 */             String selectedColor1 = "class=\"selectedborder\"";
/*  405 */             if (i % 2 == 0)
/*      */             {
/*  407 */               bgcolor1 = "class=\"whitegrayborder\"";
/*      */             }
/*      */             else
/*      */             {
/*  411 */               bgcolor1 = "class=\"yellowgrayborder\"";
/*      */             }
/*  413 */             ArrayList a11 = (ArrayList)pdfdata.get(i);
/*      */             
/*  415 */             long archivedTime1 = ((Long)a11.get(0)).longValue();
/*  416 */             pageContext.setAttribute("date1", new java.util.Date(archivedTime1));
/*      */             
/*      */ 
/*  419 */             out.write("\n            <tr> \n              <td ");
/*  420 */             out.print(bgcolor1);
/*  421 */             out.write(" align=\"center\" nowrap>");
/*  422 */             if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_page_context))
/*      */               return;
/*  424 */             out.write("</td>\n              <td ");
/*  425 */             out.print(bgcolor1);
/*  426 */             out.write(" align=\"center\" nowrap>");
/*  427 */             if (_jspx_meth_fmt_005fformatDate_005f1(_jspx_page_context))
/*      */               return;
/*  429 */             out.write("</td>\n            </tr>\n            ");
/*      */           }
/*      */         } catch (Exception ed) {
/*  432 */           ed.printStackTrace();
/*      */         }
/*  434 */         out.write("\n          </table>\n        </div></td>\n  \n  <td valign=top>\n   ");
/*  435 */         if (pdfdatasize < 20) {
/*  436 */           out.write("\n<div id=\"div2\"style=\"position:relative;overflow:auto;width:640px; \" onscroll=\"chk_height()\"> \n");
/*      */         } else {
/*  438 */           out.write("\n<div id=\"div2\"style=\"position:relative;overflow:auto;width:640px;height:");
/*  439 */           out.print(htsize1);
/*  440 */           out.write("px; \" onscroll=\"chk_height()\"> \n");
/*      */         }
/*  442 */         out.write("\n          <table  border=\"0\" cellpadding=\"2\" cellspacing=\"1\" width='100%'>\n            <tr> \n              ");
/*  443 */         for (int y = 0; y < columnsize; y++)
/*      */         {
/*  445 */           ArrayList d1 = (ArrayList)list.get(y);
/*  446 */           if (y == columnsize - 1) {
/*  447 */             out.write("\n              <td class=\"tableheading\" title='");
/*  448 */             out.print((String)d1.get(1));
/*  449 */             out.write("' nowrap width='100%'>");
/*  450 */             out.print(y + 1);
/*  451 */             out.write(46);
/*  452 */             out.print(FormatUtil.getTrimmedText((String)d1.get(1), 15));
/*  453 */             out.write("</td>\n              ");
/*      */           } else {
/*  455 */             out.write("\n              <td class=\"tableheading\" title='");
/*  456 */             out.print((String)d1.get(1));
/*  457 */             out.write("' nowrap>");
/*  458 */             out.print(y + 1);
/*  459 */             out.write(46);
/*  460 */             out.print(FormatUtil.getTrimmedText((String)d1.get(1), 15));
/*  461 */             out.write("</td>\n              ");
/*      */           } }
/*  463 */         out.write("\n            </tr>\n            ");
/*      */         
/*      */         try
/*      */         {
/*  467 */           for (int i = 0; i < pdfdata.size(); i++)
/*      */           {
/*  469 */             String bgcolor = "";
/*  470 */             String selectedColor = "class=\"selectedborder\"";
/*  471 */             if (i % 2 == 0)
/*      */             {
/*  473 */               bgcolor = "class=\"whitegrayborder\"";
/*      */             }
/*      */             else
/*      */             {
/*  477 */               bgcolor = "class=\"yellowgrayborder\"";
/*      */             }
/*  479 */             ArrayList a1 = (ArrayList)pdfdata.get(i);
/*      */             
/*  481 */             long archivedTime = ((Long)a1.get(0)).longValue();
/*  482 */             pageContext.setAttribute("date", new java.util.Date(archivedTime));
/*      */             
/*      */ 
/*  485 */             out.write("\n            <tr> \n              ");
/*      */             
/*  487 */             for (int k = 1; k < a1.size(); k++)
/*      */             {
/*      */ 
/*  490 */               for (int m = 0; m < list.size(); m++)
/*      */               {
/*  492 */                 ArrayList q1 = (ArrayList)list.get(m);
/*      */                 
/*  494 */                 String key = (String)q1.get(0);
/*      */                 
/*  496 */                 java.util.HashMap h1 = (java.util.HashMap)a1.get(k);
/*      */                 
/*  498 */                 String avgvalues = null;
/*      */                 
/*  500 */                 java.util.Properties p1 = (java.util.Properties)h1.get(key);
/*      */                 
/*  502 */                 if (p1 != null)
/*      */                 {
/*  504 */                   avgvalues = p1.getProperty("AVGVALUE");
/*      */                 }
/*      */                 else {
/*  507 */                   avgvalues = "-";
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*  512 */                 out.write("\n              <td ");
/*  513 */                 out.print(bgcolor);
/*  514 */                 out.write(" align=\"left\" >");
/*  515 */                 out.print(avgvalues);
/*  516 */                 out.write("</td>\n              ");
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*  521 */             out.write("\n            </tr>\n            ");
/*      */           }
/*      */           
/*      */ 
/*      */         }
/*      */         catch (Exception exc)
/*      */         {
/*  528 */           exc.printStackTrace();
/*      */         }
/*  530 */         out.write("\n          </table>\n          <div id=\"temp_div\"></div>\n        </div> \n</td>\n  \n </tr>\n  \n    \n  ");
/*      */       } else {
/*  532 */         out.write("\n   <tr> \n        ");
/*  533 */         String reportunit = (String)pageContext.getAttribute("unitvalue");
/*      */         
/*  535 */         if ((reportunit != null) && (reportunit.equals("")))
/*      */         {
/*  537 */           if (!one)
/*      */           {
/*  539 */             out.write("\n                     <td width=\"3%\"height=\"28\" valign=\"center\"  class=\"tableheadingbborder\">\n                      <input type=\"checkbox\" name=\"headercheckbox1\"  onClick=\"javascript:fnSelectAll(this,'compare')\">\n                </td>");
/*      */           }
/*  541 */           out.write("\n\t\t\t\t\t\t  <td width=\"41%\" class=\"tableheadingbborder\">");
/*  542 */           out.print(FormatUtil.getString("am.reporttab.performancereport.heading.text"));
/*  543 */           out.write("</td>\n    <td width=\"22%\" class=\"tableheadingbborder\">");
/*  544 */           out.print(FormatUtil.getString("am.webclient.common.minimum.text"));
/*  545 */           out.write("</td>\n    <td width=\"19%\" class=\"tableheadingbborder\">");
/*  546 */           out.print(FormatUtil.getString("am.webclient.common.maximum.text"));
/*  547 */           out.write("</td>\n    <td width=\"18%\" class=\"tableheadingbborder\">");
/*  548 */           out.print(FormatUtil.getString("am.webclient.common.average.text"));
/*  549 */           out.write("</td>\n    <td width=\"18%\" class=\"tableheadingbborder\">&nbsp;</td>\n                   ");
/*      */         }
/*      */         else {
/*  552 */           if (!one)
/*      */           {
/*  554 */             out.write("\n   <td width=\"3%\"height=\"28\" valign=\"center\"  class=\"tableheadingbborder\">\n                      <input type=\"checkbox\" name=\"headercheckbox1\"  onClick=\"javascript:fnSelectAll(this,'compare')\">\n                </td>");
/*      */           }
/*  556 */           out.write("\n    <td width=\"41%\" class=\"tableheadingbborder\">");
/*  557 */           out.print(FormatUtil.getString("am.reporttab.performancereport.heading.text"));
/*  558 */           out.write("</td>\n    <td width=\"22%\" class=\"tableheadingbborder\">");
/*  559 */           out.print(FormatUtil.getString("am.reporttab.performancereport.minimum.text", new String[] { FormatUtil.getString(reportunit) }));
/*  560 */           out.write("</td>\n    <td width=\"19%\" class=\"tableheadingbborder\">");
/*  561 */           out.print(FormatUtil.getString("am.reporttab.performancereport.maximum.text", new String[] { FormatUtil.getString(reportunit) }));
/*  562 */           out.write("</td>\n    <td width=\"18%\" class=\"tableheadingbborder\">");
/*  563 */           out.print(FormatUtil.getString("am.reporttab.performancereport.average.text", new String[] { FormatUtil.getString(reportunit) }));
/*  564 */           out.write("</td>\n     <td width=\"18%\" class=\"tableheadingbborder\">&nbsp;</td>\n    \n  ");
/*      */         }
/*  566 */         out.write("\n  </tr>\n  \n  \n  \n  \n  ");
/*      */         
/*  568 */         ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/*  569 */         _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/*  570 */         _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */         
/*  572 */         _jspx_th_c_005fforEach_005f0.setVar("row");
/*      */         
/*  574 */         _jspx_th_c_005fforEach_005f0.setItems("${data}");
/*      */         
/*  576 */         _jspx_th_c_005fforEach_005f0.setVarStatus("i");
/*  577 */         int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */         try {
/*  579 */           int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/*  580 */           if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */             for (;;) {
/*  582 */               out.write(32);
/*  583 */               out.write(10);
/*  584 */               if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  623 */                 _jspx_th_c_005fforEach_005f0.doFinally();
/*  624 */                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */               }
/*  586 */               out.write("\n  <tr>");
/*  587 */               if (!one) {
/*  588 */                 out.write(" \n  <td class=\"whitegrayrightalign\"><input type=\"checkbox\" value=\"");
/*  589 */                 if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  623 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/*  624 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                 }
/*  591 */                 out.write("\" name=\"compare\"></td>");
/*      */               }
/*  593 */               out.write("\n\t\n  ");
/*  594 */               if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  623 */                 _jspx_th_c_005fforEach_005f0.doFinally();
/*  624 */                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */               }
/*  596 */               out.write("\n               \n");
/*  597 */               if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  623 */                 _jspx_th_c_005fforEach_005f0.doFinally();
/*  624 */                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */               }
/*  599 */               out.write(10);
/*  600 */               out.write(10);
/*  601 */               if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  623 */                 _jspx_th_c_005fforEach_005f0.doFinally();
/*  624 */                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */               }
/*  603 */               out.write("\n\n<td width=\"90%\" height=\"35\" align=\"right\" class=\"whitegrayrightalign\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/*  604 */               if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  623 */                 _jspx_th_c_005fforEach_005f0.doFinally();
/*  624 */                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */               }
/*  606 */               out.write("&attributeid=");
/*  607 */               if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  623 */                 _jspx_th_c_005fforEach_005f0.doFinally();
/*  624 */                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */               }
/*  609 */               out.write("&period=-7',740,550)\"> \n           <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"Click to view last seven days data\"></a></td> \n           \n\n  </tr>\n  ");
/*  610 */               int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/*  611 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  615 */           if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  623 */             _jspx_th_c_005fforEach_005f0.doFinally();
/*  624 */             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */           }
/*      */         }
/*      */         catch (Throwable _jspx_exception)
/*      */         {
/*      */           for (;;)
/*      */           {
/*  619 */             int tmp3015_3014 = 0; int[] tmp3015_3012 = _jspx_push_body_count_c_005fforEach_005f0; int tmp3017_3016 = tmp3015_3012[tmp3015_3014];tmp3015_3012[tmp3015_3014] = (tmp3017_3016 - 1); if (tmp3017_3016 <= 0) break;
/*  620 */             out = _jspx_page_context.popBody(); }
/*  621 */           _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */         } finally {
/*  623 */           _jspx_th_c_005fforEach_005f0.doFinally();
/*  624 */           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */         }
/*  626 */         out.write(10);
/*      */       }
/*  628 */       if (!one)
/*      */       {
/*      */ 
/*  631 */         out.write("\n<tr>\n<td colspan=6  height='25' class=\"tablebottom\"><span class=\"bodytext\">\n       <a href=\"javascript:callComparision()\" class='staticlinks'>");
/*  632 */         out.print(FormatUtil.getString("am.webclient.report.compare.text"));
/*  633 */         out.write("</a> </span></td>\n</tr>\n");
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  638 */       out.write("\n<tr>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr> \n    <td align=\"center\" class=\"bodytext\"> <br><br> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" class=\"grayfullborder\">\n\t\n        <tr> \n          <td class=\"tdindent\"><span class=\"bodytext\">\n\t<p> <b>");
/*  639 */       out.print(FormatUtil.getString("am.webclient.common.minimum.text"));
/*  640 */       out.write(" </b><br>\n              ");
/*  641 */       out.print(FormatUtil.getString("am.report.attribute.minimum.note"));
/*  642 */       out.write(". </p>\n\t<p> <b>");
/*  643 */       out.print(FormatUtil.getString("am.webclient.common.maximum.text"));
/*  644 */       out.write("</b><br>\n            \t");
/*  645 */       out.print(FormatUtil.getString("am.report.attribute.maximum.note"));
/*  646 */       out.write(".</p>\n            \n\t    <p> <b>");
/*  647 */       out.print(FormatUtil.getString("am.webclient.common.average.text"));
/*  648 */       out.write(" </b><br>\n              ");
/*  649 */       out.print(FormatUtil.getString("am.report.attribute.average.note"));
/*  650 */       out.write(". </p>\n            <p> \n              <br>\n              * ");
/*  651 */       out.print(FormatUtil.getString("am.report.attribute.graph.note"));
/*  652 */       out.write(". </p></span></td>\n        </tr>\n      </table>\n</tr>\n</table>\n\n      \n      \n</form>\n</html>\n");
/*      */     } catch (Throwable t) {
/*  654 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  655 */         out = _jspx_out;
/*  656 */         if ((out != null) && (out.getBufferSize() != 0))
/*  657 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  658 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  661 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  667 */     PageContext pageContext = _jspx_page_context;
/*  668 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  670 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/*  671 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  672 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/*  674 */     _jspx_th_c_005fset_005f0.setVar("unitvalue");
/*  675 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  676 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/*  677 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/*  678 */         out = _jspx_page_context.pushBody();
/*  679 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/*  680 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  683 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/*  684 */           return true;
/*  685 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  686 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  689 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/*  690 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  693 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  694 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  695 */       return true;
/*      */     }
/*  697 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  698 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  703 */     PageContext pageContext = _jspx_page_context;
/*  704 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  706 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  707 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  708 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/*  710 */     _jspx_th_c_005fout_005f0.setValue("${ReportForm.unit}");
/*  711 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  712 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  713 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  714 */       return true;
/*      */     }
/*  716 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  717 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  722 */     PageContext pageContext = _jspx_page_context;
/*  723 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  725 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/*  726 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/*  727 */     _jspx_th_fmt_005fformatDate_005f0.setParent(null);
/*      */     
/*  729 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${date1}");
/*      */     
/*  731 */     _jspx_th_fmt_005fformatDate_005f0.setPattern("MMM d,yyyy");
/*  732 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/*  733 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/*  734 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/*  735 */       return true;
/*      */     }
/*  737 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/*  738 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  743 */     PageContext pageContext = _jspx_page_context;
/*  744 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  746 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f1 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/*  747 */     _jspx_th_fmt_005fformatDate_005f1.setPageContext(_jspx_page_context);
/*  748 */     _jspx_th_fmt_005fformatDate_005f1.setParent(null);
/*      */     
/*  750 */     _jspx_th_fmt_005fformatDate_005f1.setValue("${date1}");
/*      */     
/*  752 */     _jspx_th_fmt_005fformatDate_005f1.setPattern("H:mm");
/*  753 */     int _jspx_eval_fmt_005fformatDate_005f1 = _jspx_th_fmt_005fformatDate_005f1.doStartTag();
/*  754 */     if (_jspx_th_fmt_005fformatDate_005f1.doEndTag() == 5) {
/*  755 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/*  756 */       return true;
/*      */     }
/*  758 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/*  759 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  764 */     PageContext pageContext = _jspx_page_context;
/*  765 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  767 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/*  768 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/*  769 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/*  771 */     _jspx_th_c_005fset_005f1.setVar("temp");
/*      */     
/*  773 */     _jspx_th_c_005fset_005f1.setValue("${row[4]},${row[7]}");
/*  774 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/*  775 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/*  776 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/*  777 */       return true;
/*      */     }
/*  779 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/*  780 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  785 */     PageContext pageContext = _jspx_page_context;
/*  786 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  788 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  789 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/*  790 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/*  792 */     _jspx_th_c_005fout_005f1.setValue("${temp}");
/*  793 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/*  794 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/*  795 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  796 */       return true;
/*      */     }
/*  798 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  799 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  804 */     PageContext pageContext = _jspx_page_context;
/*  805 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  807 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  808 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  809 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*  810 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  811 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/*  813 */         out.write("\n               ");
/*  814 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  815 */           return true;
/*  816 */         out.write("\n    ");
/*  817 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  818 */           return true;
/*  819 */         out.write("\n               ");
/*  820 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  821 */           return true;
/*  822 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  823 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  827 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  828 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  829 */       return true;
/*      */     }
/*  831 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  832 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  837 */     PageContext pageContext = _jspx_page_context;
/*  838 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  840 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  841 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  842 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/*  844 */     _jspx_th_c_005fwhen_005f0.setTest("${row[7]==711}");
/*  845 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  846 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/*  848 */         out.write("\n    <td class=\"whitegrayrightalign\" nowrap title=\"");
/*  849 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  850 */           return true;
/*  851 */         out.write("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\">");
/*  852 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  853 */           return true;
/*  854 */         out.write("</td>\n    ");
/*  855 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  856 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  860 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  861 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  862 */       return true;
/*      */     }
/*  864 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  865 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  870 */     PageContext pageContext = _jspx_page_context;
/*  871 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  873 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  874 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/*  875 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  877 */     _jspx_th_c_005fout_005f2.setValue("${row[0]}");
/*  878 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/*  879 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/*  880 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  881 */       return true;
/*      */     }
/*  883 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  884 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  889 */     PageContext pageContext = _jspx_page_context;
/*  890 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  892 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  893 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/*  894 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  896 */     _jspx_th_c_005fout_005f3.setValue("${row[8]}");
/*  897 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/*  898 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/*  899 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  900 */       return true;
/*      */     }
/*  902 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/*  903 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  908 */     PageContext pageContext = _jspx_page_context;
/*  909 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  911 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  912 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/*  913 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/*  915 */     _jspx_th_c_005fwhen_005f1.setTest("${row[7]==319 || row[7]==219 || row[7]==519 || row[7]==35 || row[7]==525 || row[7]==235 || row[7]==213 || row[7]==508 || row[7]==2619 || row[7]==2617}");
/*  916 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/*  917 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/*  919 */         out.write("\n    <td class=\"whitegrayrightalign\" nowrap title=\"");
/*  920 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  921 */           return true;
/*  922 */         out.write("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\">");
/*  923 */         if (_jspx_meth_am_005fTruncate_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  924 */           return true;
/*  925 */         out.write(45);
/*  926 */         if (_jspx_meth_am_005fTruncate_005f1(_jspx_th_c_005fwhen_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  927 */           return true;
/*  928 */         out.write("</td>\n    ");
/*  929 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/*  930 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  934 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/*  935 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  936 */       return true;
/*      */     }
/*  938 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/*  939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  944 */     PageContext pageContext = _jspx_page_context;
/*  945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  947 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  948 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/*  949 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/*  951 */     _jspx_th_c_005fout_005f4.setValue("${row[0]}");
/*  952 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/*  953 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/*  954 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/*  955 */       return true;
/*      */     }
/*  957 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/*  958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/*  963 */     PageContext pageContext = _jspx_page_context;
/*  964 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  966 */     Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/*  967 */     _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/*  968 */     _jspx_th_am_005fTruncate_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/*  970 */     _jspx_th_am_005fTruncate_005f0.setLength(20);
/*  971 */     int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/*  972 */     if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/*  973 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/*  974 */         out = _jspx_page_context.pushBody();
/*  975 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/*  976 */         _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/*  977 */         _jspx_th_am_005fTruncate_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  980 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_am_005fTruncate_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*  981 */           return true;
/*  982 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/*  983 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  986 */       if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/*  987 */         out = _jspx_page_context.popBody();
/*  988 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/*  991 */     if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/*  992 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/*  993 */       return true;
/*      */     }
/*  995 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/*  996 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_am_005fTruncate_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1001 */     PageContext pageContext = _jspx_page_context;
/* 1002 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1004 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1005 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 1006 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_am_005fTruncate_005f0);
/*      */     
/* 1008 */     _jspx_th_c_005fout_005f5.setValue("${row[8]}");
/* 1009 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 1010 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 1011 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1012 */       return true;
/*      */     }
/* 1014 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 1015 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f1(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1020 */     PageContext pageContext = _jspx_page_context;
/* 1021 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1023 */     Truncate _jspx_th_am_005fTruncate_005f1 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 1024 */     _jspx_th_am_005fTruncate_005f1.setPageContext(_jspx_page_context);
/* 1025 */     _jspx_th_am_005fTruncate_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1027 */     _jspx_th_am_005fTruncate_005f1.setLength(20);
/* 1028 */     int _jspx_eval_am_005fTruncate_005f1 = _jspx_th_am_005fTruncate_005f1.doStartTag();
/* 1029 */     if (_jspx_eval_am_005fTruncate_005f1 != 0) {
/* 1030 */       if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 1031 */         out = _jspx_page_context.pushBody();
/* 1032 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 1033 */         _jspx_th_am_005fTruncate_005f1.setBodyContent((BodyContent)out);
/* 1034 */         _jspx_th_am_005fTruncate_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1037 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_am_005fTruncate_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1038 */           return true;
/* 1039 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f1.doAfterBody();
/* 1040 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1043 */       if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 1044 */         out = _jspx_page_context.popBody();
/* 1045 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 1048 */     if (_jspx_th_am_005fTruncate_005f1.doEndTag() == 5) {
/* 1049 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 1050 */       return true;
/*      */     }
/* 1052 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 1053 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_am_005fTruncate_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1058 */     PageContext pageContext = _jspx_page_context;
/* 1059 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1061 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1062 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 1063 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_am_005fTruncate_005f1);
/*      */     
/* 1065 */     _jspx_th_c_005fout_005f6.setValue("${row[0]}");
/* 1066 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 1067 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 1068 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1069 */       return true;
/*      */     }
/* 1071 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 1072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1077 */     PageContext pageContext = _jspx_page_context;
/* 1078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1080 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1081 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 1082 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 1083 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 1084 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 1086 */         out.write("   \n               <td class=\"whitegrayrightalign\" title=\"");
/* 1087 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1088 */           return true;
/* 1089 */         out.write("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\" nowrap>");
/* 1090 */         if (_jspx_meth_am_005fTruncate_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1091 */           return true;
/* 1092 */         out.write("</td>\n               ");
/* 1093 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 1094 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1098 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 1099 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1100 */       return true;
/*      */     }
/* 1102 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 1103 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1108 */     PageContext pageContext = _jspx_page_context;
/* 1109 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1111 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1112 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 1113 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 1115 */     _jspx_th_c_005fout_005f7.setValue("${row[0]}");
/* 1116 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 1117 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 1118 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1119 */       return true;
/*      */     }
/* 1121 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 1122 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fTruncate_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1127 */     PageContext pageContext = _jspx_page_context;
/* 1128 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1130 */     Truncate _jspx_th_am_005fTruncate_005f2 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 1131 */     _jspx_th_am_005fTruncate_005f2.setPageContext(_jspx_page_context);
/* 1132 */     _jspx_th_am_005fTruncate_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 1134 */     _jspx_th_am_005fTruncate_005f2.setLength(40);
/* 1135 */     int _jspx_eval_am_005fTruncate_005f2 = _jspx_th_am_005fTruncate_005f2.doStartTag();
/* 1136 */     if (_jspx_eval_am_005fTruncate_005f2 != 0) {
/* 1137 */       if (_jspx_eval_am_005fTruncate_005f2 != 1) {
/* 1138 */         out = _jspx_page_context.pushBody();
/* 1139 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 1140 */         _jspx_th_am_005fTruncate_005f2.setBodyContent((BodyContent)out);
/* 1141 */         _jspx_th_am_005fTruncate_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 1144 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_am_005fTruncate_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1145 */           return true;
/* 1146 */         int evalDoAfterBody = _jspx_th_am_005fTruncate_005f2.doAfterBody();
/* 1147 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 1150 */       if (_jspx_eval_am_005fTruncate_005f2 != 1) {
/* 1151 */         out = _jspx_page_context.popBody();
/* 1152 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 1155 */     if (_jspx_th_am_005fTruncate_005f2.doEndTag() == 5) {
/* 1156 */       this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f2);
/* 1157 */       return true;
/*      */     }
/* 1159 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f2);
/* 1160 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_am_005fTruncate_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1165 */     PageContext pageContext = _jspx_page_context;
/* 1166 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1168 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1169 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 1170 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_am_005fTruncate_005f2);
/*      */     
/* 1172 */     _jspx_th_c_005fout_005f8.setValue("${row[8]}");
/* 1173 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 1174 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 1175 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1176 */       return true;
/*      */     }
/* 1178 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 1179 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1184 */     PageContext pageContext = _jspx_page_context;
/* 1185 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1187 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1188 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 1189 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1191 */     _jspx_th_c_005fif_005f0.setTest("${ ReportForm.unit=='MB'}");
/* 1192 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 1193 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 1195 */         out.write("\n               ");
/* 1196 */         if (_jspx_meth_c_005fchoose_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1197 */           return true;
/* 1198 */         out.write("\n\t               \n");
/* 1199 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 1200 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1204 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 1205 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1206 */       return true;
/*      */     }
/* 1208 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 1209 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1214 */     PageContext pageContext = _jspx_page_context;
/* 1215 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1217 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1218 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1219 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/* 1220 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1221 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 1223 */         out.write("\n               ");
/* 1224 */         if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1225 */           return true;
/* 1226 */         out.write("\n               ");
/* 1227 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1228 */           return true;
/* 1229 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1230 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1234 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1235 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1236 */       return true;
/*      */     }
/* 1238 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1239 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1244 */     PageContext pageContext = _jspx_page_context;
/* 1245 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1247 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1248 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 1249 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1251 */     _jspx_th_c_005fwhen_005f2.setTest("${row[7]==501}");
/* 1252 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 1253 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 1255 */         out.write("\n           \n            <td class=\"whitegrayrightalign\">");
/* 1256 */         if (_jspx_meth_fmt_005fformatNumber_005f0(_jspx_th_c_005fwhen_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1257 */           return true;
/* 1258 */         out.write("</td>\n\t    <td class=\"whitegrayrightalign\">");
/* 1259 */         if (_jspx_meth_fmt_005fformatNumber_005f1(_jspx_th_c_005fwhen_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1260 */           return true;
/* 1261 */         out.write("</td>\n\t    <td class=\"whitegrayrightalign\">");
/* 1262 */         if (_jspx_meth_fmt_005fformatNumber_005f2(_jspx_th_c_005fwhen_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1263 */           return true;
/* 1264 */         out.write("</td>\n               ");
/* 1265 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 1266 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1270 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 1271 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1272 */       return true;
/*      */     }
/* 1274 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 1275 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f0(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1280 */     PageContext pageContext = _jspx_page_context;
/* 1281 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1283 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f0 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 1284 */     _jspx_th_fmt_005fformatNumber_005f0.setPageContext(_jspx_page_context);
/* 1285 */     _jspx_th_fmt_005fformatNumber_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1287 */     _jspx_th_fmt_005fformatNumber_005f0.setValue("${row[1]/(1024)}");
/* 1288 */     int _jspx_eval_fmt_005fformatNumber_005f0 = _jspx_th_fmt_005fformatNumber_005f0.doStartTag();
/* 1289 */     if (_jspx_th_fmt_005fformatNumber_005f0.doEndTag() == 5) {
/* 1290 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 1291 */       return true;
/*      */     }
/* 1293 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f0);
/* 1294 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f1(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1299 */     PageContext pageContext = _jspx_page_context;
/* 1300 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1302 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f1 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 1303 */     _jspx_th_fmt_005fformatNumber_005f1.setPageContext(_jspx_page_context);
/* 1304 */     _jspx_th_fmt_005fformatNumber_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1306 */     _jspx_th_fmt_005fformatNumber_005f1.setValue("${row[2]/(1024)}");
/* 1307 */     int _jspx_eval_fmt_005fformatNumber_005f1 = _jspx_th_fmt_005fformatNumber_005f1.doStartTag();
/* 1308 */     if (_jspx_th_fmt_005fformatNumber_005f1.doEndTag() == 5) {
/* 1309 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 1310 */       return true;
/*      */     }
/* 1312 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f1);
/* 1313 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f2(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1318 */     PageContext pageContext = _jspx_page_context;
/* 1319 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1321 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f2 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 1322 */     _jspx_th_fmt_005fformatNumber_005f2.setPageContext(_jspx_page_context);
/* 1323 */     _jspx_th_fmt_005fformatNumber_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 1325 */     _jspx_th_fmt_005fformatNumber_005f2.setValue("${row[3]/(1024)}");
/* 1326 */     int _jspx_eval_fmt_005fformatNumber_005f2 = _jspx_th_fmt_005fformatNumber_005f2.doStartTag();
/* 1327 */     if (_jspx_th_fmt_005fformatNumber_005f2.doEndTag() == 5) {
/* 1328 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 1329 */       return true;
/*      */     }
/* 1331 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f2);
/* 1332 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1337 */     PageContext pageContext = _jspx_page_context;
/* 1338 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1340 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1341 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1342 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 1343 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1344 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1346 */         out.write("                  \n        \t              <td class=\"whitegrayrightalign\">");
/* 1347 */         if (_jspx_meth_fmt_005fformatNumber_005f3(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1348 */           return true;
/* 1349 */         out.write("</td>\n\t                      <td class=\"whitegrayrightalign\">");
/* 1350 */         if (_jspx_meth_fmt_005fformatNumber_005f4(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1351 */           return true;
/* 1352 */         out.write("</td>\n\t                      <td class=\"whitegrayrightalign\">");
/* 1353 */         if (_jspx_meth_fmt_005fformatNumber_005f5(_jspx_th_c_005fotherwise_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1354 */           return true;
/* 1355 */         out.write("</td>\n               ");
/* 1356 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1357 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1361 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1362 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1363 */       return true;
/*      */     }
/* 1365 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1366 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f3(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1371 */     PageContext pageContext = _jspx_page_context;
/* 1372 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1374 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f3 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 1375 */     _jspx_th_fmt_005fformatNumber_005f3.setPageContext(_jspx_page_context);
/* 1376 */     _jspx_th_fmt_005fformatNumber_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1378 */     _jspx_th_fmt_005fformatNumber_005f3.setValue("${row[1]/(1024*1024)}");
/* 1379 */     int _jspx_eval_fmt_005fformatNumber_005f3 = _jspx_th_fmt_005fformatNumber_005f3.doStartTag();
/* 1380 */     if (_jspx_th_fmt_005fformatNumber_005f3.doEndTag() == 5) {
/* 1381 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f3);
/* 1382 */       return true;
/*      */     }
/* 1384 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f3);
/* 1385 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f4(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1390 */     PageContext pageContext = _jspx_page_context;
/* 1391 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1393 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f4 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 1394 */     _jspx_th_fmt_005fformatNumber_005f4.setPageContext(_jspx_page_context);
/* 1395 */     _jspx_th_fmt_005fformatNumber_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1397 */     _jspx_th_fmt_005fformatNumber_005f4.setValue("${row[2]/(1024*1024)}");
/* 1398 */     int _jspx_eval_fmt_005fformatNumber_005f4 = _jspx_th_fmt_005fformatNumber_005f4.doStartTag();
/* 1399 */     if (_jspx_th_fmt_005fformatNumber_005f4.doEndTag() == 5) {
/* 1400 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f4);
/* 1401 */       return true;
/*      */     }
/* 1403 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f4);
/* 1404 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f5(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1409 */     PageContext pageContext = _jspx_page_context;
/* 1410 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1412 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f5 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 1413 */     _jspx_th_fmt_005fformatNumber_005f5.setPageContext(_jspx_page_context);
/* 1414 */     _jspx_th_fmt_005fformatNumber_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1416 */     _jspx_th_fmt_005fformatNumber_005f5.setValue("${row[3]/(1024*1024)}");
/* 1417 */     int _jspx_eval_fmt_005fformatNumber_005f5 = _jspx_th_fmt_005fformatNumber_005f5.doStartTag();
/* 1418 */     if (_jspx_th_fmt_005fformatNumber_005f5.doEndTag() == 5) {
/* 1419 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f5);
/* 1420 */       return true;
/*      */     }
/* 1422 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f5);
/* 1423 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1428 */     PageContext pageContext = _jspx_page_context;
/* 1429 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1431 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 1432 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 1433 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1435 */     _jspx_th_c_005fif_005f1.setTest("${ReportForm.unit !='MB'}");
/* 1436 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 1437 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 1439 */         out.write("\n\n   \t    <td class=\"whitegrayrightalign\">");
/* 1440 */         if (_jspx_meth_fmt_005fformatNumber_005f6(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1441 */           return true;
/* 1442 */         out.write("</td>\n            <td class=\"whitegrayrightalign\">");
/* 1443 */         if (_jspx_meth_fmt_005fformatNumber_005f7(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1444 */           return true;
/* 1445 */         out.write("</td>\n            <td class=\"whitegrayrightalign\">");
/* 1446 */         if (_jspx_meth_fmt_005fformatNumber_005f8(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 1447 */           return true;
/* 1448 */         out.write("</td>\n");
/* 1449 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 1450 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1454 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 1455 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1456 */       return true;
/*      */     }
/* 1458 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 1459 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f6(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1464 */     PageContext pageContext = _jspx_page_context;
/* 1465 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1467 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f6 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 1468 */     _jspx_th_fmt_005fformatNumber_005f6.setPageContext(_jspx_page_context);
/* 1469 */     _jspx_th_fmt_005fformatNumber_005f6.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1471 */     _jspx_th_fmt_005fformatNumber_005f6.setValue("${row[1]}");
/* 1472 */     int _jspx_eval_fmt_005fformatNumber_005f6 = _jspx_th_fmt_005fformatNumber_005f6.doStartTag();
/* 1473 */     if (_jspx_th_fmt_005fformatNumber_005f6.doEndTag() == 5) {
/* 1474 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f6);
/* 1475 */       return true;
/*      */     }
/* 1477 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f6);
/* 1478 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f7(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1483 */     PageContext pageContext = _jspx_page_context;
/* 1484 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1486 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f7 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 1487 */     _jspx_th_fmt_005fformatNumber_005f7.setPageContext(_jspx_page_context);
/* 1488 */     _jspx_th_fmt_005fformatNumber_005f7.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1490 */     _jspx_th_fmt_005fformatNumber_005f7.setValue("${row[2]}");
/* 1491 */     int _jspx_eval_fmt_005fformatNumber_005f7 = _jspx_th_fmt_005fformatNumber_005f7.doStartTag();
/* 1492 */     if (_jspx_th_fmt_005fformatNumber_005f7.doEndTag() == 5) {
/* 1493 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f7);
/* 1494 */       return true;
/*      */     }
/* 1496 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f7);
/* 1497 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatNumber_005f8(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1502 */     PageContext pageContext = _jspx_page_context;
/* 1503 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1505 */     FormatNumberTag _jspx_th_fmt_005fformatNumber_005f8 = (FormatNumberTag)this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.get(FormatNumberTag.class);
/* 1506 */     _jspx_th_fmt_005fformatNumber_005f8.setPageContext(_jspx_page_context);
/* 1507 */     _jspx_th_fmt_005fformatNumber_005f8.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 1509 */     _jspx_th_fmt_005fformatNumber_005f8.setValue("${row[3]}");
/* 1510 */     int _jspx_eval_fmt_005fformatNumber_005f8 = _jspx_th_fmt_005fformatNumber_005f8.doStartTag();
/* 1511 */     if (_jspx_th_fmt_005fformatNumber_005f8.doEndTag() == 5) {
/* 1512 */       this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f8);
/* 1513 */       return true;
/*      */     }
/* 1515 */     this._005fjspx_005ftagPool_005ffmt_005fformatNumber_0026_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fformatNumber_005f8);
/* 1516 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1521 */     PageContext pageContext = _jspx_page_context;
/* 1522 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1524 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1525 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 1526 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1528 */     _jspx_th_c_005fout_005f9.setValue("${row[4]}");
/* 1529 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 1530 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 1531 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1532 */       return true;
/*      */     }
/* 1534 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 1535 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 1540 */     PageContext pageContext = _jspx_page_context;
/* 1541 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1543 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 1544 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 1545 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 1547 */     _jspx_th_c_005fout_005f10.setValue("${row[7]}");
/* 1548 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 1549 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 1550 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1551 */       return true;
/*      */     }
/* 1553 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 1554 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\PerformanceReport_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */