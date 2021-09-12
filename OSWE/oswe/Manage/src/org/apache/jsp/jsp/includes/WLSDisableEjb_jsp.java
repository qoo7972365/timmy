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
/*     */ public final class WLSDisableEjb_jsp extends org.apache.jasper.runtime.HttpJspBase implements JspSourceDependent
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
/*  80 */       ArrayList ejbDisableList = new ArrayList();
/*  81 */       AMConnectionPool pool = AMConnectionPool.getInstance();
/*  82 */       ArrayList ejbEnableList = new ArrayList();
/*     */       
/*  84 */       String ejbEnableQuery = "select t1.RESOURCEID, DISPLAYNAME from AM_ManagedObject t1,AM_WLS_DISABLE_STATS t2 where t1.resourceid=t2.resourceid and t1.type='WEBLOGIC-server' and t2.ENABLEEJB=0";
/*  85 */       ResultSet ejbEnableSet = AMConnectionPool.executeQueryStmt(ejbEnableQuery);
/*     */       
/*  87 */       while (ejbEnableSet.next())
/*     */       {
/*  89 */         String monitorResourceId = String.valueOf(ejbEnableSet.getInt("RESOURCEID"));
/*  90 */         ejbEnableList.add(monitorResourceId);
/*     */       }
/*     */       
/*     */       try
/*     */       {
/*  95 */         if (ejbEnableSet != null) {
/*  96 */           ejbEnableSet.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 104 */       String ejbDisableQuery = "select t1.RESOURCEID, DISPLAYNAME from AM_ManagedObject t1,AM_WLS_DISABLE_STATS t2 where t1.resourceid=t2.resourceid and t1.type='WEBLOGIC-server' and t2.ENABLEEJB=1";
/*     */       
/* 106 */       ResultSet ejbDisableResultSet = null;
/*     */       try
/*     */       {
/* 109 */         ejbDisableResultSet = AMConnectionPool.executeQueryStmt(ejbDisableQuery);
/* 110 */         while (ejbDisableResultSet.next())
/*     */         {
/* 112 */           String monitorDisplayName = ejbDisableResultSet.getString("DISPLAYNAME");
/* 113 */           String monitorResourceId = String.valueOf(ejbDisableResultSet.getInt("RESOURCEID"));
/* 114 */           ejbDisableList.add(monitorResourceId);
/*     */         }
/*     */       }
/*     */       catch (Exception exception)
/*     */       {
/* 119 */         exception.printStackTrace();
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 124 */         if (ejbDisableResultSet != null) {
/* 125 */           ejbDisableResultSet.close();
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 129 */         e.printStackTrace();
/*     */       }
/*     */       
/* 132 */       out.write("\n\n\n<div id=\"similarmonitors\" style=\"display:block\">\n\t     ");
/* 133 */       if (((ejbEnableList != null) && (!ejbEnableList.isEmpty())) || ((ejbDisableList != null) && (!ejbDisableList.isEmpty())))
/*     */       {
/* 135 */         int ejbDisableSize = 8;
/* 136 */         int ejbEnableSize = 8;
/* 137 */         if ((ejbEnableList != null) && (ejbEnableList.size() > 8))
/* 138 */           ejbEnableSize = ejbEnableList.size();
/* 139 */         if ((ejbDisableList != null) && (ejbDisableList.size() > 0)) {
/* 140 */           ejbDisableSize = ejbDisableList.size();
/*     */         }
/*     */         
/* 143 */         out.write("\n        \t<table>\n                <tr>\n\n        \t        <td align=\"center\" class=\"bodytext\"> &nbsp; </td>\n                \t<td align=\"center\" class=\"bodytextbold\">");
/* 144 */         out.print(FormatUtil.getString("am.webclient.disableejbmon.txt"));
/* 145 */         out.write("</td>\n\t                <td align=\"center\" class=\"bodytext\"> &nbsp; </td>\n        \t        <td align=\"center\" class=\"bodytextbold\">");
/* 146 */         out.print(FormatUtil.getString("am.webclient.enableejbmon.txt"));
/* 147 */         out.write("</td>\n                \t<td align=\"center\" class=\"bodytext\"> &nbsp; </td>\n                </tr>\n                <tr>\n\t                <td width=\"5%\" align=\"center\" class=\"bodytext\"> &nbsp; </td>\n        \t        <td width=\"26%\" align=\"right\" class=\"bodytext\">\n\t\t<div id=\"divEjbDisable\" class=\"formtextarea\" style=\"OVERFLOW: auto;width: 300px;height: 130px\" onscroll=\"OnDivScroll(this);\"> \n\t\t\t\n\t\t\t<select name=\"wlsejb_disable\" style=\"width:750px\" size=\"");
/* 148 */         out.print(ejbEnableSize);
/* 149 */         out.write("\"  multiple  id=\"ejb_disableid\" onfocus=\"OnSelectFocus(this);\" >\n                \t");
/*     */         
/* 151 */         if ((ejbEnableList != null) && (!ejbEnableList.isEmpty()))
/*     */         {
/* 153 */           Properties monitorNameId = FaultUtil.getMonitorName(ejbEnableList, false, false);
/* 154 */           LinkedHashMap sortedSimilarMonitors = FaultUtil.sortPropertiesByValue(monitorNameId);
/* 155 */           Set monitorSet = sortedSimilarMonitors.keySet();
/* 156 */           Iterator monitorItr = monitorSet.iterator();
/* 157 */           while (monitorItr.hasNext())
/*     */           {
/*     */ 
/* 160 */             String monitorId = (String)monitorItr.next();
/*     */             
/* 162 */             String monitorName = (String)sortedSimilarMonitors.get(monitorId);
/*     */             
/* 164 */             out.write("\n\t\t                <option value='");
/* 165 */             out.print(monitorId);
/* 166 */             out.write("' onmouseover=\"ddrivetip(this,event,'");
/* 167 */             out.print(monitorName);
/* 168 */             out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"> ");
/* 169 */             out.print(monitorName);
/* 170 */             out.write(" </option>\n                \t");
/*     */           } }
/* 172 */         out.write("\n\t\t\t</select>\n\t\t\t</div>\n\t\t\t</td>\n\t\t        <td width=\"7%\" align=\"center\" class=\"bodytext\"> \n\t\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t      \t\t\t<tr>\n\t\t            \t\t<td align=\"center\" ><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddToRightAndSetScrollSize(wlsejb_disable,wlsejb_enable);\" value=\"&nbsp;&gt;&nbsp;\"></td>\n\t      \t\t\t</tr>\n              \t\t\t<tr>\n\t\t    \t\t       <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n\t      \t\t\t</tr>\n\t      \t\t\t<tr>\n\t\t            \t\t<td align=\"center\"><input name=\"AddButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddAllFrmCombo(wlsejb_disable,wlsejb_enable),fnRemoveAllFrmCombo(wlsejb_disable);\" value=\"&gt;&gt;\"></td>\n\t      \t\t\t</tr>\n              \t\t\t<tr>\n\t\t    \t\t       <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n\t      \t\t\t</tr>\n              \t\t\t<tr>\n\t\t\t\t       <td align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddToRightAndSetScrollSize(wlsejb_enable,wlsejb_disable)\" value=\"&nbsp;&lt;&nbsp;\"></td>\n");
/* 173 */         out.write("\t\t                </tr>\n              \t\t\t<tr>\n\t\t    \t\t       <td><img src=\"../images/spacer.gif\" height=\"8\" width=\"5\"></td>\n\t      \t\t\t</tr>\n              \t\t\t<tr>\n\t\t\t\t       <td align=\"center\"><input name=\"RemoveButton2\" type=\"button\" class=\"buttons\" onClick=\"javascript:fnAddAllFrmCombo(wlsejb_enable,wlsejb_disable),fnRemoveAllFrmCombo(wlsejb_enable);\" value=\"&lt;&lt;\"></td>\n\t\t                </tr>\n\t\t\t      </table>\n\t\t\t</td>\n\t\t         <td width=\"26%\" align=\"left\" class=\"bodytext\">\n\t\t\t<div id=\"divEjbEnable\" class=\"formtextarea\" style=\"OVERFLOW: auto;width: 300px;height: 130px\" onscroll=\"OnDivScroll(this);\">\n\t\t\t<select  name=\"wlsejb_enable\" style=\"width:750px\" size=\"");
/* 174 */         out.print(ejbDisableSize);
/* 175 */         out.write("\" multiple id=\"ejb_enableid\" onfocus=\"OnSelectFocus(this);\">\n\t\t\t");
/*     */         
/* 177 */         if ((ejbDisableList != null) && (ejbDisableList.size() > 0))
/*     */         {
/* 179 */           Properties commonThresholdMonitorNameId = FaultUtil.getMonitorName(ejbDisableList, false, false);
/* 180 */           LinkedHashMap sortedSelectedMonitors = FaultUtil.sortPropertiesByValue(commonThresholdMonitorNameId);
/* 181 */           Set monitorSet = sortedSelectedMonitors.keySet();
/* 182 */           Iterator thresholdMonitorItr = monitorSet.iterator();
/* 183 */           while (thresholdMonitorItr.hasNext())
/*     */           {
/* 185 */             String monitorId = (String)thresholdMonitorItr.next();
/* 186 */             String monitorName = (String)sortedSelectedMonitors.get(monitorId);
/*     */             
/* 188 */             out.write("\n\t\t\t\t <option value='");
/* 189 */             out.print(monitorId);
/* 190 */             out.write("' onmouseover=\"ddrivetip(this,event,'");
/* 191 */             out.print(monitorName);
/* 192 */             out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"> ");
/* 193 */             out.print(monitorName);
/* 194 */             out.write(" </option>\n\t\t\t");
/*     */           } }
/* 196 */         out.write("\n\t\t\t</select>\n\t\t\t</div>\n</td>\n\t                <td width=\"5%\" align=\"center\" class=\"bodytext\"> &nbsp; </td>\n\t\t</tr>\n\t        </table>\n                ");
/*     */       } else {
/* 198 */         out.write("\n\t\t<table width=\"98%\" border=\"0\" cellspacing=\"5\" cellpadding=\"5\" class=\"messagebox\" align=\"center\">\n                        <tr>\n                        <td width=\"94%\" class=\"message\" > ");
/* 199 */         out.print(FormatUtil.getString("am.webclient.nowlsmon.txt"));
/* 200 */         out.write("</td>\n                        </tr>\n                 </table>\n\t\t ");
/*     */       }
/* 202 */       out.write("\n</div> \n\n\n");
/*     */     } catch (Throwable t) {
/* 204 */       if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 205 */         out = _jspx_out;
/* 206 */         if ((out != null) && (out.getBufferSize() != 0))
/* 207 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 208 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*     */       }
/*     */     } finally {
/* 211 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*     */   {
/* 217 */     PageContext pageContext = _jspx_page_context;
/* 218 */     JspWriter out = _jspx_page_context.getOut();
/*     */     
/* 220 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 221 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 222 */     _jspx_th_c_005fout_005f0.setParent(null);
/*     */     
/* 224 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*     */     
/* 226 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 227 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 228 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 229 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 230 */       return true;
/*     */     }
/* 232 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 233 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\includes\WLSDisableEjb_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */