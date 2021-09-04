/*     */ package org.apache.jsp.jsp.includes;
/*     */ 
/*     */ import com.adventnet.appmanager.db.AMConnectionPool;
/*     */ import com.adventnet.appmanager.fault.FaultUtil;
/*     */ import com.adventnet.appmanager.util.FormatUtil;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import javax.el.ExpressionFactory;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.jsp.JspFactory;
/*     */ import javax.servlet.jsp.JspWriter;
/*     */ import javax.servlet.jsp.PageContext;
/*     */ import org.apache.jasper.runtime.JspSourceDependent;
/*     */ import org.apache.jasper.runtime.TagHandlerPool;
/*     */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*     */ 
/*     */ public final class WLSDisableWebapp_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
/*     */ {
/*  27 */   private static final JspFactory _jspxFactory = ;
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
/*  38 */     return _jspx_dependants;
/*     */   }
/*     */   
/*     */   public void _jspInit() {
/*  42 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/*  43 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/*  44 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*     */   }
/*     */   
/*     */   public void _jspDestroy() {
/*  48 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*     */   }
/*     */   
/*     */ 
/*     */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*     */     throws IOException, javax.servlet.ServletException
/*     */   {
/*  55 */     javax.servlet.http.HttpSession session = null;
/*     */     
/*     */ 
/*  58 */     JspWriter out = null;
/*  59 */     Object page = this;
/*  60 */     JspWriter _jspx_out = null;
/*  61 */     PageContext _jspx_page_context = null;
/*     */     
/*     */     try
/*     */     {
/*  65 */       response.setContentType("text/html;charset=UTF-8");
/*  66 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, null, true, 8192, true);
/*     */       
/*  68 */       _jspx_page_context = pageContext;
/*  69 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/*  70 */       ServletConfig config = pageContext.getServletConfig();
/*  71 */       session = pageContext.getSession();
/*  72 */       out = pageContext.getOut();
/*  73 */       _jspx_out = out;
/*     */       
/*  75 */       out.write("<!-- $Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/*  76 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*     */         return;
/*  78 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n");
/*     */       
/*  80 */       ArrayList commonThresholdMonitorsList = new ArrayList();
/*  81 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*  82 */       ArrayList similarMonitorsList = new ArrayList();
/*     */       
/*  84 */       String similarMonitorTypeQuery = "select t1.RESOURCEID, DISPLAYNAME from AM_ManagedObject t1,AM_WLS_DISABLE_STATS t2 where t1.resourceid=t2.resourceid and t1.type='WEBLOGIC-server' and t2.ENABLEWEBAPP=0";
/*  85 */       ResultSet similarMonitorSet = AMConnectionPool.executeQueryStmt(similarMonitorTypeQuery);
/*     */       
/*  87 */       while (similarMonitorSet.next())
/*     */       {
/*  89 */         String monitorResourceId = String.valueOf(similarMonitorSet.getInt("RESOURCEID"));
/*  90 */         similarMonitorsList.add(monitorResourceId);
/*     */       }
/*     */       
/*     */       try
/*     */       {
/*  95 */         if (similarMonitorSet != null) {
/*  96 */           similarMonitorSet.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 104 */       String commonThresholdMonitorsQuery = "select t1.RESOURCEID, DISPLAYNAME from AM_ManagedObject t1,AM_WLS_DISABLE_STATS t2 where t1.resourceid=t2.resourceid and t1.type='WEBLOGIC-server' and t2.ENABLEWEBAPP=1";
/*     */       
/* 106 */       ResultSet commonThresholdMonitorResultSet = null;
/*     */       try
/*     */       {
/* 109 */         commonThresholdMonitorResultSet = AMConnectionPool.executeQueryStmt(commonThresholdMonitorsQuery);
/* 110 */         while (commonThresholdMonitorResultSet.next())
/*     */         {
/* 112 */           String monitorDisplayName = commonThresholdMonitorResultSet.getString("DISPLAYNAME");
/* 113 */           String monitorResourceId = String.valueOf(commonThresholdMonitorResultSet.getInt("RESOURCEID"));
/* 114 */           commonThresholdMonitorsList.add(monitorResourceId);
/*     */         }
/*     */         
/*     */       }
/*     */       catch (Exception exception)
/*     */       {
/* 120 */         exception.printStackTrace();
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 125 */         if (commonThresholdMonitorResultSet != null) {
/* 126 */           commonThresholdMonitorResultSet.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 130 */         e.printStackTrace();
/*     */       }
/*     */       
/* 133 */       out.write("\n\n\n<div id=\"similarmonitors\" style=\"display:block\">\n\t     ");
/* 134 */       if (((similarMonitorsList != null) && (!similarMonitorsList.isEmpty())) || ((commonThresholdMonitorsList != null) && (!commonThresholdMonitorsList.isEmpty())))
/*     */       {
/* 136 */         int selectedMonSize = 8;
/* 137 */         int availableMonSize = 8;
/* 138 */         if ((similarMonitorsList != null) && (similarMonitorsList.size() > 8))
/* 139 */           availableMonSize = similarMonitorsList.size();
/* 140 */         if ((commonThresholdMonitorsList != null) && (commonThresholdMonitorsList.size() > 0)) {
/* 141 */           selectedMonSize = commonThresholdMonitorsList.size();
/*     */         }
/*     */         
/* 144 */         out.write("\n        \t<table>\n                <tr>\n\n        \t        <td align=\"center\" class=\"bodytext\"> &nbsp; </td>\n                \t<td align=\"center\" class=\"bodytextbold\">");
/* 145 */         out.print(FormatUtil.getString("am.webclient.disablewebappmon.txt"));
/* 146 */         out.write("</td>\n\t                <td align=\"center\" class=\"bodytext\"> &nbsp; </td>\n        \t        <td align=\"center\" class=\"bodytextbold\">");
/* 147 */         out.print(FormatUtil.getString("am.webclient.enablewebappmon.txt"));
/* 148 */         out.write("</td>\n                \t<td align=\"center\" class=\"bodytext\"> &nbsp; </td>\n                </tr>\n                <tr>\n\t                <td width=\"5%\" align=\"center\" class=\"bodytext\"> &nbsp; </td>\n        \t        <td width=\"26%\" align=\"right\" class=\"bodytext\">\n\t\t<div id=\"divDisableWebapp\" class=\"formtextarea\" style=\"OVERFLOW: auto;width: 300px;height: 130px\" onscroll=\"OnDivScroll(this);\"> \n\t\t\t\n\t\t\t<select name=\"wlswebapp_disable\" style=\"width:750px\" size=\"");
/* 149 */         out.print(availableMonSize);
/* 150 */         out.write("\"  multiple  id=\"webapp_disableid\" onfocus=\"OnSelectFocus(this);\" >\n                \t");
/*     */         
/* 152 */         if ((similarMonitorsList != null) && (!similarMonitorsList.isEmpty()))
/*     */         {
/* 154 */           Properties monitorNameId = FaultUtil.getMonitorName(similarMonitorsList, false, false);
/* 155 */           LinkedHashMap sortedSimilarMonitors = FaultUtil.sortPropertiesByValue(monitorNameId);
/* 156 */           Set monitorSet = sortedSimilarMonitors.keySet();
/* 157 */           Iterator monitorItr = monitorSet.iterator();
/* 158 */           while (monitorItr.hasNext())
/*     */           {
/*     */ 
/* 161 */             String monitorId = (String)monitorItr.next();
/*     */             
/* 163 */             String monitorName = (String)sortedSimilarMonitors.get(monitorId);
/*     */             
/* 165 */             out.write("\n\t\t                <option value='");
/* 166 */             out.print(monitorId);
/* 167 */             out.write("' onmouseover=\"ddrivetip(this,event,'");
/* 168 */             out.print(monitorName);
/* 169 */             out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"> ");
/* 170 */             out.print(monitorName);
/* 171 */             out.write(" </option>\n                \t");
/*     */           } }
/* 173 */         out.write("\n\t\t\t</select>\n\t\t\t</div>\n\t\t\t</td>\n\t\t        <td width=\"7%\" align=\"center\" class=\"bodytext\"> \n\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t      \t\t\t<tr>\n\t\t            \t\t<td align=\"center\" ><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddToRightAndSetScrollSize(wlswebapp_disable,wlswebapp_enable);\" value=\"&nbsp;&gt;&nbsp;\"></td>\n\t      \t\t\t</tr>\n              \t\t\t<tr>\n\t\t    \t\t       <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n\t      \t\t\t</tr>\n\t      \t\t\t<tr>\n\t\t            \t\t<td align=\"center\"><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddAllFrmCombo(wlswebapp_disable,wlswebapp_enable),fnRemoveAllFrmCombo(wlswebapp_disable);\" value=\"&gt;&gt;\"></td>\n\t      \t\t\t</tr>\n              \t\t\t<tr>\n\t\t    \t\t       <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n\t      \t\t\t</tr>\n              \t\t\t<tr>\n\t\t\t\t       <td align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddToRightAndSetScrollSize(wlswebapp_enable,wlswebapp_disable)\" value=\"&nbsp;&lt;&nbsp;\"></td>\n");
/* 174 */         out.write("\t\t                </tr>\n              \t\t\t<tr>\n\t\t    \t\t       <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n\t      \t\t\t</tr>\n              \t\t\t<tr>\n\t\t\t\t       <td align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddAllFrmCombo(wlswebapp_enable,wlswebapp_disable),fnRemoveAllFrmCombo(wlswebapp_enable);\" value=\"&lt;&lt;\"></td>\n\t\t                </tr>\n\t\t\t      </table>\n\t\t\t</td>\n\t\t         <td width=\"26%\" align=\"left\" class=\"bodytext\">\n\t\t\t<div id=\"divEnableWebapp\" class=\"formtextarea\" style=\"OVERFLOW: auto;width: 300px;height: 130px\" onscroll=\"OnDivScroll(this);\">\n\t\t\t<select  name=\"wlswebapp_enable\" style=\"width:750px\" size=\"");
/* 175 */         out.print(selectedMonSize);
/* 176 */         out.write("\" multiple id=\"webapp_enableid\" onfocus=\"OnSelectFocus(this);\">\n\t\t\t");
/*     */         
/* 178 */         if ((commonThresholdMonitorsList != null) && (commonThresholdMonitorsList.size() > 0))
/*     */         {
/* 180 */           Properties commonThresholdMonitorNameId = FaultUtil.getMonitorName(commonThresholdMonitorsList, false, false);
/* 181 */           LinkedHashMap sortedSelectedMonitors = FaultUtil.sortPropertiesByValue(commonThresholdMonitorNameId);
/* 182 */           Set monitorSet = sortedSelectedMonitors.keySet();
/* 183 */           Iterator thresholdMonitorItr = monitorSet.iterator();
/* 184 */           while (thresholdMonitorItr.hasNext())
/*     */           {
/* 186 */             String monitorId = (String)thresholdMonitorItr.next();
/* 187 */             String monitorName = (String)sortedSelectedMonitors.get(monitorId);
/*     */             
/* 189 */             out.write("\n\t\t\t\t <option value='");
/* 190 */             out.print(monitorId);
/* 191 */             out.write("' onmouseover=\"ddrivetip(this,event,'");
/* 192 */             out.print(monitorName);
/* 193 */             out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"> ");
/* 194 */             out.print(monitorName);
/* 195 */             out.write(" </option>\n\t\t\t");
/*     */           } }
/* 197 */         out.write("\n\t\t\t</select>\n\t\t\t</div>\n</td>\n\t                <td width=\"5%\" align=\"center\" class=\"bodytext\"> &nbsp; </td>\n\t\t</tr>\n\t        </table>\n                ");
/*     */       } else {
/* 199 */         out.write("\n\t\t<table width=\"98%\" border=\"0\" cellspacing=\"5\" cellpadding=\"5\" class=\"messagebox\" align=\"center\">\n                        <tr>\n                        <td width=\"94%\" class=\"message\" > ");
/* 200 */         out.print(FormatUtil.getString("am.webclient.nowlsmon.txt"));
/* 201 */         out.write("</td>\n                        </tr>\n                 </table>\n\t\t ");
/*     */       }
/* 203 */       out.write("\n</div> \n\n\n");
/*     */     } catch (Throwable t) {
/* 205 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 206 */         out = _jspx_out;
/* 207 */         if ((out != null) && (out.getBufferSize() != 0))
/* 208 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 209 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 212 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 218 */     PageContext pageContext = _jspx_page_context;
/* 219 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 221 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 222 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 223 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 225 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 227 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 228 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 229 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 230 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 231 */       return true;
/*     */     }
/* 233 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 234 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\WLSDisableWebapp_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */