/*     */ package org.apache.jsp.jsp.hyperv;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.bean.WriteTag;
/*     */ import org.apache.struts.taglib.html.MessagesTag;
/*     */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*     */ import org.apache.struts.taglib.logic.NotPresentTag;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class ManageHyperV_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  29 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  45 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  49 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  50 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  51 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  52 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  53 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  54 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  55 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  56 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  60 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  61 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*  62 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/*  63 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
/*  64 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  65 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  72 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  75 */     JspWriter out = null;
/*  76 */     Object page = this;
/*  77 */     JspWriter _jspx_out = null;
/*  78 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  82 */       response.setContentType("text/html;charset=UTF-8");
/*  83 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  85 */       _jspx_page_context = pageContext;
/*  86 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  87 */       ServletConfig config = pageContext.getServletConfig();
/*  88 */       session = pageContext.getSession();
/*  89 */       out = pageContext.getOut();
/*  90 */       _jspx_out = out;
/*     */       
/*  92 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link\n\thref=\"/images/");
/*  93 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  95 */       out.write("/style.css\"\n\trel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*     */       
/*  97 */       Hashtable<String, String> names_ids = new Hashtable();
/*  98 */       Enumeration<String> resourceIds = null;
/*  99 */       String resIds = "";
/* 100 */       String action = "";
/* 101 */       String title = "";
/* 102 */       String toManage = "";
/* 103 */       String selectedText = "";
/* 104 */       String alertText = "";
/* 105 */       String confirmText = "";
/* 106 */       String displayNames = "";
/* 107 */       String parentID = "";
/* 108 */       if (request.getAttribute("parentid") != null)
/*     */       {
/* 110 */         parentID = (String)request.getAttribute("parentid");
/*     */       }
/*     */       
/*     */ 
/* 114 */       if (request.getAttribute("action") != null)
/*     */       {
/* 116 */         action = (String)request.getAttribute("action");
/* 117 */         if ((action.equalsIgnoreCase("start")) || (action.equalsIgnoreCase("stop")) || (action.equalsIgnoreCase("restart")) || (action.equalsIgnoreCase("pause")) || (action.equalsIgnoreCase("save")))
/*     */         {
/* 119 */           title = FormatUtil.getString("am.webclient.hyperv.actions." + action.toLowerCase() + ".title.text");
/*     */           
/* 121 */           selectedText = FormatUtil.getString("am.webclient.hyperv.actions." + action.toLowerCase() + ".selected.text");
/*     */           
/* 123 */           confirmText = FormatUtil.getString("am.webclient.hyperv.actions." + action.toLowerCase() + ".confirm.text");
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 128 */       if (request.getAttribute("nameids") != null)
/*     */       {
/* 130 */         names_ids = (Hashtable)request.getAttribute("nameids");
/* 131 */         resourceIds = names_ids.keys();
/*     */       }
/* 133 */       if (request.getAttribute("resourceIdsToManage") != null)
/*     */       {
/* 135 */         resIds = (String)request.getAttribute("resourceIdsToManage");
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 141 */       out.write("\n                 \n                    \n               <SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../../template/appmanager.js\"></SCRIPT>\n\t\t      <script language=\"JavaScript1.2\">\n\t\t      function submitForm(ids,action,parentID)   \n\t\t      {\n\t\t    \n\t\t      \t");
/* 142 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*     */         return;
/* 144 */       out.write("\n\t\t      \t");
/* 145 */       if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
/*     */         return;
/* 147 */       out.write("\n\t\t      }\n\t\t      function setFocusProperTextField()  \n\t\t      {\n\t\t      \tvar pos = document.AMActionForm.elements.length;\n\t\t          if(pos > 0)  \n\t\t          { \n\t\t      \t\tif(document.AMActionForm.elements.length >=2)  \n\t\t      \t\t{\n\t\t              \tpos = 1;\n\t\t              }\n\t\t              else   \n\t\t              {\n\t\t              \treturn; \n\t\t              }\n\t\t      \t\tfor(i=0;i<document.AMActionForm.elements.length;i++)   \n\t\t      \t\t{\n\t\t      \t\t\tif(document.AMActionForm.elements[i].type =='text')   \n\t\t      \t\t\t{               \n\t\t      \t\t\t\ttry    \n\t\t      \t\t\t\t{\n\t\t      \t\t\t\t\tdocument.AMActionForm.elements[i].focus(); \n\t\t      \t\t\t\t\tbreak;\n\t\t      \t\t\t\t}\n\t\t      \t\t\t\tcatch (e) {}                    \n\t\t                 \t}\n\t\t             \t}       \n\t\t         \t}\n\t\t      }\n\t\t      function doInitStuffOnBodyLoad()  \n\t\t      {\n\t\t      \n\t\t          \tsetFocusProperTextField();\n\t\t          \tif (window.myOnLoad)   {\n\t\t      \t\tmyOnLoad();\n\t\t          \t}\n\t\t      }\n\t\t      </script>\n\t\t      ");
/*     */       
/*     */ 
/*     */ 
/* 151 */       out.write("\n\t\t    \n<body onLoad=\"javascript:doInitStuffOnBodyLoad()\"></body>\n                      <body onLoad=\"javascript:doInitStuffOnBodyLoad()\"></body>\n                        ");
/*     */       
/* 153 */       MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(MessagesPresentTag.class);
/* 154 */       _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 155 */       _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
/*     */       
/* 157 */       _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 158 */       int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 159 */       if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*     */         for (;;) {
/* 161 */           out.write("\n\t\t      \t\t       \n\t\t      \t\t \t<table width=\"95%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\"\n\t\t      \t\t \t\tclass=\"messagebox\" align=\"center\" >\n\t\t      \t\t \t\t<tr>\n\t\t      \t\t \t\t\t<td width=\"5%\" align=\"center\"><img src=\"../../images/icon_message_success.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t\t      \t\t \t\t\t<td width=\"95%\" class=\"message\">\n\t\t      \t\t \t\t\t");
/*     */           
/* 163 */           MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 164 */           _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 165 */           _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */           
/* 167 */           _jspx_th_html_005fmessages_005f0.setId("msg");
/*     */           
/* 169 */           _jspx_th_html_005fmessages_005f0.setMessage("true");
/* 170 */           int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 171 */           if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 172 */             String msg = null;
/* 173 */             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 174 */               out = _jspx_page_context.pushBody();
/* 175 */               _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 176 */               _jspx_th_html_005fmessages_005f0.doInitBody();
/*     */             }
/* 178 */             msg = (String)_jspx_page_context.findAttribute("msg");
/*     */             for (;;) {
/* 180 */               out.write("\n\t\t      \t\t \t\t\t\t");
/* 181 */               if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*     */                 return;
/* 183 */               out.write("\n\t\t      \t\t \t\t\t\t<br>\n\t\t      \t\t \t\t\t");
/* 184 */               int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 185 */               msg = (String)_jspx_page_context.findAttribute("msg");
/* 186 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 189 */             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 190 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 193 */           if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 194 */             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*     */           }
/*     */           
/* 197 */           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 198 */           out.write("\n\t\t      \t\t \t\t\t</td>\n\t\t      \t\t \t\t</tr>\n\t\t      \t\t \t</table>\n\t\t      \t\t \t<br>\n");
/* 199 */           int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 200 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 204 */       if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 205 */         this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */       }
/*     */       else {
/* 208 */         this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 209 */         out.write(32);
/* 210 */         out.write("\n                      <form action=\"/manageHyperVMs\" method=\"post\" name=\"AMActionForm\"\tstyle=\"display: inline\">\n\t\t\n<br>\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"95%\" align=\"center\">\n\t<tr>\n\t<td>\n\n      \n     \n     \n               \n");
/*     */         
/* 212 */         if ((resIds != null) && (resIds.trim() != "") && (resourceIds != null))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 217 */           out.write("\n<table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrtborder\" width=\"100%\" align=\"center\">\n\t<tr align=\"left\">\n\t\t<td class=\"tableheadingbborder\">");
/* 218 */           out.print(title);
/* 219 */           out.write("</td>\n\t</tr>\n</table>\n<table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrborder\" width=\"100%\" align=\"center\">\n\t<tr>\n\t\t<td><b><span class=\"bodytextbold\" style=\"color:red;\"><p>");
/* 220 */           out.print(alertText);
/* 221 */           out.write("</p></span></b></td>\n\t</tr>\n\t<tr>\n\t\t<td class=\"lightbg\">&nbsp;<b><span class=\"bodytextbold\">");
/* 222 */           out.print(selectedText);
/* 223 */           out.write("</span></b></td>\n\t</tr>\n\t<tr>\n\t\t<td><img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\">\n\t\t</td>\n\t</tr>\n\t<tr align=\"left\">\n\t\t<td>\n\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\"\n\t\t\talign=\"center\">\n\n\t\t\t");
/*     */           
/* 225 */           for (int i = 0; resourceIds.hasMoreElements(); i++)
/*     */           {
/* 227 */             String resid = (String)resourceIds.nextElement();
/* 228 */             String displayname = (String)names_ids.get(resid);
/* 229 */             if (displayNames.length() > 0)
/*     */             {
/* 231 */               displayNames = displayNames + "," + displayname;
/*     */             }
/*     */             else
/*     */             {
/* 235 */               displayNames = displayNames + displayname;
/*     */             }
/* 237 */             int len = displayname.length();
/* 238 */             String bgclass = "whitegrayborder";
/* 239 */             if (i % 2 != 0)
/*     */             {
/* 241 */               bgclass = "yellowgrayborder";
/*     */             }
/*     */             else
/*     */             {
/* 245 */               bgclass = "whitegrayborder";
/*     */             }
/*     */             
/*     */ 
/* 249 */             out.write("\n\n\t\t\t<tr>\n\t\t\t\t<td class=");
/* 250 */             out.print(bgclass);
/* 251 */             out.write(" height=\"30\" align=\"left\">&#09;&#09;&#09;\n\t\t\t\t<li>");
/* 252 */             out.print(displayname);
/* 253 */             out.write("</li>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t");
/*     */           }
/*     */           
/*     */ 
/* 257 */           out.write("\n\t\t</table>\n\n\t\t<tr>\n\t\t\t<td><img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\">\n\t\t\t</td>\n\t\t</tr>\n</table>\n<table width=\"100%\" border=\"0\" class=\"lrtbdarkborder\" cellpadding=\"0\"\n\tcellspacing=\"0\" align=\"center\">\n\t<tr>\n\t\t<td height=\"35\" align=\"center\" class=\"tablebottom\">\n\t\t<input name=\"GoBack\" type=\"button\" class=\"buttons btn_link\"\tvalue='");
/* 258 */           out.print(FormatUtil.getString("am.webclient.hyperv.actions.cancel.text"));
/* 259 */           out.write("' onClick=\"opener.location.reload(true);self.close();\">&nbsp;&nbsp;\n\t\t<input name=\"Submit\" type=\"button\" class=\"buttons btn_highlt\"\tvalue='");
/* 260 */           out.print(confirmText);
/* 261 */           out.write("' onClick=\"javascript:submitForm('");
/* 262 */           out.print(resIds);
/* 263 */           out.write(39);
/* 264 */           out.write(44);
/* 265 */           out.write(39);
/* 266 */           out.print(action);
/* 267 */           out.write("');opener.location.reload(true);\">\n\t\t</td>\n\t</tr>\n</table>\n");
/*     */         }
/* 269 */         HashMap actionProps = null;
/* 270 */         actionProps = (HashMap)request.getAttribute("actionProps");
/*     */         
/* 272 */         if ((resIds == null) || (resIds.trim() == "") || (resourceIds == null))
/*     */         {
/* 274 */           String jspAction = "";
/* 275 */           jspAction = (String)request.getAttribute("action");
/*     */           
/*     */ 
/*     */ 
/* 279 */           out.write("\n <table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrtborder\" width=\"100%\" align=\"center\">\n \t<tr align=\"left\">\n \t\t<td class=\"tableheadingbborder\">");
/* 280 */           out.print(title);
/* 281 */           out.write("</td>\n \t</tr>\n </table>\n <table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrtborder\" width=\"100%\" align=\"center\">\n \t\n \t<tr>\n \t\t<td><img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\">\n \t\t</td>\n \t</tr>\n \t<tr align=\"left\">\n \t\t<td>\n \t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\"\n \t\t\talign=\"center\">\n \n \t\t\t\n\t\t\n");
/*     */           
/* 283 */           if (request.getAttribute("actionProps") != null)
/*     */           {
/*     */ 
/*     */ 
/* 287 */             Iterator it = actionProps.keySet().iterator();
/* 288 */             for (int i = 0; it.hasNext(); i++)
/*     */             {
/* 290 */               int Key = ((Integer)it.next()).intValue();
/* 291 */               Properties vmProps = (Properties)actionProps.get(Integer.valueOf(Key));
/* 292 */               String vmName = vmProps.getProperty("VMNAME");
/* 293 */               String state = vmProps.getProperty("State");
/* 294 */               String display = vmName + " is in state " + state;
/*     */               
/* 296 */               String bgclass = "whitegrayborder";
/* 297 */               if (i % 2 != 0)
/*     */               {
/* 299 */                 bgclass = "yellowgrayborder";
/*     */               }
/*     */               else
/*     */               {
/* 303 */                 bgclass = "whitegrayborder";
/*     */               }
/*     */               
/* 306 */               out.write("\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td class=");
/* 307 */               out.print(bgclass);
/* 308 */               out.write(" height=\"30\" align=\"left\">&#09;&#09;&#09;\n\t\t\t\t\t\t\t\t\t\t\t<li>");
/* 309 */               out.print(display);
/* 310 */               out.write("</li>\n\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t\t\t\t\t");
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 322 */           out.write("\n              </table>\n             <tr>\n\t     \t\t\t<td><img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\">\n\t     \t\t\t</td>\n\t\t</tr>\n             \n      \n<table width=\"100%\" border=\"0\" class=\"lrtbdarkborder\" cellpadding=\"0\"\n\tcellspacing=\"0\" align=\"center\">\n\t<tr>\n\t\t<td height=\"35\" align=\"center\" class=\"tablebottom\">\n\t\t\t<input name=\"GoBack\" type=\"button\" class=\"buttons\"\talign=\"center\" value='");
/* 323 */           out.print(FormatUtil.getString("am.webclient.hyperv.actions.close.text"));
/* 324 */           out.write("' onClick=\"self.close();\">&nbsp;&nbsp;\n \t</td>\n \t\n \t</tr>\n </table>\n \n");
/*     */         }
/* 326 */         out.write("\n\n</form>\n       \n");
/*     */       }
/* 328 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 329 */         out = _jspx_out;
/* 330 */         if ((out != null) && (out.getBufferSize() != 0))
/* 331 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 332 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 335 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 341 */     PageContext pageContext = _jspx_page_context;
/* 342 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 344 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 345 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 346 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 348 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 350 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 351 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 352 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 353 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 354 */       return true;
/*     */     }
/* 356 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 357 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 362 */     PageContext pageContext = _jspx_page_context;
/* 363 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 365 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 366 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 367 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */     
/* 369 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 370 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 371 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */       for (;;) {
/* 373 */         out.write("\n\t\t      \t\talertUser();\n\t\t      \t\treturn;\n\t\t      \t");
/* 374 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 375 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 379 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 380 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 381 */       return true;
/*     */     }
/* 383 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 384 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 389 */     PageContext pageContext = _jspx_page_context;
/* 390 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 392 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 393 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 394 */     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*     */     
/* 396 */     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 397 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 398 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*     */       for (;;) {
/* 400 */         out.write("\n\t\t      \t\tdocument.AMActionForm.action=\"/manageHyperVMs.do?method=manageVMs&action=\"+action+\"&resIds=\"+ids;//No I18N\n\t\t      \t\tdocument.AMActionForm.submit();\n\t\t      \t");
/* 401 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 402 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 406 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 407 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 408 */       return true;
/*     */     }
/* 410 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 411 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 416 */     PageContext pageContext = _jspx_page_context;
/* 417 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 419 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.get(WriteTag.class);
/* 420 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 421 */     _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fmessages_005f0);
/*     */     
/* 423 */     _jspx_th_bean_005fwrite_005f0.setName("msg");
/*     */     
/* 425 */     _jspx_th_bean_005fwrite_005f0.setFilter(false);
/* 426 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 427 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 428 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 429 */       return true;
/*     */     }
/* 431 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005ffilter_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 432 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\hyperv\ManageHyperV_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */