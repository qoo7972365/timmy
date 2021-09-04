/*     */ package org.apache.jsp.jsp.includes;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.SkipPageException;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.InstanceManagerFactory;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class NewPagingComp_jsp
/*     */   extends HttpJspBase
/*     */   implements JspSourceDependent
/*     */ {
/*  32 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  44 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  51 */     this._jsp_instancemanager = InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  55 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  56 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, ServletException
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
/*  77 */       ServletContext application = pageContext.getServletContext();
/*  78 */       ServletConfig config = pageContext.getServletConfig();
/*  79 */       session = pageContext.getSession();
/*  80 */       out = pageContext.getOut();
/*  81 */       _jspx_out = out;
/*     */       
/*  83 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"> </SCRIPT>\n<Style>  \t \n TABLE.navigatorTable A:link {\n\t\t color:#a9a9a9;\n\t\t font-family: Arial, Helvetica, sans-serif;\n\t\t text-decoration: none;\n }\n TABLE.navigatorTable A:hover {\n\t\t color: #000000;\n\t\t text-decoration: underline;\n }\t\n\n</style>\n");
/*     */       
/*     */       try
/*     */       {
/*  87 */         out.write(10);
/*     */         
/*  89 */         int startIndex = 0;
/*  90 */         int noOfRows = 25;
/*  91 */         String oldtab = request.getParameter("oldtab");
/*  92 */         String actionPath = request.getParameter("actionPath");
/*  93 */         String ajaxMethod = request.getParameter("ajaxMethod");
/*  94 */         String ajaxMethod1 = ajaxMethod;
/*  95 */         String totalObj = request.getParameter("totalObj");
/*  96 */         boolean useAjax = false;
/*  97 */         int noOfObjects = 0;
/*  98 */         if (ajaxMethod != null)
/*     */         {
/* 100 */           useAjax = true;
/* 101 */           ajaxMethod = ajaxMethod + "1('" + actionPath + "')";
/*     */         }
/*     */         
/* 104 */         out.write(10);
/*     */         
/* 106 */         Object selectedPageNo = request.getSession().getAttribute("selectedPage");
/*     */         
/* 108 */         String[] pagePerShowOptions = { "25", "50", "75", "100" };
/*     */         
/*     */ 
/* 111 */         if ((request.getParameter("noOfRows") != null) && (request.getParameter("selectedPage") != null))
/*     */         {
/* 113 */           noOfRows = Integer.parseInt(request.getParameter("noOfRows"));
/*     */         }
/* 115 */         else if (request.getSession().getAttribute("noOfRows") != null)
/*     */         {
/* 117 */           noOfRows = ((Integer)request.getSession().getAttribute("noOfRows")).intValue();
/*     */         }
/* 119 */         int selectedPage = 1;
/* 120 */         if (request.getParameter("selectedPage") != null)
/*     */         {
/*     */ 
/* 123 */           selectedPage = Integer.parseInt(request.getParameter("selectedPage"));
/*     */         }
/* 125 */         else if (selectedPageNo != null)
/*     */         {
/* 127 */           selectedPage = ((Integer)selectedPageNo).intValue();
/*     */         }
/* 129 */         boolean forwardtobj = false;
/* 130 */         if ((totalObj != null) && (!totalObj.equals("null")))
/*     */         {
/* 132 */           noOfObjects = Integer.parseInt(request.getParameter("totalObj"));
/* 133 */           int noOfPages = noOfObjects / noOfRows;
/* 134 */           int remainPage = noOfObjects % noOfRows;
/* 135 */           if (remainPage > 0)
/*     */           {
/* 137 */             noOfPages++;
/*     */           }
/* 139 */           if (selectedPage < noOfPages) {
/* 140 */             forwardtobj = true;
/*     */           }
/*     */         }
/*     */         
/* 144 */         request.getSession().setAttribute("noOfRows", Integer.valueOf(noOfRows));
/* 145 */         request.getSession().setAttribute("oldtab", oldtab);
/* 146 */         int rowCount = 0;
/* 147 */         if (request.getParameter("rowcount") != null) {
/* 148 */           rowCount = Integer.parseInt(request.getParameter("rowcount"));
/*     */         }
/*     */         
/* 151 */         out.write("\n<table id =\"mytable\"class=\"navigatorTable\" cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"padding-bottom:5px;\">\n<tr>\n\n<td class=\"bodytext dulltext\"  nowrap align=\"right\">\n");
/*     */         
/* 153 */         int pageStart = 1;
/* 154 */         int nextPage = selectedPage + 1;
/* 155 */         int prevPage = selectedPage - 1;
/*     */         
/*     */ 
/* 158 */         out.write(10);
/* 159 */         if ("true".equals(request.getParameter("showcountspan")))
/*     */         {
/* 161 */           out.write("\n\n<span id=\"showtotal\"><a href=\"javascript:getMonitorsCountForListView('/showresource.do?method=getTotalCount&sessid=totalcountqueries')\"  >\n");
/* 162 */           out.print(FormatUtil.getString("am.webclient.listview.showtotalcount"));
/* 163 */           out.write("</a> </span>\n");
/*     */         }
/* 165 */         out.write("\n<span id=\"mycount\" >\n&nbsp;\n</span> \n");
/* 166 */         out.print(FormatUtil.getString("am.webclient.listview.Navigate"));
/* 167 */         out.write(32);
/* 168 */         out.write(10);
/* 169 */         out.write(10);
/* 170 */         if (selectedPage != 1) {
/* 171 */           out.write(10);
/*     */           
/* 173 */           IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 174 */           _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 175 */           _jspx_th_c_005fif_005f0.setParent(null);
/*     */           
/* 177 */           _jspx_th_c_005fif_005f0.setTest("${param.ajaxMethod==null}");
/* 178 */           int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 179 */           if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */             for (;;) {
/* 181 */               out.write("\n\t<a href=\"javascript:showPerPage1('");
/* 182 */               out.print(actionPath);
/* 183 */               out.write("&selectedPage=");
/* 184 */               out.print(prevPage);
/* 185 */               out.write("&noOfRows=");
/* 186 */               out.print(noOfRows);
/* 187 */               out.write("&oldtab=");
/* 188 */               out.print(oldtab);
/* 189 */               out.write("')\"><img src=\"/images/navigator/previous_link.png\" alt=\"Previous\" border=\"0\" align=\"ABSMIDDLE\"></a>\n");
/* 190 */               int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 191 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 195 */           if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 196 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*     */           }
/*     */           
/* 199 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 200 */           out.write(10);
/* 201 */           out.write(10);
/*     */           
/* 203 */           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 204 */           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 205 */           _jspx_th_c_005fif_005f1.setParent(null);
/*     */           
/* 207 */           _jspx_th_c_005fif_005f1.setTest("${param.ajaxMethod!=null}");
/* 208 */           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 209 */           if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */             for (;;) {
/* 211 */               out.write("\n<a href = \"javascript:");
/* 212 */               out.print(ajaxMethod1);
/* 213 */               out.write(49);
/* 214 */               out.write(40);
/* 215 */               out.write(39);
/* 216 */               out.print(actionPath);
/* 217 */               out.write("&selectedPage=");
/* 218 */               out.print(prevPage);
/* 219 */               out.write("&noOfRows=");
/* 220 */               out.print(noOfRows);
/* 221 */               out.write("&oldtab=");
/* 222 */               out.print(oldtab);
/* 223 */               out.write("')\"><img src=\"/images/navigator/previous_link.png\" alt=\"Previous\" border=\"0\" align=\"ABSMIDDLE\"></a>\n");
/* 224 */               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 225 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 229 */           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 230 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*     */           }
/*     */           
/* 233 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 234 */           out.write(10);
/*     */         }
/* 236 */         out.write(10);
/* 237 */         out.print(selectedPage);
/* 238 */         out.write(10);
/* 239 */         if ((rowCount >= noOfRows) || (forwardtobj)) {
/* 240 */           out.write(10);
/*     */           
/* 242 */           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 243 */           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 244 */           _jspx_th_c_005fif_005f2.setParent(null);
/*     */           
/* 246 */           _jspx_th_c_005fif_005f2.setTest("${param.ajaxMethod==null}");
/* 247 */           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 248 */           if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */             for (;;) {
/* 250 */               out.write("\n\n<a href=\"javascript:showPerPage1('");
/* 251 */               out.print(actionPath);
/* 252 */               out.write("&selectedPage=");
/* 253 */               out.print(nextPage);
/* 254 */               out.write("&noOfRows=");
/* 255 */               out.print(noOfRows);
/* 256 */               out.write("&oldtab=");
/* 257 */               out.print(oldtab);
/* 258 */               out.write("')\"><img src=\"/images/navigator/next_link.png\" alt=\"Next\" border=\"0\" align=\"ABSMIDDLE\"></a>\n");
/* 259 */               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 260 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 264 */           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 265 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*     */           }
/*     */           
/* 268 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 269 */           out.write(10);
/* 270 */           out.write(10);
/*     */           
/* 272 */           IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 273 */           _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 274 */           _jspx_th_c_005fif_005f3.setParent(null);
/*     */           
/* 276 */           _jspx_th_c_005fif_005f3.setTest("${param.ajaxMethod!=null}");
/* 277 */           int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 278 */           if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */             for (;;) {
/* 280 */               out.write("\n<a href = \"javascript:");
/* 281 */               out.print(ajaxMethod1);
/* 282 */               out.write(49);
/* 283 */               out.write(40);
/* 284 */               out.write(39);
/* 285 */               out.print(actionPath);
/* 286 */               out.write("&selectedPage=");
/* 287 */               out.print(nextPage);
/* 288 */               out.write("&noOfRows=");
/* 289 */               out.print(noOfRows);
/* 290 */               out.write("&oldtab=");
/* 291 */               out.print(oldtab);
/* 292 */               out.write("')\"><img src=\"/images/navigator/next_link.png\" alt=\"Next\" border=\"0\" align=\"ABSMIDDLE\"></a>\n");
/* 293 */               int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 294 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 298 */           if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 299 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*     */           }
/*     */           
/* 302 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 303 */           out.write(10);
/*     */         }
/* 305 */         out.write("\n\n\n\n\t");
/* 306 */         out.print(FormatUtil.getString("am.webclient.paging.showperpage"));
/* 307 */         out.write("\n\t\n\t\t");
/*     */         
/* 309 */         if (useAjax)
/*     */         {
/*     */ 
/* 312 */           out.write("\n\t\t<select class=\"formtext\" name=\"noOfRows\" onChange=\"javascript:");
/* 313 */           out.print(ajaxMethod1);
/* 314 */           out.write(49);
/* 315 */           out.write(40);
/* 316 */           out.write(39);
/* 317 */           out.print(actionPath);
/* 318 */           out.write("&selectedPage=1&oldtab=");
/* 319 */           out.print(oldtab);
/* 320 */           out.write("&noOfRows='+this.value);\">\n         \t");
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 326 */           out.write("\n\t\t<select class=\"formtext\" name=\"noOfRows\" onChange=\"javascript:showPerPage1('");
/* 327 */           out.print(actionPath);
/* 328 */           out.write("&selectedPage=1&oldtab=");
/* 329 */           out.print(oldtab);
/* 330 */           out.write("&noOfRows='+this.value);\">\n\t\t");
/*     */         }
/*     */         
/*     */ 
/* 334 */         out.write("\t\n\n\t\t\t");
/*     */         
/* 336 */         for (int l = 0; l < pagePerShowOptions.length; l++)
/*     */         {
/* 338 */           if (noOfRows == Integer.parseInt(pagePerShowOptions[l]))
/*     */           {
/*     */ 
/* 341 */             out.write("\n\t\t\t\t<option value=\"");
/* 342 */             out.print(pagePerShowOptions[l]);
/* 343 */             out.write("\" Selected>");
/* 344 */             out.print(pagePerShowOptions[l]);
/* 345 */             out.write("</option>\n\t\t\t");
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/*     */ 
/* 351 */             out.write("\n\t\t\t\t<option value=\"");
/* 352 */             out.print(pagePerShowOptions[l]);
/* 353 */             out.write(34);
/* 354 */             out.write(62);
/* 355 */             out.print(pagePerShowOptions[l]);
/* 356 */             out.write("</option>\n\t\t\t");
/*     */           }
/*     */         }
/*     */         
/* 360 */         if (noOfRows == -1)
/*     */         {
/*     */ 
/* 363 */           out.write("\n\t\t\t<option value=\"-1\" Selected>");
/* 364 */           out.print(FormatUtil.getString("it360.paging.showAll"));
/* 365 */           out.write("</option>\n\t\t\t");
/*     */         }
/*     */         
/*     */ 
/* 369 */         out.write("\n\t\t</select>\n\t</td>\n\n\n</tr>\n</table>\n");
/*     */ 
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */ 
/* 375 */         ex.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/* 379 */       out.write("\n\n<script>\nfunction showPerPage1(link)\n{\n\t\t");
/* 380 */       if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
/*     */         return;
/* 382 */       out.write("\n\t\tlocation.href=link;\n\t\n}\n\n\n\nfunction getHomeAvailabilityData1(type)\n{\n\n\tSet_Cookie('am_monitorsview','availability'); //No I18N\n\tvar d = new Date();\n\thttp.open(\"GET\",type+\"&date=\"+d,true);\n\thttp.onreadystatechange = handleAvailabilityPagingResponse;\n        http.send(null);\n\n}\n\n\nfunction handleAvailabilityPagingResponse()\n{\n\tif(http.readyState == 4)\n        {\n                if (http.status == 200)\n\t\t{\n\t\t\t\n                \tvar ele = document.getElementById(\"availabilitydata\");\n\t\t\tele.innerHTML = http.responseText;\n\t\t\t/* var ele1 = document.getElementById(\"availabilityPageRange\");\n\t\t\tele1.innerHTML = (document.getElementById(\"availabilityPurpose\")).innerHTML \n\t\t\tdocument.getElementById(\"availability\").style.display=\"block\";\n\t\t\tdocument.getElementById(\"loadingg\").style.display=\"none\";*/\n\t\t}\n\t}\n}\n\n\n\n\nfunction getCategoryPerformanceData1(type)\n{\n\tSet_Cookie('am_monitorsview','performance'); //No I18N\n\tdocument.getElementById(\"availability\").style.display=\"none\";\n\thttp.open(\"GET\",type,true);\n\thttp.onreadystatechange = handleCategoryPerformanceResponse1;\n");
/* 383 */       out.write("        http.send(null);\n\n}\n\nfunction handleCategoryPerformanceResponse1()\n{\n\tif(http.readyState == 4)\n        {\n                if (http.status == 200)\n\t\t{\n\t\tvar ele = document.getElementById(\"healthdata\");\n\t\tele.innerHTML = http.responseText;\n\t\tdocument.getElementById(\"health\").style.display=\"block\";\n\t\tdocument.getElementById(\"loadingg\").style.display=\"none\";\n\t\t}\n\t}\n\n}\n\nfunction getVirtualMachineData1(type)\n{\n\n\thttp.open(\"GET\",type,true);\n\thttp.onreadystatechange = handleVirtualMachineResponse1;\n        http.send(null);\n\n}\n\nfunction handleVirtualMachineResponse1()\n{\n\tif(http.readyState == 4)\n        {\n                if (http.status == 200)\n\t\t{\n\t\t\tvar ele = document.getElementById(\"virtualmachinetable\");\n\t\t\tele.innerHTML = http.responseText;\n\n\t\t}\n\t}\n\n}\n\nfunction getEC2InstanceData1(type)\n{\n\thttp.open(\"GET\",type,true);\n\thttp.onreadystatechange = handleEC2InstanceResponse1;\n        http.send(null);\n\n}\n\nfunction handleEC2InstanceResponse1()\n{\n\tif(http.readyState == 4)\n        {\n                if (http.status == 200)\n");
/* 384 */       out.write("\t\t{\n\t\t\tvar ele = document.getElementById(\"ec2instancetable\");\n\t\t\tele.innerHTML = http.responseText;\n\n\t\t}\n\t}\n\n}\n\n\n\n</script>\n\n");
/*     */     } catch (Throwable t) {
/* 386 */       if (!(t instanceof SkipPageException)) {
/* 387 */         out = _jspx_out;
/* 388 */         if ((out != null) && (out.getBufferSize() != 0))
/* 389 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 390 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 393 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 399 */     PageContext pageContext = _jspx_page_context;
/* 400 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 402 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 403 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 404 */     _jspx_th_c_005fif_005f4.setParent(null);
/*     */     
/* 406 */     _jspx_th_c_005fif_005f4.setTest("${param.search == true}");
/* 407 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 408 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*     */       for (;;) {
/* 410 */         out.write("\n\t\t\t\tlink = link+\"&searchnextpage=true&searchString=");
/* 411 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 412 */           return true;
/* 413 */         out.write("&searchOption=\"+Get_Cookie('searchOption');\t //No I18N \t\t\n\t\t");
/* 414 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 415 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 419 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 420 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 421 */       return true;
/*     */     }
/* 423 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 424 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 429 */     PageContext pageContext = _jspx_page_context;
/* 430 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 432 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 433 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 434 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/*     */     
/* 436 */     _jspx_th_c_005fout_005f0.setValue("${searchString}");
/* 437 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 438 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 439 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 440 */       return true;
/*     */     }
/* 442 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 443 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\NewPagingComp_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */