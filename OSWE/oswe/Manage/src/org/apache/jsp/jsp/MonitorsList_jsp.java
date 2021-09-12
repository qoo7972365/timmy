/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*     */ import com.adventnet.appmanager.struts.actions.ShowResourceDetails;
/*     */ import com.adventnet.appmanager.util.Constants;
/*     */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspApplicationContext;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import javax.servlet.jsp.tagext.JspTag;
/*     */ import javax.servlet.jsp.tagext.Tag;
/*     */ import org.apache.jasper.runtime.HttpJspBase;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.bean.WriteTag;
/*     */ import org.apache.struts.taglib.logic.IterateTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ import org.apache.tomcat.InstanceManager;
/*     */ 
/*     */ public final class MonitorsList_jsp extends HttpJspBase implements JspSourceDependent
/*     */ {
/*  35 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  41 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/*  42 */   static { _jspx_dependants.put("/jsp/includes/monitortypes.jspf", Long.valueOf(1473429417000L)); }
/*     */   
/*     */ 
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid;
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private InstanceManager _jsp_instancemanager;
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  54 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  58 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  59 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  60 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  61 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  62 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  63 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  67 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/*  68 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*  69 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.release();
/*  70 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  77 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  80 */     JspWriter out = null;
/*  81 */     Object page = this;
/*  82 */     JspWriter _jspx_out = null;
/*  83 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  87 */       response.setContentType("text/html");
/*  88 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  90 */       _jspx_page_context = pageContext;
/*  91 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  92 */       ServletConfig config = pageContext.getServletConfig();
/*  93 */       session = pageContext.getSession();
/*  94 */       out = pageContext.getOut();
/*  95 */       _jspx_out = out;
/*     */       
/*  97 */       out.write("<!--$Id$-->\n\n\n\n\n");
/*  98 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*     */         return;
/* 100 */       out.write("\n<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t<tr>\n\t\t<td width=\"100%\">\n\t\t\t<div id=\"wrapper1\">\n\t\t\t\t<table class=\"newmonitor-td-bg\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tbody>\n\t\t\t\t\t\t<tr class=\"admin-hide\">\n\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t<div id=\"header-newmonitor\" class=\"sptborder\">\n\t\t\t\t\t\t\t\t\t<div class=\"content\">\n\t\t\t\t\t\t\t\t\t\t<span style=\"position:relative; top:10px;\">\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"new-monitor-tableheading\" style=\"position: relative; top: 2px; \">\n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 101 */       out.print(FormatUtil.getString("am.webclient.monitor.template.title"));
/* 102 */       out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<span style=\"position: relative; top: 0px;\" class=\"bodytext\"><b style=\"color:#eb7834; font-weight:bold; font-size:13px;\">(");
/* 103 */       out.print(FormatUtil.getString("am.webclient.monitor.template.configure"));
/* 104 */       out.write(")</b>\n\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t</span>\n\n\t\t\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td align=\"right\" >\n\t\t\t\t\t\t\t\t\t<div id=\"header-newmonitor\" class=\"sptborder\">\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"content\">\n\t\t\t\t\t\t\t\t\t\t\t\t<span style=\"position:relative; top:10px;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"new-monitor-tableheading\" style=\"position: relative; top: 2px; \">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 105 */       if ((request.getParameter("listByOrder") != null) && (request.getParameter("listByOrder").equals("true"))) {
/* 106 */         out.write("\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"level1-a\" id=\"listMonitors\" href='/showTile.do?TileName=Tile.NewMonitorList' class='bodytext-nounderline' title=\"");
/* 107 */         out.print(FormatUtil.getString("am.webclient.newmonitor.listby.category"));
/* 108 */         out.write("\"><b>");
/* 109 */         out.print(FormatUtil.getString("am.webclient.newmonitor.listby.category"));
/* 110 */         out.write("</b> </a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*     */       } else {
/* 112 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a class=\"level1-a\" id=\"listMonitors\" href='/showTile.do?TileName=Tile.NewMonitorList&listByOrder=true' class='bodytext-nounderline' title=\"");
/* 113 */         out.print(FormatUtil.getString("am.webclient.newmonitor.listby.order"));
/* 114 */         out.write("\"><b>");
/* 115 */         out.print(FormatUtil.getString("am.webclient.newmonitor.listby.order"));
/* 116 */         out.write("</b></a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*     */       }
/* 118 */       out.write("\n\t\t\t\t\t\t\t\t\t\t\t</span></span></div></div>\t\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"100%\" valign=\"top\" colspan=\"2\">\n\t\t\t\t\t\t\t\t<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t");
/* 119 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link href=\"/images/");
/* 120 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/* 122 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/*     */       
/* 124 */       ShowResourceDetails sh = new ShowResourceDetails();
/* 125 */       List list = new ArrayList();
/* 126 */       boolean listMonitorsByorder = (request.getParameter("listByOrder") != null) && (request.getParameter("listByOrder").equals("true"));
/* 127 */       list = listMonitorsByorder == true ? sh.getMonitorTypesforNewMonitor(true) : sh.getMonitorTypesforNewMonitor();
/* 128 */       ConfMonitorConfiguration conf = ConfMonitorConfiguration.getInstance();
/* 129 */       String isAgent = "NO";
/* 130 */       TreeMap eumMonitors = new TreeMap();
/* 131 */       pageContext.setAttribute("eumMonitors", eumMonitors);
/* 132 */       int totCount = 21;
/* 133 */       int dispCount = 0;
/*     */       
/* 135 */       out.write(10);
/* 136 */       out.write(10);
/* 137 */       out.write(9);
/* 138 */       String category = Constants.getCategorytype();
/* 139 */       int colcount = 0; for (int i = 0; i < list.size() - 2; i++)
/*     */       {
/* 141 */         ArrayList sublist = (ArrayList)list.get(i);
/* 142 */         String s = (String)sublist.get(0);
/* 143 */         String value = (String)sublist.get(1);
/* 144 */         String name = (String)sublist.get(0);
/*     */         
/* 146 */         if ((value != null) && (value.equals("APP")))
/*     */         {
/* 148 */           out.write("\n\t\t\t<td valign=\"top\">\n\t\t\t\t<div id=\"outer3\">\n\t\t\t\t<div class=\"content\">\n\t\t\t");
/* 149 */           out.println("<h3>" + s + "</h3>");
/*     */ 
/*     */ 
/*     */         }
/* 153 */         else if ((value != null) && ((value.equals("VIR")) || (value.equals("DBS")) || (value.equals("TM")) || (value.equals("SYS")) || (value.equals("SER")) || (value.equals("URL")) || (value.equals("MS")) || (value.equals("CAM")) || (value.equals("ALL")) || (value.equals("ERP")) || (value.equals("MOM")) || (value.equals("CLD"))))
/*     */         {
/*     */ 
/* 156 */           if (((EnterpriseUtil.isCloudEdition()) && ((value.equals("SER")) || (value.equals("CAM")))) || ((!EnterpriseUtil.isCloudEdition()) && ((value.equals("SYS")) || (value.equals("SER")) || (value.equals("MOM")))))
/*     */           {
/*     */ 
/*     */ 
/* 160 */             out.write("\n\t\t       \t     </div></div></td>\n\t\t       \t     <td valign=\"top\">\n\t\t       \t     <div id=\"outer1\">\n\t\t\t\t<div class=\"content\">\n\n\t\t       ");
/*     */           }
/*     */           else {
/* 163 */             out.println("<br>");
/*     */           }
/* 165 */           out.println("<h3>" + s + "</h3>");
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 170 */           if ((listMonitorsByorder) && ((dispCount == 20) || (dispCount == 0))) {
/* 171 */             String divStyle = "outer3";
/* 172 */             if (dispCount == 20) {
/* 173 */               dispCount = 0;
/* 174 */               divStyle = "outer1";
/*     */               
/* 176 */               out.write("\n\t\t\t\t\t   </div></div></td>\n\t\t\t\t");
/*     */             }
/* 178 */             out.write("\n\t\t\t\n\t\t\t\t<td valign=\"top\">\n\t\t\t\t<div id=\"");
/* 179 */             out.print(divStyle);
/* 180 */             out.write("\">\n\t\t\t\t<div class=\"content\">\n\t\t");
/*     */           }
/* 182 */           dispCount++;
/* 183 */           String resourceType = (String)sublist.get(3);
/* 184 */           if ((EnterpriseUtil.isAdminServer()) && ((resourceType.equals("APM-Insight-Instance")) || (resourceType.equals("RBM")) || (resourceType.equals("UrlSeq")) || (resourceType.equals("Custom-Application")) || (resourceType.equals("SAP-CCMS")))) {
/* 185 */             String alertMessage = FormatUtil.getString("am.webclient.admin.add.monitor.not.supported.alert.text", new String[] { s });
/* 186 */             out.println("<p><a href='javascript:alert(\"" + alertMessage + "\")' title=\"" + alertMessage + "\">");
/* 187 */             out.println(s + "&nbsp;");
/* 188 */             if ((Constants.addonList != null) && (Constants.addonList.contains(resourceType)) && (!EnterpriseUtil.isCloudEdition()))
/*     */             {
/* 190 */               out.println("<img src=\"/images/icon_addon.gif\" align=\"middle\" style=\"position:relative;bottom:3px\" border=\"0\" title='" + FormatUtil.getString("am.webclient.addon.tooltip") + "' >&nbsp;");
/*     */             }
/* 192 */             out.println("</a></p>");
/*     */           } else {
/* 194 */             out.println("<p><a href=\"/adminAction.do?method=reloadHostDiscoveryForm&type=" + value + "&restype=" + resourceType + "&haid=" + request.getParameter("haid") + "\">");
/* 195 */             out.println(s + "&nbsp;");
/* 196 */             if ((Constants.addonList != null) && (Constants.addonList.contains(resourceType)) && (!EnterpriseUtil.isCloudEdition()) && (!Constants.isIt360))
/*     */             {
/* 198 */               out.println("<img src=\"/images/icon_addon.gif\" align=\"middle\" style=\"position:relative;bottom:3px\" border=\"0\" title='" + FormatUtil.getString("am.webclient.addon.tooltip") + "' >&nbsp;");
/*     */             }
/* 200 */             out.println("</a></p>");
/*     */           }
/* 202 */           isAgent = conf.getTypeDescription(resourceType) != null ? conf.getTypeDescription(resourceType).getProperty("IS-AGENT-ENABLED") : "No";
/* 203 */           if (((value != null) && (value.equals("RBM"))) || ((isAgent != null) && (!isAgent.equals("null")) && (isAgent.equalsIgnoreCase("YES")))) {
/* 204 */             if ((EnterpriseUtil.isAdminServer()) && (resourceType.equals("RBM"))) {
/* 205 */               String alertMessage = FormatUtil.getString("am.webclient.admin.add.monitor.not.supported.alert.text", new String[] { s });
/* 206 */               eumMonitors.put(s, "javascript:alert('" + alertMessage + "')");
/*     */             } else {
/* 208 */               eumMonitors.put(s, "/adminAction.do?method=reloadHostDiscoveryForm&type=" + value + "&restype=" + resourceType + "&haid=" + request.getParameter("haid"));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 215 */       out.write("\n<script>\n\tSet_Cookie('listMonitorsByorder','");
/* 216 */       out.print(listMonitorsByorder);
/* 217 */       out.write("'); //NO I18N\n</script>\n\t<br>\n\n</div>\n\t<div class=\"header-heading-newmonitor\"><h3 style=\"padding-left:8px\">");
/* 218 */       out.print(FormatUtil.getString("am.new.createmonitor.text"));
/* 219 */       out.write("&nbsp;&nbsp;</h3>");
/* 220 */       out.write("\n</div>\n\t<div class=\"content\">\n\t<p><a href=\"/monitorType.do?method=showTypes\">\n\t\t");
/* 221 */       out.print(FormatUtil.getString("am.webclient.createnewmonitor"));
/* 222 */       out.write("&nbsp;\n\t</a></p>\n\t</div>\n\n\n\t<br>\n\n</div>\n</td>\n\n<td valign=\"top\">\n\t");
/* 223 */       if (!EnterpriseUtil.isCloudEdition()) {
/* 224 */         out.write("\n\t<div id=\"outer2\">\n\t\t<div class=\"header-heading\">\n\t\t\t<h3>");
/* 225 */         out.print(FormatUtil.getString("am.webclient.eum.add"));
/* 226 */         if (!Constants.isIt360) {
/* 227 */           out.write("<img src=\"images/icon_addon.gif\" style=\"position: relative; left: 3px;\">");
/*     */         }
/* 229 */         out.write("</h3>\n\t\t</div>\n\t\t<div class=\"content\">\n\t\t\t");
/*     */         
/* 231 */         IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 232 */         _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 233 */         _jspx_th_logic_005fiterate_005f0.setParent(null);
/*     */         
/* 235 */         _jspx_th_logic_005fiterate_005f0.setId("monitors");
/*     */         
/* 237 */         _jspx_th_logic_005fiterate_005f0.setName("eumMonitors");
/*     */         
/* 239 */         _jspx_th_logic_005fiterate_005f0.setIndexId("m");
/* 240 */         int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 241 */         if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 242 */           Object monitors = null;
/* 243 */           Integer m = null;
/* 244 */           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 245 */             out = _jspx_page_context.pushBody();
/* 246 */             _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 247 */             _jspx_th_logic_005fiterate_005f0.doInitBody();
/*     */           }
/* 249 */           monitors = _jspx_page_context.findAttribute("monitors");
/* 250 */           m = (Integer)_jspx_page_context.findAttribute("m");
/*     */           for (;;) {
/* 252 */             out.write("\n\t\t\t\t<p><a href=\"");
/* 253 */             if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*     */               return;
/* 255 */             out.write(34);
/* 256 */             out.write(62);
/* 257 */             if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*     */               return;
/* 259 */             out.write("&nbsp;</a></p>\n\t\t\t");
/* 260 */             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 261 */             monitors = _jspx_page_context.findAttribute("monitors");
/* 262 */             m = (Integer)_jspx_page_context.findAttribute("m");
/* 263 */             if (evalDoAfterBody != 2)
/*     */               break;
/*     */           }
/* 266 */           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 267 */             out = _jspx_page_context.popBody();
/*     */           }
/*     */         }
/* 270 */         if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 271 */           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*     */         }
/*     */         
/* 274 */         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 275 */         out.write("\n\t\t</div>\n\t\t<br>\n\t</div>\n\t");
/*     */       }
/* 277 */       out.write("\n</td>\n");
/* 278 */       out.write("\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\t\t\t</div>\n\t\t</td>\n\t</tr>\n</table>\n");
/*     */     } catch (Throwable t) {
/* 280 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 281 */         out = _jspx_out;
/* 282 */         if ((out != null) && (out.getBufferSize() != 0))
/* 283 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 284 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 287 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 293 */     PageContext pageContext = _jspx_page_context;
/* 294 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 296 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 297 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 298 */     _jspx_th_c_005fif_005f0.setParent(null);
/*     */     
/* 300 */     _jspx_th_c_005fif_005f0.setTest("${reqForAdminLayout}");
/* 301 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 302 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*     */       for (;;) {
/* 304 */         out.write("\n<style>\n.admin-hide\n{\n   display:none;\n}\n</style>\n\n");
/* 305 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 306 */         if (evalDoAfterBody != 2)
/*     */           break;
/*     */       }
/*     */     }
/* 310 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 311 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 312 */       return true;
/*     */     }
/* 314 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 315 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 320 */     PageContext pageContext = _jspx_page_context;
/* 321 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 323 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 324 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 325 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 327 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 329 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 330 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 331 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 332 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 333 */       return true;
/*     */     }
/* 335 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 336 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 341 */     PageContext pageContext = _jspx_page_context;
/* 342 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 344 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 345 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 346 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*     */     
/* 348 */     _jspx_th_bean_005fwrite_005f0.setName("monitors");
/*     */     
/* 350 */     _jspx_th_bean_005fwrite_005f0.setProperty("value");
/* 351 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 352 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 353 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 354 */       return true;
/*     */     }
/* 356 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 357 */     return false;
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 362 */     PageContext pageContext = _jspx_page_context;
/* 363 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 365 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 366 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 367 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*     */     
/* 369 */     _jspx_th_bean_005fwrite_005f1.setName("monitors");
/*     */     
/* 371 */     _jspx_th_bean_005fwrite_005f1.setProperty("key");
/* 372 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 373 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 374 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 375 */       return true;
/*     */     }
/* 377 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 378 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MonitorsList_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */