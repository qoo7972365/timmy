/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.fault.rcatree.AMRCATreeCreator;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class RCA_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  23 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  34 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  38 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  39 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  40 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  44 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  51 */     HttpSession session = null;
/*     */     
/*     */ 
/*  54 */     JspWriter out = null;
/*  55 */     Object page = this;
/*  56 */     JspWriter _jspx_out = null;
/*  57 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  61 */       response.setContentType("text/html;charset=UTF-8");
/*  62 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  64 */       _jspx_page_context = pageContext;
/*  65 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  66 */       ServletConfig config = pageContext.getServletConfig();
/*  67 */       session = pageContext.getSession();
/*  68 */       out = pageContext.getOut();
/*  69 */       _jspx_out = out;
/*     */       
/*  71 */       out.write("<!--$Id$-->\n<HTML>\n\n\n\n\n\n\n\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<title>");
/*  72 */       out.print(FormatUtil.getString("am.webclient.rca.title"));
/*  73 */       out.write("</title>\n\n");
/*     */       
/*  75 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/*  76 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*  77 */       String resIdStr = request.getParameter("resourceid");
/*  78 */       String attrIdStr = request.getParameter("attributeid");
/*  79 */       int resId = Integer.parseInt(resIdStr);
/*  80 */       int attrId = Integer.parseInt(attrIdStr);
/*  81 */       String skin = "";
/*     */       
/*  83 */       if (request.getParameter("selectedskin") != null)
/*     */       {
/*  85 */         skin = request.getParameter("selectedskin");
/*  86 */         if (!skin.equals(""))
/*     */         {
/*  88 */           request.setAttribute("selectedskin", skin);
/*     */         }
/*     */       }
/*     */       
/*  92 */       out.write("\n<link href=\"/images/");
/*  93 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  95 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<script>\nfunction openURLInParent(url)\n{\n  window.opener.location.href=url;\n}\n</script>\n</head>\n");
/*  96 */       AMRCATreeCreator RCATree = null;
/*  97 */       RCATree = (AMRCATreeCreator)_jspx_page_context.getAttribute("RCATree", 2);
/*  98 */       if (RCATree == null) {
/*  99 */         RCATree = new AMRCATreeCreator();
/* 100 */         _jspx_page_context.setAttribute("RCATree", RCATree, 2);
/*     */       }
/* 102 */       out.write(10);
/*     */       
/* 104 */       session.setAttribute("RCATree_Model", RCATree.getTreeModel(resId, attrId));
/*     */       
/* 106 */       out.write("\n\n<body leftmargin=\"7\" topmargin=\"7\" marginwidth=\"7\" marginheight=\"7\">\n\t<table width=\"100%\" height=\"55\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"darkheaderbg\">\n\t\t<tr>\n\t\t\t<td>&nbsp;<span class=\"headingboldwhite\">");
/* 107 */       out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 108 */       out.write("</span></td>\n\t\t</tr>\n\t</table>\n\t<br>\n\t<table width=\"98%\" border=0 cellpadding=0 cellspacing=0 class=\"lrtbdarkborder\" align=\"center\">\n  \t\t<tr>\n    \t\t<td width=\"100%\">\n      \t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        \t\t\t<tr>\n          \t\t\t\t<td>\n            \t\t\t");
/*     */       
/* 110 */       int currentstatus = RCATree.getCurrentStatus();
/* 111 */       String timestamp = RCATree.getRCAnalysisTime();
/* 112 */       String defaultMessage = RCATree.getFirstNodeDescription();
/* 113 */       StringBuffer buf = new StringBuffer();
/* 114 */       RCATree.updateRuleDetails(buf, resIdStr, attrIdStr);
/* 115 */       String rule = buf.toString();
/* 116 */       String attributeName = RCATree.getFirstNodeAttributeDisplayNameWithUnit();
/* 117 */       String resourceName = RCATree.getFirstNodeResourceName();
/* 118 */       if ((attrId == 18) || (attrId == 17))
/*     */       {
/* 120 */         String typeId = com.adventnet.appmanager.util.DBUtil.getAttributeDetails(attrId)[0];
/* 121 */         rule = com.adventnet.appmanager.fault.RuleAnalyser.getRuleMatched(resIdStr, typeId);
/*     */       }
/* 123 */       if (rule.equals(""))
/*     */       {
/* 125 */         rule = FormatUtil.getString("am.webclient.qenginescript.text");
/*     */       }
/* 127 */       if (timestamp == null)
/*     */       {
/* 129 */         timestamp = FormatUtil.getString("am.webclient.qenginescript.text");
/*     */       }
/* 131 */       if (defaultMessage.equals(""))
/*     */       {
/* 133 */         defaultMessage = FormatUtil.getString("am.webclient.qenginescript.message");
/*     */       }
/* 135 */       if (((defaultMessage.startsWith(FormatUtil.getString("am.webclient.rcamessage.unabletpperform.text"))) || (defaultMessage.startsWith(FormatUtil.getString("am.webclient.rcamessage.healthunknown.text")))) && (!rule.equals(FormatUtil.getString("am.fault.rcatree.anyonerule.text"))))
/*     */       {
/* 137 */         defaultMessage = FormatUtil.getString("am.webclient.rcamessage.waitfornextpolling.text");
/*     */       }
/*     */       
/* 140 */       out.write("\n          \t\t\t\t</td>\n\t        \t\t</tr>\n\t        \t\t<tr>\n\t          \t\t\t<td>\n\t          \t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t          \t\t\t\t\t<tr>\n\t          \t\t\t\t\t\t<td width=\"8%\" height=\"28\" class=\"columnheadingb\">&nbsp;<span class=\"bodytextbold\">");
/* 141 */       out.print(FormatUtil.getString("am.webclient.rca.name"));
/* 142 */       out.write("</span></td>\n\t          \t\t\t\t\t\t<td width=\"44%\" class=\"columnheadingb\" title=\"");
/* 143 */       out.print(resourceName);
/* 144 */       out.write("\">&nbsp;:&nbsp;");
/* 145 */       out.print(FormatUtil.getTrimmedText(resourceName, 45));
/* 146 */       out.write("</td>\n\t          \t\t\t\t\t\t<td width=\"18%\" class=\"columnheadingb\"><span class=\"bodytextbold\">&nbsp;");
/* 147 */       out.print(FormatUtil.getString("am.webclient.rca.statussince"));
/* 148 */       out.write("</span></td>\n\t                \t\t\t\t<td width=\"30%\" class=\"columnheadingb\">&nbsp;:&nbsp;");
/* 149 */       out.print(timestamp);
/* 150 */       out.write("</td>\n\t              \t\t\t\t</tr>\n\t               \t\t\t\t<tr>\n\t               \t\t\t\t\t<td class=\"bottomborder\" height=\"28\">&nbsp;&nbsp;");
/* 151 */       out.print(FormatUtil.getString("am.webclient.rca.attribute"));
/* 152 */       out.write("</td>\n\t               \t\t\t\t\t<td class=\"bottomborder\"><span class=\"bodytext\">&nbsp;&nbsp;:&nbsp;");
/* 153 */       out.print(attributeName);
/* 154 */       out.write("</span> </td>\n\t\t\t\t                  \t");
/*     */       
/* 156 */       if ((attrId == 18) || (attrId == 17))
/*     */       {
/*     */ 
/* 159 */         out.write("\n\t\t\t\t                  \t<td class=\"bottomborder\">&nbsp;&nbsp;");
/* 160 */         out.print(FormatUtil.getString("am.webclient.rca.dependencyrule"));
/* 161 */         out.write("<span style=\"float:right;\">&nbsp;:&nbsp; </span></td>\n\t\t\t\t                  \t<td class=\"bottomborder\">\n\t\t\t\t                  \t\t<div style=\"padding-left:5px; float:right; width:100%; display:block;\">\n\t\t\t\t                  \t\t\t<a href=\"javascript:void(0)\" onClick=\"javascript:openURLInParent('/jsp/ThresholdActionConfiguration.jsp?popup=false&attributeToSelect=");
/* 162 */         out.print(attrId);
/* 163 */         out.write("&attributeIDs=");
/* 164 */         out.print(attrId);
/* 165 */         out.write("&global=true&resourceid=");
/* 166 */         out.print(resIdStr);
/* 167 */         out.write("&redirectto=showActionProfiles.do?method=getHAProfiles&admin=true&all=true&haid=");
/* 168 */         out.print(resIdStr);
/* 169 */         out.write("&hideArea=true');\" class=\"staticlinks\">");
/* 170 */         out.print(rule);
/* 171 */         out.write("</a>\n\t\t\t\t                  \t\t</div>\n\t\t\t\t                  \t</td>\n\t\t\t\t                 \t");
/*     */ 
/*     */       }
/* 174 */       else if (attributeName.equals("Health"))
/*     */       {
/*     */ 
/* 177 */         out.write("\n\t\t\t\t                 \t<td class=\"bottomborder\">&nbsp;&nbsp;");
/* 178 */         out.print(FormatUtil.getString("am.webclient.rca.dependencyrule"));
/* 179 */         out.write("</td>\n\t\t\t\t                  \t<td class=\"bottomborder\">\n\t\t\t\t                  \t\t<span class=\"bodytext\">:&nbsp;\n\t\t\t\t                  \t\t\t<a href=\"javascript:void(0)\" onClick=\"javascript:openURLInParent('/jsp/ThresholdActionConfiguration.jsp?popup=false&attributeToSelect=");
/* 180 */         out.print(attrId);
/* 181 */         out.write("&attributeIDs=");
/* 182 */         out.print(attrId);
/* 183 */         out.write("&resourceid=");
/* 184 */         out.print(resIdStr);
/* 185 */         out.write("&check=true&redirectto=showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=");
/* 186 */         out.print(resIdStr);
/* 187 */         out.write("');\" class=\"staticlinks\">");
/* 188 */         out.print(rule);
/* 189 */         out.write("</a>\n\t\t\t\t                  \t\t</span>\n\t\t\t\t                 \t");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 195 */         out.write("\n\t\t\t\t                  \t<td  class=\"bottomborder\">&nbsp;<span class=\"disabledtext\">");
/* 196 */         out.print(FormatUtil.getString("am.webclient.rca.dependencyrule"));
/* 197 */         out.write("</span></td>\n\t\t\t\t                  \t<td class=\"bottomborder\"><span class=\"disabledtext\">:&nbsp;");
/* 198 */         out.print(rule);
/* 199 */         out.write("</span>\n\t\t\t\t                 \t");
/*     */       }
/*     */       
/*     */ 
/* 203 */       out.write("\n\t\t\t\t                  </td>\n\t              \t\t\t\t</tr>\n\t            \t\t\t</table>\n\t            \t\t</td>\n\t        \t\t</tr>\n\t        \t\t<tr>\n\t        \t\t\t<td height=\"296\" align=\"center\">\n\t        \t\t\t\t<table width=\"90%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t        \t\t\t\t\t<tr>\n\t        \t\t\t\t\t\t<td width=\"99%\" height=\"49\">\n\t        \t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"grayfullborder\">\n\t        \t\t\t\t\t\t\t\t<tr>\n\t        \t\t\t\t\t\t\t\t\t<td>   \n\t        \t\t\t\t\t\t\t\t\t");
/*     */       
/*     */ 
/* 206 */       String nodeClicked = "";
/* 207 */       if ((resIdStr != null) && (attrIdStr != null))
/*     */       {
/* 209 */         nodeClicked = "nodeClicked=" + resIdStr + attrId + "&";
/*     */       }
/* 211 */       String constructURL = "/jsp/AttributeDetails.jsp?haid=" + resId;
/* 212 */       String link = "<a href=" + constructURL + "  class=\"StaticLinks-Anomaly\" target='_blank'>" + FormatUtil.getString("am.webclient.anomaly.anomalydashboard.message") + "</a>";
/*     */       
/* 214 */       out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 215 */       if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) {
/* 216 */         String queryStr = request.getQueryString();
/* 217 */         int ticketidx = queryStr.indexOf("&ticket");
/* 218 */         if (ticketidx != -1) {
/* 219 */           queryStr = queryStr.substring(0, ticketidx);
/*     */         }
/*     */         
/* 222 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\n\t            \t\t\t\t\t\t\t\t<iframe src=\"RCATop.jsp?");
/* 223 */         out.print(nodeClicked);
/* 224 */         out.print(queryStr);
/* 225 */         out.write("\" height=\"160\" width=\"100%\" frameborder=\"0\"></iframe>\n\t\t\t\t\t\t\t\t\t\t");
/*     */       } else {
/* 227 */         out.write("\t\t\n\t            \t\t\t\t\t\t\t\t<iframe src=\"RCATop.jsp?");
/* 228 */         out.print(nodeClicked);
/* 229 */         out.print(request.getQueryString());
/* 230 */         out.write("\" height=\"160\" width=\"100%\" frameborder=\"0\"></iframe>");
/*     */       }
/* 232 */       out.write("\n</td>\n\t                    \t\t\t\t\t</tr>\n\t                    \t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 233 */       out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*     */       
/* 235 */       if ((attrId == 3200) || ("APM-Insight-Instance".intern().equals(request.getParameter("monitorType"))))
/*     */       {
/*     */ 
/* 238 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"columnheadingtb\">");
/* 239 */         out.print(FormatUtil.getString("am.webclient.rca.rcamessage"));
/* 240 */         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 246 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"columnheadingtb\">");
/* 247 */         out.print(FormatUtil.getString("am.webclient.rca.rcamessage"));
/* 248 */         out.write(32);
/* 249 */         if ((currentstatus == 1) && (!EnterpriseUtil.isAdminServer()) && (!isTablet)) {
/* 250 */           out.write(" : <span class=\"bodytext\">");
/* 251 */           out.print(FormatUtil.getString("am.webclient.anomaly.anomalydashboardlink.message", new String[] { link }));
/* 252 */           out.write("</span>");
/*     */         }
/* 254 */         out.write("</td>\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*     */       }
/*     */       
/*     */ 
/* 258 */       out.write("\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<FORM name=\"rcaform\" style=\"display:inline\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"100%\" CELLPADDING=\"0\" class=\"tdindent\" height=\"75\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 259 */       defaultMessage = EnterpriseUtil.decodeString(defaultMessage);
/* 260 */       defaultMessage = defaultMessage.replaceAll("\n", "<br>").replaceAll("\"", "\\\"");
/*     */       
/* 262 */       out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"rcaText\" style=\"display:block; position:relative; padding: 4px;\" class=\"rcatextarea\">");
/* 263 */       out.print(defaultMessage);
/* 264 */       out.write("</div>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</form>\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t</td>\n\t                \t\t\t\t<td width=\"1%\" valign=\"top\" background=\"../images/bg_shadow_vertical.gif\">\n\t                \t\t\t\t\t<img src=\"../images/bg_shadow_topcorner.gif\" width=\"4\" height=\"5\">\n\t                \t\t\t\t</td>\n\t                \t\t\t</tr>\n\t                \t\t\t<tr>\n\t                \t\t\t\t<td  background=\"../images/bg_shadow_bottomborder.gif\">\n\t                \t\t\t\t\t<img src=\"../images/bg_shadow_leftcorner.gif\" width=\"6\" height=\"5\">\n\t                \t\t\t\t</td>\n\t                \t\t\t\t<td>\n\t                \t\t\t\t\t<img src=\"../images/bg_shadow_rightcorner.gif\" width=\"4\" height=\"5\">\n\t                \t\t\t\t</td>\n\t                \t\t\t</tr>\n\t                \t\t</table>\n\t                \t</td>\n\t                </tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td height=\"2\"> </td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"tdindent\" height=\"40\">\n\t\t\t\t<span class=\"arial9\">");
/* 265 */       out.print(FormatUtil.getString("am.webclient.rcamessage.rca"));
/* 266 */       out.write("</span>\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n</HTML>\n");
/*     */     } catch (Throwable t) {
/* 268 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 269 */         out = _jspx_out;
/* 270 */         if ((out != null) && (out.getBufferSize() != 0))
/* 271 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 272 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 275 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 281 */     PageContext pageContext = _jspx_page_context;
/* 282 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 284 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 285 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 286 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 288 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 290 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 291 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 292 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 293 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 294 */       return true;
/*     */     }
/* 296 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 297 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\RCA_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */