/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.NotPresentTag;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*     */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ 
/*     */ public final class MyPage_005fAddWidgets_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  20 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  26 */   private static java.util.Map<String, Long> _jspx_dependants = new java.util.HashMap(1);
/*  27 */   static { _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  43 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  47 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  56 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  60 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  61 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  62 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  63 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  64 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*  65 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  66 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  67 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  74 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  77 */     JspWriter out = null;
/*  78 */     Object page = this;
/*  79 */     JspWriter _jspx_out = null;
/*  80 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  84 */       response.setContentType("text/html");
/*  85 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  87 */       _jspx_page_context = pageContext;
/*  88 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  89 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  90 */       session = pageContext.getSession();
/*  91 */       out = pageContext.getOut();
/*  92 */       _jspx_out = out;
/*     */       
/*  94 */       out.write(10);
/*  95 */       out.write("\n\n\n\n");
/*  96 */       com.adventnet.appmanager.struts.beans.MyPageBean mygraph = null;
/*  97 */       mygraph = (com.adventnet.appmanager.struts.beans.MyPageBean)_jspx_page_context.getAttribute("mygraph", 1);
/*  98 */       if (mygraph == null) {
/*  99 */         mygraph = new com.adventnet.appmanager.struts.beans.MyPageBean();
/* 100 */         _jspx_page_context.setAttribute("mygraph", mygraph, 1);
/*     */       }
/* 102 */       out.write(10);
/* 103 */       out.write(10);
/* 104 */       response.setContentType("text/html;charset=UTF-8");
/* 105 */       out.write(10);
/* 106 */       out.write(10);
/*     */       
/* 108 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 109 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 110 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/* 112 */       _jspx_th_c_005fif_005f0.setTest("${Action!=\"createDashBoard\"}");
/* 113 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 114 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */         for (;;) {
/* 116 */           out.write("\n<html>\n<head>\n<title>");
/* 117 */           out.print(FormatUtil.getString("am.mypage.add.widgets.text"));
/* 118 */           out.write("</title>\n</head>\n<body marginheight=0 marginwidth=0 leftmargin=0 topmargin=0 >\n");
/* 119 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 120 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 124 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 125 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 128 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 129 */         out.write("\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
/*     */         
/*     */ 
/* 132 */         boolean isInvokedInline = request.getAttribute("invokedInline") != null;
/* 133 */         if (!isInvokedInline)
/*     */         {
/* 135 */           out.write(10);
/* 136 */           out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 137 */           if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */             return;
/* 139 */           out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 140 */           out.write(10);
/* 141 */           out.write("\n<script type=\"text/javascript\" src=\"/template/mootools.js\"></script>\n");
/*     */         }
/* 143 */         out.write("\n<script type=\"text/javascript\" src=\"/template/dhtmlTree/dhtmlXTree.js\"></script>\n<script type=\"text/javascript\" src=\"/template/dhtmlTree/dhtmlXCommon.js\"></script>\n<script language=\"JavaScript\" type=\"text/javascript\" src=\"../template/appmanager.js\"></script>\n<script>\nvar http1;\nfunction validateAndSubmit()\n{\n");
/*     */         
/* 145 */         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 146 */         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 147 */         _jspx_th_c_005fif_005f1.setParent(null);
/*     */         
/* 149 */         _jspx_th_c_005fif_005f1.setTest("${'editDashboard'== 'editDashboard'}");
/* 150 */         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 151 */         if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */           for (;;) {
/* 153 */             out.write("\n\n\tvar url=\"\";\t//No I18N\n\tvar pageid='");
/* 154 */             out.print(request.getAttribute("pageid"));
/* 155 */             out.write("';//No I18N\n\t");
/*     */             
/* 157 */             ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 158 */             _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 159 */             _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f1);
/* 160 */             int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 161 */             if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */               for (;;) {
/* 163 */                 out.write(10);
/* 164 */                 out.write(9);
/*     */                 
/* 166 */                 WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 167 */                 _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 168 */                 _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */                 
/* 170 */                 _jspx_th_c_005fwhen_005f0.setTest("${MyPageForm.pageType==\"businesspage\"}");
/* 171 */                 int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 172 */                 if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */                   for (;;) {
/* 174 */                     out.write("\n\t var selwidgets=\"\";//No I18N\n\t var length=document.MyPageForm.selectedWidgetsCheckbox.length;\n\t for(i=0;i<length;i++)\n\t {\n\t     if(document.MyPageForm.selectedWidgetsCheckbox[i].checked)\n\t     {\n\t     \tselwidgets=selwidgets+document.MyPageForm.selectedWidgetsCheckbox[i].value+','; //No I18N\n\t     }\n\t }\n         if(selwidgets=='')\n         {\n              alert(\"");
/* 175 */                     out.print(FormatUtil.getString("am.mypage.widget.add.alert.text"));
/* 176 */                     out.write("\");//No I18N\n\t      return false;//No I18N\n         }\n         url=\"/MyPage.do?method=addWidgets&pageid=\"+pageid+\"&selectedWidgets=\"+selwidgets+\"&randomnumber=\"+ Math.random();//No I18N\n\t");
/* 177 */                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 178 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 182 */                 if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 183 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */                 }
/*     */                 
/* 186 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 187 */                 out.write(10);
/* 188 */                 out.write(9);
/*     */                 
/* 190 */                 OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 191 */                 _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 192 */                 _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 193 */                 int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 194 */                 if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */                   for (;;) {
/* 196 */                     out.write("\n\tdocument.MyPageForm.selectedWidgets.value=tree.getAllCheckedLeafs();\n\tif(document.MyPageForm.selectedWidgets.value == \"\")\n\t{\n\t\talert(\"");
/* 197 */                     out.print(FormatUtil.getString("am.mypage.widget.add.alert.text"));
/* 198 */                     out.write("\");\n\t\treturn false;\n\t}\n\t//alert(\"test:\"+document.MyPageForm.selectedWidgets.value);\n\tvar selwidgets=document.MyPageForm.selectedWidgets.value;\n\turl=\"/MyPage.do?method=addWidgets&pageid=\"+pageid+\"&selectedWidgets=\"+selwidgets+\"&randomnumber=\"+ Math.random();\n\t");
/* 199 */                     int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 200 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 204 */                 if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 205 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */                 }
/*     */                 
/* 208 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 209 */                 out.write(10);
/* 210 */                 out.write(9);
/* 211 */                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 212 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 216 */             if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 217 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*     */             }
/*     */             
/* 220 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 221 */             out.write("\n\thttp1=getHTTPObject();\n\thttp1.onreadystatechange =function () { addSuccess();} ;\n\thttp1.open(\"GET\", url, true);\n\thttp1.send(null);\n\t//document.MyPageForm.submit();\n\t");
/* 222 */             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 223 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 227 */         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 228 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*     */         }
/*     */         else {
/* 231 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 232 */           out.write("\n}\nfunction addSuccess()\n{\nif(http1.readyState == 4)\n \t{\n       \t   if( http1.status == 200)\n\t   {\n   \t\t    \twindow.opener.location.hash = 'scrollWidget'\t\t//No I18N\n\t\t  \t\twindow.opener.location.reload();\n  \t\t\t\tdocument.getElementById(\"savediv\").style.display=\"block\";\n  \t   }\n  \t }\n}\nfunction widgetTree()\n{\n\ttree = new dhtmlXTreeObject(document.getElementById('widgetTree'),\"100%\",\"100%\",0);\n\ttree.setImagePath(\"/images/dhtmlTree/\");\n\ttree.enableCheckBoxes(1);\n \ttree.enableThreeStateCheckboxes(true);\n\ttree.setIconSize(1,1);\n\t//tree.setOnClickHandler(showWidgetImage);\n\ttree.loadXML(\"/webclient/");
/* 233 */           if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */             return;
/* 235 */           out.write("\"); //load root level from xml\n}\nfunction setTreeTDWidth()\n{\n\tvar containerWidth = document.getElementById(\"WidgetsDiv\").offsetWidth;\n\tdocument.getElementById('treeTd').style.width = containerWidth-360;\n}\n");
/* 236 */           out.write(10);
/* 237 */           if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*     */             return;
/* 239 */           out.write("\n</script>\n<style>\n.header2 {\ncolor:#F26522;\nfont-family:Arial,Helvetica,sans-serif;\nfont-size:14px;\nfont-style:normal;\nfont-variant:normal;\nfont-weight:bold;\npadding:0px 3px;\ntext-decoration:none;\ntext-transform:none;\n}\n</style>\n");
/*     */           
/* 241 */           IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 242 */           _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 243 */           _jspx_th_c_005fif_005f7.setParent(null);
/*     */           
/* 245 */           _jspx_th_c_005fif_005f7.setTest("${Action!=\"createDashBoard\"}");
/* 246 */           int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 247 */           if (_jspx_eval_c_005fif_005f7 != 0) {
/*     */             for (;;) {
/* 249 */               out.write("\n<table width=\"100%\" height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n<tr>\n<td>&nbsp;<span class=\"headingboldwhite\">");
/* 250 */               if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*     */                 return;
/* 252 */               out.write(32);
/* 253 */               out.write(58);
/* 254 */               out.write(32);
/* 255 */               out.print(FormatUtil.getString("am.mypage.add.widgets.text"));
/* 256 */               out.write("</span><span class=\"headingwhite\"> </span>\n</td>\n</tr>\n\n</table>\n<table  border=\"0\" cellpadding=\"0\" cellspacing=\"5\" width=\"100%\">\n<tr>\n<td width=\"60%\"  valign=\"top\">\n\n");
/* 257 */               int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 258 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 262 */           if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 263 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/*     */           }
/*     */           else {
/* 266 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 267 */             out.write("\n\n<div style=\"display: none;\" id=\"savediv\">\n            <table width=\"100%\" border=\"0\">\n              <tbody><tr align=\"center\">\n                <td class=\"bodytextbold\"><span style=\"font-weight: bold; color: rgb(0, 0, 0); font-size: 11px;\" id=\"saveresult\">");
/* 268 */             out.print(FormatUtil.getString("am.mypage.widgets.add.success.text"));
/* 269 */             out.write("</span></td>\n              </tr>\n            </tbody></table>\n          </div>\n\n\n");
/* 270 */             out.write(10);
/*     */             
/* 272 */             IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 273 */             _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 274 */             _jspx_th_c_005fif_005f8.setParent(null);
/*     */             
/* 276 */             _jspx_th_c_005fif_005f8.setTest("${MyPageForm.pageType!=\"businesspage\"}");
/* 277 */             int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 278 */             if (_jspx_eval_c_005fif_005f8 != 0) {
/*     */               for (;;) {
/* 280 */                 out.write("\n<div id=\"WidgetsDiv\" style=\"margin:10px;width:100%\">\n\t");
/*     */                 
/* 282 */                 IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 283 */                 _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 284 */                 _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f8);
/*     */                 
/* 286 */                 _jspx_th_c_005fif_005f9.setTest("${Action!=\"createDashBoard\"}");
/* 287 */                 int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 288 */                 if (_jspx_eval_c_005fif_005f9 != 0) {
/*     */                   for (;;) {
/* 290 */                     out.write("\n\t<form name=\"MyPageForm\" method=\"get\" action=\"/MyPage.do\">\n\t<input type=\"hidden\" name=\"pageid\" value=\"");
/* 291 */                     out.print(request.getAttribute("pageid"));
/* 292 */                     out.write("\" />\n\t<input type=\"hidden\" name=\"method\" value=\"addWidgets\"/>\n\t");
/* 293 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 294 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 298 */                 if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 299 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*     */                 }
/*     */                 
/* 302 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 303 */                 out.write("\n\t<input type=\"hidden\" name=\"selectedWidgets\" value=\"\"/>\n\n\n\t  <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"355\" ");
/* 304 */                 if (_jspx_meth_c_005fif_005f10(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*     */                   return;
/* 306 */                 out.write(" style=\"margin-bottom: 40px;\"> ");
/* 307 */                 out.write("\n\t\t  <tr>\n\t\t\t<td id=\"treeTd\"  align=\"left\" valign=\"top\">\n\t\t\t\t<div style=\"min-height:450px\" class=\"lrtbdarkborder\">\n\t\t\t\t<div class=\"tableheadingbborder\">");
/* 308 */                 out.print(FormatUtil.getString("am.mypage.addwidgets.widgetlist.text"));
/* 309 */                 out.write("</div>\n\t\t\t\t<div style=\"overflow-y: auto;padding:10px\" id=\"widgetTree\"></div>\n\t\t\t\t</div>\n\t\t\t</td>\n\n\t\t  </tr>\n\t  </table>\n        ");
/* 310 */                 if (_jspx_meth_c_005fif_005f11(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*     */                   return;
/* 312 */                 out.write("\n</div>\n");
/* 313 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 314 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 318 */             if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 319 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/*     */             }
/*     */             else {
/* 322 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 323 */               out.write(10);
/* 324 */               out.write(10);
/* 325 */               out.write(10);
/* 326 */               out.write(10);
/* 327 */               out.write(32);
/*     */               
/* 329 */               IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 330 */               _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 331 */               _jspx_th_c_005fif_005f12.setParent(null);
/*     */               
/* 333 */               _jspx_th_c_005fif_005f12.setTest("${MyPageForm.pageType==\"businesspage\"}");
/* 334 */               int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 335 */               if (_jspx_eval_c_005fif_005f12 != 0) {
/*     */                 for (;;) {
/* 337 */                   out.write("\n <div id=\"WidgetsDiv\" style=\"margin-bottom: 10px;width:400px\">\n\t \t");
/*     */                   
/* 339 */                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 340 */                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 341 */                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f12);
/*     */                   
/* 343 */                   _jspx_th_c_005fif_005f13.setTest("${Action!=\"createDashBoard\"}");
/* 344 */                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 345 */                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*     */                     for (;;) {
/* 347 */                       out.write("\n\t\t\t<form name=\"MyPageForm\" method=\"get\" action=\"/MyPage.do\">\n\t\t\t<input type=\"hidden\" name=\"pageid\" value=\"");
/* 348 */                       out.print(request.getAttribute("pageid"));
/* 349 */                       out.write("\" />\n\t\t\t<input type=\"hidden\" name=\"method\" value=\"addWidgets\"/>\n\t\t\t");
/* 350 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 351 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 355 */                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 356 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*     */                   }
/*     */                   
/* 359 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 360 */                   out.write("\n\t\t<input type=\"hidden\" name=\"selectedWidgets\" />\n\t \t<div style=\"margin:10px;width:100%\" >\n\t \t\t\t<div>\n\t \t\t\t\t<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" height=\"70\" ");
/* 361 */                   if (_jspx_meth_c_005fif_005f14(_jspx_th_c_005fif_005f12, _jspx_page_context))
/*     */                     return;
/* 363 */                   out.write(" style=\"margin-bottom: 10px;\">\n\t \t\t\t\t  <tr>\n\t \t\t\t\t\t<td  align=\"left\" valign=\"top\" width=\"100%\" style=\"width: 751px;\">\n\t \t\t\t\t\t\t<table class=\"lrtbdarkborder\" border=\"0\" cellspacing=\"0\" cellpadding=\"5\" width=\"80%\">\n\t \t\t\t\t\t\t<tr>\n\t \t\t\t\t\t\t\t<td class=\"tableheadingbborder\"  style=\"width: 751px;\">\n\t \t\t\t\t\t\t\t");
/* 364 */                   out.print(FormatUtil.getString("am.mypage.add.widgets.text"));
/* 365 */                   out.write("\n\t \t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t</tr>\n\t \t\t\t\t\t\t<tr class=\"bodytext\" >\n\t \t\t\t\t\t\t\t<td style=\"width: 751px;\">\n\t \t\t\t\t\t\t\t     <input type=\"checkbox\" name=\"selectedWidgetsCheckbox\" value=\"2\" >&nbsp;<span style='font-size:10pt;'>");
/* 366 */                   out.print(FormatUtil.getString("am.webclient.mypage.widgettype.businessmetric"));
/* 367 */                   out.write("</span\t>\n\t \t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t</tr>\n\t \t\t\t\t\t\t<tr class=\"bodytext\">\n\t \t\t\t\t\t\t\t<td style=\"width: 751px;\">\n\t\t\t\t\t\t\t\t     <input type=\"checkbox\" name=\"selectedWidgetsCheckbox\" value=\"1\" >&nbsp;<span style='font-size:10pt;'>");
/* 368 */                   out.print(FormatUtil.getString("am.mypage.widgettypes.topn.monitors.text"));
/* 369 */                   out.write("</span>\n\t \t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr class=\"bodytext\">\n                                                                <td style=\"width: 751px;\">\n                                                                      <input type=\"checkbox\" name=\"selectedWidgetsCheckbox\" value=\"3\" >&nbsp;<span style='font-size:10pt'>");
/* 370 */                   out.print(FormatUtil.getString("am.mypage.widgettypes.configurationdata.text"));
/* 371 */                   out.write("</span>\n                                                              </td>\n                                                         </tr>\n\t\t\t\t\t\t\t<tr class=\"bodytext\">                    \n                                          \t\t\t<td style=\"width: 751px;\">\n                                                                      <input type=\"checkbox\" name=\"selectedWidgetsCheckbox\" value=\"301\" >&nbsp;<span style='font-size:10pt'>");
/* 372 */                   out.print(FormatUtil.getString("am.mypage.widgettypes.url.include.text"));
/* 373 */                   out.write("</span>\n                                                              </td>\n                                                         </tr>\n\t \t\t\t\t\t\t<tr class=\"bodytext\">\n\t\t\t\t\t\t\t\t<td style=\"width: 751px;\">\n\t\t\t\t\t\t\t\t     <input type=\"checkbox\" name=\"selectedWidgetsCheckbox\" value=\"302\" >&nbsp;<span style='font-size:10pt;'>");
/* 374 */                   out.print(FormatUtil.getString("am.mypage.widgettypes.bookmark.text"));
/* 375 */                   out.write("</span>\n\t\t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t</tr>\n\t \t\t\t\t\t\t<tr class=\"bodytext\">\n\t\t\t\t\t\t\t\t<td style=\"width: 751px;\">\n\t\t\t\t\t\t\t\t     <input type=\"checkbox\" name=\"selectedWidgetsCheckbox\" value=\"303\" >&nbsp;<span style='font-size:10pt;'>");
/* 376 */                   out.print(FormatUtil.getString("am.mypage.widgettypes.customhtml.text"));
/* 377 */                   out.write("</span>\n\t\t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t</tr>\n\t \t\t\t\t\t\t</table>\n\t \t\t\t\t\t</td>\n\n\t \t\t\t\t  </tr>\n\t \t\t\t  </table>\n\n\t \t\t\t</div>\n\t \t\t</div>\n\t         ");
/* 378 */                   if (_jspx_meth_c_005fif_005f15(_jspx_th_c_005fif_005f12, _jspx_page_context))
/*     */                     return;
/* 380 */                   out.write("\n\t </div>\n");
/* 381 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 382 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 386 */               if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 387 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/*     */               }
/*     */               else {
/* 390 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 391 */                 out.write(10);
/* 392 */                 out.write(10);
/* 393 */                 out.write(10);
/*     */                 
/* 395 */                 IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 396 */                 _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 397 */                 _jspx_th_c_005fif_005f16.setParent(null);
/*     */                 
/* 399 */                 _jspx_th_c_005fif_005f16.setTest("${Action!=\"createDashBoard\"}");
/* 400 */                 int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 401 */                 if (_jspx_eval_c_005fif_005f16 != 0) {
/*     */                   for (;;) {
/* 403 */                     out.write("\n<table width=\"100%\">\n\t <tr>\n\t<td align=\"center\">\n\n\t\t        ");
/*     */                     
/* 405 */                     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 406 */                     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 407 */                     _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fif_005f16);
/*     */                     
/* 409 */                     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 410 */                     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 411 */                     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */                       for (;;) {
/* 413 */                         out.write("\n\t\t\t<input type=\"button\" class=\"buttons btn_highlt\" onClick=\"javascript:alertUser();\" value=\"&nbsp;&nbsp;");
/* 414 */                         out.print(FormatUtil.getString("am.mypage.addwidgets.add.button.text"));
/* 415 */                         out.write("&nbsp;&nbsp;\" >\n\t\t        ");
/* 416 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 417 */                         if (evalDoAfterBody != 2)
/*     */                           break;
/*     */                       }
/*     */                     }
/* 421 */                     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 422 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*     */                     }
/*     */                     
/* 425 */                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 426 */                     out.write("\n\t\t        ");
/*     */                     
/* 428 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 429 */                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 430 */                     _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f16);
/*     */                     
/* 432 */                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 433 */                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 434 */                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */                       for (;;) {
/* 436 */                         out.write("\n\t\t        <input type=\"button\" class=\"buttons btn_highlt\" onClick=\"javascript:validateAndSubmit();\" value=\"&nbsp;&nbsp;");
/* 437 */                         out.print(FormatUtil.getString("am.mypage.addwidgets.add.button.text"));
/* 438 */                         out.write("&nbsp;&nbsp;\" >\n\t\t        ");
/* 439 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 440 */                         if (evalDoAfterBody != 2)
/*     */                           break;
/*     */                       }
/*     */                     }
/* 444 */                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 445 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*     */                     }
/*     */                     
/* 448 */                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 449 */                     out.write("\n\t\t\t<input type=\"button\" class=\"buttons btn_link\" onClick=\"javascript:window.close();\" value=\"&nbsp;");
/* 450 */                     out.print(FormatUtil.getString("am.mypage.addwidgets.window.close.text"));
/* 451 */                     out.write("&nbsp;\" >\n\n\t</td>\n\t</tr>\n\t </table>\n\t");
/* 452 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 453 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 457 */                 if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 458 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/*     */                 }
/*     */                 else {
/* 461 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 462 */                   out.write(10);
/*     */                   
/* 464 */                   IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 465 */                   _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 466 */                   _jspx_th_c_005fif_005f17.setParent(null);
/*     */                   
/* 468 */                   _jspx_th_c_005fif_005f17.setTest("${Action!=\"createDashBoard\"}");
/* 469 */                   int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 470 */                   if (_jspx_eval_c_005fif_005f17 != 0) {
/*     */                     for (;;) {
/* 472 */                       out.write("\n</td>\n<td style=\"padding-top:9px;\"  width=\"40%\" valign=\"top\">\n<table width=\"95%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t\t<tr>\n\t\t\t<td class=\"helpCardHdrTopLeft\"/>\n\t\t\t<td class=\"helpCardHdrTopBg\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t\t\t<tr>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/* 473 */                       out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/* 474 */                       out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td>\n\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t</table></td>\n\t\t\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t<td valign=\"top\">\n\t\t\t<!--//include your Helpcard template table here..-->\n\n\n\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n    <tr>\n    <td style=\"padding-top: 10px;\" class=\"boxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n      <tr>\n          <td class=\"txtSpace\">\n           ");
/* 475 */                       out.print(FormatUtil.getString("am.mypage.addwidgets.helpcard.header.text"));
/* 476 */                       out.write("\n          </td>\n      </tr>\n      <tr>\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n        <tr>\n          <td class=\"hCardInnerTopLeft\"/>\n          <td class=\"hCardInnerTopBg\"/>\n          <td class=\"hCardInnerTopRight\"/>\n        </tr>\n        <tr>\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n                <td class=\"hCardInnerBoxBg\">\n                  ");
/* 477 */                       out.print(FormatUtil.getString("am.mypage.addwidgets.helpcard.text"));
/* 478 */                       out.write("\n            </td>\n          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n        </tr>\n\n      </table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td class=\"helpCardMainBtmLeft\"/>\n\t\t\t<td class=\"helpCardMainBtmBg\"/>\n\t\t\t<td class=\"helpCardMainBtmRight\"/>\n\n\t\t\t</tr>\n\t\t\t</table>\n</td>\n</tr>\n</table>\n\n</body>\n</html>\n");
/* 479 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 480 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 484 */                   if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 485 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/*     */                   }
/*     */                   else {
/* 488 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 489 */                     out.write(10);
/*     */                   }
/* 491 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 492 */         out = _jspx_out;
/* 493 */         if ((out != null) && (out.getBufferSize() != 0))
/* 494 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 495 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 498 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 504 */     PageContext pageContext = _jspx_page_context;
/* 505 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 507 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 508 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 509 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 511 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 513 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 514 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 515 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 516 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 517 */       return true;
/*     */     }
/* 519 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 520 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 525 */     PageContext pageContext = _jspx_page_context;
/* 526 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 528 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 529 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 530 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 532 */     _jspx_th_c_005fout_005f1.setValue("${widgetsFile}");
/* 533 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 534 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 535 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 536 */       return true;
/*     */     }
/* 538 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 539 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 544 */     PageContext pageContext = _jspx_page_context;
/* 545 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 547 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 548 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 549 */     _jspx_th_c_005fif_005f2.setParent(null);
/*     */     
/* 551 */     _jspx_th_c_005fif_005f2.setTest("${MyPageForm.pageType!=\"businesspage\"}");
/* 552 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 553 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 555 */         out.write("\nwindow.addEvent('load',function(){\n\t");
/* 556 */         if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 557 */           return true;
/* 558 */         out.write(10);
/* 559 */         out.write(9);
/* 560 */         if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 561 */           return true;
/* 562 */         out.write(10);
/* 563 */         out.write(9);
/* 564 */         if (_jspx_meth_c_005fif_005f6(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 565 */           return true;
/* 566 */         out.write("\n});\n");
/* 567 */         out.write(10);
/* 568 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 569 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 573 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 574 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 575 */       return true;
/*     */     }
/* 577 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 578 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 583 */     PageContext pageContext = _jspx_page_context;
/* 584 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 586 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 587 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 588 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 590 */     _jspx_th_c_005fif_005f3.setTest("${action == 'createDashboard'}");
/* 591 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 592 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */       for (;;) {
/* 594 */         out.write("\n\t\twidgetTree();\n\t\tenableColumnWidthBoxes();\n\t\tfillDefaultColumnWidth();\n\t\tsetTreeTDWidth();\n\t");
/* 595 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 596 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 600 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 601 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 602 */       return true;
/*     */     }
/* 604 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 605 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 610 */     PageContext pageContext = _jspx_page_context;
/* 611 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 613 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 614 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 615 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 617 */     _jspx_th_c_005fif_005f4.setTest("${action == 'editDashboard'}");
/* 618 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 619 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*     */       for (;;) {
/* 621 */         out.write("\n\t\tenableColumnWidthBoxes();\n\t\t");
/* 622 */         if (_jspx_meth_c_005fif_005f5(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 623 */           return true;
/* 624 */         out.write(10);
/* 625 */         out.write(9);
/* 626 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 627 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 631 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 632 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 633 */       return true;
/*     */     }
/* 635 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 636 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 641 */     PageContext pageContext = _jspx_page_context;
/* 642 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 644 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 645 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 646 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_c_005fif_005f4);
/*     */     
/* 648 */     _jspx_th_c_005fif_005f5.setTest("${isDefault}");
/* 649 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 650 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*     */       for (;;) {
/* 652 */         out.write(" document.DashboardForm.dashboardName.readOnly=true; ");
/* 653 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 654 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 658 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 659 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 660 */       return true;
/*     */     }
/* 662 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 663 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 668 */     PageContext pageContext = _jspx_page_context;
/* 669 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 671 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 672 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 673 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 675 */     _jspx_th_c_005fif_005f6.setTest("${'true' == 'true'}");
/* 676 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 677 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*     */       for (;;) {
/* 679 */         out.write("\n\t\twidgetTree();\n\t\tsetTreeTDWidth();\n\t");
/* 680 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 681 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 685 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 686 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 687 */       return true;
/*     */     }
/* 689 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 690 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 695 */     PageContext pageContext = _jspx_page_context;
/* 696 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 698 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 699 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 700 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f7);
/*     */     
/* 702 */     _jspx_th_c_005fout_005f2.setValue("${pagename}");
/* 703 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 704 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 705 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 706 */       return true;
/*     */     }
/* 708 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 709 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f10(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 714 */     PageContext pageContext = _jspx_page_context;
/* 715 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 717 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 718 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 719 */     _jspx_th_c_005fif_005f10.setParent((Tag)_jspx_th_c_005fif_005f8);
/*     */     
/* 721 */     _jspx_th_c_005fif_005f10.setTest("${Action!=\"createDashBoard\"}");
/* 722 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 723 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*     */       for (;;) {
/* 725 */         out.write(" width=\"100%\" ");
/* 726 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 727 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 731 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 732 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 733 */       return true;
/*     */     }
/* 735 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 736 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f11(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 741 */     PageContext pageContext = _jspx_page_context;
/* 742 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 744 */     IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 745 */     _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 746 */     _jspx_th_c_005fif_005f11.setParent((Tag)_jspx_th_c_005fif_005f8);
/*     */     
/* 748 */     _jspx_th_c_005fif_005f11.setTest("${Action!=\"createDashBoard\"}");
/* 749 */     int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 750 */     if (_jspx_eval_c_005fif_005f11 != 0) {
/*     */       for (;;) {
/* 752 */         out.write("\n\t</form>\n\t");
/* 753 */         int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 754 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 758 */     if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 759 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 760 */       return true;
/*     */     }
/* 762 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 763 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f14(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 768 */     PageContext pageContext = _jspx_page_context;
/* 769 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 771 */     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 772 */     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 773 */     _jspx_th_c_005fif_005f14.setParent((Tag)_jspx_th_c_005fif_005f12);
/*     */     
/* 775 */     _jspx_th_c_005fif_005f14.setTest("${Action!=\"createDashBoard\"}");
/* 776 */     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 777 */     if (_jspx_eval_c_005fif_005f14 != 0) {
/*     */       for (;;) {
/* 779 */         out.write(" width=\"100%\" ");
/* 780 */         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 781 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 785 */     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 786 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 787 */       return true;
/*     */     }
/* 789 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 790 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f15(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 795 */     PageContext pageContext = _jspx_page_context;
/* 796 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 798 */     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 799 */     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 800 */     _jspx_th_c_005fif_005f15.setParent((Tag)_jspx_th_c_005fif_005f12);
/*     */     
/* 802 */     _jspx_th_c_005fif_005f15.setTest("${Action!=\"createDashBoard\"}");
/* 803 */     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 804 */     if (_jspx_eval_c_005fif_005f15 != 0) {
/*     */       for (;;) {
/* 806 */         out.write("\n\t \t</form>\n\t \t");
/* 807 */         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 808 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 812 */     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 813 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 814 */       return true;
/*     */     }
/* 816 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 817 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MyPage_005fAddWidgets_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */