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
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import com.manageengine.it360.sp.customermanagement.CustomerManagementAPI;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
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
/*      */ import javax.servlet.http.HttpServletResponse;
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
/*      */ public final class ConfDetailsUserArea_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   75 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   78 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   79 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   80 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   87 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   92 */     ArrayList list = null;
/*   93 */     StringBuffer sbf = new StringBuffer();
/*   94 */     ManagedApplication mo = new ManagedApplication();
/*   95 */     if (distinct)
/*      */     {
/*   97 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*  101 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*  104 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*  106 */       ArrayList row = (ArrayList)list.get(i);
/*  107 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  108 */       if (distinct) {
/*  109 */         sbf.append(row.get(0));
/*      */       } else
/*  111 */         sbf.append(row.get(1));
/*  112 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  115 */     return sbf.toString(); }
/*      */   
/*  117 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  120 */     if (severity == null)
/*      */     {
/*  122 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  124 */     if (severity.equals("5"))
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  128 */     if (severity.equals("1"))
/*      */     {
/*  130 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  135 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  142 */     if (severity == null)
/*      */     {
/*  144 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  146 */     if (severity.equals("1"))
/*      */     {
/*  148 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  150 */     if (severity.equals("4"))
/*      */     {
/*  152 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  154 */     if (severity.equals("5"))
/*      */     {
/*  156 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  161 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  167 */     if (severity == null)
/*      */     {
/*  169 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  171 */     if (severity.equals("5"))
/*      */     {
/*  173 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  175 */     if (severity.equals("1"))
/*      */     {
/*  177 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  181 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  187 */     if (severity == null)
/*      */     {
/*  189 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  191 */     if (severity.equals("1"))
/*      */     {
/*  193 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  195 */     if (severity.equals("4"))
/*      */     {
/*  197 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  199 */     if (severity.equals("5"))
/*      */     {
/*  201 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  205 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  211 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  217 */     if (severity == 5)
/*      */     {
/*  219 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  221 */     if (severity == 1)
/*      */     {
/*  223 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  228 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  234 */     if (severity == null)
/*      */     {
/*  236 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  238 */     if (severity.equals("5"))
/*      */     {
/*  240 */       if (isAvailability) {
/*  241 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  244 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  247 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  249 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  251 */     if (severity.equals("1"))
/*      */     {
/*  253 */       if (isAvailability) {
/*  254 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  257 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  264 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  271 */     if (severity == null)
/*      */     {
/*  273 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  275 */     if (severity.equals("5"))
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  279 */     if (severity.equals("4"))
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  283 */     if (severity.equals("1"))
/*      */     {
/*  285 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  290 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  296 */     if (severity == null)
/*      */     {
/*  298 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  300 */     if (severity.equals("5"))
/*      */     {
/*  302 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  304 */     if (severity.equals("4"))
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  308 */     if (severity.equals("1"))
/*      */     {
/*  310 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  315 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  322 */     if (severity == null)
/*      */     {
/*  324 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  326 */     if (severity.equals("5"))
/*      */     {
/*  328 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  330 */     if (severity.equals("4"))
/*      */     {
/*  332 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  334 */     if (severity.equals("1"))
/*      */     {
/*  336 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  341 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  349 */     StringBuffer out = new StringBuffer();
/*  350 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  351 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  352 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  353 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  354 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  355 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  356 */     out.append("</tr>");
/*  357 */     out.append("</form></table>");
/*  358 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  365 */     if (val == null)
/*      */     {
/*  367 */       return "-";
/*      */     }
/*      */     
/*  370 */     String ret = FormatUtil.formatNumber(val);
/*  371 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  372 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  375 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  379 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  387 */     StringBuffer out = new StringBuffer();
/*  388 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  389 */     out.append("<tr>");
/*  390 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  392 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  394 */     out.append("</tr>");
/*  395 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  399 */       if (j % 2 == 0)
/*      */       {
/*  401 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  405 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  408 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  410 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  413 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  417 */       out.append("</tr>");
/*      */     }
/*  419 */     out.append("</table>");
/*  420 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  421 */     out.append("<tr>");
/*  422 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  423 */     out.append("</tr>");
/*  424 */     out.append("</table>");
/*  425 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  431 */     StringBuffer out = new StringBuffer();
/*  432 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  433 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  434 */     out.append("<tr>");
/*  435 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  436 */     out.append("<tr>");
/*  437 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  438 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  439 */     out.append("</tr>");
/*  440 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  443 */       out.append("<tr>");
/*  444 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  445 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  446 */       out.append("</tr>");
/*      */     }
/*      */     
/*  449 */     out.append("</table>");
/*  450 */     out.append("</table>");
/*  451 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  456 */     if (severity.equals("0"))
/*      */     {
/*  458 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  462 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  469 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  482 */     StringBuffer out = new StringBuffer();
/*  483 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  484 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  486 */       out.append("<tr>");
/*  487 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  488 */       out.append("</tr>");
/*      */       
/*      */ 
/*  491 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  493 */         String borderclass = "";
/*      */         
/*      */ 
/*  496 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  498 */         out.append("<tr>");
/*      */         
/*  500 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  501 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  502 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  508 */     out.append("</table><br>");
/*  509 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  510 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  512 */       List sLinks = secondLevelOfLinks[0];
/*  513 */       List sText = secondLevelOfLinks[1];
/*  514 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  517 */         out.append("<tr>");
/*  518 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  519 */         out.append("</tr>");
/*  520 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  522 */           String borderclass = "";
/*      */           
/*      */ 
/*  525 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  527 */           out.append("<tr>");
/*      */           
/*  529 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  530 */           if (sLinks.get(i).toString().length() == 0) {
/*  531 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  534 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  536 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  540 */     out.append("</table>");
/*  541 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  548 */     StringBuffer out = new StringBuffer();
/*  549 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  550 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  552 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  554 */         out.append("<tr>");
/*  555 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  556 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  560 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  562 */           String borderclass = "";
/*      */           
/*      */ 
/*  565 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  567 */           out.append("<tr>");
/*      */           
/*  569 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  570 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  571 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  574 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  577 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  582 */     out.append("</table><br>");
/*  583 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  584 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  586 */       List sLinks = secondLevelOfLinks[0];
/*  587 */       List sText = secondLevelOfLinks[1];
/*  588 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  591 */         out.append("<tr>");
/*  592 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  593 */         out.append("</tr>");
/*  594 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  596 */           String borderclass = "";
/*      */           
/*      */ 
/*  599 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  601 */           out.append("<tr>");
/*      */           
/*  603 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  604 */           if (sLinks.get(i).toString().length() == 0) {
/*  605 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  608 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  610 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  614 */     out.append("</table>");
/*  615 */     return out.toString();
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
/*  628 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  631 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  634 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  637 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  640 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  643 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  646 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  649 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  657 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  662 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  667 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  672 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  677 */     if (val != null)
/*      */     {
/*  679 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  683 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  688 */     if (val == null) {
/*  689 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  693 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  698 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  704 */     if (val != null)
/*      */     {
/*  706 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  710 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  716 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  721 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  725 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  730 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  735 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  740 */     String hostaddress = "";
/*  741 */     String ip = request.getHeader("x-forwarded-for");
/*  742 */     if (ip == null)
/*  743 */       ip = request.getRemoteAddr();
/*  744 */     InetAddress add = null;
/*  745 */     if (ip.equals("127.0.0.1")) {
/*  746 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  750 */       add = InetAddress.getByName(ip);
/*      */     }
/*  752 */     hostaddress = add.getHostName();
/*  753 */     if (hostaddress.indexOf('.') != -1) {
/*  754 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  755 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  759 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  764 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  770 */     if (severity == null)
/*      */     {
/*  772 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  774 */     if (severity.equals("5"))
/*      */     {
/*  776 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  778 */     if (severity.equals("1"))
/*      */     {
/*  780 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  785 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  790 */     ResultSet set = null;
/*  791 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  792 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  794 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  795 */       if (set.next()) { String str1;
/*  796 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  797 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  800 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  805 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  808 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  810 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  814 */     StringBuffer rca = new StringBuffer();
/*  815 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  816 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  819 */     int rcalength = key.length();
/*  820 */     String split = "6. ";
/*  821 */     int splitPresent = key.indexOf(split);
/*  822 */     String div1 = "";String div2 = "";
/*  823 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  825 */       if (rcalength > 180) {
/*  826 */         rca.append("<span class=\"rca-critical-text\">");
/*  827 */         getRCATrimmedText(key, rca);
/*  828 */         rca.append("</span>");
/*      */       } else {
/*  830 */         rca.append("<span class=\"rca-critical-text\">");
/*  831 */         rca.append(key);
/*  832 */         rca.append("</span>");
/*      */       }
/*  834 */       return rca.toString();
/*      */     }
/*  836 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  837 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  838 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  839 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  840 */     getRCATrimmedText(div1, rca);
/*  841 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  844 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  845 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  846 */     getRCATrimmedText(div2, rca);
/*  847 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  849 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  854 */     String[] st = msg.split("<br>");
/*  855 */     for (int i = 0; i < st.length; i++) {
/*  856 */       String s = st[i];
/*  857 */       if (s.length() > 180) {
/*  858 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  860 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  864 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  865 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  867 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  871 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  872 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  873 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  876 */       if (key == null) {
/*  877 */         return ret;
/*      */       }
/*      */       
/*  880 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  881 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  884 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  885 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  886 */       set = AMConnectionPool.executeQueryStmt(query);
/*  887 */       if (set.next())
/*      */       {
/*  889 */         String helpLink = set.getString("LINK");
/*  890 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  893 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  899 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  918 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  909 */         if (set != null) {
/*  910 */           AMConnectionPool.closeStatement(set);
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
/*  924 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  925 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  927 */       String entityStr = (String)keys.nextElement();
/*  928 */       String mmessage = temp.getProperty(entityStr);
/*  929 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  930 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  932 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  938 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  939 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  941 */       String entityStr = (String)keys.nextElement();
/*  942 */       String mmessage = temp.getProperty(entityStr);
/*  943 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  944 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  946 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  951 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  961 */     String des = new String();
/*  962 */     while (str.indexOf(find) != -1) {
/*  963 */       des = des + str.substring(0, str.indexOf(find));
/*  964 */       des = des + replace;
/*  965 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  967 */     des = des + str;
/*  968 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  975 */       if (alert == null)
/*      */       {
/*  977 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  979 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  981 */         return "&nbsp;";
/*      */       }
/*      */       
/*  984 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  986 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  989 */       int rcalength = test.length();
/*  990 */       if (rcalength < 300)
/*      */       {
/*  992 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  996 */       StringBuffer out = new StringBuffer();
/*  997 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  998 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  999 */       out.append("</div>");
/* 1000 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/* 1001 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/* 1002 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1007 */       ex.printStackTrace();
/*      */     }
/* 1009 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1015 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1020 */     ArrayList attribIDs = new ArrayList();
/* 1021 */     ArrayList resIDs = new ArrayList();
/* 1022 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1024 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1026 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1028 */       String resourceid = "";
/* 1029 */       String resourceType = "";
/* 1030 */       if (type == 2) {
/* 1031 */         resourceid = (String)row.get(0);
/* 1032 */         resourceType = (String)row.get(3);
/*      */       }
/* 1034 */       else if (type == 3) {
/* 1035 */         resourceid = (String)row.get(0);
/* 1036 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1039 */         resourceid = (String)row.get(6);
/* 1040 */         resourceType = (String)row.get(7);
/*      */       }
/* 1042 */       resIDs.add(resourceid);
/* 1043 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1044 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1046 */       String healthentity = null;
/* 1047 */       String availentity = null;
/* 1048 */       if (healthid != null) {
/* 1049 */         healthentity = resourceid + "_" + healthid;
/* 1050 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1053 */       if (availid != null) {
/* 1054 */         availentity = resourceid + "_" + availid;
/* 1055 */         entitylist.add(availentity);
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
/* 1069 */     Properties alert = getStatus(entitylist);
/* 1070 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1075 */     int size = monitorList.size();
/*      */     
/* 1077 */     String[] severity = new String[size];
/*      */     
/* 1079 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1081 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1082 */       String resourceName1 = (String)row1.get(7);
/* 1083 */       String resourceid1 = (String)row1.get(6);
/* 1084 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1085 */       if (severity[j] == null)
/*      */       {
/* 1087 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1091 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1093 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1095 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1098 */         if (sev > 0) {
/* 1099 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1100 */           monitorList.set(k, monitorList.get(j));
/* 1101 */           monitorList.set(j, t);
/* 1102 */           String temp = severity[k];
/* 1103 */           severity[k] = severity[j];
/* 1104 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1110 */     int z = 0;
/* 1111 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1114 */       int i = 0;
/* 1115 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1118 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1122 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1126 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1128 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1131 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1135 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1138 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1139 */       String resourceName1 = (String)row1.get(7);
/* 1140 */       String resourceid1 = (String)row1.get(6);
/* 1141 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1142 */       if (hseverity[j] == null)
/*      */       {
/* 1144 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1149 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1151 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1154 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1157 */         if (hsev > 0) {
/* 1158 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1159 */           monitorList.set(k, monitorList.get(j));
/* 1160 */           monitorList.set(j, t);
/* 1161 */           String temp1 = hseverity[k];
/* 1162 */           hseverity[k] = hseverity[j];
/* 1163 */           hseverity[j] = temp1;
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
/* 1175 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1176 */     boolean forInventory = false;
/* 1177 */     String trdisplay = "none";
/* 1178 */     String plusstyle = "inline";
/* 1179 */     String minusstyle = "none";
/* 1180 */     String haidTopLevel = "";
/* 1181 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1183 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1185 */         haidTopLevel = request.getParameter("haid");
/* 1186 */         forInventory = true;
/* 1187 */         trdisplay = "table-row;";
/* 1188 */         plusstyle = "none";
/* 1189 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1196 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1199 */     ArrayList listtoreturn = new ArrayList();
/* 1200 */     StringBuffer toreturn = new StringBuffer();
/* 1201 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1202 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1203 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1205 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1207 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1208 */       String childresid = (String)singlerow.get(0);
/* 1209 */       String childresname = (String)singlerow.get(1);
/* 1210 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1211 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1212 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1213 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1214 */       String unmanagestatus = (String)singlerow.get(5);
/* 1215 */       String actionstatus = (String)singlerow.get(6);
/* 1216 */       String linkclass = "monitorgp-links";
/* 1217 */       String titleforres = childresname;
/* 1218 */       String titilechildresname = childresname;
/* 1219 */       String childimg = "/images/trcont.png";
/* 1220 */       String flag = "enable";
/* 1221 */       String dcstarted = (String)singlerow.get(8);
/* 1222 */       String configMonitor = "";
/* 1223 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1224 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1226 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1228 */       if (singlerow.get(7) != null)
/*      */       {
/* 1230 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1232 */       String haiGroupType = "0";
/* 1233 */       if ("HAI".equals(childtype))
/*      */       {
/* 1235 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1237 */       childimg = "/images/trend.png";
/* 1238 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1239 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1240 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1242 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1244 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1246 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1247 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1250 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1252 */         linkclass = "disabledtext";
/* 1253 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1255 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1256 */       String availmouseover = "";
/* 1257 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1259 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1261 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1262 */       String healthmouseover = "";
/* 1263 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1265 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1268 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1269 */       int spacing = 0;
/* 1270 */       if (level >= 1)
/*      */       {
/* 1272 */         spacing = 40 * level;
/*      */       }
/* 1274 */       if (childtype.equals("HAI"))
/*      */       {
/* 1276 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1277 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1278 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1280 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1281 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1282 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1283 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1284 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1285 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1286 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1287 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1288 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1289 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1290 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1292 */         if (!forInventory)
/*      */         {
/* 1294 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1297 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1299 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1301 */           actions = editlink + actions;
/*      */         }
/* 1303 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1305 */           actions = actions + associatelink;
/*      */         }
/* 1307 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1308 */         String arrowimg = "";
/* 1309 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1311 */           actions = "";
/* 1312 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1313 */           checkbox = "";
/* 1314 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1316 */         if (isIt360)
/*      */         {
/* 1318 */           actionimg = "";
/* 1319 */           actions = "";
/* 1320 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1321 */           checkbox = "";
/*      */         }
/*      */         
/* 1324 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1326 */           actions = "";
/*      */         }
/* 1328 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1330 */           checkbox = "";
/*      */         }
/*      */         
/* 1333 */         String resourcelink = "";
/*      */         
/* 1335 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1337 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1341 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1344 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1345 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1346 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1347 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1348 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1349 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1350 */         if (!isIt360)
/*      */         {
/* 1352 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1356 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1359 */         toreturn.append("</tr>");
/* 1360 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1362 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1363 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1367 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1368 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1371 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1375 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1377 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1378 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1379 */             toreturn.append(assocMessage);
/* 1380 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1381 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1382 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1383 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1389 */         String resourcelink = null;
/* 1390 */         boolean hideEditLink = false;
/* 1391 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1393 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1394 */           hideEditLink = true;
/* 1395 */           if (isIt360)
/*      */           {
/* 1397 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1401 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1403 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1405 */           hideEditLink = true;
/* 1406 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1407 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1412 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1415 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1416 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1417 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1418 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1419 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1420 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1421 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1422 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1423 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1424 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1425 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1426 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1427 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1429 */         if (hideEditLink)
/*      */         {
/* 1431 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1433 */         if (!forInventory)
/*      */         {
/* 1435 */           removefromgroup = "";
/*      */         }
/* 1437 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1438 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1439 */           actions = actions + configcustomfields;
/*      */         }
/* 1441 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1443 */           actions = editlink + actions;
/*      */         }
/* 1445 */         String managedLink = "";
/* 1446 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1448 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1449 */           actions = "";
/* 1450 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1451 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1454 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1456 */           checkbox = "";
/*      */         }
/*      */         
/* 1459 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1461 */           actions = "";
/*      */         }
/* 1463 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1464 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1465 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1466 */         if (isIt360)
/*      */         {
/* 1468 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1472 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1474 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1475 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1476 */         if (!isIt360)
/*      */         {
/* 1478 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1482 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1484 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1487 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1494 */       StringBuilder toreturn = new StringBuilder();
/* 1495 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1496 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1497 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1498 */       String title = "";
/* 1499 */       message = EnterpriseUtil.decodeString(message);
/* 1500 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1501 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1502 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1504 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1506 */       else if ("5".equals(severity))
/*      */       {
/* 1508 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1512 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1514 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1515 */       toreturn.append(v);
/*      */       
/* 1517 */       toreturn.append(link);
/* 1518 */       if (severity == null)
/*      */       {
/* 1520 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1522 */       else if (severity.equals("5"))
/*      */       {
/* 1524 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1526 */       else if (severity.equals("4"))
/*      */       {
/* 1528 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1530 */       else if (severity.equals("1"))
/*      */       {
/* 1532 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1537 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1539 */       toreturn.append("</a>");
/* 1540 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1544 */       ex.printStackTrace();
/*      */     }
/* 1546 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1553 */       StringBuilder toreturn = new StringBuilder();
/* 1554 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1555 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1556 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1557 */       if (message == null)
/*      */       {
/* 1559 */         message = "";
/*      */       }
/*      */       
/* 1562 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1563 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1565 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1566 */       toreturn.append(v);
/*      */       
/* 1568 */       toreturn.append(link);
/*      */       
/* 1570 */       if (severity == null)
/*      */       {
/* 1572 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1574 */       else if (severity.equals("5"))
/*      */       {
/* 1576 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1578 */       else if (severity.equals("1"))
/*      */       {
/* 1580 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1585 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1587 */       toreturn.append("</a>");
/* 1588 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1594 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1597 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1598 */     if (invokeActions != null) {
/* 1599 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1600 */       while (iterator.hasNext()) {
/* 1601 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1602 */         if (actionmap.containsKey(actionid)) {
/* 1603 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1608 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1612 */     String actionLink = "";
/* 1613 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1614 */     String query = "";
/* 1615 */     ResultSet rs = null;
/* 1616 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1617 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1618 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1619 */       actionLink = "method=" + methodName;
/*      */     }
/* 1621 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1622 */       actionLink = methodName;
/*      */     }
/* 1624 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1625 */     Iterator itr = methodarglist.iterator();
/* 1626 */     boolean isfirstparam = true;
/* 1627 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1628 */     while (itr.hasNext()) {
/* 1629 */       HashMap argmap = (HashMap)itr.next();
/* 1630 */       String argtype = (String)argmap.get("TYPE");
/* 1631 */       String argname = (String)argmap.get("IDENTITY");
/* 1632 */       String paramname = (String)argmap.get("PARAMETER");
/* 1633 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1634 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1635 */         isfirstparam = false;
/* 1636 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1638 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1642 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1646 */         actionLink = actionLink + "&";
/*      */       }
/* 1648 */       String paramValue = null;
/* 1649 */       String tempargname = argname;
/* 1650 */       if (commonValues.getProperty(tempargname) != null) {
/* 1651 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1654 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1655 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1656 */           if (dbType.equals("mysql")) {
/* 1657 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1660 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1662 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1664 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1665 */             if (rs.next()) {
/* 1666 */               paramValue = rs.getString("VALUE");
/* 1667 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1671 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1675 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1678 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1683 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1684 */           paramValue = rowId;
/*      */         }
/* 1686 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1687 */           paramValue = managedObjectName;
/*      */         }
/* 1689 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1690 */           paramValue = resID;
/*      */         }
/* 1692 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1693 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1696 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1698 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1699 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1700 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1702 */     return actionLink;
/*      */   }
/*      */   
/* 1705 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1706 */     String dependentAttribute = null;
/* 1707 */     String align = "left";
/*      */     
/* 1709 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1710 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1711 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1712 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1713 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1714 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1715 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1716 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1717 */       align = "center";
/*      */     }
/*      */     
/* 1720 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1721 */     String actualdata = "";
/*      */     
/* 1723 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1724 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1725 */         actualdata = availValue;
/*      */       }
/* 1727 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1728 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1732 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1733 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1736 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1742 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1743 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1744 */       toreturn.append("<table>");
/* 1745 */       toreturn.append("<tr>");
/* 1746 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1747 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1748 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1749 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1750 */         String toolTip = "";
/* 1751 */         String hideClass = "";
/* 1752 */         String textStyle = "";
/* 1753 */         boolean isreferenced = true;
/* 1754 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1755 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1756 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1757 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1759 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1760 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1761 */           while (valueList.hasMoreTokens()) {
/* 1762 */             String dependentVal = valueList.nextToken();
/* 1763 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1764 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1765 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1767 */               toolTip = "";
/* 1768 */               hideClass = "";
/* 1769 */               isreferenced = false;
/* 1770 */               textStyle = "disabledtext";
/* 1771 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1775 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1776 */           toolTip = "";
/* 1777 */           hideClass = "";
/* 1778 */           isreferenced = false;
/* 1779 */           textStyle = "disabledtext";
/* 1780 */           if (dependentImageMap != null) {
/* 1781 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1782 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1785 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1789 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1790 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1791 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1792 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1793 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1794 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1796 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1797 */           if (isreferenced) {
/* 1798 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1802 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1803 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1804 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1805 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1806 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1807 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1809 */           toreturn.append("</span>");
/* 1810 */           toreturn.append("</a>");
/* 1811 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1814 */       toreturn.append("</tr>");
/* 1815 */       toreturn.append("</table>");
/* 1816 */       toreturn.append("</td>");
/*      */     } else {
/* 1818 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1821 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1825 */     String colTime = null;
/* 1826 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1827 */     if ((rows != null) && (rows.size() > 0)) {
/* 1828 */       Iterator<String> itr = rows.iterator();
/* 1829 */       String maxColQuery = "";
/* 1830 */       for (;;) { if (itr.hasNext()) {
/* 1831 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1832 */           ResultSet maxCol = null;
/*      */           try {
/* 1834 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1835 */             while (maxCol.next()) {
/* 1836 */               if (colTime == null) {
/* 1837 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1840 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1849 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1851 */               if (maxCol != null)
/* 1852 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1854 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1849 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1851 */               if (maxCol != null)
/* 1852 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1854 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1859 */     return colTime;
/*      */   }
/*      */   
/* 1862 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1863 */     tablename = null;
/* 1864 */     ResultSet rsTable = null;
/* 1865 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1867 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1868 */       while (rsTable.next()) {
/* 1869 */         tablename = rsTable.getString("DATATABLE");
/* 1870 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1871 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1884 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1875 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1878 */         if (rsTable != null)
/* 1879 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1881 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1887 */     String argsList = "";
/* 1888 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1890 */       if (showArgsMap.get(row) != null) {
/* 1891 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1892 */         if (showArgslist != null) {
/* 1893 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1894 */             if (argsList.trim().equals("")) {
/* 1895 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1898 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1905 */       e.printStackTrace();
/* 1906 */       return "";
/*      */     }
/* 1908 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1913 */     String argsList = "";
/* 1914 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1917 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1919 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1920 */         if (hideArgsList != null)
/*      */         {
/* 1922 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1924 */             if (argsList.trim().equals(""))
/*      */             {
/* 1926 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1930 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1938 */       ex.printStackTrace();
/*      */     }
/* 1940 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1944 */     StringBuilder toreturn = new StringBuilder();
/* 1945 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1952 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1953 */       Iterator itr = tActionList.iterator();
/* 1954 */       while (itr.hasNext()) {
/* 1955 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1956 */         String confirmmsg = "";
/* 1957 */         String link = "";
/* 1958 */         String isJSP = "NO";
/* 1959 */         HashMap tactionMap = (HashMap)itr.next();
/* 1960 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1961 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1962 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1963 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1964 */           (actionmap.containsKey(actionId))) {
/* 1965 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1966 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1967 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1968 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1969 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1971 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1977 */           if (isTableAction) {
/* 1978 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1981 */             tableName = "Link";
/* 1982 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1983 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1984 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1985 */             toreturn.append("</a></td>");
/*      */           }
/* 1987 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1988 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1989 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1990 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1996 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 2002 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 2004 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 2005 */       Properties prop = (Properties)node.getUserObject();
/* 2006 */       String mgID = prop.getProperty("label");
/* 2007 */       String mgName = prop.getProperty("value");
/* 2008 */       String isParent = prop.getProperty("isParent");
/* 2009 */       int mgIDint = Integer.parseInt(mgID);
/* 2010 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2012 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2014 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2015 */       if (node.getChildCount() > 0)
/*      */       {
/* 2017 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2019 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2021 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2023 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2027 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2032 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2034 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2036 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2038 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2042 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2045 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2046 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2048 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2052 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2054 */       if (node.getChildCount() > 0)
/*      */       {
/* 2056 */         builder.append("<UL>");
/* 2057 */         printMGTree(node, builder);
/* 2058 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2063 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2064 */     StringBuffer toReturn = new StringBuffer();
/* 2065 */     String table = "-";
/*      */     try {
/* 2067 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2068 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2069 */       float total = 0.0F;
/* 2070 */       while (it.hasNext()) {
/* 2071 */         String attName = (String)it.next();
/* 2072 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2073 */         boolean roundOffData = false;
/* 2074 */         if ((data != null) && (!data.equals(""))) {
/* 2075 */           if (data.indexOf(",") != -1) {
/* 2076 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2079 */             float value = Float.parseFloat(data);
/* 2080 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2083 */             total += value;
/* 2084 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2087 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2092 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2093 */       while (attVsWidthList.hasNext()) {
/* 2094 */         String attName = (String)attVsWidthList.next();
/* 2095 */         String data = (String)attVsWidthProps.get(attName);
/* 2096 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2097 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2098 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2099 */         String className = (String)graphDetails.get("ClassName");
/* 2100 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2101 */         if (percentage < 1.0F)
/*      */         {
/* 2103 */           data = percentage + "";
/*      */         }
/* 2105 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2107 */       if (toReturn.length() > 0) {
/* 2108 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2112 */       e.printStackTrace();
/*      */     }
/* 2114 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2120 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2121 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2122 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2123 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2124 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2125 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2126 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2127 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2128 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2131 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2132 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2133 */       splitvalues[0] = multiplecondition.toString();
/* 2134 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2137 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2142 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2143 */     if (thresholdType != 3) {
/* 2144 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2145 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2146 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2147 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2148 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2149 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2151 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2152 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2153 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2154 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2155 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2156 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2158 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2159 */     if (updateSelected != null) {
/* 2160 */       updateSelected[0] = "selected";
/*      */     }
/* 2162 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2167 */       StringBuffer toreturn = new StringBuffer("");
/* 2168 */       if (commaSeparatedMsgId != null) {
/* 2169 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2170 */         int count = 0;
/* 2171 */         while (msgids.hasMoreTokens()) {
/* 2172 */           String id = msgids.nextToken();
/* 2173 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2174 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2175 */           count++;
/* 2176 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2177 */             if (toreturn.length() == 0) {
/* 2178 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2180 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2181 */             if (!image.trim().equals("")) {
/* 2182 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2184 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2185 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2188 */         if (toreturn.length() > 0) {
/* 2189 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2193 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2196 */       e.printStackTrace(); }
/* 2197 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2203 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2209 */   private static Map<String, Long> _jspx_dependants = new HashMap(7);
/* 2210 */   static { _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2211 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2212 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2213 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2214 */     _jspx_dependants.put("/jsp/includes/CustomLeftPage.jspf", Long.valueOf(1473429417000L));
/* 2215 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2216 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
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
/* 2249 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2253 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005ffmt_005fparam = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2271 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fchartTitle_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2274 */     this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer_005fchartTitle_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2275 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005fnodatamessage_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular_005fchartTitle_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2276 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005fnodatamessage_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2277 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2278 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2279 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2283 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2284 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2285 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2286 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2287 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2288 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2289 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.release();
/* 2290 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.release();
/* 2291 */     this._005fjspx_005ftagPool_005ffmt_005fparam.release();
/* 2292 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.release();
/* 2293 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2294 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2295 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2296 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2297 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2298 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2299 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2300 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2301 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2302 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/* 2303 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fchartTitle_005fnobody.release();
/* 2304 */     this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer_005fchartTitle_005fnobody.release();
/* 2305 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005fnodatamessage_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular_005fchartTitle_005fnobody.release();
/* 2306 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005fnodatamessage_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2307 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/*      */   }
/*      */   
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response) throws IOException, javax.servlet.ServletException {
/*      */     ;
/*      */     ;
/*      */     ;
/* 2314 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2317 */     JspWriter out = null;
/* 2318 */     Object page = this;
/* 2319 */     JspWriter _jspx_out = null;
/* 2320 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2324 */       response.setContentType("text/html;charset=UTF-8");
/* 2325 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2327 */       _jspx_page_context = pageContext;
/* 2328 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2329 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2330 */       session = pageContext.getSession();
/* 2331 */       out = pageContext.getOut();
/* 2332 */       _jspx_out = out;
/*      */       
/* 2334 */       out.write("<!--$Id$-->\n");
/* 2335 */       request.setAttribute("HelpKey", request.getParameter("type") + " Details");
/* 2336 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2337 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2339 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2340 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2341 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2343 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2345 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2347 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2349 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2350 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2351 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2352 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2355 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2356 */         String available = null;
/* 2357 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2358 */         out.write(10);
/*      */         
/* 2360 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2361 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2362 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2364 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2366 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2368 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2370 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2371 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2372 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2373 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2376 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2377 */           String unavailable = null;
/* 2378 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2379 */           out.write(10);
/*      */           
/* 2381 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2382 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2383 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2385 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2387 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2389 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2391 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2392 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2393 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2394 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2397 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2398 */             String unmanaged = null;
/* 2399 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2400 */             out.write(10);
/*      */             
/* 2402 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2403 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2404 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2406 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2408 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2410 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2412 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2413 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2414 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2415 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2418 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2419 */               String scheduled = null;
/* 2420 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2421 */               out.write(10);
/*      */               
/* 2423 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2424 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2425 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2427 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2429 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2431 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2433 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2434 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2435 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2436 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2439 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2440 */                 String critical = null;
/* 2441 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2442 */                 out.write(10);
/*      */                 
/* 2444 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2445 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2446 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2448 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2450 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2452 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2454 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2455 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2456 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2457 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2460 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2461 */                   String clear = null;
/* 2462 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2463 */                   out.write(10);
/*      */                   
/* 2465 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2466 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2467 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2469 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2471 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2473 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2475 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2476 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2477 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2478 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2481 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2482 */                     String warning = null;
/* 2483 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2484 */                     out.write(10);
/* 2485 */                     out.write(10);
/*      */                     
/* 2487 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2488 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2490 */                     out.write(10);
/* 2491 */                     out.write(10);
/* 2492 */                     out.write(10);
/* 2493 */                     out.write(10);
/* 2494 */                     out.write(10);
/* 2495 */                     ConfMonitorGraphBean confgraph = null;
/* 2496 */                     confgraph = (ConfMonitorGraphBean)_jspx_page_context.getAttribute("confgraph", 2);
/* 2497 */                     if (confgraph == null) {
/* 2498 */                       confgraph = new ConfMonitorGraphBean();
/* 2499 */                       _jspx_page_context.setAttribute("confgraph", confgraph, 2);
/*      */                     }
/* 2501 */                     out.write(10);
/* 2502 */                     Hashtable motypedisplaynames = null;
/* 2503 */                     synchronized (application) {
/* 2504 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2505 */                       if (motypedisplaynames == null) {
/* 2506 */                         motypedisplaynames = new Hashtable();
/* 2507 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2510 */                     out.write(10);
/* 2511 */                     Hashtable availabilitykeys = null;
/* 2512 */                     synchronized (application) {
/* 2513 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2514 */                       if (availabilitykeys == null) {
/* 2515 */                         availabilitykeys = new Hashtable();
/* 2516 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2519 */                     out.write(10);
/* 2520 */                     Hashtable healthkeys = null;
/* 2521 */                     synchronized (application) {
/* 2522 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2523 */                       if (healthkeys == null) {
/* 2524 */                         healthkeys = new Hashtable();
/* 2525 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2528 */                     out.write(10);
/* 2529 */                     DialChartSupport dialGraph1 = null;
/* 2530 */                     dialGraph1 = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph1", 1);
/* 2531 */                     if (dialGraph1 == null) {
/* 2532 */                       dialGraph1 = new DialChartSupport();
/* 2533 */                       _jspx_page_context.setAttribute("dialGraph1", dialGraph1, 1);
/*      */                     }
/* 2535 */                     out.write(10);
/*      */                     
/* 2537 */                     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 2538 */                     String dispname = (String)motypedisplaynames.get(request.getParameter("type"));
/* 2539 */                     String resourceName = request.getParameter("resourcename");
/* 2540 */                     Properties ess_atts = (Properties)request.getAttribute("ess_atts");
/* 2541 */                     List tabConfiguration = (ArrayList)request.getAttribute("tabConfiguration");
/* 2542 */                     HashMap groupData = new HashMap();
/* 2543 */                     List groupList = new ArrayList();
/* 2544 */                     HashMap attributeDetails = new HashMap();
/* 2545 */                     HashMap groupDetails = new HashMap();
/* 2546 */                     ArrayList attributesList = new ArrayList();
/* 2547 */                     int scrWidth = 1280;
/* 2548 */                     int m = 1;
/* 2549 */                     int noOftabs = 0;
/* 2550 */                     if (tabConfiguration != null) {
/* 2551 */                       noOftabs = tabConfiguration.size();
/*      */                     }
/* 2553 */                     String moname = request.getParameter("moname");
/* 2554 */                     String resID = request.getParameter("resourceid");
/* 2555 */                     if (request.getAttribute("tabId") != null) {
/* 2556 */                       m = ((Integer)request.getAttribute("tabId")).intValue();
/*      */                     }
/* 2558 */                     ArrayList resIDs = new ArrayList();
/* 2559 */                     resIDs.add(resID);
/* 2560 */                     ArrayList attribIDs = new ArrayList();
/* 2561 */                     attribIDs.add(ess_atts.getProperty("Availability"));
/* 2562 */                     attribIDs.add(ess_atts.getProperty("Health"));
/* 2563 */                     attribIDs.add(ess_atts.getProperty("ResponseTime"));
/* 2564 */                     int tableid = 1;
/* 2565 */                     String haid = request.getParameter("haid");
/* 2566 */                     String original_type1 = request.getParameter("original_type");
/* 2567 */                     String baseid1 = request.getParameter("baseid");
/* 2568 */                     int count = 1;
/* 2569 */                     int graphCount = 1;
/* 2570 */                     String resourcetype = request.getParameter("type");
/* 2571 */                     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resID);
/* 2572 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2573 */                     String seven_days_text = FormatUtil.getString("am.webclient.common.history.tooltip.text");
/* 2574 */                     String serverName = (String)request.getAttribute("serverName");
/* 2575 */                     String hostOs = (String)request.getAttribute("hostOs");
/* 2576 */                     String hostId = (String)request.getAttribute("hostId");
/* 2577 */                     String hostIp = (String)request.getAttribute("hostName");
/* 2578 */                     HashMap systeminfo = new HashMap();
/* 2579 */                     systeminfo.put("HOSTOS", hostOs);
/* 2580 */                     systeminfo.put("HOSTNAME", serverName);
/* 2581 */                     systeminfo.put("host_resid", hostId);
/* 2582 */                     systeminfo.put("isConf", "true");
/* 2583 */                     HashMap attributeValues = (HashMap)request.getAttribute("attributeValues");
/* 2584 */                     Properties reliedargsValues = (Properties)request.getAttribute("ReliedOnArgsMap");
/* 2585 */                     ConfMonitorConfiguration conf = ConfMonitorConfiguration.getInstance();
/* 2586 */                     String subGroup = conf.getSubGroup(resourcetype);
/* 2587 */                     ArrayList configAttList = conf.getConfigidsList(resourcetype);
/* 2588 */                     String isAgent = conf.getTypeDescription(resourcetype).getProperty("IS-AGENT-ENABLED") != null ? conf.getTypeDescription(resourcetype).getProperty("IS-AGENT-ENABLED") : "NO";
/* 2589 */                     boolean isParentMonitor = false;
/* 2590 */                     if ((!reliedargsValues.isEmpty()) && 
/* 2591 */                       (reliedargsValues.getProperty("ISPARENT") != null) && (reliedargsValues.getProperty("ISPARENT").equalsIgnoreCase("true"))) {
/* 2592 */                       isParentMonitor = true;
/*      */                     }
/*      */                     
/* 2595 */                     ArrayList agentsDown = (ArrayList)request.getAttribute("agentsDown");
/* 2596 */                     pageContext.setAttribute("agentsDown", agentsDown);
/* 2597 */                     String actionPath = "";
/* 2598 */                     String mon_health = ess_atts.getProperty("Health");
/* 2599 */                     String tablesList = (String)request.getAttribute("TABLES-LIST");
/*      */                     
/*      */ 
/* 2602 */                     out.write("\n<SCRIPT language=\"JavaScript\">\nfunction changeAction(obj,frm,objname,frmname,formid,tableid,table_health,resourceType,encodeurl,tableName){\t\n\t  var link1=\"\";\n\t  if(obj.value=='Default'){\n\t\treturn;\n\t}\n\t  if(obj.value == 'Disable'){\n\t  \t  disableRows(obj,frm,objname,frmname,formid,tableName,'");
/* 2603 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.disable"));
/* 2604 */                     out.write(39);
/* 2605 */                     out.write(44);
/* 2606 */                     out.write(39);
/* 2607 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.disable.confirm"));
/* 2608 */                     out.write("');\n\t  }\n\t  else if(obj.value == 'Enable'){\n\t  \t  enableRows(obj,frm,objname,frmname,formid,tableName,table_health,'");
/* 2609 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.disable"));
/* 2610 */                     out.write(39);
/* 2611 */                     out.write(44);
/* 2612 */                     out.write(39);
/* 2613 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.enable.confirm"));
/* 2614 */                     out.write("');// No I18N\n\t  }\n\t  else if(obj.value == 'Delete'){\n\t  \t  deleteRows(obj,frm,objname,frmname,formid,'");
/* 2615 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.delete"));
/* 2616 */                     out.write(39);
/* 2617 */                     out.write(44);
/* 2618 */                     out.write(39);
/* 2619 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.delete.confirm"));
/* 2620 */                     out.write(39);
/* 2621 */                     out.write(44);
/* 2622 */                     out.write(39);
/* 2623 */                     out.print(resID);
/* 2624 */                     out.write("');// No I18N\n\t  }\n\t  else if(obj.value == 'deleteMos'){\n\t  \tdeleteMos(obj,objname,frmname,resourceType,'");
/* 2625 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.delete"));
/* 2626 */                     out.write(39);
/* 2627 */                     out.write(44);
/* 2628 */                     out.write(39);
/* 2629 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.delete.confirm"));
/* 2630 */                     out.write(39);
/* 2631 */                     out.write(44);
/* 2632 */                     out.write(39);
/* 2633 */                     out.print(FormatUtil.getString("am.webclient.vmware.jsalertfordeletevm.text"));
/* 2634 */                     out.write("');\n    \t     }\n\telse {\n\t  \t  customTableActions(obj,frm,objname,frmname,formid,tableid,table_health,resourceType,encodeurl,'");
/* 2635 */                     out.print(FormatUtil.getString("am.webclient.scriptrow.selectone.disable"));
/* 2636 */                     out.write("',tableName,'");
/* 2637 */                     out.print(FormatUtil.getString("am.webclient.ConfTable.actionReqOut"));
/* 2638 */                     out.write("');\n\t  }\n}\n</script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/confMonitor.js\"></SCRIPT>\n<tiles:put name=\"UserArea\" type=\"string\">\n");
/* 2639 */                     if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */                       return;
/* 2641 */                     out.write(10);
/*      */                     try {
/* 2643 */                       String errormsg = request.getParameter("message");
/* 2644 */                       if (errormsg != null) {
/* 2645 */                         errormsg = java.net.URLDecoder.decode(errormsg);
/* 2646 */                         if (errormsg.contains("UnknownHostException")) {
/* 2647 */                           errormsg = "am.webclient.query.errormsg";
/*      */                         }
/* 2649 */                         else if (errormsg.contains("ConnectException")) {
/* 2650 */                           errormsg = "am.webclient.query.errormsg1";
/*      */                         }
/*      */                       }
/* 2653 */                       if ((errormsg != null) && (!errormsg.equals("null")) && (!errormsg.trim().equals("")) && (!errormsg.trim().equalsIgnoreCase("false")))
/*      */                       {
/* 2655 */                         out.write("\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t\t<td height=\"24\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t\t\t\t\t<td width=\"95%\" height=\"28\" class=\"message\">");
/* 2656 */                         out.print(FormatUtil.getString(errormsg));
/* 2657 */                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t</table><br>\n\t\t</td>\n\t</tr>\n</table>\n");
/*      */                       }
/*      */                     }
/*      */                     catch (Exception ex) {
/* 2661 */                       ex.printStackTrace();
/*      */                     }
/* 2663 */                     if ((agentsDown != null) && (!agentsDown.isEmpty())) {
/* 2664 */                       out.write("\n\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t\t\t\t<td height=\"24\" colspan=\"2\" valign=\"top\" class=\"tdindent\">\n\t\t\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messageboxfailure\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n\t\t\t\t\t\t\t<td width=\"95%\" height=\"28\" class=\"message\">\n\t\t\t\t\t\t\t");
/* 2665 */                       if (agentsDown.size() > 1) {
/* 2666 */                         out.write("\n\t\t\t\t\t\t\t\t");
/*      */                         
/* 2668 */                         IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 2669 */                         _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2670 */                         _jspx_th_logic_005fiterate_005f0.setParent(null);
/*      */                         
/* 2672 */                         _jspx_th_logic_005fiterate_005f0.setName("agentsDown");
/*      */                         
/* 2674 */                         _jspx_th_logic_005fiterate_005f0.setId("agents");
/*      */                         
/* 2676 */                         _jspx_th_logic_005fiterate_005f0.setIndexId("n");
/* 2677 */                         int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2678 */                         if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2679 */                           Object agents = null;
/* 2680 */                           Integer n = null;
/* 2681 */                           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2682 */                             out = _jspx_page_context.pushBody();
/* 2683 */                             _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2684 */                             _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                           }
/* 2686 */                           agents = _jspx_page_context.findAttribute("agents");
/* 2687 */                           n = (Integer)_jspx_page_context.findAttribute("n");
/*      */                           for (;;) {
/* 2689 */                             out.write("\n\t\t\t\t\t\t\t\t\t<li>\n\t\t\t\t\t\t\t\t\t\t");
/* 2690 */                             if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                               return;
/* 2692 */                             out.write("\n\t\t\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t\t\t");
/* 2693 */                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2694 */                             agents = _jspx_page_context.findAttribute("agents");
/* 2695 */                             n = (Integer)_jspx_page_context.findAttribute("n");
/* 2696 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2699 */                           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2700 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2703 */                         if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2704 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                         }
/*      */                         
/* 2707 */                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2708 */                         out.write("\n\t\t\t\t\t\t\t");
/* 2709 */                       } else if (agentsDown.size() == 1) {
/* 2710 */                         out.write("\n\t\t\t\t\t\t\t\t\t");
/*      */                         
/* 2712 */                         MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 2713 */                         _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2714 */                         _jspx_th_fmt_005fmessage_005f1.setParent(null);
/*      */                         
/* 2716 */                         _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.eum.agentdown");
/* 2717 */                         int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 2718 */                         if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 2719 */                           if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2720 */                             out = _jspx_page_context.pushBody();
/* 2721 */                             _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 2722 */                             _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2725 */                             out.write("\n\t\t\t\t\t\t\t\t\t\t");
/*      */                             
/* 2727 */                             ParamTag _jspx_th_fmt_005fparam_005f1 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 2728 */                             _jspx_th_fmt_005fparam_005f1.setPageContext(_jspx_page_context);
/* 2729 */                             _jspx_th_fmt_005fparam_005f1.setParent(_jspx_th_fmt_005fmessage_005f1);
/* 2730 */                             int _jspx_eval_fmt_005fparam_005f1 = _jspx_th_fmt_005fparam_005f1.doStartTag();
/* 2731 */                             if (_jspx_eval_fmt_005fparam_005f1 != 0) {
/* 2732 */                               if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 2733 */                                 out = _jspx_page_context.pushBody();
/* 2734 */                                 _jspx_th_fmt_005fparam_005f1.setBodyContent((BodyContent)out);
/* 2735 */                                 _jspx_th_fmt_005fparam_005f1.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2738 */                                 out.print(agentsDown.get(0));
/* 2739 */                                 int evalDoAfterBody = _jspx_th_fmt_005fparam_005f1.doAfterBody();
/* 2740 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2743 */                               if (_jspx_eval_fmt_005fparam_005f1 != 1) {
/* 2744 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2747 */                             if (_jspx_th_fmt_005fparam_005f1.doEndTag() == 5) {
/* 2748 */                               this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1); return;
/*      */                             }
/*      */                             
/* 2751 */                             this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f1);
/* 2752 */                             out.write("\n\t\t\t\t\t\t\t\t\t");
/* 2753 */                             int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 2754 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2757 */                           if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 2758 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2761 */                         if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 2762 */                           this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f1); return;
/*      */                         }
/*      */                         
/* 2765 */                         this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2766 */                         out.write("\n\t\t\t\t\t\t\t");
/*      */                       }
/* 2768 */                       out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table><br>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n");
/*      */                     }
/* 2770 */                     try { out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n");
/*      */                       
/* 2772 */                       Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2773 */                       String aid = request.getParameter("haid");
/* 2774 */                       String haName = null;
/* 2775 */                       if (aid != null) {
/* 2776 */                         haName = (String)ht.get(aid);
/*      */                       }
/*      */                       
/* 2779 */                       out.write(10);
/*      */                       
/* 2781 */                       SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 2782 */                       _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2783 */                       _jspx_th_c_005fset_005f1.setParent(null);
/*      */                       
/* 2785 */                       _jspx_th_c_005fset_005f1.setVar("parentMonitor");
/*      */                       
/* 2787 */                       _jspx_th_c_005fset_005f1.setScope("page");
/* 2788 */                       int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2789 */                       if (_jspx_eval_c_005fset_005f1 != 0) {
/* 2790 */                         if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2791 */                           out = _jspx_page_context.pushBody();
/* 2792 */                           _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 2793 */                           _jspx_th_c_005fset_005f1.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2796 */                           out.print(conf.getParentType(resourcetype));
/* 2797 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 2798 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2801 */                         if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2802 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2805 */                       if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2806 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                       }
/*      */                       
/* 2809 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 2810 */                       out.write(10);
/*      */                       
/* 2812 */                       SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 2813 */                       _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 2814 */                       _jspx_th_c_005fset_005f2.setParent(null);
/*      */                       
/* 2816 */                       _jspx_th_c_005fset_005f2.setVar("isAgentMonitor");
/*      */                       
/* 2818 */                       _jspx_th_c_005fset_005f2.setScope("page");
/* 2819 */                       int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 2820 */                       if (_jspx_eval_c_005fset_005f2 != 0) {
/* 2821 */                         if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2822 */                           out = _jspx_page_context.pushBody();
/* 2823 */                           _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 2824 */                           _jspx_th_c_005fset_005f2.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2827 */                           out.print(isAgent);
/* 2828 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 2829 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2832 */                         if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2833 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2836 */                       if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 2837 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2); return;
/*      */                       }
/*      */                       
/* 2840 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 2841 */                       out.write(10);
/*      */                       
/* 2843 */                       SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 2844 */                       _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 2845 */                       _jspx_th_c_005fset_005f3.setParent(null);
/*      */                       
/* 2847 */                       _jspx_th_c_005fset_005f3.setVar("isAgentparent");
/*      */                       
/* 2849 */                       _jspx_th_c_005fset_005f3.setScope("page");
/* 2850 */                       int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 2851 */                       if (_jspx_eval_c_005fset_005f3 != 0) {
/* 2852 */                         if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2853 */                           out = _jspx_page_context.pushBody();
/* 2854 */                           _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 2855 */                           _jspx_th_c_005fset_005f3.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 2858 */                           out.print(isParentMonitor);
/* 2859 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 2860 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 2863 */                         if (_jspx_eval_c_005fset_005f3 != 1) {
/* 2864 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 2867 */                       if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 2868 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3); return;
/*      */                       }
/*      */                       
/* 2871 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 2872 */                       out.write(10);
/*      */                       
/* 2874 */                       ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2875 */                       _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2876 */                       _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2877 */                       int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2878 */                       if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                         for (;;) {
/* 2880 */                           out.write(10);
/*      */                           
/* 2882 */                           WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2883 */                           _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2884 */                           _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                           
/* 2886 */                           _jspx_th_c_005fwhen_005f0.setTest("${!empty param.fromwhere && (param.fromwhere=='infrastructure')}");
/* 2887 */                           int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2888 */                           if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                             for (;;) {
/* 2890 */                               out.write("\n\t\t<td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2891 */                               out.print(BreadcrumbUtil.getMonitorsPage());
/* 2892 */                               out.write(" &gt; ");
/* 2893 */                               out.print(BreadcrumbUtil.getParentType(request.getParameter("type")));
/* 2894 */                               out.write(" &gt; <span class=\"bcactivebig\"> ");
/* 2895 */                               out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 2896 */                               out.write("</span></td>\n");
/* 2897 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2898 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2902 */                           if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2903 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                           }
/*      */                           
/* 2906 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2907 */                           out.write(10);
/*      */                           
/* 2909 */                           WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2910 */                           _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2911 */                           _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                           
/* 2913 */                           _jspx_th_c_005fwhen_005f1.setTest("${!empty param.fromwhere && (param.fromwhere=='parentview') || parentMonitor!='null' || (isAgentMonitor=='YES' && !isAgentparent)}");
/* 2914 */                           int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2915 */                           if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                             for (;;) {
/* 2917 */                               out.write(10);
/* 2918 */                               out.write(9);
/* 2919 */                               out.write(9);
/* 2920 */                               if ((!isParentMonitor) && (isAgent.equals("NO"))) {
/* 2921 */                                 out.write("\n\t\t\t<td class=\"bcsign\"  height=\"22\" valign=\"top\">");
/* 2922 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 2923 */                                 out.write(" &gt;");
/* 2924 */                                 out.print(BreadcrumbUtil.getParentDetailsPage(request.getParameter("resourceid")));
/* 2925 */                                 out.write("&gt;");
/* 2926 */                                 out.print(BreadcrumbUtil.getMonitorResourceTypes(subGroup));
/* 2927 */                                 out.write(" &gt;<span class=\"bcactivebig\">");
/* 2928 */                                 out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 2929 */                                 out.write("</span></td>\n\t\t");
/* 2930 */                               } else if (isAgent.equals("YES")) {
/* 2931 */                                 out.write("\n\t\t\t<td class=\"bcsign\"  height=\"22\" valign=\"top\">");
/* 2932 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 2933 */                                 out.write("&gt;");
/* 2934 */                                 out.print(BreadcrumbUtil.getMonitorResourceTypes(subGroup));
/* 2935 */                                 out.write("&gt;");
/* 2936 */                                 out.print(BreadcrumbUtil.getParentDetailsPage(request.getParameter("resourceid")));
/* 2937 */                                 out.write(" &gt;<span class=\"bcactivebig\">");
/* 2938 */                                 out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 2939 */                                 out.write("</span></td>\n\t\t");
/*      */                               } else {
/* 2941 */                                 out.write("\n                <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2942 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 2943 */                                 out.write(" &gt; ");
/* 2944 */                                 out.print(BreadcrumbUtil.getParentType(request.getParameter("type")));
/* 2945 */                                 out.write(" &gt; ");
/* 2946 */                                 out.print(BreadcrumbUtil.getParentDetailsPage(request.getParameter("resourceid")));
/* 2947 */                                 out.write(" &gt; ");
/* 2948 */                                 out.print(BreadcrumbUtil.getMonitorResourceTypes(subGroup));
/* 2949 */                                 out.write(" &gt; <span class=\"bcactivebig\"> ");
/* 2950 */                                 out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 2951 */                                 out.write("</span></td>\n                ");
/*      */                               }
/* 2953 */                               out.write(10);
/* 2954 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2955 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2959 */                           if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2960 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                           }
/*      */                           
/* 2963 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2964 */                           out.write(10);
/*      */                           
/* 2966 */                           WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2967 */                           _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2968 */                           _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                           
/* 2970 */                           _jspx_th_c_005fwhen_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2971 */                           int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2972 */                           if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                             for (;;) {
/* 2974 */                               out.write("\n\t\t<td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2975 */                               out.print(BreadcrumbUtil.getHomePage(request));
/* 2976 */                               out.write(" &gt; ");
/* 2977 */                               out.print(BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 2978 */                               out.write(" &gt; <span class=\"bcactive\"> ");
/* 2979 */                               out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 2980 */                               out.write(" </span></td>\n");
/* 2981 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2982 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2986 */                           if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2987 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                           }
/*      */                           
/* 2990 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2991 */                           out.write(10);
/*      */                           
/* 2993 */                           WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2994 */                           _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2995 */                           _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                           
/* 2997 */                           _jspx_th_c_005fwhen_005f3.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 2998 */                           int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2999 */                           if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                             for (;;) {
/* 3001 */                               out.write("\n\t\t<td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 3002 */                               out.print(BreadcrumbUtil.getMonitorsPage());
/* 3003 */                               out.write(" &gt; ");
/* 3004 */                               out.print(BreadcrumbUtil.getMonitorResourceTypes(subGroup));
/* 3005 */                               out.write(" &gt; <span class=\"bcactivebig\"> ");
/* 3006 */                               out.print(getTrimmedText((String)request.getAttribute("monitorname"), 35));
/* 3007 */                               out.write("</span></td>\n");
/* 3008 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 3009 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3013 */                           if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 3014 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                           }
/*      */                           
/* 3017 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3018 */                           out.write(10);
/* 3019 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3020 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3024 */                       if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3025 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                       }
/*      */                       
/* 3028 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3029 */                       out.write("\n\t<td width=\"15%\">\n\t\t");
/* 3030 */                       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n");
/*      */                       
/* 3032 */                       int healthid = 2201;
/* 3033 */                       int availabilityid = 2200;
/* 3034 */                       availabilityid = Integer.parseInt(ess_atts.getProperty("Availability"));
/* 3035 */                       healthid = Integer.parseInt(ess_atts.getProperty("Health"));
/* 3036 */                       String haid1 = "";
/* 3037 */                       String mon_type = request.getParameter("type");
/* 3038 */                       ConfMonitorConfiguration confObj = ConfMonitorConfiguration.getInstance();
/* 3039 */                       boolean isDependent = confObj.isDependentMonitor(mon_type);
/* 3040 */                       String isAgentEnabled = confObj.getTypeDescription(mon_type).getProperty("IS-AGENT-ENABLED");
/* 3041 */                       boolean isParent = false;
/* 3042 */                       if (request.getAttribute("ReliedOnArgsMap") != null) {
/* 3043 */                         Properties reliedonargsValues = (Properties)request.getAttribute("ReliedOnArgsMap");
/* 3044 */                         if ((!reliedonargsValues.isEmpty()) && 
/* 3045 */                           (reliedonargsValues.getProperty("ISPARENT") != null) && (reliedonargsValues.getProperty("ISPARENT").equalsIgnoreCase("true"))) {
/* 3046 */                           isParent = true;
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/* 3051 */                       String resourceid_poll = request.getParameter("resourceid");
/* 3052 */                       boolean allowEdit = com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil.getInstance().showEditLink(resourceid_poll, baseid1, mon_type);
/* 3053 */                       String resourcetype_poll = request.getParameter("type");
/* 3054 */                       int tabToSelect = request.getAttribute("tabId") != null ? ((Integer)request.getAttribute("tabId")).intValue() : 0;
/* 3055 */                       String mname = request.getAttribute("monitorname") != null ? URLEncoder.encode((String)request.getAttribute("monitorname")) : "";
/* 3056 */                       if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid_poll, healthid + "") != 0) {
/* 3057 */                         pageContext.setAttribute("showAlarmHistory", Boolean.valueOf(true));
/*      */                       }
/* 3059 */                       if (((isDependent) && (!allowEdit)) || ((isAgentEnabled.equals("YES")) && (!isParent))) {
/* 3060 */                         allowEdit = false;
/*      */                       }
/* 3062 */                       boolean isEEAdminResource = EnterpriseUtil.isResourceRunningInAdmin(resourceid_poll);
/*      */                       
/* 3064 */                       out.write(10);
/*      */                       
/* 3066 */                       SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3067 */                       _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 3068 */                       _jspx_th_c_005fset_005f4.setParent(null);
/*      */                       
/* 3070 */                       _jspx_th_c_005fset_005f4.setVar("isEEAdminResource");
/* 3071 */                       int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 3072 */                       if (_jspx_eval_c_005fset_005f4 != 0) {
/* 3073 */                         if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3074 */                           out = _jspx_page_context.pushBody();
/* 3075 */                           _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 3076 */                           _jspx_th_c_005fset_005f4.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3079 */                           out.print(isEEAdminResource);
/* 3080 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 3081 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3084 */                         if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3085 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3088 */                       if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 3089 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */                       }
/*      */                       
/* 3092 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 3093 */                       out.write("\n<script>\n    function confirmDelete()\n \t {\n\t\tvar s;\n\t");
/*      */                       
/* 3095 */                       ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3096 */                       _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 3097 */                       _jspx_th_c_005fchoose_005f1.setParent(null);
/* 3098 */                       int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 3099 */                       if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                         for (;;) {
/* 3101 */                           out.write(10);
/* 3102 */                           out.write(9);
/*      */                           
/* 3104 */                           WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3105 */                           _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 3106 */                           _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                           
/* 3108 */                           _jspx_th_c_005fwhen_005f4.setTest("${(param.type==\"VMWare ESX/ESXi\")}");
/* 3109 */                           int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 3110 */                           if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                             for (;;) {
/* 3112 */                               out.write("\n\t\ts = confirm(\"");
/* 3113 */                               out.print(FormatUtil.getString("am.webclient.vcenter.esxjsalertfordeletevi.text"));
/* 3114 */                               out.write("\");\n\t");
/* 3115 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 3116 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3120 */                           if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 3121 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                           }
/*      */                           
/* 3124 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3125 */                           out.write(10);
/* 3126 */                           out.write(9);
/*      */                           
/* 3128 */                           WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3129 */                           _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 3130 */                           _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                           
/* 3132 */                           _jspx_th_c_005fwhen_005f5.setTest("${(param.type==\"VirtualMachine\")}");
/* 3133 */                           int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 3134 */                           if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                             for (;;) {
/* 3136 */                               out.write("\n\t\ts = confirm(\"");
/* 3137 */                               out.print(FormatUtil.getString("am.webclient.vmware.jsalertfordeletevm.text"));
/* 3138 */                               out.write("\");\n\t");
/* 3139 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 3140 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3144 */                           if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 3145 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                           }
/*      */                           
/* 3148 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 3149 */                           out.write(10);
/* 3150 */                           out.write(9);
/*      */                           
/* 3152 */                           WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3153 */                           _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 3154 */                           _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                           
/* 3156 */                           _jspx_th_c_005fwhen_005f6.setTest("${(param.type==\"XenServerHost\")}");
/* 3157 */                           int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 3158 */                           if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                             for (;;) {
/* 3160 */                               out.write("\n\t\ts = confirm(\"");
/* 3161 */                               out.print(FormatUtil.getString("am.webclient.xencenter.hostjsalertfordeletevi.text"));
/* 3162 */                               out.write("\");\n\t");
/* 3163 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 3164 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3168 */                           if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 3169 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                           }
/*      */                           
/* 3172 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 3173 */                           out.write(10);
/* 3174 */                           out.write(9);
/*      */                           
/* 3176 */                           WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3177 */                           _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 3178 */                           _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                           
/* 3180 */                           _jspx_th_c_005fwhen_005f7.setTest("${(param.type==\"XenServerVM\")}");
/* 3181 */                           int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 3182 */                           if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                             for (;;) {
/* 3184 */                               out.write("\n\t\ts = confirm(\"");
/* 3185 */                               out.print(FormatUtil.getString("am.webclient.xenserver.jsalertfordeletevm.text"));
/* 3186 */                               out.write("\");\n\t");
/* 3187 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 3188 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3192 */                           if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 3193 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                           }
/*      */                           
/* 3196 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3197 */                           out.write(10);
/* 3198 */                           out.write(9);
/*      */                           
/* 3200 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3201 */                           _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3202 */                           _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f1);
/* 3203 */                           int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3204 */                           if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                             for (;;) {
/* 3206 */                               out.write("\n  \t\ts = confirm(\"");
/* 3207 */                               out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 3208 */                               out.write("\");\n\t");
/* 3209 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3210 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3214 */                           if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3215 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                           }
/*      */                           
/* 3218 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3219 */                           out.write(10);
/* 3220 */                           out.write(9);
/* 3221 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3222 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3226 */                       if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3227 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                       }
/*      */                       
/* 3230 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3231 */                       out.write(10);
/* 3232 */                       out.write(9);
/*      */                       
/* 3234 */                       if (mon_type.equals("XenServerVM"))
/*      */                       {
/* 3236 */                         mon_type = "VirtualMachine";
/*      */                       }
/*      */                       
/* 3239 */                       out.write("\n  \tif (s)\n         document.location.href=\"/deleteMO.do?method=deleteMO&type=");
/* 3240 */                       out.print(mon_type);
/* 3241 */                       out.write("&baseid=");
/* 3242 */                       out.print(baseid1);
/* 3243 */                       out.write("&select=");
/* 3244 */                       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */                         return;
/* 3246 */                       out.write("\";\n\t }\n        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 3247 */                       out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 3248 */                       out.write("\");\n  if (s){\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 3249 */                       if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*      */                         return;
/* 3251 */                       out.write("&type=");
/* 3252 */                       out.print(mon_type);
/* 3253 */                       out.write("&moname=");
/* 3254 */                       out.print(moname);
/* 3255 */                       out.write("&resourcename=");
/* 3256 */                       out.print(resourceName);
/* 3257 */                       out.write("\";\n\n}\n\t }\n\n        function confirmUnManage()\n    \t {\n           \t ");
/* 3258 */                       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                         return;
/* 3260 */                       out.write("\n      var show_msg=\"false\";\n      var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 3261 */                       out.print(request.getParameter("resourceid"));
/* 3262 */                       out.write("; //No i18n\n      $.ajax({\n        type:'POST', //No i18n\n        url:url,\n        async:false,\n        success: function(data)\n        {\n          show_msg=data\n        }\n      });\n      if(show_msg.indexOf(\"true\")>-1)\n      {\n          alert(\"");
/* 3263 */                       out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 3264 */                       out.write("\");\n          \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 3265 */                       if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*      */                         return;
/* 3267 */                       out.write("&type=");
/* 3268 */                       out.print(mon_type);
/* 3269 */                       out.write("&moname=");
/* 3270 */                       out.print(moname);
/* 3271 */                       out.write("&resourcename=");
/* 3272 */                       out.print(resourceName);
/* 3273 */                       out.write("\";\n\n      }\n      else {\n     var s = confirm(\"");
/* 3274 */                       out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 3275 */                       out.write("\");\n     if (s){\n   \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 3276 */                       if (_jspx_meth_c_005fout_005f4(_jspx_page_context))
/*      */                         return;
/* 3278 */                       out.write("&type=");
/* 3279 */                       out.print(mon_type);
/* 3280 */                       out.write("&moname=");
/* 3281 */                       out.print(moname);
/* 3282 */                       out.write("&resourcename=");
/* 3283 */                       out.print(resourceName);
/* 3284 */                       out.write("\";\n\n   }\n   \t }\n   \t }\n\n        function confPollNow(){\n           var hreference=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3285 */                       out.print(resourceid_poll);
/* 3286 */                       out.write("&resourcetype=");
/* 3287 */                       out.print(resourcetype_poll);
/* 3288 */                       out.write("&baseid=");
/* 3289 */                       out.print(baseid1);
/* 3290 */                       out.write("\";  //No I18N      \n        \tif(document.getElementById('tabsForm')){  \t\n        \t\tvar tabToSelect=document.getElementById('tabsForm').tabId.value;\n        \t\tvar timeUnit=document.getElementById('tabsForm').TimeUnit.value;\n        \t\tvar granularity=document.getElementById('tabsForm').granularity.value;\n        \t\tvar customDate=document.getElementById('tabsForm').customDate.value;\n        \t\tvar monthUnit=document.getElementById('tabsForm').monthUnit.value;\n        \t\tvar weekUnit=document.getElementById('tabsForm').weekUnit.value;\n        \t\threference=hreference+\"&tabId=\"+tabToSelect+\"&TimeUnit=\"+timeUnit+\"&granularity=\"+granularity+\"&customDate=\"+customDate+\"&monthUnit=\"+monthUnit+\"&weekUnit=\"+weekUnit;       //No i18N \n        \t}            \n        \tdocument.location.href=hreference;\n         }\n         function confeditMonitor(){\n        \t var hreference=\"/manageConfMons.do?haid=");
/* 3291 */                       if (_jspx_meth_c_005fout_005f5(_jspx_page_context))
/*      */                         return;
/* 3293 */                       out.write("&method=editMonitor&resourceid=");
/* 3294 */                       if (_jspx_meth_c_005fout_005f6(_jspx_page_context))
/*      */                         return;
/* 3296 */                       out.write("&type=");
/* 3297 */                       out.print(mon_type);
/* 3298 */                       if (_jspx_meth_c_005fout_005f7(_jspx_page_context))
/*      */                         return;
/* 3300 */                       out.write("&resourcename=");
/* 3301 */                       out.print(resourceName);
/* 3302 */                       out.write("\";//No I18N\n        \t\tif(document.getElementById('tabsForm')){  \t\n        \t\t\tvar tabToSelect=document.getElementById('tabsForm').tabId.value;\n            \t\tvar timeUnit=document.getElementById('tabsForm').TimeUnit.value;\n            \t\tvar granularity=document.getElementById('tabsForm').granularity.value;\n            \t\tvar customDate=document.getElementById('tabsForm').customDate.value;\n            \t\tvar monthUnit=document.getElementById('tabsForm').monthUnit.value;\n            \t\tvar weekUnit=document.getElementById('tabsForm').weekUnit.value;\n            \t\threference=hreference+\"&tabId=\"+tabToSelect+\"&TimeUnit=\"+timeUnit+\"&granularity=\"+granularity+\"&customDate=\"+customDate+\"&monthUnit=\"+monthUnit+\"&weekUnit=\"+weekUnit;       //No i18N\n            \t\t\n        \t\t} \n        \t \t");
/* 3303 */                       if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) {
/* 3304 */                         out.write("\n     \t\t\t\threference=hreference+\"&aam_jump=true&editPage=true\";//No i18N \n     \t\t\t\twindow.open(hreference);\n             \t    return;\n     \t\t\t");
/*      */                       }
/* 3306 */                       out.write("\n        \t\tdocument.location.href=hreference;\n         }\n\tfunction handleConfig(url,winname,height,width,top,left,isresize,iscroll)\n\t{\n\tvar popoutwindow=window.open(url+\"&resourceid=");
/* 3307 */                       out.print(resID);
/* 3308 */                       out.write("\",winname,'scrollbars='+iscroll+',resizable='+isresize+',width='+width+',height='+height+',left='+left+',top='+top+',screenX=250,screenY=25');//No i18N \n\tpopoutwindow.focus();\n\t}\n</script>\n");
/*      */                       
/* 3310 */                       Hashtable globals = (Hashtable)application.getAttribute("globalconfig");
/* 3311 */                       String allowOperatorManage = (String)globals.get("allowOperatorManage");
/* 3312 */                       String displayClass = "leftlinkstd-conf";
/* 3313 */                       if (mon_type.equals("QueryMonitor")) {
/* 3314 */                         displayClass = "leftlinkstd";
/*      */                         
/* 3316 */                         out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td width=\"90%\" height=\"21\" class=\"leftlinksheading\">\n\t\t");
/* 3317 */                         out.print(FormatUtil.getString("am.querymonitor.heading.text"));
/* 3318 */                         out.write("\n\n      </td>\n  </tr>\n <tr>\n    <td height=\"21\" class=\"");
/* 3319 */                         out.print(displayClass);
/* 3320 */                         out.write("\">\n    ");
/*      */                         
/* 3322 */                         IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3323 */                         _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3324 */                         _jspx_th_c_005fif_005f2.setParent(null);
/*      */                         
/* 3326 */                         _jspx_th_c_005fif_005f2.setTest("${param.method=='editScript'}");
/* 3327 */                         int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3328 */                         if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                           for (;;) {
/* 3330 */                             out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 3331 */                             if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                               return;
/* 3333 */                             out.write("&method=showResourceForResourceID");
/* 3334 */                             out.print(haid1);
/* 3335 */                             out.write("\"  class=\"new-left-links\">");
/* 3336 */                             out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 3337 */                             out.write("</a>\n    ");
/* 3338 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3339 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3343 */                         if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3344 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                         }
/*      */                         
/* 3347 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3348 */                         out.write("\n    ");
/*      */                         
/* 3350 */                         IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3351 */                         _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3352 */                         _jspx_th_c_005fif_005f3.setParent(null);
/*      */                         
/* 3354 */                         _jspx_th_c_005fif_005f3.setTest("${param.method!='editScript'}");
/* 3355 */                         int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3356 */                         if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                           for (;;) {
/* 3358 */                             out.write("\n    ");
/* 3359 */                             out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 3360 */                             out.write("\n    ");
/* 3361 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3362 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3366 */                         if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3367 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                         }
/*      */                         
/* 3370 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3371 */                         out.write("\n     </td>\n  </tr>\n  ");
/*      */                       }
/*      */                       else {
/* 3374 */                         String isReqFromCentral = request.getParameter("fromCentral");
/* 3375 */                         String tempStyl = (isReqFromCentral != null) && (isReqFromCentral.trim().equals("true")) ? "display:none" : "";
/*      */                         
/* 3377 */                         out.write("\n\t<table  cellspacing=\"0\" cellpadding=\"0\" align=\"right\" style=\"");
/* 3378 */                         out.print(tempStyl);
/* 3379 */                         out.write("\">\n\t\t\t<tr>\n\t\t\t\t<td class=\"buttons btn_action\" id=\"apmdetails\" align=\"center\" onclick=\"globalShowMenuInDialogConf('apmdetails', 'apmdetailsDiv',89,-1);\">\n\t\t\t\t\t ");
/* 3380 */                         out.print(FormatUtil.getString("am.webclient.common.conf.monitorActions.text"));
/* 3381 */                         out.write(" <img src=\"/images/icon_black_arrow.gif\" border=\"0\" style=\"margin-bottom:2px;\"/>\n\t\t\t\t\t<div style=\"display:none;\" id=\"apmdetailsDiv\" >\n\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t<table id=\"apmdetailsDivMenu\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" >\n");
/*      */                       }
/* 3383 */                       out.write(10);
/* 3384 */                       out.write(32);
/* 3385 */                       out.write(32);
/*      */                       
/* 3387 */                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3388 */                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3389 */                       _jspx_th_c_005fif_005f4.setParent(null);
/*      */                       
/* 3391 */                       _jspx_th_c_005fif_005f4.setTest("${!empty param.haid && empty invalidhaid}");
/* 3392 */                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3393 */                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                         for (;;) {
/* 3395 */                           out.write(10);
/*      */                           
/* 3397 */                           haid1 = "&haid=" + request.getParameter("haid");
/*      */                           
/* 3399 */                           out.write(10);
/* 3400 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3401 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3405 */                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3406 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                       }
/*      */                       
/* 3409 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3410 */                       out.write(32);
/* 3411 */                       out.write(10);
/* 3412 */                       if (allowEdit) {
/* 3413 */                         out.write(10);
/* 3414 */                         out.write(32);
/* 3415 */                         out.write(32);
/*      */                         
/* 3417 */                         PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3418 */                         _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3419 */                         _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                         
/* 3421 */                         _jspx_th_logic_005fpresent_005f1.setRole("ENTERPRISEADMIN");
/* 3422 */                         int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3423 */                         if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                           for (;;) {
/* 3425 */                             out.write("\n   ");
/*      */                             
/* 3427 */                             if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled())
/*      */                             {
/* 3429 */                               out.write(" \n  <tr>\n   <td height=\"21\" class=\"");
/* 3430 */                               out.print(displayClass);
/* 3431 */                               out.write("\">\n  \t\t<a href=\"javascript:confeditMonitor();\"  class=\"new-left-links\">\n    \t\t<img src=\"images/icon_edit.gif\" border=\"0\"/>&nbsp;");
/* 3432 */                               out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3433 */                               out.write("</a>\n   </td>\n  </tr>\n  ");
/*      */                             }
/* 3435 */                             out.write(10);
/* 3436 */                             out.write(32);
/* 3437 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3438 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3442 */                         if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3443 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                         }
/*      */                         
/* 3446 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3447 */                         out.write(10);
/*      */                         
/* 3449 */                         IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3450 */                         _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3451 */                         _jspx_th_c_005fif_005f5.setParent(null);
/*      */                         
/* 3453 */                         _jspx_th_c_005fif_005f5.setTest("${(!empty ADMIN || !empty DEMO) }");
/* 3454 */                         int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3455 */                         if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                           for (;;) {
/* 3457 */                             out.write("\n  <tr>\n");
/*      */                             
/* 3459 */                             PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3460 */                             _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3461 */                             _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f5);
/*      */                             
/* 3463 */                             _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3464 */                             int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3465 */                             if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                               for (;;) {
/* 3467 */                                 out.write("\n  <td height=\"21\" class=\"");
/* 3468 */                                 out.print(displayClass);
/* 3469 */                                 out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n   <img src=\"images/icon_edit.gif\" border=\"0\"/>&nbsp;");
/* 3470 */                                 out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3471 */                                 out.write("</a> </td>\n  ");
/* 3472 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3473 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3477 */                             if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3478 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                             }
/*      */                             
/* 3481 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3482 */                             out.write("\n   ");
/*      */                             
/* 3484 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3485 */                             _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3486 */                             _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                             
/* 3488 */                             _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 3489 */                             int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3490 */                             if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                               for (;;) {
/* 3492 */                                 out.write("\n    <td height=\"21\" class=\"");
/* 3493 */                                 out.print(displayClass);
/* 3494 */                                 out.write("\">\n    ");
/*      */                                 
/* 3496 */                                 IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3497 */                                 _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3498 */                                 _jspx_th_c_005fif_005f6.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                 
/* 3500 */                                 _jspx_th_c_005fif_005f6.setTest("${param.actionmethod!='editScript' }");
/* 3501 */                                 int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3502 */                                 if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                   for (;;) {
/* 3504 */                                     out.write("\n    <a href=\"javascript:confeditMonitor();\"  class=\"new-left-links\">\n    <img src=\"images/icon_edit.gif\" border=\"0\"/>&nbsp;");
/* 3505 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3506 */                                     out.write("</a>\n");
/* 3507 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3508 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3512 */                                 if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3513 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                 }
/*      */                                 
/* 3516 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3517 */                                 out.write(10);
/*      */                                 
/* 3519 */                                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3520 */                                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3521 */                                 _jspx_th_c_005fif_005f7.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                 
/* 3523 */                                 _jspx_th_c_005fif_005f7.setTest("${param.actionmethod=='editScript' }");
/* 3524 */                                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3525 */                                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                   for (;;) {
/* 3527 */                                     out.write(10);
/* 3528 */                                     out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3529 */                                     out.write(10);
/* 3530 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3531 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3535 */                                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3536 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                 }
/*      */                                 
/* 3539 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3540 */                                 out.write(10);
/* 3541 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3542 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3546 */                             if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3547 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                             }
/*      */                             
/* 3550 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3551 */                             out.write("\n</tr>\n");
/* 3552 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3553 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 3557 */                         if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3558 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                         }
/*      */                         
/* 3561 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3562 */                         out.write(10);
/*      */                       }
/* 3564 */                       out.write(10);
/*      */                       
/* 3566 */                       PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3567 */                       _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3568 */                       _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */                       
/* 3570 */                       _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 3571 */                       int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3572 */                       if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                         for (;;) {
/* 3574 */                           out.write("\n  <tr>\n    <td height=\"21\" class=\"");
/* 3575 */                           out.print(displayClass);
/* 3576 */                           out.write("\"><a href=\"javascript:confirmDelete();\" class=\"new-left-links\"><img src=\"images/deleteWidget.gif\" border=\"0\" />&nbsp; ");
/* 3577 */                           out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3578 */                           out.write("</a></td>\n  </tr>\n");
/* 3579 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3580 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3584 */                       if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3585 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                       }
/*      */                       
/* 3588 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3589 */                       out.write(10);
/*      */                       
/* 3591 */                       PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3592 */                       _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3593 */                       _jspx_th_logic_005fpresent_005f4.setParent(null);
/*      */                       
/* 3595 */                       _jspx_th_logic_005fpresent_005f4.setRole("ENTERPRISEADMIN");
/* 3596 */                       int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3597 */                       if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                         for (;;) {
/* 3599 */                           out.write(32);
/* 3600 */                           out.write(10);
/* 3601 */                           out.write(32);
/*      */                           
/* 3603 */                           IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3604 */                           _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3605 */                           _jspx_th_c_005fif_005f8.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                           
/* 3607 */                           _jspx_th_c_005fif_005f8.setTest("${isEEAdminResource}");
/* 3608 */                           int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3609 */                           if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                             for (;;) {
/* 3611 */                               out.write("\n  <tr>\n    <td height=\"21\" class=\"");
/* 3612 */                               out.print(displayClass);
/* 3613 */                               out.write("\"><a href=\"javascript:confirmDelete();\" class=\"new-left-links\"><img src=\"images/deleteWidget.gif\" border=\"0\" />&nbsp; ");
/* 3614 */                               out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3615 */                               out.write("</a></td>\n  </tr>\n  ");
/* 3616 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3617 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3621 */                           if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3622 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                           }
/*      */                           
/* 3625 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3626 */                           out.write(10);
/* 3627 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3628 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3632 */                       if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3633 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                       }
/*      */                       
/* 3636 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3637 */                       out.write(10);
/*      */                       
/* 3639 */                       PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3640 */                       _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3641 */                       _jspx_th_logic_005fpresent_005f5.setParent(null);
/*      */                       
/* 3643 */                       _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 3644 */                       int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3645 */                       if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                         for (;;) {
/* 3647 */                           out.write("\n\n<td height=\"21\" class=\"");
/* 3648 */                           out.print(displayClass);
/* 3649 */                           out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\"><img src=\"images/deleteWidget.gif\" border=\"0\" />&nbsp; ");
/* 3650 */                           out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3651 */                           out.write("</a></td>\n\n");
/* 3652 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3653 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3657 */                       if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3658 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                       }
/*      */                       
/* 3661 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3662 */                       out.write("\n\n  ");
/*      */                       
/* 3664 */                       IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3665 */                       _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3666 */                       _jspx_th_c_005fif_005f9.setParent(null);
/*      */                       
/* 3668 */                       _jspx_th_c_005fif_005f9.setTest("${!empty ADMIN || !empty DEMO}");
/* 3669 */                       int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3670 */                       if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                         for (;;) {
/* 3672 */                           out.write("\n\n  <tr>\n\n  ");
/*      */                           
/* 3674 */                           if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                           {
/*      */ 
/* 3677 */                             out.write(10);
/* 3678 */                             out.write(9);
/*      */                             
/* 3680 */                             PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3681 */                             _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3682 */                             _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f9);
/*      */                             
/* 3684 */                             _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 3685 */                             int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3686 */                             if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                               for (;;) {
/* 3688 */                                 out.write("\n  <td height=\"21\" class=\"");
/* 3689 */                                 out.print(displayClass);
/* 3690 */                                 out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  <img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3691 */                                 out.print(FormatUtil.getString("Manage"));
/* 3692 */                                 out.write("</a> </td>\n  ");
/* 3693 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3694 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3698 */                             if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3699 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                             }
/*      */                             
/* 3702 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3703 */                             out.write("\n        ");
/*      */                             
/* 3705 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3706 */                             _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3707 */                             _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f9);
/*      */                             
/* 3709 */                             _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 3710 */                             int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3711 */                             if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                               for (;;) {
/* 3713 */                                 out.write("\n\n\n    <td class=\"");
/* 3714 */                                 out.print(displayClass);
/* 3715 */                                 out.write("\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\"><img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3716 */                                 out.print(FormatUtil.getString("Manage"));
/* 3717 */                                 out.write("</A></td>\n");
/* 3718 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3719 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3723 */                             if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3724 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                             }
/*      */                             
/* 3727 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3728 */                             out.write("\n    ");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 3734 */                             out.write(10);
/*      */                             
/* 3736 */                             PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3737 */                             _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3738 */                             _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fif_005f9);
/*      */                             
/* 3740 */                             _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 3741 */                             int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3742 */                             if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                               for (;;) {
/* 3744 */                                 out.write("\n  <td height=\"21\" class=\"");
/* 3745 */                                 out.print(displayClass);
/* 3746 */                                 out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  <img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3747 */                                 out.print(FormatUtil.getString("UnManage"));
/* 3748 */                                 out.write("</a> </td>\n  ");
/* 3749 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3750 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3754 */                             if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3755 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                             }
/*      */                             
/* 3758 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3759 */                             out.write("\n        ");
/*      */                             
/* 3761 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3762 */                             _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3763 */                             _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f9);
/*      */                             
/* 3765 */                             _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3766 */                             int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3767 */                             if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                               for (;;) {
/* 3769 */                                 out.write("\n\n\n    <td class=\"");
/* 3770 */                                 out.print(displayClass);
/* 3771 */                                 out.write("\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\"><img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3772 */                                 out.print(FormatUtil.getString("UnManage"));
/* 3773 */                                 out.write("</A></td>\n");
/* 3774 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3775 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3779 */                             if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3780 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                             }
/*      */                             
/* 3783 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3784 */                             out.write("\n    ");
/*      */                           }
/*      */                           
/*      */ 
/* 3788 */                           out.write("\n  </tr>\n  ");
/* 3789 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3790 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3794 */                       if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3795 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                       }
/*      */                       
/* 3798 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3799 */                       out.write("\n  \n   ");
/*      */                       
/* 3801 */                       IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3802 */                       _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3803 */                       _jspx_th_c_005fif_005f10.setParent(null);
/*      */                       
/* 3805 */                       _jspx_th_c_005fif_005f10.setTest("${OPERATOR}");
/* 3806 */                       int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3807 */                       if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                         for (;;) {
/* 3809 */                           out.write(10);
/* 3810 */                           out.write(32);
/* 3811 */                           out.write(32);
/*      */                           
/* 3813 */                           if (allowOperatorManage.equals("true"))
/*      */                           {
/*      */ 
/* 3816 */                             out.write("\n\n  <tr>\n\n  ");
/*      */                             
/* 3818 */                             if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                             {
/*      */ 
/* 3821 */                               out.write(10);
/* 3822 */                               out.write(9);
/*      */                               
/* 3824 */                               PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3825 */                               _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3826 */                               _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fif_005f10);
/*      */                               
/* 3828 */                               _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 3829 */                               int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3830 */                               if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                 for (;;) {
/* 3832 */                                   out.write("\n  <td height=\"21\" class=\"");
/* 3833 */                                   out.print(displayClass);
/* 3834 */                                   out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  <img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3835 */                                   out.print(FormatUtil.getString("Manage"));
/* 3836 */                                   out.write("</a> </td>\n  ");
/* 3837 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3838 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3842 */                               if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3843 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                               }
/*      */                               
/* 3846 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3847 */                               out.write("\n        ");
/*      */                               
/* 3849 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3850 */                               _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3851 */                               _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_c_005fif_005f10);
/*      */                               
/* 3853 */                               _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 3854 */                               int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3855 */                               if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                                 for (;;) {
/* 3857 */                                   out.write("\n\n\n    <td class=\"");
/* 3858 */                                   out.print(displayClass);
/* 3859 */                                   out.write("\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\"><img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3860 */                                   out.print(FormatUtil.getString("Manage"));
/* 3861 */                                   out.write("</A></td>\n");
/* 3862 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3863 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3867 */                               if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3868 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                               }
/*      */                               
/* 3871 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3872 */                               out.write("\n    ");
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 3878 */                               out.write(10);
/*      */                               
/* 3880 */                               PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3881 */                               _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3882 */                               _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_c_005fif_005f10);
/*      */                               
/* 3884 */                               _jspx_th_logic_005fpresent_005f9.setRole("DEMO");
/* 3885 */                               int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3886 */                               if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                                 for (;;) {
/* 3888 */                                   out.write("\n  <td height=\"21\" class=\"");
/* 3889 */                                   out.print(displayClass);
/* 3890 */                                   out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  <img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3891 */                                   out.print(FormatUtil.getString("UnManage"));
/* 3892 */                                   out.write("</a> </td>\n  ");
/* 3893 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3894 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3898 */                               if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3899 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                               }
/*      */                               
/* 3902 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3903 */                               out.write("\n        ");
/*      */                               
/* 3905 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3906 */                               _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 3907 */                               _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_c_005fif_005f10);
/*      */                               
/* 3909 */                               _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/* 3910 */                               int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 3911 */                               if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                                 for (;;) {
/* 3913 */                                   out.write("\n\n\n    <td class=\"");
/* 3914 */                                   out.print(displayClass);
/* 3915 */                                   out.write("\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\"><img src=\"images/icon_unmanage.gif\" border=\"0\" style=\"position:relative; top:2px;\"/> &nbsp;");
/* 3916 */                                   out.print(FormatUtil.getString("UnManage"));
/* 3917 */                                   out.write("</A></td>\n");
/* 3918 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 3919 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3923 */                               if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 3924 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                               }
/*      */                               
/* 3927 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 3928 */                               out.write("\n    ");
/*      */                             }
/*      */                             
/*      */ 
/* 3932 */                             out.write("\n  </tr>\n   ");
/*      */                           }
/*      */                           
/*      */ 
/* 3936 */                           out.write(10);
/* 3937 */                           out.write(32);
/* 3938 */                           out.write(32);
/* 3939 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3940 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3944 */                       if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3945 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                       }
/*      */                       
/* 3948 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3949 */                       out.write(10);
/* 3950 */                       out.write(32);
/* 3951 */                       out.write(32);
/*      */                       
/* 3953 */                       PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3954 */                       _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 3955 */                       _jspx_th_logic_005fpresent_005f10.setParent(null);
/*      */                       
/* 3957 */                       _jspx_th_logic_005fpresent_005f10.setRole("DEMO,ENTERPRISEADMIN,ADMIN");
/* 3958 */                       int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 3959 */                       if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                         for (;;) {
/* 3961 */                           out.write("\n  \t<tr>\n  \t");
/*      */                           
/* 3963 */                           PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3964 */                           _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 3965 */                           _jspx_th_logic_005fpresent_005f11.setParent(_jspx_th_logic_005fpresent_005f10);
/*      */                           
/* 3967 */                           _jspx_th_logic_005fpresent_005f11.setRole("DEMO");
/* 3968 */                           int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 3969 */                           if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                             for (;;) {
/* 3971 */                               out.write("\n \t <td height=\"21\" class=\"");
/* 3972 */                               out.print(displayClass);
/* 3973 */                               out.write("\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n \t \t  <img src=\"images/icon_associateaction.gif\" border=\"0\" />&nbsp; ");
/* 3974 */                               out.print(ALERTCONFIG_TEXT);
/* 3975 */                               out.write("</a>\n \t </td>\n  ");
/* 3976 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 3977 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3981 */                           if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 3982 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11); return;
/*      */                           }
/*      */                           
/* 3985 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 3986 */                           out.write(10);
/* 3987 */                           out.write(32);
/* 3988 */                           out.write(32);
/*      */                           
/* 3990 */                           NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3991 */                           _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 3992 */                           _jspx_th_logic_005fnotPresent_005f5.setParent(_jspx_th_logic_005fpresent_005f10);
/*      */                           
/* 3994 */                           _jspx_th_logic_005fnotPresent_005f5.setRole("DEMO");
/* 3995 */                           int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 3996 */                           if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                             for (;;) {
/* 3998 */                               out.write("\n    <td height=\"21\" class=\"");
/* 3999 */                               out.print(displayClass);
/* 4000 */                               out.write("\"><a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 4001 */                               if (_jspx_meth_c_005fout_005f9(_jspx_th_logic_005fnotPresent_005f5, _jspx_page_context))
/*      */                                 return;
/* 4003 */                               out.write("&mtype=");
/* 4004 */                               out.print(mon_type);
/* 4005 */                               out.write("&viewBy=monitors\" class=\"new-left-links\">\n                <img src=\"images/icon_associateaction.gif\" border=\"0\" /> &nbsp;");
/* 4006 */                               out.print(ALERTCONFIG_TEXT);
/* 4007 */                               out.write("</a>\n        </td>\n   ");
/* 4008 */                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 4009 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4013 */                           if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 4014 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */                           }
/*      */                           
/* 4017 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 4018 */                           out.write("\n\n  </tr>\n  ");
/* 4019 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 4020 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4024 */                       if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 4025 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10); return;
/*      */                       }
/*      */                       
/* 4028 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 4029 */                       out.write(10);
/* 4030 */                       out.write(32);
/* 4031 */                       out.write(32);
/* 4032 */                       if ((!isAgentEnabled.equals("YES")) || (!isParent)) {
/* 4033 */                         out.write(10);
/* 4034 */                         out.write(32);
/* 4035 */                         out.write(32);
/*      */                         
/* 4037 */                         NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4038 */                         _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 4039 */                         _jspx_th_logic_005fnotPresent_005f6.setParent(null);
/*      */                         
/* 4041 */                         _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 4042 */                         int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 4043 */                         if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                           for (;;) {
/* 4045 */                             out.write("\n    \n        <tr>\n        <td width=\"49%\" height=\"21\" class=\"");
/* 4046 */                             out.print(displayClass);
/* 4047 */                             out.write("\" >\n        <a href=\"javascript:confPollNow();\" class=\"new-left-links\">\n\t\t<img src=\"images/cam_report_enabled.gif\" border=\"0\" style=\"position:relative; top:3px;\"/> &nbsp;");
/* 4048 */                             out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 4049 */                             out.write("</a></td>\n      </tr>\n    ");
/* 4050 */                             int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 4051 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4055 */                         if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 4056 */                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6); return;
/*      */                         }
/*      */                         
/* 4059 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 4060 */                         out.write("\n    ");
/*      */                         
/* 4062 */                         PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4063 */                         _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 4064 */                         _jspx_th_logic_005fpresent_005f12.setParent(null);
/*      */                         
/* 4066 */                         _jspx_th_logic_005fpresent_005f12.setRole("DEMO");
/* 4067 */                         int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 4068 */                         if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                           for (;;) {
/* 4070 */                             out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"");
/* 4071 */                             out.print(displayClass);
/* 4072 */                             out.write("\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\"><img src=\"images/cam_report_enabled.gif\" border=\"0\" style=\"position:relative; top:3px;\"/> &nbsp;");
/* 4073 */                             out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 4074 */                             out.write("</a></td>\n      </td></tr>\n    ");
/* 4075 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 4076 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 4080 */                         if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 4081 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12); return;
/*      */                         }
/*      */                         
/* 4084 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 4085 */                         out.write(10);
/* 4086 */                         out.write(32);
/* 4087 */                         out.write(32);
/*      */                       }
/* 4089 */                       out.write("\n    ");
/*      */                       
/* 4091 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4092 */                       _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 4093 */                       _jspx_th_logic_005fnotPresent_005f7.setParent(null);
/*      */                       
/* 4095 */                       _jspx_th_logic_005fnotPresent_005f7.setRole("DEMO,ENTERPRISEADMIN,OPERATOR");
/* 4096 */                       int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 4097 */                       if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                         for (;;) {
/* 4099 */                           out.write(10);
/*      */                           
/* 4101 */                           ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4102 */                           _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 4103 */                           _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_logic_005fnotPresent_005f7);
/* 4104 */                           int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 4105 */                           if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                             for (;;) {
/* 4107 */                               out.write(10);
/*      */                               
/* 4109 */                               WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4110 */                               _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 4111 */                               _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                               
/* 4113 */                               _jspx_th_c_005fwhen_005f8.setTest("${param.type=='QueryMonitor'}");
/* 4114 */                               int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 4115 */                               if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                 for (;;) {
/* 4117 */                                   out.write("\n\n<tr>\n\t\t<td height=\"21\" class=\"");
/* 4118 */                                   out.print(displayClass);
/* 4119 */                                   out.write("\">\n\n<a href=\"javascript:enableReports()\" class=\"new-left-links\"><img src=\"images/icon-anamoly-responsetime.gif\" border=\"0\" /> &nbsp;");
/* 4120 */                                   out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 4121 */                                   out.write("</a>\n     </td>\n");
/* 4122 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 4123 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4127 */                               if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 4128 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                               }
/*      */                               
/* 4131 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 4132 */                               out.write(10);
/*      */                               
/* 4134 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4135 */                               _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 4136 */                               _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f2);
/* 4137 */                               int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 4138 */                               if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                 for (;;) {
/* 4140 */                                   out.write("\n\n  <tr>\n    <td height=\"21\" class=\"");
/* 4141 */                                   out.print(displayClass);
/* 4142 */                                   out.write("\"><a href=\"/customReports.do?method=showCustomReports&selectedType=");
/* 4143 */                                   out.print(mon_type);
/* 4144 */                                   out.write("&resourceid=");
/* 4145 */                                   out.print(resID);
/* 4146 */                                   out.write("\" class=\"new-left-links\">\n\t<img src=\"images/icon-anamoly-responsetime.gif\" border=\"0\" /> &nbsp;");
/* 4147 */                                   out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 4148 */                                   out.write("</a></td>\n  </tr>\n");
/* 4149 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 4150 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4154 */                               if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 4155 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                               }
/*      */                               
/* 4158 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 4159 */                               out.write(10);
/* 4160 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 4161 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 4165 */                           if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 4166 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                           }
/*      */                           
/* 4169 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 4170 */                           out.write(10);
/* 4171 */                           out.write(32);
/* 4172 */                           out.write(32);
/* 4173 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 4174 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4178 */                       if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 4179 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7); return;
/*      */                       }
/*      */                       
/* 4182 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 4183 */                       out.write("\n    ");
/*      */                       
/* 4185 */                       PresentTag _jspx_th_logic_005fpresent_005f13 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4186 */                       _jspx_th_logic_005fpresent_005f13.setPageContext(_jspx_page_context);
/* 4187 */                       _jspx_th_logic_005fpresent_005f13.setParent(null);
/*      */                       
/* 4189 */                       _jspx_th_logic_005fpresent_005f13.setRole("DEMO");
/* 4190 */                       int _jspx_eval_logic_005fpresent_005f13 = _jspx_th_logic_005fpresent_005f13.doStartTag();
/* 4191 */                       if (_jspx_eval_logic_005fpresent_005f13 != 0) {
/*      */                         for (;;) {
/* 4193 */                           out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"");
/* 4194 */                           out.print(displayClass);
/* 4195 */                           out.write("\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 4196 */                           out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 4197 */                           out.write("</a></td>\n      </td></tr>\n    ");
/* 4198 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f13.doAfterBody();
/* 4199 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4203 */                       if (_jspx_th_logic_005fpresent_005f13.doEndTag() == 5) {
/* 4204 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13); return;
/*      */                       }
/*      */                       
/* 4207 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
/* 4208 */                       out.write("\n    ");
/*      */                       
/* 4210 */                       IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4211 */                       _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 4212 */                       _jspx_th_c_005fif_005f11.setParent(null);
/*      */                       
/* 4214 */                       _jspx_th_c_005fif_005f11.setTest("${isCustomConfigEnabled}");
/* 4215 */                       int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 4216 */                       if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                         for (;;) {
/* 4218 */                           out.write("\n      ");
/*      */                           
/* 4220 */                           ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4221 */                           _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4222 */                           _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f11);
/*      */                           
/* 4224 */                           _jspx_th_c_005fforEach_005f0.setVar("eachCustomConfig");
/*      */                           
/* 4226 */                           _jspx_th_c_005fforEach_005f0.setItems("${CustomConfigList}");
/*      */                           
/* 4228 */                           _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 4229 */                           int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                           try {
/* 4231 */                             int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4232 */                             if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                               for (;;) {
/* 4234 */                                 out.write("\n\t<tr>\n\t  ");
/*      */                                 
/* 4236 */                                 PresentTag _jspx_th_logic_005fpresent_005f14 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4237 */                                 _jspx_th_logic_005fpresent_005f14.setPageContext(_jspx_page_context);
/* 4238 */                                 _jspx_th_logic_005fpresent_005f14.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                 
/* 4240 */                                 _jspx_th_logic_005fpresent_005f14.setRole("DEMO");
/* 4241 */                                 int _jspx_eval_logic_005fpresent_005f14 = _jspx_th_logic_005fpresent_005f14.doStartTag();
/* 4242 */                                 if (_jspx_eval_logic_005fpresent_005f14 != 0) {
/*      */                                   for (;;) {
/* 4244 */                                     out.write("\n\t    <td height=\"21\" class=\"");
/* 4245 */                                     out.print(displayClass);
/* 4246 */                                     out.write("\" title=\"");
/* 4247 */                                     if (_jspx_meth_c_005fout_005f10(_jspx_th_logic_005fpresent_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4249 */                                     out.write("\"> <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n\t      <img src=\"");
/* 4250 */                                     if (_jspx_meth_c_005fout_005f11(_jspx_th_logic_005fpresent_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4252 */                                     out.write("\" border=\"0\"/>&nbsp;");
/* 4253 */                                     if (_jspx_meth_c_005fout_005f12(_jspx_th_logic_005fpresent_005f14, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4255 */                                     out.write("</a> </td>\n\t  ");
/* 4256 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f14.doAfterBody();
/* 4257 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4261 */                                 if (_jspx_th_logic_005fpresent_005f14.doEndTag() == 5) {
/* 4262 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14);
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 4265 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14);
/* 4266 */                                 out.write("\n\t  ");
/*      */                                 
/* 4268 */                                 NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 4269 */                                 _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 4270 */                                 _jspx_th_logic_005fnotPresent_005f8.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                 
/* 4272 */                                 _jspx_th_logic_005fnotPresent_005f8.setRole("DEMO");
/* 4273 */                                 int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 4274 */                                 if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */                                   for (;;) {
/* 4276 */                                     out.write("\n\t     <td height=\"21\" class=\"");
/* 4277 */                                     out.print(displayClass);
/* 4278 */                                     out.write("\" title=\"");
/* 4279 */                                     if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4281 */                                     out.write("\">\t\t\n\t\t  <a href=\"javascript:handleConfig('");
/* 4282 */                                     if (_jspx_meth_c_005fout_005f14(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4284 */                                     out.write(39);
/* 4285 */                                     out.write(44);
/* 4286 */                                     out.write(39);
/* 4287 */                                     if (_jspx_meth_c_005fout_005f15(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4289 */                                     out.write(39);
/* 4290 */                                     out.write(44);
/* 4291 */                                     out.write(39);
/* 4292 */                                     if (_jspx_meth_c_005fout_005f16(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4294 */                                     out.write(39);
/* 4295 */                                     out.write(44);
/* 4296 */                                     out.write(39);
/* 4297 */                                     if (_jspx_meth_c_005fout_005f17(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4299 */                                     out.write(39);
/* 4300 */                                     out.write(44);
/* 4301 */                                     out.write(39);
/* 4302 */                                     if (_jspx_meth_c_005fout_005f18(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4304 */                                     out.write(39);
/* 4305 */                                     out.write(44);
/* 4306 */                                     out.write(39);
/* 4307 */                                     if (_jspx_meth_c_005fout_005f19(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4309 */                                     out.write(39);
/* 4310 */                                     out.write(44);
/* 4311 */                                     out.write(39);
/* 4312 */                                     if (_jspx_meth_c_005fout_005f20(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4314 */                                     out.write(39);
/* 4315 */                                     out.write(44);
/* 4316 */                                     out.write(39);
/* 4317 */                                     if (_jspx_meth_c_005fout_005f21(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4319 */                                     out.write("')\"   class=\"new-left-links\">\n\t\t    <img src=\"");
/* 4320 */                                     if (_jspx_meth_c_005fout_005f22(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4322 */                                     out.write("\" border=\"0\"/>&nbsp;");
/* 4323 */                                     if (_jspx_meth_c_005fout_005f23(_jspx_th_logic_005fnotPresent_005f8, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 4325 */                                     out.write("\n\t\t  </a>\n\t     </td>\n\t  ");
/* 4326 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 4327 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4331 */                                 if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 4332 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 4335 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 4336 */                                 out.write("\n\t</tr>\n\t");
/* 4337 */                                 int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4338 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4342 */                             if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4350 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/*      */                           }
/*      */                           catch (Throwable _jspx_exception)
/*      */                           {
/*      */                             for (;;)
/*      */                             {
/* 4346 */                               int tmp13369_13368 = 0; int[] tmp13369_13366 = _jspx_push_body_count_c_005fforEach_005f0; int tmp13371_13370 = tmp13369_13366[tmp13369_13368];tmp13369_13366[tmp13369_13368] = (tmp13371_13370 - 1); if (tmp13371_13370 <= 0) break;
/* 4347 */                               out = _jspx_page_context.popBody(); }
/* 4348 */                             _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                           } finally {
/* 4350 */                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 4351 */                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                           }
/* 4353 */                           out.write("\n    ");
/* 4354 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 4355 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4359 */                       if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 4360 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                       }
/*      */                       
/* 4363 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 4364 */                       out.write("\n    ");
/*      */                       
/* 4366 */                       IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4367 */                       _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 4368 */                       _jspx_th_c_005fif_005f12.setParent(null);
/*      */                       
/* 4370 */                       _jspx_th_c_005fif_005f12.setTest("${showAlarmHistory == true }");
/* 4371 */                       int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 4372 */                       if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                         for (;;) {
/* 4374 */                           out.write("\n    <tr>\n    <td height=\"21\" class=\"");
/* 4375 */                           out.print(displayClass);
/* 4376 */                           out.write("\" title=\"");
/* 4377 */                           if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fif_005f12, _jspx_page_context))
/*      */                             return;
/* 4379 */                           out.write("\">\n    <a href=\"javascript:void()\" onclick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 4380 */                           out.print(resourceid_poll + "_" + healthid);
/* 4381 */                           out.write("&monitortype=");
/* 4382 */                           out.print(resourcetype_poll);
/* 4383 */                           out.write("')\" class=\"new-left-links\"><img src=\"/images/icon_alarm_history.png\" border=\"0\" /> &nbsp;");
/* 4384 */                           out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 4385 */                           out.write("</a>\n    </td>\n    </tr>\n    ");
/* 4386 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 4387 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 4391 */                       if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 4392 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                       }
/*      */                       
/* 4395 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 4396 */                       out.write("\n    ");
/* 4397 */                       out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                       
/* 4399 */                       if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                       {
/* 4401 */                         Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 4402 */                         String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                         
/* 4404 */                         String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 4405 */                         String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 4406 */                         if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                         {
/* 4408 */                           if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                           {
/*      */ 
/* 4411 */                             out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4412 */                             out.print(ciInfoUrl);
/* 4413 */                             out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 4414 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 4415 */                             out.write("</a></td>");
/* 4416 */                             out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4417 */                             out.print(ciRLUrl);
/* 4418 */                             out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 4419 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 4420 */                             out.write("</a></td>");
/* 4421 */                             out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                           }
/* 4425 */                           else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                           {
/*      */ 
/* 4428 */                             out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 4429 */                             out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 4430 */                             out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4431 */                             out.print(ciInfoUrl);
/* 4432 */                             out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 4433 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 4434 */                             out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4435 */                             out.print(ciRLUrl);
/* 4436 */                             out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 4437 */                             out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 4438 */                             out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                       
/* 4443 */                       out.write("\n \n \n\n");
/* 4444 */                       out.write("\n</table>\n");
/* 4445 */                       if (!mon_type.equals("QueryMonitor")) {
/* 4446 */                         out.write("\n\t</div>\n\t</div>\n\t</td>\n\t</tr>\n\t</table>\n\t\n\t\n");
/*      */                       } else {
/* 4448 */                         out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 4449 */                         out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 4450 */                         out.write("</td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"50%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4451 */                         if (_jspx_meth_c_005fout_005f25(_jspx_page_context))
/*      */                           return;
/* 4453 */                         out.write("&attributeid=");
/* 4454 */                         out.print(healthid);
/* 4455 */                         out.write("')\" class=\"new-left-links\">");
/* 4456 */                         out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4457 */                         out.write("</a> </td>\n    <td ><a  href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4458 */                         if (_jspx_meth_c_005fout_005f26(_jspx_page_context))
/*      */                           return;
/* 4460 */                         out.write("&attributeid=");
/* 4461 */                         out.print(healthid);
/* 4462 */                         out.write("')\">");
/* 4463 */                         out.print(getSeverityImageForHealth(alert.getProperty(request.getParameter("resourceid") + "#" + healthid)));
/* 4464 */                         out.write("</a></td>\n  </tr>\n  <tr  onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"50%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4465 */                         if (_jspx_meth_c_005fout_005f27(_jspx_page_context))
/*      */                           return;
/* 4467 */                         out.write("&attributeid=");
/* 4468 */                         out.print(availabilityid);
/* 4469 */                         out.write("')\" class=\"new-left-links\">");
/* 4470 */                         out.print(FormatUtil.getString("Availability"));
/* 4471 */                         out.write("</a> </td>\n    <td ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4472 */                         if (_jspx_meth_c_005fout_005f28(_jspx_page_context))
/*      */                           return;
/* 4474 */                         out.write("&attributeid=");
/* 4475 */                         out.print(availabilityid);
/* 4476 */                         out.write("')\">");
/* 4477 */                         out.print(getSeverityImageForAvailability(alert.getProperty(request.getParameter("resourceid") + "#" + availabilityid)));
/* 4478 */                         out.write("</a></td>\n  </tr>\n</table>\n\n\n\n\n");
/* 4479 */                         out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                         
/*      */ 
/*      */ 
/* 4483 */                         boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 4484 */                         if (EnterpriseUtil.isIt360MSPEdition)
/*      */                         {
/* 4486 */                           showAssociatedBSG = false;
/*      */                           
/*      */ 
/*      */ 
/* 4490 */                           CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 4491 */                           CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 4492 */                           String loginName = request.getUserPrincipal().getName();
/* 4493 */                           CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                           
/* 4495 */                           if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                           {
/* 4497 */                             showAssociatedBSG = true;
/*      */                           }
/*      */                         }
/* 4500 */                         String monitorType = request.getParameter("type");
/* 4501 */                         ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 4502 */                         boolean mon = conf1.isConfMonitor(monitorType);
/* 4503 */                         if (showAssociatedBSG)
/*      */                         {
/* 4505 */                           Hashtable associatedmgs = new Hashtable();
/* 4506 */                           String resId = request.getParameter("resourceid");
/* 4507 */                           request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 4508 */                           if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                           {
/* 4510 */                             mon = false;
/*      */                           }
/*      */                           
/* 4513 */                           if (!mon)
/*      */                           {
/* 4515 */                             out.write(10);
/* 4516 */                             out.write(10);
/*      */                             
/* 4518 */                             IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4519 */                             _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 4520 */                             _jspx_th_c_005fif_005f13.setParent(null);
/*      */                             
/* 4522 */                             _jspx_th_c_005fif_005f13.setTest("${!empty associatedmgs}");
/* 4523 */                             int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 4524 */                             if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                               for (;;) {
/* 4526 */                                 out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 4527 */                                 out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 4528 */                                 out.write("</td>\n        </tr>\n        ");
/*      */                                 
/* 4530 */                                 ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4531 */                                 _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 4532 */                                 _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f13);
/*      */                                 
/* 4534 */                                 _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                                 
/* 4536 */                                 _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                                 
/* 4538 */                                 _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 4539 */                                 int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                 try {
/* 4541 */                                   int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 4542 */                                   if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                     for (;;) {
/* 4544 */                                       out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 4545 */                                       if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4603 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 4604 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                       }
/* 4547 */                                       out.write("&method=showApplication\" title=\"");
/* 4548 */                                       if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4603 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 4604 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                       }
/* 4550 */                                       out.write("\"  class=\"new-left-links\">\n         ");
/* 4551 */                                       if (_jspx_meth_c_005fset_005f5(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4603 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 4604 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                       }
/* 4553 */                                       out.write("\n    \t");
/* 4554 */                                       out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4555 */                                       out.write("\n         </a></td>\n        <td>");
/*      */                                       
/* 4557 */                                       PresentTag _jspx_th_logic_005fpresent_005f15 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4558 */                                       _jspx_th_logic_005fpresent_005f15.setPageContext(_jspx_page_context);
/* 4559 */                                       _jspx_th_logic_005fpresent_005f15.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                       
/* 4561 */                                       _jspx_th_logic_005fpresent_005f15.setRole("ADMIN");
/* 4562 */                                       int _jspx_eval_logic_005fpresent_005f15 = _jspx_th_logic_005fpresent_005f15.doStartTag();
/* 4563 */                                       if (_jspx_eval_logic_005fpresent_005f15 != 0) {
/*      */                                         for (;;) {
/* 4565 */                                           out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 4566 */                                           if (_jspx_meth_c_005fout_005f32(_jspx_th_logic_005fpresent_005f15, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4603 */                                             _jspx_th_c_005fforEach_005f1.doFinally();
/* 4604 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                           }
/* 4568 */                                           out.write(39);
/* 4569 */                                           out.write(44);
/* 4570 */                                           out.write(39);
/* 4571 */                                           out.print(resId);
/* 4572 */                                           out.write(39);
/* 4573 */                                           out.write(44);
/* 4574 */                                           out.write(39);
/* 4575 */                                           out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4576 */                                           out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 4577 */                                           out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4578 */                                           out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 4579 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f15.doAfterBody();
/* 4580 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4584 */                                       if (_jspx_th_logic_005fpresent_005f15.doEndTag() == 5) {
/* 4585 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15);
/*      */                                         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4603 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 4604 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                       }
/* 4588 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15);
/* 4589 */                                       out.write("</td>\n        </tr>\n\t");
/* 4590 */                                       int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4591 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4595 */                                   if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4603 */                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 4604 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                   }
/*      */                                 }
/*      */                                 catch (Throwable _jspx_exception)
/*      */                                 {
/*      */                                   for (;;)
/*      */                                   {
/* 4599 */                                     int tmp15318_15317 = 0; int[] tmp15318_15315 = _jspx_push_body_count_c_005fforEach_005f1; int tmp15320_15319 = tmp15318_15315[tmp15318_15317];tmp15318_15315[tmp15318_15317] = (tmp15320_15319 - 1); if (tmp15320_15319 <= 0) break;
/* 4600 */                                     out = _jspx_page_context.popBody(); }
/* 4601 */                                   _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                 } finally {
/* 4603 */                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 4604 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                 }
/* 4606 */                                 out.write("\n      </table>\n ");
/* 4607 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 4608 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4612 */                             if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 4613 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                             }
/*      */                             
/* 4616 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 4617 */                             out.write(10);
/* 4618 */                             out.write(32);
/*      */                             
/* 4620 */                             IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4621 */                             _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 4622 */                             _jspx_th_c_005fif_005f14.setParent(null);
/*      */                             
/* 4624 */                             _jspx_th_c_005fif_005f14.setTest("${empty associatedmgs}");
/* 4625 */                             int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 4626 */                             if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                               for (;;) {
/* 4628 */                                 out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 4629 */                                 out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 4630 */                                 out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 4631 */                                 out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 4632 */                                 out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 4633 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 4634 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4638 */                             if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 4639 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                             }
/*      */                             
/* 4642 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 4643 */                             out.write(10);
/* 4644 */                             out.write(32);
/* 4645 */                             out.write(10);
/*      */ 
/*      */                           }
/* 4648 */                           else if (mon)
/*      */                           {
/*      */ 
/*      */ 
/* 4652 */                             out.write(10);
/*      */                             
/* 4654 */                             IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4655 */                             _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 4656 */                             _jspx_th_c_005fif_005f15.setParent(null);
/*      */                             
/* 4658 */                             _jspx_th_c_005fif_005f15.setTest("${!empty associatedmgs}");
/* 4659 */                             int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 4660 */                             if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                               for (;;) {
/* 4662 */                                 out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 4663 */                                 if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f15, _jspx_page_context))
/*      */                                   return;
/* 4665 */                                 out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                                 
/* 4667 */                                 ForEachTag _jspx_th_c_005fforEach_005f2 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4668 */                                 _jspx_th_c_005fforEach_005f2.setPageContext(_jspx_page_context);
/* 4669 */                                 _jspx_th_c_005fforEach_005f2.setParent(_jspx_th_c_005fif_005f15);
/*      */                                 
/* 4671 */                                 _jspx_th_c_005fforEach_005f2.setVar("ha");
/*      */                                 
/* 4673 */                                 _jspx_th_c_005fforEach_005f2.setItems("${associatedmgs}");
/*      */                                 
/* 4675 */                                 _jspx_th_c_005fforEach_005f2.setVarStatus("status");
/* 4676 */                                 int[] _jspx_push_body_count_c_005fforEach_005f2 = { 0 };
/*      */                                 try {
/* 4678 */                                   int _jspx_eval_c_005fforEach_005f2 = _jspx_th_c_005fforEach_005f2.doStartTag();
/* 4679 */                                   if (_jspx_eval_c_005fforEach_005f2 != 0) {
/*      */                                     for (;;) {
/* 4681 */                                       out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 4682 */                                       if (_jspx_meth_c_005fout_005f33(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4743 */                                         _jspx_th_c_005fforEach_005f2.doFinally();
/* 4744 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                       }
/* 4684 */                                       out.write("&method=showApplication\" title=\"");
/* 4685 */                                       if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4743 */                                         _jspx_th_c_005fforEach_005f2.doFinally();
/* 4744 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                       }
/* 4687 */                                       out.write("\"  class=\"staticlinks\">\n         ");
/* 4688 */                                       if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fforEach_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4743 */                                         _jspx_th_c_005fforEach_005f2.doFinally();
/* 4744 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                       }
/* 4690 */                                       out.write("\n    \t");
/* 4691 */                                       out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4692 */                                       out.write("</a></span>\t\n\t\t ");
/*      */                                       
/* 4694 */                                       PresentTag _jspx_th_logic_005fpresent_005f16 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4695 */                                       _jspx_th_logic_005fpresent_005f16.setPageContext(_jspx_page_context);
/* 4696 */                                       _jspx_th_logic_005fpresent_005f16.setParent(_jspx_th_c_005fforEach_005f2);
/*      */                                       
/* 4698 */                                       _jspx_th_logic_005fpresent_005f16.setRole("ADMIN");
/* 4699 */                                       int _jspx_eval_logic_005fpresent_005f16 = _jspx_th_logic_005fpresent_005f16.doStartTag();
/* 4700 */                                       if (_jspx_eval_logic_005fpresent_005f16 != 0) {
/*      */                                         for (;;) {
/* 4702 */                                           out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 4703 */                                           if (_jspx_meth_c_005fout_005f36(_jspx_th_logic_005fpresent_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4743 */                                             _jspx_th_c_005fforEach_005f2.doFinally();
/* 4744 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                           }
/* 4705 */                                           out.write(39);
/* 4706 */                                           out.write(44);
/* 4707 */                                           out.write(39);
/* 4708 */                                           out.print(resId);
/* 4709 */                                           out.write(39);
/* 4710 */                                           out.write(44);
/* 4711 */                                           out.write(39);
/* 4712 */                                           out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4713 */                                           out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 4714 */                                           out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4715 */                                           out.write("\"  title=\"");
/* 4716 */                                           if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_logic_005fpresent_005f16, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4743 */                                             _jspx_th_c_005fforEach_005f2.doFinally();
/* 4744 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                           }
/* 4718 */                                           out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 4719 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f16.doAfterBody();
/* 4720 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4724 */                                       if (_jspx_th_logic_005fpresent_005f16.doEndTag() == 5) {
/* 4725 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f16);
/*      */                                         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4743 */                                         _jspx_th_c_005fforEach_005f2.doFinally();
/* 4744 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                       }
/* 4728 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f16);
/* 4729 */                                       out.write("\n\n\t\t \t");
/* 4730 */                                       int evalDoAfterBody = _jspx_th_c_005fforEach_005f2.doAfterBody();
/* 4731 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4735 */                                   if (_jspx_th_c_005fforEach_005f2.doEndTag() == 5)
/*      */                                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4743 */                                     _jspx_th_c_005fforEach_005f2.doFinally();
/* 4744 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2); return;
/*      */                                   }
/*      */                                 }
/*      */                                 catch (Throwable _jspx_exception)
/*      */                                 {
/*      */                                   for (;;)
/*      */                                   {
/* 4739 */                                     int tmp16342_16341 = 0; int[] tmp16342_16339 = _jspx_push_body_count_c_005fforEach_005f2; int tmp16344_16343 = tmp16342_16339[tmp16342_16341];tmp16342_16339[tmp16342_16341] = (tmp16344_16343 - 1); if (tmp16344_16343 <= 0) break;
/* 4740 */                                     out = _jspx_page_context.popBody(); }
/* 4741 */                                   _jspx_th_c_005fforEach_005f2.doCatch(_jspx_exception);
/*      */                                 } finally {
/* 4743 */                                   _jspx_th_c_005fforEach_005f2.doFinally();
/* 4744 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f2);
/*      */                                 }
/* 4746 */                                 out.write("\n\t\n\t\t\t</td>\n\t ");
/* 4747 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 4748 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 4752 */                             if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 4753 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                             }
/*      */                             
/* 4756 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 4757 */                             out.write(10);
/* 4758 */                             out.write(32);
/* 4759 */                             if (_jspx_meth_c_005fif_005f16(_jspx_page_context))
/*      */                               return;
/* 4761 */                             out.write(32);
/* 4762 */                             out.write(10);
/*      */                           }
/*      */                           
/*      */                         }
/* 4766 */                         else if (mon)
/*      */                         {
/*      */ 
/* 4769 */                           out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4770 */                           if (_jspx_meth_fmt_005fmessage_005f6(_jspx_page_context))
/*      */                             return;
/* 4772 */                           out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                         }
/*      */                         
/*      */ 
/* 4776 */                         out.write(9);
/* 4777 */                         out.write(9);
/* 4778 */                         out.write(10);
/*      */                       }
/* 4780 */                       out.write(10);
/* 4781 */                       out.write("\n\t</td>\n</tr>\n<tr><td style=\"height:5px;\"><img src=\"images/spacer.gif\" style=\"height:10px;\"></td></tr>\n</table>\n");
/* 4782 */                       JspRuntimeLibrary.include(request, response, "/jsp/includes/CommonDetailsHeader.jsp" + ("/jsp/includes/CommonDetailsHeader.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("monitorname", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf((String)request.getAttribute("monitorname")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("healthId", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(ess_atts.getProperty("Health")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("availabilityId", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(ess_atts.getProperty("Availability")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("isConfMonitor", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("TabId", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(m), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("collectionTime", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf((String)request.getAttribute("maxcollectiontime")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resID", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resID), request.getCharacterEncoding()), out, false);
/* 4783 */                       out.write(10);
/* 4784 */                       out.write(9);
/* 4785 */                       out.write(32);
/*      */                       
/* 4787 */                       SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4788 */                       _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 4789 */                       _jspx_th_c_005fset_005f7.setParent(null);
/*      */                       
/* 4791 */                       _jspx_th_c_005fset_005f7.setVar("reportPeriodmsg");
/* 4792 */                       int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 4793 */                       if (_jspx_eval_c_005fset_005f7 != 0) {
/* 4794 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 4795 */                           out = _jspx_page_context.pushBody();
/* 4796 */                           _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 4797 */                           _jspx_th_c_005fset_005f7.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 4800 */                           out.write(32);
/* 4801 */                           out.print((String)request.getAttribute("ReportPeriodMessage"));
/* 4802 */                           int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 4803 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 4806 */                         if (_jspx_eval_c_005fset_005f7 != 1) {
/* 4807 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 4810 */                       if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 4811 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7); return;
/*      */                       }
/*      */                       
/* 4814 */                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f7);
/* 4815 */                       out.write(10);
/*      */                       try
/*      */                       {
/* 4818 */                         out.write(10);
/*      */                         
/* 4820 */                         if ((tabConfiguration != null) && (!tabConfiguration.equals("null")) && (noOftabs != 0)) {
/* 4821 */                           out.write("\n\t<br>\n\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n  \t\t\t<tbody>\n    \t\t\t<tr class=\"tabBtmLine\">\n     \t\t\t\t <td nowrap=\"nowrap\" id=\"mytd\">\n      \t\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" id=\"InnerTab\">\n         \t\t\t\t\t\t <tbody>\n           \t\t\t\t\t\t\t <tr>\n              \t\t\t\t\t\t\t<td width=\"17\">&nbsp;&nbsp;&nbsp;&nbsp;</td>\n              \t\t\t\t\t\t\t");
/* 4822 */                           String selectedtab = (String)tabConfiguration.get(m);
/* 4823 */                           for (int i = 0; i < noOftabs; ???++) {
/* 4824 */                             String title = (String)tabConfiguration.get(i);
/*      */                             
/* 4826 */                             out.write("\n\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"cursor: pointer;\" id=\"");
/* 4827 */                             out.print(title);
/* 4828 */                             out.write("Tab\"  title=\"");
/* 4829 */                             out.print(FormatUtil.getString(title));
/* 4830 */                             out.write("\">\n\t                \t\t\t\t\t\t\t  <tbody>\n\t\t                \t\t\t\t\t\t\t  <tr>\n\t\t\t\t\t\t\t\t\t                      <td id=\"Tab_");
/* 4831 */                             out.print(i);
/* 4832 */                             out.write("_left\" class=\"");
/* 4833 */                             out.print(selectedtab.equals(title) ? "tbSelected_Left" : "tbUnselected_Left");
/* 4834 */                             out.write("\" ><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n\t\t\t\t\t\t\t\t\t                      <td  id=\"Tab_");
/* 4835 */                             out.print(i);
/* 4836 */                             out.write("_middle\" style=\"padding-left:5px;padding-right:5px\" onclick=\"gettabData('");
/* 4837 */                             out.print(i);
/* 4838 */                             out.write("');\"  class=\"");
/* 4839 */                             out.print(selectedtab.equals(title) ? "tbSelected_Middle" : "tbUnselected_Middle");
/* 4840 */                             out.write("\">\n\t\t\t\t\t\t\t\t\t                   \t  \t <span id=\"Tab_");
/* 4841 */                             out.print(i);
/* 4842 */                             out.write("_text\" class=\"");
/* 4843 */                             out.print(selectedtab.equals(title) ? "tabLinkActive" : "bodytext");
/* 4844 */                             out.write(34);
/* 4845 */                             out.write(62);
/* 4846 */                             out.print(FormatUtil.getString(title));
/* 4847 */                             out.write("</span>\n\t\t                      \t\t\t\t\t\t\t </td>\n\t\t                      \t\t\t\t\t\t\t <td  id=\"Tab_");
/* 4848 */                             out.print(i);
/* 4849 */                             out.write("_right\" class=\"");
/* 4850 */                             out.print(selectedtab.equals(title) ? "tbSelected_Right" : "tbUnselected_Right");
/* 4851 */                             out.write("\"><img width=\"1\" height=\"1\" src=\"/images/spacer.gif\" alt=\"spacer\"></td>\n\t\t                   \t\t\t\t\t\t\t</tr>\n\t                  \t\t\t\t\t\t\t</tbody>\n              \t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t");
/*      */                           }
/* 4853 */                           out.write("\n              \t\t\t\t\t\t</tr>\n              \t\t\t\t\t</tbody>\n              \t\t\t\t</table>\n              \t\t\t</td>\n              \t\t</tr>\n              \t</tbody>\n              </table>\n              <span  id=\"quickMsg\">");
/* 4854 */                           if (_jspx_meth_c_005fout_005f37(_jspx_page_context))
/*      */                             return;
/* 4856 */                           out.write("</span><br>\n              <div id=\"monitorInformwithTab\" style=\"DISPLAY: block\">\t\t\t\t\t\n");
/*      */                         } else {
/* 4858 */                           out.write("\n\t\t\t<div id=\"monitorInformation\" style=\"DISPLAY: block\">\n");
/*      */                         }
/* 4860 */                         out.write("\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n<tr>\n");
/* 4861 */                         if ((((Boolean)request.getAttribute("ShowHostPerformance")).booleanValue()) && (m == 1)) {
/* 4862 */                           out.write("\t\n\t<td width=\"49%\" valign=\"top\">  ");
/* 4863 */                           out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 4864 */                           DialChartSupport dialGraph = null;
/* 4865 */                           dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 4866 */                           if (dialGraph == null) {
/* 4867 */                             dialGraph = new DialChartSupport();
/* 4868 */                             _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                           }
/* 4870 */                           out.write(10);
/*      */                           
/*      */                           try
/*      */                           {
/* 4874 */                             String hostos = (String)systeminfo.get("HOSTOS");
/* 4875 */                             String hostname = (String)systeminfo.get("HOSTNAME");
/* 4876 */                             String hostid = (String)systeminfo.get("host_resid");
/* 4877 */                             boolean isConf = false;
/* 4878 */                             if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 4879 */                               isConf = true;
/*      */                             }
/* 4881 */                             RepairTables rt = new RepairTables();
/* 4882 */                             Properties property = new Properties();
/* 4883 */                             if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                             {
/* 4885 */                               property = RepairTables.getValuesForHost(hostname, hostos);
/* 4886 */                               if ((property != null) && (property.size() > 0))
/*      */                               {
/* 4888 */                                 String cpuid = property.getProperty("cpuid");
/* 4889 */                                 String memid = property.getProperty("memid");
/* 4890 */                                 String diskid = property.getProperty("diskid");
/* 4891 */                                 String cpuvalue = property.getProperty("CPU Utilization");
/* 4892 */                                 String memvalue = property.getProperty("Memory Utilization");
/* 4893 */                                 String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 4894 */                                 String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 4895 */                                 String diskvalue = property.getProperty("Disk Utilization");
/* 4896 */                                 String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                                 
/* 4898 */                                 if (!isConf) {
/* 4899 */                                   out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 4900 */                                   out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 4901 */                                   out.write(45);
/* 4902 */                                   if (systeminfo.get("host_resid") != null) {
/* 4903 */                                     out.write("<a href=\"showresource.do?resourceid=");
/* 4904 */                                     out.print(hostid);
/* 4905 */                                     out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 4906 */                                     out.print(hostname);
/* 4907 */                                     out.write("</a>");
/* 4908 */                                   } else { out.println(hostname); }
/* 4909 */                                   out.write("</td>\t");
/* 4910 */                                   out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 4911 */                                   out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 4912 */                                   out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                                   
/*      */ 
/* 4915 */                                   if (cpuvalue != null)
/*      */                                   {
/*      */ 
/* 4918 */                                     dialGraph.setValue(Long.parseLong(cpuvalue));
/* 4919 */                                     out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 4920 */                                     out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4921 */                                     out.write(45);
/* 4922 */                                     out.print(cpuvalue);
/* 4923 */                                     out.write(" %'>\n\n");
/*      */                                     
/* 4925 */                                     DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 4926 */                                     _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 4927 */                                     _jspx_th_awolf_005fdialchart_005f0.setParent(null);
/*      */                                     
/* 4929 */                                     _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                     
/* 4931 */                                     _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                     
/* 4933 */                                     _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                     
/* 4935 */                                     _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                     
/* 4937 */                                     _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                     
/* 4939 */                                     _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                     
/* 4941 */                                     _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                     
/* 4943 */                                     _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                     
/* 4945 */                                     _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                                     
/* 4947 */                                     _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 4948 */                                     int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 4949 */                                     if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 4950 */                                       if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4951 */                                         out = _jspx_page_context.pushBody();
/* 4952 */                                         _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 4953 */                                         _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 4956 */                                         out.write(10);
/* 4957 */                                         int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 4958 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 4961 */                                       if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4962 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 4965 */                                     if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 4966 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                     }
/*      */                                     
/* 4969 */                                     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 4970 */                                     out.write("\n         </td>\n            ");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 4974 */                                     out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 4975 */                                     out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4976 */                                     out.write(32);
/* 4977 */                                     out.write(62);
/* 4978 */                                     out.write(10);
/* 4979 */                                     out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 4980 */                                     out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                                   }
/* 4982 */                                   out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 4983 */                                   if (cpuvalue != null)
/*      */                                   {
/* 4985 */                                     out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4986 */                                     out.print(hostid);
/* 4987 */                                     out.write("&attributeid=");
/* 4988 */                                     out.print(cpuid);
/* 4989 */                                     out.write("&period=-7')\" class='bodytextbold'>");
/* 4990 */                                     out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 4991 */                                     out.write(32);
/* 4992 */                                     out.write(45);
/* 4993 */                                     out.write(32);
/* 4994 */                                     out.print(cpuvalue);
/* 4995 */                                     out.write("</a> %\n");
/*      */                                   }
/* 4997 */                                   out.write("\n  </td>\n       </tr>\n       </table>");
/* 4998 */                                   out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 4999 */                                   out.write("</td>\n      <td width=\"30%\"> ");
/* 5000 */                                   out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5001 */                                   out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                                   
/* 5003 */                                   if (memvalue != null)
/*      */                                   {
/*      */ 
/* 5006 */                                     dialGraph.setValue(Long.parseLong(memvalue));
/* 5007 */                                     out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5008 */                                     out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5009 */                                     out.write(45);
/* 5010 */                                     out.print(memvalue);
/* 5011 */                                     out.write(" %' >\n\n");
/*      */                                     
/* 5013 */                                     DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5014 */                                     _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 5015 */                                     _jspx_th_awolf_005fdialchart_005f1.setParent(null);
/*      */                                     
/* 5017 */                                     _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                     
/* 5019 */                                     _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                     
/* 5021 */                                     _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                     
/* 5023 */                                     _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                     
/* 5025 */                                     _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                     
/* 5027 */                                     _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                     
/* 5029 */                                     _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                     
/* 5031 */                                     _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                     
/* 5033 */                                     _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                                     
/* 5035 */                                     _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 5036 */                                     int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 5037 */                                     if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 5038 */                                       if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5039 */                                         out = _jspx_page_context.pushBody();
/* 5040 */                                         _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 5041 */                                         _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 5044 */                                         out.write(32);
/* 5045 */                                         int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 5046 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5049 */                                       if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5050 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5053 */                                     if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 5054 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                     }
/*      */                                     
/* 5057 */                                     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 5058 */                                     out.write(32);
/* 5059 */                                     out.write("\n            </td>\n            ");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 5063 */                                     out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5064 */                                     out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5065 */                                     out.write(" >\n\n");
/* 5066 */                                     out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5067 */                                     out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                   }
/* 5069 */                                   out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5070 */                                   if (memvalue != null)
/*      */                                   {
/* 5072 */                                     out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5073 */                                     out.print(hostid);
/* 5074 */                                     out.write("&attributeid=");
/* 5075 */                                     out.print(memid);
/* 5076 */                                     out.write("&period=-7')\" class='bodytextbold'>");
/* 5077 */                                     out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5078 */                                     out.write(45);
/* 5079 */                                     out.print(memvalue);
/* 5080 */                                     out.write("</a> %\n  ");
/*      */                                   }
/* 5082 */                                   out.write("\n  </td>\n       </tr>\n    </table>");
/* 5083 */                                   out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5084 */                                   out.write("</td>\n      <td width=\"30%\">");
/* 5085 */                                   out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5086 */                                   out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                                   
/*      */ 
/* 5089 */                                   if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                   {
/*      */ 
/*      */ 
/* 5093 */                                     dialGraph.setValue(Long.parseLong(diskvalue));
/* 5094 */                                     out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5095 */                                     out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 5096 */                                     out.write(45);
/* 5097 */                                     out.print(diskvalue);
/* 5098 */                                     out.write("%' >\n");
/*      */                                     
/* 5100 */                                     DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5101 */                                     _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 5102 */                                     _jspx_th_awolf_005fdialchart_005f2.setParent(null);
/*      */                                     
/* 5104 */                                     _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                     
/* 5106 */                                     _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                     
/* 5108 */                                     _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                     
/* 5110 */                                     _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                     
/* 5112 */                                     _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                     
/* 5114 */                                     _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                     
/* 5116 */                                     _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                     
/* 5118 */                                     _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                     
/* 5120 */                                     _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                                     
/* 5122 */                                     _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 5123 */                                     int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 5124 */                                     if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 5125 */                                       if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5126 */                                         out = _jspx_page_context.pushBody();
/* 5127 */                                         _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 5128 */                                         _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 5131 */                                         out.write(32);
/* 5132 */                                         int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 5133 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5136 */                                       if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5137 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5140 */                                     if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 5141 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                     }
/*      */                                     
/* 5144 */                                     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 5145 */                                     out.write(32);
/* 5146 */                                     out.write(32);
/* 5147 */                                     out.write("\n    </td>\n            ");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 5151 */                                     out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5152 */                                     out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5153 */                                     out.write(32);
/* 5154 */                                     out.write(62);
/* 5155 */                                     out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5156 */                                     out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                   }
/* 5158 */                                   out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 5159 */                                   if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                   {
/* 5161 */                                     out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5162 */                                     out.print(hostid);
/* 5163 */                                     out.write("&attributeid=");
/* 5164 */                                     out.print(diskid);
/* 5165 */                                     out.write("&period=-7')\" class='bodytextbold'>");
/* 5166 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5167 */                                     out.write(45);
/* 5168 */                                     out.print(diskvalue);
/* 5169 */                                     out.write("</a> %\n     ");
/*      */                                   }
/* 5171 */                                   out.write("\n  </td>\n  </tr>\n</table>");
/* 5172 */                                   out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5173 */                                   out.write("</td></tr></table>\n\n");
/*      */                                 } else {
/* 5175 */                                   out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 5176 */                                   out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 5177 */                                   out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 5178 */                                   out.print(systeminfo.get("host_resid"));
/* 5179 */                                   out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5180 */                                   out.print(hostname);
/* 5181 */                                   out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 5182 */                                   if (cpuvalue != null)
/*      */                                   {
/*      */ 
/* 5185 */                                     dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5186 */                                     out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                                     
/* 5188 */                                     DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5189 */                                     _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 5190 */                                     _jspx_th_awolf_005fdialchart_005f3.setParent(null);
/*      */                                     
/* 5192 */                                     _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                                     
/* 5194 */                                     _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                                     
/* 5196 */                                     _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                                     
/* 5198 */                                     _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                                     
/* 5200 */                                     _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                                     
/* 5202 */                                     _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                                     
/* 5204 */                                     _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                                     
/* 5206 */                                     _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                                     
/* 5208 */                                     _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                                     
/* 5210 */                                     _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 5211 */                                     int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 5212 */                                     if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 5213 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                                     }
/*      */                                     
/* 5216 */                                     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 5217 */                                     out.write("\n         </td>\n     ");
/*      */                                   }
/*      */                                   else {
/* 5220 */                                     out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5221 */                                     out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5222 */                                     out.write(39);
/* 5223 */                                     out.write(32);
/* 5224 */                                     out.write(62);
/* 5225 */                                     out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5226 */                                     out.write("\n \t\t</td>\n\t\t");
/*      */                                   }
/* 5228 */                                   if (memvalue != null) {
/* 5229 */                                     dialGraph.setValue(Long.parseLong(memvalue));
/* 5230 */                                     out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                     
/* 5232 */                                     DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5233 */                                     _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 5234 */                                     _jspx_th_awolf_005fdialchart_005f4.setParent(null);
/*      */                                     
/* 5236 */                                     _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                                     
/* 5238 */                                     _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                                     
/* 5240 */                                     _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                                     
/* 5242 */                                     _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                                     
/* 5244 */                                     _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                                     
/* 5246 */                                     _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                                     
/* 5248 */                                     _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                                     
/* 5250 */                                     _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                                     
/* 5252 */                                     _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                                     
/* 5254 */                                     _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 5255 */                                     int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 5256 */                                     if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 5257 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                                     }
/*      */                                     
/* 5260 */                                     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 5261 */                                     out.write("\n            </td>\n         ");
/*      */                                   }
/*      */                                   else {
/* 5264 */                                     out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5265 */                                     out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5266 */                                     out.write(39);
/* 5267 */                                     out.write(32);
/* 5268 */                                     out.write(62);
/* 5269 */                                     out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5270 */                                     out.write("\n \t\t</td>\n\t\t");
/*      */                                   }
/* 5272 */                                   if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5273 */                                     dialGraph.setValue(Long.parseLong(diskvalue));
/* 5274 */                                     out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                     
/* 5276 */                                     DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5277 */                                     _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 5278 */                                     _jspx_th_awolf_005fdialchart_005f5.setParent(null);
/*      */                                     
/* 5280 */                                     _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                                     
/* 5282 */                                     _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                                     
/* 5284 */                                     _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                                     
/* 5286 */                                     _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                                     
/* 5288 */                                     _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                                     
/* 5290 */                                     _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                                     
/* 5292 */                                     _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                                     
/* 5294 */                                     _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                                     
/* 5296 */                                     _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                                     
/* 5298 */                                     _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 5299 */                                     int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 5300 */                                     if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 5301 */                                       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                                     }
/*      */                                     
/* 5304 */                                     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 5305 */                                     out.write(32);
/* 5306 */                                     out.write("\n\t          </td>\n\t  ");
/*      */                                   }
/*      */                                   else {
/* 5309 */                                     out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5310 */                                     out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5311 */                                     out.write(39);
/* 5312 */                                     out.write(32);
/* 5313 */                                     out.write(62);
/* 5314 */                                     out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5315 */                                     out.write("\n \t\t</td>\n\t\t");
/*      */                                   }
/* 5317 */                                   out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5318 */                                   out.print(hostid);
/* 5319 */                                   out.write("&attributeid=");
/* 5320 */                                   out.print(cpuid);
/* 5321 */                                   out.write("&period=-7')\" class='tooltip'>");
/* 5322 */                                   out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5323 */                                   out.write(32);
/* 5324 */                                   out.write(45);
/* 5325 */                                   out.write(32);
/* 5326 */                                   if (cpuvalue != null) {
/* 5327 */                                     out.print(cpuvalue);
/*      */                                   }
/* 5329 */                                   out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5330 */                                   out.print(hostid);
/* 5331 */                                   out.write("&attributeid=");
/* 5332 */                                   out.print(memid);
/* 5333 */                                   out.write("&period=-7')\" class='tooltip'>");
/* 5334 */                                   out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5335 */                                   out.write(45);
/* 5336 */                                   if (memvalue != null) {
/* 5337 */                                     out.print(memvalue);
/*      */                                   }
/* 5339 */                                   out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5340 */                                   out.print(hostid);
/* 5341 */                                   out.write("&attributeid=");
/* 5342 */                                   out.print(diskid);
/* 5343 */                                   out.write("&period=-7')\" class='tooltip'>");
/* 5344 */                                   out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5345 */                                   out.write(45);
/* 5346 */                                   if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5347 */                                     out.print(diskvalue);
/*      */                                   }
/* 5349 */                                   out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                                 }
/* 5351 */                                 out.write(10);
/* 5352 */                                 out.write(10);
/*      */                               }
/*      */                               
/*      */                             }
/*      */                           }
/*      */                           catch (Exception e)
/*      */                           {
/* 5359 */                             e.printStackTrace();
/*      */                           }
/* 5361 */                           out.write(10);
/* 5362 */                           out.write("</td>\n\t<td width=\"1%\">&nbsp;</td>\n\t<td width=\"49%\" valign=\"top\">\t<div id=\"availabilitydiv\"></div>\t<div id=\"performancediv\"></div>\t</td>\n");
/*      */                         } else {
/* 5364 */                           out.write("\n\t<td width=\"49%\" valign=\"top\">\t<div id=\"availabilitydiv\"></div></td>\n\t<td width=\"1%\">&nbsp;</td>\n\t<td width=\"49%\" valign=\"top\"><div id=\"performancediv\"></div>\t</td>\n");
/*      */                         }
/* 5366 */                         out.write("\n\t\t</tr>\n\t</table>\n</div>\n");
/* 5367 */                       } catch (Exception exc) { exc.printStackTrace(); }
/* 5368 */                       out.write("\n\t<div id=\"monitorInfoDiv\" style=\"display:none\"></div>\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 5369 */                       JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 5370 */                       out.write("</td></tr></table>\n");
/*      */                       
/* 5372 */                       String caption = "";
/* 5373 */                       String gtype = "1";
/* 5374 */                       String width1 = "99";
/* 5375 */                       String imgwidth = "450";
/* 5376 */                       String imght = "170";
/* 5377 */                       String category = "";
/* 5378 */                       String tableId = "";
/* 5379 */                       groupData = (HashMap)request.getAttribute("groupData");
/* 5380 */                       pageContext.setAttribute("groupData", groupData);
/* 5381 */                       Cookie[] cookies = request.getCookies();
/* 5382 */                       if (cookies != null)
/*      */                       {
/* 5384 */                         if (cookies.length > 0)
/*      */                         {
/* 5386 */                           for (int i = 0; i < cookies.length; ???++) {
/* 5387 */                             if (cookies[i].getName().equals("ScreenWidth")) {
/*      */                               try {
/* 5389 */                                 if ((cookies[i].getValue() != null) && (!cookies[i].getValue().equals("null")) && (!cookies[i].getValue().trim().equals(""))) {
/* 5390 */                                   scrWidth = Integer.parseInt(cookies[i].getValue());
/*      */                                 }
/*      */                               } catch (Exception e) {
/* 5393 */                                 e.printStackTrace();
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/* 5401 */                       out.write("\n<form name=\"tabsForm\" id=\"tabsForm\" style=\"display:inline\">\n\t<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 5402 */                       out.print(resourceName);
/* 5403 */                       out.write("\"/>\n \t<input type=\"hidden\" name=\"type\" value=\"");
/* 5404 */                       out.print(resourcetype);
/* 5405 */                       out.write("\"/>\n \t<input type=\"hidden\" name=\"original_type\" value=\"");
/* 5406 */                       out.print(original_type1);
/* 5407 */                       out.write("\"/>\n \t<input type=\"hidden\" name=\"baseid\" value=\"");
/* 5408 */                       out.print(baseid1);
/* 5409 */                       out.write("\"/>\n \t<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 5410 */                       out.print(resID);
/* 5411 */                       out.write("\"/>\n \t<input type=\"hidden\" name=\"monitorname\" value=\"");
/* 5412 */                       out.print(URLEncoder.encode((String)request.getAttribute("monitorname")));
/* 5413 */                       out.write("\"/>\n \t<input type=\"hidden\" name=\"moname\" value=\"");
/* 5414 */                       out.print(moname);
/* 5415 */                       out.write("\"/>\n \t<input type=\"hidden\" name=\"name\" value=\"\"/>\n \t<input type=\"hidden\" name=\"tabId\" value=\"");
/* 5416 */                       out.print(m);
/* 5417 */                       out.write("\"/>\n \t<input type=\"hidden\" name=\"method\" value=\"\"/>\n \t<input type=\"hidden\" name=\"granularity\" value=\"PolledData\"/>\n \t<input type=\"hidden\" name=\"TimeUnit\" value=\"0\"/>\n \t<input type=\"hidden\" name=\"customDate\" value=\"\"/>\n \t<input type=\"hidden\" name=\"weekUnit\" value=\"\"/>\n \t<input type=\"hidden\" name=\"monthUnit\" value=\"\"/>\n \t<input type=\"hidden\" name=\"tableid\" value=\"\"/>\n \t<input type=\"hidden\" name=\"tableName\" value=\"\"/>\n\t<input type=\"hidden\" name=\"sqlmanid\" value=\"\"/>\n\t<input type=\"hidden\" name=\"scriptid\" value=\"");
/* 5418 */                       out.print(resID);
/* 5419 */                       out.write("\"/>\n\t<input type=\"hidden\" name=\"rowid\" value=\"\"/>\n\t<input type=\"hidden\" name=\"fromConfDetails\" value=\"true\"/>\n\t<input type=\"hidden\" name=\"collectionTime\" value=\"");
/* 5420 */                       out.print((String)request.getAttribute("maxcollectiontime"));
/* 5421 */                       out.write("\"/> \n</form>\n");
/* 5422 */                       if (tabConfiguration != null) {
/* 5423 */                         out.write("\n<div id=\"");
/* 5424 */                         out.print("Group_tab" + m);
/* 5425 */                         out.write("\" style=\"display:none;\">\n\t");
/*      */                         
/* 5427 */                         actionPath = "/showCustom.do?method=showDataforConfs&resourceid=" + resID + "&resourcename=" + resourceName + "&type=" + resourcetype + "&original_type=" + original_type1 + "&moname=" + moname + "&tabId=" + (Integer)request.getAttribute("tabId") + "&baseid=" + baseid1 + "&monitorname=" + URLEncoder.encode((String)request.getAttribute("monitorname"));
/* 5428 */                         request.setAttribute("actionPath", actionPath);
/*      */                         
/* 5430 */                         out.write(10);
/*      */                       } else {
/* 5432 */                         out.write("\n<div id=\"groupsForMonitor\" style=\"display:block;\">\n");
/*      */                       }
/* 5434 */                       out.write("\n<table width=\"100%\" border=\"0\"  cellspacing=\"0\" cellpadding=\"0\" >\n");
/* 5435 */                       if ((tabConfiguration != null) && (m > 0)) {
/* 5436 */                         groupList = (ArrayList)groupData.get("Group_tab" + m);
/*      */                       }
/* 5438 */                       for (int grpct = 0; grpct < groupList.size(); ???++)
/*      */                       {
/* 5440 */                         groupDetails = (HashMap)groupList.get(grpct);
/* 5441 */                         pageContext.setAttribute("groupDetails", groupDetails);
/* 5442 */                         category = (String)groupDetails.get("CATEGORY");
/* 5443 */                         boolean showGroup = true;
/* 5444 */                         if ((groupDetails.get("RELIED-ON-ARG") != null) && (groupDetails.get("RELIED-ON-VALUE") != null)) {
/* 5445 */                           showGroup = false;
/* 5446 */                           String reliedOnArg = (String)groupDetails.get("RELIED-ON-ARG");
/* 5447 */                           String reliedOnValue = (String)groupDetails.get("RELIED-ON-VALUE");
/* 5448 */                           if ((!reliedargsValues.isEmpty()) && 
/* 5449 */                             (reliedargsValues.getProperty(reliedOnArg.toUpperCase()).equalsIgnoreCase(reliedOnValue))) {
/* 5450 */                             showGroup = true;
/*      */                           }
/*      */                         }
/*      */                         
/* 5454 */                         if (showGroup)
/*      */                         {
/*      */ 
/* 5457 */                           if ((category.equals("UIGROUP")) || (category.equals("UIGRAPH")) || (category.equals("UIINCLUDE"))) {
/* 5458 */                             int noOfCols = 3;
/* 5459 */                             if (((String)groupDetails.get("FULLWIDTH")).equals("2")) {
/* 5460 */                               width1 = "49";
/* 5461 */                               count++;
/* 5462 */                               imgwidth = scrWidth <= 1280 ? "450" : "600";
/* 5463 */                               imght = "350";
/* 5464 */                             } else if (((String)groupDetails.get("FULLWIDTH")).equals("1")) {
/* 5465 */                               width1 = "99";
/* 5466 */                               count += 2;
/* 5467 */                               imgwidth = "900";
/* 5468 */                               imght = "350";
/* 5469 */                               noOfCols = 5;
/*      */                             }
/* 5471 */                             if ((count % 2 == 0) || (width1.equals("99"))) {
/* 5472 */                               out.write("\n\t\t<tr>\n\t\t<td  width=\"100%\">\n\t\t<table  width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t<tr>\n\t\t");
/*      */                             }
/* 5474 */                             if (category.equals("UIGROUP")) {
/* 5475 */                               caption = (String)groupDetails.get("CAPTION");
/* 5476 */                               gtype = (String)groupDetails.get("GRAPHTYPE");
/* 5477 */                               attributesList = (ArrayList)groupDetails.get("ATTRIBUTES");
/* 5478 */                               pageContext.setAttribute("attributesList", attributesList);
/* 5479 */                               String attids = (String)groupDetails.get("G-ATTRIBUTES");
/* 5480 */                               String attributeid = (String)groupDetails.get("COMMA-SEPARATED-ATTRIBUTES");
/* 5481 */                               StringBuffer archivedIds = new StringBuffer();
/* 5482 */                               String attidlist = (String)groupDetails.get("THRESHOLD-ATTRIBUTES");
/* 5483 */                               ArrayList attribsInGraphs = new ArrayList();
/*      */                               
/* 5485 */                               out.write(10);
/* 5486 */                               out.write(9);
/*      */                               
/* 5488 */                               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 5489 */                               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 5490 */                               _jspx_th_logic_005fiterate_005f1.setParent(null);
/*      */                               
/* 5492 */                               _jspx_th_logic_005fiterate_005f1.setName("attributesList");
/*      */                               
/* 5494 */                               _jspx_th_logic_005fiterate_005f1.setId("attributes");
/*      */                               
/* 5496 */                               _jspx_th_logic_005fiterate_005f1.setIndexId("n");
/* 5497 */                               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 5498 */                               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 5499 */                                 Object attributes = null;
/* 5500 */                                 Integer n = null;
/* 5501 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5502 */                                   out = _jspx_page_context.pushBody();
/* 5503 */                                   _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 5504 */                                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                 }
/* 5506 */                                 attributes = _jspx_page_context.findAttribute("attributes");
/* 5507 */                                 n = (Integer)_jspx_page_context.findAttribute("n");
/*      */                                 for (;;) {
/* 5509 */                                   out.write(10);
/* 5510 */                                   attributeDetails = (HashMap)attributesList.get(n.intValue());
/* 5511 */                                   String attid = attributeDetails.get("ATTRIBUTEID") != null ? (String)attributeDetails.get("ATTRIBUTEID") : (String)attributeDetails.get("ID");
/* 5512 */                                   if ((((String)attributeDetails.get("ATYPE")).equals("0")) && ((configAttList == null) || ((configAttList != null) && (!configAttList.contains(attid))))) {
/* 5513 */                                     if (archivedIds.length() == 0) archivedIds.append(attid); else {
/* 5514 */                                       archivedIds.append("," + attid);
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 5518 */                                   out.write(10);
/* 5519 */                                   out.write(9);
/* 5520 */                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 5521 */                                   attributes = _jspx_page_context.findAttribute("attributes");
/* 5522 */                                   n = (Integer)_jspx_page_context.findAttribute("n");
/* 5523 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 5526 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5527 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 5530 */                               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 5531 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                               }
/*      */                               
/* 5534 */                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 5535 */                               out.write("\t\t\t\n\t\t<td width=\"");
/* 5536 */                               out.print(width1);
/* 5537 */                               out.write("%\"  valign=\"top\" >\n\t\t<table id=\"Table_");
/* 5538 */                               out.print(grpct);
/* 5539 */                               out.write("\" onMouseOver=\"ShowPicture('configureIcons_");
/* 5540 */                               out.print(grpct);
/* 5541 */                               out.write("',1,'");
/* 5542 */                               out.print(grpct);
/* 5543 */                               out.write("')\" onMouseOut=\"ShowPicture('configureIcons_");
/* 5544 */                               out.print(grpct);
/* 5545 */                               out.write("',0,'");
/* 5546 */                               out.print(grpct);
/* 5547 */                               out.write("')\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" class=\"conf-mon-table\">\n\t\t");
/* 5548 */                               if (gtype.equals("0")) {
/* 5549 */                                 out.write("\t\n\t\t<tr height=\"35\"><td width=\"100%\"  colspan=\"2\">\n\t\t<table border=\"0\"  class=\"conf-top-summary-tphead\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tr><td class=\"conf-mon-txt\">");
/* 5550 */                                 out.print(FormatUtil.getString(caption).toUpperCase());
/* 5551 */                                 out.write("</td>\n\t\t<td align=\"right\" valign=\"top\">\n\t\t<div id=\"configureIcons_");
/* 5552 */                                 out.print(grpct);
/* 5553 */                                 out.write("\" class=\"ShowHideConfigIconsStyle\">");
/* 5554 */                                 if (attidlist.length() > 0) {
/* 5555 */                                   out.write(10);
/* 5556 */                                   out.write(9);
/* 5557 */                                   out.write(9);
/*      */                                   
/* 5559 */                                   PresentTag _jspx_th_logic_005fpresent_005f17 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5560 */                                   _jspx_th_logic_005fpresent_005f17.setPageContext(_jspx_page_context);
/* 5561 */                                   _jspx_th_logic_005fpresent_005f17.setParent(null);
/*      */                                   
/* 5563 */                                   _jspx_th_logic_005fpresent_005f17.setRole("ADMIN,OPERATOR");
/* 5564 */                                   int _jspx_eval_logic_005fpresent_005f17 = _jspx_th_logic_005fpresent_005f17.doStartTag();
/* 5565 */                                   if (_jspx_eval_logic_005fpresent_005f17 != 0) {
/*      */                                     for (;;) {
/* 5567 */                                       out.write("<a class=\"conf-hover-buttons white\" href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5568 */                                       out.print(resID);
/* 5569 */                                       out.write("&attributeIDs=");
/* 5570 */                                       out.print(attidlist);
/* 5571 */                                       out.write("&attributeToSelect=");
/* 5572 */                                       out.print(new StringTokenizer(attidlist, ",").nextToken());
/* 5573 */                                       out.write("&redirectto=");
/* 5574 */                                       out.print(encodeurl);
/* 5575 */                                       out.write("\"><img src=\"../images/alarms-conf.png\" style=\"margin: 2px 0px 0px 3px;\" alt=\"Down\"  border=\"0\" title=\"");
/* 5576 */                                       out.print(FormatUtil.getString("am.webclient.common.configureAlarms.tooltip.text"));
/* 5577 */                                       out.write("\">&nbsp;<span class=\"conf-hover-buttons-text\">");
/* 5578 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.configurealert"));
/* 5579 */                                       out.write("</span></a>");
/* 5580 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f17.doAfterBody();
/* 5581 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5585 */                                   if (_jspx_th_logic_005fpresent_005f17.doEndTag() == 5) {
/* 5586 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f17); return;
/*      */                                   }
/*      */                                   
/* 5589 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f17);
/* 5590 */                                   out.write(10);
/* 5591 */                                   out.write(9);
/* 5592 */                                   out.write(9);
/*      */                                 }
/* 5594 */                                 out.write("\t\t\n\t\t");
/* 5595 */                                 if (archivedIds.length() > 0) {
/* 5596 */                                   out.write("\n\t\t\t<a class=\"conf-hover-buttons white conf-bottonAlign\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindowWithHeightWidth('");
/* 5597 */                                   out.print((String)request.getAttribute("reportURL"));
/* 5598 */                                   out.write("&todaytime=");
/* 5599 */                                   out.print(System.currentTimeMillis());
/* 5600 */                                   out.write("&attributeid=");
/* 5601 */                                   out.print(new StringTokenizer(archivedIds.toString(), ",").nextToken());
/* 5602 */                                   out.write("',900,550)\"><img src=\"../images/icon-anamoly-responsetime.gif\" width=\"16\" align=\"absmiddle\" height=\"10\" hspace=\"0\" vspace=\"0\" border=\"0\"  style=\"margin: 0px 0px 1px 3px;\" title=\"");
/* 5603 */                                   out.print(seven_days_text);
/* 5604 */                                   out.write("\">&nbsp;</a>\n\t\t");
/*      */                                 }
/* 5606 */                                 out.write("\n\t\t</div>\n\t\t</td>\n\t\t</tr></table></td></tr>\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t");
/*      */                               } else {
/* 5608 */                                 out.write("\t\t\t\t\t\t\t\t\t\n\t\t<tr height=\"20\"><td colspan=\"2\" align=\"right\">\n\t\t<div id=\"configureIcons_");
/* 5609 */                                 out.print(grpct);
/* 5610 */                                 out.write("\" class=\"ShowHideConfigIconsStyle\">");
/* 5611 */                                 if (attidlist.length() > 0) {
/* 5612 */                                   out.write(10);
/* 5613 */                                   out.write(9);
/* 5614 */                                   out.write(9);
/*      */                                   
/* 5616 */                                   PresentTag _jspx_th_logic_005fpresent_005f18 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5617 */                                   _jspx_th_logic_005fpresent_005f18.setPageContext(_jspx_page_context);
/* 5618 */                                   _jspx_th_logic_005fpresent_005f18.setParent(null);
/*      */                                   
/* 5620 */                                   _jspx_th_logic_005fpresent_005f18.setRole("ADMIN,OPERATOR");
/* 5621 */                                   int _jspx_eval_logic_005fpresent_005f18 = _jspx_th_logic_005fpresent_005f18.doStartTag();
/* 5622 */                                   if (_jspx_eval_logic_005fpresent_005f18 != 0) {
/*      */                                     for (;;) {
/* 5624 */                                       out.write("\n\t\t\t<a class=\"conf-hover-buttons white\" href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5625 */                                       out.print(resID);
/* 5626 */                                       out.write("&attributeIDs=");
/* 5627 */                                       out.print(attidlist);
/* 5628 */                                       out.write("&attributeToSelect=");
/* 5629 */                                       out.print(new StringTokenizer(attidlist, ",").nextToken());
/* 5630 */                                       out.write("&redirectto=");
/* 5631 */                                       out.print(encodeurl);
/* 5632 */                                       out.write("\"><img src=\"../images/alarms-conf.png\" style=\"margin: 2px 0px 0px 3px;\" alt=\"Down\"  border=\"0\" title=\"");
/* 5633 */                                       out.print(FormatUtil.getString("am.webclient.common.configureAlarms.tooltip.text"));
/* 5634 */                                       out.write("\">&nbsp;<span class=\"conf-hover-buttons-text\">");
/* 5635 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.configurealert"));
/* 5636 */                                       out.write("</span></a>\t\t");
/* 5637 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f18.doAfterBody();
/* 5638 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5642 */                                   if (_jspx_th_logic_005fpresent_005f18.doEndTag() == 5) {
/* 5643 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f18); return;
/*      */                                   }
/*      */                                   
/* 5646 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f18);
/*      */                                 }
/* 5648 */                                 out.write(10);
/* 5649 */                                 out.write(9);
/* 5650 */                                 out.write(9);
/* 5651 */                                 if (archivedIds.length() > 0) {
/* 5652 */                                   out.write("\n\t\t\t<a class=\"conf-hover-buttons white conf-bottonAlign\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindowWithHeightWidth('");
/* 5653 */                                   out.print((String)request.getAttribute("reportURL"));
/* 5654 */                                   out.write("&todaytime=");
/* 5655 */                                   out.print(System.currentTimeMillis());
/* 5656 */                                   out.write("&attributeid=");
/* 5657 */                                   out.print(new StringTokenizer(archivedIds.toString(), ",").nextToken());
/* 5658 */                                   out.write("',900,550)\"><img src=\"../images/icon-anamoly-responsetime.gif\" width=\"16\" align=\"absmiddle\" height=\"10\" hspace=\"0\" vspace=\"0\" border=\"0\"  style=\"margin: 0px 0px 1px 3px;\" title=\"");
/* 5659 */                                   out.print(seven_days_text);
/* 5660 */                                   out.write("\">&nbsp;</a>\n\t\t");
/*      */                                 }
/* 5662 */                                 out.write("\n\t\t</div>\n\t\t</td></tr>");
/*      */                               }
/* 5664 */                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\" class=\"bodytext\" colspan=\"4\" valign=\"top\" >\n");
/* 5665 */                               if ((gtype.equals("1")) || (gtype.equals("3"))) {
/* 5666 */                                 confgraph.setGraphType(gtype.equals("1") ? "LINE" : "PIE");
/* 5667 */                                 confgraph.setGraphForData("ATTRIBUTES");
/* 5668 */                                 confgraph.setResourceType(resourcetype);
/* 5669 */                                 confgraph.setGraphProperteis(groupDetails);
/* 5670 */                                 confgraph.setGraphValue(request.getAttribute("graphValues") != null ? (HashMap)request.getAttribute("graphValues") : null);
/* 5671 */                                 confgraph.setAttributeValue(request.getAttribute("attributeValues") != null ? (HashMap)request.getAttribute("attributeValues") : null);
/*      */                               }
/* 5673 */                               if (gtype.equals("1")) {
/* 5674 */                                 if (attids.indexOf(",") != -1) {
/* 5675 */                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                   
/* 5677 */                                   TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fchartTitle_005fnobody.get(TimeChart.class);
/* 5678 */                                   _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 5679 */                                   _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*      */                                   
/* 5681 */                                   _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("confgraph");
/*      */                                   
/* 5683 */                                   _jspx_th_awolf_005ftimechart_005f0.setWidth(imgwidth);
/*      */                                   
/* 5685 */                                   _jspx_th_awolf_005ftimechart_005f0.setHeight(imght);
/*      */                                   
/* 5687 */                                   _jspx_th_awolf_005ftimechart_005f0.setChartTitle(FormatUtil.getString(caption));
/*      */                                   
/* 5689 */                                   _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */                                   
/* 5691 */                                   _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*      */                                   
/* 5693 */                                   _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString((String)groupDetails.get("GRAPHUNIT")));
/*      */                                   
/* 5695 */                                   _jspx_th_awolf_005ftimechart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5696 */                                   int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 5697 */                                   if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 5698 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fchartTitle_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                                   }
/*      */                                   
/* 5701 */                                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetProducer_005fchartTitle_005fnobody.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 5702 */                                   out.write(32);
/* 5703 */                                   out.write("\t\t\t\t\t \n");
/*      */                                 } else {
/* 5705 */                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                   
/* 5707 */                                   XYAreaChart _jspx_th_awolf_005fxyareachart_005f0 = (XYAreaChart)this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer_005fchartTitle_005fnobody.get(XYAreaChart.class);
/* 5708 */                                   _jspx_th_awolf_005fxyareachart_005f0.setPageContext(_jspx_page_context);
/* 5709 */                                   _jspx_th_awolf_005fxyareachart_005f0.setParent(null);
/*      */                                   
/* 5711 */                                   _jspx_th_awolf_005fxyareachart_005f0.setDataSetProducer("confgraph");
/*      */                                   
/* 5713 */                                   _jspx_th_awolf_005fxyareachart_005f0.setWidth(imgwidth);
/*      */                                   
/* 5715 */                                   _jspx_th_awolf_005fxyareachart_005f0.setHeight(imght);
/*      */                                   
/* 5717 */                                   _jspx_th_awolf_005fxyareachart_005f0.setChartTitle(FormatUtil.getString(caption));
/*      */                                   
/* 5719 */                                   _jspx_th_awolf_005fxyareachart_005f0.setLegend("true");
/*      */                                   
/* 5721 */                                   _jspx_th_awolf_005fxyareachart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.recent5alerts.columnheader.time"));
/*      */                                   
/* 5723 */                                   _jspx_th_awolf_005fxyareachart_005f0.setYaxisLabel(FormatUtil.getString((String)groupDetails.get("GRAPHUNIT")));
/*      */                                   
/* 5725 */                                   _jspx_th_awolf_005fxyareachart_005f0.setDataSetType("SubSeriesDataset");
/*      */                                   
/* 5727 */                                   _jspx_th_awolf_005fxyareachart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5728 */                                   int _jspx_eval_awolf_005fxyareachart_005f0 = _jspx_th_awolf_005fxyareachart_005f0.doStartTag();
/* 5729 */                                   if (_jspx_th_awolf_005fxyareachart_005f0.doEndTag() == 5) {
/* 5730 */                                     this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer_005fchartTitle_005fnobody.reuse(_jspx_th_awolf_005fxyareachart_005f0); return;
/*      */                                   }
/*      */                                   
/* 5733 */                                   this._005fjspx_005ftagPool_005fawolf_005fxyareachart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fnodatamessage_005flegend_005fheight_005fdataSetType_005fdataSetProducer_005fchartTitle_005fnobody.reuse(_jspx_th_awolf_005fxyareachart_005f0);
/* 5734 */                                   out.write(9);
/* 5735 */                                   out.write(10);
/*      */                                 }
/* 5737 */                               } else if (gtype.equals("3")) {
/* 5738 */                                 out.write("\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                 
/* 5740 */                                 AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005fnodatamessage_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular_005fchartTitle_005fnobody.get(AMWolf.class);
/* 5741 */                                 _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 5742 */                                 _jspx_th_awolf_005fpiechart_005f0.setParent(null);
/*      */                                 
/* 5744 */                                 _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("confgraph");
/*      */                                 
/* 5746 */                                 _jspx_th_awolf_005fpiechart_005f0.setWidth(imgwidth);
/*      */                                 
/* 5748 */                                 _jspx_th_awolf_005fpiechart_005f0.setHeight(imght);
/*      */                                 
/* 5750 */                                 _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                                 
/* 5752 */                                 _jspx_th_awolf_005fpiechart_005f0.setLegendanchor("SOUTH");
/*      */                                 
/* 5754 */                                 _jspx_th_awolf_005fpiechart_005f0.setUnits(FormatUtil.getString((String)groupDetails.get("GRAPHUNIT")));
/*      */                                 
/* 5756 */                                 _jspx_th_awolf_005fpiechart_005f0.setChartTitle(FormatUtil.getString(caption));
/*      */                                 
/* 5758 */                                 _jspx_th_awolf_005fpiechart_005f0.setCircular(true);
/*      */                                 
/* 5760 */                                 _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/*      */                                 
/* 5762 */                                 _jspx_th_awolf_005fpiechart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5763 */                                 int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 5764 */                                 if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 5765 */                                   this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005fnodatamessage_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular_005fchartTitle_005fnobody.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                                 }
/*      */                                 
/* 5768 */                                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005fnodatamessage_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular_005fchartTitle_005fnobody.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 5769 */                                 out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
/* 5770 */                               } else if ((gtype.equals("4")) || (gtype.equals("2"))) {
/* 5771 */                                 StringTokenizer st = new StringTokenizer(attids, ",");
/* 5772 */                                 int dials = st.countTokens();
/*      */                                 
/* 5774 */                                 out.write("\n\t\t<table border=\"0\" cellpadding=\"0\"  width=\"100%\" style=\"padding-bottom:15px\">\n\t\t\t<tr heigth=\"50\">\n\t\t\t\t<td align=\"center\" colspan=\"");
/* 5775 */                                 out.print(dials * 2);
/* 5776 */                                 out.write("\" class=\"conf-mon-table-heading\"> ");
/* 5777 */                                 out.print(FormatUtil.getString(caption).toUpperCase());
/* 5778 */                                 out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr heigth=\"10\"><td  colspan=\"");
/* 5779 */                                 out.print(dials * 2);
/* 5780 */                                 out.write("\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n\t\t\t<tr  id=\"showLinks_Graph_");
/* 5781 */                                 out.print(grpct);
/* 5782 */                                 out.write("\" >\n\t\t\t\t<td width=\"1%\"></td>\n");
/*      */                                 
/* 5784 */                                 if ((!attids.equals("")) || (dials != 0)) {
/* 5785 */                                   while (st.hasMoreTokens()) {
/* 5786 */                                     out.write(" \t\t\t\n\t\t\t\t\t\t\t<td width=\"");
/* 5787 */                                     out.print(98 / dials - 2);
/* 5788 */                                     out.write("%\">\n");
/* 5789 */                                     String attid = st.nextToken();
/* 5790 */                                     attribsInGraphs.add(attid);
/* 5791 */                                     String[] attDetails = DBUtil.getAttributeDetails(Integer.parseInt(attid));
/*      */                                     try
/*      */                                     {
/* 5794 */                                       String temp1 = "0";
/* 5795 */                                       long value = 0L;
/* 5796 */                                       temp1 = (String)attributeValues.get(attDetails[3].toUpperCase());
/* 5797 */                                       if ((temp1 == null) || (temp1.equals("-")) || (temp1.trim().equals("")) || (temp1.equalsIgnoreCase("null")))
/*      */                                       {
/* 5799 */                                         temp1 = "-";
/* 5800 */                                         value = -1L;
/*      */                                       } else {
/* 5802 */                                         temp1 = new StringTokenizer(temp1, ".").nextToken();
/* 5803 */                                         value = Long.parseLong(temp1.replaceAll(",", ""));
/*      */                                       }
/* 5805 */                                       dialGraph1.setValue(value);
/*      */                                       
/* 5807 */                                       out.write("            \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"100%\" align=\"center\" class=\"dashboard\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table width=\"99%\" cellspacing=\"0\" cellpadding=\"7\" border=\"0\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5808 */                                       if (gtype.equals("2")) {
/* 5809 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"110\" width=\"25%\" align=\"center\" valign=\"top\"> ");
/* 5810 */                                         JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(temp1), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(attDetails[1]), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("isConfMonitor", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()), out, true);
/* 5811 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                       } else {
/* 5813 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\"  align=\"center\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         
/* 5815 */                                         DialChart _jspx_th_awolf_005fdialchart_005f6 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005fnodatamessage_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5816 */                                         _jspx_th_awolf_005fdialchart_005f6.setPageContext(_jspx_page_context);
/* 5817 */                                         _jspx_th_awolf_005fdialchart_005f6.setParent(null);
/*      */                                         
/* 5819 */                                         _jspx_th_awolf_005fdialchart_005f6.setDataSetProducer("dialGraph1");
/*      */                                         
/* 5821 */                                         _jspx_th_awolf_005fdialchart_005f6.setWidth("150");
/*      */                                         
/* 5823 */                                         _jspx_th_awolf_005fdialchart_005f6.setHeight("148");
/*      */                                         
/* 5825 */                                         _jspx_th_awolf_005fdialchart_005f6.setLegend("false");
/*      */                                         
/* 5827 */                                         _jspx_th_awolf_005fdialchart_005f6.setXaxisLabel("");
/*      */                                         
/* 5829 */                                         _jspx_th_awolf_005fdialchart_005f6.setYaxisLabel("");
/*      */                                         
/* 5831 */                                         _jspx_th_awolf_005fdialchart_005f6.setDateFormat("HH:mm");
/*      */                                         
/* 5833 */                                         _jspx_th_awolf_005fdialchart_005f6.setResourceId(resID);
/*      */                                         
/* 5835 */                                         _jspx_th_awolf_005fdialchart_005f6.setAttributeId(attid);
/*      */                                         
/* 5837 */                                         _jspx_th_awolf_005fdialchart_005f6.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5838 */                                         int _jspx_eval_awolf_005fdialchart_005f6 = _jspx_th_awolf_005fdialchart_005f6.doStartTag();
/* 5839 */                                         if (_jspx_eval_awolf_005fdialchart_005f6 != 0) {
/* 5840 */                                           if (_jspx_eval_awolf_005fdialchart_005f6 != 1) {
/* 5841 */                                             out = _jspx_page_context.pushBody();
/* 5842 */                                             _jspx_th_awolf_005fdialchart_005f6.setBodyContent((BodyContent)out);
/* 5843 */                                             _jspx_th_awolf_005fdialchart_005f6.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5846 */                                             out.write(32);
/* 5847 */                                             out.write(32);
/* 5848 */                                             out.write(32);
/* 5849 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5850 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f6.doAfterBody();
/* 5851 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5854 */                                           if (_jspx_eval_awolf_005fdialchart_005f6 != 1) {
/* 5855 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5858 */                                         if (_jspx_th_awolf_005fdialchart_005f6.doEndTag() == 5) {
/* 5859 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005fnodatamessage_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f6); return;
/*      */                                         }
/*      */                                         
/* 5862 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005fnodatamessage_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f6);
/* 5863 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                       }
/*      */                                       
/* 5866 */                                       out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr align=\"center\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"");
/* 5867 */                                       out.print(98 / dials - 2);
/* 5868 */                                       out.write("%\" height=\"25\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void();\" class=\"tooltip\" onClick=\"fnOpenNewWindowWithHeightWidth('../showHistoryData.do?method=getData&reporttype=html&resourceid=");
/* 5869 */                                       out.print(resID);
/* 5870 */                                       out.write("&childid=null&resourcename=");
/* 5871 */                                       out.print((String)request.getAttribute("monitorname"));
/* 5872 */                                       out.write("&attributeid=");
/* 5873 */                                       out.print(attid);
/* 5874 */                                       out.write("&period=20&businessPeriod=oni&todaytime=");
/* 5875 */                                       out.print(System.currentTimeMillis());
/* 5876 */                                       out.write("',900,550)\"  style=\"cursor:pointer\">\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5877 */                                       out.print(FormatUtil.getString(attDetails[1]));
/* 5878 */                                       out.write(" - <!--No I18N--><b>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5879 */                                       if (!temp1.equals("-")) {
/* 5880 */                                         out.println(FormatUtil.formatNumber(value) + "%");
/*      */                                       }
/*      */                                       else {
/* 5883 */                                         out.println("%");
/*      */                                       }
/*      */                                       
/* 5886 */                                       out.write("</b>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n");
/* 5887 */                                     } catch (Exception ex) { System.out.println("Exception :" + ex.getMessage()); }
/* 5888 */                                     out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n");
/* 5889 */                                     if (st.hasMoreTokens()) {
/* 5890 */                                       out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td  width=\"2%\"></td>\n");
/*      */                                     } else {
/* 5892 */                                       out.write("                             \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td  width=\"1%\"></td>\n");
/*      */                                     }
/* 5894 */                                     out.write(10);
/*      */                                   }
/*      */                                 }
/* 5897 */                                 out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n");
/*      */                               }
/* 5899 */                               out.write("\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t</tr>\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t<tr height=\"10\"><td><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                               
/* 5901 */                               StringTokenizer st = new StringTokenizer(attributeid, ",");
/* 5902 */                               boolean showattribTable = true;
/* 5903 */                               if (gtype.equals("0")) {
/* 5904 */                                 out.write("\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"45%\" align=\"left\" class=\"monitorinfoodd-conf\"><b>");
/* 5905 */                                 out.print(FormatUtil.getString("Name"));
/* 5906 */                                 out.write("</b></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"45%\" align=\"left\" class=\"monitorinfoodd-conf\"  ><b>");
/* 5907 */                                 out.print(FormatUtil.getString("Value"));
/* 5908 */                                 out.write("</b></td>\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                               } else {
/* 5910 */                                 out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t<tr height=\"20\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t<td id=\"showLinks_");
/* 5911 */                                 out.print(grpct);
/* 5912 */                                 out.write("\" align=\"center\" style=\"line-height:20px;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table width=\"100%\"><tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td class=\"");
/* 5913 */                                 out.print(noOfCols == 5 ? "confOneColUIDatatd" : "confTwoColDatatd");
/* 5914 */                                 out.write("\">\t<img src=\"/images/spacer.gif\"/></td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                               }
/* 5916 */                               int alterRow = 0;
/* 5917 */                               while (st.hasMoreTokens())
/*      */                               {
/* 5919 */                                 String value = "-";
/* 5920 */                                 ???++;
/* 5921 */                                 String attid = st.nextToken();
/* 5922 */                                 String[] attDetails = DBUtil.getAttributeDetails(Integer.parseInt(attid));
/* 5923 */                                 boolean escape = false;
/* 5924 */                                 if ((conf.getAttIdVsEscapeXMLValues(attid) != null) && (conf.getAttIdVsEscapeXMLValues(attid).trim().equalsIgnoreCase("NO"))) {
/* 5925 */                                   escape = true;
/*      */                                 }
/* 5927 */                                 value = (String)attributeValues.get(attDetails[3].toUpperCase());
/* 5928 */                                 if ((value == null) || (value.equals("")) || (value.equalsIgnoreCase("null"))) {
/* 5929 */                                   value = "-";
/*      */                                 }
/* 5931 */                                 if ((!value.equals("-")) && (!attDetails[2].equals("-")) && (attDetails[2].trim().equals(""))) {
/* 5932 */                                   value = value + " " + FormatUtil.getString(attDetails[2]);
/*      */                                 }
/* 5934 */                                 if (gtype.equals("0")) {
/* 5935 */                                   out.write("\n\t\t\t<tr onmouseout=\"this.className='confheader'\" onmouseover=\"this.className='confHeaderHover'\" class=\"confheader\" style=\"word-break: break-all;\">\n\t\t\t\t<td width=\"45%\" align=\"left\" class=\"monitorinfoodd-conf\"> ");
/* 5936 */                                   out.print((String)((HashMap)request.getAttribute("disNameValues")).get(attid));
/* 5937 */                                   out.write("</td>\n\t\t\t\t<td width=\"45%\" align=\"left\" class=\"monitorinfoodd-conf \">\n\t\t\t");
/* 5938 */                                   if (((HashMap)request.getAttribute("linkValues")).get(attid) != null) {
/* 5939 */                                     out.write("\n\t\t\t\t\t");
/* 5940 */                                     out.print((String)((HashMap)request.getAttribute("linkValues")).get(attid));
/* 5941 */                                     out.write("\t\t\n\t\t\t");
/*      */                                   }
/* 5943 */                                   out.write("<span width=\"99%\" class=\"bodytextbold\" style=\"white-space:normal;word-wrap:break-word;\">\n\t\t\t\t");
/*      */                                   
/* 5945 */                                   SetTag _jspx_th_c_005fset_005f8 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5946 */                                   _jspx_th_c_005fset_005f8.setPageContext(_jspx_page_context);
/* 5947 */                                   _jspx_th_c_005fset_005f8.setParent(null);
/*      */                                   
/* 5949 */                                   _jspx_th_c_005fset_005f8.setVar("myValue");
/*      */                                   
/* 5951 */                                   _jspx_th_c_005fset_005f8.setScope("page");
/* 5952 */                                   int _jspx_eval_c_005fset_005f8 = _jspx_th_c_005fset_005f8.doStartTag();
/* 5953 */                                   if (_jspx_eval_c_005fset_005f8 != 0) {
/* 5954 */                                     if (_jspx_eval_c_005fset_005f8 != 1) {
/* 5955 */                                       out = _jspx_page_context.pushBody();
/* 5956 */                                       _jspx_th_c_005fset_005f8.setBodyContent((BodyContent)out);
/* 5957 */                                       _jspx_th_c_005fset_005f8.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5960 */                                       out.print(value);
/* 5961 */                                       int evalDoAfterBody = _jspx_th_c_005fset_005f8.doAfterBody();
/* 5962 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5965 */                                     if (_jspx_eval_c_005fset_005f8 != 1) {
/* 5966 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5969 */                                   if (_jspx_th_c_005fset_005f8.doEndTag() == 5) {
/* 5970 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8); return;
/*      */                                   }
/*      */                                   
/* 5973 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f8);
/* 5974 */                                   out.write("\n\t\t\t  \t");
/*      */                                   
/* 5976 */                                   SetTag _jspx_th_c_005fset_005f9 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 5977 */                                   _jspx_th_c_005fset_005f9.setPageContext(_jspx_page_context);
/* 5978 */                                   _jspx_th_c_005fset_005f9.setParent(null);
/*      */                                   
/* 5980 */                                   _jspx_th_c_005fset_005f9.setVar("escape");
/*      */                                   
/* 5982 */                                   _jspx_th_c_005fset_005f9.setScope("page");
/* 5983 */                                   int _jspx_eval_c_005fset_005f9 = _jspx_th_c_005fset_005f9.doStartTag();
/* 5984 */                                   if (_jspx_eval_c_005fset_005f9 != 0) {
/* 5985 */                                     if (_jspx_eval_c_005fset_005f9 != 1) {
/* 5986 */                                       out = _jspx_page_context.pushBody();
/* 5987 */                                       _jspx_th_c_005fset_005f9.setBodyContent((BodyContent)out);
/* 5988 */                                       _jspx_th_c_005fset_005f9.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5991 */                                       out.print(escape);
/* 5992 */                                       int evalDoAfterBody = _jspx_th_c_005fset_005f9.doAfterBody();
/* 5993 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5996 */                                     if (_jspx_eval_c_005fset_005f9 != 1) {
/* 5997 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 6000 */                                   if (_jspx_th_c_005fset_005f9.doEndTag() == 5) {
/* 6001 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f9); return;
/*      */                                   }
/*      */                                   
/* 6004 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f9);
/* 6005 */                                   out.write("\n\t\t\t  \t");
/* 6006 */                                   if (_jspx_meth_c_005fout_005f38(_jspx_page_context))
/*      */                                     return;
/* 6008 */                                   out.write("\t\n\t\t\t  \t\t</span>\n\t\t\t  \t");
/* 6009 */                                   if (((HashMap)request.getAttribute("linkValues")).get(attid) != null) {
/* 6010 */                                     out.write("</a>");
/*      */                                   }
/* 6012 */                                   out.write("</td></tr>\n\t\t");
/*      */                                 }
/*      */                                 else {
/* 6015 */                                   if (alterRow == 1) {
/* 6016 */                                     out.write("<div id=\"");
/* 6017 */                                     out.print(noOfCols == 5 ? "menu-conf1" : "menu-conf");
/* 6018 */                                     out.write("\"><ul>");
/*      */                                   }
/* 6020 */                                   if (alterRow - 1 == noOfCols) {
/* 6021 */                                     out.write("<ul>\t");
/*      */                                   }
/* 6023 */                                   if (!attribsInGraphs.contains(attid)) {
/* 6024 */                                     out.write("\n\t\t\t\t\t\t<li class=\"confUlWidth\"> ");
/* 6025 */                                     out.print((String)((HashMap)request.getAttribute("disNameValues")).get(attid));
/* 6026 */                                     out.write("<br>\n\t\t\t\t\t\t\t\t<span>\n\t\t\t\t\t\t\t");
/* 6027 */                                     if (((HashMap)request.getAttribute("linkValues")).get(attid) != null) {
/* 6028 */                                       out.write("\n\t\t\t\t\t\t\t\t");
/* 6029 */                                       out.print((String)((HashMap)request.getAttribute("linkValues")).get(attid));
/* 6030 */                                       out.write("\t\t\t\n\t\t\t\t\t\t\t");
/*      */                                     }
/* 6032 */                                     out.write("\t\n\t\t\t\t\t\t\t <b>\n\t\t\t\t\t\t\t \t");
/*      */                                     
/* 6034 */                                     SetTag _jspx_th_c_005fset_005f10 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6035 */                                     _jspx_th_c_005fset_005f10.setPageContext(_jspx_page_context);
/* 6036 */                                     _jspx_th_c_005fset_005f10.setParent(null);
/*      */                                     
/* 6038 */                                     _jspx_th_c_005fset_005f10.setVar("myValue");
/*      */                                     
/* 6040 */                                     _jspx_th_c_005fset_005f10.setScope("page");
/* 6041 */                                     int _jspx_eval_c_005fset_005f10 = _jspx_th_c_005fset_005f10.doStartTag();
/* 6042 */                                     if (_jspx_eval_c_005fset_005f10 != 0) {
/* 6043 */                                       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 6044 */                                         out = _jspx_page_context.pushBody();
/* 6045 */                                         _jspx_th_c_005fset_005f10.setBodyContent((BodyContent)out);
/* 6046 */                                         _jspx_th_c_005fset_005f10.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 6049 */                                         out.print(value);
/* 6050 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f10.doAfterBody();
/* 6051 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 6054 */                                       if (_jspx_eval_c_005fset_005f10 != 1) {
/* 6055 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 6058 */                                     if (_jspx_th_c_005fset_005f10.doEndTag() == 5) {
/* 6059 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f10); return;
/*      */                                     }
/*      */                                     
/* 6062 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f10);
/* 6063 */                                     out.write("\n\t\t\t\t\t\t\t  \t");
/*      */                                     
/* 6065 */                                     SetTag _jspx_th_c_005fset_005f11 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 6066 */                                     _jspx_th_c_005fset_005f11.setPageContext(_jspx_page_context);
/* 6067 */                                     _jspx_th_c_005fset_005f11.setParent(null);
/*      */                                     
/* 6069 */                                     _jspx_th_c_005fset_005f11.setVar("escape");
/*      */                                     
/* 6071 */                                     _jspx_th_c_005fset_005f11.setScope("page");
/* 6072 */                                     int _jspx_eval_c_005fset_005f11 = _jspx_th_c_005fset_005f11.doStartTag();
/* 6073 */                                     if (_jspx_eval_c_005fset_005f11 != 0) {
/* 6074 */                                       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 6075 */                                         out = _jspx_page_context.pushBody();
/* 6076 */                                         _jspx_th_c_005fset_005f11.setBodyContent((BodyContent)out);
/* 6077 */                                         _jspx_th_c_005fset_005f11.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 6080 */                                         out.print(escape);
/* 6081 */                                         int evalDoAfterBody = _jspx_th_c_005fset_005f11.doAfterBody();
/* 6082 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 6085 */                                       if (_jspx_eval_c_005fset_005f11 != 1) {
/* 6086 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 6089 */                                     if (_jspx_th_c_005fset_005f11.doEndTag() == 5) {
/* 6090 */                                       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f11); return;
/*      */                                     }
/*      */                                     
/* 6093 */                                     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f11);
/* 6094 */                                     out.write("\n\t\t\t\t\t\t\t  \t");
/* 6095 */                                     if (_jspx_meth_c_005fout_005f39(_jspx_page_context))
/*      */                                       return;
/* 6097 */                                     out.write("\n\t\t\t\t\t\t\t \t</b>\n\t\t\t\t\t\t \t ");
/* 6098 */                                     if (((HashMap)request.getAttribute("linkValues")).get(attid) != null) {
/* 6099 */                                       out.write("</a>");
/*      */                                     }
/* 6101 */                                     out.write("\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t");
/*      */                                   }
/* 6103 */                                   if (alterRow == noOfCols)
/* 6104 */                                     out.write("</ul>");
/* 6105 */                                   if (st.countTokens() == 0) {
/* 6106 */                                     out.write("</ul></div>");
/*      */                                   }
/* 6108 */                                   out.write("\t\t\t\t\t\t\n\t\t\t\t");
/*      */                                 }
/* 6110 */                                 out.write("\t\t\t\t\t\n\t");
/*      */                               }
/*      */                               
/* 6113 */                               out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t ");
/* 6114 */                               if (!gtype.equals("0")) {
/* 6115 */                                 out.write("\t \t\t\t\n\t \t\t\t</td>\n\t \t\t\t<td class=\"");
/* 6116 */                                 out.print(noOfCols == 5 ? "confOneColUIDatatd" : "confTwoColDatatd");
/* 6117 */                                 out.write("\">\t<img src=\"/images/spacer.gif\"/></td>\n\t \t\t\t</tr></table></td></tr>\n\t ");
/*      */                               }
/* 6119 */                               out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t</td>\t\n\t\t\t");
/*      */                             }
/* 6121 */                             else if (category.equals("UIINCLUDE")) {
/* 6122 */                               try { out.write("\n\t\t\t\t\t\t<td>\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t");
/* 6123 */                                 JspRuntimeLibrary.include(request, response, (String)groupDetails.get("PATH") + (((String)groupDetails.get("PATH")).indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("resourceName", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf((String)request.getAttribute("monitorname")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceType", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(request.getParameter("type")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("baseId", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(baseid1), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("resourceID", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resID), request.getCharacterEncoding()), out, true);
/* 6124 */                                 out.write("\t\t\t\t\t\t\t\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t");
/* 6125 */                               } catch (Exception e) { e.printStackTrace(); }
/* 6126 */                               out.write("\n\t\t\t");
/* 6127 */                             } else if (category.equals("UIGRAPH")) {
/* 6128 */                               out.write("\n\t\t\t\t\n\t\t\t\t\t<td>\n\t\t\t\t\t\t<div id=\"Graph-");
/* 6129 */                               out.print(graphCount);
/* 6130 */                               out.write("-Content\">\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</td>\t\t\t\t\n\t");
/* 6131 */                               graphCount++;
/*      */                             }
/* 6133 */                             out.write("\t\t\t\t\t\t\t\t\t\n");
/* 6134 */                             if ((count % 2 == 0) && (!width1.equals("99")))
/* 6135 */                               out.write("\n\t\t<td width=\"1%\">&nbsp;\n \t\t</td>\n");
/* 6136 */                             if ((count % 2 != 0) || (width1.equals("99"))) {
/* 6137 */                               out.write("\n    \t</tr>\t\n    \t</table>\n    </td>\n </tr>\n");
/*      */                             }
/*      */                           } else {
/* 6140 */                             tableId = Integer.toString(tableid++);
/*      */                             
/* 6142 */                             out.write("\t\t\n\t\t");
/* 6143 */                             if (grpct == 0) {
/* 6144 */                               out.write("<br>");
/*      */                             }
/* 6146 */                             out.write("\t\n\t\t<tr>\n\t\t\t<td>\n\t\t\t\t<div id=\"Table-");
/* 6147 */                             out.print(tableId);
/* 6148 */                             out.write("-Content\">\n\t\t\t\t</div>\n\t\t</td>\n\t\t</tr>\n");
/*      */                           }
/*      */                         }
/*      */                       }
/* 6152 */                       out.write("\t\n</table>\n<script type=\"text/javascript\">\nvar tab_Id=");
/* 6153 */                       out.print(m);
/* 6154 */                       out.write(";\nvar periodicity=");
/* 6155 */                       if (_jspx_meth_c_005fout_005f40(_jspx_page_context))
/*      */                         return;
/* 6157 */                       out.write(";\nif(document.location.hash!=null && document.location.hash!='#cf/CustomFieldValues' && tab_Id==1 && periodicity==0){ \t\nvar http=getHTTPObject();\nURL='/dashboard.do?method=generateAvailabilityHistory&type=");
/* 6158 */                       out.print(request.getParameter("type"));
/* 6159 */                       out.write("&resourceId=");
/* 6160 */                       out.print(resID);
/* 6161 */                       out.write("&period=4&isConfMonitor=true';//No I18N\nhttp.open(\"GET\",URL,true);\n//On timeout(1 minute) abort the request\nvar availTimeOut=setTimeout(function(){ if(http.readyState!=4){http.abort();}},60000);\nhttp.onreadystatechange = handleAvailability;\n\nhttp.send(null);\nvar http1=getHTTPObject();\nURL1=\"/dashboard.do?method=generateHealthHistory&type=");
/* 6162 */                       out.print(request.getParameter("type"));
/* 6163 */                       out.write("&resourceId=");
/* 6164 */                       out.print(resID);
/* 6165 */                       out.write("&period=4&isConfMonitor=true&severName=");
/* 6166 */                       out.print(serverName);
/* 6167 */                       out.write("\";//No I18N\nhttp1.open(\"GET\",URL1,true);\n//On timeout(1 minute) abort the request\nvar perfTimeOut=setTimeout(function(){ if(http1.readyState!=4){  http1.abort();}},60000);\nhttp1.onreadystatechange = handlePerformance;\nhttp1.send(null);\n\n}\n</script>\n");
/* 6168 */                     } catch (Exception exc) { exc.printStackTrace();
/*      */                     }
/* 6170 */                     out.write("\n<script type=\"text/javascript\">\nfunction populateUIGraphs(){\n\tvar noOfGraphs=\"");
/* 6171 */                     out.print(graphCount);
/* 6172 */                     out.write("\";\n\tfor(i=1;i<noOfGraphs;i++){\t   \n\t\tvar divid=\"Graph-\"+i+\"-Content\";//No I18N\n\t\thandleUIElements(divid,i,\"Graph\");//No I18N\n\t}\n}\nfunction populateUITables(){\n\tvar noOfTables=");
/* 6173 */                     out.print(tablesList);
/* 6174 */                     out.write(";\t\n\tfor(i=1;i<=noOfTables;i++){\t\n\t\tvar divid=\"Table-\"+i+\"-Content\";//No I18N\t\t\n\t\thandleUIElements(divid,i,\"Table\");//No I18N\n\t}\n}\nfunction handleUIElements(divid,i,uiElementType)\n{\t\t\n\tvar http3=getHTTPObject();\n\tvar parameter=\"");
/* 6175 */                     out.print((String)request.getAttribute("AddToParameter"));
/* 6176 */                     out.write("\";\t\n//\tURL=parameter+'&tableId='+i;//No I18N\n\tif(uiElementType=='Table'){\n\t\tURL=parameter+'&tableId='+i;//No I18N\n\t}\n\telse{\n\t\tvar scrWidth=\"");
/* 6177 */                     out.print(scrWidth);
/* 6178 */                     out.write("\";\n\t\tURL=parameter+'&ScreenWidth='+scrWidth+'&graphid='+i;//No I18N\n\t}\n\thttp3.open(\"GET\",URL,true);\t\n\t// On timeout(1 minute) abort the request\n\t var timeOut= setTimeout(function(){if(http3.readyState!=4){  http3.abort();}},60000);\n\thttp3.onreadystatechange = function (){\t\t\n\t\tif(http3.readyState == 4)\n\t        {\t\t\n\t                if (http3.status == 200)\n\t                {         \n\t                \tvar ele = document.getElementById(divid);\t                \t\n\t                \tif(ele){\n\t\t\t\t\t\t\tele.innerHTML = http3.responseText;\n\t                \t}\t\t\t\t\t\n\t\t\t}\n\t        clearTimeout(timeOut);\n\t\t}\n\t};\n\thttp3.send(null);\n}\nfunction myOnLoad() {\t\t\n\tvar temp=");
/* 6179 */                     out.print(request.getAttribute("tabId"));
/* 6180 */                     out.write(";\t\n\tif(document.location.hash!=null && document.location.hash.indexOf('#cf/')!=-1 || (temp!=null && temp==0)){\t\n\t\tif(document.getElementById(\"monitorInformwithTab\")){\n\t\t\tdocument.getElementById(\"monitorInformwithTab\").style.display=\"none\";\n\t\t}\n\t\tShowMonitorInfoDiv(0);\n\t}\n\telse{\t\n\tpopulateUITables();\n\tpopulateUIGraphs();\n\tpopulatePeriodForm();\n\tvar tabs=");
/* 6181 */                     out.print(noOftabs);
/* 6182 */                     out.write(";\t\n\tif(tabs>0){\n\t\tif(temp==null){\n\t\t\t\ttemp=1;\n\t\t}\n\t\tvar id=\"Group_tab\"+temp;\t// No I18N\t\t\n\t\t\tif(id==\"Group_tab1\" && periodicity==0){\t\t\t\t\n\t\t\tif(document.getElementById(\"monitorInformwithTab\")){\n\t\t\t\tdocument.getElementById(\"monitorInformwithTab\").style.display=\"block\";\n\t\t\t}\n\t\t}\n\t\telse{\n\t\t\tif(document.getElementById(\"monitorInformwithTab\")){\n\t\t\t\tdocument.getElementById(\"monitorInformwithTab\").style.display=\"none\";\n\t\t\t}\n\t\t}\n\t\tif(document.getElementById(id)){\n\t\t\n\t\t\tdocument.getElementById(id).style.display=\"block\";\n\t\t\t}\n\t\t\n\t\t}\n\t}\n}\n</script>\n\n\n   \n<!--$Id:$-->\n");
/*      */                   }
/* 6184 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 6185 */         out = _jspx_out;
/* 6186 */         if ((out != null) && (out.getBufferSize() != 0))
/* 6187 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 6188 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 6191 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6197 */     PageContext pageContext = _jspx_page_context;
/* 6198 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6200 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6201 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 6202 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 6204 */     _jspx_th_c_005fif_005f0.setTest("${!empty param.haid}");
/* 6205 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 6206 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 6208 */         out.write(10);
/* 6209 */         if (_jspx_meth_c_005fcatch_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 6210 */           return true;
/* 6211 */         out.write(10);
/* 6212 */         if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 6213 */           return true;
/* 6214 */         out.write(10);
/* 6215 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 6216 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6220 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 6221 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6222 */       return true;
/*      */     }
/* 6224 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6225 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6230 */     PageContext pageContext = _jspx_page_context;
/* 6231 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6233 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 6234 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 6235 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6237 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 6238 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 6240 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 6241 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 6243 */           out.write("\n      ");
/* 6244 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 6245 */             return true;
/* 6246 */           out.write("\n      ");
/* 6247 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 6248 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6252 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 6253 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6256 */         int tmp185_184 = 0; int[] tmp185_182 = _jspx_push_body_count_c_005fcatch_005f0; int tmp187_186 = tmp185_182[tmp185_184];tmp185_182[tmp185_184] = (tmp187_186 - 1); if (tmp187_186 <= 0) break;
/* 6257 */         out = _jspx_page_context.popBody(); }
/* 6258 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 6260 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 6261 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 6263 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 6268 */     PageContext pageContext = _jspx_page_context;
/* 6269 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6271 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 6272 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 6273 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 6275 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 6277 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 6278 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 6279 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 6280 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6281 */       return true;
/*      */     }
/* 6283 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6284 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6289 */     PageContext pageContext = _jspx_page_context;
/* 6290 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6292 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6293 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 6294 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6296 */     _jspx_th_c_005fif_005f1.setTest("${(empty invalidhaid)}");
/* 6297 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 6298 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 6300 */         out.write(10);
/* 6301 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 6302 */           return true;
/* 6303 */         out.write(10);
/* 6304 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 6305 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6309 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 6310 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6311 */       return true;
/*      */     }
/* 6313 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6314 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6319 */     PageContext pageContext = _jspx_page_context;
/* 6320 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6322 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6323 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 6324 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 6326 */     _jspx_th_c_005fset_005f0.setVar("haid");
/* 6327 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 6328 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 6329 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 6330 */         out = _jspx_page_context.pushBody();
/* 6331 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 6332 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6335 */         out.write(10);
/* 6336 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 6337 */           return true;
/* 6338 */         out.write(10);
/* 6339 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 6340 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6343 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 6344 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6347 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 6348 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 6349 */       return true;
/*      */     }
/* 6351 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 6352 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6357 */     PageContext pageContext = _jspx_page_context;
/* 6358 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6360 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6361 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 6362 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 6364 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 6365 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 6366 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 6367 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6368 */       return true;
/*      */     }
/* 6370 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6371 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6376 */     PageContext pageContext = _jspx_page_context;
/* 6377 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6379 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.get(MessageTag.class);
/* 6380 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6381 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 6383 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.eum.agentdown");
/* 6384 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6385 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 6386 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6387 */         out = _jspx_page_context.pushBody();
/* 6388 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 6389 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6392 */         out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 6393 */         if (_jspx_meth_fmt_005fparam_005f0(_jspx_th_fmt_005fmessage_005f0, _jspx_page_context))
/* 6394 */           return true;
/* 6395 */         out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 6396 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 6397 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6400 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6401 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6404 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6405 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6406 */       return true;
/*      */     }
/* 6408 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6409 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparam_005f0(JspTag _jspx_th_fmt_005fmessage_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6414 */     PageContext pageContext = _jspx_page_context;
/* 6415 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6417 */     ParamTag _jspx_th_fmt_005fparam_005f0 = (ParamTag)this._005fjspx_005ftagPool_005ffmt_005fparam.get(ParamTag.class);
/* 6418 */     _jspx_th_fmt_005fparam_005f0.setPageContext(_jspx_page_context);
/* 6419 */     _jspx_th_fmt_005fparam_005f0.setParent((Tag)_jspx_th_fmt_005fmessage_005f0);
/* 6420 */     int _jspx_eval_fmt_005fparam_005f0 = _jspx_th_fmt_005fparam_005f0.doStartTag();
/* 6421 */     if (_jspx_eval_fmt_005fparam_005f0 != 0) {
/* 6422 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 6423 */         out = _jspx_page_context.pushBody();
/* 6424 */         _jspx_th_fmt_005fparam_005f0.setBodyContent((BodyContent)out);
/* 6425 */         _jspx_th_fmt_005fparam_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6428 */         if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_fmt_005fparam_005f0, _jspx_page_context))
/* 6429 */           return true;
/* 6430 */         int evalDoAfterBody = _jspx_th_fmt_005fparam_005f0.doAfterBody();
/* 6431 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6434 */       if (_jspx_eval_fmt_005fparam_005f0 != 1) {
/* 6435 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6438 */     if (_jspx_th_fmt_005fparam_005f0.doEndTag() == 5) {
/* 6439 */       this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 6440 */       return true;
/*      */     }
/* 6442 */     this._005fjspx_005ftagPool_005ffmt_005fparam.reuse(_jspx_th_fmt_005fparam_005f0);
/* 6443 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_fmt_005fparam_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6448 */     PageContext pageContext = _jspx_page_context;
/* 6449 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6451 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.get(WriteTag.class);
/* 6452 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 6453 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_fmt_005fparam_005f0);
/*      */     
/* 6455 */     _jspx_th_bean_005fwrite_005f0.setName("agents");
/* 6456 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 6457 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 6458 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 6459 */       return true;
/*      */     }
/* 6461 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 6462 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6467 */     PageContext pageContext = _jspx_page_context;
/* 6468 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6470 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6471 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 6472 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 6474 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 6475 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 6476 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 6477 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6478 */       return true;
/*      */     }
/* 6480 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6481 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6486 */     PageContext pageContext = _jspx_page_context;
/* 6487 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6489 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6490 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 6491 */     _jspx_th_c_005fout_005f2.setParent(null);
/*      */     
/* 6493 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 6494 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 6495 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 6496 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6497 */       return true;
/*      */     }
/* 6499 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6500 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6505 */     PageContext pageContext = _jspx_page_context;
/* 6506 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6508 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6509 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 6510 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 6512 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 6513 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 6514 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 6516 */         out.write("\n   \t\t\t alertUser();\n   \t\t \treturn;\n   \t\t");
/* 6517 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 6518 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6522 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 6523 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 6524 */       return true;
/*      */     }
/* 6526 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 6527 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6532 */     PageContext pageContext = _jspx_page_context;
/* 6533 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6535 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6536 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6537 */     _jspx_th_c_005fout_005f3.setParent(null);
/*      */     
/* 6539 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 6540 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6541 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6542 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6543 */       return true;
/*      */     }
/* 6545 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6551 */     PageContext pageContext = _jspx_page_context;
/* 6552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6554 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6555 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6556 */     _jspx_th_c_005fout_005f4.setParent(null);
/*      */     
/* 6558 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 6559 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6560 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6561 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6562 */       return true;
/*      */     }
/* 6564 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6565 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6570 */     PageContext pageContext = _jspx_page_context;
/* 6571 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6573 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6574 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6575 */     _jspx_th_c_005fout_005f5.setParent(null);
/*      */     
/* 6577 */     _jspx_th_c_005fout_005f5.setValue("${param.haid}");
/* 6578 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6579 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6580 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6581 */       return true;
/*      */     }
/* 6583 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6584 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6589 */     PageContext pageContext = _jspx_page_context;
/* 6590 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6592 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6593 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 6594 */     _jspx_th_c_005fout_005f6.setParent(null);
/*      */     
/* 6596 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 6597 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 6598 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 6599 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6600 */       return true;
/*      */     }
/* 6602 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6603 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6608 */     PageContext pageContext = _jspx_page_context;
/* 6609 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6611 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6612 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 6613 */     _jspx_th_c_005fout_005f7.setParent(null);
/*      */     
/* 6615 */     _jspx_th_c_005fout_005f7.setValue("${parentids}");
/* 6616 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 6617 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 6618 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6619 */       return true;
/*      */     }
/* 6621 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6622 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6627 */     PageContext pageContext = _jspx_page_context;
/* 6628 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6630 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6631 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 6632 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6634 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 6635 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 6636 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 6637 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6638 */       return true;
/*      */     }
/* 6640 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_logic_005fnotPresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6646 */     PageContext pageContext = _jspx_page_context;
/* 6647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6649 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6650 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 6651 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_logic_005fnotPresent_005f5);
/*      */     
/* 6653 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 6654 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 6655 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 6656 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6657 */       return true;
/*      */     }
/* 6659 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6660 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6665 */     PageContext pageContext = _jspx_page_context;
/* 6666 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6668 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6669 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6670 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 6672 */     _jspx_th_c_005fout_005f10.setValue("${eachCustomConfig.DESCRIPTION}");
/* 6673 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6674 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6675 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6676 */       return true;
/*      */     }
/* 6678 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6679 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6684 */     PageContext pageContext = _jspx_page_context;
/* 6685 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6687 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6688 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6689 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 6691 */     _jspx_th_c_005fout_005f11.setValue("${eachCustomConfig.IMAGE}");
/* 6692 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6693 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6694 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6695 */       return true;
/*      */     }
/* 6697 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6698 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6703 */     PageContext pageContext = _jspx_page_context;
/* 6704 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6706 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6707 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6708 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 6710 */     _jspx_th_c_005fout_005f12.setValue("${eachCustomConfig.DISPLAYNAME}");
/* 6711 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6712 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6713 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6714 */       return true;
/*      */     }
/* 6716 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6717 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6722 */     PageContext pageContext = _jspx_page_context;
/* 6723 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6725 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6726 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6727 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 6729 */     _jspx_th_c_005fout_005f13.setValue("${eachCustomConfig.DESCRIPTION}");
/* 6730 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6731 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6732 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6733 */       return true;
/*      */     }
/* 6735 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6736 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6741 */     PageContext pageContext = _jspx_page_context;
/* 6742 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6744 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6745 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6746 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 6748 */     _jspx_th_c_005fout_005f14.setValue("${eachCustomConfig.METHODNAME}");
/* 6749 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6750 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6751 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6752 */       return true;
/*      */     }
/* 6754 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6755 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6760 */     PageContext pageContext = _jspx_page_context;
/* 6761 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6763 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6764 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6765 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 6767 */     _jspx_th_c_005fout_005f15.setValue("${eachCustomConfig.PopupProps.WinName}");
/* 6768 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6769 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6770 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6771 */       return true;
/*      */     }
/* 6773 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6779 */     PageContext pageContext = _jspx_page_context;
/* 6780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6782 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6783 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 6784 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 6786 */     _jspx_th_c_005fout_005f16.setValue("${eachCustomConfig.PopupProps.Height}");
/* 6787 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 6788 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 6789 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6790 */       return true;
/*      */     }
/* 6792 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6793 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6798 */     PageContext pageContext = _jspx_page_context;
/* 6799 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6801 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6802 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 6803 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 6805 */     _jspx_th_c_005fout_005f17.setValue("${eachCustomConfig.PopupProps.Width}");
/* 6806 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 6807 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 6808 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6809 */       return true;
/*      */     }
/* 6811 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6817 */     PageContext pageContext = _jspx_page_context;
/* 6818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6820 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6821 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 6822 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 6824 */     _jspx_th_c_005fout_005f18.setValue("${eachCustomConfig.PopupProps.Top}");
/* 6825 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 6826 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 6827 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6828 */       return true;
/*      */     }
/* 6830 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6836 */     PageContext pageContext = _jspx_page_context;
/* 6837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6839 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6840 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 6841 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 6843 */     _jspx_th_c_005fout_005f19.setValue("${eachCustomConfig.PopupProps.Left}");
/* 6844 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 6845 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 6846 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6847 */       return true;
/*      */     }
/* 6849 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6850 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6855 */     PageContext pageContext = _jspx_page_context;
/* 6856 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6858 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6859 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 6860 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 6862 */     _jspx_th_c_005fout_005f20.setValue("${eachCustomConfig.PopupProps.IsResizable}");
/* 6863 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 6864 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 6865 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6866 */       return true;
/*      */     }
/* 6868 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6874 */     PageContext pageContext = _jspx_page_context;
/* 6875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6877 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6878 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 6879 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 6881 */     _jspx_th_c_005fout_005f21.setValue("${eachCustomConfig.PopupProps.IsScrollBar}");
/* 6882 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 6883 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 6884 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6885 */       return true;
/*      */     }
/* 6887 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6893 */     PageContext pageContext = _jspx_page_context;
/* 6894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6896 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6897 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 6898 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 6900 */     _jspx_th_c_005fout_005f22.setValue("${eachCustomConfig.IMAGE}");
/* 6901 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 6902 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 6903 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6904 */       return true;
/*      */     }
/* 6906 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6907 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_logic_005fnotPresent_005f8, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6912 */     PageContext pageContext = _jspx_page_context;
/* 6913 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6915 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6916 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 6917 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_logic_005fnotPresent_005f8);
/*      */     
/* 6919 */     _jspx_th_c_005fout_005f23.setValue("${eachCustomConfig.DISPLAYNAME}");
/* 6920 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 6921 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 6922 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6923 */       return true;
/*      */     }
/* 6925 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6926 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6931 */     PageContext pageContext = _jspx_page_context;
/* 6932 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6934 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6935 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 6936 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 6938 */     _jspx_th_c_005fout_005f24.setValue("${eachCustomConfig.DESCRIPTION}");
/* 6939 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 6940 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 6941 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6942 */       return true;
/*      */     }
/* 6944 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6950 */     PageContext pageContext = _jspx_page_context;
/* 6951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6953 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6954 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 6955 */     _jspx_th_c_005fout_005f25.setParent(null);
/*      */     
/* 6957 */     _jspx_th_c_005fout_005f25.setValue("${param.resourceid}");
/* 6958 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 6959 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 6960 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6961 */       return true;
/*      */     }
/* 6963 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6964 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6969 */     PageContext pageContext = _jspx_page_context;
/* 6970 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6972 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6973 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 6974 */     _jspx_th_c_005fout_005f26.setParent(null);
/*      */     
/* 6976 */     _jspx_th_c_005fout_005f26.setValue("${param.resourceid}");
/* 6977 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 6978 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 6979 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6980 */       return true;
/*      */     }
/* 6982 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6988 */     PageContext pageContext = _jspx_page_context;
/* 6989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6991 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6992 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 6993 */     _jspx_th_c_005fout_005f27.setParent(null);
/*      */     
/* 6995 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 6996 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 6997 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 6998 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6999 */       return true;
/*      */     }
/* 7001 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 7002 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7007 */     PageContext pageContext = _jspx_page_context;
/* 7008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7010 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7011 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 7012 */     _jspx_th_c_005fout_005f28.setParent(null);
/*      */     
/* 7014 */     _jspx_th_c_005fout_005f28.setValue("${param.resourceid}");
/* 7015 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 7016 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 7017 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 7018 */       return true;
/*      */     }
/* 7020 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 7021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7026 */     PageContext pageContext = _jspx_page_context;
/* 7027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7029 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7030 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 7031 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7033 */     _jspx_th_c_005fout_005f29.setValue("${ha.key}");
/* 7034 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 7035 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 7036 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 7037 */       return true;
/*      */     }
/* 7039 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 7040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7045 */     PageContext pageContext = _jspx_page_context;
/* 7046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7048 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7049 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 7050 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7052 */     _jspx_th_c_005fout_005f30.setValue("${ha.value}");
/* 7053 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 7054 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 7055 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 7056 */       return true;
/*      */     }
/* 7058 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 7059 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7064 */     PageContext pageContext = _jspx_page_context;
/* 7065 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7067 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 7068 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 7069 */     _jspx_th_c_005fset_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 7071 */     _jspx_th_c_005fset_005f5.setVar("monitorName");
/* 7072 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 7073 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 7074 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 7075 */         out = _jspx_page_context.pushBody();
/* 7076 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 7077 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 7078 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7081 */         if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fset_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 7082 */           return true;
/* 7083 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 7084 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7087 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 7088 */         out = _jspx_page_context.popBody();
/* 7089 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 7092 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 7093 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 7094 */       return true;
/*      */     }
/* 7096 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 7097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7102 */     PageContext pageContext = _jspx_page_context;
/* 7103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7105 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7106 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 7107 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 7109 */     _jspx_th_c_005fout_005f31.setValue("${ha.value}");
/* 7110 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 7111 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 7112 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 7113 */       return true;
/*      */     }
/* 7115 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 7116 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_logic_005fpresent_005f15, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 7121 */     PageContext pageContext = _jspx_page_context;
/* 7122 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7124 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7125 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 7126 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_logic_005fpresent_005f15);
/*      */     
/* 7128 */     _jspx_th_c_005fout_005f32.setValue("${ha.key}");
/* 7129 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 7130 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 7131 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 7132 */       return true;
/*      */     }
/* 7134 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 7135 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f15, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7140 */     PageContext pageContext = _jspx_page_context;
/* 7141 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7143 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7144 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 7145 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f15);
/*      */     
/* 7147 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 7148 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 7149 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 7150 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7151 */       return true;
/*      */     }
/* 7153 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 7154 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7159 */     PageContext pageContext = _jspx_page_context;
/* 7160 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7162 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7163 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 7164 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 7166 */     _jspx_th_c_005fout_005f33.setValue("${ha.key}");
/* 7167 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 7168 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 7169 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 7170 */       return true;
/*      */     }
/* 7172 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 7173 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7178 */     PageContext pageContext = _jspx_page_context;
/* 7179 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7181 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7182 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 7183 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 7185 */     _jspx_th_c_005fout_005f34.setValue("${ha.value}");
/* 7186 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 7187 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 7188 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 7189 */       return true;
/*      */     }
/* 7191 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 7192 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fforEach_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7197 */     PageContext pageContext = _jspx_page_context;
/* 7198 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7200 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 7201 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 7202 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f2);
/*      */     
/* 7204 */     _jspx_th_c_005fset_005f6.setVar("monitorName");
/* 7205 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 7206 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 7207 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 7208 */         out = _jspx_page_context.pushBody();
/* 7209 */         _jspx_push_body_count_c_005fforEach_005f2[0] += 1;
/* 7210 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 7211 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7214 */         if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fset_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f2))
/* 7215 */           return true;
/* 7216 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 7217 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7220 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 7221 */         out = _jspx_page_context.popBody();
/* 7222 */         _jspx_push_body_count_c_005fforEach_005f2[0] -= 1;
/*      */       }
/*      */     }
/* 7225 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 7226 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 7227 */       return true;
/*      */     }
/* 7229 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f6);
/* 7230 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7235 */     PageContext pageContext = _jspx_page_context;
/* 7236 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7238 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7239 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 7240 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 7242 */     _jspx_th_c_005fout_005f35.setValue("${ha.value}");
/* 7243 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 7244 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 7245 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 7246 */       return true;
/*      */     }
/* 7248 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 7249 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_logic_005fpresent_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7254 */     PageContext pageContext = _jspx_page_context;
/* 7255 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7257 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7258 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 7259 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_logic_005fpresent_005f16);
/*      */     
/* 7261 */     _jspx_th_c_005fout_005f36.setValue("${ha.key}");
/* 7262 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 7263 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 7264 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 7265 */       return true;
/*      */     }
/* 7267 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 7268 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_logic_005fpresent_005f16, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f2) throws Throwable
/*      */   {
/* 7273 */     PageContext pageContext = _jspx_page_context;
/* 7274 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7276 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7277 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 7278 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f16);
/*      */     
/* 7280 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 7281 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 7282 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 7283 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7284 */       return true;
/*      */     }
/* 7286 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 7287 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f16(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7292 */     PageContext pageContext = _jspx_page_context;
/* 7293 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7295 */     IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7296 */     _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 7297 */     _jspx_th_c_005fif_005f16.setParent(null);
/*      */     
/* 7299 */     _jspx_th_c_005fif_005f16.setTest("${empty associatedmgs}");
/* 7300 */     int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 7301 */     if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */       for (;;) {
/* 7303 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 7304 */         if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 7305 */           return true;
/* 7306 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 7307 */         if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fif_005f16, _jspx_page_context))
/* 7308 */           return true;
/* 7309 */         out.write("</td>\n\t ");
/* 7310 */         int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 7311 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7315 */     if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 7316 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 7317 */       return true;
/*      */     }
/* 7319 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 7320 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7325 */     PageContext pageContext = _jspx_page_context;
/* 7326 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7328 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7329 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 7330 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7332 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 7333 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 7334 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 7335 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 7336 */       return true;
/*      */     }
/* 7338 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 7339 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7344 */     PageContext pageContext = _jspx_page_context;
/* 7345 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7347 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7348 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 7349 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 7351 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.urlmonitor.none.text");
/* 7352 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 7353 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 7354 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7355 */       return true;
/*      */     }
/* 7357 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 7358 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7363 */     PageContext pageContext = _jspx_page_context;
/* 7364 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7366 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 7367 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 7368 */     _jspx_th_fmt_005fmessage_005f6.setParent(null);
/*      */     
/* 7370 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 7371 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 7372 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 7373 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 7374 */       return true;
/*      */     }
/* 7376 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 7377 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7382 */     PageContext pageContext = _jspx_page_context;
/* 7383 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7385 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7386 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 7387 */     _jspx_th_c_005fout_005f37.setParent(null);
/*      */     
/* 7389 */     _jspx_th_c_005fout_005f37.setValue("${reportPeriodmsg}");
/* 7390 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 7391 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 7392 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 7393 */       return true;
/*      */     }
/* 7395 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 7396 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7401 */     PageContext pageContext = _jspx_page_context;
/* 7402 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7404 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 7405 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 7406 */     _jspx_th_c_005fout_005f38.setParent(null);
/*      */     
/* 7408 */     _jspx_th_c_005fout_005f38.setValue("${myValue}");
/*      */     
/* 7410 */     _jspx_th_c_005fout_005f38.setEscapeXml("${escape}");
/* 7411 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 7412 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 7413 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 7414 */       return true;
/*      */     }
/* 7416 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 7417 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7422 */     PageContext pageContext = _jspx_page_context;
/* 7423 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7425 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 7426 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 7427 */     _jspx_th_c_005fout_005f39.setParent(null);
/*      */     
/* 7429 */     _jspx_th_c_005fout_005f39.setValue("${myValue}");
/*      */     
/* 7431 */     _jspx_th_c_005fout_005f39.setEscapeXml("${escape}");
/* 7432 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 7433 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 7434 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 7435 */       return true;
/*      */     }
/* 7437 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 7438 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f40(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7443 */     PageContext pageContext = _jspx_page_context;
/* 7444 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7446 */     OutTag _jspx_th_c_005fout_005f40 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7447 */     _jspx_th_c_005fout_005f40.setPageContext(_jspx_page_context);
/* 7448 */     _jspx_th_c_005fout_005f40.setParent(null);
/*      */     
/* 7450 */     _jspx_th_c_005fout_005f40.setValue("${perioicitySelected}");
/* 7451 */     int _jspx_eval_c_005fout_005f40 = _jspx_th_c_005fout_005f40.doStartTag();
/* 7452 */     if (_jspx_th_c_005fout_005f40.doEndTag() == 5) {
/* 7453 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 7454 */       return true;
/*      */     }
/* 7456 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f40);
/* 7457 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ConfDetailsUserArea_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */