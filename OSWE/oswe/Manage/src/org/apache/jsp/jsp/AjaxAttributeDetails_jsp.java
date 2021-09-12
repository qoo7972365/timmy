/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.bean.SummaryBean;
/*     */ import com.adventnet.appmanager.reporting.ReportUtilities;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.appmanager.util.ReportDataUtilities;
/*     */ import com.adventnet.awolf.chart.ChartInfo;
/*     */ import com.adventnet.awolf.tags.SparkLine;
/*     */ import com.adventnet.awolf.tags.SparkSeries;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class AjaxAttributeDetails_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  34 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fsparkline_0026_005fplots_005flistName_005flineColor_005fheight_005fnobody;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  46 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  50 */     this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fawolf_005fsparkline_0026_005fplots_005flistName_005flineColor_005fheight_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  53 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  57 */     this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.release();
/*  58 */     this._005fjspx_005ftagPool_005fawolf_005fsparkline_0026_005fplots_005flistName_005flineColor_005fheight_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  65 */     HttpSession session = null;
/*     */     
/*     */ 
/*  68 */     JspWriter out = null;
/*  69 */     Object page = this;
/*  70 */     JspWriter _jspx_out = null;
/*  71 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  75 */       response.setContentType("text/html;charset=UTF-8");
/*  76 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  78 */       _jspx_page_context = pageContext;
/*  79 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  80 */       ServletConfig config = pageContext.getServletConfig();
/*  81 */       session = pageContext.getSession();
/*  82 */       out = pageContext.getOut();
/*  83 */       _jspx_out = out;
/*     */       
/*  85 */       out.write("<!--$Id$-->\n");
/*  86 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n");
/*     */       try
/*     */       {
/*  89 */         int initialWidth = 100;
/*  90 */         int secondWidth = 100;
/*  91 */         long timestamps1 = Long.parseLong(request.getParameter("polledtime"));
/*     */         
/*  93 */         String temparray = request.getParameter("temparray");
/*     */         
/*  95 */         String iscustom = request.getParameter("iscustom");
/*  96 */         String isAlerts = request.getParameter("alerts");
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 101 */         long[] timestampsparams = ReportDataUtilities.returnLongFromString(temparray);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 107 */         ChartInfo cinfo = new ChartInfo();
/* 108 */         SummaryBean sbean = new SummaryBean();
/*     */         
/*     */ 
/* 111 */         Map RMAP = ReportDataUtilities.getResourceMapping(request.getParameter("haid"));
/*     */         
/* 113 */         Map DataTableMap = new HashMap();
/*     */         
/* 115 */         String k1 = request.getParameter("k1");
/* 116 */         boolean isWindows = false;
/* 117 */         if ("windows".equals(k1)) {
/* 118 */           isWindows = true;
/*     */         }
/*     */         
/* 121 */         List tempList = new ArrayList();
/* 122 */         Map resourceMaps = (Map)RMAP.get(k1);
/*     */         
/* 124 */         Map FirstLevelMap = (Map)resourceMaps.get("Firstlevel");
/*     */         
/*     */ 
/* 127 */         Map SecondLevelMap = (Map)resourceMaps.get("secondlevel");
/*     */         
/* 129 */         Map attributeIDMap = (Map)resourceMaps.get("parentMapping");
/*     */         
/* 131 */         tempList.add(FirstLevelMap);
/* 132 */         if (SecondLevelMap.size() > 0) {
/* 133 */           tempList.add(SecondLevelMap);
/*     */         }
/*     */         
/*     */ 
/* 137 */         boolean isSecondLevel = false;
/* 138 */         for (int y = 0; y < tempList.size(); y++)
/*     */         {
/* 140 */           String tableHeading = "First Level Attribute";
/* 141 */           if (y == 1)
/*     */           {
/* 143 */             isSecondLevel = true;
/* 144 */             tableHeading = "Second Level Attribute";
/*     */           }
/*     */           
/* 147 */           Map allAttributes = ReportDataUtilities.getAttributeAndName((Map)tempList.get(y));
/* 148 */           List ResourceIds = (List)resourceMaps.get("ResourceIds");
/*     */           
/* 150 */           if (y > 0)
/*     */           {
/* 152 */             ResourceIds = ReportDataUtilities.getChildIDs(ReportDataUtilities.getCommaSeparatedIds(ResourceIds));
/*     */             
/* 154 */             if (ResourceIds.size() == 0) {}
/*     */           }
/*     */           else
/*     */           {
/* 158 */             String allResourceIds = ReportDataUtilities.getCommaSeparatedIds(ResourceIds);
/* 159 */             DataTableMap.put("resourceids", ResourceIds);
/*     */             
/* 161 */             List m = ReportDataUtilities.getArchivedDataValues((Map)tempList.get(y), allResourceIds, DataTableMap, timestampsparams, iscustom);
/*     */             
/* 163 */             if ("true".equals(isAlerts)) {
/* 164 */               iscustom = "true";
/*     */             }
/* 166 */             List l = ReportDataUtilities.getRawDataForLastPolledValue(DataTableMap, timestamps1, ResourceIds, timestampsparams, iscustom);
/*     */             
/* 168 */             Map rawvalues = (Map)l.get(0);
/*     */             
/*     */ 
/* 171 */             Map formatedData = ReportDataUtilities.getFormattedData(m, l, isSecondLevel);
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 179 */             Map twelveHourGraph = (Map)m.get(2);
/* 180 */             Map sevenHourGraph = (Map)m.get(3);
/* 181 */             Map tempwindowsattidmapping = (Map)m.get(4);
/* 182 */             Map SDMap = (Map)m.get(5);
/*     */             
/* 184 */             Map AnamolyMap = (Map)m.get(6);
/* 185 */             Map DataAnamolyMap = (Map)m.get(7);
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 190 */             int colspanSize = allAttributes.size() + 1;
/* 191 */             if (allAttributes.size() > 3) {
/* 192 */               initialWidth = 100 * allAttributes.size();
/*     */             }
/*     */             
/* 195 */             if (allAttributes.size() > 1) {
/* 196 */               secondWidth = 100 / allAttributes.size();
/*     */             }
/*     */             
/*     */ 
/*     */ 
/* 201 */             out.write("\n   \n\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"margin-left:10px;\" >\n\n ");
/* 202 */             if (!isSecondLevel) {
/* 203 */               String idn = k1 + "1";
/* 204 */               String[] DN1 = ReportDataUtilities.getImagePath(k1).split("@");
/* 205 */               if (DN1.length >= 2)
/*     */               {
/* 207 */                 out.write("\n    <tr>\n        <td ><img src=\"../images/spacer.gif\" width=\"17\" height=\"25\" /></td>\n    <td width=\"");
/* 208 */                 out.print(initialWidth);
/* 209 */                 out.write("%\" class=\"\" >\n        <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n            \n            <tr>\n               <td width=\"100%\" class=\"  anamoly-font\">&nbsp;<img src=\"");
/* 210 */                 out.print(DN1[0]);
/* 211 */                 out.write("\">&nbsp;");
/* 212 */                 out.print(FormatUtil.getString(DN1[1]));
/* 213 */                 out.write(" <a name=\"");
/* 214 */                 out.print(idn);
/* 215 */                 out.write("\"></a> </td>\n                \n            </tr>\n        </table>\n    \n\n</td>\n    <td><img src=\"../images/spacer.gif\" width=\"19\" height=\"25\" /></td>\n\n    </tr>\n   ");
/*     */               }
/*     */               
/*     */ 
/* 219 */               out.write(" \n  \n   \n   \n    ");
/* 220 */               if (formatedData.size() > 0) {
/* 221 */                 out.write("\n\n    \n   \t<tr>\n            <td width=\"17\"><img src=\"../images/spacer.gif\" width=\"17\" height=\"19\" /></td>                                    \n            <td width=\"");
/* 222 */                 out.print(initialWidth);
/* 223 */                 out.write("%\" class=\"anamoly-mid-color\" style=\"background-color:#fff;\" >\n        <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t<tr>\n            <td width=\"13\"  class=\"anamoly-blue-rig-corn \"><img src=\"../images/spacer.gif\" width=\"13\" height=\"25\" /></td>\n            <td width=\"100%\" class=\"anamoly-mid-tile\" colspan=\"");
/* 224 */                 out.print(colspanSize);
/* 225 */                 out.write("\"></td>\n            <td class=\"anamoly-bgtab-right\"  width=\"13\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"25\"></td>\n\n        </tr>\n        \n        \n        \n        <tr>\n            <td class=\"anamoly-bgtab-left-tile\" width=\"13\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\"></td> \n            <td class=\"tableheadingbborder-anamoly-normal\" style=\"color:#00008B\">");
/* 226 */                 out.print(FormatUtil.getString("am.webclient.admin.table.heading"));
/* 227 */                 out.write("</td>\n    \n    ");
/* 228 */                 Collection AttributeCollection = allAttributes.keySet();
/* 229 */                 Iterator AttributeIterator = AttributeCollection.iterator();
/* 230 */                 String AttributeDisplayName = "";
/* 231 */                 while (AttributeIterator.hasNext()) {
/* 232 */                   String Attkey = AttributeIterator.next().toString();
/*     */                   
/* 234 */                   String value = allAttributes.get(Attkey).toString();
/*     */                   
/* 236 */                   AttributeDisplayName = value;
/* 237 */                   out.write("\n    <td width=\"");
/* 238 */                   out.print(secondWidth);
/* 239 */                   out.write("%\"><table  cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\"><tr>\n            <td  colspan=\"3\" width=\"12%\" colspan=\"");
/* 240 */                   out.print(colspanSize);
/* 241 */                   out.write("\" align=\"center\" class=\"tableheadingbborder-anamoly-normal\" style=\"background-color:#fff; border-bottom:1px solid #DCDCDC;\" height=\"22\" nowrap><b style=\"font-size:11px; color:#808080  ; \">");
/* 242 */                   out.print(FormatUtil.getString(value));
/* 243 */                   out.write("</b></td></tr>\n            <tr>\n            <td height=\"22\" class=\"tableheadingbborder-anamoly-normal\" width=\"30%\" align=\"center\" style=\"color:#00008B\" nowrap>");
/* 244 */                   out.print(FormatUtil.getString("am.anomaly.iframe.anomalydata.lastpoll.text"));
/* 245 */                   out.write("</td>\n            <td class=\"tableheadingbborder-anamoly-normal\" width=\"30%\" align=\"center\" style=\"color:#00008B\" nowrap>");
/* 246 */                   out.print(FormatUtil.getString("am.anomaly.iframe.anomalydata.twelevehour.text"));
/* 247 */                   out.write("</td>\n            <td class=\"tableheadingbborder-anamoly-normal\" width=\"30%\" align=\"center\" style=\"color:#00008B\" nowrap>");
/* 248 */                   out.print(FormatUtil.getString("am.anomaly.iframe.anomalydata.sevenjoursegment.text"));
/* 249 */                   out.write("</td></tr>\n            \n</table></td>");
/*     */                 }
/* 251 */                 out.write("\n\n<td class=\"anamoly-bgtab-right-tile\" width=\"19\" colspan=\"");
/* 252 */                 out.print(colspanSize);
/* 253 */                 out.write("\" align=\"right\"><img src=\"../images/spacer.gif\" width=\"19\" height=\"12\"></td>\n\n    </tr>\n  \n   \n        ");
/* 254 */                 Collection FCollection = formatedData.keySet();
/* 255 */                 Iterator FIterator = FCollection.iterator();
/* 256 */                 while (FIterator.hasNext()) {
/* 257 */                   String ResKey = FIterator.next().toString();
/*     */                   
/* 259 */                   Map first = (Map)formatedData.get(ResKey);
/*     */                   
/* 261 */                   Collection c3 = allAttributes.keySet();
/* 262 */                   Iterator i2 = c3.iterator();
/* 263 */                   int p = 0;
/* 264 */                   out.write("\n    \n    ");
/* 265 */                   while (i2.hasNext()) {
/* 266 */                     String Attkey = i2.next().toString();
/*     */                     
/* 268 */                     String v1 = allAttributes.get(Attkey).toString();
/*     */                     
/* 270 */                     if (first.containsKey(Attkey)) {
/* 271 */                       List dataList = (List)first.get(Attkey);
/*     */                       
/*     */ 
/* 274 */                       Map insiderawvalues = (Map)rawvalues.get(Attkey);
/* 275 */                       String rval = insiderawvalues.get(ResKey).toString();
/* 276 */                       String graph_key = ResKey + "#" + Attkey;
/* 277 */                       if (isWindows) {
/* 278 */                         graph_key = ResKey + "#" + (String)tempwindowsattidmapping.get(Attkey);
/* 279 */                         Attkey = (String)tempwindowsattidmapping.get(Attkey);
/*     */                       }
/*     */                       
/* 282 */                       List graphList = null;
/* 283 */                       if (twelveHourGraph.get(graph_key) != null) {
/* 284 */                         graphList = (List)twelveHourGraph.get(graph_key);
/*     */                       }
/*     */                       
/* 287 */                       ArrayList sevenGraphList = null;
/* 288 */                       if (sevenHourGraph.get(graph_key) != null) {
/* 289 */                         sevenGraphList = (ArrayList)sevenHourGraph.get(graph_key);
/*     */                       }
/*     */                       
/* 292 */                       String t12 = ReportDataUtilities.returnStringValue(graphList);
/*     */                       
/* 294 */                       String t7 = ReportDataUtilities.returnStringValue(sevenGraphList);
/*     */                       
/* 296 */                       String image = "No data Available to generate graph";
/*     */                       
/* 298 */                       request.setAttribute("list", graphList);
/* 299 */                       request.setAttribute("sparklist", sevenGraphList);
/*     */                       
/*     */ 
/* 302 */                       out.write("\n       ");
/* 303 */                       if (p == 0) {
/* 304 */                         out.write("\n\n\n\n\n\n <tr>\n<td class=\"anamoly-bgtab-left-tile\" width=\"17\"><img src=\"../images/spacer.gif\" width=\"17\" height=\"12\"></td>  \n\n    \n    <td class=\"tableheadingbborder-anamoly-normal \" title=\"");
/* 305 */                         out.print(dataList.get(0).toString());
/* 306 */                         out.write(34);
/* 307 */                         out.write(62);
/* 308 */                         out.write(32);
/* 309 */                         out.print(dataList.get(0).toString());
/* 310 */                         out.write("</td>\n         \n    ");
/*     */                       }
/* 312 */                       out.write("<td width=\"");
/* 313 */                       out.print(secondWidth);
/* 314 */                       out.write("%\" >\n              \n              <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" ><tr>\n           \n         <td height=\"22\" class=\"tableheadingbborder-anamoly-normal\" style=\"cursor:pointer;\" width=\"30%\" align=\"center\" onclick=\"javascript:getRawData('");
/* 315 */                       out.print(Attkey);
/* 316 */                       out.write(39);
/* 317 */                       out.write(44);
/* 318 */                       out.write(39);
/* 319 */                       out.print(ResKey);
/* 320 */                       out.write(39);
/* 321 */                       out.write(44);
/* 322 */                       out.write(39);
/* 323 */                       out.print(dataList.get(0).toString());
/* 324 */                       out.write(39);
/* 325 */                       out.write(44);
/* 326 */                       out.write(39);
/* 327 */                       out.print(v1);
/* 328 */                       out.write("','false','false')\">");
/* 329 */                       out.print(ReportDataUtilities.roundOff(rval, 2));
/* 330 */                       out.write("</td>\n       \n             ");
/* 331 */                       if ((graphList != null) && (AnamolyMap.containsKey(graph_key))) {
/* 332 */                         out.write("\n              ");
/*     */                         
/* 334 */                         if ((DataAnamolyMap.size() > 0) && (DataAnamolyMap.containsKey(graph_key))) {
/* 335 */                           String c1 = DataAnamolyMap.get(graph_key).toString();
/* 336 */                           if (c1 != null)
/*     */                           {
/*     */ 
/* 339 */                             out.write("\n         <!--<FAILED>-->\n         <td class=\"tableheadingbborder-anamoly-navigation\" align=\"center\" style=\"cursor:pointer;\" width=\"30%\" onclick=\"javascript:getData('tweleve','");
/* 340 */                             out.print(Attkey);
/* 341 */                             out.write(39);
/* 342 */                             out.write(44);
/* 343 */                             out.write(39);
/* 344 */                             out.print(ResKey);
/* 345 */                             out.write(39);
/* 346 */                             out.write(44);
/* 347 */                             out.write(39);
/* 348 */                             out.print(timestampsparams[0]);
/* 349 */                             out.write(39);
/* 350 */                             out.write(44);
/* 351 */                             out.write(39);
/* 352 */                             out.print(timestampsparams[4]);
/* 353 */                             out.write(39);
/* 354 */                             out.write(44);
/* 355 */                             out.write(39);
/* 356 */                             out.print(timestampsparams[3]);
/* 357 */                             out.write(39);
/* 358 */                             out.write(44);
/* 359 */                             out.print(c1);
/* 360 */                             out.write(",'false');\">");
/*     */                             
/* 362 */                             SparkSeries _jspx_th_awolf_005fsparkseries_005f0 = (SparkSeries)this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.get(SparkSeries.class);
/* 363 */                             _jspx_th_awolf_005fsparkseries_005f0.setPageContext(_jspx_page_context);
/* 364 */                             _jspx_th_awolf_005fsparkseries_005f0.setParent(null);
/*     */                             
/* 366 */                             _jspx_th_awolf_005fsparkseries_005f0.setListName("list");
/*     */                             
/* 368 */                             _jspx_th_awolf_005fsparkseries_005f0.setHeight("20");
/*     */                             
/* 370 */                             _jspx_th_awolf_005fsparkseries_005f0.setWidth("40");
/*     */                             
/* 372 */                             _jspx_th_awolf_005fsparkseries_005f0.setSeriesColor(100 + p);
/* 373 */                             int _jspx_eval_awolf_005fsparkseries_005f0 = _jspx_th_awolf_005fsparkseries_005f0.doStartTag();
/* 374 */                             if (_jspx_th_awolf_005fsparkseries_005f0.doEndTag() == 5) {
/* 375 */                               this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.reuse(_jspx_th_awolf_005fsparkseries_005f0); return;
/*     */                             }
/*     */                             
/* 378 */                             this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.reuse(_jspx_th_awolf_005fsparkseries_005f0);
/* 379 */                             out.write("\n           ");
/*     */                           }
/*     */                         } else {
/* 382 */                           out.write("\n           <td class=\"tableheadingbborder-anamoly-normal\" align=\"center\" style=\"cursor:pointer;\" width=\"30%\" onclick=\"javascript:getData('tweleve','");
/* 383 */                           out.print(Attkey);
/* 384 */                           out.write(39);
/* 385 */                           out.write(44);
/* 386 */                           out.write(39);
/* 387 */                           out.print(ResKey);
/* 388 */                           out.write(39);
/* 389 */                           out.write(44);
/* 390 */                           out.write(39);
/* 391 */                           out.print(timestampsparams[0]);
/* 392 */                           out.write(39);
/* 393 */                           out.write(44);
/* 394 */                           out.write(39);
/* 395 */                           out.print(timestampsparams[4]);
/* 396 */                           out.write(39);
/* 397 */                           out.write(44);
/* 398 */                           out.write(39);
/* 399 */                           out.print(timestampsparams[3]);
/* 400 */                           out.write("','false','false');\">");
/*     */                           
/* 402 */                           SparkSeries _jspx_th_awolf_005fsparkseries_005f1 = (SparkSeries)this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.get(SparkSeries.class);
/* 403 */                           _jspx_th_awolf_005fsparkseries_005f1.setPageContext(_jspx_page_context);
/* 404 */                           _jspx_th_awolf_005fsparkseries_005f1.setParent(null);
/*     */                           
/* 406 */                           _jspx_th_awolf_005fsparkseries_005f1.setListName("list");
/*     */                           
/* 408 */                           _jspx_th_awolf_005fsparkseries_005f1.setHeight("20");
/*     */                           
/* 410 */                           _jspx_th_awolf_005fsparkseries_005f1.setWidth("40");
/*     */                           
/* 412 */                           _jspx_th_awolf_005fsparkseries_005f1.setSeriesColor(p);
/* 413 */                           int _jspx_eval_awolf_005fsparkseries_005f1 = _jspx_th_awolf_005fsparkseries_005f1.doStartTag();
/* 414 */                           if (_jspx_th_awolf_005fsparkseries_005f1.doEndTag() == 5) {
/* 415 */                             this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.reuse(_jspx_th_awolf_005fsparkseries_005f1); return;
/*     */                           }
/*     */                           
/* 418 */                           this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.reuse(_jspx_th_awolf_005fsparkseries_005f1);
/* 419 */                           out.write("\n           ");
/*     */                         }
/* 421 */                         out.write("\n            </td>");
/*     */                       } else {
/* 423 */                         out.write("\n             <td class=\"tableheadingbborder-anamoly-normal\" width=\"30%\" align=\"center\">-\n           \n            </td>\n            ");
/*     */                       }
/* 425 */                       out.write("\n            ");
/* 426 */                       if (sevenGraphList != null)
/*     */                       {
/* 428 */                         out.write("\n           <td class=\"tableheadingbborder-anamoly-normal\" align=\"center\" style=\"cursor:pointer;\" width=\"30%\" nowrap onclick=\"javascript:getData('seven','");
/* 429 */                         out.print(Attkey);
/* 430 */                         out.write(39);
/* 431 */                         out.write(44);
/* 432 */                         out.write(39);
/* 433 */                         out.print(ResKey);
/* 434 */                         out.write(39);
/* 435 */                         out.write(44);
/* 436 */                         out.write(39);
/* 437 */                         out.print(timestampsparams[0]);
/* 438 */                         out.write(39);
/* 439 */                         out.write(44);
/* 440 */                         out.write(39);
/* 441 */                         out.print(timestampsparams[2]);
/* 442 */                         out.write(39);
/* 443 */                         out.write(44);
/* 444 */                         out.write(39);
/* 445 */                         out.print(timestampsparams[3]);
/* 446 */                         out.write("','false','false');\">");
/*     */                         
/* 448 */                         SparkLine _jspx_th_awolf_005fsparkline_005f0 = (SparkLine)this._005fjspx_005ftagPool_005fawolf_005fsparkline_0026_005fplots_005flistName_005flineColor_005fheight_005fnobody.get(SparkLine.class);
/* 449 */                         _jspx_th_awolf_005fsparkline_005f0.setPageContext(_jspx_page_context);
/* 450 */                         _jspx_th_awolf_005fsparkline_005f0.setParent(null);
/*     */                         
/* 452 */                         _jspx_th_awolf_005fsparkline_005f0.setListName("sparklist");
/*     */                         
/* 454 */                         _jspx_th_awolf_005fsparkline_005f0.setHeight(15);
/*     */                         
/* 456 */                         _jspx_th_awolf_005fsparkline_005f0.setPlots(7);
/*     */                         
/* 458 */                         _jspx_th_awolf_005fsparkline_005f0.setLineColor(String.valueOf(p));
/* 459 */                         int _jspx_eval_awolf_005fsparkline_005f0 = _jspx_th_awolf_005fsparkline_005f0.doStartTag();
/* 460 */                         if (_jspx_th_awolf_005fsparkline_005f0.doEndTag() == 5) {
/* 461 */                           this._005fjspx_005ftagPool_005fawolf_005fsparkline_0026_005fplots_005flistName_005flineColor_005fheight_005fnobody.reuse(_jspx_th_awolf_005fsparkline_005f0); return;
/*     */                         }
/*     */                         
/* 464 */                         this._005fjspx_005ftagPool_005fawolf_005fsparkline_0026_005fplots_005flistName_005flineColor_005fheight_005fnobody.reuse(_jspx_th_awolf_005fsparkline_005f0);
/* 465 */                         out.write("\n             \n            </td>\n            ");
/*     */                       } else {
/* 467 */                         out.write("\n             <td class=\"tableheadingbborder-anamoly-normal\" align=\"center\" width=\"30%\">-\n             \n            </td>\n            ");
/*     */                       }
/* 469 */                       out.write("\n             \n           \n</table></td>\n\n         ");
/* 470 */                       p++;
/* 471 */                     } else { if (p == 0) {
/* 472 */                         out.write(" \n        <tr>\n            <td class=\"anamoly-mid-left-corn\" width=\"13\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\"></td>  \n            <td class=\"tableheadingbborder-anamoly-normal \" title=\"");
/* 473 */                         out.print(ReportUtilities.getLabelName(ResKey));
/* 474 */                         out.write(34);
/* 475 */                         out.write(62);
/* 476 */                         out.print(ReportUtilities.getLabelName(ResKey));
/* 477 */                         out.write("</td>\n          ");
/*     */                       }
/* 479 */                       out.write("<td   width=");
/* 480 */                       out.print(secondWidth);
/* 481 */                       out.write("%><table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" ><tr>\n            <td class=\"tableheadingbborder-anamoly-normal\" width=\"30%\" align=\"center\">-</td>\n           <td class=\"tableheadingbborder-anamoly-normal\" width=\"30%\" align=\"center\">-</td>\n            <td class=\"tableheadingbborder-anamoly-normal\" width=\"30%\" align=\"center\">-</td>\n            </tr>\n            \n</table></td>  \n             \n         ");
/* 482 */                       p++;
/*     */                     } }
/* 484 */                   out.write("\n            \n      <td class=\"anamoly-bgtab-right-tile\" width=\"19\" colspan=\"");
/* 485 */                   out.print(colspanSize);
/* 486 */                   out.write("\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\"></td>\n     </tr> \n");
/*     */                 }
/* 488 */                 out.write("\n<tr>\n\t<td class=\"anamoly-bgtab-left-btm\" width=\"17\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\"></td>\n\t\t\t<td class=\"anamoly-mid-tile-btm\" width=\"98%\" colspan=\"");
/* 489 */                 out.print(colspanSize);
/* 490 */                 out.write("\"></td>                        \n\t\t\t<td class=\"anamoly-bgtab-right-btm\" width=\"19\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\"></td>\n</tr>\n</table>\n</td>\n\n<td  width=\"19\"><img src=\"../images/spacer.gif\" width=\"19\" height=\"19\"></td>\n    </tr>\n    \n\n\n\n        \n ");
/*     */               } else {
/* 492 */                 out.write("\n \n\n\n\n\n\t<tr>\n\t\t<td width=\"17\" ><img src=\"../images/spacer.gif\" width=\"17\" height=\"19\" /></td>\n\t\t<td width=\"98%\" class=\"anamoly-mid-color\">\n\n\t\t\t\n\n\n\n\n\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"13\" class=\"anamoly-blue-rig-corn\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"25\" /></td>\n\t\t\t\t\t<td width=\"98%\" class=\"anamoly-mid-tile \">\n\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" align=\"center\">\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td  width=\"15%\" class=\"anamoly-font-text\"> </td>\n\t\t\t\t\t\t\t<td  width=\"85%\" align=\"center\" class=\"anamoly-font-text\"></td>\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"13\" class=\"anamoly-bgtab-right\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"25\" /></td>\n\t\t\t\t</tr>\n\n                 <tr>\n\t\t\t\t\t<td width=\"13\" class=\"anamoly-bgtab-left-tile\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\" /></td>\n\t\t\t\t\t<td width=\"100%\" class=\"anamoly-mid-bgcolor\">\n\t\t\t\n                       \t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\t\t\t\t\t\t\n");
/* 493 */                 out.write("\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\n                                        <tr>\n\t\t\t\t\t\t\t\t\t\t\t<td class=\"tableheadingbborder-oracle-normal\"></td>\n\t\t\t\t\t\t\t\t\t\t\t<td><table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" border=\"0\"><tr>\n                                     <td height=\"22\"  colspan=\"3\" class=\"tdindent\">\n                                         <table align=\"center\" width=\"98%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" >\n                                            <tr>\n                                                <td width=\"5%\" align=\"center\"><img src=\"../images/icon_anomaly_nodata.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n                                                <td width=\"95%\" class=\"anomaly-nodata\">\n                                                    ");
/* 494 */                 if (ReportDataUtilities.getStartTimeOfAPM()) {
/* 495 */                   out.write("\n                                                    ");
/* 496 */                   out.print(FormatUtil.getString("am.webclient.anomaly.nodata.oneweek.text", new String[] { OEMUtil.getOEMString("product.name") }));
/* 497 */                   out.write("\n                                                    \n                                                    ");
/*     */                 } else {
/* 499 */                   out.write("\n                                                  ");
/* 500 */                   out.print(FormatUtil.getString("am.webclient.anomaly.nodata.general.text"));
/* 501 */                   out.write("\n                                                     ");
/*     */                 }
/* 503 */                 out.write("\n                                                </td>\n                                            </tr>\n                                          </table>\n                                        \n                                    \n                                         \n                                             </td>\n\n\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t</tr></table></td>\t\n\t\t\t\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t\t\t</table>                         \n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"19\" class=\"anamoly-bgtab-right-tile\"><img src=\"../images/spacer.gif\" width=\"19\" height=\"12\" /></td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"13\" class=\"anamoly-bgtab-left-btm\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\" /></td>\n\t\t\t\t\t<td width=\"98%\" class=\"anamoly-mid-tile-btm\"></td>\n\t\t\t\t\t<td width=\"13\" class=\"anamoly-bgtab-right-btm\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\" /></td>\n\t\t\t\t</tr>\n                                \n\t\t\t</table>\n\n\t\t</td>\n\t\t<td width=\"19\"><img src=\"../images/spacer.gif\" width=\"19\" height=\"19\" /></td>\n\t</tr>\n\n\n\t\n</table>\n");
/* 504 */                 out.write("\n\n\n\n\n\n\n\n\n\n\n \n \n    ");
/*     */               }
/*     */             }
/* 507 */             else if (formatedData.size() > 0)
/*     */             {
/*     */ 
/* 510 */               out.write("\n \n   \n    \n\n\n<!-- Second table starts-->\n\n\n ");
/*     */               
/* 512 */               int SInitialWidth = 100;
/* 513 */               int SWidth = 100;
/* 514 */               Collection attributeIDMapCollection = attributeIDMap.keySet();
/* 515 */               Iterator AttributeIdIterator = attributeIDMapCollection.iterator();
/*     */               
/* 517 */               while (AttributeIdIterator.hasNext()) {
/* 518 */                 String k11 = AttributeIdIterator.next().toString();
/*     */                 
/* 520 */                 if (formatedData.containsKey(k11))
/*     */                 {
/*     */ 
/*     */ 
/* 524 */                   List l1 = (List)attributeIDMap.get(k11);
/* 525 */                   int secondColspanSize = l1.size() + 1;
/* 526 */                   if (l1.size() > 3) {
/* 527 */                     SInitialWidth = 100 * l1.size();
/*     */                   }
/* 529 */                   if (l1.size() > 1) {
/* 530 */                     SWidth = 100 / l1.size();
/*     */                   }
/* 532 */                   out.write("\n       <tr>\n        <td ><img src=\"../images/spacer.gif\" width=\"17\" height=\"25\" /></td>\n    <td width=\"");
/* 533 */                   out.print(SInitialWidth);
/* 534 */                   out.write("%\"  class=\"anamoly-second-font\"  >");
/* 535 */                   out.print(FormatUtil.getString(k11));
/* 536 */                   out.write(" </td>\n    <td ><img src=\"../images/spacer.gif\" width=\"19\" height=\"25\" /></td>\n\n    </tr>\n           \n       <tr>\n            <td width=\"17\" ><img src=\"../images/spacer.gif\" width=\"17\" height=\"19\" /></td>                                    \n            <td width=\"");
/* 537 */                   out.print(SInitialWidth);
/* 538 */                   out.write("%\"  class=\"anamoly-mid-color\" style=\"background-color:fff;\">\n            \n            \n            \n            \n        <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" >\n\t<tr>\n            <td width=\"17\" class=\"anamoly-blue-rig-corn\"><img src=\"../images/spacer.gif\" width=\"17\" height=\"25\" /></td>\n            <td width=\"100%\" class=\"anamoly-mid-tile\" colspan=\"");
/* 539 */                   out.print(secondColspanSize);
/* 540 */                   out.write("\"></td>\n            <td class=\"anamoly-bgtab-right\" width=\"19\" ><img src=\"../images/spacer.gif\" width=\"19\" height=\"25\"></td>\n\n        </tr>\n        \n        \n        \n        <tr>\n            <td class=\"anamoly-bgtab-left-tile \"width=\"13\" ><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\"></td> \n            <td  class=\"tableheadingbborder-anamoly-normal\" style=\"color:#00008B\" >");
/* 541 */                   out.print(FormatUtil.getString("am.webclient.admin.table.heading"));
/* 542 */                   out.write("</td>\n       ");
/* 543 */                   for (int j = 0; j < l1.size(); j++) {
/* 544 */                     String AttIdkey = l1.get(j).toString();
/*     */                     
/* 546 */                     String value1 = allAttributes.get(AttIdkey).toString();
/* 547 */                     out.write("\n        <td width=\"");
/* 548 */                     out.print(SWidth);
/* 549 */                     out.write("%\" ><table  cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\" ><tr>\n                <td  colspan=\"3\" width=\"12%\" align=\"center\" class=\"tableheadingbborder-anamoly-normal\" style=\"background-color:#fff; border-bottom:1px solid #DCDCDC;\" height=\"22\" nowrap><b style=\"font-size:11px; color:#808080  ;\" >");
/* 550 */                     out.print(FormatUtil.getString(value1));
/* 551 */                     out.write("</b></td></tr>\n            <tr>\n            <td height=\"22\" class=\"tableheadingbborder-anamoly-normal\" align=\"center\" width=\"30%\" style=\"color:#00008B\" nowrap>");
/* 552 */                     out.print(FormatUtil.getString("am.anomaly.iframe.anomalydata.lastpoll.text"));
/* 553 */                     out.write("</td>\n            <td class=\"tableheadingbborder-anamoly-normal\" width=\"30%\" align=\"center\" style=\"color:#00008B\" nowrap>");
/* 554 */                     out.print(FormatUtil.getString("am.anomaly.iframe.anomalydata.twelevehour.text"));
/* 555 */                     out.write("</td>\n            <td class=\"tableheadingbborder-anamoly-normal\" width=\"30%\" align=\"center\" style=\"color:#00008B\" nowrap>");
/* 556 */                     out.print(FormatUtil.getString("am.anomaly.iframe.anomalydata.sevenjoursegment.text"));
/* 557 */                     out.write("</td></tr>\n            \n</table></td>\n    \n     ");
/*     */                   }
/* 559 */                   out.write(" <td class=\"anamoly-bgtab-right-tile\" width=\"19\" ><img src=\"../images/spacer.gif\" width=\"19\" height=\"12\"></td>\n </tr>\n    \n        ");
/*     */                   
/*     */ 
/* 562 */                   Map datamap = (Map)formatedData.get(k11);
/*     */                   
/* 564 */                   Collection dataMapCollection = datamap.keySet();
/* 565 */                   Iterator DatamapIterator = dataMapCollection.iterator();
/* 566 */                   while (DatamapIterator.hasNext()) {
/* 567 */                     String residkey = DatamapIterator.next().toString();
/*     */                     
/* 569 */                     Map attMap = (Map)datamap.get(residkey);
/* 570 */                     int u = 0;
/*     */                     
/* 572 */                     out.write("\n           \n            ");
/* 573 */                     for (int j = 0; j < l1.size(); j++) {
/* 574 */                       out.write("\n            \n            ");
/* 575 */                       String AttIdkey = l1.get(j).toString();
/* 576 */                       String v2 = allAttributes.get(AttIdkey).toString();
/*     */                       
/*     */ 
/* 579 */                       Map insiderawvalues1 = (Map)rawvalues.get(AttIdkey);
/*     */                       
/* 581 */                       if (insiderawvalues1.containsKey(residkey))
/*     */                       {
/* 583 */                         String rval1 = insiderawvalues1.get(residkey).toString();
/* 584 */                         if (attMap.containsKey(AttIdkey))
/*     */                         {
/* 586 */                           String graph_Key = residkey + "#" + AttIdkey;
/* 587 */                           if (isWindows) {
/* 588 */                             graph_Key = residkey + "#" + (String)tempwindowsattidmapping.get(AttIdkey);
/* 589 */                             AttIdkey = (String)tempwindowsattidmapping.get(AttIdkey);
/*     */                           }
/*     */                           
/* 592 */                           List l2 = (List)attMap.get(AttIdkey);
/*     */                           
/* 594 */                           List l5 = null;
/* 595 */                           if (twelveHourGraph.get(graph_Key) != null) {
/* 596 */                             l5 = (List)twelveHourGraph.get(graph_Key);
/*     */                           }
/*     */                           
/* 599 */                           ArrayList l6 = null;
/* 600 */                           if (sevenHourGraph.get(graph_Key) != null) {
/* 601 */                             l6 = (ArrayList)sevenHourGraph.get(graph_Key);
/*     */                           }
/* 603 */                           String g12 = ReportDataUtilities.returnStringValue(l5);
/*     */                           
/* 605 */                           String g7 = ReportDataUtilities.returnStringValue(l6);
/*     */                           
/* 607 */                           request.setAttribute("Glist", l5);
/* 608 */                           request.setAttribute("Slist", l6);
/*     */                           
/* 610 */                           String cName = ReportDataUtilities.getChildName(residkey, ReportUtilities.getLabelName(residkey));
/* 611 */                           if (("711".equals(AttIdkey)) && (cName.indexOf("DiskUtilization") > 0))
/*     */                           {
/* 613 */                             cName = ReportDataUtilities.getLabelName(residkey);
/* 614 */                             cName = FormatUtil.findReplace(cName, "DiskUtilization", "");
/*     */                           }
/*     */                           
/*     */ 
/*     */ 
/* 619 */                           if (u == 0)
/*     */                           {
/*     */ 
/*     */ 
/* 623 */                             out.write("\n           \n\n\n\n\n\n       \n<tr>\n<td class=\"anamoly-bgtab-left-tile\" width=\"13\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\"></td>  \n    <td class=\"tableheadingbborder-anamoly-normal \" title=\"");
/* 624 */                             out.print(cName);
/* 625 */                             out.write(34);
/* 626 */                             out.write(62);
/* 627 */                             out.write(32);
/* 628 */                             out.print(cName);
/* 629 */                             out.write("</td>\n\n\n         \n            ");
/*     */                           }
/* 631 */                           out.write("\n            \n            <td width=\"");
/* 632 */                           out.print(SWidth);
/* 633 */                           out.write("%\" >\n              \n              <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" >\n                  <tr>\n                      \n          \n         <td height=\"22\" class=\"tableheadingbborder-anamoly-normal\" style=\"cursor:pointer;\" width=\"30%\" align=\"center\" onclick=\"javascript:getRawData('");
/* 634 */                           out.print(AttIdkey);
/* 635 */                           out.write(39);
/* 636 */                           out.write(44);
/* 637 */                           out.write(39);
/* 638 */                           out.print(residkey);
/* 639 */                           out.write(39);
/* 640 */                           out.write(44);
/* 641 */                           out.write(39);
/* 642 */                           out.print(cName);
/* 643 */                           out.write(39);
/* 644 */                           out.write(44);
/* 645 */                           out.write(39);
/* 646 */                           out.print(v2);
/* 647 */                           out.write("','false','true')\">");
/* 648 */                           out.print(ReportDataUtilities.roundOff(rval1, 2));
/* 649 */                           out.write("</td>\n      \n            \n             ");
/* 650 */                           if ((l5 != null) && (AnamolyMap.containsKey(graph_Key))) {
/* 651 */                             out.write("\n             ");
/* 652 */                             if ((DataAnamolyMap.size() > 0) && (DataAnamolyMap.containsKey(graph_Key))) {
/* 653 */                               String c2 = DataAnamolyMap.get(graph_Key).toString();
/* 654 */                               if (c2 != null)
/*     */                               {
/* 656 */                                 out.write("\n           <!--<FAILED>-->\n         <td class=\"tableheadingbborder-anamoly-navigation\" style=\"cursor:pointer;\" align=\"center\"  width=\"30%\"  nowrap onclick=\"javascript:getData('tweleve','");
/* 657 */                                 out.print(AttIdkey);
/* 658 */                                 out.write(39);
/* 659 */                                 out.write(44);
/* 660 */                                 out.write(39);
/* 661 */                                 out.print(residkey);
/* 662 */                                 out.write(39);
/* 663 */                                 out.write(44);
/* 664 */                                 out.write(39);
/* 665 */                                 out.print(timestampsparams[0]);
/* 666 */                                 out.write(39);
/* 667 */                                 out.write(44);
/* 668 */                                 out.write(39);
/* 669 */                                 out.print(timestampsparams[4]);
/* 670 */                                 out.write(39);
/* 671 */                                 out.write(44);
/* 672 */                                 out.write(39);
/* 673 */                                 out.print(timestampsparams[3]);
/* 674 */                                 out.write(39);
/* 675 */                                 out.write(44);
/* 676 */                                 out.write(39);
/* 677 */                                 out.print(c2);
/* 678 */                                 out.write("','true');\">");
/*     */                                 
/* 680 */                                 SparkSeries _jspx_th_awolf_005fsparkseries_005f2 = (SparkSeries)this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.get(SparkSeries.class);
/* 681 */                                 _jspx_th_awolf_005fsparkseries_005f2.setPageContext(_jspx_page_context);
/* 682 */                                 _jspx_th_awolf_005fsparkseries_005f2.setParent(null);
/*     */                                 
/* 684 */                                 _jspx_th_awolf_005fsparkseries_005f2.setListName("Glist");
/*     */                                 
/* 686 */                                 _jspx_th_awolf_005fsparkseries_005f2.setHeight("20");
/*     */                                 
/* 688 */                                 _jspx_th_awolf_005fsparkseries_005f2.setWidth("40");
/*     */                                 
/* 690 */                                 _jspx_th_awolf_005fsparkseries_005f2.setSeriesColor(100 + u);
/* 691 */                                 int _jspx_eval_awolf_005fsparkseries_005f2 = _jspx_th_awolf_005fsparkseries_005f2.doStartTag();
/* 692 */                                 if (_jspx_th_awolf_005fsparkseries_005f2.doEndTag() == 5) {
/* 693 */                                   this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.reuse(_jspx_th_awolf_005fsparkseries_005f2); return;
/*     */                                 }
/*     */                                 
/* 696 */                                 this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.reuse(_jspx_th_awolf_005fsparkseries_005f2);
/* 697 */                                 out.write("</td>\n         ");
/*     */                               }
/*     */                             } else {
/* 700 */                               out.write("\n         <td class=\"tableheadingbborder-anamoly-normal\" style=\"cursor:pointer;\" align=\"center\"  width=\"30%\"  nowrap onclick=\"javascript:getData('tweleve','");
/* 701 */                               out.print(AttIdkey);
/* 702 */                               out.write(39);
/* 703 */                               out.write(44);
/* 704 */                               out.write(39);
/* 705 */                               out.print(residkey);
/* 706 */                               out.write(39);
/* 707 */                               out.write(44);
/* 708 */                               out.write(39);
/* 709 */                               out.print(timestampsparams[0]);
/* 710 */                               out.write(39);
/* 711 */                               out.write(44);
/* 712 */                               out.write(39);
/* 713 */                               out.print(timestampsparams[4]);
/* 714 */                               out.write(39);
/* 715 */                               out.write(44);
/* 716 */                               out.write(39);
/* 717 */                               out.print(timestampsparams[3]);
/* 718 */                               out.write("','false','true');\">");
/*     */                               
/* 720 */                               SparkSeries _jspx_th_awolf_005fsparkseries_005f3 = (SparkSeries)this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.get(SparkSeries.class);
/* 721 */                               _jspx_th_awolf_005fsparkseries_005f3.setPageContext(_jspx_page_context);
/* 722 */                               _jspx_th_awolf_005fsparkseries_005f3.setParent(null);
/*     */                               
/* 724 */                               _jspx_th_awolf_005fsparkseries_005f3.setListName("Glist");
/*     */                               
/* 726 */                               _jspx_th_awolf_005fsparkseries_005f3.setHeight("20");
/*     */                               
/* 728 */                               _jspx_th_awolf_005fsparkseries_005f3.setWidth("40");
/*     */                               
/* 730 */                               _jspx_th_awolf_005fsparkseries_005f3.setSeriesColor(u);
/* 731 */                               int _jspx_eval_awolf_005fsparkseries_005f3 = _jspx_th_awolf_005fsparkseries_005f3.doStartTag();
/* 732 */                               if (_jspx_th_awolf_005fsparkseries_005f3.doEndTag() == 5) {
/* 733 */                                 this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.reuse(_jspx_th_awolf_005fsparkseries_005f3); return;
/*     */                               }
/*     */                               
/* 736 */                               this._005fjspx_005ftagPool_005fawolf_005fsparkseries_0026_005fwidth_005fseriesColor_005flistName_005fheight_005fnobody.reuse(_jspx_th_awolf_005fsparkseries_005f3);
/* 737 */                               out.write("</td>\n            ");
/*     */                             }
/* 739 */                             out.write("\n           ");
/*     */                           } else {
/* 741 */                             out.write("\n          <td class=\"tableheadingbborder-anamoly-normal\" align=\"center\"  width=\"30%\">-</td>\n            ");
/*     */                           }
/* 743 */                           out.write("\n             ");
/* 744 */                           if (l6 != null) {
/* 745 */                             out.write("\n             <td class=\"tableheadingbborder-anamoly-normal\" style=\"cursor:pointer;\" align=\"center\"  width=\"30%\" nowrap onclick=\"javascript:getData('seven','");
/* 746 */                             out.print(AttIdkey);
/* 747 */                             out.write(39);
/* 748 */                             out.write(44);
/* 749 */                             out.write(39);
/* 750 */                             out.print(residkey);
/* 751 */                             out.write(39);
/* 752 */                             out.write(44);
/* 753 */                             out.write(39);
/* 754 */                             out.print(timestampsparams[0]);
/* 755 */                             out.write(39);
/* 756 */                             out.write(44);
/* 757 */                             out.write(39);
/* 758 */                             out.print(timestampsparams[2]);
/* 759 */                             out.write(39);
/* 760 */                             out.write(44);
/* 761 */                             out.write(39);
/* 762 */                             out.print(timestampsparams[3]);
/* 763 */                             out.write("','false','true');\">");
/*     */                             
/* 765 */                             SparkLine _jspx_th_awolf_005fsparkline_005f1 = (SparkLine)this._005fjspx_005ftagPool_005fawolf_005fsparkline_0026_005fplots_005flistName_005flineColor_005fheight_005fnobody.get(SparkLine.class);
/* 766 */                             _jspx_th_awolf_005fsparkline_005f1.setPageContext(_jspx_page_context);
/* 767 */                             _jspx_th_awolf_005fsparkline_005f1.setParent(null);
/*     */                             
/* 769 */                             _jspx_th_awolf_005fsparkline_005f1.setListName("Slist");
/*     */                             
/* 771 */                             _jspx_th_awolf_005fsparkline_005f1.setHeight(15);
/*     */                             
/* 773 */                             _jspx_th_awolf_005fsparkline_005f1.setPlots(7);
/*     */                             
/* 775 */                             _jspx_th_awolf_005fsparkline_005f1.setLineColor(String.valueOf(u));
/* 776 */                             int _jspx_eval_awolf_005fsparkline_005f1 = _jspx_th_awolf_005fsparkline_005f1.doStartTag();
/* 777 */                             if (_jspx_th_awolf_005fsparkline_005f1.doEndTag() == 5) {
/* 778 */                               this._005fjspx_005ftagPool_005fawolf_005fsparkline_0026_005fplots_005flistName_005flineColor_005fheight_005fnobody.reuse(_jspx_th_awolf_005fsparkline_005f1); return;
/*     */                             }
/*     */                             
/* 781 */                             this._005fjspx_005ftagPool_005fawolf_005fsparkline_0026_005fplots_005flistName_005flineColor_005fheight_005fnobody.reuse(_jspx_th_awolf_005fsparkline_005f1);
/* 782 */                             out.write("</td>\n             ");
/*     */                           } else {
/* 784 */                             out.write("\n            <td class=\"tableheadingbborder-anamoly-normal\" align=\"center\"  width=\"30%\">-</td>\n              ");
/*     */                           }
/* 786 */                           out.write("\n          </tr>\n          \n  </table></td>\n\n        ");
/*     */                         }
/*     */                         else {
/* 789 */                           out.write("\n               ");
/* 790 */                           if (u == 0)
/*     */                           {
/* 792 */                             String cName = ReportDataUtilities.getChildName(residkey, ReportUtilities.getLabelName(residkey));
/* 793 */                             if (("711".equals(AttIdkey)) && (cName.indexOf("DiskUtilization") > 0))
/*     */                             {
/* 795 */                               cName = ReportDataUtilities.getLabelName(residkey);
/* 796 */                               cName = FormatUtil.findReplace(cName, "DiskUtilization", "");
/*     */                             }
/*     */                             
/*     */ 
/* 800 */                             out.write("\n           <tr>\n<td class=\"anamoly-bgtab-left-tile\"  width=\"13\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\"></td>  \n    <td class=\"tableheadingbborder-anamoly-normal \" title=\"");
/* 801 */                             out.print(cName);
/* 802 */                             out.write(34);
/* 803 */                             out.write(62);
/* 804 */                             out.write(32);
/* 805 */                             out.print(cName);
/* 806 */                             out.write("</td>\n\n\n         \n            ");
/*     */                           }
/* 808 */                           out.write("\n          <td   width=\"");
/* 809 */                           out.print(SWidth);
/* 810 */                           out.write("%\"><table  cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\" ><tr>\n            <td class=\"tableheadingbborder-anamoly-normal\" align=\"center\"  width=\"30%\">-</td>\n           <td class=\"tableheadingbborder-anamoly-normal\" align=\"center\" width=\"30%\">-</td>\n            <td class=\"tableheadingbborder-anamoly-normal\" align=\"center\"  width=\"30%\">-</td></tr>\n            \n</table></td>    \n        ");
/*     */                         }
/*     */                         
/* 813 */                         u++; }
/* 814 */                       out.write("\n        \n            ");
/*     */                     }
/* 816 */                     out.write("\n              <td class=\"anamoly-bgtab-right-tile\"   width=\"13\" ><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\"></td>\n     </tr> \n         ");
/*     */                   }
/* 818 */                   out.write("\n            \n\n\n\n\n           \n            <tr>\n\t<td class=\"anamoly-bgtab-left-btm\" width=\"13\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\"></td>\n\t\t\t<td class=\"anamoly-mid-tile-btm\" width=\"98%\" colspan=\"");
/* 819 */                   out.print(secondColspanSize);
/* 820 */                   out.write("\"></td>                        \n\t\t\t<td class=\"anamoly-bgtab-right-btm\" width=\"13\"><img src=\"../images/spacer.gif\" width=\"13\" height=\"12\"></td>\n</tr>\n</table>\n\n</td>\n\n<td  width=\"19\"><img src=\"../images/spacer.gif\" width=\"19\" height=\"19\"></td>\n    </tr>\n    \n\n\n\n\n\n\n\n\n       ");
/*     */                 }
/*     */               }
/* 823 */               out.write("\n   \n\n\n");
/*     */             }
/*     */             
/* 826 */             out.write(10);
/* 827 */             out.write(32);
/*     */           } }
/* 829 */         out.write("\n</table>\n");
/*     */       }
/*     */       catch (Exception ex) {
/* 832 */         ex.printStackTrace();
/*     */       }
/* 834 */       out.write("\n\n \n\n");
/*     */     } catch (Throwable t) {
/* 836 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 837 */         out = _jspx_out;
/* 838 */         if ((out != null) && (out.getBufferSize() != 0))
/* 839 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 840 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 843 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AjaxAttributeDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */