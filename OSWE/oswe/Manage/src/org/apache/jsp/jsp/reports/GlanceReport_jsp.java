/*      */ package org.apache.jsp.jsp.reports;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.SummaryBean;
/*      */ import com.adventnet.appmanager.reporting.dataproducer.ReportGraphs;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.BarChart;
/*      */ import com.adventnet.awolf.tags.StackBarChart;
/*      */ import com.adventnet.awolf.tags.XYAreaChart;
/*      */ import java.awt.Color;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*      */ 
/*      */ public final class GlanceReport_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   32 */   private static final JspFactory _jspxFactory = ;
/*      */   
/*      */   private static Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolors;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fseriesPaintColor_005fnodatamessage_005flink_005flegend_005fheight_005fdataSetProducer_005fchartTitle;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public Map<String, Long> getDependants()
/*      */   {
/*   53 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   57 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolors = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fseriesPaintColor_005fnodatamessage_005flink_005flegend_005fheight_005fdataSetProducer_005fchartTitle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   69 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   73 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/*   74 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.release();
/*   75 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   76 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   77 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*   78 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/*   79 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/*   80 */     this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor.release();
/*   81 */     this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolors.release();
/*   82 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   83 */     this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fseriesPaintColor_005fnodatamessage_005flink_005flegend_005fheight_005fdataSetProducer_005fchartTitle.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   90 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*   93 */     JspWriter out = null;
/*   94 */     Object page = this;
/*   95 */     JspWriter _jspx_out = null;
/*   96 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  100 */       response.setContentType("text/html");
/*  101 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  103 */       _jspx_page_context = pageContext;
/*  104 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  105 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  106 */       session = pageContext.getSession();
/*  107 */       out = pageContext.getOut();
/*  108 */       _jspx_out = out;
/*      */       
/*  110 */       out.write("\n\n\n\n\n\n\n\n\n\n \n \n\n\n\n\n\n\n");
/*  111 */       ReportGraphs reportGraph = null;
/*  112 */       reportGraph = (ReportGraphs)_jspx_page_context.getAttribute("reportGraph", 1);
/*  113 */       if (reportGraph == null) {
/*  114 */         reportGraph = new ReportGraphs();
/*  115 */         _jspx_page_context.setAttribute("reportGraph", reportGraph, 1);
/*      */       }
/*  117 */       out.write(10);
/*  118 */       SummaryBean sumGraph = null;
/*  119 */       sumGraph = (SummaryBean)_jspx_page_context.getAttribute("sumGraph", 1);
/*  120 */       if (sumGraph == null) {
/*  121 */         sumGraph = new SummaryBean();
/*  122 */         _jspx_page_context.setAttribute("sumGraph", sumGraph, 1);
/*      */       }
/*  124 */       out.write("\n\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n</head>\n\n");
/*      */       
/*  126 */       com.adventnet.appmanager.reporting.form.ReportForm frm = (com.adventnet.appmanager.reporting.form.ReportForm)request.getAttribute("ReportForm");
/*  127 */       Color[] colorsArray = { new Color(153, 0, 153), new Color(51, 204, 0), new Color(255, 0, 0), new Color(245, 16, 236), new Color(0, 89, 189), new Color(140, 100, 40), new Color(71, 190, 250), new Color(93, 18, 225), new Color(52, 255, 50) };
/*  128 */       ArrayList list = frm.getMonitors();
/*      */       
/*  130 */       String hostresid = (String)request.getAttribute("hostresid");
/*  131 */       String queryresid = (String)request.getAttribute("queryresid");
/*      */       
/*  133 */       out.write("\n<body>\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\n\n<tr><td colspan=\"4\" height=\"10\"></td></tr>\n<tr>\n<td colspan=\"4\">\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"5\">\n<tr>\n<td class=\"at-a-glance-text\" width=\"100%\">");
/*  134 */       out.print(FormatUtil.getString("am.mypage.monitorname.text"));
/*  135 */       out.write("&nbsp; : &nbsp;<b>\n");
/*  136 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  138 */       out.write("</b>\n\n&nbsp;|&nbsp;");
/*  139 */       out.print(FormatUtil.getString("am.webclient.reports.availability"));
/*  140 */       out.write("&nbsp; : &nbsp;<b>");
/*  141 */       if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_page_context))
/*      */         return;
/*  143 */       out.write("</b>\n\n&nbsp;|&nbsp;\n");
/*  144 */       out.print(FormatUtil.getString("webclient.performance.reports.period"));
/*  145 */       out.write("&nbsp; : &nbsp;<b>\n");
/*  146 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */         return;
/*  148 */       out.write("</b></td>\n</tr>\n</table>\n</td>\n</tr>\n</table>\n<br/>\n");
/*  149 */       out.write(10);
/*      */       
/*  151 */       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  152 */       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  153 */       _jspx_th_c_005fchoose_005f0.setParent(null);
/*  154 */       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  155 */       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */         for (;;) {
/*  157 */           out.write("\n    ");
/*      */           
/*  159 */           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  160 */           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  161 */           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */           
/*  163 */           _jspx_th_c_005fwhen_005f0.setTest("${actionMethod == \"generateGlanceReport\"}");
/*  164 */           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  165 */           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */             for (;;) {
/*  167 */               out.write(10);
/*      */               
/*  169 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  170 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  171 */               _jspx_th_html_005fform_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */               
/*  173 */               _jspx_th_html_005fform_005f0.setAction("/showReports.do");
/*      */               
/*  175 */               _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/*  176 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  177 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/*  179 */                   out.write(32);
/*  180 */                   if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  182 */                   out.write(32);
/*  183 */                   if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  185 */                   out.write(32);
/*  186 */                   out.write(10);
/*  187 */                   if (_jspx_meth_html_005fhidden_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  189 */                   out.write(32);
/*  190 */                   out.write(10);
/*  191 */                   if (_jspx_meth_html_005fhidden_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  193 */                   out.write(32);
/*  194 */                   if (_jspx_meth_html_005fhidden_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  196 */                   out.write(32);
/*  197 */                   if (_jspx_meth_html_005fhidden_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  199 */                   out.write(32);
/*  200 */                   out.write(10);
/*  201 */                   if (_jspx_meth_html_005fhidden_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  203 */                   out.write(32);
/*  204 */                   if (_jspx_meth_html_005fhidden_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  206 */                   out.write(32);
/*  207 */                   if (_jspx_meth_html_005fhidden_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  209 */                   out.write(32);
/*  210 */                   out.write(10);
/*  211 */                   if (_jspx_meth_html_005fhidden_005f9(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  213 */                   out.write(32);
/*  214 */                   if (_jspx_meth_html_005fhidden_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  216 */                   out.write(10);
/*  217 */                   if (_jspx_meth_html_005fhidden_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  219 */                   out.write(10);
/*  220 */                   out.write(10);
/*      */                   
/*      */ 
/*      */ 
/*  224 */                   ArrayList dataList = (ArrayList)request.getAttribute("DATALIST");
/*  225 */                   ArrayList attributeList = (ArrayList)request.getAttribute("attributename");
/*  226 */                   ArrayList attributeUnitList = (ArrayList)request.getAttribute("ATTRIBUTEUNITLIST");
/*  227 */                   if (request.getAttribute("ATTRIBUTELIST") != null) {}
/*      */                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  233 */                   out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"grayfullborder\">\n\t<tr>\n            <td>&nbsp;\n\t    </td>\n        </tr>\t\n          <tr>\n            <td>\n<table  width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n<tr>\n\t");
/*      */                   
/*  235 */                   for (int i = 0; i < attributeList.size(); i++)
/*      */                   {
/*  237 */                     ArrayList moList = (ArrayList)dataList.get(i);
/*      */                     
/*  239 */                     if ((i > 1) && ((i + 1) % 3 == 1))
/*      */                     {
/*      */ 
/*  242 */                       out.write("\n\t\t\t<tr>\n\t\t\t");
/*      */                     }
/*      */                     
/*      */ 
/*  246 */                     Map params = new Hashtable();
/*  247 */                     if (i != 0)
/*      */                     {
/*  249 */                       params.put("type", "ATAGLANCE");
/*      */                     }
/*      */                     else
/*      */                     {
/*  253 */                       params.put("type", "ATAGLANCEAVAILABILITY");
/*      */                     }
/*  255 */                     params.put("data", moList);
/*  256 */                     String att = (String)attributeList.get(i);
/*  257 */                     att = att.substring(att.indexOf("#") + 1).trim();
/*  258 */                     reportGraph.setParams(params);
/*      */                     
/*  260 */                     out.write("\n\t\t\t<td>\n\t\t\t");
/*      */                     
/*  262 */                     if (i != 0)
/*      */                     {
/*      */ 
/*  265 */                       out.write("\n\t\t\t");
/*      */                       
/*  267 */                       BarChart _jspx_th_awolf_005fbarchart_005f0 = (BarChart)this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor.get(BarChart.class);
/*  268 */                       _jspx_th_awolf_005fbarchart_005f0.setPageContext(_jspx_page_context);
/*  269 */                       _jspx_th_awolf_005fbarchart_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/*  271 */                       _jspx_th_awolf_005fbarchart_005f0.setDataSetProducer("reportGraph");
/*      */                       
/*  273 */                       _jspx_th_awolf_005fbarchart_005f0.setWidth("450");
/*      */                       
/*  275 */                       _jspx_th_awolf_005fbarchart_005f0.setHeight("450");
/*      */                       
/*  277 */                       _jspx_th_awolf_005fbarchart_005f0.setLegend("false");
/*      */                       
/*  279 */                       _jspx_th_awolf_005fbarchart_005f0.setUrl(true);
/*      */                       
/*  281 */                       _jspx_th_awolf_005fbarchart_005f0.setXaxisLabel("");
/*      */                       
/*  283 */                       _jspx_th_awolf_005fbarchart_005f0.setYaxisLabel(FormatUtil.getString((String)attributeUnitList.get(i)));
/*      */                       
/*  285 */                       _jspx_th_awolf_005fbarchart_005f0.setChartTitle(FormatUtil.getString(att));
/*      */                       
/*  287 */                       _jspx_th_awolf_005fbarchart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*      */                       
/*  289 */                       _jspx_th_awolf_005fbarchart_005f0.setMaxBarWidth(0.07D);
/*      */                       
/*  291 */                       _jspx_th_awolf_005fbarchart_005f0.setBarcolor(new java.awt.Paint[] { colorsArray[((i + 1) % 8)] });
/*      */                       
/*  293 */                       _jspx_th_awolf_005fbarchart_005f0.setLabelRotation(false);
/*      */                       
/*  295 */                       _jspx_th_awolf_005fbarchart_005f0.setBaseItemLabel(false);
/*      */                       
/*  297 */                       _jspx_th_awolf_005fbarchart_005f0.setTwoDimensionBar(true);
/*  298 */                       int _jspx_eval_awolf_005fbarchart_005f0 = _jspx_th_awolf_005fbarchart_005f0.doStartTag();
/*  299 */                       if (_jspx_eval_awolf_005fbarchart_005f0 != 0) {
/*  300 */                         if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/*  301 */                           out = _jspx_page_context.pushBody();
/*  302 */                           _jspx_th_awolf_005fbarchart_005f0.setBodyContent((BodyContent)out);
/*  303 */                           _jspx_th_awolf_005fbarchart_005f0.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/*  306 */                           out.write("\n\t              ");
/*  307 */                           int evalDoAfterBody = _jspx_th_awolf_005fbarchart_005f0.doAfterBody();
/*  308 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*  311 */                         if (_jspx_eval_awolf_005fbarchart_005f0 != 1) {
/*  312 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/*  315 */                       if (_jspx_th_awolf_005fbarchart_005f0.doEndTag() == 5) {
/*  316 */                         this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor.reuse(_jspx_th_awolf_005fbarchart_005f0); return;
/*      */                       }
/*      */                       
/*  319 */                       this._005fjspx_005ftagPool_005fawolf_005fbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fnodatamessage_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolor.reuse(_jspx_th_awolf_005fbarchart_005f0);
/*  320 */                       out.write("\n\t\t\t");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*      */ 
/*  326 */                       out.write("\n\t\t\t");
/*      */                       
/*  328 */                       StackBarChart _jspx_th_awolf_005fstackbarchart_005f0 = (StackBarChart)this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolors.get(StackBarChart.class);
/*  329 */                       _jspx_th_awolf_005fstackbarchart_005f0.setPageContext(_jspx_page_context);
/*  330 */                       _jspx_th_awolf_005fstackbarchart_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                       
/*  332 */                       _jspx_th_awolf_005fstackbarchart_005f0.setDataSetProducer("reportGraph");
/*      */                       
/*  334 */                       _jspx_th_awolf_005fstackbarchart_005f0.setTwoDimensionBar(true);
/*      */                       
/*  336 */                       _jspx_th_awolf_005fstackbarchart_005f0.setWidth("450");
/*      */                       
/*  338 */                       _jspx_th_awolf_005fstackbarchart_005f0.setHeight("450");
/*      */                       
/*  340 */                       _jspx_th_awolf_005fstackbarchart_005f0.setLegend("false");
/*      */                       
/*  342 */                       _jspx_th_awolf_005fstackbarchart_005f0.setUrl(true);
/*      */                       
/*  344 */                       _jspx_th_awolf_005fstackbarchart_005f0.setXaxisLabel("");
/*      */                       
/*  346 */                       _jspx_th_awolf_005fstackbarchart_005f0.setYaxisLabel(FormatUtil.getString((String)attributeUnitList.get(i)));
/*      */                       
/*  348 */                       _jspx_th_awolf_005fstackbarchart_005f0.setChartTitle(FormatUtil.getString(att));
/*      */                       
/*  350 */                       _jspx_th_awolf_005fstackbarchart_005f0.setMaxBarWidth(0.07D);
/*      */                       
/*  352 */                       _jspx_th_awolf_005fstackbarchart_005f0.setLabelRotation(false);
/*      */                       
/*  354 */                       _jspx_th_awolf_005fstackbarchart_005f0.setBaseItemLabel(false);
/*      */                       
/*  356 */                       _jspx_th_awolf_005fstackbarchart_005f0.setBarcolors(new java.awt.Paint[] { new Color(0, 255, 0), new Color(255, 0, 0), new Color(93, 18, 225), new Color(255, 0, 255) });
/*  357 */                       int _jspx_eval_awolf_005fstackbarchart_005f0 = _jspx_th_awolf_005fstackbarchart_005f0.doStartTag();
/*  358 */                       if (_jspx_eval_awolf_005fstackbarchart_005f0 != 0) {
/*  359 */                         if (_jspx_eval_awolf_005fstackbarchart_005f0 != 1) {
/*  360 */                           out = _jspx_page_context.pushBody();
/*  361 */                           _jspx_th_awolf_005fstackbarchart_005f0.setBodyContent((BodyContent)out);
/*  362 */                           _jspx_th_awolf_005fstackbarchart_005f0.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/*  365 */                           out.write("\n      \t\t\t");
/*  366 */                           int evalDoAfterBody = _jspx_th_awolf_005fstackbarchart_005f0.doAfterBody();
/*  367 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*  370 */                         if (_jspx_eval_awolf_005fstackbarchart_005f0 != 1) {
/*  371 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/*  374 */                       if (_jspx_th_awolf_005fstackbarchart_005f0.doEndTag() == 5) {
/*  375 */                         this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolors.reuse(_jspx_th_awolf_005fstackbarchart_005f0); return;
/*      */                       }
/*      */                       
/*  378 */                       this._005fjspx_005ftagPool_005fawolf_005fstackbarchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005furl_005ftwoDimensionBar_005fmaxBarWidth_005flegend_005flabelRotation_005fheight_005fdataSetProducer_005fchartTitle_005fbaseItemLabel_005fbarcolors.reuse(_jspx_th_awolf_005fstackbarchart_005f0);
/*  379 */                       out.write("\n\t\t\t");
/*      */                     }
/*      */                     
/*  382 */                     out.write("\n\t\t\t</td>\n\t\t\t");
/*      */                     
/*  384 */                     if ((i > 0) && ((i + 1) % 3 == 0))
/*      */                     {
/*      */ 
/*  387 */                       out.write("\n\t\t\t</tr>\n\t\n\t");
/*      */                     }
/*      */                   }
/*      */                   
/*  391 */                   out.write("\n    \n  </table>\n\n </td>\n   </tr>\n  </table>\n");
/*  392 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  393 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  397 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  398 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */               }
/*      */               
/*  401 */               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  402 */               out.write(10);
/*  403 */               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  404 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  408 */           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  409 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */           }
/*      */           
/*  412 */           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  413 */           out.write("\n    ");
/*      */           
/*  415 */           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  416 */           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  417 */           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*  418 */           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  419 */           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */             for (;;) {
/*  421 */               out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" >\n        <tr>\n<td width='100%' >\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"grayfullborder\">\n");
/*      */               try
/*      */               {
/*  424 */                 ArrayList graphs = (ArrayList)request.getAttribute("graphs");
/*  425 */                 ArrayList attributename = (ArrayList)request.getAttribute("attributename");
/*  426 */                 for (int i = 0; i < 1; i++)
/*      */                 {
/*  428 */                   String images = (String)graphs.get(i);
/*  429 */                   String[] imagearray = images.split("working", 3);
/*  430 */                   String imagepath = imagearray[2];
/*      */                   
/*  432 */                   imagepath = imagepath.substring(1, imagepath.length());
/*  433 */                   imagepath = FormatUtil.replaceStringBySpecifiedString(imagepath, "\\", "/", 0);
/*  434 */                   String link = "/showHistoryData.do?method=getAvailabilityData&resourceid=" + (String)request.getAttribute("resourceid") + "&period=0";
/*      */                   
/*  436 */                   out.write("\n\t<tr> \n\t      <td  width=\"33%\" class=\"bodytextbold\" align=\"center\" >");
/*  437 */                   out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/*  438 */                   out.write("(%)\n\t      </td>\n\t </tr>\n         <tr>\n<td width='33%' >\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" >  \n\t  <tr> \n          <td  align=\"center\" class=\"bodytext\" > \n\t ");
/*  439 */                   out.println("<a href=\"" + link + "\">");
/*  440 */                   out.write("\n          ");
/*  441 */                   out.println("<img src=\"" + imagepath + "\" border=\"0\"  >");
/*  442 */                   out.write(10);
/*  443 */                   out.write(9);
/*  444 */                   out.write(32);
/*  445 */                   out.println("</a>");
/*  446 */                   out.write("\n           </td>\n        </tr>\n\t\n\t</table>\n </td> \n\n");
/*      */                 }
/*      */                 
/*      */               }
/*      */               catch (Exception ex)
/*      */               {
/*  452 */                 ex.printStackTrace();
/*      */               }
/*  454 */               ArrayList data = (ArrayList)request.getAttribute("data");
/*  455 */               String resid = (String)request.getAttribute("resourceid");
/*  456 */               long starttime = ((Long)request.getAttribute("startTime")).longValue();
/*  457 */               long endtime = ((Long)request.getAttribute("endtime")).longValue();
/*      */               
/*  459 */               int k = 2;
/*  460 */               for (int n = 4; n < data.size(); n++)
/*      */               {
/*  462 */                 ArrayList a1 = (ArrayList)data.get(n);
/*  463 */                 String attributename = (String)a1.get(0);
/*  464 */                 String attributeid = (String)a1.get(1);
/*  465 */                 List allSecondLevelAttribute = com.adventnet.appmanager.util.ReportUtil.getAllSecondLevelAttribute();
/*  466 */                 ArrayList childAttributes = (ArrayList)request.getAttribute("childAttributes");
/*  467 */                 if ((!allSecondLevelAttribute.contains(attributeid)) && ((childAttributes.isEmpty()) || (!childAttributes.contains(attributeid))))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*  472 */                   String url = "/showHistoryData.do?method=getData&resourceid=" + resid + "&attributeid=" + attributeid + "&period=0";
/*  473 */                   sumGraph.setResid(resid);
/*      */                   
/*  475 */                   if (com.adventnet.appmanager.util.Constants.sqlManager) {
/*  476 */                     if (com.adventnet.appmanager.util.DBUtil.windowsAttributeList.contains(attributeid))
/*      */                     {
/*  478 */                       if ((hostresid == null) && (resid != null)) {
/*  479 */                         hostresid = resid;
/*      */                       }
/*  481 */                       url = "/showHistoryData.do?method=getData&resourceid=" + hostresid + "&attributeid=" + attributeid + "&period=0";
/*  482 */                       sumGraph.setResid(hostresid);
/*      */                     }
/*  484 */                     else if (com.adventnet.appmanager.util.DBUtil.querymonitorAttributeList.contains(attributeid))
/*      */                     {
/*  486 */                       url = "/showHistoryData.do?method=getData&resourceid=" + queryresid + "&attributeid=" + attributeid + "&period=0";
/*  487 */                       sumGraph.setResid(queryresid);
/*      */                     }
/*      */                   }
/*      */                   try
/*      */                   {
/*  492 */                     ArrayList attribDetails = com.adventnet.appmanager.util.DBUtil.getArchTableNameWithExpression(attributeid);
/*  493 */                     String archivedTableName = attributeid != null ? (String)attribDetails.get(0) : "";
/*  494 */                     long[] dailyRptTimestamp = com.adventnet.appmanager.reporting.ReportUtilities.getDailyStartEndTime(starttime, endtime, archivedTableName);
/*  495 */                     if (dailyRptTimestamp[2] > 0L)
/*      */                     {
/*  497 */                       sumGraph.setDailyRptStarttime(String.valueOf(dailyRptTimestamp[2]));
/*  498 */                       sumGraph.setDailyRptEndtime(String.valueOf(dailyRptTimestamp[3]));
/*      */                     }
/*      */                   } catch (Exception e) {
/*  501 */                     e.printStackTrace();
/*      */                   }
/*      */                   
/*      */ 
/*  505 */                   sumGraph.setAttributeid(attributeid);
/*  506 */                   sumGraph.setStarttime(String.valueOf(starttime));
/*  507 */                   sumGraph.setEndtime(String.valueOf(endtime));
/*  508 */                   sumGraph.setEntity(attributename);
/*  509 */                   sumGraph.setXYAreaChart(true);
/*  510 */                   if ((k > 2) && (k % 3 == 1))
/*      */                   {
/*      */ 
/*  513 */                     out.write("\n\t\t<tr>\n");
/*      */                   }
/*      */                   
/*  516 */                   java.awt.Paint p = colorsArray[(k % 8)];
/*      */                   
/*      */ 
/*  519 */                   out.write("\n\t\t<td width='33%' >\n       \t \t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" >  \n   \t\t<tr> \n          \t<td height=\"190\" align=\"center\" class=\"bodytext\" > \n          \t");
/*      */                   
/*  521 */                   XYAreaChart _jspx_th_awolf_005fxyareachart_005f0 = (XYAreaChart)this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fseriesPaintColor_005fnodatamessage_005flink_005flegend_005fheight_005fdataSetProducer_005fchartTitle.get(XYAreaChart.class);
/*  522 */                   _jspx_th_awolf_005fxyareachart_005f0.setPageContext(_jspx_page_context);
/*  523 */                   _jspx_th_awolf_005fxyareachart_005f0.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                   
/*  525 */                   _jspx_th_awolf_005fxyareachart_005f0.setDataSetProducer("sumGraph");
/*      */                   
/*  527 */                   _jspx_th_awolf_005fxyareachart_005f0.setWidth("300");
/*      */                   
/*  529 */                   _jspx_th_awolf_005fxyareachart_005f0.setHeight("200");
/*      */                   
/*  531 */                   _jspx_th_awolf_005fxyareachart_005f0.setLegend("false");
/*      */                   
/*  533 */                   _jspx_th_awolf_005fxyareachart_005f0.setXaxisLabel(FormatUtil.getString("Time"));
/*      */                   
/*  535 */                   _jspx_th_awolf_005fxyareachart_005f0.setYaxisLabel(attributename);
/*      */                   
/*  537 */                   _jspx_th_awolf_005fxyareachart_005f0.setChartTitle(attributename);
/*      */                   
/*  539 */                   _jspx_th_awolf_005fxyareachart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/*      */                   
/*  541 */                   _jspx_th_awolf_005fxyareachart_005f0.setSeriesPaintColor(p);
/*      */                   
/*  543 */                   _jspx_th_awolf_005fxyareachart_005f0.setLink(url);
/*  544 */                   int _jspx_eval_awolf_005fxyareachart_005f0 = _jspx_th_awolf_005fxyareachart_005f0.doStartTag();
/*  545 */                   if (_jspx_eval_awolf_005fxyareachart_005f0 != 0) {
/*  546 */                     if (_jspx_eval_awolf_005fxyareachart_005f0 != 1) {
/*  547 */                       out = _jspx_page_context.pushBody();
/*  548 */                       _jspx_th_awolf_005fxyareachart_005f0.setBodyContent((BodyContent)out);
/*  549 */                       _jspx_th_awolf_005fxyareachart_005f0.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/*  552 */                       out.write(10);
/*  553 */                       out.write(9);
/*  554 */                       out.write(9);
/*  555 */                       int evalDoAfterBody = _jspx_th_awolf_005fxyareachart_005f0.doAfterBody();
/*  556 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  559 */                     if (_jspx_eval_awolf_005fxyareachart_005f0 != 1) {
/*  560 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  563 */                   if (_jspx_th_awolf_005fxyareachart_005f0.doEndTag() == 5) {
/*  564 */                     this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fseriesPaintColor_005fnodatamessage_005flink_005flegend_005fheight_005fdataSetProducer_005fchartTitle.reuse(_jspx_th_awolf_005fxyareachart_005f0); return;
/*      */                   }
/*      */                   
/*  567 */                   this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fseriesPaintColor_005fnodatamessage_005flink_005flegend_005fheight_005fdataSetProducer_005fchartTitle.reuse(_jspx_th_awolf_005fxyareachart_005f0);
/*  568 */                   out.write("\n\t   \t</td>\n\t        </tr> \n\t\t</table>  \n\t\t</td> \n\t");
/*      */                   
/*  570 */                   if (k % 3 == 0)
/*      */                   {
/*      */ 
/*  573 */                     out.write("\t</tr>\n\t\t<tr>\n\t\t<td colspan='3'>&nbsp;\n\t\t</td>\n\t\t</tr>\n\t");
/*      */                   }
/*      */                   
/*      */ 
/*  577 */                   out.write(10);
/*      */                   
/*  579 */                   k++;
/*      */                 }
/*      */               }
/*  582 */               out.write("\n\n<tr><td width='100%' colspan='3' align='center'>\n");
/*      */               
/*      */ 
/*  585 */               Hashtable tableHash = (Hashtable)request.getAttribute("SECONDLEVELATTRIBS");
/*  586 */               java.util.Enumeration enum1 = tableHash.keys();
/*  587 */               while (enum1.hasMoreElements())
/*      */               {
/*  589 */                 String tableName = (String)enum1.nextElement();
/*      */                 
/*      */ 
/*  592 */                 out.write("\n\t\t<table width='100%' border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align='center'>\n\t\t<tr>\n\t\t<td class=\"bodytextbold\" align='center'>\n");
/*      */                 
/*  594 */                 out.println(FormatUtil.getString(tableName));
/*      */                 
/*  596 */                 out.write("\n\t\t</td></tr>\n\t\t\n\t\t</table>\n\t\t<table class=\"lrtbdarkborder\" width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
/*      */                 
/*  598 */                 ArrayList contentList = (ArrayList)tableHash.get(tableName);
/*  599 */                 for (int i = 0; i < contentList.size(); i++)
/*      */                 {
/*  601 */                   ArrayList dataList = (ArrayList)contentList.get(i);
/*      */                   
/*      */ 
/*  604 */                   out.write("\n\t\t\t<tr>\n");
/*      */                   
/*  606 */                   for (int j = 0; j < dataList.size(); j++)
/*      */                   {
/*  608 */                     if (i == 0)
/*      */                     {
/*      */ 
/*  611 */                       out.write("\n\t\t\t\t\t<td width=\"19%\" class=\"tableheadingbborder\" align=\"left\">\n");
/*      */ 
/*      */                     }
/*      */                     else
/*      */                     {
/*  616 */                       out.write("\n\t\t\t\t\t<td class=\"whitegrayborder\" align=\"left\">\n");
/*      */                     }
/*      */                     
/*  619 */                     if ((tableName != null) && ((tableName.equals("Process")) || (tableName.equals("DataBase"))) && (j == 1) && (i > 0))
/*      */                     {
/*  621 */                       int avail = Math.round(Float.valueOf(dataList.get(j).toString()).floatValue());
/*  622 */                       int notavail = 100 - avail;
/*      */                       
/*  624 */                       out.write("\n\t\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t\t<tr><td width=\"60%\" height=\"26\" class=\"bodytext\">\n\t\t\t\t<table align=left width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\">\n\t\t\t\t<tr>\t\n\t\t\t\t<td class=\"criticalbar\" width=\"");
/*  625 */                       out.print(notavail);
/*  626 */                       out.write("%\"><img src=\"/images/spacer.gif\"  height=\"9\" width=\"1\"></td>\n\t\t\t\t<td  class=\"availabilitybar\" width=\"");
/*  627 */                       out.print(avail);
/*  628 */                       out.write("%\"> <img src=\"/images/spacer.gif\"  height=\"9\" width=\"1\"></td>\n\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t\t<td width=\"40%\" height=\"26\" class=\"bodytext\">\n\t\t\t\t");
/*  629 */                       out.print(FormatUtil.getString("am.webclient.historydata.uptime.text"));
/*  630 */                       out.write(32);
/*  631 */                       out.write(45);
/*  632 */                       out.write(32);
/*  633 */                       out.println((String)dataList.get(j));
/*  634 */                       out.write("%\n\t\t\t\t</td></tr>\n\t\t\t\t</table>\n");
/*      */                     }
/*      */                     else
/*      */                     {
/*  638 */                       out.println((String)dataList.get(j));
/*      */                     }
/*      */                     
/*  641 */                     out.write("\n\t\t\t\t</td>\t\n");
/*      */                   }
/*      */                   
/*      */ 
/*      */ 
/*  646 */                   out.write("\n\t\t\t</tr>\n");
/*      */                 }
/*      */                 
/*      */ 
/*  650 */                 out.write("\n\t\t</table>\n\t\t<br/>\n");
/*      */               }
/*      */               
/*      */ 
/*      */ 
/*  655 */               out.write("\n</td></tr> \n</table></td></tr> \n</td></tr>\n</table>\n ");
/*  656 */               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  657 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/*  661 */           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  662 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */           }
/*      */           
/*  665 */           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  666 */           out.write(10);
/*  667 */           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  668 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  672 */       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  673 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */       }
/*      */       else {
/*  676 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  677 */         out.write("\n</body>\n</html>\n");
/*      */       }
/*  679 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  680 */         out = _jspx_out;
/*  681 */         if ((out != null) && (out.getBufferSize() != 0))
/*  682 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  683 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  686 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  692 */     PageContext pageContext = _jspx_page_context;
/*  693 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  695 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/*  696 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  697 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  699 */     _jspx_th_c_005fout_005f0.setValue("${headingCategory}");
/*  700 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  701 */     if (_jspx_eval_c_005fout_005f0 != 0) {
/*  702 */       if (_jspx_eval_c_005fout_005f0 != 1) {
/*  703 */         out = _jspx_page_context.pushBody();
/*  704 */         _jspx_th_c_005fout_005f0.setBodyContent((BodyContent)out);
/*  705 */         _jspx_th_c_005fout_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  708 */         out.write(10);
/*  709 */         out.write(32);
/*  710 */         int evalDoAfterBody = _jspx_th_c_005fout_005f0.doAfterBody();
/*  711 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  714 */       if (_jspx_eval_c_005fout_005f0 != 1) {
/*  715 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  718 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  719 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f0);
/*  720 */       return true;
/*      */     }
/*  722 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f0);
/*  723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  728 */     PageContext pageContext = _jspx_page_context;
/*  729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  731 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.get(FormatDateTag.class);
/*  732 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/*  733 */     _jspx_th_fmt_005fformatDate_005f0.setParent(null);
/*      */     
/*  735 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${systime}");
/*      */     
/*  737 */     _jspx_th_fmt_005fformatDate_005f0.setType("BOTH");
/*  738 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/*  739 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/*  740 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/*  741 */       return true;
/*      */     }
/*  743 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005ftype_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/*  744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  749 */     PageContext pageContext = _jspx_page_context;
/*  750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  752 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/*  753 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/*  754 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/*  756 */     _jspx_th_c_005fout_005f1.setValue("${headingPeriod}");
/*  757 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/*  758 */     if (_jspx_eval_c_005fout_005f1 != 0) {
/*  759 */       if (_jspx_eval_c_005fout_005f1 != 1) {
/*  760 */         out = _jspx_page_context.pushBody();
/*  761 */         _jspx_th_c_005fout_005f1.setBodyContent((BodyContent)out);
/*  762 */         _jspx_th_c_005fout_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  765 */         out.write(10);
/*  766 */         out.write(9);
/*  767 */         out.write(32);
/*  768 */         int evalDoAfterBody = _jspx_th_c_005fout_005f1.doAfterBody();
/*  769 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  772 */       if (_jspx_eval_c_005fout_005f1 != 1) {
/*  773 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  776 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/*  777 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f1);
/*  778 */       return true;
/*      */     }
/*  780 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f1);
/*  781 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  786 */     PageContext pageContext = _jspx_page_context;
/*  787 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  789 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/*  790 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/*  791 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  793 */     _jspx_th_html_005fhidden_005f0.setProperty("actionMethod");
/*  794 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/*  795 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/*  796 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/*  797 */       return true;
/*      */     }
/*  799 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/*  800 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  805 */     PageContext pageContext = _jspx_page_context;
/*  806 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  808 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/*  809 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/*  810 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  812 */     _jspx_th_html_005fhidden_005f1.setProperty("attribute");
/*  813 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/*  814 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/*  815 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/*  816 */       return true;
/*      */     }
/*  818 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/*  819 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  824 */     PageContext pageContext = _jspx_page_context;
/*  825 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  827 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/*  828 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/*  829 */     _jspx_th_html_005fhidden_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  831 */     _jspx_th_html_005fhidden_005f2.setProperty("unit");
/*  832 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/*  833 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/*  834 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/*  835 */       return true;
/*      */     }
/*  837 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/*  838 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  843 */     PageContext pageContext = _jspx_page_context;
/*  844 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  846 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/*  847 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/*  848 */     _jspx_th_html_005fhidden_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  850 */     _jspx_th_html_005fhidden_005f3.setProperty("attributeName");
/*  851 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/*  852 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/*  853 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/*  854 */       return true;
/*      */     }
/*  856 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/*  857 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  862 */     PageContext pageContext = _jspx_page_context;
/*  863 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  865 */     HiddenTag _jspx_th_html_005fhidden_005f4 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/*  866 */     _jspx_th_html_005fhidden_005f4.setPageContext(_jspx_page_context);
/*  867 */     _jspx_th_html_005fhidden_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  869 */     _jspx_th_html_005fhidden_005f4.setProperty("resourceid");
/*  870 */     int _jspx_eval_html_005fhidden_005f4 = _jspx_th_html_005fhidden_005f4.doStartTag();
/*  871 */     if (_jspx_th_html_005fhidden_005f4.doEndTag() == 5) {
/*  872 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/*  873 */       return true;
/*      */     }
/*  875 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/*  876 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  881 */     PageContext pageContext = _jspx_page_context;
/*  882 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  884 */     HiddenTag _jspx_th_html_005fhidden_005f5 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/*  885 */     _jspx_th_html_005fhidden_005f5.setPageContext(_jspx_page_context);
/*  886 */     _jspx_th_html_005fhidden_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  888 */     _jspx_th_html_005fhidden_005f5.setProperty("resourceType");
/*  889 */     int _jspx_eval_html_005fhidden_005f5 = _jspx_th_html_005fhidden_005f5.doStartTag();
/*  890 */     if (_jspx_th_html_005fhidden_005f5.doEndTag() == 5) {
/*  891 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f5);
/*  892 */       return true;
/*      */     }
/*  894 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f5);
/*  895 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  900 */     PageContext pageContext = _jspx_page_context;
/*  901 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  903 */     HiddenTag _jspx_th_html_005fhidden_005f6 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/*  904 */     _jspx_th_html_005fhidden_005f6.setPageContext(_jspx_page_context);
/*  905 */     _jspx_th_html_005fhidden_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  907 */     _jspx_th_html_005fhidden_005f6.setProperty("period");
/*  908 */     int _jspx_eval_html_005fhidden_005f6 = _jspx_th_html_005fhidden_005f6.doStartTag();
/*  909 */     if (_jspx_th_html_005fhidden_005f6.doEndTag() == 5) {
/*  910 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f6);
/*  911 */       return true;
/*      */     }
/*  913 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f6);
/*  914 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  919 */     PageContext pageContext = _jspx_page_context;
/*  920 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  922 */     HiddenTag _jspx_th_html_005fhidden_005f7 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/*  923 */     _jspx_th_html_005fhidden_005f7.setPageContext(_jspx_page_context);
/*  924 */     _jspx_th_html_005fhidden_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  926 */     _jspx_th_html_005fhidden_005f7.setProperty("numberOfRows");
/*  927 */     int _jspx_eval_html_005fhidden_005f7 = _jspx_th_html_005fhidden_005f7.doStartTag();
/*  928 */     if (_jspx_th_html_005fhidden_005f7.doEndTag() == 5) {
/*  929 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f7);
/*  930 */       return true;
/*      */     }
/*  932 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f7);
/*  933 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  938 */     PageContext pageContext = _jspx_page_context;
/*  939 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  941 */     HiddenTag _jspx_th_html_005fhidden_005f8 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/*  942 */     _jspx_th_html_005fhidden_005f8.setPageContext(_jspx_page_context);
/*  943 */     _jspx_th_html_005fhidden_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  945 */     _jspx_th_html_005fhidden_005f8.setProperty("startDate");
/*  946 */     int _jspx_eval_html_005fhidden_005f8 = _jspx_th_html_005fhidden_005f8.doStartTag();
/*  947 */     if (_jspx_th_html_005fhidden_005f8.doEndTag() == 5) {
/*  948 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f8);
/*  949 */       return true;
/*      */     }
/*  951 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f8);
/*  952 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f9(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  957 */     PageContext pageContext = _jspx_page_context;
/*  958 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  960 */     HiddenTag _jspx_th_html_005fhidden_005f9 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/*  961 */     _jspx_th_html_005fhidden_005f9.setPageContext(_jspx_page_context);
/*  962 */     _jspx_th_html_005fhidden_005f9.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  964 */     _jspx_th_html_005fhidden_005f9.setProperty("endDate");
/*  965 */     int _jspx_eval_html_005fhidden_005f9 = _jspx_th_html_005fhidden_005f9.doStartTag();
/*  966 */     if (_jspx_th_html_005fhidden_005f9.doEndTag() == 5) {
/*  967 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f9);
/*  968 */       return true;
/*      */     }
/*  970 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f9);
/*  971 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  976 */     PageContext pageContext = _jspx_page_context;
/*  977 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  979 */     HiddenTag _jspx_th_html_005fhidden_005f10 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/*  980 */     _jspx_th_html_005fhidden_005f10.setPageContext(_jspx_page_context);
/*  981 */     _jspx_th_html_005fhidden_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  983 */     _jspx_th_html_005fhidden_005f10.setProperty("Report");
/*      */     
/*  985 */     _jspx_th_html_005fhidden_005f10.setValue("true");
/*  986 */     int _jspx_eval_html_005fhidden_005f10 = _jspx_th_html_005fhidden_005f10.doStartTag();
/*  987 */     if (_jspx_th_html_005fhidden_005f10.doEndTag() == 5) {
/*  988 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f10);
/*  989 */       return true;
/*      */     }
/*  991 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f10);
/*  992 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  997 */     PageContext pageContext = _jspx_page_context;
/*  998 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1000 */     HiddenTag _jspx_th_html_005fhidden_005f11 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 1001 */     _jspx_th_html_005fhidden_005f11.setPageContext(_jspx_page_context);
/* 1002 */     _jspx_th_html_005fhidden_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 1004 */     _jspx_th_html_005fhidden_005f11.setProperty("reporttype");
/*      */     
/* 1006 */     _jspx_th_html_005fhidden_005f11.setValue("html");
/* 1007 */     int _jspx_eval_html_005fhidden_005f11 = _jspx_th_html_005fhidden_005f11.doStartTag();
/* 1008 */     if (_jspx_th_html_005fhidden_005f11.doEndTag() == 5) {
/* 1009 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f11);
/* 1010 */       return true;
/*      */     }
/* 1012 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f11);
/* 1013 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\GlanceReport_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */