/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.bean.SummaryBean;
/*     */ import com.adventnet.appmanager.struts.actions.ComparingSla;
/*     */ import com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.awolf.tags.BarChart;
/*     */ import java.io.IOException;
/*     */ import java.util.Date;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ 
/*     */ public final class SegmentedHistory_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  33 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  50 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  54 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  57 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  58 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  59 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  60 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  61 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  62 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  66 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  67 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  68 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  69 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  70 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  71 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer.release();
/*  72 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  79 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  82 */     JspWriter out = null;
/*  83 */     Object page = this;
/*  84 */     JspWriter _jspx_out = null;
/*  85 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  89 */       response.setContentType("text/html;charset=UTF-8");
/*  90 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  92 */       _jspx_page_context = pageContext;
/*  93 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  94 */       ServletConfig config = pageContext.getServletConfig();
/*  95 */       session = pageContext.getSession();
/*  96 */       out = pageContext.getOut();
/*  97 */       _jspx_out = out;
/*     */       
/*  99 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/appmanager.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n\n");
/* 100 */       SummaryBean sumgraph = null;
/* 101 */       sumgraph = (SummaryBean)_jspx_page_context.getAttribute("sumgraph", 2);
/* 102 */       if (sumgraph == null) {
/* 103 */         sumgraph = new SummaryBean();
/* 104 */         _jspx_page_context.setAttribute("sumgraph", sumgraph, 2);
/*     */       }
/* 106 */       out.write(10);
/* 107 */       out.write(10);
/* 108 */       HistoryDataGraphUtil graph = null;
/* 109 */       graph = (HistoryDataGraphUtil)_jspx_page_context.getAttribute("graph", 1);
/* 110 */       if (graph == null) {
/* 111 */         graph = new HistoryDataGraphUtil();
/* 112 */         _jspx_page_context.setAttribute("graph", graph, 1);
/*     */       }
/* 114 */       out.write("\n\n\n\n<html>\n<head>\n\n<link href=\"/images/");
/* 115 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 117 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n\n</head>\n<body>\n");
/* 118 */       response.setContentType("text/html;charset=UTF-8");
/* 119 */       out.write(10);
/*     */       
/* 121 */       ComparingSla cs = new ComparingSla();
/* 122 */       boolean isdata = false;
/* 123 */       String resname = (String)request.getAttribute("resourcename");
/* 124 */       String nameofmonitor = (String)request.getAttribute("monitortype");
/* 125 */       String unit = (String)pageContext.findAttribute("unit");
/* 126 */       Hashtable hourhash = (Hashtable)request.getAttribute("DATA");
/*     */       
/* 128 */       String resID = (String)request.getAttribute("resourceid");
/* 129 */       String attID = (String)request.getAttribute("attributeid");
/* 130 */       String period = (String)request.getAttribute("period");
/* 131 */       String startime = FormatUtil.formatDT((Long)request.getAttribute("STIME") + "");
/* 132 */       String endtime = FormatUtil.formatDT((Long)request.getAttribute("ETIME") + "");
/* 133 */       String valueforperiod = cs.getValueForPeriod(period);
/* 134 */       String bHr = (String)request.getAttribute("bRuleName");
/* 135 */       String lowestAvg = (String)request.getAttribute("LowestAvg");
/* 136 */       String highestAvg = (String)request.getAttribute("HighestAvg");
/* 137 */       String attUnit = "";
/* 138 */       if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/*     */       {
/* 140 */         attUnit = FormatUtil.getString("in") + " " + FormatUtil.getString(unit);
/*     */       }
/* 142 */       String attrib_text = FormatUtil.getString(nameofmonitor) + " " + attUnit;
/* 143 */       int tab = ((Integer)request.getAttribute("tabsel")).intValue();
/*     */       
/* 145 */       int p = 24;
/* 146 */       int p_start = 1;
/* 147 */       if (tab == 3)
/*     */       {
/* 149 */         p = 7;
/* 150 */         p_start = 0;
/*     */       }
/* 152 */       graph.setParam(resID, attID, period, "000000000", "00000", "oni", tab, hourhash);
/* 153 */       String glanceUrl = "/showReports.do?actionMethod=generateIndividualGlanceReport&resourceid=" + resID + "&period=0&Report=true&resourceType=Monitors&resid=" + resID;
/*     */       
/*     */ 
/*     */ 
/* 157 */       out.write(10);
/*     */       
/* 159 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 160 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 161 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/* 163 */       _jspx_th_c_005fif_005f0.setTest("${STATUS!='SUCCESS'}");
/* 164 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 165 */       if (_jspx_eval_c_005fif_005f0 != 0)
/*     */       {
/* 167 */         out.write("\n\n<br>\n<br>\n<table align=\"center\" cellspacing=\"5\" width=\"98%\" >\n  <tr> \n    <td valign=\"top\" width=\"80%\"> <table cellspacing=\"0\" cellpadding=\"3\" class=\"lrtbdarkborder\" align=\"left\" width=\"100%\">\n        <tr> \n          <td class=\"lightbg\"> <span class=\"bodytextbold\">");
/* 168 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */           return;
/* 170 */         out.write("</span></td>\n        </tr>\n");
/*     */         
/* 172 */         if (!OEMUtil.isOEM())
/*     */         {
/*     */ 
/* 175 */           out.write("\n        <tr> \n          <td align=\"right\" class=\"lightbg\"> <a href=\"http://");
/* 176 */           out.print(OEMUtil.getOEMString("company.troubleshoot.link"));
/* 177 */           out.write("#m4\" class=\"staticlinks\"> \n            ");
/* 178 */           out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.morehelp"));
/* 179 */           out.write(" \n            >></a></td>\n        </tr>\n");
/*     */         }
/* 181 */         out.write("\n      </table></td>\n  </tr>\n</table>\n");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 194 */       else if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 195 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 198 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 199 */         out.write("\n<table class=\"lrtbdarkborder\" width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n<tr>\n<td class=\"tableheadingbborder\" colspan='2'>\n\n\t ");
/* 200 */         if (request.getParameter("period").equals("4"))
/*     */         {
/* 202 */           out.write("\n\t\n\t");
/*     */           
/* 204 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 205 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 206 */           _jspx_th_c_005fchoose_005f0.setParent(null);
/* 207 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 208 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */             for (;;) {
/* 210 */               out.write(" \n  \t\t");
/*     */               
/* 212 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 213 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 214 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */               
/* 216 */               _jspx_th_c_005fwhen_005f0.setTest("${tabsel == 2}");
/* 217 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 218 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */                 for (;;) {
/* 220 */                   out.write(" \n   \t\t");
/* 221 */                   out.print(FormatUtil.getString("am.webclient.historydatareport.segmentbyhr.customtimeheading.text", new String[] { FormatUtil.getString(nameofmonitor), ((Date)request.getAttribute("starttime")).toString(), ((Date)request.getAttribute("endtime")).toString() }));
/* 222 */                   out.write("  \n  \t\t");
/* 223 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 224 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 228 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 229 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */               }
/*     */               
/* 232 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 233 */               out.write(" \n  \t\t");
/*     */               
/* 235 */               WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 236 */               _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 237 */               _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f0);
/*     */               
/* 239 */               _jspx_th_c_005fwhen_005f1.setTest("${tabsel == 3 && (businessPeriod == 'oni' || businessPeriod == null)}");
/* 240 */               int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 241 */               if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*     */                 for (;;) {
/* 243 */                   out.write(" \n    \t\t");
/* 244 */                   out.print(FormatUtil.getString("am.webclient.historydatareport.segmentbywk.customtimeheading.text", new String[] { FormatUtil.getString(nameofmonitor), ((Date)request.getAttribute("starttime")).toString(), ((Date)request.getAttribute("endtime")).toString() }));
/* 245 */                   out.write("  \n  \t\t");
/* 246 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 247 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 251 */               if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 252 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*     */               }
/*     */               
/* 255 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 256 */               out.write(" \n  \t\t");
/*     */               
/* 258 */               WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 259 */               _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 260 */               _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*     */               
/* 262 */               _jspx_th_c_005fwhen_005f2.setTest("${tabsel == 3 && (businessPeriod != 'oni' && businessPeriod != null)}");
/* 263 */               int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 264 */               if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*     */                 for (;;) {
/* 266 */                   out.write(" \n    \t\t");
/* 267 */                   out.print(FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { FormatUtil.getString("am.webclient.historydatareport.segmentbywk.customtimeheading.text", new String[] { FormatUtil.getString(nameofmonitor), ((Date)request.getAttribute("starttime")).toString(), ((Date)request.getAttribute("endtime")).toString() }), bHr }));
/* 268 */                   out.write("  \n  \t\t");
/* 269 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 270 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 274 */               if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 275 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*     */               }
/*     */               
/* 278 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 279 */               out.write(32);
/* 280 */               out.write(10);
/* 281 */               out.write(9);
/* 282 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 283 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 287 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 288 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*     */           }
/*     */           
/* 291 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 292 */           out.write("  \n        ");
/*     */         }
/*     */         else {
/* 295 */           out.write(10);
/* 296 */           out.write(9);
/*     */           
/* 298 */           ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 299 */           _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 300 */           _jspx_th_c_005fchoose_005f1.setParent(null);
/* 301 */           int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 302 */           if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*     */             for (;;) {
/* 304 */               out.write(" \n  \t\t");
/*     */               
/* 306 */               WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 307 */               _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 308 */               _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f1);
/*     */               
/* 310 */               _jspx_th_c_005fwhen_005f3.setTest("${tabsel == 2}");
/* 311 */               int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 312 */               if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*     */                 for (;;) {
/* 314 */                   out.write(" \n   \t\t");
/* 315 */                   out.print(FormatUtil.getString("am.webclient.historydatareport.segmentbyhr.periodheading.text", new String[] { FormatUtil.getString(nameofmonitor), valueforperiod }));
/* 316 */                   out.write("  \n  \t\t");
/* 317 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 318 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 322 */               if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 323 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*     */               }
/*     */               
/* 326 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 327 */               out.write(" \n  \t\t");
/*     */               
/* 329 */               WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 330 */               _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 331 */               _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f1);
/*     */               
/* 333 */               _jspx_th_c_005fwhen_005f4.setTest("${tabsel == 3 && (businessPeriod == 'oni' || businessPeriod == null)}");
/* 334 */               int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 335 */               if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*     */                 for (;;) {
/* 337 */                   out.write(" \n    \t\t");
/* 338 */                   out.print(FormatUtil.getString("am.webclient.historydatareport.segmentbywk.periodheading.text", new String[] { FormatUtil.getString(nameofmonitor), valueforperiod }));
/* 339 */                   out.write("  \n  \t\t");
/* 340 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 341 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 345 */               if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 346 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*     */               }
/*     */               
/* 349 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 350 */               out.write(" \n  \t\t");
/*     */               
/* 352 */               WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 353 */               _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 354 */               _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f1);
/*     */               
/* 356 */               _jspx_th_c_005fwhen_005f5.setTest("${tabsel == 3 && (businessPeriod != 'oni' && businessPeriod != null)}");
/* 357 */               int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 358 */               if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*     */                 for (;;) {
/* 360 */                   out.write(" \n    \t\t");
/* 361 */                   out.print(FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { FormatUtil.getString("am.webclient.historydatareport.segmentbywk.periodheading.text", new String[] { FormatUtil.getString(nameofmonitor), valueforperiod }), bHr }));
/* 362 */                   out.write("  \n  \t\t");
/* 363 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 364 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 368 */               if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 369 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*     */               }
/*     */               
/* 372 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 373 */               out.write(32);
/* 374 */               out.write(10);
/* 375 */               out.write(9);
/* 376 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 377 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 381 */           if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 382 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*     */           }
/*     */           
/* 385 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 386 */           out.write("  \n     ");
/*     */         }
/*     */         
/* 389 */         out.write("\n</td>\n</tr>\n <tr> \n                <td width=\"22%\" class=\"whitegrayborderbr\">");
/* 390 */         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 391 */         out.write("</td>\n                <td width=\"78%\" class=\"whitegrayborder\" align='left'>");
/* 392 */         out.print(resname);
/* 393 */         out.write("\n\t\t&nbsp;\n\t\t<a class=\"staticlinks\" href=\"javascript:fnOpenWindow('");
/* 394 */         out.print(glanceUrl);
/* 395 */         out.write("')\"><img  align=\"absmiddle\"  src=\"images/AtaGlance-icon.gif\" border=\"0\" alt=\"");
/* 396 */         out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 397 */         out.write("\" hspace=\"4\" title=\"");
/* 398 */         out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 399 */         out.write("\"></a>\n\t\t</td>\n              </tr>\n              <tr> \n                <td class=\"yellowgrayborderbr\">");
/* 400 */         out.print(FormatUtil.getString("am.webclient.historydata.attribute.text"));
/* 401 */         out.write(" \n                </td>\n                <td class=\"yellowgrayborder\" align='left'>");
/* 402 */         out.print(FormatUtil.getString(nameofmonitor));
/* 403 */         out.write(" \n                  ");
/* 404 */         if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/* 405 */           out.println(FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/* 406 */         out.write("\n                </td>\n              </tr>\n              <tr> \n                <td class=\"whitegrayborderbr\">");
/* 407 */         out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/* 408 */         out.write(" \n                </td>\n                <td class=\"whitegrayborder\" align='left'> ");
/* 409 */         out.print(startime);
/* 410 */         out.write(" </td>\n              </tr>\n              <tr> \n                <td  class=\"yellowgrayborderbr\">");
/* 411 */         out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/* 412 */         out.write(" \n                </td>\n                <td class=\"yellowgrayborder\" align='left'>");
/* 413 */         out.print(endtime);
/* 414 */         out.write("</td>\n              </tr>\n<tr>\n<tr><td><br>\n</td></tr>\n<td align='center' colspan=2>\n");
/*     */         
/* 416 */         ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 417 */         _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 418 */         _jspx_th_c_005fchoose_005f2.setParent(null);
/* 419 */         int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 420 */         if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*     */           for (;;) {
/* 422 */             out.write(" \n  ");
/*     */             
/* 424 */             WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 425 */             _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 426 */             _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f2);
/*     */             
/* 428 */             _jspx_th_c_005fwhen_005f6.setTest("${tabsel == 2}");
/* 429 */             int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 430 */             if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*     */               for (;;) {
/* 432 */                 out.write(32);
/* 433 */                 out.write(10);
/* 434 */                 out.write(9);
/*     */                 
/* 436 */                 BarChart _jspx_th_awolf_005fbarchart_005f0 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 437 */                 _jspx_th_awolf_005fbarchart_005f0.setPageContext(_jspx_page_context);
/* 438 */                 _jspx_th_awolf_005fbarchart_005f0.setParent(_jspx_th_c_005fwhen_005f6);
/*     */                 
/* 440 */                 _jspx_th_awolf_005fbarchart_005f0.setDataSetProducer("graph");
/*     */                 
/* 442 */                 _jspx_th_awolf_005fbarchart_005f0.setWidth("850");
/*     */                 
/* 444 */                 _jspx_th_awolf_005fbarchart_005f0.setHeight("300");
/*     */                 
/* 446 */                 _jspx_th_awolf_005fbarchart_005f0.setLegend("false");
/*     */                 
/* 448 */                 _jspx_th_awolf_005fbarchart_005f0.setUrl(false);
/*     */                 
/* 450 */                 _jspx_th_awolf_005fbarchart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.hourofday.text"));
/*     */                 
/* 452 */                 _jspx_th_awolf_005fbarchart_005f0.setYaxisLabel(attrib_text);
/*     */                 
/* 454 */                 _jspx_th_awolf_005fbarchart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*     */                 
/* 456 */                 _jspx_th_awolf_005fbarchart_005f0.setMaxBarWidth(0.02D);
/* 457 */                 int _jspx_eval_awolf_005fbarchart_005f0 = _jspx_th_awolf_005fbarchart_005f0.doStartTag();
/* 458 */                 if (_jspx_eval_awolf_005fbarchart_005f0 != 0) {
/* 459 */                   if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 460 */                     out = _jspx_page_context.pushBody();
/* 461 */                     _jspx_th_awolf_005fbarchart_005f0.setBodyContent((BodyContent)out);
/* 462 */                     _jspx_th_awolf_005fbarchart_005f0.doInitBody();
/*     */                   }
/*     */                   for (;;) {
/* 465 */                     out.write(10);
/* 466 */                     out.write(9);
/* 467 */                     int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f0.doAfterBody();
/* 468 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/* 471 */                   if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/* 472 */                     out = _jspx_page_context.popBody();
/*     */                   }
/*     */                 }
/* 475 */                 if (_jspx_th_awolf_005fbarchart_005f0.doEndTag() == 5) {
/* 476 */                   this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0); return;
/*     */                 }
/*     */                 
/* 479 */                 this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005fmaxBarWidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f0);
/* 480 */                 out.write("\n   ");
/* 481 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 482 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 486 */             if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 487 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*     */             }
/*     */             
/* 490 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 491 */             out.write(" \n   ");
/*     */             
/* 493 */             WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 494 */             _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 495 */             _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f2);
/*     */             
/* 497 */             _jspx_th_c_005fwhen_005f7.setTest("${tabsel == 3}");
/* 498 */             int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 499 */             if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*     */               for (;;) {
/* 501 */                 out.write(32);
/* 502 */                 out.write(10);
/* 503 */                 out.write(9);
/*     */                 
/* 505 */                 BarChart _jspx_th_awolf_005fbarchart_005f1 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.get(BarChart.class);
/* 506 */                 _jspx_th_awolf_005fbarchart_005f1.setPageContext(_jspx_page_context);
/* 507 */                 _jspx_th_awolf_005fbarchart_005f1.setParent(_jspx_th_c_005fwhen_005f7);
/*     */                 
/* 509 */                 _jspx_th_awolf_005fbarchart_005f1.setDataSetProducer("graph");
/*     */                 
/* 511 */                 _jspx_th_awolf_005fbarchart_005f1.setWidth("700");
/*     */                 
/* 513 */                 _jspx_th_awolf_005fbarchart_005f1.setHeight("300");
/*     */                 
/* 515 */                 _jspx_th_awolf_005fbarchart_005f1.setLegend("false");
/*     */                 
/* 517 */                 _jspx_th_awolf_005fbarchart_005f1.setUrl(false);
/*     */                 
/* 519 */                 _jspx_th_awolf_005fbarchart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.dayofweek.text"));
/*     */                 
/* 521 */                 _jspx_th_awolf_005fbarchart_005f1.setYaxisLabel(attrib_text);
/*     */                 
/* 523 */                 _jspx_th_awolf_005fbarchart_005f1.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 524 */                 int _jspx_eval_awolf_005fbarchart_005f1 = _jspx_th_awolf_005fbarchart_005f1.doStartTag();
/* 525 */                 if (_jspx_eval_awolf_005fbarchart_005f1 != 0) {
/* 526 */                   if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 527 */                     out = _jspx_page_context.pushBody();
/* 528 */                     _jspx_th_awolf_005fbarchart_005f1.setBodyContent((BodyContent)out);
/* 529 */                     _jspx_th_awolf_005fbarchart_005f1.doInitBody();
/*     */                   }
/*     */                   for (;;) {
/* 532 */                     out.write(10);
/* 533 */                     out.write(9);
/* 534 */                     int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f1.doAfterBody();
/* 535 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/* 538 */                   if (_jspx_eval_awolf_005fbarchart_005f1 != 1) {
/* 539 */                     out = _jspx_page_context.popBody();
/*     */                   }
/*     */                 }
/* 542 */                 if (_jspx_th_awolf_005fbarchart_005f1.doEndTag() == 5) {
/* 543 */                   this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f1); return;
/*     */                 }
/*     */                 
/* 546 */                 this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fbarchart_005f1);
/* 547 */                 out.write("\n    ");
/* 548 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 549 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 553 */             if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 554 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*     */             }
/*     */             
/* 557 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 558 */             out.write(32);
/* 559 */             out.write(10);
/* 560 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 561 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 565 */         if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 566 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*     */         }
/*     */         else {
/* 569 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 570 */           out.write("  \n</td></tr>\n<tr><td class=\"bodytext\"  colspan=2><br>\n\t&nbsp;");
/* 571 */           out.print(FormatUtil.getString("am.webclient.historydatareport.segmentReport.summary.test", new String[] { FormatUtil.getString(nameofmonitor), highestAvg, lowestAvg }));
/* 572 */           out.write("\n</td></tr>\n</table>\n<br>\n\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\" align=\"center\">\n <tr> \n    <td class=\"tableheadingbborder\"  width='100%' colspan='4'>");
/* 573 */           out.print(FormatUtil.getString(nameofmonitor));
/* 574 */           out.write(" </td>\n  </tr>\n  <tr> \n   \t<td  class=\"columnheadingrightborder\" width=\"25%\" align=\"center\">\n\t");
/*     */           
/* 576 */           ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 577 */           _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 578 */           _jspx_th_c_005fchoose_005f3.setParent(null);
/* 579 */           int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 580 */           if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*     */             for (;;) {
/* 582 */               out.write(" \n\t\t");
/*     */               
/* 584 */               WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 585 */               _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 586 */               _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f3);
/*     */               
/* 588 */               _jspx_th_c_005fwhen_005f8.setTest("${tabsel == 2}");
/* 589 */               int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 590 */               if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*     */                 for (;;) {
/* 592 */                   out.write(" \n\t\t\t");
/* 593 */                   out.print(FormatUtil.getString("am.webclient.common.axisname.hourofday.text"));
/* 594 */                   out.write(10);
/* 595 */                   out.write(9);
/* 596 */                   out.write(9);
/* 597 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 598 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 602 */               if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 603 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*     */               }
/*     */               
/* 606 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 607 */               out.write(" \n   \t\t");
/*     */               
/* 609 */               WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 610 */               _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 611 */               _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f3);
/*     */               
/* 613 */               _jspx_th_c_005fwhen_005f9.setTest("${tabsel == 3}");
/* 614 */               int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 615 */               if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*     */                 for (;;) {
/* 617 */                   out.write(" \n\t\t\t");
/* 618 */                   out.print(FormatUtil.getString("am.webclient.common.axisname.dayofweek.text"));
/* 619 */                   out.write(10);
/* 620 */                   out.write(9);
/* 621 */                   out.write(9);
/* 622 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 623 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 627 */               if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 628 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*     */               }
/*     */               
/* 631 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 632 */               out.write(32);
/* 633 */               out.write(10);
/* 634 */               out.write(9);
/* 635 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 636 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 640 */           if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 641 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*     */           }
/*     */           else {
/* 644 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 645 */             out.write("  \n\t</td>\n\t<td  class=\"columnheadingrightborder\" width=\"25%\" align=\"center\">");
/* 646 */             out.print(FormatUtil.getString("am.webclient.historydata.minvalue.text"));
/* 647 */             out.write(32);
/* 648 */             out.print(attUnit);
/* 649 */             out.write("</td>\n\t<td  class=\"columnheadingrightborder\" width=\"25%\" align=\"center\">");
/* 650 */             out.print(FormatUtil.getString("am.webclient.historydata.maxvalue.text"));
/* 651 */             out.write(32);
/* 652 */             out.print(attUnit);
/* 653 */             out.write("</td>\n\t<td  class=\"columnheadingrightborder\" width=\"25%\" align=\"center\">");
/* 654 */             out.print(FormatUtil.getString("am.webclient.historydata.hourlyavgvalue.text"));
/* 655 */             out.write(32);
/* 656 */             out.print(attUnit);
/* 657 */             out.write("</td>\n  </tr>\n");
/*     */             
/*     */ 
/* 660 */             for (int i = p_start; i < p; i++)
/*     */             {
/* 662 */               String hrString = Integer.toString(i);
/* 663 */               Hashtable hrdata = (Hashtable)hourhash.get(hrString);
/* 664 */               if (p == 24)
/*     */               {
/* 666 */                 if (hrString.length() == 1) {
/* 667 */                   hrString = "0" + Integer.toString(i - 1) + ":00 - " + hrString + ":00";
/*     */                 } else {
/* 669 */                   hrString = Integer.toString(i - 1) + ":00 - " + hrString + ":00";
/*     */                 }
/*     */                 
/*     */               }
/* 673 */               else if (i == 0) {
/* 674 */                 hrString = "Sunday";
/* 675 */               } else if (i == 1) {
/* 676 */                 hrString = "Monday";
/* 677 */               } else if (i == 2) {
/* 678 */                 hrString = "Tuesday";
/* 679 */               } else if (i == 3) {
/* 680 */                 hrString = "Wednesday";
/* 681 */               } else if (i == 4) {
/* 682 */                 hrString = "Thursday";
/* 683 */               } else if (i == 5) {
/* 684 */                 hrString = "Friday";
/*     */               } else {
/* 686 */                 hrString = "Saturday";
/*     */               }
/* 688 */               String bgcolor = "";
/* 689 */               if (i % 2 == 0)
/*     */               {
/* 691 */                 bgcolor = "class=\"whitegrayborder\"";
/*     */               }
/*     */               else
/*     */               {
/* 695 */                 bgcolor = "class=\"yellowgrayborder\"";
/*     */               }
/*     */               
/*     */ 
/*     */ 
/* 700 */               out.write("\n\t<tr>\n\t\t<td ");
/* 701 */               out.print(bgcolor);
/* 702 */               out.write(" align=\"center\">");
/* 703 */               out.println(FormatUtil.getString(hrString));
/* 704 */               out.write("</td>\n\t<td ");
/* 705 */               out.print(bgcolor);
/* 706 */               out.write(" align=\"center\">");
/* 707 */               out.print(FormatUtil.formatNumber(hrdata.get("MIN")));
/* 708 */               out.write("</td>\n\t<td ");
/* 709 */               out.print(bgcolor);
/* 710 */               out.write(" align=\"center\">");
/* 711 */               out.print(FormatUtil.formatNumber(hrdata.get("MAX")));
/* 712 */               out.write("</td>\n\t<td ");
/* 713 */               out.print(bgcolor);
/* 714 */               out.write(" align=\"center\">");
/* 715 */               out.print(FormatUtil.formatNumber(hrdata.get("AVG")));
/* 716 */               out.write("</td>\n\t</tr>\n");
/*     */             }
/*     */             
/* 719 */             if (p == 24)
/*     */             {
/* 721 */               Hashtable hrdata = (Hashtable)hourhash.get("0");
/*     */               
/* 723 */               out.write("\n\t<tr>\n\t<td class=\"whitegrayborder\" align=\"center\">");
/* 724 */               out.println("23:00 - 00:00");
/* 725 */               out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"center\">");
/* 726 */               out.print(FormatUtil.formatNumber(hrdata.get("MIN")));
/* 727 */               out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"center\">");
/* 728 */               out.print(FormatUtil.formatNumber(hrdata.get("MAX")));
/* 729 */               out.write("</td>\n\t<td class=\"whitegrayborder\" align=\"center\">");
/* 730 */               out.print(FormatUtil.formatNumber(hrdata.get("AVG")));
/* 731 */               out.write("</td>\n\t</tr>\n");
/*     */             }
/*     */             
/*     */ 
/* 735 */             out.write("\n  <tr>\n\t\n\t\n  </tr>\n</table>\n<br>\n<br>\n<table width=\"98%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\">\n  <tr>\n     <td class=\"columnheading\"><b>");
/* 736 */             out.print(FormatUtil.getString("am.webclient.historydata.note.text"));
/* 737 */             out.write("</b><br></td>\n  </tr> \n");
/*     */             
/* 739 */             ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 740 */             _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 741 */             _jspx_th_c_005fchoose_005f4.setParent(null);
/* 742 */             int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 743 */             if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*     */               for (;;) {
/* 745 */                 out.write(" \n  ");
/*     */                 
/* 747 */                 WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 748 */                 _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 749 */                 _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f4);
/*     */                 
/* 751 */                 _jspx_th_c_005fwhen_005f10.setTest("${tabsel == 2}");
/* 752 */                 int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 753 */                 if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*     */                   for (;;) {
/* 755 */                     out.write(" \n  <tr>\n     <td class=\"bodytext\"> ");
/* 756 */                     out.print(FormatUtil.getString("am.webclient.historydatareport.segmentbyhr.help.summary"));
/* 757 */                     out.write("</td>\n  </tr>  \n  <tr>\n     <td class=\"bodytext\"> <br><b>");
/* 758 */                     out.print(FormatUtil.getString("am.webclient.historydata.minvalue.text"));
/* 759 */                     out.write("</b>: ");
/* 760 */                     out.print(FormatUtil.getString("am.webclient.historydatareport.segmentbyhr.help.minvalue.text"));
/* 761 */                     out.write("<br><br></td>\n  </tr>\n  <tr>\n     <td class=\"bodytext\">  <br><b>");
/* 762 */                     out.print(FormatUtil.getString("am.webclient.historydata.maxvalue.text"));
/* 763 */                     out.write("</b>:\n\t");
/* 764 */                     out.print(FormatUtil.getString("am.webclient.historydatareport.segmentbyhr.help.maxvalue.text"));
/* 765 */                     out.write("<br><br></td>\n  </tr>\n  <tr>\n   <tr>\n     <td class=\"bodytext\">  <br><b>");
/* 766 */                     out.print(FormatUtil.getString("am.webclient.historydata.hourlyavgvalue.text"));
/* 767 */                     out.write("</b>:\n\t");
/* 768 */                     out.print(FormatUtil.getString("am.webclient.historydatareport.segmentbyhr.help.avgvalue.text"));
/* 769 */                     out.write("</td>\n  </tr> \n ");
/* 770 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 771 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 775 */                 if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 776 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*     */                 }
/*     */                 
/* 779 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 780 */                 out.write(32);
/* 781 */                 out.write(10);
/* 782 */                 out.write(32);
/*     */                 
/* 784 */                 WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 785 */                 _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 786 */                 _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f4);
/*     */                 
/* 788 */                 _jspx_th_c_005fwhen_005f11.setTest("${tabsel == 3}");
/* 789 */                 int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 790 */                 if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*     */                   for (;;) {
/* 792 */                     out.write(" \n\t<tr>\n     <td class=\"bodytext\"> ");
/* 793 */                     out.print(FormatUtil.getString("am.webclient.historydatareport.segmentbywk.help.summary"));
/* 794 */                     out.write("</td>\n  </tr>  \n  <tr>\n     <td class=\"bodytext\"> <br><b>");
/* 795 */                     out.print(FormatUtil.getString("am.webclient.historydata.minvalue.text"));
/* 796 */                     out.write("</b>: ");
/* 797 */                     out.print(FormatUtil.getString("am.webclient.historydatareport.segmentbywk.help.minvalue.text"));
/* 798 */                     out.write("<br><br></td>\n  </tr>\n  <tr>\n     <td class=\"bodytext\">  <br><b>");
/* 799 */                     out.print(FormatUtil.getString("am.webclient.historydata.maxvalue.text"));
/* 800 */                     out.write("</b>:\n     ");
/* 801 */                     out.print(FormatUtil.getString("am.webclient.historydatareport.segmentbywk.help.maxvalue.text"));
/* 802 */                     out.write("<br><br></td>\n  </tr>\n  <tr>\n   <tr>\n     <td class=\"bodytext\">  <br><b>");
/* 803 */                     out.print(FormatUtil.getString("am.webclient.historydata.hourlyavgvalue.text"));
/* 804 */                     out.write("</b>:\n      ");
/* 805 */                     out.print(FormatUtil.getString("am.webclient.historydatareport.segmentbywk.help.avgvalue.text"));
/* 806 */                     out.write("</td>\n  </tr> \n   ");
/* 807 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 808 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 812 */                 if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 813 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*     */                 }
/*     */                 
/* 816 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 817 */                 out.write(32);
/* 818 */                 out.write(10);
/* 819 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 820 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 824 */             if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 825 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/*     */             }
/*     */             else {
/* 828 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 829 */               out.write("   \n</table>\n<br>\n</body>\n");
/*     */             }
/* 831 */           } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 832 */         out = _jspx_out;
/* 833 */         if ((out != null) && (out.getBufferSize() != 0))
/* 834 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 835 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 838 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 844 */     PageContext pageContext = _jspx_page_context;
/* 845 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 847 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 848 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 849 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 851 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 853 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 854 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 855 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 856 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 857 */       return true;
/*     */     }
/* 859 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 860 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 865 */     PageContext pageContext = _jspx_page_context;
/* 866 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 868 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 869 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 870 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 872 */     _jspx_th_c_005fout_005f1.setValue("${STATUS}");
/* 873 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 874 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 875 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 876 */       return true;
/*     */     }
/* 878 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 879 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\SegmentedHistory_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */