/*      */ package org.apache.jsp.jsp.reports;
/*      */ 
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.util.ArrayList;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.FormatDateTag;
/*      */ 
/*      */ public final class Popup_005fCompareReports_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*   26 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*      */   
/*      */   private static java.util.Map<String, Long> _jspx_dependants;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/*   52 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/*   56 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   57 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   58 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   59 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   60 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   61 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   62 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   63 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   64 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   65 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   66 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   67 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   68 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   69 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   70 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   71 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*   72 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*   73 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/*   77 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*   78 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*   79 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*   80 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/*   81 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.release();
/*   82 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/*   83 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange.release();
/*   84 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.release();
/*   85 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
/*   86 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*   87 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*   88 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*   89 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*   90 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*   91 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*   92 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/*   99 */     javax.servlet.http.HttpSession session = null;
/*      */     
/*      */ 
/*  102 */     JspWriter out = null;
/*  103 */     Object page = this;
/*  104 */     JspWriter _jspx_out = null;
/*  105 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/*  109 */       response.setContentType("text/html;charset=UTF-8");
/*  110 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*      */       
/*  112 */       _jspx_page_context = pageContext;
/*  113 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  114 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  115 */       session = pageContext.getSession();
/*  116 */       out = pageContext.getOut();
/*  117 */       _jspx_out = out;
/*      */       
/*  119 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n \n\n\n\n\n\n\n<SCRIPT src=\"../../template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"../../template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"../../template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  120 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/*  122 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n\n");
/*  123 */       com.adventnet.appmanager.bean.SummaryBean sumgraph = null;
/*  124 */       sumgraph = (com.adventnet.appmanager.bean.SummaryBean)_jspx_page_context.getAttribute("sumgraph", 2);
/*  125 */       if (sumgraph == null) {
/*  126 */         sumgraph = new com.adventnet.appmanager.bean.SummaryBean();
/*  127 */         _jspx_page_context.setAttribute("sumgraph", sumgraph, 2);
/*      */       }
/*  129 */       out.write(10);
/*  130 */       out.write(10);
/*  131 */       out.write(10);
/*  132 */       com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil graph = null;
/*  133 */       graph = (com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil)_jspx_page_context.getAttribute("graph", 1);
/*  134 */       if (graph == null) {
/*  135 */         graph = new com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil();
/*  136 */         _jspx_page_context.setAttribute("graph", graph, 1);
/*      */       }
/*  138 */       out.write(10);
/*  139 */       out.write(10);
/*      */       
/*  141 */       String resids = request.getParameter("resids");
/*  142 */       String attids = request.getParameter("attids");
/*  143 */       String period = request.getParameter("period");
/*      */       
/*  145 */       out.write("\n<html>\n<head>\n<script>\nfunction compareLoad()\n{\n\nvar temp=");
/*  146 */       out.print(request.getParameter("period"));
/*  147 */       out.write(";\n document.forms[0].period.value=temp;\n}\n\nfunction reload()\n{\n        if(document.forms[1].startDate.value=='')\n        {\n            alert(\"");
/*  148 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforstarttime"));
/*  149 */       out.write("\");\n            return false\n          }\n        else if(document.forms[1].endDate.value=='')\n        {\n            alert(\"");
/*  150 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforendtime"));
/*  151 */       out.write("\")\n            return false\n        }\n        else if(document.forms[1].startDate.value > document.forms[1].endDate.value)\n            \n        {\n            \n            \n                alert(\"");
/*  152 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforgtstartime"));
/*  153 */       out.write("\" );\n                document.forms[1].startDate.value='';\n                document.forms[1].endDate.value='';\n                return false;\n           \n         }\n         else\n         {\n        \n            document.forms[1].period.value='4';\n          \n            document.forms[1].submit();\n         }\n}\n function fnCheckCustomTime(check)\n {\n if(check.startDate.value=='')\n {\n alert(\"");
/*  154 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforstarttime"));
/*  155 */       out.write("\")\n return false\n }\n else if(check.endDate.value=='')\n {\n alert(\"");
/*  156 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforendtime"));
/*  157 */       out.write("\")\n return false\n }\n var s=check.startDate.value;\n var e=check.endDate.value;\n    if(s>e)\n    {\n     alert(\"");
/*  158 */       out.print(FormatUtil.getString("am.webclient.historydata.jsalertforgtstartime"));
/*  159 */       out.write("\" );\n     return false;\n    }\n    document.forms[1].action=\"/showHistoryData.do?method=getDataforComparision&resids=");
/*  160 */       out.print(resids);
/*  161 */       out.write("&attids=");
/*  162 */       out.print(attids);
/*  163 */       out.write("&period=4&ispopup=true\";\n return true\n }\n  function fnSetEndTime(a)\n{\n\tdocument.forms[1].endDate.value=a;\n}\nfunction fnSetStartTime(a)\n{\n\tdocument.forms[1].startDate.value=a;\n}\n \n function fnPeriod(periodcombo)\n \n{      \n        document.forms[0].reporttype.value = \"html\"; // can only be html as first generate html and then save as pdf\n\tvar val=periodcombo.value;\n\tdocument.forms[0].action=\"/showHistoryData.do?method=getDataforComparision&resids=");
/*  164 */       out.print(resids);
/*  165 */       out.write("&attids=");
/*  166 */       out.print(attids);
/*  167 */       out.write("&period=\"+val+\"&ispopup=true\";\n\tdocument.forms[0].submit();\n}\nfunction generateReport(type)\n{\n");
/*      */       
/*  169 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  170 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  171 */       _jspx_th_c_005fif_005f0.setParent(null);
/*      */       
/*  173 */       _jspx_th_c_005fif_005f0.setTest("${param.period !='4'}");
/*  174 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  175 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */         for (;;) {
/*  177 */           out.write(" \n    document.forms[0].reporttype.value=type;\n    document.forms[0].action.value=\"/showHistoryData.do?method=getDataforComparision&resids=");
/*  178 */           out.print(resids);
/*  179 */           out.write("&attids=");
/*  180 */           out.print(attids);
/*  181 */           out.write("&period=");
/*  182 */           out.print(period);
/*  183 */           out.write("\";\nalert(document.forms[0].action);\n    document.forms[0].submit();\n    document.forms[0].reporttype.value=\"html\";\n");
/*  184 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/*  185 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/*  189 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/*  190 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */       }
/*      */       else {
/*  193 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*  194 */         out.write("    \n\n");
/*      */         
/*  196 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  197 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/*  198 */         _jspx_th_c_005fif_005f1.setParent(null);
/*      */         
/*  200 */         _jspx_th_c_005fif_005f1.setTest("${param.period =='4'}");
/*  201 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/*  202 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */           for (;;) {
/*  204 */             out.write(" \n    document.forms[1].reporttype.value=type;\n    document.forms[1].action.value=\"/showHistoryData.do?method=getDataforComparision&resids=");
/*  205 */             out.print(resids);
/*  206 */             out.write("&attids=");
/*  207 */             out.print(attids);
/*  208 */             out.write("&period=");
/*  209 */             out.print(period);
/*  210 */             out.write("\";\n    alert(document.forms[1].action);\n    document.forms[1].submit();\n    document.forms[1].reporttype.value=\"html\";\n");
/*  211 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/*  212 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/*  216 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/*  217 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */         }
/*      */         else {
/*  220 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*  221 */           out.write("\n}\n \n</script>\n</head>\n<body onLoad=\"javascript:compareLoad()\">\n");
/*      */           
/*  223 */           ArrayList resourceids = (ArrayList)request.getAttribute("resourceids");
/*  224 */           ArrayList attributeids = (ArrayList)request.getAttribute("attributeids");
/*  225 */           java.util.Hashtable resatt_times = (java.util.Hashtable)request.getAttribute("resatt_times");
/*  226 */           java.util.Hashtable resatt_values = (java.util.Hashtable)request.getAttribute("resatt_values");
/*  227 */           ArrayList total_times = (ArrayList)request.getAttribute("total_times");
/*  228 */           ArrayList disp_name = (ArrayList)request.getAttribute("disp_name");
/*  229 */           String start_time = request.getParameter("startTime");
/*  230 */           String end_time = request.getParameter("endTime");
/*  231 */           sumgraph.setStarttime(start_time);
/*  232 */           sumgraph.setEndtime(end_time);
/*  233 */           sumgraph.setComparision(true);
/*  234 */           sumgraph.setResids(resourceids);
/*  235 */           sumgraph.setAttids(attributeids);
/*  236 */           pageContext.setAttribute("STATUS", "SUCCESS");
/*  237 */           if ((total_times != null) && (total_times.size() == 0))
/*      */           {
/*  239 */             pageContext.setAttribute("STATUS", FormatUtil.getString("am.webclient.availablitydatareport.status1.text", new String[] { new java.util.Date(Long.parseLong(start_time)).toString(), new java.util.Date(Long.parseLong(end_time)).toString() }));
/*      */           }
/*  241 */           int height = 300;
/*  242 */           if (resourceids != null)
/*      */           {
/*  244 */             if (resourceids.size() >= 5)
/*  245 */               height += (resourceids.size() - 5) * 30;
/*      */           }
/*  247 */           String height_graph = String.valueOf(height);
/*      */           
/*  249 */           out.write(10);
/*      */           
/*  251 */           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  252 */           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/*  253 */           _jspx_th_c_005fif_005f2.setParent(null);
/*      */           
/*  255 */           _jspx_th_c_005fif_005f2.setTest("${STATUS!='SUCCESS'}");
/*  256 */           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/*  257 */           if (_jspx_eval_c_005fif_005f2 != 0)
/*      */           {
/*  259 */             out.write("\n\n<table cellspacing=\"0\" cellpadding=\"3\" class=\"lrtbdarkborder\" align=\"center\" width=\"95%\">\n<tr>\n<td class=\"yellowgrayborder\">\n<span class=\"bodytextbold\">");
/*  260 */             if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */               return;
/*  262 */             out.write("</span>\n</td>\n</tr>\n<tr>\n<td align=\"right\" class=\"yellowgrayborder\">\n  <a href=\"http://manageengine.com/products/applications_manager/troubleshoot.html#m4\" class=\"staticlinks\"> ");
/*  263 */             out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.morehelp"));
/*  264 */             out.write(" >></a>\n</td>\n</tr>\n</table>\n");
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
/*      */           }
/*  277 */           else if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/*  278 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */           }
/*      */           else {
/*  281 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*  282 */             out.write(10);
/*  283 */             if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*      */               return;
/*  285 */             out.write(10);
/*      */             
/*  287 */             String nameofmonitor = (String)pageContext.getAttribute("rname");
/*  288 */             com.adventnet.appmanager.struts.actions.ComparingSla cs = new com.adventnet.appmanager.struts.actions.ComparingSla();
/*  289 */             String unit = (String)pageContext.findAttribute("unit");
/*  290 */             String valueforperiod = cs.getValueForPeriod(period);
/*      */             
/*  292 */             out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n  <tr>   \n    <td align=\"right\" valign='top'>\n    \t<a class=\"staticlinks\" href=\"javascript:generateReport('pdf')\"><img width=\"23\" align=\"center\" height=\"23\" src=\"/images/icon_pdf-25.gif\" border=\"0\" alt=\"PDF Report\" title=\"");
/*  293 */             out.print(FormatUtil.getString("am.reporttab.pdftitle.text"));
/*  294 */             out.write("\"></a>\n    </td>\n  </tr>\t\n</table>\n\n<table>\n<tr>\n<td>\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr> \n <td width=\"100%\" colspan=\"2\">\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr> \n    <td class=\"tableheadingbborder\">");
/*  295 */             out.print(FormatUtil.getString("am.webclient.historydatareport.periodheading.text", new String[] { FormatUtil.getString(nameofmonitor), valueforperiod }));
/*  296 */             out.write(" </td>\n  </tr>\n</table>\n</td>\n</tr>\n<tr>\n<td width=\"96%\" align=\"right\"> ");
/*      */             
/*  298 */             if (period.equals("-7"))
/*      */             {
/*      */ 
/*  301 */               out.write(" <img src=\"../../images/icon_7days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"> \n            ");
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*      */ 
/*  307 */               out.write(" <a href=\"/showHistoryData.do?method=getDataforComparision&resids=");
/*  308 */               out.print(resids);
/*  309 */               out.write("&attids=");
/*  310 */               out.print(attids);
/*  311 */               out.write("&startTime=");
/*  312 */               out.print(start_time);
/*  313 */               out.write("&endTime=");
/*  314 */               out.print(end_time);
/*  315 */               out.write("&period=-7\"><img src=\"../../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/*  316 */               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/*  317 */               out.write("\"></a> \n            ");
/*      */             }
/*      */             
/*      */ 
/*  321 */             out.write(" </td>\n          <td width=\"4%\"> ");
/*      */             
/*  323 */             if (period.equals("-30"))
/*      */             {
/*      */ 
/*  326 */               out.write(" <img src=\"../../images/icon_30days_disabled.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"> \n            ");
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*  331 */               String url = "/showHistoryData.do?method=getDataforComparision&resids=" + resids + "&attids=" + attids + "&startTime=" + start_time + "&endTime=" + end_time + "&period=-30";
/*      */               
/*  333 */               out.write(" <a href='");
/*  334 */               out.print(url);
/*  335 */               out.write("'><img src=\"../../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/*  336 */               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/*  337 */               out.write("\"></a> \n            ");
/*      */             }
/*      */             
/*      */ 
/*  341 */             out.write(" </td>\n</tr>\n<tr>\n<td>\n ");
/*      */             
/*  343 */             String temp1 = FormatUtil.getString((String)pageContext.findAttribute("monitortype")) + " " + unit + "     ";
/*      */             
/*  345 */             out.write(" \n\t  ");
/*      */             
/*  347 */             TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/*  348 */             _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/*  349 */             _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*      */             
/*  351 */             _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("sumgraph");
/*      */             
/*  353 */             _jspx_th_awolf_005ftimechart_005f0.setWidth("450");
/*      */             
/*  355 */             _jspx_th_awolf_005ftimechart_005f0.setHeight("height_graph");
/*      */             
/*  357 */             _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */             
/*  359 */             _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */             
/*  361 */             _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(temp1);
/*      */             
/*  363 */             _jspx_th_awolf_005ftimechart_005f0.setCustomDateAxis(true);
/*      */             
/*  365 */             _jspx_th_awolf_005ftimechart_005f0.setCustomAngle(270.0D);
/*      */             
/*  367 */             _jspx_th_awolf_005ftimechart_005f0.setMovingAverage(FormatUtil.getString("am.webclient.730attribute.legendmovingaverage.text"));
/*      */             
/*  369 */             _jspx_th_awolf_005ftimechart_005f0.setMovingAverageName(FormatUtil.getString("am.webclient.730attribute.legendhourlyaverage.text"));
/*  370 */             int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/*  371 */             if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/*  372 */               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/*  373 */                 out = _jspx_page_context.pushBody();
/*  374 */                 _jspx_th_awolf_005ftimechart_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  375 */                 _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */               }
/*      */               for (;;) {
/*  378 */                 out.write("  \n\t   ");
/*  379 */                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/*  380 */                 if (evalDoAfterBody != 2)
/*      */                   break;
/*      */               }
/*  383 */               if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/*  384 */                 out = _jspx_page_context.popBody();
/*      */               }
/*      */             }
/*  387 */             if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/*  388 */               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0);
/*      */             }
/*      */             else {
/*  391 */               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fmovingAverageName_005fmovingAverage_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0);
/*  392 */               out.write(" \n</td>\n</tr>\n</table>\n</td>\n<td width=\"20%\" valign=\"top\">\n");
/*      */               
/*  394 */               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/*  395 */               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/*  396 */               _jspx_th_html_005fform_005f0.setParent(null);
/*      */               
/*  398 */               _jspx_th_html_005fform_005f0.setAction("/showHistoryData.do?method=getDataforComparision");
/*      */               
/*  400 */               _jspx_th_html_005fform_005f0.setStyle("Display:inline");
/*  401 */               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/*  402 */               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                 for (;;) {
/*  404 */                   out.write("\n\n<table width=\"98%\" border=\"0\" align=\"left\"  valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td class=\"leftlinksheading\"  >");
/*  405 */                   out.print(FormatUtil.getString("am.webclient.historydata.period.text"));
/*  406 */                   out.write("</td>\n  </tr>\n  <tr> \n    <td class=\"yellowgrayborder\" >\n    ");
/*      */                   
/*  408 */                   SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/*  409 */                   _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/*  410 */                   _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                   
/*  412 */                   _jspx_th_html_005fselect_005f0.setProperty("period");
/*      */                   
/*  414 */                   _jspx_th_html_005fselect_005f0.setOnchange("javascript:fnPeriod(this)");
/*      */                   
/*  416 */                   _jspx_th_html_005fselect_005f0.setStyleClass("formtextselected");
/*      */                   
/*  418 */                   _jspx_th_html_005fselect_005f0.setValue(period);
/*  419 */                   int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/*  420 */                   if (_jspx_eval_html_005fselect_005f0 != 0) {
/*  421 */                     if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  422 */                       out = _jspx_page_context.pushBody();
/*  423 */                       _jspx_th_html_005fselect_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  424 */                       _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/*  427 */                       out.write("\n    ");
/*      */                       
/*  429 */                       OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  430 */                       _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/*  431 */                       _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/*  433 */                       _jspx_th_html_005foption_005f0.setKey(FormatUtil.getString("am.webclient.historydata.period.today.text"));
/*      */                       
/*  435 */                       _jspx_th_html_005foption_005f0.setValue("0");
/*  436 */                       int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/*  437 */                       if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/*  438 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                       }
/*      */                       
/*  441 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0);
/*  442 */                       out.write("\n        ");
/*      */                       
/*  444 */                       OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  445 */                       _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/*  446 */                       _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/*  448 */                       _jspx_th_html_005foption_005f1.setKey(FormatUtil.getString("am.webclient.historydata.period.yesterday.text"));
/*      */                       
/*  450 */                       _jspx_th_html_005foption_005f1.setValue("3");
/*  451 */                       int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/*  452 */                       if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/*  453 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                       }
/*      */                       
/*  456 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1);
/*  457 */                       out.write("    \n        ");
/*      */                       
/*  459 */                       OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  460 */                       _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/*  461 */                       _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/*  463 */                       _jspx_th_html_005foption_005f2.setKey(FormatUtil.getString("am.webclient.historydata.period.thisweek.text"));
/*      */                       
/*  465 */                       _jspx_th_html_005foption_005f2.setValue("6");
/*  466 */                       int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/*  467 */                       if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/*  468 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                       }
/*      */                       
/*  471 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2);
/*  472 */                       out.write("    \n        ");
/*      */                       
/*  474 */                       OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  475 */                       _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/*  476 */                       _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/*  478 */                       _jspx_th_html_005foption_005f3.setKey(FormatUtil.getString("am.webclient.historydata.period.7days.text"));
/*      */                       
/*  480 */                       _jspx_th_html_005foption_005f3.setValue("1");
/*  481 */                       int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/*  482 */                       if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/*  483 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                       }
/*      */                       
/*  486 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3);
/*  487 */                       out.write("\n          ");
/*      */                       
/*  489 */                       OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  490 */                       _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/*  491 */                       _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/*  493 */                       _jspx_th_html_005foption_005f4.setKey(FormatUtil.getString("am.webclient.historydata.period.lastweek.text"));
/*      */                       
/*  495 */                       _jspx_th_html_005foption_005f4.setValue("12");
/*  496 */                       int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/*  497 */                       if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/*  498 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                       }
/*      */                       
/*  501 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4);
/*  502 */                       out.write("   \n        ");
/*      */                       
/*  504 */                       OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  505 */                       _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/*  506 */                       _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/*  508 */                       _jspx_th_html_005foption_005f5.setKey(FormatUtil.getString("am.webclient.historydata.period.thismonth.text"));
/*      */                       
/*  510 */                       _jspx_th_html_005foption_005f5.setValue("7");
/*  511 */                       int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/*  512 */                       if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/*  513 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                       }
/*      */                       
/*  516 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5);
/*  517 */                       out.write("    \n        ");
/*      */                       
/*  519 */                       OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  520 */                       _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/*  521 */                       _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/*  523 */                       _jspx_th_html_005foption_005f6.setKey(FormatUtil.getString("am.webclient.historydata.period.30days.text"));
/*      */                       
/*  525 */                       _jspx_th_html_005foption_005f6.setValue("2");
/*  526 */                       int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/*  527 */                       if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/*  528 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                       }
/*      */                       
/*  531 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6);
/*  532 */                       out.write("\n         ");
/*      */                       
/*  534 */                       OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  535 */                       _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/*  536 */                       _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/*  538 */                       _jspx_th_html_005foption_005f7.setKey(FormatUtil.getString("am.webclient.historydata.period.lastmonth.text"));
/*      */                       
/*  540 */                       _jspx_th_html_005foption_005f7.setValue("11");
/*  541 */                       int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/*  542 */                       if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/*  543 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                       }
/*      */                       
/*  546 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7);
/*  547 */                       out.write("\n        ");
/*      */                       
/*  549 */                       OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  550 */                       _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/*  551 */                       _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/*  553 */                       _jspx_th_html_005foption_005f8.setKey(FormatUtil.getString("am.webclient.historydata.period.thisquarter.text"));
/*      */                       
/*  555 */                       _jspx_th_html_005foption_005f8.setValue("9");
/*  556 */                       int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/*  557 */                       if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/*  558 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                       }
/*      */                       
/*  561 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8);
/*  562 */                       out.write("    \n        ");
/*      */                       
/*  564 */                       OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  565 */                       _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/*  566 */                       _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/*  568 */                       _jspx_th_html_005foption_005f9.setKey(FormatUtil.getString("am.webclient.historydata.period.thisyear.text"));
/*      */                       
/*  570 */                       _jspx_th_html_005foption_005f9.setValue("8");
/*  571 */                       int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/*  572 */                       if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/*  573 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                       }
/*      */                       
/*  576 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9);
/*  577 */                       out.write("    \n         ");
/*      */                       
/*  579 */                       OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/*  580 */                       _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/*  581 */                       _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                       
/*  583 */                       _jspx_th_html_005foption_005f10.setKey(FormatUtil.getString("am.webclient.historydata.period.last1year.text"));
/*      */                       
/*  585 */                       _jspx_th_html_005foption_005f10.setValue("5");
/*  586 */                       int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/*  587 */                       if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/*  588 */                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                       }
/*      */                       
/*  591 */                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10);
/*  592 */                       out.write("\n\n    ");
/*  593 */                       int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/*  594 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*  597 */                     if (_jspx_eval_html_005fselect_005f0 != 1) {
/*  598 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/*  601 */                   if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/*  602 */                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                   }
/*      */                   
/*  605 */                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/*  606 */                   out.write("        \n  <input type=\"hidden\" name=\"resids\" value='");
/*  607 */                   out.print(request.getParameter("resids"));
/*  608 */                   out.write("'>\n  <input type=\"hidden\" name=\"resourcename\" value='");
/*  609 */                   out.print(request.getParameter("resourcename"));
/*  610 */                   out.write("'>\n  <input type=\"hidden\" name=\"attids\" value='");
/*  611 */                   out.print(request.getParameter("attids"));
/*  612 */                   out.write("'>\n    </td>\n  </tr>\n   \n  ");
/*  613 */                   if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                     return;
/*  615 */                   out.write("\n</table>\n");
/*  616 */                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/*  617 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/*  621 */               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/*  622 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*      */               }
/*      */               else {
/*  625 */                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/*  626 */                 out.write("\n\n<br>\n<br>\n\n");
/*      */                 
/*  628 */                 FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/*  629 */                 _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/*  630 */                 _jspx_th_html_005fform_005f1.setParent(null);
/*      */                 
/*  632 */                 _jspx_th_html_005fform_005f1.setAction("/showHistoryData.do?method=getDataforComparision&period=4");
/*  633 */                 int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/*  634 */                 if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                   for (;;) {
/*  636 */                     out.write(" \n<br>\n<br>\n      <table width=\"98%\" border=\"0\" align=\"left\" valign=\"top\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"22\"  class=\"leftlinksheading\" >");
/*  637 */                     out.print(FormatUtil.getString("am.webclient.historydata.period.customtime.text"));
/*  638 */                     out.write("</td>\n  </tr>\n\n  <tr>\n          <td height=\"24\"  class=\"yellowgrayborder\" > ");
/*  639 */                     out.print(FormatUtil.getString("am.webclient.historydata.period.starttime.text"));
/*  640 */                     out.write("</td>\n  </tr>\n  <tr>\n          <td height=\"40\"  class=\"whitegrayborder\" > ");
/*  641 */                     if (_jspx_meth_c_005fchoose_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                       return;
/*  643 */                     out.write(" <a href=\"javascript:void(0)\" ><IMG src=\"../../images/calendar-button.gif\" border=\"0\" id=startTrigger title=\"");
/*  644 */                     out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  645 */                     out.write("\"></a> \n            <SCRIPT type=text/javascript>\n                                            Calendar.setup({\n                                        inputField     :    \"start\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                                showsTime          :    true,\n                                        button         :    \"startTrigger\",  // trigger for the calendar (button ID)\n                                                timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                            });\n                                         </SCRIPT></td>\n  </tr>\n  <tr>\n          <td   class=\"yellowgrayborder\" >");
/*  646 */                     out.print(FormatUtil.getString("am.webclient.historydata.period.endtime.text"));
/*  647 */                     out.write("</td>\n  </tr>\n  <tr>\n          <td height=\"39\"  colspan=2 class=\"whitegrayborder\" > ");
/*  648 */                     if (_jspx_meth_c_005fchoose_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                       return;
/*  650 */                     out.write(" <a href=\"javascript:void(0)\" ><IMG src=\"../../images/calendar-button.gif\" border=\"0\" id=endTrigger title=\"");
/*  651 */                     out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/*  652 */                     out.write("\"></a> \n            <SCRIPT type=text/javascript>\n                                            Calendar.setup({\n                                        inputField     :    \"end\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                                showsTime          :    true,\n                                        button         :    \"endTrigger\",  // trigger for the calendar (button ID)\n                                                timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                            });\n                                         </SCRIPT> </td>\n  </tr>\n  <input type=\"hidden\" name=\"resids\" value='");
/*  653 */                     out.print(request.getParameter("resids"));
/*  654 */                     out.write("'>\n  <input type=\"hidden\" name=\"iscompare\" value='true'>\n  <input type=\"hidden\" name=\"attids\" value='");
/*  655 */                     out.print(request.getParameter("attids"));
/*  656 */                     out.write("'>\n  <tr>\n    <td height=\"32\" class=\"tablebottom\" > \n      <input type=\"submit\" name=\"show\" value=\"");
/*  657 */                     out.print(FormatUtil.getString("am.webclient.historydata.showreport.text"));
/*  658 */                     out.write("\" class=\"buttons\" onclick=\"return fnCheckCustomTime(this.form)\">\n     \n    </td>\n  </tr>\n</table>\n \n ");
/*  659 */                     if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                       return;
/*  661 */                     out.write(10);
/*  662 */                     int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/*  663 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/*  667 */                 if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/*  668 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f1);
/*      */                 }
/*      */                 else {
/*  671 */                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f1);
/*  672 */                   out.write("\n</td>\n</tr>\n</table>\n\n<br>\n<br>\n\n\n<table width=\"100%\" cellpadding=0 cellspacing=0 class=\"lrtbdarkborder\">\n<tr> \n    <td  class=\"columnheading\" width=\"25%\" border=\"1\"  align=\"center\">");
/*  673 */                   out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/*  674 */                   out.write("</td>\n    <td  class=\"columnheading\" width=\"25%\" border=\"1\"  align=\"center\">");
/*  675 */                   out.print(FormatUtil.getString("am.webclient.historydata.time.text"));
/*  676 */                   out.write("</td>\n    ");
/*      */                   
/*  678 */                   for (int i = 0; i < resourceids.size(); i++)
/*      */                   {
/*  680 */                     String displayname = (String)disp_name.get(i);
/*  681 */                     String temp = displayname;
/*  682 */                     if ((displayname != null) && (displayname.length() > 30))
/*      */                     {
/*  684 */                       displayname = temp.substring(0, 11) + ".. .." + temp.substring(temp.length() - 12, temp.length());
/*      */                     }
/*      */                     
/*  687 */                     out.write("\n\t\t\t<td class=\"columnheading\">");
/*  688 */                     out.print(displayname);
/*  689 */                     out.write("</td>\n\t\t");
/*      */                   }
/*      */                   
/*      */ 
/*  693 */                   out.write("\n</tr>\n<tr>\n");
/*      */                   
/*  695 */                   for (int i = 0; i < total_times.size(); i++)
/*      */                   {
/*  697 */                     String bgcolor = "";
/*  698 */                     String selectedColor = "class=\"selectedborder\"";
/*  699 */                     if (i % 2 == 0)
/*      */                     {
/*  701 */                       bgcolor = "class=\"whitegrayborderbr\"";
/*      */                     }
/*      */                     else
/*      */                     {
/*  705 */                       bgcolor = "class=\"yellowgrayborderbr\"";
/*      */                     }
/*  707 */                     long archivedTime = ((Long)total_times.get(i)).longValue();
/*  708 */                     pageContext.setAttribute("date", new java.util.Date(archivedTime));
/*      */                     
/*  710 */                     out.write("\n  <tr>\n\t<td ");
/*  711 */                     out.print(bgcolor);
/*  712 */                     out.write(" align=\"center\">");
/*  713 */                     if (_jspx_meth_fmt_005fformatDate_005f0(_jspx_page_context))
/*      */                       return;
/*  715 */                     out.write("</td>\n    <td ");
/*  716 */                     out.print(bgcolor);
/*  717 */                     out.write(" align=\"center\">");
/*  718 */                     if (_jspx_meth_fmt_005fformatDate_005f1(_jspx_page_context))
/*      */                       return;
/*  720 */                     out.write("</td>\n");
/*      */                     
/*  722 */                     for (int j = 0; j < resourceids.size(); j++)
/*      */                     {
/*  724 */                       String resid_attid = resourceids.get(j) + "-" + attributeids.get(j);
/*  725 */                       ArrayList times_new = (ArrayList)resatt_times.get(resid_attid);
/*  726 */                       ArrayList values_new = (ArrayList)resatt_values.get(resid_attid);
/*  727 */                       String value = "-";
/*  728 */                       for (int k = 0; k < times_new.size(); k++)
/*      */                       {
/*  730 */                         if (String.valueOf(times_new.get(k)).equals(String.valueOf(total_times.get(i))))
/*      */                         {
/*  732 */                           value = String.valueOf(values_new.get(k));
/*  733 */                           break;
/*      */                         }
/*  735 */                         if (k == times_new.size() - 1)
/*      */                         {
/*  737 */                           value = "-";
/*      */                         }
/*      */                       }
/*      */                       
/*  741 */                       out.write("\n<td ");
/*  742 */                       out.print(bgcolor);
/*  743 */                       out.write(" align=\"center\">");
/*  744 */                       out.print(value);
/*  745 */                       out.write("</td>\n");
/*      */                     }
/*      */                   }
/*      */                   
/*      */ 
/*  750 */                   out.write("\n</tr>\n</table>\n</body>\n</html>\n");
/*      */                 }
/*  752 */               } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  753 */         out = _jspx_out;
/*  754 */         if ((out != null) && (out.getBufferSize() != 0))
/*  755 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/*  756 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/*  759 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  765 */     PageContext pageContext = _jspx_page_context;
/*  766 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  768 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/*  769 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/*  770 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/*  772 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/*  774 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/*  775 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/*  776 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/*  777 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  778 */       return true;
/*      */     }
/*  780 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/*  781 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  786 */     PageContext pageContext = _jspx_page_context;
/*  787 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  789 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  790 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/*  791 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/*  793 */     _jspx_th_c_005fout_005f1.setValue("${STATUS}");
/*  794 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/*  795 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/*  796 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  797 */       return true;
/*      */     }
/*  799 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/*  800 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  805 */     PageContext pageContext = _jspx_page_context;
/*  806 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  808 */     org.apache.taglibs.standard.tag.el.core.SetTag _jspx_th_c_005fset_005f0 = (org.apache.taglibs.standard.tag.el.core.SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(org.apache.taglibs.standard.tag.el.core.SetTag.class);
/*  809 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/*  810 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/*  812 */     _jspx_th_c_005fset_005f0.setVar("rname");
/*  813 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/*  814 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/*  815 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/*  816 */         out = _jspx_page_context.pushBody();
/*  817 */         _jspx_th_c_005fset_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/*  818 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/*  821 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fset_005f0, _jspx_page_context))
/*  822 */           return true;
/*  823 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/*  824 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*  827 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/*  828 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/*  831 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/*  832 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  833 */       return true;
/*      */     }
/*  835 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/*  836 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  841 */     PageContext pageContext = _jspx_page_context;
/*  842 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  844 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/*  845 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/*  846 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/*  848 */     _jspx_th_c_005fout_005f2.setValue("${monitortype}");
/*  849 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/*  850 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/*  851 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  852 */       return true;
/*      */     }
/*  854 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/*  855 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  860 */     PageContext pageContext = _jspx_page_context;
/*  861 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  863 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/*  864 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/*  865 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/*  867 */     _jspx_th_html_005fhidden_005f0.setProperty("reporttype");
/*      */     
/*  869 */     _jspx_th_html_005fhidden_005f0.setValue("html");
/*  870 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/*  871 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/*  872 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/*  873 */       return true;
/*      */     }
/*  875 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/*  876 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  881 */     PageContext pageContext = _jspx_page_context;
/*  882 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  884 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/*  885 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/*  886 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*  887 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/*  888 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/*  890 */         out.write(32);
/*  891 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*  892 */           return true;
/*  893 */         out.write(32);
/*  894 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*  895 */           return true;
/*  896 */         out.write(32);
/*  897 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/*  898 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  902 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/*  903 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  904 */       return true;
/*      */     }
/*  906 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*  907 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  912 */     PageContext pageContext = _jspx_page_context;
/*  913 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  915 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/*  916 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/*  917 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/*  919 */     _jspx_th_c_005fwhen_005f0.setTest("${'1' != '1'}");
/*  920 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/*  921 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/*  923 */         out.write(" \n            ");
/*  924 */         if (_jspx_meth_html_005ftext_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*  925 */           return true;
/*  926 */         out.write(" \n            ");
/*  927 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/*  928 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  932 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/*  933 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  934 */       return true;
/*      */     }
/*  936 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/*  937 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  942 */     PageContext pageContext = _jspx_page_context;
/*  943 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  945 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/*  946 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/*  947 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/*  949 */     _jspx_th_html_005ftext_005f0.setSize("14");
/*      */     
/*  951 */     _jspx_th_html_005ftext_005f0.setProperty("startDate");
/*      */     
/*  953 */     _jspx_th_html_005ftext_005f0.setStyleId("start");
/*      */     
/*  955 */     _jspx_th_html_005ftext_005f0.setReadonly(true);
/*      */     
/*  957 */     _jspx_th_html_005ftext_005f0.setOnchange("javascript:fnSetStartTime(this.value)");
/*      */     
/*  959 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtextselected");
/*  960 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/*  961 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/*  962 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  963 */       return true;
/*      */     }
/*  965 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/*  966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  971 */     PageContext pageContext = _jspx_page_context;
/*  972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/*  974 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/*  975 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/*  976 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*  977 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/*  978 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/*  980 */         out.write(32);
/*  981 */         if (_jspx_meth_html_005ftext_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*  982 */           return true;
/*  983 */         out.write(" \n             ");
/*  984 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/*  985 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/*  989 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/*  990 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  991 */       return true;
/*      */     }
/*  993 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/*  994 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/*  999 */     PageContext pageContext = _jspx_page_context;
/* 1000 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1002 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 1003 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 1004 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 1006 */     _jspx_th_html_005ftext_005f1.setSize("13");
/*      */     
/* 1008 */     _jspx_th_html_005ftext_005f1.setProperty("startDate");
/*      */     
/* 1010 */     _jspx_th_html_005ftext_005f1.setStyleId("start");
/*      */     
/* 1012 */     _jspx_th_html_005ftext_005f1.setReadonly(true);
/*      */     
/* 1014 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:fnSetStartTime(this.value)");
/* 1015 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 1016 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 1017 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1018 */       return true;
/*      */     }
/* 1020 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 1021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1026 */     PageContext pageContext = _jspx_page_context;
/* 1027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1029 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 1030 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 1031 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/* 1032 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 1033 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 1035 */         out.write(32);
/* 1036 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 1037 */           return true;
/* 1038 */         out.write(32);
/* 1039 */         if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 1040 */           return true;
/* 1041 */         out.write(32);
/* 1042 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 1043 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1047 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 1048 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1049 */       return true;
/*      */     }
/* 1051 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 1052 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1057 */     PageContext pageContext = _jspx_page_context;
/* 1058 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1060 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 1061 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 1062 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 1064 */     _jspx_th_c_005fwhen_005f1.setTest("${'1'!= '1'}");
/* 1065 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 1066 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 1068 */         out.write(" \n            ");
/* 1069 */         if (_jspx_meth_html_005ftext_005f2(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 1070 */           return true;
/* 1071 */         out.write(" \n            ");
/* 1072 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 1073 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1077 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 1078 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1079 */       return true;
/*      */     }
/* 1081 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 1082 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1087 */     PageContext pageContext = _jspx_page_context;
/* 1088 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1090 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 1091 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 1092 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 1094 */     _jspx_th_html_005ftext_005f2.setProperty("endDate");
/*      */     
/* 1096 */     _jspx_th_html_005ftext_005f2.setSize("14");
/*      */     
/* 1098 */     _jspx_th_html_005ftext_005f2.setStyleId("end");
/*      */     
/* 1100 */     _jspx_th_html_005ftext_005f2.setReadonly(true);
/*      */     
/* 1102 */     _jspx_th_html_005ftext_005f2.setOnchange("javascript:fnSetEndTime(this.value)");
/*      */     
/* 1104 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtextselected");
/* 1105 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 1106 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 1107 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 1108 */       return true;
/*      */     }
/* 1110 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fstyleClass_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 1111 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1116 */     PageContext pageContext = _jspx_page_context;
/* 1117 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1119 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 1120 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 1121 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 1122 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 1123 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 1125 */         out.write(32);
/* 1126 */         if (_jspx_meth_html_005ftext_005f3(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/* 1127 */           return true;
/* 1128 */         out.write(" \n            ");
/* 1129 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 1130 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 1134 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 1135 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1136 */       return true;
/*      */     }
/* 1138 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 1139 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1144 */     PageContext pageContext = _jspx_page_context;
/* 1145 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1147 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 1148 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 1149 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 1151 */     _jspx_th_html_005ftext_005f3.setProperty("endDate");
/*      */     
/* 1153 */     _jspx_th_html_005ftext_005f3.setSize("13");
/*      */     
/* 1155 */     _jspx_th_html_005ftext_005f3.setStyleId("end");
/*      */     
/* 1157 */     _jspx_th_html_005ftext_005f3.setReadonly(true);
/*      */     
/* 1159 */     _jspx_th_html_005ftext_005f3.setOnchange("javascript:fnSetEndTime(this.value)");
/* 1160 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 1161 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 1162 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 1163 */       return true;
/*      */     }
/* 1165 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 1166 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1171 */     PageContext pageContext = _jspx_page_context;
/* 1172 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1174 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 1175 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 1176 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 1178 */     _jspx_th_html_005fhidden_005f1.setProperty("reporttype");
/*      */     
/* 1180 */     _jspx_th_html_005fhidden_005f1.setValue("html");
/* 1181 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 1182 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 1183 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 1184 */       return true;
/*      */     }
/* 1186 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 1187 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1192 */     PageContext pageContext = _jspx_page_context;
/* 1193 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1195 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f0 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 1196 */     _jspx_th_fmt_005fformatDate_005f0.setPageContext(_jspx_page_context);
/* 1197 */     _jspx_th_fmt_005fformatDate_005f0.setParent(null);
/*      */     
/* 1199 */     _jspx_th_fmt_005fformatDate_005f0.setValue("${date}");
/*      */     
/* 1201 */     _jspx_th_fmt_005fformatDate_005f0.setPattern("MMM d,yyyy");
/* 1202 */     int _jspx_eval_fmt_005fformatDate_005f0 = _jspx_th_fmt_005fformatDate_005f0.doStartTag();
/* 1203 */     if (_jspx_th_fmt_005fformatDate_005f0.doEndTag() == 5) {
/* 1204 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 1205 */       return true;
/*      */     }
/* 1207 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f0);
/* 1208 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fformatDate_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 1213 */     PageContext pageContext = _jspx_page_context;
/* 1214 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 1216 */     FormatDateTag _jspx_th_fmt_005fformatDate_005f1 = (FormatDateTag)this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.get(FormatDateTag.class);
/* 1217 */     _jspx_th_fmt_005fformatDate_005f1.setPageContext(_jspx_page_context);
/* 1218 */     _jspx_th_fmt_005fformatDate_005f1.setParent(null);
/*      */     
/* 1220 */     _jspx_th_fmt_005fformatDate_005f1.setValue("${date}");
/*      */     
/* 1222 */     _jspx_th_fmt_005fformatDate_005f1.setPattern("H:mm");
/* 1223 */     int _jspx_eval_fmt_005fformatDate_005f1 = _jspx_th_fmt_005fformatDate_005f1.doStartTag();
/* 1224 */     if (_jspx_th_fmt_005fformatDate_005f1.doEndTag() == 5) {
/* 1225 */       this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 1226 */       return true;
/*      */     }
/* 1228 */     this._005fjspx_005ftagPool_005ffmt_005fformatDate_0026_005fvalue_005fpattern_005fnobody.reuse(_jspx_th_fmt_005fformatDate_005f1);
/* 1229 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\reports\Popup_005fCompareReports_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */