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
/*     */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*     */ 
/*     */ public final class UpdateIp_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  27 */   private static final JspFactory _jspxFactory = ;
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
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  44 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  49 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  56 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  60 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  61 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  62 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  63 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  64 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*  65 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  66 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  73 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  76 */     JspWriter out = null;
/*  77 */     Object page = this;
/*  78 */     JspWriter _jspx_out = null;
/*  79 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  83 */       response.setContentType("text/html;charset=UTF-8");
/*  84 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  86 */       _jspx_page_context = pageContext;
/*  87 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  88 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  89 */       session = pageContext.getSession();
/*  90 */       out = pageContext.getOut();
/*  91 */       _jspx_out = out;
/*     */       
/*  93 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/*  94 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  96 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*     */       
/*  98 */       String resids = request.getParameter("resids");
/*  99 */       if ((com.adventnet.appmanager.util.Constants.sqlManager) && (request.getParameter("method") == null))
/*     */       {
/* 101 */         String resids_forquery = "(";
/* 102 */         StringTokenizer nameAfterComma = new StringTokenizer(resids, ",");
/* 103 */         while (nameAfterComma.hasMoreTokens()) {
/* 104 */           String temp = nameAfterComma.nextToken();
/* 105 */           resids_forquery = resids_forquery + "" + temp + ", ";
/*     */         }
/* 107 */         if (!resids_forquery.equals("(")) {
/* 108 */           resids_forquery = resids_forquery.substring(0, resids_forquery.length() - 2);
/* 109 */           resids_forquery = resids_forquery + ")";
/*     */         }
/* 111 */         resids = "";
/* 112 */         String ridsAndOldIpQuery = "select sqlhost.HOSTID from AM_ManagedObject amo join SQLDBM_SQL_HOST_MAPPING sqlhost on amo.RESOURCEID=sqlhost.SQLID where amo.RESOURCEID in" + resids_forquery;
/* 113 */         ResultSet results = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(ridsAndOldIpQuery);
/* 114 */         while (results.next()) {
/* 115 */           if (!results.isLast()) {
/* 116 */             resids = resids + results.getString("HOSTID") + ",";
/*     */           }
/*     */           else {
/* 119 */             resids = resids + results.getString("HOSTID");
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 126 */       out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\nfunction submitForm()   {\n\t");
/* 127 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/* 129 */       out.write(10);
/* 130 */       out.write(9);
/*     */       
/* 132 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 133 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 134 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */       
/* 136 */       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 137 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 138 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */         for (;;) {
/* 140 */           out.write("\n\tfor(i=0;i<document.AMActionForm.elements.length;i++)   {\n\tvar ipAddress = document.AMActionForm.elements[i].value;\n\n\n\tif((document.AMActionForm.elements[i].id!=\"refreshHost\") && (document.AMActionForm.elements[i].id!=\"replaceHost\"))\n\tif((ipAddress!=\"");
/* 141 */           out.print(FormatUtil.getString("am.webclient.updateip.apply.text"));
/* 142 */           out.write("\"))\n\tif((ipAddress!=\"");
/* 143 */           out.print(FormatUtil.getString("webclient.common.printview.button.close"));
/* 144 */           out.write("\"))\n\t\t{\n\t\t\tverifyIP(ipAddress);\n\t\t}\n\t\tif(ipAddress=='')   {\n\t  \t\talert(\"");
/* 145 */           out.print(FormatUtil.getString("am.webclient.common.updateip.text.empty"));
/* 146 */           out.write("\"); //NO I18N\n\t  \t\treturn;\n\t\t}\n\t}\n\n\n\n\n\n\t\tdocument.AMActionForm.action=\"/updateIpAction.do?method=updateIPAddress&resids=");
/* 147 */           out.print(resids);
/* 148 */           out.write("&refreshHost=\"+AMActionForm.type[0].checked+\"&replaceHost=\"+AMActionForm.type[1].checked;\n\n\t\tdocument.AMActionForm.submit();\n\t");
/* 149 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 150 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 154 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 155 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*     */       }
/*     */       else {
/* 158 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 159 */         out.write("\n}\nfunction setFocusProperTextField()  {\n\tvar pos = document.AMActionForm.elements.length;\n    \tif(pos > 0)  {\n\t\tif(document.AMActionForm.elements.length >=2)  {\n              \t\tpos = 1;\n          \t}   else   {\n          \t\treturn;\n          \t}\n\t\tfor(i=0;i<document.AMActionForm.elements.length;i++)   {\n\t\t\tif(document.AMActionForm.elements[i].type =='text')   {\n\t\t\t\ttry    {\n\t\t\t\t\tdocument.AMActionForm.elements[i].focus();\n\t\t\t\t\tbreak;\n\t\t\t\t}   catch (e) {}\n\n                \t}\n            \t}\n    \t}\n}\nfunction doInitStuffOnBodyLoad()  {\n\n    \tsetFocusProperTextField();\n    \tif (window.myOnLoad)   {\n\t\tmyOnLoad();\n    \t}\n}\n\n//SANGEETH::::VALIDATE IP\nfunction validateIPAddress (IPvalue)\n{\n\tvar re = /^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$/;\n\tif (re.test(IPvalue))\n\t{\n\t\tvar parts = IPvalue.split(\".\");\n\t\tif (parseInt(parseFloat(parts[0])) == 0)\n\t\t{\n\t\t\treturn false;\n\t\t}\n\t\tfor (var i=0; i<parts.length; i++)\n\t\t{\n\t\t\tif (parseInt(parseFloat(parts[i])) > 255)\n\t\t\t{\n\t\t\t\treturn false;\n\t\t\t}\n\t\t}\n\t\treturn true;\n\t}\n\telse\n\t{\n\t\treturn false;\n");
/* 160 */         out.write("\t}\n}\n\nfunction verifyIP(IPAddress, txtname)\n{\n\n\tif (validateIPAddress(IPAddress))\n\t{\n\t\treturn true;\n\t}\n\telse\n\t{\n\t\talert(\"");
/* 161 */         out.print(FormatUtil.getString("Enter a valid IP Address"));
/* 162 */         out.write("\");     // NO I18N\n\t\teval(\"document.AMActionForm.\"+txtname+\".focus()\");\n\t\treturn ;\n\t}\n}\n//SANGEETH::::VALIDATE IP\n\n</script>\n");
/*     */         
/*     */ 
/* 165 */         String resids_forquery = "(";
/* 166 */         StringTokenizer nameAfterComma = new StringTokenizer(resids, ",");
/* 167 */         while (nameAfterComma.hasMoreTokens()) {
/* 168 */           String temp = nameAfterComma.nextToken();
/* 169 */           resids_forquery = resids_forquery + "" + temp + ", ";
/*     */         }
/* 171 */         if (!resids_forquery.equals("(")) {
/* 172 */           resids_forquery = resids_forquery.substring(0, resids_forquery.length() - 2);
/* 173 */           resids_forquery = resids_forquery + ")";
/*     */         }
/*     */         
/* 176 */         String query = "select RESOURCEID, DISPLAYNAME, RESOURCENAME, IPADDRESS from AM_ManagedObject join TopoObject on AM_ManagedObject.RESOURCENAME=TopoObject.NAME where RESOURCEID in " + resids_forquery;
/* 177 */         System.out.println("JSPQRY::::::" + query);
/* 178 */         ArrayList resourceids = new ArrayList();
/* 179 */         Hashtable name_ids = new Hashtable();
/* 180 */         Hashtable ip_ids = new Hashtable();
/* 181 */         Properties ipAddresss = new Properties();
/* 182 */         ResultSet results = null;
/*     */         try {
/* 184 */           results = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(query);
/* 185 */           while (results.next()) {
/* 186 */             String dispname = results.getString("DISPLAYNAME");
/* 187 */             dispname = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(dispname);
/* 188 */             String ipAddress = results.getString("IPADDRESS");
/* 189 */             String id = results.getString("RESOURCEID");
/* 190 */             resourceids.add(id);
/* 191 */             name_ids.put(id, dispname);
/* 192 */             ip_ids.put(id, ipAddress);
/* 193 */             ipAddresss.setProperty(id, dispname);
/*     */           }
/* 195 */           request.setAttribute("resourceids", resourceids);
/* 196 */           request.setAttribute("nameids", name_ids);
/* 197 */           request.setAttribute("ipids", ip_ids);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */           try
/*     */           {
/* 204 */             if (results != null) {
/* 205 */               results.close();
/*     */             }
/*     */           }
/*     */           catch (Exception exc) {}
/*     */           
/* 210 */           out.write("\n<title>");
/*     */         }
/*     */         catch (Exception exception)
/*     */         {
/* 199 */           exception.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/*     */           try {
/* 204 */             if (results != null) {
/* 205 */               results.close();
/*     */             }
/*     */           }
/*     */           catch (Exception exc) {}
/*     */         }
/*     */         
/* 211 */         out.print(FormatUtil.getString("am.webclient.updateip.title.text"));
/* 212 */         out.write("</title>\n<body onLoad=\"javascript:doInitStuffOnBodyLoad()\"></body>\n<form  action=\"/UpdateIpAction\" method=\"post\" name=\"AMActionForm\" style=\"display:inline\" >\n<table width=\"100%\" height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n\t<tr>\n\t\t<td>&nbsp;<span class=\"headingboldwhite\">");
/* 213 */         out.print(FormatUtil.getString("am.webclient.updateip.title.text"));
/* 214 */         out.write("</span></td>\n\t</tr>\n</table>\n\t\t");
/*     */         
/* 216 */         MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 217 */         _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 218 */         _jspx_th_html_005fmessages_005f0.setParent(null);
/*     */         
/* 220 */         _jspx_th_html_005fmessages_005f0.setId("msg");
/*     */         
/* 222 */         _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 223 */         int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 224 */         if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 225 */           String msg = null;
/* 226 */           if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 227 */             out = _jspx_page_context.pushBody();
/* 228 */             _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 229 */             _jspx_th_html_005fmessages_005f0.doInitBody();
/*     */           }
/* 231 */           msg = (String)_jspx_page_context.findAttribute("msg");
/*     */           for (;;) {
/* 233 */             out.write("\n\n\t          \t\t<table width=\"98%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\" style=\"margin-top:10px;\">\n\t        \t\t\t<tr>\n\t                \t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t                \t\t\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 234 */             if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*     */               return;
/* 236 */             out.write("</td>\n\t              \t\t\t</tr>\n\t            \t\t</table>\n\t            \t\t<br>\n\n\t\t");
/* 237 */             int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 238 */             msg = (String)_jspx_page_context.findAttribute("msg");
/* 239 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/* 242 */           if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 243 */             out = _jspx_page_context.popBody();
/*     */           }
/*     */         }
/* 246 */         if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 247 */           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*     */         }
/*     */         else {
/* 250 */           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 251 */           out.write(10);
/* 252 */           out.write(9);
/* 253 */           out.write(9);
/*     */           
/* 255 */           MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 256 */           _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 257 */           _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
/*     */           
/* 259 */           _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 260 */           int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 261 */           if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*     */             for (;;) {
/* 263 */               out.write("\n\t\t\t\t\t<script>opener.location.reload(true);</script>\n\t\t\t\t\t<table width=\"98%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\" style=\"margin-top:10px;\">\n\t\t\t\t\t\t<tr>\n\t                \t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t                \t\t\t\t<td width=\"95%\" class=\"message\">\n\t                \t\t\t\t\t");
/*     */               
/* 265 */               MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 266 */               _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 267 */               _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */               
/* 269 */               _jspx_th_html_005fmessages_005f1.setId("msg");
/*     */               
/* 271 */               _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 272 */               int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 273 */               if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 274 */                 String msg = null;
/* 275 */                 if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 276 */                   out = _jspx_page_context.pushBody();
/* 277 */                   _jspx_th_html_005fmessages_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 278 */                   _jspx_th_html_005fmessages_005f1.doInitBody();
/*     */                 }
/* 280 */                 msg = (String)_jspx_page_context.findAttribute("msg");
/*     */                 for (;;) {
/* 282 */                   out.write("\n\t                \t  \t\t\t\t\t");
/* 283 */                   if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*     */                     return;
/* 285 */                   out.write("<br>\n\t\t\t\t\t\t\t\t");
/* 286 */                   int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 287 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/* 288 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 291 */                 if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 292 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 295 */               if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 296 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*     */               }
/*     */               
/* 299 */               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 300 */               out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t<br>\n\t\t");
/* 301 */               int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 302 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 306 */           if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 307 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */           }
/*     */           else {
/* 310 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 311 */             out.write("\n\n\n\t\t");
/*     */             
/* 313 */             if ((ipAddresss == null) || (ipAddresss.size() <= 0))
/*     */             {
/* 315 */               out.write("\n\t\t<table width=\"98%\" border=\"0\" class=\"\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:10px;\">\n\t\t<tr>\n\t\t\t<td class=\"lightbg\" align=\"center\">&nbsp;<b><span class=\"bodytextbold\">");
/* 316 */               out.print(FormatUtil.getString("There is no host monitor available in your selected monitors. Please select atleast one host monitor to update ip address."));
/* 317 */               out.write("</span></b></td>\n\t\t</tr>\n\t\t</table>\n\t\t");
/*     */             }
/* 319 */             out.write("\n\n\t\t");
/*     */             
/* 321 */             if ((ipAddresss != null) && (ipAddresss.size() > 0))
/*     */             {
/* 323 */               out.write("\n\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"98%\" align=\"center\" style=\"margin-top:10px;\">\n\t\t \t\t  <tr>\n\t\t\t\t\t<td style=\"padding-bottom:8px;padding-top:5px;\" class=\"columnheadingdelete\" height=\"40\" width=\"30%\">\n\t\t\t\t\t\t <input id=\"refreshHost\" NAME=\"type\" value=\"refreshHost\" type=\"radio\" checked style=\"position:relative;top:3px;left:4px;\"/><span>&nbsp;");
/* 324 */               out.print(FormatUtil.getString("am.webclient.updateip.refreshhost"));
/* 325 */               out.write("</span>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td style=\"padding-bottom:8px;padding-top:5px;\" class=\"columnheadingdelete\" height=\"40\" width=\"60%\">\n\t\t\t\t\t\t <input id=\"replaceHost\" NAME=\"type\" value=\"replaceHost\" type=\"radio\" style=\"position:relative;top:3px;\"/><span>");
/* 326 */               out.print(FormatUtil.getString("am.webclient.updateip.replaceip"));
/* 327 */               out.write("</span>\n\t\t\t\t\t</td>\n\t\t\t\t </tr>\n\t\t\t</table>\t\t\n\t\t\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  class=\"lrborder\" width=\"98%\" align=\"center\">\n\t\t \t<tr align=\"left\"><td>\n\t\t \t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" align=\"center\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"columnheading\" width=\"14%\" height=\"28\">");
/* 328 */               out.print(FormatUtil.getString("Display Name"));
/* 329 */               out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\" width=\"14%\" height=\"28\">");
/* 330 */               out.print(FormatUtil.getString("Existing IP Address"));
/* 331 */               out.write("</td>\n\t\t\t\t\t<td class=\"columnheading\" width=\"14%\" height=\"28\">");
/* 332 */               out.print(FormatUtil.getString("New IP Address"));
/* 333 */               out.write("</td>\n\t\t\t\t\t\t</tr>\n\n\t\t  \t\t");
/*     */               
/* 335 */               int i = 0;
/* 336 */               for (i = 0; i < resourceids.size(); i++) {
/* 337 */                 String resid = (String)resourceids.get(i);
/* 338 */                 String displayname = (String)name_ids.get(resid);
/* 339 */                 String ipAddress = (String)ip_ids.get(resid);
/* 340 */                 int len = ipAddress.length();
/* 341 */                 String bgclass = "whitegrayborder";
/* 342 */                 if (i % 2 != 0) {
/* 343 */                   bgclass = "yellowgrayborder";
/*     */                 } else {
/* 345 */                   bgclass = "whitegrayborder";
/*     */                 }
/*     */                 
/*     */ 
/* 349 */                 out.write("\n\n\t\t\t\t\t\t<tr >\n\t\t\t\t\t\t\t<td class=");
/* 350 */                 out.print(bgclass);
/* 351 */                 out.write(62);
/* 352 */                 out.print(displayname);
/* 353 */                 out.write("&nbsp;&nbsp;</td>\n\t\t\t\t\t\t\t<td class=");
/* 354 */                 out.print(bgclass);
/* 355 */                 out.write(" style=\"position:relative;height:40px;\"><input type=\"text\" disabled=\"disabled\" name=\"");
/* 356 */                 out.print(resid);
/* 357 */                 out.write("\" value=\"");
/* 358 */                 out.print(ipAddress);
/* 359 */                 out.write("\" size=\"30%\" class=\"formtext\"/> </td>\n\t\t\t\t\t\t\t<td class=");
/* 360 */                 out.print(bgclass);
/* 361 */                 out.write("><input type=\"text\" name=\"");
/* 362 */                 out.print(resid);
/* 363 */                 out.write("\" value=\"");
/* 364 */                 out.print(ipAddress);
/* 365 */                 out.write("\" size=\"30%\" class=\"formtext\" onChange=\"verifyIP(this.value, ");
/* 366 */                 out.print(resid);
/* 367 */                 out.write(")\" /> </td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*     */               }
/* 369 */               out.write("\n\n\t\t\t\t</table>\n\t\t\t\n\t\t\t\t</td></tr>\n\t\t\t\t</table>\n       \t\t\t\t<table width=\"98%\" border=\"0\" class=\"lrbborder\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\">\n       \t\t\t\t<tr>\n       \t\t\t\t\t<td height=\"35\"  align=\"center\" class=\"tablebottom\">\n        \t   \t\t\t\t<input name=\"Submit\" type=\"button\" class=\"buttons\" value=\"");
/* 370 */               out.print(FormatUtil.getString("am.webclient.updateip.apply.text"));
/* 371 */               out.write("\" onClick=\"javascript:submitForm();\">\n        \t   \t\t\t\t<input name=\"GoBack\" type=\"button\" class=\"buttons\" value=\"");
/* 372 */               out.print(FormatUtil.getString("webclient.common.printview.button.close"));
/* 373 */               out.write("\"  onClick=\"self.close();\">&nbsp;&nbsp;\n       \t\t\t\t\t</td>\n\t\t\t\t</tr>\n    \t\t\t\t</table>\n\n\n\n\n        </br>\n     <table width=\"98%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t\t<tr>\n\t\t <td class=\"helpCardHdrTopLeft\"/>\n\t\t <td class=\"helpCardHdrTopBg\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t<tr>\n\t\t <td width=\"100\" valign=\"middle\" align=\"left\" class=\"helpCardContentBg\"><span class=\"helpHdrTxt\">");
/* 374 */               if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*     */                 return;
/* 376 */               out.write("</span><img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\"/></td> ");
/* 377 */               out.write("\n\t\t <td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t <td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t</tr>\n\t\t</table></td>\n\t\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\t\t </tr>\n\t\t <tr>\n\t\t <td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t <td valign=\"top\">\n\t\t<!--//include your Helpcard template table here..-->\n\n\n\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n\t    <tr>\n\t    <td style=\"padding-top: 10px;\" class=\"boxedContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t      <tr>\n\t        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t        <tr>\n\t          <td class=\"hCardInnerTopLeft\"/>\n\t          <td class=\"hCardInnerTopBg\"/>\n\t          <td class=\"hCardInnerTopRight\"/>\n\t        </tr>\n\t        <tr>\n\t          <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n\t            <td class=\"hCardInnerBoxBg product-help\">\n\t           <span class=\"bodytext\">\n\t             ");
/* 378 */               out.print(FormatUtil.getString("am.webclient.servers.helpcard.updateip"));
/* 379 */               out.write("\n\t\t\t  </span>\n\n\t            </td>\n\t\t\t\t  <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t </table></td>\n\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t  </tr>\n\t\t\t </table>\n\t\t   </td> <td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t <tr>\n\t\t\t\t<td class=\"helpCardMainBtmLeft\"/>\n\t\t\t\t<td class=\"helpCardMainBtmBg\"/>\n\t\t\t\t<td class=\"helpCardMainBtmRight\"/>\n\t\t\t </tr>\n\t\t\t</table>\n\n\n\n\n\t\t");
/*     */             }
/* 381 */             out.write("\n\t</form>\n\n");
/*     */           }
/* 383 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 384 */         out = _jspx_out;
/* 385 */         if ((out != null) && (out.getBufferSize() != 0))
/* 386 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 387 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 390 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 396 */     PageContext pageContext = _jspx_page_context;
/* 397 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 399 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 400 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 401 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 403 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 405 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 406 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 407 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 408 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 409 */       return true;
/*     */     }
/* 411 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 412 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 417 */     PageContext pageContext = _jspx_page_context;
/* 418 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 420 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 421 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 422 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 424 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 425 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 426 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 428 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 429 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 430 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 434 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 435 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 436 */       return true;
/*     */     }
/* 438 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 439 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 444 */     PageContext pageContext = _jspx_page_context;
/* 445 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 447 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 448 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 449 */     _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fmessages_005f0);
/*     */     
/* 451 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*     */     
/* 453 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 454 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 455 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 456 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 457 */       return true;
/*     */     }
/* 459 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 460 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 465 */     PageContext pageContext = _jspx_page_context;
/* 466 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 468 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 469 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 470 */     _jspx_th_bean_005fwrite_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fmessages_005f1);
/*     */     
/* 472 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*     */     
/* 474 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 475 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 476 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 477 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 478 */       return true;
/*     */     }
/* 480 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 481 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 486 */     PageContext pageContext = _jspx_page_context;
/* 487 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 489 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 490 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 491 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/*     */     
/* 493 */     _jspx_th_fmt_005fmessage_005f0.setKey("Help Card");
/* 494 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 495 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 496 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 497 */       return true;
/*     */     }
/* 499 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 500 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\UpdateIp_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */