/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Properties;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.bean.WriteTag;
/*     */ import org.apache.struts.taglib.html.MessagesTag;
/*     */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*     */ import org.apache.struts.taglib.logic.NotPresentTag;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class EditPlatform_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  25 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  41 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  45 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  52 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  56 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  57 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  58 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  59 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  60 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  61 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  68 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  71 */     JspWriter out = null;
/*  72 */     Object page = this;
/*  73 */     JspWriter _jspx_out = null;
/*  74 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  78 */       response.setContentType("text/html;charset=UTF-8");
/*  79 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  81 */       _jspx_page_context = pageContext;
/*  82 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  83 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  84 */       session = pageContext.getSession();
/*  85 */       out = pageContext.getOut();
/*  86 */       _jspx_out = out;
/*     */       
/*  88 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/*  89 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  91 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*     */       
/*  93 */       String resids = request.getParameter("rowIds");
/*     */       
/*     */ 
/*  96 */       out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\nfunction submitForm()   {\n\t");
/*  97 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/*  99 */       out.write(10);
/* 100 */       out.write(9);
/*     */       
/* 102 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 103 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 104 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */       
/* 106 */       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 107 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 108 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */         for (;;) {
/* 110 */           out.write("\n\tfor(i=0;i<document.AMActionForm.elements.length;i++)   {\n\t\tvar displayname = document.AMActionForm.elements[i].value;\t\n\t\tif(displayname=='')   {\n\t  \t\talert('");
/* 111 */           out.print(FormatUtil.getString("am.webclient.amazon.osname.text"));
/* 112 */           out.write("');\t\n\t  \t\treturn;\n\t\t}\n\t}\n\t\tdocument.AMActionForm.action=\"/assignOSNames.do?method=editOSNames&rowIds=");
/* 113 */           out.print(resids);
/* 114 */           out.write("\";\n\t\tdocument.AMActionForm.submit();\n\t\topener.location.reload(true);\n\t\t//window.opener.location.reload(true);\n\t");
/* 115 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 116 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 120 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 121 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*     */       }
/*     */       else {
/* 124 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 125 */         out.write("\n}\nfunction setFocusProperTextField()  {\n\tvar pos = document.AMActionForm.elements.length;\n    \tif(pos > 0)  { \n\t\tif(document.AMActionForm.elements.length >=2)  {\n              \t\tpos = 1;\n          \t}   else   {\n          \t\treturn; \n          \t}\n\t\tfor(i=0;i<document.AMActionForm.elements.length;i++)   {\n\t\t\tif(document.AMActionForm.elements[i].type =='text')   {               \n\t\t\t\ttry    {\n\t\t\t\t\tdocument.AMActionForm.elements[i].focus(); \n\t\t\t\t\tbreak;\n\t\t\t\t}   catch (e) {}                    \n                    \t\n                \t}\n            \t}       \n    \t}\n}\nfunction doInitStuffOnBodyLoad()  {\n\n    \tsetFocusProperTextField();\n    \tif (window.myOnLoad)   {\n\t\tmyOnLoad();\n    \t}\n}\n</script>\n");
/*     */         
/* 127 */         String resids_forquery = "(";
/* 128 */         StringTokenizer nameAfterComma = new StringTokenizer(resids, ",");
/* 129 */         String vmCondition = "";
/* 130 */         String osName = "";
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 135 */         while (nameAfterComma.hasMoreTokens()) {
/* 136 */           String temp = nameAfterComma.nextToken();
/* 137 */           resids_forquery = resids_forquery + "" + temp + ", ";
/*     */         }
/* 139 */         if (!resids_forquery.equals("(")) {
/* 140 */           resids_forquery = resids_forquery.substring(0, resids_forquery.length() - 2);
/* 141 */           resids_forquery = resids_forquery + ")";
/*     */         }
/* 143 */         String query = "select RESOURCEID, RESOURCENAME from AM_ManagedObject where RESOURCEID in " + resids_forquery + vmCondition;
/*     */         
/* 145 */         ArrayList resourceids = new ArrayList();
/* 146 */         Hashtable name_ids = new Hashtable();
/* 147 */         Properties displaynames = new Properties();
/* 148 */         ResultSet results = null;
/*     */         try {
/* 150 */           results = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(query);
/* 151 */           while (results.next()) {
/* 152 */             String dispname = results.getString("RESOURCENAME");
/* 153 */             String id = results.getString("RESOURCEID");
/* 154 */             resourceids.add(id);
/* 155 */             name_ids.put(id, dispname);
/* 156 */             displaynames.setProperty(id, dispname);
/*     */           }
/* 158 */           request.setAttribute("resourceids", resourceids);
/* 159 */           request.setAttribute("nameids", name_ids);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */           try
/*     */           {
/* 166 */             if (results != null) {
/* 167 */               results.close();
/*     */             }
/*     */           }
/*     */           catch (Exception exc) {}
/*     */           
/* 172 */           out.write("\n<title>");
/*     */         }
/*     */         catch (Exception exception)
/*     */         {
/* 161 */           exception.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/*     */           try {
/* 166 */             if (results != null) {
/* 167 */               results.close();
/*     */             }
/*     */           }
/*     */           catch (Exception exc) {}
/*     */         }
/*     */         
/* 173 */         out.print(FormatUtil.getString("am.webclient.amazon.platformname.title.text"));
/* 174 */         out.write("</title>\n<body onLoad=\"javascript:doInitStuffOnBodyLoad()\"></body>\n<form  action=\"/editDisplaynames\" method=\"post\" name=\"AMActionForm\" style=\"display:inline\" >\n");
/*     */         
/* 176 */         MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 177 */         _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 178 */         _jspx_th_html_005fmessages_005f0.setParent(null);
/*     */         
/* 180 */         _jspx_th_html_005fmessages_005f0.setId("msg");
/*     */         
/* 182 */         _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 183 */         int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 184 */         if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 185 */           String msg = null;
/* 186 */           if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 187 */             out = _jspx_page_context.pushBody();
/* 188 */             _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 189 */             _jspx_th_html_005fmessages_005f0.doInitBody();
/*     */           }
/* 191 */           msg = (String)_jspx_page_context.findAttribute("msg");
/*     */           for (;;) {
/* 193 */             out.write("         \n\t\t\n\t          \t\t<table width=\"95%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\">\n\t        \t\t\t<tr> \n\t                \t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t                \t\t\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 194 */             if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*     */               return;
/* 196 */             out.write("</td>\n\t              \t\t\t</tr>\n\t            \t\t</table>\n\t            \t\t<br>\n\t\t\n\t\t");
/* 197 */             int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 198 */             msg = (String)_jspx_page_context.findAttribute("msg");
/* 199 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/* 202 */           if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 203 */             out = _jspx_page_context.popBody();
/*     */           }
/*     */         }
/* 206 */         if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 207 */           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*     */         }
/*     */         else {
/* 210 */           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 211 */           out.write(10);
/* 212 */           out.write(9);
/* 213 */           out.write(9);
/*     */           
/* 215 */           MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 216 */           _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 217 */           _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
/*     */           
/* 219 */           _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 220 */           int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 221 */           if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*     */             for (;;) {
/* 223 */               out.write(" \n\t\t\n\t\t\t\t\t<table width=\"95%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t\t\t\t\t\t<tr> \n\t                \t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t                \t\t\t\t<td width=\"95%\" class=\"message\">\n\t                \t\t\t\t\t");
/*     */               
/* 225 */               MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 226 */               _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 227 */               _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */               
/* 229 */               _jspx_th_html_005fmessages_005f1.setId("msg");
/*     */               
/* 231 */               _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 232 */               int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 233 */               if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 234 */                 String msg = null;
/* 235 */                 if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 236 */                   out = _jspx_page_context.pushBody();
/* 237 */                   _jspx_th_html_005fmessages_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 238 */                   _jspx_th_html_005fmessages_005f1.doInitBody();
/*     */                 }
/* 240 */                 msg = (String)_jspx_page_context.findAttribute("msg");
/*     */                 for (;;) {
/* 242 */                   out.write(" \n\t                \t  \t\t\t\t\t");
/* 243 */                   if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*     */                     return;
/* 245 */                   out.write("<br>\n\t\t\t\t\t\t\t\t");
/* 246 */                   int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 247 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/* 248 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 251 */                 if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 252 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 255 */               if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 256 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*     */               }
/*     */               
/* 259 */               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 260 */               out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t<br>\n\t\t");
/* 261 */               int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 262 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 266 */           if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 267 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */           }
/*     */           else {
/* 270 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 271 */             out.write(32);
/* 272 */             out.write(" \n\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  class=\"lrtborder\" width=\"100%\" align=\"center\">\n\t\t<tr align=\"left\">\n\t\t\t<td class=\"tableheadingbborder\">");
/* 273 */             out.print(FormatUtil.getString("am.webclient.amazon.platformname.title.text"));
/* 274 */             out.write("</td>\n\t\t</tr>\n\t\n\t\t\n\t\t");
/*     */             
/* 276 */             if (displaynames != null)
/*     */             {
/* 278 */               out.write("\n\t\t\t\n\t\t\t<tr >\n\t\t\t\t<td class=\"lightbg\">&nbsp;<b><span class=\"bodytextbold\">");
/* 279 */               out.print(FormatUtil.getString("am.webclient.amazon.osname.selected.text"));
/* 280 */               out.write("</span></b></td>\n\t\t  \t</tr>\n\t\t  \t<tr><td>\n\t\t\t\t<img src=\"../images/spacer.gif\" height=\"5\" width=\"5\">\n\t\t\t</td></tr>\n\t\t \t<tr align=\"left\"><td>\n\t\t \t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"   width=\"100%\" align=\"center\">\n\t\t  \n\t\t  \t\t\n\t\t  \t\t");
/*     */               
/* 282 */               int i = 0;
/* 283 */               for (i = 0; i < resourceids.size(); i++) {
/* 284 */                 String resid = (String)resourceids.get(i);
/* 285 */                 String displayname = (String)name_ids.get(resid);
/* 286 */                 int len = displayname.length();
/* 287 */                 String bgclass = "whitegrayborder";
/* 288 */                 if (i % 2 != 0) {
/* 289 */                   bgclass = "yellowgrayborder";
/*     */                 } else {
/* 291 */                   bgclass = "whitegrayborder";
/*     */                 }
/* 293 */                 String osQuery = "select INSTANCEOS from AM_Amazon_EC2InstanceOSMAPPING where RESOURCEID = " + resid;
/*     */                 
/* 295 */                 ResultSet osResults = null;
/*     */                 try
/*     */                 {
/* 298 */                   osResults = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(osQuery);
/* 299 */                   while (osResults.next())
/*     */                   {
/* 301 */                     osName = osResults.getString("INSTANCEOS");
/*     */                   }
/*     */                   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   try
/*     */                   {
/* 311 */                     if (osResults != null) {
/* 312 */                       osResults.close();
/*     */                     }
/*     */                   }
/*     */                   catch (Exception exc) {}
/*     */                   
/* 317 */                   out.write("\n\t\t\t\t\t\t\n\t\t\t\t\t\t<tr >\t\n\t\t\t\t\t\t\t<td class=");
/*     */                 }
/*     */                 catch (Exception exception)
/*     */                 {
/* 306 */                   exception.printStackTrace();
/*     */                 }
/*     */                 finally
/*     */                 {
/*     */                   try {
/* 311 */                     if (osResults != null) {
/* 312 */                       osResults.close();
/*     */                     }
/*     */                   }
/*     */                   catch (Exception exc) {}
/*     */                 }
/*     */                 
/* 318 */                 out.print(bgclass);
/* 319 */                 out.write(" height=\"30\">");
/* 320 */                 out.print(displayname);
/* 321 */                 out.write("&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t<td class=");
/* 322 */                 out.print(bgclass);
/* 323 */                 out.write(">\n\t\t\t\t\t\t\t");
/*     */                 
/* 325 */                 if ((osName.equalsIgnoreCase("NA")) || (osName.equalsIgnoreCase("select")))
/*     */                 {
/*     */ 
/* 328 */                   out.write("\n\t\t\t\t\t\t\t<select name=\"");
/* 329 */                   out.print(resid);
/* 330 */                   out.write("\" >\n\t\t\t\t\t\t\t<option value=\"\">Select OS</option>");
/* 331 */                   out.write(" \n\t\t\t\t\t\t\t<option value=\"Linux\">Linux</option>");
/* 332 */                   out.write(" \n\t\t\t\t\t\t\t<option value=\"Centos\">Centos</option>");
/* 333 */                   out.write(" \n\t\t\t\t\t\t\t<option value=\"Sun Solaris\">Sun Solaris</option>");
/* 334 */                   out.write(" \n\t\t\t\t\t\t\t<option value=\"AIX\">AIX</option>");
/* 335 */                   out.write(" \n\t\t\t\t\t\t\t<option value=\"Mac OS\">Mac OS</option>");
/* 336 */                   out.write(" \n\t\t\t\t\t\t\t<option value=\"AS400/iSeries\">AS400/iSeries</option>");
/* 337 */                   out.write(" \n\t\t\t\t\t\t\t<option value=\"Novell\">Novell</option>");
/* 338 */                   out.write(" \n\t\t\t\t\t\t\t<option value=\"Unknown\">Unknown</option>");
/* 339 */                   out.write(" \n\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t\t");
/*     */ 
/*     */                 }
/*     */                 else
/*     */                 {
/*     */ 
/* 345 */                   out.write("\n\t\t\t\t\t\t\t<input disabled type=\"text\" name=\"");
/* 346 */                   out.print(resid);
/* 347 */                   out.write("\" value=\"");
/* 348 */                   out.print(osName);
/* 349 */                   out.write("\" />\n\t\t\t\t\t\t\t");
/*     */                 }
/*     */                 
/*     */ 
/* 353 */                 out.write("\n\t\t\t\t\t\t\t </td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*     */               }
/* 355 */               out.write("\n\t\t\t\t</table>\t\t\n\t\t\t\t\n\t\t\t\t<tr><td>\n\t\t\t\t<img src=\"../images/spacer.gif\" height=\"5\" width=\"5\">\n\t\t\t\t</td></tr>\n\t\t\t\t</table>\n\t\t\t\t</td></tr>\n\t\t\t\t</table>\n       \t\t\t\t<table width=\"100%\" border=\"0\" class=\"lrtbdarkborder\" cellpadding=\"0\" cellspacing=\"0\">\n       \t\t\t\t<tr> \n       \t\t\t\t\t<td height=\"35\"  align=\"center\" class=\"tablebottom\">\n        \t   \t\t\t\t<input name=\"Submit\" type=\"button\" class=\"buttons\" value='");
/* 356 */               out.print(FormatUtil.getString("am.webclient.amazon.osname.apply.text"));
/* 357 */               out.write("' onClick=\"javascript:submitForm();\">\n        \t   \t\t\t\t<input name=\"GoBack\" type=\"button\" class=\"buttons\" value='");
/* 358 */               out.print(FormatUtil.getString("webclient.common.printview.button.close"));
/* 359 */               out.write("'  onClick=\"self.close();\">&nbsp;&nbsp;\n       \t\t\t\t\t</td>\n\t\t\t\t</tr>\n    \t\t\t\t</table>\n\t\t");
/*     */             }
/* 361 */             out.write("\n\t</form>\n\n");
/*     */           }
/* 363 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 364 */         out = _jspx_out;
/* 365 */         if ((out != null) && (out.getBufferSize() != 0))
/* 366 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 367 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 370 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 376 */     PageContext pageContext = _jspx_page_context;
/* 377 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 379 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 380 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 381 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 383 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 385 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 386 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 387 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 388 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 389 */       return true;
/*     */     }
/* 391 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 392 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 397 */     PageContext pageContext = _jspx_page_context;
/* 398 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 400 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 401 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 402 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 404 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 405 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 406 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 408 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 409 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 410 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 414 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 415 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 416 */       return true;
/*     */     }
/* 418 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 419 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 424 */     PageContext pageContext = _jspx_page_context;
/* 425 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 427 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 428 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 429 */     _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fmessages_005f0);
/*     */     
/* 431 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*     */     
/* 433 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 434 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 435 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 436 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 437 */       return true;
/*     */     }
/* 439 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 440 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 445 */     PageContext pageContext = _jspx_page_context;
/* 446 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 448 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 449 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 450 */     _jspx_th_bean_005fwrite_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fmessages_005f1);
/*     */     
/* 452 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*     */     
/* 454 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 455 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 456 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 457 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 458 */       return true;
/*     */     }
/* 460 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 461 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\EditPlatform_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */