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
/*     */ import javax.servlet.http.HttpServletResponse;
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
/*     */ public final class EditDisplaynames_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  26 */   private static final JspFactory _jspxFactory = ;
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
/*  42 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  46 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  53 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  57 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  58 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  59 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  60 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  61 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  62 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  69 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  72 */     JspWriter out = null;
/*  73 */     Object page = this;
/*  74 */     JspWriter _jspx_out = null;
/*  75 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  79 */       response.setContentType("text/html;charset=UTF-8");
/*  80 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  82 */       _jspx_page_context = pageContext;
/*  83 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  84 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  85 */       session = pageContext.getSession();
/*  86 */       out = pageContext.getOut();
/*  87 */       _jspx_out = out;
/*     */       
/*  89 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/*  90 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  92 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*     */       
/*  94 */       String resids = request.getParameter("resids");
/*  95 */       String vmResids = request.getParameter("vmResids");
/*     */       
/*  97 */       out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\nfunction submitForm()   {\n\t");
/*  98 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/* 100 */       out.write(10);
/* 101 */       out.write(9);
/*     */       
/* 103 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 104 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 105 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */       
/* 107 */       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 108 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 109 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */         for (;;) {
/* 111 */           out.write("\n\tfor(i=0;i<document.AMActionForm.elements.length;i++)   {\n\t\tvar displayname = document.AMActionForm.elements[i].value;\n\t\tif(displayname=='')   {\n\t  \t\talert('");
/* 112 */           out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 113 */           out.write("');\n\t  \t\treturn;\n\t\t}\n\t}\n\t\tdocument.AMActionForm.action=\"/editDisplaynames.do?method=editDisplaynames&resids=");
/* 114 */           out.print(resids);
/* 115 */           out.write("\";\n\t\tdocument.AMActionForm.submit();\n\t");
/* 116 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 117 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 121 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 122 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*     */       }
/*     */       else {
/* 125 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 126 */         out.write("\n}\nfunction setFocusProperTextField()  {\n\tvar pos = document.AMActionForm.elements.length;\n    \tif(pos > 0)  {\n\t\tif(document.AMActionForm.elements.length >=2)  {\n              \t\tpos = 1;\n          \t}   else   {\n          \t\treturn;\n          \t}\n\t\tfor(i=0;i<document.AMActionForm.elements.length;i++)   {\n\t\t\tif(document.AMActionForm.elements[i].type =='text')   {\n\t\t\t\ttry    {\n\t\t\t\t\tdocument.AMActionForm.elements[i].focus();\n\t\t\t\t\tbreak;\n\t\t\t\t}   catch (e) {}\n\n                \t}\n            \t}\n    \t}\n}\nfunction doInitStuffOnBodyLoad()  {\n\n    \tsetFocusProperTextField();\n    \tif (window.myOnLoad)   {\n\t\tmyOnLoad();\n    \t}\n}\n</script>\n");
/*     */         
/* 128 */         String resids_forquery = "(";
/* 129 */         StringTokenizer nameAfterComma = new StringTokenizer(resids, ",");
/* 130 */         String vmCondition = "";
/* 131 */         if ((vmResids != null) && (vmResids.length() > 1) && (!vmResids.equals("null")))
/*     */         {
/* 133 */           vmCondition = " AND RESOURCEID NOT IN (" + vmResids + ") ";
/*     */         }
/* 135 */         while (nameAfterComma.hasMoreTokens()) {
/* 136 */           String temp = nameAfterComma.nextToken();
/* 137 */           resids_forquery = resids_forquery + "" + temp + ", ";
/*     */         }
/* 139 */         if (!resids_forquery.equals("(")) {
/* 140 */           resids_forquery = resids_forquery.substring(0, resids_forquery.length() - 2);
/* 141 */           resids_forquery = resids_forquery + ")";
/*     */         }
/* 143 */         String query = "select RESOURCEID, DISPLAYNAME from AM_ManagedObject where RESOURCEID in " + resids_forquery + vmCondition;
/*     */         
/* 145 */         ArrayList resourceids = new ArrayList();
/* 146 */         Hashtable name_ids = new Hashtable();
/* 147 */         Properties displaynames = new Properties();
/* 148 */         ResultSet results = null;
/*     */         try {
/* 150 */           results = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(query);
/* 151 */           while (results.next()) {
/* 152 */             String dispname = results.getString("DISPLAYNAME");
/* 153 */             dispname = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(dispname);
/* 154 */             String id = results.getString("RESOURCEID");
/* 155 */             resourceids.add(id);
/* 156 */             name_ids.put(id, dispname);
/* 157 */             displaynames.setProperty(id, dispname);
/*     */           }
/* 159 */           request.setAttribute("resourceids", resourceids);
/* 160 */           request.setAttribute("nameids", name_ids);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */           try
/*     */           {
/* 167 */             if (results != null) {
/* 168 */               results.close();
/*     */             }
/*     */           }
/*     */           catch (Exception exc) {}
/*     */           
/* 173 */           out.write("\n<title>");
/*     */         }
/*     */         catch (Exception exception)
/*     */         {
/* 162 */           exception.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/*     */           try {
/* 167 */             if (results != null) {
/* 168 */               results.close();
/*     */             }
/*     */           }
/*     */           catch (Exception exc) {}
/*     */         }
/*     */         
/* 174 */         out.print(FormatUtil.getString("am.webclient.displayname.title.text"));
/* 175 */         out.write("</title>\n<body onLoad=\"javascript:doInitStuffOnBodyLoad()\"></body>\n<form  action=\"/editDisplaynames.do?method=editDisplaynames&resids=");
/* 176 */         out.print(resids);
/* 177 */         out.write("\" method=\"post\" name=\"AMActionForm\" style=\"display:inline\" >\n");
/*     */         
/* 179 */         MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 180 */         _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 181 */         _jspx_th_html_005fmessages_005f0.setParent(null);
/*     */         
/* 183 */         _jspx_th_html_005fmessages_005f0.setId("msg");
/*     */         
/* 185 */         _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 186 */         int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 187 */         if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 188 */           String msg = null;
/* 189 */           if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 190 */             out = _jspx_page_context.pushBody();
/* 191 */             _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 192 */             _jspx_th_html_005fmessages_005f0.doInitBody();
/*     */           }
/* 194 */           msg = (String)_jspx_page_context.findAttribute("msg");
/*     */           for (;;) {
/* 196 */             out.write("\n\n\n\n\n\n\n\t          \t\t<table width=\"95%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\">\n\t        \t\t\t<tr>\n\t                \t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t                \t\t\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 197 */             if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*     */               return;
/* 199 */             out.write("</td>\n\t              \t\t\t</tr>\n\t            \t\t</table>\n\t            \t\t<br>\n\n\t\t");
/* 200 */             int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 201 */             msg = (String)_jspx_page_context.findAttribute("msg");
/* 202 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/* 205 */           if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 206 */             out = _jspx_page_context.popBody();
/*     */           }
/*     */         }
/* 209 */         if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 210 */           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*     */         }
/*     */         else {
/* 213 */           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 214 */           out.write(10);
/* 215 */           out.write(9);
/* 216 */           out.write(9);
/*     */           
/* 218 */           MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 219 */           _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 220 */           _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
/*     */           
/* 222 */           _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 223 */           int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 224 */           if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*     */             for (;;) {
/* 226 */               out.write("\n\n\t\t\t\t\t<table width=\"95%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t\t\t\t\t\t<tr>\n\t                \t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t                \t\t\t\t<td width=\"95%\" class=\"message\">\n\t                \t\t\t\t\t");
/*     */               
/* 228 */               MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 229 */               _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 230 */               _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */               
/* 232 */               _jspx_th_html_005fmessages_005f1.setId("msg");
/*     */               
/* 234 */               _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 235 */               int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 236 */               if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 237 */                 String msg = null;
/* 238 */                 if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 239 */                   out = _jspx_page_context.pushBody();
/* 240 */                   _jspx_th_html_005fmessages_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 241 */                   _jspx_th_html_005fmessages_005f1.doInitBody();
/*     */                 }
/* 243 */                 msg = (String)_jspx_page_context.findAttribute("msg");
/*     */                 for (;;) {
/* 245 */                   out.write("\n\t                \t  \t\t\t\t\t");
/* 246 */                   if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*     */                     return;
/* 248 */                   out.write("<br>\n\t\t\t\t\t\t\t\t");
/* 249 */                   int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 250 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/* 251 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 254 */                 if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 255 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 258 */               if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 259 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*     */               }
/*     */               
/* 262 */               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 263 */               out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t<br>\n\t\t");
/* 264 */               int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 265 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 269 */           if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 270 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */           }
/*     */           else {
/* 273 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 274 */             out.write("\n\n\n\n\t\t<table width=\"100%\" height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n\t\t<tr>\n\t\t<td>&nbsp;<span class=\"headingboldwhite\">");
/* 275 */             out.print(FormatUtil.getString("am.webclient.displayname.title.text"));
/* 276 */             out.write("</span>\n\t\t</td>\n\t\t</tr>\n</table>\n\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  class=\"lrtborder\" width=\"98%\" style=\"margin-top:10px;margin-left:6px;margin-right:10px\">\n\n\n\n\t\t");
/*     */             
/* 278 */             if (displaynames != null)
/*     */             {
/* 280 */               out.write("\n\n\t\t\t<tr>\n\t\t\t\t<td class=\"lightbg\" style=\"height:30px\">&nbsp;<b><span class=\"bodytextbold\">");
/* 281 */               out.print(FormatUtil.getString("am.webclient.displayname.selected.text"));
/* 282 */               out.write("</span></b></td>\n\t\t  \t</tr>\n\t\t \t<tr align=\"left\"><td>\n\t\t \t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"   width=\"100%\" align=\"center\">\n\n\n\t\t  \t\t");
/*     */               
/* 284 */               int i = 0;
/* 285 */               for (i = 0; i < resourceids.size(); i++) {
/* 286 */                 String resid = (String)resourceids.get(i);
/* 287 */                 String displayname = (String)name_ids.get(resid);
/* 288 */                 int len = displayname.length();
/* 289 */                 String bgclass = "whitegrayborder";
/* 290 */                 if (i % 2 != 0) {
/* 291 */                   bgclass = "bodytext";
/*     */                 } else {
/* 293 */                   bgclass = "bodytext";
/*     */                 }
/*     */                 
/*     */ 
/* 297 */                 out.write("\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td colspan=\"3\" style=\"height:10px;\"></td>\n\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"1%\">&nbsp;</td>\n\t\t\t\t\t\t\t<td width=\"49%\" class=");
/* 298 */                 out.print(bgclass);
/* 299 */                 out.write(62);
/* 300 */                 out.print(displayname);
/* 301 */                 out.write("</td>\n\t\t\t\t\t\t\t<td height=\"50%\" class=");
/* 302 */                 out.print(bgclass);
/* 303 */                 out.write("><input type=\"text\" name=\"");
/* 304 */                 out.print(resid);
/* 305 */                 out.write("\" value=\"");
/* 306 */                 out.print(displayname);
/* 307 */                 out.write("\" size=\"50%\" class=\"formtext\"/></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*     */               }
/* 309 */               out.write("\n\t\t\t\t</table>\n\n\t\t\t\t<tr><td>\n\t\t\t\t<img src=\"../images/spacer.gif\" height=\"5\" width=\"5\">\n\t\t\t\t</td></tr>\n\t\t\t\t</table>\n\t\t\t\t</td></tr>\n\t\t\t\t</table>\n       \t\t\t\t<table width=\"98%\" border=\"0\" class=\"lrtbdarkborder\" cellpadding=\"0\" cellspacing=\"0\" align=\"left\" style=\"margin-top:0px;margin-left:6px;margin-right:10px\">\n       \t\t\t\t<tr>\n       \t\t\t\t\t<td height=\"35\"  align=\"center\" class=\"tablebottom\">\n        \t   \t\t\t\t<input name=\"Submit\" type=\"button\" class=\"buttons\" value='");
/* 310 */               out.print(FormatUtil.getString("am.webclient.displayname.apply.text"));
/* 311 */               out.write("' onClick=\"javascript:submitForm();opener.location.reload(true);\">\n        \t   \t\t\t\t<input name=\"GoBack\" type=\"button\" class=\"buttons\" value='");
/* 312 */               out.print(FormatUtil.getString("webclient.common.printview.button.close"));
/* 313 */               out.write("'  onClick=\"opener.location.reload(true);self.close();\">&nbsp;&nbsp;\n       \t\t\t\t\t</td>\n\t\t\t\t</tr>\n    \t\t\t\t</table>\n\t\t");
/*     */             }
/* 315 */             out.write("\n\t</form>\n\n");
/*     */           }
/* 317 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 318 */         out = _jspx_out;
/* 319 */         if ((out != null) && (out.getBufferSize() != 0))
/* 320 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 321 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 324 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 330 */     PageContext pageContext = _jspx_page_context;
/* 331 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 333 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 334 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 335 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 337 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 339 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 340 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 341 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 342 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 343 */       return true;
/*     */     }
/* 345 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 346 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 351 */     PageContext pageContext = _jspx_page_context;
/* 352 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 354 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 355 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 356 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 358 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 359 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 360 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 362 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 363 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 364 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 368 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 369 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 370 */       return true;
/*     */     }
/* 372 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 373 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 378 */     PageContext pageContext = _jspx_page_context;
/* 379 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 381 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 382 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 383 */     _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fmessages_005f0);
/*     */     
/* 385 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*     */     
/* 387 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 388 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 389 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 390 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 391 */       return true;
/*     */     }
/* 393 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 394 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 399 */     PageContext pageContext = _jspx_page_context;
/* 400 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 402 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 403 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 404 */     _jspx_th_bean_005fwrite_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fmessages_005f1);
/*     */     
/* 406 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*     */     
/* 408 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 409 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 410 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 411 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 412 */       return true;
/*     */     }
/* 414 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 415 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\EditDisplaynames_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */