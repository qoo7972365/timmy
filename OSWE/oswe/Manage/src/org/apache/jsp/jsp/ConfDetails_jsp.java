/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.db.RepairTables;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.fault.FaultUtil;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.confmonitor.ConfMonitorGraphBean;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import com.adventnet.awolf.tags.XYAreaChart;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParamTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class ConfDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   73 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   76 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   77 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   78 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   85 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   90 */     ArrayList list = null;
/*   91 */     StringBuffer sbf = new StringBuffer();
/*   92 */     ManagedApplication mo = new ManagedApplication();
/*   93 */     if (distinct)
/*      */     {
/*   95 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   99 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*  102 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*  104 */       ArrayList row = (ArrayList)list.get(i);
/*  105 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  106 */       if (distinct) {
/*  107 */         sbf.append(row.get(0));
/*      */       } else
/*  109 */         sbf.append(row.get(1));
/*  110 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  113 */     return sbf.toString(); }
/*      */   
/*  115 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  118 */     if (severity == null)
/*      */     {
/*  120 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  122 */     if (severity.equals("5"))
/*      */     {
/*  124 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  126 */     if (severity.equals("1"))
/*      */     {
/*  128 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  133 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  140 */     if (severity == null)
/*      */     {
/*  142 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  144 */     if (severity.equals("1"))
/*      */     {
/*  146 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  148 */     if (severity.equals("4"))
/*      */     {
/*  150 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  152 */     if (severity.equals("5"))
/*      */     {
/*  154 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  159 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  165 */     if (severity == null)
/*      */     {
/*  167 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  169 */     if (severity.equals("5"))
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  173 */     if (severity.equals("1"))
/*      */     {
/*  175 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  179 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  185 */     if (severity == null)
/*      */     {
/*  187 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  189 */     if (severity.equals("1"))
/*      */     {
/*  191 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  193 */     if (severity.equals("4"))
/*      */     {
/*  195 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  197 */     if (severity.equals("5"))
/*      */     {
/*  199 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  203 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  209 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  215 */     if (severity == 5)
/*      */     {
/*  217 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  219 */     if (severity == 1)
/*      */     {
/*  221 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  226 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  232 */     if (severity == null)
/*      */     {
/*  234 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  236 */     if (severity.equals("5"))
/*      */     {
/*  238 */       if (isAvailability) {
/*  239 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  242 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  245 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  247 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  249 */     if (severity.equals("1"))
/*      */     {
/*  251 */       if (isAvailability) {
/*  252 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  255 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  262 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  269 */     if (severity == null)
/*      */     {
/*  271 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  273 */     if (severity.equals("5"))
/*      */     {
/*  275 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  277 */     if (severity.equals("4"))
/*      */     {
/*  279 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  281 */     if (severity.equals("1"))
/*      */     {
/*  283 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  288 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  294 */     if (severity == null)
/*      */     {
/*  296 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  298 */     if (severity.equals("5"))
/*      */     {
/*  300 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  302 */     if (severity.equals("4"))
/*      */     {
/*  304 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  306 */     if (severity.equals("1"))
/*      */     {
/*  308 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  313 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  320 */     if (severity == null)
/*      */     {
/*  322 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  324 */     if (severity.equals("5"))
/*      */     {
/*  326 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  328 */     if (severity.equals("4"))
/*      */     {
/*  330 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  332 */     if (severity.equals("1"))
/*      */     {
/*  334 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  339 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  347 */     StringBuffer out = new StringBuffer();
/*  348 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  349 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  350 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  351 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  352 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  353 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  354 */     out.append("</tr>");
/*  355 */     out.append("</form></table>");
/*  356 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  363 */     if (val == null)
/*      */     {
/*  365 */       return "-";
/*      */     }
/*      */     
/*  368 */     String ret = FormatUtil.formatNumber(val);
/*  369 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  370 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  373 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  377 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  385 */     StringBuffer out = new StringBuffer();
/*  386 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  387 */     out.append("<tr>");
/*  388 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  390 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  392 */     out.append("</tr>");
/*  393 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  397 */       if (j % 2 == 0)
/*      */       {
/*  399 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  403 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  406 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  408 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  411 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  415 */       out.append("</tr>");
/*      */     }
/*  417 */     out.append("</table>");
/*  418 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  419 */     out.append("<tr>");
/*  420 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  421 */     out.append("</tr>");
/*  422 */     out.append("</table>");
/*  423 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  429 */     StringBuffer out = new StringBuffer();
/*  430 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  431 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  432 */     out.append("<tr>");
/*  433 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  434 */     out.append("<tr>");
/*  435 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  436 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  437 */     out.append("</tr>");
/*  438 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  441 */       out.append("<tr>");
/*  442 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  443 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  444 */       out.append("</tr>");
/*      */     }
/*      */     
/*  447 */     out.append("</table>");
/*  448 */     out.append("</table>");
/*  449 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  454 */     if (severity.equals("0"))
/*      */     {
/*  456 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  460 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  467 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session)
/*      */   {
/*  480 */     StringBuffer out = new StringBuffer();
/*  481 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  482 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  484 */       out.append("<tr>");
/*  485 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  486 */       out.append("</tr>");
/*      */       
/*      */ 
/*  489 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  491 */         String borderclass = "";
/*      */         
/*      */ 
/*  494 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  496 */         out.append("<tr>");
/*      */         
/*  498 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  499 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  500 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  506 */     out.append("</table><br>");
/*  507 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  508 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  510 */       List sLinks = secondLevelOfLinks[0];
/*  511 */       List sText = secondLevelOfLinks[1];
/*  512 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  515 */         out.append("<tr>");
/*  516 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  517 */         out.append("</tr>");
/*  518 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  520 */           String borderclass = "";
/*      */           
/*      */ 
/*  523 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  525 */           out.append("<tr>");
/*      */           
/*  527 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  528 */           if (sLinks.get(i).toString().length() == 0) {
/*  529 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  532 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  534 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  538 */     out.append("</table>");
/*  539 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  546 */     StringBuffer out = new StringBuffer();
/*  547 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  548 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  550 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  552 */         out.append("<tr>");
/*  553 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  554 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  558 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  560 */           String borderclass = "";
/*      */           
/*      */ 
/*  563 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  565 */           out.append("<tr>");
/*      */           
/*  567 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  568 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  569 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  572 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  575 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  580 */     out.append("</table><br>");
/*  581 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  582 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  584 */       List sLinks = secondLevelOfLinks[0];
/*  585 */       List sText = secondLevelOfLinks[1];
/*  586 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  589 */         out.append("<tr>");
/*  590 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  591 */         out.append("</tr>");
/*  592 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  594 */           String borderclass = "";
/*      */           
/*      */ 
/*  597 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  599 */           out.append("<tr>");
/*      */           
/*  601 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  602 */           if (sLinks.get(i).toString().length() == 0) {
/*  603 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  606 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  608 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  612 */     out.append("</table>");
/*  613 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSeverityClass(int status)
/*      */   {
/*  626 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  629 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  632 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  635 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  638 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  641 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  644 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  647 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  655 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  660 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  665 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  670 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  675 */     if (val != null)
/*      */     {
/*  677 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  681 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  686 */     if (val == null) {
/*  687 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  691 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  696 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  702 */     if (val != null)
/*      */     {
/*  704 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  708 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  714 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  719 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  723 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  728 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  733 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  738 */     String hostaddress = "";
/*  739 */     String ip = request.getHeader("x-forwarded-for");
/*  740 */     if (ip == null)
/*  741 */       ip = request.getRemoteAddr();
/*  742 */     InetAddress add = null;
/*  743 */     if (ip.equals("127.0.0.1")) {
/*  744 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  748 */       add = InetAddress.getByName(ip);
/*      */     }
/*  750 */     hostaddress = add.getHostName();
/*  751 */     if (hostaddress.indexOf('.') != -1) {
/*  752 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  753 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  757 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  762 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  768 */     if (severity == null)
/*      */     {
/*  770 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  772 */     if (severity.equals("5"))
/*      */     {
/*  774 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  776 */     if (severity.equals("1"))
/*      */     {
/*  778 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  783 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  788 */     ResultSet set = null;
/*  789 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  790 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  792 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  793 */       if (set.next()) { String str1;
/*  794 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  795 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  798 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  803 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  806 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  808 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  812 */     StringBuffer rca = new StringBuffer();
/*  813 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  814 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  817 */     int rcalength = key.length();
/*  818 */     String split = "6. ";
/*  819 */     int splitPresent = key.indexOf(split);
/*  820 */     String div1 = "";String div2 = "";
/*  821 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  823 */       if (rcalength > 180) {
/*  824 */         rca.append("<span class=\"rca-critical-text\">");
/*  825 */         getRCATrimmedText(key, rca);
/*  826 */         rca.append("</span>");
/*      */       } else {
/*  828 */         rca.append("<span class=\"rca-critical-text\">");
/*  829 */         rca.append(key);
/*  830 */         rca.append("</span>");
/*      */       }
/*  832 */       return rca.toString();
/*      */     }
/*  834 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  835 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  836 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  837 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  838 */     getRCATrimmedText(div1, rca);
/*  839 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  842 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  843 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  844 */     getRCATrimmedText(div2, rca);
/*  845 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  847 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  852 */     String[] st = msg.split("<br>");
/*  853 */     for (int i = 0; i < st.length; i++) {
/*  854 */       String s = st[i];
/*  855 */       if (s.length() > 180) {
/*  856 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  858 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  862 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  863 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  865 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  869 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  870 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  871 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  874 */       if (key == null) {
/*  875 */         return ret;
/*      */       }
/*      */       
/*  878 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  879 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  882 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  883 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  884 */       set = AMConnectionPool.executeQueryStmt(query);
/*  885 */       if (set.next())
/*      */       {
/*  887 */         String helpLink = set.getString("LINK");
/*  888 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  891 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  897 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  916 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  907 */         if (set != null) {
/*  908 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */       }
/*      */       catch (Exception nullexc) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Properties getStatus(List entitylist)
/*      */   {
/*  922 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  923 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  925 */       String entityStr = (String)keys.nextElement();
/*  926 */       String mmessage = temp.getProperty(entityStr);
/*  927 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  928 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  930 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  936 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  937 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  939 */       String entityStr = (String)keys.nextElement();
/*  940 */       String mmessage = temp.getProperty(entityStr);
/*  941 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  942 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  944 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  949 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  959 */     String des = new String();
/*  960 */     while (str.indexOf(find) != -1) {
/*  961 */       des = des + str.substring(0, str.indexOf(find));
/*  962 */       des = des + replace;
/*  963 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  965 */     des = des + str;
/*  966 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  973 */       if (alert == null)
/*      */       {
/*  975 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  977 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  979 */         return "&nbsp;";
/*      */       }
/*      */       
/*  982 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  984 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  987 */       int rcalength = test.length();
/*  988 */       if (rcalength < 300)
/*      */       {
/*  990 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  994 */       StringBuffer out = new StringBuffer();
/*  995 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  996 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  997 */       out.append("</div>");
/*  998 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  999 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/* 1000 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1005 */       ex.printStackTrace();
/*      */     }
/* 1007 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1013 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1018 */     ArrayList attribIDs = new ArrayList();
/* 1019 */     ArrayList resIDs = new ArrayList();
/* 1020 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1022 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1024 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1026 */       String resourceid = "";
/* 1027 */       String resourceType = "";
/* 1028 */       if (type == 2) {
/* 1029 */         resourceid = (String)row.get(0);
/* 1030 */         resourceType = (String)row.get(3);
/*      */       }
/* 1032 */       else if (type == 3) {
/* 1033 */         resourceid = (String)row.get(0);
/* 1034 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1037 */         resourceid = (String)row.get(6);
/* 1038 */         resourceType = (String)row.get(7);
/*      */       }
/* 1040 */       resIDs.add(resourceid);
/* 1041 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1042 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1044 */       String healthentity = null;
/* 1045 */       String availentity = null;
/* 1046 */       if (healthid != null) {
/* 1047 */         healthentity = resourceid + "_" + healthid;
/* 1048 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1051 */       if (availid != null) {
/* 1052 */         availentity = resourceid + "_" + availid;
/* 1053 */         entitylist.add(availentity);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1067 */     Properties alert = getStatus(entitylist);
/* 1068 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1073 */     int size = monitorList.size();
/*      */     
/* 1075 */     String[] severity = new String[size];
/*      */     
/* 1077 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1079 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1080 */       String resourceName1 = (String)row1.get(7);
/* 1081 */       String resourceid1 = (String)row1.get(6);
/* 1082 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1083 */       if (severity[j] == null)
/*      */       {
/* 1085 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1089 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1091 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1093 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1096 */         if (sev > 0) {
/* 1097 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1098 */           monitorList.set(k, monitorList.get(j));
/* 1099 */           monitorList.set(j, t);
/* 1100 */           String temp = severity[k];
/* 1101 */           severity[k] = severity[j];
/* 1102 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1108 */     int z = 0;
/* 1109 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1112 */       int i = 0;
/* 1113 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1116 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1120 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1124 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1126 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1129 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1133 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1136 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1137 */       String resourceName1 = (String)row1.get(7);
/* 1138 */       String resourceid1 = (String)row1.get(6);
/* 1139 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1140 */       if (hseverity[j] == null)
/*      */       {
/* 1142 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1147 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1149 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1152 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1155 */         if (hsev > 0) {
/* 1156 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1157 */           monitorList.set(k, monitorList.get(j));
/* 1158 */           monitorList.set(j, t);
/* 1159 */           String temp1 = hseverity[k];
/* 1160 */           hseverity[k] = hseverity[j];
/* 1161 */           hseverity[j] = temp1;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getAllChildNodestoDisplay(ArrayList singlechilmos, String resIdTOCheck, String currentresourceidtree, Hashtable childmos, Hashtable availhealth, int level, HttpServletRequest request, HashMap extDeviceMap, HashMap site24x7List)
/*      */   {
/* 1173 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1174 */     boolean forInventory = false;
/* 1175 */     String trdisplay = "none";
/* 1176 */     String plusstyle = "inline";
/* 1177 */     String minusstyle = "none";
/* 1178 */     String haidTopLevel = "";
/* 1179 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1181 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1183 */         haidTopLevel = request.getParameter("haid");
/* 1184 */         forInventory = true;
/* 1185 */         trdisplay = "table-row;";
/* 1186 */         plusstyle = "none";
/* 1187 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1194 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1197 */     ArrayList listtoreturn = new ArrayList();
/* 1198 */     StringBuffer toreturn = new StringBuffer();
/* 1199 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1200 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1201 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1203 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1205 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1206 */       String childresid = (String)singlerow.get(0);
/* 1207 */       String childresname = (String)singlerow.get(1);
/* 1208 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1209 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1210 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1211 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1212 */       String unmanagestatus = (String)singlerow.get(5);
/* 1213 */       String actionstatus = (String)singlerow.get(6);
/* 1214 */       String linkclass = "monitorgp-links";
/* 1215 */       String titleforres = childresname;
/* 1216 */       String titilechildresname = childresname;
/* 1217 */       String childimg = "/images/trcont.png";
/* 1218 */       String flag = "enable";
/* 1219 */       String dcstarted = (String)singlerow.get(8);
/* 1220 */       String configMonitor = "";
/* 1221 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1222 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1224 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1226 */       if (singlerow.get(7) != null)
/*      */       {
/* 1228 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1230 */       String haiGroupType = "0";
/* 1231 */       if ("HAI".equals(childtype))
/*      */       {
/* 1233 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1235 */       childimg = "/images/trend.png";
/* 1236 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1237 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1238 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1240 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1242 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1244 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1245 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1248 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1250 */         linkclass = "disabledtext";
/* 1251 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1253 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1254 */       String availmouseover = "";
/* 1255 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1257 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1259 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1260 */       String healthmouseover = "";
/* 1261 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1263 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1266 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1267 */       int spacing = 0;
/* 1268 */       if (level >= 1)
/*      */       {
/* 1270 */         spacing = 40 * level;
/*      */       }
/* 1272 */       if (childtype.equals("HAI"))
/*      */       {
/* 1274 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1275 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1276 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1278 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1279 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1280 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1281 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1282 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1283 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1284 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1285 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1286 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1287 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1288 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1290 */         if (!forInventory)
/*      */         {
/* 1292 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1295 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1297 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1299 */           actions = editlink + actions;
/*      */         }
/* 1301 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1303 */           actions = actions + associatelink;
/*      */         }
/* 1305 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1306 */         String arrowimg = "";
/* 1307 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1309 */           actions = "";
/* 1310 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1311 */           checkbox = "";
/* 1312 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1314 */         if (isIt360)
/*      */         {
/* 1316 */           actionimg = "";
/* 1317 */           actions = "";
/* 1318 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1319 */           checkbox = "";
/*      */         }
/*      */         
/* 1322 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1324 */           actions = "";
/*      */         }
/* 1326 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1328 */           checkbox = "";
/*      */         }
/*      */         
/* 1331 */         String resourcelink = "";
/*      */         
/* 1333 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1335 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1339 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1342 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1343 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1344 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1345 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1346 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1347 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1348 */         if (!isIt360)
/*      */         {
/* 1350 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1354 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1357 */         toreturn.append("</tr>");
/* 1358 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1360 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1361 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1365 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1366 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1369 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1373 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1375 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1376 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1377 */             toreturn.append(assocMessage);
/* 1378 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1379 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1380 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1381 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1387 */         String resourcelink = null;
/* 1388 */         boolean hideEditLink = false;
/* 1389 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1391 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1392 */           hideEditLink = true;
/* 1393 */           if (isIt360)
/*      */           {
/* 1395 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1399 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1401 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1403 */           hideEditLink = true;
/* 1404 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1405 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1410 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1413 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1414 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1415 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1416 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1417 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1418 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1419 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1420 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1421 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1422 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1423 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1424 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1425 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1427 */         if (hideEditLink)
/*      */         {
/* 1429 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1431 */         if (!forInventory)
/*      */         {
/* 1433 */           removefromgroup = "";
/*      */         }
/* 1435 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1436 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1437 */           actions = actions + configcustomfields;
/*      */         }
/* 1439 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1441 */           actions = editlink + actions;
/*      */         }
/* 1443 */         String managedLink = "";
/* 1444 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1446 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1447 */           actions = "";
/* 1448 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1449 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1452 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1454 */           checkbox = "";
/*      */         }
/*      */         
/* 1457 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1459 */           actions = "";
/*      */         }
/* 1461 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1462 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1463 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1464 */         if (isIt360)
/*      */         {
/* 1466 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1470 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1472 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1473 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1474 */         if (!isIt360)
/*      */         {
/* 1476 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1480 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1482 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1485 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1492 */       StringBuilder toreturn = new StringBuilder();
/* 1493 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1494 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1495 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1496 */       String title = "";
/* 1497 */       message = EnterpriseUtil.decodeString(message);
/* 1498 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1499 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1500 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1502 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1504 */       else if ("5".equals(severity))
/*      */       {
/* 1506 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1510 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1512 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1513 */       toreturn.append(v);
/*      */       
/* 1515 */       toreturn.append(link);
/* 1516 */       if (severity == null)
/*      */       {
/* 1518 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1520 */       else if (severity.equals("5"))
/*      */       {
/* 1522 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1524 */       else if (severity.equals("4"))
/*      */       {
/* 1526 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1528 */       else if (severity.equals("1"))
/*      */       {
/* 1530 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1535 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1537 */       toreturn.append("</a>");
/* 1538 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1542 */       ex.printStackTrace();
/*      */     }
/* 1544 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1551 */       StringBuilder toreturn = new StringBuilder();
/* 1552 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1553 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1554 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1555 */       if (message == null)
/*      */       {
/* 1557 */         message = "";
/*      */       }
/*      */       
/* 1560 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1561 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1563 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1564 */       toreturn.append(v);
/*      */       
/* 1566 */       toreturn.append(link);
/*      */       
/* 1568 */       if (severity == null)
/*      */       {
/* 1570 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1572 */       else if (severity.equals("5"))
/*      */       {
/* 1574 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1576 */       else if (severity.equals("1"))
/*      */       {
/* 1578 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1583 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1585 */       toreturn.append("</a>");
/* 1586 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1592 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1595 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1596 */     if (invokeActions != null) {
/* 1597 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1598 */       while (iterator.hasNext()) {
/* 1599 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1600 */         if (actionmap.containsKey(actionid)) {
/* 1601 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1606 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1610 */     String actionLink = "";
/* 1611 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1612 */     String query = "";
/* 1613 */     ResultSet rs = null;
/* 1614 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1615 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1616 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1617 */       actionLink = "method=" + methodName;
/*      */     }
/* 1619 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1620 */       actionLink = methodName;
/*      */     }
/* 1622 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1623 */     Iterator itr = methodarglist.iterator();
/* 1624 */     boolean isfirstparam = true;
/* 1625 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1626 */     while (itr.hasNext()) {
/* 1627 */       HashMap argmap = (HashMap)itr.next();
/* 1628 */       String argtype = (String)argmap.get("TYPE");
/* 1629 */       String argname = (String)argmap.get("IDENTITY");
/* 1630 */       String paramname = (String)argmap.get("PARAMETER");
/* 1631 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1632 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1633 */         isfirstparam = false;
/* 1634 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1636 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1640 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1644 */         actionLink = actionLink + "&";
/*      */       }
/* 1646 */       String paramValue = null;
/* 1647 */       String tempargname = argname;
/* 1648 */       if (commonValues.getProperty(tempargname) != null) {
/* 1649 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1652 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1653 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1654 */           if (dbType.equals("mysql")) {
/* 1655 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1658 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1660 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1662 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1663 */             if (rs.next()) {
/* 1664 */               paramValue = rs.getString("VALUE");
/* 1665 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1669 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1673 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1676 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1681 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1682 */           paramValue = rowId;
/*      */         }
/* 1684 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1685 */           paramValue = managedObjectName;
/*      */         }
/* 1687 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1688 */           paramValue = resID;
/*      */         }
/* 1690 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1691 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1694 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1696 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1697 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1698 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1700 */     return actionLink;
/*      */   }
/*      */   
/* 1703 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1704 */     String dependentAttribute = null;
/* 1705 */     String align = "left";
/*      */     
/* 1707 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1708 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1709 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1710 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1711 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1712 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1713 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1714 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1715 */       align = "center";
/*      */     }
/*      */     
/* 1718 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1719 */     String actualdata = "";
/*      */     
/* 1721 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1722 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1723 */         actualdata = availValue;
/*      */       }
/* 1725 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1726 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1730 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1731 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1734 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1740 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1741 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1742 */       toreturn.append("<table>");
/* 1743 */       toreturn.append("<tr>");
/* 1744 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1745 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1746 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1747 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1748 */         String toolTip = "";
/* 1749 */         String hideClass = "";
/* 1750 */         String textStyle = "";
/* 1751 */         boolean isreferenced = true;
/* 1752 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1753 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1754 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1755 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1757 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1758 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1759 */           while (valueList.hasMoreTokens()) {
/* 1760 */             String dependentVal = valueList.nextToken();
/* 1761 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1762 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1763 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1765 */               toolTip = "";
/* 1766 */               hideClass = "";
/* 1767 */               isreferenced = false;
/* 1768 */               textStyle = "disabledtext";
/* 1769 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1773 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1774 */           toolTip = "";
/* 1775 */           hideClass = "";
/* 1776 */           isreferenced = false;
/* 1777 */           textStyle = "disabledtext";
/* 1778 */           if (dependentImageMap != null) {
/* 1779 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1780 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1783 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1787 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1788 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1789 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1790 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1791 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1792 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1794 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1795 */           if (isreferenced) {
/* 1796 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1800 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1801 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1802 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1803 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1804 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1805 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1807 */           toreturn.append("</span>");
/* 1808 */           toreturn.append("</a>");
/* 1809 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1812 */       toreturn.append("</tr>");
/* 1813 */       toreturn.append("</table>");
/* 1814 */       toreturn.append("</td>");
/*      */     } else {
/* 1816 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1819 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1823 */     String colTime = null;
/* 1824 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1825 */     if ((rows != null) && (rows.size() > 0)) {
/* 1826 */       Iterator<String> itr = rows.iterator();
/* 1827 */       String maxColQuery = "";
/* 1828 */       for (;;) { if (itr.hasNext()) {
/* 1829 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1830 */           ResultSet maxCol = null;
/*      */           try {
/* 1832 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1833 */             while (maxCol.next()) {
/* 1834 */               if (colTime == null) {
/* 1835 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1838 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1847 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1849 */               if (maxCol != null)
/* 1850 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1852 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1847 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1849 */               if (maxCol != null)
/* 1850 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1852 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1857 */     return colTime;
/*      */   }
/*      */   
/* 1860 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1861 */     tablename = null;
/* 1862 */     ResultSet rsTable = null;
/* 1863 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1865 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1866 */       while (rsTable.next()) {
/* 1867 */         tablename = rsTable.getString("DATATABLE");
/* 1868 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1869 */           tablename = "AM_Script_Numeric_Data_" + baseid;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1882 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1873 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1876 */         if (rsTable != null)
/* 1877 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1879 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1885 */     String argsList = "";
/* 1886 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1888 */       if (showArgsMap.get(row) != null) {
/* 1889 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1890 */         if (showArgslist != null) {
/* 1891 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1892 */             if (argsList.trim().equals("")) {
/* 1893 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1896 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1903 */       e.printStackTrace();
/* 1904 */       return "";
/*      */     }
/* 1906 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1911 */     String argsList = "";
/* 1912 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1915 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1917 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1918 */         if (hideArgsList != null)
/*      */         {
/* 1920 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1922 */             if (argsList.trim().equals(""))
/*      */             {
/* 1924 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1928 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1936 */       ex.printStackTrace();
/*      */     }
/* 1938 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1942 */     StringBuilder toreturn = new StringBuilder();
/* 1943 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1950 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1951 */       Iterator itr = tActionList.iterator();
/* 1952 */       while (itr.hasNext()) {
/* 1953 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1954 */         String confirmmsg = "";
/* 1955 */         String link = "";
/* 1956 */         String isJSP = "NO";
/* 1957 */         HashMap tactionMap = (HashMap)itr.next();
/* 1958 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1959 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1960 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1961 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1962 */           (actionmap.containsKey(actionId))) {
/* 1963 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1964 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1965 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1966 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1967 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1969 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1975 */           if (isTableAction) {
/* 1976 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1979 */             tableName = "Link";
/* 1980 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1981 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1982 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1983 */             toreturn.append("</a></td>");
/*      */           }
/* 1985 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1986 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1987 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1988 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1994 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 2000 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 2002 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 2003 */       Properties prop = (Properties)node.getUserObject();
/* 2004 */       String mgID = prop.getProperty("label");
/* 2005 */       String mgName = prop.getProperty("value");
/* 2006 */       String isParent = prop.getProperty("isParent");
/* 2007 */       int mgIDint = Integer.parseInt(mgID);
/* 2008 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2010 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2012 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2013 */       if (node.getChildCount() > 0)
/*      */       {
/* 2015 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2017 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2019 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2021 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2025 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2030 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2032 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2034 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2036 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2040 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2043 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2044 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2046 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2050 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2052 */       if (node.getChildCount() > 0)
/*      */       {
/* 2054 */         builder.append("<UL>");
/* 2055 */         printMGTree(node, builder);
/* 2056 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2061 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2062 */     StringBuffer toReturn = new StringBuffer();
/* 2063 */     String table = "-";
/*      */     try {
/* 2065 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2066 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2067 */       float total = 0.0F;
/* 2068 */       while (it.hasNext()) {
/* 2069 */         String attName = (String)it.next();
/* 2070 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2071 */         boolean roundOffData = false;
/* 2072 */         if ((data != null) && (!data.equals(""))) {
/* 2073 */           if (data.indexOf(",") != -1) {
/* 2074 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2077 */             float value = Float.parseFloat(data);
/* 2078 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2081 */             total += value;
/* 2082 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2085 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2090 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2091 */       while (attVsWidthList.hasNext()) {
/* 2092 */         String attName = (String)attVsWidthList.next();
/* 2093 */         String data = (String)attVsWidthProps.get(attName);
/* 2094 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2095 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2096 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2097 */         String className = (String)graphDetails.get("ClassName");
/* 2098 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2099 */         if (percentage < 1.0F)
/*      */         {
/* 2101 */           data = percentage + "";
/*      */         }
/* 2103 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2105 */       if (toReturn.length() > 0) {
/* 2106 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2110 */       e.printStackTrace();
/*      */     }
/* 2112 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2118 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2119 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2120 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2121 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2122 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2123 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2124 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2125 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2126 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2129 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2130 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2131 */       splitvalues[0] = multiplecondition.toString();
/* 2132 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2135 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2140 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2141 */     if (thresholdType != 3) {
/* 2142 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2143 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2144 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2145 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2146 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2147 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2149 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2150 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2151 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2152 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2153 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2154 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2156 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2157 */     if (updateSelected != null) {
/* 2158 */       updateSelected[0] = "selected";
/*      */     }
/* 2160 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2165 */       StringBuffer toreturn = new StringBuffer("");
/* 2166 */       if (commaSeparatedMsgId != null) {
/* 2167 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2168 */         int count = 0;
/* 2169 */         while (msgids.hasMoreTokens()) {
/* 2170 */           String id = msgids.nextToken();
/* 2171 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2172 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2173 */           count++;
/* 2174 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2175 */             if (toreturn.length() == 0) {
/* 2176 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2178 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2179 */             if (!image.trim().equals("")) {
/* 2180 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2182 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2183 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2186 */         if (toreturn.length() > 0) {
/* 2187 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2191 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2194 */       e.printStackTrace(); }
/* 2195 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2201 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2207 */   private static Map<String, Long> _jspx_dependants = new HashMap(10);
/* 2208 */   static { _jspx_dependants.put("/jsp/includes/jsInclude.jspf", Long.valueOf(1473429417000L));
/* 2209 */     _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2210 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2211 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2212 */     _jspx_dependants.put("/jsp/includes/cssInclude.jspf", Long.valueOf(1473429417000L));
/* 2213 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2214 */     _jspx_dependants.put("/jsp/includes/CustomLeftPage.jspf", Long.valueOf(1473429417000L));
/* 2215 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2216 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/* 2217 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparam;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fchartTitle_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer_005fchartTitle_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005fnodatamessage_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular_005fchartTitle_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005fnodatamessage_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2254 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2258 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._005fjspx_005ftagPool_005ffmt_005fparam = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2271 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2275 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2276 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2277 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2278 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2279 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2280 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2281 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2282 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fchartTitle_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2283 */     this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer_005fchartTitle_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2284 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005fnodatamessage_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular_005fchartTitle_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2285 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005fnodatamessage_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2286 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2287 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2288 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2292 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2293 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2294 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2295 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2296 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2297 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2298 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2299 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2300 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2301 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2302 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.release();
/* 2303 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.release();
/* 2304 */     this._005fjspx_005ftagPool_005ffmt_005fparam.release();
/* 2305 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
/* 2306 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2307 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2308 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2309 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2310 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2311 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2312 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2313 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2314 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2315 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/* 2316 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fchartTitle_005fnobody.release();
/* 2317 */     this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer_005fchartTitle_005fnobody.release();
/* 2318 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005fnodatamessage_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular_005fchartTitle_005fnobody.release();
/* 2319 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005fnodatamessage_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2320 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/*      */   }
/*      */   
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/*      */     ;
/* 2327 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2330 */     JspWriter out = null;
/* 2331 */     Object page = this;
/* 2332 */     JspWriter _jspx_out = null;
/* 2333 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2337 */       response.setContentType("text/html;charset=UTF-8");
/* 2338 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2340 */       _jspx_page_context = pageContext;
/* 2341 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2342 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2343 */       session = pageContext.getSession();
/* 2344 */       out = pageContext.getOut();
/* 2345 */       _jspx_out = out;
/*      */       
/* 2347 */       out.write("<!DOCTYPE html>\n");
/* 2348 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2349 */       out.write(10);
/* 2350 */       request.setAttribute("HelpKey", request.getParameter("type") + " Details");
/* 2351 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2352 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2354 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2355 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2356 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2358 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2360 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2362 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2364 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2365 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2366 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2367 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2370 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2371 */         String available = null;
/* 2372 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2373 */         out.write(10);
/*      */         
/* 2375 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2376 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2377 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2379 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2381 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2383 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2385 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2386 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2387 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2388 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2391 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2392 */           String unavailable = null;
/* 2393 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2394 */           out.write(10);
/*      */           
/* 2396 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2397 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2398 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2400 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2402 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2404 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2406 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2407 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2408 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2409 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2412 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2413 */             String unmanaged = null;
/* 2414 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2415 */             out.write(10);
/*      */             
/* 2417 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2418 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2419 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2421 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2423 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2425 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2427 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2428 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2429 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2430 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2433 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2434 */               String scheduled = null;
/* 2435 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2436 */               out.write(10);
/*      */               
/* 2438 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2439 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2440 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2442 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2444 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2446 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2448 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2449 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2450 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2451 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2454 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2455 */                 String critical = null;
/* 2456 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2457 */                 out.write(10);
/*      */                 
/* 2459 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2460 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2461 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2463 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2465 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2467 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2469 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2470 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2471 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2472 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2475 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2476 */                   String clear = null;
/* 2477 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2478 */                   out.write(10);
/*      */                   
/* 2480 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2481 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2482 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2484 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2486 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2488 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2490 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2491 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2492 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2493 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2496 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2497 */                     String warning = null;
/* 2498 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2499 */                     out.write(10);
/* 2500 */                     out.write(10);
/*      */                     
/* 2502 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2503 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2505 */                     out.write(10);
/* 2506 */                     out.write(10);
/* 2507 */                     out.write(10);
/* 2508 */                     out.write(10);
/* 2509 */                     out.write(10);
/* 2510 */                     out.write(10);
/* 2511 */                     ConfMonitorGraphBean confgraph = null;
/* 2512 */                     confgraph = (ConfMonitorGraphBean)_jspx_page_context.getAttribute("confgraph", 2);
/* 2513 */                     if (confgraph == null) {
/* 2514 */                       confgraph = new ConfMonitorGraphBean();
/* 2515 */                       _jspx_page_context.setAttribute("confgraph", confgraph, 2);
/*      */                     }
/* 2517 */                     out.write(10);
/* 2518 */                     Hashtable motypedisplaynames = null;
/* 2519 */                     synchronized (application) {
/* 2520 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2521 */                       if (motypedisplaynames == null) {
/* 2522 */                         motypedisplaynames = new Hashtable();
/* 2523 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2526 */                     out.write(10);
/* 2527 */                     Hashtable availabilitykeys = null;
/* 2528 */                     synchronized (application) {
/* 2529 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2530 */                       if (availabilitykeys == null) {
/* 2531 */                         availabilitykeys = new Hashtable();
/* 2532 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2535 */                     out.write(10);
/* 2536 */                     Hashtable healthkeys = null;
/* 2537 */                     synchronized (application) {
/* 2538 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2539 */                       if (healthkeys == null) {
/* 2540 */                         healthkeys = new Hashtable();
/* 2541 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2544 */                     out.write(10);
/* 2545 */                     DialChartSupport dialGraph1 = null;
/* 2546 */                     dialGraph1 = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph1", 1);
/* 2547 */                     if (dialGraph1 == null) {
/* 2548 */                       dialGraph1 = new DialChartSupport();
/* 2549 */                       _jspx_page_context.setAttribute("dialGraph1", dialGraph1, 1);
/*      */                     }
/* 2551 */                     out.write("\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n");
/*      */                     
/* 2553 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2554 */                     String dispname = (String)motypedisplaynames.get(request.getParameter("type"));
/* 2555 */                     String resourceName = request.getParameter("resourcename");
/* 2556 */                     Properties ess_atts = (Properties)request.getAttribute("ess_atts");
/* 2557 */                     int m = 1;
/* 2558 */                     int noOftabs = 0;
/* 2559 */                     List tabConfiguration = (ArrayList)request.getAttribute("tabConfiguration");
/* 2560 */                     ArrayList tabToBeRemoved = (ArrayList)request.getAttribute("tabToBeRemoved");
/* 2561 */                     if (tabConfiguration != null) {
/* 2562 */                       noOftabs = tabConfiguration.size();
/*      */                     }
/* 2564 */                     String moname = request.getParameter("moname");
/* 2565 */                     String resID = request.getParameter("resourceid");
/* 2566 */                     if (request.getAttribute("tabId") != null) {
/* 2567 */                       m = ((Integer)request.getAttribute("tabId")).intValue();
/*      */                     }
/* 2569 */                     ArrayList resIDs = new ArrayList();
/* 2570 */                     resIDs.add(resID);
/* 2571 */                     ArrayList attribIDs = new ArrayList();
/* 2572 */                     attribIDs.add(ess_atts.getProperty("Availability"));
/* 2573 */                     attribIDs.add(ess_atts.getProperty("Health"));
/* 2574 */                     attribIDs.add(ess_atts.getProperty("ResponseTime"));
/* 2575 */                     String haid = request.getParameter("haid");
/* 2576 */                     String original_type1 = request.getParameter("original_type");
/* 2577 */                     String baseid1 = request.getParameter("baseid");
/* 2578 */                     String resourcetype = request.getParameter("type");
/* 2579 */                     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resID);
/* 2580 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2581 */                     String serverName = (String)request.getAttribute("serverName");
/* 2582 */                     String hostOs = (String)request.getAttribute("hostOs");
/* 2583 */                     String hostId = (String)request.getAttribute("hostId");
/* 2584 */                     String hostIp = (String)request.getAttribute("hostName");
/* 2585 */                     HashMap systeminfo = new HashMap();
/* 2586 */                     systeminfo.put("HOSTOS", hostOs);
/* 2587 */                     systeminfo.put("HOSTNAME", serverName);
/* 2588 */                     systeminfo.put("host_resid", hostId);
/* 2589 */                     systeminfo.put("isConf", "true");
/* 2590 */                     Properties reliedargsValues = (Properties)request.getAttribute("ReliedOnArgsMap");
/* 2591 */                     ConfMonitorConfiguration conf = ConfMonitorConfiguration.getInstance();
/* 2592 */                     String subGroup = conf.getSubGroup(resourcetype);
/* 2593 */                     int scrWidth = 1280;
/* 2594 */                     String resourceDisplayName = FormatUtil.getString(conf.getTypeDescription(resourcetype).getProperty("DisplayName"));
/* 2595 */                     String isAgent = conf.getTypeDescription(resourcetype).getProperty("IS-AGENT-ENABLED") != null ? conf.getTypeDescription(resourcetype).getProperty("IS-AGENT-ENABLED") : "NO";
/* 2596 */                     boolean isParentMonitor = false;
/* 2597 */                     if ((!reliedargsValues.isEmpty()) && 
/* 2598 */                       (reliedargsValues.getProperty("ISPARENT") != null) && (reliedargsValues.getProperty("ISPARENT").equalsIgnoreCase("true"))) {
/* 2599 */                       isParentMonitor = true;
/*      */                     }
/*      */                     
/* 2602 */                     ArrayList agentsDown = (ArrayList)request.getAttribute("agentsDown");
/* 2603 */                     pageContext.setAttribute("agentsDown", agentsDown);
/* 2604 */                     String mon_health = ess_atts.getProperty("Health");
/* 2605 */                     ArrayList uIElementsToRemove = (ArrayList)request.getAttribute("RemoveUIElements");
/*      */                     
/*      */ 
/* 2608 */                     out.write("\n<style type=\"text/css\"><!-- ToDo Need to move inline css to stylesheet -->\n\t.tdindent{padding: 0 5px!important;}\n\t.confUlWidth{padding: 5px 10px !important; margin:5px 10px !important; max-width:23% !important;white-space: nowrap; overflow:hidden;}\n\t.confUlWidth:hover{background-color: #f6f6f6 !important;}\n\t.confOneColUIDatatd{width:8% !important;}\n</style>\n<SCRIPT language=\"JavaScript\">\n\nfunction myOnLoad(){\n\t\n}\n</script>\n\n");
/*      */                     
/* 2610 */                     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2611 */                     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2612 */                     _jspx_th_c_005fif_005f0.setParent(null);
/*      */                     
/* 2614 */                     _jspx_th_c_005fif_005f0.setTest("${(empty param.fromTab)}");
/* 2615 */                     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2616 */                     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                       for (;;) {
/* 2618 */                         out.write("\n<head>\n\t");
/* 2619 */                         out.write("<!-- $Id$ -->\n<!-- Style include via jspf file -->\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2620 */                         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                           return;
/* 2622 */                         out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
/* 2623 */                         out.write(10);
/* 2624 */                         out.write(10);
/* 2625 */                         out.write(9);
/* 2626 */                         out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2627 */                         out.write(10);
/* 2628 */                         out.write(9);
/* 2629 */                         out.write("<!-- $Id$ -->\n\n<!-- JS include via file -->\n<script src='");
/* 2630 */                         out.print(request.getContextPath());
/* 2631 */                         out.write("/template/includeAllScript.js' type='text/javascript'></script>\n<script>\n\tincludeAllScripts('");
/* 2632 */                         out.print(request.getContextPath());
/* 2633 */                         out.write("'); //No I18N\n</script>\n");
/* 2634 */                         if (com.adventnet.appmanager.util.Constants.isIt360) {
/* 2635 */                           out.write("<script src='");
/* 2636 */                           out.print(request.getContextPath());
/* 2637 */                           out.write("/it360/js/it360general.js' type='text/javascript'></script>");
/*      */                         }
/* 2639 */                         out.write("\n\t<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/confMonitor.js\"></SCRIPT>\n</head>\n");
/* 2640 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2641 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2645 */                     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2646 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */                     }
/*      */                     else {
/* 2649 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2650 */                       out.write(10);
/*      */                       
/* 2652 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2653 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2654 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 2656 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayoutNoLeft.jsp");
/* 2657 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2658 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 2660 */                           out.write(10);
/*      */                           
/* 2662 */                           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2663 */                           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2664 */                           _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2666 */                           _jspx_th_c_005fif_005f1.setTest("${(empty param.fromTab)}");
/* 2667 */                           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2668 */                           if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                             for (;;) {
/* 2670 */                               out.write(10);
/*      */                               
/* 2672 */                               PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2673 */                               _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2674 */                               _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_c_005fif_005f1);
/*      */                               
/* 2676 */                               _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                               
/* 2678 */                               _jspx_th_tiles_005fput_005f0.setValue(resourceDisplayName);
/* 2679 */                               int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2680 */                               if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2681 */                                 this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                               }
/*      */                               
/* 2684 */                               this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2685 */                               out.write(10);
/* 2686 */                               if (_jspx_meth_tiles_005fput_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                 return;
/* 2688 */                               out.write(10);
/* 2689 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2690 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2694 */                           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2695 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                           }
/*      */                           
/* 2698 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2699 */                           out.write(10);
/* 2700 */                           if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2702 */                           out.write(10);
/*      */                           
/* 2704 */                           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2705 */                           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2706 */                           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2708 */                           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                           
/* 2710 */                           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2711 */                           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2712 */                           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2713 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2714 */                               out = _jspx_page_context.pushBody();
/* 2715 */                               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2716 */                               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2719 */                               out.write(10);
/*      */                               
/* 2721 */                               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2722 */                               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2723 */                               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 2725 */                               _jspx_th_c_005fif_005f3.setTest("${(empty param.fromTab)}");
/* 2726 */                               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2727 */                               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                 for (;;) {
/* 2729 */                                   out.write(10);
/* 2730 */                                   if (_jspx_meth_c_005fif_005f4(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                     return;
/* 2732 */                                   out.write(10);
/*      */                                   try {
/* 2734 */                                     String errormsg = request.getParameter("message");
/* 2735 */                                     if (errormsg != null) {
/* 2736 */                                       errormsg = java.net.URLDecoder.decode(errormsg);
/* 2737 */                                       if (errormsg.contains("UnknownHostException")) {
/* 2738 */                                         errormsg = "am.webclient.query.errormsg";
/*      */                                       }
/* 2740 */                                       else if (errormsg.contains("ConnectException")) {
/* 2741 */                                         errormsg = "am.webclient.query.errormsg1";
/*      */                                       }
/*      */                                     }
/* 2744 */                                     if ((errormsg != null) && (!errormsg.equals("null")) && (!errormsg.trim().equals("")) && (!errormsg.trim().equalsIgnoreCase("false")))
/*      */                                     {
/* 2746 */                                       out.write("\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t\t<td height=\"24\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t\t\t\t\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 2747 */                                       out.print(FormatUtil.getString(errormsg));
/* 2748 */                                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t</table><br>\n\t\t</td>\n\t</tr>\n</table>\n");
/*      */                                     }
/*      */                                   }
/*      */                                   catch (Exception ex) {
/* 2752 */                                     ex.printStackTrace();
/*      */                                   }
/* 2754 */                                   if ((agentsDown != null) && (!agentsDown.isEmpty())) {
/* 2755 */                                     out.write("\n\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t\t\t\t<td height=\"24\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t\t\t\t\t\t\t<td width=\"95%\" height=\"28\" class=\"message\">\n\t\t\t\t\t\t\t");
/* 2756 */                                     if (agentsDown.size() > 1) {
/* 2757 */                                       out.write("\n\t\t\t\t\t\t\t\t");
/*      */                                       
/* 2759 */                                       IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 2760 */                                       _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2761 */                                       _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_c_005fif_005f3);
/*      */                                       
/* 2763 */                                       _jspx_th_logic_005fiterate_005f0.setName("agentsDown");
/*      */                                       
/* 2765 */                                       _jspx_th_logic_005fiterate_005f0.setId("agents");
/*      */                                       
/* 2767 */                                       _jspx_th_logic_005fiterate_005f0.setIndexId("n");
/* 2768 */                                       int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2769 */                                       if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2770 */                                         Object agents = null;
/* 2771 */                                         Integer n = null;
/* 2772 */                                         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2773 */                                           out = _jspx_page_context.pushBody();
/* 2774 */                                           _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2775 */                                           _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                         }
/* 2777 */                                         agents = _jspx_page_context.findAttribute("agents");
/* 2778 */                                         n = (Integer)_jspx_page_context.findAttribute("n");
/*      */                                         for (;;) {
/* 2780 */                                           out.write("\n\t\t\t\t\t\t\t\t\t<li>\n\t\t\t\t\t\t\t\t\t\t");
/* 2781 */                                           if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                                             return;
/* 2783 */                                           out.write("\n\t\t\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t\t\t");
/* 2784 */                                           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2785 */                                           agents = _jspx_page_context.findAttribute("agents");
/* 2786 */                                           n = (Integer)_jspx_page_context.findAttribute("n");
/* 2787 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 2790 */                                         if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2791 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 2794 */                                       if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2795 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                       }
/*      */                                       
/* 2798 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2799 */                                       out.write("\n\t\t\t\t\t\t\t");
/* 2800 */                                     } else if (agentsDown.size() == 1) {
/* 2801 */                                       out.write("\n\t\t\t\t\t\t\t\t\t");
/*      */                                       
/* 2803 */                                       MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 2804 */                                       _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2805 */                                       _jspx_th_fmt_005fmessage_005f1.setParent(_jspx_th_c_005fif_005f3);
/*      */                                       
/* 2807 */                                       _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.eum.agentdown");
/* 2808 */                                       int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 2809 */                                       if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 2810 */                                         if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2811 */                                           out = _jspx_page_context.pushBody();
/* 2812 */                                           _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 2813 */                                           _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 2816 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t");
/*      */                                           
/* 2818 */                                           ParamTag _jspx_th_fmt_005fparam_005f1 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 2819 */                                           _jspx_th_fmt_005fparam_005f1.setPageContext(_jspx_page_context);
/* 2820 */                                           _jspx_th_fmt_005fparam_005f1.setParent(_jspx_th_fmt_005fmessage_005f1);
/* 2821 */                                           int _jspx_eval_fmt_005fparam_005f1 = _jspx_th_fmt_005fparam_005f1.doStartTag();
/* 2822 */                                           if (_jspx_eval_fmt_005fparam_005f1 != 0) {
/* 2823 */                                             if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 2824 */                                               out = _jspx_page_context.pushBody();
/* 2825 */                                               _jspx_th_fmt_005fparam_005f1.setBodyContent((BodyContent)out);
/* 2826 */                                               _jspx_th_fmt_005fparam_005f1.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 2829 */                                               out.print(agentsDown.get(0));
/* 2830 */                                               int evalDoAfterBody = _jspx_th_fmt_005fparam_005f1.doAfterBody();
/* 2831 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 2834 */                                             if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 2835 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 2838 */                                           if (_jspx_th_fmt_005fparam_005f1.doEndTag() == 5) {
/* 2839 */                                             this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1); return;
/*      */                                           }
/*      */                                           
/* 2842 */                                           this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1);
/* 2843 */                                           out.write("\n\t\t\t\t\t\t\t\t\t");
/* 2844 */                                           int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 2845 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 2848 */                                         if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2849 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 2852 */                                       if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 2853 */                                         this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f1); return;
/*      */                                       }
/*      */                                       
/* 2856 */                                       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2857 */                                       out.write("\n\t\t\t\t\t\t\t");
/*      */                                     }
/* 2859 */                                     out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table><br>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n");
/*      */                                   }
/* 2861 */                                   try { out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n");
/*      */                                     
/* 2863 */                                     Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2864 */                                     String aid = request.getParameter("haid");
/* 2865 */                                     String haName = null;
/* 2866 */                                     if (aid != null) {
/* 2867 */                                       haName = (String)ht.get(aid);
/*      */                                     }
/*      */                                     
/* 2870 */                                     out.write(10);
/*      */                                     
/* 2872 */                                     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 2873 */                                     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2874 */                                     _jspx_th_c_005fset_005f1.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 2876 */                                     _jspx_th_c_005fset_005f1.setVar("parentMonitor");
/*      */                                     
/* 2878 */                                     _jspx_th_c_005fset_005f1.setScope("page");
/* 2879 */                                     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2880 */                                     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 2881 */                                       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2882 */                                         out = _jspx_page_context.pushBody();
/* 2883 */                                         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 2884 */                                         _jspx_th_c_005fset_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2887 */                                         out.print(conf.getParentType(resourcetype));
/* 2888 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 2889 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2892 */                                       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2893 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2896 */                                     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2897 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                                     }
/*      */                                     
/* 2900 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 2901 */                                     out.write(10);
/*      */                                     
/* 2903 */                                     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 2904 */                                     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 2905 */                                     _jspx_th_c_005fset_005f2.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 2907 */                                     _jspx_th_c_005fset_005f2.setVar("isAgentMonitor");
/*      */                                     
/* 2909 */                                     _jspx_th_c_005fset_005f2.setScope("page");
/* 2910 */                                     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 2911 */                                     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 2912 */                                       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2913 */                                         out = _jspx_page_context.pushBody();
/* 2914 */                                         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 2915 */                                         _jspx_th_c_005fset_005f2.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2918 */                                         out.print(isAgent);
/* 2919 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 2920 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2923 */                                       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2924 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2927 */                                     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 2928 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2); return;
/*      */                                     }
/*      */                                     
/* 2931 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 2932 */                                     out.write(10);
/*      */                                     
/* 2934 */                                     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 2935 */                                     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 2936 */                                     _jspx_th_c_005fset_005f3.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 2938 */                                     _jspx_th_c_005fset_005f3.setVar("isAgentparent");
/*      */                                     
/* 2940 */                                     _jspx_th_c_005fset_005f3.setScope("page");
/* 2941 */                                     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 2942 */                                     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 2943 */                                       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2944 */                                         out = _jspx_page_context.pushBody();
/* 2945 */                                         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 2946 */                                         _jspx_th_c_005fset_005f3.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 2949 */                                         out.print(isParentMonitor);
/* 2950 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 2951 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 2954 */                                       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2955 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 2958 */                                     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 2959 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3); return;
/*      */                                     }
/*      */                                     
/* 2962 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 2963 */                                     out.write(10);
/*      */                                     
/* 2965 */                                     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2966 */                                     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2967 */                                     _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f3);
/* 2968 */                                     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2969 */                                     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                       for (;;) {
/* 2971 */                                         out.write(10);
/*      */                                         
/* 2973 */                                         WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2974 */                                         _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2975 */                                         _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                         
/* 2977 */                                         _jspx_th_c_005fwhen_005f0.setTest("${!empty param.fromwhere && (param.fromwhere=='infrastructure')}");
/* 2978 */                                         int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2979 */                                         if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                           for (;;) {
/* 2981 */                                             out.write("\n\t\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2982 */                                             out.print(BreadcrumbUtil.getMonitorsPage());
/* 2983 */                                             out.write(" &gt; ");
/* 2984 */                                             out.print(BreadcrumbUtil.getParentType(request.getParameter("type")));
/* 2985 */                                             out.write(" &gt; <span class=\"bcactivebig\"> ");
/* 2986 */                                             out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 2987 */                                             out.write("</span></td>\n");
/* 2988 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2989 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2993 */                                         if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2994 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                         }
/*      */                                         
/* 2997 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2998 */                                         out.write(10);
/*      */                                         
/* 3000 */                                         WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3001 */                                         _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 3002 */                                         _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                         
/* 3004 */                                         _jspx_th_c_005fwhen_005f1.setTest("${!empty param.fromwhere && (param.fromwhere=='parentview') || (parentMonitor!='null' && param.type!=parentMonitor)  || (isAgentMonitor=='YES' && !isAgentparent)}");
/* 3005 */                                         int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 3006 */                                         if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                           for (;;) {
/* 3008 */                                             out.write(10);
/* 3009 */                                             out.write(9);
/* 3010 */                                             out.write(9);
/* 3011 */                                             if ((!isParentMonitor) && (isAgent.equals("NO"))) {
/* 3012 */                                               out.write("\n\t\t\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\">");
/* 3013 */                                               out.print(BreadcrumbUtil.getMonitorsPage());
/* 3014 */                                               out.write(" &gt;");
/* 3015 */                                               out.print(BreadcrumbUtil.getParentDetailsPage(request.getParameter("resourceid")));
/* 3016 */                                               out.write("&gt;");
/* 3017 */                                               out.print(BreadcrumbUtil.getMonitorResourceTypes(subGroup));
/* 3018 */                                               out.write(" &gt;<span class=\"bcactivebig\">");
/* 3019 */                                               out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 3020 */                                               out.write("</span></td>\n\t\t");
/* 3021 */                                             } else if (isAgent.equals("YES")) {
/* 3022 */                                               out.write("\n\t\t\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\">");
/* 3023 */                                               out.print(BreadcrumbUtil.getMonitorsPage());
/* 3024 */                                               out.write("&gt;");
/* 3025 */                                               out.print(BreadcrumbUtil.getMonitorResourceTypes(subGroup));
/* 3026 */                                               out.write("&gt;");
/* 3027 */                                               out.print(BreadcrumbUtil.getParentDetailsPage(request.getParameter("resourceid"), request.getParameter("parentId")));
/* 3028 */                                               out.write(" &gt;<span class=\"bcactivebig\">");
/* 3029 */                                               out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 3030 */                                               out.write("</span></td>\n\t\t");
/*      */                                             } else {
/* 3032 */                                               out.write("\n                <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 3033 */                                               out.print(BreadcrumbUtil.getMonitorsPage());
/* 3034 */                                               out.write(" &gt; ");
/* 3035 */                                               out.print(BreadcrumbUtil.getParentType(request.getParameter("type")));
/* 3036 */                                               out.write(" &gt; ");
/* 3037 */                                               out.print(BreadcrumbUtil.getParentDetailsPage(request.getParameter("resourceid"), request.getParameter("parentId")));
/* 3038 */                                               out.write(" &gt; ");
/* 3039 */                                               out.print(BreadcrumbUtil.getMonitorResourceTypes(subGroup));
/* 3040 */                                               out.write(" &gt; <span class=\"bcactivebig\"> ");
/* 3041 */                                               out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 3042 */                                               out.write("</span></td>\n                ");
/*      */                                             }
/* 3044 */                                             out.write(10);
/* 3045 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 3046 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3050 */                                         if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 3051 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                         }
/*      */                                         
/* 3054 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3055 */                                         out.write(10);
/*      */                                         
/* 3057 */                                         WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3058 */                                         _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3059 */                                         _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                         
/* 3061 */                                         _jspx_th_c_005fwhen_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 3062 */                                         int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3063 */                                         if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                           for (;;) {
/* 3065 */                                             out.write("\n\t\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 3066 */                                             out.print(BreadcrumbUtil.getHomePage(request));
/* 3067 */                                             out.write(" &gt; ");
/* 3068 */                                             out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 3069 */                                             out.write(" &gt; <span class=\"bcactive\"> ");
/* 3070 */                                             out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 3071 */                                             out.write(" </span></td>\n");
/* 3072 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3073 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3077 */                                         if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3078 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                         }
/*      */                                         
/* 3081 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3082 */                                         out.write(10);
/*      */                                         
/* 3084 */                                         WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3085 */                                         _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 3086 */                                         _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                         
/* 3088 */                                         _jspx_th_c_005fwhen_005f3.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 3089 */                                         int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 3090 */                                         if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                           for (;;) {
/* 3092 */                                             out.write("\n\t\t<td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 3093 */                                             out.print(BreadcrumbUtil.getMonitorsPage());
/* 3094 */                                             out.write(" &gt; ");
/* 3095 */                                             out.print(BreadcrumbUtil.getMonitorResourceTypes(subGroup));
/* 3096 */                                             out.write(" &gt; <span class=\"bcactivebig\"> ");
/* 3097 */                                             out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 3098 */                                             out.write("</span></td>\n");
/* 3099 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 3100 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3104 */                                         if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 3105 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                         }
/*      */                                         
/* 3108 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3109 */                                         out.write(10);
/* 3110 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3111 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3115 */                                     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3116 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                                     }
/*      */                                     
/* 3119 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3120 */                                     out.write("\n\t<td width=\"15%\" class=\"breadcrumb_btm_space\">\n\t\t");
/* 3121 */                                     out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/*      */                                     
/* 3123 */                                     int healthid = 2201;
/* 3124 */                                     int availabilityid = 2200;
/* 3125 */                                     availabilityid = Integer.parseInt(ess_atts.getProperty("Availability"));
/* 3126 */                                     healthid = Integer.parseInt(ess_atts.getProperty("Health"));
/* 3127 */                                     String haid1 = "";
/* 3128 */                                     String mon_type = request.getParameter("type");
/* 3129 */                                     ConfMonitorConfiguration confObj = ConfMonitorConfiguration.getInstance();
/* 3130 */                                     boolean isDependent = confObj.isDependentMonitor(mon_type);
/* 3131 */                                     String isAgentEnabled = confObj.getTypeDescription(mon_type).getProperty("IS-AGENT-ENABLED");
/* 3132 */                                     boolean isParent = false;
/* 3133 */                                     if (request.getAttribute("ReliedOnArgsMap") != null) {
/* 3134 */                                       Properties reliedonargsValues = (Properties)request.getAttribute("ReliedOnArgsMap");
/* 3135 */                                       if ((!reliedonargsValues.isEmpty()) && 
/* 3136 */                                         (reliedonargsValues.getProperty("ISPARENT") != null) && (reliedonargsValues.getProperty("ISPARENT").equalsIgnoreCase("true"))) {
/* 3137 */                                         isParent = true;
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 3142 */                                     String resourceid_poll = request.getParameter("resourceid");
/* 3143 */                                     boolean allowEdit = com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil.getInstance().showEditLink(resourceid_poll, baseid1, mon_type);
/* 3144 */                                     String resourcetype_poll = request.getParameter("type");
/* 3145 */                                     int tabToSelect = request.getAttribute("tabId") != null ? ((Integer)request.getAttribute("tabId")).intValue() : 0;
/* 3146 */                                     String mname = request.getAttribute("monitorname") != null ? URLEncoder.encode((String)request.getAttribute("monitorname")) : "";
/* 3147 */                                     if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid_poll, healthid + "") != 0) {
/* 3148 */                                       pageContext.setAttribute("showAlarmHistory", Boolean.valueOf(true));
/*      */                                     }
/* 3150 */                                     if (((isDependent) && (!allowEdit)) || ((isAgentEnabled.equals("YES")) && (!isParent))) {
/* 3151 */                                       allowEdit = false;
/*      */                                     }
/* 3153 */                                     boolean isEEAdminResource = EnterpriseUtil.isResourceRunningInAdmin(resourceid_poll);
/*      */                                     
/* 3155 */                                     out.write(10);
/*      */                                     
/* 3157 */                                     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3158 */                                     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 3159 */                                     _jspx_th_c_005fset_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 3161 */                                     _jspx_th_c_005fset_005f4.setVar("isEEAdminResource");
/* 3162 */                                     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 3163 */                                     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 3164 */                                       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3165 */                                         out = _jspx_page_context.pushBody();
/* 3166 */                                         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 3167 */                                         _jspx_th_c_005fset_005f4.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3170 */                                         out.print(isEEAdminResource);
/* 3171 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 3172 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3175 */                                       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3176 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3179 */                                     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 3180 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */                                     }
/*      */                                     
/* 3183 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 3184 */                                     out.write("\n<script>\n    function confirmDelete()\n \t {\n\t\tvar s;\n\t");
/*      */                                     
/* 3186 */                                     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3187 */                                     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 3188 */                                     _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fif_005f3);
/* 3189 */                                     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 3190 */                                     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                       for (;;) {
/* 3192 */                                         out.write(10);
/* 3193 */                                         out.write(9);
/*      */                                         
/* 3195 */                                         WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3196 */                                         _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 3197 */                                         _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                         
/* 3199 */                                         _jspx_th_c_005fwhen_005f4.setTest("${(param.type==\"VMWare ESX/ESXi\")}");
/* 3200 */                                         int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 3201 */                                         if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                           for (;;) {
/* 3203 */                                             out.write("\n\t\ts = confirm(\"");
/* 3204 */                                             out.print(FormatUtil.getString("am.webclient.vcenter.esxjsalertfordeletevi.text"));
/* 3205 */                                             out.write("\");\n\t");
/* 3206 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 3207 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3211 */                                         if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 3212 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                                         }
/*      */                                         
/* 3215 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3216 */                                         out.write(10);
/* 3217 */                                         out.write(9);
/*      */                                         
/* 3219 */                                         WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3220 */                                         _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 3221 */                                         _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                         
/* 3223 */                                         _jspx_th_c_005fwhen_005f5.setTest("${(param.type==\"VirtualMachine\")}");
/* 3224 */                                         int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 3225 */                                         if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                           for (;;) {
/* 3227 */                                             out.write("\n\t\ts = confirm(\"");
/* 3228 */                                             out.print(FormatUtil.getString("am.webclient.vmware.jsalertfordeletevm.text"));
/* 3229 */                                             out.write("\");\n\t");
/* 3230 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 3231 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3235 */                                         if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 3236 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                                         }
/*      */                                         
/* 3239 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 3240 */                                         out.write(10);
/* 3241 */                                         out.write(9);
/*      */                                         
/* 3243 */                                         WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3244 */                                         _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 3245 */                                         _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                         
/* 3247 */                                         _jspx_th_c_005fwhen_005f6.setTest("${(param.type==\"XenServerHost\")}");
/* 3248 */                                         int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 3249 */                                         if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                                           for (;;) {
/* 3251 */                                             out.write("\n\t\ts = confirm(\"");
/* 3252 */                                             out.print(FormatUtil.getString("am.webclient.xencenter.hostjsalertfordeletevi.text"));
/* 3253 */                                             out.write("\");\n\t");
/* 3254 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 3255 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3259 */                                         if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 3260 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                                         }
/*      */                                         
/* 3263 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 3264 */                                         out.write(10);
/* 3265 */                                         out.write(9);
/*      */                                         
/* 3267 */                                         WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3268 */                                         _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 3269 */                                         _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                         
/* 3271 */                                         _jspx_th_c_005fwhen_005f7.setTest("${(param.type==\"XenServerVM\")}");
/* 3272 */                                         int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 3273 */                                         if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                                           for (;;) {
/* 3275 */                                             out.write("\n\t\ts = confirm(\"");
/* 3276 */                                             out.print(FormatUtil.getString("am.webclient.xenserver.jsalertfordeletevm.text"));
/* 3277 */                                             out.write("\");\n\t");
/* 3278 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 3279 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3283 */                                         if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 3284 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                                         }
/*      */                                         
/* 3287 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3288 */                                         out.write(10);
/* 3289 */                                         out.write(9);
/*      */                                         
/* 3291 */                                         OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3292 */                                         _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3293 */                                         _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f1);
/* 3294 */                                         int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3295 */                                         if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                           for (;;) {
/* 3297 */                                             out.write("\n  \t\ts = confirm(\"");
/* 3298 */                                             out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 3299 */                                             out.write("\");\n\t");
/* 3300 */                                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3301 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3305 */                                         if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3306 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                                         }
/*      */                                         
/* 3309 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3310 */                                         out.write(10);
/* 3311 */                                         out.write(9);
/* 3312 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3313 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3317 */                                     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3318 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                                     }
/*      */                                     
/* 3321 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3322 */                                     out.write(10);
/* 3323 */                                     out.write(9);
/*      */                                     
/* 3325 */                                     if (mon_type.equals("XenServerVM"))
/*      */                                     {
/* 3327 */                                       mon_type = "VirtualMachine";
/*      */                                     }
/*      */                                     
/* 3330 */                                     out.write("\n  \tif (s)\n         document.location.href=\"/deleteMO.do?method=deleteMO&type=");
/* 3331 */                                     out.print(mon_type);
/* 3332 */                                     out.write("&baseid=");
/* 3333 */                                     out.print(baseid1);
/* 3334 */                                     out.write("&select=");
/* 3335 */                                     if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 3337 */                                     out.write("\";\n\t }\n        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 3338 */                                     out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 3339 */                                     out.write("\");\n  if (s){\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 3340 */                                     if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 3342 */                                     out.write("&type=");
/* 3343 */                                     out.print(mon_type);
/* 3344 */                                     out.write("&moname=");
/* 3345 */                                     out.print(moname);
/* 3346 */                                     out.write("&resourcename=");
/* 3347 */                                     out.print(resourceName);
/* 3348 */                                     out.write("\";\n\n}\n\t }\n\n        function confirmUnManage()\n    \t {\n           \t ");
/* 3349 */                                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 3351 */                                     out.write("\n      var show_msg=\"false\";\n      var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 3352 */                                     out.print(request.getParameter("resourceid"));
/* 3353 */                                     out.write("; //No i18n\n      $.ajax({\n        type:'POST', //No i18n\n        url:url,\n        async:false,\n        success: function(data)\n        {\n          show_msg=data\n        }\n      });\n      if(show_msg.indexOf(\"true\")>-1)\n      {\n          alert(\"");
/* 3354 */                                     out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 3355 */                                     out.write("\");\n          \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 3356 */                                     if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 3358 */                                     out.write("&type=");
/* 3359 */                                     out.print(mon_type);
/* 3360 */                                     out.write("&moname=");
/* 3361 */                                     out.print(moname);
/* 3362 */                                     out.write("&resourcename=");
/* 3363 */                                     out.print(resourceName);
/* 3364 */                                     out.write("\";\n\n      }\n      else {\n     var s = confirm(\"");
/* 3365 */                                     out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 3366 */                                     out.write("\");\n     if (s){\n   \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 3367 */                                     if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 3369 */                                     out.write("&type=");
/* 3370 */                                     out.print(mon_type);
/* 3371 */                                     out.write("&moname=");
/* 3372 */                                     out.print(moname);
/* 3373 */                                     out.write("&resourcename=");
/* 3374 */                                     out.print(resourceName);
/* 3375 */                                     out.write("\";\n\n   }\n   \t }\n   \t }\n\n        function confPollNow(){\n           var hreference=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3376 */                                     out.print(resourceid_poll);
/* 3377 */                                     out.write("&resourcetype=");
/* 3378 */                                     out.print(resourcetype_poll);
/* 3379 */                                     out.write("&baseid=");
/* 3380 */                                     out.print(baseid1);
/* 3381 */                                     out.write("\";  //No I18N      \n        \tif(document.getElementById('tabsForm')){  \t\n        \t\tvar tabToSelect=document.getElementById('tabsForm').tabId.value;\n        \t\tvar timeUnit=document.getElementById('tabsForm').TimeUnit.value;\n        \t\tvar granularity=document.getElementById('tabsForm').granularity.value;\n        \t\tvar customDate=document.getElementById('tabsForm').customDate.value;\n        \t\tvar monthUnit=document.getElementById('tabsForm').monthUnit.value;\n        \t\tvar weekUnit=document.getElementById('tabsForm').weekUnit.value;\n        \t\threference=hreference+\"&tabId=\"+tabToSelect+\"&TimeUnit=\"+timeUnit+\"&granularity=\"+granularity+\"&customDate=\"+customDate+\"&monthUnit=\"+monthUnit+\"&weekUnit=\"+weekUnit;       //No i18N \n        \t}            \n        \tdocument.location.href=hreference;\n         }\n         function confeditMonitor(){\n        \t var hreference=\"/manageConfMons.do?haid=");
/* 3382 */                                     if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 3384 */                                     out.write("&method=editMonitor&resourceid=");
/* 3385 */                                     if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 3387 */                                     out.write("&type=");
/* 3388 */                                     out.print(mon_type);
/* 3389 */                                     if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 3391 */                                     out.write("&resourcename=");
/* 3392 */                                     out.print(resourceName);
/* 3393 */                                     out.write("\";//No I18N\n        \t\tif(document.getElementById('tabsForm')){  \t\n        \t\t\tvar tabToSelect=document.getElementById('tabsForm').tabId.value;\n            \t\tvar timeUnit=document.getElementById('tabsForm').TimeUnit.value;\n            \t\tvar granularity=document.getElementById('tabsForm').granularity.value;\n            \t\tvar customDate=document.getElementById('tabsForm').customDate.value;\n            \t\tvar monthUnit=document.getElementById('tabsForm').monthUnit.value;\n            \t\tvar weekUnit=document.getElementById('tabsForm').weekUnit.value;\n            \t\threference=hreference+\"&tabId=\"+tabToSelect+\"&TimeUnit=\"+timeUnit+\"&granularity=\"+granularity+\"&customDate=\"+customDate+\"&monthUnit=\"+monthUnit+\"&weekUnit=\"+weekUnit;       //No i18N\n            \t\t\n        \t\t} \n        \t \t");
/* 3394 */                                     if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) {
/* 3395 */                                       out.write("\n     \t\t\t\threference=hreference+\"&aam_jump=true&editPage=true\";//No i18N \n     \t\t\t\twindow.open(hreference);\n             \t    return;\n     \t\t\t");
/*      */                                     }
/* 3397 */                                     out.write("\n        \t\tdocument.location.href=hreference;\n         }\n\tfunction handleConfig(url,winname,height,width,top,left,isresize,iscroll)\n\t{\n\tvar popoutwindow=window.open(url+\"&resourceid=");
/* 3398 */                                     out.print(resID);
/* 3399 */                                     out.write("\",winname,'scrollbars='+iscroll+',resizable='+isresize+',width='+width+',height='+height+',left='+left+',top='+top+',screenX=250,screenY=25');//No i18N \n\tpopoutwindow.focus();\n\t}\n</script>\n");
/*      */                                     
/* 3401 */                                     Hashtable globals = (Hashtable)application.getAttribute("globalconfig");
/* 3402 */                                     String allowOperatorManage = (String)globals.get("allowOperatorManage");
/* 3403 */                                     String displayClass = "leftlinkstd-conf";
/* 3404 */                                     if (mon_type.equals("QueryMonitor")) {
/* 3405 */                                       displayClass = "leftlinkstd";
/*      */                                       
/* 3407 */                                       out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td width=\"90%\" height=\"21\" class=\"leftlinksheading\">\n\t\t");
/* 3408 */                                       out.print(FormatUtil.getString("am.querymonitor.heading.text"));
/* 3409 */                                       out.write("\n\n      </td>\n  </tr>\n <tr>\n    <td height=\"21\" class=\"");
/* 3410 */                                       out.print(displayClass);
/* 3411 */                                       out.write("\">\n    ");
/*      */                                       
/* 3413 */                                       IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3414 */                                       _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3415 */                                       _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f3);
/*      */                                       
/* 3417 */                                       _jspx_th_c_005fif_005f6.setTest("${param.method=='editScript'}");
/* 3418 */                                       int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3419 */                                       if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                         for (;;) {
/* 3421 */                                           out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 3422 */                                           if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                                             return;
/* 3424 */                                           out.write("&method=showResourceForResourceID");
/* 3425 */                                           out.print(haid1);
/* 3426 */                                           out.write("\"  class=\"new-left-links\">");
/* 3427 */                                           out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 3428 */                                           out.write("</a>\n    ");
/* 3429 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3430 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3434 */                                       if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3435 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                       }
/*      */                                       
/* 3438 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3439 */                                       out.write("\n    ");
/*      */                                       
/* 3441 */                                       IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3442 */                                       _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3443 */                                       _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f3);
/*      */                                       
/* 3445 */                                       _jspx_th_c_005fif_005f7.setTest("${param.method!='editScript'}");
/* 3446 */                                       int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3447 */                                       if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                         for (;;) {
/* 3449 */                                           out.write("\n    ");
/* 3450 */                                           out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 3451 */                                           out.write("\n    ");
/* 3452 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3453 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3457 */                                       if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3458 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                       }
/*      */                                       
/* 3461 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3462 */                                       out.write("\n     </td>\n  </tr>\n  ");
/*      */                                     }
/*      */                                     else {
/* 3465 */                                       String isReqFromCentral = request.getParameter("fromCentral");
/* 3466 */                                       String tempStyl = (isReqFromCentral != null) && (isReqFromCentral.trim().equals("true")) ? "display:none" : "";
/*      */                                       
/* 3468 */                                       out.write("\n\t<table  cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"");
/* 3469 */                                       out.print(tempStyl);
/* 3470 */                                       out.write("\">\n\t\t\t<tr>\n\t\t\t\t<td class=\"buttons btn_action\" id=\"apmdetails\" align=\"center\" onclick=\"globalShowMenuInDialogConf('apmdetails', 'apmdetailsDiv',89,-1);\">\n\t\t\t\t\t ");
/* 3471 */                                       out.print(FormatUtil.getString("am.webclient.common.conf.monitorActions.text"));
/* 3472 */                                       out.write(" <img src=\"/images/icon_black_arrow.gif\" border=\"0\" style=\"margin-bottom:2px;\"/>\n\t\t\t\t\t<div style=\"display:none;\" id=\"apmdetailsDiv\" >\n\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t<table id=\"apmdetailsDivMenu\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" >\n");
/*      */                                     }
/* 3474 */                                     out.write(10);
/* 3475 */                                     out.write(32);
/* 3476 */                                     out.write(32);
/*      */                                     
/* 3478 */                                     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3479 */                                     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3480 */                                     _jspx_th_c_005fif_005f8.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 3482 */                                     _jspx_th_c_005fif_005f8.setTest("${!empty param.haid && empty invalidhaid}");
/* 3483 */                                     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3484 */                                     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                       for (;;) {
/* 3486 */                                         out.write(10);
/*      */                                         
/* 3488 */                                         haid1 = "&haid=" + request.getParameter("haid");
/*      */                                         
/* 3490 */                                         out.write(10);
/* 3491 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3492 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3496 */                                     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3497 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                     }
/*      */                                     
/* 3500 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3501 */                                     out.write(32);
/* 3502 */                                     out.write(10);
/* 3503 */                                     if (allowEdit) {
/* 3504 */                                       out.write(10);
/* 3505 */                                       out.write(32);
/* 3506 */                                       out.write(32);
/*      */                                       
/* 3508 */                                       PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3509 */                                       _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3510 */                                       _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f3);
/*      */                                       
/* 3512 */                                       _jspx_th_logic_005fpresent_005f1.setRole("ENTERPRISEADMIN");
/* 3513 */                                       int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3514 */                                       if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                         for (;;) {
/* 3516 */                                           out.write("\n   ");
/*      */                                           
/* 3518 */                                           if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled())
/*      */                                           {
/* 3520 */                                             out.write(" \n  <tr>\n   <td height=\"21\" class=\"");
/* 3521 */                                             out.print(displayClass);
/* 3522 */                                             out.write("\">\n  \t\t<a href=\"javascript:confeditMonitor();\"  class=\"new-left-links\">\n    \t\t<img src=\"images/icon_edit.gif\" border=\"0\"/>&nbsp;");
/* 3523 */                                             out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3524 */                                             out.write("</a>\n   </td>\n  </tr>\n  ");
/*      */                                           }
/* 3526 */                                           out.write(10);
/* 3527 */                                           out.write(32);
/* 3528 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3529 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3533 */                                       if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3534 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                       }
/*      */                                       
/* 3537 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3538 */                                       out.write(10);
/*      */                                       
/* 3540 */                                       IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3541 */                                       _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3542 */                                       _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f3);
/*      */                                       
/* 3544 */                                       _jspx_th_c_005fif_005f9.setTest("${(!empty ADMIN || !empty DEMO) }");
/* 3545 */                                       int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3546 */                                       if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                         for (;;) {
/* 3548 */                                           out.write("\n  <tr>\n");
/*      */                                           
/* 3550 */                                           PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3551 */                                           _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3552 */                                           _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f9);
/*      */                                           
/* 3554 */                                           _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3555 */                                           int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3556 */                                           if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                             for (;;) {
/* 3558 */                                               out.write("\n  <td height=\"21\" class=\"");
/* 3559 */                                               out.print(displayClass);
/* 3560 */                                               out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n   <img src=\"images/icon_edit.gif\" border=\"0\"/>&nbsp;");
/* 3561 */                                               out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3562 */                                               out.write("</a> </td>\n  ");
/* 3563 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3564 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3568 */                                           if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3569 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                           }
/*      */                                           
/* 3572 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3573 */                                           out.write("\n   ");
/*      */                                           
/* 3575 */                                           NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3576 */                                           _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3577 */                                           _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f9);
/*      */                                           
/* 3579 */                                           _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 3580 */                                           int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3581 */                                           if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                             for (;;) {
/* 3583 */                                               out.write("\n    <td height=\"21\" class=\"");
/* 3584 */                                               out.print(displayClass);
/* 3585 */                                               out.write("\">\n    ");
/*      */                                               
/* 3587 */                                               IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3588 */                                               _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3589 */                                               _jspx_th_c_005fif_005f10.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                               
/* 3591 */                                               _jspx_th_c_005fif_005f10.setTest("${param.actionmethod!='editScript' }");
/* 3592 */                                               int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3593 */                                               if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                                 for (;;) {
/* 3595 */                                                   out.write("\n    <a href=\"javascript:confeditMonitor();\"  class=\"new-left-links\">\n    <img src=\"images/icon_edit.gif\" border=\"0\"/>&nbsp;");
/* 3596 */                                                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3597 */                                                   out.write("</a>\n");
/* 3598 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3599 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3603 */                                               if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3604 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                               }
/*      */                                               
/* 3607 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3608 */                                               out.write(10);
/*      */                                               
/* 3610 */                                               IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3611 */                                               _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3612 */                                               _jspx_th_c_005fif_005f11.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                               
/* 3614 */                                               _jspx_th_c_005fif_005f11.setTest("${param.actionmethod=='editScript' }");
/* 3615 */                                               int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3616 */                                               if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                                 for (;;) {
/* 3618 */                                                   out.write(10);
/* 3619 */                                                   out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3620 */                                                   out.write(10);
/* 3621 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3622 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3626 */                                               if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3627 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                                               }
/*      */                                               
/* 3630 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3631 */                                               out.write(10);
/* 3632 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3633 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3637 */                                           if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3638 */                                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                           }
/*      */                                           
/* 3641 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3642 */                                           out.write("\n</tr>\n");
/* 3643 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3644 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3648 */                                       if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3649 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                       }
/*      */                                       
/* 3652 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3653 */                                       out.write(10);
/*      */                                     }
/* 3655 */                                     out.write(10);
/*      */                                     
/* 3657 */                                     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3658 */                                     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3659 */                                     _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 3661 */                                     _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 3662 */                                     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3663 */                                     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                       for (;;) {
/* 3665 */                                         out.write("\n  <tr>\n    <td height=\"21\" class=\"");
/* 3666 */                                         out.print(displayClass);
/* 3667 */                                         out.write("\"><a href=\"javascript:confirmDelete();\" class=\"new-left-links\"><img src=\"images/deleteWidget.gif\" border=\"0\" />&nbsp; ");
/* 3668 */                                         out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3669 */                                         out.write("</a></td>\n  </tr>\n");
/* 3670 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3671 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3675 */                                     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3676 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                                     }
/*      */                                     
/* 3679 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3680 */                                     out.write(10);
/*      */                                     
/* 3682 */                                     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3683 */                                     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3684 */                                     _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 3686 */                                     _jspx_th_logic_005fpresent_005f4.setRole("ENTERPRISEADMIN");
/* 3687 */                                     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3688 */                                     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                       for (;;) {
/* 3690 */                                         out.write(32);
/* 3691 */                                         out.write(10);
/* 3692 */                                         out.write(32);
/*      */                                         
/* 3694 */                                         IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3695 */                                         _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3696 */                                         _jspx_th_c_005fif_005f12.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                         
/* 3698 */                                         _jspx_th_c_005fif_005f12.setTest("${isEEAdminResource}");
/* 3699 */                                         int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3700 */                                         if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                           for (;;) {
/* 3702 */                                             out.write("\n  <tr>\n    <td height=\"21\" class=\"");
/* 3703 */                                             out.print(displayClass);
/* 3704 */                                             out.write("\"><a href=\"javascript:confirmDelete();\" class=\"new-left-links\"><img src=\"images/deleteWidget.gif\" border=\"0\" />&nbsp; ");
/* 3705 */                                             out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3706 */                                             out.write("</a></td>\n  </tr>\n  ");
/* 3707 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3708 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3712 */                                         if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3713 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                                         }
/*      */                                         
/* 3716 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3717 */                                         out.write(10);
/* 3718 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3719 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3723 */                                     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3724 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                     }
/*      */                                     
/* 3727 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3728 */                                     out.write(10);
/*      */                                     
/* 3730 */                                     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3731 */                                     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3732 */                                     _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 3734 */                                     _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 3735 */                                     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3736 */                                     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                       for (;;) {
/* 3738 */                                         out.write("\n\n<td height=\"21\" class=\"");
/* 3739 */                                         out.print(displayClass);
/* 3740 */                                         out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\"><img src=\"images/deleteWidget.gif\" border=\"0\" />&nbsp; ");
/* 3741 */                                         out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3742 */                                         out.write("</a></td>\n\n");
/* 3743 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3744 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3748 */                                     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3749 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                     }
/*      */                                     
/* 3752 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3753 */                                     out.write("\n\n  ");
/*      */                                     
/* 3755 */                                     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3756 */                                     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3757 */                                     _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 3759 */                                     _jspx_th_c_005fif_005f13.setTest("${!empty ADMIN || !empty DEMO}");
/* 3760 */                                     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3761 */                                     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                       for (;;) {
/* 3763 */                                         out.write("\n\n  <tr>\n\n  ");
/*      */                                         
/* 3765 */                                         if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                                         {
/*      */ 
/* 3768 */                                           out.write(10);
/* 3769 */                                           out.write(9);
/*      */                                           
/* 3771 */                                           PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3772 */                                           _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3773 */                                           _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f13);
/*      */                                           
/* 3775 */                                           _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 3776 */                                           int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3777 */                                           if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                             for (;;) {
/* 3779 */                                               out.write("\n  <td height=\"21\" class=\"");
/* 3780 */                                               out.print(displayClass);
/* 3781 */                                               out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  <img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3782 */                                               out.print(FormatUtil.getString("Manage"));
/* 3783 */                                               out.write("</a> </td>\n  ");
/* 3784 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3785 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3789 */                                           if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3790 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                           }
/*      */                                           
/* 3793 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3794 */                                           out.write("\n        ");
/*      */                                           
/* 3796 */                                           NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3797 */                                           _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3798 */                                           _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                                           
/* 3800 */                                           _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 3801 */                                           int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3802 */                                           if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                             for (;;) {
/* 3804 */                                               out.write("\n\n\n    <td class=\"");
/* 3805 */                                               out.print(displayClass);
/* 3806 */                                               out.write("\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\"><img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3807 */                                               out.print(FormatUtil.getString("Manage"));
/* 3808 */                                               out.write("</A></td>\n");
/* 3809 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3810 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3814 */                                           if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3815 */                                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                           }
/*      */                                           
/* 3818 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3819 */                                           out.write("\n    ");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/*      */ 
/* 3825 */                                           out.write(10);
/*      */                                           
/* 3827 */                                           PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3828 */                                           _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3829 */                                           _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fif_005f13);
/*      */                                           
/* 3831 */                                           _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 3832 */                                           int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3833 */                                           if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                             for (;;) {
/* 3835 */                                               out.write("\n  <td height=\"21\" class=\"");
/* 3836 */                                               out.print(displayClass);
/* 3837 */                                               out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  <img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3838 */                                               out.print(FormatUtil.getString("UnManage"));
/* 3839 */                                               out.write("</a> </td>\n  ");
/* 3840 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3841 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3845 */                                           if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3846 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                                           }
/*      */                                           
/* 3849 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3850 */                                           out.write("\n        ");
/*      */                                           
/* 3852 */                                           NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3853 */                                           _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3854 */                                           _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f13);
/*      */                                           
/* 3856 */                                           _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3857 */                                           int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3858 */                                           if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                             for (;;) {
/* 3860 */                                               out.write("\n\n\n    <td class=\"");
/* 3861 */                                               out.print(displayClass);
/* 3862 */                                               out.write("\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\"><img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3863 */                                               out.print(FormatUtil.getString("UnManage"));
/* 3864 */                                               out.write("</A></td>\n");
/* 3865 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3866 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3870 */                                           if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3871 */                                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                           }
/*      */                                           
/* 3874 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3875 */                                           out.write("\n    ");
/*      */                                         }
/*      */                                         
/*      */ 
/* 3879 */                                         out.write("\n  </tr>\n  ");
/* 3880 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3881 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3885 */                                     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3886 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                                     }
/*      */                                     
/* 3889 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3890 */                                     out.write("\n  \n   ");
/*      */                                     
/* 3892 */                                     IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3893 */                                     _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3894 */                                     _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 3896 */                                     _jspx_th_c_005fif_005f14.setTest("${OPERATOR}");
/* 3897 */                                     int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3898 */                                     if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                       for (;;) {
/* 3900 */                                         out.write(10);
/* 3901 */                                         out.write(32);
/* 3902 */                                         out.write(32);
/*      */                                         
/* 3904 */                                         if (allowOperatorManage.equals("true"))
/*      */                                         {
/*      */ 
/* 3907 */                                           out.write("\n\n  <tr>\n\n  ");
/*      */                                           
/* 3909 */                                           if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                                           {
/*      */ 
/* 3912 */                                             out.write(10);
/* 3913 */                                             out.write(9);
/*      */                                             
/* 3915 */                                             PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3916 */                                             _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3917 */                                             _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fif_005f14);
/*      */                                             
/* 3919 */                                             _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 3920 */                                             int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3921 */                                             if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                               for (;;) {
/* 3923 */                                                 out.write("\n  <td height=\"21\" class=\"");
/* 3924 */                                                 out.print(displayClass);
/* 3925 */                                                 out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  <img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3926 */                                                 out.print(FormatUtil.getString("Manage"));
/* 3927 */                                                 out.write("</a> </td>\n  ");
/* 3928 */                                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3929 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3933 */                                             if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3934 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                                             }
/*      */                                             
/* 3937 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3938 */                                             out.write("\n        ");
/*      */                                             
/* 3940 */                                             NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3941 */                                             _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3942 */                                             _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_c_005fif_005f14);
/*      */                                             
/* 3944 */                                             _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 3945 */                                             int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3946 */                                             if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                                               for (;;) {
/* 3948 */                                                 out.write("\n\n\n    <td class=\"");
/* 3949 */                                                 out.print(displayClass);
/* 3950 */                                                 out.write("\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\"><img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3951 */                                                 out.print(FormatUtil.getString("Manage"));
/* 3952 */                                                 out.write("</A></td>\n");
/* 3953 */                                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3954 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3958 */                                             if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3959 */                                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                                             }
/*      */                                             
/* 3962 */                                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3963 */                                             out.write("\n    ");
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/*      */ 
/* 3969 */                                             out.write(10);
/*      */                                             
/* 3971 */                                             PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3972 */                                             _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3973 */                                             _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_c_005fif_005f14);
/*      */                                             
/* 3975 */                                             _jspx_th_logic_005fpresent_005f9.setRole("DEMO");
/* 3976 */                                             int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3977 */                                             if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                                               for (;;) {
/* 3979 */                                                 out.write("\n  <td height=\"21\" class=\"");
/* 3980 */                                                 out.print(displayClass);
/* 3981 */                                                 out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  <img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3982 */                                                 out.print(FormatUtil.getString("UnManage"));
/* 3983 */                                                 out.write("</a> </td>\n  ");
/* 3984 */                                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3985 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3989 */                                             if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3990 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                                             }
/*      */                                             
/* 3993 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3994 */                                             out.write("\n        ");
/*      */                                             
/* 3996 */                                             NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3997 */                                             _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 3998 */                                             _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_c_005fif_005f14);
/*      */                                             
/* 4000 */                                             _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/* 4001 */                                             int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 4002 */                                             if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                                               for (;;) {
/* 4004 */                                                 out.write("\n\n\n    <td class=\"");
/* 4005 */                                                 out.print(displayClass);
/* 4006 */                                                 out.write("\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\"><img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 4007 */                                                 out.print(FormatUtil.getString("UnManage"));
/* 4008 */                                                 out.write("</A></td>\n");
/* 4009 */                                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 4010 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 4014 */                                             if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 4015 */                                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                                             }
/*      */                                             
/* 4018 */                                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 4019 */                                             out.write("\n    ");
/*      */                                           }
/*      */                                           
/*      */ 
/* 4023 */                                           out.write("\n  </tr>\n   ");
/*      */                                         }
/*      */                                         
/*      */ 
/* 4027 */                                         out.write(10);
/* 4028 */                                         out.write(32);
/* 4029 */                                         out.write(32);
/* 4030 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 4031 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4035 */                                     if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 4036 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                     }
/*      */                                     
/* 4039 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 4040 */                                     out.write(10);
/* 4041 */                                     out.write(32);
/* 4042 */                                     out.write(32);
/*      */                                     
/* 4044 */                                     PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4045 */                                     _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 4046 */                                     _jspx_th_logic_005fpresent_005f10.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 4048 */                                     _jspx_th_logic_005fpresent_005f10.setRole("DEMO,ENTERPRISEADMIN,ADMIN");
/* 4049 */                                     int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 4050 */                                     if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                                       for (;;) {
/* 4052 */                                         out.write("\n  \t<tr>\n  \t");
/*      */                                         
/* 4054 */                                         PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4055 */                                         _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 4056 */                                         _jspx_th_logic_005fpresent_005f11.setParent(_jspx_th_logic_005fpresent_005f10);
/*      */                                         
/* 4058 */                                         _jspx_th_logic_005fpresent_005f11.setRole("DEMO");
/* 4059 */                                         int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 4060 */                                         if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                                           for (;;) {
/* 4062 */                                             out.write("\n \t <td height=\"21\" class=\"");
/* 4063 */                                             out.print(displayClass);
/* 4064 */                                             out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n \t \t  <img src=\"images/icon_associateaction.gif\" border=\"0\" />&nbsp; ");
/* 4065 */                                             out.print(ALERTCONFIG_TEXT);
/* 4066 */                                             out.write("</a>\n \t </td>\n  ");
/* 4067 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 4068 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4072 */                                         if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 4073 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11); return;
/*      */                                         }
/*      */                                         
/* 4076 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 4077 */                                         out.write(10);
/* 4078 */                                         out.write(32);
/* 4079 */                                         out.write(32);
/*      */                                         
/* 4081 */                                         NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4082 */                                         _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 4083 */                                         _jspx_th_logic_005fnotPresent_005f5.setParent(_jspx_th_logic_005fpresent_005f10);
/*      */                                         
/* 4085 */                                         _jspx_th_logic_005fnotPresent_005f5.setRole("DEMO");
/* 4086 */                                         int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 4087 */                                         if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                                           for (;;) {
/* 4089 */                                             out.write("\n    <td height=\"21\" class=\"");
/* 4090 */                                             out.print(displayClass);
/* 4091 */                                             out.write("\"><a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 4092 */                                             if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fnotPresent_005f5, _jspx_page_context))
/*      */                                               return;
/* 4094 */                                             out.write("&mtype=");
/* 4095 */                                             out.print(mon_type);
/* 4096 */                                             out.write("&viewBy=monitors\" class=\"new-left-links\">\n                <img src=\"images/icon_associateaction.gif\" border=\"0\" /> &nbsp;");
/* 4097 */                                             out.print(ALERTCONFIG_TEXT);
/* 4098 */                                             out.write("</a>\n        </td>\n   ");
/* 4099 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 4100 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4104 */                                         if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 4105 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */                                         }
/*      */                                         
/* 4108 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 4109 */                                         out.write("\n\n  </tr>\n  ");
/* 4110 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 4111 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4115 */                                     if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 4116 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10); return;
/*      */                                     }
/*      */                                     
/* 4119 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 4120 */                                     out.write(10);
/* 4121 */                                     out.write(32);
/* 4122 */                                     out.write(32);
/* 4123 */                                     if ((!isAgentEnabled.equals("YES")) || (!isParent)) {
/* 4124 */                                       out.write(10);
/* 4125 */                                       out.write(32);
/* 4126 */                                       out.write(32);
/*      */                                       
/* 4128 */                                       NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4129 */                                       _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 4130 */                                       _jspx_th_logic_005fnotPresent_005f6.setParent(_jspx_th_c_005fif_005f3);
/*      */                                       
/* 4132 */                                       _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 4133 */                                       int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 4134 */                                       if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                                         for (;;) {
/* 4136 */                                           out.write("\n    \n        <tr>\n        <td width=\"49%\" height=\"21\" class=\"");
/* 4137 */                                           out.print(displayClass);
/* 4138 */                                           out.write("\" >\n        <a href=\"javascript:confPollNow();\" class=\"new-left-links\">\n\t\t<img src=\"images/cam_report_enabled.gif\" border=\"0\" style=\"position:relative; top:3px;\"/> &nbsp;");
/* 4139 */                                           out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 4140 */                                           out.write("</a></td>\n      </tr>\n    ");
/* 4141 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 4142 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4146 */                                       if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 4147 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6); return;
/*      */                                       }
/*      */                                       
/* 4150 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 4151 */                                       out.write("\n    ");
/*      */                                       
/* 4153 */                                       PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4154 */                                       _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 4155 */                                       _jspx_th_logic_005fpresent_005f12.setParent(_jspx_th_c_005fif_005f3);
/*      */                                       
/* 4157 */                                       _jspx_th_logic_005fpresent_005f12.setRole("DEMO");
/* 4158 */                                       int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 4159 */                                       if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                                         for (;;) {
/* 4161 */                                           out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"");
/* 4162 */                                           out.print(displayClass);
/* 4163 */                                           out.write("\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\"><img src=\"images/cam_report_enabled.gif\" border=\"0\" style=\"position:relative; top:3px;\"/> &nbsp;");
/* 4164 */                                           out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 4165 */                                           out.write("</a></td>\n      </td></tr>\n    ");
/* 4166 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 4167 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4171 */                                       if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 4172 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12); return;
/*      */                                       }
/*      */                                       
/* 4175 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 4176 */                                       out.write(10);
/* 4177 */                                       out.write(32);
/* 4178 */                                       out.write(32);
/*      */                                     }
/* 4180 */                                     out.write("\n    ");
/*      */                                     
/* 4182 */                                     NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4183 */                                     _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 4184 */                                     _jspx_th_logic_005fnotPresent_005f7.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 4186 */                                     _jspx_th_logic_005fnotPresent_005f7.setRole("DEMO,ENTERPRISEADMIN,OPERATOR");
/* 4187 */                                     int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 4188 */                                     if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                                       for (;;) {
/* 4190 */                                         out.write(10);
/*      */                                         
/* 4192 */                                         ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4193 */                                         _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 4194 */                                         _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_logic_005fnotPresent_005f7);
/* 4195 */                                         int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 4196 */                                         if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                           for (;;) {
/* 4198 */                                             out.write(10);
/*      */                                             
/* 4200 */                                             WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4201 */                                             _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 4202 */                                             _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                             
/* 4204 */                                             _jspx_th_c_005fwhen_005f8.setTest("${param.type=='QueryMonitor'}");
/* 4205 */                                             int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 4206 */                                             if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                               for (;;) {
/* 4208 */                                                 out.write("\n\n<tr>\n\t\t<td height=\"21\" class=\"");
/* 4209 */                                                 out.print(displayClass);
/* 4210 */                                                 out.write("\">\n\n<a href=\"javascript:enableReports()\" class=\"new-left-links\"><img src=\"images/icon-anamoly-responsetime.gif\" border=\"0\" /> &nbsp;");
/* 4211 */                                                 out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 4212 */                                                 out.write("</a>\n     </td>\n");
/* 4213 */                                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 4214 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 4218 */                                             if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 4219 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                                             }
/*      */                                             
/* 4222 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 4223 */                                             out.write(10);
/*      */                                             
/* 4225 */                                             OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4226 */                                             _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 4227 */                                             _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f2);
/* 4228 */                                             int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 4229 */                                             if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                               for (;;) {
/* 4231 */                                                 out.write("\n\n  <tr>\n    <td height=\"21\" class=\"");
/* 4232 */                                                 out.print(displayClass);
/* 4233 */                                                 out.write("\"><a href=\"/customReports.do?method=showCustomReports&selectedType=");
/* 4234 */                                                 out.print(mon_type);
/* 4235 */                                                 out.write("&resourceid=");
/* 4236 */                                                 out.print(resID);
/* 4237 */                                                 out.write("\" class=\"new-left-links\">\n\t<img src=\"images/icon-anamoly-responsetime.gif\" border=\"0\" /> &nbsp;");
/* 4238 */                                                 out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 4239 */                                                 out.write("</a></td>\n  </tr>\n");
/* 4240 */                                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 4241 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 4245 */                                             if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 4246 */                                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                             }
/*      */                                             
/* 4249 */                                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 4250 */                                             out.write(10);
/* 4251 */                                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 4252 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4256 */                                         if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 4257 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                         }
/*      */                                         
/* 4260 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 4261 */                                         out.write(10);
/* 4262 */                                         out.write(32);
/* 4263 */                                         out.write(32);
/* 4264 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 4265 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4269 */                                     if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 4270 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7); return;
/*      */                                     }
/*      */                                     
/* 4273 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 4274 */                                     out.write("\n    ");
/*      */                                     
/* 4276 */                                     PresentTag _jspx_th_logic_005fpresent_005f13 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4277 */                                     _jspx_th_logic_005fpresent_005f13.setPageContext(_jspx_page_context);
/* 4278 */                                     _jspx_th_logic_005fpresent_005f13.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 4280 */                                     _jspx_th_logic_005fpresent_005f13.setRole("DEMO");
/* 4281 */                                     int _jspx_eval_logic_005fpresent_005f13 = _jspx_th_logic_005fpresent_005f13.doStartTag();
/* 4282 */                                     if (_jspx_eval_logic_005fpresent_005f13 != 0) {
/*      */                                       for (;;) {
/* 4284 */                                         out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"");
/* 4285 */                                         out.print(displayClass);
/* 4286 */                                         out.write("\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 4287 */                                         out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 4288 */                                         out.write("</a></td>\n      </td></tr>\n    ");
/* 4289 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f13.doAfterBody();
/* 4290 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4294 */                                     if (_jspx_th_logic_005fpresent_005f13.doEndTag() == 5) {
/* 4295 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13); return;
/*      */                                     }
/*      */                                     
/* 4298 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
/* 4299 */                                     out.write("\n    ");
/*      */                                     
/* 4301 */                                     IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4302 */                                     _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4303 */                                     _jspx_th_c_005fif_005f15.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 4305 */                                     _jspx_th_c_005fif_005f15.setTest("${isCustomConfigEnabled}");
/* 4306 */                                     int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4307 */                                     if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                       for (;;) {
/* 4309 */                                         out.write("\n      ");
/*      */                                         
/* 4311 */                                         ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4312 */                                         _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4313 */                                         _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f15);
/*      */                                         
/* 4315 */                                         _jspx_th_c_005fforEach_005f0.setVar("eachCustomConfig");
/*      */                                         
/* 4317 */                                         _jspx_th_c_005fforEach_005f0.setItems("${CustomConfigList}");
/*      */                                         
/* 4319 */                                         _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 4320 */                                         int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                         try {
/* 4322 */                                           int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4323 */                                           if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                             for (;;) {
/* 4325 */                                               out.write("\n\t<tr>\n\t  ");
/*      */                                               
/* 4327 */                                               PresentTag _jspx_th_logic_005fpresent_005f14 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4328 */                                               _jspx_th_logic_005fpresent_005f14.setPageContext(_jspx_page_context);
/* 4329 */                                               _jspx_th_logic_005fpresent_005f14.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                               
/* 4331 */                                               _jspx_th_logic_005fpresent_005f14.setRole("DEMO");
/* 4332 */                                               int _jspx_eval_logic_005fpresent_005f14 = _jspx_th_logic_005fpresent_005f14.doStartTag();
/* 4333 */                                               if (_jspx_eval_logic_005fpresent_005f14 != 0) {
/*      */                                                 for (;;) {
/* 4335 */                                                   out.write("\n\t    <td height=\"21\" class=\"");
/* 4336 */                                                   out.print(displayClass);
/* 4337 */                                                   out.write("\" title=\"");
/* 4338 */                                                   if (_jspx_meth_c_005fout_005f11(_jspx_th_logic_005fpresent_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4340 */                                                   out.write("\"> <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n\t      <img src=\"");
/* 4341 */                                                   if (_jspx_meth_c_005fout_005f12(_jspx_th_logic_005fpresent_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4343 */                                                   out.write("\" border=\"0\"/>&nbsp;");
/* 4344 */                                                   if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fpresent_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4346 */                                                   out.write("</a> </td>\n\t  ");
/* 4347 */                                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f14.doAfterBody();
/* 4348 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 4352 */                                               if (_jspx_th_logic_005fpresent_005f14.doEndTag() == 5) {
/* 4353 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14);
/*      */                                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 4356 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14);
/* 4357 */                                               out.write("\n\t  ");
/*      */                                               
/* 4359 */                                               NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4360 */                                               _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 4361 */                                               _jspx_th_logic_005fnotPresent_005f8.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                               
/* 4363 */                                               _jspx_th_logic_005fnotPresent_005f8.setRole("DEMO");
/* 4364 */                                               int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 4365 */                                               if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */                                                 for (;;) {
/* 4367 */                                                   out.write("\n\t     <td height=\"21\" class=\"");
/* 4368 */                                                   out.print(displayClass);
/* 4369 */                                                   out.write("\" title=\"");
/* 4370 */                                                   if (_jspx_meth_c_005fout_005f14(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4372 */                                                   out.write("\">\t\t\n\t\t  <a href=\"javascript:handleConfig('");
/* 4373 */                                                   if (_jspx_meth_c_005fout_005f15(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4375 */                                                   out.write(39);
/* 4376 */                                                   out.write(44);
/* 4377 */                                                   out.write(39);
/* 4378 */                                                   if (_jspx_meth_c_005fout_005f16(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4380 */                                                   out.write(39);
/* 4381 */                                                   out.write(44);
/* 4382 */                                                   out.write(39);
/* 4383 */                                                   if (_jspx_meth_c_005fout_005f17(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4385 */                                                   out.write(39);
/* 4386 */                                                   out.write(44);
/* 4387 */                                                   out.write(39);
/* 4388 */                                                   if (_jspx_meth_c_005fout_005f18(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4390 */                                                   out.write(39);
/* 4391 */                                                   out.write(44);
/* 4392 */                                                   out.write(39);
/* 4393 */                                                   if (_jspx_meth_c_005fout_005f19(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4395 */                                                   out.write(39);
/* 4396 */                                                   out.write(44);
/* 4397 */                                                   out.write(39);
/* 4398 */                                                   if (_jspx_meth_c_005fout_005f20(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4400 */                                                   out.write(39);
/* 4401 */                                                   out.write(44);
/* 4402 */                                                   out.write(39);
/* 4403 */                                                   if (_jspx_meth_c_005fout_005f21(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4405 */                                                   out.write(39);
/* 4406 */                                                   out.write(44);
/* 4407 */                                                   out.write(39);
/* 4408 */                                                   if (_jspx_meth_c_005fout_005f22(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4410 */                                                   out.write("')\"   class=\"new-left-links\">\n\t\t    <img src=\"");
/* 4411 */                                                   if (_jspx_meth_c_005fout_005f23(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4413 */                                                   out.write("\" border=\"0\"/>&nbsp;");
/* 4414 */                                                   if (_jspx_meth_c_005fout_005f24(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                   }
/* 4416 */                                                   out.write("\n\t\t  </a>\n\t     </td>\n\t  ");
/* 4417 */                                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 4418 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 4422 */                                               if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 4423 */                                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/*      */                                                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                               }
/* 4426 */                                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 4427 */                                               out.write("\n\t</tr>\n\t");
/* 4428 */                                               int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4429 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 4433 */                                           if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4441 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/*      */                                         }
/*      */                                         catch (Throwable _jspx_exception)
/*      */                                         {
/*      */                                           for (;;)
/*      */                                           {
/* 4437 */                                             int tmp13846_13845 = 0; int[] tmp13846_13843 = _jspx_push_body_count_c_005fforEach_005f0; int tmp13848_13847 = tmp13846_13843[tmp13846_13845];tmp13846_13843[tmp13846_13845] = (tmp13848_13847 - 1); if (tmp13848_13847 <= 0) break;
/* 4438 */                                             out = _jspx_page_context.popBody(); }
/* 4439 */                                           _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                         } finally {
/* 4441 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 4442 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                         }
/* 4444 */                                         out.write("\n    ");
/* 4445 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4446 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4450 */                                     if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4451 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                                     }
/*      */                                     
/* 4454 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4455 */                                     out.write("\n    ");
/*      */                                     
/* 4457 */                                     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4458 */                                     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 4459 */                                     _jspx_th_c_005fif_005f16.setParent(_jspx_th_c_005fif_005f3);
/*      */                                     
/* 4461 */                                     _jspx_th_c_005fif_005f16.setTest("${showAlarmHistory == true }");
/* 4462 */                                     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 4463 */                                     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                                       for (;;) {
/* 4465 */                                         out.write("\n    <tr>\n    <td height=\"21\" class=\"");
/* 4466 */                                         out.print(displayClass);
/* 4467 */                                         out.write("\" title=\"");
/* 4468 */                                         if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                                           return;
/* 4470 */                                         out.write("\">\n    <a href=\"javascript:void()\" onclick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 4471 */                                         out.print(resourceid_poll + "_" + healthid);
/* 4472 */                                         out.write("&monitortype=");
/* 4473 */                                         out.print(resourcetype_poll);
/* 4474 */                                         out.write("')\" class=\"new-left-links\"><img src=\"/images/icon_alarm_history.png\" border=\"0\" /> &nbsp;");
/* 4475 */                                         out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 4476 */                                         out.write("</a>\n    </td>\n    </tr>\n    ");
/* 4477 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 4478 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4482 */                                     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 4483 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                                     }
/*      */                                     
/* 4486 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 4487 */                                     out.write("\n    ");
/* 4488 */                                     out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                                     
/* 4490 */                                     if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                                     {
/* 4492 */                                       Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 4493 */                                       String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                                       
/* 4495 */                                       String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 4496 */                                       String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 4497 */                                       if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                                       {
/* 4499 */                                         if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                                         {
/*      */ 
/* 4502 */                                           out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4503 */                                           out.print(ciInfoUrl);
/* 4504 */                                           out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 4505 */                                           out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 4506 */                                           out.write("</a></td>");
/* 4507 */                                           out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4508 */                                           out.print(ciRLUrl);
/* 4509 */                                           out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 4510 */                                           out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 4511 */                                           out.write("</a></td>");
/* 4512 */                                           out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                                         }
/* 4516 */                                         else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                                         {
/*      */ 
/* 4519 */                                           out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 4520 */                                           out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 4521 */                                           out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4522 */                                           out.print(ciInfoUrl);
/* 4523 */                                           out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 4524 */                                           out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 4525 */                                           out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4526 */                                           out.print(ciRLUrl);
/* 4527 */                                           out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 4528 */                                           out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 4529 */                                           out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                                         }
/*      */                                       }
/*      */                                     }
/*      */                                     
/* 4534 */                                     out.write("\n \n \n\n");
/* 4535 */                                     out.write("\n</table>\n");
/* 4536 */                                     if (!mon_type.equals("QueryMonitor")) {
/* 4537 */                                       out.write("\n\t</div>\n\t</div>\n\t</td>\n\t</tr>\n\t</table>\n\t\n\t\n");
/*      */                                     } else {
/* 4539 */                                       out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 4540 */                                       out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 4541 */                                       out.write("</td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"50%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4542 */                                       if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                         return;
/* 4544 */                                       out.write("&attributeid=");
/* 4545 */                                       out.print(healthid);
/* 4546 */                                       out.write("')\" class=\"new-left-links\">");
/* 4547 */                                       out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4548 */                                       out.write("</a> </td>\n    <td ><a  href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4549 */                                       if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                         return;
/* 4551 */                                       out.write("&attributeid=");
/* 4552 */                                       out.print(healthid);
/* 4553 */                                       out.write("')\">");
/* 4554 */                                       out.print(getSeverityImageForHealth(alert.getProperty(request.getParameter("resourceid") + "#" + healthid)));
/* 4555 */                                       out.write("</a></td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"50%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4556 */                                       if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                         return;
/* 4558 */                                       out.write("&attributeid=");
/* 4559 */                                       out.print(availabilityid);
/* 4560 */                                       out.write("')\" class=\"new-left-links\">");
/* 4561 */                                       out.print(FormatUtil.getString("Availability"));
/* 4562 */                                       out.write("</a> </td>\n    <td ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4563 */                                       if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                         return;
/* 4565 */                                       out.write("&attributeid=");
/* 4566 */                                       out.print(availabilityid);
/* 4567 */                                       out.write("')\">");
/* 4568 */                                       out.print(getSeverityImageForAvailability(alert.getProperty(request.getParameter("resourceid") + "#" + availabilityid)));
/* 4569 */                                       out.write("</a></td>\n  </tr>\n</table>\n\n\n\n\n");
/* 4570 */                                       out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                                       
/*      */ 
/*      */ 
/* 4574 */                                       boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 4575 */                                       if (EnterpriseUtil.isIt360MSPEdition)
/*      */                                       {
/* 4577 */                                         showAssociatedBSG = false;
/*      */                                         
/*      */ 
/*      */ 
/* 4581 */                                         CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 4582 */                                         CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 4583 */                                         String loginName = request.getUserPrincipal().getName();
/* 4584 */                                         CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                                         
/* 4586 */                                         if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                                         {
/* 4588 */                                           showAssociatedBSG = true;
/*      */                                         }
/*      */                                       }
/* 4591 */                                       String monitorType = request.getParameter("type");
/* 4592 */                                       ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 4593 */                                       boolean mon = conf1.isConfMonitor(monitorType);
/* 4594 */                                       if (showAssociatedBSG)
/*      */                                       {
/* 4596 */                                         Hashtable associatedmgs = new Hashtable();
/* 4597 */                                         String resId = request.getParameter("resourceid");
/* 4598 */                                         request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 4599 */                                         if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                                         {
/* 4601 */                                           mon = false;
/*      */                                         }
/*      */                                         
/* 4604 */                                         if (!mon)
/*      */                                         {
/* 4606 */                                           out.write(10);
/* 4607 */                                           out.write(10);
/*      */                                           
/* 4609 */                                           IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4610 */                                           _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 4611 */                                           _jspx_th_c_005fif_005f17.setParent(_jspx_th_c_005fif_005f3);
/*      */                                           
/* 4613 */                                           _jspx_th_c_005fif_005f17.setTest("${!empty associatedmgs}");
/* 4614 */                                           int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 4615 */                                           if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                                             for (;;) {
/* 4617 */                                               out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 4618 */                                               out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 4619 */                                               out.write("</td>\n        </tr>\n        ");
/*      */                                               
/* 4621 */                                               ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4622 */                                               _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 4623 */                                               _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f17);
/*      */                                               
/* 4625 */                                               _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                                               
/* 4627 */                                               _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                                               
/* 4629 */                                               _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 4630 */                                               int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                               try {
/* 4632 */                                                 int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 4633 */                                                 if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                                   for (;;) {
/* 4635 */                                                     out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 4636 */                                                     if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4694 */                                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4695 */                                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                     }
/* 4638 */                                                     out.write("&method=showApplication\" title=\"");
/* 4639 */                                                     if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4694 */                                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4695 */                                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                     }
/* 4641 */                                                     out.write("\"  class=\"new-left-links\">\n         ");
/* 4642 */                                                     if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4694 */                                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4695 */                                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                     }
/* 4644 */                                                     out.write("\n    \t");
/* 4645 */                                                     out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4646 */                                                     out.write("\n         </a></td>\n        <td>");
/*      */                                                     
/* 4648 */                                                     PresentTag _jspx_th_logic_005fpresent_005f15 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4649 */                                                     _jspx_th_logic_005fpresent_005f15.setPageContext(_jspx_page_context);
/* 4650 */                                                     _jspx_th_logic_005fpresent_005f15.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                                     
/* 4652 */                                                     _jspx_th_logic_005fpresent_005f15.setRole("ADMIN");
/* 4653 */                                                     int _jspx_eval_logic_005fpresent_005f15 = _jspx_th_logic_005fpresent_005f15.doStartTag();
/* 4654 */                                                     if (_jspx_eval_logic_005fpresent_005f15 != 0) {
/*      */                                                       for (;;) {
/* 4656 */                                                         out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 4657 */                                                         if (_jspx_meth_c_005fout_005f33(_jspx_th_logic_005fpresent_005f15, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4694 */                                                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4695 */                                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                         }
/* 4659 */                                                         out.write(39);
/* 4660 */                                                         out.write(44);
/* 4661 */                                                         out.write(39);
/* 4662 */                                                         out.print(resId);
/* 4663 */                                                         out.write(39);
/* 4664 */                                                         out.write(44);
/* 4665 */                                                         out.write(39);
/* 4666 */                                                         out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4667 */                                                         out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 4668 */                                                         out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4669 */                                                         out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 4670 */                                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f15.doAfterBody();
/* 4671 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/*      */                                                     }
/* 4675 */                                                     if (_jspx_th_logic_005fpresent_005f15.doEndTag() == 5) {
/* 4676 */                                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15);
/*      */                                                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4694 */                                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 4695 */                                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                     }
/* 4679 */                                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15);
/* 4680 */                                                     out.write("</td>\n        </tr>\n\t");
/* 4681 */                                                     int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4682 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 4686 */                                                 if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4694 */                                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 4695 */                                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                 }
/*      */                                               }
/*      */                                               catch (Throwable _jspx_exception)
/*      */                                               {
/*      */                                                 for (;;)
/*      */                                                 {
/* 4690 */                                                   int tmp15805_15804 = 0; int[] tmp15805_15802 = _jspx_push_body_count_c_005fforEach_005f1; int tmp15807_15806 = tmp15805_15802[tmp15805_15804];tmp15805_15802[tmp15805_15804] = (tmp15807_15806 - 1); if (tmp15807_15806 <= 0) break;
/* 4691 */                                                   out = _jspx_page_context.popBody(); }
/* 4692 */                                                 _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                               } finally {
/* 4694 */                                                 _jspx_th_c_005fforEach_005f1.doFinally();
/* 4695 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                               }
/* 4697 */                                               out.write("\n      </table>\n ");
/* 4698 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 4699 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 4703 */                                           if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 4704 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                                           }
/*      */                                           
/* 4707 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 4708 */                                           out.write(10);
/* 4709 */                                           out.write(32);
/*      */                                           
/* 4711 */                                           IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4712 */                                           _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 4713 */                                           _jspx_th_c_005fif_005f18.setParent(_jspx_th_c_005fif_005f3);
/*      */                                           
/* 4715 */                                           _jspx_th_c_005fif_005f18.setTest("${empty associatedmgs}");
/* 4716 */                                           int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 4717 */                                           if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                                             for (;;) {
/* 4719 */                                               out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 4720 */                                               out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 4721 */                                               out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 4722 */                                               out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 4723 */                                               out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 4724 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 4725 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 4729 */                                           if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 4730 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                                           }
/*      */                                           
/* 4733 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 4734 */                                           out.write(10);
/* 4735 */                                           out.write(32);
/* 4736 */                                           out.write(10);
/*      */ 
/*      */                                         }
/* 4739 */                                         else if (mon)
/*      */                                         {
/*      */ 
/*      */ 
/* 4743 */                                           out.write(10);
/*      */                                           
/* 4745 */                                           IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4746 */                                           _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 4747 */                                           _jspx_th_c_005fif_005f19.setParent(_jspx_th_c_005fif_005f3);
/*      */                                           
/* 4749 */                                           _jspx_th_c_005fif_005f19.setTest("${!empty associatedmgs}");
/* 4750 */                                           int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 4751 */                                           if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                                             for (;;) {
/* 4753 */                                               out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 4754 */                                               if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f19, _jspx_page_context))
/*      */                                                 return;
/* 4756 */                                               out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                                               
/* 4758 */                                               ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4759 */                                               _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 4760 */                                               _jspx_th_c_005fforEach_005f2.setParent(_jspx_th_c_005fif_005f19);
/*      */                                               
/* 4762 */                                               _jspx_th_c_005fforEach_005f2.setVar("ha");
/*      */                                               
/* 4764 */                                               _jspx_th_c_005fforEach_005f2.setItems("${associatedmgs}");
/*      */                                               
/* 4766 */                                               _jspx_th_c_005fforEach_005f2.setVarStatus("status");
/* 4767 */                                               int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */                                               try {
/* 4769 */                                                 int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 4770 */                                                 if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */                                                   for (;;) {
/* 4772 */                                                     out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 4773 */                                                     if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4834 */                                                       _jspx_th_c_005fforEach_005f2.doFinally();
/* 4835 */                                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                                     }
/* 4775 */                                                     out.write("&method=showApplication\" title=\"");
/* 4776 */                                                     if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4834 */                                                       _jspx_th_c_005fforEach_005f2.doFinally();
/* 4835 */                                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                                     }
/* 4778 */                                                     out.write("\"  class=\"staticlinks\">\n         ");
/* 4779 */                                                     if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4834 */                                                       _jspx_th_c_005fforEach_005f2.doFinally();
/* 4835 */                                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                                     }
/* 4781 */                                                     out.write("\n    \t");
/* 4782 */                                                     out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4783 */                                                     out.write("</a></span>\t\n\t\t ");
/*      */                                                     
/* 4785 */                                                     PresentTag _jspx_th_logic_005fpresent_005f16 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4786 */                                                     _jspx_th_logic_005fpresent_005f16.setPageContext(_jspx_page_context);
/* 4787 */                                                     _jspx_th_logic_005fpresent_005f16.setParent(_jspx_th_c_005fforEach_005f2);
/*      */                                                     
/* 4789 */                                                     _jspx_th_logic_005fpresent_005f16.setRole("ADMIN");
/* 4790 */                                                     int _jspx_eval_logic_005fpresent_005f16 = _jspx_th_logic_005fpresent_005f16.doStartTag();
/* 4791 */                                                     if (_jspx_eval_logic_005fpresent_005f16 != 0) {
/*      */                                                       for (;;) {
/* 4793 */                                                         out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 4794 */                                                         if (_jspx_meth_c_005fout_005f37(_jspx_th_logic_005fpresent_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4834 */                                                           _jspx_th_c_005fforEach_005f2.doFinally();
/* 4835 */                                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                                         }
/* 4796 */                                                         out.write(39);
/* 4797 */                                                         out.write(44);
/* 4798 */                                                         out.write(39);
/* 4799 */                                                         out.print(resId);
/* 4800 */                                                         out.write(39);
/* 4801 */                                                         out.write(44);
/* 4802 */                                                         out.write(39);
/* 4803 */                                                         out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4804 */                                                         out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 4805 */                                                         out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4806 */                                                         out.write("\"  title=\"");
/* 4807 */                                                         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_logic_005fpresent_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4834 */                                                           _jspx_th_c_005fforEach_005f2.doFinally();
/* 4835 */                                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                                         }
/* 4809 */                                                         out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 4810 */                                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f16.doAfterBody();
/* 4811 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/*      */                                                     }
/* 4815 */                                                     if (_jspx_th_logic_005fpresent_005f16.doEndTag() == 5) {
/* 4816 */                                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f16);
/*      */                                                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4834 */                                                       _jspx_th_c_005fforEach_005f2.doFinally();
/* 4835 */                                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                                     }
/* 4819 */                                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f16);
/* 4820 */                                                     out.write("\n\n\t\t \t");
/* 4821 */                                                     int evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 4822 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 4826 */                                                 if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/*      */                                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4834 */                                                   _jspx_th_c_005fforEach_005f2.doFinally();
/* 4835 */                                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                                 }
/*      */                                               }
/*      */                                               catch (Throwable _jspx_exception)
/*      */                                               {
/*      */                                                 for (;;)
/*      */                                                 {
/* 4830 */                                                   int tmp16831_16830 = 0; int[] tmp16831_16828 = _jspx_push_body_count_c_005fforEach_005f2; int tmp16833_16832 = tmp16831_16828[tmp16831_16830];tmp16831_16828[tmp16831_16830] = (tmp16833_16832 - 1); if (tmp16833_16832 <= 0) break;
/* 4831 */                                                   out = _jspx_page_context.popBody(); }
/* 4832 */                                                 _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */                                               } finally {
/* 4834 */                                                 _jspx_th_c_005fforEach_005f2.doFinally();
/* 4835 */                                                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */                                               }
/* 4837 */                                               out.write("\n\t\n\t\t\t</td>\n\t ");
/* 4838 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 4839 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 4843 */                                           if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 4844 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                                           }
/*      */                                           
/* 4847 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 4848 */                                           out.write(10);
/* 4849 */                                           out.write(32);
/* 4850 */                                           if (_jspx_meth_c_005fif_005f20(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                             return;
/* 4852 */                                           out.write(32);
/* 4853 */                                           out.write(10);
/*      */                                         }
/*      */                                         
/*      */                                       }
/* 4857 */                                       else if (mon)
/*      */                                       {
/*      */ 
/* 4860 */                                         out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4861 */                                         if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                           return;
/* 4863 */                                         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                                       }
/*      */                                       
/*      */ 
/* 4867 */                                       out.write(9);
/* 4868 */                                       out.write(9);
/* 4869 */                                       out.write(10);
/*      */                                     }
/* 4871 */                                     out.write(10);
/* 4872 */                                     out.write("\n\t</td>\n</tr>\n</table>\n");
/* 4873 */                                     JspRuntimeLibrary.include(request, response, "/jsp/includes/CommonDetailsHeader.jsp" + ("/jsp/includes/CommonDetailsHeader.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitorname", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf((String)request.getAttribute("monitorname")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("healthId", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(ess_atts.getProperty("Health")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("availabilityId", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(ess_atts.getProperty("Availability")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("isConfMonitor", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("TabId", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(m), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("collectionTime", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf((String)request.getAttribute("maxcollectiontime")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resID", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resID), request.getCharacterEncoding()), out, false);
/* 4874 */                                     out.write(9);
/* 4875 */                                     out.write(10);
/*      */                                     try
/*      */                                     {
/* 4878 */                                       out.write(10);
/*      */                                       
/* 4880 */                                       if ((tabConfiguration != null) && (!tabConfiguration.equals("null")) && (noOftabs != 0)) {
/* 4881 */                                         out.write("\n\t<br>\n\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n  \t\t\t<tbody>\n    \t\t\t<tr class=\"tabBtmLine\">\n     \t\t\t\t <td nowrap=\"nowrap\" id=\"mytd\">\n      \t\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" id=\"InnerTab\">\n         \t\t\t\t\t\t <tbody>\n           \t\t\t\t\t\t\t <tr>\n              \t\t\t\t\t\t\t<td width=\"17\">&nbsp;&nbsp;&nbsp;&nbsp;</td>\n              \t\t\t\t\t\t\t");
/* 4882 */                                         String selectedtab = (String)tabConfiguration.get(m);
/* 4883 */                                         for (int i = 0; i < noOftabs; ???++) {
/* 4884 */                                           String title = (String)tabConfiguration.get(i);
/*      */                                           
/* 4886 */                                           if (((tabToBeRemoved == null) || ((tabToBeRemoved != null) && (!tabToBeRemoved.contains(title)))) && ((uIElementsToRemove == null) || ((uIElementsToRemove != null) && (!uIElementsToRemove.contains("UITAB_" + title)))))
/*      */                                           {
/*      */ 
/* 4889 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\" id=\"");
/* 4890 */                                             out.print(title);
/* 4891 */                                             out.write("Tab\"  title=\"");
/* 4892 */                                             out.print(FormatUtil.getString(title));
/* 4893 */                                             out.write("\">\n\t                \t\t\t\t\t\t\t  <tbody>\n\t\t                \t\t\t\t\t\t\t  <tr>\n\t\t\t\t\t\t\t\t\t                      <td id=\"Tab_");
/* 4894 */                                             out.print(i);
/* 4895 */                                             out.write("_left\" class=\"");
/* 4896 */                                             out.print(selectedtab.equals(title) ? "tbSelected_Left" : "tbUnselected_Left");
/* 4897 */                                             out.write("\" ><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n\t\t\t\t\t\t\t\t\t                      <td  id=\"Tab_");
/* 4898 */                                             out.print(i);
/* 4899 */                                             out.write("_middle\" style=\"padding-left:5px;padding-right:5px\" onclick=\"gettabData('");
/* 4900 */                                             out.print(i);
/* 4901 */                                             out.write(39);
/* 4902 */                                             out.write(44);
/* 4903 */                                             out.write(39);
/* 4904 */                                             out.print(request.getParameter("type"));
/* 4905 */                                             out.write(39);
/* 4906 */                                             out.write(44);
/* 4907 */                                             out.write(39);
/* 4908 */                                             out.print(resID);
/* 4909 */                                             out.write(39);
/* 4910 */                                             out.write(44);
/* 4911 */                                             out.write(39);
/* 4912 */                                             out.print(serverName);
/* 4913 */                                             out.write(39);
/* 4914 */                                             out.write(44);
/* 4915 */                                             out.write(39);
/* 4916 */                                             out.print((String)request.getAttribute("reloadperiod"));
/* 4917 */                                             out.write("');\"  class=\"");
/* 4918 */                                             out.print(selectedtab.equals(title) ? "tbSelected_Middle" : "tbUnselected_Middle");
/* 4919 */                                             out.write("\">\n\t\t\t\t\t\t\t\t\t                   \t  \t <span id=\"Tab_");
/* 4920 */                                             out.print(i);
/* 4921 */                                             out.write("_text\" class=\"");
/* 4922 */                                             out.print(selectedtab.equals(title) ? "tabLinkActive" : "bodytext");
/* 4923 */                                             out.write(34);
/* 4924 */                                             out.write(62);
/* 4925 */                                             out.print(FormatUtil.getString(title));
/* 4926 */                                             out.write("</span>\n\t\t                      \t\t\t\t\t\t\t </td>\n\t\t                      \t\t\t\t\t\t\t <td  id=\"Tab_");
/* 4927 */                                             out.print(i);
/* 4928 */                                             out.write("_right\" class=\"");
/* 4929 */                                             out.print(selectedtab.equals(title) ? "tbSelected_Right" : "tbUnselected_Right");
/* 4930 */                                             out.write("\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n\t\t                   \t\t\t\t\t\t\t</tr>\n\t                  \t\t\t\t\t\t\t</tbody>\n              \t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t");
/*      */                                           } }
/* 4932 */                                         out.write("\n              \t\t\t\t\t\t</tr>\n              \t\t\t\t\t</tbody>\n              \t\t\t\t</table>\n              \t\t\t</td>\n              \t\t</tr>\n              \t</tbody>\n              </table>            \t\n");
/*      */                                       }
/* 4934 */                                     } catch (Exception exc) { exc.printStackTrace();
/*      */                                     } } catch (Exception e) {}
/* 4936 */                                   out.write(10);
/* 4937 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4938 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4942 */                               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4943 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                               }
/*      */                               
/* 4946 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4947 */                               out.write("\n<form name=\"tabsForm\" id=\"tabsForm\" style=\"display:inline\">\n\t<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 4948 */                               out.print(resourceName);
/* 4949 */                               out.write("\"/>\n \t<input type=\"hidden\" name=\"type\" value=\"");
/* 4950 */                               out.print(resourcetype);
/* 4951 */                               out.write("\"/>\n \t<input type=\"hidden\" name=\"original_type\" value=\"");
/* 4952 */                               out.print(original_type1);
/* 4953 */                               out.write("\"/>\n \t<input type=\"hidden\"  name=\"baseid\" value=\"");
/* 4954 */                               out.print(baseid1);
/* 4955 */                               out.write("\"/>\n \t<input type=\"hidden\"  id=\"resourceid\" name=\"resourceid\" value=\"");
/* 4956 */                               out.print(resID);
/* 4957 */                               out.write("\"/>\n \t<input type=\"hidden\" name=\"monitorname\" value=\"");
/* 4958 */                               out.print(URLEncoder.encode((String)request.getAttribute("monitorname")));
/* 4959 */                               out.write("\"/>\n \t<input type=\"hidden\" name=\"moname\" value=\"");
/* 4960 */                               out.print(moname);
/* 4961 */                               out.write("\"/> \t\n \t<input type=\"hidden\" name=\"tabId\" value=\"");
/* 4962 */                               out.print(m);
/* 4963 */                               out.write("\"/>\n \t<input type=\"hidden\" name=\"method\" value=\"\"/>\n \t<input type=\"hidden\" name=\"granularity\" value=\"");
/* 4964 */                               if (_jspx_meth_c_005fout_005f38(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 4966 */                               out.write("\"/>\n \t<input type=\"hidden\" name=\"TimeUnit\" value=\"");
/* 4967 */                               if (_jspx_meth_c_005fout_005f39(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 4969 */                               out.write("\" />\n \t<input type=\"hidden\" name=\"customDate\" value=\"\"/> \n \t<input type=\"hidden\" name=\"weekUnit\" value=\"\"/> \n \t<input type=\"hidden\" name=\"monthUnit\" value=\"\"/> \n \t<input type=\"hidden\" name=\"tableid\" value=\"\"/>\n \t<input type=\"hidden\" name=\"tableName\" value=\"\"/>\n\t<input type=\"hidden\" name=\"sqlmanid\" value=\"\"/>\n\t<input type=\"hidden\" name=\"scriptid\" value=\"");
/* 4970 */                               out.print(resID);
/* 4971 */                               out.write("\"/>\n\t<input type=\"hidden\" name=\"rowid\" value=\"\"/>\n\t<input type=\"hidden\" name=\"fromConfDetails\" value=\"true\"/>\n\t<input type=\"hidden\" name=\"collectionTime\" value=\"");
/* 4972 */                               out.print((String)request.getAttribute("maxcollectiontime"));
/* 4973 */                               out.write("\"/> \n\t<input type=\"hidden\" name=\"creationTime\" value=\"");
/* 4974 */                               out.print((Long)request.getAttribute("creationTime"));
/* 4975 */                               out.write("\"/> \n\t<input type=\"hidden\" name=\"pollinterval\" value=\"");
/* 4976 */                               out.print((String)request.getAttribute("pollinterval"));
/* 4977 */                               out.write("\"/> \n\t<input type=\"hidden\" name=\"isParentMonitor\" value=\"");
/* 4978 */                               out.print(isParentMonitor);
/* 4979 */                               out.write("\"/>\n</form>\n<div id=\"confDetailsloadingdiv\" align=\"right\" class=\"confloading\" title=\"Loading\"><img alt=\"Loading\" src='/images/loading.gif'/><span class=\"bodytext\" style=\"position:relative; bottom:15px; left:10px;\">");
/* 4980 */                               if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 4982 */                               out.write("</span></div>\n<div id=\"monitorInfoDiv\" style=\"display:none\"></div>\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 4983 */                               JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 4984 */                               out.write("</td></tr></table>\n");
/*      */                               
/* 4986 */                               IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4987 */                               _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 4988 */                               _jspx_th_c_005fif_005f21.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 4990 */                               _jspx_th_c_005fif_005f21.setTest("${(not empty param.fromTab)}");
/* 4991 */                               int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 4992 */                               if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                                 for (;;) {
/* 4994 */                                   out.write("\t\n<span  id=\"quickMsg\">");
/* 4995 */                                   out.print((String)request.getAttribute("ReportPeriodMessage"));
/* 4996 */                                   out.write("</span><br>\n");
/* 4997 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 4998 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 5002 */                               if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 5003 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                               }
/*      */                               
/* 5006 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 5007 */                               out.write("\n<div id=\"groupContent\"width=\"100%\">\n\t<div id=\"monitorInformwithTab\" style=\"display: none\" >\n");
/*      */                               
/* 5009 */                               IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5010 */                               _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 5011 */                               _jspx_th_c_005fif_005f22.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 5013 */                               _jspx_th_c_005fif_005f22.setTest("${(not empty param.fromTab)}");
/* 5014 */                               int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 5015 */                               if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                                 for (;;) {
/* 5017 */                                   out.write("\t\n\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n");
/* 5018 */                                   if ((((Boolean)request.getAttribute("ShowHostPerformance")).booleanValue()) && (m == 1)) {
/* 5019 */                                     out.write("\t\n\t<td width=\"49%\" valign=\"top\">  ");
/* 5020 */                                     out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 5021 */                                     DialChartSupport dialGraph = null;
/* 5022 */                                     dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 5023 */                                     if (dialGraph == null) {
/* 5024 */                                       dialGraph = new DialChartSupport();
/* 5025 */                                       _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                                     }
/* 5027 */                                     out.write(10);
/*      */                                     
/*      */                                     try
/*      */                                     {
/* 5031 */                                       String hostos = (String)systeminfo.get("HOSTOS");
/* 5032 */                                       String hostname = (String)systeminfo.get("HOSTNAME");
/* 5033 */                                       String hostid = (String)systeminfo.get("host_resid");
/* 5034 */                                       boolean isConf = false;
/* 5035 */                                       if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 5036 */                                         isConf = true;
/*      */                                       }
/* 5038 */                                       RepairTables rt = new RepairTables();
/* 5039 */                                       Properties property = new Properties();
/* 5040 */                                       if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                                       {
/* 5042 */                                         property = RepairTables.getValuesForHost(hostname, hostos);
/* 5043 */                                         if ((property != null) && (property.size() > 0))
/*      */                                         {
/* 5045 */                                           String cpuid = property.getProperty("cpuid");
/* 5046 */                                           String memid = property.getProperty("memid");
/* 5047 */                                           String diskid = property.getProperty("diskid");
/* 5048 */                                           String cpuvalue = property.getProperty("CPU Utilization");
/* 5049 */                                           String memvalue = property.getProperty("Memory Utilization");
/* 5050 */                                           String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 5051 */                                           String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 5052 */                                           String diskvalue = property.getProperty("Disk Utilization");
/* 5053 */                                           String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                                           
/* 5055 */                                           if (!isConf) {
/* 5056 */                                             out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 5057 */                                             out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 5058 */                                             out.write(45);
/* 5059 */                                             if (systeminfo.get("host_resid") != null) {
/* 5060 */                                               out.write("<a href=\"showresource.do?resourceid=");
/* 5061 */                                               out.print(hostid);
/* 5062 */                                               out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5063 */                                               out.print(hostname);
/* 5064 */                                               out.write("</a>");
/* 5065 */                                             } else { out.println(hostname); }
/* 5066 */                                             out.write("</td>\t");
/* 5067 */                                             out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 5068 */                                             out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5069 */                                             out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                                             
/*      */ 
/* 5072 */                                             if (cpuvalue != null)
/*      */                                             {
/*      */ 
/* 5075 */                                               dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5076 */                                               out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5077 */                                               out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5078 */                                               out.write(45);
/* 5079 */                                               out.print(cpuvalue);
/* 5080 */                                               out.write(" %'>\n\n");
/*      */                                               
/* 5082 */                                               DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5083 */                                               _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 5084 */                                               _jspx_th_awolf_005fdialchart_005f0.setParent(_jspx_th_c_005fif_005f22);
/*      */                                               
/* 5086 */                                               _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                               
/* 5088 */                                               _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                               
/* 5090 */                                               _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                               
/* 5092 */                                               _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                               
/* 5094 */                                               _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                               
/* 5096 */                                               _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                               
/* 5098 */                                               _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                               
/* 5100 */                                               _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                               
/* 5102 */                                               _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                                               
/* 5104 */                                               _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 5105 */                                               int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 5106 */                                               if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 5107 */                                                 if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5108 */                                                   out = _jspx_page_context.pushBody();
/* 5109 */                                                   _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 5110 */                                                   _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5113 */                                                   out.write(10);
/* 5114 */                                                   int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 5115 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5118 */                                                 if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5119 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5122 */                                               if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 5123 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                               }
/*      */                                               
/* 5126 */                                               this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 5127 */                                               out.write("\n         </td>\n            ");
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 5131 */                                               out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5132 */                                               out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5133 */                                               out.write(32);
/* 5134 */                                               out.write(62);
/* 5135 */                                               out.write(10);
/* 5136 */                                               out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5137 */                                               out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                                             }
/* 5139 */                                             out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5140 */                                             if (cpuvalue != null)
/*      */                                             {
/* 5142 */                                               out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5143 */                                               out.print(hostid);
/* 5144 */                                               out.write("&attributeid=");
/* 5145 */                                               out.print(cpuid);
/* 5146 */                                               out.write("&period=-7')\" class='bodytextbold'>");
/* 5147 */                                               out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5148 */                                               out.write(32);
/* 5149 */                                               out.write(45);
/* 5150 */                                               out.write(32);
/* 5151 */                                               out.print(cpuvalue);
/* 5152 */                                               out.write("</a> %\n");
/*      */                                             }
/* 5154 */                                             out.write("\n  </td>\n       </tr>\n       </table>");
/* 5155 */                                             out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5156 */                                             out.write("</td>\n      <td width=\"30%\"> ");
/* 5157 */                                             out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5158 */                                             out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                                             
/* 5160 */                                             if (memvalue != null)
/*      */                                             {
/*      */ 
/* 5163 */                                               dialGraph.setValue(Long.parseLong(memvalue));
/* 5164 */                                               out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5165 */                                               out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5166 */                                               out.write(45);
/* 5167 */                                               out.print(memvalue);
/* 5168 */                                               out.write(" %' >\n\n");
/*      */                                               
/* 5170 */                                               DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5171 */                                               _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 5172 */                                               _jspx_th_awolf_005fdialchart_005f1.setParent(_jspx_th_c_005fif_005f22);
/*      */                                               
/* 5174 */                                               _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                               
/* 5176 */                                               _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                               
/* 5178 */                                               _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                               
/* 5180 */                                               _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                               
/* 5182 */                                               _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                               
/* 5184 */                                               _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                               
/* 5186 */                                               _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                               
/* 5188 */                                               _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                               
/* 5190 */                                               _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                                               
/* 5192 */                                               _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 5193 */                                               int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 5194 */                                               if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 5195 */                                                 if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5196 */                                                   out = _jspx_page_context.pushBody();
/* 5197 */                                                   _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 5198 */                                                   _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5201 */                                                   out.write(32);
/* 5202 */                                                   int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 5203 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5206 */                                                 if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5207 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5210 */                                               if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 5211 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                               }
/*      */                                               
/* 5214 */                                               this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 5215 */                                               out.write(32);
/* 5216 */                                               out.write("\n            </td>\n            ");
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 5220 */                                               out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5221 */                                               out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5222 */                                               out.write(" >\n\n");
/* 5223 */                                               out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5224 */                                               out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                             }
/* 5226 */                                             out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5227 */                                             if (memvalue != null)
/*      */                                             {
/* 5229 */                                               out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5230 */                                               out.print(hostid);
/* 5231 */                                               out.write("&attributeid=");
/* 5232 */                                               out.print(memid);
/* 5233 */                                               out.write("&period=-7')\" class='bodytextbold'>");
/* 5234 */                                               out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5235 */                                               out.write(45);
/* 5236 */                                               out.print(memvalue);
/* 5237 */                                               out.write("</a> %\n  ");
/*      */                                             }
/* 5239 */                                             out.write("\n  </td>\n       </tr>\n    </table>");
/* 5240 */                                             out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5241 */                                             out.write("</td>\n      <td width=\"30%\">");
/* 5242 */                                             out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5243 */                                             out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                                             
/*      */ 
/* 5246 */                                             if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                             {
/*      */ 
/*      */ 
/* 5250 */                                               dialGraph.setValue(Long.parseLong(diskvalue));
/* 5251 */                                               out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5252 */                                               out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 5253 */                                               out.write(45);
/* 5254 */                                               out.print(diskvalue);
/* 5255 */                                               out.write("%' >\n");
/*      */                                               
/* 5257 */                                               DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5258 */                                               _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 5259 */                                               _jspx_th_awolf_005fdialchart_005f2.setParent(_jspx_th_c_005fif_005f22);
/*      */                                               
/* 5261 */                                               _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                               
/* 5263 */                                               _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                               
/* 5265 */                                               _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                               
/* 5267 */                                               _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                               
/* 5269 */                                               _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                               
/* 5271 */                                               _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                               
/* 5273 */                                               _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                               
/* 5275 */                                               _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                               
/* 5277 */                                               _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                                               
/* 5279 */                                               _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 5280 */                                               int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 5281 */                                               if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 5282 */                                                 if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5283 */                                                   out = _jspx_page_context.pushBody();
/* 5284 */                                                   _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 5285 */                                                   _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5288 */                                                   out.write(32);
/* 5289 */                                                   int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 5290 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5293 */                                                 if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5294 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5297 */                                               if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 5298 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                               }
/*      */                                               
/* 5301 */                                               this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 5302 */                                               out.write(32);
/* 5303 */                                               out.write(32);
/* 5304 */                                               out.write("\n    </td>\n            ");
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 5308 */                                               out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5309 */                                               out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5310 */                                               out.write(32);
/* 5311 */                                               out.write(62);
/* 5312 */                                               out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5313 */                                               out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                             }
/* 5315 */                                             out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 5316 */                                             if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                             {
/* 5318 */                                               out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5319 */                                               out.print(hostid);
/* 5320 */                                               out.write("&attributeid=");
/* 5321 */                                               out.print(diskid);
/* 5322 */                                               out.write("&period=-7')\" class='bodytextbold'>");
/* 5323 */                                               out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5324 */                                               out.write(45);
/* 5325 */                                               out.print(diskvalue);
/* 5326 */                                               out.write("</a> %\n     ");
/*      */                                             }
/* 5328 */                                             out.write("\n  </td>\n  </tr>\n</table>");
/* 5329 */                                             out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5330 */                                             out.write("</td></tr></table>\n\n");
/*      */                                           } else {
/* 5332 */                                             out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 5333 */                                             out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 5334 */                                             out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 5335 */                                             out.print(systeminfo.get("host_resid"));
/* 5336 */                                             out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5337 */                                             out.print(hostname);
/* 5338 */                                             out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 5339 */                                             if (cpuvalue != null)
/*      */                                             {
/*      */ 
/* 5342 */                                               dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5343 */                                               out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                                               
/* 5345 */                                               DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5346 */                                               _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 5347 */                                               _jspx_th_awolf_005fdialchart_005f3.setParent(_jspx_th_c_005fif_005f22);
/*      */                                               
/* 5349 */                                               _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                                               
/* 5351 */                                               _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                                               
/* 5353 */                                               _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                                               
/* 5355 */                                               _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                                               
/* 5357 */                                               _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                                               
/* 5359 */                                               _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                                               
/* 5361 */                                               _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                                               
/* 5363 */                                               _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                                               
/* 5365 */                                               _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                                               
/* 5367 */                                               _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 5368 */                                               int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 5369 */                                               if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 5370 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                                               }
/*      */                                               
/* 5373 */                                               this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 5374 */                                               out.write("\n         </td>\n     ");
/*      */                                             }
/*      */                                             else {
/* 5377 */                                               out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5378 */                                               out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5379 */                                               out.write(39);
/* 5380 */                                               out.write(32);
/* 5381 */                                               out.write(62);
/* 5382 */                                               out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5383 */                                               out.write("\n \t\t</td>\n\t\t");
/*      */                                             }
/* 5385 */                                             if (memvalue != null) {
/* 5386 */                                               dialGraph.setValue(Long.parseLong(memvalue));
/* 5387 */                                               out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                               
/* 5389 */                                               DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5390 */                                               _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 5391 */                                               _jspx_th_awolf_005fdialchart_005f4.setParent(_jspx_th_c_005fif_005f22);
/*      */                                               
/* 5393 */                                               _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                                               
/* 5395 */                                               _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                                               
/* 5397 */                                               _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                                               
/* 5399 */                                               _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                                               
/* 5401 */                                               _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                                               
/* 5403 */                                               _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                                               
/* 5405 */                                               _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                                               
/* 5407 */                                               _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                                               
/* 5409 */                                               _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                                               
/* 5411 */                                               _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 5412 */                                               int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 5413 */                                               if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 5414 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                                               }
/*      */                                               
/* 5417 */                                               this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 5418 */                                               out.write("\n            </td>\n         ");
/*      */                                             }
/*      */                                             else {
/* 5421 */                                               out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5422 */                                               out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5423 */                                               out.write(39);
/* 5424 */                                               out.write(32);
/* 5425 */                                               out.write(62);
/* 5426 */                                               out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5427 */                                               out.write("\n \t\t</td>\n\t\t");
/*      */                                             }
/* 5429 */                                             if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5430 */                                               dialGraph.setValue(Long.parseLong(diskvalue));
/* 5431 */                                               out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                               
/* 5433 */                                               DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5434 */                                               _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 5435 */                                               _jspx_th_awolf_005fdialchart_005f5.setParent(_jspx_th_c_005fif_005f22);
/*      */                                               
/* 5437 */                                               _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                                               
/* 5439 */                                               _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                                               
/* 5441 */                                               _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                                               
/* 5443 */                                               _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                                               
/* 5445 */                                               _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                                               
/* 5447 */                                               _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                                               
/* 5449 */                                               _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                                               
/* 5451 */                                               _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                                               
/* 5453 */                                               _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                                               
/* 5455 */                                               _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 5456 */                                               int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 5457 */                                               if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 5458 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                                               }
/*      */                                               
/* 5461 */                                               this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 5462 */                                               out.write(32);
/* 5463 */                                               out.write("\n\t          </td>\n\t  ");
/*      */                                             }
/*      */                                             else {
/* 5466 */                                               out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5467 */                                               out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5468 */                                               out.write(39);
/* 5469 */                                               out.write(32);
/* 5470 */                                               out.write(62);
/* 5471 */                                               out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5472 */                                               out.write("\n \t\t</td>\n\t\t");
/*      */                                             }
/* 5474 */                                             out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5475 */                                             out.print(hostid);
/* 5476 */                                             out.write("&attributeid=");
/* 5477 */                                             out.print(cpuid);
/* 5478 */                                             out.write("&period=-7')\" class='tooltip'>");
/* 5479 */                                             out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5480 */                                             out.write(32);
/* 5481 */                                             out.write(45);
/* 5482 */                                             out.write(32);
/* 5483 */                                             if (cpuvalue != null) {
/* 5484 */                                               out.print(cpuvalue);
/*      */                                             }
/* 5486 */                                             out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5487 */                                             out.print(hostid);
/* 5488 */                                             out.write("&attributeid=");
/* 5489 */                                             out.print(memid);
/* 5490 */                                             out.write("&period=-7')\" class='tooltip'>");
/* 5491 */                                             out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5492 */                                             out.write(45);
/* 5493 */                                             if (memvalue != null) {
/* 5494 */                                               out.print(memvalue);
/*      */                                             }
/* 5496 */                                             out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5497 */                                             out.print(hostid);
/* 5498 */                                             out.write("&attributeid=");
/* 5499 */                                             out.print(diskid);
/* 5500 */                                             out.write("&period=-7')\" class='tooltip'>");
/* 5501 */                                             out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5502 */                                             out.write(45);
/* 5503 */                                             if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5504 */                                               out.print(diskvalue);
/*      */                                             }
/* 5506 */                                             out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                                           }
/* 5508 */                                           out.write(10);
/* 5509 */                                           out.write(10);
/*      */                                         }
/*      */                                         
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Exception e)
/*      */                                     {
/* 5516 */                                       e.printStackTrace();
/*      */                                     }
/* 5518 */                                     out.write(10);
/* 5519 */                                     out.write("</td>\n\t<td width=\"1%\">&nbsp;</td>\n\t<td width=\"49%\" valign=\"top\">\t<div id=\"availabilitydiv\"></div>\t<div id=\"performancediv\"></div>\t</td>\n");
/*      */                                   } else {
/* 5521 */                                     out.write("\n\t<td width=\"49%\" valign=\"top\">\t<div id=\"availabilitydiv\"></div></td>\n\t<td width=\"1%\">&nbsp;</td>\n\t<td width=\"49%\" valign=\"top\"><div id=\"performancediv\"></div>\t</td>\n");
/*      */                                   }
/* 5523 */                                   out.write("\n\t\t</tr>\n\t</table>\n");
/* 5524 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 5525 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 5529 */                               if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 5530 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                               }
/*      */                               
/* 5533 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 5534 */                               out.write("\n\t</div>\n\n");
/*      */                               
/* 5536 */                               IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5537 */                               _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 5538 */                               _jspx_th_c_005fif_005f23.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 5540 */                               _jspx_th_c_005fif_005f23.setTest("${(not empty param.fromTab)}");
/* 5541 */                               int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 5542 */                               if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */                                 for (;;) {
/* 5544 */                                   out.write(10);
/* 5545 */                                   HashMap groupData = new HashMap();
/* 5546 */                                   List groupList = new ArrayList();
/* 5547 */                                   HashMap attributeDetails = new HashMap();
/* 5548 */                                   HashMap groupDetails = new HashMap();
/* 5549 */                                   ArrayList attributesList = new ArrayList();
/* 5550 */                                   int tableid = 1;
/* 5551 */                                   int count = 1;
/* 5552 */                                   int graphCount = 1;
/* 5553 */                                   String seven_days_text = FormatUtil.getString("am.webclient.common.history.tooltip.text");
/* 5554 */                                   HashMap attributeValues = request.getAttribute("attributeValues") != null ? (HashMap)request.getAttribute("attributeValues") : null;
/* 5555 */                                   ArrayList configAttList = conf.getConfigidsList(resourcetype);
/* 5556 */                                   String actionPath = "";
/*      */                                   try {
/* 5558 */                                     String caption = "";
/* 5559 */                                     String gtype = "1";
/* 5560 */                                     String width1 = "99";
/* 5561 */                                     String imgwidth = "450";
/* 5562 */                                     String imght = "170";
/* 5563 */                                     String category = "";
/* 5564 */                                     String tableId = "";
/* 5565 */                                     groupData = (HashMap)request.getAttribute("groupData");
/* 5566 */                                     pageContext.setAttribute("groupData", groupData);
/* 5567 */                                     Cookie[] cookies = request.getCookies();
/* 5568 */                                     if (cookies != null)
/*      */                                     {
/* 5570 */                                       if (cookies.length > 0)
/*      */                                       {
/* 5572 */                                         for (int i = 0; i < cookies.length; i++) {
/* 5573 */                                           if (cookies[i].getName().equals("ScreenWidth")) {
/*      */                                             try {
/* 5575 */                                               if ((cookies[i].getValue() != null) && (!cookies[i].getValue().equals("null")) && (!cookies[i].getValue().trim().equals(""))) {
/* 5576 */                                                 scrWidth = Integer.parseInt(cookies[i].getValue());
/*      */                                               }
/*      */                                             } catch (Exception e) {
/* 5579 */                                               e.printStackTrace();
/*      */                                             }
/*      */                                           }
/*      */                                         }
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 5587 */                                     out.write("\n<div id=\"");
/* 5588 */                                     out.print("Group_tab" + m);
/* 5589 */                                     out.write("\" width=\"100%\" >\n\t");
/*      */                                     
/* 5591 */                                     actionPath = "/showCustom.do?method=showDataforConfs&resourceid=" + resID + "&resourcename=" + resourceName + "&type=" + resourcetype + "&original_type=" + original_type1 + "&moname=" + moname + "&tabId=" + (Integer)request.getAttribute("tabId") + "&baseid=" + baseid1 + "&monitorname=" + URLEncoder.encode((String)request.getAttribute("monitorname"));
/* 5592 */                                     request.setAttribute("actionPath", actionPath);
/*      */                                     
/* 5594 */                                     out.write(10);
/* 5595 */                                     out.write(10);
/* 5596 */                                     out.write(9);
/* 5597 */                                     out.write(10);
/* 5598 */                                     out.write(9);
/*      */                                     
/* 5600 */                                     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5601 */                                     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 5602 */                                     _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fif_005f23);
/* 5603 */                                     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 5604 */                                     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                                       for (;;) {
/* 5606 */                                         out.write(10);
/* 5607 */                                         out.write(9);
/*      */                                         
/* 5609 */                                         WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5610 */                                         _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 5611 */                                         _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                         
/* 5613 */                                         _jspx_th_c_005fwhen_005f9.setTest("${resourcetype eq 'VMWare ESX/ESXi'}");
/* 5614 */                                         int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 5615 */                                         if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                                           for (;;) {
/* 5617 */                                             out.write(10);
/* 5618 */                                             out.write(9);
/* 5619 */                                             out.write(9);
/*      */                                             
/* 5621 */                                             ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5622 */                                             _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 5623 */                                             _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fwhen_005f9);
/* 5624 */                                             int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 5625 */                                             if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                                               for (;;) {
/* 5627 */                                                 out.write("\n\t\t\t");
/*      */                                                 
/* 5629 */                                                 WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5630 */                                                 _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 5631 */                                                 _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                                 
/* 5633 */                                                 _jspx_th_c_005fwhen_005f10.setTest("${tabId == 8}");
/* 5634 */                                                 int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 5635 */                                                 if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                                   for (;;) {
/* 5637 */                                                     out.write("\n\t\t\t");
/* 5638 */                                                     JspRuntimeLibrary.include(request, response, "/jsp/VMStorageMapping.jsp" + ("/jsp/VMStorageMapping.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("moname", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(moname), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resID), request.getCharacterEncoding()), out, true);
/* 5639 */                                                     out.write("\n\t\t\t");
/* 5640 */                                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 5641 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 5645 */                                                 if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 5646 */                                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                                                 }
/*      */                                                 
/* 5649 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 5650 */                                                 out.write(10);
/* 5651 */                                                 out.write(9);
/* 5652 */                                                 out.write(9);
/* 5653 */                                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 5654 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 5658 */                                             if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 5659 */                                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                                             }
/*      */                                             
/* 5662 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 5663 */                                             out.write(10);
/* 5664 */                                             out.write(9);
/* 5665 */                                             out.write(32);
/* 5666 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 5667 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5671 */                                         if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 5672 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                                         }
/*      */                                         
/* 5675 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 5676 */                                         out.write(10);
/* 5677 */                                         out.write(9);
/* 5678 */                                         out.write(32);
/* 5679 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 5680 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5684 */                                     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 5685 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                                     }
/*      */                                     
/* 5688 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 5689 */                                     out.write(10);
/* 5690 */                                     out.write(9);
/* 5691 */                                     out.write("\n\n<table width=\"100%\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\" >\n");
/* 5692 */                                     if ((tabConfiguration != null) && (m > 0)) {
/* 5693 */                                       groupList = (ArrayList)groupData.get("Group_tab" + m);
/*      */                                     }
/* 5695 */                                     for (int grpct = 0; grpct < groupList.size(); grpct++)
/*      */                                     {
/* 5697 */                                       groupDetails = (HashMap)groupList.get(grpct);
/* 5698 */                                       pageContext.setAttribute("groupDetails", groupDetails);
/* 5699 */                                       category = (String)groupDetails.get("CATEGORY");
/* 5700 */                                       boolean showGroup = true;
/* 5701 */                                       if ((groupDetails.get("RELIED-ON-ARG") != null) && (groupDetails.get("RELIED-ON-VALUE") != null)) {
/* 5702 */                                         showGroup = false;
/* 5703 */                                         String reliedOnArg = (String)groupDetails.get("RELIED-ON-ARG");
/* 5704 */                                         String reliedOnValue = (String)groupDetails.get("RELIED-ON-VALUE");
/* 5705 */                                         if ((!reliedargsValues.isEmpty()) && 
/* 5706 */                                           (reliedargsValues.getProperty(reliedOnArg.toUpperCase()).equalsIgnoreCase(reliedOnValue))) {
/* 5707 */                                           showGroup = true;
/*      */                                         }
/*      */                                       }
/*      */                                       
/* 5711 */                                       String uiElement = groupDetails.get("Display") != null ? (String)groupDetails.get("Display") : groupDetails.get("CAPTION") != null ? (String)groupDetails.get("CAPTION") : (String)groupDetails.get("ID");
/* 5712 */                                       String noDataMsg = "";
/* 5713 */                                       boolean isDCDisabled = false;
/* 5714 */                                       boolean showMessageOnConditionFailure = false;
/* 5715 */                                       if ((uIElementsToRemove != null) && (uIElementsToRemove.contains(category + "_" + uiElement)))
/*      */                                       {
/* 5717 */                                         noDataMsg = FormatUtil.getString("am.webclient.conf.datacollection.disabled.msg");
/* 5718 */                                         isDCDisabled = true;
/* 5719 */                                         showMessageOnConditionFailure = (groupDetails.containsKey("Show-MsgOn-Failure")) && ("YES".equals((String)groupDetails.get("Show-MsgOn-Failure")));
/* 5720 */                                         if (!showMessageOnConditionFailure) {
/* 5721 */                                           showGroup = false;
/*      */                                         }
/*      */                                       }
/* 5724 */                                       if (!showGroup) {
/* 5725 */                                         if (category.equals("UITABLE")) {
/* 5726 */                                           tableid++;
/*      */                                         }
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 5731 */                                         out.write("\n\t\t\t");
/*      */                                         
/* 5733 */                                         if ((category.equals("UIGROUP")) || (category.equals("UIGRAPH")) || (category.equals("UIINCLUDE"))) {
/* 5734 */                                           int noOfCols = 3;
/* 5735 */                                           if (((String)groupDetails.get("FULLWIDTH")).equals("2")) {
/* 5736 */                                             width1 = "49";
/* 5737 */                                             count++;
/* 5738 */                                             imgwidth = scrWidth <= 1280 ? "450" : "600";
/* 5739 */                                             imght = scrWidth <= 1280 ? "280" : "350";
/* 5740 */                                           } else if (((String)groupDetails.get("FULLWIDTH")).equals("1")) {
/* 5741 */                                             width1 = "99";
/* 5742 */                                             count += 2;
/* 5743 */                                             imgwidth = "900";
/* 5744 */                                             imght = "350";
/* 5745 */                                             noOfCols = 5;
/*      */                                           }
/* 5747 */                                           if ((count % 2 == 0) || (width1.equals("99"))) {
/* 5748 */                                             out.write("\n\t\t<tr>\n\t\t<td  width=\"100%\">\n\t\t<table  width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t<tr>\n\t\t");
/*      */                                           }
/* 5750 */                                           if (category.equals("UIGROUP")) {
/* 5751 */                                             caption = (String)groupDetails.get("CAPTION");
/* 5752 */                                             gtype = (String)groupDetails.get("GRAPHTYPE");
/* 5753 */                                             attributesList = (ArrayList)groupDetails.get("ATTRIBUTES");
/* 5754 */                                             pageContext.setAttribute("attributesList", attributesList);
/* 5755 */                                             String attids = (String)groupDetails.get("G-ATTRIBUTES");
/* 5756 */                                             String attributeid = (String)groupDetails.get("COMMA-SEPARATED-ATTRIBUTES");
/* 5757 */                                             StringBuffer archivedIds = new StringBuffer();
/* 5758 */                                             String attidlist = (String)groupDetails.get("THRESHOLD-ATTRIBUTES");
/* 5759 */                                             ArrayList attribsInGraphs = new ArrayList();
/*      */                                             
/* 5761 */                                             out.write(10);
/* 5762 */                                             out.write(9);
/*      */                                             
/* 5764 */                                             IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 5765 */                                             _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 5766 */                                             _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_c_005fif_005f23);
/*      */                                             
/* 5768 */                                             _jspx_th_logic_005fiterate_005f1.setName("attributesList");
/*      */                                             
/* 5770 */                                             _jspx_th_logic_005fiterate_005f1.setId("attributes");
/*      */                                             
/* 5772 */                                             _jspx_th_logic_005fiterate_005f1.setIndexId("n");
/* 5773 */                                             int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 5774 */                                             if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 5775 */                                               Object attributes = null;
/* 5776 */                                               Integer n = null;
/* 5777 */                                               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5778 */                                                 out = _jspx_page_context.pushBody();
/* 5779 */                                                 _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 5780 */                                                 _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                               }
/* 5782 */                                               attributes = _jspx_page_context.findAttribute("attributes");
/* 5783 */                                               n = (Integer)_jspx_page_context.findAttribute("n");
/*      */                                               for (;;) {
/* 5785 */                                                 out.write(10);
/* 5786 */                                                 attributeDetails = (HashMap)attributesList.get(n.intValue());
/* 5787 */                                                 String attid = attributeDetails.get("ATTRIBUTEID") != null ? (String)attributeDetails.get("ATTRIBUTEID") : (String)attributeDetails.get("ID");
/* 5788 */                                                 if ((((String)attributeDetails.get("ATYPE")).equals("0")) && ((configAttList == null) || ((configAttList != null) && (!configAttList.contains(attid))))) {
/* 5789 */                                                   if (archivedIds.length() == 0) archivedIds.append(attid); else {
/* 5790 */                                                     archivedIds.append("," + attid);
/*      */                                                   }
/*      */                                                 }
/*      */                                                 
/* 5794 */                                                 out.write(10);
/* 5795 */                                                 out.write(9);
/* 5796 */                                                 int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 5797 */                                                 attributes = _jspx_page_context.findAttribute("attributes");
/* 5798 */                                                 n = (Integer)_jspx_page_context.findAttribute("n");
/* 5799 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5802 */                                               if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5803 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5806 */                                             if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 5807 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                             }
/*      */                                             
/* 5810 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 5811 */                                             out.write("\t\t\t\n\t\t<td width=\"");
/* 5812 */                                             out.print(width1);
/* 5813 */                                             out.write("%\"  valign=\"top\" >\n\t\t");
/* 5814 */                                             out.print(getCustomMessage(resourcetype, (String)groupDetails.get("Message-Id"), category, uIElementsToRemove));
/* 5815 */                                             out.write("<!-- No i18n -->\t\t\n\t\t<table id=\"Table_");
/* 5816 */                                             out.print(grpct);
/* 5817 */                                             out.write("\" onmouseover=\"ShowPicture('configureIcons_");
/* 5818 */                                             out.print(grpct);
/* 5819 */                                             out.write("',1,'");
/* 5820 */                                             out.print(grpct);
/* 5821 */                                             out.write("')\" onmouseout=\"ShowPicture('configureIcons_");
/* 5822 */                                             out.print(grpct);
/* 5823 */                                             out.write("',0,'");
/* 5824 */                                             out.print(grpct);
/* 5825 */                                             out.write("')\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"conf-mon-table\">\n\t\t");
/* 5826 */                                             if (gtype.equals("0")) {
/* 5827 */                                               out.write("\t\n\t\t<tr height=\"35\"><td width=\"100%\"  colspan=\"2\">\n\t\t<table border=\"0\"  class=\"conf-top-summary-tphead\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"conf-mon-txt padd-lt5\">");
/* 5828 */                                               out.print(FormatUtil.getString(caption).toUpperCase());
/* 5829 */                                               out.write("</td>\n\t\t<td align=\"right\" valign=\"top\">\n\t\t<div id=\"configureIcons_");
/* 5830 */                                               out.print(grpct);
/* 5831 */                                               out.write("\" class=\"ShowHideConfigIconsStyle\">");
/* 5832 */                                               if ((attidlist.length() > 0) && (!isDCDisabled)) {
/* 5833 */                                                 out.write(10);
/* 5834 */                                                 out.write(9);
/* 5835 */                                                 out.write(9);
/*      */                                                 
/* 5837 */                                                 PresentTag _jspx_th_logic_005fpresent_005f17 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5838 */                                                 _jspx_th_logic_005fpresent_005f17.setPageContext(_jspx_page_context);
/* 5839 */                                                 _jspx_th_logic_005fpresent_005f17.setParent(_jspx_th_c_005fif_005f23);
/*      */                                                 
/* 5841 */                                                 _jspx_th_logic_005fpresent_005f17.setRole("ADMIN,OPERATOR");
/* 5842 */                                                 int _jspx_eval_logic_005fpresent_005f17 = _jspx_th_logic_005fpresent_005f17.doStartTag();
/* 5843 */                                                 if (_jspx_eval_logic_005fpresent_005f17 != 0) {
/*      */                                                   for (;;) {
/* 5845 */                                                     out.write("<a class=\"conf-hover-buttons white\" href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5846 */                                                     out.print(resID);
/* 5847 */                                                     out.write("&attributeIDs=");
/* 5848 */                                                     out.print(attidlist);
/* 5849 */                                                     out.write("&attributeToSelect=");
/* 5850 */                                                     out.print(new StringTokenizer(attidlist, ",").nextToken());
/* 5851 */                                                     out.write("&redirectto=");
/* 5852 */                                                     out.print(encodeurl);
/* 5853 */                                                     out.write("\"><img src=\"../images/alarms-conf.png\" style=\"margin: 2px 0px 0px 3px;\" alt=\"Down\"  border=\"0\" title=\"");
/* 5854 */                                                     out.print(FormatUtil.getString("am.webclient.common.configureAlarms.tooltip.text"));
/* 5855 */                                                     out.write("\">&nbsp;<span class=\"conf-hover-buttons-text\">");
/* 5856 */                                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.configurealert"));
/* 5857 */                                                     out.write("</span></a>");
/* 5858 */                                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f17.doAfterBody();
/* 5859 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 5863 */                                                 if (_jspx_th_logic_005fpresent_005f17.doEndTag() == 5) {
/* 5864 */                                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f17); return;
/*      */                                                 }
/*      */                                                 
/* 5867 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f17);
/* 5868 */                                                 out.write(10);
/* 5869 */                                                 out.write(9);
/* 5870 */                                                 out.write(9);
/*      */                                               }
/* 5872 */                                               out.write("\t\t\n\t\t");
/* 5873 */                                               if ((archivedIds.length() > 0) && (!isDCDisabled)) {
/* 5874 */                                                 out.write("\n\t\t\t<a class=\"conf-hover-buttons white conf-bottonAlign\" href=\"javascript:void(0)\" onclick=\"fnOpenNewWindowWithHeightWidth('");
/* 5875 */                                                 out.print((String)request.getAttribute("reportURL"));
/* 5876 */                                                 out.write("&todaytime=");
/* 5877 */                                                 out.print(System.currentTimeMillis());
/* 5878 */                                                 out.write("&attributeid=");
/* 5879 */                                                 out.print(new StringTokenizer(archivedIds.toString(), ",").nextToken());
/* 5880 */                                                 out.write("',900,550)\"><img src=\"../images/icon-anamoly-responsetime.gif\" width=\"16\" align=\"absmiddle\" height=\"10\" hspace=\"0\" vspace=\"0\" border=\"0\"  style=\"margin: 0px 0px 1px 3px;\" title=\"");
/* 5881 */                                                 out.print(seven_days_text);
/* 5882 */                                                 out.write("\">&nbsp;</a>\n\t\t");
/*      */                                               }
/* 5884 */                                               out.write("\n\t\t</div>\n\t\t</td>\n\t\t</tr></table></td></tr>\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t");
/*      */                                             } else {
/* 5886 */                                               out.write("\t\t\t\t\t\t\t\t\t\n\t\t<tr height=\"20\"><td colspan=\"2\" align=\"right\">\n\t\t<div id=\"configureIcons_");
/* 5887 */                                               out.print(grpct);
/* 5888 */                                               out.write("\" class=\"ShowHideConfigIconsStyle\">");
/* 5889 */                                               if ((attidlist.length() > 0) && (!isDCDisabled)) {
/* 5890 */                                                 out.write(10);
/* 5891 */                                                 out.write(9);
/* 5892 */                                                 out.write(9);
/*      */                                                 
/* 5894 */                                                 PresentTag _jspx_th_logic_005fpresent_005f18 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5895 */                                                 _jspx_th_logic_005fpresent_005f18.setPageContext(_jspx_page_context);
/* 5896 */                                                 _jspx_th_logic_005fpresent_005f18.setParent(_jspx_th_c_005fif_005f23);
/*      */                                                 
/* 5898 */                                                 _jspx_th_logic_005fpresent_005f18.setRole("ADMIN,OPERATOR,ENTERPRISEADMIN");
/* 5899 */                                                 int _jspx_eval_logic_005fpresent_005f18 = _jspx_th_logic_005fpresent_005f18.doStartTag();
/* 5900 */                                                 if (_jspx_eval_logic_005fpresent_005f18 != 0) {
/*      */                                                   for (;;) {
/* 5902 */                                                     out.write("\n\t\t\t<a class=\"conf-hover-buttons white\" href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5903 */                                                     out.print(resID);
/* 5904 */                                                     out.write("&attributeIDs=");
/* 5905 */                                                     out.print(attidlist);
/* 5906 */                                                     out.write("&attributeToSelect=");
/* 5907 */                                                     out.print(new StringTokenizer(attidlist, ",").nextToken());
/* 5908 */                                                     out.write("&redirectto=");
/* 5909 */                                                     out.print(encodeurl);
/* 5910 */                                                     out.write("\"><img src=\"../images/alarms-conf.png\" style=\"margin: 2px 0px 0px 3px;\" alt=\"Down\"  border=\"0\" title=\"");
/* 5911 */                                                     out.print(FormatUtil.getString("am.webclient.common.configureAlarms.tooltip.text"));
/* 5912 */                                                     out.write("\">&nbsp;<span class=\"conf-hover-buttons-text\">");
/* 5913 */                                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.configurealert"));
/* 5914 */                                                     out.write("</span></a>\t\t");
/* 5915 */                                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f18.doAfterBody();
/* 5916 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 5920 */                                                 if (_jspx_th_logic_005fpresent_005f18.doEndTag() == 5) {
/* 5921 */                                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f18); return;
/*      */                                                 }
/*      */                                                 
/* 5924 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f18);
/*      */                                               }
/* 5926 */                                               out.write(10);
/* 5927 */                                               out.write(9);
/* 5928 */                                               out.write(9);
/* 5929 */                                               if ((archivedIds.length() > 0) && (!isDCDisabled)) {
/* 5930 */                                                 out.write("\n\t\t\t<a class=\"conf-hover-buttons white conf-bottonAlign\" href=\"javascript:void(0)\" onclick=\"fnOpenNewWindowWithHeightWidth('");
/* 5931 */                                                 out.print((String)request.getAttribute("reportURL"));
/* 5932 */                                                 out.write("&todaytime=");
/* 5933 */                                                 out.print(System.currentTimeMillis());
/* 5934 */                                                 out.write("&attributeid=");
/* 5935 */                                                 out.print(new StringTokenizer(archivedIds.toString(), ",").nextToken());
/* 5936 */                                                 out.write("',900,550)\"><img src=\"../images/icon-anamoly-responsetime.gif\" width=\"16\" align=\"absmiddle\" height=\"10\" hspace=\"0\" vspace=\"0\" border=\"0\"  style=\"margin: 0px 0px 1px 3px;\" title=\"");
/* 5937 */                                                 out.print(seven_days_text);
/* 5938 */                                                 out.write("\">&nbsp;</a>\n\t\t");
/*      */                                               }
/* 5940 */                                               out.write("\n\t\t</div>\n\t\t</td></tr>");
/*      */                                             }
/* 5942 */                                             out.write(10);
/* 5943 */                                             out.write(9);
/* 5944 */                                             out.write(9);
/* 5945 */                                             if (isDCDisabled) {
/* 5946 */                                               if (showMessageOnConditionFailure)
/*      */                                               {
/* 5948 */                                                 out.write("\n\t\t\t<tr colspan=\"4\" height=\"40\">\t<td height=\"26\" align=\"center\" class=\"bodytextbold\">");
/* 5949 */                                                 out.print(noDataMsg);
/* 5950 */                                                 out.write("\t\n\t\t\t\t");
/*      */                                                 
/* 5952 */                                                 PresentTag _jspx_th_logic_005fpresent_005f19 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5953 */                                                 _jspx_th_logic_005fpresent_005f19.setPageContext(_jspx_page_context);
/* 5954 */                                                 _jspx_th_logic_005fpresent_005f19.setParent(_jspx_th_c_005fif_005f23);
/*      */                                                 
/* 5956 */                                                 _jspx_th_logic_005fpresent_005f19.setRole("ADMIN");
/* 5957 */                                                 int _jspx_eval_logic_005fpresent_005f19 = _jspx_th_logic_005fpresent_005f19.doStartTag();
/* 5958 */                                                 if (_jspx_eval_logic_005fpresent_005f19 != 0) {
/*      */                                                   for (;;) {
/* 5960 */                                                     out.write(9);
/* 5961 */                                                     out.print(FormatUtil.getString("am.webclient.conf.datacollection.enable.link", new String[] { resourcetype }));
/* 5962 */                                                     out.write(9);
/* 5963 */                                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f19.doAfterBody();
/* 5964 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 5968 */                                                 if (_jspx_th_logic_005fpresent_005f19.doEndTag() == 5) {
/* 5969 */                                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f19); return;
/*      */                                                 }
/*      */                                                 
/* 5972 */                                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f19);
/* 5973 */                                                 out.write("\n\t\t\t</td></tr>\n\t\t");
/*      */                                               }
/*      */                                             } else {
/* 5976 */                                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" class=\"bodytext\" colspan=\"4\" valign=\"top\" >\n");
/* 5977 */                                               if ((gtype.equals("1")) || (gtype.equals("3"))) {
/* 5978 */                                                 confgraph.setGraphType(gtype.equals("1") ? "LINE" : "PIE");
/* 5979 */                                                 confgraph.setGraphForData("ATTRIBUTES");
/* 5980 */                                                 confgraph.setResourceType(resourcetype);
/* 5981 */                                                 confgraph.setGraphProperteis(groupDetails);
/* 5982 */                                                 confgraph.setGraphValue(request.getAttribute("graphValues") != null ? (HashMap)request.getAttribute("graphValues") : null);
/* 5983 */                                                 confgraph.setAttributeValue(request.getAttribute("attributeValues") != null ? (HashMap)request.getAttribute("attributeValues") : null);
/*      */                                               }
/* 5985 */                                               if (gtype.equals("1")) {
/* 5986 */                                                 if (attids.indexOf(",") != -1) {
/* 5987 */                                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                   
/* 5989 */                                                   TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fchartTitle_005fnobody.get(TimeChart.class);
/* 5990 */                                                   _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 5991 */                                                   _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_c_005fif_005f23);
/*      */                                                   
/* 5993 */                                                   _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("confgraph");
/*      */                                                   
/* 5995 */                                                   _jspx_th_awolf_005ftimechart_005f0.setWidth(imgwidth);
/*      */                                                   
/* 5997 */                                                   _jspx_th_awolf_005ftimechart_005f0.setHeight(imght);
/*      */                                                   
/* 5999 */                                                   _jspx_th_awolf_005ftimechart_005f0.setChartTitle(FormatUtil.getString(caption));
/*      */                                                   
/* 6001 */                                                   _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */                                                   
/* 6003 */                                                   _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*      */                                                   
/* 6005 */                                                   _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString((String)groupDetails.get("GRAPHUNIT")));
/*      */                                                   
/* 6007 */                                                   _jspx_th_awolf_005ftimechart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6008 */                                                   int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 6009 */                                                   if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 6010 */                                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fchartTitle_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                                                   }
/*      */                                                   
/* 6013 */                                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fchartTitle_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 6014 */                                                   out.write(32);
/* 6015 */                                                   out.write("\t\t\t\t\t \n");
/*      */                                                 } else {
/* 6017 */                                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                                   
/* 6019 */                                                   XYAreaChart _jspx_th_awolf_005fxyareachart_005f0 = (XYAreaChart)this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer_005fchartTitle_005fnobody.get(XYAreaChart.class);
/* 6020 */                                                   _jspx_th_awolf_005fxyareachart_005f0.setPageContext(_jspx_page_context);
/* 6021 */                                                   _jspx_th_awolf_005fxyareachart_005f0.setParent(_jspx_th_c_005fif_005f23);
/*      */                                                   
/* 6023 */                                                   _jspx_th_awolf_005fxyareachart_005f0.setDataSetProducer("confgraph");
/*      */                                                   
/* 6025 */                                                   _jspx_th_awolf_005fxyareachart_005f0.setWidth(imgwidth);
/*      */                                                   
/* 6027 */                                                   _jspx_th_awolf_005fxyareachart_005f0.setHeight(imght);
/*      */                                                   
/* 6029 */                                                   _jspx_th_awolf_005fxyareachart_005f0.setChartTitle(FormatUtil.getString(caption));
/*      */                                                   
/* 6031 */                                                   _jspx_th_awolf_005fxyareachart_005f0.setLegend("true");
/*      */                                                   
/* 6033 */                                                   _jspx_th_awolf_005fxyareachart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*      */                                                   
/* 6035 */                                                   _jspx_th_awolf_005fxyareachart_005f0.setYaxisLabel(FormatUtil.getString((String)groupDetails.get("GRAPHUNIT")));
/*      */                                                   
/* 6037 */                                                   _jspx_th_awolf_005fxyareachart_005f0.setDataSetType("SubSeriesDataset");
/*      */                                                   
/* 6039 */                                                   _jspx_th_awolf_005fxyareachart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6040 */                                                   int _jspx_eval_awolf_005fxyareachart_005f0 = _jspx_th_awolf_005fxyareachart_005f0.doStartTag();
/* 6041 */                                                   if (_jspx_th_awolf_005fxyareachart_005f0.doEndTag() == 5) {
/* 6042 */                                                     this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer_005fchartTitle_005fnobody.reuse(_jspx_th_awolf_005fxyareachart_005f0); return;
/*      */                                                   }
/*      */                                                   
/* 6045 */                                                   this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer_005fchartTitle_005fnobody.reuse(_jspx_th_awolf_005fxyareachart_005f0);
/* 6046 */                                                   out.write(9);
/* 6047 */                                                   out.write(10);
/*      */                                                 }
/* 6049 */                                               } else if (gtype.equals("3")) {
/* 6050 */                                                 if (!confgraph.getGraphDataNull()) {
/* 6051 */                                                   out.write("\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                                   
/* 6053 */                                                   AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005fnodatamessage_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular_005fchartTitle_005fnobody.get(AMWolf.class);
/* 6054 */                                                   _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 6055 */                                                   _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_c_005fif_005f23);
/*      */                                                   
/* 6057 */                                                   _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("confgraph");
/*      */                                                   
/* 6059 */                                                   _jspx_th_awolf_005fpiechart_005f0.setWidth(imgwidth);
/*      */                                                   
/* 6061 */                                                   _jspx_th_awolf_005fpiechart_005f0.setHeight(imght);
/*      */                                                   
/* 6063 */                                                   _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                                                   
/* 6065 */                                                   _jspx_th_awolf_005fpiechart_005f0.setLegendanchor("SOUTH");
/*      */                                                   
/* 6067 */                                                   _jspx_th_awolf_005fpiechart_005f0.setUnits(FormatUtil.getString((String)groupDetails.get("GRAPHUNIT")));
/*      */                                                   
/* 6069 */                                                   _jspx_th_awolf_005fpiechart_005f0.setChartTitle(FormatUtil.getString(caption));
/*      */                                                   
/* 6071 */                                                   _jspx_th_awolf_005fpiechart_005f0.setCircular(true);
/*      */                                                   
/* 6073 */                                                   _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/*      */                                                   
/* 6075 */                                                   _jspx_th_awolf_005fpiechart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6076 */                                                   int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 6077 */                                                   if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 6078 */                                                     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005fnodatamessage_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular_005fchartTitle_005fnobody.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                                                   }
/*      */                                                   
/* 6081 */                                                   this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005fnodatamessage_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular_005fchartTitle_005fnobody.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 6082 */                                                   out.write("\n\t\t\t");
/*      */                                                 } else {
/* 6084 */                                                   out.write("\t\t\t\t\t\n\t\t\t\t  <table width=\"95%\"><tr><td class=\"conf-mon-table-heading\" align=\"center\" colspan=\"6\">");
/* 6085 */                                                   out.print(FormatUtil.getString(caption).toUpperCase());
/* 6086 */                                                   out.write("</td></tr>\n\t\t\t\t  <tr><td align=\"center\"><table class=\"no-graph-dial\"><tr><td align=\"center\" class=\"disabledtext\" style=\"font-weight: bold;\">");
/* 6087 */                                                   out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6088 */                                                   out.write("</td></tr></table></td></tr></table>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
/* 6089 */                                                 } } else if ((gtype.equals("4")) || (gtype.equals("2"))) {
/* 6090 */                                                 StringTokenizer st = new StringTokenizer(attids, ",");
/* 6091 */                                                 int dials = st.countTokens();
/*      */                                                 
/* 6093 */                                                 out.write("\n\t\t<table border=\"0\" cellpadding=\"0\"  width=\"100%\" style=\"padding-bottom:15px\">\n\t\t\t<tr heigth=\"50\">\n\t\t\t\t<td align=\"center\" colspan=\"");
/* 6094 */                                                 out.print(dials * 2);
/* 6095 */                                                 out.write("\" class=\"conf-mon-table-heading\"> ");
/* 6096 */                                                 out.print(FormatUtil.getString(caption).toUpperCase());
/* 6097 */                                                 out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr heigth=\"10\"><td  colspan=\"");
/* 6098 */                                                 out.print(dials * 2);
/* 6099 */                                                 out.write("\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n\t\t\t<tr  id=\"showLinks_Graph_");
/* 6100 */                                                 out.print(grpct);
/* 6101 */                                                 out.write("\" >\n\t\t\t\t<td width=\"1%\"></td>\n");
/*      */                                                 
/* 6103 */                                                 if ((!attids.equals("")) || (dials != 0)) {
/* 6104 */                                                   while (st.hasMoreTokens()) {
/* 6105 */                                                     out.write(" \t\t\t\n\t\t\t\t\t\t\t<td width=\"");
/* 6106 */                                                     out.print(98 / dials - 2);
/* 6107 */                                                     out.write("%\">\n");
/* 6108 */                                                     String attid = st.nextToken();
/* 6109 */                                                     attribsInGraphs.add(attid);
/* 6110 */                                                     String[] attDetails = DBUtil.getAttributeDetails(Integer.parseInt(attid));
/*      */                                                     try
/*      */                                                     {
/* 6113 */                                                       String temp1 = "0";
/* 6114 */                                                       long value = 0L;
/* 6115 */                                                       temp1 = attributeValues != null ? (String)attributeValues.get(attDetails[3].toUpperCase()) : null;
/* 6116 */                                                       if ((temp1 == null) || (temp1.equals("-")) || (temp1.trim().equals("")) || (temp1.equalsIgnoreCase("null")))
/*      */                                                       {
/* 6118 */                                                         temp1 = "-";
/* 6119 */                                                         value = -1L;
/*      */                                                       } else {
/* 6121 */                                                         temp1 = new StringTokenizer(temp1, ".").nextToken();
/* 6122 */                                                         value = Long.parseLong(temp1.replaceAll(",", ""));
/*      */                                                       }
/* 6124 */                                                       dialGraph1.setValue(value);
/*      */                                                       
/* 6126 */                                                       out.write("    \n\t<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t<td width=\"100%\" align=\"center\" class=\"dashboard\">\n\t\t\t\t<table width=\"99%\" cellspacing=\"0\" cellpadding=\"7\" border=\"0\">\n\t\t\t\t\t<tbody>\n\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t");
/* 6127 */                                                       if (gtype.equals("2")) {
/* 6128 */                                                         out.write("\n\t\t\t\t\t\t\t<td height=\"110\" width=\"25%\" align=\"center\" valign=\"top\"> \n\t\t\t\t\t\t\t\t");
/* 6129 */                                                         JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(temp1), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(attDetails[1]), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("isConfMonitor", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()), out, true);
/* 6130 */                                                         out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t");
/*      */                                                       } else {
/* 6132 */                                                         out.write("\n\t\t\t\t\t\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\"  align=\"center\" >\n\t\t\t\t\t\t\t\t");
/*      */                                                         
/* 6134 */                                                         DialChart _jspx_th_awolf_005fdialchart_005f6 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005fnodatamessage_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 6135 */                                                         _jspx_th_awolf_005fdialchart_005f6.setPageContext(_jspx_page_context);
/* 6136 */                                                         _jspx_th_awolf_005fdialchart_005f6.setParent(_jspx_th_c_005fif_005f23);
/*      */                                                         
/* 6138 */                                                         _jspx_th_awolf_005fdialchart_005f6.setDataSetProducer("dialGraph1");
/*      */                                                         
/* 6140 */                                                         _jspx_th_awolf_005fdialchart_005f6.setWidth("150");
/*      */                                                         
/* 6142 */                                                         _jspx_th_awolf_005fdialchart_005f6.setHeight("148");
/*      */                                                         
/* 6144 */                                                         _jspx_th_awolf_005fdialchart_005f6.setLegend("false");
/*      */                                                         
/* 6146 */                                                         _jspx_th_awolf_005fdialchart_005f6.setXaxisLabel("");
/*      */                                                         
/* 6148 */                                                         _jspx_th_awolf_005fdialchart_005f6.setYaxisLabel("");
/*      */                                                         
/* 6150 */                                                         _jspx_th_awolf_005fdialchart_005f6.setDateFormat("HH:mm");
/*      */                                                         
/* 6152 */                                                         _jspx_th_awolf_005fdialchart_005f6.setResourceId(resID);
/*      */                                                         
/* 6154 */                                                         _jspx_th_awolf_005fdialchart_005f6.setAttributeId(attid);
/*      */                                                         
/* 6156 */                                                         _jspx_th_awolf_005fdialchart_005f6.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 6157 */                                                         int _jspx_eval_awolf_005fdialchart_005f6 = _jspx_th_awolf_005fdialchart_005f6.doStartTag();
/* 6158 */                                                         if (_jspx_eval_awolf_005fdialchart_005f6 != 0) {
/* 6159 */                                                           if (_jspx_eval_awolf_005fdialchart_005f6 != 1) {
/* 6160 */                                                             out = _jspx_page_context.pushBody();
/* 6161 */                                                             _jspx_th_awolf_005fdialchart_005f6.setBodyContent((BodyContent)out);
/* 6162 */                                                             _jspx_th_awolf_005fdialchart_005f6.doInitBody();
/*      */                                                           }
/*      */                                                           for (;;) {
/* 6165 */                                                             out.write(32);
/* 6166 */                                                             out.write(32);
/* 6167 */                                                             out.write(32);
/* 6168 */                                                             out.write("\n\t\t\t\t\t\t\t\t");
/* 6169 */                                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f6.doAfterBody();
/* 6170 */                                                             if (evalDoAfterBody != 2)
/*      */                                                               break;
/*      */                                                           }
/* 6173 */                                                           if (_jspx_eval_awolf_005fdialchart_005f6 != 1) {
/* 6174 */                                                             out = _jspx_page_context.popBody();
/*      */                                                           }
/*      */                                                         }
/* 6177 */                                                         if (_jspx_th_awolf_005fdialchart_005f6.doEndTag() == 5) {
/* 6178 */                                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005fnodatamessage_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f6); return;
/*      */                                                         }
/*      */                                                         
/* 6181 */                                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005fnodatamessage_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f6);
/* 6182 */                                                         out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t");
/*      */                                                       }
/* 6184 */                                                       out.write("\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t</tr>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t<tr align=\"center\">\n\t\t\t<td width=\"");
/* 6185 */                                                       out.print(98 / dials - 2);
/* 6186 */                                                       out.write("%\" height=\"25\" >\n\t\t\t\t<a href=\"javascript:void();\" class=\"tooltip\" onClick=\"fnOpenNewWindowWithHeightWidth('../showHistoryData.do?method=getData&reporttype=html&resourceid=");
/* 6187 */                                                       out.print(resID);
/* 6188 */                                                       out.write("&childid=null&resourcename=");
/* 6189 */                                                       out.print((String)request.getAttribute("monitorname"));
/* 6190 */                                                       out.write("&attributeid=");
/* 6191 */                                                       out.print(attid);
/* 6192 */                                                       out.write("&period=20&businessPeriod=oni&todaytime=");
/* 6193 */                                                       out.print(System.currentTimeMillis());
/* 6194 */                                                       out.write("',900,550)\"  style=\"cursor:pointer\">\t\n\t\t\t\t<span>\n\t\t\t\t");
/* 6195 */                                                       out.print(FormatUtil.getString(attDetails[1]));
/* 6196 */                                                       out.write(" - <!--No I18N--><b>\n\t\t\t\t");
/* 6197 */                                                       if (!temp1.equals("-")) {
/* 6198 */                                                         out.println(FormatUtil.formatNumber(value) + "%");
/*      */                                                       }
/*      */                                                       else {
/* 6201 */                                                         out.println("%");
/*      */                                                       }
/* 6203 */                                                       out.write("</b>\n\t\t\t\t</span>\n\t\t\t\t</a>\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/* 6204 */                                                     } catch (Exception ex) { System.out.println("Exception :" + ex.getMessage()); }
/* 6205 */                                                     out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n");
/* 6206 */                                                     if (st.hasMoreTokens()) {
/* 6207 */                                                       out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td  width=\"2%\"></td>\n");
/*      */                                                     } else {
/* 6209 */                                                       out.write("                             \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td  width=\"1%\"></td>\n");
/*      */                                                     }
/* 6211 */                                                     out.write(10);
/*      */                                                   }
/*      */                                                 }
/* 6214 */                                                 out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n");
/*      */                                               }
/* 6216 */                                               out.write("\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t<tr height=\"10\"><td><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                               
/* 6218 */                                               StringTokenizer st = new StringTokenizer(attributeid, ",");
/* 6219 */                                               boolean showattribTable = true;
/* 6220 */                                               if (gtype.equals("0")) {
/* 6221 */                                                 out.write("\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"50%\" align=\"left\" class=\"monitorinfoodd-conf\"><b>");
/* 6222 */                                                 out.print(FormatUtil.getString("Name"));
/* 6223 */                                                 out.write("</b></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"50%\" align=\"left\" class=\"monitorinfoodd-conf\"  ><b>");
/* 6224 */                                                 out.print(FormatUtil.getString("Value"));
/* 6225 */                                                 out.write("</b></td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                               } else {
/* 6227 */                                                 out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t<tr height=\"20\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td id=\"showLinks_");
/* 6228 */                                                 out.print(grpct);
/* 6229 */                                                 out.write("\" align=\"center\" style=\"line-height:20px;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table width=\"100%\"><tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"");
/* 6230 */                                                 out.print(noOfCols == 5 ? "confOneColUIDatatd" : "confTwoColDatatd");
/* 6231 */                                                 out.write("\">\t<img src=\"/images/spacer.gif\"/></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                               }
/* 6233 */                                               int alterRow = 0;
/* 6234 */                                               while (st.hasMoreTokens())
/*      */                                               {
/* 6236 */                                                 String value = "-";
/* 6237 */                                                 alterRow++;
/* 6238 */                                                 String attid = st.nextToken();
/* 6239 */                                                 String[] attDetails = DBUtil.getAttributeDetails(Integer.parseInt(attid));
/* 6240 */                                                 boolean escape = false;
/* 6241 */                                                 if ((conf.getAttIdVsEscapeXMLValues(attid) != null) && (conf.getAttIdVsEscapeXMLValues(attid).trim().equalsIgnoreCase("NO"))) {
/* 6242 */                                                   escape = true;
/*      */                                                 }
/* 6244 */                                                 value = attributeValues != null ? (String)attributeValues.get(attDetails[3].toUpperCase()) : null;
/* 6245 */                                                 if ((value == null) || (value.equals("")) || (value.equalsIgnoreCase("null"))) {
/* 6246 */                                                   value = "-";
/*      */                                                 }
/* 6248 */                                                 if ((!value.equals("-")) && (!attDetails[2].equals("-")) && (!attDetails[2].trim().equals(""))) {
/* 6249 */                                                   value = value + " " + FormatUtil.getString(attDetails[2]);
/*      */                                                 }
/* 6251 */                                                 if (gtype.equals("0")) {
/* 6252 */                                                   out.write("\n\t\t\t<tr onmouseout=\"this.className='confheader'\" onmouseover=\"this.className='confHeaderHover'\" class=\"confheader\" style=\"word-break: break-all;\">\n\t\t\t\t<td width=\"50%\" align=\"left\" class=\"monitorinfoodd-conf\"> ");
/* 6253 */                                                   out.print((String)((HashMap)request.getAttribute("disNameValues")).get(attid));
/* 6254 */                                                   out.write("</td>\n\t\t\t\t<td width=\"50%\" align=\"left\" class=\"monitorinfoodd-conf \">\n\t\t\t");
/* 6255 */                                                   if (((HashMap)request.getAttribute("linkValues")).get(attid) != null) {
/* 6256 */                                                     out.write("\n\t\t\t\t\t");
/* 6257 */                                                     out.print((String)((HashMap)request.getAttribute("linkValues")).get(attid));
/* 6258 */                                                     out.write("\t\t\n\t\t\t");
/*      */                                                   }
/* 6260 */                                                   out.write("<span width=\"99%\" class=\"bodytextbold\" style=\"white-space:normal;word-wrap:break-word;\">\n\t\t\t\t");
/*      */                                                   
/* 6262 */                                                   SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6263 */                                                   _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 6264 */                                                   _jspx_th_c_005fset_005f7.setParent(_jspx_th_c_005fif_005f23);
/*      */                                                   
/* 6266 */                                                   _jspx_th_c_005fset_005f7.setVar("myValue");
/*      */                                                   
/* 6268 */                                                   _jspx_th_c_005fset_005f7.setScope("page");
/* 6269 */                                                   int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 6270 */                                                   if (_jspx_eval_c_005fset_005f7 != 0) {
/* 6271 */                                                     if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6272 */                                                       out = _jspx_page_context.pushBody();
/* 6273 */                                                       _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 6274 */                                                       _jspx_th_c_005fset_005f7.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 6277 */                                                       out.print(value);
/* 6278 */                                                       int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 6279 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 6282 */                                                     if (_jspx_eval_c_005fset_005f7 != 1) {
/* 6283 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 6286 */                                                   if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 6287 */                                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                                                   }
/*      */                                                   
/* 6290 */                                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 6291 */                                                   out.write("\n\t\t\t  \t");
/*      */                                                   
/* 6293 */                                                   SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6294 */                                                   _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 6295 */                                                   _jspx_th_c_005fset_005f8.setParent(_jspx_th_c_005fif_005f23);
/*      */                                                   
/* 6297 */                                                   _jspx_th_c_005fset_005f8.setVar("escape");
/*      */                                                   
/* 6299 */                                                   _jspx_th_c_005fset_005f8.setScope("page");
/* 6300 */                                                   int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 6301 */                                                   if (_jspx_eval_c_005fset_005f8 != 0) {
/* 6302 */                                                     if (_jspx_eval_c_005fset_005f8 != 1) {
/* 6303 */                                                       out = _jspx_page_context.pushBody();
/* 6304 */                                                       _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 6305 */                                                       _jspx_th_c_005fset_005f8.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 6308 */                                                       out.print(escape);
/* 6309 */                                                       int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 6310 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 6313 */                                                     if (_jspx_eval_c_005fset_005f8 != 1) {
/* 6314 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 6317 */                                                   if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 6318 */                                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                                                   }
/*      */                                                   
/* 6321 */                                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8);
/* 6322 */                                                   out.write("\n\t\t\t  \t");
/* 6323 */                                                   if (_jspx_meth_c_005fout_005f40(_jspx_th_c_005fif_005f23, _jspx_page_context))
/*      */                                                     return;
/* 6325 */                                                   out.write("\t\n\t\t\t  \t\t</span>\n\t\t\t  \t");
/* 6326 */                                                   if (((HashMap)request.getAttribute("linkValues")).get(attid) != null) {
/* 6327 */                                                     out.write("</a>");
/*      */                                                   }
/* 6329 */                                                   out.write("</td></tr>\n\t\t");
/*      */                                                 }
/*      */                                                 else {
/* 6332 */                                                   if (alterRow == 1) {
/* 6333 */                                                     out.write("<div id=\"");
/* 6334 */                                                     out.print(noOfCols == 5 ? "menu-conf1" : "menu-conf");
/* 6335 */                                                     out.write("\"><ul>");
/*      */                                                   }
/* 6337 */                                                   if (alterRow - 1 == noOfCols) {
/* 6338 */                                                     out.write("<ul>\t");
/*      */                                                   }
/* 6340 */                                                   if (!attribsInGraphs.contains(attid)) {
/* 6341 */                                                     out.write("\n\t\t\t\t\t\t<li class=\"confUlWidth\" style=\"text-overflow:ellipsis\"> ");
/* 6342 */                                                     out.print((String)((HashMap)request.getAttribute("disNameValues")).get(attid));
/* 6343 */                                                     out.write("<br>\n\t\t\t\t\t\t\t\t<span>\n\t\t\t\t\t\t\t");
/* 6344 */                                                     if (((HashMap)request.getAttribute("linkValues")).get(attid) != null) {
/* 6345 */                                                       out.write("\n\t\t\t\t\t\t\t\t");
/* 6346 */                                                       out.print((String)((HashMap)request.getAttribute("linkValues")).get(attid));
/* 6347 */                                                       out.write("\t\t\t\n\t\t\t\t\t\t\t");
/*      */                                                     }
/* 6349 */                                                     out.write("\t\n\t\t\t\t\t\t\t <b>\n\t\t\t\t\t\t\t \t");
/*      */                                                     
/* 6351 */                                                     SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6352 */                                                     _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 6353 */                                                     _jspx_th_c_005fset_005f9.setParent(_jspx_th_c_005fif_005f23);
/*      */                                                     
/* 6355 */                                                     _jspx_th_c_005fset_005f9.setVar("myValue");
/*      */                                                     
/* 6357 */                                                     _jspx_th_c_005fset_005f9.setScope("page");
/* 6358 */                                                     int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 6359 */                                                     if (_jspx_eval_c_005fset_005f9 != 0) {
/* 6360 */                                                       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 6361 */                                                         out = _jspx_page_context.pushBody();
/* 6362 */                                                         _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 6363 */                                                         _jspx_th_c_005fset_005f9.doInitBody();
/*      */                                                       }
/*      */                                                       for (;;) {
/* 6366 */                                                         out.print(value);
/* 6367 */                                                         int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 6368 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/* 6371 */                                                       if (_jspx_eval_c_005fset_005f9 != 1) {
/* 6372 */                                                         out = _jspx_page_context.popBody();
/*      */                                                       }
/*      */                                                     }
/* 6375 */                                                     if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 6376 */                                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                                                     }
/*      */                                                     
/* 6379 */                                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f9);
/* 6380 */                                                     out.write("\n\t\t\t\t\t\t\t  \t");
/*      */                                                     
/* 6382 */                                                     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6383 */                                                     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 6384 */                                                     _jspx_th_c_005fset_005f10.setParent(_jspx_th_c_005fif_005f23);
/*      */                                                     
/* 6386 */                                                     _jspx_th_c_005fset_005f10.setVar("escape");
/*      */                                                     
/* 6388 */                                                     _jspx_th_c_005fset_005f10.setScope("page");
/* 6389 */                                                     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 6390 */                                                     if (_jspx_eval_c_005fset_005f10 != 0) {
/* 6391 */                                                       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 6392 */                                                         out = _jspx_page_context.pushBody();
/* 6393 */                                                         _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 6394 */                                                         _jspx_th_c_005fset_005f10.doInitBody();
/*      */                                                       }
/*      */                                                       for (;;) {
/* 6397 */                                                         out.print(escape);
/* 6398 */                                                         int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 6399 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/* 6402 */                                                       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 6403 */                                                         out = _jspx_page_context.popBody();
/*      */                                                       }
/*      */                                                     }
/* 6406 */                                                     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 6407 */                                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                                                     }
/*      */                                                     
/* 6410 */                                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f10);
/* 6411 */                                                     out.write("\n\t\t\t\t\t\t\t  \t");
/* 6412 */                                                     if (_jspx_meth_c_005fout_005f41(_jspx_th_c_005fif_005f23, _jspx_page_context))
/*      */                                                       return;
/* 6414 */                                                     out.write("\n\t\t\t\t\t\t\t \t</b>\n\t\t\t\t\t\t \t ");
/* 6415 */                                                     if (((HashMap)request.getAttribute("linkValues")).get(attid) != null) {
/* 6416 */                                                       out.write("</a>");
/*      */                                                     }
/* 6418 */                                                     out.write("\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t");
/*      */                                                   }
/* 6420 */                                                   if (alterRow == noOfCols)
/* 6421 */                                                     out.write("</ul>");
/* 6422 */                                                   if (st.countTokens() == 0) {
/* 6423 */                                                     out.write("</ul></div>");
/*      */                                                   }
/* 6425 */                                                   out.write("\t\t\t\t\t\t\n\t\t\t\t");
/*      */                                                 }
/* 6427 */                                                 out.write("\t\t\t\t\t\n\t");
/*      */                                               }
/*      */                                               
/* 6430 */                                               out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t ");
/* 6431 */                                               if (!gtype.equals("0")) {
/* 6432 */                                                 out.write("\t \t\t\t\n\t \t\t\t</td>\n\t \t\t\t<td class=\"");
/* 6433 */                                                 out.print(noOfCols == 5 ? "confOneColUIDatatd" : "confTwoColDatatd");
/* 6434 */                                                 out.write("\">\t<img src=\"/images/spacer.gif\"/></td>\n\t \t\t\t</tr></table></td></tr>\n\t ");
/*      */                                               } }
/* 6436 */                                             out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t \n\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t</td>\t\n\t\t\t");
/*      */                                           }
/* 6438 */                                           else if (category.equals("UIINCLUDE")) {
/* 6439 */                                             try { out.write("\t\t\t\t\t\t\n\t\t\t\t\t\t<td>\t\t\t\t\n\t\t\t\t\t\t");
/* 6440 */                                               out.print(getCustomMessage(resourcetype, (String)groupDetails.get("Message-Id"), category, uIElementsToRemove));
/* 6441 */                                               out.write("\t\t\t\n\t\t\t\t\t\t\t");
/* 6442 */                                               JspRuntimeLibrary.include(request, response, (String)groupDetails.get("PATH") + (((String)groupDetails.get("PATH")).indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceName", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(moname), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceType", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(request.getParameter("type")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("baseId", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(baseid1), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceID", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resID), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("Id", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf((String)groupDetails.get("ID")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("noDataMsg", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(noDataMsg), request.getCharacterEncoding()), out, true);
/* 6443 */                                               out.write("\t\t  \t\t\t\t\t\t\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t");
/* 6444 */                                             } catch (Exception e) { e.printStackTrace(); }
/* 6445 */                                             out.write("\n\t\t\t");
/* 6446 */                                           } else if (category.equals("UIGRAPH")) {
/* 6447 */                                             out.write("\n\t\t\t\t\n\t\t\t\t\t<td>\n\t\t\t\t\t\t<div id=\"Graph-");
/* 6448 */                                             out.print(graphCount);
/* 6449 */                                             out.write("-Content\">\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</td>\t\t\t\t\n\t");
/* 6450 */                                             graphCount++;
/*      */                                           }
/* 6452 */                                           out.write("\t\t\t\t\t\t\t\t\t\n");
/* 6453 */                                           if ((count % 2 == 0) && (!width1.equals("99")))
/* 6454 */                                             out.write("\n\t\t<td width=\"1%\">&nbsp;\n \t\t</td>\n");
/* 6455 */                                           if ((count % 2 != 0) || (width1.equals("99"))) {
/* 6456 */                                             out.write("\n    \t</tr>\t\n    \t</table>\n    </td>\n </tr>\n");
/*      */                                           }
/* 6458 */                                         } else if (category.equals("UIMESSAGE")) {
/* 6459 */                                           out.write("\t   \t\t\n\t\t\t");
/* 6460 */                                           out.print(getCustomMessage(resourcetype, (String)groupDetails.get("ID"), category, uIElementsToRemove));
/* 6461 */                                           out.write("\t\t\t\n\t\t");
/*      */                                         }
/*      */                                         else {
/* 6464 */                                           tableId = Integer.toString(tableid++);
/*      */                                           
/* 6466 */                                           out.write("\t\t\n\t\t");
/* 6467 */                                           if (grpct == 0) {
/* 6468 */                                             out.write("<br>");
/*      */                                           }
/* 6470 */                                           out.write("\t\n\t\t<tr>\n\t\t\t<td width=\"100%\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t<tbody><tr>\t<td valign=\"top\" width=\"99%\">\n\t\t\t\t<div id=\"Table-");
/* 6471 */                                           out.print(tableId);
/* 6472 */                                           out.write("-Content\">\n\t\t\t\t</div>\n\t\t</td></tr></tbody></table></td>\t\t\t\n\t\t</tr>\n");
/*      */                                         }
/*      */                                       }
/*      */                                     }
/* 6476 */                                     out.write("\t\n</table>\n</div>\n</div>\n");
/* 6477 */                                   } catch (Exception exc) { exc.printStackTrace();
/*      */                                   }
/* 6479 */                                   out.write(10);
/* 6480 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 6481 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 6485 */                               if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 6486 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23); return;
/*      */                               }
/*      */                               
/* 6489 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 6490 */                               out.write(10);
/* 6491 */                               if ((request.getAttribute("isValidDateRange") != null) && (!((Boolean)request.getAttribute("isValidDateRange")).booleanValue())) {
/* 6492 */                                 out.write("\n<div id=\"NotvalidRange\"></div>\n");
/*      */                               }
/* 6494 */                               out.write("\n</div>\n<script type=\"text/javascript\">\nvar scrWidth=");
/* 6495 */                               out.print(scrWidth);
/* 6496 */                               out.write(";\nvar tab_Id=");
/* 6497 */                               out.print(m);
/* 6498 */                               out.write(";\nvar periodicity=");
/* 6499 */                               if (_jspx_meth_c_005fout_005f42(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                 return;
/* 6501 */                               out.write("; //No I18N      \nvar reloadTimer;\n</script>\n");
/* 6502 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 6503 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 6506 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 6507 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 6510 */                           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 6511 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                           }
/*      */                           
/* 6514 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 6515 */                           out.write(32);
/* 6516 */                           out.write(10);
/* 6517 */                           out.write(9);
/* 6518 */                           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 6520 */                           out.write(32);
/* 6521 */                           out.write(10);
/* 6522 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 6523 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 6527 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 6528 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                       }
/*      */                       else {
/* 6531 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 6532 */                         out.write(" \n<script type=\"text/javascript\">\n\n$(window).bind('load', confBodyLoad);\t//NO I18N \nfunction confBodyLoad() {\t\t\n\tvar temp=\"1\";\n\tif(document.location.hash && document.location.hash.trim()!=\"\"){\n\t\t if(document.location.hash.indexOf('#tabId')!=-1){\t\t\n\t\t\t var hashValue=document.location.hash.split(\"&\");\n\t\t\ttemp=hashValue[0].split(\"-\")[1];\n\t\t\tdocument.getElementById('tabsForm').TimeUnit.value=hashValue[1].split(\"-\")[1];\n\t\t\tdocument.getElementById('tabsForm').granularity.value=hashValue[2].split(\"-\")[1];\t\t\n\t\t\tif(hashValue[1].split(\"-\")[1]==15 && hashValue.length>3){\n\t\t\t\tvar customdate=hashValue[3].split(\"-\");\n\t\t\t\tdocument.getElementById(\"periodForm\").HeadercustomDate.value=customdate[1]+\"-\"+customdate[2]+\"-\"+customdate[3];\n\t\t\t\tdocument.getElementById('tabsForm').customDate.value=document.getElementById(\"periodForm\").HeadercustomDate.value;\n\t\t\t}\n\t\t\telse if(hashValue[1].split(\"-\")[1]==16 && hashValue.length>4){\t\t\t\n\t\t\t\tdocument.getElementById('tabsForm').monthUnit.value=hashValue[3].split(\"-\")[1];\t\t\n\t\t\t\tdocument.getElementById('tabsForm').weekUnit.value=hashValue[4].split(\"-\")[1];\t\t\t\t\n");
/* 6533 */                         out.write("\t\t\t}\n\t\t}\n\t\telse if(document.location.hash.indexOf('#cf/')!=-1){\t\t\t\n\t\t\ttemp=0;\n\t\t}\t\n\t}\n\t gettabData(temp,'");
/* 6534 */                         out.print(request.getParameter("type"));
/* 6535 */                         out.write(39);
/* 6536 */                         out.write(44);
/* 6537 */                         out.write(39);
/* 6538 */                         out.print(resID);
/* 6539 */                         out.write(39);
/* 6540 */                         out.write(44);
/* 6541 */                         out.write(39);
/* 6542 */                         out.print(serverName);
/* 6543 */                         out.write(39);
/* 6544 */                         out.write(44);
/* 6545 */                         out.write(39);
/* 6546 */                         out.print((String)request.getAttribute("reloadperiod"));
/* 6547 */                         out.write("');\t\n\tpopulatePeriodForm();\n}\nif(document.location.hash!=null && document.location.hash.indexOf('#tabId')!=-1){\n\tchangeTabStyle((document.location.hash.split(\"&\")[0]).split(\"-\")[1],'");
/* 6548 */                         out.print(m);
/* 6549 */                         out.write("');\n}\n$(window).resize(function(){\n  setScreenResoultion();\n});\nsetScreenResoultion();\nfunction setScreenResoultion(){   \n    var scrWidth=this.document.body.offsetWidth;  \n    var c_value=escape(scrWidth) ;\n    document.cookie= \"ScreenWidth=\" + c_value+\";path=/\";\n}\n</script>\n<style>\n/*Temp fix to avoid dual border invoked due to userAreaContainerDiv called via two serverLayoutNoLeft. NEED to REMOVE dual layout*/\n#groupContent #userAreaContainerDiv{\n\t-moz-box-shadow:0px 0px 0px #FFF;\n-webkit-box-shadow:0px 0px 0px #FFF;\nbox-shadow: 0px 0px 0px #FFF;\n}\n</style>\n");
/*      */                       }
/* 6551 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 6552 */         out = _jspx_out;
/* 6553 */         if ((out != null) && (out.getBufferSize() != 0))
/* 6554 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 6555 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 6558 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6564 */     PageContext pageContext = _jspx_page_context;
/* 6565 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6567 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 6568 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 6569 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6571 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 6573 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 6574 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 6575 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 6576 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6577 */       return true;
/*      */     }
/* 6579 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6580 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6585 */     PageContext pageContext = _jspx_page_context;
/* 6586 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6588 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6589 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 6590 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 6592 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 6594 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=1");
/* 6595 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 6596 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 6597 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6598 */       return true;
/*      */     }
/* 6600 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6601 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6606 */     PageContext pageContext = _jspx_page_context;
/* 6607 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6609 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6610 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 6611 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6613 */     _jspx_th_c_005fif_005f2.setTest("${(not empty param.fromTab)}");
/* 6614 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 6615 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 6617 */         out.write(10);
/* 6618 */         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 6619 */           return true;
/* 6620 */         out.write(10);
/* 6621 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 6622 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6626 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 6627 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6628 */       return true;
/*      */     }
/* 6630 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6631 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6636 */     PageContext pageContext = _jspx_page_context;
/* 6637 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6639 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6640 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 6641 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6643 */     _jspx_th_tiles_005fput_005f2.setName("title");
/*      */     
/* 6645 */     _jspx_th_tiles_005fput_005f2.setValue("");
/* 6646 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 6647 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 6648 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 6649 */       return true;
/*      */     }
/* 6651 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 6652 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6657 */     PageContext pageContext = _jspx_page_context;
/* 6658 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6660 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6661 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 6662 */     _jspx_th_c_005fif_005f4.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6664 */     _jspx_th_c_005fif_005f4.setTest("${!empty param.haid}");
/* 6665 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 6666 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 6668 */         out.write(10);
/* 6669 */         if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 6670 */           return true;
/* 6671 */         out.write(10);
/* 6672 */         if (_jspx_meth_c_005fif_005f5(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 6673 */           return true;
/* 6674 */         out.write(10);
/* 6675 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 6676 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6680 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 6681 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6682 */       return true;
/*      */     }
/* 6684 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6685 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6690 */     PageContext pageContext = _jspx_page_context;
/* 6691 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6693 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 6694 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 6695 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6697 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 6698 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 6700 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 6701 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 6703 */           out.write("\n      ");
/* 6704 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 6705 */             return true;
/* 6706 */           out.write("\n      ");
/* 6707 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 6708 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6712 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 6713 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6716 */         int tmp185_184 = 0; int[] tmp185_182 = _jspx_push_body_count_c_005fcatch_005f0; int tmp187_186 = tmp185_182[tmp185_184];tmp185_182[tmp185_184] = (tmp187_186 - 1); if (tmp187_186 <= 0) break;
/* 6717 */         out = _jspx_page_context.popBody(); }
/* 6718 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 6720 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 6721 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 6723 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 6728 */     PageContext pageContext = _jspx_page_context;
/* 6729 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6731 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 6732 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 6733 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 6735 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 6737 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 6738 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 6739 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 6740 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6741 */       return true;
/*      */     }
/* 6743 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6749 */     PageContext pageContext = _jspx_page_context;
/* 6750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6752 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6753 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 6754 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6756 */     _jspx_th_c_005fif_005f5.setTest("${(empty invalidhaid)}");
/* 6757 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 6758 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 6760 */         out.write(10);
/* 6761 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 6762 */           return true;
/* 6763 */         out.write(10);
/* 6764 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 6765 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6769 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 6770 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6771 */       return true;
/*      */     }
/* 6773 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6779 */     PageContext pageContext = _jspx_page_context;
/* 6780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6782 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6783 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 6784 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 6786 */     _jspx_th_c_005fset_005f0.setVar("haid");
/* 6787 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 6788 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 6789 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 6790 */         out = _jspx_page_context.pushBody();
/* 6791 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 6792 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6795 */         out.write(10);
/* 6796 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 6797 */           return true;
/* 6798 */         out.write(10);
/* 6799 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 6800 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6803 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 6804 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6807 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 6808 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 6809 */       return true;
/*      */     }
/* 6811 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 6812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6817 */     PageContext pageContext = _jspx_page_context;
/* 6818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6820 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6821 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 6822 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 6824 */     _jspx_th_c_005fout_005f1.setValue("${param.haid}");
/* 6825 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 6826 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 6827 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6828 */       return true;
/*      */     }
/* 6830 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6836 */     PageContext pageContext = _jspx_page_context;
/* 6837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6839 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 6840 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6841 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 6843 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.eum.agentdown");
/* 6844 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6845 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 6846 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6847 */         out = _jspx_page_context.pushBody();
/* 6848 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 6849 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6852 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 6853 */         if (_jspx_meth_fmt_005fparam_005f0(_jspx_th_fmt_005fmessage_005f0, _jspx_page_context))
/* 6854 */           return true;
/* 6855 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 6856 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 6857 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6860 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6861 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6864 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6865 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6866 */       return true;
/*      */     }
/* 6868 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f0(JspTag _jspx_th_fmt_005fmessage_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6874 */     PageContext pageContext = _jspx_page_context;
/* 6875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6877 */     ParamTag _jspx_th_fmt_005fparam_005f0 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 6878 */     _jspx_th_fmt_005fparam_005f0.setPageContext(_jspx_page_context);
/* 6879 */     _jspx_th_fmt_005fparam_005f0.setParent((Tag)_jspx_th_fmt_005fmessage_005f0);
/* 6880 */     int _jspx_eval_fmt_005fparam_005f0 = _jspx_th_fmt_005fparam_005f0.doStartTag();
/* 6881 */     if (_jspx_eval_fmt_005fparam_005f0 != 0) {
/* 6882 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 6883 */         out = _jspx_page_context.pushBody();
/* 6884 */         _jspx_th_fmt_005fparam_005f0.setBodyContent((BodyContent)out);
/* 6885 */         _jspx_th_fmt_005fparam_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6888 */         if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_fmt_005fparam_005f0, _jspx_page_context))
/* 6889 */           return true;
/* 6890 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f0.doAfterBody();
/* 6891 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6894 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 6895 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6898 */     if (_jspx_th_fmt_005fparam_005f0.doEndTag() == 5) {
/* 6899 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 6900 */       return true;
/*      */     }
/* 6902 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 6903 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_fmt_005fparam_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6908 */     PageContext pageContext = _jspx_page_context;
/* 6909 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6911 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(WriteTag.class);
/* 6912 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 6913 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_fmt_005fparam_005f0);
/*      */     
/* 6915 */     _jspx_th_bean_005fwrite_005f0.setName("agents");
/* 6916 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 6917 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 6918 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 6919 */       return true;
/*      */     }
/* 6921 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 6922 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6927 */     PageContext pageContext = _jspx_page_context;
/* 6928 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6930 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6931 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 6932 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6934 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 6935 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 6936 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 6937 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6938 */       return true;
/*      */     }
/* 6940 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6941 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6946 */     PageContext pageContext = _jspx_page_context;
/* 6947 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6949 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6950 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6951 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6953 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 6954 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6955 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6956 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6957 */       return true;
/*      */     }
/* 6959 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6960 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6965 */     PageContext pageContext = _jspx_page_context;
/* 6966 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6968 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6969 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 6970 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6972 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 6973 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 6974 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 6976 */         out.write("\n   \t\t\t alertUser();\n   \t\t \treturn;\n   \t\t");
/* 6977 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 6978 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6982 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 6983 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 6984 */       return true;
/*      */     }
/* 6986 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 6987 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6992 */     PageContext pageContext = _jspx_page_context;
/* 6993 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6995 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6996 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6997 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6999 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 7000 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 7001 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 7002 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 7003 */       return true;
/*      */     }
/* 7005 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 7006 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7011 */     PageContext pageContext = _jspx_page_context;
/* 7012 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7014 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7015 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 7016 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 7018 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 7019 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 7020 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 7021 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 7022 */       return true;
/*      */     }
/* 7024 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 7025 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7030 */     PageContext pageContext = _jspx_page_context;
/* 7031 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7033 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7034 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 7035 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 7037 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 7038 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 7039 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 7040 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 7041 */       return true;
/*      */     }
/* 7043 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 7044 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7049 */     PageContext pageContext = _jspx_page_context;
/* 7050 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7052 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7053 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 7054 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 7056 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 7057 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 7058 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 7059 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7060 */       return true;
/*      */     }
/* 7062 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7063 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7068 */     PageContext pageContext = _jspx_page_context;
/* 7069 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7071 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7072 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 7073 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 7075 */     _jspx_th_c_005fout_005f8.setValue("${parentids}");
/* 7076 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 7077 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 7078 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 7079 */       return true;
/*      */     }
/* 7081 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 7082 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7087 */     PageContext pageContext = _jspx_page_context;
/* 7088 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7090 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7091 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 7092 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 7094 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 7095 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 7096 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 7097 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 7098 */       return true;
/*      */     }
/* 7100 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 7101 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fnotPresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7106 */     PageContext pageContext = _jspx_page_context;
/* 7107 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7109 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7110 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 7111 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fnotPresent_005f5);
/*      */     
/* 7113 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 7114 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 7115 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 7116 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 7117 */       return true;
/*      */     }
/* 7119 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 7120 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7125 */     PageContext pageContext = _jspx_page_context;
/* 7126 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7128 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7129 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 7130 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 7132 */     _jspx_th_c_005fout_005f11.setValue("${eachCustomConfig.DESCRIPTION}");
/* 7133 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 7134 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 7135 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 7136 */       return true;
/*      */     }
/* 7138 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 7139 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7144 */     PageContext pageContext = _jspx_page_context;
/* 7145 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7147 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7148 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 7149 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 7151 */     _jspx_th_c_005fout_005f12.setValue("${eachCustomConfig.IMAGE}");
/* 7152 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 7153 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 7154 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 7155 */       return true;
/*      */     }
/* 7157 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 7158 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7163 */     PageContext pageContext = _jspx_page_context;
/* 7164 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7166 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7167 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 7168 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 7170 */     _jspx_th_c_005fout_005f13.setValue("${eachCustomConfig.DISPLAYNAME}");
/* 7171 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 7172 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 7173 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 7174 */       return true;
/*      */     }
/* 7176 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 7177 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7182 */     PageContext pageContext = _jspx_page_context;
/* 7183 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7185 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7186 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 7187 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 7189 */     _jspx_th_c_005fout_005f14.setValue("${eachCustomConfig.DESCRIPTION}");
/* 7190 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 7191 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 7192 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 7193 */       return true;
/*      */     }
/* 7195 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 7196 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7201 */     PageContext pageContext = _jspx_page_context;
/* 7202 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7204 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7205 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 7206 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 7208 */     _jspx_th_c_005fout_005f15.setValue("${eachCustomConfig.METHODNAME}");
/* 7209 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 7210 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 7211 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 7212 */       return true;
/*      */     }
/* 7214 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 7215 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7220 */     PageContext pageContext = _jspx_page_context;
/* 7221 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7223 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7224 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 7225 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 7227 */     _jspx_th_c_005fout_005f16.setValue("${eachCustomConfig.PopupProps.WinName}");
/* 7228 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 7229 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 7230 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 7231 */       return true;
/*      */     }
/* 7233 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 7234 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7239 */     PageContext pageContext = _jspx_page_context;
/* 7240 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7242 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7243 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 7244 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 7246 */     _jspx_th_c_005fout_005f17.setValue("${eachCustomConfig.PopupProps.Height}");
/* 7247 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 7248 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 7249 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 7250 */       return true;
/*      */     }
/* 7252 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 7253 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7258 */     PageContext pageContext = _jspx_page_context;
/* 7259 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7261 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7262 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 7263 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 7265 */     _jspx_th_c_005fout_005f18.setValue("${eachCustomConfig.PopupProps.Width}");
/* 7266 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 7267 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 7268 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 7269 */       return true;
/*      */     }
/* 7271 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 7272 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7277 */     PageContext pageContext = _jspx_page_context;
/* 7278 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7280 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7281 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 7282 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 7284 */     _jspx_th_c_005fout_005f19.setValue("${eachCustomConfig.PopupProps.Top}");
/* 7285 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 7286 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 7287 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 7288 */       return true;
/*      */     }
/* 7290 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 7291 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7296 */     PageContext pageContext = _jspx_page_context;
/* 7297 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7299 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7300 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 7301 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 7303 */     _jspx_th_c_005fout_005f20.setValue("${eachCustomConfig.PopupProps.Left}");
/* 7304 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 7305 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 7306 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 7307 */       return true;
/*      */     }
/* 7309 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 7310 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7315 */     PageContext pageContext = _jspx_page_context;
/* 7316 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7318 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7319 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 7320 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 7322 */     _jspx_th_c_005fout_005f21.setValue("${eachCustomConfig.PopupProps.IsResizable}");
/* 7323 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 7324 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 7325 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 7326 */       return true;
/*      */     }
/* 7328 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 7329 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7334 */     PageContext pageContext = _jspx_page_context;
/* 7335 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7337 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7338 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 7339 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 7341 */     _jspx_th_c_005fout_005f22.setValue("${eachCustomConfig.PopupProps.IsScrollBar}");
/* 7342 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 7343 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 7344 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 7345 */       return true;
/*      */     }
/* 7347 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 7348 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7353 */     PageContext pageContext = _jspx_page_context;
/* 7354 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7356 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7357 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 7358 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 7360 */     _jspx_th_c_005fout_005f23.setValue("${eachCustomConfig.IMAGE}");
/* 7361 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 7362 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 7363 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 7364 */       return true;
/*      */     }
/* 7366 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 7367 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 7372 */     PageContext pageContext = _jspx_page_context;
/* 7373 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7375 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7376 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 7377 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 7379 */     _jspx_th_c_005fout_005f24.setValue("${eachCustomConfig.DISPLAYNAME}");
/* 7380 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 7381 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 7382 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 7383 */       return true;
/*      */     }
/* 7385 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 7386 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7391 */     PageContext pageContext = _jspx_page_context;
/* 7392 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7394 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7395 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 7396 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7398 */     _jspx_th_c_005fout_005f25.setValue("${eachCustomConfig.DESCRIPTION}");
/* 7399 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 7400 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 7401 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 7402 */       return true;
/*      */     }
/* 7404 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 7405 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7410 */     PageContext pageContext = _jspx_page_context;
/* 7411 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7413 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7414 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 7415 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 7417 */     _jspx_th_c_005fout_005f26.setValue("${param.resourceid}");
/* 7418 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 7419 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 7420 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 7421 */       return true;
/*      */     }
/* 7423 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 7424 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7429 */     PageContext pageContext = _jspx_page_context;
/* 7430 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7432 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7433 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 7434 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 7436 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 7437 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 7438 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 7439 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 7440 */       return true;
/*      */     }
/* 7442 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 7443 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7448 */     PageContext pageContext = _jspx_page_context;
/* 7449 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7451 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7452 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 7453 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 7455 */     _jspx_th_c_005fout_005f28.setValue("${param.resourceid}");
/* 7456 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 7457 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 7458 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 7459 */       return true;
/*      */     }
/* 7461 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 7462 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7467 */     PageContext pageContext = _jspx_page_context;
/* 7468 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7470 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7471 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 7472 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 7474 */     _jspx_th_c_005fout_005f29.setValue("${param.resourceid}");
/* 7475 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 7476 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 7477 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 7478 */       return true;
/*      */     }
/* 7480 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 7481 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7486 */     PageContext pageContext = _jspx_page_context;
/* 7487 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7489 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7490 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 7491 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7493 */     _jspx_th_c_005fout_005f30.setValue("${ha.key}");
/* 7494 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 7495 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 7496 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 7497 */       return true;
/*      */     }
/* 7499 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 7500 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7505 */     PageContext pageContext = _jspx_page_context;
/* 7506 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7508 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7509 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 7510 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7512 */     _jspx_th_c_005fout_005f31.setValue("${ha.value}");
/* 7513 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 7514 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 7515 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 7516 */       return true;
/*      */     }
/* 7518 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 7519 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7524 */     PageContext pageContext = _jspx_page_context;
/* 7525 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7527 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 7528 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 7529 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7531 */     _jspx_th_c_005fset_005f5.setVar("monitorName");
/* 7532 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 7533 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 7534 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 7535 */         out = _jspx_page_context.pushBody();
/* 7536 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 7537 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 7538 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7541 */         if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fset_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7542 */           return true;
/* 7543 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 7544 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7547 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 7548 */         out = _jspx_page_context.popBody();
/* 7549 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 7552 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 7553 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 7554 */       return true;
/*      */     }
/* 7556 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 7557 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7562 */     PageContext pageContext = _jspx_page_context;
/* 7563 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7565 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7566 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 7567 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 7569 */     _jspx_th_c_005fout_005f32.setValue("${ha.value}");
/* 7570 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 7571 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 7572 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 7573 */       return true;
/*      */     }
/* 7575 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 7576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_logic_005fpresent_005f15, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7581 */     PageContext pageContext = _jspx_page_context;
/* 7582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7584 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7585 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 7586 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_logic_005fpresent_005f15);
/*      */     
/* 7588 */     _jspx_th_c_005fout_005f33.setValue("${ha.key}");
/* 7589 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 7590 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 7591 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 7592 */       return true;
/*      */     }
/* 7594 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 7595 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f19, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7600 */     PageContext pageContext = _jspx_page_context;
/* 7601 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7603 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7604 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 7605 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f19);
/*      */     
/* 7607 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 7608 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 7609 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 7610 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7611 */       return true;
/*      */     }
/* 7613 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7614 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7619 */     PageContext pageContext = _jspx_page_context;
/* 7620 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7622 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7623 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 7624 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 7626 */     _jspx_th_c_005fout_005f34.setValue("${ha.key}");
/* 7627 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 7628 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 7629 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 7630 */       return true;
/*      */     }
/* 7632 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 7633 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7638 */     PageContext pageContext = _jspx_page_context;
/* 7639 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7641 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7642 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 7643 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 7645 */     _jspx_th_c_005fout_005f35.setValue("${ha.value}");
/* 7646 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 7647 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 7648 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 7649 */       return true;
/*      */     }
/* 7651 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 7652 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7657 */     PageContext pageContext = _jspx_page_context;
/* 7658 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7660 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 7661 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 7662 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 7664 */     _jspx_th_c_005fset_005f6.setVar("monitorName");
/* 7665 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 7666 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 7667 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 7668 */         out = _jspx_page_context.pushBody();
/* 7669 */         _jspx_push_body_count_c_005fforEach_005f2[0] += 1;
/* 7670 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 7671 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7674 */         if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fset_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 7675 */           return true;
/* 7676 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 7677 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7680 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 7681 */         out = _jspx_page_context.popBody();
/* 7682 */         _jspx_push_body_count_c_005fforEach_005f2[0] -= 1;
/*      */       }
/*      */     }
/* 7685 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 7686 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 7687 */       return true;
/*      */     }
/* 7689 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 7690 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7695 */     PageContext pageContext = _jspx_page_context;
/* 7696 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7698 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7699 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 7700 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 7702 */     _jspx_th_c_005fout_005f36.setValue("${ha.value}");
/* 7703 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 7704 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 7705 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 7706 */       return true;
/*      */     }
/* 7708 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 7709 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_logic_005fpresent_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7714 */     PageContext pageContext = _jspx_page_context;
/* 7715 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7717 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7718 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 7719 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_logic_005fpresent_005f16);
/*      */     
/* 7721 */     _jspx_th_c_005fout_005f37.setValue("${ha.key}");
/* 7722 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 7723 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 7724 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 7725 */       return true;
/*      */     }
/* 7727 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 7728 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_logic_005fpresent_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7733 */     PageContext pageContext = _jspx_page_context;
/* 7734 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7736 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7737 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 7738 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f16);
/*      */     
/* 7740 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 7741 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 7742 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 7743 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7744 */       return true;
/*      */     }
/* 7746 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f20(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7752 */     PageContext pageContext = _jspx_page_context;
/* 7753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7755 */     IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7756 */     _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 7757 */     _jspx_th_c_005fif_005f20.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 7759 */     _jspx_th_c_005fif_005f20.setTest("${empty associatedmgs}");
/* 7760 */     int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 7761 */     if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */       for (;;) {
/* 7763 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 7764 */         if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fif_005f20, _jspx_page_context))
/* 7765 */           return true;
/* 7766 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 7767 */         if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fif_005f20, _jspx_page_context))
/* 7768 */           return true;
/* 7769 */         out.write("</td>\n\t ");
/* 7770 */         int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 7771 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7775 */     if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 7776 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 7777 */       return true;
/*      */     }
/* 7779 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 7780 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fif_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7785 */     PageContext pageContext = _jspx_page_context;
/* 7786 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7788 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7789 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 7790 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fif_005f20);
/*      */     
/* 7792 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 7793 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 7794 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 7795 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 7796 */       return true;
/*      */     }
/* 7798 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 7799 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fif_005f20, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7804 */     PageContext pageContext = _jspx_page_context;
/* 7805 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7807 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7808 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 7809 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fif_005f20);
/*      */     
/* 7811 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.urlmonitor.none.text");
/* 7812 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 7813 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 7814 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7815 */       return true;
/*      */     }
/* 7817 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7818 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7823 */     PageContext pageContext = _jspx_page_context;
/* 7824 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7826 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7827 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 7828 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 7830 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 7831 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 7832 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 7833 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 7834 */       return true;
/*      */     }
/* 7836 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 7837 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7842 */     PageContext pageContext = _jspx_page_context;
/* 7843 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7845 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7846 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 7847 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 7849 */     _jspx_th_c_005fout_005f38.setValue("${granularitySelected}");
/* 7850 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 7851 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 7852 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 7853 */       return true;
/*      */     }
/* 7855 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 7856 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7861 */     PageContext pageContext = _jspx_page_context;
/* 7862 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7864 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7865 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 7866 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 7868 */     _jspx_th_c_005fout_005f39.setValue("${perioicitySelected}");
/* 7869 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 7870 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 7871 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 7872 */       return true;
/*      */     }
/* 7874 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 7875 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7880 */     PageContext pageContext = _jspx_page_context;
/* 7881 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7883 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7884 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 7885 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 7887 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.webclient.ajax.loadingPage");
/* 7888 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 7889 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 7890 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 7891 */       return true;
/*      */     }
/* 7893 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 7894 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7899 */     PageContext pageContext = _jspx_page_context;
/* 7900 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7902 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 7903 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 7904 */     _jspx_th_c_005fout_005f40.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 7906 */     _jspx_th_c_005fout_005f40.setValue("${myValue}");
/*      */     
/* 7908 */     _jspx_th_c_005fout_005f40.setEscapeXml("${escape}");
/* 7909 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 7910 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 7911 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 7912 */       return true;
/*      */     }
/* 7914 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 7915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f41(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7920 */     PageContext pageContext = _jspx_page_context;
/* 7921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7923 */     OutTag _jspx_th_c_005fout_005f41 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 7924 */     _jspx_th_c_005fout_005f41.setPageContext(_jspx_page_context);
/* 7925 */     _jspx_th_c_005fout_005f41.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 7927 */     _jspx_th_c_005fout_005f41.setValue("${myValue}");
/*      */     
/* 7929 */     _jspx_th_c_005fout_005f41.setEscapeXml("${escape}");
/* 7930 */     int _jspx_eval_c_005fout_005f41 = _jspx_th_c_005fout_005f41.doStartTag();
/* 7931 */     if (_jspx_th_c_005fout_005f41.doEndTag() == 5) {
/* 7932 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 7933 */       return true;
/*      */     }
/* 7935 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f41);
/* 7936 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f42(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7941 */     PageContext pageContext = _jspx_page_context;
/* 7942 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7944 */     OutTag _jspx_th_c_005fout_005f42 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7945 */     _jspx_th_c_005fout_005f42.setPageContext(_jspx_page_context);
/* 7946 */     _jspx_th_c_005fout_005f42.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 7948 */     _jspx_th_c_005fout_005f42.setValue("${perioicitySelected}");
/* 7949 */     int _jspx_eval_c_005fout_005f42 = _jspx_th_c_005fout_005f42.doStartTag();
/* 7950 */     if (_jspx_th_c_005fout_005f42.doEndTag() == 5) {
/* 7951 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 7952 */       return true;
/*      */     }
/* 7954 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f42);
/* 7955 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7960 */     PageContext pageContext = _jspx_page_context;
/* 7961 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7963 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 7964 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 7965 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 7967 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 7969 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 7970 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 7971 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 7972 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 7973 */       return true;
/*      */     }
/* 7975 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 7976 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ConfDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */