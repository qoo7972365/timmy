/*     */ package org.apache.jsp.jsp.amazon;
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
/*     */ public final class DisableCloudWatch_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  94 */       String toEnable = "";
/*  95 */       String toDisable = "";
/*     */       
/*     */ 
/*  98 */       out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../../template/appmanager.js\"></SCRIPT>\n<script language=\"JavaScript1.2\">\nfunction submitForm(ids)   \n{\n\t");
/*  99 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/* 101 */       out.write(10);
/* 102 */       out.write(9);
/*     */       
/* 104 */       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 105 */       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 106 */       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */       
/* 108 */       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 109 */       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 110 */       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */         for (;;) {
/* 112 */           out.write("\n\tfor(i=0;i<document.AMActionForm.elements.length;i++)   {\n\t\tvar displayname = document.AMActionForm.elements[i].value;\t\n\t\tif(displayname=='')   {\n\t  \t\talert('");
/* 113 */           out.print(FormatUtil.getString("am.webclient.amazon.cloudwatch.text"));
/* 114 */           out.write("'+i);\t\n\t  \t\treturn;\n\t\t}\n\t}\n\t\tvar resId = ids.substring(0,ids.length-1); \n\t\t \n\t\tdocument.AMActionForm.action=\"/disableCloudWatch.do?method=manageCloudWatch&To=disable&rowIds=\"+resId;\n//\t\talert(document.AMActionForm.action);\n\t\tdocument.AMActionForm.submit();\n\t");
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
/* 125 */         out.write("\n}\nfunction setFocusProperTextField()  \n{\n\tvar pos = document.AMActionForm.elements.length;\n    if(pos > 0)  \n    { \n\t\tif(document.AMActionForm.elements.length >=2)  \n\t\t{\n        \tpos = 1;\n        }\n        else   \n        {\n        \treturn; \n        }\n\t\tfor(i=0;i<document.AMActionForm.elements.length;i++)   \n\t\t{\n\t\t\tif(document.AMActionForm.elements[i].type =='text')   \n\t\t\t{               \n\t\t\t\ttry    \n\t\t\t\t{\n\t\t\t\t\tdocument.AMActionForm.elements[i].focus(); \n\t\t\t\t\tbreak;\n\t\t\t\t}\n\t\t\t\tcatch (e) {}                    \n           \t}\n       \t}       \n   \t}\n}\nfunction doInitStuffOnBodyLoad()  \n{\n\n    \tsetFocusProperTextField();\n    \tif (window.myOnLoad)   {\n\t\tmyOnLoad();\n    \t}\n}\n</script>\n");
/*     */         
/* 127 */         String resids_forquery = "(";
/* 128 */         StringTokenizer nameAfterComma = new StringTokenizer(resids, ",");
/* 129 */         while (nameAfterComma.hasMoreTokens())
/*     */         {
/* 131 */           String temp = nameAfterComma.nextToken();
/* 132 */           resids_forquery = resids_forquery + "" + temp + ", ";
/*     */         }
/* 134 */         if (!resids_forquery.equals("("))
/*     */         {
/* 136 */           resids_forquery = resids_forquery.substring(0, resids_forquery.length() - 2);
/* 137 */           resids_forquery = resids_forquery + ")";
/*     */         }
/* 139 */         String query = "select RESOURCEID, DISPLAYNAME from AM_ManagedObject where RESOURCEID in " + resids_forquery;
/*     */         
/* 141 */         ArrayList toEnableIds = new ArrayList();
/* 142 */         ArrayList toDisableIds = new ArrayList();
/* 143 */         Hashtable name_ids = new Hashtable();
/* 144 */         Properties displaynames = new Properties();
/* 145 */         ResultSet results = null;
/* 146 */         ResultSet rsForTypeId = null;
/* 147 */         ResultSet rsForCollectionTime = null;
/* 148 */         String ec2InstanceTypeId = "";
/*     */         try
/*     */         {
/* 151 */           results = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(query);
/* 152 */           String typeIdQuery = "select TYPEID from AM_MONITOR_TYPES where TYPENAME = 'EC2Instance'";
/* 153 */           rsForTypeId = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(typeIdQuery);
/* 154 */           if (rsForTypeId.next())
/*     */           {
/* 156 */             ec2InstanceTypeId = rsForTypeId.getString("TYPEID");
/*     */           }
/* 158 */           while (results.next())
/*     */           {
/* 160 */             String dispname = results.getString("DISPLAYNAME");
/* 161 */             String id = results.getString("RESOURCEID");
/* 162 */             String collectionTimeQuery = "select Value from AM_Script_String_Data_" + ec2InstanceTypeId + " where RESOURCEID = " + id + " and ATTRIBUTEID = '9829'";
/* 163 */             rsForCollectionTime = com.adventnet.appmanager.db.AMConnectionPool.executeQueryStmt(collectionTimeQuery);
/* 164 */             String state = "";
/* 165 */             while (rsForCollectionTime.next())
/*     */             {
/* 167 */               state = rsForCollectionTime.getString("Value");
/*     */             }
/* 169 */             if (state.equalsIgnoreCase("disabled"))
/*     */             {
/* 171 */               toEnable = toEnable + id + ",";
/* 172 */               toEnableIds.add(id);
/* 173 */               name_ids.put(id, dispname);
/* 174 */               displaynames.setProperty(id, dispname);
/*     */             }
/* 176 */             else if (state.equalsIgnoreCase("enabled"))
/*     */             {
/* 178 */               toDisable = toDisable + id + ",";
/* 179 */               toDisableIds.add(id);
/* 180 */               name_ids.put(id, dispname);
/* 181 */               displaynames.setProperty(id, dispname);
/*     */             }
/*     */           }
/* 184 */           request.setAttribute("resourceids", toDisableIds);
/* 185 */           request.setAttribute("nameids", name_ids);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           try
/*     */           {
/* 195 */             if (results != null) {
/* 196 */               results.close();
/*     */             }
/* 198 */             if (rsForTypeId != null)
/* 199 */               rsForTypeId.close();
/* 200 */             rsForCollectionTime.close();
/*     */           }
/*     */           catch (Exception exc) {}
/*     */           
/*     */ 
/*     */ 
/* 206 */           out.write("\n<title>");
/*     */         }
/*     */         catch (Exception exception)
/*     */         {
/* 189 */           exception.printStackTrace();
/*     */         }
/*     */         finally
/*     */         {
/*     */           try
/*     */           {
/* 195 */             if (results != null) {
/* 196 */               results.close();
/*     */             }
/* 198 */             if (rsForTypeId != null)
/* 199 */               rsForTypeId.close();
/* 200 */             rsForCollectionTime.close();
/*     */           }
/*     */           catch (Exception exc) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 207 */         out.print(FormatUtil.getString("am.webclient.amazon.disablecloudwatch.title.text"));
/* 208 */         out.write("</title>\n<body onLoad=\"javascript:doInitStuffOnBodyLoad()\"></body>\n<form  action=\"/disableCloudWatch.do\" method=\"post\" name=\"AMActionForm\" style=\"display:inline\" >\n\n");
/*     */         
/* 210 */         MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 211 */         _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 212 */         _jspx_th_html_005fmessages_005f0.setParent(null);
/*     */         
/* 214 */         _jspx_th_html_005fmessages_005f0.setId("msg");
/*     */         
/* 216 */         _jspx_th_html_005fmessages_005f0.setMessage("false");
/* 217 */         int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 218 */         if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 219 */           String msg = null;
/* 220 */           if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 221 */             out = _jspx_page_context.pushBody();
/* 222 */             _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 223 */             _jspx_th_html_005fmessages_005f0.doInitBody();
/*     */           }
/* 225 */           msg = (String)_jspx_page_context.findAttribute("msg");
/*     */           for (;;) {
/* 227 */             out.write("         \n\t\t\n\t          \t\t<table width=\"95%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\">\n\t        \t\t\t<tr> \n\t                \t\t\t<td width=\"5%\" align=\"center\"><img src=\"../../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t                \t\t\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 228 */             if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*     */               return;
/* 230 */             out.write("</td>\n\t              \t\t\t</tr>\n\t            \t\t</table>\n\t            \t\t<br>\n\t\t\n\t\t");
/* 231 */             int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 232 */             msg = (String)_jspx_page_context.findAttribute("msg");
/* 233 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/* 236 */           if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 237 */             out = _jspx_page_context.popBody();
/*     */           }
/*     */         }
/* 240 */         if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 241 */           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/*     */         }
/*     */         else {
/* 244 */           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 245 */           out.write(10);
/* 246 */           out.write(9);
/* 247 */           out.write(9);
/*     */           
/* 249 */           MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 250 */           _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 251 */           _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
/*     */           
/* 253 */           _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 254 */           int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 255 */           if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*     */             for (;;) {
/* 257 */               out.write(" \n\t\t\n\t\t\t\t\t<table width=\"95%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n\t\t\t\t\t\t<tr> \n\t                \t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t                \t\t\t\t<td width=\"95%\" class=\"message\">\n\t                \t\t\t\t\t");
/*     */               
/* 259 */               MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 260 */               _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 261 */               _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */               
/* 263 */               _jspx_th_html_005fmessages_005f1.setId("msg");
/*     */               
/* 265 */               _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 266 */               int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 267 */               if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 268 */                 String msg = null;
/* 269 */                 if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 270 */                   out = _jspx_page_context.pushBody();
/* 271 */                   _jspx_th_html_005fmessages_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 272 */                   _jspx_th_html_005fmessages_005f1.doInitBody();
/*     */                 }
/* 274 */                 msg = (String)_jspx_page_context.findAttribute("msg");
/*     */                 for (;;) {
/* 276 */                   out.write(" \n\t                \t  \t\t\t\t\t");
/* 277 */                   if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*     */                     return;
/* 279 */                   out.write("<br>\n\t\t\t\t\t\t\t\t");
/* 280 */                   int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 281 */                   msg = (String)_jspx_page_context.findAttribute("msg");
/* 282 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/* 285 */                 if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 286 */                   out = _jspx_page_context.popBody();
/*     */                 }
/*     */               }
/* 289 */               if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 290 */                 this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*     */               }
/*     */               
/* 293 */               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 294 */               out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t<br>\n\t\t");
/* 295 */               int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 296 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 300 */           if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 301 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */           }
/*     */           else {
/* 304 */             this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 305 */             out.write(32);
/* 306 */             out.write(" \n\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\"  class=\"lrtborder\" width=\"100%\" align=\"center\">\n\t\t<tr align=\"left\">\n\t\t\t<td class=\"tableheadingbborder\">");
/* 307 */             out.print(FormatUtil.getString("am.webclient.amazon.disablecloudwatch.title.text"));
/* 308 */             out.write("</td>\n\t\t</tr>\n\t\n\t\t\n\t\t");
/*     */             
/* 310 */             if (displaynames != null)
/*     */             {
/* 312 */               out.write("\n\t\t\t\n\t\t\t<tr >\n\t\t\t\t<td class=\"lightbg\">&nbsp;<b><span class=\"bodytextbold\">");
/* 313 */               out.print(FormatUtil.getString("am.webclient.amazon.disablecloudwatch.selected.text"));
/* 314 */               out.write("</span></b></td>\n\t\t  \t</tr>\n\t\t  \t<tr><td>\n\t\t\t\t<img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\">\n\t\t\t</td></tr>\n\t\t \t<tr align=\"left\"><td>\n\t\t \t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"   width=\"100%\" align=\"center\">\n\t\t  \n\t\t  \t\t\n\t\t  \t\t");
/*     */               
/* 316 */               int i = 0;
/* 317 */               for (i = 0; i < toDisableIds.size(); i++) {
/* 318 */                 String resid = (String)toDisableIds.get(i);
/* 319 */                 String displayname = (String)name_ids.get(resid);
/* 320 */                 int len = displayname.length();
/* 321 */                 String bgclass = "whitegrayborder";
/* 322 */                 if (i % 2 != 0) {
/* 323 */                   bgclass = "yellowgrayborder";
/*     */                 } else {
/* 325 */                   bgclass = "whitegrayborder";
/*     */                 }
/*     */                 
/*     */ 
/* 329 */                 out.write("\n\t\t\t\t\t\t\n\t\t\t\t\t\t<tr >\n\t\t\t\t\t\t\t<td class=");
/* 330 */                 out.print(bgclass);
/* 331 */                 out.write(" height=\"30\" align = \"left\">&nbsp;");
/* 332 */                 out.print(displayname);
/* 333 */                 out.write("&nbsp;</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*     */               }
/* 335 */               out.write("\n\t\t\t\t</table>\t\t\n\t\t\t\t\n\t\t\t\t<tr><td>\n\t\t\t\t<img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\">\n\t\t\t\t</td></tr>\n\t\t\t\t</table>\n\t\t\t\t</td></tr>\n\t\t\t\t</table>\n       \t\t\t\t<table width=\"100%\" border=\"0\" class=\"lrtbdarkborder\" cellpadding=\"0\" cellspacing=\"0\">\n       \t\t\t\t<tr> \n       \t\t\t\t\t<td height=\"35\"  align=\"center\" class=\"tablebottom\">\n        \t   \t\t\t\t<input name=\"Submit\" type=\"button\" class=\"buttons\" value='");
/* 336 */               out.print(FormatUtil.getString("am.webclient.amazon.cloudwatch.confirm.text"));
/* 337 */               out.write("' onClick=\"javascript:submitForm('");
/* 338 */               out.print(toDisable);
/* 339 */               out.write("');opener.location.reload(true);\">\n        \t   \t\t\t\t<input name=\"GoBack\" type=\"button\" class=\"buttons btn_link\" value='");
/* 340 */               out.print(FormatUtil.getString("am.webclient.amazon.cloudwatch.cancel.text"));
/* 341 */               out.write("'  onClick=\"opener.location.reload(true);self.close();\">&nbsp;&nbsp;\n       \t\t\t\t\t</td>\n\t\t\t\t</tr>\n    \t\t\t\t</table>\n\t\t");
/*     */             }
/* 343 */             out.write("\n\t</form>\n\n");
/*     */           }
/* 345 */         } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 346 */         out = _jspx_out;
/* 347 */         if ((out != null) && (out.getBufferSize() != 0))
/* 348 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 349 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 352 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 358 */     PageContext pageContext = _jspx_page_context;
/* 359 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 361 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 362 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 363 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 365 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 367 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 368 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 369 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 370 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 371 */       return true;
/*     */     }
/* 373 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 374 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 379 */     PageContext pageContext = _jspx_page_context;
/* 380 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 382 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 383 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 384 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 386 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 387 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 388 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 390 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 391 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 392 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 396 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 397 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 398 */       return true;
/*     */     }
/* 400 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 401 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 406 */     PageContext pageContext = _jspx_page_context;
/* 407 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 409 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 410 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 411 */     _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fmessages_005f0);
/*     */     
/* 413 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*     */     
/* 415 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 416 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 417 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 418 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 419 */       return true;
/*     */     }
/* 421 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 422 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 427 */     PageContext pageContext = _jspx_page_context;
/* 428 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 430 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 431 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 432 */     _jspx_th_bean_005fwrite_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fmessages_005f1);
/*     */     
/* 434 */     _jspx_th_bean_005fwrite_005f1.setName("msg");
/*     */     
/* 436 */     _jspx_th_bean_005fwrite_005f1.setFilter(false);
/* 437 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 438 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 439 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 440 */       return true;
/*     */     }
/* 442 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 443 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\amazon\DisableCloudWatch_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */