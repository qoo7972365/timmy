/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.tags.Truncate;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import com.adventnet.appmanager.util.SegmentReportUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
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
/*     */ import javax.servlet.jsp.tagext.BodyContent;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class HeatChart_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  29 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  42 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  46 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  50 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  54 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  55 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  56 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  63 */     HttpSession session = null;
/*     */     
/*     */ 
/*  66 */     JspWriter out = null;
/*  67 */     Object page = this;
/*  68 */     JspWriter _jspx_out = null;
/*  69 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  73 */       response.setContentType("text/html;charset=UTF-8");
/*  74 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  76 */       _jspx_page_context = pageContext;
/*  77 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  78 */       ServletConfig config = pageContext.getServletConfig();
/*  79 */       session = pageContext.getSession();
/*  80 */       out = pageContext.getOut();
/*  81 */       _jspx_out = out;
/*     */       
/*  83 */       out.write("<!--$Id$-->\n\n\n\n\n \n \n\n\n\n\n\n\n \n\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  84 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  86 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT language=JavaScript1.2 src=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT language=JavaScript1.2 src=\"/template/validation.js\"></SCRIPT>\n<script>\n\tvar userThresh=0;\n\tvar isThreshconf;\n\tvar threshC1;\n\tvar threshV1;\n\tvar threshC2;\n\tvar threshV2;\n\tvar threshC3;\n\tvar threshV3;\n\tfunction updateHeatThreshDash(period,divid,attriblist,owner,role,isThresh)\n\t{\n\t\tif((document.heatthreshForm.threshvalue1.value.length == 0) || (isPositiveInteger(document.heatthreshForm.threshvalue1.value)==false))\n                  {\n        \t\talert(\"");
/*  87 */       out.print(FormatUtil.getString("am.webclient.historydatareport.heatchart.edit.alert"));
/*  88 */       out.write("\"); \n\t\t        return false;\n                  }\n\t\t else if((document.heatthreshForm.threshvalue2.value.length == 0) || (isPositiveInteger(document.heatthreshForm.threshvalue2.value)==false))\n\t\t {\n\t\t\talert(\"");
/*  89 */       out.print(FormatUtil.getString("am.webclient.historydatareport.heatchart.edit.alert"));
/*  90 */       out.write("\");\n\t\t }\n\t\t else if((document.heatthreshForm.threshvalue3.value.length == 0) || (isPositiveInteger(document.heatthreshForm.threshvalue3.value)==false))\n\t\t{\n\t\t\talert(\"");
/*  91 */       out.print(FormatUtil.getString("am.webclient.historydatareport.heatchart.edit.alert"));
/*  92 */       out.write("\");\n\n\t\t}\n\t\telse\n\t\t{\n\t\tvar d = new Date();\n\t\tvar url;\n\t\tuserThresh =1;\n\t\tisThreshconf = isThresh;\n\t\tthreshC1 = document.heatthreshForm.threshcondn1.value;\n\t\tthreshV1 = document.heatthreshForm.threshvalue1.value;\n\t\tthreshC2 = document.heatthreshForm.threshcondn2.value;\n\t\tthreshV2 = document.heatthreshForm.threshvalue2.value;\n\t\tthreshC3 = document.heatthreshForm.threshcondn3.value;\n\t\tthreshV3 = document.heatthreshForm.threshvalue3.value;\n\t\t\n\t\turl =  \"/jsp/HeatChart.jsp?period=\"+period+\"&divname=\"+divid+\"&attributes=\"+attriblist+\"&owner=\"+owner+\"&role=\"+role+\"&date=\"+d+\"&userThresh=\"+userThresh+\"&isThresh=\"+isThreshconf+\"&threshC1=\"+threshC1+\"&threshV1=\"+threshV1+\"&threshC2=\"+threshC2+\"&threshV2=\"+threshV2+\"&threshC3=\"+threshC3+\"&threshV3=\"+threshV3;\n\t\t\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = new Function('getHeatresp(\\''+divid+'\\',http.responseText);');\n        \thttp.send(null); \n\t\t}\n\t}\n</script>\n\n");
/*     */       
/*  94 */       String attrib = new String();
/*  95 */       String per = new String();
/*  96 */       ArrayList list = new ArrayList();
/*     */       
/*  98 */       String divname = request.getParameter("divname");
/*  99 */       Hashtable thresholds = new Hashtable();
/* 100 */       String attributelist = new String();
/* 101 */       String owner = new String();
/* 102 */       String role = new String();
/* 103 */       if (divname != null)
/*     */       {
/* 105 */         attributelist = request.getParameter("attributes");
/* 106 */         owner = request.getParameter("owner");
/* 107 */         role = request.getParameter("role");
/* 108 */         per = request.getParameter("period");
/* 109 */         int isUserThresh = 0;
/* 110 */         boolean isThreshEnabled = false;
/* 111 */         String dashthCndn1 = "";
/* 112 */         String dashthValue1 = "";
/* 113 */         String dashthCndn2 = "";
/* 114 */         String dashthValue2 = "";
/* 115 */         String dashthCndn3 = "";
/* 116 */         String dashthValue3 = "";
/* 117 */         if ((request.getParameter("userThresh") != null) && (Integer.parseInt(request.getParameter("userThresh")) == 1))
/*     */         {
/* 119 */           isUserThresh = Integer.parseInt(request.getParameter("userThresh"));
/* 120 */           isThreshEnabled = Boolean.valueOf(request.getParameter("isThresh")).booleanValue();
/* 121 */           dashthCndn1 = request.getParameter("threshC1");
/* 122 */           dashthValue1 = request.getParameter("threshV1");
/* 123 */           dashthCndn2 = request.getParameter("threshC2");
/* 124 */           dashthValue2 = request.getParameter("threshV2");
/* 125 */           dashthCndn3 = request.getParameter("threshC3");
/* 126 */           dashthValue3 = request.getParameter("threshV3");
/*     */         }
/* 128 */         Hashtable dashDataHash = null;
/* 129 */         if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition())
/*     */         {
/* 131 */           dashDataHash = SegmentReportUtil.getHeatForDashboard(attributelist, owner, role, per, isUserThresh, isThreshEnabled, dashthCndn1, dashthValue1, dashthCndn2, dashthValue2, dashthCndn3, dashthValue3, request);
/*     */         }
/*     */         else
/*     */         {
/* 135 */           dashDataHash = SegmentReportUtil.getHeatForDashboard(attributelist, owner, role, per, isUserThresh, isThreshEnabled, dashthCndn1, dashthValue1, dashthCndn2, dashthValue2, dashthCndn3, dashthValue3);
/*     */         }
/*     */         
/* 138 */         attrib = (String)dashDataHash.get("monitortype");
/* 139 */         list = (ArrayList)dashDataHash.get("heatdata");
/*     */         
/* 141 */         thresholds = (Hashtable)dashDataHash.get("thresholdlegend");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 146 */         attrib = (String)request.getAttribute("monitortype");
/* 147 */         list = (ArrayList)request.getAttribute("heatdata");
/* 148 */         thresholds = (Hashtable)request.getAttribute("thresholdlegend");
/* 149 */         per = (String)request.getAttribute("period");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 156 */       boolean isthreconfd = ((Boolean)thresholds.get("isthreshcnfgd")).booleanValue();
/*     */       
/* 158 */       ArrayList timestamp = SegmentReportUtil.getTimeStamps(per);
/*     */       
/*     */ 
/* 161 */       if (divname != null)
/*     */       {
/*     */ 
/* 164 */         out.write("\n\t\t\t\t<div id='dashHeat'>\n\t\t");
/*     */       }
/*     */       
/*     */ 
/* 168 */       out.write("\n<br>\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\">\n   <tr> \n        <td  width=\"100%\"  class=\"tableheadingbborder\" >\n        ");
/* 169 */       out.print(FormatUtil.getString("am.webclient.historydatareport.heatchart.heading.text", new String[] { FormatUtil.getString(attrib) }));
/* 170 */       out.write("\n        </td>\n        <td align=\"right\" class=\"tableheadingbborder\">\n\t\t");
/*     */       
/* 172 */       if (divname != null)
/*     */       {
/*     */ 
/* 175 */         out.write("\n\t           <SELECT name=\"healthperiod\" onchange=\"fnHeatPeriod(this.value,'");
/* 176 */         out.print(divname);
/* 177 */         out.write(39);
/* 178 */         out.write(44);
/* 179 */         out.write(39);
/* 180 */         out.print(attributelist);
/* 181 */         out.write(39);
/* 182 */         out.write(44);
/* 183 */         out.write(39);
/* 184 */         out.print(owner);
/* 185 */         out.write(39);
/* 186 */         out.write(44);
/* 187 */         out.write(39);
/* 188 */         out.print(role);
/* 189 */         out.write("')\" class=\"formtext\">\n\t                  <OPTION value=\"1\" ");
/* 190 */         if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */           return;
/* 192 */         out.write(32);
/* 193 */         out.write(62);
/* 194 */         out.print(FormatUtil.getString("am.webclient.period.last24hours"));
/* 195 */         out.write("  </OPTION> \n\t\t          <OPTION value=\"2\" ");
/* 196 */         if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*     */           return;
/* 198 */         out.write(32);
/* 199 */         out.write(62);
/* 200 */         out.print(FormatUtil.getString("am.webclient.period.last30days"));
/* 201 */         out.write("</OPTION> \n\t\t\t  <OPTION value=\"3\" ");
/* 202 */         if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*     */           return;
/* 204 */         out.write(32);
/* 205 */         out.write(62);
/* 206 */         out.print(FormatUtil.getString("am.webclient.period.last12month"));
/* 207 */         out.write("</OPTION> \n\t           </SELECT>\n\t\t");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 213 */         out.write("\n\t\t\t<SELECT name=\"healthperiod\" onchange=\"fnPer(this)\" class=\"formtext\">\n\t                  <OPTION value=\"1\" ");
/* 214 */         if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*     */           return;
/* 216 */         out.write(32);
/* 217 */         out.write(62);
/* 218 */         out.print(FormatUtil.getString("am.webclient.period.last24hours"));
/* 219 */         out.write("  </OPTION> \n\t\t          <OPTION value=\"2\" ");
/* 220 */         if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
/*     */           return;
/* 222 */         out.write(32);
/* 223 */         out.write(62);
/* 224 */         out.print(FormatUtil.getString("am.webclient.period.last30days"));
/* 225 */         out.write("</OPTION> \n\t\t\t  <OPTION value=\"3\" ");
/* 226 */         if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*     */           return;
/* 228 */         out.write(32);
/* 229 */         out.write(62);
/* 230 */         out.print(FormatUtil.getString("am.webclient.period.last12month"));
/* 231 */         out.write("</OPTION> \n\t           \t</SELECT>\n\t\t");
/*     */       }
/*     */       
/*     */ 
/* 235 */       out.write("\n        </td>\n   </tr>\n  <tr>\n <td colspan=\"2\">\n\t<table width='100%' border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t<td align=\"left\"  class=\"columnheading\" width=\"30px\" align=\"left\" valign=\"middle\" >\n\t\t<table width=\"120px\" cellpadding=\"0\" cellspacing=\"0\"  align=\"left\">\n        \t<tr>\n        \t<td  width=\"30\" style=\"font-size:11px; font-family:Arial, Helvetica, sans-serif;\" >");
/* 236 */       out.print(FormatUtil.getString("Name"));
/* 237 */       out.write("</td>\n    \t\t<td width=\"12\"><img src=\"../images/img_dboard_heading1.gif\" /></td>\n        \t<td style=\"font-size:11px; font-family:Arial, Helvetica, sans-serif;\"  width=\"60\" align=\"right\">\n\t\t");
/*     */       
/* 239 */       IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 240 */       _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 241 */       _jspx_th_c_005fif_005f6.setParent(null);
/*     */       
/* 243 */       _jspx_th_c_005fif_005f6.setTest("${param.period == 1}");
/* 244 */       int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 245 */       if (_jspx_eval_c_005fif_005f6 != 0) {
/*     */         for (;;) {
/* 247 */           out.write(10);
/* 248 */           out.write(9);
/* 249 */           out.write(9);
/* 250 */           out.print(FormatUtil.getString("am.webclient.historydatareport.heatchart.hour.text"));
/* 251 */           out.write(10);
/* 252 */           out.write(9);
/* 253 */           out.write(9);
/* 254 */           int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 255 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 259 */       if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 260 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/*     */       }
/*     */       else {
/* 263 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 264 */         out.write(10);
/* 265 */         out.write(9);
/* 266 */         out.write(9);
/*     */         
/* 268 */         IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 269 */         _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 270 */         _jspx_th_c_005fif_005f7.setParent(null);
/*     */         
/* 272 */         _jspx_th_c_005fif_005f7.setTest("${param.period == 2}");
/* 273 */         int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 274 */         if (_jspx_eval_c_005fif_005f7 != 0) {
/*     */           for (;;) {
/* 276 */             out.write(10);
/* 277 */             out.write(9);
/* 278 */             out.write(9);
/* 279 */             out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 280 */             out.write(10);
/* 281 */             out.write(9);
/* 282 */             out.write(9);
/* 283 */             int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 284 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 288 */         if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 289 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/*     */         }
/*     */         else {
/* 292 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 293 */           out.write(10);
/* 294 */           out.write(9);
/* 295 */           out.write(9);
/*     */           
/* 297 */           IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 298 */           _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 299 */           _jspx_th_c_005fif_005f8.setParent(null);
/*     */           
/* 301 */           _jspx_th_c_005fif_005f8.setTest("${param.period == 3}");
/* 302 */           int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 303 */           if (_jspx_eval_c_005fif_005f8 != 0) {
/*     */             for (;;) {
/* 305 */               out.write(10);
/* 306 */               out.write(9);
/* 307 */               out.write(9);
/* 308 */               out.print(FormatUtil.getString("am.webclient.schedulemail.month.text"));
/* 309 */               out.write(10);
/* 310 */               out.write(9);
/* 311 */               out.write(9);
/* 312 */               int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 313 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 317 */           if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 318 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/*     */           }
/*     */           else {
/* 321 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 322 */             out.write("\n\t\t&nbsp;</td>\n        \t<td width=\"5\" align=\"right\"><img src=\"../images/img_dboard_heading2.gif\" /></td>\n        \t</tr>\n\t\t</table>\n\t</td>\n\t");
/*     */             
/*     */ 
/* 325 */             for (int i = 0; i < timestamp.size(); i++)
/*     */             {
/*     */ 
/* 328 */               out.write("\n\t <td align=\"center\"  class=\"heat-time\" height=\"21px\" >\n\t\t");
/*     */               
/* 330 */               String temp = (String)timestamp.get(i);
/* 331 */               out.println(temp.substring(0, temp.indexOf("|")));
/*     */               
/* 333 */               out.write("\n\t</td>\n\t");
/*     */             }
/*     */             
/*     */ 
/* 337 */             out.write("\n\t</tr>\n\t<tr  >\n\t");
/*     */             
/* 339 */             for (int n = 0; n < list.size(); n++)
/*     */             {
/*     */ 
/* 342 */               Hashtable heathash = (Hashtable)list.get(n);
/* 343 */               String mo = (String)heathash.get("MO");
/*     */               
/*     */ 
/*     */ 
/* 347 */               ArrayList heat = (ArrayList)heathash.get("Heat");
/* 348 */               ArrayList time = new ArrayList();
/* 349 */               ArrayList values = new ArrayList();
/* 350 */               for (int i = 0; i < heat.size(); i++)
/*     */               {
/* 352 */                 Hashtable heatdata = (Hashtable)heat.get(i);
/* 353 */                 if (!time.contains((String)heatdata.get("Time"))) {
/* 354 */                   time.add((String)heatdata.get("Time"));
/* 355 */                   values.add((Integer)heatdata.get("Data"));
/*     */                 }
/*     */               }
/* 358 */               if (mo == null)
/*     */               {
/*     */ 
/* 361 */                 out.write("\n\t\t<td  height=\"30px\" align=\"center\" valign=\"middle\" class=\"bodytextbold\">\n\t\t&nbsp;\n\t\t");
/* 362 */                 out.print(FormatUtil.getString("am.webclient.historydatareport.heatchart.nodata.text"));
/* 363 */                 out.write("\n\t\t</td>\t\n\t\t");
/*     */ 
/*     */               }
/*     */               else
/*     */               {
/* 368 */                 if ((divname != null) || (n != 0))
/*     */                 {
/*     */ 
/* 371 */                   out.write("\n\t<td align=\"left\" class=\"heat_moname\"  title=\"");
/* 372 */                   out.print(mo);
/* 373 */                   out.write("\">\n\t\t\n\t\t");
/*     */                   
/* 375 */                   Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 376 */                   _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 377 */                   _jspx_th_am_005fTruncate_005f0.setParent(null);
/*     */                   
/* 379 */                   _jspx_th_am_005fTruncate_005f0.setLength(27);
/*     */                   
/* 381 */                   _jspx_th_am_005fTruncate_005f0.setTooltip("false");
/* 382 */                   int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 383 */                   if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 384 */                     if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 385 */                       out = _jspx_page_context.pushBody();
/* 386 */                       _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 387 */                       _jspx_th_am_005fTruncate_005f0.doInitBody();
/*     */                     }
/*     */                     for (;;) {
/* 390 */                       out.print(mo);
/* 391 */                       int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 392 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/* 395 */                     if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 396 */                       out = _jspx_page_context.popBody();
/*     */                     }
/*     */                   }
/* 399 */                   if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 400 */                     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0); return;
/*     */                   }
/*     */                   
/* 403 */                   this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 404 */                   out.write("\n\t</td>\n\t\n\t");
/*     */ 
/*     */                 }
/* 407 */                 else if (n == 0)
/*     */                 {
/*     */ 
/* 410 */                   out.write("\n\t\t<td align=\"left\" class=\"selectedborder\" title=\"");
/* 411 */                   out.print(mo);
/* 412 */                   out.write("\">\n\t\t\n\t\t");
/*     */                   
/* 414 */                   Truncate _jspx_th_am_005fTruncate_005f1 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.get(Truncate.class);
/* 415 */                   _jspx_th_am_005fTruncate_005f1.setPageContext(_jspx_page_context);
/* 416 */                   _jspx_th_am_005fTruncate_005f1.setParent(null);
/*     */                   
/* 418 */                   _jspx_th_am_005fTruncate_005f1.setLength(27);
/*     */                   
/* 420 */                   _jspx_th_am_005fTruncate_005f1.setTooltip("false");
/* 421 */                   int _jspx_eval_am_005fTruncate_005f1 = _jspx_th_am_005fTruncate_005f1.doStartTag();
/* 422 */                   if (_jspx_eval_am_005fTruncate_005f1 != 0) {
/* 423 */                     if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 424 */                       out = _jspx_page_context.pushBody();
/* 425 */                       _jspx_th_am_005fTruncate_005f1.setBodyContent((BodyContent)out);
/* 426 */                       _jspx_th_am_005fTruncate_005f1.doInitBody();
/*     */                     }
/*     */                     for (;;) {
/* 429 */                       out.print(mo);
/* 430 */                       int evalDoAfterBody = _jspx_th_am_005fTruncate_005f1.doAfterBody();
/* 431 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/* 434 */                     if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 435 */                       out = _jspx_page_context.popBody();
/*     */                     }
/*     */                   }
/* 438 */                   if (_jspx_th_am_005fTruncate_005f1.doEndTag() == 5) {
/* 439 */                     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f1); return;
/*     */                   }
/*     */                   
/* 442 */                   this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005ftooltip_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 443 */                   out.write("\n\t\t</td>\n\t");
/*     */                 }
/*     */                 
/* 446 */                 int i = 0;
/* 447 */                 for (int k = 0; k < timestamp.size(); k++)
/*     */                 {
/* 449 */                   String classname = new String();
/* 450 */                   String temp = (String)timestamp.get(k);
/* 451 */                   if ((i < time.size()) && (time.get(i) != null) && (((String)time.get(i)).equals(temp.substring(0, temp.indexOf("|")))))
/*     */                   {
/* 453 */                     if (((Integer)values.get(i)).intValue() == 1)
/*     */                     {
/*     */ 
/* 456 */                       classname = "heat_critical";
/*     */                     }
/* 458 */                     else if (((Integer)values.get(i)).intValue() == 4)
/*     */                     {
/*     */ 
/* 461 */                       classname = "heat_warning";
/*     */                     }
/* 463 */                     else if (((Integer)values.get(i)).intValue() == 5)
/*     */                     {
/*     */ 
/* 466 */                       classname = "heat_clear1";
/*     */                     }
/* 468 */                     else if (((Integer)values.get(i)).intValue() == 2)
/*     */                     {
/*     */ 
/* 471 */                       classname = "heat_clear2";
/*     */                     }
/* 473 */                     else if (((Integer)values.get(i)).intValue() == 3)
/*     */                     {
/*     */ 
/* 476 */                       classname = "heat_clear3";
/*     */ 
/*     */                     }
/*     */                     else
/*     */                     {
/* 481 */                       classname = "heat_unknown";
/*     */                     }
/* 483 */                     i++;
/*     */ 
/*     */                   }
/*     */                   else
/*     */                   {
/* 488 */                     classname = "heat_unknown";
/*     */                   }
/*     */                   
/* 491 */                   out.write("\n\t <td align=\"center\" title=\"");
/* 492 */                   out.print(mo + " - " + temp.substring(temp.indexOf("|") + 1, temp.length()));
/* 493 */                   out.write("\" class='");
/* 494 */                   out.print(classname);
/* 495 */                   out.write("' >\n\t\t<img src=\"/images/spacer.gif\"  height=\"21px\"   >\n\t</td>\n\t");
/*     */                 }
/*     */               }
/*     */               
/*     */ 
/*     */ 
/* 501 */               out.write("\n\t\n\t</tr>\n\t");
/*     */             }
/*     */             
/*     */ 
/* 505 */             out.write("\n\t</table>\n</td>\n</tr>\n<tr class=\"tablebottom\" rowspan='2' >\n<td  colspan=\"2\" class=\"bodytext\" width='100%'>\n     <div id='showThreshold' style='white-space: nowrap; align:left;'><span class=\"footer\" align=\"left\" >\n\t");
/* 506 */             out.print(FormatUtil.getString("am.webclient.historydatareport.heatchart.help.text"));
/* 507 */             out.write("&nbsp; \n\t<img border=\"0\" width=\"40px\" height=\"21px\" src=\"/images/spacer.gif\" hspace=\"1\" ></span>\n\t\n\n\t\n \t<img border=\"0\" width=\"31px\" height=\"21px\" src=\"/images/heat_green1.gif\" hspace=\"1\" class=\"heat\" style=\"vertical-align:middle\">\n\t");
/* 508 */             out.print(FormatUtil.getString(((String)thresholds.get("clear1")).replace("#", " ")));
/* 509 */             out.write("\n\t&nbsp;\n\t");
/*     */             
/* 511 */             if (!isthreconfd)
/*     */             {
/*     */ 
/* 514 */               out.write("\n\t<img border=\"0\" width=\"31px\" height=\"21px\" src=\"/images/heat_green2.gif\" hspace=\"1\" class=\"heat\" style=\"vertical-align:middle\">\n\t");
/* 515 */               out.print(FormatUtil.getString(((String)thresholds.get("clear2")).replace("#", " ")));
/* 516 */               out.write("\n\t&nbsp;\n\t<img border=\"0\" width=\"31px\" height=\"21px\" src=\"/images/heat_green3.gif\" hspace=\"1\" class=\"heat\" style=\"vertical-align:middle\">\n\t");
/* 517 */               out.print(FormatUtil.getString(((String)thresholds.get("clear3")).replace("#", " ")));
/* 518 */               out.write("\n\t&nbsp;\n\t");
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/*     */ 
/* 524 */               out.write("\n\t<img border=\"0\" width=\"31px\" height=\"21px\" src=\"/images/heat_orange.gif\" hspace=\"1\" class=\"heat\" style=\"vertical-align:middle\">\n\t");
/* 525 */               out.print(FormatUtil.getString(((String)thresholds.get("warning")).replace("#", " ")));
/* 526 */               out.write("&nbsp;\n\t<img border=\"0\" width=\"31px\" height=\"21px\" src=\"/images/heat_red.gif\" hspace=\"1\" class=\"heat\" style=\"vertical-align:middle\">\n\t");
/* 527 */               out.print(FormatUtil.getString(((String)thresholds.get("critical")).replace("#", " ")));
/* 528 */               out.write("&nbsp;\n\t");
/*     */             }
/*     */             
/*     */ 
/* 532 */             out.write("\n\t<img border=\"0\" width=\"31px\" height=\"21px\" src=\"/images/heat_gray.gif\" hspace=\"1\" class=\"heat\" style=\"vertical-align:middle\">\n\t");
/* 533 */             out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown"));
/* 534 */             out.write("&nbsp;\n\t<input name=\"button\" class=\"buttons\" style=\"vertical-align:middle;\" value=\"");
/* 535 */             out.print(FormatUtil.getString("am.webclient.threshold.editview"));
/* 536 */             out.write("\" onclick=\"editHeatThresh()\" type=\"button\">\n      </div>\n      <div id=\"editThreshold\" align=\"center\"  style=\"DISPLAY: none;\">\n\t<form name='heatthreshForm'>\n\t\t<table>\n\t\t\t<tr align=\"center\">\n\t\t\t\t<td align=\"left\" class=\"bodytext\" >\n\t\t\t\t\t<img border=\"0\" width=\"31px\" height=\"21px\" src=\"/images/heat_green1.gif\" hspace=\"1\" class=\"heat\" style=\"vertical-align:middle\">&nbsp;\n\t\t\t\t</td>\n\t\t\t\t<td align=\"left\" class=\"bodytext\" >\n\t\t\t\t\t");
/*     */             
/* 538 */             String select1 = "";
/* 539 */             String select2 = "";
/* 540 */             String select3 = "";
/* 541 */             String select4 = "";
/* 542 */             String select5 = "";
/* 543 */             String select6 = "";
/* 544 */             String th1 = (String)thresholds.get("clear1");
/* 545 */             int th1_pos = ((String)thresholds.get("clear1")).indexOf("#");
/* 546 */             if (th1.substring(0, th1_pos).equals("< ")) {
/* 547 */               select1 = "selected";
/* 548 */             } else if (th1.substring(0, th1_pos).equals("> ")) {
/* 549 */               select2 = "selected";
/* 550 */             } else if (th1.substring(0, th1_pos).equals("= ")) {
/* 551 */               select3 = "selected";
/* 552 */             } else if (th1.substring(0, th1_pos).equals("!= ")) {
/* 553 */               select4 = "selected";
/* 554 */             } else if (th1.substring(0, th1_pos).equals("<= ")) {
/* 555 */               select5 = "selected";
/* 556 */             } else if (th1.substring(0, th1_pos).equals(">= ")) {
/* 557 */               select6 = "selected";
/*     */             }
/* 559 */             out.write("\n\t\t\t\t\t<select name=\"threshcondn1\" class=\"formtext\">\n\t\t\t\t\t\t<option value=\"LT\" ");
/* 560 */             out.print(select1);
/* 561 */             out.write(">&lt;</option>\n\t\t\t\t\t\t<option value=\"GT\" ");
/* 562 */             out.print(select2);
/* 563 */             out.write(">&gt;</option>\n\t\t\t\t\t\t<option value=\"EQ\" ");
/* 564 */             out.print(select3);
/* 565 */             out.write(">=</option>\n\t\t\t\t\t\t<option value=\"NE\" ");
/* 566 */             out.print(select4);
/* 567 */             out.write(">!=</option>\n\t\t\t\t\t\t<option value=\"LE\" ");
/* 568 */             out.print(select5);
/* 569 */             out.write(">&lt;=</option>\n\t\t\t\t\t\t<option value=\"GE\" ");
/* 570 */             out.print(select6);
/* 571 */             out.write(">&gt;=</option>\n\t\t\t\t\t</select>\n\t\t\t\t\t&nbsp;\n\t\t\t\t\t<input type=\"text\" name=\"threshvalue1\" value=\"");
/* 572 */             out.print(th1.substring(th1_pos + 1));
/* 573 */             out.write("\" styleClass=\"formtext\" maxlength=\"15\" size=\"5\">\n\t\t\t\t</td>\n\t\t\t");
/*     */             
/* 575 */             if (!isthreconfd)
/*     */             {
/* 577 */               select1 = "";
/* 578 */               select2 = "";
/* 579 */               select3 = "";
/* 580 */               select4 = "";
/* 581 */               select5 = "";
/* 582 */               select6 = "";
/* 583 */               String th2 = (String)thresholds.get("clear2");
/* 584 */               int th2_pos = ((String)thresholds.get("clear2")).indexOf("#");
/* 585 */               if (th2.substring(0, th1_pos).equals("< ")) {
/* 586 */                 select1 = "selected";
/* 587 */               } else if (th2.substring(0, th2_pos).equals("> ")) {
/* 588 */                 select2 = "selected";
/* 589 */               } else if (th2.substring(0, th2_pos).equals("= ")) {
/* 590 */                 select3 = "selected";
/* 591 */               } else if (th2.substring(0, th2_pos).equals("!= ")) {
/* 592 */                 select4 = "selected";
/* 593 */               } else if (th2.substring(0, th2_pos).equals("<= ")) {
/* 594 */                 select5 = "selected";
/* 595 */               } else if (th2.substring(0, th2_pos).equals(">= ")) {
/* 596 */                 select6 = "selected";
/*     */               }
/* 598 */               out.write("\n\t\t\t\n\t\t\t\t<td align=\"left\" class=\"bodytext\" >\n\t\t\t\t\t<img border=\"0\" width=\"31px\" height=\"21px\" src=\"/images/heat_green2.gif\" hspace=\"1\" class=\"heat\" style=\"vertical-align:middle\">&nbsp;\n\t\t\t\t</td>\n\t\t\t\t<td align=\"left\" class=\"bodytext\" >\n\t\t\t\t\t<select name=\"threshcondn2\" class=\"formtext\">\n\t\t\t\t\t\t<option value=\"LT\" ");
/* 599 */               out.print(select1);
/* 600 */               out.write(">&lt;</option>\n\t\t\t\t\t\t<option value=\"GT\" ");
/* 601 */               out.print(select2);
/* 602 */               out.write(">&gt;</option>\n\t\t\t\t\t\t<option value=\"EQ\" ");
/* 603 */               out.print(select3);
/* 604 */               out.write(">=</option>\n\t\t\t\t\t\t<option value=\"NE\" ");
/* 605 */               out.print(select4);
/* 606 */               out.write(">!=</option>\n\t\t\t\t\t\t<option value=\"LE\" ");
/* 607 */               out.print(select5);
/* 608 */               out.write(">&lt;=</option>\n\t\t\t\t\t\t<option value=\"GE\" ");
/* 609 */               out.print(select6);
/* 610 */               out.write(">&gt;=</option>\n\t\t\t\t\t</select>\n\t\t\t\t\t&nbsp;\n\t\t\t\t\t<input type=\"text\" name=\"threshvalue2\" value=\"");
/* 611 */               out.print(th2.substring(th2_pos + 1));
/* 612 */               out.write("\" styleClass=\"formtext\" maxlength=\"15\" size=\"5\">\n\t\t\t\t</td>\n\t\t\t\n\t\t\t\t<td align=\"left\" class=\"bodytext\" >\n\t\t\t\t\t<img border=\"0\" width=\"31px\" height=\"21px\" src=\"/images/heat_green3.gif\" hspace=\"1\" class=\"heat\" style=\"vertical-align:middle\">&nbsp;\n\t\t\t\t</td>\n\t\t\t\t<td align=\"left\" class=\"bodytext\" >\n\t\t\t\t\t");
/*     */               
/*     */ 
/* 615 */               select1 = "";
/* 616 */               select2 = "";
/* 617 */               select3 = "";
/* 618 */               select4 = "";
/* 619 */               select5 = "";
/* 620 */               select6 = "";
/* 621 */               String th3 = (String)thresholds.get("clear3");
/* 622 */               int th3_pos = ((String)thresholds.get("clear3")).indexOf("#");
/* 623 */               if (th3.substring(0, th1_pos).equals("< ")) {
/* 624 */                 select1 = "selected";
/* 625 */               } else if (th3.substring(0, th3_pos).equals("> ")) {
/* 626 */                 select2 = "selected";
/* 627 */               } else if (th3.substring(0, th3_pos).equals("= ")) {
/* 628 */                 select3 = "selected";
/* 629 */               } else if (th3.substring(0, th3_pos).equals("!= ")) {
/* 630 */                 select4 = "selected";
/* 631 */               } else if (th3.substring(0, th3_pos).equals("<= ")) {
/* 632 */                 select5 = "selected";
/* 633 */               } else if (th3.substring(0, th3_pos).equals(">= ")) {
/* 634 */                 select6 = "selected";
/*     */               }
/* 636 */               out.write("\t\n\t\t\t\t\t<select name=\"threshcondn3\" class=\"formtext\">\n\t\t\t\t\t\t<option value=\"LT\" ");
/* 637 */               out.print(select1);
/* 638 */               out.write(">&lt;</option>\n\t\t\t\t\t\t<option value=\"GT\" ");
/* 639 */               out.print(select2);
/* 640 */               out.write(">&gt;</option>\n\t\t\t\t\t\t<option value=\"EQ\" ");
/* 641 */               out.print(select3);
/* 642 */               out.write(">=</option>\n\t\t\t\t\t\t<option value=\"NE\" ");
/* 643 */               out.print(select4);
/* 644 */               out.write(">!=</option>\n\t\t\t\t\t\t<option value=\"LE\" ");
/* 645 */               out.print(select5);
/* 646 */               out.write(">&lt;=</option>\n\t\t\t\t\t\t<option value=\"GE\" ");
/* 647 */               out.print(select6);
/* 648 */               out.write(">&gt;=</option>\n\t\t\t\t\t</select>\n\t\t\t\t\t&nbsp;\n\t\t\t\t\t<input type=\"text\" name=\"threshvalue3\" value=\"");
/* 649 */               out.print(th3.substring(th3_pos + 1));
/* 650 */               out.write("\" styleClass=\"formtext\" maxlength=\"15\" size=\"5\">\n\t\t\t\t</td>\n\t\t\t\n\t\t\t");
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/* 655 */               select1 = "";
/* 656 */               select2 = "";
/* 657 */               select3 = "";
/* 658 */               select4 = "";
/* 659 */               select5 = "";
/* 660 */               select6 = "";
/* 661 */               String th2 = (String)thresholds.get("warning");
/* 662 */               int th2_pos = ((String)thresholds.get("warning")).indexOf("#");
/* 663 */               if (th2.substring(0, th1_pos).equals("< ")) {
/* 664 */                 select1 = "selected";
/* 665 */               } else if (th2.substring(0, th2_pos).equals("> ")) {
/* 666 */                 select2 = "selected";
/* 667 */               } else if (th2.substring(0, th2_pos).equals("= ")) {
/* 668 */                 select3 = "selected";
/* 669 */               } else if (th2.substring(0, th2_pos).equals("!= ")) {
/* 670 */                 select4 = "selected";
/* 671 */               } else if (th2.substring(0, th2_pos).equals("<= ")) {
/* 672 */                 select5 = "selected";
/* 673 */               } else if (th2.substring(0, th2_pos).equals(">= ")) {
/* 674 */                 select6 = "selected";
/*     */               }
/* 676 */               out.write("\n\n\t\t\t\n\t\t\t\t<td align=\"left\" class=\"bodytext\" >\n\t\t\t\t\t<img border=\"0\" width=\"31px\" height=\"21px\" src=\"/images/heat_orange.gif\" hspace=\"1\" class=\"heat\" style=\"vertical-align:middle\">&nbsp;\n\t\t\t\t</td>\n\t\t\t\t<td align=\"left\" class=\"bodytext\" >\n\t\t\t\t\t<select name=\"threshcondn2\" class=\"formtext\">\n\t\t\t\t\t\t<option value=\"LT\" ");
/* 677 */               out.print(select1);
/* 678 */               out.write(">&lt;</option>\n\t\t\t\t\t\t<option value=\"GT\" ");
/* 679 */               out.print(select2);
/* 680 */               out.write(">&gt;</option>\n\t\t\t\t\t\t<option value=\"EQ\" ");
/* 681 */               out.print(select3);
/* 682 */               out.write(">=</option>\n\t\t\t\t\t\t<option value=\"NE\" ");
/* 683 */               out.print(select4);
/* 684 */               out.write(">!=</option>\n\t\t\t\t\t\t<option value=\"LE\" ");
/* 685 */               out.print(select5);
/* 686 */               out.write(">&lt;=</option>\n\t\t\t\t\t\t<option value=\"GE\" ");
/* 687 */               out.print(select6);
/* 688 */               out.write(">&gt;=</option>\n\t\t\t\t\t</select>\n\t\t\t\t\t&nbsp;\n\t\t\t\t\t<input type=\"text\" name=\"threshvalue2\" value=\"");
/* 689 */               out.print(th2.substring(th2_pos + 1));
/* 690 */               out.write("\" styleClass=\"formtext\" maxlength=\"15\" size=\"5\">\n\t\t\t\t</td>\n\t\t\t\t<td align=\"left\" class=\"bodytext\" >\n\t\t\t\t\t<img border=\"0\" width=\"31px\" height=\"21px\" src=\"/images/heat_red.gif\" hspace=\"1\" class=\"heat\" style=\"vertical-align:middle\">&nbsp;\n\t\t\t\t</td>\n\t\t\t\t<td align=\"left\" class=\"bodytext\" >\n\t\t\t\t\t");
/*     */               
/*     */ 
/* 693 */               select1 = "";
/* 694 */               select2 = "";
/* 695 */               select3 = "";
/* 696 */               select4 = "";
/* 697 */               select5 = "";
/* 698 */               select6 = "";
/* 699 */               String th3 = (String)thresholds.get("critical");
/* 700 */               int th3_pos = ((String)thresholds.get("critical")).indexOf("#");
/* 701 */               if (th3.substring(0, th1_pos).equals("< ")) {
/* 702 */                 select1 = "selected";
/* 703 */               } else if (th3.substring(0, th3_pos).equals("> ")) {
/* 704 */                 select2 = "selected";
/* 705 */               } else if (th3.substring(0, th3_pos).equals("= ")) {
/* 706 */                 select3 = "selected";
/* 707 */               } else if (th3.substring(0, th3_pos).equals("!= ")) {
/* 708 */                 select4 = "selected";
/* 709 */               } else if (th3.substring(0, th3_pos).equals("<= ")) {
/* 710 */                 select5 = "selected";
/* 711 */               } else if (th3.substring(0, th3_pos).equals(">= ")) {
/* 712 */                 select6 = "selected";
/*     */               }
/* 714 */               out.write("\t\n\t\t\t\t\t<select name=\"threshcondn3\" class=\"formtext\">\n\t\t\t\t\t\t<option value=\"LT\" ");
/* 715 */               out.print(select1);
/* 716 */               out.write(">&lt;</option>\n\t\t\t\t\t\t<option value=\"GT\" ");
/* 717 */               out.print(select2);
/* 718 */               out.write(">&gt;</option>\n\t\t\t\t\t\t<option value=\"EQ\" ");
/* 719 */               out.print(select3);
/* 720 */               out.write(">=</option>\n\t\t\t\t\t\t<option value=\"NE\" ");
/* 721 */               out.print(select4);
/* 722 */               out.write(">!=</option>\n\t\t\t\t\t\t<option value=\"LE\" ");
/* 723 */               out.print(select5);
/* 724 */               out.write(">&lt;=</option>\n\t\t\t\t\t\t<option value=\"GE\" ");
/* 725 */               out.print(select6);
/* 726 */               out.write(">&gt;=</option>\n\t\t\t\t\t</select>\n\t\t\t\t\t&nbsp;\n\t\t\t\t\t<input type=\"text\" name=\"threshvalue3\" value=\"");
/* 727 */               out.print(th3.substring(th3_pos + 1));
/* 728 */               out.write("\" styleClass=\"formtext\" maxlength=\"15\" size=\"5\">\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t");
/*     */             }
/*     */             
/*     */ 
/* 732 */             out.write("\n\t\t\t<tr align=\"center\">\n\t\t\t\t<td align=\"center\" class=\"bodytext\" colspan=\"6\" >\n\t\t\t\t\t");
/*     */             
/* 734 */             if (divname != null)
/*     */             {
/*     */ 
/* 737 */               out.write("\n\t\t\t\t\t&nbsp;<input width=\"30\" align=\"middle\" name=\"button\" class=\"buttons\" value=\"");
/* 738 */               out.print(FormatUtil.getString("am.webclient.newmonitorgroup.show"));
/* 739 */               out.write("\" onclick=\"javascript:updateHeatThreshDash('");
/* 740 */               out.print(per);
/* 741 */               out.write(39);
/* 742 */               out.write(44);
/* 743 */               out.write(39);
/* 744 */               out.print(divname);
/* 745 */               out.write(39);
/* 746 */               out.write(44);
/* 747 */               out.write(39);
/* 748 */               out.print(attributelist);
/* 749 */               out.write(39);
/* 750 */               out.write(44);
/* 751 */               out.write(39);
/* 752 */               out.print(owner);
/* 753 */               out.write(39);
/* 754 */               out.write(44);
/* 755 */               out.write(39);
/* 756 */               out.print(role);
/* 757 */               out.write(39);
/* 758 */               out.write(44);
/* 759 */               out.write(39);
/* 760 */               out.print(isthreconfd);
/* 761 */               out.write("')\" type=\"button\">&nbsp;&nbsp;\n\t\t\t\t\t");
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/*     */ 
/* 767 */               out.write("\n\n\t\t\t\t\t&nbsp;<input width=\"30\" align=\"middle\" name=\"button\" class=\"buttons\" value=\"");
/* 768 */               out.print(FormatUtil.getString("am.webclient.newmonitorgroup.show"));
/* 769 */               out.write("\" onclick=\"javascript:updateHeatThresh('");
/* 770 */               out.print(per);
/* 771 */               out.write(39);
/* 772 */               out.write(44);
/* 773 */               out.write(39);
/* 774 */               out.print(isthreconfd);
/* 775 */               out.write("')\" type=\"button\">&nbsp;&nbsp;\n\t\t\t\t\t");
/*     */             }
/*     */             
/*     */ 
/* 779 */             out.write("\n\t\t\t\t\t<input name=\"button\" class=\"buttons\" value=\"");
/* 780 */             out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 781 */             out.write("\" onclick=\"cancelHeatThresh()\" type=\"button\">\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\t</form>\n      </div>\n</td>\n</tr>\n</table>\n\t\t");
/*     */             
/* 783 */             if (divname != null)
/*     */             {
/*     */ 
/* 786 */               out.write("\n\t\t\t</div>\n\t\t");
/*     */             }
/*     */             
/*     */ 
/* 790 */             out.write(10);
/*     */           }
/* 792 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 793 */         out = _jspx_out;
/* 794 */         if ((out != null) && (out.getBufferSize() != 0))
/* 795 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 796 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 799 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 805 */     PageContext pageContext = _jspx_page_context;
/* 806 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 808 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 809 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 810 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 812 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 814 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 815 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 816 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 817 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 818 */       return true;
/*     */     }
/* 820 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 821 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 826 */     PageContext pageContext = _jspx_page_context;
/* 827 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 829 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 830 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 831 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 833 */     _jspx_th_c_005fif_005f0.setTest("${param.period == 1}");
/* 834 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 835 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 837 */         out.write("SELECTED");
/* 838 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 839 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 843 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 844 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 845 */       return true;
/*     */     }
/* 847 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 848 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 853 */     PageContext pageContext = _jspx_page_context;
/* 854 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 856 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 857 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 858 */     _jspx_th_c_005fif_005f1.setParent(null);
/*     */     
/* 860 */     _jspx_th_c_005fif_005f1.setTest("${param.period == 2}");
/* 861 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 862 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */       for (;;) {
/* 864 */         out.write("SELECTED");
/* 865 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 866 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 870 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 871 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 872 */       return true;
/*     */     }
/* 874 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 875 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 880 */     PageContext pageContext = _jspx_page_context;
/* 881 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 883 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 884 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 885 */     _jspx_th_c_005fif_005f2.setParent(null);
/*     */     
/* 887 */     _jspx_th_c_005fif_005f2.setTest("${param.period == 3}");
/* 888 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 889 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 891 */         out.write("SELECTED");
/* 892 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 893 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 897 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 898 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 899 */       return true;
/*     */     }
/* 901 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 902 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 907 */     PageContext pageContext = _jspx_page_context;
/* 908 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 910 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 911 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 912 */     _jspx_th_c_005fif_005f3.setParent(null);
/*     */     
/* 914 */     _jspx_th_c_005fif_005f3.setTest("${param.period == 1}");
/* 915 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 916 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */       for (;;) {
/* 918 */         out.write("SELECTED");
/* 919 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 920 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 924 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 925 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 926 */       return true;
/*     */     }
/* 928 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 929 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 934 */     PageContext pageContext = _jspx_page_context;
/* 935 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 937 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 938 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 939 */     _jspx_th_c_005fif_005f4.setParent(null);
/*     */     
/* 941 */     _jspx_th_c_005fif_005f4.setTest("${param.period == 2}");
/* 942 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 943 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*     */       for (;;) {
/* 945 */         out.write("SELECTED");
/* 946 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 947 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 951 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 952 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 953 */       return true;
/*     */     }
/* 955 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 956 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 961 */     PageContext pageContext = _jspx_page_context;
/* 962 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 964 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 965 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 966 */     _jspx_th_c_005fif_005f5.setParent(null);
/*     */     
/* 968 */     _jspx_th_c_005fif_005f5.setTest("${param.period == 3}");
/* 969 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 970 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*     */       for (;;) {
/* 972 */         out.write("SELECTED");
/* 973 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 974 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 978 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 979 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 980 */       return true;
/*     */     }
/* 982 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 983 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HeatChart_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */