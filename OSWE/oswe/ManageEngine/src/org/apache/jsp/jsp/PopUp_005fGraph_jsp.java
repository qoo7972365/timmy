/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.bean.HostResourceGraph;
/*     */ import com.adventnet.appmanager.bean.SummaryBean;
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*     */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorGraphBean;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.awolf.tags.TimeChart;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class PopUp_005fGraph_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  24 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisUnit_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  37 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  41 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisUnit_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  45 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  49 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  50 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisUnit_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/*  51 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  58 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  61 */     JspWriter out = null;
/*  62 */     Object page = this;
/*  63 */     JspWriter _jspx_out = null;
/*  64 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  68 */       response.setContentType("text/html;charset=UTF-8");
/*  69 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  71 */       _jspx_page_context = pageContext;
/*  72 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  73 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  74 */       session = pageContext.getSession();
/*  75 */       out = pageContext.getOut();
/*  76 */       _jspx_out = out;
/*     */       
/*  78 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n  \n\n\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  79 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  81 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n    \n");
/*  82 */       SummaryBean sumgraph = null;
/*  83 */       sumgraph = (SummaryBean)_jspx_page_context.getAttribute("sumgraph", 2);
/*  84 */       if (sumgraph == null) {
/*  85 */         sumgraph = new SummaryBean();
/*  86 */         _jspx_page_context.setAttribute("sumgraph", sumgraph, 2);
/*     */       }
/*  88 */       out.write(10);
/*  89 */       ConfMonitorGraphBean confgraph = null;
/*  90 */       confgraph = (ConfMonitorGraphBean)_jspx_page_context.getAttribute("confgraph", 2);
/*  91 */       if (confgraph == null) {
/*  92 */         confgraph = new ConfMonitorGraphBean();
/*  93 */         _jspx_page_context.setAttribute("confgraph", confgraph, 2);
/*     */       }
/*  95 */       out.write(10);
/*  96 */       HostResourceGraph hostCPUGraph = null;
/*  97 */       hostCPUGraph = (HostResourceGraph)_jspx_page_context.getAttribute("hostCPUGraph", 2);
/*  98 */       if (hostCPUGraph == null) {
/*  99 */         hostCPUGraph = new HostResourceGraph();
/* 100 */         _jspx_page_context.setAttribute("hostCPUGraph", hostCPUGraph, 2);
/*     */       }
/* 102 */       out.write("\n<title>");
/* 103 */       out.print(FormatUtil.getString("am.webclient.common.showgraph.text"));
/* 104 */       out.write("</title>\n\n<table width=\"100%\"  height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n<tr>\n<td>&nbsp;<span class=\"headingboldwhite\">");
/* 105 */       out.print(FormatUtil.getString("am.webclient.common.showgraph.text"));
/* 106 */       out.write("</span><span class=\"headingwhite\"> </span>\n</td>\n</tr>\n</table>\n\n");
/*     */       
/* 108 */       String resids = request.getParameter("resids");
/* 109 */       String resourcetype = request.getParameter("restype");
/* 110 */       String monId = request.getParameter("monID");
/* 111 */       String attids = request.getParameter("attids");
/* 112 */       String attName = request.getParameter("attName");
/* 113 */       boolean isConfMonitor = ConfMonitorConfiguration.getInstance().isConfMonitor(resourcetype);
/* 114 */       int size = 2;
/*     */       try {
/* 116 */         size = Integer.parseInt(request.getParameter("listsize"));
/*     */       }
/*     */       catch (Exception ex) {}
/* 119 */       out.write("\n<script>\n\nfunction showHistory(period,attrbId)\n{\n\tdocument.form1.period.value=period;\n\tdocument.form1.attributeid.value=attrbId;\n\tdocument.form1.submit();\n}\n</script>\n\n");
/*     */       
/* 121 */       int height = 350;
/* 122 */       if (size >= 5) {
/* 123 */         height = 350 + (size - 5) * 10;
/*     */       }
/* 125 */       String height_graph = String.valueOf(height);
/*     */       
/* 127 */       boolean isSNMPMode = false;
/* 128 */       boolean isSummaryGraph = false;
/* 129 */       String modeOfMon = request.getParameter("modeOfMon");
/* 130 */       String reqSummaryGraphFor = request.getParameter("summarygraphfor");
/* 131 */       if ((modeOfMon != null) && (modeOfMon.equalsIgnoreCase("snmp"))) {
/* 132 */         isSNMPMode = true;
/*     */       }
/* 134 */       if ((reqSummaryGraphFor != null) && (reqSummaryGraphFor.equals("cpucore")))
/*     */       {
/* 136 */         if (isSNMPMode) {
/* 137 */           attids = "9641,9645";
/*     */         } else {
/* 139 */           attids = "all,9642,9643,9644,9645,9641,9646";
/*     */         }
/* 141 */         isSummaryGraph = true;
/*     */       }
/* 143 */       if ((attids != null) && (attids.length() > 0))
/*     */       {
/* 145 */         if (attids.endsWith(",")) {
/* 146 */           attids = attids.substring(0, attids.length() - 1);
/*     */         }
/*     */       } else {
/* 149 */         out.println("Attribute Id is invalid"); return;
/*     */       }
/*     */       
/* 152 */       StringTokenizer tokens = new StringTokenizer(attids, ",");
/* 153 */       while (tokens.hasMoreTokens())
/*     */       {
/* 155 */         String attributeId = tokens.nextToken();
/* 156 */         boolean cpuCoreGraph = false;
/* 157 */         if ((attributeId != null) && ((attributeId.equals("all")) || (attributeId.equals("9641")) || (attributeId.equals("9642")) || (attributeId.equals("9643")) || (attributeId.equals("9644")) || (attributeId.equals("9645")) || (attributeId.equals("9646"))))
/*     */         {
/* 159 */           if (resids != null)
/*     */           {
/* 161 */             java.util.HashMap cpuCoresPolledData = null;
/* 162 */             hostCPUGraph.setEntity("cpucore");
/* 163 */             if (attributeId.equals("all"))
/*     */             {
/*     */ 
/* 166 */               hostCPUGraph.setResourceId(resids);
/* 167 */               cpuCoresPolledData = hostCPUGraph.getCPUPolledData();
/*     */             } else {
/* 169 */               hostCPUGraph.setResourceId(resids + ",");
/* 170 */               hostCPUGraph.setAttributeId(attributeId);
/* 171 */               cpuCoresPolledData = hostCPUGraph.getCPUPolledDataForAttrb();
/*     */             }
/* 173 */             hostCPUGraph.setCpuUsagePolledData(cpuCoresPolledData);
/* 174 */             cpuCoreGraph = true;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 179 */           sumgraph.setResid(resids);
/* 180 */           sumgraph.setAttributeid(attributeId);
/* 181 */           sumgraph.setArchivedforUrl(false);
/* 182 */           sumgraph.setCompareRows(true);
/* 183 */           if ((request.getParameter("baseid") != null) && (!request.getParameter("baseid").equals("-1")))
/*     */           {
/* 185 */             if (resourcetype.equals("QueryMonitor"))
/*     */             {
/* 187 */               sumgraph.setToappend("_" + monId);
/*     */             }
/* 189 */             else if (!isConfMonitor) {
/* 190 */               sumgraph.setToappend("_" + request.getParameter("baseid"));
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 195 */               confgraph.setGraphType("LINE");
/* 196 */               confgraph.setGraphForData("COMPAREROWS");
/* 197 */               confgraph.setResourceType(resourcetype);
/* 198 */               confgraph.setResIds(resids);
/* 199 */               confgraph.setAttributeId(attids);
/* 200 */               confgraph.setTableName(request.getParameter("tableName"));
/* 201 */               confgraph.getGraphValues();
/*     */             }
/*     */           }
/* 204 */           if ((request.getParameter("toappend") != null) && (!request.getParameter("toappend").trim().equals("")))
/*     */           {
/* 206 */             sumgraph.setToappend("_" + request.getParameter("toappend"));
/*     */           }
/* 208 */           sumgraph.setTables(true);
/*     */         }
/* 210 */         ResultSet rs = null;
/* 211 */         boolean seven_thirty = false;
/* 212 */         boolean skipIsArchive = false;
/* 213 */         if ((attributeId != null) && (attributeId.equals("all"))) {
/* 214 */           skipIsArchive = true;
/*     */         }
/* 216 */         if (!skipIsArchive) {
/* 217 */           AMConnectionPool cp = AMConnectionPool.getInstance();
/*     */           try
/*     */           {
/* 220 */             rs = AMConnectionPool.executeQueryStmt("select ISARCHIVEING from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeId);
/* 221 */             if (rs.next())
/*     */             {
/* 223 */               if (rs.getInt("ISARCHIVEING") == 1)
/* 224 */                 seven_thirty = true;
/*     */             }
/* 226 */             rs.close();
/*     */           }
/*     */           catch (Exception exc) {
/* 229 */             exc.printStackTrace();
/*     */           }
/*     */           
/*     */           try
/*     */           {
/* 234 */             if (!seven_thirty)
/*     */             {
/* 236 */               rs = AMConnectionPool.executeQueryStmt("select * from AM_ArchiverCAMConfig where ATTRIBUTEID=" + attributeId);
/* 237 */               if (rs.next())
/*     */               {
/* 239 */                 seven_thirty = true;
/*     */               }
/* 241 */               rs.close();
/*     */             }
/*     */           } catch (Exception exc) {
/* 244 */             exc.printStackTrace();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 277 */         String unit = "";
/* 278 */         if ((attributeId != null) && (attributeId.equals("all"))) {
/* 279 */           attName = "am.webclient.server.cpucore.usage.individual.popup";
/* 280 */           unit = "%";
/*     */         }
/* 282 */         if ((attName == null) || (attName.length() == 0))
/*     */         {
/*     */           try
/*     */           {
/* 286 */             AMConnectionPool.getInstance();rs = AMConnectionPool.executeQueryStmt("select DISPLAYNAME,UNITS from AM_ATTRIBUTES WHERE ATTRIBUTEID=" + attributeId);
/* 287 */             if (rs.next())
/*     */             {
/* 289 */               attName = rs.getString("DISPLAYNAME");
/* 290 */               unit = rs.getString("UNITS");
/*     */             }
/* 292 */             rs.close();
/*     */           }
/*     */           catch (Exception exc) {}
/*     */         }
/*     */         
/* 297 */         if ((unit == null) || (unit.length() == 0) || (unit.equalsIgnoreCase("null")))
/*     */         {
/* 299 */           unit = "";
/*     */         } else {
/* 301 */           unit = " (" + unit + ")";
/*     */         }
/* 303 */         String graph_title = FormatUtil.getString("am.webclient.confmonitors.compare.popup.title.unit", new String[] { FormatUtil.getString(attName), unit });
/* 304 */         if ((cpuCoreGraph) && 
/* 305 */           (resids.indexOf(",") == -1)) {
/* 306 */           graph_title = hostCPUGraph.getDisplayName(resids) + " - " + graph_title;
/*     */         }
/*     */         
/*     */ 
/* 310 */         out.write("\n<br>\n<table width=\"98%\" class=\"lrtbdarkborder\" cellspacing=\"0\" cellpadding=\"5\" align=\"center\">\n<form name=\"form1\" action=\"/showHistoryData.do\">\n    <tr> \n\t\t<td colspan=\"2\" class=\"tableheadingbborder\"><b>");
/* 311 */         out.print(graph_title);
/* 312 */         out.write("</td>\n    </tr>\n");
/*     */         
/* 314 */         if (seven_thirty)
/*     */         {
/*     */ 
/* 317 */           out.write("\n    <tr> \n      <td width=\"90%\" height=\"35\" align=\"right\">\n        <a href=\"#\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" onClick=\"showHistory('-7','");
/* 318 */           out.print(attributeId);
/* 319 */           out.write("')\"  title=\"");
/* 320 */           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 321 */           out.write("\"></a></td>\n      <td width=\"10%\" height=\"35\"><a href=\"#\" ><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" onClick=\"showHistory('-30','");
/* 322 */           out.print(attributeId);
/* 323 */           out.write("')\" title=\"");
/* 324 */           out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 325 */           out.write("\"></a></td>\n    </tr>\n    ");
/*     */         }
/*     */         
/*     */ 
/* 329 */         out.write("\n    <tr> \n\t    <td height=\"190\" align=\"center\" class=\"bodytext\" > \n");
/*     */         
/* 331 */         if (cpuCoreGraph) {
/* 332 */           String yAxisUnit = "%";
/* 333 */           if (attributeId.equals("9646")) {
/* 334 */             yAxisUnit = "default";
/*     */           }
/*     */           
/* 337 */           out.write("\t    \n\t    ");
/*     */           
/* 339 */           TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisUnit_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 340 */           _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 341 */           _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*     */           
/* 343 */           _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("hostCPUGraph");
/*     */           
/* 345 */           _jspx_th_awolf_005ftimechart_005f0.setWidth("650");
/*     */           
/* 347 */           _jspx_th_awolf_005ftimechart_005f0.setHeight(height_graph);
/*     */           
/* 349 */           _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*     */           
/* 351 */           _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*     */           
/* 353 */           _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString(attName) + unit);
/*     */           
/* 355 */           _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/*     */           
/* 357 */           _jspx_th_awolf_005ftimechart_005f0.setYaxisUnit(yAxisUnit);
/* 358 */           int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 359 */           if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 360 */             if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 361 */               out = _jspx_page_context.pushBody();
/* 362 */               _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 363 */               _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*     */             }
/*     */             for (;;) {
/* 366 */               out.write(" \n        ");
/* 367 */               int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 368 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 371 */             if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 372 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 375 */           if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 376 */             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisUnit_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*     */           }
/*     */           
/* 379 */           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisUnit_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 380 */           out.write(32);
/* 381 */           out.write(10);
/*     */ 
/*     */         }
/* 384 */         else if ((isConfMonitor) && (!resourcetype.equals("QueryMonitor"))) {
/* 385 */           out.write("\n \t\t");
/*     */           
/* 387 */           TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 388 */           _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 389 */           _jspx_th_awolf_005ftimechart_005f1.setParent(null);
/*     */           
/* 391 */           _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("confgraph");
/*     */           
/* 393 */           _jspx_th_awolf_005ftimechart_005f1.setWidth("650");
/*     */           
/* 395 */           _jspx_th_awolf_005ftimechart_005f1.setHeight(height_graph);
/*     */           
/* 397 */           _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*     */           
/* 399 */           _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*     */           
/* 401 */           _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(FormatUtil.getString(attName));
/*     */           
/* 403 */           _jspx_th_awolf_005ftimechart_005f1.setDateFormat("HH:mm");
/* 404 */           int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 405 */           if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 406 */             if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 407 */               out = _jspx_page_context.pushBody();
/* 408 */               _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 409 */               _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*     */             }
/*     */             for (;;) {
/* 412 */               out.write(" \n        ");
/* 413 */               int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 414 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 417 */             if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 418 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 421 */           if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 422 */             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*     */           }
/*     */           
/* 425 */           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 426 */           out.write(32);
/* 427 */           out.write(32);
/* 428 */           out.write("\n \t\t");
/*     */         }
/*     */         else {
/* 431 */           out.write("\n\t    ");
/*     */           
/* 433 */           TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 434 */           _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 435 */           _jspx_th_awolf_005ftimechart_005f2.setParent(null);
/*     */           
/* 437 */           _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("sumgraph");
/*     */           
/* 439 */           _jspx_th_awolf_005ftimechart_005f2.setWidth("650");
/*     */           
/* 441 */           _jspx_th_awolf_005ftimechart_005f2.setHeight(height_graph);
/*     */           
/* 443 */           _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*     */           
/* 445 */           _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*     */           
/* 447 */           _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(FormatUtil.getString(attName));
/*     */           
/* 449 */           _jspx_th_awolf_005ftimechart_005f2.setDateFormat("HH:mm");
/* 450 */           int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 451 */           if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 452 */             if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 453 */               out = _jspx_page_context.pushBody();
/* 454 */               _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 455 */               _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*     */             }
/*     */             for (;;) {
/* 458 */               out.write(" \n        ");
/* 459 */               int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 460 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 463 */             if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 464 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 467 */           if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 468 */             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*     */           }
/*     */           
/* 471 */           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 472 */           out.write(32);
/* 473 */           out.write(32);
/* 474 */           out.write(10);
/*     */         }
/*     */         
/*     */ 
/* 478 */         out.write("        \n        </td>\n    </tr>\n");
/*     */         
/* 480 */         attName = "";
/*     */       }
/*     */       
/* 483 */       out.write("<br>\n\t<tr>\n\t\t<td align=\"center\" class=\"tablebottom\" colspan=\"2\">\n\t\t<input type=\"button\" class=\"buttons btn_link\" value=\"");
/* 484 */       out.print(FormatUtil.getString("webclient.common.printview.button.close"));
/* 485 */       out.write("\" onclick=\"window.close()\">\n\t\t</td>\n\t</tr>\n");
/*     */       
/* 487 */       if (resids.contains(","))
/*     */       {
/*     */ 
/* 490 */         out.write("\n<input name=\"method\" type=\"hidden\" value=\"getIndividualURLandCompareReportsData\"/>\n<input name=\"resourceid\" type=\"hidden\" value=\"-1\"/>\n");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 496 */         out.write("\n\t<input name=\"method\" type=\"hidden\" value=\"getData\"/>\n\t<input name=\"resourceid\" type=\"hidden\" value=\"");
/* 497 */         out.print(resids);
/* 498 */         out.write("\"/>\n");
/*     */       }
/*     */       
/*     */ 
/* 502 */       out.write("\n<input name=\"childid\" type=\"hidden\" value=\"");
/* 503 */       out.print(resids);
/* 504 */       out.write("\"/>\n<input name=\"attributeid\" type=\"hidden\" value=\"\"/>\n<input name=\"period\" type=\"hidden\" value=\"-7\"/>\n</form>\n</table>\n<br>\n");
/*     */     } catch (Throwable t) {
/* 506 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 507 */         out = _jspx_out;
/* 508 */         if ((out != null) && (out.getBufferSize() != 0))
/* 509 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 510 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 513 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 519 */     PageContext pageContext = _jspx_page_context;
/* 520 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 522 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 523 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 524 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 526 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 528 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 529 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 530 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 531 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 532 */       return true;
/*     */     }
/* 534 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 535 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\PopUp_005fGraph_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */