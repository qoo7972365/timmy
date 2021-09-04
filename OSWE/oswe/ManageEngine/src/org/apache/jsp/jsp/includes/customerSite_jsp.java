/*     */ package org.apache.jsp.jsp.includes;
/*     */ 
/*     */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
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
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.IterateTag;
/*     */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class customerSite_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  32 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  48 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  52 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  56 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  57 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  58 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  59 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  63 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  64 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*  65 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  66 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  67 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  68 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  75 */     HttpSession session = null;
/*     */     
/*     */ 
/*  78 */     JspWriter out = null;
/*  79 */     Object page = this;
/*  80 */     JspWriter _jspx_out = null;
/*  81 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  85 */       response.setContentType("text/html;charset=UTF-8");
/*  86 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  88 */       _jspx_page_context = pageContext;
/*  89 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  90 */       ServletConfig config = pageContext.getServletConfig();
/*  91 */       session = pageContext.getSession();
/*  92 */       out = pageContext.getOut();
/*  93 */       _jspx_out = out;
/*     */       
/*  95 */       out.write("<!-- $Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  96 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  98 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"> </SCRIPT>\n\n\n\n");
/*     */       
/*     */ 
/* 101 */       ArrayList dynamicSites = AlarmUtil.getSiteMonitorGroups();
/* 102 */       if (dynamicSites != null)
/*     */       {
/* 104 */         request.setAttribute("dynamicSites", dynamicSites);
/*     */       }
/*     */       
/* 107 */       String resType = request.getParameter("resType");
/* 108 */       String resId = request.getParameter("resId");
/* 109 */       String attrId = request.getParameter("attrId");
/* 110 */       String isAvailablity = request.getParameter("avail");
/* 111 */       String isHealth = request.getParameter("health");
/* 112 */       if (isAvailablity == null)
/*     */       {
/* 114 */         isAvailablity = "false";
/*     */       }
/* 116 */       if (isHealth == null)
/*     */       {
/* 118 */         isHealth = "false";
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 123 */       out.write("\n\n\t<script>\n\tfunction loadSite(object)\n\t{\n\t//var formCustomerId = document.AMActionForm.organization.value;\n\t//var formCustomerId = object.form.name;\n\t//alert(formCustomerId);\n\tvar formCustomerId = object.value;\n\t//alert(object.name);\n\t//alert(object.value);\n\tvar site;\n\tvar siteId;\n\tvar customerId;\n\tvar rowCount=0;\n\tvar siteBoxObj =document.getElementById('siteName');\n\tsiteBoxObj.options.length=0; //Reset site before loading\n\tsiteBoxObj.options[rowCount++] = new Option('-Select Site-','-'); //No I18N\n\t");
/* 124 */       if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*     */         return;
/* 126 */       out.write("\n\nsiteBoxObj.options[0].selected=true;\n \n\n\nif(");
/* 127 */       out.print(isAvailablity);
/* 128 */       out.write(" || ");
/* 129 */       out.print(isHealth);
/* 130 */       out.write(")\n{\n\tthId1 =\"-1\";\n}\nelse\n{\n\ttry{\n\t\tthId1 = document.getElementById(\"thresholdlistid\").value;\n\t}\n\tcatch(e)\n\t{\n\t\tthId1=\"-1\";\n\t}\n}\n\n\t\tshowApplySelectedOnChange1(thId1,'");
/* 131 */       out.print(resType);
/* 132 */       out.write(39);
/* 133 */       out.write(44);
/* 134 */       out.print(resId);
/* 135 */       out.write(44);
/* 136 */       out.print(attrId);
/* 137 */       out.write(",object.value,siteBoxObj.value) ;\n\n}\n\n\n\n\nfunction showApplySelectedOnChange1(thresholdId, resourcetype,resourceid, attributeID,custId,siteId)\n{\n\nobj = getHTTPObject();\n//alert(thresholdId);\n        var url = '/jsp/includes/SelectedMonitorThreshold.jsp?resourceid='+resourceid+'&thresholdid='+thresholdId+'&attributeid='+attributeID+'&resourcetype='+resourcetype+'&custId='+custId+'&siteId='+siteId; //No I18N\n        obj.open(\"GET\",url,true);\n        obj.onreadystatechange = handleResponse1;\n        obj.send(null);\n\t}\n\n\n\nfunction handleResponse1()\n{\n        if(obj.readyState == 4)\n\t{ \n                var result = obj.responseText;\n                document.getElementById(\"similarmonitorsajax\").innerHTML = result;\n                document.getElementById(\"similarmonitorsajax\").style.display='block';\n                document.getElementById(\"processingmonitors\").style.display='none';\n        }\n}\n\n\n\nfunction showSimilarMonitor(object)\n{\n\n\tvar custBoxObj =document.getElementById('custName');\n\t\n\nif(");
/* 138 */       out.print(isAvailablity);
/* 139 */       out.write(" || ");
/* 140 */       out.print(isHealth);
/* 141 */       out.write(")\n{\n\tthId =\"-1\";\n}\nelse\n{\n\t\ttry{\n\t\tthId = document.getElementById(\"thresholdlistid\").value;\n\t}\n\tcatch(e)\n\t{\n\t\tthId=\"-1\";\n\t}\n}\n\n//alert(document.getElementById(\"organization\").value);\n\nshowApplySelectedOnChange1(thId,'");
/* 142 */       out.print(resType);
/* 143 */       out.write(39);
/* 144 */       out.write(44);
/* 145 */       out.print(resId);
/* 146 */       out.write(44);
/* 147 */       out.print(attrId);
/* 148 */       out.write(",custBoxObj.value,object.value) ;\n}\n\n\n</script>\n");
/*     */       
/* 150 */       ArrayList orgs = AlarmUtil.getCustomerNames();
/*     */       
/* 152 */       if (orgs != null)
/*     */       {
/* 154 */         request.setAttribute("customers", orgs);
/*     */       }
/*     */       
/* 157 */       out.write("\n<!--%\nout.println(\"<div style=\\\"width:55px\\\" id=\\\"loading\\\"><span class=\\\"bodytextboldwhite\\\">&nbsp;Loading...&nbsp;</span></div>\");\nout.println(\"<div id=\\\"dhtmltooltip\\\"></div>\");\nout.println(\"<div id=\\\"dhtmlpointer\\\"><img src=\\\"/images/arrow2.gif\\\"></div>\");\n%-->\n\n<html>\n<tr>\n    <td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext\">");
/* 158 */       out.print(FormatUtil.getString("it360.sp.customermgmt.customer.txt", new String[] { "MGSTR" }));
/* 159 */       out.write("\n    </td>\n    <td height=\"28\" align=\"left\" >\n\t      <select name=\"organization\" id=\"custName\" styleClass=\"formtext\" style=\"width:100%\" onchange=\"javascript:loadSite(this)\">\n\t\t      <option value='-'>");
/* 160 */       out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.customer", new String[] { "MGSTR" }));
/* 161 */       out.write("</option>\n\t\t      ");
/*     */       
/* 163 */       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 164 */       _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 165 */       _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*     */       
/* 167 */       _jspx_th_logic_005fnotEmpty_005f0.setName("customers");
/* 168 */       int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 169 */       if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*     */         for (;;) {
/* 171 */           out.write(32);
/*     */           
/* 173 */           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 174 */           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 175 */           _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*     */           
/* 177 */           _jspx_th_logic_005fiterate_005f0.setName("customers");
/*     */           
/* 179 */           _jspx_th_logic_005fiterate_005f0.setId("row");
/*     */           
/* 181 */           _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*     */           
/* 183 */           _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 184 */           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 185 */           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 186 */             ArrayList row = null;
/* 187 */             Integer j = null;
/* 188 */             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 189 */               out = _jspx_page_context.pushBody();
/* 190 */               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 191 */               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */             }
/* 193 */             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 194 */             j = (Integer)_jspx_page_context.findAttribute("j");
/*     */             for (;;) {
/* 196 */               out.write("\n\t\t<option value='");
/* 197 */               out.print(row.get(1));
/* 198 */               out.write(39);
/* 199 */               out.write(62);
/* 200 */               out.print(row.get(0));
/* 201 */               out.write("</option>\n\t\t ");
/* 202 */               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 203 */               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 204 */               j = (Integer)_jspx_page_context.findAttribute("j");
/* 205 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 208 */             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 209 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 212 */           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 213 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*     */           }
/*     */           
/* 216 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 217 */           out.write(32);
/* 218 */           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 219 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 223 */       if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 224 */         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*     */       }
/*     */       else {
/* 227 */         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 228 */         out.write("\n\t</select>\n\t    \n      \n\t</td>\n</tr>\n\n\n\n <tr>\n    <td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext\">");
/* 229 */         out.print(FormatUtil.getString("it360.sp.customermgmt.site.txt", new String[] { "MGSTR" }));
/* 230 */         out.write("\n    </td>\n    <td height=\"28\" align=\"left\" >\n\n\n  <select name=\"siteName\" id=\"siteName\" styleClass=\"formtext\"  style=\"width:100%\"  onchange=\"javascript:showSimilarMonitor(this);\">\n\t\t<option value='-'>");
/* 231 */         out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.siteGroup", new String[] { "MGSTR" }));
/* 232 */         out.write("</option>\n\t\t    ");
/*     */         
/* 234 */         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 235 */         _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 236 */         _jspx_th_logic_005fnotEmpty_005f1.setParent(null);
/*     */         
/* 238 */         _jspx_th_logic_005fnotEmpty_005f1.setName("site");
/* 239 */         int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 240 */         if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*     */           for (;;) {
/* 242 */             out.write(32);
/*     */             
/* 244 */             IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 245 */             _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 246 */             _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*     */             
/* 248 */             _jspx_th_logic_005fiterate_005f1.setName("applications2");
/*     */             
/* 250 */             _jspx_th_logic_005fiterate_005f1.setId("row");
/*     */             
/* 252 */             _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*     */             
/* 254 */             _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/* 255 */             int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 256 */             if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 257 */               ArrayList row = null;
/* 258 */               Integer j = null;
/* 259 */               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 260 */                 out = _jspx_page_context.pushBody();
/* 261 */                 _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 262 */                 _jspx_th_logic_005fiterate_005f1.doInitBody();
/*     */               }
/* 264 */               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 265 */               j = (Integer)_jspx_page_context.findAttribute("j");
/*     */               for (;;) {
/* 267 */                 out.write("\n\t\t<option value='");
/* 268 */                 out.print(row.get(1));
/* 269 */                 out.write(39);
/* 270 */                 out.write(62);
/* 271 */                 out.print(row.get(0));
/* 272 */                 out.write("</option>\n\t\t ");
/* 273 */                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 274 */                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 275 */                 j = (Integer)_jspx_page_context.findAttribute("j");
/* 276 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/* 279 */               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 280 */                 out = _jspx_page_context.popBody();
/*     */               }
/*     */             }
/* 283 */             if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 284 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*     */             }
/*     */             
/* 287 */             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 288 */             out.write(32);
/* 289 */             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 290 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 294 */         if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 295 */           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/*     */         }
/*     */         else {
/* 298 */           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 299 */           out.write("\t</select>\n\n      </td>\n      </tr>\n \n      </html>\n\n\n");
/*     */         }
/* 301 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 302 */         out = _jspx_out;
/* 303 */         if ((out != null) && (out.getBufferSize() != 0))
/* 304 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 305 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 308 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 314 */     PageContext pageContext = _jspx_page_context;
/* 315 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 317 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 318 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 319 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 321 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 323 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 324 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 325 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 326 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 327 */       return true;
/*     */     }
/* 329 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 330 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 335 */     PageContext pageContext = _jspx_page_context;
/* 336 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 338 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 339 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 340 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*     */     
/* 342 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*     */     
/* 344 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*     */     
/* 346 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 347 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*     */     try {
/* 349 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 350 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*     */         for (;;) {
/* 352 */           out.write(10);
/* 353 */           out.write(9);
/* 354 */           out.write(9);
/* 355 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 356 */             return true;
/* 357 */           out.write("\n\t\tif(formCustomerId == customerId)\n\t\t{\n\t\t\t//object.form.siteName.options[rowCount++] = new Option(site,siteId);\n\t\t\tsiteBoxObj.options[rowCount++] = new Option(site,siteId);\n\t\t}\n\n\t\t");
/* 358 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 359 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 363 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 364 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 367 */         int tmp202_201 = 0; int[] tmp202_199 = _jspx_push_body_count_c_005fforEach_005f0; int tmp204_203 = tmp202_199[tmp202_201];tmp202_199[tmp202_201] = (tmp204_203 - 1); if (tmp204_203 <= 0) break;
/* 368 */         out = _jspx_page_context.popBody(); }
/* 369 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*     */     } finally {
/* 371 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 372 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*     */     }
/* 374 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*     */   {
/* 379 */     PageContext pageContext = _jspx_page_context;
/* 380 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 382 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 383 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 384 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*     */     
/* 386 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*     */     
/* 388 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*     */     
/* 390 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 391 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*     */     try {
/* 393 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 394 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*     */         for (;;) {
/* 396 */           out.write("\n\t\t\t");
/* 397 */           boolean bool; if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 398 */             return true;
/* 399 */           out.write("\n\t\t\t");
/* 400 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 401 */             return true;
/* 402 */           out.write("\n\t\t\t");
/* 403 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 404 */             return true;
/* 405 */           out.write(10);
/* 406 */           out.write(9);
/* 407 */           out.write(9);
/* 408 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 409 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 413 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 414 */         return 1;
/*     */     } catch (Throwable _jspx_exception) {
/*     */       for (;;) {
/* 417 */         int tmp289_288 = 0; int[] tmp289_286 = _jspx_push_body_count_c_005fforEach_005f1; int tmp291_290 = tmp289_286[tmp289_288];tmp289_286[tmp289_288] = (tmp291_290 - 1); if (tmp291_290 <= 0) break;
/* 418 */         out = _jspx_page_context.popBody(); }
/* 419 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*     */     } finally {
/* 421 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 422 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*     */     }
/* 424 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 429 */     PageContext pageContext = _jspx_page_context;
/* 430 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 432 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 433 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 434 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 436 */     _jspx_th_c_005fif_005f0.setTest("${rowCounter1.count == 1}");
/* 437 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 438 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 440 */         out.write("\n\t\t\t\tsite = '");
/* 441 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 442 */           return true;
/* 443 */         out.write("';\n\t\t\t");
/* 444 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 445 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 449 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 450 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 451 */       return true;
/*     */     }
/* 453 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 454 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 459 */     PageContext pageContext = _jspx_page_context;
/* 460 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 462 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 463 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 464 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*     */     
/* 466 */     _jspx_th_c_005fout_005f1.setValue("${b}");
/* 467 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 468 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 469 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 470 */       return true;
/*     */     }
/* 472 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 473 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 478 */     PageContext pageContext = _jspx_page_context;
/* 479 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 481 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 482 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 483 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 485 */     _jspx_th_c_005fif_005f1.setTest("${rowCounter1.count == 2}");
/* 486 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 487 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */       for (;;) {
/* 489 */         out.write("\n\t\t\t\tsiteId = '");
/* 490 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 491 */           return true;
/* 492 */         out.write("';\n\t\t\t");
/* 493 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 494 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 498 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 499 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 500 */       return true;
/*     */     }
/* 502 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 503 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 508 */     PageContext pageContext = _jspx_page_context;
/* 509 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 511 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 512 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 513 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*     */     
/* 515 */     _jspx_th_c_005fout_005f2.setValue("${b}");
/* 516 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 517 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 518 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 519 */       return true;
/*     */     }
/* 521 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 522 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 527 */     PageContext pageContext = _jspx_page_context;
/* 528 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 530 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 531 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 532 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*     */     
/* 534 */     _jspx_th_c_005fif_005f2.setTest("${rowCounter1.count == 3}");
/* 535 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 536 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */       for (;;) {
/* 538 */         out.write("\n\t\t\t\tcustomerId = '");
/* 539 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 540 */           return true;
/* 541 */         out.write("';\n\t\t\t");
/* 542 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 543 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 547 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 548 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 549 */       return true;
/*     */     }
/* 551 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 552 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*     */   {
/* 557 */     PageContext pageContext = _jspx_page_context;
/* 558 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 560 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 561 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 562 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/*     */     
/* 564 */     _jspx_th_c_005fout_005f3.setValue("${b}");
/* 565 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 566 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 567 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 568 */       return true;
/*     */     }
/* 570 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 571 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\customerSite_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */