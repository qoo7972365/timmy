/*     */ package org.apache.jsp.jsp;
/*     */ 
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.struts.taglib.logic.PresentTag;
/*     */ 
/*     */ public final class tabs_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  22 */   private static final JspFactory _jspxFactory = ;
/*     */   
/*     */   private static Map<String, Long> _jspx_dependants;
/*     */   
/*     */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*     */   
/*     */   private ExpressionFactory _el_expressionfactory;
/*     */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*     */   
/*     */   public Map<String, Long> getDependants()
/*     */   {
/*  33 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  37 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  38 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  39 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  43 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  50 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  53 */     JspWriter out = null;
/*  54 */     Object page = this;
/*  55 */     JspWriter _jspx_out = null;
/*  56 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  60 */       response.setContentType("text/html");
/*  61 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  63 */       _jspx_page_context = pageContext;
/*  64 */       ServletContext application = pageContext.getServletContext();
/*  65 */       ServletConfig config = pageContext.getServletConfig();
/*  66 */       session = pageContext.getSession();
/*  67 */       out = pageContext.getOut();
/*  68 */       _jspx_out = out;
/*     */       
/*  70 */       out.write("<!-- $Id$-->\n\n\n\n\n");
/*     */       
/*  72 */       double randomnumber = Math.random();
/*  73 */       String tableWidth = (request.getAttribute("fullpercent") != null) && (((String)request.getAttribute("fullpercent")).equals("true")) ? "100%" : "99%";
/*     */       
/*  75 */       out.write("\n<script>\nvar tabsTableID=\"InnerTab");
/*  76 */       out.print(randomnumber);
/*  77 */       out.write("\";\nfunction resizeTabContent(){\n  ");
/*     */       
/*  79 */       if (com.manageengine.appmanager.plugin.PluginUtil.isPlugin())
/*     */       {
/*     */ 
/*  82 */         out.write("\n    if(window!=top){\n      setTimeout(function(){postMessage('height:500')},1000); //NO I18N\n      setTimeout(function(){\n        $('body').trigger('click'); //NO I18N\n      },2000);\n    }\n  ");
/*     */       }
/*     */       
/*     */ 
/*  86 */       out.write("\n}\n</script>\n<table width=\"");
/*  87 */       out.print(tableWidth);
/*  88 */       out.write("\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n  <tbody>\n    <tr class=\"tabBtmLine\">\n      <td nowrap=\"nowrap\" id=\"mytd\">");
/*  89 */       out.write("\n      \t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" id=\"InnerTab");
/*  90 */       out.print(randomnumber);
/*  91 */       out.write("\">\n          <tbody>\n            <tr>\n              <td width=\"17\"/>\n");
/*     */       
/*     */ 
/*  94 */       Hashtable globals = (Hashtable)application.getAttribute("globalconfig");
/*  95 */       String allowDownTimeSchedule = (String)globals.get("allowDownTimeSchedule");
/*  96 */       String allowOprViewAllDownTimeSchedule = (String)globals.get("allowOprViewAllDownTimeSchedule");
/*  97 */       String resourceid = request.getParameter("resourceid");
/*  98 */       String resourcename = request.getParameter("resourcename");
/*  99 */       boolean showDownTimeLink = false;
/* 100 */       if (resourcename == null) {
/* 101 */         showDownTimeLink = true;
/*     */       }
/* 103 */       String titles = request.getParameter("titles");
/* 104 */       String tooltips = request.getParameter("tooltip");
/* 105 */       String functions = request.getParameter("functions");
/* 106 */       String selectedtab = request.getParameter("selected");
/* 107 */       String errorcode = request.getParameter("errorcode");
/* 108 */       StringTokenizer tok = new StringTokenizer(titles, ",");
/* 109 */       StringTokenizer tok1 = new StringTokenizer(functions, ",");
/* 110 */       StringTokenizer tok2 = new StringTokenizer(tooltips, ",");
/* 111 */       String tooltip = null;
/* 112 */       while (tok.hasMoreTokens())
/*     */       {
/* 114 */         String title = tok.nextToken();
/* 115 */         if (title.equals("am.webclient.common.recentalerts.text")) {
/* 116 */           showDownTimeLink = false;
/*     */         }
/* 118 */         String functionName = tok1.nextToken();
/* 119 */         tooltip = tok2.nextToken();
/*     */         
/*     */ 
/* 122 */         if (com.adventnet.appmanager.util.Constants.isIt360)
/*     */         {
/* 124 */           if (request.getAttribute("isReqFromAdmin") != null)
/*     */           {
/* 126 */             String isReqFromAdmin = request.getAttribute("isReqFromAdmin").toString();
/* 127 */             if ((isReqFromAdmin.equals("true")) && (
/*     */             
/* 129 */               (title.equals("am.webclient.dasboard.performancetab.title")) || (title.equals("am.webclient.dasboard.availabilitytab.title")) || (title.equals("am.webclient.dashboard.topvm.name")) || (title.equals("am.webclient.dashboard.tophyperv.name")) || (title.equals("am.webclient.dashboard.topesx.name")) || (title.equals("am.webclient.dashboard.infrastructuretab.title")))) {
/*     */               continue;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 138 */         out.write("\n           <td><table title=\"");
/* 139 */         out.print(FormatUtil.getString(tooltip));
/* 140 */         out.write("\" id=\"");
/* 141 */         out.print(title);
/* 142 */         out.write("Tab\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\" >\n                  <tbody>\n                    <tr>\n                      <td ");
/* 143 */         if (!selectedtab.equals(title)) {
/* 144 */           out.write("class=\"tbUnselected_Left\"");
/*     */         }
/* 146 */         if (selectedtab.equals(title)) {
/* 147 */           out.write("class=\"tbSelected_Left\"");
/*     */         }
/* 149 */         out.write(" ><img width=\"1\" height=\"1\" alt=\"spacer\" src=\"/images/spacer.gif\"/></td>\n                      <td ");
/* 150 */         if (!selectedtab.equals(title)) {
/* 151 */           out.write("class=\"tbUnselected_Middle\"");
/*     */         }
/* 153 */         out.write(32);
/* 154 */         if (selectedtab.equals(title)) {
/* 155 */           out.write("class=\"tbSelected_Middle\"");
/*     */         }
/* 157 */         out.write(" onClick=\"SetTabStyle('");
/* 158 */         out.print(title);
/* 159 */         out.write("','InnerTab");
/* 160 */         out.print(randomnumber);
/* 161 */         out.write(39);
/* 162 */         out.write(41);
/* 163 */         out.write(59);
/* 164 */         out.print(functionName);
/* 165 */         out.write(40);
/* 166 */         out.write(39);
/* 167 */         out.print(resourceid);
/* 168 */         out.write(39);
/* 169 */         out.write(44);
/* 170 */         out.write(39);
/* 171 */         out.print(resourcename);
/* 172 */         out.write(39);
/* 173 */         out.write(44);
/* 174 */         out.write(39);
/* 175 */         out.print(errorcode);
/* 176 */         out.write("');resizeTabContent();\" style=\"padding-left:5px;padding-right:5px\">\n                      <span  ");
/* 177 */         if (!selectedtab.equals(title)) {
/* 178 */           out.write(" class=\"tabLink\"");
/*     */         }
/* 180 */         out.write(32);
/* 181 */         if (selectedtab.equals(title)) {
/* 182 */           out.write("class=\"tabLinkActive\"");
/*     */         }
/* 184 */         out.write(" id=\"A_");
/* 185 */         out.print(title);
/* 186 */         out.write("Tab\">");
/* 187 */         out.print(FormatUtil.getString(title));
/* 188 */         out.write("</span>\n                      </td>\n                      <td ");
/* 189 */         if (!selectedtab.equals(title)) {
/* 190 */           out.write("class=\"tbUnselected_Right\"");
/*     */         }
/* 192 */         out.write(32);
/* 193 */         if (selectedtab.equals(title)) {
/* 194 */           out.write("class=\"tbSelected_Right\"");
/*     */         }
/* 196 */         out.write("><img width=\"1\" height=\"1\" alt=\"spacer\" src=\"/images/spacer.gif\"/></td>\n                  \t\n                   </tr>\n                    \n                  </tbody>\n                </table>\n              </td>\n");
/*     */       }
/*     */       
/*     */ 
/* 200 */       out.write("\n           \n            </tr>\n          </tbody>\n      \t</table>\n      </td>\n       ");
/*     */       
/* 202 */       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 203 */       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 204 */       _jspx_th_logic_005fpresent_005f0.setParent(null);
/*     */       
/* 206 */       _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 207 */       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 208 */       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*     */         for (;;) {
/* 210 */           out.write("\n  \t\t\t\t\t");
/*     */           
/* 212 */           if (((allowDownTimeSchedule.equals("true")) || ("true".equals(allowOprViewAllDownTimeSchedule))) && (!FormatUtil.getString(tooltip).equals("Getting Started")) && (showDownTimeLink))
/*     */           {
/*     */ 
/* 215 */             out.write("\n \n  \t\t\t\t\t<td  class=\"bodytext\" align=\"right\" width=\"72%\" >\n  \t\t\t\t\t<span  style=\"padding-bottom:3px\">\t<a class=\"staticlinks\" href='javascript:void(0)' onClick=\"MM_openBrWindow('/downTimeScheduler.do?method=maintenanceTaskListView','DownTimeScheduler','width=800,height=600, scrollbars=yes,resizable=yes')\">");
/* 216 */             out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 217 */             out.write(" </a>\n \t\t\t\t\t</span> </td>\n\t\t\t\t  ");
/*     */           }
/*     */           
/*     */ 
/* 221 */           out.write("\n \t\t");
/* 222 */           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 223 */           if (evalDoAfterBody != 2)
/*     */             break;
/*     */         }
/*     */       }
/* 227 */       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 228 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*     */       }
/*     */       else {
/* 231 */         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 232 */         out.write("\n      <td align=\"right\">\n      <div id=\"loadingg\" style=\"display:none;\">\n      <img alt=\"Loading...\" src=\"/images/icon_cogwheel.gif\" border=\"0\"/>\n      </div>\n      </td>\n    </tr>\n  </tbody>\n</table>\n<input type=\"hidden\" id=\"tab_random_number\" value=\"");
/* 233 */         out.print(randomnumber);
/* 234 */         out.write("\"></input>\n<div id=\"loadingDiv\" align=\"center\" style=\"display:none;padding-top:20px;\"><img src=\"/images/loading.gif\"/><span class=\"bodytext\" style=\"position:relative; bottom:15px; left:10px;\">");
/* 235 */         out.print(FormatUtil.getString("am.webclient.ajax.loadingPage"));
/* 236 */         out.write("</span></div>\n");
/*     */       }
/* 238 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 239 */         out = _jspx_out;
/* 240 */         if ((out != null) && (out.getBufferSize() != 0))
/* 241 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 242 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 245 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\tabs_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */