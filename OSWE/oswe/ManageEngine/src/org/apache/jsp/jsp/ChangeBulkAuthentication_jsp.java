/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.Vector;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.html.FormTag;
/*     */ import org.apache.struts.taglib.html.PasswordTag;
/*     */ import org.apache.struts.taglib.html.TextTag;
/*     */ import org.apache.struts.taglib.logic.IterateTag;
/*     */ import org.apache.struts.taglib.logic.NotPresentTag;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class ChangeBulkAuthentication_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  24 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
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
/*  49 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  55 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  59 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  60 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  61 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  62 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/*  63 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/*  64 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*  65 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*  66 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
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
/*  93 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/*  94 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  96 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\nfunction submitForm1()\n{\n\t");
/*  97 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/*  99 */       out.write(10);
/* 100 */       out.write(9);
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
/* 111 */           out.write("\n\tvar timeout=document.AMActionForm.timeout.value;\n\tif(timeout=='' || timeout<1)\n\t{\n\t  alert('");
/* 112 */           out.print(FormatUtil.getString("am.webclient.common.validpollinginterval.text"));
/* 113 */           out.write("');\n\t  return;\n\t}\n\t");
/* 114 */           if ("RBM".equalsIgnoreCase(request.getParameter("networkName")))
/*     */           {
/* 116 */             out.write("\n\t\tif(timeout<10)\n\t\t{\n\t\t\t alert('");
/* 117 */             out.print(FormatUtil.getString("am.webclient.rbm.alert.pollinginterval.text"));
/* 118 */             out.write("');\n\t\t     return;\n\t\t}\n\t");
/*     */           }
/* 120 */           out.write("\n\tdocument.AMActionForm.action=\"/adminAction.do?method=bulkUpdate\";\n\tdocument.AMActionForm.submit();\n\t");
/* 121 */           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 122 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 126 */       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 127 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*     */       }
/*     */       else {
/* 130 */         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 131 */         out.write("\n}\n\nfunction showuserName()\n{\n\ttoggleDiv('user_info');\n}\nfunction myOnLoad()\n{\n\t");
/*     */         
/* 133 */         if (request.getParameter("polling") != null)
/*     */         {
/*     */ 
/* 136 */           out.write("\n\t document.AMActionForm.timeout.focus();\n    ");
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 142 */           out.write("\n\tdocument.AMActionForm.password.focus();\n    ");
/*     */         }
/* 144 */         String toclose = request.getParameter("toclose");
/* 145 */         if ((toclose != null) && (toclose.equals("true")))
/*     */         {
/* 147 */           String methodName = request.getParameter("methodName");
/* 148 */           String networkName = request.getParameter("networkName");
/* 149 */           String groupName = request.getParameter("groupName");
/* 150 */           String viewmontype = request.getParameter("viewmontype");
/* 151 */           String showmanage = request.getParameter("showmanage");
/* 152 */           String fromWhere = "bulkupdate";
/* 153 */           String enterpriseSearch = System.getProperty("EnterpriseSearch");
/* 154 */           String isFromEnterpriseSearch = request.getParameter("isFromEnterpriseSearch");
/*     */           
/* 156 */           if (request.getParameter("pollingInterval") != null)
/*     */           {
/* 158 */             fromWhere = "pollingMessage";
/*     */           }
/* 160 */           if ((request.getParameter("haid") != null) && (!"".equals(request.getParameter("haid"))))
/*     */           {
/*     */ 
/* 163 */             out.write("\n                window.opener.location.href=\"/showapplication.do?haid=");
/* 164 */             if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*     */               return;
/* 166 */             out.write("&method=showApplication&fromwhere=pollingMessage\";\n                window.close();\n\n        ");
/*     */ 
/*     */ 
/*     */           }
/* 170 */           else if (methodName.equals("showResourceTypesAll"))
/*     */           {
/*     */ 
/* 173 */             out.write("\n\t\tvar redirecturl = \"/showresource.do?group=All&method=showResourceTypesAll&fromwhere=");
/* 174 */             out.print(fromWhere);
/* 175 */             out.write("&viewmontype=");
/* 176 */             out.print(viewmontype);
/* 177 */             out.write("&showmanage=");
/* 178 */             out.print(showmanage);
/* 179 */             out.write("\";\n\t\tvar urlstr = window.opener.document.URL;\n\t\tif(urlstr.indexOf(\"Search.do\")!=-1)\n\t\t{\n\t\t\tvar eSearch = '");
/* 180 */             out.print(enterpriseSearch);
/* 181 */             out.write("' ;\t //No I18N\n\t\t\tvar isEE = '");
/* 182 */             out.print(isFromEnterpriseSearch);
/* 183 */             out.write("' ;\t //No I18N\n\n\t\t\tif (isEE != null && isEE.equalsIgnoreCase(\"true\")) //No I18N\n\t\t\t{\nredirecturl =   \"/SearchTemp.do?query=\"+window.opener.document.getElementById('searchword').value+\"&module=IT360_APM&performSearch=true&detailedView=true\"+\"&fromIndex=0&pageLength=25&noOfRows=25&startIndex=1&personality=ManagedObjectPersonality&isAPMsearchRedirect=true\"; //No I18N\n                    \t}\n \t\t    \telse if(eSearch !=null &&  eSearch.equalsIgnoreCase(\"true\") )\n                    \t{\n\t\t\t\tredirecturl=\"/Search.do?old=truequery=\"+window.opener.document.getElementById('searchword').value; //No I18N\t\t\t     \n\t\t    \t}\n\t\t    \telse\n\t\t    \t{\n\t\t\t\tredirecturl=\"/Search.do?query=\"+window.opener.document.getElementById('searchword').value; //No I18N\t\t\t    \n\t\t    \t}\n\t\t\t//redirecturl=\"/Search.do?query=\"+window.opener.document.getElementById('searchword').value; //No I18N\n\t\t}\n\t\twindow.close();\n\t\twindow.opener.location.href=redirecturl;\n\t\t");
/*     */ 
/*     */           }
/* 186 */           else if (methodName.equals("showMonitorGroupView"))
/*     */           {
/*     */ 
/* 189 */             out.write("\n\t\t window.close();\n\t\twindow.opener.location.href=\"/showresource.do?method=showMonitorGroupView&fromwhere=");
/* 190 */             out.print(fromWhere);
/* 191 */             out.write("&retaintree=");
/* 192 */             out.print(request.getParameter("retaintree"));
/* 193 */             out.write("\";\n\t\t");
/*     */ 
/*     */           }
/* 196 */           else if ((groupName != null) && (!groupName.equals("null")) && (!com.adventnet.appmanager.util.Constants.sqlManager))
/*     */           {
/*     */ 
/* 199 */             out.write("\n\t\t   window.close();\n\t\t   window.opener.location.href=\"/showresource.do?method=showResourceTypes&detailspage=true&group=");
/* 200 */             out.print(groupName);
/* 201 */             out.write("&fromwhere=");
/* 202 */             out.print(fromWhere);
/* 203 */             out.write("&viewmontype=");
/* 204 */             out.print(viewmontype);
/* 205 */             out.write("&showmanage=");
/* 206 */             out.print(showmanage);
/* 207 */             out.write("\";\n\t\t ");
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 212 */             out.write("\n\t\t   window.close();\n\t\t   window.opener.location.href=\"/showresource.do?&method=showResourceTypes&direct=true&network=");
/* 213 */             out.print(networkName);
/* 214 */             out.write("&detailspage=true&fromwhere=");
/* 215 */             out.print(fromWhere);
/* 216 */             out.write("&viewmontype=");
/* 217 */             out.print(viewmontype);
/* 218 */             out.write("&showmanage=");
/* 219 */             out.print(showmanage);
/* 220 */             out.write("\";\n\t\t  ");
/*     */           }
/*     */           
/* 223 */           if (com.adventnet.appmanager.util.Constants.sqlManager)
/*     */           {
/*     */ 
/* 226 */             out.write("\n\n\t\t   window.close();\n\t\t  // window.opener.location.href=\"/showresource.do?group=All&method=showResourceMSSQL\";\n       ");
/*     */           }
/*     */         }
/*     */         
/* 230 */         if (request.getParameter("polling") == null)
/*     */         {
/*     */ 
/* 233 */           out.write("\n\tdocument.AMActionForm.smtpauth.checked=false;\n\t");
/*     */         }
/* 235 */         out.write("\n}\n</script>\n");
/*     */         
/* 237 */         String resIDsString = "";
/* 238 */         String resids = request.getParameter("values");
/* 239 */         String unopengroups = request.getParameter("unopengroups");
/* 240 */         if ((unopengroups != null) && (unopengroups.trim().length() > 0)) {
/* 241 */           Vector<String> vresid = new Vector();
/* 242 */           StringTokenizer st = new StringTokenizer(unopengroups, "|");
/* 243 */           String str = "";
/* 244 */           while (st.hasMoreElements()) {
/* 245 */             str = st.nextToken();
/* 246 */             if ("-1".equals(str)) {
/* 247 */               com.adventnet.appmanager.util.ParentChildRelationalUtil.getUncategorizedMonitors(vresid);
/*     */             }
/*     */             else {
/* 250 */               com.adventnet.appmanager.util.ParentChildRelationalUtil.getAllChildMapper(vresid, str, true);
/*     */             }
/*     */           }
/* 253 */           StringBuffer sb = new StringBuffer();
/* 254 */           if (vresid.size() > 0) {
/* 255 */             java.util.Iterator<String> it = vresid.iterator();
/* 256 */             while (it.hasNext()) {
/* 257 */               sb.append((String)it.next() + ",");
/*     */             }
/*     */           }
/* 260 */           if (sb.length() > 1) {
/* 261 */             String unopnmonitors = sb.substring(0, sb.length() - 1);
/* 262 */             resids = resids + unopnmonitors;
/*     */           }
/*     */         }
/* 265 */         String resids_forquery = "(";
/* 266 */         StringTokenizer comma_token = new StringTokenizer(resids, ",");
/* 267 */         while (comma_token.hasMoreTokens())
/*     */         {
/* 269 */           String temp = comma_token.nextToken();
/* 270 */           resids_forquery = resids_forquery + "'" + temp + "',";
/*     */         }
/* 272 */         if (!resids_forquery.equals("("))
/*     */         {
/* 274 */           resids_forquery = resids_forquery.substring(0, resids_forquery.length() - 1);
/* 275 */           resids_forquery = resids_forquery + ")";
/*     */         }
/* 277 */         java.util.ArrayList monitor_types = new java.util.ArrayList();
/*     */         try
/*     */         {
/* 280 */           com.adventnet.appmanager.db.AMConnectionPool.getInstance();ResultSet rs_types = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt("select distinct type from AM_ManagedObject where resourceid in " + resids_forquery);
/* 281 */           while (rs_types.next())
/*     */           {
/* 283 */             monitor_types.add(rs_types.getString(1));
/*     */           }
/* 285 */           pageContext.setAttribute("collectiontypes", monitor_types);
/* 286 */           rs_types.close();
/*     */ 
/*     */         }
/*     */         catch (Exception exc)
/*     */         {
/* 291 */           exc.printStackTrace();
/*     */         }
/*     */         
/*     */ 
/*     */         try
/*     */         {
/* 297 */           com.adventnet.appmanager.db.AMConnectionPool.getInstance();ResultSet rs_mappingIds = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt("select displayname from AM_ManagedObject where resourceid in (select resourceid from CREDENTIALTORESOURCEMAPPING where resourceid in " + resids_forquery + ")");
/* 298 */           while (rs_mappingIds.next())
/*     */           {
/* 300 */             resIDsString = rs_mappingIds.getString(1) + "," + resIDsString;
/*     */           }
/* 302 */           if (resIDsString.endsWith(","))
/*     */           {
/* 304 */             resIDsString = resIDsString.substring(0, resIDsString.length() - 1);
/*     */           }
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 309 */           ex.printStackTrace();
/*     */         }
/*     */         
/*     */ 
/* 313 */         out.write("\n\n<title>");
/* 314 */         out.print(FormatUtil.getString("am.webclient.bulkupdate.title.text"));
/* 315 */         out.write("</title>\n<body onLoad=\"javascript:myOnLoad()\"></body>\n");
/*     */         
/* 317 */         FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 318 */         _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 319 */         _jspx_th_html_005fform_005f0.setParent(null);
/*     */         
/* 321 */         _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*     */         
/* 323 */         _jspx_th_html_005fform_005f0.setMethod("post");
/*     */         
/* 325 */         _jspx_th_html_005fform_005f0.setStyle("display:inline;");
/* 326 */         int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 327 */         if (_jspx_eval_html_005fform_005f0 != 0) {
/*     */           for (;;) {
/* 329 */             out.write("\n\n <table width=\"100%\" height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n <tr>\n ");
/*     */             
/* 331 */             if (request.getParameter("polling") != null)
/*     */             {
/*     */ 
/* 334 */               out.write("\n \t<td>&nbsp;<span class=\"headingboldwhite\">");
/* 335 */               out.print(FormatUtil.getString("am.webclient.bulkupdate.polling.text"));
/* 336 */               out.write("</span></td>\n");
/*     */             }
/*     */             else
/*     */             {
/* 340 */               out.write("\n    <td>&nbsp;<span class=\"headingboldwhite\">");
/* 341 */               out.print(FormatUtil.getString("am.webclient.bulkupdate.bulkupdate.text"));
/* 342 */               out.write("</span></td>\n ");
/*     */             }
/*     */             
/*     */ 
/* 346 */             out.write(" \n </tr>\n</table>\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\" style=\"margin-top:10px;margin-left:6px;margin-right:10px\">\n\t<input type=\"hidden\" name=\"values\" value=\"");
/* 347 */             out.print(resids);
/* 348 */             out.write("\"/>\n        <input type=\"hidden\" name=\"mappingIDs\" value=\"");
/* 349 */             out.print(resIDsString);
/* 350 */             out.write("\"/>\n\t<input type=\"hidden\" name=\"methodName\" value=\"");
/* 351 */             out.print(request.getParameter("methodName"));
/* 352 */             out.write("\"/>\n\t<input type=\"hidden\" name=\"isFromEnterpriseSearch\" value=\"");
/* 353 */             out.print(request.getParameter("isFromEnterpriseSearch"));
/* 354 */             out.write("\"/>\n\t<input type=\"hidden\" name=\"networkName\" value=\"");
/* 355 */             out.print(request.getParameter("networkName"));
/* 356 */             out.write("\"/>\n\t<input type=\"hidden\" name=\"groupName\" value=\"");
/* 357 */             out.print(request.getParameter("groupName"));
/* 358 */             out.write("\"/>\n\t<input type=\"hidden\" name=\"retaintree\" value=\"");
/* 359 */             out.print(request.getParameter("retaintree"));
/* 360 */             out.write("\"/>\n\t<input type=\"hidden\" name=\"viewmontype\" value=\"");
/* 361 */             out.print(request.getParameter("viewmontype"));
/* 362 */             out.write("\"/>\n\t<input type=\"hidden\" name=\"showmanage\" value=\"");
/* 363 */             out.print(request.getParameter("showmanage"));
/* 364 */             out.write("\" />\n\t<input type=\"hidden\" name=\"haid\" value=\"");
/* 365 */             if (_jspx_meth_c_005fout_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */               return;
/* 367 */             out.write("\" />\n");
/*     */             
/* 369 */             if (request.getParameter("polling") != null)
/*     */             {
/*     */ 
/* 372 */               out.write("\n\t<tr>\n\t\t<td style=\"height:10px;\"></td>\n\t</tr>\n\t <input type=\"hidden\" name=\"polling\" value=\"updatePolling\"/>\n\t\t<tr align=\"left\" >\n\t\t <td style=\"position:relative; top:10px;\">\n\t\t  <table cellspacing=\"0\" cellpadding=\"8\" border=\"0\"  class=\"\" width=\"95%\" align=\"center\" >\n\t\t     <tr>\n\t\t\t \t <td class=\"bodytext\" width=\"15%\">");
/* 373 */               out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 374 */               out.write("&nbsp;&nbsp;</td>\n\t\t      \t <td align=\"left\" width=\"30%\">");
/* 375 */               if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                 return;
/* 377 */               out.write("&nbsp;&nbsp;<span class=\"bodytext\">");
/* 378 */               out.print(FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/* 379 */               out.write("</span></td>\n\t\t     </tr>\n\t\t</table>\n\t\t</td></tr>\n\t\t<tr><td>\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t<tr><td style=\"height:5px;\"></td></tr>\n\t\t\t <tr>\n\t\t\t  <td height=\"27\"  align=\"center\" class=\"tablebottom\">\n\t\t\t   <input name=\"Submit\" type=\"button\" class=\"buttons btn_highlt\" value='");
/* 380 */               out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 381 */               out.write("' onClick=\"javascript:submitForm1();\">\n\t\t\t <input name=\"GoBack\" type=\"button\" class=\"buttons btn_link\" value='");
/* 382 */               out.print(FormatUtil.getString("webclient.common.printview.button.close"));
/* 383 */               out.write("'  onClick=\"window.close();\">&nbsp;&nbsp;\n\t\t\t </td>\n\t\t\t</tr>\n\t\t</table>\n\t  </td></tr>\t\n ");
/*     */ 
/*     */             }
/*     */             else
/*     */             {
/*     */ 
/* 389 */               out.write("\n\t <tr align=\"center\">\n\t  <td style=\"height:30px\" class=\"lightbg bodytext\" align=\"left\">&nbsp;&nbsp;<b>");
/* 390 */               out.print(FormatUtil.getString("am.webclient.bulkupdate.haveselectedfollowing.text"));
/* 391 */               out.write("</b>\n\t  </td>\n\t </tr>\n\t<tr>\n\n\n            <td   class=\"bodytext\"><ul class=\"bodytext\">\n\t  ");
/*     */               
/* 393 */               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 394 */               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 395 */               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_html_005fform_005f0);
/*     */               
/* 397 */               _jspx_th_logic_005fiterate_005f0.setName("collectiontypes");
/*     */               
/* 399 */               _jspx_th_logic_005fiterate_005f0.setId("attribute");
/*     */               
/* 401 */               _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*     */               
/* 403 */               _jspx_th_logic_005fiterate_005f0.setType("java.lang.String");
/* 404 */               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 405 */               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 406 */                 String attribute = null;
/* 407 */                 Integer i = null;
/* 408 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 409 */                   out = _jspx_page_context.pushBody();
/* 410 */                   _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 411 */                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */                 }
/* 413 */                 attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 414 */                 i = (Integer)_jspx_page_context.findAttribute("i");
/*     */                 for (;;) {
/* 416 */                   out.write(10);
/* 417 */                   out.write(9);
/* 418 */                   out.write(9);
/*     */                   
/* 420 */                   String bgclass = "whitegrayborder";
/* 421 */                   if (i.intValue() % 2 != 0)
/*     */                   {
/* 423 */                     bgclass = "yellowgrayborder";
/*     */                   }
/*     */                   
/* 426 */                   out.write("\n\t &nbsp;&nbsp;&nbsp;<li>");
/* 427 */                   out.print(attribute);
/* 428 */                   out.write("</li>\n\t  ");
/* 429 */                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 430 */                   attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 431 */                   i = (Integer)_jspx_page_context.findAttribute("i");
/* 432 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 435 */                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 436 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 439 */               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 440 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*     */               }
/*     */               
/* 443 */               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 444 */               out.write("\n\t  </ul>\n\t  </td>\n\t  </tr>\n        <tr align=\"left\">\n          <td>\n\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  class=\"\" width=\"95%\" align=\"center\">\n\t\t\t<tr>\n\t\t\t<td>\n\t\t\t <table width= \"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t  <tr>\n\t\t\t   <td  colspan=\"2\" class=\"bodytext\" height=\"40\" > <input type=\"checkbox\" name=\"smtpauth\" value=\"true\" onClick=\"javascript:showuserName()\">");
/* 445 */               out.print(FormatUtil.getString("am.webclient.bulkupdate.changeusername.text"));
/* 446 */               out.write("</td>\n\t\t\t\t  </tr>\n\t\t\t  <tr>\n\t\t\t   <td colspan=\"2\">\n\t\t\t    <div id=\"user_info\"   style=\"DISPLAY: none\">\n\t\t\t<table width=\"300\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t<tr>\n\t\t\t<td class=\"bodytext\" height=\"30\" width=\"100\">");
/* 447 */               out.print(FormatUtil.getString("Username"));
/* 448 */               out.write("&nbsp;&nbsp;</td>\n\t\t\t<td align=\"left\" width=\"200\">");
/* 449 */               if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                 return;
/* 451 */               out.write(" </td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</div>\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td colspan=\"2\">\n\t\t\t<table width=\"300\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t<tr>\n\t\t\t<td class=\"bodytext\" width=\"100\">");
/* 452 */               out.print(FormatUtil.getString("webclient.login.password"));
/* 453 */               out.write("</td>\n\t\t\t<td align=\"left\">");
/* 454 */               if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */                 return;
/* 456 */               out.write(" </td>\n\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td>\n\t\t\t<img src=\"../images/spacer.gif\" height=\"5\" width=\"5\"></td></tr>\n\t\t</table>\n\t  </td>\n\t</tr>\n\n   </table>\n   </td>\n   </tr>\n\t<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:0px;margin-left:6px;margin-right:10px\">\n\t       <tr>\n\t       <td height=\"27\"  align=\"center\" class=\"tablebottom\">\n\t           <input name=\"Submit\" type=\"button\" class=\"buttons btn_highlt\" value='");
/* 457 */               out.print(FormatUtil.getString("am.webclient.common.update.text"));
/* 458 */               out.write("' onClick=\"javascript:submitForm();\">\n\t           <input name=\"GoBack\" type=\"button\" class=\"buttons btn_link\" value='");
/* 459 */               out.print(FormatUtil.getString("webclient.common.printview.button.close"));
/* 460 */               out.write("'  onClick=\"window.close();\">&nbsp;&nbsp;\n\t       </td>\n\t        </tr>\n\t    </table>\n \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>&nbsp;</tr>\n        <tr><td  align=\"left\"class=\"bodytext\" width=\"15%\">&nbsp;");
/* 461 */               out.print(FormatUtil.getString("am.webclient.bulkupdate.note.text"));
/* 462 */               out.write("</td></tr>\n        <tr><td>&nbsp;</td></tr>\n        </table>\n");
/*     */             }
/* 464 */             out.write("\n</table>\n<script>\nfunction submitForm()\n{\n\n");
/* 465 */             if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*     */               return;
/* 467 */             out.write(10);
/* 468 */             out.write(9);
/*     */             
/* 470 */             NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 471 */             _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 472 */             _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_html_005fform_005f0);
/*     */             
/* 474 */             _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 475 */             int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 476 */             if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*     */               for (;;) {
/* 478 */                 out.write("\n\n    var username=document.AMActionForm.username.value;\n    var password=document.AMActionForm.password.value;\n    if(document.AMActionForm.smtpauth.checked==true && username=='')\n\t{\n\t\talert('");
/* 479 */                 out.print(FormatUtil.getString("am.webclient.login.jsalertforusername.text"));
/* 480 */                 out.write("');\n\tdocument.AMActionForm.username.focus();\n\treturn;\n\t}\n\tif(password=='')\n\t{\n\talert('");
/* 481 */                 out.print(FormatUtil.getString("am.webclient.login.jsalertforpassowrd.text"));
/* 482 */                 out.write("');\n\tdocument.AMActionForm.password.focus();\n\treturn;\n\t}\n\tif(confirm('");
/* 483 */                 out.print(FormatUtil.getString("am.webclient.common.alert.authentication.text"));
/* 484 */                 out.write("'))\n\t{\n                if(document.getElementsByName(\"mappingIDs\")[0].value != \"\")\n                {\n                    var devicesList = document.getElementsByName(\"mappingIDs\")[0].value;\n                    var message = '");
/* 485 */                 out.print(FormatUtil.getString("webclient.bulkUpdate.credentialMessage"));
/* 486 */                 out.write("'+\" [\"+'");
/* 487 */                 out.print(FormatUtil.getString("webclient.bulkUpdate.devicesList"));
/* 488 */                 out.write("'+\":\"+devicesList+\"]\";\n                    var toDelete = confirm(message);\n                    if(!toDelete)\n                    {\n                        return;\n                    }\n                }\n\t\tdocument.AMActionForm.action=\"/adminAction.do?method=bulkUpdate\";\n\t\tdocument.AMActionForm.submit();\n\t}\n\treturn;\n\t");
/* 489 */                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 490 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 494 */             if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 495 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*     */             }
/*     */             
/* 498 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 499 */             out.write("\n}\n</script>\n");
/* 500 */             int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 501 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 505 */         if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 506 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/*     */         }
/*     */         else {
/* 509 */           this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 510 */           out.write(10);
/* 511 */           out.write(10);
/*     */         }
/* 513 */       } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 514 */         out = _jspx_out;
/* 515 */         if ((out != null) && (out.getBufferSize() != 0))
/* 516 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 517 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 520 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 526 */     PageContext pageContext = _jspx_page_context;
/* 527 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 529 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 530 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 531 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 533 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 535 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 536 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 537 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 538 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 539 */       return true;
/*     */     }
/* 541 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 542 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 547 */     PageContext pageContext = _jspx_page_context;
/* 548 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 550 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 551 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 552 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 554 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 555 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 556 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 558 */         out.write("\n\t\talertUser();\n\treturn;\n\t");
/* 559 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 560 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 564 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 565 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 566 */       return true;
/*     */     }
/* 568 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 569 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 574 */     PageContext pageContext = _jspx_page_context;
/* 575 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 577 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 578 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 579 */     _jspx_th_c_005fout_005f1.setParent(null);
/*     */     
/* 581 */     _jspx_th_c_005fout_005f1.setValue("${param.haid}");
/* 582 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 583 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 584 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 585 */       return true;
/*     */     }
/* 587 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 588 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 593 */     PageContext pageContext = _jspx_page_context;
/* 594 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 596 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 597 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 598 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 600 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 601 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 602 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 603 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 604 */       return true;
/*     */     }
/* 606 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 607 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 612 */     PageContext pageContext = _jspx_page_context;
/* 613 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 615 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 616 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 617 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 619 */     _jspx_th_html_005ftext_005f0.setProperty("timeout");
/*     */     
/* 621 */     _jspx_th_html_005ftext_005f0.setSize("10%");
/*     */     
/* 623 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/* 624 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 625 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 626 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 627 */       return true;
/*     */     }
/* 629 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 630 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 635 */     PageContext pageContext = _jspx_page_context;
/* 636 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 638 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 639 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 640 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 642 */     _jspx_th_html_005ftext_005f1.setProperty("username");
/*     */     
/* 644 */     _jspx_th_html_005ftext_005f1.setSize("20%");
/*     */     
/* 646 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/* 647 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 648 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 649 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 650 */       return true;
/*     */     }
/* 652 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 653 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 658 */     PageContext pageContext = _jspx_page_context;
/* 659 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 661 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 662 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 663 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 665 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*     */     
/* 667 */     _jspx_th_html_005fpassword_005f0.setSize("20%");
/*     */     
/* 669 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/* 670 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 671 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 672 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 673 */       return true;
/*     */     }
/* 675 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 676 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 681 */     PageContext pageContext = _jspx_page_context;
/* 682 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 684 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 685 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 686 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*     */     
/* 688 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 689 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 690 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*     */       for (;;) {
/* 692 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 693 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 694 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 698 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 699 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 700 */       return true;
/*     */     }
/* 702 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 703 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ChangeBulkAuthentication_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */