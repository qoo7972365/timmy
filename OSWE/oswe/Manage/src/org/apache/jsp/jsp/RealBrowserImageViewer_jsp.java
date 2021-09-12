/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*     */ 
/*     */ public final class RealBrowserImageViewer_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  20 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  26 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(2);
/*  27 */   static { _jspx_dependants.put("/jsp/includes/RBMUtil.jspf", Long.valueOf(1473429417000L));
/*  28 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*     */   }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  44 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  57 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  61 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  62 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/*  63 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  64 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  65 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  66 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  67 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.release();
/*  68 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
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
/*  95 */       out.write("<!--$Id$-->\n<!DOCTYPE html>\n\n\n\n\n");
/*  96 */       java.util.Date myDate = null;
/*  97 */       myDate = (java.util.Date)_jspx_page_context.getAttribute("myDate", 1);
/*  98 */       if (myDate == null) {
/*  99 */         myDate = new java.util.Date();
/* 100 */         _jspx_page_context.setAttribute("myDate", myDate, 1);
/*     */       }
/* 102 */       out.write(32);
/* 103 */       out.write(10);
/* 104 */       java.util.Date myDate2 = null;
/* 105 */       myDate2 = (java.util.Date)_jspx_page_context.getAttribute("myDate2", 1);
/* 106 */       if (myDate2 == null) {
/* 107 */         myDate2 = new java.util.Date();
/* 108 */         _jspx_page_context.setAttribute("myDate2", myDate2, 1);
/*     */       }
/* 110 */       out.write(" \n<script type=\"text/javascript\" src=\"/template/highcharts.js\"></script>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/RBM_style.css\">\n\n");
/* 111 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/* 113 */       out.write("\n\n<html>\n<head>\n\t<title>");
/* 114 */       out.print(FormatUtil.getString("am.selenium.imagePage.title"));
/* 115 */       out.write("</title>\n\t");
/* 116 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 117 */       out.write(10);
/* 118 */       out.write(9);
/* 119 */       out.write("<!--$Id$-->\n\n\n\t<script>\n\t   function generatePerfMets(perfMetrics)\n\t   {\n\t   \t    var imgDom = new Array();\n\t\t\t");
/* 120 */       if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*     */         return;
/* 122 */       out.write("\n\t\t\tvar slides = document.getElementById(\"slides\");\n\t\t\tvar i=0;\n\t\t\tfor(var step in perfMetrics)\n\t\t\t{\n\t\t\t\tvar stepPerf = perfMetrics[step];\n\t\t\t\tif(stepPerf.navigationStart == undefined)\n\t\t\t\t{\n\t\t\t\t\tcontinue;\n\t\t\t\t}\n\t\t\t\tvar pageLoadTime = stepPerf.loadEventEnd-stepPerf.navigationStart;\n\t\t\t\t//totalDur is used for generating the waterfall graph. Adding 1000ms extra as a buffer for showing the total time taken by the resource by the side and for better readability. \n\t\t\t\ttotalDur=pageLoadTime+1000;\n\t\t\t\tvar slideDiv = document.createElement(\"div\"); \n\t\t\t\tvar table = document.createElement(\"table\");\n\t\t\t\ttable.className=\"PerfTable\";\n\t\t\t\tvar caption = document.createElement(\"caption\");\n\t\t\t\tcaption.innerHTML=stepPerf.stepId;\n\t\t\t\ttable.appendChild(caption);\n\t\t\t\tvar tr = document.createElement(\"tr\");\n\t\t\t\ttr.className=\"highlight\";\n\t\t\t\tvar td1 = document.createElement(\"td\");\n\t\t\t\tvar td2 = document.createElement(\"td\");\n\t\t\t\tvar td3 = document.createElement(\"td\");\n\t\t\t\tvar td4 = document.createElement(\"td\");\n\t\t\t\tvar td5 = document.createElement(\"td\");\n");
/* 123 */       out.write("\t\t\t\ttd1.innerHTML='");
/* 124 */       out.print(FormatUtil.getString("am.webclient.camscreen.attributegraphs.resourcename.text"));
/* 125 */       out.write("';\n\t\t\t\ttd1.style.width=\"15%\";\n\t\t\t\ttd2.innerHTML='");
/* 126 */       out.print(FormatUtil.getString("Status Code"));
/* 127 */       out.write("'; \n\t\t\t\ttd2.style.width=\"7%\";\n\t\t\t\ttd3.innerHTML='");
/* 128 */       out.print(FormatUtil.getString("am.webclient.mssqldetails.sizeinkb"));
/* 129 */       out.write("'; \n\t\t\t\ttd3.style.width=\"5%\";\n\t\t\t\ttd4.innerHTML='");
/* 130 */       out.print(FormatUtil.getString("Content Type"));
/* 131 */       out.write("'; \n\t\t\t\ttd4.style.width=\"7%\";\n\t\t\t\tvar timelineWidth=winWidth;\n\t\t\t\ttd5.innerHTML=createTimeline(timelineWidth,totalDur);\n\t\t\t\ttr.appendChild(td1);\n\t\t\t\ttr.appendChild(td2);\n\t\t\t\ttr.appendChild(td3);\n\t\t\t\ttr.appendChild(td4);\n\t\t\t\ttr.appendChild(td5);\n\t\t\t\ttable.appendChild(tr);\n\n\t\t\t\tvar rowEle = createRow(stepPerf.name,stepPerf.responseStatus+\" \"+stepPerf.responseText,stepPerf.contentLength/1000,stepPerf.contentType,0,stepPerf.redirectionTime,stepPerf.dnsTime,stepPerf.connectionTime,stepPerf.sslTime,stepPerf.networkLatency,stepPerf.downloadTime,stepPerf.stallTime,0,timelineWidth,totalDur);\n\t\t\t\ttable.appendChild(rowEle);\n\t\t\t\tvar othersCount = 0,cssCount = 0,scriptCount = 0,htmlCount = 0,imagesCount = 0;\n\t\t\t\tvar othersSize = 0,cssSize = 0,totalSize = 0,scriptSize = 0,htmlSize = 0,imagesSize = 0;\n\t\t\t\tvar othersFiles =new Map();\n\t\t\t\tvar cssFiles = new Map();\n\t\t\t\tvar scriptFiles = new Map();\n\t\t\t\tvar htmlFiles = new Map();\n\t\t\t\tvar imagesFiles = new Map();\n\t\t\t\tif(stepPerf.contentType && stepPerf.contentType.indexOf(\"html\") != -1)//NO I18N\n");
/* 132 */       out.write("\t\t\t\t{\n\t\t\t\t\thtmlCount++;\n\t\t\t\t\thtmlSize+=stepPerf.contentLength/1000;\n\t\t\t\t\thtmlFiles.set(stepPerf.stepId,stepPerf.contentLength);\n\t\t\t\t}\n\t\t\t\t\n\t\t\t\tvar navStart = stepPerf.navigationStart;\n\t\t\t\tvar domContentLoadedEventStart = stepPerf.domContentLoadedEventStart - stepPerf.navigationStart;\n\t\t\t\tvar loadEventStart = stepPerf.loadEventStart - stepPerf.navigationStart;\n\t\t\t\tfor(var res in stepPerf['resourceMetrics'])\n\t\t\t\t{\n\t\t\t\t\tvar resObj = stepPerf['resourceMetrics'][res];\n\t\t\t\t\tif(resObj.startTime !== undefined)\n\t\t\t\t\t{\n\t\t\t\t\t\tif(resObj.networkLatency != -1)\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\tvar rowEle = createRow(resObj.name,resObj.responseStatus+\" \"+resObj.responseText,resObj.contentLength/1000,resObj.contentType,resObj.startTime,resObj.redirectionTime,resObj.dnsTime,resObj.connectionTime,resObj.sslTime,resObj.networkLatency,resObj.downloadTime,resObj.stallTime,0,timelineWidth,totalDur);\n\t\t\t\t\t\t\ttable.appendChild(rowEle);\n\t\t\t\t\t\t}\n\t\t\t\t\t\telse\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\tvar rowEle = createRow(resObj.name,resObj.responseStatus+\" \"+resObj.responseText,resObj.contentLength/1000,resObj.contentType,resObj.startTime,0,0,0,0,0,0,0,resObj.duration,timelineWidth,totalDur);\n");
/* 133 */       out.write("\t\t\t\t\t\t\ttable.appendChild(rowEle);\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t\tif(resObj.contentType)\n\t\t\t\t\t{\n\t\t\t\t\t\tresObj.contentLength=resObj.contentLength==-1?0:(resObj.contentLength/1000);\n\t\t\t\t\t\tif(resObj.contentType.indexOf(\"html\") != -1)//NO I18N\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\thtmlCount++;\n\t\t\t\t\t\t\thtmlSize+=resObj.contentLength;\n\t\t\t\t\t\t\thtmlFiles.set(res,resObj.contentLength);\n\t\t\t\t\t\t}\n\t\t\t\t\t\telse if(resObj.contentType.indexOf(\"css\") != -1)\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\tcssCount++;\n\t\t\t\t\t\t\tcssSize+=resObj.contentLength;\n\t\t\t\t\t\t\tcssFiles.set(res,resObj.contentLength);\n\t\t\t\t\t\t}\n\t\t\t\t\t\telse if(resObj.contentType.indexOf(\"javascript\") != -1)\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\tscriptCount++;\n\t\t\t\t\t\t\tscriptSize+=resObj.contentLength;\n\t\t\t\t\t\t\tscriptFiles.set(res,resObj.contentLength);\n\t\t\t\t\t\t}\n\t\t\t\t\t\telse if(resObj.contentType.indexOf(\"image\") != -1)//No I18N\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\timagesCount++;\n\t\t\t\t\t\t\timagesSize+=resObj.contentLength;\n\t\t\t\t\t\t\timagesFiles.set(res,resObj.contentLength);\n\t\t\t\t\t\t}\n\t\t\t\t\t\telse\n\t\t\t\t\t\t{\n\t\t\t\t\t\t\tothersCount++;\n\t\t\t\t\t\t\tothersSize+=resObj.contentLength;\n\t\t\t\t\t\t\tothersFiles.set(res,resObj.contentLength);\n");
/* 134 */       out.write("\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t}\n\t\t\ttotalSize = othersSize+cssSize+scriptSize+htmlSize+imagesSize; \n\t\t\t\n\t\t\t\n\t\t\t\tvar legendRow = document.createElement(\"tr\");\n\t\t\t\tlegendRow.innerHTML=\"<td colspan=\\\"4\\\"></td>\";\n\t\t\t\tvar legend = document.createElement(\"td\");\n\t\t\t\tlegend.innerHTML=createLegend();\n\t\t\t\tlegendRow.appendChild(legend);\n\t\t\t\ttable.appendChild(legendRow);\n\n\t\t\t\tvar  row = document.createElement(\"tr\");\n\t\t\t\trow.className=\"highlight\";\n\t\t\t\tvar totalReq = document.createElement(\"td\");\n\t\t\t\ttotalReq.colSpan=\"2\";\n\t\t\t\ttotalReq.innerHTML='");
/* 135 */       out.print(FormatUtil.getString("Total Requests"));
/* 136 */       out.write(" : '+(stepPerf['resourceMetrics'].length+1); //No I18N\n\t\t\t\tvar totalSizetd = document.createElement(\"td\");\n\t\t\t\ttotalSizetd.innerHTML=parseFloat(totalSize).toFixed(2)+\"KB\"; //No I18N\n\t\t\t\tvar totalTimetd = document.createElement(\"td\");\n\t\t\t\ttotalTimetd.colSpan=\"2\";\n\t\t\t\ttotalTimetd.align=\"right\";\n\t\t\t\ttotalTimetd.innerHTML=pageLoadTime+\"ms\"; //No I18N\n\t\t\t\trow.appendChild(totalReq);\n\t\t\t\trow.appendChild(totalSizetd);\n\t\t\t\trow.appendChild(totalTimetd);\n\t\t\t\ttable.appendChild(row);\n\t\t\t\tslideDiv.appendChild(table);\n\t\t\t\n\t\t\t");
/* 137 */       if (_jspx_meth_c_005fchoose_005f0(_jspx_page_context))
/*     */         return;
/* 139 */       out.write("\n      \t\t\tvar piechart = new Highcharts.Chart({\n      \t\t\t\t        chart: {\n      \t\t\t\t        \trenderTo:step+'_ressize', //NO I18N\n    \t\t\t\t            plotBackgroundColor: null,\n                \t            plotBorderWidth: null,\n                \t            plotShadow: false,\n                \t            type: 'pie' //NO I18N\n            \t            },\n            \t            title: {\n                \t            text: 'Resource size %' //NO I18N\n            \t            },\n            \t            tooltip: {\n                \t            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>' //NO I18N\n            \t            },\n            \t            plotOptions: {\n                \t            pie: {\n\t                                allowPointSelect: true,\n                \t                cursor: 'pointer', //NO I18N\n                    \t            dataLabels: {\n                        \t            enabled: false\n                    \t            },\n                    \t            showInLegend: true\n");
/* 140 */       out.write("                \t            }\n            \t            },\n            \t            series: [{\n \t                           name: 'Size', //NO I18N\n \t                           colorByPoint: true,\n                \t            data: [{\n                    \t            name: '");
/* 141 */       out.print(FormatUtil.getString("Others"));
/* 142 */       out.write("',\n                    \t            y: othersSize\n                \t            }, {\n                    \t            name: '");
/* 143 */       out.print(FormatUtil.getString("CSS"));
/* 144 */       out.write("',\n                    \t            y: cssSize\n                    \t        }, {\n\t                                name: '");
/* 145 */       out.print(FormatUtil.getString("am.monitortab.tableview.script.text"));
/* 146 */       out.write("',\n \t                               y: scriptSize\n \t                           }, {\n \t                               name: '");
/* 147 */       out.print(FormatUtil.getString("am.webclient.newaction.html"));
/* 148 */       out.write("', \n  \t                              y: htmlSize\n                \t            }, {\n  \t                              name: '");
/* 149 */       out.print(FormatUtil.getString("am.webclient.image.text"));
/* 150 */       out.write("',\n \t                               y: imagesSize\n \t                           }]\n   \t                     }]\n      \t\t\t});\n\t\t\t\tvar piechart2 = new Highcharts.Chart({\n      \t\t\t\t        chart: {\n      \t\t\t\t        \trenderTo:step+'_rescount', //NO I18N\n    \t\t\t\t            plotBackgroundColor: null,\n                \t            plotBorderWidth: null,\n                \t            plotShadow: false,\n                \t            type: 'pie' //NO I18N\n            \t            },\n            \t            title: {\n                \t            text: 'Resource count %' //NO I18N\n            \t            },\n            \t            tooltip: {\n                \t            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>' //NO I18N\n            \t            },\n            \t            plotOptions: {\n                \t            pie: {\n\t                                allowPointSelect: true,\n                \t                cursor: 'pointer', //NO I18N\n                    \t            dataLabels: {\n                        \t            enabled: false\n");
/* 151 */       out.write("                    \t            },\n                    \t            showInLegend: true\n                \t            }\n            \t            },\n            \t            series: [{\n \t                           name: 'Count', //NO I18N\n \t                           colorByPoint: true,\n                \t            data: [{\n                    \t            name: '");
/* 152 */       out.print(FormatUtil.getString("Others"));
/* 153 */       out.write("',\n                    \t            y: othersCount\n                \t            }, {\n                    \t            name: '");
/* 154 */       out.print(FormatUtil.getString("CSS"));
/* 155 */       out.write("',\n                    \t            y: cssCount\n                    \t        }, {\n\t                                name: '");
/* 156 */       out.print(FormatUtil.getString("am.monitortab.tableview.script.text"));
/* 157 */       out.write("',\n \t                               y: scriptCount\n \t                           }, {\n \t                               name: '");
/* 158 */       out.print(FormatUtil.getString("am.webclient.newaction.html"));
/* 159 */       out.write("',\n  \t                              y: htmlCount\n                \t            }, {\n  \t                              name: '");
/* 160 */       out.print(FormatUtil.getString("am.webclient.image.text"));
/* 161 */       out.write("',\n \t                               y: imagesCount\n \t                           }]\n   \t                     }]\n      \t\t\t});\n\t\t\t}\n\n\t   }\n\t</script>\n\t<script type=\"text/javascript\">\n\tfunction createTimeline(width,totalTime)\n\t{\n\t\tvar tableDiv = document.createElement(\"div\");\n\t\tvar table = document.createElement(\"table\");\n\t\tvar tr = document.createElement(\"tr\");\n\t\tvar timelineRow=document.createElement(\"tr\");\n\t\tvar timelineValue=document.createElement(\"tr\");\n      \tvar intervalSpace = Math.round(width/11);\n\t\tvar intervalsCount=10;\n\t\tvar lineRowContent=\"\",timesRowContent=\"\";\n        for(var i=0;i<intervalsCount;i++)\n        {\n          lineRowContent+=\"<td class=\\\"xaxis\\\"></td>\";\n          timesRowContent+=\"<td class=\\\"timeline\\\">\"+Math.round(i*totalTime/10)+\"</td>\";\n        }\n        timelineRow.innerHTML=lineRowContent;\n        timelineValue.innerHTML=timesRowContent;\n        table.style.width=\"100%\";\n        table.appendChild(tr);\n        table.appendChild(timelineRow);\n        table.appendChild(timelineValue);\n");
/* 162 */       out.write("        tableDiv.appendChild(table);\n        return tableDiv.innerHTML;\n  \t}\n\tfunction createLegend()\n\t{\n\t\tvar listDiv=document.createElement(\"div\");\n\t\tvar list = document.createElement(\"ul\");\n\t\tlist.className=\"list\";\n\t\tlist.style.listStyle=\"none\"; //No I18N\n\t\tlist.appendChild(createListElement(\"3%\",\"rdt\")); //No I18N\n\t\tlist.appendChild(createLegendName('");
/* 163 */       out.print(FormatUtil.getString("am.webclient.rbm.wpa.redirection.time"));
/* 164 */       out.write("'));\n\t\tlist.appendChild(createListElement(\"3%\",\"dns\")); //No I18N\n\t\tlist.appendChild(createLegendName('");
/* 165 */       out.print(FormatUtil.getString("am.webclient.rbm.wpa.dns.time"));
/* 166 */       out.write("'));\n\t\tlist.appendChild(createListElement(\"3%\",\"tcp\")); //No I18N\n\t\tlist.appendChild(createLegendName('");
/* 167 */       out.print(FormatUtil.getString("am.webclient.rbm.wpa.connect.time"));
/* 168 */       out.write("'));\n\t\tlist.appendChild(createListElement(\"3%\",\"ssl\")); //No I18N\n\t\tlist.appendChild(createLegendName('");
/* 169 */       out.print(FormatUtil.getString("am.webclient.rbm.wpa.ssl.time"));
/* 170 */       out.write("'));\n\t\tlist.appendChild(createListElement(\"3%\",\"nl\")); //No I18N\n\t\tlist.appendChild(createLegendName('");
/* 171 */       out.print(FormatUtil.getString("am.webclient.rbm.wpa.network.time"));
/* 172 */       out.write("')); //No I18N\n\t\tlist.appendChild(createListElement(\"3%\",\"cd\")); //No I18N\n\t\tlist.appendChild(createLegendName('");
/* 173 */       out.print(FormatUtil.getString("am.webclient.rbm.wpa.download.time"));
/* 174 */       out.write("')); //No I18N\n\t\tlist.appendChild(createListElement(\"3%\",\"stall\")); //No I18N\n\t\tlist.appendChild(createLegendName('");
/* 175 */       out.print(FormatUtil.getString("am.webclient.rbm.wpa.block.time"));
/* 176 */       out.write("')); //No I18N\n\t\tlist.appendChild(createListElement(\"3%\",\"dur\")); //No I18N\n\t\tlist.appendChild(createLegendName('");
/* 177 */       out.print(FormatUtil.getString("am.webclient.rbm.wpa.duration"));
/* 178 */       out.write("')); //No I18N\n\t\tlistDiv.appendChild(list);\n\t\treturn listDiv.innerHTML;\t\t\n\t}\n\tfunction createLegendName(name)\n\t{\n\t\tvar ele = document.createElement(\"li\");\n\t\tele.innerHTML=name;\n\t\tele.style.width=\"30%\";\n\t\treturn ele;\n\t}\n\tfunction createListElement(width,className)\n\t{\n\t\tvar ele = document.createElement(\"li\");\n\t\tele.style.width=width;\n\t\tele.className=className;\n\t\treturn ele;\n\t}\n\tfunction createList(name,st,rdt,dns,tcp,ssl,nl,cd,stall,dur,width,totalDur)\n\t{\n\t\tvar listDiv=document.createElement(\"div\");\n\t\tvar toolTipTag = document.createElement(\"a\");\n\t\ttoolTipTag.className=\"tooltips\";\n\t\ttoolTipTag.href=\"#\";\n\t\tvar list = document.createElement(\"ul\");\n\t\tlist.className=\"list\";\n\t\tlist.style.listStyle=\"none\"; //No I18N\n\t\tlist.appendChild(createListElement((st*width)/totalDur+\"px\",\"st\")); //No I18N\n\t\tlist.appendChild(createListElement((rdt*width)/totalDur+\"px\",\"rdt\")); //No I18N\n\t\tlist.appendChild(createListElement((dns*width)/totalDur+\"px\",\"dns\")); //No I18N\n\t\tlist.appendChild(createListElement((tcp*width)/totalDur+\"px\",\"tcp\")); //No I18N\n");
/* 179 */       out.write("\t\tlist.appendChild(createListElement((ssl*width)/totalDur+\"px\",\"ssl\")); //No I18N\n\t\tlist.appendChild(createListElement((stall*width)/totalDur+\"px\",\"stall\")); //No I18N\n\t\tlist.appendChild(createListElement((nl*width)/totalDur+\"px\",\"nl\")); //No I18N\n\t\tlist.appendChild(createListElement((cd*width)/totalDur+\"px\",\"cd\")); //No I18N\n\t\tlist.appendChild(createListElement((dur*width)/totalDur+\"px\",\"dur\")); //No I18N\n\t\tvar resLoadTime=document.createElement('li');\n\t\tresLoadTime.innerHTML=(rdt+dns+tcp+ssl+nl+cd+stall+dur)+\" ms\"; //No I18N\n\t\tlist.appendChild(resLoadTime);\n\t\ttoolTipTag.appendChild(list);\n\t\tvar toolTipSpan = document.createElement(\"span\");\n\t\t//st,rdt,dns,tcp,ssl,nl,cd,stall,dur,width,totalDur\n\t\tif(dur==0)\n\t\t{\n\t\t\ttoolTipSpan.innerHTML=name.substring(0,20)+\"....\"+name.substring(name.length-20,name.length)+\"<br/>Start Time : \"+st+\"ms <br />Redirection Time : \"+rdt+\"ms<br />Domain LookUp Time : \"+dns+\"ms<br />Connection Time : \"+tcp+\"ms<br />Secure Connection Time : \"+ssl+\"ms<br />Network Latency : \"+nl+\"ms<br />Download Time : \"+cd+\"ms<br />Blocking Time : \"+stall+\"ms\"; //No I18N\n");
/* 180 */       out.write("\t\t}\n\t\telse\n\t\t{\n\t\t\ttoolTipSpan.innerHTML=name+\"<br/>Start Time : \"+st+\"ms <br />Duration : \"+dur+\"ms\"; //No I18N\n\t\t}\n\t\ttoolTipTag.appendChild(toolTipSpan);\n\t\tlistDiv.appendChild(toolTipTag);\n\t\treturn listDiv.innerHTML;\n\t}\n\tfunction createRow(name,status,size,type,st,rdt,dns,tcp,ssl,nl,cd,stall,dur,width,totalDur)\n\t{\n\t\tvar row = document.createElement(\"tr\");\n\t\tvar resName=document.createElement(\"td\");\n\t\tvar resStatus=document.createElement(\"td\");\n\t\tvar resSize=document.createElement(\"td\");\n\t\tvar resType=document.createElement(\"td\");\n\t\tvar resTime=document.createElement(\"td\");\n\t\tvar nameTemp=(name.length>40)?name.substring(0,20)+\"....\"+name.substring(name.length-20,name.length):name;\n\t\tresName.innerHTML=\"<span title=\\\"\"+name+\"\\\" style=\\\"word-wrap:break-word;\\\">\"+nameTemp+\"</span>\";\n\t\tresStatus.innerHTML=status;\n\t\tresSize.innerHTML=parseFloat(size).toFixed(2);\n\t\tresType.innerHTML=type;\n\t\tresType.style.wordWrap = \"break-word\"; //No I18N\n\t\tvar list = createList(name,Math.abs(Math.round(st)),Math.abs(Math.round(rdt)),Math.abs(Math.round(dns)),Math.abs(Math.round(tcp)),Math.abs(Math.round(ssl)),Math.abs(Math.round(nl)),Math.abs(Math.round(cd)),Math.abs(Math.round(stall)),Math.abs(Math.round(dur)),width,totalDur);\n");
/* 181 */       out.write("\t\tresTime.innerHTML=list;\n\t\trow.appendChild(resName);\n\t\trow.appendChild(resStatus);\n\t\trow.appendChild(resSize);\n\t\trow.appendChild(resType);\n\t\trow.appendChild(resTime);\n\t\treturn row;\n\t\t\t\t\n\t}\n</script>\n");
/* 182 */       out.write("\n\t\t<script>\n\t\tvar winWidth=\"\";\n        winWidth = jQuery(window).width();\n\t\twinWidth = (winWidth/100)*81;\n\t\twinWidth = winWidth/1.50;\n\t</script>\n</head>\n<body>\n\t<div class=\"apmconf-table-frame sliderScreen\">\n\t  <div class=\"sliderHead\">\n\t  ");
/*     */       
/* 184 */       ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 185 */       _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 186 */       _jspx_th_c_005fchoose_005f1.setParent(null);
/* 187 */       int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 188 */       if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*     */         for (;;) {
/* 190 */           out.write("\n\t  ");
/*     */           
/* 192 */           WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 193 */           _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 194 */           _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*     */           
/* 196 */           _jspx_th_c_005fwhen_005f1.setTest("${not empty images}");
/* 197 */           int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 198 */           if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*     */             for (;;) {
/* 200 */               out.write("\n\t  \t ");
/* 201 */               if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*     */                 return;
/* 203 */               out.write("\n         <span class=\"conf-mon-txt\">");
/* 204 */               out.print(FormatUtil.getString("am.selenium.screenshots"));
/* 205 */               out.write(32);
/* 206 */               if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/*     */                 return;
/* 208 */               out.write("</span>\n\t  \t <div class=\"screenHistory\">\n\t\t\t");
/*     */               
/* 210 */               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 211 */               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 212 */               _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/* 213 */               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 214 */               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*     */                 for (;;) {
/* 216 */                   out.write("\n\t\t\t");
/*     */                   
/* 218 */                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 219 */                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 220 */                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*     */                   
/* 222 */                   _jspx_th_c_005fwhen_005f2.setTest("${not showHistory}");
/* 223 */                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 224 */                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*     */                     for (;;) {
/* 226 */                       out.write("\n\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onclick=\"openWindow('/SeleniumActions.do?method=showScreenShots&resourceid=");
/* 227 */                       if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f2, _jspx_page_context))
/*     */                         return;
/* 229 */                       out.write("&showHistory=true','annotate','1000','500')\">");
/* 230 */                       out.print(FormatUtil.getString("am.selenium.show.screenshots.history"));
/* 231 */                       out.write("</a>\n\t\t\t");
/* 232 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 233 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 237 */                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 238 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*     */                   }
/*     */                   
/* 241 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 242 */                   out.write("\n\t\t\t");
/*     */                   
/* 244 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 245 */                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 246 */                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f2);
/* 247 */                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 248 */                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*     */                     for (;;) {
/* 250 */                       out.write("\n\t\t\t\t\t\n\t\t\t        <span>");
/* 251 */                       out.print(FormatUtil.getString("am.webclient.conf.selectDate"));
/* 252 */                       out.write(" : </span>\n\t\t\t        \t<select name=\"sTime\" onChange=\"javascript:loadScreen(this.value)\">\n\t\t\t\t\t\t\t\t");
/* 253 */                       if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*     */                         return;
/* 255 */                       out.write("\n\t\t\t\t\t\t\t</select>\n\t\t\t");
/* 256 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 257 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 261 */                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 262 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*     */                   }
/*     */                   
/* 265 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 266 */                   out.write("\t\n\t\t\t");
/* 267 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 268 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 272 */               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 273 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*     */               }
/*     */               
/* 276 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 277 */               out.write(10);
/* 278 */               out.write(9);
/* 279 */               out.write(9);
/* 280 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 281 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 285 */           if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 286 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*     */           }
/*     */           
/* 289 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 290 */           out.write("\n\t  \t");
/*     */           
/* 292 */           OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 293 */           _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 294 */           _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f1);
/* 295 */           int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 296 */           if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*     */             for (;;) {
/* 298 */               out.write("\n\t\t  \t");
/*     */               
/* 300 */               IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 301 */               _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 302 */               _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fotherwise_005f2);
/*     */               
/* 304 */               _jspx_th_c_005fif_005f1.setTest("${not empty perfFileLoc}");
/* 305 */               int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 306 */               if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */                 for (;;) {
/* 308 */                   out.write("\n\t\t  \t\t<span class=\"conf-mon-txt\">");
/* 309 */                   out.print(FormatUtil.getString("am.webclient.customattribute.attribute.text"));
/* 310 */                   out.write("</span>\n\t  \t\t");
/* 311 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 312 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 316 */               if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 317 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*     */               }
/*     */               
/* 320 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 321 */               out.write("\n\t  \t");
/* 322 */               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 323 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 327 */           if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 328 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*     */           }
/*     */           
/* 331 */           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 332 */           out.write("\n\t  \t");
/* 333 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 334 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 338 */       if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 339 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*     */       }
/*     */       else {
/* 342 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 343 */         out.write("\t\n        </div>\n        \n        </div>\n            <div id=\"container\" class=\"maxwidth\">\n\t\t\t\t\t<div class=\"slidesContainer\">\n\t\t\t  \t\t");
/* 344 */         if (_jspx_meth_c_005fchoose_005f3(_jspx_page_context))
/*     */           return;
/* 346 */         out.write("\t\n\t\t\t\t\t</div>\t\n\t\t\t\t</div>\n     \t\t</div>\n     \t</div>\n</body>\n\n\t<script src=\"/template/jquery.slides.min.js\"></script>\n\t<script type=\"text/javascript\">\n\t\tvar slidesContainer = $('div.slidesContainer');\n\t\t$(function(){\n\t\t\t$('#slides').slidesjs({\n\t\t\t\twidth: 200,\n\t\t\t\theight: 100,\n\t\t        effect: {\n\t\t\t\t\tslide: {\n\t\t\t\t\t\tspeed: 1800\n\t\t\t\t\t}\n\t\t        },\n\t\t        pagination: {\n\t\t\t    \tactive: true,\n\t\t\t    \teffect: \"slide\"\t//NO I18N \n\t\t\t    },\n\t\t        navigation: {\n\t\t        \tactive: true,\n\t\t        \teffect: \"slide\"\t//NO I18N \n\t\t        \t},\n\t\t         start: 1,\n\t\t        play: {\n\t\t          auto: false,\n\t\t          pauseOnHover: true,\n\t\t          restartDelay: 2500\n\t\t        }\n\t\t\t});\n\t\t\t\n\t\t\t$('.slidesjs-container').height($('#slides').height());\n\t\t\t$('.slidesjs-container img').each(function() {\n\t\t\t\t$(this).height($('#slides').height()-30).width($('#slides').width());\n\t\t\t});\n\t\t\t\n\t\t\t$('div#slidesContainer').resize(function() {\n\t\t    \t$('.slidesjs-container img').each(function() {\n\t\t\t    \t$(this).height($('#slides').height()-30).width($('#slides').width());\n");
/* 347 */         out.write("\t\t\t\t});\n\t\t\t});\t\n\t\t});\n\tfunction loadScreen(sTime) {\n\t\turl=\"/SeleniumActions.do?method=showScreenShots&resourceid=");
/* 348 */         if (_jspx_meth_c_005fout_005f6(_jspx_page_context))
/*     */           return;
/* 350 */         out.write("&showHistory=true&sTime=\"+sTime; //No I18N\n\t\twindow.location.href=url;\n\t}\n  \t$(document).ready(function() {\n        var contHight = $('.sliderDiv.slidesjs-slide').height();\n\t\t$('.slidesjs-container,.slidesjs-control').css({'height':contHight}); //No I18N\n\t\tvar paginationTop = $('#slides').height()+10;\n\t\t$('ul.slidesjs-pagination').css({'top':-paginationTop}); //No I18N\n    });\n\t$(window).resize(function(){\n         var contHight2 = $('.sliderDiv.slidesjs-slide').height();\n\t\t$('.slidesjs-container,.slidesjs-control').css({'height':contHight2}); //No I18N\n    });\n</script>\t\n \n </html>\n");
/*     */       }
/* 352 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 353 */         out = _jspx_out;
/* 354 */         if ((out != null) && (out.getBufferSize() != 0))
/* 355 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 356 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 359 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 365 */     PageContext pageContext = _jspx_page_context;
/* 366 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 368 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 369 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 370 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 372 */     _jspx_th_c_005fif_005f0.setTest("${showHistory}");
/* 373 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 374 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 376 */         out.write("\n\t<link rel=\"stylesheet\" type=\"text/css\" href=\"/images/commonstyle.css\">\n");
/* 377 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 378 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 382 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 383 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 384 */       return true;
/*     */     }
/* 386 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 387 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 392 */     PageContext pageContext = _jspx_page_context;
/* 393 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 395 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 396 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 397 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*     */     
/* 399 */     _jspx_th_c_005fforEach_005f0.setItems("${images}");
/*     */     
/* 401 */     _jspx_th_c_005fforEach_005f0.setVar("current");
/* 402 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*     */     try {
/* 404 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 405 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*     */         for (;;) {
/* 407 */           out.write("\n\t\t\t\timgDom.push('<div style=\"height:400px;\"><img src=/projects/webscripts/ScreenShots/");
/* 408 */           if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 409 */             return true;
/* 410 */           out.write("></div>');\n\t\t\t");
/* 411 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 412 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 416 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 417 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 420 */         int tmp183_182 = 0; int[] tmp183_180 = _jspx_push_body_count_c_005fforEach_005f0; int tmp185_184 = tmp183_180[tmp183_182];tmp183_180[tmp183_182] = (tmp185_184 - 1); if (tmp185_184 <= 0) break;
/* 421 */         out = _jspx_page_context.popBody(); }
/* 422 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*     */     } finally {
/* 424 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 425 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*     */     }
/* 427 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 432 */     PageContext pageContext = _jspx_page_context;
/* 433 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 435 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 436 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 437 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 439 */     _jspx_th_c_005fout_005f0.setValue("${current}");
/* 440 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 441 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 442 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 443 */       return true;
/*     */     }
/* 445 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 446 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fchoose_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 451 */     PageContext pageContext = _jspx_page_context;
/* 452 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 454 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 455 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 456 */     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 457 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 458 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */       for (;;) {
/* 460 */         out.write("\n\t\t\t\t");
/* 461 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 462 */           return true;
/* 463 */         out.write("\n\t\t\t\t");
/* 464 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 465 */           return true;
/* 466 */         out.write("\n\t\t\t");
/* 467 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 468 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 472 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 473 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 474 */       return true;
/*     */     }
/* 476 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 477 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 482 */     PageContext pageContext = _jspx_page_context;
/* 483 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 485 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 486 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 487 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*     */     
/* 489 */     _jspx_th_c_005fwhen_005f0.setTest("${not empty images}");
/* 490 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 491 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */       for (;;) {
/* 493 */         out.write("\n\t\t\t\t\t$(\"#slides\").append(\"<div id=\\\"\"+step+\"\\\" class=\\\"sliderDiv\\\"><div class=\\\"widBox\\\">\"+imgDom[i++]+\"</div><div class=\\\"widBox\\\" id=\\\"\"+step+\"_ressize\\\"></div><div class=\\\"widBox\\\" id=\\\"\"+step+\"_rescount\\\"></div><div class=\\\"tableData\\\" id=\\\"\"+step+\"_perf\\\">\"+slideDiv.innerHTML+\"</div>\");\n\t\t\t\t");
/* 494 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 495 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 499 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 500 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 501 */       return true;
/*     */     }
/* 503 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 504 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 509 */     PageContext pageContext = _jspx_page_context;
/* 510 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 512 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 513 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 514 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 515 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 516 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */       for (;;) {
/* 518 */         out.write("\n\t\t\t\t\t$(\"#slides\").append(\"<div id=\\\"\"+step+\"\\\" class=\\\"sliderDiv onlyPerfMet\\\"><div id=\\\"\"+step+\"_ressize\\\" class=\\\"widBox\\\"></div><div class=\\\"widBox\\\" id=\\\"\"+step+\"_rescount\\\"></div><div class=\\\"tableData\\\" id=\\\"\"+step+\"_perf\\\">\"+slideDiv.innerHTML+\"</div></div>\");\n\t\t\t\t");
/* 519 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 520 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 524 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 525 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 526 */       return true;
/*     */     }
/* 528 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 529 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 534 */     PageContext pageContext = _jspx_page_context;
/* 535 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 537 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 538 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 539 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*     */     
/* 541 */     _jspx_th_c_005fset_005f0.setTarget("${myDate2}");
/*     */     
/* 543 */     _jspx_th_c_005fset_005f0.setProperty("time");
/*     */     
/* 545 */     _jspx_th_c_005fset_005f0.setValue("${selTime}");
/* 546 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 547 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 548 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 549 */       return true;
/*     */     }
/* 551 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 552 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 557 */     PageContext pageContext = _jspx_page_context;
/* 558 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 560 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 561 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 562 */     _jspx_th_fmt_005fformatDate_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*     */     
/* 564 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${myDate2}");
/*     */     
/* 566 */     _jspx_th_fmt_005fformatDate_005f0.setPattern("dd-MMM-yyyy  HH:mm");
/* 567 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 568 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 569 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 570 */       return true;
/*     */     }
/* 572 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 573 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 578 */     PageContext pageContext = _jspx_page_context;
/* 579 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 581 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 582 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 583 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*     */     
/* 585 */     _jspx_th_c_005fout_005f1.setValue("${resourceid}");
/* 586 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 587 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 588 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 589 */       return true;
/*     */     }
/* 591 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 592 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 597 */     PageContext pageContext = _jspx_page_context;
/* 598 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 600 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 601 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 602 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*     */     
/* 604 */     _jspx_th_c_005fforEach_005f1.setItems("${sTimes}");
/*     */     
/* 606 */     _jspx_th_c_005fforEach_005f1.setVar("sTime");
/* 607 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*     */     try {
/* 609 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 610 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*     */         for (;;) {
/* 612 */           out.write("\n\t\t\t\t\t\t\t\t\t");
/* 613 */           boolean bool; if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 614 */             return true;
/* 615 */           out.write("  \n\t\t\t\t\t\t\t\t\t<option value=");
/* 616 */           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 617 */             return true;
/* 618 */           out.write(62);
/* 619 */           if (_jspx_meth_fmt_005fformatDate_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 620 */             return true;
/* 621 */           out.write("</option>\n\t\t\t\t\t\t\t\t");
/* 622 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 623 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 627 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 628 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 631 */         int tmp267_266 = 0; int[] tmp267_264 = _jspx_push_body_count_c_005fforEach_005f1; int tmp269_268 = tmp267_264[tmp267_266];tmp267_264[tmp267_266] = (tmp269_268 - 1); if (tmp269_268 <= 0) break;
/* 632 */         out = _jspx_page_context.popBody(); }
/* 633 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*     */     } finally {
/* 635 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 636 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*     */     }
/* 638 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 643 */     PageContext pageContext = _jspx_page_context;
/* 644 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 646 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.get(SetTag.class);
/* 647 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 648 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 650 */     _jspx_th_c_005fset_005f1.setTarget("${myDate}");
/*     */     
/* 652 */     _jspx_th_c_005fset_005f1.setProperty("time");
/*     */     
/* 654 */     _jspx_th_c_005fset_005f1.setValue("${sTime}");
/* 655 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 656 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 657 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 658 */       return true;
/*     */     }
/* 660 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvalue_005ftarget_005fproperty_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 661 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 666 */     PageContext pageContext = _jspx_page_context;
/* 667 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 669 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 670 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 671 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 673 */     _jspx_th_c_005fout_005f2.setValue("${sTime}");
/* 674 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 675 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 676 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 677 */       return true;
/*     */     }
/* 679 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 680 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fformatDate_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 685 */     PageContext pageContext = _jspx_page_context;
/* 686 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 688 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f1 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 689 */     _jspx_th_fmt_005fformatDate_005f1.setPageContext(_jspx_page_context);
/* 690 */     _jspx_th_fmt_005fformatDate_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 692 */     _jspx_th_fmt_005fformatDate_005f1.setValue("${myDate}");
/*     */     
/* 694 */     _jspx_th_fmt_005fformatDate_005f1.setPattern("dd-MMM-yyyy  HH:mm");
/* 695 */     int _jspx_eval_fmt_005fformatDate_005f1 = _jspx_th_fmt_005fformatDate_005f1.doStartTag();
/* 696 */     if (_jspx_th_fmt_005fformatDate_005f1.doEndTag() == 5) {
/* 697 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 698 */       return true;
/*     */     }
/* 700 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 701 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fchoose_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 706 */     PageContext pageContext = _jspx_page_context;
/* 707 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 709 */     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 710 */     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 711 */     _jspx_th_c_005fchoose_005f3.setParent(null);
/* 712 */     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 713 */     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*     */       for (;;) {
/* 715 */         out.write("\n\t\t\t\t\t\t");
/* 716 */         if (_jspx_meth_c_005fwhen_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 717 */           return true;
/* 718 */         out.write("\n\t\t\t\t\t\t");
/* 719 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context))
/* 720 */           return true;
/* 721 */         out.write("\t\n\t\t\t\t    ");
/* 722 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 723 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 727 */     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 728 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 729 */       return true;
/*     */     }
/* 731 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 732 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fwhen_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 737 */     PageContext pageContext = _jspx_page_context;
/* 738 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 740 */     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 741 */     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 742 */     _jspx_th_c_005fwhen_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/*     */     
/* 744 */     _jspx_th_c_005fwhen_005f3.setTest("${not empty perfFileLoc}");
/* 745 */     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 746 */     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*     */       for (;;) {
/* 748 */         out.write("\n\t\t\t\t\t  \t<div id=\"slides\">\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<script>\n\t\t\t\t\t\t\t\t$.getJSON('/projects/webscripts/");
/* 749 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/* 750 */           return true;
/* 751 */         out.write("',function(data)  //NO I18N\n\t\t\t\t\t\t\t\t{\n\t\t\t\t\t\t\t\t\tgeneratePerfMets(data);\n\t\t\t\t\t\t\t\t});\n\n\t\t\t\t\t\t\t</script>\n\t\t\t\t\t\t");
/* 752 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 753 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 757 */     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 758 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 759 */       return true;
/*     */     }
/* 761 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 762 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 767 */     PageContext pageContext = _jspx_page_context;
/* 768 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 770 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 771 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 772 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*     */     
/* 774 */     _jspx_th_c_005fout_005f3.setValue("${perfFileLoc}");
/* 775 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 776 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 777 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 778 */       return true;
/*     */     }
/* 780 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 781 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 786 */     PageContext pageContext = _jspx_page_context;
/* 787 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 789 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 790 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 791 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 792 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 793 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*     */       for (;;) {
/* 795 */         out.write("\n\t\t\t\t\t\t\t");
/* 796 */         if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/* 797 */           return true;
/* 798 */         out.write("\n\t\t\t\t\t\t\t<div id=\"slides\">\n\t\t\t\t\t\t\t  \t");
/* 799 */         if (_jspx_meth_c_005fforEach_005f2(_jspx_th_c_005fotherwise_005f3, _jspx_page_context))
/* 800 */           return true;
/* 801 */         out.write("\n\t\t\t\t\t    \t</div>\n\t\t\t\t    \t");
/* 802 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 803 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 807 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 808 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 809 */       return true;
/*     */     }
/* 811 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 812 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 817 */     PageContext pageContext = _jspx_page_context;
/* 818 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 820 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 821 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 822 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*     */     
/* 824 */     _jspx_th_c_005fif_005f2.setTest("${not empty errMsg}");
/* 825 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 826 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 828 */         out.write("\n\t\t\t\t\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"messagebox\" >\n\t \t\t\t\t\t\t\t<tr>\n\t \t\t\t\t\t\t\t <td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\"> <img src=\"/images/icon_message_failure.gif\" ></td>\n\t\t\t\t\t\t\t\t  <td><span class=\"bodytext\">");
/* 829 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 830 */           return true;
/* 831 */         out.write(" </span> </td>\n\t\t\t\t\t\t\t\t </tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t<br /><br />\n\t\t\t\t\t\t\t");
/* 832 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 833 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 837 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 838 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 839 */       return true;
/*     */     }
/* 841 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 842 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 847 */     PageContext pageContext = _jspx_page_context;
/* 848 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 850 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 851 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 852 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 854 */     _jspx_th_c_005fout_005f4.setValue("${errMsg}");
/* 855 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 856 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 857 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 858 */       return true;
/*     */     }
/* 860 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 861 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fforEach_005f2(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 866 */     PageContext pageContext = _jspx_page_context;
/* 867 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 869 */     ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 870 */     _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 871 */     _jspx_th_c_005fforEach_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*     */     
/* 873 */     _jspx_th_c_005fforEach_005f2.setItems("${images}");
/*     */     
/* 875 */     _jspx_th_c_005fforEach_005f2.setVar("current");
/* 876 */     int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*     */     try {
/* 878 */       int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 879 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*     */         for (;;) {
/* 881 */           out.write("\n\t\t\t\t\t\t\t  \t\t<div class=\"sliderDiv onlyScreenshot\">\n\t\t\t\t\t\t  \t\t\t\t<div class=\"widBox\">\n\t\t\t\t\t\t  \t\t\t\t\t <img src=/projects/webscripts/ScreenShots/");
/* 882 */           if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 883 */             return true;
/* 884 */           out.write(62);
/* 885 */           out.write(32);
/* 886 */           out.write("\n\t\t\t\t\t\t        \t\t</div>\n\t\t\t\t\t\t        \t</div>\n\t\t\t\t\t    \t  \t");
/* 887 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 888 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 892 */       if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/* 893 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 896 */         int tmp203_202 = 0; int[] tmp203_200 = _jspx_push_body_count_c_005fforEach_005f2; int tmp205_204 = tmp203_200[tmp203_202];tmp203_200[tmp203_202] = (tmp205_204 - 1); if (tmp205_204 <= 0) break;
/* 897 */         out = _jspx_page_context.popBody(); }
/* 898 */       _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*     */     } finally {
/* 900 */       _jspx_th_c_005fforEach_005f2.doFinally();
/* 901 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*     */     }
/* 903 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*     */   {
/* 908 */     PageContext pageContext = _jspx_page_context;
/* 909 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 911 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 912 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 913 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*     */     
/* 915 */     _jspx_th_c_005fout_005f5.setValue("${current}");
/* 916 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 917 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 918 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 919 */       return true;
/*     */     }
/* 921 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 922 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f6(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 927 */     PageContext pageContext = _jspx_page_context;
/* 928 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 930 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 931 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 932 */     _jspx_th_c_005fout_005f6.setParent(null);
/*     */     
/* 934 */     _jspx_th_c_005fout_005f6.setValue("${resourceid}");
/* 935 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 936 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 937 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 938 */       return true;
/*     */     }
/* 940 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 941 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\RealBrowserImageViewer_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */