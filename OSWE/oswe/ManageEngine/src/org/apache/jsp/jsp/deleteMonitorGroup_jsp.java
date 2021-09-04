/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ 
/*     */ public final class deleteMonitorGroup_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  21 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  36 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  40 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  41 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  46 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  50 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  51 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  52 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  53 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  54 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  61 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  64 */     JspWriter out = null;
/*  65 */     Object page = this;
/*  66 */     JspWriter _jspx_out = null;
/*  67 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  71 */       response.setContentType("text/html;charset=UTF-8");
/*  72 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  74 */       _jspx_page_context = pageContext;
/*  75 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  76 */       ServletConfig config = pageContext.getServletConfig();
/*  77 */       session = pageContext.getSession();
/*  78 */       out = pageContext.getOut();
/*  79 */       _jspx_out = out;
/*     */       
/*  81 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/*  82 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  84 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<script>\nfunction myOnLoad()\n{\n");
/*     */       
/*  86 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/*  87 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/*  88 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/*  90 */       _jspx_th_c_005fif_005f0.setTest("${fromwhere=='monitorgroupview'}");
/*  91 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/*  92 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */         for (;;) {
/*  94 */           out.write(10);
/*     */           
/*  96 */           String retaintree = (String)request.getAttribute("retaintree");
/*     */           
/*  98 */           out.write(10);
/*     */           
/* 100 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 101 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 102 */           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f0);
/* 103 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 104 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */             for (;;) {
/* 106 */               out.write(10);
/*     */               
/* 108 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 109 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 110 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */               
/* 112 */               _jspx_th_c_005fwhen_005f0.setTest("${param.haid!=\"\"}");
/* 113 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 114 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */                 for (;;) {
/* 116 */                   out.write("\nwindow.opener.location.href=\"/showapplication.do?method=showApplication&haid=");
/* 117 */                   out.print(request.getParameter("haid"));
/* 118 */                   out.write("&fromwhere=");
/* 119 */                   out.print(request.getParameter("todelete"));
/* 120 */                   out.write(34);
/* 121 */                   out.write(59);
/* 122 */                   out.write(10);
/* 123 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 124 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 128 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 129 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */               }
/*     */               
/* 132 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 133 */               out.write(10);
/*     */               
/* 135 */               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 136 */               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 137 */               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 138 */               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 139 */               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */                 for (;;) {
/* 141 */                   out.write("\nwindow.opener.location.href= \"/showresource.do?method=showMonitorGroupView&haid=");
/* 142 */                   out.print(request.getParameter("haid"));
/* 143 */                   out.write("&fromwhere=");
/* 144 */                   out.print(request.getParameter("todelete"));
/* 145 */                   out.write("&retaintree=");
/* 146 */                   out.print(retaintree);
/* 147 */                   out.write("&apimessage=");
/* 148 */                   out.print(request.getParameter("apimessage"));
/* 149 */                   out.write(34);
/* 150 */                   out.write(59);
/* 151 */                   out.write(10);
/* 152 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 153 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 157 */               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 158 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */               }
/*     */               
/* 161 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 162 */               out.write(10);
/* 163 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 164 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 168 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 169 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*     */           }
/*     */           
/* 172 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 173 */           out.write("\nwindow.close();\t\n");
/* 174 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 175 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 179 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 180 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 183 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 184 */         out.write("\n\n}\n\n\nfunction deleteMG()\n{\nfor( i = 0; i < document.deletemonitor.mode.length; i++ )\n{\nif( document.deletemonitor.mode[i].checked == true )\n{\nval = document.deletemonitor.mode[i].value;\nif(val=='deleteMG')\n{\n document.deletemonitor.action=\"/deleteMO.do?method=deleteMonitorGroup&todelete=monitorgrouponly&select=");
/* 185 */         out.print((String)request.getAttribute("select"));
/* 186 */         out.write("\";\n document.deletemonitor.submit();\n\n}\nelse if(val=='deleteMGwithMO')\n{\n document.deletemonitor.action=\"/deleteMO.do?method=deleteMonitorGroup&select=");
/* 187 */         out.print((String)request.getAttribute("select"));
/* 188 */         out.write("\";\ndocument.deletemonitor.submit();\n}\n\n\n}\n}\n\n//location.href=\"/deleteMO.do?method=deleteMonitorGroup\";\n//location.href=\"/deleteMO.do?method=deleteMonitorGroup&=monitorgrouponly\";\n}\n</script>\n<html>\n<head>\n<title>\n");
/* 189 */         out.print(FormatUtil.getString("am.webclient.monitorgroupview.deletemg.text"));
/* 190 */         out.write("\n\n</title>\n</head>\n<body onload=\"javascript:myOnLoad()\">\n<br>\n<br>\n<form action=\"/deleteMO.do?method=deleteMO\" name=\"deletemonitor\" method=\"post\" style=\"display:inline\">\n<input type=\"hidden\" name=\"retaintree\" value=\"");
/* 191 */         out.print(request.getParameter("retaintree"));
/* 192 */         out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 193 */         out.print(request.getParameter("haid"));
/* 194 */         out.write("\">\n\n<table class=\"lrtbdarkborder\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" width=\"99%\">\n<tr>\n<td class=\"tableheadingbborder\" colspan=\"2\" height=\"19\">");
/* 195 */         out.print(FormatUtil.getString("am.webclient.monitorgroupview.choose.option.text"));
/* 196 */         out.write("</td>\n</tr>\n     <tr>\n            <td class=\"bodytext\">\n            <input name=\"mode\" value=\"deleteMG\" checked=\"checked\" type=\"radio\">\n            </td>\n            <td class=\"bodytext\">\n\t\t");
/*     */         
/* 198 */         ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 199 */         _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 200 */         _jspx_th_c_005fchoose_005f1.setParent(null);
/* 201 */         int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 202 */         if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*     */           for (;;) {
/* 204 */             out.write(10);
/* 205 */             out.write(9);
/* 206 */             out.write(9);
/*     */             
/* 208 */             WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 209 */             _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 210 */             _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*     */             
/* 212 */             _jspx_th_c_005fwhen_005f1.setTest("${param.haid!=\"\"}");
/* 213 */             int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 214 */             if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*     */               for (;;) {
/* 216 */                 out.write("\n         \t\t ");
/* 217 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupview.subgroups.only.text"));
/* 218 */                 out.write(10);
/* 219 */                 out.write(9);
/* 220 */                 out.write(9);
/* 221 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 222 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 226 */             if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 227 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*     */             }
/*     */             
/* 230 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 231 */             out.write(10);
/* 232 */             out.write(9);
/* 233 */             out.write(9);
/*     */             
/* 235 */             OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 236 */             _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 237 */             _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 238 */             int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 239 */             if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*     */               for (;;) {
/* 241 */                 out.write("\n\t\t\t ");
/* 242 */                 out.print(FormatUtil.getString("am.webclient.monitorgroupview.monitorgroup.only.text"));
/* 243 */                 out.write(10);
/* 244 */                 out.write(9);
/* 245 */                 out.write(9);
/* 246 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 247 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 251 */             if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 252 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*     */             }
/*     */             
/* 255 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 256 */             out.write(10);
/* 257 */             out.write(9);
/* 258 */             out.write(9);
/* 259 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 260 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 264 */         if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 265 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*     */         }
/*     */         else {
/* 268 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 269 */           out.write("\n            </td>\n            <tr>\n            <td class=\"bodytext\">\n\t    <input name=\"mode\" value=\"deleteMGwithMO\"  type=\"radio\">\n\t    </td>\n            <td class=\"bodytext\">\n\t\t");
/*     */           
/* 271 */           ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 272 */           _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 273 */           _jspx_th_c_005fchoose_005f2.setParent(null);
/* 274 */           int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 275 */           if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*     */             for (;;) {
/* 277 */               out.write("\n                ");
/*     */               
/* 279 */               WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 280 */               _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 281 */               _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*     */               
/* 283 */               _jspx_th_c_005fwhen_005f2.setTest("${param.haid!=\"\"}");
/* 284 */               int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 285 */               if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*     */                 for (;;) {
/* 287 */                   out.write("\n                         ");
/* 288 */                   out.print(FormatUtil.getString("am.webclient.monitorgroupview.delete.monitorsandsubgroup.text"));
/* 289 */                   out.write("\n                ");
/* 290 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 291 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 295 */               if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 296 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*     */               }
/*     */               
/* 299 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 300 */               out.write("\n                ");
/*     */               
/* 302 */               OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 303 */               _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 304 */               _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 305 */               int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 306 */               if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*     */                 for (;;) {
/* 308 */                   out.write("\n\t\t  ");
/* 309 */                   out.print(FormatUtil.getString("am.webclient.monitorgroupview.delete.monitorsandmg.text"));
/* 310 */                   out.write("\n\t\t ");
/* 311 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 312 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 316 */               if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 317 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*     */               }
/*     */               
/* 320 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 321 */               out.write("\n                ");
/* 322 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 323 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 327 */           if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 328 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*     */           }
/*     */           else {
/* 331 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 332 */             out.write("\n            </td>\n            </tr>\n            </table>\n            <table class=\"lrbborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\">\n            <tr>\n            \n            <td colspan=\"2\" class=\"tablebottom\"  align=\"center\">\n            <input name=\"addwmiperf\" type=\"button\" class=\"buttons\" value='");
/* 333 */             out.print(FormatUtil.getString("am.webclient.cam.delete.link"));
/* 334 */             out.write("' onClick=\"return deleteMG()\"/> \n\t          &nbsp;&nbsp;<input type=\"button\" value=\"");
/* 335 */             out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 336 */             out.write("\" class='buttons' onClick=\"window.close();\" />\n            </td>\n            </tr>\n            </table>\n            </form>\n </body>\n </html>\n");
/*     */           }
/* 338 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 339 */         out = _jspx_out;
/* 340 */         if ((out != null) && (out.getBufferSize() != 0))
/* 341 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 342 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 345 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 351 */     PageContext pageContext = _jspx_page_context;
/* 352 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 354 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 355 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 356 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 358 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 360 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 361 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 362 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 363 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 364 */       return true;
/*     */     }
/* 366 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 367 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\deleteMonitorGroup_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */