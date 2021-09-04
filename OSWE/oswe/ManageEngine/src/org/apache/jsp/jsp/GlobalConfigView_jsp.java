/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.bean.SummaryBean;
/*     */ import com.adventnet.appmanager.reporting.form.ReportForm;
/*     */ import com.adventnet.appmanager.struts.beans.HistoryDataGraphUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.html.OptionTag;
/*     */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*     */ import org.apache.struts.taglib.html.SelectTag;
/*     */ import org.apache.struts.taglib.logic.IterateTag;
/*     */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class GlobalConfigView_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  35 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  54 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  58 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  59 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  60 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  61 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  62 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  63 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  64 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  65 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  66 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  67 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  68 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  72 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  73 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.release();
/*  74 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/*  75 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.release();
/*  76 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  77 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  78 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/*  79 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  80 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  87 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  90 */     JspWriter out = null;
/*  91 */     Object page = this;
/*  92 */     JspWriter _jspx_out = null;
/*  93 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  97 */       response.setContentType("text/html;charset=UTF-8");
/*  98 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/* 100 */       _jspx_page_context = pageContext;
/* 101 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 102 */       ServletConfig config = pageContext.getServletConfig();
/* 103 */       session = pageContext.getSession();
/* 104 */       out = pageContext.getOut();
/* 105 */       _jspx_out = out;
/*     */       
/* 107 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/appmanager.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n\n");
/* 108 */       SummaryBean sumgraph = null;
/* 109 */       sumgraph = (SummaryBean)_jspx_page_context.getAttribute("sumgraph", 2);
/* 110 */       if (sumgraph == null) {
/* 111 */         sumgraph = new SummaryBean();
/* 112 */         _jspx_page_context.setAttribute("sumgraph", sumgraph, 2);
/*     */       }
/* 114 */       out.write(10);
/* 115 */       out.write(10);
/* 116 */       HistoryDataGraphUtil graph = null;
/* 117 */       graph = (HistoryDataGraphUtil)_jspx_page_context.getAttribute("graph", 1);
/* 118 */       if (graph == null) {
/* 119 */         graph = new HistoryDataGraphUtil();
/* 120 */         _jspx_page_context.setAttribute("graph", graph, 1);
/*     */       }
/* 122 */       out.write(10);
/* 123 */       out.write(10);
/* 124 */       ReportForm FlashForm = null;
/* 125 */       FlashForm = (ReportForm)_jspx_page_context.getAttribute("FlashForm", 2);
/* 126 */       if (FlashForm == null) {
/* 127 */         FlashForm = new ReportForm();
/* 128 */         _jspx_page_context.setAttribute("FlashForm", FlashForm, 2);
/*     */       }
/* 130 */       out.write("\n\n<html>\n<head>\n\n<link href=\"/images/");
/* 131 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 133 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n\n</head>\n<body>\n");
/* 134 */       response.setContentType("text/html;charset=UTF-8");
/* 135 */       out.write(10);
/*     */       
/* 137 */       HashMap renderData = (HashMap)request.getAttribute("outputData");
/* 138 */       HashMap attrUnits = (HashMap)renderData.get("attrUnits");
/* 139 */       ArrayList attrNames = (ArrayList)renderData.get("ATTRDISPNAME");
/*     */       
/* 141 */       out.write("\t\t\n\n<div id=\"EditDiv\" style=\"display:none; padding-left:1%;\">\n\n");
/*     */       
/* 143 */       FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.get(FormTag.class);
/* 144 */       _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 145 */       _jspx_th_html_005fform_005f0.setParent(null);
/*     */       
/* 147 */       _jspx_th_html_005fform_005f0.setAction("/showConfigurationData.do");
/* 148 */       int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 149 */       if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */         for (;;) {
/* 151 */           out.write("\n<table class=\"lrtbdarkborder marg-btm\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\n  <tr> \n     <td class=\"columnheading\" colspan=\"2\">");
/* 152 */           out.print(FormatUtil.getString("am.javaruntime.editglobalview"));
/* 153 */           out.write("</td>\n </tr>\t \n  <tr class=\"bodytext\">\n            <td style=\"padding-left:6px;display:none\" width=\"25%\"  align=\"left\" class=\"label-align\">");
/* 154 */           out.print(FormatUtil.getString("am.mypage.avail.monitortypes.text"));
/* 155 */           out.write(" : </td>\n            <td width=\"75%\" align=\"left\" style=\"display:none\">\n            ");
/* 156 */           if (_jspx_meth_html_005fselect_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */             return;
/* 158 */           out.write("\n            </td>\n        </tr>\n\t\t\n\t\t\t");
/* 159 */           request.setAttribute("related_action", "relatedattributes");
/* 160 */           out.write("\n\n\t<tr class=\"bodytext\">\n\t<td width=\"100%\" align=\"left\" colspan=\"2\">&nbsp;\n\t<div id=\"attrlist\">\t\t  \n\t<br>\t\t  \n\t <table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t <tr class=\"bodytext\" valign=\"top\">\n\t<td style=\"padding-left:6px;\" width=\"25%\" class=\"label-align tdindent\">\n\t  ");
/* 161 */           out.print(FormatUtil.getString("am.javaruntime.selectmetrics"));
/* 162 */           out.write("\n\t</td>\n\t<td width=\"75%\">\n\t ");
/*     */           
/* 164 */           ArrayList related_sizelist = (ArrayList)request.getAttribute("relatedattributes");
/* 165 */           int divsizeforattribs = 300;
/* 166 */           if (related_sizelist != null)
/*     */           {
/* 168 */             if (related_sizelist.size() <= 3)
/*     */             {
/* 170 */               divsizeforattribs = 60;
/*     */             }
/* 172 */             else if (related_sizelist.size() <= 4)
/*     */             {
/* 174 */               divsizeforattribs = 80;
/*     */             }
/* 176 */             else if (related_sizelist.size() <= 6)
/*     */             {
/* 178 */               divsizeforattribs = 120;
/*     */             }
/*     */           }
/*     */           
/* 182 */           out.write("\n\t<div style=\"overflow: auto; display: block; height: ");
/* 183 */           out.print(divsizeforattribs);
/* 184 */           out.write("px;\">\n\t<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\"  >\n\t\t    ");
/*     */           
/* 186 */           boolean areRelatedAttributesPresent = false;
/*     */           
/* 188 */           out.write(10);
/* 189 */           out.write(9);
/* 190 */           out.write(9);
/*     */           
/* 192 */           ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 193 */           _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 194 */           _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_html_005fform_005f0);
/*     */           
/* 196 */           _jspx_th_c_005fforEach_005f0.setVar("attr");
/*     */           
/* 198 */           _jspx_th_c_005fforEach_005f0.setItems("${relatedattributes}");
/*     */           
/* 200 */           _jspx_th_c_005fforEach_005f0.setVarStatus("counter");
/* 201 */           int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*     */           try {
/* 203 */             int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 204 */             if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*     */               for (;;) {
/* 206 */                 out.write(10);
/* 207 */                 out.write(9);
/* 208 */                 out.write(9);
/*     */                 
/* 210 */                 areRelatedAttributesPresent = true;
/* 211 */                 ArrayList singlerow = (ArrayList)pageContext.getAttribute("attr");
/* 212 */                 String attr = (String)singlerow.get(0);
/*     */                 
/* 214 */                 out.write("\n\t\t<tr>\n\t\t<td width=\"5%\" align=\"center\" class=\"editwidget-checkbox-padding\"><input type=\"checkbox\" id=\"");
/* 215 */                 if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */                 {
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
/* 239 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 240 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                 }
/* 217 */                 out.write("\" value=\"");
/* 218 */                 if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */                 {
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
/* 239 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 240 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                 }
/* 220 */                 out.write("\" name=\"relatedAttributes\"/></td>\n\t\t<td  width=\"90%\"><span class=\"bodytext\">");
/* 221 */                 if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */                 {
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
/* 239 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 240 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                 }
/* 223 */                 if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*     */                 {
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
/* 239 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 240 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */                 }
/* 225 */                 out.write("</span></td>\n\t\t</tr>\n\t\t");
/* 226 */                 int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 227 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 231 */             if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 239 */               _jspx_th_c_005fforEach_005f0.doFinally();
/* 240 */               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*     */             }
/*     */           }
/*     */           catch (Throwable _jspx_exception)
/*     */           {
/*     */             for (;;)
/*     */             {
/* 235 */               int tmp941_940 = 0; int[] tmp941_938 = _jspx_push_body_count_c_005fforEach_005f0; int tmp943_942 = tmp941_938[tmp941_940];tmp941_938[tmp941_940] = (tmp943_942 - 1); if (tmp943_942 <= 0) break;
/* 236 */               out = _jspx_page_context.popBody(); }
/* 237 */             _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*     */           } finally {
/* 239 */             _jspx_th_c_005fforEach_005f0.doFinally();
/* 240 */             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*     */           }
/* 242 */           out.write(10);
/* 243 */           out.write(9);
/* 244 */           out.write(9);
/* 245 */           if (!areRelatedAttributesPresent) {
/* 246 */             out.write("\n\t\t<tr height=\"40px\">\n\t\t<td class=\"bodytext\">\n\t\t");
/* 247 */             out.print(FormatUtil.getString("am.javaruntime.noattributes.available"));
/* 248 */             out.write("\n\t\t</td>\n\t\t</tr>\n\t\t");
/*     */           }
/* 250 */           out.write("\n\t</table>\n\t</div>\n\t</td>\n\t</tr>\n\t</table>  \t  \n\t</div>\t\t  \n \t </td>\n \n       </tr>\n\t \n\t<tr class=\"bodytext\" >\n\t<td class=\"bodytext label-align tdindent\" style=\"padding-left:6px;padding-top:15px;\" width=\"25%\"  align=\"left\">");
/* 251 */           out.print(FormatUtil.getString("am.mypage.showmonitorsfrom.text"));
/* 252 */           out.write("</td>\n\t<td class=\"bodytext\" style=\"padding-top:15px;\" width=\"75%\" align=\"left\">\n\t\n\t     \t");
/*     */           
/* 254 */           SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 255 */           _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 256 */           _jspx_th_html_005fselect_005f1.setParent(_jspx_th_html_005fform_005f0);
/*     */           
/* 258 */           _jspx_th_html_005fselect_005f1.setProperty("selecttionType");
/*     */           
/* 260 */           _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/*     */           
/* 262 */           _jspx_th_html_005fselect_005f1.setStyle("width:206px;vertical-align:middle;");
/*     */           
/* 264 */           _jspx_th_html_005fselect_005f1.setOnchange("javascript:getResourceForSelectionType();");
/* 265 */           int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 266 */           if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 267 */             if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 268 */               out = _jspx_page_context.pushBody();
/* 269 */               _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 270 */               _jspx_th_html_005fselect_005f1.doInitBody();
/*     */             }
/*     */             for (;;) {
/* 273 */               out.write("\n\t\t\t    ");
/*     */               
/* 275 */               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 276 */               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 277 */               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f1);
/*     */               
/* 279 */               _jspx_th_html_005foption_005f0.setValue("ALL");
/* 280 */               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 281 */               if (_jspx_eval_html_005foption_005f0 != 0) {
/* 282 */                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 283 */                   out = _jspx_page_context.pushBody();
/* 284 */                   _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 285 */                   _jspx_th_html_005foption_005f0.doInitBody();
/*     */                 }
/*     */                 for (;;) {
/* 288 */                   out.print(FormatUtil.getString("am.mypage.allmonitors.text"));
/* 289 */                   out.write(32);
/* 290 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 291 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 294 */                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 295 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 298 */               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 299 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*     */               }
/*     */               
/* 302 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 303 */               out.write("\n\t\t\t    ");
/*     */               
/* 305 */               OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 306 */               _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 307 */               _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f1);
/*     */               
/* 309 */               _jspx_th_html_005foption_005f1.setValue("GROUP");
/* 310 */               int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 311 */               if (_jspx_eval_html_005foption_005f1 != 0) {
/* 312 */                 if (_jspx_eval_html_005foption_005f1 != 1) {
/* 313 */                   out = _jspx_page_context.pushBody();
/* 314 */                   _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 315 */                   _jspx_th_html_005foption_005f1.doInitBody();
/*     */                 }
/*     */                 for (;;) {
/* 318 */                   out.print(FormatUtil.getString("am.mypage.frommg.text"));
/* 319 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 320 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 323 */                 if (_jspx_eval_html_005foption_005f1 != 1) {
/* 324 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 327 */               if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 328 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*     */               }
/*     */               
/* 331 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 332 */               out.write("\n\t\t\t    <!--");
/*     */               
/* 334 */               OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 335 */               _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 336 */               _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f1);
/*     */               
/* 338 */               _jspx_th_html_005foption_005f2.setValue("CUSTOM");
/* 339 */               int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 340 */               if (_jspx_eval_html_005foption_005f2 != 0) {
/* 341 */                 if (_jspx_eval_html_005foption_005f2 != 1) {
/* 342 */                   out = _jspx_page_context.pushBody();
/* 343 */                   _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 344 */                   _jspx_th_html_005foption_005f2.doInitBody();
/*     */                 }
/*     */                 for (;;) {
/* 347 */                   out.print(FormatUtil.getString("am.mypage.customselect.text"));
/* 348 */                   int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 349 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 352 */                 if (_jspx_eval_html_005foption_005f2 != 1) {
/* 353 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 356 */               if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 357 */                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*     */               }
/*     */               
/* 360 */               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 361 */               out.write("-->\n\t\t\t ");
/* 362 */               int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 363 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 366 */             if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 367 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 370 */           if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 371 */             this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*     */           }
/*     */           
/* 374 */           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 375 */           out.write("\n \n\t</td>\n</tr>\n\n\n\n\t <tr class=\"bodytext\">\n\t\n\t<td colspan=\"2\"> \n\t <div id=\"mg\" style=\"display:none\">\n\t\t\n\t\t<table border=\"0\" width=\"100%\">\n\t\t<tr class=\"bodytext\">\n\t\t\t<td width=\"25%\" style=\"padding-top:15px;\" align=\"left\" class=\"label-align\">");
/* 376 */           out.print(FormatUtil.getString("am.reporttab.selectmg.text"));
/* 377 */           out.write(":</td>\n\t\t\t<td width=\"75%\" style=\"padding-top:15px;\" align=\"left\">\n\t\t\t\t <select name=\"selectedMG\"  class=\"formtext medium\">\n\t\t\t\t\t ");
/*     */           
/* 379 */           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 380 */           _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 381 */           _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*     */           
/* 383 */           _jspx_th_logic_005fnotEmpty_005f0.setName("applications1");
/* 384 */           int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 385 */           if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*     */             for (;;) {
/* 387 */               out.write("\n\t\t\t\t\t\t ");
/*     */               
/* 389 */               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 390 */               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 391 */               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*     */               
/* 393 */               _jspx_th_logic_005fiterate_005f0.setName("applications1");
/*     */               
/* 395 */               _jspx_th_logic_005fiterate_005f0.setId("row");
/*     */               
/* 397 */               _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*     */               
/* 399 */               _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 400 */               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 401 */               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 402 */                 ArrayList row = null;
/* 403 */                 Integer j = null;
/* 404 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 405 */                   out = _jspx_page_context.pushBody();
/* 406 */                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 407 */                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */                 }
/* 409 */                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 410 */                 j = (Integer)_jspx_page_context.findAttribute("j");
/*     */                 for (;;) {
/* 412 */                   out.write("\n\t\t\t\t\t\t\t\t  ");
/*     */                   
/* 414 */                   String selected = "";
/* 415 */                   String currentmg = (String)row.get(1);
/*     */                   
/* 417 */                   out.write("\n\t\t\t\t\t\t\t  <option value=\"");
/* 418 */                   out.print((String)row.get(1));
/* 419 */                   out.write(34);
/* 420 */                   out.write(62);
/* 421 */                   out.print(row.get(0));
/* 422 */                   out.write("</option>\n\t\t\t\t\t\t ");
/* 423 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 424 */                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 425 */                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 426 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 429 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 430 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 433 */               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 434 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*     */               }
/*     */               
/* 437 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 438 */               out.write("\n\t\t\t\t\t ");
/* 439 */               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 440 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 444 */           if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 445 */             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*     */           }
/*     */           
/* 448 */           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 449 */           out.write("\n\t\t\t\t</select>\n\t\t\t   </td>\n\t\t\t   </tr>\n\t\t   </table>\n\t\t   \n\t\t</div>\n\t</td>\n\t\n\t</tr>\n\n \n\t\t<tr>\n        <td width=\"25%\"></td>\n\t\t <td width=\"75%\" align=\"center\" class=\"align-left\" style=\"padding-top:25px;\"><input class=\"buttons btn_highlt\" type=\"button\" value=\"");
/* 450 */           out.print(FormatUtil.getString("am.webclient.historydata.showreport.text"));
/* 451 */           out.write("\" onClick=\"javascript:collectvalue()\">\n\t\t &nbsp;&nbsp;<input name=\"button5\" class=\"buttons btn_reset\" value=\"");
/* 452 */           out.print(FormatUtil.getString("am.webclient.common.reset.text"));
/* 453 */           out.write("\" type=\"reset\"/>\n\t\t&nbsp;&nbsp;<input name=\"button5\" class=\"buttons btn_link\" value=\"");
/* 454 */           out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.cancel"));
/* 455 */           out.write("\" type=\"button\" onClick=\"toggleDiv('EditDiv')\"/>\n\t\t</td>\n\t\t</tr>  \n\n\t\t<tr> \n\t\t\t<td colspan=\"2\" align=\"center\" width=\"17%\">&nbsp;</td>\n\t\t</tr>\n\t\t\n</table>\n");
/* 456 */           int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 457 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 461 */       if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 462 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */       }
/*     */       else {
/* 465 */         this._005fjspx_005ftagPool_005fhtml_005fform_0026_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 466 */         out.write("\n\n</div>\n\n\n\n\n\n\n<!--<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"60%\">\n  <td  align=\"right\" width=\"1%\"><img src=\"/images/icon_edit.gif\" border=\"0\"  hspace=\"1\" onclick=\"toggleDiv('EditDiv')\">\n  <img src=\"images/icon_pdf.gif\" border=\"0\"  hspace=\"1\" onclick=\"javascript:generateReport('gv')\">\n  </td>\n</table>-->\n\n");
/*     */         
/* 468 */         if (renderData.size() > 0)
/*     */         {
/*     */ 
/* 471 */           out.write("\n\n<table class=\"lrbtborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"98%\" align=\"center\">\n <tbody> \n  \n  <tr> \n     <td class=\"tableheadingbborder\" align=\"left\" width=\"17%\">");
/* 472 */           out.print(FormatUtil.getString("am.webclient.configurealert.monitorname"));
/* 473 */           out.write("</td>\n\t \n ");
/*     */           
/* 475 */           for (int i = 0; i < attrNames.size(); i++)
/*     */           {
/*     */ 
/* 478 */             out.write("\n     <td class=\"tableheadingbborder\" align=\"center\" width=\"14%\">");
/* 479 */             out.print(FormatUtil.getString((String)attrNames.get(i)));
/* 480 */             out.print((String)attrUnits.get(attrNames.get(i)));
/* 481 */             out.write("</td>\n ");
/*     */           }
/*     */           
/*     */ 
/* 485 */           out.write("\n   \n  </tr>\n\t\t\n\t");
/*     */           
/* 487 */           String mkey = null;
/* 488 */           Iterator it = renderData.keySet().iterator();
/* 489 */           while (it.hasNext())
/*     */           {
/* 491 */             mkey = (String)it.next();
/* 492 */             if ((!mkey.equals("ATTRDISPNAME")) && (!mkey.equals("attrUnits")))
/*     */             {
/* 494 */               Properties val = (Properties)renderData.get(mkey);
/*     */               
/* 496 */               out.write("\n\n        <tr> \n        <td class=\"sqldb-data\" align=\"left\">");
/* 497 */               out.print(val.get("DISPLAYNAME"));
/* 498 */               out.write("</td>\n ");
/*     */               
/* 500 */               for (int k = 0; k < attrNames.size(); k++) {
/* 501 */                 if (val.get(attrNames.get(k)) != null) {
/* 502 */                   String title = (String)val.get(attrNames.get(k) + "_REASON");
/* 503 */                   if ((title == null) || (title.trim().length() == 0)) {
/* 504 */                     title = "- ";
/*     */                   }
/*     */                   
/* 507 */                   out.write("\t\n       <td class=\"sqldb-data\" align=\"center\" title='Reason : ");
/* 508 */                   out.print(title);
/* 509 */                   out.write(39);
/* 510 */                   out.write(62);
/* 511 */                   out.write(32);
/* 512 */                   out.print(val.get(attrNames.get(k)));
/* 513 */                   out.write("</td>\n ");
/*     */                 } else {
/* 515 */                   out.write("    \n       <td class=\"sqldb-data\" align=\"center\">-</td>\n ");
/*     */                 }
/*     */               }
/*     */               
/*     */ 
/* 520 */               out.write("\n \n      </tr>\n\n \n");
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 525 */           out.write("\n</table>\n\n");
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 531 */           out.write("\n <table class=\"lrtbdarkborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"60%\">\n  <tr>\n  <td class=\"columnheading\">No Monitor(s) available to generate Report.<img src=\"/images/icon_edit.gif\" border=\"0\" onClick=\"toggleDiv('EditDiv')\"></td> ");
/* 532 */           out.write("\n  </tr>\n  </table>\n");
/*     */         }
/*     */         
/*     */ 
/* 536 */         out.write("\n\n\n</body>\n\n\n\n\n\n");
/*     */       }
/* 538 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 539 */         out = _jspx_out;
/* 540 */         if ((out != null) && (out.getBufferSize() != 0))
/* 541 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 542 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 545 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 551 */     PageContext pageContext = _jspx_page_context;
/* 552 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 554 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 555 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 556 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 558 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 560 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 561 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 562 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 563 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 564 */       return true;
/*     */     }
/* 566 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 567 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fselect_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 572 */     PageContext pageContext = _jspx_page_context;
/* 573 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 575 */     SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 576 */     _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 577 */     _jspx_th_html_005fselect_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 579 */     _jspx_th_html_005fselect_005f0.setProperty("selectedMonitorType");
/*     */     
/* 581 */     _jspx_th_html_005fselect_005f0.setStyleClass("formtext normal");
/*     */     
/* 583 */     _jspx_th_html_005fselect_005f0.setStyle("vertical-align:middle;");
/*     */     
/* 585 */     _jspx_th_html_005fselect_005f0.setOnchange("javascript:getAttrList(this.value)");
/* 586 */     int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 587 */     if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 588 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 589 */         out = _jspx_page_context.pushBody();
/* 590 */         _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 591 */         _jspx_th_html_005fselect_005f0.doInitBody();
/*     */       }
/*     */       for (;;) {
/* 594 */         out.write("\n            ");
/* 595 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_html_005fselect_005f0, _jspx_page_context))
/* 596 */           return true;
/* 597 */         out.write("\n            ");
/* 598 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 599 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/* 602 */       if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 603 */         out = _jspx_page_context.popBody();
/*     */       }
/*     */     }
/* 606 */     if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 607 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 608 */       return true;
/*     */     }
/* 610 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 611 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_html_005fselect_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 616 */     PageContext pageContext = _jspx_page_context;
/* 617 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 619 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.get(OptionsCollectionTag.class);
/* 620 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 621 */     _jspx_th_html_005foptionsCollection_005f0.setParent((Tag)_jspx_th_html_005fselect_005f0);
/*     */     
/* 623 */     _jspx_th_html_005foptionsCollection_005f0.setProperty("availMonitorTypes");
/* 624 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 625 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 626 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 627 */       return true;
/*     */     }
/* 629 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 630 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 635 */     PageContext pageContext = _jspx_page_context;
/* 636 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 638 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 639 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 640 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 642 */     _jspx_th_c_005fout_005f1.setValue("${attr[0]}");
/* 643 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 644 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 645 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 646 */       return true;
/*     */     }
/* 648 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 649 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 654 */     PageContext pageContext = _jspx_page_context;
/* 655 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 657 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 658 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 659 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 661 */     _jspx_th_c_005fout_005f2.setValue("${attr[0]}");
/* 662 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 663 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 664 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 665 */       return true;
/*     */     }
/* 667 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 668 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 673 */     PageContext pageContext = _jspx_page_context;
/* 674 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 676 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 677 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 678 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 680 */     _jspx_th_c_005fout_005f3.setValue("${attr[1]}");
/* 681 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 682 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 683 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 684 */       return true;
/*     */     }
/* 686 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 687 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 692 */     PageContext pageContext = _jspx_page_context;
/* 693 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 695 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 696 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 697 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 699 */     _jspx_th_c_005fout_005f4.setValue("${attr[2]}");
/* 700 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 701 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 702 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 703 */       return true;
/*     */     }
/* 705 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 706 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\GlobalConfigView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */