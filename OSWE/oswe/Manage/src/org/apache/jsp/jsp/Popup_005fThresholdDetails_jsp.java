/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.URLEncoder;
/*     */ import java.sql.ResultSet;
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
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ 
/*     */ public final class Popup_005fThresholdDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  37 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  41 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  46 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  50 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  51 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  52 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  53 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  60 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  63 */     JspWriter out = null;
/*  64 */     Object page = this;
/*  65 */     JspWriter _jspx_out = null;
/*  66 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  70 */       response.setContentType("text/html");
/*  71 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  73 */       _jspx_page_context = pageContext;
/*  74 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  75 */       ServletConfig config = pageContext.getServletConfig();
/*  76 */       session = pageContext.getSession();
/*  77 */       out = pageContext.getOut();
/*  78 */       _jspx_out = out;
/*     */       
/*  80 */       out.write("<!--$Id$-->\n<html>\n<head>\n\n\n\n\n\n\n\n\n\n\n<title>Threshold Profile Details</title>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n\n\n<link href=\"/images/");
/*  81 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  83 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n \n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<script language=\"JavaScript1.2\">\n\tfunction fnFramechange(optvalue)\n\t{\n\n\treturn (window.iframe_thresh.location.href=optvalue);\n\n\t}\n</script>\n</head>\n<body leftmargin=\"5\" topmargin=\"5\" marginwidth=\"5\" marginheight=\"5\">\n\n<form name=\"form1\">\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"1\" class=\"reportperiod\">\n    <tr class=\"reportperiodbg\"> \n      <td width=\"32%\" class=\"bodytext\">");
/*  84 */       out.print(FormatUtil.getString("am.webclient.configurealert.selectprofile"));
/*  85 */       out.write("&nbsp;:</td>\n    <td width=\"68%\">\n\t  <select name=\"select\" class=\"formtext\" onChange=\"fnFramechange(document.form1.select.options[this.selectedIndex].value);\">\n");
/*     */       
/*  87 */       String resourceid = request.getParameter("resourceid");
/*  88 */       String attributeid = request.getParameter("attributeid");
/*  89 */       String redirectTo = "";
/*  90 */       boolean isStringAttribute = (request.getParameter("isStringAttribute") != null) && (request.getParameter("isStringAttribute").equals("true"));
/*  91 */       if (request.getParameter("redirectTo") != null)
/*     */       {
/*  93 */         redirectTo = URLEncoder.encode(request.getParameter("redirectTo"));
/*     */       }
/*  95 */       else if (request.getParameter("redirectto") != null)
/*     */       {
/*  97 */         redirectTo = URLEncoder.encode(request.getParameter("redirectto"));
/*     */       }
/*  99 */       String defaultID = "";
/* 100 */       String param = request.getParameter("thresholdid");
/* 101 */       if ((param != null) && (!param.equals("Reset")))
/*     */       {
/* 103 */         defaultID = param;
/*     */       }
/* 105 */       String methodd = "getThresholdDetails";
/*     */       try
/*     */       {
/* 108 */         AMConnectionPool pool = AMConnectionPool.getInstance();
/*     */         
/* 110 */         String tmpQ = "select ID,NAME from AM_THRESHOLDCONFIG where ID <> 1 AND ID <> 2  AND (DESCRIPTION <>'##Threshod for URL##') AND NAME NOT IN ('Marker THRESHOLD','Availability Threshold','Health Threshold')";
/* 111 */         if (isStringAttribute)
/*     */         {
/* 113 */           tmpQ = tmpQ + " and TYPE=3";
/* 114 */           methodd = "getPatternDetails";
/*     */         }
/*     */         else
/*     */         {
/* 118 */           tmpQ = tmpQ + " and TYPE!=3";
/*     */         }
/* 120 */         ResultSet result = AMConnectionPool.executeQueryStmt(tmpQ);
/* 121 */         while (result.next())
/*     */         {
/* 123 */           String selected = "";
/* 124 */           if (defaultID.equals(""))
/*     */           {
/* 126 */             defaultID = String.valueOf(result.getInt("ID"));
/*     */           }
/* 128 */           if (("" + result.getInt("ID")).equals(defaultID))
/*     */           {
/* 130 */             selected = "selected";
/*     */           }
/*     */           
/* 133 */           out.write(" \n\t\t ");
/*     */           
/* 135 */           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 136 */           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 137 */           _jspx_th_c_005fchoose_005f0.setParent(null);
/* 138 */           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 139 */           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */             for (;;) {
/* 141 */               out.write("\n\t\t  ");
/*     */               
/* 143 */               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 144 */               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 145 */               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */               
/* 147 */               _jspx_th_c_005fwhen_005f0.setTest("${param.popupNewThreshold=='true'}");
/* 148 */               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 149 */               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */                 for (;;) {
/* 151 */                   out.write("\n\t\t  \n\t         \t<option value=\"/showActionProfiles.do?method=");
/* 152 */                   out.print(methodd);
/* 153 */                   out.write("&frompopupNewThreshold=true&delete=true&resourceid=");
/* 154 */                   out.print(resourceid);
/* 155 */                   out.write("&attributeid=");
/* 156 */                   out.print(attributeid);
/* 157 */                   out.write("&redirectTo=");
/* 158 */                   out.print(redirectTo);
/* 159 */                   out.write("&thresholdid=");
/* 160 */                   out.print(result.getInt("ID"));
/* 161 */                   out.write(34);
/* 162 */                   out.write(32);
/* 163 */                   out.print(selected);
/* 164 */                   out.write(62);
/* 165 */                   out.print(result.getString("NAME"));
/* 166 */                   out.write("</option>\n\t         ");
/* 167 */                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 168 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 172 */               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 173 */                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */               }
/*     */               
/* 176 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 177 */               out.write("\n\t         ");
/*     */               
/* 179 */               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 180 */               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 181 */               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 182 */               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 183 */               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */                 for (;;) {
/* 185 */                   out.write("\n\t         \t<option value=\"/showActionProfiles.do?method=");
/* 186 */                   out.print(methodd);
/* 187 */                   out.write("&thresholdid=");
/* 188 */                   out.print(result.getInt("ID"));
/* 189 */                   out.write(34);
/* 190 */                   out.write(32);
/* 191 */                   out.print(selected);
/* 192 */                   out.write(62);
/* 193 */                   out.print(FormatUtil.getString(result.getString("NAME")));
/* 194 */                   out.write("</option>\n\t         ");
/* 195 */                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 196 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 200 */               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 201 */                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */               }
/*     */               
/* 204 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 205 */               out.write("\n\t         ");
/* 206 */               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 207 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 211 */           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 212 */             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*     */           }
/*     */           
/* 215 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 216 */           out.write(10);
/*     */         }
/*     */         
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 222 */         e.printStackTrace();
/*     */       }
/*     */       
/* 225 */       out.write("\n\t\t\n      </select></td>\n  </tr>  \n</table>\n  <img src=\"/images/spacer.gif\" width=\"1\" height=\"10\"> \n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tr> \n    <td height=\"18\"  class=\"bodytext\">\n\t");
/*     */       
/* 227 */       if (!defaultID.equals(""))
/*     */       {
/*     */ 
/* 230 */         out.write("\n\t\t ");
/*     */         
/* 232 */         ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 233 */         _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 234 */         _jspx_th_c_005fchoose_005f1.setParent(null);
/* 235 */         int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 236 */         if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*     */           for (;;) {
/* 238 */             out.write("\n\t\t ");
/*     */             
/* 240 */             WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 241 */             _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 242 */             _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*     */             
/* 244 */             _jspx_th_c_005fwhen_005f1.setTest("${param.popupNewThreshold=='true'}");
/* 245 */             int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 246 */             if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*     */               for (;;) {
/* 248 */                 out.write("\n\t\t\t<iframe name=\"iframe_thresh\" id=\"iframe_thresh\" frameborder=\"0\" src=\"/showActionProfiles.do?method=");
/* 249 */                 out.print(methodd);
/* 250 */                 out.write("&frompopupNewThreshold=true&delete=true&resourceid=");
/* 251 */                 out.print(resourceid);
/* 252 */                 out.write("&attributeid=");
/* 253 */                 out.print(attributeid);
/* 254 */                 out.write("&redirectTo=");
/* 255 */                 out.print(redirectTo);
/* 256 */                 out.write("&thresholdid=");
/* 257 */                 out.print(defaultID);
/* 258 */                 out.write("\" width=\"100%\" height=\"320\" vspace=\"0\" marginheight=\"0\" marginwidth=\"0\"></iframe>\n\t\t ");
/* 259 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 260 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 264 */             if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 265 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*     */             }
/*     */             
/* 268 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 269 */             out.write("\n\t\t ");
/*     */             
/* 271 */             OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 272 */             _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 273 */             _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 274 */             int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 275 */             if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*     */               for (;;) {
/* 277 */                 out.write("\n\t\t\t<iframe name=\"iframe_thresh\" id=\"iframe_thresh\" frameborder=\"0\" src=\"/showActionProfiles.do?method=");
/* 278 */                 out.print(methodd);
/* 279 */                 out.write("&thresholdid=");
/* 280 */                 out.print(defaultID);
/* 281 */                 out.write("\" width=\"100%\" height=\"320\" vspace=\"0\" marginheight=\"0\" marginwidth=\"0\"></iframe>\n\t\t ");
/* 282 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 283 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 287 */             if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 288 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*     */             }
/*     */             
/* 291 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 292 */             out.write("\n\t\t ");
/* 293 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 294 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 298 */         if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 299 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*     */         }
/*     */         
/* 302 */         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 303 */         out.write(10);
/* 304 */         out.write(9);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 310 */         out.write("\n\t<span class=\"bodytextbold\">");
/* 311 */         out.print(FormatUtil.getString("am.webclient.threshold.nothresholdprofiles"));
/* 312 */         out.write("</span>\n\t");
/*     */       }
/*     */       
/*     */ 
/* 316 */       out.write("\n\t</td>\n  </tr>\n  </table>\n</form>\n");
/* 317 */       response.setContentType("text/html;charset=UTF-8");
/* 318 */       out.write("\n</body>\n</html>\n");
/*     */     } catch (Throwable t) {
/* 320 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 321 */         out = _jspx_out;
/* 322 */         if ((out != null) && (out.getBufferSize() != 0))
/* 323 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 324 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 327 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 333 */     PageContext pageContext = _jspx_page_context;
/* 334 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 336 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 337 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 338 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 340 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 342 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 343 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 344 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 345 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 346 */       return true;
/*     */     }
/* 348 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 349 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Popup_005fThresholdDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */