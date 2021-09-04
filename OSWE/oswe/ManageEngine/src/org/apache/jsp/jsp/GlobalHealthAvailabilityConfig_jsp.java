/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.DBUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
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
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*     */ 
/*     */ public final class GlobalHealthAvailabilityConfig_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*     */ {
/*  25 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*     */   private javax.el.ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  39 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  43 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  44 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  45 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  46 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  47 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  48 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  52 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  53 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/*  54 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/*  55 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  62 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  65 */     JspWriter out = null;
/*  66 */     Object page = this;
/*  67 */     JspWriter _jspx_out = null;
/*  68 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  72 */       response.setContentType("text/html");
/*  73 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  75 */       _jspx_page_context = pageContext;
/*  76 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  77 */       ServletConfig config = pageContext.getServletConfig();
/*  78 */       session = pageContext.getSession();
/*  79 */       out = pageContext.getOut();
/*  80 */       _jspx_out = out;
/*     */       
/*  82 */       out.write("\n\n\n\n\n\n\n\n\n\n<html xmlns=\"http://www.w3.org/1999/xhtml\">\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n<title>Global Alarm Configuration</title>\n\n");
/*     */       
/*  84 */       request.setAttribute("HelpKey", "Configure Alerts");
/*  85 */       String resourceid = request.getParameter("resourceid");
/*  86 */       String redirect = request.getParameter("redirect");
/*  87 */       int availabilityId = Constants.getAvailIDforResourceID(resourceid);
/*  88 */       int healthId = Constants.getHealthIDforResourceID(resourceid);
/*  89 */       String avaActionImg = "Alarm-gray-tick.gif";
/*  90 */       String healthActionImg = "Alarm-gray-tick.gif";
/*  91 */       if (!DBUtil.getActionDetails(resourceid, availabilityId + "").isEmpty())
/*     */       {
/*  93 */         avaActionImg = "Alarm-green-tick.gif";
/*     */       }
/*  95 */       if (!DBUtil.getActionDetails(resourceid, healthId + "").isEmpty())
/*     */       {
/*  97 */         healthActionImg = "Alarm-green-tick.gif";
/*     */       }
/*  99 */       boolean isdelegatedAdmin = DBUtil.isDelegatedAdmin(request.getRemoteUser());
/*     */       
/* 101 */       out.write("\n\n<script>\nvar message=\"Not Configured\"\nfunction getAlarmDetails(type,resourceid,attributeId,ele)\n{\n$.ajax({  //No I18N\n  async: false, //No I18N\n  cache: false, //No I18N\n  type: \"POST\", //No I18N\n  url: \"/showresource.do\", //No I18N\n  data: {method:\"getAlarmDetails\",type:type,resourceId:resourceid,attributeId:attributeId}, //No I18N\n  success: function(data){message=data; return true;} //No I18N\n  });\n  return true;\n}\n</script>\n</head>\n\n<body>\n\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" class=\"centerAlign\">\n\t\t\t<tr>\n\t\t\t<td class=\"box-header\" colspan=\"3\">");
/* 102 */       out.print(FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.health"));
/* 103 */       out.write("</td>\n\t\t\t</tr>\n<tr>\n\t\t\t<td colspan=\"3\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td style=\"width: 12px;\" >&nbsp;</td>\n\t\t\t<td valign=\"top\">\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n    <tr>\n    <td style=\"padding-top:3px;\" ><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n      <tr>\n          <td >\n           <!--text -->\n          </td>\n      </tr>\n      <tr>\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\n        <tr>\n          <td >&nbsp;</td>\n                <td  valign=\"top\" width=\"100%\">\n\n\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"4\" valign=\"top\" class=\"AlarmtxtSpace\">\n\t\t\t\t\t\t");
/* 104 */       if (!isdelegatedAdmin) {
/* 105 */         out.write("\n\t\t\t\t\t\t");
/* 106 */         out.print(FormatUtil.getString("am.globalHA.conf.Action.text"));
/* 107 */         out.write("\n\t\t\t\t\t\t");
/*     */       } else {
/* 109 */         out.write("\n\t\t\t\t\t\t");
/* 110 */         out.print(FormatUtil.getString("am.globalHA.conf.Action1.text"));
/* 111 */         out.write("\n\t\t\t\t\t\t");
/*     */       }
/* 113 */       out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr><td colspan=\"4\" height=\"5\"></td></tr>\n\t\t\t\t\t");
/*     */       
/* 115 */       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 116 */       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 117 */       _jspx_th_c_005fif_005f0.setParent(null);
/*     */       
/* 119 */       _jspx_th_c_005fif_005f0.setTest("${param.isMG=='true'}");
/* 120 */       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 121 */       if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */         for (;;) {
/* 123 */           out.write("\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td colspan=\"4\" valign=\"top\" class=\"AlarmtxtSpace\">\n\t\t\t\t\t\t\t ");
/* 124 */           out.print(FormatUtil.getString("am.globalHA.conf.ruleDef.text"));
/* 125 */           out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t");
/* 126 */           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 127 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 131 */       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 132 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*     */       }
/*     */       else {
/* 135 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 136 */         out.write("\n\t\t\t\t\t<tr><td colspan=\"4\" height=\"10\"></td></tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"25%\">\t</td>\n\t\t\t\t\t\t<td width=\"5%\">\n\t\t\t\t\t\t");
/*     */         
/* 138 */         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 139 */         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 140 */         _jspx_th_c_005fchoose_005f0.setParent(null);
/* 141 */         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 142 */         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*     */           for (;;) {
/* 144 */             out.write("\n\t\t\t\t\t\t\t");
/*     */             
/* 146 */             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 147 */             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 148 */             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*     */             
/* 150 */             _jspx_th_c_005fwhen_005f0.setTest("${param.isMG=='true'}");
/* 151 */             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 152 */             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*     */               for (;;) {
/* 154 */                 out.write("\n\t\t\t\t\t\t\t\t<input type='button' value='");
/* 155 */                 out.print(FormatUtil.getString("am.globalHA.conf.health"));
/* 156 */                 out.write("' class='buttons btn_highlt' valign='center' onClick=\"javascript:invokePage('");
/* 157 */                 out.print(resourceid);
/* 158 */                 out.write("','/jsp/NewThresholdConfiguration.jsp?resourceid=");
/* 159 */                 out.print(resourceid);
/* 160 */                 out.write("&attributeToSelect=");
/* 161 */                 out.print(healthId);
/* 162 */                 out.write("&attributeIDs=");
/* 163 */                 out.print(healthId);
/* 164 */                 out.write("&global=true&redirectto=");
/* 165 */                 out.print(URLEncoder.encode(redirect));
/* 166 */                 out.write("&PRINTER_FRIENDLY=true&&hideArea=true','','resizable=yes,scrollbars=yes,top=100,left=100,width=950,height=500')\">\n\t\t\t\t\t\t\t");
/* 167 */                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 168 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 172 */             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 173 */               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*     */             }
/*     */             
/* 176 */             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 177 */             out.write("\n\t\t\t\t\t\t\t");
/*     */             
/* 179 */             OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 180 */             _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 181 */             _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 182 */             int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 183 */             if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*     */               for (;;) {
/* 185 */                 out.write("\n\t\t\t\t\t\t\t\t<input type='button' value='");
/* 186 */                 out.print(FormatUtil.getString("am.globalHA.conf.health"));
/* 187 */                 out.write("' class='buttons btn_highlt' valign='center' onclick=\"javascript:invokePage('");
/* 188 */                 out.print(resourceid);
/* 189 */                 out.write("','/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 190 */                 out.print(resourceid);
/* 191 */                 out.write("&attributeToSelect=");
/* 192 */                 out.print(healthId);
/* 193 */                 out.write("&attributeIDs=");
/* 194 */                 out.print(healthId);
/* 195 */                 out.write("&global=true&redirectto=");
/* 196 */                 out.print(URLEncoder.encode(redirect));
/* 197 */                 out.write("&PRINTER_FRIENDLY=true','','resizable=yes,scrollbars=yes,top=100,left=100,width=950,height=500')\">\n\t\t\t\t\t\t\t");
/* 198 */                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 199 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 203 */             if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 204 */               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*     */             }
/*     */             
/* 207 */             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 208 */             out.write("\n\t\t\t\t\t\t");
/* 209 */             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 210 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/*     */         }
/* 214 */         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 215 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*     */         }
/*     */         else {
/* 218 */           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 219 */           out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td  valign=\"top\" align=\"left\">\n\n\t\t\t\t\t\t<table cellspacing='10' cellpadding='5' >\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"100%\" class=\"AlarmtxtSpace\" nowrap='nowrap'>");
/* 220 */           out.print(FormatUtil.getString("am.webclient.rule.actions"));
/* 221 */           out.write("&nbsp; <img src=\"images/");
/* 222 */           out.print(healthActionImg);
/* 223 */           out.write("\"  onmouseover=\"if(getAlarmDetails('1','");
/* 224 */           out.print(resourceid);
/* 225 */           out.write(39);
/* 226 */           out.write(44);
/* 227 */           out.write(39);
/* 228 */           out.print(healthId);
/* 229 */           out.write("',this)){ddrivetip(this,event,message,null,true,'#000000')}\" onmouseout='hideddrivetip()' id=\"health\"/></td>\n\t\t\t\t\t\t\t\t");
/*     */           
/* 231 */           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 232 */           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 233 */           _jspx_th_c_005fif_005f1.setParent(null);
/*     */           
/* 235 */           _jspx_th_c_005fif_005f1.setTest("${param.isMG=='true'}");
/* 236 */           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 237 */           if (_jspx_eval_c_005fif_005f1 != 0) {
/*     */             for (;;) {
/* 239 */               out.write("\n\t\t\t\t\t\t\t\t\t<td width=\"50%\" class=\"AlarmtxtSpace\" nowrap='nowrap'>");
/* 240 */               out.print(FormatUtil.getString("am.webclient.rule.alarmrules"));
/* 241 */               out.write("&nbsp; <img src=\"images/Alarm-green-tick.gif\"  onmouseover=\"if(getAlarmDetails('2','");
/* 242 */               out.print(resourceid);
/* 243 */               out.write(39);
/* 244 */               out.write(44);
/* 245 */               out.write(39);
/* 246 */               out.print(healthId);
/* 247 */               out.write("',this)){ddrivetip(this,event,message,null,true,'#000000')}\" onmouseout='hideddrivetip()'/></td>\n\t\t\t\t\t\t\t\t");
/* 248 */               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 249 */               if (evalDoAfterBody != 2)
/*     */                 break;
/*     */             }
/*     */           }
/* 253 */           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 254 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*     */           }
/*     */           else {
/* 257 */             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 258 */             out.write("\n\t\t\t\t\t\t\t\t<td></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\n\t\t\t\t</table>\n            </td>\n          <td >&nbsp;</td>\n        </tr>\n\n      </table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\">&nbsp;</td>\n\t\t\t</tr>\n\t\t\n\t\t\t</table>\n<br /><br />\n\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" class=\"centerAlign\">\n\t\t\t<tr>\n\t\t\t\n\t\t\t<td class=\"box-header\" colspan=\"3\">");
/* 259 */             out.print(FormatUtil.getString("Availability"));
/* 260 */             out.write("</td>\n\t\t\t</tr>\n            \n            <tr>\n\t\t\t\n\t\t\t<td colspan=\"3\">&nbsp;</td>\n\t\t\t</tr>\n\n\t\t\t<tr>\n\t\t\t<td style=\"width: 12px;\">&nbsp;</td>\n\t\t\t<td valign=\"top\">\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\" align=\"center\">\n    <tr>\n    <td style=\"padding-top:3px;\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">      \n      <tr>\n        <td><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n  \n        <tr>\n          <td >&nbsp;</td>\n                <td valign=\"top\" width=\"100%\">\n\n\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"4\" valign=\"top\" class=\"AlarmtxtSpace\">\n\t\t\t\t\t\t");
/* 261 */             if (!isdelegatedAdmin) {
/* 262 */               out.write("\n\t\t\t\t\t\t");
/* 263 */               out.print(FormatUtil.getString("am.globalHA.conf.Action.text"));
/* 264 */               out.write("\n\t\t\t\t\t\t");
/*     */             } else {
/* 266 */               out.write("\n\t\t\t\t\t\t");
/* 267 */               out.print(FormatUtil.getString("am.globalHA.conf.Action1.text"));
/* 268 */               out.write("\n\t\t\t\t\t\t");
/*     */             }
/* 270 */             out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*     */             
/* 272 */             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 273 */             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 274 */             _jspx_th_c_005fif_005f2.setParent(null);
/*     */             
/* 276 */             _jspx_th_c_005fif_005f2.setTest("${param.isMG=='true'}");
/* 277 */             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 278 */             if (_jspx_eval_c_005fif_005f2 != 0) {
/*     */               for (;;) {
/* 280 */                 out.write("\n\n\t\t\t\t\t\t<tr><td colspan=\"4\" height=\"5\"></td></tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td colspan=\"4\" valign=\"top\" class=\"AlarmtxtSpace\">\n\t\t\t\t\t\t\t ");
/* 281 */                 out.print(FormatUtil.getString("am.globalHA.conf.ruleDef.text"));
/* 282 */                 out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t");
/* 283 */                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 284 */                 if (evalDoAfterBody != 2)
/*     */                   break;
/*     */               }
/*     */             }
/* 288 */             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 289 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*     */             }
/*     */             else {
/* 292 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 293 */               out.write("\n                    <tr><td colspan=\"4\" height=\"5\"></td></tr>                   \n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"4\" valign=\"top\" class=\"AlarmtxtSpace\">\n\t\t\t\t\t\t\n\t\t\t\t\t\t");
/*     */               
/* 295 */               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 296 */               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 297 */               _jspx_th_c_005fchoose_005f1.setParent(null);
/* 298 */               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 299 */               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*     */                 for (;;) {
/* 301 */                   out.write("\n\t\t\t\t\t\t\t");
/*     */                   
/* 303 */                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 304 */                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 305 */                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*     */                   
/* 307 */                   _jspx_th_c_005fwhen_005f1.setTest("${param.isMG=='true'}");
/* 308 */                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 309 */                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*     */                     for (;;) {
/* 311 */                       out.write("\n\t\t\t\t\t\t \t\t");
/* 312 */                       out.print(FormatUtil.getString("am.globalHA.conf.depDevice.monitorGroup.text"));
/* 313 */                       out.write("\n\t\t\t\t\t\t\t");
/* 314 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 315 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 319 */                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 320 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*     */                   }
/*     */                   
/* 323 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 324 */                   out.write("\n\t\t\t\t\t\t\t");
/*     */                   
/* 326 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 327 */                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 328 */                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 329 */                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 330 */                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*     */                     for (;;) {
/* 332 */                       out.write("\n\t\t\t\t\t\t \t\t");
/* 333 */                       out.print(FormatUtil.getString("am.globalHA.conf.depDevice.monitor.text"));
/* 334 */                       out.write("\n\t\t\t\t\t\t\t");
/* 335 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 336 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 340 */                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 341 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*     */                   }
/*     */                   
/* 344 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 345 */                   out.write("\n\t\t\t\t\t\t");
/* 346 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 347 */                   if (evalDoAfterBody != 2)
/*     */                     break;
/*     */                 }
/*     */               }
/* 351 */               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 352 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/*     */               }
/*     */               else {
/* 355 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 356 */                 out.write("\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\n\t\t\t\t\t<tr><td colspan=\"4\" height=\"10\"></td></tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td  valign=\"top\" align=\"center\" width=\"25%\">\t</td>\n\t\t\t\t\t\t<td width=\"5%\">\n\t\t\t\t\t\t");
/*     */                 
/* 358 */                 ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 359 */                 _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 360 */                 _jspx_th_c_005fchoose_005f2.setParent(null);
/* 361 */                 int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 362 */                 if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*     */                   for (;;) {
/* 364 */                     out.write("\n\t\t\t\t\t\t\t");
/*     */                     
/* 366 */                     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 367 */                     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 368 */                     _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*     */                     
/* 370 */                     _jspx_th_c_005fwhen_005f2.setTest("${param.isMG=='true'}");
/* 371 */                     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 372 */                     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*     */                       for (;;) {
/* 374 */                         out.write("\n\t\t\t\t\t\t\t\t<input type='button' value='");
/* 375 */                         out.print(FormatUtil.getString("am.globalHA.conf.availability"));
/* 376 */                         out.write("' class='buttons btn_highlt' valign='center' onClick=\"javascript:invokePage('");
/* 377 */                         out.print(resourceid);
/* 378 */                         out.write("','/jsp/NewThresholdConfiguration.jsp?resourceid=");
/* 379 */                         out.print(resourceid);
/* 380 */                         out.write("&attributeToSelect=");
/* 381 */                         out.print(availabilityId);
/* 382 */                         out.write("&attributeIDs=");
/* 383 */                         out.print(availabilityId);
/* 384 */                         out.write("&global=true&redirectto=");
/* 385 */                         out.print(URLEncoder.encode(redirect));
/* 386 */                         out.write("&PRINTER_FRIENDLY=true&hideArea=true','','resizable=yes,scrollbars=yes,top=100,left=100,width=950,height=500')\">\n\t\t\t\t\t\t\t");
/* 387 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 388 */                         if (evalDoAfterBody != 2)
/*     */                           break;
/*     */                       }
/*     */                     }
/* 392 */                     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 393 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*     */                     }
/*     */                     
/* 396 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 397 */                     out.write("\n\t\t\t\t\t\t\t");
/*     */                     
/* 399 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 400 */                     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 401 */                     _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 402 */                     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 403 */                     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*     */                       for (;;) {
/* 405 */                         out.write("\n\t\t\t\t\t\t\t\t<input type='button' value='");
/* 406 */                         out.print(FormatUtil.getString("am.globalHA.conf.availability"));
/* 407 */                         out.write("' class='buttons btn_highlt' valign='center' onClick=\"javascript:invokePage('");
/* 408 */                         out.print(resourceid);
/* 409 */                         out.write("','/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 410 */                         out.print(resourceid);
/* 411 */                         out.write("&attributeToSelect=");
/* 412 */                         out.print(availabilityId);
/* 413 */                         out.write("&attributeIDs=");
/* 414 */                         out.print(availabilityId);
/* 415 */                         out.write("&global=true&redirectto=");
/* 416 */                         out.print(URLEncoder.encode(redirect));
/* 417 */                         out.write("&PRINTER_FRIENDLY=true','','resizable=yes,scrollbars=yes,top=100,left=100,width=950,height=500')\">\n\t\t\t\t\t\t\t");
/* 418 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 419 */                         if (evalDoAfterBody != 2)
/*     */                           break;
/*     */                       }
/*     */                     }
/* 423 */                     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 424 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*     */                     }
/*     */                     
/* 427 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 428 */                     out.write("\n\t\t\t\t\t\t");
/* 429 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 430 */                     if (evalDoAfterBody != 2)
/*     */                       break;
/*     */                   }
/*     */                 }
/* 434 */                 if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 435 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*     */                 }
/*     */                 else {
/* 438 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 439 */                   out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td  valign=\"top\" align=\"left\">\n\n\t\t\t\t\t\t<table cellspacing='10' cellpadding='5' >\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td  class=\"AlarmtxtSpace\" nowrap='nowrap'>&nbsp;&nbsp;&nbsp;");
/* 440 */                   out.print(FormatUtil.getString("am.webclient.rule.actions"));
/* 441 */                   out.write("&nbsp; <img src=\"images/");
/* 442 */                   out.print(avaActionImg);
/* 443 */                   out.write("\"  onmouseover=\"if(getAlarmDetails('1','");
/* 444 */                   out.print(resourceid);
/* 445 */                   out.write(39);
/* 446 */                   out.write(44);
/* 447 */                   out.write(39);
/* 448 */                   out.print(availabilityId);
/* 449 */                   out.write("',this)){ddrivetip(this,event,message,null,true,'#000000')}\" onmouseout='hideddrivetip()'/></td>\n\t\t\t\t\t\t\t\t");
/*     */                   
/* 451 */                   IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 452 */                   _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 453 */                   _jspx_th_c_005fif_005f3.setParent(null);
/*     */                   
/* 455 */                   _jspx_th_c_005fif_005f3.setTest("${param.isMG=='true'}");
/* 456 */                   int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 457 */                   if (_jspx_eval_c_005fif_005f3 != 0) {
/*     */                     for (;;) {
/* 459 */                       out.write("\n\t\t\t\t\t\t\t\t<td  class=\"AlarmtxtSpace\" nowrap='nowrap'>");
/* 460 */                       out.print(FormatUtil.getString("am.webclient.rule.alarmrules"));
/* 461 */                       out.write("&nbsp; <img src=\"images/Alarm-green-tick.gif\"  onmouseover=\"if(getAlarmDetails('2','");
/* 462 */                       out.print(resourceid);
/* 463 */                       out.write(39);
/* 464 */                       out.write(44);
/* 465 */                       out.write(39);
/* 466 */                       out.print(availabilityId);
/* 467 */                       out.write("',this)){ddrivetip(this,event,message,null,true,'#000000')}\" onmouseout='hideddrivetip()'/></td>\n\t\t\t\t\t\t\t\t");
/* 468 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 469 */                       if (evalDoAfterBody != 2)
/*     */                         break;
/*     */                     }
/*     */                   }
/* 473 */                   if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 474 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*     */                   }
/*     */                   else {
/* 477 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 478 */                     out.write("\n\t\t\t\t\t\t\t\t");
/*     */                     
/* 480 */                     HashMap<String, HashMap<String, String>> dependencyMap = com.adventnet.appmanager.dbcache.AMCacheHandler.getDependentDevice(resourceid);
/* 481 */                     if ((dependencyMap != null) && (!dependencyMap.isEmpty())) {
/* 482 */                       out.write("\n\t\t\t\t\t\t\t\t\t<td  class=\"AlarmtxtSpace\" nowrap='nowrap'>");
/* 483 */                       out.print(FormatUtil.getString("am.webclient.rule.depDevice"));
/* 484 */                       out.write(" &nbsp;<img src=\"images/Alarm-green-tick.gif\" onMouseOver=\"if(getAlarmDetails('3','");
/* 485 */                       out.print(resourceid);
/* 486 */                       out.write(39);
/* 487 */                       out.write(44);
/* 488 */                       out.write(39);
/* 489 */                       out.print(availabilityId);
/* 490 */                       out.write("',this)){ddrivetip(this,event,message,null,true,'#000000')}\" onmouseout='hideddrivetip()' /></td>\n\t\t\t\t\t\t\t\t");
/*     */                     } else {
/* 492 */                       out.write("\n\t\t\t\t\t\t\t\t\t<td  class=\"AlarmtxtSpace\" nowrap='nowrap'>");
/* 493 */                       out.print(FormatUtil.getString("am.webclient.rule.depDevice"));
/* 494 */                       out.write(" &nbsp;<img src=\"images/Alarm-gray-tick.gif\" onMouseOver=\"if(getAlarmDetails('3','");
/* 495 */                       out.print(resourceid);
/* 496 */                       out.write(39);
/* 497 */                       out.write(44);
/* 498 */                       out.write(39);
/* 499 */                       out.print(availabilityId);
/* 500 */                       out.write("',this)){ddrivetip(this,event,message,null,true,'#000000')}\" onmouseout='hideddrivetip()'/></td>\n\t\t\t\t\t\t\t\t");
/*     */                     }
/* 502 */                     out.write("\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t<td></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n            </td>\n          <td >&nbsp;</td>\n        </tr>\n\n      </table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\">&nbsp;</td>\n\t\t\t</tr>\t\t\n\t\t\t</table>\n\n</body>\n</html>\n\n\n");
/*     */                   }
/* 504 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 505 */         out = _jspx_out;
/* 506 */         if ((out != null) && (out.getBufferSize() != 0))
/* 507 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 508 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 511 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\GlobalHealthAvailabilityConfig_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */