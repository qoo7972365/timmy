/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.struts.actions.ComparingSla;
/*     */ import com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.OEMUtil;
/*     */ import com.adventnet.awolf.tags.AreaChart;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
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
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ 
/*     */ public final class StandardDeviation_jsp extends HttpJspBase implements JspSourceDependent
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
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer;
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
/*  59 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  60 */     this._005fjspx_005ftagPool_005fawolf_005fareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
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
/*  71 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  72 */     this._005fjspx_005ftagPool_005fawolf_005fareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.release();
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
/*  99 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<!--$Id$-->\n\n\n\n\n");
/* 100 */       HistoryDataGraphUtil graph = null;
/* 101 */       graph = (HistoryDataGraphUtil)_jspx_page_context.getAttribute("graph", 1);
/* 102 */       if (graph == null) {
/* 103 */         graph = new HistoryDataGraphUtil();
/* 104 */         _jspx_page_context.setAttribute("graph", graph, 1);
/*     */       }
/* 106 */       out.write("\n\n\n\n<html>\n<head>\n\n<link href=\"/images/");
/* 107 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 109 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n\n</head>\n<body>\n");
/*     */       
/* 111 */       ComparingSla cs = new ComparingSla();
/* 112 */       String resname = (String)request.getAttribute("resourcename");
/* 113 */       String nameofmonitor = (String)request.getAttribute("monitortype");
/* 114 */       String unit = (String)pageContext.findAttribute("unit");
/* 115 */       Hashtable hourhash = (Hashtable)request.getAttribute("DATA");
/*     */       
/* 117 */       String resID = (String)request.getAttribute("resourceid");
/* 118 */       String attID = (String)request.getAttribute("attributeid");
/* 119 */       String period = (String)request.getAttribute("period");
/* 120 */       String startime = FormatUtil.formatDT((Long)request.getAttribute("STIME") + "");
/* 121 */       String endtime = FormatUtil.formatDT((Long)request.getAttribute("ETIME") + "");
/* 122 */       String valueforperiod = cs.getValueForPeriod(period);
/* 123 */       String attUnit = "";
/* 124 */       if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/*     */       {
/* 126 */         attUnit = FormatUtil.getString("in") + " " + FormatUtil.getString(unit);
/*     */       }
/*     */       
/* 129 */       String minval = (String)request.getAttribute("MINVAL");
/* 130 */       String maxval = (String)request.getAttribute("MAXVAL");
/* 131 */       String avgval = (String)request.getAttribute("AVGVAL");
/* 132 */       float median = ((Float)request.getAttribute("MEDIAN")).floatValue();
/* 133 */       float stddevn = ((Float)request.getAttribute("StdDevn")).floatValue();
/* 134 */       String nfpercentile = (String)request.getAttribute("NFPERCENTILE");
/*     */       
/*     */ 
/* 137 */       float[] avgdata = (float[])request.getAttribute("AVGDATAS");
/*     */       
/* 139 */       String lowtenlist = (String)request.getAttribute("LOWTEN");
/* 140 */       String hightenlist = (String)request.getAttribute("HIGHTEN");
/* 141 */       String attrib_text = FormatUtil.getString(nameofmonitor) + " " + attUnit;
/* 142 */       ArrayList countperrange = (ArrayList)request.getAttribute("countperrange");
/* 143 */       ArrayList rangelist = (ArrayList)request.getAttribute("range");
/* 144 */       String bHr = (String)request.getAttribute("bRuleName");
/*     */       
/* 146 */       String seriesname = "(" + nameofmonitor + ", Count% )";
/* 147 */       graph.setParam(rangelist, countperrange, 4, seriesname);
/*     */       
/*     */ 
/* 150 */       String glanceUrl = "/showReports.do?actionMethod=generateIndividualGlanceReport&resourceid=" + resID + "&period=0&Report=true&resourceType=Monitors&resid=" + resID;
/*     */       
/* 152 */       out.write(10);
/*     */       
/* 154 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 155 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 156 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/* 158 */       _jspx_th_c_005fif_005f0.setTest("${STATUS!='SUCCESS'}");
/* 159 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 160 */       if (_jspx_eval_c_005fif_005f0 != 0)
/*     */       {
/* 162 */         out.write("\n\n<br>\n<br>\n<table align=\"center\" cellspacing=\"5\" width=\"98%\" >\n  <tr> \n    <td valign=\"top\" width=\"80%\"> <table cellspacing=\"0\" cellpadding=\"3\" class=\"lrtbdarkborder\" align=\"left\" width=\"100%\">\n        <tr> \n          <td class=\"lightbg\"> <span class=\"bodytextbold\">");
/* 163 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*     */           return;
/* 165 */         out.write("</span></td>\n        </tr>\n");
/*     */         
/* 167 */         if (!OEMUtil.isOEM())
/*     */         {
/*     */ 
/* 170 */           out.write("\n        <tr> \n          <td align=\"right\" class=\"lightbg\"> <a href=\"http://");
/* 171 */           out.print(OEMUtil.getOEMString("company.troubleshoot.link"));
/* 172 */           out.write("#m4\" class=\"staticlinks\"> \n            ");
/* 173 */           out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.morehelp"));
/* 174 */           out.write(" \n            >></a></td>\n        </tr>\n");
/*     */         }
/* 176 */         out.write("\n      </table></td>\n  </tr>\n</table>\n");
/*     */ 
/*     */ 
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
/* 189 */       else if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 190 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 193 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 194 */         out.write("\n<table class=\"lrtbdarkborder\" width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n<tr>\n<td class=\"tableheadingbborder\" colspan='2'>\n\n\n        ");
/* 195 */         if (request.getParameter("period").equals("4"))
/*     */         {
/* 197 */           out.write(10);
/* 198 */           out.write(9);
/*     */           
/* 200 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 201 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 202 */           _jspx_th_c_005fchoose_005f0.setParent(null);
/* 203 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 204 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */             for (;;) {
/* 206 */               out.write(" \n   \t\t");
/*     */               
/* 208 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 209 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 210 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */               
/* 212 */               _jspx_th_c_005fwhen_005f0.setTest("${businessPeriod == 'oni' || businessPeriod == null}");
/* 213 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 214 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */                 for (;;) {
/* 216 */                   out.write(" \n\t\t\t");
/* 217 */                   out.print(FormatUtil.getString("am.webclient.historydatareport.stdDevn.customtimeheading.text", new String[] { FormatUtil.getString(nameofmonitor), ((Date)request.getAttribute("starttime")).toString(), ((Date)request.getAttribute("endtime")).toString() }));
/* 218 */                   out.write("  \n   \t\t");
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
/* 229 */               out.write(" \n   \t\t");
/*     */               
/* 231 */               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 232 */               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 233 */               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 234 */               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 235 */               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */                 for (;;) {
/* 237 */                   out.write(" \n   \t \t\t");
/* 238 */                   out.print(FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { FormatUtil.getString("am.webclient.historydatareport.stdDevn.customtimeheading.text", new String[] { FormatUtil.getString(nameofmonitor), ((Date)request.getAttribute("starttime")).toString(), ((Date)request.getAttribute("endtime")).toString() }), bHr }));
/* 239 */                   out.write("  \n   \t\t");
/* 240 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 241 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 245 */               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 246 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */               }
/*     */               
/* 249 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 250 */               out.write(32);
/* 251 */               out.write(10);
/* 252 */               out.write(9);
/* 253 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 254 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 258 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 259 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*     */           }
/*     */           
/* 262 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 263 */           out.write("\n          </td>\n        </tr>\n        ");
/*     */         }
/*     */         else {
/* 266 */           out.write(10);
/* 267 */           out.write(9);
/*     */           
/* 269 */           ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 270 */           _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 271 */           _jspx_th_c_005fchoose_005f1.setParent(null);
/* 272 */           int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 273 */           if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*     */             for (;;) {
/* 275 */               out.write(" \n   ");
/*     */               
/* 277 */               WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 278 */               _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 279 */               _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*     */               
/* 281 */               _jspx_th_c_005fwhen_005f1.setTest("${businessPeriod == 'oni' || businessPeriod == null}");
/* 282 */               int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 283 */               if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*     */                 for (;;) {
/* 285 */                   out.write(32);
/* 286 */                   out.write(10);
/* 287 */                   out.write(9);
/* 288 */                   out.print(FormatUtil.getString("am.webclient.historydatareport.stdDevn.periodheading.text", new String[] { FormatUtil.getString(nameofmonitor), valueforperiod }));
/* 289 */                   out.write("  \n   ");
/* 290 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 291 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 295 */               if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 296 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*     */               }
/*     */               
/* 299 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 300 */               out.write(" \n   ");
/*     */               
/* 302 */               OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 303 */               _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 304 */               _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 305 */               int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 306 */               if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*     */                 for (;;) {
/* 308 */                   out.write(" \n   \t ");
/* 309 */                   out.print(FormatUtil.getString("am.webclient.reports.newheading.businesshour.report.text", new String[] { FormatUtil.getString("am.webclient.historydatareport.stdDevn.periodheading.text", new String[] { FormatUtil.getString(nameofmonitor), valueforperiod }), bHr }));
/* 310 */                   out.write("  \n   ");
/* 311 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 312 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 316 */               if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 317 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*     */               }
/*     */               
/* 320 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 321 */               out.write(32);
/* 322 */               out.write(10);
/* 323 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 324 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 328 */           if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 329 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*     */           }
/*     */           
/* 332 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 333 */           out.write(10);
/* 334 */           out.write(10);
/*     */         }
/* 336 */         out.write("\n</td>\n</tr>\n <tr> \n\t <td width=\"22%\" class=\"whitegrayborderbr\">");
/* 337 */         out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 338 */         out.write("</td>\n                <td width=\"78%\" class=\"whitegrayborder\" align='left'>");
/* 339 */         out.print(resname);
/* 340 */         out.write("\n\t\t&nbsp;\n\t\t<a class=\"staticlinks\" href=\"javascript:fnOpenWindow('");
/* 341 */         out.print(glanceUrl);
/* 342 */         out.write("')\"><img  align=\"absmiddle\"  src=\"images/AtaGlance-icon.gif\" border=\"0\" alt=\"");
/* 343 */         out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 344 */         out.write("\" hspace=\"4\" title=\"");
/* 345 */         out.print(FormatUtil.getString("am.webclient.reports.ataglance.report"));
/* 346 */         out.write("\"></a>\n\t\t</td>\n              </tr>\n              <tr> \n                <td class=\"yellowgrayborderbr\">");
/* 347 */         out.print(FormatUtil.getString("am.webclient.historydata.attribute.text"));
/* 348 */         out.write(" \n                </td>\n                <td class=\"yellowgrayborder\" align='left'>");
/* 349 */         out.print(FormatUtil.getString(nameofmonitor));
/* 350 */         out.write(" \n                  ");
/* 351 */         if ((unit != null) && (!unit.equals("null")) && (!unit.equals("")))
/* 352 */           out.println(FormatUtil.getString("in") + " " + FormatUtil.getString(unit));
/* 353 */         out.write("\n                </td>\n              </tr>\n              <tr> \n                <td class=\"whitegrayborderbr\">");
/* 354 */         out.print(FormatUtil.getString("am.webclient.historydata.capfrom.text"));
/* 355 */         out.write(" \n                </td>\n                <td class=\"whitegrayborder\" align='left'> ");
/* 356 */         out.print(startime);
/* 357 */         out.write(" </td>\n              </tr>\n              <tr> \n                <td  class=\"yellowgrayborderbr\">");
/* 358 */         out.print(FormatUtil.getString("am.webclient.historydata.capto.text"));
/* 359 */         out.write(" \n                </td>\n                <td class=\"yellowgrayborder\" align='left'>");
/* 360 */         out.print(endtime);
/* 361 */         out.write("</td>\n              </tr>\n\t\n<tr><td colspan='2'><br>\n</td></tr>\n\t<tr><td colspan='2'>\n\t\t&nbsp;\n\t\t");
/*     */         
/* 363 */         AreaChart _jspx_th_awolf_005fareachart_005f0 = (AreaChart)this._005fjspx_005ftagPool_005fawolf_005fareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.get(AreaChart.class);
/* 364 */         _jspx_th_awolf_005fareachart_005f0.setPageContext(_jspx_page_context);
/* 365 */         _jspx_th_awolf_005fareachart_005f0.setParent(null);
/*     */         
/* 367 */         _jspx_th_awolf_005fareachart_005f0.setDataSetProducer("graph");
/*     */         
/* 369 */         _jspx_th_awolf_005fareachart_005f0.setWidth("800");
/*     */         
/* 371 */         _jspx_th_awolf_005fareachart_005f0.setHeight("250");
/*     */         
/* 373 */         _jspx_th_awolf_005fareachart_005f0.setLegend("false");
/*     */         
/* 375 */         _jspx_th_awolf_005fareachart_005f0.setXaxisLabel(attrib_text);
/*     */         
/* 377 */         _jspx_th_awolf_005fareachart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.historydatareport.count"));
/*     */         
/* 379 */         _jspx_th_awolf_005fareachart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 380 */         int _jspx_eval_awolf_005fareachart_005f0 = _jspx_th_awolf_005fareachart_005f0.doStartTag();
/* 381 */         if (_jspx_eval_awolf_005fareachart_005f0 != 0) {
/* 382 */           if (_jspx_eval_awolf_005fareachart_005f0 != 1) {
/* 383 */             out = _jspx_page_context.pushBody();
/* 384 */             _jspx_th_awolf_005fareachart_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 385 */             _jspx_th_awolf_005fareachart_005f0.doInitBody();
/*     */           }
/*     */           for (;;) {
/* 388 */             out.write(10);
/* 389 */             out.write(9);
/* 390 */             out.write(9);
/* 391 */             int evalDoAfterBody = _jspx_th_awolf_005fareachart_005f0.doAfterBody();
/* 392 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/* 395 */           if (_jspx_eval_awolf_005fareachart_005f0 != 1) {
/* 396 */             out = _jspx_page_context.popBody();
/*     */           }
/*     */         }
/* 399 */         if (_jspx_th_awolf_005fareachart_005f0.doEndTag() == 5) {
/* 400 */           this._005fjspx_005ftagPool_005fawolf_005fareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fareachart_005f0);
/*     */         }
/*     */         else {
/* 403 */           this._005fjspx_005ftagPool_005fawolf_005fareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fareachart_005f0);
/* 404 */           out.write("\n\t</td>\t</tr> \n\t<tr>\n\t<td><br>\n\t</td>\n\t</tr>\n</table>  \n<br>\n<TABLE width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1px\" align=\"center\">\n<TR>\n<TD width=\"48%\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\" align ='center'>\n <tr> \n    <td class=\"tableheadingbborder\"  width='100%' colspan='4'>");
/* 405 */           out.print(FormatUtil.getString("am.webclient.historydatareport.stdDevn.tab"));
/* 406 */           out.write(" </td>\n  </tr>\n\t<tr>\n\t<td class='columnheading' width='25%' >");
/* 407 */           out.print(FormatUtil.getString("am.webclient.common.average.text"));
/* 408 */           out.write(" :\n\t</td>\n\t<td class=\"yellowgrayborder\" width='25%'>");
/* 409 */           out.print(FormatUtil.formatNumber(avgval));
/* 410 */           out.write(32);
/* 411 */           out.write(32);
/* 412 */           out.print(FormatUtil.getString(unit));
/* 413 */           out.write(" \n\t</td>\n\t<td class='columnheading' width='25%' >");
/* 414 */           out.print(FormatUtil.getString("am.webclient.historydatareport.stdDevn.median.text"));
/* 415 */           out.write(" :\n\t</td>\n\t<td class=\"yellowgrayborder\" width='25%'>");
/* 416 */           out.print(FormatUtil.formatNumber(Float.valueOf(median)));
/* 417 */           out.write(32);
/* 418 */           out.print(FormatUtil.getString(unit));
/* 419 */           out.write("\n\t<td>\n   </tr>     \n   <tr>\n\t<td class='columnheading' width='25%' >");
/* 420 */           out.print(FormatUtil.getString("am.webclient.common.maximum.text"));
/* 421 */           out.write(" : \n\t</td>\n\t<td class=\"yellowgrayborder\" width='25%'>");
/* 422 */           out.print(FormatUtil.formatNumber(maxval));
/* 423 */           out.write(32);
/* 424 */           out.print(FormatUtil.getString(unit));
/* 425 */           out.write("\n\t</td>\n\t<td class='columnheading' width='25%' >");
/* 426 */           out.print(FormatUtil.getString("am.webclient.common.minimum.text"));
/* 427 */           out.write(" : \n\t</td>\n\t<td class=\"yellowgrayborder\" width='25%'>");
/* 428 */           out.print(FormatUtil.formatNumber(minval));
/* 429 */           out.write(32);
/* 430 */           out.print(FormatUtil.getString(unit));
/* 431 */           out.write(" \n\t<td>\n   </tr>\n\t \n\t<tr>\n\t<td class='columnheading' width='25%' >");
/* 432 */           out.print(FormatUtil.getString("am.webclient.historydatareport.stdDevn.text"));
/* 433 */           out.write(" : \n\t</td>\n\t<td class=\"yellowgrayborder\" width='25%'>");
/* 434 */           out.print(FormatUtil.formatNumber(Float.valueOf(stddevn)));
/* 435 */           out.write(32);
/* 436 */           out.print(FormatUtil.getString(unit));
/* 437 */           out.write("\n\t</td>\n\t<td class='columnheading' width='25%' >");
/* 438 */           out.print(FormatUtil.getString("am.webclient.historydatareport.stdDevn.nfpercentile.text"));
/* 439 */           out.write(" :\n\t</td>\n\t<td class=\"yellowgrayborder\" width='25%'>");
/* 440 */           out.print(FormatUtil.formatNumber(nfpercentile));
/* 441 */           out.write(32);
/* 442 */           out.print(FormatUtil.getString(unit));
/* 443 */           out.write("\n\t</td>\n\t\n\t\n   </tr>        \n </table>\n</TD>\n<td width=\"2%\"></td>\n<TD width=\"48%\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\" align ='center'>\n <tr> \n    <td class=\"tableheadingbborder\"  width='100%' colspan='4'>");
/* 444 */           out.print(FormatUtil.getString("am.webclient.historydatareport.stdDevn.avgVal.table.name"));
/* 445 */           out.write(" </td>\n  </tr>\n   <tr>\n    <td class='columnheading' width='25%' >");
/* 446 */           out.print(FormatUtil.getString("am.webclient.period.today"));
/* 447 */           out.write(" :\n\t</td>\n\t<td class=\"yellowgrayborder\" width='25%'>");
/* 448 */           out.print(FormatUtil.formatNumber(Float.valueOf(avgdata[0])));
/* 449 */           out.write(32);
/* 450 */           out.print(FormatUtil.getString(unit));
/* 451 */           out.write("\n\t</td>\n\t<td class='columnheading' width='25%' >");
/* 452 */           out.print(FormatUtil.getString("am.webclient.period.yesterday"));
/* 453 */           out.write(" :\n\t</td>\n\t<td class=\"yellowgrayborder\" width='25%'>");
/* 454 */           out.print(FormatUtil.formatNumber(Float.valueOf(avgdata[1])));
/* 455 */           out.write(32);
/* 456 */           out.print(FormatUtil.getString(unit));
/* 457 */           out.write("\n\t<td>\n   </tr>\n    <tr>\n    <td class='columnheading' width='25%' >");
/* 458 */           out.print(FormatUtil.getString("am.webclient.period.thisweek"));
/* 459 */           out.write(" :\n\t</td>\n\t<td class=\"yellowgrayborder\" width='25%'>");
/* 460 */           out.print(FormatUtil.formatNumber(Float.valueOf(avgdata[2])));
/* 461 */           out.write(32);
/* 462 */           out.print(FormatUtil.getString(unit));
/* 463 */           out.write("\n\t</td>\n\t<td class='columnheading' width='25%' >");
/* 464 */           out.print(FormatUtil.getString("am.webclient.period.lastweek"));
/* 465 */           out.write(" :\n\t</td>\n\t<td class=\"yellowgrayborder\" width='25%'>");
/* 466 */           out.print(FormatUtil.formatNumber(Float.valueOf(avgdata[3])));
/* 467 */           out.write(32);
/* 468 */           out.print(FormatUtil.getString(unit));
/* 469 */           out.write("\n\t<td>\n   </tr>\n    <tr>\n    <td class='columnheading' width='25%' >");
/* 470 */           out.print(FormatUtil.getString("am.webclient.period.thismonth"));
/* 471 */           out.write(" :\n\t</td>\n\t<td class=\"yellowgrayborder\" width='25%'>");
/* 472 */           out.print(FormatUtil.formatNumber(Float.valueOf(avgdata[4])));
/* 473 */           out.write(32);
/* 474 */           out.print(FormatUtil.getString(unit));
/* 475 */           out.write("\n\t</td>\n\t<td class='columnheading' width='25%' >");
/* 476 */           out.print(FormatUtil.getString("am.webclient.period.lastmonth"));
/* 477 */           out.write(" :\n\t</td>\n\t<td class=\"yellowgrayborder\" width='25%'>");
/* 478 */           out.print(FormatUtil.formatNumber(Float.valueOf(avgdata[5])));
/* 479 */           out.write(32);
/* 480 */           out.print(FormatUtil.getString(unit));
/* 481 */           out.write("\n\t<td>\n   </tr>\n</table>\n</TD>\n</TR>\n<TR><TD colspan='3'><BR></TD></TR>\n<TR>\n<TD Colspan='3' align=\"center\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\" align ='center'>\n <tr> \n    <td class=\"tableheadingbborder\"  width='100%' colspan='2'>");
/* 482 */           out.print(FormatUtil.getString("am.webclient.historydatareport.stdDevn.minmax.table.name"));
/* 483 */           out.write(" </td>\n  </tr>\n   <tr>\n    <td class='columnheading' width='30%' >");
/* 484 */           out.print(FormatUtil.getString("am.webclient.historydatareport.stdDevn.lowTen.text"));
/* 485 */           out.write(32);
/* 486 */           out.write(10);
/* 487 */           if ((unit != null) && (!unit.equals("")))
/*     */           {
/*     */ 
/* 490 */             out.write(10);
/* 491 */             out.write(10);
/* 492 */             out.write(40);
/* 493 */             out.print(FormatUtil.getString(unit));
/* 494 */             out.write(41);
/* 495 */             out.write(10);
/*     */           }
/*     */           
/*     */ 
/* 499 */           out.write("\n\t</td>\n\t<td class=\"yellowgrayborder\" width='70%'>");
/* 500 */           out.print(FormatUtil.getString(lowtenlist));
/* 501 */           out.write(" \n\t</td>\n\t</tr>\n   \t<tr>\n\t<td class='columnheading' width='30%' >");
/* 502 */           out.print(FormatUtil.getString("am.webclient.historydatareport.stdDevn.highTen.text"));
/* 503 */           out.write(10);
/* 504 */           if ((unit != null) && (!unit.equals("")))
/*     */           {
/*     */ 
/* 507 */             out.write(10);
/* 508 */             out.write(32);
/* 509 */             out.write(40);
/* 510 */             out.print(FormatUtil.getString(unit));
/* 511 */             out.write(41);
/* 512 */             out.write(10);
/*     */           }
/*     */           
/*     */ 
/* 516 */           out.write("\n\t</td>\n\t<td class=\"yellowgrayborder\" width='70%'>");
/* 517 */           out.print(FormatUtil.getString(hightenlist));
/* 518 */           out.write("  \n\t<td>\n   </tr>\n</table>\n</TD>\n</TR>\n</TABLE>\n\n<br>\n<br>\n<table width=\"98%\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\">\n  <tr>\n     <td class=\"columnheading\"><b>");
/* 519 */           out.print(FormatUtil.getString("am.webclient.historydata.note.text"));
/* 520 */           out.write("</b><br></td>\n  </tr> \n  <tr>\n     <td class=\"bodytext\"> ");
/* 521 */           out.print(FormatUtil.getString("am.webclient.historydatareport.stdDevn.help.summary"));
/* 522 */           out.write("</td>\n  </tr>  \n \n</table>\n<br>\n</body>\n</html>\n");
/*     */         }
/* 524 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 525 */         out = _jspx_out;
/* 526 */         if ((out != null) && (out.getBufferSize() != 0))
/* 527 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 528 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 531 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 537 */     PageContext pageContext = _jspx_page_context;
/* 538 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 540 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 541 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 542 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 544 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 546 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 547 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 548 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 549 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 550 */       return true;
/*     */     }
/* 552 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 553 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 558 */     PageContext pageContext = _jspx_page_context;
/* 559 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 561 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 562 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 563 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 565 */     _jspx_th_c_005fout_005f1.setValue("${STATUS}");
/* 566 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 567 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 568 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 569 */       return true;
/*     */     }
/* 571 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 572 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\StandardDeviation_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */