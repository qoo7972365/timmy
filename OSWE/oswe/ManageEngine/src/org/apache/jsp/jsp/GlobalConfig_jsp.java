/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.tiles.InsertTag;
/*     */ import org.apache.struts.taglib.tiles.PutTag;
/*     */ 
/*     */ public final class GlobalConfig_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  19 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  32 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  36 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  37 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  40 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  44 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/*  45 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*  46 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  53 */     HttpSession session = null;
/*     */     
/*     */ 
/*  56 */     JspWriter out = null;
/*  57 */     Object page = this;
/*  58 */     JspWriter _jspx_out = null;
/*  59 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  63 */       response.setContentType("text/html");
/*  64 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  66 */       _jspx_page_context = pageContext;
/*  67 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  68 */       ServletConfig config = pageContext.getServletConfig();
/*  69 */       session = pageContext.getSession();
/*  70 */       out = pageContext.getOut();
/*  71 */       _jspx_out = out;
/*     */       
/*  73 */       out.write("<!DOCTYPE html>\n");
/*  74 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!-- $Id$ -->\n\n\n\n\n\n");
/*     */       
/*  76 */       String type = request.getParameter("type");
/*  77 */       String appname = request.getParameter("name");
/*  78 */       String haid = request.getParameter("haid");
/*     */       
/*  80 */       out.write("\n<script>\nfunction fnFormSubmit()\n{\n\n");
/*     */       
/*  82 */       if (type.equals("WebSphere-server"))
/*     */       {
/*     */ 
/*  85 */         out.write("\n    if(trimAll(document.AMActionForm.soapport.value) == '')\n\t{\n\t   alert(\"Please enter the SOAP Port value\");\n\t   document.AMActionForm.soapport.select();\n\t   return;\n\t}\n");
/*     */       }
/*     */       
/*     */ 
/*  89 */       out.write(10);
/*     */       
/*  91 */       if ((type.equals("ORACLE-DB-server")) || (type.equals("MYSQL-DB-server")))
/*     */       {
/*  93 */         out.write("\n  if(trimAll(document.AMActionForm.instance.value) == '')\n\t{\n\t   alert(\"Please enter the Instance Name\");\n\t   document.AMActionForm.instance.select();\n\t   return;\n\t}\n\t\n");
/*     */       }
/*     */       
/*  96 */       if (type.equals("MAIL-server"))
/*     */       {
/*     */ 
/*  99 */         out.write("\n  if(trimAll(document.AMActionForm.smtpPort.value) == '')\n\t{\n\t   alert(\"Please enter the SMTP Port\");\n\t   document.AMActionForm.smtpPort.select();\n\t   return;\n\t}\n  if(trimAll(document.AMActionForm.popPort.value) == '')\n\t{\n\t   alert(\"Please enter the POP Port\");\n\t   document.AMActionForm.popPort.select();\n\t   return;\n\t}\t\t\n\n");
/*     */       }
/*     */       
/*     */ 
/* 103 */       out.write("\n\n\tif(trimAll(document.AMActionForm.pollInterval.value) == '')\n\t{\n\t\talert(\"Please enter the Polling Interval value\");\n\t\tdocument.AMActionForm.pollInterval.select();\n\t\treturn;\n\t}\n    document.AMActionForm.action=\"/adminAction.do\";\n\tdocument.AMActionForm.submit();\n}\n\n</script>\n");
/*     */       
/* 105 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 106 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 107 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*     */       
/* 109 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/* 110 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 111 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*     */         for (;;) {
/* 113 */           out.write(10);
/* 114 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 116 */           out.write(10);
/* 117 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 119 */           out.write(10);
/* 120 */           if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 122 */           out.write(10);
/*     */           
/* 124 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 125 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 126 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*     */           
/* 128 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*     */           
/* 130 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 131 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 132 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 133 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 134 */               out = _jspx_page_context.pushBody();
/* 135 */               _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 136 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*     */             }
/*     */             for (;;) {
/* 139 */               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <form name=\"AMActionForm\" action=\"/adminAction.do\">\n<tr>\n    <td height=\"28\" colspan=\"2\" class=\"tableheadingbborder\"><span class=\"bodytext\">Provide \n      the details for Monitoring ");
/* 140 */               out.print(request.getParameter("displayname"));
/* 141 */               out.write("</span> \n      <input type=\"hidden\" name=\"org.apache.struts.taglib.html.TOKEN\" value=\"");
/* 142 */               out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 143 */               out.write("\">\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 144 */               out.print(request.getParameter("name"));
/* 145 */               out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 146 */               out.print(request.getParameter("haid"));
/* 147 */               out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 148 */               out.print(type);
/* 149 */               out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureresource\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 150 */               out.print(request.getParameter("resourceid"));
/* 151 */               out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 152 */               out.print(request.getParameter("resourcename"));
/* 153 */               out.write("\">\n<input type=\"hidden\" name=\"displayname\" value=\"");
/* 154 */               out.print(request.getParameter("displayname"));
/* 155 */               out.write("\">\n</td>\n\n</tr>\n");
/*     */               
/* 157 */               if (type.equals("WebSphere-server"))
/*     */               {
/*     */ 
/* 160 */                 out.write("\n\t<TR>\n\t<TD height=\"28\" width=\"16%\" class=\"bodytext\">SOAP Port<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"84%\"> <INPUT NAME=\"soapport\" TYPE=\"text\" class=\"formtext\" VALUE=\"8880\" SIZE=\"15\">\n\t</TD>\n\t</TR>\n\n");
/*     */               }
/*     */               
/* 163 */               if ((type.equals("WEBLOGIC-server")) || (type.equals("ORACLE-DB-server")) || (type.equals("MYSQL-DB-server")) || (type.equals("Node")) || (type.equals("snmp-node")) || (type.equals("Linux")) || (type.equals("WindowsNT")) || (type.equals("WindowsNT_Server")) || (type.equals("Windows95")) || (type.equals("SUN")) || (type.equals("SUN PC")) || (type.equals("MAIL-server")) || (type.equals("WebSphere-server")))
/*     */               {
/*     */ 
/* 166 */                 out.write("\n\n\t<TR>\n\t<TD height=\"28\" width=\"16%\" class=\"bodytext\">UserName<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\" width=\"84%\"> <INPUT NAME=\"username\" TYPE=\"text\" class=\"formtext\" VALUE=\"\" SIZE=\"15\">\n\t</TD>\n\t</TR>\n\t<TR>\n\t<TD height=\"28\" class=\"bodytext\">Password<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\"> <INPUT NAME=\"password\" TYPE=\"password\" class=\"formtext\" VALUE=\"\" SIZE=\"15\">\n\t</TD>\n\t</TR>\n");
/*     */               }
/*     */               
/* 169 */               if ((type.equals("Node")) || (type.equals("snmp-node")) || (type.equals("Linux")) || (type.equals("WindowsNT")) || (type.equals("WindowsNT_Server")) || (type.equals("Windows95")) || (type.equals("SUN")) || (type.equals("SUN PC")))
/*     */               {
/* 171 */                 out.write("\n\t\t<tr>\n\t\t\n    <td class=\"bodytext\">OS Type<span class=\"mandatory\">*</span></td>\n\t\t<td>\n\t\t<select name=\"os\" styleClass=\"formtextarea\" >\n\t\t<option value=\"Node\">Select OS</option>\n\t\t<option value=\"Linux\">Linux</option>\n\t\t<option value=\"SUN\">Sun OS</option>\n\t\t<option value=\"Windows 2000\">Windows 2000</option>\n\t\t<option value=\"WindowsNT\">Windows NT</option>\n\t\t</select></td>\n\t\t</tr>\n\n");
/*     */               }
/*     */               
/*     */ 
/* 175 */               if ((type.equals("ORACLE-DB-server")) || (type.equals("MYSQL-DB-server")))
/*     */               {
/* 177 */                 out.write("\n\t<TR>\n\t<TD height=\"28\" class=\"bodytext\">Instance Name<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\"> <INPUT NAME=\"instance\" TYPE=\"text\" class=\"formtext\" VALUE=\"\" SIZE=\"15\">\n\t</TD>\n\t</TR>\n");
/*     */               }
/*     */               
/* 180 */               if (type.equals("MAIL-server"))
/*     */               {
/*     */ 
/* 183 */                 out.write("\n\t<TR>\n        \n    <TD height=\"28\" class=\"bodytext\">Smtp Port<span class=\"mandatory\">*</span></TD>\n        <TD height=\"28\"> <INPUT NAME=\"smtpPort\" TYPE=\"text\" class=\"formtext\" VALUE=\"\" SIZE=\"10\">\n        </TD>\n        </TR>\n\n\t<TR>\n        \n    <TD height=\"28\" class=\"bodytext\">Pop Port<span class=\"mandatory\">*</span></TD>\n        <TD height=\"28\"> <INPUT NAME=\"popPort\" TYPE=\"text\" class=\"formtext\" VALUE=\"\" SIZE=\"10\">\n        </TD>\n        </TR>\n\n        <TR>\n        \n    <TD height=\"28\" class=\"bodytext\">Message<span class=\"mandatory\">*</span></TD>\n        <TD height=\"28\"> <INPUT NAME=\"mailMsg\" TYPE=\"text\" class=\"formtext\" VALUE=\"\" SIZE=\"50\">\n        </TD>\n        </TR>\n");
/*     */               }
/*     */               
/*     */ 
/* 187 */               out.write("\n\n\t<TR>\n\t<TD height=\"28\" class=\"bodytext\">Polling Interval<span class=\"mandatory\">*</span></TD>\n\t<TD height=\"28\"> <INPUT NAME=\"pollInterval\" TYPE=\"text\" class=\"formtext\" VALUE=\"5\" SIZE=\"15\"> &nbsp; <span class=\"bodytext\">minute(s)</span>\n\t</TD>\n\t</TR>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"16%\" class=\"tablebottom\">&nbsp;</td>\n<td width=\"84%\" class=\"tablebottom\"><input type=\"button\"  class=\"buttons\" value=\"Start Monitoring and Add to Application\" onClick=\"javascript:fnFormSubmit()\"/>\n&nbsp;\n<input type=\"reset\" class=\"buttons\" value=\"Reset\"/></td>         </tr>\n</table>\n</form>\t\n");
/* 188 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 189 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 192 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 193 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 196 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 197 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*     */           }
/*     */           
/* 200 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 201 */           out.write(10);
/* 202 */           out.write(32);
/* 203 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*     */             return;
/* 205 */           out.write(10);
/* 206 */           out.write(32);
/* 207 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 208 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 212 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 213 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*     */       }
/*     */       else {
/* 216 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 217 */         out.write(10);
/*     */       }
/* 219 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 220 */         out = _jspx_out;
/* 221 */         if ((out != null) && (out.getBufferSize() != 0))
/* 222 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 223 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 226 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 232 */     PageContext pageContext = _jspx_page_context;
/* 233 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 235 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 236 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 237 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 239 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*     */     
/* 241 */     _jspx_th_tiles_005fput_005f0.setValue("Select Resources");
/* 242 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 243 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 244 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 245 */       return true;
/*     */     }
/* 247 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 248 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 253 */     PageContext pageContext = _jspx_page_context;
/* 254 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 256 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 257 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 258 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 260 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*     */     
/* 262 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 263 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 264 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 265 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 266 */       return true;
/*     */     }
/* 268 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 269 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 274 */     PageContext pageContext = _jspx_page_context;
/* 275 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 277 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 278 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 279 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 281 */     _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*     */     
/* 283 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/AddMonitorsLeftPage.jsp");
/* 284 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 285 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 286 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 287 */       return true;
/*     */     }
/* 289 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 290 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 295 */     PageContext pageContext = _jspx_page_context;
/* 296 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 298 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 299 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 300 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*     */     
/* 302 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*     */     
/* 304 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 305 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 306 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 307 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 308 */       return true;
/*     */     }
/* 310 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 311 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\GlobalConfig_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */