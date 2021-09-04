/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.bean.WriteTag;
/*     */ import org.apache.struts.taglib.html.MessagesTag;
/*     */ import org.apache.struts.taglib.logic.EmptyTag;
/*     */ import org.apache.struts.taglib.logic.IterateTag;
/*     */ import org.apache.struts.taglib.logic.MessagesPresentTag;
/*     */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*     */ 
/*     */ public final class TestAmazonAction_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  20 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static java.util.Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fproperty_005fmessage;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public java.util.Map<String, Long> getDependants()
/*     */   {
/*  37 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  41 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fproperty_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  42 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  48 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  49 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  53 */     this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fproperty_005fmessage.release();
/*  54 */     this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.release();
/*  55 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
/*  56 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/*  57 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/*  58 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.release();
/*  59 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*     */     throws java.io.IOException, javax.servlet.ServletException
/*     */   {
/*  66 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  69 */     JspWriter out = null;
/*  70 */     Object page = this;
/*  71 */     JspWriter _jspx_out = null;
/*  72 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  76 */       response.setContentType("text/html;charset=UTF-8");
/*  77 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  79 */       _jspx_page_context = pageContext;
/*  80 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  81 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/*  82 */       session = pageContext.getSession();
/*  83 */       out = pageContext.getOut();
/*  84 */       _jspx_out = out;
/*     */       
/*  86 */       out.write("\n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/validation.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<script language=\"javascript\"></script>\n<link\nhref=\"/images/Blue/style.css\"\nrel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<html>\n");
/*     */       
/*     */ 
/*  89 */       java.util.ArrayList list = (java.util.ArrayList)request.getAttribute("ec2InstList");
/*     */       
/*     */ 
/*     */ 
/*  93 */       String type = (String)request.getAttribute("type");
/*  94 */       int actionID = Integer.parseInt(request.getParameter("actionID"));
/*     */       
/*  96 */       String haid = request.getParameter("haid");
/*     */       
/*  98 */       out.write("\n<script>\nfunction validateAndSubmit()\n{\n\t\n\tif(document.amazontest.testaction[0].checked==true){\n\tdocument.amazontest.action=\"/manageEC2Instances.do?method=triggerAmazonAction&update=load&haid=");
/*  99 */       out.print(haid);
/* 100 */       out.write("&actionID=\"+");
/* 101 */       out.print(actionID);
/* 102 */       out.write(";\n\t}\n\telse{\n\tdocument.amazontest.action=\"/manageEC2Instances.do?method=triggerAmazonAction&haid=");
/* 103 */       out.print(haid);
/* 104 */       out.write("&actionID=\"+");
/* 105 */       out.print(actionID);
/* 106 */       out.write(";\n\t}\n\tdocument.amazontest.method=\"POST\";\n\tdocument.amazontest.submit();\t\n\t\n}\n</script>\n\n<body>\n<form name=\"amazontest\" action=\"\" method=\"POST\">\n\n");
/*     */       
/* 108 */       MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fproperty_005fmessage.get(MessagesPresentTag.class);
/* 109 */       _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
/* 110 */       _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
/*     */       
/* 112 */       _jspx_th_logic_005fmessagesPresent_005f0.setProperty("success");
/*     */       
/* 114 */       _jspx_th_logic_005fmessagesPresent_005f0.setMessage("true");
/* 115 */       int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
/* 116 */       if (_jspx_eval_logic_005fmessagesPresent_005f0 != 0) {
/*     */         for (;;) {
/* 118 */           out.write(10);
/* 119 */           out.write(32);
/*     */           
/* 121 */           MessagesTag _jspx_th_html_005fmessages_005f0 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 122 */           _jspx_th_html_005fmessages_005f0.setPageContext(_jspx_page_context);
/* 123 */           _jspx_th_html_005fmessages_005f0.setParent(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */           
/* 125 */           _jspx_th_html_005fmessages_005f0.setId("messages");
/*     */           
/* 127 */           _jspx_th_html_005fmessages_005f0.setMessage("true");
/* 128 */           int _jspx_eval_html_005fmessages_005f0 = _jspx_th_html_005fmessages_005f0.doStartTag();
/* 129 */           if (_jspx_eval_html_005fmessages_005f0 != 0) {
/* 130 */             String messages = null;
/* 131 */             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 132 */               out = _jspx_page_context.pushBody();
/* 133 */               _jspx_th_html_005fmessages_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 134 */               _jspx_th_html_005fmessages_005f0.doInitBody();
/*     */             }
/* 136 */             messages = (String)_jspx_page_context.findAttribute("messages");
/*     */             for (;;) {
/* 138 */               out.write("\n \t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"messagebox\">\n \t  <tr> \n    \t  <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_success.gif\" alt=\"Icon\"></td>\n\t  <td width=\"95%\" class=\"message\"> ");
/* 139 */               if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmessages_005f0, _jspx_page_context))
/*     */                 return;
/* 141 */               out.write("</td>\n\t  </tr>\n\t</table></br>\n ");
/* 142 */               int evalDoAfterBody = _jspx_th_html_005fmessages_005f0.doAfterBody();
/* 143 */               messages = (String)_jspx_page_context.findAttribute("messages");
/* 144 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/* 147 */             if (_jspx_eval_html_005fmessages_005f0 != 1) {
/* 148 */               out = _jspx_page_context.popBody();
/*     */             }
/*     */           }
/* 151 */           if (_jspx_th_html_005fmessages_005f0.doEndTag() == 5) {
/* 152 */             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0); return;
/*     */           }
/*     */           
/* 155 */           this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f0);
/* 156 */           out.write(10);
/* 157 */           int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
/* 158 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 162 */       if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == 5) {
/* 163 */         this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fproperty_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/*     */       }
/*     */       else {
/* 166 */         this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fproperty_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
/* 167 */         out.write(10);
/*     */         
/* 169 */         MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (MessagesPresentTag)this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fproperty_005fmessage.get(MessagesPresentTag.class);
/* 170 */         _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
/* 171 */         _jspx_th_logic_005fmessagesPresent_005f1.setParent(null);
/*     */         
/* 173 */         _jspx_th_logic_005fmessagesPresent_005f1.setProperty("failure");
/*     */         
/* 175 */         _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
/* 176 */         int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
/* 177 */         if (_jspx_eval_logic_005fmessagesPresent_005f1 != 0) {
/*     */           for (;;) {
/* 179 */             out.write(10);
/* 180 */             out.write(32);
/*     */             
/* 182 */             MessagesTag _jspx_th_html_005fmessages_005f1 = (MessagesTag)this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.get(MessagesTag.class);
/* 183 */             _jspx_th_html_005fmessages_005f1.setPageContext(_jspx_page_context);
/* 184 */             _jspx_th_html_005fmessages_005f1.setParent(_jspx_th_logic_005fmessagesPresent_005f1);
/*     */             
/* 186 */             _jspx_th_html_005fmessages_005f1.setId("messages");
/*     */             
/* 188 */             _jspx_th_html_005fmessages_005f1.setMessage("true");
/* 189 */             int _jspx_eval_html_005fmessages_005f1 = _jspx_th_html_005fmessages_005f1.doStartTag();
/* 190 */             if (_jspx_eval_html_005fmessages_005f1 != 0) {
/* 191 */               String messages = null;
/* 192 */               if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 193 */                 out = _jspx_page_context.pushBody();
/* 194 */                 _jspx_th_html_005fmessages_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 195 */                 _jspx_th_html_005fmessages_005f1.doInitBody();
/*     */               }
/* 197 */               messages = (String)_jspx_page_context.findAttribute("messages");
/*     */               for (;;) {
/* 199 */                 out.write("\n \t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"messagebox\">\n \t  <tr> \n    \t  <td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\"></td>\n\t  <td width=\"95%\" class=\"message\"> ");
/* 200 */                 if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_html_005fmessages_005f1, _jspx_page_context))
/*     */                   return;
/* 202 */                 out.write("</td>\n\t  </tr>\n\t</table></br>\n ");
/* 203 */                 int evalDoAfterBody = _jspx_th_html_005fmessages_005f1.doAfterBody();
/* 204 */                 messages = (String)_jspx_page_context.findAttribute("messages");
/* 205 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/* 208 */               if (_jspx_eval_html_005fmessages_005f1 != 1) {
/* 209 */                 out = _jspx_page_context.popBody();
/*     */               }
/*     */             }
/* 212 */             if (_jspx_th_html_005fmessages_005f1.doEndTag() == 5) {
/* 213 */               this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1); return;
/*     */             }
/*     */             
/* 216 */             this._005fjspx_005ftagPool_005fhtml_005fmessages_0026_005fmessage_005fid.reuse(_jspx_th_html_005fmessages_005f1);
/* 217 */             out.write(10);
/* 218 */             int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
/* 219 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 223 */         if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == 5) {
/* 224 */           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fproperty_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/*     */         }
/*     */         else {
/* 227 */           this._005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fproperty_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
/* 228 */           out.write("\n\n<table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrtborder\" width=\"100%\" align=\"center\">\n   <tr align=\"left\">\n\t<td class=\"tableheadingbborder\">");
/* 229 */           out.print(FormatUtil.getString("am.ec2.testamazonzction.text"));
/* 230 */           out.write("</td>\n   </tr>\n</table>\n<table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrborder\" width=\"100%\" align=\"center\">\n   <tr>\n\t<td>&nbsp;&nbsp;</td>\n\t<td>\n\n\t<table class=\"bodytext\">\n\t<tr>\n\t\t   <td colspan=\"2\"><img src=\"../../images/spacer.gif\" height=\"5\" width=\"5\"></td>\n\t</tr>\n\t");
/*     */           
/* 232 */           EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 233 */           _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 234 */           _jspx_th_logic_005fempty_005f0.setParent(null);
/*     */           
/* 236 */           _jspx_th_logic_005fempty_005f0.setName("ec2InstList");
/* 237 */           int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 238 */           if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*     */             for (;;) {
/* 240 */               out.write("\n\t<tr>\n\t\t<td>\n\t\t\n\t\t<input type=\"radio\" name=\"testaction\" checked />");
/* 241 */               out.print(FormatUtil.getString("am.ec2.testamazonzction.sendtestmail.text"));
/* 242 */               out.write("</td>\n\t\t<input type=\"hidden\" value=\"\" name=\"testaction\" />\n\t</tr>\n\t");
/* 243 */               int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 244 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 248 */           if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 249 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/*     */           }
/*     */           else {
/* 252 */             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 253 */             out.write(10);
/* 254 */             out.write(9);
/*     */             
/* 256 */             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 257 */             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 258 */             _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*     */             
/* 260 */             _jspx_th_logic_005fnotEmpty_005f0.setName("ec2InstList");
/* 261 */             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 262 */             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*     */               for (;;) {
/* 264 */                 out.write("\n\t  <tr>\n\t\t<td><input type=\"radio\" name=\"testaction\" checked />");
/* 265 */                 out.print(FormatUtil.getString("am.ec2.testamazonzction.sendtestmail.text"));
/* 266 */                 out.write("</td>\n\t  </tr>\n\t  ");
/* 267 */                 if (type.equals("14")) {
/* 268 */                   out.write("\n\t  <tr>\n\t\t<td><input type=\"radio\" name=\"testaction\" />");
/* 269 */                   out.print(FormatUtil.getString("am.ec2.testamazonaction.start.text"));
/* 270 */                   out.write("</td>\n\t  </tr>\n\t  <tr>\n\t\t\t<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 271 */                   out.print(FormatUtil.getString("am.ec2.testamazonaction.listofinststart.text"));
/* 272 */                   out.write("</td>\n\t  </tr>\n\t  ");
/* 273 */                 } else if (type.equals("15")) {
/* 274 */                   out.write("\n\t  <tr>\n\t  \t<td><input type=\"radio\" name=\"testaction\" />");
/* 275 */                   out.print(FormatUtil.getString("am.ec2.testamazonaction.stop.text"));
/* 276 */                   out.write("</td>\n\t  </tr>\n\t  <tr>\n\t  \t<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 277 */                   out.print(FormatUtil.getString("am.ec2.testamazonaction.listofinststop.text"));
/* 278 */                   out.write("</td>\n\t  </tr>\n\t  ");
/*     */                 } else {
/* 280 */                   out.write("\n\t  <tr>\n\t  \t<td><input type=\"radio\" name=\"testaction\" />");
/* 281 */                   out.print(FormatUtil.getString("am.ec2.testamazonaction.restart.text"));
/* 282 */                   out.write("</td>\n\t  </tr>\n\t  <tr>\n\t    \t<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 283 */                   out.print(FormatUtil.getString("am.ec2.testamazonaction.listofinstrestart.text"));
/* 284 */                   out.write("</td>\n\t  </tr>\n\t  ");
/*     */                 }
/* 286 */                 out.write("\n\t\n\t\t");
/* 287 */                 int i = 1;
/* 288 */                 out.write(10);
/* 289 */                 out.write(9);
/* 290 */                 out.write(9);
/*     */                 
/* 292 */                 IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.get(IterateTag.class);
/* 293 */                 _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 294 */                 _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*     */                 
/* 296 */                 _jspx_th_logic_005fiterate_005f0.setId("element");
/*     */                 
/* 298 */                 _jspx_th_logic_005fiterate_005f0.setName("ec2InstList");
/* 299 */                 int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 300 */                 if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 301 */                   Object element = null;
/* 302 */                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 303 */                     out = _jspx_page_context.pushBody();
/* 304 */                     _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 305 */                     _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */                   }
/* 307 */                   element = _jspx_page_context.findAttribute("element");
/*     */                   for (;;) {
/* 309 */                     out.write("\n\t\t<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
/* 310 */                     out.print(i++);
/* 311 */                     out.write(58);
/* 312 */                     out.write(32);
/* 313 */                     if (_jspx_meth_bean_005fwrite_005f2(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*     */                       return;
/* 315 */                     out.write("</td></tr>\n\t\t");
/* 316 */                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 317 */                     element = _jspx_page_context.findAttribute("element");
/* 318 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/* 321 */                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 322 */                     out = _jspx_page_context.popBody();
/*     */                   }
/*     */                 }
/* 325 */                 if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 326 */                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*     */                 }
/*     */                 
/* 329 */                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 330 */                 out.write(10);
/* 331 */                 out.write(9);
/* 332 */                 out.write(9);
/* 333 */                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 334 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 338 */             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 339 */               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*     */             }
/*     */             else {
/* 342 */               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 343 */               out.write("\n\t\t\n\t<tr>\n\t\t   <td colspan=\"2\"><img src=\"../../images/spacer.gif\" height=\"20\" width=\"20\"></td>\n\t</tr>\n\t</table>\n\t</td>\n   </tr>\n\n</table>\n<table style=\"top-padding: 20px;\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"lrtbdarkborder\" width=\"100%\" align=\"center\">\n<tr align=\"left\">\n\t<td class=\"tablebottom\" width=\"45%\" align=\"right\"><input type=\"button\" value=\"");
/* 344 */               out.print(FormatUtil.getString("am.ec2instance.testactiontab.text"));
/* 345 */               out.write("\" class=\"buttons btn_test\"  onClick=\"javascript:validateAndSubmit();\"></td>\n\t<td class=\"tablebottom\" width=\"55%\" align=\"left\"><input type=\"button\" value=\"");
/* 346 */               out.print(FormatUtil.getString("am.ec2instance.cancelactiontab.text"));
/* 347 */               out.write("\" class=\"buttons btn_link\" onClick=\"javascript:window.parent.close();\"></td>\n</tr>\n</table>\n</form>\n</body>\n\n</html>\n");
/*     */             }
/* 349 */           } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 350 */         out = _jspx_out;
/* 351 */         if ((out != null) && (out.getBufferSize() != 0))
/* 352 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 353 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 356 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmessages_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 362 */     PageContext pageContext = _jspx_page_context;
/* 363 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 365 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(WriteTag.class);
/* 366 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 367 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmessages_005f0);
/*     */     
/* 369 */     _jspx_th_bean_005fwrite_005f0.setName("messages");
/* 370 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 371 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 372 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 373 */       return true;
/*     */     }
/* 375 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 376 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_html_005fmessages_005f1, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 381 */     PageContext pageContext = _jspx_page_context;
/* 382 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 384 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(WriteTag.class);
/* 385 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 386 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_html_005fmessages_005f1);
/*     */     
/* 388 */     _jspx_th_bean_005fwrite_005f1.setName("messages");
/* 389 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 390 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 391 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 392 */       return true;
/*     */     }
/* 394 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 395 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f2(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 400 */     PageContext pageContext = _jspx_page_context;
/* 401 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 403 */     WriteTag _jspx_th_bean_005fwrite_005f2 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 404 */     _jspx_th_bean_005fwrite_005f2.setPageContext(_jspx_page_context);
/* 405 */     _jspx_th_bean_005fwrite_005f2.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*     */     
/* 407 */     _jspx_th_bean_005fwrite_005f2.setName("element");
/*     */     
/* 409 */     _jspx_th_bean_005fwrite_005f2.setProperty("label");
/* 410 */     int _jspx_eval_bean_005fwrite_005f2 = _jspx_th_bean_005fwrite_005f2.doStartTag();
/* 411 */     if (_jspx_th_bean_005fwrite_005f2.doEndTag() == 5) {
/* 412 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 413 */       return true;
/*     */     }
/* 415 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f2);
/* 416 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\TestAmazonAction_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */