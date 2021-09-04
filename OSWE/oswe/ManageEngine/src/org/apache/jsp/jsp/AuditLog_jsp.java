/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class AuditLog_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  25 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  26 */   static { _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  35 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  39 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  40 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  41 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  52 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  55 */     JspWriter out = null;
/*  56 */     Object page = this;
/*  57 */     JspWriter _jspx_out = null;
/*  58 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  62 */       response.setContentType("text/html");
/*  63 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  65 */       _jspx_page_context = pageContext;
/*  66 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  67 */       ServletConfig config = pageContext.getServletConfig();
/*  68 */       session = pageContext.getSession();
/*  69 */       out = pageContext.getOut();
/*  70 */       _jspx_out = out;
/*     */       
/*  72 */       out.write("<!--$Id$-->\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n\n");
/*  73 */       out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/*  74 */       out.write("\n\n\n<html>\n<head>\n <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n<title>");
/*  75 */       out.print(FormatUtil.getString("am.audit.auditReport"));
/*  76 */       out.write("</title> ");
/*  77 */       out.write("\n<style>\nbody {\n\tfont-family: Arial, Helvetica, sans-serif;\n\tpadding: 0;\n\tmargin: 0;\n}\n.ar-header {\n\tfont-size: 16px;\n    background: #f7f7f7;\n    color: #777;\n    padding: 15px 10px;\n    margin: 0 0 25px;\n    border-bottom: 1px dotted #d3d3d3;\n}\n.ar-timeline-list {\n\tpadding: 0;\n\tmargin: 0 auto;\n\twidth: 90%;\n\tposition: relative;\n\tz-index:1;\n}\n.ar-timeline-list:before {\n\tcontent: '';\n\tposition: absolute;\n\twidth: 1px;\n\tbackground-color: #e3e3e3;\n\ttop: 10px;\n\tbottom: 0;\n\tleft: 42px;\n\tz-index:0;\n}\n.ar-timeline-list > li {\n\tpadding: 0;\n\tmargin: 20px 0 20px 70px;\n\tlist-style: none;\n\tposition: relative;\n}\n.ar-timeline-list > li:before {\n\tcontent: '';\n\tposition: absolute;\n\twidth: 5px;\n\theight: 5px;\n\tbackground-color: #c5c7c5;\n\tborder-radius: 5px;\n\ttop: 6px;\n\tleft: -30px;\n}\n.ar-timeline-list > li:after {\n\tcontent: '';\n\tposition: absolute;\n\twidth: 13px;\n\theight: 13px;\n\tborder: 2px solid #c5c7c5;\n\tborder-radius: 13px;\n\ttop: 0px;\n\tleft: -36px;\n}\n.green-box h2 {\n\tpadding: 10px 20px;\n\tmargin: 0;\n\tcolor: #fff;\n\ttext-align: center;\n");
/*  78 */       out.write("\tvertical-align: middle;\n\tfont-size: 16px;\n\tfont-weight: 100;\n\tbackground-color: #1ab394;\n\tborder: 5px solid #fff;\n\tdisplay: inline-block;\n\tposition: relative;\n}\n.green-box {\n\tposition: relative;\n\tz-index:1;\n}\n.green-box:after {\n\tcontent: '';\n\tposition: absolute;\n\twidth: 100%;\n\theight: 1px;\n\tbackground-color: #e3e3e3;\n\ttop: 25px;\n\tleft: 0;\n\tdisplay: block;\n\tz-index:-1;\n}\n.ar-list-msg ul {\n\tpadding: 10px 19px;\n}\n.ar-list-msg li {\n\tcolor:#333 !important;\n\tpadding: 2px 0;\n\tmargin:5px 0;\n\tfont-size: 12px;\n\tline-height:19px;\n\tlist-style-type:square;\n}\n.ar-time {\n\tdisplay: inline-block;\n\tcolor: #159f83;\n\tfont-size: 14px;\n\tfont-weight: 600;\n}\n</style>\n<script>\n$(document).ready(function()\n{ \n\tloadAuditLog();\n\tvar scrollEvent = false;\n\tjQuery(window).scroll(function(){\n\t\tif(jQuery(window).scrollTop() + jQuery(window).height() > jQuery(document).height() - 100){\n\t\t\t  if(scrollEvent){\n\t\t\t\t  return false;\n\t\t\t  }else{\n\t\t\t\t  scrollEvent = true;\n\t\t\t\t  if(continueLoading == true){\n\t\t\t\t\t  loadAuditLog();\n\t\t\t\t\t  scrollEvent = false\n");
/*  79 */       out.write("\t\t\t\t  }\n\t\t\t   }\n\t\t}\n\t});\n});\nvar continueLoading = true;\nvar startIndex=0;\nvar latestDate=\"\";\nvar latestTime=\"\";\nfunction loadAuditLog() {\n\tvar resourceid = \"");
/*  80 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  82 */       out.write("\";\n\tvar dataString = \"&method=loadAuditLog&resourceid=\"+resourceid+\"&startIndex=\"+startIndex; //No I18N\n\t$.ajax({\n\t\t\ttype:\"POST\", //No I18N\n\t\t\turl:\"/auditLogAction.do\",//No I18N\n\t\t\tdata: dataString,\n\t\t\tsuccess:function(response){\n\t\t\t\tvar eachResponse = response.auditLogMsg;\n\t\t\t\tparseAndDisplay(eachResponse);\n\t\t\t\tstartIndex += 25;\n\t\t\t}\n\t});\t\t\n}\n\nfunction parseAndDisplay(eachResponse) {\n    \tvar finalToAppend = \"\";\n\t\tvar count = 0;\n\t\t$.each(eachResponse, function(dayName,timeArr) {\n\t\tvar fullString = \"\";\n\t\tvar dayNameToAppend=\"\";\n\t\tif(latestDate != dayName) {\n\t\t\tdayNameToAppend =  \"<div íd = \\\"\"+dayName+\"\\\"class=\\\"green-box\\\"> <h2>\"+dayName+\"</h2> </div>\";\n\t\t\tlatestDate = dayName;\n\t\t}\n\t\tvar idForMsgList = \"\";\n\t\t$.each(timeArr, function(timeName,msgArr) {\n\t\t\tvar timeValueToAppend = \"<div class=\\\"ar-time\\\">\"+timeName+\"</div>  \";\n\t\t\tvar idForMsgList = dayName+\"_\"+timeName;\n\t\t\tvar msgList = \"\";\n\t\t\tfor(var k in msgArr) {\n\t\t\t\tmsgList += \"<li>\"+msgArr[k]+\" </li>\"\n\t\t\t\tcount +=1;\n\t\t\t}\n\t\t\tidForMsgList = idForMsgList.replace(/:| /g,\"_\");\n");
/*  83 */       out.write("\t\t\tif(latestTime == idForMsgList){\n\t\t\t\t$(\"#\"+idForMsgList).append(msgList);\n\t\t\t}\n\t\t\telse{\n\t\t\t\tfullString += \"<li> <div class=\\\"ar-list-msg\\\"> \"+ timeValueToAppend+ \"<ul id=\\\"\"+idForMsgList+\"\\\">\"+ msgList +\" </ul></div> </li>\";\t\n\t\t\t\tlatestTime = idForMsgList;\n\t\t\t}\n\t\t});\n\t\tfinalToAppend += dayNameToAppend+fullString;\n\t\t});\n\t\tif(count < 25){\n\t\t\tcontinueLoading = false;\n\t\t}\n\t$(\"#auditDetailsList\").append(finalToAppend);\n}\n</script>\n</head>\n<body>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n    <tr>\n      <td valign=\"middle\" class=\"bcsign breadcrumb_btm_space\"><a href=\"/showapplication.do?method=showApplication&haid=");
/*  84 */       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */         return;
/*  86 */       out.write("\" class=\"bcinactive\" > < ");
/*  87 */       out.print(FormatUtil.getString("am.audit.backtomonitordetails"));
/*  88 */       out.write(" </a>  \n\t  </td>\n\t</tr>\n</table>\t\n<div class=\"ar-container\">\n  <div class=\"ar-header\">");
/*  89 */       out.print(FormatUtil.getString("am.audit.auditReport"));
/*  90 */       out.write(32);
/*  91 */       out.write(45);
/*  92 */       out.write(32);
/*  93 */       if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*     */         return;
/*  95 */       out.write("</div>\n  <div id=\"testing\" class=\"ar-timeline\">\n    <ul id=\"auditDetailsList\" class=\"ar-timeline-list\">\n    </ul>\n  </div>\n</div>\n</body>\n</html>\n\n\n");
/*     */     } catch (Throwable t) {
/*  97 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/*  98 */         out = _jspx_out;
/*  99 */         if ((out != null) && (out.getBufferSize() != 0))
/* 100 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 101 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 104 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 110 */     PageContext pageContext = _jspx_page_context;
/* 111 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 113 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 114 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 115 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 117 */     _jspx_th_c_005fout_005f0.setValue("${resourceidlist}");
/* 118 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 119 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 120 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 121 */       return true;
/*     */     }
/* 123 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 129 */     PageContext pageContext = _jspx_page_context;
/* 130 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 132 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 133 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 134 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 136 */     _jspx_th_c_005fout_005f1.setValue("${haid}");
/* 137 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 138 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 139 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 140 */       return true;
/*     */     }
/* 142 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 143 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 148 */     PageContext pageContext = _jspx_page_context;
/* 149 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 151 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 152 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 153 */     _jspx_th_c_005fout_005f2.setParent(null);
/*     */     
/* 155 */     _jspx_th_c_005fout_005f2.setValue("${auditName}");
/* 156 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 157 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 158 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 159 */       return true;
/*     */     }
/* 161 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 162 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AuditLog_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */